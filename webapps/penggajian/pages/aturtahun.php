
<div id="post">
    <div align="center" class="link">
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div> 
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      =isset($_GET['action']) ? $_GET['action']:NULL ;
                $tahun       =isset($_GET['tahun']) ? $_GET['tahun']:date("y");
                $bulan       =isset($_GET['bulan']) ? $_GET['bulan']:date("m");
                echo "<input type=hidden name=tahun value=$tahun><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Tahun Gaji</td><td width="">:</td>
                    <td width="67%">
                        <select name="tahun" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                loadThn2();
                            ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Bulan Gaji</td><td width="">:</td>
					<td width="67%">
                       <select name="bulan" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                loadBln();
                            ?>
                        </select>
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>       
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=  isset($_POST['BtnSimpan'])? $_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {                    
                    $bulan          = trim(isset($_POST['bulan'])) ? trim($_POST['bulan']):"01";
                    $bln_leng=strlen(trim($_POST['bulan']));
                    if ($bln_leng==1){
                        $bulan="0".$bulan;
                    }
                    
                    $tahun   =  trim(isset($_POST['tahun']))?trim($_POST['tahun']):"2015";
                    $jumHari = cal_days_in_month(CAL_GREGORIAN,$bulan,$tahun);
                    $date1   = "01-".$bulan."-".$tahun;
                    $date2   = $jumHari."-".$bulan."-".$tahun;

                    $pecahTgl1 = explode("-", $date1);
                    $tgl1 = $pecahTgl1[0];
                    $bln1 = $pecahTgl1[1];
                    $thn1 = $pecahTgl1[2];
                    $i = 0;
                    // counter untuk jumlah hari minggu
                    $sum = 0;
                    do{
                        // mengenerate tanggal berikutnya
                        $tanggal = date("d-m-Y", mktime(0, 0, 0, $bln1, $tgl1+$i, $thn1));

                        // cek jika harinya minggu, maka counter $sum bertambah satu, lalu tampilkan tanggalnya
                        if (date("w", mktime(0, 0, 0, $bln1, $tgl1+$i, $thn1)) == 0){
                            $sum++;
                        }
                        // increment untuk counter looping
                        $i++;
                    }while ($tanggal != $date2);

                    $selisihhari=$jumHari-$sum;
                    
                    
                    if ((!empty($tahun))&&(!empty($bulan))) {
                        $action      =isset($_GET['action']) ? $_GET['action']:"TAMBAH" ;
                        switch($action) {
                            case "TAMBAH":
                                    Tambah(" set_tahun "," '$tahun','$bulan','$jumHari','$sum','$selisihhari'", " Seting Tahun & Bulan gaji " );
                                    echo"<meta http-equiv='refresh' content='1;URL=?act=InputTahun&action=TAMBAH&bulan=$bulan&tahun=$tahun'>";
                                    break;                                
                        }
                    }else if ((empty($tahun))||(empty($bulan))){
                        echo 'Semua field harus isi..!!!';
                        /*echo $jumHari;
                        echo "<br>".$date1."<br>".$date2."<br>".$sum."<br>".$selisihhari;*/
                    }
                }
            ?>
            <div style="width: 100%; height: 100px; overflow: auto;">
            <?php

                $_sql = "SELECT * FROM set_tahun ORDER BY tahun";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                $masuk=0;
                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='17%'><div align='center'>Tahun Gaji</div></td>
                                <td width='17%'><div align='center'>Bulan gaji</div></td>
                                <td width='17%'><div align='center'>Jumlah Hari</div></td>
                                <td width='17%'><div align='center'>Jumlah Akhad</div></td>
                                <td width='17%'><div align='center'>Normal Masuk</div></td>
                                <td width='15%'><div align='center'>Proses</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        $masuk=$baris[4];
                      echo "<tr class='isi'>
                                <td>$baris[0]</td>
                                <td>$baris[1]</td>
                                <td>$baris[2]</td>
                                <td>$baris[3]</td>
                                <td>$baris[4]</td>
                                <td width='120'>
                                    <center>"; ?>
                                    <a href="?act=InputTahun&action=HAPUS&tahun=<?php print $baris[0] ?>&bulan=<?php print $baris[1] ?>" >hapus</a>
                            <?php
                            echo "</center>
                                </td>
                           </tr>";
                    }
                echo "</table><br>";
        ?>
            </div>
            &nbsp;Libur Nasional dibulan ini :
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Tgl.Libur Nasional</td><td width="">:</td>
                    <td width="67%">
                        <select name="tanggal2" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                loadTgl();
                            ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Keterangan</td><td width="">:</td>
                    <td width="67%"><input name="ktg" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo isset($ktg)?$ktg:NULL;?>" size="45" maxlength="40" />
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan2 type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <div style="width: 100%; height: 200px; overflow: auto;">                
            <?php
            $BtnSimpan2=isset($_POST['BtnSimpan2'])?$_POST['BtnSimpan2']:NULL;
                     $_sql2         = "SELECT * FROM set_tahun";
                     $hasil2        = bukaquery($_sql2);
                     $baris2        = mysqli_fetch_row($hasil2);
                     $tahun2        = $baris2[0];
                     $bln_leng=strlen($baris2[1]);
                     $bulan2="0";
                     if ($bln_leng==1){
                        $bulan2="0".$baris2[1];
                     }else{
                        $bulan2=$baris2[1];
                     }
                if (isset($BtnSimpan2)) {
                     
                     $tanggal2       = trim($_POST['tanggal2']);
                     $tgl           =$tahun2."-".$bulan2."-".$tanggal2;
                     $ktg           = trim($_POST['ktg']);
                    
                    if ((!empty($tahun2))&&(!empty($bulan2))&&(!empty($tanggal2))) {
                        $action      =isset($_GET['action']) ? $_GET['action']:"TAMBAH" ;
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" set_hari_libur  "," '$tgl','$ktg'", " hari libur nasional " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=InputTahun&action=TAMBAH&bulan=$bulan&tahun=$tahun'>";
                                break;
                        }
                    }else if ((empty($tahun2))||(empty($bulan2))||(empty($tanggal2))){
                        echo 'Semua field harus isi..!!!';
                    }
                }

                $_sql3 = "select `tanggal`, `ktg`
                        from set_hari_libur 
                        where tanggal like '%".$tahun2."-".$bulan2."%' ORDER BY tanggal";
                $hasil3=bukaquery($_sql3);
                $jumlah3=mysqli_num_rows($hasil3);

                if(mysqli_num_rows($hasil3)!=0) {
                    echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='70px'><div align='center'>Proses</div></td>
                                <td width='100px'><div align='center'>Tgl.Libur</div></td>
                                <td width='400px'><div align='center'>Keterangan</div></td>
                            </tr>";
                            while($baris3 = mysqli_fetch_array($hasil3)) {
                                echo "<tr class='isi'>
                                    <td width='70'>
                                        <center>"; ?>
                                        <a href="?act=InputTahun&action=HAPUS2&tanggal3=<?php print $baris3[0] ?>" >hapus</a>
                                <?php
                                    echo "</center>
                                    </td>
                                    <td>$baris3[0]</td>
                                    <td>$baris3[1]</td>
                             </tr>";
                    }
                echo "</table><br>";
                $sisamasuk=$masuk-$jumlah3;
                echo "&nbsp;Total Masuk=Normal Masuk-Libur Nasional=$masuk-$jumlah3=$sisamasuk";
                }


            } else {echo "Data Setting tahun & bulan gaji masih kosong !";}
        ?>
        </div>
        </form>
        <?php
            $hapus=isset($_GET['action'])? $_GET['action']:NULL;                
            if ($hapus=="HAPUS") {
                    Hapus(" set_tahun "," tahun ='".$tahun."' and bulan ='".$bulan."' ","?act=InputTahun&action=TAMBAH&bulan=$bulan&tahun=$tahun");
            }
            if ($hapus=="HAPUS2") {
                    Hapus(" set_hari_libur "," tanggal ='".$_GET['tanggal3']."' ","?act=InputTahun&action=TAMBAH&bulan=$bulan&tahun=$tahun");
            }
        ?>
    </div>
</div>
