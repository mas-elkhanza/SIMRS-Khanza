<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Ps_info_view extends backend_controller {

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

			$where 	= 'kategori="1" ';
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
			'title'		=> 'Manajemen Informasi',
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
			'v'			=> 'ad_info'
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

		else if($action == 'save')
		{
			date_default_timezone_set('Asia/Jakarta');
			$p = $this->input->post();
			$get_data = $this->Ld_model->getRecordList('jadwal','*',array('kd_dokter' => $p['kd_dokter'],'kd_poli'=>$p['kd_poli'],'hari_kerja'=> hari_indo($p['waktu_masuk'])),'','hari_kerja ASC');
			
			$tnow = date('Y-m-d');
			$tnows = date('Y-m-d H:i:s');
			
			$tgl_prkatek = date('Y-m-d H:i:s',strtotime($tnow . $get_data[0]->jam_mulai));
			
			$awal  = date_create($tgl_prkatek);
			$akhir = date_create(); // waktu sekarang, pukul 06:13
			$diff  = date_diff( $akhir, $awal );
			$jam_menit = ($diff->h * 60) + $diff->i;

			$d['kd_poli']		= isset($p['kd_poli']) ? $p['kd_poli'] : '';
			$d['kd_dokter']		= isset($p['kd_dokter']) ? $p['kd_dokter'] : '';
			$d['judul']			= isset($p['judul']) ? $p['judul'] : '';
			$d['keterangan']	= isset($p['keterangan']) ? $p['keterangan'] : '';
			$d['status']		= 'Y';
			$d['kategori']		= '1';
			$d['tgl_periksa']	= isset($p['tgl_periksa']) ? date('Y-m-d',strtotime($p['tgl_periksa'])) : '';
			$d['waktu_masuk']	= isset($p['waktu_masuk']) ? date('Y-m-d H:i:s',strtotime($p['waktu_masuk'])) : '';
			$d['masuk_tgl']		= date('Y-m-d H:i:s');
			$d['masuk_oleh']	= $this->session->userdata('ID');

			$rp['tanggal'] 		= date('Y-m-d');
			$rp['menit'] 		= $jam_menit;
			$rp['jumlah'] 		= 1;
			$rp['waktu_datang']	= isset($p['waktu_masuk']) ? date('Y-m-d H:i:s',strtotime($p['waktu_masuk'])) : '';


			if($tnows >= $tgl_prkatek)
			{
				$rp['kategori'] 		= 'telat';
			}
			else if($tnows == $tgl_prkatek)
			{
				$rp['kategori'] 		= 'tepat';
			}
			else if($tnows <= $tgl_prkatek)
			{
				$rp['kategori'] 		= 'cepat';
			}
			
			//exit();
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
					
					$this->Ld_model->insert('brodcast',$d,FALSE);
					$this->Ld_model->insert('report_jam_praktek_dokter',$rp,FALSE);

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

			$get_data = $this->Ld_model->getRecordList('jadwal','*',array('kd_dokter' => $p['kd_dokter'],'kd_poli'=>$p['kd_poli'],'hari_kerja'=> hari_indo($p['waktu_masuk'])),'','hari_kerja ASC');

			echo '<table class="table table-responsive table-hover table-bordered table-striped">';
				echo '<thead class="dark">';
					echo '<tr>';
						echo '<th style="width: 150px;">Nama Poli</th>';
						echo '<th style="width: 250px;">Nama Dokter</th>';
						echo '<th style="width: 150px;">Hari Kerja</th>';
						echo '<th style="width: 150px;">Jam Mulai</th>';
						echo '<th style="width: 150px;">Jam Selesai</th>';
					echo '</tr>';
				echo '</thead>';
				echo '<body>';
				foreach ($get_data as $k => $v) {
					echo '<tr>';
						echo '<td>'.$lsrp[$v->kd_poli].'</td>';
						echo '<td>'.$lsrd[$v->kd_dokter].'</td>';
						echo '<td>'.$v->hari_kerja.'</td>';
						echo '<td>'.$v->jam_mulai.'</td>';
						echo '<td>'.$v->jam_selesai.'</td>';
					echo '</tr>';
				}
				echo '</body>';
			echo '</table>';
		}
	}
}