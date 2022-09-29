<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div class="block-header">
    <h2><center>KOLEKSI EBOOK/PERPUSTAKAAN</center></h2>
</div>
<div class="row clearfix">
    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
        <div class="card">
            <div class="body">
                <div class="table-responsive">
                    <table class="table table-bordered table-striped table-hover js-basic-example dataTable">
                        <thead>
                            <tr>
                                <th width="15%"><center>Kode Ebook</center></th>
                                <th width="40%"><center>Judul Ebook</center></th>
                                <th width="15%"><center>Penerbit</center></th>
                                <th width="15%"><center>Pengarang</center></th>
                                <th width="10%"><center>Terbit</center></th>
                                <th width="5%"><center>File</center></th>
                            </tr>
                        </thead>
                        <tbody>
                        <?php 
                           $queryperpustakaan = bukaquery("select perpustakaan_ebook.kode_ebook, perpustakaan_ebook.judul_ebook, perpustakaan_ebook.jml_halaman, 
                                perpustakaan_penerbit.nama_penerbit, perpustakaan_pengarang.nama_pengarang, perpustakaan_ebook.thn_terbit,perpustakaan_ebook.berkas 
                                from perpustakaan_ebook inner join perpustakaan_penerbit inner join perpustakaan_pengarang on perpustakaan_ebook.kode_penerbit=perpustakaan_penerbit.kode_penerbit 
                                and perpustakaan_ebook.kode_pengarang=perpustakaan_pengarang.kode_pengarang order by perpustakaan_ebook.kode_ebook ");
                           while($rsqueryperpustakaan = mysqli_fetch_array($queryperpustakaan)) {
                               echo "<tr>
                                        <td align='center' valign='middle'>".$rsqueryperpustakaan["kode_ebook"]."</td>
                                        <td align='left' valign='middle'>".$rsqueryperpustakaan["judul_ebook"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperpustakaan["nama_penerbit"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperpustakaan["nama_pengarang"]."</td>
                                        <td align='center' valign='middle'>".$rsqueryperpustakaan["thn_terbit"]."</td>
                                        <td align='center' valign='middle'><a target=_blank href='http://".host()."/webapps/ebook/".$rsqueryperpustakaan["berkas"]."' class='btn btn-warning waves-effect'>Baca</a></td>
                                     </tr>";
                           }
                        ?>
                        </tbody>
                    </table>
                </div>
                <center><a href="index.php?act=HomeUser&hal=Beranda" class="btn btn-danger waves-effect">Kembali</a></center>
            </div>
        </div>
    </div>
</div>