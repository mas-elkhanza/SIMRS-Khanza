<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Kat_model extends MY_Model{

	protected $_table_name = 'kategori';
	protected $_primary_key = 'id';
	protected $_order_by = 'id';
	protected $_order_by_type = 'DESC';

	public $rules = array(
		'nm_kategori' => array(
            'field' => 'nm_kategori',
            'label' => 'Nama Kategori',
            'rules' => 'trim|required'
		)
	);

	public function __construct(){
		parent::__construct();
	}

}