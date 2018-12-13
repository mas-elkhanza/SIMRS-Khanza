routes = [
  {
    path: '/',
    url: './index.html',
  },
  {
    path: '/signin/',
    url: './pages/signin.html',
  },
  {
    path: '/panduan/',
    url: './pages/panduan.html',
  },
  {
    path: '/home/',
    url: './pages/home.html',
  },
  {
    path: '/daftar/',
    url: './pages/daftar.html',
  },
  {
    path: '/kamar/',
    url: './pages/kamar.html',
  },
  {
    path: '/dokter/',
    url: './pages/dokter.html',
  },
  {
    path: '/pengaduan/',
    url: './pages/pengaduan.html',
  },
  {
    path: '/booking/:no_rkm_medis/:tanggal_periksa/:no_reg/',
    url: './pages/booking-detail.html',
  },
  {
    path: '/riwayat/:no_rkm_medis/:tgl_registrasi/:no_reg/',
    url: './pages/riwayat-detail.html',
  },
  {
    path: '/bantuan/',
    url: './pages/bantuan.html',
  },
  {
    path: '/profil/',
    url: './pages/profil.html',
  },
  {
    path: '/sukses/',
    url: './pages/sukses.html',
  },

  // Default route (404 page). MUST BE THE LAST
  {
    path: '(.*)',
    url: './pages/404.html',
  },
];
