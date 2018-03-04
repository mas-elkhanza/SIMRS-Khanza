<?php
    //fitur update kamar aplicare ini adalah penyempurnaan dari kontribusi Mas Fauzan dari RSUK Kemayoran Jakarta Pusat
    require_once('conf/conf.php');
    
	function  updateAplicare(){
            $kodekelas=0;
            $koderuang=0;
            $namaruang=0;
            $kapasitas=0;
            $tersedia=0;
            $tersediapria=0;
            $tersediawanita=0;
            $tersediapriawanita=0;
            $host="http://dvlp.bpjs-kesehatan.go.id:8888";
            $url = $host."/aplicaresws/rest/bed/update/".getOne("select kode_ppk from setting");				
            $session = curl_init ( $url );			
            $cid = "1000";
            $ckey = "1112";			
            date_default_timezone_set("Asia/Jakarta");
            $timestamp = strtotime(date("Y/m/d H:i:s"));
            $data = $cid."&".$timestamp;
            $signature = hash_hmac('sha256', $data, $ckey, true);
            $encodedSignature = base64_encode($signature);

            $arrheader = array (
                'X-cons-id: '.$cid,
                'X-Timestamp: '.$timestamp,
                'X-Signature: '.$encodedSignature,
                'Accept: application/json',
                'Content-Type: application/json'
            );

            $_sql="select aplicare_ketersediaan_kamar.kode_kelas_aplicare,aplicare_ketersediaan_kamar.kd_bangsal,
                       bangsal.nm_bangsal,aplicare_ketersediaan_kamar.kelas,aplicare_ketersediaan_kamar.kapasitas,
                       aplicare_ketersediaan_kamar.tersedia,aplicare_ketersediaan_kamar.tersediapria,
                       aplicare_ketersediaan_kamar.tersediawanita,aplicare_ketersediaan_kamar.tersediapriawanita 
                       from aplicare_ketersediaan_kamar inner join bangsal on aplicare_ketersediaan_kamar.kd_bangsal=bangsal.kd_bangsal" ;  
            $hasil=bukaquery($_sql);
            while ($data = mysqli_fetch_array ($hasil)){
                $kodekelas=$data['kode_kelas_aplicare'];
                $koderuang=$data['kd_bangsal'];
                $namaruang=$data['nm_bangsal'];
                $kapasitas=getOne("select count(kd_kamar) from kamar where statusdata='1' and kelas='".$data['kelas']."' and kd_bangsal='".$data['kd_bangsal']."'");
                $tersedia=getOne("select count(kd_kamar) from kamar where statusdata='1' and kelas='".$data['kelas']."' and kd_bangsal='".$data['kd_bangsal']."' and status='KOSONG'");
                $tersediapria=$data['tersediapria'];
                $tersediawanita=$data['tersediawanita'];
                $tersediapriawanita=getOne("select count(kd_kamar) from kamar where statusdata='1' and kelas='".$data['kelas']."' and kd_bangsal='".$data['kd_bangsal']."' and status='KOSONG'");

                Ubah2(" aplicare_ketersediaan_kamar "," kapasitas='".$kapasitas."',tersedia='".$tersedia."',tersediawanita='".$tersediawanita."' WHERE kode_kelas_aplicare='".$data['kode_kelas_aplicare']."' and kd_bangsal='".$data['kd_bangsal']."' and kelas='".$data['kelas']."' ");

                $myvars="";
                $item = array(
                    'kodekelas'=>$kodekelas,
                    'koderuang'=>$koderuang,
                    'namaruang'=>$namaruang,
                    'kapasitas'=>$kapasitas,
                    'tersedia'=>$tersedia,
                    'tersediapria'=>$tersediapria,
                    'tersediawanita'=>$tersediawanita,
                    'tersediapriawanita'=>$tersediapriawanita
                );

                $myvars = json_encode($item);	
                curl_setopt ( $session, CURLOPT_URL, $url );
                curl_setopt ( $session, CURLOPT_HTTPHEADER, $arrheader );
                curl_setopt ( $session, CURLOPT_VERBOSE, true );
                curl_setopt ( $session, CURLOPT_SSL_VERIFYPEER, false);
                curl_setopt ( $session, CURLOPT_SSL_VERIFYHOST, false);
                curl_setopt ( $session, CURLOPT_POST, true );
                curl_setopt ( $session, CURLOPT_POSTFIELDS, $myvars );
                curl_setopt ( $session, CURLOPT_RETURNTRANSFER, TRUE );
                $response = curl_exec ( $session );
                echo "Response : ".$response;
            }		
	}
 ?>
