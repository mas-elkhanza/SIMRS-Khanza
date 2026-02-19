var http_request = false;
   function makeRequest(url, parameters) {
      http_request = false;
      if (window.XMLHttpRequest) { // Mozilla, Safari,...
         http_request = new XMLHttpRequest();
         if (http_request.overrideMimeType) {
         	// set type accordingly to anticipated content type
            //http_request.overrideMimeType('text/xml');
            http_request.overrideMimeType('text/html');
         }
      } else if (window.ActiveXObject) { // IE
         try {
            http_request = new ActiveXObject("Msxml2.XMLHTTP");
         } catch (e) {
            try {
               http_request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e) {}
         }
      }
      if (!http_request) {
         alert('Browser ga suport XMLHTTP ');
         return false;
      }
      http_request.onreadystatechange = alertContents;
      http_request.open('GET', url + parameters, true);
      http_request.send(null);
   }

   function alertContents() {
      if (http_request.readyState == 4) {
         if (http_request.status == 200) {
            result = http_request.responseText;
            document.getElementById('myspan').innerHTML = result;            
         } else {
            alert('ada masalah.');
         }
      }
   }

function kedip(element)
{
 	setTimeout(""+element+" '#FF2A00'", 100);
 	setTimeout(""+element+" '#EEEEEE'", 300);
 	setTimeout(""+element+" '#FF2A00'", 500);
}

function setDefault(element, msg)
{
 	element.style.backgroundColor = '#EEEEEE';
 	msg.innerHTML = "";
}

function validasiLogin()
{
 	var TxtUser  		= document.getElementById("TxtUser");
 	var TxtPassword 	= document.getElementById("TxtPassword");
        var MsgUser  		= document.getElementById("MsgUser");
 	var MsgPassword 	= document.getElementById("MsgPassword");
 	
 	if (TxtUser.value.length == 0)
 	{
  		kedip("document.getElementById('TxtUser').style.backgroundColor = ");
  		MsgUser.innerHTML = "<br>Maaf, user id belum diisi";
  		TxtUser.focus();
  		return false;
 	}
 	else if (TxtPassword.value.length == 0)
 	{
  		kedip("document.getElementById('TxtPassword').style.backgroundColor = ");
  		MsgPassword.innerHTML = "<br>Password Anda belum diisi";
  		TxtPassword.focus();
  		return false;
 	}
 	else
 	{
  		return true;
 	}
}

function validasiSubkategori()
{
 	var TxtKodeSub 		= document.getElementById("TxtKodeSub");
 	var TxtNamaSub     	= document.getElementById("TxtNamaSub");
        var TxtKeterangan     	= document.getElementById("TxtKeterangan");
        var MsgKodeSub 		= document.getElementById("MsgKodeSub");
 	var MsgNamaSub    	= document.getElementById("MsgNamaSub");
        var MsgKeterangan   	= document.getElementById("MsgKeterangan");

 	if (TxtKodeSub.value.length == 0)
 	{
  		kedip("document.getElementById('TxtKodeSub').style.backgroundColor = ");
  		MsgKodeSub.innerHTML = "<br>Maaf, Kode Subkategori belum diisi";
  		TxtKodeSub.focus();
  		return false;
 	}
 	else if (TxtNamaSub.value.length == 0)
 	{
  		kedip("document.getElementById('TxtNamaSub').style.backgroundColor = ");
  		MsgNamaSub.innerHTML = "<br>Maaf, Subkategori belum diisi";
  		TxtNamaSub.focus();
  		return false;
 	}
        else if (TxtKeterangan.value.length == 0)
 	{
  		kedip("document.getElementById('TxtKeterangan').style.backgroundColor = ");
  		MsgKeterangan.innerHTML = "<br>Maaf, Katerangan belum diisi";
  		TxtKeterangan.focus();
  		return false;
 	}
 	else
 	{
  		return true;
 	}
}

