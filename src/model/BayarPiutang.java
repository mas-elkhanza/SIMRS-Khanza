package model;


import java.util.Date;

/**
 *
 * @author RSUI HA
 */
public class BayarPiutang {
    private int id_bayar_piutang;
    private Date tgl_bayar;
    private String no_rkm_medis;
    private double besar_cicilan;
    private String cacatan;
    private String no_rawat;
    private String kd_rek;

    /**
     *
     * @return
     */
    public int getId_bayar_piutang() {
        return id_bayar_piutang;
    }

    /**
     *
     * @param id_bayar_piutang
     */
    public void setId_bayar_piutang(int id_bayar_piutang) {
        this.id_bayar_piutang = id_bayar_piutang;
    }

    /**
     *
     * @return
     */
    public Date getTgl_bayar() {
        return tgl_bayar;
    }

    /**
     *
     * @param tgl_bayar
     */
    public void setTgl_bayar(Date tgl_bayar) {
        this.tgl_bayar = tgl_bayar;
    }

    /**
     *
     * @return
     */
    public String getNo_rkm_medis() {
        return no_rkm_medis;
    }

    /**
     *
     * @param no_rkm_medis
     */
    public void setNo_rkm_medis(String no_rkm_medis) {
        this.no_rkm_medis = no_rkm_medis;
    }

    /**
     *
     * @return
     */
    public double getBesar_cicilan() {
        return besar_cicilan;
    }

    /**
     *
     * @param besar_cicilan
     */
    public void setBesar_cicilan(double besar_cicilan) {
        this.besar_cicilan = besar_cicilan;
    }

    /**
     *
     * @return
     */
    public String getCacatan() {
        return cacatan;
    }

    /**
     *
     * @param cacatan
     */
    public void setCacatan(String cacatan) {
        this.cacatan = cacatan;
    }

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
    public String getKd_rek() {
        return kd_rek;
    }

    /**
     *
     * @param kd_rek
     */
    public void setKd_rek(String kd_rek) {
        this.kd_rek = kd_rek;
    }
    
}