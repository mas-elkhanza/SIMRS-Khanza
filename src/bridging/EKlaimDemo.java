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



/**
 * @author tubagus
 *
 */
public class EKlaimDemo {
	
	public static String postURL(String data) {

		data = EKlaimHelper.mcEncrypt(data, EKlaimHelper.WS_KEY);
		String result = "";
		HttpURLConnection con = null;
		BufferedReader in = null;
		try {
			URL obj = new URL(EKlaimHelper.WS_URL);
			con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			con.setRequestProperty("Accept-Language",
					"en-US,en;q=0.8,id;q=0.6,ms;q=0.4");
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
			result = EKlaimHelper.mcDecrypt(x, EKlaimHelper.WS_KEY);
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

	public static String ReqNewClaim() throws JSONException {
		JSONObject metadata = new JSONObject();
		metadata.put("method", "new_claim");

		JSONObject data = new JSONObject();
		data.put("nomor_kartu", "0000668873981");
		data.put("nomor_sep", "0301R00112140006067");
		data.put("nomor_rm", "123-45-67");
		data.put("nama_pasien", "SATINI");
		data.put("tgl_lahir", "1940-01-01 02:00:00");
		data.put("gender", "2");

		JSONObject obj = new JSONObject();
		obj.put("metadata", metadata);
		obj.put("data", data);

		System.out.println("Request : ");
		System.out.println(obj.toString());
		return obj.toString();
	}

	public static String ReqUpdatePatient() throws JSONException {
		JSONObject metadata = new JSONObject();
		metadata.put("method", "update_patient");
		metadata.put("nomor_rm", "123-45-67");

		JSONObject data = new JSONObject();
		data.put("nomor_kartu", "0000668873981");
		data.put("nomor_rm", "123-45-76");
		data.put("nama_pasien", "SATINI");
		data.put("tgl_lahir", "1940-01-01 02:00:00");
		data.put("gender", "2");

		JSONObject obj = new JSONObject();
		obj.put("metadata", metadata);
		obj.put("data", data);
		
		System.out.println("Request : ");
		System.out.println(obj.toString());
		return obj.toString();
	}

	public static String ReqUpdateClaim() throws JSONException {
		JSONObject metadata = new JSONObject();
		metadata.put("method", "set_claim_data");
		metadata.put("nomor_sep", "0301R00112140006067");

		JSONObject data = new JSONObject();
		data.put("nomor_sep", "0301R00112140006067");
		data.put("tgl_masuk", "2015-07-01 07:00:00");
		data.put("tgl_pulang", "2016-01-07 15:00:00");
		data.put("jenis_rawat", "1");
		data.put("kelas_rawat", "3");
		data.put("birth_weight", "");
		data.put("discharge_status", "1");
		data.put("diagnosa", "D56.1#A41.3");
		data.put("procedure", "99.0#88.09");
		data.put("adl_sub_acute", "15");
		data.put("adl_chronic", "12");
		data.put("tarif_rs", "2500000");
		data.put("nama_dokter", "dr. Erna");
		data.put("icu_indikator", "1");
		data.put("icu_los", "2");
		data.put("ventilator_hour", "5");
		data.put("kode_tarif", "CS");
		data.put("payor_id", "3");
		data.put("payor_cd", "JKN");
		data.put("coder_nik", "123123123123");

		JSONObject obj = new JSONObject();
		obj.put("metadata", metadata);
		obj.put("data", data);

		System.out.println("Request : ");
		System.out.println(obj.toString());
		return obj.toString();
	}

	public static String ReqGrouper1() throws JSONException {
		JSONObject metadata = new JSONObject();
		metadata.put("method", "grouper");
		metadata.put("stage", "1");

		JSONObject data = new JSONObject();
		data.put("nomor_sep", "0301R00112140006067");

		JSONObject obj = new JSONObject();
		obj.put("metadata", metadata);
		obj.put("data", data);

		System.out.println("Request : ");
		System.out.println(obj.toString());
		return obj.toString();
	}

	public static String ReqGrouper2() throws JSONException {
		JSONObject metadata = new JSONObject();
		metadata.put("method", "grouper");
		metadata.put("stage", "2");

		JSONObject data = new JSONObject();
		data.put("nomor_sep", "0301R00112140006067");
		data.put("special_cmg", "DD02");

		JSONObject obj = new JSONObject();
		obj.put("metadata", metadata);
		obj.put("data", data);
		
		System.out.println("Request : ");
		System.out.println(obj.toString());
		return obj.toString();
	}

	public static String ReqFinalClaim() throws JSONException {
		JSONObject metadata = new JSONObject();
		metadata.put("method", "claim_final");

		JSONObject data = new JSONObject();
		data.put("nomor_sep", "0301R00112140006067");

		JSONObject obj = new JSONObject();
		obj.put("metadata", metadata);
		obj.put("data", data);

		System.out.println("Request : ");
		System.out.println(obj.toString());
		return obj.toString();
	}

	public static String ReqReeditClaim() throws JSONException {
		JSONObject metadata = new JSONObject();
		metadata.put("method", "reedit_claim");

		JSONObject data = new JSONObject();
		data.put("nomor_sep", "0301R00112140006067");

		JSONObject obj = new JSONObject();
		obj.put("metadata", metadata);
		obj.put("data", data);

		System.out.println("Request : ");
		System.out.println(obj.toString());
		return obj.toString();
	}

	public static String ReqKirimDC() throws JSONException {
		JSONObject metadata = new JSONObject();
		metadata.put("method", "send_claim");

		JSONObject data = new JSONObject();
		data.put("start_dt", "2016-01-07");
		data.put("stop_dt", "2016-01-07");
		data.put("jenis_rawat", "1");

		JSONObject obj = new JSONObject();
		obj.put("metadata", metadata);
		obj.put("data", data);

		System.out.println("Request : ");
		System.out.println(obj.toString());
		return obj.toString();
	}

	public static String ReqPullClaim() throws JSONException {
		JSONObject metadata = new JSONObject();
		metadata.put("method", "pull_claim");

		JSONObject data = new JSONObject();
		data.put("start_dt", "2016-01-07");
		data.put("stop_dt", "2016-01-07");
		data.put("jenis_rawat", "1");

		JSONObject obj = new JSONObject();
		obj.put("metadata", metadata);
		obj.put("data", data);

		System.out.println("Request : ");
		System.out.println(obj.toString());
		return obj.toString();
	}

	public static String ReqGetClaim() throws JSONException {
		JSONObject metadata = new JSONObject();
		metadata.put("method", "get_claim_data");

		JSONObject data = new JSONObject();
		data.put("nomor_sep", "0301R00112140006067");

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
		
		System.out.println(postURL(ReqFinalClaim()));
		
//		System.out.println(postURL(ReqKirimDC()));
//		System.out.println(postURL(ReqPullClaim()));
//		System.out.println(postURL(ReqGetClaim()));
	}
	
}
