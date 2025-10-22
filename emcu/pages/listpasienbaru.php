<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $besok            = date("Y-m-d", strtotime("+1 day"));
    $thnbesok         = substr($besok,0,4);
    $blnbesok         = substr($besok,5,2);
    $tglbesok         = substr($besok,8,2);
    $sekarang         = date("Y-m-d");
    $thnmcu           = validTeks(trim(isset($_POST['tgl_daftar']))?substr($_POST['tgl_daftar'],6,4):$thnbesok);
    $blnmcu           = validTeks(trim(isset($_POST['tgl_daftar']))?substr($_POST['tgl_daftar'],3,2):$blnbesok);
    $tglmcu           = validTeks(trim(isset($_POST['tgl_daftar']))?substr($_POST['tgl_daftar'],0,2):$tglbesok);
    $interval         = getOne2("select (TO_DAYS('$thnmcu-$blnmcu-$tglmcu')-TO_DAYS('$sekarang'))");
    $perusahaan       = validTeks4(encrypt_decrypt($_SESSION["ses_emcu"],"d"),20);
    $thnsekarang      = substr($sekarang,0,4);
    $blnsekarang      = substr($sekarang,5,2);
    $tglsekarang      = substr($sekarang,8,2);
    $status           = validTeks(trim(isset($_GET['status']))?$_GET['status']:NULL);
    $thncarimcu       = $thnsekarang;
    $blncarimcu       = $blnsekarang;
    $tglcarimcu       = $tglsekarang;
    $thncarimcu2      = $thnsekarang;
    $blncarimcu2      = $blnsekarang;
    $tglcarimcu2      = $tglsekarang;
    $nopengajuanhapus = NULL;
    $nopengajuanubah  = NULL;
    $nm_pasien        = NULL;
    $no_ktp           = NULL;
    $jk               = NULL;
    $tmp_lahir        = NULL;
    $tgl_lahir        = NULL;
    $thnlahir         = NULL;
    $blnlahir         = NULL;
    $tgllahir         = NULL;
    $nm_ibu           = NULL;
    $alamat           = NULL;
    $gol_darah        = NULL;
    $pekerjaan        = NULL;
    $stts_nikah       = NULL;
    $agama            = NULL;
    $no_tlp           = NULL;
    $umur             = NULL;
    $pnd              = NULL;
    $keluarga         = NULL;
    $namakeluarga     = NULL;
    $kelurahan        = NULL;
    $kecamatan        = NULL;
    $kabupaten        = NULL;
    $pekerjaanpj      = NULL;
    $alamatpj         = NULL;
    $kelurahanpj      = NULL;
    $kecamatanpj      = NULL;
    $kabupatenpj      = NULL;
    $suku_bangsa      = NULL;
    $bahasa_pasien    = NULL;
    $cacat_fisik      = NULL;
    $email            = NULL;
    $nip              = NULL;
    $propinsi         = NULL;
    $propinsipj       = NULL;
    
    if(isset($_GET['iyem'])){
        $json = json_decode(encrypt_decrypt($_GET['iyem'],"d"),true);
        if (isset($json["tgl_cari_mcu"])) {
            $thncarimcu  = validTeks(trim(isset($json['tgl_cari_mcu']))?substr($json['tgl_cari_mcu'],6,4):$thnsekarang);
            $blncarimcu  = validTeks(trim(isset($json['tgl_cari_mcu']))?substr($json['tgl_cari_mcu'],3,2):$blnsekarang);
            $tglcarimcu  = validTeks(trim(isset($json['tgl_cari_mcu']))?substr($json['tgl_cari_mcu'],0,2):$tglsekarang);
        }

        if (isset($json["tgl_cari_mcu2"])) {
            $thncarimcu2 = validTeks(trim(isset($json['tgl_cari_mcu2']))?substr($json['tgl_cari_mcu2'],6,4):$thnsekarang);
            $blncarimcu2 = validTeks(trim(isset($json['tgl_cari_mcu2']))?substr($json['tgl_cari_mcu2'],3,2):$blnsekarang);
            $tglcarimcu2 = validTeks(trim(isset($json['tgl_cari_mcu2']))?substr($json['tgl_cari_mcu2'],0,2):$tglsekarang);
        }

        if (isset($json["nopengajuanhapus"])) {
            $nopengajuanhapus = validTeks($json["nopengajuanhapus"]);
            $status           = "Aktif";
        }
        
        if (isset($json["nopengajuanubah"])) {
            $nopengajuanubah  = validTeks($json["nopengajuanubah"]);
            $nm_pasien        = validTeks(trim(isset($json['nm_pasien']))?$json['nm_pasien']:NULL);
            $no_ktp           = validTeks(trim(isset($json['no_ktp']))?$json['no_ktp']:NULL);
            $jk               = NULL;
            $tmp_lahir        = validTeks(trim(isset($json['tmp_lahir']))?$json['tmp_lahir']:NULL);
            $tgl_lahir        = validTeks(trim(isset($json['tgl_lahir']))?$json['tgl_lahir']:NULL);
            $thnlahir         = validTeks(trim(isset($json['tgl_lahir']))?substr($json['tgl_lahir'],0,4):$thnsekarang);
            $blnlahir         = validTeks(trim(isset($json['tgl_lahir']))?substr($json['tgl_lahir'],5,2):$blnsekarang);
            $tgllahir         = validTeks(trim(isset($json['tgl_lahir']))?substr($json['tgl_lahir'],8,2):$tglsekarang);
            $nm_ibu           = validTeks(trim(isset($json['nm_ibu']))?$json['nm_ibu']:NULL);
            $alamat           = validTeks(trim(isset($json['alamat']))?$json['alamat']:NULL);
            $gol_darah        = validTeks(trim(isset($json['gol_darah']))?$json['gol_darah']:NULL);
            $pekerjaan        = validTeks(trim(isset($json['pekerjaan']))?$json['pekerjaan']:"Karyawan");
            $stts_nikah       = validTeks(trim(isset($json['stts_nikah']))?$json['stts_nikah']:NULL);
            $agama            = validTeks(trim(isset($json['agama']))?$json['agama']:NULL);
            $no_tlp           = validTeks(trim(isset($json['no_tlp']))?$json['no_tlp']:NULL);
            $thnmcu           = validTeks(trim(isset($json['tgl_mcu']))?substr($json['tgl_mcu'],0,4):$thnbesok);
            $blnmcu           = validTeks(trim(isset($json['tgl_mcu']))?substr($json['tgl_mcu'],5,2):$blnbesok);
            $tglmcu           = validTeks(trim(isset($json['tgl_mcu']))?substr($json['tgl_mcu'],8,2):$tglbesok);
            $umur             = validTeks(trim(isset($json['umur']))?$json['umur']:NULL);
            $pnd              = validTeks(trim(isset($json['pnd']))?$json['pnd']:NULL);
            $keluarga         = validTeks(trim(isset($json['keluarga']))?$json['keluarga']:NULL);
            $namakeluarga     = validTeks(trim(isset($json['namakeluarga']))?$json['namakeluarga']:NULL);
            $kelurahan        = validTeks(trim(isset($json['kelurahan']))?$json['kelurahan']:NULL);
            $kecamatan        = validTeks(trim(isset($json['kecamatan']))?$json['kecamatan']:NULL);
            $kabupaten        = validTeks(trim(isset($json['kabupaten']))?$json['kabupaten']:NULL);
            $pekerjaanpj      = validTeks(trim(isset($json['pekerjaanpj']))?$json['pekerjaanpj']:NULL);
            $alamatpj         = validTeks(trim(isset($json['alamatpj']))?$json['alamatpj']:NULL);
            $kelurahanpj      = validTeks(trim(isset($json['kelurahanpj']))?$json['kelurahanpj']:NULL);
            $kecamatanpj      = validTeks(trim(isset($json['kecamatanpj']))?$json['kecamatanpj']:NULL);
            $kabupatenpj      = validTeks(trim(isset($json['kabupatenpj']))?$json['kabupatenpj']:NULL);
            $suku_bangsa      = validTeks(trim(isset($json['suku_bangsa']))?$json['suku_bangsa']:NULL);
            $bahasa_pasien    = validTeks(trim(isset($json['bahasa_pasien']))?$json['bahasa_pasien']:NULL);
            $cacat_fisik      = validTeks(trim(isset($json['cacat_fisik']))?$json['cacat_fisik']:NULL);
            $email            = validTeks2(trim(isset($json['email']))?$json['email']:NULL);
            $nip              = validTeks(trim(isset($json['nip']))?$json['nip']:NULL);
            $propinsi         = validTeks(trim(isset($json['propinsi']))?$json['propinsi']:NULL);
            $propinsipj       = validTeks(trim(isset($json['propinsipj']))?$json['propinsipj']:NULL);

            if(isset($json['jk'])=="L"){
                $jk="LAKI-LAKI";
            }else if(isset($json['jk'])=="P"){
                $jk="PEREMPUAN";
            }
        }
    }
    
    if(isset($_POST["BtnCari"])){
        $thncarimcu  = validTeks(trim(isset($_POST['tgl_cari_mcu']))?substr($_POST['tgl_cari_mcu'],6,4):$thnsekarang);
        $blncarimcu  = validTeks(trim(isset($_POST['tgl_cari_mcu']))?substr($_POST['tgl_cari_mcu'],3,2):$blnsekarang);
        $tglcarimcu  = validTeks(trim(isset($_POST['tgl_cari_mcu']))?substr($_POST['tgl_cari_mcu'],0,2):$tglsekarang);
        $thncarimcu2 = validTeks(trim(isset($_POST['tgl_cari_mcu2']))?substr($_POST['tgl_cari_mcu2'],6,4):$thnsekarang);
        $blncarimcu2 = validTeks(trim(isset($_POST['tgl_cari_mcu2']))?substr($_POST['tgl_cari_mcu2'],3,2):$blnsekarang);
        $tglcarimcu2 = validTeks(trim(isset($_POST['tgl_cari_mcu2']))?substr($_POST['tgl_cari_mcu2'],0,2):$tglsekarang);
    }
    
    if(isset($_POST["BtnSimpan"])){
        $nm_pasien        = validTeks(trim(isset($_POST['nm_pasien']))?$_POST['nm_pasien']:NULL);
        $no_ktp           = validTeks(trim(isset($_POST['no_ktp']))?$_POST['no_ktp']:NULL);
        $jk               = validTeks(trim(isset($_POST['jk']))?$_POST['jk']:NULL);
        $tmp_lahir        = validTeks(trim(isset($_POST['tmp_lahir']))?$_POST['tmp_lahir']:NULL);
        $tgl_lahir        = validTeks(trim(isset($_POST['tgl_lahir']))?$_POST['tgl_lahir']:NULL);
        $thnlahir         = validTeks(trim(isset($_POST['tgl_lahir']))?substr($_POST['tgl_lahir'],6,4):$thnsekarang);
        $blnlahir         = validTeks(trim(isset($_POST['tgl_lahir']))?substr($_POST['tgl_lahir'],3,2):$blnsekarang);
        $tgllahir         = validTeks(trim(isset($_POST['tgl_lahir']))?substr($_POST['tgl_lahir'],0,2):$tglsekarang);
        $nm_ibu           = validTeks(trim(isset($_POST['nm_ibu']))?$_POST['nm_ibu']:NULL);
        $alamat           = validTeks(trim(isset($_POST['alamat']))?$_POST['alamat']:NULL);
        $gol_darah        = validTeks(trim(isset($_POST['gol_darah']))?$_POST['gol_darah']:NULL);
        $pekerjaan        = validTeks(trim(isset($_POST['pekerjaan']))?$_POST['pekerjaan']:"Karyawan");
        $stts_nikah       = validTeks(trim(isset($_POST['stts_nikah']))?$_POST['stts_nikah']:NULL);
        $agama            = validTeks(trim(isset($_POST['agama']))?$_POST['agama']:NULL);
        $no_tlp           = validTeks(trim(isset($_POST['no_tlp']))?$_POST['no_tlp']:NULL);
        $umur             = validTeks(trim(isset($_POST['umur']))?$_POST['umur']:NULL);
        $pnd              = validTeks(trim(isset($_POST['pnd']))?$_POST['pnd']:NULL);
        $keluarga         = validTeks(trim(isset($_POST['keluarga']))?$_POST['keluarga']:NULL);
        $namakeluarga     = validTeks(trim(isset($_POST['namakeluarga']))?$_POST['namakeluarga']:NULL);
        $kelurahan        = validTeks(trim(isset($_POST['kelurahan']))?$_POST['kelurahan']:NULL);
        $kecamatan        = validTeks(trim(isset($_POST['kecamatan']))?$_POST['kecamatan']:NULL);
        $kabupaten        = validTeks(trim(isset($_POST['kabupaten']))?$_POST['kabupaten']:NULL);
        $pekerjaanpj      = validTeks(trim(isset($_POST['pekerjaanpj']))?$_POST['pekerjaanpj']:NULL);
        $alamatpj         = validTeks(trim(isset($_POST['alamatpj']))?$_POST['alamatpj']:NULL);
        $kelurahanpj      = validTeks(trim(isset($_POST['kelurahanpj']))?$_POST['kelurahanpj']:NULL);
        $kecamatanpj      = validTeks(trim(isset($_POST['kecamatanpj']))?$_POST['kecamatanpj']:NULL);
        $kabupatenpj      = validTeks(trim(isset($_POST['kabupatenpj']))?$_POST['kabupatenpj']:NULL);
        $suku_bangsa      = validTeks(trim(isset($_POST['suku_bangsa']))?$_POST['suku_bangsa']:NULL);
        $bahasa_pasien    = validTeks(trim(isset($_POST['bahasa_pasien']))?$_POST['bahasa_pasien']:NULL);
        $cacat_fisik      = validTeks(trim(isset($_POST['cacat_fisik']))?$_POST['cacat_fisik']:NULL);
        $email            = validTeks2(trim(isset($_POST['email']))?$_POST['email']:NULL);
        $nip              = validTeks(trim(isset($_POST['nip']))?$_POST['nip']:NULL);
        $propinsi         = validTeks(trim(isset($_POST['propinsi']))?$_POST['propinsi']:NULL);
        $propinsipj       = validTeks(trim(isset($_POST['propinsipj']))?$_POST['propinsipj']:NULL);
        $nopengajuanubah  = validTeks(trim(isset($_POST['nopengajuanubah']))?$_POST['nopengajuanubah']:NULL);
    }
    
    if(isset($_POST["BtnCari"])){
        $status="Aktif";
    }
