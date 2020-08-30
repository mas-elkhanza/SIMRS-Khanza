<?php
    ob_start();
    session_start();
    require_once('../conf/conf.php');
    
    switch (URUTNOREG) {
        case "poli" : 
            echo "<p class='col-pink'>".getOne("select count(no_rawat) from reg_periksa where stts = 'Belum' and kd_poli='".$_SESSION["kd_poli"]."' and tgl_registrasi='".$_SESSION["tgl_registrasi"]."' and no_reg < '".$_SESSION["no_reg"]."' ")." Antrian Lagi</p>";
            break;
        case "dokter" : 
            echo "<p class='col-pink'>".getOne("select count(no_rawat) from reg_periksa where stts = 'Belum' and kd_dokter='".$_SESSION["kd_dokter"]."' and tgl_registrasi='".$_SESSION["tgl_registrasi"]."' and no_reg < '".$_SESSION["no_reg"]."'")." Antrian Lagi</p>";
            break;
        case "dokter + poli" : 
            echo "<p class='col-pink'>".getOne("select count(no_rawat) from reg_periksa where stts = 'Belum' and kd_poli='".$_SESSION["kd_poli"]."' and kd_dokter='".$_SESSION["kd_dokter"]."' and tgl_registrasi='".$_SESSION["tgl_registrasi"]."' and no_reg < '".$_SESSION["no_reg"]."'")." Antrian Lagi</p>";
            break;
        default : 
            echo "<p class='col-pink'>".getOne("select count(no_rawat) from reg_periksa where stts = 'Belum' and kd_dokter='".$_SESSION["kd_dokter"]."' and tgl_registrasi='".$_SESSION["tgl_registrasi"]."' and no_reg < '".$_SESSION["no_reg"]."'")." Antrian Lagi</p>";
            break;
    }
?>
    

