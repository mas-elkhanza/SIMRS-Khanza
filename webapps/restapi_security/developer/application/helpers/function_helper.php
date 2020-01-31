<?php defined('BASEPATH') OR exit('No direct script access allowed');


if(!function_exists('htmlSelectFromArray')){
	function htmlSelectFromArray($array, $attribute, $blank = true, $selected = '', $showKey = false, $arrayNonActive = '') {
	    //$html  = "\r\n";
	    $html = '<select ' . $attribute . '>';
	    if (!empty($blank)) {
	        if ($blank === true) {
	            $html .= '<option value="">-- -- --</option>';
	        } else {
	            $html .= '<option value="">' . $blank . '</option>';
	        }
	    }
	    if (is_array($array)) {
	        if (count($array) > 0) {
	            foreach ($array as $k => $v) {
	                $html .= '<option value="' . $k . '"';
	                if (is_array($selected)) {
	                    if (in_array($k, $selected)) {
	                        $html .= ' selected="selected"';
	                    }
	                } else {
	                    if ((string) $k == $selected && strlen($k) == strlen($selected)) {
	                        $html .= ' selected="selected"';
	                    }
	                }
	                if (is_array($arrayNonActive)) {
	                    if (in_array($k, $arrayNonActive)) {
	                        $html .= ' style="background-color:#eeeeee;color:#ff0000;"';
	                    }
	                }
	                $html .= '>';
	                if ($showKey == true) {
	                    $html .= $k . ' - ';
	                }
	                $html .= $v . '</option>';
	            }
	        } else {
	            $html .= '<option value="">-- --</option>';
	        }
	    } else {
	        $html .= '<option value="">-- --</option>';
	    }
	    $html .= '</select>';
	    return $html;
	}
}

if(!function_exists('htmlSelectGroup'))
{
	function htmlSelectGroup($data='',$name='')
	{
		echo '<select name='.$name.' class="form-control select2" style="width:100%;">';
		foreach ($data as $k => $v) {
			/*print_r($v);*/
			echo '<option value='.$v->id_departement.'>';
			if($v->parent == 0)
			{
				echo $v->nm_departement;
			}
			else
			{
				echo ' --'.$v->nm_departement;
			}
			echo '</option>';
		}
		echo '</select>';
	}
}

if(!function_exists('entity'))
{
	function entity($var, $htmlentity = true)
	{
		if ($htmlentity) {
        	return htmlentities($var, ENT_QUOTES);
	    }
	    return $var;
	}
}

if(!function_exists('calrt'))
{
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
}


/*
| Fungsi konversi
| 
|
*/

if(!function_exists('ConDataToArray')){
	function ConDataToArray($data='',$key1='',$key2='',$key3='',$key4='',$key5='')
	{
	    $ls = array();
	    if($data)
	    {
	        if(!empty($key1) && !empty($key2) && !empty($key3) && !empty($key4))
	        {
	            foreach ($data as $key => $value) {
	                $ls[$value->$key1] = trim($value->$key2) .'  ('. trim($value->$key3).' - '. trim($value->$key4) .')';
	            }
	        }
	        else if(!empty($key1) && !empty($key2) && !empty($key3))
	        {
	            foreach ($data as $key => $value) {
	                $ls[$value->$key1] = trim($value->$key2) .'  ('. trim($value->$key3).')';
	            }
	        }
	        else if(!empty($key1) && !empty($key2))
	        {
	            foreach ($data as $key => $value) {
	                $ls[$value->$key1] = trim($value->$key2);
	            }
	        }
	    }

	    return $ls;
	}
}

if(!function_exists('ConDataToArrayObj')){
	function ConDataToArrayObj($data='',$key1='',$key2='',$key3='',$key4='',$key5='')
	{
	    $ls = array();
	    if($data)
	    {
	        if(!empty($key1) && !empty($key2) && !empty($key3) && !empty($key4))
	        {
	            foreach ($data as $key => $value) {
	                $ls[$value[$key1]] = trim($value[$key2]) .'  ('. trim($value[$key3]).' - '. trim($value[$key4]) .')';
	            }
	        }
	        else if(!empty($key1) && !empty($key2) && !empty($key3))
	        {
	            foreach ($data as $key => $value) {
	                $ls[$value[$key1]] = trim($value[$key2]) .'  ('. trim($value[$key3]).')';
	            }
	        }
	        else if(!empty($key1) && !empty($key2))
	        {
	            foreach ($data as $key => $value) {
	                $ls[$value[$key1]] = trim($value[$key2]);
	            }
	        }
	    }

	    return $ls;
	}
}

