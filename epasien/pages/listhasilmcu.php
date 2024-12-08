<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $nomcuhasil = trim(isset($_GET['iyem']))?trim($_GET['iyem']):NULL;
    $nomcuhasil = json_decode(encrypt_decrypt($nomcuhasil,"d"),true); 
    if (isset($nomcuhasil["nomcuhasil"])) {
        $nomcuhasil   = cleankar($nomcuhasil["nomcuhasil"]);
        $querynorawat = bukaquery("select booking_mcu_perusahaan_berhasil_registrasi.no_rawat from booking_mcu_perusahaan_berhasil_registrasi where booking_mcu_perusahaan_berhasil_registrasi.no_mcu='$nomcuhasil'");
        if($rsquerynorawat = mysqli_fetch_array($querynorawat)) {
            echo "<div class='row clearfix'>
                    <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='header'>
                                <h2><center>HASIL PEMERIKSAAN MEDICAL CHECK UP<br/>NO.$nomcuhasil</center></h2>
                            </div>
                            <div class='body' align='justify'>";
            
            $querydatamcu = bukaquery(
                "select penilaian_mcu.tanggal,penilaian_mcu.informasi,penilaian_mcu.rps,penilaian_mcu.rpk,penilaian_mcu.rpd,penilaian_mcu.alergi,penilaian_mcu.keadaan,penilaian_mcu.kesadaran,penilaian_mcu.td,".
                "penilaian_mcu.nadi,penilaian_mcu.rr,penilaian_mcu.tb,penilaian_mcu.bb,penilaian_mcu.suhu,penilaian_mcu.submandibula,penilaian_mcu.axilla,penilaian_mcu.supraklavikula,".
                "penilaian_mcu.leher,penilaian_mcu.inguinal,penilaian_mcu.oedema,penilaian_mcu.sinus_frontalis,penilaian_mcu.sinus_maxilaris,penilaian_mcu.palpebra,penilaian_mcu.sklera,".
                "penilaian_mcu.cornea,penilaian_mcu.buta_warna,penilaian_mcu.konjungtiva,penilaian_mcu.lensa,penilaian_mcu.pupil,penilaian_mcu.lubang_telinga,penilaian_mcu.daun_telinga,".
                "penilaian_mcu.selaput_pendengaran,penilaian_mcu.proc_mastoideus,penilaian_mcu.septum_nasi,penilaian_mcu.lubang_hidung,penilaian_mcu.bibir,penilaian_mcu.caries,".
                "penilaian_mcu.lidah,penilaian_mcu.faring,penilaian_mcu.tonsil,penilaian_mcu.kelenjar_limfe,penilaian_mcu.kelenjar_gondok,penilaian_mcu.gerakan_dada,".
                "penilaian_mcu.vocal_femitus,penilaian_mcu.perkusi_dada,penilaian_mcu.bunyi_napas,penilaian_mcu.bunyi_tambahan,penilaian_mcu.ictus_cordis,penilaian_mcu.bunyi_jantung,".
                "penilaian_mcu.batas,penilaian_mcu.inspeksi,penilaian_mcu.palpasi,penilaian_mcu.hepar,penilaian_mcu.perkusi_abdomen,penilaian_mcu.auskultasi,penilaian_mcu.limpa,".
                "penilaian_mcu.costovertebral,penilaian_mcu.kondisi_kulit,penilaian_mcu.ekstrimitas_atas,penilaian_mcu.ekstrimitas_atas_ket,penilaian_mcu.ekstrimitas_bawah,".
                "penilaian_mcu.ekstrimitas_bawah_ket,penilaian_mcu.laborat,penilaian_mcu.radiologi,penilaian_mcu.ekg,penilaian_mcu.spirometri,penilaian_mcu.audiometri,".
                "penilaian_mcu.treadmill,penilaian_mcu.lainlain,penilaian_mcu.merokok,penilaian_mcu.alkohol,penilaian_mcu.kesimpulan,penilaian_mcu.anjuran,dokter.nm_dokter ".
                "from penilaian_mcu inner join dokter on penilaian_mcu.kd_dokter=dokter.kd_dokter where penilaian_mcu.no_rawat='".$rsquerynorawat["no_rawat"]."'"
            );
            if($rsquerydatamcu = mysqli_fetch_array($querydatamcu)){
                echo "          <table width='100%' class='table table-hover js-basic-example dataTable' align='right' cellpadding='3px' cellspacing='0'>
                                    <caption><b>PENILAIAN AWAL MEDICAL CHECK UP</b></caption>
                                    <tr>
                                        <td valign='top'>
                                           YANG MELAKUKAN PENGKAJIAN  
                                           <table width='100%' align='right'>
                                              <tr>
                                                  <td width='33%'>Tanggal : ".$rsquerydatamcu["tanggal"]."</td>
                                                  <td width='36%'>Dokter : ".$rsquerydatamcu["nm_dokter"]."</td>
                                                  <td width='30%'>Anamnesis : ".$rsquerydatamcu["informasi"]."</td>
                                              </tr>
                                           </table>
                                        </td>
                                     </tr>
                                     <tr>
                                        <td valign='top'>
                                           A. ANAMNESA SINGKAT  
                                           <table width='100%' align='right'>
                                              <tr>
                                                  <td width='50%'>Riwayat Penyakit Sekarang : ".$rsquerydatamcu["rps"]."</td>
                                                  <td width='50%'>Riwayat Penyakit Keluarga : ".$rsquerydatamcu["rpk"]."</td>
                                              </tr>
                                              <tr>
                                                  <td width='50%'>Riwayat Penyakit Dahulu : ".$rsquerydatamcu["rpd"]."</td>
                                                  <td width='50%'>Riwayat Alergi Makanan & Obat : ".$rsquerydatamcu["alergi"]."</td>
                                              </tr>
                                           </table>
                                        </td>
                                     </tr>
                                     <tr>
                                        <td valign='top'>
                                           B. PEMERIKSAAN FISIK 
                                           <table width='100%' align='right'>
                                              <tr>
                                                  <td width='25%'>Keadaan : ".$rsquerydatamcu["keadaan"]."</td>
                                                  <td width='25%'>Kesadaran : ".$rsquerydatamcu["kesadaran"]."</td>
                                                  <td width='25%'>TD : ".$rsquerydatamcu["td"]." mmHg</td>
                                                  <td width='25%'>Nadi : ".$rsquerydatamcu["nadi"]." x/menit</td>
                                              </tr>
                                              <tr>
                                                  <td width='25%'>RR : ".$rsquerydatamcu["rr"]." x/menit</td>
                                                  <td width='25%'>TB : ".$rsquerydatamcu["tb"]." Cm</td>
                                                  <td width='25%'>BB : ".$rsquerydatamcu["bb"]." Kg</td>
                                                  <td width='25%'>Suhu : ".$rsquerydatamcu["suhu"]." °C</td>
                                              </tr>
                                              <tr>
                                                  <td width='100%' colspan='4'>
                                                     Kelenjar Limfe :
                                                     <table width='100%' align='right'>
                                                        <tr>
                                                            <td width='33%'>Submandibula : ".$rsquerydatamcu["submandibula"]."</td>
                                                            <td width='33%'>Axilla : ".$rsquerydatamcu["axilla"]."</td>
                                                            <td width='33%'>Supraklavikula : ".$rsquerydatamcu["supraklavikula"]."</td>
                                                        </tr>
                                                        <tr>
                                                            <td width='33%'>Leher : ".$rsquerydatamcu["leher"]."</td>
                                                            <td width='33%'>Inguinal : ".$rsquerydatamcu["inguinal"]."</td>
                                                            <td width='33%'></td>
                                                        </tr>
                                                     </table>
                                                  </td>
                                              </tr>
                                              <tr>
                                                  <td width='100%' colspan='4'>
                                                     Kepala :
                                                     <table width='100%' align='right'>
                                                        <tr>
                                                            <td width='100%'>
                                                                1. Muka
                                                                <table width='100%' align='right'>
                                                                   <tr>
                                                                       <td width='24%'>Oedema : ".$rsquerydatamcu["oedema"]."</td>
                                                                       <td width='38%'>Nyeri Tekanan Sinus Frontalis : ".$rsquerydatamcu["sinus_frontalis"]."</td>
                                                                       <td width='38%'>Nyeri Tekanan Sinus Maxilaris : ".$rsquerydatamcu["sinus_maxilaris"]."</td>
                                                                   </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width='100%'>
                                                                2. Mata
                                                                <table width='100%' align='right'>
                                                                   <tr>
                                                                       <td width='25%'>Palpebra : ".$rsquerydatamcu["palpebra"]."</td>
                                                                       <td width='25%'>Sklera : ".$rsquerydatamcu["sklera"]."</td>
                                                                       <td width='25%'>Cornea : ".$rsquerydatamcu["cornea"]."</td>
                                                                       <td width='25%'>Buta Warna : ".$rsquerydatamcu["buta_warna"]."</td>
                                                                   </tr>
                                                                   <tr>
                                                                       <td width='25%'>Konjungtiva : ".$rsquerydatamcu["konjungtiva"]."</td>
                                                                       <td width='25%'>Lensa : ".$rsquerydatamcu["lensa"]."</td>
                                                                       <td width='25%'>Pupil : ".$rsquerydatamcu["pupil"]."</td>
                                                                       <td width='25%'></td>
                                                                   </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width='100%'>
                                                                3. Telinga
                                                                <table width='100%' align='right'>
                                                                   <tr>
                                                                       <td width='20%'>Lubang : ".$rsquerydatamcu["lubang_telinga"]."</td>
                                                                       <td width='20%'>Daun : ".$rsquerydatamcu["daun_telinga"]."</td>
                                                                       <td width='30%'>Proc. Mastoideus : ".$rsquerydatamcu["proc_mastoideus"]."</td>
                                                                       <td width='30%'>Selaput Pendengara : ".$rsquerydatamcu["selaput_pendengaran"]."</td>
                                                                   </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width='100%'>
                                                                4. Hidung
                                                                <table width='100%' align='right'>
                                                                   <tr>
                                                                       <td width='20%'>Septum Nasi : ".$rsquerydatamcu["septum_nasi"]."</td>
                                                                       <td width='20%'>Lubang Hidung : ".$rsquerydatamcu["lubang_hidung"]."</td>
                                                                   </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width='100%'>
                                                                5. Mulut
                                                                <table width='100%' align='right'>
                                                                   <tr>
                                                                       <td width='33%'>Bibir : ".$rsquerydatamcu["bibir"]."</td>
                                                                       <td width='33%'>Caries : ".$rsquerydatamcu["caries"]."</td>
                                                                       <td width='33%'>Lidah : ".$rsquerydatamcu["lidah"]."</td>
                                                                   </tr>
                                                                   <tr>
                                                                       <td width='33%'>Faring : ".$rsquerydatamcu["faring"]."</td>
                                                                       <td width='33%'>Tonsil : ".$rsquerydatamcu["tonsil"]."</td>
                                                                       <td width='33%'></td>
                                                                   </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width='100%'>
                                                                6. Leher
                                                                <table width='100%' align='right'>
                                                                   <tr>
                                                                       <td width='50%'>Kelenjar Limfe : ".$rsquerydatamcu["kelenjar_limfe"]."</td>
                                                                       <td width='50%'>Kelenjar Gondok : ".$rsquerydatamcu["kelenjar_gondok"]."</td>
                                                                   </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                     </table>
                                                  </td>
                                              </tr>
                                              <tr>
                                                  <td width='100%' colspan='4'>
                                                     Dada :
                                                     <table width='100%' align='right'>
                                                        <tr>
                                                            <td width='100%'>
                                                                1. Paru
                                                                <table width='100%' align='right'>
                                                                   <tr>
                                                                       <td width='33%'>Gerakan : ".$rsquerydatamcu["gerakan_dada"]."</td>
                                                                       <td width='33%'>Vocal Fremitus : ".$rsquerydatamcu["vocal_femitus"]."</td>
                                                                       <td width='33%'>Perkusi : ".$rsquerydatamcu["perkusi_dada"]."</td>
                                                                   </tr>
                                                                   <tr>
                                                                       <td width='33%'>Bunyi Napas : ".$rsquerydatamcu["bunyi_napas"]."</td>
                                                                       <td width='33%'>Bunyi : ".$rsquerydatamcu["bunyi_tambahan"]."</td>
                                                                       <td width='33%'></td>
                                                                   </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td width='100%'>
                                                                2. Jantung
                                                                <table width='100%' align='right'>
                                                                   <tr>
                                                                       <td width='33%'>Ictus Cordis : ".$rsquerydatamcu["ictus_cordis"]."</td>
                                                                       <td width='33%'>Bunyi Jantung : ".$rsquerydatamcu["bunyi_jantung"]."</td>
                                                                       <td width='33%'>Batas : ".$rsquerydatamcu["batas"]."</td>
                                                                   </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                     </table>
                                                  </td>
                                              </tr>
                                              <tr>
                                                  <td width='100%' colspan='4'>
                                                     Abdomen :
                                                     <table width='100%' align='right'>
                                                        <tr>
                                                            <td width='33%'>Inspeksi : ".$rsquerydatamcu["inspeksi"]."</td>
                                                            <td width='33%'>Palpasi : ".$rsquerydatamcu["palpasi"]."</td>
                                                            <td width='33%'>Perkusi : ".$rsquerydatamcu["perkusi_abdomen"]."</td>
                                                        </tr>
                                                        <tr>
                                                            <td width='33%'>Auskultasi : ".$rsquerydatamcu["auskultasi"]."</td>
                                                            <td width='33%'>Hepar : ".$rsquerydatamcu["hepar"]."</td>
                                                            <td width='33%'>Limpa : ".$rsquerydatamcu["limpa"]."</td>
                                                        </tr>
                                                     </table>
                                                  </td>
                                              </tr>
                                              <tr>
                                                  <td width='100%' colspan='4'>
                                                     Punggung :
                                                     <table width='100%' align='right'>
                                                        <tr>
                                                            <td width='100%'>Nyeri Ketok Costovertebral Angle/CVA : ".$rsquerydatamcu["costovertebral"]."</td>
                                                        </tr>
                                                     </table>
                                                  </td>
                                              </tr>
                                              <tr>
                                                  <td width='100%' colspan='4'>
                                                     Kulit :
                                                     <table width='100%' align='right'>
                                                        <tr>
                                                            <td width='100%'>Kondisi Kulit : ".$rsquerydatamcu["kondisi_kulit"]."</td>
                                                        </tr>
                                                     </table>
                                                  </td>
                                              </tr>
                                              <tr>
                                                  <td width='100%' colspan='4'>
                                                     Anggota Gerak :
                                                     <table width='100%' align='right'>
                                                        <tr>
                                                            <td width='50%'>Extermitas Atas : ".$rsquerydatamcu["ekstrimitas_atas"].($rsquerydatamcu["ekstrimitas_atas_ket"]==""?"":", ".$rsquerydatamcu["ekstrimitas_atas_ket"])."</td>
                                                            <td width='50%'>Extermitas Bawah : ".$rsquerydatamcu["ekstrimitas_bawah"].($rsquerydatamcu["ekstrimitas_bawah_ket"]==""?"":", ".$rsquerydatamcu["ekstrimitas_bawah_ket"])."</td>
                                                        </tr>
                                                     </table>
                                                  </td>
                                              </tr>
                                           </table>
                                        </td>
                                     </tr>
                                     <tr>
                                        <td valign='top'>
                                           C. PEMERIKSAAN LABORATORIUM
                                           <table width='100%' align='right'>
                                              <tr>
                                                  <td width='100%' align='justify'>".$rsquerydatamcu["laborat"]."</td>
                                              </tr>
                                           </table>
                                        </td>
                                     </tr>
                                     <tr>
                                        <td valign='top'>
                                           D. RONGSEN THORAX
                                           <table width='100%' align='right'>
                                              <tr>
                                                  <td width='100%' align='justify'>".$rsquerydatamcu["radiologi"]."</td>
                                              </tr>
                                           </table>
                                        </td>
                                     </tr>
                                     <tr>
                                        <td valign='top'>
                                           E. EKG
                                           <table width='100%' align='right'>
                                              <tr>
                                                  <td width='100%' align='justify'>".$rsquerydatamcu["ekg"]."</td>
                                              </tr>
                                           </table>
                                        </td>
                                     </tr>
                                     <tr>
                                        <td valign='top'>
                                           F. SPIROMETRI
                                           <table width='100%' align='right'>
                                              <tr>
                                                  <td width='100%' align='justify'>".$rsquerydatamcu["spirometri"]."</td>
                                              </tr>
                                           </table>
                                        </td>
                                     </tr>
                                     <tr>
                                        <td valign='top'>
                                           G. AUDIOMETRI
                                           <table width='100%' align='right'>
                                              <tr>
                                                  <td width='100%' align='justify'>".$rsquerydatamcu["audiometri"]."</td>
                                              </tr>
                                           </table>
                                        </td>
                                     </tr>
                                     <tr>
                                        <td valign='top'>
                                           H. TREADMILL
                                           <table width='100%' align='right'>
                                              <tr>
                                                  <td width='100%' align='justify'>".$rsquerydatamcu["treadmill"]."</td>
                                              </tr>
                                           </table>
                                        </td>
                                     </tr>
                                     <tr>
                                        <td valign='top'>
                                           I. LAIN-LAIN
                                           <table width='100%' align='right'>
                                              <tr>
                                                  <td width='100%' align='justify'>".$rsquerydatamcu["lainlain"]."</td>
                                              </tr>
                                           </table>
                                        </td>
                                     </tr>
                                     <tr>
                                        <td valign='top'>
                                           J. RIWAYAT MEROKOK DAN KONSUMSI ALKOHOL
                                           <table width='100%' align='right'>
                                              <tr>
                                                  <td width='100%' align='justify'>Merokok : ".$rsquerydatamcu["merokok"]."</td>
                                              </tr>
                                              <tr>
                                                  <td width='100%' align='justify'>Alkohol :".$rsquerydatamcu["alkohol"]."</td>
                                              </tr>
                                           </table>
                                        </td>
                                     </tr>
                                     <tr>
                                        <td valign='top'>
                                           K. KESIMPULAN
                                           <table width='100%' align='right'>
                                              <tr>
                                                  <td width='100%' align='justify'>".$rsquerydatamcu["kesimpulan"]."</td>
                                              </tr>
                                           </table>
                                        </td>
                                     </tr>
                                     <tr>
                                        <td valign='top'>
                                           L. ANJURAN
                                           <table width='100%' align='right'>
                                              <tr>
                                                  <td width='100%' align='justify'>".$rsquerydatamcu["anjuran"]."</td>
                                              </tr>
                                           </table>
                                        </td>
                                     </tr>
                                </table>";
            }
            
            $querysoap = bukaquery(
                "select pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi,pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,".
                "pemeriksaan_ralan.tinggi,pemeriksaan_ralan.berat,pemeriksaan_ralan.gcs,pemeriksaan_ralan.spo2,pemeriksaan_ralan.kesadaran,pemeriksaan_ralan.keluhan, ".
                "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.lingkar_perut,pemeriksaan_ralan.rtl,pemeriksaan_ralan.penilaian,".
                "pemeriksaan_ralan.instruksi,pemeriksaan_ralan.evaluasi,pegawai.nama,pegawai.jbtn from pemeriksaan_ralan inner join pegawai on pemeriksaan_ralan.nip=pegawai.nik where ".
                "pemeriksaan_ralan.no_rawat='".$rsquerynorawat["no_rawat"]."' order by pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat" 
            );
            if(mysqli_num_rows($querysoap)!=0) {
                echo "          <table width='100%' align='center' class='table table-bordered table-hover js-basic-example dataTable'>
                                    <caption><b>PEMERIKSAAN ULANG/SOAP</b></caption>
                                    <tr align='center'>
                                        <td valign='top' width='4%' bgcolor='#FCFCFC'>No.</td>
                                        <td valign='top' width='15%' bgcolor='#FCFCFC'>Tanggal</td>
                                        <td valign='top' width='54%' bgcolor='#FCFCFC' colspan='7'>Dokter/Paramedis</td>
                                        <td valign='top' width='27%' bgcolor='#FCFCFC' colspan='3'>Profesi/Jabatan/Departemen</td>
                                    </tr>";
                $w=1;
                while($rsquerysoap = mysqli_fetch_array($querysoap)){
                    echo "          <tr>
                                        <td valign='top' align='center'>".$w."</td>
                                        <td valign='top'>".$rsquerysoap["tgl_perawatan"]." ".$rsquerysoap["jam_rawat"]."</td>
                                        <td valign='top' colspan='7'>".$rsquerysoap["nama"]."</td>
                                        <td valign='top' colspan='3'>".$rsquerysoap["jbtn"]."</td>
                                    </tr>";
                    if($rsquerysoap["keluhan"]!=""){
                        echo "      <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' colspan='2'>Subjek</td>
                                        <td valign='top' colspan='8'> : ".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerysoap["keluhan"])))."</td>
                                    </tr>";
                    }
                    
                    if($rsquerysoap["pemeriksaan"]!=""){
                        echo "      <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' colspan='2'>Objek</td>
                                        <td valign='top' colspan='8'> : ".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerysoap["pemeriksaan"])))."</td>
                                    </tr>";
                    }
                    
                    echo "          <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' width='8%' bgcolor='#FCFCFC' align='center'>Suhu(C)</td>
                                        <td valign='top' width='8%' bgcolor='#FCFCFC' align='center'>Tensi</td>
                                        <td valign='top' width='8%' bgcolor='#FCFCFC' align='center'>Nadi(/menit)</td>
                                        <td valign='top' width='8%' bgcolor='#FCFCFC' align='center'>Respirasi(/menit)</td>
                                        <td valign='top' width='8%' bgcolor='#FCFCFC' align='center'>Tinggi(Cm)</td>
                                        <td valign='top' width='8%' bgcolor='#FCFCFC' align='center'>Berat(Kg)</td>
                                        <td valign='top' width='8%' bgcolor='#FCFCFC' align='center'>SpO2(%)</td>
                                        <td valign='top' width='8%' bgcolor='#FCFCFC' align='center'>GCS(E,V,M)</td>
                                        <td valign='top' width='8%' bgcolor='#FCFCFC' align='center'>Kesadaran</td>
                                        <td valign='top' width='8%' bgcolor='#FCFCFC' align='center'>L.P.(Cm)</td>
                                    </tr>
                                    <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top'></td>
                                        <td valign='top' align='center'>".$rsquerysoap["suhu_tubuh"]."</td>
                                        <td valign='top' align='center'>".$rsquerysoap["tensi"]."</td>
                                        <td valign='top' align='center'>".$rsquerysoap["nadi"]."</td>
                                        <td valign='top' align='center'>".$rsquerysoap["respirasi"]."</td>
                                        <td valign='top' align='center'>".$rsquerysoap["tinggi"]."</td>
                                        <td valign='top' align='center'>".$rsquerysoap["berat"]."</td>
                                        <td valign='top' align='center'>".$rsquerysoap["spo2"]."</td>
                                        <td valign='top' align='center'>".$rsquerysoap["gcs"]."</td>
                                        <td valign='top' align='center'>".$rsquerysoap["kesadaran"]."</td>
                                        <td valign='top' align='center'>".$rsquerysoap["lingkar_perut"]."</td>
                                    </tr>";
                    
                    if($rsquerysoap["alergi"]!=""){
                        echo "      <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' colspan='2'>Alergi</td>
                                        <td valign='top' colspan='8'> : ".$rsquerysoap["alergi"]."</td>
                                    </tr>";
                    }
                    
                    if($rsquerysoap["penilaian"]!=""){
                        echo "      <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' colspan='2'>Asesmen</td>
                                        <td valign='top' colspan='8'> : ".$rsquerysoap["penilaian"]."</td>
                                    </tr>";
                    }
                    
                    if($rsquerysoap["rtl"]!=""){
                        echo "      <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' colspan='2'>Plan</td>
                                        <td valign='top' colspan='8'> : ".$rsquerysoap["rtl"]."</td>
                                    </tr>";
                    }
                    
                    if($rsquerysoap["instruksi"]!=""){
                        echo "      <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' colspan='2'>Instruksi</td>
                                        <td valign='top' colspan='8'> : ".$rsquerysoap["instruksi"]."</td>
                                    </tr>";
                    }
                    
                    if($rsquerysoap["evaluasi"]!=""){
                        echo "      <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' align='center'></td>
                                        <td valign='top' colspan='2'>Evaluasi</td>
                                        <td valign='top' colspan='8'> : ".$rsquerysoap["evaluasi"]."</td>
                                    </tr>";
                    }
                }
                echo "          </table>";
            }
            
            $querytanggalperiksalab = bukaquery(
                "select periksa_lab.tgl_periksa,periksa_lab.jam from periksa_lab where periksa_lab.kategori<>'PA' and periksa_lab.no_rawat='".$rsquerynorawat["no_rawat"]."' ".
                "group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam) order by periksa_lab.tgl_periksa,periksa_lab.jam" 
            );
            if(mysqli_num_rows($querytanggalperiksalab)!=0) {
                echo "          <table width='100%' align='center' class='table table-bordered table-hover js-basic-example dataTable'>
                                    <caption><b>HASIL PEMERIKSAAN LABORATORIUM PK & MB</b></caption>
                                    <tr align='center'>
                                      <td valign='top' width='4%' bgcolor='#FCFCFC'>No.</td>
                                      <td valign='top' width='15%' bgcolor='#FCFCFC'>Tanggal</td>
                                      <td valign='top' width='36%' bgcolor='#FCFCFC'>Nama Pemeriksaan</td>
                                      <td valign='top' width='23%' bgcolor='#FCFCFC'>Dokter PJ</td>
                                      <td valign='top' width='22%' bgcolor='#FCFCFC'>Petugas</td>
                                    </tr>";
                $w=1;
                while($rsquerytanggalperiksalab= mysqli_fetch_array($querytanggalperiksalab)){
                    $queryperiksalab = bukaquery(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,petugas.nama,periksa_lab.dokter_perujuk,dokter.nm_dokter ".
                        "from periksa_lab inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw ".
                        "inner join petugas on periksa_lab.nip=petugas.nip inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter ".
                        "where periksa_lab.kategori<>'PA' and periksa_lab.no_rawat='".$rsquerynorawat["no_rawat"]."' ".
                        "and periksa_lab.tgl_periksa='".$rsquerytanggalperiksalab["tgl_periksa"]."' and periksa_lab.jam='".$rsquerytanggalperiksalab["jam"]."'"
                    );
                    $s=1;
                    while($rsqueryperiksalab = mysqli_fetch_array($queryperiksalab)){
                        if($s==1){
                            echo"   <tr>
                                        <td valign='top' align='center'>".$w."</td>
                                        <td valign='top'>".$rsquerytanggalperiksalab["tgl_periksa"]." ".$rsquerytanggalperiksalab["jam"]."</td>
                                        <td valign='top'>".$rsqueryperiksalab["nm_perawatan"]."</td>
                                        <td valign='top'>".$rsqueryperiksalab["nm_dokter"]."</td>
                                        <td valign='top'>".$rsqueryperiksalab["nama"]."</td>
                                    </tr>"; 
                        }else{
                            echo"   <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top'></td>
                                        <td valign='top'>".$rsqueryperiksalab["nm_perawatan"]."</td>
                                        <td valign='top'>".$rsqueryperiksalab["nm_dokter"]."</td>
                                        <td valign='top'>".$rsqueryperiksalab["nama"]."</td>
                                    </tr>"; 
                        }
                        
                        $querydetailperiksalab = bukaquery(
                            "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,".
                            "detail_periksa_lab.keterangan from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template ".
                            "where detail_periksa_lab.no_rawat='".$rsquerynorawat["no_rawat"]."' and detail_periksa_lab.kd_jenis_prw='".$rsqueryperiksalab["kd_jenis_prw"]."' and ".
                            "detail_periksa_lab.tgl_periksa='".$rsquerytanggalperiksalab["tgl_periksa"]."' and detail_periksa_lab.jam='".$rsquerytanggalperiksalab["jam"]."' ".
                            "order by detail_periksa_lab.kd_jenis_prw,template_laboratorium.urut"
                        );
                        if(mysqli_num_rows($querydetailperiksalab)!=0) {
                            echo "  <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top'></td>
                                        <td valign='top' align='center' bgcolor='#FCFCFC'>Detail Pemeriksaan</td>
                                        <td valign='top' align='center' bgcolor='#FCFCFC'>Hasil</td>
                                        <td valign='top' align='center' bgcolor='#FCFCFC'>Nilai Rujukan</td>
                                    </tr>";
                            while($rsquerydetailperiksalab = mysqli_fetch_array($querydetailperiksalab)){
                                switch (strtolower($rsquerydetailperiksalab["keterangan"])) {
                                    case "l":
                                        echo "<tr>
                                                <td valign='top' align='center'></td>
                                                <td valign='top'></td>
                                                <td valign='top'>".$rsquerydetailperiksalab["Pemeriksaan"]."</td>
                                                <td valign='top' style='color:#0000FF'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai"])))." ".$rsquerydetailperiksalab["satuan"]."</td>
                                                <td valign='top'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai_rujukan"])))."</td>
                                              </tr>"; 
                                        break;
                                    case "h":
                                        echo "<tr>
                                                <td valign='top' align='center'></td>
                                                <td valign='top'></td>
                                                <td valign='top'>".$rsquerydetailperiksalab["Pemeriksaan"]."</td>
                                                <td valign='top' style='color:#FF0000'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai"])))." ".$rsquerydetailperiksalab["satuan"]."</td>
                                                <td valign='top'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai_rujukan"])))."</td>
                                              </tr>"; 
                                        break;
                                    case "t":
                                        echo "<tr>
                                                <td valign='top' align='center'></td>
                                                <td valign='top'></td>
                                                <td valign='top'>".$rsquerydetailperiksalab["Pemeriksaan"]."</td>
                                                <td valign='top'><b>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai"])))." ".$rsquerydetailperiksalab["satuan"]."</b></td>
                                                <td valign='top'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai_rujukan"])))."</td>
                                              </tr>"; 
                                        break;
                                    default:
                                        echo "<tr>
                                                <td valign='top' align='center'></td>
                                                <td valign='top'></td>
                                                <td valign='top'>".$rsquerydetailperiksalab["Pemeriksaan"]."</td>
                                                <td valign='top'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai"])))." ".$rsquerydetailperiksalab["satuan"]."</td>
                                                <td valign='top'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsquerydetailperiksalab["nilai_rujukan"])))."</td>
                                              </tr>"; 
                                }
                            }
                        }
                        
                        $s++;
                    }
                    
                    $querysarankesanperiksalab = bukaquery(
                        "select saran_kesan_lab.saran,saran_kesan_lab.kesan from saran_kesan_lab where saran_kesan_lab.no_rawat='".$rsquerynorawat["no_rawat"]."' and saran_kesan_lab.tgl_periksa='".$rsquerytanggalperiksalab["tgl_periksa"]."' and saran_kesan_lab.jam='".$rsquerytanggalperiksalab["jam"]."'"
                    );
                    if($querysarankesanperiksalab = mysqli_fetch_array($querysarankesanperiksalab)){
                        echo        "<tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top'>Kesan</td>
                                        <td valign='top' colspan='3'>: ".$querysarankesanperiksalab["kesan"]."</td>
                                    </tr>
                                    <tr>
                                        <td valign='top' align='center'></td>
                                        <td valign='top'>Saran</td>
                                        <td valign='top' colspan='3'>: ".$querysarankesanperiksalab["saran"]."</td>
                                    </tr>";
                    }
                    
                    $w++;
                }
                
                echo "          </table>";
            }
            
            $queryperiksaradiologi = bukaquery(
                "select periksa_radiologi.tgl_periksa,periksa_radiologi.jam, ".
                 "jns_perawatan_radiologi.nm_perawatan,petugas.nama,periksa_radiologi.biaya,periksa_radiologi.dokter_perujuk,".
                 "dokter.nm_dokter,concat(".
                 "if(periksa_radiologi.proyeksi<>'',concat('Proyeksi : ',periksa_radiologi.proyeksi,', '),''),".
                 "if(periksa_radiologi.kV<>'',concat('kV : ',periksa_radiologi.kV,', '),''),".
                 "if(periksa_radiologi.mAS<>'',concat('mAS : ',periksa_radiologi.mAS,', '),''),".
                 "if(periksa_radiologi.FFD<>'',concat('FFD : ',periksa_radiologi.FFD,', '),''),".
                 "if(periksa_radiologi.BSF<>'',concat('BSF : ',periksa_radiologi.BSF,', '),''),".
                 "if(periksa_radiologi.inak<>'',concat('Inak : ',periksa_radiologi.inak,', '),''),".
                 "if(periksa_radiologi.jml_penyinaran<>'',concat('Jml Penyinaran : ',periksa_radiologi.jml_penyinaran,', '),''),".
                 "if(periksa_radiologi.dosis<>'',concat('Dosis Radiasi : ',periksa_radiologi.dosis),'')) as proyeksi ".
                 "from periksa_radiologi inner join jns_perawatan_radiologi on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw ".
                 "inner join petugas on periksa_radiologi.nip=petugas.nip inner join dokter on periksa_radiologi.kd_dokter=dokter.kd_dokter ".
                 "where periksa_radiologi.no_rawat='".$rsquerynorawat["no_rawat"]."' order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam"
            );
            if(mysqli_num_rows($queryperiksaradiologi)!=0) {
                echo "          <table width='100%' align='center' class='table table-bordered table-hover js-basic-example dataTable'>
                                    <caption><b>HASIL PEMERIKSAAN RADIOLOGI</b></caption>
                                    <tr align='center'>
                                      <td valign='top' width='4%' bgcolor='#FCFCFC'>No.</td>
                                      <td valign='top' width='15%' bgcolor='#FCFCFC'>Tanggal</td>
                                      <td valign='top' width='36%' bgcolor='#FCFCFC'>Nama Pemeriksaan</td>
                                      <td valign='top' width='23%' bgcolor='#FCFCFC'>Dokter PJ</td>
                                      <td valign='top' width='22%' bgcolor='#FCFCFC'>Petugas</td>
                                    </tr>";
                $w=1;
                while($rsqueryperiksaradiologi= mysqli_fetch_array($queryperiksaradiologi)){
                    echo "          <tr>
                                        <td valign='top' align='center'>".$w."</td>
                                        <td valign='top'>".$rsqueryperiksaradiologi["tgl_periksa"]." ".$rsqueryperiksaradiologi["jam"]."</td>
                                        <td valign='top'>".$rsqueryperiksaradiologi["nm_perawatan"]."<br/>".$rsqueryperiksaradiologi["proyeksi"]."</td>
                                        <td valign='top'>".$rsqueryperiksaradiologi["nm_dokter"]."</td>
                                        <td valign='top'>".$rsqueryperiksaradiologi["nama"]."</td>
                                    </tr>";
                    $w++;
                }
                echo "          </table>";
            }
            
            $queryhasilradiologi = bukaquery(
                "select hasil_radiologi.tgl_periksa,hasil_radiologi.jam,hasil_radiologi.hasil from hasil_radiologi where hasil_radiologi.no_rawat='".$rsquerynorawat["no_rawat"]."' order by hasil_radiologi.tgl_periksa,hasil_radiologi.jam"
            );
            if(mysqli_num_rows($queryhasilradiologi)!=0) {
                echo "          <table width='100%' align='center' class='table table-bordered table-hover js-basic-example dataTable'>
                                    <tr align='center'>
                                        <td valign='top' width='4%' bgcolor='#FCFCFC'>No.</td>
                                        <td valign='top' width='15%' bgcolor='#FCFCFC'>Tanggal</td>
                                        <td valign='top' width='81%' bgcolor='#FCFCFC'>Hasil Pemeriksaan</td>
                                    </tr>";
                $w=1;
                while($rsqueryhasilradiologi= mysqli_fetch_array($queryhasilradiologi)){
                    echo "          <tr>
                                        <td valign='top' align='center'>".$w."</td>
                                        <td valign='top'>".$rsqueryhasilradiologi["tgl_periksa"]." ".$rsqueryhasilradiologi["jam"]."</td>
                                        <td valign='top'>".str_replace(array("\r\n","\r","\n","\\r","\\n","\\r\\n"),"<br/>",str_replace(">","&gt;",str_replace("<","&lt;",$rsqueryhasilradiologi["hasil"])))."</td>
                                    </tr>";
                    $w++;
                }
                echo "          </table>";
            }
            
            $querygambarradiologi = bukaquery(
                "select gambar_radiologi.tgl_periksa,gambar_radiologi.jam,gambar_radiologi.lokasi_gambar from gambar_radiologi where gambar_radiologi.no_rawat='".$rsquerynorawat["no_rawat"]."' order by gambar_radiologi.tgl_periksa,gambar_radiologi.jam"
            );
            if(mysqli_num_rows($querygambarradiologi)!=0) {
                echo "          <table width='100%' align='center' class='table table-bordered table-hover js-basic-example dataTable'>
                                    <tr align='center'>
                                        <td valign='top' width='4%' bgcolor='#FCFCFC'>No.</td>
                                        <td valign='top' width='15%' bgcolor='#FCFCFC'>Tanggal</td>
                                        <td valign='top' width='81%' bgcolor='#FCFCFC'>Gambar Radiologi</td>
                                    </tr>";
                $w=1;
                while($rsquerygambarradiologi= mysqli_fetch_array($querygambarradiologi)){
                    $src = 'data: '.mime_content_type("http://".host()."/webapps/radiologi/".$rsquerygambarradiologi["lokasi_gambar"]).';base64,'.base64_encode(file_get_contents("http://".host()."/webapps/radiologi/".$rsquerygambarradiologi["lokasi_gambar"]));
                    echo "          <tr>
                                        <td valign='top' align='center'>".$w."</td>
                                        <td valign='top'>".$rsquerygambarradiologi["tgl_periksa"]." ".$rsquerygambarradiologi["jam"]."</td>
                                        <td valign='top' align='center'><img alt='Gambar Radiologi' src='$src' width='500px' height='500px'/></td>
                                    </tr>";
                    $w++;
                }
                echo "          </table>";
            }
                                
            echo "              <center><a href='index.php?act=RiwayatMCU' class='btn btn-danger waves-effect'>Kembali</a></center>
                            </div>
                        </div>
                    </div>
                  </div>";
        }else{
            echo "<div class='block-header'>
                        <h2><center>HASIL PEMERIKSAAN MCU</center></h2>
                  </div>
                  <div class='row clearfix'>
                     <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                        <div class='card'>
                            <div class='body'>
                                <center>Maaf masih menunggu hasil pemeriksaan MACU</center>
                            </div>
                        </div>
                     </div>
                  </div>";
            JSRedirect2("index.php?act=RiwayatMCU",4);
        }
    }else{
        echo "<div class='block-header'>
                    <h2><center>HASIL PEMERIKSAAN MCU</center></h2>
              </div>
              <div class='row clearfix'>
                 <div class='col-lg-12 col-md-12 col-sm-12 col-xs-12'>
                    <div class='card'>
                        <div class='body'>
                            <center>Maaf nomor pemeriksaan MCU tidak ditemukan</center>
                        </div>
                    </div>
                 </div>
              </div>";
        JSRedirect2("index.php?act=RiwayatMCU",4);
    }
?>
