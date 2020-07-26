<?php
	include_once "conf/command.php";
	include_once "conf/conf.php";
  	if (isset($_GET['act']) && ($_GET['act']=="login")){
            $sql = "SELECT nip,usere,passwordte,type FROM user WHERE usere='".$_POST['usere']."' AND passwordte=aes_encrypt('".$_POST['passwordte']."','dianku')";
	    $hasil=bukaquery($sql);
	    $baris=mysqli_fetch_row($hasil);

            $nip            = $baris[0];
            $usere          = $baris[1];
            $passwordte     = $baris[2];
            $type           = $baris[3];

            $hasil=bukaquery($sql);
	    $baris=mysqli_fetch_row($hasil);
		if (JumlahBaris($hasil)==0) {
                        $sql2   = "SELECT pegawai.id,user.password FROM user inner join pegawai
                            on pegawai.id=user.id
                            where pegawai.nik='".$_POST['usere']."' AND 
                            user.password=aes_encrypt('".$_POST['passwordte']."','dianku')";
                        $hasil2  = bukaquery($sql2);
                        $baris2  = mysqli_fetch_row($hasil2);

                        $nip     = $baris2[0];

                        $hasil2=bukaquery($sql2);
                        $baris2=mysqli_fetch_row($hasil2);
                        if (JumlahBaris($hasil2)==0) {
                            header("Location:index.php");
                        }else{
                            session_start();
                            HapusAll(" sesion ");
                            InsertData(" sesion ","'$nip'");
                            $ses_pegawai = $hasil2[0];
                            session_register("ses_pegawai");
                            $url = "index.php?act=HomeAdmin";                            
                            header("Location:".$url);
                        }                        
			
		} else {
		     session_start();
                     HapusAll(" sesion ");
                     InsertData(" sesion ","'$nip'");
                     /*$ses_admin = $hasil[0];
				session_register("ses_admin");
				$url = "index.php?act=HomeAdmin&type=$type";*/
                     if($type=='ADMIN'){
                         $ses_admin = $hasil[0];
                         session_register("ses_admin");
                         $url = "index.php?act=HomeAdmin";
                     }
		    header("Location:".$url);
		}
            
	}

    
?>
