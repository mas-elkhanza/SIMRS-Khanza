<?php include_once ('layout/header.php'); ?>

    <section class="content">
        <div class="container-fluid">
            <div class="block-header">
                <h2>INFO KAMAR & BANGSAL</h2>
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
                            <table id="informasi_kamar" class="table table-bordered table-striped table-hover display nowrap" width="100%">
                                <thead>
                                    <tr>
                                        <th>Kelas Kamar</th>
                                        <th>Jumlah Bed</th>
                                        <th>Bed Terisi</th>
                                        <th>Bed Kosong</th>
                                    </tr>
                                </thead>
                            </table>
                            <table id="informasi_bangsal" class="table table-bordered table-striped table-hover display nowrap" width="100%">
                                <thead>
                                    <tr>
                                        <th>Nama Kamar</th>
                                        <th>Jumlah Bed</th>
                                        <th>Bed Terisi</th>
                                        <th>Bed Kosong</th>
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
