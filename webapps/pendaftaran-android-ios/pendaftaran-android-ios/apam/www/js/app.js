//Main configuration. Silakan sesuaikan settingan dibawah ini sesuai. Baca komentar dibelakangnya
const nama_instansi = 'RSUD H. Damanhuri'; // Hospital Name
const apiUrl = 'http://localhost/basoro.net-dev/pasien-rest/'; // API Server URL
const startDate = -1; // Start date of day for registration
const endDate = 7; // End date of day for registration

// Dom7
var $$ = Dom7;

// Framework7 App main instance
var app  = new Framework7({
  root: '#app', // App root element
  id: 'com.rshdbarabai.apam', // App bundle ID
  name: 'APAM Barabai', // App name
  dialog: {
    title: 'Peringatan'
  },
  statusbar: {
    iosOverlaysWebview: true,
  },
  theme: 'auto', // Automatic theme detection
  // App routes
  routes: routes,
});

// Init/Create main view
var mainView = app.views.create('.view-main', {
  url: '/'
});

// If authentication is success, moving to Main page.
var no_rkm_medis = localStorage.getItem("no_rkm_medis");

if (no_rkm_medis) {
  mainView.router.navigate('/home/', {
    clearPreviousHistory: true
  });
}

//=================================================//
// Load data untuk halaman signin.html               //
//=================================================//

$$(document).on('page:init', '.page[data-name="signin"]', function(e) {

  $$(".nama-instansi").html(nama_instansi);

  $$('.page[data-name="signin"] .signin-btn').on('click', function () {
    var no_rkm_medis = $$('#signin-form .no_rkm_medis').val();
    var no_ktp = $$('#signin-form .no_ktp').val();

    if(no_rkm_medis == "") {
      app.dialog.alert('Isian nomor kartu berobat tidak boleh kosong.');
    }
    else if(no_ktp == "") {
      app.dialog.alert('Isian nomor KTP tidak boleh kosong.');
    }
    else {
      // Show Preloader
      app.dialog.preloader("Menghubungkan ke server...");
      app.request.post(apiUrl + 'api.php', {
        action: 'signin',
        no_rkm_medis: no_rkm_medis,
        no_ktp: no_ktp
      }, function (data) {
        app.dialog.close();
        data = JSON.parse(data);

        if(data.state == "invalid") {
          app.dialog.alert('Nomor kartu dan/atau nomor KTP anda tidak sesuai.');
        }
        else if(data.state == "valid") {
          localStorage.setItem("no_rkm_medis", data.no_rkm_medis);
          no_rkm_medis = data.no_rkm_medis;

          mainView.router.navigate('/home/', {
            clearPreviousHistory: true
          });
        }
        else {
          app.dialog.alert('Login error:', data);
        }
      });
    }
  });
});

//=================================================//
// Load data untuk halaman home.html               //
//=================================================//

