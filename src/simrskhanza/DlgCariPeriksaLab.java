package simrskhanza;
import kepegawaian.DlgCariPetugas;
import keuangan.Jurnal;
import fungsi.WarnaTable;
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

public class DlgCariPeriksaLab extends javax.swing.JDialog {
    private final DefaultTableModel tabMode,tabMode2;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private DlgPasien member=new DlgPasien(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private int i,jmlkunjungan=0,jmlpemeriksaan=0,jmlsubpemeriksaan=0;
    private PreparedStatement ps,ps2,ps3,ps4,psrekening,ps5,pspermintaan;
    private ResultSet rs,rs2,rs3,rs5,rsrekening,rspermintaan;
    private String kamar,namakamar,datapasien="";
    private double ttl=0,item=0;
    private StringBuilder htmlContent;
    private double ttljmdokter=0,ttljmpetugas=0,ttlkso=0,ttlpendapatan=0,ttlbhp=0;
    private String diagnosa="",saran="",kesan="",Suspen_Piutang_Laborat_Ranap="", Laborat_Ranap="", Beban_Jasa_Medik_Dokter_Laborat_Ranap="", 
            Utang_Jasa_Medik_Dokter_Laborat_Ranap="", Beban_Jasa_Medik_Petugas_Laborat_Ranap="", 
            Utang_Jasa_Medik_Petugas_Laborat_Ranap="", Beban_Kso_Laborat_Ranap="", Utang_Kso_Laborat_Ranap="", 
            HPP_Persediaan_Laborat_Rawat_inap="", Persediaan_BHP_Laborat_Rawat_Inap="",status="";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariPeriksaLab(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"No.Rawat","Pasien","Petugas","Tgl.Periksa","Jam Periksa","Dokter Perujuk","Penanggung Jawab"};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 7; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(110);
            }else if(i==1){
                column.setPreferredWidth(220);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(130);
            }else if(i==4){
                column.setPreferredWidth(100);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(200);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
            tabMode2=new DefaultTableModel(null,new Object[]{
                "No.Rawat","Pasien","Tgl.Periksa","Jam","Pemeriksaan",
                "Item Pemeriksaan","Hasil","Satuan","Nilai Rujukan","Keterangan",
                "Ruang","Petugas","Dokter Perujuk","Penanggung Jawab"
            }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter2.setModel(tabMode2);

        tbDokter2.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 14; i++) {
            TableColumn column = tbDokter2.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(210);
            }else if(i==2){
                column.setPreferredWidth(65);
            }else if(i==3){
                column.setPreferredWidth(55);
            }else if(i==4){
                column.setPreferredWidth(155);
            }else if(i==5){
                column.setPreferredWidth(165);
            }else if(i==6){
                column.setPreferredWidth(70);
            }else if(i==7){
                column.setPreferredWidth(50);
            }else if(i==8){
                column.setPreferredWidth(110);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(130);
            }else if(i==11){
                column.setPreferredWidth(130);
            }else if(i==12){
                column.setPreferredWidth(160);
            }else if(i==13){
                column.setPreferredWidth(160);
            }
        }
        tbDokter2.setDefaultRenderer(Object.class, new WarnaTable());
        NoRawat.setDocument(new batasInput((byte)17).getKata(NoRawat));
        kdmem.setDocument(new batasInput((byte)8).getKata(kdmem));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
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
        MnCetakLab = new javax.swing.JMenu();
        MnCetakHasilLab = new javax.swing.JMenuItem();
        MnCetakHasilLab1 = new javax.swing.JMenuItem();
        MnCetakHasilLab2 = new javax.swing.JMenuItem();
        MnCetakHasilLab3 = new javax.swing.JMenuItem();
        MnCetakHasilLab4 = new javax.swing.JMenuItem();
        MnCetakHasilLab5 = new javax.swing.JMenuItem();
        MnCetakHasilLab6 = new javax.swing.JMenuItem();
        MnCetakHasilLab7 = new javax.swing.JMenuItem();
        MnCetakHasilLab8 = new javax.swing.JMenuItem();
        MnCetakHasilLab9 = new javax.swing.JMenuItem();
        MnCetakHasilLab10 = new javax.swing.JMenuItem();
        MnPDFLab = new javax.swing.JMenu();
        MnCetakHasilLab11 = new javax.swing.JMenuItem();
        MnCetakHasilLab12 = new javax.swing.JMenuItem();
        MnCetakHasilLab13 = new javax.swing.JMenuItem();
        MnCetakHasilLab14 = new javax.swing.JMenuItem();
        MnCetakHasilLab15 = new javax.swing.JMenuItem();
        MnCetakHasilLab16 = new javax.swing.JMenuItem();
        MnCetakHasilLab17 = new javax.swing.JMenuItem();
        MnCetakHasilLab18 = new javax.swing.JMenuItem();
        MnCetakHasilLab19 = new javax.swing.JMenuItem();
        MnCetakHasilLab20 = new javax.swing.JMenuItem();
        MnCetakHasilLab21 = new javax.swing.JMenuItem();
        MnCetakNota = new javax.swing.JMenuItem();
        MnUbah = new javax.swing.JMenuItem();
        MnUbah1 = new javax.swing.JMenuItem();
        MnSaranKesan = new javax.swing.JMenuItem();
        WindowSaran = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        panelGlass6 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnCloseIn5 = new widget.Button();
        jPanel3 = new javax.swing.JPanel();
        Scroll4 = new widget.ScrollPane();
        Kesan = new widget.TextArea();
        Scroll3 = new widget.ScrollPane();
        Saran = new widget.TextArea();
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
        label9 = new widget.Label();
        BtnHapus = new widget.Button();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        scrollPane2 = new widget.ScrollPane();
        tbDokter2 = new widget.Table();
        Scroll = new widget.ScrollPane();
        LoadHTML1 = new widget.editorpane();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakLab.setBackground(new java.awt.Color(255, 253, 247));
        MnCetakLab.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakLab.setText("Cetak Hasil Lab");
        MnCetakLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakLab.setName("MnCetakLab"); // NOI18N
        MnCetakLab.setPreferredSize(new java.awt.Dimension(160, 26));

