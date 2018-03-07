
<div id="post">
    <div align="center" class="link">
	<a href=?act=DetailTunjanganBulanan&action=TAMBAH>| Ms.Tunj Bulanan |</a>
        <a href=?act=DetailTunjanganHarian&action=TAMBAH>| Ms.Tunj Harian |</a>
        <a href=?act=DetailHarianBulanan&action=TAMBAH>| Harian-Bulanan |</a>
        <a href=?act=ListTunjangan>| List Penerima |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action             =isset($_GET['action'])?$_GET['action']:NULL;
                @$tnj                =$_GET['tnj'];
                @$tnj2                =$_GET['tnj2'];
                echo "<input type=hidden name=tnj  value=$tnj><input type=hidden name=tnj2  value=$tnj2><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Tunjangan Harian</td><td width="">:</td>
                    <td width="67%">
                        <select name="tnj" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT id,nama,tnj FROM master_tunjangan_harian ORDER BY nama";
                                $hasil=bukaquery($_sql);
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi1' value='$baris[0]'>$baris[1]  ".formatDuit($baris[2])."</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Tunjangan Bulanan</td><td width="">:</td>
                    <td width="67%">
                        <select name="tnj2" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT id,nama,tnj FROM master_tunjangan_bulanan ORDER BY nama";
                                $hasil=bukaquery($_sql);
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi1' value='$baris[0]'>$baris[1]  ".formatDuit($baris[2])."</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                @$BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    @$tnj                =trim($_POST['tnj']);
                    @$tnj2               =trim($_POST['tnj2']);
                    if ((!empty($tnj))&&(!empty($tnj2))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" harian_kurangi_bulanan ","'$tnj','$tnj2'", " Data Tnj.Harian - Tnj.Bulanan " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=DetailHarianBulanan&action=TAMBAH'>";
                                break;
                        }
                    }else if ((empty($tnj))||(empty($tnj2))){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 69%; overflow: auto;">
            <?php
                
                $_sql = "SELECT master_tunjangan_harian.nama,master_tunjangan_bulanan.nama,
                        master_tunjangan_harian.id,master_tunjangan_bulanan.id 
						from harian_kurangi_bulanan inner join master_tunjangan_harian 
						inner join master_tunjangan_bulanan on harian_kurangi_bulanan.harian=master_tunjangan_harian.id and
						harian_kurangi_bulanan.bulanan=master_tunjangan_bulanan.id 
						ORDER BY master_tunjangan_harian.nama ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='12%'><div align='center'>Proses</div></td>
                                <td width='44%'><div align='center'>Harian</div></td>
                                <td width='44%'><div align='center'>Bulanan</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                      echo "<tr class='isi'>
                                <td>
                                    <center>";?>
                                    <a href="?act=DetailHarianBulanan&action=HAPUS&tnj=<?php print $baris[2]?>&tnj2=<?php print $baris[3]?>">[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[0]</td>
                                <td>$baris[1]</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "<b>Data Master Tunjangan Harian - Bulanan !</b>";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus("harian_kurangi_bulanan"," harian='".$tnj."' and bulanan='".$tnj2."' ","?act=DetailHarianBulanan&action=TAMBAH");
            }

            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        ?>
    </div>

</div>

