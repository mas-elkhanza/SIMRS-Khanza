/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Administrator
 */
public interface SpriInterface<T> {
    void save(T domain)throws SQLException;
    void update(T domain)throws SQLException;
    void delete(DefaultTableModel a, JTextField text ,String domain)throws SQLException;
    List<T> findByDate(String tgl_awal, String tgl_ahir)throws SQLException;
    List<T> search(String domain)throws SQLException;
}