function validasiKategori()
{
 	var TxtKode  		= document.getElementById("TxtKode");
 	var TxtKategori     	= document.getElementById("TxtKategori");
        var TxtKeterangan     	= document.getElementById("TxtKeterangan");
        var MsgKode 		= document.getElementById("MsgKode");
 	var MsgKategori    	= document.getElementById("MsgKategori");
        var MsgKeterangan   	= document.getElementById("MsgKeterangan");

 	if (TxtKode.value.length == 0)
 	{
  		kedip("document.getElementById('TxtKode').style.backgroundColor = ");
  		MsgKode.innerHTML = "<br>Maaf, Kode belum diisi";
  		TxtKode.focus();
  		return false;
 	}
 	else if (TxtKategori.value.length == 0)
 	{
  		kedip("document.getElementById('TxtKategori').style.backgroundColor = ");
  		MsgKategori.innerHTML = "<br>Maaf, Kategori belum diisi";
  		TxtKategori.focus();
  		return false;
 	}
        else if (TxtKeterangan.value.length == 0)
 	{
  		kedip("document.getElementById('TxtKeterangan').style.backgroundColor = ");
  		MsgKeterangan.innerHTML = "<br>Maaf, Katerangan belum diisi";
  		TxtKeterangan.focus();
  		return false;
 	}
 	else
 	{
  		return true;
 	}
}

function validasiJnsBayar()
{
 	var TxtKode  		= document.getElementById("TxtKode");
 	var TxtJenis     	= document.getElementById("TxtJenis");
        var TxtKeterangan     	= document.getElementById("TxtKeterangan");
        var MsgKode 		= document.getElementById("MsgKode");
 	var MsgJenis    	= document.getElementById("MsgJenis");
        var MsgKeterangan   	= document.getElementById("MsgKeterangan");

 	if (TxtKode.value.length == 0)
 	{
  		kedip("document.getElementById('TxtKode').style.backgroundColor = ");
  		MsgKode.innerHTML = "<br>Maaf, Kode belum diisi";
  		TxtKode.focus();
  		return false;
 	}
 	else if (TxtJenis.value.length == 0)
 	{
  		kedip("document.getElementById('TxtJenis').style.backgroundColor = ");
  		MsgJenis.innerHTML = "<br>Maaf, Jenis belum diisi";
  		TxtJenis.focus();
  		return false;
 	}
        else if (TxtKeterangan.value.length == 0)
 	{
  		kedip("document.getElementById('TxtKeterangan').style.backgroundColor = ");
  		MsgKeterangan.innerHTML = "<br>Maaf, Katerangan belum diisi";
  		TxtKeterangan.focus();
  		return false;
 	}
 	else
 	{
  		return true;
 	}
}

