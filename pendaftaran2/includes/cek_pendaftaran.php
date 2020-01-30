<?php  
ob_start();
session_start();

include_once('../config.php');

	$aksi				= isset($_POST['aksi']) ? $_POST['aksi'] : '';
	$tgl_registrasi 	= isset($_POST['tgl_registrasi']) ? date('Y-m-d',strtotime($_POST['tgl_registrasi'])) : '';

	// 12-09-2018
	if($aksi == 'cek-pendaftaran')
	{
		/*
		| Transfer data via post melalui ajax
		| Penamaan variable
		*/
		$id_norm			= isset($_POST['id_norm']) ? $_POST['id_norm'] : '';

		$tgl_sekarang   = date_create($date);
		date_add($tgl_sekarang, date_interval_create_from_date_string(HARIDAFTAR.' days'));
		$tgl_kedepan  = date_format($tgl_sekarang, 'Y-m-d');

		if($tgl_registrasi == $date && $time > LIMITJAM)
		{
			$view_error = '<div class="alert bg-pink alert-dismissible" role="alert">Jam pendaftaran anda sudah lewat jam '.LIMITJAM.' WIB. Silahkan pilih tanggal periksa yang lain.</div>';
			$status = 1;
		}

		else if($tgl_registrasi < $date)
		{
			$view_error = '<div class="alert bg-pink alert-dismissible" role="alert">Tanggal pendaftaran anda sudah lewat. Silahkan pilih tanggal periksa yang lain.</div>';
			$status = 1;
		}

		else if($tgl_registrasi > $tgl_kedepan)
		{
			$view_error = '<div class="alert bg-pink alert-dismissible" role="alert">Tanggal pendaftaran anda lebih dari hari yang ditentukan. Silahkan pilih tanggal periksa yang lain.</div>';
			$status = 1;
		}
		else
		{
			$view_error = 'regis_booking.php?action=pilih-poli&tgl_registrasi='.$tgl_registrasi;
			$status = $tgl_registrasi;
		}

		$data = array(
		'e' => $view_error,
		's' => $status
		);

		echo json_encode($data);
	}
	
	// 13-09-2018
	else if($aksi == 'get-dokter')
	{
		$lsd = array();
		$hari = hari_indo($tgl_registrasi);
		$kd_poli = isset($_POST['kd_poli']) ? $_POST['kd_poli'] : '';
		if($kd_poli)
		{
			$q =	query("
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
                        jadwal.kd_poli = '$kd_poli'
                    AND
                        jadwal.hari_kerja LIKE '%$hari%'
                ");
			echo '<select name="kd_dokter" id="kd_dokter" class="form-control show-tick">';
			echo '<option>--- Pilih Dokter ---</option>';
			while($data = fetch_array($q))
		    {
		    	echo '<option value="'.$data['kd_dokter'].'">'.$data['nm_dokter'].'</option>';   
		    }
		    echo '</select>';
		}
		else
		{
			echo '<select name="kd_dokter" id="kd_dokter" class="form-control show-tick">';
			echo '<option>--- Pilih Dokter ---</option>';
			echo '</select>';
		}
	}

	// 16-09-2018
	else if($aksi == 'cek-info')
	{
		$kd_dokter 		= ($_POST['kd_dokter']) ? $_POST['kd_dokter'] : '';
		$id_poli 		= ($_POST['kd_poli']) ? $_POST['kd_poli'] : '';
		$no_rm 			= ($_POST['no_rm']) ? $_POST['no_rm'] : '';
		$tgl_sekarang 	= date('Y-m-d');


		$tnow   = date_create($tgl_sekarang);
		date_add($tnow, date_interval_create_from_date_string(HARIDAFTAR.' days'));
		$tgl_kedepan  = date_format($tnow, 'Y-m-d');

		$con_tgl 	= abs(strtotime($tgl_sekarang) - strtotime($tgl_kedepan));
		$con_hari	= $con_tgl/86400;

		// Select booking berdasarkan tgl_registrasi
		$qbok = query('SELECT tanggal_periksa FROM booking_registrasi WHERE no_rkm_medis=\''.$no_rm.'\' and tanggal_periksa=\''.$tgl_registrasi.'\' and kd_poli=\''.$id_poli.'\' and status="Belum"');
		$rbok = fetch_assoc($qbok);

		// Select Nama Poli
		$qnmp = query('SELECT nm_poli FROM poliklinik WHERE kd_poli=\''.$id_poli.'\'');
		$rnmp = fetch_assoc($qnmp);

		// Select limit booking online
		$qlmbo = query('SELECT * FROM limit_pasien_online WHERE kd_dokter=\''.$kd_dokter.'\'');
		$rlmbo = fetch_assoc($qlmbo);

		$lmt_reg_online = ($rlmbo['limit_reg']) ? $rlmbo['limit_reg'] : 30;
		$nm_dokter = select_nm_dokter($kd_dokter);

		if($rbok['tanggal_periksa'])
		{
			$d = array(
	    		'status' => 'booking',
	    		'url'	=> 'regis_booking.php',
	    		'dokter' => '',
	    		'view' => '<div class="alert bg-pink alert-dismissible" role="alert">Anda sudah terdaftar pada Tanggal : '.date('d M Y', strtotime($tgl_registrasi)).' , Tujuan : '. $rnmp['nm_poli'] .' , Dokter : '. $nm_dokter['nm_dokter'] .' .</div>'
    		);
		}
		else
		{
			// Select Jadwal Libur Dokter
			$qjdwl = query('SELECT * FROM jadwal_libur WHERE kd_dokter=\''.$kd_dokter.'\' order by id DESC');
			$rjdwl = fetch_assoc($qjdwl);

			$tgl_awal 	= date('Y-m-d',strtotime($rjdwl['tgl_awal']));
			$tgl_akhir 	= date('Y-m-d',strtotime($rjdwl['tgl_akhir']));

			// Select Limit Registrasi 
			$qlmrg = query('SELECT count(limit_reg) as total FROM booking_registrasi WHERE tanggal_periksa=\''.$tgl_registrasi.'\' and kd_dokter=\''.$kd_dokter.'\' and kd_poli=\''.$id_poli.'\' and status="Belum"');
			$rlmrg = fetch_assoc($qlmrg);

			//echo $rlmrg['total'];
			
			if($tgl_registrasi >= $tgl_sekarang)
			{
				if($tgl_registrasi >= $tgl_awal && $tgl_registrasi <= $tgl_akhir)
				{
					$d = array(
			    		'status' => 'cuti',
			    		'url'	=> 'regis_booking.php',
			    		'dokter' => '<button type="button" class="btn btn-warning" data-dismiss="modal">Pilih Dokter Lain</button>',
			    		'view' => '<div class="alert bg-pink alert-dismissible" role="alert">Mohon maaf Dokter : '. $nm_dokter['nm_dokter'] .' sedang cuti dari Tanggal '. date('d M Y', strtotime($tgl_awal)) .' sampai Tanggal '. date('d M Y', strtotime($tgl_akhir)) .'.</div>'
		    		);
				}
	
				else if($rlmrg['total'] >= $lmt_reg_online)
				{
					$d = array(
			    		'status' => 'booking-penuh',
			    		'url'	=> 'regis_booking.php',
			    		'dokter' => '<button type="button" class="btn btn-warning" data-dismiss="modal">Pilih Dokter Lain</button>',
			    		'view' => '<div class="alert bg-pink alert-dismissible" role="alert">Mohon maaf untuk Dokter : '. $nm_dokter['nm_dokter'] .' pada Poli : '. $rnmp['nm_poli'] .' sudah penuh kuota pendaftaran online untuk Tanggal '. date('d M Y', strtotime($tgl_registrasi)) .', Silahkan daftar secara langsung ke RSUD Kota Depok.</div>'
		    		);
				}

				else
				{
					$lscrbyr 	= select_cara_bayar('',$kd_dokter);
	   				$rcrbyr		= ConDataToArray($lscrbyr,'kd_pj','png_jawab');
					//htmlSelectFromArray($rcrbyr, 'name="cara_bayar" id="cara_bayar" class="form-control"', true);
					$d = array(
			    		'status' => 'kuota-tersedia',
			    		'url'	=> 'regis_booking.php',
			    		'dokter' => htmlSelectFromArray($rcrbyr, 'name="cara_bayar" id="cara_bayar" class="form-control"', true),
			    		'view' => '<div class="alert bg-pink alert-dismissible" role="alert">Pendaftaran Tersedia.</div>'
		    		);
				}
				
			}
			
		}

		echo json_encode($d);
	}

	// 17-09-2018
	else if($aksi == 'save')
	{
		$no_rm 			= ($_POST['no_rm']) ? $_POST['no_rm'] : '';
		$cara_bayar 	= ($_POST['cara_bayar']) ? $_POST['cara_bayar'] : '';
		$kd_poli 		= ($_POST['kd_poli']) ? $_POST['kd_poli'] : '';
		$kd_dokter 		= ($_POST['kd_dokter']) ? $_POST['kd_dokter'] : '';
		$tgl_booking 	= date('Y-m-d');
		$tgl_periksa 	= $tgl_registrasi;
		$nm_cara_bayar	= select_cara_bayar($cara_bayar);
		$nm_dokter = select_nm_dokter($kd_dokter);

		// Select Nama Poli
		$qnmp = query('SELECT nm_poli FROM poliklinik WHERE kd_poli=\''.$kd_poli.'\'');
		$rnmp = fetch_assoc($qnmp);

		// Select limit booking online
		$qlmbo = query('SELECT * FROM limit_pasien_online WHERE kd_dokter=\''.$kd_dokter.'\'');
		$rlmbo = fetch_assoc($qlmbo);

		// Select Limit Registrasi 
		$qlmrg = query('SELECT count(limit_reg) as total FROM booking_registrasi WHERE tanggal_periksa=\''.$tgl_registrasi.'\' and kd_dokter=\''.$kd_dokter.'\' and kd_poli=\''.$kd_poli.'\' and status="Belum"');
		$rlmrg = fetch_assoc($qlmrg);

		// Select Jadwal Libur Dokter
		$qjdwl = query('SELECT * FROM jadwal_libur WHERE kd_dokter=\''.$kd_dokter.'\' order by id DESC');
		$rjdwl = fetch_assoc($qjdwl);

		$tgl_awal 	= date('Y-m-d',strtotime($rjdwl['tgl_awal']));
		$tgl_akhir 	= date('Y-m-d',strtotime($rjdwl['tgl_akhir']));


		if($tgl_registrasi >= $tgl_awal && $tgl_registrasi <= $tgl_akhir)
		{
			$d = array(
	    		'status' => 'cuti',
	    		'url'	=> 'regis_booking.php',
	    		'dokter' => '<button type="button" class="btn btn-warning" data-dismiss="modal">Pilih Dokter Lain</button>',
	    		'view' => '<div class="alert bg-pink alert-dismissible" role="alert">Mohon maaf Dokter : '. $nm_dokter['nm_dokter'] .' sedang cuti dari Tanggal '. date('d M Y', strtotime($tgl_awal)) .' sampai Tanggal '. date('d M Y', strtotime($tgl_akhir)) .'.</div>'
    		);
		}
		else
		{
			if($rlmrg['total'] >= $rlmbo['limit_reg'])
			{
				$d = array(
		    		'status' => 'booking-penuh',
		    		'url'	=> 'regis_booking.php',
		    		'dokter' => '<button type="button" class="btn btn-warning" data-dismiss="modal">Pilih Dokter Lain</button>',
		    		'view' => '<div class="alert bg-pink alert-dismissible" role="alert">Mohon maaf untuk Dokter : '. $nm_dokter['nm_dokter'] .' pada Poli : '. $rnmp['nm_poli'] .' sudah penuh kuota pendaftaran online untuk Tanggal '. date('d M Y', strtotime($tgl_registrasi)) .', Silahkan daftar secara langsung ke RSUD Kota Depok.</div>'
	    		);
			}
			else
			{
				if($no_rm)
				{
					// Cek No Reg Terakhir
					$qgbk 	= query('SELECT no_reg,limit_reg FROM booking_registrasi WHERE tanggal_periksa=\''.$tgl_periksa.'\' and kd_dokter=\''.$kd_dokter.'\' and kd_poli=\''.$kd_poli.'\' and status="Belum" order by no_reg DESC');
					$rgbk 	= fetch_array($qgbk); 

					// Pembuatan No Registrasi
					$no_urut_reg = substr($rgbk['no_reg'], 0, 3);
		            @$no_reg = sprintf('%03s', ($no_urut_reg + 1));

		            // Mendapatakan nama hari dari tanggal registrasi
		            $hari = hari_indo($tgl_registrasi);

		            // Ambil waktu pengurangan jam prakter dan waktu praktek dari tabel limit_pasien_online
		            $rlmt_dokter 		= select_tb_limit_dokter($kd_dokter);
		            $limit_kedatangan 	= $rlmt_dokter['limit_kedatangan'];
		            $waktu_praktek 		= $rlmt_dokter['waktu_praktek'];

		            // Ambil nama poli


		            // Ambil jam praktek dokter
		            $rjam_mulai = select_jam_praktek($kd_dokter,$hari);

		            // Perkalian limit dan no registrasi
		            $wkt_kunjungna_menit = $limit_kedatangan * $no_reg;

		            // Menyatukan Tanggal Registrasi dengan Jam mulai prkatek
					$satukan_tgl = date('Y-m-d H:i:s', strtotime($tgl_registrasi . $rjam_mulai));

					// Pengurangan 30 menit sebelum praktek
					$tentukan_waktu = date_create($satukan_tgl); // Jadikan format data menjadi tanggal
					date_add($tentukan_waktu, date_interval_create_from_date_string('-'. $waktu_praktek .' minutes'));
					$jam_kurang_wkt_praktek =  date_format($tentukan_waktu, 'Y-m-d H:i:s');

					// Penambahan 5 menit setiap pasien
					$tentukan_waktu_pengurangan = date_create($jam_kurang_wkt_praktek);
					date_add($tentukan_waktu_pengurangan, date_interval_create_from_date_string('+'. $wkt_kunjungna_menit . ' minutes'));
					$waktu_datang = date_format($tentukan_waktu_pengurangan, 'Y-m-d H:i:s');
					$tanggal_datang = date('Y-m-d',strtotime($waktu_datang));
					$jam_datang = date('H:i:s',strtotime($waktu_datang));
					$waktu_format_indonesia = tanggal_indo($tanggal_datang,true);
					//$waktu_format_indonesia . ' '. $jam_datang;

					// Get Data Pasien
					$gpasien = select_pasien($no_rm);
					$nm_poli = select_nm_poli($kd_poli);
					$nm_dokter = select_nm_dokter($kd_dokter);

					$insert = mysqli_query($connection,'INSERT INTO booking_registrasi SET tanggal_booking =\''.$tgl_booking.'\',
								jam_booking		=\''.$time.'\',
		                    	no_rkm_medis    =\''.$no_rm.'\',
		                    	tanggal_periksa =\''.$tgl_registrasi.'\',
		                    	kd_dokter       =\''.$kd_dokter.'\',
		                        kd_poli         =\''.$kd_poli.'\',
		                    	no_reg          =\''.$no_reg.'\',
		                    	kd_pj			=\''.$cara_bayar.'\',
		                        limit_reg       = 1,
		                        waktu_kunjungan =\''.$waktu_datang.'\',
		                        status = "belum"');

					if($insert)
					{
						$cbooking = fetch_assoc(query('SELECT count(*) as total FROM report_pendaftaran_online WHERE tanggal=\''.$tgl_registrasi.'\' and kd_dokter=\''.$kd_dokter.'\' and kd_poli=\''.$kd_poli.'\' and kategori="belum"'));
						
						$report_log = array();
						if($cbooking['total'] > 0)
						{
							$report_log['jumlah']	= $cbooking['total'] + 1;
							query('UPDATE report_pendaftaran_online SET jumlah=\''.$report_log['jumlah'].'\' WHERE tanggal=\''.$tgl_registrasi.'\' and kd_dokter=\''.$kd_dokter.'\' and kd_poli=\''.$kd_poli.'\' and kategori="belum"');	
						}
						else
						{
							$report_log['tanggal']	= $tgl_registrasi;
							$report_log['kd_poli']	= $kd_poli;
							$report_log['kd_dokter']	= $kd_dokter;
							$report_log['kategori']	= "belum";
							$report_log['jumlah']	= 1;

							$field = implode(',', array_keys($report_log));
	        				$value = '\'' . implode('\',\'', $report_log) . '\'';

							query('INSERT INTO report_pendaftaran_online (' . $field . ') VALUES(' . $value . ')');
						}

						$d = array(
							'status' => 'selesai',
							'nama_lengkap' => $gpasien['nm_pasien'],
							'tanggal_booking' => $tgl_booking,
							'jam_booking' => $time,
							'no_rm' => $no_rm,
							'tgl_periksa' => $tgl_registrasi,
							'no_urut' => $no_reg,
							'nm_poli' => $nm_poli['nm_poli'],
							'nm_dokter' => $nm_dokter['nm_dokter'],
							'kd_pj' => $cara_bayar,
							'waktu_kunjungan' => $waktu_datang,
							'url' => 'regis_booking.php?action=selesai&nama_lengkap='.$gpasien['nm_pasien'].'&tanggal_booking='.$tgl_booking.'&jam_booking='.$time.'&no_rm='.$no_rm.'&tgl_periksa='.$tgl_registrasi.'&no_urut='.$no_reg.'&nm_poli='.$nm_poli['nm_poli'].'&nm_dokter='.$nm_dokter['nm_dokter'].'&kd_pj='.$nm_cara_bayar['png_jawab'].'&waktu_kunjungan='.$waktu_datang 
						);
					}
					else
					{
						$d = array(
							'status' => 'gagal',
							'url' => 'regis_booking.php',
							'view' => '<div class="alert bg-pink alert-dismissible" role="alert">Mohon maaf anda tidak bisa mendaftar pada tanggal yang sama ke dua poliklinik...</div>'
						);
					}
				}
			}
		}
		echo json_encode($d);

	}

	// 20-09-2018
	else if($aksi == 'cabar')
	{
		$cara_bayar 	= ($_POST['cara_bayar']) ? $_POST['cara_bayar'] : '';
		$kd_poli 		= ($_POST['kd_poli']) ? $_POST['kd_poli'] : '';
		$kd_dokter 		= ($_POST['kd_dokter']) ? $_POST['kd_dokter'] : '';

		// Hitung Jumlah pendaftar online berdasarkan cara bayar
		$qlmrg = query('SELECT count(limit_reg) as total FROM booking_registrasi WHERE tanggal_periksa=\''.$tgl_registrasi.'\' and kd_dokter=\''.$kd_dokter.'\' and kd_poli=\''.$kd_poli.'\' and kd_pj=\''.$cara_bayar.'\' and status="Belum"');
		$rlmrg = fetch_assoc($qlmrg);
		
		// Ambil jumlah limit dokter
		$qlmbo = query('SELECT * FROM limit_pasien_online WHERE kd_dokter=\''.$kd_dokter.'\'');
		$rlmbo = fetch_assoc($qlmbo);
		$lmt_reg_online = ($rlmbo['limit_reg']) ? $rlmbo['limit_reg'] : 30;

		// Dapatkan jumlah kuota umum 
		if($cara_bayar == 'BPJ')
		{
			$kuota = !empty($rlmbo['bpjs']) ? $rlmbo['bpjs'] : $lmt_reg_online;
		}
		else if($cara_bayar == 'A09')
		{
			$kuota = !empty($rlmbo['umum']) ? $rlmbo['umum'] : $lmt_reg_online;
		}

		$nnm_pnjb = select_cara_bayar($cara_bayar,'');
		//echo $kuota . $rlmrg['total'];
		//print_r($_POST);
		if($rlmrg['total'] >= $kuota)
		{
			$d = array(
	    		'status' => 'penuh',
	    		'url'	=> 'regis_booking.php',
	    		'cabar' => '<button type="button" class="btn btn-warning" data-dismiss="modal">Pilih Cara Bayar Lain</button>',
	    		'view' => '<div class="alert bg-pink alert-dismissible" role="alert">Mohon maaf untuk kuota pendaftaran online dengan cara bayar '.$nnm_pnjb['png_jawab'].' sudah penuh silahkan gunakan cara bayar yang lain atau datang langsung ke RSUD KOTA DEPOK.</div>'
    		);
		}
		else
		{
			$d = array(
	    		'status' => 'tersedia',
	    		'url'	=> 'regis_booking.php',
	    		'view' => '<div class="alert bg-pink alert-dismissible" role="alert">Pendaftaran Tersedia.</div>'
    		);
		}

		echo json_encode($d);

	}

	else if($aksi == 'batal')
	{
		$view_error = 'regis_booking.php?action=batal-pendaftaran&tgl_registrasi='.$tgl_registrasi;
		$status = $tgl_registrasi;	
		
		$data = array(
			'e' => $view_error,
			's' => $status
		);

		echo json_encode($data);
	}

	else if($aksi == 'edit-booking')
	{
		$view_error = 'regis_booking.php?action=edit-booking&tgl_registrasi='.$tgl_registrasi;
		$status = $tgl_registrasi;	
		
		$data = array(
			'e' => $view_error,
			's' => $status
		);

		echo json_encode($data);
	}

	else if($aksi == 'kirim-pesan')
	{
		$qget_idpesan 	= query('SELECT idpesan FROM pesan order by idpesan DESC');
		$rget_idpesan 	= fetch_assoc($qget_idpesan);
		$no_idpesan		= ($rget_idpesan['idpesan']) ? $rget_idpesan['idpesan']+1 : 1;

		$insert = query('INSERT INTO pesan SET 
						idpesan 	=\''.$no_idpesan.'\',
						judul		=\''.$_POST['judul'].'\',
                    	isi 		=\''.$_POST['isi'].'\',
                    	pengirim    =\''.$_SESSION['username'].'\',
                    	masuk_tgl   =\''.date('Y-m-d H:i:s').'\',
                        status      =1');
		if($insert)
		{
			$d = array(
				'status' => 'sukses',
				'view' => '<div class="alert bg-info alert-dismissible" role="alert">Pesan anda sudah terkirim...</div>'
			);
		}
		else
		{
			$d = array(
				'status' => 'gagal',
				'view' => '<div class="alert bg-pink alert-dismissible" role="alert">Mohon maaf pesan anda gagal dikirim...</div>'
			);
		}

		echo json_encode($d);
	}

	else if($aksi == 'proses-batal')
	{

		$no_rm = $_SESSION['username'];
		if($tgl_registrasi!='' && $no_rm!='')
		{
			if(query('UPDATE booking_registrasi SET status="Batal" WHERE no_rkm_medis=\''.$no_rm.'\' and tanggal_periksa=\''.$tgl_registrasi.'\' and kd_dokter=\''.$_POST['kd_dokter'].'\' and kd_poli=\''.$_POST['kd_poli'].'\''))
			{

				$cbooking = fetch_assoc(query('SELECT count(*) as total FROM report_pendaftaran_online WHERE tanggal=\''.$tgl_registrasi.'\' and kd_dokter=\''.$_POST['kd_dokter'].'\' and kd_poli=\''.$_POST['kd_poli'].'\' and kategori="batal"'));
					
					$report_log = array();
					if($cbooking['total'] > 0)
					{
						$report_log['jumlah']	= $cbooking['total'] + 1;
						query('UPDATE report_pendaftaran_online SET jumlah=\''.$report_log['jumlah'].'\' WHERE tanggal=\''.$tgl_registrasi.'\' and kd_dokter=\''.$_POST['kd_dokter'].'\' and kd_poli=\''.$_POST['kd_poli'].'\' and kategori="batal"');	
					}
					else
					{
						$report_log['tanggal']	= $tgl_registrasi;
						$report_log['kd_poli']	= $_POST['kd_poli'];
						$report_log['kd_dokter']	= $_POST['kd_dokter'];
						$report_log['kategori']	= "batal";
						$report_log['jumlah']	= 1;

						$field = implode(',', array_keys($report_log));
        				$value = '\'' . implode('\',\'', $report_log) . '\'';

						query('INSERT INTO report_pendaftaran_online (' . $field . ') VALUES(' . $value . ')');
					}

				$d = array(
					'status' => 'Sukses',
					'e' => 'regis_booking.php?action=batal-pendaftaran&st=s&tgl_registrasi='.$tgl_registrasi
				);
			}
			else
			{
				$d = array(
					'status' => 'Gagal',
					'e' => 'regis_booking.php?action=batal-pendaftaran&st=e&tgl_registrasi='.$tgl_registrasi
				);
			}

			echo json_encode($d);
		}
	}

	// 18-09-2018
	else if(@$_GET['aksi'] == 'cetak-data')
	{
		//require_once "dom5/dompdf_config.inc.php";
		require_once('fpdf17/fpdf.php');
		/*header('Content-Type: application/x-msdownload'); 
		Header('Content-Type: application/pdf');
		Header('Content-disposition: attachment; filename=Bukti_pendaftaran');  */
				
		$d = $_GET;
		$ex_wd = explode(" ", $d['waktu_kunjungan']);
				
		//Create a new PDF file
		$pdf = new FPDF('P','mm',array(125,176)); //L For Landscape / P For Portrait
		$pdf->AddPage();

		//Menambahkan Gambar
		//$pdf->Image('../foto/logo.png',10,10,-175);

		$pdf->SetFont('Arial','B',13);
		$pdf->Cell(40);
		$pdf->Cell(30,0,'RSUD KOTA DEPOK',0,0,'C');
		$pdf->SetY(12);
		$pdf->SetFont('Arial','B',8);
		$pdf->Cell(8);
		$pdf->Cell(90,5,'Jl. Raya Muchtar No.99, Sawangan Lama, Sawangan',0,0,'C');
		$pdf->Ln();
		$pdf->SetY(15);
		$pdf->Cell(8);
		$pdf->Cell(90,5,'Kota Depok, Jawa Barat 16511',0,0,'C');
		$pdf->Ln();
		$pdf->SetY(18);
		$pdf->Cell(8);
		$pdf->Cell(90,5,'Telp : (0251) 8602514',0,0,'C');
		$pdf->Ln();

		//Fields Name position
		$Y_Fields_Name_position = 35;

		//First create each Field Name
		//Gray color filling each Field Name box
		$pdf->SetFillColor(110,180,230);
		//Bold Font for Field Name
		$pdf->SetFont('Arial','B',10);
		$pdf->SetY($Y_Fields_Name_position);
		$pdf->SetX(1);
		$pdf->Cell(40,8,'Bukti Pendaftaran Online', 0 ,0 , 'L' , 0);
		$pdf->Ln();
		$pdf->SetX(2);
		$pdf->Cell(40,8, 'No Urut Booking', 1 ,0 , 'C' , 1);
		$pdf->Ln();
		$pdf->SetFont('Arial','B',15);
		$pdf->SetX(2);
		$pdf->Cell(40,8, $d['no_urut'], 1 ,0 , 'C' , 1);
		$pdf->Ln();
		$pdf->SetFont('Arial','B',10);
		$pdf->SetX(2);
		$pdf->Cell(40,8,'Nomor Rekam Medik',1,0,'L',1);
		$pdf->SetX(42);
		$pdf->Cell(3,8,':',1,0,'C',0);
		$pdf->SetX(45);
		$pdf->Cell(78,8,$d['no_rm'],1,0,'L',0);
		$pdf->Ln();
		$pdf->SetX(2);
		$pdf->Cell(40,8,'Nama Lengkap',1,0,'L',1);
		$pdf->SetX(42);
		$pdf->Cell(3,8,':',1,0,'C',0);
		$pdf->SetX(45);
		$pdf->Cell(78,8,$d['nama_lengkap'],1,0,'L',0);
		$pdf->Ln();
		$pdf->SetX(2);
		$pdf->Cell(40,8,'Tanggal Booking',1,0,'L',1);
		$pdf->SetX(42);
		$pdf->Cell(3,8,':',1,0,'C',0);
		$pdf->SetX(45);
		$pdf->Cell(78,8, tanggal_indo($d['tanggal_booking'],true) .'  '. $d['jam_booking'],1,0,'L',0);
		$pdf->Ln();
		$pdf->SetX(2);
		$pdf->Cell(40,8,'Tanggal Periksa',1,0,'L',1);
		$pdf->SetX(42);
		$pdf->Cell(3,8,':',1,0,'C',0);
		$pdf->SetX(45);
		$pdf->Cell(78,8, tanggal_indo($d['tgl_periksa'],true),1,0,'L',0);
		$pdf->Ln();
		$pdf->SetX(2);
		$pdf->Cell(40,8,'Waktu Datang',1,0,'L',1);
		$pdf->SetX(42);
		$pdf->Cell(3,8,':',1,0,'C',0);
		$pdf->SetX(45);
		$pdf->Cell(78,8, tanggal_indo($ex_wd[0]) .'  '. $ex_wd[1] ,1,0,'L',0);
		$pdf->Ln();
		$pdf->SetX(2);
		$pdf->Cell(40,8,'Poliklinik Tujuan',1,0,'L',1);
		$pdf->SetX(42);
		$pdf->Cell(3,8,':',1,0,'C',0);
		$pdf->SetX(45);
		$pdf->Cell(78,8,$d['nm_poli'],1,0,'L',0);
		$pdf->Ln();
		$pdf->SetX(2);
		$pdf->Cell(40,8,'Dokter',1,0,'L',1);
		$pdf->SetX(42);
		$pdf->Cell(3,8,':',1,0,'C',0);
		$pdf->SetX(45);
		$pdf->Cell(78,8,$d['nm_dokter'],1,0,'L',0);
		$pdf->Ln();
		$pdf->SetX(2);
		$pdf->Cell(40,8,'Cara Bayar',1,0,'L',1);
		$pdf->SetX(42);
		$pdf->Cell(3,8,':',1,0,'C',0);
		$pdf->SetX(45);
		$pdf->Cell(78,8,$d['kd_pj'],1,0,'L',0);
		$pdf->Ln();
		$pdf->SetFont('Arial','',8);
		$pdf->SetX(2);
		$pdf->Cell(40,8,'Di Cetak Tanggal : ' . tanggal_indo(date('Y-m-d')), 0 ,0 , 'L' , 0);
		$pdf->SetY(128);
		$pdf->SetX(2);
		$pdf->Cell(25,6,'Catatan : Silahkan datang sesuai pada jam waktu datang yang telah ditentukan...',0,'L');

		$pdf->SetY(145);
		$pdf->SetFont('Arial','I',8);
		$pdf->Cell(0,10,'http://www.rsudreg.online ',0,0,'C');
		$pdf->Output();
	}

?>