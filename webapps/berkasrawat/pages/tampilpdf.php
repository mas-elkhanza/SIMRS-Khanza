<?php
     require_once("../../conf/conf.php");
     header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
     header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
     header("Cache-Control: no-store, no-cache, must-revalidate"); 
     header("Cache-Control: post-check=0, pre-check=0", false);
     header("Pragma: no-cache"); // HTTP/1.0
?>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="../css/default.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tbl_form">
    <?php
        $no_rawat=isset($_GET['norawat'])?$_GET['norawat']:NULL;
        $_sql2 = "SELECT berkas_digital_perawatan.no_rawat,berkas_digital_perawatan.kode, 
                  master_berkas_digital.nama,berkas_digital_perawatan.lokasi_file 
                  from berkas_digital_perawatan inner join master_berkas_digital 
                  on berkas_digital_perawatan.kode=master_berkas_digital.kode 
                  where berkas_digital_perawatan.no_rawat='$no_rawat' ORDER BY master_berkas_digital.nama ASC ";
        $hasil2=bukaquery($_sql2);
        $no=1;
        while($baris2 = mysqli_fetch_array($hasil2)) { 
            echo "<tr class='isi8'> 
                    <td width='99%'>
                        <object data='../".$baris2["lokasi_file"]."' type='application/pdf' width='100%' height='730px'>
                        Tidak suport pdf, Silakan download <a href='../".$baris2["lokasi_file"]."'>Download PDF</a>
                        </object>
                    </td>
                  </tr>";
        }
    ?>                                
    </table>    
</body>
</html>

