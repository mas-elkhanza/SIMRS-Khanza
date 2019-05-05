function editAkun() {
    $('#modalAuthUpdate').modal();
}

$(document).ready(function () {
    $('.alert').delay(2500).slideUp(500, function () {
        $(this).remove();
    });

    if ($('.select2').length > 0) {
        $('.select2').select2();
    }
});

function convertRemToPixels(rem) {
    return parseFloat(rem) * parseFloat(getComputedStyle(document.documentElement).fontSize);
}

function convertPixelToRem(px) {
    return parseFloat(px) / parseFloat(getComputedStyle(document.documentElement).fontSize);
}

function rgbToHex(rgb) {
    var r, g, b;

    rgb = rgb.toString().replace('rgb(', '');
    rgb = rgb.toString().replace(')', '');
    rgb = rgb.toString().replace(' ', '');
    var x = rgb.split(',');

    r = parseInt(x[0]);
    g = parseInt(x[1]);
    b = parseInt(x[2]);

    return "#" + ((1 << 24) + (r << 16) + (g << 8) + b).toString(16).slice(1);
}

function checkPrivilege(aksesFull, aksesRequest) {
    var aksesName = '';

    switch (aksesRequest) {
        case "desktop_view":
            aksesName="View Dashboard";
        break;
        case "ms_module_view":
            aksesName="View List Module";
        break;
        case "ms_module_add":
            aksesName="Add Module";
        break;
        case "ms_module_edit":
            aksesName="Edit Module";
        break;
        case "ms_module_delete":
            aksesName="Delete Module";
        break;
        case "ms_level_view":
            aksesName="View List Level";
        break;
        case "ms_level_add":
            aksesName="Add Level";
        break;
        case "ms_level_edit":
            aksesName="Edit Level";
        break;
        case "ms_level_delete":
            aksesName="Delete Level";
        break;
        case "ms_user_view":
            aksesName="View List User";
        break;
        case "ms_user_add":
            aksesName="Add User";
        break;
        case "ms_user_edit":
            aksesName="Edit User";
        break;
        case "ms_user_delete":
            aksesName="Delete User";
        break;
        case "ms_user_upload":
            aksesName="Upload User";
        break;
        case "ms_user_download":
            aksesName="Download User";
        break;
        case "ms_user_log":
            aksesName="User Info Login";
        break;
        case "ad_cuti_view":
            aksesName="View Cuti Dokter";
        break;
        case "ad_cuti_add":
            aksesName="Add Cuti Dokter";
        break;
        case "ad_cuti_edit":
            aksesName="Edit Cuti Dokter";
        break;
        case "ad_cuti_delete":
            aksesName="Delete Cuti Dokter";
        break;
        case "ad_cuti_upload":
            aksesName="Upload Cuti Dokter";
        break;
        case "ad_cuti_download":
            aksesName="Download Cuti Dokter";
        break;
        case "ad_cabar_view":
            aksesName="View List Cara Bayar";
        break;
        case "ad_cabar_add":
            aksesName="Add Cara Bayar";
        break;
        case "ad_cabar_edit":
            aksesName="Edit Cara Bayar";
        break;
        case "ad_cabar_delete":
            aksesName="Delete Cara Bayar";
        break;
        case "ad_cabar_upload":
            aksesName="Upload Cara Bayar";
        break;
        case "ad_pb_view":
            aksesName="View List Kelas";
        break;
        case "ad_pb_add":
            aksesName="Add Kelas";
        break;
        case "ad_pb_edit":
            aksesName="Edit Kelas";
        break;
        case "ad_pb_delete":
            aksesName="Delete Kelas";
        break;
        case "ad_ld_view":
            aksesName="View List Dokter";
        break;
        case "ad_ld_add":
            aksesName="Add Dokter";
        break;
        case "ad_ld_edit":
            aksesName="Edit Dokter";
        break;
        case "ad_ld_delete":
            aksesName="Delete Dokter";
        break;
        case "st_mutu_view":
            aksesName="View Statistik Indikasi Mutu";
        break;
        case "st_mutu_search":
            aksesName="Analisis Indikasi Mutu";
        break;
        case "st_mutu_export":
            aksesName="Export Data";
        break;
        case "st_fungsi_view":
            aksesName="View Statistik Status Fungsional";
        break;
        case "st_fungsi_search":
            aksesName="Analisis Status Fungsional";
        break;
        case "st_fungsi_export":
            aksesName="Export Status Fungsional";
        break;
        case "st_nyer_view":
            aksesName="View Statistik Nyeri";
        break;
        case "st_nyer_search":
            aksesName="Analisis Nyeri";
        break;
        case "st_nyer_export":
            aksesName="Export Nyeri";
        break;
        case "st_resjat_view":
            aksesName="View Statistik Resiko Jatuh";
        break;
        case "st_resjat_search":
            aksesName="Analisis Resiko Jatuh";
        break;
        case "st_resjat_export":
            aksesName="Export Resiko Jatuh";
        break;
        case "st_cemas_view":
            aksesName="View Statistik Cemas";
        break;
        case "st_cemas_search":
            aksesName="Analisis Cemas";
        break;
        case "st_cemas_export":
            aksesName="Export Cemas";
        break;
        case "st_edukasi_view":
            aksesName="View Statistik Edukasi";
        break;
        case "st_edukasi_search":
            aksesName="Analisis Edukasi";
        break;
        case "st_edukasi_export":
            aksesName="Export Edukasi";
        break;
        case "ps_psm_view":
            aksesName="View";
        break;
        case "ps_psm_add":
            aksesName="Add";
        break;
        case "ps_psm_edit":
            aksesName="Edit";
        break;
        case "ps_psm_delete":
            aksesName="Delete";
        break;
        case "ps_bc_view":
            aksesName="View";
        break;
        case "ps_bc_add":
            aksesName="Add";
        break;
        case "ps_bc_edit":
            aksesName="Edit";
        break;
        case "ps_info_view":
            aksesName="View List Pasien";
        break; 
        case "ps_info_add":
            aksesName="View List Pasien";
        break;
        case "ps_info_edit":
            aksesName="Search Pasien";
        break;
        case "ps_info_delete":
            aksesName="Proses Pasien";
        break;
    }

    if (aksesFull == '' && aksesName == '') {
        alert('Maaf sistem tidak bisa memproses permintaan anda!...');
        return false;
    } 
    else if(aksesFull == '' && aksesName!=''){
        alert('Silahkan hubungi administrator!...');
        return false;
    } 
    else if(aksesFull !='' && aksesName == '') {
        alert('Anda tidak bisa mengakses fitur ini, karena tidak memiliki aksesName');
        return false;
    }
    else
    {
        //alert(aksesName);
        return true;
    }
}

function round(value, precision, mode) {
    var m, f, isHalf, sgn // helper variables
    // making sure precision is integer
    precision |= 0
    m = Math.pow(10, precision)
    value *= m
    // sign of the number
    sgn = (value > 0) | -(value < 0)
    isHalf = value % 1 === 0.5 * sgn
    f = Math.floor(value)
    if (isHalf) {
        switch (mode) {
            case 'PHP_ROUND_HALF_DOWN':
                // rounds .5 toward zero
                value = f + (sgn < 0)
                break
            case 'PHP_ROUND_HALF_EVEN':
                // rouds .5 towards the next even integer
                value = f + (f % 2 * sgn)
                break
            case 'PHP_ROUND_HALF_ODD':
                // rounds .5 towards the next odd integer
                value = f + !(f % 2)
                break
            default:
                // rounds .5 away from zero
                value = f + (sgn > 0)
        }
    }
    return (isHalf ? value : Math.round(value)) / m
}