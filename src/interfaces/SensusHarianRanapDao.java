/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import fungsi.koneksiDB;
import fungsi.sekuel;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Bangsal;
import model.Kamar;
import model.KamarInap;

/**
 *
 * @author Administrator
 */
public class SensusHarianRanapDao implements SensusHarianRanapIntf<KamarInap> {

    private sekuel sekuel = new sekuel();
    PreparedStatement ps;
    KamarInap kamarInap = new KamarInap();
    Kamar kamar = new Kamar();
    Bangsal bangsal = new Bangsal();
    ResultSet rs;
    private final java.sql.Connection connect = koneksiDB.condb();

    /**
     *
     * @param kd_bangsal
     * @param tgl_awal
     * @param tgl_ahir
     * @return
     */
    @Override
    public List<KamarInap> findByKamar(String kd_bangsal, String tgl_awal, String tgl_ahir) {
        
        System.out.println("KD_BANGSAL = "+kd_bangsal);
            List<KamarInap> kis = new ArrayList<>();
            try {
            ps = connect.prepareStatement("SELECT nm_bangsal,kamar_inap.tgl_masuk, COUNT(kamar.kd_bangsal) AS isi "
                    + "FROM kamar_inap INNER JOIN reg_periksa "
                    + "INNER JOIN kamar INNER JOIN bangsal "
                    + "ON kamar_inap.no_rawat=reg_periksa.no_rawat "
                    + "AND kamar_inap.kd_kamar=kamar.kd_kamar "
                    + "AND kamar.kd_bangsal=bangsal.kd_bangsal "
                    + "WHERE kamar_inap.tgl_masuk BETWEEN '" + tgl_awal + "' AND '" + tgl_ahir + "' "
                            + "AND kamar.kd_bangsal LIKE '%" + kd_bangsal + "%' "
                                    + "AND kamar_inap.stts_pulang ='-' "
                                    + "GROUP BY kamar.kd_bangsal, kamar_inap.tgl_masuk ");
            rs = ps.executeQuery();
            while (rs.next()) {
                setNilaiList(kis);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SensusHarianRanapDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kis;
    }

    private void setNilaiList(List<KamarInap> kis) throws SQLException {
        KamarInap kamarInap = new KamarInap();
        kamarInap.setNm_kamar(rs.getString("nm_bangsal"));
        kamarInap.setTgl_masuk(rs.getDate("tgl_masuk"));
        kamarInap.setIsi(rs.getInt("isi"));
        kis.add(kamarInap);
    }

    /**
     *
     * @param tgl_awal
     * @param tgl_ahir
     * @return
     */
    @Override
    public List<KamarInap> findByAll(String tgl_awal, String tgl_ahir) {
        List<KamarInap> kis = new ArrayList<>();
        try {
            ps = connect.prepareStatement("SELECT nm_bangsal,kamar_inap.tgl_masuk, COUNT(kamar.kd_bangsal) AS isi "
                    + "FROM kamar_inap INNER JOIN reg_periksa "
                    + "INNER JOIN kamar INNER JOIN bangsal "
                    + "ON kamar_inap.no_rawat=reg_periksa.no_rawat "
                    + "AND kamar_inap.kd_kamar=kamar.kd_kamar "
                    + "AND kamar.kd_bangsal=bangsal.kd_bangsal "
                    + "WHERE kamar_inap.tgl_masuk BETWEEN '" + tgl_awal + "' AND '" + tgl_ahir + "' "
                    + "AND kamar_inap.stts_pulang ='-' "
                    + "GROUP BY kamar.kd_bangsal, kamar_inap.tgl_masuk ");

            rs = ps.executeQuery();
            while (rs.next()) {
                setNilaiList(kis);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SensusHarianRanapDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return kis;
    }

}
