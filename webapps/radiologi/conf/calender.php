<?php
	// fungsi untuk merubah format tanggal
	function saiki($hr,$tgl,$bln,$thn) {
		switch($hr) {
			case '0' : $hr="Minggu"; break;
			case '1' : $hr="Senin"; break;
			case '2' : $hr="Selasa"; break;
			case '3' : $hr="Rabu"; break;
			case '4' : $hr="Kamis"; break;
			case '5' : $hr="Jum'at"; break;
			default  : $hr="Sabtu"; break;
		}
		switch($bln) {
			case "01": $bulan="Januari"; break;
			case "02": $bulan="Februari"; break;
			case "03": $bulan="Maret"; break;
			case "04": $bulan="April"; break;
			case "05": $bulan="Mei"; break;
			case "06": $bulan="Juni"; break;
			case "07": $bulan="Juli"; break;
			case "08": $bulan="Agustus"; break;
			case "09": $bulan="September"; break;
			case "10": $bulan="Oktober"; break;
			case "11": $bulan="November"; break;
			default: $bulan="Desember"; break;
		}
		echo "$hr, $tgl $bulan $thn";
	}

	$month=date("m");
	$year=date("Y");
	$day=date("d");
	$hari=date("w");
	$endDate=date("t",mktime(0,0,0,$month,$day,$year));

	echo '<table align="center" border="0" width="100%" cellpadding=1 cellspacing=1 style="border:1px solid #CCCCCC" bgcolor="#FFFFFF"><tr style="font-family:arial;font-size:10px;"><td align=center><font color="#CC6633">';
	echo saiki($hari,$day,$month,$year);
	echo '</font></td></tr></table>';

	echo '
		<table align="center" border="0" width="100%" cellpadding=1 cellspacing=1 style="border:1px solid #CCCCCC" bgcolor="#FFFFFF">
		<tr bgcolor="#EFEFEF" style="font-family:arial;color:green;font-size:10px;">
		<td align=center><font color=red>M</font></td>
		<td align=center>S</td>
		<td align=center>S</td>
		<td align=center>R</td>
		<td align=center>K</td>
		<td align=center>J</td>
		<td align=center><font color=blue>S</font></td>
		</tr>
	';

	$s=date ("w", mktime (0,0,0,$month,1,$year));
	for ($ds=1;$ds<=$s;$ds++) {
		echo "<td style=\"font-family:arial;font-size:10px;color:#B3D9FF;\" width=\8%\" align=center valign=middle bgcolor=\"#FFFFFF\"></td>";
	}

	for ($d=1;$d<=$endDate;$d++) {
		if (date("w",mktime (0,0,0,$month,$d,$year)) == 0) echo "<tr bgcolor=\"#FFFFFF\">";
		$fontColor="#000000";
		
		if (date("D",mktime (0,0,0,$month,$d,$year)) == "Sun") $fontColor="red";
		if (date("D",mktime (0,0,0,$month,$d,$year)) == "Sat") $fontColor="blue";
		if ($d==$day)
			echo "<td style=\"font-family:arial;font-size:10px;color:green\" width=\"15%\" align=center valign=middle bgcolor=#B9DCFD><b>$d</b></td>";
		else
			echo "<td style=\"font-family:arial;font-size:10px;color:#333333\" width=\"15%\" align=center valign=middle><span style=\"color:$fontColor\">$d</span></td>";

		if (date("w",mktime (0,0,0,$month,$d,$year)) == 6) echo "</tr>";
	}
	echo '</table>';
?>