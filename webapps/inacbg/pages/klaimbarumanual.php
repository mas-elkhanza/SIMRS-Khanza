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
                $no_sep         = validTeks4((isset($_GET['no_sep'])?$_GET['no_sep']:NULL),30);
                $norawat        = validTeks4((isset($_GET['norawat'])?$_GET['norawat']:NULL),20);
                $codernik       = validTeks4((isset($_GET['codernik'])?$_GET['codernik']:NULL),30);
                $keyword        = validTeks4((isset($_GET['keyword'])?$_GET['keyword']:NULL),20);
                $statuskirim    = validTeks(str_replace("_"," ",isset($_GET['statuskirim']))?str_replace("_"," ",$_GET['statuskirim']):NULL);
                echo "<input type=hidden name=codernik  value=$codernik><input type=hidden name=keyword value=$keyword>";
        ?>
        <div style="width: 100%; height: 85%; overflow: auto;">
        <?php
            $BtnCari  = isset($_POST['BtnCari'])?$_POST['BtnCari']:NULL;
            $keyword  = isset($_POST['keyword'])?trim($_POST['keyword']):NULL;
            $keyword  = validTeks4($keyword,20);
            if (isset($BtnCari)) {      
                    $tahunawal      = validTeks4(trim($_POST['tahunawal']),4);
                    $bulanawal      = validTeks4(trim($_POST['bulanawal']),2);
                    $tanggalawal    = validTeks4(trim($_POST['tanggalawal']),2);
                    $tahunakhir     = validTeks4(trim($_POST['tahunakhir']),4);
                    $bulanakhir     = validTeks4(trim($_POST['bulanakhir']),2);
                    $tanggalakhir   = validTeks4(trim($_POST['tanggalakhir']),2);
                    $codernik       = validTeks4(trim($_POST['codernik']),30);      
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
            $_sql = "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,
                    bridging_sep.tglsep,bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,
                    bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,reg_periksa.kd_dokter,dokter.nm_dokter,
                    if(bridging_sep.jnspelayanan='1','1. Rawat Inap','2. Rawat Jalan') as jenispelayanan,bridging_sep.catatan,bridging_sep.diagawal,
                    bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,
                    if(bridging_sep.klsrawat='1','1. Kelas 1',if(bridging_sep.klsrawat='2','2. Kelas 2','3. Kelas 3')) as kelas,
                    if(bridging_sep.lakalantas='0','2. Bukan Kasus Kecelakaan','1. Kasus Kecelakaan') as lakalantas,bridging_sep.user, 
                    bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.tglpulang 
                    from bridging_sep inner join reg_periksa on reg_periksa.no_rawat=bridging_sep.no_rawat
                    inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where 
                    bridging_sep.tglsep between '".$tahunawal."-".$bulanawal."-".$tanggalawal." 00:00:00' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir." 23:59:59' and
                    (bridging_sep.no_sep like '%".$keyword."%' or bridging_sep.nomr like '%".$keyword."%' or bridging_sep.nama_pasien like '%".$keyword."%' or 
                    bridging_sep.no_rawat like '%".$keyword."%' or bridging_sep.no_kartu like '%".$keyword."%') order by bridging_sep.tglsep";
            $hasil=bukaquery($_sql);
            
            $_sqlinternal = "select bridging_sep_internal.no_sep, bridging_sep_internal.no_rawat,bridging_sep_internal.nomr,bridging_sep_internal.nama_pasien,
                    bridging_sep_internal.tglsep,bridging_sep_internal.tglrujukan,bridging_sep_internal.no_rujukan,bridging_sep_internal.kdppkrujukan,
                    bridging_sep_internal.nmppkrujukan,bridging_sep_internal.kdppkpelayanan,bridging_sep_internal.nmppkpelayanan,reg_periksa.kd_dokter,dokter.nm_dokter,
                    if(bridging_sep_internal.jnspelayanan='1','1. Rawat Inap','2. Rawat Jalan') as jenispelayanan,bridging_sep_internal.catatan,bridging_sep_internal.diagawal,
                    bridging_sep_internal.nmdiagnosaawal,bridging_sep_internal.kdpolitujuan,bridging_sep_internal.nmpolitujuan,
                    if(bridging_sep_internal.klsrawat='1','1. Kelas 1',if(bridging_sep_internal.klsrawat='2','2. Kelas 2','3. Kelas 3')) as kelas,
                    if(bridging_sep_internal.lakalantas='0','2. Bukan Kasus Kecelakaan','1. Kasus Kecelakaan') as lakalantas,bridging_sep_internal.user, 
                    bridging_sep_internal.tanggal_lahir,bridging_sep_internal.peserta,bridging_sep_internal.jkel,bridging_sep_internal.no_kartu,bridging_sep_internal.tglpulang 
                    from bridging_sep_internal inner join reg_periksa on reg_periksa.no_rawat=bridging_sep_internal.no_rawat
                    inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where 
                    bridging_sep_internal.tglsep between '".$tahunawal."-".$bulanawal."-".$tanggalawal." 00:00:00' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir." 23:59:59' and 
                    (bridging_sep_internal.no_sep like '%".$keyword."%' or bridging_sep_internal.nomr like '%".$keyword."%' or bridging_sep_internal.nama_pasien like '%".$keyword."%' or
                    bridging_sep_internal.no_rawat like '%".$keyword."%' or bridging_sep_internal.no_kartu like '%".$keyword."%') order by bridging_sep_internal.tglsep";
            $hasilinternal=bukaquery($_sqlinternal);
            
            $jumlah=0;
            if((mysqli_num_rows($hasil)+mysqli_num_rows($hasilinternal))!=0) {
                echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head2'>
                            <td width='22%'><div align='center'>SEP</div></td>
                            <td width='23%'><div align='center'>Pasien</div></td>
                            <td width='23%'><div align='center'>Dokter</div></td>
                            <td width='22%'><div align='center'>Diagnosa & Prosedur</div></td>
                            <td width='10%'><div align='center'>Status Data</div></td>
                        </tr>";
                        while($baris = mysqli_fetch_array($hasil)) {
                            $statuskirim =str_replace(" ","_",$statuskirim)?str_replace(" ","_",$statuskirim):NULL;
                            $status="<a href='?act=KlaimBaruManual&action=Kirim&no_sep=".$baris["no_sep"]."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&statuskirim=$statuskirim'>[Kirim]</a>";
                            $statusdata="Belum Terkirim";
                            if(getOne("select count(inacbg_klaim_baru.no_sep) from inacbg_klaim_baru where inacbg_klaim_baru.no_sep='".$baris["no_sep"]."'")>0){
                                $status="<a href='?act=KlaimBaruManual&action=Kirim&no_sep=".$baris["no_sep"]."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&statuskirim=$statuskirim'>[Kirim Ulang]</a>";
                                $statusdata="Sudah Terkirim";
                            }
                            $statuskirim =str_replace("_"," ",$statuskirim)?str_replace("_"," ",$statuskirim):NULL;
                            if(($statuskirim=="Semua")||($statuskirim==$statusdata)){
                                echo "<tr class='isi' title='".$baris["no_rawat"].", ".$baris["no_sep"].", ".$baris["tglsep"].", ".$baris["no_kartu"].", ".$baris["nomr"].", ".$baris["nama_pasien"]."'>
                                        <td valign='top'>Tgl.SEP : ".$baris["tglsep"]."<br>No.SEP : ".$baris["no_sep"]."<br>No.Kartu : ".$baris["no_kartu"]."</td>
                                        <td valign='top'>No.Rawat : ".$baris["no_rawat"]."<br>No.MR : ".$baris["nomr"]."<br>Nama Pasien : ".$baris["nama_pasien"]."</td>
                                        <td bgcolor='#FFFFFF' valign='top'>".$baris["nm_dokter"]."<br>Status : ".$baris["jenispelayanan"]."<br>Ruang : ".$baris["nmpolitujuan"]."</td>
                                        <td valign='top'>";
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
                                        <td valign='top' align='center'><a href='?act=KlaimBaruManual&action=InputDiagnosa&norawat=".$baris["no_rawat"]."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&keyword=$keyword&statuskirim=$statuskirim'>[Input Diagnosa]</a><br>".$status."</td>
                                     </tr>";
                                 $jumlah++;
                            }
                        }
                        
                        while($baris = mysqli_fetch_array($hasilinternal)) {
                            $statuskirim =str_replace(" ","_",$statuskirim)?str_replace(" ","_",$statuskirim):NULL;
                            $status="<a href='?act=KlaimBaruManual&action=KirimInternal&no_sep=".$baris["no_sep"]."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&statuskirim=$statuskirim'>[Kirim]</a>";
                            $statusdata="Belum Terkirim";
                            if(getOne("select count(inacbg_klaim_baru_internal.no_sep) from inacbg_klaim_baru_internal where inacbg_klaim_baru_internal.no_sep='".$baris["no_sep"]."'")>0){
                                $status="<a href='?act=KlaimBaruManual&action=KirimInternal&no_sep=".$baris["no_sep"]."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&statuskirim=$statuskirim'>[Kirim Ulang]</a>";
                                $statusdata="Sudah Terkirim";
                            }
                            $statuskirim =str_replace("_"," ",$statuskirim)?str_replace("_"," ",$statuskirim):NULL;
                            if(($statuskirim=="Semua")||($statuskirim==$statusdata)){
                                echo "<tr class='isi' title='".$baris["no_rawat"].", ".$baris["no_sep"].", ".$baris["tglsep"].", ".$baris["no_kartu"].", ".$baris["nomr"].", ".$baris["nama_pasien"]."'>
                                        <td valign='top'>Tgl.SEP : ".$baris["tglsep"]."<br>No.SEP : ".$baris["no_sep"]."<br>No.Kartu : ".$baris["no_kartu"]."</td>
                                        <td valign='top'>No.Rawat : ".$baris["no_rawat"]."<br>No.MR : ".$baris["nomr"]."<br>Nama Pasien : ".$baris["nama_pasien"]."</td>
                                        <td bgcolor='#FFFFFF' valign='top'>".$baris["nm_dokter"]."<br>Status : ".$baris["jenispelayanan"]."<br>Ruang : ".$baris["nmpolitujuan"]."</td>
                                        <td valign='top'>";
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
                                        <td valign='top' align='center'><a href='?act=KlaimBaruManual&action=InputDiagnosa&norawat=".$baris["no_rawat"]."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&keyword=$keyword&statuskirim=$statuskirim'>[Input Diagnosa]</a><br>".$status."</td>
                                     </tr>";
                                     $jumlah++;
                            }
                        }
                echo "</table>";           
            }else{
                echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head2'>
                            <td width='22%'><div align='center'>SEP</div></td>
                            <td width='23%'><div align='center'>Pasien</div></td>
                            <td width='23%'><div align='center'>Dokter</div></td>
                            <td width='22%'><div align='center'>Diagnosa & Prosedur</div></td>
                            <td width='10%'><div align='center'>Status Data</div></td>
                        </tr>
                       </table>";
            }         

            if($action=="Kirim") {
                $_sql   = "select bridging_sep.no_kartu,bridging_sep.no_sep,bridging_sep.nomr,bridging_sep.nama_pasien,bridging_sep.tanggal_lahir,bridging_sep.jkel,bridging_sep.tglsep,if(bridging_sep.tglpulang='0000-00-00 00:00:00',now(),bridging_sep.tglpulang) as tglpulang,bridging_sep.jnspelayanan,bridging_sep.klsrawat,bridging_sep.no_rawat from bridging_sep where bridging_sep.no_sep='".$no_sep."'";
                $hasil  = bukaquery($_sql);
                $baris  = mysqli_fetch_array($hasil);
                $gender = "";
                if($baris["jkel"]=="L"){
                    $gender="1";
                }else{
                    $gender="2";
                }

                $prosedur="";
                $a=1;
                $hasilprosedur=bukaquery("select prosedur_pasien.kode from prosedur_pasien where prosedur_pasien.no_rawat='".$baris["no_rawat"]."' order by prosedur_pasien.prioritas asc");
                while($barisprosedur = mysqli_fetch_array($hasilprosedur)) {
                    if($a==1){
                        $prosedur=$barisprosedur["kode"];
                    }else{
                        $prosedur=$prosedur."#".$barisprosedur["kode"];
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
                    $nm_dokter=$nm_dokter2;
                }else{
                    $nm_dokter    = getOne("select dokter.nm_dokter from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='".$baris["no_rawat"]."'");
                }

                $naikkelas=getOne("select bridging_sep.klsnaik from bridging_sep where bridging_sep.no_rawat='$norawat'");
                
                $upgrade_class_ind="0";
                if(!empty($naikkelas)){
                    $upgrade_class_ind="1";
                    if($naikkelas=="1"){
                        $naikkelas="Kelas VVIP";
                    }else if($naikkelas=="2"){
                        $naikkelas="Kelas VIP";
                    }else if($naikkelas=="3"){
                        $naikkelas="Kelas 1";
                    }else if($naikkelas=="4"){
                        $naikkelas="Kelas 2";
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
                UpdateDataKlaim($baris["no_sep"],$baris["no_kartu"],$baris["tglsep"]." ".getOne("select reg_periksa.jam_reg from reg_periksa where reg_periksa.no_rawat='".$baris["no_rawat"]."'"),$baris["tglpulang"],$baris["jnspelayanan"],$baris["klsrawat"],"","","","","",$upgrade_class_ind,$naikkelas,"","",getOne("select berat_badan from pasien_bayi where no_rkm_medis='".$baris["nomr"]."'"),$discharge_status,$penyakit,$prosedur, getOne("select biaya_reg from reg_periksa where no_rawat='".$baris["no_rawat"]."'"), $nm_dokter,getKelasRS(),"","","#",$codernik,$baris["no_rawat"],$sistole,$diastole);

                echo "<meta http-equiv='refresh' content='1;URL=?act=KlaimBaruManual&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&action=no&keyword=$keyword&statuskirim=$statuskirim'>";
            }else if($action=="KirimInternal") {
                $_sql   = "select bridging_sep_internal.no_kartu,bridging_sep_internal.no_sep,bridging_sep_internal.nomr,bridging_sep_internal.nama_pasien,bridging_sep_internal.tanggal_lahir,bridging_sep_internal.jkel,bridging_sep_internal.tglsep,if(bridging_sep_internal.tglpulang='0000-00-00 00:00:00',now(),bridging_sep_internal.tglpulang) as tglpulang,bridging_sep_internal.jnspelayanan,bridging_sep_internal.klsrawat,bridging_sep_internal.no_rawat from bridging_sep_internal where bridging_sep_internal.no_sep='".$no_sep."'";
                $hasil  = bukaquery($_sql);
                $baris  = mysqli_fetch_array($hasil);
                $gender = "";
                if($baris["jkel"]=="L"){
                    $gender="1";
                }else{
                    $gender="2";
                }

                $prosedur="";
                $a=1;
                $hasilprosedur=bukaquery("select prosedur_pasien.kode from prosedur_pasien where prosedur_pasien.no_rawat='".$baris["no_rawat"]."' order by prosedur_pasien.prioritas asc");
                while($barisprosedur = mysqli_fetch_array($hasilprosedur)) {
                    if($a==1){
                        $prosedur=$barisprosedur["kode"];
                    }else{
                        $prosedur=$prosedur."#".$barisprosedur["kode"];
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
                    $nm_dokter=$nm_dokter2;
                }else{
                    $nm_dokter    = getOne("select dokter.nm_dokter from reg_periksa inner join dokter on reg_periksa.kd_dokter=dokter.kd_dokter where reg_periksa.no_rawat='".$baris["no_rawat"]."'");
                }

                $naikkelas=getOne("select bridging_sep_internal.klsnaik from bridging_sep_internal where bridging_sep_internal.no_rawat='$norawat'");
                
                $upgrade_class_ind="0";
                if(!empty($naikkelas)){
                    $upgrade_class_ind="1";
                    if($naikkelas=="1"){
                        $naikkelas="Kelas VVIP";
                    }else if($naikkelas=="2"){
                        $naikkelas="Kelas VIP";
                    }else if($naikkelas=="3"){
                        $naikkelas="Kelas 1";
                    }else if($naikkelas=="4"){
                        $naikkelas="Kelas 2";
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
                UpdateDataKlaimInternal($baris["no_sep"],$baris["no_kartu"],$baris["tglsep"]." ".getOne("select reg_periksa.jam_reg from reg_periksa where reg_periksa.no_rawat='".$baris["no_rawat"]."'"),$baris["tglpulang"],$baris["jnspelayanan"],$baris["klsrawat"],"","","","","",$upgrade_class_ind,$naikkelas,"","",getOne("select berat_badan from pasien_bayi where no_rkm_medis='".$baris["nomr"]."'"),$discharge_status,$penyakit,$prosedur, getOne("select biaya_reg from reg_periksa where no_rawat='".$baris["no_rawat"]."'"), $nm_dokter,getKelasRS(),"","","#",$codernik,$baris["no_rawat"],$sistole,$diastole);

                echo "<meta http-equiv='refresh' content='1;URL=?act=KlaimBaruManual&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&action=no&keyword=$keyword&statuskirim=$statuskirim'>";
            }else if($action=="InputDiagnosa") {
                HapusAll("temppanggilnorawat");
                InsertData2("temppanggilnorawat","'$norawat'");
                echo "<meta http-equiv='refresh' content='1;URL=?act=KlaimBaruManual&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik&action=no&keyword=$keyword&statuskirim=$statuskirim'>";
            }

            $BtnKeluar=isset($_POST['BtnKeluar'])?$_POST['BtnKeluar']:NULL;
            if (isset($BtnKeluar)) {
                echo"<meta http-equiv='refresh' content='1;URL=?act=KlaimBaruManual&action=Keluar'>";
            }

        ?>
        </div>
            <table width="100%" align="center" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr class="head3">					
                    <td width="100%">
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
                        &nbsp;&nbsp;&nbsp;
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
                        </select>
                    </td>
                </tr>
                <tr class="head3">					
                    <td width="100%">
                        Keyword : <input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="47" maxlength="20" pattern="[a-zA-Z0-9, ./@_]{1,20}" title=" a-zA-Z0-9, ./@_ (Maksimal 20 karakter)" autocomplete="off" autocomplete="off" autofocus />
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;" />&nbsp;&nbsp;&nbsp;
                        Record : <?php echo $jumlah; ?>&nbsp;&nbsp;&nbsp;
                        <input name=BtnKeluar type=submit class="button" value="&nbsp;&nbsp;&nbsp;Keluar&nbsp;&nbsp;&nbsp;" />
                    </td>
                </tr>
            </table>
	</form>
    </div>
</div>
