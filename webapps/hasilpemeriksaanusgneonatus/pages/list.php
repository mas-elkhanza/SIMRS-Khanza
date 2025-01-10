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
                $action             = isset($_GET['action'])?$_GET['action']:NULL;
                $no_rawat           = validTeks4((isset($_GET['no_rawat'])?$_GET['no_rawat']:NULL),20);
                $no_rm              = getOne("select reg_periksa.no_rkm_medis from reg_periksa where reg_periksa.no_rawat='$no_rawat'");
                $nama_pasien        = getOne("select pasien.nm_pasien from pasien where pasien.no_rkm_medis='$no_rm'");
                echo "<input type=hidden name=no_rawat value=$no_rawat>
                      <input type=hidden name=action value=$action>";
            ?>
            <div style="width: 100%; height: 17%; overflow: auto;">
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >No.Rawat</td><td width="">:</td>
                    <td width="67%"><?php echo $no_rawat;?></td>
                </tr>
	        <tr class="head">
                    <td width="31%">Pasien</td><td width="">:</td>
                    <td width="67%"><?php echo $no_rm." ".$nama_pasien;?></td>
                </tr> 
                <tr class="head">
                    <td width="31%" >File Gambar USG</td><td width="">:</td>
                    <td width="67%">
                        <input name="gambar" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=file id="TxtIsi1" value="<?php echo $gambar;?>" size="50" maxlength="500" accept="image/jpeg,image/jpg"/>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>        
            </table>
            </div>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;Simpan&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;Kosong&nbsp;&nbsp;"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $no_rawat   = validTeks4(trim($_POST['no_rawat']),20);
                    $gambar     = validTeks(str_replace(" ","_","pages/upload/".$_FILES['gambar']['name']));
                    if((strtolower(substr($gambar,-4))==".jpg")||(strtolower(substr($gambar,-5))==".jpeg")){
                        if(($_FILES['gambar']['type'] == 'image/jpeg')||($_FILES['gambar']['type'] == 'image/jpg')){
                            if((@mime_content_type($_FILES['gambar']['tmp_name'])== 'image/jpeg')||(@mime_content_type($_FILES['gambar']['tmp_name'])== 'image/jpg')){
                                if ((!empty($no_rawat))&&(!empty($gambar))) {
                                    if(Tambah(" hasil_pemeriksaan_usg_neonatus_gambar "," '$no_rawat','$gambar'", " Gambar USG " )){
                                        move_uploaded_file($_FILES['gambar']['tmp_name'],$gambar);
                                    }
                                    echo"<meta http-equiv='refresh' content='1;URL=?act=List&no_rawat=$no_rawat&tanggal=$tanggal&jam=$jam'>";                              
                                }else if ((empty($no_rawat))||(empty($gambar))){
                                    echo 'Semua field harus isi..!!!';
                                }
                            }else{
                                echo "Berkas harus JPEG/JPG";
                            }
                        }else{
                            echo "Berkas harus JPEG/JPG";
                        }
                    }else{
                        echo "Berkas harus JPEG/JPG";
                    }
                }
            ?>
            <div style="width: 100%; height: 72%; overflow: auto;">
            <?php
                $_sql = "SELECT * from hasil_pemeriksaan_usg_neonatus_gambar where hasil_pemeriksaan_usg_neonatus_gambar.no_rawat='$no_rawat'";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                $ttllembur=0;
                $ttlhr=0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='95%'><div align='center'>Gambar USG</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td valign='Top' align='center'>
                                    <center>"; ?>
                                    <a href="?act=List&action=HAPUS&no_rawat=<?php print $baris[0] ?>&gambar=<?php print $baris[1] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td valign='Top' align='center'><img src='$baris[1]' width='99%' height='99%'></td>
                           </tr>";
                    }
                echo "</table>";

            } else {
                echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='95%'><div align='center'>Gambar USG</div></td>
                            </tr>
                      </table>";
            }
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                unlink($_GET['gambar']);
                Hapus(" hasil_pemeriksaan_usg_neonatus_gambar "," no_rawat ='".validTeks4($_GET['no_rawat'],20)."'","?act=List&action=TAMBAH&no_rawat=$no_rawat");
            }

        
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        
        ?>
    </div>

</div>
