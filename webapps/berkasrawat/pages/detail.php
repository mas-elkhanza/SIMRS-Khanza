<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        if(!strpos($_SERVER['REQUEST_URI'],"pages/upload/")){
            exit(header("Location:../index.php"));
        }
    }
?>
<div id="post">
    <div class="entry">        
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action       = isset($_GET['action'])?$_GET['action']:NULL;
                $norawat      = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
                $norawat      = json_decode(encrypt_decrypt($norawat,"d"),true); 
                if (isset($norawat["no_rawat"])) {
                    $no_rawat = validTeks4($norawat["no_rawat"],20);
                }else{
                    exit(header("Location:../index.php"));
                }
                
                $_sql         = "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,
                                reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,
                                pasien.umur,poliklinik.nm_poli,reg_periksa.status_lanjut,reg_periksa.umurdaftar,reg_periksa.sttsumur,
                                reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab 
                                from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab 
                                on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                                and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='$no_rawat' ";
                @$hasil        = bukaquery($_sql);
                @$baris        = mysqli_fetch_array($hasil);
                @$no_rkm_medis = $baris["no_rkm_medis"];
                @$nm_pasien    = $baris["nm_pasien"];
                @$umurdaftar   = $baris["umurdaftar"];
                @$sttsumur     = $baris["sttsumur"];
                @$jk           = $baris["jk"];
                @$almt_pj      = $baris["almt_pj"];
                @$tgl_registrasi = $baris["tgl_registrasi"]." ".$baris["jam_reg"];
                @$nm_poli      = $baris["nm_poli"];
                @$nm_dokter    = $baris["nm_dokter"];
                @$status_lanjut  = $baris["status_lanjut"];
                @$png_jawab    = $baris["png_jawab"];

                echo "<input type=hidden name=no_rawat  value=$no_rawat>
                      <input type=hidden name=action value=$action>";
                echo "<div align='center' class='link'>
                          <a href=?act=List>| List Berkas |</a>
                      </div>";
            ?>
            <div style="width: 100%; height: 45%; overflow: auto;">
            <table width="100%" align="center">
                <tr class="isi2">
                    <td width="25%" >No.Rawat</td><td width="">:</td>
                    <td width="75%"><?php echo $no_rawat;?></td>
                </tr>
                <tr class="isi2">
                    <td width="25%" >No.RM</td><td width="">:</td>
                    <td width="75%"><?php echo $no_rkm_medis;?></td>
                </tr>
		<tr class="isi2">
                    <td width="25%">Nama Pasien</td><td width="">:</td>
                    <td width="75%"><?php echo $nm_pasien.", ".$umurdaftar." ".$sttsumur;?></td>
                </tr>
                <tr class="isi2">
                    <td width="25%">Jenis Kelamin</td><td width="">:</td>
                    <td width="75%"><?php echo $jk;?></td>
                </tr>
                <tr class="isi2">
                    <td width="25%">Alamat</td><td width="">:</td>
                    <td width="75%"><?php echo $almt_pj;?></td>
                </tr>
                <tr class="isi2">
                    <td width="25%">Tgl.Registrasi</td><td width="">:</td>
                    <td width="75%"><?php echo $tgl_registrasi;?></td>
                </tr>   
                <tr class="isi2">
                    <td width="25%">Poliklinik</td><td width="">:</td>
                    <td width="75%"><?php echo $nm_poli;?></td>
                </tr> 
                <tr class="isi2">
                    <td width="25%">Dokter</td><td width="">:</td>
                    <td width="75%"><?php echo $nm_dokter;?></td>
                </tr> 
                <tr class="isi2">
                    <td width="25%">Status</td><td width="">:</td>
                    <td width="75%"><?php echo $status_lanjut." (".$png_jawab.")";?></td>
                </tr>
                <tr class="isi2">
                    <td width="25%" >Berkas Digital</td><td width="">:</td>
                    <td width="75%">
                        <select name="kode" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                            <?php
                                $_sql = "SELECT master_berkas_digital.kode,master_berkas_digital.nama FROM master_berkas_digital ORDER BY master_berkas_digital.nama";
                                $hasil=bukaquery($_sql);

                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi1' value='$baris[0]'>$baris[1]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="isi2">
                    <td width="25%" >File Berkas(PDF/JPG/JPEG)</td><td width="">:</td>
                    <td width="75%"><input name="dokumen" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=file id="TxtIsi2" value="<?php echo $dokumen;?>" size="30" maxlength="255" accept="application/pdf,image/jpeg,image/jpg"/>
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>        
            </table>
            </div>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;Simpan&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;Kosong&nbsp;&nbsp;"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $no_rawat           = validTeks(trim($_POST['no_rawat']));
                    $kode               = validTeks(trim($_POST['kode']));
                    $dokumen            = validTeks(str_replace(" ","_","pages/upload/".$_FILES['dokumen']['name']));
                    if((strtolower(substr($dokumen,-3))=="jpg")||(strtolower(substr($dokumen,-3))=="pdf")||(strtolower(substr($dokumen,-4))=="jpeg")){
                        move_uploaded_file($_FILES['dokumen']['tmp_name'],$dokumen);
                        if ((!empty($no_rawat))&&(!empty($kode))&&(!empty($dokumen))) {
                            switch($action) {
                                case "TAMBAH":
                                    Tambah(" berkas_digital_perawatan "," '$no_rawat','$kode','$dokumen'", " Berkas Digital Perawatan " );
                                    echo"<meta http-equiv='refresh' content='1;URL=?act=Detail&action=TAMBAH&iyem=".encrypt_decrypt("{\"no_rawat\":\"".validTeks($no_rawat)."\"}","e")."'>";
                                    break;
                            }
                        }else if ((empty($no_rawat))||(empty($kode))||(empty($dokumen))){
                            echo 'Semua field harus isi..!!!';
                        }
                    }else{
                        echo "Berkas harus pdf/JPG";
                    }
                }
            ?>
            <div style="width: 100%; height: 35%; overflow: auto;">
            <?php
                $_sql = "SELECT berkas_digital_perawatan.no_rawat,berkas_digital_perawatan.kode, 
                        master_berkas_digital.nama,berkas_digital_perawatan.lokasi_file 
                        from berkas_digital_perawatan inner join master_berkas_digital 
                        on berkas_digital_perawatan.kode=master_berkas_digital.kode 
                        where berkas_digital_perawatan.no_rawat='$no_rawat' ORDER BY master_berkas_digital.nama ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                $ttllembur=0;
                $ttlhr=0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='30%'><div align='center'>Berkas Digital</div></td>
                                <td width='65%'><div align='center'>File</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td>
                                    <center>
                                    <a href='?act=Detail&action=HAPUS&iyem=".encrypt_decrypt("{\"no_rawat\":\"".$baris["no_rawat"]."\",\"kode\":\"".$baris["kode"]."\",\"lokasi_file\":\"".$baris["lokasi_file"]."\"}","e")."'>[hapus]</a>
                                   </center>
                                </td>
                                <td>".$baris["nama"]."</td>
                                <td><a target=_blank href=../berkasrawat/pages/upload/".$baris["lokasi_file"].">".str_replace("pages/upload/","",$baris["lokasi_file"])."</a></td>
                           </tr>";
                    }
                    echo "</table>";
                } else {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='30%'><div align='center'>Berkas Digital</div></td>
                                <td width='65%'><div align='center'>File</div></td>
                            </tr>
                          </table>";}
            ?>
            </div>
        </form>
        <?php
            if ($action=="HAPUS") {                
                unlink($norawat["lokasi_file"]);
                Hapus(" berkas_digital_perawatan "," no_rawat ='".validTeks($norawat["no_rawat"])."' and kode ='".validTeks($norawat["kode"])."' and lokasi_file='".validTeks($norawat["lokasi_file"])."' ","?act=Detail&action=TAMBAH&iyem=".encrypt_decrypt("{\"no_rawat\":\"".validTeks($no_rawat)."\"}","e"));
            }
        
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        
        ?>
    </div>

</div>
