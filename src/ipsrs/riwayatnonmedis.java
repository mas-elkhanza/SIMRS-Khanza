/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ipsrs;

import toko.*;
import fungsi.koneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author khanzamedia
 */
public class riwayatnonmedis {
    private final Connection koneksi=koneksiDB.condb(); 
    private ResultSet rs,rsawal;
    private PreparedStatement ps,psawal;
    private double stokawal=0,stokakhir=0;
    public void catatRiwayat(String kodebarang,double masuk,double keluar,String posisi,String petugas,String status){        
        try {
            stokakhir=0;stokawal=0;            
            psawal=koneksi.prepareStatement("select stok from ipsrsbarang where kode_brng=?");
            try {
                psawal.setString(1,kodebarang);
                rs=psawal.executeQuery();
                if(rs.next()){
                    stokawal=rs.getDouble("stok");
                    stokakhir=stokawal+masuk-keluar;
                }else{
                    stokawal=0;
                    stokakhir=stokawal+masuk-keluar;
                }
            } catch (Exception e) {
                System.out.println("Notif Stok : "+e);
            } finally{
                if(rsawal!=null){
                    rsawal.close();
                }
                if(psawal!=null){
                    psawal.close();
                }
            }
                     
            ps=koneksi.prepareStatement("insert into ipsrs_riwayat_barang values(?,?,?,?,?,?,current_date(),current_time(),?,?)");
            try {
                if(posisi.equals("Opname")){
                    ps.setString(1,kodebarang);
                    ps.setDouble(2,stokawal);
                    ps.setDouble(3,masuk);
                    ps.setDouble(4,0);
                    ps.setDouble(5,masuk);
                    ps.setString(6,posisi);
                    ps.setString(7,petugas);
                    ps.setString(8,status);
                    ps.executeUpdate();
                }else{
                    ps.setString(1,kodebarang);
                    ps.setDouble(2,stokawal);
                    ps.setDouble(3,masuk);
                    ps.setDouble(4,keluar);
                    ps.setDouble(5,stokakhir);
                    ps.setString(6,posisi);
                    ps.setString(7,petugas);
                    ps.setString(8,status);
                    ps.executeUpdate();
                }                    
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(ps!=null){
                    ps.close();
                }
            }          
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);  
        }            
   }
}
    

