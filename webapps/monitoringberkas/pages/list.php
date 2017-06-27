<div id="post">
    <div class="entry">   
	<form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
                echo "";
                $tahunawal      =isset($_GET['tahunawal'])?$_GET['tahunawal']:NULL;
                $bulanawal      =isset($_GET['bulanawal'])?$_GET['bulanawal']:NULL;
                $tanggalawal    =isset($_GET['tanggalawal'])?$_GET['tanggalawal']:NULL;
                $tahunakhir     =isset($_GET['tahunakhir'])?$_GET['tahunakhir']:NULL;
                $bulanakhir     =isset($_GET['bulanakhir'])?$_GET['bulanakhir']:NULL;
                $tanggalakhir   =isset($_GET['tanggalakhir'])?$_GET['tanggalakhir']:NULL;  
                $action         =isset($_GET['action'])?$_GET['action']:NULL;
                $no_rawat       =isset($_GET['no_rawat'])?$_GET['no_rawat']:NULL;
                $keyword        =str_replace("_"," ",isset($_GET['keyword']))?str_replace("_"," ",$_GET['keyword']):NULL;
                $dokter         =str_replace("_"," ",isset($_GET['dokter']))?str_replace("_"," ",$_GET['dokter']):NULL;
                $poli           =str_replace("_"," ",isset($_GET['poli']))?str_replace("_"," ",$_GET['poli']):NULL;
                echo "<input type=hidden name=dokter value=$dokter><input type=hidden name=poli value=$poli><input type=hidden name=keyword value=$keyword>";
        ?>
    <div style="width: 100%; height: 85%; overflow: auto;">
    <?php        
	$BtnCari  =isset($_POST['BtnCari'])?$_POST['BtnCari']:NULL;
        if (isset($BtnCari)) {      
                $tahunawal      =trim($_POST['tahunawal']);
                $bulanawal      =trim($_POST['bulanawal']);
                $tanggalawal    =trim($_POST['tanggalawal']);
                $tahunakhir     =trim($_POST['tahunakhir']);
                $bulanakhir     =trim($_POST['bulanakhir']);
                $tanggalakhir   =trim($_POST['tanggalakhir']);    
                $dokter         =trim($_POST['dokter']);
                $keyword        =trim($_POST['keyword']);
                $poli           =trim($_POST['poli']);
                $action         ="no";
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
                reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab 
                from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab 
                on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli  where  
                poliklinik.nm_poli like '%".$poli."%' and  dokter.nm_dokter like '%".$dokter."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  reg_periksa.no_reg like '%".$keyword."%' or 
                poliklinik.nm_poli like '%".$poli."%' and  dokter.nm_dokter like '%".$dokter."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  reg_periksa.no_rawat like '%".$keyword."%' or 
                poliklinik.nm_poli like '%".$poli."%' and  dokter.nm_dokter like '%".$dokter."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  reg_periksa.tgl_registrasi like '%".$keyword."%' or
                poliklinik.nm_poli like '%".$poli."%' and  dokter.nm_dokter like '%".$dokter."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  reg_periksa.kd_dokter like '%".$keyword."%' or 
                poliklinik.nm_poli like '%".$poli."%' and  dokter.nm_dokter like '%".$dokter."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  dokter.nm_dokter like '%".$keyword."%' or 
                poliklinik.nm_poli like '%".$poli."%' and  dokter.nm_dokter like '%".$dokter."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  reg_periksa.no_rkm_medis like '%".$keyword."%' or 
                poliklinik.nm_poli like '%".$poli."%' and  dokter.nm_dokter like '%".$dokter."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  reg_periksa.stts_daftar like '%".$keyword."%' or 
                poliklinik.nm_poli like '%".$poli."%' and  dokter.nm_dokter like '%".$dokter."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  pasien.nm_pasien like '%".$keyword."%' or 
                poliklinik.nm_poli like '%".$poli."%' and  dokter.nm_dokter like '%".$dokter."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  poliklinik.nm_poli like '%".$keyword."%' or 
                poliklinik.nm_poli like '%".$poli."%' and  dokter.nm_dokter like '%".$dokter."%' and tgl_registrasi between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' and  penjab.png_jawab like '%".$keyword."%' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg desc ";
        $hasil=bukaquery($_sql);
        $jumlah=mysql_num_rows($hasil);
        if(mysql_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head2'>
                        <td width='6%'><div align='center'>No.RM</div></td>
                        <td width='38%'><div align='center'>Data Pasien</div></td>
                        <td width='20%'><div align='center'>Registrasi</div></td>
                        <td width='25%'><div align='center'>Dokter</div></td>
                        <td width='10%'><div align='center'>Proses</div></td>
                    </tr>";
                    while($baris = mysql_fetch_array($hasil)) {
                        $status="";
                        if(getOne("select count(no_rawat) from mutasi_berkas where no_rawat='".$baris["no_rawat"]."'")==0){
                            if($baris["stts_daftar"]=="Lama"){
                                echo "<tr class='isi7'>
                                        <td bgcolor='#FFFFA9' valign='center'>".$baris["no_rkm_medis"]."</td>
                                        <td bgcolor='#FFFFA9' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                        <td bgcolor='#FFFFA9' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                        <td bgcolor='#FFFFA9' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                        <td valign='center' align='center'>
                                            <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=CetakBerkasRM&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Cetak RM]</a><br>
                                            <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kirim&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kirim]</a><br>
                                            <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>                                                
                                        </td>                                
                                     </tr>";
                            }else if($baris["stts_daftar"]=="Baru"){
                                echo "<tr class='isi7'>
                                        <td bgcolor='#FACC2E' valign='center'>".$baris["no_rkm_medis"]."</td>
                                        <td bgcolor='#FFFFA9' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                        <td bgcolor='#FFFFA9' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                        <td bgcolor='#FFFFA9' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                        <td valign='center' align='center'>
                                            <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=CetakBerkasRM&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Cetak RM]</a><br>
                                            <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kirim&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kirim]</a><br>
                                            <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                        </td>  
                                    </tr>";
                            }else if($baris["stts_daftar"]=="-"){
                                echo "<tr class='isi7'>
                                        <td bgcolor='#FFFFA9' valign='center'>".$baris["no_rkm_medis"]."</td>
                                        <td bgcolor='#FFFFA9' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                        <td bgcolor='#FFFFA9' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                        <td bgcolor='#FFFFA9' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                        <td valign='center' align='center'>
                                            <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=CetakBerkasRM&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Cetak RM]</a><br>
                                            <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kirim&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kirim]</a><br>
                                            <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                        </td>                                
                                     </tr>";
                            }
                        }else{
                            $status= getOne("select status from mutasi_berkas where no_rawat='".$baris["no_rawat"]."'");
                            if($status=="Sudah Dikirim"){
                                if($baris["stts_daftar"]=="Lama"){
                                    echo "<tr class='isi7'>
                                            <td bgcolor='#74DF00' valign='center'>".$baris["no_rkm_medis"]."</td>
                                            <td bgcolor='#74DF00' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                            <td bgcolor='#74DF00' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                            <td bgcolor='#74DF00' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                            <td valign='center' align='center'>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kembali&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kembali]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Permintaan&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Permintaan]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                            </td>  
                                        </tr>";  
                                } else if($baris["stts_daftar"]=="Baru"){
                                    echo "<tr class='isi7'>
                                            <td bgcolor='#FACC2E' valign='center'>".$baris["no_rkm_medis"]."</td>
                                            <td bgcolor='#74DF00' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                            <td bgcolor='#74DF00' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                            <td bgcolor='#74DF00' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                            <td valign='center' align='center'>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kembali&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kembali]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Permintaan&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Permintaan]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                            </td>  
                                        </tr>";  
                                } else if($baris["stts_daftar"]=="-"){
                                    echo "<tr class='isi7'>
                                            <td bgcolor='#74DF00' valign='center'>".$baris["no_rkm_medis"]."</td>
                                            <td bgcolor='#74DF00' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                            <td bgcolor='#74DF00' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                            <td bgcolor='#74DF00' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                            <td valign='center' align='center'>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kembali&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kembali]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Permintaan&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Permintaan]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                            </td>  
                                        </tr>";  
                                }
                                                                  
                            }else if($status=="Sudah Diterima"){
                                if($baris["stts_daftar"]=="Lama"){
                                    echo "<tr class='isi7'>
                                           <td bgcolor='#CECEF6' valign='center'>".$baris["no_rkm_medis"]."</td>
                                           <td bgcolor='#CECEF6' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                           <td bgcolor='#CECEF6' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                           <td bgcolor='#CECEF6' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                           <td valign='center' align='center'>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kembali&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kembali]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Permintaan&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Permintaan]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                           </td>  
                                       </tr>";
                                } else if($baris["stts_daftar"]=="Baru"){
                                    echo "<tr class='isi7'>
                                           <td bgcolor='#FACC2E' valign='center'>".$baris["no_rkm_medis"]."</td>
                                           <td bgcolor='#CECEF6' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                           <td bgcolor='#CECEF6' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                           <td bgcolor='#CECEF6' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                           <td valign='center' align='center'>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kembali&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kembali]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Permintaan&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Permintaan]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                           </td>   
                                       </tr>"; 
                                } else if($baris["stts_daftar"]=="-"){
                                    echo "<tr class='isi7'>
                                           <td bgcolor='#CECEF6' valign='center'>".$baris["no_rkm_medis"]."</td>
                                           <td bgcolor='#CECEF6' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                           <td bgcolor='#CECEF6' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                           <td bgcolor='#CECEF6' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                           <td valign='center' align='center'>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kembali&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kembali]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Permintaan&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Permintaan]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                           </td>  
                                       </tr>";
                                }
                            }else if($status=="Sudah Kembali"){
                                if($baris["stts_daftar"]=="Lama"){
                                    echo "<tr class='isi7'>
                                           <td bgcolor='#FFFFFF' valign='center'>".$baris["no_rkm_medis"]."</td>
                                           <td bgcolor='#FFFFFF' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                           <td bgcolor='#FFFFFF' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                           <td bgcolor='#FFFFFF' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                           <td valign='center' align='center'>
                                                
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kembali&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kembali]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Permintaan&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Permintaan]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                           </td>  
                                       </tr>";
                                } else if($baris["stts_daftar"]=="Baru"){
                                    echo "<tr class='isi7'>
                                           <td bgcolor='#FACC2E' valign='center'>".$baris["no_rkm_medis"]."</td>
                                           <td bgcolor='#FFFFFF' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                           <td bgcolor='#FFFFFF' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                           <td bgcolor='#FFFFFF' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                           <td valign='center' align='center'>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kembali&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kembali]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Permintaan&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Permintaan]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                           </td>  
                                       </tr>";
                                } else if($baris["stts_daftar"]=="-"){
                                    echo "<tr class='isi7'>
                                           <td bgcolor='#FFFFFF' valign='center'>".$baris["no_rkm_medis"]."</td>
                                           <td bgcolor='#FFFFFF' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                           <td bgcolor='#FFFFFF' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                           <td bgcolor='#FFFFFF' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                           <td valign='center' align='center'>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kembali&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kembali]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Permintaan&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Permintaan]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                           </td>   
                                       </tr>";
                                }
                            }else if($status=="Tidak Ada"){
                                if($baris["stts_daftar"]=="Lama"){
                                    echo "<tr class='isi7'>
                                           <td bgcolor='#FF0040' valign='center'>".$baris["no_rkm_medis"]."</td>
                                           <td bgcolor='#FF0040' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                           <td bgcolor='#FF0040' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                           <td bgcolor='#FF0040' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                           <td valign='center' align='center'>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kembali&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kembali]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Permintaan&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Permintaan]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                           </td>  
                                       </tr>";
                                } else if($baris["stts_daftar"]=="Baru"){
                                    echo "<tr class='isi7'>
                                           <td bgcolor='#FACC2E' valign='center'>".$baris["no_rkm_medis"]."</td>
                                           <td bgcolor='#FF0040' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                           <td bgcolor='#FF0040' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                           <td bgcolor='#FF0040' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                           <td valign='center' align='center'>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kembali&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kembali]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Permintaan&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Permintaan]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                           </td>  
                                       </tr>";
                                } else if($baris["stts_daftar"]=="-"){
                                    echo "<tr class='isi7'>
                                           <td bgcolor='#FF0040' valign='center'>".$baris["no_rkm_medis"]."</td>
                                           <td bgcolor='#FF0040' valign='top'>".$baris["nm_pasien"]."<br>".$baris["almt_pj"]."<br>".$baris["jk"].", Usia ".$baris["umur"]."</td>
                                           <td bgcolor='#FF0040' valign='top'>".$baris["no_rawat"]." ".$baris["no_reg"]."<br>".$baris["tgl_registrasi"]." ".$baris["jam_reg"]."<br>Status : ".$baris["stts_daftar"]."</td>
                                           <td bgcolor='#FF0040' valign='top'>".$baris["nm_dokter"]."<br>".$baris["nm_poli"]."<br>Cara Bayar : ".$baris["png_jawab"]."</td>
                                           <td valign='center' align='center'>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Kembali&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Kembali]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=Permintaan&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Permintaan]</a><br>
                                                <a href=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=TidakAda&no_rawat=".$baris["no_rawat"]."&no_rm=".$baris["no_rkm_medis"].">[Tidak Ada]</a>
                                           </td>  
                                       </tr>";
                                }                                    
                            }
                        }
                                
                    }
            echo "</table>";           
        } else {echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head2'>
                            <td width='7%'><div align='center'>No.RM</div></td>
                            <td width='20%'><div align='center'>Data Pasien</div></td>
                            <td width='15%'><div align='center'>Registrasi</div></td>
                            <td width='19%'><div align='center'>Dokter</div></td>
                            <td width='9%'><div align='center'>Cara Bayar</div></td>
                            <td width='30%'><div align='center'>Proses</div></td>
                        </tr>
                      </table>";        
        }        
        
        $BtnKeluar=isset($_POST['BtnKeluar'])?$_POST['BtnKeluar']:NULL;
        if (isset($BtnKeluar)) {
            echo"<meta http-equiv='refresh' content='1;URL=?act=List&action=Keluar'>";
	}
        
        if($action!="no"){
            if($action=="Permintaan") {
                Hapus2("mutasi_berkas","no_rawat='$no_rawat'"); 
            }else if($action=="Kirim") {
                if(getOne("select count(no_rawat) from mutasi_berkas where no_rawat='".$no_rawat."'")==0){
                    InsertData2(" mutasi_berkas"," '$no_rawat','Sudah Dikirim',now(),'0000-00-00 00:00:00','0000-00-00 00:00:00','0000-00-00 00:00:00' ");
                }else{
                    Ubah2(" mutasi_berkas "," status='Sudah Dikirim',dikirim=now() WHERE no_rawat='$no_rawat' ");                
                }                                     
            }else if ($action=="Kembali") {            
                if(getOne("select count(no_rawat) from mutasi_berkas where no_rawat='".$no_rawat."'")==0){
                    InsertData2(" mutasi_berkas"," '$no_rawat','Sudah Kembali',now(),'0000-00-00 00:00:00',now(),'0000-00-00 00:00:00' ");
                }else{
                    Ubah2(" mutasi_berkas "," status='Sudah Kembali',kembali=now() WHERE no_rawat='$no_rawat' ");
                }                
            }else if ($action=="TidakAda") {
                if(getOne("select count(no_rawat) from mutasi_berkas where no_rawat='".$no_rawat."'")==0){
                    InsertData2(" mutasi_berkas"," '$no_rawat','Tidak Ada',now(),'0000-00-00 00:00:00','0000-00-00 00:00:00',now() ");
                }else{
                    Ubah2(" mutasi_berkas "," status='Tidak Ada',tidakada=now() WHERE no_rawat='$no_rawat' ");                  
                }                
            }else if ($action=="CetakBerkasRM") {            
                $_sql2 = "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,
                    reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.umur,poliklinik.nm_poli,
                    reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab 
                    from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab 
                    on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                    and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='".$no_rawat."'";
                $hasil2=bukaquery($_sql2);
                while($baris2 = mysql_fetch_array($hasil2)) {
                    HapusAll("temporary");
                    InsertData2("temporary", "'0','".$baris2["no_rawat"]."','".$baris2["no_rkm_medis"]."','','".$baris2["no_reg"]."','".$baris2["nm_poli"]."','".$baris2["png_jawab"]."', 
                            '".$baris2["nm_pasien"]."','".$baris2["almt_pj"]."','".$baris2["nm_dokter"]."','".$baris2["p_jawab"]."','".$baris2["tgl_registrasi"]."','ralan','','','','','','','','','','','','','','','','','','','','','','','','',''");                
                }            
            }            
            echo "<meta http-equiv='refresh' content='1;URL=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=no'>";                 
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
                        Dokter : 
                        <select name="dokter" class="text4">
                            <?php
                                $_sql = "SELECT nm_dokter FROM dokter  ORDER BY nm_dokter";
                                $hasil=bukaquery($_sql);
                                if(!empty($dokter)){
                                    echo "<option value='$dokter'>$dokter</option>";
                                }
                                echo "<option value=''></option>";
                                while($baris = mysql_fetch_array($hasil)) {
                                    echo "<option value='$baris[0]'>$baris[0]</option>";
                                }
                            ?>
                        </select>
                    </td>
                </tr>
                <tr class="head4">
                    <td>
                        Unit/Poli : 
                        <select name="poli" class="text5">
                            <?php
                                $_sql = "SELECT nm_poli FROM poliklinik  ORDER BY nm_poli";
                                $hasil=bukaquery($_sql);
                                if(!empty($poli)){
                                    echo "<option value='$poli'>$poli</option>";
                                }
                                echo "<option value=''></option>";
                                while($baris = mysql_fetch_array($hasil)) {
                                    echo "<option value='$baris[0]'>$baris[0]</option>";
                                }
                            ?>
                        </select>
                        &nbsp;&nbsp;
                        Keyword : <input name="keyword" class="text" type="text" value="<?php echo $keyword;?>" size="24" maxlength="250" autofocus />
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;" />&nbsp;&nbsp;&nbsp;&nbsp;
                        Record : <?php echo $jumlah; ?>&nbsp;&nbsp;&nbsp;&nbsp;
                        <input name=BtnKeluar type=submit class="button" value="&nbsp;&nbsp;&nbsp;Keluar&nbsp;&nbsp;&nbsp;" />
                    </td>
                </tr>
            </table>
	</form>
    </div>
</div>
<?php 
  echo "<meta http-equiv='refresh' content='7;URL=?act=List&keyword=".str_replace(" ","_",$keyword)."&dokter=".str_replace(" ","_",$dokter)."&poli=".str_replace(" ","_",$poli)."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&action=no'>";
?>
