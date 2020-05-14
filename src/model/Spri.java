/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class Spri {
    private int id;
    private String tanggal;
    private String jam;
    private String norm;
    private String diagnosa;
    private String rencana_perawatan;
    private String upf;
    private String nama;
    private String keluhan;
    private String status;
    
    private Pasien pasien;
    private Dokter dokter;
    private List<Pasien>pasiens = new ArrayList<>();
    private List<Dokter>dokters = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
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

    public String getUpf() {
        return upf;
    }

    public void setUpf(String upf) {
        this.upf = upf;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Dokter getDokter() {
        return dokter;
    }

    public void setDokter(Dokter dokter) {
        this.dokter = dokter;
    }

    public Pasien getPasien() {
        return pasien;
    }

    public void setPasien(Pasien pasien) {
        this.pasien = pasien;
    }

    public List<Pasien> getPasiens() {
        return pasiens;
    }

    public void setPasiens(List<Pasien> pasiens) {
        this.pasiens = pasiens;
    }

    public List<Dokter> getDokters() {
        return dokters;
    }

    public void setDokters(List<Dokter> dokters) {
        this.dokters = dokters;
    }
    
}
