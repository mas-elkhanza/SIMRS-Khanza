
<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun        = empty($baris[0])?date("Y"):$baris[0];
   $bulan        = empty($baris[1])?date("m"):$baris[1];
   $bln_leng     = strlen(empty($baris[1])?date("m"):$baris[1]);
   if ($bln_leng==1){
    	$bulan = "0".$bulan;
   }else{
		$bulan = $bulan;
   }
?>
<div id="post">
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =isset($_GET['action'])?$_GET['action']:NULL;
                $id                 =isset($_GET['id'])?$_GET['id']:NULL;
                $tglpres            =isset($_GET['TglPres'])?$_GET['TglPres']:NULL;
                $tgl                =$tahun."-".$bulan."-".$tglpres;
                $jns                =isset($_GET['jns'])?$_GET['jns']:NULL;
                $lembur             =isset($_GET['lembur'])?$_GET['lembur']:NULL;
                echo "<input type=hidden name=id  value=$id><input type=hidden name=tgl value=$tgl><input type=hidden name=action value=$action>";
		        $_sql       = "SELECT nik,nama FROM pegawai where id='$id'";
                $hasil      = bukaquery($_sql);
                $baris      = mysqli_fetch_row($hasil);

                    $_sqlnext         	= "SELECT id FROM pegawai WHERE id>'$id' order by id asc limit 1";
                    $hasilnext        	= bukaquery($_sqlnext);
                    $barisnext        	= mysqli_fetch_row($hasilnext);
                    @$next               = $barisnext[0];

                    $_sqlprev         	= "SELECT id FROM pegawai WHERE id<'$id' order by id desc limit 1";
                    $hasilprev        	= bukaquery($_sqlprev);
                    $barisprev        	= mysqli_fetch_row($hasilprev);
                    @$prev               = $barisprev[0];
                    if(empty($next)){
                        $next=$prev;
                    }
                    
                    if(empty($prev)){
                        $prev=$next;
                    }

                    echo "<div align='center' class='link'>
                          <a href=?act=DetailPresensi&action=TAMBAH&id=$prev><<--</a>
                          <a href=?act=ListPresensi>| List Lembur |</a>
                          <a href=?act=HomeAdmin>| Menu Utama |</a>
                          <a href=?act=DetailPresensi&action=TAMBAH&id=$next>-->></a>
                          </div>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >NIP</td><td width="">:</td>
                    <td width="67%">
                     <?php echo $baris[0];?>
                    </td>
                </tr>
		        <tr class="head">
                    <td width="31%">Nama</td><td width="">:</td>
                    <td width="67%"><?php echo $baris[1];?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Tanggal</td><td width="">:</td>
                    <td width="67%">
                        <select name="TglPres" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1" autofocus>
                             <?php
                                loadTgl2();
                             ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Jenis Lembur</td><td width="">:</td>
                    <td width="75%">
                        <select name="jns" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                            <!-- <option id='TxtIsi2' value=' '>- Jenis Kelamin -</option> -->                            
                            <option id='TxtIsi2' value='HB'>HB</option>
                            <option id='TxtIsi2' value='HR'>HR</option>
                        </select>
                        <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Lembur</td><td width="">:</td>
                    <td width="67%"><input name="lembur" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo isset($lembur)?$lembur:NULL;?>" size="10" maxlength="4" /> Jam, jk HR isi dengan 1
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id                 = trim($_POST['id']);
                    $tgl                = $tahun."-".$bulan."-".trim($_POST['TglPres']);
                    $lembur             = validangka(trim($_POST['lembur']));
                    $jns                = trim($_POST['jns']);
                    if ((isset($id))&&(isset($tgl))&&(isset($jns))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" presensi "," '$tgl','$id','$jns','$lembur'", " lembur " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=DetailPresensi&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else{
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 53%; overflow: auto;">
            <?php
                $_sql   = "SELECT tgl,id,jns,lembur
                        from presensi where id='$id'
			            and tgl like '%".$tahun."-".$bulan."%' ORDER BY tgl ASC ";
                $hasil  = bukaquery($_sql);
                $jumlah = mysqli_num_rows($hasil);
                $ttllembur = 0;
                $ttlhr = 0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='10%'><div align='center'>Proses</div></td>
                                <td width='20%'><div align='center'>Tgl.Lembur</div></td>
                                <td width='50%'><div align='center'>Jns Lembur</div></td>
                                <td width='20%'><div align='center'>Lembur</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        if($baris[2]=='HB'){
                            $ttllembur=$ttllembur+$baris[3];
                        }
                        if($baris[2]=='HR'){
                            $ttlhr=$ttlhr+$baris[3];
                        }
                        
                      echo "<tr class='isi'>
                                <td width='70'>
                                    <center>"; ?>
                                    <a href="?act=DetailPresensi&action=HAPUS&tgl=<?php print $baris[0] ?>&id=<?php print $baris[1] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[0]</td>
                                <td>$baris[2]</td>
                                <td>$baris[3] Jam</td>
                           </tr>";
                    }
                echo "</table>";

            } else {
                echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='10%'><div align='center'>Proses</div></td>
                                <td width='20%'><div align='center'>Tgl.Lembur</div></td>
                                <td width='50%'><div align='center'>Jns Lembur</div></td>
                                <td width='20%'><div align='center'>Lembur</div></td>
                            </tr>
                      </table>";
            }
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" presensi"," id ='".$_GET['id']."' and tgl ='".$_GET['tgl']."'","?act=DetailPresensi&action=TAMBAH&id=$id");
            }

            if(mysqli_num_rows($hasil)!=0) {
                $hasil1 = bukaquery("SELECT tgl,id,jns,lembur
                          from presensi where id='$id'
			              and tgl like '%".$tahun."-".$bulan."%' ORDER BY tgl ASC ");
                $jumladiv = mysqli_num_rows($hasil1);
                $i=$jumladiv/19;
                $i=ceil($i);
                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah, Ttl.Lembur HB : ".$ttllembur." , Ttl.Lembur HR : ".$ttlhr." <a target=_blank href=../penggajian/pages/presensi/LaporanPresensiDetail.php?&id=$id>| Laporan |</a></div></td>                        
                    </tr>     
                 </table>");
            }
        ?>
    </div>

</div>
