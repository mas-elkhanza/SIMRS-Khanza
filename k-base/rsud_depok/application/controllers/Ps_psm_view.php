<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Ps_psm_view extends backend_controller {

	public function __construct()
	{
		parent::__construct();		
		$this->load->model(array('Ld_model','User_model'));
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
			$read 	= ($this->uri->segment(7))	? $this->uri->segment(7) : '';

			$pq 	= $p * $l;
			$no 	= ($p * $l) + 1;

			$where 	= 'status=1 ';
			if($f!='f' && $read!='')
			{
				$where = 'judul like \'%'.$f.'%\' and dibaca=\''.$read.'\'';
			}
			else if($f!='f')
			{
				$where = 'judul like \'%'.$f.'%\'';
			}
			else if($read!='')
			{
				$where = 'dibaca=\''.$read.'\'';
			}

			$jum 	= $this->Ld_model->count('pesan',$where);
			$r 		= $this->Ld_model->getRecordList('pesan','*',$where,'idpesan','id DESC',$l,$pq);

			// Get Nama Pasien
			/*$qnmp	= $this->Ld_model->getRecordList('pasien','no_rkm_medis,nm_pasien');
			$lsnmp 	=  ConDataToArray($qnmp,'no_rkm_medis','nm_pasien');*/

			$data = array(
			'title'		=> 'Administrasi Pesan Masuk',
			'f' 		=> $f,
			'l' 		=> $l,
			'p' 		=> $p,
			'pq' 		=> $pq,
			'no' 		=> $no,
			'jum' 		=> $jum,
			'r'			=> $r,
			'limit'		=> $SConfig->_limit,
			'v'			=> 'ad_psm'
			);

			//print_r($r);
			$this->site->view('v',$data);
		}

		else if($action == 'jawab')
		{
			$id 		= $this->uri->segment(4);
			$idpesan	= $this->uri->segment(5);
			$pengirim	= $this->uri->segment(6);

			$rj 			= $this->Ld_model->getRecordList('pesan','judul',array('id'=>$id,'idpesan'=>$idpesan,'pengirim'=>$pengirim),'','id ASC');

			$r 			= $this->Ld_model->getRecordList('pesan','*',array('idpesan'=>$idpesan),'','id ASC');

			// Get Nama Pasien
			$qnmp	= $this->Ld_model->getRecordList('pasien','no_rkm_medis,nm_pasien');
			$lsnmp 	=  ConDataToArray($qnmp,'no_rkm_medis','nm_pasien');

			// Get Nama Admin
			$qnml	= $this->User_model->getRecordList('user','ID,nm_lengkap');
			$lsnml 	=  ConDataToArray($qnml,'ID','nm_lengkap');


			$data = array(
				'title'		=> 'Administrasi Pesan Masuk',
				'judul'		=> $rj,
				'r'			=> $r,
				'nm_pasien' => $lsnmp,
				'nm_admin' => $lsnml,
				'idpesan'	=> $idpesan,
				'v'			=> 'ad_psmj'
			);
			//echo $rj[0]->judul;
			$this->site->view('v',$data);
		}

		else if($action == 'save')
		{
			$p = $this->input->post();

			$data = array(
				'idpesan'	=> $p['idpesan'],
				'isi' => $p['jawab'],
				'penjawab' => $this->session->userdata('ID'),
				'masuk_tgl' => date('Y-m-d H:i:s'),
				'status'	=> 2
			);

			$rules = $this->Ld_model->psn;
			$this->form_validation->set_rules($rules);

			if($p['id'])
			{
				if($this->form_validation->run() == FALSE)
				{
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully update, please try again later';
					$this->Ld_model->createHistory('Gagal update pesan bc',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully update';
					$this->Ld_model->createHistory('Update update pesan ',$this->session->userdata('ID'));
					$this->Ld_model->update('pesan',$data,array('id'=>$p['id']));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
			}
			else
			{
				if($this->form_validation->run() == FALSE)
				{
					
					$_SESSION['error'] = '1';
					$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully saved, please try again later';
					$this->Ld_model->createHistory('Gagal tambah pesan',$this->session->userdata('ID'));
					generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
				}
				else
				{
					$_SESSION['error'] = '';
					$_SESSION['msg']   = 'Data has been succesfully saved';
					
					$this->Ld_model->createHistory('Tambah pesan',$this->session->userdata('ID'));
					$this->Ld_model->insert('pesan',$data);
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
				$det = $this->Ld_model->getRecordList('tag','nm_tag','id=\''.$idv.'\'');
				if(!$this->Ld_model->delete($idv))
				{
					$_SESSION['error'] = '1';
                	$_SESSION['msg']   = 'Data has been <b>NOT</b> succesfully deleted, please try again later';
				}
				else
				{
					$this->Ld_model->createHistory('Delete jabatan dengan nama : '.$det[0]->nm_tag.'',$this->session->userdata('ID'));
				}
				//echo $det[0]->nm_level;
			}

			generateReturn(str_replace($host, '', $_SERVER['HTTP_REFERER']));
		}*/
	}
}