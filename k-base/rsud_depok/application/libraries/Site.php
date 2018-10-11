<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Site{

	public $side;
	public $template;
	public $template_setting;
	public $website_setting;
	public $_isHome = FALSE;
	public $_isCategory = FALSE;
	public $_isSearch = FALSE;
	public $_isDetail = FALSE;

	function view($pages,$data=NULL)
	{
		$_this =& get_instance();

		$data ?
		$_this->load->view($this->side.'/'.$this->template.'/'.$pages,$data)
			:
				$_this->load->view($this->side.'/'.$this->template.'/'.$pages);
	}

	function view_error($pages,$data = NULL){
		$_this =& get_instance();

		$data ? 
		$_this->load->view($this->side_error.'/'.$this->template_error.'/'.$pages,$data)
			:
				$_this->load->view($this->side_error.'/'.$this->template_error.'/'.$pages);
	}

	function is_logged_in(){
		$_this =& get_instance();

		$user_session = $_this->session->userdata;
		
		if($this->side == 'backend')
		{
			if($_this->session->userdata('logged_in')!=TRUE && $_this->session->userdata('id_level')=='' && $_this->session->userdata('status')!='Y')
			{
				$url = base_url('Auth');
				redirect($url);
			}
		}
		else
		{
			$url = base_url('Auth');
			redirect($url);
		}
	}

}