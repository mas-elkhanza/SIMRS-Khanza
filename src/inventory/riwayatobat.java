/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory;

import fungsi.koneksiDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author khanzamedia
 */
public class riwayatobat {
    private final Connection koneksi=koneksiDB.condb(); 
    private ResultSet rs,rsawal;
    private PreparedStatement ps,psawal;
    private double stokawal=0,stokakhir=0;
    public void catatRiwayat(String kodebarang,double masuk,double keluar,String posisi,String petugas,String kdbangsal,String status,String nobatch,String nofaktur,String keterangan){        
        try {
            stokakhir=0;stokawal=0;            
            psawal=koneksi.prepareStatement("select stok from gudangbarang where kode_brng=? and kd_bangsal=? and no_batch=? and no_faktur=?");
            try {
                psawal.setString(1,kodebarang);
                psawal.setString(2,kdbangsal);
                psawal.setString(3,nobatch);
                psawal.setString(4,nofaktur);
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
                     
            ps=koneksi.prepareStatement("insert into riwayat_barang_medis values(?,?,?,?,?,?,current_date(),current_time(),?,?,?,?,?,?)");
            try {
                if(posisi.equals("Opname")){
                    ps.setString(1,kodebarang);
                    ps.setDouble(2,stokawal);
                    ps.setDouble(3,masuk);
                    ps.setDouble(4,0);
                    ps.setDouble(5,masuk);
                    ps.setString(6,posisi);
                    ps.setString(7,petugas);
                    ps.setString(8,kdbangsal);
                    ps.setString(9,status);
                    ps.setString(10,nobatch);
                    ps.setString(11,nofaktur);
                    ps.setString(12,keterangan);
                    ps.executeUpdate();
                }else{
                    ps.setString(1,kodebarang);
                    ps.setDouble(2,stokawal);
                    ps.setDouble(3,masuk);
                    ps.setDouble(4,keluar);
                    ps.setDouble(5,stokakhir);
                    ps.setString(6,posisi);
                    ps.setString(7,petugas);
                    ps.setString(8,kdbangsal);
                    ps.setString(9,status);
                    ps.setString(10,nobatch);
                    ps.setString(11,nofaktur);
                    ps.setString(12,keterangan);
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
    

