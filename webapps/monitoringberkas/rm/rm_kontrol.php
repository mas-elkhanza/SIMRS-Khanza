<?php
//untuk koneksi database
include "../koneksi.php";
include "indotgl.php";

/** Nilai default si tanggal buat pas load */
$querytanggal            = "";
$tgl_awal                   = date('Y-m-d');
$tgl_akhir                  = date('Y-m-d');
$cari_rm                    = "";
$cari_stts                  = "";
$querytanggal            = "AND reg_periksa.tgl_registrasi = '$tgl_awal'";
if (isset($_REQUEST['cari'])){
    $tgl_awal               = $_REQUEST['tgl_awal'];
    $tgl_akhir              = $_REQUEST['tgl_akhir'];
    $cari_rm                = TRIM($_REQUEST['txt_rm']);
    $cari_stts              = TRIM($_REQUEST['txt_stts']);
    
    if (trim($_REQUEST['tgl_awal']) == '' AND trim($_REQUEST['tgl_akhir']) == ''){
        $tgl_awal           = date('Y-m-d');
        $tgl_akhir          = date('Y-m-d');          
        $querytanggal    = "";
    }else{        
        $querytanggal    = " AND reg_periksa.tgl_registrasi between '$tgl_awal' AND '$tgl_akhir' ";                
    }
    if (TRIM($_REQUEST['txt_rm']) <> ''){
        $querytanggal    = $querytanggal." AND UPPER(reg_periksa.no_rkm_medis) LIKE UPPER('$cari_rm%')";
    }
    if (TRIM($_REQUEST['txt_stts']) <> ''){
        $querytanggal    = $querytanggal." AND UPPER(rm.stts_rm) LIKE UPPER('$cari_stts%')";
    }
}

?>

<!DOCTYPE html>
<html>

<head>
  <title></title>
          <link rel="stylesheet" type="text/css" href="../css/style.css">
       
</head>

<body>
<div id="header"><h><b>&ensp;&ensp;RUMAH SAKIT UMUM DAERAH CIRACAS</b></h></div>
<div id="ket"><h><b>&ensp;&ensp;&ensp;Unit Rekam Medis &ensp;<span style="color: blue">< Kontrol Manual ></span></b><ul><a href="../index.php">Home</a> &nbsp;&nbsp;<a href="rm.php">Rekam Medis</a></ul></h></div>
<div id="body">
     <div id="kontent1">
           
<?php //..........................................................................................................................?>   
            <div class="tabel1">
              <center>
             <form action='rm_kontrol.php' method='POST' name='ApaAjaNamanya'>
                <table>
                    <tr>
                      <td>Tanggal Awal</td>
                      <td>:</td>
                      <td><input type='text' name='tgl_awal' size='15' placeholder='yyyy-mm-dd' value='<?php echo $tgl_awal           = date('Y-m-d') ?>' />
                      <a href="javascript:void(0)" onClick="if(self.gfPop)gfPop.fPopCalendar(document.ApaAjaNamanya.tgl_awal);return false;" ><img src="calender/calender.jpeg" alt="" name="tgl_awal" width="34" height="29" border="0" align="absmiddle" id="popcal" /></a>
                      </td>
                      <td>&nbsp;</td>
                      <td>Tanggal Akhir</td>
                      <td>:</td>
                      <td><input type='text' name='tgl_akhir' size='15' placeholder='yyyy-mm-dd' value='<?php echo $tgl_akhir          = date('Y-m-d') ?>' />
                      <a href="javascript:void(0)" onClick="if(self.gfPop)gfPop.fPopCalendar(document.ApaAjaNamanya.tgl_akhir);return false;" ><img src="calender/calender.jpeg" alt="" name="tgl_akhir" width="34" height="29" border="0" align="absmiddle" id="popcal" /></a>
                      </td>
                    </tr>
                    <tr>
                      <td>No RM</td>
                      <td>:</td>
                      <td><input type='text' name='txt_rm' size='15' placeholder='No Rm' value='' /></td>
                      <td>&nbsp;</td>
                      <td>Indikator RM</td>
                      <td>:</td>
                      <td>
                           <select name='txt_stts'>  
                           <option value="">Silahkan Pilih</option>  
                           <option value="Kembali">Kembali</option>  
                           <option value="Terkirim">Terkirim</option>  
                           <option value="Terima">Terima</option>  
                           <option value="Pinjam">Pinjam</option> 
                           <option value="Hilang">Hilang</option> 
                           </select> 
                      </td>
                      <td>&nbsp;</td>
                      <td><button type='submit' name='cari' class='btn btn-white btn-info btn-bold'>Tampilkan Data</button></td>
                    </tr>
                </table> 
              </form></center>

              <?php

