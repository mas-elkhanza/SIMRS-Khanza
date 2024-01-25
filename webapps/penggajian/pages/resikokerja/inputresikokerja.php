<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>

<div id="post">
    <div align="center" class="link">
        <a href=?act=InputResikoKerja&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListResikoKerja>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                $action       = isset($_GET['action'])?$_GET['action']:NULL;
                $kode_resiko  = validTeks(str_replace("_"," ",isset($_GET['kode_resiko']))?str_replace("_"," ",$_GET['kode_resiko']):NULL);
                if($action == "TAMBAH"){
                    $kode_resiko    = validTeks(str_replace("_"," ",isset($_GET['kode_resiko']))?str_replace("_"," ",$_GET['kode_resiko']):NULL);
                    $nama_resiko    = "";
                    $indek          = "";
                }else if($action == "UBAH"){
                    $_sql           = "SELECT * FROM resiko_kerja WHERE resiko_kerja.kode_resiko='$kode_resiko'";
                    $hasil          = bukaquery($_sql);
                    $baris          = mysqli_fetch_row($hasil);
                    $kode_resiko    = $baris[0];
                    $nama_resiko    = $baris[1];
                    $indek          = $baris[2];
                }
                echo"<input type=hidden name=kode_resiko value=$kode_resiko><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Kode</td><td width="">:</td>
                    <td width="67%"><input name="kode_resiko" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $kode_resiko;?>" size="10" maxlength="3" pattern="[a-zA-Z0-9, ./@_]{1,3}" title=" a-zA-Z0-9, ./@_ (Maksimal 3 karakter)" autocomplete="off" autofocus/>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Resiko Kerja</td><td width="">:</td>
                    <td width="67%"><input name="nama_resiko" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $nama_resiko;?>" size="40" maxlength="100" pattern="[a-zA-Z0-9, ./@_()]{1,100}" title=" a-zA-Z0-9, ./@_() (Maksimal 100 karakter)" autocomplete="off"/>
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
                    $kode_resiko    = validTeks4(trim($_POST['kode_resiko']),3);
                    $nama_resiko    = validTeks6(trim($_POST['nama_resiko']),100);
                    $indek          = validangka(trim($_POST['indek']));
                    if ((isset($kode_resiko))&&(isset($nama_resiko))&&(isset($indek))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" resiko_kerja "," '$kode_resiko','$nama_resiko','$indek' ", " resiko kerja " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputResikoKerja&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" resiko_kerja "," nama_resiko='$nama_resiko',indek='$indek' WHERE kode_resiko='$kode_resiko' ", " resiko kerja ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListResikoKerja'></head><body></body></html>";
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

