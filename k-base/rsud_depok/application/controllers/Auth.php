<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Auth extends backend_controller {

	protected $user_detail;

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('User_model','Level_model'));
	}

	public function index()
	{
		$data = array(
			'head' => 'Hallo',
			'body' => 'world',
			'foot' => 'copy'
		);
		
		if($this->session->userdata('logged_in')==TRUE && $this->session->userdata('status')=='Y')
		{
			$this->User_model->createHistory('Akun ini terpaksa di keluarkan :'.$this->session->userdata('nm_lengkap').'',$this->session->userdata('ID'));
			$this->session->sess_destroy();
			redirect(base_url('Auth'));
		}
		else
		{
			$this->site->view('login');
		}
	}

	public function action($action=NULL)
	{
		if($action == 'checking')
		{
			$post = $this->input->post();
			
			if(isset($post['username'])){
			$this->user_detail = $this->User_model->get_by(
					array('username' => $post['username']) , 1, NULL,TRUE
				);
			}
			
			$this->form_validation->set_message('required', '%s kosong, tolong diisi');

			$rules = $this->User_model->rules;

			$this->form_validation->set_rules($rules);

			if($this->form_validation->run() == FALSE)
			{
				$this->User_model->createHistory('Akun ini gagal login :'.$this->user_detail->nm_lengkap.'',$this->user_detail->ID);

				$_SESSION['error'] = '1';
				$_SESSION['msg']   = '';

				redirect(set_url('Auth'));
			}
			else
			{
				// Ambil data akses level, untuk pengolahan menu yang akan di munculkan
				$get_data = $this->Level_model->get_by(array('id_level' => $this->user_detail->id_level) , 1, NULL,TRUE);
				
				// Update data ke dalam user,
				$w = array('ID' => $this->user_detail->ID);
				$d = array('online' => 'Y');
				$this->User_model->update($d,$w);

				$dataLog             = array();
			    $dataLog['id_user']  = $this->user_detail->ID;
			    $dataLog['ua']       = $_SERVER['HTTP_USER_AGENT'];
			    $dataLog['ip']       = $_SERVER['REMOTE_ADDR'];
			    $dataLog['dns']      = gethostbyaddr($_SERVER['REMOTE_ADDR']);
			    $dataLog['date'] = date('Y-m-d H:i:s');
			    $this->User_model->insertRecord('tbl_user_account_login_log',$dataLog,FALSE);

				$login_data = array(
					'ID' => $this->user_detail->ID,
					'username' => $post['username'],
					'nm_lengkap' => $this->user_detail->nm_lengkap,
					'logged_in' => TRUE,
					'status' =>  $this->user_detail->status,
					'nm_level' => $get_data->nm_level,
					'id_level' => $this->user_detail->id_level,
					'akses' => $get_data->akses,
					'id_jabatan' => $this->user_detail->id_jabatan,
					'email' => $this->user_detail->email,
					'online' => $this->user_detail->online,
					'terakhir_login' => $this->user_detail->terakhir_login
				);
				
				$this->session->set_userdata($login_data);

				$this->User_model->createHistory('Akun ini berhasil login :'.$this->session->userdata('nm_lengkap').'',$this->session->userdata('ID'));

				if(isset($post['remember'])){
					$expire = time() + (86400 * 7);
					set_cookie('username', $post['username'], $expire, "/");
					set_cookie('password', $post['password'], $expire, "/");
					set_cookie('id_user',$this->user_detail->ID, $expire, "/");
				}
				$_SESSION['error'] = '';
				$_SESSION['msg']   = 'Selamat datang';
				
				redirect(set_url('dashboard'));
			}
		}

		else if($action == 'logout')
		{
			$this->User_model->createHistory('Akun ini berhasil logout :'.$this->session->userdata('nm_lengkap').'',$this->session->userdata('ID'));

			$w = array('ID' => $this->session->userdata('ID'));
			$d = array('online' => 'T', 'terakhir_login' => date('Y-m-d H:i:s'));
			$this->User_model->update($d,$w);
			$this->session->sess_destroy();
			redirect(set_url('Auth'));
		}
	}

	public function password_check($str){
    	$user_detail =  $this->user_detail;  
    	//echo $user_detail->password;	
    	if (@$user_detail->password == crypt($str,@$user_detail->password)){
			return TRUE;
		}
		else if(@$user_detail->password){
			$this->form_validation->set_message('password_check', 'Passwordnya Anda salah...');
			return FALSE;
		}
		else{
			$this->form_validation->set_message('password_check', 'Anda tidak punya akses Admin...');
			return FALSE;	
		}		
	}
}
