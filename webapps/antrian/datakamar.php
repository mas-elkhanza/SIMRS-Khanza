<?php
 require_once('conf/conf.php');
 header("Expires: Mon, 26 Jul 1997 05:00:00 GMT"); 
 header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT"); 
 header("Cache-Control: no-store, no-cache, must-revalidate"); 
 header("Cache-Control: post-check=0, pre-check=0", false);
 header("Pragma: no-cache"); // HTTP/1.0
 date_default_timezone_set("Asia/Bangkok");
 $tanggal= mktime(date("m"),date("d"),date("Y"));
 $jam=date("H:i");
 


 
 
 $_sql="Select a.status,d.nm_poli,c.nm_pasien,a.no_rawat,a.kd_dokter from antripoli a,reg_periksa b, pasien c,poliklinik d where a.kd_poli=d.kd_poli and a.no_rawat=b.no_rawat and b.no_rkm_medis=c.no_rkm_medis and a.status='1'  and a.kd_poli!='H' ";  
 $hasil=bukaquery($_sql);
 while ($data = mysqli_fetch_array ($hasil)){
    
    
      if($data['status']=="1"){ 
         ?>


<script type="text/javascript">
responsiveVoice.speak(
"Atas nama  <?= strtolower($data['nm_pasien']);?>, Silahkan menuju <?= strtolower($data['nm_poli']);?> ",
"Indonesian Female",
{
pitch: 1, 
rate: 0.9, 
volume: 1
}
);</script>
<?php
         
         bukaquery2("delete from antripoli where antripoli.kd_dokter='$data[kd_dokter]' and antripoli.status='2' ");
         bukaquery2("update antripoli set antripoli.status='2' where antripoli.no_rawat='$data[no_rawat]'");
         
     }   
 } 
  
                          
?>

<br>
         <div class="col-sm-12">
<div style="color:#f00; font-family:'Orbitron', sans-serif;font-size:30px;"><center>ANTRIAN POLI</center></div>
                    <div class="col-sm-12"> 

                    <div class="queue well text-center " style="background: rgb(3,91,200);
background: linear-gradient(36deg, rgba(3,91,200,1) 0%, rgba(110,255,251,1) 100%);border-color:#3c8dbc;min-height:50px;height:50px;padding:0;margin:0 0 0 0">
                        
                        <div class="text-center" style="display:inline-block;padding:13px 0">
                            <h3 class="token" style="color:#FFF;">DOKTER</h3>
                        </div>
                        
                    <div class="text-center" style="width:280px; height:100%;float:right;background: rgb(3,91,200);
background: linear-gradient(36deg, rgba(3,91,200,1) 0%, rgba(97,154,244,1) 100%);color:#FFF"><h3>PASIEN</h3></div> 
                    <div class="text-center" style="width:280px; height:100%;float:right;background: rgb(3,91,200);
background: linear-gradient(36deg, rgba(3,91,200,1) 0%, rgba(97,154,244,1) 100%);color:#FFF"><h3>POLI</h3></div>
                </div>


    <?php  
$tanggal=date('Y-m-d');
  
	    $hari=getOne("select DAYNAME(current_date())");
	    $namahari="";
	    if($hari=="Sunday"){
			$namahari="AKHAD";
		}else if($hari=="Monday"){
			$namahari="SENIN";
		}else if($hari=="Tuesday"){
			$namahari="SELASA";
		}else if($hari=="Wednesday"){
			$namahari="RABU";
		}else if($hari=="Thursday"){
			$namahari="KAMIS";
		}else if($hari=="Friday"){
			$namahari="JUMAT";
		}else if($hari=="Saturday"){
			$namahari="SABTU";
		}
		$_sql="Select dokter.nm_dokter,poliklinik.nm_poli,jadwal.jam_mulai,jadwal.jam_selesai,poliklinik.kd_poli, 
		       dokter.kd_dokter from jadwal inner join dokter inner join poliklinik on dokter.kd_dokter=jadwal.kd_dokter 
		       and jadwal.kd_poli=poliklinik.kd_poli where jadwal.hari_kerja='$namahari' and poliklinik.kd_poli!='H' and poliklinik.kd_poli!='PRAD'
               group by poliklinik.kd_poli,dokter.kd_dokter" ;  
		$hasil=bukaquery($_sql);

		while ($data = mysqli_fetch_array ($hasil)){

          
                        
                         
                ?>
                

 <div class=" well text-left" style="
border:0px solid #fff ;
background: rgb(126,249,243);
background: linear-gradient(36deg, rgba(126,249,243,1) 0%, rgba(145,193,250,0.9416141456582633) 100%);
border-color:#3c8dbc;min-height:50px;height:50px;padding:0;margin:0 0 0 0">
                        
                        <div  style="display:inline-block;padding-bottom:10px 0">
                            <h4 class="token" style="color:#000; 
padding-left:10px; "> 
<?php
echo $data['nm_dokter'];
?>
 </h4>
                        </div>
                        
                    <div class="text-LEFT" style="width:280px; background: rgb(126,249,243);padding-left:5px;
background: linear-gradient(36deg, rgba(126,249,243,1) 0%, rgba(145,193,250,0.9416141456582633) 100%); height:100%;float:right; color:#000; "><h4 class="token" >
<?=
  getOne("Select concat(c.nm_pasien,'(',b.no_reg,')' ) from antripoli a,reg_periksa b, pasien c where a.no_rawat=b.no_rawat and b.no_rkm_medis=c.no_rkm_medis AND a.kd_dokter='$data[kd_dokter]' and a.status='2' limit 1") ;  
?></h4></div>
                     
                    <div class="text-left" style="width:280px; background: rgb(126,249,243);padding-left:5px;
background: linear-gradient(36deg, rgba(126,249,243,1) 0%, rgba(145,193,250,0.9416141456582633) 100%); height:100%;float:right; color:#000; "><h4 class="token" >
<?php
  echo $data['nm_poli'];
?>
</h4></div>
                </div>

<?php

} 

