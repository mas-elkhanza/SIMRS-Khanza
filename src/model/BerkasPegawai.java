package model;


import java.util.Date;

/**
 *
 * @author RSUI HA
 */
public class BerkasPegawai {
private String nik;
private Date tgl_upload;
private String kode_berkas;
private String berkas;

    /**
     *
     * @return
     */
    public String getNik() {
        return nik;
    }

    /**
     *
     * @param nik
     */
    public void setNik(String nik) {
        this.nik = nik;
    }

    /**
     *
     * @return
     */
    public Date getTgl_upload() {
        return tgl_upload;
    }

    /**
     *
     * @param tgl_upload
     */
    public void setTgl_upload(Date tgl_upload) {
        this.tgl_upload = tgl_upload;
    }

    /**
     *
     * @return
     */
    public String getKode_berkas() {
        return kode_berkas;
    }

    /**
     *
     * @param kode_berkas
     */
    public void setKode_berkas(String kode_berkas) {
        this.kode_berkas = kode_berkas;
    }

    /**
     *
     * @return
     */
    public String getBerkas() {
        return berkas;
    }

    /**
     *
     * @param berkas
     */
    public void setBerkas(String berkas) {
        this.berkas = berkas;
    }

}