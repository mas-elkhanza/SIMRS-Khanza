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
                        
                        <form method="post" action="">
                        <div class="header">
                            <h2> Detail e-Sign Vital </h2>
                        </div>

                         <?php 
                        if ($_POST['ok_vital']) {
                            if (($_POST['ok_vital'] <> "") and ($no_rawat <> "")) {
                                $insert = query("INSERT INTO pemeriksaan_ralan 
                                                (no_rawat, tgl_perawatan,jam_rawat,
                                                 suhu_tubuh,tensi,nadi,respirasi,
                                                tinggi,berat,gcs,keluhan,pemeriksaan,alergi,imun_ke,
                                                kepala,mata,hidung,gimul,tenggorokan,telinga,leher,
                                                thoraks,jantung,paru,abdomen,genital_anus,ekstremitas,
                                                kulit) 
                                                 VALUES ('{$no_rawat}',
                                                         '{$_POST['tgl_perawatan']}',
                                                          '{$_POST['jam_rawat']}',
                                                         '{$_POST['suhu_tubuh']}',
                                                         '{$_POST['tensi']}',
                                                         '{$_POST['nadi']}',
                                                          '{$_POST['respirasi']}',
                                                         '{$_POST['tinggi']}',
                                                         '{$_POST['berat']}',
                                                          '{$_POST['gcs']}',
                                                         '{$_POST['keluhan']}',
                                                         '{$_POST['pemeriksaan']}',
                                                          '{$_POST['alergi']}',
                                                         '{$_POST['imun_ke']}',
                                                         '{$_POST['kepala']}',
                                                         '{$_POST['mata']}',
                                                         '{$_POST['hidung']}',
                                                          '{$_POST['gimul']}',
                                                         '{$_POST['tenggorokan']}',
                                                         '{$_POST['telinga']}',
                                                          '{$_POST['leher']}',
                                                         '{$_POST['thoraks']}',
                                                         '{$_POST['jantung']}',
                                                          '{$_POST['paru']}',
                                                         '{$_POST['abdomen']}',
                                                         '{$_POST['genital_anus']}',
                                                          '{$_POST['ekstremitas']}',
                                                         '{$_POST['kulit']}'
                                                     )");
                                if ($insert) {
                                    redirect("pasien.php?action=view&id={$id}");
                                }
                            }
                        }
                        ?>
                        <div class="body" >
                            <dl class="dl-horizontal">
                                <dt>Anamnesa</dt> 
                                        <dd>
                                            <input name="keluhan" placeholder="anamnesa" class="form-control" style="width:50%">
                                        </dd> </br>
                                <dt>Pemeriksaan</dt>
                                    <dd>
                                        <input name="pemeriksaan" placeholder="pemeriksaan" class="
                                        form-control" style="width:70%">

                                    </dd> </br>
                                <dt>Suhu </dt>
                                    <dd>
                                        <input name="suhu_tubuh" placeholder="suhu" class="form-control" style="width:30%"></br>
                                    </dd>
                                <dt>Tensi</dt>
                                    <dd>
                                        <input name="tensi" placeholder="tensi" class="form-control" style="width:30%"></br>
                                    </dd>
                                <dt>Berat</dt>
                                    <dd>
                                        <input name="berat" placeholder="berat" class="form-control" style="width:30%"></br>
                                    </dd>
                                <dt>Tinggi</dt>
                                    <dd>
                                        <input name="tinggi" placeholder="tinggi" class="form-control" style="width:30%"></br>
                                    </dd>
                                 <dt>Nadi</dt>
                                    <dd>
                                        <input name="nadi" placeholder="nadi" class="form-control" style="width:30%"></br>
                                    </dd>

                                <dt>GCS</dt>
                                    <dd>
                                        <input name="gcs" placeholder="E,V,M" class="form-control" style="width:30%"></br>
                                    </dd>

                                <dt>Respirasi</dt>
                                    <dd>
                                        <input name="respirasi" placeholder="respirasi" class="form-control" style="width:50%"></br>
                                    </dd>

                                 <dt>Alergi</dt>
                                    <dd>
                                        <input name="alergi" placeholder="alergi" class="form-control" style="width:50%"></br>
                                    </dd>

                                <dt>Imun ke</dt>
                                    <dd>
                                        <input name="imun_ke" placeholder="imun_ke" class="form-control" style="width:30%"></br>
                                        <input name="tgl_perawatan" type="hidden" 
                                                value="<? echo date ('Y-m-d'); ?>" >
                                         <input name="jam_rawat" type="hidden" 
                                                value="<? echo date ('H:i:s'); ?>" >
                                    </dd>
                                 <dt>Kepala</dt>
                                    <dd>
                                        <select name="kepala" class="form-control" id="kepala">
                                            <option>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>

                                <dt>Mata</dt>
                                    <dd>
                                        <select name="mata" class="form-control" id="mata" >
                                            <option>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>

                                <dt>Hidung</dt>
                                    <dd>
                                        <select name="hidung" class="form-control" id="hidung" >
                                            <option>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>

                                <dt>Gigi dan Mulut</dt>
                                    <dd>
                                        <select name="gimul" class="form-control"  id="gimul" >
                                            <option selected>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>
                                <dt>Tenggorokan</dt>
                                    <dd>
                                        <select name="gimul" class="form-control" id="tenggorokan" >
                                            <option>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>
                                 <dt>Telinga</dt>
                                    <dd>
                                        <select name="telinga" class="form-control" id="telinga" >
                                            <option>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>

                                <dt>Leher</dt>
                                    <dd>
                                        <select name="leher" class="form-control" id="leher" >
                                            <option>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>

                                <dt>Thoraks</dt>
                                    <dd>
                                        <select name="thoraks" class="form-control" id="thoraks" >
                                            <option>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>

                                <dt>Jantung</dt>
                                    <dd>
                                        <select name="jantung" class="form-control" id="jantung" >
                                            <option>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>

                                <dt>Paru</dt>
                                    <dd>
                                        <select name="paru" class="form-control" id="paru" >
                                            <option>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>

                                <dt>Abdomen</dt>
                                    <dd>
                                        <select name="abdomen" class="form-control" id="abdomen" >
                                            <option>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>

                                <dt>Genital Anus</dt>
                                    <dd>
                                    <select name="genital_anus" class="form-control" id="genital_anus" >
                                            <option>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>

                                <dt>Ekstremitas</dt>
                                    <dd>
                                        <select name="ekstremitas" class="form-control" id="ekstremitas" >
                                            <option>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>

                                <dt>Kulit</dt>
                                    <dd>
                                        <select name="kulit" class="form-control" id="kulit" >
                                            <option>Normal</option>
                                            <option>Abnormal</option>
                                        </select>
                                    </dd>

                                <dt></dt>
                                <dd>
                                    <button type="submit" name="ok_vital" value="ok_vital" class="btn bg-indigo waves-effect" onclick="this.value=\'ok_vital\'">OK</button></dd><br/>
                                <dt></dt>    
                            </dl>
                        </div>
                      
            <div class="body table-responsive">
                <table class="table table-striped">
                        <thead>
                            <tr>
                                <th>Anamnesa</th>
                                <th>Pemeriksaan</th>
                                <th>Suhu</th>
                                <th>Tensi</th>
                                <th>Berat</th>
                                <th>Tinggi</th>
                                <th>Nadi</th>
                                <th>GCS</th>
                                <th>Respirasi</th>
                                <th>Alergi</th>
                                <th>Imun ke</th>
                            </tr>
                        </thead>
                    <tbody>
                        <?php 
                            $query_vital = query("SELECT a.keluhan, a.pemeriksaan, a.suhu_tubuh, a.tensi, 
                                                         a.berat, a.tinggi, a.nadi, a.gcs, a.respirasi,
                                                         a.alergi, a.imun_ke
                                                  FROM pemeriksaan_ralan a, reg_periksa b, dokter c
                                                  WHERE a.no_rawat = '{$no_rawat}' and
                                                        a.no_rawat = b.no_rawat    and
                                                        c.kd_dokter = '{$_SESSION['username']}'");
                            while ($data_vital = fetch_array($query_vital)) 
                            {
                                ?>
                                <tr>
                                    <td><?php echo $data_vital['0']; ?></td>
                                    <td><?php echo $data_vital['1']; ?></td>
                                    <td><?php echo $data_vital['2']; ?></td>
                                    <td><?php echo $data_vital['3']; ?></td>
                                    <td><?php echo $data_vital['4']; ?></td>
                                    <td><?php echo $data_vital['5']; ?></td>
                                    <td><?php echo $data_vital['6']; ?></td>
                                    <td><?php echo $data_vital['7']; ?></td>
                                    <td><?php echo $data_vital['8']; ?></td>
                                    <td><?php echo $data_vital['9']; ?></td>
                                    <td><?php echo $data_vital['10']; ?></td>


                                    <td><a href="<?php $_SERVER['PHP_SELF']; ?>?action=delete_obat&kode_obat=<?php echo $data_resep['0']; ?>&no_resep=<?php echo $data_resep['4']; ?>&id=<?php echo $id; ?>">Hapus</a></td>
                                </tr>
                                <?php 
                            }
                                ?>
                    </tbody>
                </table>
            </div>

             <div class="body table-responsive">
                <table class="table table-striped">
                        <thead>
                            <tr>
                              <th>Kepala</th>
                              <th>Mata</th>
                              <th>Hidung</th>
                              <th>Gimul</th>
                              <th>Tenggorokan</th>
                              <th>Telinga</th>
                              <th>Leher</th>
                              <th>Thoraks</th>
                              <th>Jantung</th>
                              <th>Paru</th>
                              <th>Abdomen</th>
                              <th>Genital_anus</th>
                              <th>Ekstremitas</th>
                              <th>Kulit</th> 
                            </tr>
                        </thead>
                    <tbody>
                        <?php 
                            $query_vital = query("SELECT a.kepala, a.mata, a.hidung, a.gimul, 
                                                         a.tenggorokan, a.telinga, a.leher, a.thoraks,
                                                         a.jantung,a.paru, a.abdomen, a.genital_anus,
                                                         a.ekstremitas, a.kulit
                                                  FROM pemeriksaan_ralan a, reg_periksa b, dokter c
                                                  WHERE a.no_rawat = '{$no_rawat}' and
                                                        a.no_rawat = b.no_rawat    and
                                                        c.kd_dokter = '{$_SESSION['username']}'");
                            while ($data_vital = fetch_array($query_vital)) 
                            {
                                ?>
                                <tr>
                                    <td><?php echo $data_vital['0']; ?></td>
                                    <td><?php echo $data_vital['1']; ?></td>
                                    <td><?php echo $data_vital['2']; ?></td>
                                    <td><?php echo $data_vital['3']; ?></td>
                                    <td><?php echo $data_vital['4']; ?></td>
                                    <td><?php echo $data_vital['5']; ?></td>
                                    <td><?php echo $data_vital['6']; ?></td>
                                    <td><?php echo $data_vital['7']; ?></td>
                                    <td><?php echo $data_vital['8']; ?></td>
                                    <td><?php echo $data_vital['9']; ?></td>
                                    <td><?php echo $data_vital['10']; ?></td>
                                    <td><?php echo $data_vital['11']; ?></td>
                                    <td><?php echo $data_vital['12']; ?></td>
                                    <td><?php echo $data_vital['13']; ?></td>
                                    <td><?php echo $data_vital['14']; ?></td>
                                </tr>
                                <?php 
                            }
                                ?>
                    </tbody>
                </table>
            </div>
                   	    
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
                                        <option value="5">Diagnosa Ke-5</option>
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
                                Detail e-Tindakan
                            </h2>
                        </div>

                        <?php 
                        if ($_POST['ok_tindakan']) {
                            if (($_POST['kode_prosedur'] <> "") and ($no_rawat <> "")) {
                                $insert = query("INSERT INTO prosedur_pasien VALUES ('{$no_rawat}', '{$_POST['kode_prosedur']}', 'Ralan', '{$_POST['prioritas']}')");
                                if ($insert) {
                                    redirect("pasien.php?action=view&id={$id}");
                                }
                            }
                        }
                        ?>

                        <div class="body">
                            <dl class="dl-horizontal">
                                <dt>Tindakan</dt>
                                <dd><select name="kode_prosedur" class="kd_prosedur" style="width:100%"></select></dd><br/>
                                <dt>Prioritas</dt>
                                <dd>
                                    <select name="prioritas" class="prioritas" style="width:100%">
                                        <option value="1">Tindakan Ke-1</option>
                                        <option value="2">Tindakan Ke-2</option>
                                        <option value="3">Tindakan Ke-3</option>
                                        <option value="4">Tindakan Ke-4</option>
                                        <option value="5">Tindakan Ke-5</option>
                                    </select>
                                </dd><br/>
                                <dt></dt>
                                <dd><button type="submit" name="ok_tindakan" value="ok_tindakan" class="btn bg-indigo waves-effect" onclick="this.value=\'ok_tindakan\'">OK</button></dd><br/>
                                <dt></dt>
                                <dd>
                                    <ul style="list-style:none;margin-left:0;padding-left:0;">
                                    <?php 
                                    $query = query("SELECT a.kode, b.deskripsi_panjang, a.prioritas FROM prosedur_pasien a, icd9 b WHERE a.kode = b.kode AND a.no_rawat = '{$no_rawat}' ORDER BY a.prioritas ASC");
                                    $no=1;
                                    while ($data = fetch_array($query)) {
                                    ?>
                                        <li><?php echo $no; ?>. <?php echo $data['1']; ?> <a href="<?php $_SERVER['PHP_SELF']; ?>?action=delete_tindakan&kode=<?php echo $data['0']; ?>&faktur=<?php echo $no_rawat; ?>&prioritas=<?php echo $data['2']; ?>&id=<?php echo $id; ?>">[Hapus]</a></li>
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
          <div class="header">
                    <h2>
                         Detail e-Lab
                    </h2>
            </div>

            
                <div class="body table-responsive">
                 <table class="table table-striped">
                <thead>
                    <tr>
                        <th>Tgl Periksa</th>
                        <th>Jam</th>
                        <th>Pemeriksaan</th>
                        <th>Biaya</th>
                    </tr>
                </thead>
                <tbody>
                <?php 
                $query_lab = query("SELECT a.tgl_periksa, a.jam, b.nm_perawatan, a.biaya
                                      FROM periksa_lab a, jns_perawatan_lab b, reg_periksa c 
                                      WHERE a.no_rawat = c.no_rawat AND a.kd_jenis_prw = b.kd_jenis_prw 
                                      AND a.no_rawat = '{$no_rawat}' AND
                                      a.dokter_perujuk = '{$_SESSION['username']}' AND a.status = 'Ralan'");
                while ($data_lab = fetch_array($query_lab)) {
                ?>
                    <tr>
                        <td><?php echo $data_lab['0']; ?></td>
                        <td><?php echo $data_lab['1']; ?></td>
                        <td><?php echo $data_lab['2']; ?></td>
                        <td><?php echo $data_lab['3']; ?></td>
                        <td><a href="<?php $_SERVER['PHP_SELF']; ?>?action=delete_obat&kode_obat=<?php echo $data_resep['0']; ?>&no_resep=<?php echo $data_resep['4']; ?>&id=<?php echo $id; ?>">Hapus</a></td>
                    </tr>
                <?php 
                }
                ?>
                </tbody>
            </table>
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
    if($_GET['action'] == "delete_tindakan"){ 

    $hapus = "DELETE FROM prosedur_pasien 
              WHERE no_rawat='{$_REQUEST['faktur']}' AND 
                    kode = '{$_REQUEST['kode']}' AND 
                    prioritas = '{$_REQUEST['prioritas']}'";
   
   var_dump($hapus); 
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
