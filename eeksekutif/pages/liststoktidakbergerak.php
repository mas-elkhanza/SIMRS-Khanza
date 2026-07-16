<div class="block-header">
    <h2><center>DEAD STOK/STOK MATI/BARANG TIDAK BERGERAK</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <ul class="nav nav-tabs tab-nav-right" role="tablist">
                <?php
                    $periodeList = [1,3,6,9,12];
                    $no          = 0;
                    foreach($periodeList as $periode) {
                        $no++;
                        $activeTab = ($no==1) ? " class='active'" : "";
                        echo "<li role='presentation'".$activeTab."><a href='#periode".$periode."' data-toggle='tab'>".$periode." Bulan</a></li>";
                    }
                ?>
                </ul>
                <div class="tab-content">
                <?php
                    $no = 0;
                    foreach($periodeList as $periode) {
                        $no++;
                        $activeClass = ($no==1) ? " in active" : "";
                        echo "<div role='tabpanel' class='tab-pane fade".$activeClass."' id='periode".$periode."'>";
                        echo "<div class='table-responsive'>
                                <table class='table table-bordered table-striped table-hover js-basic-example dataTable'>
                                    <thead>
                                        <tr>
                                            <th style='min-width:100px;white-space:nowrap;'><center>Kode Barang</center></th>
                                            <th style='min-width:220px;'><center>Nama Barang</center></th>
                                            <th style='min-width:90px;white-space:nowrap;'><center>Satuan</center></th>
                                            <th style='min-width:150px;white-space:nowrap;'><center>Jenis</center></th>
                                            <th style='min-width:120px;white-space:nowrap;'><center>Kategori</center></th>
                                            <th style='min-width:120px;white-space:nowrap;'><center>Golongan</center></th>
                                            <th style='min-width:90px;white-space:nowrap;'><center>Stok</center></th>
                                            <th style='min-width:100px;white-space:nowrap;'><center>Harga</center></th>
                                            <th style='min-width:130px;white-space:nowrap;'><center>Nilai Aset</center></th>
                                        </tr>
                                    </thead>
                                    <tbody>";
                        $totalAset      = 0;
                        $queryStokMati  = bukaquery(
                            "select databarang.kode_brng,databarang.nama_brng,kodesatuan.satuan,jenis.nama as nama_jenis,kategori_barang.nama as nama_kategori,golongan_barang.nama as nama_golongan,databarang.dasar as harga from databarang ".
                            "inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat inner join jenis on databarang.kdjns=jenis.kdjns inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode ".
                            "inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode where databarang.status='1' and databarang.kode_brng not in ".
                            "(select riwayat_barang_medis.kode_brng from riwayat_barang_medis where riwayat_barang_medis.posisi<>'Opname' and riwayat_barang_medis.tanggal between SUBDATE(current_date(), INTERVAL ".$periode." MONTH) and current_date()) ".
                            "order by databarang.nama_brng asc"
                        );
                        while($rsqueryStokMati = mysqli_fetch_array($queryStokMati)) {
                            $stok = (float) getOne("select sum(gudangbarang.stok) from gudangbarang inner join bangsal on gudangbarang.kd_bangsal=bangsal.kd_bangsal where bangsal.status='1' and gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.kode_brng='".$rsqueryStokMati["kode_brng"]."'");
                            if($stok>0) {
                                $nilaiAset  = $stok * $rsqueryStokMati["harga"];
                                $totalAset += $nilaiAset;
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStokMati["kode_brng"]."</td>
                                        <td align='left'>".$rsqueryStokMati["nama_brng"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStokMati["satuan"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStokMati["nama_jenis"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStokMati["nama_kategori"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStokMati["nama_golongan"]."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($stok,1,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryStokMati["harga"],0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($nilaiAset,0,',','.')."</td>
                                      </tr>";
                            }
                        }
                        echo "</tbody>
                                <tfoot>
                                    <tr>
                                        <th colspan='8' style='text-align:right'>Total</th>
                                        <th style='text-align:right'>".number_format($totalAset,0,',','.')."</th>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>";
                        echo "</div>";
                    }
                ?>
                </div>
            </div>
        </div>
    </div>
</div>