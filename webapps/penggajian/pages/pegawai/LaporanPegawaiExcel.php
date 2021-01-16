<?php
    header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanPegawai.xls");
    header("Pragma: no-cache");
    header("Expires: 0");
    include '../../../conf/conf.php';
    $_sql         = "SELECT * FROM set_tahun";
    $hasil        = bukaquery($_sql);
    $baris        = mysqli_fetch_row($hasil);
    $tahun     = empty($baristhn[0])?date("Y"):$baristhn[0];
    $blnini    = empty($baristhn[1])?date("m"):$baristhn[1];
    $hari      = empty($baristhn[2])?date("d"):$baristhn[2];
    $bln_leng  = strlen($blnini);
    $bulan     = "0";
    if ($bln_leng==1){
        $bulan="0".$blnini;
    }else{
        $bulan=$blnini;
    }
 
    $bulanindex = empty($baristhn[1])?date("m"):$baristhn[1];

    $_sqllibur   = "select `tanggal`, `ktg` from set_hari_libur where tanggal like '%".$tahun."-".$bulan."%' ORDER BY tanggal";
    $hasillibur  = bukaquery($_sqllibur);
    $jumlahlibur = mysqli_num_rows($hasillibur);
?>
<html>
    <head>
        <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>

    <?php
        $keyword = isset($_GET['keyword'])?$_GET['keyword']:NULL;
        $keyword = validTeks($keyword);
        $status  = trim(isset($_GET['status']))?trim($_GET['status']):"AKTIF";
        $_sql = "select pegawai.id,pegawai.nik,pegawai.nama,pegawai.jk,pegawai.jbtn,jnj_jabatan.nama as jnj_jabatan,kelompok_jabatan.nama_kelompok,
                    resiko_kerja.nama_resiko,departemen.nama as departemen,pegawai.bidang,emergency_index.nama_emergency,stts_wp.ktg as stts_wp,
                    stts_kerja.ktg as stts_kerja,pegawai.npwp,pegawai.pendidikan,pegawai.gapok,pegawai.tmp_lahir,pegawai.tgl_lahir,
                    pegawai.alamat,pegawai.kota,pegawai.mulai_kerja,pegawai.ms_kerja,pegawai.indexins,pegawai.bpd,pegawai.rekening,pegawai.stts_aktif,
                    pegawai.wajibmasuk,pegawai.mulai_kontrak,pegawai.photo,pegawai.no_ktp from pegawai inner join jnj_jabatan inner join departemen
                    inner join stts_wp inner join stts_kerja inner join kelompok_jabatan inner join resiko_kerja inner join emergency_index on
                    pegawai.jnj_jabatan=jnj_jabatan.kode and pegawai.departemen=departemen.dep_id and pegawai.stts_wp=stts_wp.stts and pegawai.stts_kerja=stts_kerja.stts
                    and kelompok_jabatan.kode_kelompok=pegawai.kode_kelompok and resiko_kerja.kode_resiko=pegawai.kode_resiko and
                    emergency_index.kode_emergency=pegawai.kode_emergency
                    where stts_aktif='$status' and (pegawai.nik like '%".$keyword."%' or
                     pegawai.nama like '%".$keyword."%' or
                     pegawai.jk like '%".$keyword."%' or
                     pegawai.jbtn like '%".$keyword."%' or
                     jnj_jabatan.nama like '%".$keyword."%' or
                     departemen.nama like '%".$keyword."%' or
                     pegawai.bidang like '%".$keyword."%' or
                     stts_wp.ktg like '%".$keyword."%' or
                     stts_kerja.ktg like '%".$keyword."%' or
                     kelompok_jabatan.nama_kelompok like '%".$keyword."%' or
                     resiko_kerja.nama_resiko like '%".$keyword."%' or
                     emergency_index.nama_emergency like '%".$keyword."%' or
                     pegawai.npwp like '%".$keyword."%' or
                     pegawai.pendidikan like '%".$keyword."%' or
                     pegawai.gapok like '%".$keyword."%' or
                     pegawai.tmp_lahir like '%".$keyword."%' or
                     pegawai.tgl_lahir like '%".$keyword."%' or
                     pegawai.alamat like '%".$keyword."%' or
                     pegawai.kota like '%".$keyword."%' or
                     pegawai.mulai_kerja like '%".$keyword."%' or
                     pegawai.ms_kerja like '%".$keyword."%' or
                     pegawai.indexins like '%".$keyword."%' or
                     pegawai.bpd like '%".$keyword."%' or
                     pegawai.rekening like '%".$keyword."%' or
                     pegawai.stts_aktif like '%".$keyword."%' or
                     pegawai.no_ktp like '%".$keyword."%')
                     order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);

        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='3820px'  border='1' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h1 class=title>DAFTAR PEGAWAI/KARYAWAN</h1></caption>
                    <tr class='head'>
                         <td width='80px'><div align='center'>NIP</div></td>
                         <td width='200px'><div align='center'>Nama</div></td>
                         <td width='50px'><div align='center'>J.K.</div></td>
                         <td width='100px'><div align='center'>Jabatan</div></td>
                         <td width='100px'><div align='center'>Jenjang</div></td>
                         <td width='100px'><div align='center'>Kelompok Jabatan</div></td>
                         <td width='90px'><div align='center'>Departemen</div></td>
                         <td width='80px'><div align='center'>Bagian</div></td>
                         <td width='80px'><div align='center'>Resiko Kerja</div></td>
                         <td width='80px'><div align='center'>Tingkat Emergency</div></td>
                         <td width='120px'><div align='center'>Status WP</div></td>
                         <td width='100px'><div align='center'>Status Karyawan</div></td>
                         <td width='100px'><div align='center'>NPWP</div></td>
                         <td width='190px'><div align='center'>Pendidikan</div></td>
                         <td width='100px'><div align='center'>Tmp.Lahir</div></td>
                         <td width='70px'><div align='center'>Tgl.Lahir</div></td>
                         <td width='250px'><div align='center'>Alamat</div></td>
                         <td width='100px'><div align='center'>Kota </div></td>
                         <td width='80px'><div align='center'>Mulai Kerja</div></td>
                         <td width='80px'><div align='center'>Masa Kerja</div></td>
                         <td width='80px'><div align='center'>Kode Index</div></td>
                         <td width='40px'><div align='center'>Bank</div></td>
                         <td width='70px'><div align='center'>Rekening</div></td>
                         <td width='80px'><div align='center'>Stts Aktif</div></td>
                         <td width='40px'><div align='center'>Wajib Masuk</div></td>
                         <td width='70px'><div align='center'>Mulai Kontrak</div></td>
                         <td width='120px'><div align='center'>Photo</div></td>
                         <td width='100px'><div align='center'>No.KTP</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
			                   $_sql2         = "SELECT normal-$jumlahlibur,jmlhr,normal FROM set_tahun";
                    		 $hasil2        = bukaquery($_sql2);
                    		 $baris2        = mysqli_fetch_row($hasil2);
                    		 $jmlmsk        = empty($baris2[0])?date("d"):$baris2[0];
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
                                 <td valign='top'>".$baris["nik"]."</td>
                                 <td valign='top'>".$baris["nama"]."</td>
                                 <td valign='top'>".$baris["jk"]."</td>
                                 <td valign='top'>".$baris["jbtn"]."</td>
                                 <td valign='top'>".$baris["jnj_jabatan"]."</td>
                                 <td valign='top'>".$baris["nama_kelompok"]."</td>
                                 <td valign='top'>".$baris["departemen"]."</td>
                                 <td valign='top'>".$baris["bidang"]."</td>
                                 <td valign='top'>".$baris["nama_resiko"]."</td>
                                 <td valign='top'>".$baris["nama_emergency"]."</td>
                                 <td valign='top'>".$baris["stts_wp"]."</td>
                                 <td valign='top'>".$baris["stts_kerja"]."</td>
                                 <td valign='top'>".$baris["npwp"]."</td>
                                 <td valign='top'>".$baris["pendidikan"]."</td>
                                 <td valign='top'>".$baris["tmp_lahir"]."</td>
                                 <td valign='top'>".$baris["tgl_lahir"]."</td>
                                 <td valign='top'>".$baris["alamat"]."</td>
                                 <td valign='top'>".$baris["kota"]."</td>
                                 <td valign='top'>".$baris["mulai_kerja"]."</td>
                                 <td valign='top'>".$baris["ms_kerja"]."</td>
                                 <td valign='top'>".$baris["indexins"]."</td>
                                 <td valign='top'>".$baris["bpd"]."</td>
                                 <td valign='top'>".$baris["rekening"]."</td>
                                 <td valign='top'>".$baris["stts_aktif"]."</td>
                                 <td valign='top'>$jmlmsk</td>
                                 <td valign='top'>".$baris["mulai_kontrak"]."</td>
                                 <td valign='top'>$gb</td>
                                 <td valign='top'>".$baris["no_ktp"]."</td>
                               </tr>";
                    }
            echo "</table>";
            echo("<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>
                    </tr>
                 </table>");
        }else{
            echo "<table width='3820px'  border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                      <caption><h1 class=title>DAFTAR PEGAWAI/KARYAWAN</h1></caption>
                      <tr class='head'>
                           <td width='80px'><div align='center'>NIP</div></td>
                           <td width='200px'><div align='center'>Nama</div></td>
                           <td width='50px'><div align='center'>J.K.</div></td>
                           <td width='100px'><div align='center'>Jabatan</div></td>
                           <td width='100px'><div align='center'>Jenjang</div></td>
                           <td width='100px'><div align='center'>Kelompok Jabatan</div></td>
                           <td width='90px'><div align='center'>Departemen</div></td>
                           <td width='80px'><div align='center'>Bagian</div></td>
                           <td width='80px'><div align='center'>Resiko Kerja</div></td>
                           <td width='80px'><div align='center'>Tingkat Emergency</div></td>
                           <td width='120px'><div align='center'>Status WP</div></td>
                           <td width='100px'><div align='center'>Status Karyawan</div></td>
                           <td width='100px'><div align='center'>NPWP</div></td>
                           <td width='190px'><div align='center'>Pendidikan</div></td>
                           <td width='100px'><div align='center'>Tmp.Lahir</div></td>
                           <td width='70px'><div align='center'>Tgl.Lahir</div></td>
                           <td width='250px'><div align='center'>Alamat</div></td>
                           <td width='100px'><div align='center'>Kota </div></td>
                           <td width='80px'><div align='center'>Mulai Kerja</div></td>
                           <td width='80px'><div align='center'>Masa Kerja</div></td>
                           <td width='80px'><div align='center'>Kode Index</div></td>
                           <td width='40px'><div align='center'>Bank</div></td>
                           <td width='70px'><div align='center'>Rekening</div></td>
                           <td width='80px'><div align='center'>Stts Aktif</div></td>
                           <td width='40px'><div align='center'>Wajib Masuk</div></td>
                           <td width='70px'><div align='center'>Mulai Kontrak</div></td>
                           <td width='120px'><div align='center'>Photo</div></td>
                           <td width='100px'><div align='center'>No.KTP</div></td>
                      </tr>
                 </table>";
          }
    ?>

    </body>
</html>