        MnCetakHasilLab.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab.setText("Model 1");
        MnCetakHasilLab.setName("MnCetakHasilLab"); // NOI18N
        MnCetakHasilLab.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLabActionPerformed(evt);
            }
        });
        MnCetakLab.add(MnCetakHasilLab);

        MnCetakHasilLab1.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab1.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab1.setText("Model 2");
        MnCetakHasilLab1.setName("MnCetakHasilLab1"); // NOI18N
        MnCetakHasilLab1.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab1ActionPerformed(evt);
            }
        });
        MnCetakLab.add(MnCetakHasilLab1);

        MnCetakHasilLab2.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab2.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab2.setText("Model 3");
        MnCetakHasilLab2.setName("MnCetakHasilLab2"); // NOI18N
        MnCetakHasilLab2.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab2ActionPerformed(evt);
            }
        });
        MnCetakLab.add(MnCetakHasilLab2);

        MnCetakHasilLab3.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab3.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab3.setText("Model 4");
        MnCetakHasilLab3.setName("MnCetakHasilLab3"); // NOI18N
        MnCetakHasilLab3.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab3ActionPerformed(evt);
            }
        });
        MnCetakLab.add(MnCetakHasilLab3);

        MnCetakHasilLab4.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab4.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab4.setText("Model 5");
        MnCetakHasilLab4.setName("MnCetakHasilLab4"); // NOI18N
        MnCetakHasilLab4.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab4ActionPerformed(evt);
            }
        });
        MnCetakLab.add(MnCetakHasilLab4);

        MnCetakHasilLab5.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab5.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab5.setText("Model 6");
        MnCetakHasilLab5.setName("MnCetakHasilLab5"); // NOI18N
        MnCetakHasilLab5.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab5ActionPerformed(evt);
            }
        });
        MnCetakLab.add(MnCetakHasilLab5);

        MnCetakHasilLab6.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab6.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab6.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab6.setText("Model 7");
        MnCetakHasilLab6.setName("MnCetakHasilLab6"); // NOI18N
        MnCetakHasilLab6.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab6ActionPerformed(evt);
            }
        });
        MnCetakLab.add(MnCetakHasilLab6);

        MnCetakHasilLab7.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab7.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab7.setText("Model 8");
        MnCetakHasilLab7.setName("MnCetakHasilLab7"); // NOI18N
        MnCetakHasilLab7.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab7ActionPerformed(evt);
            }
        });
        MnCetakLab.add(MnCetakHasilLab7);

        MnCetakHasilLab8.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab8.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab8.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab8.setText("Model 9");
        MnCetakHasilLab8.setName("MnCetakHasilLab8"); // NOI18N
        MnCetakHasilLab8.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab8ActionPerformed(evt);
            }
        });
        MnCetakLab.add(MnCetakHasilLab8);

        MnCetakHasilLab9.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab9.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab9.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab9.setText("Model 10");
        MnCetakHasilLab9.setName("MnCetakHasilLab9"); // NOI18N
        MnCetakHasilLab9.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab9ActionPerformed(evt);
            }
        });
        MnCetakLab.add(MnCetakHasilLab9);

        MnCetakHasilLab10.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab10.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab10.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab10.setText("Model 11");
        MnCetakHasilLab10.setName("MnCetakHasilLab10"); // NOI18N
        MnCetakHasilLab10.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab10ActionPerformed(evt);
            }
        });
        MnCetakLab.add(MnCetakHasilLab10);

        jPopupMenu1.add(MnCetakLab);

        MnPDFLab.setBackground(new java.awt.Color(255, 253, 247));
        MnPDFLab.setForeground(new java.awt.Color(50, 50, 50));
        MnPDFLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnPDFLab.setText("PDF Hasil Lab");
        MnPDFLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPDFLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPDFLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPDFLab.setName("MnPDFLab"); // NOI18N
        MnPDFLab.setPreferredSize(new java.awt.Dimension(160, 26));

        MnCetakHasilLab11.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab11.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab11.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab11.setText("Model 1");
        MnCetakHasilLab11.setName("MnCetakHasilLab11"); // NOI18N
        MnCetakHasilLab11.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab11ActionPerformed(evt);
            }
        });
        MnPDFLab.add(MnCetakHasilLab11);

        MnCetakHasilLab12.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab12.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab12.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab12.setText("Model 2");
        MnCetakHasilLab12.setName("MnCetakHasilLab12"); // NOI18N
        MnCetakHasilLab12.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab12ActionPerformed(evt);
            }
        });
        MnPDFLab.add(MnCetakHasilLab12);

        MnCetakHasilLab13.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab13.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab13.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab13.setText("Model 3");
        MnCetakHasilLab13.setName("MnCetakHasilLab13"); // NOI18N
        MnCetakHasilLab13.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab13ActionPerformed(evt);
            }
        });
        MnPDFLab.add(MnCetakHasilLab13);

        MnCetakHasilLab14.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab14.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab14.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab14.setText("Model 4");
        MnCetakHasilLab14.setName("MnCetakHasilLab14"); // NOI18N
        MnCetakHasilLab14.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab14ActionPerformed(evt);
            }
        });
        MnPDFLab.add(MnCetakHasilLab14);

        MnCetakHasilLab15.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab15.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab15.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab15.setText("Model 5");
        MnCetakHasilLab15.setName("MnCetakHasilLab15"); // NOI18N
        MnCetakHasilLab15.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab15ActionPerformed(evt);
            }
        });
        MnPDFLab.add(MnCetakHasilLab15);

        MnCetakHasilLab16.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab16.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab16.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab16.setText("Model 6");
        MnCetakHasilLab16.setName("MnCetakHasilLab16"); // NOI18N
        MnCetakHasilLab16.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab16ActionPerformed(evt);
            }
        });
        MnPDFLab.add(MnCetakHasilLab16);

        MnCetakHasilLab17.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab17.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab17.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab17.setText("Model 7");
        MnCetakHasilLab17.setName("MnCetakHasilLab17"); // NOI18N
        MnCetakHasilLab17.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab17ActionPerformed(evt);
            }
        });
        MnPDFLab.add(MnCetakHasilLab17);

        MnCetakHasilLab18.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab18.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab18.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab18.setText("Model 8");
        MnCetakHasilLab18.setName("MnCetakHasilLab18"); // NOI18N
        MnCetakHasilLab18.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab18ActionPerformed(evt);
            }
        });
        MnPDFLab.add(MnCetakHasilLab18);

        MnCetakHasilLab19.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab19.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab19.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab19.setText("Model 9");
        MnCetakHasilLab19.setName("MnCetakHasilLab19"); // NOI18N
        MnCetakHasilLab19.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab19ActionPerformed(evt);
            }
        });
        MnPDFLab.add(MnCetakHasilLab19);

        MnCetakHasilLab20.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab20.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab20.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab20.setText("Model 10");
        MnCetakHasilLab20.setName("MnCetakHasilLab20"); // NOI18N
        MnCetakHasilLab20.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab20ActionPerformed(evt);
            }
        });
        MnPDFLab.add(MnCetakHasilLab20);

        MnCetakHasilLab21.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakHasilLab21.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab21.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakHasilLab21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab21.setText("Model 11");
        MnCetakHasilLab21.setName("MnCetakHasilLab21"); // NOI18N
        MnCetakHasilLab21.setPreferredSize(new java.awt.Dimension(100, 26));
        MnCetakHasilLab21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab21ActionPerformed(evt);
            }
        });
        MnPDFLab.add(MnCetakHasilLab21);

        jPopupMenu1.add(MnPDFLab);

        MnCetakNota.setBackground(new java.awt.Color(255, 255, 254));
        MnCetakNota.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakNota.setForeground(new java.awt.Color(50, 50, 50));
        MnCetakNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakNota.setText("Cetak Nota Lab");
        MnCetakNota.setName("MnCetakNota"); // NOI18N
        MnCetakNota.setPreferredSize(new java.awt.Dimension(160, 26));
        MnCetakNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakNotaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakNota);

        MnUbah.setBackground(new java.awt.Color(255, 255, 254));
        MnUbah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUbah.setForeground(new java.awt.Color(50, 50, 50));
        MnUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUbah.setText("Ubah Periksa Lab");
        MnUbah.setName("MnUbah"); // NOI18N
        MnUbah.setPreferredSize(new java.awt.Dimension(160, 26));
        MnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUbahActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUbah);

        MnUbah1.setBackground(new java.awt.Color(255, 255, 254));
        MnUbah1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUbah1.setForeground(new java.awt.Color(50, 50, 50));
        MnUbah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUbah1.setText("Ubah Nilai Hasil");
        MnUbah1.setName("MnUbah1"); // NOI18N
        MnUbah1.setPreferredSize(new java.awt.Dimension(160, 26));
        MnUbah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUbah1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUbah1);

        MnSaranKesan.setBackground(new java.awt.Color(255, 255, 254));
        MnSaranKesan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSaranKesan.setForeground(new java.awt.Color(50, 50, 50));
        MnSaranKesan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnSaranKesan.setText("Kesan & Saran");
        MnSaranKesan.setName("MnSaranKesan"); // NOI18N
        MnSaranKesan.setPreferredSize(new java.awt.Dimension(160, 26));
        MnSaranKesan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSaranKesanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnSaranKesan);

        WindowSaran.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowSaran.setName("WindowSaran"); // NOI18N
        WindowSaran.setUndecorated(true);
        WindowSaran.setResizable(false);

        internalFrame6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Kesan & Saran ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('U');
        BtnSimpan.setText("Update");
        BtnSimpan.setToolTipText("Alt+U");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnSimpan);

        BtnCloseIn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn5.setMnemonic('U');
        BtnCloseIn5.setText("Tutup");
        BtnCloseIn5.setToolTipText("Alt+U");
        BtnCloseIn5.setName("BtnCloseIn5"); // NOI18N
        BtnCloseIn5.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnCloseIn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn5ActionPerformed(evt);
            }
        });
        panelGlass6.add(BtnCloseIn5);

        internalFrame6.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)), "Kesan :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll4.setName("Scroll4"); // NOI18N
        Scroll4.setOpaque(true);
        Scroll4.setPreferredSize(new java.awt.Dimension(182, 183));

        Kesan.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(201, 206, 196)));
        Kesan.setColumns(20);
        Kesan.setRows(5);
        Kesan.setName("Kesan"); // NOI18N
        Scroll4.setViewportView(Kesan);

        jPanel3.add(Scroll4, java.awt.BorderLayout.PAGE_START);

        Scroll3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 253)), "Saran :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        Saran.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(201, 206, 196)));
        Saran.setColumns(20);
        Saran.setRows(5);
        Saran.setName("Saran"); // NOI18N
        Scroll3.setViewportView(Saran);

        jPanel3.add(Scroll3, java.awt.BorderLayout.CENTER);

        internalFrame6.add(jPanel3, java.awt.BorderLayout.CENTER);

        WindowSaran.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 253, 247)), "::[ Data Pemeriksaan Laboratorium ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
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

        label10.setText("Keyword :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(240, 23));
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

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(30, 30));
        panelisi1.add(label9);

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

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
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

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
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

        TabRawat.addTab("Data Pemeriksaan", scrollPane1);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        scrollPane2.setName("scrollPane2"); // NOI18N
        scrollPane2.setOpaque(true);

        tbDokter2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbDokter2.setName("tbDokter2"); // NOI18N
        tbDokter2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDokter2MouseClicked(evt);
            }
        });
        tbDokter2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDokter2KeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(tbDokter2);

        TabRawat.addTab("Item Pemeriksaan", scrollPane2);

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

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
    }//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        NoRawat.setText("");
        kdmem.setText("");
        nmmem.setText("");
        kdptg.setText("");
        nmptg.setText("");
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

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
                                tabMode.getValueAt(i,6).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab");
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDataLab.jasper","report","::[ Data Pemeriksaan Laboratorium ]::",param);
                }   break;
            case 1:
                if(tabMode2.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabMode2.getRowCount()!=0){
                    
                    Sequel.queryu("truncate table temporary_lab");
                    int row=tabMode2.getRowCount();
                    for(i=0;i<row;i++){
                        Sequel.menyimpan("temporary_lab","'0','"+
                                tabMode2.getValueAt(i,0).toString()+"','"+
                                tabMode2.getValueAt(i,1).toString()+"','"+
                                tabMode2.getValueAt(i,2).toString()+"','"+
                                tabMode2.getValueAt(i,3).toString()+"','"+
                                tabMode2.getValueAt(i,4).toString()+"','"+
                                tabMode2.getValueAt(i,5).toString()+"','"+
                                tabMode2.getValueAt(i,6).toString()+"','"+
                                tabMode2.getValueAt(i,7).toString()+"','"+
                                tabMode2.getValueAt(i,8).toString()+"','"+
                                tabMode2.getValueAt(i,9).toString()+"','"+
                                tabMode2.getValueAt(i,10).toString()+"','"+
                                tabMode2.getValueAt(i,11).toString()+"','"+
                                tabMode2.getValueAt(i,12).toString()+"','"+
                                tabMode2.getValueAt(i,13).toString()+"','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab");
                    }
                    
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDataLab2.jasper","report","::[ Data Item Pemeriksaan Laboratorium ]::",param);
                }   break;
            case 2:
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
                            "<table width='100%' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
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

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnAll,BtnAll);
        }
    }//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        petugas.dispose();
        member.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,NoRawat);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

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
                    
                    status="";
                    ttljmdokter=0;ttljmpetugas=0;ttlkso=0;ttlpendapatan=0;ttlbhp=0;
                    ttljmdokter=Sequel.cariIsiAngka("select sum(tarif_perujuk)+sum(tarif_tindakan_dokter) from periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttljmpetugas=Sequel.cariIsiAngka("select sum(tarif_tindakan_petugas) from periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttlkso=Sequel.cariIsiAngka("select sum(kso) from periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttlbhp=Sequel.cariIsiAngka("select sum(bhp) from periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttlpendapatan=Sequel.cariIsiAngka("select sum(biaya) from periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttljmdokter=ttljmdokter+Sequel.cariIsiAngka("select sum(bagian_perujuk)+sum(bagian_dokter) from detail_periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttljmpetugas=ttljmpetugas+Sequel.cariIsiAngka("select sum(bagian_laborat) from detail_periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttlkso=ttlkso+Sequel.cariIsiAngka("select sum(kso) from detail_periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttlbhp=ttlbhp+Sequel.cariIsiAngka("select sum(bhp) from detail_periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttlpendapatan=ttlpendapatan+Sequel.cariIsiAngka("select sum(biaya_item) from detail_periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    status=Sequel.cariIsi("select status from periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                  "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                  "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");

                    if(Sequel.queryutf("delete from periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                  "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                  "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'")==true){                    
                        if(Sequel.queryutf("delete from detail_periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                      "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                      "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'")==true){
                            if(status.equals("Ranap")){
                                Sequel.queryu("delete from tampjurnal");    
                                if(ttlpendapatan>0){
                                    Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Laborat_Ranap+"','Suspen Piutang Laborat Ranap','0','"+ttlpendapatan+"'","Rekening");    
                                    Sequel.menyimpan("tampjurnal","'"+Laborat_Ranap+"','Pendapatan Laborat Rawat Inap','"+ttlpendapatan+"','0'","Rekening");                              
                                }
                                if(ttljmdokter>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Laborat_Ranap+"','Beban Jasa Medik Dokter Laborat Ranap','0','"+ttljmdokter+"'","Rekening");    
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Laborat_Ranap+"','Utang Jasa Medik Dokter Laborat Ranap','"+ttljmdokter+"','0'","Rekening");                              
                                }
                                if(ttljmpetugas>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Petugas_Laborat_Ranap+"','Beban Jasa Medik Petugas Laborat Ranap','0','"+ttljmpetugas+"'","Rekening");    
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Petugas_Laborat_Ranap+"','Utang Jasa Medik Petugas Laborat Ranap','"+ttljmpetugas+"','0'","Rekening");                              
                                }
                                if(ttlbhp>0){
                                    Sequel.menyimpan("tampjurnal","'"+HPP_Persediaan_Laborat_Rawat_inap+"','HPP Persediaan Laborat Rawat Inap','0','"+ttlbhp+"'","Rekening");    
                                    Sequel.menyimpan("tampjurnal","'"+Persediaan_BHP_Laborat_Rawat_Inap+"','Persediaan BHP Laborat Rawat Inap','"+ttlbhp+"','0'","Rekening");                              
                                }
                                if(ttlkso>0){
                                    Sequel.menyimpan("tampjurnal","'"+Beban_Kso_Laborat_Ranap+"','Beban KSO Laborat Ranap','0','"+ttlkso+"'","Rekening");    
                                    Sequel.menyimpan("tampjurnal","'"+Utang_Kso_Laborat_Ranap+"','Utang KSO Laborat Ranap','"+ttlkso+"','0'","Rekening");                              
                                }
                                jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),Sequel.cariIsi("select current_date()"),"U","PEMBATALAN PEMERIKSAAN LABORAT RAWAT INAP PASIEN OLEH "+akses.getkode());  
                            }
                            tampil();                        
                        }
                    }
                    
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