$$(document).on('page:init', '.page[data-name="home"]', function(e) {

  $$(".nama-instansi").html(nama_instansi);

  var no_rkm_medis = localStorage.getItem("no_rkm_medis");

  app.dialog.preloader('Loading...');

  //Getting Booking Result
  app.request.post(apiUrl + 'api.php', {
    action: 'antrian',
    no_rkm_medis: no_rkm_medis
  }, function (data) {
    //app.dialog.close();
    data = JSON.parse(data);

    var html = '';
    for(i=0; i<data.length; i++) {
      html += '<div class="row">';
      html += '  <div class="col">';
      html += '      <div class="text-align-center" style="font-size:18px;font-weight:bold;text-shadow: 2px 2px 4px #000000;color:#fff;padding-top:5px;">' + data[i]['nm_poli'] + '</div>';
      html += '  </div>';
      html += '</div>';
      html += '<div class="row">';
      html += '  <div class="col">';
      html += '      <div class="text-align-center" style="font-size:18px;font-weight:bold;text-shadow: 2px 2px 4px #000000;color:#fff;padding-top:px;">' + data[i]['nm_dokter'] + '</div>';
      html += '  </div>';
      html += '</div>';
      html += '<div class="row">';
      html += '  <div class="col">';
      html += '      <div class="text-align-center" style="font-size:80px;font-weight:bold;text-shadow: 2px 2px 4px #000000;color:#fff;padding-top:0px;">' + data[i]['no_reg'] + '</div>';
      html += '  </div>';
      html += '</div>';
    }

    $$(".antrian-list").html(html);

  });

  //Getting Booking list
  app.request.post(apiUrl + 'api.php', {
    action: 'lastbooking',
    no_rkm_medis: no_rkm_medis
  }, function (data) {
    app.dialog.close();
    data = JSON.parse(data);

    var html = '';
    for(i=0; i<data.length; i++) {
      html += '<li>';
      html += ' <a href="/booking/' + no_rkm_medis + '/' + data[i]['tanggal_periksa'] + '/' + data[i]['no_reg'] + '/" class="item-link item-content">';
      html += '  <div class="item-inner">';
      html += '   <div class="item-title-row">';
      html += '    <div class="item-title">';
      html += '     <div class="item-header">' + data[i]['tanggal_periksa'] + ' / ' + data[i]['status'] + '</div>';
      html += '     ' + data[i]['nm_poli'] + '';
      html += '     <div class="item">' + data[i]['nm_dokter'] + '</div>';
      html += '     <div class="item-footer">' + data[i]['png_jawab'] + '</div>';
      html += '    </div>';
      html += '   </div>';
      html += '  </div>';
      html += ' </a>';
      html += '</li>';
    }

    $$(".lastbooking-list").html(html);

  });

});

//=================================================//
// Load data untuk halaman booking-detail.html               //
//=================================================//

$$(document).on('page:init', '.page[data-name="bookingdetail"]', function(e) {

  var page = e.detail;
  var no_rkm_medis = page.route.params.no_rkm_medis;
  var tanggal_periksa = page.route.params.tanggal_periksa;
  var no_reg = page.route.params.no_reg;

  app.dialog.preloader('Loading...');

  //Getting Booking list
  app.request.post(apiUrl + 'api.php', {
    action: 'bookingdetail',
    no_rkm_medis: no_rkm_medis,
    tanggal_periksa: tanggal_periksa,
    no_reg: no_reg
  }, function (data) {
    app.dialog.close();
    data = JSON.parse(data);

    var html = '';
    for(i=0; i<data.length; i++) {
      html += ' <div class="item-content">';
      html += '  <div class="item-inner">';
      html += '   <div class="item-title">';
      html += '    Nomor Kartu Berobat: ' + no_rkm_medis + '';
      html += '    <div class="item-header">Tanggal daftar: ' + data[i]['tanggal_booking'] + '</div>';
      html += '    <div class="item-header">Tanggal periksa: ' + data[i]['tanggal_periksa'] + '</div>';
      html += '    <div class="item-header">Status: ' + data[i]['status'] + '</div>';
      html += '    Klinik: ' + data[i]['nm_poli'] + '';
      html += '    <div class="item">Dokter: ' + data[i]['nm_dokter'] + '</div>';
      html += '    <div class="item">Nomor antrian: ' + data[i]['no_reg'] + '</div>';
      html += '    <div class="item-footer">Cara bayar: ' + data[i]['png_jawab'] + '</div>';
      html += '   </div>';
      html += '  </div>';
      html += ' </div>';
    }

    $$(".booking-detail").html(html);

  });

});

//=================================================//
// Load data untuk halaman kamar.html               //
//=================================================//

$$(document).on('page:init', '.page[data-name="kamar"]', function(e) {

  //Getting Dokter list
  app.dialog.preloader('Loading...');
  app.request.post(apiUrl + 'api.php', {
    action: 'kamar'
  }, function (data) {
    app.dialog.close();
    data = JSON.parse(data);

    var html = '';
    for(i=0; i<data.length; i++) {
      html += '<li>';
      html += ' <div class="item-content">';
      html += '  <div class="item-inner">';
      html += '   <div class="item-title">';
      html += '    <div class="item">' + data[i]['kelas'] + '</div>';
      html += '    Tersedia: ' + data[i]['kosong'] + '';
      html += '    <div class="item-footer">Kapasitas: ' + data[i]['total'] + '</div>';
      html += '   </div>';
      html += '  </div>';
      html += ' </div>';
      html += '</li>';
    }

    $$(".kamar-list").html(html);
  });

});

