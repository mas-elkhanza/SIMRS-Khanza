<?php
    if(!function_exists('pbrAngka')){
        function pbrAngka($sql){
            $rs = bukaquery($sql);
            if($rs && ($row = mysqli_fetch_row($rs))){
                return $row[0]===null?0:$row[0];
            }
            return 0;
        }
    }
?>
<div class="block-header">
    <h2><center>PERKIRAAN BIAYA RANAP</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="white-space:nowrap;"><center>No.Rawat</center></th>
                                <th style="white-space:nowrap;"><center>No.RM</center></th>
                                <th style="min-width:150px;"><center>Nama Pasien</center></th>
                                <th style="min-width:150px;"><center>Kamar/Bangsal</center></th>
                                <th style="min-width:120px;"><center>Perujuk</center></th>
                                <th style="white-space:nowrap;"><center>Registrasi</center></th>
                                <th style="white-space:nowrap;"><center>Tindakan</center></th>
                                <th style="white-space:nowrap;"><center>Obt+Emb+Tsl</center></th>
                                <th style="white-space:nowrap;"><center>Retur Obat</center></th>
                                <th style="white-space:nowrap;"><center>Resep Pulang</center></th>
                                <th style="white-space:nowrap;"><center>Laborat</center></th>
                                <th style="white-space:nowrap;"><center>Radiologi</center></th>
                                <th style="white-space:nowrap;"><center>Potongan</center></th>
                                <th style="white-space:nowrap;"><center>Tambahan</center></th>
                                <th style="white-space:nowrap;"><center>Kamar</center></th>
                                <th style="white-space:nowrap;"><center>Operasi</center></th>
                                <th style="white-space:nowrap;"><center>Harian</center></th>
                                <th style="white-space:nowrap;"><center>Total</center></th>
                                <th style="white-space:nowrap;"><center>Deposit</center></th>
                                <th style="white-space:nowrap;"><center>Kekurangan</center></th>
                                <th style="min-width:120px;"><center>Diagnosa Awal</center></th>
                                <th style="white-space:nowrap;"><center>ICD 10</center></th>
                                <th style="white-space:nowrap;"><center>Perkiraan Tarif</center></th>
                                <th style="white-space:nowrap;"><center>Limit</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $queryPBR = bukaquery(
                                "select kamar_inap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,bangsal.nm_bangsal,kamar.kd_kamar,".
                                "reg_periksa.biaya_reg,kamar_inap.diagnosa_awal,perkiraan_biaya_ranap.kd_penyakit,perkiraan_biaya_ranap.tarif ".
                                "from kamar_inap ".
                                "inner join reg_periksa on kamar_inap.no_rawat=reg_periksa.no_rawat ".
                                "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis ".
                                "inner join kamar on kamar_inap.kd_kamar=kamar.kd_kamar ".
                                "inner join bangsal on kamar.kd_bangsal=bangsal.kd_bangsal ".
                                "inner join perkiraan_biaya_ranap on kamar_inap.no_rawat=perkiraan_biaya_ranap.no_rawat ".
                                "where kamar_inap.stts_pulang='-' ".
                                "order by bangsal.nm_bangsal"
                            );

                            while($rsPBR = mysqli_fetch_array($queryPBR)){
                                $noRawat = $rsPBR["no_rawat"];

                                $registrasi = (float)$rsPBR["biaya_reg"];

                                $laborat = pbrAngka("select sum(periksa_lab.biaya) from periksa_lab where periksa_lab.no_rawat='$noRawat'")
                                         + pbrAngka("select sum(detail_periksa_lab.biaya_item) from detail_periksa_lab where detail_periksa_lab.no_rawat='$noRawat'");

                                $radiologi = pbrAngka("select sum(periksa_radiologi.biaya) from periksa_radiologi where periksa_radiologi.no_rawat='$noRawat'");

                                $operasi = pbrAngka(
                                    "select sum(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+operasi.biayaasisten_operator1+".
                                    "operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+operasi.biayadokter_anak+".
                                    "operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+".
                                    "operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biayaalat+operasi.biayasewaok+".
                                    "operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+".
                                    "operasi.biaya_omloop5+operasi.biayasarpras+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum) from operasi where operasi.no_rawat='$noRawat'"
                                );

                                $obat = pbrAngka("select sum(detail_pemberian_obat.total) from detail_pemberian_obat where detail_pemberian_obat.no_rawat='$noRawat'")
                                      + pbrAngka("select sum(tagihan_obat_langsung.besar_tagihan) from tagihan_obat_langsung where tagihan_obat_langsung.no_rawat='$noRawat'")
                                      + pbrAngka("select sum(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) from beri_obat_operasi where beri_obat_operasi.no_rawat='$noRawat'");

                                $tindakan = pbrAngka("select sum(rawat_inap_dr.biaya_rawat) from rawat_inap_dr where rawat_inap_dr.no_rawat='$noRawat'")
                                          + pbrAngka("select sum(rawat_inap_drpr.biaya_rawat) from rawat_inap_drpr where rawat_inap_drpr.no_rawat='$noRawat'")
                                          + pbrAngka("select sum(rawat_inap_pr.biaya_rawat) from rawat_inap_pr where rawat_inap_pr.no_rawat='$noRawat'")
                                          + pbrAngka("select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr where rawat_jl_dr.no_rawat='$noRawat'")
                                          + pbrAngka("select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr where rawat_jl_drpr.no_rawat='$noRawat'")
                                          + pbrAngka("select sum(rawat_jl_pr.biaya_rawat) from rawat_jl_pr where rawat_jl_pr.no_rawat='$noRawat'");

                                $tambahan = pbrAngka("select sum(tambahan_biaya.besar_biaya) from tambahan_biaya where tambahan_biaya.no_rawat='$noRawat'");

                                $potongan = pbrAngka("select sum(pengurangan_biaya.besar_pengurangan) from pengurangan_biaya where pengurangan_biaya.no_rawat='$noRawat'");

                                $kamar = pbrAngka("select sum(kamar_inap.ttl_biaya) from kamar_inap where kamar_inap.no_rawat='$noRawat'")
                                       + pbrAngka("select sum(biaya_sekali.besar_biaya) from biaya_sekali inner join kamar_inap on kamar_inap.kd_kamar=biaya_sekali.kd_kamar where kamar_inap.no_rawat='$noRawat'");

                                $harian = pbrAngka(
                                    "select sum(biaya_harian.jml*biaya_harian.besar_biaya*kamar_inap.lama) from kamar_inap ".
                                    "inner join biaya_harian on kamar_inap.kd_kamar=biaya_harian.kd_kamar where kamar_inap.no_rawat='$noRawat'"
                                );

                                $returObat = (-1)*pbrAngka("select sum(detreturjual.subtotal) from detreturjual where detreturjual.no_retur_jual like '%$noRawat%'");

                                $resepPulang = pbrAngka("select sum(resep_pulang.total) from resep_pulang where resep_pulang.no_rawat='$noRawat'");

                                $deposit = pbrAngka("select sum(deposit.besar_deposit) from deposit where deposit.no_rawat='$noRawat'");

                                $queryGabung = bukaquery("select ranap_gabung.no_rawat2 from ranap_gabung where ranap_gabung.no_rawat='$noRawat'");
                                if($queryGabung && ($rsGabung = mysqli_fetch_array($queryGabung))){
                                    $noRawat2 = $rsGabung["no_rawat2"];

                                    $laborat += pbrAngka("select sum(periksa_lab.biaya) from periksa_lab where periksa_lab.no_rawat='$noRawat2'")
                                              + pbrAngka("select sum(detail_periksa_lab.biaya_item) from detail_periksa_lab where detail_periksa_lab.no_rawat='$noRawat2'");

                                    $radiologi += pbrAngka("select sum(periksa_radiologi.biaya) from periksa_radiologi where periksa_radiologi.no_rawat='$noRawat2'");

                                    $operasi += pbrAngka(
                                        "select sum(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+operasi.biayaasisten_operator1+".
                                        "operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+operasi.biayadokter_anak+".
                                        "operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+".
                                        "operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biayaalat+operasi.biayasewaok+".
                                        "operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+".
                                        "operasi.biaya_omloop5+operasi.biayasarpras+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum) from operasi where operasi.no_rawat='$noRawat2'"
                                    );

                                    $obat += pbrAngka("select sum(detail_pemberian_obat.total) from detail_pemberian_obat where detail_pemberian_obat.no_rawat='$noRawat2'")
                                           + pbrAngka("select sum(tagihan_obat_langsung.besar_tagihan) from tagihan_obat_langsung where tagihan_obat_langsung.no_rawat='$noRawat2'")
                                           + pbrAngka("select sum(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) from beri_obat_operasi where beri_obat_operasi.no_rawat='$noRawat2'");

                                    $tindakan += pbrAngka("select sum(rawat_inap_dr.biaya_rawat) from rawat_inap_dr where rawat_inap_dr.no_rawat='$noRawat2'")
                                              + pbrAngka("select sum(rawat_inap_drpr.biaya_rawat) from rawat_inap_drpr where rawat_inap_drpr.no_rawat='$noRawat2'")
                                              + pbrAngka("select sum(rawat_inap_pr.biaya_rawat) from rawat_inap_pr where rawat_inap_pr.no_rawat='$noRawat2'")
                                              + pbrAngka("select sum(rawat_jl_dr.biaya_rawat) from rawat_jl_dr where rawat_jl_dr.no_rawat='$noRawat2'")
                                              + pbrAngka("select sum(rawat_jl_drpr.biaya_rawat) from rawat_jl_drpr where rawat_jl_drpr.no_rawat='$noRawat2'")
                                              + pbrAngka("select sum(rawat_jl_pr.biaya_rawat) from rawat_jl_pr where rawat_jl_pr.no_rawat='$noRawat2'");

                                    $tambahan += pbrAngka("select sum(tambahan_biaya.besar_biaya) from tambahan_biaya where tambahan_biaya.no_rawat='$noRawat2'");

                                    $potongan += pbrAngka("select sum(pengurangan_biaya.besar_pengurangan) from pengurangan_biaya where pengurangan_biaya.no_rawat='$noRawat2'");

                                    $kamar += pbrAngka("select sum(kamar_inap.ttl_biaya) from kamar_inap where kamar_inap.no_rawat='$noRawat2'")
                                            + pbrAngka("select sum(biaya_sekali.besar_biaya) from biaya_sekali inner join kamar_inap on kamar_inap.kd_kamar=biaya_sekali.kd_kamar where kamar_inap.no_rawat='$noRawat2'");

                                    $harian += pbrAngka(
                                        "select sum(biaya_harian.jml*biaya_harian.besar_biaya*kamar_inap.lama) from kamar_inap ".
                                        "inner join biaya_harian on kamar_inap.kd_kamar=biaya_harian.kd_kamar where kamar_inap.no_rawat='$noRawat2'"
                                    );

                                    $returObat += (-1)*pbrAngka("select sum(detreturjual.subtotal) from detreturjual where detreturjual.no_retur_jual like '%$noRawat2%'");

                                    $resepPulang += pbrAngka("select sum(resep_pulang.total) from resep_pulang where resep_pulang.no_rawat='$noRawat2'");
                                }

                                $jumlah = $laborat+$radiologi+$operasi+$obat+$tindakan+$tambahan+$potongan+$kamar+$registrasi+$harian+$returObat+$resepPulang;

                                $diag           = $rsPBR["kd_penyakit"];
                                $perkiraanTarif = (float)$rsPBR["tarif"];
                                $status         = ($perkiraanTarif<=$jumlah)?"Tidak Aman":"Aman";
                                $labelClass     = ($status=="Aman")?"label label-success":"label label-danger";

                                $perujuk = pbrAngka("select rujuk_masuk.perujuk from rujuk_masuk where rujuk_masuk.no_rawat='$noRawat'");
                                if($perujuk===0){ $perujuk=""; }

                                echo "<tr>
                                        <td>".$noRawat."</td>
                                        <td>".$rsPBR["no_rkm_medis"]."</td>
                                        <td align='left'>".$rsPBR["nm_pasien"]."</td>
                                        <td align='left'>".$rsPBR["kd_kamar"]." ".$rsPBR["nm_bangsal"]."</td>
                                        <td align='left'>".$perujuk."</td>
                                        <td align='right'>".number_format($registrasi,0,'.',',')."</td>
                                        <td align='right'>".number_format($tindakan,0,'.',',')."</td>
                                        <td align='right'>".number_format($obat,0,'.',',')."</td>
                                        <td align='right'>".number_format($returObat,0,'.',',')."</td>
                                        <td align='right'>".number_format($resepPulang,0,'.',',')."</td>
                                        <td align='right'>".number_format($laborat,0,'.',',')."</td>
                                        <td align='right'>".number_format($radiologi,0,'.',',')."</td>
                                        <td align='right'>".number_format($potongan,0,'.',',')."</td>
                                        <td align='right'>".number_format($tambahan,0,'.',',')."</td>
                                        <td align='right'>".number_format($kamar,0,'.',',')."</td>
                                        <td align='right'>".number_format($operasi,0,'.',',')."</td>
                                        <td align='right'>".number_format($harian,0,'.',',')."</td>
                                        <td align='right'>".number_format($jumlah,0,'.',',')."</td>
                                        <td align='right'>".number_format($deposit,0,'.',',')."</td>
                                        <td align='right'>".number_format($deposit-$jumlah,0,'.',',')."</td>
                                        <td align='left'>".$rsPBR["diagnosa_awal"]."</td>
                                        <td>".$diag."</td>
                                        <td align='right'>".number_format($perkiraanTarif,0,'.',',')."</td>
                                        <td align='center'><span class='".$labelClass."'>".$status."</span></td>
                                      </tr>";
                            }
                        ?>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="plugins/jquery/jquery.min.js" type="text/javascript"></script>