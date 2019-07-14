<div id="post">
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
    <div class="entry">        
        <?php
            echo "";
            $codernik       = isset($_GET['codernik'])?$_GET['codernik']:NULL;
            $norawat     = isset($_GET['norawat'])?$_GET['norawat']:NULL;
            $tahunawal      =isset($_GET['tahunawal'])?$_GET['tahunawal']:NULL;
            $bulanawal      =isset($_GET['bulanawal'])?$_GET['bulanawal']:NULL;
            $tanggalawal    =isset($_GET['tanggalawal'])?$_GET['tanggalawal']:NULL;
            $tahunakhir     =isset($_GET['tahunakhir'])?$_GET['tahunakhir']:NULL;
            $bulanakhir     =isset($_GET['bulanakhir'])?$_GET['bulanakhir']:NULL;
            $tanggalakhir   =isset($_GET['tanggalakhir'])?$_GET['tanggalakhir']:NULL;  
            $action         =isset($_GET['action'])?$_GET['action']:NULL;
            $codernik       =isset($_GET['codernik'])?$_GET['codernik']:NULL;
            $carabayar      =str_replace("_"," ",isset($_GET['carabayar']))?str_replace("_"," ",$_GET['carabayar']):NULL;
            $_sql         = "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,
                            reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,
                            pasien.umur,pasien.tgl_lahir,poliklinik.nm_poli,reg_periksa.status_lanjut,reg_periksa.umurdaftar,reg_periksa.sttsumur,
                            reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab 
                            from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab 
                            on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis 
                            and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='$norawat' ";
            $hasil        = bukaquery($_sql);
            $baris        = mysqli_fetch_array($hasil);
            $no_rkm_medis = $baris["no_rkm_medis"];
            $nm_pasien    = $baris["nm_pasien"];
            $umurdaftar   = $baris["umurdaftar"];
            $sttsumur     = $baris["sttsumur"];
            $tgl_lahir    = $baris["tgl_lahir"];
            $jk           = $baris["jk"];
            $almt_pj      = $baris["almt_pj"];
            $tgl_registrasi = $baris["tgl_registrasi"];
            $jam_reg      = $baris["jam_reg"];
            $nm_poli      = $baris["nm_poli"];
            $nm_dokter    = $baris["nm_dokter"];
            $nm_dokter2   = getOne("select dokter.nm_dokter from dpjp_ranap inner join dokter on dpjp_ranap.kd_dokter=dokter.kd_dokter where dpjp_ranap.no_rawat='$norawat'");
            if(!empty($nm_dokter2)){
                $nm_dokter=$nm_dokter2;
            }
            $status_lanjut  = $baris["status_lanjut"];
            $png_jawab    = $baris["png_jawab"];
            $jnsrawat="1";
            if($status_lanjut=="Ranap"){
                $jnsrawat="1";
            }else{
                $jnsrawat="2";
            }

            echo "<input type=hidden name=no_rawat value='$norawat'>
                  <input type=hidden name=tgl_registrasi value='$tgl_registrasi'>
                  <input type=hidden name=tgl_lahir value='$tgl_lahir'>
                  <input type=hidden name=nm_pasien value='$nm_pasien'>
                  <input type=hidden name=jnsrawat value='$jnsrawat'>
                  <input type=hidden name=jk value='$jk'>
                  <input type=hidden name=codernik value='$codernik'>";
            echo "<div align='center' class='link'>
                      <a href='?act=KlaimBaruManual2&codernik=$codernik&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&carabayar=$carabayar'>| List Data |</a>
                  </div>";
        ?>
        <div style="width: 100%; height: 87%; overflow: auto;">
        <table width="100%" align="center">
            <tr class="head">
                <td width="25%" >No.Rawat</td><td width="">:</td>
                <td width="75%"><?php echo $norawat;?></td>
            </tr>
            <tr class="head">
                <td width="25%" >No.RM</td><td width="">:</td>
                <td width="75%"><?php echo $no_rkm_medis;?></td>
            </tr>
            <tr class="head">
                <td width="25%">Nama Pasien</td><td width="">:</td>
                <td width="75%"><?php echo $nm_pasien.", ".$umurdaftar." ".$sttsumur;?></td>
            </tr>
            <tr class="head">
                <td width="25%">Jenis Kelamin</td><td width="">:</td>
                <td width="75%"><?php echo $jk;?></td>
            </tr>
            <tr class="head">
                <td width="25%">Alamat</td><td width="">:</td>
                <td width="75%"><?php echo $almt_pj;?></td>
            </tr>
            <tr class="head">
                <td width="25%">Tgl.Registrasi</td><td width="">:</td>
                <td width="75%"><?php echo $tgl_registrasi." ".$jam_reg;?></td>
            </tr>   
            <tr class="head">
                <td width="25%">Poliklinik</td><td width="">:</td>
                <td width="75%"><?php echo $nm_poli;?></td>
            </tr> 
            <tr class="head">
                <td width="25%">Dokter</td><td width="">:</td>
                <td width="75%"><?php echo $nm_dokter;?></td>
            </tr> 
            <tr class="head">
                <td width="25%">Status</td><td width="">:</td>
                <td width="75%"><?php echo $status_lanjut." (".$png_jawab.")";?></td>
            </tr>   
            <tr class="head">
                <td width="31%" >No.SEP</td><td width="">:</td>
                <td width="67%">
                    <input name="nosep" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="" size="40" maxlength="40">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >No.Kartu</td><td width="">:</td>
                <td width="67%">
                    <input name="nokartu" class="text" type=text class="inputbox" value="<?php echo getOne("select no_peserta from pasien where no_rkm_medis='$no_rkm_medis'")?>" size="40" maxlength="40">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Tgl.Keluar</td><td width="">:</td>
                <td width="67%">
                    <input name="keluar" class="text" type=text class="inputbox" 
                        value="<?php if($status_lanjut=="Ralan"){
                                         echo $tgl_registrasi;
                                     }else{
                                         echo getOne("select tgl_keluar from kamar_inap where no_rawat='".$norawat."'order by tgl_keluar desc limit 1");
                                     }
                                ?>" size="15" maxlength="10">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Kelas Rawat</td><td width="">:</td>
                <td width="67%">
                    <select name="kelas_rawat" class="text3">
                        <?php if($status_lanjut=="Ralan"){
                                  echo "<option value='3'>Kelas Reguler</option>
                                        <option value='1'>Kelas Eksekutif</option>";                            
                              }else{
                                  echo "<option value='1'>Kelas 1</option>
                                        <option value='2'>Kelas 2</option>
                                        <option value='3'>Kelas 3</option>";
                              }
                        ?>
                    </select> 
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >ADL Sub Acute</td><td width="">:</td>
                <td width="67%">
                    <select name="adl_sub_acute" class="text3">                            
                        <option value=""></option>
                        <?php 
                            for($i=12; $i<=60; $i++){                        
                               echo "<option value=$i>$i</option>";
                            }
                        ?>
                    </select> 
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >ADL Chronic</td><td width="">:</td>
                <td width="67%">
                    <select name="adl_chronic" class="text3">                            
                        <option value=""></option>
                        <?php 
                            for($i=12; $i<=60; $i++){                        
                               echo "<option value=$i>$i</option>";
                            }
                        ?>
                    </select> 
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >ICU Indikator</td><td width="">:</td>
                <td width="67%">
                    <select name="icu_indikator" class="text3">
                       <option value="0">0</option>
                       <option value="1">1</option>
                    </select> 
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >ICU Los</td><td width="">:</td>
                <td width="67%">
                    <input name="icu_los" class="text" type="text" class="inputbox" value="0" size="5" maxlength="5">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Jumlah Jam Penggunaan Ventilator di ICU</td><td width="">:</td>
                <td width="67%">
                    <input name="ventilator_hour" class="text" type="text" class="inputbox" value="0" size="5" maxlength="5">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Indikator Upgrade Kelas</td><td width="">:</td>
                <td width="67%">
                    <select name="upgrade_class_ind" class="text3">
                       <option value="0">0</option>
                       <option value="1">1</option>
                    </select> 
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Naik ke Kelas</td><td width="">:</td>
                <td width="67%">
                    <select name="upgrade_class_class" class="text2">
                       <option value=""></option>
                       <option value="kelas_1">Kelas 1</option>
                       <option value="kelas_2">Kelas 2</option>
                       <option value="vip">Kelas VIP</option>
                       <option value="vvip">Kelas VVIP</option>
                    </select> 
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Lama Hari Naik Kelas</td><td width="">:</td>
                <td width="67%">
                    <input name="upgrade_class_los" class="text" type="text" class="inputbox" value="0" size="5" maxlength="5">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Tambahan</td><td width="">:</td>
                <td width="67%">
                    <input name="add_payment_pct" class="text" type="text" class="inputbox" value="0" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Berat Saat Lahir</td><td width="">:</td>
                <td width="67%">
                    <input name="birth_weight" class="text" type="text" class="inputbox" value="" size="5" maxlength="5">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Status Pulang</td><td width="">:</td>
                <td width="67%">
                    <select name="discharge_status" class="text2">
                        <?php 
                            if(getOne("select count(no_rawat) from kamar_inap where stts_pulang='Sembuh' and no_rawat='".$norawat."'")>0){
                                echo "<option value='1'>Atas persetujuan dokter</option>";
                            }else if(getOne("select count(no_rawat) from kamar_inap where stts_pulang='Sehat' and no_rawat='".$norawat."'")>0){
                                echo "<option value='1'>Atas persetujuan dokter</option>";
                            }else if(getOne("select count(no_rawat) from kamar_inap where stts_pulang='Rujuk' and no_rawat='".$norawat."'")>0){
                                echo "<option value='2'>Dirujuk</option>";
                            }else if(getOne("select count(no_rawat) from kamar_inap where stts_pulang='APS' and no_rawat='".$norawat."'")>0){
                                echo "<option value='3'>Atas permintaan sendiri</option>";
                            }else if(getOne("select count(no_rawat) from kamar_inap where stts_pulang='Pulang Paksa' and no_rawat='".$norawat."'")>0){
                                echo "<option value='3'>Atas permintaan sendiri</option>";
                            }else if(getOne("select count(no_rawat) from kamar_inap where stts_pulang='Meninggal' and no_rawat='".$norawat."'")>0){
                                echo "<option value='4'>Meninggal</option>";
                            }else if(getOne("select count(no_rawat) from kamar_inap where stts_pulang='+' and no_rawat='".$norawat."'")>0){
                                echo "<option value='4'>Meninggal</option>";
                            }else if(getOne("select count(no_rawat) from kamar_inap where stts_pulang='Atas Persetujuan Dokter' and no_rawat='".$norawat."'")>0){
                                echo "<option value='1'>Atas persetujuan dokter</option>";
                            }else if(getOne("select count(no_rawat) from kamar_inap where stts_pulang='Atas Permintaan Sendiri' and no_rawat='".$norawat."'")>0){
                                echo "<option value='3'>Atas permintaan sendiri</option>";
                            }else if(getOne("select count(no_rawat) from kamar_inap where stts_pulang='Lain-lain' and no_rawat='".$norawat."'")>0){
                                echo "<option value='5'>Lain-lain</option>";
                            }else{
                                echo "<option value='1'>Atas persetujuan dokter</option>";
                            }
                        ?>
                        <option value="1">Atas persetujuan dokter</option>
                        <option value="2">Dirujuk</option>
                        <option value="3">Atas permintaan sendiri</option>
                        <option value="4">Meninggal</option>
                        <option value="5">Lain-lain</option>
                    </select> 
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Diagnosa</td><td width="">:</td>
                <td width="67%">
                    <input name="diagnosa" class="text" type=text class="inputbox" 
                           value="<?php  
                                        $penyakit="";
                                        $a=1;
                                        $hasilpenyakit=bukaquery("select kd_penyakit from diagnosa_pasien where no_rawat='".$norawat."' order by prioritas asc");
                                        while($barispenyakit = mysqli_fetch_array($hasilpenyakit)) {
                                            if($a==1){
                                                $penyakit=$barispenyakit["kd_penyakit"];
                                            }else{
                                                $penyakit=$penyakit."#".$barispenyakit["kd_penyakit"];
                                            }                
                                            $a++;
                                        }
                                        echo $penyakit;
                                  ?>" size="60" maxlength="100">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Prosedur</td><td width="">:</td>
                <td width="67%">
                    <input name="procedure" class="text" type=text class="inputbox" 
                        value="<?php  
                                    $prosedur="";
                                    $a=1;
                                    $hasilprosedur=bukaquery("select kode from prosedur_pasien where no_rawat='".$norawat."' order by prioritas asc");
                                    while($barisprosedur = mysqli_fetch_array($hasilprosedur)) {
                                        if($a==1){
                                            $prosedur=$barisprosedur["kode"];
                                        }else{
                                            $prosedur=$prosedur."#".$barisprosedur["kode"];
                                        }                
                                        $a++;
                                    } 
                                    echo $prosedur;
                              ?>" size="60" maxlength="100">
                </td>
            </tr>
            <?php 
                $prosedur_non_bedah=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Ralan Dokter Paramedis'");
                if($prosedur_non_bedah==""){
                    $prosedur_non_bedah="0";
                }
                $prosedur_bedah=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Operasi'");
                if($prosedur_bedah==""){
                    $prosedur_bedah="0";
                }
                $konsultasi=(getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Ranap Dokter'")+
                             getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Ralan Dokter'"));
                if($konsultasi==""){
                    $konsultasi="0";
                }
                $tenaga_ahli=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Ranap Dokter Paramedis'");
                if($tenaga_ahli==""){
                    $tenaga_ahli="0";
                }
                $keperawatan=(getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Ranap Paramedis'")+
                              getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Ralan Paramedis'"));
                if($keperawatan==""){
                    $keperawatan="0";
                }
                $radiologi=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Radiologi'");
                if($radiologi==""){
                    $radiologi="0";
                }
                $laboratorium=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Laborat'");
                if($laboratorium==""){
                    $laboratorium="0";
                }
                $kamar=(getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Kamar'")+getOne("select biaya_reg from reg_periksa where no_rawat='".$norawat."'"));
                if($kamar==""){
                    $kamar="0";
                }
                $obat_kronis=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where nm_perawatan like '%kronis%' and no_rawat='".$norawat."' and status='Obat'");
                $obat_kemoterapi=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where nm_perawatan like '%kemo%' and no_rawat='".$norawat."' and status='Obat'");
                $obat=(getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Obat'")+
                       getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Retur Obat'")+
                       getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Resep Pulang'")-$obat_kronis-$obat_kemoterapi);
                if($obat==""){
                    $obat="0";
                }
                if($obat_kemoterapi==""){
                    $obat_kemoterapi="0";
                }        
                if($obat_kronis==""){
                    $obat_kronis="0";
                }
                $bmhp=getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Tambahan'");
                if($bmhp==""){
                    $bmhp="0";
                }
                $sewa_alat=(getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Harian'")+
                            getOne("select if(sum(totalbiaya)='','0',sum(totalbiaya)) from billing where no_rawat='".$norawat."' and status='Service'"));
                if($sewa_alat==""){
                    $sewa_alat="0";
                }
            ?>
            <tr class="head">
                <td width="31%" >Biaya Prosedur Non Bedah</td><td width="">:</td>
                <td width="67%">
                    <input name="prosedur_non_bedah" class="text" type="text" class="inputbox" value="<?php echo $prosedur_non_bedah; ?>" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Prosedur Bedah</td><td width="">:</td>
                <td width="67%">
                    <input name="prosedur_bedah" class="text" type="text" class="inputbox" value="<?php echo $prosedur_bedah; ?>" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Konsultasi</td><td width="">:</td>
                <td width="67%">
                    <input name="konsultasi" class="text" type="text" class="inputbox" value="<?php echo $konsultasi; ?>" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Tenaga Ahli</td><td width="">:</td>
                <td width="67%">
                    <input name="tenaga_ahli" class="text" type="text" class="inputbox" value="<?php echo $tenaga_ahli; ?>" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Keperawatan</td><td width="">:</td>
                <td width="67%">
                    <input name="keperawatan" class="text" type="text" class="inputbox" value="<?php echo $keperawatan; ?>" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Penunjang</td><td width="">:</td>
                <td width="67%">
                    <input name="penunjang" class="text" type="text" class="inputbox" value="0" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Radiologi</td><td width="">:</td>
                <td width="67%">
                    <input name="radiologi" class="text" type="text" class="inputbox" value="<?php echo $radiologi;?>" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Laboratorium</td><td width="">:</td>
                <td width="67%">
                    <input name="laboratorium" class="text" type="text" class="inputbox" value="<?php echo $laboratorium;?>" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Pelayanan Darah</td><td width="">:</td>
                <td width="67%">
                    <input name="pelayanan_darah" class="text" type="text" class="inputbox" value="0" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Rehabilitasi</td><td width="">:</td>
                <td width="67%">
                    <input name="rehabilitasi" class="text" type="text" class="inputbox" value="0" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Kamar</td><td width="">:</td>
                <td width="67%">
                    <input name="kamar" class="text" type="text" class="inputbox" value="<?php echo $kamar;?>" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Rawat Intensif</td><td width="">:</td>
                <td width="67%">
                    <input name="rawat_intensif" class="text" type="text" class="inputbox" value="0" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Obat</td><td width="">:</td>
                <td width="67%">
                    <input name="obat" class="text" type="text" class="inputbox" value="<?php echo $obat;?>" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Obat Kronis</td><td width="">:</td>
                <td width="67%">
                    <input name="obat_kronis" class="text" type="text" class="inputbox" value="<?php echo $obat_kronis;?>" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Obat Kemoterapi</td><td width="">:</td>
                <td width="67%">
                    <input name="obat_kemoterapi" class="text" type="text" class="inputbox" value="<?php echo $obat_kemoterapi;?>" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Alkes</td><td width="">:</td>
                <td width="67%">
                    <input name="alkes" class="text" type="text" class="inputbox" value="0" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya BMHP</td><td width="">:</td>
                <td width="67%">
                    <input name="bmhp" class="text" type="text" class="inputbox" value="<?php echo $bmhp;?>" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Biaya Sewa Alat</td><td width="">:</td>
                <td width="67%">
                    <input name="sewa_alat" class="text" type="text" class="inputbox" value="<?php echo $sewa_alat;?>" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Tarif Poli Eksekutif</td><td width="">:</td>
                <td width="67%">
                    <input name="tarif_poli_eks" class="text" type="text" class="inputbox" value="0" size="20" maxlength="15">
                </td>
            </tr>
            <tr class="head">
                <td width="31%" >Nama Dokter</td><td width="">:</td>
                <td width="67%">
                    <input name="nama_dokter" class="text" type="text" class="inputbox" value="<?php echo $nm_dokter;?>" size="60" maxlength="100">
                </td>
            </tr>
        </table>                
        </div>
        <?php
            $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
            if (isset($BtnSimpan)) {
                $norawat           = trim($_POST['no_rawat']);
                $tgl_registrasi    = trim($_POST['tgl_registrasi']);
                $codernik          = trim($_POST['codernik']);
                $nosep             = trim($_POST['nosep']);
                $nokartu           = trim($_POST['nokartu']);
                $nm_pasien         = trim($_POST['nm_pasien']);
                $keluar            = trim($_POST['keluar']);
                $kelas_rawat       = trim($_POST['kelas_rawat']);
                $adl_sub_acute     = trim($_POST['adl_sub_acute']);
                $adl_chronic       = trim($_POST['adl_chronic']);
                $icu_indikator     = trim($_POST['icu_indikator']);
                $icu_los           = trim($_POST['icu_los']);
                $ventilator_hour   = trim($_POST['ventilator_hour']);
                $upgrade_class_ind = trim($_POST['upgrade_class_ind']);
                $upgrade_class_class = trim($_POST['upgrade_class_class']);
                $upgrade_class_los = trim($_POST['upgrade_class_los']);
                $add_payment_pct   = trim($_POST['add_payment_pct']);
                $birth_weight      = trim($_POST['birth_weight']);
                $discharge_status  = trim($_POST['discharge_status']);
                $diagnosa          = trim($_POST['diagnosa']);
                $procedure         = trim($_POST['procedure']);
                $prosedur_non_bedah = trim($_POST['prosedur_non_bedah']);
                $prosedur_bedah    = trim($_POST['prosedur_bedah']);
                $konsultasi        = trim($_POST['konsultasi']);
                $tenaga_ahli       = trim($_POST['tenaga_ahli']);
                $keperawatan       = trim($_POST['keperawatan']);
                $penunjang         = trim($_POST['penunjang']);
                $radiologi         = trim($_POST['radiologi']);
                $laboratorium      = trim($_POST['laboratorium']);
                $pelayanan_darah   = trim($_POST['pelayanan_darah']);
                $rehabilitasi      = trim($_POST['rehabilitasi']);
                $kamar             = trim($_POST['kamar']);
                $rawat_intensif    = trim($_POST['rawat_intensif']);
                $obat              = trim($_POST['obat']);
                $obat_kronis       = trim($_POST['obat_kronis']);
                $obat_kemoterapi   = trim($_POST['obat_kemoterapi']);
                $alkes             = trim($_POST['alkes']);
                $bmhp              = trim($_POST['bmhp']);
                $sewa_alat         = trim($_POST['sewa_alat']);
                $tarif_poli_eks    = trim($_POST['tarif_poli_eks']);
                $nama_dokter       = trim($_POST['nama_dokter']);
                $jk                = trim($_POST['jk']);
                $tgl_lahir         = trim($_POST['tgl_lahir']);
                $jnsrawat          = trim($_POST['jnsrawat']);
                $gender            = "";
                if($jk=="L"){
                    $gender="1";
                }else{
                    $gender="2";
                }
                
                if ((!empty($norawat))&&(!empty($nosep))&&(!empty($nokartu))) {                        
                    BuatKlaimBaru2($nokartu,$nosep,$no_rkm_medis,$nm_pasien,$tgl_lahir." 00:00:00", $gender,$norawat);
                    EditUlangKlaim($nosep);
                    UpdateDataKlaim2($nosep,$nokartu,$tgl_registrasi,$keluar,$jnsrawat,$kelas_rawat,$adl_sub_acute,
                        $adl_chronic,$icu_indikator,$icu_los,$ventilator_hour,$upgrade_class_ind,$upgrade_class_class,
                        $upgrade_class_los,$add_payment_pct,$birth_weight,$discharge_status,$diagnosa,$procedure, 
                        $tarif_poli_eks,$nama_dokter,getKelasRS(),"","","#",$codernik,
                        $prosedur_non_bedah,$prosedur_bedah,$konsultasi,$tenaga_ahli,$keperawatan,$penunjang,
                        $radiologi,$laboratorium,$pelayanan_darah,$rehabilitasi,$kamar,$rawat_intensif,$obat,
                        $obat_kronis,$obat_kemoterapi,$alkes,$bmhp,$sewa_alat);
                    echo "<meta http-equiv='refresh' content='1;URL=?act=KlaimBaruManual2&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir&codernik=$codernik'>";
                }else if ((empty($norawat))||(empty($nosep))||(empty($nokartu))){
                    echo 'Semua field harus isi..!!!';
                }
            }
        ?>         
    </div>
    <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;Simpan&nbsp;&nbsp;"></div>
    </form>
</div>

