package model;

import java.util.Date;

/**
 *
 * @author RSUI HA
 */
public class BookingOperasi {
private String no_rawat;
private String kode_paket;
private Date tanggal;
private String jam_mulai;
private String jam_selesai;
private String status;

    /**
     *
     * @return
     */
    public String getNo_rawat() {
        return no_rawat;
    }

    /**
     *
     * @param no_rawat
     */
    public void setNo_rawat(String no_rawat) {
        this.no_rawat = no_rawat;
    }

    /**
     *
     * @return
     */
    public String getKode_paket() {
        return kode_paket;
    }

    /**
     *
     * @param kode_paket
     */
    public void setKode_paket(String kode_paket) {
        this.kode_paket = kode_paket;
    }

    /**
     *
     * @return
     */
    public Date getTanggal() {
        return tanggal;
    }

    /**
     *
     * @param tanggal
     */
    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    /**
     *
     * @return
     */
    public String getJam_mulai() {
        return jam_mulai;
    }

    /**
     *
     * @param jam_mulai
     */
    public void setJam_mulai(String jam_mulai) {
        this.jam_mulai = jam_mulai;
    }

    /**
     *
     * @return
     */
    public String getJam_selesai() {
        return jam_selesai;
    }

    /**
     *
     * @param jam_selesai
     */
    public void setJam_selesai(String jam_selesai) {
        this.jam_selesai = jam_selesai;
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


}