/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package keuangan;

import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author khanzamedia
 */
public class Jurnal {
    private final sekuel Sequel=new sekuel();
    private final validasi Valid=new validasi();
    private final Connection koneksi=koneksiDB.condb(); 
    private ResultSet rs;
    private PreparedStatement ps2,ps;
    public void simpanJurnal(String nobukti,String tanggal,String jenis,String keterangan){
        String nojur=Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_jurnal,8),signed)),0) from jurnal","JR",10);                   
        try {
             koneksi.setAutoCommit(false);
             ps=koneksi.prepareStatement("insert into jurnal values(?,?,?,?,?)");
             try {
                ps.setString(1,nojur);
                ps.setString(2,nobukti);
                ps.setString(3,tanggal);
                ps.setString(4,jenis);
                ps.setString(5,keterangan);
                ps.executeUpdate();
             } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
             } finally{
                if(ps!=null){
                    ps.close();
                }
             }
             
             try {
                rs=koneksi.prepareStatement("select kd_rek, nm_rek, debet, kredit from tampjurnal").executeQuery();
                while(rs.next()){
                    ps2=koneksi.prepareStatement("insert into detailjurnal values(?,?,?,?)");
                    try {
                        ps2.setString(1,nojur);
                        ps2.setString(2,rs.getString(1));
                        ps2.setString(3,rs.getString(3));
                        ps2.setString(4,rs.getString(4));
                        ps2.executeUpdate();
                    } catch (Exception e) {
                        System.out.println("Notifikasi sub : "+e);
                    } finally{
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                }
             } catch (Exception e) {
                 System.out.println("Notif Temp Rek : "+e);
             } finally{
                 if(rs!=null){
                     rs.close();
                 }
             }
             Sequel.queryu2("delete from tampjurnal");   
             koneksi.setAutoCommit(true);              
        } catch (Exception ex) {
            System.out.println("Notifikasi : "+ex);  
        }            
   }
}
    

