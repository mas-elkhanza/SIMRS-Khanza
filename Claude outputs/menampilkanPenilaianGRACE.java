    private void menampilkanPenilaianGRACE(String norawat) {
        try {
            //menampilkan penilaian GRACE
            if(chkPenilaianGRACE.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select penilaian_grace_scor.tanggal,penilaian_grace_scor.usia,penilaian_grace_scor.skor_usia,penilaian_grace_scor.denyut_jantung,penilaian_grace_scor.skor_denyut_jantung,penilaian_grace_scor.sistolik,penilaian_grace_scor.skor_sistolik,penilaian_grace_scor.kreatinin,penilaian_grace_scor.skor_kreatinin,penilaian_grace_scor.killip,penilaian_grace_scor.skor_killip,penilaian_grace_scor.deviasi_st,penilaian_grace_scor.skor_deviasi_st,penilaian_grace_scor.troponin,penilaian_grace_scor.skor_troponin,penilaian_grace_scor.henti_jantung,penilaian_grace_scor.skor_henti_jantung,penilaian_grace_scor.skor,penilaian_grace_scor.kategori,penilaian_grace_scor.keterangan,penilaian_grace_scor.kd_dokter,dokter.nm_dokter "+
                            "from penilaian_grace_scor inner join dokter on penilaian_grace_scor.kd_dokter=dokter.kd_dokter "+
                            "where penilaian_grace_scor.no_rawat='"+norawat+"' order by penilaian_grace_scor.tanggal").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>").append(
                            "<td valign='top' width='2%'></td>").append(
                            "<td valign='top' width='18%'>Penilaian GRACE</td>").append(
                            "<td valign='top' width='1%' align='center'>:</td>").append(
                            "<td valign='top' width='79%'>").append(
                              "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>").append(
                                 "<tr align='center'>").append(
                                    "<td valign='middle' width='4%' bgcolor='#FFFAF8'>No.</td>").append(
                                    "<td valign='middle' width='11%' bgcolor='#FFFAF8'>Tanggal</td>").append(
                                    "<td valign='middle' width='6%' bgcolor='#FFFAF8'>Usia<br>(Nilai / Skor)</td>").append(
                                    "<td valign='middle' width='6%' bgcolor='#FFFAF8'>Heart Rate<br>(Nilai / Skor)</td>").append(
                                    "<td valign='middle' width='6%' bgcolor='#FFFAF8'>SBP<br>(Nilai / Skor)</td>").append(
                                    "<td valign='middle' width='6%' bgcolor='#FFFAF8'>Creatinine<br>(Nilai / Skor)</td>").append(
                                    "<td valign='middle' width='6%' bgcolor='#FFFAF8'>Killip<br>(Nilai / Skor)</td>").append(
                                    "<td valign='middle' width='6%' bgcolor='#FFFAF8'>ST Dev.<br>(Nilai / Skor)</td>").append(
                                    "<td valign='middle' width='6%' bgcolor='#FFFAF8'>Troponin<br>(Nilai / Skor)</td>").append(
                                    "<td valign='middle' width='6%' bgcolor='#FFFAF8'>Cardiac Arrest<br>(Nilai / Skor)</td>").append(
                                    "<td valign='middle' width='6%' bgcolor='#FFFAF8'>Total Skor</td>").append(
                                    "<td valign='middle' width='8%' bgcolor='#FFFAF8'>Kategori Risiko</td>").append(
                                    "<td valign='middle' width='12%' bgcolor='#FFFAF8'>Dokter</td>").append(
                                    "<td valign='middle' width='10%' bgcolor='#FFFAF8'>Keterangan Dokter</td>").append(
                                 "</tr>"
                        );
                        w=1;
                        do{
                            htmlContent.append(
                                 "<tr>").append(
                                    "<td valign='top' align='center'>").append(w).append("</td>").append(
                                    "<td valign='top'>").append(rs2.getString("tanggal")).append("</td>").append(
                                    "<td valign='top' align='center'>").append(rs2.getString("usia")).append(" / <b>").append(rs2.getString("skor_usia")).append("</b></td>").append(
                                    "<td valign='top' align='center'>").append(rs2.getString("denyut_jantung")).append(" / <b>").append(rs2.getString("skor_denyut_jantung")).append("</b></td>").append(
                                    "<td valign='top' align='center'>").append(rs2.getString("sistolik")).append(" / <b>").append(rs2.getString("skor_sistolik")).append("</b></td>").append(
                                    "<td valign='top' align='center'>").append(rs2.getString("kreatinin")).append(" / <b>").append(rs2.getString("skor_kreatinin")).append("</b></td>").append(
                                    "<td valign='top' align='center'>").append(rs2.getString("killip")).append(" / <b>").append(rs2.getString("skor_killip")).append("</b></td>").append(
                                    "<td valign='top' align='center'>").append(rs2.getString("deviasi_st")).append(" / <b>").append(rs2.getString("skor_deviasi_st")).append("</b></td>").append(
                                    "<td valign='top' align='center'>").append(rs2.getString("troponin")).append(" / <b>").append(rs2.getString("skor_troponin")).append("</b></td>").append(
                                    "<td valign='top' align='center'>").append(rs2.getString("henti_jantung")).append(" / <b>").append(rs2.getString("skor_henti_jantung")).append("</b></td>").append(
                                    "<td valign='top' align='center'><b>").append(rs2.getString("skor")).append("</b></td>").append(
                                    "<td valign='top' align='center'><font color='").append(rs2.getString("kategori").equals("High Risk")?"#CC0000":rs2.getString("kategori").equals("Intermediate Risk")?"#C8A000":"#00AA00").append("'><b>").append(rs2.getString("kategori")).append("</b></font></td>").append(
                                    "<td valign='top'>").append(rs2.getString("kd_dokter")).append(" ").append(rs2.getString("nm_dokter")).append("</td>").append(
                                    "<td valign='top'>").append(rs2.getString("keterangan")==null?"":rs2.getString("keterangan")).append("</td>").append(
                                 "</tr>");
                            w++;
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
            System.out.println("Notif Penilaian GRACE : "+e);
        }
    }
