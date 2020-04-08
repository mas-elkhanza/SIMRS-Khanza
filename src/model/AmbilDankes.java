package model;

import java.util.Date;

public class AmbilDankes {
private int id;
private Date tanggal;
private String ktg;
private double dankes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getKtg() {
        return ktg;
    }

    public void setKtg(String ktg) {
        this.ktg = ktg;
    }

    public double getDankes() {
        return dankes;
    }

    public void setDankes(double dankes) {
        this.dankes = dankes;
    }

}