/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
import lombok.Data;

/**
 *
 * @author Administrator
 */
@Data
public class KamarInap {
    private String no_rawat;
    private Kamar kd_kamar;
    private double trf_kamar;
    private String diagnosa_awal;
    private String diagnosa_akhir;
    private String asal_kiriman;
    private Date tgl_masuk;
    private String upf;
    private String jam_masuk;
    private Date tgl_keluar;
    private String jam_keluar;
    private double lama;
    private double ttl_biaya;
    private String stts_pulang;
    
    private int isi;
    private String nm_kamar;
}
