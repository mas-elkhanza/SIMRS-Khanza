package model;

import java.util.Date;

/**
 *
 * @author RSUI HA
 */
public class AngsuranKoperasi {
private int id_angsuran;//awalnya id
private Date tanggal_pinjam;
private Date tanggal_angsur;
private double pokok;
private double jasa; 

    /**
     *
     * @return
     */
    public int getId_angsuran() {
        return id_angsuran;
    }

    /**
     *
     * @param id_angsuran
     */
    public void setId_angsuran(int id_angsuran) {
        this.id_angsuran = id_angsuran;
    }

    /**
     *
     * @return
     */
    public Date getTanggal_pinjam() {
        return tanggal_pinjam;
    }

    /**
     *
     * @param tanggal_pinjam
     */
    public void setTanggal_pinjam(Date tanggal_pinjam) {
        this.tanggal_pinjam = tanggal_pinjam;
    }

    /**
     *
     * @return
     */
    public Date getTanggal_angsur() {
        return tanggal_angsur;
    }

    /**
     *
     * @param tanggal_angsur
     */
    public void setTanggal_angsur(Date tanggal_angsur) {
        this.tanggal_angsur = tanggal_angsur;
    }

    /**
     *
     * @return
     */
    public double getPokok() {
        return pokok;
    }

    /**
     *
     * @param pokok
     */
    public void setPokok(double pokok) {
        this.pokok = pokok;
    }

    /**
     *
     * @return
     */
    public double getJasa() {
        return jasa;
    }

    /**
     *
     * @param jasa
     */
    public void setJasa(double jasa) {
        this.jasa = jasa;
    }

}