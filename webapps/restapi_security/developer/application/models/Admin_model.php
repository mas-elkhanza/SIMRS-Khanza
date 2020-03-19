<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Admin_model extends MY_Model{

	protected $_table_name = 'password_asuransi';
	protected $_primary_key = 'usere';
	protected $_order_by = '';
	protected $_order_by_type = 'ASC';
	protected $_database = 'default'; 

	/*public $rules = array(
		'usere' => [
            'field' => 'usere',
            'label' => 'Usere',
            'rules' => 'trim|required'
		]
	);
*/
	private $field = '
		usere,
		passworde
	';

	/*private $tbjoin = [
		'detail_users' => [
			'metode' => 'LEFT',
			'relasi' => 'detail_users.id_users=users.id_user'
		]
	];
*/
	public function __construct(){
		parent::__construct();
		//$this->db2 = $this->load->database($this->_database,TRUE);
	}

	public function getData($where=[]){
		if(is_array($where)){
			$wheres = $where;
			return $this->get('',$this->field,$where,'','','',1);
		}	
	}

	/*public function getData($where='',$limit='',$offset='')
	{
		return $this->getJoin('',$this->tbjoin,$this->field,$where,'','','',$limit,$offset)->result();
	}*/

}