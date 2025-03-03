<?php
    date_default_timezone_set('Asia/Jakarta');
    define('DB_HOST', 'localhost');
    define('DB_USER', 'root');
    define('DB_PASS', '');
    define('DB_NAME', 'sik');

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
