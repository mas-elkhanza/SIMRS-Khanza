<?php  
$tgl = date('Y-m-d');

if($tgl == date('Y-m-d'))
{
	echo send_notification();
}

function send_notification ($tokens, $message)
{
 	$url = 'https://fcm.googleapis.com/fcm/send';
 
	 $msg = array
	 (
	 'body' => "$message",
	 'title' => "PUSH NOTIFICATION"
	 );

	 $fields = array
	 (
		 'registration_ids' => $tokens,
		 'notification' => $msg
	 );

	 $headers = array(
		 'Authorization:key = Your_AUTH_KEY_FIND_FROM_FIREBASE_CONSOLE',
		 'Content-Type: application/json'
	 );
 
	 $ch = curl_init();
	 curl_setopt($ch, CURLOPT_URL, $url);
	 curl_setopt($ch, CURLOPT_POST, true);
	 curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
	 curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	 curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);  
	 curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
	 curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
	 $result = curl_exec($ch);           
 
	 if ($result === FALSE) {
	 	die('Curl failed: ' . curl_error($ch));
	 }

 	curl_close($ch);
 	return $result;
}
?>