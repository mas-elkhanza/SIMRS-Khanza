<?php 
    if(isset($_SESSION["ses_pasien"])){
        $halaman                            = isset($_GET["hal"])?$_GET["hal"]:NULL;
        $subhalaman                         = isset($_GET["act"])?$_GET["act"]:NULL;
        if(!isset($_SESSION["nm_pasien"])){
            $queryuser                      = @bukaquery2("select pasien.nm_pasien,pasien.email,pasien.jk,personal_pasien.gambar,pasien.no_tlp,pasien.no_peserta,pasien.no_ktp,pasien.tmp_lahir,date_format(pasien.tgl_lahir,'%d/%m/%Y') as tgl_lahir from pasien inner join personal_pasien on personal_pasien.no_rkm_medis=pasien.no_rkm_medis where pasien.no_rkm_medis='".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."'");
            while($rsqueryuser = mysqli_fetch_array($queryuser)) {
                $_SESSION["nm_pasien"]      = $rsqueryuser["nm_pasien"];
                $_SESSION["email"]          = $rsqueryuser["email"];
                $_SESSION["jk"]             = $rsqueryuser["jk"];
                $_SESSION["no_tlp"]         = $rsqueryuser["no_tlp"];
                $_SESSION["no_peserta"]     = $rsqueryuser["no_peserta"];
                $_SESSION["no_ktp"]         = $rsqueryuser["no_ktp"];
                $_SESSION["tmp_lahir"]      = $rsqueryuser["tmp_lahir"];
                $_SESSION["tgl_lahir"]      = $rsqueryuser["tgl_lahir"];
                $_SESSION["photo"]          = "";
                if(($rsqueryuser["gambar"]=="")||($rsqueryuser["gambar"]=="-")){
                    if($rsqueryuser["jk"]=="L"){
                        $_SESSION["photo"]  = "images/userlaki.png";
                    }else{
                        $_SESSION["photo"]  = "images/userperempuan.png";
                    }
                }else{
                    $_SESSION["photo"]      = "http://".host()."/webapps/photopasien/".$rsqueryuser["gambar"];
                }
            }
        }
    }else{
        JSRedirect("index.php?act=Home");
    }
?>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="robots" content="noindex,nofollow">
    <meta http-equiv="X-UA-Compatible" content="IE=Edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <title>Selamat Datang di EPasien <?=$_SESSION["nama_instansi"];?></title>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
    <link href="css/style3.css" rel="stylesheet" type="text/css">
    <link href="css/style4.css" rel="stylesheet" type="text/css">
    <link href="plugins/bootstrap/css/bootstrap.css" rel="stylesheet">
    <link href="plugins/node-waves/waves.css" rel="stylesheet" />
    <link href="plugins/animate-css/animate.css" rel="stylesheet" />
    <link href="plugins/jquery-datatable/skin/bootstrap/css/dataTables.bootstrap.css" rel="stylesheet">
    <link href="plugins/bootstrap-select/css/bootstrap-select.css" rel="stylesheet" />
    <link href="css/style2.css" rel="stylesheet">
    <link href="css/themes/all-themes.css" rel="stylesheet" />
