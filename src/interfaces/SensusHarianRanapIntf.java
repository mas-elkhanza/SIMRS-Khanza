/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Administrator
 * @param <T>
 */
public interface SensusHarianRanapIntf<T> {

    /**
     *
     * @param kd_bangsal
     * @param tgl_awal
     * @param tgl_ahir
     * @return
     * @throws SQLException
     */
    List<T> findByKamar(String kd_bangsal, String tgl_awal, String tgl_ahir) throws SQLException ;

    /**
     *
     * @param tgl_awal
     * @param tgl_ahir
     * @return
     * @throws SQLException
     */
    List<T> findByAll(String tgl_awal, String tgl_ahir) throws SQLException ;
}
