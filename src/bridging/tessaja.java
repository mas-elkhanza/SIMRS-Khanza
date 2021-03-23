/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import fungsi.koneksiDB;

/**
 *
 * @author khanzasoft
 */
public class tessaja {
    public static ApiKemenkesSITT api=new ApiKemenkesSITT();
    public static void tessaja(){
        System.out.println("X-rs-id:"+koneksiDB.IDSITT());
        System.out.println("X-pass:"+koneksiDB.PASSSITT()); 
        System.out.println("Content-Type:application/json");
        System.out.println("X-Timestamp:"+String.valueOf(api.GetUTCdatetimeAsString()));
    }
    public static void main(String[] args) {
         tessaja();
    }
    
}
