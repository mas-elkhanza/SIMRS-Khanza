/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package smsobj;

/**
 *
 * @author perpustakaan
 */
public class Status {
    private String manufacture;

    /**
     *
     * @return
     */
    public int getBaterai() {
        return baterai;
    }

    /**
     *
     * @param baterai
     */
    public void setBaterai(int baterai) {
        this.baterai = baterai;
    }

    /**
     *
     * @return
     */
    public String getManufacture() {
        return manufacture;
    }

    /**
     *
     * @param manufacture
     */
    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    /**
     *
     * @return
     */
    public String getModel() {
        return model;
    }

    /**
     *
     * @param model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     *
     * @return
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     *
     * @param serialNo
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     *
     * @return
     */
    public int getSignal() {
        return signal;
    }

    /**
     *
     * @param signal
     */
    public void setSignal(int signal) {
        this.signal = signal;
    }

    /**
     *
     * @return
     */
    public String getSimImsi() {
        return simImsi;
    }

    /**
     *
     * @param simImsi
     */
    public void setSimImsi(String simImsi) {
        this.simImsi = simImsi;
    }
    private String model;
    private String serialNo;
    private String simImsi;
    private int signal;
    private int baterai;

}
