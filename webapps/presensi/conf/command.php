<?php
	function title(){
 		$judul ="Presensi Pegawai  --)(*!!@#$%";
		$judul = preg_replace("[^A-Za-z0-9_\-\./,|]"," ",$judul);
		$judul = str_replace(array('.','-','/',',')," ",$judul);
		$judul = trim($judul);
		echo "$judul";	
 	}      
         

 	function cekSessiKunjung() {
		if (isset($_SESSION['ses_kunjung']))
			return true;
		else
			return false;
	}

	function cekSessiAdmin() {
		if (isset($_SESSION['ses_admin']))
			return true;
		else
			return false;
	}

        function cekSessiKelas() {
		if (isset($_SESSION['ses_pegawai']))
			return true;
		else
			return false;
	}
        
        function cekUser() {
		if (isset($_SESSION['ses_admin']))
			return true;
		elseif (isset($_SESSION['ses_pegawai']))
			return true;
		else
			return false;
	}
	
	function kunjungAktif() {
		if (cekSessiPakar()) return $_SESSION['ses_kunjung'];
	}

	function adminAktif() {
		if (cekSessiAdmin()) return $_SESSION['ses_admin'];
	}

        function pegawaiAktif() {
		if (cekSessiKelas()) return $_SESSION['ses_pegawai'];
	}
	
	function isGuest() {
		if (cekSessiKunjung()|| cekSessiAdmin()) return false;
		else return true;
	}
	
	
	function samping()
	{
            
		if (cekSessiAdmin()):
			echo "<br/>  <h2>.:: Menu &nbsp&nbsp;&nbsp;&nbsp&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;::.</h2>
                                 <p>
                                 <ul class=\"left_menu\">
                                        <li class=\"odd\"><a href='index.php?act=ListPjawab'>P.J. Laboratorium </a></li>
                                        <li class=\"even\"><a href='index.php?act=ListAnalis'>Analis Laboratorium </a></li>
					<li class=\"odd\"><a href='index.php?act=ListPasien'>Data Pasien</a></li>
                                        <li class=\"even\"><a href='index.php?act=ListSemen'>Data Analisa Semen </a></li>
                                        <li class=\"odd\"><a href='index.php?act=ListHormon'>Data Analisa Hormon </a></li>
                                        <li class=\"even\"><a href='index.php?act=ListFragmen'>Data Analisa Fragmentasi </a></li>
				  </ul>
                                  </p>
                               ";
		elseif(isGuest()):
			menuLogin();
			//calender();
                        //kategori();

		endif;	
	}
	
	function bawah() 
	{
	  echo	"<p id=\"legal\">Copyright &copy; KhanzaSoft Media. All Rights Reserved. Design by Khanza.Soft Media</p>
                ";
	}
 
 	function tampilMenu() {
		if (cekSessiKunjung()) {
			$menu = array(
				'Home'			=> '?act=HomeKunjung',
				'Data Komentar'		=> '?act=DataKomentar',
				'Sign Out'		=> 'logout.php');
		} elseif (cekSessiAdmin()) {
			$menu = array(
				'Input Data'            => 'index.php?page=Input',
	                        'Presensi Datang'	=> 'index.php?page=TampilDatang',
                                'Presensi Pulang'       => 'index.php?page=TampilPulang',
                                'Cari Presensi'       => 'index.php?page=Cari');
		}elseif (cekSessiKelas ()) {
			$menu = array(
                                'About Program'	        => '?act=HomeAdmin',
                                'Set Tahun & Bulan'	=> '?act=InputTahun&action=TAMBAH',
				'Sign Out'		=> 'logout.php');
		} else {
			$menu = array(
				'Input Data'            => 'index.php?page=Input',
	                        'Presensi Datang'	=> 'index.php?page=TampilDatang',
                                'Presensi Pulang'       => 'index.php?page=TampilPulang',
                                'Cari Presensi'       => 'index.php?page=Cari');
		}		
		echo "<ul id=\"navlist\">";
		$i=0;
		foreach ($menu as $key => $val) {
			$i++;
			if ($key=='Sign Out')	$klik = "onclick=\"return confirm('Yakinkah anda akan logout.?');\""; 
			if (isGuest()) {
				if ($i == 5) $last = "id='current'";
			} else {
				if ($i == 5) $last = "id='current'";
			}
			echo "<li title='$key'><a href='$val' >$key</a></li>";
		}
		echo "</ul>";
	} 	
	
	
	function ListArtikel()
	{
		$x= bukaquery("SELECT * FROM artikel where page='artikel' ORDER BY id DESC LIMIT 4");
		while($row=mysqli_fetch_array($x))
		{
		  $judul=$row['1'];
		  $isi  = substr($row['1'],0,160);
		  $post =konversiTanggal((substr($row[4],0,10)));  	
		  echo "<ul><li><b>$judul</b><br />
					<small>posted on $post</small><br/>";
		  echo	"$isi<a href=\"index.php?act=News&id=$row[0]\">...detail</a>";	  	
		  echo "</li></ul>";	
		} 
	}
		
	function calender() {
		echo "  
                        <h2>.: KALENDER :.</h2>                     
                        <p>";
		include_once "include/calender.php";
		echo "    
                        </p>
                        <br>
                      ";
	}

        function kategori() {
		echo "
                        <h2>.: KATEGORI :.</h2>                      
                      <p>
                          ";
		include_once "pages/subside.php";
		echo "    
                       </p>";
	}
	
	
	function formProtek() {
		if (!cekUser()) {
			$form = array ('HomeAdmin','InputDataAdmin','InputPasien','ListPasien','ListPjawab','InputPjawab',
                                       'ListAnalis','InputAnalis','DetailSemen','ListSemen','ListHormon','DetailHormon',
                                       'ListFragmen','DetailFragmen');
				foreach ($form as $page) {
					if ($_GET['act']==$page) {
						echo "<META HTTP-EQUIV = 'Refresh' Content = '0; URL = ?act=Home'>";
						exit;
						break;
					}
				}
			}
			
		
	}
	
	function actionPages() {
		formProtek();
		switch ($_REQUEST['act']) {
			case 'Kontak'		  	: include_once('pages/kontak.php'); break;
                        
                        case 'HomeAdmin'		: include_once('pages/admin.php'); break;
			case 'InputDataAdmin'		: include_once('pages/aturadmin.php'); break;
			case 'ListArtikel'		: include_once('pages/listartikel.php'); break;
			case 'InputArtikel'		: include_once('pages/inputartikel.php'); break;
                        
                        case 'ListPasien'               : include_once('pages/pasien/listpasien.php'); break;
			case 'InputPasien'              : include_once('pages/pasien/inputpasien.php'); break;
                        
			case 'DetailSemen'              : include_once('pages/semen/detailsemen.php'); break;
			case 'ListSemen'                : include_once('pages/semen/listsemen.php'); break;
                        
                        case 'ListPjawab'               : include_once('pages/pjawab/listpjawab.php'); break;
			case 'InputPjawab'              : include_once('pages/pjawab/inputpjawab.php'); break;
                        
                        case 'ListAnalis'               : include_once('pages/analis/listanalis.php'); break;
			case 'InputAnalis'              : include_once('pages/analis/inputanalis.php'); break;
                    
                        case 'ListHormon'               : include_once('pages/hormon/listhormon.php'); break;
			case 'DetailHormon'             : include_once('pages/hormon/detailhormon.php'); break;
                    
                        case 'ListFragmen'              : include_once('pages/fragmen/listfragmen.php'); break;
			case 'DetailFragmen'            : include_once('pages/fragmen/detailfragmen.php'); break;
                        
                        
                        default			          : include_once('pages/home.php');
			
		}
	}
	
	
	 
 function menuLogin(){
 
 	echo "  <br>
                <h2>.: LOGIN ADMIN :.</h2>
              
              <p>
              
	     <form name=\"form\" action=\"login.php?act=login\"  method='post'  onSubmit=\"return validasiLogin();\">
		<table width='100%' align='center' cellpadding='0' cellspacing='0'>
			<tr class=head2>
                            <td>&nbsp;User&nbsp;</td>
                            <td><input class=\"teks_input\" type=\"password\"  size=\"16\" value=\"$_GET[usere]\" id=\"TxtUser\" name=\"usere\" title=\"Masukkan username kamu...\" onKeyDown=\"setDefault(this, document.getElementById('MsgUser'));\" />
				 <span id=\"MsgUser\" style=\"color:#CC0000; font-size:10px;\"></span>
			    </td>
                        </tr>
			<tr class=head2>
                            <td>&nbsp;Password&nbsp;</td>
                            <td><input class=\"teks_input\" type=\"password\" size=\"16\"value=\"$_GET[passwordte]\" id=\"TxtPassword\" name=\"passwordte\" title=\"Masukkan password kamu...\" onKeyDown=\"setDefault(this, document.getElementById('MsgPassword'));\" />
				 <span id=\"MsgPassword\" style=\"color:#CC0000; font-size:10px;\"></span>
			    </td>
                        </tr>
			<tr>
                            <td colspan=2 align=center>
                                <input class='button' name=\"BtnOk\" value='Log-in' type='submit'/>
                                <input class='button' type='reset' name=\"BtnReset\" value='Reset'/>
                            </td>
                        </tr>
		</table>
	    </form>
            </p>
            <br>
            ";
 }	
?>