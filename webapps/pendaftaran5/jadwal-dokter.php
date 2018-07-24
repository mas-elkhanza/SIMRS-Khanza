<?php include_once ('layout/header.php'); ?>

    <section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>JADWAL DOKTER</h2>
            </div>

            <!-- Basic Examples -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="card">
                        <div class="header">
                            <h2>
                                Tanggal : <?php echo $date; ?>
                            </h2>
                        </div>
                        <div class="body">
                            <table id="jadwal_dokter" class="table table-bordered table-striped table-hover display nowrap" width="100%">
                                <thead>
                                    <tr>
                                        <th>Nama Dokter</th>
                                        <th>Poliklinik</th>
                                        <th>Jam Mulai</th>
                                        <th>Jam Selesai</th>
                                    </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- #END# Basic Examples -->

        </div>
    </section>

<?php include_once ('layout/footer.php'); ?>
