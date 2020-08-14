<?php
    require_once('conf/conf.php');
    $nopermintaan = isset($_GET["nopermintaan"])?$_GET["nopermintaan"]:NULL;
    $nopermintaan = str_replace("PL","",$nopermintaan);
    
    echo "no_lab=$nopermintaan";
    $curl = curl_init();
    curl_setopt_array($curl, array(
      CURLOPT_URL => "api.terassekawanbersama.co.id/hasil/",
      CURLOPT_RETURNTRANSFER => true,
      CURLOPT_ENCODING => "",
      CURLOPT_MAXREDIRS => 10,
      CURLOPT_TIMEOUT => 0,
      CURLOPT_FOLLOWLOCATION => true,
      CURLOPT_HTTP_VERSION => CURL_HTTP_VERSION_1_1,
      CURLOPT_CUSTOMREQUEST => "POST",
      CURLOPT_POSTFIELDS => array('no_lab' => $nopermintaan),
      CURLOPT_HTTPHEADER => array(
        "x-cid: 5309202024070000",
        "x-user: HIS_CILINCING",
        "x-secret: $3c!r3t",
        "x-token: 75E3128346CA72462BE7C3B6F4F5BA75"
      ),
    ));

    $response = curl_exec($curl);

    curl_close($curl);
    if(strpos(strtolower($response),'"status": 1')===true){
        echo"<meta http-equiv='refresh' content='1;URL=?json=".str_replace(" ","_",$response)."'>";
    }else{
        echo"<meta http-equiv='refresh' content='1;URL=?'>";
    }
 ?>