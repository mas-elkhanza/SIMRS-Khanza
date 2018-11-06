var link_url = 'http://rsudreg.online';
        
/*
| Cek ke dalam tabel database melalui ajax
*/
$(document).ready(function() {

    function blink_text() {
        $('.blink').fadeOut(500);
        $('.blink').fadeIn(500);
    }
    setInterval(blink_text, 2500);

    $(document).on('click','#tujuan',function(){
        var tujuan          = $(this).val();    
        var tgl_registrasi  = $('#tgl_registrasi').val();
        var id_norm         = $('#nomor_rm').val(); 

        if(tujuan == 'daftar')
        {
            var d = 'tgl_registrasi='+tgl_registrasi+'&id_norm='+id_norm+'&aksi=cek-pendaftaran';
        }
        else if(tujuan == 'batal')
        {
            var d = 'tgl_registrasi='+tgl_registrasi+'&id_norm='+id_norm+'&aksi=batal';
        }
        else if(tujuan == 'edit-booking'){
            var d = 'tgl_registrasi='+tgl_registrasi+'&id_norm='+id_norm+'&aksi=edit-booking';
        }

        $.ajax({
            type : 'post',
            url : link_url+'/pendaftaran/includes/cek_pendaftaran.php',
            data : d,
            success : function(r)
            {
                var json_data = JSON.parse(r);

                if(json_data.s == 1)
                {
                    $('#notif').html(json_data.e);
                    //alert(json_data.e);
                    $('#RegAwal').modal();
                    
                    $('#tutup-pendaftaran').click( function () {
                        var btn_tutup = $(this).val();
                        $('#tujuan').prop('checked', false);
                    });
                }
                else
                {
                    window.location = json_data.e;
                }

            }
        });
        
    });
    
    $(document).on('change','#id_poli_tujuan',function(){
        var id_poli_tujuan = $(this).val();
        var tgl_reg  = $('#tgl_reg').val();
        $.ajax({
            type    : 'post',
            url     : link_url+'/pendaftaran/includes/cek_pendaftaran.php',
            data    : 'tgl_registrasi='+tgl_reg+'&kd_poli='+id_poli_tujuan+'&aksi=get-dokter',
            success : function(r)
            {
                $('#pilihan_dokter').html(r);
            }
        });
    });

    $(document).on('change','#kd_dokter',function(){
        var id = $(this).val();
        var tgl_reg  = $('#tgl_reg').val();
        var id_poli = $('#id_poli_tujuan').val();
        var no_rm = $('#no_rm').val();
        var form_cek = '<div class="form-group"><input type="checkbox" id="checkbox" name="checkbox" required><label>Saya menyetujui ketentuan dan persyaratan</label></div>';

        $.ajax({
            type : 'post',
            url : link_url+'/pendaftaran/includes/cek_pendaftaran.php',
            data : 'kd_dokter='+id+'&tgl_registrasi='+tgl_reg+'&kd_poli='+id_poli+'&no_rm='+no_rm+'&aksi=cek-info',
            success : function(r)
            {
                var json_reg = JSON.parse(r);

                $('#notif').html(json_reg.view);
                $('#mfooter').html( json_reg.dokter + ' &nbsp; ' +' <a href="'+json_reg.url+'" class="btn btn-warning">Kembali</a>');
                if(json_reg.status != 'kuota-tersedia')
                {
                    $('#RegAwal').modal();
                }
                else if(json_reg.status == 'kuota-tersedia')
                {
                    $('#cara_bayars').html(json_reg.dokter);
                }
            }
        });
    });

    $(document).on('change','#cara_bayar',function(){
        var cr_bayar        = $(this).val();
        var tgl_registrasi  = $('#tgl_reg').val();
        var kd_poli         = $('#id_poli_tujuan').val();
        var kd_dokter       = $('#kd_dokter').val();

        $.ajax({
            type : 'post',
            url : link_url+'/pendaftaran/includes/cek_pendaftaran.php',
             data : 'tgl_registrasi='+tgl_registrasi+'&kd_poli='+kd_poli+'&kd_dokter='+kd_dokter+'&cara_bayar='+cr_bayar+'&aksi=cabar',
             success : function(c)
             {
                var json_cabar = JSON.parse(c);
                if(json_cabar.status == 'penuh')
                {
                    $('#notif').html(json_cabar.view);
                    $('#mfooter').html(json_cabar.cabar + ' &nbsp; ' +'<a href="'+json_cabar.url+'" class="btn btn-warning">Kembali</a>');
                    $('#RegAwal').modal();
                }
             }
        });
    });
    
    $(document).on('click','#checkbox',function(){
        var st              = $(this).val();
        var tgl_registrasi  = $('#tgl_reg').val();
        var kd_poli         = $('#id_poli_tujuan').val();
        var kd_dokter       = $('#kd_dokter').val();
        var cr_bayar        = $('#cara_bayar').val();
        var no_rm           = $('#no_rm').val();

        if(st == 'proses')
        {
            $.ajax({
                type : 'post',
                url : link_url+'/pendaftaran/includes/cek_pendaftaran.php',
                data : 'no_rm='+no_rm+'&tgl_registrasi='+tgl_registrasi+'&kd_poli='+kd_poli+'&kd_dokter='+kd_dokter+'&cara_bayar='+cr_bayar+'&aksi=save',
                success : function(r){
                    var json_sukses = JSON.parse(r);
                    if(json_sukses.status == 'selesai')
                    {
                        //alert(json_sukses.url);
                        window.location = json_sukses.url;
                        $('#waktu_datang').html(json_sukses.waktu_kunjungan);
                    }
                    else if(json_sukses.status == 'gagal')
                    {
                        $('#notif').html(json_sukses.view);
                        $('#mfooter').html('<a href="'+json_sukses.url+'" class="btn btn-warning">Kembali</a>');
                        $('#RegAwal').modal();
                        //alert('sd');
                    }
                    else if(json_sukses.status == 'booking-penuh')
                    {
                        $('#notif').html(json_sukses.view);
                        $('#mfooter').html('<a href="'+json_sukses.url+'" class="btn btn-warning">Kembali</a>');
                        $('#form-setuju').html('<a href="'+json_sukses.url+'" class="btn btn-warning">Kembali</a>');
                        $('#RegAwal').modal();
                    }else if(json_sukses.status == 'cuti')
                    {
                        $('#notif').html(json_sukses.view);
                        $('#mfooter').html('<a href="'+json_sukses.url+'" class="btn btn-warning">Kembali</a>');
                        $('#form-setuju').html('<a href="'+json_sukses.url+'" class="btn btn-warning">Kembali</a>');
                        $('#RegAwal').modal();
                    }
                }
            });   
        }
    });

    $('.tr_mod').click( function() {
        var tr = $(this).parent();
        $.ajax({
            type : 'post',
            url : link_url+'/pendaftaran/includes/cek_pendaftaran.php',
            data : 'tgl_registrasi='+tr.data('tgl_periksa')+'&kd_poli='+tr.data('kd_poli')+'&kd_dokter='+tr.data('kd_dokter')+'&aksi=proses-batal',
            success : function(s){
                var json_batal = JSON.parse(s);
                window.location = json_batal.e;
            }
        });
    });

    $('#simpan-edit-booking').click(function(){

    });

    /*$('.tr_show').click( function() {
        var tr = $(this).parent();
        //alert(tr.data('idpesan'));
        $.ajax({
            type : 'post',
            url : link_url+'/pendaftaran/includes/cek_pendaftaran.php',
            data : 'idpesan='+tr.data('idpesan')+'&aksi=v-all',
            success : function(s){
                alert(s);
                //var json_batal = JSON.parse(s);
                //window.location = json_batal.e;
            }
        });
    });*/


    var url = window.location.pathname; //sets the variable "url" to the pathname of the current window
    var activePage = url.substring(url.lastIndexOf('/') + 1); //sets the variable "activePage" as the substring after the last "/" in the "url" variable
    if($('.active').length > 0){
        $('.active').removeClass('active');//remove current active element if there's
    }

    $('.menu li a').each(function () { //looks in each link item within the primary-nav list
        var linkPage = this.href.substring(this.href.lastIndexOf('/') + 1); //sets the variable "linkPage" as the substring of the url path in each &lt;a&gt;

        if (activePage == linkPage) { //compares the path of the current window to the path of the linked page in the nav item
            $(this).parent().addClass('active'); //if the above is true, add the "active" class to the parent of the &lt;a&gt; which is the &lt;li&gt; in the nav list
        }
    });


    $('#antrian').DataTable( {
        "ajax": "includes/antrian-pasien.php",
        "oLanguage": {
            "sProcessing":   "Sedang memproses...",
            "sLengthMenu":   "Tampilkan _MENU_ entri",
            "sZeroRecords":  "Tidak ditemukan data yang sesuai",
            "sInfo":         "Menampilkan _START_ sampai _END_ dari _TOTAL_ entri",
            "sInfoEmpty":    "Menampilkan 0 sampai 0 dari 0 entri",
            "sInfoFiltered": "(disaring dari _MAX_ entri keseluruhan)",
            "sInfoPostFix":  "",
            "sSearch":       "Cari:",
            "sUrl":          "",
            "oPaginate": {
                "sFirst":    "«",
                "sPrevious": "‹",
                "sNext":     "›",
                "sLast":     "»"
            }
        },
        "columns": [
            { "data": "Antrian" },
            { "data": "Status" },
            { "data": "Pasien" },
            { "data": "Dokter" }
        ]
    } );


    $('#riwayat_periksa').dataTable( {
		"scrollX": true,
		"pagingType": "full",
        "processing": true,
        "serverSide": true,
        "responsive": {
           "details": {
               "display": $.fn.dataTable.Responsive.display.modal( {
                    "header": function ( row ) {
                        var data = row.data();
                        return '<h3>Antrian Poliklinik</h3><br>';
                    }
                } ),
                "renderer": $.fn.dataTable.Responsive.renderer.tableAll()
            }
        },
        "order": [[ 0, "desc" ]],
        "ajax": "includes/riwayat-periksa.php",
        "oLanguage": {
            "sProcessing":   "Sedang memproses...",
            "sLengthMenu":   "Tampilkan _MENU_ entri",
            "sZeroRecords":  "Tidak ditemukan data yang sesuai",
            "sInfo":         "Menampilkan _START_ sampai _END_ dari _TOTAL_ entri",
            "sInfoEmpty":    "Menampilkan 0 sampai 0 dari 0 entri",
            "sInfoFiltered": "(disaring dari _MAX_ entri keseluruhan)",
            "sInfoPostFix":  "",
            "sSearch":       "Cari:",
            "sUrl":          "",
            "oPaginate": {
                "sFirst":    "«",
                "sPrevious": "‹",
                "sNext":     "›",
                "sLast":     "»"
            }
        } 
    } );

    $('#riwayat_booking').dataTable( {
        "scrollX": true,
        "pagingType": "full",
        "processing": true,
        "serverSide": true,
        "responsive": {
           "details": {
               "display": $.fn.dataTable.Responsive.display.modal( {
                    "header": function ( row ) {
                        var data = row.data();
                        return '<h3>Antrian Poliklinik</h3><br>';
                    }
                } ),
                "renderer": $.fn.dataTable.Responsive.renderer.tableAll()
            }
        },
        "order": [[ 0, "desc" ]],
        "ajax": "includes/riwayat-booking.php",
        "oLanguage": {
            "sProcessing":   "Sedang memproses...",
            "sLengthMenu":   "Tampilkan _MENU_ entri",
            "sZeroRecords":  "Tidak ditemukan data yang sesuai",
            "sInfo":         "Menampilkan _START_ sampai _END_ dari _TOTAL_ entri",
            "sInfoEmpty":    "Menampilkan 0 sampai 0 dari 0 entri",
            "sInfoFiltered": "(disaring dari _MAX_ entri keseluruhan)",
            "sInfoPostFix":  "",
            "sSearch":       "Cari:",
            "sUrl":          "",
            "oPaginate": {
                "sFirst":    "«",
                "sPrevious": "‹",
                "sNext":     "›",
                "sLast":     "»"
            }
        } 
    } );


    $('#jadwal_dokter').dataTable( {
		"scrollX": true,
		"pagingType": "full",
        "processing": true,
        "serverSide": true,
        "bFilter": false,
        "paging":   false,
        "ordering": false,
        "info":     false,
        "responsive": {
           "details": {
               "display": $.fn.dataTable.Responsive.display.modal( {
                    "header": function ( row ) {
                        var data = row.data();
                        return '<h3>Jadwal Dokter</h3><br>';
                    }
                } ),
                "renderer": $.fn.dataTable.Responsive.renderer.tableAll()
            }
        },
        "ajax": "includes/jadwal-dokter.php",
        "oLanguage": {
            "sProcessing":   "Sedang memproses...",
            "sLengthMenu":   "Tampilkan _MENU_ entri",
            "sZeroRecords":  "Tidak ditemukan data yang sesuai",
            "sInfo":         "Menampilkan _START_ sampai _END_ dari _TOTAL_ entri",
            "sInfoEmpty":    "Menampilkan 0 sampai 0 dari 0 entri",
            "sInfoFiltered": "(disaring dari _MAX_ entri keseluruhan)",
            "sInfoPostFix":  "",
            "sSearch":       "Cari:",
            "sUrl":          "",
            "oPaginate": {
                "sFirst":    "«",
                "sPrevious": "‹",
                "sNext":     "›",
                "sLast":     "»"
            }
        } 
    } );

    $('#informasi_kamar').dataTable( {
		"scrollX": true,
		"pagingType": "full",
        "processing": true,
        "serverSide": true,
        "bFilter": false,
        "paging":   false,
        "ordering": false,
        "info":     false,
        "responsive": {
           "details": {
               "display": $.fn.dataTable.Responsive.display.modal( {
                    "header": function ( row ) {
                        var data = row.data();
                        return '<h3>Informasi Kamar</h3><br>';
                    }
                } ),
                "renderer": $.fn.dataTable.Responsive.renderer.tableAll()
            }
        },
        "ajax": "includes/informasi-kamar.php",
        "oLanguage": {
            "sProcessing":   "Sedang memproses...",
            "sLengthMenu":   "Tampilkan _MENU_ entri",
            "sZeroRecords":  "Tidak ditemukan data yang sesuai",
            "sInfo":         "Menampilkan _START_ sampai _END_ dari _TOTAL_ entri",
            "sInfoEmpty":    "Menampilkan 0 sampai 0 dari 0 entri",
            "sInfoFiltered": "(disaring dari _MAX_ entri keseluruhan)",
            "sInfoPostFix":  "",
            "sSearch":       "Cari:",
            "sUrl":          "",
            "oPaginate": {
                "sFirst":    "«",
                "sPrevious": "‹",
                "sNext":     "›",
                "sLast":     "»"
            }
        } 
    } );

    $('#informasi_bangsal').dataTable( {
		"scrollX": true,
		"pagingType": "full",
        "processing": true,
        "serverSide": true,
        "bFilter": false,
        "paging":   false,
        "ordering": false,
        "info":     false,
        "responsive": {
           "details": {
               "display": $.fn.dataTable.Responsive.display.modal( {
                    "header": function ( row ) {
                        var data = row.data();
                        return '<h3>Informasi Bangsal</h3><br>';
                    }
                } ),
                "renderer": $.fn.dataTable.Responsive.renderer.tableAll()
            }
        },
        "ajax": "includes/informasi-bangsal.php",
        "oLanguage": {
            "sProcessing":   "Sedang memproses...",
            "sLengthMenu":   "Tampilkan _MENU_ entri",
            "sZeroRecords":  "Tidak ditemukan data yang sesuai",
            "sInfo":         "Menampilkan _START_ sampai _END_ dari _TOTAL_ entri",
            "sInfoEmpty":    "Menampilkan 0 sampai 0 dari 0 entri",
            "sInfoFiltered": "(disaring dari _MAX_ entri keseluruhan)",
            "sInfoPostFix":  "",
            "sSearch":       "Cari:",
            "sUrl":          "",
            "oPaginate": {
                "sFirst":    "«",
                "sPrevious": "‹",
                "sNext":     "›",
                "sLast":     "»"
            }
        } 
    } );

    //Textare auto growth
    autosize($('textarea.auto-growth'));

    $('.datepicker').bootstrapMaterialDatePicker({
        format: 'YYYY-MM-DD',
        clearButton: true,
        weekStart: 1,
        time: false
    });

    /*$('.count-to').countTo();

    $('.chat_head').click(function(){

    $('.chat_body').slideToggle('slow');

    // $('.user').slideToggle('slow');

    });*/



    $('.msg_head').click(function(){
        $('.msg_wrap').slideToggle('slow');
    });

    $(".close").click(function(){
        $('.msg_box').hide();
    });

    $("#kirim-pesan").click(function(){  
        $('.msg_wrap').show();
        $('.msg_box').show();
    });

    $('#kirim_pesan').click(function(){
        var judul = $('[name="judul"]').val();
        var isi = $('[name="isi"]').val();

        $.ajax({
            type : 'post',
            url  : link_url+'/pendaftaran/includes/cek_pendaftaran.php',
            data : 'judul='+judul+'&isi='+isi+'&aksi=kirim-pesan',
            success : function(r){
                var jres = JSON.parse(r);
                if(jres.status == 'sukses'){
                    $('.msg_box').hide();
                    $('#notif').html(jres.view);
                    $('#ShowPesan').modal();
                }   
            }
        });
    }); 
    /*$("#myInput").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#myTable tbody").filter(function() {
          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
    });*/

} );