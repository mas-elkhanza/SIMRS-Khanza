<?php
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
                    echo "<center><h1><font color='999999'>Laporan Data Insentif</font></h1></center>
                        &nbsp;Pendapatan :";
                    $_sql 		= "SELECT set_insentif.pendapatan,set_insentif.persen,set_insentif.total_insentif FROM set_insentif WHERE set_insentif.tahun='$tahun' and set_insentif.bulan='$bulan' ORDER BY set_insentif.pendapatan";
                    $hasil		= bukaquery($_sql);
                    $jumlah		= mysqli_num_rows($hasil);
                    $total_insentif = "0";
                    $no		= 1;
                    if(mysqli_num_rows($hasil)!=0) {
                        echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                <tr class='head'>
                                    <td width='10%'><div align='center'>No.</strong></div></td>
                                    <td width='34%'><div align='center'>Pendapatan</div></td>
                                    <td width='20%'><div align='center'>Prosentase</div></td>
                                    <td width='34%'><div align='center'>Total Insentif</div></td>
                                </tr>";
                                while($baris = mysqli_fetch_array($hasil)) {
                                    $total_insentif=$baris[2];				
                                    echo "<tr class='isi'>
                                            <td>$no</td>  
                                            <td>".formatDuit($baris[0])."</td>
                                            <td>$baris[1]%</td>
                                            <td>".formatDuit($baris[2])."</td>                                
                                         </tr>";$no++;
                                }
                        echo "</table>";
                    }else{
                        echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                <tr class='head'>
                                    <td width='10%'><div align='center'>No.</strong></div></td>
                                    <td width='34%'><div align='center'>Pendapatan</div></td>
                                    <td width='20%'><div align='center'>Prosentase</div></td>
                                    <td width='34%'><div align='center'>Total Insentif</div></td>
                                </tr>
                               </table>";
                    } 

                    echo "<br>&nbsp;Insentif :";
                    $keyword 	= validTeks(trim(isset($cari["keyword"]))?trim($cari["keyword"]):NULL);
                    $_sql 	= "SELECT indexins.dep_id,indexins.persen FROM indexins where indexins.dep_id like '%".$keyword."%'ORDER BY indexins.persen desc";
                    $hasil	= bukaquery($_sql);
                    $jumlah	= mysqli_num_rows($hasil);
                    $ttl	= 0;
                    $prosen	= 0;
                    $no		= 1;
                    if(mysqli_num_rows($hasil)!=0) {
                        echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                <tr class='head'>
                                    <td width='10%'><div align='center'>No.</strong></div></td>
                                    <td width='20%'><div align='center'>Kode Index</div></td>
                                    <td width='30%'><div align='center'>Porsi Insentif</div></td>
                                    <td width='38%'><div align='center'>Total Insentif</div></td>
                                </tr>";                    
                                $insentifindex=0;
                                while($baris = mysqli_fetch_array($hasil)) {
                                    $insentifindex=($baris[1]/100)*$total_insentif;
                                    $ttl=$ttl+$insentifindex;
                                    $prosen=$prosen+$baris[1];
                                    echo "<tr class='isi'>
                                            <td>$no</td>  
                                            <td>$baris[0]</td>
                                            <td>$baris[1]%</td>
                                            <td>".formatDuit($insentifindex)."</td>                            
                                         </tr>";
                                    $no++;
                                }
                        echo "</table>
                             <table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                <tr class='head'>
                                    <td><div align='left'>Data : $jumlah, Ttl Prosen : ".$prosen."%, Ttl Insentif : ".formatDuit($ttl)." </div></td>                        
                                </tr>     
                             </table>";
                    }else{
                        echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                <tr class='head'>
                                    <td width='10%'><div align='center'>No.</strong></div></td>
                                    <td width='20%'><div align='center'>Kode Index</div></td>
                                    <td width='30%'><div align='center'>Porsi Insentif</div></td>
                                    <td width='38%'><div align='center'>Total Insentif</div></td>
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
