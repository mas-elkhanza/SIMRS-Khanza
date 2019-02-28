<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Jabatan_model extends MY_Model{

	protected $_table_name = 'jabatan';
	protected $_primary_key = 'id';
	protected $_order_by = 'id';
	protected $_order_by_type = 'DESC';

	public $rules = array(
		'nm_jabatan' => array(
            'field' => 'nm_jabatan',
            'label' => 'Nama Jabatan',
            'rules' => 'trim|required'
		)
	);

	public function __construct(){
		parent::__construct();
	}

}