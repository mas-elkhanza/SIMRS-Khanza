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
    public Kamar getKd_kamar() {
        return kd_kamar;
    }

    /**
     *
     * @param kd_kamar
     */
    public void setKd_kamar(Kamar kd_kamar) {
        this.kd_kamar = kd_kamar;
    }

    /**
     *
     * @return
     */
    public double getTrf_kamar() {
        return trf_kamar;
    }

    /**
     *
     * @param trf_kamar
     */
    public void setTrf_kamar(double trf_kamar) {
        this.trf_kamar = trf_kamar;
    }

    /**
     *
     * @return
     */
    public String getDiagnosa_awal() {
        return diagnosa_awal;
    }

    /**
     *
     * @param diagnosa_awal
     */
    public void setDiagnosa_awal(String diagnosa_awal) {
        this.diagnosa_awal = diagnosa_awal;
    }

    /**
     *
     * @return
     */
    public String getDiagnosa_akhir() {
        return diagnosa_akhir;
    }

    /**
     *
     * @param diagnosa_akhir
     */
    public void setDiagnosa_akhir(String diagnosa_akhir) {
        this.diagnosa_akhir = diagnosa_akhir;
    }

    /**
     *
     * @return
     */
    public String getAsal_kiriman() {
        return asal_kiriman;
    }

    /**
     *
     * @param asal_kiriman
     */
    public void setAsal_kiriman(String asal_kiriman) {
        this.asal_kiriman = asal_kiriman;
    }

    /**
     *
     * @return
     */
    public Date getTgl_masuk() {
        return tgl_masuk;
    }

    /**
     *
     * @param tgl_masuk
     */
    public void setTgl_masuk(Date tgl_masuk) {
        this.tgl_masuk = tgl_masuk;
    }

    /**
     *
     * @return
     */
    public String getUpf() {
        return upf;
    }

    /**
     *
     * @param upf
     */
    public void setUpf(String upf) {
        this.upf = upf;
    }

    /**
     *
     * @return
     */
    public String getJam_masuk() {
        return jam_masuk;
    }

    /**
     *
     * @param jam_masuk
     */
    public void setJam_masuk(String jam_masuk) {
        this.jam_masuk = jam_masuk;
    }

    /**
     *
     * @return
     */
    public Date getTgl_keluar() {
        return tgl_keluar;
    }

    /**
     *
     * @param tgl_keluar
     */
    public void setTgl_keluar(Date tgl_keluar) {
        this.tgl_keluar = tgl_keluar;
    }

    /**
     *
     * @return
     */
    public String getJam_keluar() {
        return jam_keluar;
    }

    /**
     *
     * @param jam_keluar
     */
    public void setJam_keluar(String jam_keluar) {
        this.jam_keluar = jam_keluar;
    }

    /**
     *
     * @return
     */
    public double getLama() {
        return lama;
    }

    /**
     *
     * @param lama
     */
    public void setLama(double lama) {
        this.lama = lama;
    }

    /**
     *
     * @return
     */
    public double getTtl_biaya() {
        return ttl_biaya;
    }

    /**
     *
     * @param ttl_biaya
     */
    public void setTtl_biaya(double ttl_biaya) {
        this.ttl_biaya = ttl_biaya;
    }

    /**
     *
     * @return
     */
    public String getStts_pulang() {
        return stts_pulang;
    }

    /**
     *
     * @param stts_pulang
     */
    public void setStts_pulang(String stts_pulang) {
        this.stts_pulang = stts_pulang;
    }

    /**
     *
     * @return
     */
    public int getIsi() {
        return isi;
    }

    /**
     *
     * @param isi
     */
    public void setIsi(int isi) {
        this.isi = isi;
    }

    /**
     *
     * @return
     */
    public String getNm_kamar() {
        return nm_kamar;
    }

    /**
     *
     * @param nm_kamar
     */
    public void setNm_kamar(String nm_kamar) {
        this.nm_kamar = nm_kamar;
    }
    
}
