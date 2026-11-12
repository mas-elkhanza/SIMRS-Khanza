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





/* Perbaikan <select> pada WebView JavaFX di macOS: klik mouse pada popup bawaan tidak memilih item. Dropdown diganti dengan daftar HTML. */
(function () {
    var ua = navigator.userAgent;
    if (ua.indexOf('JavaFX') < 0 || ua.indexOf('Macintosh') < 0 || window.__selectJavaFX) {
        return;
    }
    window.__selectJavaFX = true;
    var box = null, sel = null, idx = -1;

    function tutup() {
        if (box && box.parentNode) {
            box.parentNode.removeChild(box);
        }
        box = null;
        sel = null;
        idx = -1;
    }

    function sorot(i) {
        var it = box.childNodes, n;
        for (n = 0; n < it.length; n++) {
            it[n].style.background = (n === i) ? '#1a8fc9' : '#ffffff';
            it[n].style.color = (n === i) ? '#ffffff' : '#323232';
        }
        idx = i;
        if (it[i]) {
            var t = it[i].offsetTop, h = it[i].offsetHeight;
            if (t < box.scrollTop) {
                box.scrollTop = t;
            } else if (t + h > box.scrollTop + box.clientHeight) {
                box.scrollTop = t + h - box.clientHeight;
            }
        }
    }

    function pilih(i) {
        var s = sel;
        tutup();
        if (s && i >= 0 && s.selectedIndex !== i) {
            s.selectedIndex = i;
            var e1 = document.createEvent('HTMLEvents');
            e1.initEvent('input', true, false);
            s.dispatchEvent(e1);
            var e2 = document.createEvent('HTMLEvents');
            e2.initEvent('change', true, false);
            s.dispatchEvent(e2);
        }
        if (s) {
            s.focus();
        }
    }

    function buka(s) {
        var r = s.getBoundingClientRect(), cs = window.getComputedStyle(s), o = s.options, n, d;
        tutup();
        sel = s;
        box = document.createElement('div');
        box.style.cssText = 'position:absolute;z-index:2147483647;background:#fff;border:1px solid #9aa5a0;box-shadow:0 3px 8px rgba(0,0,0,.3);overflow-y:auto;max-height:260px;margin:0;padding:2px 0;box-sizing:border-box;';
        box.style.fontFamily = cs.fontFamily;
        box.style.fontSize = cs.fontSize;
        box.style.minWidth = r.width + 'px';
        for (n = 0; n < o.length; n++) {
            d = document.createElement('div');
            d.appendChild(document.createTextNode(o[n].text === '' ? ' ' : o[n].text));
            d.style.cssText = 'padding:2px 8px;cursor:default;white-space:nowrap;';
            d.setAttribute('data-i', n);
            if (o[n].disabled) {
                d.style.color = '#999999';
                d.setAttribute('data-d', '1');
            }
            box.appendChild(d);
        }
        document.body.appendChild(box);
        var tinggi = box.offsetHeight, bawah = window.innerHeight - r.bottom;
        box.style.left = (r.left + window.pageXOffset) + 'px';
        box.style.top = ((bawah < tinggi && r.top > bawah) ? (r.top - tinggi) : r.bottom) + window.pageYOffset + 'px';
        sorot(s.selectedIndex);
    }

    document.addEventListener('mousedown', function (e) {
        var t = e.target;
        if (box && box.contains(t)) {
            e.preventDefault();
            return;
        }
        if (t.tagName === 'SELECT' && !t.multiple && !(t.size > 1) && !t.disabled) {
            e.preventDefault();
            var sama = (sel === t);
            t.focus();
            if (sama) {
                tutup();
            } else {
                buka(t);
            }
            return;
        }
        tutup();
    }, true);

    document.addEventListener('click', function (e) {
        var t = e.target;
        if (box && box.contains(t) && t.getAttribute('data-i') !== null && t.getAttribute('data-d') === null) {
            pilih(parseInt(t.getAttribute('data-i'), 10));
        }
    }, true);

    document.addEventListener('mouseover', function (e) {
        var t = e.target;
        if (box && box.contains(t) && t.getAttribute('data-i') !== null) {
            sorot(parseInt(t.getAttribute('data-i'), 10));
        }
    }, true);

    document.addEventListener('keydown', function (e) {
        if (!box) {
            return;
        }
        var k = e.keyCode, n = box.childNodes.length;
        if (k === 27 || k === 9) {
            tutup();
        } else if (k === 40 && idx < n - 1) {
            e.preventDefault();
            sorot(idx + 1);
        } else if (k === 38 && idx > 0) {
            e.preventDefault();
            sorot(idx - 1);
        } else if (k === 13 || k === 32) {
            e.preventDefault();
            e.stopPropagation();
            pilih(idx);
        }
    }, true);

    window.addEventListener('blur', tutup);
    window.addEventListener('resize', tutup);
})();
