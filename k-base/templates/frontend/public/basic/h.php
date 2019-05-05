<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title><?php echo (@$title) ? $title : 'K-Base'; ?></title>
  <meta name="description" content="<?php echo (@$description) ? $description : ''; ?>" />
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, minimal-ui" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge">

  <!-- for ios 7 style, multi-resolution icon of 152x152 -->
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-barstyle" content="black-translucent">
  <link rel="apple-touch-icon" href="">
  <meta name="apple-mobile-web-app-title" content="Flatkit">
  <!-- for Chrome on Android, multi-resolution icon of 196x196 -->
  <meta name="mobile-web-app-capable" content="yes">
  <link rel="shortcut icon" sizes="196x196" href="">
  
  <!-- style -->
  <link rel="stylesheet" href="<?php echo base_url('assets/assets_flatkit/animate.css/animate.min.css');?>" type="text/css" />
  <link rel="stylesheet" href="<?php echo base_url('assets/assets_flatkit/glyphicons/glyphicons.css');?>" type="text/css" />
  <link rel="stylesheet" href="<?php echo base_url('assets/assets_flatkit/font-awesome/css/font-awesome.min.css');?>" type="text/css" />
  <link rel="stylesheet" href="<?php echo base_url('assets/assets_flatkit/material-design-icons/material-design-icons.css');?>" type="text/css" />

  <link rel="stylesheet" href="<?php echo base_url('assets/assets_flatkit/bootstrap/dist/css/bootstrap.min.css');?>" type="text/css" />
  <!-- build:css ../assets/styles/app.min.css -->
  <link rel="stylesheet" href="<?php echo base_url('assets/assets_flatkit/styles/app.css');?>" type="text/css" />
  <!-- endbuild -->
  <link rel="stylesheet" href="<?php echo base_url('assets/assets_flatkit/styles/font.css');?>" type="text/css" />
</head>
<body>
  <div class="app" id="app">