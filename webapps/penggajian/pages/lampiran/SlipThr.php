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
        $id=$_GET['id'];
        
        $_sqlthnini  = "SELECT DAY(LAST_DAY('$tahun-$bulan-01')) ";
        $hasilthnini = bukaquery($_sqlthnini);
        $datathnini  = mysqli_fetch_array($hasilthnini);        
        $thnini      = $tahun."-".$bulan."-".$datathnini[0];
        $thnlalu     = ($tahun-1)."-".$bulan."-".$datathnini[0];
        
        $_sql        = "SELECT pegawai.nik,pegawai.nama,stts_kerja,pegawai.
                        pendidikan,(To_days('$tahun-".($bulan)."-01')-to_days(mulai_kerja))/365 as masker,pegawai.departemen ,
                        stts_kerja.ktg,departemen.nama
                        FROM pegawai inner join stts_kerja
                        inner join departemen 
                       on pegawai.stts_kerja=stts_kerja.stts
                       and pegawai.departemen=departemen.dep_id where id='$id' ";
        $hasil       = bukaquery($_sql);
        $data        = mysqli_fetch_array($hasil);
        $nik         = $data[0];
        $nama        = $data[1];
        $status      = $data[2]; 
        $pendidikan  = $data[3]; 
        $masker      = $data[4];  
        $departemen  = $data[5];
        
        $gapok=0;           
        $wajibmasuk=0;
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
        
      echo "<table width=300px border='0' align='left' bgcolor='#FFFFFF' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
            <caption><center>
                           <font color='777777' size='1'  face='Arial'><b>Slip Gaji THR<br/><br/><br/><br/></b></font></center></caption>
             <tr class='isi6'>
                <td width='30%'>NIP</td>
                <td>:</td>
                <td width='70%'>$nik</td>                    
             </tr>
             <tr class='isi6'>
                <td width='30%'>Nama</td>
                <td>:</td>
                <td width='70%'>$nama</td>                    
             </tr>
              <tr class='isi6'>
                <td width='30%'>Status</td>
                <td>:</td>
                <td width='70%'>$data[6]</td>                    
             </tr>
              <tr class='isi6'>
                <td width='30%'>Departemen</td>
                <td>:</td>
                <td width='70%'>$data[7]</td>                    
             </tr>
             <tr class='isi6'>
                <td width='30%'>THR</td>
                <td>:</td>
                <td width='70%'>".formatDuit($gapok)."</td>                    
             </tr>
             <tr class='isi6'>
                <td width='30%'>Terbilang</td>
                <td>:</td>
                <td width='70%'>".Terbilang($gapok)." rupiah</td>                    
             </tr>
                                
              <TR class='isi6'>
                      <TD width='130px'><center>&nbsp;</center></TD>
		      <td width='3px'>&nbsp;</td>
		      <td width='140px'><center>&nbsp;</center></td>
	     </TR>
              <TR class='isi6'>
                      <TD width='130px'><center>&nbsp;</center></TD>
		      <td width='3px'>&nbsp;</td>
		      <td width='140px'><center>&nbsp;</center></td>
	     </TR>
              <TR class='isi6'>
                      <TD width='130px'><center>&nbsp;</center></TD>
		      <td width='3px'>&nbsp;</td>
		      <td width='140px'><center>&nbsp;</center></td>
	     </TR>
             <TR class='isi6'>
                      <TD width='130px'><center>Penerima</center></TD>
		      <td width='3px'>&nbsp;</td>
		      <td width='140px'><center>Pengelola</center></td>
	     </TR>
              <TR class='isi6'>
                      <TD width='130px'><center>&nbsp;</center></TD>
		      <td width='3px'>&nbsp;</td>
		      <td width='140px'><center>&nbsp;</center></td>
	     </TR>
              <TR class='isi6'>
                      <TD width='130px'><center>&nbsp;</center></TD>
		      <td width='3px'>&nbsp;</td>
		      <td width='140px'><center>&nbsp;</center></td>
	     </TR>
              <TR class='isi6'>
                      <TD width='130px'><center>&nbsp;</center></TD>
		      <td width='3px'>&nbsp;</td>
		      <td width='140px'><center>&nbsp;</center></td>
	     </TR>  
              <TR class='isi6'>
                      <TD colspan=3></TD>
            </TR><TR class='isi6'>
                      <TD colspan=3></TD>
            </TR>
             <TR class='isi6'>
                      <TD width='130px'><center>( $nama )</center></TD>
		      <td width='3px'>&nbsp;</td>
		      <td width='140px'><center>( .................. )</center></td>
            </TR>
              <TR class='isi6'>
                      <TD width='130px'><center>&nbsp;</center></TD>
		      <td width='3px'>&nbsp;</td>
		      <td width='140px'><center>&nbsp;</center></td>
	     </TR> 
            
              <TR class='isi6'>
                      <TD width='130px'><center>&nbsp;</center></TD>
		      <td width='3px'>&nbsp;</td>
		      <td width='140px'><center>&nbsp;</center></td>
	     </TR>
              
              <TR class='isi6'>
                      <TD width='130px'><center>&nbsp;</center></TD>
		      <td width='3px'>&nbsp;</td>
		      <td width='140px'><center>&nbsp;</center></td>
	     </TR>
              
            <TR class='isi6'>
                      <TD colspan=3><center>
                     <font size='2px' face='Arial' color='777777'><i><b>Selamat Hari Raya Idul Fitri<br/></b></i></font>
                     <font size='1px' face='Arial' color='777777'><i><b>Mohon Maaf Lahir dan Batin<br/>Semoga Berhasil Menjadi Insan Yang Bertaqwa<br/></b></i></font>
                     <font size='2px' face='Arial' color='777777'><i><b>Salam Sukses!</b></i></font></center></TD>
            </TR>
              <TR class='isi6'>
                      <TD width='130px'><center>&nbsp;</center></TD>
		      <td width='3px'>&nbsp;</td>
		      <td width='140px'><center>&nbsp;</center></td>
	     </TR>
              <TR class='isi6'>
                      <TD width='130px'><center>&nbsp;</center></TD>
		      <td width='3px'>&nbsp;</td>
		      <td width='140px'><center>&nbsp;</center></td>
	     </TR>
              <TR class='isi6'>
                      <TD colspan=3><center><font size='1px' face='Arial' color='777777'><i><b>..Terima kasih atas kerjasamanya <br>dalam memberikan pelayanan yang berkualitas..</b></i></font></center></TD>
            </TR>
           </table>" ;
                    
        
        
    ?>
    </body>
</html>