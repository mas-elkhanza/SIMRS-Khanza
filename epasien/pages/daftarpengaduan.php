<?php
    ob_start();
    session_start();
    require_once('../conf/conf.php');
?>

<table class="table table-hover dataTable">
    <body>
    <?php 
        $sqlpengaduan    = "SELECT DATE_FORMAT(pengaduan.tanggal,'%d/%m/%y %H:%i:%s')as tanggal,pengaduan.pesan,pengaduan.id FROM pengaduan where pengaduan.no_rkm_medis='".encrypt_decrypt($_SESSION["ses_pasien"],"d")."' ORDER BY tanggal limit 30";
        $resultpengaduan = bukaquery2($sqlpengaduan);
        while($rowpengaduan = mysqli_fetch_array($resultpengaduan)) {
            echo "<tr class='col-blue-grey'>
                   <td align='left'>".$rowpengaduan["pesan"]."<br/>(".$rowpengaduan["tanggal"].")</td>
                 </tr>";
            $balasan=getOne("select pesan_balasan from balasan_pengaduan where id_pengaduan='".$rowpengaduan["id"]."'");
            if(!empty($balasan)){
                echo "<tr class='col-pink'>
                        <td align='right'>$balasan</td>
                      </tr>";
            }
        }

        if(mysqli_num_rows($resultpengaduan)==0){
            echo "<tr class='col-blue-grey'>
                   <td align='center'>Kosong</td>
                 </tr>";
        }
    ?>
    </body>
</table>
    

