<?php 
include '../conf/conf.php';
$tgl=date('Y/m/d/');
$act=$_GET['act'];
if($act=='registrasi' ){ 
				$tanggal=date('Y-m-d', strtotime($_POST["tanggal"]));
				$tgl_registrasi = date('Y/m/d/', strtotime($_POST["tanggal"]));
				$hari=$_POST['hari'];
				$jam_reg=date('07:i:s');
				$kd_dokter=$_POST['kd_dokter'];
				$no_rkm_medis=$_POST['no_rkm_medis'];
				$kd_poli=$_POST['kd_poli'];
				$kd_pj=$_POST['kd_penjab'];
				$stts='Belum';
				$stts_daftar='Lama';
				$stts_lanjut='Ralan';	
				//jam pelayanan
				$jam_pel=mysqli_fetch_array(bukaquery("select jadwal.kd_poli,poliklinik.nm_poli,jadwal.jam_mulai,jadwal.jam_selesai from jadwal,poliklinik,dokter where jadwal.kd_poli=poliklinik.kd_poli and
																				jadwal.kd_dokter=dokter.kd_dokter and hari_kerja LIKE '%$hari%' GROUP BY poliklinik.kd_poli"));
				$jam_mulai=$jam_pel['jam_mulai'];
				$jam_selesai=$jam_pel['jam_selesai'];
				//mencari biodata pasien dari no rm
				$b=mysqli_fetch_array(bukaquery("SELECT pasien.nm_ibu, pasien.alamatpj, pasien.kd_kel, pasien.nm_pasien, pasien.kd_kec, pasien.kd_kab, pasien.tgl_lahir, pasien.keluarga FROM pasien WHERE pasien.no_rkm_medis='$no_rkm_medis'"));
				$p_jawab=$b['nm_ibu'];
				$alamat_pj=$b['alamatpj'];
				$hubunganpj=$b['keluarga'];
				$tgl_lahir=$b['tgl_lahir'];
				$nm_pasien=$b['nm_pasien'];
				//menentukan umur sekarang
				$tmp =new datetime($tgl_lahir);
				$today = new datetime($tgl_registrasi);
				$masa= $today->diff($tmp);
				$umurdaftar= $masa->y;
				$sttsumur='Th';
				//mencari biaya registrasi
				$biaya_reg=getone("SELECT poliklinik.registrasilama FROM poliklinik WHERE poliklinik.kd_poli='$kd_poli'");
				//mencari nama poli
				$nm_poli=getone("SELECT poliklinik.nm_poli FROM poliklinik WHERE poliklinik.kd_poli='$kd_poli'");
				//mencari nama pembayaran
				$jns_bayar=getone("SELECT png_jawab FROM penjab WHERE penjab.kd_pj='$kd_pj'");
				//mencari nama dokter
				$nm_dokter=getone("SELECT dokter.nm_dokter FROM dokter WHERE dokter.kd_dokter='$kd_dokter'");
				//mengambil kop rumah sakit
				$setting=  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
				//mencari no rawat terakhir
				$no_rawat_akhir = getone("SELECT max(reg_periksa.no_rawat) as maxno_rawat FROM reg_periksa WHERE no_rawat LIKE '%$tgl_registrasi%'");
				$no_urut_rawat = (int) substr($no_rawat_akhir, 11, 6);
				$no_urut_rawat++;
				//output no rawat
				$no_rawat= $tgl_registrasi .sprintf('%06s',$no_urut_rawat++);
				//mencari no reg terakhir
				$no_reg_akhir = getone("SELECT max(reg_periksa.no_reg) as maxno_reg FROM reg_periksa WHERE reg_periksa.kd_dokter='$_POST[kd_dokter]' and reg_periksa.tgl_registrasi='$tgl_registrasi'");
				$no_urut_reg = (int) substr($no_reg_akhir,0,3);
				$no_urut_reg++;
				//output no reg
				$no_reg= sprintf('%03s',$no_urut_reg++);
				//cek biar ga double datanya
				$cek = getone("SELECT reg_periksa.no_rawat FROM reg_periksa WHERE reg_periksa.no_rkm_medis='$no_rkm_medis' AND reg_periksa.tgl_registrasi='$tanggal'");
				if($cek == ''){
				mysql_query("insert into reg_periksa set no_reg='$no_reg', no_rawat='$no_rawat', tgl_registrasi='$tanggal', jam_reg='$jam_reg',
														 kd_dokter='$kd_dokter', no_rkm_medis='$no_rkm_medis', kd_poli='$kd_poli', p_jawab='$p_jawab',
														 almt_pj='$alamat_pj', hubunganpj='$hubunganpj', biaya_reg='$biaya_reg', stts='$stts',
														 stts_daftar='$stts_daftar', status_lanjut='$stts_lanjut', kd_pj='$kd_pj', umurdaftar='$umurdaftar',
														 sttsumur='$sttsumur' ");
				Echo"
				<form action='cetak.php' method='post' target='_BLANK' enctype='multipart/form-data'>
				<table bo align='center' border='0'>
				<tr>
				<td colspan='3' align='center'>
				<center>
                      <font color='000000' size='3'  face='Tahoma'>".$setting["nama_instansi"]."</font><br>
                      <font color='000000' size='1'  face='Tahoma'>".$setting["alamat_instansi"].", ".$setting["kabupaten"].", ".$setting["propinsi"]."<br/>
                      ".$setting["kontak"].", E-mail : ".$setting["email"]."
                       </font> </center>
				</td>
				</tr>
				<tr>
				<td colspan='3' align='center'><hr></td>
				</tr>
				<tr>
				<td colspan='3' align='center'><h4>BUKTI REGISTRASI PENDAFTARAN ONLINE</h4></td>
				</tr>
				<tr font color='000000' size='1'  face='Tahoma'>
				
				<td>Tanggal</td><td>:</td><td><input type='hidden' name='tanggal' value='$tanggal'>$tanggal</td>
				
				</tr>
				<tr>
				
				<td>No.Rawat</td><td>:</td><td>$no_rawat</td>
				
				</tr>
				<tr>
				
				<td>No.Antrian poli</td><td>:</td><td><input type='hidden' name='kd_poli' value='$kd_poli'>$no_reg / $nm_poli</td>
				
				</tr>
				<tr>
				
				<td>Dokter</td><td>:</td><td><input type='hidden' name='kd_dokter' value='$kd_dokter'>$nm_dokter</td>
				
				</tr>
				<tr>
				
				<td>Nama Pasien</td><td>:</td><td>$nm_pasien</td>
				
				</tr>
				<tr>
				
				<td>No. RM</td><td>:</td><td><input type='hidden' name='no_rkm_medis' value='$no_rkm_medis'>$no_rkm_medis</td>
				
				</tr>
				<tr>
				
				<td>Jns.Pasien</td><td>:</td><td><input type='hidden' name='kd_penjab' value='$kd_pj'>$jns_bayar</td>
				
				</tr>
				<tr>
				
				<td>Jadwal Pelayanan</td><td>:</td><td>$jam_mulai-$jam_selesai</td>
				
				</tr>
				<tr>
				<td colspan='3' align='center'><hr><input type='hidden' name='hari' value='$hari'></td>
				</tr>
				<tr>
				<td colspan='3' align='center'  valign='jusfity'>
				<center>
                      <font color='000000' size='1'  face='Tahoma'>Terima Kasih Atas kepercayaan Anda</font><p>
					  <font color='000000' size='1'  face='Tahoma'>Bawalah kartu Berobat anda dan</font> <font color='000000' size='1'  face='Tahoma'>datang 1 Jam Sebelumnya</p></font></td>
				</tr>
				<tr>
				<td colspan='3' align='center'><input type='submit' value='Cetak'></td>
				</tr>
				</table>
				</form>";
				}
				else
				{
				Echo"
				<form action='cetak.php' method='post' target='_BLANK' enctype='multipart/form-data'>
				<table bo align='center' border='0'>
				<tr>
				<td colspan='3' align='center'>
				<center>
                      <font color='000000' size='3'  face='Tahoma'>".$setting["nama_instansi"]."</font><br>
                      <font color='000000' size='1'  face='Tahoma'>".$setting["alamat_instansi"].", ".$setting["kabupaten"].", ".$setting["propinsi"]."<br/>
                      ".$setting["kontak"].", E-mail : ".$setting["email"]."
                       </font> </center>
				</td>
				</tr>
				<tr>
				<td colspan='3' align='center'><hr></td>
				</tr>
				<tr>
				<td colspan='3' align='center'><h4>BUKTI REGISTRASI PENDAFTARAN ONLINE</h4></td>
				</tr>
				<tr font color='000000' size='1'  face='Tahoma'>
				
				<td>Tanggal</td><td>:</td><td><input type='hidden' name='tanggal' value='$tanggal'>$tanggal</td>
				
				</tr>
				<tr>
				
				<td>No.Rawat</td><td>:</td><td>$no_rawat</td>
				
				</tr>
				<tr>
				
				<td>No.Antrian poli</td><td>:</td><td><input type='hidden' name='kd_poli' value='$kd_poli'>$no_reg / $nm_poli</td>
				
				</tr>
				<tr>
				
				<td>Dokter</td><td>:</td><td><input type='hidden' name='kd_dokter' value='$kd_dokter'>$nm_dokter</td>
				
				</tr>
				<tr>
				
				<td>Nama Pasien</td><td>:</td><td>$nm_pasien</td>
				
				</tr>
				<tr>
				
				<td>No. RM</td><td>:</td><td><input type='hidden' name='no_rkm_medis' value='$no_rkm_medis'>$no_rkm_medis</td>
				
				</tr>
				<tr>
				
				<td>Jns.Pasien</td><td>:</td><td><input type='hidden' name='kd_penjab' value='$kd_pj'>$jns_bayar</td>
				
				</tr>
				<tr>
				
				<td>Jadwal Pelayanan</td><td>:</td><td>$jam_mulai-$jam_selesai</td>
				
				</tr>
				<tr>
				<td colspan='3' align='center'><hr><input type='hidden' name='hari' value='$hari'></td>
				</tr>
				<tr>
				<td colspan='3' align='center'  valign='jusfity'>
				<center>
                      <font color='000000' size='1'  face='Tahoma'>Terima Kasih Atas kepercayaan Anda</font><p>
					  <font color='000000' size='1'  face='Tahoma'>Bawalah kartu Berobat anda dan</font> <font color='000000' size='1'  face='Tahoma'>datang 1 Jam Sebelumnya</p></font></td>
				</tr>
				<tr>
				<td colspan='3' align='center'><input type='submit' value='Cetak'><a href='index.php'><input type='button' value='Selesai'></a></td>
				</tr>
				</table>
				</form>";	
				}
				
}
?>