<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Rekapantreanbpjs_model extends MY_Model{

	protected $_table_name = 'reg_periksa';
	protected $_primary_key = 'no_rawat';
	protected $_order_by = '';
	protected $_order_by_type = 'ASC';
	protected $_database = 'default'; 

	/*public $rules = array(
		'username' => [
            'field' => 'username',
            'label' => 'Username',
            'rules' => 'trim|required'
		]
	);*/

	private $field = '
		reg_periksa.no_reg,
		reg_periksa.no_rkm_medis,
		reg_periksa.tgl_registrasi,
		reg_periksa.jam_reg,
		reg_periksa.kd_poli,
		reg_periksa.stts,
		poliklinik.nm_poli
	';

	private $tbjoin = [
		
		'poliklinik' => [
			'metode' => 'LEFT',
			'relasi' => 'reg_periksa.kd_poli=poliklinik.kd_poli'
		]
	];

	public function __construct(){
		parent::__construct();
		$this->db2 = $this->load->database($this->_database,TRUE);
	}

	/*public function getData($where=[]){
		if(is_array($where)){
			$wheres = $where;
			return $this->get('',$this->field,$where,'','','','');
		}	
	}*/

	public function getData($where='',$limit='',$offset='')
	{
		return $this->getJoin('',$this->tbjoin,$this->field,$where,'','','',$limit,$offset)->result();
	}

}