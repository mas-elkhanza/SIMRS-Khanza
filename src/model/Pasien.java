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
public class Pasien {
    private String no_rkm_medis;
    private String nm_pasien;
    private String no_ktp;
    private String tmp_lahir;
    private String jk;
    private java.util.Date tgl_lahir;
    private String nm_ibu;
    private String alamat;
    private String gol_darah;
    private String pekerjaan;
    private String stts_nikah;
    private String agama;
    private java.util.Date tgl_daftar;
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

    public String getNo_rkm_medis() {
        return no_rkm_medis;
    }

    public void setNo_rkm_medis(String no_rkm_medis) {
        this.no_rkm_medis = no_rkm_medis;
    }

    public String getNm_pasien() {
        return nm_pasien;
    }

    public void setNm_pasien(String nm_pasien) {
        this.nm_pasien = nm_pasien;
    }

    public String getNo_ktp() {
        return no_ktp;
    }

    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    public String getTmp_lahir() {
        return tmp_lahir;
    }

    public void setTmp_lahir(String tmp_lahir) {
        this.tmp_lahir = tmp_lahir;
    }

    public String getJk() {
        return jk;
    }

    public void setJk(String jk) {
        this.jk = jk;
    }

    public Date getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(Date tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getNm_ibu() {
        return nm_ibu;
    }

    public void setNm_ibu(String nm_ibu) {
        this.nm_ibu = nm_ibu;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getGol_darah() {
        return gol_darah;
    }

    public void setGol_darah(String gol_darah) {
        this.gol_darah = gol_darah;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getStts_nikah() {
        return stts_nikah;
    }

    public void setStts_nikah(String stts_nikah) {
        this.stts_nikah = stts_nikah;
    }

    public String getAgama() {
        return agama;
    }

    public void setAgama(String agama) {
        this.agama = agama;
    }

    public Date getTgl_daftar() {
        return tgl_daftar;
    }

    public void setTgl_daftar(Date tgl_daftar) {
        this.tgl_daftar = tgl_daftar;
    }

    public String getNo_tlp() {
        return no_tlp;
    }

    public void setNo_tlp(String no_tlp) {
        this.no_tlp = no_tlp;
    }

    public String getUmur() {
        return umur;
    }

    public void setUmur(String umur) {
        this.umur = umur;
    }

    public String getPnd() {
        return pnd;
    }

    public void setPnd(String pnd) {
        this.pnd = pnd;
    }

    public String getKeluarga() {
        return keluarga;
    }

    public void setKeluarga(String keluarga) {
        this.keluarga = keluarga;
    }

    public String getNamakeluarga() {
        return namakeluarga;
    }

    public void setNamakeluarga(String namakeluarga) {
        this.namakeluarga = namakeluarga;
    }

    public String getKd_pj() {
        return kd_pj;
    }

    public void setKd_pj(String kd_pj) {
        this.kd_pj = kd_pj;
    }

    public String getNo_peserta() {
        return no_peserta;
    }

    public void setNo_peserta(String no_peserta) {
        this.no_peserta = no_peserta;
    }

    public Wilayah getKd_kel() {
        return kd_kel;
    }

    public void setKd_kel(Wilayah kd_kel) {
        this.kd_kel = kd_kel;
    }

    public Wilayah getKd_kec() {
        return kd_kec;
    }

    public void setKd_kec(Wilayah kd_kec) {
        this.kd_kec = kd_kec;
    }

    public Wilayah getKd_kab() {
        return kd_kab;
    }

    public void setKd_kab(Wilayah kd_kab) {
        this.kd_kab = kd_kab;
    }

    public String getPekerjaanpj() {
        return pekerjaanpj;
    }

    public void setPekerjaanpj(String pekerjaanpj) {
        this.pekerjaanpj = pekerjaanpj;
    }

    public String getAlamatpj() {
        return alamatpj;
    }

    public void setAlamatpj(String alamatpj) {
        this.alamatpj = alamatpj;
    }

    public String getKelurahanpj() {
        return kelurahanpj;
    }

    public void setKelurahanpj(String kelurahanpj) {
        this.kelurahanpj = kelurahanpj;
    }

    public String getKecamatanpj() {
        return kecamatanpj;
    }

    public void setKecamatanpj(String kecamatanpj) {
        this.kecamatanpj = kecamatanpj;
    }

    public String getKabupatenpj() {
        return kabupatenpj;
    }

    public void setKabupatenpj(String kabupatenpj) {
        this.kabupatenpj = kabupatenpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getPropinsipj() {
        return propinsipj;
    }

    public void setPropinsipj(String propinsipj) {
        this.propinsipj = propinsipj;
    }

    public PerusahaanPasien getPerusahaan_pasien() {
        return perusahaan_pasien;
    }

    public void setPerusahaan_pasien(PerusahaanPasien perusahaan_pasien) {
        this.perusahaan_pasien = perusahaan_pasien;
    }

    public SukuBangsa getSuku_bangsa() {
        return suku_bangsa;
    }

    public void setSuku_bangsa(SukuBangsa suku_bangsa) {
        this.suku_bangsa = suku_bangsa;
    }

    public BahasaPasien getBahasa_pasien() {
        return bahasa_pasien;
    }

    public void setBahasa_pasien(BahasaPasien bahasa_pasien) {
        this.bahasa_pasien = bahasa_pasien;
    }

    public CacatFisik getCacat_fisik() {
        return cacat_fisik;
    }

    public void setCacat_fisik(CacatFisik cacat_fisik) {
        this.cacat_fisik = cacat_fisik;
    }

    public Wilayah getKd_prop() {
        return kd_prop;
    }

    public void setKd_prop(Wilayah kd_prop) {
        this.kd_prop = kd_prop;
    }
}