if(!function_exists('tanggal_indo'))
{
	function tanggal_indo($tanggal, $cetak_hari = false)
	{
	    $hari = array ( 1 =>    'Senin',
	                'Selasa',
	                'Rabu',
	                'Kamis',
	                'Jumat',
	                'Sabtu',
	                'Minggu'
	            );
	            
	    $bulan = array (1 =>   'Januari',
	                'Februari',
	                'Maret',
	                'April',
	                'Mei',
	                'Juni',
	                'Juli',
	                'Agustus',
	                'September',
	                'Oktober',
	                'November',
	                'Desember'
	            );
	    $split    = explode('-', $tanggal);
	    
	    $tgl_indo = $split[2] . ' ' . $bulan[ (int)$split[1] ] . ' ' . $split[0];
	    
	    if ($cetak_hari) {
	        $num = date('N', strtotime($tanggal));
	        return $hari[$num] . ', ' . $tgl_indo;
	    }
	    return $tgl_indo;
	}
}

if(!function_exists('hari_indo'))
{
	function hari_indo($tgl){
	    $tentukan_hari=date('D',strtotime($tgl));
	    $day = array(
	        'Sun' => 'AKHAD',
	        'Mon' => 'SENIN',
	        'Tue' => 'SELASA',
	        'Wed' => 'RABU',
	        'Thu' => 'KAMIS',
	        'Fri' => 'JUMAT',
	        'Sat' => 'SABTU'
	    );
	    return $hari=$day[$tentukan_hari];
	}
}

/*
| Fungsi generate data
| 
|
*/

if(!function_exists('generateReturn'))
{
	function generateReturn($location, $json = '', $returnJson = false) {
	    if ($returnJson) {
	    	//$this->isMobile() || 
	        //header('content-type:json/text');
	        echo $json ? json_encode($json) : json_encode($_SESSION);
	        exit();
	    } else {
	        header('location:' . $location);
	    }
	}
}

/*
| Fungsi keamanan
| 
|
*/

if(!function_exists('bCrypt'))
{
	function bCrypt($pass,$cost)
	{
	    $chars='./ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

	    // Build the beginning of the salt
	    $salt=sprintf('$2a$%02d$',$cost);

	    // Seed the random generator
	    mt_srand();

	    // Generate a random salt
	    for($i=0;$i<22;$i++) $salt.=$chars[mt_rand(0,63)];

	    // return the hash
	    return crypt($pass,$salt);
	}
}


/*
| Fungsi Menampilkan data
| 
|
*/
if(!function_exists('showValRec'))
{
	function showValRec($ar) 
	{
	    $ret = array();
	    if (@count($ar) > 0) {
	        foreach ($ar as $k => $v) {
	            $ret[] = 'data-' . $k . '="' . $v . '"';
	        }
	    }

	    return implode(' ', $ret);
	}
}


if(!function_exists('spasi_parent')){
	function spasi_parent($data=''){
		if($data == 1)
		{
			$spasi = '';
		}
		else if($data == 2){
			$spasi = '&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>';
		}
		else if($data == 3)
		{
			$spasi = '&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>';
		}
		else if($data == 3)
		{
			$spasi = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>';
		}
		else if($data == 4)
		{
			$spasi = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>';
		}
		else if($data == 5)
		{
			$spasi = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>';
		}
		else if($data == 6)
		{
			$spasi = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>';
		}
		else if($data == 7)
		{
			$spasi = '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i class="fa fa-angle-double-right"></i>';
		}
		return $spasi;
	}
}

/*
|
|
*/

if(!function_exists('getData'))
{
	function getData($table='',$field='',$id='')
	{
		$_this =& get_instance();
		$_this->load->model('Get_model');
		$data = $_this->Get_model->getRecordList($table,$field,$id);
		return $data;
	}
}

/*
|
|
*/

if(!function_exists('Tahun'))
{
	function Tahun($tgl)
	{
		$_this =& get_instance();
        $sekarang = date('Y-m-d H:i');
        $return   = '';
        if(!empty($tgl)){
            $lama_inap = Lama($tgl, $sekarang);
            $tahun   = $lama_inap['years'];
            $bulan   = $lama_inap['months'];
            $bulan2   = $lama_inap['months_total'];
            $return = 'Tahun '. $tahun . ', Bulan '. $bulan;
        }else{
            $return  =  $tgl_pasien  = "";
        }
        
        return $return;
	}
}

if(!function_exists('UsiaTahun'))
{
	function UsiaTahun($tgl)
	{
	    $sekarang = date('Y-m-d H:i');
	    $return   = '';
	    if(!empty($tgl)){
	        $lama_inap = Lama($tgl, $sekarang);
	        $tahun   = $lama_inap['years'];
	        $bulan   = $lama_inap['months'];
	        $bulan2   = $lama_inap['months_total'];
	        $return = $tahun;
	    }else{
	        $return  =  $tgl_pasien  = "";
	    }
	    
	    return $return;
	}
}

if(!function_exists('Lama'))
{
	function Lama($d1, $d2)
	{
		$d1 = (is_string($d1) ? strtotime($d1) : $d1);
	    $d2 = (is_string($d2) ? strtotime($d2) : $d2);
	    $diff_secs = abs($d1 - $d2);
	    $base_year = min(date("Y", $d1), date("Y", $d2));
	    $diff = mktime(0, 0, $diff_secs, 1, 1, $base_year);
	    
	    return array(
	    "years" => date("Y", $diff) - $base_year,
	    "months_total" => (date("Y", $diff) - $base_year) * 12 + date("n", $diff) - 1,
	    "months" => date("n", $diff) - 1,
	    "days_total" => floor($diff_secs / (3600 * 24)),
	    "days" => date("j", $diff) - 1,
	    "hours_total" => floor($diff_secs / 3600),
	    "hours" => date("G", $diff),
	    "minutes_total" => floor($diff_secs / 60),
	    "minutes" => (int) date("i", $diff),
	    "seconds_total" => $diff_secs,
	    "seconds" => (int) date("s", $diff)
	    ); 
	}
}

