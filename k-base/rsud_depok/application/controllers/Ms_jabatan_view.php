<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Ms_jabatan_view extends backend_controller {

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('Jabatan_model'));
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
				$where = 'nm_jabatan like \'%'.$f.'%\'';
			}

			$jum 	= $this->Jabatan_model->count($where);
			$r 		= $this->Jabatan_model->getRecordList('jabatan','*',$where,'','id ASC',$l,$pq);

			$data = array(
			'title'		=> 'Manajemen Jabatan',
			'f' 		=> $f,
			'l' 		=> $l,
			'p' 		=> $p,
			'pq' 		=> $pq,
			'no' 		=> $no,
			'jum' 		=> $jum,
			'r'			=> $r,
			'limit'		=> $SConfig->_limit,
			'v'			=> 'ms_jabatan'
			);

			//print_r($r);
			$this->site->view('v',$data);
		}

		else if($action == 'save')
		{
			$post = $this->input->post();
			$data['nm_jabatan'] 	= htmlentities($post['nm_jabatan'],ENT_QUOTES);

			$rules = $this->Jabatan_model->rules;
			$this->form_validation->set_rules($rules);

			if($post['id'])
			{
				if($this->form_validation->run() == FALSE)
				{
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully update, please try again later';
					$this->Jabatan_model->createHistory('Gagal update jabatan dengan nama : '.$post['nm_jabatan'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully update';
					$this->Jabatan_model->createHistory('Update jabatan dengan nama : '.$post['nm_jabatan'].'',$this->session->userdata('ID'));
					$this->Jabatan_model->update($data,array('id'=>$post['id']));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
			else
			{
				if($this->form_validation->run() == FALSE)
				{
					
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully saved, please try again later';
					$this->Jabatan_model->createHistory('Gagal tambah jabatan dengan nama : '.$post['nm_jabatan'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully saved';
					
					$this->Jabatan_model->createHistory('Tambah jabatan dengan nama : '.$post['nm_jabatan'].'',$this->session->userdata('ID'));
					$this->Jabatan_model->insert($data,FALSE);
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
			//print_r($post);
		}
		else if($action == 'delete')
		{
			$uri = $this->uri->segment(4);
			$idl = explode('k', $uri);
			/*print_r($idl);
			exit();*/

			$_SESSION['error'] = '';
        	$_SESSION['msg']   = 'Data has been succesfully deleted';

			foreach ($idl as $idv)
			{
				$det = $this->Jabatan_model->getRecordList('jabatan','nm_jabatan','id=\''.$idv.'\'');
				if(!$this->Jabatan_model->delete($idv))
				{
					$_SESSION['error'] = '1';
                	$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully deleted, please try again later';
				}
				else
				{
					$this->Jabatan_model->createHistory('Delete jabatan dengan nama : '.$det[0]->nm_jabatan.'',$this->session->userdata('ID'));
				}
				//echo $det[0]->nm_level;
			}

			generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
		}
	}
}