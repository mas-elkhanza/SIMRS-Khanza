/*
  by Ananda Widitomo,S.Kom.
 */
package bridging;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Pelaksana pembuatan TTE untuk SATU penanda tangan, apa pun model dokumennya.
 *
 * Tiga model (diuji ke staging 1 Agustus 2026, seluruhnya diterima server):
 * <table>
 *   <tr><th>Model</th><th>Provenance</th><th>Task</th></tr>
 *   <tr><td>single</td><td>1 per dokumen, agent = author + custodian</td><td>1 untuk semua dokumen penanda ini</td></tr>
 *   <tr><td>paralel</td><td>1 per DOKUMEN memuat SELURUH penanda sebagai agent; dipakai bersama</td>
 *       <td>1 per PENANDA, menunjuk Provenance yang sama</td></tr>
 *   <tr><td>serial</td><td>1 per PENANDA; yang ke-n menunjuk Provenance ke-(n-1) lewat entity[]</td>
 *       <td>1 per penanda</td></tr>
 * </table>
 *
 * Kelas ini sengaja memakai POST individual (Provenance lalu Task), bukan Bundle transaction:
 * pada model paralel sebuah Provenance dipakai bersama beberapa penanda (jadi tidak selalu perlu
 * dibuat), dan pada model serial Provenance berikutnya BUTUH id pendahulunya — dua-duanya tidak
 * bisa dinyatakan dalam satu transaction sekali kirim. Jalur single yang lama tetap memakai
 * {@link SatuSehatBundleProvenance} agar sifat atomiknya tidak hilang.
 */
public class SatuSehatSignatureFlow {

    /** Untuk menyalurkan pesan ke panel Log TTE tanpa mengikat kelas ini ke Swing. */
    public interface Log {
        void info(String pesan);
    }

    public static class Hasil {
        public String taskUuid = "";
        /** kunci baris ({@code targetRef|ihsPenanda}) -> id Provenance yang dipakai baris itu. */
        public final Map<String, String> provenancePerBaris = new LinkedHashMap<>();
        public String pesan = "";
        public boolean berhasil = false;
    }

    private final SatuSehatProvenance provSender = new SatuSehatProvenance();
    private final SatuSehatTask taskSender = new SatuSehatTask();
    private final SatuSehatProvenanceStore store = new SatuSehatProvenanceStore();

    /**
     * Buat (atau pakai ulang) Provenance untuk tiap baris, lalu SATU Task milik penanda ini.
     *
     * @param baris          baris tanda tangan yang dicentang — SELURUHNYA milik penanda yang sama.
     * @param penandaPerDok  targetRef -> daftar SELURUH penanda dokumen itu; dipakai model paralel
     *                       yang menaruh semua penanda dalam satu Provenance.
     * @param idEncounter    Encounter kunjungan; "" bila memakai varian bulk lintas-encounter.
     * @param bulkOut        true = Task "electronic-sign-doc-out" (khusus target DiagnosticReport).
     */
    public Hasil kirim(String noRawat,
                       List<SatuSehatBridgingTTE.DokumenTte> baris,
                       Map<String, List<SatuSehatProvenance.Penanda>> penandaPerDok,
                       String idPenanda, String namaPenanda,
                       String idEncounter, boolean bulkOut, Log log) {
        Hasil hasil = new Hasil();
        if (baris == null || baris.isEmpty()) {
            hasil.pesan = "Tidak ada berkas terpilih.";
            return hasil;
        }
        if (idPenanda == null || idPenanda.trim().equals("")) {
            hasil.pesan = "IHS Practitioner penanda tangan belum ter-mapping.";
            return hasil;
        }

        List<String> provIds = new ArrayList<>();
        try {
            for (SatuSehatBridgingTTE.DokumenTte d : baris) {
                if (d.terkunci) {
                    // Penjaga terakhir: model serial tak boleh melompati giliran karena Provenance
                    // berikutnya harus menunjuk id pendahulunya.
                    catat(log, "Dilewati (menunggu giliran): " + d.display + " — " + d.alasanKunci);
                    continue;
                }
                String idProv = provenanceUntuk(noRawat, d, penandaPerDok, log);
                if (idProv.equals("")) {
                    hasil.pesan = "Provenance gagal dibuat untuk " + d.display + ".";
                    return hasil;
                }
                d.idProvenance = idProv;
                hasil.provenancePerBaris.put(d.kunci(), idProv);
                provIds.add(idProv);
            }

            if (provIds.isEmpty()) {
                hasil.pesan = "Semua berkas terpilih sedang menunggu giliran tanda tangan sebelumnya.";
                return hasil;
            }

            hasil.taskUuid = bulkOut
                    ? taskSender.buatTaskBulkDiagnosticReport(idPenanda, namaPenanda, provIds)
                    : taskSender.buatTask(idPenanda, namaPenanda, idEncounter, provIds);
            if (hasil.taskUuid.equals("")) {
                hasil.pesan = "Task terkirim tetapi id-nya tidak terbaca dari respons server.";
                return hasil;
            }

            for (SatuSehatBridgingTTE.DokumenTte d : baris) {
                String idProv = hasil.provenancePerBaris.get(d.kunci());
                if (idProv == null) {
                    continue;
                }
                d.taskUuid = hasil.taskUuid;
                store.simpan(noRawat, d.jenis, d.targetRef, idProv, hasil.taskUuid, "requested",
                        idPenanda, d.peran, d.urutan, d.idProvenanceSebelumnya);
            }
            hasil.berhasil = true;
            hasil.pesan = "Task " + hasil.taskUuid + " dibuat untuk " + provIds.size() + " berkas.";
        } catch (Exception e) {
            hasil.pesan = "Gagal membuat TTE : " + e;
            System.out.println("Notifikasi SignatureFlow : " + e);
        }
        return hasil;
    }

