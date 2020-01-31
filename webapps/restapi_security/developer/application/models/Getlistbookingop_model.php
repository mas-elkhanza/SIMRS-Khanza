<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Getlistbookingop_model extends MY_Model{

	protected $_table_name = 'booking_operasi';
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
		booking_operasi.no_rawat,
		booking_operasi.tanggal,
		booking_operasi.status,
		paket_operasi.nm_perawatan,
		reg_periksa.kd_poli,
		poliklinik.nm_poli,
		pasien.no_peserta
	
	';

	private $tbjoin = [
	
		'reg_periksa' => [
			'metode' => 'LEFT',
			'relasi' => 'reg_periksa.no_rawat=booking_operasi.no_rawat'
		],

		'pasien' => [
			'metode' => 'LEFT',
			'relasi' => 'pasien.no_rkm_medis=reg_periksa.no_rkm_medis'
		],


		'poliklinik' => [
			'metode' => 'LEFT',
			'relasi' => 'reg_periksa.kd_poli=poliklinik.kd_poli'
		],

		'paket_operasi' => [
			'metode' => 'LEFT',
			'relasi' => 'booking_operasi.kode_paket=paket_operasi.kode_paket'
		]
	];

	public function __construct(){
		parent::__construct();
		$this->db2 = $this->load->database($this->_database,TRUE);
	}

	/*public function getData($where=[]){
		if(is_array($where)){
			$wheres = $where;
			return $this->get('',$this->field,$where,'','','',1);
		}	
	}*/

	public function getData($where='',$limit='',$offset='')
	{
		return $this->getJoin('',$this->tbjoin,$this->field,$where,'','','',$limit,$offset)->result();
	}

}