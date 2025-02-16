<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div id="post">
    <div class="entry">   
	<form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
                echo "";
                $tahunawal      = validTeks4((isset($_GET['tahunawal'])?$_GET['tahunawal']:NULL),4);
                $bulanawal      = validTeks4((isset($_GET['bulanawal'])?$_GET['bulanawal']:NULL),2);
                $tanggalawal    = validTeks4((isset($_GET['tanggalawal'])?$_GET['tanggalawal']:NULL),2);
                $tahunakhir     = validTeks4((isset($_GET['tahunakhir'])?$_GET['tahunakhir']:NULL),4);
                $bulanakhir     = validTeks4((isset($_GET['bulanakhir'])?$_GET['bulanakhir']:NULL),2);
                $tanggalakhir   = validTeks4((isset($_GET['tanggalakhir'])?$_GET['tanggalakhir']:NULL),2);  
                $action         = validTeks(isset($_GET['action'])?$_GET['action']:NULL);
                $norawat        = validTeks4((isset($_GET['norawat'])?$_GET['norawat']:NULL),20);
                $codernik       = validTeks4((isset($_GET['codernik'])?$_GET['codernik']:NULL),30);
                $carabayar      = validTeks4((str_replace("_"," ",isset($_GET['carabayar']))?str_replace("_"," ",$_GET['carabayar']):NULL),40);
                $keyword        = validTeks4((isset($_GET['keyword'])?$_GET['keyword']:NULL),20);
                $statuskirim    = validTeks(str_replace("_"," ",isset($_GET['statuskirim']))?str_replace("_"," ",$_GET['statuskirim']):NULL);
                echo "<input type=hidden name=codernik  value=$codernik><input type=hidden name=keyword value=$keyword>";
        ?>
        <div style="width: 100%; height: 85%; overflow: auto;">
        <?php
            $BtnCari  =isset($_POST['BtnCari'])?$_POST['BtnCari']:NULL;
            $keyword  =isset($_POST['keyword'])?trim($_POST['keyword']):NULL;
            $keyword  = validTeks4($keyword,20);
            if (isset($BtnCari)) {      
                    $tahunawal      = validTeks4(trim($_POST['tahunawal']),4);
                    $bulanawal      = validTeks4(trim($_POST['bulanawal']),2);
                    $tanggalawal    = validTeks4(trim($_POST['tanggalawal']),2);
                    $tahunakhir     = validTeks4(trim($_POST['tahunakhir']),4);
                    $bulanakhir     = validTeks4(trim($_POST['bulanakhir']),2);
                    $tanggalakhir   = validTeks4(trim($_POST['tanggalakhir']),2);
                    $codernik       = validTeks4(trim($_POST['codernik']),30); 
                    $carabayar      = validTeks4((str_replace("_"," ",isset($_POST['carabayar']))?str_replace("_"," ",trim($_POST['carabayar'])):NULL),40);
                    $statuskirim    = validTeks((str_replace("_"," ",isset($_POST['statuskirim']))?str_replace("_"," ",trim($_POST['statuskirim'])):NULL),40);
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
                    reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.umur,poliklinik.nm_poli,
                    reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_bayar,penjab.png_jawab 
                    from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis
                    inner join poliklinik on reg_periksa.kd_poli=poliklinik.kd_poli inner join penjab on reg_periksa.kd_pj=penjab.kd_pj where  
                    reg_periksa.stts<>'Batal' ".(!empty($carabayar)?"and penjab.png_jawab like '%".$carabayar."%'":"")." and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' ".
                    (!empty($keyword)?"and (reg_periksa.no_reg like '%".$keyword."%' or reg_periksa.no_rawat like '%".$keyword."%' or reg_periksa.tgl_registrasi like '%".$keyword."%' or reg_periksa.kd_dokter like '%".$keyword."%' or dokter.nm_dokter like '%".$keyword."%' or 
                    reg_periksa.no_rkm_medis like '%".$keyword."%' or reg_periksa.status_bayar like '%".$keyword."%' or pasien.nm_pasien like '%".$keyword."%' or poliklinik.nm_poli like '%".$keyword."%' or penjab.png_jawab like '%".$keyword."%') ":"").
                    " order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg desc ";
            $hasil=bukaquery($_sql);
            $jumlah=0;
            if(mysqli_num_rows($hasil)!=0) {
                echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head2'>
                            <td width='30%'><div align='center'>Data Pasien</div></td>
                            <td width='20%'><div align='center'>Registrasi</div></td>
                            <td width='20%'><div align='center'>Dokter</div></td>
                            <td width='18%'><div align='center'>Diagnosa</div></td>
                            <td width='12%'><div align='center'>Status</div></td>
                        </tr>";
                        while($baris = mysqli_fetch_array($hasil)) {
                            $statuscovid="Bukan Corona";
                            $aksi="BukanCorona";
                            /*if(getOne("select count(perawatan_corona.no_rawat) from perawatan_corona where perawatan_corona.no_rawat='".$baris["no_rawat"]."'")>0){
                                $statuscovid="Pasien Corona";
                                $aksi="PasienCorona";
                            }*/

                            $carabayar =str_replace(" ","_",$carabayar)?str_replace(" ","_",$carabayar):NULL;
                            $statuskirim =str_replace(" ","_",$statuskirim)?str_replace(" ","_",$statuskirim):NULL;
                            $status="<a href='?act=DetailKirim&corona=$aksi&norawat=".$baris["no_rawat"]."&codernik=$codernik&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&carabayar=$carabayar&statuskirim=$statuskirim'>[Kirim]</a>";
                            $statusdata="Belum Terkirim";
                            if(getOne("select count(inacbg_klaim_baru2.no_rawat) from inacbg_klaim_baru2 where inacbg_klaim_baru2.no_rawat='".$baris["no_rawat"]."'")>0){
                                $status="<a href='?act=DetailKirim&corona=$aksi&norawat=".$baris["no_rawat"]."&codernik=$codernik&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&carabayar=$carabayar&statuskirim=$statuskirim'>[Kirim Ulang]</a>";
                                $statusdata="Sudah Terkirim";
                            }
                            $carabayar =str_replace("_"," ",$carabayar)?str_replace("_"," ",$carabayar):NULL;
                            $statuskirim =str_replace("_"," ",$statuskirim)?str_replace("_"," ",$statuskirim):NULL;
                            if(($statuskirim=="Semua")||($statuskirim==$statusdata)){
                                echo "<tr class='isi' title='".$baris["no_rawat"].", ".$baris["no_rkm_medis"].", ".$baris["nm_pasien"]."'>
                                        <td bgcolor='#FFFFFF' valign='middle'>".$baris["no_rkm_medis"]." ".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                        <td bgcolor='#FFFFFF' valign='middle'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".str_replace("Bayar","Closing",$baris["status_bayar"])."</td>
                                        <td bgcolor='#FFFFFF' valign='middle'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                        <td valign='middle'>";
                                        $penyakit="";
                                        $a=1;
                                        $hasilpenyakit=bukaquery("select diagnosa_pasien.kd_penyakit from diagnosa_pasien where diagnosa_pasien.no_rawat='".$baris["no_rawat"]."' order by diagnosa_pasien.prioritas asc");
                                        while($barispenyakit = mysqli_fetch_array($hasilpenyakit)) {
                                            if($a==1){
                                                $penyakit=$barispenyakit["kd_penyakit"];
                                            }else{
                                                $penyakit=$penyakit.", ".$barispenyakit["kd_penyakit"];
                                            }                
                                            $a++;
                                        }
                                        echo $penyakit."<br>";

                                        $prosedur="";
                                        $a=1;
                                        $hasilprosedur=bukaquery("select prosedur_pasien.kode from prosedur_pasien where prosedur_pasien.no_rawat='".$baris["no_rawat"]."' order by prosedur_pasien.prioritas asc");
                                        while($barisprosedur = mysqli_fetch_array($hasilprosedur)) {
                                            if($a==1){
                                                $prosedur=$barisprosedur["kode"];
                                            }else{
                                                $prosedur=$prosedur.", ".$barisprosedur["kode"];
                                            }                
                                            $a++;
                                        } 
                                        echo $prosedur;
                                 echo  "</td>
                                        <td valign='center' align='center'>
                                            <!-- <a href='?act=KlaimBaruManual2&action=InputCorona&norawat=".$baris["no_rawat"]."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&keyword=$keyword&carabayar=$carabayar&statuskirim=$statuskirim'>[$statuscovid]</a><br> -->
                                            <a href='?act=KlaimBaruManual2&action=RiwayatPerawatan&norawat=".$baris["no_rawat"]."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&keyword=$keyword&carabayar=$carabayar&statuskirim=$statuskirim'>[Riwayat Perawatan]</a><br>
                                            <a href='?act=KlaimBaruManual2&action=DataBilling&norawat=".$baris["no_rawat"]."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&keyword=$keyword&carabayar=$carabayar&statuskirim=$statuskirim'>[Billing]</a><br>
                                            <a href='?act=KlaimBaruManual2&action=InputDiagnosa&norawat=".$baris["no_rawat"]."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&keyword=$keyword&carabayar=$carabayar&statuskirim=$statuskirim'>[Input Diagnosa]</a><br>
                                            ".$status."
                                        </td>                                
                                     </tr>";
                                 $jumlah++;
                            }
                        }
                echo "</table>";           
            }else{
                echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head2'>
                            <td width='30%'><div align='center'>Data Pasien</div></td>
                            <td width='20%'><div align='center'>Registrasi</div></td>
                            <td width='20%'><div align='center'>Dokter</div></td>
                            <td width='20%'><div align='center'>Diagnosa</div></td>
                            <td width='10%'><div align='center'>Status</div></td>
                        </tr>
                       </table>";
            }         

            if(($action=="InputDiagnosa")||($action=="InputCorona")||($action=="RiwayatPerawatan")||($action=="DataBilling")) {
                HapusAll("temppanggilnorawat");
                InsertData2("temppanggilnorawat","'$norawat'");
                echo "<meta http-equiv='refresh' content='1;URL=?act=KlaimBaruManual2&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&action=no&keyword=$keyword&carabayar=$carabayar&statuskirim=$statuskirim'>";
            }

            $BtnKeluar=isset($_POST['BtnKeluar'])?$_POST['BtnKeluar']:NULL;
            if (isset($BtnKeluar)) {
                echo"<meta http-equiv='refresh' content='1;URL=?act=KlaimBaruManual2&action=Keluar'>";
            }

        ?>
        </div>
            <table width="100%" align="center" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr class="head3">					
                    <td width="690px">
                        Periode : 
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
                        &nbsp;
                        Cara Bayar : 
                        <select name="carabayar" class="text4">
                            <?php
                                if(!empty($carabayar)){
                                    echo "<option value='$carabayar'>$carabayar</option>";
                                }
                                
                                if(!isset($_SESSION["penjab"])){
                                    $penjab = "<option value=''>Semua</option>";
                                    $_sql   = "SELECT penjab.png_jawab FROM penjab  ORDER BY penjab.png_jawab";
                                    $hasil=bukaquery($_sql);
                                    echo "<option value=''>Semua</option>";
                                    while($baris = mysqli_fetch_array($hasil)) {
                                        echo "<option value='$baris[0]'>$baris[0]</option>";
                                        $penjab=$penjab."<option value='$baris[0]'>$baris[0]</option>";
                                    }
                                    $_SESSION["penjab"]=$penjab;
                                }else{
                                    echo $_SESSION["penjab"];
                                }
                            ?>
                        </select>                        
                    </td>
                </tr>
                <tr class="head3">					
                    <td width="690px">
                        Status :
                        <select name="statuskirim" class="text">
                            <?php
                                if(!empty($statuskirim)){
                                    echo "<option value='$statuskirim'>$statuskirim</option>";
                                }
                            ?>
                            <option value="Semua">Semua</option>
                            <option value="Sudah Terkirim">Sudah Terkirim</option>
                            <option value="Belum Terkirim">Belum Terkirim</option>
                        </select>&nbsp;&nbsp;&nbsp;
                        Keyword : <input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="37" maxlength="20" pattern="[a-zA-Z0-9, ./@_]{1,20}" title=" a-zA-Z0-9, ./@_ (Maksimal 20 karakter)" autocomplete="off" autocomplete="off" autofocus />
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;" />
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        Record : <?php echo $jumlah; ?>
                        &nbsp;&nbsp;&nbsp;
                        <input name=BtnKeluar type=submit class="button" value="&nbsp;&nbsp;&nbsp;Keluar&nbsp;&nbsp;&nbsp;" />
                    </td>
                </tr>
            </table>
	</form>
    </div>
</div>

