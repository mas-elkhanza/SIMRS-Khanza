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
   $bln_leng=strlen($baris[1]);
   $blncari=$baris[1];
   $bulan="0";
   if ($bln_leng==1){
    	$bulan="0".$baris[1];
   }else{
		$bulan=$baris[1]; 
   }
?>
<div id="post">
    <h1 class="title">::[ Input Detail Ketidakhadiran Tahun <?php echo$tahun ;?> Bulan <?php echo$bulan ;?> ]::</h1>
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =$_GET['action'];
                $id                 =$_GET['id'];
                $tgl                =$tahun."-".$bulan."-1";
                $jns                =$_GET['jns'];
                $ktg                =$_GET['ktg'];
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
                          <a href=?act=InputTidakHadir&action=TAMBAH&id=$prev><<--</a>
                          <a href=?act=ListLampiran&action=LIHAT>| List Lampiran |</a>
                          <a href=?act=InputTidakHadir&action=TAMBAH&id=$next>-->></a>
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
                    <td width="25%" >Ketidakhadiran</td><td width="">:</td>
                    <td width="75%">
                        <select name="jns" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                            <!-- <option id='TxtIsi1' value=' '>- Jenis Kelamin -</option> -->
                            <option id='TxtIsi1' value='A'>A</option>
                            <option id='TxtIsi1' value='S'>S</option>
                            <option id='TxtIsi1' value='C'>C</option>
                            <option id='TxtIsi1' value='I'>I</option>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Keterangan</td><td width="">:</td>
                    <td width="67%"><input name="ktg" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $ktg;?>" size="50" maxlength="40" />
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Jumlah</td><td width="">:</td>
                    <td width="67%"><input name="jml" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo $jml;?>" size="10" maxlength="5" />
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=$_POST['BtnSimpan'];
                if (isset($BtnSimpan)) {
                    $id                 =trim($_POST['id']);
                    $tgl                =$tahun."-".$bulan."-1";
                    $ktg                = validTeks(trim($_POST['ktg']));
                    $jns                = validTeks(trim($_POST['jns']));
                    $jml                = validangka(trim($_POST['jml']));
                    if ((!empty($id))&&(!empty($tgl))&&(!empty($jns))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" ketidakhadiran "," '$tgl','$id','$jns','$ktg','$jml'", " ketidakhadiran " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputTidakHadir&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else if ((empty($id))||(empty($tgl))||(empty($jns))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 598px; height: 150px; overflow: auto;">
            <?php
                $awal=$_GET['awal'];
                if (empty($awal)) $awal=0;
                $_sql = "SELECT tgl,id,jns,ktg,jml
                        from ketidakhadiran where id='$id'
			and tgl like '%".$tahun."-".$bulan."%' ORDER BY tgl ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                $ttls=0;
                $ttla=0;
                $ttlc=0;
                $ttli=0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='598px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='70px'><div align='center'><font size='2' face='Verdana'><strong>Proses</strong></font></div></td>
                                <td width='110px'><div align='center'><font size='2' face='Verdana'><strong>Jns.Tdk Hadir</strong></font></div></td>
                                <td width='110px'><div align='center'><font size='2' face='Verdana'><strong>Katerangan</strong></font></div></td>
                                <td width='110px'><div align='center'><font size='2' face='Verdana'><strong>Jumlah</strong></font></div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        if($baris[2]=='S'){
                            $ttls=$ttls+$baris[4];
                        }
                        if($baris[2]=='A'){
                            $ttla=$ttla+$baris[4];
                        }
                        if($baris[2]=='C'){
                            $ttlc=$ttlc+$baris[4];
                        }
                        if($baris[2]=='I'){
                            $ttli=$ttli+$baris[4];
                        }
                        
                      echo "<tr class='isi'>
                                <td width='70'>
                                    <center>"; ?>
                                    <a href="?act=InputTidakHadir&action=HAPUS&tgl=<?php print $baris[0] ?>&id=<?php print $baris[1] ?>&jns=<?php print $baris[2] ?>" onClick="if (!confirm('Anda yakin menghapus data ketidakhadiran  <?php print $baris[2]?>?')) return false;">[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[2]</td>
                                <td>$baris[3]</td>
                                <td>$baris[4]</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "<b>Data ketidakhadiran masih kosong !</b>";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" ketidakhadiran"," id ='".$_GET['id']."' and tgl ='".$_GET['tgl']."' and jns ='".$_GET['jns']."'","?act=InputTidakHadir&action=TAMBAH&id=$id");
            }

        if(mysqli_num_rows($hasil)!=0) {
                $hasil1=bukaquery("SELECT tgl,id,jns,ktg,jml
                        from ketidakhadiran where id='$id'
			     and tgl like '%".$tahun."-".$bulan."%' ORDER BY tgl ASC ");
                $jumlah1=mysqli_num_rows($hasil1);
                $i=$jumlah1/19;
                $i=ceil($i);
                echo("Data : $jumlah, A : ".$ttla." , S : ".$ttls.", C : ".$ttlc.", I : ".$ttli." ");
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