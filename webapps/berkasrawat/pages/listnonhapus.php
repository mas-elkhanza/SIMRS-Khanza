<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div id="post">
    <div align="center" class="link">
            <a href=?act=ListNonHapus>| List Berkas |</a>
    </div> 
    <div class="entry">   
	<form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
            $action = validTeks(isset($_GET['action'])?$_GET['action']:NULL);
            $cari   = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
            $cari   = json_decode(encrypt_decrypt($cari,"d"),true); 
            $tahunawal      = "";
            $bulanawal      = "";
            $tanggalawal    = "";
            $tahunakhir     = "";
            $bulanakhir     = "";
            $tanggalakhir   = ""; 
            $no_rawat       = "";
            $status         = "";
            $keyword        = "";
            $carabayar      = "";
            $poli           = "";
            if (isset($cari["tahunawal"])) {
                $tahunawal      = validTeks4((isset($cari['tahunawal'])?$cari['tahunawal']:NULL),4);
                $bulanawal      = validTeks4((isset($cari['bulanawal'])?$cari['bulanawal']:NULL),2);
                $tanggalawal    = validTeks4((isset($cari['tanggalawal'])?$cari['tanggalawal']:NULL),2);
                $tahunakhir     = validTeks4((isset($cari['tahunakhir'])?$cari['tahunakhir']:NULL),4);
                $bulanakhir     = validTeks4((isset($cari['bulanakhir'])?$cari['bulanakhir']:NULL),4);
                $tanggalakhir   = validTeks4((isset($cari['tanggalakhir'])?$cari['tanggalakhir']:NULL),2);  
                $no_rawat       = validTeks4((isset($cari['no_rawat'])?$cari['no_rawat']:NULL),20);
                $status         = validTeks4((isset($cari['status'])?$cari['status']:NULL),20);  
                $keyword        = validTeks4((str_replace("_"," ",isset($cari['keyword']))?str_replace("_"," ",$cari['keyword']):NULL),20);
                $carabayar      = validTeks4((str_replace("_"," ",isset($cari['carabayar']))?str_replace("_"," ",$cari['carabayar']):NULL),30);
                $poli           = validTeks4((str_replace("_"," ",isset($cari['poli']))?str_replace("_"," ",$cari['poli']):NULL),50);
                echo "<input type=hidden name=carabayar value=$carabayar><input type=hidden name=poli value=$poli><input type=hidden name=keyword value=$keyword>";
            }
        ?>
    <div style="width: 100%; height: 80.4%; overflow: auto;">
    <?php        
	$BtnCari  =isset($_POST['BtnCari'])?$_POST['BtnCari']:NULL;
        if (isset($BtnCari)) {      
                $tahunawal      = validTeks4(trim($_POST['tahunawal']),4);
                $bulanawal      = validTeks4(trim($_POST['bulanawal']),2);
                $tanggalawal    = validTeks4(trim($_POST['tanggalawal']),2);
                $tahunakhir     = validTeks4(trim($_POST['tahunakhir']),4);
                $bulanakhir     = validTeks4(trim($_POST['bulanakhir']),2);
                $tanggalakhir   = validTeks4(trim($_POST['tanggalakhir']),2);    
                $carabayar      = validTeks4(trim($_POST['carabayar']),30);
                $keyword        = validTeks4(trim($_POST['keyword']),20);
                $status         = validTeks4(trim($_POST['status']),20);
                $poli           = validTeks4(trim($_POST['poli']),50);
                $action         = "no";
        }
        if(empty($tahunawal)){
            $tahunawal=date('Y');
        }
        if(empty($bulanawal)){
            $bulanawal=date('m');
        }
        if(empty($tanggalawal)){
            $tanggalawal=date('d');
        }
        if(empty($tahunakhir)){
            $tahunakhir=date('Y');
        }
        if(empty($bulanakhir)){
            $bulanakhir=date('m');
        }
        if(empty($tanggalakhir)){
            $tanggalakhir=date('d');
        }     
        

        $_sql = "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,
                reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,
                pasien.umur,poliklinik.nm_poli,reg_periksa.status_lanjut,reg_periksa.umurdaftar,reg_periksa.sttsumur,
                reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab 
                from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab 
                on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli  where  
                reg_periksa.status_lanjut like '%".$status."%' and poliklinik.nm_poli like '%".$poli."%' and  penjab.png_jawab like '%".$carabayar."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  reg_periksa.no_reg like '%".$keyword."%' or 
                reg_periksa.status_lanjut like '%".$status."%' and poliklinik.nm_poli like '%".$poli."%' and  penjab.png_jawab like '%".$carabayar."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  reg_periksa.no_rawat like '%".$keyword."%' or 
                reg_periksa.status_lanjut like '%".$status."%' and poliklinik.nm_poli like '%".$poli."%' and  penjab.png_jawab like '%".$carabayar."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  reg_periksa.tgl_registrasi like '%".$keyword."%' or
                reg_periksa.status_lanjut like '%".$status."%' and poliklinik.nm_poli like '%".$poli."%' and  penjab.png_jawab like '%".$carabayar."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  reg_periksa.kd_dokter like '%".$keyword."%' or 
                reg_periksa.status_lanjut like '%".$status."%' and poliklinik.nm_poli like '%".$poli."%' and  penjab.png_jawab like '%".$carabayar."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  dokter.nm_dokter like '%".$keyword."%' or 
                reg_periksa.status_lanjut like '%".$status."%' and poliklinik.nm_poli like '%".$poli."%' and  penjab.png_jawab like '%".$carabayar."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  reg_periksa.no_rkm_medis like '%".$keyword."%' or 
                reg_periksa.status_lanjut like '%".$status."%' and poliklinik.nm_poli like '%".$poli."%' and  penjab.png_jawab like '%".$carabayar."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  reg_periksa.status_lanjut like '%".$keyword."%' or 
                reg_periksa.status_lanjut like '%".$status."%' and poliklinik.nm_poli like '%".$poli."%' and  penjab.png_jawab like '%".$carabayar."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  reg_periksa.almt_pj like '%".$keyword."%' or 
                reg_periksa.status_lanjut like '%".$status."%' and poliklinik.nm_poli like '%".$poli."%' and  penjab.png_jawab like '%".$carabayar."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  pasien.nm_pasien like '%".$keyword."%' or 
                reg_periksa.status_lanjut like '%".$status."%' and poliklinik.nm_poli like '%".$poli."%' and  penjab.png_jawab like '%".$carabayar."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  poliklinik.nm_poli like '%".$keyword."%' or 
                reg_periksa.status_lanjut like '%".$status."%' and poliklinik.nm_poli like '%".$poli."%' and  penjab.png_jawab like '%".$carabayar."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  penjab.png_jawab like '%".$keyword."%' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg desc ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='7%'><div align='center'>Proses</div></td>
                        <td width='35%'><div align='center'>Data Pasien</div></td>
                        <td width='22%'><div align='center'>Registrasi</div></td>
                        <td width='36%'><div align='center'>Berkas Digital</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                        echo "<tr class='isi'>
                               <td valign='midle'>
                                    <center>
                                        <a href=?act=DetailNonHapus&action=TAMBAH&iyem=".encrypt_decrypt("{\"no_rawat\":\"".$baris["no_rawat"]."\"}","e").">[Detail Berkas]</a>
                                    </center>
                                    <br>
                                    <center>
                                        <a href=?act=ListNonHapus&iyem=".encrypt_decrypt("{\"keyword\":\"".str_replace(" ","_",$keyword)."\",\"carabayar\":\"".str_replace(" ","_",$carabayar)."\",\"poli\":\"".str_replace(" ","_",$poli)."\",\"tahunawal\":\"".$tahunawal."\",\"bulanawal\":\"".$bulanawal."\",\"tanggalawal\":\"".$tanggalawal."\",\"tahunakhir\":\"".$tahunakhir."\",\"bulanakhir\":\"".$bulanakhir."\",\"tanggalakhir\":\"".$tanggalakhir."\",\"no_rawat\":\"".$baris["no_rawat"]."\"}","e")."&action=GABUNG>[Gabung PDF]</a>
                                    </center>
                               </td>
                               <td bgcolor='#FF0040' valign='top'>
                                  <table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                    <tr class='isi8'>
                                      <td width='23%'>No.Rawat</td><td width='77%'>: ".$baris["no_rawat"]."</td>
                                    </tr>
                                    <tr class='isi8'>
                                      <td width='23%'>No.RM</td><td width='77%'>: ".$baris["no_rkm_medis"]."</td>
                                    </tr>
                                    <tr class='isi8'>
                                      <td width='23%'>Nama Pasien</td><td width='77%'>: ".$baris["nm_pasien"].", ".$baris["umurdaftar"]." ".$baris["sttsumur"].", ".$baris["jk"]." </td>
                                    </tr>
                                    <tr class='isi8'>
                                      <td width='23%'>Alamat Pasien</td><td width='77%'>: ".$baris["almt_pj"]."</td>
                                    </tr>
                                  </table>
                               </td>
                               <td bgcolor='#FF0040' valign='top'>
                                  <table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                    <tr class='isi8'>
                                      <td width='27%'>Tgl.Registrasi</td><td width='73%'>: ".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."</td>
                                    </tr>
                                    <tr class='isi8'>
                                      <td width='27%'>Poliklinik</td><td width='73%'>: ".$baris["nm_poli"]."</td>
                                    </tr>
                                    <tr class='isi8'>
                                      <td width='27%'>Dokter</td><td width='73%'>: ".$baris["nm_dokter"]." </td>
                                    </tr>
                                    <tr class='isi8'>
                                      <td width='27%'>Status</td><td width='73%'>: ".$baris["status_lanjut"]." (".$baris["png_jawab"].")</td>
                                    </tr>
                                  </table>
                               </td>
                               <td bgcolor='#FF0040' valign='top'>
                                  <table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>";
                                        $_sql2 = "SELECT berkas_digital_perawatan.no_rawat,berkas_digital_perawatan.kode, 
                                                  master_berkas_digital.nama,berkas_digital_perawatan.lokasi_file 
                                                  from berkas_digital_perawatan inner join master_berkas_digital 
                                                  on berkas_digital_perawatan.kode=master_berkas_digital.kode 
                                                  where berkas_digital_perawatan.no_rawat='".$baris["no_rawat"]."' ORDER BY master_berkas_digital.nama ASC ";
                                        $hasil2=bukaquery($_sql2);
                                        $no=1;
                                        while($baris2 = mysqli_fetch_array($hasil2)) { 
                                            echo "<tr class='isi8'> 
                                                    <td width='5%'>$no.</td>
                                                    <td width='95%'><a target=_blank href=../berkasrawat/pages/upload/".$baris2["lokasi_file"].">".$baris2["nama"]."</a></td>
                                                  </tr>";$no++;
                                        }
                                    echo "                                  
                                  </table>
                               </td>                                 
                           </tr>";
                    }
            echo "</table>";           
        } else {echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head'>
                            <td width='7%'><div align='center'>Proses</div></td>
                            <td width='30%'><div align='center'>Data Pasien</div></td>
                            <td width='30%'><div align='center'>Registrasi</div></td>
                            <td width='33%'><div align='center'>Berkas Digital</div></td>
                        </tr>
                      </table>";        
        }        
        
        $BtnKeluar=isset($_POST['BtnKeluar'])?$_POST['BtnKeluar']:NULL;
        if (isset($BtnKeluar)) {
            echo"<meta http-equiv='refresh' content='1;URL=?act=ListNonHapus&action=Keluar'>";
	}
        
        if ($action=="GABUNG") { 
            HapusAll("temppanggilnorawat");
            Tambah3(" temppanggilnorawat "," '$no_rawat'");
            echo "<meta http-equiv='refresh' content='1;URL=?act=ListNonHapus&iyem=".encrypt_decrypt("{\"keyword\":\"".str_replace(" ","_",$keyword)."\",\"carabayar\":\"".str_replace(" ","_",$carabayar)."\",\"poli\":\"".str_replace(" ","_",$poli)."\",\"tahunawal\":\"".$tahunawal."\",\"bulanawal\":\"".$bulanawal."\",\"tanggalawal\":\"".$tanggalawal."\",\"tahunakhir\":\"".$tahunakhir."\",\"bulanakhir\":\"".$bulanakhir."\",\"tanggalakhir\":\"".$tanggalakhir."\"}","e")."&action=no'>";                 
        }else{
            if($action!="no"){                 
                echo "<meta http-equiv='refresh' content='1;URL=?act=ListNonHapus&iyem=".encrypt_decrypt("{\"keyword\":\"".str_replace(" ","_",$keyword)."\",\"carabayar\":\"".str_replace(" ","_",$carabayar)."\",\"poli\":\"".str_replace(" ","_",$poli)."\",\"tahunawal\":\"".$tahunawal."\",\"bulanawal\":\"".$bulanawal."\",\"tanggalawal\":\"".$tanggalawal."\",\"tahunakhir\":\"".$tahunakhir."\",\"bulanakhir\":\"".$bulanakhir."\",\"tanggalakhir\":\"".$tanggalakhir."\"}","e")."&action=no'>";                 
            }
        }
    ?>
    </div>
	    <table width="100%" align="center" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr class="head3">					
                    <td>
                        &nbsp;Periode : 
                        <select name="tanggalawal" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                             <?php
                                if(!$tanggalawal==""){
                                    echo "<option id='TxtIsi3' value=$tanggalawal>$tanggalawal</option>";
                                }                                    
                                loadTglnow();
                             ?>
                        </select>                        
                        <select name="bulanawal" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                             <?php
                                if(!$bulanawal==""){
                                    echo "<option id='TxtIsi2' value=$bulanawal>$bulanawal</option>";
                                }                                    
                                loadBlnnow();
                             ?>
                        </select>                        
                        <select name="tahunawal" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                             <?php
                                if(!$tahunawal==""){
                                    echo "<option id='TxtIsi1' value=$tahunawal>$tahunawal</option>";
                                }                                    
                                loadThnnow();
                             ?>
                        </select>
                        &nbsp;s.d.&nbsp;
                        <select name="tanggalakhir" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                             <?php
                                if(!$tanggalakhir==""){
                                    echo "<option id='TxtIsi6' value=$tanggalakhir>$tanggalakhir</option>";
                                }                                    
                                loadTglnow();
                             ?>
                        </select>                        
                        <select name="bulanakhir" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                             <?php
                                if(!$bulanakhir==""){
                                    echo "<option id='TxtIsi5' value=$bulanakhir>$bulanakhir</option>";
                                }                                    
                                loadBlnnow();
                             ?>
                        </select>
                        <select name="tahunakhir" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                             <?php
                                if(!$tahunakhir==""){
                                    echo "<option id='TxtIsi4' value=$tahunakhir>$tahunakhir</option>";
                                }                                    
                                loadThnnow();
                             ?>
                        </select>
                        &nbsp;&nbsp;
                        Cara Bayar : 
                        <select name="carabayar" class="text5">
                            <?php
                                $_sql = "SELECT penjab.png_jawab FROM penjab  ORDER BY penjab.png_jawab";
                                $hasil=bukaquery($_sql);
                                if(!empty($carabayar)){
                                    echo "<option value='$carabayar'>$carabayar</option>";
                                }
                                echo "<option value=''></option>";
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option value='$baris[0]'>$baris[0]</option>";
                                }
                            ?>
                        </select>
                        &nbsp;&nbsp;
                        Record : <?php echo $jumlah; ?>
                    </td>
                </tr>
                <tr class="head3">
                    <td>
                        Unit/Poli : 
                        <select name="poli" class="text5">
                            <?php
                                $_sql = "SELECT poliklinik.nm_poli FROM poliklinik  ORDER BY poliklinik.nm_poli";
                                $hasil=bukaquery($_sql);
                                if(!empty($poli)){
                                    echo "<option value='$poli'>$poli</option>";
                                }
                                echo "<option value=''></option>";
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option value='$baris[0]'>$baris[0]</option>";
                                }
                            ?>
                        </select>
                        &nbsp;&nbsp;
                        Keyword : <input name="keyword" class="text" type="text" value="<?php echo $keyword;?>" size="30" maxlength="20" pattern="[a-zA-Z0-9, ./@_]{1,20}" title=" a-zA-Z0-9, ./@_ (Maksimal 20 karakter)" autocomplete="off" autofocus />
                        &nbsp;&nbsp;
                        Status : 
                        <select name="status" class="text">
                            <option value=''>Semua</option>";
                            <option value='Ralan'>Ralan</option>";
                            <option value='Ranap'>Ranap</option>";
                        </select>
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;" />&nbsp;&nbsp;&nbsp;&nbsp;
                        <input name=BtnKeluar type=submit class="button" value="&nbsp;&nbsp;&nbsp;Keluar&nbsp;&nbsp;&nbsp;" />
                    </td>
                </tr>
            </table>
	</form>
    </div>
</div>

