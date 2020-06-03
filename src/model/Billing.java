package model;

import java.util.Date;

/**
 *
 * @author RSUI HA
 */
public class Billing {

    private int noindex;
    private String no_rawat;
    private Date tgl_byr;
    private String no;
    private String nm_perawatan;
    private String pemisah;
    private double biaya;
    private double jumlah;
    private double tambahan;
    private double totalbiaya;
    private String status;

    /**
     *
     * @return
     */
    public int getNoindex() {
        return noindex;
    }

    /**
     *
     * @param noindex
     */
    public void setNoindex(int noindex) {
        this.noindex = noindex;
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
    public Date getTgl_byr() {
        return tgl_byr;
    }

    /**
     *
     * @param tgl_byr
     */
    public void setTgl_byr(Date tgl_byr) {
        this.tgl_byr = tgl_byr;
    }

    /**
     *
     * @return
     */
    public String getNo() {
        return no;
    }

    /**
     *
     * @param no
     */
    public void setNo(String no) {
        this.no = no;
    }

    /**
     *
     * @return
     */
    public String getNm_perawatan() {
        return nm_perawatan;
    }

    /**
     *
     * @param nm_perawatan
     */
    public void setNm_perawatan(String nm_perawatan) {
        this.nm_perawatan = nm_perawatan;
    }

    /**
     *
     * @return
     */
    public String getPemisah() {
        return pemisah;
    }

    /**
     *
     * @param pemisah
     */
    public void setPemisah(String pemisah) {
        this.pemisah = pemisah;
    }

    /**
     *
     * @return
     */
    public double getBiaya() {
        return biaya;
    }

    /**
     *
     * @param biaya
     */
    public void setBiaya(double biaya) {
        this.biaya = biaya;
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
    public double getTambahan() {
        return tambahan;
    }

    /**
     *
     * @param tambahan
     */
    public void setTambahan(double tambahan) {
        this.tambahan = tambahan;
    }

    /**
     *
     * @return
     */
    public double getTotalbiaya() {
        return totalbiaya;
    }

    /**
     *
     * @param totalbiaya
     */
    public void setTotalbiaya(double totalbiaya) {
        this.totalbiaya = totalbiaya;
    }

    /**
     *
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
