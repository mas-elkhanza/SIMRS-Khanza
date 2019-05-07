<?php
	function title(){
 		$judul ="Digital Payrol Khanza HMS --)(*!!@#$%";
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

        function cekSessiPegawai() {
            if (isset($_SESSION['ses_pegawai'])) {
                return true;
            } else {
                return false;
            }
        }

        function cekUser() {
            if (isset($_SESSION['ses_admin'])) {
                return true;
            } elseif (isset($_SESSION['ses_pegawai'])) {
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

        function pegawaiAktif() {
            if (cekSessiPegawai()) {
                return $_SESSION['ses_pegawai'];
            }
        }

    
	function isGuest() {
            if (cekSessiPegawai() || cekSessiAdmin()) {
                return false;
            } else {
                return true;
            }
        }	
		
	
	function formProtek() {
                $aksi=isset($_GET['act'])?$_GET['act']:NULL;
		if (!cekUser()) {
			$form = array ('HomeAdmin','Pengguna','olahpengguna','InputBidang','InputLokasi',
                                      'ListLokasi','ListBidang','InputBidang','ListPendidikan','InputPendidikan',
                                      'ListSttskerja','InputSttskerja','ListSttswp','InputSttswp','ListJenjang','InputJenjang',
                                      'ListDepartemen','InputDepartemen','ListInsentif','InputInsentif','InputIndex',
                                      'ListAkte','InputAkte','InputPenerimaAkte','ListResume','InputResume','InputPenerimaResume',
                                      'ListWarung','InputWarung','InputPenerimaWarung','ListTuslah','InputTuslah','InputPenerimaTuslah',
                                      'ListPegawai','InputPegawai','ListCariPegawai','ListIndexPegawai','EditIndexPegawai',
                                      'PrintPegawai','ListKeanggotaan','DetailKoperasi','DetailJamsostek','InputKeanggotaan',
                                      'InputPangkat','ListPotongan','InputDansos','InputPotongan','ListTindakan','ListTindakanDokter',
                                      'ListTindakanSpesialis','DetailTindakan','InputTindakan','InputTindakanDokter','InputTindakanSpesialis',
                                      'InputDiklat','ListTunjangan','DetailTunjanganHarian','DetailTunjanganBulanan','DetailPenerimaTunjanganBulanan',
                                      'DetailPenerimaTunjanganHarian','ListLampiran','InputJagaMalam','InputTidakHadir','InputTambahJaga',
                                      'InputSetJagaMalam','InputSetTambahJaga','InputSetTunjanganHadir','InputSetLemburHB','InputSetLemburHR',
                                      'InputJasaLain','InputArtikel','InputDataAdmin','InputTahun','ListArtikel','ListPresensi',
                                      'DetailPresensi','ListKS','InputJasLa','ListJasLa','InputPasien','ListRj','ListPinjam',
                                      'DetailPinjam','BayarPinjam','ListJam','SisaDankes','ListKoperasi','InputRiwayatPangkat','ListRiwayatPangkat',
                                      'InputRiwayatPendidikan','ListRiwayatPendidikan','InputRiwayatSeminar','ListRiwayatSeminar','ListRiwayatGaji',
                                      'InputBank','ListBank','DetailBpjs','DetailHarianBulanan','DetailBerkasPegawai','InputRiwayatPenghargaan',
                                      'ListRiwayatPenghargaan','InputRiwayatPenelitian','ListRiwayatPenelitian');
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
			case 'Kontak'		  	: include_once('pages/kontak.php'); break;
			case 'InputLokasi'              : include_once('pages/lokasi/inputlokasi.php'); break;
			case 'ListLokasi'               : include_once('pages/lokasi/listlokasi.php'); break;
                        case 'ListBidang'		: include_once('pages/bidang/listbidang.php'); break;
			case 'InputBidang'		: include_once('pages/bidang/inputbidang.php'); break;
                        case 'ListPendidikan'		: include_once('pages/pendidikan/listpendidikan.php'); break;
			case 'InputPendidikan'		: include_once('pages/pendidikan/inputpendidikan.php'); break;
                        case 'ListSttskerja'		: include_once('pages/statuskerja/liststatuskerja.php'); break;
			case 'InputSttskerja'		: include_once('pages/statuskerja/inputstatuskerja.php'); break;
                        case 'ListSttswp'		: include_once('pages/statuswp/liststatuswp.php'); break;
			case 'InputSttswp'		: include_once('pages/statuswp/inputstatuswp.php'); break;
                        case 'ListJenjang'		: include_once('pages/jenjang/listjenjang.php'); break;
			case 'InputJenjang'		: include_once('pages/jenjang/inputjenjang.php'); break;
			case 'ListDepartemen'		: include_once('pages/departemen/listdepartemen.php'); break;
			case 'InputDepartemen'		: include_once('pages/departemen/inputdepartemen.php'); break;
			case 'ListInsentif'		: include_once('pages/insentif/listinsentif.php'); break;
			case 'InputInsentif'		: include_once('pages/insentif/inputinsentif.php'); break;
                        case 'InputIndex'		: include_once('pages/insentif/inputindex.php'); break;
                        case 'ListAkte'         	: include_once('pages/akte/listakte.php'); break;
                        case 'InputAkte'		: include_once('pages/akte/inputakte.php'); break;
                        case 'InputPenerimaAkte'	: include_once('pages/akte/inputpenerimaakte.php'); break;

                        case 'ListResume'         	: include_once('pages/resume/listresume.php'); break;
                        case 'InputResume'		: include_once('pages/resume/inputresume.php'); break;
                        case 'InputPenerimaResume'	: include_once('pages/resume/inputpenerimaresume.php'); break;

                        case 'ListWarung'         	: include_once('pages/warung/listwarung.php'); break;
                        case 'InputWarung'		: include_once('pages/warung/inputwarung.php'); break;
                        case 'InputPenerimaWarung'	: include_once('pages/warung/inputpenerimawarung.php'); break;

                        case 'ListTuslah'         	: include_once('pages/tuslah/listtuslah.php'); break;
                        case 'InputTuslah'		: include_once('pages/tuslah/inputtuslah.php'); break;
                        case 'InputPenerimaTuslah'	: include_once('pages/tuslah/inputpenerimatuslah.php'); break;

                        case 'ListPegawai'		: include_once('pages/pegawai/listpegawai.php'); break;
                        case 'ListPeg'		        : include_once('pages/pegawai/listpeg.php'); break;
                        case 'SisaDankes'	        : include_once('pages/pegawai/detaildankes.php'); break;
			case 'InputPegawai'		: include_once('pages/pegawai/inputpegawai.php'); break;
                        case 'ListCariPegawai'		: include_once('pages/pegawai/listcaripegawai.php'); break;
                        case 'ListIndexPegawai'		: include_once('pages/pegawai/listindexpegawai.php'); break;
                        case 'EditIndexPegawai'		: include_once('pages/pegawai/editindex.php'); break;
                        case 'PrintPegawai'		: include_once('pages/pegawai/laporanpegawai.php'); break;
                        case 'ListKeanggotaan'		: include_once('pages/keanggotaan/listkeanggotaan.php'); break;
                        case 'DetailKoperasi'		: include_once('pages/keanggotaan/detailkoperasi.php'); break;
                        case 'DetailJamsostek'		: include_once('pages/keanggotaan/detailjamsostek.php'); break;
                        case 'DetailBpjs'		: include_once('pages/keanggotaan/detailbpjs.php'); break;
                        case 'InputKeanggotaan'		: include_once('pages/keanggotaan/inputkeanggotaan.php'); break;
			case 'InputPangkat'		: include_once('pages/pangkat/inputpangkat.php'); break;
			case 'ListJam'		        : include_once('pages/jam/detailjam.php'); break;
                        
                        case 'ListPotongan'		: include_once('pages/potongan/listpotongan.php'); break;
                        case 'InputDansos'		: include_once('pages/potongan/inputdansos.php'); break;
			case 'InputPotongan'		: include_once('pages/potongan/inputpotongan.php'); break;
			case 'ListTindakan'		: include_once('pages/tindakan/listtindakan.php'); break;
                        case 'ListTindakanDokter'	: include_once('pages/tindakan/listtindakandokter.php'); break;
                        case 'ListTindakanSpesialis'	: include_once('pages/tindakan/listtindakanspesialis.php'); break;
                        case 'DetailTindakan'		: include_once('pages/tindakan/detailtindakan.php'); break;
			case 'InputTindakan'		: include_once('pages/tindakan/inputtindakan.php'); break;
                        case 'InputTindakanDokter'	: include_once('pages/tindakan/inputtindakandokter.php'); break;
                        case 'InputTindakanSpesialis'	: include_once('pages/tindakan/inputtindakanspesialis.php'); break;

                        case 'InputDiklat'		: include_once('pages/diklat/inputdiklat.php'); break;
                        case 'ListTunjangan'		: include_once('pages/tunjangan/listtunjangan.php'); break;
                        case 'DetailHarianBulanan'      : include_once('pages/tunjangan/detailharianbulanan.php'); break;
			case 'DetailTunjanganHarian'		: include_once('pages/tunjangan/detailtunjanganharian.php'); break;
			case 'DetailTunjanganBulanan'		: include_once('pages/tunjangan/detailtunjanganbulanan.php'); break;
			case 'DetailPenerimaTunjanganBulanan'	: include_once('pages/tunjangan/detailpenerimatunjanganbulanan.php'); break;
                        case 'DetailPenerimaTunjanganHarian'	: include_once('pages/tunjangan/detailpenerimatunjanganharian.php'); break;
                        case 'ListLampiran'	        : include_once('pages/lampiran/listlampiran.php'); break;
                        case 'InputJagaMalam'	        : include_once('pages/lampiran/detailmalam.php'); break;
                        case 'InputTidakHadir'	        : include_once('pages/lampiran/detailtidakhadir.php'); break;
                        case 'InputTambahJaga'	        : include_once('pages/lampiran/detailtambahjaga.php'); break;
                        case 'InputSetJagaMalam'	: include_once('pages/lampiran/detailsetjgmlm.php'); break;
			case 'InputSetTambahJaga'	: include_once('pages/lampiran/detailsettambahjaga.php'); break;
			case 'InputSetTunjanganHadir'	: include_once('pages/lampiran/detailsethadir.php'); break;
                        case 'InputSetLemburHB'	        : include_once('pages/lampiran/detailsetlemburhb.php'); break;
                        case 'InputSetLemburHR'	        : include_once('pages/lampiran/detailsetlemburhr.php'); break;
                        case 'InputJasaLain'	        : include_once('pages/lampiran/detailjasalain.php'); break;
                        case 'InputPasien'	        : include_once('pages/lampiran/detailpasien.php'); break;

                        case 'InputJasLa'	        : include_once('pages/jasalain/detailjasalain.php'); break;
                        case 'ListJasLa'	        : include_once('pages/jasalain/listjasalain.php'); break;
                        
                        case 'InputBank'	        : include_once('pages/bank/inputbank.php'); break;
                        case 'ListBank' 	        : include_once('pages/bank/listbank.php'); break;

                        case 'ListPinjam'	        : include_once('pages/pinjam/listpinjam.php'); break;
                        case 'DetailPinjam'	        : include_once('pages/pinjam/detailpinjam.php'); break;
                        case 'BayarPinjam'	        : include_once('pages/pinjam/bayarpinjam.php'); break;
                        
                        case 'InputRj'                  : include_once('pages/rawat/inputrj.php'); break;
                        case 'ListRj'                   : include_once('pages/rawat/listrj.php'); break;
                        case 'DetailTindakanRj'         : include_once('pages/rawat/detailtindakanrj.php'); break;
						
                        case 'HomeAdmin'		: include_once('pages/home.php'); break;
			case 'InputDataAdmin'		: include_once('pages/aturadmin.php'); break;			
			case 'InputTahun'		: include_once('pages/aturtahun.php'); break;

                        case 'ListPresensi'              : include_once('pages/presensi/listpresensi.php'); break;
                        case 'DetailPresensi'            : include_once('pages/presensi/detailpresensi.php'); break;
                        case 'ListKS'		        : include_once('pages/ks/detailks.php'); break;
                        
                        case 'InputRiwayatPangkat'      : include_once('pages/riwayatpangkat/detail.php'); break;
                        case 'ListRiwayatPangkat'       : include_once('pages/riwayatpangkat/list.php'); break;
                        
                        case 'InputRiwayatPendidikan'   : include_once('pages/riwayatpendidikan/detail.php'); break;
                        case 'ListRiwayatPendidikan'    : include_once('pages/riwayatpendidikan/list.php'); break;
                        
                        case 'InputRiwayatSeminar'      : include_once('pages/riwayatseminar/detail.php'); break;
                        case 'ListRiwayatSeminar'       : include_once('pages/riwayatseminar/list.php'); break;
                        
                        case 'InputRiwayatGaji'         : include_once('pages/riwayatgaji/detail.php'); break;
                        case 'ListRiwayatGaji'          : include_once('pages/riwayatgaji/list.php'); break;

                        case 'InputRiwayatPenghargaan'  : include_once('pages/riwayatpenghargaan/detail.php'); break;
                        case 'ListRiwayatPenghargaan'   : include_once('pages/riwayatpenghargaan/list.php'); break;
                        
                        case 'InputRiwayatPenelitian'   : include_once('pages/riwayatpenelitian/detail.php'); break;
                        case 'ListRiwayatPenelitian'    : include_once('pages/riwayatpenelitian/list.php'); break;
                        case 'ListPerpustakaanPenelitian'   : include_once('pages/riwayatpenelitian/perpustakaanpenelitian.php'); break;
                        
                        case 'DetailBerkasPegawai'      : include_once('pages/berkaspegawai/detail.php'); break;
                        
                    default			       : include_once('pages/kontak.php');
			
		}
	}
	
	
	 
 
?>
