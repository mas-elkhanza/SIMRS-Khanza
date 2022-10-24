<?php
 include '../../../conf/conf.php';
?>
<html>
    <head>
        <link href="../../css/default.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <?php
            $tanggal         = validTeks($_GET['tanggal']);
            $id              = validTeks($_GET['id']);
            $_sql2           = "SELECT peminjaman_koperasi.id,peminjaman_koperasi.banyak_angsur,peminjaman_koperasi.pinjaman,peminjaman_koperasi.pokok,peminjaman_koperasi.jasa,peminjaman_koperasi.tanggal,peminjaman_koperasi.status FROM peminjaman_koperasi where peminjaman_koperasi.id='$id' and peminjaman_koperasi.tanggal='$tanggal'";
            $baris2          = mysqli_fetch_row($hasil2);
            @$id             = $baris2[0];
            @$banyak_angsur  = $baris2[1];
            @$pinjaman       = $baris2[2];
            @$pokok          = $baris2[3];
            @$jasa           = $baris2[4];
            @$setoran        = $baris2[4]+$baris2[3];
            @$tanggal        = $baris2[5];
            @$status         = $baris2[6];                
            $_sql            = "SELECT pegawai.nik,pegawai.nama FROM pegawai where pegawai.id='$id'";
            $hasil           = bukaquery($_sql);
            $baris           = mysqli_fetch_row($hasil);
            $_sqlj           = "select count(angsuran_koperasi.id),sum(angsuran_koperasi.pokok) from angsuran_koperasi where angsuran_koperasi.id='$id' and angsuran_koperasi.tanggal_pinjam='$tanggal' group by angsuran_koperasi.id";
            $hasilj          = bukaquery($_sqlj);
            $barisj          = mysqli_fetch_array($hasilj);
            @$jml_sdh_angsur = $barisj[0];
            @$sdh_setor      = $barisj[1];
            @$sisa_pinjam    = $pinjaman-($jml_sdh_angsur*$pokok);
        ?>
        <table width="100%" align="center">
            <caption><h1 class=title><font color='999999'>Laporan Bayar Peminjaman</font></h1></caption>
            <tr class="head">
                <td width="31%" >NIP</td><td width="">:</td>
                <td width="67%">
                 <?php echo @$baris[0];?>
                </td>
            </tr>
            <tr class="head">
                <td width="31%">Nama</td><td width="">:</td>
                <td width="67%"><?php echo @$baris[1];?></td>
            </tr>
            <tr class="head">
                <td width="31%" >Tanggal Pinjam</td><td width="">:</td>
                <td width="67%"><?php echo $tanggal;?></td>
            </tr>
            <tr class="head">
                <td width="31%" >Jml.Angsuran</td><td width="">:</td>
                <td width="67%"><?php echo $banyak_angsur;?></td>
            </tr>
            <tr class="head">
                <td width="31%" >Jml.Pinjaman</td><td width="">:</td>
                <td width="67%"><?php echo formatDuit($pinjaman);?></td>
            </tr>
            <tr class="head">
                <td width="31%" >Pokok</td><td width="">:</td>
                <td width="67%"><?php echo formatDuit($pokok);?></td>
            </tr>
            <tr class="head">
                <td width="31%" >Jasa</td><td width="">:</td>
                <td width="67%"><?php echo formatDuit($jasa);?></td>
            </tr>
            <tr class="head">
                <td width="31%" >Setoran</td><td width="">:</td>
                <td width="67%"><?php echo formatDuit($setoran);?></td>
            </tr>
            <tr class="head">
                <td width="31%" >Status Pinjaman</td><td width="">:</td>
                <td width="67%"><?php echo $status;?></td>
            </tr>
            <tr class="head">
                <td width="31%" >Jml.Sdh Diangsur</td><td width="">:</td>
                <td width="67%"><?php echo formatDec($jml_sdh_angsur);?></td>
            </tr>
            <tr class="head">
                <td width="31%" >Ttl.Sdh Diangsur</td><td width="">:</td>
                <td width="67%"><?php echo formatDuit($sdh_setor);?></td>
            </tr>
            <tr class="head">
                <td width="31%" >Sisa Pinjaman</td><td width="">:</td>
                <td width="67%"><?php echo formatDuit($sisa_pinjam);?></td>
            </tr>
        </table>     
        </div>      
        <?php
            $BtnGenerate=isset($_POST['BtnGenerate'])?$_POST['BtnGenerate']:NULL;
            if (isset($BtnGenerate)) {
                $id            = validTeks(trim($_POST['id']));
                $tanggal       = validTeks(trim($_POST['tanggal']));
                $pokok         = validangka(trim($_POST['pokok']));
                $jasa          = validangka(trim($_POST['jasa']));
                $banyak_angsur = validangka(trim($_POST['banyak_angsur']));
                $_sql          = "SELECT * FROM set_tahun";
                $hasil         = bukaquery($_sql);
                $baris         = mysqli_fetch_row($hasil);
                $tahun         = $baris[0];
                $bulan         = $baris[1];
                if(strlen($bulan)==1){
                    $bulan="0".($bulan+1);
                }
                for($interval=1; $interval<=$banyak_angsur; $interval++){
                    InsertData(" angsuran_koperasi ","'$id','$tanggal',DATE_SUB('$tahun-$bulan-05',INTERVAL $interval MONTH),'$pokok','$jasa'");
                }
                echo "<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=BayarPinjam&action=TAMBAH&id=$id&tanggal=$tanggal'></head><body></body></html>";
            }
            
            $_sql   = "select angsuran_koperasi.id,angsuran_koperasi.tanggal_pinjam,angsuran_koperasi.tanggal_angsur,angsuran_koperasi.pokok,angsuran_koperasi.jasa from angsuran_koperasi where angsuran_koperasi.id='$id' and angsuran_koperasi.tanggal_pinjam='$tanggal'";
            $hasil  = bukaquery($_sql);
            $jumlah = mysqli_num_rows($hasil);
            if(mysqli_num_rows($hasil)!=0) {
                echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head'>
                            <td width='5%'><div align='center'>No.</div></td>
                            <td width='15%'><div align='center'>Tgl.Angsur</div></td>
                            <td width='23%'><div align='center'>Pokok</div></td>
                            <td width='23%'><div align='center'>Jasa</div></td>
                            <td width='24%'><div align='center'>Angsuran</div></td>
                        </tr>";
                $i=1;
                while($baris = mysqli_fetch_array($hasil)) {
                  echo "<tr class='isi' title='$baris[2], $baris[3], $baris[4]'>
                            <td>$i</td>
                            <td>$baris[2]</td>
                            <td>".formatDuit($baris[3])."</td>
                            <td>".formatDuit($baris[4])."</td>
                            <td>".formatDuit($baris[3]+$baris[4])."</td>
                       </tr>";$i++;
                }
                echo "</table>";
            } else {
                echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head'>
                            <td width='5%'><div align='center'>No.</div></td>
                            <td width='15%'><div align='center'>Tgl.Angsur</div></td>
                            <td width='23%'><div align='center'>Pokok</div></td>
                            <td width='23%'><div align='center'>Jasa</div></td>
                            <td width='24%'><div align='center'>Angsuran</div></td>
                        </tr>
                     </table>";
            }
        ?>
    </body>
</html>
