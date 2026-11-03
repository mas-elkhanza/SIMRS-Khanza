    private void menampilkanPenilaianBishop(String norawat) {
        try {
            if(chkPenilaianBishop.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_bishop_scor.kd_dokter,dokter.nm_dokter,penilaian_bishop_scor.tanggal,penilaian_bishop_scor.pembukaan,penilaian_bishop_scor.skor_pembukaan,penilaian_bishop_scor.pendataran,penilaian_bishop_scor.skor_pendataran,"+
                            "penilaian_bishop_scor.station,penilaian_bishop_scor.skor_station,penilaian_bishop_scor.konsistensi,penilaian_bishop_scor.skor_konsistensi,penilaian_bishop_scor.posisi,penilaian_bishop_scor.skor_posisi,penilaian_bishop_scor.skor,penilaian_bishop_scor.kategori,"+
                            "penilaian_bishop_scor.keterangan from penilaian_bishop_scor inner join dokter on penilaian_bishop_scor.kd_dokter=dokter.kd_dokter "+
                            "where penilaian_bishop_scor.no_rawat='"+norawat+"'").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>").append( 
                            "<td valign='top' width='2%'></td>").append(        
                            "<td valign='top' width='18%'>Penilaian Skor Bishop</td>").append(
                            "<td valign='top' width='1%' align='center'>:</td>").append(
                            "<td valign='top' width='79%'>").append(
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"
                        );
                        do{
                            htmlContent.append(
                                 "<tr>").append(
                                    "<td valign='top'>").append(
                                       "YANG MELAKUKAN PENILAIAN").append(  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>").append(
                                          "<tr>").append(
                                              "<td width='30%' border='0'>Tanggal : ").append(rs2.getString("tanggal")).append("</td>").append(
                                              "<td width='70%' border='0'>DPJP : ").append(rs2.getString("kd_dokter")).append(" ").append(rs2.getString("nm_dokter")).append("</td>").append(
                                          "</tr>").append(
                                       "</table>").append(
                                    "</td>").append(
                                 "</tr>").append(
                                 "<tr>").append(
                                    "<td valign='top'>").append(
                                       "I. HASIL SKRINING").append(  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>").append(
                                          "<tr>").append(
                                              "<td valign='top' width='4%' bgcolor='#FFFAF8' align='center'>No.</td>").append(
                                              "<td valign='top' width='46%' bgcolor='#FFFAF8' align='center'>Parameter</td>").append(
                                              "<td valign='top' width='34%' bgcolor='#FFFAF8' align='center'>Hasil</td>").append(
                                              "<td valign='top' width='16%' bgcolor='#FFFAF8' align='center'>Skor</td>").append(
                                          "</tr>").append(
                                          "<tr>").append(
                                              "<td width='4%' align='center'>1.</td>").append(
                                              "<td width='46%'>Pembukaan (Dilatasi)</td>").append(
                                              "<td width='34%'>").append(rs2.getString("pembukaan")).append("</td>").append(
                                              "<td width='16%' align='center'>").append(rs2.getString("skor_pembukaan")).append("</td>").append(
                                          "</tr>").append(
                                          "<tr>").append(
                                              "<td width='4%' align='center'>2.</td>").append(
                                              "<td width='46%'>Pendataran (Effacement)</td>").append(
                                              "<td width='34%'>").append(rs2.getString("pendataran")).append("</td>").append(
                                              "<td width='16%' align='center'>").append(rs2.getString("skor_pendataran")).append("</td>").append(
                                          "</tr>").append(
                                          "<tr>").append(
                                              "<td width='4%' align='center'>3.</td>").append(
                                              "<td width='46%'>Station (Penurunan Kepala)</td>").append(
                                              "<td width='34%'>").append(rs2.getString("station")).append("</td>").append(
                                              "<td width='16%' align='center'>").append(rs2.getString("skor_station")).append("</td>").append(
                                          "</tr>").append(
                                          "<tr>").append(
                                              "<td width='4%' align='center'>4.</td>").append(
                                              "<td width='46%'>Konsistensi Serviks</td>").append(
                                              "<td width='34%'>").append(rs2.getString("konsistensi")).append("</td>").append(
                                              "<td width='16%' align='center'>").append(rs2.getString("skor_konsistensi")).append("</td>").append(
                                          "</tr>").append(
                                          "<tr>").append(
                                              "<td width='4%' align='center'>5.</td>").append(
                                              "<td width='46%'>Posisi Serviks</td>").append(
                                              "<td width='34%'>").append(rs2.getString("posisi")).append("</td>").append(
                                              "<td width='16%' align='center'>").append(rs2.getString("skor_posisi")).append("</td>").append(
                                          "</tr>").append(
                                       "</table>").append(
                                    "</td>").append(
                                 "</tr>").append(
                                 "<tr>").append(
                                    "<td valign='top'>").append(
                                       "II. INTERPRETASI").append(  
                                       "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0px' class='tbl_form'>").append(
                                          "<tr>").append(
                                              "<td width='25%' border='0'>Total Skor : <b>").append(rs2.getString("skor")).append("</b></td>").append(
                                              "<td width='35%' border='0'>Kategori Serviks : <font color='").append(rs2.getString("kategori").equals("Belum Matang")?"#CC0000":rs2.getString("kategori").equals("Menengah")?"#C8A000":"#00AA00").append("'><b>").append(rs2.getString("kategori")).append("</b></font></td>").append(
                                              "<td width='40%' border='0'>Keterangan : ").append(rs2.getString("keterangan")).append("</td>").append(
                                          "</tr>").append(
                                       "</table>").append(
                                    "</td>").append(
                                 "</tr>"
                            ); 
                        }while(rs2.next());
                        htmlContent.append(
                              "</table>").append(
                            "</td>").append(
                          "</tr>");
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : "+e);
                } finally{
                    if(rs2!=null){
                        rs2.close();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Notif Penilaian Skor Bishop : "+e);
        }
    }
