<?php
 include '../conf/conf.php';
?>
<html>
    <head>
        <link href="../../style.css" rel="stylesheet" type="text/css" media="screen" />
    </head>
    <body bgcolor='#ffffff'>
        <script type="text/javascript">
            window.onload = function() { window.print(); }
        </script>
    <?php
        reportsqlinjection();
        
        $norujuk     =str_replace("_"," ",$_GET['norujuk']);
        $rujukke     =str_replace("_"," ",$_GET['rujukke']);
        $diagnosa    =str_replace("_"," ",$_GET['diagnosa']);      
        $tanggal     =str_replace("_"," ",$_GET['tanggal']);   
        $dokter      =str_replace("_"," ",$_GET['dokter']);      
        $norm        =str_replace("_"," ",$_GET['norm']);     
        $norw        =str_replace("_"," ",$_GET['norw']);   
        
        $_sql = "select pasien.nm_pasien,pasien.jk,pasien.alamat, pasien.umur
                from pasien where pasien.no_rkm_medis='$norm' ";   
        $hasil=bukaquery($_sql);
        $datapasien=  mysqli_fetch_array($hasil);
        
        if(mysqli_num_rows($hasil)!=0) {             
            echo "   
            <table width='100%' bgcolor='#ffffff' align='left' border='0' class='tbl_form'>
            <tr class='isi12'>
            <td>
                   <table width='100%' bgcolor='#ffffff' align='left' border='0' class='tbl_form'>
                    <tr>
                        <td>
                            <img width='80' height='70' src='images/LOGO.png'/>
                        </td>
                        <td>
                        <center>
                                <font color='999999' size='2'  face='Tahoma'>RUMAH SAKIT</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br/>
                                <font color='999999' size='4'  face='Tahoma'>AMAL SEHAT WONOGIRI</font>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br/>
                                <font color='999999' size='2'  face='Tahoma'>
                                Jl.Ngerjopuro-Slogohimo, Slogohimo, Wonogiri 57694&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br/>
                                Telp. : 0273-5316677, 5316688, SMS AMAL SEHAT : 08132952199&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br/>
                                Email : amal_sehat@telkom.net&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                </font>
                        </center>
                        </td>
                    </tr>
                   </table><hr/>
                        <center>
                                <font color='999999' size='3'  face='Tahoma'>SURAT RUJUKAN</font> <br/>
                        </center>
            </td>
            </tr>
            <td>
            <tr class='isi12'>
            <td>
            <table width='100%' align='center' border='0' align='center' cellpadding='1' cellspacing='1' class='tbl_form'>
               <tr class='isi12'>
                    <td width='25%' ><font color='999999' size='2'  face='Tahoma'>Nomor</td><td width=''>:</td>
                    <td width='75%'><font color='999999' size='2'  face='Tahoma'>$norujuk</td>
                </tr>
                <tr class='isi12'>
                    <td width='25%' ><font color='999999' size='2'  face='Tahoma'>Lampiran</td><td width=''>:</td>
                    <td width='75%'><font color='999999' size='2'  face='Tahoma'>-</td>
                </tr>
            </table>
            <br/>
            <font color='999999' size='2'  face='Tahoma'>Kepada :<br/>
            Yth. $rujukke<br/><br/>
            Dengan hormat,<br/>
            Bersama ini kami kirimkan seorang penderita :</font><br/>
            <table width='100%' align='center' border='0' align='center' cellpadding='1' cellspacing='1' class='tbl_form'>
               <tr class='isi12'>
                    <td width='30%' ><font color='999999' size='2'  face='Tahoma'>Nama</td><td width=''>:</td>
                    <td width='70%'><font color='999999' size='2'  face='Tahoma'>".$datapasien["nm_pasien"]."</td>
                </tr>
                <tr class='isi12'>
                    <td width='30%' ><font color='999999' size='2'  face='Tahoma'>Umur</td><td width=''>:</td>
                    <td width='70%'><font color='999999' size='2'  face='Tahoma'>".$datapasien["umur"]." Th (".$datapasien["jk"].")</td>
                </tr>
                <tr class='isi12'>
                    <td width='30%' ><font color='999999' size='2'  face='Tahoma'>Alamat</td><td width=''>:</td>
                    <td width='70%'><font color='999999' size='2'  face='Tahoma'>".$datapasien["alamat"]."</td>
                </tr>
                <tr class='isi12'>
                    <td width='30%' ><font color='999999' size='2'  face='Tahoma'>Diagnosa</td><td width=''>:</td>
                    <td width='70%'><font color='999999' size='2'  face='Tahoma'>$diagnosa</td>
                </tr>
                <tr class='isi12'>
                    <td width='30%' ><font color='999999' size='2'  face='Tahoma'>Telah Kami Beri Terapi</td><td width=''>:</td>
                    <td width='70%'><font color='999999' size='2'  face='Tahoma'></td>
                </tr>
            </table>
               <font color='999999' size='2'  face='Tahoma'>";     
                $_sqlobat="select databarang.nama_brng
                           from detail_pemberian_obat,databarang 
                           where detail_pemberian_obat.kode_brng=databarang.kode_brng
                           and no_rawat='$norw' group by databarang.nama_brng";
                $hasilobat=bukaquery($_sqlobat);               
                while($obat = mysqli_fetch_array($hasilobat)) {
                    echo "$obat[0], ";
                } 
            echo "<br/><br/>Hasil pemeriksaan penunjang :<br/>";
                $_sqljalandr="select hasil from rawat_jl_dr  where  rawat_jl_dr.no_rawat='$norw' group by hasil";
                $hasiljalandr=bukaquery($_sqljalandr);
                $no=1;
                while($pemeriksaan = mysqli_fetch_array($hasiljalandr)) {
                    if(!empty($pemeriksaan[0])){
                        echo "$no. $pemeriksaan[0]<br/> ";
                       $no++;
                    }
                } 
                $_sqlinapdr="select hasil from rawat_inap_dr  where  rawat_inap_dr.no_rawat='$norw' group by hasil ";
                $hasilinapdr=bukaquery($_sqlinapdr);
                while($pemeriksaan = mysqli_fetch_array($hasilinapdr)) {
                    if(!empty($pemeriksaan[0])){
                        echo "$no. $pemeriksaan[0]<br/> ";
                        $no++;
                    }
                } 
            
            echo"<br/>
                Mohon Penatalaksanaan selanjutnya.<br/>
                Demikian rujukan ini, mohon balasan rujukan (Dx dan Tx). Atas kerjasamanya kami ucapkan terima kasih.<br/><br/>
                Wonogiri, ".substr($tanggal,8,2)."-".substr($tanggal,5,2)."-".substr($tanggal,0,4)."<br/>
                Salan dan Hormat Kami, <br/><br/><br/><br/>
                <u>( $dokter )</u>
                </font>
                
                </td>
                  </tr>
                </table>"; 
        } else {echo "<font color='999999' size='2'  face='Tahoma'><b>Data  Billing masih kosong !</b>";}

    ?>  

    </body>
</html>
