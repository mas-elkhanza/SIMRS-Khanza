<?php 
defined('BASEPATH') OR exit('No direct script accsess allowed');

class Get_model extends MY_Model{

	public $auth = array(
		'username' => array(
            'field' => 'username',
            'label' => 'Username',
            'rules' => 'trim|required'
		), 
		'password' => array(
			'field' => 'password', 
			'label' => 'Password', 
			'rules' => 'trim|required'
		)
	);

	public function __construct(){
		parent::__construct();
	}

	public function getNmUnIn(){
		$this->db->select('a.id_inventaris,b.nm_departement');
		$this->db->from('tbl_inventaris as a');		
		$this->db->join('tbl_departement as b', 'b.id_departement=a.id_dept');
		return $this->db->get()->result();
	}

	public function getJadwalDokter($hari=''){
		$this->db->select('a.kd_dokter,a.jam_mulai,a.jam_selesai,b.nm_dokter,c.nm_poli,d.tgl_awal,d.tgl_akhir,d.status');
		$this->db->from('jadwal as a');	
		if($hari)
		{
			
			$this->db->where('a.hari_kerja',$hari);	
		}
		$this->db->join('dokter as b', 'b.kd_dokter=a.kd_dokter','inner');
		$this->db->join('poliklinik as c', 'c.kd_poli=a.kd_poli','inner');
		$this->db->join('jadwal_libur as d', 'd.kd_dokter=a.kd_dokter','left');
		return $this->db->get()->result();
	}

