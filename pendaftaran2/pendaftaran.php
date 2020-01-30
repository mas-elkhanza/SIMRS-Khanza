<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : pendaftaran.php
* Description : Pendaftaran page
* Licence under GPL
***/

include_once ('layout/header.php');

?>

    <section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>PENDAFTARAN PASIEN</h2>
            </div>


    <?php
    $date_now   = date_create($date);
    date_add($date_now, date_interval_create_from_date_string(HARIDAFTAR.' days'));
    $date_next  = date_format($date_now, 'Y-m-d');

    $action     = isset($_GET['action'])?$_GET['action']:null;

    if(!$action){

        if($_SERVER['REQUEST_METHOD'] == "POST") {
            if($_POST['tgl_registrasi'] == $date && $time > LIMITJAM) {
                echo '<div class="alert bg-pink alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>Jam pendaftaran anda sudah lewat jam '.LIMITJAM.' WITA. Silahkan pilih tanggal periksa yang lain.</div>';
            } else if($_POST['tgl_registrasi'] < $date) {
                echo '<div class="alert bg-pink alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>Tanggal pendaftaran anda sudah lewat. Silahkan pilih tanggal periksa yang lain.</div>';
            } else if($_POST['tgl_registrasi'] > $date_next) {
                echo '<div class="alert bg-pink alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>Tanggal pendaftaran anda lebih dari hari yang ditentukan. Silahkan pilih tanggal periksa yang lain.</div>';
            } else {
                redirect("pendaftaran.php?action=pilih-poli&tgl_registrasi=$_POST[tgl_registrasi]");
            }
        }

    ?>
            <!-- Basic Validation -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="body">
                            <form id="form_validation" action="" method="POST">
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
                                                no_rkm_medis = '{$_SESSION['username']}'
                                        "));
                                        ?>
                                       <input type="text" id="nama_lengkap" value="<?php echo $pasien['nm_pasien']; ?>" class="form-control" disabled>
                                    </div>
                                </div>
                                <label for="email_address">Nomor Rekam Medik</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <input type="text" id="nomor_rm" value="<?php echo $_SESSION['username']; ?>" class="form-control" disabled>
                                    </div>
                                </div>
                                <label for="email_address">Tanggal Kunjungan</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <button type="button" class="btn btn-default btn-sm">
                                          <span class="glyphicon glyphicon-calendar"></span>
                                        </button>
                                        <input type="text" name="tgl_registrasi" class="datepicker form-control">
                                    </div>
                                </div>
                                    <button class="btn btn-primary waves-effect" type="submit" name="pilihpoli">PILIH POLI</button>
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

    $tanggal= @$_GET['tgl_registrasi'];
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
        $cek = fetch_array(query("SELECT no_rawat FROM reg_periksa WHERE no_rkm_medis='$_SESSION[username]' AND tgl_registrasi='$_POST[tgl_registrasi]'"));
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

            // Check file size not empty
            if ($_POST['kd_pj'] == "A02" &&  $_FILES["file"]["name"] == "") {
                $errors[] = 'Anda memilih cara bayar BPJS. Silahkan upload berkas rujukan anda.';
            }

            // Check file size
            if ($_FILES["file"]["size"] > UKURAN_BERKAS) {
                $errors[] = 'Ukuran berkas rujukan terlalu besar.';
            }
            
            if(!empty($errors)) {
                foreach($errors as $error) {
                    echo validation_errors($error);
                }
            } 
            else 
            {

                $get_pasien = fetch_array(query("SELECT * FROM pasien WHERE no_rkm_medis = '{$_SESSION['username']}'"));

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
                list($Y, $m, $d) = explode('-', date('Y-m-d', strtotime($get_pasien['tgl_lahir'])));
                $umurdaftar = $cY - $Y;

                $insert = query("
                    INSERT INTO reg_periksa
                    SET no_reg          = '$no_reg',
                        no_rawat        = '$no_rawat',
                        tgl_registrasi  = '$tgl_reg',
                        jam_reg         = '$time',
                        kd_dokter       = '{$_POST['kd_dokter']}',
                        no_rkm_medis    = '{$_SESSION['username']}',
                        kd_poli         = '{$_POST['kd_poli']}',
                        p_jawab         = '{$get_pasien['namakeluarga']}',
                        almt_pj         = '{$get_pasien['alamat']}',
                        hubunganpj      = '{$get_pasien['keluarga']}',
                        biaya_reg       = '{$biaya_reg['0']}',
                        stts            = 'Belum',
                        stts_daftar     = 'Lama',
                        status_lanjut   = 'Ralan',
                        kd_pj           = '{$_POST['kd_pj']}',
                        umurdaftar      = '$umurdaftar',
                        sttsumur        = 'Th',
                        status_bayar    = 'Belum Bayar',
                        limit_reg       = 1
                ");

                if($_FILES['file']['name']!='') {
                    $file='../berkasrawat/'.$photo_rujukan;
                    @unlink($file);
                    $tmp_name = $_FILES["file"]["tmp_name"];
                    $namefile = $_FILES["file"]["name"];
                    $ext = end(explode(".", $namefile));
                    $image_name = "rujukanfktp-".time().".".$ext;
                    move_uploaded_file($tmp_name,"../berkasrawat/pages/upload/".$image_name);
                    $lokasi_berkas = 'pages/upload/'.$image_name;
                    $insert_berkas = query("INSERT INTO berkas_digital_perawatan VALUES('$no_rawat', '$kode_berkas', '$lokasi_berkas')");
                }

                if($insert) {
                    redirect("pendaftaran.php?action=selesai&no_rawat=$no_rawat&kd_poli={$_POST['kd_poli']}&kd_dokter={$_POST['kd_dokter']}&tgl_registrasi=$tgl_reg");
                }
            }
            // End empty errors
        } 
        else 
        {
            echo '<div class="alert bg-pink alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>Anda sudah terdaftar untuk tanggal '.$_POST[tgl_registrasi].'. Silahkan pilih tanggal periksa yang lain.</div>';
        }

    }
    ?>
            <!-- Basic Validation -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="body">
                            <form id="form_validation" name="pilihan" action="" method="POST"  enctype="multipart/form-data">
                                <input type="hidden" name="tgl_registrasi" id="tgl_reg" value="<?php echo $tanggal; ?>">
                                <label for="email_address">Poliklinik Tujuan</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <select class="form-control show-tick" name="kd_poli" onchange="showDokter()" id="limit_poli">
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
                                    $content = "document.getElementById('dokter').innerHTML = \"<div style='padding-left:10px;margin-right:24px;'><select name='kd_dokter' id='kd_dok' class='form-control show-tick'>";
                                            $content .= "<option>--- Pilih Dokter ---</option>";
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
                                                    png_jawab LIKE '%UMUM%'
                                                OR
                                                    png_jawab LIKE '%JAMKESNAS%'
                                                OR
                                                    png_jawab LIKE '%BPJS%'
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
                                                <img id="image_upload_preview" width="200px" src="images/upload-rujukan.png" onclick="upload_rujukan()" style="cursor:pointer;" />
                                                <br/>
                                                <div class="alert bg-green alert-dismissible" role="alert">Anda memilih cara bayar BPJS. <br/>Apakah anda memiliki surat rujukan atau surat kontrol? <br/>Jika tidak, silahkan pilih cara bayar umum. <br/>Jika ya, silahkan lanjutkan pendaftaran anda dengan mengunggah/upload surat rujukan atau surat kontrol.</div>
                                                <input name="file" id="inputFile" type="file" style="display:none;" />
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <input type="checkbox" id="checkbox" name="checkbox" required>
                                    <label for="checkbox">Saya menyetujui ketentuan dan persyaratan</label>
                                </div>
                                <div id="sbm">
                                    <button class="btn btn-primary waves-effect" type="submit" name="submit">SUBMIT</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# Basic Validation -->
