/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RSUI HA
 */
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
    public String getTanggal() {
        return tanggal;
    }

    /**
     *
     * @param tanggal
     */
    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    /**
     *
     * @return
     */
    public String getJam() {
        return jam;
    }

    /**
     *
     * @param jam
     */
    public void setJam(String jam) {
        this.jam = jam;
    }

    /**
     *
     * @return
     */
    public String getNorm() {
        return norm;
    }

    /**
     *
     * @param norm
     */
    public void setNorm(String norm) {
        this.norm = norm;
    }

    /**
     *
     * @return
     */
    public String getDiagnosa() {
        return diagnosa;
    }

    /**
     *
     * @param diagnosa
     */
    public void setDiagnosa(String diagnosa) {
        this.diagnosa = diagnosa;
    }

    /**
     *
     * @return
     */
    public String getRencana_perawatan() {
        return rencana_perawatan;
    }

    /**
     *
     * @param rencana_perawatan
     */
    public void setRencana_perawatan(String rencana_perawatan) {
        this.rencana_perawatan = rencana_perawatan;
    }

    /**
     *
     * @return
     */
    public String getUpf() {
        return upf;
    }

    /**
     *
     * @param upf
     */
    public void setUpf(String upf) {
        this.upf = upf;
    }

    /**
     *
     * @return
     */
    public String getNama() {
        return nama;
    }

    /**
     *
     * @param nama
     */
    public void setNama(String nama) {
        this.nama = nama;
    }

    /**
     *
     * @return
     */
    public String getKeluhan() {
        return keluhan;
    }

    /**
     *
     * @param keluhan
     */
    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
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
    public Dokter getDokter() {
        return dokter;
    }

    /**
     *
     * @param dokter
     */
    public void setDokter(Dokter dokter) {
        this.dokter = dokter;
    }

    /**
     *
     * @return
     */
    public Pasien getPasien() {
        return pasien;
    }

    /**
     *
     * @param pasien
     */
    public void setPasien(Pasien pasien) {
        this.pasien = pasien;
    }

    /**
     *
     * @return
     */
    public List<Pasien> getPasiens() {
        return pasiens;
    }

    /**
     *
     * @param pasiens
     */
    public void setPasiens(List<Pasien> pasiens) {
        this.pasiens = pasiens;
    }

    /**
     *
     * @return
     */
    public List<Dokter> getDokters() {
        return dokters;
    }

    /**
     *
     * @param dokters
     */
    public void setDokters(List<Dokter> dokters) {
        this.dokters = dokters;
    }
    
}
