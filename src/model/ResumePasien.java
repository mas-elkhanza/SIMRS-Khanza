/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import lombok.Data;

/**
 *
 * @author RSUI HA
 */
@Data
public class ResumePasien {
	private String no_rawat;
	private String kd_dokter;
	private String keluhan_utama;
	private String jalannya_penyakit;
	private String pemeriksaan_penunjang;
	private String hasil_laborat;
	private String diagnosa_utama;
	private String kd_diagnosa_utama;
	private String diagnosa_sekunder;
	private String kd_diagnosa_sekunder;
	private String diagnosa_sekunder2;
	private String kd_diagnosa_sekunder2;
	private String diagnosa_sekunder3;
	private String kd_diagnosa_sekunder3;
	private String diagnosa_sekunder4;
	private String kd_diagnosa_sekunder4;
	private String prosedur_utama;
	private String kd_prosedur_utama;
	private String prosedur_sekunder;
	private String kd_prosedur_sekunder;
	private String prosedur_sekunder2;
	private String kd_prosedur_sekunder2;
	private String prosedur_sekunder3;
	private String kd_prosedur_sekunder3;
	private String kondisi_pulang;
	private String riwayat_penyakit;
	private String tindakan_perawatan;
	private String pemeriksaan_fisik;
	private String alasan_pulang;

}
