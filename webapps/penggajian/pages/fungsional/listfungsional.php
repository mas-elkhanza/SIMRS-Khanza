<div class="t">
<div class="b">
<div class="l">
<div class="r">
<div class="bl">
<div class="br">
<div class="tl">
<div class="tr">
<div class="y">

<div id="post">
    <div class="entry">   

    <div align="center" class="link">
        <a href=?act=InputFungsional&action=TAMBAH>| Input Data |</a>
        <a href=?act=ListFungsional>| List Data |</a>
    </div>   
	<br/>
    <div style="width: 598px; height: 500px; overflow: auto;">
    <?php
        $awal=$_GET['awal'];
        if (empty($awal)) $awal=0;
        $_sql = "SELECT kode,nama FROM fungsional ORDER BY kode ASC ";
        $hasil=bukaquery($_sql);
        $jumlah=mysqli_num_rows($hasil);
        
        if(mysqli_num_rows($hasil)!=0) {
            echo "<table width='600px' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td width='20%'><div align='center'><font size='2' face='Verdana'><strong>Kode Jabatan</strong></font></div></td>
                        <td width='57%'><div align='center'><font size='2' face='Verdana'><strong>Jabatan Fungsional</strong></font></div></td>
                        <td width='23%'><div align='center'><font size='2' face='Verdana'><strong>Proses</strong></font></div></td>
                    </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                        echo "<tr class='isi'>
                                <td>$baris[0]</td>
                                <td>$baris[1]</td>
                                <td width='120'>
                                    <center>
                                        <a href=?act=InputFungsional&action=UBAH&kode=$baris[0]>edit</a>";?>
                                        <a href="?act=ListFungsional&action=HAPUS&kode=<?php print $baris[0] ?>" >| hapus</a>
                            <?php
                            echo "</center>
                               </td>
                             </tr>";
                    }
            echo "</table>";            
        } else {echo "Data Jabatan Fungsional masih kosong !";}

    ?>
    
    <?php
       if ($action=="HAPUS") {
            Hapus(" fungsional "," kode ='".$_GET['kode']."' ","?act=ListFungsional");
       }
    ?>
    </div>
    <?php
        if(mysqli_num_rows($hasil)!=0) {
            $hasil1=bukaquery("SELECT kode,nama FROM fungsional");
            $jumladiv=mysqli_num_rows($hasil1);
            $i=$jumladiv/19;
            $i=ceil($i);
            echo("<br/>Jumlah Record : $jumlah ");
        }
    ?>
    </div>
</div>

</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>
</div>