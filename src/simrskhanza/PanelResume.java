/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simrskhanza;

import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.HyperlinkEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;

/**
 *
 * @author khanzamedia
 */
public class PanelResume extends widget.panelisi {
    private final Connection koneksi=koneksiDB.condb();
    private DefaultTableModel tabModeRegistrasi;
    private final sekuel Sequel=new sekuel();
    private final Properties prop = new Properties(); 
    private validasi Valid=new validasi();
    private ResultSet rs,rs2,rs3,rs4,rs5,rshal;
    private PreparedStatement ps,ps2;
    private String tanggal="",jam="",dpjp="",kddpjp="",tanggal1="",tanggal2="",norm="",keputusan="";
    private StringBuilder htmlContent;
    private boolean caritanggal=false;
    private int i=0,y=0,w=0,urut;
    /**
     * Creates new form PanelResume
     */
    public PanelResume() {
        initComponents();
        tabModeRegistrasi=new DefaultTableModel(null,new Object[]{
                "No.","No.Rawat","Tanggal","Jam","Kd.Dokter","Dokter Dituju/DPJP","Umur","Poliklinik/Kamar","Jenis Bayar"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbRegistrasi.setModel(tabModeRegistrasi);

        tbRegistrasi.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbRegistrasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 9; i++) {
            TableColumn column = tbRegistrasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(60);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(250);   
            }else if(i==6){
                column.setPreferredWidth(40);
            }else if(i==7){
                column.setPreferredWidth(200);
            }else if(i==8){
                column.setPreferredWidth(110);
            }
        }
        tbRegistrasi.setDefaultRenderer(Object.class, new WarnaTable());
        
        try {
            prop.loadFromXML(new FileInputStream("setting/database.xml"));
        } catch (Exception e) {
            System.out.println("Resume : "+e);
        }
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditorKit(kit);
        LoadHTML2.setEditorKit(kit);
        LoadHTML3.setEditorKit(kit);
        LoadHTML4.setEditorKit(kit);
        LoadHTML5.setEditorKit(kit);
        LoadHTML6.setEditorKit(kit);
        LoadHTML7.setEditorKit(kit);
        LoadHTML8.setEditorKit(kit);
        LoadHTML9.setEditorKit(kit);
        LoadHTML10.setEditorKit(kit);
        LoadHTML11.setEditorKit(kit);
        LoadHTML12.setEditorKit(kit);
        LoadHTML13.setEditorKit(kit);
        LoadHTML14.setEditorKit(kit);
        
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}.isi a{text-decoration:none;color:#8b9b95;padding:0 0 0 0px;font-family: Tahoma;font-size: 8.5px;}");
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
        LoadHTML2.setDocument(doc);
        LoadHTML3.setDocument(doc);
        LoadHTML4.setDocument(doc);
        LoadHTML5.setDocument(doc);
        LoadHTML6.setDocument(doc);
        LoadHTML7.setDocument(doc);
        LoadHTML8.setDocument(doc);
        LoadHTML9.setDocument(doc);
        LoadHTML10.setDocument(doc);
        LoadHTML11.setDocument(doc);
        LoadHTML12.setDocument(doc);
        LoadHTML13.setDocument(doc);
        LoadHTML14.setDocument(doc);
        LoadHTML.setEditable(false);
        LoadHTML2.setEditable(false);
        LoadHTML3.setEditable(false);
        LoadHTML4.setEditable(false);
        LoadHTML5.setEditable(false);
        LoadHTML6.setEditable(false);
        LoadHTML7.setEditable(false);
        LoadHTML8.setEditable(false);
        LoadHTML9.setEditable(false);
        LoadHTML10.setEditable(false);
        LoadHTML11.setEditable(false);
        LoadHTML12.setEditable(false);
        LoadHTML13.setEditable(false);
        LoadHTML14.setEditable(false);
        
        LoadHTML.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML2.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML3.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              System.out.println(e.getURL());
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML4.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML5.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML6.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML7.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML8.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML9.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML10.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        LoadHTML11.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });  
        
        LoadHTML12.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        }); 
        
        LoadHTML13.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
        
        LoadHTML14.addHyperlinkListener(e -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(e.getEventType())) {
              Desktop desktop = Desktop.getDesktop();
              try {
                desktop.browse(e.getURL().toURI());
              } catch (Exception ex) {
                ex.printStackTrace();
              }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TabRawat = new javax.swing.JTabbedPane();
        internalFrame16 = new widget.InternalFrame();
        Scroll14 = new widget.ScrollPane();
        LoadHTML14 = new widget.editorpane();
        internalFrame2 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        internalFrame3 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        LoadHTML2 = new widget.editorpane();
        internalFrame4 = new widget.InternalFrame();
        Scroll2 = new widget.ScrollPane();
        LoadHTML3 = new widget.editorpane();
        internalFrame5 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        LoadHTML4 = new widget.editorpane();
        internalFrame6 = new widget.InternalFrame();
        Scroll4 = new widget.ScrollPane();
        LoadHTML5 = new widget.editorpane();
        internalFrame7 = new widget.InternalFrame();
        Scroll5 = new widget.ScrollPane();
        LoadHTML6 = new widget.editorpane();
        internalFrame8 = new widget.InternalFrame();
        Scroll6 = new widget.ScrollPane();
        LoadHTML7 = new widget.editorpane();
        internalFrame9 = new widget.InternalFrame();
        Scroll7 = new widget.ScrollPane();
        LoadHTML8 = new widget.editorpane();
        internalFrame10 = new widget.InternalFrame();
        Scroll8 = new widget.ScrollPane();
        LoadHTML9 = new widget.editorpane();
        internalFrame11 = new widget.InternalFrame();
        Scroll9 = new widget.ScrollPane();
        LoadHTML10 = new widget.editorpane();
        internalFrame12 = new widget.InternalFrame();
        Scroll10 = new widget.ScrollPane();
        LoadHTML11 = new widget.editorpane();
        internalFrame13 = new widget.InternalFrame();
        Scroll11 = new widget.ScrollPane();
        LoadHTML12 = new widget.editorpane();
        internalFrame14 = new widget.InternalFrame();
        Scroll12 = new widget.ScrollPane();
        tbRegistrasi = new widget.Table();
        internalFrame15 = new widget.InternalFrame();
        Scroll13 = new widget.ScrollPane();
        LoadHTML13 = new widget.editorpane();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        TabRawat.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame16.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame16.setBorder(null);
        internalFrame16.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll14.setOpaque(true);

        LoadHTML14.setBorder(null);
        Scroll14.setViewportView(LoadHTML14);

        internalFrame16.add(Scroll14, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("3 Riwayat Terakhir", internalFrame16);

        internalFrame2.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame2.setBorder(null);
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setOpaque(true);

        LoadHTML.setBorder(null);
        Scroll.setViewportView(LoadHTML);

        internalFrame2.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Seluruh Riwayat", internalFrame2);

        internalFrame3.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame3.setBorder(null);
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setOpaque(true);

        LoadHTML2.setBorder(null);
        Scroll1.setViewportView(LoadHTML2);

        internalFrame3.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Penyakit/ICD 10", internalFrame3);

        internalFrame4.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame4.setBorder(null);
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll2.setOpaque(true);

        LoadHTML3.setBorder(null);
        Scroll2.setViewportView(LoadHTML3);

        internalFrame4.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Prosedur/ICD 9", internalFrame4);

        internalFrame5.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame5.setBorder(null);
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll3.setOpaque(true);

        LoadHTML4.setBorder(null);
        Scroll3.setViewportView(LoadHTML4);

        internalFrame5.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tindakan Ralan", internalFrame5);

        internalFrame6.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame6.setBorder(null);
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setOpaque(true);

        LoadHTML5.setBorder(null);
        Scroll4.setViewportView(LoadHTML5);

        internalFrame6.add(Scroll4, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tindakan Ranap", internalFrame6);

        internalFrame7.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame7.setBorder(null);
        internalFrame7.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll5.setOpaque(true);

        LoadHTML6.setBorder(null);
        Scroll5.setViewportView(LoadHTML6);

        internalFrame7.add(Scroll5, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Tindakan Operasi", internalFrame7);

        internalFrame8.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame8.setBorder(null);
        internalFrame8.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll6.setOpaque(true);

        LoadHTML7.setBorder(null);
        Scroll6.setViewportView(LoadHTML7);

        internalFrame8.add(Scroll6, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Radiologi", internalFrame8);

        internalFrame9.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame9.setBorder(null);
        internalFrame9.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll7.setOpaque(true);

        LoadHTML8.setBorder(null);
        Scroll7.setViewportView(LoadHTML8);

        internalFrame9.add(Scroll7, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Laborat", internalFrame9);

        internalFrame10.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame10.setBorder(null);
        internalFrame10.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll8.setOpaque(true);

        LoadHTML9.setBorder(null);
        Scroll8.setViewportView(LoadHTML9);

        internalFrame10.add(Scroll8, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Pemberian Obat", internalFrame10);

        internalFrame11.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame11.setBorder(null);
        internalFrame11.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll9.setOpaque(true);

        LoadHTML10.setBorder(null);
        Scroll9.setViewportView(LoadHTML10);

        internalFrame11.add(Scroll9, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Obat Operasi", internalFrame11);

        internalFrame12.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame12.setBorder(null);
        internalFrame12.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll10.setOpaque(true);

        LoadHTML11.setBorder(null);
        Scroll10.setViewportView(LoadHTML11);

        internalFrame12.add(Scroll10, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Resep Pulang", internalFrame12);

        internalFrame13.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame13.setBorder(null);
        internalFrame13.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll11.setOpaque(true);

        LoadHTML12.setBorder(null);
        Scroll11.setViewportView(LoadHTML12);

        internalFrame13.add(Scroll11, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Berkas Digital", internalFrame13);

        internalFrame14.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame14.setBorder(null);
        internalFrame14.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll12.setOpaque(true);
        Scroll12.setViewportView(tbRegistrasi);

        internalFrame14.add(Scroll12, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Kunjungan", internalFrame14);

        internalFrame15.setBackground(new java.awt.Color(255, 255, 255));
        internalFrame15.setBorder(null);
        internalFrame15.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll13.setOpaque(true);

        LoadHTML13.setBorder(null);
        Scroll13.setViewportView(LoadHTML13);

        internalFrame15.add(Scroll13, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Catatan Dokter", internalFrame15);

        add(TabRawat, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        pilihTab();
    }//GEN-LAST:event_TabRawatMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML10;
    private widget.editorpane LoadHTML11;
    private widget.editorpane LoadHTML12;
    private widget.editorpane LoadHTML13;
    private widget.editorpane LoadHTML14;
    private widget.editorpane LoadHTML2;
    private widget.editorpane LoadHTML3;
    private widget.editorpane LoadHTML4;
    private widget.editorpane LoadHTML5;
    private widget.editorpane LoadHTML6;
    private widget.editorpane LoadHTML7;
    private widget.editorpane LoadHTML8;
    private widget.editorpane LoadHTML9;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll11;
    private widget.ScrollPane Scroll12;
    private widget.ScrollPane Scroll13;
    private widget.ScrollPane Scroll14;
    private widget.ScrollPane Scroll2;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane Scroll5;
    private widget.ScrollPane Scroll6;
    private widget.ScrollPane Scroll7;
    private widget.ScrollPane Scroll8;
    private widget.ScrollPane Scroll9;
    private javax.swing.JTabbedPane TabRawat;
    private widget.InternalFrame internalFrame10;
    private widget.InternalFrame internalFrame11;
    private widget.InternalFrame internalFrame12;
    private widget.InternalFrame internalFrame13;
    private widget.InternalFrame internalFrame14;
    private widget.InternalFrame internalFrame15;
    private widget.InternalFrame internalFrame16;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame7;
    private widget.InternalFrame internalFrame8;
    private widget.InternalFrame internalFrame9;
    private widget.Table tbRegistrasi;
    // End of variables declaration//GEN-END:variables
    private void tampil(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(caritanggal==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' order by reg_periksa.tgl_registrasi").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            //menampilkan triase gawat darurat primer
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select data_triase_igdprimer.keluhan_utama,data_triase_igdprimer.kebutuhan_khusus,data_triase_igdprimer.catatan,"+
                                        "data_triase_igdprimer.plan,data_triase_igdprimer.tanggaltriase,data_triase_igdprimer.nip,data_triase_igd.tekanan_darah,"+
                                        "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                                        "data_triase_igd.no_rawat,data_triase_igd.cara_masuk,data_triase_igd.alat_transportasi,data_triase_igd.alasan_kedatangan,"+
                                        "data_triase_igd.keterangan_kedatangan,data_triase_igd.kode_kasus,master_triase_macam_kasus.macam_kasus from data_triase_igdprimer "+
                                        "inner join data_triase_igd inner join master_triase_macam_kasus on data_triase_igd.no_rawat=data_triase_igdprimer.no_rawat "+
                                        "and data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus where data_triase_igd.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Triase Gawat Darurat</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Cara Masuk</td><td valign='top'>: "+rs3.getString("cara_masuk")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+            
                                                "<td valign='top'>Transportasi</td><td valign='top'>: "+rs3.getString("alat_transportasi")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Alasan Kedatangan</td><td valign='top'>: "+rs3.getString("alasan_kedatangan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Keterangan Kedatangan</td><td valign='top'>: "+rs3.getString("keterangan_kedatangan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Macam Kasus</td><td valign='top'>: "+rs3.getString("macam_kasus")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35%'>Keterangan</td>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65%'>Triase Primer</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Keluhan Utama</td>"+
                                                "<td valign='middle'>"+rs3.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Tanda Vital</td>"+
                                                "<td valign='middle'>Suhu (C) : "+rs3.getString("suhu")+", Nyeri : "+rs3.getString("nyeri")+", Tensi : "+rs3.getString("tekanan_darah")+", Nadi(/menit) : "+rs3.getString("nadi")+", Saturasi O²(%) : "+rs3.getString("saturasi_o2")+", Respirasi(/menit) : "+rs3.getString("pernapasan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Kebutuhan Khusus</td>"+
                                                "<td valign='middle'>"+rs3.getString("kebutuhan_khusus")+"</td>"+
                                            "</tr>"
                                    );
                                    
                                    try {
                                        rs4=koneksi.prepareStatement(
                                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                            "from master_triase_pemeriksaan inner join master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                                            "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala1.kode_pemeriksaan and "+
                                            "master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where data_triase_igddetail_skala1.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                            "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                        if(rs4.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                    "<td valign='middle' bgcolor='#AA0000' color='ffffff' align='center'>Immediate/Segera</td>"+
                                                "</tr>"
                                            );
                                            rs4.beforeFirst();
                                            while(rs4.next()){
                                                htmlContent.append(                             
                                                    "<tr class='isi'>"+
                                                        "<td valign='middle'>"+rs4.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='middle' bgcolor='#AA0000' color='ffffff'>"+
                                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                                );
                                                try {
                                                    rs5=koneksi.prepareStatement(
                                                        "select master_triase_skala1.pengkajian_skala1 from master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                                                        "on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where "+
                                                        "master_triase_skala1.kode_pemeriksaan='"+rs4.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala1.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                                        "order by data_triase_igddetail_skala1.kode_skala1").executeQuery();
                                                    while(rs5.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi'>"+
                                                                "<td border='0' valign='middle' bgcolor='#AA0000' color='ffffff' width='100%'>"+rs5.getString("pengkajian_skala1")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs5!=null){
                                                        rs5.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            keputusan="#AA0000";
                                        }
                                    } catch (Exception e) {
                                        if(rs4!=null){
                                            rs4.close();
                                        }
                                    }
                                    
                                    try {
                                        rs4=koneksi.prepareStatement(
                                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                            "from master_triase_pemeriksaan inner join master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                            "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala2.kode_pemeriksaan and "+
                                            "master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where data_triase_igddetail_skala2.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                            "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                        if(rs4.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                    "<td valign='middle' bgcolor='#FF0000' color='ffffff' align='center'>Emergensi</td>"+
                                                "</tr>"
                                            );
                                            rs4.beforeFirst();
                                            while(rs4.next()){
                                                htmlContent.append(                             
                                                    "<tr class='isi'>"+
                                                        "<td valign='middle'>"+rs4.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='middle' bgcolor='#FF0000' color='ffffff'>"+
                                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                                );
                                                try {
                                                    rs5=koneksi.prepareStatement(
                                                        "select master_triase_skala2.pengkajian_skala2 from master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                                        "on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where "+
                                                        "master_triase_skala2.kode_pemeriksaan='"+rs4.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala2.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                                        "order by data_triase_igddetail_skala2.kode_skala2").executeQuery();
                                                    while(rs5.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi'>"+
                                                                "<td border='0' valign='middle' bgcolor='#FF0000' color='ffffff' width='100%'>"+rs5.getString("pengkajian_skala2")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs5!=null){
                                                        rs5.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            keputusan="#FF0000";
                                        }
                                    } catch (Exception e) {
                                        if(rs4!=null){
                                            rs4.close();
                                        }
                                    }
                                    
                                    htmlContent.append(
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Plan/Keputusan</td>"+
                                                "<td valign='middle' bgcolor='"+keputusan+"' color='ffffff'>Zona Merah "+rs3.getString("plan")+"</td>"+
                                            "</tr>"+                       
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>&nbsp;</td>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>Petugas Triase Primer</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Tanggal & Jam</td>"+
                                                "<td valign='middle'>"+rs3.getString("tanggaltriase")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Catatan</td>"+
                                                "<td valign='middle'>"+rs3.getString("catatan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Nama Petugas</td>"+
                                                "<td valign='middle'>"+rs3.getString("nip")+" "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("nip"))+"</td>"+
                                            "</tr>"+
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                    
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan triase gawat darurat sekunder
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select data_triase_igdsekunder.anamnesa_singkat,data_triase_igdsekunder.catatan,"+
                                        "data_triase_igdsekunder.plan,data_triase_igdsekunder.tanggaltriase,data_triase_igdsekunder.nip,data_triase_igd.tekanan_darah,"+
                                        "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                                        "data_triase_igd.no_rawat,data_triase_igd.cara_masuk,data_triase_igd.alat_transportasi,data_triase_igd.alasan_kedatangan,"+
                                        "data_triase_igd.keterangan_kedatangan,data_triase_igd.kode_kasus,master_triase_macam_kasus.macam_kasus from data_triase_igdsekunder "+
                                        "inner join data_triase_igd inner join master_triase_macam_kasus on data_triase_igd.no_rawat=data_triase_igdsekunder.no_rawat "+
                                        "and data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus where data_triase_igd.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Triase Gawat Darurat</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Cara Masuk</td><td valign='top'>: "+rs3.getString("cara_masuk")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+            
                                                "<td valign='top'>Transportasi</td><td valign='top'>: "+rs3.getString("alat_transportasi")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Alasan Kedatangan</td><td valign='top'>: "+rs3.getString("alasan_kedatangan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Keterangan Kedatangan</td><td valign='top'>: "+rs3.getString("keterangan_kedatangan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Macam Kasus</td><td valign='top'>: "+rs3.getString("macam_kasus")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35%'>Keterangan</td>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65%'>Triase Sekunder</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Anamnesa Singkat</td>"+
                                                "<td valign='middle'>"+rs3.getString("anamnesa_singkat").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Tanda Vital</td>"+
                                                "<td valign='middle'>Suhu (C) : "+rs3.getString("suhu")+", Nyeri : "+rs3.getString("nyeri")+", Tensi : "+rs3.getString("tekanan_darah")+", Nadi(/menit) : "+rs3.getString("nadi")+", Saturasi O²(%) : "+rs3.getString("saturasi_o2")+", Respirasi(/menit) : "+rs3.getString("pernapasan")+"</td>"+
                                            "</tr>"
                                    );
                                    
                                    try {
                                        rs4=koneksi.prepareStatement(
                                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                            "from master_triase_pemeriksaan inner join master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                                            "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala3.kode_pemeriksaan and "+
                                            "master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where data_triase_igddetail_skala3.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                            "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                        if(rs4.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                    "<td valign='middle' bgcolor='#C8C800' color='ffffff' align='center'>Urgensi</td>"+
                                                "</tr>"
                                            );
                                            rs4.beforeFirst();
                                            while(rs4.next()){
                                                htmlContent.append(                             
                                                    "<tr class='isi'>"+
                                                        "<td valign='middle'>"+rs4.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='middle' bgcolor='#C8C800' color='ffffff'>"+
                                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                                );
                                                try {
                                                    rs5=koneksi.prepareStatement(
                                                        "select master_triase_skala3.pengkajian_skala3 from master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                                                        "on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where "+
                                                        "master_triase_skala3.kode_pemeriksaan='"+rs4.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala3.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                                        "order by data_triase_igddetail_skala3.kode_skala3").executeQuery();
                                                    while(rs5.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi'>"+
                                                                "<td border='0' valign='middle' bgcolor='#C8C800' color='ffffff' width='100%'>"+rs5.getString("pengkajian_skala3")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs5!=null){
                                                        rs5.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            keputusan="#C8C800";
                                        }
                                    } catch (Exception e) {
                                        if(rs4!=null){
                                            rs4.close();
                                        }
                                    }
                                    
                                    try {
                                        rs4=koneksi.prepareStatement(
                                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                            "from master_triase_pemeriksaan inner join master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                            "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala4.kode_pemeriksaan and "+
                                            "master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where data_triase_igddetail_skala4.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                            "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                        if(rs4.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                    "<td valign='middle' bgcolor='#00AA00' color='ffffff' align='center'>Semi Urgensi/Urgensi Rendah</td>"+
                                                "</tr>"
                                            );
                                            rs4.beforeFirst();
                                            while(rs4.next()){
                                                htmlContent.append(                             
                                                    "<tr class='isi'>"+
                                                        "<td valign='middle'>"+rs4.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='middle' bgcolor='#00AA00' color='ffffff'>"+
                                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                                );
                                                try {
                                                    rs5=koneksi.prepareStatement(
                                                        "select master_triase_skala4.pengkajian_skala4 from master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                                        "on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where "+
                                                        "master_triase_skala4.kode_pemeriksaan='"+rs4.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala4.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                                        "order by data_triase_igddetail_skala4.kode_skala4").executeQuery();
                                                    while(rs5.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi'>"+
                                                                "<td border='0' valign='middle' bgcolor='#00AA00' color='ffffff' width='100%'>"+rs5.getString("pengkajian_skala4")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs5!=null){
                                                        rs5.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            keputusan="#00AA00";
                                        }
                                    } catch (Exception e) {
                                        if(rs4!=null){
                                            rs4.close();
                                        }
                                    }
                                    
                                    try {
                                        rs4=koneksi.prepareStatement(
                                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                            "from master_triase_pemeriksaan inner join master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                            "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala5.kode_pemeriksaan and "+
                                            "master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where data_triase_igddetail_skala5.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                            "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                        if(rs4.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                    "<td valign='middle' bgcolor='#969696' color='ffffff' align='center'>Non Urgensi</td>"+
                                                "</tr>"
                                            );
                                            rs4.beforeFirst();
                                            while(rs4.next()){
                                                htmlContent.append(                             
                                                    "<tr class='isi'>"+
                                                        "<td valign='middle'>"+rs4.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='middle' bgcolor='#969696' color='ffffff'>"+
                                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                                );
                                                try {
                                                    rs5=koneksi.prepareStatement(
                                                        "select master_triase_skala5.pengkajian_skala5 from master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                                        "on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where "+
                                                        "master_triase_skala5.kode_pemeriksaan='"+rs4.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala5.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                                        "order by data_triase_igddetail_skala5.kode_skala5").executeQuery();
                                                    while(rs5.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi'>"+
                                                                "<td border='0' valign='middle' bgcolor='#969696' color='ffffff' width='100%'>"+rs5.getString("pengkajian_skala5")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs5!=null){
                                                        rs5.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            keputusan="#969696";
                                        }
                                    } catch (Exception e) {
                                        if(rs4!=null){
                                            rs4.close();
                                        }
                                    }
                                    
                                    htmlContent.append(
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Plan/Keputusan</td>"+
                                                "<td valign='middle' bgcolor='"+keputusan+"' color='ffffff'>"+rs3.getString("plan")+"</td>"+
                                            "</tr>"+                       
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>&nbsp;</td>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>Petugas Triase Sekunder</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Tanggal & Jam</td>"+
                                                "<td valign='middle'>"+rs3.getString("tanggaltriase")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Catatan</td>"+
                                                "<td valign='middle'>"+rs3.getString("catatan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Nama Petugas</td>"+
                                                "<td valign='middle'>"+rs3.getString("nip")+" "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("nip"))+"</td>"+
                                            "</tr>"+
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                    
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan diagnosa penyakit                            
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit, diagnosa_pasien.status "+
                                        "from diagnosa_pasien inner join penyakit "+
                                        "on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                                        "where diagnosa_pasien.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Diagnosa/Penyakit/ICD 10</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'><td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td><td valign='top' width='24%' bgcolor='#FFFAF8'>Kode</td><td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Penyakit</td><td valign='top' width='23%' bgcolor='#FFFAF8'>Status</td></tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append("<tr><td valign='top' align='center'>"+w+"</td><td valign='top'>"+rs3.getString("kd_penyakit")+"</td><td valign='top'>"+rs3.getString("nm_penyakit")+"</td><td valign='top'>"+rs3.getString("status")+"</td></tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                    
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan prosedur tindakan
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select prosedur_pasien.kode,icd9.deskripsi_panjang, prosedur_pasien.status "+
                                        "from prosedur_pasien inner join icd9 "+
                                        "on prosedur_pasien.kode=icd9.kode "+
                                        "where prosedur_pasien.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prosedur Tindakan/ICD 9</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'><td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td><td valign='top' width='24%' bgcolor='#FFFAF8'>Kode</td><td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Prosedur</td><td valign='top' width='23%' bgcolor='#FFFAF8'>Status</td></tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append("<tr><td valign='top' align='center'>"+w+"</td><td valign='top'>"+rs3.getString("kode")+"</td><td valign='top'>"+rs3.getString("deskripsi_panjang")+"</td><td valign='top'>"+rs3.getString("status")+"</td></tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan catatan dokter
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select catatan_perawatan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                        "catatan_perawatan.tanggal,catatan_perawatan.jam,catatan_perawatan.kd_dokter,dokter.nm_dokter,"+
                                        "catatan_perawatan.catatan from pasien inner join reg_periksa inner join catatan_perawatan inner join dokter "+
                                        "on catatan_perawatan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                        "and catatan_perawatan.kd_dokter=dokter.kd_dokter "+
                                        "where catatan_perawatan.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Catatan Dokter</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode Dokter</td>"+
                                                "<td valign='top' width='20%' bgcolor='#FFFAF8'>Nama Dokter</td>"+
                                                "<td valign='top' width='50%' bgcolor='#FFFAF8'>Catatan</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tanggal")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("catatan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                             "</tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan ralan
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi,pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,"+
                                        "pemeriksaan_ralan.tinggi,pemeriksaan_ralan.berat,pemeriksaan_ralan.gcs,pemeriksaan_ralan.keluhan, "+
                                        "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.imun_ke,pemeriksaan_ralan.rtl,"+
                                        "pemeriksaan_ralan.penilaian from pemeriksaan_ralan where "+
                                        "pemeriksaan_ralan.no_rawat='"+rs2.getString("no_rawat")+"' order by pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Rawat Jalan</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Suhu(C)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Tensi</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Nadi(/menit)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Respirasi(/menit)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Tinggi(Cm)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Berat(Kg)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>GCS(E,V,M)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Imunisasi Ke</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("suhu_tubuh")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tensi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nadi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("respirasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tinggi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("berat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("gcs")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("imun_ke")+"</td>"+
                                             "</tr>"); 
                                        if(!rs3.getString("keluhan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Keluhan</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("keluhan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("pemeriksaan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Pemeriksaan</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("pemeriksaan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("penilaian").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Penilaian</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("penilaian").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("rtl").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Tindak Lanjut</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("rtl").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("alergi").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Alergi</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("alergi")+"</td>"+
                                                 "</tr>");
                                        }
                                            
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan obstetri ralan
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat,pemeriksaan_obstetri_ralan.tinggi_uteri,pemeriksaan_obstetri_ralan.janin,pemeriksaan_obstetri_ralan.letak, " +
                                        "pemeriksaan_obstetri_ralan.panggul,pemeriksaan_obstetri_ralan.denyut,pemeriksaan_obstetri_ralan.kontraksi, " +
                                        "pemeriksaan_obstetri_ralan.kualitas_mnt,pemeriksaan_obstetri_ralan.kualitas_dtk,pemeriksaan_obstetri_ralan.fluksus,pemeriksaan_obstetri_ralan.albus, " +
                                        "pemeriksaan_obstetri_ralan.vulva,pemeriksaan_obstetri_ralan.portio,pemeriksaan_obstetri_ralan.dalam, pemeriksaan_obstetri_ralan.tebal, pemeriksaan_obstetri_ralan.arah, pemeriksaan_obstetri_ralan.pembukaan," +
                                        "pemeriksaan_obstetri_ralan.penurunan, pemeriksaan_obstetri_ralan.denominator, pemeriksaan_obstetri_ralan.ketuban, pemeriksaan_obstetri_ralan.feto " +
                                        "from pasien inner join reg_periksa inner join pemeriksaan_obstetri_ralan "+
                                        "on pemeriksaan_obstetri_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                                        "pemeriksaan_obstetri_ralan.no_rawat='"+rs2.getString("no_rawat")+"' order by pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Obstetri Rawat Jalan</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Tinggi Fundus</td>"+
                                                "<td valign='top' width='6%' bgcolor='#FFFAF8'>Janin</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Letak</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Panggul</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Denyut</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kontraksi</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kualitas Mnt</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kualitas Detik</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Fluksus</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Albus</td>"+
                                                "<td valign='top' width='6%' bgcolor='#FFFAF8'>Dalam</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Tebal</td>"+
                                                "<td valign='top' width='6%' bgcolor='#FFFAF8'>Arah</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Ketuban</td>"+
                                                "<td valign='top' width='7%' bgcolor='#FFFAF8'>Feto</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tinggi_uteri")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("janin")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("letak")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("panggul")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("denyut")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kontraksi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kualitas_mnt")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kualitas_dtk")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("fluksus")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("albus")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("dalam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tebal")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("arah")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("ketuban")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("feto")+"</td>"+
                                             "</tr>"); 
                                        if(!rs3.getString("vulva").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Vulva</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("vulva")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("portio").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Portio</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("portio")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("pembukaan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Pembukaan</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("pembukaan")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("penurunan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Penurunan</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("penurunan")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("denominator").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Denominator</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("denominator")+"</td>"+
                                                 "</tr>");
                                        }
                                            
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan genekologi ralan
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_ginekologi_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                        "pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat,pemeriksaan_ginekologi_ralan.inspeksi,pemeriksaan_ginekologi_ralan.inspeksi_vulva,pemeriksaan_ginekologi_ralan.inspekulo_gine, " +
                                        "pemeriksaan_ginekologi_ralan.fluxus_gine,pemeriksaan_ginekologi_ralan.fluor_gine,pemeriksaan_ginekologi_ralan.vulva_inspekulo, " +
                                        "pemeriksaan_ginekologi_ralan.portio_inspekulo,pemeriksaan_ginekologi_ralan.sondage,pemeriksaan_ginekologi_ralan.portio_dalam,pemeriksaan_ginekologi_ralan.bentuk, " +
                                        "pemeriksaan_ginekologi_ralan.cavum_uteri,pemeriksaan_ginekologi_ralan.mobilitas,pemeriksaan_ginekologi_ralan.ukuran, pemeriksaan_ginekologi_ralan.nyeri_tekan, pemeriksaan_ginekologi_ralan.adnexa_kanan, pemeriksaan_ginekologi_ralan.adnexa_kiri," +
                                        "pemeriksaan_ginekologi_ralan.cavum_douglas " +
                                        "from pasien inner join reg_periksa inner join pemeriksaan_ginekologi_ralan "+
                                        "on pemeriksaan_ginekologi_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                                        "pemeriksaan_ginekologi_ralan.no_rawat='"+rs2.getString("no_rawat")+"' order by pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Ginekologi Rawat Jalan</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='80%' bgcolor='#FFFAF8'>Pemeriksaan</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+
                                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>Inspeksi</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("inspeksi")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Vulva/Uretra/Vagina</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("inspeksi_vulva")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>Inspekulo</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("inspekulo_gine")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Fluxus</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("fluxus_gine")+",&nbsp;&nbsp;Fluor Albus : "+rs3.getString("fluor_gine")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Vulva/Vagina</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("vulva_inspekulo")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Portio</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("portio_inspekulo")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Sondage</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("sondage")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>Pemeriksaan Dalam</td>"+
                                                           "<td border='0' valign='top' width='70%'>:</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Portio</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("portio_dalam")+",&nbsp;&nbsp;Bentuk : "+rs3.getString("bentuk")+"</td>"+
                                                        "</tr>"+   
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Cavum Uteri</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("cavum_uteri")+",&nbsp;&nbsp;Mobilitas : "+rs3.getString("mobilitas")+"</td>"+
                                                        "</tr>"+ 
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;</td>"+
                                                           "<td border='0' valign='top' width='70%'>&nbsp;&nbsp;&nbsp;Ukuran : "+rs3.getString("ukuran")+",&nbsp;&nbsp;Nyeri Tekan : "+rs3.getString("nyeri_tekan")+"</td>"+
                                                        "</tr>"+ 
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Adnexa/Parametrium</td>"+
                                                           "<td border='0' valign='top' width='70%'>: Kanan : "+rs3.getString("adnexa_kanan")+",&nbsp;&nbsp;Kiri : "+rs3.getString("adnexa_kiri")+"</td>"+
                                                        "</tr>"+ 
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Cavum Douglas</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("cavum_douglas")+"</td>"+
                                                        "</tr>"+ 
                                                    "</table>"+
                                                "</td>"+
                                             "</tr>");                                                                                     
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan ranap
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_ranap.suhu_tubuh,pemeriksaan_ranap.tensi,pemeriksaan_ranap.nadi,pemeriksaan_ranap.respirasi," +
                                        "pemeriksaan_ranap.tinggi,pemeriksaan_ranap.berat,pemeriksaan_ranap.gcs,pemeriksaan_ranap.keluhan,pemeriksaan_ranap.penilaian,pemeriksaan_ranap.rtl," +
                                        "pemeriksaan_ranap.pemeriksaan,pemeriksaan_ranap.alergi,pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat "+
                                        "from pemeriksaan_ranap where pemeriksaan_ranap.no_rawat='"+rs2.getString("no_rawat")+"' order by pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Rawat Inap</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Suhu(C)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Tensi</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Nadi(/menit)</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Respirasi(/menit)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Tinggi(Cm)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Berat(Kg)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>GCS(E,V,M)</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("suhu_tubuh")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tensi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nadi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("respirasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tinggi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("berat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("gcs")+"</td>"+
                                             "</tr>");   
                                        
                                        if(!rs3.getString("keluhan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Keluhan</td>"+
                                                    "<td valign='top' colspan='5'> : "+rs3.getString("keluhan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("pemeriksaan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Pemeriksaan</td>"+
                                                    "<td valign='top' colspan='5'> : "+rs3.getString("pemeriksaan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("penilaian").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Penilaian</td>"+
                                                    "<td valign='top' colspan='5'> : "+rs3.getString("penilaian").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("rtl").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Tindak Lanjut</td>"+
                                                    "<td valign='top' colspan='5'> : "+rs3.getString("rtl").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("alergi").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Alergi</td>"+
                                                    "<td valign='top' colspan='5'> : "+rs3.getString("alergi")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan obstetri ranap
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_obstetri_ranap.tgl_perawatan,pemeriksaan_obstetri_ranap.jam_rawat,pemeriksaan_obstetri_ranap.tinggi_uteri,pemeriksaan_obstetri_ranap.janin,pemeriksaan_obstetri_ranap.letak, " +
                                        "pemeriksaan_obstetri_ranap.panggul,pemeriksaan_obstetri_ranap.denyut,pemeriksaan_obstetri_ranap.kontraksi, " +
                                        "pemeriksaan_obstetri_ranap.kualitas_mnt,pemeriksaan_obstetri_ranap.kualitas_dtk,pemeriksaan_obstetri_ranap.fluksus,pemeriksaan_obstetri_ranap.albus, " +
                                        "pemeriksaan_obstetri_ranap.vulva,pemeriksaan_obstetri_ranap.portio,pemeriksaan_obstetri_ranap.dalam, pemeriksaan_obstetri_ranap.tebal, pemeriksaan_obstetri_ranap.arah, pemeriksaan_obstetri_ranap.pembukaan," +
                                        "pemeriksaan_obstetri_ranap.penurunan, pemeriksaan_obstetri_ranap.denominator, pemeriksaan_obstetri_ranap.ketuban, pemeriksaan_obstetri_ranap.feto " +
                                        "from pasien inner join reg_periksa inner join pemeriksaan_obstetri_ranap "+
                                        "on pemeriksaan_obstetri_ranap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                                        "pemeriksaan_obstetri_ranap.no_rawat='"+rs2.getString("no_rawat")+"' order by pemeriksaan_obstetri_ranap.tgl_perawatan,pemeriksaan_obstetri_ranap.jam_rawat").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Obstetri Rawat Inap</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Tinggi Fundus</td>"+
                                                "<td valign='top' width='6%' bgcolor='#FFFAF8'>Janin</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Letak</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Panggul</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Denyut</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kontraksi</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kualitas Mnt</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kualitas Detik</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Fluksus</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Albus</td>"+
                                                "<td valign='top' width='6%' bgcolor='#FFFAF8'>Dalam</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Tebal</td>"+
                                                "<td valign='top' width='6%' bgcolor='#FFFAF8'>Arah</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Ketuban</td>"+
                                                "<td valign='top' width='7%' bgcolor='#FFFAF8'>Feto</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tinggi_uteri")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("janin")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("letak")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("panggul")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("denyut")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kontraksi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kualitas_mnt")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kualitas_dtk")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("fluksus")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("albus")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("dalam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tebal")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("arah")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("ketuban")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("feto")+"</td>"+
                                             "</tr>"); 
                                        if(!rs3.getString("vulva").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Vulva</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("vulva")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("portio").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Portio</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("portio")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("pembukaan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Pembukaan</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("pembukaan")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("penurunan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Penurunan</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("penurunan")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("denominator").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Denominator</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("denominator")+"</td>"+
                                                 "</tr>");
                                        }
                                            
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan genekologi ranap
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_ginekologi_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                        "pemeriksaan_ginekologi_ranap.tgl_perawatan,pemeriksaan_ginekologi_ranap.jam_rawat,pemeriksaan_ginekologi_ranap.inspeksi,pemeriksaan_ginekologi_ranap.inspeksi_vulva,pemeriksaan_ginekologi_ranap.inspekulo_gine, " +
                                        "pemeriksaan_ginekologi_ranap.fluxus_gine,pemeriksaan_ginekologi_ranap.fluor_gine,pemeriksaan_ginekologi_ranap.vulva_inspekulo, " +
                                        "pemeriksaan_ginekologi_ranap.portio_inspekulo,pemeriksaan_ginekologi_ranap.sondage,pemeriksaan_ginekologi_ranap.portio_dalam,pemeriksaan_ginekologi_ranap.bentuk, " +
                                        "pemeriksaan_ginekologi_ranap.cavum_uteri,pemeriksaan_ginekologi_ranap.mobilitas,pemeriksaan_ginekologi_ranap.ukuran, pemeriksaan_ginekologi_ranap.nyeri_tekan, pemeriksaan_ginekologi_ranap.adnexa_kanan, pemeriksaan_ginekologi_ranap.adnexa_kiri," +
                                        "pemeriksaan_ginekologi_ranap.cavum_douglas " +
                                        "from pasien inner join reg_periksa inner join pemeriksaan_ginekologi_ranap "+
                                        "on pemeriksaan_ginekologi_ranap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                                        "pemeriksaan_ginekologi_ranap.no_rawat='"+rs2.getString("no_rawat")+"' order by pemeriksaan_ginekologi_ranap.tgl_perawatan,pemeriksaan_ginekologi_ranap.jam_rawat").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Ginekologi Rawat Inap</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='80%' bgcolor='#FFFAF8'>Pemeriksaan</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+
                                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>Inspeksi</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("inspeksi")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Vulva/Uretra/Vagina</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("inspeksi_vulva")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>Inspekulo</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("inspekulo_gine")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Fluxus</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("fluxus_gine")+",&nbsp;&nbsp;Fluor Albus : "+rs3.getString("fluor_gine")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Vulva/Vagina</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("vulva_inspekulo")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Portio</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("portio_inspekulo")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Sondage</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("sondage")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>Pemeriksaan Dalam</td>"+
                                                           "<td border='0' valign='top' width='70%'>:</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Portio</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("portio_dalam")+",&nbsp;&nbsp;Bentuk : "+rs3.getString("bentuk")+"</td>"+
                                                        "</tr>"+   
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Cavum Uteri</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("cavum_uteri")+",&nbsp;&nbsp;Mobilitas : "+rs3.getString("mobilitas")+"</td>"+
                                                        "</tr>"+ 
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;</td>"+
                                                           "<td border='0' valign='top' width='70%'>&nbsp;&nbsp;&nbsp;Ukuran : "+rs3.getString("ukuran")+",&nbsp;&nbsp;Nyeri Tekan : "+rs3.getString("nyeri_tekan")+"</td>"+
                                                        "</tr>"+ 
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Adnexa/Parametrium</td>"+
                                                           "<td border='0' valign='top' width='70%'>: Kanan : "+rs3.getString("adnexa_kanan")+",&nbsp;&nbsp;Kiri : "+rs3.getString("adnexa_kiri")+"</td>"+
                                                        "</tr>"+ 
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Cavum Douglas</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("cavum_douglas")+"</td>"+
                                                        "</tr>"+ 
                                                    "</table>"+
                                                "</td>"+
                                             "</tr>");                                                                                     
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //biaya administrasi
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"+
                                     "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                       "<tr>"+
                                         "<td valign='top' width='89%'>Administrasi</td>"+
                                         "<td valign='top' width='1%' align='right'>:</td>"+
                                         "<td valign='top' width='10%' align='right'>"+Valid.SetAngka(rs2.getDouble("biaya_reg"))+"</td>"+
                                       "</tr>"+
                                     "</table>"
                            );
                            
                            //tindakan dokter ralan
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,dokter.nm_dokter,rawat_jl_dr.biaya_rawat, "+
                                        "rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat from rawat_jl_dr inner join jns_perawatan inner join dokter "+
                                        "on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                        "and rawat_jl_dr.kd_dokter=dokter.kd_dokter where rawat_jl_dr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Jalan Dokter</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Dokter</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+" </td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis ralan
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,petugas.nama,rawat_jl_pr.biaya_rawat, "+
                                        "rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat from rawat_jl_pr inner join jns_perawatan inner join petugas "+
                                        "on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                        "and rawat_jl_pr.nip=petugas.nip where rawat_jl_pr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+                                        
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Jalan Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+      
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan ralan dokter dan paramedis
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,dokter.nm_dokter,petugas.nama,rawat_jl_drpr.biaya_rawat, "+
                                        "rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat from rawat_jl_drpr inner join jns_perawatan inner join dokter inner join petugas "+
                                        "on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw and rawat_jl_drpr.nip=petugas.nip "+
                                        "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter where rawat_jl_drpr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Tindakan Rawat Jalan Dokter & Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Dokter</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan dokter ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,"+
                                        "rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                        "dokter.nm_dokter,rawat_inap_dr.biaya_rawat "+
                                        "from rawat_inap_dr inner join jns_perawatan_inap inner join dokter "+
                                        "on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                        "and rawat_inap_dr.kd_dokter=dokter.kd_dokter where rawat_inap_dr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Inap Dokter</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Dokter</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,"+
                                        "rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                        "petugas.nama,rawat_inap_pr.biaya_rawat "+
                                        "from rawat_inap_pr inner join jns_perawatan_inap inner join petugas "+
                                        "on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                        "and rawat_inap_pr.nip=petugas.nip where rawat_inap_pr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Inap Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }      
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis dan dokter ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.kd_jenis_prw,"+
                                        "jns_perawatan_inap.nm_perawatan,dokter.nm_dokter,petugas.nama,rawat_inap_drpr.biaya_rawat "+
                                        "from rawat_inap_drpr inner join jns_perawatan_inap inner join dokter inner join petugas "+
                                        "on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and rawat_inap_drpr.nip=petugas.nip "+
                                        "and rawat_inap_drpr.kd_dokter=dokter.kd_dokter where rawat_inap_drpr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Tindakan Rawat Inap Dokter & Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Dokter</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //kamar inap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.tgl_masuk, kamar_inap.tgl_keluar, "+
                                        "kamar_inap.stts_pulang,kamar_inap.lama,kamar_inap.jam_masuk,kamar_inap.jam_keluar,"+
                                        "kamar_inap.ttl_biaya from kamar_inap inner join bangsal inner join kamar "+
                                        "on kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal  "+
                                        "where kamar_inap.no_rawat='"+rs2.getString("no_rawat")+"' order by kamar_inap.tgl_masuk,kamar_inap.jam_masuk").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Penggunaan Kamar</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal Masuk</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggak Keluar</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Lama Inap</td>"+
                                          "<td valign='top' width='35%' bgcolor='#FFFAF8'>Kamar</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Status</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_masuk")+" "+rs3.getString("jam_masuk")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_keluar")+" "+rs3.getString("jam_keluar")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("lama")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_kamar")+", "+rs3.getString("nm_bangsal")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("stts_pulang")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("ttl_biaya"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //operasi
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select operasi.tgl_operasi,operasi.jenis_anasthesi,operasi.operator1, operasi.operator2, operasi.operator3, operasi.asisten_operator1,"+
                                        "operasi.asisten_operator2,operasi.asisten_operator3,operasi.biayaasisten_operator3, operasi.instrumen, operasi.dokter_anak, operasi.perawaat_resusitas, "+
                                        "operasi.dokter_anestesi, operasi.asisten_anestesi, operasi.asisten_anestesi2,operasi.asisten_anestesi2, operasi.bidan, operasi.bidan2, operasi.bidan3, operasi.perawat_luar, operasi.omloop,"+
                                        "operasi.omloop2,operasi.omloop3,operasi.omloop4,operasi.omloop5,operasi.dokter_pjanak,operasi.dokter_umum, "+
                                        "operasi.kode_paket,paket_operasi.nm_perawatan, operasi.biayaoperator1, operasi.biayaoperator2, operasi.biayaoperator3, "+
                                        "operasi.biayaasisten_operator1, operasi.biayaasisten_operator2, operasi.biayaasisten_operator3, operasi.biayainstrumen, "+
                                        "operasi.biayadokter_anak, operasi.biayaperawaat_resusitas, operasi.biayadokter_anestesi, "+
                                        "operasi.biayaasisten_anestesi,operasi.biayaasisten_anestesi2, operasi.biayabidan,operasi.biayabidan2,operasi.biayabidan3, operasi.biayaperawat_luar, operasi.biayaalat,"+
                                        "operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biaya_omloop,operasi.biaya_omloop2,operasi.biaya_omloop3,operasi.biaya_omloop4,operasi.biaya_omloop5,"+
                                        "operasi.biayasarpras,operasi.biaya_dokter_pjanak,operasi.biaya_dokter_umum,"+
                                        "(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+"+
                                        "operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+"+
                                        "operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+"+
                                        "operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biayaalat+"+
                                        "operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+"+
                                        "operasi.biayasarpras+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum) as total from operasi inner join paket_operasi "+
                                        "on operasi.kode_paket=paket_operasi.kode_paket where operasi.no_rawat='"+rs2.getString("no_rawat")+"' order by operasi.tgl_operasi").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Operasi/VK</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Tindakan</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Anastesi</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_operasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_paket")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+" (");
                                        if(rs3.getDouble("biayaoperator1")>0){
                                            htmlContent.append("Operator 1 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator1"))+", ");
                                        }
                                        if(rs3.getDouble("biayaoperator2")>0){
                                            htmlContent.append("Operator 2 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator2"))+", ");
                                        }
                                        if(rs3.getDouble("biayaoperator3")>0){
                                            htmlContent.append("Operator 3 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator3"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator1")>0){
                                            htmlContent.append("Asisten Operator 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator1"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator2")>0){
                                            htmlContent.append("Asisten Operator 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator2"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator3")>0){
                                            htmlContent.append("Asisten Operator 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator3"))+", ");
                                        }
                                        if(rs3.getDouble("biayainstrumen")>0){
                                            htmlContent.append("Instrumen : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("instrumen"))+", ");
                                        }
                                        if(rs3.getDouble("biayadokter_anak")>0){
                                            htmlContent.append("Dokter Anak : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_anak"))+", ");
                                        }
                                        if(rs3.getDouble("biayaperawaat_resusitas")>0){
                                            htmlContent.append("Perawat Resusitas : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("perawaat_resusitas"))+", ");
                                        }
                                        if(rs3.getDouble("biayadokter_anestesi")>0){
                                            htmlContent.append("Dokter Anestesi : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_anestesi"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_anestesi")>0){
                                            htmlContent.append("Asisten Anestesi : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_anestesi"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_anestesi2")>0){
                                            htmlContent.append("Asisten Anestesi 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_anestesi2"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan")>0){
                                            htmlContent.append("Bidan 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan2")>0){
                                            htmlContent.append("Bidan 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan2"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan3")>0){
                                            htmlContent.append("Bidan 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan3"))+", ");
                                        }
                                        if(rs3.getDouble("biayaperawat_luar")>0){
                                            htmlContent.append("Perawat Luar : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("perawat_luar"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop")>0){
                                            htmlContent.append("Onloop 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop2")>0){
                                            htmlContent.append("Onloop 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop2"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop3")>0){
                                            htmlContent.append("Onloop 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop3"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop4")>0){
                                            htmlContent.append("Onloop 4 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop4"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop5")>0){
                                            htmlContent.append("Onloop 5 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop5"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_dokter_pjanak")>0){
                                            htmlContent.append("Dokter Pj Anak : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_pjanak"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_dokter_umum")>0){
                                            htmlContent.append("Dokter Umum : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_umum"))+", ");
                                        }
                                        htmlContent.append(
                                                ")</td>"+
                                                "<td valign='top'>"+rs3.getString("jenis_anasthesi")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //laporan operasi
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select tanggal, diagnosa_preop, diagnosa_postop, jaringan_dieksekusi, selesaioperasi, permintaan_pa, laporan_operasi "+
                                        "from laporan_operasi where no_rawat='"+rs2.getString("no_rawat")+"' group by no_rawat,tanggal order by tanggal").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Laporan Operasi :</td></tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'>"+w+"</td>"+
                                                "<td valign='top' width='20%'>Mulai Operasi</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("tanggal")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Diagnosa Pre-operatif</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("diagnosa_preop")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Jaringan Yang di-Eksisi/-Insisi</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("jaringan_dieksekusi")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Diagnosa Post-operatif</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("diagnosa_postop")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Selesai Operasi</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("selesaioperasi")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Dikirim Untuk Pemeriksaan PA</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("permintaan_pa")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Laporan</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("laporan_operasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.kd_jenis_prw, "+
                                     "jns_perawatan_radiologi.nm_perawatan,petugas.nama,periksa_radiologi.biaya,periksa_radiologi.dokter_perujuk,dokter.nm_dokter "+
                                     "from periksa_radiologi inner join jns_perawatan_radiologi inner join petugas inner join dokter "+
                                     "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw and periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                                     "and periksa_radiologi.nip=petugas.nip  where periksa_radiologi.no_rawat='"+rs2.getString("no_rawat")+"' order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemeriksaan Radiologi</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Pemeriksaan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Dokter PJ</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Petugas</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //hasil pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select tgl_periksa,jam, hasil from hasil_radiologi where no_rawat='"+rs2.getString("no_rawat")+"' order by tgl_periksa,jam").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Bacaan/Hasil Radiologi</td></tr>"+  
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='80%' bgcolor='#FFFAF8'>Hasil Pemeriksaan</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("hasil").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //gambar pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select tgl_periksa,jam, lokasi_gambar from gambar_radiologi where no_rawat='"+rs2.getString("no_rawat")+"' order by tgl_periksa,jam").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Gambar Radiologi</td></tr>"+  
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='80%' bgcolor='#FFFAF8'>Gambar Radiologi</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/radiologi/"+rs3.getString("lokasi_gambar")+"'>"+rs3.getString("lokasi_gambar").replaceAll("pages/upload/","")+"</a></td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan pemeriksaan laborat
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw, "+
                                     "jns_perawatan_lab.nm_perawatan,petugas.nama,periksa_lab.biaya,periksa_lab.dokter_perujuk,dokter.nm_dokter "+
                                     "from periksa_lab inner join jns_perawatan_lab inner join petugas inner join dokter "+
                                     "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw and periksa_lab.kd_dokter=dokter.kd_dokter "+
                                     "and periksa_lab.nip=petugas.nip  where periksa_lab.no_rawat='"+rs2.getString("no_rawat")+"' order by periksa_lab.tgl_periksa,periksa_lab.jam").executeQuery();
                                if(rs3.next()){
                                    tanggal=rs3.getString("tgl_periksa");
                                    jam=rs3.getString("jam");
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemeriksaan Laboratorium</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Pemeriksaan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Dokter PJ</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Petugas</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya"))+"</td>"+
                                             "</tr>"
                                        ); 
                                        try {
                                            rs4=koneksi.prepareStatement(
                                                "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,"+
                                                "template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                                "detail_periksa_lab.keterangan from detail_periksa_lab inner join "+
                                                "template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                                "where detail_periksa_lab.no_rawat='"+rs2.getString("no_rawat")+"' and "+
                                                "detail_periksa_lab.kd_jenis_prw='"+rs3.getString("kd_jenis_prw")+"' and "+
                                                "detail_periksa_lab.tgl_periksa='"+rs3.getString("tgl_periksa")+"' and "+
                                                "detail_periksa_lab.jam='"+rs3.getString("jam")+"' order by detail_periksa_lab.kd_jenis_prw,template_laboratorium.urut ").executeQuery();
                                            if(rs4.next()){ 
                                                htmlContent.append(
                                                    "<tr>"+
                                                       "<td valign='top' align='center'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top' align='center' bgcolor='#FFFAF8'>Detail Pemeriksaan</td>"+
                                                       "<td valign='top' align='center' bgcolor='#FFFAF8'>Hasil</td>"+
                                                       "<td valign='top' align='center' bgcolor='#FFFAF8'>Nilai Rujukan</td>"+
                                                       "<td valign='top' align='right'></td>"+
                                                    "</tr>");
                                                rs4.beforeFirst();
                                                while(rs4.next()){
                                                    htmlContent.append(
                                                        "<tr>"+
                                                           "<td valign='top' align='center'></td>"+
                                                           "<td valign='top'></td>"+
                                                           "<td valign='top'></td>"+
                                                           "<td valign='top'>"+rs4.getString("Pemeriksaan")+"</td>"+
                                                           "<td valign='top'>"+rs4.getString("nilai")+" "+rs4.getString("satuan")+"</td>"+
                                                           "<td valign='top'>"+rs4.getString("nilai_rujukan")+"</td>"+
                                                           "<td valign='top' align='right'>"+Valid.SetAngka(rs4.getDouble("biaya_item"))+"</td>"+
                                                        "</tr>"); 
                                                }                                               
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notifikasi : "+e);
                                        } finally{
                                            if(rs4!=null){
                                                rs4.close();
                                            }
                                        }
                                        w++;
                                    }
                                    
                                    try {
                                        rs4=koneksi.prepareStatement("select saran,kesan from saran_kesan_lab where no_rawat='"+rs2.getString("no_rawat")+"' and tgl_periksa='"+tanggal+"' and jam='"+jam+"'").executeQuery();
                                        if(rs4.next()){      
                                            htmlContent.append(
                                                    "<tr>"+
                                                       "<td valign='top' align='center'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top'>Kesan</td>"+
                                                       "<td valign='top' colspan='4'>: "+rs4.getString("kesan")+"</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                       "<td valign='top' align='center'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top'>Saran</td>"+
                                                       "<td valign='top' colspan='4'>: "+rs4.getString("saran")+"</td>"+
                                                    "</tr>");
                                        } 
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs4!=null){
                                            rs4.close();
                                        }
                                    }
                                    
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi Lab : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //pemberian obat
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,databarang.kode_sat, "+
                                    "detail_pemberian_obat.kode_brng,detail_pemberian_obat.jml,detail_pemberian_obat.total,"+
                                    "databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                                    "on detail_pemberian_obat.kode_brng=databarang.kode_brng  "+
                                    "where detail_pemberian_obat.no_rawat='"+rs2.getString("no_rawat")+"' order by detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemberian Obat/BHP/Alkes</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='35%' bgcolor='#FFFAF8'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Aturan Pakai</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jml")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top'>"+Sequel.cariIsi("select aturan from aturan_pakai where tgl_perawatan='"+rs3.getString("tgl_perawatan")+"' and jam='"+rs3.getString("jam")+"' and no_rawat='"+rs2.getString("no_rawat")+"' and kode_brng='"+rs3.getString("kode_brng")+"'")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //pemberian obat Operasi
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select beri_obat_operasi.tanggal,beri_obat_operasi.kd_obat,beri_obat_operasi.hargasatuan,obatbhp_ok.kode_sat, "+
                                    "beri_obat_operasi.jumlah, obatbhp_ok.nm_obat,(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) as total "+
                                    "from beri_obat_operasi inner join obatbhp_ok  on  beri_obat_operasi.kd_obat=obatbhp_ok.kd_obat  "+
                                    "where beri_obat_operasi.no_rawat='"+rs2.getString("no_rawat")+"' order by beri_obat_operasi.tanggal").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Penggunaan Obat/BHP Operasi</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Obat/BHP</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tanggal")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_obat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_obat")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jumlah")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Resep Pulang
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select resep_pulang.kode_brng,databarang.nama_brng,resep_pulang.dosis,resep_pulang.jml_barang, "+
                                    "databarang.kode_sat,resep_pulang.dosis,resep_pulang.total from resep_pulang inner join databarang "+
                                    "on resep_pulang.kode_brng=databarang.kode_brng where "+
                                    "resep_pulang.no_rawat='"+rs2.getString("no_rawat")+"' order by databarang.nama_brng").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Resep Pulang</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Dosis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("dosis")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jml_barang")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Retur Obat
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select databarang.kode_brng,databarang.nama_brng,detreturjual.kode_sat,detreturjual.h_retur, "+
				    "(detreturjual.jml_retur * -1) as jumlah,(detreturjual.subtotal * -1) as total from detreturjual "+
				    "inner join databarang inner join returjual on detreturjual.kode_brng=databarang.kode_brng "+
				    "and returjual.no_retur_jual=detreturjual.no_retur_jual where returjual.no_retur_jual='"+rs2.getString("no_rawat")+"' order by databarang.nama_brng").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Retur Obat</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='65%' bgcolor='#FFFAF8'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jumlah")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Tambahan Biaya
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select nama_biaya, besar_biaya from tambahan_biaya where no_rawat='"+rs2.getString("no_rawat")+"' order by nama_biaya").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='2'>Tambahan Biaya</td><td valign='top' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='84%' bgcolor='#FFFAF8'>Nama Tambahan</td>"+
                                          "<td valign='top' width='1%' bgcolor='#FFFAF8'></td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_biaya")+"</td>"+
                                                "<td valign='top'></td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("besar_biaya"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Pengurangan Biaya
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select nama_pengurangan, (-1*besar_pengurangan) as besar_pengurangan from pengurangan_biaya where no_rawat='"+rs2.getString("no_rawat")+"' order by nama_pengurangan").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='2'>Potongan Biaya</td><td valign='top' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='84%' bgcolor='#FFFAF8'>Nama Potongan</td>"+
                                          "<td valign='top' width='1%' bgcolor='#FFFAF8'></td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_pengurangan")+"</td>"+
                                                "<td valign='top'></td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("besar_pengurangan"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //berkas digital perawatan
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select master_berkas_digital.nama,berkas_digital_perawatan.lokasi_file "+
				    "from berkas_digital_perawatan inner join master_berkas_digital "+
                                    "on berkas_digital_perawatan.kode=master_berkas_digital.kode "+
                                    "where berkas_digital_perawatan.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Berkas Digital Perawatan</td></tr>"+  
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='95%' bgcolor='#FFFAF8'>Berkas Digital</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/berkasrawat/"+rs3.getString("lokasi_file")+"'>"+rs3.getString("nama").replaceAll("pages/upload/","")+"</a></td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }        
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    public void tampil2(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(caritanggal==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            //menampilkan diagnosa penyakit                            
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit, diagnosa_pasien.status "+
                                        "from diagnosa_pasien inner join penyakit "+
                                        "on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                                        "where diagnosa_pasien.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Diagnosa/Penyakit/ICD 10</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'><td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td><td valign='top' width='24%' bgcolor='#FFFAF8'>Kode</td><td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Penyakit</td><td valign='top' width='23%' bgcolor='#FFFAF8'>Status</td></tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append("<tr><td valign='top' align='center'>"+w+"</td><td valign='top'>"+rs3.getString("kd_penyakit")+"</td><td valign='top'>"+rs3.getString("nm_penyakit")+"</td><td valign='top'>"+rs3.getString("status")+"</td></tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                    
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }    
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML2.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil3(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(caritanggal==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            //menampilkan prosedur tindakan
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select prosedur_pasien.kode,icd9.deskripsi_panjang, prosedur_pasien.status "+
                                        "from prosedur_pasien inner join icd9 "+
                                        "on prosedur_pasien.kode=icd9.kode "+
                                        "where prosedur_pasien.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prosedur Tindakan/ICD 9</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'><td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td><td valign='top' width='24%' bgcolor='#FFFAF8'>Kode</td><td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Prosedur</td><td valign='top' width='23%' bgcolor='#FFFAF8'>Status</td></tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append("<tr><td valign='top' align='center'>"+w+"</td><td valign='top'>"+rs3.getString("kode")+"</td><td valign='top'>"+rs3.getString("deskripsi_panjang")+"</td><td valign='top'>"+rs3.getString("status")+"</td></tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML3.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil4(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(caritanggal==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            //tindakan dokter ralan
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,dokter.nm_dokter,rawat_jl_dr.biaya_rawat, "+
                                        "rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat from rawat_jl_dr inner join jns_perawatan inner join dokter "+
                                        "on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                        "and rawat_jl_dr.kd_dokter=dokter.kd_dokter where rawat_jl_dr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Jalan Dokter</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Dokter</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis ralan
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,petugas.nama,rawat_jl_pr.biaya_rawat, "+
                                        "rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat from rawat_jl_pr inner join jns_perawatan inner join petugas "+
                                        "on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                        "and rawat_jl_pr.nip=petugas.nip where rawat_jl_pr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+                                        
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Jalan Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+      
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan ralan dokter dan paramedis
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,dokter.nm_dokter,petugas.nama,rawat_jl_drpr.biaya_rawat, "+
                                        "rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat from rawat_jl_drpr inner join jns_perawatan inner join dokter inner join petugas "+
                                        "on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw and rawat_jl_drpr.nip=petugas.nip "+
                                        "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter where rawat_jl_drpr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Tindakan Rawat Jalan Dokter & Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='25%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Dokter</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML4.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil5(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(caritanggal==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //tindakan dokter ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,"+
                                        "rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                        "dokter.nm_dokter,rawat_inap_dr.biaya_rawat "+
                                        "from rawat_inap_dr inner join jns_perawatan_inap inner join dokter "+
                                        "on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                        "and rawat_inap_dr.kd_dokter=dokter.kd_dokter where rawat_inap_dr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Inap Dokter</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Dokter</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,"+
                                        "rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                        "petugas.nama,rawat_inap_pr.biaya_rawat "+
                                        "from rawat_inap_pr inner join jns_perawatan_inap inner join petugas "+
                                        "on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                        "and rawat_inap_pr.nip=petugas.nip where rawat_inap_pr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Inap Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }      
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis dan dokter ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.kd_jenis_prw,"+
                                        "jns_perawatan_inap.nm_perawatan,dokter.nm_dokter,petugas.nama,rawat_inap_drpr.biaya_rawat "+
                                        "from rawat_inap_drpr inner join jns_perawatan_inap inner join dokter inner join petugas "+
                                        "on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and rawat_inap_drpr.nip=petugas.nip "+
                                        "and rawat_inap_drpr.kd_dokter=dokter.kd_dokter where rawat_inap_drpr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Tindakan Rawat Inap Dokter & Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Dokter</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML5.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil6(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(caritanggal==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //operasi
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select operasi.tgl_operasi,operasi.jenis_anasthesi,operasi.operator1, operasi.operator2, operasi.operator3, operasi.asisten_operator1,"+
                                        "operasi.asisten_operator2,operasi.asisten_operator3, operasi.instrumen, operasi.dokter_anak, operasi.perawaat_resusitas, "+
                                        "operasi.dokter_anestesi, operasi.asisten_anestesi, operasi.asisten_anestesi2, operasi.bidan, operasi.bidan2, operasi.bidan3, operasi.perawat_luar, operasi.omloop,"+
                                        "operasi.omloop2,operasi.omloop3,operasi.omloop4,operasi.omloop5,operasi.dokter_pjanak,operasi.dokter_umum, "+
                                        "operasi.kode_paket,paket_operasi.nm_perawatan, operasi.biayaoperator1, operasi.biayaoperator2, operasi.biayaoperator3, "+
                                        "operasi.biayaasisten_operator1, operasi.biayaasisten_operator2, operasi.biayaasisten_operator3, operasi.biayainstrumen, "+
                                        "operasi.biayadokter_anak, operasi.biayaperawaat_resusitas, operasi.biayadokter_anestesi, "+
                                        "operasi.biayaasisten_anestesi,operasi.biayaasisten_anestesi2, operasi.biayabidan,operasi.biayabidan2,operasi.biayabidan3, operasi.biayaperawat_luar, operasi.biayaalat,"+
                                        "operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biaya_omloop,operasi.biaya_omloop2,operasi.biaya_omloop3,operasi.biaya_omloop4,operasi.biaya_omloop5,"+
                                        "operasi.biayasarpras,operasi.biaya_dokter_pjanak,operasi.biaya_dokter_umum,"+
                                        "(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+"+
                                        "operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+"+
                                        "operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+"+
                                        "operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biayaalat+"+
                                        "operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+"+
                                        "operasi.biayasarpras+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum) as total from operasi inner join paket_operasi "+
                                        "on operasi.kode_paket=paket_operasi.kode_paket where operasi.no_rawat='"+rs2.getString("no_rawat")+"' order by operasi.tgl_operasi").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Operasi/VK</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Tindakan</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Anastesi</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_operasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_paket")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+" (");
                                        if(rs3.getDouble("biayaoperator1")>0){
                                            htmlContent.append("Operator 1 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator1"))+", ");
                                        }
                                        if(rs3.getDouble("biayaoperator2")>0){
                                            htmlContent.append("Operator 2 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator2"))+", ");
                                        }
                                        if(rs3.getDouble("biayaoperator3")>0){
                                            htmlContent.append("Operator 3 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator3"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator1")>0){
                                            htmlContent.append("Asisten Operator 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator1"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator2")>0){
                                            htmlContent.append("Asisten Operator 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator2"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator3")>0){
                                            htmlContent.append("Asisten Operator 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator3"))+", ");
                                        }
                                        if(rs3.getDouble("biayainstrumen")>0){
                                            htmlContent.append("Instrumen : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("instrumen"))+", ");
                                        }
                                        if(rs3.getDouble("biayadokter_anak")>0){
                                            htmlContent.append("Dokter Anak : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_anak"))+", ");
                                        }
                                        if(rs3.getDouble("biayaperawaat_resusitas")>0){
                                            htmlContent.append("Perawat Resusitas : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("perawaat_resusitas"))+", ");
                                        }
                                        if(rs3.getDouble("biayadokter_anestesi")>0){
                                            htmlContent.append("Dokter Anestesi : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_anestesi"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_anestesi")>0){
                                            htmlContent.append("Asisten Anestesi : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_anestesi"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_anestesi2")>0){
                                            htmlContent.append("Asisten Anestesi 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_anestesi2"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan")>0){
                                            htmlContent.append("Bidan 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan2")>0){
                                            htmlContent.append("Bidan 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan2"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan3")>0){
                                            htmlContent.append("Bidan 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan3"))+", ");
                                        }
                                        if(rs3.getDouble("biayaperawat_luar")>0){
                                            htmlContent.append("Perawat Luar : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("perawat_luar"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop")>0){
                                            htmlContent.append("Onloop 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop2")>0){
                                            htmlContent.append("Onloop 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop2"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop3")>0){
                                            htmlContent.append("Onloop 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop3"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop4")>0){
                                            htmlContent.append("Onloop 4 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop4"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop5")>0){
                                            htmlContent.append("Onloop 5 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop5"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_dokter_pjanak")>0){
                                            htmlContent.append("Dokter Pj Anak : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_pjanak"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_dokter_umum")>0){
                                            htmlContent.append("Dokter Umum : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_umum"))+", ");
                                        }
                                        htmlContent.append(
                                                ")</td>"+
                                                "<td valign='top'>"+rs3.getString("jenis_anasthesi")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //laporan operasi
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select tanggal, diagnosa_preop, diagnosa_postop, jaringan_dieksekusi, selesaioperasi, permintaan_pa, laporan_operasi "+
                                        "from laporan_operasi where no_rawat='"+rs2.getString("no_rawat")+"' group by no_rawat,tanggal order by tanggal").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Laporan Operasi :</td></tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'>"+w+"</td>"+
                                                "<td valign='top' width='20%'>Mulai Operasi</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("tanggal")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Diagnosa Pre-operatif</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("diagnosa_preop")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Jaringan Yang di-Eksisi/-Insisi</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("jaringan_dieksekusi")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Diagnosa Post-operatif</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("diagnosa_postop")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Selesai Operasi</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("selesaioperasi")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Dikirim Untuk Pemeriksaan PA</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("permintaan_pa")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Laporan</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("laporan_operasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML6.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil7(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(caritanggal==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //tindakan pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.kd_jenis_prw, "+
                                     "jns_perawatan_radiologi.nm_perawatan,petugas.nama,periksa_radiologi.biaya,periksa_radiologi.dokter_perujuk,dokter.nm_dokter "+
                                     "from periksa_radiologi inner join jns_perawatan_radiologi inner join petugas inner join dokter "+
                                     "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw and periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                                     "and periksa_radiologi.nip=petugas.nip  where periksa_radiologi.no_rawat='"+rs2.getString("no_rawat")+"' order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemeriksaan Radiologi</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Pemeriksaan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Dokter PJ</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Petugas</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //hasil pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select tgl_periksa,jam, hasil from hasil_radiologi where no_rawat='"+rs2.getString("no_rawat")+"' order by tgl_periksa,jam").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Bacaan/Hasil Radiologi</td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='80%' bgcolor='#FFFAF8'>Hasil Pemeriksaan</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("hasil").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //gambar pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select tgl_periksa,jam, lokasi_gambar from gambar_radiologi where no_rawat='"+rs2.getString("no_rawat")+"' order by tgl_periksa,jam").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Gambar Radiologi</td></tr>"+  
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='80%' bgcolor='#FFFAF8'>Gambar Radiologi</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/radiologi/"+rs3.getString("lokasi_gambar")+"'>"+rs3.getString("lokasi_gambar").replaceAll("pages/upload/","")+"</a></td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML7.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil8(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(caritanggal==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //tindakan pemeriksaan laborat
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw, "+
                                     "jns_perawatan_lab.nm_perawatan,petugas.nama,periksa_lab.biaya,periksa_lab.dokter_perujuk,dokter.nm_dokter "+
                                     "from periksa_lab inner join jns_perawatan_lab inner join petugas inner join dokter "+
                                     "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw and periksa_lab.kd_dokter=dokter.kd_dokter "+
                                     "and periksa_lab.nip=petugas.nip  where periksa_lab.no_rawat='"+rs2.getString("no_rawat")+"' order by periksa_lab.tgl_periksa,periksa_lab.jam").executeQuery();
                                if(rs3.next()){ 
                                    tanggal=rs3.getString("tgl_periksa");
                                    jam=rs3.getString("jam");
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemeriksaan Laboratorium</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Pemeriksaan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Dokter PJ</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Petugas</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya"))+"</td>"+
                                             "</tr>"
                                        ); 
                                        try {
                                            rs4=koneksi.prepareStatement(
                                                "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,"+
                                                "template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                                "detail_periksa_lab.keterangan from detail_periksa_lab inner join "+
                                                "template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                                "where detail_periksa_lab.no_rawat='"+rs2.getString("no_rawat")+"' and "+
                                                "detail_periksa_lab.kd_jenis_prw='"+rs3.getString("kd_jenis_prw")+"' and "+
                                                "detail_periksa_lab.tgl_periksa='"+rs3.getString("tgl_periksa")+"' and "+
                                                "detail_periksa_lab.jam='"+rs3.getString("jam")+"' order by detail_periksa_lab.kd_jenis_prw,template_laboratorium.urut").executeQuery();
                                            if(rs4.next()){ 
                                                htmlContent.append(
                                                    "<tr>"+
                                                       "<td valign='top' align='center'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top' align='center' bgcolor='#FFFAF8'>Detail Pemeriksaan</td>"+
                                                       "<td valign='top' align='center' bgcolor='#FFFAF8'>Hasil</td>"+
                                                       "<td valign='top' align='center' bgcolor='#FFFAF8'>Nilai Rujukan</td>"+
                                                       "<td valign='top' align='right'></td>"+
                                                    "</tr>");
                                                rs4.beforeFirst();
                                                while(rs4.next()){
                                                    htmlContent.append(
                                                        "<tr>"+
                                                           "<td valign='top' align='center'></td>"+
                                                           "<td valign='top'></td>"+
                                                           "<td valign='top'></td>"+
                                                           "<td valign='top'>"+rs4.getString("Pemeriksaan")+"</td>"+
                                                           "<td valign='top'>"+rs4.getString("nilai")+" "+rs4.getString("satuan")+"</td>"+
                                                           "<td valign='top'>"+rs4.getString("nilai_rujukan")+"</td>"+
                                                           "<td valign='top' align='right'>"+Valid.SetAngka(rs4.getDouble("biaya_item"))+"</td>"+
                                                        "</tr>"); 
                                                }                                               
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notifikasi : "+e);
                                        } finally{
                                            if(rs4!=null){
                                                rs4.close();
                                            }
                                        }
                                        w++;
                                    }
                                    
                                    try {
                                        rs4=koneksi.prepareStatement("select saran,kesan from saran_kesan_lab where no_rawat='"+rs2.getString("no_rawat")+"' and tgl_periksa='"+tanggal+"' and jam='"+jam+"'").executeQuery();
                                        if(rs4.next()){      
                                            htmlContent.append(
                                                    "<tr>"+
                                                       "<td valign='top' align='center'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top'>Kesan</td>"+
                                                       "<td valign='top' colspan='4'>: "+rs4.getString("kesan")+"</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                       "<td valign='top' align='center'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top'>Saran</td>"+
                                                       "<td valign='top' colspan='4'>: "+rs4.getString("saran")+"</td>"+
                                                    "</tr>");
                                        } 
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs4!=null){
                                            rs4.close();
                                        }
                                    }
                                    
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML8.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil9(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(caritanggal==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //pemberian obat
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,databarang.kode_sat, "+
                                    "detail_pemberian_obat.kode_brng,detail_pemberian_obat.jml,detail_pemberian_obat.total,"+
                                    "databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                                    "on detail_pemberian_obat.kode_brng=databarang.kode_brng  "+
                                    "where detail_pemberian_obat.no_rawat='"+rs2.getString("no_rawat")+"' order by detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemberian Obat/BHP/Alkes</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='35%' bgcolor='#FFFAF8'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Aturan Pakai</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jml")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top'>"+Sequel.cariIsi("select aturan from aturan_pakai where tgl_perawatan='"+rs3.getString("tgl_perawatan")+"' and jam='"+rs3.getString("jam")+"' and no_rawat='"+rs2.getString("no_rawat")+"' and kode_brng='"+rs3.getString("kode_brng")+"'")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Retur Obat
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select databarang.kode_brng,databarang.nama_brng,detreturjual.kode_sat,detreturjual.h_retur, "+
				    "(detreturjual.jml_retur * -1) as jumlah,(detreturjual.subtotal * -1) as total from detreturjual "+
				    "inner join databarang inner join returjual on detreturjual.kode_brng=databarang.kode_brng "+
				    "and returjual.no_retur_jual=detreturjual.no_retur_jual where returjual.no_retur_jual='"+rs2.getString("no_rawat")+"' order by databarang.nama_brng").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Retur Obat</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='65%' bgcolor='#FFFAF8'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jumlah")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML9.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil10(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(caritanggal==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //pemberian obat Operasi
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select beri_obat_operasi.tanggal,beri_obat_operasi.kd_obat,beri_obat_operasi.hargasatuan,obatbhp_ok.kode_sat, "+
                                    "beri_obat_operasi.jumlah, obatbhp_ok.nm_obat,(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) as total "+
                                    "from beri_obat_operasi inner join obatbhp_ok  on  beri_obat_operasi.kd_obat=obatbhp_ok.kd_obat  "+
                                    "where beri_obat_operasi.no_rawat='"+rs2.getString("no_rawat")+"' order by beri_obat_operasi.tanggal").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Penggunaan Obat/BHP Operasi</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Obat/BHP</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tanggal")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_obat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_obat")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jumlah")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML10.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil11(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(caritanggal==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );
                            
                            //Resep Pulang
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select resep_pulang.kode_brng,databarang.nama_brng,resep_pulang.dosis,resep_pulang.jml_barang, "+
                                    "databarang.kode_sat,resep_pulang.dosis,resep_pulang.total from resep_pulang inner join databarang "+
                                    "on resep_pulang.kode_brng=databarang.kode_brng where "+
                                    "resep_pulang.no_rawat='"+rs2.getString("no_rawat")+"' order by databarang.nama_brng").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Resep Pulang</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Dosis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("dosis")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jml_barang")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML11.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void tampil12(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(caritanggal==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"
                            );                            
                            
                            //berkas digital perawatan
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select master_berkas_digital.nama,berkas_digital_perawatan.lokasi_file "+
                                        "from berkas_digital_perawatan inner join master_berkas_digital "+
                                        "on berkas_digital_perawatan.kode=master_berkas_digital.kode "+
                                        "where berkas_digital_perawatan.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Berkas Digital Perawatan</td></tr>"+  
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='95%' bgcolor='#FFFAF8'>Berkas Digital</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/radiologi/"+rs3.getString("lokasi_file")+"'>"+rs3.getString("nama").replaceAll("pages/upload/","")+"</a></td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML12.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void tampil13() {
        Valid.tabelKosong(tabModeRegistrasi);
        try{   
            if(caritanggal==true){
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.status_lanjut,"+
                    "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "poliklinik.kd_poli,poliklinik.nm_poli,penjab.png_jawab from reg_periksa inner join dokter inner join "+
                    "poliklinik inner join penjab on reg_periksa.kd_dokter=dokter.kd_dokter and "+
                    "reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "where stts<>'Batal' and reg_periksa.no_rkm_medis=? and "+
                    "reg_periksa.tgl_registrasi between ? and ? order by reg_periksa.tgl_registrasi");
            }else{
                ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,reg_periksa.status_lanjut,"+
                    "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "poliklinik.kd_poli,poliklinik.nm_poli,penjab.png_jawab from reg_periksa inner join dokter inner join "+
                    "poliklinik inner join penjab on reg_periksa.kd_dokter=dokter.kd_dokter and "+
                    "reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "where stts<>'Batal' and reg_periksa.no_rkm_medis=? order by reg_periksa.tgl_registrasi");
            }
            
            try {
                i=0;
                if(caritanggal==true){
                    ps.setString(1,norm);
                    ps.setString(2,tanggal1);
                    ps.setString(3,tanggal2);
                }else{
                    ps.setString(1,norm);
                }                    
                rs=ps.executeQuery();
                while(rs.next()){
                    i++;
                    tabModeRegistrasi.addRow(new String[]{
                        i+"",rs.getString("no_rawat"),rs.getString("tgl_registrasi"),rs.getString("jam_reg"),
                        rs.getString("kd_dokter"),rs.getString("nm_dokter"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                        rs.getString("kd_poli")+" "+rs.getString("nm_poli"),rs.getString("png_jawab")
                    });
                    ps2=koneksi.prepareStatement(
                            "select rujukan_internal_poli.kd_dokter,dokter.nm_dokter,"+
                            "rujukan_internal_poli.kd_poli,poliklinik.nm_poli from rujukan_internal_poli "+
                            "inner join dokter inner join poliklinik on rujukan_internal_poli.kd_dokter=dokter.kd_dokter "+
                            "and rujukan_internal_poli.kd_poli=poliklinik.kd_poli where rujukan_internal_poli.no_rawat=?");
                    try {
                        ps2.setString(1,rs.getString("no_rawat"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){                            
                            i++;
                            tabModeRegistrasi.addRow(new String[]{
                                i+"",rs.getString("no_rawat"),rs.getString("tgl_registrasi"),"",
                                rs2.getString("kd_dokter"),rs2.getString("nm_dokter"),rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                                rs2.getString("kd_poli")+" "+rs2.getString("nm_poli"),rs.getString("png_jawab")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }  
                    kddpjp="";
                    dpjp="";
                    if(rs.getString("status_lanjut").equals("Ranap")){
                        kddpjp=Sequel.cariIsi("select kd_dokter from dpjp_ranap where no_rawat=?",rs.getString("no_rawat"));
                        if(!kddpjp.equals("")){
                            dpjp=Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",kddpjp);
                        }else{
                            kddpjp=rs.getString("kd_dokter");
                            dpjp=rs.getString("nm_dokter");
                        }
                    }                        
                    ps2=koneksi.prepareStatement(
                            "select kamar_inap.tgl_masuk,kamar_inap.jam_masuk,kamar_inap.kd_kamar,bangsal.nm_bangsal "+
                            "from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                            "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=?");
                    try {
                        ps2.setString(1,rs.getString("no_rawat"));
                        rs2=ps2.executeQuery();
                        while(rs2.next()){                            
                            i++;                            
                            tabModeRegistrasi.addRow(new String[]{
                                i+"",rs.getString("no_rawat"),rs2.getString("tgl_masuk"),rs2.getString("jam_masuk"),
                                kddpjp,dpjp,rs.getString("umurdaftar")+" "+rs.getString("sttsumur"),
                                rs2.getString("kd_kamar")+" "+rs2.getString("nm_bangsal"),rs.getString("png_jawab")
                            });
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    } 
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }                
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
    }

    private void panggilLaporan(String teks) {
        try{
            File g = new File("file.css");            
            BufferedWriter bg = new BufferedWriter(new FileWriter(g));
            bg.write(".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}.isi a{text-decoration:none;color:#8b9b95;padding:0 0 0 0px;font-family: Tahoma;font-size: 8.5px;}");
            bg.close();

            File f = new File("resumemedis.html");            
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));
            bw.write(teks.replaceAll(
                    "<head>","<head><link href=\"file.css\" rel=\"stylesheet\" type=\"text/css\" />")
            );  bw.close();
            Desktop.getDesktop().browse(f.toURI());
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }   
    }
    
    public void tampil14(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        if(caritanggal==true){
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' and "+
                                   "reg_periksa.tgl_registrasi between '"+tanggal1+"' and '"+tanggal2+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }else{
                            rs2=koneksi.prepareStatement(
                                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                                   "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                                   "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                                   "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                                   "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                                   "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi,reg_periksa.jam_reg").executeQuery();
                        }
                            
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            
                            //menampilkan catatan dokter
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select catatan_perawatan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                        "catatan_perawatan.tanggal,catatan_perawatan.jam,catatan_perawatan.kd_dokter,dokter.nm_dokter,"+
                                        "catatan_perawatan.catatan from pasien inner join reg_periksa inner join catatan_perawatan inner join dokter "+
                                        "on catatan_perawatan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                        "and catatan_perawatan.kd_dokter=dokter.kd_dokter "+
                                        "where catatan_perawatan.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Catatan Dokter</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode Dokter</td>"+
                                                "<td valign='top' width='20%' bgcolor='#FFFAF8'>Nama Dokter</td>"+
                                                "<td valign='top' width='50%' bgcolor='#FFFAF8'>Catatan</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tanggal")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("catatan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                             "</tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }    
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML13.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void tampil15(){     
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try{
            htmlContent = new StringBuilder();
            try {
                rs=koneksi.prepareStatement("select pasien.no_rkm_medis, pasien.nm_pasien, pasien.jk, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.umur, "+
                   "tmp_lahir,tgl_lahir,nm_ibu,gol_darah,stts_nikah,agama,pnd,tgl_daftar from pasien inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and "+
                   "pasien.kd_kab=kabupaten.kd_kab where pasien.no_rkm_medis='"+norm+"' order by pasien.no_rkm_medis desc ").executeQuery();
                y=1;
                while(rs.next()){   
                    htmlContent.append(
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>No.RM</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("no_rkm_medis")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Nama Pasien</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_pasien")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Alamat</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("alamat")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Umur</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("umur")+" ("+rs.getString("jk").replaceAll("L","Laki-Laki").replaceAll("P","Perempuan")+")</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Tempat & Tanggal Lahir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tmp_lahir")+" "+rs.getString("tgl_lahir")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Ibu Kandung</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("nm_ibu")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Golongan Darah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("gol_darah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Status Nikah</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("stts_nikah")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Agama</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("agama")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pendidikan Terakhir</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("pnd")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Pertama Daftar</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'>"+rs.getString("tgl_daftar")+"</td>"+
                        "</tr>"+
                        "<tr class='isi'>"+ 
                          "<td valign='top' width='20%'>Riwayat Perawatan</td>"+
                          "<td valign='top' width='1%' align='center'>:</td>"+
                          "<td valign='top' width='79%'></td>"+
                        "</tr>"
                    );
                    try {
                        rs2=koneksi.prepareStatement(
                               "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                               "reg_periksa.kd_dokter,dokter.nm_dokter,poliklinik.nm_poli,reg_periksa.p_jawab,reg_periksa.almt_pj,"+
                               "reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.status_lanjut,penjab.png_jawab "+
                               "from reg_periksa inner join dokter inner join poliklinik inner join penjab "+
                               "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                               "and reg_periksa.kd_poli=poliklinik.kd_poli where stts<>'Batal' and "+
                               "reg_periksa.no_rkm_medis='"+rs.getString("no_rkm_medis")+"' order by reg_periksa.tgl_registrasi desc limit 3").executeQuery();
                        urut=1;
                        while(rs2.next()){      
                            htmlContent.append(
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;"+urut+". No.Rawat</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_rawat")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;No.Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("no_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Tanggal Registrasi</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("tgl_registrasi")+" "+rs2.getString("jam_reg")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Unit/Poliklinik</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_poli")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Dokter</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("nm_dokter")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cara Bayar</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("png_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Penanggung Jawab</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("p_jawab")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Alamat P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("almt_pj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Hubungan P.J.</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("hubunganpj")+"</td>"+
                              "</tr>"+
                              "<tr class='isi'>"+ 
                                "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Status</td>"+
                                "<td valign='top' width='1%' align='center'>:</td>"+
                                "<td valign='top' width='79%'>"+rs2.getString("status_lanjut")+"</td>"+
                              "</tr>"
                            );                            
                            urut++;
                            //menampilkan triase gawat darurat primer
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select data_triase_igdprimer.keluhan_utama,data_triase_igdprimer.kebutuhan_khusus,data_triase_igdprimer.catatan,"+
                                        "data_triase_igdprimer.plan,data_triase_igdprimer.tanggaltriase,data_triase_igdprimer.nip,data_triase_igd.tekanan_darah,"+
                                        "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                                        "data_triase_igd.no_rawat,data_triase_igd.cara_masuk,data_triase_igd.alat_transportasi,data_triase_igd.alasan_kedatangan,"+
                                        "data_triase_igd.keterangan_kedatangan,data_triase_igd.kode_kasus,master_triase_macam_kasus.macam_kasus from data_triase_igdprimer "+
                                        "inner join data_triase_igd inner join master_triase_macam_kasus on data_triase_igd.no_rawat=data_triase_igdprimer.no_rawat "+
                                        "and data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus where data_triase_igd.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Triase Gawat Darurat</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Cara Masuk</td><td valign='top'>: "+rs3.getString("cara_masuk")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+            
                                                "<td valign='top'>Transportasi</td><td valign='top'>: "+rs3.getString("alat_transportasi")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Alasan Kedatangan</td><td valign='top'>: "+rs3.getString("alasan_kedatangan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Keterangan Kedatangan</td><td valign='top'>: "+rs3.getString("keterangan_kedatangan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Macam Kasus</td><td valign='top'>: "+rs3.getString("macam_kasus")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35%'>Keterangan</td>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65%'>Triase Primer</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Keluhan Utama</td>"+
                                                "<td valign='middle'>"+rs3.getString("keluhan_utama").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Tanda Vital</td>"+
                                                "<td valign='middle'>Suhu (C) : "+rs3.getString("suhu")+", Nyeri : "+rs3.getString("nyeri")+", Tensi : "+rs3.getString("tekanan_darah")+", Nadi(/menit) : "+rs3.getString("nadi")+", Saturasi O²(%) : "+rs3.getString("saturasi_o2")+", Respirasi(/menit) : "+rs3.getString("pernapasan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Kebutuhan Khusus</td>"+
                                                "<td valign='middle'>"+rs3.getString("kebutuhan_khusus")+"</td>"+
                                            "</tr>"
                                    );
                                    
                                    try {
                                        rs4=koneksi.prepareStatement(
                                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                            "from master_triase_pemeriksaan inner join master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                                            "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala1.kode_pemeriksaan and "+
                                            "master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where data_triase_igddetail_skala1.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                            "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                        if(rs4.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                    "<td valign='middle' bgcolor='#AA0000' color='ffffff' align='center'>Immediate/Segera</td>"+
                                                "</tr>"
                                            );
                                            rs4.beforeFirst();
                                            while(rs4.next()){
                                                htmlContent.append(                             
                                                    "<tr class='isi'>"+
                                                        "<td valign='middle'>"+rs4.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='middle' bgcolor='#AA0000' color='ffffff'>"+
                                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                                );
                                                try {
                                                    rs5=koneksi.prepareStatement(
                                                        "select master_triase_skala1.pengkajian_skala1 from master_triase_skala1 inner join data_triase_igddetail_skala1 "+
                                                        "on master_triase_skala1.kode_skala1=data_triase_igddetail_skala1.kode_skala1 where "+
                                                        "master_triase_skala1.kode_pemeriksaan='"+rs4.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala1.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                                        "order by data_triase_igddetail_skala1.kode_skala1").executeQuery();
                                                    while(rs5.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi'>"+
                                                                "<td border='0' valign='middle' bgcolor='#AA0000' color='ffffff' width='100%'>"+rs5.getString("pengkajian_skala1")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs5!=null){
                                                        rs5.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            keputusan="#AA0000";
                                        }
                                    } catch (Exception e) {
                                        if(rs4!=null){
                                            rs4.close();
                                        }
                                    }
                                    
                                    try {
                                        rs4=koneksi.prepareStatement(
                                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                            "from master_triase_pemeriksaan inner join master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                            "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala2.kode_pemeriksaan and "+
                                            "master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where data_triase_igddetail_skala2.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                            "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                        if(rs4.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                    "<td valign='middle' bgcolor='#FF0000' color='ffffff' align='center'>Emergensi</td>"+
                                                "</tr>"
                                            );
                                            rs4.beforeFirst();
                                            while(rs4.next()){
                                                htmlContent.append(                             
                                                    "<tr class='isi'>"+
                                                        "<td valign='middle'>"+rs4.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='middle' bgcolor='#FF0000' color='ffffff'>"+
                                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                                );
                                                try {
                                                    rs5=koneksi.prepareStatement(
                                                        "select master_triase_skala2.pengkajian_skala2 from master_triase_skala2 inner join data_triase_igddetail_skala2 "+
                                                        "on master_triase_skala2.kode_skala2=data_triase_igddetail_skala2.kode_skala2 where "+
                                                        "master_triase_skala2.kode_pemeriksaan='"+rs4.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala2.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                                        "order by data_triase_igddetail_skala2.kode_skala2").executeQuery();
                                                    while(rs5.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi'>"+
                                                                "<td border='0' valign='middle' bgcolor='#FF0000' color='ffffff' width='100%'>"+rs5.getString("pengkajian_skala2")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs5!=null){
                                                        rs5.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            keputusan="#FF0000";
                                        }
                                    } catch (Exception e) {
                                        if(rs4!=null){
                                            rs4.close();
                                        }
                                    }
                                    
                                    htmlContent.append(
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Plan/Keputusan</td>"+
                                                "<td valign='middle' bgcolor='"+keputusan+"' color='ffffff'>Zona Merah "+rs3.getString("plan")+"</td>"+
                                            "</tr>"+                       
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>&nbsp;</td>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>Petugas Triase Primer</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Tanggal & Jam</td>"+
                                                "<td valign='middle'>"+rs3.getString("tanggaltriase")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Catatan</td>"+
                                                "<td valign='middle'>"+rs3.getString("catatan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Nama Petugas</td>"+
                                                "<td valign='middle'>"+rs3.getString("nip")+" "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("nip"))+"</td>"+
                                            "</tr>"+
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                    
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan triase gawat darurat sekunder
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select data_triase_igdsekunder.anamnesa_singkat,data_triase_igdsekunder.catatan,"+
                                        "data_triase_igdsekunder.plan,data_triase_igdsekunder.tanggaltriase,data_triase_igdsekunder.nip,data_triase_igd.tekanan_darah,"+
                                        "data_triase_igd.nadi,data_triase_igd.pernapasan,data_triase_igd.suhu,data_triase_igd.saturasi_o2,data_triase_igd.nyeri,"+
                                        "data_triase_igd.no_rawat,data_triase_igd.cara_masuk,data_triase_igd.alat_transportasi,data_triase_igd.alasan_kedatangan,"+
                                        "data_triase_igd.keterangan_kedatangan,data_triase_igd.kode_kasus,master_triase_macam_kasus.macam_kasus from data_triase_igdsekunder "+
                                        "inner join data_triase_igd inner join master_triase_macam_kasus on data_triase_igd.no_rawat=data_triase_igdsekunder.no_rawat "+
                                        "and data_triase_igd.kode_kasus=master_triase_macam_kasus.kode_kasus where data_triase_igd.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Triase Gawat Darurat</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Cara Masuk</td><td valign='top'>: "+rs3.getString("cara_masuk")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+            
                                                "<td valign='top'>Transportasi</td><td valign='top'>: "+rs3.getString("alat_transportasi")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Alasan Kedatangan</td><td valign='top'>: "+rs3.getString("alasan_kedatangan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Keterangan Kedatangan</td><td valign='top'>: "+rs3.getString("keterangan_kedatangan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top'>Macam Kasus</td><td valign='top'>: "+rs3.getString("macam_kasus")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='35%'>Keterangan</td>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center' width='65%'>Triase Sekunder</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Anamnesa Singkat</td>"+
                                                "<td valign='middle'>"+rs3.getString("anamnesa_singkat").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Tanda Vital</td>"+
                                                "<td valign='middle'>Suhu (C) : "+rs3.getString("suhu")+", Nyeri : "+rs3.getString("nyeri")+", Tensi : "+rs3.getString("tekanan_darah")+", Nadi(/menit) : "+rs3.getString("nadi")+", Saturasi O²(%) : "+rs3.getString("saturasi_o2")+", Respirasi(/menit) : "+rs3.getString("pernapasan")+"</td>"+
                                            "</tr>"
                                    );
                                    
                                    try {
                                        rs4=koneksi.prepareStatement(
                                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                            "from master_triase_pemeriksaan inner join master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                                            "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala3.kode_pemeriksaan and "+
                                            "master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where data_triase_igddetail_skala3.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                            "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                        if(rs4.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                    "<td valign='middle' bgcolor='#C8C800' color='ffffff' align='center'>Urgensi</td>"+
                                                "</tr>"
                                            );
                                            rs4.beforeFirst();
                                            while(rs4.next()){
                                                htmlContent.append(                             
                                                    "<tr class='isi'>"+
                                                        "<td valign='middle'>"+rs4.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='middle' bgcolor='#C8C800' color='ffffff'>"+
                                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                                );
                                                try {
                                                    rs5=koneksi.prepareStatement(
                                                        "select master_triase_skala3.pengkajian_skala3 from master_triase_skala3 inner join data_triase_igddetail_skala3 "+
                                                        "on master_triase_skala3.kode_skala3=data_triase_igddetail_skala3.kode_skala3 where "+
                                                        "master_triase_skala3.kode_pemeriksaan='"+rs4.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala3.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                                        "order by data_triase_igddetail_skala3.kode_skala3").executeQuery();
                                                    while(rs5.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi'>"+
                                                                "<td border='0' valign='middle' bgcolor='#C8C800' color='ffffff' width='100%'>"+rs5.getString("pengkajian_skala3")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs5!=null){
                                                        rs5.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            keputusan="#C8C800";
                                        }
                                    } catch (Exception e) {
                                        if(rs4!=null){
                                            rs4.close();
                                        }
                                    }
                                    
                                    try {
                                        rs4=koneksi.prepareStatement(
                                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                            "from master_triase_pemeriksaan inner join master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                            "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala4.kode_pemeriksaan and "+
                                            "master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where data_triase_igddetail_skala4.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                            "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                        if(rs4.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                    "<td valign='middle' bgcolor='#00AA00' color='ffffff' align='center'>Semi Urgensi/Urgensi Rendah</td>"+
                                                "</tr>"
                                            );
                                            rs4.beforeFirst();
                                            while(rs4.next()){
                                                htmlContent.append(                             
                                                    "<tr class='isi'>"+
                                                        "<td valign='middle'>"+rs4.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='middle' bgcolor='#00AA00' color='ffffff'>"+
                                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                                );
                                                try {
                                                    rs5=koneksi.prepareStatement(
                                                        "select master_triase_skala4.pengkajian_skala4 from master_triase_skala4 inner join data_triase_igddetail_skala4 "+
                                                        "on master_triase_skala4.kode_skala4=data_triase_igddetail_skala4.kode_skala4 where "+
                                                        "master_triase_skala4.kode_pemeriksaan='"+rs4.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala4.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                                        "order by data_triase_igddetail_skala4.kode_skala4").executeQuery();
                                                    while(rs5.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi'>"+
                                                                "<td border='0' valign='middle' bgcolor='#00AA00' color='ffffff' width='100%'>"+rs5.getString("pengkajian_skala4")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs5!=null){
                                                        rs5.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            keputusan="#00AA00";
                                        }
                                    } catch (Exception e) {
                                        if(rs4!=null){
                                            rs4.close();
                                        }
                                    }
                                    
                                    try {
                                        rs4=koneksi.prepareStatement(
                                            "select master_triase_pemeriksaan.kode_pemeriksaan,master_triase_pemeriksaan.nama_pemeriksaan "+
                                            "from master_triase_pemeriksaan inner join master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                            "on master_triase_pemeriksaan.kode_pemeriksaan=master_triase_skala5.kode_pemeriksaan and "+
                                            "master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where data_triase_igddetail_skala5.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                            "group by master_triase_pemeriksaan.kode_pemeriksaan order by master_triase_pemeriksaan.kode_pemeriksaan").executeQuery();
                                        if(rs4.next()){
                                            htmlContent.append(                             
                                                "<tr class='isi'>"+
                                                    "<td valign='middle' bgcolor='#FFFAF8' align='center'>Pemeriksaan</td>"+
                                                    "<td valign='middle' bgcolor='#969696' color='ffffff' align='center'>Non Urgensi</td>"+
                                                "</tr>"
                                            );
                                            rs4.beforeFirst();
                                            while(rs4.next()){
                                                htmlContent.append(                             
                                                    "<tr class='isi'>"+
                                                        "<td valign='middle'>"+rs4.getString("nama_pemeriksaan")+"</td>"+
                                                        "<td valign='middle' bgcolor='#969696' color='ffffff'>"+
                                                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0'>"
                                                );
                                                try {
                                                    rs5=koneksi.prepareStatement(
                                                        "select master_triase_skala5.pengkajian_skala5 from master_triase_skala5 inner join data_triase_igddetail_skala5 "+
                                                        "on master_triase_skala5.kode_skala5=data_triase_igddetail_skala5.kode_skala5 where "+
                                                        "master_triase_skala5.kode_pemeriksaan='"+rs4.getString("kode_pemeriksaan")+"' and data_triase_igddetail_skala5.no_rawat='"+rs2.getString("no_rawat")+"' "+
                                                        "order by data_triase_igddetail_skala5.kode_skala5").executeQuery();
                                                    while(rs5.next()){
                                                        htmlContent.append(                             
                                                            "<tr class='isi'>"+
                                                                "<td border='0' valign='middle' bgcolor='#969696' color='ffffff' width='100%'>"+rs5.getString("pengkajian_skala5")+"</td>"+
                                                            "</tr>"
                                                        );
                                                    }
                                                } catch (Exception e) {
                                                    System.out.println("Notif : "+e);
                                                } finally{
                                                    if(rs5!=null){
                                                        rs5.close();
                                                    }
                                                }
                                                htmlContent.append(
                                                            "</table>"+
                                                        "</td>"+
                                                    "</tr>"
                                                );
                                            }
                                            keputusan="#969696";
                                        }
                                    } catch (Exception e) {
                                        if(rs4!=null){
                                            rs4.close();
                                        }
                                    }
                                    
                                    htmlContent.append(
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Plan/Keputusan</td>"+
                                                "<td valign='middle' bgcolor='"+keputusan+"' color='ffffff'>"+rs3.getString("plan")+"</td>"+
                                            "</tr>"+                       
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>&nbsp;</td>"+
                                                "<td valign='middle' bgcolor='#FFFAF8' align='center'>Petugas Triase Sekunder</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Tanggal & Jam</td>"+
                                                "<td valign='middle'>"+rs3.getString("tanggaltriase")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Catatan</td>"+
                                                "<td valign='middle'>"+rs3.getString("catatan")+"</td>"+
                                            "</tr>"+
                                            "<tr class='isi'>"+
                                                "<td valign='middle'>Nama Petugas</td>"+
                                                "<td valign='middle'>"+rs3.getString("nip")+" "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("nip"))+"</td>"+
                                            "</tr>"+
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                    
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan diagnosa penyakit                            
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select diagnosa_pasien.kd_penyakit,penyakit.nm_penyakit, diagnosa_pasien.status "+
                                        "from diagnosa_pasien inner join penyakit "+
                                        "on diagnosa_pasien.kd_penyakit=penyakit.kd_penyakit "+
                                        "where diagnosa_pasien.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Diagnosa/Penyakit/ICD 10</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'><td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td><td valign='top' width='24%' bgcolor='#FFFAF8'>Kode</td><td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Penyakit</td><td valign='top' width='23%' bgcolor='#FFFAF8'>Status</td></tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append("<tr><td valign='top' align='center'>"+w+"</td><td valign='top'>"+rs3.getString("kd_penyakit")+"</td><td valign='top'>"+rs3.getString("nm_penyakit")+"</td><td valign='top'>"+rs3.getString("status")+"</td></tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                    
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan prosedur tindakan
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select prosedur_pasien.kode,icd9.deskripsi_panjang, prosedur_pasien.status "+
                                        "from prosedur_pasien inner join icd9 "+
                                        "on prosedur_pasien.kode=icd9.kode "+
                                        "where prosedur_pasien.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prosedur Tindakan/ICD 9</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'><td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td><td valign='top' width='24%' bgcolor='#FFFAF8'>Kode</td><td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Prosedur</td><td valign='top' width='23%' bgcolor='#FFFAF8'>Status</td></tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append("<tr><td valign='top' align='center'>"+w+"</td><td valign='top'>"+rs3.getString("kode")+"</td><td valign='top'>"+rs3.getString("deskripsi_panjang")+"</td><td valign='top'>"+rs3.getString("status")+"</td></tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan catatan dokter
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select catatan_perawatan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                        "catatan_perawatan.tanggal,catatan_perawatan.jam,catatan_perawatan.kd_dokter,dokter.nm_dokter,"+
                                        "catatan_perawatan.catatan from pasien inner join reg_periksa inner join catatan_perawatan inner join dokter "+
                                        "on catatan_perawatan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                                        "and catatan_perawatan.kd_dokter=dokter.kd_dokter "+
                                        "where catatan_perawatan.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Catatan Dokter</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode Dokter</td>"+
                                                "<td valign='top' width='20%' bgcolor='#FFFAF8'>Nama Dokter</td>"+
                                                "<td valign='top' width='50%' bgcolor='#FFFAF8'>Catatan</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tanggal")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("catatan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                             "</tr>");                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan ralan
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat,pemeriksaan_ralan.suhu_tubuh,pemeriksaan_ralan.tensi,pemeriksaan_ralan.nadi,pemeriksaan_ralan.respirasi,"+
                                        "pemeriksaan_ralan.tinggi,pemeriksaan_ralan.berat,pemeriksaan_ralan.gcs,pemeriksaan_ralan.keluhan, "+
                                        "pemeriksaan_ralan.pemeriksaan,pemeriksaan_ralan.alergi,pemeriksaan_ralan.imun_ke,pemeriksaan_ralan.rtl,"+
                                        "pemeriksaan_ralan.penilaian from pemeriksaan_ralan where "+
                                        "pemeriksaan_ralan.no_rawat='"+rs2.getString("no_rawat")+"' order by pemeriksaan_ralan.tgl_perawatan,pemeriksaan_ralan.jam_rawat").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Rawat Jalan</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Suhu(C)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Tensi</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Nadi(/menit)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Respirasi(/menit)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Tinggi(Cm)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Berat(Kg)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>GCS(E,V,M)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Imunisasi Ke</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("suhu_tubuh")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tensi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nadi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("respirasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tinggi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("berat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("gcs")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("imun_ke")+"</td>"+
                                             "</tr>"); 
                                        if(!rs3.getString("keluhan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Keluhan</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("keluhan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("pemeriksaan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Pemeriksaan</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("pemeriksaan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("penilaian").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Penilaian</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("penilaian").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("rtl").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Tindak Lanjut</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("rtl").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("alergi").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Alergi</td>"+
                                                    "<td valign='top' colspan='6'> : "+rs3.getString("alergi")+"</td>"+
                                                 "</tr>");
                                        }
                                            
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan obstetri ralan
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat,pemeriksaan_obstetri_ralan.tinggi_uteri,pemeriksaan_obstetri_ralan.janin,pemeriksaan_obstetri_ralan.letak, " +
                                        "pemeriksaan_obstetri_ralan.panggul,pemeriksaan_obstetri_ralan.denyut,pemeriksaan_obstetri_ralan.kontraksi, " +
                                        "pemeriksaan_obstetri_ralan.kualitas_mnt,pemeriksaan_obstetri_ralan.kualitas_dtk,pemeriksaan_obstetri_ralan.fluksus,pemeriksaan_obstetri_ralan.albus, " +
                                        "pemeriksaan_obstetri_ralan.vulva,pemeriksaan_obstetri_ralan.portio,pemeriksaan_obstetri_ralan.dalam, pemeriksaan_obstetri_ralan.tebal, pemeriksaan_obstetri_ralan.arah, pemeriksaan_obstetri_ralan.pembukaan," +
                                        "pemeriksaan_obstetri_ralan.penurunan, pemeriksaan_obstetri_ralan.denominator, pemeriksaan_obstetri_ralan.ketuban, pemeriksaan_obstetri_ralan.feto " +
                                        "from pasien inner join reg_periksa inner join pemeriksaan_obstetri_ralan "+
                                        "on pemeriksaan_obstetri_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                                        "pemeriksaan_obstetri_ralan.no_rawat='"+rs2.getString("no_rawat")+"' order by pemeriksaan_obstetri_ralan.tgl_perawatan,pemeriksaan_obstetri_ralan.jam_rawat").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Obstetri Rawat Jalan</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Tinggi Fundus</td>"+
                                                "<td valign='top' width='6%' bgcolor='#FFFAF8'>Janin</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Letak</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Panggul</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Denyut</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kontraksi</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kualitas Mnt</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kualitas Detik</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Fluksus</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Albus</td>"+
                                                "<td valign='top' width='6%' bgcolor='#FFFAF8'>Dalam</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Tebal</td>"+
                                                "<td valign='top' width='6%' bgcolor='#FFFAF8'>Arah</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Ketuban</td>"+
                                                "<td valign='top' width='7%' bgcolor='#FFFAF8'>Feto</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tinggi_uteri")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("janin")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("letak")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("panggul")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("denyut")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kontraksi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kualitas_mnt")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kualitas_dtk")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("fluksus")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("albus")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("dalam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tebal")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("arah")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("ketuban")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("feto")+"</td>"+
                                             "</tr>"); 
                                        if(!rs3.getString("vulva").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Vulva</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("vulva")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("portio").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Portio</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("portio")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("pembukaan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Pembukaan</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("pembukaan")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("penurunan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Penurunan</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("penurunan")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("denominator").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Denominator</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("denominator")+"</td>"+
                                                 "</tr>");
                                        }
                                            
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan genekologi ralan
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_ginekologi_ralan.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                        "pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat,pemeriksaan_ginekologi_ralan.inspeksi,pemeriksaan_ginekologi_ralan.inspeksi_vulva,pemeriksaan_ginekologi_ralan.inspekulo_gine, " +
                                        "pemeriksaan_ginekologi_ralan.fluxus_gine,pemeriksaan_ginekologi_ralan.fluor_gine,pemeriksaan_ginekologi_ralan.vulva_inspekulo, " +
                                        "pemeriksaan_ginekologi_ralan.portio_inspekulo,pemeriksaan_ginekologi_ralan.sondage,pemeriksaan_ginekologi_ralan.portio_dalam,pemeriksaan_ginekologi_ralan.bentuk, " +
                                        "pemeriksaan_ginekologi_ralan.cavum_uteri,pemeriksaan_ginekologi_ralan.mobilitas,pemeriksaan_ginekologi_ralan.ukuran, pemeriksaan_ginekologi_ralan.nyeri_tekan, pemeriksaan_ginekologi_ralan.adnexa_kanan, pemeriksaan_ginekologi_ralan.adnexa_kiri," +
                                        "pemeriksaan_ginekologi_ralan.cavum_douglas " +
                                        "from pasien inner join reg_periksa inner join pemeriksaan_ginekologi_ralan "+
                                        "on pemeriksaan_ginekologi_ralan.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                                        "pemeriksaan_ginekologi_ralan.no_rawat='"+rs2.getString("no_rawat")+"' order by pemeriksaan_ginekologi_ralan.tgl_perawatan,pemeriksaan_ginekologi_ralan.jam_rawat").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Ginekologi Rawat Jalan</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='80%' bgcolor='#FFFAF8'>Pemeriksaan</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+
                                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>Inspeksi</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("inspeksi")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Vulva/Uretra/Vagina</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("inspeksi_vulva")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>Inspekulo</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("inspekulo_gine")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Fluxus</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("fluxus_gine")+",&nbsp;&nbsp;Fluor Albus : "+rs3.getString("fluor_gine")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Vulva/Vagina</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("vulva_inspekulo")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Portio</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("portio_inspekulo")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Sondage</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("sondage")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>Pemeriksaan Dalam</td>"+
                                                           "<td border='0' valign='top' width='70%'>:</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Portio</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("portio_dalam")+",&nbsp;&nbsp;Bentuk : "+rs3.getString("bentuk")+"</td>"+
                                                        "</tr>"+   
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Cavum Uteri</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("cavum_uteri")+",&nbsp;&nbsp;Mobilitas : "+rs3.getString("mobilitas")+"</td>"+
                                                        "</tr>"+ 
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;</td>"+
                                                           "<td border='0' valign='top' width='70%'>&nbsp;&nbsp;&nbsp;Ukuran : "+rs3.getString("ukuran")+",&nbsp;&nbsp;Nyeri Tekan : "+rs3.getString("nyeri_tekan")+"</td>"+
                                                        "</tr>"+ 
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Adnexa/Parametrium</td>"+
                                                           "<td border='0' valign='top' width='70%'>: Kanan : "+rs3.getString("adnexa_kanan")+",&nbsp;&nbsp;Kiri : "+rs3.getString("adnexa_kiri")+"</td>"+
                                                        "</tr>"+ 
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Cavum Douglas</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("cavum_douglas")+"</td>"+
                                                        "</tr>"+ 
                                                    "</table>"+
                                                "</td>"+
                                             "</tr>");                                                                                     
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan ranap
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_ranap.suhu_tubuh,pemeriksaan_ranap.tensi,pemeriksaan_ranap.nadi,pemeriksaan_ranap.respirasi," +
                                        "pemeriksaan_ranap.tinggi,pemeriksaan_ranap.berat,pemeriksaan_ranap.gcs,pemeriksaan_ranap.keluhan,pemeriksaan_ranap.penilaian,pemeriksaan_ranap.rtl," +
                                        "pemeriksaan_ranap.pemeriksaan,pemeriksaan_ranap.alergi,pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat "+
                                        "from pemeriksaan_ranap where pemeriksaan_ranap.no_rawat='"+rs2.getString("no_rawat")+"' order by pemeriksaan_ranap.tgl_perawatan,pemeriksaan_ranap.jam_rawat").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Rawat Inap</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Suhu(C)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Tensi</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Nadi(/menit)</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Respirasi(/menit)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Tinggi(Cm)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>Berat(Kg)</td>"+
                                                "<td valign='top' width='10%' bgcolor='#FFFAF8'>GCS(E,V,M)</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("suhu_tubuh")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tensi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nadi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("respirasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tinggi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("berat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("gcs")+"</td>"+
                                             "</tr>");   
                                        
                                        if(!rs3.getString("keluhan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Keluhan</td>"+
                                                    "<td valign='top' colspan='5'> : "+rs3.getString("keluhan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("pemeriksaan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Pemeriksaan</td>"+
                                                    "<td valign='top' colspan='5'> : "+rs3.getString("pemeriksaan").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("penilaian").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Penilaian</td>"+
                                                    "<td valign='top' colspan='5'> : "+rs3.getString("penilaian").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("rtl").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Tindak Lanjut</td>"+
                                                    "<td valign='top' colspan='5'> : "+rs3.getString("rtl").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("alergi").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Alergi</td>"+
                                                    "<td valign='top' colspan='5'> : "+rs3.getString("alergi")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan obstetri ranap
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_obstetri_ranap.tgl_perawatan,pemeriksaan_obstetri_ranap.jam_rawat,pemeriksaan_obstetri_ranap.tinggi_uteri,pemeriksaan_obstetri_ranap.janin,pemeriksaan_obstetri_ranap.letak, " +
                                        "pemeriksaan_obstetri_ranap.panggul,pemeriksaan_obstetri_ranap.denyut,pemeriksaan_obstetri_ranap.kontraksi, " +
                                        "pemeriksaan_obstetri_ranap.kualitas_mnt,pemeriksaan_obstetri_ranap.kualitas_dtk,pemeriksaan_obstetri_ranap.fluksus,pemeriksaan_obstetri_ranap.albus, " +
                                        "pemeriksaan_obstetri_ranap.vulva,pemeriksaan_obstetri_ranap.portio,pemeriksaan_obstetri_ranap.dalam, pemeriksaan_obstetri_ranap.tebal, pemeriksaan_obstetri_ranap.arah, pemeriksaan_obstetri_ranap.pembukaan," +
                                        "pemeriksaan_obstetri_ranap.penurunan, pemeriksaan_obstetri_ranap.denominator, pemeriksaan_obstetri_ranap.ketuban, pemeriksaan_obstetri_ranap.feto " +
                                        "from pasien inner join reg_periksa inner join pemeriksaan_obstetri_ranap "+
                                        "on pemeriksaan_obstetri_ranap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                                        "pemeriksaan_obstetri_ranap.no_rawat='"+rs2.getString("no_rawat")+"' order by pemeriksaan_obstetri_ranap.tgl_perawatan,pemeriksaan_obstetri_ranap.jam_rawat").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Obstetri Rawat Inap</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Tinggi Fundus</td>"+
                                                "<td valign='top' width='6%' bgcolor='#FFFAF8'>Janin</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Letak</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Panggul</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Denyut</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kontraksi</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kualitas Mnt</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Kualitas Detik</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Fluksus</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Albus</td>"+
                                                "<td valign='top' width='6%' bgcolor='#FFFAF8'>Dalam</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Tebal</td>"+
                                                "<td valign='top' width='6%' bgcolor='#FFFAF8'>Arah</td>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>Ketuban</td>"+
                                                "<td valign='top' width='7%' bgcolor='#FFFAF8'>Feto</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tinggi_uteri")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("janin")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("letak")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("panggul")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("denyut")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kontraksi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kualitas_mnt")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kualitas_dtk")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("fluksus")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("albus")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("dalam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tebal")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("arah")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("ketuban")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("feto")+"</td>"+
                                             "</tr>"); 
                                        if(!rs3.getString("vulva").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Vulva</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("vulva")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("portio").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Portio</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("portio")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("pembukaan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Pembukaan</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("pembukaan")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("penurunan").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Penurunan</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("penurunan")+"</td>"+
                                                 "</tr>");
                                        }
                                        
                                        if(!rs3.getString("denominator").equals("")){
                                            htmlContent.append(
                                                 "<tr>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' align='center'></td>"+
                                                    "<td valign='top' colspan='2'>Denominator</td>"+
                                                    "<td valign='top' colspan='13'> : "+rs3.getString("denominator")+"</td>"+
                                                 "</tr>");
                                        }
                                            
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //menampilkan riwayat pemeriksaan genekologi ranap
                            try {
                                rs3=koneksi.prepareStatement(
                                        "select pemeriksaan_ginekologi_ranap.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,"+
                                        "pemeriksaan_ginekologi_ranap.tgl_perawatan,pemeriksaan_ginekologi_ranap.jam_rawat,pemeriksaan_ginekologi_ranap.inspeksi,pemeriksaan_ginekologi_ranap.inspeksi_vulva,pemeriksaan_ginekologi_ranap.inspekulo_gine, " +
                                        "pemeriksaan_ginekologi_ranap.fluxus_gine,pemeriksaan_ginekologi_ranap.fluor_gine,pemeriksaan_ginekologi_ranap.vulva_inspekulo, " +
                                        "pemeriksaan_ginekologi_ranap.portio_inspekulo,pemeriksaan_ginekologi_ranap.sondage,pemeriksaan_ginekologi_ranap.portio_dalam,pemeriksaan_ginekologi_ranap.bentuk, " +
                                        "pemeriksaan_ginekologi_ranap.cavum_uteri,pemeriksaan_ginekologi_ranap.mobilitas,pemeriksaan_ginekologi_ranap.ukuran, pemeriksaan_ginekologi_ranap.nyeri_tekan, pemeriksaan_ginekologi_ranap.adnexa_kanan, pemeriksaan_ginekologi_ranap.adnexa_kiri," +
                                        "pemeriksaan_ginekologi_ranap.cavum_douglas " +
                                        "from pasien inner join reg_periksa inner join pemeriksaan_ginekologi_ranap "+
                                        "on pemeriksaan_ginekologi_ranap.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where "+
                                        "pemeriksaan_ginekologi_ranap.no_rawat='"+rs2.getString("no_rawat")+"' order by pemeriksaan_ginekologi_ranap.tgl_perawatan,pemeriksaan_ginekologi_ranap.jam_rawat").executeQuery();
                                if(rs3.next()){
                                    htmlContent.append(
                                      "<tr class='isi'>"+ 
                                        "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Pemeriksaan Ginekologi Rawat Inap</td>"+
                                        "<td valign='top' width='1%' align='center'>:</td>"+
                                        "<td valign='top' width='79%'>"+
                                          "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                             "<tr align='center'>"+
                                                "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                                "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                                "<td valign='top' width='80%' bgcolor='#FFFAF8'>Pemeriksaan</td>"+
                                             "</tr>"
                                    );
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+
                                                    "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>Inspeksi</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("inspeksi")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Vulva/Uretra/Vagina</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("inspeksi_vulva")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>Inspekulo</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("inspekulo_gine")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Fluxus</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("fluxus_gine")+",&nbsp;&nbsp;Fluor Albus : "+rs3.getString("fluor_gine")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Vulva/Vagina</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("vulva_inspekulo")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Portio</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("portio_inspekulo")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Sondage</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("sondage")+"</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>Pemeriksaan Dalam</td>"+
                                                           "<td border='0' valign='top' width='70%'>:</td>"+
                                                        "</tr>"+
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Portio</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("portio_dalam")+",&nbsp;&nbsp;Bentuk : "+rs3.getString("bentuk")+"</td>"+
                                                        "</tr>"+   
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Cavum Uteri</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("cavum_uteri")+",&nbsp;&nbsp;Mobilitas : "+rs3.getString("mobilitas")+"</td>"+
                                                        "</tr>"+ 
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;</td>"+
                                                           "<td border='0' valign='top' width='70%'>&nbsp;&nbsp;&nbsp;Ukuran : "+rs3.getString("ukuran")+",&nbsp;&nbsp;Nyeri Tekan : "+rs3.getString("nyeri_tekan")+"</td>"+
                                                        "</tr>"+ 
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Adnexa/Parametrium</td>"+
                                                           "<td border='0' valign='top' width='70%'>: Kanan : "+rs3.getString("adnexa_kanan")+",&nbsp;&nbsp;Kiri : "+rs3.getString("adnexa_kiri")+"</td>"+
                                                        "</tr>"+ 
                                                        "<tr align='left'>"+
                                                           "<td border='0' valign='top' width='30%'>&nbsp;&nbsp;&nbsp;Cavum Douglas</td>"+
                                                           "<td border='0' valign='top' width='70%'>: "+rs3.getString("cavum_douglas")+"</td>"+
                                                        "</tr>"+ 
                                                    "</table>"+
                                                "</td>"+
                                             "</tr>");                                                                                     
                                        w++;
                                    }
                                    htmlContent.append(
                                          "</table>"+
                                        "</td>"+
                                      "</tr>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //biaya administrasi
                            htmlContent.append(
                               "<tr class='isi'>"+ 
                                 "<td valign='top' width='20%'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Biaya & Perawatan</td>"+
                                 "<td valign='top' width='1%' align='center'>:</td>"+
                                 "<td valign='top' width='79%'>"+
                                     "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                       "<tr>"+
                                         "<td valign='top' width='89%'>Administrasi</td>"+
                                         "<td valign='top' width='1%' align='right'>:</td>"+
                                         "<td valign='top' width='10%' align='right'>"+Valid.SetAngka(rs2.getDouble("biaya_reg"))+"</td>"+
                                       "</tr>"+
                                     "</table>"
                            );
                            
                            //tindakan dokter ralan
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_dr.kd_jenis_prw,jns_perawatan.nm_perawatan,dokter.nm_dokter,rawat_jl_dr.biaya_rawat, "+
                                        "rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat from rawat_jl_dr inner join jns_perawatan inner join dokter "+
                                        "on rawat_jl_dr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                        "and rawat_jl_dr.kd_dokter=dokter.kd_dokter where rawat_jl_dr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_jl_dr.tgl_perawatan,rawat_jl_dr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Jalan Dokter</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Dokter</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+" </td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis ralan
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_pr.kd_jenis_prw,jns_perawatan.nm_perawatan,petugas.nama,rawat_jl_pr.biaya_rawat, "+
                                        "rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat from rawat_jl_pr inner join jns_perawatan inner join petugas "+
                                        "on rawat_jl_pr.kd_jenis_prw=jns_perawatan.kd_jenis_prw "+
                                        "and rawat_jl_pr.nip=petugas.nip where rawat_jl_pr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_jl_pr.tgl_perawatan,rawat_jl_pr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+                                        
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Jalan Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+      
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan ralan dokter dan paramedis
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_jl_drpr.kd_jenis_prw,jns_perawatan.nm_perawatan,dokter.nm_dokter,petugas.nama,rawat_jl_drpr.biaya_rawat, "+
                                        "rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat from rawat_jl_drpr inner join jns_perawatan inner join dokter inner join petugas "+
                                        "on rawat_jl_drpr.kd_jenis_prw=jns_perawatan.kd_jenis_prw and rawat_jl_drpr.nip=petugas.nip "+
                                        "and rawat_jl_drpr.kd_dokter=dokter.kd_dokter where rawat_jl_drpr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_jl_drpr.tgl_perawatan,rawat_jl_drpr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Tindakan Rawat Jalan Dokter & Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Dokter</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan dokter ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat,"+
                                        "rawat_inap_dr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                        "dokter.nm_dokter,rawat_inap_dr.biaya_rawat "+
                                        "from rawat_inap_dr inner join jns_perawatan_inap inner join dokter "+
                                        "on rawat_inap_dr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                        "and rawat_inap_dr.kd_dokter=dokter.kd_dokter where rawat_inap_dr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_inap_dr.tgl_perawatan,rawat_inap_dr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Inap Dokter</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Dokter</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat,"+
                                        "rawat_inap_pr.kd_jenis_prw,jns_perawatan_inap.nm_perawatan,"+
                                        "petugas.nama,rawat_inap_pr.biaya_rawat "+
                                        "from rawat_inap_pr inner join jns_perawatan_inap inner join petugas "+
                                        "on rawat_inap_pr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw "+
                                        "and rawat_inap_pr.nip=petugas.nip where rawat_inap_pr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_inap_pr.tgl_perawatan,rawat_inap_pr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Tindakan Rawat Inap Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='40%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='20%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }      
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan paramedis dan dokter ranap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat,rawat_inap_drpr.kd_jenis_prw,"+
                                        "jns_perawatan_inap.nm_perawatan,dokter.nm_dokter,petugas.nama,rawat_inap_drpr.biaya_rawat "+
                                        "from rawat_inap_drpr inner join jns_perawatan_inap inner join dokter inner join petugas "+
                                        "on rawat_inap_drpr.kd_jenis_prw=jns_perawatan_inap.kd_jenis_prw and rawat_inap_drpr.nip=petugas.nip "+
                                        "and rawat_inap_drpr.kd_dokter=dokter.kd_dokter where rawat_inap_drpr.no_rawat='"+rs2.getString("no_rawat")+"' order by rawat_inap_drpr.tgl_perawatan,rawat_inap_drpr.jam_rawat").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Tindakan Rawat Inap Dokter & Paramedis</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Tindakan/Perawatan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Dokter</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Paramedis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam_rawat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya_rawat"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //kamar inap
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select kamar_inap.kd_kamar,bangsal.nm_bangsal,kamar_inap.tgl_masuk, kamar_inap.tgl_keluar, "+
                                        "kamar_inap.stts_pulang,kamar_inap.lama,kamar_inap.jam_masuk,kamar_inap.jam_keluar,"+
                                        "kamar_inap.ttl_biaya from kamar_inap inner join bangsal inner join kamar "+
                                        "on kamar_inap.kd_kamar=kamar.kd_kamar and kamar.kd_bangsal=bangsal.kd_bangsal  "+
                                        "where kamar_inap.no_rawat='"+rs2.getString("no_rawat")+"' order by kamar_inap.tgl_masuk,kamar_inap.jam_masuk").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Penggunaan Kamar</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal Masuk</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggak Keluar</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Lama Inap</td>"+
                                          "<td valign='top' width='35%' bgcolor='#FFFAF8'>Kamar</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Status</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_masuk")+" "+rs3.getString("jam_masuk")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_keluar")+" "+rs3.getString("jam_keluar")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("lama")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_kamar")+", "+rs3.getString("nm_bangsal")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("stts_pulang")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("ttl_biaya"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //operasi
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select operasi.tgl_operasi,operasi.jenis_anasthesi,operasi.operator1, operasi.operator2, operasi.operator3, operasi.asisten_operator1,"+
                                        "operasi.asisten_operator2,operasi.asisten_operator3,operasi.biayaasisten_operator3, operasi.instrumen, operasi.dokter_anak, operasi.perawaat_resusitas, "+
                                        "operasi.dokter_anestesi, operasi.asisten_anestesi, operasi.asisten_anestesi2,operasi.asisten_anestesi2, operasi.bidan, operasi.bidan2, operasi.bidan3, operasi.perawat_luar, operasi.omloop,"+
                                        "operasi.omloop2,operasi.omloop3,operasi.omloop4,operasi.omloop5,operasi.dokter_pjanak,operasi.dokter_umum, "+
                                        "operasi.kode_paket,paket_operasi.nm_perawatan, operasi.biayaoperator1, operasi.biayaoperator2, operasi.biayaoperator3, "+
                                        "operasi.biayaasisten_operator1, operasi.biayaasisten_operator2, operasi.biayaasisten_operator3, operasi.biayainstrumen, "+
                                        "operasi.biayadokter_anak, operasi.biayaperawaat_resusitas, operasi.biayadokter_anestesi, "+
                                        "operasi.biayaasisten_anestesi,operasi.biayaasisten_anestesi2, operasi.biayabidan,operasi.biayabidan2,operasi.biayabidan3, operasi.biayaperawat_luar, operasi.biayaalat,"+
                                        "operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biaya_omloop,operasi.biaya_omloop2,operasi.biaya_omloop3,operasi.biaya_omloop4,operasi.biaya_omloop5,"+
                                        "operasi.biayasarpras,operasi.biaya_dokter_pjanak,operasi.biaya_dokter_umum,"+
                                        "(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+"+
                                        "operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+"+
                                        "operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+"+
                                        "operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+operasi.biayaperawat_luar+operasi.biayaalat+"+
                                        "operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+"+
                                        "operasi.biayasarpras+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum) as total from operasi inner join paket_operasi "+
                                        "on operasi.kode_paket=paket_operasi.kode_paket where operasi.no_rawat='"+rs2.getString("no_rawat")+"' order by operasi.tgl_operasi").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Operasi/VK</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Tindakan</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Anastesi</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_operasi")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_paket")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+" (");
                                        if(rs3.getDouble("biayaoperator1")>0){
                                            htmlContent.append("Operator 1 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator1"))+", ");
                                        }
                                        if(rs3.getDouble("biayaoperator2")>0){
                                            htmlContent.append("Operator 2 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator2"))+", ");
                                        }
                                        if(rs3.getDouble("biayaoperator3")>0){
                                            htmlContent.append("Operator 3 : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("operator3"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator1")>0){
                                            htmlContent.append("Asisten Operator 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator1"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator2")>0){
                                            htmlContent.append("Asisten Operator 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator2"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_operator3")>0){
                                            htmlContent.append("Asisten Operator 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_operator3"))+", ");
                                        }
                                        if(rs3.getDouble("biayainstrumen")>0){
                                            htmlContent.append("Instrumen : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("instrumen"))+", ");
                                        }
                                        if(rs3.getDouble("biayadokter_anak")>0){
                                            htmlContent.append("Dokter Anak : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_anak"))+", ");
                                        }
                                        if(rs3.getDouble("biayaperawaat_resusitas")>0){
                                            htmlContent.append("Perawat Resusitas : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("perawaat_resusitas"))+", ");
                                        }
                                        if(rs3.getDouble("biayadokter_anestesi")>0){
                                            htmlContent.append("Dokter Anestesi : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_anestesi"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_anestesi")>0){
                                            htmlContent.append("Asisten Anestesi : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_anestesi"))+", ");
                                        }
                                        if(rs3.getDouble("biayaasisten_anestesi2")>0){
                                            htmlContent.append("Asisten Anestesi 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("asisten_anestesi2"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan")>0){
                                            htmlContent.append("Bidan 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan2")>0){
                                            htmlContent.append("Bidan 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan2"))+", ");
                                        }
                                        if(rs3.getDouble("biayabidan3")>0){
                                            htmlContent.append("Bidan 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("bidan3"))+", ");
                                        }
                                        if(rs3.getDouble("biayaperawat_luar")>0){
                                            htmlContent.append("Perawat Luar : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("perawat_luar"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop")>0){
                                            htmlContent.append("Onloop 1 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop2")>0){
                                            htmlContent.append("Onloop 2 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop2"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop3")>0){
                                            htmlContent.append("Onloop 3 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop3"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop4")>0){
                                            htmlContent.append("Onloop 4 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop4"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_omloop5")>0){
                                            htmlContent.append("Onloop 5 : "+Sequel.cariIsi("select nama from petugas where nip=?",rs3.getString("omloop5"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_dokter_pjanak")>0){
                                            htmlContent.append("Dokter Pj Anak : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_pjanak"))+", ");
                                        }
                                        if(rs3.getDouble("biaya_dokter_umum")>0){
                                            htmlContent.append("Dokter Umum : "+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("dokter_umum"))+", ");
                                        }
                                        htmlContent.append(
                                                ")</td>"+
                                                "<td valign='top'>"+rs3.getString("jenis_anasthesi")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //laporan operasi
                            try{
                                rs3=koneksi.prepareStatement(
                                        "select tanggal, diagnosa_preop, diagnosa_postop, jaringan_dieksekusi, selesaioperasi, permintaan_pa, laporan_operasi "+
                                        "from laporan_operasi where no_rawat='"+rs2.getString("no_rawat")+"' group by no_rawat,tanggal order by tanggal").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Laporan Operasi :</td></tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'>"+w+"</td>"+
                                                "<td valign='top' width='20%'>Mulai Operasi</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("tanggal")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Diagnosa Pre-operatif</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("diagnosa_preop")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Jaringan Yang di-Eksisi/-Insisi</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("jaringan_dieksekusi")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Diagnosa Post-operatif</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("diagnosa_postop")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Selesai Operasi</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("selesaioperasi")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Dikirim Untuk Pemeriksaan PA</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("permintaan_pa")+"</td>"+
                                             "</tr>"+
                                             "<tr>"+
                                                "<td valign='top' width='5%' align='center'></td>"+
                                                "<td valign='top' width='20%'>Laporan</td>"+
                                                "<td valign='top' width='75%'>:&nbsp;"+rs3.getString("laporan_operasi").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select periksa_radiologi.tgl_periksa,periksa_radiologi.jam,periksa_radiologi.kd_jenis_prw, "+
                                     "jns_perawatan_radiologi.nm_perawatan,petugas.nama,periksa_radiologi.biaya,periksa_radiologi.dokter_perujuk,dokter.nm_dokter "+
                                     "from periksa_radiologi inner join jns_perawatan_radiologi inner join petugas inner join dokter "+
                                     "on periksa_radiologi.kd_jenis_prw=jns_perawatan_radiologi.kd_jenis_prw and periksa_radiologi.kd_dokter=dokter.kd_dokter "+
                                     "and periksa_radiologi.nip=petugas.nip  where periksa_radiologi.no_rawat='"+rs2.getString("no_rawat")+"' order by periksa_radiologi.tgl_periksa,periksa_radiologi.jam").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemeriksaan Radiologi</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Pemeriksaan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Dokter PJ</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Petugas</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //hasil pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select tgl_periksa,jam, hasil from hasil_radiologi where no_rawat='"+rs2.getString("no_rawat")+"' order by tgl_periksa,jam").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Bacaan/Hasil Radiologi</td></tr>"+  
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='80%' bgcolor='#FFFAF8'>Hasil Pemeriksaan</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("hasil").replaceAll("(\r\n|\r|\n|\n\r)","<br>")+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //gambar pemeriksaan radiologi
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select tgl_periksa,jam, lokasi_gambar from gambar_radiologi where no_rawat='"+rs2.getString("no_rawat")+"' order by tgl_periksa,jam").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Gambar Radiologi</td></tr>"+  
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='80%' bgcolor='#FFFAF8'>Gambar Radiologi</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/radiologi/"+rs3.getString("lokasi_gambar")+"'>"+rs3.getString("lokasi_gambar").replaceAll("pages/upload/","")+"</a></td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //tindakan pemeriksaan laborat
                            try{
                                rs3=koneksi.prepareStatement(
                                     "select periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.kd_jenis_prw, "+
                                     "jns_perawatan_lab.nm_perawatan,petugas.nama,periksa_lab.biaya,periksa_lab.dokter_perujuk,dokter.nm_dokter "+
                                     "from periksa_lab inner join jns_perawatan_lab inner join petugas inner join dokter "+
                                     "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw and periksa_lab.kd_dokter=dokter.kd_dokter "+
                                     "and periksa_lab.nip=petugas.nip  where periksa_lab.no_rawat='"+rs2.getString("no_rawat")+"' order by periksa_lab.tgl_periksa,periksa_lab.jam").executeQuery();
                                if(rs3.next()){
                                    tanggal=rs3.getString("tgl_periksa");
                                    jam=rs3.getString("jam");
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemeriksaan Laboratorium</td><td valign='top' colspan='1' align='right'>:</td><td valign='top'></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='26%' bgcolor='#FFFAF8'>Nama Pemeriksaan</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Dokter PJ</td>"+
                                          "<td valign='top' width='17%' bgcolor='#FFFAF8'>Petugas</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_periksa")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_jenis_prw")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("biaya"))+"</td>"+
                                             "</tr>"
                                        ); 
                                        try {
                                            rs4=koneksi.prepareStatement(
                                                "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,"+
                                                "template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                                "detail_periksa_lab.keterangan from detail_periksa_lab inner join "+
                                                "template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                                "where detail_periksa_lab.no_rawat='"+rs2.getString("no_rawat")+"' and "+
                                                "detail_periksa_lab.kd_jenis_prw='"+rs3.getString("kd_jenis_prw")+"' and "+
                                                "detail_periksa_lab.tgl_periksa='"+rs3.getString("tgl_periksa")+"' and "+
                                                "detail_periksa_lab.jam='"+rs3.getString("jam")+"' order by detail_periksa_lab.kd_jenis_prw,template_laboratorium.urut ").executeQuery();
                                            if(rs4.next()){ 
                                                htmlContent.append(
                                                    "<tr>"+
                                                       "<td valign='top' align='center'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top' align='center' bgcolor='#FFFAF8'>Detail Pemeriksaan</td>"+
                                                       "<td valign='top' align='center' bgcolor='#FFFAF8'>Hasil</td>"+
                                                       "<td valign='top' align='center' bgcolor='#FFFAF8'>Nilai Rujukan</td>"+
                                                       "<td valign='top' align='right'></td>"+
                                                    "</tr>");
                                                rs4.beforeFirst();
                                                while(rs4.next()){
                                                    htmlContent.append(
                                                        "<tr>"+
                                                           "<td valign='top' align='center'></td>"+
                                                           "<td valign='top'></td>"+
                                                           "<td valign='top'></td>"+
                                                           "<td valign='top'>"+rs4.getString("Pemeriksaan")+"</td>"+
                                                           "<td valign='top'>"+rs4.getString("nilai")+" "+rs4.getString("satuan")+"</td>"+
                                                           "<td valign='top'>"+rs4.getString("nilai_rujukan")+"</td>"+
                                                           "<td valign='top' align='right'>"+Valid.SetAngka(rs4.getDouble("biaya_item"))+"</td>"+
                                                        "</tr>"); 
                                                }                                               
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Notifikasi : "+e);
                                        } finally{
                                            if(rs4!=null){
                                                rs4.close();
                                            }
                                        }
                                        w++;
                                    }
                                    
                                    try {
                                        rs4=koneksi.prepareStatement("select saran,kesan from saran_kesan_lab where no_rawat='"+rs2.getString("no_rawat")+"' and tgl_periksa='"+tanggal+"' and jam='"+jam+"'").executeQuery();
                                        if(rs4.next()){      
                                            htmlContent.append(
                                                    "<tr>"+
                                                       "<td valign='top' align='center'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top'>Kesan</td>"+
                                                       "<td valign='top' colspan='4'>: "+rs4.getString("kesan")+"</td>"+
                                                    "</tr>"+
                                                    "<tr>"+
                                                       "<td valign='top' align='center'></td>"+
                                                       "<td valign='top'></td>"+
                                                       "<td valign='top'>Saran</td>"+
                                                       "<td valign='top' colspan='4'>: "+rs4.getString("saran")+"</td>"+
                                                    "</tr>");
                                        } 
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs4!=null){
                                            rs4.close();
                                        }
                                    }
                                    
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi Lab : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //pemberian obat
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam,databarang.kode_sat, "+
                                    "detail_pemberian_obat.kode_brng,detail_pemberian_obat.jml,detail_pemberian_obat.total,"+
                                    "databarang.nama_brng from detail_pemberian_obat inner join databarang "+
                                    "on detail_pemberian_obat.kode_brng=databarang.kode_brng  "+
                                    "where detail_pemberian_obat.no_rawat='"+rs2.getString("no_rawat")+"' order by detail_pemberian_obat.tgl_perawatan,detail_pemberian_obat.jam").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='5'>Pemberian Obat/BHP/Alkes</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='35%' bgcolor='#FFFAF8'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Aturan Pakai</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tgl_perawatan")+" "+rs3.getString("jam")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jml")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top'>"+Sequel.cariIsi("select aturan from aturan_pakai where tgl_perawatan='"+rs3.getString("tgl_perawatan")+"' and jam='"+rs3.getString("jam")+"' and no_rawat='"+rs2.getString("no_rawat")+"' and kode_brng='"+rs3.getString("kode_brng")+"'")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //pemberian obat Operasi
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select beri_obat_operasi.tanggal,beri_obat_operasi.kd_obat,beri_obat_operasi.hargasatuan,obatbhp_ok.kode_sat, "+
                                    "beri_obat_operasi.jumlah, obatbhp_ok.nm_obat,(beri_obat_operasi.hargasatuan*beri_obat_operasi.jumlah) as total "+
                                    "from beri_obat_operasi inner join obatbhp_ok  on  beri_obat_operasi.kd_obat=obatbhp_ok.kd_obat  "+
                                    "where beri_obat_operasi.no_rawat='"+rs2.getString("no_rawat")+"' order by beri_obat_operasi.tanggal").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Penggunaan Obat/BHP Operasi</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Tanggal</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Obat/BHP</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("tanggal")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kd_obat")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nm_obat")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jumlah")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Resep Pulang
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select resep_pulang.kode_brng,databarang.nama_brng,resep_pulang.dosis,resep_pulang.jml_barang, "+
                                    "databarang.kode_sat,resep_pulang.dosis,resep_pulang.total from resep_pulang inner join databarang "+
                                    "on resep_pulang.kode_brng=databarang.kode_brng where "+
                                    "resep_pulang.no_rawat='"+rs2.getString("no_rawat")+"' order by databarang.nama_brng").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='4'>Resep Pulang</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='50%' bgcolor='#FFFAF8'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='15%' bgcolor='#FFFAF8'>Dosis</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("dosis")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jml_barang")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Retur Obat
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select databarang.kode_brng,databarang.nama_brng,detreturjual.kode_sat,detreturjual.h_retur, "+
				    "(detreturjual.jml_retur * -1) as jumlah,(detreturjual.subtotal * -1) as total from detreturjual "+
				    "inner join databarang inner join returjual on detreturjual.kode_brng=databarang.kode_brng "+
				    "and returjual.no_retur_jual=detreturjual.no_retur_jual where returjual.no_retur_jual='"+rs2.getString("no_rawat")+"' order by databarang.nama_brng").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Retur Obat</td><td valign='top' colspan='1' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Kode</td>"+
                                          "<td valign='top' width='65%' bgcolor='#FFFAF8'>Nama Obat/BHP/Alkes</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Jumlah</td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("kode_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_brng")+"</td>"+
                                                "<td valign='top'>"+rs3.getDouble("jumlah")+" "+rs3.getString("kode_sat")+"</td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("total"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Tambahan Biaya
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select nama_biaya, besar_biaya from tambahan_biaya where no_rawat='"+rs2.getString("no_rawat")+"' order by nama_biaya").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='2'>Tambahan Biaya</td><td valign='top' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='84%' bgcolor='#FFFAF8'>Nama Tambahan</td>"+
                                          "<td valign='top' width='1%' bgcolor='#FFFAF8'></td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_biaya")+"</td>"+
                                                "<td valign='top'></td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("besar_biaya"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //Pengurangan Biaya
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select nama_pengurangan, (-1*besar_pengurangan) as besar_pengurangan from pengurangan_biaya where no_rawat='"+rs2.getString("no_rawat")+"' order by nama_pengurangan").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='2'>Potongan Biaya</td><td valign='top' align='right'>:</td><td></td></tr>"+            
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='84%' bgcolor='#FFFAF8'>Nama Potongan</td>"+
                                          "<td valign='top' width='1%' bgcolor='#FFFAF8'></td>"+
                                          "<td valign='top' width='10%' bgcolor='#FFFAF8'>Biaya</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'>"+rs3.getString("nama_pengurangan")+"</td>"+
                                                "<td valign='top'></td>"+
                                                "<td valign='top' align='right'>"+Valid.SetAngka(rs3.getDouble("besar_pengurangan"))+"</td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            //berkas digital perawatan
                            try{
                                rs3=koneksi.prepareStatement(
                                    "select master_berkas_digital.nama,berkas_digital_perawatan.lokasi_file "+
				    "from berkas_digital_perawatan inner join master_berkas_digital "+
                                    "on berkas_digital_perawatan.kode=master_berkas_digital.kode "+
                                    "where berkas_digital_perawatan.no_rawat='"+rs2.getString("no_rawat")+"'").executeQuery();
                                if(rs3.next()){                                    
                                    htmlContent.append(  
                                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                        "<tr><td valign='top' colspan='3'>Berkas Digital Perawatan</td></tr>"+  
                                        "<tr align='center'>"+
                                          "<td valign='top' width='5%' bgcolor='#FFFAF8'>No.</td>"+
                                          "<td valign='top' width='95%' bgcolor='#FFFAF8'>Berkas Digital</td>"+
                                        "</tr>");
                                    rs3.beforeFirst();
                                    w=1;
                                    while(rs3.next()){
                                        htmlContent.append(
                                             "<tr>"+
                                                "<td valign='top' align='center'>"+w+"</td>"+
                                                "<td valign='top'><a href='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/berkasrawat/"+rs3.getString("lokasi_file")+"'>"+rs3.getString("nama").replaceAll("pages/upload/","")+"</a></td>"+
                                             "</tr>"); 
                                        w++;
                                    }
                                    htmlContent.append(
                                      "</table>");
                                }                                
                            } catch (Exception e) {
                                System.out.println("Notifikasi : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                            }
                            
                            htmlContent.append(                                    
                                 "</td>"+
                               "</tr>"                               
                            );
                            htmlContent.append("<tr class='isi'><td colspan='3'>&nbsp;</td></tr>");
                        }        
                    } catch (Exception e) {
                        System.out.println("Notifikasi : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                    }
                    y++;
                }
                LoadHTML14.setText(
                    "<html>"+
                      "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>");
            } catch (Exception e) {
                System.out.println("Notifikasi : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
            }
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void setRM(String norm,String tanggal1,String tanggal2,boolean caritanggal){
        this.norm=norm;
        this.tanggal1=tanggal1;
        this.tanggal2=tanggal2;
        this.caritanggal=caritanggal;
    }

    public void pilihTab() {
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampil15();
                break;
            case 1:
                tampil();
                break;
            case 2:
                tampil2();
                break;
            case 3:
                tampil3();
                break;
            case 4:
                tampil4();
                break;
            case 5:
                tampil5();
                break;
            case 6:
                tampil6();
                break;
            case 7:
                tampil7();
                break;
            case 8:
                tampil8();
                break;
            case 9:
                tampil9();
                break;
            case 10:
                tampil10();
                break;
            case 11:
                tampil11();
                break;
            case 12:
                tampil12();
                break;
            case 13:
                tampil13();
                break;
            case 14:
                tampil14();
                break;
            default:
                break;
        }
    }
    
    public void laporan(){
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                panggilLaporan(LoadHTML.getText());                    
                break;
            case 1:
                panggilLaporan(LoadHTML2.getText());
                break;
            case 2:
                panggilLaporan(LoadHTML3.getText());
                break;
            case 3:
                panggilLaporan(LoadHTML4.getText());
                break;
            case 4:
                panggilLaporan(LoadHTML5.getText());
                break;
            case 5:
                panggilLaporan(LoadHTML6.getText());
                break;
            case 6:
                panggilLaporan(LoadHTML7.getText());
                break;
            case 7:
                panggilLaporan(LoadHTML8.getText());
                break;
            case 8:
                panggilLaporan(LoadHTML9.getText());
                break;
            case 9:
                panggilLaporan(LoadHTML10.getText());
                break;
            case 10:
                panggilLaporan(LoadHTML11.getText());
                break;
            case 11:
                panggilLaporan(LoadHTML12.getText());
                break;
            case 12:
                if(tabModeRegistrasi.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                }else if(tabModeRegistrasi.getRowCount()!=0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));        
                    Sequel.queryu("delete from temporary_resume");
                    
                    for(int i=0;i<tabModeRegistrasi.getRowCount();i++){  
                        Sequel.menyimpan("temporary_resume","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?",38,new String[]{
                            "0",tabModeRegistrasi.getValueAt(i,0).toString(),tabModeRegistrasi.getValueAt(i,1).toString(),tabModeRegistrasi.getValueAt(i,2).toString(),
                            tabModeRegistrasi.getValueAt(i,3).toString(),tabModeRegistrasi.getValueAt(i,4).toString(),tabModeRegistrasi.getValueAt(i,5).toString(),
                            tabModeRegistrasi.getValueAt(i,6).toString(),tabModeRegistrasi.getValueAt(i,7).toString(),tabModeRegistrasi.getValueAt(i,8).toString(),
                            "","","","","","","","","","","","","","","","","","","","","","","","","","","","",""
                        });
                    }
                    
                    Map<String, Object> param = new HashMap<>();  
                        param.put("namars",akses.getnamars());
                        param.put("alamatrs",akses.getalamatrs());
                        param.put("kotars",akses.getkabupatenrs());
                        param.put("propinsirs",akses.getpropinsirs());
                        param.put("kontakrs",akses.getkontakrs());
                        param.put("emailrs",akses.getemailrs());   
                        param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport2("rptRiwayatRegistrasi.jasper","report","::[ Riwayat Registrasi ]::",param);
                    this.setCursor(Cursor.getDefaultCursor());
                }
                break;
            case 13:
                panggilLaporan(LoadHTML13.getText());
                break;
            default:
                break;
        }    
        this.setCursor(Cursor.getDefaultCursor());
    }
}
