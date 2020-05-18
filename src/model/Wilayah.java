/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Administrator
 */
public class Wilayah {
    private String kode_wilayah;
    private String mst_kode_wilayah;
    private String nama;
    private int level;

    public String getKode_wilayah() {
        return kode_wilayah;
    }

    public void setKode_wilayah(String kode_wilayah) {
        this.kode_wilayah = kode_wilayah;
    }

    public String getMst_kode_wilayah() {
        return mst_kode_wilayah;
    }

    public void setMst_kode_wilayah(String mst_kode_wilayah) {
        this.mst_kode_wilayah = mst_kode_wilayah;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
