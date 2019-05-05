<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Publik extends frontend_controller {

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('Post_model'));
	}

	public function index()
	{
		$data = array(
			'title' => 'Bihar-Tech'
		);
		//echo akses_link();
		$this->site->view('index',$data);
	}

	public function get_judul_autoc()
	{
		$where = 'judul like \'%'.strval($_POST['query']).'%\'';
	  	$result = $this->Post_model->getRecordList('post','judul',$where,'','id DESC',10,'');
	   	if (count($result) > 0) {
		    foreach ($result as $k => $row)
		    {
		    	$arr_result[] = $row->judul;
		    }
	     	echo json_encode($arr_result);
	   	}
	}


}