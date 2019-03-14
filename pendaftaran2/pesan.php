<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : index.php
* Description : Pengaduan page
* Licence under GPL
***/

include_once ('layout/header.php');

  $m = 'pesan.php?';
  $v = isset($_GET['v']) ? $_GET['v'] : '';
  $d = isset($_GET['d']) ? $_GET['d'] : '';
  $i = isset($_GET['i']) ? $_GET['i'] : '';
  $l = isset($_GET['l']) && $_GET['l'] ? $_GET['l'] : 7;
  $p = isset($_GET['p']) && $_GET['p'] ? $_GET['p'] - 1 : 0;

  $pq   = $p * $l;
  $no   = ($p * $l) + 1;

  if($d)
  {
    $wh = ' and judul like \'%'.$d.'%\'';
  }

  $gcount = fetch_assoc(query('SELECT count(*) as total FROM pesan WHERE pengirim=\''.$_SESSION['username'].'\' and status=1 '.$wh.''));
  $gpesan = query('SELECT * FROM pesan WHERE pengirim=\''.$_SESSION['username'].'\' and status=1 '.$wh.' group by idpesan LIMIT '.$l.' OFFSET '.$pq.'');
?>

    <section class="content">
        <div class="container-fluid">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
              <div class="block-header">
                <h2>PENGADUAN PASIEN<h2>
              </div>
            </div>
            <!-- Basic Examples -->
            <div class="row clearfix">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12 list-group-item">
                    <div class="col-md-3 col-sm-12 col-xs-12" style="height: 100%;">
                        <div class="list-group-item">
                            <div class="row clearfix">
                                <div class="body">
                                    <div class="col-xs-12 col-sm-12 col-md-12">
                                       <ul class="list-group">
                                          <li class="list-group-item btn btn-success" id="kirim-pesan">Tulis Pesan</li>
                                          <li class="list-group-item"><a href="pesan.php">Pesan</a> <span class="badge"><?php echo $gcount['total']; ?></span></li>
                                          <li class="list-group-item"><a href="pesan.php?v=pb">Pesan Brodcast</a></li> 
                                        </ul>
                                    </div>
                                </div>
                            </div>
                       </div> 
                    </div>

                    <div class="col-md-9 col-sm-12 col-xs-12">
                      <?php if($v=='search' || $v==''){?>
                        <div class="list-group-item col-sm-12 col-md-12 col-xs-12" style="margin:0;">
                          <input type="text" class="form-control input-sm" name="cari" placeholder="Cari" value="<?php echo ($d) ? $d : ''; ?>" onkeyup="event.keyCode == 13 ? window.location = 'pesan.php?v=search&d=' + this.value : '';">
                        </div>
                        <div class="list-group-item col-sm-12 col-md-12 col-xs-12">
                          <div class="row clearfix">
                            <?php
                            $p = $p ? $p + 1 : 1;

                            $paging = array();
                            for ($i = 1; $i <= ceil($gcount['total'] / $l); $i++) {
                                $paging[$i] = $i;
                            }
                            ?>
                            <!-- <div class="body"> -->
                              <div class="col-sm-12 col-md-4 col-xs-12">
                                <?php echo 'Limit : ' . htmlSelectFromArray($limit, 'name="l" class="col-md-9" onchange="window.location=\'' . $m . 'v=search&d=' . $d . '&p=0&l=\'+this.value"', true, $l); ?>
                              </div>
                              <div class="col-sm-12 col-md-4 col-xs-12">
                                
                              </div>
                              <div class="col-sm-12 col-md-4 col-xs-12">
                                halaman : <?php echo htmlSelectFromArray($paging, 'name="p" class="col-md-8" onchange="window.location=\'' . $m . 'v=search&d=' . $d . '&l=0&p=\'+this.value"', true, $p); ?>
                              </div>
                            <!-- </div> -->
                          </div>
                        </div>
                      <?php } ?>

                        <div class="list-group-item col-sm-12 col-md-12 col-xs-12">
                          <div class="row clearfix">
                              <div class="body table-responsive">
                                    <?php                                      
                                    switch ($v) {
                                      case 'search':
                                        echo '<div class="col-sm-12 col-md-12 col-xs-12" style="margin:0 0 10px 0;">';
                                        echo '</div>';

                                        echo '<div class="col-sm-12 col-md-12 col-xs-12">';
                                        echo '<table id="myTable" class="table table-bordered table-striped table-hover display nowrap" width="100%">';
                                            echo '<thead>';
                                              echo '<tr>';
                                                echo '<th class="col-md-1">No</th>';
                                                echo '<th class="col-md-1">Percakapan</th>';
                                                echo '<th class="col-md-10">Judul</th>';
                                                echo '<th class="col-md-1">Status</th>';
                                              echo '</tr>';
                                            echo '</thead>';

                                            echo '<tbody>';
                                            while ($r = fetch_array($gpesan)) {
                                              echo '<tr data-idpesan="'.$r['idpesan'].'">';
                                                echo '<td class="tr_show">'.($no++).'</td>';
                                                echo '<td class="tr_show"><span class="badge">'.count_percakapan($r['idpesan']).'</span></td>';
                                                echo '<td class="tr_show"><a href="pesan.php?v=b&i='.$r['idpesan'].'">'.$r['judul'].'</a></td>';
                                                echo '<td class="tr_show">';
                                                  if($r['status'] == 1)
                                                  {
                                                    echo '<span class="badge">Terkirim</span>';
                                                  }
                                                echo '</td>';
                                              echo '</tr>';
                                            }
                                            echo '</tbody>';
                                        echo '</table>';
                                      echo '</div>';
                                        break;

                                      case 'b':
                                      echo '<div class="col-sm-12 col-md-12 col-xs-12">';
                                        $get = query('SELECT * FROM pesan WHERE idpesan=\''.$i.'\' order by id asc');
                                        while ($r = fetch_array($get)) {
                                          if($r['judul'])
                                          {
                                            echo '<h4>'.$r['judul'].'</h4>';
                                          }

                                          if($r['pengirim'])
                                          {
                                            echo 'Oleh : '. $r['pengirim']. '<br>';
                                          }
                                          else if($r['penjawab'])
                                          {
                                            echo 'Oleh : '. $r['penjawab'] . '<br>';
                                          }

                                          echo $r['isi'] . '<br>';
                                        }
                                      echo '</div>';
                                        break;
                                      
                                      default:
                                      echo '<div class="col-sm-12 col-md-12 col-xs-12">';
                                        echo '<table id="myTable" class="table table-bordered table-striped table-hover display nowrap" width="100%">';
                                            echo '<thead>';
                                              echo '<tr>';
                                                echo '<th class="col-md-1">Percakapan</th>';
                                                echo '<th class="col-md-10">Judul</th>';
                                                echo '<th class="col-md-1">Status</th>';
                                              echo '</tr>';
                                            echo '</thead>';

                                            echo '<tbody>';
                                            while ($r = fetch_array($gpesan)) {
                                              echo '<tr data-idpesan="'.$r['idpesan'].'">';
                                                echo '<td class="tr_show"><span class="badge">'.count_percakapan($r['idpesan']).'</span></td>';
                                                echo '<td class="tr_show"><a href="pesan.php?v=b&i='.$r['idpesan'].'">'.$r['judul'].'</a></td>';
                                                echo '<td class="tr_show">';
                                                  if($r['status'] == 1)
                                                  {
                                                    echo '<span class="badge">Terkirim</span>';
                                                  }
                                                echo '</td>';
                                              echo '</tr>';
                                            }
                                            echo '</tbody>';
                                        echo '</table>';
                                      echo '</div>';
                                        break;
                                    }
                                    ?>

                                    <?php if($v=='search' || $v==''){?>
                                    <div class="col-sm-12 col-md-12 col-xs-12">
                                      <p class="right">Total Data : <?php echo $gcount['total']; ?></p>
                                    </div>
                                    <?php } ?>
                              </div>

                           </div>
                        </div>
                    </div>
                </div>
            </div>
        </div> 
    </section>
    <div class="msg_box col-xs-12 col-md-7 col-sm-12" style="display: none;">
        <div class="msg_head col-xs-12 col-md-12 col-sm-12">Formulir Hubungi Kami
            <div class="close" style="padding: 0 5px 0 0;">x</div>
        </div>
        <!-- <div class="container"> -->
        <div class="msg_wrap col-md-12 col-sm-12 col-xs-12 list-group-item">
          <table class="table table-bordered table-striped table-hover display nowrap" width="100%">
            <tbody>
              <tr>
                <td>
                  <input type="text" class="form-control" name="judul" placeholder="Isikan Judul disini">
                </td>
              </tr>
              <tr>
                <td>
                  <textarea name="isi" class="form-control" rows="3"></textarea>
                </td>
              </tr>
              <tr>
                <td>
                  <button type="button" id="kirim_pesan" class="btn btn-success right">Kirim</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <!-- </div> -->
    </div>
<?php 
echo '<div id="ShowPesan" class="modal fade" role="dialog" style="display: none">';
  echo '<div class="modal-dialog">';
    echo '<div class="modal-content">';
      echo '<div class="modal-header">';
        echo '<button type="button" class="close" data-dismiss="modal">&times;</button>';
        echo '<h4 class="modal-title">Informasi Pesan</h4>';
      echo '</div>';
      echo '<div class="modal-body" id="notif">';
        
      echo '</div>';
      echo '<div class="modal-footer" id="mfooter">';
        echo '<a href="pesan.php" class="btn btn-warning">Tutup</a>';
      echo '</div>';
    echo '</div>';

  echo '</div>';
echo '</div>';
include_once ('layout/footer.php'); 
?>
