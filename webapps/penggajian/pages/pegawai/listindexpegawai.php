

<div id="post">
    <div class="entry">   

    <div align="center" class="link">
        <a href=?act=InputPegawai&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListCariPegawai>| List Data |</a>
        <a href=?act=ListIndexPegawai>| Index Pegawai |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
            $_sqlthn         = "SELECT * FROM set_tahun";
            $hasilthn        = bukaquery($_sqlthn);
            $baristhn        = mysqli_fetch_row($hasilthn);
            $tahun           = $baristhn[0];
            $blnini          =$baristhn[1];
            $hari            =$baristhn[2];
            $bln_leng=strlen($blnini);
            $bulan="0";
            if ($bln_leng==1){
                $bulan="0".$blnini;
            }else{
                $bulan=$blnini;
            }
  
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $keyword     =isset($_GET['keyword'])?$_GET['keyword']:NULL;
                echo "<input type=hidden name=keyword value=$keyword><input type=hidden name=action value=$action>";
        ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="25%" >Keyword</td><td width="">:</td>
                    <td width="82%"><input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="65" maxlength="250" />
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;">
                        <input name=BtnPrint type=submit class="button" value="&nbsp;Print&nbsp;">
                    </td>
                </tr>
            </table><br>
            
    <div style="width: 100%; height: 78%; overflow: auto;">
    <?php
        $awal=isset($_GET['awal'])?$_GET['awal']:NULL;
        $keyword=trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;
        if (empty($awal)) $awal=0;
        $say=" pegawai.pendidikan=pendidikan.tingkat
                and pegawai.stts_kerja =stts_kerja.stts
                and pegawai.jnj_jabatan=jnj_jabatan.kode ";
        $_sql = "select pegawai.id,
                pegawai.nik,
                pegawai.nama,
                pegawai.jbtn,
                pegawai.pendidikan,
                pegawai.mulai_kerja,
                CONCAT(FLOOR(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'),DATE_FORMAT(mulai_kerja, '%Y%m'))/12), ' Tahun ',MOD(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'), DATE_FORMAT(mulai_kerja, '%Y%m')),12), ' Bulan ') as lama,
                pendidikan.indek as index_pendidikan,
                (To_days('$tahun-$bulan-$hari')-to_days(mulai_kerja))/365 as masker,
                stts_kerja.indek as index_status,
                pegawai.indek as index_struktural,
                pegawai.pengurang,
                pegawai.mulai_kontrak,
                CONCAT(FLOOR(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'),DATE_FORMAT(mulai_kontrak, '%Y%m'))/12), ' Tahun ',MOD(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'), DATE_FORMAT(mulai_kontrak, '%Y%m')),12), ' Bulan ') as lamakontrak,
                (To_days('$tahun-$bulan-$hari')-to_days(mulai_kontrak))/365 as maskon,
                cuti_diambil,
                dankes
                from pegawai inner join pendidikan
                inner join stts_kerja
                inner join jnj_jabatan
                where ".$say." and pegawai.nik like '%".$keyword."%' or
                ".$say." and pegawai.nama like '%".$keyword."%' or
                ".$say." and pegawai.jbtn like '%".$keyword."%' or
                ".$say." and pegawai.pendidikan like '%".$keyword."%' or
                ".$say." and pegawai.mulai_kerja like '%".$keyword."%' 
                order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        
        if(mysqli_num_rows($hasil)!=0) {            
            echo "<table width='2650px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                                 <td width='60px'><div align='center'>Proses</div></td>
                                 <td width='80px'><div align='center'>NIP</div></td>
                                 <td width='190px'><div align='center'>Nama</div></td>
                                 <td width='100px'><div align='center'>Jabatan</div></td>
                                 <td width='150px'><div align='center'>Pendidikan</div></td>
                                 <td width='80px'><div align='center'>Mulai Kerja</div></td>
                                 <td width='150px'><div align='center'>Lama Kerja</div></td>
                                 <td width='80px'><div align='center'>Index Pendidikan</div></td>
                                 <td width='80px'><div align='center'>Index Masa Kerja</div></td>
                                 <td width='100px'><div align='center'>Index Status</div></td>
                                 <td width='80px'><div align='center'>Index Struktural</div></td>
                                 <td width='80px'><div align='center'>Pengurang</div></td>
                                 <td width='100px'><div align='center'>Total Index</div></td>
                                 <td width='80px'><div align='center'>Mulai Kontrak</div></td>
                                 <td width='150px'><div align='center'>Lama Kontrak</div></td>
                                 <td width='150px'><div align='center'>Gaji Pokok</div></td>
                                 <td width='100px'><div align='center'>Hak Cuti</div></td>
                                 <td width='100px'><div align='center'>Cuti Diambil</div></td>
                                 <td width='100px'><div align='center'>Sisa Cuti</div></td>
                                 <td width='100px'><div align='center'>DanKes</div></td>   
                                 <td width='100px'><div align='center'>Sisa DanKes</div></td>                
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                           $_sql4    = "SELECT `gapok1`, `kenaikan`, `maksimal`
                            from pendidikan  where tingkat='$baris[4]' ";
                            $hasil4   = bukaquery($_sql4);
                            $baris4   = mysqli_fetch_array($hasil4);
                            $gapok     = 0;
                            @$gapok1    = $baris4["gapok1"];
                            @$kenaikan  = $baris4["kenaikan"];
                            @$maksimal  = $baris4["maksimal"];
                            $hakcuti   = 0;

                            $_sql6    = "SELECT sum(jml)
                            from ketidakhadiran  where id='$baris[0]'
                            and tgl like '%".$tahun."%' and jns='C' group by id";
                            $hasil6   = bukaquery($_sql6);
                            $baris6   = mysqli_fetch_row($hasil6);
                            if(empty ($baris6[0])){
                                $ttlc=0;
                            }

                            $ttlc     = $baris6[0]+$baris[15];
                            
                          $masa_kerja=0;
                          if($baris[8]<1){
                             $masa_kerja=0;
                          }else if(($baris[8]>=1)&&($baris[8]<2)){
                             $masa_kerja=2;
                          }else if(($baris[8]>=2)&&($baris[8]<3)){
                             $masa_kerja=4;
                          }else if(($baris[8]>=3)&&($baris[8]<4)){
                             $masa_kerja=6;
                          }else if(($baris[8]>=4)&&($baris[8]<5)){
                             $masa_kerja=8;
                          }else if(($baris[8]>=5)&&($baris[8]<6)){
                             $masa_kerja=10;
                          }else if(($baris[8]>=6)&&($baris[8]<7)){
                             $masa_kerja=12;
                          }else if($baris[8]>=7){
                             $masa_kerja=14;
                          }
                          
                          if($baris[14]<$maksimal){
                             $gapok=$gapok1+($kenaikan*round($baris[14]));
                             $hakcuti=12;
                          }elseif($baris[14]>=$maksimal){
                             $gapok=$gapok1+($kenaikan*$maksimal);
                             $hakcuti=14;
                          }
                          
                          $total=0;
                          if($baris[11]==0){
                              $total=($baris[7]+$masa_kerja+$baris[9]+$baris[10]);
                          }else if($baris[11]>0){
                              $total=($baris[7]+$masa_kerja+$baris[9]+$baris[10])*($baris[11]/100);
                          }
                          
                        echo "<tr class='isi' title='$baris[1] $baris[2]'>
                                  <td width='70'>
                                        <center>
                                        <a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>[edit]</a>";
                           echo "       </center>
                                </td>						        
                                 <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[1]</a></td>
                                 <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[2]</a></td>
                                 <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[3]</a></td>
                                 <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[4]</a></td>
                                 <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[5]</a></td>
                                 <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[6]</a></td>
                                 <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[7]</a></td>
                                 <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$masa_kerja</a></td>
                                 <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[9]</a></td>
                                 <td><a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>$baris[10]</a></td>
                                  <td><a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>$baris[11] %</a></td>
                                  <td><a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>$total</a></td>
                                  <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[12]</a></td>
                                  <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$baris[13]</a></td>
                                  <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".formatDuit($gapok)."</a></td>
                                  <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$hakcuti</a></td>
                                  <td><a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>$ttlc</a></td>
                                  <td><a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>".($hakcuti-$ttlc)."</a></td>
                                  <td><a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>".formatDuit($baris[16])."</a></td>
                                  <td><a href=?act=SisaDankes&id=$baris[0]&action=TAMBAH>".formatDuit($baris[16]-getOne("select sum(dankes) from ambil_dankes where id='$baris[0]' and tanggal like '%$tahun%'"))."</a></td>
                               </tr>";
                    }
            echo "</table>";
            
        } else {echo "Data Index Pegawai masih kosong !";}

       $BtnPrint=isset($_POST['BtnPrint'])?$_POST['BtnPrint']:NULL;
       if (isset($BtnPrint)) {
           echo"<html><head><title></title><meta http-equiv='refresh' content='2;pages/pegawai/LaporanIndex.php?&keyword=$keyword'></head><body></body></html>";

       }
    ?>
    </div>
            </form>
       <?php
            if(mysqli_num_rows($hasil)!=0) {
                $say=" pegawai.pendidikan=pendidikan.tingkat
                and pegawai.stts_kerja =stts_kerja.stts
                and pegawai.jnj_jabatan=jnj_jabatan.kode ";
                $hasil1=bukaquery("select pegawai.id,
                pegawai.nik,
                pegawai.nama,
                pegawai.jbtn,
                pegawai.pendidikan,
                pegawai.mulai_kerja,
                CONCAT(FLOOR(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'),DATE_FORMAT(mulai_kerja, '%Y%m'))/12), ' Tahun ',MOD(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'), DATE_FORMAT(mulai_kerja, '%Y%m')),12), ' Bulan ') as lama,
                pendidikan.indek as index_pendidikan,
                (To_days('$tahun-$bulan-$hari')-to_days(mulai_kerja))/365 as masker,
                stts_kerja.indek as index_status,
                pegawai.indek as index_struktural,
                pegawai.pengurang,
                pegawai.mulai_kontrak,
                CONCAT(FLOOR(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'),DATE_FORMAT(mulai_kontrak, '%Y%m'))/12), ' Tahun ',MOD(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'), DATE_FORMAT(mulai_kontrak, '%Y%m')),12), ' Bulan ') as lamakontrak,
                (To_days('$tahun-$bulan-$hari')-to_days(mulai_kontrak))/365 as maskon
                from pegawai inner join pendidikan
                inner join stts_kerja
                inner join jnj_jabatan
                where ".$say." and pegawai.nik like '%".$keyword."%' or
                ".$say." and pegawai.nama like '%".$keyword."%' or
                ".$say." and pegawai.jbtn like '%".$keyword."%' or
                ".$say." and pegawai.pendidikan like '%".$keyword."%' or
                ".$say." and pegawai.mulai_kontrak like '%".$keyword."%' or
                ".$say." and pegawai.mulai_kerja like '%".$keyword."%'
                order by pegawai.id ASC");
                $jumladiv=mysqli_num_rows($hasil1);
                $i=$jumladiv/19;
                $i=ceil($i);
                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah <a target=_blank href=../penggajian/pages/pegawai/LaporanIndex.php?&keyword=$keyword>| Laporan |</a></div></td>                        
                    </tr>     
                 </table>");
             }
       ?>
    </div>
</div>
