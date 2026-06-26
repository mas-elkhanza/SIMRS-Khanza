<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $iyem = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $iyem = json_decode(encrypt_decrypt($iyem,"d"),true); 
    if (isset($iyem["nopersetujuan"])) {
        $nopersetujuan    = validTeks3($iyem["nopersetujuan"],20);
        $photo            = validTeks3($iyem["photo"],50);
        $querypersetujuan = bukaquery(
            "select DATE_FORMAT(permintaan_binrohtal.tanggal,'%d-%m-%Y %H:%i:%s') as tanggal,permintaan_binrohtal.no_surat,permintaan_binrohtal.no_rawat,permintaan_binrohtal.nip,pegawai.nama,".
            "permintaan_binrohtal.kd_dokter,dokter.nm_dokter,permintaan_binrohtal.kd_bangsal,bangsal.nm_bangsal,permintaan_binrohtal.jns_permintaan,ifnull(nullif(permintaan_binrohtal.agama,''),pasien.agama) as agama,".
            "permintaan_binrohtal.jns_pelayanan,permintaan_binrohtal.ket_pelayanan,permintaan_binrohtal.respon,permintaan_binrohtal.ket_respon,permintaan_binrohtal.keterangan from permintaan_binrohtal ".
            "inner join reg_periksa on permintaan_binrohtal.no_rawat=reg_periksa.no_rawat inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis inner join pegawai on permintaan_binrohtal.nip=pegawai.nik ".
            "left join dokter on permintaan_binrohtal.kd_dokter=dokter.kd_dokter inner join bangsal on permintaan_binrohtal.kd_bangsal=bangsal.kd_bangsal where permintaan_binrohtal.no_surat='$nopersetujuan'"
        );
        if($rsquerypersetujuan= mysqli_fetch_array($querypersetujuan)){
            @$src = 'data: image/jpeg;base64,'.base64_encode(file_get_contents("http://".$_SERVER['HTTP_HOST']."/webapps/permintaanbinrohtal/".$photo));
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>PERSETUJUAN BIMBINGAN ROHANI DAN MENTAL<br/>No. $nopersetujuan Tanggal ".$rsquerypersetujuan["tanggal"]."</center></h2>
                            </div>
                            <div class='body'>
                                <h7>
                                    Data pasien yang akan mendapatkan pelayanan bimbingan rohani dan mental :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Nama Pasien</td>
                                        <td width='75%'>: ".$_SESSION["nm_pasien"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Nomor Rekam Medis</td>
                                        <td width='75%'>: ".cleankar(encrypt_decrypt($_SESSION["ses_pasien"],"d"))."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Jenis Kelamin</td>
                                        <td width='75%'>: ".($_SESSION["jk"]=="L"?"Laki-laki":"Perempuan")."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Tanggal Lahir</td>
                                        <td width='75%'>: ".$_SESSION["tgl_lahir"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>No.Telp/No.HP</td>
                                        <td width='75%'>: ".$_SESSION["no_tlp"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Agama</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["agama"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Detail permintaan pelayanan bimbingan rohani dan mental :
                                </h7>
                                <table width='100%' align='center' class='table table-hover js-basic-example dataTable'>
                                    <tr>
                                        <td width='25%'>Nomor Surat</td>
                                        <td width='75%'>: ".$nopersetujuan."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Ruang / Bangsal</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["kd_bangsal"]."-".$rsquerypersetujuan["nm_bangsal"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Jenis Permintaan</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["jns_permintaan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Jenis Pelayanan</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["jns_pelayanan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Keterangan Pelayanan</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["ket_pelayanan"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Respon</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["respon"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Keterangan Respon</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["ket_respon"]."</td>
                                    </tr>
                                    <tr>
                                        <td width='25%'>Keterangan Tambahan</td>
                                        <td width='75%'>: ".$rsquerypersetujuan["keterangan"]."</td>
                                    </tr>
                                </table>
                                <br/>
                                <h7>
                                    Pelayanan bimbingan rohani dan mental dilaksanakan oleh <b>".$rsquerypersetujuan["nama"]."</b> (NIP ".$rsquerypersetujuan["nip"].") dengan dokter penanggung jawab pasien <b>".$rsquerypersetujuan["nm_dokter"]."</b>.
                                </h7>
                                <br/>
                                <br/>
                                <h7><center>Yang Membuat Persetujuan</center></h7>
                                <br/>
                                <div class='row'>
                                    <div class='col-md-12 text-center'>
                                        <img alt='Gambar Persetujuan' src='$src' width='490px' height='420px'/><br/><br/>".$_SESSION["nm_pasien"]."<br/><br/>
                                        <a href='index.php?act=PersetujuanBimbinganRohani&hal=Persetujuan' class='btn btn-danger waves-effect'>Kembali</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>PERSETUJUAN BIMBINGAN ROHANI DAN MENTAL</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu persetujuan</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=PersetujuanBimbinganRohani&hal=Persetujuan",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>PERSETUJUAN BIMBINGAN ROHANI DAN MENTAL</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf persetujuan tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=PersetujuanBimbinganRohani&hal=Persetujuan",4);
    }
?>