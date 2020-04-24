package model;


import java.util.Date;


public class BayarPemesanan {
    private int id;
    private Date tgl_bayar;
    private String no_faktur;
    private String nip;
    private double besar_bayar;
    private String keterangan;
    private String nama_bayar;
    private String no_bukti;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTgl_bayar() {
        return tgl_bayar;
    }

    public void setTgl_bayar(Date tgl_bayar) {
        this.tgl_bayar = tgl_bayar;
    }

    public String getNo_faktur() {
        return no_faktur;
    }

    public void setNo_faktur(String no_faktur) {
        this.no_faktur = no_faktur;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public double getBesar_bayar() {
        return besar_bayar;
    }

    public void setBesar_bayar(double besar_bayar) {
        this.besar_bayar = besar_bayar;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getNama_bayar() {
        return nama_bayar;
    }

    public void setNama_bayar(String nama_bayar) {
        this.nama_bayar = nama_bayar;
    }

    public String getNo_bukti() {
        return no_bukti;
    }

    public void setNo_bukti(String no_bukti) {
        this.no_bukti = no_bukti;
    }
    
}