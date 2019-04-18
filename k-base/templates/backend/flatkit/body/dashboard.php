
      <!-- ############ PAGE START-->
      <div class="row no-gutter b-b white">
        <div class="col-md-12">

          <div class="box row-col" style="min-height:450px">
              <div class="box-header b-b">
                <strong>Data aktivitas anda terkahir.</strong>
              </div>
              <div class="row-row dker">
                <div class="row-body">
                  <div class="row-inner">
                    <div class="p-a-md">
                      <table class="table table-striped table-hover" id="tblHist">
                            <thead>
                                <tr><th>Action</th><th>Date Time</th></tr>
                            </thead>
                            <tbody>
                                <?php
                                if (count($AcList) > 0) {
                                    foreach ($AcList as $hi) {
                                      //print_r($hi);
                                        echo '<tr>';
                                        echo '<td>' . strip_tags($hi->activity) . '</td>';
                                        echo '<td>' . date('d F Y H:i:s', strtotime($hi->activity_date)) . '</td>';
                                        echo '</tr>';
                                    }
                                } else {
                                    echo '<tr><td colspan="3" style="text-align:center">There\'s no history recorded</td></tr>';
                                }
                                ?>
                            </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
            </div>
        </div>
      </div>