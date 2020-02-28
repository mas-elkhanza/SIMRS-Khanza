

<div id="post">
    <div align="center" class="link">
        <a href=?act=InputPendidikan&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListPendidikan>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_pendidikan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $tingkat     =str_replace("_"," ",isset($_GET['tingkat']))?str_replace("_"," ",$_GET['tingkat']):NULL;
                $tingkatx     ="";
                if($action == "TAMBAH"){
                    $tingkat      = isset($_GET['tingkat'])?$_GET['tingkat']:NULL;
                    $indek        ="";
                }else if($action == "UBAH"){
                    $_sql         = "SELECT tingkat,indek,gapok1,kenaikan,maksimal FROM pendidikan WHERE tingkat='$tingkat'";
                    $hasil        = bukaquery($_sql);
                    $baris        = mysqli_fetch_row($hasil);
                    $tingkat      = $baris[0];
                    $tingkatx     = $baris[0];
                    $indek        = $baris[1];
                    $gapok1       = $baris[2];
                    $kenaikan       = $baris[3];
                    $maksimal       = $baris[4];
                }
                echo"<input type=hidden name=tingkatx value='$tingkatx'><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Tingkat Pendidikan</td><td width="">:</td>
                    <td width="67%"><input name="tingkat" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo isset($tingkat)?$tingkat:NULL;?>" size="50" maxlength="80">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Index Pendidikan</td><td width="">:</td>
                    <td width="67%"><input name="indek" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo isset($indek)?$indek:NULL;?>" size="10" maxlength="2">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Gaji Pokok</td><td width="">:</td>
                    <td width="67%"><input name="gapok1" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo isset($gapok1)?$gapok1:NULL;?>" size="10" maxlength="15">
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Kenaikan Per Tahun</td><td width="">:</td>
                    <td width="67%"><input name="kenaikan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" type=text id="TxtIsi4" class="inputbox" value="<?php echo isset($kenaikan)?$kenaikan:NULL;?>" size="10" maxlength="15">
                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Jml Tahun Maksimal</td><td width="">:</td>
                    <td width="67%"><input name="maksimal" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" type=text id="TxtIsi5" class="inputbox" value="<?php echo isset($maksimal)?$maksimal:NULL;?>" size="10" maxlength="15">
                    <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $tingkat        = validTeks(trim($_POST['tingkat']));
                    $tingkatx       = validTeks(trim($_POST['tingkatx']));
                    $indek          = validTeks(trim($_POST['indek']));
                    $gapok1         = validTeks(trim($_POST['gapok1']));
                    $kenaikan       = validTeks(trim($_POST['kenaikan']));
                    $maksimal       = validTeks(trim($_POST['maksimal']));
                    if (!empty($tingkat)&&!empty($indek)&&!empty($gapok1)&&!empty($kenaikan)&&!empty($maksimal)) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" pendidikan "," '$tingkat','$indek','$gapok1','$kenaikan','$maksimal' ", " Pendidikan " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputPendidikan&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" pendidikan "," tingkat='$tingkat',indek='$indek',gapok1='$gapok1',kenaikan='$kenaikan',
                                        maksimal='$maksimal' WHERE tingkat='$tingkatx' ", "Pendidikan");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListPendidikan'></head><body></body></html>";
                                break;
                        }
                    }else if (empty($tingkat)||empty($indek)||empty($gapok1)||empty($kenaikan)||empty($maksimal)){
                        echo 'Semua field harus isi..!! ';
                        //echo "1'$tingkat',2'$indek',3'$gapok1',4'$kenaikan',5'$maksimal'";
                    }
                }
            ?>
        </form>
    </div>
</div>

