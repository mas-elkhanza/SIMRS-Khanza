<?php
include ('config.php');
if(!empty($_POST['no_rkm_medis'])){
    $data = array();

    // get data pasien
    $get_pasien = fetch_array(query("SELECT * FROM pasien WHERE no_rkm_medis = '{$_POST['no_rkm_medis']}'"));

    // set format tanggal
    $tgl_reg = date('Y/m/d', strtotime($_POST['tgl_registrasi']));
    //mencari no rawat terakhir
    $no_rawat_akhir = fetch_array(query("SELECT max(no_rawat) FROM reg_periksa WHERE tgl_registrasi='$_POST[tgl_registrasi]'"));
    $no_urut_rawat = substr($no_rawat_akhir[0], 11, 6);
    $no_rawat = $tgl_reg.'/'.sprintf('%06s', ($no_urut_rawat + 1));
    //mencari no reg terakhir
    $no_reg_akhir = fetch_array(query("SELECT max(no_reg) FROM reg_periksa WHERE kd_dokter='$_POST[kd_dokter]' and tgl_registrasi='$_POST[tgl_registrasi]'"));
    if($no_reg_akhir[0] == NULL) {
        $no_reg = '001';
    } else {
      $no_urut_reg = substr($no_reg_akhir[0], 0, 3);
      $no_reg = sprintf('%03s', ($no_urut_reg + 1));
    }
    // get biaya
    $biaya_reg=fetch_array(query("SELECT registrasilama FROM poliklinik WHERE kd_poli='{$_POST['kd_poli']}'"));
    //menentukan umur sekarang
    list($cY, $cm, $cd) = explode('-', date('Y-m-d'));
    list($Y, $m, $d) = explode('-', date('Y-m-d', strtotime($get_pasien['tgl_lahir'])));
    $umurdaftar = $cY - $Y;

    $insert = query("INSERT
        INTO
            reg_periksa
        SET
            no_reg          = '$no_reg',
            no_rawat        = '$no_rawat',
            tgl_registrasi  = '$tgl_reg',
            jam_reg         = '$time',
            kd_dokter       = '{$_POST['kd_dokter']}',
            no_rkm_medis    = '{$_POST['no_rkm_medis']}',
            kd_poli         = '{$_POST['kd_poli']}',
            p_jawab         = '{$get_pasien['namakeluarga']}',
            almt_pj         = '{$get_pasien['alamat']}',
            hubunganpj      = '{$get_pasien['keluarga']}',
            biaya_reg       = '{$biaya_reg['0']}',
            stts            = 'Belum',
            stts_daftar     = 'Lama',
            status_lanjut   = 'Ralan',
            kd_pj           = '{$get_pasien['kd_pj']}',
            umurdaftar      = '{$umurdaftar}',
            sttsumur        = 'Th',
            status_bayar    = 'Belum Bayar',
            status_poli     = 'Lama'
    ");

    $query = $db->query(
      "SELECT
        f.no_rkm_medis,
        f.nm_pasien,
        a.tgl_registrasi,
        a.jam_reg,
        a.no_rawat,
        a.no_reg,
        b.nm_poli,
        c.nm_dokter,
        a.status_lanjut,
        d.png_jawab
      FROM reg_periksa a
      LEFT JOIN poliklinik b ON a.kd_poli = b.kd_poli
      LEFT JOIN dokter c ON a.kd_dokter = c.kd_dokter
      LEFT JOIN penjab d ON a.kd_pj = d.kd_pj
      LEFT JOIN pasien f ON a.no_rkm_medis = f.no_rkm_medis
      WHERE a.no_rawat = '{$no_rawat}'
    ");

    if($query->num_rows > 0){
        $userData = $query->fetch_assoc();
        $data['status'] = 'ok';
        $data['result'] = $userData;
    }else{
        $data['status'] = 'err';
    }
    //returns data as JSON format
    echo json_encode($data);
}
?>
