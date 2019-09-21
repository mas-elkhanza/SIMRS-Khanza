<?php
include ('config.php');
if(!empty($_POST['no_rawat'])){
    $data = array();

    //get data from the database
    $query = $db->query("SELECT * FROM bridging_sep WHERE no_rawat = '{$_POST['no_rawat']}'");

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
