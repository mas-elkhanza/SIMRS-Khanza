/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JTextField;

/**
 *
 * @author Administrator
 * @param <T>
 */
public interface SpriInterface<T> {

    /**
     *
     * @param domain
     * @throws SQLException
     */
    void save(T domain)throws SQLException;

    /**
     *
     * @param domain
     * @throws SQLException
     */
    void update(T domain)throws SQLException;

    /**
     *
     * @param a
     * @param text
     * @param domain
     * @throws SQLException
     */
    void delete(String tgl_awal, JTextField text ,String tgl_ahir)throws SQLException;

    /**
     *
     * @param tgl_awal
     * @param tgl_ahir
     * @return
     * @throws SQLException
     */
    List<T> findByDate(String tgl_awal, String tgl_ahir)throws SQLException;

    /**
     *
     * @param domain
     * @return
     * @throws SQLException
     */
    List<T> search(String domain,String tgl_awal, String tgl_ahir)throws SQLException;
}
