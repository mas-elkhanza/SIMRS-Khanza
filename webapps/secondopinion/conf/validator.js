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
        for (var i = 1; i <= 41; i++) {
            var txt = document.getElementById("TxtIsi" + i);
            if (!txt) {
                continue;
            }

            if (txt.value.length === 0) {
                kedip("document.getElementById('TxtIsi" + i + "').style.backgroundColor = ");
                var msg = document.getElementById("MsgIsi" + i);
                if (msg) {
                    msg.innerHTML = "<br>Maaf, field ini belum diisi";
                }
                txt.focus();
                return false;
            }
        }

        return true;
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




