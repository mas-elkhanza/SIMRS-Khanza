package fungsi;

/**
 * Mengisi field form dari data pemeriksaan_ranap pada no_rawat yang sama.
 * Cara pakai dan perilakunya sama dengan AutofillPemeriksaanRalan, hanya tabel
 * sumbernya yang berbeda.
 *
 * <pre>
 * AutofillPemeriksaanRanap.dari(TNoRw.getText())
 *         .isi("keluhan",KeluhanUtama)
 *         .isi("tensi",TD)
 *         .jalankan();
 * </pre>
 */
public final class AutofillPemeriksaanRanap {
    private AutofillPemeriksaanRanap(){}

    /** @param noRawat no_rawat yang datanya dicari di pemeriksaan_ranap
     * @return builder yang sama dengan AutofillPemeriksaanRalan */
    public static AutofillPemeriksaanRalan dari(String noRawat){
        return AutofillPemeriksaanRalan.dariTabel("pemeriksaan_ranap",noRawat);
    }
}
