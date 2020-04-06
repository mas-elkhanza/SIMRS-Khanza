
<div id="post">
    <div align="center" class="link">
	<a href=?act=DetailTunjanganBulanan&action=TAMBAH>| Ms.Tunj Bulanan |</a>
        <a href=?act=DetailTunjanganHarian&action=TAMBAH>| Ms.Tunj Harian |</a>
        <a href=?act=DetailHarianBulanan&action=TAMBAH>| Harian-Bulanan |</a>
        <a href=?act=ListTunjangan>| List Penerima |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =isset($_GET['action'])?$_GET['action']:NULL;
		$id                 =isset($_GET['id'])?$_GET['id']:NULL;
		$nama               =str_replace("_"," ",isset($_GET['nama']))?str_replace("_"," ",$_GET['nama']):NULL;
                $tnj                =isset($_GET['tnj'])?$_GET['tnj']:NULL;
                echo "<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Nama Tunjangan</td><td width="">:</td>
                    <td width="67%"><input name="nama" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $nama;?>" size="50" maxlength="40">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Besar tunjangan</td><td width="">:</td>
                    <td width="67%">Rp <input name="tnj" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" value="<?php echo $tnj;?>" size="20" maxlength="15">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
		    $id                 =trim($_POST['id']);
                    $nama               = validTeks(trim($_POST['nama']));
                    $tnj                = validangka(trim($_POST['tnj']));
                    if ((!empty($nama))&&(!empty($tnj))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" master_tunjangan_harian ","'','$nama','$tnj'", " Master Tunjangan Harian " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=DetailTunjanganHarian&action=TAMBAH&nama='$nama'>";
                                break;
							case "UBAH":
                                Ubah(" master_tunjangan_harian ","tnj='$tnj',nama='$nama' WHERE id='$id'  ", " Master Tunjangan Harian   ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=DetailTunjanganHarian&action=TAMBAH&nama='$nama'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($nama))||(empty($tnj))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 69%; overflow: auto;">
            <?php
                $_sql = "SELECT id,nama,tnj from master_tunjangan_harian ORDER BY nama ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='12%'><div align='center'>Proses</div></td>
                                <td width='55%'><div align='center'>Nama Tunjangan</div></td>
                                <td width='33%'><div align='center'>Besar Tunjangan</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                      echo "<tr class='isi'>
                                <td>
                                    <center>
				    <a href=?act=DetailTunjanganHarian&action=UBAH&id=".$baris[0]."&nama=".str_replace(" ","_",$baris[1])."&tnj=".$baris[2].">[edit]</a>";?>
                                    <a href="?act=DetailTunjanganHarian&action=HAPUS&id=<?php print $baris[0] ?>&nama=<?php print str_replace(" ","_",$baris[1]) ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[1]</td>
                                <td>".formatDuit($baris[2])."</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "Data Master Tunjangan Harian !";}
        ?>
        </div>
        </form>
        <?php
            $aksi=isset($_GET['action'])?$_GET['action']:NULL;
            if ($aksi=="HAPUS") {
                Hapus(" master_tunjangan_harian "," id ='".$id."' ","?act=DetailTunjanganHarian&action=TAMBAH&nama=".$nama);
            }

            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        ?>
    </div>

</div>
