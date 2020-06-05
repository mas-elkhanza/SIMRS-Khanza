package model;

/**
 *
 * @author RSUI HA
 */
public class AkunBayar {
private String nama_bayar;
private String kd_rek;
private double ppn;

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
    public String getKd_rek() {
        return kd_rek;
    }

    /**
     *
     * @param kd_rek
     */
    public void setKd_rek(String kd_rek) {
        this.kd_rek = kd_rek;
    }

    /**
     *
     * @return
     */
    public double getPpn() {
        return ppn;
    }

    /**
     *
     * @param ppn
     */
    public void setPpn(double ppn) {
        this.ppn = ppn;
    }

}