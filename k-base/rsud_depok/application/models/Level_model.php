<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Level_model extends MY_Model{

	protected $_table_name = 'level';
	protected $_primary_key = 'id_level';
	protected $_order_by = 'id_level';
	protected $_order_by_type = 'DESC';

	public $rules = array(
		'nm_level' => array(
            'field' => 'nm_level',
            'label' => 'Nama Level',
            'rules' => 'trim|required'
		)
	);

	public function __construct(){
		parent::__construct();
	}

}