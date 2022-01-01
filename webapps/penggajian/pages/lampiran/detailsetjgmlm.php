
<div id="post">
    <div align="center" class="link">
        <a href=?act=ListLampiran&action=LIHAT>| List Lampiran |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action   =isset($_GET['action'])?$_GET['action']:NULL;
                $tnj      =isset($_GET['tnj'])?$_GET['tnj']:NULL;
                echo "<input type=hidden name=tnj  value=$tnj><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Besar tunjangan</td><td width="">:</td>
                    <td width="67%">Rp <input name="tnj" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $tnj;?>" size="20" maxlength="15" autofocus>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $tnj                = validangka(trim($_POST['tnj']));
                    if (isset($tnj)) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" set_jgmlm ","'$tnj'", " Set Jaga Malam " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputSetJagaMalam&action=TAMBAH&tnj='$tnj'>";
                                break;
                        }
                    }else{
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 100px; overflow: auto;">
            <?php
                $_sql = "SELECT tnj from set_jgmlm ORDER BY tnj ASC";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);

                    echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='10%'><div align='center'>Proses</div></td>
                                <td width='90%'><div align='center'>Besar Tunjangan</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                      echo "<tr class='isi'>
                                <td>
                                   <center>";?>
                                    <a href="?act=InputSetJagaMalam&action=HAPUS&tnj=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>".formatDuit($baris[0])."</td>
                           </tr>";
                    }
                echo "</table>";
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" set_jgmlm "," tnj ='".$tnj."' ","?act=InputSetJagaMalam&action=TAMBAH&tnj=".$tnj);
            }
        ?>
    </div>

</div>
