package simrskhanza;
import fungsi.WarnaTable;
import kepegawaian.DlgCariPetugas;
import keuangan.Jurnal;
import fungsi.WarnaTable4;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import laporan.DlgBerkasRawat;

public class DlgCariPeriksaLabPA extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private DlgPasien member=new DlgPasien(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private int i,jmlkunjungan=0,jmlpemeriksaan=0,jmlsubpemeriksaan=0;
    private PreparedStatement ps,ps2,ps3,ps4,psrekening,ps5,pspermintaan;
    private ResultSet rs,rs2,rs3,rs5,rsrekening,rspermintaan;
    private String kamar,namakamar,datapasien="",finger="";
    private boolean sukses=false;
    private double ttl=0,item=0;
    private StringBuilder htmlContent;
    private double ttljmdokter=0,ttljmpetugas=0,ttlkso=0,ttlpendapatan=0,ttlbhp=0,ttljasasarana=0,ttljmperujuk=0,ttlmenejemen=0;
    private String diagnosa_klinik, makroskopik, mikroskopik, kesimpulan, kesan,Suspen_Piutang_Laborat_Ranap="",Laborat_Ranap="",Beban_Jasa_Medik_Dokter_Laborat_Ranap="",Utang_Jasa_Medik_Dokter_Laborat_Ranap="",
            Beban_Jasa_Medik_Petugas_Laborat_Ranap="",Utang_Jasa_Medik_Petugas_Laborat_Ranap="",Beban_Kso_Laborat_Ranap="",Utang_Kso_Laborat_Ranap="",
            HPP_Persediaan_Laborat_Rawat_inap="",Persediaan_BHP_Laborat_Rawat_Inap="",Beban_Jasa_Sarana_Laborat_Ranap="",Utang_Jasa_Sarana_Laborat_Ranap="",
            Beban_Jasa_Perujuk_Laborat_Ranap="",Utang_Jasa_Perujuk_Laborat_Ranap="",Beban_Jasa_Menejemen_Laborat_Ranap="",Utang_Jasa_Menejemen_Laborat_Ranap="",
            Suspen_Piutang_Laborat_Ralan="",Laborat_Ralan="",Beban_Jasa_Medik_Dokter_Laborat_Ralan="",Utang_Jasa_Medik_Dokter_Laborat_Ralan="",
            Beban_Jasa_Medik_Petugas_Laborat_Ralan="",Utang_Jasa_Medik_Petugas_Laborat_Ralan="",Beban_Kso_Laborat_Ralan="",Utang_Kso_Laborat_Ralan="",
            HPP_Persediaan_Laborat_Rawat_Jalan="",Persediaan_BHP_Laborat_Rawat_Jalan="",Beban_Jasa_Sarana_Laborat_Ralan="",Utang_Jasa_Sarana_Laborat_Ralan="",
            Beban_Jasa_Perujuk_Laborat_Ralan="",Utang_Jasa_Perujuk_Laborat_Ralan="",Beban_Jasa_Menejemen_Laborat_Ralan="",Utang_Jasa_Menejemen_Laborat_Ralan="",status="";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariPeriksaLabPA(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.Rawat","Pasien","Petugas","Tgl.Periksa","Jam Periksa","Dokter Perujuk","Penanggung Jawab","Pemeriksaan","Biaya","Diagnosa Klinis","Makroskopik","Mikroskopik","Kesimpulan","Kesan","Kode Periksa","Cara Bayar"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 16; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(110);
            }else if(i==1){
                column.setPreferredWidth(220);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(70);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(130);
            }else if(i==8){
                column.setPreferredWidth(100);
            }else if(i==9){
                column.setPreferredWidth(130);
            }else if(i==10){
                column.setPreferredWidth(230);
            }else if(i==11){
                column.setPreferredWidth(230);
            }else if(i==12){
                column.setPreferredWidth(140);
            }else if(i==13){
                column.setPreferredWidth(140);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setPreferredWidth(130);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        
        NoRawat.setDocument(new batasInput((byte)17).getKata(NoRawat));
        kdmem.setDocument(new batasInput((byte)8).getKata(kdmem));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        DiagnosaKlinis.setDocument(new batasInput((int)50).getKata(DiagnosaKlinis));
        Makroskopis.setDocument(new batasInput((int)1024).getKata(Makroskopis));
        Mikroskopis.setDocument(new batasInput((int)1024).getKata(Mikroskopis));
        Kesimpulan.setDocument(new batasInput((int)300).getKata(Kesimpulan));
        Kesan.setDocument(new batasInput((int)300).getKata(Kesan));
        if(koneksiDB.CARICEPAT().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        } 
        
        member.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPeriksaLab")){
                    if(member.getTable().getSelectedRow()!= -1){                   
                        kdmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),1).toString());
                        nmmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),2).toString());
                    } 
                    if(member.getTable2().getSelectedRow()!= -1){                   
                        kdmem.setText(member.getTable2().getValueAt(member.getTable2().getSelectedRow(),1).toString());
                        nmmem.setText(member.getTable2().getValueAt(member.getTable2().getSelectedRow(),2).toString());
                    }  
                    if(member.getTable3().getSelectedRow()!= -1){                   
                        kdmem.setText(member.getTable3().getValueAt(member.getTable3().getSelectedRow(),1).toString());
                        nmmem.setText(member.getTable3().getValueAt(member.getTable3().getSelectedRow(),2).toString());
                    } 
                    kdmem.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        member.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgCariPeriksaLab")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        member.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        member.getTable2().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgCariPeriksaLab")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        member.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        member.getTable3().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgCariPeriksaLab")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        member.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgCariPeriksaLab")){
                    if(petugas.getTable().getSelectedRow()!= -1){                   
                        kdptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmptg.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                    }  
                    kdptg.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        
        try {
            psrekening=koneksi.prepareStatement("select * from set_akun_ranap");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Laborat_Ranap=rsrekening.getString("Suspen_Piutang_Laborat_Ranap");
                    Laborat_Ranap=rsrekening.getString("Laborat_Ranap");
                    Beban_Jasa_Medik_Dokter_Laborat_Ranap=rsrekening.getString("Beban_Jasa_Medik_Dokter_Laborat_Ranap");
                    Utang_Jasa_Medik_Dokter_Laborat_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Laborat_Ranap");
                    Beban_Jasa_Medik_Petugas_Laborat_Ranap=rsrekening.getString("Beban_Jasa_Medik_Petugas_Laborat_Ranap");
                    Utang_Jasa_Medik_Petugas_Laborat_Ranap=rsrekening.getString("Utang_Jasa_Medik_Petugas_Laborat_Ranap");
                    Beban_Kso_Laborat_Ranap=rsrekening.getString("Beban_Kso_Laborat_Ranap");
                    Utang_Kso_Laborat_Ranap=rsrekening.getString("Utang_Kso_Laborat_Ranap");
                    HPP_Persediaan_Laborat_Rawat_inap=rsrekening.getString("HPP_Persediaan_Laborat_Rawat_inap");
                    Persediaan_BHP_Laborat_Rawat_Inap=rsrekening.getString("Persediaan_BHP_Laborat_Rawat_Inap");
                    Beban_Jasa_Sarana_Laborat_Ranap=rsrekening.getString("Beban_Jasa_Sarana_Laborat_Ranap");
                    Utang_Jasa_Sarana_Laborat_Ranap=rsrekening.getString("Utang_Jasa_Sarana_Laborat_Ranap");
                    Beban_Jasa_Perujuk_Laborat_Ranap=rsrekening.getString("Beban_Jasa_Perujuk_Laborat_Ranap");
                    Utang_Jasa_Perujuk_Laborat_Ranap=rsrekening.getString("Utang_Jasa_Perujuk_Laborat_Ranap");
                    Beban_Jasa_Menejemen_Laborat_Ranap=rsrekening.getString("Beban_Jasa_Menejemen_Laborat_Ranap");
                    Utang_Jasa_Menejemen_Laborat_Ranap=rsrekening.getString("Utang_Jasa_Menejemen_Laborat_Ranap");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }   
            
            psrekening=koneksi.prepareStatement("select * from set_akun_ralan");
            try {
                rsrekening=psrekening.executeQuery();
                while(rsrekening.next()){
                    Suspen_Piutang_Laborat_Ralan=rsrekening.getString("Suspen_Piutang_Laborat_Ralan");
                    Laborat_Ralan=rsrekening.getString("Laborat_Ralan");
                    Beban_Jasa_Medik_Dokter_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Medik_Dokter_Laborat_Ralan");
                    Utang_Jasa_Medik_Dokter_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Medik_Dokter_Laborat_Ralan");
                    Beban_Jasa_Medik_Petugas_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Medik_Petugas_Laborat_Ralan");
                    Utang_Jasa_Medik_Petugas_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Medik_Petugas_Laborat_Ralan");
                    Beban_Kso_Laborat_Ralan=rsrekening.getString("Beban_Kso_Laborat_Ralan");
                    Utang_Kso_Laborat_Ralan=rsrekening.getString("Utang_Kso_Laborat_Ralan");
                    HPP_Persediaan_Laborat_Rawat_Jalan=rsrekening.getString("HPP_Persediaan_Laborat_Rawat_Jalan");
                    Persediaan_BHP_Laborat_Rawat_Jalan=rsrekening.getString("Persediaan_BHP_Laborat_Rawat_Jalan");
                    Beban_Jasa_Sarana_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Sarana_Laborat_Ralan");
                    Utang_Jasa_Sarana_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Sarana_Laborat_Ralan");
                    Beban_Jasa_Perujuk_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Perujuk_Laborat_Ralan");
                    Utang_Jasa_Perujuk_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Perujuk_Laborat_Ralan");
                    Beban_Jasa_Menejemen_Laborat_Ralan=rsrekening.getString("Beban_Jasa_Menejemen_Laborat_Ralan");
                    Utang_Jasa_Menejemen_Laborat_Ralan=rsrekening.getString("Utang_Jasa_Menejemen_Laborat_Ralan");
                }
            } catch (Exception e) {
                System.out.println("Notif Rekening : "+e);
            } finally{
                if(rsrekening!=null){
                    rsrekening.close();
                }
                if(psrekening!=null){
                    psrekening.close();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML1.setEditable(true);
        LoadHTML1.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".head td{border-right: 1px solid #777777;font: 8.5px tahoma;height:10px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi a{text-decoration:none;color:#8b9b95;padding:0 0 0 0px;font-family: Tahoma;font-size: 8.5px;}"+
                ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
        );
        Document doc = kit.createDefaultDocument();
        LoadHTML1.setDocument(doc);
        LoadHTML1.setEditable(false);
        
        HTMLEditorKit kit2 = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet2 = kit2.getStyleSheet();
        styleSheet2.addRule(
                ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi2 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#323232;}"+
                ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                ".isi5 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#AA0000;}"+
                ".isi6 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#FF0000;}"+
                ".isi7 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#C8C800;}"+
                ".isi8 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#00AA00;}"+
                ".isi9 td{font: 8.5px tahoma;border:none;height:12px;background: #ffffff;color:#969696;}"
        );
        
        Document doc2 = kit2.createDefaultDocument();
        LoadHTML.setDocument(doc2);
     
        ChkAccor.setSelected(false);
        isPhoto();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Kd2 = new widget.TextBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnCetakHasilLab = new javax.swing.JMenuItem();
        MnCetakHasilLab11 = new javax.swing.JMenuItem();
        MnCetakNota = new javax.swing.JMenuItem();
        ppBerkasDigital = new javax.swing.JMenuItem();
        Tinggi22 = new javax.swing.JMenuItem();
        Tinggi32 = new javax.swing.JMenuItem();
        Tinggi150 = new javax.swing.JMenuItem();
        Tinggi200 = new javax.swing.JMenuItem();
        Tinggi250 = new javax.swing.JMenuItem();
        Tinggi300 = new javax.swing.JMenuItem();
        Tinggi350 = new javax.swing.JMenuItem();
        Tinggi400 = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        label15 = new widget.Label();
        NoRawat = new widget.TextBox();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label16 = new widget.Label();
        label13 = new widget.Label();
        kdmem = new widget.TextBox();
        kdptg = new widget.TextBox();
        nmmem = new widget.TextBox();
        nmptg = new widget.TextBox();
        btnPasien = new widget.Button();
        btnPetugas = new widget.Button();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label12 = new widget.Label();
        LTotal = new widget.Label();
        BtnHapus = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        panelisi2 = new widget.panelisi();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        ScrollMenu = new widget.ScrollPane();
        FormPhotoPass = new widget.PanelBiasa();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        btnAmbilPhoto = new widget.Button();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        FormPass = new widget.PanelBiasa();
        label14 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        DiagnosaKlinis = new widget.TextArea();
        label17 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        Makroskopis = new widget.TextArea();
        btnUbahPassword = new widget.Button();
        label19 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Mikroskopis = new widget.TextArea();
        label20 = new widget.Label();
        scrollPane5 = new widget.ScrollPane();
        Kesimpulan = new widget.TextArea();
        label21 = new widget.Label();
        scrollPane6 = new widget.ScrollPane();
        Kesan = new widget.TextArea();
        Scroll = new widget.ScrollPane();
        LoadHTML1 = new widget.editorpane();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakHasilLab.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab.setText("Cetak Hasil Lab");
        MnCetakHasilLab.setName("MnCetakHasilLab"); // NOI18N
        MnCetakHasilLab.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakHasilLab);

        MnCetakHasilLab11.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab11.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab11.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab11.setText("PDF Hasil Lab");
        MnCetakHasilLab11.setName("MnCetakHasilLab11"); // NOI18N
        MnCetakHasilLab11.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab11ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakHasilLab11);

        MnCetakNota.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakNota.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakNota.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakNota.setText("Cetak Nota Lab");
        MnCetakNota.setName("MnCetakNota"); // NOI18N
        MnCetakNota.setPreferredSize(new java.awt.Dimension(190, 26));
        MnCetakNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakNotaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakNota);

        ppBerkasDigital.setBackground(new java.awt.Color(255, 255, 254));
        ppBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital.setForeground(new java.awt.Color(50, 50, 50));
        ppBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBerkasDigital.setText("Berkas Digital Perawatan");
        ppBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital.setName("ppBerkasDigital"); // NOI18N
        ppBerkasDigital.setPreferredSize(new java.awt.Dimension(190, 26));
        ppBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigitalBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppBerkasDigital);

        Tinggi22.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi22.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi22.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi22.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi22.setLabel("Tinggi Baris 22");
        Tinggi22.setName("Tinggi22"); // NOI18N
        Tinggi22.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi22ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Tinggi22);

        Tinggi32.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi32.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi32.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi32.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi32.setLabel("Tinggi Baris 32");
        Tinggi32.setName("Tinggi32"); // NOI18N
        Tinggi32.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi32ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Tinggi32);

        Tinggi150.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi150.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi150.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi150.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi150.setText("Tinggi Baris 150");
        Tinggi150.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi150.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi150.setName("Tinggi150"); // NOI18N
        Tinggi150.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi150.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi150ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Tinggi150);

        Tinggi200.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi200.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi200.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi200.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi200.setText("Tinggi Baris 200");
        Tinggi200.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi200.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi200.setName("Tinggi200"); // NOI18N
        Tinggi200.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi200.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi200ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Tinggi200);

        Tinggi250.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi250.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi250.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi250.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi250.setText("Tinggi Baris 250");
        Tinggi250.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi250.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi250.setName("Tinggi250"); // NOI18N
        Tinggi250.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi250.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi250ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Tinggi250);

        Tinggi300.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi300.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi300.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi300.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi300.setText("Tinggi Baris 300");
        Tinggi300.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi300.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi300.setName("Tinggi300"); // NOI18N
        Tinggi300.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi300.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi300ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Tinggi300);

        Tinggi350.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi350.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi350.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi350.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi350.setText("Tinggi Baris 350");
        Tinggi350.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi350.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi350.setName("Tinggi350"); // NOI18N
        Tinggi350.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi350.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi350ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Tinggi350);

        Tinggi400.setBackground(new java.awt.Color(255, 255, 254));
        Tinggi400.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        Tinggi400.setForeground(new java.awt.Color(50, 50, 50));
        Tinggi400.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        Tinggi400.setText("Tinggi Baris 400");
        Tinggi400.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Tinggi400.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        Tinggi400.setName("Tinggi400"); // NOI18N
        Tinggi400.setPreferredSize(new java.awt.Dimension(170, 25));
        Tinggi400.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tinggi400ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(Tinggi400);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pemeriksaan Laboratorium PA ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label15.setText("No.Rawat :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 75, 23);

        NoRawat.setName("NoRawat"); // NOI18N
        NoRawat.setPreferredSize(new java.awt.Dimension(207, 23));
        NoRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRawatKeyPressed(evt);
            }
        });
        panelisi3.add(NoRawat);
        NoRawat.setBounds(79, 10, 226, 23);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 75, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl1);
        Tgl1.setBounds(79, 40, 100, 23);

        label16.setText("Pasien :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label16);
        label16.setBounds(385, 10, 60, 23);

        label13.setText("Petugas :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(385, 40, 60, 23);

        kdmem.setName("kdmem"); // NOI18N
        kdmem.setPreferredSize(new java.awt.Dimension(80, 23));
        kdmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdmemKeyPressed(evt);
            }
        });
        panelisi3.add(kdmem);
        kdmem.setBounds(449, 10, 80, 23);

        kdptg.setName("kdptg"); // NOI18N
        kdptg.setPreferredSize(new java.awt.Dimension(80, 23));
        kdptg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdptgKeyPressed(evt);
            }
        });
        panelisi3.add(kdptg);
        kdptg.setBounds(449, 40, 80, 23);

        nmmem.setEditable(false);
        nmmem.setName("nmmem"); // NOI18N
        nmmem.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmmem);
        nmmem.setBounds(531, 10, 240, 23);

        nmptg.setEditable(false);
        nmptg.setName("nmptg"); // NOI18N
        nmptg.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmptg);
        nmptg.setBounds(531, 40, 240, 23);

        btnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPasien.setMnemonic('1');
        btnPasien.setToolTipText("Alt+1");
        btnPasien.setName("btnPasien"); // NOI18N
        btnPasien.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPasienActionPerformed(evt);
            }
        });
        panelisi3.add(btnPasien);
        btnPasien.setBounds(774, 10, 28, 23);

        btnPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPetugas.setMnemonic('2');
        btnPetugas.setToolTipText("Alt+2");
        btnPetugas.setName("btnPetugas"); // NOI18N
        btnPetugas.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPetugasActionPerformed(evt);
            }
        });
        panelisi3.add(btnPetugas);
        btnPetugas.setBounds(774, 40, 28, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(178, 40, 30, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl2);
        Tgl2.setBounds(205, 40, 100, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(210, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi1.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('5');
        BtnCari.setToolTipText("Alt+5");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelisi1.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi1.add(BtnAll);

        label12.setText("Total :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(40, 23));
        panelisi1.add(label12);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(100, 23));
        panelisi1.add(LTotal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelisi1.add(BtnHapus);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelisi1.add(BtnPrint);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelisi1.add(BtnKeluar);

        internalFrame1.add(panelisi1, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi2.setLayout(new java.awt.BorderLayout());

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane1.setComponentPopupMenu(jPopupMenu1);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter.setComponentPopupMenu(jPopupMenu1);
        tbDokter.setName("tbDokter"); // NOI18N
        tbDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokterMouseClicked(evt);
            }
        });
        tbDokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokterKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        panelisi2.add(scrollPane1, java.awt.BorderLayout.CENTER);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(445, 43));
        PanelAccor.setLayout(new java.awt.BorderLayout(1, 1));

        ChkAccor.setBackground(new java.awt.Color(255, 250, 248));
        ChkAccor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setSelected(true);
        ChkAccor.setFocusable(false);
        ChkAccor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkAccor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkAccor.setName("ChkAccor"); // NOI18N
        ChkAccor.setPreferredSize(new java.awt.Dimension(15, 20));
        ChkAccor.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kiri.png"))); // NOI18N
        ChkAccor.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/kanan.png"))); // NOI18N
        ChkAccor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkAccorActionPerformed(evt);
            }
        });
        PanelAccor.add(ChkAccor, java.awt.BorderLayout.WEST);

        ScrollMenu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        ScrollMenu.setComponentPopupMenu(jPopupMenu1);
        ScrollMenu.setName("ScrollMenu"); // NOI18N
        ScrollMenu.setOpaque(true);
        ScrollMenu.setPreferredSize(new java.awt.Dimension(407, 1075));

        FormPhotoPass.setBackground(new java.awt.Color(255, 255, 255));
        FormPhotoPass.setBorder(null);
        FormPhotoPass.setName("FormPhotoPass"); // NOI18N
        FormPhotoPass.setPreferredSize(new java.awt.Dimension(405, 1050));
        FormPhotoPass.setLayout(new java.awt.BorderLayout());

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)), " Photo : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(400, 400));
        FormPhoto.setLayout(new java.awt.BorderLayout());

        FormPass2.setBackground(new java.awt.Color(255, 255, 255));
        FormPass2.setBorder(null);
        FormPass2.setName("FormPass2"); // NOI18N
        FormPass2.setPreferredSize(new java.awt.Dimension(115, 40));

        btnAmbilPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        btnAmbilPhoto.setMnemonic('U');
        btnAmbilPhoto.setText("Ambil");
        btnAmbilPhoto.setToolTipText("Alt+U");
        btnAmbilPhoto.setName("btnAmbilPhoto"); // NOI18N
        btnAmbilPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        btnAmbilPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAmbilPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(btnAmbilPhoto);

        BtnRefreshPhoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/refresh.png"))); // NOI18N
        BtnRefreshPhoto.setMnemonic('U');
        BtnRefreshPhoto.setText("Refresh");
        BtnRefreshPhoto.setToolTipText("Alt+U");
        BtnRefreshPhoto.setName("BtnRefreshPhoto"); // NOI18N
        BtnRefreshPhoto.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnRefreshPhoto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnRefreshPhotoActionPerformed(evt);
            }
        });
        FormPass2.add(BtnRefreshPhoto);

        FormPhoto.add(FormPass2, java.awt.BorderLayout.PAGE_END);

        Scroll4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(400, 400));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll4.setViewportView(LoadHTML);

        FormPhoto.add(Scroll4, java.awt.BorderLayout.CENTER);

        FormPhotoPass.add(FormPhoto, java.awt.BorderLayout.PAGE_START);

        FormPass.setBackground(new java.awt.Color(255, 255, 255));
        FormPass.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)), " Hasil Pemeriksaan : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPass.setName("FormPass"); // NOI18N
        FormPass.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPass.setLayout(null);

        label14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label14.setText("Diagnosa Klinis :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(60, 23));
        FormPass.add(label14);
        label14.setBounds(15, 15, 90, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        DiagnosaKlinis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        DiagnosaKlinis.setColumns(20);
        DiagnosaKlinis.setRows(5);
        DiagnosaKlinis.setName("DiagnosaKlinis"); // NOI18N
        DiagnosaKlinis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DiagnosaKlinisKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(DiagnosaKlinis);

        FormPass.add(scrollPane2);
        scrollPane2.setBounds(15, 33, 385, 30);

        label17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label17.setText("Makroskopik :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(60, 23));
        FormPass.add(label17);
        label17.setBounds(15, 69, 90, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        Makroskopis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Makroskopis.setColumns(20);
        Makroskopis.setRows(5);
        Makroskopis.setName("Makroskopis"); // NOI18N
        Makroskopis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MakroskopisKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(Makroskopis);

        FormPass.add(scrollPane3);
        scrollPane3.setBounds(15, 87, 385, 170);

        btnUbahPassword.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        btnUbahPassword.setMnemonic('U');
        btnUbahPassword.setText("Ubah");
        btnUbahPassword.setToolTipText("Alt+U");
        btnUbahPassword.setName("btnUbahPassword"); // NOI18N
        btnUbahPassword.setPreferredSize(new java.awt.Dimension(100, 30));
        btnUbahPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahPasswordActionPerformed(evt);
            }
        });
        FormPass.add(btnUbahPassword);
        btnUbahPassword.setBounds(145, 610, 100, 30);

        label19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label19.setText("Mikroskopik :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(60, 23));
        FormPass.add(label19);
        label19.setBounds(15, 263, 90, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Mikroskopis.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Mikroskopis.setColumns(20);
        Mikroskopis.setRows(5);
        Mikroskopis.setName("Mikroskopis"); // NOI18N
        Mikroskopis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MikroskopisKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Mikroskopis);

        FormPass.add(scrollPane4);
        scrollPane4.setBounds(15, 281, 385, 170);

        label20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label20.setText("Kesimpulan :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(60, 23));
        FormPass.add(label20);
        label20.setBounds(15, 457, 90, 23);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Kesimpulan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kesimpulan.setColumns(20);
        Kesimpulan.setRows(5);
        Kesimpulan.setName("Kesimpulan"); // NOI18N
        Kesimpulan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesimpulanKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Kesimpulan);

        FormPass.add(scrollPane5);
        scrollPane5.setBounds(15, 475, 385, 50);

        label21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        label21.setText("Kesan :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(60, 23));
        FormPass.add(label21);
        label21.setBounds(15, 531, 90, 23);

        scrollPane6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane6.setName("scrollPane6"); // NOI18N

        Kesan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kesan.setColumns(20);
        Kesan.setRows(5);
        Kesan.setName("Kesan"); // NOI18N
        Kesan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesanKeyPressed(evt);
            }
        });
        scrollPane6.setViewportView(Kesan);

        FormPass.add(scrollPane6);
        scrollPane6.setBounds(15, 549, 385, 50);

        FormPhotoPass.add(FormPass, java.awt.BorderLayout.CENTER);

        ScrollMenu.setViewportView(FormPhotoPass);

        PanelAccor.add(ScrollMenu, java.awt.BorderLayout.CENTER);

        panelisi2.add(PanelAccor, java.awt.BorderLayout.EAST);

        TabRawat.addTab("Data Pemeriksaan", panelisi2);

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        LoadHTML1.setBorder(null);
        LoadHTML1.setName("LoadHTML1"); // NOI18N
        Scroll.setViewportView(LoadHTML1);

        TabRawat.addTab("Detail Kunjungan", Scroll);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        akses.setform("DlgCariPeriksaLab");
        member.emptTeks();
        member.isCek();
        member.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        member.setLocationRelativeTo(internalFrame1);
        member.setAlwaysOnTop(false);
        member.setVisible(true);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        akses.setform("DlgCariPeriksaLab");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setAlwaysOnTop(false);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPetugasActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,kdmem,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void kdmemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdmemKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());      
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
            NoRawat.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPasienActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
            Tgl1.requestFocus();      
        }
    }//GEN-LAST:event_kdmemKeyPressed

    private void NoRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRawatKeyPressed
        Valid.pindah(evt, BtnKeluar, kdptg);
    }//GEN-LAST:event_NoRawatKeyPressed

    private void kdptgKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdptgKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPetugasActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nama from petugas where nip=?", nmptg,kdptg.getText());
            NoRawat.requestFocus();            
        }
    }//GEN-LAST:event_kdptgKeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kdptg);
    }//GEN-LAST:event_Tgl2KeyPressed

