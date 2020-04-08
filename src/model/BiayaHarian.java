package model;


public class BiayaHarian {

    private int id_biaya_harian;
    private String kd_kamar;
    private String nama_biaya;
    private double besar_biaya;
    private int jml;

    public int getId_biaya_harian() {
        return id_biaya_harian;
    }

    public void setId_biaya_harian(int id_biaya_harian) {
        this.id_biaya_harian = id_biaya_harian;
    }

    public String getKd_kamar() {
        return kd_kamar;
    }

    public void setKd_kamar(String kd_kamar) {
        this.kd_kamar = kd_kamar;
    }

    public String getNama_biaya() {
        return nama_biaya;
    }

    public void setNama_biaya(String nama_biaya) {
        this.nama_biaya = nama_biaya;
    }

    public double getBesar_biaya() {
        return besar_biaya;
    }

    public void setBesar_biaya(double besar_biaya) {
        this.besar_biaya = besar_biaya;
    }

    public int getJml() {
        return jml;
    }

    public void setJml(int jml) {
        this.jml = jml;
    }
    
}
