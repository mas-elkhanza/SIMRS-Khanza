<div id="navcontainer">
    <?php 
       if ($_SESSION['ses_admin']=="admin"){
            echo "
                <div style='width: 100%; height: 150%; overflow: auto;'> 
                <table width='120%' align='center' height='100%'>
                     <tr width='100%' height='14.2%' align='center'>
                        <td width='16%' align='center'>
                          <a href='index.php?act=InputTahun'>                                                  
                             <img src='images/1360485865_schedule.png'/><br>
                             Tahun & Bulan                                                  
                          </a>
                        </td>
                        <td width='16%' align='center'>     
                           <a href='index.php?act=ListBidang'>
                             <img src='images/1360486910_company.png'/><br>
                             Bidang
                           </a>
                        </td>
                        <td width='16%' align='center'> 
                           <a href='index.php?act=ListPendidikan'>                                                  
                             <img src='images/Gnome-X-Office-Address-Book-48.png'/></br>
                             Pendidikan
                           </a>
                        </td>
                        <td width='16%' align='center'>
                           <a href='index.php?act=ListSttskerja'>                                                  
                             <img src='images/users.png'/><br>
                             Status Kerja                                                  
                           </a>
                        </td>
                        <td width='16%' align='center'>
                           <a href='index.php?act=ListSttswp'>                                                  
                              <img src='images/family.png'/><br>
                              Status WP                                                  
                           </a>
                        </td>                                        
                     </tr>     
                     <tr width='100%' height='14.2%' align='center'>
                        <td width='16%' align='center'>
                           <a href='index.php?act=ListJenjang'>                                                  
                              <img src='images/edit-female-user.png'/><br>
                              Jenjang Jabatan                                                  
                           </a>
                        </td>
                        <td width='16%' align='center'>
                           <a href='index.php?act=ListKelompokJabatan'>                                                  
                              <img src='images/if_Users_131982.png'/><br>
                              Kelompok Jabatan                                                  
                           </a>
                        </td>
                        <td width='16%' align='center'>
                           <a href='index.php?act=ListResikoKerja'>                                                  
                              <img src='images/if_surgeon_45573.png'/><br>
                              Resiko Kerja                                                  
                           </a>
                        </td>
                        <td width='16%' align='center'>
                           <a href='index.php?act=ListEmergencyIndex'>                                                  
                              <img src='images/iconfinder_stethoscope_38717.png'/><br>
                              Emergency Index                                                  
                           </a>
                        </td>
                        <td width='16%' align='center'>
                           <a href='index.php?act=ListPencapaianKinerja'>                                                  
                              <img src='images/if_vector_65_13_473800.png'/><br>
                              Pencapaian Kinerja                                                 
                           </a>
                        </td>                                                                                                                  
                     </tr>
                     <tr width='100%' height='14.2%' align='center'>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListEvaluasiKinerja'>
                                <img src='images/iconfinder_ordering_49597.png'/><br>
                                Evaluasi Kinerja
                            </a>
                        </td> 
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListDepartemen'>
                                <img src='images/Home.png'/><br>
                                Departemen
                            </a>
                        </td> 
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListBank'>
                                <img src='images/cabinet.png'/><br>
                                Bank
                            </a>
                        </td>   
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListInsentif'>
                                <img src='images/money_bag.png'/><br>
                                Insentif                                                  
                            </a>
                        </td> 
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListAkte'>
                                <img src='images/coins.png'/><br>
                                Pendapatan Akte
                            </a>
                        </td>                             
                     </tr>
                     <tr width='100%' height='14.2%' align='center'> 
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListResume'>
                                <img src='images/1404047007_02.png'/><br>
                                Pendapatan Resume
                            </a>
                        </td>  
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListTuslah'>
                                <img src='images/1404046800_Cash_register.png'/><br>
                                Pendapatan Tuslah
                            </a>
                        </td> 
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListWarung'>
                                <img src='images/1360487067_calculator.png'/><br>
                                Pendapatan Warung
                            </a>
                        </td>    
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListCariPegawai'>
                                <img src='images/user3.png'/><br>
                                Data Pegawai
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListCariRiwayatKinerja'>
                                <img src='images/iconfinder_people04_1934228.png'/><br>
                                Riwayat Evaluasi
                            </a>
                        </td>                                                           
                     </tr>
                     <tr width='100%' height='14.2%' align='center'>   
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListCariRiwayatPencapaian'>
                                <img src='images/iconfinder_f-target_256_282461.png'/><br>
                                Riwayat Pencapaian
                            </a>
                        </td>                    
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListPresensi'>                                                  
                                    <img src='images/1404047834_application-vnd.ms-excel.png'/><br>
                                    Lembur Pegawai                                                  
                            </a>
                        </td>                  
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListKeanggotaan'>
                                 <img src='images/user-group-new.png'/><br>
                                Keanggotaan
                             </a>
                        </td> 
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListPotongan'>                                                  
                                     <img src='images/1360487093_price.png'/><br>
                                     Potongan Gaji                                                  
                            </a>
                        </td>   
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListTindakan'>                                                  
                                     <img src='images/doctor2.png'/><br>
                                     Tindakan Medis                                                  
                            </a>
                        </td> 
                     </tr>
                     <tr width='100%' height='14.2%' align='center'>  
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListTunjangan'>                                                  
                                    <img src='images/Money.png'/><br>
                                    Tunjangan                                                  
                            </a>
                        </td> 
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListJasLa'>                                                  
                                    <img src='images/checklist_pencil-o.png'/><br>
                                    Jasa Lain                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListKS&action=TAMBAH'>                                                  
                                     <img src='images/administrator.png'/><br>
                                     Daftar Kasift                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListRj'>                                                  
                                     <img src='images/doctor 3.png'/><br>
                                     Rwt Jln Spesialis                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListPinjam'>                                                  
                                     <img src='images/Card_file.png'/><br>
                                     Koperasi                                                  
                            </a>
                        </td>
                    </tr>
                    <tr width='100%' height='14.2%' align='center'> 
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListJam&action=TAMBAH'>                                                  
                                    <img src='images/Time.png'/><br>
                                    Jam Jaga Departemen                                                  
                            </a>
                        </td> 
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListLampiran&action=LIHAT'>                                                  
                                    <img src='images/checklist.png'/><br>
                                    Penggajian                                                  
                            </a>
                        </td>
                    </tr>
                </table> 
                </div>";		
       }elseif($_SESSION['ses_admin']=="paijo"){
             echo "<table width='100%' align='center' height='100%'>
                     <tr width='100%' height='16.5%' align='center'>
                        <td width='16%' align='center'>
                            <a href='index.php?act=InputTahun'>
                                <img src='images/1360485865_schedule.png'/><br>
                                Tahun & Bulan
                            </a>
                        </td> 
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListInsentif'>                                                  
                                     <img src='images/money_bag.png'/><br>
                                     Insentif                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListAkte'>                                                  
                                     <img src='images/coins.png'/><br>
                                     Pendapatan Akte                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListResume'>                                                  
                                     <img src='images/1404047007_02.png'/><br>
                                     Pendapatan Resume                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListTuslah'>                                                  
                                     <img src='images/1404046800_Cash_register.png'/><br>
                                     Pendapatan Tuslah                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListWarung'>                                                  
                                     <img src='images/1360487067_calculator.png'/><br>
                                     Pendapatan Warung                                                  
                            </a>
                        </td>
                     </tr>
                     <tr width='100%' height='16.5%' align='center'>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListPresensi'>                                                  
                                     <img src='images/1404047834_application-vnd.ms-excel.png'/><br>
                                     Lembur Pegawai                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListKeanggotaan'>                                                  
                                     <img src='images/user-group-new.png'/><br>
                                     Keanggotaan                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListPotongan'>                                                  
                                     <img src='images/1360487093_price.png'/><br>
                                     Potongan Gaji                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListTindakan'>                                                  
                                     <img src='images/doctor2.png'/><br>
                                     Tindakan Medis                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListTunjangan'>                                                  
                                     <img src='images/Money.png'/><br>
                                     Tunjangan                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListJasLa'>                                                  
                                     <img src='images/checklist_pencil-o.png'/><br>
                                     Jasa Lain                                                  
                            </a>
                        </td>
                     </tr>
                     <tr width='100%' height='16.5%' align='center'>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListKS&action=TAMBAH'>                                                  
                                     <img src='images/administrator.png'/><br>
                                     Daftar Kasift                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListRj'>                                                  
                                     <img src='images/doctor 3.png'/><br>
                                     Rwt Jln Spesialis                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListPinjam'>                                                  
                                     <img src='images/Card_file.png'/><br>
                                     Koperasi                                                  
                            </a>
                        </td>
                        <td width='16%' align='center'>
                            <a href='index.php?act=ListLampiran&action=LIHAT'>                                                  
                                     <img src='images/checklist.png'/><br>
                                     Penggajian                                                  
                            </a>
                        </td>
                     </tr>
                   </table>";

       }else{
           include 'admin.php';       
       }
    ?>
</div>
