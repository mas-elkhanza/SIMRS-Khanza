
<div id="post">
    <div align="center" class="link">
        <a href=?act=InputDansos&action=TAMBAH>| Set Dana Sosial |</a>
        <a href=?act=ListPotongan>| List Potongan |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =isset($_GET['action'])?$_GET['action']:NULL;
                $dana               =isset($_GET['dana'])?$_GET['dana']:NULL;
                $stts               =isset($_GET['stts'])?$_GET['stts']:NULL;
                echo "<input type=hidden name=stts  value=$stts><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Dana Sosial</td><td width="">:</td>
                    <td width="67%">Rp <input name="dana" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $dana;?>" size="20" maxlength="15">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $dana                =trim($_POST['dana']);
                    if (!empty($dana)) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" dansos "," '$dana '", " Set/Pengaturan dana sosial " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputDansos&action=TAMBAH&dana='$dana'>";
                                break;
                        }
                    }else if (empty($dana)){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 200px; overflow: auto;">
            <?php
                $_sql = "SELECT dana from dansos ORDER BY dana desc ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='12%'><div align='center'>Proses</div></td>
                                <td width='88%'><div align='center'>Besarnya Dana Sosial</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                      echo "<tr class='isi'>
                                <td>
                                    <center>"; ?>
                                    <a href="?act=InputDansos&action=HAPUS&dana=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>".formatDuit($baris[0])."</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "Set/pengaturan dana sosial !";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" dansos "," dana ='".$dana."' ","?act=InputDansos&action=TAMBAH&dana=$dana");
            }
        ?>
    </div>

</div>
