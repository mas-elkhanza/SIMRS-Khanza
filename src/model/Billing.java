package model;

import java.util.Date;

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

    public int getNoindex() {
        return noindex;
    }

    public void setNoindex(int noindex) {
        this.noindex = noindex;
    }

    public String getNo_rawat() {
        return no_rawat;
    }

    public void setNo_rawat(String no_rawat) {
        this.no_rawat = no_rawat;
    }

    public Date getTgl_byr() {
        return tgl_byr;
    }

    public void setTgl_byr(Date tgl_byr) {
        this.tgl_byr = tgl_byr;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNm_perawatan() {
        return nm_perawatan;
    }

    public void setNm_perawatan(String nm_perawatan) {
        this.nm_perawatan = nm_perawatan;
    }

    public String getPemisah() {
        return pemisah;
    }

    public void setPemisah(String pemisah) {
        this.pemisah = pemisah;
    }

    public double getBiaya() {
        return biaya;
    }

    public void setBiaya(double biaya) {
        this.biaya = biaya;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public double getTambahan() {
        return tambahan;
    }

    public void setTambahan(double tambahan) {
        this.tambahan = tambahan;
    }

    public double getTotalbiaya() {
        return totalbiaya;
    }

    public void setTotalbiaya(double totalbiaya) {
        this.totalbiaya = totalbiaya;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
