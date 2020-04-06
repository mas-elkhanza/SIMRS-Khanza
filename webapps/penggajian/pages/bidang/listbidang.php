

<div id="post">
<div class="entry"> 
    <div align="center" class="link">
        <a href=?act=InputBidang&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListBidang>| List Data |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $keyword     =isset($_GET['keyword'])?$_GET['keyword']:NULL;
                echo "<input type=hidden name=keyword value=$keyword><input type=hidden name=action value=$action>";
        ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="25%" >Keyword</td><td width="">:</td>
                    <td width="82%"><input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="65" maxlength="250" />
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;">
                    </td>
                </tr>
            </table><br>
    </form>
    <div style="width: 100%; height: 78%; overflow: auto;">
    <?php
        $keyword = trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;
        $keyword = validTeks($keyword);
        $_sql = "SELECT nama FROM bidang where nama like '%".$keyword."%' ORDER BY nama ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        
        if(mysqli_num_rows($hasil)!=0) {            
            
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='10%'><div align='center'>Proses</strong></div></td>
                        <td width='90%'><div align='center'>Bidang</strong></div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
								<td>
                                    <center>";?>
                                        <a href="?act=ListBidang&action=HAPUS&nama=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[0]</td>                                
                             </tr>";
                    }
            echo "</table>";
            
        } else {echo "Data Bidang masih kosong !";}
         
        $hapus=isset($_GET['action'])?$_GET['action']:NULL;
        if ($hapus=="HAPUS") {
            Hapus(" bidang "," nama ='".$_GET['nama']."' ","?act=ListBidang");
        }
    ?>
    </div>
    <?php
        if(mysqli_num_rows($hasil)!=0) {
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah | <a target=_blank href=../penggajian/pages/bidang/LaporanBidang.php?&keyword=$keyword>Laporan</a> | <a target=_blank href=../penggajian/pages/bidang/LaporanBidangExel.php?&keyword=$keyword>Excel</a> |</div></td>                        
                    </tr>     
                 </table>");
        }
    ?>
	</div>
</div>

