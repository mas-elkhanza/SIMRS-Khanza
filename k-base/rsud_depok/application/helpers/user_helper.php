<?php defined('BASEPATH') OR exit('No direct script access allowed');

function seo_title($s) {
    $c = array (' ');
    $d = array ('-','/','\\',',','.','#',':',';','\'','"','[',']','{','}',')','(','|','`','~','!','@','%','$','^','&','*','=','?','+','–');
    $s = str_replace($d, '', $s); // Hilangkan karakter yang telah disebutkan di array $d
    $s = strtolower(str_replace($c, '-', $s)); // Ganti spasi dengan tanda - dan ubah hurufnya menjadi kecil semua
    return $s;
}

function object_to_array($object) {
        if(is_object($object)) {
            $object = get_object_vars($object);
        }
        if(is_array($object)) {
            return array_map(__FUNCTION__, $object);
        } else {
            return $object;
        }
    }

function get_parent($data, $parent = '',$id = '') {
  static $i = 1;
  $tab = str_repeat(" ", $i);
  if($parent)
  {
    $p = $parent;
  }
  else
  {
    $p = 0;
  }
  if (@$data[$p]) {
      $html = "$tab<ul id='menu-tree' class='filetree'>";
      $i++;
      foreach ($data[$p] as $v) {
        //print_r($v);
           $child = get_parent($data, $v->id);
           $html .= "$tab<li><label class='md-check'>";
           $html .= '<input type="checkbox" name="id_kat_post['.$v->nm_kategori.']['.$v->id.']" value="'.$v->id.'" data-id_kat_post="'.$v->id.'"';
           if($id)
           {
                if($v->id == $id)
               {
                    $html .= ' checked';
               }
               else
               {
                    $html .= ' ';
               }
           }

           $html .= '> <i class="indigo"></i> '. $v->nm_kategori .'</label>';
           if ($child) {
               $i--;
               $html .= $child;
               $html .= "$tab";
           }
           $html .= '</li>';
      }
      $html .= "$tab</ul>";
      return $html;
  } 
else {
      return false;
  }
}

function kategori_post($key='')
{
    $data = array(
        'H' => 'Headline',
        'S' => 'Slideshow',
        'U' => 'Utama'
    );

    if($key)
    {
        $list = $data[$key];
    }
    else
    {
        $list = $data;
    }

    return $list;
}

function getJabatanVal($where='')
{
    $_this =& get_instance();

    $_this->db->select('nm_jabatan');
    if($where)
    {
        $_this->db->where('id',$where);
    }

        $res = $_this->db->get('tbl_jabatan')->result();

    return $res[0]->nm_jabatan;
}

function getLevelVal($where='')
{
    $_this =& get_instance();

    $_this->db->select('nm_level');
    if($where)
    {
        $_this->db->where('id_level',$where);
    }

        $res = $_this->db->get('tbl_level')->result();

    return $res[0]->nm_level;
}

function getInfoUser($where='',$field='')
{   
    $_this =& get_instance();

    $_this->db->select($field);
    if($where)
    {
        $_this->db->where('ID',$where);
    }

        $res = $_this->db->get('tbl_user')->result();

    if($field == 'online')
    {
        if($res[0]->online == 'Y')
        {
            $res = 'online';
        }
        else
        {
            $res = 'Ofline';
        }
    }
    

    return $res;
}


function getFotoVal($where='')
{
    $_this =& get_instance();

    $_this->db->select('foto');
    if($where)
    {
        $_this->db->where('ID',$where);
    }

        $res = $_this->db->get('tbl_user')->result();

    return base_url($res[0]->foto);
}

function StatusOnline($data='')
{   
    if($data == 'Y')
    {
        $res = '<small class="block text-muted"><i class="fa fa-circle text-success m-r-sm"></i>online</small>';
    }
    else
    {
        $res = '<small class="block text-muted"><i class="fa fa-circle m-r-sm"></i>Ofline</small>';
    }

    return $res;
}

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

/*
Fungsi : untuk extrac data array, yang akan dijadikan ajax
*/
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

function ConDataToArray($data='',$key1='',$key2='',$key3='',$key4='',$key5='')
{
    $ls = array();
    if($data)
    {
        foreach ($data as $key => $value) {
            $ls[$value->$key1] = trim($value->$key2);
        }
    }

    return $ls;
}

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

function tgl_indo($tgl)
{
    $ubah = gmdate($tgl, time()+60*60*8);
    $pecah = explode("-",$ubah);
    //$time = $pecah[3];
    $tanggal = $pecah[2];
    $bulan = bulan($pecah[1]);
    $tahun = $pecah[0];
    return $tanggal.' '.$bulan.' '.$tahun;
}

function FilterArrayDuplikat($data)
{
    if(is_array($data)){
        $cek = array_unique($data);
        foreach($cek as $key => $val){
            if(strlen(trim($val)) > 0){
                $hasil = $val;
            }
        }
        return $hasil;
    }
}

/*
Administrasi
*/
function agama($key='',$id='',$class='')
{
    $res = '';
    $list = array(
        1 => 'Islam',
        2 => 'Kristen',
        3 => 'Katolik',
        4 => 'Hindu',
        5 => 'Budha',
        6 => 'Konghucu'
    );

    if($id)
    {
        $i = 'id="'.$id.'"';
    }

    if($class)
    {
        $c = 'class="'.$class.'"';
    }

    if($key)
    {
        $res = $list[$key];
    }
    else
    {
        $res .= '<div class="form-group row">';
        $res .= '<label class="col-sm-2 form-control-label">Agama</label>';
        $res .= '<div '.$c.'>';
        $res .= '<select class="form-control input-c" '.$i.'>';
            foreach ($list as $key => $value) {
                $res .= '<option value='.$key.'>'.$value.'</option>';
            }
        $res .= '</select>';
        $res .= '</div>';
        $res .= '</div>';
    }

    return $res;
}

