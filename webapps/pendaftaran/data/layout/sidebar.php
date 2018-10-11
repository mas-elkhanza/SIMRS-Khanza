<?php

/***
* e-Pasien from version 0.1 Beta
* Last modified: 05 July 2018
* Author : drg. Faisol Basoro
* Email : dentix.id@gmail.com
*
* File : layout/sidebar.php
* Description : Sidebar menu
* Licence under GPL
***/

?>
    <section>
        <!-- Left Sidebar -->
        <aside id="leftsidebar" class="sidebar">
            <!-- User Info -->
            <div class="user-info">
                <?php $dataGet = fetch_array(query("SELECT nm_pasien, jk FROM pasien WHERE no_rkm_medis='{$_SESSION['username']}'")); ?>
                <div class="image">
                <?php
                if ($dataGet['1'] == 'L') {
                    echo '<img src="images/pria.png" width="48" height="48" alt="User" />';
                } else if ($dataGet['1'] == 'P') {
                    echo '<img src="images/wanita.png" width="48" height="48" alt="User" />';
                }
                ?>
                </div>
                <div class="info-container">
                    <div class="name" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?php echo $dataGet['0']; ?></div>
                    <div class="email"><?php echo $_SESSION['username']; ?></div>
                    <div class="btn-group user-helper-dropdown">
                        <i class="material-icons" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">keyboard_arrow_down</i>
                        <ul class="dropdown-menu pull-right">
                            <li><a href="javascript:void(0);"><i class="material-icons">person</i>Profile</a></li>
                            <li role="seperator" class="divider"></li>
                            <li><a href="login.php?action=logout"><i class="material-icons">input</i>Sign Out</a></li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- #User Info -->
            <!-- Menu -->
            <div class="menu">
                <ul class="list">
                    <li class="header">MENU UTAMA</li>
                    <li class="active">
                        <a href="index.php">
                            <i class="material-icons">home</i>
                            <span>Beranda</span>
                        </a>
                    </li>
                    <li>
                        <a href="pendaftaran.php">
                            <i class="material-icons">person_add</i>
                            <span>Pendaftaran</span>
                        </a>
                    </li>
                    <li>
                        <a href="riwayat-periksa.php">
                            <i class="material-icons">local_pharmacy</i>
                            <span>Riwayat Periksa</span>
                        </a>
                    </li>
                    <li>
                        <a href="jadwal-dokter.php">
                            <i class="material-icons">event_available</i>
                            <span>Jadwal Dokter</span>
                        </a>
                    </li>
                    <li>
                        <a href="informasi-kamar.php">
                            <i class="material-icons">hotel</i>
                            <span>Informasi Kamar</span>
                        </a>
                    </li>
                    <?php if(PENGADUAN == 'ENABLE') { ?>
                    <li>
                        <a href="pengaduan.php">
                            <i class="material-icons">add_alert</i>
                            <span>Pengaduan</span>
                        </a>
                    </li>
                    <?php } ?>
                </ul>
            </div>
            <!-- #Menu -->
            <!-- Footer -->
            <div class="legal">
                <div class="copyright">
                    &copy; 2017 - <?php echo date('Y'); ?> <a href="#" data-toggle="modal" data-target="#ICTRSHD">Instalasi ICT RSHD Barabai</a>.
                </div>
            </div>
            <!-- #Footer -->
        </aside>
        <!-- #END# Left Sidebar -->
    </section>
