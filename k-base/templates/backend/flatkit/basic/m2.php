  <?php  
  global $SConfig;
  ?>
  <!-- aside -->
  <div id="aside" class="app-aside modal fade nav-expand">
    <div class="left navside black dk" layout="column">
      <div class="navbar no-radius">
        <!-- brand -->
        <a class="navbar-brand">
          <img src="<?php echo base_url('assets/assets_flatkit/images/logo.png');?>" alt="." class="hide">
          <span class="hidden-folded inline"><?php echo $SConfig->_sitename; ?></span>
        </a>
        <!-- / brand -->
      </div>
      <div flex-no-shrink>
        <div class="nav-fold">
          <a href="#/app/page/profile">
             <span class="pull-left" id="foto">
                
              </span>
              <span class="clear hidden-folded p-x">
                <span class="block _500"><?php echo $this->session->userdata('username');?></span>
                <small class="block text-muted" id="online"><i class="fa fa-circle text-success m-r-sm"></i></small>
              </span>
          </a>
        </div>

      </div>
      <div flex class="hide-scroll">
        <nav class="scroll nav-stacked nav-active-primary">
            <ul class="nav" ui-nav>                                
              <li class="nav-header hidden-folded">
                <small class="text-muted">Main Menu</small>
              </li>
              <?php 
              echo akses_link();
              ?>          
            </ul>
        </nav>
      </div>
        <nav ui-nav>
          <ul class="nav">
            <li><div class="b-b b m-t-sm"></div></li>
            <li class="no-bg">
              <a href="<?php echo base_url('Auth/action/logout');?>">
                <span class="nav-icon">
                 <i class="material-icons">&#xe8ac;</i>
                </span>
                <span class="nav-text">Logout</span>
              </a>
            </li>
          </ul>
        </nav>
    </div>
  </div>
  <!-- / aside -->
  
  
  <!-- content -->
  <div id="content" class="app-content box-shadow-z1" role="main">