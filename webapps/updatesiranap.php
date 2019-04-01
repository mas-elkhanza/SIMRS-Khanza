<?php
    //fitur update kamar aplicare ini adalah penyempurnaan dari kontribusi Mas Fauzan dari RSUK Kemayoran Jakarta Pusat
    require_once('conf/conf.php');
    
	function  updateSiranap(){
            $kodekelas=0;
            $koderuang=0;
            $namaruang=0;
            $kapasitas=0;
            $tersedia=0;
            $tersediapria=0;
            $tersediawanita=0;
            $tersediapriawanita=0;
            
            # seting koneksi webservices #
            $xrsid = "7303010";  # ID Rumah Sakit #
            $xpass = md5("12345"); # Password #
            $strURLSiranap = "http://sirs.yankes.kemkes.go.id/sirsservice/ranap";  
            date_default_timezone_set("Asia/Jakarta");
            $timestamp = strtotime(date("d-m-Y"));
            
            $_sql="select aplicare_ketersediaan_kamar.kode_kelas_aplicare,aplicare_ketersediaan_kamar.kd_bangsal,
                       bangsal.nm_bangsal,aplicare_ketersediaan_kamar.kelas,aplicare_ketersediaan_kamar.kapasitas,
                       aplicare_ketersediaan_kamar.tersedia,aplicare_ketersediaan_kamar.tersediapria,
                       aplicare_ketersediaan_kamar.tersediawanita,aplicare_ketersediaan_kamar.tersediapriawanita 
                       from aplicare_ketersediaan_kamar inner join bangsal on aplicare_ketersediaan_kamar.kd_bangsal=bangsal.kd_bangsal" ;  
            $hasil=bukaquery($_sql);
            $xmlStr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<xml>\n";                    
            while ($data = mysqli_fetch_array ($hasil)){
                $kodekelas=$data['kode_kelas_aplicare'];
                $koderuang=$data['kd_bangsal'];
                $namaruang=$data['nm_bangsal'];
                $kapasitas=getOne("select count(kd_kamar) from kamar where statusdata='1' and kelas='".$data['kelas']."' and kd_bangsal='".$data['kd_bangsal']."'");
                $tersedia=getOne("select count(kd_kamar) from kamar where statusdata='1' and kelas='".$data['kelas']."' and kd_bangsal='".$data['kd_bangsal']."' and status='KOSONG'");
                $tersediapria=$data['tersediapria'];
                $tersediawanita=$data['tersediawanita'];
                $tersediapriawanita=getOne("select count(kd_kamar) from kamar where statusdata='1' and kelas='".$data['kelas']."' and kd_bangsal='".$data['kd_bangsal']."' and status='KOSONG'");

                $xmlStr .= "<data>\n";
                $xmlStr .= "<kode_ruang>0000</kode_ruang>\n";
                $xmlStr .= "<tipe_pasien>0002</tipe_pasien>\n";
                $xmlStr .= "<total_TT>".$kapasitas."</total_TT>\n";
                $xmlStr .= "<terpakai_male>".($kapasitas-$tersediapria)."</terpakai_male>\n";
                $xmlStr .= "<terpakai_female>".($kapasitas-$tersediawanita)."</terpakai_female>\n";
                $xmlStr .= "<kosong_male>".$tersediapria."</kosong_male>\n";
                $xmlStr .= "<kosong_female>".$tersediawanita."</kosong_female>\n";
                $xmlStr .= "<waiting>0</waiting>\n";
                $xmlStr .= "<tgl_update>".$timestamp."</tgl_update>\n";
                $xmlStr .= "</data>\n";
            }
            $xmlStr .="</xml>\n";
            $curl = curl_init(); 
            curl_setopt($curl, CURLOPT_URL, $strURLSiranap);  
            curl_setopt($curl, CURLOPT_HTTPHEADER, Array(
                    "X-rs-id: $xrsid",
                    "X-pass:$xpass",
                    "Content-Type:application/xml; charset=ISO-8859-1",
                    "User-Agent: Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.15) Gecko/20080623 Firefox/2.0.0.15") 
            ); 
            curl_setopt($curl, CURLOPT_NOBODY, false);
            curl_setopt($curl, CURLOPT_FOLLOWLOCATION, true);
            curl_setopt($curl, CURLOPT_RETURNTRANSFER, true); 
            curl_setopt($curl, CURLOPT_POST, 1);
            curl_setopt($curl, CURLOPT_POSTFIELDS, $xmlStr);
            $str = curl_exec($curl);  
            curl_close($curl); 
            echo "respon : ".$str;
	}
        
        updateSiranap();
 ?>
