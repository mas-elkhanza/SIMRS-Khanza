<?php 
 global $SConfig;
?>
 <!-- ############ LAYOUT START-->
  <div class="center-block w-xxl w-auto-xs p-y-md">
    <div class="navbar">
      <div class="pull-center">
        <!-- brand -->
        <a class="navbar-brand">
          <img src="<?php echo base_url('assets/assets_flatkit/images/logo.png');?>" alt="." class="hide">
          <span class="hidden-folded inline"><?php echo $SConfig->_sitename;?></span>
        </a>
        <!-- / brand -->
      </div>
    </div>
    <div class="p-a-md box-color r box-shadow-z1 text-color m-a">
      <div class="m-b text-sm">
        Silahkan masuk dengan akun anda ke dalam K-Base
      </div>
      <form method="post" action="<?php echo base_url('auth/action/checking');?>">
        <div class="md-form-group float-label">
          <input type="text" name="username" class="md-input" required>
          <label>Email</label>
        </div>
        <div class="md-form-group float-label">
          <input type="password" name="password" class="md-input" required>
          <label>Password</label>
        </div>      
        <div class="m-b-md">        
          <label class="md-check">
            <input type="checkbox" value="Y" name="remember"><i class="primary"></i> Keep me signed in
          </label>
        </div>
        <button type="submit" class="btn primary btn-block p-x-md">Sign in</button>
      </form>
    </div>

    <!-- <div class="p-v-lg text-center">
      <div class="m-b"><a ui-sref="access.forgot-password" href="forgot-password.html" class="text-primary _600">Forgot password?</a></div>
      <div>Do not have an account? <a ui-sref="access.signup" href="signup.html" class="text-primary _600">Sign up</a></div>
    </div> -->
  </div>

<!-- ############ LAYOUT END -->