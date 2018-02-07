<?php
/* Indonesian Language Module for v2.3 (translated by Eko Junaidi Salam) */

global $_VERSION;

$GLOBALS["charset"] = "UTF-8";
$GLOBALS["text_dir"] = "ltr"; // ('ltr' for left ke right, 'rtl' for right ke left)
$GLOBALS["date_fmt"] = "d/m/Y H:i";
$GLOBALS["error_msg"] = array(
	// error
	"error"				=> "Kesalahan",
	"message"			=> "Pesan",
	"back"				=> "Kembali",

	// root
	"home"				=> "Direktori home tidak ada, periksa pengaturan anda kembali.",
	"abovehome"			=> "Direktori saat ini mungkin tidak berada diatas direktori home.",
	"targetabovehome"	=> "Direktori tujuan mungkin tidak berada diatas direktori home.",

	// exist
	"direxist"			=> "Direktori ini tidak ada.",
	//"filedoesexist"	=> "File ini sudah ada.",
	"fileexist"			=> "File ini tidak ada.",
	"itemdoesexist"		=> "Item ini sudah ada.",
	"itemexist"			=> "Item ini tidak ada.",
	"targetexist"		=> "Direktori tujuan tidak ada.",
	"targetdoesexist"	=> "Item tujuan sudah ada.",

	// open
	"opendir"			=> "Tidak dapat membuka direktori.",
	"readdir"			=> "Tidak dapat membaca direktori.",

	// access
	"accessdir"			=> "Anda tidak diizinkan untuk mengakses direktori ini.",
	"accessfile"		=> "Anda tidak diizinkan untuk mengakses file ini.",
	"accessitem"		=> "Anda tidak diizinkan untuk mengakses item ini.",
	"accessfunc"		=> "Anda tidak diizinkan untuk menggunakan fungsi ini.",
	"accesstarget"		=> "Anda tidak diizinkan untuk mengakses direktori tujuan.",

	// actions
	"permread"			=> "Gagal mendapatkan izin.",
	"permchange"		=> "CHMOD gagal (Biasanya disebabkan karena masalah kepemilikan file - contoh : Jika pengguna HTTP ('wwwrun' atau 'nobody') dan pengguna FTP tidak sama)",
	"openfile"			=> "Gagal membuka file.",
	"savefile"			=> "Gagal menyimpan file.",
	"createfile"		=> "Gagal membuat file.",
	"createdir"			=> "Gagal membuat direktori.",
	"uploadfile"		=> "Gagal mengunggah file.",
	"copyitem"			=> "Gagal menyalin.",
	"moveitem"			=> "Gagal memindah.",
	"delitem"			=> "Gagal menghapus.",
	"chpass"			=> "Gagal mengubah kata sandi.",
	"deluser"			=> "Gagal menghapus pengguna.",
	"adduser"			=> "Gagal menambah pengguna.",
	"saveuser"			=> "Gagal menyimpan pengguna.",
	"searchnothing"		=> "Anda harus mengisikan sesuatu untuk melakukan pencarian.",

	// misc
	"miscnofunc"		=> "Fungsi tidak tersedia.",
	"miscfilesize"		=> "File melebihi ukuran maksimal.",
	"miscfilepart"		=> "File hanya sebagian terunggah.",
	"miscnoname"		=> "Anda harus menyediakan sebuah nama.",
	"miscselitems"		=> "Anda tidak memilih item apapun.",
	"miscdelitems"		=> "Apakah anda yakin akan menghapus {0} item ini?",
	"miscdeluser"		=> "Apakah anda yakin akan menghapus pengguna '{0}'?",
	"miscnopassdiff"	=> "Kata sandi baru tidak berbeda dari kata sandi saat ini.",
	"miscnopassmatch"	=> "Kata sandi tidak cocok.",
	"miscfieldmissed"	=> "Anda melewatkan kolom yang penting.",
	"miscnouserpass"	=> "Nama pengguna atau kata sandi tidak tepat.",
	"miscselfremove"	=> "Anda tidak bisa menghapus diri sendiri.",
	"miscuserexist"		=> "Pengguna sudah ada.",
	"miscnofinduser"	=> "Tidak bisa menemukan pengguna.",
	"extract_noarchive"		=> "File bukan arsip yang bisa diekstrak.",
	"extract_unknowntype"	=> "Arsip tidak diketahui tipenya",
	
	'chmod_none_not_allowed'	=> 'Mengubah izin pada <none> tidak diperbolehkan',
	'archive_dir_notexists'		=> 'Direktori Simpan-Ke yang anda tentukan tidak ada.',
	'archive_dir_unwritable'	=> 'Silahkan tentukan direktori yang dapat ditulis untuk menyimpan arsip tersebut.',
	'archive_creation_failed'	=> 'Gagal menyimpan file arsip'

);
$GLOBALS["messages"] = array(
	// links
	"permlink"			=> "Ubah Izin",
	"editlink"			=> "Ubah",
	"downlink"			=> "Unduh",
	"uplink"			=> "Atas",
	"homelink"			=> "Beranda",
	"reloadlink"		=> "Load Kembali",
	"copylink"			=> "Menyalin",
	"movelink"			=> "Memindah",
	"dellink"			=> "Menghapus",
	"comprlink"			=> "Arsipkan",
	"adminlink"			=> "Admin",
	"logoutlink"		=> "Keluar",
	"uploadlink"		=> "Unggah",
	"searchlink"		=> "Pencarian",
	'difflink'     		=> 'Perubahan',
	"extractlink"		=> "Ekstrak Arsip",
	'chmodlink'			=> 'Ubah (chmod) hak (Folder/File)', // new mic
	'mossysinfolink'	=> 'eXtplorer System Information (eXtplorer, Server, PHP, mySQL)', // new mic
	'logolink'			=> 'Menuju eXtplorer Website (Jendela baru)', // new mic

	// list
	"nameheader"		=> "Nama",
	"sizeheader"		=> "Ukuran",
	"typeheader"		=> "Tipe",
	"modifheader"		=> "Perubahan",
	"permheader"		=> "Izin",
	"actionheader"		=> "Aksi",
	"pathheader"		=> "Lokasi",

	// buttons
	"btncancel"			=> "Batal",
	"btnsave"			=> "Simpan",
	"btnchange"			=> "Ganti",
	"btnreset"			=> "Reset",
	"btnclose"			=> "Tutup",
	"btnreopen"			=> "Buka Kembali",
	"btncreate"			=> "Buat",
	"btnsearch"			=> "Pencarian",
	"btnupload"			=> "Unggah",
	"btncopy"			=> "Salin",
	"btnmove"			=> "Pindah",
	"btnlogin"			=> "Masuk",
	"btnlogout"			=> "Keluar",
	"btnadd"			=> "Tambah",
	"btnedit"			=> "Ubah",
	"btnremove"			=> "Hapus",
	"btndiff"			=> "Perubahan",
	
	// pengguna messages, new in eXtplorer 1.3.0
	'renamelink'		=> 'Ganti nama',
	'confirm_delete_file' => 'Apakah anda yakin akan menghapus file ini? <br />%s',
	'success_delete_file' => 'Item berhasil dihapus.',
	'success_rename_file' => 'Direktori/file %s berhasil dirubah menjadi %s.',
	
	// actions
	"actdir"			=> "Direktori",
	"actperms"			=> "Ubah izin",
	"actedit"			=> "Ubah file",
	"actsearchresults"	=> "Hasil Pencarian",
	"actcopyitems"		=> "Menyalin item",
	"actcopyfrom"		=> "Menyalin dari /%s ke /%s ",
	"actmoveitems"		=> "Memindah item",
	"actmovefrom"		=> "Memindah dari /%s ke /%s ",
	"actlogin"			=> "Masuk",
	"actloginheader"	=> "Masuk untuk menggunakan eXtplorer",
	"actadmin"			=> "Administrasi",
	"actchpwd"			=> "Ubah kata sandi",
	"actusers"			=> "Pengguna",
	"actarchive"		=> "Arsipkan item",
	"actupload"			=> "Unggah file",

	// misc
	"miscitems"			=> "Item",
	"miscfree"			=> "Bebas",
	"miscusername"		=> "Nama pengguna",
	"miscpassword"		=> "Kata sandi",
	"miscoldpass"		=> "Kata sandi lama",
	"miscnewpass"		=> "Kata sandi baru",
	"miscconfpass"		=> "Memastikan kata sandi",
	"miscconfnewpass"	=> "Memastikan kata sandi baru",
	"miscchpass"		=> "Ubah kata sandi",
	"mischomedir"		=> "Direktori Home",
	"mischomeurl"		=> "Home URL",
	"miscshowhidden"	=> "Tampilkan item tersembunyi",
	"mischidepattern"	=> "Pola tersembunyi",
	"miscperms"			=> "Izin",
	"miscuseritems"		=> "(nama, direktori home, tampilkan item tersembunyi, izin, aktif)",
	"miscadduser"		=> "tambah pengguna",
	"miscedituser"		=> "ubah pengguna '%s'",
	"miscactive"		=> "Aktif",
	"misclang"			=> "Bahasa",
	"miscnoresult"		=> "Tidak ada hasil yang tersedia.",
	"miscsubdirs"		=> "Cari subdirektori",
	"miscpermnames"		=> array("Hanya melihat","Mengubah","Ubah kata sandi","Mengubah & Ubah kata sandi","Administrator"),
	"miscyesno"			=> array("Ya","Tidak","Y","T"),
	"miscchmod"			=> array("Pemilik", "Grup", "Publik"),
	'misccontent'		=> "Konten file",

	// from here all new by mic
	'miscowner'			=> 'Pemilik',
	'miscownerdesc'		=> '<strong>Deskripsi:</strong><br />Pengguna (UID) /<br />Grup (GID)<br />Hak saat ini:<br /><strong> %s ( %s ) </strong>/<br /><strong> %s ( %s )</strong>',

	// sysinfo (new by mic)
	'simamsysinfo'		=> "eXtplorer System Info",
	'sisysteminfo'		=> 'System Info',
	'sibuilton'			=> 'Operating System',
	'sidbversion'		=> 'Database Version (MySQL)',
	'siphpversion'		=> 'PHP Version',
	'siphpupdate'		=> 'INFORMATION: <span style="color: red;">The PHP version you use is <strong>not</strong> actual!</span><br />To guarantee all functions and features of Mambo and addons,<br />you should use as minimum <strong>PHP.Version 4.3</strong>!',
	'siwebserver'		=> 'Webserver',
	'siwebsphpif'		=> 'WebServer - PHP Interface',
	'simamboversion'	=> 'eXtplorer Version',
	'siuseragent'		=> 'Browser Version',
	'sirelevantsettings'	=> 'Important PHP Settings',
	'sisafemode'		=> 'Safe Mode',
	'sibasedir'			=> 'Open basedir',
	'sidisplayerrors'	=> 'PHP Errors',
	'sishortopentags'	=> 'Short Open Tags',
	'sifileuploads'		=> 'File Uploads',
	'simagicquotes'		=> 'Magic Quotes',
	'siregglobals'		=> 'Register Globals',
	'sioutputbuf'		=> 'Output Buffer',
	'sisesssavepath'	=> 'Session Savepath',
	'sisessautostart'	=> 'Session auto start',
	'sixmlenabled'		=> 'XML enabled',
	'sizlibenabled'		=> 'ZLIB enabled',
	'sidisabledfuncs'	=> 'Disabled functions',
	'sieditor'			=> 'WYSIWYG Editor',
	'siconfigfile'		=> 'Config file',
	'siphpinfo'			=> 'PHP Info',
	'siphpinformation'	=> 'PHP Information',
	'sipermissions'		=> 'Permissions',
	'sidirperms'		=> 'Direktori permissions',
	'sidirpermsmess'	=> 'To be shure that all functions and features of eXtplorer are working correct, following folders should have permission to write [chmod 0777]',
	'sionoff'			=> array( 'On', 'Off' ),
	
	'extract_warning'	=> "Apakah anda yakin akan mengekstrak file ini? Disini?<br />Ini akan menimpa file yang sudah ada bila tidak digunakan dengan berhati-hati!",
	'extract_success'	=> "Extrak berhasil",
	'extract_failure'	=> "Extrak gagal",
	
	'overwrite_files'	=> 'Menimpa file yang sudah ada?',
	"viewlink"			=> "Lihat",
	"actview"			=> "Menampilkan sumber file",
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_chmod.php file
	'recurse_subdirs'	=> 'Recurse ke subdirektori?',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to footer.php file
	'check_version'		=> 'Periksa versi yang terakhir',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_rename.php file
	'rename_file'		=>	'Mengubah nama direktori atau file...',
	'newname'			=>	'Nama baru',
	
	// added by Paulino Michelazzo (paulino@michelazzo.com.br) to fun_edit.php file
	'returndir'			=>	'Kembali ke direktori setelah disimpan?',
	'line'				=> 	'Baris',
	'column'			=>	'Kolom',
	'wordwrap'			=>	'Wordwrap: (IE only)',
	'copyfile'			=>	'Menyalin file ke nama file berikut',
	
	// Bookmarks
	'quick_jump' 		=> 'Jalan pintas ke',
	'already_bookmarked' => 'Direktori ini sudah di bookmark',
	'bookmark_was_added' => 'Direktori ini sudah ditambahkan pada daftar bookmark',
	'not_a_bookmark'	=> 'Direktori ini bukan bookmark.',
	'bookmark_was_removed' => 'Direktori ini telah dihapus pada daftar bookmark.',
	'bookmarkfile_not_writable' => "Gagal untuk membookmark %s.\n File bookmark '%s' \ntidak bisa ditulis.",
	
	'lbl_add_bookmark'	=> 'Tambahkan direktori ini sebagai Bookmark',
	'lbl_remove_bookmark' => 'Hapus direktori ini dari Daftar Bookmark',
	
	'enter_alias_name'	=> 'Silahkan masukkan nama alias untuk bookmark ini',
	
	'normal_compression' => 'normal compression',
	'good_compression'	=> 'good compression',
	'best_compression'	=> 'best compression',
	'no_compression'	=> 'no compression',
	
	'creating_archive'	=> 'Membuat file arsip...',
	'processed_x_files'	=> 'Memproses %s dari %s file',
	
	'ftp_header'		=> 'Otentikasi FTP Lokal',
	'ftp_login_lbl'		=> 'Silahkan masukkan login pada Server FTP',
	'ftp_login_name'	=> 'Nama Pengguna FTP',
	'ftp_login_pass'	=> 'Kata sandi FTP',
	'ftp_hostname_port'	=> 'FTP Server Hostname dan Port <br />(Port opsional)',
	'ftp_login_check'	=> 'Pengecekan koneksi FTP...',
	'ftp_connection_failed' => "Server FTP tidak dapat dikontak. \nSilahkan periksa Server FTP apakah telah berjalan di server anda.",
	'ftp_login_failed'	=> "Login FTP gagal. Silahkan periksa nama pengguna dan kata sandi dan coba kembali.",
		
	'switch_file_mode'	=> 'Mode saat ini: <strong>%s</strong>. Anda dapat mengubah ke mode %s.',
	'symlink_target'	=> 'Target dari Symbolic Link',
	
	"permchange"		=> "CHMOD Sukses:",
	"savefile"			=> "File telah disimpan.",
	"moveitem"			=> "Sukses memindah.",
	"copyitem"			=> "Sukses menyalin.",
	'archive_name'	 	=> 'Nama dari file arsip',
	'archive_saveToDir'	=> 'Simpan arsip di direktori ini',
	
	'editor_simple'		=> 'Simple Editor Mode',
	'editor_syntaxhighlight'	=> 'Syntax-Highlighted Mode',

	'newlink'			=> 'File/Direktori baru',
	'show_directories'	=> 'Tampilkan Direktori',
	'actlogin_success'	=> 'Login berhasil!',
	'actlogin_failure'	=> 'Login gagal, coba kembali.',
	'directory_tree'	=> 'Directory Tree',
	'browsing_directory'	=> 'Direktori Browsing',
	'filter_grid'		=> 'Saring',
	'paging_page'		=> 'Halaman',
	'paging_of_X'		=> 'dari {0}',
	'paging_firstpage'	=> 'Halaman Pertama',
	'paging_lastpage'	=> 'Halaman Terakhir',
	'paging_nextpage'	=> 'Halaman Selanjutnya',
	'paging_prevpage'	=> 'Halaman Sebelumnya',
	
	'paging_info'		=> 'Menampilkan item {0} - {1} dari {2}',
	'paging_noitems'	=> 'Tidak ada item yang bisa ditampilkan',
	'aboutlink'			=> 'Tentang...',
	'password_warning_title'	=> 'Penting - Ubah kata sandi anda!',
	'password_warning_text'		=> 'Akun pengguna anda masuk dengan (admin dengan kata sandi admin) sama dengan akun default eXtplorer. Instalasi eXtplorer anda membuka peluang gangguan dan anda harus segera memperbaiki celah keamanan ini!',
	'change_password_success'	=> 'Kata sandi anda telah dirubah!',
	'success'			=> 'Sukses',
	'failure'			=> 'Gagal',
	'dialog_title'		=> 'Website Dialog',
	'upload_processing'	=> 'Proses Unggah, tolong tunggu...',
	'upload_completed'	=> 'Unggah sukses!',
	'acttransfer'		=> 'Transfer dari Server lain',
	'transfer_processing'	=> 'Proses Transfer Server-to-Server, please wait...',
	'transfer_completed'	=> 'Transfer komplit!',
	'max_file_size'		=> 'Ukuran file maksimal',
	'max_post_size'		=> 'Batas Unggah maksimal',
	'done'				=> 'Selesai.',
	'permissions_processing' => 'Menerapkan izin, tolong tunggu...',
	'archive_created'	=> 'Arsip file telah dibuat!',
	'save_processing'	=> 'Menyimpan file...',
	'current_user'		=> 'Script ini berjalan dengan izin pengguna berikut:',
	'your_version'		=> 'Versi Anda',
	'search_processing'	=> 'Proses Pencarian, tolong tunggu...',
	'url_to_file'		=> 'URL File',
	'file'				=> 'File'
	
);
?>
