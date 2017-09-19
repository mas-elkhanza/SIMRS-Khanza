<div id="post">
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $keyword     =isset($_GET['keyword'])?$_GET['keyword']:NULL;
                $norm        =isset($_GET['Pasien'])?$_GET['Pasien']:NULL;
                echo "<input type=hidden name=keyword value=$keyword>"
                        . "<input type=hidden name=norm value=$norm>";
        ?>
            <table width="770px" align="center">
                <tr class="head">
                    <td width="25%" >No.RM</td><td width="">:</td>
                    <td width="82%"><?php echo$norm;?></td>
                </tr>
                <tr class="head">
                    <td width="25%" >Nama</td><td width="">:</td>
                    <td width="82%"><?php echo getOne("select nm_pasien from pasien where no_rkm_medis='$norm'");?></td>
                </tr>
            </table>
            <br>
            <table width="770px" align="center">
                <tr class="head">
                    <td width="25%" >Keyword</td><td width="">:</td>
                    <td width="82%"><input name="keyword" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" value="<?php echo $keyword;?>" size="35" maxlength="250" autofocus/>
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;">
                    </td>
                </tr>
            </table><br>
    </form>
    <div align="center">
    <div style="width: 770px; height: 75%; overflow: auto;">
    <?php
        $keyword=trim(isset($_POST['keyword']))?trim($_POST['keyword']):NULL;
        $_sql = "SELECT * FROM poliklinik where nm_poli like '%".$keyword."%' ORDER BY nm_poli ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysql_num_rows($hasil);
        
        if(mysql_num_rows($hasil)!=0) {            
            
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='90%'><div align='center'>Silahkan Pilih Unit/Poliklinik</strong></div></td>
                    </tr>";
                    while($baris = mysql_fetch_array($hasil)) {
                        echo "<tr class='isi' align='center'>
                                <td><a href='index.php?page=PilihDokter&Pasien=$norm&Poli=$baris[0]'><font size='5' color='#ff5555'>$baris[1]</font></a></td>                                
                             </tr>";
                    }
            echo "</table>";            
        } else {echo "Data Poliklinik masih kosong !";}
    ?>
    </div>   
    </div>
</div>