<?php  
} 
?>
    

    <?php
    //Halaman Jika sudah berhasil menyimpan pendaftaran online.
        $action      = isset($_GET['action'])?$_GET['action']:null;
        echo '<input type="hidden" value='.$action.' name="aksi" id="aksis">';
        if($action  == "selesai"){
            echo '<input type="hidden" id="no_rawats" name="no_rawats" value="'.$_GET['no_rawat'].'">';
            echo '<input type="hidden" id="kd_polis" name="kd_polis" value="'.$_GET['kd_poli'].'">';
            echo '<input type="hidden" id="kd_dokters" name="kd_dokters" value="'.$_GET['kd_dokter'].'">';
            echo '<input type="hidden" id="tgl_registrasis" name="tgl_registrasis" value="'.$_GET['tgl_registrasi'].'">';
            /*echo $_GET['no_rawat'];
            echo $_GET['kd_poli'];
            echo $_GET['kd_dokter'];
            echo $_GET['tgl_registrasi'];*/

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
              a.waktu_kunjungan,
              d.png_jawab
            FROM reg_periksa a
            LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli
            LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter
            LEFT JOIN penjab d ON a.kd_pj = d.kd_pj
            LEFT JOIN pasien f ON a.no_rkm_medis = f.no_rkm_medis
            WHERE a.no_rawat = '{$_GET['no_rawat']}'
            "));
    ?>
           
            <div class="alert bg-green alert-dismissible" role="alert">
            Terima Kasih Atas kepercayaan Anda.<br>
            Bawalah kartu Berobat anda dan datang 1 jam sebelum jadwal poliklinik yang dituju.<br>
            Jika memilih cara bayar JAMKESNAS, bawalah SURAT RUJUKAN / SURAT KONTROL asli dan tunjukkan pada petugas pendaftaran.
            </div>     

            <!-- Basic Validation -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div id="tess">
                            
                        </div>
                        <div class="body">
                                <label for="email_address">Waktu Datang</label>
                                <div class="form-group">
                                    <div class="form-line" id="waktu_kunjungan">
                                        
                                    </div>
                                </div>
                                <label for="email_address">Nama Lengkap</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <?php echo $reg_det['nm_pasien']; ?>
                                    </div>
                                </div>
                                <label for="email_address">Nomor Rekam Medik</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <?php echo $_SESSION['username']; ?>
                                    </div>
                                </div>
                                <label for="email_address">Tanggal Daftar</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <?php echo $reg_det['tgl_registrasi']; ?>
                                    </div>
                                </div>
                                <label for="email_address">Jam Daftar</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <?php echo $reg_det['jam_reg']; ?>
                                    </div>
                                </div>
                                <label for="email_address">Nomor Urut</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <?php echo $reg_det['no_reg']; ?>
                                    </div>
                                </div>
                                <label for="email_address">Poliklinik Tujuan</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <?php echo $reg_det['nm_poli']; ?>
                                    </div>
                                </div>
                                <label for="email_address">Dokter Tujuan</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <?php echo $reg_det['nm_dokter']; ?>
                                    </div>
                                </div>
                                <label for="email_address">Cara Bayar</label>
                                <div class="form-group">
                                    <div class="form-line">
                                        <?php echo $reg_det['png_jawab']; ?>
                                    </div>
                                </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# Basic Validation -->
           
    <?php 
        } 
    ?>

        </div>
    </section>

    <?php  
    $link = 'http://'.$_SERVER['HTTP_HOST']; 
    ?>
    <div id="myModal" class="modal fade" role="dialog" style="display: none">
      <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
            <h4 class="modal-title">Informasi Pendaftaran Online</h4>
          </div>
          <div class="modal-body">
            <p>Maaf untuk pendaftaran online sudah penuh pada poli <span id="nmp"></span></p>
          </div>
          <div class="modal-footer">
            <a href="<?php echo $link; ?>/webapps/pendaftaran-ori" class="btn btn-primary" >Kembali</a>
          </div>
        </div>

      </div>
    </div>

    <div id="Cuti" class="modal fade" role="dialog" style="display: none">
      <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal">&times;</button>
            <h4 class="modal-title">Informasi Pendaftaran Online</h4>
          </div>
          <div class="modal-body">
            <p>Maaf untuk pendaftaran online pada Dokter <span id="nm_dokter"></span> sedang cuti.</p>
          </div>
          <div class="modal-footer">
            <a href="<?php echo $link; ?>/webapps/pendaftaran-ori" class="btn btn-primary" >Kembali</a>
          </div>
        </div>

      </div>
    </div>
<?php include_once ('layout/footer.php'); ?>
