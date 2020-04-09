

<div id="post">
    <div align="center" class="link">
        <a href=?act=InputEvaluasiKinerja&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListEvaluasiKinerja>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action   =isset($_GET['action'])?$_GET['action']:NULL;
                $kode_evaluasi     =str_replace("_"," ",isset($_GET['kode_evaluasi'])?$_GET['kode_evaluasi']:NULL);
                if($action == "TAMBAH"){
                    $kode_evaluasi       = str_replace("_"," ",isset($_GET['kode_evaluasi']))?str_replace("_"," ",$_GET['kode_evaluasi']):NULL;
                    $nama_evaluasi        = "";
                    $indek      ="";
                }else if($action == "UBAH"){
                    $_sql           = "SELECT * FROM evaluasi_kinerja WHERE kode_evaluasi='$kode_evaluasi'";
                    $hasil          = bukaquery($_sql);
                    $baris          = mysqli_fetch_row($hasil);
                    $kode_evaluasi  = $baris[0];
                    $nama_evaluasi  = $baris[1];
                    $indek          = $baris[2];
                }
                echo"<input type=hidden name=kode_evaluasi value=$kode_evaluasi><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Kode</td><td width="">:</td>
                    <td width="67%"><input name="kode_evaluasi" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $kode_evaluasi;?>" size="10" maxlength="3">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Evaluasi Kinerja</td><td width="">:</td>
                    <td width="67%"><input name="nama_evaluasi" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $nama_evaluasi;?>" size="40" maxlength="100" />
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Index</td><td width="">:</td>
                    <td width="67%"><input name="indek" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo $indek;?>" size="10" maxlength="4" />
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $kode_evaluasi    = trim($_POST['kode_evaluasi']);
                    $kode_evaluasi    = validTeks($kode_evaluasi);
                    $nama_evaluasi    = trim($_POST['nama_evaluasi']);
                    $nama_evaluasi    = validTeks($nama_evaluasi);
                    $indek            = validTeks(trim($_POST['indek']));
                    if ((!empty($kode_evaluasi))&&(!empty($nama_evaluasi))&&(!empty($indek))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" evaluasi_kinerja "," '$kode_evaluasi','$nama_evaluasi','$indek' ", " evaluasi kinerja" );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputEvaluasiKinerja&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" evaluasi_kinerja "," nama_evaluasi='$nama_evaluasi',indek='$indek' WHERE kode_evaluasi='$kode_evaluasi' ", " evaluasi kinerja");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListEvaluasiKinerja'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($kode_evaluasi))||(empty($nama_evaluasi))||(empty($indek))){
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>

