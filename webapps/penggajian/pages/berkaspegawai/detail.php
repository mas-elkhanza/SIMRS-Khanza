<div class="entry">        
    <form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
            echo "";
            $action       = isset($_GET['action'])?$_GET['action']:NULL;
            $nik          = str_replace("_"," ",isset($_GET['nik']))?str_replace("_"," ",$_GET['nik']):NULL;
            $keyword      = str_replace("_"," ",isset($_GET['keyword']))?str_replace("_"," ",$_GET['keyword']):NULL;
            $kategori     = str_replace("_"," ",isset($_GET['kategori']))?str_replace("_"," ",$_GET['kategori']):NULL;
            $_sql         = "select nik,nama,jk,jbtn,jnj_jabatan,departemen,bidang,stts_kerja,
                            pendidikan from pegawai where nik='$nik' ";
            $hasil        = bukaquery($_sql);
            $baris        = mysqli_fetch_array($hasil);
            $nama         = $baris["nama"];
            $jk           = $baris["jk"];
            $jbtn         = $baris["jbtn"];
            $jnj_jabatan  = getOne("select nama from jnj_jabatan where kode='".$baris["jnj_jabatan"]."'");
            $departemen   = getOne("select nama from departemen where dep_id='".$baris["departemen"]."'");
            $bidang       = $baris["bidang"];
            $pendidikan   = $baris["pendidikan"];

            echo "<input type=hidden name=nik value='$nik'>
                  <input type=hidden name=kategori value='$kategori'>
                  <input type=hidden name=action value=$action>";
        ?>
        <table width="100%" align="center" border="0">
            <tr class="isi2">
                <td width="15%">Jenis Kelamin</td><td width="1%">:</td>
                <td width="34%"><?php echo $jk;?></td>
                <td width="15%">Jabatan</td><td width="1%">:</td>
                <td width="34%"><?php echo $jbtn;?></td>
            </tr>
            <tr class="isi2">
                <td width="15%">Jenjang Jabatan</td><td width="1%">:</td>
                <td width="34%"><?php echo $jnj_jabatan;?></td>
                <td width="15%">Departemen</td><td width="1%">:</td>
                <td width="34%"><?php echo $departemen;?></td>
            </tr>  
            <tr class="isi2">
                <td width="15%">Bidang</td><td width="1%">:</td>
                <td width="34%"><?php echo $bidang;?></td>
                <td width="15%">Pendidikan</td><td width="1%">:</td>
                <td width="34%"><?php echo $pendidikan;?></td>
            </tr>         
            <tr class="isi2">
                <td width="15%">Berkas Pegawai</td><td width="1%">:</td>
                <td width="34%">
                    <select name="kode" class="text4">
                        <?php
                            $_sql = "SELECT kode,nama_berkas FROM master_berkas_pegawai where kategori='$kategori' ORDER BY no_urut";
                            $hasil=bukaquery($_sql);

                            while($baris = mysqli_fetch_array($hasil)) {
                                echo "<option value='$baris[0]'>$baris[1]</option>";
                            }
                        ?>
                    </select>
                    <input name="dokumen" class="text3" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=file id="TxtIsi1" value="<?php echo $dokumen;?>" size="30" maxlength="255" />
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                </td>
                <td width="15%">Tgl.Uploud</td><td width="1%">:</td>
                <td width="34%">
                    <select name="TglUploud" class="text">
                         <?php
                           loadTglnow();
                         ?>
                    </select>
                    <select name="BlnUploud" class="text">
                         <?php
                            loadBlnnow();
                         ?>
                    </select>
                    <select name="ThnUploud" class="text">
                         <?php
                            loadThnnow();
                         ?>
                    </select>
                </td>
            </tr>   
        </table>
        <div align="center"><input name=BtnSimpan type=submit class="button" value="&nbsp;&nbsp;Simpan&nbsp;&nbsp;">&nbsp<input name=BtnKosong type=reset class="button" value="&nbsp;&nbsp;Kosong&nbsp;&nbsp;"></div><br>
        <?php
            $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
            if (isset($BtnSimpan)) {
                $nik           = trim($_POST['nik']);
                $kategori      = trim($_POST['kategori']);
                $kode          = trim($_POST['kode']);
                $tgl_uploud    = trim($_POST['ThnUploud'])."-".trim($_POST['BlnUploud'])."-".trim($_POST['TglUploud']);
                $dokumen       = str_replace(" ","_","pages/berkaspegawai/berkas/".$_FILES['dokumen']['name']);
                if ((!empty($nik))&&(!empty($kode))&&(!empty($dokumen))) {
                    switch($action) {
                        case "TAMBAH":
                            if((strtolower(substr($dokumen,-3))=="jpg")||(strtolower(substr($dokumen,-3))=="jpeg")){
                                move_uploaded_file($_FILES['dokumen']['tmp_name'],$dokumen);
                                Tambah(" berkas_pegawai "," '$nik','$tgl_uploud','$kode','$dokumen'", " Berkas Pegawai " );
                                echo"<meta http-equiv='refresh' content='1;URL=?act=DetailBerkasPegawai&action=TAMBAH&nik=".str_replace(" ","_",$nik)."&kategori=".str_replace(" ","_",$kategori)."'>";
                            }else{
                                echo "Berkas harus JPEG/JPG";
                            } 
                            break;
                    }
                }else if ((empty($nik))||(empty($kode))||(empty($dokumen))){
                    echo 'Semua field harus isi..!!!';
                }
            }
        ?>
        <?php
            $_sql = "SELECT berkas_pegawai.nik, berkas_pegawai.tgl_uploud,berkas_pegawai.kode_berkas,
                    master_berkas_pegawai.nama_berkas,berkas_pegawai.berkas from berkas_pegawai inner join 
                    master_berkas_pegawai on berkas_pegawai.kode_berkas=master_berkas_pegawai.kode 
                    where berkas_pegawai.nik='$nik' and master_berkas_pegawai.kategori='$kategori' 
                    and master_berkas_pegawai.nama_berkas like '%$keyword%' order by master_berkas_pegawai.no_urut";
            $hasil=bukaquery($_sql);
            $jumlah=mysqli_num_rows($hasil);

            if(mysqli_num_rows($hasil)!=0) {
                echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head'>
                            <td width='4%'><div align='center'>Proses</div></td>
                            <td width='2%'><div align='center'>No.</div></td>
                            <td width='6%'><div align='center'>Tgl.Uploud</div></td>
                            <td width='88%'><div align='center'>Nama Berkas</div></td>
                        </tr>";
                $i=1;
                while($baris = mysqli_fetch_array($hasil)) { 
                    $gb="-";
                    if($baris["berkas"]=="pages/berkaspegawai/berkas"){
                        $gb="-";                            
                    }else{
                        $gb="<img src='".$baris["berkas"]."' width='850px' height='950px'>";
                    }
                    echo "<tr class='isi'>
                            <td valign='top'>
                                <center>
                                <a href='?act=DetailBerkasPegawai&action=HAPUS&nik=".str_replace(" ","_",$baris["nik"])."&kode=".str_replace(" ","_",$baris["kode_berkas"])."&kategori=".str_replace(" ","_",$kategori)."&berkas=".$baris["berkas"]."'>[hapus]</a>
                               </center>
                            </td>
                            <td valign='top'>".$i."</td>
                            <td valign='top'>".$baris["tgl_uploud"]."</td>
                            <td valign='top'>".$baris["nama_berkas"]."</td>
                       </tr>
                       <tr class='isi'>
                            <td valign='top'></td>
                            <td valign='top'></td>
                            <td valign='top' align='center' colspan='2'><a target=_blank href=../penggajian/".$baris["berkas"].">".$gb."</a></td>
                       </tr>";
                    $i++;
                }
            echo "</table>";

        } else {echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                        <tr class='head'>
                            <td width='4%'><div align='center'>Proses</div></td>
                            <td width='2%'><div align='center'>No.</div></td>
                            <td width='6%'><div align='center'>Tgl.Uploud</div></td>
                            <td width='88%'><div align='center'>Nama Berkas</div></td>
                        </tr>
                      </table>";}
    ?>
    <?php
        if ($action=="HAPUS") {      
            unlink($_GET['berkas']);
            Hapus(" berkas_pegawai "," nik ='".$_GET['nik']."' and kode_berkas ='".$_GET['kode']."' ","?act=DetailBerkasPegawai&action=TAMBAH&nik=".str_replace(" ","_",$nik)."&kategori=".str_replace(" ","_",$kategori));
        }

    ?>       
    </form>
</div>


