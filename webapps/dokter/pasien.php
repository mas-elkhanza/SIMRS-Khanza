<?php 

include_once ('layout/header.php'); 

if(isset($_GET['id'])) {
    $id = $_GET['id']; 
    $_sql = "SELECT a.no_rkm_medis, a.no_rawat, b.nm_pasien, b.umur FROM reg_periksa a, pasien b WHERE a.no_rkm_medis = b.no_rkm_medis AND a.kd_poli = '{$_SESSION['jenis_poli']}'AND b.no_rkm_medis = {$id}";
    if(isset($_REQUEST['tanggal']) && $_REQUEST['tanggal'] !="") {
        $_sql .= " AND a.tgl_registrasi = '{$_REQUEST['tanggal']}'";
    } else { 
        $_sql .= " AND a.tgl_registrasi = '$date'";
    }

    $found_pasien = query($_sql);	
    if(num_rows($found_pasien) == 1) {
	while($row = fetch_array($found_pasien)) { 
	    $no_rkm_medis  = $row['0']; 
	    $no_rawat	   = $row['1'];
	    $nm_pasien     = $row['2'];
	    $umur          = $row['3'];
	}
    } else {
	redirect ('pasien.php');
    }
}

?>

    <section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>PASIEN <?php echo $nmpoli; ?></h2>
            </div>

    <?php if(!$_GET['action']){  ?> 

            <!-- Basic Examples -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                Tanggal : <?php if(isset($_POST['tanggal']) && $_POST['tanggal'] !="") { echo $_POST['tanggal']; } else { echo $date; } ?>
                            </h2>
                        </div>
                        <div class="body">
                            <table id="jadwal_dokter" class="table table-bordered table-striped table-hover display nowrap" width="100%">
                                <thead>
                                    <tr>
                                        <th>#</th>
                                        <th>Nama Pasien</th>
                                        <th>Dokter Tujuan</th>
                                        <th>No. Antrian</th>
                                    </tr>
                                </thead>
	    						<tbody>
	    						<?php
	    						$_sql = "SELECT b.nm_pasien, c.nm_dokter, a.no_reg, a.no_rkm_medis FROM reg_periksa a, pasien b, dokter c WHERE a.kd_poli = '{$_SESSION['jenis_poli']}' AND a.no_rkm_medis = b.no_rkm_medis AND a.kd_dokter = c.kd_dokter"; 
        						if(isset($_POST['tanggal']) && $_POST['tanggal'] !="") {
            						$_sql .= " AND a.tgl_registrasi = '{$_POST['tanggal']}'";
        						} else { 
            						$_sql .= " AND a.tgl_registrasi = '$date'";
        						}
        						$_sql .= "  ORDER BY a.no_reg ASC";

        						$sql = query($_sql); 
        						$no = 1;
								while($row = fetch_array($sql)){ 
		    						echo '<tr>';
		    						echo '<td>'.$no.'</td>';
		    						echo '<td>';
		    						echo '<a href="'.$_SERVER['PHP_SELF'].'?action=view&tanggal='.$_POST['tanggal'].'&id='.$row['3'].'" class="title">'.ucwords(strtolower($row['0'])).'</a>';
		    						echo '</td>';
		    						echo '<td>'.$row['1'].'</td>';
		    						echo '<td>'.$row['2'].'</td>';
		    						echo '</tr>';
        							$no++;
	    						}
	    						?>
	    						</tbody>
                            </table>
                        </div>
                      	<div class="body">
                        	<form method="POST" action="">
                              	<div class="row clearfix">
                                	<div class="col-xs-8 col-lg-10">
                                    	<div class="form-group">
                                        	<div class="form-line">
                                            	<input type="text" class="datepicker form-control" name="tanggal" placeholder="Pilih tanggal...">
                                        	</div>
                                    	</div>
                                	</div>
                                	<div class="col-xs-4 col-lg-2">
                                     	<input type="submit" class="btn btn-primary btn-lg m-l-15 waves-effect" value="Submit">
                                	</div>
                              	</div>
                            </form>
						</div>                                                                                                   
                    </div>
                </div>
            </div>
            <!-- #END# Basic Examples -->

    <?php } ?>

    <?php if($_GET['action'] == "view"){ ?>

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
                        <div class="header">
                            <h2>
                                Catatan Medis
                            </h2>
                        </div>
                        <div class="body">                          
                            <table id="riwayatmedis" class="table">
                                <thead>
                                    <tr>
                                        <th>Tanggal</th>
                                        <th>Poliklinik</th>
                                        <th>Keluhan</th>
                                        <th>Pemeriksaan</th>
                                        <th>Diagnosa</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php 
                                $q_kunj = query ("SELECT a.tgl_registrasi, b.nm_poli, c.keluhan, c.pemeriksaan, a.no_rawat FROM reg_periksa a, poliklinik b, pemeriksaan_ralan c WHERE a.no_rkm_medis = '$no_rkm_medis' AND a.kd_poli = b.kd_poli AND a.no_rawat = c.no_rawat ORDER BY a.tgl_registrasi DESC");

                                if(num_rows($q_kunj) >= 1) {
                                    while ($data_kunj = fetch_array($q_kunj)) { 
                                        $tanggal   = $data_kunj[0];
                                        $nama_poli = $data_kunj[1];
                                        $keluhan = $data_kunj[2];
                                        $pemeriksaan = $data_kunj[3];
                                        $no_rawat = $data_kunj[4];
                                ?>
                                    <tr>
                                        <td><?php echo $tanggal; ?></td>
                                        <td><?php echo $nama_poli; ?></td>
                                        <td><?php echo $keluhan; ?></td>
                                        <td><?php echo $pemeriksaan; ?></td>
                                        <td>
                                            <ul style="list-style:none;">
                                            <?php 
                                            $sql_dx = query("SELECT a.kd_penyakit, a.nm_penyakit FROM penyakit a, diagnosa_pasien b WHERE a.kd_penyakit = b.kd_penyakit AND b.no_rawat = '$no_rawat'");
                	                        $no=1;
                                            while ($row_dx = fetch_array($sql_dx)) { 
                                                echo '<li>'.$no.'. '.$row_dx[1].' ('.$row_dx[0].')</li>';
		                                        $no++;
                                            }              
                                            ?>     
                                            </ul>
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
                                    </tr>
                                <?php
                                } 
                                ?>
                                </tbody>
                            </table>
                        </div>

                    	<form method="post" action="">
                        <div class="header">
                            <h2>
                                Detail e-Diagnosa
                            </h2>
                        </div>
                	    <?php 
                	    if ($_POST['ok_diagnosa']) {
		                    if (($_POST['kode_diagnosa'] <> "") and ($no_rawat <> "")) {
			                    $insert = query("INSERT INTO diagnosa_pasien VALUES ('{$no_rawat}', '{$_POST['kode_diagnosa']}', 'Ralan', '{$_POST['prioritas']}')");
			                    if ($insert) {
			                        redirect("pasien.php?action=view&id={$id}");
			                    }
		                    }
	                    }
	                    ?>

                        <div class="body">
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
                                        <option value="5">Diagnosa Ke-4</option>
                                    </select>
                                </dd><br/>
                                <dt></dt>
                                <dd><button type="submit" name="ok_diagnosa" value="ok_diagnosa" class="btn bg-indigo waves-effect" onclick="this.value=\'ok_diagnosa\'">OK</button></dd><br/>
                                <dt></dt>
                                <dd>
	                        		<ul style="list-style:none;margin-left:0;padding-left:0;">
	                    		    <?php 
	                    		    $query = query("SELECT a.kd_penyakit, b.nm_penyakit, a.prioritas FROM diagnosa_pasien a, penyakit b WHERE a.kd_penyakit = b.kd_penyakit AND a.no_rawat = '{$no_rawat}' ORDER BY a.prioritas ASC");
                            		$no=1;
	                    		    while ($data = fetch_array($query)) {
	                    		    ?>
	                        			<li><?php echo $no; ?>. <?php echo $data['1']; ?> <a href="<?php $_SERVER['PHP_SELF']; ?>?action=delete_diagnosa&kode=<?php echo $data['0']; ?>&faktur=<?php echo $no_rawat; ?>&prioritas=<?php echo $data['2']; ?>&id=<?php echo $id; ?>">[Hapus]</a></li>
	                    		    <?php 
                                		$no++;
	                        		}
	                        		?>
	                        		</ul>
                                </dd>
                            </dl>
                        </div>
                        <div class="header">
                            <h2>
                                Detail e-Resep
                            </h2>
                        </div>
            			<?php 
            			if ($_POST['ok_obat']) {
                			if (($_POST['kode_obat'] <> "") and ($no_rawat <> "")) {
                    			$onhand = query("SELECT no_resep FROM resep_obat WHERE no_rawat = '{$no_rawat}'");
                    			$dtonhand = fetch_array($onhand);

                    			$get_number = fetch_array(query("SELECT max(no_resep) FROM resep_obat"));
                    			$lastNumber = substr($get_number[0], 0, 10);
                    			$next_no_resep = sprintf('%010s', ($lastNumber + 1)); 

                    			if ($dtonhand['0'] > 1) {
                        			$insert = query("INSERT INTO resep_dokter VALUES ('{$dtonhand['0']}', '{$_POST['kode_obat']}', '{$_POST['jumlah']}', '{$_POST['aturan_pakai']}')");
                            		redirect("pasien.php?action=view&id={$id}");
								} else {
                        			$insert = query("INSERT INTO resep_obat VALUES ('{$next_no_resep}', '{$date}', '{$time}', '{$no_rawat}', '{$_SESSION['username']}', '{$date}', '{$time}')");
                        			$insert2 = query("INSERT INTO resep_dokter VALUES ('{$next_no_resep}', '{$_POST['kode_obat']}', '{$_POST['jumlah']}', '{$_POST['aturan_pakai']}')");
                            		redirect("pasien.php?action=view&id={$id}");
                    			}
                			}
            			}
            			?>

                        <div class="body">
                            <dl class="dl-horizontal">
                                <dt>Nama Obat</dt>
                                <dd><select name="kode_obat" class="kd_obat" style="width:100%"></select></dd><br>
                                <dt>Jumlah Obat</dt>
                                <dd><input name="jumlah" value="10" style="width:100%"></dd><br>
                                <dt>Aturan Pakai</dt>
                                <dd>
                                    <select name="aturan_pakai" class="aturan_pakai" style="width:100%">
                                    <?php
                                    $sql = query("SELECT aturan FROM master_aturan_pakai");
                                    while($row = fetch_array($sql)){
                                        echo '<option value="'.$row[0].'">'.$row[0].'</option>';
                                    }
                                    ?>
                                    </select>
                                </dd><br>
                                <dt></dt>
                                <dd><button type="submit" name="ok_obat" value="ok_obat" class="btn bg-indigo waves-effect" onclick="this.value=\'ok_diagnosa\'">OK</button></dd><br>
                                <dt></dt>
                            </dl>
                        </div>
                        <div class="body">
                 <div class="body table-responsive">
                 <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Nama Obat</th>
                        <th>Jumlah</th>
                        <th>Aturan Pakai</th>
                        <th>Tools</th>
                    </tr>
                </thead>
                <tbody>
                <?php 
                $query_resep = query("SELECT a.kode_brng, a.jml, a.aturan_pakai, b.nama_brng, a.no_resep FROM resep_dokter a, databarang b, resep_obat c WHERE a.kode_brng = b.kode_brng AND a.no_resep = c.no_resep AND c.no_rawat = '{$no_rawat}' AND c.kd_dokter = '{$_SESSION['username']}' ");
                while ($data_resep = fetch_array($query_resep)) {
                ?>
                    <tr>
                        <td><?php echo $data_resep['3']; ?></td>
                        <td><?php echo $data_resep['1']; ?></td>
                        <td><?php echo $data_resep['2']; ?></td>
                        <td><a href="<?php $_SERVER['PHP_SELF']; ?>?action=delete_obat&kode_obat=<?php echo $data_resep['0']; ?>&no_resep=<?php echo $data_resep['4']; ?>&id=<?php echo $id; ?>">Hapus</a></td>
                    </tr>
                <?php 
                }
                ?>
                </tbody>
            </table>
            </div>
                        </div>
                        </form>
                    </div>
                </div>
            </div>

    <?php } ?>

    <?php

    //delete
    if($_GET['action'] == "delete_diagnosa"){ 

	$hapus = "DELETE FROM diagnosa_pasien WHERE no_rawat='{$_REQUEST['faktur']}' AND kd_penyakit = '{$_REQUEST['kode']}' AND prioritas = '{$_REQUEST['prioritas']}'";
	$hasil = query($hapus);
	if (($hasil)) {
	    redirect("pasien.php?action=view&id={$id}");
	}

    }

    //delete
    if($_GET['action'] == "delete_obat"){ 

	$hapus = "DELETE FROM resep_dokter WHERE no_resep='{$_REQUEST['no_resep']}' AND kode_brng='{$_REQUEST['kode_obat']}'";
	$hasil = query($hapus);
	if (($hasil)) {
	    redirect("pasien.php?action=view&id={$id}");
	}

    }

    ?>

        </div>
    </section>


<?php include_once ('layout/footer.php'); ?>
