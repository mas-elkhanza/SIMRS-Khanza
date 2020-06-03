package model;

/**
 *
 * @author RSUI HA
 */
public class BahasaPasien {

    private int id_bahasa;//id di database
    private String nama_bahasa;

    /**
     *
     * @return
     */
    public int getId_bahasa() {
        return id_bahasa;
    }

    /**
     *
     * @param id_bahasa
     */
    public void setId_bahasa(int id_bahasa) {
        this.id_bahasa = id_bahasa;
    }

    /**
     *
     * @return
     */
    public String getNama_bahasa() {
        return nama_bahasa;
    }

    /**
     *
     * @param nama_bahasa
     */
    public void setNama_bahasa(String nama_bahasa) {
        this.nama_bahasa = nama_bahasa;
    }
    
}