//=================================================//
// Load data untuk halaman dokter.html               //
//=================================================//

$$(document).on('page:init', '.page[data-name="dokter"]', function(e) {

  //Getting History list
  app.dialog.preloader("Loading...");
  app.request.post(apiUrl + 'api.php', {
    action: 'dokter',
  }, function (data) {
    app.dialog.close();
    data = JSON.parse(data);

    var html = '';
    if(data.state == "notfound") {
      html += '<li><div class="item-content">Tidak ada jadwal dokter hari ini</div></li>';
    } else {
      for(i=0; i<data.length; i++) {
        html += '<li>';
        html += ' <div class="item-content">';
        html += '  <div class="item-media"><img src="img/' + data[i]['jk'] + '.png" width="44"></div>';
        html += '  <div class="item-inner">';
        html += '   <div class="item-title">';
        html += '    <div class="item">' + data[i]['nm_poli'] + '</div>';
        html += '    ' + data[i]['nm_dokter'] + '';
        html += '    <div class="item-footer">' + data[i]['jam_mulai'] + ' s/d ' + data[i]['jam_selesai'] + ' WITA</div>';
        html += '   </div>';
        html += '  </div>';
        html += ' </div>';
        html += '</li>';
      }
    }

    $$(".dokter-list").html(html);

  });

});

//=================================================//
// Load data untuk halaman riwayat-detail.html               //
//=================================================//

$$(document).on('page:init', '.page[data-name="riwayatdetail"]', function(e) {

  var page = e.detail;
  var no_rkm_medis = page.route.params.no_rkm_medis;
  var tgl_registrasi = page.route.params.tgl_registrasi;
  var no_reg = page.route.params.no_reg;

  //Getting History list
  app.dialog.preloader("Loading...");
  app.request.post(apiUrl + 'api.php', {
    action: 'riwayatdetail',
    no_rkm_medis: no_rkm_medis,
    tgl_registrasi: tgl_registrasi,
    no_reg: no_reg
  }, function (data) {
    app.dialog.close();
    data = JSON.parse(data);

    var html = '';
    for(i=0; i<data.length; i++) {

      html += '<div class="block-title">Data Pendaftaran</div>';
      html += '<div class="card">';
      html += ' <div class="list">';
      html += '  <ul>';
      html += '   <li>';
      html += '    <div class="item-content">';
      html += '     <div class="item-inner">';
      html += '      <div class="item-title">Nomor Rawat</div>';
      html += '      <div class="item-after">' + data[i]['no_rawat'] + '</div>';
      html += '     </div>';
      html += '    </div>';
      html += '   </li>';
      html += '   <li>';
      html += '    <div class="item-content">';
      html += '     <div class="item-inner">';
      html += '      <div class="item-title">Tanggal</div>';
      html += '      <div class="item-after">' + data[i]['tgl_registrasi'] + '</div>';
      html += '     </div>';
      html += '    </div>';
      html += '   </li>';
      html += '   <li>';
      html += '    <div class="item-content">';
      html += '     <div class="item-inner">';
      html += '      <div class="item-title">Klinik</div>';
      html += '      <div class="item-after">' + data[i]['nm_poli'] + '</div>';
      html += '     </div>';
      html += '    </div>';
      html += '   </li>';
      html += '   <li>';
      html += '    <div class="item-content">';
      html += '     <div class="item-inner">';
      html += '      <div class="item-title">Dokter</div>';
      html += '      <div class="item-after">' + data[i]['nm_dokter'] + '</div>';
      html += '     </div>';
      html += '    </div>';
      html += '   </li>';
      html += '   <li>';
      html += '    <div class="item-content">';
      html += '     <div class="item-inner">';
      html += '      <div class="item-title">Cara Bayar</div>';
      html += '      <div class="item-after">' + data[i]['png_jawab'] + '</div>';
      html += '     </div>';
      html += '    </div>';
      html += '   </li>';
      html += '  </ul>';
      html += ' </div>';
      html += '</div>';

      html += '<div class="block-title">Pemeriksaan</div>';
      html += '<div class="card padding">';
      html += '  <div class="card-content">Keluhan: ' + data[i]['keluhan'] + '</div>';
      html += '  <div class="card-content">Pemeriksaan: ' + data[i]['pemeriksaan'] + '</div>';
      html += '</div>';

      html += '<div class="block-title">Diagnosa</div>';
      html += '<div class="card padding">';
      html += '  <div class="card-content">' + data[i]['nm_penyakit'] + '</div>';
      html += '</div>';

      html += '<div class="block-title">Resep Obat</div>';
      html += '<div class="card padding">';
      html += '  <div class="card-content">' + data[i]['nama_brng'] + '</div>';
      html += '</div>';

    }

    $$(".riwayat-detail").html(html);

  });

});

