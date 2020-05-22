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
import javax.swing.table.DefaultTableModel;
import model.Dokter;
import model.Pasien;
import model.Spri;
import org.apache.log4j.Logger;

/**
 *
 * @author Administrator
 */
public class SpriDao implements SpriInterface<Spri> {

    static Logger log = Logger.getLogger(SpriDao.class.getName());  
    private final sekuel Sequel = new sekuel();
    private final java.sql.Connection connect = koneksiDB.condb();
    private validasi Valid = new validasi();
    PreparedStatement ps;
    ResultSet rs;
    Spri spri = new Spri();
    Pasien p = new Pasien();
    Dokter d = new Dokter();
    List<Spri> spris = new ArrayList<>();
    List<Pasien> pasiens = new ArrayList<>();
    List<Dokter> dokters = new ArrayList<>();

    @Override
    public void save(Spri domain) {
        Sequel.menyimpan("temp_spri", ""
                + null + ",'"
                + domain.getTanggal() + "','"
                + domain.getJam() + "','"
                + domain.getNorm() + "','"
                + domain.getDiagnosa() + "','"
                + domain.getRencana_perawatan() + "','"
                + domain.getUpf()+ "','"
                + domain.getDokter().getKd_dokter() + "','"
                + domain.getNama() + "','"
                + domain.getKeluhan() + "','"
                + domain.getStatus() + "'", "spri");

    }

    @Override
    public void delete(DefaultTableModel a, JTextField text, String domain) {
        Valid.hapusTable(a, text, "temp_spri", domain);
    }

    @Override
    public List<Spri> search(String domain) {
            List<Spri> kis = new ArrayList<>();
        try {
            ps = connect.prepareStatement("SELECT temp_spri.id,temp_spri.tanggal,temp_spri.jam,temp_spri.norm,temp_spri.nama,"
                    + "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah,"
                    + "pasien.agama,temp_spri.rencana_perawatan,temp_spri.upf,dokter.nm_dokter,penyakit.kd_penyakit,penyakit.nm_penyakit,temp_spri.kd_dokter,temp_spri.diagnosa,temp_spri.keluhan "
                    + " FROM temp_spri left join pasien on temp_spri.norm=pasien.no_rkm_medis"
                    + " left join dokter on temp_spri.kd_dokter=dokter.kd_dokter"
                    + " left join penyakit on temp_spri.diagnosa=penyakit.kd_penyakit where"
                    + " temp_spri.norm like ? or temp_spri.kd_dokter like ? or temp_spri.nama like ?"
                    + " order by temp_spri.tanggal ");
            ps.setString(1, "%"+domain+"%");
            ps.setString(2, "%"+domain+"%");
            ps.setString(3, "%"+domain+"%");
            
            rs = ps.executeQuery();
            while (rs.next()) {
                setSpriData(kis);
            }
        } catch (SQLException ex) {
            log.error(ex);
        }
            return kis;
    }

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
                + "keluhan='" + domain.getKeluhan() + "'");
    }

    @Override
    public List<Spri> findByDate(String tgl_awal, String tgl_ahir) {
            List<Spri> kis = new ArrayList<>();
        try {
            log.info("Tanggal SPRI ="+tgl_awal+", "+tgl_ahir);
            ps = connect.prepareStatement("SELECT temp_spri.id,temp_spri.tanggal,temp_spri.jam,temp_spri.norm,temp_spri.nama,"
                    + "pasien.jk,pasien.tmp_lahir,pasien.tgl_lahir,pasien.gol_darah,pasien.stts_nikah,"
                    + "pasien.agama,temp_spri.rencana_perawatan,temp_spri.upf,"
                    + "dokter.nm_dokter,temp_spri.kd_dokter,temp_spri.diagnosa,temp_spri.keluhan "
                    + " FROM temp_spri left join pasien on temp_spri.norm=pasien.no_rkm_medis"
                    + " left join dokter on temp_spri.kd_dokter=dokter.kd_dokter"
                    + " where temp_spri.tanggal between ? and ? "
                    + " order by temp_spri.tanggal desc");

            ps.setString(1, tgl_awal);
            ps.setString(2, tgl_ahir);
            
            rs = ps.executeQuery();
            while (rs.next()) {
                setSpriData(kis);
            }
            
        } catch (SQLException ex) {
            log.error(ex);
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
        spri.setNama(rs.getString("temp_spri.nama"));
        
        p.setJk(rs.getString("pasien.jk"));
        p.setTmp_lahir(rs.getString("pasien.tmp_lahir"));
        p.setTgl_lahir(rs.getDate("pasien.tgl_lahir"));
        p.setGol_darah(rs.getString("pasien.gol_darah"));
        p.setStts_nikah(rs.getString("pasien.stts_nikah"));
        p.setAgama(rs.getString("pasien.agama"));
        spri.setPasien(p);
        
        spri.setRencana_perawatan(rs.getString("temp_spri.rencana_perawatan"));
        spri.setUpf(rs.getString("temp_spri.upf"));
        
        d.setNm_dokter(rs.getString("dokter.nm_dokter"));
        d.setKd_dokter(rs.getString("temp_spri.kd_dokter"));
        //dokters.add(d);
        spri.setDokter(d);
        
        spri.setNama(rs.getString("nama"));
        spri.setKeluhan(rs.getString("keluhan"));
        spri.setDiagnosa(rs.getString("diagnosa"));
        spri.setId(rs.getInt("temp_spri.id"));
        kis.add(spri);
    }

}
