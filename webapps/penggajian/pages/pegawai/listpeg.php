<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun         = $baris[0];
   $bln_leng=strlen($baris[1]);
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
    <div class="entry">
	<br/>
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
                echo "";
                $action      =$_GET['action'];
                $keyword     =$_GET['keyword'];
                echo "<input type=hidden name=keyword value=$keyword><input type=hidden name=action value=$action>";
        ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="25%" >Keyword</td><td width="">:</td>
                    <td width="82%"><input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="65" maxlength="250" />
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;"></div><br>
        <div style="width: 598px; height: 400px; overflow: auto;">
    <?php
        $awal=$_GET['awal'];
        $keyword=trim($_POST['keyword']);

        if (empty($awal)) $awal=0;
        $_sql = "select id,nik,nama,jk,jbtn,jnj_jabatan,departemen,bidang,stts_wp,stts_kerja,
                npwp, pendidikan, gapok,tmp_lahir,tgl_lahir,alamat,kota,mulai_kerja,ms_kerja,
                indexins,bpd,rekening,stts_aktif,wajibmasuk,mulai_kontrak,photo from pegawai
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
                 order by nik ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);

        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='2600px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                                 <td width='80px'><div align='center'><font size='2' face='Tahoma'><strong>NIP</strong></font></div></td>
                                 <td width='200px'><div align='center'><font size='2' face='Tahoma'><strong>Nama</strong></font></div></td>
                                 <td width='50px'><div align='center'><font size='2' face='Tahoma'><strong>J.K.</strong></font></div></td>
                                 <td width='100px'><div align='center'><font size='2' face='Tahoma'><strong>Jabatan</strong></font></div></td>
                                 <td width='80px'><div align='center'><font size='2' face='Tahoma'><strong>Kode Jenjang</strong></font></div></td>
                                 <td width='80px'><div align='center'><font size='2' face='Tahoma'><strong>Departemen</strong></font></div></td>
                                 <td width='80px'><div align='center'><font size='2' face='Tahoma'><strong>Bagian</strong></font></div></td>
                                 <td width='80px'><div align='center'><font size='2' face='Tahoma'><strong>Status</strong></font></div></td>
                                 <td width='80px'><div align='center'><font size='2' face='Tahoma'><strong>Status Karyawan</strong></font></div></td>
                                 <td width='100px'><div align='center'><font size='2' face='Tahoma'><strong>NPWP</strong></font></div></td>
                                 <td width='80px'><div align='center'><font size='2' face='Tahoma'><strong>Pendidikan</strong></font></div></td>
                                 <td width='100px'><div align='center'><font size='2' face='Tahoma'><strong>Tmp.Lahir</strong></font></div></td>
                                 <td width='80px'><div align='center'><font size='2' face='Tahoma'><strong>Tgl.Lahir</strong></font></div></td>
                                 <td width='250px'><div align='center'><font size='2' face='Tahoma'><strong>Alamat</strong></font></div></td>
                                 <td width='100px'><div align='center'><font size='2' face='Tahoma'><strong>Kota </strong></font></div></td>
                                 <td width='80px'><div align='center'><font size='2' face='Tahoma'><strong>Mulai Kerja</strong></font></div></td>
                                 <td width='80px'><div align='center'><font size='2' face='Tahoma'><strong>Kode Ms Kerja</strong></font></div></td>
                                 <td width='80px'><div align='center'><font size='2' face='Tahoma'><strong>Kode Index</strong></font></div></td>
                                 <td width='80px'><div align='center'><font size='2' face='Tahoma'><strong>BPD</strong></font></div></td>
                                 <td width='70px'><div align='center'><font size='2' face='Tahoma'><strong>Rekening</strong></font></div></td>
                                 <td width='80px'><div align='center'><font size='2' face='Tahoma'><strong>Stts Aktif</strong></font></div></td>
                                 <td width='70px'><div align='center'><font size='2' face='Tahoma'><strong>Wajib Masuk</strong></font></div></td>
                                 <td width='100px'><div align='center'><font size='2' face='Tahoma'><strong>Mulai Kontrak</strong></font></div></td>
                                 <td width='100px'><div align='center'><font size='2' face='Tahoma'><strong>Photo</strong></font></div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                         $_sql2         = "SELECT normal-$jumlahlibur,jmlhr FROM set_tahun";
			 $hasil2        = bukaquery($_sql2);
			 $baris2        = mysqli_fetch_row($hasil2);
			 $jmlmsk         = $baris2[0];
			 if($baris[23]==-1){
			     $jmlmsk=0;
			 }else if($baris[23]==-2){
			     $jmlmsk=$baris2[1]-4;
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
                        echo "<tr class='isi'>
                                 <td>$baris[1]</td>
                                 <td>$baris[2]</td>
                                 <td>$baris[3]</td>
                                 <td>$baris[4]</td>
                                 <td>$baris[5]</td>
                                 <td>$baris[6]</td>
                                 <td>$baris[7]</td>
                                 <td>$baris[8]</td>
                                 <td>$baris[9]</td>
                                 <td>$baris[10]</td>
                                 <td>$baris[11]</td>
                                  <td>$baris[13]</td>
                                  <td>$baris[14]</td>
                                  <td>$baris[15]</td>
								  <td>$baris[16]</td>
                                  <td>$baris[17]</td>
                                  <td>$baris[18]</td>
                                  <td>$baris[19]</td>
                                  <td>$baris[20]</td>
                                  <td>$baris[21]</td>
                                  <td>$baris[22]</td>
                                  <td>$jmlmsk</td>
                                  <td>$baris[24]</td>
                                  <td>$gb</td>
                              </tr>";
                    }
            echo "</table>";

        } else {echo "Data Pencarian kosong !";}

    ?>

    </div>

    </form>


       <?php
            if(mysqli_num_rows($hasil)!=0) {
                $_sql = "select id,nik,nama,jk,jbtn,jnj_jabatan,departemen,bidang,stts_wp,stts_kerja,
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
                 order by nik ASC ";
                $hasil2=bukaquery($_sql);
                $jumladiv=mysqli_num_rows($hasil2);
                $i=$jumladiv/19;
                $i=ceil($i);
                echo("Jumlah Record : $jumladiv ");

             }
       ?>
    </div>
</div>
