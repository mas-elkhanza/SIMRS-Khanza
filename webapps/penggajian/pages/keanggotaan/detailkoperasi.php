<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div id="post">
    <div align="center" class="link">
        <a href=?act=DetailBpjs&action=TAMBAH>| Stts BPJS |</a>
        <a href=?act=DetailJamsostek&action=TAMBAH>| Stts Jamsostek |</a>
        <a href=?act=DetailKoperasi&action=TAMBAH>| Stts Koperasi |</a>
        <a href=?act=ListKeanggotaan>| List Keanggotaan |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                $action        = isset($_GET['action'])?$_GET['action']:NULL;
                $stts          = validTeks(str_replace("_"," ",isset($_GET['stts']))?str_replace("_"," ",$_GET['stts']):NULL);
                $wajib         = validangka(isset($_GET['wajib'])?$_GET['wajib']:NULL);
                echo "<input type=hidden name=stts  value=$stts><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Stts Keanggotaan</td><td width="">:</td>
                    <td width="67%"><input name="stts" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $stts;?>" size="10" maxlength="5" pattern="[a-zA-Z0-9, ./@_]{1,5}" title=" a-zA-Z0-9, ./@_ (Maksimal 5 karakter)" autocomplete="off" autofocus>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Simpanan Wajib</td><td width="">:</td>
                    <td width="67%">Rp <input name="wajib" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" value="<?php echo $wajib;?>" size="20" maxlength="15" pattern="[0-9-]{1,15}" title=" 0-9- (Maksimal 15 karakter)" autocomplete="off">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $stts    = validTeks(trim($_POST['stts']));
                    $wajib   = validangka(trim($_POST['wajib']));
                    if ((isset($stts))&&(isset($wajib))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" koperasi "," '$stts','$wajib'", " Status Keanggotaan Koperasi " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=DetailKoperasi&action=TAMBAH&stts='$stts'>";
                                break;
			    case "UBAH":
                                Ubah(" koperasi ","wajib='$wajib' WHERE stts='$stts'", " Status Keanggotaan Koperasi ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=DetailKoperasi&action=TAMBAH&stts='$stts'></head><body></body></html>";
                                break;
                        }
                    }else{
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 69%; overflow: auto;">
            <?php
                $_sql   = "SELECT koperasi.stts,koperasi.wajib from koperasi ORDER BY koperasi.stts ASC ";
                $hasil  = bukaquery($_sql);
                $jumlah = mysqli_num_rows($hasil);
                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='12%'><div align='center'>Proses</div></td>
                                <td width='33%'><div align='center'>Status Keanggotaan</div></td>
                                <td width='55%'><div align='center'>Besar Simpanan Wajib</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                      echo "<tr class='isi'>
                                <td>
                                    <center>
				    <a href=?act=DetailKoperasi&action=UBAH&stts=".str_replace(" ","_",$baris[0])."&wajib=".$baris[1].">[edit]</a>";?>
                                    <a href="?act=DetailKoperasi&action=HAPUS&stts=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[0]</td>
                                <td>".formatDuit($baris[1])."</td>
                           </tr>";
                    }
                    echo "</table>";
                } else {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='12%'><div align='center'>Proses</div></td>
                                <td width='33%'><div align='center'>Status Keanggotaan</div></td>
                                <td width='55%'><div align='center'>Besar Simpanan Wajib</div></td>
                            </tr>
                          </table>";
                }
            ?>
            </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" koperasi "," stts ='".$stts."' ","?act=DetailKoperasi&action=TAMBAH&stts=$stts");
            }

            if($jumlah!=0) {
                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head'>
                            <td><div align='left'>Data : $jumlah</div></td>
                        </tr>
                     </table>");
            }
        ?>
    </div>
</div>
