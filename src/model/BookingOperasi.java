package model;

import java.util.Date;


public class BookingOperasi {
private String no_rawat;
private String kode_paket;
private Date tanggal;
private String jam_mulai;
private String jam_selesai;
private String status;

    public String getNo_rawat() {
        return no_rawat;
    }

    public void setNo_rawat(String no_rawat) {
        this.no_rawat = no_rawat;
    }

    public String getKode_paket() {
        return kode_paket;
    }

    public void setKode_paket(String kode_paket) {
        this.kode_paket = kode_paket;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam_mulai() {
        return jam_mulai;
    }

    public void setJam_mulai(String jam_mulai) {
        this.jam_mulai = jam_mulai;
    }

    public String getJam_selesai() {
        return jam_selesai;
    }

    public void setJam_selesai(String jam_selesai) {
        this.jam_selesai = jam_selesai;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}