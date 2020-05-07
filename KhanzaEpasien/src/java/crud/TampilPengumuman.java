/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;
import fungsi.koneksiDB;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
@ManagedBean(name = "pengumumanEPasien")
@RequestScoped


/**
 *
 * @author windiartonugroho
 */
public final class TampilPengumuman {
    private Connection koneksi=koneksiDB.condb();
    private ResultSet rs;
    private String nama,tanggal,pengumuman;
    
    public TampilPengumuman(){
        try{
            rs=koneksi.prepareStatement(
                "select pengumuman_epasien.nik,pegawai.nama,date_format(pengumuman_epasien.tanggal,'%d/%m/%Y')as tanggal,pengumuman_epasien.pengumuman "+
                   "from pengumuman_epasien inner join pegawai on pengumuman_epasien.nik=pegawai.nik order by pengumuman_epasien.tanggal desc limit 1").executeQuery();  
            if(rs.next()){
                setNama(rs.getString("nama"));
                setTanggal(rs.getString("tanggal"));
                setPengumuman(rs.getString("pengumuman"));
            }     
            rs.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public String getNama() {
        return nama;
    }
    
    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPengumuman() {
        return pengumuman;
    }

    public void setPengumuman(String pengumuman) {
        this.pengumuman = pengumuman;
    }
    
}
