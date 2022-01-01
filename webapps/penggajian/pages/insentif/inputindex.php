

<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baristh      = mysqli_fetch_row($hasil);
   $tahun        = empty($baristh[0])?date("Y"):$baristh[0];
   $bulan        = empty($baristh[1])?date("m"):$baristh[1];
?>

<div id="post">
    <br>
    <div align="center" class="link">
        <a href=?act=ListInsentif>| List Insentif |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $dep_id      =str_replace("_"," ",isset($_GET['dep_id']))?str_replace("_"," ",$_GET['dep_id']):NULL;
                if($action == "TAMBAH"){
                    $dep_id      = str_replace("_"," ",isset($_GET['dep_id']))?str_replace("_"," ",$_GET['dep_id']):NULL;
                    $persen      = "";
                }else if($action == "UBAH"){
                    $_sql         = "SELECT dep_id,persen FROM indexins WHERE dep_id='$dep_id' ";
                    $hasil        = bukaquery($_sql);
                    $baris        = mysqli_fetch_row($hasil);
                    $dep_id       = $baris[0];
                    $persen       = $baris[1];
                }
                echo"<input type=hidden name=dep_id value=$dep_id><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Pendapatan</td><td width="">:</td>
                    <td width="67%">
                         <select name="dep_id" class="text1" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1" autofocus>
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php                            
                                if($action == "UBAH"){
                                    $_sql2  = "SELECT dep_id,nama FROM departemen where dep_id='$dep_id' ORDER BY dep_id";
                                    $hasil2 = bukaquery($_sql2);
                                    while($baris2 = mysqli_fetch_array($hasil2)) {
                                        echo "<option id='TxtIsi1' value='$baris2[0]'>$baris2[0] $baris2[1]</option>";
                                    }
                                }
                                if($action == "TAMBAH"){
                                    $_sql     = "SELECT dep_id,nama FROM  departemen ORDER BY dep_id";
                                    $hasildep = bukaquery($_sql);
                                    while($barisdep = mysqli_fetch_array($hasildep)) {
                                        echo "<option id='TxtIsi1' value='$barisdep[0]'>$barisdep[0]  $barisdep[1]</option>";
                                    }
                                }                                
                            ?>
                        </select>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Porsi Insentif</td><td width="">:</td>
                    <td width="67%"><input name="persen" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $persen;?>" size="10" maxlength="6" />%
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                $_sql         = "SELECT * FROM set_tahun";
                $hasil        = bukaquery($_sql);
                $baris        = mysqli_fetch_row($hasil);
                $tahun        = empty($baris[0])?date("Y"):$baris[0];
                $bulan        = empty($baris[1])?date("m"):$baris[1];

                if (isset($BtnSimpan)) {
                    $dep_id    = trim($_POST['dep_id']);
                    $persen    = trim($_POST['persen']);
                    $persen    = validangka($persen);
                    if ((isset($dep_id))&&(isset($persen))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" indexins ","'$dep_id','$persen' ", " Porsi Insentif " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputIndex&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" indexins ","persen='$persen' WHERE dep_id='$dep_id'  ", " Porsi Insentif  ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListInsentif'></head><body></body></html>";
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
