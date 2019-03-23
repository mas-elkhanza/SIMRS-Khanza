<?php  

	class SConfig{
		
		// Config Database
		var $_hostname = 'localhost';
		var $_database = 'sik';
		var $_username = 'root';
		var $_password = '';
		var $_dbprefix = 'tbl_';

		var $_hostname2 = 'localhost';
		var $_database2 = 'sik';
		var $_username2 = 'root';
		var $_password2 = '';

		// Other
		var $_site_url = 'http://localhost/k-base';
		var $_document_root = '/opt/lampp/htdocs/webapps/k-base';
		var $_cms_name = 'Sistem Informasi Laporan';
		var $_sitename = 'Hallo - IT';
		var $_limit      = array(10 => 10, 20 => 20, 50 => 50, 100 => 100, 150 => 150, 200 => 200, 500 => 500);

		var $_jenis = array(
			1 => 'Kategori Panduan'
		);

		var $_status = array(
			'Y' => 'Aktif',
			'T'	=> 'Tidak Aktif'
		);

		var $_st_post = array(
			'Y' => 'Publish',
			'P' => 'Pending',
			'C' => 'Cancel'
		);

		var $_status_booking = array(
			'belum' => 'Booking',
			'terdaftar' => 'Terdaftar',
			'batal' => 'Batal',
			'cuti' => 'Dokter Cuti Mendadak'
		);
	}

?>
