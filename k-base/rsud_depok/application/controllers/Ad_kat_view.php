<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Ad_kat_view extends backend_controller {

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('Kat_model'));
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
				$where = 'nm_kategori like \'%'.$f.'%\'';
			}

			$jum 	= $this->Kat_model->count($where);
			$r 		= $this->Kat_model->getRecordList('kategori','*',$where,'','id ASC',$l,$pq);

			$data = array(
			'title'		=> 'Administrasi Kategori',
			'f' 		=> $f,
			'l' 		=> $l,
			'p' 		=> $p,
			'pq' 		=> $pq,
			'no' 		=> $no,
			'jum' 		=> $jum,
			'r'			=> $r,
			'limit'		=> $SConfig->_limit,
			'jenis'		=> $SConfig->_jenis,
			'status'	=> $SConfig->_status,
			'v'			=> 'ad_kat'
			);

			//print_r($r);
			$this->site->view('v',$data);
		}

		else if($action == 'save')
		{
			$post = $this->input->post();
			$data['nm_kategori'] 	= htmlentities($post['nm_kategori'],ENT_QUOTES);
			$data['jenis'] 	= htmlentities($post['jenis'],ENT_QUOTES);
			$data['status'] 	= htmlentities($post['status'],ENT_QUOTES);
			$data['masuk_oleh'] 	= $this->session->userdata('ID');
			$data['masuk_tgl'] 		= date('Y-m-d');

			$rules = $this->Kat_model->rules;
			$this->form_validation->set_rules($rules);

			if(!empty($post['parent_id']))
			{
				$data['parent_id'] = $post['parent_id']; 
			}
			else
			{
				$data['parent_id'] = 0;
			}

			/*print_r($data);
			exit();*/
			if($post['id'])
			{
				if($this->form_validation->run() == FALSE)
				{
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully update, please try again later';
					$this->Kat_model->createHistory('Gagal update nama kategori dengan nama : '.$post['nm_kategori'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully update';
					$this->Kat_model->createHistory('Update nama kategori dengan nama : '.$post['nm_kategori'].'',$this->session->userdata('ID'));
					$this->Kat_model->update($data,array('id'=>$post['id']));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
			else
			{
				if($this->form_validation->run() == FALSE)
				{
					
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully saved, please try again later';
					$this->Kat_model->createHistory('Gagal tambah nama kategori dengan nama : '.$post['nm_kategori'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully saved';
					
					$this->Kat_model->createHistory('Tambah nama kategori dengan nama : '.$post['nm_kategori'].'',$this->session->userdata('ID'));
					$this->Kat_model->insert($data,FALSE);
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
				$det = $this->Kat_model->getRecordList('kategori','nm_kategori','id=\''.$idv.'\'');
				if(!$this->Kat_model->delete($idv))
				{
					$_SESSION['error'] = '1';
                	$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully deleted, please try again later';
				}
				else
				{
					$this->Kat_model->createHistory('Delete kategori dengan nama : '.$det[0]->nm_kategori.'',$this->session->userdata('ID'));
				}
				//echo $det[0]->nm_level;
			}

			generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
		}
		else if($action == 'get_parent')
		{
			$id 	= $this->input->post();
			$wh 	= array('status' => 'Y', 'jenis' => $id['id']);
			$r 		= $this->Kat_model->getRecordList('kategori','id,nm_kategori',$wh);
			$ls 	=  ConDataToArray($r,'id','nm_kategori');
			
			echo htmlSelectFromArray($ls, 'name="parent_id" class="form-control input-sm"', true);
				
		}
	}
}