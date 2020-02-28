<div class="t">
<div class="b">
<div class="l">
<div class="r">
<div class="bl">
<div class="br">
<div class="tl">
<div class="tr">
<div class="y">
<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun        = $baris[0];
   $bulan        = $baris[1];
?>
<div id="post">
    <h1 class="title">::[ Input Detail Jumlah Pasien Tahun <?php echo$tahun ;?> Bulan <?php echo$bulan ;?> ]::</h1>
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =$_GET['action'];
                $id                 =$_GET['id'];
                $jml                =$_GET['jml'];
                echo "<input type=hidden name=id  value=$id><input type=hidden name=tgl value=$tgl><input type=hidden name=action value=$action>";
		$_sql = "SELECT nik,nama FROM pegawai where id='$id'";
                $hasil=bukaquery($_sql);
                $baris = mysqli_fetch_row($hasil);

                $_sqlnext         	= "SELECT id FROM pegawai WHERE id>'$id' order by id asc limit 1";
                    $hasilnext        	= bukaquery($_sqlnext);
                    $barisnext        	= mysqli_fetch_row($hasilnext);
                    $next               = $barisnext[0];

                    $_sqlprev         	= "SELECT id FROM pegawai WHERE id<'$id' order by id desc limit 1";
                    $hasilprev        	= bukaquery($_sqlprev);
                    $barisprev        	= mysqli_fetch_row($hasilprev);
                    $prev               = $barisprev[0];

                    echo "<div align='center' class='link'>
                          <a href=?act=InputPasien&action=TAMBAH&id=$prev><<--</a>
                          <a href=?act=ListLampiran&action=LIHAT>| List Lampiran |</a>
                          <a href=?act=InputPasien&action=TAMBAH&id=$next>-->></a>
                          </div>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >NIK</td><td width="">:</td>
                    <td width="67%"><?php echo $baris[0];?></td>
                </tr>
		<tr class="head">
                    <td width="31%">Nama</td><td width="">:</td>
                    <td width="67%"><?php echo $baris[1];?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Jml.Pasien</td><td width="">:</td>
                    <td width="67%"><input name="jml" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $jml;?>" size="10" maxlength="5">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=$_POST['BtnSimpan'];
                if (isset($BtnSimpan)) {
                    $id                 =trim($_POST['id']);
                    $jml                = validTeks(trim($_POST['jml']));
                    if ((!empty($id))&&(!empty($jml))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah("jumpasien "," '$tahun','$bulan','$id','$jml'", " Jumlah Pasien " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputPasien&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else if ((empty($id))||(empty($jml))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 598px; height: 400px; overflow: auto;">
            <?php
                $awal=$_GET['awal'];
                if (empty($awal)) $awal=0;
                $_sql = "SELECT `thn`, `bln`, `id`, `jml`
                        from  jumpasien  where id='$id'
			and thn='$tahun' and bln='$bulan' ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                $ttllembur=0;
                $ttlhr=0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='598px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='70px'><div align='center'><font size='2' face='Verdana'><strong>Proses</strong></font></div></td>
                                <td width='520px'><div align='center'><font size='2' face='Verdana'><strong>Jumlah Pasien</strong></font></div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td width='70'>
                                    <center>"; ?>
                                    <a href="?act=InputPasien&action=HAPUS&thn=<?php print $baris[0] ?>&bln=<?php print $baris[1] ?>&id=<?php print $baris[2] ?>" onClick="if (!confirm('Anda yakin menghapus data Jumlah Pasien <?php print $baris[3]?>?')) return false;">[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[3]</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "<b>Data Jumlah Pasien masih kosong !</b>";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus("  jumpasien "," id ='".$_GET['id']."' and thn ='".$_GET['thn']."' and bln ='".$_GET['bln']."'","?act=InputPasien&action=TAMBAH&id=$id");
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