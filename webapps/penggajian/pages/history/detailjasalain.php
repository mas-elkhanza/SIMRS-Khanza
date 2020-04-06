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
   $tahun         = $baris[0];
   $bulan         =$baris[1];

?>
<div id="post">
    <h1 class="title">::[ Input Detail Jasa Lain Tahun <?php echo$tahun ;?> Bulan <?php echo$bulan ;?> ]::</h1>
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =$_GET['action'];
                $id                 =$_GET['id'];
                $thn                =$tahun;
                $bln                =$bulan;
                $bsr_jasa           =$_GET['bsr_jasa'];
                $ktg                =$_GET['ktg'];
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
                          <a href=?act=InputJasaLain&action=TAMBAH&id=$prev><<--</a>
                          <a href=?act=ListLampiran&action=LIHAT>| List Lampiran |</a>
                          <a href=?act=InputJasaLain&action=TAMBAH&id=$next>-->></a>
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
                    <td width="31%" >Besar Jasa</td><td width="">:</td>
                    <td width="67%">Rp.<input name="bsr_jasa" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $bsr_jasa;?>" size="30" maxlength="15">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Keterangan</td><td width="">:</td>
                    <td width="67%"><input name="ktg" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $ktg;?>" size="50" maxlength="40">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=$_POST['BtnSimpan'];
                if (isset($BtnSimpan)) {
                    $id                 =trim($_POST['id']);
                    $thn                =$tahun;
                    $bln                =$bulan;
                    $bsr_jasa           = validTeks(trim($_POST['bsr_jasa']));
                    $ktg                = validTeks(trim($_POST['ktg']));
                    if ((!empty($id))&&(!empty($bsr_jasa))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" jasa_lain "," '$thn','$bln','$id','$bsr_jasa','$ktg'", " Tambahan Jaga " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputJasaLain&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else if ((empty($id))||(empty($bsr_jasa))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 598px; height: 400px; overflow: auto;">
            <?php
                $awal=$_GET['awal'];
                if (empty($awal)) $awal=0;
                $_sql = "SELECT thn, bln, id, bsr_jasa, ktg
                        from jasa_lain  where id='$id'
			and thn='".$tahun."' and bln='".$bulan."' ORDER BY bsr_jasa ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                $ttllembur=0;
                $ttlhr=0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='598px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='70px'><div align='center'><font size='2' face='Verdana'><strong>Proses</strong></font></div></td>
                                <td width='120px'><div align='center'><font size='2' face='Verdana'><strong>Besar Jasa</strong></font></div></td>
                                <td width='220px'><div align='center'><font size='2' face='Verdana'><strong>Keterangan</strong></font></div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td width='70'>
                                    <center>"; ?>
                                    <a href="?act=InputJasaLain&action=HAPUS&thn=<?php print $baris[0] ?>&bln=<?php print $baris[1] ?>&id=<?php print $baris[2] ?>&bsr_jasa=<?php print $baris[3] ?>" onClick="if (!confirm('Anda yakin menghapus data ini ?')) return false;">[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>".formatDuit($baris[3])."</td>
                                <td>$baris[4]</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "<b>Data jasa lain masih kosong !</b>";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" jasa_lain "," id ='".$_GET['id']."' and thn ='".$_GET['thn']."'
                       and bln ='".$_GET['bln']."' and bsr_jasa ='".$_GET['bsr_jasa']."'","?act=InputJasaLain&action=TAMBAH&id=$id");
            }

        if(mysqli_num_rows($hasil)!=0) {
                $hasil1=bukaquery("SELECT thn, bln, id, bsr_jasa, ktg
                        from jasa_lain  where id='$id'
			and thn='".$tahun."' and bln='".$bulan."' ORDER BY bsr_jasa ASC ");
                $jumlah1=mysqli_num_rows($hasil1);
                $i=$jumlah1/19;
                $i=ceil($i);
                echo("Data : $jumlah ");
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