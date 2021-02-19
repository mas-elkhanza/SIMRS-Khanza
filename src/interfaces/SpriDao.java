/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTextField;
import model.Dokter;
import model.Pasien;
import model.Spri;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class SpriDao implements SpriInterface<Spri> {

    private final sekuel Sequel = new sekuel();
    private final java.sql.Connection connect = koneksiDB.condb();
    private final validasi Valid = new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private Spri spri = new Spri();
    private Pasien p = new Pasien();
    private Dokter d = new Dokter();
    private List<Spri> spris = new ArrayList<>();
    private List<Pasien> pasiens = new ArrayList<>();
    private List<Dokter> dokters = new ArrayList<>();

    /**
     *
     * @param domain
     */
    @Override
    public void save(Spri domain) {
        Sequel.menyimpan("temp_spri", ""
                + null + ",'"
                + domain.getTanggal() + "','"
                + domain.getJam() + "','"
                + domain.getNorm() + "','"
                + domain.getDiagnosa() + "','"
                + domain.getRencana_perawatan() + "','"
                + domain.getUpf() + "','"
                + domain.getDokter().getKd_dokter() + "','"
                + domain.getNama() + "','"
                + domain.getKeluhan() + "','"
                + domain.getStatus() + "','"
                + domain.getRujukan() + "','"
                + domain.getTerapi() + "','"
                + domain.getDokter().getNm_dokter() + "'", "spri");

    }

    /**
     *
     * @param a
     * @param text
     * @param domain
     */
    @Override
    public void delete(String tgl_awal, JTextField text, String tgl_ahir) {
        System.out.println("DELETE FROM temp_spri WHERE id = " + text.getText() + " AND tanggal between '" + tgl_awal + "' AND '" + tgl_ahir + "'");
        Sequel.queryu("DELETE FROM temp_spri WHERE id = " + text.getText() + " AND tanggal between '" + tgl_awal + "' AND '" + tgl_ahir + "'");
    }

    /**
     *
     * @param domain
     * @return
     */
    @Override
    public List<Spri> search(String domain, String tgl_awal, String tgl_ahir) {
        List<Spri> kis = new ArrayList<>();
        try {
            ps = connect.prepareStatement("SELECT temp_spri.id,temp_spri.tanggal,temp_spri.jam,temp_spri.norm,temp_spri.nama,"
                    + "temp_spri.rencana_perawatan,temp_spri.upf,dokter.nm_dokter,temp_spri.kd_dokter,temp_spri.diagnosa,temp_spri.keluhan,temp_spri.rujukan,temp_spri.terapi "
                    + " FROM temp_spri "
                    //                    + "inner join pasien on temp_spri.norm=pasien.no_rkm_medis"
                                        + " inner join dokter on temp_spri.kd_dokter=dokter.kd_dokter "
                    + " where"
                    + " temp_spri.norm like ? and temp_spri.tanggal between ? and ? or "
                    + " temp_spri.kd_dokter like ? and temp_spri.tanggal between ? and ? or "
                    + " temp_spri.nama like ? and temp_spri.tanggal between ? and ?"
                    + " order by temp_spri.tanggal ");

            ps.setString(1, "%" + domain + "%");
            ps.setString(2, tgl_awal);
            ps.setString(3, tgl_ahir);
            ps.setString(4, "%" + domain + "%");
            ps.setString(5, tgl_awal);
            ps.setString(6, tgl_ahir);
            ps.setString(7, "%" + domain + "%");
            ps.setString(8, tgl_awal);
            ps.setString(9, tgl_ahir);

            rs = ps.executeQuery();
            while (rs.next()) {
                setSpriData(kis);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SpriDao.class.getName()).log(Level.ERROR, null, ex);
            System.out.println("Error: " + ex);
        }
        return kis;
    }

    /**
     *
     * @param domain
     */
    @Override
    public void update(Spri domain) {
        Sequel.mengedit("temp_spri", "id='" + domain.getId() + "'",
                "tanggal='" + domain.getTanggal() + "',"
                + "jam='" + domain.getJam() + "',"
                + "norm='" + domain.getNorm() + "',"
                + "diagnosa='" + domain.getDiagnosa() + "',"
                + "rencana_perawatan='" + domain.getRencana_perawatan() + "',"
                + "upf='" + domain.getUpf() + "',"
                + "kd_dokter='" + domain.getDokter().getKd_dokter() + "',"
                + "nama='" + domain.getNama() + "',"
                + "keluhan='" + domain.getKeluhan() + "',"
                + "rujukan='" + domain.getRujukan() + "',"
                + "terapi='" + domain.getTerapi() + "',"
                + "nm_dokter='" + domain.getDokter().getNm_dokter() + "'");
    }

    /**
     *
     * @param tgl_awal
     * @param tgl_ahir
     * @return
     */
    @Override
    public List<Spri> findByDate(String tgl_awal, String tgl_ahir) {
        List<Spri> kis = new ArrayList<>();
        try {
            ps = connect.prepareStatement("SELECT temp_spri.id,temp_spri.tanggal,temp_spri.jam,temp_spri.norm,temp_spri.nama,"
                    //                    + "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah,"
                    //                    + "pasien.agama,"
                    + "temp_spri.rencana_perawatan,temp_spri.upf,"
                    + "dokter.nm_dokter,temp_spri.kd_dokter,temp_spri.diagnosa,temp_spri.keluhan,temp_spri.rujukan,temp_spri.terapi "
                    + " FROM temp_spri" //inner join pasien on temp_spri.norm=pasien.no_rkm_medis"
                                        + " inner join dokter on temp_spri.kd_dokter=dokter.kd_dokter"
                    + " where temp_spri.tanggal between ? and ? "
                    + " order by temp_spri.tanggal desc");

            ps.setString(1, tgl_awal);
            ps.setString(2, tgl_ahir);

            rs = ps.executeQuery();
            while (rs.next()) {
                setSpriData(kis);
            }

        } catch (SQLException ex) {
            Logger.getLogger(SpriDao.class.getName()).log(Level.ERROR, null, ex);
            System.out.println("Error: " + ex);
        }
        return kis;
    }

    private void setSpriData(List<Spri> kis) throws SQLException {
        spri = new Spri();
        p = new Pasien();
        d = new Dokter();
        spri.setTanggal(rs.getString("tanggal"));
        spri.setJam(rs.getString("jam"));
        spri.setNorm(rs.getString("norm"));
        spri.setNama(rs.getString("nama"));

//        p.setJk(rs.getString("jk"));
//        p.setTmp_lahir(rs.getString("tmp_lahir"));
//        p.setTgl_lahir(rs.getDate("tgl_lahir"));
//        p.setGol_darah(rs.getString("gol_darah"));
//        p.setStts_nikah(rs.getString("pasien.stts_nikah"));
//        p.setAgama(rs.getString("pasien.agama"));
//        spri.setPasien(p);
        spri.setRencana_perawatan(rs.getString("rencana_perawatan"));
        spri.setUpf(rs.getString("temp_spri.upf"));

        d.setNm_dokter(rs.getString("nm_dokter"));
        d.setKd_dokter(rs.getString("kd_dokter"));
        //dokters.add(d);
        spri.setDokter(d);

        spri.setNama(rs.getString("nama"));
        spri.setKeluhan(rs.getString("keluhan"));
        spri.setDiagnosa(rs.getString("diagnosa"));
        spri.setRujukan(rs.getString("rujukan"));
        spri.setTerapi(rs.getString("terapi"));
        spri.setId(rs.getInt("id"));
        kis.add(spri);
    }
}
