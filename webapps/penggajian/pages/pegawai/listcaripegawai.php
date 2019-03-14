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
            if (empty($awal)) $awal=0;
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
                     stts_aktif like '%".$keyword."%' or
                     no_ktp like '%".$keyword."%'
                     order by id ASC ";
            $hasil=bukaquery($_sql);
            $jumlah=mysqli_num_rows($hasil);

            if(mysqli_num_rows($hasil)!=0) {            
                echo "<table width='3120px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head'>
                                     <td width='100px'><div align='center'>Proses</div></td>
                                     <td width='80px'><div align='center'>NIP</div></td>
                                     <td width='200px'><div align='center'>Nama</div></td>
                                     <td width='50px'><div align='center'>J.K.</div></td>
                                     <td width='150px'><div align='center'>Jabatan</div></td>
                                     <td width='80px'><div align='center'>Jenjang</div></td>
                                     <td width='80px'><div align='center'>Departemen</div></td>
                                     <td width='80px'><div align='center'>Bagian</div></td>
                                     <td width='80px'><div align='center'>Status</div></td>
                                     <td width='80px'><div align='center'>Status Karyawan</div></td>
                                     <td width='100px'><div align='center'>NPWP</div></td>
                                     <td width='200px'><div align='center'>Pendidikan</div></td>
                                     <td width='100px'><div align='center'>Tmp.Lahir</div></td>
                                     <td width='80px'><div align='center'>Tgl.Lahir</div></td>
                                     <td width='250px'><div align='center'>Alamat</div></td>
                                     <td width='100px'><div align='center'>Kota </div></td>
                                     <td width='80px'><div align='center'>Mulai Kerja</div></td>
                                     <td width='80px'><div align='center'>Kode Ms Kerja</div></td>
                                     <td width='80px'><div align='center'>Kode Index</div></td>
                                     <td width='40px'><div align='center'>BPD</div></td>
                                     <td width='70px'><div align='center'>Rekening</div></td>
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
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[1]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[2]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[3]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[4]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[5]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[6]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[7]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[8]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[9]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[10]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[11]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[13]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[14]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[15]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[16]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[17]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[18]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[19]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[20]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[21]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[22]</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$jmlmsk</a></td>
                                     <td valign='top'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[24]</a></td>
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
            if(mysqli_num_rows($hasil)!=0) {
				$hasil1=bukaquery("select id,nik,nama,jk,jbtn,jnj_jabatan,departemen,bidang,stts_wp,stts_kerja,
                 npwp, pendidikan, gapok,tmp_lahir,tgl_lahir,alamat,kota,mulai_kerja,ms_kerja,
                 indexins,bpd,rekening,stts_aktif,wajibmasuk,mulai_kontrak from pegawai
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
                 order by id ASC");
                $jumladiv=mysqli_num_rows($hasil1);
                $i=$jumladiv/19;
                $i=ceil($i);
                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah<a target=_blank href=../penggajian/pages/pegawai/LaporanPegawai.php?&keyword=$keyword>| Laporan |</a></div></td>                        
                    </tr>     
                 </table>");
             }
       ?>
    </div>
</div>
