<div class="t">
<div class="b">
<div class="l">
<div class="r">
<div class="bl">
<div class="br">
<div class="tl">
<div class="tr">
<div class="y">
<div id="post">
    <h1 class="title">::[ Set Tunjangan Hadir ]::</h1>
    <div align="center" class="link">
        <a href=?act=ListLampiran&action=LIHAT>| List Lampiran |</a>
    </div>   
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action   =$_GET['action'];
                $tnj      =$_GET['tnj'];
                echo "<input type=hidden name=tnj  value=$tnj><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Besar tunjangan</td><td width="">:</td>
                    <td width="67%">Rp <input name="tnj" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $tnj;?>" size="20" maxlength="15">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=$_POST['BtnSimpan'];
                if (isset($BtnSimpan)) {
                    $tnj                = validTeks(trim($_POST['tnj']));
                    if (!empty($tnj)) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" set_hadir  ","'$tnj'", " Set tunjangan hadir " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputSetTunjanganHadir&action=TAMBAH&tnj='$tnj'>";
                                break;
                        }
                    }else if (empty($tnj)){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 598px; height: 150px; overflow: auto;">
            <?php
                $_sql = "SELECT tnj from set_hadir  ORDER BY tnj ASC";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='598px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='100px'><div align='center'><font size='2' face='Verdana'><strong>Proses</strong></font></div></td>
                                <td width='250px'><div align='center'><font size='2' face='Verdana'><strong>Besar Tunjangan</strong></font></div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                      echo "<tr class='isi'>
                                <td>
                                   <center>";?>
                                    <a href="?act=InputSetTunjanganHadir&action=HAPUS&tnj=<?php print $baris[0] ?>" onClick="if (!confirm('Anda yakin menghapus set tunjangan hadir ?')) return false;">[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>".formatDuit($baris[0])."</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "<b>Data set tunjangan hadir masih kosong !</b>";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" set_hadir  "," tnj ='".$tnj."' ","?act=InputSetTunjanganHadir&action=TAMBAH&tnj=".$tnj);
            }
        ?>
    </div>

</div>

</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>