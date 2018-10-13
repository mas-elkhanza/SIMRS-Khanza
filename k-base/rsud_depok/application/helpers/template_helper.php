<?php  defined('BASEPATH') OR exit('No direct script access allowed');

/*
|-------------------------------------------------------------
|Tata Cara Menambahkan Module Induk
| 1. Buat nama inisial di bagian fungsi inisial.
| 2. Tambah array Icon pada fungsi akses_link.
|-------------------------------------------------------------
|Tata Cara Menambahkan level akses
| 1. Tambahkan pada list array yang ada di fungsi akses_level
| 2. Tambahkan pada list array yang ada di fungsi menu_link
| 3. Tambahkan pada script kode file core.js 
|-------------------------------------------------------------
*/

// list ini untuk menambahkan induk menu
function inisial($key='')
{
	$ins 	= array(
		'ms' => 'Master User',
		'ad' => 'Administrasi',
		'ps' => 'Pesan',
		'rd' => 'Reporting Data'
	);

	if($key)
	{
		$res = $ins[$key];
	}
	else
	{
		$res = $ins;
	}

	return $res;
}

// Fungsi ini yang akan di munculkan pada menu
function akses_link()
{
	$_this =& get_instance();
	$id_level = $_this->session->userdata('id_level');
	$_this->db->select('menu');
	$_this->db->where(array('id_level'=>$id_level));
	$_this->db->limit(1);
	$r = $_this->db->get('tbl_level')->result();
	$cuns = unserialize($r[0]->menu);
	$list = $cuns;

	$icon = array(
		'Administrasi' => '<i class="material-icons">&#xe24d;</i>',
		'Master User' => '<i class="material-icons">&#xe851;</i>',
		'Pesan' => '<i class="material-icons">&#xe0b9;</i>',
		'Reporting Data' => '<i class="material-icons">&#xe160;</i>'
	);

	$html = '';
	$uri = array();
	$uri = $_this->uri->segment(1);
	$html .= '<nav class="scroll nav-stacked nav-active-primary">';
	$html .= '<ul class="nav" ui-nav>';
	$html .= '<li';
	$uri = $_this->uri->segment(1);
	if($uri == 'dashboard'){
		$html .= ' class="active"';
	}
	else
	{
		$html .= '';
	}
	$html .= '>';
		$html .= '<a href="'.base_url('dashboard').'">';
		$html .= '<span class="nav-icon">';
		$html .= '<i class="material-icons">&#xe3fc;</i>';
		$html .= '</span>';
		$html .= '<span class="nav-text">Dashboard</span>';
		$html .= '</a>';
	$html .= '</li>';
	$html .= '<li class="nav-header hidden-folded">';
		$html .= '<small class="text-muted">Main Menu</small>';
	$html .= '</li>';
	foreach ($list as $key => $value) {
		$html .= '<li';
			if(@$value[$uri])
			{
				$html .= ' class="active"';
			}
			else
			{
				$html .= '';
			}
		$html .= '>';
			$html .= '<a>';
				$html .= '<span class="nav-caret"><i class="fa fa-caret-down"></i></span>';
				$html .= '<span class="nav-icon">'.$icon[$key].'</span>';
				$html .= '<span class="nav-text">'.$key.'</span>';
			$html .= '</a>';
			$html .= '<ul class="nav-sub nav-mega nav-mega-3">';
				foreach ($value as $key1 => $value1) {

					$html .= '<li';
					if($uri == $key1)
					{
						$html .= ' class="active"';
					}
					else
					{
						$html .= '';
					}
					$html .= '>';
						$html .= '<a href="'.base_url($key1).'/action/view">';
							$html .= '<span class="nav-text">'.$value1.'</span>';
						$html .= '</a>';
					echo '</li>';
				}
			$html .= '</ul>';
		$html .= '</li>';
	}
	$html .= '</ul>';
	$html .= '</nav>';

	return $html;
}

