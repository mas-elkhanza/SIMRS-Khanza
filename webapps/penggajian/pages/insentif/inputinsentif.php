<?php
    if(strpos($_SERVER['REQUEST_URI'],"pages")){
        exit(header("Location:../index.php"));
    }
    
    $_sql         = "SELECT * FROM set_tahun";
    $hasil        = bukaquery($_sql);
    $baris        = mysqli_fetch_row($hasil);
    $tahun        = empty($baris[0])?date("Y"):$baris[0];
    $bulan        = empty($baris[1])?date("m"):$baris[1];
?>

<div id="post">
    <div align="center" class="link">
        <a href=?act=ListInsentif>| List Insentif |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      = isset($_GET['action'])?$_GET['action']:NULL;
                $pendapatan  = validangka(str_replace("_"," ",isset($_GET['pendapatan']))?str_replace("_"," ",$_GET['pendapatan']):NULL);
                if($action == "TAMBAH"){
                    $pendapatan      = validangka(str_replace("_"," ",isset($_GET['pendapatan']))?str_replace("_"," ",$_GET['pendapatan']):NULL);
                    $persen          = "";
		    $total_insentif  = "";
                }else if($action == "UBAH"){
                    $_sql            = "SELECT set_insentif.pendapatan,set_insentif.persen FROM set_insentif WHERE set_insentif.tahun='$tahun' and set_insentif.bulan='$bulan'";
                    $hasil           = bukaquery($_sql);
                    $baris           = mysqli_fetch_row($hasil);
                    @$pendapatan     = $baris[0];
                    @$persen         = $baris[1];
		    @$total_insentif = $baris[2];
                }
                echo"<input type=hidden name=pendapatan value=$pendapatan><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Pendapatan</td><td width="">:</td>
                    <td width="67%">Rp.<input name="pendapatan" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $pendapatan;?>" size="30" maxlength="15" pattern="[0-9-]{1,15}" title=" 0-9- (Maksimal 15 karakter)" autocomplete="off" autofocus>
                        <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Prosentase Insentif</td><td width="">:</td>
                    <td width="67%"><input name="persen" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $persen;?>" size="10" maxlength="6" pattern="[0-9-]{1,6}" title=" 0-9- (Maksimal 6 karakter)" autocomplete="off"/>%
                        <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;	
                if (isset($BtnSimpan)) {
                    $pendapatan = trim($_POST['pendapatan']);
                    $pendapatan = validangka($pendapatan);
                    $persen     = trim($_POST['persen']);
                    $persen     = validangka($persen);
                    $total_insentif=($persen/100)*$pendapatan;
                    if ((isset($pendapatan))&&(isset($persen))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" set_insentif ","'$tahun','$bulan','$pendapatan','$persen','$total_insentif' ", " Pendapatan " );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputInsentif&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" set_insentif ","pendapatan='$pendapatan',persen='$persen',total_insentif='$total_insentif' WHERE tahun='$tahun' and bulan='$bulan'  ", " Pendapatan ");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListInsentif'></head><body></body></html>";
                                break;
                        }
                    }else{
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>