//=================================================//
// Load data untuk halaman profil.html               //
//=================================================//

$$(document).on('page:init', '.page[data-name="profil"]', function(e) {

  var no_rkm_medis = localStorage.getItem("no_rkm_medis");

  // With callback on close
  var notifikasiBooking = app.notification.create({
    icon: '<i class="icon demo-icon color-red">!</i>',
    title: 'Pemberitahuan',
    titleRightText: 'now',
    subtitle: 'Status booking anda telah terdaftar',
    text: 'Ini adalah demo status notifikasi booking pendaftaran. Saat ditutup, maka status notif akan berubah menjadi telah dibaca.',
    closeButton: true,
    closeOnClick: true,
    on: {
      close: function () {
        app.dialog.alert('Notifikasi telah dibaca.');
      },
    },
  });

  $$('.notifikasi-btn').on('click', function () {
    notifikasiBooking.open();
  });

  //Getting user information
  app.dialog.preloader('Loading...');
  app.request.post(apiUrl + 'api.php', {
    action: "profil",
    no_rkm_medis: no_rkm_medis
  }, function (data) {
    //app.dialog.close();
    data = JSON.parse(data);

    $$('.no_rkm_medis').text(data['no_rkm_medis']);
    $$('.nm_pasien').text(data['nm_pasien']);
    $$('.tgl_lahir').text(data['tgl_lahir']);
    $$('.alamat').text(data['alamat']);

  });

  //Getting Booking list
  app.request.post(apiUrl + 'api.php', {
    action: 'booking',
    no_rkm_medis: no_rkm_medis
  }, function (data) {
    //app.dialog.close();
    data = JSON.parse(data);

    var html = '';
    for(i=0; i<data.length; i++) {
      html += '<li>';
      html += ' <a href="/booking/' + no_rkm_medis + '/' + data[i]['tanggal_periksa'] + '/' + data[i]['no_reg'] + '/" class="item-link item-content">';
      html += '  <div class="item-inner">';
      html += '   <div class="item-title-row">';
      html += '    <div class="item-title">';
      html += '     <div class="item-header">' + data[i]['tanggal_periksa'] + ' / ' + data[i]['status'] + '</div>';
      html += '     ' + data[i]['nm_poli'] + '';
      html += '     <div class="item">' + data[i]['nm_dokter'] + '</div>';
      html += '     <div class="item-footer">' + data[i]['png_jawab'] + '</div>';
      html += '    </div>';
      html += '   </div>';
      html += '  </div>';
      html += ' </a>';
      html += '</li>';
    }

    $$(".booking-list").html(html);

  });

  //Getting History list
  //app.dialog.preloader("Loading...");
  app.request.post(apiUrl + 'api.php', {
    action: 'riwayat',
    no_rkm_medis: no_rkm_medis
  }, function (data) {
    app.dialog.close();
    data = JSON.parse(data);

    var html = '';
    for(i=0; i<data.length; i++) {
      html += '<li>';
      html += ' <a href="/riwayat/' + no_rkm_medis + '/' + data[i]['tgl_registrasi'] + '/' + data[i]['no_reg'] + '/" class="item-link item-content">';
      html += '  <div class="item-inner">';
      html += '   <div class="item-title-row">';
      html += '    <div class="item-title">';
      html += '     <div class="item-header">' + data[i]['tgl_registrasi'] + '</div>';
      html += '     ' + data[i]['nm_poli'] + '';
      html += '     <div class="item">' + data[i]['nm_dokter'] + '</div>';
      html += '     <div class="item-footer">' + data[i]['png_jawab'] + '</div>';
      html += '    </div>';
      html += '   </div>';
      html += '  </div>';
      html += ' </a>';
      html += '</li>';
    }

    $$(".riwayat-list").html(html);

  });

  $$('.logout-btn').on('click', function () {
    app.dialog.confirm('Anda yakin ingin signout?', function () {
      app.dialog.alert('Anda telah keluar sepenuhnya dari sistem!');
      localStorage.removeItem("no_rkm_medis");
      mainView.router.navigate('/', {
        clearPreviousHistory: true
      });
    });
  });

});


