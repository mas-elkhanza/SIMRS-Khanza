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
        $keyword = $_GET['keyword'];
        $keyword = validTeks($keyword);
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
                where pegawai.stts_aktif<>'KELUAR' and pegawai.nik like '%".$keyword."%' or pegawai.stts_aktif<>'KELUAR' and pegawai.nama like '%".$keyword."%' 
                or pegawai.stts_aktif<>'KELUAR' and pegawai.jbtn like '%".$keyword."%' or pegawai.stts_aktif<>'KELUAR' and pegawai.pendidikan like '%".$keyword."%' 
                or pegawai.stts_aktif<>'KELUAR' and jnj_jabatan.nama like '%".$keyword."%' or pegawai.stts_aktif<>'KELUAR' and departemen.nama like '%".$keyword."%' order by pegawai.id ASC ";
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

                         $_sql2         = "SELECT normal-$jumlahlibur,jmlhr,normal FROM set_tahun";
			 $hasil2        = bukaquery($_sql2);
			 $baris2        = mysqli_fetch_row($hasil2);
			 $jmlmsk         = $baris2[0];
			 if($baris["wajibmasuk"]==-1){
			     $jmlmsk=0;
			 }else if($baris["wajibmasuk"]==-2){
			     $jmlmsk=$baris2[1]-4;
			 }else if($baris["wajibmasuk"]==-3){
			     $jmlmsk=$baris2[1]-2-$jumlahlibur;
			 }else if($baris["wajibmasuk"]==-4){
			     $jmlmsk=$baris2[2];
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
			     $jmlmsk=$baris2[0];
			 }
                            $_sql3    = "SELECT sum(jml)
                            from jgmlm  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' group by id";
                            $hasil3   = bukaquery($_sql3);
                            $baris3   = mysqli_fetch_row($hasil3);
                            $jgmlm    = $baris3[0];
                            $sisamlm  =0;
                            if($baris3[0]<=0){
                                $jgmlm=0;
                                $sisamlm=0;
                            }else if($baris3[0]>0){
                                $jgmlm=$baris3[0];
                                $sisamlm=$baris3[0]-0;
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
                            $ttlc     = $baris6[0]+getOne("select sum(jumlah) from pengajuan_cuti where tanggal_awal like '%".$tahun."-".$bulan."%' and status='Disetujui' and nik='".$baris["nik"]."'");
                            if(empty ($ttlc)){
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

                            $ttln=getOne("select count(rekap_presensi.id) from rekap_presensi where rekap_presensi.id='$baris[0]' and rekap_presensi.jam_datang like '%$tahun-$bulan%'")+$ttltmb;
			//bukainput("insert into indekref values('$baris["indexins"]','$ttln','$total')");
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
		   // bukainput("insert into indextotal  values('$barisindex[0]','$ttlindex')");
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
            echo "<table width='5000px' border='0' align='center' cellpadding='0' cellspacing='0' valign='top' class='tbl_form'>
                    <caption><h3><font color='999999'>Laporan Penggajian Karyawan Tahun ".$tahun." Bulan ".$bulan."</font></h3></caption>
                    <tr class='head'  >
                         <td width='80px'><div align='center'>NIP</div></td>
                         <td width='180px'><div align='center'>Nama</div></td>
                         <td width='100px'><div align='center'>Jabatan</div></td>
                         <td width='60px'><div align='center'>Kode Jenjang</div></td>
                         <td width='60px'><div align='center'>Depart</div></td>
                         <td width='60px'><div align='center'>Kode Index</div></td>
                         <td width='60px'><div align='center'>Index Kary</div></td>
                         <td width='200px'><div align='center'>Jumlah Hari</div></td>
                         <td width='70px'><div align='center'>Jml.Index Lembur</div></td>
                         <td width='910px'><div align='center'>Gaji & Tunjangan diterima</div></td>
                         <td width='310px'><div align='center'>Lembur Diterima</div></td>
                         <td width='760px'><div align='center'>Tambahan Gaji Diterima</div></td>
                         <td width='100px'><div align='center'><font size='2' face='Tahoma' color='green'>Total Gaji</div></td>
                         <td width='920px'><div align='center'>Potongan Gaji</div></td>
                         <td width='100px'><div align='center'><font size='2' face='Tahoma' color='green'>Total Gaji Diterima</div></td>
                    </tr>";
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
			 $jmlmsk         = $baris2[0];
			 if($baris["wajibmasuk"]==-1){
			     $jmlmsk=0;
			 }else if($baris["wajibmasuk"]==-2){
			     $jmlmsk=$baris2[1]-4;
			 }else if($baris["wajibmasuk"]==-3){
			     $jmlmsk=$baris2[1]-2-$jumlahlibur;
			 }else if($baris["wajibmasuk"]==-4){
			     $jmlmsk=$baris2[2];
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
			     $jmlmsk=$baris2[0];
			 }

                            $_sql3    = "SELECT sum(jml)
                            from jgmlm  where id='$baris[0]'
                            and tgl like '%".$tahun."-".$bulan."%' group by id";
                            $hasil3   = bukaquery($_sql3);
                            $baris3   = mysqli_fetch_row($hasil3);
                            $jgmlm    = $baris3[0];
                            $sisamlm  =0;
                            if($baris3[0]<=0){
                                $jgmlm=0;
                                $sisamlm=0;
                            }else if($baris3[0]>0){
                                $jgmlm=$baris3[0];
                                $sisamlm=$baris3[0]-0;
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
                            $ttlc     = $baris6[0]+getOne("select sum(jumlah) from pengajuan_cuti where tanggal_awal like '%".$tahun."-".$bulan."%' and status='Disetujui' and nik='".$baris["nik"]."'");
                            if(empty ($ttlc)){
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


                            $ttln=getOne("select count(rekap_presensi.id) from rekap_presensi where rekap_presensi.id='$baris[0]' and rekap_presensi.jam_datang like '%$tahun-$bulan%'")+$ttltmb;
                            $tmbh=$ttltmb;

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

			    $_sql18  ="SELECT tnj from set_jgtambah where pendidikan='".$baris["pendidikan"]."' ";
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
                            if($baris["indexins"]!="DIR"){
                                $_sql22  ="SELECT ($ttln/sum(n))*100 from indekref where kdindex='".$baris["indexins"]."'";
                            }else if($baris["indexins"]=="DIR"){
                                $_sql22  ="SELECT ($ttln/sum(n))*100 from indekref where kdindex='MNJ'";
                            }
                            
			    $hasil22 =bukaquery($_sql22);
			    $baris22 = mysqli_fetch_array($hasil22);
			    $indexjaga=round($baris22[0],2);

                            /*if($baris[0]=="1"){
                                $indexjaga=0;
                            }*/
                            $ttlindexjaga=$ttlindexjaga+$indexjaga;

			    $_sqlpassum  ="select sum(jumpasien.jml) from jumpasien  
                                 where thn='".$tahun."' and bln='".$bulanindex."'";
                            $hasilpassum =bukaquery($_sqlpassum);
                            $barispassum = mysqli_fetch_array($hasilpassum);
                            $indexpassum=$barispassum[0];
                            $indexpas=0;
                            
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

			    $jl   = $baris26[0]+$baris27[0]+$baris28[0]+$baris29[0]+$baris31[0];
			    if(empty($baris26[0])&&empty($baris27[0])&&empty($baris28[0])&&empty($baris29[0])&&empty($baris31[0])){
                                $jl=0;
                            }else {
				$jl   = $baris26[0]+$baris27[0]+$baris28[0]+$baris29[0]+$baris31[0];
			    }
                            $ttljasalain=$ttljasalain+$jl;


                        echo "<tr class='isi' valign='top' >
                                 <td>".$baris["nik"]."&nbsp;</td>
                                 <td>".$baris["nama"]."&nbsp;</td>
                                 <td>".$baris["jbtn"]."&nbsp;</td>
                                 <td>".$baris["jnj_jabatan"]."&nbsp;</td>
                                 <td>".$baris["departemen"]."&nbsp;</td>
                                 <td>".$baris["indexins"]."&nbsp;</td>
                                 <td>$total&nbsp;</td>
                                 <td>
                                     <table width='100%' cellpadding='0' cellspacing='0' valign='top'>
                                       <TR class='isi' valign='top' >
                                         <TD width='20px'>WJB</TD>
                                         <TD width='20px'>N</TD>
                                         <TD width='20px'>+/-</TD>
                                         <TD width='20px'>MLM</TD>
                                         <TD width='20px'>+/-</TD>
                                         <TD width='20px'>KS</TD>
                                         <TD width='20px'>A</TD>
                                         <TD width='20px'>S</TD>
                                         <TD width='20px'>C</TD>
                                         <TD width='20px'>I</TD>
                                       </TR>
                                       <TR class='isi' valign='top' >
                                         <TD>$jmlmsk&nbsp;</TD>
                                         <TD>$ttln&nbsp;</TD>
                                         <TD>$tmbh&nbsp;</TD>
                                         <TD>$jgmlm&nbsp;</TD>
                                         <TD>$sisamlm&nbsp;</TD>
                                         <TD>$ks&nbsp;</TD>
                                         <TD>$ttla&nbsp;</TD>
                                         <TD>$ttls&nbsp;</TD>
                                         <TD>$ttlc&nbsp;</TD>
                                         <TD>$ttli&nbsp;</TD>
                                       </TR>
                                     </table>
				 </td>
                                 <td>
                                     <table width='100%' cellpadding='0' cellspacing='0' valign='top'>
                                       <TR class='isi' valign='top' >
                                         <TD width='20px'>HB</TD>
                                         <TD width='20px'>HR</TD>
                                       </TR>
				       <TR class='isi' valign='top' >
                                         <TD>$hb&nbsp;</TD>
                                         <TD>$hr&nbsp;</TD>
                                       </TR>
                                     </table>
				  </td>
				  <td>
                                     <table width='100%' cellpadding='0' cellspacing='0' valign='top'>
                                       <TR class='isi' valign='top' >
                                         <TD width='100px'>Gaji Pokok</TD>
					 <TD width='100px'>Tnj.Jabatan</TD>
                                         <TD width='100px'>Tunjangan</TD>
                                         <TD width='100px'>Tmbh.Jg.Mlm</TD>
					 <TD width='100px'>Tmbh.jaga</TD>
					 <TD width='100px'>Tnj.Kehadiran</TD>
					 <TD width='120px'><i>Jml.Gaji & Tunj</i></TD>
                                       </TR>
				       <TR class='isi' valign='top' >
                                         <TD>".formatDuit($gapok)."&nbsp;</TD>
					 <TD>".formatDuit($tnjjbtn)."&nbsp;</TD>
                                         <TD>
                                            <table width='100%' cellpadding='0' cellspacing='0' valign='top'>";
                                            echo "<tr class='isi3' valign='top'><td width='150px'>Tunjangan KaSift</td><td>: ".formatDuit($ttlkasift)."</td></tr>";
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
						$tunjanganpengurang=$barispengurang["tnj"];						
						$nilaitunjangan=0;
						$nilaitunjangan=($ttln*$baris16[1])-$tunjanganpengurang;
						if($nilaitunjangan<0){
                                                    $nilaitunjangan=0;
						}
												 
                                                $tnjtnj=$tnjtnj+$nilaitunjangan;
                                                echo "<tr class='isi3'><td width='150px'>$baris16[0]</td><td>: ".formatDuit($nilaitunjangan)."</td></tr>";
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
                                                echo "<tr class='isi3' valign='top' ><td width='150px'>$baris50[0]</td><td>: ".formatDuit($baris50[1])."</td></tr>";
                                            }

                                            $ttltnjtnj=$ttltnjtnj+$tnjtnj+$tnjtnjbln+$ttlkasift;
                                            $ttljmlgaji=$ttljmlgaji+$gapok+$tnjjbtn+$tnjtnj+$tnjtnjbln+$ttlkasift+$tmbhjgmlm+$tmbahanjg+$tnjhadir;
                                            echo"
                                            </table>
                                         </TD>
                                         <TD>".formatDuit($tmbhjgmlm)."&nbsp;</TD>
					 <TD>".formatDuit($tmbahanjg)."&nbsp;</TD>
					 <TD>".formatDuit($tnjhadir)."&nbsp;</TD>
					 <TD><i>".formatDuit($gapok+$tnjjbtn+$tnjtnj+$tnjtnjbln+$ttlkasift+$tmbhjgmlm+$tmbahanjg+$tnjhadir)."</i>&nbsp;</TD>
                                       </TR>
                                     </table>
				 </td>
                                 <td>
                                     <table width='100%' cellpadding='0' cellspacing='0' valign='top'>
                                       <TR class='isi' valign='top' >
                                         <TD width='100px'>Hari/Libur Biasa</TD>
                                         <TD width='100px'>Hari Raya</TD>
                                         <TD width='100px'><i>Jumlah Lembur</i></TD>
                                       </TR>
				       <TR class='isi' valign='top' >
                                         <TD>".formatDuit($lemburhb)."&nbsp;</TD>
                                         <TD>".formatDuit($lemburhr)."&nbsp;</TD>
                                         <TD><i>".formatDuit($lemburhb+$lemburhr)."</i>&nbsp;</TD>
                                       </TR>
                                     </table>
				  </td>
				  <td>
                                     <table width='100%' cellpadding='0' cellspacing='0' valign='top'>
                                       <TR class='isi' valign='top' >
                                         <TD width='65px'>Index Kary</TD>
					 <TD width='65px'>Index Jaga</TD>
					 <TD width='65px'>Total Index</TD>
                                         <TD width='100px'>Total Insentif</TD>
                                         <TD width='100px'>Tindakan Medis :</TD>
                                         <TD width='100px'>Jasa Lain</TD>
                                         <TD width='100px'><i>Jml.Tambahan</i></TD>
                                       </TR>
				       <TR class='isi' valign='top' >
                                         <TD>$total&nbsp;</TD>
					 <TD>$indexjaga&nbsp;</TD>
					 <TD>$ttlindex&nbsp;</TD>
                                         <TD width='100px'>";
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
                                            echo formatDuit($ttlinsentif)."
                                         &nbsp;</TD>
                                         <TD>
                                          <table width='100%' cellpadding='0' cellspacing='0' valign='top'>";
                                            $_sql30="select master_tindakan.nama,sum(tindakan.jm)
                                              from master_tindakan,tindakan
                                              where tindakan.tnd=master_tindakan.id and
                                              tindakan.id='$baris[0]'
                                              and tgl like '%".$tahun."-".$bulan."%'
                                              group by tindakan.tnd ";
				         $hasil30=bukaquery($_sql30);
				         while($baris30 = mysqli_fetch_array($hasil30)) {
					     echo "<tr class='isi3' valign='top' ><td width='150px'>$baris30[0]&nbsp;</td><td>: ".formatDuit($baris30[1])."&nbsp;</td></tr>";
				         }
                                   echo " </table>
                                         </TD>
                                         <TD>".formatDuit($jl)."</TD>
                                         <TD><i>".formatDuit($jmltmb)."</i>&nbsp;</TD>
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
                                                potongan.lain,
                                                potongan.bpjs from potongan
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
                                        $bpjs        = $baris25[9];
                                        
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
                                        
                                        if(empty ($baris25[9])){
                                            $bpjs=0;
                                        }else {
                                            $bpjs=$baris25[9];
                                        }
                                        $ttlbpjs=$ttlbpjs+$bpjs;

                                        $ttlditerima=$ttlgaji-($bpjs+$jamsostek+$dansos+$simwajib+$angkop+$angla+$telpri+$pajak+$pribadi+$lain);
                                        $ttlttlditerima=$ttlttlditerima+$ttlditerima;
			         echo "
                                     <table width='100%' cellpadding='0' cellspacing='0' valign='top'>
                                       <TR class='isi' valign='top' >
                                         <TD width='100px'>BPJS</TD>
                                         <TD width='100px'>Jamsostek</TD>
                                         <TD width='100px'>Dana Sosial</TD>
                                         <TD width='100px'>Simp.Wajib</TD>
                                         <TD width='100px'>Ang.Koperasi</TD>
                                         <TD width='100px'>Ang.Lain</TD>
                                         <TD width='100px'>Telp.Pribadi</TD>
                                         <TD width='100px'>Pajak</TD>
                                         <TD width='100px'>Pribadi</TD>
                                         <TD width='100px'>Potongan Lain</TD>
                                       </TR>
				       <TR class='isi' valign='top' >
                                         <TD>".formatDuit($bpjs)."</TD>
                                         <TD>".formatDuit($jamsostek)."&nbsp;</TD>
                                         <TD>".formatDuit($dansos)."&nbsp;</TD>
                                         <TD>".formatDuit($simwajib)."&nbsp;</TD>
                                         <TD>".formatDuit($angkop)."&nbsp;</TD>
                                         <TD>".formatDuit($angla)."&nbsp;</TD>
                                         <TD>".formatDuit($telpri)."&nbsp;</TD>
                                         <TD>".formatDuit($pajak)."&nbsp;</TD>
                                         <TD>".formatDuit($pribadi)."&nbsp;</TD>
                                         <TD>".formatDuit($lain)."&nbsp;</TD>
                                     </table>
				  </td>
                                  <TD><font size='2' color='green'><i>".formatDuit($ttlditerima)."</i></font></TD>
                               </tr>";
                    }
            $ttlttlindex=$ttlindexjaga+$ttltotal;
            echo "<tr class='head' valign='top' >
                         <td COLSPAN='9'><div align='right'>Total :&nbsp; </div></td>
                         <td>
                                     <table width='100%' cellpadding='0' cellspacing='0' valign='top'>
				       <TR class='isi5'valign='top' >
                                         <TD width='100px'>".formatDuit($ttlgapok)."&nbsp;</TD>
					 <TD width='100px'>".formatDuit($ttltnjjbtn)."&nbsp;</TD>
                                         <TD width='300px'>".formatDuit($ttltnjtnj)."&nbsp;</TD>
                                         <TD width='100px'>".formatDuit($ttltmbhjgmlm)."&nbsp;</TD>
					 <TD width='100px'>".formatDuit($ttltmbahanjg)."&nbsp;</TD>
					 <TD width='100px'>".formatDuit($ttltnjhadir)."&nbsp;</TD>
					 <TD width='120px'><i>".formatDuit($ttljmlgaji)."</i>&nbsp;</TD>
                                       </TR>
                                     </table>
			 </td>
                         <td>
                                     <table width='100%' cellpadding='0' cellspacing='0' valign='top'>
				       <TR class='isi5' valign='top' >
                                         <TD width='100px'>".formatDuit($ttllemburhb)."&nbsp;</TD>
                                         <TD width='100px'>".formatDuit($ttllemburhr)."&nbsp;</TD>
                                         <TD width='100px'><i>".formatDuit($ttllemburhb+$ttllemburhr)."</i>&nbsp;</TD>
                                       </TR>
                                     </table>
			 </td>
                         <td>
                                     <table width='100%' cellpadding='0' cellspacing='0' valign='top'>
				       <TR class='isi5' valign='top' >
                                         <TD width='65px'>$ttltotal&nbsp;</TD>
					 <TD width='65px'>$ttlindexjaga&nbsp;</TD>
					 <TD width='65px'>$ttlttlindex&nbsp;</TD>
                                         <TD width='105px'>".formatDuit($ttlttlinsentif)."&nbsp;</TD>
                                         <TD width='250px'>".formatDuit($ttljm)."&nbsp;</TD>
                                         <TD width='105px'>".formatDuit($ttljasalain)."</TD>
                                         <TD width='105px'><i>".formatDuit($ttljmltmb)."</i>&nbsp;</TD>
                                       </TR>
                                     </table>
			 </td>
                         <td width='100px'><div align='center'><font size='2' color='green'><i>".formatDuit($ttlttlgaji)."</i></font></div></td>
                         <td>
                                     <table width='100%' cellpadding='0' cellspacing='0' valign='top'>
				       <TR class='isi5' valign='top' >
                                         <TD width='100px'>".formatDuit($ttlbpjs)."</TD>
                                         <TD width='100px'>".formatDuit($ttljamsostek)."&nbsp;</TD>
                                         <TD width='100px'>".formatDuit($ttldansos)."&nbsp;</TD>
                                         <TD width='100px'>".formatDuit($ttlsimwajib)."&nbsp;</TD>
                                         <TD width='100px'>".formatDuit($ttlangkop)."&nbsp;</TD>
                                         <TD width='100px'>".formatDuit($ttlangla)."&nbsp;</TD>
                                         <TD width='100px'>".formatDuit($ttltelpri)."&nbsp;</TD>
                                         <TD width='100px'>".formatDuit($ttlpajak)."&nbsp;</TD>
                                         <TD width='100px'>".formatDuit($ttlpribadi)."&nbsp;</TD>
                                         <TD width='100px'>".formatDuit($ttllain)."&nbsp;</TD>
                                     </table>
			 </td>
                         <TD><font size='2' color='green'><i>".formatDuit($ttlttlditerima)."</i></font>&nbsp;</TD>
                    </tr>
                  </table>";
        } 
    ?>
    </body>
</html>
