<?php
include ('config.php');
if(!empty($_POST['kd_poli'])){
    $data = array();

    $query = $db->query("SELECT nm_poli FROM poliklinik WHERE kd_poli='$_POST[kd_poli]'");

    if($query->num_rows > 0){
        $userData = $query->fetch_assoc();
        $data['status'] = 'ok';
        $data['result'] = $userData;
    }else{
        $data['status'] = 'err';
        $data['result'] = '';
    }

    //returns data as JSON format
    echo json_encode($data);

}
?>