//=================================================//
// Load data untuk halaman daftar.html               //
//=================================================//

$$(document).on('page:init', '.page[data-name="daftar"]', function(e) {

  var today = new Date();
  var startDay = new Date().setDate(today.getDate() - (startDate));
  var endDay = new Date().setDate(today.getDate() + endDate);
  var yearBefore = new Date().setDate(today.getDate() - 365);
  var monthNames = ['Januari', 'Februari', 'Maret', 'April', 'Mei', 'Juni', 'Juli', 'Agustus' , 'September' , 'Oktober', 'November', 'Desember'];
  var dayNamesShort = ['Min', 'Sen', 'Sel', 'Rab', 'Kam', 'Jum', 'Sab'];

  var calendar = app.calendar.create({
    containerEl: '#calendar-container',
    inputEl: '.tanggal',
    disabled: [
      {
        from: yearBefore,
        to: startDay
      },
      {
        from: endDay
      }
    ],
    //value: [new Date()],
    weekHeader: true,
    dayNamesShort: dayNamesShort,
    renderToolbar: function () {
      return '<div class="toolbar calendar-custom-toolbar no-shadow">' +
        '<div class="toolbar-inner">' +
          '<div class="left">' +
            '<a href="#" class="link icon-only"><i class="icon icon-back ' + (app.theme === 'md' ? 'color-black' : '') + '"></i></a>' +
          '</div>' +
          '<div class="center"></div>' +
          '<div class="right">' +
            '<a href="#" class="link icon-only"><i class="icon icon-forward ' + (app.theme === 'md' ? 'color-black' : '') + '"></i></a>' +
          '</div>' +
        '</div>' +
      '</div>';
    },
    on: {
      init: function (c) {
        $$('.calendar-custom-toolbar .center').text(monthNames[c.currentMonth] +' ' + c.currentYear);
        $$('.calendar-custom-toolbar .left .link').on('click', function () {
          calendar.prevMonth();
        });
        $$('.calendar-custom-toolbar .right .link').on('click', function () {
          calendar.nextMonth();
        });
      },
      monthYearChangeStart: function (c) {
        $$('.calendar-custom-toolbar .center').text(monthNames[c.currentMonth] +' ' + c.currentYear);
      }
    }
  });

  $$('.page[data-name="daftar"] .cekjadwal-btn').on('click', function () {

    var tanggal = $$('.tanggal').val();

    if(tanggal == "") {
      app.dialog.alert('Silakan pilih tanggal berobat.');
    } else {

      app.dialog.preloader("Loading...");

      app.request.post(apiUrl + 'api.php', {
        action: 'jadwalklinik',
        tanggal: tanggal
      }, function (data) {
        app.dialog.close();
        data = JSON.parse(data);

        var html = '';
        html += '<option class="kd_poli" value="" selected>____Pilih Klinik____</option>';
        for(i=0; i<data.length; i++) {
          html += '<option class="kd_poli" value="' + data[i]['kd_poli'] + '">' + data[i]['nm_poli'] + ' [' + data[i]['jam_mulai'] + ' - ' + data[i]['jam_selesai'] + ']</option>';
        }

        $$(".klinik-list").html(html);
        $$(".klinik-list~.item-content .item-after").html('____Pilih Klinik____');
      });

      $$('body').on('change', '.klinik-list', function() {
        var kd_poli = $$(this).val();
        var tanggal = $$('.tanggal').val();

        app.request.post(apiUrl + 'api.php', {
          action: 'jadwaldokter',
          tanggal: tanggal,
          kd_poli: kd_poli
        }, function (data) {
          app.dialog.close();
          data = JSON.parse(data);

          var html = '';
          var attrStr = '';

          for(i=0; i<data.length; i++) {
            if(i == 0) attrStr = 'checked';
            else attrStr = "";

            html += '<li>';
            html += '  <label class="item-radio item-content">';
            html += '    <input type="radio" class="kd_dokter" name="kd_dokter" value="' + data[i]['kd_dokter'] + '" ' + attrStr + '>';
            html += '    <i class="icon icon-radio"></i>';
            html += '    <div class="item-inner">';
            html += '      <div class="item-title">' + data[i]['nm_dokter'] + '</div>';
            html += '    </div>';
            html += '  </label>';
            html += '</li>';

          }

          $$(".dokter-list").html(html);
          $$(".dokter-list~.item-content .item-after").html(data[0]['nm_dokter']);
          $$(".dokter-list").show();
        });

      });

    }

    $$(".klinik").show();
    $$(".dokter-list").hide();

  });

  app.request.post(apiUrl + 'api.php', {
    action: "carabayar"
  }, function (data) {
    app.dialog.close();
    data = JSON.parse(data);

    var html = '';
    html += '<option class="kd_pj" value="" selected>____Pilih____</option>';

    for(i=0; i<data.length; i++) {
      html += '<option class="kd_pj" value="' + data[i]['kd_pj'] + '">' + data[i]['png_jawab'] + '</option>';
    }

    $$(".penjab-list").html(html);
    $$(".penjab-list~.item-content .item-after").html('____Pilih____');

  });

  $$('.page[data-name="daftar"] .daftar-btn').on('click', function () {

    var no_rkm_medis = localStorage.getItem("no_rkm_medis");
    var tanggal = $$('#daftar-form .tanggal').val();
    var kd_poli = $$('#daftar-form .kd_poli').val();
    var kd_dokter = $$("input[name='kd_dokter']:checked").val();
    var kd_pj = $$('#daftar-form .kd_pj').val();

    if(no_rkm_medis == "") {
      app.dialog.alert('Nomor rekam medis anda tidak sesuai dengan akun login.');
    }
    else if(tanggal == "") {
      app.dialog.alert('Silakan masukkan tanggal berobat.');
    }
    else if(kd_poli == "") {
      app.dialog.alert('Pilih klinik tujuan anda.');
    }
    else if(kd_dokter == "") {
      app.dialog.alert('Silakan pilih dokter.');
    }
    else if(kd_pj == "") {
      app.dialog.alert('Silakan pilih cara bayar.');
    }
    else {
      // Show Preloader
      app.dialog.preloader("Loading...");

      app.request.post(apiUrl + 'api.php', {
        action: "daftar",
        no_rkm_medis: no_rkm_medis,
        tanggal: tanggal,
        kd_poli: kd_poli,
        kd_dokter: kd_dokter,
        kd_pj: kd_pj
      }, function (data) {
        app.dialog.close();
        data = JSON.parse(data);

        if(data.state == "duplication") {
          app.dialog.alert('Anda sudah terdaftar ditanggal pilihan anda.');
        }
        else if(data.state == "success") {
          mainView.router.navigate('/sukses/', {
            clearPreviousHistory: true
          });
        }
        else {
          app.dialog.alert('Register error: ', data);
        }
      });
    }

  });

});


