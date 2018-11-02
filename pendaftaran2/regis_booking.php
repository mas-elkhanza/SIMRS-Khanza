
<?php  
include_once ('layout/header.php'); 
$action     = isset($_GET['action']) ? $_GET['action'] : null;

echo '<section class="content">';
	echo '<div class="container-fluid">';
		echo '<div class="block-header">';
		    echo '<h2>PENDAFTARAN PASIEN</h2>';
		echo '</div>';

		switch ($action) {
			// 13-09-2018
			case 'pilih-poli':
				$tanggal 	= @$_GET['tgl_registrasi'];
				
				/*
				| Bagian : Form Pemilihan Poli dan Dokter
				*/
			    $hari 		= hari_indo($tanggal);
			    $lsd 		= select_jadwal_poli($hari);
			    $cls 		= ConDataToArray($lsd,'kd_poli','nm_poli');

			    

		   		//print_r($rcrbyr);

			    echo '<div class="row clearfix">';
					echo '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">';
						echo '<div class="card">';
							echo '<div class="body">';
								echo '<form id="" name="pilihan" action="" method="POST"  enctype="multipart/form-data">';
									echo '<input type="hidden" name="tgl_registrasi" id="tgl_reg" value="'.$_GET['tgl_registrasi'].'">';
									echo '<input type="hidden" name="no_rm" id="no_rm" value="'.$_SESSION['username'].'">';

									echo '<label for="email_address">Poliklinik Tujuan</label>';
									echo '<div class="form-group">';
										echo '<div class="form-line">';
											echo htmlSelectFromArray($cls, 'name="id_poli_tujuan" id="id_poli_tujuan" class="form-control"', true);
										echo '</div>';
									echo '</div>';

									echo '<label for="dokter">Dokter Tujuan</label>';
									echo '<div class="form-group">';
										echo '<div class="form-line" >';
											echo '<div id="pilihan_dokter">';
											echo '<select class="form-control" name="kd_dokter" id="kd_dokter">';
												echo '<option>--- Tidak ada dokter ---</option>';
											echo '</select>';
											echo '</div>';
										echo '</div>';
									echo '</div>';

									/*
									| Select cara bayar
									*/								

									echo '<label for="email_address">Cara Bayar</label>';
									echo '<div class="form-group">';
										echo '<div class="form-line" id="cara_bayars">';
											echo '<select class="form-control" name="cara_bayar">';
												echo '<option>--- Tidak ada Cara Bayar ---</option>';
											echo '</select>';
										echo '</div>';
									echo '</div>';

									echo '<div class="form-group">';
	                                    echo '<input type="checkbox" name="checkbox" id="remember" class="filled-in chk-col-pink">';
	                                    echo '<label for="remember">Saya menyetujui ketentuan dan persyaratan</label>';
                                	echo '</div>';

                                	echo '<div id="form-setuju">';
	                                    echo '<button type="button" id="checkbox" value="proses" class="btn btn-primary" name="button">Simpan</button>';
                                	echo '</div>';

								echo '</form>';	
							echo '</div>';
						echo '</div>';
					echo '</div>';
				echo '</div>';

				break;

			// 17-09-2018
			case 'selesai':
				echo '<div class="row clearfix">';
					echo '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">';
						echo '<div class="card">';
							echo '<div class="body">';
								echo '<table class="table table-striped">';
									echo '<tr>';
										echo '<th style="width:250px;">Waktu Datang</th>';
										echo '<th style="width:10px;">:</th>';
										echo '<td id="waktu_datang">'.@$_GET['waktu_kunjungan'].'</td>';
									echo '</tr>';
									echo '<tr>';
										echo '<th>Nama Lengkap</th>';
										echo '<td>:</td>';
										echo '<td id="nama_lengkap">'.@$_GET['nama_lengkap'].'</td>';
									echo '</tr>';
									echo '<tr>';
										echo '<th>Nomor Rekam Medik</th>';
										echo '<td>:</td>';
										echo '<td id="no_rm">'.@$_GET['no_rm'].'</td>';
									echo '</tr>';
									echo '<tr>';
										echo '<th>Tanggal Periksa</th>';
										echo '<td>:</td>';
										echo '<td id="tgl_periksa">'.@$_GET['tgl_periksa'].'</td>';
									echo '</tr>';
									echo '<tr>';
										echo '<th>Tanggal Booking</th>';
										echo '<td>:</td>';
										echo '<td id="tgl_daftar">'.@$_GET['tanggal_booking'].'</td>';
									echo '</tr>';
									echo '<tr>';
										echo '<th>Jam Daftar</th>';
										echo '<td>:</td>';
										echo '<td id="jam_booking">'.@$_GET['jam_booking'].'</td>';
									echo '</tr>';
									echo '<tr>';
										echo '<th>Nomor Urut</th>';
										echo '<td>:</td>';
										echo '<td id="no_urut">'.@$_GET['no_urut'].'</td>';
									echo '</tr>';
									echo '<tr>';
										echo '<th>Poliklinik Tujuan</th>';
										echo '<td>:</td>';
										echo '<td id="nm_poli">'.@$_GET['nm_poli'].'</td>';
									echo '</tr>';
									echo '<tr>';
										echo '<th>Dokter Tujuan</th>';
										echo '<td>:</td>';
										echo '<td id="nm_dokter">'.@$_GET['nm_dokter'].'</td>';
									echo '</tr>';
									echo '<tr>';
										echo '<th>Cara Bayar</th>';
										echo '<td>:</td>';
										echo '<td id="kd_pj">'.@$_GET['kd_pj'].'</td>';
									echo '</tr>';
									echo '<tr>';
										echo '<th>';
											echo '<a href="includes/cek_pendaftaran.php?aksi=cetak-data&nama_lengkap='.$_GET['nama_lengkap'].'&tanggal_booking='.$_GET['tanggal_booking'].'&jam_booking='.$_GET['jam_booking'].'&no_rm='.$_GET['no_rm'].'&tgl_periksa='.$_GET['tgl_periksa'].'&no_urut='.$_GET['no_urut'].'&nm_poli='.$_GET['nm_poli'].'&nm_dokter='.$_GET['nm_dokter'].'&kd_pj='.$_GET['kd_pj'].'&waktu_kunjungan='.$_GET['waktu_kunjungan'].'" class="btn btn-warning" target="_blank">Cetak Data</a>';
										echo '</th>';
									echo '</tr>';
								echo '</table>';
							echo '</div>';
						echo '</div>';
					echo '</div>';
				echo '</div>';
			break;

			case "batal-pendaftaran":
				$tgl_registrasi = date('Y-m-d',strtotime($_GET['tgl_registrasi']));
				$no_rm = $_SESSION['username'];

				$qcn = query('SELECT 
					a.no_reg, 
					a.tanggal_booking,
					a.jam_booking,
					a.tanggal_periksa,
					a.no_rkm_medis,
					a.status,
					a.kd_poli,
					a.kd_dokter,
					b.nm_poli,
					c.nm_dokter
					FROM booking_registrasi as a
					LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli
            		LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter
					WHERE 	a.tanggal_periksa=\''.$tgl_registrasi.'\' and 
							a.no_rkm_medis=\''.$no_rm.'\'');
				
				if(@$_GET['st'] == 's')
				{
					echo '<div class="alert bg-green alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button> Pembatalan anda berhasil.</div>';
				}
				else if(@$_GET['st'] == 'e')
				{
					
					echo '<div class="alert bg-pink alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button> Maaf pembatalan booking anda gagal, Silahkan coba beberapa saat lagi...</div>';
				}

				echo '<div class="row clearfix">';
	                echo '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">';
	                    echo '<div class="card">';
	                        echo '<div class="body table-responsive">';
	                            echo '<table class="table table-bordered table-striped table-hover display nowrap" width="100%">';
	                                echo '<thead>';
	                                    echo '<tr>';
	                                        echo '<th>No. Antrian</th>';
	                                        echo '<th>Tanggal Booking</th>';
	                                        echo '<th>Tanggal Periksa</th>';
	                                        echo '<th>No Rm</th>';
	                                        echo '<th>Poliklinik</th>';
	                                        echo '<th>Dokter</th>';
	                                        echo '<th>Status</th>';
	                                        echo '<th>Aksi</th>';
	                                    echo '</tr>';
	                                echo '</thead>';
	                                echo '<tbody>';
	                                while ($data = fetch_array($qcn)) {
										echo '<tr data-tgl_periksa='.$data['tanggal_periksa'].' data-kd_poli='.$data['kd_poli'].' data-kd_dokter='.$data['kd_dokter'].'>';
											echo '<td class="tr_mod">'.$data['no_reg'].'</td>';
											echo '<td class="tr_mod">'.$data['tanggal_booking']. ' '. $data['jam_booking'] .'</td>';
											echo '<td class="tr_mod">'.$data['tanggal_periksa'].'</td>';
											echo '<td class="tr_mod">'.$data['no_rkm_medis'].'</td>';
											echo '<td class="tr_mod">'.$data['nm_poli'].'</td>';
											echo '<td class="tr_mod">'.$data['nm_dokter'].'</td>';
											echo '<td class="tr_mod">';
											if($data['status'] == 'belum')
											{
												echo '<h4><span class="label label-success">Booking</span></h4>';
											}else if($data['status'] == 'batal')
											{
												echo '<h4><span class="label label-danger">Batal</span></h4>';
											}
											echo '</td>';
											echo '<td>';
											if(@$_GET['st']=='s')
											{
												echo '<a href="regis_booking.php" class="btn btn-success" value="Batal">Kembali</a>';
											}
											else
											{
												echo '<h4><span class="label label-danger">Batal</span></h4>';
											}
											echo '</td>';
										echo '</tr>';
									}
	                                echo '</tbody>';
	                            echo '</table>';
	                        echo '</div>';
	                    echo '</div>';
	                echo '</div>';
	            echo '</div>';
			break;

			case 'edit-booking':
				$tgl_registrasi = date('Y-m-d',strtotime($_GET['tgl_registrasi']));
				$no_rm = $_SESSION['username'];

				$hari 		= hari_indo($tgl_registrasi);
			    $lsd 		= select_jadwal_poli($hari);
			    $cls 		= ConDataToArray($lsd,'kd_poli','nm_poli');

				$qcn = query('SELECT 
					a.no_reg, 
					a.tanggal_booking,
					a.jam_booking,
					a.tanggal_periksa,
					a.no_rkm_medis,
					a.status,
					a.kd_poli,
					a.kd_dokter,
					b.nm_poli,
					c.nm_dokter
					FROM booking_registrasi as a
					LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli
            		LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter
					WHERE 	a.tanggal_periksa=\''.$tgl_registrasi.'\' and 
							a.no_rkm_medis=\''.$no_rm.'\'');
				$rcn = fetch_assoc($qcn);

				if(@$_GET['st'] == 's')
				{
					echo '<div class="alert bg-green alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button> Pembatalan anda berhasil.</div>';
				}
				else if(@$_GET['st'] == 'e')
				{
					
					echo '<div class="alert bg-pink alert-dismissible" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button> Maaf pembatalan booking anda gagal, Silahkan coba beberapa saat lagi...</div>';
				}

				echo '<div class="row clearfix">';
	                echo '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">';
	                    echo '<div class="card">';
	                        echo '<div class="body table-responsive">';
	                            echo '<table class="table table-bordered table-striped table-hover display nowrap" width="100%">';
	                                echo '<tbody>';

	                                echo '<tr>';
	                                    echo '<th style="width:150px;">No. Antrian</th>';
	                                    echo '<td style="width:10px;">:</td>';
	                                    echo '<td>'.$rcn['no_reg'].'</td>';
	                                echo '</tr>';

	                                echo '<tr>';
	                                    echo '<th style="width:150px;">Tanggal Booking</th>';
	                                    echo '<td style="width:10px;">:</td>';
	                                    echo '<td>'.tanggal_indo($rcn['tanggal_booking'],true). ' - '. $rcn['jam_booking'].'</td>';
	                                echo '</tr>';

	                                echo '<tr>';
	                                    echo '<th style="width:150px;">Tanggal Periksa</th>';
	                                    echo '<td style="width:10px;">:</td>';
	                                    echo '<td class="tr_mod">'.tanggal_indo($rcn['tanggal_periksa'],true).'</td>';
	                                echo '</tr>';

	                                echo '<tr>';
	                                    echo '<th style="width:150px;">No Rm</th>';
	                                    echo '<td style="width:10px;">:</td>';
	                                    echo '<td>'.$rcn['no_rkm_medis'].'</td>';
	                                echo '</tr>';

	                                echo '<tr>';
	                                    echo '<th style="width:150px;">Poliklinik</th>';
	                                    echo '<td style="width:10px;">:</td>';
	                                    echo '<td>';
	                                    	echo htmlSelectFromArray($cls, 'name="id_poli_tujuan" id="id_poli_tujuan" class="form-control"', true,$rcn['kd_poli']);
	                                    echo '</td>';
	                                echo '</tr>';

	                                echo '<tr>';
	                                    echo '<th style="width:150px;">Dokter</th>';
	                                    echo '<td style="width:10px;">:</td>';
	                                    echo '<td>'.$rcn['nm_dokter'].'</td>';
	                                echo '</tr>';

	                                echo '<tr>';
	                                    echo '<th style="width:150px;">Pilih Dokter</th>';
	                                    echo '<td style="width:10px;">:</td>';
	                                    echo '<td>';
	                                    	
	                                    echo '</td>';
	                                echo '</tr>';

	                                echo '<tr>';
	                                    echo '<th style="width:150px;">Status</th>';
	                                    echo '<td style="width:10px;">:</td>';
	                                    echo '<td>';
	                                    if($rcn['status'] == 'belum')
										{
											echo '<h4><span class="label label-success">Booking</span></h4>';
										}else if($rcn['status'] == 'batal')
										{
											echo '<h4><span class="label label-danger">Batal</span></h4>';
										}
	                                    echo '</td>';
	                                echo '</tr>';

	                                echo '<tr>';
	                                    echo '<th style="width:150px;" colspan="3">';
	                                    	 echo '<button type="button" id="simpan-edit-booking" class="btn btn-primary" value="daftar">Simpan</button>&nbsp;';
	                                    echo '</th>';
	                                echo '</tr>';

	                                echo '</tbody>';
	                            echo '</table>';
	                        echo '</div>';
	                    echo '</div>';
	                echo '</div>';
	            echo '</div>';
			break;

			default:
			/*
			| Bagian : Form Pendaftaran Awal
			// 12-09-2018
			*/
				echo '<div class="row clearfix">';
					echo '<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">';
						echo '<div class="card">';
							echo '<div class="body">';
								echo '<form id="form_validation" action="" method="POST">';
									echo '<label for="email_address">Nama Lengkap</label>';
									echo '<div class="form-group">';
										echo '<div class="form-line">';
											
		                                    $pasien = fetch_array(query("
		                                        SELECT
		                                            *
		                                        FROM
		                                            pasien
		                                        WHERE
		                                            no_rkm_medis = '{$_SESSION['username']}'
		                                    "));
		                                  
		                                    echo '<input type="text" id="nama_lengkap" value="'.$pasien['nm_pasien'].'" class="form-control" disabled>';
										echo '</div>';
									echo '</div>';

									echo '<label for="email_address">Nomor Rekam Medik</label>';
		                            echo '<div class="form-group">';
		                                echo '<div class="form-line">';
		                                    echo '<input type="text" id="nomor_rm" value="'.$_SESSION['username'].'" class="form-control" disabled>';
		                                echo '</div>';
		                            echo '</div>';

		                            echo '<label for="email_address">Tanggal Kunjungan</label>';
		                            echo '<div class="form-group">';
		                                echo '<div class="form-line">';
		                                    /*echo '<button type="button" class="btn btn-default btn-sm">';
		                                      echo '<span class="glyphicon glyphicon-calendar"></span>';
		                                    echo '</button>';*/
		                                    echo '<input type="text" name="tgl_registrasi" id="tgl_registrasi" class="datepicker form-control" placeholder="Masukan Tanggal">';
		                                echo '</div>';
		                            echo '</div>';

		                            echo '<label for="email_address">Status Tujuan</label>';
		                            echo '<div class="form-group">';
		                                //echo '<div class="form-line">';
		                                    echo '<button type="button" class="btn btn-primary" name="tujuan" id="tujuan" value="daftar">Daftar</button>&nbsp;';
		                                    echo '<button type="button" class="btn btn-primary" name="tujuan" id="tujuan" value="batal">Batal</button>';
		                                    echo '&nbsp;<button type="button" class="btn btn-primary" name="tujuan" id="tujuan" value="edit-booking">Edit Poli</button>';
		                                //echo '</div>';
		                            echo '</div>';

								echo '</form>';
							echo '</div>';
						echo '</div>';
					echo '</div>';
				echo '</div>';
			/*
			| Bagian : Form Pendaftaran Akhir
			*/
			break;
		}

	echo '</div>';
echo '</section>';

echo '<div id="RegAwal" class="modal fade" role="dialog" style="display: none">';
	echo '<div class="modal-dialog">';
		echo '<div class="modal-content">';
		  echo '<div class="modal-header">';
		    echo '<button type="button" class="close" data-dismiss="modal">&times;</button>';
		    echo '<h4 class="modal-title">Informasi Pendaftaran</h4>';
		  echo '</div>';
		  echo '<div class="modal-body" id="notif">';
		    
		  echo '</div>';
		  echo '<div class="modal-footer" id="mfooter">';
		    echo '<button type="button" class="btn btn-warning" data-dismiss="modal" id="tutup-pendaftaran" value="tutup-pendaftaran">Tutup</button>';
		  echo '</div>';
		echo '</div>';

	echo '</div>';
echo '</div>';

include_once ('layout/footer.php'); 
?>