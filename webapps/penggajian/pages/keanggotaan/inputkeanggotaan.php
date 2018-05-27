

<div id="post">
    <div class="entry">
        <form name="frm_lokasi" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $id          =isset($_GET['id'])?$_GET['id']:NULL;
                $action      =isset($_GET['action'])?$_GET['action']:NULL; 
                $_sql = "SELECT id,koperasi,jamsostek,bpjs FROM keanggotaan WHERE id='$id'";
                $hasil=bukaquery($_sql);
                if(mysqli_num_rows($hasil)!=0) {
                    $action = "UBAH";
                }else if(mysqli_num_rows($hasil)==0) {
                    $action = "TAMBAH";
                }
                
                $baris     = mysqli_fetch_row($hasil);
                $koperasi  = $baris[1];
                $jamsostek = $baris[2];
                $bpjs      = $baris[3];
                
                $_sql2 = "SELECT nik,nama FROM pegawai where id='$id'";
                $hasil2=bukaquery($_sql2);
                $baris2 = mysqli_fetch_row($hasil2);                
                echo"<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";

                    $_sqlnext         	= "SELECT id FROM pegawai WHERE id>'$id' order by id asc limit 1";
                    $hasilnext        	= bukaquery($_sqlnext);
                    $barisnext        	= mysqli_fetch_row($hasilnext);
                    $next               = $barisnext[0];

                    $_sqlprev         	= "SELECT id FROM pegawai WHERE id<'$id' order by id desc limit 1";
                    $hasilprev        	= bukaquery($_sqlprev);
                    $barisprev        	= mysqli_fetch_row($hasilprev);
                    $prev               = $barisprev[0];
                    
                    if(empty($prev)){
                        $prev=$next;
                    }
                    
                    if(empty($next)){
                        $next=$prev;
                    }

                    echo "<div align='center' class='link'>
                          <a href=?act=InputKeanggotaan&action=$action&id=$prev><<--</a>
                          <a href=?act=ListKeanggotaan>| List Keanggotaan |</a>
                          <a href=?act=HomeAdmin>| Menu Utama |</a>
                          <a href=?act=InputKeanggotaan&action=$action&id=$next>-->></a>
                          </div>";
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
                                echo "<option id='TxtIsi1' value='$koperasi'>$koperasi</option>";
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
                                echo "<option id='TxtIsi2' value='$jamsostek'>$jamsostek</option>";
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi2' value='$baris[0]'>$baris[0]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Anggota BPJS</td><td width="">:</td>
                    <td width="75%">
                        <select name="bpjs" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT stts,biaya FROM bpjs ORDER BY stts";
                                $hasil=bukaquery($_sql);
                                echo "<option id='TxtIsi3' value='$bpjs'>$bpjs</option>";
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi3' value='$baris[0]'>$baris[0]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id        = trim($_POST['id']);
                    $koperasi  = trim($_POST['koperasi']);
                    $jamsostek = trim($_POST['jamsostek']);
                    $bpjs      = trim($_POST['bpjs']);
                    if ((!empty($id))&&(!empty($koperasi))&&(!empty($jamsostek))&&(!empty($bpjs))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" keanggotaan "," '$id','$koperasi','$jamsostek','$bpjs' ", " Keanggotaan " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputKeanggotaan&id=$id'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" keanggotaan "," koperasi='$koperasi',jamsostek='$jamsostek',bpjs='$bpjs' WHERE id='".$_GET['id']."' ", " Keanggotaan ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=InputKeanggotaan&id=$id'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($id))||(empty($koperasi))||(empty($jamsostek))||(empty($bpjs))){
                        echo 'Semua field harus isi..!!';
                        echo $action;
                    }
                }
            ?>
        </form>
    </div>
</div>
