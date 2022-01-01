

<div id="post">
    <h1 class="title">:: Presensi Pulang ::</h1>
    <div class="entry">    
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" enctype=multipart/form-data>
        <?php
                antisqlinjection("?page=TampilPulang");
                echo "";                
                $keyword      =isset($_GET['keyword'])?$_GET['keyword']:NULL;
                echo "<input type=hidden name=keyword value=$keyword>";
        ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="20%" >Keyword</td><td width="">:</td>
                    <td width="80%"><input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="50" maxlength="250" /></td>
                </tr>  
            </table>
              <div align="center"><input name=BtnCari type=submit class="button" value="Cari"/></div>
            <br/>
            
            
    <div style="width: 100%; height: 350px; overflow: auto; ">

    <?php
       
        $order=cleankar(isset($_GET['order']))?cleankar($_GET['order']):NULL;       
        if (empty($order)) $order=1;
        
        $urut="pegawai.nik asc";
        if($order==1){
            $urut="pegawai.nik asc";
        }elseif($order==2){
            $urut="pegawai.nik desc";
        }elseif($order==3){
            $urut="pegawai.nama asc";
        }elseif($order==4){
            $urut="pegawai.nama desc";
        }elseif($order==5){
            $urut="rekap_presensi.shift asc";
        }elseif($order==6){
            $urut="rekap_presensi.shift desc";
        }elseif($order==7){
            $urut="rekap_presensi.jam_datang asc";
        }elseif($order==8){
            $urut="rekap_presensi.jam_datang desc";
        }elseif($order==9){
            $urut="rekap_presensi.jam_pulang asc";
        }elseif($order==10){
            $urut="rekap_presensi.jam_pulang desc";
        }elseif($order==11){
            $urut="rekap_presensi.status asc";
        }elseif($order==12){
            $urut="rekap_presensi.status desc";
        }elseif($order==13){
            $urut="rekap_presensi.keterlambatan asc";
        }elseif($order==14){
            $urut="rekap_presensi.keterlambatan desc";
        }elseif($order==15){
            $urut="rekap_presensi.durasi asc";
        }elseif($order==16){
            $urut="rekap_presensi.durasi desc";
        } 
        
        $_sqlbar        = "select current_date()";
        $hasilbar       = bukaquery($_sqlbar);
        $barisbar       = mysqli_fetch_array($hasilbar);  
        $tgl          = $barisbar[0];
        
        $keyword=trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;
        $keyword= validTeks($keyword);
        $_sql = "SELECT pegawai.id, pegawai.nik, pegawai.nama, rekap_presensi.shift,
                rekap_presensi.jam_datang, rekap_presensi.jam_pulang, rekap_presensi.status, 
                rekap_presensi.keterlambatan, rekap_presensi.durasi,rekap_presensi.keterangan,
                rekap_presensi.photo  from pegawai 
                inner join rekap_presensi on pegawai.id=rekap_presensi.id                 
                where  rekap_presensi.jam_datang like '$tgl' and pegawai.nik like '%".$keyword."%' or
                rekap_presensi.jam_datang like '%$tgl%' and pegawai.nama like '%".$keyword."%' or
                rekap_presensi.jam_datang like '%$tgl%' and rekap_presensi.shift like '%".$keyword."%' or
                rekap_presensi.jam_datang like '%$tgl%' and rekap_presensi.jam_datang like '%".$keyword."%' or
                rekap_presensi.jam_datang like '%$tgl%' and rekap_presensi.status like '%".$keyword."%' or
                rekap_presensi.jam_datang like '%$tgl%' and rekap_presensi.keterlambatan like '%".$keyword."%' 
                order by $urut ";                 

       
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        
        if(mysqli_num_rows($hasil)!=0) { 
            $i=1;
            
            echo "<table width='1600px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='20px'><div align='center'><font size='2' face='Comic Sans Ms'><strong>No</strong></font></div></td>
                        <td width='100px'><div align='center'><font size='2' face='Comic Sans Ms'><strong> <a href='?page=TampilPulang&order=1'> < </a> NIK<a href='?page=TampilPulang&order=2'> > </a> </strong></font></div></td>
                        <td width='200px'><div align='center'><font size='2' face='Comic Sans Ms'><strong> <a href='?page=TampilPulang&order=3'> < </a> Nama <a href='?page=TampilPulang&order=4'> > </a> </strong></font></div></td>
                        <td width='70px'><div align='center'><font size='2' face='Comic Sans Ms'><strong> <a href='?page=TampilPulang&order=5'> < </a> Shift <a href='?page=TampilPulang&order=6'> > </a> </strong></font></div></td>
                        <td width='100px'><div align='center'><font size='2' face='Comic Sans Ms'><strong> <a href='?page=TampilPulang&order=7'> < </a> Jam Datang <a href='?page=TampilPulang&order=8'> > </a> </strong></font></div></td>
                        <td width='100px'><div align='center'><font size='2' face='Comic Sans Ms'><strong> <a href='?page=TampilPulang&order=9'> < </a> Jam Pulang <a href='?page=TampilPulang&order=10'> > </a> </strong></font></div></td>
                        <td width='100px'><div align='center'><font size='2' face='Comic Sans Ms'><strong> <a href='?page=TampilPulang&order=11'> < </a> Status <a href='?page=TampilPulang&order=12'> > </a> </strong></font></div></td>
                        <td width='100px'><div align='center'><font size='2' face='Comic Sans Ms'><strong> <a href='?page=TampilPulang&order=13'> < </a> Keterlambatan<a href='?page=TampilPulang&order=14'> > </a> </strong></font></div></td>
                        <td width='100px'><div align='center'><font size='2' face='Comic Sans Ms'><strong> <a href='?page=TampilPulang&order=15'> < </a> Durasi <a href='?page=TampilPulang&order=16'> > </a> </strong></font></div></td>
                        <td width='100px'><div align='center'><font size='2' face='Comic Sans Ms'><strong> Keterangan </strong></font></div></td>
                        <td width='100px'><div align='center'><font size='2' face='Comic Sans Ms'><strong> Photo </strong></font></div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {            
                        $gb="-";
                        if($baris[10]=="pages/pegawai/"){
                            $gb="-";                            
                        }else{
                            $gb="<img src='$baris[10]' width='120px' height='120px'>";
                        }
                        echo "<tr class='isi' title='$baris[0], $baris[1], $baris[2], $baris[3], $baris[4], $baris[5], $baris[6], $baris[7]'>
                                <td valign='Top'>$i</td>
                                <td valign='Top'>$baris[1]</td>
                                <td valign='Top'>$baris[2]</td>   
                                <td valign='Top'>$baris[3]</td>         
                                <td valign='Top'>$baris[4]</td>         
                                <td valign='Top'>$baris[5]</td>         
                                <td valign='Top'>$baris[6]</td>         
                                <td valign='Top'>$baris[7]</td>         
                                <td valign='Top'>$baris[8]</td>        
                                <td valign='Top'>$baris[9]<br/>
                                    <a href=?page=GantiKeterangan&pageion=UBAH&id=".str_replace(" ","_",$baris[0])."&jam_datang=".str_replace(" ","_",$baris[4]).">[Ubah]</a>
                                </td>       
                                <td valign='Top' align='center'>$gb</a></td>                          
                             </tr>";$i++;
                    }
            echo "</table>";
            
        } else {echo "<b>Data Presensi Masuk masih kosong !</b>";}

    ?>   
    </div>
              
 </form>
    <?php
        if(mysqli_num_rows($hasil)!=0) {
            echo("Data : $jumlah ");
        }
    ?>
    </div>
</div>