function jk($key='',$id='',$class='')
{
    $res = '';
    $list = array(
        1 => 'Laki-laki',
        2 => 'Perempuan'
    );

    if($id)
    {
        $i = 'id="'.$id.'"';
    }

    if($class)
    {
        $c = 'class="'.$class.'"';
    }

    if($key)
    {
         $res = $list[$key];
    }
    else
    {
        $res .= '<div class="form-group row">';
        $res .= '<label class="col-sm-2 form-control-label">Jenis Kelamin</label>';
        $res .= '<div '.$c.'>';
        $res .= '<div class="checkbox">';
            foreach ($list as $key => $value) {
                $res .= '<label class="ui-check">';
                $res .= '<input type="radio" name="jk" value="'.$key.'" '.$i.'>';
                $res .= '<i class="dark-white"></i>';
                $res .= $value;
                $res .= '</label> &nbsp;';
            }
        $res .= '</div>';
        $res .= '</div>';
        $res .= '</div>';
    }

    return $res;
}

function status_pernikahan($key)
{
    $list = array(
        1 => 'Belum pernah',
        2 => 'Sudah menikah',
        3 => 'Duda',
        4 => 'Janda'
    );

    if($key)
    {
         $res = $list[$key];
    }
    else
    {
        $res = $list;
    }

    return $res;
}

function cara_datang($key='')
{
    $list = array(
        1 => 'Mandiri',
        2 => 'Rujukan'
    );

    if($key)
    {
         $res = $list[$key];
    }
    else
    {
        $res = $list;
    }

    return $res;
}

function status($key='')
{
    $list = array(
        'Y' => 'Aktif',
        'T' => 'Tidak aktif'
    );

    if($key)
    {
         $res = $list[$key];
    }
    else
    {
        $res = $list;
    }

    return $res;
}

function tipe_ruangan($key='')
{
    $list = array(
        1 => 'Rawat Inap',
        2 => 'Poliklinik',
        3 => 'Igd',
        4 => 'Icu',
        5 => 'Kamar Operasi'
    );

    if($key)
    {
         $res = $list[$key];
    }
    else
    {
        $res = $list;
    }

    return $res;
}

function AddButton_outline($key='',$type='',$link1='',$link2='',$text1='',$text2='')
{
    $res ='';

    if($type == 1)
    {
        $t = 'a';
        $l1 = 'href="'.base_url($link1).'"';
        $l2 = 'href="'.base_url($link2).'"';
    }
    else if($type == 2)
    {
        $t = 'button type="submit"';
    }

    if($text1)
    {
        $t1 = $text1;
    }
    else
    {
        $t1 = 'Cancel';
    }

    if($text2)
    {
        $t2 = $text2;
    }
    else
    {
        $t2 = 'Save';
    }

    $list = array(
        1 => '<'.$t.' '. $l1 .' class="btn white">'.$t1.'</'.$t.'>',
        2 => '<'.$t.' '. $l2 .' class="btn btn-primary">'.$t2.'</'.$t.'>'
    );

    if($key)
    {
         $res = $list[$key];
    }
    else
    {
        //$res .= '<div class="form-group row m-t-lg">';
        $res .= '<div class="col-sm-4 col-sm-offset-2">';
            foreach ($list as $key => $value) {
                $res .= $value . '&nbsp;';
            }
        $res .= '</div>'; 
        //$res .= '</div>'; 
    }

    return $res;
}

function FsBtn_outline($key='',$type='',$link1='',$link2='',$icon1='',$icon2='')
{
    $res ='';

    if($type == 1)
    {
        $t = 'a';
        $l1 = 'href="'.base_url($link1).'"';
        $l2 = 'href="'.base_url($link2).'"';
    }
    else if($type == 2)
    {
        $t = 'button type="submit"';
    }

    if($icon1)
    {
        $i1 = '<i class="'.$icon1.'"></i>';
    }

    if($icon2)
    {
        $i2 = '<i class="'.$icon2.'"></i>';
    }
    

    $list = array(
        1 => '<'.$t.' '. $l1 .' class="btn btn-outline b-warn text-warn">'.$i1.'</'.$t.'>',
        2 => '<'.$t.' '. $l2 .' class="btn btn-outline b-warn text-warn">'.$i2.'</'.$t.'>'
    );

    if($key)
    {
         $res = $list[$key];
    }
    else
    {
        $res .= '<div class="form-group row m-t-lg">';
        $res .= '<div class="col-sm-4 col-sm-offset-2">';
            foreach ($list as $key => $value) {
                $res .= $value . '&nbsp;';
            }
        $res .= '</div>'; 
        $res .= '</div>'; 
    }

    return $res;
}

function beda_waktu($date1, $date2, $format = false) 
{
    $diff = date_diff( date_create($date1), date_create($date2) );
    if ($format)
        return $diff->format($format);
    
    return array('y' => $diff->y,
                'm' => $diff->m,
                'd' => $diff->d,
                'h' => $diff->h,
                'i' => $diff->i,
                's' => $diff->s
            );
}

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
