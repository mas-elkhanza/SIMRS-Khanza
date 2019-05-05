package simrskhanza;
import kepegawaian.DlgCariDokter;
import kepegawaian.DlgCariPetugas;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.Jurnal;

public class DlgCariTagihanOperasi extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection  koneksi=koneksiDB.condb();
    private PreparedStatement psrekening;
    private DlgCariPetugas petugas=new DlgCariPetugas( null,false);
    private DlgCariDokter dokter=new DlgCariDokter(null,false);
    private ResultSet rsrekening,rs,rs2,rs3;
    private int pilihan=0;
    private double ttljmdokter=0,ttljmpetugas=0,ttlpendapatan=0,ttlbhp=0;
    private String norm="",kamar="",namakamar="",Suspen_Piutang_Operasi_Ranap="",Operasi_Ranap="",Beban_Jasa_Medik_Dokter_Operasi_Ranap="",
            Utang_Jasa_Medik_Dokter_Operasi_Ranap="",Beban_Jasa_Medik_Paramedis_Operasi_Ranap="",
            Utang_Jasa_Medik_Paramedis_Operasi_Ranap="",HPP_Obat_Operasi_Ranap="",Persediaan_Obat_Kamar_Operasi_Ranap="",status="";

    /** Creates new form DlgProgramStudi
     * @param parent
     * @param modal */
    public DlgCariTagihanOperasi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        Object[] row={"Tgl.Operasi",
                      "No.Rawat",
                      "Pasien",
                      "Jns.Ans","","","","","","","","","","","","","","","","","","","","","","","","","","","","","",""};
        tabMode=new DefaultTableModel(null,row){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < 34; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(120);
            }else if(i==1){
                column.setPreferredWidth(120);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(200);
            }else{
                column.setPreferredWidth(130);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        kdmem.setDocument(new batasInput((byte)10).getKata(kdmem));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.cariCepat().equals("aktif")){
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
                if(akses.getform().equals("DlgCariTagihanOperasi")){
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
                if(akses.getform().equals("DlgCariTagihanOperasi")){
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
                if(akses.getform().equals("DlgCariTagihanOperasi")){
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
                if(akses.getform().equals("DlgCariTagihanOperasi")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        member.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dokter.getTable().getSelectedRow()!= -1){                    
                    if(pilihan==1){
                        kdoperator1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmoperator1.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kdoperator1.requestFocus();
                    }else if(pilihan==2){
                        kdoperator2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmoperator2.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kdoperator2.requestFocus();
                    }else if(pilihan==3){
                        kdoperator3.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmoperator3.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kdoperator3.requestFocus();
                    }else if(pilihan==4){
                        kdanestesi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmanestesi.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kdanestesi.requestFocus();
                    }else if(pilihan==5){
                        kddranak.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmdranak.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kddranak.requestFocus();
                    }else if(pilihan==6){
                        kdpjanak.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmpjanak.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kdpjanak.requestFocus();
                    }else if(pilihan==7){
                        kddrumum.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                        nmdrumum.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                        kddrumum.requestFocus();
                    }                   
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
        
        petugas.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(petugas.getTable().getSelectedRow()!= -1){    
                    if(pilihan==1){
                        kdasistoperator1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmasistoperator1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdasistoperator1.requestFocus();
                    }else if(pilihan==2){
                        kdasistoperator2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmasistoperator2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdasistoperator2.requestFocus();
                    }else if(pilihan==3){
                        kdInstrumen.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nminstrumen.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdInstrumen.requestFocus();
                    }else if(pilihan==4){
                        kdasistanestesi.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmasistanestesi.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdasistanestesi.requestFocus();
                    }else if(pilihan==5){
                        kdprwresust.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmprwresust.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdprwresust.requestFocus();
                    }else if(pilihan==6){
                        kdprwluar.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmprwluar.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdprwluar.requestFocus();
                    }else if(pilihan==7){
                        kdbidan.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmbidan.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdbidan.requestFocus();
                    }else if(pilihan==8){
                        kdbidan2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmbidan2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdbidan2.requestFocus();
                    }else if(pilihan==9){
                        kdbidan3.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmbidan3.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdbidan3.requestFocus();
                    }else if(pilihan==10){
                        kdonloop1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmonloop1.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdonloop1.requestFocus();
                    }else if(pilihan==11){
                        kdonloop2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmonloop2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdonloop2.requestFocus();
                    }else if(pilihan==12){
                        kdonloop3.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmonloop3.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdonloop3.requestFocus();
                    }else if(pilihan==13){
                        kdonloop4.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmonloop4.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdonloop4.requestFocus();
                    }else if(pilihan==14){
                        kdonloop5.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmonloop5.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdonloop5.requestFocus();
                    }else if(pilihan==15){
                        kdasistoperator3.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmasistoperator3.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdasistoperator3.requestFocus();
                    }else if(pilihan==16){
                        kdasistanestesi2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),0).toString());
                        nmasistanestesi2.setText(petugas.getTable().getValueAt(petugas.getTable().getSelectedRow(),1).toString());
                        kdasistanestesi2.requestFocus();
                    }               
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
                    Suspen_Piutang_Operasi_Ranap=rsrekening.getString("Suspen_Piutang_Operasi_Ranap");
                    Operasi_Ranap=rsrekening.getString("Operasi_Ranap");
                    Beban_Jasa_Medik_Dokter_Operasi_Ranap=rsrekening.getString("Beban_Jasa_Medik_Dokter_Operasi_Ranap");
                    Utang_Jasa_Medik_Dokter_Operasi_Ranap=rsrekening.getString("Utang_Jasa_Medik_Dokter_Operasi_Ranap");
                    Beban_Jasa_Medik_Paramedis_Operasi_Ranap=rsrekening.getString("Beban_Jasa_Medik_Paramedis_Operasi_Ranap");
                    Utang_Jasa_Medik_Paramedis_Operasi_Ranap=rsrekening.getString("Utang_Jasa_Medik_Paramedis_Operasi_Ranap");
                    HPP_Obat_Operasi_Ranap=rsrekening.getString("HPP_Obat_Operasi_Ranap");
                    Persediaan_Obat_Kamar_Operasi_Ranap=rsrekening.getString("Persediaan_Obat_Kamar_Operasi_Ranap");
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
    }
    private DlgPasien member=new DlgPasien(null,false);
    private double total=0;
    private int no=0;

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
        MnHapusObatOperasi = new javax.swing.JMenuItem();
        MnHapusTagihanOperasi = new javax.swing.JMenuItem();
        MnUbahOperatorPetugas = new javax.swing.JMenuItem();
        WindowGantiDokterParamedis = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        scrollPane2 = new widget.ScrollPane();
        FormInput = new widget.panelisi();
        label14 = new widget.Label();
        kdoperator1 = new widget.TextBox();
        nmoperator1 = new widget.TextBox();
        BtnOperator1 = new widget.Button();
        label17 = new widget.Label();
        kdasistoperator1 = new widget.TextBox();
        nmasistoperator1 = new widget.TextBox();
        btnAsis1 = new widget.Button();
        label19 = new widget.Label();
        kdoperator2 = new widget.TextBox();
        nmoperator2 = new widget.TextBox();
        BtnOperator2 = new widget.Button();
        label20 = new widget.Label();
        kdoperator3 = new widget.TextBox();
        nmoperator3 = new widget.TextBox();
        btnInstrumen = new widget.Button();
        label21 = new widget.Label();
        kdanestesi = new widget.TextBox();
        nmanestesi = new widget.TextBox();
        BtnAnastesi = new widget.Button();
        label22 = new widget.Label();
        kddranak = new widget.TextBox();
        nmdranak = new widget.TextBox();
        btnAnak = new widget.Button();
        btnAsis2 = new widget.Button();
        nmasistoperator2 = new widget.TextBox();
        kdasistoperator2 = new widget.TextBox();
        label23 = new widget.Label();
        btnAsis3 = new widget.Button();
        nminstrumen = new widget.TextBox();
        kdInstrumen = new widget.TextBox();
        label24 = new widget.Label();
        btnPrwRes = new widget.Button();
        nmprwresust = new widget.TextBox();
        kdprwresust = new widget.TextBox();
        label25 = new widget.Label();
        label26 = new widget.Label();
        kdasistanestesi = new widget.TextBox();
        nmasistanestesi = new widget.TextBox();
        BtnAsnes = new widget.Button();
        label27 = new widget.Label();
        kdbidan = new widget.TextBox();
        nmbidan = new widget.TextBox();
        btnBidan = new widget.Button();
        label28 = new widget.Label();
        kdprwluar = new widget.TextBox();
        nmprwluar = new widget.TextBox();
        btnPrwLuar = new widget.Button();
        btnBidan2 = new widget.Button();
        nmbidan2 = new widget.TextBox();
        kdbidan2 = new widget.TextBox();
        label29 = new widget.Label();
        label30 = new widget.Label();
        kdbidan3 = new widget.TextBox();
        nmbidan3 = new widget.TextBox();
        btnBidan3 = new widget.Button();
        label31 = new widget.Label();
        kdonloop1 = new widget.TextBox();
        nmonloop1 = new widget.TextBox();
        btnOnloop1 = new widget.Button();
        btnOnloop2 = new widget.Button();
        nmonloop2 = new widget.TextBox();
        kdonloop2 = new widget.TextBox();
        label32 = new widget.Label();
        label33 = new widget.Label();
        btnOnloop3 = new widget.Button();
        nmonloop3 = new widget.TextBox();
        kdonloop3 = new widget.TextBox();
        label34 = new widget.Label();
        kdpjanak = new widget.TextBox();
        nmpjanak = new widget.TextBox();
        btndrpjanak = new widget.Button();
        label35 = new widget.Label();
        kddrumum = new widget.TextBox();
        nmdrumum = new widget.TextBox();
        btndrumum = new widget.Button();
        label36 = new widget.Label();
        kdasistoperator3 = new widget.TextBox();
        nmasistoperator3 = new widget.TextBox();
        btnAsis4 = new widget.Button();
        label37 = new widget.Label();
        kdasistanestesi2 = new widget.TextBox();
        nmasistanestesi2 = new widget.TextBox();
        BtnAsnes1 = new widget.Button();
        label38 = new widget.Label();
        kdonloop4 = new widget.TextBox();
        nmonloop4 = new widget.TextBox();
        btnOnloop4 = new widget.Button();
        btnOnloop5 = new widget.Button();
        nmonloop5 = new widget.TextBox();
        kdonloop5 = new widget.TextBox();
        label39 = new widget.Label();
        BtnSimpan4 = new widget.Button();
        BtnCloseIn4 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        panelisi3 = new widget.panelisi();
        label11 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label13 = new widget.Label();
        kdmem = new widget.TextBox();
        nmmem = new widget.TextBox();
        BtnCari5 = new widget.Button();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label15 = new widget.Label();
        NoRawat = new widget.TextBox();
        panelisi1 = new widget.panelisi();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label9 = new widget.Label();
        LTotal = new widget.Label();
        BtnAll = new widget.Button();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnHapusObatOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusObatOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusObatOperasi.setForeground(new java.awt.Color(70, 70, 70));
        MnHapusObatOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusObatOperasi.setText("Hapus Obat Operasi");
        MnHapusObatOperasi.setName("MnHapusObatOperasi"); // NOI18N
        MnHapusObatOperasi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusObatOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusObatOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHapusObatOperasi);

        MnHapusTagihanOperasi.setBackground(new java.awt.Color(255, 255, 254));
        MnHapusTagihanOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnHapusTagihanOperasi.setForeground(new java.awt.Color(70, 70, 70));
        MnHapusTagihanOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnHapusTagihanOperasi.setText("Hapus Tagihan Operasi");
        MnHapusTagihanOperasi.setName("MnHapusTagihanOperasi"); // NOI18N
        MnHapusTagihanOperasi.setPreferredSize(new java.awt.Dimension(220, 26));
        MnHapusTagihanOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnHapusTagihanOperasiActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnHapusTagihanOperasi);

        MnUbahOperatorPetugas.setBackground(new java.awt.Color(255, 255, 254));
        MnUbahOperatorPetugas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUbahOperatorPetugas.setForeground(new java.awt.Color(70, 70, 70));
        MnUbahOperatorPetugas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUbahOperatorPetugas.setText("Ubah Operator & Paramedis");
        MnUbahOperatorPetugas.setName("MnUbahOperatorPetugas"); // NOI18N
        MnUbahOperatorPetugas.setPreferredSize(new java.awt.Dimension(220, 26));
        MnUbahOperatorPetugas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUbahOperatorPetugasActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUbahOperatorPetugas);

        WindowGantiDokterParamedis.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGantiDokterParamedis.setName("WindowGantiDokterParamedis"); // NOI18N
        WindowGantiDokterParamedis.setUndecorated(true);
        WindowGantiDokterParamedis.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ubah Operator & Paramedis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setWarnaBawah(new java.awt.Color(240, 245, 235));
        internalFrame5.setLayout(new java.awt.BorderLayout());

        scrollPane2.setBorder(null);
        scrollPane2.setName("scrollPane2"); // NOI18N

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(89, 434));
        FormInput.setLayout(null);

        label14.setText("Operator 1 :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label14);
        label14.setBounds(0, 10, 82, 23);

        kdoperator1.setName("kdoperator1"); // NOI18N
        kdoperator1.setPreferredSize(new java.awt.Dimension(80, 23));
        kdoperator1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdoperator1KeyPressed(evt);
            }
        });
        FormInput.add(kdoperator1);
        kdoperator1.setBounds(84, 10, 100, 23);

        nmoperator1.setEditable(false);
        nmoperator1.setName("nmoperator1"); // NOI18N
        nmoperator1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmoperator1);
        nmoperator1.setBounds(185, 10, 190, 23);

        BtnOperator1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperator1.setMnemonic('2');
        BtnOperator1.setToolTipText("Alt+2");
        BtnOperator1.setName("BtnOperator1"); // NOI18N
        BtnOperator1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnOperator1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperator1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnOperator1);
        BtnOperator1.setBounds(376, 10, 28, 23);

        label17.setText("Ast. Operator 1 :");
        label17.setName("label17"); // NOI18N
        label17.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label17);
        label17.setBounds(406, 10, 102, 23);

        kdasistoperator1.setName("kdasistoperator1"); // NOI18N
        kdasistoperator1.setPreferredSize(new java.awt.Dimension(80, 23));
        kdasistoperator1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdasistoperator1KeyPressed(evt);
            }
        });
        FormInput.add(kdasistoperator1);
        kdasistoperator1.setBounds(510, 10, 100, 23);

        nmasistoperator1.setEditable(false);
        nmasistoperator1.setName("nmasistoperator1"); // NOI18N
        nmasistoperator1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmasistoperator1);
        nmasistoperator1.setBounds(611, 10, 190, 23);

        btnAsis1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAsis1.setMnemonic('2');
        btnAsis1.setToolTipText("Alt+2");
        btnAsis1.setName("btnAsis1"); // NOI18N
        btnAsis1.setPreferredSize(new java.awt.Dimension(28, 23));
        btnAsis1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsis1ActionPerformed(evt);
            }
        });
        FormInput.add(btnAsis1);
        btnAsis1.setBounds(802, 10, 28, 23);

        label19.setText("Operator 2 :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label19);
        label19.setBounds(0, 40, 82, 23);

        kdoperator2.setName("kdoperator2"); // NOI18N
        kdoperator2.setPreferredSize(new java.awt.Dimension(80, 23));
        kdoperator2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdoperator2KeyPressed(evt);
            }
        });
        FormInput.add(kdoperator2);
        kdoperator2.setBounds(84, 40, 100, 23);

        nmoperator2.setEditable(false);
        nmoperator2.setName("nmoperator2"); // NOI18N
        nmoperator2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmoperator2);
        nmoperator2.setBounds(185, 40, 190, 23);

        BtnOperator2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnOperator2.setMnemonic('2');
        BtnOperator2.setToolTipText("Alt+2");
        BtnOperator2.setName("BtnOperator2"); // NOI18N
        BtnOperator2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnOperator2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnOperator2ActionPerformed(evt);
            }
        });
        FormInput.add(BtnOperator2);
        BtnOperator2.setBounds(376, 40, 28, 23);

        label20.setText("Operator 3 :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label20);
        label20.setBounds(0, 70, 82, 23);

        kdoperator3.setName("kdoperator3"); // NOI18N
        kdoperator3.setPreferredSize(new java.awt.Dimension(80, 23));
        kdoperator3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdoperator3KeyPressed(evt);
            }
        });
        FormInput.add(kdoperator3);
        kdoperator3.setBounds(84, 70, 100, 23);

        nmoperator3.setEditable(false);
        nmoperator3.setName("nmoperator3"); // NOI18N
        nmoperator3.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmoperator3);
        nmoperator3.setBounds(185, 70, 190, 23);

        btnInstrumen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnInstrumen.setMnemonic('2');
        btnInstrumen.setToolTipText("Alt+2");
        btnInstrumen.setName("btnInstrumen"); // NOI18N
        btnInstrumen.setPreferredSize(new java.awt.Dimension(28, 23));
        btnInstrumen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInstrumenActionPerformed(evt);
            }
        });
        FormInput.add(btnInstrumen);
        btnInstrumen.setBounds(376, 70, 28, 23);

        label21.setText("dr Anestesi :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(0, 100, 82, 23);

        kdanestesi.setName("kdanestesi"); // NOI18N
        kdanestesi.setPreferredSize(new java.awt.Dimension(80, 23));
        kdanestesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdanestesiKeyPressed(evt);
            }
        });
        FormInput.add(kdanestesi);
        kdanestesi.setBounds(84, 100, 100, 23);

        nmanestesi.setEditable(false);
        nmanestesi.setName("nmanestesi"); // NOI18N
        nmanestesi.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmanestesi);
        nmanestesi.setBounds(185, 100, 190, 23);

        BtnAnastesi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnAnastesi.setMnemonic('2');
        BtnAnastesi.setToolTipText("Alt+2");
        BtnAnastesi.setName("BtnAnastesi"); // NOI18N
        BtnAnastesi.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAnastesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAnastesiActionPerformed(evt);
            }
        });
        FormInput.add(BtnAnastesi);
        BtnAnastesi.setBounds(376, 100, 28, 23);

        label22.setText("dr Anak :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label22);
        label22.setBounds(0, 130, 82, 23);

        kddranak.setName("kddranak"); // NOI18N
        kddranak.setPreferredSize(new java.awt.Dimension(80, 23));
        kddranak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddranakKeyPressed(evt);
            }
        });
        FormInput.add(kddranak);
        kddranak.setBounds(84, 130, 100, 23);

        nmdranak.setEditable(false);
        nmdranak.setName("nmdranak"); // NOI18N
        nmdranak.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmdranak);
        nmdranak.setBounds(185, 130, 190, 23);

        btnAnak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAnak.setMnemonic('2');
        btnAnak.setToolTipText("Alt+2");
        btnAnak.setName("btnAnak"); // NOI18N
        btnAnak.setPreferredSize(new java.awt.Dimension(28, 23));
        btnAnak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnakActionPerformed(evt);
            }
        });
        FormInput.add(btnAnak);
        btnAnak.setBounds(376, 130, 28, 23);

        btnAsis2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAsis2.setMnemonic('2');
        btnAsis2.setToolTipText("Alt+2");
        btnAsis2.setName("btnAsis2"); // NOI18N
        btnAsis2.setPreferredSize(new java.awt.Dimension(28, 23));
        btnAsis2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsis2ActionPerformed(evt);
            }
        });
        FormInput.add(btnAsis2);
        btnAsis2.setBounds(802, 40, 28, 23);

        nmasistoperator2.setEditable(false);
        nmasistoperator2.setName("nmasistoperator2"); // NOI18N
        nmasistoperator2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmasistoperator2);
        nmasistoperator2.setBounds(611, 40, 190, 23);

        kdasistoperator2.setName("kdasistoperator2"); // NOI18N
        kdasistoperator2.setPreferredSize(new java.awt.Dimension(80, 23));
        kdasistoperator2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdasistoperator2KeyPressed(evt);
            }
        });
        FormInput.add(kdasistoperator2);
        kdasistoperator2.setBounds(510, 40, 100, 23);

        label23.setText("Ast. Operator 2 :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label23);
        label23.setBounds(406, 40, 102, 23);

        btnAsis3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAsis3.setMnemonic('2');
        btnAsis3.setToolTipText("Alt+2");
        btnAsis3.setName("btnAsis3"); // NOI18N
        btnAsis3.setPreferredSize(new java.awt.Dimension(28, 23));
        btnAsis3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsis3ActionPerformed(evt);
            }
        });
        FormInput.add(btnAsis3);
        btnAsis3.setBounds(376, 280, 28, 23);

        nminstrumen.setEditable(false);
        nminstrumen.setName("nminstrumen"); // NOI18N
        nminstrumen.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nminstrumen);
        nminstrumen.setBounds(185, 280, 190, 23);

        kdInstrumen.setName("kdInstrumen"); // NOI18N
        kdInstrumen.setPreferredSize(new java.awt.Dimension(80, 23));
        kdInstrumen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdInstrumenKeyPressed(evt);
            }
        });
        FormInput.add(kdInstrumen);
        kdInstrumen.setBounds(84, 280, 100, 23);

        label24.setText("Instrumen :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label24);
        label24.setBounds(0, 280, 82, 23);

        btnPrwRes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPrwRes.setMnemonic('2');
        btnPrwRes.setToolTipText("Alt+2");
        btnPrwRes.setName("btnPrwRes"); // NOI18N
        btnPrwRes.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPrwRes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrwResActionPerformed(evt);
            }
        });
        FormInput.add(btnPrwRes);
        btnPrwRes.setBounds(802, 160, 28, 23);

        nmprwresust.setEditable(false);
        nmprwresust.setName("nmprwresust"); // NOI18N
        nmprwresust.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmprwresust);
        nmprwresust.setBounds(611, 160, 190, 23);

        kdprwresust.setName("kdprwresust"); // NOI18N
        kdprwresust.setPreferredSize(new java.awt.Dimension(80, 23));
        kdprwresust.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdprwresustKeyPressed(evt);
            }
        });
        FormInput.add(kdprwresust);
        kdprwresust.setBounds(510, 160, 100, 23);

        label25.setText("Prw.Resusitasi :");
        label25.setName("label25"); // NOI18N
        label25.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label25);
        label25.setBounds(406, 160, 102, 23);

        label26.setText("Ast. Anestesi 1 :");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label26);
        label26.setBounds(406, 100, 102, 23);

        kdasistanestesi.setName("kdasistanestesi"); // NOI18N
        kdasistanestesi.setPreferredSize(new java.awt.Dimension(80, 23));
        kdasistanestesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdasistanestesiKeyPressed(evt);
            }
        });
        FormInput.add(kdasistanestesi);
        kdasistanestesi.setBounds(510, 100, 100, 23);

        nmasistanestesi.setEditable(false);
        nmasistanestesi.setName("nmasistanestesi"); // NOI18N
        nmasistanestesi.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmasistanestesi);
        nmasistanestesi.setBounds(611, 100, 190, 23);

        BtnAsnes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnAsnes.setMnemonic('2');
        BtnAsnes.setToolTipText("Alt+2");
        BtnAsnes.setName("BtnAsnes"); // NOI18N
        BtnAsnes.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAsnes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAsnesActionPerformed(evt);
            }
        });
        FormInput.add(BtnAsnes);
        BtnAsnes.setBounds(802, 100, 28, 23);

        label27.setText("Bidan 1 :");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label27);
        label27.setBounds(0, 160, 82, 23);

        kdbidan.setName("kdbidan"); // NOI18N
        kdbidan.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbidan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbidanKeyPressed(evt);
            }
        });
        FormInput.add(kdbidan);
        kdbidan.setBounds(84, 160, 100, 23);

        nmbidan.setEditable(false);
        nmbidan.setName("nmbidan"); // NOI18N
        nmbidan.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmbidan);
        nmbidan.setBounds(185, 160, 190, 23);

        btnBidan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBidan.setMnemonic('2');
        btnBidan.setToolTipText("Alt+2");
        btnBidan.setName("btnBidan"); // NOI18N
        btnBidan.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBidan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBidanActionPerformed(evt);
            }
        });
        FormInput.add(btnBidan);
        btnBidan.setBounds(376, 160, 28, 23);

        label28.setText("Prwat Luar :");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label28);
        label28.setBounds(0, 250, 82, 23);

        kdprwluar.setName("kdprwluar"); // NOI18N
        kdprwluar.setPreferredSize(new java.awt.Dimension(80, 23));
        kdprwluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdprwluarKeyPressed(evt);
            }
        });
        FormInput.add(kdprwluar);
        kdprwluar.setBounds(84, 250, 100, 23);

        nmprwluar.setEditable(false);
        nmprwluar.setName("nmprwluar"); // NOI18N
        nmprwluar.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmprwluar);
        nmprwluar.setBounds(185, 250, 190, 23);

        btnPrwLuar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPrwLuar.setMnemonic('2');
        btnPrwLuar.setToolTipText("Alt+2");
        btnPrwLuar.setName("btnPrwLuar"); // NOI18N
        btnPrwLuar.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPrwLuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrwLuarActionPerformed(evt);
            }
        });
        FormInput.add(btnPrwLuar);
        btnPrwLuar.setBounds(376, 250, 28, 23);

        btnBidan2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBidan2.setMnemonic('2');
        btnBidan2.setToolTipText("Alt+2");
        btnBidan2.setName("btnBidan2"); // NOI18N
        btnBidan2.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBidan2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBidan2ActionPerformed(evt);
            }
        });
        FormInput.add(btnBidan2);
        btnBidan2.setBounds(376, 190, 28, 23);

        nmbidan2.setEditable(false);
        nmbidan2.setName("nmbidan2"); // NOI18N
        nmbidan2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmbidan2);
        nmbidan2.setBounds(185, 190, 190, 23);

        kdbidan2.setName("kdbidan2"); // NOI18N
        kdbidan2.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbidan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbidan2KeyPressed(evt);
            }
        });
        FormInput.add(kdbidan2);
        kdbidan2.setBounds(84, 190, 100, 23);

        label29.setText("Bidan 2 :");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label29);
        label29.setBounds(0, 190, 82, 23);

        label30.setText("Bidan 3 :");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label30);
        label30.setBounds(0, 220, 82, 23);

        kdbidan3.setName("kdbidan3"); // NOI18N
        kdbidan3.setPreferredSize(new java.awt.Dimension(80, 23));
        kdbidan3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdbidan3KeyPressed(evt);
            }
        });
        FormInput.add(kdbidan3);
        kdbidan3.setBounds(84, 220, 100, 23);

        nmbidan3.setEditable(false);
        nmbidan3.setName("nmbidan3"); // NOI18N
        nmbidan3.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmbidan3);
        nmbidan3.setBounds(185, 220, 190, 23);

        btnBidan3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBidan3.setMnemonic('2');
        btnBidan3.setToolTipText("Alt+2");
        btnBidan3.setName("btnBidan3"); // NOI18N
        btnBidan3.setPreferredSize(new java.awt.Dimension(28, 23));
        btnBidan3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBidan3ActionPerformed(evt);
            }
        });
        FormInput.add(btnBidan3);
        btnBidan3.setBounds(376, 220, 28, 23);

        label31.setText("Onloop 1 :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label31);
        label31.setBounds(406, 190, 102, 23);

        kdonloop1.setName("kdonloop1"); // NOI18N
        kdonloop1.setPreferredSize(new java.awt.Dimension(80, 23));
        kdonloop1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdonloop1KeyPressed(evt);
            }
        });
        FormInput.add(kdonloop1);
        kdonloop1.setBounds(510, 190, 100, 23);

        nmonloop1.setEditable(false);
        nmonloop1.setName("nmonloop1"); // NOI18N
        nmonloop1.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmonloop1);
        nmonloop1.setBounds(611, 190, 190, 23);

        btnOnloop1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnOnloop1.setMnemonic('2');
        btnOnloop1.setToolTipText("Alt+2");
        btnOnloop1.setName("btnOnloop1"); // NOI18N
        btnOnloop1.setPreferredSize(new java.awt.Dimension(28, 23));
        btnOnloop1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnloop1ActionPerformed(evt);
            }
        });
        FormInput.add(btnOnloop1);
        btnOnloop1.setBounds(802, 190, 28, 23);

        btnOnloop2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnOnloop2.setMnemonic('2');
        btnOnloop2.setToolTipText("Alt+2");
        btnOnloop2.setName("btnOnloop2"); // NOI18N
        btnOnloop2.setPreferredSize(new java.awt.Dimension(28, 23));
        btnOnloop2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnloop2ActionPerformed(evt);
            }
        });
        FormInput.add(btnOnloop2);
        btnOnloop2.setBounds(802, 220, 28, 23);

        nmonloop2.setEditable(false);
        nmonloop2.setName("nmonloop2"); // NOI18N
        nmonloop2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmonloop2);
        nmonloop2.setBounds(611, 220, 190, 23);

        kdonloop2.setName("kdonloop2"); // NOI18N
        kdonloop2.setPreferredSize(new java.awt.Dimension(80, 23));
        kdonloop2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdonloop2KeyPressed(evt);
            }
        });
        FormInput.add(kdonloop2);
        kdonloop2.setBounds(510, 220, 100, 23);

        label32.setText("Onloop 2 :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label32);
        label32.setBounds(406, 220, 102, 23);

        label33.setText("Onloop 3 :");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label33);
        label33.setBounds(406, 250, 102, 23);

        btnOnloop3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnOnloop3.setMnemonic('2');
        btnOnloop3.setToolTipText("Alt+2");
        btnOnloop3.setName("btnOnloop3"); // NOI18N
        btnOnloop3.setPreferredSize(new java.awt.Dimension(28, 23));
        btnOnloop3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnloop3ActionPerformed(evt);
            }
        });
        FormInput.add(btnOnloop3);
        btnOnloop3.setBounds(802, 250, 28, 23);

        nmonloop3.setEditable(false);
        nmonloop3.setName("nmonloop3"); // NOI18N
        nmonloop3.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmonloop3);
        nmonloop3.setBounds(611, 250, 190, 23);

        kdonloop3.setName("kdonloop3"); // NOI18N
        kdonloop3.setPreferredSize(new java.awt.Dimension(80, 23));
        kdonloop3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdonloop3KeyPressed(evt);
            }
        });
        FormInput.add(kdonloop3);
        kdonloop3.setBounds(510, 250, 100, 23);

        label34.setText("dr Pj. Anak :");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label34);
        label34.setBounds(0, 310, 82, 23);

        kdpjanak.setName("kdpjanak"); // NOI18N
        kdpjanak.setPreferredSize(new java.awt.Dimension(80, 23));
        kdpjanak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpjanakKeyPressed(evt);
            }
        });
        FormInput.add(kdpjanak);
        kdpjanak.setBounds(84, 310, 100, 23);

        nmpjanak.setEditable(false);
        nmpjanak.setName("nmpjanak"); // NOI18N
        nmpjanak.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmpjanak);
        nmpjanak.setBounds(185, 310, 190, 23);

        btndrpjanak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btndrpjanak.setMnemonic('2');
        btndrpjanak.setToolTipText("Alt+2");
        btndrpjanak.setName("btndrpjanak"); // NOI18N
        btndrpjanak.setPreferredSize(new java.awt.Dimension(28, 23));
        btndrpjanak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndrpjanakActionPerformed(evt);
            }
        });
        FormInput.add(btndrpjanak);
        btndrpjanak.setBounds(376, 310, 28, 23);

        label35.setText("dr Umum :");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label35);
        label35.setBounds(0, 340, 82, 23);

        kddrumum.setName("kddrumum"); // NOI18N
        kddrumum.setPreferredSize(new java.awt.Dimension(80, 23));
        kddrumum.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddrumumKeyPressed(evt);
            }
        });
        FormInput.add(kddrumum);
        kddrumum.setBounds(84, 340, 100, 23);

        nmdrumum.setEditable(false);
        nmdrumum.setName("nmdrumum"); // NOI18N
        nmdrumum.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmdrumum);
        nmdrumum.setBounds(185, 340, 190, 23);

        btndrumum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btndrumum.setMnemonic('2');
        btndrumum.setToolTipText("Alt+2");
        btndrumum.setName("btndrumum"); // NOI18N
        btndrumum.setPreferredSize(new java.awt.Dimension(28, 23));
        btndrumum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btndrumumActionPerformed(evt);
            }
        });
        FormInput.add(btndrumum);
        btndrumum.setBounds(376, 340, 28, 23);

        label36.setText("Ast. Operator 3 :");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label36);
        label36.setBounds(406, 70, 102, 23);

        kdasistoperator3.setName("kdasistoperator3"); // NOI18N
        kdasistoperator3.setPreferredSize(new java.awt.Dimension(80, 23));
        kdasistoperator3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdasistoperator3KeyPressed(evt);
            }
        });
        FormInput.add(kdasistoperator3);
        kdasistoperator3.setBounds(510, 70, 100, 23);

        nmasistoperator3.setEditable(false);
        nmasistoperator3.setName("nmasistoperator3"); // NOI18N
        nmasistoperator3.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmasistoperator3);
        nmasistoperator3.setBounds(611, 70, 190, 23);

        btnAsis4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnAsis4.setMnemonic('2');
        btnAsis4.setToolTipText("Alt+2");
        btnAsis4.setName("btnAsis4"); // NOI18N
        btnAsis4.setPreferredSize(new java.awt.Dimension(28, 23));
        btnAsis4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAsis4ActionPerformed(evt);
            }
        });
        FormInput.add(btnAsis4);
        btnAsis4.setBounds(802, 70, 28, 23);

        label37.setText("Ast. Anestesi 2 :");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label37);
        label37.setBounds(406, 130, 102, 23);

        kdasistanestesi2.setName("kdasistanestesi2"); // NOI18N
        kdasistanestesi2.setPreferredSize(new java.awt.Dimension(80, 23));
        kdasistanestesi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdasistanestesi2KeyPressed(evt);
            }
        });
        FormInput.add(kdasistanestesi2);
        kdasistanestesi2.setBounds(510, 130, 100, 23);

        nmasistanestesi2.setEditable(false);
        nmasistanestesi2.setName("nmasistanestesi2"); // NOI18N
        nmasistanestesi2.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmasistanestesi2);
        nmasistanestesi2.setBounds(611, 130, 190, 23);

        BtnAsnes1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnAsnes1.setMnemonic('2');
        BtnAsnes1.setToolTipText("Alt+2");
        BtnAsnes1.setName("BtnAsnes1"); // NOI18N
        BtnAsnes1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAsnes1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAsnes1ActionPerformed(evt);
            }
        });
        FormInput.add(BtnAsnes1);
        BtnAsnes1.setBounds(802, 130, 28, 23);

        label38.setText("Onloop 4 :");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label38);
        label38.setBounds(406, 280, 102, 23);

        kdonloop4.setName("kdonloop4"); // NOI18N
        kdonloop4.setPreferredSize(new java.awt.Dimension(80, 23));
        kdonloop4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdonloop4KeyPressed(evt);
            }
        });
        FormInput.add(kdonloop4);
        kdonloop4.setBounds(510, 280, 100, 23);

        nmonloop4.setEditable(false);
        nmonloop4.setName("nmonloop4"); // NOI18N
        nmonloop4.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmonloop4);
        nmonloop4.setBounds(611, 280, 190, 23);

        btnOnloop4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnOnloop4.setMnemonic('2');
        btnOnloop4.setToolTipText("Alt+2");
        btnOnloop4.setName("btnOnloop4"); // NOI18N
        btnOnloop4.setPreferredSize(new java.awt.Dimension(28, 23));
        btnOnloop4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnloop4ActionPerformed(evt);
            }
        });
        FormInput.add(btnOnloop4);
        btnOnloop4.setBounds(802, 280, 28, 23);

        btnOnloop5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnOnloop5.setMnemonic('2');
        btnOnloop5.setToolTipText("Alt+2");
        btnOnloop5.setName("btnOnloop5"); // NOI18N
        btnOnloop5.setPreferredSize(new java.awt.Dimension(28, 23));
        btnOnloop5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnloop5ActionPerformed(evt);
            }
        });
        FormInput.add(btnOnloop5);
        btnOnloop5.setBounds(802, 310, 28, 23);

        nmonloop5.setEditable(false);
        nmonloop5.setName("nmonloop5"); // NOI18N
        nmonloop5.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmonloop5);
        nmonloop5.setBounds(611, 310, 190, 23);

        kdonloop5.setName("kdonloop5"); // NOI18N
        kdonloop5.setPreferredSize(new java.awt.Dimension(80, 23));
        kdonloop5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdonloop5KeyPressed(evt);
            }
        });
        FormInput.add(kdonloop5);
        kdonloop5.setBounds(510, 310, 100, 23);

        label39.setText("Onloop 5 :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label39);
        label39.setBounds(406, 310, 102, 23);

        BtnSimpan4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan4.setMnemonic('S');
        BtnSimpan4.setText("Simpan");
        BtnSimpan4.setToolTipText("Alt+S");
        BtnSimpan4.setName("BtnSimpan4"); // NOI18N
        BtnSimpan4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpan4ActionPerformed(evt);
            }
        });
        FormInput.add(BtnSimpan4);
        BtnSimpan4.setBounds(580, 340, 100, 30);

        BtnCloseIn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/cross.png"))); // NOI18N
        BtnCloseIn4.setMnemonic('U');
        BtnCloseIn4.setText("Tutup");
        BtnCloseIn4.setToolTipText("Alt+U");
        BtnCloseIn4.setName("BtnCloseIn4"); // NOI18N
        BtnCloseIn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCloseIn4ActionPerformed(evt);
            }
        });
        FormInput.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(680, 340, 100, 30);

        scrollPane2.setViewportView(FormInput);

        internalFrame5.add(scrollPane2, java.awt.BorderLayout.CENTER);

        WindowGantiDokterParamedis.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Cari Tagihan Operasi/VK ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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
        tbDokter.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
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

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 73));
        panelisi3.setLayout(null);

        label11.setText("Tanggal :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label11);
        label11.setBounds(0, 40, 70, 23);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl1);
        Tgl1.setBounds(74, 40, 100, 23);

        label13.setText("Pasien :");
        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label13);
        label13.setBounds(385, 10, 60, 23);

        kdmem.setName("kdmem"); // NOI18N
        kdmem.setPreferredSize(new java.awt.Dimension(80, 23));
        kdmem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdmemKeyPressed(evt);
            }
        });
        panelisi3.add(kdmem);
        kdmem.setBounds(449, 10, 80, 23);

        nmmem.setEditable(false);
        nmmem.setName("nmmem"); // NOI18N
        nmmem.setPreferredSize(new java.awt.Dimension(207, 23));
        panelisi3.add(nmmem);
        nmmem.setBounds(531, 10, 240, 23);

        BtnCari5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCari5.setMnemonic('2');
        BtnCari5.setToolTipText("Alt+2");
        BtnCari5.setName("BtnCari5"); // NOI18N
        BtnCari5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCari5ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnCari5);
        BtnCari5.setBounds(774, 10, 28, 23);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi3.add(label18);
        label18.setBounds(173, 40, 30, 23);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelisi3.add(Tgl2);
        Tgl2.setBounds(200, 40, 100, 23);

        label15.setText("No.Rawat :");
        label15.setName("label15"); // NOI18N
        label15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi3.add(label15);
        label15.setBounds(0, 10, 70, 23);

        NoRawat.setName("NoRawat"); // NOI18N
        NoRawat.setPreferredSize(new java.awt.Dimension(207, 23));
        NoRawat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoRawatKeyPressed(evt);
            }
        });
        panelisi3.add(NoRawat);
        NoRawat.setBounds(74, 10, 226, 23);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 56));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi1.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(170, 23));
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

        label9.setText("Record :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(55, 30));
        panelisi1.add(label9);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(155, 30));
        panelisi1.add(LTotal);

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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void BtnCari5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCari5ActionPerformed
        akses.setform("DlgCariTagihanOperasi");
        member.emptTeks();
        member.isCek();
        member.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        member.setLocationRelativeTo(internalFrame1);
        member.setAlwaysOnTop(false);
        member.setVisible(true);
    }//GEN-LAST:event_BtnCari5ActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt,kdmem,Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void kdmemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdmemKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());         
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
            Tgl2.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select nm_pasien from pasien where no_rkm_medis=?", nmmem,kdmem.getText());
            TCari.requestFocus();   
        }
    }//GEN-LAST:event_kdmemKeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        Valid.pindah(evt, Tgl1,kdmem);
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
        tampil();
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
        kdmem.setText("");
        nmmem.setText("");
        tampil();
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(int i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,33).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi operasi"); 
            }
            
            
            Map<String, Object> param = new HashMap<>();    
                param.put("namars",akses.getnamars());
                param.put("alamatrs",akses.getalamatrs());
                param.put("kotars",akses.getkabupatenrs());
                param.put("propinsirs",akses.getpropinsirs());
                param.put("kontakrs",akses.getkontakrs());
                param.put("emailrs",akses.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptOperasi.jasper","report","::[ Transaksi Operasi ]::",param);
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
        WindowGantiDokterParamedis.dispose();
        petugas.dispose();
        dokter.dispose();
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,Tgl1);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

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

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
    }//GEN-LAST:event_tbDokterMouseClicked