// List array ini untuk form level
function akses_level()
{
	$list = array(
		'ad' => array(
			'Administrasi Pasien Booking' => array(
				'ad_pb_view' => 'View Pasien Booking',
				'ad_pb_add' => 'Add Pasien Booking',
				'ad_pb_edit' => 'Edit Pasien Booking',
				'ad_pb_delete' => 'Hapus Pasien Booking'
			),
			'Administrasi Limit Pasien' => array(
				'ad_ld_view' => 'View Limit Dokter',
				'ad_ld_add' => 'Add Limit Dokter',
				'ad_ld_edit' => 'Edit Limit Dokter',
				'ad_ld_delete' => 'Hapus Limit Dokter'
			),
			'Jadwal Cuti Dokter'	=> array(
				'ad_cuti_view'		=> 'View List Cuti Dokter',
				'ad_cuti_add'		=> 'Add Cuti Dokter',
				'ad_cuti_edit'		=> 'Edit Cuti Dokter',
				'ad_cuti_delete'		=> 'Delete Cuti Dokter'
			),
			'Cara Bayar'	=> array(
				'ad_cabar_view'		=> 'View List Cara Bayar',
				'ad_cabar_add'		=> 'Add Cara Bayar',
				'ad_cabar_edit'		=> 'Edit Cara Bayar',
				'ad_cabar_delete'		=> 'Delete Cara Bayar'
			)
		),
		'ms' => array(
			'Manajemen Level' 		=> array(
				'ms_level_view' 	=> 'View List Level',
				'ms_level_add' 		=> 'Add Level',
				'ms_level_edit' 	=> 'Edit Level',
				'ms_level_delete' 	=> 'Delete Level'
				),
			'Manajemen User' 		=> array(
				'ms_user_view' 		=> 'View List User',
				'ms_user_add'		=> 'Add User',
				'ms_user_edit'		=> 'Edit User',
				'ms_user_delete'	=> 'Delete User',
				'ms_user_upload'	=> 'Upload User',
				'ms_user_download'	=> 'Download User',
				'ms_user_log'		=> 'User Log'
				)
		),
		'ps' => array(
			'Pesan Masuk'			=> array(
				'ps_psm_view'		=> 'View Pesan Masuk',
				'ps_psm_add'		=> 'Add Pesan Masuk',
				'ps_psm_edit'		=> 'Edit Pesan Masuk',
				'ps_psm_delete'		=> 'Delete Pesan Masuk'
			),
			'Pesan Brodcast'		=> array(
				'ps_bc_view'		=> 'View Pesan Brodcast',
				'ps_bc_add'			=> 'Add Pesan Brodcast',
				'ps_bc_edit'		=> 'Edit Pesan Brodcast'
			),
			'Manajemen Informasi'		=> array(
				'ps_info_view'		=> 'View Manajemen Informasi',
				'ps_info_add'			=> 'Add Manajemen Informasi',
				'ps_info_edit'		=> 'Edit Manajemen Informasi',
				'ps_info_delete'		=> 'Delete Manajemen Informasi'
			)
		),
		'rd' => array(
			'Laporan Data' => array(
				'rd_datapen_view'		=> 'Laporan Data',
				'rd_datapen_download'	=> 'Download Data'
			)
		)
	);

	return $list;
}

// List array ini untuk data menu
function menu_link()
{
	$links = array(
		'ms_user_view' 		=> 'Manajemen User',
		'ms_level_view' 	=> 'Manajemen Level',
		'ms_jabatan_view' 	=> 'Manajemen Jabatan',
		'ad_cuti_view' 		=> 'Jadwal Cuti Dokter',
		'ad_cabar_view' 	=> 'Cara Bayar',
		'ad_ld_view' 		=> 'Limit Pasien Online',
		'ad_pb_view' 		=> 'Pasien Booking',
		'ps_psm_view' 		=> 'Pesan Masuk',
		'ps_bc_view' 		=> 'Pesan Brodcast',
		'ps_info_view' 		=> 'Manajemen Informasi',
		'rd_datapen_view' 	=> 'Laporan Data'
	);
	return $links;
}



