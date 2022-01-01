
<div id="post">
    <div align="center" class="link">
        <a href=?act=ListLampiran&action=LIHAT>| List Lampiran |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action     =isset($_GET['action'])?$_GET['action']:NULL;
                $tnj        =isset($_GET['tnj'])?$_GET['tnj']:NULL;
                $pendidikan =isset($_GET['pendidikan'])?$_GET['pendidikan']:NULL;
                echo "<input type=hidden name=pendidikan  value=$pendidikan><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Besar Tunjangan</td><td width="">:</td>
                    <td width="67%">Rp <input name="tnj" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $tnj;?>" size="20" maxlength="15" autofocus>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                 <tr class="head">
                    <td width="25%" >Pendidikan</td><td width="">:</td>
                    <td width="75%">
                        <select name="pendidikan" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT tingkat FROM pendidikan  ORDER BY tingkat";
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
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $tnj                = validangka(trim($_POST['tnj']));
                    $pendidikan         = validTeks(trim($_POST['pendidikan']));
                    if (isset($pendidikan)) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" set_jgtambah  ","'$tnj','$pendidikan'", " Set tambah jaga " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputSetTambahJaga&action=TAMBAH&pendidikan='$pendidikan'>";
                                break;
                        }
                    }else {
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 75%; overflow: auto;">
            <?php
                $_sql = "SELECT tnj,pendidikan from set_jgtambah  ORDER BY tnj ASC";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);

                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='10%'><div align='center'>Proses</div></td>
                                <td width='30%'><div align='center'>Besar Tunjangan</div></td>
                                <td width='60%'><div align='center'>Pendidikan</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                      echo "<tr class='isi'>
                                <td>
                                   <center>";?>
                                    <a href="?act=InputSetTambahJaga&action=HAPUS&pendidikan=<?php print $baris[1] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>".formatDuit($baris[0])."</td>
                                <td>$baris[1]</td>
                           </tr>";
                    }
                echo "</table>";

        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" set_jgtambah  "," pendidikan ='".$pendidikan."' ","?act=InputSetTambahJaga&action=TAMBAH&tnj=".$tnj);
            }
        ?>
    </div>

</div>
