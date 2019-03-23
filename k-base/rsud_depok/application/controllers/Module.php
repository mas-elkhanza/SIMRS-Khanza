<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Module extends backend_controller {

	public function __construct()
	{
		parent::__construct();		
		//$this->load->model(array('Dashboard_model'));
		$this->site->is_logged_in();
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
			$data = array(
			'title' => 'Module'
			//'AcList' => $Aclist
		);

		$this->site->view('ms_module',$data);
		}
	}

}