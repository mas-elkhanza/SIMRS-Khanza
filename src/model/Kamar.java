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
public class Kamar {
    private String kd_kamar;
    private Bangsal kd_bangsal;
    private double trf_kamar;
    private String status;
    private String kelas;
    private String statusdata;

    /**
     *
     * @return
     */
    public String getKd_kamar() {
        return kd_kamar;
    }

    /**
     *
     * @param kd_kamar
     */
    public void setKd_kamar(String kd_kamar) {
        this.kd_kamar = kd_kamar;
    }

    /**
     *
     * @return
     */
    public Bangsal getKd_bangsal() {
        return kd_bangsal;
    }

    /**
     *
     * @param kd_bangsal
     */
    public void setKd_bangsal(Bangsal kd_bangsal) {
        this.kd_bangsal = kd_bangsal;
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
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return
     */
    public String getKelas() {
        return kelas;
    }

    /**
     *
     * @param kelas
     */
    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    /**
     *
     * @return
     */
    public String getStatusdata() {
        return statusdata;
    }

    /**
     *
     * @param statusdata
     */
    public void setStatusdata(String statusdata) {
        this.statusdata = statusdata;
    }
    
}
