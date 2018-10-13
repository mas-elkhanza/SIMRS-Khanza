<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class MY_Controller extends CI_Controller{

	function __construct(){
		parent::__construct();
		$this->load->library(array('session','Site','form_validation'));
		$this->load->helper(array('template_helper','user_helper','security','cookie'));
	}

}