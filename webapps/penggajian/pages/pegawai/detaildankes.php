

<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun         = $baris[0];
   $bulan         =$baris[1];

?>
<div id="post">
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =isset($_GET['action'])?$_GET['action']:NULL;
                $id                 =isset($_GET['id'])?$_GET['id']:NULL;
                echo "<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
				$_sql  = "SELECT nik,nama FROM pegawai where id='$id'";
                $hasil =bukaquery($_sql);
                $baris = mysqli_fetch_row($hasil);   

                $_sqlnext         	= "SELECT id FROM pegawai WHERE id>'$id' order by id asc limit 1";
                $hasilnext        	= bukaquery($_sqlnext);
                $barisnext        	= mysqli_fetch_row($hasilnext);
                $next                   = $barisnext[0];

                $_sqlprev         	= "SELECT id FROM pegawai WHERE id<'$id' order by id desc limit 1";
                $hasilprev        	= bukaquery($_sqlprev);
                $barisprev        	= mysqli_fetch_row($hasilprev);
                $prev                   = $barisprev[0];
                
                if(empty($next)){
                    $next=$prev;
                }
                
                if(empty($prev)){
                    $prev=$next;
                }

                echo "<div align='center' class='link'>
                      <a href=?act=SisaDankes&action=TAMBAH&id=$prev><<--</a>
                      <a href=?act=ListIndexPegawai>| Index Pegawai |</a>
                      <a href=?act=HomeAdmin>| Menu Utama |</a>
                      <a href=?act=SisaDankes&action=TAMBAH&id=$next>-->></a>
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
                    <td width="31%" >Tanggal Ambil</td><td width="">:</td>
                    <td width="67%">
                        <select name="TglAmbil" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                             <?php
                                loadTgl2();
                             ?>
                        </select>
						<select name="BlnAmbil" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                             <?php
                                loadBln2();
                             ?>
                        </select>
                        <?php echo $tahun;?>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Keterangan</td><td width="">:</td>
                    <td width="67%"><input name="ktg" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo isset($ktg)?$ktg:NULL;?>" size="30" maxlength="40">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Dankes Diambil</td><td width="">:</td>
                    <td width="67%">Rp.<input name="dankes" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo isset($dankes)?$dankes:NULL;?>" size="20" maxlength="20">
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id           =trim($_POST['id']);
                    $TglAmbil     =trim($_POST['TglAmbil']);
                    $BlnAmbil     =trim($_POST['BlnAmbil']);
                    $ktg          =trim($_POST['ktg']);
                    $dankes       =trim($_POST['dankes']);
                    if ((!empty($id))&&(!empty($dankes))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" ambil_dankes "," '$id','$tahun-$BlnAmbil-$TglAmbil','$ktg','$dankes'", " Ambil Dankes " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=SisaDankes&action=TAMBAH&id=$id'>";
                                break;
                        }
                    }else if ((empty($id))||(empty($dankes))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 400px; overflow: auto;">
            <?php
                $awal=isset($_GET['awal'])?$_GET['awal']:NULL;
                if (empty($awal)) $awal=0;
                $_sql = "SELECT id,tanggal,ktg,dankes from ambil_dankes  where id='$id' and tanggal like '%$tahun%' ORDER BY dankes ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                $ttllembur=0;
                $ttlhr=0;

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='12%'><div align='center'>Proses</div></td>
                                <td width='18%'><div align='center'>Tanggal</div></td>
                                <td width='50%'><div align='center'>Keterangan</div></td>
                                <td width='20%'><div align='center'>Dankes Diambil</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                      echo "<tr class='isi'>
                                <td width='70'>
                                    <center>"; ?>
                                    <a href="?act=SisaDankes&action=HAPUS&tanggal=<?php print $baris[1] ?>&id=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[1]</td>
                                <td>$baris[2]</td>
                                <td>".formatDuit($baris[3])."</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "Data ambil dankes masih kosong !";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" ambil_dankes "," id ='".$_GET['id']."' and tanggal ='".$_GET['tanggal']."'","?act=SisaDankes&action=TAMBAH&id=$id");
            }

        if(mysqli_num_rows($hasil)!=0) {
                $hasil1=bukaquery("SELECT id,tanggal,ktg,dankes from ambil_dankes  where id='$id' and tanggal like '%$tahun%' ORDER BY dankes ASC ");
                $jumladiv=mysqli_num_rows($hasil1);
                $i=$jumladiv/19;
                $i=ceil($i);
                echo("Data : $jumlah ");
        }
        ?>
    </div>

</div>
