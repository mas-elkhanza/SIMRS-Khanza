<div class="block-header">
    <h2><center>SISA STOK & NILAI ASET FARMASI</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th style="min-width:40px;white-space:nowrap;"><center>No</center></th>
                                <th style="min-width:100px;white-space:nowrap;"><center>Kode Barang</center></th>
                                <th style="min-width:220px;"><center>Nama Barang</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Jenis</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Kategori</center></th>
                                <th style="min-width:120px;white-space:nowrap;"><center>Golongan</center></th>
                                <th style="min-width:80px;white-space:nowrap;"><center>Satuan</center></th>
                                <th style="min-width:110px;white-space:nowrap;"><center>Harga Dasar</center></th>
                                <?php
                                    $bangsalList  = [];
                                    $querybangsal = bukaquery("select bangsal.kd_bangsal,bangsal.nm_bangsal from bangsal where bangsal.status='1' and bangsal.kd_bangsal<>'-' order by bangsal.kd_bangsal asc");
                                    while($rsquerybangsal = mysqli_fetch_array($querybangsal)) {
                                        $bangsalList[] = $rsquerybangsal["kd_bangsal"];
                                        echo "<th style='min-width:110px;white-space:nowrap;'><center>".$rsquerybangsal["nm_bangsal"]."</center></th>";
                                    }
                                ?>
                                <th style="min-width:90px;white-space:nowrap;"><center>Total</center></th>
                                <th style="min-width:130px;white-space:nowrap;"><center>Nilai Aset</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php
                            $sqlkolomstok = "";
                            foreach($bangsalList as $kodebangsal) {
                                $sqlkolomstok .= ",sum(case when g.kd_bangsal='".$kodebangsal."' then g.total_stok else 0 end) as stok_".$kodebangsal;
                            }

                            $no        = 1;
                            $totalAset = 0;
                            $queryStok = bukaquery(
                                "select databarang.kode_brng,databarang.nama_brng,databarang.kode_sat,databarang.dasar,jenis.nama as nama_jenis,kategori_barang.nama as nama_kategori,golongan_barang.nama as nama_golongan".$sqlkolomstok." from databarang ".
                                "inner join jenis on databarang.kdjns=jenis.kdjns inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode ".
                                "left join (select gudangbarang.kode_brng,gudangbarang.kd_bangsal,sum(gudangbarang.stok) as total_stok from gudangbarang where gudangbarang.no_batch='' and gudangbarang.no_faktur='' group by gudangbarang.kode_brng,gudangbarang.kd_bangsal) g on g.kode_brng=databarang.kode_brng ".
                                "where databarang.status='1' group by databarang.kode_brng,jenis.nama,kategori_barang.nama,golongan_barang.nama order by databarang.kode_brng asc"
                            );
                            while($rsqueryStok = mysqli_fetch_array($queryStok)) {
                                $totalBarang = 0;
                                echo "<tr>
                                        <td align='center' style='white-space:nowrap;'>".$no++."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStok["kode_brng"]."</td>
                                        <td align='left'>".$rsqueryStok["nama_brng"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStok["nama_jenis"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStok["nama_kategori"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStok["nama_golongan"]."</td>
                                        <td align='left' style='white-space:nowrap;'>".$rsqueryStok["kode_sat"]."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($rsqueryStok["dasar"],0,',','.')."</td>";
                                foreach($bangsalList as $kodebangsal) {
                                    $stokbangsal  = (int)$rsqueryStok["stok_".$kodebangsal];
                                    $totalBarang += $stokbangsal;
                                    echo "<td align='right' style='white-space:nowrap;'>".number_format($stokbangsal,0,',','.')."</td>";
                                }
                                $nilaiAsetBarang  = $rsqueryStok["dasar"] * $totalBarang;
                                $totalAset       += $nilaiAsetBarang;
                                echo "<td align='right' style='white-space:nowrap;'>".number_format($totalBarang,0,',','.')."</td>
                                        <td align='right' style='white-space:nowrap;'>".number_format($nilaiAsetBarang,0,',','.')."</td>
                                      </tr>";
                            }
                        ?>
                        </tbody>
                        <tfoot>
                            <tr>
                                <th colspan="<?=(8+count($bangsalList)+1);?>" style="text-align:right">Total</th>
                                <th style="text-align:right"><?=number_format($totalAset,0,',','.');?></th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>