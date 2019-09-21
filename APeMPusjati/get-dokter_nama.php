<?php
include ('config.php');
if(!empty($_POST['kd_dokter'])){
    $data = array();

    $query = $db->query("SELECT nm_dokter FROM dokter WHERE kd_dokter='$_POST[kd_dokter]'");

    if($query->num_rows > 0){
      $userData = $query->fetch_assoc();
      $data['status'] = 'ok';
      $data['result'] = $userData;
      echo json_encode($data);

    }

}
?>