function validasiIsi()
{
 	var TxtIsi1  		= document.getElementById("TxtIsi1");
        var MsgIsi1 		= document.getElementById("MsgIsi1");

        var TxtIsi2  		= document.getElementById("TxtIsi2");
        var MsgIsi2 		= document.getElementById("MsgIsi2");

        var TxtIsi3  		= document.getElementById("TxtIsi3");
        var MsgIsi3 		= document.getElementById("MsgIsi3");

        var TxtIsi4  		= document.getElementById("TxtIsi4");
        var MsgIsi4 		= document.getElementById("MsgIsi4");

        var TxtIsi5  		= document.getElementById("TxtIsi5");
        var MsgIsi5 		= document.getElementById("MsgIsi5");

        var TxtIsi6  		= document.getElementById("TxtIsi6");
        var MsgIsi6 		= document.getElementById("MsgIsi6");

        var TxtIsi7  		= document.getElementById("TxtIsi7");
        var MsgIsi7 		= document.getElementById("MsgIsi7");

        var TxtIsi8  		= document.getElementById("TxtIsi8");
        var MsgIsi8 		= document.getElementById("MsgIsi8");

        var TxtIsi9  		= document.getElementById("TxtIsi9");
        var MsgIsi9 		= document.getElementById("MsgIsi9");

        var TxtIsi10  		= document.getElementById("TxtIsi10");
        var MsgIsi10 		= document.getElementById("MsgIsi10");

        var TxtIsi11  		= document.getElementById("TxtIsi11");
        var MsgIsi11 		= document.getElementById("MsgIsi11");

        var TxtIsi12  		= document.getElementById("TxtIsi12");
        var MsgIsi12 		= document.getElementById("MsgIsi12");
        
        var TxtIsi13  		= document.getElementById("TxtIsi13");
        var MsgIsi13 		= document.getElementById("MsgIsi13");

        var TxtIsi14  		= document.getElementById("TxtIsi14");
        var MsgIsi14 		= document.getElementById("MsgIsi14");

        var TxtIsi15 		= document.getElementById("TxtIsi15");
        var MsgIsi15 		= document.getElementById("MsgIsi15");

        var TxtIsi16  		= document.getElementById("TxtIsi16");
        var MsgIsi16 		= document.getElementById("MsgIsi16");

        var TxtIsi17  		= document.getElementById("TxtIsi17");
        var MsgIsi17 		= document.getElementById("MsgIsi17");

        var TxtIsi18  		= document.getElementById("TxtIsi18");
        var MsgIsi18 		= document.getElementById("MsgIsi18");

        var TxtIsi19  		= document.getElementById("TxtIsi19");
        var MsgIsi19 		= document.getElementById("MsgIsi19");

        var TxtIsi20  		= document.getElementById("TxtIsi20");
        var MsgIsi20 		= document.getElementById("MsgIsi20");

        var TxtIsi21  		= document.getElementById("TxtIsi21");
        var MsgIsi21 		= document.getElementById("MsgIsi21");

        var TxtIsi22  		= document.getElementById("TxtIsi22");
        var MsgIsi22 		= document.getElementById("MsgIsi22");

        var TxtIsi23  		= document.getElementById("TxtIsi23");
        var MsgIsi23 		= document.getElementById("MsgIsi23");

        var TxtIsi24  		= document.getElementById("TxtIsi24");
        var MsgIsi24 		= document.getElementById("MsgIsi24");

        var TxtIsi25  		= document.getElementById("TxtIsi25");
        var MsgIsi25 		= document.getElementById("MsgIsi25");

        var TxtIsi26  		= document.getElementById("TxtIsi26");
        var MsgIsi26 		= document.getElementById("MsgIsi26");

        var TxtIsi27  		= document.getElementById("TxtIsi27");
        var MsgIsi27 		= document.getElementById("MsgIsi27");

        var TxtIsi28  		= document.getElementById("TxtIsi28");
        var MsgIsi28 		= document.getElementById("MsgIsi28");

        var TxtIsi29  		= document.getElementById("TxtIsi29");
        var MsgIsi29 		= document.getElementById("MsgIsi29");

        var TxtIsi30  		= document.getElementById("TxtIsi30");
        var MsgIsi30 		= document.getElementById("MsgIsi30");

        var TxtIsi31  		= document.getElementById("TxtIsi31");
        var MsgIsi31 		= document.getElementById("MsgIsi31");

        var TxtIsi32  		= document.getElementById("TxtIsi32");
        var MsgIsi32 		= document.getElementById("MsgIsi32");

        var TxtIsi33  		= document.getElementById("TxtIsi33");
        var MsgIsi33 		= document.getElementById("MsgIsi33");

        var TxtIsi34  		= document.getElementById("TxtIsi34");
        var MsgIsi34 		= document.getElementById("MsgIsi34");

        var TxtIsi35  		= document.getElementById("TxtIsi35");
        var MsgIsi35 		= document.getElementById("MsgIsi35");

        var TxtIsi36  		= document.getElementById("TxtIsi36");
        var MsgIsi36 		= document.getElementById("MsgIsi36");

        var TxtIsi37  		= document.getElementById("TxtIsi37");
        var MsgIsi37 		= document.getElementById("MsgIsi37");

        var TxtIsi38  		= document.getElementById("TxtIsi38");
        var MsgIsi38 		= document.getElementById("MsgIsi38");

        var TxtIsi39  		= document.getElementById("TxtIsi39");
        var MsgIsi39 		= document.getElementById("MsgIsi39");

        var TxtIsi40  		= document.getElementById("TxtIsi40");
        var MsgIsi40 		= document.getElementById("MsgIsi40");

        var TxtIsi41  		= document.getElementById("TxtIsi41");
        var MsgIsi41 		= document.getElementById("MsgIsi41");

 	if (TxtIsi1.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi1').style.backgroundColor = ");
  		MsgIsi1.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi1.focus();
  		return false;
 	}
 	else
        if (TxtIsi2.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi2').style.backgroundColor = ");
  		MsgIsi2.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi2.focus();
  		return false;
 	}
        else
        if (TxtIsi3.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi3').style.backgroundColor = ");
  		MsgIsi3.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi3.focus();
  		return false;
 	}
        else
        if (TxtIsi4.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi4').style.backgroundColor = ");
  		MsgIsi4.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi4.focus();
  		return false;
 	}
        else
        if (TxtIsi5.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi5').style.backgroundColor = ");
  		MsgIsi5.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi5.focus();
  		return false;
 	}
        else
        if (TxtIsi6.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi6').style.backgroundColor = ");
  		MsgIsi6.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi6.focus();
  		return false;
 	}
        else
        if (TxtIsi7.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi7').style.backgroundColor = ");
  		MsgIsi7.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi7.focus();
  		return false;
 	}
        else
        if (TxtIsi8.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi8').style.backgroundColor = ");
  		MsgIsi8.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi8.focus();
  		return false;
 	}
        else
        if (TxtIsi9.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi9').style.backgroundColor = ");
  		MsgIsi9.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi9.focus();
  		return false;
 	}
        else
        if (TxtIsi10.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi10').style.backgroundColor = ");
  		MsgIsi10.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi10.focus();
  		return false;
 	}
        else
        if (TxtIsi11.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi11').style.backgroundColor = ");
  		MsgIsi11.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi11.focus();
  		return false;
 	}
        else
        if (TxtIsi12.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi12').style.backgroundColor = ");
  		MsgIsi12.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi12.focus();
  		return false;
 	}
        else
        if (TxtIsi13.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi13').style.backgroundColor = ");
  		MsgIsi13.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi13.focus();
  		return false;
 	}
        else
        if (TxtIsi14.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi14').style.backgroundColor = ");
  		MsgIsi14.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi14.focus();
  		return false;
 	}
        else
        if (TxtIsi15.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi15').style.backgroundColor = ");
  		MsgIsi15.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi15.focus();
  		return false;
 	}
        else
        if (TxtIsi16.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi16').style.backgroundColor = ");
  		MsgIsi16.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi16.focus();
  		return false;
 	}
        else
        if (TxtIsi17.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi17').style.backgroundColor = ");
  		MsgIsi17.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi17.focus();
  		return false;
 	}
        else
        if (TxtIsi18.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi18').style.backgroundColor = ");
  		MsgIsi18.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi18.focus();
  		return false;
 	}
        else
        if (TxtIsi19.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi19').style.backgroundColor = ");
  		MsgIsi19.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi19.focus();
  		return false;
 	}
        else
        if (TxtIsi20.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi20').style.backgroundColor = ");
  		MsgIsi20.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi20.focus();
  		return false;
 	}
        else
        if (TxtIsi21.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi21').style.backgroundColor = ");
  		MsgIsi21.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi21.focus();
  		return false;
 	}
        else
        if (TxtIsi22.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi22').style.backgroundColor = ");
  		MsgIsi22.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi22.focus();
  		return false;
 	}
        else
        if (TxtIsi23.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi23').style.backgroundColor = ");
  		MsgIsi23.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi23.focus();
  		return false;
 	}
        else
        if (TxtIsi24.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi24').style.backgroundColor = ");
  		MsgIsi24.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi24.focus();
  		return false;
 	}
        else
        if (TxtIsi25.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi25').style.backgroundColor = ");
  		MsgIsi25.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi25.focus();
  		return false;
 	}
        else
        if (TxtIsi26.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi26').style.backgroundColor = ");
  		MsgIsi26.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi26.focus();
  		return false;
 	}
        else
        if (TxtIsi27.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi27').style.backgroundColor = ");
  		MsgIsi27.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi27.focus();
  		return false;
 	}
        else
        if (TxtIsi28.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi28').style.backgroundColor = ");
  		MsgIsi28.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi28.focus();
  		return false;
 	}
        else
        if (TxtIsi29.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi29').style.backgroundColor = ");
  		MsgIsi29.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi29.focus();
  		return false;
 	}
        else
        if (TxtIsi30.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi30').style.backgroundColor = ");
  		MsgIsi30.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi30.focus();
  		return false;
 	}
        else
        if (TxtIsi31.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi31').style.backgroundColor = ");
  		MsgIsi31.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi31.focus();
  		return false;
 	}
        else
        if (TxtIsi32.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi32').style.backgroundColor = ");
  		MsgIsi32.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi32.focus();
  		return false;
 	}
        else
        if (TxtIsi33.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi33').style.backgroundColor = ");
  		MsgIsi33.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi33.focus();
  		return false;
 	}
        else
        if (TxtIsi34.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi34').style.backgroundColor = ");
  		MsgIsi34.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi34.focus();
  		return false;
 	}
        else
        if (TxtIsi35.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi35').style.backgroundColor = ");
  		MsgIsi35.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi35.focus();
  		return false;
 	}
        else
        if (TxtIsi36.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi36').style.backgroundColor = ");
  		MsgIsi36.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi36.focus();
  		return false;
 	}
        else
        if (TxtIsi37.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi37').style.backgroundColor = ");
  		MsgIsi37.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi37.focus();
  		return false;
 	}
        else
        if (TxtIsi38.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi38').style.backgroundColor = ");
  		MsgIsi38.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi38.focus();
  		return false;
 	}
        else
        if (TxtIsi39.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi39').style.backgroundColor = ");
  		MsgIsi39.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi39.focus();
  		return false;
 	}
        else
        if (TxtIsi40.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi40').style.backgroundColor = ");
  		MsgIsi40.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi40.focus();
  		return false;
 	}
        else
        if (TxtIsi41.value.length == 0)
 	{
  		kedip("document.getElementById('TxtIsi41').style.backgroundColor = ");
  		MsgIsi41.innerHTML = "<br>Maaf, field ini belum diisi";
  		TxtIsi41.focus();
  		return false;
 	}
 	else
 	{
  		return true;
 	}
}