//=================================================//
// Load data untuk halaman sukses.html               //
//=================================================//

$$(document).on('page:init', '.page[data-name="sukses"]', function(e) {

  var no_rkm_medis = localStorage.getItem("no_rkm_medis");

  //Getting Booking Result
  app.dialog.preloader("Loading...");
  app.request.post(apiUrl + 'api.php', {
    action: 'sukses',
    no_rkm_medis: no_rkm_medis
  }, function (data) {
    app.dialog.close();
    data = JSON.parse(data);

    var html = '';
    for(i=0; i<data.length; i++) {
      html += ' <div class="item-content">';
      html += '  <div class="item-inner">';
      html += '   <div class="item-title">';
      html += '    Nomor Kartu Berobat: ' + no_rkm_medis + '';
      html += '    <div class="item-header">Tanggal booking: ' + data[i]['tanggal_booking'] + '</div>';
      html += '    <div class="item-header">Tanggal periksa: ' + data[i]['tanggal_periksa'] + '</div>';
      html += '    <div class="item">Status booking: ' + data[i]['status'] + '</div>';
      html += '    Klinik: ' + data[i]['nm_poli'] + '';
      html += '    <div class="item">Dokter: ' + data[i]['nm_dokter'] + '</div>';
      html += '    <div class="item">Nomor antrian: ' + data[i]['no_reg'] + '</div>';
      html += '    <div class="item-footer">Cara bayar: ' + data[i]['png_jawab'] + '</div>';
      html += '   </div>';
      html += '  </div>';
      html += ' </div>';
    }

    $$(".sukses-detail").html(html);

  });

});

