

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
            $blnini          = $baristhn[1];
            $hari            = $baristhn[2];
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
        $keyword= validTeks($keyword);
        if (empty($awal)) $awal=0;
        $_sql = "select pegawai.id,pegawai.nik,pegawai.nama,pegawai.jbtn,pegawai.pendidikan,pegawai.mulai_kerja,
                kelompok_jabatan.indek as indekkelompok,resiko_kerja.indek as indekresiko,emergency_index.indek as indekemergency,
                jnj_jabatan.indek as indekjabatan,CONCAT(FLOOR(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'),
                DATE_FORMAT(mulai_kerja, '%Y%m'))/12), ' Tahun ',MOD(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'), DATE_FORMAT(mulai_kerja, '%Y%m')),12), ' Bulan ') as lama,
                pendidikan.indek as index_pendidikan,(To_days('$tahun-$bulan-$hari')-to_days(mulai_kerja))/365 as masker,stts_kerja.indek as index_status,
                pegawai.indek as index_struktural,pegawai.pengurang,pegawai.mulai_kontrak,CONCAT(FLOOR(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'),DATE_FORMAT(mulai_kontrak, '%Y%m'))/12), ' Tahun ',MOD(PERIOD_DIFF(DATE_FORMAT('$tahun-$bulan-$hari', '%Y%m'), DATE_FORMAT(mulai_kontrak, '%Y%m')),12), ' Bulan ') as lamakontrak,
                (To_days('$tahun-$bulan-$hari')-to_days(mulai_kontrak))/365 as maskon, pegawai.cuti_diambil,pegawai.dankes
                from pegawai inner join pendidikan inner join stts_kerja inner join kelompok_jabatan inner join resiko_kerja inner join emergency_index
                inner join jnj_jabatan on pegawai.pendidikan=pendidikan.tingkat and pegawai.stts_kerja=stts_kerja.stts and pegawai.jnj_jabatan=jnj_jabatan.kode
                and pegawai.kode_kelompok=kelompok_jabatan.kode_kelompok and pegawai.kode_resiko=resiko_kerja.kode_resiko and pegawai.kode_emergency=emergency_index.kode_emergency
                where pegawai.nik like '%".$keyword."%' or pegawai.nama like '%".$keyword."%' or pegawai.jbtn like '%".$keyword."%' or 
                pegawai.pendidikan like '%".$keyword."%' or pegawai.mulai_kerja like '%".$keyword."%' order by pegawai.id ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        
        if(mysqli_num_rows($hasil)!=0) {            
            echo "<table width='2950px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
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
                         <td width='100px'><div align='center'>Index Jenjang Jabatan</div></td>
                         <td width='100px'><div align='center'>Index Kelompok Jabatan</div></td>
                         <td width='100px'><div align='center'>Index Resiko Kerjo</div></td>
                         <td width='100px'><div align='center'>Index Tingkat Emergency</div></td>
                         <td width='100px'><div align='center'>Index Evaluasi Kinerja</div></td>
                         <td width='100px'><div align='center'>Index Pencapaian Kinerja</div></td>
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
                        from pendidikan  where tingkat='".$baris["pendidikan"]."' ";
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

                        $ttlc     = $baris6[0]+$baris["cuti_diambil"]+getOne("select sum(jumlah) from pengajuan_cuti where tanggal_awal like '%".$tahun."%' and status='Disetujui' and nik='".$baris["nik"]."'");
                            
                        $masa_kerja=0;
                        if($baris["masker"]<1){
                            $masa_kerja=0;
                        }else if(($baris["masker"]>=1)&&($baris["masker"]<2)){
                            $masa_kerja=2;
                        }else if(($baris["masker"]>=2)&&($baris["masker"]<3)){
                            $masa_kerja=4;
                        }else if(($baris["masker"]>=3)&&($baris["masker"]<4)){
                            $masa_kerja=6;
                        }else if(($baris["masker"]>=4)&&($baris["masker"]<5)){
                            $masa_kerja=8;
                        }else if(($baris["masker"]>=5)&&($baris["masker"]<6)){
                            $masa_kerja=10;
                        }else if(($baris["masker"]>=6)&&($baris["masker"]<7)){
                            $masa_kerja=12;
                        }else if($baris["masker"]>=7){
                            $masa_kerja=14;
                        }

                        if($baris["maskon"]<$maksimal){
                            $gapok=$gapok1+($kenaikan*round($baris["maskon"]));
                            $hakcuti=12;
                        }elseif($baris["maskon"]>=$maksimal){
                            $gapok=$gapok1+($kenaikan*$maksimal);
                            $hakcuti=14;
                        }

                        $indexevaluasi= getOne("select evaluasi_kinerja.indek from evaluasi_kinerja inner join evaluasi_kinerja_pegawai 
                                                on evaluasi_kinerja_pegawai.kode_evaluasi=evaluasi_kinerja.kode_evaluasi where 
                                                evaluasi_kinerja_pegawai.id='$baris[0]' order by evaluasi_kinerja_pegawai.tahun,
                                                evaluasi_kinerja_pegawai.bulan desc limit 1");
                        if(empty($indexevaluasi)){
                            $indexevaluasi=0;
                        }
                        
                        $indexpencapaian= getOne("select pencapaian_kinerja.indek from pencapaian_kinerja inner join pencapaian_kinerja_pegawai 
                                                on pencapaian_kinerja_pegawai.kode_pencapaian=pencapaian_kinerja.kode_pencapaian where 
                                                pencapaian_kinerja_pegawai.id='$baris[0]' order by pencapaian_kinerja_pegawai.tahun,
                                                pencapaian_kinerja_pegawai.bulan desc limit 1");
                        if(empty($indexpencapaian)){
                            $indexpencapaian=0;
                        }
                        
                        $total=0;
                        if($baris["pengurang"]==0){
                            $total=($baris["index_pendidikan"]+$masa_kerja+$baris["index_status"]+$baris["index_struktural"]+
                                    $baris["indekjabatan"]+$baris["indekkelompok"]+$baris["indekresiko"]+$baris["indekemergency"]+
                                    $indexevaluasi+$indexpencapaian);
                        }else if($baris["pengurang"]>0){
                            $total=($baris["index_pendidikan"]+$masa_kerja+$baris["index_status"]+$baris["index_struktural"]+
                                    $baris["indekjabatan"]+$baris["indekkelompok"]+$baris["indekresiko"]+$baris["indekemergency"]+
                                    $indexevaluasi+$indexpencapaian)*($baris["pengurang"]/100);
                        }
                          
                        echo "<tr class='isi' title='$baris[1] $baris[2]'>
                                  <td width='70'>
                                        <center>
                                        <a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>[edit]</a>";
                           echo "       </center>
                                </td>						        
                                <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["nik"]."</a></td>
                                <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["nama"]."</a></td>
                                <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["jbtn"]."</a></td>
                                <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["pendidikan"]."</a></td>
                                <td align='center'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["mulai_kerja"]."</a></td>
                                <td align='center'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["lama"]."</a></td>
                                <td align='center'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["index_pendidikan"]."</a></td>
                                <td align='center'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$masa_kerja</a></td>
                                <td align='center'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["index_status"]."</a></td>
                                <td align='center'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["indekjabatan"]."</a></td>
                                <td align='center'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["indekkelompok"]."</a></td>
                                <td align='center'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["indekresiko"]."</a></td>
                                <td align='center'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["indekemergency"]."</a></td>
                                <td align='center'><a href=?act=DetailEvaluasiKinerja&action=TAMBAH&id=$baris[0]>".$indexevaluasi."</a></td>
                                <td align='center'><a href=?act=DetailPencapaianKinerja&action=TAMBAH&id=$baris[0]>".$indexpencapaian."</a></td>
                                <td align='center'><a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>".$baris["index_struktural"]."</a></td>
                                <td align='center'><a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>".$baris["pengurang"]." %</a></td>
                                <td align='center'><a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>$total</a></td>
                                <td align='center'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["mulai_kontrak"]."</a></td>
                                <td align='center'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".$baris["lamakontrak"]."</a></td>
                                <td><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>".formatDuit($gapok)."</a></td>
                                <td align='center'><a href=?act=InputPegawai&action=UBAH&id=$baris[0]>$hakcuti</a></td>
                                <td align='center'><a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>$ttlc</a></td>
                                <td align='center'><a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>".($hakcuti-$ttlc)."</a></td>
                                <td><a href=?act=EditIndexPegawai&action=UBAH&id=$baris[0]>".formatDuit($baris["dankes"])."</a></td>
                                <td><a href=?act=SisaDankes&id=$baris[0]&action=TAMBAH>".formatDuit($baris["dankes"]-getOne("select sum(dankes) from ambil_dankes where id='$baris[0]' and tanggal like '%$tahun%'"))."</a></td>
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
            if($jumlah>0) {
                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah | <a target=_blank href=../penggajian/pages/pegawai/LaporanIndex.php?&keyword=$keyword> Laporan </a> | <a target=_blank href=../penggajian/pages/pegawai/LaporanIndexExcel.php?&keyword=$keyword>Excel</a> |</div></td>                            
                    </tr>     
                 </table>");
             }
       ?>
    </div>
</div>
