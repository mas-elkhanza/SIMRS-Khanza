/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fungsi;

import AESsecurity.EnkripsiAES;
import java.io.FileInputStream;
import java.util.Properties;

/**
 *
 * @author windiartonugroho
 */
public class getxml {
    private static final Properties prop = new Properties();
    private static String var="";
    
    public getxml(){
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    public static String cariCepat(){
        try{
            var=prop.getProperty("CARICEPAT");
        }catch(Exception e){
            var="tidak aktif"; 
        }
        return var;
    }
    
    public static String HOST(){
        try{
            var=EnkripsiAES.decrypt(prop.getProperty("HOSTHYBRIDWEB"));
        }catch(Exception e){
            var="localhost"; 
        }
        return var;
    }
    
    public static String PORT(){
        try{
            var=EnkripsiAES.decrypt(prop.getProperty("PORT"));
        }catch(Exception e){
            var="3306"; 
        }
        return var;
    }
    
    public static String DATABASE(){
        try{
            var=EnkripsiAES.decrypt(prop.getProperty("DATABASE"));
        }catch(Exception e){
            var="sik"; 
        }
        return var;
    }
}
