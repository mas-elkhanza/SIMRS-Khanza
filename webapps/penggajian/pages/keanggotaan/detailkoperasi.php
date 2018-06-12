
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
                $wajib              =isset($_GET['wajib'])?$_GET['wajib']:NULL;
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
                    <td width="31%" >Simpanan Wajib</td><td width="">:</td>
                    <td width="67%">Rp <input name="wajib" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" value="<?php echo $wajib;?>" size="20" maxlength="15">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $stts                 =trim($_POST['stts']);
                    $wajib                =trim($_POST['wajib']);
                    if ((!empty($stts))&&(!empty($wajib))) {
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
                    }else if ((empty($stts))||(empty($wajib))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 69%; overflow: auto;">
            <?php
                $_sql = "SELECT stts,wajib from koperasi ORDER BY stts ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);

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

            } else {echo "Data Status Keanggotaan Koperasi masih kosong !";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" koperasi "," stts ='".$stts."' ","?act=DetailKoperasi&action=TAMBAH&stts=$stts");
            }

        if(mysqli_num_rows($hasil)!=0) {
                $hasil1=bukaquery("SELECT stts,wajib from koperasi ORDER BY stts ASC ");
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
