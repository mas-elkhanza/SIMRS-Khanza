<?php
 include '../../../conf/conf.php';
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baristahun   = mysqli_fetch_row($hasil);
   $tahun        = $baristahun[0];
   $bln_leng     = strlen($baristahun[1]);
   $hari         = $baristahun[2];
   $bulan        = "0";
   $bulanindex   = $baristahun[1];
   if ($bln_leng==1){
    	$bulan="0".$baristahun[1];
   }else{
	$bulan=$baristahun[1];
   }

?>
<html>
    <head>
        <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
	
   <?php
   
        
        $_sqlthnini  = "SELECT DAY(LAST_DAY('$tahun-$bulan-01')) ";
        $hasilthnini = bukaquery($_sqlthnini);
        $datathnini  = mysqli_fetch_array($hasilthnini);        
        $thnini      = $tahun."-".$bulan."-".$datathnini[0];
        $thnlalu     = ($tahun-1)."-".$bulan."-".$datathnini[0];
        
        $_sql        = "SELECT pegawai.nik,
                        pegawai.nama,
                        stts_kerja,
                        pegawai.pendidikan,
                        (To_days('$tahun-".($bulan)."-01')-to_days(mulai_kerja))/365 as masker,
                        pegawai.departemen ,
                        stts_kerja.ktg,
                        departemen.nama,
                        pegawai.id, pegawai.rekening
                        FROM pegawai inner join stts_kerja
                        inner join departemen 
                       on pegawai.stts_kerja=stts_kerja.stts
                       and pegawai.departemen=departemen.dep_id where pegawai.stts_aktif<>'KELUAR' and pegawai.bpd<>'T' order by pegawai.id ASC  ";
        $hasil6       = bukaquery($_sql);
        
        if(mysqli_num_rows($hasil6)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h2 class=title><font color='999999'>Laporan Transfer THR Karyawan Tahun ".$tahun." Bulan ".($bulan+1)."</font></h2></caption>
                    <tr class='head'>
                         <td width='20%'><div align='center'>NIP</div></td>
                         <td width='20%'><div align='center'>Nama</div></td>
                         <td width='20%'><div align='center'>Status</div></td>
                         <td width='20%'><div align='center'>Departemen</div></td>
                         <td width='10%'><div align='center'>Rekening</div></td>
                         <td width='10%'><div align='center'><font color='green'>THR</font></div></td>
                    </tr>";
            while($data = mysqli_fetch_array($hasil6)) {   
                $nik         = $data[0];
                $nama        = $data[1];
                $status      = $data[2]; 
                $pendidikan  = $data[3]; 
                $masker      = $data[4];  
                $departemen  = $data[5]; 
                $id          = $data[8];
                $rekening    = $data[9];
        
                $gapok=0;     
                $wajibmasuk=0;
                $ttlgapok=0;
                if($departemen=="POM"){
                    $wajibmasuk=24; 
                }elseif($departemen=="RNP"){
                    $wajibmasuk=22; 
                }elseif($departemen=="LAB"){
                    $wajibmasuk=22; 
                }elseif($departemen=="PRK"){
                    $wajibmasuk=26; 
                }elseif($departemen=="SAT"){
                    $wajibmasuk=26; 
                }elseif($departemen=="KBY"){
                    $wajibmasuk=22; 
                }elseif($departemen=="POG"){
                    $wajibmasuk=24; 
                }elseif($departemen=="RWJ"){
                    $wajibmasuk=24; 
                }elseif($departemen=="CST"){
                    $wajibmasuk=26; 
                }elseif($departemen=="DPR"){
                    $wajibmasuk=23; 
                }elseif($departemen=="DRV"){
                    $wajibmasuk=18; 
                }elseif($departemen=="FAR"){
                    $wajibmasuk=26; 
                }elseif($departemen=="KSR"){
                    $wajibmasuk=26; 
                }elseif($departemen=="LOG"){
                    $wajibmasuk=26; 
                }elseif($departemen=="MNJ"){
                    $wajibmasuk=26; 
                }elseif($departemen=="POD"){
                    $wajibmasuk=24; 
                }elseif($departemen=="RM"){
                    $wajibmasuk=26; 
                }elseif($departemen=="ROS"){
                    $wajibmasuk=26; 
                }elseif($departemen=="TAM"){
                    $wajibmasuk=26; 
                }
        
                $_sql3     = "SELECT sum(jml) FROM tambahjaga where id='$id' and tgl between '$thnlalu' and '$thnini' ";
                $hasil3    = bukaquery($_sql3);
                $data3     = mysqli_fetch_array($hasil3);
                $tmbhn     = $data3[0];
                $nmasuk    = $tmbhn/12;
        
                if(($status=="FT")||($status=="T")||($status=="CPT")||($status=="CK")||($status=="CT")){
                    $_sqlgp    = "SELECT `gapok1`, `kenaikan`, `maksimal`
                                from pendidikan  where tingkat like '%".$pendidikan."%' order by gapok1 desc limit 1 ";
                    $hasilgp   = bukaquery($_sqlgp);
                    $barisgp   = mysqli_fetch_array($hasilgp);
                    $gapokgp    = 0;
                    @$gapok1    = $barisgp["gapok1"];
                    @$kenaikan  = $barisgp["kenaikan"];
                    @$maksimal  = $barisgp["maksimal"];
                          
                    if($masker<$maksimal){
                        $gapokgp=$gapok1+($kenaikan*round($masker));
                    }elseif($masker>=$maksimal){
                        $gapokgp=$gapok1+($kenaikan*$maksimal);
                    }
                    $gapok=$gapokgp;
                    if((($masker<1)&&($status=="CK"))||(($masker<1)&&($status=="FT"))){
                       $gapok=$masker*$gapokgp;
                    }
                }else if($status=="PT"){
                    $_sqlgp    = "SELECT `gapok1`, `kenaikan`, `maksimal`
                                from pendidikan  where tingkat like '%".$pendidikan."%' order by gapok1 desc limit 1 ";
                    $hasilgp   = bukaquery($_sqlgp);
                    $barisgp   = mysqli_fetch_array($hasilgp);
                    $gapokgp    = 0;
                    @$gapok1    = $barisgp["gapok1"];
                    @$kenaikan  = $barisgp["kenaikan"];
                    @$maksimal  = $barisgp["maksimal"];
                          
                    if($masker<$maksimal){
                        $gapokgp=$gapok1+($kenaikan*round($masker));
                    }elseif($masker>=$maksimal){
                        $gapokgp=$gapok1+($kenaikan*$maksimal);
                    }
                    $gapok=$gapokgp;
                    if($masker<1){
                        $gapok=($masker/12)*$gapokgp;
                    }
                }
                
                $ttlgapok=$ttlgapok+$gapok;
                
                echo "<tr class='isi'>
                                 <td>$nik&nbsp;</td>
                                 <td>$nama&nbsp;</td>
                                 <td>$data[6]&nbsp;</td>
                                 <td>$data[7]&nbsp;</td>
                                 <td>$rekening&nbsp;</td>
                                 <td align=right>".round($gapok)."</td>
				  ";
                
            }
         echo "<tr class='head'>
                         <td COLSPAN='5'><div align='right'>Total :&nbsp; </div></td>
                         <TD><font size='2' color='green'><i>".formatDuit($ttlgapok)."</i></font>&nbsp;</TD>
                    </tr>
                  </table>";           
        }
        
    ?>
    </body>
</html>