<!DOCTYPE html>
<html>
<head>
    <title>Edit dan Print</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
<div id="headerrajal">
    <h2>Edit Indikator RM</h2>
</div>
    <br>
<div id="kontenti">
        <?php
        include "../koneksi.php";
        include "indotgl.php";
        $id = $_GET['id'];
        $show = mysql_query("SELECT
              sik.reg_periksa.no_reg,
              sik.reg_periksa.no_rawat,
              sik.reg_periksa.tgl_registrasi,
              sik.reg_periksa.jam_reg,
              sik.reg_periksa.no_rkm_medis,
              sik.pasien.nm_pasien,
              sik.pasien.no_ktp,
              sik.pasien.jk,
              sik.pasien.tmp_lahir,
              sik.pasien.tgl_lahir,
              sik.pasien.nm_ibu,
              sik.pasien.alamat,
              sik.kelurahan.nm_kel,
              sik.kecamatan.nm_kec,
              sik.kabupaten.nm_kab,
              sik.pasien.gol_darah,
              sik.pasien.pekerjaan,
              sik.pasien.stts_nikah,
              sik.pasien.agama,
              sik.pasien.tgl_daftar,
              sik.pasien.no_tlp,
              sik.pasien.umur,
              sik.pasien.pnd,
              sik.pasien.keluarga,
              sik.pasien.namakeluarga,
              sik.penjab.png_jawab,
              sik.poliklinik.nm_poli,
              sik.dokter.nm_dokter,
              sik.reg_periksa.biaya_reg,
              sik.reg_periksa.stts,
              sik.reg_periksa.stts_daftar,
              sik.reg_periksa.status_lanjut,
              sik.rm.stts_rm
            FROM
              sik.reg_periksa,
              sik.pasien,
              sik.penjab,
              sik.poliklinik,
              sik.kelurahan,
              sik.kecamatan,
              sik.kabupaten,
              sik.dokter,
              sik.rm
            WHERE
              sik.reg_periksa.no_rkm_medis = sik.pasien.no_rkm_medis AND
              sik.kabupaten.kd_kab = sik.pasien.kd_kab AND
              sik.kecamatan.kd_kec = sik.pasien.kd_kec AND
              sik.kelurahan.kd_kel = sik.pasien.kd_kel AND
              sik.penjab.kd_pj = sik.pasien.kd_pj AND
              sik.dokter.kd_dokter = sik.reg_periksa.kd_dokter AND
              sik.poliklinik.kd_poli = sik.reg_periksa.kd_poli AND
              sik.rm.no_rkm_medis = sik.reg_periksa.no_rkm_medis and sik.reg_periksa.no_rkm_medis='$id'
            ORDER BY
              sik.reg_periksa.tgl_registrasi DESC,
              sik.reg_periksa.jam_reg DESC");

        if(mysql_num_rows($show) == 0){
            echo "<script>window.history.back()</sript>";
        }
        else{
            $row = mysql_fetch_assoc($show);
        }
        ?>

        
            <table width="200" border="0" align="right">
              <tr>
                <th scope="col" align="right"><h><?php echo $row['no_rkm_medis'];?>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>
              </tr>
            </table>
              <br>
              <br>
              <br>
              <br>
              <br>
              <br>
              <br>
              <br>
              <br>
            <table width="590" border="0" margin="200px">
              <tr>
                <th width="300" scope="col" align="left"><l>:&nbsp;<?php echo "".konversi_tanggal($row['tgl_registrasi'])."";?></th>
                <th width="170" scope="col">&nbsp;</th>
                <th width="120" scope="col" align="left"><l><?php echo $row['jam_reg'];?></th>
              </tr>
               <tr>
                <td valign="top"><l>:&nbsp;<?php echo $row['nm_pasien'];?></td>
                <td>&nbsp;</td>
                <td valign="top"><l><?php echo $row['agama'];?></td>
              </tr>
              <tr>
                <td valign="top"><l>:&nbsp;<?php echo "".konversi_tanggal($row['tgl_lahir'])."" ?>,&nbsp;<?php $lahir=new datetime($row['tgl_lahir']);
                    $today = new DateTime();
                    $umur=$today->diff($lahir);
                    echo $umur->y; echo" {th} - "; echo $umur->m; echo" {bl} - "; echo $umur->d; echo" {hr} ";?></td>
                <td>&nbsp;</td>
                <td valign="top"><l><?php echo $row['pekerjaan'];?></td>
              </tr>
              <tr>
                <td valign="top"><l>:&nbsp;(<?php echo $row['jk'];?>)</td>
                <td>&nbsp;</td>
                <td valign="top"><l><?php echo $row['namakeluarga'];?></td>
              </tr>
              <tr>
                <td valign="top"><ll>:&nbsp;<?php echo $row['alamat'];?>&nbsp;,<?php echo $row['nm_kel'];?>&nbsp;,<?php echo $row['nm_kec'];?>&nbsp;,<?php echo $row['nm_kab'];?></td>
                <td>&nbsp;</td>
                <td valign="top"><l><?php echo $row['keluarga'];?></td>
              </tr>
            </table>
</div>      
        <br>
<div id="footer3">
            <form action="edit-proses.php" method="post">
                <table cellpadding="15" cellspacing="0" border="0" align="center">
                    <tr>
                        <td><input type="text" value=<?php echo $row['no_rkm_medis']; ?> name="no_rkm_medis" hidden></td>
                    </tr>
                    
                    <tr>
                        <td>Stts RM</td>
                        <td>:</td>
                        <td>
                        <input name="stts_rm" type="radio" value="kembali" <?php if (($row['stts_rm'])=='kembali'){echo 'checked';}?>/>kembali  
                        <input name="stts_rm" type="radio" value="terkirim" <?php if (($row['stts_rm'])=='terkirim'){echo 'checked';}?>/>terkirim
                        <input name="stts_rm" type="radio" value="terima" <?php if (($row['stts_rm'])=='terima'){echo 'checked';}?>/>terima
                        <input name="stts_rm" type="radio" value="pinjam" <?php if (($row['stts_rm'])=='pinjam'){echo 'checked';}?>/>pinjam
                        <input name="stts_rm" type="radio" value="hilang" <?php if (($row['stts_rm'])=='hilang'){echo 'checked';}?>/>hilang
                        </td>
                    </tr>

                    <tr>
                        <td><a href="rm_kstatus.php"><b>BATAL<b></a></td>
                        <td align="center"><span id="cetak"><b><U>CETAK</U><b></span></td>
                        <td>
                            <input type="submit" value="Simpan" name="simpan">
                        </td>
                    </tr>
                </table>
            </form>
             <p></p>
</div>


<script src="jquery.min.js"></script>
        <script>
//            membuat fungsi cetak
            $('#cetak').click(function () {
                printDiv('kontenti');
            });

//            membuat fungsi print untuk div tertentu
//            fungsi ini akan membuat jendela baru
//            dan menuliskan kembali html ke dalamnya
            function printDiv(divId) {
                var config = {base: "http://localhost/harviacode2/print/"};
                var divToPrint = document.getElementById(divId);
                newWin = window.open("", "", "width=800, height=500, scrollbars=yes");
                newWin.document.write('<!doctype html><html><head>');
                newWin.document.write('<link rel="stylesheet" href="../css/style.css" />');
                newWin.document.write('<style> .jangan_cetak {display:none}</style>');
                newWin.document.write('</head><body>');
                newWin.document.write('<div id="kontenti">');
                newWin.document.write(divToPrint.innerHTML);
                newWin.document.write('</div>');
                newWin.document.write('</body>');
                newWin.document.write('</html>');
                newWin.document.close();
                newWin.focus();
                newWin.print();
                newWin.close();
            }
</script>
</body>
</html>