
    <section>
        <!-- Left Sidebar -->
        <aside id="leftsidebar" class="sidebar">
            <!-- User Info -->
            <div class="user-info">
                <?php $dataGet = fetch_array(query("SELECT nm_dokter, jk FROM dokter WHERE kd_dokter = '{$_SESSION['username']}'")); ?>
                <div class="image">
                <?php
                if ($dataGet['1'] == 'L') {
                    echo '<img src="assets/images/pria.png" width="48" height="48" alt="User" />';
                } else if ($dataGet['1'] == 'P') {
                    echo '<img src="assets/images/wanita.png" width="48" height="48" alt="User" />';
                }
                ?>
                </div>
                <div class="info-container">
                    <div class="name" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><?php echo $dataGet['0']; ?></div>
                    <div class="email"><?php echo $_SESSION['username']; ?></div>
                    <div class="btn-group user-helper-dropdown">
                        <i class="material-icons" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">keyboard_arrow_down</i>
                        <ul class="dropdown-menu pull-right">
                            <li><a href="profile.php"><i class="material-icons">person</i>Profile</a></li>
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
                    <li class="header">MAIN NAVIGATION</li>
                    <li class="active">
                    </li>
                    <li class>
                        <a href="index.php">
                            <i class="material-icons">home</i>
                            <span>Beranda</span>
                        </a>
                    </li>
                    <li>
                        <a href="javascript:void(0);" class="menu-toggle">
                            <i class="material-icons">text_fields</i>
                            <span>Pasien</span>
                        </a>
                        <ul class="ml-menu">
                            <li>
                                <a href="booking.php">Booking</a>
                            </li>
                            <li>
                                <a href="pasien-ralan.php">Rawat Jalan</a>
                            </li>
                            <li>
                                <a href="pasien-ranap.php">Rawat Inap</a>
                            </li>
                            <li>
                                <a href="rekam-medik.php">Rekam Medik</a>
                            </li>
                            <li>
                                <a href="pasien.php">Database</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="tarif-inacbg.php">
                            <i class="material-icons">attach_money</i>
                            <span>Cek Tarif INACBG's</span>
                        </a>
                    </li>
                    <li>
                        <a href="icd10.php">
                            <i class="material-icons">assignment</i>
                            <span>Master ICD-10</span>
                        </a>
                    </li>
                    <li>
                        <a href="icd9.php">
                            <i class="material-icons">assessment</i>
                            <span>Master ICD-9</span>
                        </a>
                    </li>
                </ul>
            </div>
            <!-- #Menu -->
            <!-- Footer -->
            <div class="legal">
                <div class="copyright">
                    &copy; 2017 - <?php echo date('Y'); ?> <a href="#" data-toggle="modal" data-target="#ICTRSHD">Instalasi ICT RSHD</a> <a href="../dr_ulun/">Barabai</a>.
                </div>
            </div>
            <!-- #Footer -->
        </aside>
        <!-- #END# Left Sidebar -->
    </section>
