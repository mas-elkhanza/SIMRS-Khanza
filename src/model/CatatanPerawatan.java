package model;

import java.util.Date;
import org.joda.time.DateTime;

/**
 *
 * @author RSUI HA
 */
public class CatatanPerawatan {

    private Date tanggal;
    private DateTime jam;
    private String no_rawat;
    private String kd_dokter;
    private String catatan;

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
    public DateTime getJam() {
        return jam;
    }

    /**
     *
     * @param jam
     */
    public void setJam(DateTime jam) {
        this.jam = jam;
    }

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
    public String getKd_dokter() {
        return kd_dokter;
    }

    /**
     *
     * @param kd_dokter
     */
    public void setKd_dokter(String kd_dokter) {
        this.kd_dokter = kd_dokter;
    }

    /**
     *
     * @return
     */
    public String getCatatan() {
        return catatan;
    }

    /**
     *
     * @param catatan
     */
    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }
    
}