$query = mysql_query("
          SELECT
            sik.reg_periksa.no_rawat,
            sik.reg_periksa.tgl_registrasi,
            sik.reg_periksa.jam_reg,
            sik.reg_periksa.no_rkm_medis,
            sik.pasien.nm_pasien,
            sik.reg_periksa.no_reg,
            sik.poliklinik.nm_poli,
            sik.reg_periksa.stts,
            sik.reg_periksa.stts_daftar,
            sik.reg_periksa.status_lanjut,
            sik.penjab.png_jawab,
            sik.rm.stts_rm
          FROM
            sik.reg_periksa,
            sik.rm,
            sik.pasien,
            sik.poliklinik,
            sik.penjab
          WHERE
            sik.rm.no_rkm_medis = sik.reg_periksa.no_rkm_medis AND
            sik.pasien.no_rkm_medis = sik.reg_periksa.no_rkm_medis AND
            sik.poliklinik.kd_poli = sik.reg_periksa.kd_poli AND
            sik.penjab.kd_pj = sik.reg_periksa.kd_pj
            $querytanggal
            ORDER BY
            reg_periksa.tgl_registrasi DESC,
            reg_periksa.jam_reg DESC");

?>
 
            </div>
            <br>
            <div id="flex1" class="tabel2">
                    <table class="table table-bordered" border="1">  
                          <tr>  
                               <th width="2%">No Rawat</th>  
                               <th width="2%">Tgl Reg</th>  
                               <th width="2%">Jam Reg</th>  
                               <th width="5%">No RM</th>
                               <th width="10%">Nama Pasien</th>
                               <th width="5%">Poliklinik</th>  
                               <th width="5%">Status Periksa</th>
                               <th width="5%">Status Daftar</th> 
                               <th width="5%">Status Bayar</th>
                               <th width="5%">Status Rawat</th>
                               <th width="5%">Indikator</th>
                               <th colspan="3" width="6%">Print</th>
                          </tr>  
                     <?php  
                     while($row = mysql_fetch_array($query))  
                     {  
                     ?>  
                          <tr>  
                               <td align="center"><?php echo $row["no_rawat"]; ?></td>  
                               <td align="center"><?php echo "".konversi_tanggal($row['tgl_registrasi']).""; ?></td>  
                               <td align="center"><?php echo $row["jam_reg"]; ?></td>  
                               <td align="center"><?php echo $row["no_rkm_medis"]; ?></td>
                               <td>&nbsp;&nbsp;<?php echo $row["nm_pasien"]; ?></td>
                               <td align="center"><?php echo $row["nm_poli"]; ?></td>  
                               
                                <?php if (strtoupper($row['stts'])=='BELUM'){ 
                                echo "<td bgcolor='white' align='center'><b>BELUM</b></td>";
                                } 
                                if 
                                (strtoupper($row['stts'])=='SUDAH'){
                                echo "<td bgcolor='white' align='center'><b>SUDAH</b></td>";}
                                if 
                                (strtoupper($row['stts'])=='BAYAR'){
                                echo "<td bgcolor='white' align='center'><b>BAYAR</b></td>";}
                                if
                                (strtoupper($row['stts'])=='BATAL'){
                                echo "<td bgcolor='yellow' align='center'><b>BATAL</b></td>";}
                                ?>
                               
                               <?php if (strtoupper($row['stts_daftar'])=='LAMA'){ 
                                echo "<td bgcolor='white' align='center'><b>LAMA</b></td>";
                                } 
                                if 
                                (strtoupper($row['stts_daftar'])=='BARU'){
                                echo "<td bgcolor='#1E90FF' align='center'><b>BARU</b></td>";}
                                ?>


                               <td align="center"><?php echo $row["png_jawab"]; ?></td>
                               <td align="center"><?php echo $row["status_lanjut"]; ?></td>  
                               
                               <?php if (strtoupper($row['stts_rm'])=='KEMBALI'){ 
                                echo "<td bgcolor='#FF4500' align='center'><b>KEMBALI</b></td>";
                                } 
                                if 
                                (strtoupper($row['stts_rm'])=='TERKIRIM'){
                                echo "<td bgcolor='yellow' align='center'><b>TERKIRIM</b></td>";}
                                if 
                                (strtoupper($row['stts_rm'])=='TERIMA'){
                                echo "<td bgcolor='#7FFF00' align='center'><b>TERIMA</b></td>";}
                                if
                                (strtoupper($row['stts_rm'])=='PINJAM'){
                                echo "<td bgcolor='#B0C4DE' align='center'><b>PINJAM</b></td>";}
                                if 
                                (strtoupper($row['stts_rm'])=='HILANG'){
                                echo "<td bgcolor='pink' align='center'><b>HILANG</b></td>";}
                                ?>                             
                                <td align="center"><a href="print_umum.php?id=<?php echo $row['no_rkm_medis']?>"><b>RAJAL<b></a></td>
                                <td align="center"><a href="print_gigi.php?id=<?php echo $row['no_rkm_medis']?>"><b>GIGI<b></a></td>
                                <td align="center"><a href="print_igd.php?id=<?php echo $row['no_rkm_medis']?>"><b>IGD<b></td>
                          </tr>  
                     <?php  
                     }  
                     ?>  
                     </table>  
            </div>
<?php //..........................................................................................................................?>          
      </div>
 
</div>

<div id="footer"><p>2017</p></div>

<iframe width=174 height=189 name="gToday:normal:calender/normal.js" id="gToday:normal:calender/normal.js" src="calender/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
</body>

</html>