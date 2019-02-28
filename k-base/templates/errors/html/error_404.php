<?php global $SConfig; 
//echo $SConfig->_site_url;
?>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title>E-Mutu</title>
  <meta name="description" content="Admin, Dashboard, Bootstrap, Bootstrap 4, Angular, AngularJS" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimal-ui" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">

  <!-- for ios 7 style, multi-resolution icon of 152x152 -->
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-barstyle" content="black-translucent">
  <link rel="apple-touch-icon" href="<?php echo $SConfig->_site_url.'assets/assets_flatkit/images/logo.png';?>">
  <meta name="apple-mobile-web-app-title" content="Flatkit">
  <!-- for Chrome on Android, multi-resolution icon of 196x196 -->
  <meta name="mobile-web-app-capable" content="yes">
  <link rel="shortcut icon" sizes="196x196" href="<?php echo $SConfig->_site_url.'assets/assets_flatkit/images/logo.png';?>">
  
  <!-- style -->
  <link rel="stylesheet" href="<?php echo $SConfig->_site_url.'assets/assets_flatkit/animate.css/animate.min.css';?>" type="text/css" />
  <link rel="stylesheet" href="<?php echo $SConfig->_site_url.'assets/assets_flatkit/glyphicons/glyphicons.css';?>" type="text/css" />
  <link rel="stylesheet" href="<?php echo $SConfig->_site_url.'assets/assets_flatkit/font-awesome/css/font-awesome.min.css';?>" type="text/css" />
  <link rel="stylesheet" href="<?php echo $SConfig->_site_url.'assets/assets_flatkit/material-design-icons/material-design-icons.css';?>" type="text/css" />

  <link rel="stylesheet" href="<?php echo $SConfig->_site_url.'assets/assets_flatkit/bootstrap/dist/css/bootstrap.min.css';?>" type="text/css" />
  <!-- build:css ../assets/styles/app.min.css -->
  <link rel="stylesheet" href="<?php echo $SConfig->_site_url.'assets/assets_flatkit/styles/app.css';?>" type="text/css" />
  <!-- endbuild -->
  <link rel="stylesheet" href="<?php echo $SConfig->_site_url.'assets/assets_flatkit/styles/font.css';?>" type="text/css" />
</head>
<body>
  <div class="app" id="app">

<!-- ############ LAYOUT START-->
<div class="app-footer white">
  <div ui-include="'../views/blocks/footer.html'"></div>
</div>
<div class="app-body amber bg-auto w-full">
  <div class="text-center pos-rlt p-y-md">
    <h1 class="text-shadow m-0 text-white text-4x">
      <span class="text-2x font-bold block m-t-lg">404</span>
    </h1>
    <h2 class="h1 m-y-lg text-black">OOPS!</h2>
    <p class="h5 m-y-lg text-u-c font-bold text-black">Sorry! the page you are looking for doesn't exist.</p>
    <a ui-sref="app.dashboard" href="<?php echo $SConfig->_site_url; ?>" class="md-btn amber-700 md-raised p-x-md">
      <span class="text-white">Go to the home page</span>
    </a>
  </div>
</div>

<!-- ############ LAYOUT END-->

  </div>
<!-- build:js scripts/app.html.js -->
<script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/libs/jquery/jquery/dist/jquery.js';?>"></script>
<!-- Bootstrap -->
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/libs/jquery/tether/dist/js/tether.min.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/libs/jquery/bootstrap/dist/js/bootstrap.js';?>"></script>
<!-- core -->
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/libs/jquery/underscore/underscore-min.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/libs/jquery/jQuery-Storage-API/jquery.storageapi.min.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/libs/jquery/PACE/pace.min.js';?>"></script>

  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/scripts/config.lazyload.js';?>"></script>

  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/scripts/palette.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/scripts/ui-load.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/scripts/ui-jp.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/scripts/ui-include.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/scripts/ui-device.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/scripts/ui-form.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/scripts/ui-nav.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/scripts/ui-screenfull.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/scripts/ui-scroll-to.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/scripts/ui-toggle-class.js';?>"></script>

  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/scripts/app.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/core.js?u=3';?>"></script>

  <!-- ajax -->
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/libs/jquery/jquery-pjax/jquery.pjax.js';?>"></script>
  <script src="<?php echo $SConfig->_site_url.'assets/assets_flatkit/scripts/ajax.js';?>"></script>
<!-- endbuild -->
</body>
</html>
