package model;

import java.util.Date;

/**
 *
 * @author RSUI HA
 */
public class ClosingKasir {
private String shift;
private Date jam_masuk;
private Date jam_pulang;

    /**
     *
     * @return
     */
    public String getShift() {
        return shift;
    }

    /**
     *
     * @param shift
     */
    public void setShift(String shift) {
        this.shift = shift;
    }

    /**
     *
     * @return
     */
    public Date getJam_masuk() {
        return jam_masuk;
    }

    /**
     *
     * @param jam_masuk
     */
    public void setJam_masuk(Date jam_masuk) {
        this.jam_masuk = jam_masuk;
    }

    /**
     *
     * @return
     */
    public Date getJam_pulang() {
        return jam_pulang;
    }

    /**
     *
     * @param jam_pulang
     */
    public void setJam_pulang(Date jam_pulang) {
        this.jam_pulang = jam_pulang;
    }

}