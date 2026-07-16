<div class="block-header">
    <h2><center>DATA KADALUARSA 3 BULAN KEDEPAN</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:100px;white-space:nowrap;"><center>No.Batch</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Kode Barang</center></th>
                                <th style="min-width:220px;"><center>Nama Barang</center></th>
                                <th style="min-width:90px;white-space:nowrap;"><center>Satuan</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Tgl.Datang</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Kadaluarsa</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Asal</center></th>
                                <th style="min-width:140px;white-space:nowrap;"><center>No.Faktur</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Harga Beli</center></th>
                                <th style="min-width:90px;white-space:nowrap;"><center>Jml.Beli</center></th>
                                <th style="min-width:80px;white-space:nowrap;"><center>Sisa</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $queryKadaluarsa = bukaquery(
                                "select data_batch.no_batch,data_batch.kode_brng,databarang.nama_brng,kodesatuan.satuan,data_batch.tgl_beli,data_batch.tgl_kadaluarsa,data_batch.asal,data_batch.no_faktur,data_batch.h_beli,data_batch.jumlahbeli,data_batch.sisa from data_batch ".
                                "inner join databarang inner join kodesatuan on data_batch.kode_brng=databarang.kode_brng and kodesatuan.kode_sat=databarang.kode_sat where data_batch.tgl_kadaluarsa between '".date("Y-m-d")."' and '".date("Y-m-d", strtotime("+3 month"))."' ".
                                "order by data_batch.tgl_kadaluarsa asc"
                            );
                            while($rsqueryKadaluarsa = mysqli_fetch_array($queryKadaluarsa)) {
                                echo "<tr>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryKadaluarsa["no_batch"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryKadaluarsa["kode_brng"]."</td>
                                        <td align='left'>".$rsqueryKadaluarsa["nama_brng"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryKadaluarsa["satuan"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryKadaluarsa["tgl_beli"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryKadaluarsa["tgl_kadaluarsa"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryKadaluarsa["asal"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryKadaluarsa["no_faktur"]."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryKadaluarsa["h_beli"],0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryKadaluarsa["jumlahbeli"],0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryKadaluarsa["sisa"],0,',','.')."</td>
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