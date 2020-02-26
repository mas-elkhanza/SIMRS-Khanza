
<div id="post">
   <div align="center" class="link">
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>   
    <div class="entry">
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action            =isset($_GET['action'])?$_GET['action']:NULL;
                $id                =isset($_GET['id'])?$_GET['id']:NULL;
                $jmlks             =isset($_GET['jmlks'])?$_GET['jmlks']:NULL;
                $bsr               =isset($_GET['bsr'])?$_GET['bsr']:NULL;

                echo "<input type=hidden name=id  value=$id><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="25%" >Pegawai</td><td width="">:</td>
                    <td width="75%">
                        <select name="id" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT id,nik,nama FROM pegawai  ORDER BY nama";
                                $hasil=bukaquery($_sql);
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi1' value='$baris[0]'>$baris[2] $baris[1]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Jml.KS</td><td width="">:</td>
                    <td width="75%"><input name="jmlks" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $jmlks;?>" size="10" maxlength="10">
                    isi dengan - jika ingin KS mengikuti normal masuk, isi dengan angka masuk jika tidak !!!!
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Besar Tunjangan</td><td width="">:</td>
                    <td width="75%"><input name="bsr" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo $bsr;?>" size="15" maxlength="15">
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp;<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $id                 = trim($_POST['id']);
                    $jmlks              = validangka(trim($_POST['jmlks']));
                    $bsr                = validangka(trim($_POST['bsr']));
                    if (!empty($id)) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" kasift "," '$id','$jmlks','$bsr'", " Daftar Kasift " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=ListKS&action=TAMBAH&id='$id'>";
                                break;
                        }
                    }else if (empty($id)){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 63%; overflow: auto;">
            <?php
                $_sql = "SELECT kasift.id,kasift.jmlks,kasift.bsr,
                pegawai.nik,
                pegawai.nama,
                pegawai.jbtn,
                pegawai.jnj_jabatan,
                pegawai.departemen,
                pegawai.bidang from kasift inner join pegawai where pegawai.id=kasift.id ORDER BY pegawai.nik ASC ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='7%'><div align='center'>Proses</div></td>
                                <td width='5%'><div align='center'>KS</div></td>
                                <td width='11%'><div align='center'>Bsr.Tnj</div></td>
                                <td width='9%'><div align='center'>NIP</div></td>
                                 <td width='20%'><div align='center'>Nama</div></td>
                                 <td width='17%'><div align='center'>Jabatan</div></td>
                                 <td width='11%'><div align='center'>Kode Jenjang</div></td>
                                 <td width='11%'><div align='center'>Departemen</div></td>
                                 <td width='9%'><div align='center'>Bidang</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                      echo "<tr class='isi'>
                                <td>
                                    <center>";?>
                                    <a href="?act=ListKS&action=HAPUS&id=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[1]</td>
                                 <td>".formatDuit($baris[2])."</td>
                                 <td>$baris[3]</td>
                                 <td>$baris[4]</td>
                                 <td>$baris[5]</td>
                                 <td>$baris[6]</td>
                                 <td>$baris[7]</td>
                                 <td>$baris[8]</td>
                           </tr>";
                    }
                echo "</table>";

            } else {echo "Data kasift masih kosong !";}
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus(" kasift"," id ='".$id."' ","?act=ListKS&action=TAMBAH&id=$id");
            }

                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        
        ?>
    </div>

</div>
