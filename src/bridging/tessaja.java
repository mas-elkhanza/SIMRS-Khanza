/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

/**
 *
 * @author khanzasoft
 */
public class tessaja {
    public static ApiBPJS api=new ApiBPJS();
    public static void tessaja(){
        System.out.println("X-Timestamp:"+String.valueOf(api.GetUTCdatetimeAsString()));
        System.out.println("X-Signature:"+api.getHmac());
    }
    public static void main(String[] args) {
         tessaja();
    }
    
}
