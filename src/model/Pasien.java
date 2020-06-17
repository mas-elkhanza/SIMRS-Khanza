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
public class Pasien {

    private String no_rkm_medis;
    private String nm_pasien;
    private String no_ktp;
    private String tmp_lahir;
    private String jk;
    private Date tgl_lahir;
    private String nm_ibu;
    private String alamat;
    private String gol_darah;
    private String pekerjaan;
    private String stts_nikah;
    private String agama;
    private Date tgl_daftar;
    private String no_tlp;
    private String umur;
    private String pnd;
    private String keluarga;
    private String namakeluarga;
    private String kd_pj;
    private String no_peserta;
    private Wilayah kd_kel;
    private Wilayah kd_kec;
    private Wilayah kd_kab;
    private String pekerjaanpj;
    private String alamatpj;
    private String kelurahanpj;
    private String kecamatanpj;
    private String kabupatenpj;
    private PerusahaanPasien perusahaan_pasien;
    private SukuBangsa suku_bangsa;
    private BahasaPasien bahasa_pasien;
    private CacatFisik cacat_fisik;
    private String email;
    private String nip;
    private Wilayah kd_prop;
    private String propinsipj;
}
