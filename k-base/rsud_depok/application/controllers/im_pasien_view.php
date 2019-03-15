<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Ms_jabatan_view extends backend_controller {

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('Pasien_model'));
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
			$data = array(
				
			);

			$this->site->view('im_pasien');
		}
	}
}
