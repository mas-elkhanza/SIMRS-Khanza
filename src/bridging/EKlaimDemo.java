package bridging;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class EKlaimDemo {	
    public String postURL(String data) {
	data = EKlaimHelper.mcEncrypt(data, EKlaimHelper.WS_KEY());
	String result = "";
	HttpURLConnection con = null;
	BufferedReader in = null;
	try {
            URL obj = new URL(EKlaimHelper.WS_URL());
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            con.setRequestProperty("Accept-Language","en-US,en;q=0.8,id;q=0.6,ms;q=0.4");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(data);
            wr.flush();
            wr.close();

            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            System.out.println("Response : ");
            List<String> arrresponse = new ArrayList<String>();
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
                arrresponse.add(inputLine);
            }
            in.close();
            List<String> arrdec = arrresponse.subList(1, arrresponse.size() - 1);

            String x = EKlaimHelper.implode("", arrdec);
            result = EKlaimHelper.mcDecrypt(x, EKlaimHelper.WS_KEY());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
            if (con != null) {
                con.disconnect();
            }
        }
	return result;
    }

    public String ReqNewClaim(String nomor_kartu,String nomor_sep,String nomor_rm,String nama_pasien,String tgl_lahir,String gender) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "new_claim");

        JSONObject data = new JSONObject();
        data.put("nomor_kartu",nomor_kartu);
        data.put("nomor_sep",nomor_sep);
        data.put("nomor_rm",nomor_rm);
        data.put("nama_pasien",nama_pasien);
        data.put("tgl_lahir", tgl_lahir);
        data.put("gender",gender);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }

    public static String ReqUpdatePatient(String nomor_rmlama,String nomor_kartu,String nomor_sep,String nomor_rm,String nama_pasien,String tgl_lahir,String gender) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "update_patient");
        metadata.put("nomor_rm", nomor_rmlama);

        JSONObject data = new JSONObject();
        data.put("nomor_kartu",nomor_kartu);
        data.put("nomor_rm", nomor_rm);
        data.put("nama_pasien",nama_pasien);
        data.put("tgl_lahir",tgl_lahir);
        data.put("gender",gender);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }
    
    public static String ReqDeletePatient(String nomor_rm,String coder_nik) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "delete_patient");

        JSONObject data = new JSONObject();
        data.put("nomor_rm",nomor_rm);
        data.put("coder_nik",coder_nik);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }

    public static String ReqUpdateClaim(
          String nomor_seplama,String nomor_sep,String nomor_kartu,String tgl_masuk,
          String tgl_pulang,String jenis_rawat,String kelas_rawat,String adl_sub_acute,
          String adl_chronic,String icu_indikator,String icu_los,String ventilator_hour,
          String upgrade_class_ind,String upgrade_class_class,String upgrade_class_los,
          String add_payment_pct,String birth_weight,String discharge_status,String diagnosa,
          String procedure,String tarif_rs,String tarif_poli_eks,String nama_dokter,String kode_tarif,
          String payor_id,String payor_cd,String cob_cd,String coder_nik) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "set_claim_data");
        metadata.put("nomor_sep",nomor_seplama);

        JSONObject data = new JSONObject();
        data.put("nomor_sep", nomor_sep);
        data.put("nomor_kartu",nomor_kartu);
        data.put("tgl_masuk",tgl_masuk);
        data.put("tgl_pulang",tgl_pulang);
        data.put("jenis_rawat",jenis_rawat);
        data.put("kelas_rawat",kelas_rawat);
        data.put("adl_sub_acute",adl_sub_acute);
        data.put("adl_chronic",adl_chronic);
        data.put("icu_indikator",icu_indikator);
        data.put("icu_los",icu_los);
        data.put("ventilator_hour",ventilator_hour);
        data.put("upgrade_class_ind", upgrade_class_ind);
        data.put("upgrade_class_class", upgrade_class_class);
        data.put("upgrade_class_los",upgrade_class_los);
        data.put("add_payment_pct",add_payment_pct);
        data.put("birth_weight",birth_weight);
        data.put("discharge_status", discharge_status);
        data.put("diagnosa",diagnosa);
        data.put("procedure",procedure);
        data.put("tarif_rs",tarif_rs);
        data.put("tarif_poli_eks",tarif_poli_eks);
        data.put("nama_dokter",nama_dokter);
        data.put("kode_tarif",kode_tarif);
        data.put("payor_id",payor_id);
        data.put("payor_cd",payor_cd);
        data.put("cob_cd",cob_cd);
        data.put("coder_nik",coder_nik);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }
    
    public static String ReqUpdateProcedure(String nomor_sep,String procedure,String coder_nik)throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "set_claim_data");
        metadata.put("nomor_sep",nomor_sep);
        
        JSONObject data = new JSONObject();
        data.put("procedure",procedure);
        data.put("coder_nik",coder_nik);
        
        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }
    
    public static String ReqDeleteAllProcedure(String nomor_sep,String coder_nik)throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "set_claim_data");
        metadata.put("nomor_sep",nomor_sep);
        
        JSONObject data = new JSONObject();
        data.put("procedure","#");
        data.put("coder_nik",coder_nik);
        
        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }

    public static String ReqGrouper1(String nomor_sep) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "grouper");
        metadata.put("stage", "1");

        JSONObject data = new JSONObject();
        data.put("nomor_sep",nomor_sep);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }

    public static String ReqGrouper2(String nomor_sep,String special_cmg) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "grouper");
        metadata.put("stage", "2");

        JSONObject data = new JSONObject();
        data.put("nomor_sep", nomor_sep);
        data.put("special_cmg",special_cmg);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }

    public static String ReqFinalClaim(String nomor_sep,String coder_nik) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "claim_final");

        JSONObject data = new JSONObject();
        data.put("nomor_sep", nomor_sep);
        data.put("coder_nik", coder_nik);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }

    public static String ReqReeditClaim(String nomor_sep) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "reedit_claim");

        JSONObject data = new JSONObject();
        data.put("nomor_sep", nomor_sep);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }

    public static String ReqSendDC(String start_dt,String stop_dt,String jenis_rawat) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "send_claim");

        JSONObject data = new JSONObject();
        data.put("start_dt",start_dt);
        data.put("stop_dt",stop_dt);
        data.put("jenis_rawat",jenis_rawat);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }
    
    public static String ReqSendIndividual(String nomor_sep) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "send_claim_individual");

        JSONObject data = new JSONObject();
        data.put("nomor_sep",nomor_sep);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }

    public static String ReqPullClaim(String start_dt,String stop_dt,String jenis_rawat) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "pull_claim");

        JSONObject data = new JSONObject();
        data.put("start_dt",start_dt);
        data.put("stop_dt", stop_dt);
        data.put("jenis_rawat",jenis_rawat);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }

    public static String ReqGetClaim(String nomor_sep) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "get_claim_data");

        JSONObject data = new JSONObject();
        data.put("nomor_sep", nomor_sep);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }
    
    public static String ReqDeleteClaim(String nomor_sep,String coder_nik) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "delete_claim");

        JSONObject data = new JSONObject();
        data.put("nomor_sep", nomor_sep);
        data.put("coder_nik",coder_nik);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }
    
    public static String ReqPrintClaim(String nomor_sep) throws JSONException {
        JSONObject metadata = new JSONObject();
        metadata.put("method", "claim_print");

        JSONObject data = new JSONObject();
        data.put("nomor_sep", nomor_sep);

        JSONObject obj = new JSONObject();
        obj.put("metadata", metadata);
        obj.put("data", data);

        System.out.println("Request : ");
        System.out.println(obj.toString());
        return obj.toString();
    }

    public static void main(String[] args) throws JSONException {
//		untuk percobaan silahkan comment / uncomment yang akan dicoba
//		System.out.println(postURL(ReqNewClaim()));
//		System.out.println(postURL(ReqUpdatePatient()));
//		System.out.println(postURL(ReqUpdateClaim()));
//		System.out.println(postURL(ReqGrouper1()));
//		System.out.println(postURL(ReqGrouper2()));
//		System.out.println(postURL(ReqFinalClaim()));
//		System.out.println(postURL(ReqReeditClaim()));
//              System.out.println(postURL(ReqFinalClaim()));
//		System.out.println(postURL(ReqKirimDC()));
//		System.out.println(postURL(ReqPullClaim()));
//		System.out.println(postURL(ReqGetClaim()));
    }
	
}
