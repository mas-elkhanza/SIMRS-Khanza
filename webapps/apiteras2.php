<?php
    require_once('conf/conf.php');
    
    $curl = curl_init();
    curl_setopt_array($curl, array(
        CURLOPT_URL => "https://demo2.terassekawanbersama.co.id/ws/",
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_ENCODING => "",
        CURLOPT_MAXREDIRS => 10,
        CURLOPT_TIMEOUT => 0,
        CURLOPT_FOLLOWLOCATION => true,
        CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
        CURLOPT_CUSTOMREQUEST => "POST",
        CURLOPT_HTTPHEADER => array(
          "x-user: demo",
          "x-secret: 123qwe",
          "x-mod: auth",
          "x-cid: 4301202030080005"
        )
    ));

    $token        = json_decode(curl_exec($curl),true);
    curl_close($curl);
    $nopermintaan = isset($_GET["nopermintaan"])?$_GET["nopermintaan"]:NULL;
    $nopermintaan = str_replace("PL","",$nopermintaan);
    
    echo "no_lab=$nopermintaan";
    $curl2 = curl_init();
    curl_setopt_array($curl2, array(
        CURLOPT_URL => "https://demo2.terassekawanbersama.co.id/ws/",
        CURLOPT_RETURNTRANSFER => true,
        CURLOPT_ENCODING => "",
        CURLOPT_MAXREDIRS => 10,
        CURLOPT_TIMEOUT => 0,
        CURLOPT_FOLLOWLOCATION => true,
        CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
        CURLOPT_CUSTOMREQUEST => "POST",
        CURLOPT_HTTPHEADER => array(
          "x-token:$token[token]",
          "x-mod: get_hasil",
          "x-noo:$nopermintaan"
        )
    ));

    $response = curl_exec($curl2);
    
    curl_close($curl);
    if(strpos(strtolower($response),'"status": 1')===true){
        echo"<meta http-equiv='refresh' content='1;URL=?json=".str_replace(" ","_",$response)."'>";
    }
 ?>