if(!function_exists('AddTglNext'))
{
	function AddTglNext($tgl='',$jml_hari,$satuan='')
	{
		$tNow   = date_create($tgl);
		date_add($tNow, date_interval_create_from_date_string($jml_hari .' '. $satuan));
		$res  = date_format($tNow, 'Y-m-d');
		return $res;
	}
}

if(!function_exists('CountTglNowToNext'))
{
	function CountTglNowToNext($dNow,$dNext)
	{
		$convert_totime 	= abs(strtotime($dNow) - strtotime($dNext));
		$res_day			= $convert_totime/86400;
		return $res_day;
	}
}

if(!function_exists('getNoReg'))
{
	function getNoReg($noreg1='',$noreg2='')
	{
		if(empty($noreg2) && empty($noreg1))
		{
			$NoReg = 000;
		}
		else if($noreg2 >= $noreg1)
		{
			$NoReg = $noreg2;
		}
		else if($noreg2 <= $noreg1)
		{
			$NoReg = $noreg1;
		}
		else if($noreg2 == $noreg1){
			$NoReg = $noreg2;
		}
		else
		{
			$NoReg = $noreg1;
		}

		return $NoReg;
	}
}

if(!function_exists('cekAlamat'))
{
	function cekAlamat($v1='',$v2='',$v3='',$v4='',$v5='')
	{
		if($v1=='-,-,-,-' && $v2=='-' && $v3=='-' && $v3=='-')
		{
			$alamat = $v5;
		}
		else if($v1!='' && $v2=='' && $v3=='' && $v3=='')
		{
			$alamat = $v1;
		}
		else if($v1!='' && $v2!='' && $v3=='' && $v3=='')
		{
			$alamat = $v1 . ' - '. $v2 ;
		}
		else if($v1!='' && $v2!='' && $v3!='' && $v3=='')
		{
			$alamat = $v1 . ' - '. $v2 . ' - '. $v3 ;
		}
		else if($v1!='' && $v2!='' && $v3!='' && $v3!='')
		{
			$alamat = $v1 . ' - '. $v2 . ' - '. $v3 . ' - '. $v4 ;
		}
		return $alamat;
	}
}

if(!function_exists('notifikasi'))
{
	function notifikasi($show='')
	{
		$dNow = date('Y-m-d');
		$_this =& get_instance();
		$_this->load->model('Get_model');
		$data = $_this->Get_model->CountNotifikasi();
		
		if($dNow > @$data[0]->tgl_periksa)
		{
			return '0';
		}
		else
		{
			if($show == 'jumlah')
			{
				return ($data[0]->jumlah) ? $data[0]->jumlah : '0';
			}
		}
	}
}

function pagination($url='',$total_rows='',$per_page='')
{
	$CI =& get_instance();
	$CI->load->library('pagination');

	$num_links					= $total_rows / $per_page;
	$config['base_url'] 		= $url;
	$config['total_rows'] 		= ($total_rows) ? $total_rows : 1;
	$config['per_page'] 		= ($per_page) ? $per_page : 0;
	$config["uri_segment"] 		= 3;
    $config["num_links"] 		= 3;
    //$config['use_page_numbers'] = TRUE;
	// Membuat Style pagination untuk BootStrap v4
  	$config['first_link']       = 'First&nbsp;';
    $config['last_link']        = '&nbsp;Last';
    $config['next_link']        = '&nbsp;Next';
    $config['prev_link']        = 'Prev&nbsp;';
    $config['full_tag_open']    = '<span class="label label-lg black pos-rlt m-r-xs"><b class="arrow right b-black"></b><span class="">';
    $config['full_tag_close']   = '<b class="arrow left b-black"></b></span> </span>';   
    $config['cur_tag_open']     = '<b class="text-blue">';
    $config['cur_tag_close']    = '</b>';    

	$CI->pagination->initialize($config);
}

function user_files($file_name){
	$_this =& get_instance();
    if($_this->session->userdata('status')=='Y'){  // login check
        if (preg_match('^[A-Za-z0-9]{2,32}+[.]{1}[A-Za-z]{3,4}$^', $file_name)) // validation
        {
            	$file = 'http://172.16.200.5/appcore/uploads/artikel/'.$file_name;
	            if (file_exists($file)) // check the file is existing 
	            {
	                header('Content-Type: '.get_mime_by_extension($file));
	                readfile($file);
	            }
	            else
	            {
	            	show_404();
	            }
	    }
	    else
	    {
	    	show_404();
	    }  
	} 
}