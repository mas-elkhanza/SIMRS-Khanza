package model;

import java.util.Date;

public class AngsuranKoperasi {
private int id_angsuran;//awalnya id
private Date tanggal_pinjam;
private Date tanggal_angsur;
private double pokok;
private double jasa; 

    public int getId_angsuran() {
        return id_angsuran;
    }

    public void setId_angsuran(int id_angsuran) {
        this.id_angsuran = id_angsuran;
    }

    public Date getTanggal_pinjam() {
        return tanggal_pinjam;
    }

    public void setTanggal_pinjam(Date tanggal_pinjam) {
        this.tanggal_pinjam = tanggal_pinjam;
    }

    public Date getTanggal_angsur() {
        return tanggal_angsur;
    }

    public void setTanggal_angsur(Date tanggal_angsur) {
        this.tanggal_angsur = tanggal_angsur;
    }

    public double getPokok() {
        return pokok;
    }

    public void setPokok(double pokok) {
        this.pokok = pokok;
    }

    public double getJasa() {
        return jasa;
    }

    public void setJasa(double jasa) {
        this.jasa = jasa;
    }

}