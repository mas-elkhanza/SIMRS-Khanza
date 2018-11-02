<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Tag_model extends MY_Model{

	protected $_table_name = 'tag';
	protected $_primary_key = 'id';
	protected $_order_by = 'id';
	protected $_order_by_type = 'DESC';

	public $rules = array(
		'nm_tag' => array(
            'field' => 'nm_tag',
            'label' => 'Nama Tag',
            'rules' => 'trim|required'
		)
	);

	public function __construct(){
		parent::__construct();
	}

}