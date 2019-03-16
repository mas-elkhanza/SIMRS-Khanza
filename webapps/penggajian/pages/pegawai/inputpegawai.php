<div id="post">
    <div class="entry">
        <form name="frm_unit" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $id          =isset($_GET['id'])?$_GET['id']:NULL;
                $nik2        =isset($_GET['nik'])?$_GET['nik']:NULL;
                
                if($action == "TAMBAH"){
                    $id    	= isset($_GET['id'])?$_GET['id']:NULL;
                    $nik        ='';
                    $goto       =isset($_GET['id'])?$_GET['id']:NULL;
                }else if($action == "UBAH"){
                    $_sql           = "SELECT `id`, `nik`, `nama`, `jk`, `jbtn`, `jnj_jabatan`, `departemen`, `bidang`, `stts_wp`, `stts_kerja`, `npwp`, `pendidikan`, `gapok`, `tmp_lahir`, `tgl_lahir`, `alamat`, `kota`, `mulai_kerja`, `ms_kerja`, `indexins`, `bpd`, `rekening`, `stts_aktif`, `wajibmasuk`, `pengurang`, `indek`, `mulai_kontrak`, `cuti_diambil`,`photo`,no_ktp FROM pegawai WHERE id='$id'";
                    $hasil          = bukaquery($_sql);
                    $baris          = mysqli_fetch_row($hasil);                    

                    $id             = $baris[0];
                    $nik            = $baris[1];
                    $nik2           = $baris[1];
                    $nama           = $baris[2];
                    $jk             = $baris[3];
                    $jbtn           = $baris[4];
                    $jnj_jabatan    = $baris[5];
                    $departemen     = $baris[6];
                    $bidang         = $baris[7];
                    $stts_wp        = $baris[8];
                    $stts_kerja     = $baris[9];
                    $npwp           = $baris[10];
                    $pendidikan     = $baris[11];
                    $gapok          = $baris[12];
                    $tmp_lahir      = $baris[13];                    

                    $Thnlahir       =substr($baris[14],0,4);
                    $Blnlahir       =substr($baris[14],5,2);
                    $Tgllahir       =substr($baris[14],8,2);
                    
                    $tgl_lahir      = $Thnlahir."-".$Blnlahir."-".$Tgllahir;

                    $alamat         = $baris[15];
                    $kota           = $baris[16];                    

                    $ThnMulai       =substr($baris[17],0,4);
                    $BlnMulai       =substr($baris[17],5,2);
                    $TglMulai       =substr($baris[17],8,2);

                    $mulai_kerja    = $ThnMulai."-".$BlnMulai."-".$TglMulai;

                    $ms_kerja       = $baris[18];
                    $indexins       = $baris[19];
                    $bpd            = $baris[20];
                    $rekening       = $baris[21];
		    $stts_aktif     = $baris[22];
		    $wajibmasuk     = $baris[23];

                    $ThnKontrak     =substr($baris[26],0,4);
                    $BlnKontrak     =substr($baris[26],5,2);
                    $TglKontrak     =substr($baris[26],8,2);                    
                    $no_ktp         = $baris[29]; 

                    $mulai_kontrak  = $ThnKontrak."-".$BlnKontrak."-".$TglKontrak;
                    $photo         = $baris[28];                   
                }
                
                echo"<input type=hidden name=id value=$id><input type=hidden name=nik2 value=$nik2><input type=hidden name=action value=$action>";
                
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
                          <a href=?act=InputPegawai&action=UBAH&id=$prev><<--</a>
                          <a href=?act=InputPegawai&action=TAMBAH>| Input Data |</a>
                          <a href=?act=ListCariPegawai>| List Data |</a>
                          <a href=?act=ListIndexPegawai>| Index Pegawai |</a>
                          <a href=?act=HomeAdmin>| Menu Utama |</a>
                          <a href=?act=InputPegawai&action=UBAH&id=$next>-->></a>
                          </div>";
            ?>
            <div style="width: 100%; height: 88%; overflow: auto;">  
            <table width="100%" align="center">
                <tr class="head">
                    <td width="25%" >NIP</td><td width="">:</td>
                    <td width="75%"><input name="nik" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo isset($nik)?$nik:NULL;?>" size="20" maxlength="20">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Nama</td><td width="">:</td>
                    <td width="75%"><input name="nama" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo isset($nama)?$nama:NULL;?>" size="50" maxlength="50">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Jenis Kelamin</td><td width="">:</td>
                    <td width="75%">
                        <select name="jk" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                            <!-- <option id='TxtIsi2' value=' '>- Jenis Kelamin -</option> -->
                            <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi3' value=$jk>$jk</option>";
                                }                                 
                            ?>

                            <option id='TxtIsi3' value='Pria'>Pria</option>
                            <option id='TxtIsi3' value='Wanita'>Wanita</option>
                        </select>
                        <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>		
		<tr class="head">
                    <td width="25%" >Jabatan</td><td width="">:</td>
                    <td width="75%"><input name="jbtn" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" type=text id="TxtIsi4" class="inputbox" value="<?php echo isset($jbtn)?$jbtn:NULL;?>" size="30" maxlength="25">
                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Jenjang</td><td width="">:</td>
                    <td width="75%">
                        <select name="jnj_jabatan" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT kode,nama FROM jnj_jabatan  ORDER BY kode";
                                $hasil=bukaquery($_sql);

                                if($action == "UBAH"){
                                    $_sql2 = "SELECT kode,nama FROM jnj_jabatan where kode='$jnj_jabatan' ORDER BY kode";
                                    $hasil2=bukaquery($_sql2);
                                    while($baris2 = mysqli_fetch_array($hasil2)) {
                                        echo "<option id='TxtIsi5' value='$baris2[0]'>$baris2[0]  $baris2[1]</option>";
                                    }
                                }

                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi5' value='$baris[0]'>$baris[0] $baris[1]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Departemen</td><td width="">:</td>
                    <td width="75%">
                        <select name="departemen" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT dep_id,nama FROM departemen  ORDER BY dep_id";
                                $hasil=bukaquery($_sql);

                                if($action == "UBAH"){
                                    $_sql2 = "SELECT dep_id,nama FROM departemen where dep_id='$departemen' ORDER BY dep_id";
                                    $hasil2=bukaquery($_sql2);
                                    while($baris2 = mysqli_fetch_array($hasil2)) {
                                        echo "<option id='TxtIsi6' value='$baris2[0]'>$baris2[0]  $baris2[1]</option>";
                                    }
                                }

                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi6' value='$baris[0]'>$baris[0]  $baris[1]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi6" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Bagian</td><td width="">:</td>
                    <td width="75%">
                        <select name="bidang" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" id="TxtIsi7">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT nama FROM bidang  ORDER BY nama";
                                $hasil=bukaquery($_sql);

                                if($action == "UBAH"){
                                    $_sql2 = "SELECT nama FROM bidang where nama='$bidang' ORDER BY nama";
                                    $hasil2=bukaquery($_sql2);
                                    while($baris2 = mysqli_fetch_array($hasil2)) {
                                        echo "<option id='TxtIsi7' value='$baris2[0]'>$baris2[0]</option>";
                                    }
                                }

                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi7' value='$baris[0]'>$baris[0]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi7" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Status</td><td width="">:</td>
                    <td width="75%">
                        <select name="stts_wp" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi8'));" id="TxtIsi8">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT stts,ktg FROM stts_wp  ORDER BY stts";
                                $hasil=bukaquery($_sql);

                                if($action == "UBAH"){
                                    $_sql2 = "SELECT stts,ktg FROM stts_wp where stts='$stts_wp' ORDER BY stts";
                                    $hasil2=bukaquery($_sql2);
                                    while($baris2 = mysqli_fetch_array($hasil2)) {
                                        echo "<option id='TxtIsi8' value='$baris2[0]'>$baris2[0]  $baris2[1]</option>";
                                    }
                                }

                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi8' value='$baris[0]'>$baris[0] $baris[1]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi8" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Status</td><td width="">:</td>
                    <td width="75%">
                        <select name="stts_kerja" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi9'));" id="TxtIsi9">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT stts,ktg FROM stts_kerja  ORDER BY stts";
                                $hasil=bukaquery($_sql);

                                if($action == "UBAH"){
                                    $_sql2 = "SELECT stts,ktg FROM stts_kerja where stts='$stts_kerja' ORDER BY stts";
                                    $hasil2=bukaquery($_sql2);
                                    while($baris2 = mysqli_fetch_array($hasil2)) {
                                        echo "<option id='TxtIsi9' value='$baris2[0]'>$baris2[0]  $baris2[1]</option>";
                                    }
                                }

                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi9' value='$baris[0]'>$baris[0] $baris[1]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi9" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >NPWP</td><td width="">:</td>
                    <td width="75%"><input name="npwp" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi10'));" type=text id="TxtIsi10" class="inputbox" value="<?php echo isset($npwp)?$npwp:NULL;?>" size="20" maxlength="15">
                    <span id="MsgIsi10" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Pendidikan</td><td width="">:</td>
                    <td width="75%">
                        <select name="pendidikan" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi11'));" id="TxtIsi11">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT tingkat FROM pendidikan  ORDER BY tingkat";
                                $hasil=bukaquery($_sql);

                                if($action == "UBAH"){
                                    $_sql2 = "SELECT tingkat FROM pendidikan where tingkat='$pendidikan' ORDER BY tingkat";
                                    $hasil2=bukaquery($_sql2);
                                    while($baris2 = mysqli_fetch_array($hasil2)) {
                                        echo "<option id='TxtIsi11' value='$baris2[0]'>$baris2[0]</option>";
                                    }
                                }

                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi11' value='$baris[0]'>$baris[0]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi11" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Tempat Lahir</td><td width="">:</td>
                    <td width="75%"><input name="tmp_lahir" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi12'));" type=text id="TxtIsi12" class="inputbox" value="<?php echo isset($tmp_lahir)?$tmp_lahir:NULL;?>" size="30" maxlength="20">
                    <span id="MsgIsi12" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>

		<tr class="head">
                    <td width="25%" >Tanggal Lahir </td><td width="">:</td>
                    <td width="75%">
                        <select name="Tgllahir" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi13'));" id="TxtIsi13">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi13' value=$Tgllahir>$Tgllahir</option>";
                                }
                                loadTgl();
                             ?>
                        </select>
			<select name="Blnlahir" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi13'));" id="TxtIsi13">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi13' value=$Blnlahir>$Blnlahir</option>";
                                }
                                loadBln();
                             ?>
                        </select>
			<select name="Thnlahir" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi13'));" id="TxtIsi13">
                             <?php                               
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi13' value=$Thnlahir>$Thnlahir</option>";
                                }

                                loadThn();
                             ?>
                        </select>
                        <span id="MsgIsi13" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>						
		<tr class="head">
                    <td width="25%" >Alamat</td><td width="">:</td>
                    <td width="75%"><input name="alamat" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi14'));" type=text id="TxtIsi14" class="inputbox" value="<?php echo isset($alamat)?$alamat:NULL;?>" size="50" maxlength="50">
                    <span id="MsgIsi14" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
				<tr class="head">
                    <td width="25%" >Kota</td><td width="">:</td>
                    <td width="75%"><input name="kota" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi15'));" type=text id="TxtIsi15" class="inputbox" value="<?php echo isset($kota)?$kota:NULL;?>" size="30" maxlength="20">
                    <span id="MsgIsi15" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>		
		<tr class="head">
                    <td width="25%" >Mulai Kerja</td><td width="">:</td>
                    <td width="75%">
                        <select name="TglMulai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi16'));" id="TxtIsi16">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi16' value=$TglMulai>$TglMulai</option>";
                                }
                                loadTgl();
                             ?>
                        </select>
			<select name="BlnMulai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi16'));" id="TxtIsi16">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi16' value=$BlnMulai>$BlnMulai</option>";
                                }
                                loadBln();
                             ?>
                        </select>
			<select name="ThnMulai" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi16'));" id="TxtIsi16">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi16' value=$ThnMulai>$ThnMulai</option>";
                                }
                                loadThn();
                             ?>
                        </select>
                        <span id="MsgIsi16" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="25%" >Kode Ms Kerja <?php echo isset($ms_kerja)?$ms_kerja:NULL;?></td><td width="">:</td>
                    <td width="75%">
                        <select name="ms_kerja" class="text3" onkeydown="setDefault(this, document.getElementById('MsgIsi17'));" id="TxtIsi17">
                            <!-- <option id='TxtIsi2' value=' '>- Jenis Kelamin -</option> -->
                            <?php
                                if($action == "UBAH"){
                             ?>
                                    <option id='TxtIsi17' value='<?php echo $ms_kerja; ?>'><?php echo $ms_kerja; ?></option>
                            <?php    
                                }
                            ?>
                            <option id='TxtIsi17' value='<1'><1</option>
                            <option id='TxtIsi17' value='PT'>PT</option>
			    <option id='TxtIsi17' value='FT>1'>FT>1</option>
                        </select>
                        <span id="MsgIsi17" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="25%" >Kode Index</td><td width="">:</td>
                    <td width="75%">
                        <select name="indexins" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi18'));" id="TxtIsi18">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT dep_id,persen FROM indexins  ORDER BY dep_id";
                                $hasil=bukaquery($_sql);

                                if($action == "UBAH"){
                                    $_sql2 = "SELECT dep_id,persen FROM indexins where dep_id='$indexins' ORDER BY dep_id";
                                    $hasil2=bukaquery($_sql2);
                                    while($baris2 = mysqli_fetch_array($hasil2)) {
                                        echo "<option id='TxtIsi18' value='$baris2[0]'>$baris2[0] $baris2[1]%</option>";
                                    }
                                }

                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi18' value='$baris[0]'>$baris[0] $baris[1]%</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi18" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="25%" >Bank</td><td width="">:</td>
                    <td width="75%">
                        <select name="bpd" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi19'));" id="TxtIsi19">
                            <!-- <option id='TxtIsi2' value=' '>- Jenis Kelamin -</option> -->
                            <?php
                                $_sql = "SELECT namabank FROM bank  ORDER BY namabank";
                                $hasil=bukaquery($_sql);

                                if($action == "UBAH"){
                                    $_sql2 = "SELECT namabank FROM bank where namabank='$bpd' ORDER BY namabank";
                                    $hasil2=bukaquery($_sql2);
                                    while($baris2 = mysqli_fetch_array($hasil2)) {
                                        echo "<option id='TxtIsi19' value='$baris2[0]'>$baris2[0]</option>";
                                    }
                                }

                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi19' value='$baris[0]'>$baris[0]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi19" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="25%" >Rekening</td><td width="">:</td>
                    <td width="75%"><input name="rekening" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi20'));" type=text id="TxtIsi20" class="inputbox" value="<?php echo isset($rekening)?$rekening:NULL;?>" size="30" maxlength="25">
                    <span id="MsgIsi20" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr> 
                <tr class="head">
                    <td width="25%" >Status Aktif</td><td width="">:</td>
                    <td width="75%">
                        <select name="stts_aktif" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi21'));" id="TxtIsi21">
                            <!-- <option id='TxtIsi2' value=' '>- Jenis Kelamin -</option> -->
                            <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi21' value=$stts_aktif>$stts_aktif</option>";
                                }                                 
                            ?>

                            <option id='TxtIsi21' value='AKTIF'>AKTIF</option>
                            <option id='TxtIsi21' value='CUTI'>CUTI</option>
                            <option id='TxtIsi21' value='KELUAR'>KELUAR</option>
                        </select>
                        <span id="MsgIsi21" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>	
                <tr class="head">
                    <td width="25%" valign="top">Wajib Masuk</td><td width="">:</td>
                    <td width="75%"><input name="wajibmasuk" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi22'));" type=text id="TxtIsi22" class="inputbox" value="<?php echo isset($wajibmasuk)?$wajibmasuk:NULL;?>" size="5" maxlength="2">
                    <br>Isi dgn "-" jk ingin wjb masuk 1 bulan-hari libur<br>
                    Isi dgn "-1" jk ingin wajib masuk kosong<br>
                    Isi dgn "-2" jk ingin wajib masuk 1 bulan-4 hari<br>
                    Isi dgn "-3" jk ingin wajib masuk 1 bulan-2 hari-linas<br>
                    Isi dgn "-4" jk ingin wajib masuk 1 bulan-hari akhad<br>
                    Isi dgn "-5" jk ingin wajib mengikuti penjadwalan
		    <span id="MsgIsi22" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Mulai Kontrak</td><td width="">:</td>
                    <td width="75%">
                        <select name="TglKontrak" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi23'));" id="TxtIsi23">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi23' value=$TglKontrak>$TglKontrak</option>";
                                }
                                loadTgl();
                             ?>
                        </select>
			<select name="BlnKontrak" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi23'));" id="TxtIsi23">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi23' value=$BlnKontrak>$BlnKontrak</option>";
                                }
                                loadBln();
                             ?>
                        </select>
			<select name="ThnKontrak" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi23'));" id="TxtIsi23">
                             <?php
                                if($action == "UBAH"){
                                    echo "<option id='TxtIsi23' value=$ThnKontrak>$ThnKontrak</option>";
                                }
                                loadThn();
                             ?>
                        </select>
                        <span id="MsgIsi23" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Photo</td><td width="">:</td>
                    <td width="75%">
                        <input name="photo" class="text2"  type=file  value="<?php echo $photo;?>" size="15" maxlength="250" />                                          
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >No.KTP</td><td width="">:</td>
                    <td width="75%"><input name="no_ktp" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi24'));" type=text id="TxtIsi24" class="inputbox" value="<?php echo isset($no_ktp)?$no_ktp:NULL;?>" size="30" maxlength="20">
                    <span id="MsgIsi24" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            </div>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp;<input name=BtnKosong type=reset class="button" value="KOSONG"></div>

            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
	            $id             = str_replace("'","`",trim($_POST['id']));
                    $nik            = str_replace("'","`",trim($_POST['nik']));
                    $nik2           = str_replace("'","`",trim($_POST['nik2']));
                    $nama           = str_replace("'","`",trim($_POST['nama']));
                    $jk             = str_replace("'","`",trim($_POST['jk']));
                    $jbtn           = str_replace("'","`",trim($_POST['jbtn']));
                    $jnj_jabatan    = str_replace("'","`",trim($_POST['jnj_jabatan']));
                    $departemen     = str_replace("'","`",trim($_POST['departemen']));
                    $bidang         = str_replace("'","`", trim($_POST['bidang']));
                    $stts_wp        = trim($_POST['stts_wp']);
                    $stts_kerja     = trim($_POST['stts_kerja']);
                    $npwp           = trim($_POST['npwp']);
                    $pendidikan     = str_replace("'","`",trim($_POST['pendidikan']));
                    $tmp_lahir      = str_replace("'","`",trim($_POST['tmp_lahir']));                     
                    $tgl_lahir      = trim($_POST['Thnlahir'])."-".trim($_POST['Blnlahir'])."-".trim($_POST['Tgllahir']);
                    $alamat         = str_replace("'","`",trim($_POST['alamat']));
                    $kota           = str_replace("'","`",trim($_POST['kota']));                 
                    $mulai_kerja    = trim($_POST['ThnMulai'])."-".trim($_POST['BlnMulai'])."-".trim($_POST['TglMulai']);
                    $ms_kerja       = str_replace("'","`",trim($_POST['ms_kerja']));
                    $indexins       = trim($_POST['indexins']);
                    $bpd            = str_replace("'","`",trim($_POST['bpd']));
                    $rekening       = str_replace("'","`",trim($_POST['rekening']));					
		    $stts_aktif     = trim($_POST['stts_aktif']);
		    $wajibmasuk     = trim($_POST['wajibmasuk']);

                    $mulai_kontrak  = trim($_POST['ThnKontrak'])."-".trim($_POST['BlnKontrak'])."-".trim($_POST['TglKontrak']);
                    $photo          = "pages/pegawai/photo/".$_FILES['photo']['name'];
                    $no_ktp         = trim($_POST['no_ktp']);
                    move_uploaded_file($_FILES['photo']['tmp_name'],$photo);
                    
                    if ((!empty($nik))&&(!empty($jnj_jabatan))&&(!empty($departemen))&&(!empty($bidang))&&(!empty($stts_wp))&&(!empty($stts_kerja))&&
			(!empty($pendidikan))&&(!empty($tgl_lahir))&&(!empty($mulai_kerja))&&(!empty($indexins))&&(!empty($bpd))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" pegawai ","'','$nik','$nama','$jk','$jbtn','$jnj_jabatan','$departemen','$bidang','$stts_wp',
							'$stts_kerja','$npwp','$pendidikan','0','$tmp_lahir','$tgl_lahir','$alamat',
							'$kota','$mulai_kerja','$ms_kerja','$indexins','$bpd','$rekening','$stts_aktif',
                                                        '$wajibmasuk','0','0','$mulai_kontrak','0','0','$photo','$no_ktp'", " pegawai " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputPegawai&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                if($photo=="pages/pegawai/photo/"){
                                    $ph="";
                                }else if($photo<>"pages/pegawai/photo/"){
                                    $ph=",photo='$photo'";
                                }
                                
                                Ubah2(" dokter ","nm_dokter='$nama',jk='".str_replace("Wanita","P",str_replace("Pria","L",$jk))."',
                                        tmp_lahir='$tmp_lahir',tgl_lahir='$tgl_lahir',almt_tgl='$alamat' where kd_dokter='$nik2'");
                                Ubah2(" petugas ","nama='$nama',jk='".str_replace("Wanita","P",str_replace("Pria","L",$jk))."',
                                        tmp_lahir='$tmp_lahir',tgl_lahir='$tgl_lahir',alamat='$alamat' where nip='$nik2' ");
                                Ubah(" pegawai "," nik='$nik',nama='$nama',jk='$jk',jbtn='$jbtn',jnj_jabatan='$jnj_jabatan',departemen='$departemen',
					bidang='$bidang',stts_wp='$stts_wp',stts_kerja='$stts_kerja',npwp='$npwp',pendidikan='$pendidikan',
					tmp_lahir='$tmp_lahir',tgl_lahir='$tgl_lahir',alamat='$alamat',kota='$kota',mulai_kontrak='$mulai_kontrak',
					mulai_kerja='$mulai_kerja',ms_kerja='$ms_kerja',indexins='$indexins',bpd='$bpd',
					rekening='$rekening',stts_aktif='$stts_aktif',no_ktp='$no_ktp',wajibmasuk='$wajibmasuk' ".$ph." WHERE id='$id' ", " pegawai ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=InputPegawai&action=UBAH&id=$id'></head><body></body></html>";
                                break;
                        }
                    }else if (empty($nik)||empty($jnj_jabatan)||empty($departemen)||empty($bidang)||empty($stts_wp)||empty($stts_kerja)||
                               empty($pendidikan)||empty($tgl_lahir)||empty($mulai_kerja)||empty($indexins)||empty($bpd)) {
                        echo 'Semua field harus isi..!!';
                        //echo $ms_kerja;
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
