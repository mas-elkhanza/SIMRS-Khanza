<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Ps_bc_view extends backend_controller {

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('Ld_model','User_model'));
		$this->site->is_logged_in();
		$host = base_url();
	}

	public function index()
	{
		$data = array(
			'heading' => 'Akses salah...',
			'message' => 'Maaf kami tidak bisa memunculkan halaman yang anda cari..'
		);
		$this->site->view_error('error_404',$data);
	}

	public function action($action='')
	{
		if($action == 'view')
		{
			global $SConfig;
			$f 		= ($this->uri->segment(4))	? $this->uri->segment(4) : 'f';
			$l 		= ($this->uri->segment(5))	? $this->uri->segment(5) : 10;
			$p 		= ($this->uri->segment(6))	? $this->uri->segment(6) - 1 : 0;

			$pq 	= $p * $l;
			$no 	= ($p * $l) + 1;

			$where 	= 'status="Y" ';
			if($f!='f')
			{
				$where = 'judul like \'%'.$f.'%\'';
			}

			$jum 	= $this->Ld_model->count('brodcast',$where);
			$r 		= $this->Ld_model->getRecordList('brodcast','*',$where,'','id DESC',$l,$pq);

			$rd 	= $this->Ld_model->getRecordList('dokter','kd_dokter,nm_dokter');
			$lsrd	= ConDataToArray($rd,'kd_dokter','nm_dokter');

			// Get Nama Admin
			$qnml	= $this->User_model->getRecordList('user','ID,nm_lengkap');
			$lsnml 	=  ConDataToArray($qnml,'ID','nm_lengkap');

			$data = array(
			'title'		=> 'Administrasi Pesan Brodcast',
			'f' 		=> $f,
			'l' 		=> $l,
			'p' 		=> $p,
			'pq' 		=> $pq,
			'no' 		=> $no,
			'jum' 		=> $jum,
			'r'			=> $r,
			'limit'		=> $SConfig->_limit,
			'dokter'	=> $lsrd,
			'nm_admin'	=> $lsnml,
			'v'			=> 'ad_brodcast'
			);

			$this->site->view('v',$data);
		}

		else if($action == 'get_poli')
		{	
			$p 	= $this->input->post();
			$rp 	= $this->Ld_model->get_join_poli(array('kd_dokter'=>$p['kd_dokter']));
			$lsrp	= ConDataToArray($rp,'kd_poli','nm_poli');
			
				echo '<option>Pilih Poli</option>';
			foreach ($lsrp as $k => $v) {
				echo '<option value="'.$k.'">'.$v.'</option>';
			}
		}

		else if($action == 'savebc')
		{
			$p = $this->input->post();
			
			// Update batch booking_registrasi
			$u['status'] = 'dokter-berhalangan';

			// Insert batch tabel brodcast
			$d['kd_poli'] 		= isset($p['kd_poli']) ? $p['kd_poli'] : '';
			$d['kd_dokter'] 	= isset($p['kd_dokter']) ? $p['kd_dokter'] : '';
			$d['judul'] 		= isset($p['judul']) ? $p['judul'] : '';
			$d['keterangan'] 	= isset($p['keterangan']) ? $p['keterangan'] : '';
			$d['masuk_tgl']		= date('Y-m-d H:i:s');
			$d['masuk_oleh']	= $this->session->userdata('ID');
			$d['status']		= 'Y';
			
			// Insert tabel bc info cuti
			$bc['kd_dokter']	= isset($p['kd_dokter']) ? $p['kd_dokter'] : '';
			$bc['kd_poli']		= isset($p['kd_poli']) ? $p['kd_poli'] : '';
			
			// Insert tabel jadwal cuti
			$c['kd_dokter']		= isset($p['kd_dokter']) ? $p['kd_dokter'] : '';
			$c['tgl_awal']		= isset($p['tgl_awal']) ? date('Y-m-d',strtotime($p['tgl_awal'])) : '';
			$c['tgl_akhir']		= isset($p['tgl_akhir']) ? date('Y-m-d',strtotime($p['tgl_akhir'])) : '';
			$c['status']		= 'Y';

			$rules = $this->Ld_model->bc;
			$this->form_validation->set_rules($rules);

			if($p['id'])
			{
				if($this->form_validation->run() == FALSE)
				{
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully update, please try again later';
					$this->Ld_model->createHistory('Gagal update pesan bc : '.$p['kd_dokter'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully update';
					$this->Ld_model->createHistory('Update update pesan bc : '.$p['kd_dokter'].'',$this->session->userdata('ID'));
					$this->Ld_model->update('brodcast',$d,array('id'=>$p['id']));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
			else
			{
				if($this->form_validation->run() == FALSE)
				{
					
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully saved, please try again later';
					$this->Ld_model->createHistory('Gagal tambah pesan bc : '.$p['kd_dokter'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully saved';
					
					$id_bc_info_cuti 		= $this->Ld_model->insert('bc_info_cuti',$bc);
					$c['id_bc_info_cuti'] 	= $id_bc_info_cuti;
					
					foreach ($p['tglperiksa'] as $k => $v) {
						$wh_update_booking = array('kd_dokter'=>$p['kd_dokter'], 'kd_poli'=>$p['kd_poli'], 'tanggal_periksa'=>$v);
						$d['tgl_periksa'] 	= $v;

						// Booking registrasi
						$wh_cek_jumlah_pasien = array('kd_dokter'=>$p['kd_dokter'], 'kd_poli'=>$p['kd_poli'], 'tanggal_periksa'=>$v);

						// Report Pendaftaran Online
						$wh_report_pendaftaran = array('tanggal'=>$v, 'kd_dokter'=>$p['kd_dokter'],'kd_poli'=>$p['kd_poli'], 'kategori'=>'dokter-berhalangan');

						$this->Ld_model->update('booking_registrasi',$u,$wh_update_booking);
						$this->Ld_model->insert('brodcast',$d,FALSE);

						$get_jml_pasien = $this->Ld_model->count('booking_registrasi',$wh_cek_jumlah_pasien);
						$cbooking		= $this->Ld_model->count('report_pendaftaran_online',$wh_report_pendaftaran);

						$report_log = array();
						if($cbooking > 0)
						{
							$report_log['jumlah']	= $cbooking + $get_jml_pasien;
							$this->Ld_model->update('report_pendaftaran_online',$report_log,$wh_report_pendaftaran);
						}
						else
						{
							$report_log['tanggal']	= $v;
							$report_log['kd_poli']	= $p['kd_poli'];
							$report_log['kd_dokter']	= $p['kd_dokter'];
							$report_log['kategori']	= "dokter-berhalangan";
							$report_log['jumlah']	= $get_jml_pasien;
							$this->Ld_model->insert('report_pendaftaran_online',$report_log,FALSE);
						}
					}

					$this->Ld_model->insert('jadwal_libur',$c);

					$this->Ld_model->createHistory('Tambah pesan bc : '.$p['kd_dokter'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
		}

		else if($action == 'get_data_booking_pasien')
		{
			$p = $this->input->post();

			$rd 	= $this->Ld_model->getRecordList('dokter','kd_dokter,nm_dokter');
			$lsrd	= ConDataToArray($rd,'kd_dokter','nm_dokter');

			$rp 	= $this->Ld_model->getRecordList('poliklinik','kd_poli,nm_poli');
			$lsrp	= ConDataToArray($rp,'kd_poli','nm_poli');

			$get_data = $this->Ld_model->getRecordList('booking_registrasi','tanggal_periksa,kd_poli,kd_dokter',array('kd_dokter' => $p['kd_dokter'],'kd_poli'=>$p['kd_poli'],'status'=>'belum'),'tanggal_periksa','tanggal_periksa ASC');
			
			echo '<table class="table table-hover table-bordered table-striped">';
				echo '<thead class="dark">';
					echo '<tr>';
						echo '<th style="width: 20px;">Centang</th>';
						echo '<th style="width: 150px;">Tanggal Periksa</th>';
						echo '<th style="width: 250px;">Nama Poli</th>';
						echo '<th>Nama Dokter</th>';
					echo '</tr>';
				echo '</thead>';
				echo '<body>';
				foreach ($get_data as $k => $v) {
					echo '<tr>';
						echo '<td><input type="checkbox" id="tglperiksa" name="tglperiksa[]" value="'.$v->tanggal_periksa.'"></td>';
						echo '<td>'.$v->tanggal_periksa.'</td>';
						echo '<td>'.$lsrp[$v->kd_poli].'</td>';
						echo '<td>'.$lsrd[$v->kd_dokter].'</td>';
					echo '</tr>';
				}
				echo '</body>';
			echo '</table>';
		}
	}
}