<div class="block-header">
    <h2><center>DAFECTA/DARURAT STOK FARMASI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:100px;white-space:nowrap;"><center>Kode Barang</center></th>
                                <th style="min-width:220px;"><center>Nama Barang</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Satuan</center></th>
                                <th style="min-width:150px;white-space:nowrap;"><center>Jenis</center></th>
                                <th style="min-width:90px;white-space:nowrap;"><center>Minimal</center></th>
                                <th style="min-width:90px;white-space:nowrap;"><center>Saat Ini</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $queryDefecta = bukaquery("select databarang.kode_brng,databarang.nama_brng,kodesatuan.satuan,databarang.stokminimal,jenis.nama from databarang inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat inner join jenis on databarang.kdjns=jenis.kdjns where databarang.status='1' order by databarang.nama_brng asc");
                            while($rsqueryDefecta = mysqli_fetch_array($queryDefecta)) {
                                $stoksaatini = (float) getOne("select sum(gudangbarang.stok) from gudangbarang inner join bangsal on gudangbarang.kd_bangsal=bangsal.kd_bangsal where bangsal.status='1' and gudangbarang.no_batch='' and gudangbarang.no_faktur='' and gudangbarang.kode_brng='".$rsqueryDefecta["kode_brng"]."'");
                                if($stoksaatini<=$rsqueryDefecta["stokminimal"]) {
                                    echo "<tr>
                                            <td align='left' style='white-space:nowrap;'>".$rsqueryDefecta["kode_brng"]."</td>
                                            <td align='left'>".$rsqueryDefecta["nama_brng"]."</td>
                                            <td align='left' style='white-space:nowrap;'>".$rsqueryDefecta["satuan"]."</td>
                                            <td align='left' style='white-space:nowrap;'>".$rsqueryDefecta["nama"]."</td>
                                            <td align='right' style='white-space:nowrap;'>".number_format($rsqueryDefecta["stokminimal"],1,',','.')."</td>
                                            <td align='right' style='white-space:nowrap;'>".number_format($stoksaatini,1,',','.')."</td>
                                          </tr>";
                                }
                            }
                        ?>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>