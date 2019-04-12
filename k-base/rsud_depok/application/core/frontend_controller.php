<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class frontend_controller extends MY_Controller{
	
	function __construct()
	{
		parent::__construct();

		// Config template
		$this->site->side 			= 'frontend';
		$this->site->side_error 	= 'errors';
		
		$this->site->template 		= 'magz';
		$this->site->template_error = 'html';
	}

}