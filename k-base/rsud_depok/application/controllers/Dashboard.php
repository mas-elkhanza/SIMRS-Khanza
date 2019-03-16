<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Dashboard extends backend_controller {

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('Dashboard_model'));
		$this->site->is_logged_in();
	}

	public function index()
	{
		$Aclist = $this->Dashboard_model->getRecordList('user_account_log','*',array('id_user'=>$this->session->userdata('ID')),'','activity_date DESC','25','0');
		$data = array(
			'title' => 'Dashboard',
			'AcList' => $Aclist
		);
		
		$this->site->view('index',$data);
	}


}