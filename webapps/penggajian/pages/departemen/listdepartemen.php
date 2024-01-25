<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
?>
<div id="post">
    <div class="entry">   
    <div align="center" class="link">
        <a href=?act=InputDepartemen&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListDepartemen>| List Data |</a>
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
        $_sql   = "SELECT dep_id,nama FROM departemen where dep_id like '%".$keyword."%' or nama like '%".$keyword."%' ORDER BY dep_id ASC ";
        $hasil  = bukaquery($_sql);
        $jumlah = mysqli_num_rows($hasil);
        
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='23%'><div align='center'>Dep ID</div></td>
                        <td width='65%'><div align='center'>Nama Departemen</div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
				<td width='120'>
                                    <center>
                                        <a href=?act=InputDepartemen&action=UBAH&dep_id=".str_replace(" ","_",$baris[0]).">[edit]</a>";?>
                                        <a href="?act=ListDepartemen&action=HAPUS&dep_id=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                               </td>
                                <td>$baris[0] &nbsp;</td>
                                <td>$baris[1] &nbsp;</td>                                
                             </tr>";
                    }
            echo "</table>";
        } else {
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='12%'><div align='center'>Proses</div></td>
                        <td width='23%'><div align='center'>Dep ID</div></td>
                        <td width='65%'><div align='center'>Nama Departemen</div></td>
                    </tr>
                 </table>";
        }
    ?>
    
    <?php
       $aksi=isset($_GET['action'])?$_GET['action']:NULL;
       if ($aksi=="HAPUS") {
            Hapus(" departemen "," dep_id ='". validTeks($_GET['dep_id'])."' ","?act=ListDepartemen");
       }
    ?>
    </div>
    <?php
        if(mysqli_num_rows($hasil)!=0) {
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah | <a target=_blank href=../penggajian/pages/departemen/LaporanDepartemen.php?iyem=".encrypt_decrypt("{\"keyword\":\"".$keyword."\",\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">Laporan</a> | <a target=_blank href=../penggajian/pages/departemen/LaporanDepartemenExel.php?iyem=".encrypt_decrypt("{\"keyword\":\"".$keyword."\",\"usere\":\"".USERHYBRIDWEB."\",\"passwordte\":\"".PASHYBRIDWEB."\"}","e").">Excel</a> |</div></td>                        
                    </tr>     
                 </table>");
        }
    ?>
    </div>
</div>
