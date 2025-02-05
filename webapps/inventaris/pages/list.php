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
                $action             = isset($_GET['action'])?$_GET['action']:NULL;
                $no_inventaris      = validTeks4(str_replace("_"," ",$_GET['no_inventaris']),30);
                $gambar             = validTeks(str_replace("_"," ",isset($_GET['gambar']))?str_replace("_"," ",isset($_GET['gambar'])):NULL);
                echo "<input type=hidden name=no_inventaris value=$no_inventaris>
                      <input type=hidden name=action value=$action>";
            ?>
            <div style="width: 100%; height: 10%; overflow: auto;">
                <table width="100%" align="center">
                    <tr class="head">
                        <td width="31%" >No.Inventaris</td><td width="">:</td>
                        <td width="67%"><?php echo $no_inventaris;?></td>
                    </tr>
                    <tr class="head">
                        <td width="31%" >File/Gambar</td><td width="">:</td>
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
                    $no_inventaris = validTeks4(trim($_POST['no_inventaris']),30);
                    $gambar        = validTeks(str_replace(" ","_","pages/upload/".$_FILES['gambar']['name']));
                    if((strtolower(substr($gambar,-3))=="jpg")||(strtolower(substr($gambar,-4))=="jpeg")){
                        if ((!empty($no_inventaris))&&(!empty($gambar))) {
                            move_uploaded_file($_FILES['gambar']['tmp_name'],$gambar);
                            Tambah(" inventaris_gambar "," '$no_inventaris','$gambar'", " Gambar Inventaris " );
                            echo"<meta http-equiv='refresh' content='1;URL=?act=List&no_inventaris=$no_inventaris'>";                              
                        }else if ((empty($no_inventaris))||(empty($gambar))){
                            echo 'Semua field harus isi..!!!';
                        }
                    }else{
                        echo "Berkas harus JPEG/JPG";
                    }  
                }
            ?>
            <div style="width: 100%; height: 78%; overflow: auto;">
            <?php
                $_sql = "SELECT * from inventaris_gambar where no_inventaris='$no_inventaris'";
                $hasil=bukaquery($_sql);

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='95%'><div align='center'>File/Gambar</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td valign='Top' align='center'>
                                    <center>"; ?>
                                    <a href="?act=List&action=HAPUS&no_inventaris=<?php print $baris["no_inventaris"] ?>&gambar=<?php print $baris["photo"] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td valign='Top' align='center'><img src='$baris[photo]' width='99%' height='99%'></td>
                           </tr>";
                    }
                    echo "</table>";

                }
            ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                unlink($_GET['gambar']);
                Hapus(" inventaris_gambar "," no_inventaris ='".validTeks4($_GET['no_inventaris'],30)."' ","?act=List&action=TAMBAH&no_inventaris=$no_inventaris");
            }
        
        ?>
    </div>

</div>
