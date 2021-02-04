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
import laporan.DlgBerkasRawat;

public class DlgCariPeriksaLabPA extends javax.swing.JDialog {
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
    private boolean sukses=false;
    private double ttl=0,item=0;
    private StringBuilder htmlContent;
    private double ttljmdokter=0,ttljmpetugas=0,ttlkso=0,ttlpendapatan=0,ttlbhp=0,ttljasasarana=0,ttljmperujuk=0,ttlmenejemen=0;
    private String diagnosa="",saran="",kesan="",Suspen_Piutang_Laborat_Ranap="",Laborat_Ranap="",Beban_Jasa_Medik_Dokter_Laborat_Ranap="",Utang_Jasa_Medik_Dokter_Laborat_Ranap="",
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
        panelisi2 = new widget.panelisi();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        PanelAccor = new widget.PanelBiasa();
        ChkAccor = new widget.CekBox();
        FormPhotoPass = new widget.PanelBiasa();
        FormPhoto = new widget.PanelBiasa();
        FormPass2 = new widget.PanelBiasa();
        btnAmbilPhoto = new widget.Button();
        BtnRefreshPhoto = new widget.Button();
        Scroll4 = new widget.ScrollPane();
        LoadHTML = new widget.editorpane();
        FormPass = new widget.PanelBiasa();
        FormPass1 = new widget.PanelBiasa();
        btnUbahPassword = new widget.Button();
        PasswordPasien = new widget.TextArea();
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

        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi2.setLayout(new java.awt.BorderLayout());

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

        panelisi2.add(scrollPane1, java.awt.BorderLayout.CENTER);

        PanelAccor.setBackground(new java.awt.Color(255, 255, 255));
        PanelAccor.setName("PanelAccor"); // NOI18N
        PanelAccor.setPreferredSize(new java.awt.Dimension(245, 43));
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

        FormPhotoPass.setBackground(new java.awt.Color(255, 255, 255));
        FormPhotoPass.setBorder(null);
        FormPhotoPass.setName("FormPhotoPass"); // NOI18N
        FormPhotoPass.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPhotoPass.setLayout(new java.awt.GridLayout(2, 0));

        FormPhoto.setBackground(new java.awt.Color(255, 255, 255));
        FormPhoto.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Photo Pasien : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPhoto.setName("FormPhoto"); // NOI18N
        FormPhoto.setPreferredSize(new java.awt.Dimension(115, 73));
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
        Scroll4.setPreferredSize(new java.awt.Dimension(200, 200));

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N
        Scroll4.setViewportView(LoadHTML);

        FormPhoto.add(Scroll4, java.awt.BorderLayout.CENTER);

        FormPhotoPass.add(FormPhoto);

