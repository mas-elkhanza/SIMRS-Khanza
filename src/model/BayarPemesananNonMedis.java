package model;

import java.util.Date;

/**
 *
 * @author RSUI HA
 */
public class BayarPemesananNonMedis {

    private int id;
    private Date tgl_bayar;
    private String no_faktur;
    private String nip;
    private double besar_bayar;
    private String keterangan;
    private String nama_bayar;
    private String no_bukti;

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public Date getTgl_bayar() {
        return tgl_bayar;
    }

    /**
     *
     * @param tgl_bayar
     */
    public void setTgl_bayar(Date tgl_bayar) {
        this.tgl_bayar = tgl_bayar;
    }

    /**
     *
     * @return
     */
    public String getNo_faktur() {
        return no_faktur;
    }

    /**
     *
     * @param no_faktur
     */
    public void setNo_faktur(String no_faktur) {
        this.no_faktur = no_faktur;
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
    public double getBesar_bayar() {
        return besar_bayar;
    }

    /**
     *
     * @param besar_bayar
     */
    public void setBesar_bayar(double besar_bayar) {
        this.besar_bayar = besar_bayar;
    }

    /**
     *
     * @return
     */
    public String getKeterangan() {
        return keterangan;
    }

    /**
     *
     * @param keterangan
     */
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    /**
     *
     * @return
     */
    public String getNama_bayar() {
        return nama_bayar;
    }

    /**
     *
     * @param nama_bayar
     */
    public void setNama_bayar(String nama_bayar) {
        this.nama_bayar = nama_bayar;
    }

    /**
     *
     * @return
     */
    public String getNo_bukti() {
        return no_bukti;
    }

    /**
     *
     * @param no_bukti
     */
    public void setNo_bukti(String no_bukti) {
        this.no_bukti = no_bukti;
    }

}
