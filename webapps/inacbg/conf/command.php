<?php
	function title(){
 		$judul ="Bridging INACBG --)(*!!@#$%";
		$judul = preg_replace("[^A-Za-z0-9_\-\./,|]"," ",$judul);
		$judul = str_replace(array('.','-','/',',')," ",$judul);
		$judul = trim($judul);
		echo "$judul";	
 	}
 
	function cekSessiAdmin() {
            if (isset($_SESSION['ses_admin'])) {
                return true;
            } else {
                return false;
            }
        }


        function cekUser() {
            if (isset($_SESSION['ses_admin'])) {
                return true;
            } else {
                return false;
            }
        }
	
	function adminAktif() {
            if (cekSessiAdmin()) {
                return $_SESSION['ses_admin'];
            }
        }

        
    
	function isGuest() {
            if (cekSessiAdmin()) {
                return false;
            } else {
                return true;
            }
        }	
		
	
	function formProtek() {
                $aksi=isset($_GET['act'])?$_GET['act']:NULL;
		if (!cekUser()) {
                    $form = array ('HomeAdmin','KlaimBaruOtomatis','KlaimBaruManual','KlaimBaruManual2','DetailKirim');
                        foreach ($form as $page) {
                            if ($aksi==$page) {
                                echo "<META HTTP-EQUIV = 'Refresh' Content = '0; URL = ?act=Home'>";
                                exit;
                                break;
                            }
                        }
                }		
		
	}
	
	function actionPages() {
            $aksi=isset($_REQUEST['act'])?$_REQUEST['act']:NULL;
		formProtek();
		switch ($aksi) {
                    case 'HomeAdmin'            : include_once('pages/kontak.php'); break;
                    case 'KlaimBaruOtomatis'    : include_once('pages/klaimbaruotomatis.php'); break;
                    case 'KlaimBaruManual'      : include_once('pages/klaimbarumanual.php'); break;
                    case 'KlaimBaruManual2'     : include_once('pages/klaimbarumanual2.php'); break;
                    case 'DetailKirim'          : include_once('pages/detailkirim.php'); break;

                    default                     : include_once('pages/kontak.php');
			
		}
	}
	
	
	 
 
?>
