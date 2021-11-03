
<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baristahun   = mysqli_fetch_row($hasil);
   $tahun        = empty($baristahun[0])?date("Y"):$baristahun[0];
   $bulan        = empty($baristahun[1])?date("m"):$baristahun[1];

?>
<div id="post">
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =isset($_GET['action'])?$_GET['action']:NULL;
                $id                 =isset($_GET['id'])?$_GET['id']:NULL;
                $thn                =$tahun;
                $bln                =$bulan;
                $bsr_jasa           =isset($_GET['bsr_jasa'])?$_GET['bsr_jasa']:NULL;
                $ktg                =isset($_GET['ktg'])?$_GET['ktg']:NULL;
                echo "<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
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
                    
                    if(empty($prev)){
                        $prev=$next;
                    }
                    
                    if(empty($next)){
                        $next=$prev;
                    }

                    echo "<div align='center' class='link'>
                          <a href=?act=InputJasaLain&action=TAMBAH&id=$prev><<--</a>
                          <a href=?act=ListLampiran&action=LIHAT>| List Lampiran |</a>
                          <a href=?act=HomeAdmin>| Menu Utama |</a>
                          <a href=?act=InputJasaLain&action=TAMBAH&id=$next>-->></a>
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
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id                 = trim($_POST['id']);
                    $thn                = $tahun;
                    $bln                = $bulan;
                    $bsr_jasa           = validangka(trim($_POST['bsr_jasa']));
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
            <div style="width: 100%; height: 61%; overflow: auto;">
            <?php
                $_sql = "SELECT thn, bln, id, bsr_jasa, ktg
                        from jasa_lain  where id='$id'
			and thn='".$tahun."' and bln='".$bulan."' ORDER BY bsr_jasa ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                $ttllembur=0;
                $ttlhr=0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='10%'><div align='center'>Proses</div></td>
                                <td width='30%'><div align='center'>Besar Jasa</div></td>
                                <td width='60%'><div align='center'>Keterangan</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td width='70'>
                                    <center>"; ?>
                                    <a href="?act=InputJasaLain&action=HAPUS&thn=<?php print $baris[0] ?>&bln=<?php print $baris[1] ?>&id=<?php print $baris[2] ?>&bsr_jasa=<?php print $baris[3] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>".formatDuit($baris[3])."</td>
                                <td>$baris[4]</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "Data jasa lain masih kosong !";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" jasa_lain "," id ='".$_GET['id']."' and thn ='".$_GET['thn']."'
                       and bln ='".$_GET['bln']."' and bsr_jasa ='".$_GET['bsr_jasa']."'","?act=InputJasaLain&action=TAMBAH&id=$id");
            }

                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        ?>
    </div>

</div>
