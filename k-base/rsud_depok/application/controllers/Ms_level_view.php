<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Ms_level_view extends backend_controller {

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('Level_model'));
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
			$u3 	= $this->uri->segment(4);
			$f 		= ($this->uri->segment(4))	? $this->uri->segment(4) : 'f';
			$l 		= ($this->uri->segment(5))	? $this->uri->segment(5) : 10;
			$p 		= ($this->uri->segment(6))	? $this->uri->segment(6) - 1 : 0;
			$in 	= inisial();
		
			$pq 	= $p * $l;
			$no 	= ($p * $l) + 1;

			$where 	= '';
			if($f!='f')
			{
				$where = 'nm_level like \'%'.$f.'%\' ';
			}

			$jum 	= $this->Level_model->count($where);
			$r 		= $this->Level_model->getRecordList('level','*',$where,'','nm_level ASC',$l,$pq);

			$data = array(
			'title'		=> 'Manajemen Level',
			'f' 		=> $f,
			'l' 		=> $l,
			'p' 		=> $p,
			'pq' 		=> $pq,
			'no' 		=> $no,
			'jum' 		=> $jum,
			'r'			=> $r,
			'limit'		=> $SConfig->_limit,
			'in'		=> $in,
			'v'			=> 'ms_level'
			);
			//print_r($r);
			$this->site->view('v',$data);
		}

		else if($action == 'save')
		{
			$post = $this->input->post();
			
			$links = menu_link();
			$ins = inisial();
			$mar = array();
			$aks = array();
			
			foreach ($post['akses'] as $key => $value) {
				foreach ($value as $key1 => $value1) {
					 if(!empty(@$links[$key1]))
					 {
					 	$mar[$ins[$key]][$key1] = $links[$key1];
					 }
					 $aks[$key1] = $key1;
				}
			}

			$mss = serialize($mar);
			//$uns = unserialize($tss);
			$data['nm_level']  	= htmlentities($_POST['nm_level'], ENT_QUOTES);
			$data['akses']		= implode('|', array_keys($aks));
			$data['menu']		= $mss;
			echo '<pre>';
			print_r($post['akses']);

			//exit();
			$rules = $this->Level_model->rules;
			$this->form_validation->set_rules($rules);

			if($post['id_level'])
			{
				if($this->form_validation->run() == FALSE)
				{
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully update, please try again later';
					$this->Level_model->createHistory('Gagal update level dengan nama : '.$post['nm_level'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully update';
					$this->Level_model->createHistory('Update level dengan nama : '.$post['nm_level'].'',$this->session->userdata('ID'));
					$this->Level_model->update($data,array('id_level'=>$post['id_level']));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
			else
			{
				if($this->form_validation->run() == FALSE)
				{
					
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully saved, please try again later';
					$this->Level_model->createHistory('Gagal tambah level dengan nama : '.$post['nm_level'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully saved';
					$this->Level_model->createHistory('Tambah level dengan nama : '.$post['nm_level'].'',$this->session->userdata('ID'));
					$this->Level_model->insert($data,FALSE);
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
				$det = $this->Level_model->getRecordList('level','nm_level','id_level=\''.$idv.'\'');
				if(!$this->Level_model->delete($idv))
				{
					$_SESSION['error'] = '1';
                	$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully deleted, please try again later';
				}
				else
				{
					$this->Level_model->createHistory('Delete level dengan nama : '.$det[0]->nm_level.'',$this->session->userdata('ID'));
				}
				//echo $det[0]->nm_level;
			}

			generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
		}
	}

}