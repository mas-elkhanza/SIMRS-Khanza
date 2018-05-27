

<div id="post">
    <div align="center" class="link">
        <a href=?act=ListKeanggotaan>| List Keanggotaan |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>
    <div class="entry">
        <form name="frm_lokasi" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $id          =$_GET['id'];
                $action      =$_GET['action']; 
                $_sql = "SELECT id,koperasi,jamsostek FROM keanggotaan WHERE id='$id'";
                $hasil=bukaquery($_sql);
                if(mysqli_num_rows($hasil)!=0) {
                    $action = "UBAH";
                }else if(mysqli_num_rows($hasil)==0) {
                    $action = "TAMBAH";
                }
                
                $baris     = mysqli_fetch_row($hasil);
                $koperasi  = $baris[1];
                $jamsostek = $baris[2];
                
                $_sql2 = "SELECT nik,nama FROM pegawai where id='$id'";
                $hasil2=bukaquery($_sql2);
                $baris2 = mysqli_fetch_row($hasil2);                
                echo"<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >NIP</td><td width="">:</td>
                    <td width="67%"><?php echo $baris2[0];?></td>
                </tr>
		<tr class="head">
                    <td width="31%">Nama</td><td width="">:</td>
                    <td width="67%"><?php echo $baris2[1];?></td>
                </tr>
                <tr class="head">
                    <td width="25%" >Anggota Koperasi</td><td width="">:</td>
                    <td width="75%">
                        <select name="koperasi" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT stts,wajib FROM koperasi  ORDER BY stts";
                                $hasil=bukaquery($_sql);
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi1' value='$baris[0]'>$baris[0]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Anggota Jamsostek</td><td width="">:</td>
                    <td width="75%">
                        <select name="jamsostek" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT stts,biaya FROM jamsostek  ORDER BY stts";
                                $hasil=bukaquery($_sql);
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi2' value='$baris[0]'>$baris[0]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=$_POST['BtnSimpan'];
                if (isset($BtnSimpan)) {
                    $id        = trim($_POST['id']);
                    $koperasi  = trim($_POST['koperasi']);
                    $jamsostek = trim($_POST['jamsostek']);
                    if ((!empty($id))&&(!empty($koperasi))&&(!empty($jamsostek))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" keanggotaan "," '$id','$koperasi','$jamsostek' ", " Keanggotaan " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=ListKeanggotaan'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" keanggotaan "," koperasi='$koperasi',jamsostek='$jamsostek' WHERE id='".$_GET['id']."' ", " Keanggotaan ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListKeanggotaan'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($id))||(empty($koperasi))||(empty($jamsostek))){
                        echo 'Semua field harus isi..!!';
                        echo $action;
                    }
                }
            ?>
        </form>
    </div>
</div>
