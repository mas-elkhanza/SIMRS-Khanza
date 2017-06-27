/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bridging;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileInputStream;
import java.util.Properties;
import javax.swing.JOptionPane;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author khanzasoft
 */
public class BPJSCekNoKartu {
    public String nik="",nama="",pekerjaan="",tgl_lahir="",jk="",umur="",kdJenisPeserta="",
                kdKelas="",nmKelas="",noMr="",pisa="",kdCabang="",kdProvider="",nmCabang="",
                nmProvider="",keterangan="",kode="",tglCetakKartu="",tglTAT="",tglTMT="",
                umurSaatPelayanan="",informasi="";
    private final Properties prop = new Properties();
    
    public BPJSCekNoKartu(){
        super();
    }
    
    public void tampil(String nokartu) {
        BPJSApi api=new BPJSApi();
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
            String URL = prop.getProperty("URLAPIBPJS")+"/Peserta/Peserta/"+nokartu;	

	    HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.add("X-Cons-ID",prop.getProperty("CONSIDAPIBPJS"));
	    headers.add("X-Timestamp",String.valueOf(api.GetUTCdatetimeAsString()));
	    headers.add("X-Signature",api.getHmac());
	    HttpEntity requestEntity = new HttpEntity(headers);
	    RestTemplate rest = new RestTemplate();	
            
            //System.out.println(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(rest.exchange(URL, HttpMethod.GET, requestEntity, String.class).getBody());
            JsonNode nameNode = root.path("metadata");
            //System.out.println("code : "+nameNode.path("code").asText());
            //System.out.println("message : "+nameNode.path("message").asText());
            informasi=nameNode.path("message").asText();
            if(nameNode.path("message").asText().equals("OK")){
                JsonNode response = root.path("response");
                nik=response.path("peserta").path("nik").asText();
                nama=response.path("peserta").path("nama").asText();
                pekerjaan=response.path("peserta").path("jenisPeserta").path("nmJenisPeserta").asText();
                tgl_lahir=response.path("peserta").path("tglLahir").asText();
                jk=response.path("peserta").path("sex").asText().replaceAll("L","LAKI-LAKI").replaceAll("P","PEREMPUAN");
                try {
                    umur=response.path("peserta").path("umur").path("umurSekarang").asText().substring(0,2);
                } catch (Exception e) {
                    umur="0";
                }
                kdJenisPeserta=response.path("peserta").path("jenisPeserta").path("kdJenisPeserta").asText();
                kdKelas=response.path("peserta").path("kelasTanggungan").path("kdKelas").asText();
                nmKelas=response.path("peserta").path("kelasTanggungan").path("nmKelas").asText();
                noMr=response.path("peserta").path("noMr").asText();
                pisa=response.path("peserta").path("pisa").asText();
                kdCabang=response.path("peserta").path("provUmum").path("kdCabang").asText();
                kdProvider=response.path("peserta").path("provUmum").path("kdProvider").asText();
                nmCabang=response.path("peserta").path("provUmum").path("nmCabang").asText();
                nmProvider=response.path("peserta").path("provUmum").path("nmProvider").asText();
                keterangan=response.path("peserta").path("statusPeserta").path("keterangan").asText();
                kode=response.path("peserta").path("statusPeserta").path("kode").asText();
                tglCetakKartu=response.path("peserta").path("tglCetakKartu").asText();
                tglTAT=response.path("peserta").path("tglTAT").asText();
                tglTMT=response.path("peserta").path("tglTMT").asText();
                umurSaatPelayanan=response.path("peserta").path("umur").path("umurSaatPelayanan").asText();
            }else {
                JOptionPane.showMessageDialog(null,nameNode.path("message").asText());                
            }   
        } catch (Exception ex) {
            System.out.println("Notifikasi Peserta : "+ex);
            if(ex.toString().contains("UnknownHostException")){
                JOptionPane.showMessageDialog(null,"Koneksi ke server BPJS terputus...!");
            }
        }
    }
    
}
