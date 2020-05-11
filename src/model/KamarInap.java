/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author Administrator
 */
public class KamarInap {
    private String no_rawat;
    private Kamar kd_kamar;
    private double trf_kamar;
    private String diagnosa_awal;
    private String diagnosa_akhir;
    private String asal_kiriman;
    private Date tgl_masuk;
    private String upf;
    private String jam_masuk;
    private Date tgl_keluar;
    private String jam_keluar;
    private double lama;
    private double ttl_biaya;
    private String stts_pulang;
    
    private int isi;
    private String nm_kamar;

    public String getNo_rawat() {
        return no_rawat;
    }

    public void setNo_rawat(String no_rawat) {
        this.no_rawat = no_rawat;
    }

    public Kamar getKd_kamar() {
        return kd_kamar;
    }

    public void setKd_kamar(Kamar kd_kamar) {
        this.kd_kamar = kd_kamar;
    }

    public double getTrf_kamar() {
        return trf_kamar;
    }

    public void setTrf_kamar(double trf_kamar) {
        this.trf_kamar = trf_kamar;
    }

    public String getDiagnosa_awal() {
        return diagnosa_awal;
    }

    public void setDiagnosa_awal(String diagnosa_awal) {
        this.diagnosa_awal = diagnosa_awal;
    }

    public String getDiagnosa_akhir() {
        return diagnosa_akhir;
    }

    public void setDiagnosa_akhir(String diagnosa_akhir) {
        this.diagnosa_akhir = diagnosa_akhir;
    }

    public String getAsal_kiriman() {
        return asal_kiriman;
    }

    public void setAsal_kiriman(String asal_kiriman) {
        this.asal_kiriman = asal_kiriman;
    }

    public Date getTgl_masuk() {
        return tgl_masuk;
    }

    public void setTgl_masuk(Date tgl_masuk) {
        this.tgl_masuk = tgl_masuk;
    }

    public String getUpf() {
        return upf;
    }

    public void setUpf(String upf) {
        this.upf = upf;
    }

    public String getJam_masuk() {
        return jam_masuk;
    }

    public void setJam_masuk(String jam_masuk) {
        this.jam_masuk = jam_masuk;
    }

    public Date getTgl_keluar() {
        return tgl_keluar;
    }

    public void setTgl_keluar(Date tgl_keluar) {
        this.tgl_keluar = tgl_keluar;
    }

    public String getJam_keluar() {
        return jam_keluar;
    }

    public void setJam_keluar(String jam_keluar) {
        this.jam_keluar = jam_keluar;
    }

    public double getLama() {
        return lama;
    }

    public void setLama(double lama) {
        this.lama = lama;
    }

    public double getTtl_biaya() {
        return ttl_biaya;
    }

    public void setTtl_biaya(double ttl_biaya) {
        this.ttl_biaya = ttl_biaya;
    }

    public String getStts_pulang() {
        return stts_pulang;
    }

    public void setStts_pulang(String stts_pulang) {
        this.stts_pulang = stts_pulang;
    }

    public int getIsi() {
        return isi;
    }

    public void setIsi(int isi) {
        this.isi = isi;
    }

    public String getNm_kamar() {
        return nm_kamar;
    }

    public void setNm_kamar(String nm_kamar) {
        this.nm_kamar = nm_kamar;
    }
    
}
