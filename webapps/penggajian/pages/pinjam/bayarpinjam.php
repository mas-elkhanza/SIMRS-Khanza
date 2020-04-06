

<div id="post">
    <div class="entry">
            <?php
                echo "";
                $action     =$_GET['action'];
                $tanggal    =$_GET['tanggal'];
                $id         =$_GET['id'];

                $_sql2  	= "SELECT id, banyak_angsur, pinjaman, pokok,jasa, tanggal, status  FROM peminjaman_koperasi where id='$id' and tanggal='$tanggal'";
                $hasil2 	=bukaquery($_sql2);
                $baris2 	= mysqli_fetch_row($hasil2);
                $id         = $baris2[0];
                $banyak_angsur = $baris2[1];
                $pinjaman   = $baris2[2];
                $pokok      = $baris2[3];
                $jasa       = $baris2[4];
                $setoran    = $baris2[4]+$baris2[3];
                $tanggal 	= $baris2[5];
                $status     = $baris2[6];                
                
		        $_sql  = "SELECT nik,nama FROM pegawai where id='$id'";
                $hasil = bukaquery($_sql);
                $baris = mysqli_fetch_row($hasil);
                //mencari jumlah angsuran dan angsuran yang sudah dibayarkan
                $_sqlj = "select count(id),sum(pokok)
                        from angsuran_koperasi where id='$id' and tanggal_pinjam='$tanggal'  group by id";
                $hasilj=bukaquery($_sqlj);
                $barisj = mysqli_fetch_array($hasilj);
                $jml_sdh_angsur =$barisj[0];
                $sdh_setor      =$barisj[1];
                $sisa_pinjam    =$pinjaman-($jml_sdh_angsur*$pokok);

                echo "<input type=hidden name=id value=$id>
                      <input type=hidden name=setoran value=$setoran>
                      <input type=hidden name=pinjaman value=$pinjaman>
                      <input type=hidden name=action value=$action>";

                echo "<div align='center' class='link'>                          
                      <a href=?act=DetailPinjam&action=TAMBAH&id=$id>| List History Pinjam |</a>
                      <a href=?act=HomeAdmin>| Menu Utama |</a>
                      </div>";
            ?>
            <table width="100%" align="center">
                <tr class="head">
                    <td width="31%" >NIP</td><td width="">:</td>
                    <td width="67%">
                     <?php echo $baris[0];?>
                    </td>
                </tr>
		<tr class="head">
                    <td width="31%">Nama</td><td width="">:</td>
                    <td width="67%"><?php echo $baris[1];?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Tanggal Pinjam</td><td width="">:</td>
                    <td width="67%"><?php echo $tanggal;?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Jml.Angsuran</td><td width="">:</td>
                    <td width="67%"><?php echo $banyak_angsur;?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Jml.Pinjaman</td><td width="">:</td>
                    <td width="67%"><?php echo formatDuit($pinjaman);?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Pokok</td><td width="">:</td>
                    <td width="67%"><?php echo formatDuit($pokok);?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Jasa</td><td width="">:</td>
                    <td width="67%"><?php echo formatDuit($jasa);?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Setoran</td><td width="">:</td>
                    <td width="67%"><?php echo formatDuit($setoran);?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Status Pinjaman</td><td width="">:</td>
                    <td width="67%"><?php echo $status;?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Jml.Sdh Diangsur</td><td width="">:</td>
                    <td width="67%"><?php echo formatDec($jml_sdh_angsur);?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Ttl.Sdh Diangsur</td><td width="">:</td>
                    <td width="67%"><?php echo formatDuit($sdh_setor);?></td>
                </tr>
                <tr class="head">
                    <td width="31%" >Sisa Pinjaman</td><td width="">:</td>
                    <td width="67%"><?php echo formatDuit($sisa_pinjam);?></td>
                </tr>
            </table>     
            </div>      
        <form onsubmit="return validasiIsi();" method="post" action="" enctype=multipart/form-data>
                <?php 
                  echo "<input type=hidden name=id value=$id>
                      <input type=hidden name=tanggal value=$tanggal>
                      <input type=hidden name=pokok value=$pokok>
                      <input type=hidden name=jasa value=$jasa>";
                ?>
	    <table width="100%" align="center">		
                <tr class="head">
                    <td width="31%" >No.Angsuran Terakhir</td><td width="">:</td>
                    <td width="67%"><input name="banyak_angsur" class="text" type=text  size="10" maxlength="4" />
                    <input name=BtnGenerate type=submit class="button" value="Generate">
                    </td>
                </tr>
            </table> 
	</form>
        <?php
             $BtnGenerate=isset($_POST['BtnGenerate'])?$_POST['BtnGenerate']:NULL;
             if (isset($BtnGenerate)) {
                $id                 = trim($_POST['id']);
                $tanggal            = trim($_POST['tanggal']);
                $pokok              = validangka(trim($_POST['pokok']));
                $jasa               = validangka(trim($_POST['jasa']));
                $banyak_angsur      = validangka(trim($_POST['banyak_angsur']));

                $_sql         = "SELECT * FROM set_tahun";
                $hasil        = bukaquery($_sql);
                $baris        = mysqli_fetch_row($hasil);
                $tahun        = $baris[0];
                $bulan        = $baris[1];
                if(strlen($bulan)==1){
                    $bulan="0".($bulan+1);
                }

                for($interval=1; $interval<=$banyak_angsur; $interval++){
                    InsertData(" angsuran_koperasi ","'$id','$tanggal',DATE_SUB('$tahun-$bulan-05',INTERVAL $interval MONTH),'$pokok','$jasa'");
                }

                    echo "<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=BayarPinjam&action=TAMBAH&id=$id&tanggal=$tanggal'></head><body></body></html>";
             }
        ?>
		
        <div style="width: 100%; height: 29%; overflow: auto;">  
            <?php
                $_sql = "select id, tanggal_pinjam, tanggal_angsur, pokok, jasa
                        from angsuran_koperasi where id='$id' and tanggal_pinjam='$tanggal'";
                $hasil=bukaquery($_sql);
                $jumlah=mysqli_num_rows($hasil);
                if(mysqli_num_rows($hasil)!=0) {
                    echo "<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                            <tr class='head'>
                                <td width='10%'><div align='center'>Proses</div></td>
                                <td width='5%'><div align='center'>No.</div></td>
                                <td width='15%'><div align='center'>Tgl.Angsur</div></td>
                                <td width='23%'><div align='center'>Pokok</div></td>
                                <td width='23%'><div align='center'>Jasa</div></td>
                                <td width='24%'><div align='center'>Angsuran</div></td>
                            </tr>";
                    $i=1;
                    while($baris = mysqli_fetch_array($hasil)) {
                      echo "<tr class='isi' title='$baris[2], $baris[3], $baris[4]'>
                                <td>
                                    <center>";?>
                                    <a href="?act=BayarPinjam&action=HAPUS&pinjaman=<?php print $pinjaman ?>&id=<?php print $baris[0] ?>&tanggal_pinjam=<?php print $baris[1] ?>&tanggal_angsur=<?php print $baris[2] ?>" >[hapus]</a>
                            <?php
                            echo "</center>
                                </td>
                                <td>$i</td>
                                <td>$baris[2]</td>
                                <td>".formatDuit($baris[3])."</td>
                                <td>".formatDuit($baris[4])."</td>
                                <td>".formatDuit($baris[3]+$baris[4])."</td>
                           </tr>";$i++;
                    }
                echo "</table>";

            } else {echo "Data Bayar Peminjaman masih kosong !";}
        ?>
        </div>
        <?php
            if ($action=="HAPUS") {
                HapusAll(" angsuran_koperasi where id ='".$_GET["id"]."' and tanggal_angsur='".$_GET["tanggal_angsur"]."' ");	
                EditData(" potongan "," angkop='0' WHERE id='".$_GET["id"]."' and tahun=year('".$_GET["tanggal_angsur"]."') and bulan=(MONTH('".$_GET["tanggal_angsur"]."')-1) ");	
                if($_GET["pinjaman"]<=getOne("select sum(pokok) from angsuran_koperasi where id ='".$_GET["id"]."' and tanggal_pinjam='".$_GET["tanggal_pinjam"]."'  group by id")){
                    Ubah(" peminjaman_koperasi "," status='Lunas' WHERE id ='".$_GET["id"]."' and tanggal='".$_GET["tanggal_pinjam"]."' ", " Potongan ");
                }else{
                    Ubah(" peminjaman_koperasi "," status='Belum Lunas' WHERE id ='".$_GET["id"]."' and tanggal='".$_GET["tanggal_pinjam"]."' ", " Potongan ");
                }
                echo "<html><head><title></title><meta http-equiv='refresh' content='2;URL=?act=BayarPinjam&action=TAMBAH&id=".$_GET["id"]."&tanggal=".$_GET["tanggal_pinjam"]."'></head><body></body></html>";
            }

        if(mysqli_num_rows($hasil)!=0) {
            echo("<table width='99.6%' border='0' align='center' cellpadding='0' cellspacing='0' class='tbl_form'>
                    <tr class='head'>
                        <td><div align='left'>Data : $jumlah <a target=_blank href=../penggajian/pages/pinjam/LaporanBayarPinjam.php?id=$id&tanggal=$tanggal>| Laporan |</a> </div></td>                        
                    </tr>     
                 </table>");
        }
        ?>
   

</div>
