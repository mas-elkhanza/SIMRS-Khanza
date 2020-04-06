<?php
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
    $action      =isset($_GET['action'])?$_GET['action']:NULL;
    $_sqllibur = "select `tanggal`, `ktg` from set_hari_libur where tanggal like '%".$tahun."-".$bulan."%' ORDER BY tanggal";
    $hasillibur=bukaquery($_sqllibur);
    $jumlahlibur=mysqli_num_rows($hasillibur);
?>

<div id="post">
    <div class="entry">
    <div align="center" class="link">
        <a href=?act=InputPegawai&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListCariPegawai>| List Data |</a>
        <a href=?act=ListIndexPegawai>| Index Pegawai |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $keyword=isset($_GET['keyword'])?$_GET['keyword']:NULL;
                $keyword= validTeks($keyword);
                echo "<input type=hidden name=keyword value=$keyword><input type=hidden name=action value=$action>";
        ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="25%" >Keyword</td><td width="">:</td>
                    <td width="74%">
                        <input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type="text" id="TxtIsi1" value="<?php echo $keyword;?>" size="65" maxlength="250" />                   
                        <input name="BtnCari" type="submit" class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;">                        
                    </td>
                </tr>
            </table><br>
        <div style="width: 100%; height: 78%; overflow: auto;">
        <?php
            $awal=isset($_GET['awal'])?$_GET['awal']:NULL;
            $keyword=trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;
            $keyword= validTeks($keyword);
            if (empty($awal)) $awal=0;
            $_sql = "select pegawai.id,pegawai.nik,pegawai.nama,pegawai.jk,pegawai.jbtn,jnj_jabatan.nama as jnj_jabatan,kelompok_jabatan.nama_kelompok,
                    resiko_kerja.nama_resiko,departemen.nama as departemen,pegawai.bidang,emergency_index.nama_emergency,stts_wp.ktg as stts_wp,
                    stts_kerja.ktg as stts_kerja,pegawai.npwp,pegawai.pendidikan,pegawai.gapok,pegawai.tmp_lahir,pegawai.tgl_lahir,
                    pegawai.alamat,pegawai.kota,pegawai.mulai_kerja,pegawai.ms_kerja,pegawai.indexins,pegawai.bpd,pegawai.rekening,pegawai.stts_aktif,
                    pegawai.wajibmasuk,pegawai.mulai_kontrak,pegawai.photo,pegawai.no_ktp from pegawai inner join jnj_jabatan inner join departemen 
                    inner join stts_wp inner join stts_kerja inner join kelompok_jabatan inner join resiko_kerja inner join emergency_index on 
                    pegawai.jnj_jabatan=jnj_jabatan.kode and pegawai.departemen=departemen.dep_id and pegawai.stts_wp=stts_wp.stts and pegawai.stts_kerja=stts_kerja.stts
                    and kelompok_jabatan.kode_kelompok=pegawai.kode_kelompok and resiko_kerja.kode_resiko=pegawai.kode_resiko and 
                    emergency_index.kode_emergency=pegawai.kode_emergency
                    where pegawai.nik like '%".$keyword."%' or
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
                     pegawai.no_ktp like '%".$keyword."%'
                     order by pegawai.id ASC ";
            $hasil=bukaquery($_sql);
            $jumlah=mysqli_num_rows($hasil);

            if(mysqli_num_rows($hasil)!=0) {            
                echo "<table width='2820px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head'>
                             <td width='100px'><div align='center'>Proses</div></td>
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
                             $jmlmsk         = $baris2[0];
                             if($baris[23]==-1){
                                 $jmlmsk=0;
                             }else if($baris[23]==-2){
                                 $jmlmsk=$baris2[1]-4;
                             }else if($baris[23]==-3){
                                 $jmlmsk=$baris2[1]-2-$jumlahlibur;
                             }else if($baris[23]==-4){
                                 $jmlmsk=$baris2[2];
                             }else if($baris[23]==-5){
                                 $jmlmsk=getOne("select (if(h1='',0,1)+if(h2='',0,1)+if(h3='',0,1)+if(h4='',0,1)+if(h5='',0,1)+"
                                               ."if(h6='',0,1)+if(h7='',0,1)+if(h8='',0,1)+if(h9='',0,1)+if(h10='',0,1)+"
                                               ."if(h11='',0,1)+if(h12='',0,1)+if(h13='',0,1)+if(h14='',0,1)+if(h15='',0,1)+"
                                               ."if(h16='',0,1)+if(h17='',0,1)+if(h18='',0,1)+if(h19='',0,1)+if(h20='',0,1)+"
                                               ."if(h21='',0,1)+if(h22='',0,1)+if(h23='',0,1)+if(h24='',0,1)+if(h25='',0,1)+"
                                               ."if(h26='',0,1)+if(h27='',0,1)+if(h28='',0,1)+if(h29='',0,1)+if(h30='',0,1)+"
                                               ."if(h31='',0,1)) from jadwal_pegawai where id='$baris[0]' and tahun='$tahun' and bulan='$bulan'");
                             }else if($baris[23]!=0){
                                 $jmlmsk=$baris[23];
                             }else if(!($baris[23]==0)){
                                 $jmlmsk=$baris2[0];
                             }
                             $gb="-";
                             if($baris["photo"]=="pages/pegawai/photo/"){
                                $gb="-";                            
                             }else{
                                $gb="<img src='".$baris["photo"]."' width='120px' height='120px'>";
                             }
                            echo "<tr class='isi' title='$baris[1] $baris[2]'>
                                    <td valign='top'>
                                            <center>
                                            <a href='?act=InputPegawai&action=UBAH&id=$baris[0]'>[edit]</a>"; ?>
                                            <a href="?act=ListCariPegawai&action=HAPUS&id=<?php print $baris[0] ?>" >[hapus]</a>
                                <?php
                               echo "       </center>
                                    </td>								
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["nik"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["nama"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["jk"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["jbtn"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["jnj_jabatan"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["nama_kelompok"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["departemen"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["bidang"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["nama_resiko"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["nama_emergency"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["stts_wp"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["stts_kerja"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["npwp"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["pendidikan"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["tmp_lahir"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["tgl_lahir"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["alamat"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["kota"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["mulai_kerja"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["ms_kerja"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["indexins"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["bpd"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["rekening"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["stts_aktif"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$jmlmsk</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["mulai_kontrak"]."</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$gb</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["no_ktp"]."</a></td>
                                   </tr>";
                        }
                echo "</table>";
            } else {
                echo "Data Pegawai masih kosong !";                
            }
            $aksi=isset($_GET['action'])?$_GET['action']:NULL;
            if ($aksi=="HAPUS") {
                Hapus(" pegawai "," id ='".$_GET['id']."' ","?act=ListCariPegawai");
            }
            $BtnPrint=isset($_POST['BtnPrint'])?$_POST['BtnPrint']:NULL;
            if (isset($BtnPrint)) {
               echo"<html><head><title></title><meta http-equiv='refresh' content='2;pages/pegawai/LaporanPegawai.php?&keyword=$keyword'></head><body></body></html>";
            }
        ?>
        </div>
    </form>     
       <?php
            if($jumlah>0) {
                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah | <a target=_blank href=../penggajian/pages/pegawai/LaporanPegawai.php?&keyword=$keyword> Laporan </a> | <a target=_blank href=../penggajian/pages/pegawai/LaporanPegawaiExcel.php?&keyword=$keyword>Excel</a> |</div></td>                        
                    </tr>     
                 </table>");
             }
       ?>
    </div>
</div>
