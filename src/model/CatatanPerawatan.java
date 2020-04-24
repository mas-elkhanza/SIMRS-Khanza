package model;

import java.util.Date;
import org.joda.time.DateTime;


public class CatatanPerawatan {

    private Date tanggal;
    private DateTime jam;
    private String no_rawat;
    private String kd_dokter;
    private String catatan;

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public DateTime getJam() {
        return jam;
    }

    public void setJam(DateTime jam) {
        this.jam = jam;
    }

    public String getNo_rawat() {
        return no_rawat;
    }

    public void setNo_rawat(String no_rawat) {
        this.no_rawat = no_rawat;
    }

    public String getKd_dokter() {
        return kd_dokter;
    }

    public void setKd_dokter(String kd_dokter) {
        this.kd_dokter = kd_dokter;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
    
}
