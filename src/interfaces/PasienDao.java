/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.BahasaPasien;
import model.CacatFisik;
import model.Dokter;
import model.Pasien;
import model.PerusahaanPasien;
import model.Spri;
import model.SukuBangsa;
import model.Wilayah;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class PasienDao {
    private final sekuel Sequel = new sekuel();
    private final java.sql.Connection connect = koneksiDB.condb();
    private validasi Valid = new validasi();
    PreparedStatement ps;
    ResultSet rs;
    Pasien p = new Pasien();
    Wilayah d = new Wilayah();
    private PerusahaanPasien perusahaan_pasien;
    private SukuBangsa suku_bangsa;
    private BahasaPasien bahasa_pasien;
    private CacatFisik cacat_fisik;
    List<Spri> spris = new ArrayList<>();
    List<Pasien> pasiens = new ArrayList<>();
    List<Dokter> dokters = new ArrayList<>();

}
