<?php 
defined('BASEPATH') OR exit('No direct script accsess allowed');

class MY_Model extends CI_Model{

	protected $_table_name;
	protected $_order_by;
	protected $_order_by_type;
	protected $_primary_filter = 'intval';
	protected $_primary_key;
	protected $_type;
	protected $_rules;

	function __construct(){
		parent::__construct();
	}

	public function insert($data,$batch=FALSE)
	{
		if($batch==TRUE)
		{
			$this->db->insert('{PRE}'.$this->_table_name,$data);
		}
		else
		{
			$this->db->set($data);
			$this->db->insert('{PRE}'.$this->_table_name);
			$id = $this->db->insert_id();
			return $id;
		}
	}

	public function update($data, $where=array()){
		$this->db->set($data);
		$this->db->where($where);
		$this->db->update('{PRE}'.$this->_table_name);
		return TRUE;
	}

	public function get($id=NULL,$single=FALSE){
		if($id != NULL){
			$filter = $this->_primary_filter;
			$id = $filter($id);
			$this->db->where($this->_primary_key,$id);
			$method = 'row';
		}

		else if($single == TRUE){
			$method = 'row';
		}

		else{
			$method = 'result';
		}

		if($this->_order_by_type){
			$this->db->order_by($this->_order_by,$this->_order_by_type);
			// $this->db->order_by('ID','DESC');
		}
		else{
			$this->db->order_by($this->_order_by);
		}

		return $this->db->get('{PRE}'.$this->_table_name)->$method();
		
	}
	
	public function get_by($where = NULL, $limit = NULL, $offset = NULL, $single = NULL, $select = NULL){
		if($select != NULL){
			$this->db->select($select);
		}

		if($where){
			$this->db->where($where);
		}

		if(($limit) && ($offset)){
			$this->db->limit($limit,$offset);
		}
		else if($limit){
			$this->db->limit($limit);
		}

		return $this->get(NULL,$single);
	}

	public function delete($id){
		$filter = $this->_primary_filter;
		$id = $filter($id);

		if(!$id){
			return FALSE;
		}

		$this->db->where($this->_primary_key,$id);
		$this->db->limit(1);
		$this->db->delete($this->_table_name);
		return TRUE;
	}

	public function delete_by($where=NULL){
		if($where){
			$this->db->where($where);
		}

		$this->db->delete($this->_table_name);
	}

	public function count($where=NULL)
	{
		if($where)
		{
			$this->db->where($where);
		}
		$this->db->from('{PRE}'.$this->_table_name);
		return $this->db->count_all_results();	
	}

	public function countCustom($table='',$where=NULL)
	{
		if($where)
		{
			$this->db->where($where);
		}

		$this->db->from('tbl_'.$table);
		return $this->db->count_all_results();	
	}

	public function insertRecord($table,$data,$debug=FALSE)
	{
		if ($debug) {
            $field = implode(',', array_keys($data));
            $value = '\'' . implode('\',\'', $data) . '\'';

            return 'INSERT INTO ' . $table . '(' . $field . ') VALUES(' . $value . ')';
        }

        $this->db->set($data);
        $this->db->insert($table);
        $id = $this->db->insert_id();
		return $id;
	}

	public function getRecordList($table, $field = '', $where = '', $groupBy = '', $orderBy = '', $limit = '', $offset = NULL)
	{

		if($field)
		{
			$this->db->select($field);
		}

		if($where)
		{
			$this->db->where($where);
		}

		if($groupBy)
		{
			$this->db->group_by($groupBy);
		}

		if($orderBy)
		{
			$this->db->order_by($orderBy);
		}

		if(($limit) && ($offset)){
			$this->db->limit($limit,$offset);
		}
		else if($limit){
			$this->db->limit($limit);
		}

		return $this->db->get('tbl_'.$table)->result();
	}

	public function getRecord($table, $field = '', $where = '', $groupBy = '', $orderBy = '', $limit = '', $offset = NULL)
	{

		if($field)
		{
			$this->db->select($field);
		}

		if($where)
		{
			$this->db->where($where);
		}

		if($groupBy)
		{
			$this->db->group_by($groupBy);
		}

		if($orderBy)
		{
			$this->db->order_by($orderBy);
		}

		if(($limit) && ($offset)){
			$this->db->limit($limit,$offset);
		}
		else if($limit){
			$this->db->limit($limit);
		}

		$res = $this->db->get('tbl_'.$table)->result();
		return $res;
	}


	public function createHistory($activity,$id) {
        $data                  = array();
        $data['id_user']       = $id;
        $data['activity']      = $activity;
        $data['activity_date'] = date('Y-m-d H:i:s');

        $this->db->set($data);
		$this->db->insert('tbl_user_account_log');
		$id = $this->db->insert_id();
		return $id;
    }

    function isStringExist($string, $stringAll) {
        if (!empty($stringAll) && !empty($string)) {
            if (strpos($stringAll, $string) !== false) {
                //echo $string.' ..';
                return true;
            } else {
                //echo $string;
                return false;
            }
        } else {
            //echo $string;
            return false;
        }
    }
	
}