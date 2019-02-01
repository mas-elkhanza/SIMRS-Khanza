<?php
 include '../../../conf/conf.php';

   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun         = $baris[0];
   $bln_leng=strlen($baris[1]);
   $hari         =$baris[2];
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
        $keyword=isset($_GET['keyword'])?$_GET['keyword']:NULL;   
        $_sql = "select id,nik,nama,jk,jbtn,jnj_jabatan,departemen,bidang,stts_wp,stts_kerja,
                npwp, pendidikan, gapok,tmp_lahir,tgl_lahir,alamat,kota,mulai_kerja,ms_kerja,
                indexins,bpd,rekening,stts_aktif,wajibmasuk,mulai_kontrak,photo,no_ktp from pegawai
                 where
                 nik like '%".$keyword."%' or
                 nama like '%".$keyword."%' or
                 jk like '%".$keyword."%' or
                 jbtn like '%".$keyword."%' or
                 jnj_jabatan like '%".$keyword."%' or
                 departemen like '%".$keyword."%' or
                 bidang like '%".$keyword."%' or
                 stts_wp like '%".$keyword."%' or
                 stts_kerja like '%".$keyword."%' or
                 npwp like '%".$keyword."%' or
                 pendidikan like '%".$keyword."%' or
                 gapok like '%".$keyword."%' or
                 tmp_lahir like '%".$keyword."%' or
                 tgl_lahir like '%".$keyword."%' or
                 alamat like '%".$keyword."%' or
                 kota like '%".$keyword."%' or
                 mulai_kerja like '%".$keyword."%' or
                 ms_kerja like '%".$keyword."%' or
                 indexins like '%".$keyword."%' or
                 bpd like '%".$keyword."%' or
                 rekening like '%".$keyword."%' or
                 stts_aktif like '%".$keyword."%' 
                 order by id ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);

        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='3120px'  border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
				<caption><h1 class=title>DAFTAR PEGAWAI/KARYAWAN</h1><br></caption>
                    <tr class='head'>
                                 <td width='80px'><div align='center'>NIP</div></td>
                                 <td width='200px'><div align='center'>Nama</div></td>
                                 <td width='50px'><div align='center'>J.K.</div></td>
                                 <td width='100px'><div align='center'>Jbtn</div></td>
                                 <td width='80px'><div align='center'>Kd Jnjng</div></td>
                                 <td width='80px'><div align='center'>Dprt</div></td>
                                 <td width='80px'><div align='center'>Bagian</div></td>
                                 <td width='80px'><div align='center'>Stts</div></td>
                                 <td width='80px'><div align='center'>Stts Krywn</div></td>
                                 <td width='100px'><div align='center'>NPWP</div></td>
                                 <td width='80px'><div align='center'>Pnd</div></td>
                                 <td width='100px'><div align='center'>Tmp.Lhr</div></td>
                                 <td width='80px'><div align='center'>Tgl.Lhr</div></td>
                                 <td width='250px'><div align='center'>Alamat</div></td>
                                 <td width='100px'><div align='center'>Kota </div></td>
                                 <td width='80px'><div align='center'>Mulai Krj</div></td>
                                 <td width='80px'><div align='center'>Kd Ms Krj</div></td>
                                 <td width='80px'><div align='center'>Kd Index</div></td>
                                 <td width='80px'><div align='center'>BPD</div></td>
                                 <td width='100px'><div align='center'>Rekening</div></td>
                                 <td width='80px'><div align='center'>Stts Aktif</div></td>
                                 <td width='70px'><div align='center'>Wajib Masuk</div></td>
                                 <td width='100px'><div align='center'>Mulai Kontrak</div></td>
                                 <td width='120px'><div align='center'>Photo</div></td>
                                 <td width='100px'><div align='center'>No.KTP</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
			$_sql2         = "SELECT normal-$jumlahlibur,jmlhr,normal FROM set_tahun";
			 $hasil2        = bukaquery($_sql2);
			 $baris2        = mysqli_fetch_row($hasil2);
			 $jmlmsk         = $baris2[0];
			 if($baris[23]==-1){
			     $jmlmsk=0;
			 }else if($baris[23]==-2){
			     $jmlmsk=$baris2[1]-4;
			 }else if($baris[23]==-3){
			     $jmlmsk=$baris2[1]-2-$jumlahlibur;
			 }else if($baris[23]==-4){
			     $jmlmsk=$baris2[2];
			 }else if($baris[23]!=0){
			     $jmlmsk=$baris[23];
			 }else if(!($baris[23]==0)){
			     $jmlmsk=$baris2[0];
			 }
                         $gb="-";
                         if($baris["photo"]=="pages/pegawai/photo/"){
                            $gb="-";                            
                         }else{
                            $gb="<img src='".str_replace("pages/pegawai/photo/","photo/",$baris["photo"])."' width='120px' height='120px'>";
                         }
                        echo "<tr class='isi'>
                                 <td>$baris[1]&nbsp;</td>
                                 <td>$baris[2]&nbsp;</td>
                                 <td>$baris[3]&nbsp;</td>
                                 <td>$baris[4]&nbsp;</td>
                                 <td>$baris[5]&nbsp;</td>
                                 <td>$baris[6]&nbsp;</td>
                                 <td>$baris[7]&nbsp;</td>
                                 <td>$baris[8]&nbsp;</td>
                                 <td>$baris[9]&nbsp;</td>
                                 <td>$baris[10]&nbsp;</td>
                                 <td>$baris[11]&nbsp;</td>
                                  <td>$baris[13]&nbsp;</td>
                                  <td>$baris[14]&nbsp;</td>
                                  <td>$baris[15]&nbsp;</td>
				 <td>$baris[16]&nbsp;</td>
                                  <td>$baris[17]&nbsp;</td>
                                  <td>$baris[18]&nbsp;</td>
                                  <td>$baris[19]&nbsp;</td>
                                  <td>$baris[20]&nbsp;</td>
                                  <td>$baris[21]&nbsp;</td>
                                  <td>$baris[22]&nbsp;</td>
                                  <td>$jmlmsk&nbsp;</td>
                                  <td>$baris[24]</td>
                                  <td>$gb</a></td>
                                  <td>".$baris["no_ktp"]."</a></td>
                               </tr>";
                    }
            echo "</table>";
            echo("<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        } 
    ?>

    </body>
</html>