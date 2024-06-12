<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        if(!strpos($_SERVER['REQUEST_URI'],"pages/riwayatgaji/berkas/")){
            exit(header("Location:../index.php"));
        }
    }
?>
<div id="entry">       
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
            echo "";
            $action             = isset($_GET['action'])?$_GET['action']:NULL;
            $id                 = validTeks(isset($_GET['id'])?$_GET['id']:NULL);
            $jenis_penelitian   = validTeks(isset($_GET['jenis_penelitian'])?$_GET['jenis_penelitian']:NULL);
            $judul_penelitian   = validTeks(isset($_GET['judul_penelitian'])?$_GET['judul_penelitian']:NULL);
            echo "<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
            $_sql               = "SELECT pegawai.nik,pegawai.nama FROM pegawai where pegawai.id='$id'";
            $hasil              = bukaquery($_sql);
            $baris              = mysqli_fetch_row($hasil);   
            $_sqlnext         	= "SELECT pegawai.id FROM pegawai WHERE pegawai.id>'$id' order by pegawai.id asc limit 1";
            $hasilnext        	= bukaquery($_sqlnext);
            $barisnext        	= mysqli_fetch_row($hasilnext);
            $next               = $barisnext[0];
            $_sqlprev         	= "SELECT pegawai.id FROM pegawai WHERE pegawai.id<'$id' order by pegawai.id desc limit 1";
            $hasilprev        	= bukaquery($_sqlprev);
            $barisprev        	= mysqli_fetch_row($hasilprev);
            $prev               = $barisprev[0];
            if(empty($prev)){
                $prev = $next;
            }
            if(empty($next)){
                $next = $prev;
            }
            echo "<div align='center' class='link'>
                    <a href=?act=InputRiwayatPenelitian&action=TAMBAH&id=$prev><<--</a>
                    <a href=?act=ListRiwayatPenelitian&action=LIHAT>| List Riwayat Penelitian |</a>
                    <a href=?act=InputRiwayatPenelitian&action=TAMBAH&id=$next>-->></a>
                  </div>";
        ?>
        <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" class="tbl_form">
            <tr class="isi2">
                <td width="17%" >NIP</td><td width="">:</td>
                <td width="31%"><?php echo $baris[0];?></td>
                <td width="17%" >Diterbitkan di Jurnal</td><td width="">:</td>
                <td width="31%">
                    <input name="judul_jurnal" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" type=text id="TxtIsi4" class="inputbox" value="<?php echo isset($judul_jurnal)?$judul_jurnal:NULL;?>" size="40" maxlength="60">
                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%">Nama</td><td width="">:</td>
                <td width="31%"><?php echo $baris[1];?></td>
                <td width="17%" >Tahun Penelitian</td><td width="">:</td>
                <td width="31%">
                    <select name="tahun" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                         <?php
                            loadThnnow();
                         ?>
                    </select>
                    <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >Jenis Penelitian</td><td width="">:</td>
                <td width="31%">
                    <input name="jenis_penelitian" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo isset($jenis_penelitian)?$jenis_penelitian:NULL;?>" size="40" maxlength="30">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Biaya Penelitian</td><td width="">:</td>
                <td width="31%">
                    <input name="biaya_penelitian" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" type=text id="TxtIsi6" class="inputbox" value="<?php echo isset($biaya_penelitian)?$biaya_penelitian:NULL;?>" size="25" maxlength="15">
                    <span id="MsgIsi6" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>  
            <tr class="isi2">
                <td width="17%" >Peranan Dalam Penelitian</td><td width="">:</td>
                <td width="31%">
                    <input name="peranan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo isset($peranan)?$peranan:NULL;?>" size="40" maxlength="30">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Asal Dana</td><td width="">:</td>
                <td width="31%">
                    <input name="asal_dana" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" type=text id="TxtIsi7" class="inputbox" value="<?php echo isset($asal_dana)?$asal_dana:NULL;?>" size="40" maxlength="30">
                    <span id="MsgIsi7" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >Judul Penelitian</td><td width="">:</td>
                <td width="31%">
                    <input name="judul_penelitian" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo isset($judul_penelitian)?$judul_penelitian:NULL;?>" size="40" maxlength="60">
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Makalah/Berkas Penelitian</td><td width="">:</td>
                <td width="31%">
                    <input name="dokumen" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi8'));" type=file id="TxtIsi8" value="<?php echo $dokumen;?>" size="40" maxlength="255" accept="application/pdf"/>
                    <span id="MsgIsi8" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr> 
        </table>
        <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;SIMPAN&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;KOSONG&nbsp;&nbsp;"></div><br>
        <div style="width: 100%; height: 60%; overflow: auto;">
        <?php
            $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
            if (isset($BtnSimpan)) {
                $id                 = validTeks(trim($_POST['id']));
                $jenis_penelitian   = validTeks(trim($_POST['jenis_penelitian']));
                $peranan            = validTeks(trim($_POST['peranan']));
                $tahun              = validTeks(trim($_POST['tahun']));
                $judul_penelitian   = validTeks(trim($_POST['judul_penelitian']));
                $judul_jurnal       = validTeks(trim($_POST['judul_jurnal']));
                $biaya_penelitian   = validTeks(trim($_POST['biaya_penelitian']));
                $asal_dana          = validTeks(trim($_POST['asal_dana']));
                $dokumen            = validTeks(str_replace(" ","_","pages/riwayatpenelitian/berkas/".$_FILES['dokumen']['name']));
                if ((!empty($id))&&(!empty($jenis_penelitian))&&(!empty($judul_penelitian))) {
                    switch($action) {
                        case "TAMBAH":
                            if((strtolower(substr($dokumen,-4))==".jpg")||(strtolower(substr($dokumen,-5))==".jpeg")){
                                if(($_FILES['dokumen']['type'] == 'image/jpeg')||($_FILES['dokumen']['type'] == 'image/jpg')){
                                    if((mime_content_type($_FILES['dokumen']['tmp_name'])== 'image/jpeg')||(mime_content_type($_FILES['dokumen']['tmp_name'])== 'image/jpg')){
                                        if(Tambah(" riwayat_penelitian "," '$id','$jenis_penelitian','$peranan','$judul_penelitian','$judul_jurnal','$tahun','$biaya_penelitian','$asal_dana','$dokumen'", " Riwayat Penelitian " )){
                                            move_uploaded_file($_FILES['dokumen']['tmp_name'],$dokumen);
                                        }
                                        echo"<meta http-equiv='refresh' content='1;URL=?act=InputRiwayatPenelitian&action=TAMBAH&id=$id'>";
                                    }else{
                                        echo "Berkas harus JPEG/JPG";
                                    } 
                                }else{
                                    echo "Berkas harus JPEG/JPG";
                                } 
                            }else{
                                echo "Berkas harus pdf";
                            } 
                            break;
                    }
                }else if ((empty($id))||(empty($jenis_penelitian))||(empty($judul_penelitian))){
                    echo 'Semua field harus isi..!!!';
                }
            }
            
            $_sql       = "SELECT riwayat_penelitian.jenis_penelitian,riwayat_penelitian.peranan,riwayat_penelitian.judul_penelitian,riwayat_penelitian.judul_jurnal,riwayat_penelitian.tahun,riwayat_penelitian.biaya_penelitian,riwayat_penelitian.asal_dana,riwayat_penelitian.berkas from riwayat_penelitian where riwayat_penelitian.id='$id' ORDER BY riwayat_penelitian.tahun ASC ";
            $hasil      = bukaquery($_sql);
            $jumlah     = mysqli_num_rows($hasil);
            $ttllembur  = 0;
            $ttlhr      = 0;
            if(mysqli_num_rows($hasil)!=0) {
                echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head3'>
                            <td width='5%'><div align='center'>Proses</div></td>
                            <td width='15%'><div align='center'>Jenis Penelitian</div></td>
                            <td width='15%'><div align='center'>Peranan</div></td>
                            <td width='20%'><div align='center'>Judul Penelitian</div></td>
                            <td width='15%'><div align='center'>Diterbitkan di Jurnal</div></td>
                            <td width='5%'><div align='center'>Tahun</div></td>
                            <td width='5%'><div align='center'>Biaya</div></td>
                            <td width='10%'><div align='center'>Asal Dana</div></td>
                            <td width='10%'><div align='center'>Makalah/Berkas</div></td>
                        </tr>";
                while($baris = mysqli_fetch_array($hasil)) {   
                    echo "<tr class='isi'>
                            <td width='70' valign='top'>
                                <center>
                                    <a href=?act=InputRiwayatPenelitian&action=HAPUS&tahun=".$baris["tahun"]."&judul_penelitian=".$baris["judul_penelitian"]."&id=".$id."&berkas=".$baris["berkas"].">[hapus]</a>
                                </center>
                            </td>
                            <td valign='top'>$baris[0]</td>
                            <td valign='top'>$baris[1]</td>
                            <td valign='top'>$baris[2]</td>
                            <td valign='top'>$baris[3]</td>
                            <td valign='top'>$baris[4]</td>
                            <td valign='top'>$baris[5]</td>
                            <td valign='top'>$baris[6]</td>
                            <td valign='top'><a target=_blank href=../penggajian/pages/".$baris["berkas"].">".str_replace("pages/riwayatpenelitian/berkas/","",$baris["berkas"])."</a></td>
                          </tr>";
                }
                echo "</table>";
            } else {
                echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                           <tr class='head3'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='15%'><div align='center'>Jenis Penelitian</div></td>
                                <td width='15%'><div align='center'>Peranan</div></td>
                                <td width='20%'><div align='center'>Judul Penelitian</div></td>
                                <td width='15%'><div align='center'>Diterbitkan di Jurnal</div></td>
                                <td width='5%'><div align='center'>Tahun</div></td>
                                <td width='5%'><div align='center'>Biaya</div></td>
                                <td width='10%'><div align='center'>Asal Dana</div></td>
                                <td width='10%'><div align='center'>Makalah/Berkas</div></td>
                            </tr>
                      </table>";            
            }
        ?>
        </div>
    </form>
    <?php
        if ($action=="HAPUS") {
            unlink($_GET['berkas']);
            Hapus(" riwayat_penelitian "," id ='".validTeks($_GET['id'])."' and tahun ='".validTeks($_GET['tahun'])."' and judul_penelitian ='".validTeks($_GET['judul_penelitian'])."'","?act=InputRiwayatPenelitian&action=TAMBAH&id=$id");
        }
    ?>
</div>