    /**
     * Id Provenance yang harus dipakai baris ini — dibuat baru, atau dipakai ulang pada model
     * paralel (satu Provenance milik bersama seluruh penanda dokumen tersebut).
     */
    private String provenanceUntuk(String noRawat, SatuSehatBridgingTTE.DokumenTte d,
                                   Map<String, List<SatuSehatProvenance.Penanda>> penandaPerDok,
                                   Log log) throws Exception {
        if (!d.idProvenance.equals("")) {
            return d.idProvenance;   // retry: Provenance placeholder lama dipakai ulang
        }

        if (d.model == SatuSehatTteModel.Model.PARALEL) {
            String bersama = provenanceBersama(noRawat, d.targetRef);
            if (!bersama.equals("")) {
                catat(log, "Paralel: memakai Provenance " + bersama + " yang sudah dibuat penanda lain"
                        + " untuk " + d.display + ".");
                return bersama;
            }
            List<SatuSehatProvenance.Penanda> semua = penandaPerDok.get(d.targetRef);
            if (semua == null || semua.isEmpty()) {
                semua = satuPenanda(d);
            }
            catat(log, "Paralel: membuat 1 Provenance dengan " + semua.size() + " penanda untuk "
                    + d.display + ".");
            return provSender.buatPlaceholder(d.targetRef, d.display, semua, d.mulaiUtc, d.selesaiUtc, "");
        }

        if (d.model == SatuSehatTteModel.Model.SERIAL) {
            String sebelumnya = d.idProvenanceSebelumnya;
            if (sebelumnya.equals("") && d.urutan > 1) {
                sebelumnya = provenanceGiliranSebelumnya(noRawat, d.targetRef, d.urutan);
            }
            if (!sebelumnya.equals("")) {
                catat(log, "Serial: Provenance giliran " + d.urutan + " menunjuk " + sebelumnya
                        + " untuk " + d.display + ".");
            }
            d.idProvenanceSebelumnya = sebelumnya;
            return provSender.buatPlaceholder(d.targetRef, d.display, satuPenanda(d),
                    d.mulaiUtc, d.selesaiUtc, sebelumnya);
        }

        return provSender.buatPlaceholder(d.targetRef, d.display, satuPenanda(d),
                d.mulaiUtc, d.selesaiUtc, "");
    }

    /** Provenance dokumen yang sudah dibuat penanda lain (model paralel). "" bila belum ada. */
    private String provenanceBersama(String noRawat, String targetRef) {
        for (SatuSehatProvenanceStore.Baris b : store.ambilSemua(noRawat, targetRef)) {
            if (!b.idProvenance.equals("")) {
                return b.idProvenance;
            }
        }
        return "";
    }

    /** Id Provenance giliran sebelumnya pada model serial. "" bila belum ada. */
    private String provenanceGiliranSebelumnya(String noRawat, String targetRef, int urutan) {
        String kandidat = "";
        int terdekat = -1;
        for (SatuSehatProvenanceStore.Baris b : store.ambilSemua(noRawat, targetRef)) {
            if (b.urutan < urutan && b.urutan > terdekat && !b.idProvenance.equals("")) {
                terdekat = b.urutan;
                kandidat = b.idProvenance;
            }
        }
        return kandidat;
    }

    private List<SatuSehatProvenance.Penanda> satuPenanda(SatuSehatBridgingTTE.DokumenTte d) {
        List<SatuSehatProvenance.Penanda> satu = new ArrayList<>();
        satu.add(new SatuSehatProvenance.Penanda(d.signerIhs, d.signer, d.peran));
        return satu;
    }

    private void catat(Log log, String pesan) {
        if (log != null) {
            log.info(pesan);
        } else {
            System.out.println("SignatureFlow : " + pesan);
        }
    }
}
