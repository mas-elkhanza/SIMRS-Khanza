/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author RSUI HA
 */
@Data
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
}
