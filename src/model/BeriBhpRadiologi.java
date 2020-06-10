package model;


import java.util.Date;

/**
 *
 * @author RSUI HA
 */
public class BeriBhpRadiologi {
private String no_rawat;
private Date tgl_periksa;
private String jam;
private String kode_brng;
private String kode_sat;
private double jumlah;
private double harga;
private double total;

    /**
     *
     * @return
     */
    public String getNo_rawat() {
        return no_rawat;
    }

    /**
     *
     * @param no_rawat
     */
    public void setNo_rawat(String no_rawat) {
        this.no_rawat = no_rawat;
    }

    /**
     *
     * @return
     */
    public Date getTgl_periksa() {
        return tgl_periksa;
    }

    /**
     *
     * @param tgl_periksa
     */
    public void setTgl_periksa(Date tgl_periksa) {
        this.tgl_periksa = tgl_periksa;
    }

    /**
     *
     * @return
     */
    public String getJam() {
        return jam;
    }

    /**
     *
     * @param jam
     */
    public void setJam(String jam) {
        this.jam = jam;
    }

    /**
     *
     * @return
     */
    public String getKode_brng() {
        return kode_brng;
    }

    /**
     *
     * @param kode_brng
     */
    public void setKode_brng(String kode_brng) {
        this.kode_brng = kode_brng;
    }

    /**
     *
     * @return
     */
    public String getKode_sat() {
        return kode_sat;
    }

    /**
     *
     * @param kode_sat
     */
    public void setKode_sat(String kode_sat) {
        this.kode_sat = kode_sat;
    }

    /**
     *
     * @return
     */
    public double getJumlah() {
        return jumlah;
    }

    /**
     *
     * @param jumlah
     */
    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    /**
     *
     * @return
     */
    public double getHarga() {
        return harga;
    }

    /**
     *
     * @param harga
     */
    public void setHarga(double harga) {
        this.harga = harga;
    }

    /**
     *
     * @return
     */
    public double getTotal() {
        return total;
    }

    /**
     *
     * @param total
     */
    public void setTotal(double total) {
        this.total = total;
    }

}