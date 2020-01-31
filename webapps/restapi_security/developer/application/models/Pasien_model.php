<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Pasien_model extends MY_Model{

	protected $_table_name = 'pasien';
	protected $_primary_key = 'no_rkm_medis';
	protected $_order_by = 'no_rkm_medis';
	protected $_order_by_type = 'ASC';
	protected $_database = 'default'; 

	public $rules = array(
		'name' => [
            'field' => 'name',
            'label' => 'Name',
            'rules' => 'trim|required'
		]
	);

	private $field = '
		pasien.*

	';

	/*private $tbjoin = [
		'detail_users' => [
			'metode' => 'LEFT',
			'relasi' => 'detail_users.id_users=users.id_user'
		]
	];*/

	public function __construct(){
		parent::__construct();
		$this->db2 = $this->load->database($this->_database,TRUE);
	}

	public function getData($where='',$limit='',$offset='')
	{
		return $this->get('',$this->field,$where,'','','',$limit,$offset)->result();
	}

}