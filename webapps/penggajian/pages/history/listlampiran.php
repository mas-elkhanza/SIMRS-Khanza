<div class="t">
<div class="b">
<div class="l">
<div class="r">
<div class="bl">
<div class="br">
<div class="tl">
<div class="tr">
<div class="y">
<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun         = $baris[0];
   $bln_leng=strlen($baris[1]);
   $hari          =$baris[2];
   $bulan="0";
   $bulanindex=$baris[1];
   if ($bln_leng==1){
    	$bulan="0".$baris[1];
   }else{
	$bulan=$baris[1];
   }
   $action      =$_GET['action'];

    $_sqllibur = "select `tanggal`, `ktg`
                        from set_hari_libur
                        where tanggal like '%".$tahun."-".$bulan."%' ORDER BY tanggal";
                $hasillibur=bukaquery($_sqllibur);
                $jumlahlibur=mysqli_num_rows($hasillibur);
?>

<div id="post">
    <h1 class="title">:: List Data Lampiran1 Tahun <?php echo$tahun ;?> Bulan <?php echo$bulan ;?> ::</h1>
    <div class="entry">

    <div align="center" class="link">
        <a href=?act=InputSetJagaMalam&action=TAMBAH>| Set Jaga Malam |</a>
	<a href=?act=InputSetTambahJaga&action=TAMBAH>| Set Tambah Jaga |</a>
	<a href=?act=InputSetTunjanganHadir&action=TAMBAH>| Set Tnj.Hadir |</a>
    </div>
    <div align="center" class="link">
        <a href=?act=InputSetLemburHB&action=TAMBAH>| Set Lembur HB |</a>
        <a href=?act=InputSetLemburHR&action=TAMBAH>| Set Lembur HR |</a>
        <a href=?act=ListLampiran&action=LIHAT>| List Lampiran |</a>
    </div>
	<br/>
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
                echo "";
                $action      =$_GET['action'];
                //$keyword     =$_GET['keyword'];
                echo "<input type=hidden name=keyword value=$keyword><input type=hidden name=action value=CARI>";
        ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="25%" >Keyword</td><td width="">:</td>
                    <td width="70%"><input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="65" maxlength="250" />
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;">            
            </div><br>
    <div style="width: 598px; height: 400px; overflow: auto;">
    <?php
        $keyword=trim($_POST['keyword']);
        $action      =$_POST['action'];
        $say=" pegawai.pendidikan=pendidikan.tingkat
                and pegawai.stts_kerja =stts_kerja.stts
                and pegawai.jnj_jabatan=jnj_jabatan.kode
                and pegawai.stts_aktif<>'KELUAR'";
        $_sql = "select pegawai.id,
                pegawai.nik,
                pegawai.nama,
                pegawai.jbtn,
                pegawai.jnj_jabatan,
                pegawai.departemen,
                pegawai.indexins,
                CONCAT(FLOOR(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'),DATE_FORMAT(mulai_kerja, '%Y%m'))/12), ' Tahun ',MOD(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'), DATE_FORMAT(mulai_kerja, '%Y%m')),12), ' Bulan ') as lama,
                pendidikan.indek as index_pendidikan,
                (To_days('$tahun-$bulan-$hari')-to_days(mulai_kerja))/365 as masker,
                stts_kerja.indek as index_status,
                pegawai.indek as index_struktural,
                pegawai.pengurang,
                pegawai.wajibmasuk,
		pegawai.gapok,
                jnj_jabatan.tnj,
                pegawai.pendidikan,
                (To_days('$tahun-$bulan-$hari')-to_days(mulai_kontrak))/365 as maskon                
                from pegawai inner join pendidikan
                inner join stts_kerja
                inner join jnj_jabatan
                where ".$say." and pegawai.nik like '%".$keyword."%' or
                ".$say." and pegawai.nama like '%".$keyword."%' or
                ".$say." and pegawai.jbtn like '%".$keyword."%' or
                ".$say." and pegawai.jnj_jabatan like '%".$keyword."%' or
                ".$say." and pegawai.indexins like '%".$keyword."%'
                order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);	
	$hasilcari=bukaquery($_sql);
		
		//untuk mencari nilai referensinya
                if ($action!="CARI") {
                    hapusinput("delete from  indekref");
                }		
		while($baris = mysqli_fetch_array($hasilcari)) {
			$masa_kerja=0;
                          if($baris[9]<1){
                             $masa_kerja=0;
                          }else if(($baris[9]>=1)&&($baris[9]<2)){
                             $masa_kerja=2;
                          }else if(($baris[9]>=2)&&($baris[9]<3)){
                             $masa_kerja=4;
                          }else if(($baris[9]>=3)&&($baris[9]<4)){
                             $masa_kerja=6;
                          }else if(($baris[9]>=4)&&($baris[9]<5)){
                             $masa_kerja=8;
                          }else if(($baris[9]>=5)&&($baris[9]<6)){
                             $masa_kerja=10;
                          }else if(($baris[9]>=6)&&($baris[9]<7)){
                             $masa_kerja=12;
                          }else if($baris[9]>=7){
                             $masa_kerja=14;
                          }

                          $total=0;
                          if($baris[12]==0){
                              $total=($baris[8]+$masa_kerja+$baris[10]+$baris[11]);
                          }else if($baris[12]>0){
                              $total=($baris[8]+$masa_kerja+$baris[10]+$baris[11])*($baris[12]/100);
                          }                          

                         $_sql2         = "SELECT normal-$jumlahlibur,jmlhr FROM set_tahun";
			 $hasil2        = bukaquery($_sql2);
			 $baris2        = mysqli_fetch_row($hasil2);
			 $jmlmsk         = $baris2[0];
			 if($baris[13]==-1){
			     $jmlmsk=0;
			 }else if($baris[13]==-2){
			     $jmlmsk=$baris2[1]-4;
			 }else if($baris[13]==-3){
			     $jmlmsk=$baris2[1]-2-$jumlahlibur;
			 }else if($baris[13]!=0){
			     $jmlmsk=$baris[13];
			 }else if(!($baris[13]==0)){
			     $jmlmsk=$baris2[0];
			 }

                            $_sql3    = "SELECT sum(jml)
                            from jgmlm  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' group by id";
                            $hasil3   = bukaquery($_sql3);
                            $baris3   = mysqli_fetch_row($hasil3);
                            $jgmlm    = $baris3[0];
                            $sisamlm  =0;
                            if($baris3[0]<=7){
                                $jgmlm=0;
                                $sisamlm=0;
                            }else if($baris3[0]>7){
                                $jgmlm=$baris3[0];
                                $sisamlm=$baris3[0]-7;
                            }

                            $_sql4    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='A' group by id";
                            $hasil4   = bukaquery($_sql4);
                            $baris4   = mysqli_fetch_row($hasil4);
                            $ttla     = $baris4[0];
                            if(empty ($baris4[0])){
                                $ttla=0;
                            }
                            
                            $_sql5    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='S' group by id";
                            $hasil5   = bukaquery($_sql5);
                            $baris5   = mysqli_fetch_row($hasil5);
                            $ttls     = $baris5[0];
                            if(empty ($baris5[0])){
                                $ttls=0;
                            }

                            $_sql6    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='C' group by id";
                            $hasil6   = bukaquery($_sql6);
                            $baris6   = mysqli_fetch_row($hasil6);
                            $ttlc     = $baris6[0];
                            if(empty ($baris6[0])){
                                $ttlc=0;
                            }

                            $_sql7    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='I' group by id";
                            $hasil7   = bukaquery($_sql7);
                            $baris7   = mysqli_fetch_row($hasil7);
                            $ttli     = $baris7[0];
                            if(empty ($baris7[0])){
                                $ttli=0;
                            }

                            $_sql8    = "SELECT sum(jml)
                            from tambahjaga  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' group by id";
                            $hasil8   = bukaquery($_sql8);
                            $baris8   = mysqli_fetch_row($hasil8);
                            $ttltmb   = $baris8[0];
                            if(empty ($baris8[0])){
                                $ttltmb=0;
                            }					
							
                            $ttln=$jmlmsk+$ttltmb-($ttla+$ttls+$ttlc+$ttli);
                            if ($action!="CARI") {
                                bukainput("insert into indekref values('$baris[6]','$ttln','$total')");
                            }			    
		}
		
		//insert data ke total index
		$hasilindex=bukaquery("select kdindex,n,ttl from indekref");		
		//untuk mencari nilai referensinya
                if ($action!="CARI") {
                    hapusinput("delete from  indextotal");
                }
		while($barisindex = mysqli_fetch_array($hasilindex)) {		
		    $_sql22  ="SELECT ($barisindex[1]/sum(n))*100 from indekref where kdindex='$barisindex[0]'";
		    $hasil22 =bukaquery($_sql22);
		    $baris22 = mysqli_fetch_array($hasil22);
		    $indexjaga=$baris22[0];                   
                    
				
		    $ttlindex=$barisindex[2]+$indexjaga;
                    if ($action!="CARI") {
                        bukainput("insert into indextotal  values('$barisindex[0]','$ttlindex')");
                    }
		}
		
                $ttlgapok=0;
                $ttltnjjbtn=0;
                $ttltnjtnj=0;
                $ttltmbhjgmlm=0;
                $ttltmbahanjg=0;
                $ttltnjhadir=0;
                $ttljmlgaji=0;
                $ttllemburhb=0;
                $ttllemburhr=0;
                $ttltotal=0;
                $ttlindexjaga=0;
                $ttlttlinsentif=0;
                $ttljm=0;
                $ttljmltmb=0;
                $ttlttlgaji=0;
                $ttljamsostek=0;
                $ttldansos=0;
                $ttlsimwajib=0;
                $ttlangkop=0;
                $ttlangla=0;
                $ttltelpri=0;
                $ttlpajak=0;
                $ttlpribadi=0;
                $ttllain=0;
                $ttlttlditerima=0;
                $ttljasalain=0;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='4900px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                         <td width='80px'><div align='center'><font size='2' face='Verdana'><strong>NIK</strong></font></div></td>
                         <td width='180px'><div align='center'><font size='2' face='Verdana'><strong>Nama</strong></font></div></td>
                         <td width='100px'><div align='center'><font size='2' face='Verdana'><strong>Jabatan</strong></font></div></td>
                         <td width='60px'><div align='center'><font size='2' face='Verdana'><strong>Kode Jenjang</strong></font></div></td>
                         <td width='60px'><div align='center'><font size='2' face='Verdana'><strong>Depart</strong></font></div></td>
                         <td width='60px'><div align='center'><font size='2' face='Verdana'><strong>Kode Index</strong></font></div></td>
                         <td width='60px'><div align='center'><font size='2' face='Verdana'><strong>Index Kary</strong></font></div></td>
                         <td width='200px'><div align='center'><font size='2' face='Verdana'><strong>Jumlah Hari</strong></font></div></td>
                         <td width='70px'><div align='center'><font size='2' face='Verdana'><strong>Jml.Index Lembur</strong></font></div></td>
                         <td width='910px'><div align='center'><font size='2' face='Verdana'><strong>Gaji & Tunjangan diterima</strong></font></div></td>
                         <td width='310px'><div align='center'><font size='2' face='Verdana'><strong>Lembur Diterima</strong></font></div></td>
                         <td width='630px'><div align='center'><font size='2' face='Verdana'><strong>Tambahan Gaji Diterima</strong></font></div></td>
                         <td width='100px'><div align='center'><font size='2' face='Verdana' color='green'><strong>Total Gaji</strong></font></div></td>
                         <td width='920px'><div align='center'><font size='2' face='Verdana'><strong>Potongan Gaji</strong></font></div></td>
                         <td width='100px'><div align='center'><font size='2' face='Verdana' color='green'><strong>Total Gaji Diterima</strong></font></div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                          $_sqlgp    = "SELECT `gapok1`, `kenaikan`, `maksimal`
                          from pendidikan  where tingkat='$baris[16]' ";
                          $hasilgp    = bukaquery($_sqlgp);
                          $barisgp    = mysqli_fetch_array($hasilgp);
                          $gapokgp    = 0;
                          @$gapok1    = $barisgp["gapok1"];
                          @$kenaikan  = $barisgp["kenaikan"];
                          @$maksimal  = $barisgp["maksimal"];
                          
                          if($baris[17]<$maksimal){
                             $gapokgp=$gapok1+($kenaikan*round($baris[17]));
                          }elseif($baris[17]>=$maksimal){
                             $gapokgp=$gapok1+($kenaikan*$maksimal);
                          }

                          $masa_kerja=0;
                          if($baris[9]<1){
                             $masa_kerja=0;
                          }else if(($baris[9]>=1)&&($baris[9]<2)){
                             $masa_kerja=2;
                          }else if(($baris[9]>=2)&&($baris[9]<3)){
                             $masa_kerja=4;
                          }else if(($baris[9]>=3)&&($baris[9]<4)){
                             $masa_kerja=6;
                          }else if(($baris[9]>=4)&&($baris[9]<5)){
                             $masa_kerja=8;
                          }else if(($baris[9]>=5)&&($baris[9]<6)){
                             $masa_kerja=10;
                          }else if(($baris[9]>=6)&&($baris[9]<7)){
                             $masa_kerja=12;
                          }else if($baris[9]>=7){
                             $masa_kerja=14;
                          }
                          
                          $total=0;
                          if($baris[12]==0){
                              $total=($baris[8]+$masa_kerja+$baris[10]+$baris[11]);
                          }else if($baris[12]>0){
                              $total=($baris[8]+$masa_kerja+$baris[10]+$baris[11])*($baris[12]/100);
                          }
                          $ttltotal=$ttltotal+$total;

                         $_sql2         = "SELECT normal-$jumlahlibur,jmlhr FROM set_tahun";
			 $hasil2        = bukaquery($_sql2);
			 $baris2        = mysqli_fetch_row($hasil2);
			 $jmlmsk         = $baris2[0];
			 if($baris[13]==-1){
			     $jmlmsk=0;
			 }else if($baris[13]==-2){
			     $jmlmsk=$baris2[1]-4;
			 }else if($baris[13]==-3){
			     $jmlmsk=$baris2[1]-2-$jumlahlibur;
			 }else if($baris[13]!=0){
			     $jmlmsk=$baris[13];
			 }else if(!($baris[13]==0)){
			     $jmlmsk=$baris2[0];
			 }

                            $_sql3    = "SELECT sum(jml)
                            from jgmlm  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' group by id";
                            $hasil3   = bukaquery($_sql3);
                            $baris3   = mysqli_fetch_row($hasil3);
                            $jgmlm    = $baris3[0];
                            $sisamlm  =0;
                            if($baris3[0]<=7){
                                $jgmlm=0;
                                $sisamlm=0;
                            }else if($baris3[0]>7){
                                $jgmlm=$baris3[0];
                                $sisamlm=$baris3[0]-7;
                            }

                            $_sql4    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='A' group by id";
                            $hasil4   = bukaquery($_sql4);
                            $baris4   = mysqli_fetch_row($hasil4);
                            $ttla     = $baris4[0];
                            if(empty ($baris4[0])){
                                $ttla=0;
                            }
                            
                            $_sql5    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='S' group by id";
                            $hasil5   = bukaquery($_sql5);
                            $baris5   = mysqli_fetch_row($hasil5);
                            $ttls     = $baris5[0];
                            if(empty ($baris5[0])){
                                $ttls=0;
                            }

                            $_sql6    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='C' group by id";
                            $hasil6   = bukaquery($_sql6);
                            $baris6   = mysqli_fetch_row($hasil6);
                            $ttlc     = $baris6[0];
                            if(empty ($baris6[0])){
                                $ttlc=0;
                            }

                            $_sql7    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='I' group by id";
                            $hasil7   = bukaquery($_sql7);
                            $baris7   = mysqli_fetch_row($hasil7);
                            $ttli     = $baris7[0];
                            if(empty ($baris7[0])){
                                $ttli=0;
                            }

                            $_sql8    = "SELECT sum(jml)
                            from tambahjaga  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' group by id";
                            $hasil8   = bukaquery($_sql8);
                            $baris8   = mysqli_fetch_row($hasil8);
                            $ttltmb   = $baris8[0];
                            if(empty ($baris8[0])){
                                $ttltmb=0;
                            }						
							

                            $ttln=$jmlmsk+$ttltmb-($ttla+$ttls+$ttlc+$ttli);
                            $tmbh=($ttltmb-$ttla-$ttls-$ttlc-$ttli);
							
			    $_sql9    = "SELECT id,jmlks,bsr from kasift  where id='$baris[0]'";
                            $hasil9   = bukaquery($_sql9);
                            $baris9   = mysqli_fetch_row($hasil9);
                            $ks   = $baris9[1];
                            $bsrkasift=$baris9[2];
                            if($baris9[1]!=0){
                                $ks=$baris9[1];
                            }else if($baris9[1]==0){
				$ks=$ttln;
			    }
                            $ttlkasift=$ks*$bsrkasift;
							
			    $_sql10="select sum(presensi.lembur)
                                from presensi
                                where presensi.id='$baris[0]' and presensi.tgl like '%".$tahun."-".$bulan."%'
                                and presensi.jns='HB'
                                group by presensi.id";
			    $hasil10=bukaquery($_sql10);
			    $baris10 = mysqli_fetch_array($hasil10);
			    $hb   = $baris10[0];
			    if(empty ($baris10[0])){
                                $hb=0;
                            }else {
				$hb=$baris10[0];
			    }
							
			    $_sql11="select sum(presensi.lembur)
                                from presensi
                                where presensi.id='$baris[0]' and presensi.tgl like '%".$tahun."-".$bulan."%'
                                and presensi.jns='HR'
                                group by presensi.id";
			    $hasil11=bukaquery($_sql11);
			    $baris11 = mysqli_fetch_array($hasil11);
			    $hr   = $baris11[0];
			    if(empty ($baris11[0])){
                                $hr=0;
                            }else {
				$hr=$baris11[0];
			    }
							
			    $gapok=0;
			    if(empty ($gapokgp)){
                                $gapok=0;
                            }else {
				$gapok=$gapokgp;
			    }
                            $ttlgapok=$ttlgapok+$gapok;
							
			    $tnjjbtn=0;
		            if(empty ($baris[15])){
                                $tnjjbtn=0;
                            }else {
				$tnjjbtn=$baris[15];
			    }
                            $ttltnjjbtn=$ttltnjjbtn+$tnjjbtn;

                            $_sql17  ="SELECT tnj from set_jgmlm ";
			    $hasil17 =bukaquery($_sql17);
			    $baris17 = mysqli_fetch_array($hasil17);
			    $tmbhjgmlm = $sisamlm*$baris17[0];
                            $ttltmbhjgmlm=$ttltmbhjgmlm+$tmbhjgmlm;
				
			    $_sql18  ="SELECT tnj from set_jgtambah where pendidikan='$baris[16]' ";
			    $hasil18 =bukaquery($_sql18);
			    $baris18 = mysqli_fetch_array($hasil18);
			    $tmbahanjg =0;
                            $alpha=$baris18[0]*$ttla;
			    if(($tmbh>0)){
				 $tmbahanjg=$tmbh*$baris18[0];
			    }
                            $ttltmbahanjg=$ttltmbahanjg+$tmbahanjg;
				
			    $_sql19  ="SELECT tnj from set_hadir ";
			    $hasil19 =bukaquery($_sql19);
			    $baris19 = mysqli_fetch_array($hasil19);
			    $tnjhadir =0;
			    if(($ttln>=$jmlmsk)&&($jmlmsk!=0)){
				 $tnjhadir=$baris19[0];
			    }
                            $ttltnjhadir=$ttltnjhadir+$tnjhadir;
                            
                            //potongan kurang kehadiran 25.000
                            $ptg_krghadir=0;
                            if($ttln<$jmlmsk){
                                $ptg_krghadir=25000;
                            }

                            $_sql20  ="SELECT tnj from set_lemburhb";
			    $hasil20 =bukaquery($_sql20);
			    $baris20 = mysqli_fetch_array($hasil20);
			    $lemburhb=$hb*$baris20[0];
                            $ttllemburhb=$ttllemburhb+$lemburhb;

                            $_sql21  ="SELECT tnj from set_lemburhr";
			    $hasil21 =bukaquery($_sql21);
			    $baris21 = mysqli_fetch_array($hasil21);
			    $lemburhr=$hr*$baris21[0];
                            $ttllemburhr=$ttllemburhr+$lemburhr;

                            $_sql22="";
                            if($baris[6]!="DIR"){
                                $_sql22  ="SELECT ($ttln/sum(n))*100 from indekref where kdindex='$baris[6]'";
                            }else if($baris[6]=="DIR"){
                                $_sql22  ="SELECT ($ttln/sum(n))*100 from indekref where kdindex='MNJ'";
                            }
                            
			    $hasil22 =bukaquery($_sql22);
			    $baris22 = mysqli_fetch_array($hasil22);
			    $indexjaga=$baris22[0];

                            if($baris[0]=="1"){
                                $indexjaga=0;
                            }                            
                            
                            $ttlindexjaga=$ttlindexjaga+$indexjaga;
                            
                            $_sqlpassum  ="select sum(jumpasien.jml) from jumpasien  
                                 where thn='".$tahun."' and bln='".$bulanindex."'";
                            $hasilpassum =bukaquery($_sqlpassum);
                            $barispassum = mysqli_fetch_array($hasilpassum);
                            $indexpassum=$barispassum[0];
                            
                            if(!empty ($indexpassum)){
                                $_sqlpas  ="select (jumpasien.jml/$indexpassum)*100
                                from jumpasien  where id='$baris[0]' 
			        and thn='".$tahun."' and bln='".$bulanindex."'";
                                $hasilpas =bukaquery($_sqlpas);
                                $barispas = mysqli_fetch_array($hasilpas);
                                $indexpas=$barispas[0];
                            }  
				
			    $ttlindex=$total+$indexjaga+$indexpas;

                            $_sql24="select sum(tindakan.jm)
                                from tindakan
                                where tindakan.id='$baris[0]' and tindakan.tgl like '%".$tahun."-".$bulan."%'
                                group by tindakan.id";
			    $hasil24=bukaquery($_sql24);
			    $baris24 = mysqli_fetch_array($hasil24);
			    $jm   = $baris24[0];
			    if(empty ($baris24[0])){
                                $jm=0;
                            }else {
				$jm=$baris24[0];
			    }
                            $ttljm=$ttljm+$jm;

                            $_sql26="select sum(jasa_lain.bsr_jasa)
                                from jasa_lain  where id='$baris[0]' 
			        and thn='".$tahun."' and bln='".$bulanindex."'
                                group by jasa_lain.id";
			    $hasil26=bukaquery($_sql26);
			    $baris26 = mysqli_fetch_array($hasil26);

                            $_sql27="select (pembagian_akte.persen/100)*set_akte.bagian_kry
                                from pembagian_akte,set_akte  where pembagian_akte.id='$baris[0]'
			        and set_akte.tahun='".$tahun."' and set_akte.bulan='".$bulanindex."'";
			    $hasil27=bukaquery($_sql27);
			    $baris27 = mysqli_fetch_array($hasil27);

                            $_sql28="select (pembagian_resume.persen/100)*set_resume.pendapatan_resume
                                from pembagian_resume,set_resume  where pembagian_resume.id='$baris[0]'
			        and set_resume.tahun='".$tahun."' and set_resume.bulan='".$bulanindex."'";
			    $hasil28=bukaquery($_sql28);
			    $baris28 = mysqli_fetch_array($hasil28);

                            $_sql29="select (pembagian_tuslah.persen/100)*set_tuslah.bagian_kry
                                from pembagian_tuslah,set_tuslah  where pembagian_tuslah.id='$baris[0]'
			        and set_tuslah.tahun='".$tahun."' and set_tuslah.bulan='".$bulanindex."'";
			    $hasil29=bukaquery($_sql29);
			    $baris29 = mysqli_fetch_array($hasil29);

                            $_sql31="SELECT ($ttlindex/sum(indextotal.ttl))*set_warung.bagian_kry
                                from indextotal,set_warung,pembagian_warung where pembagian_warung.id='$baris[0]'
                                and set_warung.tahun='$tahun' and set_warung.bulan='$bulanindex'
                                and indextotal.kdindex='$baris[6]'";
			    $hasil31=bukaquery($_sql31);
			    $baris31 = mysqli_fetch_array($hasil31);

			    $jl   = $baris26[0]+$baris27[0]+$baris28[0]+$baris29[0]+$baris31[0];
			    if(empty($baris26[0])&&empty($baris27[0])&&empty($baris28[0])&&empty($baris29[0])&&empty($baris31[0])){
                                $jl=0;
                            }else {
				$jl   = $baris26[0]+$baris27[0]+$baris28[0]+$baris29[0]+$baris31[0];
			    }
                            $ttljasalain=$ttljasalain+$jl;

                        echo "<tr class='isi' title='$baris[1] $baris[2]'>
                                 <td><a target=_blank href=/penggajian/pages/lampiran/SlipGaji.php?&id=$baris[0]>- $baris[1]</a></td>
                                 <td><a target=_blank href=/penggajian/pages/lampiran/SlipDokter.php?&id=$baris[0]>$baris[2]</a></td>
                                 <td><a target=_blank href=/penggajian/pages/lampiran/SlipGajisp.php?&id=$baris[0]>$baris[3]</a></td>
                                 <td><a target=_blank href=/penggajian/pages/lampiran/SlipGajisp2.php?&id=$baris[0]>$baris[4]</a></td>
                                 <td><a target=_blank href=/penggajian/pages/lampiran/SlipThr.php?&id=$baris[0]&jmlmsk=$jmlmsk&gapokgp=$gapokgp>$baris[5]</a></td>
                                 <td>$baris[6]</td>
                                 <td>$total</td>
                                 <td>
                                     <table>
                                       <TR class='isi'>
                                         <TD width='20px'>WJB</TD>
                                         <TD width='20px'>N</TD>
                                         <TD width='20px'><a href=?act=InputTambahJaga&action=TAMBAH&id=$baris[0]>+<a>/<a href=?act=InputTidakHadir&action=TAMBAH&id=$baris[0]>-</a></TD>
                                         <TD width='20px'><a href=?act=InputJagaMalam&action=TAMBAH&id=$baris[0]>MLM</a></TD>
                                         <TD width='20px'>+/-</TD>
                                         <TD width='20px'>KS</TD>
                                         <TD width='20px'>A</TD>
                                         <TD width='20px'>S</TD>
                                         <TD width='20px'>C</TD>
                                         <TD width='20px'>I</TD>
                                       </TR>
                                       <TR class='isi'>
                                         <TD>$jmlmsk</TD> 
                                         <TD>$ttln</TD>
                                         <TD>$tmbh</TD>
                                         <TD>$jgmlm</TD>
                                         <TD>$sisamlm</TD>
                                         <TD>$ks</TD>
                                         <TD>$ttla</TD>
                                         <TD>$ttls</TD>
                                         <TD>$ttlc</TD>
                                         <TD>$ttli</TD>
                                       </TR>
                                     </table>
				 </td>
                                 <td>
                                     <table>
                                       <TR class='isi'>
                                         <TD width='20px'><a href=?act=DetailPresensi&action=TAMBAH&id=$baris[0]>HB</a></TD>
                                         <TD width='20px'><a href=?act=DetailPresensi&action=TAMBAH&id=$baris[0]>HR</a></TD>
                                       </TR>
				       <TR class='isi'>
                                         <TD>$hb</TD>
                                         <TD>$hr</TD>
                                       </TR>
                                     </table>
				  </td>
				  <td>
                                     <table>
                                       <TR class='isi'>
                                         <TD width='100px'>Gaji Pokok</TD>
					 <TD width='100px'>Tnj.Jabatan</TD>
                                         <TD width='100px'>Tunjangan</TD>
                                         <TD width='100px'>Tmbh.Jg.Mlm</TD>
					 <TD width='100px'>Tmbh.jaga</TD>
					 <TD width='100px'>Tnj.Kehadiran</TD>
					 <TD width='120px'><i>Jml.Gaji & Tunj</i></TD>
                                       </TR>
				       <TR class='isi'>
                                         <TD>".formatDuit($gapok)."</TD>
					 <TD>".formatDuit($tnjjbtn)."</TD>
                                         <TD>
                                            <table width='300px'>";
                                            echo "<tr class='isi3'><td width='150px'>Tunjangan KaSift</td><td>: ".formatDuit($ttlkasift)."</td></tr>";

                                            $_sql16="select master_tunjangan_harian.nama,
				             master_tunjangan_harian.tnj
					     from pnm_tnj_harian,master_tunjangan_harian
					     where pnm_tnj_harian.id_tnj=master_tunjangan_harian.id
					     and pnm_tnj_harian.id='$baris[0]'";
                                            $hasil16=bukaquery($_sql16);
                                            $tnjtnj=0;
                                            while($baris16 = mysqli_fetch_array($hasil16)) {
                                                $tnjtnj=$tnjtnj+($ttln*$baris16[1]);
                                                echo "<tr class='isi3'><td width='150px'>$baris16[0]</td><td>: ".formatDuit($ttln*$baris16[1])."</td></tr>";
                                            }
                                            
                                            $_sql50="select master_tunjangan_bulanan.nama,
				             master_tunjangan_bulanan.tnj
					     from pnm_tnj_bulanan,master_tunjangan_bulanan
					     where pnm_tnj_bulanan.id_tnj=master_tunjangan_bulanan.id
					     and pnm_tnj_bulanan.id='$baris[0]'";
                                            $hasil50=bukaquery($_sql50);
                                            $tnjtnjbln=0;
                                            while($baris50 = mysqli_fetch_array($hasil50)) {
                                                $tnjtnjbln=$tnjtnjbln+$baris50[1];
                                                echo "<tr class='isi3'><td width='150px'>$baris50[0]</td><td>: ".formatDuit($baris50[1])."</td></tr>";
                                            }
                                            $ttltnjtnj=$ttltnjtnj+$tnjtnj+$tnjtnjbln+$ttlkasift;
                                            $ttljmlgaji=$ttljmlgaji+$gapok+$tnjjbtn+$tnjtnj+$ttlkasift+$tmbhjgmlm+$tmbahanjg+$tnjhadir;
                                            echo"
                                            </table>
                                         </TD>
                                         <TD>".formatDuit($tmbhjgmlm)."</TD>
					 <TD>".formatDuit($tmbahanjg)."</TD>
					 <TD>".formatDuit($tnjhadir)."</TD>
					 <TD><i>".formatDuit($gapok+$tnjjbtn+$tnjtnj+$tnjtnjbln+$ttlkasift+$tmbhjgmlm+$tmbahanjg+$tnjhadir)."</i></TD>
                                       </TR>
                                     </table>
				 </td>
                                 <td>
                                     <table>
                                       <TR class='isi'>
                                         <TD width='100px'><a href=?act=DetailPresensi&action=TAMBAH&id=$baris[0]>Hari/Libur Biasa</a></TD>
                                         <TD width='100px'><a href=?act=DetailPresensi&action=TAMBAH&id=$baris[0]>Hari Raya</a></TD>
                                         <TD width='100px'><a href=?act=DetailPresensi&action=TAMBAH&id=$baris[0]><i>Jumlah Lembur</i></a></TD>
                                       </TR>
				       <TR class='isi'>
                                         <TD>".formatDuit($lemburhb)."</TD>
                                         <TD>".formatDuit($lemburhr)."</TD>
                                         <TD><i>".formatDuit($lemburhb+$lemburhr)."</i></TD>
                                       </TR>
                                     </table>
				  </td>
				  <td>
                                     <table>
                                       <TR class='isi'>
                                         <TD width='65px'>Index Kary</TD>
					 <TD width='65px'><a href=?act=InputPasien&action=TAMBAH&id=$baris[0]>Index Jaga</a></TD>
					 <TD width='65px'>Total Index</TD>
                                         <TD width='100px'>Total Insentif</TD>
                                         <TD width='100px'><a href=?act=InputTindakan&action=TAMBAH&id=$baris[0]>Tindakan Medis</a></TD>
                                         <TD width='100px'><a href=?act=InputJasaLain&action=TAMBAH&id=$baris[0]>Jasa Lain</a></TD>
                                         <TD width='100px'><i>Jml.Tambahan</i></TD>
                                       </TR>
				       <TR class='isi'>
                                         <TD>$total</TD>
					 <TD>$indexjaga</TD>
					 <TD>$ttlindex</TD>
                                         <TD width='100px'>";
                                            $_sql23="";
                                            if($baris[6]!="DIR"){
                                                $_sql23="SELECT ($ttlindex/sum(indextotal.ttl))*((indexins.persen/100)*total_insentif)
                                                    from indextotal,indexins,set_insentif where
                                                    set_insentif.tahun='$tahun' and set_insentif.bulan='$bulanindex' and
                                                    indextotal.kdindex=indexins.dep_id and
                                                    indextotal.kdindex='$baris[6]'";
                                                
                                            }else if($baris[6]=="DIR"){
                                                $_sql23="SELECT ($ttlindex/sum(indextotal.ttl))*((indexins.persen/100)*total_insentif)*2
                                                    from indextotal,indexins,set_insentif where
                                                    set_insentif.tahun='$tahun' and set_insentif.bulan='$bulanindex' and
                                                    indextotal.kdindex=indexins.dep_id and
                                                    indextotal.kdindex='MNJ'";
                                                if($baris[0]=="43"){
                                                    $_sql23="SELECT ($ttlindex/sum(indextotal.ttl))*((indexins.persen/100)*total_insentif)*1.85
                                                    from indextotal,indexins,set_insentif where
                                                    set_insentif.tahun='$tahun' and set_insentif.bulan='$bulanindex' and
                                                    indextotal.kdindex=indexins.dep_id and
                                                    indextotal.kdindex='MNJ'";                                                    
                                                }

                                            }
                                            
                                            $hasil23=bukaquery($_sql23);                                            
                                            $baris23 = mysqli_fetch_array($hasil23);
                                            $ttlinsentif=$baris23[0];
                                            $ttlttlinsentif=$ttlttlinsentif+$ttlinsentif;
                                            $jmltmb=$ttlinsentif+$jm+$jl;
                                            $ttljmltmb=$ttljmltmb+$jmltmb;

                                            $ttlgaji=$jmltmb+$lemburhb+$lemburhr+$gapok+$tnjjbtn+$tnjtnj+$tnjtnjbln+$ttlkasift+$tmbhjgmlm+$tmbahanjg+$tnjhadir;
                                            $ttlttlgaji=$ttlttlgaji+$ttlgaji;
                                            echo formatDuit($ttlinsentif)."
                                         </TD>
                                         <TD>".formatDuit($jm)."</TD>
                                         <TD>".formatDuit($jl)."</TD>
                                         <TD><i>".formatDuit($jmltmb)."</i></TD>
                                       </TR>
                                     </table>
				  </td>
                                  <td><font size='2' color='green'><i>".formatDuit($ttlgaji)."</i></font></td>
                                  <td>";
                                        $_sql25="select potongan.jamsostek,
                                                potongan.dansos,
                                                potongan.simwajib,
                                                potongan.angkop,
                                                potongan.angla,
                                                potongan.telpri,
                                                potongan.pajak,
                                                potongan.pribadi,
                                                potongan.lain from potongan
                                                where potongan.id='$baris[0]' and
                                                potongan.tahun='$tahun' and potongan.bulan='$bulanindex' ";
                                        $hasil25=bukaquery($_sql25);
                                        $baris25 = mysqli_fetch_array($hasil25);
                                        $jamsostek   = $baris25[0];
                                        $dansos      = $baris25[1];
                                        $simwajib    = $baris25[2];
                                        $angkop      = $baris25[3];
                                        $angla       = $baris25[4];
                                        $telpri      = $baris25[5];
                                        $pajak       = $baris25[6];
                                        $pribadi     = $baris25[7];
                                        $lain        = $baris25[8];
                                        $ktg         = $baris25[9];
                                        
                                        if(empty ($baris25[0])){
                                            $jamsostek=0;
                                        }else {
                                            $jamsostek=$baris25[0];
                                        }
                                        $ttljamsostek=$ttljamsostek+$jamsostek;
                                        
                                        if(empty ($baris25[1])){
                                            $dansos=0;
                                        }else {
                                            $dansos=$baris25[1];
                                        }
                                        $ttldansos=$ttldansos+$dansos;

                                        if(empty ($baris25[2])){
                                            $simwajib=0;
                                        }else {
                                            $simwajib=$baris25[2];
                                        }
                                        $ttlsimwajib=$ttlsimwajib+$simwajib;

                                        if(empty ($baris25[3])){
                                            $angkop=0;
                                        }else {
                                            $angkop=$baris25[3];
                                        }
                                        $ttlangkop=$ttlangkop+$angkop;

                                        if(empty ($baris25[4])){
                                            $angla=0;
                                        }else {
                                            $angla=$baris25[4];
                                        }
                                        $ttlangla=$ttlangla+$angla;

                                        if(empty ($baris25[5])){
                                            $telpri=0;
                                        }else {
                                            $telpri=$baris25[5];
                                        }
                                        $ttltelpri=$ttltelpri+$telpri;

                                        if(empty ($baris25[6])){
                                            $pajak=0;
                                        }else {
                                            $pajak=$baris25[6];
                                        }
                                        $ttlpajak=$ttlpajak+$pajak;

                                        if(empty ($baris25[7])){
                                            $pribadi=0;
                                        }else {
                                            $pribadi=$baris25[7];
                                        }
                                        $ttlpribadi=$ttlpribadi+$pribadi;

                                        if(empty ($baris25[8])){
                                            $lain=0+$alpha;
                                        }else {
                                            $lain=$baris25[8]+$alpha;
                                        }
                                        $ttllain=$ttllain+$lain;

                                        $ttlditerima=$ttlgaji-($jamsostek+$dansos+$simwajib+$angkop+$angla+$telpri+$pajak+$pribadi+$lain);
                                        $ttlttlditerima=$ttlttlditerima+$ttlditerima;
			         echo "
                                     <table>
                                       <TR class='isi'>
                                         <TD width='100px'><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>Jamsostek</a></TD>
                                         <TD width='100px'><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>Dana Sosial</a></TD>
                                         <TD width='100px'><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>Simp.Wajib</a></TD>
                                         <TD width='100px'><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>Ang.Koperasi</a></TD>
                                         <TD width='100px'><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>Ang.Lain</a></TD>
                                         <TD width='100px'><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>Telp.Pribadi</a></TD>
                                         <TD width='100px'><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>Pajak</a></TD>
                                         <TD width='100px'><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>Pribadi</a></TD>
                                         <TD width='100px'><a href=?act=InputPotongan&action=UBAH&id=$baris[0]>Potongan Lain</a></TD>
                                       </TR>
				       <TR class='isi'>
                                         <TD>".formatDuit($jamsostek)."</TD>
                                         <TD>".formatDuit($dansos)."</TD>
                                         <TD>".formatDuit($simwajib)."</TD>
                                         <TD>".formatDuit($angkop)."</TD>
                                         <TD>".formatDuit($angla)."</TD>
                                         <TD>".formatDuit($telpri)."</TD>
                                         <TD>".formatDuit($pajak)."</TD>
                                         <TD>".formatDuit($pribadi)."</TD>
                                         <TD>".formatDuit($lain)."</TD>
                                     </table>
				  </td>
                                  <TD><font size='2' color='green'><i>".formatDuit($ttlditerima)."</i></font></TD>
                               </tr>";
                    }
                    $ttlttlindex=$ttlindexjaga+$ttltotal;
            echo "<tr class='head'>
                         <td COLSPAN='9'><div align='right'>Total :&nbsp; </div></td>
                         <td>
                                     <table>
				       <TR class='isi5'>
                                         <TD width='100px'>".formatDuit($ttlgapok)."</TD>
					 <TD width='100px'>".formatDuit($ttltnjjbtn)."</TD>
                                         <TD width='300px'>".formatDuit($ttltnjtnj)."</TD>
                                         <TD width='100px'>".formatDuit($ttltmbhjgmlm)."</TD>
					 <TD width='100px'>".formatDuit($ttltmbahanjg)."</TD>
					 <TD width='100px'>".formatDuit($ttltnjhadir)."</TD>
					 <TD width='120px'><i>".formatDuit($ttljmlgaji)."</i></TD>
                                       </TR>
                                     </table>
			 </td>
                         <td>
                                     <table>
				       <TR class='isi5'>
                                         <TD width='100px'>".formatDuit($ttllemburhb)."</TD>
                                         <TD width='100px'>".formatDuit($ttllemburhr)."</TD>
                                         <TD width='100px'><i>".formatDuit($ttllemburhb+$ttllemburhr)."</i></TD>
                                       </TR>
                                     </table>
			 </td>
                         <td>
                                     <table>
				       <TR class='isi5'>
                                         <TD width='65px'>$ttltotal</TD>
					 <TD width='65px'>$ttlindexjaga</TD>
					 <TD width='65px'>$ttlttlindex</TD>
                                         <TD width='105px'>".formatDuit($ttlttlinsentif)."</TD>
                                         <TD width='105px'>".formatDuit($ttljm)."</TD>
                                         <TD width='105px'>".formatDuit($ttljasalain)."</TD>
                                         <TD width='105px'><i>".formatDuit($ttljmltmb)."</i></TD>
                                       </TR>
                                     </table>
			 </td>
                         <td width='100px'><div align='center'><font size='2' color='green'><i>".formatDuit($ttlttlgaji)."</i></font></div></td>
                         <td>
                                     <table>
				       <TR class='isi5'>
                                         <TD width='100px'>".formatDuit($ttljamsostek)."</TD>
                                         <TD width='100px'>".formatDuit($ttldansos)."</TD>
                                         <TD width='100px'>".formatDuit($ttlsimwajib)."</TD>
                                         <TD width='100px'>".formatDuit($ttlangkop)."</TD>
                                         <TD width='100px'>".formatDuit($ttlangla)."</TD>
                                         <TD width='100px'>".formatDuit($ttltelpri)."</TD>
                                         <TD width='100px'>".formatDuit($ttlpajak)."</TD>
                                         <TD width='100px'>".formatDuit($ttlpribadi)."</TD>
                                         <TD width='100px'>".formatDuit($ttllain)."</TD>
                                     </table>
			 </td>
                         <TD><font size='2' color='green'><i>".formatDuit($ttlttlditerima)."</i></font></TD>
                    </tr>
                  </table>";

        } else {echo "<b>Data Lampiran masih kosong !</b>";}
    ?>
    </div>
            </form>
       <?php
            if(mysqli_num_rows($hasil)!=0) {
                echo("Data : $jumlah <a target=_blank href=/penggajian/pages/lampiran/LaporanLampiran.php?&keyword=$keyword>| Laporan1 </a>
                      <a target=_blank href=/penggajian/pages/lampiran/LaporanLampiran2.php?&keyword=$keyword>| Laporan2 </a>
                      <a target=_blank href=/penggajian/pages/lampiran/LaporanLampiran3.php?&keyword=$keyword>| Laporan3 </a>
                      <a target=_blank href=/penggajian/pages/lampiran/LaporanLampiran4.php?&keyword=$keyword>| Laporan4 </a>
                      <a target=_blank href=/penggajian/pages/lampiran/LaporanTransfer.php?&keyword=$keyword>| Transfer1 </a>
                      <a target=_blank href=/penggajian/pages/lampiran/LaporanTransfer2.php?&keyword=$keyword>| Transfer2 </a>
                      <a target=_blank href=/penggajian/pages/lampiran/LaporanCash.php?&keyword=$keyword>| Cash1 </a>
                      <a target=_blank href=/penggajian/pages/lampiran/LaporanCash2.php?&keyword=$keyword>| Cash2 </a>
                      <a target=_blank href=/penggajian/pages/lampiran/LaporanTHR.php?&keyword=$keyword>| Laporan THR </a>
                      <a target=_blank href=/penggajian/pages/lampiran/LaporanTHR2.php?&keyword=$keyword>| Laporan THR2 </a>");
             }
       ?>
    </div>
</div>

</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>