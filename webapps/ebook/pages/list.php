<div id="post">        
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
            echo "<br>";
            $action             = isset($_GET['action'])?$_GET['action']:NULL;
            $keyword            = str_replace("_"," ",isset($_GET['keyword']))?str_replace("_"," ",$_GET['keyword']):NULL;
            $kode_ebook         = str_replace("_"," ",isset($_GET['kode_ebook']))?str_replace("_"," ",$_GET['kode_ebook']):NULL;
            $kode_ebook2        ="";
            $judul_ebook        = "";
            $jml_halaman        = "";
            $kode_penerbit      = "";
            $kode_pengarang     = "";
            $id_jenis           = "";
            $id_kategori        = "";
            $berkas             = "";
            $thn_terbit         = "";
            if($action == "TAMBAH"){
                $max                = getOne("select ifnull(MAX(CONVERT(RIGHT(kode_ebook,7),signed)),0)+1 from perpustakaan_ebook");
                $kode_ebook         = "E".sprintf("%07s", $max);
                $kode_ebook2        ="";
                $judul_ebook        = "";
                $jml_halaman        = "";
                $kode_penerbit      = "";
                $kode_pengarang     = "";
                $id_jenis           = "";
                $id_kategori        = "";
                $berkas             = "";
                $thn_terbit         = "";
            }else if($action == "UBAH"){
                $_sql               = "SELECT * FROM perpustakaan_ebook WHERE kode_ebook='$kode_ebook'";
                $hasil              = bukaquery($_sql);
                while($baris = mysqli_fetch_array($hasil)) {
                    $kode_ebook         = $baris["kode_ebook"];
                    $kode_ebook2        = $baris["kode_ebook"];
                    $judul_ebook        = $baris["judul_ebook"];
                    $jml_halaman        = $baris["jml_halaman"];
                    $kode_penerbit      = $baris["kode_penerbit"];
                    $kode_pengarang     = $baris["kode_pengarang"];
                    $id_jenis           = $baris["id_jenis"];
                    $id_kategori        = $baris["id_kategori"];
                    $berkas             = $baris["berkas"];
                    $thn_terbit         = $baris["thn_terbit"];
                }
                    
            }
            echo "<input type=hidden name=action value=$action><input type=hidden name=kode_ebook2 value=$kode_ebook2>";
        ?>
        
        <table width="100%" align="center" border="0" cellpadding="0" cellspacing="0" class="tbl_form">
            <tr class="isi2">
                <td width="15%" >Kode Ebook</td>
                <td width="35%">
                    :&nbsp;<input name="kode_ebook" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $kode_ebook;?>" size="20" maxlength="10">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="15%" >Pengarang</td>
                <td width="35%">
                    :&nbsp;<select name="kode_pengarang" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                        <?php
                            $_sql = "SELECT kode_pengarang,nama_pengarang FROM perpustakaan_pengarang ORDER BY nama_pengarang";
                            $hasil=bukaquery($_sql);
                            if($action == "UBAH"){
                                echo "<option id='TxtIsi5' value='$kode_pengarang'>$kode_pengarang ".getOne("select nama_pengarang FROM perpustakaan_pengarang where kode_pengarang='$kode_pengarang'")."</option>";
                            }
                            while($baris = mysqli_fetch_array($hasil)) {
                                echo "<option id='TxtIsi5' value='$baris[0]'>$baris[0] $baris[1]</option>";
                            }
                        ?>
                    </select>
                    <span id="MsgIsi5" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>  
            <tr class="isi2">
                <td width="15%" >Judul Ebook</td>
                <td width="35%">
                    :&nbsp;<input name="judul_ebook" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $judul_ebook;?>" size="50" maxlength="200">
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="15%" >Tahun Terbit</td>
                <td width="35%">
                    :&nbsp;<select name="thn_terbit" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                        <?php                               
                            if($action == "UBAH"){
                                echo "<option id='TxtIsi6' value=$thn_terbit>$thn_terbit</option>";
                            }

                            loadThnnow();
                         ?>
                    </select>
                    <span id="MsgIsi6" style="color:#CC0000; font-size:10px;"></span>
                    &nbsp;&nbsp;
                    Jml.Halaman :&nbsp;<input name="jml_halaman" class="text5" onkeydown="setDefault(this, document.getElementById('MsgIsi7'));" type="number" id="TxtIsi7" class="inputbox" value="<?php echo $jml_halaman;?>" size="5" maxlength="7">
                    <span id="MsgIsi7" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr>
            <tr class="isi2">
                <td width="15%" >Penerbit</td>
                <td width="35%">
                    :&nbsp;<select name="kode_penerbit" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                        <?php
                            $_sql = "SELECT kode_penerbit,nama_penerbit FROM perpustakaan_penerbit ORDER BY nama_penerbit";
                            $hasil=bukaquery($_sql);
                            if($action == "UBAH"){
                                echo "<option id='TxtIsi3' value='$kode_penerbit'>$kode_penerbit ".getOne("select nama_penerbit FROM perpustakaan_penerbit where kode_penerbit='$kode_penerbit'")."</option>";
                            }
                            while($baris = mysqli_fetch_array($hasil)) {
                                echo "<option id='TxtIsi3' value='$baris[0]'>$baris[0] $baris[1]</option>";
                            }
                        ?>
                    </select>
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="15%" >Kategori Koleksi</td>
                <td width="35%">
                    :&nbsp;<select name="id_kategori" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi8'));" id="TxtIsi8">
                        <?php
                            $_sql = "SELECT id_kategori,nama_kategori FROM perpustakaan_kategori ORDER BY nama_kategori";
                            $hasil=bukaquery($_sql);
                            if($action == "UBAH"){
                                echo "<option id='TxtIsi8' value='$id_kategori'>$id_kategori ".getOne("select nama_kategori FROM perpustakaan_kategori where id_kategori='$id_kategori'")."</option>";
                            }
                            while($baris = mysqli_fetch_array($hasil)) {
                                echo "<option id='TxtIsi8' value='$baris[0]'>$baris[0] $baris[1]</option>";
                            }
                        ?>
                    </select>
                    <span id="MsgIsi8" style="color:#CC0000; font-size:10px;"></span>
                </td>
            </tr> 
            <tr class="isi2">
                <td width="15%" >Jenis Koleksi</td>
                <td width="35%">
                    :&nbsp;<select name="id_jenis" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                        <?php
                            $_sql = "SELECT id_jenis,nama_jenis FROM perpustakaan_jenis_buku ORDER BY nama_jenis";
                            $hasil=bukaquery($_sql);
                            if($action == "UBAH"){
                                echo "<option id='TxtIsi4' value='$id_jenis'>$id_jenis ".getOne("select nama_jenis FROM perpustakaan_jenis_buku where id_jenis='$id_jenis'")."</option>";
                            }
                            while($baris = mysqli_fetch_array($hasil)) {
                                echo "<option id='TxtIsi4' value='$baris[0]'>$baris[0] $baris[1]</option>";
                            }
                        ?>
                    </select>
                    <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="15%" >File Ebook</td>
                <td width="35%">
                <?php if($action == "UBAH"){ ?>
                        :&nbsp;<input name="berkas" class="text" type="file" class="inputbox" value="<?php echo isset($berkas)?$berkas:NULL;?>" size="40" maxlength="200">
                <?php }else{ ?>
                        :&nbsp;<input name="berkas" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi9'));" type="file" id="TxtIsi9" class="inputbox" value="<?php echo isset($berkas)?$berkas:NULL;?>" size="40" maxlength="200">
                        <span id="MsgIsi9" style="color:#CC0000; font-size:10px;"></span>
                <?php } ?>
                </td>
            </tr> 
        </table>
        <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;Simpan&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;Kosong&nbsp;&nbsp;"></div>
        <?php
            $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
            if (isset($BtnSimpan)) {
                $kode_ebook         =trim($_POST['kode_ebook']);
                $kode_ebook2        =trim($_POST['kode_ebook2']);
                $judul_ebook        =trim($_POST['judul_ebook']);
                $jml_halaman        =trim($_POST['jml_halaman']);
                $kode_penerbit      =trim($_POST['kode_penerbit']);
                $kode_pengarang     =trim($_POST['kode_pengarang']);
                $id_jenis           =trim($_POST['id_jenis']);
                $id_kategori        =trim($_POST['id_kategori']);
                $thn_terbit         =trim($_POST['thn_terbit']);
                $berkas             =str_replace(" ","_","pages/upload/".$_FILES['berkas']['name']);
                move_uploaded_file($_FILES['berkas']['tmp_name'],$berkas);

                if ((!empty($kode_ebook))&&(!empty($judul_ebook))&&(!empty($jml_halaman))&&(!empty($kode_penerbit))&&(!empty($kode_pengarang))&&(!empty($id_jenis))&&(!empty($id_kategori))&&(!empty($berkas))) {
                    switch($action) {
                        case "TAMBAH":
                            if(strtolower(substr($berkas,-3))=="pdf"){
                                move_uploaded_file($_FILES['berkas']['tmp_name'],$berkas);
                                Tambah(" perpustakaan_ebook "," '$kode_ebook', '$judul_ebook', '$jml_halaman', '$kode_penerbit', '$kode_pengarang', '$thn_terbit', '$id_kategori', '$id_jenis', '$berkas'", " Koleksi Ebook " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=List&action=TAMBAH'>";
                            }else{
                                echo "Berkas harus pdf";
                            }   
                            break;
                        case "UBAH":
                            if($berkas=="pages/upload/"){
                                $ph="";
                            }else if($berkas<>"pages/upload/"){
                                $ph=",berkas='$berkas'";
                                move_uploaded_file($_FILES['berkas']['tmp_name'],$berkas);
                            }
                            Ubah(" perpustakaan_ebook ","kode_ebook='$kode_ebook',judul_ebook='$judul_ebook',jml_halaman='$jml_halaman',kode_penerbit='$kode_penerbit',kode_pengarang='$kode_pengarang',thn_terbit='$thn_terbit',id_kategori='$id_kategori',id_jenis='$id_jenis' $ph where kode_ebook='$kode_ebook2'", " Koleksi Ebook " );
                            echo"<meta http-equiv='refresh' content='1;URL=?act=List&action=TAMBAH'>";
                            break;
                    }                        
                }else if ((empty($kode_ebook))||(empty($judul_ebook))||(empty($jml_halaman))||(empty($kode_penerbit))||(empty($kode_pengarang))||(empty($id_jenis))||(empty($id_kategori))||(empty($berkas))){
                    echo 'Semua field harus isi..!!!';
                }
            }
        ?>
    </form>
    <div style="width: 100%; height: 72%; overflow: auto;">
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
                        <td width='9%'><div align='center'>Proses</div></td>
                        <td width='8%'><div align='center'>Kode Ebook</div></td>
                        <td width='31%'><div align='center'>Judul Ebook</div></td>
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
                            <a href='?act=List&action=UBAH&kode_ebook=".str_replace(" ","_",$baris[0])."'>[edit]</a>
                            <a href='?act=List&action=HAPUS&berkas=".$baris["berkas"]."&kode_ebook=".str_replace(" ","_",$baris[0])."'>[hapus]</a>
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
                        <td width='9%'><div align='center'>Proses</div></td>
                        <td width='8%'><div align='center'>Kode Ebook</div></td>
                        <td width='31%'><div align='center'>Judul Ebook</div></td>
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
        if ($action=="HAPUS") {
            unlink($_GET['berkas']);
            Hapus(" perpustakaan_ebook ","  kode_ebook='".$_GET['kode_ebook']."'","?act=List&action=TAMBAH");
        }
        
        echo("<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                <tr class='head'>
                    <td><div align='left'>Data : $jumlah</div></td>                        
                </tr>     
             </table>");
    ?>
</div>

