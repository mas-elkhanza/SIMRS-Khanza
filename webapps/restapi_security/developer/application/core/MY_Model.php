<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class MY_Model extends CI_Model {

	protected $_table_name;
	protected $_field = '*';
	protected $_primary_filter = 'intval';
	protected $_primary_key;
	protected $_group_by = '';
	protected $_having;
	protected $_order_by;
	protected $_order_by_type;
	protected $_rules;
	protected $_limit = 10;
	protected $_offset;
	private   $db2;
	protected $_database = 'default';

	public function __construct()
	{
		parent::__construct();
		$this->db2 = $this->load->database($this->_database,TRUE);
	}

	public function insert($table='',$data='',$batch=FALSE)
	{
		try {
			$this->db2->trans_begin();

			if($table)
			{
				$this->_table_name = $table;	
			}

			if($batch==TRUE)
			{
				$this->db2->insert_batch($this->_table_name,$data);
			}
			else
			{
				$this->db2->set($data);
				$this->db2->insert($this->_table_name);
			}
		
			$id = $this->db2->insert_id();
			$this->db2->trans_complete();

			if ($this->db2->trans_status() === FALSE)
			{
				return FALSE;
				$this->db2->trans_rollback();
			}
			else
			{
				return $id;
				$this->db2->trans_commit();
			}			
		} catch (Exception $e) {
			echo 'Error : '. $e->getMessage();
            die();
		}
	}

	/*
	| -------------------------------------------------------------------
	| PENGGUNAAN TABEL
	| -------------------------------------------------------------------
	| Fungsi Get untuk menampilkan data dari tabel database.
	|
	| -------------------------------------------------------------------
	| Penjelsaan Variable
	| -------------------------------------------------------------------
	| $table = parameter untuk penampung nama tabel database.
	| $field = parameter untuk penampung filed tabel database yang akan di tampilkan.
	| $where = parameter untuk membatasi data yang ditampilkan dengan kondisi 
	|          tertentu. Kondisi yang diterapakan sangat beragam seperti tanda sama 
	|		   dengan (=), adapun kondisi yang bisa dipakai diterapakan diantaranya :
	|          ------------------------------------------
	| 		   | OPERATOR    | PENJELASAN        		|
	|		   ------------------------------------------
	| 		   | =           | Sama dengan       		|
	| 		   | <> atau !=  | Tidak sama dengan 		|
	| 		   | <           | Lebih kecil       		|
	| 		   | <=          | Lebih kecil dari  		|
	| 		   | >           | Lebih besar       		|
	| 		   | >=          | Lebih besar dari  		|
	|
	|          ------------------------------------------
	| 		   | OPERATOR    | PENJELASAN        		|
	|		   ------------------------------------------
	| 		   | <=>         | Sama dengan (Null Safe)	|
	| 		   | IS NULL     | Cek data berisi NULL		|
	| 		   | IS NOT NULL | Tidak NULL        		|
	|          ------------------------------------------
	|		   ------------------------------------------
	| 		   | OPERATOR    | PENJELASAN        		|
	|		   ------------------------------------------
	| 		   | && / and    | dan						|
	| 		   | || / or     | atau						|
	| 		   | XOR         |         					|
	|          ------------------------------------------
	|
	| $group_by = parameter untuk membuat group pada data yang sama
	| $having = 
	| $order_by = parameter untuk menampung filed yang akan dijadikan acuan 
	|             pengurutan data baik dari yang terkecil atau dari yang terbesar.
	| $limit = parameter yang bisa digunakan untuk membatasi jumalh data yang 
	|          ditampilkan.
	| $offset = parameter yang digunakan untuk menampilkan data berdasarkan halaman.
	|
	| Contoh :
	| 1. Menampilkan data keseluruhan
	| 	A. - $this->Nama_model->get('nama_tabel','')->result();
	| 	B. - $this->Nama_model->get('')->result_array();
	| 2. Menampilkan data field tertentu
	| 	A. - $this->Nama_model->get('nama_tabel','field1,field2')->result();
	| 	B. - $this->Nama_model->get('','field1,field2')->result_array();
	| 3. Menampilkan data dengan kondisi/pengecualian
	| 	A. - $this->Nama_model->get('nama_tabel','',array('filed'=>'Kondisi'));
	| 	B. - $this->Nama_model->get('nama_tabel','field1',array('filed'=>'Kondisi'));
	*/
	public function get($table='',$field='',$where='',$group_by='',$having='',$order_by='',$limit='',$offset='')
	{
		try {
			$this->db2->trans_begin();

			if($table)
			{
				$this->_table_name = $table;
			}

			if($field)
			{
				$this->_field = $field;
			}

			if($where)
			{
				$this->db2->where($where);
			}

			if($having)
			{
				$this->_having = $having;
				$this->db2->having($this->_having);
			}

			if(!empty($group_by))
			{
				$this->_group_by = $group_by;
			}

			if($this->_order_by_type)
			{
				if($order_by)
				{
					$this->_order_by = $order_by;

				}
				$this->db2->order_by($this->_order_by,$this->_order_by_type);
			}
			else
			{
				if($order_by)
				{
					$this->_order_by = $order_by;
				}
				$this->db2->order_by($this->_order_by);
			}

			if(!empty($limit) && !empty($offset)){
				$this->db2->limit($limit,$offset);
			}
			else if(!empty($limit) && empty($offset))
			{
				$this->db2->limit($limit);
			}
			
			//echo $this->_offset;
			$this->db2->select($this->_field);
			$this->db2->group_by($this->_group_by);
			$res = $this->db2->get($this->_table_name);	

			$this->db2->trans_complete();

			if ($this->db2->trans_status() === FALSE)
			{
				return FALSE;
				$this->db2->trans_rollback();
			}
			else
			{
				return $res;
				$this->db2->trans_commit();
			}
		} catch (Exception $e) {
			echo 'Error : '. $e->getMessage();
            die();
		}
	}

	public function getJoin($table=NULL,$tbjoin='',$field='',$where='',$group_by='',$having='',$order_by='',$limit='',$offset='')
	{

		try {
			$this->db2->trans_begin();

			if($table)
			{
				$this->_table_name = $table;
			}

			if(is_array($tbjoin))
			{
				foreach ($tbjoin as $k => $v) {
					$this->db2->join($k,''.$v['relasi'].'',''.$v['metode'].'');
				}
			}

			if($field)
			{
				$this->_field = $field;
			}

			if($where)
			{
				$this->db2->where($where);
			}

			if($having)
			{
				$this->_having = $having;
				$this->db2->having($this->_having);
			}

			if($group_by)
			{
				$this->_group_by = $group_by;
			}

			if($this->_order_by_type)
			{
				if($order_by)
				{
					$this->_order_by = $order_by;

				}
				$this->db2->order_by($this->_order_by,$this->_order_by_type);
			}
			else
			{
				if($order_by)
				{
					$this->_order_by = $order_by;
				}
				$this->db2->order_by($this->_order_by);
			}

			if(($limit) && ($offset)){
				$this->db2->limit($limit,$offset);
			}
			else if($limit)
			{
				$this->db2->limit($limit);
			}
			
			//echo $this->_offset;
			$this->db2->select($this->_field);
			$this->db2->group_by($this->_group_by);
			$this->db2->from($this->_table_name);
			$res = $this->db2->get();
			$this->db2->trans_complete();

			if ($this->db2->trans_status() === FALSE)
			{
				return FALSE;
				$this->db2->trans_rollback();
			}
			else
			{
				return $res;
				$this->db2->trans_commit();
			}
		} catch (Exception $e) {
			echo 'Error : '. $e->getMessage();
            die();
		}
		
	}

	public function getQuery($query='')
	{
		try {
			$this->db2->trans_begin();
			if($query)
			{
				$query = $this->db2->query($query);
			}

			$res = $query->result();
			$this->db2->trans_complete();

			if ($this->db2->trans_status() === FALSE)
			{
				return FALSE;
				$this->db2->trans_rollback();
			}
			else
			{
				return $res;
				$this->db2->trans_commit();
			}

		} catch (Exception $e) {
			echo 'Error : '. $e->getMessage();
            die();
		}
		
	}

	public function count($table='',$where='')
	{
		try {
			$this->db2->trans_begin();
			if($table)
			{
				$this->_table_name = $table;
			}

			if($where)
			{
				$this->db2->where($where);
			}

			$this->db2->from($this->_table_name);
			$res = $this->db2->count_all_results();
			$this->db2->trans_complete();

			if ($this->db2->trans_status() === FALSE)
			{
				return FALSE;
				$this->db2->trans_rollback();
			}
			else
			{
				return $res;
				$this->db2->trans_commit();
			}

		} catch (Exception $e) {
			echo 'Error : '. $e->getMessage();
            die();
		}
	}

	public function countJoin($table=NULL,$tbjoin='',$where='')
	{

		try {
			$this->db2->trans_begin();

			if($table)
			{
				$this->_table_name = $table;
			}

			if(is_array($tbjoin))
			{
				foreach ($tbjoin as $k => $v) {
					$this->db2->join($k,''.$v['relasi'].'',''.$v['metode'].'');
				}
			}

			if($where)
			{
				$this->db2->where($where);
			}

			$this->db2->from($this->_table_name);
			$res = $this->db2->count_all_results();
			$this->db2->trans_complete();

			if ($this->db2->trans_status() === FALSE)
			{
				return FALSE;
				$this->db2->trans_rollback();
			}
			else
			{
				return $res;
				$this->db2->trans_commit();
			}
		} catch (Exception $e) {
			echo 'Error : '. $e->getMessage();
            die();
		}
		
	}

	public function update($table='',$data='',$where='',$order_by='',$limit='',$batch=FALSE)
	{
		try {
			$this->db2->trans_begin();

			if($table)
			{
				$this->_table_name = $table;
			}

			if($this->_order_by_type)
			{
				if($order_by)
				{
					$this->_order_by = $order_by;

				}
				$this->db2->order_by($this->_order_by,$this->_order_by_type);
			}
			else
			{
				if($order_by)
				{
					$this->_order_by = $order_by;
				}
				$this->db2->order_by($this->_order_by);
			}

			if($limit)
			{
				$this->db2->limit($limit);
			}

			if($batch == TRUE)
			{
				$this->db2->update_batch($this->_table_name,$data,$where);
			}
			else
			{
				$this->db2->set($data);
				$this->db2->where($where);
				$this->db2->update($this->_table_name);
			}

			$this->db2->trans_complete();

			if ($this->db2->trans_status() === FALSE)
			{
				return FALSE;
				$this->db2->trans_rollback();
			}
			else
			{
				return TRUE;
				$this->db2->trans_commit();
			}

		} catch (Exception $e) {
			echo 'Error : '. $e->getMessage();
		}
		
	}

	public function delete($table='',$where='')
	{
		try {
			$this->db2->trans_begin();
			if($table)
			{
				$this->_table_name = $table;
			}

			if($where)
			{
				$this->db2->where($where);
			}

			$this->db2->delete($this->_table_name);

			$this->db2->trans_complete();

			if ($this->db2->trans_status() === FALSE)
			{
				return FALSE;
				$this->db2->trans_rollback();
			}
			else
			{
				return TRUE;
				$this->db2->trans_commit();
			}

		} catch (Exception $e) {
			echo 'Error : '. $e->getMessage();
            die();
		}
	}

	public function deleteReset($table='')
	{
		try {
			$this->db2->trans_begin();
			if($table)
			{
				$this->_table_name = $table;
			}
			$this->db2->truncate($this->_table_name);
			$this->db2->trans_complete();

			if ($this->db2->trans_status() === FALSE)
			{
				return FALSE;
				$this->db2->trans_rollback();
			}
			else
			{
				return TRUE;
				$this->db2->trans_commit();
			}
		} catch (Exception $e) {
			echo 'Error : '. $e->getMessage();
            die();
		}
	}
}