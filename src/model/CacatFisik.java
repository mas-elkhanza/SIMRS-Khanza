package model;

/**
 *
 * @author RSUI HA
 */
public class CacatFisik {
private int id_cacat_fisik;//id di tabel
private String nama_cacat;

    /**
     *
     * @return
     */
    public int getId_cacat_fisik() {
        return id_cacat_fisik;
    }

    /**
     *
     * @param id_cacat_fisik
     */
    public void setId_cacat_fisik(int id_cacat_fisik) {
        this.id_cacat_fisik = id_cacat_fisik;
    }

    /**
     *
     * @return
     */
    public String getNama_cacat() {
        return nama_cacat;
    }

    /**
     *
     * @param nama_cacat
     */
    public void setNama_cacat(String nama_cacat) {
        this.nama_cacat = nama_cacat;
    }


}