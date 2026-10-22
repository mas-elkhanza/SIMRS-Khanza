    private void menampilkanSkorGRACE(String norawat) {
        try {
            //menampilkan skor GRACE
            if(chkSkorGRACE.isSelected()==true){
                try {
                    rs2=koneksi.prepareStatement(
                            "select skor_grace.tanggal,skor_grace.usia,skor_grace.skor_usia,skor_grace.denyut_jantung,skor_grace.skor_denyut_jantung,skor_grace.sistolik,skor_grace.skor_sistolik,skor_grace.kreatinin,skor_grace.skor_kreatinin,skor_grace.killip,skor_grace.skor_killip,skor_grace.deviasi_st,skor_grace.skor_deviasi_st,skor_grace.troponin,skor_grace.skor_troponin,skor_grace.henti_jantung,skor_grace.skor_henti_jantung,skor_grace.skor,skor_grace.kategori,skor_grace.keterangan,skor_grace.kd_dokter,dokter.nm_dokter "+
                            "from skor_grace inner join dokter on skor_grace.kd_dokter=dokter.kd_dokter "+
                            "where skor_grace.no_rawat='"+norawat+"' order by skor_grace.tanggal").executeQuery();
                    if(rs2.next()){
                        htmlContent.append(
                          "<tr class='isi'>").append(
                            "<td valign='top' width='2%'></td>").append(
                            "<td valign='top' width='18%'>Skor GRACE</td>").append(
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
            System.out.println("Notif Skor GRACE : "+e);
        }
    }
