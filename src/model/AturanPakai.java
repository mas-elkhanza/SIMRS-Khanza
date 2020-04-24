package model;

import java.util.Date;

public class AturanPakai {

    private Date tgl_perawatan;
    private String jam;
    private String no_rawat;
    private String kode_brng;
    private String aturan;

    public Date getTgl_perawatan() {
        return tgl_perawatan;
    }

    public void setTgl_perawatan(Date tgl_perawatan) {
        this.tgl_perawatan = tgl_perawatan;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getNo_rawat() {
        return no_rawat;
    }

    public void setNo_rawat(String no_rawat) {
        this.no_rawat = no_rawat;
    }

    public String getKode_brng() {
        return kode_brng;
    }

    public void setKode_brng(String kode_brng) {
        this.kode_brng = kode_brng;
    }

    public String getAturan() {
        return aturan;
    }

    public void setAturan(String aturan) {
        this.aturan = aturan;
    }
    
}