private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
    if(tabMode.getRowCount()!=0){
        try {
            isPhoto();
            panggilPhoto();
            getData();
        } catch (java.lang.NullPointerException e) {
        }
    }
}//GEN-LAST:event_tbDokterMouseClicked

private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
   if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void MnCetakHasilLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLabActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencetak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){   
            try {   
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where periksa_lab.kategori='PA' and "+
                    "periksa_lab.tgl_periksa=? and periksa_lab.jam=? and periksa_lab.no_rawat=? group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam)");
                try {
                    ps4.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                    ps4.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                    ps4.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                    rs=ps4.executeQuery();
                    while(rs.next()){
                        kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat='"+rs.getString("no_rawat")+"' order by tgl_masuk desc limit 1");
                        if(!kamar.equals("")){
                            namakamar=kamar+", "+Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                                    " where kamar.kd_kamar='"+kamar+"' ");            
                            kamar="Kamar";
                        }else if(kamar.equals("")){
                            kamar="Poli";
                            namakamar=Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                                    "where reg_periksa.no_rawat='"+rs.getString("no_rawat")+"'");
                        }
                        Map<String, Object> param = new HashMap<>();
                        param.put("noperiksa",rs.getString("no_rawat"));
                        param.put("norm",rs.getString("no_rkm_medis"));
                        param.put("namapasien",rs.getString("nm_pasien"));
                        param.put("jkel",rs.getString("jk"));
                        param.put("umur",rs.getString("umur"));
                        param.put("pengirim",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")));
                        param.put("tanggal",rs.getString("tgl_periksa"));
                        param.put("penjab",rs.getString("nm_dokter"));
                        param.put("petugas",rs.getString("nama"));
                        param.put("jam",rs.getString("jam"));
                        param.put("alamat",rs.getString("alamat"));
                        param.put("kamar",kamar);
                        param.put("namakamar",namakamar);
                        finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter"));
                        param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nm_dokter")+"\nID "+(finger.equals("")?rs.getString("kd_dokter"):finger)+"\n"+rs.getString("tgl_periksa"));  
                        finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip"));
                        param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nama")+"\nID "+(finger.equals("")?rs.getString("nip"):finger)+"\n"+rs.getString("tgl_periksa"));  
                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.kategori='PA' and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ps3=koneksi.prepareStatement(
                                    "select diagnosa_klinik, makroskopik, mikroskopik, kesimpulan, kesan from detail_periksa_labpa "+
                                    "where no_rawat=? and kd_jenis_prw=? and tgl_periksa=? and jam=? ");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan").replaceAll("'","")+"','"+rs3.getString("diagnosa_klinik").replaceAll("'","")+"','"+rs3.getString("makroskopik").replaceAll("'","")
                                                +"','"+rs3.getString("mikroskopik").replaceAll("'","")+"','"+rs3.getString("kesimpulan").replaceAll("'","")+"','"+rs3.getString("kesan").replaceAll("'","")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif ps3 : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif ps2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        param.put("namars",akses.getnamars());
                        param.put("alamatrs",akses.getalamatrs());
                        param.put("kotars",akses.getkabupatenrs());
                        param.put("propinsirs",akses.getpropinsirs());
                        param.put("kontakrs",akses.getkontakrs());
                        param.put("emailrs",akses.getemailrs());   
                        param.put("logo",Sequel.cariGambar("select logo from setting")); 
                        pspermintaan=koneksi.prepareStatement(
                                "select noorder,DATE_FORMAT(tgl_permintaan,'%d-%m-%Y') as tgl_permintaan,jam_permintaan from permintaan_lab where "+
                                "no_rawat=? and tgl_hasil=? and jam_hasil=?");
                        try {
                            pspermintaan.setString(1,rs.getString("no_rawat"));
                            pspermintaan.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            pspermintaan.setString(3,rs.getString("jam"));
                            rspermintaan=pspermintaan.executeQuery();
                            if(rspermintaan.next()){
                                param.put("nopermintaan",rspermintaan.getString("noorder"));   
                                param.put("tanggalpermintaan",rspermintaan.getString("tgl_permintaan"));  
                                param.put("jampermintaan",rspermintaan.getString("jam_permintaan"));
                                Valid.MyReport("rptPeriksaLabPermintaanPA.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }else{
                                Valid.MyReport("rptPeriksaLabPA.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rspermintaan!=null){
                                rspermintaan.close();
                            }
                            if(pspermintaan!=null){
                                pspermintaan.close();
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps4 : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps4!=null){
                        ps4.close();
                    }
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }            
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCetakHasilLabActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_formWindowOpened

    private void MnCetakNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakNotaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencetak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){  
            try {
                
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where periksa_lab.kategori='PA' and "+
                    "periksa_lab.tgl_periksa=? and periksa_lab.jam=? and periksa_lab.no_rawat=? group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam)");
                try {
                    ps4.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                    ps4.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                    ps4.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                    rs=ps4.executeQuery();
                    if(rs.next()){
                        Sequel.queryu("truncate table temporary_lab");
                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.kategori='PA' and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            ttl=0;
                            while(rs2.next()){
                                item=rs2.getDouble("biaya");//Sequel.cariIsiAngka("select sum(biaya_item) from template_laboratorium where kd_jenis_prw=?",rs2.getString("kd_jenis_prw"));
                                ttl=ttl+item;                    
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("kd_jenis_prw")+"','"+rs2.getString("nm_perawatan")+"','"+item+"','Pemeriksaan','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Biaya Lab");                        
                            }   
                        } catch (Exception e) {
                            System.out.println("Notif ps2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        Sequel.menyimpan("temporary_lab","'0','','Total Biaya Pemeriksaan Lab','"+ttl+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Biaya Lab");
                        Valid.panggilUrl("billing/LaporanBiayaLab.php?norm="+rs.getString("no_rkm_medis")+"&pasien="+rs.getString("nm_pasien").replaceAll(" ","_")
                                +"&tanggal="+rs.getString("tgl_periksa")+"&jam="+rs.getString("jam")+"&pjlab="+rs.getString("nm_dokter").replaceAll(" ","_")
                                +"&petugas="+rs.getString("nama").replaceAll(" ","_")+"&kasir="+Sequel.cariIsi("select nama from pegawai where nik=?",akses.getkode()));
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps4 : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps4!=null){
                        ps4.close();
                    }
                }  
                
            } catch (Exception ex) {
                System.out.println(ex);
            }            
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCetakNotaActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil3();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void MnCetakHasilLab11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab11ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencetak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){   
            try {   
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where periksa_lab.kategori='PA' and "+
                    "periksa_lab.tgl_periksa=? and periksa_lab.jam=? and periksa_lab.no_rawat=? group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam)");
                try {
                    ps4.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                    ps4.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                    ps4.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                    rs=ps4.executeQuery();
                    while(rs.next()){
                        kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat='"+rs.getString("no_rawat")+"' order by tgl_masuk desc limit 1");
                        if(!kamar.equals("")){
                            namakamar=kamar+", "+Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                                    " where kamar.kd_kamar='"+kamar+"' ");            
                            kamar="Kamar";
                        }else if(kamar.equals("")){
                            kamar="Poli";
                            namakamar=Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                                    "where reg_periksa.no_rawat='"+rs.getString("no_rawat")+"'");
                        }
                        Map<String, Object> param = new HashMap<>();
                        param.put("noperiksa",rs.getString("no_rawat"));
                        param.put("norm",rs.getString("no_rkm_medis"));
                        param.put("namapasien",rs.getString("nm_pasien"));
                        param.put("jkel",rs.getString("jk"));
                        param.put("umur",rs.getString("umur"));
                        param.put("pengirim",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")));
                        param.put("tanggal",rs.getString("tgl_periksa"));
                        param.put("penjab",rs.getString("nm_dokter"));
                        param.put("petugas",rs.getString("nama"));
                        param.put("jam",rs.getString("jam"));
                        param.put("alamat",rs.getString("alamat"));
                        param.put("kamar",kamar);
                        param.put("namakamar",namakamar);
                        finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter"));
                        param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nm_dokter")+"\nID "+(finger.equals("")?rs.getString("kd_dokter"):finger)+"\n"+rs.getString("tgl_periksa"));  
                        finger=Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip"));
                        param.put("finger2","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+rs.getString("nama")+"\nID "+(finger.equals("")?rs.getString("nip"):finger)+"\n"+rs.getString("tgl_periksa"));  
                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.kategori='PA' and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                ps3=koneksi.prepareStatement(
                                    "select diagnosa_klinik, makroskopik, mikroskopik, kesimpulan, kesan from detail_periksa_labpa "+
                                    "where no_rawat=? and kd_jenis_prw=? and tgl_periksa=? and jam=? ");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan").replaceAll("'","")+"','"+rs3.getString("diagnosa_klinik").replaceAll("'","")+"','"+rs3.getString("makroskopik").replaceAll("'","")
                                                +"','"+rs3.getString("mikroskopik").replaceAll("'","")+"','"+rs3.getString("kesimpulan").replaceAll("'","")+"','"+rs3.getString("kesan").replaceAll("'","")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notif ps3 : "+e);
                                } finally{
                                    if(rs3!=null){
                                        rs3.close();
                                    }
                                    if(ps3!=null){
                                        ps3.close();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            System.out.println("Notif ps2 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }

                        param.put("namars",akses.getnamars());
                        param.put("alamatrs",akses.getalamatrs());
                        param.put("kotars",akses.getkabupatenrs());
                        param.put("propinsirs",akses.getpropinsirs());
                        param.put("kontakrs",akses.getkontakrs());
                        param.put("emailrs",akses.getemailrs());   
                        param.put("logo",Sequel.cariGambar("select logo from setting")); 
                        pspermintaan=koneksi.prepareStatement(
                                "select noorder,DATE_FORMAT(tgl_permintaan,'%d-%m-%Y') as tgl_permintaan,jam_permintaan from permintaan_lab where "+
                                "no_rawat=? and tgl_hasil=? and jam_hasil=?");
                        try {
                            pspermintaan.setString(1,rs.getString("no_rawat"));
                            pspermintaan.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            pspermintaan.setString(3,rs.getString("jam"));
                            rspermintaan=pspermintaan.executeQuery();
                            if(rspermintaan.next()){
                                param.put("nopermintaan",rspermintaan.getString("noorder"));   
                                param.put("tanggalpermintaan",rspermintaan.getString("tgl_permintaan"));  
                                param.put("jampermintaan",rspermintaan.getString("jam_permintaan"));
                                Valid.MyReportPDF("rptPeriksaLabPermintaanPA.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }else{
                                Valid.MyReportPDF("rptPeriksaLabPA.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rspermintaan!=null){
                                rspermintaan.close();
                            }
                            if(pspermintaan!=null){
                                pspermintaan.close();
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif ps4 : "+e);
                } finally{
                    if(rs!=null){
                        rs.close();
                    }
                    if(ps4!=null){
                        ps4.close();
                    }
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }            
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCetakHasilLab11ActionPerformed

    private void ppBerkasDigitalBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDigitalBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mengubah. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){  
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
            berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
            try {
                berkas.loadURL("http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/"+"berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
            }

            berkas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            berkas.setLocationRelativeTo(internalFrame1);
            berkas.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppBerkasDigitalBtnPrintActionPerformed

    private void ChkAccorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkAccorActionPerformed
        if(tbDokter.getSelectedRow()!= -1){
            isPhoto();
            panggilPhoto();
        }else{
            ChkAccor.setSelected(false);
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        }
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilPhotoActionPerformed
        if(tbDokter.getSelectedRow()!= -1){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Valid.panggilUrl("labpa/login.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()+"&tanggal="+tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString()+"&jam="+tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString()+"&kd_jenis_prw="+tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString());
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_btnAmbilPhotoActionPerformed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
        panggilPhoto();
    }//GEN-LAST:event_BtnRefreshPhotoActionPerformed

    private void btnUbahPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahPasswordActionPerformed
        if(tbDokter.getSelectedRow()!= -1){
            if(Sequel.mengedittf("detail_periksa_labpa","no_rawat=? and kd_jenis_prw=? and tgl_periksa=? and jam=?","diagnosa_klinik=?,makroskopik=?,mikroskopik=?,kesimpulan=?,kesan=?",9,new String[]{
                DiagnosaKlinis.getText(),Makroskopis.getText(),Mikroskopis.getText(),Kesimpulan.getText(),Kesan.getText(),tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),
                tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString()
            })==true){
                JOptionPane.showMessageDialog(null,"Proses update berhasil.....!"); 
            }else{
                JOptionPane.showMessageDialog(null,"Proses update gagal.....!"); 
            }
        }else{
           JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data pasien terlebih dahulu...!!!!"); 
        }
    }//GEN-LAST:event_btnUbahPasswordActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,NoRawat);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        member.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        switch (TabRawat.getSelectedIndex()) {
            case 0:
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                TCari.requestFocus();
            }else if(tabMode.getRowCount()!=0){

                Sequel.queryu("truncate table temporary_lab");
                int row=tabMode.getRowCount();
                for(i=0;i<row;i++){
                    Sequel.menyimpan("temporary_lab","'0','"+
                        tabMode.getValueAt(i,0).toString()+"','"+
                        tabMode.getValueAt(i,1).toString()+"','"+
                        tabMode.getValueAt(i,2).toString()+"','"+
                        tabMode.getValueAt(i,3).toString()+"','"+
                        tabMode.getValueAt(i,4).toString()+"','"+
                        tabMode.getValueAt(i,5).toString()+"','"+
                        tabMode.getValueAt(i,6).toString()+"','"+
                        tabMode.getValueAt(i,7).toString()+"','"+
                        tabMode.getValueAt(i,8).toString()+"','"+
                        tabMode.getValueAt(i,9).toString()+"','"+
                        tabMode.getValueAt(i,10).toString()+"','"+
                        tabMode.getValueAt(i,11).toString()+"','"+
                        tabMode.getValueAt(i,12).toString()+"','"+
                        tabMode.getValueAt(i,13).toString()+"','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab");
                }

                Map<String, Object> param = new HashMap<>();
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());
                param.put("logo",Sequel.cariGambar("select logo from setting"));
                Valid.MyReport("rptDataLabPA.jasper","report","::[ Data Pemeriksaan Laboratorium ]::",param);
            }   break;
            case 1:
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            try {

                File g = new File("file2.css");
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
                    ".isi td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".head td{border-right: 1px solid #777777;font: 8.5px tahoma;height:10px;border-bottom: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi a{text-decoration:none;color:#8b9b95;padding:0 0 0 0px;font-family: Tahoma;font-size: 8.5px;}"+
                    ".isi2 td{font: 8.5px tahoma;height:12px;background: #ffffff;color:#323232;}"+
                    ".isi3 td{border-right: 1px solid #e2e7dd;font: 8.5px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"+
                    ".isi4 td{font: 11px tahoma;height:12px;border-top: 1px solid #e2e7dd;background: #ffffff;color:#323232;}"
                );
                bg.close();

                File f = new File("DetailKunjunganLab.html");
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));
                bw.write(LoadHTML1.getText().replaceAll("<head>","<head><link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                    "<table width='1400px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                    "<tr class='isi2'>"+
                    "<td valign='top' align='center'>"+
                    "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                    akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                    akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                    "<font size='2' face='Tahoma'>DETAIL KUNJUNGAN LAB PERIODE "+Tgl1.getSelectedItem()+" s.d. "+Tgl2.getSelectedItem()+"<br><br></font>"+
                    "</td>"+
                    "</tr>"+
                    "</table>")
            );
            bw.close();
            Desktop.getDesktop().browse(f.toURI());
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }
        this.setCursor(Cursor.getDefaultCursor());
        break;
        default:
        break;
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari,BtnAll);
        }
    }//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            if(tabMode.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
                TCari.requestFocus();
            }else if(Kd2.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik No.Rawat pada table untuk memilih...!!!!");
            }else if(!(Kd2.getText().trim().equals(""))){
                if(Sequel.cariRegistrasi(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCari.requestFocus();
                }else{
                    try{
                        Sequel.AutoComitFalse();
                        sukses=true;
                        status="";
                        ttljmdokter=0;ttljmpetugas=0;ttlkso=0;ttlpendapatan=0;ttlbhp=0;ttljasasarana=0;ttljmperujuk=0;ttlmenejemen=0;
                        ttljmdokter=Sequel.cariIsiAngka("select sum(tarif_tindakan_dokter) from periksa_lab where periksa_lab.kategori='PA' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                            "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                            "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +
                            "' and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString()+"'");
                        ttljmpetugas=Sequel.cariIsiAngka("select sum(tarif_tindakan_petugas) from periksa_lab where periksa_lab.kategori='PA' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                            "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                            "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +
                            "' and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString()+"'");
                        ttlkso=Sequel.cariIsiAngka("select sum(kso) from periksa_lab where periksa_lab.kategori='PA' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                            "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                            "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +
                            "' and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString()+"'");
                        ttlbhp=Sequel.cariIsiAngka("select sum(bhp) from periksa_lab where periksa_lab.kategori='PA' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                            "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                            "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +
                            "' and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString()+"'");
                        ttlpendapatan=Sequel.cariIsiAngka("select sum(biaya) from periksa_lab where periksa_lab.kategori='PA' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                            "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                            "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +
                            "' and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString()+"'");
                        ttljasasarana=Sequel.cariIsiAngka("select sum(bagian_rs) from periksa_lab where periksa_lab.kategori='PA' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                            "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                            "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +
                            "' and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString()+"'");
                        ttljmperujuk=Sequel.cariIsiAngka("select sum(tarif_perujuk) from periksa_lab where periksa_lab.kategori='PA' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                            "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                            "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +
                            "' and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString()+"'");
                        ttlmenejemen=Sequel.cariIsiAngka("select sum(menejemen) from periksa_lab where periksa_lab.kategori='PA' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                            "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                            "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +
                            "' and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString()+"'");

                        status=Sequel.cariIsi("select status from periksa_lab where periksa_lab.kategori='PA' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                            "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                            "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +
                            "' and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString()+"'");

                        if(Sequel.queryutf("delete from periksa_lab where periksa_lab.kategori='PA' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                            "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                            "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +
                            "' and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString()+"'")==true){
                            if(Sequel.queryutf("delete from detail_periksa_labpa where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                    "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                    "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +
                                    "' and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString()+"'")==false){
                                sukses=false;
                            }else{
                                Sequel.queryu("delete from detail_periksa_labpa_gambar where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                    "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                    "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +
                                    "' and kd_jenis_prw='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString()+"'");
                            }
                        }else{
                            sukses=false;
                        }
                        
                        if(sukses==true){
                            if(status.equals("Ranap")){
                                Sequel.queryu("delete from tampjurnal");
                                if(ttlpendapatan>0){
                                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Laborat_Ranap+"','Suspen Piutang Laborat Ranap','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Laborat_Ranap+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Laborat_Ranap+"','Pendapatan Laborat Rawat Inap','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Laborat_Ranap+"'");
                                }
                                if(ttljmdokter>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"','Beban Jasa Medik Dokter Laborat Ranap','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"','Utang Jasa Medik Dokter Laborat Ranap','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"'");
                                }
                                if(ttljmpetugas>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"','Beban Jasa Medik Petugas Laborat Ranap','0','"+ttljmpetugas+"'","kredit=kredit+'"+(ttljmpetugas)+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"','Utang Jasa Medik Petugas Laborat Ranap','"+ttljmpetugas+"','0'","debet=debet+'"+(ttljmpetugas)+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"'");
                                }
                                if(ttlbhp>0){
                                    Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Laborat_Rawat_inap+"','HPP Persediaan Laborat Rawat Inap','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_Persediaan_Laborat_Rawat_inap+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Laborat_Rawat_Inap+"','Persediaan BHP Laborat Rawat Inap','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Laborat_Rawat_Inap+"'");
                                }
                                if(ttlkso>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ranap+"','HPP Persediaan Laborat Rawat Inap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_Kso_Laborat_Ranap+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ranap+"','Persediaan BHP Laborat Rawat Inap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_Kso_Laborat_Ranap+"'");
                                }
                                if(ttljasasarana>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ranap+"','Beban Jasa Sarana Laborat Ranap','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ranap+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ranap+"','Utang Jasa Sarana Laborat Ranap','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ranap+"'");
                                }
                                if(ttljmperujuk>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ranap+"','Beban Jasa Perujuk Laborat Ranap','0','"+ttljmperujuk+"'","kredit=kredit+'"+(ttljmperujuk)+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ranap+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ranap+"','Utang Jasa Perujuk Laborat Ranap','"+ttljmperujuk+"','0'","debet=debet+'"+(ttljmperujuk)+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ranap+"'");
                                }
                                if(ttlmenejemen>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ranap+"','Beban Jasa Menejemen Laborat Ranap','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ranap+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ranap+"','Utang Jasa Menejemen Laborat Ranap','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ranap+"'");
                                }
                                sukses=jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),"U","PEMBATALAN PEMERIKSAAN LABORAT RAWAT INAP PASIEN OLEH "+akses.getkode());
                            }else if(status.equals("Ralan")){
                                Sequel.queryu("delete from tampjurnal");
                                if(ttlpendapatan>0){
                                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Laborat_Ralan+"','Suspen Piutang Laborat Ralan','0','"+ttlpendapatan+"'","kredit=kredit+'"+(ttlpendapatan)+"'","kd_rek='"+Suspen_Piutang_Laborat_Ralan+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Laborat_Ralan+"','Pendapatan Laborat Rawat Jalan','"+ttlpendapatan+"','0'","debet=debet+'"+(ttlpendapatan)+"'","kd_rek='"+Laborat_Ralan+"'");
                                }
                                if(ttljmdokter>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"','Beban Jasa Medik Dokter Laborat Ralan','0','"+ttljmdokter+"'","kredit=kredit+'"+(ttljmdokter)+"'","kd_rek='"+Beban_Jasa_Medik_Dokter_Laborat_Ralan+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"','Utang Jasa Medik Dokter Laborat Ralan','"+ttljmdokter+"','0'","debet=debet+'"+(ttljmdokter)+"'","kd_rek='"+Utang_Jasa_Medik_Dokter_Laborat_Ralan+"'");
                                }
                                if(ttljmpetugas>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"','Beban Jasa Medik Petugas Laborat Ralan','0','"+ttljmpetugas+"'","kredit=kredit+'"+(ttljmpetugas)+"'","kd_rek='"+Beban_Jasa_Medik_Petugas_Laborat_Ralan+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"','Utang Jasa Medik Petugas Laborat Ralan','"+ttljmpetugas+"','0'","debet=debet+'"+(ttljmpetugas)+"'","kd_rek='"+Utang_Jasa_Medik_Petugas_Laborat_Ralan+"'");
                                }
                                if(ttlbhp>0){
                                    Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Laborat_Rawat_Jalan+"','HPP Persediaan Laborat Rawat Jalan','0','"+ttlbhp+"'","kredit=kredit+'"+(ttlbhp)+"'","kd_rek='"+HPP_Persediaan_Laborat_Rawat_inap+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Laborat_Rawat_Jalan+"','Persediaan BHP Laborat Rawat Jalan','"+ttlbhp+"','0'","debet=debet+'"+(ttlbhp)+"'","kd_rek='"+Persediaan_BHP_Laborat_Rawat_Inap+"'");
                                }
                                if(ttlkso>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ralan+"','HPP Persediaan Laborat Rawat Inap','0','"+ttlkso+"'","kredit=kredit+'"+(ttlkso)+"'","kd_rek='"+Beban_Kso_Laborat_Ralan+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ralan+"','Persediaan BHP Laborat Rawat Inap','"+ttlkso+"','0'","debet=debet+'"+(ttlkso)+"'","kd_rek='"+Utang_Kso_Laborat_Ralan+"'");
                                }
                                if(ttljasasarana>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Sarana_Laborat_Ralan+"','Beban Jasa Sarana Laborat Ralan','0','"+ttljasasarana+"'","kredit=kredit+'"+(ttljasasarana)+"'","kd_rek='"+Beban_Jasa_Sarana_Laborat_Ralan+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Sarana_Laborat_Ralan+"','Utang Jasa Sarana Laborat Ralan','"+ttljasasarana+"','0'","debet=debet+'"+(ttljasasarana)+"'","kd_rek='"+Utang_Jasa_Sarana_Laborat_Ralan+"'");
                                }
                                if(ttljmperujuk>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Perujuk_Laborat_Ralan+"','Beban Jasa Perujuk Laborat Ralan','0','"+ttljmperujuk+"'","kredit=kredit+'"+(ttljmperujuk)+"'","kd_rek='"+Beban_Jasa_Perujuk_Laborat_Ralan+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Perujuk_Laborat_Ralan+"','Utang Jasa Perujuk Laborat Ralan','"+ttljmperujuk+"','0'","debet=debet+'"+(ttljmperujuk)+"'","kd_rek='"+Utang_Jasa_Perujuk_Laborat_Ralan+"'");
                                }
                                if(ttlmenejemen>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Menejemen_Laborat_Ralan+"','Beban Jasa Menejemen Laborat Ralan','0','"+ttlmenejemen+"'","kredit=kredit+'"+(ttlmenejemen)+"'","kd_rek='"+Beban_Jasa_Menejemen_Laborat_Ralan+"'");
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Menejemen_Laborat_Ralan+"','Utang Jasa Menejemen Laborat Ralan','"+ttlmenejemen+"','0'","debet=debet+'"+(ttlmenejemen)+"'","kd_rek='"+Utang_Jasa_Menejemen_Laborat_Ralan+"'");
                                }
                                sukses=jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),"U","PEMBATALAN PEMERIKSAAN LABORAT RAWAT JALAN PASIEN OLEH "+akses.getkode());
                            }
                        }

                        if(sukses==true){
                            Sequel.Commit();
                            tampil();
                        }else{
                            JOptionPane.showMessageDialog(null,"Terjadi kesalahan saat pemrosesan data, transaksi dibatalkan.\nPeriksa kembali data sebelum melanjutkan menyimpan..!!");
                            Sequel.RollBack();
                        }
                        Sequel.AutoComitTrue();
                    }catch(Exception e){
                        System.out.println("Notifikasi : "+e);
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
                    }
                }
            }
        }else if(TabRawat.getSelectedIndex()==1){
            JOptionPane.showMessageDialog(null,"Hanya bisa dilakukan hapus di Data Pemeriksaan..!!!");
            TabRawat.setSelectedIndex(0);
            TCari.requestFocus();
        }
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        NoRawat.setText("");
        kdmem.setText("");
        nmmem.setText("");
        kdptg.setText("");
        nmptg.setText("");
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnCariActionPerformed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void Tinggi150ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi150ActionPerformed
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable4());
        tbDokter.setRowHeight(150);
    }//GEN-LAST:event_Tinggi150ActionPerformed

    private void Tinggi200ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi200ActionPerformed
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable4());
        tbDokter.setRowHeight(200);
    }//GEN-LAST:event_Tinggi200ActionPerformed

    private void Tinggi250ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi250ActionPerformed
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable4());
        tbDokter.setRowHeight(250);
    }//GEN-LAST:event_Tinggi250ActionPerformed

    private void Tinggi300ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi300ActionPerformed
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable4());
        tbDokter.setRowHeight(300);
    }//GEN-LAST:event_Tinggi300ActionPerformed

    private void Tinggi350ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi350ActionPerformed
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable4());
        tbDokter.setRowHeight(350);
    }//GEN-LAST:event_Tinggi350ActionPerformed

    private void Tinggi400ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi400ActionPerformed
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable4());
        tbDokter.setRowHeight(400);
    }//GEN-LAST:event_Tinggi400ActionPerformed

    private void Tinggi32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi32ActionPerformed
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable4());
        tbDokter.setRowHeight(32);
    }//GEN-LAST:event_Tinggi32ActionPerformed

    private void DiagnosaKlinisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DiagnosaKlinisKeyPressed
        
    }//GEN-LAST:event_DiagnosaKlinisKeyPressed

    private void MakroskopisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MakroskopisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MakroskopisKeyPressed

    private void MikroskopisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MikroskopisKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_MikroskopisKeyPressed

    private void KesimpulanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesimpulanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KesimpulanKeyPressed

    private void KesanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_KesanKeyPressed

    private void Tinggi22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tinggi22ActionPerformed
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        tbDokter.setRowHeight(22);
    }//GEN-LAST:event_Tinggi22ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPeriksaLabPA dialog = new DlgCariPeriksaLabPA(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnRefreshPhoto;
    private widget.CekBox ChkAccor;
    private widget.TextArea DiagnosaKlinis;
    private widget.PanelBiasa FormPass;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPhoto;
    private widget.PanelBiasa FormPhotoPass;
    private widget.TextBox Kd2;
    private widget.TextArea Kesan;
    private widget.TextArea Kesimpulan;
    private widget.Label LTotal;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML1;
    private widget.TextArea Makroskopis;
    private widget.TextArea Mikroskopis;
    private javax.swing.JMenuItem MnCetakHasilLab;
    private javax.swing.JMenuItem MnCetakHasilLab11;
    private javax.swing.JMenuItem MnCetakNota;
    private widget.TextBox NoRawat;
    private widget.PanelBiasa PanelAccor;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.ScrollPane ScrollMenu;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private javax.swing.JMenuItem Tinggi150;
    private javax.swing.JMenuItem Tinggi200;
    private javax.swing.JMenuItem Tinggi22;
    private javax.swing.JMenuItem Tinggi250;
    private javax.swing.JMenuItem Tinggi300;
    private javax.swing.JMenuItem Tinggi32;
    private javax.swing.JMenuItem Tinggi350;
    private javax.swing.JMenuItem Tinggi400;
    private widget.Button btnAmbilPhoto;
    private widget.Button btnPasien;
    private widget.Button btnPetugas;
    private widget.Button btnUbahPassword;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdmem;
    private widget.TextBox kdptg;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.TextBox nmmem;
    private widget.TextBox nmptg;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBerkasDigital;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane6;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        try {
            Valid.tabelKosong(tabMode);  
            if(NoRawat.getText().equals("")&&kdmem.getText().equals("")&&kdptg.getText().equals("")&&TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_lab.tgl_periksa,periksa_lab.jam,penjab.png_jawab,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,dokter.nm_dokter,jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya "+
                    "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on periksa_lab.nip=petugas.nip "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                    "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter where periksa_lab.kategori='PA' and "+
                    "periksa_lab.tgl_periksa between ? and ? order by periksa_lab.tgl_periksa desc,periksa_lab.jam desc");
            }else{
                ps=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_lab.tgl_periksa,periksa_lab.jam,penjab.png_jawab,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,dokter.nm_dokter,jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya "+
                    "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on periksa_lab.nip=petugas.nip "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "inner join jns_perawatan_lab on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw "+
                    "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter where periksa_lab.kategori='PA' and "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? "+
                    "and (pasien.nm_pasien like ? or petugas.nama like ? or reg_periksa.no_rkm_medis like ? or penjab.png_jawab like ?) "+
                    "order by periksa_lab.tgl_periksa desc,periksa_lab.jam desc");
            }
                
            try {
                if(NoRawat.getText().equals("")&&kdmem.getText().equals("")&&kdptg.getText().equals("")&&TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(3,"%"+NoRawat.getText()+"%");
                    ps.setString(4,"%"+kdmem.getText()+"%");
                    ps.setString(5,"%"+kdptg.getText()+"%");
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                ttl=0;
                while(rs.next()){
                    kamar=Sequel.cariIsi("select ifnull(kd_kamar,'') from kamar_inap where no_rawat='"+rs.getString("no_rawat")+"' order by tgl_masuk desc limit 1");
                    if(!kamar.equals("")){
                        namakamar=kamar+", "+Sequel.cariIsi("select nm_bangsal from bangsal inner join kamar on bangsal.kd_bangsal=kamar.kd_bangsal "+
                                    " where kamar.kd_kamar='"+kamar+"' ");            
                        kamar="Kamar";
                    }else if(kamar.equals("")){
                        kamar="Poli";
                        namakamar=Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                                    "where reg_periksa.no_rawat='"+rs.getString("no_rawat")+"'");
                    }
                    diagnosa_klinik="";makroskopik="";mikroskopik="";kesimpulan="";kesan="";
                    ps2=koneksi.prepareStatement("select diagnosa_klinik, makroskopik, mikroskopik, kesimpulan, kesan from detail_periksa_labpa where no_rawat=? and kd_jenis_prw=? and tgl_periksa=? and jam=?");
                    try {
                        ps2.setString(1,rs.getString("no_rawat"));
                        ps2.setString(2,rs.getString("kd_jenis_prw"));
                        ps2.setString(3,rs.getString("tgl_periksa"));
                        ps2.setString(4,rs.getString("jam"));
                        rs2=ps2.executeQuery();
                        if(rs2.next()){
                            diagnosa_klinik=rs2.getString("diagnosa_klinik");
                            makroskopik=rs2.getString("makroskopik");
                            mikroskopik=rs2.getString("mikroskopik");
                            kesimpulan=rs2.getString("kesimpulan");
                            kesan=rs2.getString("kesan");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif 2 : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }
                    tabMode.addRow(new Object[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+kamar+" : "+namakamar+")",rs.getString("nama"),
                        rs.getString("tgl_periksa"),rs.getString("jam"),Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")),
                        rs.getString("nm_dokter"),rs.getString("nm_perawatan"),rs.getString("biaya"),diagnosa_klinik,makroskopik,mikroskopik,kesimpulan,kesan,
                        rs.getString("kd_jenis_prw"),rs.getString("png_jawab")
                    });
                    ttl=ttl+rs.getDouble("biaya");
                }
            } catch (Exception e) {
                System.out.println("Notif ps : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            
            if(ttl>0){
                  LTotal.setText(Valid.SetAngka(ttl));
            }  
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
    }
    
    public void tampil3(){        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            htmlContent = new StringBuilder();
            htmlContent.append(
                "<tr class='head'>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='1%'>No.</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='4%'>No.RM</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='12%'>Nama Pasien</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='2%'>Umur</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='1%'>J.K.</td>"+
                    "<td valign='top' bgcolor='#FFFAF8' align='center' width='80%'>Kunjungan</td>"+
                "</tr>"); 
            if(NoRawat.getText().equals("")&&kdmem.getText().equals("")&&kdptg.getText().equals("")&&TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,penjab.png_jawab,"+
                    "pasien.jk from pasien inner join reg_periksa on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                    "inner join periksa_lab on reg_periksa.no_rawat=periksa_lab.no_rawat "+
                    "inner join dokter on periksa_lab.dokter_perujuk=dokter.kd_dokter "+
                    "inner join jns_perawatan_lab on jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                    "inner join petugas on periksa_lab.nip=petugas.nip "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where periksa_lab.kategori='PA' and periksa_lab.tgl_periksa between ? and ? group by pasien.no_rkm_medis order by tgl_registrasi desc");
            }else{
                ps=koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,penjab.png_jawab,"+
                    "pasien.jk from pasien inner join reg_periksa on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                    "inner join periksa_lab on reg_periksa.no_rawat=periksa_lab.no_rawat "+
                    "inner join dokter on periksa_lab.dokter_perujuk=dokter.kd_dokter "+
                    "inner join jns_perawatan_lab on jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                    "inner join petugas on periksa_lab.nip=petugas.nip "+
                    "inner join penjab on reg_periksa.kd_pj=penjab.kd_pj "+
                    "where periksa_lab.kategori='PA' and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and periksa_lab.tgl_periksa between ? and ? and "+
                    "(pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or jns_perawatan_lab.nm_perawatan like ? or periksa_lab.no_rawat like ? or petugas.nama like ? or dokter.nm_dokter like ? or penjab.png_jawab like ?) group by pasien.no_rkm_medis order by tgl_registrasi desc");
            }
                
            try {
                if(NoRawat.getText().equals("")&&kdmem.getText().equals("")&&kdptg.getText().equals("")&&TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                }else{
                    ps.setString(1,"%"+NoRawat.getText()+"%");
                    ps.setString(2,"%"+kdmem.getText()+"%");
                    ps.setString(3,"%"+kdptg.getText()+"%");
                    ps.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(6,"%"+TCari.getText().trim()+"%");
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                i=1;
                jmlkunjungan=0;
                jmlpemeriksaan=0;
                jmlsubpemeriksaan=0;
                while(rs.next()){
                    htmlContent.append(
                        "<tr class='isi'>"+
                            "<td valign='top' align='center'>"+i+"</td>"+
                            "<td valign='top' align='center'>"+rs.getString("no_rkm_medis")+"</td>"+
                            "<td valign='top'>"+rs.getString("nm_pasien")+" ("+rs.getString("png_jawab")+")"+"</td>"+
                            "<td valign='top'>"+rs.getString("umurdaftar")+" "+rs.getString("sttsumur")+"</td>"+
                            "<td valign='top'>"+rs.getString("jk")+"</td>"+
                            "<td valign='top'>"+
                                "<table width='100%' border='0' align='center' cellpadding='2px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='8%'>No.Rawat</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='8%'>Tanggal</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='84%'>Pemeriksaan</td>"+
                                    "</tr>");
                    
                    ps2=koneksi.prepareStatement(
                        "select periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam,dokter.nm_dokter from "+
                        "pasien inner join reg_periksa on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                        "inner join periksa_lab on reg_periksa.no_rawat=periksa_lab.no_rawat "+
                        "inner join dokter on periksa_lab.dokter_perujuk=dokter.kd_dokter "+
                        "inner join jns_perawatan_lab on jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                        "inner join petugas on periksa_lab.nip=petugas.nip "+
                        "where periksa_lab.kategori='PA' and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.tgl_periksa between ? and ? and "+
                        "(pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or jns_perawatan_lab.nm_perawatan like ? or periksa_lab.no_rawat like ? or petugas.nama like ? or dokter.nm_dokter like ? )"+
                        "group by periksa_lab.tgl_periksa,periksa_lab.jam order by tgl_registrasi");
                        
                    try {
                        ps2.setString(1,"%"+NoRawat.getText()+"%");
                        ps2.setString(2,"%"+kdmem.getText()+"%");
                        ps2.setString(3,"%"+kdptg.getText()+"%");
                        ps2.setString(4,rs.getString("no_rkm_medis"));
                        ps2.setString(5,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                        ps2.setString(6,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        ps2.setString(7,"%"+TCari.getText().trim()+"%");
                        ps2.setString(8,"%"+TCari.getText().trim()+"%");
                        ps2.setString(9,"%"+TCari.getText().trim()+"%");
                        ps2.setString(10,"%"+TCari.getText().trim()+"%");   
                        ps2.setString(11,"%"+TCari.getText().trim()+"%"); 
                        ps2.setString(12,"%"+TCari.getText().trim()+"%"); 
                               
                        rs2=ps2.executeQuery();
                        while(rs2.next()){
                            htmlContent.append(
                                "<tr class='isi'>"+
                                    "<td valign='top' align='center'>"+rs2.getString("no_rawat")+"</td>"+
                                    "<td valign='top'>"+rs2.getString("tgl_periksa")+" "+rs2.getString("jam")+"</td>"+
                                    "<td valign='top'>"+
                                        "<table width='100%' border='0' align='center' cellpadding='2px' cellspacing='0' class='tbl_form'>"+
                                            "<tr class='isi'>"+
                                                "<td valign='top' bgcolor='#fdfff9' align='center' width='16%'>P.J.Lab</td>"+
                                                "<td valign='top' bgcolor='#fdfff9' align='center' width='16%'>Perujuk</td>"+
                                                "<td valign='top' bgcolor='#fdfff9' align='center' width='16%'>Petugas Lab</td>"+
                                                "<td valign='top' bgcolor='#fdfff9' align='center' width='62%'>Detail Pemeriksaan</td>"+
                                            "</tr>");
                            
                            ps3=koneksi.prepareStatement(
                                "select periksa_lab.kd_dokter,dokter.nm_dokter,petugas.nama as nm_petugas,jns_perawatan_lab.nm_perawatan,periksa_lab.kd_jenis_prw from "+
                                "pasien inner join reg_periksa on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                                "inner join periksa_lab on reg_periksa.no_rawat=periksa_lab.no_rawat "+
                                "inner join dokter on periksa_lab.dokter_perujuk=dokter.kd_dokter "+
                                "inner join jns_perawatan_lab on jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                                "inner join petugas on periksa_lab.nip=petugas.nip "+
                                "where periksa_lab.kategori='PA' and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? and periksa_lab.jam=? and periksa_lab.tgl_periksa between ? and ? and "+
                                "(pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or jns_perawatan_lab.nm_perawatan like ? or periksa_lab.no_rawat like ? or petugas.nama like ? or dokter.nm_dokter like ?)"+
                                "order by tgl_registrasi");
                                
                            try {
                                ps3.setString(1,"%"+NoRawat.getText()+"%");
                                ps3.setString(2,"%"+kdmem.getText()+"%");
                                ps3.setString(3,"%"+kdptg.getText()+"%");
                                ps3.setString(4,rs.getString("no_rkm_medis"));
                                ps3.setString(5,rs2.getString("no_rawat"));
                                ps3.setString(6,rs2.getString("tgl_periksa"));
                                ps3.setString(7,rs2.getString("jam"));
                                ps3.setString(8,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                ps3.setString(9,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                ps3.setString(10,"%"+TCari.getText().trim()+"%");
                                ps3.setString(11,"%"+TCari.getText().trim()+"%");
                                ps3.setString(12,"%"+TCari.getText().trim()+"%");
                                ps3.setString(13,"%"+TCari.getText().trim()+"%");
                                ps3.setString(14,"%"+TCari.getText().trim()+"%");
                                ps3.setString(15,"%"+TCari.getText().trim()+"%");
                                    
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    htmlContent.append(
                                        "<tr class='isi'>"+
                                            "<td valign='top'>"+Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs3.getString("kd_dokter"))+"</td>"+
                                            "<td valign='top'>"+rs3.getString("nm_dokter")+"</td>"+
                                            "<td valign='top'>"+rs3.getString("nm_petugas")+"</td>"+
                                            "<td valign='top'>"+
                                                "<table width='100%' border='0' align='center' cellpadding='2px' cellspacing='0' class='tbl_form'>"+
                                                    "<tr class='isi'>"+
                                                        "<td valign='top' bgcolor='#fdfff9' align='left' colspan='5'>"+rs3.getString("nm_perawatan")+"</td>"+
                                                    "</tr>");
                                    ps5=koneksi.prepareStatement(
                                            "select diagnosa_klinik, makroskopik, mikroskopik, kesimpulan, kesan from detail_periksa_labpa "+
                                            "where no_rawat=? and kd_jenis_prw=? and tgl_periksa=? and jam=?");
                                    try {
                                        ps5.setString(1,rs2.getString("no_rawat"));
                                        ps5.setString(2,rs3.getString("kd_jenis_prw"));
                                        ps5.setString(3,rs2.getString("tgl_periksa"));
                                        ps5.setString(4,rs2.getString("jam"));
                                        rs5=ps5.executeQuery();
                                        if(rs5.next()){
                                            htmlContent.append(
                                                "<tr class='isi'>"+
                                                    "<td valign='top' bgcolor='#fdfff9' align='center' width='10%'>Diagnosa</td>"+
                                                    "<td valign='top' bgcolor='#fdfff9' align='center' width='35%'>Makroskopik</td>"+
                                                    "<td valign='top' bgcolor='#fdfff9' align='center' width='35%'>Mikroskopik</td>"+
                                                    "<td valign='top' bgcolor='#fdfff9' align='center' width='10%'>Kesimpulan</td>"+
                                                    "<td valign='top' bgcolor='#fdfff9' align='center' width='10%'>Kesan</td>"+
                                                "</tr>");
                                        }
                                        rs5.beforeFirst();
                                        while(rs5.next()){
                                            htmlContent.append(
                                                "<tr class='isi'>"+
                                                    "<td valign='top'>"+rs5.getString("diagnosa_klinik")+"</td>"+
                                                    "<td valign='top'>"+rs5.getString("makroskopik")+"</td>"+
                                                    "<td valign='top'>"+rs5.getString("mikroskopik")+"</td>"+
                                                    "<td valign='top'>"+rs5.getString("kesimpulan")+"</td>"+
                                                    "<td valign='top'>"+rs5.getString("kesan")+"</td>"+
                                                "</tr>");
                                            jmlsubpemeriksaan++;
                                        }
                                    } catch (Exception e) {
                                        System.out.println("Notif : "+e);
                                    } finally{
                                        if(rs5!=null){
                                            rs5.close();
                                        }
                                        if(ps5!=null){
                                            ps5.close();
                                        }
                                    }                                 
                                    htmlContent.append(
                                                "</table>"+
                                            "</td>"+
                                        "</tr>");
                                    jmlpemeriksaan++;                                    
                                }
                            } catch (Exception e) {
                                System.out.println("Notif : "+e);
                            } finally{
                                if(rs3!=null){
                                    rs3.close();
                                }
                                if(ps3!=null){
                                    ps3.close();
                                }
                            }
                            htmlContent.append(
                                        "</table>"+
                                    "</td>"+
                                "</tr>");
                            jmlkunjungan++;
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs2!=null){
                            rs2.close();
                        }
                        if(ps2!=null){
                            ps2.close();
                        }
                    }                        
                    htmlContent.append(
                                "</table>"+
                            "</td>"+
                        "</tr>");
                    i++;
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
            if((i-1)>0){
                datapasien=
                      "<table width='1400px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                          "<tr class='isi'>"+
                            "<td valign='top' align='center' width='25%'>Jumlah Pasien : "+(i-1)+"</td>"+
                            "<td valign='top' align='center' width='25%'>Jumlah Kunjungan : "+(jmlkunjungan)+"</td>"+
                            "<td valign='top' align='center' width='25%'>Jumlah Pemeriksaan : "+(jmlpemeriksaan)+"</td>"+
                            "<td valign='top' align='center' width='25%'>Jumlah Sub Pemeriksaan : "+(jmlsubpemeriksaan)+"</td>"+
                          "</tr>"+
                      "</table>";
            }
            LoadHTML1.setText(
                    "<html>"+
                      "<table width='1400px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+datapasien+
                    "</html>");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        } 
        this.setCursor(Cursor.getDefaultCursor());
    }
    
    public void SetNoRw(String norw){
        NoRawat.setText(norw);
        tampil();
        Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+norw+"'", Tgl1);
    }
    
    private void getData() {
        Kd2.setText("");
        if(tbDokter.getSelectedRow()!= -1){
            Kd2.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
            DiagnosaKlinis.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),9).toString());
            Makroskopis.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),10).toString());
            Mikroskopis.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),11).toString());
            Kesimpulan.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),12).toString());
            Kesan.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),13).toString());
        }
    }
    
    public void isCek(){
        MnCetakHasilLab.setEnabled(akses.getpemeriksaan_lab_pa());
        MnCetakNota.setEnabled(akses.getpemeriksaan_lab_pa());
        BtnHapus.setEnabled(akses.getpemeriksaan_lab_pa());
        BtnPrint.setEnabled(akses.getpemeriksaan_lab_pa());
        ppBerkasDigital.setEnabled(akses.getberkas_digital_perawatan());     
    }
    
    public void setPasien(String pasien){
        NoRawat.setText(pasien);
    }
 
    private void isPhoto(){
        if(ChkAccor.isSelected()==true){
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(445,HEIGHT));
            ScrollMenu.setVisible(true);  
            ChkAccor.setVisible(true);
        }else if(ChkAccor.isSelected()==false){    
            ChkAccor.setVisible(false);
            PanelAccor.setPreferredSize(new Dimension(15,HEIGHT));
            ScrollMenu.setVisible(false);
            ChkAccor.setVisible(true);
        }
    }
    
    private void panggilPhoto() {
        if(ScrollMenu.isVisible()==true){
            if(tbDokter.getSelectedRow()!= -1){
                try {
                    ps=koneksi.prepareStatement("select photo from detail_periksa_labpa_gambar where no_rawat=? and tgl_periksa=? and jam=? and kd_jenis_prw=?");
                    try {
                        ps.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        ps.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                        ps.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                        ps.setString(4,tbDokter.getValueAt(tbDokter.getSelectedRow(),14).toString());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            if(rs.getString("photo").equals("")||rs.getString("photo").equals("-")){
                                LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                            }else{
                                LoadHTML.setText("<html><body><center><img src='http://"+koneksiDB.HOSTHYBRIDWEB()+":"+koneksiDB.PORTWEB()+"/"+koneksiDB.HYBRIDWEB()+"/labpa/"+rs.getString("photo")+"' alt='photo' width='375' height='335'/></center></body></html>");
                            }  
                        }else{
                            LoadHTML.setText("<html><body><center><br><br><font face='tahoma' size='2' color='#434343'>Kosong</font></center></body></html>");
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                } 
            }
        }
    }
 
}
