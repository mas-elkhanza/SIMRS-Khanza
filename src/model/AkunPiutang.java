package model;

/**
 *
 * @author RSUI HA
 */
public class AkunPiutang {

    private int id;
    private String nama_bayar;
    private String kd_rek;
    private String kd_pj;

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

}
