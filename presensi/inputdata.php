
        <div id="post">
            <h1 class="title">::[ Input Presensi Pegawai (foto diambil saat memilih shift) ]::</h1>
            <div class="entry">
            <form name="frmPresensi" id="frmPresensi" method="post" onsubmit="gambar()">
            <table  width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                
              <tr class='head'>
                <td width=50% align="center" style=" background: #FFFFFF ;">
                    <!-- First, include the JPEGCam JavaScript Library -->
                    <script type="text/javascript" src="webcam.js"></script>

                    <!-- Configure a few settings -->
                    <script language="JavaScript">
                            webcam.set_api_url( 'test.php' );
                            webcam.set_quality( 90 ); // JPEG quality (1 - 100)
                            webcam.set_shutter_sound( true ); // play shutter click sound
                    </script>

                    <!-- Next, write the movie to the page at 320x240 -->
                    
                    <script language="JavaScript">
                            document.write( webcam.get_html(370, 300) );
                    </script>

                    <!-- Code to handle the server response (see test.php) -->
                    <script language="JavaScript">
                            var gambar="gambar";
                            webcam.set_hook( 'onComplete', 'my_completion_handler' );

                            function take_snapshot() {
                                    // take snapshot and upload to server
                                    //document.getElementById('upload_results').innerHTML = '<h1>Uploading...</h1>';
                                    webcam.snap();
                            }

                            function my_completion_handler(msg) {
                                    // extract URL out of PHP output
                                    if (msg.match(/(http\:\/\/\S+)/)) {
                                            var image_url = RegExp.$1;
                                            // show JPEG image in page
                                            gambar=image_url;
                                            document.frmPresensi.urlnya.value = gambar;
                                            webcam.reset();
                                    }
                                    //else alert("PHP Error: " + msg);
                            }
                            function gambar(){
                                document.frmPresensi.urlnya.value = gambar;
                            }                            
                    </script>
                     
                    <input type="hidden" id="urlnya" name="urlnya" value=""/>
                            <!--<input type=button value="Configure..." onClick="webcam.configure()"/>-->
                    <!--<input type="button" class="button" value="&nbsp;&nbsp;&nbsp;Ambil Photo&nbsp;&nbsp;&nbsp;" onClick="take_snapshot();"/>-->                   
               </td>
                <td width=50%>
                        <!--<div id="upload_results" style="background-color:#eee;"></div>-->
                     <table width="100%" align="center">
                          <tr class="head4">
                                <td width="31%" >Jam Masuk Departemen</td><td width="">:</td>
                                <td width="67%">
                                    <select name="jam_masuk" class="text7" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" onClick="take_snapshot();" id="TxtIsi1">
                                        <!-- <option id='TxtIsi2' value=' '>- Jenis Kelamin -</option> --> 
                                        <option id='TxtIsi1' value=''>&nbsp;</option>
                                        <?php
                                            $hasil=bukaquery("select jam_masuk from jam_jaga group by jam_masuk");
                                            while($baris = mysqli_fetch_array($hasil)) {   
                                                echo "<option id='TxtIsi1' value='$baris[0]'>$baris[0]</option>";
                                            }
                                        ?>
                                    </select>
                                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                                </td>
                          </tr>
                          <tr class="head4">
                              <td width="31%" >Nmr.Kartu</td><td width="">:</td>
                              <td width="67%"><input name="barcode" class="text7" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=password id="TxtIsi2" class="inputbox" value="" size="20" maxlength="70"/>
                              <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                              </td>
                          </tr>
                      </table>
                      <div align="center"><input name=BtnSimpan type=submit class="button" value="Simpan"/>&nbsp;<input name=BtnKosong type=reset class="button" value="Kosong"/></div><br/>
               </td>
               <td>
					<script src="http://www.clocklink.com/embed.js"></script> <script type="text/javascript" language="JavaScript">obj=new Object;obj.clockfile="5031-blue.swf";obj.TimeZone="GMT0700"; obj.width=200;obj.height=80;obj.wmode="transparent";showClock(obj);</script>
                    <!-My calendar widget - HTML code - mycalendar.org --><div align="center" style="margin:15px 0px 0px 0px"><noscript><div align="center" style="width:140px;border:1px solid #ccc;background:#fff ;color: #fff ;font-weight:bold;"><a style="font-size:12px;text-decoration:none;color:#000 ;" href="http://mycalendar.org/Holiday/Indonesia/">Indonesia Calendar</a></div></noscript><script type="text/javascript" src="http://mycalendar.org/calendar.php?group=Holiday&calendar=Indonesia&widget_number=3&cp3_Hex=FFB200&cp2_Hex=040244&cp1_Hex=F9F9FF&fwdt=140&lab=1"></script></div><!-end of code-->
               </td>
              </tr>
            </table> 
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $urlnya         = trim($_POST['urlnya']);
                    $jam_masuk      = trim($_POST['jam_masuk']);  
                    $barcode        = trim($_POST['barcode']);
                    
                    $_sqlbar        = "select id from barcode where barcode='$barcode'";
                    $hasilbar       = bukaquery($_sqlbar);
                    $barisbar       = mysqli_fetch_array($hasilbar);  
                    $idpeg          = $barisbar["id"];
                    
                    $_sqljamdatang  = "select jam_jaga.shift,CURRENT_DATE() as hariini,pegawai.departemen from jam_jaga inner join pegawai on pegawai.departemen=jam_jaga.dep_id 
                                       where jam_jaga.jam_masuk='$jam_masuk' and pegawai.id='$idpeg'";
                    $hasiljamdatang = bukaquery($_sqljamdatang);
                    $barisjamdatang = mysqli_fetch_array($hasiljamdatang);  
                    $shift          = $barisjamdatang["shift"];
                    $hariini        = $barisjamdatang["hariini"];
                    $departemen     = $barisjamdatang["departemen"];
                    
                    $_sqlketerlambatan = "select * from set_keterlambatan";
                    $hasilketerlmabatan=  bukaquery($_sqlketerlambatan);
                    $barisketerlambatan=  mysqli_fetch_array($hasilketerlmabatan);
                    $toleransi      = $barisketerlambatan[0];
                    $terlambat1     = $barisketerlambatan[1];
                    $terlambat2     = $barisketerlambatan[2];
                    
                    //echo "Jam Masuk : ".$jam_masuk." ID : ".$idpeg."departemen : $departemen  Shift : $shift";
                    
                    $jam="now()";
                    if(!empty($jam_masuk)){
                        $jam="CONCAT(CURRENT_DATE(),' $jam_masuk')";
                    }
                    
                    $_sqlvalid        = "select id from rekap_presensi where id='$idpeg' and shift='$shift' and jam_datang like '%$hariini%'";
                    $hasilvalid       = bukaquery($_sqlvalid);
                    $barisvalid       = mysqli_fetch_array($hasilvalid);  
                    $idvalid          = $barisvalid["id"];  
                    
                    if(!empty($idvalid)){
                        echo"<font size='9'>Anda sudah presensi untuk tanggal ".date('Y-m-d')."</font> <html><head><title></title><meta http-equiv='refresh' content='5;URL=?page=Input'></head><body></body></html>";
                    }elseif((!empty($idpeg))&&(!empty($shift))&&(empty($idvalid))) {
                        $_sqlcek        = "select id, shift, jam_datang, jam_pulang, status, keterlambatan, durasi, photo from temporary_presensi where id='$idpeg'";
                        $hasilcek       = bukaquery($_sqlcek);
                        $bariscek       = mysqli_fetch_array($hasilcek);  
                        $idcek          = $bariscek["id"];         
                        
                        
                        if(empty($idcek)){
                            if(empty($urlnya)){
                                echo "<font size='9'>Pilih shift dulu !!!!!!!</font>";
                            }else{
                                Tambah2("temporary_presensi","'$idpeg','$shift',NOW(),NULL,
                                if(TIME_TO_SEC(now())-TIME_TO_SEC($jam)>($toleransi*60),if(TIME_TO_SEC(now())-TIME_TO_SEC($jam)>($terlambat1*60),if(TIME_TO_SEC(now())-TIME_TO_SEC($jam)>($terlambat2*60),'Terlambat II','Terlambat I'),'Terlambat Toleransi'),'Tepat Waktu'),
                                if(TIME_TO_SEC(now())-TIME_TO_SEC($jam)>($toleransi*60),SEC_TO_TIME(TIME_TO_SEC(now())-TIME_TO_SEC($jam)),''),'','$urlnya'", 
                                " Presensi Masuk jam $jam_masuk ".getOne("select if(TIME_TO_SEC(now())-TIME_TO_SEC($jam)>($toleransi*60),concat('Keterlambatan ',SEC_TO_TIME(TIME_TO_SEC(now())-TIME_TO_SEC($jam))),'')"));
                                echo"<html><head><title></title><meta http-equiv='refresh' content='3;URL=?page=Input'></head><body></body></html>";
                            }                            
                        }elseif(!empty($idcek)){  
                            //if(empty($urlnya)){
                            //    echo "<font size='9'>Pilih shift dulu !!!!!!!</font>";
                            //}else{
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
                                Tambah2("rekap_presensi","'$idcek','$shift','$jam_datang','$jam_pulang','$status','$keterlambatan','$durasi','','$urlnya'", " Presensi Pulang jam $jam_pulang" );
                                hapusinput(" delete from temporary_presensi where id ='$idcek' ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='3;URL=?page=Input'></head><body></body></html>";
                           // } 
                            
                        } 
                    }elseif (empty($idpeg)||empty($shift)){
                        echo "<b>ID Pegawai atau Jam Masuk ada yang salah, Silahkan pilih berdasarkan shift departemen anda</b>";
                    }
                }
            ?>
            </form>
           </div>
        </div>
