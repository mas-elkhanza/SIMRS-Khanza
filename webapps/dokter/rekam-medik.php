<?php

include_once ('layout/header.php');

?>

    <section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>REKAM MEDIK PASIEN<h2>
            </div>


            <!-- Basic Examples -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">

                    <?php if (isset($_POST['proses'])) { ?>
                        <div class="header">
                            <h2>
                                Periode : <?php echo $_POST['tanggal_awal']; ?> s/d <?php echo $_POST['tanggal_akhir']; ?>
                            </h2>
                        </div>
                    <?php } ?>
                        <div class="body">

                        <?php
                        if (isset($_POST['proses'])) {
                            if (($_POST['tanggal_awal'] == "")||($_POST['tanggal_akhir'] == "")) {
        	                    redirect ('rekam-medik.php');
                            } else {
                        ?>


                        <?php
                        $q_pasien = query ("select * from pasien where no_rkm_medis = '$_POST[no_pasien]'");
                        $no_rkm_medis = $_POST['no_pasien'];
                        $data_pasien = fetch_array($q_pasien);
                        ?>
                            <dl class="dl-horizontal">
                                <dt>Nama Lengkap</dt>
                                <dd><?php echo $data_pasien['nm_pasien']; ?></dd>
                                <dt>No. RM</dt>
                                <dd><?php echo $data_pasien['no_rkm_medis']; ?></dd>
                                <dt>Jenis Kelamin</dt>
                                <dd><?php echo $data_pasien['jk']; ?></dd>
                                <dt>Umur</dt>
                                <dd><?php echo $data_pasien['umur']; ?></dd>
                            </dl>
			    			<div class="body table-responsive">

                            <table id="riwayatmedis" class="table">
                                <thead>
                                    <tr>
                                        <th>Tanggal</th>
                                        <th>Nomor Rawat</th>
                                        <th>Klinik/Ruangan</th>
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
                            $q_kunj = query ("SELECT tgl_registrasi, no_rawat, status_lanjut FROM reg_periksa WHERE no_rkm_medis = '$no_rkm_medis' ORDER BY tgl_registrasi DESC");
                            if(num_rows($q_kunj) >= 1) {
                            while ($data_kunj = fetch_array($q_kunj)) {
                                $tanggal_kunj   = $data_kunj[0];
                                $no_rawat_kunj = $data_kunj[1];
                                $status_lanjut = $data_kunj[2];
                            ?>
                                <tr>
                                    <td><?php echo $tanggal_kunj; ?></td>
                                    <td><?php echo $no_rawat_kunj; ?></td>
                                    <td>
                                      <?php
                                      if($status_lanjut == 'Ralan') {
                                        $sql_poli = fetch_assoc(query("SELECT a.nm_poli FROM poliklinik a, reg_periksa b WHERE b.no_rawat = '$no_rawat_kunj' AND a.kd_poli = b.kd_poli"));
                                        echo $sql_poli['nm_poli'];
                                      } else {
                                        echo 'Rawat Inap';
                                      }
                                      ?>
                                    </td>
                                      <?php
                                      if($status_lanjut == 'Ralan') {
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
                                } else {
                                ?>
                                    <tr>
                                        <td>Blank..!!</td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                        <td></td>
                                    </tr>
                                <?php
                                }
                                ?>
                            </tbody>
                            </table>

						    </div>
                        </div>
                        <div class="body">
                        <?php
                            }
                        }
                        ?>

                        <form method="post" action="">
                            <dl class="dl-horizontal">
                                <dt>Pasien</dt>
                                <dd><select name="no_pasien" class="pasien" style="width:100%"></select></dd><br/>
                                <dt>Periode</dt>
                                <dd><input type="text" class="datepicker form-control" name="tanggal_awal">
                                <dt></dt><dd>s/d</dd>
                                <dt></dt><dd><input type="text" class="datepicker form-control" name="tanggal_akhir"></dd><br/>
                                <dt></dt><dd><input type="submit" class="btn btn-primary waves-effect" name="proses" value="Proses"> <button type="reset" class="btn btn-red waves-effect" name="batal" style="background-color: #f7f7f7 !important; color: #555; border-color: #ccc; text-shadow: none; -webkit-appearance: none;">Batal</button></dd>
                            </dl>
                        </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>


<?php include_once ('layout/footer.php'); ?>
