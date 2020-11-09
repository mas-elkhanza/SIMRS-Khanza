/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fungsi;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 *
 * @author RSUI HA
 */
public class Base64ToPdf {

    public String baseToPdf(String name, String bytedata) {
        File file = new File("./SJP/" + name);

        try (FileOutputStream fos = new FileOutputStream(file);) {
            // To be short I use a corrupted PDF string, so make sure to use a valid one if you want to preview the PDF file
            // String b64 = "JVBERi0xLjUKJYCBgoMKMSAwIG9iago8PC9GaWx0ZXIvRmxhdGVEZWNvZGUvRmlyc3QgMTQxL04gMjAvTGVuZ3==";
            byte[] decoder = Base64.getDecoder().decode(bytedata);
            fos.write(decoder);
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(file);
                } catch (IOException ex) {
                    // no application registered for PDFs
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return name;
    }
}
