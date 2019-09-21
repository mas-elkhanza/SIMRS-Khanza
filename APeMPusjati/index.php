<?php
include ('config.php');
?>
<!doctype html>
<html lang="en">
  <head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <link rel="stylesheet" href="fontawesome-free-5.6.3-web/css/all.css">
    <link href="css/gijgo.min.css" rel="stylesheet" type="text/css" />

    <style>
    .modal-full {
      min-width: 100%;
      margin: 0;
    }
    .modal-full .modal-content {
      min-height: 100vh;
    }
    .modal-fix {
      min-width: 1024px;
      margin: 0;
    }
    .modal-fix .modal-content {
      min-height: 100vh;
    }
    .modal .tab-content {
      min-height: 50vh;
    }
    .nav-pills.nav-wizard > li {
      position: relative;
      overflow: visible;
      border-right: 8px solid transparent;
      border-left: 8px solid transparent;
    }

    .nav-pills.nav-wizard > li + li {
      margin-left: 0;
    }

    .nav-pills.nav-wizard > li:first-child {
      border-left: 0;
    }

    .nav-pills.nav-wizard > li:first-child a {
      border-radius: 5px 0 0 5px;
    }

    .nav-pills.nav-wizard > li:last-child {
      border-right: 0;
    }

    .nav-pills.nav-wizard > li:last-child a {
      border-radius: 0 5px 5px 0;
    }

    .nav-pills.nav-wizard > li a {
      border-radius: 0;
      background-color: #eee;
    }

    .nav-pills.nav-wizard > li:not(:last-child) a:after {
      position: absolute;
      content: "";
      top: 0px;
      right: -20px;
      width: 0px;
      height: 0px;
      border-style: solid;
      border-width: 20px 0 20px 20px;
      border-color: transparent transparent transparent #eee;
      z-index: 150;
    }

    .nav-pills.nav-wizard > li:not(:first-child) a:before {
      position: absolute;
      content: "";
      top: 0px;
      left: -20px;
      width: 0px;
      height: 0px;
      border-style: solid;
      border-width: 20px 0 20px 20px;
      border-color: #eee #eee #eee transparent;
      z-index: 150;
    }

    .nav-pills.nav-wizard > li:hover:not(:last-child) a:after {
      border-color: transparent transparent transparent #aaa;
    }

    .nav-pills.nav-wizard > li:hover:not(:first-child) a:before {
      border-color: #aaa #aaa #aaa transparent;
    }

    .nav-pills.nav-wizard > li:hover a {
      background-color: #aaa;
      color: #fff;
    }

    .nav-pills.nav-wizard > li:not(:last-child) a.active:after {
      border-color: transparent transparent transparent #428bca;
    }

    .nav-pills.nav-wizard > li:not(:first-child) a.active:before {
      border-color: #428bca #428bca #428bca transparent;
    }

    .nav-pills.nav-wizard > li a.active {
      background-color: #428bca;
    }
    </style>
	
    <title>APeMPusjati</title>
  </head>
  <body>
    <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
      <h1 class="display-2">APeM Pusjati</h1>
      <h3 class="display-6">Anjungan Pendaftaran Mandiri Pasien</h3>
      <h2 class="display-5"><?php echo $dataSettings['nama_instansi']; ?></h2>
    </div>
    <br><br>
    <div class="container">
      <div class="card-deck mb-3 text-center">
        <div class="card mb-4 shadow-sm" data-toggle="modal" data-target="#antrian">
          <div class="card-body btn btn-lg btn-success">
            <ul class="list-unstyled mt-3 mb-4">
              <span style="font-size: 120px; color: white;"><i class="fas fa-question"></i></span>
            </ul>
            <a href="#" style="text-decoration: none; color: white;"><h1 class="display-4">ANTRIAN LOKET</h1></a>
          </div>
        </div>

        <div class="card mb-4 shadow-sm" data-toggle="modal" data-target="#daftar">
          <div class="card-body btn btn-lg btn-primary">
            <ul class="list-unstyled mt-3 mb-4">
              <span style="font-size: 120px; color: white;"><i class="fas fa-stethoscope"></i></span>
            </ul>
            <h1 class="display-4">DAFTAR MANDIRI</h1>
          </div>
        </div>
        
        
        
      </div>
    </div>        

    <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center text-danger">
      <h3 class="display-6">Silahkan hubungi petugas Puskesmas jika anda mengalami kesulitan.</h3>
    </div>

    <!-- Modal Antrian -->
	 <div class="col-xs-6 col-md-6 big-box" >
	 
    <div  class="modal fade" id="antrian" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-lg  modal-fix" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalCenterTitle"><font color='blue' align='center'>Antrian Loket Puskesmas Tunggakjati</font></h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>		
          </div>
          <div class="modal-body">
            <div class="row" class="col-md-12">
            <div class="col-4 pt-4 pb-4">
              <div id="printAntrianLoket" style="display: none;" class="cetak">
                <div style="width: 180px; font-family: Tahoma; margin-top: 10px; margin-right: 5px; margin-bottom: 100px; margin-left: 15px; font-size: 21px !important;">
                  <div id="print_nomor_loket"></div>
                  Loket Pasien Biasa
                </div>
              </div>
              <div id="display_nomor_loket"></div>
              <form role="form" id="formloket" name="formloket">
                <button type="submit" class="btn btn-lg btn-danger btn-block" id="btnKRM" value="Submit" name="btnKRM" onclick="printDiv('printAntrianLoket');">PASIEN BIASA</button>
              </form>
            </div>
            <div class="col-4 pt-4 pb-4">
              <div id="printAntrianCS" style="display: none;" class="cetak">
                <div style="width: 180px; font-family: Tahoma; margin-top: 10px; margin-right: 5px; margin-bottom: 100px; margin-left: 15px; font-size: 18px !important;">
                  <div id="print_nomor_cs"></div>
                  Loket Pasien Prioritas
                </div>
              </div>
              <div id="display_nomor_cs"></div>
              <form role="form" id="formcs" name="formcs">
                <button type="submit" class="btn btn-lg btn-danger btn-block" id="btnKRMCS" value="Submit" name="btnKRMCS" onclick="printDiv('printAntrianCS');">PASIEN PRIORITAS</button>
              </form>
            </div>
            
	</div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Tutup</button>
          </div>
        </div>
      </div>
    </div>
 </div>
    <!-- Modal Daftar -->
    <div class="modal fade" id="daftar" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-lg modal-fix" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalCenterTitle">Pendaftaran Pasien Mandiri</h5>
            <button type="button" class="close" data-dismiss="modal" onclick="javascript:window.location='/APeMPusjati/'" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <ul class="nav nav-tabs mt-3 pl-2 pr-2">
            <li class="nav-item">
              <a class="nav-link active" data-toggle="tab" href="#umum">Umum/ BPJS/ KIS/ Karawang Sehat</a>
            </li>
          </ul>
          <!-- Tab panes -->
          <div class="tab-content">
            <div class="tab-pane container active" id="umum">

              <div class="modal-body">
                <ul class="nav nav-pills nav-wizard" id="myTab" role="tablist">
                  <li class="nav-item">
                    <a class="nav-link active" data-toggle="tab" href="#infoPanel" role="tab">No. RM</a>
                  <li>
                  <li class="nav-item">
                    <a class="nav-link" data-toggle="tab" href="#poli" role="tab">Pilih Poli dan Dokter</a>
                  <li>
                  <li class="nav-item">
                    <a class="nav-link" data-toggle="tab" href="#reviewPrint" role="tab">Cetak</a>
                  <li>
                </ul>

                <div class="tab-content mt-2">
                  <div class="tab-pane fade show active" id="infoPanel" role="tabpanel">
                    <h4>Data Rekam Medik</h4>
                    <div class="form-group">
                      <label for="campaignName">Nomor Kartu Berobat</label>
                      <input type="text" class="form-text form-control form-control-lg" id="no_rm" value=""></input>
                    </div>
                    <button class="btn btn-secondary" id="infoContinue">Cek Kartu</button>
                  </div>
                  <div class="tab-pane fade" id="poli" role="tabpanel">
                    <h4>Informasi Pribadi, Klinik dan Dokter Tujuan</h4>
                    <div class="form-row">
                  		<div class="col form-group">
                  			<label>Nomor Rekam Medik</label>
                  		  	<input type="text" name="no_rkm_medis_daftar" class="form-control" placeholder="" id="no_rkm_medis_daftar" disabled>
                  		</div>
                  		<div class="col form-group">
                  			<label>Nama Lengkap</label>
                  		  	<input type="text" name="nm_pasien_daftar" class="form-control" placeholder=" " id="nm_pasien_daftar" disabled>
                  		</div>
                  	</div>
                    <div class="form-group">
                  		<label>Alamat Lengkap</label>
                  		<textarea class="form-control" placeholder="" id="alamat_daftar" disabled></textarea>
                  	</div>
                  	<div class="form-group">
                  		<label>Tanggal Kunjungan</label>
                  		<input type="text" class="form-control" name="datepicker" id="tgl_registrasi_daftar" placeholder="">
                  	</div>
                    <div class="form-row">
                      <div class="col form-group">
                        <label>Kode Poli</label>
                        <select id="pilih_poli_daftar" class="form-control">
                          <option value="">Pilih Poli</option>
                        </select>
                  	   </div>
                       <div class="col form-group">
                        <label>Kode Dokter</label>
                         <select id="pilih_dokter_daftar" class="form-control">
                           <option value="">Pilih Dokter</option>
                         </select>
                       </div>
                   	</div>
                    <button class="btn btn-secondary" id="finish">Simpan Pendaftaran</button>
                  </div>
                  <div class="tab-pane fade" id="reviewPrint" role="tabpanel">
                    <h4>Cetak Bukti Daftar</h4>
                    <div style="width: 200px; font-family: Tahoma; margin: 0 auto; font-size: 11px !important;">


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



             		    <hr>
                    <div style="width: 50px; display: inline-block;">Tanggal</div>: <span id="tgl_registrasi_cetak"></span><br>
                    <div style="width: 50px; display: inline-block;">No. RM</div>: <span id="no_rkm_medis_cetak"></span><br>
                    <div style="width: 50px; display: inline-block;">Nama</div>: <span id="nm_pasien_cetak"></span><br>
                    <div style="width: 50px; display: inline-block;">Klinik</div>: <span id="nm_poli_cetak"></span><br>
                    <div style="width: 50px; display: inline-block;">Dokter</div>: <span id="nm_dokter_cetak"></span><br>

                    <div style="text-align: center;">
                      <hr>
                      Terima Kasih Atas kepercayaan Anda.<br>
                      Berikan Bukti Registrasi ini Kepada Petugas Pendaftaran.<br>
                      <hr>
                      
                    </div>

                    <div id="printDaftar" style="display: none;" class="cetak">
                      <section style="margin: 0 auto; width: 250px; font-family: Tahoma; font-size: 11px !important;">
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
                      <br>
                      <div style="width: 70px; display: inline-block;">Tanggal </div>:<span id="tgl_registrasi_final"></span><br>
                      <div style="width: 70px; display: inline-block;">No. Antrian </div>: <span id="no_reg_final"></span><br>
                      <div style="width: 70px; display: inline-block;">Nama </div>: <span id="nm_pasien_final"></span><br>
                      <div style="width: 70px; display: inline-block;">No. RM </div>: <span id="no_rkm_medis_final"></span><br>
                      <div style="width: 70px; display: inline-block;">Alamat </div>: <span id="alamat_final"></span><br>
                      <div style="width: 70px; display: inline-block;">Ruang </div>: <span id="nm_poli_final"></span><br>
                      <div style="width: 70px; display: inline-block;">Dokter </div>: <span id="nm_dokter_final"></span><br>
                      <div style="width: 70px; display: inline-block;">Cara Bayar </div>: <span id="cara_bayar_final"></span><br>

                    <div style="text-align: center;">
                      <hr>
                      Terima Kasih Atas kepercayaan Anda.<br>
                      Bawalah kartu Berobat anda dan datang 30 menit sebelumnya.<br>
                      <hr>
                      Bawalah surat rujukan atau surat kontrol asli dan tunjukkan pada petugas di Lobby resepsionis.<br><br>
                    </div>
                    </section>
                    </div>
                    <button class="btn btn-primary btn-block" onclick="printDiv('printDaftar');">Cetak</button>
                    </div>
                  </div>
                </div>
                <div class="progress mt-5">
                  <div class="progress-bar" role="progressbar" style="width: 35%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">Step 1 of 3</div>
                </div>
              </div>

            </div>

            <div class="tab-pane container fade" id="bpjs">

              <div class="modal-body">
                <ul class="nav nav-pills nav-wizard" id="myTab" role="tablist">
                  <li class="nav-item">
                    <a class="nav-link active" data-toggle="tab" href="#infonorawat" role="tab">No. Rawat</a>
                  <li>
                  <li class="nav-item">
                    <a class="nav-link" data-toggle="tab" href="#reviewsep" role="tab">Review SEP</a>
                  <li>
                  <li class="nav-item">
                    <a class="nav-link" data-toggle="tab" href="#reviewCetak" role="tab">Cetak</a>
                  <li>
                </ul>

                <div class="tab-content mt-2">
                  <div class="tab-pane fade show active" id="infonorawat" role="tabpanel">
                    <h4>Data Pendaftaran</h4>
                    <div class="form-group">
                      <label for="campaignName">Nomor Perawatan</label>
                      <input type="text" class="form-text form-control form-control-lg" id="no_rawat" value=""></input>
                    </div>
                    <button class="btn btn-secondary" id="infoPendaftaran">Cek Nomor Rawat</button>
                  </div>
                  <div class="tab-pane fade" id="reviewsep" role="tabpanel">
                    <section class="content">
                        <div class="container-fluid">
                            <div class="row clearfix">
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <div class="card">
                                        <div class="body">
                                          <div class="container" style="margin-top : 50px;" action="cetak">
                                            <div class="row clearfix">
                                              <div class="col-md-4">
                                                <div class="form-group">
                                                  <img src="img/bpjslogo.png" height="60" width="350">
                                                </div>
                                              </div>
                                              <div class="col-md-8">
                                                <div class="form-group">
                                                  <h2><center>SURAT ELEGIBILITAS PESERTA</center></h2>
                                                  <h5><center><?php echo $dataSettings['nama_instansi']; ?></center></h5>
                                                </div>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                No. SEP
                                              </div>
                                              <div class="col-md-10">
                                                : <span id="sep_no_sep"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Tgl. SEP
                                              </div>
                                              <div class="col-md-10">
                                                : <span id="sep_tglsep"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                No. Kartu
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="sep_no_kartu"></span>
                                              </div>
                                              <div class="col-md-2">
                                                Jenis Kelamin
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="sep_jkel"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Nama Peserta
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="sep_nama_pasien"></span>
                                              </div>
                                              <div class="col-md-2">
                                                No. Rawat
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="sep_no_rawat"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Tgl. Lahir
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="sep_tanggal_lahir"></span>
                                              </div>
                                              <div class="col-md-2">
                                                No. Reg
                                              </div>
                                              <div class="col-md-4">
                                                :
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                No.Telepon
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="sep_notelep"></span>
                                              </div>
                                              <div class="col-md-2">
                                                Peserta
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="sep_peserta"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Sub/Spesialis
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="sep_nmpolitujuan"></span>
                                              </div>
                                              <div class="col-md-2">
                                                COB
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="sep_cob"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Faskes Perujuk
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="sep_nmppkrujukan"></span>
                                              </div>
                                              <div class="col-md-2">
                                                Jns. Rawat
                                              </div>
                                              <div class="col-md-4">
                                                : Rawat Jalan
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Diagnosa Awal
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="sep_nmdiagnosaawal"></span>
                                              </div>
                                              <div class="col-md-2">
                                                Kls. Rawat
                                              </div>
                                              <div class="col-md-4">
                                                : Kelas <span id="sep_klsrawat"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-6">

                                              </div>
                                              <div class="col-md-2">
                                                Penjamin
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="sep_penjamin"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Catatan
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="sep_catatan"></span>
                                              </div>
                                              <div class="col-md-6" style="left: 90px;">
                                                Pasien/Keluarga Pasien
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-6">
                                                <small><em><font size="1px">*Saya Menyetujui BPJS Kesehatan menggunakan informasi Medis Pasien jika diperlukan.</font></em></small>
                                              </div>
                                              <div class="col-md-6">
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-6">
                                              </div>
                                              <div class="col-md-6">
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-6">
                                                <small><em><font size="1px">**SEP bukan sebagai bukti penjaminan peserta</font></em></small>
                                              </div>
                                              <div class="col-md-6">
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                <small><em><font size="1px">Cetakan ke 1</font></em></small>
                                              </div>
                                              <div class="col-md-10">
                                                <small><em><font size="1px"><?php echo date("d/m/Y h:i:s A");?></font></em></small>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-4">
                                              </div>
                                              <div class="col-md-8">
                                                <center>----------------------------------------------------</center>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    <br>
                    <button class="btn btn-secondary" id="cetak">Simpan SEP</button>
                  </div>
                  <div class="tab-pane fade" id="reviewCetak" role="tabpanel">
                    <section class="content">
                        <div class="container-fluid">
                            <div class="row clearfix">
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <div class="card">
                                        <div class="body">
                                          <div class="container" style="margin-top : 50px;" action="cetak">
                                            <div class="row clearfix">
                                              <div class="col-md-4">
                                                <div class="form-group">
                                                  <img src="img/bpjslogo.png" height="60" width="350">
                                                </div>
                                              </div>
                                              <div class="col-md-8">
                                                <div class="form-group">
                                                  <h2><center>SURAT ELEGIBILITAS PESERTA</center></h2>
                                                  <h5><center><?php echo $dataSettings['nama_instansi']; ?></center></h5>
                                                </div>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                No. SEP
                                              </div>
                                              <div class="col-md-10">
                                                : <span id="_sep_no_sep"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Tgl. SEP
                                              </div>
                                              <div class="col-md-10">
                                                : <span id="_sep_tglsep"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                No. Kartu
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="_sep_no_kartu"></span>
                                              </div>
                                              <div class="col-md-2">
                                                Jenis Kelamin
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="_sep_jkel"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Nama Peserta
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="_sep_nama_pasien"></span>
                                              </div>
                                              <div class="col-md-2">
                                                No. Rawat
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="_sep_no_rawat"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Tgl. Lahir
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="_sep_tanggal_lahir"></span>
                                              </div>
                                              <div class="col-md-2">
                                                No. Reg
                                              </div>
                                              <div class="col-md-4">
                                                :
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                No.Telepon
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="_sep_notelep"></span>
                                              </div>
                                              <div class="col-md-2">
                                                Peserta
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="_sep_peserta"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Sub/Spesialis
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="_sep_nmpolitujuan"></span>
                                              </div>
                                              <div class="col-md-2">
                                                COB
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="_sep_cob"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Faskes Perujuk
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="_sep_nmppkrujukan"></span>
                                              </div>
                                              <div class="col-md-2">
                                                Jns. Rawat
                                              </div>
                                              <div class="col-md-4">
                                                : Rawat Jalan
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Diagnosa Awal
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="_sep_nmdiagnosaawal"></span>
                                              </div>
                                              <div class="col-md-2">
                                                Kls. Rawat
                                              </div>
                                              <div class="col-md-4">
                                                : Kelas <span id="_sep_klsrawat"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-6">

                                              </div>
                                              <div class="col-md-2">
                                                Penjamin
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="_sep_penjamin"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Catatan
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="_sep_catatan"></span>
                                              </div>
                                              <div class="col-md-6" style="left: 90px;">
                                                Pasien/Keluarga Pasien
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-6">
                                                <small><em><font size="1px">*Saya Menyetujui BPJS Kesehatan menggunakan informasi Medis Pasien jika diperlukan.</font></em></small>
                                              </div>
                                              <div class="col-md-6">
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-6">
                                              </div>
                                              <div class="col-md-6">
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-6">
                                                <small><em><font size="1px">**SEP bukan sebagai bukti penjaminan peserta</font></em></small>
                                              </div>
                                              <div class="col-md-6">
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                <small><em><font size="1px">Cetakan ke 1</font></em></small>
                                              </div>
                                              <div class="col-md-10">
                                                <small><em><font size="1px"><?php echo date("d/m/Y h:i:s A");?></font></em></small>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-4">
                                              </div>
                                              <div class="col-md-8">
                                                <center>----------------------------------------------------</center>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    <br>

                    <section class="content" id="printSEP" style="display: none;">
                      <link rel="stylesheet" href="css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
                        <div class="container-fluid">
                            <div class="row clearfix">
                                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                    <div class="card">
                                        <div class="body">
                                          <div class="container" style="margin-top : 50px;" action="cetak">
                                            <div class="row clearfix">
                                              <div class="col-md-4">
                                                <div class="form-group">
                                                  <img src="img/bpjslogo.png" height="60" width="350">
                                                </div>
                                              </div>
                                              <div class="col-md-8">
                                                <div class="form-group">
                                                  <h2><center>SURAT ELEGIBILITAS PESERTA</center></h2>
                                                  <h5><center><?php echo $dataSettings['nama_instansi']; ?></center></h5>
                                                </div>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                No. SEP
                                              </div>
                                              <div class="col-md-10">
                                                : <span id="__sep_no_sep"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Tgl. SEP
                                              </div>
                                              <div class="col-md-10">
                                                : <span id="__sep_tglsep"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                No. Kartu
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="__sep_no_kartu"></span>
                                              </div>
                                              <div class="col-md-2">
                                                Jenis Kelamin
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="__sep_jkel"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Nama Peserta
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="__sep_nama_pasien"></span>
                                              </div>
                                              <div class="col-md-2">
                                                No. Rawat
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="__sep_no_rawat"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Tgl. Lahir
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="__sep_tanggal_lahir"></span>
                                              </div>
                                              <div class="col-md-2">
                                                No. Reg
                                              </div>
                                              <div class="col-md-4">
                                                :
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                No.Telepon
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="__sep_notelep"></span>
                                              </div>
                                              <div class="col-md-2">
                                                Peserta
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="__sep_peserta"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Sub/Spesialis
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="__sep_nmpolitujuan"></span>
                                              </div>
                                              <div class="col-md-2">
                                                COB
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="__sep_cob"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Faskes Perujuk
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="__sep_nmppkrujukan"></span>
                                              </div>
                                              <div class="col-md-2">
                                                Jns. Rawat
                                              </div>
                                              <div class="col-md-4">
                                                : Rawat Jalan
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Diagnosa Awal
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="__sep_nmdiagnosaawal"></span>
                                              </div>
                                              <div class="col-md-2">
                                                Kls. Rawat
                                              </div>
                                              <div class="col-md-4">
                                                : Kelas <span id="_sep_klsrawat"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-6">

                                              </div>
                                              <div class="col-md-2">
                                                Penjamin
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="__sep_penjamin"></span>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                Catatan
                                              </div>
                                              <div class="col-md-4">
                                                : <span id="__sep_catatan"></span>
                                              </div>
                                              <div class="col-md-6" style="left: 90px;">
                                                Pasien/Keluarga Pasien
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-6">
                                                <small><em><font size="1px">*Saya Menyetujui BPJS Kesehatan menggunakan informasi Medis Pasien jika diperlukan.</font></em></small>
                                              </div>
                                              <div class="col-md-6">
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-6">
                                              </div>
                                              <div class="col-md-6">
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-6">
                                                <small><em><font size="1px">**SEP bukan sebagai bukti penjaminan peserta</font></em></small>
                                              </div>
                                              <div class="col-md-6">
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-2">
                                                <small><em><font size="1px">Cetakan ke 1</font></em></small>
                                              </div>
                                              <div class="col-md-10">
                                                <small><em><font size="1px"><?php echo date("d/m/Y h:i:s A");?></font></em></small>
                                              </div>
                                            </div>
                                            <div class="row clearfix">
                                              <div class="col-md-4">
                                              </div>
                                              <div class="col-md-8">
                                                <center>----------------------------------------------------</center>
                                              </div>
                                            </div>
                                          </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>

                    <center><button style="width:200px;" class="btn btn-primary btn-block" onclick="printSEP('printSEP');">Cetak</button></center>
                  </div>
                </div>
                <div class="progress mt-5">
                  <div class="progress-bar" role="progressbar" style="width: 35%" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100">Step 1 of 3</div>
                </div>
              </div>

            </div>

          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="javascript:window.location='/APeMPusjati/'">Tutup</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Modal Kontrol -->
    <div class="modal fade" id="kontrol" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalCenterTitle">Cetak Surat Kontrol</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">
        	<div>
              <div class="form-group">
                <input type="text" class="form-control form-control-lg" id="norm" placeholder="Nomor Kartu Berobat">
              </div>
              <div class="form-group">
                <!--<input type="text" class="form-control form-control-lg" id="kdpoli" placeholder="Klinik Tujuan">-->
                <select class="form-control form-control-lg" name="kd_poli" id="kdpoli">
                  <option value="" selected >-- Pilih poliklinik --</option>
                  <?php
                    $result=query("SELECT * FROM poliklinik WHERE nm_poli LIKE '%Poli%'");
                    while($row=fetch_array($result)){
                      echo "<option id='$row[kd_poli]' value='$row[kd_poli]'>$row[nm_poli]</option>";
                  }?>
                </select>
              </div>
              <div class="get-content" style="display: none;">
                  <br>
                  <h4>Antrian Kontrol</h4>
                  <br>
                  <p>No RM: <span id="no_rkm_medis"></span></p>
                  <p>Tanggal Kontrol: <span id="tanggal_datang"></span></p>
                  <p>Klinik Tujuan: <span id="nm_poli"></span></p>
                  <br><br>
                  <button type="button" class="btn btn-lg btn-danger" onclick="printDiv('printSKDP');">Cetak</button>
                </div>
                <div id="printSKDP" style="display: none;" class="cetak">
                  <div style="width: 180px; font-family: Tahoma; margin-top: 10px; margin-right: 5px; margin-bottom: 100px; margin-left: 15px; font-size: 11px !important;">
                  <h4>Surat Kontrol (SKDP)</h4>
                  <div style="width: 50px; display: inline-block;">No. Surat</div>: <span id="c_no_antrian"></span><br>
           		    <hr>
                  <div style="width: 50px; display: inline-block;">Tanggal</div>: <span id="c_tanggal_datang"></span><br>
                  <div style="width: 50px; display: inline-block;">Antrian</div>: <span id="c_no_reg"></span><br>
                  <div style="width: 50px; display: inline-block;">No. RM</div>: <span id="c_no_rkm_medis"></span><br>
                  <div style="width: 50px; display: inline-block;">Nama</div>: <span id="c_nm_pasien"></span><br>
                  <div style="width: 50px; display: inline-block;">Diagnosa</div>: <span id="c_diagnosa"></span><br>
                  <div style="width: 50px; display: inline-block;">Terapi</div>: <span id="c_terapi"></span><br>
                  <div style="width: 50px; display: inline-block;">Klinik</div>: <span id="c_nm_poli"></span><br>
                  <div style="width: 50px; display: inline-block;">Dokter</div>: <span id="c_nm_dokter"></span><br>

                  <div style="text-align: center;">
                    <hr>
                    Terima Kasih Atas kepercayaan Anda.<br>
                    Bawalah kartu Berobat anda dan datang 30 menit sebelumnya.<br>
                    <hr>
                    Bawalah surat rujukan atau surat kontrol asli dan tunjukkan pada petugas di Lobby resepsionis.<br><br>
                  </div>
                  </div>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-lg btn-secondary" data-dismiss="modal">Tutup</button>
            <button type="submit" id="getNoRM" class="btn btn-lg btn-primary">Cek Surat Kontrol</button>
          </div>
        </div>
      </div>
    </div>


    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="js/jquery.min.js"></script>
    <script src="js/popper.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/gijgo.min.js" type="text/javascript"></script>
    <script>
    $(document).ready(function(){
      $('#getNoRM').on('click',function(){
        var no_rkm_medis = $('#norm').val();
        var kd_poli = $('#kdpoli').val();
        $.ajax({
          type:'POST',
          url:'get-skdp.php',
          dataType: "json",
          data: {no_rkm_medis:no_rkm_medis, kd_poli:kd_poli},
          success:function(data){
            if(data.status == 'ok'){
              $('#no_rkm_medis').text(data.result.no_rkm_medis);
              $('#tanggal_datang').text(data.result.tanggal_datang);
              $('#nm_poli').text(data.result.nm_poli);
              $('#c_no_rkm_medis').text(data.result.no_rkm_medis);
              $('#c_nm_pasien').text(data.result.nm_pasien);
              $('#c_diagnosa').text(data.result.diagnosa);
              $('#c_terapi').text(data.result.terapi);
              $('#c_alasan1').text(data.result.alasan1);
              $('#c_alasan2').text(data.result.alasan2);
              $('#c_rtl1').text(data.result.rtl1);
              $('#c_rtl2').text(data.result.rtl2);
              $('#c_tanggal_datang').text(data.result.tanggal_datang);
              $('#c_tanggal_rujukan').text(data.result.tanggal_rujukan);
              $('#c_no_antrian').text(data.result.no_antrian);
              $('#c_nm_dokter').text(data.result.nm_dokter);
              $('#c_nm_poli').text(data.result.nm_poli);
              $('#c_no_reg').text(data.result.no_reg);
              $('.get-content').slideDown();
            }else{
              $('.get-content').slideUp();
              alert("Surat kontrol tidak ditemukan...");
            }
          }
        });
      });
    });
    </script>
    <script>
    $(document).ready(function() {
      $("#display_nomor_loket").load("get-antrian.php?aksi=tampilloket");
      $("#print_nomor_loket").load("get-antrian.php?aksi=tampilloket");
      $("#display_nomor_cs").load("get-antrian.php?aksi=tampilcs");
      $("#print_nomor_cs").load("get-antrian.php?aksi=tampilcs");
	  $("#display_nomor_pr").load("get-antrian.php?aksi=tampilpr");
      $("#print_nomor_pr").load("get-antrian.php?aksi=tampilpr");
    })
    </script>
    <script>
    function printDiv(eleId){
        var PW = window.open('', '_blank', 'Print content');
        //Use css for print style
        //PW.document.write('<style>.cetak {width: 250px; font-family: Tahoma; margin-top: 10px; margin-right: 5px; margin-bottom: 50px; margin-left: 5px; font-size: 11px !important;}</style>');
        PW.document.write(document.getElementById(eleId).innerHTML);
        PW.document.close();
        PW.focus();
        PW.print();
        PW.close();
        // Redirect after close
        window.location.href = "index.php";
    }
    </script>

    <script>
    function printSEP(eleId){
        var PW = window.open('', '_blank', 'Print content');
        //Use css for print style
        PW.document.write('<link rel="stylesheet" href="../css/bootstrap.min.css">');
        PW.document.write(document.getElementById(eleId).innerHTML);
        PW.document.close();
        PW.focus();
        PW.print();
        PW.close();
        // Redirect after close
        window.location.href = "index.php";
    }
    </script>

    <script>
    $(function () {

      $('#infoContinue').click(function (e) {
        var no_rkm_medis_daftar = $('#no_rm').val();
        $.ajax({
          type:'POST',
          url:'get-daftar.php',
          dataType: "json",
          data: {no_rkm_medis_daftar:no_rkm_medis_daftar},
          success:function(data){
            if(data.status == 'ok'){
              e.preventDefault();
              $('.progress-bar').css('width', '70%');
              $('.progress-bar').html('Step 2 of 3');
              $('#myTab a[href="#poli"]').tab('show');
              $('#no_rkm_medis_daftar').val(data.result.no_rkm_medis);
              $('#nm_pasien_daftar').val(data.result.nm_pasien);
              $('#alamat_daftar').val(data.result.alamat);
            }else{
              alert("Detail nomor rekam medik tidak ditemukan...");
              document.getElementById("no_rm").value = "";
            }
          }
        });

        setTimeout(function() { window.location.href = "/anjungan/"; }, 500000);

      });

      $('input[name="datepicker"]').datepicker({
        uiLibrary: 'bootstrap4',
        format: 'yyyy-mm-dd',
        modal: true,
        change: function (e) {
          var no_rkm_medis_daftar = $('#no_rkm_medis_daftar').val();
          var tgl_registrasi_daftar = $('#tgl_registrasi_daftar').val();
          $.ajax({
            type:'POST',
            url:'get-poli.php',
            dataType: "json",
            data: {no_rkm_medis:no_rkm_medis_daftar, tgl_registrasi:tgl_registrasi_daftar},
            success:function(data){
              if(data.status == 'ok'){
                $("#pilih_poli_daftar").empty();
                var div_data = '<option value="">Pilih Poli</option>';
                $.each(data.result,function(i,data) {
                  div_data +="<option value="+data.kd_poli+">"+data.nm_poli+"</option>";
                });
                $(div_data).appendTo('#pilih_poli_daftar');
              } else if(data.status == 'exist'){
                alert("Anda sudah terdaftar...");
                document.getElementById("tgl_registrasi_daftar").value = "";
              } else {
                alert("Detail poli tidak ditemukan...");
                document.getElementById("tgl_registrasi_daftar").value = "";
              }
            }
          });
        }
      });

      $('select#pilih_poli_daftar').on('change',function(){
        var kd_poli_daftar = this.value;
        var tgl_registrasi_daftar = $('#tgl_registrasi_daftar').val();
        $.ajax({
          type:'POST',
          url:'get-dokter.php',
          dataType: "json",
          data: {kd_poli:kd_poli_daftar, tgl_registrasi:tgl_registrasi_daftar},
          success:function(data){
            if(data.status == 'ok'){
              $("#pilih_dokter_daftar").empty();
              $.each(data.result,function(i,data) {
                var div_data="<option value="+data.kd_dokter+">"+data.nm_dokter+"</option>";
                $(div_data).appendTo('#pilih_dokter_daftar');
              });
            }else{
              $("#pilih_dokter_daftar").empty();
              alert("Dokter tidak ditemukan...");
            }
          }
        });
      });

      $('#finish').click(function (e) {
        var no_rkm_medis_daftar = $('#no_rkm_medis_daftar').val();
        var nm_pasien_daftar = $('#nm_pasien_daftar').val();
        var alamat_daftar = $('#alamat_daftar').val();
        var tgl_registrasi_daftar = $('#tgl_registrasi_daftar').val();
        var kd_poli_daftar = $('#pilih_poli_daftar').val();
        var kd_dokter_daftar = $('#pilih_dokter_daftar').val();

        $('#no_rkm_medis_cetak').text(no_rkm_medis_daftar);
        $('#nm_pasien_cetak').text(nm_pasien_daftar);
        $('#alamat_cetak').text(alamat_daftar);
        $('#tgl_registrasi_cetak').text(tgl_registrasi_daftar);
        $('#kd_poli_cetak').text(kd_poli_daftar);
        $('#kd_dokter_cetak').text(kd_dokter_daftar);

        $.ajax({
            url:'get-poli_nama.php',
            type:'POST',
            dataType: "json",
            data:{
                kd_poli:kd_poli_daftar
            },
           success:function(data){
             if(data.status == 'ok'){
               $('#nm_poli_cetak').text(data.result.nm_poli);
             } else {
               alert('error');
             }
           }
        });

        $.ajax({
            url:'get-dokter_nama.php',
            type:'POST',
            dataType: "json",
            data:{
                kd_dokter:kd_dokter_daftar
            },
           success:function(data){
             if(data.status == 'ok'){
               $('#nm_dokter_cetak').text(data.result.nm_dokter);
             } else {
               alert('error');
             }
           }
        });

        $.ajax({
            url:'post-registrasi.php',
            type:'POST',
            dataType: "json",
            data:{
                no_rkm_medis:no_rkm_medis_daftar,
                tgl_registrasi:tgl_registrasi_daftar,
                kd_poli:kd_poli_daftar,
                kd_dokter:kd_dokter_daftar
            },
           success:function(data){
             if(data.status == 'ok'){
               //alert(data);
               $('#tgl_registrasi_final').text(data.result.tgl_registrasi);
               $('#jam_reg_final').text(data.result.jam_reg);
               $('#no_reg_final').text(data.result.no_reg);
               $('#nm_pasien_final').text(data.result.nm_pasien);
               $('#no_rkm_medis_final').text(data.result.no_rkm_medis);
               $('#alamat_final').text(data.result.alamat);
               $('#nm_dokter_final').text(data.result.nm_dokter);
               $('#nm_poli_final').text(data.result.nm_poli);
               $('#cara_bayar_final').text(data.result.png_jawab);
             } else {
               alert('error');
             }
           }
        });
        $('.progress-bar').css('width', '100%');
        $('.progress-bar').html('Step 3 of 3');
        $('#myTab a[href="#reviewPrint"]').tab('show');
        setTimeout(function() { window.location.href = "/anjungan/"; }, 500000);
      });

    });
    </script>

    <script>
    $(function () {

      $('#infoPendaftaran').click(function (e) {
        var no_rawat = $('#no_rawat').val();
        $.ajax({
          type:'POST',
          url:'get-sep.php',
          dataType: "json",
          data: {no_rawat:no_rawat},
          success:function(data){
            if(data.status == 'ok'){
              //alert(data);
              e.preventDefault();
              var tgl_lahir = new Date(data.result.tanggal_lahir);
              var dd = String(tgl_lahir.getDate()).padStart(2, '0');
              var mm = String(tgl_lahir.getMonth() + 1).padStart(2, '0'); //January is 0!
              var yyyy = tgl_lahir.getFullYear();
              tanggal_lahir = dd + '/' + mm + '/' + yyyy;

              $('.progress-bar').css('width', '70%');
              $('.progress-bar').html('Step 2 of 3');
              $('#myTab a[href="#reviewsep"]').tab('show');
              $('#sep_no_sep').text(data.result.no_sep);
              $('#sep_tglsep').text(data.result.tglsep);
              $('#sep_no_kartu').text(data.result.no_kartu);
              $('#sep_jkel').text(data.result.jkel);
              $('#sep_nama_pasien').text(data.result.nama_pasien);
              $('#sep_no_rawat').text(data.result.no_rawat);
              $('#sep_tanggal_lahir').text(tanggal_lahir);
              $('#sep_notelep').text(data.result.notelep);
              $('#sep_peserta').text(data.result.peserta);
              $('#sep_nmpolitujuan').text(data.result.nmpolitujuan);
              $('#sep_cob').text(data.result.cob);
              $('#sep_nmppkrujukan').text(data.result.nmppkrujukan);
              $('#sep_jnspelayanan').text(data.result.jnspelayanan);
              $('#sep_nmdiagnosaawal').text(data.result.nmdiagnosaawal);
              $('#sep_klsrawat').text(data.result.klsrawat);
              $('#sep_penjamin').text(data.result.penjamin);
              $('#sep_catatan').text(data.result.catatan);
            }else{
              alert("Detail nomor perawatan tidak ditemukan...");
              document.getElementById("no_rawat").value = "";
            }
          }
        });

        setTimeout(function() { window.location.href = "/anjungan/"; }, 500000);

      });

      $('#cetak').click(function (e) {
        var no_rawat = $('#no_rawat').val();
        $.ajax({
          type:'POST',
          url:'get-sep.php',
          dataType: "json",
          data: {no_rawat:no_rawat},
          success:function(data){
            if(data.status == 'ok'){
              //alert(data);
              e.preventDefault();
              var tgl_lahir = new Date(data.result.tanggal_lahir);
              var dd = String(tgl_lahir.getDate()).padStart(2, '0');
              var mm = String(tgl_lahir.getMonth() + 1).padStart(2, '0'); //January is 0!
              var yyyy = tgl_lahir.getFullYear();
              tanggal_lahir = dd + '/' + mm + '/' + yyyy;

              $('.progress-bar').css('width', '100%');
              $('.progress-bar').html('Step 3 of 3');
              $('#myTab a[href="#reviewCetak"]').tab('show');
              $('#_sep_no_sep').text(data.result.no_sep);
              $('#_sep_tglsep').text(data.result.tglsep);
              $('#_sep_no_kartu').text(data.result.no_kartu);
              $('#_sep_jkel').text(data.result.jkel);
              $('#_sep_nama_pasien').text(data.result.nama_pasien);
              $('#_sep_no_rawat').text(data.result.no_rawat);
              $('#_sep_tanggal_lahir').text(tanggal_lahir);
              $('#_sep_notelep').text(data.result.notelep);
              $('#_sep_peserta').text(data.result.peserta);
              $('#_sep_nmpolitujuan').text(data.result.nmpolitujuan);
              $('#_sep_cob').text(data.result.cob);
              $('#_sep_nmppkrujukan').text(data.result.nmppkrujukan);
              $('#_sep_jnspelayanan').text(data.result.jnspelayanan);
              $('#_sep_nmdiagnosaawal').text(data.result.nmdiagnosaawal);
              $('#_sep_klsrawat').text(data.result.klsrawat);
              $('#_sep_penjamin').text(data.result.penjamin);
              $('#_sep_catatan').text(data.result.catatan);

              $('#__sep_no_sep').text(data.result.no_sep);
              $('#__sep_tglsep').text(data.result.tglsep);
              $('#__sep_no_kartu').text(data.result.no_kartu);
              $('#__sep_jkel').text(data.result.jkel);
              $('#__sep_nama_pasien').text(data.result.nama_pasien);
              $('#__sep_no_rawat').text(data.result.no_rawat);
              $('#__sep_tanggal_lahir').text(tanggal_lahir);
              $('#__sep_notelep').text(data.result.notelep);
              $('#__sep_peserta').text(data.result.peserta);
              $('#__sep_nmpolitujuan').text(data.result.nmpolitujuan);
              $('#__sep_cob').text(data.result.cob);
              $('#__sep_nmppkrujukan').text(data.result.nmppkrujukan);
              $('#__sep_jnspelayanan').text(data.result.jnspelayanan);
              $('#__sep_nmdiagnosaawal').text(data.result.nmdiagnosaawal);
              $('#__sep_klsrawat').text(data.result.klsrawat);
              $('#__sep_penjamin').text(data.result.penjamin);
              $('#__sep_catatan').text(data.result.catatan);

            }else{
              alert("Detail nomor perawatan tidak ditemukan...");
              document.getElementById("no_rawat").value = "";
            }
          }
        });
        setTimeout(function() { window.location.href = "/anjungan/"; }, 500000);
      });

    });
    </script>

  </body>
</html>
