<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        if(!strpos($_SERVER['REQUEST_URI'],"pages/riwayatpenghargaan/berkas/")){
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
            $jenis              = validTeks(isset($_GET['jenis'])?$_GET['jenis']:NULL);
            $nama_penghargaan   = validTeks(isset($_GET['nama_penghargaan'])?$_GET['nama_penghargaan']:NULL);
            echo "<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
            $_sql               = "SELECT pegawai.nik,pegawai.nama FROM pegawai where pegawai.id='$id'";
            $hasil              = bukaquery($_sql);
            $baris              = mysqli_fetch_row($hasil);   
            $_sqlnext         	= "SELECT pegawai.id FROM pegawai WHERE pegawai.id>'$id' order by pegawai.id asc limit 1";
            $hasilnext        	= bukaquery($_sqlnext);
            $barisnext        	= mysqli_fetch_row($hasilnext);
            @$next              = $barisnext[0];
            $_sqlprev         	= "SELECT pegawai.id FROM pegawai WHERE pegawai.id<'$id' order by pegawai.id desc limit 1";
            $hasilprev        	= bukaquery($_sqlprev);
            $barisprev        	= mysqli_fetch_row($hasilprev);
            @$prev              = $barisprev[0];
            if(empty($prev)){
                $prev = $next;
            }
            if(empty($next)){
                $next = $prev;
            }
            echo "<div align='center' class='link'>
                    <a href=?act=InputRiwayatPenghargaan&action=TAMBAH&id=$prev><<--</a>
                    <a href=?act=ListRiwayatPenghargaan&action=LIHAT>| List Riwayat Penghargaan |</a>
                    <a href=?act=InputRiwayatPenghargaan&action=TAMBAH&id=$next>-->></a>
                  </div>";
        ?>
        <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" class="tbl_form">
            <tr class="isi2">
                <td width="17%" >NIP</td><td width="">:</td>
                <td width="31%"><?php echo $baris[0];?></td>
                <td width="17%" >Tanggal Penghargaan</td><td width="">:</td>
                <td width="31%">
                    <select name="TglPenghargaan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                         <?php
                            loadTglnow();
                         ?>
                    </select>
                    <select name="BlnPenghargaan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                         <?php
                            loadBlnnow();
                         ?>
                    </select>
                    <select name="ThnPenghargaan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                         <?php
                            loadThnnow();
                         ?>
                    </select>
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%">Nama</td><td width="">:</td>
                <td width="31%"><?php echo $baris[1];?></td>
                <td width="17%" >Instansi Pemberi Penghargaan</td><td width="">:</td>
                <td width="31%">
                    <input name="instansi" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" type=text id="TxtIsi4" class="inputbox" value="<?php echo isset($instansi)?$instansi:NULL;?>" size="40" maxlength="40">
                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="17%" >Jenis Penghargaan</td><td width="">:</td>
                <td width="31%">
                    <input name="jenis" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo isset($jenis)?$jenis:NULL;?>" size="40" maxlength="30">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Pejabat Pemberi Penghargaan</td><td width="">:</td>
                <td width="31%">
                    <input name="pejabat_pemberi" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" type=text id="TxtIsi5" class="inputbox" value="<?php echo isset($pejabat_pemberi)?$pejabat_pemberi:NULL;?>" size="40" maxlength="40">
                    <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>  
            <tr class="isi2">
                <td width="17%" >Nama Penghargaan</td><td width="">:</td>
                <td width="31%">
                    <input name="nama_penghargaan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo isset($nama_penghargaan)?$nama_penghargaan:NULL;?>" size="40" maxlength="60">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="17%" >Sertifikat/Piagam Penghargaan</td><td width="">:</td>
                <td width="31%">
                    <input name="dokumen" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" type=file id="TxtIsi6" value="<?php echo $dokumen;?>" size="40" maxlength="255" accept="image/jpeg,image/jpg"/>
                    <span id="MsgIsi6" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
        </table>
        <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;SIMPAN&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;KOSONG&nbsp;&nbsp;"></div><br>
        <div style="width: 100%; height: 70%; overflow: auto;">
        <?php
            $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
            if (isset($BtnSimpan)) {
                $id                 = validTeks(trim($_POST['id']));
                $jenis              = validTeks(trim($_POST['jenis']));
                $nama_penghargaan   = validTeks(trim($_POST['nama_penghargaan']));
                $tanggal            = validTeks(trim($_POST['ThnPenghargaan'])."-".trim($_POST['BlnPenghargaan'])."-".trim($_POST['TglPenghargaan']));
                $instansi           = validTeks(trim($_POST['instansi']));
                $pejabat_pemberi    = validTeks(trim($_POST['pejabat_pemberi']));
                $dokumen            = validTeks(str_replace(" ","_","pages/riwayatpenghargaan/berkas/".$_FILES['dokumen']['name']));
                if ((!empty($id))&&(!empty($jenis))&&(!empty($nama_penghargaan))) {
                    switch($action) {
                        case "TAMBAH":
                            if((strtolower(substr($dokumen,-4))==".jpg")||(strtolower(substr($dokumen,-5))==".jpeg")){
                                if(($_FILES['dokumen']['type'] == 'image/jpeg')||($_FILES['dokumen']['type'] == 'image/jpg')){
                                    if((@mime_content_type($_FILES['dokumen']['tmp_name'])== 'image/jpeg')||(@mime_content_type($_FILES['dokumen']['tmp_name'])== 'image/jpg')){
                                        if(Tambah(" riwayat_penghargaan "," '$id','$jenis','$nama_penghargaan','$tanggal','$instansi','$pejabat_pemberi','$dokumen'", " Riwayat Penghargaan " )){
                                            move_uploaded_file($_FILES['dokumen']['tmp_name'],$dokumen);
                                        }
                                        echo"<meta http-equiv='refresh' content='1;URL=?act=InputRiwayatPenghargaan&action=TAMBAH&id=$id'>";
                                    }else{
                                        echo "Berkas harus JPEG/JPG";
                                    } 
                                }else{
                                    echo "Berkas harus JPEG/JPG";
                                } 
                            }else{
                                echo "Berkas harus JPEG/JPG";
                            } 
                            break;
                    }
                }else if ((empty($id))||(empty($jenis))||(empty($nama_penghargaan))){
                    echo 'Semua field harus isi..!!!';
                }
            }
            
            $_sql       = "SELECT riwayat_penghargaan.jenis,riwayat_penghargaan.nama_penghargaan,riwayat_penghargaan.tanggal,riwayat_penghargaan.instansi,riwayat_penghargaan.pejabat_pemberi,riwayat_penghargaan.berkas from riwayat_penghargaan where riwayat_penghargaan.id='$id' ORDER BY riwayat_penghargaan.tanggal ASC ";
            $hasil      = bukaquery($_sql);
            $jumlah     = mysqli_num_rows($hasil);
            $ttllembur  = 0;
            $ttlhr      = 0;
            if(mysqli_num_rows($hasil)!=0) {
                echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head3'>
                            <td width='5%'><div align='center'>Proses</div></td>
                            <td width='20%'><div align='center'>Jenis Penghargaan</div></td>
                            <td width='30%'><div align='center'>Nama Penghargaan</div></td>
                            <td width='5%'><div align='center'>Tanggal</div></td>
                            <td width='20%'><div align='center'>Instansi Pemberi Penghargaan</div></td>
                            <td width='20%'><div align='center'>Pejabat Pemberi Penghargaan</div></td>
                        </tr>";
                while($baris = mysqli_fetch_array($hasil)) {   
                    $gb="-";
                    if($baris["berkas"]=="pages/riwayatpenghargaan/berkas"){
                        $gb="-";                            
                    }else{
                        $gb="<img src='".$baris["berkas"]."' width='850px' height='950px'>";
                    }
                    echo "<tr class='isi'>
                            <td width='70'>
                                <center>
                                    <a href=?act=InputRiwayatPenghargaan&action=HAPUS&tanggal=".$baris["tanggal"]."&nama_penghargaan=".$baris["nama_penghargaan"]."&id=".$id."&berkas=".$baris["berkas"].">[hapus]</a>
                                </center>
                            </td>
                            <td>$baris[0]</td>
                            <td>$baris[1]</td>
                            <td>$baris[2]</td>
                            <td>$baris[3]</td>
                            <td>$baris[4]</td>
                          </tr>
                          <tr class='isi'>
                            <td width='70'></td>
                            <td valign='top' align='center' colspan='7'><a target=_blank href=../penggajian/".$baris["berkas"].">".$gb."</a></td>
                          </tr>";   
                }
                echo "</table>";
            } else {
                echo "<table width='99.8%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                           <tr class='head3'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='20%'><div align='center'>Jenis Penghargaan</div></td>
                                <td width='30%'><div align='center'>Nama Penghargaan</div></td>
                                <td width='5%'><div align='center'>Tanggal</div></td>
                                <td width='20%'><div align='center'>Instansi Pemberi Penghargaan</div></td>
                                <td width='20%'><div align='center'>Pejabat Pemberi Penghargaan</div></td>
                            </tr>
                      </table>";            
            }
        ?>
        </div>                        
    </form>
    <?php
        if ($action=="HAPUS") {
            unlink($_GET['berkas']);
            Hapus(" riwayat_penghargaan "," id ='".validTeks($_GET['id'])."' and tanggal ='".validTeks($_GET['tanggal'])."' and nama_penghargaan ='".validTeks($_GET['nama_penghargaan'])."'","?act=InputRiwayatPenghargaan&action=TAMBAH&id=$id");
        }
    ?>
</div>
