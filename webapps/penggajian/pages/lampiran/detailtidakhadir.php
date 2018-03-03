
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
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =isset($_GET['action'])?$_GET['action']:NULL;
                $id                 =isset($_GET['id'])?$_GET['id']:NULL;
                $tgl                =$tahun."-".$bulan."-1";
                $jns                =isset($_GET['jns'])?$_GET['jns']:NULL;
                $ktg                =isset($_GET['ktg'])?$_GET['ktg']:NULL;
                $jml                =isset($_GET['jml'])?$_GET['jml']:NULL;
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

                    if(empty($prev)){
                        $prev=$next;
                    }
                    
                    if(empty($next)){
                        $next=$prev;
                    }
                    
                    echo "<div align='center' class='link'>
                          <a href=?act=InputTidakHadir&action=TAMBAH&id=$prev><<--</a>
                          <a href=?act=ListLampiran&action=LIHAT>| List Lampiran |</a>
                          <a href=?act=HomeAdmin>| Menu Utama |</a>
                          <a href=?act=InputTidakHadir&action=TAMBAH&id=$next>-->></a>
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
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id                 =trim($_POST['id']);
                    $tgl                =$tahun."-".$bulan."-1";
                    $ktg                =trim($_POST['ktg']);
                    $jns                =trim($_POST['jns']);
                    $jml                =trim($_POST['jml']);
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
            <div style="width: 100%; height: 55%; overflow: auto;">
            <?php
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
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='10%'><div align='center'>Proses</div></td>
                                <td width='20%'><div align='center'>Jns.Tdk Hadir</div></td>
                                <td width='50%'><div align='center'>Katerangan</div></td>
                                <td width='20%'><div align='center'>Jumlah</div></td>
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
                                    <a href="?act=InputTidakHadir&action=HAPUS&tgl=<?php print $baris[0] ?>&id=<?php print $baris[1] ?>&jns=<?php print $baris[2] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[2]</td>
                                <td>$baris[3]</td>
                                <td>$baris[4]</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "Data ketidakhadiran masih kosong !";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" ketidakhadiran"," id ='".$_GET['id']."' and tgl ='".$_GET['tgl']."' and jns ='".$_GET['jns']."'","?act=InputTidakHadir&action=TAMBAH&id=$id");
            }
                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah, A : ".$ttla." , S : ".$ttls.", C : ".$ttlc.", I : ".$ttli."</div></td>                        
                    </tr>     
                 </table>");
        
        ?>
    </div>

</div>
