<?php
/***
* SIMRS Khanza Lite from version 0.1 Beta
* About : Porting of SIMRS Khanza by Windiarto a.k.a Mas Elkhanza as web and mobile app.
* Last modified: 02 Pebruari 2018
* Author : drg. Faisol Basoro
* Email : drg.faisol@basoro.org
* Licence under GPL
***/

require_once('includes/wsinacbg.php');
header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");
header("Last-Modified: ".gmdate("D, d M Y H:i:s")." GMT");
header("Cache-Control: no-store, no-cache, must-revalidate");
header("Cache-Control: post-check=0, pre-check=0", false);
header("Pragma: no-cache"); // HTTP/1.0

$title = 'Data Tarif INACBG';
include_once('config.php');
include_once('layout/header.php');
include_once('layout/sidebar.php');
?>

    <section class="content">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                CEK TARIF INACBG's
                            </h2>
                        </div>
                        <div class="body">

                            <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
                              <dl class="dl-horizontal">
                                <dt>Diagnosa</dt>
                                <dd>
                                  <select name="diagnosa[]" class="kd_diagnosa" multiple="multiple" style="width:100%"></select>
                                  <p class="small">* Wajib diisi</p>
                                </dd>
                                <br>
                                <dt>Prosedur</dt>
                                <dd>
                                  <select name="prosedur[]" class="kd_prosedur" multiple="multiple" style="width:100%"></select>
                                  <p class="small">* Wajib diisi</p>
                                </dd>
                                <br>
                                <dt>Jenis Rawat</dt>
                                <dd>
                                  <select class="form-control show-tick"  name="jenis_rawat" style="width:100%">
                                    <option value="2">Rawat Jalan</option>
                                    <option value="1">Rawat Inap</option>
                                  </select>
                                </dd><br>
                                <dt>Kelas Rawat</dt>
                                <dd>
                                  <select class="form-control show-tick"  name="kelas_rawat" style="width:100%">
                                    <option value="1">Kelas 1</option>
                                    <option value="2">Kelas 2</option>
                                    <option value="3">Kelas 3</option>
                                  </select>
                                </dd><br>
                                <dt>Jenis Kelamin</dt>
                                <dd>
                                  <select class="form-control show-tick" name="jenis_kelamin" style="width:100%">
                                    <option value="1">Laki-Laki</option>
                                    <option value="2">Perempuan</option>
                                  </select>
                                <p class="small">* Obgyn pilih jenis kelamin perempuan</p>
                                </dd><br>
                                <dt>Tanggal Lahir</dt>
                                <dd><input type="text" class="datepicker form-control" name="tgl_lahir" placeholder="Pilih tanggal lahir...">
                                <p class="small">* Pediatri pilih tanggal lahir</p>
                                </dd><br>
                                <?php
                                $cektarif = isset($_POST['cektarif'])?$_POST['cektarif']:NULL;
                                if (isset($cektarif)) {
                                  if(!empty($_POST['tgl_lahir'])) {
                                    $tgl_lahir = $_POST['tgl_lahir'];
                                  } else {
                                    $tgl_lahir = '1990-01-01';
                                  }

                                  $diagnosa="";
                                  $a=1;
				                          $get = $_POST['diagnosa'];
                                  foreach ($get as $key => $value) {
                                    if($a==1){
                                      $diagnosa=$value;
                                    }else{
                                      $diagnosa=$diagnosa."#".$value;
                                    }
                                  $a++;
                                  }

                                  $prosedur="";
                                  $a=1;
				                          $get = $_POST['prosedur'];
                                  foreach ($get as $key => $value) {
                                    $value = substr($value, 0, 2) . '.' . substr($value, 2);
                                    if($a==1){
                                      $prosedur=$value;
                                    }else{
                                      $prosedur=$prosedur."#".$value;
                                    }
                                  $a++;
                                  }

                                  $jenis_rawat  = trim($_POST['jenis_rawat']);
                                  $kelas_rawat  = trim($_POST['kelas_rawat']);
                                  $jenis_kelamin  = trim($_POST['jenis_kelamin']);
                                  UpdateDataPasien(NO_RM,NO_PESERTA,NO_RM,'Pasien Uji Coba',$tgl_lahir.' 00:00:00',$jenis_kelamin);
                                  CekTarif(SEP,NO_PESERTA,'2018-01-01','2018-01-01',$jenis_rawat,$kelas_rawat,'','','0','0','0','0','','0','','0','1',$diagnosa,$prosedur,'0','Dokter Uji Coba',TIPE_RS,'','','#',NIK_KODER,'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0','0');
                                }
                                ?>

                                <dt></dt>
                                <dd><button type="submit" name="cektarif" value="cektarif" class="btn bg-indigo waves-effect" onclick="this.value=\'cektarif\'">Cek Tarif</button></dd>
                              </dl>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

<?php
include_once('layout/footer.php');
?>
