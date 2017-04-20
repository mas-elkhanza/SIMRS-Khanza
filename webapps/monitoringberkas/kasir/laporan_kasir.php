<?php
//untuk koneksi database
include "../koneksi.php";

?>

<!DOCTYPE html>
<html>
<head>
  <title></title>
  <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>

<body>
<div id="header"><h><b>&ensp;&ensp;RUMAH SAKIT UMUM DAERAH CIRACAS</b></h></div>
<div id="ket"><h><b>&ensp;&ensp;&ensp;Unit Kasir &ensp;<span style="color: blue">< Laporan Kasir ></span></b><ul><a href="../index.php">Home</a> &nbsp;&nbsp;<a href="Kasir.php">Kasir</a></ul></h></div>
<div id="body">
      <div id="kontent">
          <?php
            $query = mysql_query ("SELECT
                      sik.tagihan_sadewa.no_nota,
                      sik.tagihan_sadewa.no_rkm_medis,
                      sik.tagihan_sadewa.alamat,
                      sik.tagihan_sadewa.tgl_bayar
                    FROM
                      sik.tagihan_sadewa
                    ORDER BY
                      sik.tagihan_sadewa.tgl_bayar DESC");
          ?> 

          <table border="1">
            <tr>
              <td width="30%" align="center">No Nota</td>
              <td width="30%" align="center">No RM</td>
              <td width="30%" align="center">No RM</td>
              <td width="30%" align="center">001</td>
              <td width="30%" align="center">003</td>
            </tr>
      

            <?php  
            while($row = mysql_fetch_array($query))  
            {  
            ?>  
            <tr>  
              <td align="center"><?php echo $row["no_nota"]; ?></td>  
              <td align="center"><?php echo $row['no_rkm_medis']; ?></td> 
              <td><?php $tampilnama  =mysql_query("SELECT * from pasien WHERE no_rkm_medis='$row[no_rkm_medis]'");
                    $nama  =mysql_fetch_array($tampilnama);
                    echo $nama['nm_pasien']
                  ?></td> 
              <td><?php $tampil001  =mysql_query("SELECT
                                    sik.reg_periksa.no_rawat,
                                    sik.rek_ciracas.nama_rek,
                                    sik.reg_periksa.biaya_reg
                                  FROM
                                    sik.reg_periksa,
                                    sik.rek_ciracas,
                                    sik.poliklinik
                                  WHERE
                                    sik.poliklinik.kd_poli = sik.reg_periksa.kd_poli AND
                                    sik.rek_ciracas.kode_rek = sik.poliklinik.kode_rek AND no_rawat='$row[no_nota]'");
                    $rek001  =mysql_fetch_array($tampil001); { if ($rek001['nama_rek']=='001') {
                      echo $rek001['biaya_reg'];
                    } else {
                      echo $rek001='0';
                    }
                    }
                  ?></td>

               <td><?php $tampil003  =mysql_query("SELECT
                                    sik.rawat_jl_dr.no_rawat,
                                    sik.rawat_jl_dr.kd_jenis_prw,
                                    sik.jns_perawatan.tarif_tindakandr,
                                    sik.rek_ciracas.nama_rek
                                  FROM
                                    sik.jns_perawatan,
                                    sik.rawat_jl_dr,
                                    sik.rek_ciracas
                                  WHERE
                                    sik.jns_perawatan.kd_jenis_prw = sik.rawat_jl_dr.kd_jenis_prw AND
                                    sik.jns_perawatan.kode_rek = sik.rek_ciracas.kode_rek AND no_rawat='$row[no_nota]'");
                    $rek003  =mysql_fetch_array($tampil003); { if ($rek003['nama_rek']=='003') {
                      echo $rek003['tarif_tindakandr'];
                    } else {
                      echo $rek003='0';
                    }
                    }
                  ?></td>  
                             
            </tr>  
            <?php  
            }  
            ?>  
            </table>     
        
      </div>
  
</div>

<div id="footer"><p>2017</p></div>

</body>
</html>