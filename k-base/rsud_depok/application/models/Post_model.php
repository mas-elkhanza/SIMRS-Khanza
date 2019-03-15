<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Post_model extends MY_Model{

	protected $_table_name = 'post';
	protected $_primary_key = 'id';
	protected $_order_by = 'id';
	protected $_order_by_type = 'DESC';

	public $rules = array(
		'judul' => array(
            'field' => 'judul',
            'label' => 'Judul',
            'rules' => 'trim|required'
		),
		'konten' => array(
            'field' => 'konten',
            'label' => 'konten',
            'rules' => 'trim|required'
		)
	);

	public function __construct(){
		parent::__construct();
	}

}