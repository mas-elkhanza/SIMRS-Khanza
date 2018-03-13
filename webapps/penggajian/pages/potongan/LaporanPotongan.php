<?php
 include '../../../conf/conf.php';
?>
<html>
    <head>
        <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
   <?php
        $keyword=isset($_GET['keyword'])?$_GET['keyword']:NULL;
        $_sql         = "SELECT * FROM set_tahun";
        $hasil        = bukaquery($_sql);
        $baris        = mysqli_fetch_row($hasil);
        $tahun        = $baris[0];
        $bulan        = $baris[1];
        $_sql = "SELECT pegawai.id, 
		        pegawai.nik,
				pegawai.nama,
				pegawai.departemen,
				keanggotaan.koperasi, 
				keanggotaan.jamsostek, 				 
				keanggotaan.bpjs,
				potongan.bpjs, 
				potongan.jamsostek, 
				potongan.dansos, 
				potongan.simwajib, 
				potongan.angkop, 
				potongan.angla, 
				potongan.telpri, 
				potongan.pajak, 
				potongan.pribadi, 
				potongan.lain, 
				potongan.ktg
				FROM keanggotaan, potongan
				RIGHT OUTER JOIN pegawai ON potongan.id = pegawai.id
				AND tahun like '%".$tahun."%'  and bulan like '%".$bulan."%' 
				WHERE pegawai.stts_aktif<>'KELUAR' and keanggotaan.id=pegawai.id and pegawai.nik like '%".$keyword."%' or
				pegawai.stts_aktif<>'KELUAR' and keanggotaan.id=pegawai.id and pegawai.nama like '%".$keyword."%' or
				pegawai.stts_aktif<>'KELUAR' and keanggotaan.id=pegawai.id and pegawai.departemen like '%".$keyword."%' or
				pegawai.stts_aktif<>'KELUAR' and keanggotaan.id=pegawai.id and keanggotaan.koperasi like '%".$keyword."%' or
				pegawai.stts_aktif<>'KELUAR' and keanggotaan.id=pegawai.id and keanggotaan.bpjs like '%".$keyword."%' or
				pegawai.stts_aktif<>'KELUAR' and keanggotaan.id=pegawai.id and keanggotaan.jamsostek like '%".$keyword."%'
				order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        $bpjs=0;
        $jamsos=0;
        $dansos=0;
        $simwa=0;
        $angkop=0;
        $angla=0;
        $telpri=0;
        $pajak=0;
        $pribadi=0;
        $jml=0;
        $lain=0;
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <caption><h3><font color='999999'>Laporan Potongan Gaji Tahun ".$tahun." Bulan ".$bulan."</font></h3></caption>
                    <tr class='head'>
                        <td width='100px'><div align='center'>NIP</div></td>
                        <td width='250px'><div align='center'>Nama</div></td>
			<td width='100px'><div align='center'>Departemen</div></td>
                        <td width='80px'><div align='center'>Anggota Koperasi</div></td>
                        <td width='80px'><div align='center'>Anggota Jamsostek</div></td>
                        <td width='80px'><div align='center'>Anggota BPJS</div></td>
                        <td width='100px'><div align='center'>BPJS</div></td>
                        <td width='100px'><div align='center'>Jamsostek</div></td>
                        <td width='100px'><div align='center'>Dana Sosial</div></td>
                        <td width='100px'><div align='center'>Simpanan Wajib</div></td>
                        <td width='100px'><div align='center'>Angsuran Koperasi</div></td>
                        <td width='100px'><div align='center'>Angsuran Lain</div></td>
                        <td width='100px'><div align='center'>Telepon Pribadi</div></td>
                        <td width='100px'><div align='center'>Pajak</div></td>
                        <td width='100px'><div align='center'>Pribadi</div></td>
                        <td width='100px'><div align='center'>Lain-Lain</div></td>
                        <td width='100px'><div align='center'>Total Potongan</div></td>
                        <td width='200px'><div align='center'>Keterangan</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        $ttl=$baris[7]+$baris[8]+$baris[9]+$baris[10]+$baris[11]+$baris[12]+$baris[13]+$baris[14]+$baris[15]+$baris[16];
                        $jml=$jml+$ttl;
                        $bpjs=$bpjs+$baris[7];
                        $jamsos=$jamsos+$baris[8];
                        $dansos=$dansos+$baris[9];
                        $simwa=$simwa+$baris[10];
                        $angkop=$angkop+$baris[11];
                        $angla=$angla+$baris[12];
                        $telpri=$telpri+$baris[13];
                        $pajak=$pajak+$baris[14];
                        $pribadi=$pribadi+$baris[15];
                        $lain=$lain+$baris[16];
                        echo "<tr class='isi'>
                                <td>$baris[1]&nbsp;</td>
                                <td>$baris[2]&nbsp;</td>
                                <td>$baris[3]&nbsp;</td>
                                <td>$baris[4]&nbsp;</td>
                                <td>$baris[5]&nbsp;</td>
                                <td>$baris[6]&nbsp;</td>
                                <td>".formatDuit($baris[7])."&nbsp;</td>
                                <td>".formatDuit($baris[8])."&nbsp;</td>
                                <td>".formatDuit($baris[9])."&nbsp;</td>
                                <td>".formatDuit($baris[10])."&nbsp;</td>
                                <td>".formatDuit($baris[11])."&nbsp;</td>
                                <td>".formatDuit($baris[12])."&nbsp;</td>
                                <td>".formatDuit($baris[13])."&nbsp;</td>
                                <td>".formatDuit($baris[14])."&nbsp;</td>
                                <td>".formatDuit($baris[15])."&nbsp;</td>
                                <td>".formatDuit($baris[16])."&nbsp;</td>
                                <td>".formatDuit($ttl)."&nbsp;</td>
                                <td>$baris[17]&nbsp;</td>
                             </tr>";
                    }
            echo "</table>";
        }
        echo("<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                  <tr class='head'>
                      <td width='50%'>Total BPJS </td><td width='1%'>:</td><td width='49%'>".formatDuit($bpjs)."</td> 
                  </tr>
                  <tr class='head'>
                      <td width='50%'>Total Jamsostek </td><td width='1%'>:</td><td width='49%'>".formatDuit($jamsos)."</td> 
                  </tr>
                  <tr class='head'>
                      <td width='50%'>Total Dana Sosial </td><td width='1%'>:</td><td width='49%'>".formatDuit($dansos)."</td> 
                  </tr>
                  <tr class='head'>
                      <td width='50%'>Total Simpanan Wajib</td><td width='1%'>:</td><td width='49%'>".formatDuit($simwa)."</td> 
                  </tr>
                  <tr class='head'>
                      <td width='50%'>Total Angsuran koperasi </td><td width='1%'>:</td><td width='49%'>".formatDuit($angkop)."</td>
                  </tr>
                  <tr class='head'>
                      <td width='50%'>Total Angsuran Lain </td><td width='1%'>:</td><td width='49%'>".formatDuit($angla)."</td> 
                  </tr>
                  <tr class='head'>
                      <td width='50%'>Total Telephone Pribadi </td><td width='1%'>:</td><td width='49%'>".formatDuit($telpri)."</td> 
                  </tr>
                  <tr class='head'>
                      <td width='50%'>Total Pajak </td><td width='1%'>:</td><td width='49%'>".formatDuit($pajak)."</td>                        
                  </tr>   
                  <tr class='head'>
                      <td width='50%'>Total Pribadi  </td><td width='1%'>:</td><td width='49%'>".formatDuit($pribadi)."</td>                        
                  </tr>
                  <tr class='head'>
                      <td width='50%'>Total Lain-Lain </td><td width='1%'>:</td><td width='49%'>".formatDuit($lain)."</td>                        
                  </tr>
                  <tr class='head'>
                      <td width='50%'>Jumlah Total Potongan Gaji </td><td width='1%'>:</td><td width='49%'>".formatDuit($jml)."</td>                        
                  </tr>
             </table>");
    ?>
    </body>
</html>