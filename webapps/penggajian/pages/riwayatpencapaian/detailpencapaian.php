<div id="post">
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             = isset($_GET['action'])?$_GET['action']:NULL;
                $id                 = isset($_GET['id'])?$_GET['id']:NULL;
                $kode_pencapaian    = isset($_GET['kode_pencapaian'])?$_GET['kode_pencapaian']:NULL;
                $keterangan         = isset($_GET['keterangan'])?$_GET['keterangan']:NULL;
                echo "<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
	            $_sql               = "SELECT nik,nama FROM pegawai where id='$id'";
                $hasil              = bukaquery($_sql);
                $baris              = mysqli_fetch_row($hasil);   

                $_sqlnext         	= "SELECT id FROM pegawai WHERE id>'$id' order by id asc limit 1";
                $hasilnext        	= bukaquery($_sqlnext);
                $barisnext        	= mysqli_fetch_row($hasilnext);
                @$next              = $barisnext[0];

                $_sqlprev         	= "SELECT id FROM pegawai WHERE id<'$id' order by id desc limit 1";
                $hasilprev        	= bukaquery($_sqlprev);
                $barisprev        	= mysqli_fetch_row($hasilprev);
                @$prev              = $barisprev[0];

                if(empty($next)){
                    $next=$prev;
                }

                if(empty($prev)){
                    $prev=$next;
                }

                echo "<div align='center' class='link'>
                      <a href=?act=InputRiwayatPencapaian&action=TAMBAH&id=$prev><<--</a>
                      <a href=?act=ListCariRiwayatPencapaian&action=LIHAT>| List Riwayat Pencapaian |</a>
                      <a href=?act=HomeAdmin>| Menu Utama |</a>
                      <a href=?act=InputRiwayatPencapaian&action=TAMBAH&id=$next>-->></a>
                      </div>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >NIP</td><td width="">:</td>
                    <td width="67%"><?php echo $baris[0];?></td>
                </tr>
				<tr class="head">
                    <td width="31%">Nama</td><td width="">:</td>
                    <td width="67%"><?php echo $baris[1];?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Tahun Pencapaian</td><td width="">:</td>
                    <td width="67%">
                        <select name="tahun" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1" autofocus>
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                loadThn4();
                            ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Bulan Pencapaian</td><td width="">:</td>
					<td width="67%">
                       <select name="bulan" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                loadBln2();
                            ?>
                        </select>
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Pencapaian Kinerja</td><td width="">:</td>
                    <td width="75%">
                        <select name="kode_pencapaian" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3" autofocus>
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql  = "SELECT kode_pencapaian,nama_pencapaian FROM pencapaian_kinerja ORDER BY nama_pencapaian";
                                $hasil = bukaquery($_sql);
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi3' value='$baris[0]'>$baris[0] $baris[1]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Keterangan</td><td width="">:</td>
                    <td width="67%"><input name="keterangan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" type=text id="TxtIsi4" class="inputbox" value="<?php echo $keterangan;?>" size="50" maxlength="150">
                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id                 = trim($_POST['id']);
                    $tahun              = trim($_POST['tahun']);
                    $bulan              = trim($_POST['bulan']);
                    $kode_pencapaian    = trim($_POST['kode_pencapaian']);
                    $keterangan         = validTeks(trim($_POST['keterangan']));
                    if ((isset($id))&&(isset($kode_pencapaian))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" pencapaian_kinerja_pegawai "," '$id','$kode_pencapaian','$tahun','$bulan','$keterangan'", " Evaluasi Pencapaian " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputRiwayatPencapaian&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else{
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 61%; overflow: auto;">
            <?php
                $_sql       = "Select pencapaian_kinerja_pegawai.tahun,pencapaian_kinerja_pegawai.bulan,pencapaian_kinerja_pegawai.id,pencapaian_kinerja_pegawai.kode_pencapaian, 
                                pencapaian_kinerja.nama_pencapaian,pencapaian_kinerja_pegawai.keterangan from pencapaian_kinerja_pegawai inner join pencapaian_kinerja on
                                pencapaian_kinerja_pegawai.kode_pencapaian=pencapaian_kinerja.kode_pencapaian where pencapaian_kinerja_pegawai.id='$id' order by 
                                pencapaian_kinerja_pegawai.tahun,pencapaian_kinerja_pegawai.bulan ASC ";
                $hasil      = bukaquery($_sql);
                $jumlah     = mysqli_num_rows($hasil);
                $ttllembur  = 0;
                $ttlhr      = 0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%' align='center'>Proses</td>
                                <td width='5%' align='center'>Tahun</td>
                                <td width='5%' align='center'>Bulan</td>
                                <td width='50%' align='center'>Hasil Pencapaian</td>
                                <td width='35%' align='center'>Keterangan</td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td>
                                    <center>"; ?>
                                    <a href="?act=InputRiwayatPencapaian&action=HAPUS&id=<?php print $baris["id"] ?>&kode_pencapaian=<?php print $baris["kode_pencapaian"] ?>&tahun=<?php print $baris["tahun"] ?>&bulan=<?php print $baris["bulan"] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>".$baris["tahun"]."</td>
                                <td>".$baris["bulan"]."</td>
                                <td>".$baris["nama_pencapaian"]."</td>
                                <td>".$baris["keterangan"]."</td>
                           </tr>";
                    }
                echo "</table>";
            } else {
                echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%' align='center'>Proses</td>
                                <td width='5%' align='center'>Tahun</td>
                                <td width='5%' align='center'>Bulan</td>
                                <td width='50%' align='center'>Hasil Pencapaian</td>
                                <td width='35%' align='center'>Keterangan</td>
                            </tr>
                        </table>";
            }
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" pencapaian_kinerja_pegawai "," id ='".$_GET['id']."' and tahun ='".$_GET['tahun']."' and bulan ='".$_GET['bulan']."' and kode_pencapaian ='".$_GET['kode_pencapaian']."'","?act=InputRiwayatPencapaian&action=TAMBAH&id=$id");
            }

                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        ?>
    </div>

</div>