private void MnHapusTagihanOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusTagihanOperasiActionPerformed
    if(tbDokter.getSelectedRow()>-1){
        if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().equals("")){
            if(Sequel.cariRegistrasi(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                ttljmdokter=0;ttljmpetugas=0;ttlpendapatan=0;ttlbhp=0;status="";
                status=Sequel.cariIsi("select status from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
                ttljmdokter=Sequel.cariIsiAngka("select sum(biayaoperator1+biayaoperator2+biayaoperator3+biayadokter_anak+"+
                        "biayadokter_anestesi+biaya_dokter_pjanak+biaya_dokter_umum) from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
                ttljmpetugas=Sequel.cariIsiAngka("select sum(biayaasisten_operator1+biayaasisten_operator2+biayaasisten_operator3+"+
                        "biayainstrumen+biayaperawaat_resusitas+biayaasisten_anestesi+biayaasisten_anestesi2+biayabidan+biayabidan2+"+
                        "biayabidan3+biayaperawat_luar+biaya_omloop+biaya_omloop2+biaya_omloop3+biaya_omloop4+biaya_omloop5) "+
                        "from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
                ttlpendapatan=Sequel.cariIsiAngka("select sum(operasi.biayaoperator1+operasi.biayaoperator2+"+
                        "operasi.biayaoperator3+operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+"+
                        "operasi.biayaasisten_operator3+operasi.biayainstrumen+operasi.biayadokter_anak+"+
                        "operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+operasi.biayaasisten_anestesi+"+
                        "operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+"+
                        "operasi.biayaperawat_luar+operasi.biayaalat+operasi.biayasewaok+operasi.akomodasi+"+
                        "operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+"+
                        "operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biayasarpras+operasi.biaya_dokter_pjanak+"+
                        "operasi.biaya_dokter_umum) from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
                ttlbhp=Sequel.cariIsiAngka("select sum(jumlah*hargasatuan) from beri_obat_operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tanggal='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");

                
                if(Sequel.queryutf("delete from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1)+"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0) +"'")==true){
                    if(Sequel.queryutf("delete from beri_obat_operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tanggal='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'")==false){
                       ttlbhp=0;
                    }
                    if(status.equals("Ranap")){
                        Sequel.queryu("delete from tampjurnal");    
                        if(ttlpendapatan>0){
                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Operasi_Ranap+"','Suspen Piutang Operasi Ranap','0','"+ttlpendapatan+"'","Rekening");    
                            Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','Pendapatan Operasi Rawat Inap','"+ttlpendapatan+"','0'","Rekening");                              
                        }
                        if(ttljmdokter>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Dokter_Operasi_Ranap+"','Beban Jasa Medik Dokter Operasi Ranap','0','"+ttljmdokter+"'","Rekening");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Dokter_Operasi_Ranap+"','Utang Jasa Medik Dokter Operasi Ranap','"+ttljmdokter+"','0'","Rekening");                              
                        }
                        if(ttljmpetugas>0){
                            Sequel.menyimpan("tampjurnal","'"+Beban_Jasa_Medik_Paramedis_Operasi_Ranap+"','Beban Jasa Medik Petugas Operasi Ranap','0','"+ttljmpetugas+"'","Rekening");    
                            Sequel.menyimpan("tampjurnal","'"+Utang_Jasa_Medik_Paramedis_Operasi_Ranap+"','Utang Jasa Medik Petugas Operasi Ranap','"+ttljmpetugas+"','0'","Rekening");                              
                        }
                        if(ttlbhp>0){
                            Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Operasi_Ranap+"','HPP Persediaan Operasi Rawat Inap','0','"+ttlbhp+"'","Rekening");    
                            Sequel.menyimpan("tampjurnal","'"+Persediaan_Obat_Kamar_Operasi_Ranap+"','Persediaan BHP Operasi Rawat Inap','"+ttlbhp+"','0'","Rekening");                              
                        }
                        jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().substring(0,10),"U","PEMBATALAN OPERASI RAWAT INAP PASIEN OLEH "+akses.getkode());                                              
                    }       
                }
                
                tampil();
            }
        } 
    }               
}//GEN-LAST:event_MnHapusTagihanOperasiActionPerformed

