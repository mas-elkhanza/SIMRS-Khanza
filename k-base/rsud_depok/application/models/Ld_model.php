<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Ld_model extends CI_Model{

	protected $_table_name = 'limit_pasien_online';
	protected $_primary_key = 'kd_dokter';
	protected $_order_by = 'kd_dokter';
	protected $_order_by_type = 'DESC';
	protected $_primary_filter = 'intval';
	private $db2;
	private $db1;

	public $rules = array(
		'limit_reg' => array(
            'field' => 'limit_reg',
            'label' => 'Limit Registrasi Online',
            'rules' => 'trim|required'
		)
	);

	public $cuti = array(
		'kd_dokter' => array(
            'field' => 'kd_dokter',
            'label' => 'Kode Dokter',
            'rules' => 'trim|required'
		)
	);

	public $bc = array(
		'kd_dokter' => array(
            'field' => 'kd_dokter',
            'label' => 'Kode Dokter',
            'rules' => 'trim|required'
		),
		'kd_poli' => array(
            'field' => 'kd_poli',
            'label' => 'Kode Poli',
            'rules' => 'trim|required'
		)
	);

	public $psn = array(
		'jawab' => array(
            'field' => 'jawab',
            'label' => 'Isi',
            'rules' => 'trim|required'
		)
	);

	public function __construct(){
		parent::__construct();
		$this->db2 = $this->load->database('db_dua', TRUE);
	}

	public function insert($table='',$data='',$batch=FALSE)
	{
		if($batch==TRUE)
		{
			$this->db2->insert($table,$data);
		}
		else
		{
			$this->db2->set($data);
			$this->db2->insert($table);
			$id = $this->db2->insert_id();
			return $id;
		}
	}

	/*
	| Wilayah Update
	*/

	public function update($table='',$data, $where=array()){
		$this->db2->set($data);
		$this->db2->where($where);
		$this->db2->update($table);
		return TRUE;
	}

	public function update_booking_registrasi($table='',$data='',$where1='',$where2='')
	{
		$this->db2->set($data);
		$this->db2->where($where1);
		$this->db2->where_in('DATE(tanggal_periksa)',$where2);
		$this->db2->update($table);
		return TRUE;
	}

	public function count($table='',$where=NULL)
	{
		if($where)
		{
			$this->db2->where($where);
		}
		$this->db2->from($table);
		return $this->db2->count_all_results();	
	}

	public function delete($table='',$where='',$id=''){
		$filter = $this->_primary_filter;
		$id = $filter($id);

		if(!$id){
			return FALSE;
		}

		$this->db2->where($where);
		$this->db2->limit(1);
		$this->db2->delete($table);
		return TRUE;
	}

	public function delete_by($table='',$where=NULL){
		if($where){
			$this->db2->where($where);
		}

		$this->db2->delete($table);
	}

	public function getRecordList($table, $field = '', $where = '', $groupBy = '', $orderBy = '', $limit = '', $offset = NULL)
	{

		if($field)
		{
			$this->db2->select($field);
		}

		if($where)
		{
			$this->db2->where($where);
		}

		if($groupBy)
		{
			$this->db2->group_by($groupBy);
		}

		if($orderBy)
		{
			$this->db2->order_by($orderBy);
		}

		if(($limit) && ($offset)){
			$this->db2->limit($limit,$offset);
		}
		else if($limit){
			$this->db2->limit($limit);
		}

		return $this->db2->get($table)->result();
	}

	public function createHistory($activity,$id) {
		$this->db1 = $this->load->database('default', TRUE);
        $data                  = array();
        $data['id_user']       = $id;
        $data['activity']      = $activity;
        $data['activity_date'] = date('Y-m-d H:i:s');

        $this->db1->set($data);
		$this->db1->insert('tbl_user_account_log');
		$id = $this->db1->insert_id();
		return $id;
    }

    public function get_join_poli($data='')
    {
    	$this->db2->select('a.kd_poli,b.nm_poli');
    	$this->db2->where($data);
    	$this->db2->group_by('kd_poli');
    	$this->db2->order_by('kd_poli','ASC');
    	$this->db2->from('jadwal as a');
		$this->db2->join('poliklinik as b','b.kd_poli=a.kd_poli');

		return $this->db2->get()->result();
    }

    public function get_join_dokter($data='')
    {
    	$this->db2->select('a.kd_dokter,a.nm_dokter');
    	$this->db2->where($data);
    	$this->db2->group_by('kd_dokter');
    	$this->db2->order_by('kd_dokter','ASC');
    	$this->db2->from('dokter as a');
		$this->db2->join('jadwal as b','b.kd_dokter=a.kd_dokter');

		return $this->db2->get()->result();
    }


    // Pasien Booking
    public function getPasienBooking($table, $field = '', $groupBy = '', $orderBy = '', $limit = '', $offset = NULL,$where1 = '',$where2 = '',$where3 = '',$where4 = '',$where5 = '',$where6 = '',$where7 = '')
	{

		if($field)
		{
			$this->db2->select($field);
		}

		if($where1)
		{
			$this->db2->where('kd_poli',$where1);
		}

		if($where2)
		{
			$this->db2->where('kd_dokter',$where2);
		}

		if(!empty($where3) && !empty($where4))
		{
			$this->db2->where('tanggal_periksa >= ', $where3);
			$this->db2->where('tanggal_periksa <= ', $where4);
		}

		if($where5)
		{
			$this->db2->where('no_rkm_medis',$where5);
		}

		if($where6)
		{
			$this->db2->where('status',$where6);
		}

		if($groupBy)
		{
			$this->db2->group_by($groupBy);
		}

		if($orderBy)
		{
			$this->db2->order_by($orderBy);
		}

		if(($limit) && ($offset)){
			$this->db2->limit($limit,$offset);
		}
		else if($limit){
			$this->db2->limit($limit);
		}

		return $this->db2->get($table)->result();
	}

	public function countPasienBooking($table='',$where1 = '',$where2 = '',$where3 = '',$where4 = '',$where5 = '',$where6 = '',$where7 = '')
	{
		if($where1)
		{
			$this->db2->where('kd_poli',$where1);
		}

		if($where2)
		{
			$this->db2->where('kd_dokter',$where2);
		}

		if(!empty($where3) && !empty($where4))
		{
			$this->db2->where('tanggal_periksa >= ', $where3);
			$this->db2->where('tanggal_periksa <= ', $where4);
		}

		if($where5)
		{
			$this->db2->where('no_rkm_medis',$where5);
		}

		if($where6)
		{
			$this->db2->where('status',$where6);
		}
		
		$this->db2->from($table);
		return $this->db2->count_all_results();	
	}

	public function get_jumlah_daftar($kd_dokter='',$kd_poli='',$tanggal='')
	{

		$tnow = date('Y');
		$dnow = date('m');

		if($kd_dokter)
		{
			$where = 'kd_dokter=\''.$kd_dokter.'\' and YEAR(tanggal)=\''.$tnow.'\' and MONTH(tanggal)=\''.$dnow.'\'';
		}else if($kd_poli)
		{
			$where = 'kd_poli=\''.$kd_poli.'\' and YEAR(tanggal)=\''.$tnow.'\' and MONTH(tanggal)=\''.$dnow.'\'';
		}else if($tanggal)
		{
			$where = 'YEAR(tanggal)=\''.$tanggal.'\' and MONTH(tanggal)=\''.$tanggal.'\'';
		}
		else if(!empty($kd_dokter) && !empty($kd_poli))
		{
			$where = 'kd_dokter=\''.$kd_dokter.'\' and kd_poli=\''.$kd_poli.'\' and YEAR(tanggal)=\''.$tnow.'\' and MONTH(tanggal)=\''.$dnow.'\'';
		}
		else if(!empty($kd_dokter) && !empty($kd_poli) && !empty($tanggal))
		{
			$where = 'kd_dokter=\''.$kd_dokter.'\' and kd_poli=\''.$kd_poli.'\' and YEAR(tanggal)=\''.$tanggal.'\' and MONTH(tanggal)=\''.$tanggal.'\'';
		}
		else
		{
			$where = 'YEAR(tanggal)=\''.$tnow.'\' and MONTH(tanggal)=\''.$dnow.'\'';
		}

		$query = $this->db2->query("SELECT DAY(tanggal) as hari, kategori ,count(jumlah) as jumlah 
				FROM report_pendaftaran_online
				WHERE $where
				GROUP BY tanggal, kategori");
		
		$res = $query->result();
		return $res;
	}

}