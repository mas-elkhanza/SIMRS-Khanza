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
                $codernik       = validTeks4((isset($_GET['codernik'])?$_GET['codernik']:NULL),30);
                echo "<input type=hidden name=codernik  value=$codernik>";           
        ?>
        <div style="width: 100%; height: 90%; overflow: auto;">
        <?php
            $BtnCari=isset($_POST['BtnCari'])?$_POST['BtnCari']:NULL;
            if (isset($BtnCari)) {      
                    $tahunawal      = validTeks4(trim($_POST['tahunawal']),4);
                    $bulanawal      = validTeks4(trim($_POST['bulanawal']),2);
                    $tanggalawal    = validTeks4(trim($_POST['tanggalawal']),2);
                    $tahunakhir     = validTeks4(trim($_POST['tahunakhir']),4);
                    $bulanakhir     = validTeks4(trim($_POST['bulanakhir']),2);
                    $tanggalakhir   = validTeks4(trim($_POST['tanggalakhir']),2);
                    $codernik       = validTeks4(trim($_POST['codernik']),30); 
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
            
            $_sql = "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.klsnaik,
                    bridging_sep.tglsep,bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,bridging_sep.asal_rujukan,
                    bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,bridging_sep.jnspelayanan,
                    if(bridging_sep.jnspelayanan='1','1. Rawat Inap','2. Rawat Jalan') as jenispelayanan,bridging_sep.catatan,bridging_sep.diagawal,
                    bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,bridging_sep.klsrawat,
                    if(bridging_sep.klsrawat='1','1. Kelas 1',if(bridging_sep.klsrawat='2','2. Kelas 2','3. Kelas 3')) as kelas,
                    if(bridging_sep.lakalantas='1','1. Kasus Kecelakaan','2. Bukan Kasus Kecelakaan') as lakalantas,bridging_sep.user, 
                    bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu, 
                    if(bridging_sep.tglpulang='0000-00-00 00:00:00',concat(bridging_sep.tglsep,' 23:00:00'),bridging_sep.tglpulang) as tglpulang  
                    from bridging_sep where bridging_sep.tglsep between '".$tahunawal."-".$bulanawal."-".$tanggalawal." 00:00:00' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir." 23:59:59' order by bridging_sep.tglsep";
            $hasil=bukaquery($_sql);
            
            $_sqlinternal = "select bridging_sep_internal.no_sep, bridging_sep_internal.no_rawat,bridging_sep_internal.nomr,bridging_sep_internal.nama_pasien,bridging_sep_internal.klsnaik,
                    bridging_sep_internal.tglsep,bridging_sep_internal.tglrujukan,bridging_sep_internal.no_rujukan,bridging_sep_internal.kdppkrujukan,bridging_sep_internal.asal_rujukan,
                    bridging_sep_internal.nmppkrujukan,bridging_sep_internal.kdppkpelayanan,bridging_sep_internal.nmppkpelayanan,bridging_sep_internal.jnspelayanan,
                    if(bridging_sep_internal.jnspelayanan='1','1. Rawat Inap','2. Rawat Jalan') as jenispelayanan,bridging_sep_internal.catatan,bridging_sep_internal.diagawal,
                    bridging_sep_internal.nmdiagnosaawal,bridging_sep_internal.kdpolitujuan,bridging_sep_internal.nmpolitujuan,bridging_sep_internal.klsrawat,
                    if(bridging_sep_internal.klsrawat='1','1. Kelas 1',if(bridging_sep_internal.klsrawat='2','2. Kelas 2','3. Kelas 3')) as kelas,
                    if(bridging_sep_internal.lakalantas='1','1. Kasus Kecelakaan','2. Bukan Kasus Kecelakaan') as lakalantas,bridging_sep_internal.user, 
                    bridging_sep_internal.tanggal_lahir,bridging_sep_internal.peserta,bridging_sep_internal.jkel,bridging_sep_internal.no_kartu, 
                    if(bridging_sep_internal.tglpulang='0000-00-00 00:00:00',concat(bridging_sep_internal.tglsep,' 23:00:00'),bridging_sep_internal.tglpulang) as tglpulang  
                    from bridging_sep_internal where bridging_sep_internal.tglsep between '".$tahunawal."-".$bulanawal."-".$tanggalawal." 00:00:00' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir." 23:59:59' order by bridging_sep_internal.tglsep";
            $hasilinternal=bukaquery($_sqlinternal);
            
            $jumlah=mysqli_num_rows($hasil)+mysqli_num_rows($hasilinternal);
            if($jumlah!=0) {
                echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head2'>
                            <td width='10%'><div align='center'>No.Rawat</div></td>
                            <td width='10%'><div align='center'>No.SEP</div></td>
                            <td width='10%'><div align='center'>Tanggal SEP</div></td>
                            <td width='9%'><div align='center'>No.Kartu</div></td>
                            <td width='7%'><div align='center'>No.RM</div></td>
                            <td width='18%'><div align='center'>Nama Pasien</div></td>
                            <td width='7%'><div align='center'>Tgl.Lahir</div></td>
                            <td width='7%'><div align='center'>J.Kel</div></td>
                            <td width='9%'><div align='center'>Peserta</div></td>
                            <td width='13%'><div align='center'>Status Data</div></td>
                        </tr>";
                        while($baris = mysqli_fetch_array($hasil)) {
                            $status="";
                            if(getOne("select count(inacbg_data_terkirim.no_sep) from inacbg_data_terkirim where inacbg_data_terkirim.no_sep='".$baris["no_sep"]."'")>0){
                                $status="Terkirim INACBG";
                            }
                            $gender = "";
                            if($baris["jkel"]=="L"){
                                $gender="1";
                            }else{
                                $gender="2";
                            }

                            $prosedur="";
                            $a=1;
                            $hasilprosedur=bukaquery("select prosedur_pasien.kode,prosedur_pasien.jumlah from prosedur_pasien where prosedur_pasien.no_rawat='".$baris["no_rawat"]."' order by prosedur_pasien.prioritas asc");
                            while($barisprosedur = mysqli_fetch_array($hasilprosedur)) {
                                if($a==1){
                                    $prosedur=$barisprosedur["kode"].str_replace("+1","","+".$barisprosedur["jumlah"]);
                                }else{
                                    $prosedur=$prosedur."#".$barisprosedur["kode"].str_replace("+1","","+".$barisprosedur["jumlah"]);
                                }                
                                $a++;
                            }            

                            $penyakit="";
                            $a=1;
                            $hasilpenyakit=bukaquery("select diagnosa_pasien.kd_penyakit from diagnosa_pasien where diagnosa_pasien.no_rawat='".$baris["no_rawat"]."' order by diagnosa_pasien.prioritas asc");
                            while($barispenyakit = mysqli_fetch_array($hasilpenyakit)) {
                                if($a==1){
                                    $penyakit=$barispenyakit["kd_penyakit"];
                                }else{
                                    $penyakit=$penyakit."#".$barispenyakit["kd_penyakit"];
                                }                
                                $a++;
                            } 
                            
                            $procedureinacbg="";
                            $a=1;
                            $hasilprosedur=bukaquery("select prosedur_pasien.kode,prosedur_pasien.jumlah from prosedur_pasien inner join icd9 on prosedur_pasien.kode=icd9.kode where icd9.im='0' and prosedur_pasien.no_rawat='".$baris["no_rawat"]."' order by prosedur_pasien.prioritas asc");
                            while($barisprosedur = mysqli_fetch_array($hasilprosedur)) {
                                if($a==1){
                                    $procedureinacbg=$barisprosedur["kode"].str_replace("+1","","+".$barisprosedur["jumlah"]);
                                }else{
                                    $procedureinacbg=$procedureinacbg."#".$barisprosedur["kode"].str_replace("+1","","+".$barisprosedur["jumlah"]);
                                }                
                                $a++;
                            }            

                            $diagnosainacbg="";
                            $a=1;
                            $hasilpenyakit=bukaquery("select diagnosa_pasien.kd_penyakit from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit where penyakit.im='0' and diagnosa_pasien.no_rawat='".$baris["no_rawat"]."' order by diagnosa_pasien.prioritas asc");
                            while($barispenyakit = mysqli_fetch_array($hasilpenyakit)) {
                                if($a==1){
                                    $diagnosainacbg=$barispenyakit["kd_penyakit"];
                                }else{
                                    $diagnosainacbg=$diagnosainacbg."#".$barispenyakit["kd_penyakit"];
                                }                
                                $a++;
                            } 

                            $discharge_status="5";
                            if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Sembuh' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="1";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Sehat' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="1";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Rujuk' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="2";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='APS' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="3";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Pulang Paksa' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="3";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Meninggal' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="4";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='+' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="4";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Atas Persetujuan Dokter' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="1";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Atas Permintaan Sendiri' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="3";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Lain-lain' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="5";
                            }else{
                                $discharge_status="1";
                            }                  

                            echo "<tr class='isi' title='".$baris["no_rawat"].", ".$baris["no_sep"].", ".$baris["tglsep"].", ".$baris["no_kartu"].", ".$baris["nomr"].", ".$baris["nama_pasien"]."'>
                                    <td valign='top'>".$baris["no_rawat"]."</td>
                                    <td valign='top'>".$baris["no_sep"]."</td>
                                    <td valign='top' align='center'>".$baris["tglsep"]."</td>
                                    <td valign='top'>".$baris["no_kartu"]."</td>
                                    <td valign='top'>".$baris["nomr"]."</td>
                                    <td valign='top'>".$baris["nama_pasien"]."</td>
                                    <td valign='top' align='center'>".$baris["tanggal_lahir"]."</td>
                                    <td valign='top' align='center'>".$baris["jkel"]."</td>
                                    <td valign='top' align='center'>".$baris["peserta"]."</td>
                                    <td valign='top' align='center'>".$status."</td>
                                 </tr>";

                            $nm_dokter2="";
                            $a=1;
                            $hasildokter=bukaquery("select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat='".$baris["no_rawat"]."'");
                            while($barisdokter = mysqli_fetch_array($hasildokter)) {
                                if($a==1){
                                    $nm_dokter2=$barisdokter["nm_dokter"];
                                }else{
                                    $nm_dokter2=$nm_dokter2."#".$barisdokter["nm_dokter"];
                                }                
                                $a++;
                            } 

                            $nm_dokter    = "";
                            if(!empty($nm_dokter2)){
                                $nm_dokter = $nm_dokter2;
                            }else{
                                $nm_dokter = getOne("select dokter.nm_dokter from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='".$baris["no_rawat"]."'");
                            }
                            
                            $naikkelas= $baris["klsnaik"];
                            $asalrujukan= $baris["asal_rujukan"];
                            if($asalrujukan=="1. Faskes 1"){
                                $asalrujukan="gp";
                            }else if($asalrujukan=="2. Faskes 2(RS)"){
                                $asalrujukan="hosp-trans";
                            }else{
                                $asalrujukan="other";
                            }

                            $upgrade_class_ind="0";
                            if(!empty($naikkelas)){
                                $upgrade_class_ind="1";
                                if($naikkelas=="1"){
                                    $naikkelas="vvip";
                                }else if($naikkelas=="2"){
                                    $naikkelas="vip";
                                }else if($naikkelas=="3"){
                                    $naikkelas="kelas_1";
                                }else if($naikkelas=="4"){
                                    $naikkelas="kelas_2";
                                }else{
                                    $naikkelas="";
                                }   
                            }else{
                                $naikkelas="";
                            }
                            
                            $sistole="120";
                            $diastole="90";
                            if($baris["jnspelayanan"]=="1"){
                                $tensi=explode("/", getOne("select pemeriksaan_ranap.tensi from pemeriksaan_ranap where pemeriksaan_ranap.no_rawat='".$baris["no_rawat"]."' order by pemeriksaan_ranap.tgl_perawatan desc,pemeriksaan_ranap.jam_rawat desc"));
                                if(!empty($tensi[0])){
                                    $sistole=$tensi[0];
                                }
                                if(!empty($tensi[1])){
                                    $diastole=$tensi[1];
                                }
                            }else{
                                $tensi=explode("/", getOne("select pemeriksaan_ralan.tensi from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='".$baris["no_rawat"]."' order by pemeriksaan_ralan.tgl_perawatan desc,pemeriksaan_ralan.jam_rawat desc"));
                                if(!empty($tensi[0])){
                                    $sistole=$tensi[0];
                                }
                                if(!empty($tensi[1])){
                                    $diastole=$tensi[1];
                                }
                            }

                            BuatKlaimBaru($baris["no_kartu"],$baris["no_sep"],$baris["nomr"],$baris["nama_pasien"],$baris["tanggal_lahir"]." 00:00:00", $gender);
                            EditUlangKlaim($baris["no_sep"]);
                            UpdateDataKlaim($baris["no_sep"],$baris["no_kartu"],$baris["tglsep"]." ".getOne("select reg_periksa.jam_reg from reg_periksa where reg_periksa.no_rawat='".$baris["no_rawat"]."'"),$baris["tglpulang"],$baris["jnspelayanan"],$baris["klsrawat"],"","","","","",$upgrade_class_ind,$naikkelas,"","",getOne("select pasien_bayi.berat_badan from pasien_bayi where pasien_bayi.no_rkm_medis='".$baris["nomr"]."'"),$discharge_status,$penyakit,$prosedur,$diagnosainacbg,$procedureinacbg, getOne("select biaya_reg from reg_periksa where no_rawat='".$baris["no_rawat"]."'"), $nm_dokter,getKelasRS(),"","","#",$codernik,$baris["no_rawat"],$sistole,$diastole,$asalrujukan);
                        }
                        
                        while($baris = mysqli_fetch_array($hasilinternal)) {
                            $status="";
                            if(getOne("select count(inacbg_data_terkirim_internal.no_sep) from inacbg_data_terkirim_internal where inacbg_data_terkirim_internal.no_sep='".$baris["no_sep"]."'")>0){
                                $status="Terkirim INACBG";
                            }
                            $gender = "";
                            if($baris["jkel"]=="L"){
                                $gender="1";
                            }else{
                                $gender="2";
                            }

                            $prosedur="";
                            $a=1;
                            $hasilprosedur=bukaquery("select prosedur_pasien.kode,prosedur_pasien.jumlah from prosedur_pasien where prosedur_pasien.no_rawat='".$baris["no_rawat"]."' order by prosedur_pasien.prioritas asc");
                            while($barisprosedur = mysqli_fetch_array($hasilprosedur)) {
                                if($a==1){
                                    $prosedur=$barisprosedur["kode"].str_replace("+1","","+".$barisprosedur["jumlah"]);
                                }else{
                                    $prosedur=$prosedur."#".$barisprosedur["kode"].str_replace("+1","","+".$barisprosedur["jumlah"]);
                                }                
                                $a++;
                            }            

                            $penyakit="";
                            $a=1;
                            $hasilpenyakit=bukaquery("select diagnosa_pasien.kd_penyakit from diagnosa_pasien where diagnosa_pasien.no_rawat='".$baris["no_rawat"]."' order by diagnosa_pasien.prioritas asc");
                            while($barispenyakit = mysqli_fetch_array($hasilpenyakit)) {
                                if($a==1){
                                    $penyakit=$barispenyakit["kd_penyakit"];
                                }else{
                                    $penyakit=$penyakit."#".$barispenyakit["kd_penyakit"];
                                }                
                                $a++;
                            } 
                            
                            $procedureinacbg="";
                            $a=1;
                            $hasilprosedur=bukaquery("select prosedur_pasien.kode,prosedur_pasien.jumlah from prosedur_pasien inner join icd9 on prosedur_pasien.kode=icd9.kode where icd9.im='0' and prosedur_pasien.no_rawat='".$baris["no_rawat"]."' order by prosedur_pasien.prioritas asc");
                            while($barisprosedur = mysqli_fetch_array($hasilprosedur)) {
                                if($a==1){
                                    $procedureinacbg=$barisprosedur["kode"].str_replace("+1","","+".$barisprosedur["jumlah"]);
                                }else{
                                    $procedureinacbg=$procedureinacbg."#".$barisprosedur["kode"].str_replace("+1","","+".$barisprosedur["jumlah"]);
                                }                
                                $a++;
                            }            

                            $diagnosainacbg="";
                            $a=1;
                            $hasilpenyakit=bukaquery("select diagnosa_pasien.kd_penyakit from diagnosa_pasien inner join penyakit on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit where penyakit.im='0' and diagnosa_pasien.no_rawat='".$baris["no_rawat"]."' order by diagnosa_pasien.prioritas asc");
                            while($barispenyakit = mysqli_fetch_array($hasilpenyakit)) {
                                if($a==1){
                                    $diagnosainacbg=$barispenyakit["kd_penyakit"];
                                }else{
                                    $diagnosainacbg=$diagnosainacbg."#".$barispenyakit["kd_penyakit"];
                                }                
                                $a++;
                            } 

                            $discharge_status="5";
                            if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Sembuh' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="1";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Sehat' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="1";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Rujuk' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="2";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='APS' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="3";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Pulang Paksa' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="3";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Meninggal' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="4";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='+' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="4";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Atas Persetujuan Dokter' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="1";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Atas Permintaan Sendiri' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="3";
                            }else if(getOne("select count(kamar_inap.no_rawat) from kamar_inap where kamar_inap.stts_pulang='Lain-lain' and kamar_inap.no_rawat='".$baris["no_rawat"]."'")>0){
                                $discharge_status="5";
                            }else{
                                $discharge_status="1";
                            }                  

                            echo "<tr class='isi' title='".$baris["no_rawat"].", ".$baris["no_sep"].", ".$baris["tglsep"].", ".$baris["no_kartu"].", ".$baris["nomr"].", ".$baris["nama_pasien"]."'>
                                    <td valign='top'>".$baris["no_rawat"]."</td>
                                    <td valign='top'>".$baris["no_sep"]."</td>
                                    <td valign='top' align='center'>".$baris["tglsep"]."</td>
                                    <td valign='top'>".$baris["no_kartu"]."</td>
                                    <td valign='top'>".$baris["nomr"]."</td>
                                    <td valign='top'>".$baris["nama_pasien"]."</td>
                                    <td valign='top' align='center'>".$baris["tanggal_lahir"]."</td>
                                    <td valign='top' align='center'>".$baris["jkel"]."</td>
                                    <td valign='top' align='center'>".$baris["peserta"]."</td>
                                    <td valign='top' align='center'>".$status."</td>
                                 </tr>";

                            $nm_dokter2="";
                            $a=1;
                            $hasildokter=bukaquery("select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat='".$baris["no_rawat"]."'");
                            while($barisdokter = mysqli_fetch_array($hasildokter)) {
                                if($a==1){
                                    $nm_dokter2=$barisdokter["nm_dokter"];
                                }else{
                                    $nm_dokter2=$nm_dokter2."#".$barisdokter["nm_dokter"];
                                }                
                                $a++;
                            } 

                            $nm_dokter    = "";
                            if(!empty($nm_dokter2)){
                                $nm_dokter = $nm_dokter2;
                            }else{
                                $nm_dokter = getOne("select dokter.nm_dokter from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='".$baris["no_rawat"]."'");
                            }
                            
                            $naikkelas= $baris["klsnaik"];
                            $asalrujukan= $baris["asal_rujukan"];
                            if($asalrujukan=="1. Faskes 1"){
                                $asalrujukan="gp";
                            }else if($asalrujukan=="2. Faskes 2(RS)"){
                                $asalrujukan="hosp-trans";
                            }else{
                                $asalrujukan="other";
                            }

                            $upgrade_class_ind="0";
                            if(!empty($naikkelas)){
                                $upgrade_class_ind="1";
                                if($naikkelas=="1"){
                                    $naikkelas="vvip";
                                }else if($naikkelas=="2"){
                                    $naikkelas="vip";
                                }else if($naikkelas=="3"){
                                    $naikkelas="kelas_1";
                                }else if($naikkelas=="4"){
                                    $naikkelas="kelas_2";
                                }else{
                                    $naikkelas="";
                                }   
                            }else{
                                $naikkelas="";
                            }
                            
                            $sistole="120";
                            $diastole="90";
                            if($baris["jnspelayanan"]=="1"){
                                $tensi=explode("/", getOne("select pemeriksaan_ranap.tensi from pemeriksaan_ranap where pemeriksaan_ranap.no_rawat='".$baris["no_rawat"]."' order by pemeriksaan_ranap.tgl_perawatan desc,pemeriksaan_ranap.jam_rawat desc"));
                                if(!empty($tensi[0])){
                                    $sistole=$tensi[0];
                                }
                                if(!empty($tensi[1])){
                                    $diastole=$tensi[1];
                                }
                            }else{
                                $tensi=explode("/", getOne("select pemeriksaan_ralan.tensi from pemeriksaan_ralan where pemeriksaan_ralan.no_rawat='".$baris["no_rawat"]."' order by pemeriksaan_ralan.tgl_perawatan desc,pemeriksaan_ralan.jam_rawat desc"));
                                if(!empty($tensi[0])){
                                    $sistole=$tensi[0];
                                }
                                if(!empty($tensi[1])){
                                    $diastole=$tensi[1];
                                }
                            }

                            BuatKlaimBaruInternal($baris["no_kartu"],$baris["no_sep"],$baris["nomr"],$baris["nama_pasien"],$baris["tanggal_lahir"]." 00:00:00", $gender);
                            EditUlangKlaim($baris["no_sep"]);
                            UpdateDataKlaimInternal($baris["no_sep"],$baris["no_kartu"],$baris["tglsep"]." ".getOne("select reg_periksa.jam_reg from reg_periksa where reg_periksa.no_rawat='".$baris["no_rawat"]."'"),$baris["tglpulang"],$baris["jnspelayanan"],$baris["klsrawat"],"","","","","",$upgrade_class_ind,$naikkelas,"","",getOne("select berat_badan from pasien_bayi where no_rkm_medis='".$baris["nomr"]."'"),$discharge_status,$penyakit,$prosedur,$diagnosainacbg,$procedureinacbg, getOne("select biaya_reg from reg_periksa where no_rawat='".$baris["no_rawat"]."'"), $nm_dokter,getKelasRS(),"","","#",$codernik,$baris["no_rawat"],$sistole,$diastole,$asalrujukan);
                        }
                echo "</table>";           
            }else{
                echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head2'>
                            <td width='10%'><div align='center'>No.Rawat</div></td>
                            <td width='10%'><div align='center'>No.SEP</div></td>
                            <td width='10%'><div align='center'>Tanggal SEP</div></td>
                            <td width='9%'><div align='center'>No.Kartu</div></td>
                            <td width='7%'><div align='center'>No.RM</div></td>
                            <td width='18%'><div align='center'>Nama Pasien</div></td>
                            <td width='7%'><div align='center'>Tgl.Lahir</div></td>
                            <td width='7%'><div align='center'>J.Kel</div></td>
                            <td width='9%'><div align='center'>Peserta</div></td>
                            <td width='13%'><div align='center'>Status Data</div></td>
                        </tr>
                       </table>";
            }   

            $BtnKeluar=isset($_POST['BtnKeluar'])?$_POST['BtnKeluar']:NULL;
            if (isset($BtnKeluar)) {
                echo"<meta http-equiv='refresh' content='1;URL=?act=KlaimBaruOtomatis&action=Keluar'>";
            }

        ?>
        </div>
            <table width="100%" align="center" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr class="head3">					
                    <td width="490px">
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
                        &nbsp;&nbsp;s.d.&nbsp;&nbsp;                        
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
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;" />
                    </td>
                    <td width="140px" >Record : <?php echo $jumlah; ?> </td>
                    <td><input name=BtnKeluar type=submit class="button" value="&nbsp;&nbsp;&nbsp;Keluar&nbsp;&nbsp;&nbsp;" /> </td>
                </tr>
            </table>
	</form>
    </div>
</div>
<?php 
   echo "<meta http-equiv='refresh' content='600;URL=?act=KlaimBaruOtomatis&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik'>";
?>