</head>
<body class="theme-pink">
    <!--<div class="page-loader-wrapper">
        <div class="loader">
            <div class="preloader">
                <div class="spinner-layer pl-red">
                    <div class="circle-clipper left">
                        <div class="circle"></div>
                    </div>
                    <div class="circle-clipper right">
                        <div class="circle"></div>
                    </div>
                </div>
            </div>
            <p>Silahkan tunggu.....!</p>
        </div>
    </div>-->
    <div class="overlay"></div>
    <div class="search-bar">
        <div class="search-icon">
            <i class="material-icons">search</i>
        </div>
        <input type="text" placeholder="Mulai Menulis...">
        <div class="close-search">
            <i class="material-icons">close</i>
        </div>
    </div>
    <nav class="navbar">
        <div class="container-fluid">
            <div class="navbar-header">
                <a href="javascript:void(0);" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse" aria-expanded="false"></a>
                <a href="javascript:void(0);" class="bars"></a>
                <a class="navbar-brand" href="index.php?act=HomeUser">E-Pasien <?=$_SESSION["nama_instansi"];?></a>
            </div>
            <div class="collapse navbar-collapse" id="navbar-collapse">
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="javascript:void(0);" class="js-search" data-close="true"><i class="material-icons">search</i></a></li>
                    <li class="dropdown">
                        <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown" role="button">
                            <i class="material-icons">notifications</i>
                            <span class="label-count">7</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">NOTIFICATIONS</li>
                            <li class="body">
                                <ul class="menu">
                                    <li>
                                        <a href="javascript:void(0);">
                                            <div class="icon-circle bg-light-green">
                                                <i class="material-icons">person_add</i>
                                            </div>
                                            <div class="menu-info">
                                                <h4>12 new members joined</h4>
                                                <p>
                                                    <i class="material-icons">access_time</i> 14 mins ago
                                                </p>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0);">
                                            <div class="icon-circle bg-cyan">
                                                <i class="material-icons">add_shopping_cart</i>
                                            </div>
                                            <div class="menu-info">
                                                <h4>4 sales made</h4>
                                                <p>
                                                    <i class="material-icons">access_time</i> 22 mins ago
                                                </p>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0);">
                                            <div class="icon-circle bg-red">
                                                <i class="material-icons">delete_forever</i>
                                            </div>
                                            <div class="menu-info">
                                                <h4><b>Nancy Doe</b> deleted account</h4>
                                                <p>
                                                    <i class="material-icons">access_time</i> 3 hours ago
                                                </p>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0);">
                                            <div class="icon-circle bg-orange">
                                                <i class="material-icons">mode_edit</i>
                                            </div>
                                            <div class="menu-info">
                                                <h4><b>Nancy</b> changed name</h4>
                                                <p>
                                                    <i class="material-icons">access_time</i> 2 hours ago
                                                </p>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0);">
                                            <div class="icon-circle bg-blue-grey">
                                                <i class="material-icons">comment</i>
                                            </div>
                                            <div class="menu-info">
                                                <h4><b>John</b> commented your post</h4>
                                                <p>
                                                    <i class="material-icons">access_time</i> 4 hours ago
                                                </p>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0);">
                                            <div class="icon-circle bg-light-green">
                                                <i class="material-icons">cached</i>
                                            </div>
                                            <div class="menu-info">
                                                <h4><b>John</b> updated status</h4>
                                                <p>
                                                    <i class="material-icons">access_time</i> 3 hours ago
                                                </p>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0);">
                                            <div class="icon-circle bg-purple">
                                                <i class="material-icons">settings</i>
                                            </div>
                                            <div class="menu-info">
                                                <h4>Settings updated</h4>
                                                <p>
                                                    <i class="material-icons">access_time</i> Yesterday
                                                </p>
                                            </div>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="footer">
                                <a href="javascript:void(0);">View All Notifications</a>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a href="javascript:void(0);" class="dropdown-toggle" data-toggle="dropdown" role="button">
                            <i class="material-icons">flag</i>
                            <span class="label-count">9</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">TASKS</li>
                            <li class="body">
                                <ul class="menu tasks">
                                    <li>
                                        <a href="javascript:void(0);">
                                            <h4>
                                                Footer display issue
                                                <small>32%</small>
                                            </h4>
                                            <div class="progress">
                                                <div class="progress-bar bg-pink" role="progressbar" aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" style="width: 32%">
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0);">
                                            <h4>
                                                Make new buttons
                                                <small>45%</small>
                                            </h4>
                                            <div class="progress">
                                                <div class="progress-bar bg-cyan" role="progressbar" aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" style="width: 45%">
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0);">
                                            <h4>
                                                Create new dashboard
                                                <small>54%</small>
                                            </h4>
                                            <div class="progress">
                                                <div class="progress-bar bg-teal" role="progressbar" aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" style="width: 54%">
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0);">
                                            <h4>
                                                Solve transition issue
                                                <small>65%</small>
                                            </h4>
                                            <div class="progress">
                                                <div class="progress-bar bg-orange" role="progressbar" aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" style="width: 65%">
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:void(0);">
                                            <h4>
                                                Answer GitHub questions
                                                <small>92%</small>
                                            </h4>
                                            <div class="progress">
                                                <div class="progress-bar bg-purple" role="progressbar" aria-valuenow="85" aria-valuemin="0" aria-valuemax="100" style="width: 92%">
                                                </div>
                                            </div>
                                        </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="footer">
                                <a href="javascript:void(0);">View All Tasks</a>
                            </li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <section>
        <!-- Left Sidebar -->
        <aside id="leftsidebar" class="sidebar">
            <div class="user-info">
                <div class="image">
                    <img src="<?='data: '.@mime_content_type($_SESSION["photo"]).';base64,'.base64_encode(file_get_contents($_SESSION["photo"]));?>" width="55" height="48" alt="Photo" />&nbsp;&nbsp;&nbsp;&nbsp;<b><font color="#DDFF55">No.RM : <?=encrypt_decrypt($_SESSION["ses_pasien"],"d");?></font></b>
                </div>
                <div class="info-container">
                    <div class="name" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?=$_SESSION["nm_pasien"];?></div>
                    <div class="email"><?=$_SESSION["email"];?></div>
                    <div class="btn-group user-helper-dropdown">
                        <i class="material-icons" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">keyboard_arrow_down</i>
                        <ul class="dropdown-menu pull-right">
                            <li><a href="index.php?act=UpdatePassword"><i class="material-icons">person</i>Ubah Password</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="pages/logout.php"><i class="material-icons">input</i>Log Out</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="menu">
                <ul class="list">
                    <li class="header">MENU UTAMA</li>
                    <li <?=$halaman=="Beranda"?"class='active'":""?>>
                        <a href="index.php?act=HomeUser&hal=Beranda">
                            <i class="material-icons">home</i>
                            <span>Beranda</span>
                        </a>
                    </li>
                    <li <?=$halaman=="Booking"?"class='active'":""?>>
                        <a href="index.php?act=BookingRegistrasi&hal=Booking">
                            <i class="material-icons">library_books</i>
                            <span>Booking Registrasi</span>
                        </a>
                    </li>
                    <li <?=$halaman=="RiwayatPeriksa"?"class='active'":""?>>
                        <a href="index.php?act=RiwayatPeriksa&hal=RiwayatPeriksa">
                            <i class="material-icons">local_pharmacy</i>
                            <span>Riwayat Kunjungan</span>
                        </a>
                    </li>
                    <li <?=$halaman=="RiwayatMCU"?"class='active'":""?>>
                        <a href="index.php?act=RiwayatMCU&hal=RiwayatMCU">
                            <i class="material-icons">assignment_ind</i>
                            <span>Riwayat MCU</span>
                        </a>
                    </li>
                    <li <?=$halaman=="AntrianTindakan"?"class='active'":""?>>
                        <a href="javascript:void(0);" class="menu-toggle">
                            <i class="material-icons">event_seat</i>
                            <span>Hasil Pemeriksaan</span>
                        </a>
                        <ul class="ml-menu">
                            <li <?=$subhalaman=="AntrianPemeriksaanLab"?"class='active'":""?>>
                                <a href="index.php?act=AntrianPemeriksaanLab&hal=AntrianTindakan">Laborat PK</a>
                            </li>
                            <li <?=$subhalaman=="AntrianPemeriksaanLabMB"?"class='active'":""?>>
                                <a href="index.php?act=AntrianPemeriksaanLabMB&hal=AntrianTindakan">Laborat MB</a>
                            </li>
                            <li <?=$subhalaman=="AntrianPemeriksaanLabPA"?"class='active'":""?>>
                                <a href="index.php?act=AntrianPemeriksaanLabPA&hal=AntrianTindakan">Laborat PA</a>
                            </li>
                            <li <?=$subhalaman=="AntrianPemeriksaanRad"?"class='active'":""?>>
                                <a href="index.php?act=AntrianPemeriksaanRad&hal=AntrianTindakan">Radiologi</a>
                            </li>
                            <li <?=$subhalaman=="AntrianUSGKandungan"?"class='active'":""?>>
                                <a href="index.php?act=AntrianUSGKandungan&hal=AntrianTindakan">USG Kandungan</a>
                            </li>
                            <li <?=$subhalaman=="AntrianUSGUrologi"?"class='active'":""?>>
                                <a href="index.php?act=AntrianUSGUrologi&hal=AntrianTindakan">USG Urologi</a>
                            </li>
                            <li <?=$subhalaman=="AntrianUSGGynecologi"?"class='active'":""?>>
                                <a href="index.php?act=AntrianUSGGynecologi&hal=AntrianTindakan">USG Gynecologi</a>
                            </li>
                            <li <?=$subhalaman=="AntrianUSGNeonatus"?"class='active'":""?>>
                                <a href="index.php?act=AntrianUSGNeonatus&hal=AntrianTindakan">USG Neonatus</a>
                            </li>
                            <li <?=$subhalaman=="AntrianEKG"?"class='active'":""?>>
                                <a href="index.php?act=AntrianEKG&hal=AntrianTindakan">EKG</a>
                            </li>
                            <li <?=$subhalaman=="AntrianEcho"?"class='active'":""?>>
                                <a href="index.php?act=AntrianEcho&hal=AntrianTindakan">Echocardiografi</a>
                            </li>
                            <li <?=$subhalaman=="AntrianEndoskopiFaring"?"class='active'":""?>>
                                <a href="index.php?act=AntrianEndoskopiFaring&hal=AntrianTindakan">Endoskopi Faring/Laringoskopi</a>
                            </li>
                            <li <?=$subhalaman=="AntrianEndoskopiHidung"?"class='active'":""?>>
                                <a href="index.php?act=AntrianEndoskopiHidung&hal=AntrianTindakan">Endoskopi Hidung</a>
                            </li>
                            <li <?=$subhalaman=="AntrianEndoskopiTelinga"?"class='active'":""?>>
                                <a href="index.php?act=AntrianEndoskopiTelinga&hal=AntrianTindakan">Endoskopi Telinga</a>
                            </li>
                        </ul>
                    </li>
                    <li <?=$halaman=="Surat"?"class='active'":""?>>
                        <a href="javascript:void(0);" class="menu-toggle">
                            <i class="material-icons">layers</i>
                            <span>Permintaan Surat</span>
                        </a>
                        <ul class="ml-menu">
                            <li <?=$subhalaman=="SuratSakit"?"class='active'":""?>>
                                <a href="index.php?act=SuratSakit&hal=Surat">Cuti Sakit</a>
                            </li>
                            <li <?=$subhalaman=="SuratHamil"?"class='active'":""?>>
                                <a href="index.php?act=SuratHamil&hal=Surat">Hamil/Tidak</a>
                            </li>
                            <li <?=$subhalaman=="SuratBebasNarkoba"?"class='active'":""?>>
                                <a href="index.php?act=SuratBebasNarkoba&hal=Surat">Bebas Narkoba</a>
                            </li>
                            <li <?=$subhalaman=="SuratKontrol"?"class='active'":""?>>
                                <a href="index.php?act=SuratKontrol&hal=Surat">Kontrol/SKDP</a>
                            </li>
                            <li <?=$subhalaman=="SuratRujuk"?"class='active'":""?>>
                                <a href="index.php?act=SuratRujuk&hal=Surat">Rujukan</a>
                            </li>
                            <li <?=$subhalaman=="SuratCovid"?"class='active'":""?>>
                                <a href="index.php?act=SuratCovid&hal=Surat">Keterangan Covid</a>
                            </li>
                        </ul>
                    </li>
                    <li <?=$halaman=="Persetujuan"?"class='active'":""?>>
                        <a href="javascript:void(0);" class="menu-toggle">
                            <i class="material-icons">assignment_turned_in</i>
                            <span>Persetujuan</span>
                        </a>
                        <ul class="ml-menu">
                            <li <?=$subhalaman=="PersetujuanUmum"?"class='active'":""?>>
                                <a href="index.php?act=PersetujuanUmum&hal=Persetujuan">Persetujuan Umum</a>
                            </li>
                            <li <?=$subhalaman=="PersetujuanPenolakanTindakan"?"class='active'":""?>>
                                <a href="index.php?act=PersetujuanPenolakanTindakan&hal=Persetujuan">Persetujuan/Penolakan Tindakan</a>
                            </li>
                            <li <?=$subhalaman=="PersetujuanRencanaPemulangan"?"class='active'":""?>>
                                <a href="index.php?act=PersetujuanRencanaPemulangan&hal=Persetujuan">Rencana Pemulangan</a>
                            </li>
                            <li <?=$subhalaman=="PersetujuanPenyerahanResepRalan"?"class='active'":""?>>
                                <a href="index.php?act=PersetujuanPenyerahanResepRalan&hal=Persetujuan">Penyerahan Resep Ralan</a>
                            </li>
                            <li <?=$subhalaman=="PersetujuanPernyataanPasienUmum"?"class='active'":""?>>
                                <a href="index.php?act=PersetujuanPernyataanPasienUmum&hal=Persetujuan">Pernyataan Pasien Umum</a>
                            </li>
                            <li <?=$subhalaman=="PersetujuanPernyataanPulangAPS"?"class='active'":""?>>
                                <a href="index.php?act=PersetujuanPernyataanPulangAPS&hal=Persetujuan">Pernyataan Pulang APS</a>
                            </li>
                            <li <?=$subhalaman=="PersetujuanTransferAntarRuang"?"class='active'":""?>>
                                <a href="index.php?act=PersetujuanTransferAntarRuang&hal=Persetujuan">Transfer Antar Ruang</a>
                            </li>
                            <li <?=$subhalaman=="PersetujuanRawatInap"?"class='active'":""?>>
                                <a href="index.php?act=PersetujuanRawatInap&hal=Persetujuan">Persetujuan Rawat Inap</a>
                            </li>
                            <li <?=$subhalaman=="PersetujuanPenundaanPelayanan"?"class='active'":""?>>
                                <a href="index.php?act=PersetujuanPenundaanPelayanan&hal=Persetujuan">Penundaan Pelayanan</a>
                            </li>
                            <li <?=$subhalaman=="PersetujuanPenolakanAnjuranMedis"?"class='active'":""?>>
                                <a href="index.php?act=PersetujuanPenolakanAnjuranMedis&hal=Persetujuan">Penolakan Anjuran Medis</a>
                            </li>
                            <li <?=$subhalaman=="PersetujuanRestrain"?"class='active'":""?>>
                                <a href="index.php?act=PersetujuanRestrain&hal=Persetujuan">Persetujuan Restrain</a>
                            </li>
                            <li <?=$subhalaman=="BuktiPelaksanaanEdukasi"?"class='active'":""?>>
                                <a href="index.php?act=BuktiPelaksanaanEdukasi&hal=Persetujuan">Pelaksanaan Informasi/Edukasi</a>
                            </li>
                        </ul>
                    </li>
                    <li <?=$halaman=="Fasilitas"?"class='active'":""?>>
                        <a href="javascript:void(0);" class="menu-toggle">
                            <i class="material-icons">card_membership</i>
                            <span>Fasilitas & Tarif</span>
                        </a>
                        <ul class="ml-menu">
                            <li <?=$subhalaman=="FasilitasKamarUser"?"class='active'":""?>>
                                <a href="index.php?act=FasilitasKamarUser&hal=Fasilitas">Kamar</a>
                            </li>
                            <li <?=$subhalaman=="FasilitasRadiologiUser"?"class='active'":""?>>
                                <a href="index.php?act=FasilitasRadiologiUser&hal=Fasilitas">Radiologi</a>
                            </li>
                            <li <?=$subhalaman=="FasilitasLaboratUser"?"class='active'":""?>>
                                <a href="index.php?act=FasilitasLaboratUser&hal=Fasilitas">Laborat</a>
                            </li>
                            <li <?=$subhalaman=="FasilitasOperasiUser"?"class='active'":""?>>
                                <a href="index.php?act=FasilitasOperasiUser&hal=Fasilitas">Operasi</a>
                            </li>
                            <li <?=$subhalaman=="FasilitasOnlineUser"?"class='active'":""?>>
                                <a href="index.php?act=FasilitasOnlineUser&hal=Fasilitas">Konsultasi Online</a>
                            </li>
                            <li <?=$subhalaman=="CekPoliUser"?"class='active'":""?>>
                                <a href="index.php?act=CekPoliUser&hal=Fasilitas">Poli Tersedia</a>
                            </li>
                            <li <?=$subhalaman=="CekAsuransiUser"?"class='active'":""?>>
                                <a href="index.php?act=CekAsuransiUser&hal=Fasilitas">Kerjasama Asuransi</a>
                            </li>
                        </ul>
                    </li>
                    <li <?=$halaman=="JadwalDokter"?"class='active'":""?>>
                        <a href="index.php?act=JadwalDokterUser&hal=JadwalDokter">
                            <i class="material-icons">event_available</i>
                            <span>Jadwal Dokter</span>
                        </a>
                    </li>
                    <li <?=$halaman=="InformasiKamar"?"class='active'":""?>>
                        <a href="index.php?act=InformasiKamarUser&hal=InformasiKamar">
                            <i class="material-icons">hotel</i>
                            <span>Ketersediaan Kamar</span>
                        </a>
                    </li>
                    <li <?=$halaman=="Pengaduan"?"class='active'":""?>>
                        <a href="index.php?act=Pengaduan&hal=Pengaduan">
                            <i class="material-icons">message</i>
                            <span>Pengaduan</span>
                        </a>
                    </li>
                    <li <?=$halaman=="KartuPasien"?"class='active'":""?>>
                        <a href="index.php?act=KartuPasien&hal=KartuPasien">
                            <i class="material-icons">card_membership</i>
                            <span>Kartu Pasien</span>
                        </a>
                    </li>
                </ul>
            </div>
            <!-- #Menu -->
            <!-- Footer -->
            <div class="legal">
                <div class="copyright">
                    &copy; 2010 - 2020 <a href="javascript:void(0);">SIMKES Khanza</a>.
                </div>
            </div>
            <!-- #Footer -->
        </aside>
    </section>

    <section class="content">
        <div class="container-fluid">
            <?php actionPages();?>
        </div>
    </section>
    <script src="plugins/jquery/jquery.min.js"></script>
    <script src="plugins/bootstrap/js/bootstrap.js"></script>
    <script src="plugins/bootstrap-select/js/bootstrap-select.js"></script>
    <script src="plugins/jquery-slimscroll/jquery.slimscroll.js"></script>
    <script src="plugins/node-waves/waves.js"></script>
    <script src="plugins/jquery-countto/jquery.countTo.js"></script>
    <script src="plugins/raphael/raphael.min.js"></script>
    <script src="plugins/chartjs/Chart.bundle.js"></script>
    <script src="plugins/flot-charts/jquery.flot.js"></script>
    <script src="plugins/flot-charts/jquery.flot.resize.js"></script>
    <script src="plugins/flot-charts/jquery.flot.pie.js"></script>
    <script src="plugins/flot-charts/jquery.flot.categories.js"></script>
    <script src="plugins/flot-charts/jquery.flot.time.js"></script>
    <script src="plugins/jquery-datatable/jquery.dataTables.js"></script>
    <script src="plugins/jquery-datatable/skin/bootstrap/js/dataTables.bootstrap.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/dataTables.buttons.min.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/buttons.flash.min.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/jszip.min.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/pdfmake.min.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/vfs_fonts.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/buttons.html5.min.js"></script>
    <script src="plugins/jquery-datatable/extensions/export/buttons.print.min.js"></script>
    <script src="plugins/jquery-sparkline/jquery.sparkline.js"></script>
    <script src="js/admin.js"></script>
    <script src="js/pages/tables/jquery-datatable.js"></script>
    <script src="js/pages/index.js"></script>
    <script src="js/demo.js"></script>
    <script src="conf/validator.js" type="text/javascript"></script>
</body>
</html>

