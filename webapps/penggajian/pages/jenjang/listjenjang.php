<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div id="post">
    <div class="entry">   
    <div align="center" class="link">
        <a href=?act=InputJenjang&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListJenjang>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
                $action  = isset($_GET['action'])?$_GET['action']:NULL;
                $keyword = trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;
                $keyword = validTeks($keyword);
                echo "<input type=hidden name=keyword value=$keyword><input type=hidden name=action value=$action>";
        ?>
        <table width="100%" align="center">
            <tr class="head">
                <td width="25%" >Keyword</td><td width="">:</td>
                <td width="82%">
                    <input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="65" maxlength="250" pattern="[a-zA-Z0-9, ./@_]{1,250}" title=" a-zA-Z0-9, ./@_ (Maksimal 250 karakter)" autocomplete="off" autofocus/>
                    <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;">
                </td>
            </tr>
        </table><br>
    </form>
    <div style="width: 100%; height: 78%; overflow: auto;">
    <?php
        $_sql   = "SELECT jnj_jabatan.kode,jnj_jabatan.nama,jnj_jabatan.tnj,jnj_jabatan.indek FROM jnj_jabatan where jnj_jabatan.kode like '%".$keyword."%' or jnj_jabatan.nama like '%".$keyword."%' ORDER BY jnj_jabatan.tnj DESC ";
        $hasil  = bukaquery($_sql);
        $jumlah = mysqli_num_rows($hasil);
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='15%'><div align='center'>Kode Jenjang</div></td>
                        <td width='43%'><div align='center'>Nama Jenjang</div></td>
                        <td width='20%'><div align='center'>Tunjangan Jabatan</div></td>
                        <td width='10%'><div align='center'>Index</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
                                <td>
                                    <center>
                                        <a href=?act=InputJenjang&action=UBAH&kode=".str_replace(" ","_",$baris[0]).">[edit]</a>";?>
                                        <a href="?act=ListJenjang&action=HAPUS&kode=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                               </td>
                                <td>$baris[0]</td>
                                <td>$baris[1]</td>
                                <td>".formatDuit($baris[2])."</td>
                                <td align='center'>$baris[3]</td>
                             </tr>";
                    }
            echo "</table>";
            
        } else {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='15%'><div align='center'>Kode Jenjang</div></td>
                        <td width='43%'><div align='center'>Nama Jenjang</div></td>
                        <td width='20%'><div align='center'>Tunjangan Jabatan</div></td>
                        <td width='10%'><div align='center'>Index</div></td>
                    </tr>
                  </table>";    
        }
        
        $aksi=isset($_GET['action'])?$_GET['action']:NULL;
        if ($aksi=="HAPUS") {
            Hapus(" jnj_jabatan "," kode ='".validTeks($_GET['kode'])."' ","?act=ListJenjang");
        }
    ?>
    </div>
    <?php
        if(mysqli_num_rows($hasil)!=0) {
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah | <a target=_blank href=../penggajian/pages/jenjang/LaporanJenjang.php?iyem=".encrypt_decrypt("{\"keyword\":\"".$keyword."\",\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">Laporan</a> | <a target=_blank href=../penggajian/pages/jenjang/LaporanJenjangExel.php?iyem=".encrypt_decrypt("{\"keyword\":\"".$keyword."\",\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">Excel</a> |</div></td>
                    </tr>     
                 </table>");
        }
    ?>
    </div>
</div>
