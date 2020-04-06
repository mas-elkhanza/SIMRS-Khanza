
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
                echo "";
                $action             =isset($_GET['action'])?$_GET['action']:NULL;
		$stts               =str_replace("_"," ",isset($_GET['stts']))?str_replace("_"," ",$_GET['stts']):NULL;
                $biaya              =isset($_GET['biaya'])?$_GET['biaya']:NULL;
                echo "<input type=hidden name=stts  value=$stts><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Stts Keanggotaan</td><td width="">:</td>
                    <td width="67%"><input name="stts" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $stts;?>" size="10" maxlength="5">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Biaya BPJS</td><td width="">:</td>
                    <td width="67%">Rp <input name="biaya" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" value="<?php echo $biaya;?>" size="20" maxlength="15">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $stts                 = validTeks(trim($_POST['stts']));
                    $biaya                = validangka(trim($_POST['biaya']));
                    if ((!empty($stts))&&(!empty($biaya))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" bpjs "," '$stts','$biaya'", " Status Keanggotaan BPJS " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=DetailBpjs&action=TAMBAH&stts='$stts'>";
                                break;
							case "UBAH":
                                Ubah(" bpjs ","biaya='$biaya' WHERE stts='$stts'  ", " Status Keanggotaan BPJS   ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=DetailBpjs&action=TAMBAH&stts='$stts'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($stts))||(empty($biaya))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 69%; overflow: auto;">
            <?php
                $_sql = "SELECT stts,biaya from bpjs ORDER BY stts ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='12%'><div align='center'>Proses</div></td>
                                <td width='33%'><div align='center'>Status Keanggotaan</div></td>
                                <td width='55%'><div align='center'>Biaya BPJS</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                      echo "<tr class='isi'>
                                <td>
                                    <center>
				    <a href=?act=DetailBpjs&action=UBAH&stts=".str_replace(" ","_",$baris[0])."&biaya=".$baris[1].">[edit]</a>";?>
                                    <a href="?act=DetailBpjs&action=HAPUS&stts=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[0]</td>
                                <td>".formatDuit($baris[1])."</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "Data Status Keanggotaan BPJS masih kosong !";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" bpjs "," stts ='".$stts."' ","?act=DetailBpjs&action=TAMBAH&stts=$stts");
            }

        if(mysqli_num_rows($hasil)!=0) {
                $hasil1=bukaquery("SELECT stts,biaya from bpjs ORDER BY stts ");
                $jumladiv=mysqli_num_rows($hasil1);
                $i=$jumladiv/19;
                $i=ceil($i);
                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        }
        ?>
    </div>

</div>
