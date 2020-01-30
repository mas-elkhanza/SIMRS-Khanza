<?php
/* 
 * Plugin Name: WP Damanhuri
 * Plugin URI: http://basoro.org/
 * Description: A simple Patient Online Registration for Yaski Network Hospital.
 * Version: 0.1
 * Author: Basoro
 * Author URI: http://basoro.org/
 * License: MIT 
 */

$PLUGIN_DIR = plugin_dir_path(__FILE__);
$PLUGIN_URL = plugins_url(__FILE__);

register_activation_hook( __FILE__, 'insert_page' );

function insert_page()
{
  // Create post object
  $pasien = array(
    'post_title'    => 'Pasien Online',
    'post_name'     => 'pasien',
    'post_status'   => 'publish',
    'post_author'   => get_current_user_id(),
    'post_type'     => 'page',
  );

  // Insert the post into the database
  if(!get_page_by_title('Pendaftaran Pasien Online')) :
    wp_insert_post( $pasien, '' );
  endif; 
  $pasien_postid = get_page_by_path('pasien', '', 'page');
  $parent_id = $pasien_postid->ID;
  
  // Create post object
  $pendaftaran = array(
    'post_title'    => 'Pendaftaran Antrian',
    'post_name'     => 'pendaftaran',
    'post_status'   => 'publish',
    'post_author'   => get_current_user_id(),
    'post_type'     => 'page',
    'post_parent'   => $parent_id,
  );

  // Insert the post into the database
  if(!get_page_by_title('Pendaftaran Antrian')) :
    wp_insert_post( $pendaftaran, '' );
  endif; 
  // Create post object
  $informasi_kamar = array(
    'post_title'    => 'Informasi Kamar',
    'post_name'     => 'informasi-kamar',
    'post_status'   => 'publish',
    'post_author'   => get_current_user_id(),
    'post_type'     => 'page',
    'post_parent'   => $parent_id,
  );

  // Insert the post into the database
  if(!get_page_by_title('Informasi Kamar')) :
    wp_insert_post( $informasi_kamar, '' );
  endif; 
  // Create post object
  $jadwal_dokter = array(
    'post_title'    => 'Jadwal Dokter',
    'post_name'     => 'jadwal-dokter',
    'post_status'   => 'publish',
    'post_author'   => get_current_user_id(),
    'post_type'     => 'page',
    'post_parent'   => $parent_id,
  );

  // Insert the post into the database
  if(!get_page_by_title('Jadwal Dokter')) :
    wp_insert_post( $jadwal_dokter, '' );
  endif; 
  // Create post object
  $riwayat_periksa = array(
    'post_title'    => 'Riwayat Periksa',
    'post_name'     => 'riwayat-periksa',
    'post_status'   => 'publish',
    'post_author'   => get_current_user_id(),
    'post_type'     => 'page',
    'post_parent'   => $parent_id,
  );

  // Insert the post into the database
  if(!get_page_by_title('Riwayat Periksa')) :
    wp_insert_post( $riwayat_periksa, '' );
  endif; 
  // Create post object
  $login = array(
    'post_title'    => 'Login',
    'post_name'     => 'login',
    'post_status'   => 'publish',
    'post_author'   => get_current_user_id(),
    'post_type'     => 'page',
    'post_parent'   => $parent_id,
  );

  // Insert the post into the database
  if(!get_page_by_title('Login')) :
    wp_insert_post( $login, '' );
  endif; 
}

add_filter( 'page_template', 'wp_damanhuri_pages' );
function wp_damanhuri_pages( $page_template )
{
  if ( is_page( 'pasien' ) ) {
    $page_template = dirname( __FILE__ ) . '/index.php';
  } 
  if ( is_page( 'pendaftaran' ) ) {
    $page_template = dirname( __FILE__ ) . '/pendaftaran.php';
  } 
  if ( is_page( 'riwayat-periksa' ) ) {
    $page_template = dirname( __FILE__ ) . '/riwayat-periksa.php';
  }
  if ( is_page( 'jadwal-dokter' ) ) {
    $page_template = dirname( __FILE__ ) . '/jadwal-dokter.php';
  }
  if ( is_page( 'informasi-kamar' ) ) {
    $page_template = dirname( __FILE__ ) . '/informasi-kamar.php';
  }
  if ( is_page( 'login' ) ) {
    $page_template = dirname( __FILE__ ) . '/login.php';
  }
  return $page_template;
}

function wp_damanhuri_register_options_page() {
  add_options_page('Pengaturan Database SIK', 'SIK Setting', 'manage_options', 'wp_damanhuri', 'wp_damanhuri_options_page');
}
add_action('admin_menu', 'wp_damanhuri_register_options_page');

function wp_damanhuri_options_page()
{
?>
	    <div class="wrap">
	    <h1>Pengaturan Database SIK</h1>
        <p>Silakan lakukan penyesuaian pengaturan database SIMRS Khanza.</p>
	    <form method="post" action="options.php">
	        <?php
	            settings_fields("section");
	            do_settings_sections("wp-damanhuri-options");      
	            submit_button(); 
	        ?>          
	    </form>
		</div>
<?php
} ?>