private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari,BtnAll);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
    if(tabMode.getRowCount()!=0){
            try {
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
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReport("rptPeriksaLabPermintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }else{
                                Valid.MyReport("rptPeriksaLab.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
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
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
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
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        item=rs3.getDouble("biaya_item");
                                        ttl=ttl+item; 
                                        Sequel.menyimpan("temporary_lab","'0','"+rs3.getString("kd_jenis_prw")+"','   "+rs3.getString("Pemeriksaan")+"','"+item+"','Detail Pemeriksaan','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Biaya Lab");                        
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

    private void MnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUbahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mengubah. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){  
            if(Sequel.cariRegistrasi(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                DlgUbahPeriksaLab ubah=new DlgUbahPeriksaLab(null,false);
                ubah.isCek();
                ubah.setSize(this.getWidth()-20,this.getHeight()-20);
                ubah.setNoRm(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),
                        tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString(), 
                        tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                ubah.setLocationRelativeTo(this);
                ubah.setVisible(true);
            }                
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnUbahActionPerformed

    private void MnCetakHasilLab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {         
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            
                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReport("rptPeriksaLab2Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);  
                            }else{
                                Valid.MyReport("rptPeriksaLab2.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);  
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
    }//GEN-LAST:event_MnCetakHasilLab1ActionPerformed

    private void MnCetakHasilLab2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab2ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {     
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            
                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReport("rptPeriksaLab3Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }else{
                                Valid.MyReport("rptPeriksaLab3.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
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
    }//GEN-LAST:event_MnCetakHasilLab2ActionPerformed

    private void MnCetakHasilLab3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab3ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {      
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("lahir",rs.getString("lahir"));
                        param.put("pengirim",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")));
                        param.put("tanggal",rs.getString("tgl_periksa"));
                        param.put("penjab",rs.getString("nm_dokter"));
                        param.put("petugas",rs.getString("nama"));
                        param.put("jam",rs.getString("jam"));
                        param.put("alamat",rs.getString("alamat"));
                        param.put("kamar",kamar);
                        param.put("namakamar",namakamar);
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            

                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReport("rptPeriksaLab4Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }else{
                                Valid.MyReport("rptPeriksaLab4.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);
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
    }//GEN-LAST:event_MnCetakHasilLab3ActionPerformed

    private void MnCetakHasilLab4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab4ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {        
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("lahir",rs.getString("lahir"));
                        param.put("pengirim",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")));
                        param.put("tanggal",rs.getString("tgl_periksa"));
                        param.put("penjab",rs.getString("nm_dokter"));
                        param.put("petugas",rs.getString("nama"));
                        param.put("jam",rs.getString("jam"));
                        param.put("alamat",rs.getString("alamat"));
                        param.put("kamar",kamar);
                        param.put("namakamar",namakamar);
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            

                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReport("rptPeriksaLab5Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }else{
                                Valid.MyReport("rptPeriksaLab5.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
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
    }//GEN-LAST:event_MnCetakHasilLab4ActionPerformed

    private void MnCetakHasilLab5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab5ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {         
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("lahir",rs.getString("lahir"));
                        param.put("pengirim",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")));
                        param.put("tanggal",rs.getString("tgl_periksa"));
                        param.put("penjab",rs.getString("nm_dokter"));
                        param.put("petugas",rs.getString("nama"));
                        param.put("jam",rs.getString("jam"));
                        param.put("alamat",rs.getString("alamat"));
                        param.put("kamar",kamar);
                        param.put("namakamar",namakamar);
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            

                        Sequel.queryu("truncate table temporary_lab");
                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReport("rptPeriksaLab6Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }else{
                                Valid.MyReport("rptPeriksaLab6.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
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
    }//GEN-LAST:event_MnCetakHasilLab5ActionPerformed

    private void MnCetakHasilLab6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab6ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {   
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                            diagnosa=Sequel.cariIsi("select nm_penyakit from penyakit inner join diagnosa_pasien on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit where diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='1' and diagnosa_pasien.no_rawat=?",rs.getString("no_rawat"));
                        }else if(kamar.equals("")){
                            kamar="Poli";
                            namakamar=Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                                    "where reg_periksa.no_rawat='"+rs.getString("no_rawat")+"'");
                            diagnosa=Sequel.cariIsi("select nm_penyakit from penyakit inner join diagnosa_pasien on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit where diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and diagnosa_pasien.no_rawat=?",rs.getString("no_rawat"));
                        }
                        Map<String, Object> param = new HashMap<>();
                        param.put("noperiksa",rs.getString("no_rawat"));
                        param.put("norm",rs.getString("no_rkm_medis"));
                        param.put("namapasien",rs.getString("nm_pasien"));
                        param.put("jkel",rs.getString("jk"));
                        param.put("diagnosa",diagnosa);
                        param.put("umur",rs.getString("umur"));
                        param.put("lahir",rs.getString("lahir"));
                        param.put("pengirim",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")));
                        param.put("tanggal",rs.getString("tgl_periksa"));
                        param.put("penjab",rs.getString("nm_dokter"));
                        param.put("petugas",rs.getString("nama"));
                        param.put("jam",rs.getString("jam"));
                        param.put("alamat",rs.getString("alamat"));
                        param.put("kamar",kamar);
                        param.put("namakamar",namakamar);
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            

                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReport("rptPeriksaLab7Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }else{
                                Valid.MyReport("rptPeriksaLab7.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
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
    }//GEN-LAST:event_MnCetakHasilLab6ActionPerformed

    private void MnCetakHasilLab7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab7ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {    
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("pekerjaan",Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?",rs.getString("no_rkm_medis")));
                        param.put("noktp",Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?",rs.getString("no_rkm_medis")));
                        param.put("lahir",rs.getString("lahir"));
                        param.put("pengirim",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")));
                        param.put("tanggal",rs.getString("tgl_periksa"));
                        param.put("penjab",rs.getString("nm_dokter"));
                        param.put("petugas",rs.getString("nama"));
                        param.put("jam",rs.getString("jam"));
                        param.put("alamat",rs.getString("alamat"));
                        param.put("kamar",kamar);
                        param.put("namakamar",namakamar);
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            
                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReport("rptPeriksaLab8Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }else{
                                Valid.MyReport("rptPeriksaLab8.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
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
    }//GEN-LAST:event_MnCetakHasilLab7ActionPerformed

    private void MnCetakHasilLab8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab8ActionPerformed
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
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            
                        kesan="";
                        saran="";
                        ps5=koneksi.prepareStatement(
                            "select saran,kesan from saran_kesan_lab where no_rawat=? and tgl_periksa=? and jam=?");  
                        try {
                            ps5.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            ps5.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                            ps5.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                            rs2=ps5.executeQuery();
                            while(rs2.next()){      
                                kesan=rs2.getString("kesan");
                                saran=rs2.getString("saran");
                            } 
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps5!=null){
                                ps5.close();
                            }
                        }                    
                        param.put("kesan",kesan);
                        param.put("saran",saran);
                        Sequel.queryu("truncate table temporary_lab");  
                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReport("rptPeriksaLab9Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }else{
                                Valid.MyReport("rptPeriksaLab9.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
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
    }//GEN-LAST:event_MnCetakHasilLab8ActionPerformed

    private void MnCetakHasilLab9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab9ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {         
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            
                        kesan="";
                        saran="";
                        ps5=koneksi.prepareStatement(
                            "select saran,kesan from saran_kesan_lab where no_rawat=? and tgl_periksa=? and jam=?");  
                        try {
                            ps5.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            ps5.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                            ps5.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                            rs2=ps5.executeQuery();
                            while(rs2.next()){      
                                kesan=rs2.getString("kesan");
                                saran=rs2.getString("saran");
                            } 
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps5!=null){
                                ps5.close();
                            }
                        }                    
                        param.put("kesan",kesan);
                        param.put("saran",saran);
                        Sequel.queryu("truncate table temporary_lab");
                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReport("rptPeriksaLab10Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }else{
                                Valid.MyReport("rptPeriksaLab10.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
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
    }//GEN-LAST:event_MnCetakHasilLab9ActionPerformed

    private void MnCetakHasilLab10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab10ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {      
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            
                        kesan="";
                        saran="";
                        ps5=koneksi.prepareStatement(
                            "select saran,kesan from saran_kesan_lab where no_rawat=? and tgl_periksa=? and jam=?");  
                        try {
                            ps5.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            ps5.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                            ps5.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                            rs2=ps5.executeQuery();
                            while(rs2.next()){      
                                kesan=rs2.getString("kesan");
                                saran=rs2.getString("saran");
                            } 
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps5!=null){
                                ps5.close();
                            }
                        }                    
                        param.put("kesan",kesan);
                        param.put("saran",saran);
                        Sequel.queryu("truncate table temporary_lab");
                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReport("rptPeriksaLab11Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
                            }else{
                                Valid.MyReport("rptPeriksaLab11.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);   
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
    }//GEN-LAST:event_MnCetakHasilLab10ActionPerformed

    private void MnSaranKesanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSaranKesanActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(tbDokter.getSelectedRow()<= -1){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan pilih data..!!");
        }else {
            if(Kd2.getText().equals("")){
               JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!"); 
            }else{
                try {
                    Kesan.setText("");
                    Saran.setText("");
                    ps5=koneksi.prepareStatement(
                        "select saran,kesan from saran_kesan_lab where no_rawat=? and tgl_periksa=? and jam=?");  
                    try {
                        ps5.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                        ps5.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                        ps5.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                        rs=ps5.executeQuery();
                        while(rs.next()){      
                            Kesan.setText(rs.getString("kesan"));
                            Saran.setText(rs.getString("saran"));
                        } 
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps5!=null){
                            ps5.close();
                        }
                    }
                    WindowSaran.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    WindowSaran.setLocationRelativeTo(internalFrame1);
                    WindowSaran.setVisible(true);
                } catch (Exception ex) {
                    System.out.println(ex);
                }
            }         
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnSaranKesanActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(Kd2.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
        }else{
            if(Saran.getText().equals("")&&Kesan.getText().equals("")){
                Sequel.queryu2("delete from saran_kesan_lab where no_rawat=? and tgl_periksa=? and jam=?",3,new String[]{
                    tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString()
                });
            }else{
                if(Sequel.menyimpantf2("saran_kesan_lab","?,?,?,?,?","Kesan & Saran", 5,new String[]{
                    tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString(),
                    tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString(),Saran.getText(),Kesan.getText()
                })==false){
                    Sequel.queryu2("update saran_kesan_lab set saran=?,kesan=? where no_rawat=? and tgl_periksa=? and jam=?",5,new String[]{
                        Saran.getText(),Kesan.getText(),tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),
                        tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString()
                    });
                }
            }

            JOptionPane.showMessageDialog(null,"Proses update selesai...!!!!");
        }
    }//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }
    }//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnCloseIn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn5ActionPerformed
        WindowSaran.dispose();
    }//GEN-LAST:event_BtnCloseIn5ActionPerformed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }else if(TabRawat.getSelectedIndex()==2){
            tampil3();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void tbDokter2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokter2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDokter2MouseClicked

    private void tbDokter2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokter2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbDokter2KeyPressed

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
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            

                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReportPDF("rptPeriksaLabPermintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);
                            }else{
                                Valid.MyReportPDF("rptPeriksaLab.jasper","report","::[ Pemeriksaan Laboratorium ]::",param); 
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

    private void MnCetakHasilLab12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab12ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {         
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            

                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReportPDF("rptPeriksaLab2Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);
                            }else{
                                Valid.MyReportPDF("rptPeriksaLab2.jasper","report","::[ Pemeriksaan Laboratorium ]::",param); 
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
    }//GEN-LAST:event_MnCetakHasilLab12ActionPerformed

    private void MnCetakHasilLab13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab13ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {     
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            

                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReportPDF("rptPeriksaLab3Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param); 
                            }else{
                                Valid.MyReportPDF("rptPeriksaLab3.jasper","report","::[ Pemeriksaan Laboratorium ]::",param); 
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
    }//GEN-LAST:event_MnCetakHasilLab13ActionPerformed

    private void MnCetakHasilLab14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab14ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {      
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("lahir",rs.getString("lahir"));
                        param.put("pengirim",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")));
                        param.put("tanggal",rs.getString("tgl_periksa"));
                        param.put("penjab",rs.getString("nm_dokter"));
                        param.put("petugas",rs.getString("nama"));
                        param.put("jam",rs.getString("jam"));
                        param.put("alamat",rs.getString("alamat"));
                        param.put("kamar",kamar);
                        param.put("namakamar",namakamar);
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            

                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReportPDF("rptPeriksaLab4Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);
                            }else{
                                Valid.MyReportPDF("rptPeriksaLab4.jasper","report","::[ Pemeriksaan Laboratorium ]::",param); 
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
    }//GEN-LAST:event_MnCetakHasilLab14ActionPerformed

    private void MnCetakHasilLab15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab15ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {        
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("lahir",rs.getString("lahir"));
                        param.put("pengirim",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")));
                        param.put("tanggal",rs.getString("tgl_periksa"));
                        param.put("penjab",rs.getString("nm_dokter"));
                        param.put("petugas",rs.getString("nama"));
                        param.put("jam",rs.getString("jam"));
                        param.put("alamat",rs.getString("alamat"));
                        param.put("kamar",kamar);
                        param.put("namakamar",namakamar);
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            

                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReportPDF("rptPeriksaLab5Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);
                            }else{
                                Valid.MyReportPDF("rptPeriksaLab5.jasper","report","::[ Pemeriksaan Laboratorium ]::",param); 
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
    }//GEN-LAST:event_MnCetakHasilLab15ActionPerformed

    private void MnCetakHasilLab16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab16ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {         
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("lahir",rs.getString("lahir"));
                        param.put("pengirim",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")));
                        param.put("tanggal",rs.getString("tgl_periksa"));
                        param.put("penjab",rs.getString("nm_dokter"));
                        param.put("petugas",rs.getString("nama"));
                        param.put("jam",rs.getString("jam"));
                        param.put("alamat",rs.getString("alamat"));
                        param.put("kamar",kamar);
                        param.put("namakamar",namakamar);
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            

                        Sequel.queryu("truncate table temporary_lab");
                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReportPDF("rptPeriksaLab6Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);
                            }else{
                                Valid.MyReportPDF("rptPeriksaLab6.jasper","report","::[ Pemeriksaan Laboratorium ]::",param); 
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
    }//GEN-LAST:event_MnCetakHasilLab16ActionPerformed

    private void MnCetakHasilLab17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab17ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {   
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                            diagnosa=Sequel.cariIsi("select nm_penyakit from penyakit inner join diagnosa_pasien on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit where diagnosa_pasien.status='Ranap' and diagnosa_pasien.prioritas='1' and diagnosa_pasien.no_rawat=?",rs.getString("no_rawat"));
                        }else if(kamar.equals("")){
                            kamar="Poli";
                            namakamar=Sequel.cariIsi("select nm_poli from poliklinik inner join reg_periksa on poliklinik.kd_poli=reg_periksa.kd_poli "+
                                    "where reg_periksa.no_rawat='"+rs.getString("no_rawat")+"'");
                            diagnosa=Sequel.cariIsi("select nm_penyakit from penyakit inner join diagnosa_pasien on penyakit.kd_penyakit=diagnosa_pasien.kd_penyakit where diagnosa_pasien.status='Ralan' and diagnosa_pasien.prioritas='1' and diagnosa_pasien.no_rawat=?",rs.getString("no_rawat"));
                        }
                        Map<String, Object> param = new HashMap<>();
                        param.put("noperiksa",rs.getString("no_rawat"));
                        param.put("norm",rs.getString("no_rkm_medis"));
                        param.put("namapasien",rs.getString("nm_pasien"));
                        param.put("jkel",rs.getString("jk"));
                        param.put("diagnosa",diagnosa);
                        param.put("umur",rs.getString("umur"));
                        param.put("lahir",rs.getString("lahir"));
                        param.put("pengirim",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")));
                        param.put("tanggal",rs.getString("tgl_periksa"));
                        param.put("penjab",rs.getString("nm_dokter"));
                        param.put("petugas",rs.getString("nama"));
                        param.put("jam",rs.getString("jam"));
                        param.put("alamat",rs.getString("alamat"));
                        param.put("kamar",kamar);
                        param.put("namakamar",namakamar);
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            

                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReportPDF("rptPeriksaLab7Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);
                            }else{
                                Valid.MyReportPDF("rptPeriksaLab7.jasper","report","::[ Pemeriksaan Laboratorium ]::",param); 
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
    }//GEN-LAST:event_MnCetakHasilLab17ActionPerformed

    private void MnCetakHasilLab18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab18ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {    
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("pekerjaan",Sequel.cariIsi("select pekerjaan from pasien where no_rkm_medis=?",rs.getString("no_rkm_medis")));
                        param.put("noktp",Sequel.cariIsi("select no_ktp from pasien where no_rkm_medis=?",rs.getString("no_rkm_medis")));
                        param.put("lahir",rs.getString("lahir"));
                        param.put("pengirim",Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")));
                        param.put("tanggal",rs.getString("tgl_periksa"));
                        param.put("penjab",rs.getString("nm_dokter"));
                        param.put("petugas",rs.getString("nama"));
                        param.put("jam",rs.getString("jam"));
                        param.put("alamat",rs.getString("alamat"));
                        param.put("kamar",kamar);
                        param.put("namakamar",namakamar);
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            

                        Sequel.queryu("truncate table temporary_lab");

                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReportPDF("rptPeriksaLab8Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);
                            }else{
                                Valid.MyReportPDF("rptPeriksaLab8.jasper","report","::[ Pemeriksaan Laboratorium ]::",param); 
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
    }//GEN-LAST:event_MnCetakHasilLab18ActionPerformed

    private void MnCetakHasilLab19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab19ActionPerformed
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
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            
                        
                        kesan="";
                        saran="";
                        ps5=koneksi.prepareStatement(
                            "select saran,kesan from saran_kesan_lab where no_rawat=? and tgl_periksa=? and jam=?");  
                        try {
                            ps5.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            ps5.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                            ps5.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                            rs2=ps5.executeQuery();
                            while(rs2.next()){      
                                kesan=rs2.getString("kesan");
                                saran=rs2.getString("saran");
                            } 
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps5!=null){
                                ps5.close();
                            }
                        }                    
                        param.put("kesan",kesan);
                        param.put("saran",saran);
                        Sequel.queryu("truncate table temporary_lab");  
                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReportPDF("rptPeriksaLab9Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);
                            }else{
                                Valid.MyReportPDF("rptPeriksaLab9.jasper","report","::[ Pemeriksaan Laboratorium ]::",param); 
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
    }//GEN-LAST:event_MnCetakHasilLab19ActionPerformed

    private void MnCetakHasilLab20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab20ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {         
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            
                        
                        kesan="";
                        saran="";
                        ps5=koneksi.prepareStatement(
                            "select saran,kesan from saran_kesan_lab where no_rawat=? and tgl_periksa=? and jam=?");  
                        try {
                            ps5.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            ps5.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                            ps5.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                            rs2=ps5.executeQuery();
                            while(rs2.next()){      
                                kesan=rs2.getString("kesan");
                                saran=rs2.getString("saran");
                            } 
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps5!=null){
                                ps5.close();
                            }
                        }                    
                        param.put("kesan",kesan);
                        param.put("saran",saran);
                        Sequel.queryu("truncate table temporary_lab");
                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReportPDF("rptPeriksaLab10Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);
                            }else{
                                Valid.MyReportPDF("rptPeriksaLab10.jasper","report","::[ Pemeriksaan Laboratorium ]::",param); 
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
    }//GEN-LAST:event_MnCetakHasilLab20ActionPerformed

    private void MnCetakHasilLab21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakHasilLab21ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {      
                ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,periksa_lab.nip,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter inner join kelurahan inner join kecamatan inner join kabupaten "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter "+
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where "+
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
                        param.put("finger",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("kd_dokter")));  
                        param.put("finger2",Sequel.cariIsi("select sha1(sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",rs.getString("nip")));  
            
                        
                        kesan="";
                        saran="";
                        ps5=koneksi.prepareStatement(
                            "select saran,kesan from saran_kesan_lab where no_rawat=? and tgl_periksa=? and jam=?");  
                        try {
                            ps5.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                            ps5.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                            ps5.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                            rs2=ps5.executeQuery();
                            while(rs2.next()){      
                                kesan=rs2.getString("kesan");
                                saran=rs2.getString("saran");
                            } 
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps5!=null){
                                ps5.close();
                            }
                        }                    
                        param.put("kesan",kesan);
                        param.put("saran",saran);
                        Sequel.queryu("truncate table temporary_lab");
                        ps2=koneksi.prepareStatement(
                            "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                            "and periksa_lab.jam=?");
                        try {
                            ps2.setString(1,rs.getString("no_rawat"));
                            ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                            ps2.setString(3,rs.getString("jam"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                Sequel.menyimpan("temporary_lab","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                                ps3=koneksi.prepareStatement(
                                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                                try {
                                    ps3.setString(1,rs.getString("no_rawat"));
                                    ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                    ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                                    ps3.setString(4,rs.getString("jam"));
                                    rs3=ps3.executeQuery();
                                    while(rs3.next()){
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                                +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
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
                                Valid.MyReportPDF("rptPeriksaLab11Permintaan.jasper","report","::[ Pemeriksaan Laboratorium ]::",param);
                            }else{
                                Valid.MyReportPDF("rptPeriksaLab11.jasper","report","::[ Pemeriksaan Laboratorium ]::",param); 
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
    }//GEN-LAST:event_MnCetakHasilLab21ActionPerformed

    private void MnUbah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUbah1ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mengubah. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){  
            DlgUbahNilaiLab ubah=new DlgUbahNilaiLab(null,false);
            ubah.isCek();
            ubah.setNoRm(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),
                    tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString(), 
                    tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
            ubah.setSize(this.getWidth()-20,this.getHeight()-20);
            ubah.setLocationRelativeTo(this);
            ubah.setVisible(true);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnUbah1ActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariPeriksaLab dialog = new DlgCariPeriksaLab(new javax.swing.JFrame(), true);
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
    private widget.Button BtnCloseIn5;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox Kd2;
    private widget.TextArea Kesan;
    private widget.editorpane LoadHTML1;
    private javax.swing.JMenuItem MnCetakHasilLab;
    private javax.swing.JMenuItem MnCetakHasilLab1;
    private javax.swing.JMenuItem MnCetakHasilLab10;
    private javax.swing.JMenuItem MnCetakHasilLab11;
    private javax.swing.JMenuItem MnCetakHasilLab12;
    private javax.swing.JMenuItem MnCetakHasilLab13;
    private javax.swing.JMenuItem MnCetakHasilLab14;
    private javax.swing.JMenuItem MnCetakHasilLab15;
    private javax.swing.JMenuItem MnCetakHasilLab16;
    private javax.swing.JMenuItem MnCetakHasilLab17;
    private javax.swing.JMenuItem MnCetakHasilLab18;
    private javax.swing.JMenuItem MnCetakHasilLab19;
    private javax.swing.JMenuItem MnCetakHasilLab2;
    private javax.swing.JMenuItem MnCetakHasilLab20;
    private javax.swing.JMenuItem MnCetakHasilLab21;
    private javax.swing.JMenuItem MnCetakHasilLab3;
    private javax.swing.JMenuItem MnCetakHasilLab4;
    private javax.swing.JMenuItem MnCetakHasilLab5;
    private javax.swing.JMenuItem MnCetakHasilLab6;
    private javax.swing.JMenuItem MnCetakHasilLab7;
    private javax.swing.JMenuItem MnCetakHasilLab8;
    private javax.swing.JMenuItem MnCetakHasilLab9;
    private javax.swing.JMenu MnCetakLab;
    private javax.swing.JMenuItem MnCetakNota;
    private javax.swing.JMenu MnPDFLab;
    private javax.swing.JMenuItem MnSaranKesan;
    private javax.swing.JMenuItem MnUbah;
    private javax.swing.JMenuItem MnUbah1;
    private widget.TextBox NoRawat;
    private widget.TextArea Saran;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll3;
    private widget.ScrollPane Scroll4;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private javax.swing.JDialog WindowSaran;
    private widget.Button btnPasien;
    private widget.Button btnPetugas;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame6;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdmem;
    private widget.TextBox kdptg;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmmem;
    private widget.TextBox nmptg;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbDokter;
    private widget.Table tbDokter2;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        try {
            Valid.tabelKosong(tabMode);  
            if(NoRawat.getText().equals("")&&kdmem.getText().equals("")&&kdptg.getText().equals("")&&TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_lab.tgl_periksa,periksa_lab.jam,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,dokter.nm_dokter "+
                    "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on periksa_lab.nip=petugas.nip "+
                    "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter where "+
                    "periksa_lab.tgl_periksa between ? and ? group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam) "+
                    "order by periksa_lab.tgl_periksa desc,periksa_lab.jam desc");
            }else{
                ps=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_lab.tgl_periksa,periksa_lab.jam,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,dokter.nm_dokter "+
                    "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on periksa_lab.nip=petugas.nip "+
                    "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter where "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? "+
                    "and petugas.nip like ? and pasien.nm_pasien like ? or "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? "+
                    "and petugas.nip like ? and petugas.nama like ? or "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? "+
                    "and petugas.nip like ? and reg_periksa.no_rkm_medis like ? group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam) "+
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
                    ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(9,"%"+NoRawat.getText()+"%");
                    ps.setString(10,"%"+kdmem.getText()+"%");
                    ps.setString(11,"%"+kdptg.getText()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(15,"%"+NoRawat.getText()+"%");
                    ps.setString(16,"%"+kdmem.getText()+"%");
                    ps.setString(17,"%"+kdptg.getText()+"%");
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
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
                    tabMode.addRow(new Object[]{rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien")+" ("+kamar+" : "+namakamar+")",
                                                rs.getString("nama"),rs.getString("tgl_periksa"),rs.getString("jam"),
                                                Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")),rs.getString("nm_dokter")});
                    tabMode.addRow(new Object[]{"","","Pemeriksaan","Hasil","Satuan","Nilai Rujukan","Keterangan"});
                    ps2=koneksi.prepareStatement(
                        "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                        "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                        "and periksa_lab.jam=? order by jns_perawatan_lab.kd_jenis_prw");
                    try {
                        ps2.setString(1,rs.getString("no_rawat"));
                        ps2.setString(2,rs.getString("tgl_periksa"));
                        ps2.setString(3,rs.getString("jam"));
                        rs2=ps2.executeQuery();
                        item=0;
                        while(rs2.next()){     
                           item=item+rs2.getDouble("biaya");
                           ttl=ttl+rs2.getDouble("biaya");
                           tabMode.addRow(new Object[]{"","",rs2.getString("kd_jenis_prw")+" "+rs2.getString("nm_perawatan")+" "+Valid.SetAngka(rs2.getDouble("biaya")),"","","",""});
                           ps3=koneksi.prepareStatement(
                                "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                                "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? order by template_laboratorium.urut");
                           try {
                                ps3.setString(1,rs.getString("no_rawat"));
                                ps3.setString(2,rs2.getString("kd_jenis_prw"));
                                ps3.setString(3,rs.getString("tgl_periksa"));
                                ps3.setString(4,rs.getString("jam"));
                                rs3=ps3.executeQuery();
                                while(rs3.next()){
                                    item=item+rs3.getDouble("biaya_item");
                                    ttl=ttl+rs3.getDouble("biaya_item");
                                    tabMode.addRow(new Object[]{"","","  "+rs3.getString("Pemeriksaan")+" "+Valid.SetAngka(rs3.getDouble("biaya_item")),rs3.getString("nilai"),
                                                                rs3.getString("satuan"),rs3.getString("nilai_rujukan"),rs3.getString("keterangan")});
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

                    saran="";kesan="";
                    ps5=koneksi.prepareStatement(
                        "select saran,kesan from saran_kesan_lab where no_rawat=? and tgl_periksa=? and jam=?");  
                    try {
                        ps5.setString(1,rs.getString("no_rawat"));
                        ps5.setString(2,rs.getString("tgl_periksa"));
                        ps5.setString(3,rs.getString("jam"));
                        rs5=ps5.executeQuery();
                        if(rs5.next()){      
                            kesan=rs5.getString("kesan");saran=rs5.getString("saran");
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
                    if(item>0){
                        tabMode.addRow(new Object[]{"","","Biaya Periksa : "+Valid.SetAngka(item),"","","Kesan : "+kesan,"Saran : "+saran});
                    }
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
                  tabMode.addRow(new Object[]{">>","Total : "+Valid.SetAngka(ttl),"","","","",""});
            }  
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
    }
    
    private void tampil2() {
        try {
            Valid.tabelKosong(tabMode2);  
            if(NoRawat.getText().equals("")&&kdmem.getText().equals("")&&kdptg.getText().equals("")&&TCari.getText().equals("")){
                ps=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.status,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,dokter.nm_dokter,template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,"+
                    "template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,detail_periksa_lab.keterangan,poliklinik.nm_poli, "+
                    "jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from periksa_lab inner join reg_periksa inner join pasien "+
                    "inner join petugas inner join dokter inner join detail_periksa_lab inner join jns_perawatan_lab inner join template_laboratorium inner join poliklinik "+
                    "on detail_periksa_lab.id_template=template_laboratorium.id_template and periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.kd_dokter=dokter.kd_dokter and "+
                    "periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw and "+
                    "periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam and periksa_lab.nip=petugas.nip and reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "where periksa_lab.tgl_periksa between ? and ? order by periksa_lab.tgl_periksa desc,periksa_lab.jam desc,detail_periksa_lab.kd_jenis_prw,template_laboratorium.urut");
            }else{
                ps=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_lab.tgl_periksa,periksa_lab.jam,periksa_lab.status,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,dokter.nm_dokter,template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,"+
                    "template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,detail_periksa_lab.keterangan,poliklinik.nm_poli, "+
                    "jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan from periksa_lab inner join reg_periksa inner join pasien "+
                    "inner join petugas inner join dokter inner join detail_periksa_lab inner join jns_perawatan_lab inner join template_laboratorium inner join poliklinik "+
                    "on detail_periksa_lab.id_template=template_laboratorium.id_template and periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.kd_dokter=dokter.kd_dokter and "+
                    "periksa_lab.no_rawat=detail_periksa_lab.no_rawat and periksa_lab.kd_jenis_prw=detail_periksa_lab.kd_jenis_prw and periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw and "+
                    "periksa_lab.tgl_periksa=detail_periksa_lab.tgl_periksa and periksa_lab.jam=detail_periksa_lab.jam and periksa_lab.nip=petugas.nip and reg_periksa.kd_poli=poliklinik.kd_poli "+
                    "where periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and pasien.nm_pasien like ? or "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and petugas.nama like ? or "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and dokter.nm_dokter like ? or "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and template_laboratorium.Pemeriksaan like ? or "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and jns_perawatan_lab.nm_perawatan like ? or "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and detail_periksa_lab.keterangan like ? or "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis like ? "+
                    "order by periksa_lab.tgl_periksa desc,periksa_lab.jam desc,detail_periksa_lab.kd_jenis_prw,template_laboratorium.urut");
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
                    ps.setString(7,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(8,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(9,"%"+NoRawat.getText()+"%");
                    ps.setString(10,"%"+kdmem.getText()+"%");
                    ps.setString(11,"%"+kdptg.getText()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(14,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(15,"%"+NoRawat.getText()+"%");
                    ps.setString(16,"%"+kdmem.getText()+"%");
                    ps.setString(17,"%"+kdptg.getText()+"%");
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
                    ps.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(21,"%"+NoRawat.getText()+"%");
                    ps.setString(22,"%"+kdmem.getText()+"%");
                    ps.setString(23,"%"+kdptg.getText()+"%");
                    ps.setString(24,"%"+TCari.getText().trim()+"%");
                    ps.setString(25,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(26,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(27,"%"+NoRawat.getText()+"%");
                    ps.setString(28,"%"+kdmem.getText()+"%");
                    ps.setString(29,"%"+kdptg.getText()+"%");
                    ps.setString(30,"%"+TCari.getText().trim()+"%");
                    ps.setString(31,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(32,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(33,"%"+NoRawat.getText()+"%");
                    ps.setString(34,"%"+kdmem.getText()+"%");
                    ps.setString(35,"%"+kdptg.getText()+"%");
                    ps.setString(36,"%"+TCari.getText().trim()+"%");
                    ps.setString(37,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(38,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(39,"%"+NoRawat.getText()+"%");
                    ps.setString(40,"%"+kdmem.getText()+"%");
                    ps.setString(41,"%"+kdptg.getText()+"%");
                    ps.setString(42,"%"+TCari.getText().trim()+"%");
                }
                    
                rs=ps.executeQuery();
                while(rs.next()){
                    kamar=rs.getString("nm_poli");
                    if(rs.getString("status").equals("Ranap")){
                        kamar=Sequel.cariIsi(
                                "select nm_bangsal from bangsal inner join kamar inner join kamar_inap on bangsal.kd_bangsal=kamar.kd_bangsal "+
                                " and kamar_inap.kd_kamar=kamar.kd_kamar where no_rawat=? order by tgl_masuk desc limit 1 ",rs.getString("no_rawat"));
                    }
                    
                    tabMode2.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis")+" "+rs.getString("nm_pasien"),
                        rs.getString("tgl_periksa"),rs.getString("jam"),rs.getString("nm_perawatan"),rs.getString("Pemeriksaan"),
                        rs.getString("nilai"),rs.getString("satuan"),rs.getString("nilai_rujukan"),rs.getString("keterangan"),
                        kamar,rs.getString("nama"),Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs.getString("dokter_perujuk")),
                        rs.getString("nm_dokter")                        
                    });
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
                    "select pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk from pasien inner join reg_periksa inner join periksa_lab inner join dokter inner join jns_perawatan_lab inner join petugas on "+
                    "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=periksa_lab.no_rawat and periksa_lab.nip=petugas.nip "+
                    "and periksa_lab.dokter_perujuk=dokter.kd_dokter and jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                    "where periksa_lab.tgl_periksa between ? and ? group by pasien.no_rkm_medis order by tgl_registrasi desc");
            }else{
                ps=koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk from pasien inner join reg_periksa inner join periksa_lab inner join dokter inner join jns_perawatan_lab inner join petugas on "+
                    "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=periksa_lab.no_rawat and periksa_lab.nip=petugas.nip "+
                    "and periksa_lab.dokter_perujuk=dokter.kd_dokter and jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                    "where periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and periksa_lab.tgl_periksa between ? and ? and pasien.no_rkm_medis like ? or "+
                    "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and periksa_lab.tgl_periksa between ? and ? and pasien.nm_pasien like ? or "+
                    "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and periksa_lab.tgl_periksa between ? and ? and jns_perawatan_lab.nm_perawatan like ? or "+
                    "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? or "+
                    "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and periksa_lab.tgl_periksa between ? and ? and petugas.nama like ? or "+
                    "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and periksa_lab.tgl_periksa between ? and ? and dokter.nm_dokter like ? group by pasien.no_rkm_medis order by tgl_registrasi desc");
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
                    ps.setString(7,"%"+NoRawat.getText()+"%");
                    ps.setString(8,"%"+kdmem.getText()+"%");
                    ps.setString(9,"%"+kdptg.getText()+"%");
                    ps.setString(10,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(11,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
                    ps.setString(13,"%"+NoRawat.getText()+"%");
                    ps.setString(14,"%"+kdmem.getText()+"%");
                    ps.setString(15,"%"+kdptg.getText()+"%");
                    ps.setString(16,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(17,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(18,"%"+TCari.getText().trim()+"%");
                    ps.setString(19,"%"+NoRawat.getText()+"%");
                    ps.setString(20,"%"+kdmem.getText()+"%");
                    ps.setString(21,"%"+kdptg.getText()+"%");
                    ps.setString(22,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(23,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(24,"%"+TCari.getText().trim()+"%");
                    ps.setString(25,"%"+NoRawat.getText()+"%");
                    ps.setString(26,"%"+kdmem.getText()+"%");
                    ps.setString(27,"%"+kdptg.getText()+"%");
                    ps.setString(28,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(29,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(30,"%"+TCari.getText().trim()+"%");
                    ps.setString(31,"%"+NoRawat.getText()+"%");
                    ps.setString(32,"%"+kdmem.getText()+"%");
                    ps.setString(33,"%"+kdptg.getText()+"%");
                    ps.setString(34,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                    ps.setString(35,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                    ps.setString(36,"%"+TCari.getText().trim()+"%");
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
                            "<td valign='top'>"+rs.getString("nm_pasien")+"</td>"+
                            "<td valign='top'>"+rs.getString("umurdaftar")+" "+rs.getString("sttsumur")+"</td>"+
                            "<td valign='top'>"+rs.getString("jk")+"</td>"+
                            "<td valign='top'>"+
                                "<table width='100%' border='0' align='center' cellpadding='2px' cellspacing='0' class='tbl_form'>"+
                                    "<tr class='isi'>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='8%'>No.Rawat</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='8%'>Tanggal</td>"+
                                        "<td valign='top' bgcolor='#fdfff9' align='center' width='84%'>Pemeriksaan</td>"+
                                    "</tr>");
                    if(NoRawat.getText().equals("")&&kdmem.getText().equals("")&&kdptg.getText().equals("")&&TCari.getText().equals("")){
                        ps2=koneksi.prepareStatement(
                            "select periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam,dokter.nm_dokter from "+
                            "pasien inner join reg_periksa inner join periksa_lab inner join dokter inner join jns_perawatan_lab inner join petugas on "+
                            "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=periksa_lab.no_rawat and periksa_lab.nip=petugas.nip "+
                            "and periksa_lab.dokter_perujuk=dokter.kd_dokter and jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                            "where periksa_lab.tgl_periksa between ? and ? group by periksa_lab.tgl_periksa,periksa_lab.jam order by tgl_registrasi");
                    }else{
                        ps2=koneksi.prepareStatement(
                            "select periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam,dokter.nm_dokter from "+
                            "pasien inner join reg_periksa inner join periksa_lab inner join dokter inner join jns_perawatan_lab inner join petugas on "+
                            "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=periksa_lab.no_rawat and periksa_lab.nip=petugas.nip "+
                            "and periksa_lab.dokter_perujuk=dokter.kd_dokter and jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                            "where periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.tgl_periksa between ? and ? and pasien.no_rkm_medis like ? or "+
                            "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.tgl_periksa between ? and ? and pasien.nm_pasien like ? or "+
                            "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.tgl_periksa between ? and ? and jns_perawatan_lab.nm_perawatan like ? or "+
                            "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? or "+
                            "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.tgl_periksa between ? and ? and petugas.nama like ? or "+
                            "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.tgl_periksa between ? and ? and dokter.nm_dokter like ? "+
                            "group by periksa_lab.tgl_periksa,periksa_lab.jam order by tgl_registrasi");
                    }
                        
                    try {
                        if(NoRawat.getText().equals("")&&kdmem.getText().equals("")&&kdptg.getText().equals("")){
                            ps2.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            ps2.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                        }else{
                            ps2.setString(1,"%"+NoRawat.getText()+"%");
                            ps2.setString(2,"%"+kdmem.getText()+"%");
                            ps2.setString(3,"%"+kdptg.getText()+"%");
                            ps2.setString(4,rs.getString("no_rkm_medis"));
                            ps2.setString(5,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            ps2.setString(6,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            ps2.setString(7,"%"+TCari.getText().trim()+"%");
                            ps2.setString(8,"%"+NoRawat.getText()+"%");
                            ps2.setString(9,"%"+kdmem.getText()+"%");
                            ps2.setString(10,"%"+kdptg.getText()+"%");
                            ps2.setString(11,rs.getString("no_rkm_medis"));
                            ps2.setString(12,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            ps2.setString(13,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            ps2.setString(14,"%"+TCari.getText().trim()+"%");
                            ps2.setString(15,"%"+NoRawat.getText()+"%");
                            ps2.setString(16,"%"+kdmem.getText()+"%");
                            ps2.setString(17,"%"+kdptg.getText()+"%");
                            ps2.setString(18,rs.getString("no_rkm_medis"));
                            ps2.setString(19,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            ps2.setString(20,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            ps2.setString(21,"%"+TCari.getText().trim()+"%");
                            ps2.setString(22,"%"+NoRawat.getText()+"%");
                            ps2.setString(23,"%"+kdmem.getText()+"%");
                            ps2.setString(24,"%"+kdptg.getText()+"%");
                            ps2.setString(25,rs.getString("no_rkm_medis"));
                            ps2.setString(26,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            ps2.setString(27,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            ps2.setString(28,"%"+TCari.getText().trim()+"%");    
                            ps2.setString(29,"%"+NoRawat.getText()+"%");
                            ps2.setString(30,"%"+kdmem.getText()+"%");
                            ps2.setString(31,"%"+kdptg.getText()+"%");
                            ps2.setString(32,rs.getString("no_rkm_medis"));
                            ps2.setString(33,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            ps2.setString(34,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            ps2.setString(35,"%"+TCari.getText().trim()+"%");  
                            ps2.setString(36,"%"+NoRawat.getText()+"%");
                            ps2.setString(37,"%"+kdmem.getText()+"%");
                            ps2.setString(38,"%"+kdptg.getText()+"%");
                            ps2.setString(39,rs.getString("no_rkm_medis"));
                            ps2.setString(40,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                            ps2.setString(41,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                            ps2.setString(42,"%"+TCari.getText().trim()+"%"); 
                        }
                               
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
                            if(NoRawat.getText().equals("")&&kdmem.getText().equals("")&&kdptg.getText().equals("")){
                                ps3=koneksi.prepareStatement(
                                    "select periksa_lab.kd_dokter,dokter.nm_dokter,petugas.nama as nm_petugas,jns_perawatan_lab.nm_perawatan,periksa_lab.kd_jenis_prw from "+
                                    "pasien inner join reg_periksa inner join periksa_lab inner join dokter inner join jns_perawatan_lab inner join petugas on "+
                                    "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=periksa_lab.no_rawat and periksa_lab.nip=petugas.nip "+
                                    "and periksa_lab.dokter_perujuk=dokter.kd_dokter and jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                                    "where periksa_lab.tgl_periksa between ? and ? order by tgl_registrasi");
                            }else{
                                ps3=koneksi.prepareStatement(
                                    "select periksa_lab.kd_dokter,dokter.nm_dokter,petugas.nama as nm_petugas,jns_perawatan_lab.nm_perawatan,periksa_lab.kd_jenis_prw from "+
                                    "pasien inner join reg_periksa inner join periksa_lab inner join dokter inner join jns_perawatan_lab inner join petugas on "+
                                    "pasien.no_rkm_medis=reg_periksa.no_rkm_medis and reg_periksa.no_rawat=periksa_lab.no_rawat and periksa_lab.nip=petugas.nip "+
                                    "and periksa_lab.dokter_perujuk=dokter.kd_dokter and jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                                    "where periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? and periksa_lab.jam=? and periksa_lab.tgl_periksa between ? and ? and pasien.no_rkm_medis like ? or "+
                                    "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? and periksa_lab.jam=? and periksa_lab.tgl_periksa between ? and ? and pasien.nm_pasien like ? or "+
                                    "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? and periksa_lab.jam=? and periksa_lab.tgl_periksa between ? and ? and jns_perawatan_lab.nm_perawatan like ? or "+
                                    "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? and periksa_lab.jam=? and periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? or "+
                                    "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? and periksa_lab.jam=? and periksa_lab.tgl_periksa between ? and ? and petugas.nama like ? or "+
                                    "periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? and periksa_lab.jam=? and periksa_lab.tgl_periksa between ? and ? and dokter.nm_dokter like ? "+
                                    "order by tgl_registrasi");
                            }
                                
                            try {
                                if(NoRawat.getText().equals("")&&kdmem.getText().equals("")&&kdptg.getText().equals("")){
                                    ps3.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    ps3.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                }else{
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
                                    ps3.setString(11,"%"+NoRawat.getText()+"%");
                                    ps3.setString(12,"%"+kdmem.getText()+"%");
                                    ps3.setString(13,"%"+kdptg.getText()+"%");
                                    ps3.setString(14,rs.getString("no_rkm_medis"));
                                    ps3.setString(15,rs2.getString("no_rawat"));
                                    ps3.setString(16,rs2.getString("tgl_periksa"));
                                    ps3.setString(17,rs2.getString("jam"));
                                    ps3.setString(18,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    ps3.setString(19,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(20,"%"+TCari.getText().trim()+"%");
                                    ps3.setString(21,"%"+NoRawat.getText()+"%");
                                    ps3.setString(22,"%"+kdmem.getText()+"%");
                                    ps3.setString(23,"%"+kdptg.getText()+"%");
                                    ps3.setString(24,rs.getString("no_rkm_medis"));
                                    ps3.setString(25,rs2.getString("no_rawat"));
                                    ps3.setString(26,rs2.getString("tgl_periksa"));
                                    ps3.setString(27,rs2.getString("jam"));
                                    ps3.setString(28,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    ps3.setString(29,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(30,"%"+TCari.getText().trim()+"%");
                                    ps3.setString(31,"%"+NoRawat.getText()+"%");
                                    ps3.setString(32,"%"+kdmem.getText()+"%");
                                    ps3.setString(33,"%"+kdptg.getText()+"%");
                                    ps3.setString(34,rs.getString("no_rkm_medis"));
                                    ps3.setString(35,rs2.getString("no_rawat"));
                                    ps3.setString(36,rs2.getString("tgl_periksa"));
                                    ps3.setString(37,rs2.getString("jam"));
                                    ps3.setString(38,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    ps3.setString(39,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(40,"%"+TCari.getText().trim()+"%");
                                    ps3.setString(41,"%"+NoRawat.getText()+"%");
                                    ps3.setString(42,"%"+kdmem.getText()+"%");
                                    ps3.setString(43,"%"+kdptg.getText()+"%");
                                    ps3.setString(44,rs.getString("no_rkm_medis"));
                                    ps3.setString(45,rs2.getString("no_rawat"));
                                    ps3.setString(46,rs2.getString("tgl_periksa"));
                                    ps3.setString(47,rs2.getString("jam"));
                                    ps3.setString(48,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    ps3.setString(49,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(50,"%"+TCari.getText().trim()+"%");
                                    ps3.setString(51,"%"+NoRawat.getText()+"%");
                                    ps3.setString(52,"%"+kdmem.getText()+"%");
                                    ps3.setString(53,"%"+kdptg.getText()+"%");
                                    ps3.setString(54,rs.getString("no_rkm_medis"));
                                    ps3.setString(55,rs2.getString("no_rawat"));
                                    ps3.setString(56,rs2.getString("tgl_periksa"));
                                    ps3.setString(57,rs2.getString("jam"));
                                    ps3.setString(58,Valid.SetTgl(Tgl1.getSelectedItem()+""));
                                    ps3.setString(59,Valid.SetTgl(Tgl2.getSelectedItem()+""));
                                    ps3.setString(60,"%"+TCari.getText().trim()+"%");
                                }
                                    
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
                                            "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,"+
                                            "template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,"+
                                            "detail_periksa_lab.keterangan from detail_periksa_lab inner join "+
                                            "template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                                            "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and "+
                                            "detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=? "+
                                            "order by detail_periksa_lab.kd_jenis_prw,template_laboratorium.urut ");
                                    try {
                                        ps5.setString(1,rs2.getString("no_rawat"));
                                        ps5.setString(2,rs3.getString("kd_jenis_prw"));
                                        ps5.setString(3,rs2.getString("tgl_periksa"));
                                        ps5.setString(4,rs2.getString("jam"));
                                        rs5=ps5.executeQuery();
                                        if(rs5.next()){
                                            htmlContent.append(
                                                "<tr class='isi'>"+
                                                    "<td valign='top' bgcolor='#fdfff9' align='center' width='35%'>Pemeriksaan</td>"+
                                                    "<td valign='top' bgcolor='#fdfff9' align='center' width='15%'>Hasil</td>"+
                                                    "<td valign='top' bgcolor='#fdfff9' align='center' width='10%'>Satuan</td>"+
                                                    "<td valign='top' bgcolor='#fdfff9' align='center' width='20%'>Nilai Rujukan</td>"+
                                                    "<td valign='top' bgcolor='#fdfff9' align='center' width='20%'>Keterangan</td>"+
                                                "</tr>");
                                        }
                                        rs5.beforeFirst();
                                        while(rs5.next()){
                                            htmlContent.append(
                                                "<tr class='isi'>"+
                                                    "<td valign='top'>"+rs5.getString("Pemeriksaan")+"</td>"+
                                                    "<td valign='top' align='center'>"+rs5.getString("nilai")+"</td>"+
                                                    "<td valign='top' align='center'>"+rs5.getString("satuan")+"</td>"+
                                                    "<td valign='top' align='center'>"+rs5.getString("nilai_rujukan")+"</td>"+
                                                    "<td valign='top'>"+rs5.getString("keterangan")+"</td>"+
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
            NoRawat.setText(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
        }
    }
    
    public void isCek(){
        MnCetakHasilLab.setEnabled(akses.getperiksa_lab());
        MnCetakNota.setEnabled(akses.getperiksa_lab());
        MnUbah.setEnabled(akses.getperiksa_lab());
        BtnHapus.setEnabled(akses.getperiksa_lab());
        BtnPrint.setEnabled(akses.getperiksa_lab());
    }
    
    public void setPasien(String pasien){
        NoRawat.setText(pasien);
    }
 

 
}
