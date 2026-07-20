<?php
    function title(){
        $judul ="Aplikasi E-Eksekutif --)(*!!@#$%";
        $judul = preg_replace("[^A-Za-z0-9_\-\./,|]"," ",$judul);
        $judul = str_replace(array('.','-','/',',')," ",$judul);
        $judul = trim($judul);
        echo "$judul";	
    }

    function cekSessiAdmin() {
        if (isset($_SESSION['ses_eksekutif'])) {
            return true;
        } else {
            return false;
        }
    }

    function PasienAktif() {
        if (cekSessiAdmin()) {
            return $_SESSION['ses_eksekutif'];
        }
    }

    function isPengunjung() {
        if (cekSessiAdmin()) {
            return false;
        } else {
            return true;
        }
    }

    function formProtek() {
        $aksi=isset($_GET['act'])?$_GET['act']:NULL;
        if (!cekSessiAdmin()) {
            $form = array (
                'HomeUser','PelayananRawatJalan','PelayananIGDK','PelayananRawatInap','PelayananLaborat' ,'PelayananRadiologi',
                'SisaStokFarmasi','DaruratStok','KadaluarsaBatch','RingkasanPengadaanFarmasi','StokTidakBergerak','RingkasanPenerimaanFarmasi',
                'RingkasanHibahFarmasi','RingkasanPiutangObatFarmasi','RingkasanStokKeluarFarmasi','RingkasanReturSuplierFarmasi',
                'RingkasanReturObatPasien','RingkasanObatPerPoli','RingkasanObatPerDokter','NilaiPenerimaanVendorFarmasiPerBulan',
                'PembayaranPerAkunRekeningCOA','PembayaranPerAkunBayar','PiutangPerAkunPiutang','PendapatanPerAkunClosing','PendapatanPerAkunRekening',
                'SisaStokNonMedis','RingkasanPengadaanNonMedis','RingkasanPenerimaanNonMedis','RingkasanHibahNonMedis','RingkasanStokKeluarNonMedis',
                'RingkasanReturSuplierNonMedis','NilaiPenerimaanVendorNonMedisPerBulan'
            );
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
            case "HomeUser"                             : include_once("pages/listhome.php"); break;
            case "PelayananRawatJalan"                  : include_once("pages/listpelayananrawatjalan.php"); break;
            case "PelayananIGDK"                        : include_once("pages/listpelayananigd.php"); break;
            case "PelayananRawatInap"                   : include_once("pages/listpelayananrawatinap.php"); break;
            case "PelayananLaborat"                     : include_once("pages/listpelayananlab.php"); break;
            case "PelayananRadiologi"                   : include_once("pages/listpelayananradiologi.php"); break;
            case "SisaStokFarmasi"                      : include_once("pages/listsisastokfarmasi.php"); break;
            case "SisaStokNonMedis"                     : include_once("pages/listsisastoknonmedis.php"); break;
            case "DaruratStok"                          : include_once("pages/listdaruratstok.php"); break;
            case "KadaluarsaBatch"                      : include_once("pages/listkadaluarsabatch.php"); break;
            case "RingkasanPengadaanFarmasi"            : include_once("pages/listringkasanpengadaanfarmasi.php"); break;
            case "RingkasanPenerimaanFarmasi"           : include_once("pages/listringkasanpenerimaanfarmasi.php"); break;
            case "RingkasanHibahFarmasi"                : include_once("pages/listringkasanhibahfarmasi.php"); break;
            case "RingkasanPenjualanFarmasi"            : include_once("pages/listringkasanpenjualanfarmasi.php"); break;
            case "RingkasanBeriObatFarmasi"             : include_once("pages/listringkasanberobatfarmasi.php"); break;
            case "RingkasanPiutangObatFarmasi"          : include_once("pages/listringkasanpiutangfarmasi.php"); break;
            case "RingkasanStokKeluarFarmasi"           : include_once("pages/listringkasanstokkeluarfarmasi.php"); break;
            case "RingkasanReturSuplierFarmasi"         : include_once("pages/listringkasanretursuplierfarmasi.php"); break;
            case "RingkasanReturObatPasien"             : include_once("pages/listringkasanreturobatpasien.php"); break;
            case "RingkasanObatPerPoli"                 : include_once("pages/listringkasanobatperpoli.php"); break;
            case "RingkasanObatPerDokter"               : include_once("pages/listringkasanobatperdokter.php"); break;
            case "RingkasanPengadaanNonMedis"           : include_once("pages/listringkasanpengadaannonmedis.php"); break;
            case "RingkasanPenerimaanNonMedis"          : include_once("pages/listringkasanpenerimaannonmedis.php"); break;
            case "RingkasanHibahNonMedis"               : include_once("pages/listringkasanhibahnonmedis.php"); break;
            case "RingkasanStokKeluarNonMedis"          : include_once("pages/listringkasanstokkeluarnonmedis.php"); break;
            case "RingkasanReturSuplierNonMedis"        : include_once("pages/listringkasanretursupliernonmedis.php"); break;
            case "StokTidakBergerak"                    : include_once("pages/liststoktidakbergerak.php"); break;
            case "NilaiPenerimaanVendorFarmasiPerBulan" : include_once("pages/listnilaipenerimaanvendorfarmasiperbulan.php"); break;
            case "NilaiPenerimaanVendorNonMedisPerBulan": include_once("pages/listnilaipenerimaanvendornonmedisperbulan.php"); break;
            case "PembayaranPerAkunRekeningCOA"         : include_once("pages/listpembayaranperakunrekeningcoa.php"); break;
            case "PembayaranPerAkunBayar"               : include_once("pages/listpembayaranperakunbayar.php"); break;
            case "PiutangPerAkunPiutang"                : include_once("pages/listpiutangperakunpiutang.php"); break;
            case "PendapatanPerAkunClosing"             : include_once("pages/listpendapatanperakunclosing.php"); break;
            case "PendapatanPerAkunRekening"            : include_once("pages/listpendapatanperakunrekening.php"); break;
            default                                     : include_once("pages/listhome.php");
        }   
    }
?>