function get_template_directory($path,$dir_file)
{	
	global $SConfig;
	$path_replace = str_replace('\\', '/', $path);
	$get_digit_doc_root = strlen($SConfig->_document_root);
	$full_path = substr($path_replace, $get_digit_doc_root);
	return $SConfig->_site_url.$full_path.'/'.$dir_file;
}

function get_template($view){
	$_this =& get_instance();
	return $_this->site->view($view);
}

function set_url($sub){
	$_this =& get_instance();
	
	if($_this->site->side == 'backend'){
		return site_url($sub);
	}
}

function array_trim($array = array()) {
    if (count($array)) {
        $ar = array();

        foreach ($array as $k => $v) {
            $ar[$k] = trim($v);
        }

        return $ar;
    } else {
        return $array;
    }
}

function array_find($needle, $haystack = array()){
    $needle = trim($needle);
    foreach ($haystack as $key => $value) {
        if (is_array($value)) {
            if($this->array_find($needle, $value)){
                return true;
                                
            }
        } else {
            if (false !== stripos($needle, $value)) {
                return true;
            }
        }
    }
    return false;
}

function isMobile($ua = '') {
    if ($ua == '') {
        $ua = $_SERVER["HTTP_USER_AGENT"];
    }
    if (isset($_REQUEST['mobile']) || isset($_SERVER['mobile']) || preg_match("/(android|avantgo|blackberry|bolt|boost|cricket|docomo|fone|hiptop|mini|mobi|palm|phone|pie|tablet|up\.browser|up\.link|webos|wos)/i", $ua)) {
        return true;
    } else {
        return false;
    }
}

function calrt() {
	$_this =& get_instance();
	$msg = '';
    if (@$_SESSION['msg']) {
        $msg = @$_SESSION['msg'];
        $err = @$_SESSION['error'];

        if ((int) $err == 0) {
            return '<div class="alert alert-success " style="cursor:pointer;z-index:10000;height: 40px;margin-top: 30px;margin-bottom: 10px;margin-left:20px;margin-right:20px;background-color:rgb(210, 220, 228);padding:10px" onclick="$(this).slideUp(\'fast\',function(){$(this).remove()});">' . $msg . '</div>';
        } elseif ($err == 'info') {
            return '<div class="alert alert-info" style="cursor:pointer;z-index:10000;height: 40px;margin-top: 30px;margin-bottom: 10px;margin-left:20px;margin-right:20px;background-color:rgb(212, 228, 210);padding:10px" onclick="$(this).slideUp(\'fast\',function(){$(this).remove()});">' . $msg . '</div>';
        } else {
            return '<div class="alert alert-error" style="cursor:pointer;z-index:10000;height: 40px;margin-top: 30px;margin-bottom: 10px;margin-left:20px;margin-right:20px;background-color: rgb(228, 210, 210);padding:10px" onclick="$(this).slideUp(\'fast\',function(){$(this).remove()});">' . $msg . '</div>';
        }
    }
}

function generateReturn($location, $json = '', $returnJson = false) {
    if ($returnJson) {//$this->isMobile() || 
        //header('content-type:json/text');
        echo $json ? json_encode($json) : json_encode($_SESSION);
        exit();
    } else {
        header('location:' . $location);
    }
}

function aksesName($keys='')
{
	$_this 	=& get_instance();
	$akses 	= $_this->session->userdata('akses');
	$exa	= explode("|", $akses);
	$ls = array();
	foreach ($exa as $key => $value) {
		$ls[$value] = $value;
	}

	if($keys)
	{
		if(!empty($ls[$keys]))
		{
			$res = $ls[$keys];
		}
		else
		{
			$res = 't';
		}
	}
	else
	{
		$res = 's';
	}

	echo $res;
}
