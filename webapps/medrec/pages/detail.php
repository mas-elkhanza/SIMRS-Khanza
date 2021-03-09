
<div id="post">
    <div class="entry">        
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =isset($_GET['action'])?$_GET['action']:NULL;
                $id                 =isset($_GET['id'])?$_GET['id']:NULL;
                echo "<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
                echo "<div align='center' class='link'>
                          <a href=?act=List>| List Retensi |</a>
                      </div>";
            ?>
            <div style="width: 100%; height: 40%; overflow: auto;">
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >No.RM</td><td width="">:</td>
                    <td width="67%"><?php echo $id;?></td>
                </tr>
		        <tr class="head">
                    <td width="31%">Nama Pasien</td><td width="">:</td>
                    <td width="67%"><?php echo getOne("select nm_pasien from pasien where no_rkm_medis='$id'");?></td>
                </tr>
                <tr class="head">
                    <td width="31%">Jenis Kelamin</td><td width="">:</td>
                    <td width="67%"><?php echo getOne("select if(jk='L','Laki-Laki','Perempuan') from pasien where no_rkm_medis='$id'");?></td>
                </tr>
                <tr class="head">
                    <td width="31%">Tanggal Lahir</td><td width="">:</td>
                    <td width="67%"><?php echo getOne("select tgl_lahir from pasien where no_rkm_medis='$id'");?></td>
                </tr>
                <tr class="head">
                    <td width="31%">Nama Ibu</td><td width="">:</td>
                    <td width="67%"><?php echo getOne("select nm_ibu from pasien where no_rkm_medis='$id'");?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Terakhir Daftar</td><td width="">:</td>
                    <td width="67%">
                        <select name="TglTerakhir" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi1' value=$TglTerakhir>$TglTerakhir</option>";
                                }
                                loadTgl2();
                             ?>
                        </select>
			<select name="BlnTerakhir" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi1' value=$BlnTerakhir>$BlnTerakhir</option>";
                                }
                                loadBln2();
                             ?>
                        </select>
			<select name="ThnTerakhir" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi1' value=$ThnTerakhir>$ThnTerakhir</option>";
                                }
                                loadThn4();
                             ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Tgl.Retensi</td><td width="">:</td>
                    <td width="67%">
                        <select name="TglRetensi" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi2' value=$TglRetensi>$TglRetensi</option>";
                                }
                                loadTglnow();
                             ?>
                        </select>
			<select name="BlnRetensi" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi2' value=$BlnRetensi>$BlnRetensi</option>";
                                }
                                loadBlnnow();
                             ?>
                        </select>
			<select name="ThnRetensi" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi2' value=$ThnRetensi>$ThnRetensi</option>";
                                }
                                loadThnnow();
                             ?>
                        </select>
                        <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>                
                <tr class="head">
                    <td width="31%" >File Retensi</td><td width="">:</td>
                    <td width="67%"><input name="dokumen" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=file id="TxtIsi3" value="<?php echo $dokumen;?>" size="30" maxlength="255" />
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>        
            </table>
            </div>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;Simpan&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;Kosong&nbsp;&nbsp;"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id                 =trim($_POST['id']);
                    $terakhir_daftar    =trim($_POST['ThnTerakhir'])."-".trim($_POST['BlnTerakhir'])."-".trim($_POST['TglTerakhir']);
                    $tgl_retensi        =trim($_POST['ThnRetensi'])."-".trim($_POST['BlnRetensi'])."-".trim($_POST['TglRetensi']);
                    $dokumen            =str_replace(" ","_","pages/upload/".$_FILES['dokumen']['name']);
                    move_uploaded_file($_FILES['dokumen']['tmp_name'],$dokumen);
                    
                    if ((!empty($id))&&(!empty($dokumen))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" retensi_pasien "," '$id','$terakhir_daftar','$tgl_retensi','$dokumen'", " Riwayat Retensi " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=Detail&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else if ((empty($id))||(empty($dokumen))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 42%; overflow: auto;">
            <?php
                $_sql = "SELECT * from retensi_pasien where no_rkm_medis='$id' ORDER BY tgl_retensi ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                $ttllembur=0;
                $ttlhr=0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='10%'><div align='center'>Terakhir Daftar</div></td>
                                <td width='10%'><div align='center'>Tgl.Retensi</div></td>
                                <td width='25%'><div align='center'>File Retensi</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td width='70'>
                                    <center>"; ?>
                                    <a href="?act=Detail&action=HAPUS&tgl_retensi=<?php print $baris[2] ?>&id=<?php echo $id ?>&lokasi_pdf=<?php print $baris[3] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[1]</td>
                                <td>$baris[2]</td>
                                <td><a target=_blank href=../medrec/pages/upload/$baris[3]>".str_replace("pages/upload/","",$baris[3])."</a></td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='10%'><div align='center'>Terakhir Daftar</div></td>
                                <td width='10%'><div align='center'>Tgl.Retensi</div></td>
                                <td width='25%'><div align='center'>File Retensi</div></td>
                            </tr>
                         </table>";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                unlink($_GET['lokasi_pdf']);
                Hapus(" retensi_pasien "," no_rkm_medis ='".$_GET['id']."' and tgl_retensi ='".$_GET['tgl_retensi']."' ","?act=Detail&action=TAMBAH&id=$id");
            }

        
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        
        ?>
    </div>

</div>
