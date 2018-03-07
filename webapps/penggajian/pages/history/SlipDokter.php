<?php
 include '../../../conf/conf.php';
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

   $_sqllibur = "select `tanggal`, `ktg`
                        from set_hari_libur
                        where tanggal like '%".$tahun."-".$bulan."%' ORDER BY tanggal";
                $hasillibur=bukaquery($_sqllibur);
                $jumlahlibur=mysqli_num_rows($hasillibur);
?>
<html>
    <head>
        <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
	
   <?php
        echo "<table  border='0' align='left'>
             <caption><center><font color='777777' size='1'  face='Arial'><b>
                           Slip Gaji Dokter Tahun ".$tahun." Bulan ".$bulan." </b></font></center></caption>
             <tr>
             <td>
            ";
        $id=$_GET['id'];
        
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
                where ".$say." and pegawai.id ='".$id."' 
                order by pegawai.nik ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        $hasilcari=bukaquery($_sql);
              //untuk mencari nilai referensinya
		//hapusinput("delete from  indekref");
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
                          
                          $totalind=($baris[8]+$masa_kerja+$baris[10]+$baris[11]);

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
                            from jgmlm  where id='$id'
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
                            from ketidakhadiran  where id='$id'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='A' group by id";
                            $hasil4   = bukaquery($_sql4);
                            $baris4   = mysqli_fetch_row($hasil4);
                            $ttla     = $baris4[0];
                            if(empty ($baris4[0])){
                                $ttla=0;
                            }

                            $_sql5    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$id'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='S' group by id";
                            $hasil5   = bukaquery($_sql5);
                            $baris5   = mysqli_fetch_row($hasil5);
                            $ttls     = $baris5[0];
                            if(empty ($baris5[0])){
                                $ttls=0;
                            }

                            $_sql6    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$id'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='C' group by id";
                            $hasil6   = bukaquery($_sql6);
                            $baris6   = mysqli_fetch_row($hasil6);
                            $ttlc     = $baris6[0];
                            if(empty ($baris6[0])){
                                $ttlc=0;
                            }

                            $_sql7    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$id'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='I' group by id";
                            $hasil7   = bukaquery($_sql7);
                            $baris7   = mysqli_fetch_row($hasil7);
                            $ttli     = $baris7[0];
                            if(empty ($baris7[0])){
                                $ttli=0;
                            }

                            $_sql8    = "SELECT sum(jml)
                            from tambahjaga  where id='$id'
                            and tgl like '%".$tahun."-".$bulan."%' group by id";
                            $hasil8   = bukaquery($_sql8);
                            $baris8   = mysqli_fetch_row($hasil8);
                            $ttltmb   = $baris8[0];
                            if(empty ($baris8[0])){
                                $ttltmb=0;
                            }

                            $ttln=$jmlmsk+$ttltmb-($ttla+$ttls+$ttlc+$ttli);
			//bukainput("insert into indekref values('$baris[6]','$ttln','$total')");
			echo "<table width='270px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
			        <tr class='isi6'>
				  <td width='60px'>NIK</td><td width='3px'>:</td><td width='90px'>$baris[1]</td>
				  <td width='60px'>Nama</td><td width='3px'>:</td><td width='90px'>$baris[2]</td>
				</tr>
				<tr class='isi6'>
				  <td width='60px'>Jabatan</td><td width='3px'>:</td><td width='90px'>$baris[3]</td>
				  <td width='60px'>Index Kary</td><td width='3px'>:</td><td width='90px'>$totalind</td>
				</tr>
			     </table>";
		}

		//insert data ke total index
		$hasilindex=bukaquery("select kdindex,n,ttl from indekref");
		//untuk mencari nilai referensinya
		//hapusinput("delete from  indextotal");
		while($barisindex = mysqli_fetch_array($hasilindex)) {
		    $_sql22  ="SELECT ($barisindex[1]/sum(n))*100 from indekref where kdindex='$barisindex[0]'";
		    $hasil22 =bukaquery($_sql22);
		    $baris22 = mysqli_fetch_array($hasil22);
		    $indexjaga=$baris22[0];

		    $ttlindex=$barisindex[2]+$indexjaga;
		    //bukainput("insert into indextotal  values('$barisindex[0]','$ttlindex')");
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
        if(mysqli_num_rows($hasil)!=0) {	
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
                            from jgmlm  where id='$id'
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
                            from ketidakhadiran  where id='$id'
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

			    $_sql18  ="SELECT tnj from set_jgtambah where pendidikan='$baris[16]'";
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
                            
                        echo "                                        
				     <table width='270px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
					<caption><font size='1px' face='Arial' color='777777'><strong>Jasa Medis</strong></font></caption>
                                        <TR class='isi6'>
                                            <TD width='130px'>Tunjangan :</TD><td></td><td></td>
					 </TR>
				       ";
					 $_sql16="select master_tunjangan_harian.nama,
				         master_tunjangan_harian.tnj
					 from pnm_tnj_harian,master_tunjangan_harian
					 where pnm_tnj_harian.id_tnj=master_tunjangan_harian.id
					 and pnm_tnj_harian.id='$baris[0]'";
					 $hasil16=bukaquery($_sql16);
                                         $tnjtnj=0;
                                         while($baris16 = mysqli_fetch_array($hasil16)) {
                                                $tnjtnj=$tnjtnj+($ttln*$baris16[1]);
                                                echo "<tr class='isi6'>
						 <td width='130px'>$baris16[0]</td><td width='3px'>:</td><td width='140px'> ".formatDuit($ttln*$baris16[1])."</td></tr>";
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
                                                echo "<tr class='isi6'>
                                                <td width='130px'>$baris50[0]</td><td width='3px'>:</td><td width='140px'> ".formatDuit($baris50[1])."</td></tr>";
                                            }
                                         $ttltnjtnj=$ttltnjtnj+$tnjtnj+$tnjtnjbln+$ttlkasift;
                                         $ttljmlgaji=$ttljmlgaji+$gapok+$tnjjbtn+$tnjtnj+$tnjtnjbln+$ttlkasift+$tmbhjgmlm+$tmbahanjg+$tnjhadir;
									   
				    if($tmbhjgmlm!=0){
                                        echo"
					<TR class='isi6'>
                                          <TD width='130px'>Tunjangan Jaga Malam</TD><td width='3px'>:</td><td width='140px'>".formatDuit($tmbhjgmlm)."</td>
					</TR>";
                                    }				   
				    
                                    if($tmbahanjg!=0){
                                        echo "
					<TR class='isi6'>
                                          <TD width='130px'>Tunjangan Tambahan Jaga</TD><td width='3px'>:</td><td width='140px'>".formatDuit($tmbahanjg)."</td>
					</TR>";
                                    }
                                    
                                    echo "
					 <TR class='isi6'>
                                           <TD width='130px'>&nbsp;&nbsp;&nbsp;<i>Jumlah Gaji & Tunjangan</i></TD>
					   <td width='3px'>:</td>
					   <td width='140px' align=right><i>".formatDuit($gapok+$tnjjbtn+$tnjtnj+$tnjtnjbln+$ttlkasift+$tmbhjgmlm+$tmbahanjg+$tnjhadir)."</i></td>
					 </TR>";
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
			        echo "
                                      <TR class='isi6'>
                                         <TD width='130px'>Tindakan Medis </TD><td width='3px'></td><td width='140px'></td></TR>";
                                         $_sql30="select master_tindakan.nama,sum(tindakan.jm),count(master_tindakan.nama)
                                              from master_tindakan,tindakan
                                              where tindakan.tnd=master_tindakan.id and
                                              tindakan.id='$baris[0]'
                                              and tgl like '%".$tahun."-".$bulan."%'
                                              group by tindakan.tnd ";
				         $hasil30=bukaquery($_sql30);
				         while($baris30 = mysqli_fetch_array($hasil30)) {
					     echo "<tr class='isi6'><td width='130px'>&nbsp;&nbsp;- $baris30[0]</td><td width='3px'>:</td><td width='140px'>&nbsp;$baris30[2]&nbsp; ".formatDuit($baris30[1])."</td></tr>";
				         }
                                echo" 
                                      <TR class='isi6'>
                                         <TD width='130px'>Jasa Lain :</TD><td width='3px'></td><td width='140px'></td>
				      </TR>";
                                         
                                $_sqljs="select jasa_lain.ktg,jasa_lain.bsr_jasa
                                    from jasa_lain  where id='$baris[0]' 
                                    and thn='".$tahun."' and bln='".$bulanindex."' ";
                                $hasiljs=bukaquery($_sqljs);
                                while($barisjs = mysqli_fetch_array($hasiljs)) {
				      echo" 
                                      <TR class='isi6'>
                                         <TD width='130px'>".$barisjs[0]."</TD><td width='3px'>:</td><td width='140px'>".formatDuit($barisjs[1])."</td>
				      </TR>";
				}
                            
                                
                                echo "
				      <TR class='isi6'>
                                         <TD width='130px'>&nbsp;&nbsp;&nbsp;<i>Jumlah JM Tindakan & Tunjangan Diterima</i></TD>
					 <td width='3px'>:</td>
					 <td width='140px' align=right><i>".formatDuit($jmltmb)."</i></td>
				     </TR>";
                                        $_sql25="select potongan.jamsostek,
                                                potongan.dansos,
                                                potongan.simwajib,
                                                potongan.angkop,
                                                potongan.angla,
                                                potongan.telpri,
                                                potongan.pajak,
                                                potongan.pribadi,
                                                potongan.lain,potongan.ktg from potongan
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
                                       <TR class='isi6'>
                                         <TD width='130px'>&nbsp;&nbsp;&nbsp;<i><b><strong><font size='2px' face='Arial' color='777777'>Total Gaji</font></strong></b></i></TD>
					 <td width='3px'>:</td>
					 <td width='140px' align=right><i><b><strong><font size='2px' face='Arial' color='777777'>".formatDuit($ttlgaji)."</font></strong></b></i></td>
				     </TR>
                                        <TR class='isi6'>
                                         <TD width='130px'>Potongan Gaji :</TD><td></td><td></td>
				       </TR>";
                                 if($jamsostek!=0){
                                     echo"
				       <TR class='isi6'>
                                         <TD width='130px'>Jamsostek</TD><td width='3px'>:</td><td width='140px'>".formatDuit($jamsostek)."</td>
				       </TR>";
                                 }
                                 
                                 if($dansos!=0){
                                     echo"
				       <TR class='isi6'>
                                         <TD width='130px'>Dana Sosial</TD><td width='3px'>:</td><td width='140px'>".formatDuit($dansos)."</td>
				       </TR>";
                                 }    
                                 
                                 if($simwajib!=0){
                                     echo "
				       <TR class='isi6'>
                                         <TD width='130px'>Simpanan Wajib</TD><td width='3px'>:</td><td width='140px'>".formatDuit($simwajib)."</td>
				       </TR>";
                                 }
                                 
                                 if($angkop!=0){
                                     echo"
				       <TR class='isi6'>
                                         <TD width='130px'>Angsuran Koperasi</TD><td width='3px'>:</td><td width='140px'>".formatDuit($angkop)."</td>
				       </TR>";
                                 }  
                                 
                                 if($angla!=0){
                                     echo "
				       <TR class='isi6'>
                                         <TD width='130px'>Angsuran Lain</TD><td width='3px'>:</td><td width='140px'>".formatDuit($angla)."</td>
				       </TR>";
                                 }
                                 
                                 if($telpri!=0){
                                     echo "
				       <TR class='isi6'>
                                         <TD width='130px'>Telepon Pribadi</TD><td width='3px'>:</td><td width='140px'>".formatDuit($telpri)."</td>
				       </TR>";
                                 }
                                 
                                 if($pajak!=0){
                                     echo "
				       <TR class='isi6'>
                                         <TD width='130px'>Pajak</TD><td width='3px'>:</td><td width='140px'>".formatDuit($pajak)."</td>
				       </TR>";
                                 }
                                 
                                 if($pribadi!=0){
                                     echo "
				       <TR class='isi6'>
                                         <TD width='130px'>Pribadi</TD><td width='3px'>:</td><td width='140px'>".formatDuit($pribadi)."</td>
				       </TR>";
                                 }
                                 
                                 if(($lain-$alpha)>0){
                                     echo "
				       <TR class='isi6'>
                                         <TD width='130px'>$ktg</TD><td width='3px'>:</td><td width='140px'>".formatDuit($lain-$alpha)."</td>
				       </TR>";
                                 }
                                 
                                 if($alpha!=0){
                                     echo "
				       <TR class='isi6'>
                                         <TD width='130px'>Kurang Jaga</TD><td width='3px'>:</td><td width='140px'>".formatDuit($alpha)."</td>
				       </TR>";
                                 }
                                 echo"<TR class='isi6'>
                                            <TD width='130px'>&nbsp;&nbsp;&nbsp;<i>Jumlah Potongan </i></TD>
					    <td width='3px'>:</td>
					    <td width='140px' align=right><i>".formatDuit($jamsostek+$dansos+$simwajib+$angkop+$angla+$telpri+$pajak+$pribadi+$lain)."</i></td>
					 </TR> 
                                         
				       <TR class='isi6'>
                                         <TD width='130px'>&nbsp;&nbsp;&nbsp;<i>TOTAL JM DITERIMA</i></TD>
					 <td width='3px'>:</td>
					 <td width='140px' align=right><font size='2px' face='Arial' color='777777'><i>".formatDuit($ttlditerima)."</i></font></td>
				      </TR>
                                      <TR class='isi6'>
                                         <TD width='130px' colspan='3'>&nbsp;&nbsp;&nbsp;<i>". Terbilang($ttlditerima)."</i></td>
				      </TR>
				      <TR class='isi6'>
                                         <TD width='130px'></TD><td></td><td><center>Yogyakarta, ".date('d-m-Y')."</center> </td>
				      </TR>
				      <TR class='isi6'>
                                         <TD width='130px'><center>Penerima</center></TD>
					 <td width='3px'>&nbsp;</td>
					 <td width='140px'><center>Pengelola</center></td>
				      </TR>
				      <TR class='isi6'>
                                         <TD width='130px'>&nbsp;</TD>
					 <td width='3px'>&nbsp;</td>
					 <td width='140px'>&nbsp;</td>
				      </TR>
				      <TR class='isi6'>
                                         <TD width='130px'>&nbsp;</TD>
					 <td width='3px'>&nbsp;</td>
					 <td width='140px'>&nbsp;</td>
				      </TR>
				      <TR class='isi6'>
                                         <TD width='130px'><center>( $baris[2] )</center></TD>
					 <td width='3px'>&nbsp;</td>
					 <td width='140px'><center>( dr Lily T S Siagian )</center></td>
				      </TR>
				      </table>
				      <center><font size='1px' face='Arial' color='777777'><i><b>..Terima kasih atas kerjasamanya <br>dalam memberikan pelayanan yang berkualitas..</b></i></font></center>

                                        <center><font face='Arial' size='3' color='777777'>Rincian Tindakan</font></center>";
                                         $_sql30="select tindakan.tgl,tindakan.nm_pasien,tindakan.kamar,master_tindakan.nama
                                              from master_tindakan,tindakan
                                              where tindakan.tnd=master_tindakan.id and
                                              tindakan.id='$baris[0]'
                                              and tgl like '%".$tahun."-".$bulan."%' ";
                                         echo "<table width='270px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                               <tr class='isi6'>
                                                   <td width='50px'><div align='center'><font size='1' face='Arial'>Tgl</font></div></td>
                                                   <td width='50px'><div align='center'><font size='1' face='Arial'>Nm.Pasien</font></div></td>
                                                   <td width='50px'><div align='center'><font size='1' face='Arial'>Kamar</font></div></td>
                                                   <td width='50px'><div align='center'><font size='1' face='Arial'>Tindakan</font></div></td>
                                                 </tr>";
				         $hasil30=bukaquery($_sql30);
				         while($baris30 = mysqli_fetch_array($hasil30)) {
					     echo "<TR class='isi6'>
                                                   <td>".date("Y-m-d",$baris30[0])."</td>
                                                    <td>$baris30[1]</td>
                                                    <td>$baris30[2]</td>
                                                    <td>$baris30[3]</td>
                                                 </tr>";
				         }
                                echo" 
                                      </table>
                                      
                                     </td>
                                     </tr>
                                     </table>" ;
                    }
        }
        
    ?>
    </body>
</html>