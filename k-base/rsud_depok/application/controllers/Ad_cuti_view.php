<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Ad_cuti_view extends backend_controller {

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('Ld_model'));
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
				$where = 'kd_dokter like \'%'.$f.'%\' OR tgl_awal like \'%'.$f.'%\' OR tgl_akhir like \'%'.$f.'%\'';
			}

			$jum 	= $this->Ld_model->count('jadwal_libur',$where);
			$r 		= $this->Ld_model->getRecordList('jadwal_libur','*',$where,'','id ASC',$l,$pq);

			$rd 	= $this->Ld_model->getRecordList('dokter','kd_dokter,nm_dokter');
			$lsrd	= ConDataToArray($rd,'kd_dokter','nm_dokter');

			$data = array(
			'title'		=> 'Administrasi Jadwal Cuti Dokter',
			'f' 		=> $f,
			'l' 		=> $l,
			'p' 		=> $p,
			'pq' 		=> $pq,
			'no' 		=> $no,
			'jum' 		=> $jum,
			'r'			=> $r,
			'lsd'		=> $lsrd,
			'limit'		=> $SConfig->_limit,
			'jenis'		=> $SConfig->_jenis,
			'status'	=> $SConfig->_status,
			'v'			=> 'ad_cuti'
			);

			/*echo '<pre>';
			print_r($r);
			exit();*/
			$this->site->view('v',$data);
		}

		else if($action == 'save')
		{
			$post = $this->input->post();
			$data['kd_dokter'] 	= htmlentities($post['kd_dokter'],ENT_QUOTES);
			$data['tgl_awal'] 	= date('Y-m-d',strtotime($post['tgl_awal']));
			$data['tgl_akhir'] 	= date('Y-m-d',strtotime($post['tgl_akhir']));
			$data['status'] 	= htmlentities($post['status'],ENT_QUOTES);

			$rules = $this->Ld_model->cuti;
			$this->form_validation->set_rules($rules);

			if($post['id'])
			{
				if($this->form_validation->run() == FALSE)
				{
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully update, please try again later';
					$this->Ld_model->createHistory('Gagal update Jadwal Cuti dengan Kode Dokter : '.$post['kd_dokter'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully update';
					$this->Ld_model->createHistory('Update Jadwal Cuti dengan Kode Dokter : '.$post['kd_dokter'].'',$this->session->userdata('ID'));
					$this->Ld_model->update('jadwal_libur',$data,array('id'=>$post['id']));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
			else
			{
				if($this->form_validation->run() == FALSE)
				{
					
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully saved, please try again later';
					$this->Ld_model->createHistory('Gagal tambah Jadwal Cuti dengan Kode Dokter : '.$post['kd_dokter'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully saved';
					
					$this->Ld_model->createHistory('Tambah Jadwal Cuti dengan Kode Dokter : '.$post['kd_dokter'].'',$this->session->userdata('ID'));
					$this->Ld_model->insert('jadwal_libur',$data,FALSE);
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
			//print_r($post);
		}
		else if($action == 'delete')
		{
			$uri = $this->uri->segment(4);
			$idl = explode('k', $uri);

			$_SESSION['error'] = '';
        	$_SESSION['msg']   = 'Data has been succesfully deleted';

			foreach ($idl as $idv)
			{
				$det = $this->Ld_model->getRecordList('jadwal_libur','kd_dokter','id=\''.$idv.'\'');
				if(!$this->Ld_model->delete('jadwal_libur',array('id' => $idv),$idv))
				{
					$_SESSION['error'] = '1';
                	$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully deleted, please try again later';
				}
				else
				{
					$this->Ld_model->createHistory('Delete Jadwal Cuti dengan Kode Dokter : '.$det[0]->kd_dokter.'',$this->session->userdata('ID'));
				}
				//echo $det[0]->nm_level;
			}

			generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
		}
		/*else if($action == 'get_parent')
		{
			$id 	= $this->input->post();
			$wh 	= array('status' => 'Y', 'jenis' => $id['id']);
			$r 		= $this->Ld_model->getRecordList('kategori','id,nm_kategori',$wh);
			$ls 	=  ConDataToArray($r,'id','nm_kategori');
			
			echo htmlSelectFromArray($ls, 'name="parent_id" class="form-control input-sm"', true);
				
		}*/
	}
}