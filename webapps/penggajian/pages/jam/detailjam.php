
<div id="post">
    <div class="entry">
    <div align="center" class="link">
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
        <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action            =isset($_GET['action'])?$_GET['action']:NULL;
                $no_id             =isset($_GET['no_id'])?$_GET['no_id']:NULL;
                echo "<input type=hidden name=no_id  value=$no_id><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="25%" >Departemen</td><td width="">:</td>
                    <td width="75%">
                        <select name="dep_id" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1" autofocus>
                            <!--<option id='TxtIsi12' value='null'>- Ruang -</option>-->
                            <?php
                                $_sql = "SELECT dep_id,nama FROM departemen ORDER BY dep_id";
                                $hasil=bukaquery($_sql);
                                while($baris = mysqli_fetch_array($hasil)) {
                                    echo "<option id='TxtIsi1' value='$baris[0]'>$baris[0] $baris[1]</option>";
                                }
                            ?>
                        </select>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Jam Shift</td><td width="">:</td>
                    <td width="75%">
                        <select name="shift" class="text2" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">                             
                            	<option id='TxtIsi2' value='Pagi'>Pagi</option>
                                <option id='TxtIsi2' value='Pagi2'>Pagi2</option>
                                <option id='TxtIsi2' value='Pagi3'>Pagi3</option>
                                <option id='TxtIsi2' value='Pagi4'>Pagi4</option>
                                <option id='TxtIsi2' value='Pagi5'>Pagi5</option>
                            	<option id='TxtIsi2' value='Pagi6'>Pagi6</option>
                                <option id='TxtIsi2' value='Pagi7'>Pagi7</option>
                                <option id='TxtIsi2' value='Pagi8'>Pagi8</option>
                                <option id='TxtIsi2' value='Pagi9'>Pagi9</option>
                                <option id='TxtIsi2' value='Pagi10'>Pagi10</option>
                                <option id='TxtIsi2' value='Siang'>Siang</option>
                                <option id='TxtIsi2' value='Siang2'>Siang2</option>
                                <option id='TxtIsi2' value='Siang3'>Siang3</option>
                                <option id='TxtIsi2' value='Siang4'>Siang4</option>
                                <option id='TxtIsi2' value='Siang5'>Siang5</option>
                                <option id='TxtIsi2' value='Siang6'>Siang6</option>
                                <option id='TxtIsi2' value='Siang7'>Siang7</option>
                                <option id='TxtIsi2' value='Siang8'>Siang8</option>
                                <option id='TxtIsi2' value='Siang9'>Siang9</option>
                                <option id='TxtIsi2' value='Siang10'>Siang10</option>
                                <option id='TxtIsi2' value='Malam'>Malam</option>
                                <option id='TxtIsi2' value='Malam2'>Malam2</option>
                                <option id='TxtIsi2' value='Malam3'>Malam3</option>
                                <option id='TxtIsi2' value='Malam4'>Malam4</option>
                                <option id='TxtIsi2' value='Malam5'>Malam5</option>
                                <option id='TxtIsi2' value='Malam6'>Malam6</option>
                                <option id='TxtIsi2' value='Malam7'>Malam7</option>
                                <option id='TxtIsi2' value='Malam8'>Malam8</option>
                                <option id='TxtIsi2' value='Malam9'>Malam9</option>
                                <option id='TxtIsi2' value='Malam10'>Malam10</option>
                                <option id='TxtIsi2' value='Midle Pagi1'>Midle Pagi1</option>
                                <option id='TxtIsi2' value='Midle Pagi2'>Midle Pagi2</option>
                                <option id='TxtIsi2' value='Midle Pagi3'>Midle Pagi3</option>
                                <option id='TxtIsi2' value='Midle Pagi4'>Midle Pagi4</option>
                                <option id='TxtIsi2' value='Midle Pagi5'>Midle Pagi5</option>
                                <option id='TxtIsi2' value='Midle Pagi6'>Midle Pagi6</option>
                                <option id='TxtIsi2' value='Midle Pagi7'>Midle Pagi7</option>
                                <option id='TxtIsi2' value='Midle Pagi8'>Midle Pagi8</option>
                                <option id='TxtIsi2' value='Midle Pagi9'>Midle Pagi9</option>
                                <option id='TxtIsi2' value='Midle Pagi10'>Midle Pagi10</option>
                                <option id='TxtIsi2' value='Midle Siang1'>Midle Siang1</option>
                                <option id='TxtIsi2' value='Midle Siang2'>Midle Siang2</option>
                                <option id='TxtIsi2' value='Midle Siang3'>Midle Siang3</option>
                                <option id='TxtIsi2' value='Midle Siang4'>Midle Siang4</option>
                                <option id='TxtIsi2' value='Midle Siang5'>Midle Siang5</option>
                                <option id='TxtIsi2' value='Midle Siang6'>Midle Siang6</option>
                                <option id='TxtIsi2' value='Midle Siang7'>Midle Siang7</option>
                                <option id='TxtIsi2' value='Midle Siang8'>Midle Siang8</option>
                                <option id='TxtIsi2' value='Midle Siang9'>Midle Siang9</option>
                                <option id='TxtIsi2' value='Midle Siang10'>Midle Siang10</option>
                                <option id='TxtIsi2' value='Midle Malam1'>Midle Malam1</option>
                                <option id='TxtIsi2' value='Midle Malam2'>Midle Malam2</option>
                                <option id='TxtIsi2' value='Midle Malam3'>Midle Malam3</option>
                                <option id='TxtIsi2' value='Midle Malam4'>Midle Malam4</option>
                                <option id='TxtIsi2' value='Midle Malam5'>Midle Malam5</option>
                                <option id='TxtIsi2' value='Midle Malam6'>Midle Malam6</option>
                                <option id='TxtIsi2' value='Midle Malam7'>Midle Malam7</option>
                                <option id='TxtIsi2' value='Midle Malam8'>Midle Malam8</option>
                                <option id='TxtIsi2' value='Midle Malam9'>Midle Malam9</option>
                                <option id='TxtIsi2' value='Midle Malam10'>Midle Malam10</option>
                        </select>
                        <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Jam Masuk</td><td width="">:</td>
                    <td width="75%">
                        <select name="jam_masuk" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                             <?php
                                loadJam();
                             ?>
                        </select>
			            <select name="menit_masuk" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                             <?php
                                loadMenit();
                             ?>
                        </select>
                        <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="25%" >Jam Pulang</td><td width="">:</td>
                    <td width="75%">
                        <select name="jam_pulang" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                             <?php
                                loadJam();
                             ?>
                        </select>
			            <select name="menit_pulang" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                             <?php
                                loadMenit();
                             ?>
                        </select>
                        <span id="MsgIsi4" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp;<input name=BtnKosong type=reset class="button" value="KOSONG"></div><br>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
                if (isset($BtnSimpan)) {
                    $no_id              =trim($_POST['no_id']);
                    $dep_id             =trim($_POST['dep_id']);
                    $shift              =trim($_POST['shift']);
                    $jam_masuk          =trim($_POST['jam_masuk']);
                    $menit_masuk        =trim($_POST['menit_masuk']);
                    $jam_pulang         =trim($_POST['jam_pulang']);
                    $menit_pulang       =trim($_POST['menit_pulang']);
                    
                    if (!empty($dep_id)) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" jam_jaga "," '0','$dep_id','$shift','$jam_masuk:$menit_masuk:00',
                                        '$jam_pulang:$menit_pulang:00'", " Jam Jaga " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=ListJam&action=TAMBAH'>";
                                break;
                        }
                    }else if (empty($dep_id)){
                        echo 'Semua field harus isi..!!!';
                    }
                }
            ?>
            <div style="width: 100%; height: 57%; overflow: auto;">
            <?php
                $_sql = "SELECT  jam_jaga.no_id,jam_jaga.dep_id,jam_jaga.shift,
                    jam_jaga.jam_masuk,jam_jaga.jam_pulang from jam_jaga
                    ORDER BY jam_jaga.dep_id ";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);

                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='10%'><div align='center'>Proses</div></td>
                                <td width='20%'><div align='center'>Departemen</div></td>
                                <td width='24%'><div align='center'>Shift</div></td>
                                <td width='23%'><div align='center'>Jam Datang</div></td>
                                <td width='23%'><div align='center'>Jam Pulang</div></td>
                            </tr>";
                    while($baris = mysqli_fetch_array($hasil)) {
                      echo "<tr class='isi'>
                                <td>
                                    <center>";?>
                                    <a href="?act=ListJam&action=HAPUS&no_id=<?php print $baris[0] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$baris[1]</td>
                                <td>$baris[2]</td>
                                <td>$baris[3]</td>
                                <td>$baris[4]</td>
                           </tr>";
                    }
                echo "</table>";

            } else {
                echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='10%'><div align='center'>Proses</div></td>
                                <td width='20%'><div align='center'>Departemen</div></td>
                                <td width='24%'><div align='center'>Shift</div></td>
                                <td width='23%'><div align='center'>Jam Datang</div></td>
                                <td width='23%'><div align='center'>Jam Pulang</div></td>
                            </tr>
                            </table>";
            }
        ?>
        </div>
        </form>
        <?php
            if ($action=="HAPUS") {
                Hapus("  jam_jaga "," no_id ='".$no_id."' ","?act=ListJam&action=TAMBAH");
            }

        if(mysqli_num_rows($hasil)!=0) {
                echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah</div></td>                        
                    </tr>     
                 </table>");
        }
        ?>
    </div>

</div>
