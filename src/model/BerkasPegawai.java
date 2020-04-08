package model;


import java.util.Date;


public class BerkasPegawai {
private String nik;
private Date tgl_upload;
private String kode_berkas;
private String berkas;

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public Date getTgl_upload() {
        return tgl_upload;
    }

    public void setTgl_upload(Date tgl_upload) {
        this.tgl_upload = tgl_upload;
    }

    public String getKode_berkas() {
        return kode_berkas;
    }

    public void setKode_berkas(String kode_berkas) {
        this.kode_berkas = kode_berkas;
    }

    public String getBerkas() {
        return berkas;
    }

    public void setBerkas(String berkas) {
        this.berkas = berkas;
    }

}