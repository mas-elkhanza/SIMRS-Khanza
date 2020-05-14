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

    public String getKd_kamar() {
        return kd_kamar;
    }

    public void setKd_kamar(String kd_kamar) {
        this.kd_kamar = kd_kamar;
    }

    public Bangsal getKd_bangsal() {
        return kd_bangsal;
    }

    public void setKd_bangsal(Bangsal kd_bangsal) {
        this.kd_bangsal = kd_bangsal;
    }

    public double getTrf_kamar() {
        return trf_kamar;
    }

    public void setTrf_kamar(double trf_kamar) {
        this.trf_kamar = trf_kamar;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getStatusdata() {
        return statusdata;
    }

    public void setStatusdata(String statusdata) {
        this.statusdata = statusdata;
    }
    
}
