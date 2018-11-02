<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Ms_user_view extends backend_controller {

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('User_model'));
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
				$where = 'nm_lengkap like \'%'.$f.'%\'';
			}

			$jum 	= $this->User_model->count($where);
			$r 		= $this->User_model->getRecordList('user','*',$where,'','nm_lengkap ASC',$l,$pq);
			$jab 	= $this->User_model->getRecord('jabatan','id,nm_jabatan','','','id ASC','','');
			$lev 	= $this->User_model->getRecord('level','id_level,nm_level','','','id_level ASC','','');
			$lsl 	=  ConDataToArray($lev,'id_level','nm_level');
			$lsj 	=  ConDataToArray($jab,'id','nm_jabatan');

			$data = array(
			'title'		=> 'Manajemen User',
			'f' 		=> $f,
			'l' 		=> $l,
			'p' 		=> $p,
			'pq' 		=> $pq,
			'no' 		=> $no,
			'jum' 		=> $jum,
			'r'			=> $r,
			'limit'		=> $SConfig->_limit,
			'level'		=> $lsl,
			'jabatan' 	=> $lsj,
			'v'			=> 'ms_user'
			);

			$this->site->view('v',$data);
		}
		else if($action  == 'save')
		{
			$post = $this->input->post();

			if (isset($_FILES['foto']['tmp_name']) && $_FILES['foto']['name']) {
            $foto = 'uploads/profilPicture/' . date('YmdHis') . '/';
	            if (!is_dir($foto)) {
	                mkdir($foto, 0777, true);
	            }
	            $foto .= $_FILES['foto']['name'];
	        } else {
	            $foto = '';
	        }

	        if ($foto) {
	            $data['foto'] = $foto;
	        }

	        if($post['password'])
	        {
	        	$data['password'] 	= bCrypt($post['password'],12);
	        }

	        if($post['id_jabatan'])
	        {
	        	$data['id_jabatan']	= htmlentities($post['id_jabatan'], ENT_QUOTES);
	        }

			$data['username'] 	= htmlentities($post['username'],ENT_QUOTES);
			$data['nik']		= htmlentities($post['nik'], ENT_QUOTES);
			$data['nm_lengkap']	= htmlentities($post['nm_lengkap'], ENT_QUOTES);
			$data['id_level']	= htmlentities($post['id_level'], ENT_QUOTES);
			$data['status']		= htmlentities($post['status'], ENT_QUOTES);
			$data['masuk_oleh']	= $this->session->userdata('ID');
			$data['masuk_tgl']	= date('Y-m-d H:i:s');
			/*print_r($data);
			exit();*/
			$rules = $this->User_model->rules_master;
			$this->form_validation->set_rules($rules);

			if($post['ID'])
			{
				if($this->form_validation->run() == FALSE)
				{
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully update, please try again later';
					$this->User_model->createHistory('Gagal update user dengan nama : '.$post['username'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully update';
					if ($foto) {
		                move_uploaded_file($_FILES['foto']['tmp_name'], $foto);
		            }
					$this->User_model->createHistory('Update user dengan nama : '.$post['username'].'',$this->session->userdata('ID'));
					$this->User_model->update($data,array('ID'=>$post['ID']));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
			else
			{
				if($this->form_validation->run() == FALSE)
				{
					
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully saved, please try again later';
					$this->User_model->createHistory('Gagal tambah user dengan nama : '.$post['username'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully saved';
					if ($foto) {
		                move_uploaded_file($_FILES['foto']['tmp_name'], $foto);
		            }
					$this->User_model->createHistory('Tambah user dengan nama : '.$post['username'].'',$this->session->userdata('ID'));
					$this->User_model->insert($data,FALSE);
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
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
				$det = $this->User_model->getRecordList('user','nm_lengkap','ID=\''.$idv.'\'');
				if(!$this->User_model->delete($idv))
				{
					$_SESSION['error'] = '1';
                	$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully deleted, please try again later';
				}
				else
				{
					$this->User_model->createHistory('Delete user dengan nama : '.$det[0]->nm_lengkap.'',$this->session->userdata('ID'),'ms_user_delete',1);
				}
			}
			generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
		}
	}

	public function log()
	{
		global $SConfig;
		$id 	= $this->uri->segment(3);
		$f 		= ($this->uri->segment(4))	? $this->uri->segment(4) : 'f';
		$l 		= ($this->uri->segment(5))	? $this->uri->segment(5) : 10;
		$p 		= ($this->uri->segment(6))	? $this->uri->segment(6) - 1 : 0;

		$pq 	= $p * $l;
		$no 	= ($p * $l) + 1;

		$where 	= '';
		if($f!='f')
		{
			$where .= ' id_user=\''.$id.'\' and ';
			$where .= ' date like \'%' . $f . '%\' or ip like \'%' . $f . '%\' or dns like \'%' . $f . '%\'';
		}
		else
		{
			$where .= ' id_user=\''.$id.'\'';
		}
		
		$jum 	= $this->User_model->countCustom('user_account_login_log',$where);
		$r = $this->User_model->getRecordList('user_account_login_log','*',$where,'','id DESC',$l,$pq);

		$data = array(
		'title'		=> 'Log User',
		'f' 		=> $f,
		'l' 		=> $l,
		'p' 		=> $p,
		'pq' 		=> $pq,
		'no' 		=> $no,
		'jum' 		=> $jum,
		'r'			=> $r,
		'limit'		=> $SConfig->_limit,
		'id'		=> $id
		);
		/*echo '<pre>';
		print_r($r);*/
		$this->site->view('ms_log',$data);
	}
}