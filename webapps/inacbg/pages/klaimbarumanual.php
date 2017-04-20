<div id="post">
    <div class="entry">   
	<form name="frm_aturadmin" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
        <?php
                echo "";
                $tahunawal      =isset($_GET['tahunawal'])?$_GET['tahunawal']:NULL;
                $bulanawal      =isset($_GET['bulanawal'])?$_GET['bulanawal']:NULL;
                $tanggalawal    =isset($_GET['tanggalawal'])?$_GET['tanggalawal']:NULL;
                $tahunakhir     =isset($_GET['tahunakhir'])?$_GET['tahunakhir']:NULL;
                $bulanakhir     =isset($_GET['bulanakhir'])?$_GET['bulanakhir']:NULL;
                $tanggalakhir   =isset($_GET['tanggalakhir'])?$_GET['tanggalakhir']:NULL;  
                $action         =isset($_GET['action'])?$_GET['action']:NULL;
                $no_sep         =isset($_GET['no_sep'])?$_GET['no_sep']:NULL;
        ?>
    <div style="width: 100%; height: 90%; overflow: auto;">
    <?php
        $BtnCari=isset($_POST['BtnCari'])?$_POST['BtnCari']:NULL;
        if (isset($BtnCari)) {      
                $tahunawal      =trim($_POST['tahunawal']);
                $bulanawal      =trim($_POST['bulanawal']);
                $tanggalawal    =trim($_POST['tanggalawal']);
                $tahunakhir     =trim($_POST['tahunakhir']);
                $bulanakhir     =trim($_POST['bulanakhir']);
                $tanggalakhir   =trim($_POST['tanggalakhir']);
        }
        $_sql = "select bridging_sep.no_sep, bridging_sep.no_rawat,bridging_sep.nomr,bridging_sep.nama_pasien,
                bridging_sep.tglsep,bridging_sep.tglrujukan,bridging_sep.no_rujukan,bridging_sep.kdppkrujukan,
                bridging_sep.nmppkrujukan,bridging_sep.kdppkpelayanan,bridging_sep.nmppkpelayanan,
                if(bridging_sep.jnspelayanan='1','1. Rawat Inap','2. Rawat Jalan') as jenispelayanan,bridging_sep.catatan,bridging_sep.diagawal,
                bridging_sep.nmdiagnosaawal,bridging_sep.kdpolitujuan,bridging_sep.nmpolitujuan,
                if(bridging_sep.klsrawat='1','1. Kelas 1',if(bridging_sep.klsrawat='2','2. Kelas 2','3. Kelas 3')) as kelas,
                if(bridging_sep.lakalantas='1','1. Kasus Kecelakaan','2. Bukan Kasus Kecelakaan') as lakalantas,bridging_sep.lokasilaka,bridging_sep.user, 
                bridging_sep.tanggal_lahir,bridging_sep.peserta,bridging_sep.jkel,bridging_sep.no_kartu,bridging_sep.tglpulang  
                from bridging_sep where bridging_sep.tglsep between '".$tahunawal."-".$bulanawal."-".$tanggalawal."' and '".$tahunakhir."-".$bulanakhir."-".$tanggalakhir."' order by bridging_sep.tglsep";
        $hasil=bukaquery($_sql);
        $jumlah=mysql_num_rows($hasil);
        if(mysql_num_rows($hasil)!=0) {
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head2'>
                        <td width='10%'><div align='center'>No.Rawat</div></td>
                        <td width='10%'><div align='center'>No.SEP</div></td>
                        <td width='10%'><div align='center'>Tanggal SEP</div></td>
                        <td width='9%'><div align='center'>No.Kartu</div></td>
                        <td width='7%'><div align='center'>No.RM</div></td>
                        <td width='18%'><div align='center'>Nama Pasien</div></td>
                        <td width='7%'><div align='center'>Tgl.Lahir</div></td>
                        <td width='7%'><div align='center'>J.Kel</div></td>
                        <td width='9%'><div align='center'>Peserta</div></td>
                        <td width='13%'><div align='center'>Status Data</div></td>
                    </tr>";
                    while($baris = mysql_fetch_array($hasil)) {
                        $status="<a href='?act=KlaimBaruManual&action=Kirim&no_sep=".$baris["no_sep"]."&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir' >[Kirim]</a>";
                        if(getOne("select count(no_sep) from inacbg_klaim_baru where no_sep='".$baris["no_sep"]."'")>0){
                            $status="Terkirim INACBG";
                        }
                        echo "<tr class='isi' title='".$baris["no_rawat"].", ".$baris["no_sep"].", ".$baris["tglsep"].", ".$baris["no_kartu"].", ".$baris["nomr"].", ".$baris["nama_pasien"]."'>
                                <td valign='top'>".$baris["no_rawat"]."</td>
                                <td valign='top'>".$baris["no_sep"]."</td>
                                <td valign='top' align='center'>".$baris["tglsep"]."</td>
                                <td valign='top'>".$baris["no_kartu"]."</td>
                                <td valign='top'>".$baris["nomr"]."</td>
                                <td valign='top'>".$baris["nama_pasien"]."</td>
                                <td valign='top' align='center'>".$baris["tanggal_lahir"]."</td>
                                <td valign='top' align='center'>".$baris["jkel"]."</td>
                                <td valign='top' align='center'>".$baris["peserta"]."</td>
                                <td valign='top' align='center'>".$status."</td>
                             </tr>";
                    }
            echo "</table>";           
        }else{
            echo "<table width='100%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head2'>
                        <td width='10%'><div align='center'>No.Rawat</div></td>
                        <td width='10%'><div align='center'>No.SEP</div></td>
                        <td width='10%'><div align='center'>Tanggal SEP</div></td>
                        <td width='9%'><div align='center'>No.Kartu</div></td>
                        <td width='7%'><div align='center'>No.RM</div></td>
                        <td width='18%'><div align='center'>Nama Pasien</div></td>
                        <td width='7%'><div align='center'>Tgl.Lahir</div></td>
                        <td width='7%'><div align='center'>J.Kel</div></td>
                        <td width='9%'><div align='center'>Peserta</div></td>
                        <td width='13%'><div align='center'>Status Data</div></td>
                    </tr>
                   </table>";
        }         
        
        
        if($action=="Kirim") {
            $_sql   = "select no_kartu,no_sep,nomr,nama_pasien,tanggal_lahir,jkel from bridging_sep where no_sep='".$no_sep."'";
            $hasil  = bukaquery($_sql);
            $baris  = mysql_fetch_array($hasil);
            $gender = "";
            if($baris["jkel"]=="LAKI-LAKI"){
                $gender="1";
            }else{
                $gender="2";
            }
            BuatKlaimBaru($baris["no_kartu"],$baris["no_sep"],$baris["nomr"],$baris["nama_pasien"],$baris["tanggal_lahir"]." 00:00:00", $gender);            
            echo "<meta http-equiv='refresh' content='1;URL=?act=KlaimBaruManual&tahunawal=$tahunawal&bulanawal=$bulanawal&tanggalawal=$tanggalawal&tahunakhir=$tahunakhir&bulanakhir=$bulanakhir&tanggalakhir=$tanggalakhir'>";
        }
        
        $BtnKeluar=isset($_POST['BtnKeluar'])?$_POST['BtnKeluar']:NULL;
        if (isset($BtnKeluar)) {
            echo"<meta http-equiv='refresh' content='1;URL=?act=KlaimBaruManual&action=Keluar'>";
        }

    ?>
    </div>
            <table width="100%" align="center" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr class="head3">					
                    <td width="490px">
                        Periode : 
                        <select name="tanggalawal" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" id="TxtIsi3">
                             <?php
                                if(!$tanggalawal==""){
                                    echo "<option id='TxtIsi3' value=$tanggalawal>$tanggalawal</option>";
                                }                                    
                                loadTglnow();
                             ?>
                        </select>                        
                        <select name="bulanawal" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" id="TxtIsi2">
                             <?php
                                if(!$bulanawal==""){
                                    echo "<option id='TxtIsi2' value=$bulanawal>$bulanawal</option>";
                                }                                    
                                loadBlnnow();
                             ?>
                        </select>                        
                        <select name="tahunawal" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" id="TxtIsi1">
                             <?php
                                if(!$tahunawal==""){
                                    echo "<option id='TxtIsi1' value=$tahunawal>$tahunawal</option>";
                                }                                    
                                loadThnnow();
                             ?>
                        </select>
                        &nbsp;&nbsp;s.d.&nbsp;&nbsp;                        
                        <select name="tanggalakhir" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi6'));" id="TxtIsi6">
                             <?php
                                if(!$tanggalakhir==""){
                                    echo "<option id='TxtIsi6' value=$tanggalakhir>$tanggalakhir</option>";
                                }                                    
                                loadTglnow();
                             ?>
                        </select>                        
                        <select name="bulanakhir" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi5'));" id="TxtIsi5">
                             <?php
                                if(!$bulanakhir==""){
                                    echo "<option id='TxtIsi5' value=$bulanakhir>$bulanakhir</option>";
                                }                                    
                                loadBlnnow();
                             ?>
                        </select>
                        <select name="tahunakhir" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi4'));" id="TxtIsi4">
                             <?php
                                if(!$tahunakhir==""){
                                    echo "<option id='TxtIsi4' value=$tahunakhir>$tahunakhir</option>";
                                }                                    
                                loadThnnow();
                             ?>
                        </select>
                        <input name=BtnCari type=submit class="button" value="&nbsp;&nbsp;Cari&nbsp;&nbsp;" />
                    </td>
                    <td width="140px" >Record : <?php echo $jumlah; ?> </td>
                    <td><input name=BtnKeluar type=submit class="button" value="&nbsp;&nbsp;&nbsp;Keluar&nbsp;&nbsp;&nbsp;" /> </td>
                </tr>
            </table>
	</form>
    </div>
</div>

