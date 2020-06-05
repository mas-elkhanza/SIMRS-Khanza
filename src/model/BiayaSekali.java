package model;

/**
 *
 * @author RSUI HA
 */
public class BiayaSekali {
private String kode_kamar;
private String nama_biaya;
private double besar_biaya;

    /**
     *
     * @return
     */
    public String getKode_kamar() {
        return kode_kamar;
    }

    /**
     *
     * @param kode_kamar
     */
    public void setKode_kamar(String kode_kamar) {
        this.kode_kamar = kode_kamar;
    }

    /**
     *
     * @return
     */
    public String getNama_biaya() {
        return nama_biaya;
    }

    /**
     *
     * @param nama_biaya
     */
    public void setNama_biaya(String nama_biaya) {
        this.nama_biaya = nama_biaya;
    }

    /**
     *
     * @return
     */
    public double getBesar_biaya() {
        return besar_biaya;
    }

    /**
     *
     * @param besar_biaya
     */
    public void setBesar_biaya(double besar_biaya) {
        this.besar_biaya = besar_biaya;
    }

}