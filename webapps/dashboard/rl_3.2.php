<?php
 include '../conf/conf.php';
   /* header("Content-type: application/x-msdownload");
    header("Content-Disposition: attachment; filename=LaporanRekapKunjuangnJenisBayar.xls");
    header("Pragma: no-cache");
    header("Expires: 0");
    print "$header\n$data";*/
?>
<html>
    <body>
     <div class="panel-heading">
	<h4>Laporan RL 3.2</h4>
     </div>
    <?php
        switch($_GET[act]){
        default:
        echo"<form action='?module=3.2&act=tampil' method='post'>
                <table>
                    <tr>
                        <td>Periode</td>
                        <td>:</td>
                        <td><input type='text' id='calender1' name='tanggal1' >-S/D-<input type='text' id='calender' name='tanggal2' ></td>
                        <td align='right'><input type='submit' value='tampilkan' ></td>
                    </tr>
                </table>
             </form>";
	break;
	
	case "tampil":
        reportsqlinjection();      
        date_default_timezone_set("Asia/Jakarta");
        $datatime   = date("Y-m-d H:i:s");
        $date1      = $_POST['tanggal1']; 
        $date2      = $_POST['tanggal2'];
        $tanggal1   = date("Y-m-d",strtotime($date1));
        $tanggal2   = date("Y-m-d",strtotime($date2));
        $datatime   = date("Y-m-d H:i:s");

        //query setting
        $ppkkode="SELECT setting.kode_ppk, poliklinik.kd_poli, poliklinik.nm_poli FROM poliklinik, setting where poliklinik.kd_poli!='-' order by poliklinik.nm_poli";
        $ppk_kode=bukaquery($ppkkode);
        echo"
            <input type='button' value='Back' onclick=self.history.go(-1)>
            <h3>Periode: $tanggal1 - $tanggal2</h3>
            <table class='table table-bordered table-hover table-striped' data-toggle='table'>
                <thead>                    
                    <td>Kode</td>
                    <td>Tanggal</td>
                    <td>Jenis Pelayanan</td>
                    <td>Status Keluar</td>
                    <td>Jumlah Pasien</td>                    
                </thead>";
		while($ppk = mysqli_fetch_array($ppk_kode)) { 
                    $k_ppk=$ppk['kode_ppk'];
                    $kd_poli=$ppk['kd_poli'];
                    $jumlahdirawat= getOne("select count(reg_periksa.no_rawat) from reg_periksa inner join kamar_inap 
                                    on reg_periksa.no_rawat=kamar_inap.no_rawat where reg_periksa.kd_poli='".$ppk['kd_poli']."'
                                    and reg_periksa.tgl_registrasi between '$tanggal1' and '$tanggal2'");
                    echo "
                        <tr>
                            <td>$k_ppk</td>
                            <td>$tanggal2</td>
                            <td>".$ppk["nm_poli"]."</td>
                            <td>Dirawat</td>
                            <td>$jumlahdirawat</td>
                        </tr>
                        ";
                    
                    $post[] = array(
                        'koders'=>$baris['kode_ppk'],
                        'tanggal'=>$tanggal2,
                        'jenispelayanan'=>$ppk["nm_poli"],
                        'statuskeluar'=>'Dirawat',
                        'jumlahpasien'=>$jumlahdirawat
                    );
                    
                    $jumlahdirujuk= getOne("select count(reg_periksa.no_rawat) from reg_periksa inner join rujuk 
                                    on reg_periksa.no_rawat=rujuk.no_rawat where reg_periksa.kd_poli='".$ppk['kd_poli']."' 
                                    and reg_periksa.tgl_registrasi between '$tanggal1' and '$tanggal2'");
                    echo "
                        <tr>
                            <td>$k_ppk</td>
                            <td>$tanggal2</td>
                            <td>".$ppk["nm_poli"]."</td>
                            <td>Dirujuk</td>
                            <td>$jumlahdirujuk</td>
                        </tr>
                        ";
                    
                    $post[] = array(
                        'koders'=>$baris['kode_ppk'],
                        'tanggal'=>$tanggal2,
                        'jenispelayanan'=>$ppk["nm_poli"],
                        'statuskeluar'=>'Dirujuk',
                        'jumlahpasien'=>$jumlahdirujuk 
                    );
                    
                    $jumlahmati= getOne("select count(reg_periksa.no_rawat) from reg_periksa inner join pasien inner join pasien_mati 
                                    on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and pasien.no_rkm_medis=pasien_mati.no_rkm_medis 
                                    where reg_periksa.kd_poli='".$ppk['kd_poli']."' and reg_periksa.tgl_registrasi between '$tanggal1' and '$tanggal2'");
                    echo "
                        <tr>
                            <td>$k_ppk</td>
                            <td>$tanggal2</td>
                            <td>".$ppk["nm_poli"]."</td>
                            <td>Meninggal</td>
                            <td>$jumlahmati</td>
                        </tr>
                        ";
                    
                    $post[] = array(
                        'koders'=>$baris['kode_ppk'],
                        'tanggal'=>$tanggal2,
                        'jenispelayanan'=>$ppk["nm_poli"],
                        'statuskeluar'=>'Meninggal',
                        'jumlahpasien'=>$jumlahmati 
                    );
                    
                    $jumlahpasien= getOne("select count(reg_periksa.no_rawat) from reg_periksa where reg_periksa.kd_poli='".$ppk['kd_poli']."' 
                                          and reg_periksa.tgl_registrasi between '$tanggal1' and '$tanggal2'");
                    echo "
                        <tr>
                            <td>$k_ppk</td>
                            <td>$tanggal2</td>
                            <td>".$ppk["nm_poli"]."</td>
                            <td>Pulang</td>
                            <td>".($jumlahpasien-$jumlahdirawat-$jumlahdirujuk-$jumlahmati)."</td>
                        </tr>
                        ";
                    
                    $post[] = array(
                        'koders'=>$baris['kode_ppk'],
                        'tanggal'=>$tanggal2,
                        'jenispelayanan'=>$ppk["nm_poli"],
                        'statuskeluar'=>'Pulang',
                        'jumlahpasien'=>($jumlahpasien-$jumlahdirawat-$jumlahdirujuk-$jumlahmati)
                    );
                }
        echo "</table>";
                            
                                

                                
                             
                        
		
		 
        
?>
<input id = 'content' type='text' name='content' value='<?php echo $myvars; ?>'>
<div id="result"></div>

<script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	    $.ajax({
	        url: "http://202.147.199.11/wsdashboarddinkes/wskunjunganpasiendiagnosaphp",
	        type: "POST",
	        data: {
				data: $("#content").val()
			},
	        dataType: "JSON",
	        success: function (jsonStr) {
	            $("#result").text(JSON.stringify(jsonStr));
	        }
	    });
	});
</script>

	
<!-- <button id="submit" name="submit" type="submit">Send</buttton> -->
		<?php break ; 
}
?>	
    </body>
</html>
