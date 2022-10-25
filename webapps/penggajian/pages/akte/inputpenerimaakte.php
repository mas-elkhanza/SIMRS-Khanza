<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $_sql         = "SELECT * FROM set_tahun";
    $hasil        = bukaquery($_sql);
    $baristh      = mysqli_fetch_row($hasil);
    $tahun        = empty($baristh[0])?date("Y"):$baristh[0];
    $bulan        = empty($baristh[1])?date("m"):$baristh[1];
?>

<div id="post">
    <div align="center" class="link">
        <a href=?act=ListAkte>| List Akte |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      = isset($_GET['action'])?$_GET['action']:NULL;
                $id          = validTeks(str_replace("_"," ",isset($_GET['id']))?str_replace("_"," ",$_GET['id']):NULL);
                if($action == "TAMBAH"){
                    $id          = validTeks(str_replace("_"," ",isset($_GET['id']))?str_replace("_"," ",$_GET['id']):NULL);
                    $persen      = "";
                }else if($action == "UBAH"){
                    $_sql         = "SELECT pembagian_akte.id,pembagian_akte.persen FROM pembagian_akte WHERE pembagian_akte.id='$id' ";
                    $hasil        = bukaquery($_sql);
                    $baris        = mysqli_fetch_row($hasil);
                    $id           = $baris[0];
                    $persen       = $baris[1];
                }
                echo"<input type=hidden name=id value=$id><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Pegawai</td><td width="">:</td>
                    <td width="67%">
                         <select name="id" class="text1" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1" autofocus>
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php                            
                                if($action == "UBAH"){
                                    $_sql2 = "SELECT pegawai.id,pegawai.nik,pegawai.nama FROM pegawai where pegawai.id='$id' ORDER BY pegawai.nama";
                                    $hasil2=bukaquery($_sql2);
                                    while($baris2 = mysqli_fetch_array($hasil2)) {
                                        echo "<option id='TxtIsi1' value='$baris2[0]'>$baris2[2] $baris2[1]</option>";
                                    }
                                }
                                if($action == "TAMBAH"){
                                    $_sql = "SELECT pegawai.id,pegawai.nik,pegawai.nama FROM pegawai ORDER BY pegawai.nama";
                                    $hasildep=bukaquery($_sql);
                                    while($barisdep = mysqli_fetch_array($hasildep)) {
                                        echo "<option id='TxtIsi1' value='$barisdep[0]'>$barisdep[2] $barisdep[1]</option>";
                                    }
                                }                                
                            ?>
                        </select>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Porsi Akte(%)</td><td width="">:</td>
                    <td width="67%"><input name="persen" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $persen;?>" size="10" maxlength="6" pattern="[0-9-]{1,6}" title=" 0-9- (Maksimal 6 karakter)" autocomplete="off"/>%
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan= isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id          = validTeks(trim($_POST['id']));
                    $persen      = validTeks(trim($_POST['persen']));
                    $persen      = validangka($persen);
                    if ((isset($id))&&(isset($persen))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" pembagian_akte ","'$id','$persen' ", " Porsi Akte " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputPenerimaAkte&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" pembagian_akte ","persen='$persen' WHERE id='$id'  ", " Porsi Akte  ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListAkte'></head><body></body></html>";
                                break;
                        }
                    }else{
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>
