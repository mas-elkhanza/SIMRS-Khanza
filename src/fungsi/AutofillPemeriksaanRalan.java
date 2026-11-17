package fungsi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.JTextComponent;

/**
 * Mengisi field form dari data pemeriksaan_ralan pada no_rawat yang sama.
 * Semua kolom diambil sekaligus dengan satu query. Bila no_rawat belum punya
 * data pemeriksaan_ralan, field dibiarkan apa adanya. Bila ada beberapa baris
 * SOAP, yang dipakai adalah yang paling awal (tgl_perawatan, jam_rawat).
 *
 * <pre>
 * AutofillPemeriksaanRalan.dari(TNoRw.getText())
 *         .isi("keluhan",KeluhanUtama)
 *         .isi("tensi",TD)
 *         .jalankan();
 * </pre>
 *
 * Untuk rawat inap gunakan AutofillPemeriksaanRanap, yang memakai builder yang
 * sama tetapi membaca tabel pemeriksaan_ranap.
 *
 * Nama kolom harus benar-benar ada di tabel sumber. Karena diambil dalam satu
 * query, satu nama kolom yang salah membatalkan seluruh pengisian.
 */
public final class AutofillPemeriksaanRalan {
    private final String tabel,noRawat;
    private final List<String> kolom=new ArrayList<>();
    private final List<JTextComponent> field=new ArrayList<>();

    private AutofillPemeriksaanRalan(String tabel,String noRawat){
        this.tabel=tabel;
        this.noRawat=noRawat;
    }

    /** @param noRawat no_rawat yang datanya dicari di pemeriksaan_ralan
     * @return builder untuk didaftari field lewat isi(...) */
    public static AutofillPemeriksaanRalan dari(String noRawat){
        return new AutofillPemeriksaanRalan("pemeriksaan_ralan",noRawat);
    }

    /** Dipakai AutofillPemeriksaanRanap. Sengaja tidak public supaya tabel
     * sumber hanya pemeriksaan_ralan atau pemeriksaan_ranap. */
    static AutofillPemeriksaanRalan dariTabel(String tabel,String noRawat){
        return new AutofillPemeriksaanRalan(tabel,noRawat);
    }

    /** Isi field dengan nilai dari kolom tabel sumber.
     * @param namaKolom nama kolom di tabel sumber
     * @param target field yang diisi
     * @return builder yang sama, supaya bisa dirantai */
    public AutofillPemeriksaanRalan isi(String namaKolom,JTextComponent target){
        kolom.add(namaKolom);
        field.add(target);
        return this;
    }

    public void jalankan(){
        if(kolom.isEmpty()){
            return;
        }
        // Koneksi dipakai bersama seluruh aplikasi, jadi hanya statement dan
        // resultset yang ditutup.
        try(PreparedStatement ps=koneksiDB.condb().prepareStatement(
                "select "+String.join(",",kolom)+" from "+tabel+" where no_rawat=? "+
                "order by tgl_perawatan,jam_rawat limit 1")){
            ps.setString(1,noRawat);
            try(ResultSet rs=ps.executeQuery()){
                if(rs.next()){
                    for(int i=0;i<field.size();i++){
                        String nilai=rs.getString(i+1);
                        field.get(i).setText(nilai==null?"":nilai);
                    }
                }
            }
        }catch(Exception e){
            System.out.println("Notif Autofill "+tabel+" : "+e);
        }
    }
}
