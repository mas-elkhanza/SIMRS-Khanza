<?php
    //fitur update kamar aplicare ini adalah penyempurnaan dari kontribusi Mas Fauzan dari RSUK Kemayoran Jakarta Pusat
    require_once('conf/conf.php');
    $id = "7303010";  # ID Rumah Sakit #
    $pass = md5("12345"); # Password #
    $dt=new DateTime(null,new DateTimeZone("UTC"));
    $timestamp=$dt->getTimestamp();
    $key=$id."&".$timestamp;
    $signature= base64_encode(hash_hmac('sha256', utf8_encode($key),utf8_encode($pass), true));
    
    $url = "https://api.sisrute.kemkes.go.id/rujukan?tanggal=2019-01-25&create=true";  
    $method="GET";
    $postdata="";
    $header = array (
                'X-cons-id: '.$id,
                'X-Timestamp: '.$timestamp,
                'X-signature: '.$signature,
                'Content-type: application/json',
                'Content-length:'.strlen($postdata)
            );
    $curl= curl_init();
    curl_setopt($curl, CURLOPT_URL, $url);
    curl_setopt($curl, CURLOPT_HEADER, false);
    curl_setopt($curl, CURLOPT_CUSTOMREQUEST,$method);
    curl_setopt($curl, CURLOPT_POSTFIELDS,$postdata);
    curl_setopt($curl, CURLOPT_FOLLOWLOCATION,true);
    curl_setopt($curl, CURLOPT_SSL_VERIFYHOST,false);
    curl_setopt($curl, CURLOPT_SSL_VERIFYPEER,false);
    curl_setopt($curl, CURLOPT_HTTPHEADER,$header);
    $result = curl_exec($curl);  
    curl_close($curl); 
    echo $result;
	
 ?>