        FormPass.setBackground(new java.awt.Color(255, 255, 255));
        FormPass.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), " Password EPasien : ", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        FormPass.setName("FormPass"); // NOI18N
        FormPass.setPreferredSize(new java.awt.Dimension(115, 73));
        FormPass.setLayout(new java.awt.BorderLayout());

        FormPass1.setBackground(new java.awt.Color(255, 255, 255));
        FormPass1.setBorder(null);
        FormPass1.setName("FormPass1"); // NOI18N
        FormPass1.setPreferredSize(new java.awt.Dimension(115, 40));

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
        FormPass1.add(btnUbahPassword);

        FormPass.add(FormPass1, java.awt.BorderLayout.PAGE_END);

        PasswordPasien.setColumns(20);
        PasswordPasien.setRows(5);
        PasswordPasien.setName("PasswordPasien"); // NOI18N
        PasswordPasien.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PasswordPasienKeyPressed(evt);
            }
        });
        FormPass.add(PasswordPasien, java.awt.BorderLayout.CENTER);

        FormPhotoPass.add(FormPass);

        PanelAccor.add(FormPhotoPass, java.awt.BorderLayout.CENTER);

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
                    Sequel.AutoComitFalse();
                    sukses=true;
                    status="";
                    ttljmdokter=0;ttljmpetugas=0;ttlkso=0;ttlpendapatan=0;ttlbhp=0;ttljasasarana=0;ttljmperujuk=0;ttlmenejemen=0;
                    ttljmdokter=Sequel.cariIsiAngka("select sum(tarif_tindakan_dokter) from periksa_lab where periksa_lab.kategori='PK' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttljmpetugas=Sequel.cariIsiAngka("select sum(tarif_tindakan_petugas) from periksa_lab where periksa_lab.kategori='PK' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttlkso=Sequel.cariIsiAngka("select sum(kso) from periksa_lab where periksa_lab.kategori='PK' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttlbhp=Sequel.cariIsiAngka("select sum(bhp) from periksa_lab where periksa_lab.kategori='PK' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttlpendapatan=Sequel.cariIsiAngka("select sum(biaya) from periksa_lab where periksa_lab.kategori='PK' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttljasasarana=Sequel.cariIsiAngka("select sum(bagian_rs) from periksa_lab where periksa_lab.kategori='PK' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttljmperujuk=Sequel.cariIsiAngka("select sum(tarif_perujuk) from periksa_lab where periksa_lab.kategori='PK' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttlmenejemen=Sequel.cariIsiAngka("select sum(menejemen) from periksa_lab where periksa_lab.kategori='PK' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    
                    ttljmdokter=ttljmdokter+Sequel.cariIsiAngka("select sum(bagian_dokter) from detail_periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
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
                    ttljasasarana=ttljasasarana+Sequel.cariIsiAngka("select sum(bagian_rs) from detail_periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttljmperujuk=ttljmperujuk+Sequel.cariIsiAngka("select sum(bagian_perujuk) from detail_periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    ttlmenejemen=ttlmenejemen+Sequel.cariIsiAngka("select sum(menejemen) from detail_periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                              "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                              "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");
                    
                    status=Sequel.cariIsi("select status from periksa_lab where periksa_lab.kategori='PK' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                  "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                  "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'");

                    if(Sequel.queryutf("delete from periksa_lab where periksa_lab.kategori='PK' and no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                  "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                  "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'")==true){                    
                        if(Sequel.queryutf("delete from detail_periksa_lab where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +
                                  "' and tgl_periksa='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),3) +
                                  "' and jam='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),4) +"'")==false){
                                sukses=false;                   
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
                            sukses=jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),Sequel.cariIsi("select current_date()"),"U","PEMBATALAN PEMERIKSAAN LABORAT RAWAT INAP PASIEN OLEH "+akses.getkode());  
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
                            sukses=jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),Sequel.cariIsi("select current_date()"),"U","PEMBATALAN PEMERIKSAAN LABORAT RAWAT JALAN PASIEN OLEH "+akses.getkode());  
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
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where periksa_lab.kategori='PK' and "+
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
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.kategori='PK' and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
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
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai").replaceAll("'","`")+"','"+rs3.getString("satuan")
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
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where periksa_lab.kategori='PK' and "+
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
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.kategori='PK' and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
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

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            tampil();
        }else if(TabRawat.getSelectedIndex()==1){
            tampil2();
        }else if(TabRawat.getSelectedIndex()==2){
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
                    "and pasien.kd_kel=kelurahan.kd_kel and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab where periksa_lab.kategori='PK' and "+
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
                            "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.kategori='PK' and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
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
                                        Sequel.menyimpan("temporary_lab","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai").replaceAll("'","`")+"','"+rs3.getString("satuan")
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
        /*switch (TabRawat.getSelectedIndex()) {
            case 1:
            if(tbPasien.getSelectedRow()!= -1){
                isPhoto();
                panggilPhoto();
            }else{
                ChkAccor.setSelected(false);
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
            }
            break;
            case 2:
            if(tbPasien2.getSelectedRow()!= -1){
                isPhoto();
                panggilPhoto();
            }else{
                ChkAccor.setSelected(false);
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
            }
            break;
            case 3:
            if(tbPasien3.getSelectedRow()!= -1){
                isPhoto();
                panggilPhoto();
            }else{
                ChkAccor.setSelected(false);
                JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data yang mau ditampilkan...!!!!");
            }
            break;
            default:
            break;
        }*/
    }//GEN-LAST:event_ChkAccorActionPerformed

    private void btnAmbilPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAmbilPhotoActionPerformed
        /*this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(NoRekamMedisDipilih.getText().equals("")||NamaPasienDipilih.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data pasien terlebih dahulu...!!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Sequel.menyimpan2("personal_pasien","?,'',''",1,new String[]{NoRekamMedisDipilih.getText()});
            Valid.panggilUrl("photopasien/login.php?act=login&usere=admin&passwordte=akusayangsamakamu&norm="+NoRekamMedisDipilih.getText());
            this.setCursor(Cursor.getDefaultCursor());
        }
        this.setCursor(Cursor.getDefaultCursor());*/
    }//GEN-LAST:event_btnAmbilPhotoActionPerformed

    private void BtnRefreshPhotoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnRefreshPhotoActionPerformed
        //panggilPhoto();
    }//GEN-LAST:event_BtnRefreshPhotoActionPerformed

    private void btnUbahPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahPasswordActionPerformed
        /*if(NoRekamMedisDipilih.getText().equals("")||NamaPasienDipilih.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, silahkan pilih data pasien terlebih dahulu...!!!!");
        }else{
            if(Sequel.menyimpantf("personal_pasien","?,'',aes_encrypt(?,'windi')",2,new String[]{NoRekamMedisDipilih.getText(),PasswordPasien.getText()},"no_rkm_medis=?","password=aes_encrypt(?,'windi')",2,new String[]{PasswordPasien.getText(),NoRekamMedisDipilih.getText()})==true){
                JOptionPane.showMessageDialog(null,"Update password pasien berhasil..!!");
            }else{
                JOptionPane.showMessageDialog(null,"Update password pasien gagal..!!");
                PasswordPasien.requestFocus();
            }
        }*/
    }//GEN-LAST:event_btnUbahPasswordActionPerformed

    private void PasswordPasienKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PasswordPasienKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_PasswordPasienKeyPressed

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
    private widget.PanelBiasa FormPass;
    private widget.PanelBiasa FormPass1;
    private widget.PanelBiasa FormPass2;
    private widget.PanelBiasa FormPhoto;
    private widget.PanelBiasa FormPhotoPass;
    private widget.TextBox Kd2;
    private widget.editorpane LoadHTML;
    private widget.editorpane LoadHTML1;
    private javax.swing.JMenuItem MnCetakHasilLab;
    private javax.swing.JMenuItem MnCetakHasilLab11;
    private javax.swing.JMenuItem MnCetakNota;
    private widget.TextBox NoRawat;
    private widget.PanelBiasa PanelAccor;
    private widget.TextArea PasswordPasien;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll4;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
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
    private widget.Label label13;
    private widget.Label label15;
    private widget.Label label16;
    private widget.Label label18;
    private widget.Label label9;
    private widget.TextBox nmmem;
    private widget.TextBox nmptg;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBerkasDigital;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
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
                    "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter where periksa_lab.kategori='PK' and "+
                    "periksa_lab.tgl_periksa between ? and ? group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam) "+
                    "order by periksa_lab.tgl_periksa desc,periksa_lab.jam desc");
            }else{
                ps=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_lab.tgl_periksa,periksa_lab.jam,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,dokter.nm_dokter "+
                    "from periksa_lab inner join reg_periksa on periksa_lab.no_rawat=reg_periksa.no_rawat "+
                    "inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    "inner join petugas on periksa_lab.nip=petugas.nip "+
                    "inner join dokter on periksa_lab.kd_dokter=dokter.kd_dokter where periksa_lab.kategori='PK' and "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and (pasien.nm_pasien like ? or petugas.nama like ? or reg_periksa.no_rkm_medis like ?) group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam) "+
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
                        "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.kategori='PK' and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
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
                                    tabMode.addRow(new Object[]{"","","  "+rs3.getString("Pemeriksaan")+" "+Valid.SetAngka(rs3.getDouble("biaya_item")),rs3.getString("nilai").replaceAll("'","`"),
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
                    "where periksa_lab.kategori='PK' and periksa_lab.tgl_periksa between ? and ? order by periksa_lab.tgl_periksa desc,periksa_lab.jam desc,detail_periksa_lab.kd_jenis_prw,template_laboratorium.urut");
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
                    "where periksa_lab.kategori='PK' and periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and "+
                    "(pasien.nm_pasien like ? or petugas.nama like ? or dokter.nm_dokter like ? or template_laboratorium.Pemeriksaan like ? or jns_perawatan_lab.nm_perawatan like ? or detail_periksa_lab.keterangan like ? or reg_periksa.no_rkm_medis like ?) "+
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
                    ps.setString(7,"%"+TCari.getText().trim()+"%");
                    ps.setString(8,"%"+TCari.getText().trim()+"%");
                    ps.setString(9,"%"+TCari.getText().trim()+"%");
                    ps.setString(10,"%"+TCari.getText().trim()+"%");
                    ps.setString(11,"%"+TCari.getText().trim()+"%");
                    ps.setString(12,"%"+TCari.getText().trim()+"%");
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
                    "pasien.jk from pasien inner join reg_periksa on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                    "inner join periksa_lab on reg_periksa.no_rawat=periksa_lab.no_rawat "+
                    "inner join dokter on periksa_lab.dokter_perujuk=dokter.kd_dokter "+
                    "inner join jns_perawatan_lab on jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                    "inner join petugas on periksa_lab.nip=petugas.nip "+
                    "where periksa_lab.kategori='PK' and periksa_lab.tgl_periksa between ? and ? group by pasien.no_rkm_medis order by tgl_registrasi desc");
            }else{
                ps=koneksi.prepareStatement(
                    "select pasien.no_rkm_medis,pasien.nm_pasien,reg_periksa.umurdaftar,reg_periksa.sttsumur,"+
                    "pasien.jk from pasien inner join reg_periksa on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                    "inner join periksa_lab on reg_periksa.no_rawat=periksa_lab.no_rawat "+
                    "inner join dokter on periksa_lab.dokter_perujuk=dokter.kd_dokter "+
                    "inner join jns_perawatan_lab on jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                    "inner join petugas on periksa_lab.nip=petugas.nip "+
                    "where periksa_lab.kategori='PK' and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and periksa_lab.tgl_periksa between ? and ? and "+
                    "(pasien.no_rkm_medis like ? or pasien.nm_pasien like ? or jns_perawatan_lab.nm_perawatan like ? or periksa_lab.no_rawat like ? or petugas.nama like ? or dokter.nm_dokter like ?) group by pasien.no_rkm_medis order by tgl_registrasi desc");
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
                    
                    ps2=koneksi.prepareStatement(
                        "select periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam,dokter.nm_dokter from "+
                        "pasien inner join reg_periksa on pasien.no_rkm_medis=reg_periksa.no_rkm_medis "+
                        "inner join periksa_lab on reg_periksa.no_rawat=periksa_lab.no_rawat "+
                        "inner join dokter on periksa_lab.dokter_perujuk=dokter.kd_dokter "+
                        "inner join jns_perawatan_lab on jns_perawatan_lab.kd_jenis_prw=periksa_lab.kd_jenis_prw "+
                        "inner join petugas on periksa_lab.nip=petugas.nip "+
                        "where periksa_lab.kategori='PK' and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.tgl_periksa between ? and ? and "+
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
                                "where periksa_lab.kategori='PK' and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? and petugas.nip like ? and reg_periksa.no_rkm_medis=? and periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? and periksa_lab.jam=? and periksa_lab.tgl_periksa between ? and ? and "+
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
 

 
}
