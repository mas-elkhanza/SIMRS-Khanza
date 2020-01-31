<?php defined('BASEPATH') OR exit('No direct script access allowed');

// require APPPATH . 'controllers/v1/Rest.php';
 
class Users extends CI_Controller {

	private $token;

	public function __construct($config = 'rest')
	{
		parent::__construct($config);
		// $this->load->model(['User_model']);
		// $this->cektoken();
	}


	/*
	| Get data
	*/

	public function test($user=''){

		echo sha1($user);
	}

}