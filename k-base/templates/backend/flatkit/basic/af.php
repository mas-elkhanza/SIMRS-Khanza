  <div class="app-footer">
      <div class="p-2 text-xs">
        <div class="pull-right text-muted py-1">
          &copy; Copyright <strong>RSUD Kota Depok - 2018</strong> <span class="hidden-xs-down"> | <?php global $SConfig; echo $SConfig->_sitename; ?> v1.0</span>
          <a ui-scroll-to="content"><i class="fa fa-long-arrow-up p-x-sm"></i></a>
        </div>
        <div class="nav">
          
        </div>
      </div>
    </div>

  <div ui-view class="app-body" id="view">
  <?php 
  echo calrt();
  $this->session->unset_userdata('msg');
  $this->session->unset_userdata('error');
  
  ?>