	public function getJadwal(){
		$this->db->select('
			b.nm_dokter,
			c.nm_poli,
			GROUP_CONCAT(DISTINCT
				CONCAT(
					IF(a.hari_kerja="a.hari_kerja" , a.jam_mulai, NULL) as a.hari_kerja
				)
			)
		');
		$this->db->from('jadwal as a');	
		$this->db->group_by('a.kd_dokter');
		$this->db->join('dokter as b', 'b.kd_dokter=a.kd_dokter');
		$this->db->join('poliklinik as c', 'c.kd_poli=a.kd_poli');
		return $this->db->get()->result();
	}

	public function getCutiDokter($where=''){
		$this->db->select('a.tgl_awal,a.tgl_akhir,b.nm_dokter,d.nm_poli');
		$this->db->from('jadwal_libur as a');	
		$this->db->where($where);
		$this->db->join('dokter as b', 'b.kd_dokter=a.kd_dokter');
		$this->db->join('jadwal as c', 'c.kd_dokter=a.kd_dokter');
		$this->db->join('poliklinik as d', 'd.kd_poli=c.kd_poli');
		$this->db->group_by('a.kd_dokter');
		return $this->db->get()->result();
	}

	public function getDetailKamar($kd=''){
		$this->db->select('*');
		$this->db->from('aplicare_ketersediaan_kamar as a');	
		$this->db->where('a.kode_kelas_aplicare',$kd);	
		$this->db->join('bangsal as b', 'b.kd_bangsal=a.kd_bangsal');
		$this->db->order_by('b.nm_bangsal');
		return $this->db->get()->result();
	}

	public function getRiwayatBooking($where=''){
		
		$this->db->select('
			a.tanggal_booking,
			a.jam_booking,
			a.no_rkm_medis,
			a.tanggal_periksa,
			a.no_reg,
			a.waktu_kunjungan,
			a.kd_poli,
			a.kd_dokter,
			b.nm_poli,
			c.nm_dokter,
			d.png_jawab,
			a.status
		');

		$this->db->from('booking_registrasi as a');	
		if($where)
		{	
			$this->db->where($where);	
		}
		else
		{
			$this->db->where('a.no_rkm_medis',$this->session->userdata('no_rkm_medis'));	
		}
		$this->db->join('poliklinik as b', 'b.kd_poli=a.kd_poli');
		$this->db->join('dokter as c', 'c.kd_dokter=a.kd_dokter');
		$this->db->join('penjab as d', 'd.kd_pj=a.kd_pj');
		$this->db->order_by('a.tanggal_periksa','DESC');
		$this->db->limit(10,0);
		return $this->db->get()->result();
	}

	public function getRegPeriksa($where=''){
		
		$this->db->select('
			a.no_rawat,
			a.no_reg,
			a.no_rkm_medis,
			a.tgl_registrasi,
			a.jam_reg,
			a.kd_pj,
			a.kd_poli,
			a.kd_dokter,
			a.status_lanjut,
			b.nm_poli,
			c.nm_dokter,
			d.png_jawab,
			a.status_bayar,
			e.nm_pasien
		');

		$this->db->from('reg_periksa as a');	
		if($where)
		{	
			$this->db->where($where);	
		}
		else
		{
			$this->db->where('a.no_rkm_medis',$this->session->userdata('no_rkm_medis'));	
		}
		$this->db->join('poliklinik as b', 'b.kd_poli=a.kd_poli');
		$this->db->join('dokter as c', 'c.kd_dokter=a.kd_dokter');
		$this->db->join('penjab as d', 'd.kd_pj=a.kd_pj');
		$this->db->join('pasien as e', 'e.no_rkm_medis=a.no_rkm_medis');
		$this->db->order_by('a.tgl_registrasi','DESC');
		$this->db->limit(10,0);
		return $this->db->get()->result();
	}

	public function getAktivitas($where){
		$this->db->select('activity,activity_date');
		$this->db->from('user_account_log');	
		$this->db->where($where);	
		$this->db->order_by('id','DESC');
		$this->db->limit(5,0);
		return $this->db->get()->result();
	}

	public function getInfoBooking($where='')
	{
		$this->db->select('count(*) as jumlah,a.tanggal_periksa,a.no_reg,a.waktu_kunjungan,a.tanggal_booking,a.jam_booking,a.status,b.nm_poli,c.nm_dokter,d.png_jawab');
		$this->db->from('booking_registrasi as a');	
		$this->db->where($where);	
		$this->db->join('poliklinik as b', 'b.kd_poli=a.kd_poli');
		$this->db->join('dokter as c', 'c.kd_dokter=a.kd_dokter');
		$this->db->join('penjab as d', 'd.kd_pj=a.kd_pj');
		$this->db->limit(1);
		return $this->db->get()->result();
	}

	public function getKunjunganPoli($where='')
	{
		$this->db->select('a.kd_poli,b.nm_poli');
		$this->db->from('jadwal as a');	
		if($where)
		{
			$this->db->where($where);
		}
		$this->db->join('poliklinik as b', 'b.kd_poli=a.kd_poli');
		$this->db->group_by('a.kd_poli');
		return $this->db->get()->result();
	}

	public function getDokterPoli($where='')
	{
		$this->db->select('a.kd_dokter,a.nm_dokter');
    	$this->db->where($where);
    	$this->db->group_by('kd_dokter');
    	$this->db->order_by('kd_dokter','ASC');
    	$this->db->from('dokter as a');
		$this->db->join('jadwal as b','b.kd_dokter=a.kd_dokter');
		return $this->db->get()->result();
	}

	public function CountNotifikasi()
	{
		$this->db->select('count(*) as jumlah,a.tgl_periksa');
    	$this->db->where(array('d.no_rkm_medis'=>$this->session->userdata('no_rkm_medis'),'a.status'=>'Y','d.status'=>'Dokter Berhalangan'));
    	$this->db->from('brodcast as a');
		$this->db->join('poliklinik as b','b.kd_poli=a.kd_poli');
		$this->db->join('dokter as c','c.kd_dokter=a.kd_dokter');
		$this->db->join('booking_registrasi as d','d.kd_dokter=a.kd_dokter and d.kd_poli=a.kd_poli and d.tanggal_periksa=a.tgl_periksa');
		$this->db->join('bc_info_cuti as e','e.kd_dokter=a.kd_dokter and e.kd_poli=a.kd_poli');
		$this->db->join('jadwal_libur as f','f.id_bc_info_cuti=e.id');
		$this->db->order_by('masuk_tgl','DESC');
		$this->db->group_by('d.kd_dokter');
		$this->db->limit(1);
		$res = $this->db->get()->result();
		return $res;
	}

	public function GetNotifikasi($where)
	{
		$this->db->select('
		count(*) as jumlah,
		a.judul, 
        a.keterangan,
        a.masuk_tgl,
        a.tgl_periksa,
        a.status,
        b.nm_poli,
        c.nm_dokter,
        f.tgl_awal,
        f.tgl_akhir
		');
    	$this->db->where($where);
    	$this->db->from('brodcast as a');
		$this->db->join('poliklinik as b','b.kd_poli=a.kd_poli');
		$this->db->join('dokter as c','c.kd_dokter=a.kd_dokter');
		$this->db->join('booking_registrasi as d','d.kd_dokter=a.kd_dokter and d.kd_poli=a.kd_poli and d.tanggal_periksa=a.tgl_periksa');
		$this->db->join('bc_info_cuti as e','e.kd_dokter=a.kd_dokter and e.kd_poli=a.kd_poli');
		$this->db->join('jadwal_libur as f','f.id_bc_info_cuti=e.id');
		$this->db->order_by('masuk_tgl','DESC');
		$this->db->group_by('d.kd_dokter');
		$this->db->limit(1);
		$res = $this->db->get()->result();
		return $res;
	}

	public function GetRiwayatPemeriksaanRjDr($where='')
	{
		$this->db->select('
			a.no_rawat,
			a.no_rkm_medis,
			a.tgl_registrasi,
			b.nm_poli,
			i.nm_dokter,
			d.png_jawab,
			e.nm_pasien,
			f.kd_jenis_prw,
			g.nm_perawatan,
			h.nm_kategori
		');

		$this->db->from('reg_periksa as a');	
		if($where)
		{	
			$this->db->where($where);	
		}
		else
		{
			$this->db->where('a.no_rkm_medis',$this->session->userdata('no_rkm_medis'));	
		}
		$this->db->join('poliklinik as b', 'b.kd_poli=a.kd_poli');
		$this->db->join('penjab as d', 'd.kd_pj=a.kd_pj');
		$this->db->join('pasien as e', 'e.no_rkm_medis=a.no_rkm_medis');
		$this->db->join('rawat_jl_dr as f', 'f.no_rawat=a.no_rawat');
		$this->db->join('jns_perawatan as g', 'g.kd_jenis_prw=f.kd_jenis_prw');
		$this->db->join('kategori_perawatan as h', 'h.kd_kategori=g.kd_kategori');
		$this->db->join('dokter as i', 'i.kd_dokter=f.kd_dokter');
		$this->db->order_by('a.tgl_registrasi','DESC');
		return $this->db->get()->result();
	}

	public function GetRiwayatPemeriksaanRjPr($where='')
	{
		$this->db->select('
			a.no_rawat,
			a.no_rkm_medis,
			a.tgl_registrasi,
			b.nm_poli,
			d.png_jawab,
			e.nm_pasien,
			f.kd_jenis_prw,
			f.nip,
			g.nm_perawatan,
			h.nm_kategori
		');

		$this->db->from('reg_periksa as a');	
		if($where)
		{	
			$this->db->where($where);	
		}
		else
		{
			$this->db->where('a.no_rkm_medis',$this->session->userdata('no_rkm_medis'));	
		}
		$this->db->join('poliklinik as b', 'b.kd_poli=a.kd_poli');
		$this->db->join('penjab as d', 'd.kd_pj=a.kd_pj');
		$this->db->join('pasien as e', 'e.no_rkm_medis=a.no_rkm_medis');
		$this->db->join('rawat_jl_pr as f', 'f.no_rawat=a.no_rawat');
		$this->db->join('jns_perawatan as g', 'g.kd_jenis_prw=f.kd_jenis_prw');
		$this->db->join('kategori_perawatan as h', 'h.kd_kategori=g.kd_kategori');
		$this->db->order_by('a.tgl_registrasi','DESC');
		return $this->db->get()->result();
	}

	public function GetRiwayatPemeriksaanObat($where='')
	{
		$this->db->select('
			a.no_rawat,
			a.no_rkm_medis,
			a.tgl_registrasi,
			b.nm_poli,
			d.png_jawab,
			e.nm_pasien,
			f.kode_brng,
			f.jml,
			g.nama_brng,
			h.satuan
		');

		$this->db->from('reg_periksa as a');	
		if($where)
		{	
			$this->db->where($where);	
		}
		else
		{
			$this->db->where('a.no_rkm_medis',$this->session->userdata('no_rkm_medis'));	
		}
		$this->db->join('poliklinik as b', 'b.kd_poli=a.kd_poli');
		$this->db->join('penjab as d', 'd.kd_pj=a.kd_pj');
		$this->db->join('pasien as e', 'e.no_rkm_medis=a.no_rkm_medis');
		$this->db->join('detail_pemberian_obat as f', 'f.no_rawat=a.no_rawat');
		$this->db->join('databarang as g', 'g.kode_brng=f.kode_brng');
		$this->db->join('kodesatuan as h', 'h.kode_sat=g.kode_sat');
		$this->db->order_by('a.tgl_registrasi','DESC');
		return $this->db->get()->result();
	}

	public function getSts($date)
	{
		$this->db->select('
			b.nm_poli,
			count(*) as jumlah
		');
		$this->db->from('reg_periksa as a');
		$this->db->join('poliklinik as b', 'b.kd_poli=a.kd_poli');
		$this->db->where('a.tgl_registrasi',$date);
		$this->db->where('b.nm_poli!=','-');
		$this->db->group_by('a.kd_poli');
		$this->db->order_by('count(*)','DESC');
		$res = $this->db->get()->result_array();
		return $res;
	}

	public function getArtikel($table, $field = '', $where = '', $groupBy = '', $orderBy = '', $limit = '', $offset = NULL)
	{
		$this->db2 = $this->load->database('db_two',TRUE);

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

	public function countArtikel($table='',$where=NULL)
	{
		$this->db2 = $this->load->database('db_two',TRUE);
		if($where)
		{
			$this->db2->where($where);
		}

		$this->db2->from($table);
		return $this->db2->count_all_results();	
	}

	public function updateArtikel($table='',$data, $where=array()){
		$this->db2 = $this->load->database('db_two',TRUE);
		$this->db2->set($data);
		$this->db2->where($where);
		$this->db2->update($table);
		return TRUE;
	}

	public function getTb($date1='',$date2='',$kd_penyakit='')
	{
		$this->db->select('
			a.no_rawat,
			a.no_rkm_medis,
			a.tgl_registrasi,
			a.jam_reg,	
			b.nm_poli,
			c.nm_pasien,
			c.no_ktp,
			c.jk,
			c.tgl_lahir,
			c.alamat,
			d.kd_penyakit,
			e.hasil,
			g.nama_brng
		');

		$this->db->from('reg_periksa as a');	
		
		$this->db->where('a.tgl_registrasi >=', date('Y-m-d',strtotime($date1)));		
		$this->db->where('a.tgl_registrasi <=', date('Y-m-d',strtotime($date2)));
		if($kd_penyakit)
		{
			$this->db->where_in('d.kd_penyakit',$kd_penyakit);		
		}
		$this->db->where('d.status','Ralan');
		$this->db->join('poliklinik as b', 'b.kd_poli=a.kd_poli','inner');
		$this->db->join('pasien as c', 'c.no_rkm_medis=a.no_rkm_medis','inner');
		$this->db->join('diagnosa_pasien as d', 'd.no_rawat=a.no_rawat','left');
		$this->db->join('hasil_radiologi as e', 'e.no_rawat=a.no_rawat','left');
		$this->db->join('detail_pemberian_obat as f', 'f.no_rawat=a.no_rawat','left');
		$this->db->join('databarang as g', 'g.kode_brng=f.kode_brng','inner');
		$this->db->order_by('a.tgl_registrasi','DESC');
		//$this->db->limit(10,0);
		return $this->db->get()->result();
	}

	public function getInfoKuota1($hari=''){
		return $this->db->query('
			SELECT 
				a.hari_kerja, 
				a.kd_dokter, 
				a.kd_poli, 
				b.nm_dokter, 
				c.nm_poli,
				d.limit_reg
			FROM 
				jadwal as a 
					INNER JOIN dokter as b ON b.kd_dokter=a.kd_dokter 
					INNER JOIN poliklinik as c ON c.kd_poli=a.kd_poli 
					INNER JOIN limit_pasien_online as d ON d.kd_poli=a.kd_poli and d.kd_dokter=a.kd_dokter 
			WHERE 
				a.hari_kerja =\''.$hari.'\'
					GROUP BY a.kd_dokter,a.kd_poli
		')->result_array();
	}

	public function getInfoKuota2($tanggal=''){
		return $this->db->query('
			SELECT 
				SUM(limit_reg) as limit_reg,tanggal_periksa,kd_poli,kd_dokter
			FROM 
				booking_registrasi
			WHERE 
				tanggal_periksa=\''.$tanggal.'\' and status IN ("Belum","Terdaftar")
					GROUP BY kd_dokter,kd_poli
		')->result_array();
	}

	public function getInfoKuota3($hari=''){
		return $this->db->query('
			SELECT 
				a.hari_kerja, 
				a.kd_dokter, 
				a.kd_poli, 
				b.tanggal
			FROM 
				jadwal as a 
					INNER JOIN dokter_libur as b ON b.kd_dokter=a.kd_dokter 
			WHERE 
				a.hari_kerja =\''.$hari.'\' and tanggal!=""
					GROUP BY a.kd_dokter,a.kd_poli
		')->result_array();
	}

	public function searchInfoKuota1($hari='',$kd_poli=''){

		$where = '';
		if($kd_poli)
		{
			$where = 'and a.kd_poli=\''.$kd_poli.'\'';
		}

		return $this->db->query('
			SELECT 
				a.hari_kerja, 
				a.kd_dokter, 
				a.kd_poli, 
				b.nm_dokter, 
				c.nm_poli,
				d.limit_reg
			FROM 
				jadwal as a 
					INNER JOIN dokter as b ON b.kd_dokter=a.kd_dokter 
					INNER JOIN poliklinik as c ON c.kd_poli=a.kd_poli 
					INNER JOIN limit_pasien_online as d ON d.kd_poli=a.kd_poli and d.kd_dokter=a.kd_dokter
			WHERE 
				a.hari_kerja =\''.$hari.'\'  '.$where.'
					GROUP BY a.kd_dokter,a.kd_poli
		')->result_array();
	}

	public function searchInfoKuota2($tanggal='',$kd_poli=''){
		$where = '';
		if($kd_poli)
		{
			$where = ' and kd_poli=\''.$kd_poli.'\'';
		}
		return $this->db->query('
			SELECT 
				SUM(limit_reg) as limit_reg,tanggal_periksa,kd_poli,kd_dokter
			FROM 
				booking_registrasi
			WHERE 
				tanggal_periksa=\''.$tanggal.'\' '.$where.' and status IN ("Belum","Terdaftar")
					GROUP BY kd_dokter,kd_poli
		')->result_array();
	}

	public function searchInfoKuota3($hari='',$tanggal=''){
		return $this->db->query('
			SELECT 
				a.hari_kerja, 
				a.kd_dokter, 
				a.kd_poli, 
				b.tanggal
			FROM 
				jadwal as a 
					INNER JOIN dokter_libur as b ON b.kd_dokter=a.kd_dokter 
			WHERE 
				a.hari_kerja =\''.$hari.'\' and b.tanggal=\''.$tanggal.'\'
					GROUP BY a.kd_dokter,a.kd_poli
		')->result_array();
	}	
}