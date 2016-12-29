<?php
//include_once("koneksi.php");

$hasil=$_POST['res'];
/*
foreach($hasil as $key){
$query=mysql_query("INSERT INTO `user` (
`id` ,
`nama`
)
VALUES (
NULL , '$key'
);");
}
*/
echo json_encode($hasil);
//print_r($hasil);
?>