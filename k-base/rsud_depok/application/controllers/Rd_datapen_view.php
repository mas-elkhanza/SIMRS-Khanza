<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Rd_datapen_view extends backend_controller {

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('Ld_model'));
		$this->site->is_logged_in();
		$host = base_url();
	}

	public function index()
	{
		$data = array(
			'heading' => 'Akses salah...',
			'message' => 'Maaf kami tidak bisa memunculkan halaman yang anda cari..'
		);
		$this->site->view_error('error_404',$data);
	}

	public function action($action='')
	{
		if($action == 'view')
		{
			global $SConfig;
			$f 		= ($this->uri->segment(4))	? $this->uri->segment(4) : 'f';
			$l 		= ($this->uri->segment(5))	? $this->uri->segment(5) : 10;
			$p 		= ($this->uri->segment(6))	? $this->uri->segment(6) - 1 : 0;

			$pq 	= $p * $l;
			$no 	= ($p * $l) + 1;

			$where 	= '';
			if($f!='f')
			{
				$where = 'kd_dokter like \'%'.$f.'%\' OR nm_dokter like \'%'.$f.'%\'';
			}

			$jum 	= $this->Ld_model->count('limit_pasien_online',$where);
			$r 		= $this->Ld_model->getRecordList('limit_pasien_online','*',$where,'','kd_dokter ASC',$l,$pq);
			$rjum 	= $this->Ld_model->get_jumlah_daftar();

			$tnow = date('Y');
			$dnow = date('m');

			$jml_tgl = cal_days_in_month(CAL_GREGORIAN, $dnow, $tnow);
			echo '<pre>';

			$kat = array('belum'=>'belum','terdaftar'=>'terdaftar','dokter-berhalangan'=>'dokter-berhalangan','batal'=>'batal');
			
			foreach ($rjum as $k => $v) {
				$jml_hari[$v->hari] = array('jumlah' => $v->jumlah);
			}

			for($i=1; $i<=$jml_tgl; $i++)
			{
				$hasil[$i] = !empty($jml_hari[$i]) ? $jml_hari[$i] : 0;
			}

			/*foreach ($hasil as $k => $v) {
				$vst[] = ''
			}*/

			print_r($hasil);


			$data = array(
			'title'		=> 'Laporan Data',
			'f' 		=> $f,
			'l' 		=> $l,
			'p' 		=> $p,
			'pq' 		=> $pq,
			'no' 		=> $no,
			'jum' 		=> $jum,
			'r'			=> $r,
			'limit'		=> $SConfig->_limit,
			'v'			=> 'rd_statistik'
			);

			exit();

			$this->site->view('v',$data);
		}

		else if($action == 'save')
		{
			$post = $this->input->post();
			//$data['id'] 	= htmlentities($post['id'],ENT_QUOTES);
			$data['limit_reg'] 	= htmlentities($post['limit_reg'],ENT_QUOTES);
			$data['limit_kedatangan'] 	= htmlentities($post['limit_kedatangan'],ENT_QUOTES);
			$data['waktu_praktek'] 	= htmlentities($post['waktu_praktek'],ENT_QUOTES);

			$rules = $this->Ld_model->rules;
			$this->form_validation->set_rules($rules);
			
			if($post['id'])
			{
				if($this->form_validation->run() == FALSE)
				{
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully update, please try again later';
					$this->Ld_model->createHistory('Gagal update limit pendaftaran dengan kode dokter : '.$post['id'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully update';
					$this->Ld_model->createHistory('Update limit dengan kode dokter : '.$post['id'].'',$this->session->userdata('ID'));
					$this->Ld_model->update('limit_pasien_online',$data,array('kd_dokter'=>$post['id']));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
			else
			{
				if($this->form_validation->run() == FALSE)
				{
					
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully saved, please try again later';
					$this->Ld_model->createHistory('Gagal tambah limit dengan kode dokter : '.$post['id'].'',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully saved';
					
					$this->Ld_model->createHistory('Tambah limi dengan kode dokter : '.$post['id'].'',$this->session->userdata('ID'));
					$this->Ld_model->insert('limit_pasien_online',$data,FALSE);
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
			//print_r($post);
		}
		/*else if($action == 'delete')
		{
			$uri = $this->uri->segment(4);
			$idl = explode('k', $uri);
			
			$_SESSION['error'] = '';
        	$_SESSION['msg']   = 'Data has been succesfully deleted';

			foreach ($idl as $idv)
			{
				$det = $this->Ld_model->getRecordList('kategori','nm_kategori','id=\''.$idv.'\'');
				if(!$this->Ld_model->delete($idv))
				{
					$_SESSION['error'] = '1';
                	$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully deleted, please try again later';
				}
				else
				{
					$this->Ld_model->createHistory('Delete kategori dengan nama : '.$det[0]->nm_kategori.'',$this->session->userdata('ID'));
				}
				//echo $det[0]->nm_level;
			}

			generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
		}
		else if($action == 'get_parent')
		{
			$id 	= $this->input->post();
			$wh 	= array('status' => 'Y', 'jenis' => $id['id']);
			$r 		= $this->Ld_model->getRecordList('kategori','id,nm_kategori',$wh);
			$ls 	=  ConDataToArray($r,'id','nm_kategori');
			
			echo htmlSelectFromArray($ls, 'name="parent_id" class="form-control input-sm"', true);
				
		}*/
	}
}