
        <div id="post">
            <h1 class="title">::[ Input Presensi Pegawai (foto diambil saat memilih shift) ]::</h1>
            <div class="entry">
            <form name="frmPresensi" id="frmPresensi" method="post" onsubmit="return validasiIsi();">
            <table  width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
              <tr class='head'>
                <td width=50% align="center" style=" background: #FFFFFF ;">
                    <div id="my_camera"></div>
                    <input type="hidden" name="image" class="image-tag" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">   
               </td>
                <td width=50%>
                      <table width="100%" align="center">
                          <tr class="head4">
                                <td width="31%" >Jam Masuk Departemen</td><td width="">:</td>
                                <td width="67%">
                                    <select name="jam_masuk" class="text7" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                                        <option id='TxtIsi2' value=''>&nbsp;</option>
                                        <?php
                                            $hasil=bukaquery("select jam_masuk from jam_jaga group by jam_masuk");
                                            while($baris = mysqli_fetch_array($hasil)) {   
                                                echo "<option id='TxtIsi2' value='$baris[0]'>$baris[0]</option>";
                                            }
                                        ?>
                                    </select>
                                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                                </td>
                          </tr>
                          <tr class="head4">
                              <td width="31%" >Nmr.Kartu</td><td width="">:</td>
                              <td width="67%"><input name="barcode" class="text7" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=password id="TxtIsi3" class="inputbox" value="" size="20" maxlength="70"/>
                              <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                              </td>
                          </tr>
                      </table>
                      <div align="center"><input name=BtnSimpan type=submit class="button" value="Simpan" onClick="take_snapshot()"/>&nbsp;<input name=BtnKosong type=reset class="button" value="Kosong"/></div><br/>
               </td>
              </tr>
            </table> 
            <script language="JavaScript">
                Webcam.set({
                    width: 370,
                    height: 300,
                    image_format: 'jpeg',
                    jpeg_quality: 90
                });

                Webcam.attach( '#my_camera' );

                function take_snapshot() {
                    Webcam.snap( function(data_uri) {
                        $(".image-tag").val(data_uri);
                        document.getElementById('results').innerHTML = '<img src="'+data_uri+'"/>';
                    } );
                }
            </script>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $jam_masuk      = validTeks3(trim($_POST['jam_masuk']));  
                    $barcode        = validTeks(trim($_POST['barcode']));
                    
                    $_sqlbar        = "select id from barcode where barcode='$barcode'";
                    $hasilbar       = bukaquery($_sqlbar);
                    @$barisbar      = mysqli_fetch_array($hasilbar);  
                    @$idpeg         = $barisbar["id"];
                    
                    $_sqljamdatang  = "select jam_jaga.shift,CURRENT_DATE() as hariini,pegawai.departemen from jam_jaga inner join pegawai on pegawai.departemen=jam_jaga.dep_id 
                                       where jam_jaga.jam_masuk='$jam_masuk' and pegawai.id='$idpeg'";
                    $hasiljamdatang = bukaquery($_sqljamdatang);
                    @$barisjamdatang = mysqli_fetch_array($hasiljamdatang);  
                    @$shift          = $barisjamdatang["shift"];
                    @$hariini        = $barisjamdatang["hariini"];
                    @$departemen     = $barisjamdatang["departemen"];
                    
                    $_sqlketerlambatan = "select * from set_keterlambatan";
                    $hasilketerlmabatan=  bukaquery($_sqlketerlambatan);
                    @$barisketerlambatan=  mysqli_fetch_array($hasilketerlmabatan);
                    @$toleransi      = $barisketerlambatan[0];
                    @$terlambat1     = $barisketerlambatan[1];
                    @$terlambat2     = $barisketerlambatan[2];
                    
                    if(file_exists(host()."webapps/presensi/".$hariini.$shift.$idpeg.".jpeg")){
                        @unlink(host()."webapps/presensi/".$hariini.$shift.$idpeg.".jpeg");
                    }
                    
                    @$img            = $_POST["image"];
                    @$image_parts    = explode(";base64,", $img);
                    @$image_type_aux = explode("image/", $image_parts[0]);
                    @$image_type     = $image_type_aux[1];
                    @$image_base64   = base64_decode($image_parts[1]);
                    @$file           = $hariini.$shift.$idpeg.".jpeg";
                    @file_put_contents($file, $image_base64);
                    
                    //echo "Jam Masuk : ".$jam_masuk." ID : ".$idpeg."departemen : $departemen  Shift : $shift";
                    
                    $jam="now()";
                    if(!empty($jam_masuk)){
                        $jam="CONCAT(CURRENT_DATE(),' $jam_masuk')";
                    }
                    
                    $_sqlvalid        = "select id from rekap_presensi where id='$idpeg' and shift='$shift' and jam_datang like '%$hariini%'";
                    $hasilvalid       = bukaquery($_sqlvalid);
                    @$barisvalid      = mysqli_fetch_array($hasilvalid);  
                    @$idvalid         = $barisvalid["id"];  
                    
                    if(!empty($idvalid)){
                        echo"<font size='9'>Anda sudah presensi untuk tanggal ".date('Y-m-d')."</font> <html><head><title></title><meta http-equiv='refresh' content='5;URL=?page=Input'></head><body></body></html>";
                    }elseif((!empty($idpeg))&&(!empty($shift))&&(empty($idvalid))) {
                        $_sqlcek        = "select id, shift, jam_datang, jam_pulang, status, keterlambatan, durasi, photo from temporary_presensi where id='$idpeg'";
                        $hasilcek       = bukaquery($_sqlcek);
                        @$bariscek       = mysqli_fetch_array($hasilcek);  
                        @$idcek          = $bariscek["id"];         
                        
                        
                        if(empty($idcek)){
                            if(empty($img)){
                                echo "<font size='9'>Pilih shift dulu !!!!!!!</font>";
                            }else{
                                Tambah2("temporary_presensi","'$idpeg','$shift',NOW(),NULL,
                                if(TIME_TO_SEC(now())-TIME_TO_SEC($jam)>($toleransi*60),if(TIME_TO_SEC(now())-TIME_TO_SEC($jam)>($terlambat1*60),if(TIME_TO_SEC(now())-TIME_TO_SEC($jam)>($terlambat2*60),'Terlambat II','Terlambat I'),'Terlambat Toleransi'),'Tepat Waktu'),
                                if(TIME_TO_SEC(now())-TIME_TO_SEC($jam)>($toleransi*60),SEC_TO_TIME(TIME_TO_SEC(now())-TIME_TO_SEC($jam)),''),'','$file'", 
                                " Presensi Masuk jam $jam_masuk ".getOne("select if(TIME_TO_SEC(now())-TIME_TO_SEC($jam)>($toleransi*60),concat('Keterlambatan ',SEC_TO_TIME(TIME_TO_SEC(now())-TIME_TO_SEC($jam))),'')"));
                                echo"<html><head><title></title><meta http-equiv='refresh' content='3;URL=?page=Input'></head><body></body></html>";
                            }                            
                        }elseif(!empty($idcek)){  
                            $jamdatang=getOne("select jam_jaga.jam_masuk from jam_jaga inner join pegawai on pegawai.departemen=jam_jaga.dep_id where jam_jaga.shift='$shift' and pegawai.id='$idcek'");
                            $jampulang=getOne("select jam_jaga.jam_pulang from jam_jaga inner join pegawai on pegawai.departemen=jam_jaga.dep_id where jam_jaga.shift='$shift' and pegawai.id='$idcek'");

                            $jam="now()";
                            if(!empty($jamdatang)){
                                $jam="CONCAT(CURRENT_DATE(),' $jamdatang')";
                            }
                            $jam2="now()";
                            if(!empty($jampulang)){
                                 $jam2="CONCAT(CURRENT_DATE(),' $jampulang')";
                            }
                            $masuk=getOne("select jam_datang from temporary_presensi where id='$idcek'");
                            $pulang="now()";

                            Ubah2(" temporary_presensi "," jam_pulang=NOW(),status=if(TIME_TO_SEC('$masuk')-TIME_TO_SEC($jam)>($toleransi*60),if(TIME_TO_SEC('$masuk')-TIME_TO_SEC($jam)>($terlambat1*60),if(TIME_TO_SEC('$masuk')-TIME_TO_SEC($jam)>($terlambat2*60),
                                   concat('Terlambat II',if(TIME_TO_SEC($pulang)-TIME_TO_SEC($jam2)<0,' & PSW',' ')),concat('Terlambat I',if(TIME_TO_SEC($pulang)-TIME_TO_SEC($jam2)<0,' & PSW',' '))),
                                   concat('Terlambat Toleransi',if(TIME_TO_SEC($pulang)-TIME_TO_SEC($jam2)<0,' & PSW',' '))),concat('Tepat Waktu',if(TIME_TO_SEC($pulang)-TIME_TO_SEC($jam2)<0,' & PSW',' '))),
                                   durasi=(SEC_TO_TIME(unix_timestamp(now()) - unix_timestamp(jam_datang))) where id='$idpeg'  ");                            
                            $_sqlcek        = "select id, shift, jam_datang, jam_pulang, status, keterlambatan, durasi, photo from temporary_presensi where id='$idpeg'";
                            $hasilcek       = bukaquery($_sqlcek);
                            $bariscek       = mysqli_fetch_array($hasilcek);  
                            $idcek          = $bariscek["id"];                                                      
                            $shift          = $bariscek["shift"];
                            $jam_datang     = $bariscek["jam_datang"];
                            $jam_pulang     = $bariscek["jam_pulang"];
                            $status         = $bariscek["status"];
                            $keterlambatan  = $bariscek["keterlambatan"];
                            $durasi         = $bariscek["durasi"];
                            Tambah2("rekap_presensi","'$idcek','$shift','$jam_datang','$jam_pulang','$status','$keterlambatan','$durasi','','$file'", " Presensi Pulang jam $jam_pulang" );
                            hapusinput(" delete from temporary_presensi where id ='$idcek' ");
                            echo"<html><head><title></title><meta http-equiv='refresh' content='3;URL=?page=Input'></head><body></body></html>";
                        } 
                    }elseif (empty($idpeg)||empty($shift)){
                        echo "<b>ID Pegawai atau Jam Masuk ada yang salah, Silahkan pilih berdasarkan shift departemen anda</b>";
                    }
                }
            ?>
            </form>
           </div>
        </div>
