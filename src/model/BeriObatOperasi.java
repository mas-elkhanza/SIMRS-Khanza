package model;


import org.joda.time.DateTime;

/**
 *
 * @author RSUI HA
 */
public class BeriObatOperasi {
private String no_rawat;
private DateTime tanggal;
private String kd_obat;
private double hargasatuan;
private double jumlah;

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
    public DateTime getTanggal() {
        return tanggal;
    }

    /**
     *
     * @param tanggal
     */
    public void setTanggal(DateTime tanggal) {
        this.tanggal = tanggal;
    }

    /**
     *
     * @return
     */
    public String getKd_obat() {
        return kd_obat;
    }

    /**
     *
     * @param kd_obat
     */
    public void setKd_obat(String kd_obat) {
        this.kd_obat = kd_obat;
    }

    /**
     *
     * @return
     */
    public double getHargasatuan() {
        return hargasatuan;
    }

    /**
     *
     * @param hargasatuan
     */
    public void setHargasatuan(double hargasatuan) {
        this.hargasatuan = hargasatuan;
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

}