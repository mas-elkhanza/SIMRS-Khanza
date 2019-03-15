<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class backend_controller extends MY_Controller{
	
	function __construct()
	{
		parent::__construct();

		// Config template
		$this->site->side 			= 'backend';
		$this->site->side_error 	= 'errors';
		
		$this->site->template 		= 'flatkit';
		$this->site->template_error = 'html';
	}

}