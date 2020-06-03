package model;

import java.util.Date;

/**
 *
 * @author RSUI HA
 */
public class AmbilDankes {
private int id;
private Date tanggal;
private String ktg;
private double dankes;

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Date getTanggal() {
        return tanggal;
    }

    /**
     *
     * @param tanggal
     */
    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    /**
     *
     * @return
     */
    public String getKtg() {
        return ktg;
    }

    /**
     *
     * @param ktg
     */
    public void setKtg(String ktg) {
        this.ktg = ktg;
    }

    /**
     *
     * @return
     */
    public double getDankes() {
        return dankes;
    }

    /**
     *
     * @param dankes
     */
    public void setDankes(double dankes) {
        this.dankes = dankes;
    }

}