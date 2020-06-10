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

    /**
     *
     * @return
     */
    public String getNo_rkm_medis() {
        return no_rkm_medis;
    }

    /**
     *
     * @param no_rkm_medis
     */
    public void setNo_rkm_medis(String no_rkm_medis) {
        this.no_rkm_medis = no_rkm_medis;
    }

    /**
     *
     * @return
     */
    public String getNm_pasien() {
        return nm_pasien;
    }

    /**
     *
     * @param nm_pasien
     */
    public void setNm_pasien(String nm_pasien) {
        this.nm_pasien = nm_pasien;
    }

    /**
     *
     * @return
     */
    public String getNo_ktp() {
        return no_ktp;
    }

    /**
     *
     * @param no_ktp
     */
    public void setNo_ktp(String no_ktp) {
        this.no_ktp = no_ktp;
    }

    /**
     *
     * @return
     */
    public String getTmp_lahir() {
        return tmp_lahir;
    }

    /**
     *
     * @param tmp_lahir
     */
    public void setTmp_lahir(String tmp_lahir) {
        this.tmp_lahir = tmp_lahir;
    }

    /**
     *
     * @return
     */
    public String getJk() {
        return jk;
    }

    /**
     *
     * @param jk
     */
    public void setJk(String jk) {
        this.jk = jk;
    }

    /**
     *
     * @return
     */
    public Date getTgl_lahir() {
        return tgl_lahir;
    }

    /**
     *
     * @param tgl_lahir
     */
    public void setTgl_lahir(Date tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    /**
     *
     * @return
     */
    public String getNm_ibu() {
        return nm_ibu;
    }

    /**
     *
     * @param nm_ibu
     */
    public void setNm_ibu(String nm_ibu) {
        this.nm_ibu = nm_ibu;
    }

    /**
     *
     * @return
     */
    public String getAlamat() {
        return alamat;
    }

    /**
     *
     * @param alamat
     */
    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    /**
     *
     * @return
     */
    public String getGol_darah() {
        return gol_darah;
    }

    /**
     *
     * @param gol_darah
     */
    public void setGol_darah(String gol_darah) {
        this.gol_darah = gol_darah;
    }

    /**
     *
     * @return
     */
    public String getPekerjaan() {
        return pekerjaan;
    }

    /**
     *
     * @param pekerjaan
     */
    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    /**
     *
     * @return
     */
    public String getStts_nikah() {
        return stts_nikah;
    }

    /**
     *
     * @param stts_nikah
     */
    public void setStts_nikah(String stts_nikah) {
        this.stts_nikah = stts_nikah;
    }

    /**
     *
     * @return
     */
    public String getAgama() {
        return agama;
    }

    /**
     *
     * @param agama
     */
    public void setAgama(String agama) {
        this.agama = agama;
    }

    /**
     *
     * @return
     */
    public Date getTgl_daftar() {
        return tgl_daftar;
    }

    /**
     *
     * @param tgl_daftar
     */
    public void setTgl_daftar(Date tgl_daftar) {
        this.tgl_daftar = tgl_daftar;
    }

    /**
     *
     * @return
     */
    public String getNo_tlp() {
        return no_tlp;
    }

    /**
     *
     * @param no_tlp
     */
    public void setNo_tlp(String no_tlp) {
        this.no_tlp = no_tlp;
    }

    /**
     *
     * @return
     */
    public String getUmur() {
        return umur;
    }

    /**
     *
     * @param umur
     */
    public void setUmur(String umur) {
        this.umur = umur;
    }

    /**
     *
     * @return
     */
    public String getPnd() {
        return pnd;
    }

    /**
     *
     * @param pnd
     */
    public void setPnd(String pnd) {
        this.pnd = pnd;
    }

    /**
     *
     * @return
     */
    public String getKeluarga() {
        return keluarga;
    }

    /**
     *
     * @param keluarga
     */
    public void setKeluarga(String keluarga) {
        this.keluarga = keluarga;
    }

    /**
     *
     * @return
     */
    public String getNamakeluarga() {
        return namakeluarga;
    }

    /**
     *
     * @param namakeluarga
     */
    public void setNamakeluarga(String namakeluarga) {
        this.namakeluarga = namakeluarga;
    }

    /**
     *
     * @return
     */
    public String getKd_pj() {
        return kd_pj;
    }

    /**
     *
     * @param kd_pj
     */
    public void setKd_pj(String kd_pj) {
        this.kd_pj = kd_pj;
    }

    /**
     *
     * @return
     */
    public String getNo_peserta() {
        return no_peserta;
    }

    /**
     *
     * @param no_peserta
     */
    public void setNo_peserta(String no_peserta) {
        this.no_peserta = no_peserta;
    }

    /**
     *
     * @return
     */
    public Wilayah getKd_kel() {
        return kd_kel;
    }

    /**
     *
     * @param kd_kel
     */
    public void setKd_kel(Wilayah kd_kel) {
        this.kd_kel = kd_kel;
    }

    /**
     *
     * @return
     */
    public Wilayah getKd_kec() {
        return kd_kec;
    }

    /**
     *
     * @param kd_kec
     */
    public void setKd_kec(Wilayah kd_kec) {
        this.kd_kec = kd_kec;
    }

    /**
     *
     * @return
     */
    public Wilayah getKd_kab() {
        return kd_kab;
    }

    /**
     *
     * @param kd_kab
     */
    public void setKd_kab(Wilayah kd_kab) {
        this.kd_kab = kd_kab;
    }

    /**
     *
     * @return
     */
    public String getPekerjaanpj() {
        return pekerjaanpj;
    }

    /**
     *
     * @param pekerjaanpj
     */
    public void setPekerjaanpj(String pekerjaanpj) {
        this.pekerjaanpj = pekerjaanpj;
    }

    /**
     *
     * @return
     */
    public String getAlamatpj() {
        return alamatpj;
    }

    /**
     *
     * @param alamatpj
     */
    public void setAlamatpj(String alamatpj) {
        this.alamatpj = alamatpj;
    }

    /**
     *
     * @return
     */
    public String getKelurahanpj() {
        return kelurahanpj;
    }

    /**
     *
     * @param kelurahanpj
     */
    public void setKelurahanpj(String kelurahanpj) {
        this.kelurahanpj = kelurahanpj;
    }

    /**
     *
     * @return
     */
    public String getKecamatanpj() {
        return kecamatanpj;
    }

    /**
     *
     * @param kecamatanpj
     */
    public void setKecamatanpj(String kecamatanpj) {
        this.kecamatanpj = kecamatanpj;
    }

    /**
     *
     * @return
     */
    public String getKabupatenpj() {
        return kabupatenpj;
    }

    /**
     *
     * @param kabupatenpj
     */
    public void setKabupatenpj(String kabupatenpj) {
        this.kabupatenpj = kabupatenpj;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getNip() {
        return nip;
    }

    /**
     *
     * @param nip
     */
    public void setNip(String nip) {
        this.nip = nip;
    }

    /**
     *
     * @return
     */
    public String getPropinsipj() {
        return propinsipj;
    }

    /**
     *
     * @param propinsipj
     */
    public void setPropinsipj(String propinsipj) {
        this.propinsipj = propinsipj;
    }

    /**
     *
     * @return
     */
    public PerusahaanPasien getPerusahaan_pasien() {
        return perusahaan_pasien;
    }

    /**
     *
     * @param perusahaan_pasien
     */
    public void setPerusahaan_pasien(PerusahaanPasien perusahaan_pasien) {
        this.perusahaan_pasien = perusahaan_pasien;
    }

    /**
     *
     * @return
     */
    public SukuBangsa getSuku_bangsa() {
        return suku_bangsa;
    }

    /**
     *
     * @param suku_bangsa
     */
    public void setSuku_bangsa(SukuBangsa suku_bangsa) {
        this.suku_bangsa = suku_bangsa;
    }

    /**
     *
     * @return
     */
    public BahasaPasien getBahasa_pasien() {
        return bahasa_pasien;
    }

    /**
     *
     * @param bahasa_pasien
     */
    public void setBahasa_pasien(BahasaPasien bahasa_pasien) {
        this.bahasa_pasien = bahasa_pasien;
    }

    /**
     *
     * @return
     */
    public CacatFisik getCacat_fisik() {
        return cacat_fisik;
    }

    /**
     *
     * @param cacat_fisik
     */
    public void setCacat_fisik(CacatFisik cacat_fisik) {
        this.cacat_fisik = cacat_fisik;
    }

    /**
     *
     * @return
     */
    public Wilayah getKd_prop() {
        return kd_prop;
    }

    /**
     *
     * @param kd_prop
     */
    public void setKd_prop(Wilayah kd_prop) {
        this.kd_prop = kd_prop;
    }
}
