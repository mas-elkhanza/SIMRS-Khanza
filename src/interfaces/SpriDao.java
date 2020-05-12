/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import fungsi.sekuel;
import fungsi.validasi;
import java.util.List;
import model.Spri;

/**
 *
 * @author Administrator
 */
public class SpriDao implements SpriInterface<Spri> {

    private final sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();

    @Override
    public void saveOrUpdate(Spri domain) {
        Sequel.menyimpan("temp_spri", "" 
                + null + ",'" 
                + Valid.SetTgl(domain.getTanggal() + "") + "','"
                + domain.getJam() + "','"
                + domain.getNorm() + "','"
                + domain.getDiagnosa() + "','"
                + domain.getRencana_perawatan() + "','"
                + domain.getRencana_perawatan() + "','"
                + domain.getKd_dokter() + "','"
                + domain.getNama() + "','"
                + domain.getKeluhan() + "','"
                + domain.getStatus() + "'", "spri");

    }

    @Override
    public void delete(Spri domain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Spri> search(Object domain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Spri> findBy(Object domain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
