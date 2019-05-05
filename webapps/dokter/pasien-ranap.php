<?php

include_once ('layout/header.php');

if(isset($_GET['no_rawat'])) {
    $_sql = "SELECT a.no_rkm_medis, a.no_rawat, b.nm_pasien, b.umur, a.kd_dokter, a.status_lanjut FROM reg_periksa a, pasien b WHERE a.no_rkm_medis = b.no_rkm_medis AND a.no_rawat = '$_GET[no_rawat]'";
    $found_pasien = query($_sql);
    if(num_rows($found_pasien) == 1) {
	     while($row = fetch_array($found_pasien)) {
	        $no_rkm_medis  = $row['0'];
	        $get_no_rawat	     = $row['1'];
            $no_rawat	     = $row['1'];
	        $nm_pasien     = $row['2'];
	        $umur          = $row['3'];
          $dokter          = $row['4'];
          $status_lanjut   = $row['5'];
	     }
    } else {
	redirect ("{$_SERVER['PHP_SELF']}");
    }
}

?>

    <section class="content">
        <div class="container-fluid">
    <?php $action = isset($_GET['action'])?$_GET['action']:null; ?>

    <?php if(!$action){  ?>

            <!-- Basic Examples -->
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                PASIEN RAWAT INAP
                            </h2>
                        </div>
                        <div class="table-responsive">
                          <div class="body">
                            <table id="datatable_ranap" class="table responsive table-bordered table-striped table-hover display nowrap js-exportable" width="100%">
                                <thead>
                                    <tr>
                                        <th>Nama</th>
                                        <th>Nomer MR</th>
                                        <th>Kamar</th>
                                        <th>Bed</th>
                                        <th>Tanggal Masuk</th>
                                        <th>Cara Bayar</th>
                                     </tr>
                                </thead>
                                <tbody>
                                <!-- This query based on Adly's (Adly Hidayat S.KOM) query. Thanks bro -->
                                <?php
                                $sql = "
                                	SELECT
                                		pasien.nm_pasien,
                                    	reg_periksa.no_rkm_medis,
                                    	bangsal.nm_bangsal,
                                    	kamar_inap.kd_kamar,
                                    	kamar_inap.tgl_masuk,
                                    	penjab.png_jawab,
                                    	reg_periksa.no_rawat
                                    FROM
                                    	kamar_inap,
                                        reg_periksa,
                                        pasien,
                                        bangsal,
                                        kamar,
                                        penjab,
                                        dpjp_ranap
                                    WHERE
                                    	kamar_inap.no_rawat = reg_periksa.no_rawat
                                    AND
                                    	reg_periksa.no_rkm_medis = pasien.no_rkm_medis
                                    AND
                                    	kamar_inap.kd_kamar = kamar.kd_kamar
                                    AND
                                    	kamar.kd_bangsal = bangsal.kd_bangsal
                                    AND
                                    	kamar_inap.stts_pulang = '-'
                                    AND
                                    	reg_periksa.kd_pj = penjab.kd_pj
                                    AND
                                    	dpjp_ranap.no_rawat = reg_periksa.no_rawat
                                    AND
                                    	dpjp_ranap.kd_dokter = '{$_SESSION['username']}'
                                ";
                                $sql .= " ORDER BY kamar_inap.tgl_masuk ASC";
                                $result = query($sql);
        						$no = 1;
                                while($row = fetch_array($result)) {
                                  $get_no_rawat = $row['6'];
                                ?>
                                    <tr>
                                        <td><a href="<?php echo $_SERVER['PHP_SELF']; ?>?action=view&no_rawat=<?php echo $row['6'];?>"><?php echo SUBSTR($row['0'], 0, 25).' ...'; ?></a></td>
                                        <td><?php echo $row['1']; ?></td>
                                        <td><?php echo $row['2']; ?></td>
                                        <td><?php echo $row['3']; ?></td>
                                        <td><?php echo $row['4']; ?></td>
                                        <td><?php echo $row['5']; ?></td>
                                    </tr>
                                <?php
                                  $no++;
                                }
                                ?>
                                </tbody>
                            </table>
                          </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# Basic Examples -->

    <?php } ?>

    <?php if($action == "view"){ ?>

            <!-- Basic Examples -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                Detail Pasien
                            </h2>
                        </div>
                        <div class="body">
                            <dl class="dl-horizontal">
                                <dt>Nama Lengkap</dt>
                                <dd><?php echo $nm_pasien; ?></dd>
                                <dt>No. RM</dt>
                                <dd><?php echo $no_rkm_medis; ?></dd>
                                <dt>No. Rawat</dt>
                                <dd><?php echo $no_rawat; ?></dd>
                                <dt>Umur</dt>
                                <dd><?php echo $umur; ?></dd>
                            </dl>
                        </div>

                        <div class="body">
                        <!-- Nav tabs -->
                          	<div class="row">
                            <ul class="nav nav-tabs tab-nav-right" role="tablist">
                                <li role="presentation" class="active"><a href="#riwayat" data-toggle="tab">RIWAYAT</a></li>
                                <li role="presentation"><a href="#diagnosa" data-toggle="tab">DIAGNOSA</a></li>
                                <li role="presentation"><a href="#resep" data-toggle="tab">RESEP</a></li>
                                <li role="presentation"><a href="#permintaanlab" data-toggle="tab">PERMINTAAN LAB</a></li>
                                <li role="presentation"><a href="#permintaanrad" data-toggle="tab">PERMINTAAN RAD</a></li>
                            </ul>
                          	</div>

                            <form method="post" action="">
                      	    <?php
                      	    if (isset($_POST['ok_diagnosa'])) {
      		                    if (($_POST['kode_diagnosa'] <> "") and ($no_rawat <> "")) {

                                $cek_dx = fetch_assoc(query("SELECT a.kd_penyakit FROM diagnosa_pasien a, reg_periksa b WHERE a.kd_penyakit = '".$_POST['kode_diagnosa']."' AND b.no_rkm_medis = '$no_rkm_medis' AND a.no_rawat = b.no_rawat"));
                                if(empty($cek_dx)) {
                                  $status_penyakit = 'Baru';
                                } else {
                                  $status_penyakit = 'Lama';
                                }

                                $cek_prioritas_penyakit = fetch_assoc(query("SELECT prioritas FROM diagnosa_pasien WHERE kd_penyakit = '".$_POST['kode_diagnosa']."' AND no_rawat = '$no_rawat'"));
                                $cek_prioritas_primer = fetch_assoc(query("SELECT prioritas FROM diagnosa_pasien WHERE prioritas = '1' AND no_rawat = '$no_rawat'"));
                                $cek_prioritas = fetch_assoc(query("SELECT prioritas FROM diagnosa_pasien WHERE prioritas = '".$_POST['prioritas']."' AND no_rawat = '$no_rawat'"));

                                if (!empty($cek_prioritas_penyakit)) {
                                    $errors[] = 'Sudah ada diagnosa yang sama.';
                                }

                                //if (!empty($cek_prioritas_primer)) {
                                //    $errors[] = 'Sudah ada prioritas primer.';
                                //} else if (!empty($cek_prioritas)) {
                                //    $errors[] = 'Sudah ada prioritas yang sama sebelumnya.';
                                //}

                                if(!empty($errors)) {

                                    foreach($errors as $error) {
                                        echo validation_errors($error);
                                    }

                                } else {

      			                         $insert = query("INSERT INTO diagnosa_pasien VALUES ('{$no_rawat}', '{$_POST['kode_diagnosa']}', 'Ranap', '{$_POST['prioritas']}', '{$status_penyakit}')");
      			                         if ($insert) {
      			                              redirect("{$_SERVER['PHP_SELF']}?action=view&no_rawat={$no_rawat}");
      			                         }
                                }
      		                    }
      	                    }
      	                    ?>

                          		<?php
                          		if (isset($_POST['ok_obat'])) {
                                  if (($_POST['kode_obat'] <> "") and ($no_rawat <> "")) {
                            	    		$onhand = query("SELECT no_resep FROM resep_obat WHERE no_rawat = '{$no_rawat}' AND tgl_peresepan = '{$date}'");
                                			$dtonhand = fetch_array($onhand);
                                			$get_number = fetch_array(query("select ifnull(MAX(CONVERT(RIGHT(no_resep,10),signed)),0) from resep_obat where tgl_perawatan like '%{$date}%'"));
                                			$lastNumber = substr($get_number[0], 0, 10);
                                			$next_no_resep = sprintf('%010s', ($lastNumber + 1));

                                      if ($dtonhand['0'] > 1) {
                                        if ($_POST['aturan_pakai_lainnya'] == "") {
                                			    $insert = query("INSERT INTO resep_dokter VALUES ('{$dtonhand['0']}', '{$_POST['kode_obat']}', '{$_POST['jumlah']}', '{$_POST['aturan_pakai']}')");
                                        } else {
                                			    $insert = query("INSERT INTO resep_dokter VALUES ('{$dtonhand['0']}', '{$_POST['kode_obat']}', '{$_POST['jumlah']}', '{$_POST['aturan_pakai_lainnya']}')");
                                        }
                                    		redirect("{$_SERVER['PHP_SELF']}?action=view&no_rawat={$no_rawat}");
              								        } else {
                                      		$insert = query("INSERT INTO resep_obat VALUES ('{$next_no_resep}', '{$date}', '{$time}', '{$no_rawat}', '{$_SESSION['username']}', '{$date}', '{$time}', '{$status_lanjut}')");
                                          if ($_POST['aturan_pakai_lainnya'] == "") {
                                  			    $insert2 = query("INSERT INTO resep_dokter VALUES ('{$next_no_resep}', '{$_POST['kode_obat']}', '{$_POST['jumlah']}', '{$_POST['aturan_pakai']}')");
                                          } else {
                                  			    $insert2 = query("INSERT INTO resep_dokter VALUES ('{$next_no_resep}', '{$_POST['kode_obat']}', '{$_POST['jumlah']}', '{$_POST['aturan_pakai_lainnya']}')");
                                          }
                                      		redirect("{$_SERVER['PHP_SELF']}?action=view&no_rawat={$no_rawat}");
                                  		}
                              	  }
                          		}
                          		?>

                              <?php
                          		if (isset($_POST['ok_lab'])) {
                                  if (($_POST['kd_jenis_prw_lab'] <> "") and ($no_rawat <> "")) {

                                      $get_number = fetch_array(query("SELECT ifnull(MAX(CONVERT(RIGHT(noorder,4),signed)),0) FROM permintaan_lab WHERE tgl_permintaan = '{$date}'"));
                                			$lastNumber = substr($get_number[0], 0, 4);
                                      $get_next_number = sprintf('%04s', ($lastNumber + 1));
                                      $get_date = str_replace('-', '',$date);
                                			$next_no_order = 'PL'.$get_date.''.$get_next_number;
                                      echo $next_no_order;
                                      $insert = query("INSERT INTO permintaan_lab VALUES ('{$next_no_order}', '{$no_rawat}', '{$date}', '{$time}', '0000-00-00', '00:00:00', '0000-00-00', '00:00:00', '{$_SESSION['username']}', 'ranap')");
                                      if($insert) {
                                        $get_kd_jenis_prw = $_POST['kd_jenis_prw_lab'];
                                        for ($i = 0; $i < count($get_kd_jenis_prw); $i++) {
                                            $kd_jenis_prw = $get_kd_jenis_prw[$i];
                                            $insert2 = query("INSERT INTO permintaan_pemeriksaan_lab VALUES ('{$next_no_order}', '{$kd_jenis_prw}', 'Belum')");
                                            redirect("{$_SERVER['PHP_SELF']}?action=view&no_rawat={$no_rawat}");
                                        }
                                      }

                              	  }
                          		}
                          		?>

                              <?php
                          		if (isset($_POST['ok_rad'])) {
                                  if (($_POST['kd_jenis_prw_rad'] <> "") and ($no_rawat <> "")) {

                                      $get_number = fetch_array(query("SELECT ifnull(MAX(CONVERT(RIGHT(noorder,4),signed)),0) FROM permintaan_radiologi WHERE tgl_permintaan = '{$date}'"));
                                			$lastNumber = substr($get_number[0], 0, 4);
                                      $get_next_number = sprintf('%04s', ($lastNumber + 1));
                                      $get_date = str_replace('-', '',$date);
                                			$next_no_order = 'PR'.$get_date.''.$get_next_number;
                                      echo $next_no_order;
                                      $insert = query("INSERT INTO permintaan_radiologi VALUES ('{$next_no_order}', '{$no_rawat}', '{$date}', '{$time}', '0000-00-00', '00:00:00', '0000-00-00', '00:00:00', '{$_SESSION['username']}', 'ranap')");
                                      if($insert) {
                                        $get_kd_jenis_prw = $_POST['kd_jenis_prw_rad'];
                                        for ($i = 0; $i < count($get_kd_jenis_prw); $i++) {
                                            $kd_jenis_prw = $get_kd_jenis_prw[$i];
                                            $insert2 = query("INSERT INTO permintaan_pemeriksaan_radiologi VALUES ('{$next_no_order}', '{$kd_jenis_prw}', 'Belum')");
                                            redirect("{$_SERVER['PHP_SELF']}?action=view&no_rawat={$no_rawat}");
                                        }
                                      }

                              	  }
                          		}
                          		?>


                        <!-- Tab panes -->
                            <div class="tab-content m-t-20">
                                <div role="tabpanel" class="tab-pane fade in active" id="riwayat">
                                  <table id="riwayatmedis" class="table row">
                                      <thead>
                                          <tr>
                                              <th>Tanggal</th>
                                              <th>Nomor Rawat</th>
                                              <th>Klinik/Ruangan/Dokter</th>
                                              <th>Keluhan</th>
                                              <th>Pemeriksaan</th>
                                              <th>Diagnosa</th>
                                              <th>Obat</th>
                                              <th>Laboratorium</th>
                                              <th>Radiologi</th>
                                          </tr>
                                      </thead>
                                  <tbody>
                                  <?php
                                  $q_kunj = query ("SELECT tgl_registrasi, no_rawat, status_lanjut FROM reg_periksa WHERE no_rkm_medis = '$no_rkm_medis' AND stts !='Batal' ORDER BY tgl_registrasi DESC");
                                  while ($data_kunj = fetch_array($q_kunj)) {
                                      $tanggal_kunj   = $data_kunj[0];
                                      $no_rawat_kunj = $data_kunj[1];
                                      $status_lanjut_kunj = $data_kunj[2];
                                  ?>
                                      <tr>
                                          <td><?php echo $tanggal_kunj; ?></td>
                                          <td><?php echo $no_rawat_kunj; ?></td>
                                          <td>
                                            <?php
                                            if($status_lanjut_kunj == 'Ralan') {
                                              $sql_poli = fetch_assoc(query("SELECT a.nm_poli, c.nm_dokter FROM poliklinik a, reg_periksa b, dokter c WHERE b.no_rawat = '$no_rawat_kunj' AND a.kd_poli = b.kd_poli AND b.kd_dokter = c.kd_dokter"));
                                              echo $sql_poli['nm_poli'];
                                              echo '<br>';
                                              echo "(".$sql_poli['nm_dokter'].")";
                                            } else {
                                              echo 'Rawat Inap';
                                            }
                                            ?>
                                          </td>
                                            <?php
                                            if($status_lanjut_kunj == 'Ralan') {
                                              $sql_riksaralan = fetch_assoc(query("SELECT keluhan, pemeriksaan FROM pemeriksaan_ralan WHERE no_rawat = '$no_rawat_kunj'"));
                                              echo "<td>".$sql_riksaralan['keluhan']."</td>";
                                              echo "<td>".$sql_riksaralan['pemeriksaan']."</td>";
                                            } else {
                                              $sql_riksaranap = fetch_assoc(query("SELECT keluhan, pemeriksaan FROM pemeriksaan_ranap WHERE no_rawat = '$no_rawat_kunj'"));
                                              echo "<td>".$sql_riksaranap['keluhan']."</td>";
                                              echo "<td>".$sql_riksaranap['pemeriksaan']."</td>";
                                            }
                                            ?>
                                          <td>
                                              <ul style="list-style:none;">
                                              <?php
                                              $sql_dx = query("SELECT a.kd_penyakit, a.nm_penyakit FROM penyakit a, diagnosa_pasien b WHERE a.kd_penyakit = b.kd_penyakit AND b.no_rawat = '$no_rawat_kunj'");
                              	              $no=1;
                                              while ($row_dx = fetch_array($sql_dx)) {
                                                  echo '<li>'.$no.'. '.$row_dx[1].' ('.$row_dx[0].')</li>';
              		                                $no++;
                                              }
                                              ?>
                                              </ul>
                                          </td>
                                          <td>
                                              <ul style="list-style:none;">
                                              <?php
                                              $sql_obat = query("select detail_pemberian_obat.jml, databarang.nama_brng from detail_pemberian_obat inner join databarang on detail_pemberian_obat.kode_brng=databarang.kode_brng where detail_pemberian_obat.no_rawat= '$no_rawat_kunj'");
                              	              $no=1;
                                              while ($row_obat = fetch_array($sql_obat)) {
                                                  echo '<li>'.$no.'. '.$row_obat[1].' ('.$row_obat[0].')</li>';
              		                                $no++;
                                              }
                                              ?>
                                              </ul>
                                          </td>
                                          <td>
                                              <ul style="list-style:none;">
                                              <?php
                                              $sql_lab = query("select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai, template_laboratorium.satuan, detail_periksa_lab.nilai_rujukan, detail_periksa_lab.keterangan from detail_periksa_lab inner join  template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template  where detail_periksa_lab.no_rawat= '$no_rawat_kunj'");
                              	              $no=1;
                                              while ($row_lab = fetch_array($sql_lab)) {
                                                  echo '<li>'.$no.'. '.$row_lab[0].' ('.$row_lab[3].') = '.$row_lab[1].' '.$row_lab[2].'</li>';
              		                                $no++;
                                              }
                                              ?>
                                              </ul>
                                          </td>
                                          <td>
                                              <div id="aniimated-thumbnials" class="list-unstyled row clearfix">
                                              <?php
                                              $sql_rad = query("select * from gambar_radiologi where no_rawat= '$no_rawat_kunj'");
                                              $no=1;
                                              while ($row_rad = fetch_array($sql_rad)) {
                                                  echo '<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12">';
                                                  echo '<a href="'.$_SERVER['PHP_SELF'].'?action=radiologi&no_rawat='.$no_rawat_kunj.'" class="title"><img class="img-responsive thumbnail"  src="'.SIMRSURL.'/radiologi/'.$row_rad[3].'"></a>';
                                                  echo '</div>';
                                                  $no++;
                                              }
                                              ?>

                                            </div>
                                          </td>
                                      </tr>
                                  <?php
                                  }
                                  ?>
                                  </tbody>
                                  </table>

                                </div>
                                <div role="tabpanel" class="tab-pane fade" id="diagnosa">
                                  <dl class="dl-horizontal">
                                      <dt>Diagnosa</dt>
                                      <dd><select name="kode_diagnosa" class="kd_diagnosa" style="width:100%"></select></dd><br/>
                                      <dt>Prioritas</dt>
                                      <dd>
                                          <select name="prioritas" class="prioritas" style="width:100%">
                                              <option value="1">Diagnosa Ke-1</option>
                                              <option value="2">Diagnosa Ke-2</option>
                                              <option value="3">Diagnosa Ke-3</option>
                                              <option value="4">Diagnosa Ke-4</option>
                                              <option value="5">Diagnosa Ke-5</option>
                                              <option value="6">Diagnosa Ke-6</option>
                                              <option value="7">Diagnosa Ke-7</option>
                                              <option value="8">Diagnosa Ke-8</option>
                                              <option value="9">Diagnosa Ke-9</option>
                                              <option value="10">Diagnosa Ke-10</option>
                                          </select>
                                      </dd><br/>
                                      <dt></dt>
                                      <dd><button type="submit" name="ok_diagnosa" value="ok_diagnosa" class="btn bg-indigo waves-effect" onclick="this.value=\'ok_diagnosa\'">OK</button></dd><br/>
                                      <dt></dt>
                                      <dd>
      	                        		<ul style="list-style:none;margin-left:0;padding-left:0;">
      	                    		    <?php
      	                    		    $query = query("SELECT a.kd_penyakit, b.nm_penyakit, a.prioritas, c.kd_dokter FROM diagnosa_pasien a, penyakit b, reg_periksa c WHERE a.kd_penyakit = b.kd_penyakit AND a.no_rawat = '{$no_rawat}' AND a.no_rawat = c.no_rawat ORDER BY a.prioritas ASC");
                                  		$no=1;
      	                    		    while ($data = fetch_array($query)) {
      	                    		    ?>
              	                              <li><?php echo $no; ?>. <?php echo $data['1']; ?> <?php if($data['3'] == $_SESSION['username']) { ?><a class="btn btn-danger btn-xs" href="<?php $_SERVER['PHP_SELF']; ?>?action=delete_diagnosa&kode=<?php echo $data['0']; ?>&prioritas=<?php echo $data['2']; ?>&no_rawat=<?php echo $no_rawat; ?>">[X]</a><?php } ?></li>
      	                    		    <?php
                                      		$no++;
      	                        		}
      	                        		?>
      	                        		</ul>
                                      </dd>
                                  </dl>

                                </div>
                                <div role="tabpanel" class="tab-pane fade" id="resep">
                                  <dl class="dl-horizontal">
                                      <dt>Nama Obat</dt>
                                      <dd><select name="kode_obat" class="kd_obat" style="width:100%"></select></dd><br>
                                      <dt>Jumlah Obat</dt>
                                      <dd><input name="jumlah" value="10" style="width:100%"></dd><br>
                                      <dt>Aturan Pakai</dt>
                                      <dd>
                                          <select name="aturan_pakai" class="aturan_pakai" id="lainnya" style="width:100%">
                                          <?php
                                          $sql = query("SELECT aturan FROM master_aturan_pakai");
                                          while($row = fetch_array($sql)){
                                              echo '<option value="'.$row[0].'">'.$row[0].'</option>';
                                          }
                                          ?>
                                          <option value="lainnya">Lainnya</option>
                                          </select>
                                      </dd><br>
                                      <div id="row_dim">
                                      <dt></dt>
                                      <dd><input name="aturan_pakai_lainnya" style="width:100%"></dd><br>
                                      </div>
                                      <dt></dt>
                                      <dd><button type="submit" name="ok_obat" value="ok_obat" class="btn bg-indigo waves-effect" onclick="this.value=\'ok_obat\'">OK</button></dd><br>
                                      <dt></dt>
                                  </dl>
                       <div class="table-responsive">
                       <table class="table table-striped">
                      <thead>
                          <tr>
                              <th>Nama Obat</th>
                              <th>Jumlah</th>
                              <th>Aturan Pakai</th>
                              <th>Dokter</th>
                              <th>Tanggal</th>
                          </tr>
                      </thead>
                      <tbody>
                      <?php
                      $query_resep = query("SELECT a.kode_brng, a.jml, a.aturan_pakai, b.nama_brng, a.no_resep, c.tgl_peresepan, d.nm_dokter, c.kd_dokter FROM resep_dokter a, databarang b, resep_obat c, dokter d WHERE a.kode_brng = b.kode_brng AND a.no_resep = c.no_resep AND c.no_rawat = '{$no_rawat}' AND c.kd_dokter = d.kd_dokter ORDER BY c.tgl_peresepan DESC");
                      while ($data_resep = fetch_array($query_resep)) {
                      ?>
                          <tr>
                              <td><?php echo $data_resep['3']; ?> <?php if($data_resep['5'] == $date) { if($data_resep['7'] == $_SESSION['username']) { ?><a class="btn btn-danger btn-xs" href="<?php $_SERVER['PHP_SELF']; ?>?action=delete_obat&kode_obat=<?php echo $data_resep['0']; ?>&no_resep=<?php echo $data_resep['4']; ?>&no_rawat=<?php echo $no_rawat; ?>">[X]</a><?php } } ?></td>
                              <td><?php echo $data_resep['1']; ?></td>
                              <td><?php echo $data_resep['2']; ?></td>
                              <td><?php echo $data_resep['6']; ?></td>
                              <td><?php echo $data_resep['5']; ?></td>
                          </tr>
                      <?php
                      }
                      ?>
                      </tbody>
                  </table>
                  </div>
                                </div>
                                <div role="tabpanel" class="tab-pane fade" id="permintaanlab">
                                  <dl class="dl-horizontal">
                                      <dt>Jenis Pemeriksaan</dt>
                                      <dd><select name="kd_jenis_prw_lab[]" class="kd_jenis_prw_lab" multiple="multiple" style="width:100%"></select></dd><br/>
                                      <dt></dt>
                                      <dd><button type="submit" name="ok_lab" value="ok_lab" class="btn bg-indigo waves-effect" onclick="this.value=\'ok_lab\'">OK</button></dd><br/>
                                      <dt></dt>
                                      <dd>
      	                        		<ul style="list-style:none;margin-left:0;padding-left:0;">
      	                    		    <?php
      	                    		    $query = query("SELECT c.kd_jenis_prw, d.nm_perawatan, c.noorder, b.dokter_perujuk FROM  reg_periksa a, permintaan_lab b, permintaan_pemeriksaan_lab c, jns_perawatan_lab d  WHERE a.no_rawat = '{$no_rawat}' AND a.no_rawat = b.no_rawat AND b.noorder = c.noorder AND c.kd_jenis_prw = d.kd_jenis_prw");
                                  		$no=1;
      	                    		    while ($data = fetch_array($query)) {
      	                    		    ?>
              	                              <li><?php echo $no; ?>. <?php echo $data['1']; ?> <?php if($data['3'] == $_SESSION['username']) { ?><a class="btn btn-danger btn-xs" href="<?php $_SERVER['PHP_SELF']; ?>?action=delete_lab&kd_jenis_prw=<?php echo $data['0']; ?>&noorder=<?php echo $data['2']; ?>&no_rawat=<?php echo $no_rawat; ?>">[X]</a><?php } ?></li>
      	                    		    <?php
                                      		$no++;
      	                        		}
      	                        		?>
      	                        		</ul>
                                      </dd>
                                  </dl>
                                </div>
                                <div role="tabpanel" class="tab-pane fade" id="permintaanrad">
                                  <dl class="dl-horizontal">
                                      <dt>Jenis Pemeriksaan</dt>
                                      <dd><select name="kd_jenis_prw_rad[]" class="kd_jenis_prw_rad" multiple="multiple" style="width:100%"></select></dd><br/>
                                      <dt></dt>
                                      <dd><button type="submit" name="ok_rad" value="ok_rad" class="btn bg-indigo waves-effect" onclick="this.value=\'ok_rad\'">OK</button></dd><br/>
                                      <dt></dt>
                                      <dd>
      	                        		<ul style="list-style:none;margin-left:0;padding-left:0;">
      	                    		    <?php
                                    $query = query("SELECT c.kd_jenis_prw, d.nm_perawatan, c.noorder, b.dokter_perujuk FROM  reg_periksa a, permintaan_radiologi b, permintaan_pemeriksaan_radiologi c, jns_perawatan_radiologi d  WHERE a.no_rawat = '{$no_rawat}' AND a.no_rawat = b.no_rawat AND b.noorder = c.noorder AND c.kd_jenis_prw = d.kd_jenis_prw");
                                  		$no=1;
      	                    		    while ($data = fetch_array($query)) {
      	                    		    ?>
              	                              <li><?php echo $no; ?>. <?php echo $data['1']; ?> <?php if($data['3'] == $_SESSION['username']) { ?><a class="btn btn-danger btn-xs" href="<?php $_SERVER['PHP_SELF']; ?>?action=delete_rad&kd_jenis_prw=<?php echo $data['0']; ?>&noorder=<?php echo $data['2']; ?>&no_rawat=<?php echo $no_rawat; ?>">[X]</a><?php } ?></li>
      	                    		    <?php
                                      		$no++;
      	                        		}
      	                        		?>
      	                        		</ul>
                                      </dd>
                                  </dl>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
    <?php } ?>

    <?php if($action == "radiologi"){ ?>

                    <div class="card">
                        <div class="header">
                            <h2>
                                BERKAS DIGITAL RADIOLOGI
                            </h2>
                        </div>
                        <div class="body">
                            <dl class="dl-horizontal">
                                <dt>Nama Lengkap</dt>
                                <dd><?php echo $nm_pasien; ?></dd>
                                <dt>No. RM</dt>
                                <dd><?php echo $no_rkm_medis; ?></dd>
                                <dt>No. Rawat</dt>
                                <dd><?php echo $no_rawat; ?></dd>
                                <dt>Umur</dt>
                                <dd><?php echo $umur; ?></dd>
                            </dl>
						    <hr>
                                        <div id="aniimated-thumbnials" class="list-unstyled row clearfix">
                                        <?php
                                        $sql_rad = query("select * from gambar_radiologi where no_rawat= '{$_GET['no_rawat']}'");
                                        $no=1;
                                        while ($row_rad = fetch_array($sql_rad)) {
                                            echo '<div class="col-lg-3 col-md-6 col-sm-12 col-xs-12">';
                                            echo '<a href="'.SIMRSURL.'/radiologi/'.$row_rad[3].'" data-sub-html=""><img class="img-responsive thumbnail"  src="'.SIMRSURL.'/radiologi/'.$row_rad[3].'"></a>';
                                            echo '</div>';
                                            $no++;
                                        }
                                        ?>

                                      </div>

                        </div>
                    </div>
                    <a class="btn btn-primary" href="<?php echo $_SERVER['PHP_SELF'].'?action=view&no_rawat='.$_GET['no_rawat']; ?>">BACK</a>
          			<br><br>

    <?php } ?>

    <?php

    //delete
    if($action == "delete_diagnosa"){

	$hapus = "DELETE FROM diagnosa_pasien WHERE no_rawat='{$_REQUEST['no_rawat']}' AND kd_penyakit = '{$_REQUEST['kode']}' AND prioritas = '{$_REQUEST['prioritas']}'";
	$hasil = query($hapus);
	if (($hasil)) {
	    redirect("{$_SERVER['PHP_SELF']}?action=view&no_rawat={$no_rawat}");
	}

    }

    //delete
    if($action == "delete_obat"){

	$hapus = "DELETE FROM resep_dokter WHERE no_resep='{$_REQUEST['no_resep']}' AND kode_brng='{$_REQUEST['kode_obat']}'";
	$hasil = query($hapus);
	if (($hasil)) {
	    redirect("{$_SERVER['PHP_SELF']}?action=view&no_rawat={$no_rawat}");
	}

    }

    ?>

        </div>
    </section>


<?php include_once ('layout/footer.php'); ?>
