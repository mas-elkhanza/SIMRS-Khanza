<?php
    date_default_timezone_set('Asia/Jakarta');
    define('DB_HOST', 'localhost');
    define('DB_USER', 'root');
    define('DB_PASS', '');
    define('DB_NAME', 'sik');
    $akunmandiri=fetch_array(bukaquery("select aes_decrypt(username,'nur') as username,aes_decrypt(set_akun_mandiri.password,'windi') as password,aes_decrypt(set_akun_mandiri.client_id,'nur') as client_id,aes_decrypt(set_akun_mandiri.client_secret,'windi') as client_secret FROM set_akun_mandiri"));
    @define('USERNAME', $akunmandiri['username']);
    @define('PASSWORD', $akunmandiri['password']);
    @define('CLIENTID', $akunmandiri['client_id']);
    @define('CLIENTSECRET', $akunmandiri['client_secret']);

    function bukakoneksi() {
        $response = array(
            'metadata' => array(
                'titile' => 'Configurasi Not Found !',
                'message' => 'Anda Belum Melakukan Configurasi !',
                'code' => 404
            )
        );
        $konektor = mysqli_connect(DB_HOST, DB_USER, DB_PASS, DB_NAME) or die("" . json_encode($response, true) . "");
        return $konektor;
    }
    
    function cleankar($dirty){
        $konektor=bukakoneksi();
	$clean = mysqli_real_escape_string($konektor,$dirty);	
	mysqli_close($konektor);
	return preg_replace('/[^a-zA-Z0-9\s_,@. ]/', '',$clean);
    }

    function fetch_array($sql){
        $while = mysqli_fetch_array($sql);
        return $while;
    }

    function fetch_assoc($sql){
        $while = mysqli_fetch_assoc($sql);
        return $while;
    }

    function num_rows($sql){
        $while = mysqli_num_rows($sql);
        return $while;
    }

    function tutupkoneksi(){
        global $konektor;
        mysqli_close($konektor);
    }

    function bukaquery2($sql){
        $konektor = bukakoneksi();
        $result = mysqli_query($konektor,$sql);
        mysqli_close($konektor);
        return $result;
    }

    function bukaquery3($sql){
        $konektor = bukakoneksi();
        mysqli_query($konektor,$sql);
        mysqli_close($konektor);
    }
    
    function getOne2($sql) {
        $hasil = bukaquery2($sql);
        list($result) = mysqli_fetch_array($hasil);
        return $result;
    }

    function bukaquery($sql) {
        $konektor = bukakoneksi();
        $response = array(
            'metadata' => array(
                'message' => 'Data Sudah Pernah Di Buat Atau Sudah Ada !',
                'code' => 201
            )
        );
        http_response_code(201);
        $result = mysqli_query($konektor, $sql) or die(mysqli_error($konektor)."".json_encode($response)."");
        mysqli_close($konektor);
        return $result;
    }
    
    function JumlahBaris($result) {
        return mysqli_num_rows($result);
    }
    
    function bukainput($sql) {
        $konektor = bukakoneksi();
        $result = mysqli_query($konektor, $sql) or die("Gagal menjalankan query !");
        mysqli_close($konektor);
        return $result;
    }
    
    function getOne($sql){
        $hasil = bukaquery($sql);
        list($result) = fetch_array($hasil);
        return $result;
    }

    function escape($string){
        $konektor = bukakoneksi();
        $result = mysqli_real_escape_string($konektor, $string);
        mysqli_close($konektor);
        return $result;
    }

    function noRegPoli($kd_poli, $tanggal) {
        $max    = getOne("select ifnull(MAX(CONVERT(no_reg,signed)),0)+1 from reg_periksa where kd_poli='$kd_poli' and tgl_registrasi='$tanggal'");
        $no_reg = sprintf("%03s", $max);
        return $no_reg;
    }

    function FormatTgl($format, $tanggal){
        return date($format, strtotime($tanggal));
    }

    function hariindo($x){
        $hari = FormatTgl("D", $x);
        switch ($hari) {
            case 'Sun':
                $hari_ini = "Akhad";
                break;
            case 'Mon':
                $hari_ini = "Senin";
                break;
            case 'Tue':
                $hari_ini = "Selasa";
                break;
            case 'Wed':
                $hari_ini = "Rabu";
                break;
            case 'Thu':
                $hari_ini = "Kamis";
                break;
            case 'Fri':
                $hari_ini = "Jumat";
                break;
            case 'Sat':
                $hari_ini = "Sabtu";
                break;
            default:
                $hari_ini = "Tidak di ketahui";
                break;
        }

        return $hari_ini;
    }

    function hash_pass($pass, $int){
        $options = ['cost' => $int];
        return password_hash($pass, PASSWORD_DEFAULT, $options);
    }
    
    function query($sql) {
        global $connection;
        $query = mysqli_query($connection, $sql);
        confirm($query);
        return $query;
    }
    
    function getToken() {
        global $username, $password;
        $header = json_encode(['alg' => 'RS256','typ' => 'JWT']);
        $payload = json_encode(['username' => USERNAME, 'password' => PASSWORD, 'date' => strtotime(date('Y-m-d H:')) * 1000]);
        $base64UrlHeader = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($header));
        $base64UrlPayload = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($payload));
        $signature = hash_hmac('sha256', $base64UrlHeader . "." . $base64UrlPayload, 'b155m1774H', true);
        $base64UrlSignature = str_replace(['+', '/', '='], ['-', '_', ''], base64_encode($signature));
        $jwt = $base64UrlHeader . "." . $base64UrlPayload . "." . $base64UrlSignature;
        return $jwt;
    }

    function algoritm($alg){
        $supported_algs = array(
            'ES256' => array('openssl', 'SHA256'),
            'RS256' => array('hash_hmac', 'SHA256'),
            'HS384' => array('hash_hmac', 'SHA384'),
            'HS512' => array('hash_hmac', 'SHA512'),
            'HS256' => array('openssl', 'SHA256'),
            'RS384' => array('openssl', 'SHA384'),
            'RS512' => array('openssl', 'SHA512'),
        );  
        return $supported_algs[$alg];
    }
    
    function urlsafeB64Encode($input){
        return str_replace(['+/', '='], ['-_', ''], base64_encode($input));
    }
    
    function urlsafeB64Decode($input){
        return str_replace(['-_', ''],['+/',  '='], base64_decode($input));
    }
    
    function signnature($msg, $key, $alg = 'RS256'){
        list($function, $algorithm)=algoritm($alg);
        switch ($function) {
            case 'hash_hmac':
                return hash_hmac($algorithm, $msg, $key, true);
        } 
    }
    
    function verify($msg, $signature, $key, $alg){
        if (empty(algoritm($alg))) {
            throw new DomainException('Algorithm not supported');
        }

        list($function, $algorithm) = algoritm($alg);
        switch ($function) {
            case 'hash_hmac':
            default:
                $hash = hash_hmac($algorithm, $msg, $key, true);
                if (function_exists('hash_equals')) {
                    return hash_equals($signature, $hash);
                }
                $len = min(strlen($signature), strlen($hash));

                $status = 0;
                for ($i = 0; $i < $len; $i++) {
                    $status |= (\ord($signature[$i]) ^ \ord($hash[$i]));
                }
                $status |= (strlen($signature) ^ strlen($hash));

                return ($status === 0);
        }
    }

    function encode_jwt($payload,$key,$alg = 'RS256'){
        $header = json_encode(['alg' => $alg,'typ' => 'JWT']);
        $payload = json_encode($payload);
        $segments = array();
        $segments[] = urlsafeB64Encode($header);
        $segments[] = urlsafeB64Encode($payload);
        $sign_input = implode('.',$segments);
        $signature = signnature($sign_input,$key,$alg);
        $segments[] = urlsafeB64Encode($signature);
        return implode('.',$segments);
    }

    function decode_jwt($token,$key,array $allowed_algs = array()){
        if (empty($key)) {
            throw new InvalidArgumentException('Key may not be empty');
        }
        
        $tks = explode('.', $token);
        if (count($tks) != 3) {
             throw new UnexpectedValueException('Wrong number of segments');
        }
        
        list($headb64, $bodyb64, $cryptob64) = $tks;
        $header =json_decode(urlsafeB64Decode($headb64));
        $payload=json_decode(urlsafeB64Decode($bodyb64));   

        if (null === ($header = json_decode(urlsafeB64Decode($headb64)))) {
            throw new UnexpectedValueException('Invalid header encoding');
        }
        
        if (null === $payload = json_decode(urlsafeB64Decode($bodyb64))) {
            throw new UnexpectedValueException('Invalid claims encoding');
        }
        
        if (false === ($sig = urlsafeB64Decode($cryptob64))) {
            throw new UnexpectedValueException('Invalid signature encoding');
        }
        
        if (empty($header->alg)) {
            throw new UnexpectedValueException('Empty algorithm');
        }
        
        if (empty(algoritm($header->alg))) {
            throw new UnexpectedValueException('Algorithm not supported');
        }
        
        if (!in_array($header->alg, $allowed_algs)) {
            throw new UnexpectedValueException('Algorithm not allowed');
        }
        
        // Check the signature
        if (!verify("$headb64.$bodyb64", $sig, $key, $header->alg)) {
            throw new UnexpectedValueException('Signature verification failed');
        }
        
        // Check if this token has expired.
        if (isset($payload->exp) && (time()-$payload->iat) >= $payload->exp) {
            throw new UnexpectedValueException('Expired token');
        }
        
        return $payload;
    }
    
    function cekuser($username,$password){   
        $cek=false;
        if((!empty($username)) && (!empty($password)) &&(USERNAME==$username) && (PASSWORD==$password)){
            $cek=true;
        }else{
            $cek=false;
        }
        return $cek;
    }

    function createtoken(){   
        $gtoken=encode_jwt(payloadtoken(),privateKey());
        return $gtoken;
    }
    
    function cektoken($token){
        try{
            if (decode_jwt($token,privateKey(),['typ' => 'JWT', 'alg' => 'RS256'])) {
                $response =TRUE;
                return $response;
            } 
        }catch(Exception $e){
            $response = array(
                'metadata' => array(
                    'message' => $e->getMessage(),
                    'code' => 201
                )
            );
            http_response_code(201);
            return $response;
        }
    }
    
    /* ---------------------- Configurasi TOKEN Information ----------------*/
    function privateKey(){
        $key = '123!!abc**';
        return $key;
    }

    function payloadtoken(){
        $data_array[] = array(
            'name' => 'role_admin',
            'permissions' => []
        );
        $token = array(
            "aud" => array(
                "USER_CLIENT_RESOURCE",
                "USER_ADMIN_RESOURCE"
            ),
            'role' => (
                $data_array
            ),
            "user_name" => USERNAME,
            "scope" => array(
                "role_admin"
            ),
            "iat" => time(), //time create Token
            "exp" => 899, //5 menit {second time} 
            "authorities" => array(
                "role_admin"
            ),
            'jti' => generate_uuid(),
            'email' => 'mhas@mandiri.co.id',
            'client_id' => getOne2("select aes_decrypt(set_akun_mandiri.client_id,'nur') from set_akun_mandiri"),
            "username" => USERNAME
        );
        return $token;
    }

    function validTeks($data){
        $save=str_replace("'","",$data);
        $save=str_replace("\\","",$save);
        $save=str_replace(";","",$save);
        $save=str_replace("`","",$save);
        $save=str_replace("--","",$save);
        $save=str_replace("/*","",$save);
        $save=str_replace("*/","",$save);
        $save=str_replace("text/html","",$save);
        $save=str_replace("<script>","",$save);
        $save=str_replace("</script>","",$save);
        $save=str_replace("<noscript>","",$save);
        $save=str_replace("</noscript>","",$save);
        $save=str_replace("<img","",$save);
        $save=str_replace("document","",$save);
        $save=str_replace(" from ","",$save);
        $save=str_replace("concat","",$save);
        $save=str_replace("union","",$save);
        $save=str_replace("base64","",$save);
        $save=str_replace("//","",$save);
        $save=str_replace("*","",$save);
        $save=str_replace("}","",$save);
        $save=str_replace("$","",$save);
        $save=str_replace("{","",$save);
        $save=str_replace("@","",$save);
        $save=str_replace("[","",$save);
        $save=str_replace("]","",$save);
        $save=str_replace("(","",$save);
        $save=str_replace(")","",$save);
        $save=str_replace("|","",$save);
        $save=str_replace(",","",$save);
        $save=str_replace("<","",$save);
        $save=str_replace(">","",$save);
        $save=str_replace(":","",$save);
        $save=str_replace("+","",$save);
        $save=str_replace("^","",$save);
        $save=str_replace("#","",$save);
        $save=str_replace("!","",$save);
        $save=str_replace("='","",$save);
        $save=str_replace("=/","",$save);
        $save=str_replace("=","",$save);
        $save=str_replace("password","",$save);
        $save=str_replace("submit","",$save);
        $save=str_replace("input","",$save);
        $save=str_replace("meta","",$save);
        $save=str_replace("md5","",$save);
        $save=str_replace("pass","",$save);
        $save=str_replace("SESSION","",$save);
        $save=str_replace("login_shell","",$save);
        $save=str_replace("value","",$save);
        return $save;
    }
    
    function validTeks2($data){
        $save=str_replace("'","",$data);
        $save=str_replace("\\","",$save);
        $save=str_replace(";","",$save);
        $save=str_replace("`","",$save);
        $save=str_replace("--","",$save);
        $save=str_replace("/*","",$save);
        $save=str_replace("*/","",$save);
        $save=str_replace("text/html","",$save);
        $save=str_replace("<script>","",$save);
        $save=str_replace("</script>","",$save);
        $save=str_replace("<noscript>","",$save);
        $save=str_replace("</noscript>","",$save);
        $save=str_replace("<img","",$save);
        $save=str_replace("document","",$save);
        $save=str_replace(" from ","",$save);
        $save=str_replace("concat","",$save);
        $save=str_replace("union","",$save);
        $save=str_replace("base64","",$save);
        $save=str_replace("//","",$save);
        $save=str_replace("*","",$save);
        $save=str_replace("}","",$save);
        $save=str_replace("$","",$save);
        $save=str_replace("{","",$save);
        $save=str_replace("@","",$save);
        $save=str_replace("[","",$save);
        $save=str_replace("]","",$save);
        $save=str_replace("(","",$save);
        $save=str_replace(")","",$save);
        $save=str_replace("|","",$save);
        $save=str_replace(",","",$save);
        $save=str_replace("<","",$save);
        $save=str_replace(">","",$save);
        $save=str_replace("+","",$save);
        $save=str_replace("^","",$save);
        $save=str_replace("#","",$save);
        $save=str_replace("!","",$save);
        $save=str_replace("='","",$save);
        $save=str_replace("=/","",$save);
        $save=str_replace("=","",$save);
        $save=str_replace("password","",$save);
        $save=str_replace("submit","",$save);
        $save=str_replace("input","",$save);
        $save=str_replace("meta","",$save);
        $save=str_replace("md5","",$save);
        $save=str_replace("pass","",$save);
        $save=str_replace("SESSION","",$save);
        $save=str_replace("login_shell","",$save);
        $save=str_replace("value","",$save);
        return $save;
    }
    
    function generate_uuid() {
	return sprintf( '%04x%04x-%04x-%04x-%04x-%04x%04x%04x',
		mt_rand( 0, 0xffff ), mt_rand( 0, 0xffff ),
		mt_rand( 0, 0xffff ),
		mt_rand( 0, 0x0fff ) | 0x4000,
		mt_rand( 0, 0x3fff ) | 0x8000,
		mt_rand( 0, 0xffff ), mt_rand( 0, 0xffff ), mt_rand( 0, 0xffff )
	);
    }
    
    function validTeks3($data,$panjang){
        $save="";
        if(strlen($data)>$panjang){
            header('Location: https://www.google.com');
        }else{
            $save=str_replace("'","",$data);
            $save=str_replace("\\","",$save);
            $save=str_replace(";","",$save);
            $save=str_replace("`","",$save);
            $save=str_replace("--","",$save);
            $save=str_replace("/*","",$save);
            $save=str_replace("*/","",$save);
            $save=str_replace("text/html","",$save);
            $save=str_replace("<script>","",$save);
            $save=str_replace("</script>","",$save);
            $save=str_replace("<noscript>","",$save);
            $save=str_replace("</noscript>","",$save);
            $save=str_replace("<img","",$save);
            $save=str_replace("document","",$save);
            $save=str_replace(" from ","",$save);
            $save=str_replace("concat","",$save);
            $save=str_replace("union","",$save);
            $save=str_replace("base64","",$save);
            $save=str_replace("//","",$save);
            $save=str_replace("*","",$save);
            $save=str_replace("}","",$save);
            $save=str_replace("$","",$save);
            $save=str_replace("{","",$save);
            $save=str_replace("@","",$save);
            $save=str_replace("[","",$save);
            $save=str_replace("]","",$save);
            $save=str_replace("(","",$save);
            $save=str_replace(")","",$save);
            $save=str_replace("|","",$save);
            $save=str_replace(",","",$save);
            $save=str_replace("<","",$save);
            $save=str_replace(">","",$save);
            $save=str_replace(":","",$save);
            $save=str_replace("+","",$save);
            $save=str_replace("^","",$save);
            $save=str_replace("#","",$save);
            $save=str_replace("!","",$save);
            $save=str_replace("='","",$save);
            $save=str_replace("=/","",$save);
            $save=str_replace("=","",$save);
            $save=str_replace("password","",$save);
            $save=str_replace("submit","",$save);
            $save=str_replace("input","",$save);
            $save=str_replace("meta","",$save);
            $save=str_replace("md5","",$save);
            $save=str_replace("pass","",$save);
            $save=str_replace("SESSION","",$save);
            $save=str_replace("login_shell","",$save);
            $save=str_replace("value","",$save);
        }
        return $save;
    }
    
    function validTeks4($data,$panjang){
        $save="";
        if(strlen($data)>$panjang){
            header('Location: https://www.google.com');
        }else{
            $save=str_replace("'","",$data);
            $save=str_replace("\\","",$save);
            $save=str_replace(";","",$save);
            $save=str_replace("`","",$save);
            $save=str_replace("--","",$save);
            $save=str_replace("/*","",$save);
            $save=str_replace("*/","",$save);
            $save=str_replace("text/html","",$save);
            $save=str_replace("<script>","",$save);
            $save=str_replace("</script>","",$save);
            $save=str_replace("<noscript>","",$save);
            $save=str_replace("</noscript>","",$save);
            $save=str_replace("<img","",$save);
            $save=str_replace("document","",$save);
            $save=str_replace(" from ","",$save);
            $save=str_replace("concat","",$save);
            $save=str_replace("union","",$save);
            $save=str_replace("base64","",$save);
            $save=str_replace("//","",$save);
            $save=str_replace("*","",$save);
            $save=str_replace("}","",$save);
            $save=str_replace("$","",$save);
            $save=str_replace("{","",$save);
            $save=str_replace("@","",$save);
            $save=str_replace("[","",$save);
            $save=str_replace("]","",$save);
            $save=str_replace("(","",$save);
            $save=str_replace(")","",$save);
            $save=str_replace("|","",$save);
            $save=str_replace(",","",$save);
            $save=str_replace("<","",$save);
            $save=str_replace(">","",$save);
            $save=str_replace("+","",$save);
            $save=str_replace("^","",$save);
            $save=str_replace("#","",$save);
            $save=str_replace("!","",$save);
            $save=str_replace("='","",$save);
            $save=str_replace("=/","",$save);
            $save=str_replace("=","",$save);
            $save=str_replace("password","",$save);
            $save=str_replace("submit","",$save);
            $save=str_replace("input","",$save);
            $save=str_replace("meta","",$save);
            $save=str_replace("md5","",$save);
            $save=str_replace("pass","",$save);
            $save=str_replace("SESSION","",$save);
            $save=str_replace("login_shell","",$save);
            $save=str_replace("value","",$save);
        }
        return $save;
    }
    
    date_default_timezone_set('Asia/Jakarta');
    $month      = date('Y-m');
    $date       = date('Y-m-d');
    $time       = date('H:i:s');
    $date_time  = date('Y-m-d H:i:s');
?>
