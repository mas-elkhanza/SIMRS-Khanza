/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;


import fungsi.koneksiDB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
@ManagedBean(name = "jadwalDokter")
@RequestScoped

/**
 *
 * @author windiartonugroho
 */
public class TampilJadwal {
    private Connection koneksi=koneksiDB.condb();
    private ResultSet rs;
    private String nm_dokter,hari_kerja,jam_mulai,jam_selesai,nm_poli;
    private ArrayList jadwalList ;
    public String getNm_dokter() {
        return nm_dokter;
    }

    public void setNm_dokter(String nm_dokter) {
        this.nm_dokter = nm_dokter;
    }

    public String getHari_kerja() {
        return hari_kerja;
    }

    public void setHari_kerja(String hari_kerja) {
        this.hari_kerja = hari_kerja;
    }

    public String getJam_mulai() {
        return jam_mulai;
    }

    public void setJam_mulai(String jam_mulai) {
        this.jam_mulai = jam_mulai;
    }

    public String getJam_selesai() {
        return jam_selesai;
    }

    public void setJam_selesai(String jam_selesai) {
        this.jam_selesai = jam_selesai;
    }

    public String getNm_poli() {
        return nm_poli;
    }

    public void setNm_poli(String nm_poli) {
        this.nm_poli = nm_poli;
    }
    
    public ArrayList jadwalList(){
        try{
            jadwalList = new ArrayList();
            rs=koneksi.prepareStatement(
                "select dokter.nm_dokter,jadwal.hari_kerja, "+
                "jadwal.jam_mulai,jadwal.jam_selesai,poliklinik.nm_poli,jadwal.kuota "+
                "from jadwal inner join poliklinik inner join dokter "+
                "on jadwal.kd_dokter=dokter.kd_dokter "+
                "and jadwal.kd_poli=poliklinik.kd_poli "+
                "order by jadwal.hari_kerja,jadwal.kd_dokter").executeQuery();  
            while(rs.next()){
                TampilJadwal jadwal = new TampilJadwal();
                jadwal.setNm_dokter(rs.getString("nm_dokter"));
                jadwal.setHari_kerja(rs.getString("hari_kerja"));
                jadwal.setJam_mulai(rs.getString("jam_mulai"));
                jadwal.setJam_selesai(rs.getString("jam_selesai"));
                jadwal.setNm_poli(rs.getString("nm_poli"));
                jadwalList.add(jadwal);
            }     
            rs.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return jadwalList;
    }
}
