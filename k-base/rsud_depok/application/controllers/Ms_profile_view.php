<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Ms_profile_view extends backend_controller {

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('Profile_model'));
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

	public function action($value='')
	{
		if($value == 'view')
		{
			$wh = array('online'=>'Y', 'ID !='=>$this->session->userdata('ID'));
			$jum = $this->Profile_model->count($wh);
			$uo = $this->Profile_model->get_by($wh,'','','','ID,nm_lengkap,id_level,id_jabatan,foto');

			$data = array(
				'title' => 'Profile',
				'r'		=> $uo,
				'jum'	=> $jum	,
				'v'		=> 'ms_profile'
			);
			//print_r($jum);
			$this->site->view('v',$data);
		}
	}

}