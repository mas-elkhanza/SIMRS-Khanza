<?php

/*header("Content-type: application/x-msdownload");
header("Content-Disposition: attachment; filename=GajiSp2.xls");
header("Pragma: no-cache");
header("Expires: 0");
print isset($header)?$header:NULL;*/

 include '../../../conf/conf.php';
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baristahun   = mysqli_fetch_row($hasil);
   $tahun     = empty($baristahun[0])?date("Y"):$baristahun[0];
   $blnini    = empty($baristahun[1])?date("m"):$baristahun[1];
   $hari      = empty($baristahun[2])?date("d"):$baristahun[2];
   $bln_leng  = strlen($blnini);
   $bulan     = "0";
   if ($bln_leng==1){
       $bulan="0".$blnini;
   }else{
       $bulan=$blnini;
   }

   $bulanindex = empty($baristahun[1])?date("m"):$baristahun[1];

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
            <caption><center>
                           <font color='999999' size='1'  face='Arial'><b>
                           Jasa Medis Tahun ".$tahun." </b></font></center></caption>
             <!-- awal hiden<tr>
             <td>
            ";
        $id=$_GET['id'];
        
        $_sql = "select pegawai.id,pegawai.nik,pegawai.nama,pegawai.jbtn,pegawai.pendidikan,pegawai.mulai_kerja,pegawai.wajibmasuk,departemen.nama as departemen,
                kelompok_jabatan.indek as indekkelompok,resiko_kerja.indek as indekresiko,emergency_index.indek as indekemergency,jnj_jabatan.nama as jnj_jabatan,
                jnj_jabatan.indek as indekjabatan,jnj_jabatan.tnj,pegawai.indexins,CONCAT(FLOOR(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'),
                DATE_FORMAT(mulai_kerja, '%Y%m'))/12), ' Tahun ',MOD(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'), DATE_FORMAT(mulai_kerja, '%Y%m')),12), ' Bulan ') as lama,
                pendidikan.indek as index_pendidikan,(To_days('$tahun-$bulan-$hari')-to_days(mulai_kerja))/365 as masker,stts_kerja.indek as index_status,
                pegawai.indek as index_struktural,pegawai.pengurang,pegawai.mulai_kontrak,CONCAT(FLOOR(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'),DATE_FORMAT(mulai_kontrak, '%Y%m'))/12), ' Tahun ',MOD(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'), DATE_FORMAT(mulai_kontrak, '%Y%m')),12), ' Bulan ') as lamakontrak,
                (To_days('$tahun-$bulan-$hari')-to_days(mulai_kontrak))/365 as maskon, pegawai.cuti_diambil,pegawai.dankes
                from pegawai inner join pendidikan inner join stts_kerja inner join kelompok_jabatan inner join resiko_kerja inner join emergency_index
                inner join jnj_jabatan inner join departemen on pegawai.departemen=departemen.dep_id and pegawai.pendidikan=pendidikan.tingkat and pegawai.stts_kerja=stts_kerja.stts and pegawai.jnj_jabatan=jnj_jabatan.kode
                and pegawai.kode_kelompok=kelompok_jabatan.kode_kelompok and pegawai.kode_resiko=resiko_kerja.kode_resiko and pegawai.kode_emergency=emergency_index.kode_emergency
                where pegawai.stts_aktif<>'KELUAR' and pegawai.id ='".$id."'  ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        $hasilcari=bukaquery($_sql);
              //untuk mencari nilai referensinya
		//hapusinput("delete from  indekref");
		while($baris = mysqli_fetch_array($hasilcari)) {
			$masa_kerja=0;
                          if($baris["masker"]<1){
                             $masa_kerja=0;
                          }else if(($baris["masker"]>=1)&&($baris["masker"]<2)){
                             $masa_kerja=2;
                          }else if(($baris["masker"]>=2)&&($baris["masker"]<3)){
                             $masa_kerja=4;
                          }else if(($baris["masker"]>=3)&&($baris["masker"]<4)){
                             $masa_kerja=6;
                          }else if(($baris["masker"]>=4)&&($baris["masker"]<5)){
                             $masa_kerja=8;
                          }else if(($baris["masker"]>=5)&&($baris["masker"]<6)){
                             $masa_kerja=10;
                          }else if(($baris["masker"]>=6)&&($baris["masker"]<7)){
                             $masa_kerja=12;
                          }else if($baris["masker"]>=7){
                             $masa_kerja=14;
                          }
                          
                          $indexevaluasi= getOne("select evaluasi_kinerja.indek from evaluasi_kinerja inner join evaluasi_kinerja_pegawai 
                                        on evaluasi_kinerja_pegawai.kode_evaluasi=evaluasi_kinerja.kode_evaluasi where 
                                        evaluasi_kinerja_pegawai.id='$baris[0]' order by evaluasi_kinerja_pegawai.tahun,
                                        evaluasi_kinerja_pegawai.bulan desc limit 1");
                          if(empty($indexevaluasi)){
                            $indexevaluasi=0;
                          }

                          $indexpencapaian= getOne("select pencapaian_kinerja.indek from pencapaian_kinerja inner join pencapaian_kinerja_pegawai 
                                                on pencapaian_kinerja_pegawai.kode_pencapaian=pencapaian_kinerja.kode_pencapaian where 
                                                pencapaian_kinerja_pegawai.id='$baris[0]' order by pencapaian_kinerja_pegawai.tahun,
                                                pencapaian_kinerja_pegawai.bulan desc limit 1");
                          if(empty($indexpencapaian)){
                            $indexpencapaian=0;
                          }
                          
                          $total=0;
                          if($baris["pengurang"]==0){
                            $total=($baris["index_pendidikan"]+$masa_kerja+$baris["index_status"]+$baris["index_struktural"]+
                                    $baris["indekjabatan"]+$baris["indekkelompok"]+$baris["indekresiko"]+$baris["indekemergency"]+
                                    $indexevaluasi+$indexpencapaian);
                          }else if($baris["pengurang"]>0){
                            $total=($baris["index_pendidikan"]+$masa_kerja+$baris["index_status"]+$baris["index_struktural"]+
                                    $baris["indekjabatan"]+$baris["indekkelompok"]+$baris["indekresiko"]+$baris["indekemergency"]+
                                    $indexevaluasi+$indexpencapaian)*($baris["pengurang"]/100);
                          } 
                          
                          $totalind=($baris["index_pendidikan"]+$masa_kerja+$baris["index_status"]+$baris["index_struktural"]);

                         $_sql2         = "SELECT normal-$jumlahlibur,jmlhr,normal FROM set_tahun";
			 $hasil2        = bukaquery($_sql2);
			 $baris2        = mysqli_fetch_row($hasil2);
			 $jmlmsk        = isset($baris2[0])?$baris2[0]:0;
                if(isset($baris["wajibmasuk"])==-1){
                    $jmlmsk=0;
                }else if(isset($baris["wajibmasuk"])==-2){
                    $jmlmsk=(isset($baris2[1])?$baris2[1]:0)-4;
                }else if($baris["wajibmasuk"]==-3){
                    $jmlmsk=(isset($baris2[1])?$baris2[1]:0)-2-$jumlahlibur;
                }else if($baris["wajibmasuk"]==-4){
                    $jmlmsk=(isset($baris2[2])?$baris2[2]:0);
                }else if($baris["wajibmasuk"]==-5){
                    $jmlmsk=getOne("select (if(h1='',0,1)+if(h2='',0,1)+if(h3='',0,1)+if(h4='',0,1)+if(h5='',0,1)+"
                                   ."if(h6='',0,1)+if(h7='',0,1)+if(h8='',0,1)+if(h9='',0,1)+if(h10='',0,1)+"
                                   ."if(h11='',0,1)+if(h12='',0,1)+if(h13='',0,1)+if(h14='',0,1)+if(h15='',0,1)+"
                                   ."if(h16='',0,1)+if(h17='',0,1)+if(h18='',0,1)+if(h19='',0,1)+if(h20='',0,1)+"
                                   ."if(h21='',0,1)+if(h22='',0,1)+if(h23='',0,1)+if(h24='',0,1)+if(h25='',0,1)+"
                                   ."if(h26='',0,1)+if(h27='',0,1)+if(h28='',0,1)+if(h29='',0,1)+if(h30='',0,1)+"
                                   ."if(h31='',0,1)) from jadwal_pegawai where id='$baris[0]' and tahun='$tahun' and bulan='$bulan'");
                }else if($baris["wajibmasuk"]!=0){
                    $jmlmsk=$baris["wajibmasuk"];
                }else if(!($baris["wajibmasuk"]==0)){
                    $jmlmsk=isset($baris2[0])?$baris2[0]:0;
                }
                            $_sql3    = "SELECT sum(jml)
                            from jgmlm  where id='$id'
                            and tgl like '%".$tahun."-".$bulan."%' group by id";
                            $hasil3   = bukaquery($_sql3);
                            $baris3   = mysqli_fetch_row($hasil3);
                            $jgmlm=isset($baris3[0])?$baris3[0]:0;
                            $sisamlm=(isset($baris3[0])?$baris3[0]:0)-0;

                            $_sql4    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$id'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='A' group by id";
                            $hasil4   = bukaquery($_sql4);
                            $baris4   = mysqli_fetch_row($hasil4);
                            $ttla     = isset($baris4[0])?$baris4[0]:0;

                            $_sql5    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$id'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='S' group by id";
                            $hasil5   = bukaquery($_sql5);
                            $baris5   = mysqli_fetch_row($hasil5);
                            $ttls     = isset($baris5[0])?$baris5[0]:0;

                            $_sql6    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$id'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='C' group by id";
                            $hasil6   = bukaquery($_sql6);
                            $baris6   = mysqli_fetch_row($hasil6);
                            $ttlc     = (isset($baris6[0])?$baris6[0]:0)+getOne("select sum(jumlah) from pengajuan_cuti where tanggal_awal like '%".$tahun."-".$bulan."%' and status='Disetujui' and nik='".$baris["nik"]."'");
                
                            $_sql7    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$id'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='I' group by id";
                            $hasil7   = bukaquery($_sql7);
                            $baris7   = mysqli_fetch_row($hasil7);
                            $ttli     = isset($baris7[0])?$baris7[0]:0;

                            $_sql8    = "SELECT sum(jml)
                            from tambahjaga  where id='$id'
                            and tgl like '%".$tahun."-".$bulan."%' group by id";
                            $hasil8   = bukaquery($_sql8);
                            $baris8   = mysqli_fetch_row($hasil8);
                            $ttltmb   = isset($baris8[0])?$baris8[0]:0;

                            $ttln=getOne("select count(rekap_presensi.id) from rekap_presensi where rekap_presensi.id='$baris[0]' and rekap_presensi.jam_datang like '%$tahun-$bulan%'")+$ttltmb;
			//bukainput("insert into indekref values('$baris["indexins"]','$ttln','$total')");
			echo "<table width='270px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
			        <tr class='isi6'>
				  <td width='60px'>Nama</td><td width='3px'>:</td><td width='90px'>".$baris["nama"]."</td>
				</tr>
				<tr class='isi6'>
				  <td width='60px'>Bulan</td><td width='3px'>:</td><td width='90px'>".$bulan."</td>
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
		    $indexjaga=round($baris22[0],2);

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
                $ttlbpjs=0;
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
                    while($baris = mysqli_fetch_array($hasil)) {
                        $_sqlgp    = "SELECT `gapok1`, `kenaikan`, `maksimal`
                          from pendidikan  where tingkat='".$baris["pendidikan"]."' ";
                          $hasilgp    = bukaquery($_sqlgp);
                          $barisgp    = mysqli_fetch_array($hasilgp);
                          $gapokgp    = 0;
                          @$gapok1    = $barisgp["gapok1"];
                          @$kenaikan  = $barisgp["kenaikan"];
                          @$maksimal  = $barisgp["maksimal"];
                          
                          if($baris["maskon"]<$maksimal){
                             $gapokgp=$gapok1+($kenaikan*round($baris["maskon"]));
                          }elseif($baris["maskon"]>=$maksimal){
                             $gapokgp=$gapok1+($kenaikan*$maksimal);
                          }

                          $masa_kerja=0;
                          if($baris["masker"]<1){
                             $masa_kerja=0;
                          }else if(($baris["masker"]>=1)&&($baris["masker"]<2)){
                             $masa_kerja=2;
                          }else if(($baris["masker"]>=2)&&($baris["masker"]<3)){
                             $masa_kerja=4;
                          }else if(($baris["masker"]>=3)&&($baris["masker"]<4)){
                             $masa_kerja=6;
                          }else if(($baris["masker"]>=4)&&($baris["masker"]<5)){
                             $masa_kerja=8;
                          }else if(($baris["masker"]>=5)&&($baris["masker"]<6)){
                             $masa_kerja=10;
                          }else if(($baris["masker"]>=6)&&($baris["masker"]<7)){
                             $masa_kerja=12;
                          }else if($baris["masker"]>=7){
                             $masa_kerja=14;
                          }
                          
                          $indexevaluasi= getOne("select evaluasi_kinerja.indek from evaluasi_kinerja inner join evaluasi_kinerja_pegawai 
                                        on evaluasi_kinerja_pegawai.kode_evaluasi=evaluasi_kinerja.kode_evaluasi where 
                                        evaluasi_kinerja_pegawai.id='$baris[0]' order by evaluasi_kinerja_pegawai.tahun,
                                        evaluasi_kinerja_pegawai.bulan desc limit 1");
                          if(empty($indexevaluasi)){
                            $indexevaluasi=0;
                          }

                          $indexpencapaian= getOne("select pencapaian_kinerja.indek from pencapaian_kinerja inner join pencapaian_kinerja_pegawai 
                                                on pencapaian_kinerja_pegawai.kode_pencapaian=pencapaian_kinerja.kode_pencapaian where 
                                                pencapaian_kinerja_pegawai.id='$baris[0]' order by pencapaian_kinerja_pegawai.tahun,
                                                pencapaian_kinerja_pegawai.bulan desc limit 1");
                          if(empty($indexpencapaian)){
                            $indexpencapaian=0;
                          }
                          
                          $total=0;
                          if($baris["pengurang"]==0){
                            $total=($baris["index_pendidikan"]+$masa_kerja+$baris["index_status"]+$baris["index_struktural"]+
                                    $baris["indekjabatan"]+$baris["indekkelompok"]+$baris["indekresiko"]+$baris["indekemergency"]+
                                    $indexevaluasi+$indexpencapaian);
                          }else if($baris["pengurang"]>0){
                            $total=($baris["index_pendidikan"]+$masa_kerja+$baris["index_status"]+$baris["index_struktural"]+
                                    $baris["indekjabatan"]+$baris["indekkelompok"]+$baris["indekresiko"]+$baris["indekemergency"]+
                                    $indexevaluasi+$indexpencapaian)*($baris["pengurang"]/100);
                          } 
                          $ttltotal=$ttltotal+$total;

                         $_sql2         = "SELECT normal-$jumlahlibur,jmlhr,normal FROM set_tahun";
			 $hasil2        = bukaquery($_sql2);
			 $baris2        = mysqli_fetch_row($hasil2);
			 $jmlmsk         = isset($baris2[0])?$baris2[0]:0;
                             if($baris["wajibmasuk"]==-1){
                                 $jmlmsk=0;
                             }else if($baris["wajibmasuk"]==-2){
                                 $jmlmsk=(isset($baris2[1])?$baris2[1]:0)-4;
                             }else if($baris["wajibmasuk"]==-3){
                                 $jmlmsk=(isset($baris2[1])?$baris2[1]:0)-2-$jumlahlibur;
                             }else if($baris["wajibmasuk"]==-4){
                                 $jmlmsk=isset($baris2[2])?$baris2[2]:0;
                             }else if($baris["wajibmasuk"]==-5){
                                 $jmlmsk=getOne("select (if(h1='',0,1)+if(h2='',0,1)+if(h3='',0,1)+if(h4='',0,1)+if(h5='',0,1)+"
                                               ."if(h6='',0,1)+if(h7='',0,1)+if(h8='',0,1)+if(h9='',0,1)+if(h10='',0,1)+"
                                               ."if(h11='',0,1)+if(h12='',0,1)+if(h13='',0,1)+if(h14='',0,1)+if(h15='',0,1)+"
                                               ."if(h16='',0,1)+if(h17='',0,1)+if(h18='',0,1)+if(h19='',0,1)+if(h20='',0,1)+"
                                               ."if(h21='',0,1)+if(h22='',0,1)+if(h23='',0,1)+if(h24='',0,1)+if(h25='',0,1)+"
                                               ."if(h26='',0,1)+if(h27='',0,1)+if(h28='',0,1)+if(h29='',0,1)+if(h30='',0,1)+"
                                               ."if(h31='',0,1)) from jadwal_pegawai where id='$baris[0]' and tahun='$tahun' and bulan='$bulan'");
                             }else if($baris["wajibmasuk"]!=0){
                                 $jmlmsk=$baris["wajibmasuk"];
                             }else if(!($baris["wajibmasuk"]==0)){
                                 $jmlmsk=isset($baris2[0])?$baris2[0]:0;
                             }

                            $_sql3    = "SELECT sum(jml)
                            from jgmlm  where id='$id'
                            and tgl like '%".$tahun."-".$bulan."%' group by id";
                            $hasil3   = bukaquery($_sql3);
                            $baris3   = mysqli_fetch_row($hasil3);
                            $jgmlm    = isset($baris3[0])?$baris3[0]:0;
                            $sisamlm=(isset($baris3[0])?$baris3[0]:0)-0;

                            $_sql4    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$id'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='A' group by id";
                            $hasil4   = bukaquery($_sql4);
                            $baris4   = mysqli_fetch_row($hasil4);
                            $ttla     = isset($baris4[0])?$baris4[0]:0;

                            $_sql5    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='S' group by id";
                            $hasil5   = bukaquery($_sql5);
                            $baris5   = mysqli_fetch_row($hasil5);
                            $ttls     = isset($baris5[0])?$baris5[0]:0;

                            $_sql6    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='C' group by id";
                            $hasil6   = bukaquery($_sql6);
                            $baris6   = mysqli_fetch_row($hasil6);
                            $ttlc     = (isset($baris6[0])?$baris6[0]:0)+getOne("select sum(jumlah) from pengajuan_cuti where tanggal_awal like '%".$tahun."-".$bulan."%' and status='Disetujui' and nik='".$baris["nik"]."'");
                                
                            $_sql7    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' and jns='I' group by id";
                            $hasil7   = bukaquery($_sql7);
                            $baris7   = mysqli_fetch_row($hasil7);
                            $ttli     = isset($baris7[0])?$baris7[0]:0;

                            $_sql8    = "SELECT sum(jml)
                            from tambahjaga  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' group by id";
                            $hasil8   = bukaquery($_sql8);
                            $baris8   = mysqli_fetch_row($hasil8);
                            $ttltmb   = isset($baris8[0])?$baris8[0]:0;

                            $ttln=getOne("select count(rekap_presensi.id) from rekap_presensi where rekap_presensi.id='$baris[0]' and rekap_presensi.jam_datang like '%$tahun-$bulan%'")+$ttltmb;
                            $tmbh=$ttltmb;

			    $_sql9    = "SELECT id,jmlks,bsr from kasift  where id='$baris[0]'";
                            $hasil9   = bukaquery($_sql9);
                            $baris9   = mysqli_fetch_row($hasil9);
                            
                            $ks   = isset($baris9[1])?$baris9[1]:0;
                            $bsrkasift=isset($baris9[2])?$baris9[2]:0;
                            if(isset($baris9[1])!=0){
                                $ks=$baris9[1];
                            }else if(isset($baris9[1])==0){
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
                                $hb   = isset($baris10[0])?$baris10[0]:0;

			    $_sql11="select sum(presensi.lembur)
                                from presensi
                                where presensi.id='$baris[0]' and presensi.tgl like '%".$tahun."-".$bulan."%'
                                and presensi.jns='HR'
                                group by presensi.id";
			    $hasil11=bukaquery($_sql11);
			    $baris11 = mysqli_fetch_array($hasil11);
                                $hr   = isset($baris11[0])?$baris11[0]:0;

			    $gapok=0;
			    if(empty ($gapokgp)){
                                $gapok=0;
                            }else {
				$gapok=$gapokgp;
			    }
                            $ttlgapok=$ttlgapok+$gapok;

			    $tnjjbtn=0;
		            if(empty ($baris["tnj"])){
                                $tnjjbtn=0;
                            }else {
				$tnjjbtn=$baris["tnj"];
			    }
                            $ttltnjjbtn=$ttltnjjbtn+$tnjjbtn;

                            $_sql17  ="SELECT tnj from set_jgmlm ";
			    $hasil17 =bukaquery($_sql17);
			    $baris17 = mysqli_fetch_array($hasil17);
                $tmbhjgmlm = $sisamlm*$baris17[0];
                            $ttltmbhjgmlm=$ttltmbhjgmlm+$tmbhjgmlm;

			    $_sql18  ="SELECT tnj from set_jgtambah where pendidikan='".$baris["pendidikan"]."'";
			    $hasil18 =bukaquery($_sql18);
			    $baris18 = mysqli_fetch_array($hasil18);
			    $tmbahanjg =0;
                $alpha=(isset($baris18[0])?$baris18[0]:0)*$ttla;
                                if(($tmbh>0)){
                                     $tmbahanjg=$tmbh*(isset($baris18[0])?$baris18[0]:0);
                                }
                            $ttltmbahanjg=$ttltmbahanjg+$tmbahanjg;

			    $_sql19  ="SELECT tnj from set_hadir ";
			    $hasil19 =bukaquery($_sql19);
			    $baris19 = mysqli_fetch_array($hasil19);
			    $tnjhadir =0;
			    if(($ttln>=$jmlmsk)&&($jmlmsk!=0)){
                    $tnjhadir=(isset($baris19[0])?$baris19[0]:0);
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
                $lemburhb=$hb*(isset($baris20[0])?$baris20[0]:0);
                            $ttllemburhb=$ttllemburhb+$lemburhb;

                            $_sql21  ="SELECT tnj from set_lemburhr";
			    $hasil21 =bukaquery($_sql21);
			    $baris21 = mysqli_fetch_array($hasil21);
                $lemburhr=$hr*(isset($baris21[0])?$baris21[0]:0);
                            $ttllemburhr=$ttllemburhr+$lemburhr;

                            $_sql22="";
                            if($baris["indexins"]!="DIR"){
                                $_sql22  ="SELECT ($ttln/sum(n))*100 from indekref where kdindex='".$baris["indexins"]."'";
                            }else if($baris["indexins"]=="DIR"){
                                $_sql22  ="SELECT ($ttln/sum(n))*100 from indekref where kdindex='MNJ'";
                            }
                            
			    $hasil22 =bukaquery($_sql22);
			    $baris22 = mysqli_fetch_array($hasil22);
                $indexjaga=round((isset($baris22[0])?$baris22[0]:0),2);

                            /*if($baris[0]=="1"){
                                $indexjaga=0;
                            }*/

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
                $jm   = (isset($baris24[0])?$baris24[0]:0);
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

                            $_sql29="select (pembagian_tuslah.persen/100)*set_tuslah.pendapatan_tuslah
                                from pembagian_tuslah,set_tuslah  where pembagian_tuslah.id='$baris[0]'
			        and set_tuslah.tahun='".$tahun."' and set_tuslah.bulan='".$bulanindex."'";
			    $hasil29=bukaquery($_sql29);
			    $baris29 = mysqli_fetch_array($hasil29);

                            $_sql31="SELECT ($ttlindex/sum(indextotal.ttl))*set_warung.bagian_kry
                                from indextotal,set_warung,pembagian_warung where pembagian_warung.id='$baris[0]'
                                and set_warung.tahun='$tahun' and set_warung.bulan='$bulanindex'
                                and indextotal.kdindex='".$baris["indexins"]."'";
			    $hasil31=bukaquery($_sql31);
			    $baris31 = mysqli_fetch_array($hasil31);
                            

			    $jl   = (isset($baris26[0])?$baris26[0]:0)+
                                        (isset($baris27[0])?$baris27[0]:0)+
                                        (isset($baris28[0])?$baris28[0]:0)+
                                        (isset($baris29[0])?$baris29[0]:0)+
                                        (isset($baris31[0])?$baris31[0]:0);
                                $ttljasalain=$ttljasalain+$jl;

             $_sql30="select tindakan.tgl,tindakan.nm_pasien,tindakan.diagnosa,master_tindakan.nama,
                                              tindakan.kamar,tindakan.jm,tindakan.jmlh
                                              from master_tindakan,tindakan
                                              where tindakan.tnd=master_tindakan.id and
                                              tindakan.id='$baris[0]'
                                              and tgl like '%".$tahun."-".$bulan."%' ";
                                         echo "<table width='270px' border='1' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                               <tr class='isi6'>
                                                   <td width='10px'><div align='center'><font size='1' face='Arial'>No</font></div></td>
                                                   <td width='30px'><div align='center'><font size='1' face='Arial'>Tgl</font></div></td>
                                                   <td width='60px'><div align='center'><font size='1' face='Arial'>Nm.Pasien</font></div></td>
                                                   <td width='20px'><div align='center'><font size='1' face='Arial'>Kls</font></div></td>
                                                   <td width='50px'><div align='center'><font size='1' face='Arial'>Tindakan</font></div></td>
                                                   <td width='50px'><div align='center'><font size='1' face='Arial'>JM</font></div></td>
                                                 </tr>";
				         $hasil30=bukaquery($_sql30);
                                         $x=1;
				         while($baris30 = mysqli_fetch_array($hasil30)) {
					     echo "<TR class='isi6'>
                                                   <td>$x</td>
                                                   <td>".substr($baris30[0],0,10)."</td>
                                                    <td>$baris30[1]</td>
                                                    <td>$baris30[4]</td>
                                                    <td>$baris30[3] $baris30[6]</td>
                                                    <td>$baris30[5]</td>
                                                 </tr>";
                                             $x++;
				         }
                                echo" 
                                      </table>                                      
				     <table width='270px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                        
				       ";
					 $_sql16="select master_tunjangan_harian.nama,
						    master_tunjangan_harian.tnj,master_tunjangan_harian.id
						    from pnm_tnj_harian,master_tunjangan_harian
						    where pnm_tnj_harian.id_tnj=master_tunjangan_harian.id
						    and pnm_tnj_harian.id='$baris[0]'";
                                            $hasil16=bukaquery($_sql16);
                                            $tnjtnj=0;
                                            while($baris16 = mysqli_fetch_array($hasil16)) {
                                                $tunjanganpengurang=0;
						$_sqltnjpengurang="select master_tunjangan_bulanan.tnj 
                                                    from master_tunjangan_bulanan inner join harian_kurangi_bulanan
                                                    inner join pnm_tnj_bulanan on master_tunjangan_bulanan.id=harian_kurangi_bulanan.bulanan
						    and pnm_tnj_bulanan.id_tnj=harian_kurangi_bulanan.bulanan where
						    harian_kurangi_bulanan.harian='".$baris16["id"]."' and pnm_tnj_bulanan.id='".$baris[0]."' ";
						$hasilpengurang=bukaquery($_sqltnjpengurang);
						$barispengurang=mysqli_fetch_array($hasilpengurang);
						$tunjanganpengurang=isset($barispengurang["tnj"])?$barispengurang["tnj"]:0;						
                                                    $nilaitunjangan=0;
                                                    $nilaitunjangan=($ttln*(isset($baris16[1])?$baris16[1]:0))-$tunjanganpengurang;
                                                    if($nilaitunjangan<0){
                                                        $nilaitunjangan=0;
                                                    }
												 
                                                $tnjtnj=$tnjtnj+$nilaitunjangan;
                                                echo "<tr class='isi6'>
						 <td width='130px'>$baris16[0]</td><td width='3px'>:</td><td width='140px'> ".formatDuit($nilaitunjangan)."</td></tr>";
                                         }
                                         
                                         echo " <TR class='isi6'>
                                         <TD width='130px'>Tambahan Lain :</TD><td></td><td></td>
				       </TR>";
                                         
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
									   
				   
					    $_sql23="";
                                            if($baris["indexins"]!="DIR"){
                                                $_sql23="SELECT ($ttlindex/sum(indextotal.ttl))*((indexins.persen/100)*total_insentif)
                                                    from indextotal,indexins,set_insentif where
                                                    set_insentif.tahun='$tahun' and set_insentif.bulan='$bulanindex' and
                                                    indextotal.kdindex=indexins.dep_id and
                                                    indextotal.kdindex='".$baris["indexins"]."'";

                                            }else if($baris["indexins"]=="DIR"){
                                                $_sql23="SELECT ($ttlindex/sum(indextotal.ttl))*((indexins.persen/100)*total_insentif)*2.3
                                                    from indextotal,indexins,set_insentif where
                                                    set_insentif.tahun='$tahun' and set_insentif.bulan='$bulanindex' and
                                                    indextotal.kdindex=indexins.dep_id and
                                                    indextotal.kdindex='MNJ'";
                                                if($baris[0]=="43"){
                                                    $_sql23="SELECT ($ttlindex/sum(indextotal.ttl))*((indexins.persen/100)*total_insentif)*1.80
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
			        
                                         $_sql30="select master_tindakan.nama,sum(tindakan.jm),count(master_tindakan.nama)
                                              from master_tindakan,tindakan
                                              where tindakan.tnd=master_tindakan.id and
                                              tindakan.id='$baris[0]'
                                              and tgl like '%".$tahun."-".$bulan."%'
                                              group by tindakan.tnd ";
				         $hasil30=bukaquery($_sql30);
				         while($baris30 = mysqli_fetch_array($hasil30)) {
					     //echo "<tr class='isi6'><td width='130px'>&nbsp;&nbsp;- $baris30[0]</td><td width='3px'>:</td><td width='140px'>&nbsp;$baris30[2]&nbsp; ".formatDuit($baris30[1])."</td></tr>";
				         }
                                echo" <TR class='isi6'>
                                         <TD width='130px'>Jasa Lain</TD><td width='3px'>:</td><td width='140px'>".formatDuit($jl)."</td>
				      </TR>
                                    
				      <TR class='isi6'>
                                         <TD width='130px'>&nbsp;&nbsp;&nbsp;<i>Jumlah Tindakan</i></TD>
					 <td width='3px'>:</td>
					 <td width='140px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>".formatDuit($jmltmb)."</i></td>
				     </TR>";
                                        $_sql25="select potongan.jamsostek,
                                                potongan.dansos,
                                                potongan.simwajib,
                                                potongan.angkop,
                                                potongan.angla,
                                                potongan.telpri,
                                                potongan.pajak,
                                                potongan.pribadi,
                                                potongan.lain,potongan.ktg,
                                                potongan.bpjs from potongan
                                                where potongan.id='$baris[0]' and
                                                potongan.tahun='$tahun' and potongan.bulan='$bulanindex' ";
                                        $hasil25=bukaquery($_sql25);
                                        $baris25 = mysqli_fetch_array($hasil25);
                                        $jamsostek   = isset($baris25[0])?$baris25[0]:0;
                                            $dansos      = isset($baris25[1])?validangka($baris25[1]):0;
                                            $simwajib    = isset($baris25[2])?validangka($baris25[2]):0;
                                            $angkop      = isset($baris25[3])?validangka($baris25[3]):0;
                                            $angla       = isset($baris25[4])?validangka($baris25[4]):0;
                                            $telpri      = isset($baris25[5])?validangka($baris25[5]):0;
                                            $pajak       = isset($baris25[6])?validangka($baris25[6]):0;
                                            $pribadi     = isset($baris25[7])?validangka($baris25[7]):0;
                                            $lain        = isset($baris25[8])?validangka($baris25[8]):0;
                                            $bpjs        = isset($baris25[9])?validangka($baris25[9]):0;

                                            $ttljamsostek=$ttljamsostek+$jamsostek;
                                            $ttldansos=$ttldansos+$dansos;
                                            $ttlsimwajib=$ttlsimwajib+$simwajib;
                                            $ttlangkop=$ttlangkop+$angkop;
                                            $ttlangla=$ttlangla+$angla;
                                            $ttltelpri=$ttltelpri+$telpri;
                                            $ttlpajak=$ttlpajak+$pajak;
                                            $ttlpribadi=$ttlpribadi+$pribadi;
                                            $ttllain=$ttllain+$lain;
                                            $ttlbpjs=$ttlbpjs+$bpjs;

                                        $ttlditerima=$ttlgaji-($bpjs+$jamsostek+$dansos+$simwajib+$angkop+$angla+$telpri+$pajak+$pribadi+$lain);
                                        $ttlttlditerima=$ttlttlditerima+$ttlditerima;
                                        
				 echo "
                                        <TR class='isi6'>
                                         <TD width='130px'>Potongan :</TD><td></td><td></td>
				       </TR>";
                                 if($bpjs!=0){
                                     echo"
				       <TR class='isi6'>
                                         <TD width='130px'>BPJS</TD><td width='3px'>:</td><td width='140px'>".formatDuit($bpjs)."</td>
				       </TR>";
                                 }
                                 
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
									 $_sqlj = "select count(angsuran_koperasi.id) from angsuran_koperasi,peminjaman_koperasi 
									           where angsuran_koperasi.id='$id' and angsuran_koperasi.id=peminjaman_koperasi.id
									           and angsuran_koperasi.tanggal_pinjam=peminjaman_koperasi.tanggal group by angsuran_koperasi.id";
									$hasilj=bukaquery($_sqlj);
									$barisj = mysqli_fetch_array($hasilj);
									$jml_sdh_angsur =$barisj[0];
                                     echo"
				       <TR class='isi6'>
                                         <TD width='130px'>Angsuran Koperasi</TD><td width='3px'>:</td><td width='140px'>".formatDuit($angkop)." (Ke $jml_sdh_angsur)</td>
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
					    <td width='140px' align=right><i>".formatDuit($bpjs+$jamsostek+$dansos+$simwajib+$angkop+$angla+$telpri+$pajak+$pribadi+$lain)."</i></td>
				     </TR> 
				       <TR class='isi6'>
                                         <TD width='130px'>&nbsp;&nbsp;&nbsp;<i>TOTAL JM DITERIMA</i></TD>
					 <td width='3px'>:</td>
					 <td width='140px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i>".formatDuit($ttlditerima)."</i></td>
				      </TR>
                                      <TR class='isi6'>
                                         <TD width='130px' colspan='3'>&nbsp;&nbsp;&nbsp;<i>". Terbilang($ttlditerima)."</i></td>
				      </TR>
                                       <TR class='isi6'>
                                         <TD width='130px'></TD><td></td><td><center>".getOne("select kabupaten from setting").", ".date('d-m-Y')."</center> </td>
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
                                         <TD width='130px'><center>( ".$baris["nama"]." )</center></TD>
					 <td width='3px'>&nbsp;</td>
					 <td width='140px'><center>( .................. )</center></td>
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
				      </TR>-->
                                     
                                        
                                 
                                      <TR class='isi6'>
                                         <TD width='130px' colspan='3'><center><font face='Arial' size='1' color='999999'>Rawat Jalan</font></center>";
                                         $_sql30="select rawatjalan.tgl,rawatjalan.nm_pasien,rawatjalan.diagnosa,master_tindakan.nama,
                                              rawatjalan.kamar,rawatjalan.jm,rawatjalan.jmlh,master_tindakan.jm
                                              from master_tindakan,rawatjalan
                                              where rawatjalan.tnd=master_tindakan.id and
                                              rawatjalan.id='$baris[0]'
                                              and tgl like '%".$tahun."-".$bulan."%' ";
                                         echo "<table width='270px' border='1' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                               <tr class='isi6'>
                                                   <td width='10px'><div align='center'><font size='1' face='Arial'>No</font></div></td>
                                                   <td width='40px'><div align='center'><font size='1' face='Arial'>Tgl</font></div></td>
                                                   <td width='50px'><div align='center'><font size='1' face='Arial'>Tindakan</font></div></td>
                                                   <td width='20px'><div align='center'><font size='1' face='Arial'>Jmlh</font></div></td>
                                                   <td width='50px'><div align='center'><font size='1' face='Arial'>JM</font></div></td>
                                                   <td width='50px'><div align='center'><font size='1' face='Arial'>Ttl.JM</font></div></td>
                                                 </tr>";
				         $hasil30=bukaquery($_sql30);
                                         $x=1;
                                         $jmrj=0;
				         while($baris30 = mysqli_fetch_array($hasil30)) {
					     echo "<TR class='isi6'>
                                                   <td>$x</td>
                                                   <td>".substr($baris30[0],0,10)."</td>
                                                    <td>$baris30[3]</td>
                                                    <td>$baris30[6]</td>
                                                    <td>$baris30[7]</td>
                                                    <td>$baris30[5]</td>
                                                 </tr>";
                                             $x++;
                                             $jmrj=$jmrj+$baris30[5];
				         }
                                echo" 
                                    
                                      </table>   
                                     </td>
				      </TR>
                                      <TR class='isi6'>
                                         <TD width='130px'>JM Rawat Jalan</TD>
					 <td width='3px'>:</td>
					 <td width='140px'>".formatDuit($jmrj)."</td>
				      </TR>
                                      <TR class='isi6'>
                                         <TD width='130px'>JM Tindakan </TD>
					 <td width='3px'>:</td>
					 <td width='140px'>".formatDuit($ttlditerima)."</td>
				      </TR>
                                      <TR class='isi6'>
                                         <TD width='130px'>Total Diterima </TD>
					 <td width='3px'>:</td>
					 <td width='140px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;".formatDuit($jmrj+$ttlditerima)."</td>
				      </TR>
                                       <TR class='isi6'>
					 <td colspan=3>".Terbilang($jmrj+$ttlditerima)."</td>
				      </TR>
				      <TR class='isi6'>
                                         <TD width='130px'></TD><td></td><td><center>".getOne("select kabupaten from setting").", ".date('d-m-Y')."</center> </td>
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
                                         <TD width='130px'><center>( ".$baris["nama"]." )</center></TD>
					 <td width='3px'>&nbsp;</td>
					 <td width='140px'><center>( .................. )</center></td>
				      </TR>
				      </table>
				      
                                        </td>                                     
                                     </tr>                                     
                                     
                                     </table>" ;
                    }
        }
        
    ?>
    </body>
</html>
