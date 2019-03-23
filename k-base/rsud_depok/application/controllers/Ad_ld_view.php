<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Ad_ld_view extends backend_controller {

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

			$where 	= '';
			if($f!='f')
			{
				$where = 'kd_dokter like \'%'.$f.'%\' OR nm_dokter like \'%'.$f.'%\'';
			}

			$jum 	= $this->Ld_model->count('limit_pasien_online',$where);
			$r 		= $this->Ld_model->getRecordList('limit_pasien_online','*',$where,'','kd_dokter ASC',$l,$pq);
			
			// Get Dokter
			$gd 		= $this->Ld_model->getRecordList('dokter','kd_dokter,nm_dokter',array('status'=>'1'),'','kd_dokter ASC');
			$rd 		= ConDataToArray($gd,'kd_dokter','nm_dokter');

			// Get User
			$gu 		= $this->User_model->getRecordList('user','ID,nm_lengkap','','','ID ASC');
			$ru 		= ConDataToArray($gu,'ID','nm_lengkap');

			$data = array(
			'title'		=> 'Administrasi Limit Pasien',
			'f' 		=> $f,
			'l' 		=> $l,
			'p' 		=> $p,
			'pq' 		=> $pq,
			'no' 		=> $no,
			'jum' 		=> $jum,
			'r'			=> $r,
			'rd'		=> $rd,
			'ru'		=> $ru,
			'limit'		=> $SConfig->_limit,
			'v'			=> 'ad_ld'
			);

			/*echo '<pre>';
			print_r($r);*/
			$this->site->view('v',$data);
		}

		else if($action == 'save')
		{
			$post = $this->input->post();
			//$data['id'] 	= htmlentities($post['id'],ENT_QUOTES);
			$data['kd_dokter'] 	= htmlentities($post['kd_dokter'],ENT_QUOTES);
			$data['limit_reg'] 	= htmlentities($post['limit_reg'],ENT_QUOTES);
			$data['limit_kedatangan'] 	= htmlentities($post['limit_kedatangan'],ENT_QUOTES);
			$data['waktu_praktek'] 	= htmlentities($post['waktu_praktek'],ENT_QUOTES);
			$data['umum'] 	= htmlentities($post['umum'],ENT_QUOTES);
			$data['bpjs'] 	= htmlentities($post['bpjs'],ENT_QUOTES);
			$data['masuk_tgl'] 	= date('Y-m-d H:i:s');
			$data['masuk_oleh'] 	= $this->session->userdata('ID');

			$rules = $this->Ld_model->rules;
			$this->form_validation->set_rules($rules);
			
			if($post['id'])
			{
				if($this->form_validation->run() == FALSE)
				{
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully update, please try again later';
					$this->Ld_model->createHistory('Gagal update limit pendaftaran dengan kode dokter : '.$post['id'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully update';
					$this->Ld_model->createHistory('Update limit dengan kode dokter : '.$post['id'].'',$this->session->userdata('ID'));
					$this->Ld_model->update('limit_pasien_online',$data,array('kd_dokter'=>$post['id']));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
			else
			{
				if($this->form_validation->run() == FALSE)
				{
					
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully saved, please try again later';
					$this->Ld_model->createHistory('Gagal tambah limit dengan kode dokter : '.$post['id'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully saved';
					
					$this->Ld_model->createHistory('Tambah limi dengan kode dokter : '.$post['id'].'',$this->session->userdata('ID'));
					$this->Ld_model->insert('limit_pasien_online',$data,FALSE);
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}

		}
		else if($action == 'delete')
		{
			$uri = $this->uri->segment(4);
			$idl = explode('k', $uri);
			
			$_SESSION['error'] = '';
        	$_SESSION['msg']   = 'Data has been succesfully deleted';

			foreach ($idl as $idv)
			{
				$det = $this->Ld_model->getRecordList('limit_pasien_online','kd_dokter','kd_dokter=\''.$idv.'\'');
				//print_r($idv);
				if(!$this->Ld_model->delete_by('limit_pasien_online',array('kd_dokter'=>$idv)))
				{
					$_SESSION['error'] = '1';
                	$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully deleted, please try again later';
				}
				else
				{
					$this->Ld_model->createHistory('Delete limit pasien dengan kode dokter : '.$det[0]->kd_dokter.'',$this->session->userdata('ID'));
				}
			}
			generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
		}
	}
}