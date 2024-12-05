<?php
    require_once('../../conf/conf.php');
    session_start();
    
    if(file_exists(host()."/webapps/dokumenasuransi/pages/upload/".validTeks4($_SESSION["kdpj"],3).".jpeg")){
        @unlink(host()."/webapps/dokumenasuransi/pages/upload/".validTeks4($_SESSION["kdpj"],3).".jpeg");
    }
    
    $TglBerakhir    = validTeks4($_POST["TglBerakhir"],2);
    $BlnBerakhir    = validTeks4($_POST["BlnBerakhir"],2);
    $ThnBerakhir    = validTeks4($_POST["ThnBerakhir"],4);
    $img            = $_POST["image"];
    $folderPath     = "upload/";
    $image_parts    = explode(";base64,", $img);
    $image_type_aux = explode("image/", $image_parts[0]);
    $image_type     = $image_type_aux[1];
    $image_base64   = base64_decode($image_parts[1]);
    $fileName       = validTeks4($_SESSION["kdpj"],3).".jpeg";
    $file           = $folderPath . $fileName;
    file_put_contents($file, $image_base64);
    Ubah2("penjab_dokumen_kerjasama","photo='pages/upload/$fileName',kerjasama_berakhir='$ThnBerakhir-$BlnBerakhir-$TglBerakhir' where kd_pj='".validTeks4($_SESSION['kdpj'],3)."'");
?>
<html xmlns="http://www.w3.org/1999/xhtml">
<body>
    <center><br><br><font face='tahoma' size='4' color='#545454'>Proses uploud photo selesai, silahkan tutup browser ..!!  </font></center>
    <script type='text/javascript'>window.open('', '_self').close();window.open('','_self','').close();window.close();this.focus();self.opener=this;self.close();</script>
</body>
</html>