function validasiInpMerk()
{
 	var TxtKode  		= document.getElementById("TxtKode");
 	var Txtmerk     	= document.getElementById("Txtmerk");
        var MsgKode 		= document.getElementById("MsgKode");
 	var MsgMerk     	= document.getElementById("MsgMerk");

 	if (TxtKode.value.length == 0)
 	{
  		kedip("document.getElementById('TxtKode').style.backgroundColor = ");
  		MsgKode.innerHTML = "<br>Maaf, Kode belum diisi";
  		TxtKode.focus();
  		return false;
 	}
 	else if (Txtmerk.value.length == 0)
 	{
  		kedip("document.getElementById('Txtmerk').style.backgroundColor = ");
  		MsgMerk.innerHTML = "<br>Maaf, Merk belum diisi";
  		Txtmerk.focus();
  		return false;
 	}
 	else
 	{
  		return true;
 	}
}

function validasiPengguna()
{
         var edNama    = document.getElementById("edNama");
         var edAlamat  = document.getElementById("edAlamat");
         var edUser    = document.getElementById("edUser");
         var edPass    = document.getElementById("edPass");
         var edKonfirm = document.getElementById("edKonfirm");

         if (edNama.value.length == 0)
         {
              kedip("document.getElementById('edNama').style.backgroundColor = ");
              MsgNama.innerHTML = "<br>Nama pengguna belum diisi";
              edNama.focus();
              return false;
         }
         else if (edAlamat.value.length == 0)
         {
              kedip("document.getElementById('edAlamat').style.backgroundColor = ");
              MsgAlamat.innerHTML = "<br>Alamat belum diisi";
              edAlamat.focus();
              return false;
         }
         else if (edUser.value.length == 0)
         {
              kedip("document.getElementById('edUser').style.backgroundColor = ");
              MsgUser.innerHTML = "<br>User id belum diisi";
              edUser.focus();
              return false;
         }
          else if (edPass.value.length == 0)
         {
              kedip("document.getElementById('edPass').style.backgroundColor = ");
              MsgPass.innerHTML = "<br>Password belum diisi";
              edPass.focus();
              return false;
         }
         else if (edKonfirm.value.length == 0)
         {
              kedip("document.getElementById('edKonfirm').style.backgroundColor = ");
              MsgKonfirm.innerHTML = "<br>Konfirmasi belum diisi";
              edKonfirm.focus();
              return false;
         }
         else if (edKonfirm.value.length != edPass.value.length )
         {
              kedip("document.getElementById('edKonfirm').style.backgroundColor = ");
              MsgKonfirm.innerHTML = "<br>Konfirmasi tidak sesuai dengan password";
              edKonfirm.focus();
              return false;
         }
         else
         {
          return true;
         }
}




