    <div class="app-header white box-shadow">
        <div class="navbar navbar-toggleable-sm flex-row align-items-center">
            <!-- Open side - Naviation on mobile -->
            <a data-toggle="modal" data-target="#aside" class="hidden-lg-up mr-3">
              <i class="material-icons">&#xe5d2;</i>
            </a>
            <!-- / -->
        
            <!-- Page title - Bind to $state's title -->
            <div class="mb-0 h5 no-wrap" ng-bind="$state.current.data.title" id="pageTitle"></div>
        
            <!-- navbar collapse -->
            <div class="collapse navbar-collapse" id="collapse">
              <!-- link and dropdown -->
              <ul class="nav navbar-nav mr-auto">
                <li class="nav-item dropdown">
                  <a class="nav-link" href="<?php echo base_url('dashboard');?>">
                    <i class="fa fa-fw fa-dashboard text-muted"></i>
                    <span> <?php global $SConfig; echo $SConfig->_sitename; ?> | <?php  echo ($title) ? $title : ''; ?>  </span>
                  </a>
                  
                </li>
              </ul>
              <!-- / -->
            </div>
            <!-- / navbar collapse -->
        
            <!-- navbar right -->
            <ul class="nav navbar-nav ml-auto flex-row">
              <li class="nav-item dropdown pos-stc-xs">
                <a class="nav-link mr-2" href data-toggle="dropdown">
                  <i class="material-icons">&#xe7f5;</i>
                  <span class="label label-sm up warn">3</span>
                </a>
                <!-- dropdown -->
                
                <!-- / dropdown -->
              </li>
              <li class="nav-item dropdown">
                <a class="nav-link p-0 clear" href="#" data-toggle="dropdown">
                  <span class="avatar w-32">
                    <img src="<?php echo getFotoVal($this->session->userdata('ID'));?>" alt="...">
                    <i class="on b-white bottom"></i>
                  </span>
                </a>
                <div class="dropdown-menu dropdown-menu-overlay pull-right">
                
                <a class="dropdown-item" ui-sref="access.signin" href="<?php echo base_url('Auth/action/logout'); ?>">Sign out</a>
              </div>

              </li>
              <li class="nav-item hidden-md-up">
                <a class="nav-link pl-2" data-toggle="collapse" data-target="#collapse">
                  <i class="material-icons">&#xe5d4;</i>
                </a>
              <li>
            </ul>
            <!-- / navbar right -->
        </div>
    </div>  