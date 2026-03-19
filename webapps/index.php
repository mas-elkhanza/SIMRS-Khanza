<?php
    header("X-Robots-Tag: noindex", true);
    session_start();
    require_once('conf/conf.php');
    header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
    header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
    header("Cache-Control: no-store, no-cache, must-revalidate"); 
    header("Cache-Control: post-check=0, pre-check=0", false);
    header("Pragma: no-cache"); // HTTP/1.0
    $action      = isset($_GET['aksi'])?$_GET['aksi']:NULL;
    if($action=="Keluar"){
        session_start();
        $_SESSION["ses_admin_login"]=null;
        unset($_SESSION["ses_admin_login"]); 
        session_destroy();
        exit(header("Location:index.php"));
    }
?>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="robots" content="noindex,nofollow">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>Edukasi, Konfirmasi & Persetujuan</title>
    <link href="css/login.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="conf/validator.js"></script>
    <script>
        function PopupCenter(pageURL, title,w,h) {
            var left = (screen.width/2)-(w/2);
            var top = (screen.height/2)-(h/2);
            var targetWin = window.open (pageURL, title, 'toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width='+w+', height='+h+', top='+top+', left='+left);        
        }
    </script>
</head>
<body>
    <div class="bg"></div>
    <div class="bg bg2"></div>
    <div class="bg bg3"></div>
    <div id="content">
        <?php 
           $sesilogin=isset($_SESSION['ses_admin_login'])?$_SESSION['ses_admin_login']:NULL;
           if ($sesilogin==USERHYBRIDWEB.PASHYBRIDWEB){
                echo "
                    <div id=\"navcontainer\">
                        <div style='width: 100%; height: 100%; overflow: auto;'> 
                            <table width='100%' align='center' height='100%'>
                                <tr width='100%' align='center'>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=persetujuanumum/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/5868931_architecture_building_coronavirus_hospital_corona_icon.png'/><br>
                                         Persetujuan Umum                                                  
                                      </a>
                                    </td>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=persetujuantindakan/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/6771569_education_learning_pencil_school_signature_icon.png'/><br>
                                         Persetujuan/Penolakan Tindakan                                                  
                                      </a>
                                    </td>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=perencanaanpemulangan/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/6141469_coronavirus_covid_covid19_hospital_infected_icon.png'/><br>
                                         Perencanaan Pemulangan Pasien                                                  
                                      </a>
                                    </td>
                                </tr>
                                <tr width='100%' align='center'>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=penyerahanresep/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/1360485894_add-notes.png'/><br>
                                         Penyerahan Resep Rawat Jalan                                                 
                                      </a>
                                    </td>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=pernyataanumum/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/Edit-Male-User.png'/><br>
                                         Pernyataan Pasien Umum                                                  
                                      </a>
                                    </td>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=pulangaps/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/5947112_clinic_doctor_healthcare_hospital_medical_icon.png'/><br>
                                         Pernyataan Pulang Atas Permintaan Sendiri                                                 
                                      </a>
                                    </td>
                                </tr>
                                <tr width='100%' align='center'>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=persetujuantransferruang/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/6009596_case_coronavirus_covid19_hospital_patient_icon.png'/><br>
                                         Persetujuan Transfer Antar Ruang                                                
                                      </a>
                                    </td>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=persetujuanrawatinap/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/5983455_bed_hospital_medical_patient_icon.png'/><br>
                                         Persetujuan Rawat Inap                                                
                                      </a>
                                    </td>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=penundaanpelayanan/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/9160890_clock_commerce_shopping_online_store_icon.png'/><br>
                                         Persetujuan Penundaan Pelayanan                                                
                                      </a>
                                    </td>
                                </tr>
                                <tr width='100%' align='center'>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=penolakananjuranmedis/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/8960611_hospitals_hospital_building_medic_health_icon.png'/><br>
                                         Penolakan Anjuran Medis                                               
                                      </a>
                                    </td>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=pengkajianrestrain/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/3841816_chain_hyperlink_interface_link_multimedia_icon.png'/><br>
                                         Persetujuan Restrain                                               
                                      </a>
                                    </td>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=pelaksanaanedukasi/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/11211449_book_library_learning_knowledge_education_icon.png'/><br>
                                         Bukti Pelaksanaan Informasi & Edukasi                                               
                                      </a>
                                    </td>
                                </tr>
                                <tr width='100%' align='center'>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=layanankedokteranfisikrehabilitasi/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/4082071_healthcare_hospital_medical_icon.png'/><br>
                                         Bukti Pelayanan Kedokteran Fisik & Rehabilitasi                                               
                                      </a>
                                    </td>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=layananprogramkfr/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/8960631_crutches_crutch_orthopedic_physiotherapy_rehabilitation_icon.png'/><br>
                                         Bukti Pelayanan Program KFR                                            
                                      </a>
                                    </td>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=persetujuanpemeriksaanhiv/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/6217201_corona_coronavirus_test_tube_virus_icon.png'/><br>
                                         Bukti Persetujuan Pemeriksaan HIV                                           
                                      </a>
                                    </td>
                                </tr>
                                <tr width='100%' align='center'>
                                    <td width='33%' align='center'>
                                      <a target=_blank href=pernyataanmemilihdpjp/login.php?iyem=".encrypt_decrypt("{\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">                                                 
                                         <img src='images/5898997_avatar_doctor_man_mask_user_icon.png'/><br>
                                         Pernyataan Memilih DPJP                                          
                                      </a>
                                    </td>
                                    <td width='33%' align='center'>
                                      <a href='?aksi=Keluar'>                                                 
                                         <img src='images/1360484978_application-pgp-signature.png'/><br>
                                         Keluar                                               
                                      </a>
                                    </td>
                                </tr>
                            </table> 
                        </div>
                    </div>";		
           }else{
               $BtnLogin=isset($_POST['BtnLogin'])?$_POST['BtnLogin']:NULL;
                if (isset($BtnLogin)) {
                    $usere      = validTeks4($_POST['usere'],30);
                    $passworde  = validTeks4($_POST['passworde'],30);
                    if(getOne("select count(admin.passworde) from admin where admin.usere=aes_encrypt('$usere','nur') and admin.passworde=aes_encrypt('$passworde','windi')")>0){
                        $_SESSION["ses_admin_login"]= USERHYBRIDWEB.PASHYBRIDWEB;
                        exit(header("Location:index.php"));
                    }else if(getOne("select count(user.password) from user where user.id_user=aes_encrypt('$usere','nur') and user.password=aes_encrypt('$passworde','windi')")>0){
                        $_SESSION["ses_admin_login"]= USERHYBRIDWEB.PASHYBRIDWEB;
                        exit(header("Location:index.php"));
                    }else{
                        echo "  <form id=\"pengenmasuk-form\" role=\"form\" onsubmit=\"return validasiIsi();\" method=\"post\" action=\"\" enctype=multipart/form-data>
                                    <table width='100%' align='center' height='100%' border='0'>
                                        <tr width='100%' align='center' height='100%' border='0'>
                                            <td width='100%' height='100%' alin='center' valign='middle' border='0'> 
                                                <table width='400px' align='center' height='100px' border='0'>
                                                    <tr width='100%' align='center' border='0'>
                                                        <td width='100%' align='center' border='0' class=\"text\">
                                                            <input type=\"password\" name=\"usere\" class=\"text\" pattern=\"[a-zA-Z0-9, ./_]{1,30}\" title=\" a-zA-Z0-9, ./_ (Maksimal 30 karakter)\" required placeholder=\"User Login\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi1'));\" id=\"TxtIsi1\" autocomplete=\"off\" size=\"17\" maxlength=\"30\" autofocus/>
                                                            <span id=\"MsgIsi1\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                        </td>
                                                    </tr>
                                                    <tr width='100%' align='center' border='0'>
                                                        <td width='100%' align='center' border='0' class=\"text\">
                                                            <input type=\"password\" name=\"passworde\" class=\"text\" pattern=\"[a-zA-Z0-9, ./_]{1,30}\" title=\" a-zA-Z0-9, ./_ (Maksimal 30 karakter)\" required placeholder=\"Password\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi2'));\" id=\"TxtIsi2\" autocomplete=\"off\" size=\"17\" maxlength=\"30\"/>
                                                            <span id=\"MsgIsi2\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                        </td>
                                                    </tr>
                                                    <tr width='100%' align='center' border='0'>
                                                        <td width='100%' border='0' class=\"text\">
                                                            <div align=\"center\"><input name=\"BtnLogin\" type=\"submit\" class=\"button\" value=\"Log-In\">&nbsp<input name=\"BtnKosong\" type=\"reset\" class=\"button\" value=\"Batal\"></div>
                                                        </td>
                                                    </tr>
                                                </table> 
                                            </td>
                                        </tr>
                                    </table>
                                </form>";		
                    }
                }else{
                    echo "  <form id=\"pengenmasuk-form\" role=\"form\" onsubmit=\"return validasiIsi();\" method=\"post\" action=\"\" enctype=multipart/form-data>
                                <table width='100%' align='center' height='100%' border='0'>
                                    <tr width='100%' align='center' height='100%' border='0'>
                                        <td width='100%' height='100%' alin='center' valign='middle' border='0'> 
                                            <table width='400px' align='center' height='100px' border='0'>
                                                <tr width='100%' align='center' border='0'>
                                                    <td width='100%' align='center' border='0' class=\"text\">
                                                        <input type=\"password\" name=\"usere\" class=\"text\" pattern=\"[a-zA-Z0-9, ./_]{1,30}\" title=\" a-zA-Z0-9, ./_ (Maksimal 30 karakter)\" required placeholder=\"User Login\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi1'));\" id=\"TxtIsi1\" autocomplete=\"off\" size=\"17\" maxlength=\"30\" autofocus/>
                                                        <span id=\"MsgIsi1\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                    </td>
                                                </tr>
                                                <tr width='100%' align='center' border='0'>
                                                    <td width='100%' align='center' border='0' class=\"text\">
                                                        <input type=\"password\" name=\"passworde\" class=\"text\" pattern=\"[a-zA-Z0-9, ./_]{1,30}\" title=\" a-zA-Z0-9, ./_ (Maksimal 30 karakter)\" required placeholder=\"Password\" onkeydown=\"setDefault(this, document.getElementById('MsgIsi2'));\" id=\"TxtIsi2\" autocomplete=\"off\" size=\"17\" maxlength=\"30\"/>
                                                        <span id=\"MsgIsi2\" style=\"color:#CC0000; font-size:10px;\"></span>
                                                    </td>
                                                </tr>
                                                <tr width='100%' align='center' border='0'>
                                                    <td width='100%' border='0' class=\"text\">
                                                        <div align=\"center\"><input name=\"BtnLogin\" type=\"submit\" class=\"button\" value=\"Log-In\">&nbsp<input name=\"BtnKosong\" type=\"reset\" class=\"button\" value=\"Batal\"></div>
                                                    </td>
                                                </tr>
                                            </table> 
                                        </td>
                                    </tr>
                                </table>
                            </form>";	
                }      
           }
        ?>
    </div>    
</body>
</html>
