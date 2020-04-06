package model;


import java.util.Date;


public class BeriBhpRadiologi {
private String no_rawat;
private Date tgl_periksa;
private String jam;
private String kode_brng;
private String kode_sat;
private double jumlah;
private double harga;
private double total;

    public String getNo_rawat() {
        return no_rawat;
    }

    public void setNo_rawat(String no_rawat) {
        this.no_rawat = no_rawat;
    }

    public Date getTgl_periksa() {
        return tgl_periksa;
    }

    public void setTgl_periksa(Date tgl_periksa) {
        this.tgl_periksa = tgl_periksa;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getKode_brng() {
        return kode_brng;
    }

    public void setKode_brng(String kode_brng) {
        this.kode_brng = kode_brng;
    }

    public String getKode_sat() {
        return kode_sat;
    }

    public void setKode_sat(String kode_sat) {
        this.kode_sat = kode_sat;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}