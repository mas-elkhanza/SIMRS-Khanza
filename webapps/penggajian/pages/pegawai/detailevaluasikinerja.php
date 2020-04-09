
<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun        = $baris[0];
   $bulan        = $baris[1];
?>
<div id="post">
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =isset($_GET['action'])?$_GET['action']:NULL;
                $id                 =isset($_GET['id'])?$_GET['id']:NULL;
                $tahun              =$tahun;
                $bulan              =$bulan;
                $kode_evaluasi      =isset($_GET['kode_evaluasi'])?$_GET['kode_evaluasi']:NULL;
                $keterangan         =isset($_GET['keterangan'])?$_GET['keterangan']:NULL;
                echo "<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
		$_sql  = "SELECT nik,nama FROM pegawai where id='$id'";
                $hasil = bukaquery($_sql);
                $baris = mysqli_fetch_row($hasil);

                $_sqlnext         	= "SELECT id FROM pegawai WHERE id>'$id' order by id asc limit 1";
                $hasilnext        	= bukaquery($_sqlnext);
                $barisnext        	= mysqli_fetch_row($hasilnext);
                $next                    = $barisnext[0];

                $_sqlprev         	= "SELECT id FROM pegawai WHERE id<'$id' order by id desc limit 1";
                $hasilprev        	= bukaquery($_sqlprev);
                $barisprev        	= mysqli_fetch_row($hasilprev);
                $prev                    = $barisprev[0];

                if(empty($prev)){
                    $prev=$next;
                }

                if(empty($next)){
                    $next=$prev;
                }

                echo "<div align='center' class='link'>
                      <a href=?act=DetailEvaluasiKinerja&action=TAMBAH&id=$prev><<--</a>
                      <a href=?act=ListIndexPegawai>| Index Pegawai |</a>
                      <a href=?act=DetailEvaluasiKinerja&action=TAMBAH&id=$next>-->></a>
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
                    <td width="25%" >Evaluasi Kinerja</td><td width="">:</td>
                    <td width="75%">
                        <select name="kode_evaluasi" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT kode_evaluasi,nama_evaluasi FROM evaluasi_kinerja ORDER BY nama_evaluasi";
                                $hasil=bukaquery($_sql);

                                if($action == "UBAH"){
                                    $_sql2 = "SELECT kode_evaluasi,nama_evaluasi FROM evaluasi_kinerja where kode_evaluasi='$kode_evaluasi'";
                                    $hasil2=bukaquery($_sql2);
                                    while($baris2 = mysqli_fetch_array($hasil2)) {
                                        echo "<option id='TxtIsi1' value='$baris2[0]'>$baris2[0]  $baris2[1]</option>";
                                    }
                                }

                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi1' value='$baris[0]'>$baris[0] $baris[1]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Keterangan</td><td width="">:</td>
                    <td width="67%"><input name="keterangan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $keterangan;?>" size="50" maxlength="150">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id                 =trim($_POST['id']);
                    $tahun              =$tahun;
                    $bulan              =$bulan;
                    $kode_evaluasi      =trim($_POST['kode_evaluasi']);
                    $keterangan         = validTeks(trim($_POST['keterangan']));
                    if ((!empty($id))&&(!empty($kode_evaluasi))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" evaluasi_kinerja_pegawai "," '$id','$kode_evaluasi','$tahun','$bulan','$keterangan'", " Evaluasi Kinerja " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=DetailEvaluasiKinerja&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else if ((empty($id))||(empty($kode_evaluasi))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 61%; overflow: auto;">
            <?php
                $_sql       = "Select evaluasi_kinerja_pegawai.tahun,evaluasi_kinerja_pegawai.bulan,evaluasi_kinerja_pegawai.id,evaluasi_kinerja_pegawai.kode_evaluasi, 
                                evaluasi_kinerja.nama_evaluasi,evaluasi_kinerja_pegawai.keterangan from evaluasi_kinerja_pegawai inner join evaluasi_kinerja on
                                evaluasi_kinerja_pegawai.kode_evaluasi=evaluasi_kinerja.kode_evaluasi where evaluasi_kinerja_pegawai.id='$id' order by 
                                evaluasi_kinerja_pegawai.tahun,evaluasi_kinerja_pegawai.bulan ASC ";
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
                                <td width='50%' align='center'>Hasil Evaluasi</td>
                                <td width='35%' align='center'>Keterangan</td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td>
                                    <center>"; ?>
                                    <a href="?act=DetailEvaluasiKinerja&action=HAPUS&id=<?php print $baris["id"] ?>&kode_evaluasi=<?php print $baris["kode_evaluasi"] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>".$baris["tahun"]."</td>
                                <td>".$baris["bulan"]."</td>
                                <td>".$baris["nama_evaluasi"]."</td>
                                <td>".$baris["keterangan"]."</td>
                           </tr>";
                    }
                echo "</table>";
            } else {echo "Data evaluasi kinerja masih kosong !";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" evaluasi_kinerja_pegawai "," id ='".$_GET['id']."' and tahun ='".$tahun."' and bulan ='".$bulan."' and kode_evaluasi ='".$_GET['kode_evaluasi']."'","?act=DetailEvaluasiKinerja&action=TAMBAH&id=$id");
            }

            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        ?>
    </div>
</div>
