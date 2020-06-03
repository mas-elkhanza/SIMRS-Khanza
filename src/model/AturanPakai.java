package model;

import java.util.Date;

/**
 *
 * @author RSUI HA
 */
public class AturanPakai {

    private Date tgl_perawatan;
    private String jam;
    private String no_rawat;
    private String kode_brng;
    private String aturan;

    /**
     *
     * @return
     */
    public Date getTgl_perawatan() {
        return tgl_perawatan;
    }

    /**
     *
     * @param tgl_perawatan
     */
    public void setTgl_perawatan(Date tgl_perawatan) {
        this.tgl_perawatan = tgl_perawatan;
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
    public String getAturan() {
        return aturan;
    }

    /**
     *
     * @param aturan
     */
    public void setAturan(String aturan) {
        this.aturan = aturan;
    }
    
}