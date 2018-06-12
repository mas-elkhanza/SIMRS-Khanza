

<div id="post">
    <div class="entry">
        <form name="frm_unit" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $id          =isset($_GET['id'])?$_GET['id']:NULL;
                if($action == "TAMBAH"){
                    $id    	      =isset($_GET['id'])?$_GET['id']:NULL;
                    $nik              ='';
                }else if($action == "UBAH"){
                    $_sql         	= "SELECT id,nik,nama,indek,pengurang, cuti_diambil,dankes FROM pegawai WHERE id='$id'";
                    $hasil        	= bukaquery($_sql);
                    $baris        	= mysqli_fetch_row($hasil);
                    $id                 = $baris[0];
                    $nik                = $baris[1];
                    $nama               = $baris[2];
                    if(empty($baris[3])){
			$indek='-';
		    }else{
			$indek              = $baris[3];
		    }
		   
                    if(empty($baris[4])){
			$pengurang='-';
		    }else{
			$pengurang          = $baris[4];
		    }
                    
                    if(empty($baris[5])){
			$cuti_diambil='-';
		    }else{
			$cuti_diambil       = $baris[5];
		    }
			
                    if(empty($baris[6])){
			$dankes='-';
		    }else{
			$dankes             = $baris[6];
		    }                   
        
                }
                echo"<input type=hidden name=id value=$id><input type=hidden name=action value=$action>";
                
                    $_sqlnext         	= "SELECT id FROM pegawai WHERE id>'$id' order by id asc limit 1";
                    $hasilnext        	= bukaquery($_sqlnext);
                    $barisnext        	= mysqli_fetch_row($hasilnext);
                    $next               = $barisnext[0];

                    $_sqlprev         	= "SELECT id FROM pegawai WHERE id<'$id' order by id desc limit 1";
                    $hasilprev        	= bukaquery($_sqlprev);
                    $barisprev        	= mysqli_fetch_row($hasilprev);
                    $prev               = $barisprev[0];
                    
                    if(empty($next)){
                        $next=$prev;
                    }

                    if(empty($prev)){
                        $prev=$next;
                    }                    
                    
                    echo "<div align='center' class='link'>
                          <a href=?act=EditIndexPegawai&action=UBAH&id=$prev><<--</a>
                          <a href=?act=ListIndexPegawai>| Index Pegawai |</a>
                          <a href=?act=HomeAdmin>| Menu Utama |</a>
                          <a href=?act=EditIndexPegawai&action=UBAH&id=$next>-->></a>
                          </div>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="45%" >NIP</td><td width="">:</td>
                    <td width="55%"><?php echo isset($nik)?$nik:NULL; ?></td>
                </tr>
                <tr class="head">
                    <td width="45%" >Nama</td><td width="">:</td>
                    <td width="55%"><?php echo isset($nama)?$nama:NULL;?></td>
                </tr>
                <tr class="head">
                    <td width="45%" >Index Struktural</td><td width="">:</td>
                    <td width="55%"><input name="indek" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo isset($indek)?$indek:NULL;?>" size="10" maxlength="2">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="45%" >Pengurang</td><td width="">:</td>
                    <td width="55%"><input name="pengurang" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo isset($pengurang)?$pengurang:NULL;?>" size="10" maxlength="5"> %
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="45%" valign="top">Cuti Yang Sudah Diambil</td><td width="" valign="top">:</td>
                    <td width="55%" valign="top"><input name="cuti_diambil" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo isset($cuti_diambil)?$cuti_diambil:NULL;?>" size="10" maxlength="5"> X
                      <br>(hanya untuk menampilkan data cuti yang tidak diinput lewat lampiran, Sisa cuti diambil dari data ini ditambah dengan inputan cuti tiap bulan)
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>  
                <tr class="head">
                    <td width="45%" >Dana Kesehatan Selama 1 Tahun</td><td width="">:</td>
                    <td width="55%">Rp.<input name="dankes" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" type=text id="TxtIsi4" class="inputbox" value="<?php echo isset($dankes)?$dankes:NULL;?>" size="15" maxlength="20">
                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>

            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
	            $id               = trim($_POST['id']);
                    $indek            = trim($_POST['indek']);
                    $pengurang        = trim($_POST['pengurang']);
                    $cuti_diambil     = trim($_POST['cuti_diambil']);
                    $dankes           = trim($_POST['dankes']);
                    if ((!empty($pengurang))&&(!empty($indek))) {
                        switch($action) {
                            case "UBAH":
                                Ubah(" pegawai "," pengurang='$pengurang',indek='$indek',cuti_diambil='$cuti_diambil',dankes='$dankes' WHERE id='".$_GET['id']."' ", " Index Pegawai ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=EditIndexPegawai&action=UBAH&id=$id'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($indek))||(empty($pengurang))) {
                        echo 'Semua field harus isi..!!';
                        /*echo " nik='$nik',nama='$nama',jk='$jk',jbtn='$jbtn',jnj_jabatan='$jnj_jabatan',departemen='$departemen',
								                bidang='$bidang',stts_wp='$stts_wp',stts_kerja='$stts_kerja',npwp='$npwp',pendidikan='$pendidikan',
												gapok='$gapok',tmp_lahir='$tmp_lahir',tgl_lahir='$tgl_lahir',alamat='$alamat',kota='$kota',
												mulai_kerja='$mulai_kerja',ms_kerja='$ms_kerja',indek='$indek',indexins='$indexins',bpd='$bpd',
												rekening='$rekening' ";*/
                    }
                }
            ?>
        </form>
    </div>
</div>
