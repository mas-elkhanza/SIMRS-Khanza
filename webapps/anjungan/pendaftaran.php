<?php
/***
* SIMRS Khanza Lite from version 0.1 Beta
* About : Porting of SIMRS Khanza by Windiarto a.k.a Mas Elkhanza as web and mobile app.
* Last modified: 02 Pebruari 2018
* Author : drg. Faisol Basoro
* Email : drg.faisol@basoro.org
* Licence under GPL
***/

include_once('config.php');

$action     = isset($_GET['action'])?$_GET['action']:null;

    if($action  !== "sukses"){


include_once('layout/header.php');

    }
    $date_now   = date_create($date);
    date_add($date_now, date_interval_create_from_date_string(HARIDAFTAR.' days'));
    $date_next  = date_format($date_now, 'Y-m-d');


    if($action  == "sukses"){

?>

      <section style="margin: 0 auto; width: 250px; font-family: Tahoma;">
        <div class="container-fluid" style="margin-top: 10px; margin-right: 5px; margin-bottom: 50px; margin-left: 5px; font-size: 11px !important;">
            <div class="block-header">
                <div style="font-size:12px; font-weight: bold; text-align: center;">
                <?php echo $dataSettings['nama_instansi']; ?><br>
                <?php echo $dataSettings['alamat_instansi']; ?><br>
                <?php echo $dataSettings['kontak']; ?><br>
                <hr>
                BUKTI PENDAFTARAN<br>
                ANJUNGAN PASIEN MANDIRI<br>
              	</div>
            </div>

<?php

    } else if($action  == "selesai") {
?>
      <section class="content">
        <div class="container-fluid" style="margin: 120px;">
            <div class="block-header">
            </div>
<?php
    } else {
?>
      <section class="content">
        <div class="container-fluid" style="margin: 120px;">
            <div class="block-header">
                <h2>PENDAFTARAN PASIEN</h2>
            </div>


<?php } ?>

          <?php

    if(!$action){

    // check if nik exits
	function no_rm_exits($no_rkm_medis) {
    	$sql = "SELECT no_rkm_medis FROM pasien WHERE no_rkm_medis = '$no_rkm_medis' ";
    	$result = query($sql);
    	// check if we found something
    	if(num_rows($result) == 0) {
        	return true;
    	} else {
        	return false;
    	}
	}


    ?>

<?php
if($_SERVER['REQUEST_METHOD'] == "POST") {

  	if($_POST['tgl_registrasi'] == $date && $time > LIMITJAM) {
      	$errors[] = 'Silahkan pilih hari berikutnya. Anda tidak diperkenankan mendaftar dihari ini.';
    }
  	if($_POST['tgl_registrasi'] > $date_next) {
      	$errors[] = 'Maksimal pendaftaran 30 hari kedepan.';
    }

    if(empty($_POST['no_rkm_medis'])) {
	$errors[] = 'Kolom No Rekam Medis tidak boleh kosong';
    }
    if(!empty($_POST['no_rkm_medis']) && no_rm_exits($_POST['no_rkm_medis'])) {
	$errors[] = 'Pasien tidak terdaftar';
    }
    	if(!empty($errors)) {
			foreach($errors as $error) {
	    		echo validation_errors($error);
		}
    } else {
        redirect("pendaftaran.php?action=pilih-poli&no_rkm_medis=$_POST[no_rkm_medis]&tgl_registrasi=$_POST[tgl_registrasi]");
	}

}
?>

<audio autoplay>
<source src="sound/inside.mp3" type="audio/mpeg">
</audio>

            <!-- Basic Validation -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="body">
                            <form id="form_validation" action="" method="POST">
                                <label for="email_address">Nomor Rekam Medik*</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <input type="text" name="no_rkm_medis" class="form-control" placeholder="Masukkan Nomor Rekam Medis">
                                    </div>
                                </div>
                                <label for="email_address">Tanggal Kunjungan</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <input type="text" name="tgl_registrasi" class="datepicker form-control" placeholder="Tanggal Kunjungan Ke Poliklinik Tujuan">
                                    </div>
                                </div>
                                <button class="btn btn-primary waves-effect" type="submit" name="detailpasien">CEK PASIEN</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# Basic Validation -->
    <?php } ?>

    <?php
    //edit
    if($action == "pilih-poli"){

        $tanggal=$_GET['tgl_registrasi'];
        $tentukan_hari=date('D',strtotime($tanggal));
		 $day = array(
			'Sun' => 'AKHAD',
			'Mon' => 'SENIN',
			'Tue' => 'SELASA',
			'Wed' => 'RABU',
			'Thu' => 'KAMIS',
			'Fri' => 'JUMAT',
			'Sat' => 'SABTU'
			);
			$hari=$day[$tentukan_hari];

if($_SERVER['REQUEST_METHOD'] == "POST") {

    //cek biar ga double datanya
    $cek = fetch_array(query("SELECT no_rawat FROM reg_periksa WHERE no_rkm_medis='$_GET[no_rkm_medis]' AND tgl_registrasi='$_POST[tgl_registrasi]'"));
    if($cek == ''){

        if(empty($_POST['tgl_registrasi'])) {
	    $errors[] = 'Tanggal registrasi tidak boleh kosong';
        }
        if(empty($_POST['kd_poli'])) {
	    $errors[] = 'Poliklinik tujuan tidak boleh kosong';
        }
        if(empty($_POST['kd_dokter'])) {
	    $errors[] = 'Dokter tujuan tidak boleh kosong';
        }
        if(!empty($errors)) {
	        foreach($errors as $error) {
	            echo validation_errors($error);
	        }
        } else {

		$get_pasien = fetch_array(query("SELECT * FROM pasien WHERE no_rkm_medis = '{$_GET[no_rkm_medis]}'"));

	    $tgl_reg = date('Y/m/d', strtotime($_POST['tgl_registrasi']));

        //mencari no rawat terakhir
	      $no_rawat_akhir = fetch_array(query("SELECT max(no_rawat) FROM reg_periksa WHERE tgl_registrasi='$_POST[tgl_registrasi]'"));
        $no_urut_rawat = substr($no_rawat_akhir[0], 11, 6);
        $no_rawat = $tgl_reg.'/'.sprintf('%06s', ($no_urut_rawat + 1));

	    //mencari no reg terakhir
	      $no_reg_akhir = fetch_array(query("SELECT max(no_reg) FROM reg_periksa WHERE kd_dokter='$_POST[kd_dokter]' and tgl_registrasi='$_POST[tgl_registrasi]'"));
        $no_urut_reg = substr($no_reg_akhir[0], 0, 3);
        $no_reg = sprintf('%03s', ($no_urut_reg + 1));

        $biaya_reg=fetch_array(query("SELECT registrasilama FROM poliklinik WHERE kd_poli='{$_POST['kd_poli']}'"));

        $kode_berkas = KODE_BERKAS;
        $photo_rujukan=fetch_array(query("SELECT lokasi_file FROM berkas_digital_perawatan WHERE kode = '{$kode_berkas}' AND no_rawat='{$no_rawat}'"));

        //menentukan umur sekarang
        list($cY, $cm, $cd) = explode('-', date('Y-m-d'));
        list($Y, $m, $d) = explode('-', date('Y-m-d', strtotime($get_pasien[tgl_lahir])));
        $umurdaftar = $cY - $Y;

	    $insert = query("
            INSERT INTO reg_periksa
            SET no_reg          = '$no_reg',
                no_rawat        = '$no_rawat',
                tgl_registrasi  = '$tgl_reg',
                jam_reg         = '$time',
                kd_dokter       = '{$_POST['kd_dokter']}',
                no_rkm_medis    = '{$get_pasien['no_rkm_medis']}',
                kd_poli         = '{$_POST['kd_poli']}',
                p_jawab         = '{$get_pasien['namakeluarga']}',
                almt_pj         = '{$get_pasien['alamatpj']} - {$get_pasien['kelurahanpj']} - {$get_pasien['kecamatanpj']} - {$get_pasien['kabupatenpj']}',
                hubunganpj      = '{$get_pasien['keluarga']}',
                biaya_reg       = '{$biaya_reg['0']}',
                stts            = 'Belum',
                stts_daftar     = 'Lama',
                status_lanjut   = 'Ralan',
                kd_pj           = '{$_POST['kd_pj']}',
                umurdaftar      = '$umurdaftar',
                sttsumur        = 'Th',
                status_bayar    = 'Belum Bayar'
        ");

	    if($insert) {
	        redirect("pendaftaran.php?action=sukses&no_rawat=$no_rawat");
	    }

        }
} else {
    echo '<div class="alert bg-pink alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>Anda sudah terdaftar untuk tanggal '.$_POST[tgl_registrasi].'. Silahkan pilih tanggal periksa yang lain.</div>';

}
}


    ?>
	
<audio autoplay>
<source src="sound/poli.mp3" type="audio/mpeg">
</audio>

            <!-- Basic Validation -->
          	<div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="body">
                            <form id="form_validation" name="pilihan" action="" method="POST"  enctype="multipart/form-data">
                                <input type="hidden" name="tgl_registrasi" value="<?php echo $tanggal; ?>">
                                <label for="email_address">Nama Lengkap</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <?php
                                        $pasien = fetch_array(query("
                                            SELECT
                                                *
                                            FROM
                                                pasien
                                            WHERE
                                                no_rkm_medis = '{$_GET['no_rkm_medis']}'
                                        "));
                                        ?>
                                       <input type="text" id="nama_lengkap" value="<?php echo $pasien['nm_pasien']; ?>" class="form-control" disabled>
                                    </div>
                                </div>
                                <label for="email_address">Nomor Rekam Medik</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <input type="text" name="no_rkm_medis" value="<?php echo $_GET['no_rkm_medis']; ?>" class="form-control" disabled>
                                    </div>
                                </div>
                                <label for="email_address">Tanggal Kunjungan</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <input type="text" name="tgl_registrasi" value="<?php echo $_GET['tgl_registrasi']; ?>" class="form-control" disabled>
                                    </div>
                                </div>

                                <label for="email_address">Poliklinik Tujuan</label>
                                <div class="form-group">
                                    <div class="form-line">
            							<select class="form-control show-tick" name="kd_poli" onchange="showDokter()">
										    <option value="" selected >-- Pilih poliklinik --</option>
									    	<?php
											$result = query("
                                                SELECT
                                                    jadwal.kd_poli,poliklinik.nm_poli,
                                                    DATE_FORMAT(jadwal.jam_mulai, '%H:%i') AS jam_mulai,
                                                    DATE_FORMAT(jadwal.jam_selesai, '%H:%i') AS jam_selesai
                                                FROM
                                                    jadwal,
                                                    poliklinik,
                                                    dokter
                                                WHERE
                                                    jadwal.kd_poli = poliklinik.kd_poli
                                                AND
                                                    jadwal.kd_dokter = dokter.kd_dokter
                                                AND
                                                    hari_kerja LIKE '%$hari%'
                                                GROUP BY
                                                    poliklinik.kd_poli
                                            ");
											while($data = fetch_array($result)){
											    echo "<option value='".$data['kd_poli']."'>".$data['nm_poli']." [".$data['jam_mulai']." - ".$data['jam_selesai']."]</option>";
											}
                                            ?>
										</select>
                                    </div>
                                </div>

                                <script language="JavaScript" type="text/JavaScript">
                                function showDokter() {
                                <?php
                                $result = query("
                                    SELECT
                                        jadwal.kd_poli
                                    FROM
                                        jadwal,
                                        poliklinik,
                                        dokter
                                    WHERE
                                        jadwal.kd_poli = poliklinik.kd_poli
                                    AND
                                        jadwal.kd_dokter = dokter.kd_dokter
                                    AND
                                        jadwal.hari_kerja LIKE '%$hari%'
                                ");
                                while ($data = fetch_array($result)) {
                                    $idPoli = $data['kd_poli'];
                                    echo "if (document.pilihan.kd_poli.value == \"".$idPoli."\") {";
                                    $hasil2 = query("
                                        SELECT
                                            jadwal.kd_dokter,
                                            dokter.nm_dokter
                                        FROM
                                            jadwal,
                                            poliklinik,
                                            dokter
                                        WHERE
                                            jadwal.kd_poli = poliklinik.kd_poli
                                        AND
                                            jadwal.kd_dokter = dokter.kd_dokter
                                        AND
                                            jadwal.kd_poli = '$idPoli'
                                        AND
                                            jadwal.hari_kerja LIKE '%$hari%'
                                    ");
                                    $content = "document.getElementById('dokter').innerHTML = \"<div style='padding-left:10px;margin-right:24px;'><select name='kd_dokter' class='form-control show-tick'>";
                                        while ($data2 = fetch_array($hasil2)) {
                                            $content .= "<option value='".$data2['kd_dokter']."'>".$data2['nm_dokter']."</option>";
                                        }
                                        $content .= "</select></div>\";";
                                    echo $content;
                                echo "}\n";
                                }
                                ?>
                                }
                                </script>
                                <label for="email_address">Dokter Tujuan</label>
                                <div class="form-group">
                                    <div class="form-line" id="dokter">
                                    <select class="form-control show-tick">
                                        <option>-- Pilih dokter --</option>
                                    </select>
                                    </div>
                                </div>
                                <label for="email_address">Cara Bayar</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <select class="form-control show-tick" name="kd_pj" id="getFname" onchange="admSelectCheck(this);">
											<?php
                                            $result=query("
                                                SELECT
                                                    *
                                                FROM
                                                    penjab
                                                WHERE
                                                    png_jawab LIKE '%umum%'
                                                OR
                                                    png_jawab LIKE '%bpjs%'
                                                AND
                                                    kd_pj!='2'
                                                AND
                                                    kd_pj!='A14'
                                            ");
                                            while($row=fetch_array($result)){
											    echo "<option id='$row[png_jawab]' value='$row[kd_pj]'>$row[png_jawab]</option>";
											}?>
                                        </select>
                                        <div id="admDivCheck" style="display:none;">
                                                <!--<img id="image_upload_preview" width="200px" src="images/upload-rujukan.png" onclick="upload_rujukan()" style="cursor:pointer;" />-->
                                          		<br/>
                                          		<div class="alert bg-green alert-dismissible" role="alert">Anda memilih cara bayar BPJS. <br/>Apakah anda memiliki surat rujukan atau surat kontrol? <br/>Jika tidak, silahkan pilih cara bayar umum. <br/>Jika ya, silahkan lanjutkan pendaftaran anda.</div>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <input type="checkbox" id="checkbox" name="checkbox" required>
                                    <label for="checkbox">Saya menyetujui ketentuan dan persyaratan</label>
                                </div>
                                <button class="btn btn-primary waves-effect" type="submit" name="submit">SUBMIT</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# Basic Validation -->
    <?php } ?>
	
		
				
    <?php
    //edit
    $action      =isset($_GET['action'])?$_GET['action']:null;
    if($action  == "sukses"){
    $reg_det = fetch_array(query("
    SELECT
      f.nm_pasien,
      a.tgl_registrasi,
      a.jam_reg,
      a.no_rawat,
      a.no_reg,
      b.nm_poli,
      c.nm_dokter,
      a.status_lanjut,
      d.png_jawab,
      a.no_rkm_medis,
      f.alamat,
      f.umur
    FROM reg_periksa a
    LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli
    LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter
    LEFT JOIN penjab d ON a.kd_pj = d.kd_pj
    LEFT JOIN pasien f ON a.no_rkm_medis = f.no_rkm_medis
    WHERE a.no_rawat = '{$_GET['no_rawat']}'
    "));
    ?>


            <!-- Basic Validation -->
			<form name="MyForm" action="pendaftaran.php?action=selesai" method="post" style="margin-right: 15px; margin-left: 10px;">
			  <br><br>
              <div style="width: 70px; display: inline-block;">Tanggal </div>: <?php echo $reg_det['tgl_registrasi']; ?> <?php echo $reg_det['jam_reg']; ?><br>
              <div style="width: 70px; display: inline-block;">No. Antrian </div>: <div style="font-size: 20px; display: inline-block;"><?php echo $reg_det['no_reg']; ?></div><br>
              <div style="width: 70px; display: inline-block;">Nama </div>: <?php echo $reg_det['nm_pasien']; ?><br>
              <div style="width: 70px; display: inline-block;">No. RM </div>: <?php echo $reg_det['no_rkm_medis']; ?><br>
              <div style="width: 70px; display: inline-block;">Alamat </div>: <?php echo $reg_det['alamat']; ?><br>
              <div style="width: 70px; display: inline-block;">Ruang </div>: <?php echo $reg_det['nm_poli']; ?><br>
              <div style="width: 70px; display: inline-block;">Dokter </div>: <?php echo $reg_det['nm_dokter']; ?><br>
              <div style="width: 70px; display: inline-block;">Cara Bayar </div>: <?php echo $reg_det['png_jawab']; ?><br><br>

            <div style="text-align: center;">
            <hr>
            Terima Kasih Atas kepercayaan Anda.<br>
            Bawalah kartu Berobat anda dan datang 1 jam sebelumnya.<br>
            <hr>
            Jika memilih cara bayar UMUM, lakukan pembayaran di kasir terlebih dahulu sebelum ke Poliklinik tujuan.<br>
            Jika memilih cara bayar BPJS, bawalah surat rujukan atau surat kontrol asli dan tunjukkan pada petugas di Lobby resepsionis.<br><br>

            </div>
                <input type="button" class="btn btn-lg btn-primary waves-effect" value="CETAK BUKTI PENDAFTARAN" onclick="PrintMeSubmitMe(this)"><br><br>


			</form>
<audio autoplay>
<source src="sound/cetak.mp3" type="audio/mpeg">
</audio>

    <?php } ?>


    <?php
          if($action  == "selesai"){
            header( "refresh:15; url='index.php" );
	?>

          <script type="text/javascript">

 (function () {
  var timeLeft = 15,
  cinterval;

  var timeDec = function (){
  timeLeft--;
  document.getElementById('countdown').innerHTML = timeLeft;
  if(timeLeft === 0){
  clearInterval(cinterval);
    }
};

cinterval = setInterval(timeDec, 1000);
})();

</script>
<div style="font-size: 30px; text-align: center;">
Terima kasih. <br>
Pendaftaran anda telah selesai.<br>
Bawalah bukti pendaftaran anda saat berobat sesuai tanggal yang anda pilih.<br>
Halaman ini akan kembali ke halaman awal dalam <br><p style="font-size: 120px;" id="countdown">15</p>detik.
</div>
<br><br>

    <?php } ?>

        </div>
    </section>


<?php
include_once('layout/footer.php');
?>
