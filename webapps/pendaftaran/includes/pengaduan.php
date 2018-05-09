<?php

ob_start();
session_start();

include ('../config.php');


@$message = $_POST['message'];
 
if($message != "") {
 $sql = "INSERT INTO pengaduan VALUES('',current_time(), '$_SESSION[username]', '$message')";
 query($sql);
}
 
$sql = "SELECT * FROM pengaduan ORDER BY id ASC LIMIT 100";
$result = query($sql);
 
while($row = fetch_array($result))
 echo $row['username'].": ".$row['message']."\n";
 
 
?>