private void MnHapusObatOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnHapusObatOperasiActionPerformed
    if(tbDokter.getSelectedRow()>-1){
        if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().equals("")){            
            if(Sequel.cariRegistrasi(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString())>0){
                JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                TCari.requestFocus();
            }else{
                ttljmdokter=0;ttljmpetugas=0;ttlpendapatan=0;ttlbhp=0;
                status=Sequel.cariIsi("select status from operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
                ttlbhp=Sequel.cariIsiAngka("select sum(jumlah*hargasatuan) from beri_obat_operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tanggal='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'");
                
                if(Sequel.queryutf("delete from beri_obat_operasi where no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1) +"' and tanggal='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0)+"'")==true){
                    if(status.equals("Ranap")){
                        if(ttlbhp>0){
                            Sequel.menyimpan("tampjurnal","'"+HPP_Obat_Operasi_Ranap+"','HPP Persediaan Operasi Rawat Inap','0','"+ttlbhp+"'","Rekening");    
                            Sequel.menyimpan("tampjurnal","'"+Persediaan_Obat_Kamar_Operasi_Ranap+"','Persediaan BHP Operasi Rawat Inap','"+ttlbhp+"','0'","Rekening");                              
                            Sequel.menyimpan("tampjurnal","'"+Suspen_Piutang_Operasi_Ranap+"','Suspen Piutang Operasi Ranap','0','"+ttlbhp+"'","Rekening");    
                            Sequel.menyimpan("tampjurnal","'"+Operasi_Ranap+"','Pendapatan Operasi Rawat Inap','"+ttlbhp+"','0'","Rekening");                             
                            jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString(),tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString().substring(0,10),"U","PEMBATALAN OBAT OPERASI RAWAT INAP OLEH "+akses.getkode());                                                  
                        }
                    }
                }
                
                tampil();
            }
        }   
    }        
}//GEN-LAST:event_MnHapusObatOperasiActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnUbahOperatorPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnUbahOperatorPetugasActionPerformed
        if(tbDokter.getSelectedRow()>-1){
            if(!tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString().equals("")){
                if(Sequel.cariRegistrasi(tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh diubah.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCari.requestFocus();
                }else{
                    try {
                        rs2=koneksi.prepareStatement(
                                "select operasi.operator1, operasi.operator2, operasi.operator3, operasi.asisten_operator1,"+
                                "operasi.asisten_operator2,operasi.asisten_operator3, operasi.instrumen, operasi.dokter_anak, operasi.perawaat_resusitas, "+
                                "operasi.dokter_anestesi, operasi.asisten_anestesi,operasi.asisten_anestesi2, operasi.bidan, operasi.bidan2, operasi.bidan3, operasi.perawat_luar, "+
                                "operasi.omloop,operasi.omloop2,operasi.omloop3,operasi.omloop4,operasi.omloop5,operasi.dokter_pjanak,operasi.dokter_umum "+
                                "from operasi where operasi.no_rawat='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString()+"' and operasi.tgl_operasi='"+tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()+"'").executeQuery();
                        no=1;
                        while(rs2.next()){
                            kdoperator1.setText(rs2.getString("operator1"));
                            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator1,rs2.getString("operator1"));
                            kdoperator2.setText(rs2.getString("operator2"));
                            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator2,rs2.getString("operator2"));
                            kdoperator3.setText(rs2.getString("operator3"));
                            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator3,rs2.getString("operator3"));
                            kdasistoperator1.setText(rs2.getString("asisten_operator1"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmasistoperator1,rs2.getString("asisten_operator1"));
                            kdasistoperator2.setText(rs2.getString("asisten_operator2"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmasistoperator2,rs2.getString("asisten_operator2"));
                            kdasistoperator3.setText(rs2.getString("asisten_operator3"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmasistoperator3,rs2.getString("asisten_operator3"));
                            kdInstrumen.setText(rs2.getString("instrumen"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nminstrumen,rs2.getString("instrumen"));
                            kddranak.setText(rs2.getString("dokter_anak"));
                            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmdranak,rs2.getString("dokter_anak"));
                            kdprwresust.setText(rs2.getString("perawaat_resusitas"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmprwresust,rs2.getString("perawaat_resusitas"));
                            kdanestesi.setText(rs2.getString("dokter_anestesi"));
                            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmanestesi,rs2.getString("dokter_anestesi"));
                            kdasistanestesi.setText(rs2.getString("asisten_anestesi"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmasistanestesi,rs2.getString("asisten_anestesi"));
                            kdasistanestesi2.setText(rs2.getString("asisten_anestesi2"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmasistanestesi2,rs2.getString("asisten_anestesi2"));
                            kdbidan.setText(rs2.getString("bidan"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmbidan,rs2.getString("bidan"));
                            kdbidan2.setText(rs2.getString("bidan2"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmbidan2,rs2.getString("bidan2"));
                            kdbidan3.setText(rs2.getString("bidan3"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmbidan3,rs2.getString("bidan3"));
                            kdprwluar.setText(rs2.getString("perawat_luar"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmprwluar,rs2.getString("perawat_luar"));
                            kdonloop1.setText(rs2.getString("omloop"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmonloop1,rs2.getString("omloop"));
                            kdonloop2.setText(rs2.getString("omloop2"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmonloop2,rs2.getString("omloop2"));
                            kdonloop3.setText(rs2.getString("omloop3"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmonloop3,rs2.getString("omloop3"));
                            kdonloop4.setText(rs2.getString("omloop4"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmonloop4,rs2.getString("omloop4"));
                            kdonloop5.setText(rs2.getString("omloop5"));
                            Sequel.cariIsi("select nama from petugas where nip=?",nmonloop5,rs2.getString("omloop5"));
                            kdpjanak.setText(rs2.getString("dokter_pjanak"));
                            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmpjanak,rs2.getString("dokter_pjanak"));
                            kddrumum.setText(rs2.getString("dokter_umum"));
                            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmdrumum,rs2.getString("dokter_umum"));
                        }
                        if(rs2!=null){
                            rs2.close();
                        }
                    } catch (Exception e) {
                        System.out.println("Notifikasi Cari Data : "+e);
                    }
                    WindowGantiDokterParamedis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                    WindowGantiDokterParamedis.setLocationRelativeTo(internalFrame1);
                    WindowGantiDokterParamedis.setVisible(true);
                }                
            }
        }                
    }//GEN-LAST:event_MnUbahOperatorPetugasActionPerformed

    private void NoRawatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoRawatKeyPressed
        Valid.pindah(evt, BtnKeluar,kdmem);
    }//GEN-LAST:event_NoRawatKeyPressed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        dokter.dispose();
        petugas.dispose();
        WindowGantiDokterParamedis.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if(kdoperator2.getText().trim().equals("")||nmoperator2.getText().trim().equals("")){
            kdoperator2.setText("-");
            nmoperator2.setText("-");
        }

        if(kdoperator3.getText().trim().equals("")||nmoperator3.getText().trim().equals("")){
            kdoperator3.setText("-");
            nmoperator3.setText("-");
        }

        if(kdanestesi.getText().trim().equals("")||nmanestesi.getText().trim().equals("")){
            kdanestesi.setText("-");
            nmanestesi.setText("-");
        }

        if(kddranak.getText().trim().equals("")||nmdranak.getText().trim().equals("")){
            kddranak.setText("-");
            nmdranak.setText("-");
        }

        if(kdbidan.getText().trim().equals("")||nmbidan.getText().trim().equals("")){
            kdbidan.setText("-");
            nmbidan.setText("-");
        }

        if(kdbidan2.getText().trim().equals("")||nmbidan2.getText().trim().equals("")){
            kdbidan2.setText("-");
            nmbidan2.setText("-");
        }

        if(kdbidan3.getText().trim().equals("")||nmbidan3.getText().trim().equals("")){
            kdbidan3.setText("-");
            nmbidan3.setText("-");
        }

        if(kdonloop1.getText().trim().equals("")||nmonloop1.getText().trim().equals("")){
            kdonloop1.setText("-");
            nmonloop1.setText("-");
        }

        if(kdonloop2.getText().trim().equals("")||nmonloop2.getText().trim().equals("")){
            kdonloop2.setText("-");
            nmonloop2.setText("-");
        }

        if(kdonloop3.getText().trim().equals("")||nmonloop3.getText().trim().equals("")){
            kdonloop3.setText("-");
            nmonloop3.setText("-");
        }
        
        if(kdonloop4.getText().trim().equals("")||nmonloop4.getText().trim().equals("")){
            kdonloop4.setText("-");
            nmonloop4.setText("-");
        }
        
        if(kdonloop5.getText().trim().equals("")||nmonloop5.getText().trim().equals("")){
            kdonloop5.setText("-");
            nmonloop5.setText("-");
        }

        if(kdasistoperator1.getText().trim().equals("")||nmasistoperator1.getText().trim().equals("")){
            kdasistoperator1.setText("-");
            nmasistoperator1.setText("-");
        }

        if(kdasistoperator2.getText().trim().equals("")||nmasistoperator2.getText().trim().equals("")){
            kdasistoperator2.setText("-");
            nmasistoperator2.setText("-");
        }
        
        if(kdasistoperator3.getText().trim().equals("")||nmasistoperator3.getText().trim().equals("")){
            kdasistoperator3.setText("-");
            nmasistoperator3.setText("-");
        }

        if(kdInstrumen.getText().trim().equals("")||nminstrumen.getText().trim().equals("")){
            kdInstrumen.setText("-");
            nminstrumen.setText("-");
        }

        if(kdasistanestesi.getText().trim().equals("")||nmasistanestesi.getText().trim().equals("")){
            kdasistanestesi.setText("-");
            nmasistanestesi.setText("-");
        }
        
        if(kdasistanestesi2.getText().trim().equals("")||nmasistanestesi2.getText().trim().equals("")){
            kdasistanestesi2.setText("-");
            nmasistanestesi2.setText("-");
        }

        if(kdprwresust.getText().trim().equals("")||nmprwresust.getText().trim().equals("")){
            kdprwresust.setText("-");
            nmprwresust.setText("-");
        }

        if(kdprwluar.getText().trim().equals("")||nmprwluar.getText().trim().equals("")){
            kdprwluar.setText("-");
            nmprwluar.setText("-");
        }
        
        if(kdpjanak.getText().trim().equals("")||nmpjanak.getText().trim().equals("")){
            kdpjanak.setText("-");
            nmpjanak.setText("-");
        }
        
        if(kddrumum.getText().trim().equals("")||nmdrumum.getText().trim().equals("")){
            kddrumum.setText("-");
            nmdrumum.setText("-");
        }

        if(kdoperator1.getText().trim().equals("")||nmoperator1.getText().trim().equals("")){
            Valid.textKosong(kdoperator1,"Operator 1");
        }else if(kdoperator2.getText().trim().equals("")||nmoperator2.getText().trim().equals("")){
            Valid.textKosong(kdoperator2,"Operator 2");
        }else if(kdoperator3.getText().trim().equals("")||nmoperator3.getText().trim().equals("")){
            Valid.textKosong(kdoperator3,"Operator 3");
        }else if(kdanestesi.getText().trim().equals("")||nmanestesi.getText().trim().equals("")){
            Valid.textKosong(kdanestesi,"dr Anestesi");
        }else if(kddranak.getText().trim().equals("")||nmdranak.getText().trim().equals("")){
            Valid.textKosong(kddranak,"dr Anak");
        }else if(kdbidan.getText().trim().equals("")||nmbidan.getText().trim().equals("")){
            Valid.textKosong(kdbidan,"Bidan 1");
        }else if(kdbidan2.getText().trim().equals("")||nmbidan2.getText().trim().equals("")){
            Valid.textKosong(kdbidan,"Bidan 2");
        }else if(kdbidan3.getText().trim().equals("")||nmbidan3.getText().trim().equals("")){
            Valid.textKosong(kdbidan,"Bidan 3");
        }else if(kdonloop1.getText().trim().equals("")||nmonloop1.getText().trim().equals("")){
            Valid.textKosong(kdonloop1,"Onloop 1");
        }else if(kdonloop2.getText().trim().equals("")||nmonloop2.getText().trim().equals("")){
            Valid.textKosong(kdonloop2,"Onloop 2");
        }else if(kdonloop3.getText().trim().equals("")||nmonloop3.getText().trim().equals("")){
            Valid.textKosong(kdonloop3,"Onloop 3");
        }else if(kdonloop4.getText().trim().equals("")||nmonloop4.getText().trim().equals("")){
            Valid.textKosong(kdonloop4,"Onloop 4");
        }else if(kdonloop5.getText().trim().equals("")||nmonloop5.getText().trim().equals("")){
            Valid.textKosong(kdonloop5,"Onloop 5");
        }else if(kdasistoperator1.getText().trim().equals("")||nmasistoperator1.getText().trim().equals("")){
            Valid.textKosong(kdasistoperator1,"Asisten Operator 1");
        }else if(kdasistoperator2.getText().trim().equals("")||nmasistoperator2.getText().trim().equals("")){
            Valid.textKosong(kdasistoperator2,"Asisten Operator 2");
        }else if(kdasistoperator3.getText().trim().equals("")||nmasistoperator3.getText().trim().equals("")){
            Valid.textKosong(kdasistoperator3,"Asisten Operator 3");
        }else if(kdInstrumen.getText().trim().equals("")||nminstrumen.getText().trim().equals("")){
            Valid.textKosong(kdInstrumen,"Instrumen");
        }else if(kdasistanestesi.getText().trim().equals("")||nmasistanestesi.getText().trim().equals("")){
            Valid.textKosong(kdasistanestesi,"Asisten Anastesi 1");
        }else if(kdasistanestesi2.getText().trim().equals("")||nmasistanestesi2.getText().trim().equals("")){
            Valid.textKosong(kdasistanestesi2,"Asisten Anastesi 2");
        }else if(kdprwresust.getText().trim().equals("")||nmprwresust.getText().trim().equals("")){
            Valid.textKosong(kdprwresust,"Perawat Resusitas");
        }else if(kdprwluar.getText().trim().equals("")||nmprwluar.getText().trim().equals("")){
            Valid.textKosong(kdprwluar,"Perawat Luar");
        }else if(kdpjanak.getText().trim().equals("")||nmpjanak.getText().trim().equals("")){
            Valid.textKosong(kdpjanak,"dr Pj Anak");
        }else if(kddrumum.getText().trim().equals("")||nmdrumum.getText().trim().equals("")){
            Valid.textKosong(kddrumum,"dr Umum");
        }else if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.mengedittf("operasi","no_rawat=? and tgl_operasi=?","operator1=?,operator2=?,operator3=?, "+
                 "asisten_operator1=?,asisten_operator2=?,asisten_operator3=?,instrumen=?,"+
                 "dokter_anak=?,perawaat_resusitas=?,dokter_anestesi=?,asisten_anestesi=?,"+
                 "asisten_anestesi2=?,bidan=?,bidan2=?,bidan3=?,perawat_luar=?,omloop=?,"+
                 "omloop2=?,omloop3=?,omloop4=?,omloop5=?,dokter_pjanak=?,dokter_umum=?",25,new String[]{
                     kdoperator1.getText(),kdoperator2.getText(),kdoperator3.getText(),kdasistoperator1.getText(),
                     kdasistoperator2.getText(),kdasistoperator3.getText(),kdInstrumen.getText(),kddranak.getText(),
                     kdprwresust.getText(),kdanestesi.getText(),kdasistanestesi.getText(),kdasistanestesi2.getText(),
                     kdbidan.getText(),kdbidan2.getText(),kdbidan3.getText(),kdprwluar.getText(),kdonloop1.getText(),
                     kdonloop2.getText(),kdonloop3.getText(),kdonloop4.getText(),kdonloop5.getText(),kdpjanak.getText(),
                     kddrumum.getText(),tbDokter.getValueAt(tbDokter.getSelectedRow(),1).toString(),
                     tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString()
                })==true){
                tampil();
                dokter.dispose();
                petugas.dispose();
                WindowGantiDokterParamedis.dispose();
            }
        }
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void kdoperator1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdoperator1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator1,kdoperator1.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnOperator1ActionPerformed(null);
        }else{
            Valid.pindah(evt,BtnSimpan4,kdoperator2);
        }
    }//GEN-LAST:event_kdoperator1KeyPressed

    private void BtnOperator1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperator1ActionPerformed
        pilihan=1;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnOperator1ActionPerformed

    private void kdasistoperator1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdasistoperator1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmasistoperator1,kdasistoperator1.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnAsis1ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdInstrumen,kdasistoperator2);
        }

    }//GEN-LAST:event_kdasistoperator1KeyPressed

    private void btnAsis1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsis1ActionPerformed
        pilihan=1;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnAsis1ActionPerformed

    private void kdoperator2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdoperator2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator2,kdoperator2.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnOperator2ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdoperator1,kdoperator3);
        }
    }//GEN-LAST:event_kdoperator2KeyPressed

    private void BtnOperator2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnOperator2ActionPerformed
        pilihan=2;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnOperator2ActionPerformed

    private void kdoperator3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdoperator3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmoperator3,kdoperator3.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnInstrumenActionPerformed(null);
        }else{
            Valid.pindah(evt,kdoperator2,kdanestesi);
        }
    }//GEN-LAST:event_kdoperator3KeyPressed

    private void btnInstrumenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInstrumenActionPerformed
        pilihan=3;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnInstrumenActionPerformed

    private void kdanestesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdanestesiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmanestesi,kdanestesi.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnAnastesiActionPerformed(null);
        }else{
            Valid.pindah(evt,kdoperator3,kddranak);
        }
    }//GEN-LAST:event_kdanestesiKeyPressed

    private void BtnAnastesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAnastesiActionPerformed
        pilihan=4;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnAnastesiActionPerformed

    private void kddranakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddranakKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmdranak,kddranak.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnAnakActionPerformed(null);
        }else{
            Valid.pindah(evt,kdanestesi,kdbidan);
        }
    }//GEN-LAST:event_kddranakKeyPressed

    private void btnAnakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnakActionPerformed
        pilihan=5;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btnAnakActionPerformed

    private void btnAsis2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsis2ActionPerformed
        pilihan=2;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnAsis2ActionPerformed

    private void kdasistoperator2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdasistoperator2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmasistoperator2,kdasistoperator2.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnAsis2ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdasistoperator1,kdasistanestesi);
        }
    }//GEN-LAST:event_kdasistoperator2KeyPressed

    private void btnAsis3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsis3ActionPerformed
        pilihan=3;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnAsis3ActionPerformed

    private void kdInstrumenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdInstrumenKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip='"+kdInstrumen.getText()+"'",nminstrumen);
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnAsis3ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdprwluar,kdasistoperator1);
        }
    }//GEN-LAST:event_kdInstrumenKeyPressed

    private void btnPrwResActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrwResActionPerformed
        pilihan=5;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPrwResActionPerformed

    private void kdprwresustKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdprwresustKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmprwresust,kdprwresust.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPrwResActionPerformed(null);
        }else{
            Valid.pindah(evt,kdasistanestesi,kdonloop1);
        }
    }//GEN-LAST:event_kdprwresustKeyPressed

    private void kdasistanestesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdasistanestesiKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmasistanestesi,kdasistanestesi.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnAsnesActionPerformed(null);
        }else{
            Valid.pindah(evt,kdasistoperator2,kdprwresust);
        }
    }//GEN-LAST:event_kdasistanestesiKeyPressed

    private void BtnAsnesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAsnesActionPerformed
        pilihan=4;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnAsnesActionPerformed

    private void kdbidanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbidanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmbidan,kdbidan.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBidanActionPerformed(null);
        }else{
            Valid.pindah(evt,kddranak,kdbidan2);
        }
    }//GEN-LAST:event_kdbidanKeyPressed

    private void btnBidanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBidanActionPerformed
        pilihan=7;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnBidanActionPerformed

    private void kdprwluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdprwluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmprwluar,kdprwluar.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPrwLuarActionPerformed(null);
        }else{
            Valid.pindah(evt,kdbidan3,kdInstrumen);
        }
    }//GEN-LAST:event_kdprwluarKeyPressed

    private void btnPrwLuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrwLuarActionPerformed
        pilihan=6;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnPrwLuarActionPerformed

    private void btnBidan2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBidan2ActionPerformed
        pilihan=8;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnBidan2ActionPerformed

    private void kdbidan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbidan2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmbidan2,kdbidan2.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBidan2ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdbidan,kdbidan3);
        }
    }//GEN-LAST:event_kdbidan2KeyPressed

    private void kdbidan3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdbidan3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmbidan3,kdbidan3.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnBidan3ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdbidan2,kdprwluar);
        }
    }//GEN-LAST:event_kdbidan3KeyPressed

    private void btnBidan3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBidan3ActionPerformed
        pilihan=9;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnBidan3ActionPerformed

    private void kdonloop1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdonloop1KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmonloop1,kdonloop1.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnOnloop1ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdprwresust,kdonloop2);
        }
    }//GEN-LAST:event_kdonloop1KeyPressed

    private void btnOnloop1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnloop1ActionPerformed
        pilihan=10;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnOnloop1ActionPerformed

    private void btnOnloop2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnloop2ActionPerformed
        pilihan=11;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnOnloop2ActionPerformed

    private void kdonloop2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdonloop2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmonloop2,kdonloop2.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnOnloop1ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdonloop1,kdonloop3);
        }
    }//GEN-LAST:event_kdonloop2KeyPressed

    private void btnOnloop3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnloop3ActionPerformed
        pilihan=12;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnOnloop3ActionPerformed

    private void kdonloop3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdonloop3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nama from petugas where nip=?",nmonloop3,kdonloop3.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnOnloop1ActionPerformed(null);
        }else{
            Valid.pindah(evt,kdonloop2,kdpjanak);
        }
    }//GEN-LAST:event_kdonloop3KeyPressed

    private void kdpjanakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpjanakKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmpjanak,kdpjanak.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btndrpjanakActionPerformed(null);
        }else{
            Valid.pindah(evt,kdonloop3,kddrumum);
        }
    }//GEN-LAST:event_kdpjanakKeyPressed

    private void btndrpjanakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndrpjanakActionPerformed
        pilihan=6;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btndrpjanakActionPerformed

    private void kddrumumKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddrumumKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",nmdranak,kddranak.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btndrumumActionPerformed(null);
        }else{
            Valid.pindah(evt,kdpjanak,BtnSimpan4);
        }
    }//GEN-LAST:event_kddrumumKeyPressed

    private void btndrumumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndrumumActionPerformed
        pilihan=7;
        dokter.emptTeks();
        dokter.isCek();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_btndrumumActionPerformed

    private void kdasistoperator3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdasistoperator3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdasistoperator3KeyPressed

    private void btnAsis4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsis4ActionPerformed
        pilihan=15;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnAsis4ActionPerformed

    private void kdasistanestesi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdasistanestesi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdasistanestesi2KeyPressed

    private void BtnAsnes1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAsnes1ActionPerformed
        pilihan=14;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_BtnAsnes1ActionPerformed

    private void kdonloop4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdonloop4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdonloop4KeyPressed

    private void btnOnloop4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnloop4ActionPerformed
        pilihan=13;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnOnloop4ActionPerformed

    private void btnOnloop5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnloop5ActionPerformed
        pilihan=14;
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        petugas.setLocationRelativeTo(internalFrame1);
        petugas.setVisible(true);
    }//GEN-LAST:event_btnOnloop5ActionPerformed

    private void kdonloop5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdonloop5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_kdonloop5KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariTagihanOperasi dialog = new DlgCariTagihanOperasi(new javax.swing.JFrame(), true);
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
    private widget.Button BtnAnastesi;
    private widget.Button BtnAsnes;
    private widget.Button BtnAsnes1;
    private widget.Button BtnCari;
    private widget.Button BtnCari5;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnKeluar;
    private widget.Button BtnOperator1;
    private widget.Button BtnOperator2;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan4;
    private widget.panelisi FormInput;
    private widget.TextBox Kd2;
    private widget.Label LTotal;
    private javax.swing.JMenuItem MnHapusObatOperasi;
    private javax.swing.JMenuItem MnHapusTagihanOperasi;
    private javax.swing.JMenuItem MnUbahOperatorPetugas;
    private widget.TextBox NoRawat;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private javax.swing.JDialog WindowGantiDokterParamedis;
    private widget.Button btnAnak;
    private widget.Button btnAsis1;
    private widget.Button btnAsis2;
    private widget.Button btnAsis3;
    private widget.Button btnAsis4;
    private widget.Button btnBidan;
    private widget.Button btnBidan2;
    private widget.Button btnBidan3;
    private widget.Button btnInstrumen;
    private widget.Button btnOnloop1;
    private widget.Button btnOnloop2;
    private widget.Button btnOnloop3;
    private widget.Button btnOnloop4;
    private widget.Button btnOnloop5;
    private widget.Button btnPrwLuar;
    private widget.Button btnPrwRes;
    private widget.Button btndrpjanak;
    private widget.Button btndrumum;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame5;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kdInstrumen;
    private widget.TextBox kdanestesi;
    private widget.TextBox kdasistanestesi;
    private widget.TextBox kdasistanestesi2;
    private widget.TextBox kdasistoperator1;
    private widget.TextBox kdasistoperator2;
    private widget.TextBox kdasistoperator3;
    private widget.TextBox kdbidan;
    private widget.TextBox kdbidan2;
    private widget.TextBox kdbidan3;
    private widget.TextBox kddranak;
    private widget.TextBox kddrumum;
    private widget.TextBox kdmem;
    private widget.TextBox kdonloop1;
    private widget.TextBox kdonloop2;
    private widget.TextBox kdonloop3;
    private widget.TextBox kdonloop4;
    private widget.TextBox kdonloop5;
    private widget.TextBox kdoperator1;
    private widget.TextBox kdoperator2;
    private widget.TextBox kdoperator3;
    private widget.TextBox kdpjanak;
    private widget.TextBox kdprwluar;
    private widget.TextBox kdprwresust;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label13;
    private widget.Label label14;
    private widget.Label label15;
    private widget.Label label17;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
    private widget.Label label25;
    private widget.Label label26;
    private widget.Label label27;
    private widget.Label label28;
    private widget.Label label29;
    private widget.Label label30;
    private widget.Label label31;
    private widget.Label label32;
    private widget.Label label33;
    private widget.Label label34;
    private widget.Label label35;
    private widget.Label label36;
    private widget.Label label37;
    private widget.Label label38;
    private widget.Label label39;
    private widget.Label label9;
    private widget.TextBox nmanestesi;
    private widget.TextBox nmasistanestesi;
    private widget.TextBox nmasistanestesi2;
    private widget.TextBox nmasistoperator1;
    private widget.TextBox nmasistoperator2;
    private widget.TextBox nmasistoperator3;
    private widget.TextBox nmbidan;
    private widget.TextBox nmbidan2;
    private widget.TextBox nmbidan3;
    private widget.TextBox nmdranak;
    private widget.TextBox nmdrumum;
    private widget.TextBox nminstrumen;
    private widget.TextBox nmmem;
    private widget.TextBox nmonloop1;
    private widget.TextBox nmonloop2;
    private widget.TextBox nmonloop3;
    private widget.TextBox nmonloop4;
    private widget.TextBox nmonloop5;
    private widget.TextBox nmoperator1;
    private widget.TextBox nmoperator2;
    private widget.TextBox nmoperator3;
    private widget.TextBox nmpjanak;
    private widget.TextBox nmprwluar;
    private widget.TextBox nmprwresust;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        String tanggal="  operasi.tgl_operasi between '"+Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00' and '"+Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59' ";
        String mem="";      
        if(!kdmem.getText().equals("")){
            mem=" and pasien.no_rkm_medis='"+kdmem.getText()+"' ";
        }
        String norawat="";
        if(!NoRawat.getText().equals("")){
            norawat=" and operasi.no_rawat='"+NoRawat.getText()+"' ";
        }
        String sql="select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,operasi.jenis_anasthesi,"+
                   "operasi.tgl_operasi from operasi inner join reg_periksa inner join pasien "+
                    " on operasi.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                    " where "+tanggal+mem+norawat+" and operasi.no_rawat like '%"+TCari.getText()+"%' or "+
                    tanggal+mem+norawat+" and reg_periksa.no_rkm_medis like '%"+TCari.getText()+"%' or "+
                    tanggal+mem+norawat+" and pasien.nm_pasien like '%"+TCari.getText()+"%' or "+
                    tanggal+mem+norawat+" and operasi.tgl_operasi like '%"+TCari.getText()+"%' or "+
                    tanggal+mem+norawat+" and operasi.jenis_anasthesi like '%"+TCari.getText()+"%'  "+                   
                    " group by operasi.no_rawat,operasi.tgl_operasi order by operasi.tgl_operasi,operasi.no_rawat ";
        prosesCari(sql);
    }

    private void prosesCari(String sql) {
       Valid.tabelKosong(tabMode);
        try{
            rs=koneksi.prepareStatement(sql).executeQuery();
            while(rs.next()){
                total=0;
                tabMode.addRow(new Object[]{rs.getString("tgl_operasi"),
                               rs.getString("no_rawat"),
                               rs.getString("no_rkm_medis")+", "+rs.getString("nm_pasien"),
                               rs.getString("jenis_anasthesi"),"Perawatan",
                               "Operator 1",
                               "Operator 2",
                               "Operator 3",
                               "Asisten Operator 1",
                               "Asisten Operator 2",
                               "Asisten Operator 3",
                               "Instrumen",
                               "Dokter Anak",
                               "Perawat Resusitas",
                               "Dokter Anestesi",
                               "Asisten Anestesi 1",
                               "Asisten Anestesi 2",
                               "Bidan 1",
                               "Bidan 2",
                               "Bidan 3",
                               "Perawat Luar",
                               "Onloop 1",
                               "Onloop 2",
                               "Onloop 3",
                               "Onloop 4",
                               "Onloop 5",
                               "Sewa OK/VK",
                               "Alat",
                               "Akomodasi",
                               "N.M.S.",
                               "Sarpras",
                               "Dokter PJ Anak",
                               "Dokter Umum",
                               "Biaya Perawatan"});     
                rs2=koneksi.prepareStatement(
                        "select operasi.operator1, operasi.operator2, operasi.operator3, operasi.asisten_operator1,"+
                        "operasi.asisten_operator2,operasi.asisten_operator3, operasi.instrumen, operasi.dokter_anak, operasi.perawaat_resusitas, "+
                        "operasi.dokter_anestesi, operasi.asisten_anestesi,operasi.asisten_anestesi2, operasi.bidan, operasi.bidan2, operasi.bidan3, operasi.perawat_luar, "+
                        "operasi.omloop,operasi.omloop2,operasi.omloop3,operasi.omloop4,operasi.omloop5,operasi.dokter_pjanak,operasi.dokter_umum,"+
                        "operasi.kode_paket,paket_operasi.nm_perawatan, operasi.biayaoperator1, operasi.biayaoperator2, operasi.biayaoperator3, "+
                        "operasi.biayaasisten_operator1, operasi.biayaasisten_operator2,operasi.biayaasisten_operator3, operasi.biayainstrumen, "+
                        "operasi.biayadokter_anak, operasi.biayaperawaat_resusitas, operasi.biayadokter_anestesi, "+
                        "operasi.biayaasisten_anestesi,operasi.biayaasisten_anestesi2, operasi.biayabidan,operasi.biayabidan2,operasi.biayabidan3, operasi.biayaperawat_luar, operasi.biayaalat,"+
                        "operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biaya_omloop,operasi.biaya_omloop2,"+
                        "operasi.biaya_omloop3,operasi.biaya_omloop4,operasi.biaya_omloop5,operasi.biayasarpras,operasi.biaya_dokter_pjanak,operasi.biaya_dokter_umum,"+
                        "(operasi.biayaoperator1+operasi.biayaoperator2+operasi.biayaoperator3+"+
                        "operasi.biayaasisten_operator1+operasi.biayaasisten_operator2+operasi.biayaasisten_operator3+operasi.biayainstrumen+"+
                        "operasi.biayadokter_anak+operasi.biayaperawaat_resusitas+operasi.biayadokter_anestesi+"+
                        "operasi.biayaasisten_anestesi+operasi.biayaasisten_anestesi2+operasi.biayabidan+operasi.biayabidan2+operasi.biayabidan3+"+
                        "operasi.biayaperawat_luar+operasi.biayaalat+operasi.biaya_dokter_pjanak+operasi.biaya_dokter_umum+"+
                        "operasi.biayasewaok+operasi.akomodasi+operasi.bagian_rs+operasi.biaya_omloop+operasi.biaya_omloop2+operasi.biaya_omloop3+operasi.biaya_omloop4+operasi.biaya_omloop5+operasi.biayasarpras) as total from operasi inner join paket_operasi "+
                        "on operasi.kode_paket=paket_operasi.kode_paket where operasi.no_rawat='"+rs.getString("no_rawat")+"' and operasi.tgl_operasi='"+rs.getString("tgl_operasi")+"'").executeQuery();
                no=1;
                while(rs2.next()){
                    Object[] data2={"","","","",no+". "+rs2.getString("nm_perawatan"),
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("operator1")),
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("operator2")),
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("operator3")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("asisten_operator1")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("asisten_operator2")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("asisten_operator3")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("instrumen")),
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("dokter_anak")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("perawaat_resusitas")),
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("dokter_anestesi")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("asisten_anestesi")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("asisten_anestesi2")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("bidan")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("bidan2")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("bidan3")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("perawat_luar")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("omloop")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("omloop2")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("omloop3")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("omloop4")),
                               Sequel.cariIsi("select nama from petugas where nip=?",rs2.getString("omloop5")),
                               "",
                               "",
                               "",
                               "",
                               "",
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("dokter_pjanak")),
                               Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",rs2.getString("dokter_umum")),
                               ""};
                    tabMode.addRow(data2);  
                    Object[] data3={"","","","","",Valid.SetAngka(rs2.getDouble("biayaoperator1")),
                               Valid.SetAngka(rs2.getDouble("biayaoperator2")),
                               Valid.SetAngka(rs2.getDouble("biayaoperator3")),
                               Valid.SetAngka(rs2.getDouble("biayaasisten_operator1")),
                               Valid.SetAngka(rs2.getDouble("biayaasisten_operator2")),
                               Valid.SetAngka(rs2.getDouble("biayaasisten_operator3")),
                               Valid.SetAngka(rs2.getDouble("biayainstrumen")),
                               Valid.SetAngka(rs2.getDouble("biayadokter_anak")),
                               Valid.SetAngka(rs2.getDouble("biayaperawaat_resusitas")),
                               Valid.SetAngka(rs2.getDouble("biayadokter_anestesi")),
                               Valid.SetAngka(rs2.getDouble("biayaasisten_anestesi")),
                               Valid.SetAngka(rs2.getDouble("biayaasisten_anestesi2")),
                               Valid.SetAngka(rs2.getDouble("biayabidan")),
                               Valid.SetAngka(rs2.getDouble("biayabidan2")),
                               Valid.SetAngka(rs2.getDouble("biayabidan3")),
                               Valid.SetAngka(rs2.getDouble("biayaperawat_luar")),
                               Valid.SetAngka(rs2.getDouble("biaya_omloop")),
                               Valid.SetAngka(rs2.getDouble("biaya_omloop2")),
                               Valid.SetAngka(rs2.getDouble("biaya_omloop3")),
                               Valid.SetAngka(rs2.getDouble("biaya_omloop4")),
                               Valid.SetAngka(rs2.getDouble("biaya_omloop5")),
                               Valid.SetAngka(rs2.getDouble("biayasewaok")),
                               Valid.SetAngka(rs2.getDouble("biayaalat")),
                               Valid.SetAngka(rs2.getDouble("akomodasi")),
                               Valid.SetAngka(rs2.getDouble("bagian_rs")),
                               Valid.SetAngka(rs2.getDouble("biayasarpras")),
                               Valid.SetAngka(rs2.getDouble("biaya_dokter_pjanak")),
                               Valid.SetAngka(rs2.getDouble("biaya_dokter_umum")),
                               Valid.SetAngka(rs2.getDouble("total"))};
                    tabMode.addRow(data3); 
                    total=total+rs2.getDouble("total");
                    no++;
                }
                if(rs2!=null){
                    rs2.close();
                }
                Object[] data3={"","","","","Obat & BHP", "Satuan", "Harga","Jml","","","","","","","","","","","","","","","","","","","","","","","","","","Biaya Obat"};
                tabMode.addRow(data3); 
                rs3=koneksi.createStatement().executeQuery(
                        "select beri_obat_operasi.kd_obat,obatbhp_ok.nm_obat,kodesatuan.satuan, beri_obat_operasi.hargasatuan,beri_obat_operasi.jumlah "+
                        "from beri_obat_operasi inner join obatbhp_ok inner join  kodesatuan "+
                        "on beri_obat_operasi.kd_obat=obatbhp_ok.kd_obat and obatbhp_ok.kode_sat=kodesatuan.kode_sat "+
                        "where beri_obat_operasi.no_rawat='"+rs.getString("no_rawat")+"' and beri_obat_operasi.tanggal='"+rs.getString("tgl_operasi")+"'");
                no=1;
                while(rs3.next()){
                    Object[] data2={"","","","",no+". "+rs3.getString("nm_obat"),rs3.getString("satuan"), rs3.getString("hargasatuan"), 
                                   rs3.getString("jumlah"),"","","","","","","","","","","","","","","","","","","","","","","","","",Valid.SetAngka(rs3.getDouble("jumlah")*rs3.getDouble("hargasatuan"))};
                    tabMode.addRow(data2);  
                    total=total+(rs3.getDouble("jumlah")*rs3.getDouble("hargasatuan"));
                    no++;
                }
                if(rs3!=null){
                    rs3.close();
                }
                Object[] data4={"","","","","Total Biaya :", "", "","","","","","","","","","","","","","","","","","","","",""," ","","","","","",Valid.SetAngka(total)};
                tabMode.addRow(data4); 
            }      
            rs.last();  
            LTotal.setText(""+rs.getRow());
            if(rs!=null){
                rs.close();
            }
        }catch(SQLException e){
            System.out.println("Notifikasi : "+e);
        }
        
    }

    public void isCek(){
        MnHapusObatOperasi.setEnabled(akses.getoperasi());
        MnHapusTagihanOperasi.setEnabled(akses.getoperasi());
        BtnPrint.setEnabled(akses.getoperasi());
    }
     
    private void getData() {
        int row=tbDokter.getSelectedRow();
        if(row!= -1){
            Kd2.setText(tabMode.getValueAt(row,0).toString());
        }
    }
     
    public JTextField getTextField(){
        return Kd2;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    public void setPasien(String pasien){
        NoRawat.setText(pasien);
    }
 
}
