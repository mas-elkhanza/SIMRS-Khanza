

<div id="post">
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action                 =isset($_GET['action'])?$_GET['action']:NULL;
                $id                     =isset($_GET['id'])?$_GET['id']:NULL;
                $tanggal                =isset($_GET['tanggal'])?$_GET['tanggal']:NULL;
                $pinjaman               =isset($_GET['pinjaman'])?$_GET['pinjaman']:NULL;
                $banyak_angsur          =isset($_GET['banyak_angsur'])?$_GET['banyak_angsur']:NULL;
                $pokok                  =isset($_GET['pokok'])?$_GET['pokok']:NULL;
                $jasa                   =isset($_GET['jasa'])?$_GET['jasa']:NULL;
                $status                 =isset($_GET['status'])?$_GET['status']:NULL;
                
                
                echo "<input type=hidden name=id  value=$id>
                <input type=hidden name=action value=$action>";
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
                          <a href=?act=DetailPinjam&action=TAMBAH&id=$prev><<--</a>
                          <a href=?act=ListPinjam>| List Pinjam |</a>
                          <a href=?act=HomeAdmin>| Menu Utama |</a>
                          <a href=?act=DetailPinjam&action=TAMBAH&id=$next>-->></a>
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
                    <td width="31%" >Tanggal Pinjam</td><td width="">:</td>
                    <td width="67%">
                         <select name="Tgl" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi1' value=$Tgl>$Tgl</option>";
                                }
                                loadTgl();
                             ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
			<select name="Bln" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi1' value=$Bln>$Bln</option>";
                                }
                                loadBln();
                             ?>
                        </select>
			<select name="Thn" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi1' value=$Thn>$Thn</option>";
                                }

                                loadThn();
                             ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Angsuran</td><td width="">:</td>
                    <td width="67%"><input name="banyak_angsur" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $banyak_angsur;?>" size="10" maxlength="4" /> X
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Pinjaman</td><td width="">:</td>
                    <td width="67%"><input name="pinjaman" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo $pinjaman;?>" size="20" maxlength="15" />
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id                 = trim($_POST['id']);
                    $banyak_angsur      = validangka(trim($_POST['banyak_angsur']));
                    $pinjaman           = validangka(trim($_POST['pinjaman']));
                    $pokok              = $pinjaman/$banyak_angsur;
                    $jasa               = 0.01*$pinjaman;

                    $Tgl                = trim($_POST['Tgl']);
                    $Bln                = trim($_POST['Bln']);
                    $Thn                = trim($_POST['Thn']);

                    $tgl_pinjam         = $Thn.'-'.$Bln.'-'.$Tgl;
                    $status             ='Belum Lunas';
                    if ((!empty($id))&&(!empty($banyak_angsur))&&(!empty($pinjaman))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" peminjaman_koperasi "," '$id','$tgl_pinjam','$pinjaman','$banyak_angsur','$pokok','$jasa','$status'", " peminjaman " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=DetailPinjam&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else if ((empty($id))||(empty($banyak_angsur))||(empty($pinjaman))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 53%; overflow: auto;">
            <?php
                $_sql = "select id,tanggal,pinjaman,banyak_angsur,pokok, jasa, status from peminjaman_koperasi
                    where id='$id' order by tanggal ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                $ttllembur=0;
                $ttlhr=0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='12%'><div align='center'>Proses</div></td>
                                <td width='10%'><div align='center'>Tgl.Pinjam</div></td>
                                <td width='15%'><div align='center'>Pinjaman</div></td>
                                <td width='10%'><div align='center'>Jml.Angsur</div></td>
                                <td width='14%'><div align='center'>Pokok</div></td>
                                <td width='12%'><div align='center'>Jasa</div></td>
                                <td width='14%'><div align='center'>Angsuran</div></td>
                                <td width='13%'><div align='center'>Status</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                      echo "<tr class='isi' title='$baris[1], $baris[2], $baris[3], $baris[4], $baris[5], $baris[6]'>
                                <td>
                                    <center>
                                     <a href=?act=BayarPinjam&action=TAMBAH&id=".str_replace(" ","_",$baris[0])."&tanggal=".str_replace(" ","_",$baris[1]).">[Detail]</a>";?>
                                    <a href="?act=DetailPinjam&action=HAPUS&id=<?php print $baris[0] ?>&tanggal=<?php print $baris[1] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[1]</td>
                                <td>".formatDuit($baris[2])."</td>
                                <td>$baris[3]</td>
                                <td>".formatDuit($baris[4])."</td>
                                <td>".formatDuit($baris[5])."</td>
                                <td>".formatDuit($baris[4]+$baris[5])."</td>
                                <td>$baris[6]</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "Data Peminjaman masih kosong !";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {	
                $_sql = "select id, tanggal_pinjam, tanggal_angsur, pokok, jasa
                        from angsuran_koperasi where id='".$_GET['id']."' and tanggal_pinjam='".$_GET['tanggal']."'";
                $hasil=bukaquery($_sql);
                while($baris = mysqli_fetch_array($hasil)) {
                        EditData(" potongan "," angkop='0' WHERE id='".$baris["id"]."' and tahun=year('".$baris["tanggal_angsur"]."') and bulan=(MONTH('".$baris["tanggal_angsur"]."')-1) ");
                }
                HapusAll(" angsuran_koperasi where id ='".$_GET['id']."' and tanggal_pinjam ='".$_GET['tanggal']."'");
                Hapus(" peminjaman_koperasi "," id ='".$_GET['id']."' and tanggal ='".$_GET['tanggal']."'","?act=DetailPinjam&action=TAMBAH&id=".$_GET['id']);
            }

        if(mysqli_num_rows($hasil)!=0) {
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah <a target=_blank href=../penggajian/pages/pinjam/LaporanDetailPinjam.php?&id=$id>| Laporan |</a></div></td>                        
                    </tr>     
                 </table>");
        }
        ?>
    </div>

</div>
