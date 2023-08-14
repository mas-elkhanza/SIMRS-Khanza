<?php
    header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanAkte.xls");
    header("Pragma: no-cache");
    header("Expires: 0");
    print isset($header)?$header:NULL;
    include '../../../conf/conf.php';
    $_sql         = "SELECT * FROM set_tahun";
    $hasil        = bukaquery($_sql);
    $baris        = mysqli_fetch_row($hasil);
    $tahun        = empty($baris[0])?date("Y"):$baris[0];
    $bulan        = empty($baris[1])?date("m"):$baris[1];
?>
<html>
    <head>
        <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <?php
            $cari    = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
            $cari    = json_decode(encrypt_decrypt($cari,"d"),true);
            $keyword = "";
            if (isset($cari["usere"])) {
                if(($cari["usere"]==USERHYBRIDWEB)&&($cari["passwordte"]==PASHYBRIDWEB)){
                    echo "<center><h1><font color='999999'>Laporan Data Pendapatan Akte</font></h1></center>
                          &nbsp;Pendapatan Akte :";

                    $_sql 	    = "SELECT set_akte.pendapatan_akte,set_akte.persen_rs,set_akte.bagian_rs,set_akte.persen_kry,set_akte.bagian_kry FROM set_akte WHERE set_akte.tahun='$tahun' and set_akte.bulan='$bulan' ORDER BY set_akte.pendapatan_akte";
                    $hasil	    = bukaquery($_sql);
                    $jumlah	    = mysqli_num_rows($hasil);
                    $total_akte     = 0;
                    $no             = 1;
                    if(mysqli_num_rows($hasil)!=0) {
                        echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                <tr class='head'>
                                    <td width='5%'><div align='center'>No.</strong></div></td>
                                    <td width='30%'><div align='center'>Pendapatan Akte</div></td>
                                    <td width='6%'><div align='center'>% RS</div></td>
                                    <td width='25%'><div align='center'>Bagian RS</div></td>
                                    <td width='7%'><div align='center'>% Kry</div></td>
                                    <td width='25%'><div align='center'>Bagian Kry</div></td>
                                </tr>";
                                while($baris = mysqli_fetch_array($hasil)) {
                                    $total_akte=$baris[4];			
                                    echo "<tr class='isi'>
                                            <td>$no</td>  
                                            <td>".formatDuit($baris[0])."</td>
                                            <td>$baris[1]%</td>
                                            <td>".formatDuit($baris[2])."</td>
                                            <td>$baris[3]%</td>
                                            <td>".formatDuit($baris[4])."</td>                             
                                         </tr>";$no++;
                                }
                        echo "</table>";
                    }else{
                        echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                <tr class='head'>
                                    <td width='5%'><div align='center'>No.</strong></div></td>
                                    <td width='30%'><div align='center'>Pendapatan Akte</div></td>
                                    <td width='6%'><div align='center'>% RS</div></td>
                                    <td width='25%'><div align='center'>Bagian RS</div></td>
                                    <td width='7%'><div align='center'>% Kry</div></td>
                                    <td width='25%'><div align='center'>Bagian Kry</div></td>
                                </tr>
                              </table>";
                    }

                    echo "&nbsp;Pembagian Akte :";

                    $keyword 	= validTeks(trim(isset($cari["keyword"]))?trim($cari["keyword"]):NULL);
                    $_sql 	= "SELECT pembagian_akte.id,pegawai.nama,persen FROM pembagian_akte inner join pegawai
                                   on pembagian_akte.id=pegawai.id where pegawai.nama like '%".$keyword."%' ORDER BY persen desc";
                    $hasil	= bukaquery($_sql);
                    $jumlah	= mysqli_num_rows($hasil);
                    $ttl	= 0;
                    $prosen     = 0;
                    $no		= 1;
                    if(mysqli_num_rows($hasil)!=0) {
                        echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                <tr class='head'>
                                    <td width='5%'><div align='center'>No.</strong></div></td>
                                    <td width='45%'><div align='center'>Nama Karyawan</div></td>
                                    <td width='25%'><div align='center'>Porsi Bagian</div></td>
                                    <td width='25%'><div align='center'>Bagian Karyawan</div></td>
                                </tr>";                    
                               $bagiankry=0;
                                while($baris = mysqli_fetch_array($hasil)) {
                                    $bagiankry=($baris[2]/100)*$total_akte;
                                    $ttl=$ttl+$bagiankry;
                                    $prosen=$prosen+$baris[2];
                                    echo "<tr class='isi'>
                                            <td>$no</td>  
                                            <td>$baris[1]</td>
                                            <td>$baris[2]%</td>
                                            <td>".formatDuit($bagiankry)."</td>                        
                                         </tr>";$no++;
                                }
                        echo "</table>
                              <table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                <tr class='head'>
                                    <td><div align='left'>Data : $jumlah, Ttl Prosen : ".$prosen."%, Ttl Bagian : ".formatDuit($ttl)." </div></td>                        
                                </tr>     
                              </table>";
                    }else{
                        echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                <tr class='head'>
                                    <td width='5%'><div align='center'>No.</strong></div></td>
                                    <td width='45%'><div align='center'>Nama Karyawan</div></td>
                                    <td width='25%'><div align='center'>Porsi Bagian</div></td>
                                    <td width='25%'><div align='center'>Bagian Karyawan</div></td>
                                </tr>
                              </table>";  
                    } 
                }else{
                    exit(header("Location:../index.php"));
                }
            }else{
                exit(header("Location:../index.php"));
            }
        ?>
    </body>
</html>
