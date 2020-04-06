
<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun         = $baris[0];
   $bulan          = $baris[1];
?>


<div id="post">
    <div class="entry">
        <form name="frm_lokasi" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $id          =isset($_GET['id'])?$_GET['id']:NULL;
                $action      =isset($_GET['action'])?$_GET['action']:NULL; 
                $_sql = "SELECT id,bpjs,jamsostek,dansos,simwajib,angkop,angla,
                        telpri,pajak,pribadi,lain,ktg FROM potongan WHERE id='$id' AND tahun='$tahun'  and bulan='$bulan'";
                $hasil=bukaquery($_sql);
                
                $_sqlkon = "SELECT (To_days(NOW())-to_days(mulai_kontrak)) as maskon FROM pegawai WHERE id='$id'";
                $hasilkon=bukaquery($_sqlkon);
                $bariskon= mysqli_fetch_row($hasilkon);

                if(mysqli_num_rows($hasil)!=0) {
                    $action = "UBAH";
		    $baris        = mysqli_fetch_row($hasil); 
		    $bpjs         = $baris[1];
		    $jamsostek    = $baris[2];
		    $dansos	  = $baris[3];
		    $simwajib	  = $baris[4];
					
	            $_sql2	="select status,pokok,jasa,tanggal,pinjaman from peminjaman_koperasi where
					    status='Belum Lunas' and id='$id' ";
		    $hasil2	=bukaquery($_sql2);
                    $jumlah2	=mysqli_num_rows($hasil2);
		    $angkop	= '-';
		    $pokok	= '-';
		    $jasa	= '-';
		    $tanggal_pinjam='0000-00-00';
		    $pinjaman   ='0';
		    if($jumlah2!=0){
			$baris2		=mysqli_fetch_row($hasil2);
			$pokok		=$baris2[1];
			$jasa		=$baris2[2];
			$tanggal_pinjam =$baris2[3];
			$pinjaman       =$baris2[4];
                        $angkop		=$baris2[1]+$baris2[2];
                    }
                    
		    $angla			  = $baris[6];
		    $telpri			  = $baris[7];
		    $pajak			  = $baris[8];
		    $pribadi		          = $baris[9];
		    $lain			  = $baris[10];
		    $ktg		          = $baris[11];

                    if($bpjs==0){$bpjs = '-';}
                    if($jamsostek==0){$jamsostek = '-';}
		    if($dansos==0){$dansos = '-';}
		    if($simwajib==0){$simwajib = '-';}
                    if($angkop==0){$angkop = '-';}
		    if($angla==0){$angla = '-';}
		    if($telpri==0){$telpri = '-';}
		    if($pajak==0){$pajak = '-';}
		    if($pribadi==0){$pribadi = '-';}
		    if($lain==0){$lain = '-';}
		    if($ktg==""){$ktg = '-';}
                                        
                }else if(mysqli_num_rows($hasil)==0) {
                    $action = "TAMBAH";
		    $_sql3  = "SELECT koperasi.wajib,jamsostek.biaya,bpjs.biaya
			       from keanggotaan,jamsostek,koperasi,bpjs
			       where keanggotaan.koperasi=koperasi.stts
			       and keanggotaan.jamsostek=jamsostek.stts 
			       and keanggotaan.bpjs=bpjs.stts 
			       and keanggotaan.id='$id'";
		    $hasil3      = bukaquery($_sql3);
		    $baris3      = mysqli_fetch_row($hasil3);
		    $simwajib	 = $baris3[0];
		    $jamsostek   = $baris3[1];
		    $bpjs        = $baris3[2];
				
		    $_sql4       = "SELECT dana FROM dansos";
		    $hasil4      = bukaquery($_sql4);
		    $baris4      = mysqli_fetch_row($hasil4);
                    if($bariskon[0]>=1){
                          $dansos      = $baris4[0];
                    }else if($bariskon[0]<1){
                          $dansos      = 0;
                    }

                    if($bpjs==0){$bpjs = '-';}
                    if($jamsostek==0){$jamsostek = '-';}
		    if($dansos==0){$dansos	 = '-';}
                    if($simwajib==0){$simwajib = '-';}
					
		    $_sql2	="select status,pokok,jasa,tanggal,pinjaman from 
                                peminjaman_koperasi where status='Belum Lunas' and id='$id' ";
	            $hasil2	=bukaquery($_sql2);
                    $jumlah2	=mysqli_num_rows($hasil2);
		    $angkop	= '-';
		    $pokok	= '-';
		    $jasa	= '-';
		    $tanggal_pinjam='0000-00-00';
		    $pinjaman   ='0';
		    if($jumlah2!=0){
			$baris2		=mysqli_fetch_row($hasil2);
			$pokok		=$baris2[1];
			$jasa		=$baris2[2];
			$tanggal_pinjam=$baris2[3];
			$pinjaman   =$baris2[4];
                        $angkop		=$baris2[1]+$baris2[2];
                    }
					
		    $angla	 = '-';
		    $telpri	 = '-';
		    $pajak	 = '-';
		    $pribadi	 = '-';
		    $lain        = '-';
	            $ktg         = '-';
                }
                
                $_sql2 = "SELECT nik,nama,departemen FROM pegawai where id='$id'";
                $hasil2=bukaquery($_sql2);
                $baris2 = mysqli_fetch_row($hasil2);  		
				
                echo"<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";

                    $_sqlnext         	= "SELECT id FROM pegawai WHERE id>'$id' order by id asc limit 1";
                    $hasilnext        	= bukaquery($_sqlnext);
                    $barisnext        	= mysqli_fetch_row($hasilnext);
                    $next               = $barisnext[0];

                    $_sqlprev         	= "SELECT id FROM pegawai WHERE id<'$id' order by id desc limit 1";
                    $hasilprev        	= bukaquery($_sqlprev);
                    $barisprev        	= mysqli_fetch_row($hasilprev);
                    $prev               = $barisprev[0];
                    
                    if(empty($prev)){
                        $prev=$next;
                    }
                    
                    if(empty($next)){
                        $next=$prev;
                    }

                    echo "<div align='center' class='link'>
                          <a href=?act=InputPotongan&action=$action&id=$prev><<--</a>
                          <a href=?act=ListPotongan>| List Potongan |</a>
                          <a href=?act=HomeAdmin>| Menu Utama |</a>
                          <a href=?act=InputPotongan&action=$action&id=$next>-->></a>
                          </div>";
            ?>
            <div style="width: 100%; height: 75%; overflow: auto;"> 
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >NIP</td><td width="">:</td>
                    <td width="67%"><?php echo $baris2[0];?></td>
                </tr>
		<tr class="head">
                    <td width="31%">Nama</td><td width="">:</td>
                    <td width="67%"><?php echo $baris2[1];?></td>
                </tr>
		<tr class="head">
                    <td width="31%">Departemen</td><td width="">:</td>
                    <td width="67%"><?php echo $baris2[2];?></td>
                </tr>
		<tr class="head">
                    <td width="31%">Smp.Wjb Koperasi</td><td width="">:</td>
                    <td width="67%"><input name="simwajib" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $simwajib;?>" size="20" maxlength="15">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%">BPJS</td><td width="">:</td>
                    <td width="67%"><input name="bpjs" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $bpjs;?>" size="20" maxlength="15">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="31%">Jamsostek</td><td width="">:</td>
                    <td width="67%"><input name="jamsostek" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo $jamsostek;?>" size="20" maxlength="15">
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="31%">Dana Sosial</td><td width="">:</td>
                    <td width="67%"><input name="dansos" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" type=text id="TxtIsi4" class="inputbox" value="<?php echo $dansos;?>" size="20" maxlength="15">
                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="31%">Angsuran Koperasi</td><td width="">:</td>
                    <td width="67%"><input name="angkop" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" type=text id="TxtIsi5" class="inputbox" value="<?php echo $angkop;?>" size="20" maxlength="15">
                    <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="31%">Angsuran Lain</td><td width="">:</td>
                    <td width="67%"><input name="angla" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" type=text id="TxtIsi6" class="inputbox" value="<?php echo $angla;?>" size="20" maxlength="15">
                    <span id="MsgIsi6" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="31%">Telepon Pribadi</td><td width="">:</td>
                    <td width="67%"><input name="telpri" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" type=text id="TxtIsi7" class="inputbox" value="<?php echo $telpri;?>" size="20" maxlength="15">
                    <span id="MsgIsi7" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="31%">Pajak</td><td width="">:</td>
                    <td width="67%"><input name="pajak" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi8'));" type=text id="TxtIsi8" class="inputbox" value="<?php echo $pajak;?>" size="20" maxlength="15">
                    <span id="MsgIsi8" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="31%">Pribadi</td><td width="">:</td>
                    <td width="67%"><input name="pribadi" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi9'));" type=text id="TxtIsi9" class="inputbox" value="<?php echo $pribadi;?>" size="20" maxlength="15">
                    <span id="MsgIsi9" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="31%">Lain</td><td width="">:</td>
                    <td width="67%"><input name="lain" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi10'));" type=text id="TxtIsi10" class="inputbox" value="<?php echo $lain;?>" size="20" maxlength="15">
                    <span id="MsgIsi10" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
		<tr class="head">
                    <td width="31%">Keterangan</td><td width="">:</td>
                    <td width="67%"><input name="ktg" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi11'));" type=text id="TxtIsi11" class="inputbox" value="<?php echo $ktg;?>" size="50" maxlength="50">
                    <span id="MsgIsi11" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            </div>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id        = trim($_POST['id']);
                    $bpjs      = validTeks(trim($_POST['bpjs']));
                    $jamsostek = validTeks(trim($_POST['jamsostek']));
                    $dansos    = validTeks(trim($_POST['dansos']));
                    $simwajib  = validTeks(trim($_POST['simwajib']));
                    $angkop    = validTeks(trim($_POST['angkop']));
                    $angla     = validTeks(trim($_POST['angla']));
                    $telpri    = validTeks(trim($_POST['telpri']));
                    $pajak     = validTeks(trim($_POST['pajak']));
                    $pribadi   = validTeks(trim($_POST['pribadi']));
                    $lain      = validTeks(trim($_POST['lain']));
                    $ktg       = validTeks(trim($_POST['ktg']));

                    if ((!empty($id))&&(!empty($bpjs))&&(!empty($simwajib))&&(!empty($jamsostek))&&
                           (!empty($simwajib))&&(!empty($angkop))&&(!empty($angla))&&
                           (!empty($telpri))&&(!empty($pajak))&&(!empty($pribadi))&&(!empty($lain))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" potongan ","'$tahun','$bulan','$id','$bpjs','$jamsostek','$dansos','$simwajib','$angkop','$angla','$telpri','$pajak','$pribadi','$lain','$ktg' ", " Potongan " );

                                if($angkop>0){
                                    if(strlen($bulan)==1){
                                        $bulan="0".($bulan+1);
                                    }else{
                                        $bulan=$bulan+1;
                                    }

                                    InsertData(" angsuran_koperasi ","'$id','$tanggal_pinjam','$tahun-$bulan-05','".($angkop-$jasa)."','$jasa'");
                                    if($pinjaman<=getOne("select sum(pokok) from angsuran_koperasi where id='$id' and tanggal_pinjam='$tanggal_pinjam'  group by id")){
                                        EditData(" peminjaman_koperasi "," status='Lunas' WHERE id='$id' and tanggal='$tanggal_pinjam' ");
                                    }else{
                                        EditData(" peminjaman_koperasi "," status='Belum Lunas' WHERE id='$id' and tanggal='$tanggal_pinjam' ");
                                    }
                                }		
							    
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputPotongan&id=$id'></head><body></body></html>";
                                break;
                                
                            case "UBAH":
                                Ubah(" potongan "," bpjs='$bpjs',simwajib='$simwajib',jamsostek='$jamsostek',dansos='$dansos',
                                        simwajib='$simwajib',angkop='$angkop',angla='$angla',telpri='$telpri',pajak='$pajak',
                                        pribadi='$pribadi',lain='$lain',ktg='$ktg' WHERE id='$id' and tahun='$tahun' and bulan='$bulan' ", " Potongan ");

                                if($angkop>0){
                                    if(strlen($bulan)==1){
                                        $bulan="0".($bulan+1);
                                    }else{
                                        $bulan=$bulan+1;
                                    }

                                    HapusAll(" angsuran_koperasi where id ='$id' and tanggal_angsur like '%$tahun-$bulan%' ");							    
                                    InsertData(" angsuran_koperasi ","'$id','$tanggal_pinjam','$tahun-$bulan-05','".($angkop-$jasa)."','$jasa'");
                                    if($pinjaman<=getOne("select sum(pokok) from angsuran_koperasi where id='$id' and tanggal_pinjam='$tanggal_pinjam'  group by id")){
                                        EditData(" peminjaman_koperasi "," status='Lunas' WHERE id='$id' and tanggal='$tanggal_pinjam' ");
                                    }else{
                                        EditData(" peminjaman_koperasi "," status='Belum Lunas' WHERE id='$id' and tanggal='$tanggal_pinjam' ");
                                    }
                                }	
							    
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=InputPotongan&id=$id'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($id))||(empty($bpjs))||(empty($jamsostek))||
                            (empty($simwajib))||(empty($angkop))||(empty($angla))||
                                (empty($telpri))||(empty($pajak))||(empty($pribadi))||(empty($lain))){
                        echo '<b>Semua field harus isi..!!</b>';
						/*echo " simwajib='$simwajib',jamsostek='$jamsostek',dansos='$dansos',simwajib='$simwajib',
						       angkop='$angkop',angla='$angla',telpri='$telpri',pajak='$pajak',
										pribadi='$pribadi',lain='$lain',ktg='$ktg' WHERE id='$id' and tahun='$tahun' and bulan='$bulan'";*/
                    }
                }
            ?>
        </form>
    </div>
</div>
