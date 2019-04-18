/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fungsi;

/**
 *
 * @author khanzamedia
 */
public class Tes {
    public static void main(String[] args){
        String strMain = "3.7 x 1 sehari";
        String[] arrSplit = strMain.split("x");
        
          System.out.println(arrSplit[0].replaceAll("[^0-9.]+", ""));
          System.out.println(arrSplit[1].replaceAll("[^0-9.]+", ""));
        
    }
}
