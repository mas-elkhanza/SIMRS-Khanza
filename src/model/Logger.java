/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;

/**
 *
 * @author RSUI HA
 */
public class Logger {
    private int id_logger;
    private Date tanggal;
    private String pesan;
    private String level;

    public int getId_logger() {
        return id_logger;
    }

    public void setId_logger(int id_logger) {
        this.id_logger = id_logger;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    
}
