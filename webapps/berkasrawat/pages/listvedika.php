<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div id="post">
    <div class="entry">   
	<form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <div style="width: 100%; height: 87.4%; overflow: auto;">
            <?php
                $carabayar      = encrypt_decrypt($_SESSION["carabayar"],"d");
                $BtnCari        = isset($_POST['BtnCari'])?$_POST['BtnCari']:NULL;
                $tahunawal      = "";
                $bulanawal      = "";
                $tanggalawal    = "";
                $tahunakhir     = "";
                $bulanakhir     = "";
                $tanggalakhir   = ""; 
                $keyword        = "";
                $status         = "";
                $poli           = "";
                if (isset($BtnCari)) {      
                    $tahunawal      = validTeks4(trim($_POST['tahunawal']),4);
                    $bulanawal      = validTeks4(trim($_POST['bulanawal']),2);
                    $tanggalawal    = validTeks4(trim($_POST['tanggalawal']),2);
                    $tahunakhir     = validTeks4(trim($_POST['tahunakhir']),4);
                    $bulanakhir     = validTeks4(trim($_POST['bulanakhir']),2);
                    $tanggalakhir   = validTeks4(trim($_POST['tanggalakhir']),2);    
                    $keyword        = validTeks4(trim($_POST['keyword']),30);
                    $status         = validTeks4(trim($_POST['status']),15);
                    $poli           = validTeks4(trim($_POST['poli']),50);
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
                
                $_sql   = "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,
                            reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,
                            pasien.umur,poliklinik.nm_poli,reg_periksa.status_lanjut,reg_periksa.umurdaftar,reg_periksa.sttsumur,pasien.no_peserta,
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
                $hasil  = bukaquery($_sql);
                $jumlah = mysqli_num_rows($hasil);
                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='30%'><div align='center'>Data Pasien</div></td>
                                <td width='22%'><div align='center'>Registrasi</div></td>
                                <td width='22%'><div align='center'>Kunjungan</div></td>
                                <td width='21%'><div align='center'>Berkas Digital</div></td>
                            </tr>";
                            while($baris = mysqli_fetch_array($hasil)) {                        
                                echo "<tr class='isi'>
                                       <td valign='midle'>
                                            <center>
                                                <a target=_blank href=../berkasrawat/pages/tampilpdf.php?iyem=".encrypt_decrypt("{\"no_rawat\":\"".$baris["no_rawat"]."\"}","e").">[Gabung]</a><br>
                                                <a target=_blank href=../berkasrawat/pages/billing.php?iyem=".encrypt_decrypt("{\"no_rawat\":\"".$baris["no_rawat"]."\"}","e").">[Billing]</a>
                                            </center>
                                       </td>
                                       <td bgcolor='#FFFFFF' valign='top'>
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
                                       <td bgcolor='#FFFFFF' valign='top'>
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
                                       <td bgcolor='#FFFFFF' valign='top'>
                                          <table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                                            <tr class='isi8'>
                                              <td width='27%'>No.Kunjungan</td><td width='73%'>: ".getOne("select no_sep from bridging_sep where no_rawat='".$baris["no_rawat"]."'")."</td>
                                            </tr>
                                            <tr class='isi8'>
                                              <td width='27%'>No.Kartu</td><td width='73%'>: ".$baris["no_peserta"]."</td>
                                            </tr>
                                            <tr class='isi8'>
                                              <td width='27%'>D.U.</td><td width='73%'>: ".getOne("select concat(diagnosa_pasien.kd_penyakit,' ',penyakit.nm_penyakit) from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit where diagnosa_pasien.no_rawat='".$baris["no_rawat"]."' and diagnosa_pasien.prioritas='1' and diagnosa_pasien.status='".$baris["status_lanjut"]."'")." </td>
                                            </tr>
                                          </table>
                                       </td>
                                       <td bgcolor='#FFFFFF' valign='top'>
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
                                                            <td width='95%'><a target=_blank href=../berkasrawat/".$baris2["lokasi_file"].">".$baris2["nama"]."</a></td>
                                                          </tr>";$no++;
                                                }
                                            echo "                                  
                                          </table>
                                       </td>                                 
                                   </tr>";
                            }
                    echo "</table>";           
                } else {
                    echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='5%'><div align='center'>Proses</div></td>
                                <td width='30%'><div align='center'>Data Pasien</div></td>
                                <td width='22%'><div align='center'>Registrasi</div></td>
                                <td width='22%'><div align='center'>Kunjungan</div></td>
                                <td width='21%'><div align='center'>Berkas Digital</div></td>
                            </tr>
                          </table>";        
                }        

                $BtnKeluar=isset($_POST['BtnKeluar'])?$_POST['BtnKeluar']:NULL;
                if (isset($BtnKeluar)) {
                    echo"<meta http-equiv='refresh' content='1;URL=logout.php'>";
                }
            ?>
            </div>
	    <table width="100%" align="center" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr class="head">					
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
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        Unit/Poli : 
                        <select name="poli" class="text5">
                            <?php
                                $_sql = "SELECT poliklinik.nm_poli FROM poliklinik ORDER BY poliklinik.nm_poli";
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
                    </td>
                </tr>
                <tr class="head3">
                    <td>
                        Keyword : <input name="keyword" class="text" type="text" value="<?php echo $keyword;?>" size="43" maxlength="30" pattern="[a-zA-Z0-9, ./@_]{1,30}" title=" a-zA-Z0-9, ./@_ (Maksimal 30 karakter)" autocomplete="off" autofocus />
                        &nbsp;&nbsp;
                        Status : 
                        <select name="status" class="text">
                            <option value=''>Semua</option>";
                            <option value='Ralan'>Ralan</option>";
                            <option value='Ranap'>Ranap</option>";
                        </select>
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;" />&nbsp;&nbsp;
                        Record : <?php echo $jumlah; ?>&nbsp;&nbsp;
                        <input name=BtnKeluar type=submit class="button" value="&nbsp;&nbsp;Keluar&nbsp;&nbsp;" />
                    </td>
                </tr>
            </table>
	</form>
    </div>
</div>

