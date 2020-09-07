<div id="post">
    <div class="entry">   
	<form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
            $tgl1     =isset($_GET['tgl1'])?$_GET['tgl1']:NULL;
            $tgl2     =isset($_GET['tgl2'])?$_GET['tgl2']:NULL;
            $action   =isset($_GET['action'])?$_GET['action']:NULL;
            $keyword  =str_replace("_"," ",isset($_GET['keyword']))?str_replace("_"," ",$_GET['keyword']):NULL;
            $ruang    =str_replace("_"," ",isset($_GET['ruang']))?str_replace("_"," ",$_GET['ruang']):NULL;
            $sttssurat=str_replace("_"," ",isset($_GET['sttssurat']))?str_replace("_"," ",$_GET['sttssurat']):NULL;
            $sttsbalas=str_replace("_"," ",isset($_GET['sttsbalas']))?str_replace("_"," ",$_GET['sttsbalas']):NULL;
        ?>
    <div style="width: 100%; height: 99.9%; overflow: auto;">
    <?php        
	$_sql = "select surat_masuk.no_urut,surat_masuk.no_surat,surat_masuk.asal,surat_masuk.tujuan,
                surat_masuk.tgl_surat,surat_masuk.perihal,surat_masuk.tgl_terima,surat_masuk.kd_lemari,
                surat_lemari.lemari,surat_masuk.kd_rak,surat_rak.rak,surat_masuk.kd_map,surat_map.map,
                surat_masuk.kd_ruang,surat_ruang.ruang,surat_masuk.kd_sifat,surat_sifat.sifat,
                surat_masuk.lampiran,surat_masuk.tembusan,surat_masuk.tgl_deadline_balas,surat_masuk.kd_balas,
                surat_balas.balas,surat_masuk.keterangan,surat_masuk.kd_status,surat_status.status,
                surat_masuk.kd_klasifikasi,surat_klasifikasi.klasifikasi,surat_masuk.file_url
                from surat_masuk inner join surat_lemari inner join surat_rak inner join surat_map 
                inner join surat_ruang inner join surat_sifat inner join surat_balas inner join surat_status
                inner join surat_klasifikasi on surat_masuk.kd_klasifikasi=surat_klasifikasi.kd 
                and surat_status.kd=surat_masuk.kd_status and surat_balas.kd=surat_masuk.kd_balas and 
                surat_masuk.kd_sifat=surat_sifat.kd and surat_masuk.kd_ruang=surat_ruang.kd and 
                surat_lemari.kd=surat_masuk.kd_lemari and surat_masuk.kd_rak=surat_rak.kd and surat_masuk.kd_map=surat_map.kd
                where surat_status.status like '%".$sttssurat."%' and  surat_ruang.ruang like '%".$ruang."%' and surat_balas.balas like '%".$sttsbalas."%' and surat_masuk.tgl_terima between '".$tgl1."' and '".$tgl2."' and  surat_masuk.no_surat like '%".$keyword."%' or 
                surat_status.status like '%".$sttssurat."%' and  surat_ruang.ruang like '%".$ruang."%' and surat_balas.balas like '%".$sttsbalas."%' and surat_masuk.tgl_terima between '".$tgl1."' and '".$tgl2."' and  surat_masuk.asal like '%".$keyword."%' or 
                surat_status.status like '%".$sttssurat."%' and  surat_ruang.ruang like '%".$ruang."%' and surat_balas.balas like '%".$sttsbalas."%' and surat_masuk.tgl_terima between '".$tgl1."' and '".$tgl2."' and  surat_masuk.tujuan like '%".$keyword."%' or 
                surat_status.status like '%".$sttssurat."%' and  surat_ruang.ruang like '%".$ruang."%' and surat_balas.balas like '%".$sttsbalas."%' and surat_masuk.tgl_terima between '".$tgl1."' and '".$tgl2."' and  surat_masuk.lampiran like '%".$keyword."%' or 
                surat_status.status like '%".$sttssurat."%' and  surat_ruang.ruang like '%".$ruang."%' and surat_balas.balas like '%".$sttsbalas."%' and surat_masuk.tgl_terima between '".$tgl1."' and '".$tgl2."' and  surat_masuk.tembusan like '%".$keyword."%' or 
                surat_status.status like '%".$sttssurat."%' and  surat_ruang.ruang like '%".$ruang."%' and surat_balas.balas like '%".$sttsbalas."%' and surat_masuk.tgl_terima between '".$tgl1."' and '".$tgl2."' and  surat_sifat.sifat like '%".$keyword."%' or 
                surat_status.status like '%".$sttssurat."%' and  surat_ruang.ruang like '%".$ruang."%' and surat_balas.balas like '%".$sttsbalas."%' and surat_masuk.tgl_terima between '".$tgl1."' and '".$tgl2."' and  surat_masuk.keterangan like '%".$keyword."%' or 
                surat_status.status like '%".$sttssurat."%' and  surat_ruang.ruang like '%".$ruang."%' and surat_balas.balas like '%".$sttsbalas."%' and surat_masuk.tgl_terima between '".$tgl1."' and '".$tgl2."' and  surat_klasifikasi.klasifikasi like '%".$keyword."%' order by surat_masuk.tgl_terima desc ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='2500px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head2'>
                        <td width='100px'><div align='center'>Proses</div></td>
                        <td width='90px'><div align='center'>No.Masuk</div></td>
                        <td width='100px'><div align='center'>No.Surat</div></td>
                        <td width='160px'><div align='center'>Asal</div></td>
                        <td width='160px'><div align='center'>Tujuan</div></td>
                        <td width='70px'><div align='center'>Tgl.Surat</div></td>
                        <td width='160px'><div align='center'>Perihal</div></td>
                        <td width='70px'><div align='center'>Tgl.Terima</div></td>
                        <td width='140px'><div align='center'>Almari</div></td>
                        <td width='130px'><div align='center'>Rak</div></td>
                        <td width='130px'><div align='center'>Map</div></td>
                        <td width='140px'><div align='center'>Ruang</div></td>
                        <td width='130px'><div align='center'>Sifat</div></td>
                        <td width='160px'><div align='center'>Lampiran</div></td>
                        <td width='160px'><div align='center'>Tembusan</div></td>
                        <td width='70px'><div align='center'>Deadline</div></td>
                        <td width='110px'><div align='center'>Balas</div></td>
                        <td width='130px'><div align='center'>Keterangan</div></td>
                        <td width='120px'><div align='center'>Status</div></td>
                        <td width='120px'><div align='center'>Klasifikasi</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {                        
                        echo "<tr class='isi'>
                                <td align='center'>
                                   <a href=?act=List&action=HAPUS&no_urut=".$baris["no_urut"]."&file_url=".$baris["file_url"]."&tgl1=".$_GET["tgl1"]."&tgl2=".$_GET["tgl2"]."&ruang=".$_GET["ruang"]."&sttssurat=".$_GET["sttssurat"]."&sttsbalas=".$_GET["sttsbalas"]."&keyword=".$_GET["keyword"].">[Hapus]</a>
                                   <a target=_blank href=../surat/pages/upload/".$baris["file_url"].">[Berkas]</a>
                                </td>
                                <td align='left'>".$baris["no_urut"]."</td>
                                <td align='left'>".$baris["no_surat"]."</td>
                                <td align='left'>".$baris["asal"]."</td>
                                <td align='left'>".$baris["tujuan"]."</td>
                                <td align='center'>".$baris["tgl_surat"]."</td>
                                <td align='left'>".$baris["perihal"]."</td>
                                <td align='center'>".$baris["tgl_terima"]."</td>
                                <td align='left'>".$baris["lemari"]."</td>
                                <td align='left'>".$baris["rak"]."</td>
                                <td align='left'>".$baris["map"]."</td>
                                <td align='left'>".$baris["ruang"]."</td>
                                <td align='left'>".$baris["sifat"]."</td>
                                <td align='left'>".$baris["lampiran"]."</td>
                                <td align='left'>".$baris["tembusan"]."</td>
                                <td align='center'>".$baris["tgl_deadline_balas"]."</td>
                                <td align='center'>".$baris["balas"]."</td>
                                <td align='left'>".$baris["keterangan"]."</td>
                                <td align='center'>".$baris["status"]."</td>
                                <td align='center'>".$baris["klasifikasi"]."</td>
                             </tr>";
                    }
            echo "</table>";           
        } else {echo "<table width='3300px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head2'>
                            <td width='130px'><div align='center'>Proses</div></td>
                            <td width='80px'><div align='center'>No.Urut</div></td>
                            <td width='80px'><div align='center'>No.Surat</div></td>
                            <td width='150px'><div align='center'>Asal</div></td>
                            <td width='150px'><div align='center'>Tujuan</div></td>
                            <td width='80px'><div align='center'>Tgl.Surat</div></td>
                            <td width='150px'><div align='center'>Perihal</div></td>
                            <td width='80px'><div align='center'>Tgl.Terima</div></td>
                            <td width='150px'><div align='center'>Almari</div></td>
                            <td width='130px'><div align='center'>Rak</div></td>
                            <td width='130px'><div align='center'>Map</div></td>
                            <td width='150px'><div align='center'>Ruang</div></td>
                            <td width='130px'><div align='center'>Sifat</div></td>
                            <td width='150px'><div align='center'>Lampiran</div></td>
                            <td width='150px'><div align='center'>Tembusan</div></td>
                            <td width='80px'><div align='center'>Deadline Balas</div></td>
                            <td width='100px'><div align='center'>Balas</div></td>
                            <td width='120px'><div align='center'>Keterangan</div></td>
                            <td width='100px'><div align='center'>Status</div></td>
                            <td width='100px'><div align='center'>Klasifikasi</div></td>
                        </tr>
                      </table>";        
        }        
        
        if ($action=="HAPUS") {                
            unlink($_GET['file_url']);
            Hapus(" surat_masuk "," no_urut ='".$_GET['no_urut']."' ","?act=List&tgl1=".$_GET['tgl1']."&tgl2=".$_GET['tgl2']."&ruang=".$_GET['ruang']."&sttssurat=".$_GET['sttssurat']."&sttsbalas=".$_GET['sttsbalas']."&keyword=".$_GET['keyword']);
        }
    ?>
    </div>
	</form>
    </div>
</div>

