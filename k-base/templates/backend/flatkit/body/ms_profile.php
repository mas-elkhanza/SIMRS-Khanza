<!-- ############ PAGE START-->

  <div class="item">
    <div class="item-bg">
      <img src="<?php echo getFotoVal($this->session->userdata('ID'));?>" class="blur opacity-3">
    </div>
    <div class="p-a-md">
      <div class="row m-t">
        <div class="col-sm-7">
          <a href class="pull-left m-r-md">
            <span class="avatar w-96">
              <img src="<?php echo getFotoVal($this->session->userdata('ID'));?>">
              <i class="on b-white"></i>
            </span>
          </a>
          <div class="clear m-b">
            <h3 class="m-0 m-b-xs"><?php echo $this->session->userdata('nm_lengkap'); ?></h3>
            <p class="text-muted"><span class="m-r"><?php echo getJabatanVal($this->session->userdata('id_jabatan'));?></span> <small><i class="fa fa-map-marker m-r-xs"></i>London, UK</small></p>
            <div class="block clearfix m-b">
              <a href="" class="btn btn-icon btn-social rounded white btn-sm">
                <i class="fa fa-facebook"></i>
                <i class="fa fa-facebook indigo"></i>
              </a>
              <a href="" class="btn btn-icon btn-social rounded white btn-sm">
                <i class="fa fa-twitter"></i>
                <i class="fa fa-twitter light-blue"></i>
              </a>
              <a href="" class="btn btn-icon btn-social rounded white btn-sm">
                <i class="fa fa-google-plus"></i>
                <i class="fa fa-google-plus red"></i>
              </a>
              <a href="" class="btn btn-icon btn-social rounded white btn-sm">
                <i class="fa fa-linkedin"></i>
                <i class="fa fa-linkedin cyan-600"></i>
              </a>
            </div>
            <a href class="btn btn-sm warn btn-rounded m-b">Follow</a>
          </div>
        </div>
        <div class="col-sm-5">
          <p class="text-md profile-status">I am feeling good!</p>
          <button class="btn btn-sm white" data-toggle="collapse" data-target="#editor">Edit</button>
          <div class="collapse box m-t-sm" id="editor">
            <textarea class="form-control no-border" rows="2" placeholder="Type something..."></textarea>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="dker p-x">
    <div class="row">
      <div class="col-sm-6 push-sm-6">
        <div class="p-y text-center text-sm-right">
          <a href class="inline p-x text-center">
            <span class="h4 block m-0">2k</span>
            <small class="text-xs text-muted">Followers</small>
          </a>
          <a href class="inline p-x b-l b-r text-center">
            <span class="h4 block m-0">250</span>
            <small class="text-xs text-muted">Following</small>
          </a>
          <a href class="inline p-x text-center">
            <span class="h4 block m-0">89</span>
            <small class="text-xs text-muted">Activities</small>
          </a>
        </div>
      </div>
      <div class="col-sm-6 pull-sm-6">
        <div class="p-y-md clearfix nav-active-primary">
          <ul class="nav nav-pills nav-sm">
            <li class="nav-item active">
              <a class="nav-link" href data-toggle="tab" data-target="#tab_1">Activities</a>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <div class="padding">
    <div class="row">
      <div class="col-sm-8 col-lg-9">
        <div class="tab-content">      
          <div class="tab-pane p-v-sm active" id="tab_1">
            <div class="streamline b-l m-b m-l">
              <div class="sl-item">
                <div class="sl-left">
                  <img src="../assets/images/a0.jpg" class="img-circle">
                </div>
                <div class="sl-content">
                  <div class="sl-date text-muted">2 minutes ago</div>
                  <div class="sl-author">
                    <a href>Peter Joo</a>
                  </div>
                  <div>
                    <p>Check your Internet connection</p>
                  </div>
                  <div class="sl-footer">
                    <a href ui-toggle-class class="btn white btn-xs">
                      <i class="fa fa-fw fa-star-o text-muted inline"></i>
                      <i class="fa fa-fw fa-star text-danger none"></i>
                    </a>
                    <a href class="btn white btn-xs" data-toggle="collapse" data-target="#reply-1">
                      <i class="fa fa-fw fa-mail-reply text-muted"></i>
                    </a>
                  </div>
                  <div class="box collapse m-0" id="reply-1">
                    <form>
                      <textarea class="form-control no-border" rows="3" placeholder="Type something..."></textarea>
                    </form>
                    <div class="box-footer clearfix">
                      <button class="btn btn-info pull-right btn-sm">Post</button>
                      <ul class="nav nav-pills nav-sm">
                        <li class="nav-item"><a class="nav-link" href><i class="fa fa-camera text-muted"></i></a></li>
                        <li class="nav-item"><a class="nav-link" href><i class="fa fa-video-camera text-muted"></i></a></li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-sm-4 col-lg-3">
        <div>
          <div class="box">
              <div class="box-header">
                <h3>User Online</h3>
              </div>
              <div class="box-divider m-0"></div>
              <ul class="list no-border p-b">
                <?php
                if($jum > 0)
                {
                  foreach ($r as $key => $rdw) {
                    echo '<li class="list-item">';
                      echo '<a herf class="list-left">';
                        echo '<span class="w-40 avatar">';
                        echo '<img src="'.base_url($rdw->foto).'" alt="'.$rdw->nm_lengkap.'">';
                        echo '<i class="on b-white bottom"></i>';
                        echo '</span>';
                      echo '</a>';
                      echo '<div class="list-body">';
                        echo '<div><a href="'.base_url('ms_profile_view/action/v/'). $rdw->ID .'">'.$rdw->nm_lengkap.'</a></div>';
                      echo '<small class="text-muted text-ellipsis">'.getLevelVal($rdw->id_level).', '.getJabatanVal($rdw->id_jabatan).'</small>';
                      echo '</div>';
                    echo '</li>';
                  }
                }
                else
                {
                  echo '<li class="list-item">';
                     echo '<div class="list-body">';
                        echo '<div>Tidak ada yang online</div>';
                      echo '</div>';
                  echo '</li>';
                }  
                
                ?>
                    
                    
                  
              </ul>
          </div>
        
        </div>
      </div>
    </div>
  </div>

<!-- ############ PAGE END-->
<script type="text/javascript">
  if (!checkPrivilege('<?php echo $this->session->userdata('id_level');?>','ms_profile_view')) {
            history.go(-1);
        }
</script>