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
public class Spri {
    private Date tanggal;
    private String jam;
    private String norm;
    private String diagnosa;
    private String rencana_perawatan;
    private String kd_kamar;
    private String kd_dokter;

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getNorm() {
        return norm;
    }

    public void setNorm(String norm) {
        this.norm = norm;
    }

    public String getDiagnosa() {
        return diagnosa;
    }

    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    public String getRencana_perawatan() {
        return rencana_perawatan;
    }

    public void setRencana_perawatan(String rencana_perawatan) {
        this.rencana_perawatan = rencana_perawatan;
    }

    public String getKd_kamar() {
        return kd_kamar;
    }

    public void setKd_kamar(String kd_kamar) {
        this.kd_kamar = kd_kamar;
    }

    public String getKd_dokter() {
        return kd_dokter;
    }

    public void setKd_dokter(String kd_dokter) {
        this.kd_dokter = kd_dokter;
    }
    
    
}
