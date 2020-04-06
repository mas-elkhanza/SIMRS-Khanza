

<?php
   $_sql         = "SELECT * FROM set_tahun";
   $hasil        = bukaquery($_sql);
   $baris        = mysqli_fetch_row($hasil);
   $tahun         = $baris[0];
   $bulan          = $baris[1];
?>

<div id="post">
    <div align="center" class="link">
        <a href=?act=ListWarung>| List Warung |</a>
        <a href=?act=HomeAdmin>| Menu Utama |</a>
    </div>  
    <div class="entry">
        <form name="frm_pelatihan" onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
            <?php
                echo "";
                $action      =isset($_GET['action'])?$_GET['action']:NULL;
                $pendapatan_warung     =str_replace("_"," ",isset($_GET['pendapatan_warung']))?str_replace("_"," ",$_GET['pendapatan_warung']):NULL;
                if($action == "TAMBAH"){
                    $pendapatan_warung = str_replace("_"," ",isset($_GET['pendapatan_warung']))?str_replace("_"," ",$_GET['pendapatan_warung']):NULL;
                    $persen_rs       = "";
		    $bagian_rs       ="";
                    $persen_kry      = "";
		    $bagian_kry      ="";
                }else if($action == "UBAH"){
                    $_sql         = "SELECT pendapatan_warung,persen_rs,
                                    bagian_rs,persen_kry,bagian_kry
                                    FROM set_warung WHERE tahun='$tahun' and bulan='$bulan'";
                    $hasil        = bukaquery($_sql);
                    $baris        = mysqli_fetch_row($hasil);
                    $pendapatan_warung = $baris[0];
                    $persen_rs       = $baris[1];
		    $bagian_rs       = $baris[2];
                    $persen_kry      = $baris[3];
		    $bagian_kry      = $baris[4];
                }
                echo"<input type=hidden name=pendapatan_warung value=$pendapatan_warung><input type=hidden name=action value=$action>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >Pendapatan Warung</td><td width="">:</td>
                    <td width="67%">Rp.<input name="pendapatan_warung" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi1'));" type=text id="TxtIsi1" class="inputbox" value="<?php echo $pendapatan_warung;?>" size="30" maxlength="15">
                    <span id="MsgIsi1" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Prosentase RS</td><td width="">:</td>
                    <td width="67%"><input name="persen_rs" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi2'));" type=text id="TxtIsi2" class="inputbox" value="<?php echo $persen_rs;?>" size="10" maxlength="6" />%
                    <span id="MsgIsi2" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
                <tr class="head">
                    <td width="31%" >Prosentase Kry</td><td width="">:</td>
                    <td width="67%"><input name="persen_kry" class="text" onkeydown="setDefault(this, document.getElementById('MsgIsi3'));" type=text id="TxtIsi3" class="inputbox" value="<?php echo $persen_kry;?>" size="10" maxlength="6" />%
                    <span id="MsgIsi3" style="color:#CC0000; font-size:10px;"></span>
                    </td>
                </tr>
            </table>
            <div align="center"><input name=BtnSimpan type=submit class="button" value="SIMPAN">&nbsp<input name=BtnKosong type=reset class="button" value="KOSONG"></div>
            <?php
                $BtnSimpan=isset($_POST['BtnSimpan'])?$_POST['BtnSimpan']:NULL;
			
		$_sql         = "SELECT * FROM set_tahun";
		$hasil        = bukaquery($_sql);
		$baris        = mysqli_fetch_row($hasil);
		$tahun        = $baris[0];
		$bulan        = $baris[1];

                if (isset($BtnSimpan)) {
                    $pendapatan_warung  = validangka(trim($_POST['pendapatan_warung']));
                    $persen_rs          = validangka(trim($_POST['persen_rs']));
		    $bagian_rs          =($persen_rs/100)*$pendapatan_warung;
                    $persen_kry         = validangka(trim($_POST['persen_kry']));
		    $bagian_kry         =($persen_kry/100)*$pendapatan_warung;
                    if ((!empty($pendapatan_warung))&&(!empty($persen_rs))&&(!empty($persen_kry))) {
                        switch($action) {
                            case "TAMBAH":
                                Tambah(" set_warung ","'$tahun','$bulan','$pendapatan_warung','$persen_rs','$bagian_rs','$persen_kry','$bagian_kry' ", " Pendapatan Warung" );
                                echo"<html><head><title></title><meta http-equiv='refresh' content='1;URL=?act=InputWarung&action=TAMBAH'></head><body></body></html>";
                                break;
                            case "UBAH":
                                Ubah(" set_warung ","pendapatan_warung='$pendapatan_warung',persen_rs='$persen_rs',bagian_rs='$bagian_rs',persen_kry='$persen_kry',
                                                bagian_kry='$bagian_kry' WHERE tahun='$tahun' and bulan='$bulan'  ", " Pendapatan Warung");
                                echo"<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=ListWarung'></head><body></body></html>";
                                break;
                        }
                    }else if ((empty($pendapatan_warung))||(empty($persen_rs))||(empty($persen_kry))){
                        echo 'Semua field harus isi..!!';
                    }
                }
            ?>
        </form>
    </div>
</div>