?>
<link href="plugins/bootstrap-datepicker/css/bootstrap-datepicker.min.css" rel="stylesheet" />
<div class="block-header">
    <h2><center>PANGAJUAN PASIEN BARU YANG BELUM TERDAFTAR DI <?=$_SESSION["nama_instansi"];?></center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <ul class="nav nav-tabs tab-nav-right" role="tablist">
                    <li role="presentation" <?=($status=="Aktif"?"":"class='active'")?>><a href="#home" data-toggle="tab">INPUT PENGAJUAN</a></li>
                    <li role="presentation" <?=($status=="Aktif"?"class='active'":"")?>><a href="#riwayat" data-toggle="tab">DATA PENGAJUAN</a></li>
                </ul>
                <!-- Tab panes -->
                <div class="tab-content">
                    <div role="tabpanel" <?=($status=="Aktif"?"class='tab-pane fade'":"class='tab-pane fade in active'")?> id="home">
                        <form id="form_validation" action="" method="POST">
                            <?php 
                                if (!empty($nopengajuanubah)) {
                                    echo "<input type='hidden' name='nopengajuanubah' value='$nopengajuanubah'/>";
                                }
                            ?>
                            <label for="nm_pasien">Nama Pasien</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input name="nm_pasien" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1" pattern="[a-zA-Z0-9 -]{1,40}" title=" a-z A-Z 0-9 (Maksimal 40 karakter)" placeholder="a-z A-Z 0-9 (Maksimal 40 karakter)" class="form-control" value="<?=$nm_pasien;?>" size="60" maxlength="40" autocomplete="off" required autofocus/>
                                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="no_ktp">No.KTP/SIM/Passport/Pengenal Lainnya</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input name="no_ktp" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2" pattern="[0-9]{1,20}" title=" 0-9 (Maksimal 20 karakter)"  placeholder="0-9 (Maksimal 20 karakter)" class="form-control" value="<?=$no_ktp;?>" size="60" maxlength="20" autocomplete="off" required/>
                                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="jk">Jenis Kelamin</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="jeniskelamin" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3" name="jk" pattern="[A-Z -]{1,10}" title="LAKI-LAKI/PEREMPUAN" placeholder="LAKI-LAKI/PEREMPUAN" class="form-control" value="<?=$jk;?>" size="60" maxlength="10" autocomplete="off" required/>
                                    <datalist id="jeniskelamin">
                                        <option>LAKI-LAKI</option>
                                        <option>PEREMPUAN</option>
                                    </datalist>
                                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="tmp_lahir">Tempat Lahir</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input name="tmp_lahir" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4" pattern="[a-zA-Z ]{1,15}" title=" a-z A-Z (Maksimal 15 karakter)" placeholder=" a-z A-Z (Maksimal 15 karakter)" class="form-control" value="<?=$tmp_lahir;?>" size="60" maxlength="15" autocomplete="off" required/>
                                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="tgl_lahir">Tanggal Lahir</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_lahir" class="datepicker form-control" required value="<?=$tglsekarang."-".$blnsekarang."-".$thnsekarang;?>"/>
                                    <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="nm_ibu">Nama Ibu</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input name="nm_ibu" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6" pattern="[a-zA-Z ]{1,40}" title=" a-z A-Z (Maksimal 40 karakter)" placeholder="a-z A-Z (Maksimal 40 karakter)" class="form-control" value="<?=$nm_ibu;?>" size="60" maxlength="40" autocomplete="off" required/>
                                    <span id="MsgIsi6" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="alamat">Alamat Pasien</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input name="alamat" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" id="TxtIsi7" pattern="[a-zA-Z0-9 -]{1,200}" title=" a-z A-Z 0-9 (Maksimal 200 karakter)" placeholder="a-z A-Z 0-9 (Maksimal 200 karakter)" class="form-control" value="<?=$alamat;?>" size="60" maxlength="200" autocomplete="off" required/>
                                    <span id="MsgIsi7" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="kelurahan">Kelurahan Pasien</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="kelurahan" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi8'));" id="TxtIsi8" name="kelurahan" pattern="[A-Z ]{1,60}" title="A-Z (Maksimal 60 karakter)" placeholder="A-Z (Maksimal 60 karakter)" class="form-control" value="<?=$kelurahan?>" size="60" maxlength="60" autocomplete="off" required/>
                                    <datalist id="kelurahan">
                                    <?php
                                        $querykelurahan = bukaquery(
                                            "select kelurahan.nm_kel from kelurahan"
                                        );
                                        while($rsquerykelurahan = mysqli_fetch_array($querykelurahan)) {
                                            echo "<option>".$rsquerykelurahan["nm_kel"]."</option>";
                                        }
                                    ?>
                                    </datalist>
                                    <span id="MsgIsi8" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="kecamatan">Kecamatan Pasien</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="kecamatan" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi9'));" id="TxtIsi9" name="kecamatan" pattern="[A-Z ]{1,60}" title="A-Z (Maksimal 60 karakter)" placeholder="A-Z (Maksimal 60 karakter)" class="form-control" value="<?=$kecamatan;?>" size="60" maxlength="60" autocomplete="off" required/>
                                    <datalist id="kecamatan">
                                    <?php
                                        $querykecamatan = bukaquery(
                                            "select kecamatan.nm_kec from kecamatan"
                                        );
                                        while($rsquerykecamatan = mysqli_fetch_array($querykecamatan)) {
                                            echo "<option>".$rsquerykecamatan["nm_kec"]."</option>";
                                        }
                                    ?>
                                    </datalist>
                                    <span id="MsgIsi9" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="kabupaten">Kabupaten Pasien</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="kabupaten" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi10'));" id="TxtIsi10" name="kabupaten" pattern="[A-Z ]{1,60}" title="A-Z (Maksimal 60 karakter)" placeholder="A-Z (Maksimal 60 karakter)" class="form-control" value="<?=$kabupaten;?>" size="60" maxlength="60" autocomplete="off" required/>
                                    <datalist id="kabupaten">
                                    <?php
                                        $querykabupaten = bukaquery(
                                            "select kabupaten.nm_kab from kabupaten"
                                        );
                                        while($rsquerykabupaten = mysqli_fetch_array($querykabupaten)) {
                                            echo "<option>".$rsquerykabupaten["nm_kab"]."</option>";
                                        }
                                    ?>
                                    </datalist>
                                    <span id="MsgIsi10" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="propinsi">Propinsi Pasien</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="propinsi" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi11'));" id="TxtIsi11" name="propinsi" pattern="[A-Z ]{1,60}" title="A-Z (Maksimal 60 karakter)" placeholder="A-Z (Maksimal 60 karakter)" class="form-control" value="<?=$propinsi;?>" size="60" maxlength="60" autocomplete="off" required/>
                                    <datalist id="propinsi">
                                    <?php
                                        $querypropinsi = bukaquery(
                                            "select propinsi.nm_prop from propinsi"
                                        );
                                        while($rsquerypropinsi = mysqli_fetch_array($querypropinsi)) {
                                            echo "<option>".$rsquerypropinsi["nm_prop"]."</option>";
                                        }
                                    ?>
                                    </datalist>
                                    <span id="MsgIsi11" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="gol_darah">Golongan Darah</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="gol_darah" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi12'));" id="TxtIsi12" name="gol_darah" pattern="[A-Z -]{1,2}" title="A/B/AB/O/-" placeholder="A/B/AB/O/-" class="form-control" value="<?=$gol_darah;?>" size="60" maxlength="2" autocomplete="off" required/>
                                    <datalist id="gol_darah">
                                        <option>-</option>
                                        <option>A</option>
                                        <option>B</option>
                                        <option>AB</option>
                                        <option>O</option>
                                    </datalist>
                                    <span id="MsgIsi12" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="pekerjaan">Pekerjaan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input name="pekerjaan" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi13'));" id="TxtIsi13" pattern="[a-zA-Z ]{1,60}" title=" a-z A-Z (Maksimal 60 karakter)" placeholder="a-z A-Z (Maksimal 60 karakter)" class="form-control" value="<?=$pekerjaan;?>" size="60" maxlength="60" autocomplete="off" required/>
                                    <span id="MsgIsi13" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="stts_nikah">Status Nikah</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="stts_nikah" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi14'));" id="TxtIsi14" name="stts_nikah" pattern="[A-Z -]{1,13}" title="BELUM MENIKAH/MENIKAH/JANDA/DUDHA/JOMBLO" placeholder="BELUM MENIKAH/MENIKAH/JANDA/DUDHA/JOMBLO" class="form-control" value="<?=$stts_nikah;?>" size="60" maxlength="13" autocomplete="off" required/>
                                    <datalist id="stts_nikah">
                                        <option>BELUM MENIKAH</option>
                                        <option>MENIKAH</option>
                                        <option>JANDA</option>
                                        <option>DUDHA</option>
                                        <option>JOMBLO</option>
                                    </datalist>
                                    <span id="MsgIsi14" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="agama">Agama / Kepercayaan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="agama" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi15'));" id="TxtIsi15" name="agama" pattern="[A-Z -]{1,12}" title="ISLAM/KRISTEN/KATOLIK/HINDU/BUDHA/KONG HU CHU/-" placeholder="ISLAM/KRISTEN/KATOLIK/HINDU/BUDHA/KONG HU CHU/-" class="form-control" value="<?=$agama;?>" size="60" maxlength="12" autocomplete="off" required/>
                                    <datalist id="agama">
                                        <option>ISLAM</option>
                                        <option>KRISTEN</option>
                                        <option>KATOLIK</option>
                                        <option>HINDU</option>
                                        <option>BUDHA</option>
                                        <option>KONG HU CHU</option>
                                        <option>-</option>
                                    </datalist>
                                    <span id="MsgIsi15" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="tgl_daftar">Tanggal Rencana MCU</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi16'));" id="TxtIsi16" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title="DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_daftar" class="datepicker form-control" value="<?=$tglbesok."-".$blnbesok."-".$thnbesok;?>" required/>
                                    <span id="MsgIsi16" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="no_tlp">No.Telp/No.HP</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input name="no_tlp" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi17'));" id="TxtIsi17" pattern="[0-9 ,]{1,40}" title=" 0-9 (Maksimal 40 karakter)"  placeholder="0-9 (Maksimal 40 karakter)" class="form-control" value="<?=$no_tlp;?>" size="60" maxlength="40" autocomplete="off" required/>
                                    <span id="MsgIsi17" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="umur">Umur</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input name="umur" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi18'));" id="TxtIsi18" pattern="[a-zA-Z0-9 -]{1,30}" title=" a-z A-Z 0-9 (Maksimal 30 karakter)" placeholder="a-z A-Z 0-9 (Maksimal 30 karakter)" class="form-control" value="<?=$umur;?>" size="60" maxlength="30" autocomplete="off" required/>
                                    <span id="MsgIsi18" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="pnd">Pendidikan</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="pnd" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi19'));" id="TxtIsi19" name="pnd" pattern="[A-Z /]{1,15}" title="-/TS/TK/SD/SMP/SMA/ SLTA/SEDERAJAT /D1/D2/D3/D4/S1/S2/S3" placeholder="-/TS/TK/SD/SMP/SMA/ SLTA/SEDERAJAT /D1/D2/D3/D4/S1/S2/S3" class="form-control" value="<?=$pnd;?>" size="60" maxlength="15" autocomplete="off" required/>
                                    <datalist id="pnd">
                                        <option>-</option>
                                        <option>TS</option>
                                        <option>TK</option>
                                        <option>SD</option>
                                        <option>SMP</option>
                                        <option>SMA</option>
                                        <option>SLTA/SEDERAJAT</option>
                                        <option>D1</option>
                                        <option>D2</option>
                                        <option>D3</option>
                                        <option>D4</option>
                                        <option>S1</option>
                                        <option>S2</option>
                                        <option>S3</option>
                                    </datalist>
                                    <span id="MsgIsi19" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="keluarga">Penanggung Jawab / Keluarga</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="keluarga" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi20'));" id="TxtIsi20" name="keluarga" pattern="[A-Z -]{1,12}" title="AYAH/IBU/ISTRI/SUAMI/SAUDARA/ANAK/DIRI SENDIRI/LAIN-LAIN" placeholder="AYAH/IBU/ISTRI/SUAMI/SAUDARA/ANAK/DIRI SENDIRI/LAIN-LAIN" class="form-control" value="<?=$keluarga;?>" size="60" maxlength="12" autocomplete="off" required/>
                                    <datalist id="keluarga">
                                        <option>AYAH</option>
                                        <option>IBU</option>
                                        <option>ISTRI</option>
                                        <option>SUAMI</option>
                                        <option>SAUDARA</option>
                                        <option>ANAK</option>
                                        <option>DIRI SENDIRI</option>
                                        <option>LAIN-LAIN</option>
                                    </datalist>
                                    <span id="MsgIsi20" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="namakeluarga">Nama Penanggung Jawab / Keluarga</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input name="namakeluarga" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi21'));" id="TxtIsi21" pattern="[a-zA-Z ]{1,50}" title=" a-z A-Z (Maksimal 50 karakter)" placeholder="a-z A-Z (Maksimal 50 karakter)" class="form-control" value="<?=$namakeluarga;?>" size="60" maxlength="50" autocomplete="off" required/>
                                    <span id="MsgIsi21" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="pekerjaanpj">Pekerjaan Penanggung Jawab / Keluarga</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input name="pekerjaanpj" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi22'));" id="TxtIsi22" pattern="[a-zA-Z ]{1,35}" title=" a-z A-Z (Maksimal 35 karakter)" placeholder="a-z A-Z (Maksimal 35 karakter)" class="form-control" value="<?=$pekerjaanpj;?>" size="60" maxlength="35" autocomplete="off" required/>
                                    <span id="MsgIsi22" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="alamatpj">Alamat Penanggung Jawab / Keluarga</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input name="alamatpj" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi23'));" id="TxtIsi23" pattern="[a-zA-Z0-9 -]{1,100}" title=" a-z A-Z 0-9 (Maksimal 100 karakter)" placeholder="a-z A-Z 0-9 (Maksimal 100 karakter)" class="form-control" value="<?=$alamatpj;?>" size="60" maxlength="100" autocomplete="off" required/>
                                    <span id="MsgIsi23" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="kelurahanpj">Kelurahan Penanggung Jawab / Keluarga</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="kelurahanpj" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi24'));" id="TxtIsi24" name="kelurahanpj" pattern="[A-Z ]{1,60}" title="A-Z (Maksimal 60 karakter)" placeholder="A-Z (Maksimal 60 karakter)" class="form-control" value="<?=$kelurahanpj;?>" size="60" maxlength="60" autocomplete="off" required/>
                                    <datalist id="kelurahanpj">
                                    <?php
                                        $querykelurahan = bukaquery(
                                            "select kelurahan.nm_kel from kelurahan"
                                        );
                                        while($rsquerykelurahan = mysqli_fetch_array($querykelurahan)) {
                                            echo "<option>".$rsquerykelurahan["nm_kel"]."</option>";
                                        }
                                    ?>
                                    </datalist>
                                    <span id="MsgIsi24" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="kecamatanpj">Kecamatan Penanggung Jawab / Keluarga</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="kecamatanpj" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi25'));" id="TxtIsi25" name="kecamatanpj" pattern="[A-Z ]{1,60}" title="A-Z (Maksimal 60 karakter)" placeholder="A-Z (Maksimal 60 karakter)" class="form-control" value="<?=$kecamatanpj;?>" size="60" maxlength="60" autocomplete="off" required/>
                                    <datalist id="kecamatanpj">
                                    <?php
                                        $querykecamatan = bukaquery(
                                            "select kecamatan.nm_kec from kecamatan"
                                        );
                                        while($rsquerykecamatan = mysqli_fetch_array($querykecamatan)) {
                                            echo "<option>".$rsquerykecamatan["nm_kec"]."</option>";
                                        }
                                    ?>
                                    </datalist>
                                    <span id="MsgIsi25" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="kabupatenpj">Kabupaten Penanggung Jawab / Keluarga</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="kabupatenpj" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi26'));" id="TxtIsi26" name="kabupatenpj" pattern="[A-Z ]{1,60}" title="A-Z (Maksimal 60 karakter)" placeholder="A-Z (Maksimal 60 karakter)" class="form-control" value="<?=$kabupatenpj;?>" size="60" maxlength="60" autocomplete="off" required/>
                                    <datalist id="kabupatenpj">
                                    <?php
                                        $querykabupaten = bukaquery(
                                            "select kabupaten.nm_kab from kabupaten"
                                        );
                                        while($rsquerykabupaten = mysqli_fetch_array($querykabupaten)) {
                                            echo "<option>".$rsquerykabupaten["nm_kab"]."</option>";
                                        }
                                    ?>
                                    </datalist>
                                    <span id="MsgIsi26" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="propinsipj">Propinsi Penanggung Jawab / Keluarga</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="propinsipj" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi27'));" id="TxtIsi27" name="propinsipj" pattern="[A-Z ]{1,60}" title="A-Z (Maksimal 60 karakter)" placeholder="A-Z (Maksimal 60 karakter)" class="form-control" value="<?=$propinsipj;?>" size="60" maxlength="60" autocomplete="off" required/>
                                    <datalist id="propinsipj">
                                    <?php
                                        $querypropinsi = bukaquery(
                                            "select propinsi.nm_prop from propinsi"
                                        );
                                        while($rsquerypropinsi = mysqli_fetch_array($querypropinsi)) {
                                            echo "<option>".$rsquerypropinsi["nm_prop"]."</option>";
                                        }
                                    ?>
                                    </datalist>
                                    <span id="MsgIsi27" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="suku_bangsa">Suku Bangsa Pasien</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="suku_bangsa" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi28'));" id="TxtIsi28" name="suku_bangsa" pattern="[A-Z ]{1,30}" title="A-Z (Maksimal 30 karakter)" placeholder="A-Z (Maksimal 30 karakter)" class="form-control" value="<?=$suku_bangsa;?>" size="60" maxlength="30" autocomplete="off" required/>
                                    <datalist id="suku_bangsa">
                                    <?php
                                        $querysukubangsa = bukaquery(
                                            "select suku_bangsa.nama_suku_bangsa from suku_bangsa"
                                        );
                                        while($rsquerysukubangsa = mysqli_fetch_array($querysukubangsa)) {
                                            echo "<option>".$rsquerysukubangsa["nama_suku_bangsa"]."</option>";
                                        }
                                    ?>
                                    </datalist>
                                    <span id="MsgIsi28" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="bahasa_pasien">Bahasa Pasien</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="bahasa_pasien" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi29'));" id="TxtIsi29" name="bahasa_pasien" pattern="[A-Z ]{1,30}" title="A-Z (Maksimal 30 karakter)" placeholder="A-Z (Maksimal 30 karakter)" class="form-control" value="<?=$bahasa_pasien;?>" size="60" maxlength="30" autocomplete="off" required/>
                                    <datalist id="bahasa_pasien">
                                    <?php
                                        $querybahasapasien = bukaquery(
                                            "select bahasa_pasien.nama_bahasa from bahasa_pasien"
                                        );
                                        while($rsquerybahasapasien = mysqli_fetch_array($querybahasapasien)) {
                                            echo "<option>".$rsquerybahasapasien["nama_bahasa"]."</option>";
                                        }
                                    ?>
                                    </datalist>
                                    <span id="MsgIsi29" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="cacat_fisik">Cacat Fisik Pasien</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input class="form-control" list="cacat_fisik" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi30'));" id="TxtIsi30" name="cacat_fisik" pattern="[A-Z ]{1,30}" title="A-Z (Maksimal 30 karakter)" placeholder="A-Z (Maksimal 30 karakter)" class="form-control" value="<?=$cacat_fisik;?>" size="60" maxlength="30" autocomplete="off" required/>
                                    <datalist id="cacat_fisik">
                                    <?php
                                        $querycacatfisik = bukaquery(
                                            "select cacat_fisik.nama_cacat from cacat_fisik"
                                        );
                                        while($rsquerycacatfisik = mysqli_fetch_array($querycacatfisik)) {
                                            echo "<option>".$rsquerycacatfisik["nama_cacat"]."</option>";
                                        }
                                    ?>
                                    </datalist>
                                    <span id="MsgIsi30" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="email">Email Pasien</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input name="email" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi31'));" id="TxtIsi31" pattern="[a-zA-Z0-9, ./@_]{1,50}" title=" a-z A-Z 0-9 ,./@_ (Maksimal 50 karakter)" placeholder="a-z A-Z 0-9 ,./@_ (Maksimal 50 karakter)" class="form-control" value="<?=$email;?>" size="60" maxlength="50" autocomplete="off" required/>
                                    <span id="MsgIsi31" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <label for="nip">NIP/No.Pegawai Pasien</label>
                            <div class="form-group">
                                <div class="form-line">
                                    <input name="nip" type="text" onkeydown="setDefault(this, document.getElementById('MsgIsi32'));" id="TxtIsi32" pattern="[a-zA-Z0-9 -]{1,30}" title=" a-z A-Z 0-9 (Maksimal 30 karakter)" placeholder="a-z A-Z 0-9 (Maksimal 30 karakter)" class="form-control" value="<?=$nip;?>" size="60" maxlength="30" autocomplete="off" required/>
                                    <span id="MsgIsi32" style="color:#CC0000; font-size:10px;"></span>
                                </div>
                            </div>
                            <?php
                                if(isset($_POST["BtnSimpan"])){
                                    if($interval<0){
                                        echo "<div class='row clearfix'>
                                                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                        <div class='card'>
                                                            <div class='body bg-success'>
                                                                <center><h4>Tanggal Rencana MCU tidak berlaku mundur...!</h4></center>
                                                            </div>
                                                        </div>
                                                    </div>
                                               </div>";
                                    }else{
                                        if ((!empty($nm_pasien))&&(!empty($no_ktp))&&(!empty($jk))&&(!empty($tmp_lahir))&&(!empty($tgl_lahir))&&(!empty($nm_ibu))&&(!empty($alamat))&&(!empty($kelurahan))&&(!empty($kecamatan))&&(!empty($kabupaten))&&(!empty($propinsi))&&(!empty($gol_darah))&&(!empty($pekerjaan))&&(!empty($stts_nikah))&&(!empty($agama))&&(!empty($thnmcu-$blnmcu-$tglmcu))&&(!empty($no_tlp))&&(!empty($umur))&&(!empty($pnd))&&(!empty($keluarga))&&(!empty($namakeluarga))&&(!empty($pekerjaanpj))&&(!empty($alamatpj))&&(!empty($kelurahanpj))&&(!empty($kecamatanpj))&&(!empty($kabupatenpj))&&(!empty($propinsipj))&&(!empty($suku_bangsa))&&(!empty($bahasa_pasien))&&(!empty($cacat_fisik))&&(!empty($email))&&(!empty($nip))) {
                                            if(!empty($nopengajuanubah)){
                                                echo "'$nopengajuan','$nm_pasien','$no_ktp','".substr($jk,0,1)."','$tmp_lahir','$thnlahir-$blnlahir-$tgllahir','$nm_ibu','$alamat','$kelurahan','$kecamatan','$kabupaten','$propinsi','$gol_darah','$pekerjaan','$stts_nikah','$agama','$thnmcu-$blnmcu-$tglmcu','$no_tlp','$umur','$pnd','$keluarga','$namakeluarga','$pekerjaanpj','$alamatpj','$kelurahanpj','$kecamatanpj','$kabupatenpj','$propinsipj','$perusahaan','$suku_bangsa','$bahasa_pasien','$cacat_fisik','$email','$nip','Menunggu Konfirmasi'";
                                                $ubah        = Ubah2("booking_mcu_perusahaan_pasien_baru","nm_pasien='$nm_pasien',no_ktp='$no_ktp',jk='".substr($jk,0,1)."',tmp_lahir='$tmp_lahir',tgl_lahir='$thnlahir-$blnlahir-$tgllahir',nm_ibu='$nm_ibu',alamat='$alamat',kelurahan='$kelurahan',kecamatan='$kecamatan',kabupaten='$kabupaten',propinsi='$propinsi',gol_darah='$gol_darah',pekerjaan='$pekerjaan',stts_nikah='$stts_nikah',agama='$agama',tgl_mcu='$thnmcu-$blnmcu-$tglmcu',no_tlp='$no_tlp',umur='$umur',pnd='$pnd',keluarga='$keluarga',namakeluarga='$namakeluarga',pekerjaanpj='$pekerjaanpj',alamatpj='$alamatpj',kelurahanpj='$kelurahanpj',kecamatanpj='$kecamatanpj',kabupatenpj='$kabupatenpj',propinsipj='$propinsipj',perusahaan_pasien='$perusahaan',suku_bangsa='$suku_bangsa',bahasa_pasien='$bahasa_pasien',cacat_fisik='$cacat_fisik',email='$email',nip='$nip' where no_pengajuan='$nopengajuanubah'");
                                                if(!$ubah){
                                                    echo "<div class='row clearfix'>
                                                              <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                                  <div class='card'>
                                                                      <div class='body bg-success'>
                                                                           <center><h4>Gagal mengubah pengajuan Pasien Baru MCU, ada parameter yang tidak sesuai...!</h4></center>
                                                                      </div>
                                                                  </div>
                                                              </div>
                                                          </div>";
                                                }else{
                                                    JSRedirect("index.php?act=PasienBaru&status=Aktif&iyem=".encrypt_decrypt("{\"tgl_cari_mcu\":\"$tglmcu-$blnmcu-$thnmcu\",\"tgl_cari_mcu2\":\"$tglmcu-$blnmcu-$thnmcu\"}","e")."");
                                                }
                                            }else{
                                                $max         = getOne2("select ifnull(MAX(CONVERT(RIGHT(booking_mcu_perusahaan_pasien_baru.no_pengajuan,4),signed)),0)+1 from booking_mcu_perusahaan_pasien_baru where booking_mcu_perusahaan_pasien_baru.tgl_mcu='$thnmcu-$blnmcu-$tglmcu'");
                                                $nopengajuan = "MPB"."$thnmcu$blnmcu$tglmcu".sprintf("%04s", $max);
                                                $insert      = Tambah4("booking_mcu_perusahaan_pasien_baru","'$nopengajuan','$nm_pasien','$no_ktp','".substr($jk,0,1)."','$tmp_lahir','$thnlahir-$blnlahir-$tgllahir','$nm_ibu','$alamat','$kelurahan','$kecamatan','$kabupaten','$propinsi','$gol_darah','$pekerjaan','$stts_nikah','$agama','$thnmcu-$blnmcu-$tglmcu','$no_tlp','$umur','$pnd','$keluarga','$namakeluarga','$pekerjaanpj','$alamatpj','$kelurahanpj','$kecamatanpj','$kabupatenpj','$propinsipj','$perusahaan','$suku_bangsa','$bahasa_pasien','$cacat_fisik','$email','$nip','Menunggu Konfirmasi'");
                                                if(!$insert){
                                                    echo "<div class='row clearfix'>
                                                              <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                                  <div class='card'>
                                                                      <div class='body bg-success'>
                                                                           <center><h4>Gagal menyimpan pengajuan Pasien Baru MCU, ada parameter yang tidak sesuai...!</h4></center>
                                                                      </div>
                                                                  </div>
                                                              </div>
                                                          </div>";
                                                }else{
                                                    JSRedirect("index.php?act=PasienBaru");
                                                }
                                            }
                                        }else{
                                            echo "<div class='row clearfix'>
                                                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                        <div class='card'>
                                                            <div class='body bg-success'>
                                                                <center><h4>Semua field wajib diisi...!</h4></center>
                                                            </div>
                                                        </div>
                                                    </div>
                                                  </div>";
                                        }
                                    }
                                }
                            ?>
                            <center><button class="btn btn-danger waves-effect" type="submit" name="BtnSimpan">Simpan</button>&nbsp;&nbsp;<button class="btn btn-warning waves-effect" type="reset" name="BtnReset">Reset</button></center>
                        </form>
                    </div>
                    <div role="tabpanel" <?=($status=="Aktif"?"class='tab-pane fade in active'":"class='tab-pane fade'")?> id="riwayat">
                        <form id="form_validation" action="" method="POST">
                            <div class="row clearfix">
                                <div class="col-md-6">
                                    <label for="tgl_cari_mcu">Tanggal MCU</label>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_mcu" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarimcu."-".$blncarimcu."-".$thncarimcu;?>"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-md-6">
                                    <label for="tgl_cari_mcu2">Sampai Dengan</label>
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="text" data-provide="datepicker" data-date-format="dd-mm-yyyy" pattern="(0[1-9]|1[0-9]|2[0-9]|3[01])-(0[1-9]|1[012])-[0-9]{4}" title=" DD-MM-YYYY" placeholder="DD-MM-YYYY" name="tgl_cari_mcu2" class="datepicker form-control" required autocomplete="off" value="<?=$tglcarimcu2."-".$blncarimcu2."-".$thncarimcu2;?>"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <center><button class="btn btn-danger waves-effect" type="submit" name="BtnCari">Cari Data MCU</button></center>
                        </form>
                        <?php
                            if(!empty($nopengajuanhapus)){
                                $hapus=Hapus2("booking_mcu_perusahaan_pasien_baru","no_pengajuan='".$nopengajuanhapus."'");
                                if(!$hapus){
                                    echo "<div class='row clearfix'>
                                              <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                                                  <div class='card'>
                                                      <div class='body bg-success'>
                                                           <center><h4>No.Booking MCU ".validTeks($json["nomcuhapus"])." gagal dihapus, silahkan hubungi bagian administrasi kami...!</h4></center>
                                                      </div>
                                                  </div>
                                              </div>
                                          </div>";
                                }
                            }
                        ?>
                        <div class="table-responsive">
                            <table class="table table-bordered table-striped table-hover js-basic-example dataTable" width="6500px">
                                <thead>
                                    <tr>
                                        <th valign='middle'><center>Aksi</center></th>
                                        <th valign='middle'><center>Status<br>Pengajuan</center></th>
                                        <th valign='middle'><center>Nomor<br>Pengajuan</center></th>
                                        <th valign='middle'><center>Nama Pasien</center></th>
                                        <th valign='middle'><center>No.KTP/SIM/Passport<br>/Pengenal Lainnya</center></th>
                                        <th valign='middle'><center>JK</center></th>
                                        <th valign='middle'><center>Tempat<br>Lahir</center></th>
                                        <th valign='middle'><center>Tanggal<br>lahir</center></th>
                                        <th valign='middle'><center>Nama Ibu</center></th>
                                        <th valign='middle'><center>Alamat Pasien</center></th>
                                        <th valign='middle'><center>Kelurahan<br>Pasien</center></th>
                                        <th valign='middle'><center>Kecamatan<br>Pasien</center></th>
                                        <th valign='middle'><center>Kabupaten<br>Pasien</center></th>
                                        <th valign='middle'><center>Propinsi<br>Pasien</center></th>
                                        <th valign='middle'><center>G.D.</center></th>
                                        <th valign='middle'><center>Pekerjaan<br>Pasien</center></th>
                                        <th valign='middle'><center>Stts.Nikah</center></th>
                                        <th valign='middle'><center>Agama/<br>Kepercayaan</center></th>
                                        <th valign='middle'><center>Tanggal<br>Rencana MCU</center></th>
                                        <th valign='middle'><center>No.Telp/<br>No.HP</center></th>
                                        <th valign='middle'><center>Umur<br>Pasien</center></th>
                                        <th valign='middle'><center>Pendidikan</center></th>
                                        <th valign='middle'><center>Penanggung Jawab<br>/ Keluarga</center></th>
                                        <th valign='middle'><center>Nama Penanggung<br>Jawab / Keluarga</center></th>
                                        <th valign='middle'><center>Pekerjaan Penanggung<br>Jawab / Keluarga</center></th>
                                        <th valign='middle'><center>Alamat Penanggung<br>Jawab / Keluarga</center></th>
                                        <th valign='middle'><center>Kelurahan Penanggung<br>Jawab / Keluarga</center></th>
                                        <th valign='middle'><center>Kecamatan Penanggung<br>Jawab / Keluarga</center></th>
                                        <th valign='middle'><center>Kabupaten Penanggung<br>Jawab / Keluarga</center></th>
                                        <th valign='middle'><center>Propinsi Penanggung<br>Jawab / Keluarga</center></th>
                                        <th valign='middle'><center>Suku Bangsa<br>Pasien</center></th>
                                        <th valign='middle'><center>Bahasa<br>Pasien</center></th>
                                        <th valign='middle'><center>Cacat Fisik<br>Pasien</center></th>
                                        <th valign='middle'><center>Email<br>Pasien</center></th>
                                        <th valign='middle'><center>NIP/No.Pegawai<br>Pasien</center></th>
                                    </tr>
                                </thead>
                                <tbody>
                                <?php 
                                    $querypasiencari = bukaquery(
                                        "select booking_mcu_perusahaan_pasien_baru.no_pengajuan,booking_mcu_perusahaan_pasien_baru.nm_pasien,booking_mcu_perusahaan_pasien_baru.no_ktp,".
                                        "booking_mcu_perusahaan_pasien_baru.jk,booking_mcu_perusahaan_pasien_baru.tmp_lahir,booking_mcu_perusahaan_pasien_baru.tgl_lahir,booking_mcu_perusahaan_pasien_baru.nm_ibu,".
                                        "booking_mcu_perusahaan_pasien_baru.alamat,booking_mcu_perusahaan_pasien_baru.kelurahan,booking_mcu_perusahaan_pasien_baru.kecamatan,booking_mcu_perusahaan_pasien_baru.kabupaten,".
                                        "booking_mcu_perusahaan_pasien_baru.propinsi,booking_mcu_perusahaan_pasien_baru.gol_darah,booking_mcu_perusahaan_pasien_baru.pekerjaan,booking_mcu_perusahaan_pasien_baru.stts_nikah,".
                                        "booking_mcu_perusahaan_pasien_baru.agama,booking_mcu_perusahaan_pasien_baru.tgl_mcu,booking_mcu_perusahaan_pasien_baru.no_tlp,booking_mcu_perusahaan_pasien_baru.umur,".
                                        "booking_mcu_perusahaan_pasien_baru.pnd,booking_mcu_perusahaan_pasien_baru.keluarga,booking_mcu_perusahaan_pasien_baru.namakeluarga,booking_mcu_perusahaan_pasien_baru.pekerjaanpj,".
                                        "booking_mcu_perusahaan_pasien_baru.alamatpj,booking_mcu_perusahaan_pasien_baru.kelurahanpj,booking_mcu_perusahaan_pasien_baru.kecamatanpj,booking_mcu_perusahaan_pasien_baru.kabupatenpj,".
                                        "booking_mcu_perusahaan_pasien_baru.propinsipj,booking_mcu_perusahaan_pasien_baru.perusahaan_pasien,booking_mcu_perusahaan_pasien_baru.suku_bangsa,".
                                        "booking_mcu_perusahaan_pasien_baru.bahasa_pasien,booking_mcu_perusahaan_pasien_baru.cacat_fisik,booking_mcu_perusahaan_pasien_baru.email,booking_mcu_perusahaan_pasien_baru.nip,".
                                        "booking_mcu_perusahaan_pasien_baru.status from booking_mcu_perusahaan_pasien_baru where booking_mcu_perusahaan_pasien_baru.perusahaan_pasien='$perusahaan' and ".
                                        "booking_mcu_perusahaan_pasien_baru.tgl_mcu between '$thncarimcu-$blncarimcu-$tglcarimcu' and '$thncarimcu2-$blncarimcu2-$tglcarimcu2'"
                                    );
                                    while($rsquerypasiencari = mysqli_fetch_array($querypasiencari)) {
                                        echo "<tr title='Nama : ".$rsquerypasiencari["nm_pasien"].", NIP : ".$rsquerypasiencari["nip"].", No.Pengajuan : ".$rsquerypasiencari["no_pengajuan"]."'>
                                                 <td align='center' valign='middle'>".
                                                    ($rsquerypasiencari["status"]!="Sudah Dikonfirmasi"?
                                                        "<a href='index.php?act=PasienBaru&iyem=".encrypt_decrypt("{\"nopengajuanhapus\":\"".$rsquerypasiencari["no_pengajuan"]."\",\"tgl_cari_mcu\":\"$tglcarimcu-$blncarimcu-$thncarimcu\",\"tgl_cari_mcu2\":\"$tglcarimcu2-$blncarimcu2-$thncarimcu2\"}","e")."' class='btn btn-warning waves-effect'>Hapus</a>".
                                                        "<a href='index.php?act=PasienBaru&iyem=".encrypt_decrypt("{\"nopengajuanubah\":\"".$rsquerypasiencari["no_pengajuan"]."\",\"nm_pasien\":\"".$rsquerypasiencari["nm_pasien"]."\",\"no_ktp\":\"".$rsquerypasiencari["no_ktp"]."\",\"jk\":\"".$rsquerypasiencari["jk"]."\",\"tmp_lahir\":\"".$rsquerypasiencari["tmp_lahir"]."\",\"tgl_lahir\":\"".$rsquerypasiencari["tgl_lahir"]."\",\"nm_ibu\":\"".$rsquerypasiencari["nm_ibu"]."\",\"alamat\":\"".$rsquerypasiencari["alamat"]."\",\"kelurahan\":\"".$rsquerypasiencari["kelurahan"]."\",\"kecamatan\":\"".$rsquerypasiencari["kecamatan"]."\",\"kabupaten\":\"".$rsquerypasiencari["kabupaten"]."\",\"propinsi\":\"".$rsquerypasiencari["propinsi"]."\",\"gol_darah\":\"".$rsquerypasiencari["gol_darah"]."\",\"pekerjaan\":\"".$rsquerypasiencari["pekerjaan"]."\",\"stts_nikah\":\"".$rsquerypasiencari["stts_nikah"]."\",\"agama\":\"".$rsquerypasiencari["agama"]."\",\"tgl_mcu\":\"".$rsquerypasiencari["tgl_mcu"]."\",\"no_tlp\":\"".$rsquerypasiencari["no_tlp"]."\",\"umur\":\"".$rsquerypasiencari["umur"]."\",\"pnd\":\"".$rsquerypasiencari["pnd"]."\",\"keluarga\":\"".$rsquerypasiencari["keluarga"]."\",\"namakeluarga\":\"".$rsquerypasiencari["namakeluarga"]."\",\"pekerjaanpj\":\"".$rsquerypasiencari["pekerjaanpj"]."\",\"alamatpj\":\"".$rsquerypasiencari["alamatpj"]."\",\"kelurahanpj\":\"".$rsquerypasiencari["kelurahanpj"]."\",\"kecamatanpj\":\"".$rsquerypasiencari["kecamatanpj"]."\",\"kabupatenpj\":\"".$rsquerypasiencari["kabupatenpj"]."\",\"propinsipj\":\"".$rsquerypasiencari["propinsipj"]."\",\"suku_bangsa\":\"".$rsquerypasiencari["suku_bangsa"]."\",\"bahasa_pasien\":\"".$rsquerypasiencari["bahasa_pasien"]."\",\"cacat_fisik\":\"".$rsquerypasiencari["cacat_fisik"]."\",\"email\":\"".$rsquerypasiencari["email"]."\",\"nip\":\"".$rsquerypasiencari["nip"]."\"}","e")."' class='btn btn-info waves-effect'>Ubah</a>":
                                                        "<a href='index.php?act=BookingMCU&iyem=".encrypt_decrypt("{\"tgl_cari_mcu\":\"".substr($rsquerypasiencari["tgl_mcu"],8,2)."-".substr($rsquerypasiencari["tgl_mcu"],5,2)."-".substr($rsquerypasiencari["tgl_mcu"],0,4)."\",\"tgl_cari_mcu2\":\"".substr($rsquerypasiencari["tgl_mcu"],8,2)."-".substr($rsquerypasiencari["tgl_mcu"],5,2)."-".substr($rsquerypasiencari["tgl_mcu"],0,4)."\"}","e")."' class='btn btn-success waves-effect'>Data Booking MCU</a>"
                                                    ).
                                                 "</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["status"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["no_pengajuan"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["nm_pasien"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["no_ktp"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["jk"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["tmp_lahir"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["tgl_lahir"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["nm_ibu"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["alamat"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["kelurahan"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["kecamatan"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["kabupaten"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["propinsi"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["gol_darah"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["pekerjaan"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["stts_nikah"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["agama"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["tgl_mcu"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["no_tlp"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["umur"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["pnd"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["keluarga"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["namakeluarga"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["pekerjaanpj"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["alamatpj"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["kelurahanpj"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["kecamatanpj"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["kabupatenpj"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["propinsipj"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["suku_bangsa"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["bahasa_pasien"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["cacat_fisik"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["email"]."</td>
                                                 <td align='left' valign='middle'>".$rsquerypasiencari["nip"]."</td>
                                              </tr>";
                                    }
                                ?>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>  
            </div>
        </div>
    </div>
</div>

<script src="plugins/jquery/jquery.min.js" type="text/javascript"></script>
<script src="plugins/bootstrap-datepicker/js/bootstrap-datepicker.min.js" type="text/javascript"></script>
