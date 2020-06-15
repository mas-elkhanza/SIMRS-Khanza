<?php
	function title(){
 		$judul ="SIMKES Khanza --)(*!!@#$%";
		$judul = preg_replace("[^A-Za-z0-9_\-\./,|]"," ",$judul);
		$judul = str_replace(array('.','-','/',',')," ",$judul);
		$judul = trim($judul);
		echo "$judul";	
 	}      
         

 	function cekSessiKunjung() {
		if (session_is_registered('ses_kunjung'))
			return true;
		else
			return false;
	}

	function cekSessiAdmin() {
		if (session_is_registered('ses_admin'))
			return true;
		else
			return false;
	}

        function cekSessiKelas() {
		if (session_is_registered('ses_billing'))
			return true;
		else
			return false;
	}
        
        function cekUser() {
		if (session_is_registered('ses_admin'))
			return true;
		elseif (session_is_registered('ses_billing'))
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

        function billingAktif() {
		if (cekSessiKelas()) return $_SESSION['ses_billing'];
	}
	
	function isGuest() {
		if (cekSessiKunjung()|| cekSessiAdmin()) return false;
		else return true;
	}
	
	
	function samping()
	{
            
		if (cekSessiAdmin()):
			echo "<br/>  <h2><>> Billing </h2>
                                 <p>
                                 <ul class=\"left_menu\">
                                        <li class=\"odd\"><a href='index.php?act=ListPasien'>-> Pasien Umum</a></li>
                                        <li class=\"even\"><a href='index.php?act=ListReg'>-> Registrasi Pasien Umum</a></li>
					<li class=\"odd\"><a href='index.php?act=ListBilling'>-> Billing Pasien</a></li>
					<li class=\"even\"><a href='index.php?act=ListBillingIbu'>-> Billing Pasien Ibu</a></li>
                                        <li class=\"odd\"><a href='index.php?act=ListBillingBayi'>-> Billing Bayi</a></li>
				  </ul>
                                  </p>
                               ";
                elseif (cekSessiKelas()):
                        
                         
                            echo" 
                                        <li class=\"even\"><a href='index.php?act=ListKepribadian'>Kepribadian</a></li>
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
	  echo	"<p id=\"legal\">Copyright &copy; 2012, RS Kharisma Paramedika Kulon Progo. All Rights Reserved. Design by Khanza.Soft Media</p>
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
				'Informasi'		=> '?act=Home',
                                'About Program'	        => '?act=HomeAdmin',
				'Sign Out'		=> 'logout.php');
		}elseif (cekSessiKelas ()) {
			$menu = array(
                                'About Program'	        => '?act=HomeAdmin',
                                'Set Tahun & Bulan'	=> '?act=InputTahun&action=TAMBAH',
				'Sign Out'		=> 'logout.php');
		} else {
			$menu = array(
				'Informasi'             => 'index.php?act=Home',
	                        'About Program'		=> 'index.php?act=Kontak');
		}		
		echo "<ul id=\"navlist\">";
		$i=0;
		foreach ($menu as $key => $val) {
			$i++;
			if ($key=='Sign Out')	$klik = "onclick=\"return confirm('Yakinkah anda akan logout.?');\""; 
			if (isGuest()) {
				if ($i == 1) $last = "class='current_page_item'";
			} else {
				if ($i == 1) $last = "class='current_page_item'";
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
			$form = array ('HomeAdmin','ListBilling','ListBillingBayi','ListBillingIbu','ListPasien',
                                       'ListReg','InputPasien','InputArtikel','InputDataAdmin','ListArtikel');
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
                        
                        case 'ListBilling'		: include_once('pages/billing/listbilling.php'); break;
			case 'InputBilling'		: include_once('pages/billing/inputbilling.php'); break;                       
                        case 'ListBillingIbu'		: include_once('pages/billing/listbillingibu.php'); break;                  
                        case 'ListBillingBayi'		: include_once('pages/billing/listbillingbayi.php'); break;                
                        case 'ListPasien'		: include_once('pages/pasien/listpasien.php'); break;               
                        case 'InputPasien'		: include_once('pages/pasien/inputpasien.php'); break;              
                        case 'ListReg'  		: include_once('pages/registrasi/listreg.php'); break;             
                        case 'DetailReg'  		: include_once('pages/registrasi/detailreg.php'); break;
                        
                        case 'HomeAdmin'		: include_once('pages/admin.php'); break;			
			case 'InputArtikel'		: include_once('pages/inputartikel.php'); break;
			case 'InputDataAdmin'		: include_once('pages/aturadmin.php'); break;	
			case 'ListArtikel'		: include_once('pages/listartikel.php'); break;

                        default			          : include_once('pages/home.php');
			
		}
	}
	
	
	 
 function menuLogin(){
 
 	echo "  <br>
                <h2>.: Login Admin :.</h2>
              
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