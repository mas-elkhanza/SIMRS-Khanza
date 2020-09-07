<div id="post">        
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
            $action             = isset($_GET['action'])?$_GET['action']:NULL;
            $keyword            = str_replace("_"," ",isset($_GET['keyword']))?str_replace("_"," ",$_GET['keyword']):NULL;
        ?>
    <div style="width: 100%; height: 96%; overflow: auto;">
    <?php
        $_sql = "select perpustakaan_ebook.kode_ebook, perpustakaan_ebook.judul_ebook, perpustakaan_ebook.jml_halaman, 
               perpustakaan_penerbit.nama_penerbit, perpustakaan_pengarang.nama_pengarang, perpustakaan_ebook.thn_terbit,
               perpustakaan_kategori.nama_kategori, perpustakaan_jenis_buku.nama_jenis,perpustakaan_ebook.berkas from perpustakaan_ebook inner join perpustakaan_penerbit 
               inner join perpustakaan_jenis_buku inner join perpustakaan_kategori inner join perpustakaan_pengarang 
               on perpustakaan_ebook.kode_penerbit=perpustakaan_penerbit.kode_penerbit and perpustakaan_ebook.kode_pengarang=perpustakaan_pengarang.kode_pengarang 
               and perpustakaan_ebook.id_kategori=perpustakaan_kategori.id_kategori and perpustakaan_ebook.id_jenis=perpustakaan_jenis_buku.id_jenis 
               where perpustakaan_ebook.kode_ebook like '%$keyword%' 
                or perpustakaan_ebook.judul_ebook like '%$keyword%' 
                or perpustakaan_ebook.jml_halaman like '%$keyword%' 
                or perpustakaan_penerbit.nama_penerbit like '%$keyword%' 
                or perpustakaan_pengarang.nama_pengarang like '%$keyword%' 
                or perpustakaan_ebook.thn_terbit like '%$keyword%' 
                or perpustakaan_kategori.nama_kategori like '%$keyword%' 
                or perpustakaan_jenis_buku.nama_jenis like '%$keyword%' order by perpustakaan_ebook.kode_ebook ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);

        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head2'>
                        <td width='5%'><div align='center'>Proses</div></td>
                        <td width='8%'><div align='center'>Kode Ebook</div></td>
                        <td width='35%'><div align='center'>Judul Ebook</div></td>
                        <td width='4%'><div align='center'>Halaman</div></td>
                        <td width='11%'><div align='center'>Penerbit</div></td>
                        <td width='11%'><div align='center'>Pengarang</div></td>
                        <td width='4%'><div align='center'>Terbit</div></td>
                        <td width='11%'><div align='center'>Kategori</div></td>
                        <td width='11%'><div align='center'>Jenis</div></td>
                    </tr>";
            while($baris = mysqli_fetch_array($hasil)) {                        
              echo "<tr class='isi'>
                        <td valign='top'>
                            <center>
                            <a target=_blank href=../ebook/pages/upload/".$baris["berkas"].">[File]</a>
                            </center>
                        </td>
                        <td align='left'>".$baris["kode_ebook"]."</td>
                        <td align='left'>".$baris["judul_ebook"]."</td>
                        <td align='center'>".$baris["jml_halaman"]."</td>
                        <td align='left'>".$baris["nama_penerbit"]."</td>
                        <td align='left'>".$baris["nama_pengarang"]."</td>
                        <td align='center'>".$baris["thn_terbit"]."</td>
                        <td align='left'>".$baris["nama_kategori"]."</td>
                        <td align='left'>".$baris["nama_jenis"]."</td>
                   </tr>";
            }
            echo "</table>";
        } else {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head2'>
                        <td width='5%'><div align='center'>Proses</div></td>
                        <td width='8%'><div align='center'>Kode Ebook</div></td>
                        <td width='35%'><div align='center'>Judul Ebook</div></td>
                        <td width='4%'><div align='center'>Halaman</div></td>
                        <td width='11%'><div align='center'>Penerbit</div></td>
                        <td width='11%'><div align='center'>Pengarang</div></td>
                        <td width='4%'><div align='center'>Terbit</div></td>
                        <td width='11%'><div align='center'>Kategori</div></td>
                        <td width='11%'><div align='center'>Jenis</div></td>
                    </tr>
                 </table>";
        }
    ?>
    </div>
    <?php
        echo("<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                <tr class='head'>
                    <td><div align='left'>Data : $jumlah</div></td>                        
                </tr>     
             </table>");
    ?>
</div>

