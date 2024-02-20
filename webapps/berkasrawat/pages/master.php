<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div id="post">
    <div class="entry">        
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action  = isset($_GET['action'])?$_GET['action']:NULL;
                $kode    = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
                $kode    = json_decode(encrypt_decrypt($kode,"d"),true); 
                if (isset($kode["kode"])) {
                    $kode = validTeks4($kode["kode"],10);
                }else{
                    $kode = "";
                }
                
                $kode = validTeks4($kode,10);
                
                echo "<input type=hidden name=kode  value=$kode><input type=hidden name=action value=$action>";
                echo "<div align='center' class='link'>
                          <a href=?act=List>| List Berkas |</a>
                      </div>";
            ?>
            <div style="width: 100%; height: 13%; overflow: auto;">
            <table width="100%" align="center">
                <tr class="head">
                    <td width="25%" >Kode Berkas Digital</td><td width="">:</td>
                    <td width="74%"><input name="kode" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $kode;?>" size="30" maxlength="10" pattern="[A-Z0-9-]{1,10}" title=" A-Z0-9- (Maksimal 10 karakter)" autocomplete="off" required autofocus>
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Nama Berkas Digital</td><td width="">:</td>
                    <td width="74%"><input name="nama" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="" size="70" maxlength="100" pattern="[a-zA-Z 0-9-]{1,100}" title=" a-zA-Z 0-9- (Maksimal 100 karakter)" autocomplete="off" required>
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            </div>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;Simpan&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;Kosong&nbsp;&nbsp;"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $kode   = trim($_POST['kode']);
                    $nama   = trim($_POST['nama']);
                    $kode   = validTeks4($kode,10);
                    $nama   = validTeks4($nama,100);
                    if ((!empty($kode))&&(!empty($nama))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" master_berkas_digital "," '$kode','$nama'", " Master Berkas Digital " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=MasterBerkas&action=TAMBAH'>";
                                break;
                        }
                    }else if ((empty($kode))||(empty($nama))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 67%; overflow: auto;">
            <?php
                $_sql = "SELECT * from master_berkas_digital ORDER BY master_berkas_digital.kode ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                $ttllembur=0;
                $ttlhr=0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='18%'><div align='center'>Kode Berkas Digital</div></td>
                                <td width='76%'><div align='center'>Nama Berkas Digital</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td width='70'>
                                    <center>
                                    <a href=?act=MasterBerkas&action=HAPUS&iyem=".encrypt_decrypt("{\"kode\":\"".$baris[0]."\"}","e").">[hapus]</a>
                                    </center>
                                </td>
                                <td>$baris[0]</td>
                                <td>$baris[1]</td>
                            </tr>";
                    }
                echo "</table>";
            }else{
                echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='18%'><div align='center'>Kode Berkas Digital</div></td>
                                <td width='76%'><div align='center'>Nama Berkas Digital</div></td>
                            </tr>
                      </table>";
            } 
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" master_berkas_digital "," kode ='$kode' ","?act=MasterBerkas&action=TAMBAH");
            }
            
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        
        ?>
    </div>

</div>
