<div id="post">
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $keyword     =isset($_GET['keyword'])?$_GET['keyword']:NULL;
                $norm        =isset($_GET['Pasien'])?$_GET['Pasien']:NULL;
                $poli        =isset($_GET['Poli'])?$_GET['Poli']:NULL;
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
                <tr class="head">
                    <td width="25%" >Unit/Poliklinik</td><td width="">:</td>
                    <td width="82%"><?php echo getOne("select nm_poli from poliklinik where kd_poli='$poli'");?></td>
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
        $_sql = "SELECT * FROM dokter where nm_dokter like '%".$keyword."%' ORDER BY nm_dokter ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysql_num_rows($hasil);
        
        if(mysql_num_rows($hasil)!=0) {            
            
            echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='90%'><div align='center'>Silahkan Pilih Dokter</strong></div></td>
                    </tr>";
                    while($baris = mysql_fetch_array($hasil)) {
                        echo "<tr class='isi' align='center'>
                                <td><a href='index.php?page=InputRegister&Pasien=$norm&Poli=$poli&Dokter=$baris[0]'><font size='5' color='#ff5555'>$baris[1]</font></a></td>                                
                             </tr>";
                    }
            echo "</table>";            
        } else {echo "Data dokter kosong !";}
    ?>
    </div>   
    </div>
</div>