?>
<!--

 <div class="queue well text-center " style=" border:0px solid #fff; background: rgb(126,249,243);
background: linear-gradient(36deg, rgba(126,249,243,1) 0%, rgba(145,193,250,0.9416141456582633) 100%);
border-color:#3c8dbc;min-height:50px;height:50px;padding:0;margin:0 0 0 0">
                        
                        <div class="text-center" style=";display:inline-block;padding:10px 0">
                            <h3 class="token" style="color:#000; "> <center><b>TIDAK ADA JADWAL TINDAKAN OPERASI</b> </center></h3>
                        </div>
                         
                </div>

-->
                </div>
                </div>
                







         
<!--          
         <div class="col-sm-3">

<div style="color:#f00; font-family:'Orbitron', sans-serif;font-size:30px;"><center>NOMOR ANTRIAN</center></div>

                    <div class="col-sm-12"> 
                    <div class="queue well text-center " style="background-color:#6094d4; border:0px #46e9fc solid;min-height:50px;height:50px;padding:0;margin:0 0 0 0">
                        
                        <div class="text-center" style="display:inline-block;padding:13px 0">
                            <h3 class="token" style="color:#fff;">NOMOR</h3>
                        </div>
                         
                </div>
                </div>
                
                <?php  
                          $_sql="Select kode_kelas_aplicare,kelas from aplicare_ketersediaan_kamar group by kode_kelas_aplicare order by kelas asc" ;  
                          $hasil=bukaquery($_sql);

                          while ($data = mysqli_fetch_array ($hasil)){
                         
                ?>
                
                
                    <div class="col-sm-12"> 
                    <div class="queue well " style="background: rgb(126,249,243);
background: linear-gradient(36deg, rgba(126,249,243,1) 0%, rgba(145,193,250,0.9416141456582633) 100%);

border:0px #46e9fc solid; color:#000; min-height:50px;height:50px;padding:0;margin:0 0 0 0">
                        
                    <div class="text-left" style="display:inline-block;padding-left:15px;">
                    <h2 class="token"> 
                    <?php
                     $n=$data['kode_kelas_aplicare'];
                     
                     
                     if($n=='ICU'){
                     echo 'Kamar ICU';
                     } else if($n=='ISO') {
                       echo 'Ruang ISOLASI';
                      } else if($n=='KL1') {
                       echo 'Kamar Kelas 1';
                      } else if($n=='KL2') {
                       echo 'Kamar Kelas 2';
                      } else if($n=='KL3') {
                       echo 'Kamar Kelas 3';
                      } else if($n=='NIC') {
                       echo 'NICU';
                      } else if($n=='PIC') {
                       echo 'PICU';
                      } else if($n=='SAL') {
                       echo 'Kamar Bersalin';
                      } else if($n=='UTM') {
                       echo 'Kamar Perina';
                      } else if($n=='VIP') {
                       echo 'Kamar VIP';
                      } else if($n=='VVP') {
                       echo 'Kamar VVIP';
                      }

                    ?>
                     </h2>
                    </div>  
                    
                  </div>
                </div>
                
                <?php }
                ?> 
                   
                </div>
                 -->
                
                
      </div>
              

