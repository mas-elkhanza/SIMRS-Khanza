/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud;
import fungsi.koneksiDB;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
@ManagedBean(name = "aplikasiSet")
@RequestScoped

/**
 *
 * @author windiartonugroho
 */
public class SetAplikasi {
    private String nama_instansi, alamat_instansi, kabupaten, propinsi, kontak, email, aktifkan, kode_ppk, kode_ppkinhealth, kode_ppkkemenkes;
    private Blob wallpaper,logo;
    private Connection koneksi=koneksiDB.condb();
    private ResultSet rs;
    
    public SetAplikasi(){
        try{
            rs=koneksi.prepareStatement("select * from setting").executeQuery();  
            if(rs.next()){
                setNama_instansi(rs.getString("nama_instansi"));
                setAlamat_instansi(rs.getString("alamat_instansi"));
                setKabupaten(rs.getString("kabupaten"));
                setPropinsi(rs.getString("propinsi"));
                setKontak(rs.getString("kontak"));
                setEmail(rs.getString("email"));
                setAktifkan(rs.getString("aktifkan"));
                setKode_ppk(rs.getString("kode_ppk"));
                setKode_ppkinhealth(rs.getString("kode_ppkinhealth"));
                setKode_ppkkemenkes(rs.getString("kode_ppkkemenkes"));
                setWallpaper(rs.getBlob("wallpaper"));
                setLogo(rs.getBlob("logo"));
            }
            rs.close();        
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public String getNama_instansi() {
        return nama_instansi;
    }

    public void setNama_instansi(String nama_instansi) {
        this.nama_instansi = nama_instansi;
    }

    public String getAlamat_instansi() {
        return alamat_instansi;
    }

    public void setAlamat_instansi(String alamat_instansi) {
        this.alamat_instansi = alamat_instansi;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getPropinsi() {
        return propinsi;
    }

    public void setPropinsi(String propinsi) {
        this.propinsi = propinsi;
    }
    
    public String getKontak() {
        return kontak;
    }

    public void setKontak(String kontak) {
        this.kontak = kontak;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAktifkan() {
        return aktifkan;
    }

    public void setAktifkan(String aktifkan) {
        this.aktifkan = aktifkan;
    }

    public String getKode_ppk() {
        return kode_ppk;
    }

    public void setKode_ppk(String kode_ppk) {
        this.kode_ppk = kode_ppk;
    }

    public String getKode_ppkinhealth() {
        return kode_ppkinhealth;
    }

    public void setKode_ppkinhealth(String kode_ppkinhealth) {
        this.kode_ppkinhealth = kode_ppkinhealth;
    }

    public String getKode_ppkkemenkes() {
        return kode_ppkkemenkes;
    }

    public void setKode_ppkkemenkes(String kode_ppkkemenkes) {
        this.kode_ppkkemenkes = kode_ppkkemenkes;
    }

    public Blob getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(Blob wallpaper) {
        this.wallpaper = wallpaper;
    }

    public Blob getLogo() {
        return logo;
    }

    public void setLogo(Blob logo) {
        this.logo = logo;
    }
    
}
