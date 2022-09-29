<div id="post">
     <div align="center" class="link">
        <a href=?act=DetailTindakan&action=TAMBAH>| Master Tindakan |</a>
        <a href=?act=ListTindakan>| Tindakan Paramedis |</a>
        <a href=?act=ListTindakanDokter>| Tindakan Dokter |</a>
        <a href=?act=ListTindakanSpesialis>| Tindakan Spesialis |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                $action   = isset($_GET['action'])?$_GET['action']:NULL;				
		$id       = validTeks(isset($_GET['id'])?$_GET['id']:NULL);
		$nama     = validTeks(str_replace("_"," ",isset($_GET['nama']))?str_replace("_"," ",$_GET['nama']):NULL);
		$jm       = validangka(isset($_GET['jm'])?$_GET['jm']:NULL);
                $jns      = validTeks(str_replace("_"," ",isset($_GET['jns']))?str_replace("_"," ",$_GET['jns']):NULL);
                echo "<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Nama Tindakan</td><td width="">:</td>
                    <td width="67%">
                        <input name="nama" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $nama;?>" size="50" maxlength="50" autofocus>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >JM Tindakan</td><td width="">:</td>
                    <td width="67%">
                        Rp <input name="jm" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" value="<?php echo $jm;?>" size="20" maxlength="15">
                        <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Jenis Tindakan</td><td width="">:</td>
                    <td width="75%">
                        <select name="jns" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                            <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi3' value='$jns'>$jns</option>";
                                }
                            ?>
                            <option id='TxtIsi3' value='Karyawan'>Karyawan</option>
                            <option id='TxtIsi3' value='dr Umum'>dr Umum</option>
                            <option id='TxtIsi3' value='dr Spesialis'>dr Spesialis</option>
                        </select>
                        <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
		    $id        = validTeks(trim($_POST['id']));
                    $nama      = validTeks(trim($_POST['nama']));
                    $jm        = validangka(trim($_POST['jm']));
                    $jns       = validTeks(trim($_POST['jns']));
                    if ((isset($nama))&&(isset($jm))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" master_tindakan ","'0','$nama','$jm','$jns'", " Master Tindakan " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=DetailTindakan&action=TAMBAH&nama='$nama'>";
                                break;
			    case "UBAH":
                                Ubah(" master_tindakan ","nama='$nama',jm='$jm',jns='$jns' WHERE id='$id'  ", " Master Tindakan  ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=DetailTindakan&action=TAMBAH&nama=$nama&jns=$jns'></head><body></body></html>";
                                break;
                        }
                    }else{
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 63%; overflow: auto;">
            <?php
                $_sql   = "SELECT master_tindakan.id,master_tindakan.nama,master_tindakan.jm,master_tindakan.jns from master_tindakan ORDER BY master_tindakan.nama,master_tindakan.jns ASC ";
                $hasil  = bukaquery($_sql);
                $jumlah = mysqli_num_rows($hasil);
                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='12%'><div align='center'>Proses</div></td>
                                <td width='38%'><div align='center'>Nama Tindakan</div></td>
                                <td width='25%'><div align='center'>JM Tindakan</div></td>
                                <td width='25%'><div align='center'>Jns.Tindakan</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                      echo "<tr class='isi'>
                                <td>
                                    <center>
				      <a href=?act=DetailTindakan&action=UBAH&id=".$baris[0]."&nama=".str_replace(" ","_",$baris[1])."&jm=".$baris[2]."&jns=".str_replace(" ","_",$baris[3]).">[edit]</a>
                                      <a href=?act=DetailTindakan&action=HAPUS&id=".$baris[0].">[hapus]</a>
                                    </center>
                                </td>
                                <td>$baris[1]</td>
                                <td>".formatDuit($baris[2])."</td>
                                <td>$baris[3]</td>
                           </tr>";
                    }
                    echo "</table>";
                } else {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='12%'><div align='center'>Proses</div></td>
                                <td width='38%'><div align='center'>Nama Tindakan</div></td>
                                <td width='25%'><div align='center'>JM Tindakan</div></td>
                                <td width='25%'><div align='center'>Jns.Tindakan</div></td>
                            </tr>
                          </table>";
                }
            ?>
            </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" master_tindakan "," id ='".validTeks($_GET['id'])."' ","?act=DetailTindakan&action=TAMBAH&nama=$nama");
            }
            
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        ?>
    </div>
</div>