//=================================================//
// Load data untuk halaman pengaduan.html               //
//=================================================//

$$(document).on('page:init', '.page[data-name="pengaduan"]', function(e) {

  var messages = app.messages.create({
    el: '.messages',

    // First message rule
    firstMessageRule: function (message, previousMessage, nextMessage) {
      // Skip if title
      if (message.isTitle) return false;
      /* if:
        - there is no previous message
        - or previous message type (send/received) is different
        - or previous message sender name is different
      */
      if (!previousMessage || previousMessage.type !== message.type || previousMessage.name !== message.name) return true;
      return false;
    },
    // Last message rule
    lastMessageRule: function (message, previousMessage, nextMessage) {
      // Skip if title
      if (message.isTitle) return false;
      /* if:
        - there is no next message
        - or next message type (send/received) is different
        - or next message sender name is different
      */
      if (!nextMessage || nextMessage.type !== message.type || nextMessage.name !== message.name) return true;
      return false;
    },
    // Last message rule
    tailMessageRule: function (message, previousMessage, nextMessage) {
      // Skip if title
      if (message.isTitle) return false;
        /* if (bascially same as lastMessageRule):
        - there is no next message
        - or next message type (send/received) is different
        - or next message sender name is different
      */
      if (!nextMessage || nextMessage.type !== message.type || nextMessage.name !== message.name) return true;
      return false;
    }
  });

  // Init Messagebar
  var messagebar = app.messagebar.create({
    el: '.messagebar'
  });

  // Response flag
  var responseInProgress = false;

  // Send Message
  $$('.send-link').on('click', function () {
    var text = messagebar.getValue().replace(/\n/g, '<br>').trim();
    // return if empty message
    if (!text.length) return;

    // Clear area
    messagebar.clear();

    // Return focus to area
    messagebar.focus();

    // Add message to messages
    messages.addMessage({
      text: text,
    });

    if (responseInProgress) return;
    // Receive dummy message
    receiveMessage();
  });

  // Dummy response
  var answers = [
    'Ya!',
    'Tidak',
    'Hm...',
    'Kami tidak yakin',
    'Mungkin saja ;)',
    'Apa?',
    'Apakah anda yakin?',
    'Tentu saja',
    'Keluhan anda kami catat',
    'Luar biasa!!!'
  ]
  var people = [
    {
      name: 'Layanan Pelanggan',
      avatar: 'img/P.png'
    },
    {
      name: 'Admin Utama',
      avatar: 'img/L.png'
    }
  ];
  function receiveMessage() {
    responseInProgress = true;
    setTimeout(function () {
      // Get random answer and random person
      var answer = answers[Math.floor(Math.random() * answers.length)];
      var person = people[Math.floor(Math.random() * people.length)];

      // Show typing indicator
      messages.showTyping({
        header: person.name + ' is typing',
        avatar: person.avatar
      });

      setTimeout(function () {
        // Add received dummy message
        messages.addMessage({
          text: answer,
          type: 'received',
          name: person.name,
          avatar: person.avatar
        });
        // Hide typing indicator
        messages.hideTyping();
        responseInProgress = false;
      }, 4000);
    }, 1000);
  }

});
