<?php
require_once("../../conf/conf.php");
header("Expires: Mon, 26 Jul 1997 05:00:00 GMT");
header("Last-Modified: " . gmdate("D, d M Y H:i:s") . " GMT");
header("Cache-Control: no-store, no-cache, must-revalidate");
header("Cache-Control: post-check=0, pre-check=0", false);
header("Pragma: no-cache"); // HTTP/1.0
?>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="../css/default.css" rel="stylesheet" type="text/css" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.10.1/html2pdf.bundle.min.js"></script>
</head>

<body bgcolor='#ffffff'>
    <?php
    reportsqlinjection();
    $no_rawat = isset($_GET['norawat']) ? $_GET['norawat'] : NULL;
    $kodecarabayar = getOne("select kd_pj from reg_periksa where no_rawat='$no_rawat'");
    $carabayar = getOne("select png_jawab from penjab where kd_pj='$kodecarabayar'");
    $statusrawat = getOne("select status_lanjut from reg_periksa where no_rawat='$no_rawat'");
    if ($kodecarabayar == 'BPJ') {
        if($statusrawat=='Ralan'){
        $nomorsep = getOne("select bridging_sep.no_sep from bridging_sep where bridging_sep.no_rawat='$no_rawat' and bridging_sep.jnspelayanan='2' ");
        $doktersep = getOne("select bridging_sep.nmdpdjp from bridging_sep where bridging_sep.no_rawat='$no_rawat' and bridging_sep.jnspelayanan='2' ");
        $namabilling = "BILLING RAWAT JALAN";
        } else {
        $nomorsep = getOne("select bridging_sep.no_sep from bridging_sep where bridging_sep.no_rawat='$no_rawat' and bridging_sep.jnspelayanan='1' ");
        $doktersep = getOne("select bridging_sep.nmdpdjp from bridging_sep where bridging_sep.no_rawat='$no_rawat' and bridging_sep.jnspelayanan='1' ");
        $namabilling = "BILLING RAWAT INAP";
        }
    } else {
        $nomorsep = "";
        $doktersep = "";
    }
    $_sql = "select no,nm_perawatan,pemisah,if(biaya=0,'',biaya),if(jumlah=0,'',jumlah),if(tambahan=0,'',tambahan),if(totalbiaya=0,'',totalbiaya),totalbiaya,status from billing where no_rawat='$no_rawat' ";
    $hasil = bukaquery($_sql);

    if (mysqli_num_rows($hasil) != 0) {
        $setting =  mysqli_fetch_array(bukaquery("select nama_instansi,alamat_instansi,kabupaten,propinsi,kontak,email,logo from setting"));
        echo "   
            <table width='" . getOne("select nota1ranap from set_nota") . "' bgcolor='#ffffff' align='left' border='0' padding='0' cellspacing='0' cellpadding='0'>
            <tr class='isi12' padding='0'>
            <td colspan='7' padding='0'>
                   <table width='100%' bgcolor='#ffffff' align='left' border='0' cellspacing='0' cellpadding='0'>
                        <tr>
                            <td  width='20%'>
				<img width='45' height='45' src='data:image/jpeg;base64," . base64_encode($setting['logo']) . "'/>
			    </td>
			    <td>
				<center><strong>
                <font color='000000' size='3'  face='Tahoma'>" . $setting["nama_instansi"] . "</font></strong><br>
				    <font color='000000' size='1'  face='Tahoma'>
					" . $setting["alamat_instansi"] . ", " . $setting["kabupaten"] . ", " . $setting["propinsi"] . "<br/>
					" . $setting["kontak"] . ", E-mail : " . $setting["email"] . "
                                    </font> 
                                    
                                    <br>".$namabilling."
				</center>
			    </td>
                <td  width='20%'><strong><font color='000000' size='2'  face='Tahoma' align='right'>$carabayar
                <br>$nomorsep<br>$doktersep</font></strong></td>
                        </tr>
                  </table>
            </td>
            </tr>
            <tr>
              <td colspan='7' padding='0'>
               <hr/>
              </td>
            </tr>";
        $total = 0;
        while ($inapdrpasien = mysqli_fetch_array($hasil)) {
            $total = $total + $inapdrpasien[7];
            if ($inapdrpasien[8] != "TtlObat") {
                echo    "<tr class='isi12' padding='0'>
                    <td padding='0' width='18%'><font color='111111' size='1'  face='Tahoma'>" . $inapdrpasien[0] . "</td> 
                    <td padding='0' width='40%'><font color='111111' size='1'  face='Tahoma'>" . $inapdrpasien[1] . "</td> 
                    <td padding='0' width='2%'><font color='111111' size='1'  face='Tahoma'>" . $inapdrpasien[2] . "</td> 
                    <td padding='0' width='10%' align='left'><font color='111111' size='1'  face='Tahoma'>" . formatDuit2($inapdrpasien[3]) . "</td> 
                    <td padding='0' width='5%' align='left'><font color='111111' size='1'  face='Tahoma'>" . formatDuit2($inapdrpasien[4]) . "</td> 
                    <td padding='0' width='10%' align='left'><font color='111111' size='1'  face='Tahoma'>" . formatDuit2($inapdrpasien[5]) . "</td> 
                    <td padding='0' width='15%' align='left'><font color='111111' size='1'  face='Tahoma'>" . formatDuit2($inapdrpasien[6]) . "</td> 
                </tr>";
            } else {
                echo    "<tr class='isi12' padding='0'><strong>
                    <td padding='0' width='18%'><font color='111111' size='1'  face='Tahoma'></td> 
                    <td padding='0' width='40%'><font color='111111' size='1'  face='Tahoma'>Total Obat & BHP</td> 
                    <td padding='0' width='2%'><font color='111111' size='1'  face='Tahoma'></td> 
                    <td padding='0' width='10%' align='left'><font color='111111' size='1'  face='Tahoma'></td> 
                    <td padding='0' width='5%' align='left'><font color='111111' size='1'  face='Tahoma'></td> 
                    <td padding='0' width='10%' align='left'><font color='111111' size='1'  face='Tahoma'></td> 
                    <td padding='0' width='15%' align='left'><font color='111111' size='1'  face='Tahoma'>" . $inapdrpasien[1] . "</td> 
                    </strong>
                           
                        </tr>";
            }
        }
        echo "  <tr class='isi12' padding='0'>
                            <td padding='0' width='18%'><font color='111111' size='1'  face='Tahoma'><b>TOTAL BIAYA</b></td> 
                            <td padding='0' width='40%'><font color='111111' size='1'  face='Tahoma'><b>:</b></td> 
                            <td padding='0' width='2%'><font color='111111' size='1'  face='Tahoma'></td> 
                            <td padding='0' width='10%' align='right'><font color='111111' size='1'  face='Tahoma'></td> 
                            <td padding='0' width='5%' align='right'><font color='111111' size='1'  face='Tahoma'></td> 
                            <td padding='0' width='10%' align='right'><font color='111111' size='1'  face='Tahoma'></td> 
                            <td padding='0' width='15%' align='right'><font color='111111' size='1'  face='Tahoma'><b>" . formatDuit2($total) . "</b></td> 
                        </tr>
                        <tr class='isi12' padding='0'>
			    <td colspan='7' padding='0'>
                                <table width='100%' bgcolor='#ffffff' align='left' border='0' padding='0' cellspacing='0' cellpadding='0'>
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='40%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>&nbsp;</font></td>              
                                    </tr>
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='40%' align=center><font color='000000' size='1'  face='Tahoma'>Keluarga Pasien</td> 
                                     <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>     
                                     <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>" . getOne("select kabupaten from setting") . "</font></td>              
                                    </tr>  
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='40%' align=center><font color='000000' size='1'  face='Tahoma'></td> 
                                     <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>Kasir</font></td>              
                                    </tr>  
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='40%'><font color='000000' size='1'  face='Tahoma'>&nbsp;</td> 
                                     <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='40%' align='right'><font color='000000' size='1'  face='Tahoma'></font></td>              
                                    </tr> 
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='40%'><font color='000000' size='1'  face='Tahoma'>&nbsp;</td> 
                                     <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='40%' align='right'><font color='000000' size='1'  face='Tahoma'></font></td>              
                                    </tr> 
                                    <tr class='isi12' padding='0'>
                                     <td padding='0' width='40%' align=center><font color='000000' size='1'  face='Tahoma'>( ............................. )</td>     
                                     <td padding='0' width='20%' align=center><font color='000000' size='1'  face='Tahoma'>&nbsp;</td>   
                                     <td padding='0' width='40%' align='center'><font color='000000' size='1'  face='Tahoma'>( ............................. )</font></td>              
                                    </tr>   
                              </table>
                            </td>
                        </tr>
                        <tr class='isi12' padding='0'>
			    <td colspan='7' padding='0'>&nbsp</td>
                        </tr>
                </table>";
    } else {
        echo "<font color='333333' size='1'  face='Times New Roman'><b>Data  Billing masih kosong !</b>";
    }

    ?>

    <script>
        window.onload = function() {
            // Use html2pdf.js to generate the PDF
            const content = document.body;
            const options = {
                margin: 0.20,
                filename: 'myfile.pdf',
                image: {
                    type: 'jpeg',
                    quality: 0.98
                },
                html2canvas: {
                    scale: 2
                },
                jsPDF: {
                    unit: 'in',
                    format: 'letter',
                    orientation: 'portrait'
                }
            };
            // html2pdf().from(content).set(options).save();
            html2pdf().from(content).set(options).outputPdf().then(function(dataPDFtoEncode) {
                const base64pdf = btoa(dataPDFtoEncode);
                // Send the base64Pdf to the server-side script for storage
                savePdfToDatabase(base64pdf);
            });
        };


        // function arrayBufferToBase64(arrayBuffer) {
        //     const uint8Array = new Uint8Array(arrayBuffer);
        //     let binary = '';
        //     for (let i = 0; i < uint8Array.length; i++) {
        //         binary += String.fromCharCode(uint8Array[i]);
        //     }
        //     return btoa(binary);
        // }

        function savePdfToDatabase(base64Pdf) {
            // Use AJAX to send base64Pdf to server-side script
            const xhr = new XMLHttpRequest();
            xhr.open('POST', 'save_pdf.php', true);
            xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
            xhr.onreadystatechange = function() {
                console.log(xhr);
                if (xhr.readyState === 4 && xhr.status === 200) {
                    console.log('PDF saved to database successfully.');
                }
            };

            const params = 'base64Pdf=' + encodeURIComponent(base64Pdf) +
                '&nomorsep=' + encodeURIComponent("<?php print($nomorsep); ?>");
            // console.log(params);
            // console.log(paramssep);
            // console.log(params);
            xhr.send(params);
        }
    </script>
</body>

</html>