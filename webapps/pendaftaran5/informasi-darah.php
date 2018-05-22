<?php include_once ('layout/header.php'); ?>

    <section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>INFO KETERSEDIAAN DARAH UTD</h2>
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
                            <table id="informasi_darah" class="table table-bordered table-striped table-hover display nowrap" width="100%">
                                <thead>
                                    <tr>
                                        <th>No Kantong</th>
                                        <th>Kode Komponen</th>
                                        <th>Golongan Darah</th>
                                        <th>Tanggal Kadaluarsa</th>
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
