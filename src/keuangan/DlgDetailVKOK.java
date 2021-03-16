

package keuangan;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author perpustakaan
 */
public final class DlgDetailVKOK extends javax.swing.JDialog {
    private final Connection koneksi=koneksiDB.condb();
    private final sekuel Sequel=new sekuel();
    private DefaultTableModel tabModeOperasi,tabModeOperasi2;
    private validasi Valid=new validasi();
    private ResultSet rs,rstindakan;
    private PreparedStatement ps,pstindakan;
    private String sql;
    private int i=0;
    private double total=0,biayaoperator1=0,biayaoperator2=0, 
            biayaoperator3=0,biayaasisten_operator1=0,biayaasisten_operator2=0,
            biayaasisten_operator3=0,biayainstrumen=0,biayadokter_anak=0,
            biayaperawaat_resusitas=0,biayadokter_anestesi=0,biayaasisten_anestesi=0,
            biayaasisten_anestesi2=0,biayabidan=0,biayabidan2=0,biayabidan3=0,
            biayaperawat_luar=0,biayaalat=0,biayasewaok=0,akomodasi=0,
            bagian_rs=0,biaya_omloop=0,biaya_omloop2=0,biaya_omloop3=0,
            biaya_omloop4=0,biaya_omloop5=0,biayasarpras=0,biaya_dokter_pjanak=0,
            biaya_dokter_umum=0;

    /** Creates new form DlgLhtBiaya
     * @param parent
     * @param modal */
    public DlgDetailVKOK(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(8,1);
        setSize(885,674);
        
        
        tabModeOperasi=new DefaultTableModel(null,new Object[]{
            "No.","No.Rawat","No.R.M.","Nama Pasien","Tanggal","Jam","Cara Bayar","Ruangan",
            "Operator 1","JM Operator 1","Operator 2","JM Operator 2",
            "Operator 3","JM Operator 3", "Asisten Operator 1","JM AO 1",
            "Asisten Operator 2","JM AO 2","Asisten Operator 3", "JM AO 3",
            "Instrumen","JM Instrumen","Dokter Anak", "JM dr Anak",
            "Perawat Resusitas","JM P.R.","Dokter Anestesi","JM dr Anastesi",
            "Asisten Anestesi 1", "JM A.A. 1","Asisten Anestesi 2","JM A.A. 2",
            "Bidan 1","JM Bidan 1","Bidan 2","JM Bidan 2","Bidan 3","JM Bidan 3",
            "Perawat Luar","JM P.L.","Onloop 1","JM Onloop 1","Onloop 2","JM Onloop 2",
            "Onloop 3","JM Onloop 3", "Onloop 4","JM Onloop 4", "Onloop 5","JM Onloop 5",
            "Dokter P.J. Anak","JM dr P.J. Anak","Dokter Umum", "JM dr Umum",
            "Sewa Alat", "Sewa OK/VK", "Akomodasi", "N.M.S.",  "Sarpras","Total" 
        }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbOperasi.setModel(tabModeOperasi);
        tbOperasi.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbOperasi.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 60; i++) {
            TableColumn column = tbOperasi.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(250);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(55);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==7){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(80);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else if(i==18){
                column.setPreferredWidth(150);
            }else if(i==19){
                column.setPreferredWidth(80);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(80);
            }else if(i==22){
                column.setPreferredWidth(150);
            }else if(i==23){
                column.setPreferredWidth(80);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else if(i==25){
                column.setPreferredWidth(80);
            }else if(i==26){
                column.setPreferredWidth(150);
            }else if(i==27){
                column.setPreferredWidth(80);
            }else if(i==28){
                column.setPreferredWidth(150);
            }else if(i==29){
                column.setPreferredWidth(80);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(80);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(80);
            }else if(i==34){
                column.setPreferredWidth(150);
            }else if(i==35){
                column.setPreferredWidth(80);
            }else if(i==36){
                column.setPreferredWidth(150);
            }else if(i==37){
                column.setPreferredWidth(80);
            }else if(i==38){
                column.setPreferredWidth(150);
            }else if(i==39){
                column.setPreferredWidth(80);
            }else if(i==40){
                column.setPreferredWidth(150);
            }else if(i==41){
                column.setPreferredWidth(80);
            }else if(i==42){
                column.setPreferredWidth(150);
            }else if(i==43){
                column.setPreferredWidth(80);
            }else if(i==44){
                column.setPreferredWidth(150);
            }else if(i==45){
                column.setPreferredWidth(80);
            }else if(i==46){
                column.setPreferredWidth(150);
            }else if(i==47){
                column.setPreferredWidth(80);
            }else if(i==48){
                column.setPreferredWidth(150);
            }else if(i==49){
                column.setPreferredWidth(80);
            }else if(i==50){
                column.setPreferredWidth(150);
            }else if(i==51){
                column.setPreferredWidth(80);
            }else if(i==52){
                column.setPreferredWidth(150);
            }else if(i==53){
                column.setPreferredWidth(80);
            }else if(i==54){
                column.setPreferredWidth(80);
            }else if(i==55){
                column.setPreferredWidth(80);
            }else if(i==56){
                column.setPreferredWidth(80);
            }else if(i==57){
                column.setPreferredWidth(80);
            }else if(i==58){
                column.setPreferredWidth(80);
            }else if(i==59){
                column.setPreferredWidth(100);
            }
        }
        tbOperasi.setDefaultRenderer(Object.class, new WarnaTable());
        
        tabModeOperasi2=new DefaultTableModel(null,new Object[]{
            "No.","No.Rawat","No.R.M.","Nama Pasien","Tanggal","Jam","Cara Bayar","Ruangan",
            "Operator 1","JM Operator 1","Operator 2","JM Operator 2",
            "Operator 3","JM Operator 3", "Asisten Operator 1","JM AO 1",
            "Asisten Operator 2","JM AO 2","Asisten Operator 3", "JM AO 3",
            "Instrumen","JM Instrumen","Dokter Anak", "JM dr Anak",
            "Perawat Resusitas","JM P.R.","Dokter Anestesi","JM dr Anastesi",
            "Asisten Anestesi 1", "JM A.A. 1","Asisten Anestesi 2","JM A.A. 2",
            "Bidan 1","JM Bidan 1","Bidan 2","JM Bidan 2","Bidan 3","JM Bidan 3",
            "Perawat Luar","JM P.L.","Onloop 1","JM Onloop 1","Onloop 2","JM Onloop 2",
            "Onloop 3","JM Onloop 3", "Onloop 4","JM Onloop 4", "Onloop 5","JM Onloop 5",
            "Dokter P.J. Anak","JM dr P.J. Anak","Dokter Umum", "JM dr Umum",
            "Sewa Alat", "Sewa OK/VK", "Akomodasi", "N.M.S.",  "Sarpras","Total" 
        }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
             Class[] types = new Class[] {
                 java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                 java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                 java.lang.Double.class,java.lang.Object.class,java.lang.Double.class,
                 java.lang.Object.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                 java.lang.Double.class,java.lang.Double.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbOperasi1.setModel(tabModeOperasi2);
        tbOperasi1.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbOperasi1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 60; i++) {
            TableColumn column = tbOperasi1.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(30);
            }else if(i==1){
                column.setPreferredWidth(105);
            }else if(i==2){
                column.setPreferredWidth(70);
            }else if(i==3){
                column.setPreferredWidth(250);
            }else if(i==4){
                column.setPreferredWidth(75);
            }else if(i==5){
                column.setPreferredWidth(55);
            }else if(i==6){
                column.setPreferredWidth(120);
            }else if(i==7){
                column.setPreferredWidth(120);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(80);
            }else if(i==10){
                column.setPreferredWidth(150);
            }else if(i==11){
                column.setPreferredWidth(80);
            }else if(i==12){
                column.setPreferredWidth(150);
            }else if(i==13){
                column.setPreferredWidth(80);
            }else if(i==14){
                column.setPreferredWidth(150);
            }else if(i==15){
                column.setPreferredWidth(80);
            }else if(i==16){
                column.setPreferredWidth(150);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else if(i==18){
                column.setPreferredWidth(150);
            }else if(i==19){
                column.setPreferredWidth(80);
            }else if(i==20){
                column.setPreferredWidth(150);
            }else if(i==21){
                column.setPreferredWidth(80);
            }else if(i==22){
                column.setPreferredWidth(150);
            }else if(i==23){
                column.setPreferredWidth(80);
            }else if(i==24){
                column.setPreferredWidth(150);
            }else if(i==25){
                column.setPreferredWidth(80);
            }else if(i==26){
                column.setPreferredWidth(150);
            }else if(i==27){
                column.setPreferredWidth(80);
            }else if(i==28){
                column.setPreferredWidth(150);
            }else if(i==29){
                column.setPreferredWidth(80);
            }else if(i==30){
                column.setPreferredWidth(150);
            }else if(i==31){
                column.setPreferredWidth(80);
            }else if(i==32){
                column.setPreferredWidth(150);
            }else if(i==33){
                column.setPreferredWidth(80);
            }else if(i==34){
                column.setPreferredWidth(150);
            }else if(i==35){
                column.setPreferredWidth(80);
            }else if(i==36){
                column.setPreferredWidth(150);
            }else if(i==37){
                column.setPreferredWidth(80);
            }else if(i==38){
                column.setPreferredWidth(150);
            }else if(i==39){
                column.setPreferredWidth(80);
            }else if(i==40){
                column.setPreferredWidth(150);
            }else if(i==41){
                column.setPreferredWidth(80);
            }else if(i==42){
                column.setPreferredWidth(150);
            }else if(i==43){
                column.setPreferredWidth(80);
            }else if(i==44){
                column.setPreferredWidth(150);
            }else if(i==45){
                column.setPreferredWidth(80);
            }else if(i==46){
                column.setPreferredWidth(150);
            }else if(i==47){
                column.setPreferredWidth(80);
            }else if(i==48){
                column.setPreferredWidth(150);
            }else if(i==49){
                column.setPreferredWidth(80);
            }else if(i==50){
                column.setPreferredWidth(150);
            }else if(i==51){
                column.setPreferredWidth(80);
            }else if(i==52){
                column.setPreferredWidth(150);
            }else if(i==53){
                column.setPreferredWidth(80);
            }else if(i==54){
                column.setPreferredWidth(80);
            }else if(i==55){
                column.setPreferredWidth(80);
            }else if(i==56){
                column.setPreferredWidth(80);
            }else if(i==57){
                column.setPreferredWidth(80);
            }else if(i==58){
                column.setPreferredWidth(80);
            }else if(i==59){
                column.setPreferredWidth(100);
            }
        }
        tbOperasi1.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
            
    }
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        internalFrame1 = new widget.InternalFrame();
        panelGlass5 = new widget.panelisi();
        label9 = new widget.Label();
        Tgl1 = new widget.Tanggal();
        label18 = new widget.Label();
        Tgl2 = new widget.Tanggal();
        label10 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        label11 = new widget.Label();
        BtnPrint = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame5 = new widget.InternalFrame();
        Scroll3 = new widget.ScrollPane();
        tbOperasi = new widget.Table();
        internalFrame12 = new widget.InternalFrame();
        Scroll10 = new widget.ScrollPane();
        tbOperasi1 = new widget.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Detail Tindakan VK & OK]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass5.setName("panelGlass5"); // NOI18N
        panelGlass5.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        label9.setText("Tanggal :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(50, 23));
        panelGlass5.add(label9);

        Tgl1.setDisplayFormat("dd-MM-yyyy");
        Tgl1.setName("Tgl1"); // NOI18N
        Tgl1.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl1KeyPressed(evt);
            }
        });
        panelGlass5.add(Tgl1);

        label18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label18.setText("s.d.");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(25, 23));
        panelGlass5.add(label18);

        Tgl2.setDisplayFormat("dd-MM-yyyy");
        Tgl2.setName("Tgl2"); // NOI18N
        Tgl2.setPreferredSize(new java.awt.Dimension(90, 23));
        Tgl2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Tgl2KeyPressed(evt);
            }
        });
        panelGlass5.add(Tgl2);

        label10.setText("Key Word :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass5.add(label10);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(200, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass5.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
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
        panelGlass5.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("2Alt+2");
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
        panelGlass5.add(BtnAll);

        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(20, 23));
        panelGlass5.add(label11);

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
        panelGlass5.add(BtnPrint);

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
        panelGlass5.add(BtnKeluar);

        internalFrame1.add(panelGlass5, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(241, 246, 236)));
        TabRawat.setForeground(new java.awt.Color(50,50,50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame5.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame5.setBorder(null);
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll3.setName("Scroll3"); // NOI18N
        Scroll3.setOpaque(true);

        tbOperasi.setName("tbOperasi"); // NOI18N
        tbOperasi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbOperasiMouseClicked(evt);
            }
        });
        tbOperasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbOperasiKeyPressed(evt);
            }
        });
        Scroll3.setViewportView(tbOperasi);

        internalFrame5.add(Scroll3, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Detail Tindakan VK", internalFrame5);

        internalFrame12.setBackground(new java.awt.Color(235, 255, 235));
        internalFrame12.setBorder(null);
        internalFrame12.setName("internalFrame12"); // NOI18N
        internalFrame12.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll10.setName("Scroll10"); // NOI18N
        Scroll10.setOpaque(true);

        tbOperasi1.setName("tbOperasi1"); // NOI18N
        tbOperasi1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbOperasi1MouseClicked(evt);
            }
        });
        tbOperasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbOperasi1KeyPressed(evt);
            }
        });
        Scroll10.setViewportView(tbOperasi1);

        internalFrame12.add(Scroll10, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Detail Tindakan OK", internalFrame12);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,Tgl1,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        switch (TabRawat.getSelectedIndex()) {
            case 0:                
                if(tabModeOperasi.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabModeOperasi.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    param.put("cari","%"+TCari.getText().trim()+"%");                    
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDetailTindakanOperasi.jasper",param,"::[ Detail Tindakan Operasi ]::");                    
                }   break;
            case 1:
                if(tabModeOperasi2.getRowCount()==0){
                    JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
                    TCari.requestFocus();
                }else if(tabModeOperasi2.getRowCount()!=0){
                    Map<String, Object> param = new HashMap<>();
                    param.put("namars",akses.getnamars());
                    param.put("alamatrs",akses.getalamatrs());
                    param.put("kotars",akses.getkabupatenrs());
                    param.put("propinsirs",akses.getpropinsirs());
                    param.put("kontakrs",akses.getkontakrs());
                    param.put("emailrs",akses.getemailrs());
                    param.put("tanggal1",Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                    param.put("tanggal2",Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                    param.put("cari","%"+TCari.getText().trim()+"%");                    
                    param.put("logo",Sequel.cariGambar("select logo from setting"));
                    Valid.MyReport("rptDetailTindakanOperasi.jasper",param,"::[ Detail Tindakan Operasi ]::");                    
                }  break;
            default:
                    break;
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_BtnPrintActionPerformed

    private void Tgl1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl1KeyPressed
        Valid.pindah(evt, BtnKeluar, Tgl2);
    }//GEN-LAST:event_Tgl1KeyPressed

    private void Tgl2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Tgl2KeyPressed
        
    }//GEN-LAST:event_Tgl2KeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        switch (TabRawat.getSelectedIndex()) {
            case 0:
                tampil();
                break;
            case 1:
                tampil2();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            TabRawatMouseClicked(null);
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
            Valid.pindah(evt, TCari, BtnKeluar);
        }
    }//GEN-LAST:event_BtnCariKeyPressed

    private void tbOperasiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbOperasiMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbOperasiMouseClicked

    private void tbOperasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbOperasiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbOperasiKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        TabRawatMouseClicked(null);
    }//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
    }//GEN-LAST:event_BtnAllKeyPressed

    private void tbOperasi1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbOperasi1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbOperasi1MouseClicked

    private void tbOperasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbOperasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_tbOperasi1KeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgDetailVKOK dialog = new DlgDetailVKOK(new javax.swing.JFrame(), true);
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
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.ScrollPane Scroll10;
    private widget.ScrollPane Scroll3;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame12;
    private widget.InternalFrame internalFrame5;
    private widget.Label label10;
    private widget.Label label11;
    private widget.Label label18;
    private widget.Label label9;
    private widget.panelisi panelGlass5;
    private widget.Table tbOperasi;
    private widget.Table tbOperasi1;
    // End of variables declaration//GEN-END:variables
    
    public void tampil(){     
        Valid.tabelKosong(tabModeOperasi);
        try{
            pstindakan=koneksi.prepareStatement(
                    "select operasi.kode_paket,paket_operasi.nm_perawatan from operasi inner join paket_operasi "+
                    "on operasi.kode_paket=paket_operasi.kode_paket where "+
                    "paket_operasi.kategori='Kebidanan' and operasi.tgl_operasi between ? and ? and operasi.kode_paket like ? or "+
                    "paket_operasi.kategori='Kebidanan' and operasi.tgl_operasi between ? and ? and paket_operasi.nm_perawatan like ? "+
                    "group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
            try {
                pstindakan.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                pstindakan.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                pstindakan.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                pstindakan.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                pstindakan.setString(6,"%"+TCari.getText().trim()+"%");
                rstindakan=pstindakan.executeQuery();
                while(rstindakan.next()){
                    tabModeOperasi.addRow(new Object[]{
                        "@","Tindakan :",rstindakan.getString("kode_paket"),rstindakan.getString("nm_perawatan"),
                         "","","","","",null,"",null,"",null,"",null,"",null,
                         "",null,"",null,"",null,"",null,"",null,"",null,"",null,
                         "",null,"",null,"",null,"",null,"",null,"",null,"",null,
                         "",null,"",null,"",null,"",null,null,null,null,null,null,null
                    });
                    ps=koneksi.prepareStatement(
                            "select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                            "operasi.kode_paket,paket_operasi.nm_perawatan,operasi.tgl_operasi, "+
                            "penjab.png_jawab,if(operasi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                            "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                            "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=operasi.no_rawat limit 1 )) as ruangan,"+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.operator1) as operator1,operasi.biayaoperator1, "+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.operator2) as operator2,operasi.biayaoperator2, "+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.operator3) as operator3,operasi.biayaoperator3,"+
                            "(select nama from petugas where petugas.nip=operasi.asisten_operator1) as asisten_operator1,operasi.biayaasisten_operator1, "+
                            "(select nama from petugas where petugas.nip=operasi.asisten_operator2) as asisten_operator2,operasi.biayaasisten_operator2, "+
                            "(select nama from petugas where petugas.nip=operasi.asisten_operator3) as asisten_operator3,operasi.biayaasisten_operator3, "+
                            "(select nama from petugas where petugas.nip=operasi.instrumen) as instrumen,operasi.biayainstrumen, "+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.dokter_anak) as dokter_anak,operasi.biayadokter_anak, "+
                            "(select nama from petugas where petugas.nip=operasi.perawaat_resusitas) as perawaat_resusitas,operasi.biayaperawaat_resusitas, "+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.dokter_anestesi) as dokter_anestesi,operasi.biayadokter_anestesi, "+
                            "(select nama from petugas where petugas.nip=operasi.asisten_anestesi) as asisten_anestesi,operasi.biayaasisten_anestesi, "+
                            "(select nama from petugas where petugas.nip=operasi.asisten_anestesi2) as asisten_anestesi2,operasi.biayaasisten_anestesi2, "+
                            "(select nama from petugas where petugas.nip=operasi.bidan) as bidan,operasi.biayabidan, "+
                            "(select nama from petugas where petugas.nip=operasi.bidan2) as bidan2,operasi.biayabidan2, "+
                            "(select nama from petugas where petugas.nip=operasi.bidan3) as bidan3,operasi.biayabidan3, "+
                            "(select nama from petugas where petugas.nip=operasi.perawat_luar) as perawat_luar,operasi.biayaperawat_luar, "+
                            "(select nama from petugas where petugas.nip=operasi.omloop) as omloop,operasi.biaya_omloop, "+
                            "(select nama from petugas where petugas.nip=operasi.omloop2) as omloop2,operasi.biaya_omloop2, "+
                            "(select nama from petugas where petugas.nip=operasi.omloop3) as omloop3,operasi.biaya_omloop3, "+
                            "(select nama from petugas where petugas.nip=operasi.omloop4) as omloop4,operasi.biaya_omloop4, "+
                            "(select nama from petugas where petugas.nip=operasi.omloop5) as omloop5,operasi.biaya_omloop5, "+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.dokter_pjanak) as dokter_pjanak,operasi.biaya_dokter_pjanak, "+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.dokter_umum) as dokter_umum,operasi.biaya_dokter_umum, "+
                            "operasi.biayaalat,operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biayasarpras "+
                            "from operasi inner join reg_periksa inner join pasien "+
                            "inner join paket_operasi inner join penjab "+
                            "on operasi.no_rawat=reg_periksa.no_rawat and "+
                            "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and "+
                            "operasi.kode_paket=paket_operasi.kode_paket and "+
                            "reg_periksa.kd_pj=penjab.kd_pj "+
                            "where operasi.tgl_operasi between ? and ? and operasi.kode_paket=? "+
                            "order by operasi.tgl_operasi");
                     try {
                         ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                         ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                         ps.setString(3,rstindakan.getString("kode_paket"));                         
                         rs=ps.executeQuery();
                         i=1;
                         total=0;biayaoperator1=0;biayaoperator2=0; 
                         biayaoperator3=0;biayaasisten_operator1=0;biayaasisten_operator2=0;
                         biayaasisten_operator3=0;biayainstrumen=0;biayadokter_anak=0;
                         biayaperawaat_resusitas=0;biayadokter_anestesi=0;biayaasisten_anestesi=0;
                         biayaasisten_anestesi2=0;biayabidan=0;biayabidan2=0;biayabidan3=0;
                         biayaperawat_luar=0;biayaalat=0;biayasewaok=0;akomodasi=0;
                         bagian_rs=0;biaya_omloop=0;biaya_omloop2=0;biaya_omloop3=0;
                         biaya_omloop4=0;biaya_omloop5=0;biayasarpras=0;biaya_dokter_pjanak=0;
                         biaya_dokter_umum=0;
                         while(rs.next()){  
                             biayaoperator1=biayaoperator1+rs.getDouble("biayaoperator1");
                             biayaoperator2=biayaoperator2+rs.getDouble("biayaoperator2");
                             biayaoperator3=biayaoperator3+rs.getDouble("biayaoperator3");
                             biayaasisten_operator1=biayaasisten_operator1+rs.getDouble("biayaasisten_operator1");
                             biayaasisten_operator2=biayaasisten_operator2+rs.getDouble("biayaasisten_operator2");
                             biayaasisten_operator3=biayaasisten_operator3+rs.getDouble("biayaasisten_operator3");
                             biayainstrumen=biayainstrumen+rs.getDouble("biayainstrumen");
                             biayadokter_anak=biayadokter_anak+rs.getDouble("biayadokter_anak");
                             biayaperawaat_resusitas=biayaperawaat_resusitas+rs.getDouble("biayaperawaat_resusitas");
                             biayadokter_anestesi=biayadokter_anestesi+rs.getDouble("biayadokter_anestesi");
                             biayaasisten_anestesi=biayaasisten_anestesi+rs.getDouble("biayaasisten_anestesi");
                             biayaasisten_anestesi2=biayaasisten_anestesi2+rs.getDouble("biayaasisten_anestesi2");
                             biayabidan=biayabidan+rs.getDouble("biayabidan");
                             biayabidan2=biayabidan2+rs.getDouble("biayabidan2");
                             biayabidan3=biayabidan3+rs.getDouble("biayabidan3");
                             biayaperawat_luar=biayaperawat_luar+rs.getDouble("biayaperawat_luar");
                             biaya_omloop=biaya_omloop+rs.getDouble("biaya_omloop");
                             biaya_omloop2=biaya_omloop2+rs.getDouble("biaya_omloop2");
                             biaya_omloop3=biaya_omloop3+rs.getDouble("biaya_omloop3");
                             biaya_omloop4=biaya_omloop4+rs.getDouble("biaya_omloop4");
                             biaya_omloop5=biaya_omloop5+rs.getDouble("biaya_omloop5");
                             biaya_dokter_pjanak=biaya_dokter_pjanak+rs.getDouble("biaya_dokter_pjanak");
                             biaya_dokter_umum=biaya_dokter_umum+rs.getDouble("biaya_dokter_umum");
                             biayaalat=biayaalat+rs.getDouble("biayaalat");
                             biayasewaok=biayasewaok+rs.getDouble("biayasewaok");
                             akomodasi=akomodasi+rs.getDouble("akomodasi");
                             bagian_rs=bagian_rs+rs.getDouble("bagian_rs");
                             biayasarpras=biayasarpras+rs.getDouble("biayasarpras");
                             total=total+rs.getDouble("biayaoperator1")+rs.getDouble("biayaoperator2")+rs.getDouble("biayaoperator3")+
                                   rs.getDouble("biayaasisten_operator1")+rs.getDouble("biayaasisten_operator2")+rs.getDouble("biayaasisten_operator3")+
                                   rs.getDouble("biayainstrumen")+rs.getDouble("biayadokter_anak")+rs.getDouble("biayaperawaat_resusitas")+
                                   rs.getDouble("biayadokter_anestesi")+rs.getDouble("biayaasisten_anestesi")+rs.getDouble("biayaasisten_anestesi2")+
                                   rs.getDouble("biayabidan")+rs.getDouble("biayabidan2")+rs.getDouble("biayabidan3")+
                                   rs.getDouble("biayaperawat_luar")+rs.getDouble("biaya_omloop")+rs.getDouble("biaya_omloop2")+
                                   rs.getDouble("biaya_omloop3")+rs.getDouble("biaya_omloop4")+rs.getDouble("biaya_omloop5")+
                                   rs.getDouble("biaya_dokter_pjanak")+rs.getDouble("biaya_dokter_umum")+rs.getDouble("biayaalat")+
                                   rs.getDouble("biayasewaok")+rs.getDouble("akomodasi")+rs.getDouble("bagian_rs")+rs.getDouble("biayasarpras");
                             tabModeOperasi.addRow(new Object[]{
                                 i,rs.getString("no_rawat"),rs.getString("no_rkm_medis"),
                                 rs.getString("nm_pasien"),rs.getString("tgl_operasi").substring(0,10),
                                 rs.getString("tgl_operasi").substring(11,19),rs.getString("png_jawab"),
                                 rs.getString("ruangan"),rs.getString("operator1"),rs.getDouble("biayaoperator1"),
                                 rs.getString("operator2"),rs.getDouble("biayaoperator2"),
                                 rs.getString("operator3"),rs.getDouble("biayaoperator3"),
                                 rs.getString("asisten_operator1"),rs.getDouble("biayaasisten_operator1"),
                                 rs.getString("asisten_operator2"),rs.getDouble("biayaasisten_operator2"),
                                 rs.getString("asisten_operator3"),rs.getDouble("biayaasisten_operator3"),
                                 rs.getString("instrumen"),rs.getDouble("biayainstrumen"),
                                 rs.getString("dokter_anak"),rs.getDouble("biayadokter_anak"),
                                 rs.getString("perawaat_resusitas"),rs.getDouble("biayaperawaat_resusitas"),
                                 rs.getString("dokter_anestesi"),rs.getDouble("biayadokter_anestesi"),
                                 rs.getString("asisten_anestesi"),rs.getDouble("biayaasisten_anestesi"),
                                 rs.getString("asisten_anestesi2"),rs.getDouble("biayaasisten_anestesi2"),
                                 rs.getString("bidan"),rs.getDouble("biayabidan"),
                                 rs.getString("bidan2"),rs.getDouble("biayabidan2"),
                                 rs.getString("bidan3"),rs.getDouble("biayabidan3"),
                                 rs.getString("perawat_luar"),rs.getDouble("biayaperawat_luar"),
                                 rs.getString("omloop"),rs.getDouble("biaya_omloop"),
                                 rs.getString("omloop2"),rs.getDouble("biaya_omloop2"),
                                 rs.getString("omloop3"),rs.getDouble("biaya_omloop3"),
                                 rs.getString("omloop4"),rs.getDouble("biaya_omloop4"),
                                 rs.getString("omloop5"),rs.getDouble("biaya_omloop5"),
                                 rs.getString("dokter_pjanak"),rs.getDouble("biaya_dokter_pjanak"),
                                 rs.getString("dokter_umum"),rs.getDouble("biaya_dokter_umum"),
                                 rs.getDouble("biayaalat"),rs.getDouble("biayasewaok"),
                                 rs.getDouble("akomodasi"),rs.getDouble("bagian_rs"),
                                 rs.getDouble("biayasarpras"),(rs.getDouble("biayaoperator1")+rs.getDouble("biayaoperator2")+rs.getDouble("biayaoperator3")+
                                 rs.getDouble("biayaasisten_operator1")+rs.getDouble("biayaasisten_operator2")+rs.getDouble("biayaasisten_operator3")+
                                 rs.getDouble("biayainstrumen")+rs.getDouble("biayadokter_anak")+rs.getDouble("biayaperawaat_resusitas")+
                                 rs.getDouble("biayadokter_anestesi")+rs.getDouble("biayaasisten_anestesi")+rs.getDouble("biayaasisten_anestesi2")+
                                 rs.getDouble("biayabidan")+rs.getDouble("biayabidan2")+rs.getDouble("biayabidan3")+
                                 rs.getDouble("biayaperawat_luar")+rs.getDouble("biaya_omloop")+rs.getDouble("biaya_omloop2")+
                                 rs.getDouble("biaya_omloop3")+rs.getDouble("biaya_omloop4")+rs.getDouble("biaya_omloop5")+
                                 rs.getDouble("biaya_dokter_pjanak")+rs.getDouble("biaya_dokter_umum")+rs.getDouble("biayaalat")+
                                 rs.getDouble("biayasewaok")+rs.getDouble("akomodasi")+rs.getDouble("bagian_rs")+rs.getDouble("biayasarpras"))
                             });
                             i++;
                         }
                         if(total>0){
                             tabModeOperasi.addRow(new Object[]{
                                 "","","","","","","","Jumlah Total :","",biayaoperator1,
                                 "",biayaoperator2,"",biayaoperator3,"",biayaasisten_operator1,
                                 "",biayaasisten_operator2,"",biayaasisten_operator3,
                                 "",biayainstrumen,"",biayadokter_anak,"",biayaperawaat_resusitas,
                                 "",biayadokter_anestesi,"",biayaasisten_anestesi,"",
                                 biayaasisten_anestesi2,"",biayabidan,"",biayabidan2,
                                 "",biayabidan3,"",biayaperawat_luar,"",biaya_omloop,
                                 "",biaya_omloop2,"",biaya_omloop3,"",biaya_omloop4,
                                 "",biaya_omloop5,"",biaya_dokter_pjanak,"",biaya_dokter_umum,
                                 biayaalat,biayasewaok,akomodasi,bagian_rs,biayasarpras,total
                             });
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
                }
            } catch (Exception e) {
                System.out.println("keuangan.DlgDetailVKOK.tampil() 1 : "+e);
            } finally{
                if(rstindakan!=null){
                    rstindakan.close();
                }
                if(pstindakan!=null){
                    pstindakan.close();
                }
            }           
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
    public void tampil2(){     
        Valid.tabelKosong(tabModeOperasi2);
        try{
            pstindakan=koneksi.prepareStatement(
                    "select operasi.kode_paket,paket_operasi.nm_perawatan from operasi inner join paket_operasi "+
                    "on operasi.kode_paket=paket_operasi.kode_paket where "+
                    "paket_operasi.kategori='Operasi' and operasi.tgl_operasi between ? and ? and operasi.kode_paket like ? or "+
                    "paket_operasi.kategori='Operasi' and operasi.tgl_operasi between ? and ? and paket_operasi.nm_perawatan like ? "+
                    "group by operasi.kode_paket order by paket_operasi.nm_perawatan ");
            try {
                pstindakan.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                pstindakan.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                pstindakan.setString(3,"%"+TCari.getText().trim()+"%");
                pstindakan.setString(4,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                pstindakan.setString(5,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                pstindakan.setString(6,"%"+TCari.getText().trim()+"%");
                rstindakan=pstindakan.executeQuery();
                while(rstindakan.next()){
                    tabModeOperasi2.addRow(new Object[]{
                        "@","Tindakan :",rstindakan.getString("kode_paket"),rstindakan.getString("nm_perawatan"),
                         "","","","","",null,"",null,"",null,"",null,"",null,
                         "",null,"",null,"",null,"",null,"",null,"",null,"",null,
                         "",null,"",null,"",null,"",null,"",null,"",null,"",null,
                         "",null,"",null,"",null,"",null,null,null,null,null,null,null
                    });
                    ps=koneksi.prepareStatement(
                            "select operasi.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien, "+
                            "operasi.kode_paket,paket_operasi.nm_perawatan,operasi.tgl_operasi, "+
                            "penjab.png_jawab,if(operasi.status='Ralan',(select nm_poli from poliklinik where poliklinik.kd_poli=reg_periksa.kd_poli),"+
                            "(select bangsal.nm_bangsal from kamar_inap inner join kamar inner join bangsal on kamar_inap.kd_kamar=kamar.kd_kamar "+
                            "and kamar.kd_bangsal=bangsal.kd_bangsal where kamar_inap.no_rawat=operasi.no_rawat limit 1 )) as ruangan,"+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.operator1) as operator1,operasi.biayaoperator1, "+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.operator2) as operator2,operasi.biayaoperator2, "+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.operator3) as operator3,operasi.biayaoperator3,"+
                            "(select nama from petugas where petugas.nip=operasi.asisten_operator1) as asisten_operator1,operasi.biayaasisten_operator1, "+
                            "(select nama from petugas where petugas.nip=operasi.asisten_operator2) as asisten_operator2,operasi.biayaasisten_operator2, "+
                            "(select nama from petugas where petugas.nip=operasi.asisten_operator3) as asisten_operator3,operasi.biayaasisten_operator3, "+
                            "(select nama from petugas where petugas.nip=operasi.instrumen) as instrumen,operasi.biayainstrumen, "+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.dokter_anak) as dokter_anak,operasi.biayadokter_anak, "+
                            "(select nama from petugas where petugas.nip=operasi.perawaat_resusitas) as perawaat_resusitas,operasi.biayaperawaat_resusitas, "+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.dokter_anestesi) as dokter_anestesi,operasi.biayadokter_anestesi, "+
                            "(select nama from petugas where petugas.nip=operasi.asisten_anestesi) as asisten_anestesi,operasi.biayaasisten_anestesi, "+
                            "(select nama from petugas where petugas.nip=operasi.asisten_anestesi2) as asisten_anestesi2,operasi.biayaasisten_anestesi2, "+
                            "(select nama from petugas where petugas.nip=operasi.bidan) as bidan,operasi.biayabidan, "+
                            "(select nama from petugas where petugas.nip=operasi.bidan2) as bidan2,operasi.biayabidan2, "+
                            "(select nama from petugas where petugas.nip=operasi.bidan3) as bidan3,operasi.biayabidan3, "+
                            "(select nama from petugas where petugas.nip=operasi.perawat_luar) as perawat_luar,operasi.biayaperawat_luar, "+
                            "(select nama from petugas where petugas.nip=operasi.omloop) as omloop,operasi.biaya_omloop, "+
                            "(select nama from petugas where petugas.nip=operasi.omloop2) as omloop2,operasi.biaya_omloop2, "+
                            "(select nama from petugas where petugas.nip=operasi.omloop3) as omloop3,operasi.biaya_omloop3, "+
                            "(select nama from petugas where petugas.nip=operasi.omloop4) as omloop4,operasi.biaya_omloop4, "+
                            "(select nama from petugas where petugas.nip=operasi.omloop5) as omloop5,operasi.biaya_omloop5, "+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.dokter_pjanak) as dokter_pjanak,operasi.biaya_dokter_pjanak, "+
                            "(select nm_dokter from dokter where dokter.kd_dokter=operasi.dokter_umum) as dokter_umum,operasi.biaya_dokter_umum, "+
                            "operasi.biayaalat,operasi.biayasewaok,operasi.akomodasi,operasi.bagian_rs,operasi.biayasarpras "+
                            "from operasi inner join reg_periksa inner join pasien "+
                            "inner join paket_operasi inner join penjab "+
                            "on operasi.no_rawat=reg_periksa.no_rawat and "+
                            "reg_periksa.no_rkm_medis=pasien.no_rkm_medis and "+
                            "operasi.kode_paket=paket_operasi.kode_paket and "+
                            "reg_periksa.kd_pj=penjab.kd_pj "+
                            "where operasi.tgl_operasi between ? and ? and operasi.kode_paket=? "+
                            "order by operasi.tgl_operasi");
                     try {
                         ps.setString(1,Valid.SetTgl(Tgl1.getSelectedItem()+"")+" 00:00:00");
                         ps.setString(2,Valid.SetTgl(Tgl2.getSelectedItem()+"")+" 23:59:59");
                         ps.setString(3,rstindakan.getString("kode_paket"));                         
                         rs=ps.executeQuery();
                         i=1;
                         total=0;biayaoperator1=0;biayaoperator2=0; 
                         biayaoperator3=0;biayaasisten_operator1=0;biayaasisten_operator2=0;
                         biayaasisten_operator3=0;biayainstrumen=0;biayadokter_anak=0;
                         biayaperawaat_resusitas=0;biayadokter_anestesi=0;biayaasisten_anestesi=0;
                         biayaasisten_anestesi2=0;biayabidan=0;biayabidan2=0;biayabidan3=0;
                         biayaperawat_luar=0;biayaalat=0;biayasewaok=0;akomodasi=0;
                         bagian_rs=0;biaya_omloop=0;biaya_omloop2=0;biaya_omloop3=0;
                         biaya_omloop4=0;biaya_omloop5=0;biayasarpras=0;biaya_dokter_pjanak=0;
                         biaya_dokter_umum=0;
                         while(rs.next()){  
                             biayaoperator1=biayaoperator1+rs.getDouble("biayaoperator1");
                             biayaoperator2=biayaoperator2+rs.getDouble("biayaoperator2");
                             biayaoperator3=biayaoperator3+rs.getDouble("biayaoperator3");
                             biayaasisten_operator1=biayaasisten_operator1+rs.getDouble("biayaasisten_operator1");
                             biayaasisten_operator2=biayaasisten_operator2+rs.getDouble("biayaasisten_operator2");
                             biayaasisten_operator3=biayaasisten_operator3+rs.getDouble("biayaasisten_operator3");
                             biayainstrumen=biayainstrumen+rs.getDouble("biayainstrumen");
                             biayadokter_anak=biayadokter_anak+rs.getDouble("biayadokter_anak");
                             biayaperawaat_resusitas=biayaperawaat_resusitas+rs.getDouble("biayaperawaat_resusitas");
                             biayadokter_anestesi=biayadokter_anestesi+rs.getDouble("biayadokter_anestesi");
                             biayaasisten_anestesi=biayaasisten_anestesi+rs.getDouble("biayaasisten_anestesi");
                             biayaasisten_anestesi2=biayaasisten_anestesi2+rs.getDouble("biayaasisten_anestesi2");
                             biayabidan=biayabidan+rs.getDouble("biayabidan");
                             biayabidan2=biayabidan2+rs.getDouble("biayabidan2");
                             biayabidan3=biayabidan3+rs.getDouble("biayabidan3");
                             biayaperawat_luar=biayaperawat_luar+rs.getDouble("biayaperawat_luar");
                             biaya_omloop=biaya_omloop+rs.getDouble("biaya_omloop");
                             biaya_omloop2=biaya_omloop2+rs.getDouble("biaya_omloop2");
                             biaya_omloop3=biaya_omloop3+rs.getDouble("biaya_omloop3");
                             biaya_omloop4=biaya_omloop4+rs.getDouble("biaya_omloop4");
                             biaya_omloop5=biaya_omloop5+rs.getDouble("biaya_omloop5");
                             biaya_dokter_pjanak=biaya_dokter_pjanak+rs.getDouble("biaya_dokter_pjanak");
                             biaya_dokter_umum=biaya_dokter_umum+rs.getDouble("biaya_dokter_umum");
                             biayaalat=biayaalat+rs.getDouble("biayaalat");
                             biayasewaok=biayasewaok+rs.getDouble("biayasewaok");
                             akomodasi=akomodasi+rs.getDouble("akomodasi");
                             bagian_rs=bagian_rs+rs.getDouble("bagian_rs");
                             biayasarpras=biayasarpras+rs.getDouble("biayasarpras");
                             total=total+rs.getDouble("biayaoperator1")+rs.getDouble("biayaoperator2")+rs.getDouble("biayaoperator3")+
                                   rs.getDouble("biayaasisten_operator1")+rs.getDouble("biayaasisten_operator2")+rs.getDouble("biayaasisten_operator3")+
                                   rs.getDouble("biayainstrumen")+rs.getDouble("biayadokter_anak")+rs.getDouble("biayaperawaat_resusitas")+
                                   rs.getDouble("biayadokter_anestesi")+rs.getDouble("biayaasisten_anestesi")+rs.getDouble("biayaasisten_anestesi2")+
                                   rs.getDouble("biayabidan")+rs.getDouble("biayabidan2")+rs.getDouble("biayabidan3")+
                                   rs.getDouble("biayaperawat_luar")+rs.getDouble("biaya_omloop")+rs.getDouble("biaya_omloop2")+
                                   rs.getDouble("biaya_omloop3")+rs.getDouble("biaya_omloop4")+rs.getDouble("biaya_omloop5")+
                                   rs.getDouble("biaya_dokter_pjanak")+rs.getDouble("biaya_dokter_umum")+rs.getDouble("biayaalat")+
                                   rs.getDouble("biayasewaok")+rs.getDouble("akomodasi")+rs.getDouble("bagian_rs")+rs.getDouble("biayasarpras");
                             tabModeOperasi2.addRow(new Object[]{
                                 i,rs.getString("no_rawat"),rs.getString("no_rkm_medis"),
                                 rs.getString("nm_pasien"),rs.getString("tgl_operasi").substring(0,10),
                                 rs.getString("tgl_operasi").substring(11,19),rs.getString("png_jawab"),
                                 rs.getString("ruangan"),rs.getString("operator1"),rs.getDouble("biayaoperator1"),
                                 rs.getString("operator2"),rs.getDouble("biayaoperator2"),
                                 rs.getString("operator3"),rs.getDouble("biayaoperator3"),
                                 rs.getString("asisten_operator1"),rs.getDouble("biayaasisten_operator1"),
                                 rs.getString("asisten_operator2"),rs.getDouble("biayaasisten_operator2"),
                                 rs.getString("asisten_operator3"),rs.getDouble("biayaasisten_operator3"),
                                 rs.getString("instrumen"),rs.getDouble("biayainstrumen"),
                                 rs.getString("dokter_anak"),rs.getDouble("biayadokter_anak"),
                                 rs.getString("perawaat_resusitas"),rs.getDouble("biayaperawaat_resusitas"),
                                 rs.getString("dokter_anestesi"),rs.getDouble("biayadokter_anestesi"),
                                 rs.getString("asisten_anestesi"),rs.getDouble("biayaasisten_anestesi"),
                                 rs.getString("asisten_anestesi2"),rs.getDouble("biayaasisten_anestesi2"),
                                 rs.getString("bidan"),rs.getDouble("biayabidan"),
                                 rs.getString("bidan2"),rs.getDouble("biayabidan2"),
                                 rs.getString("bidan3"),rs.getDouble("biayabidan3"),
                                 rs.getString("perawat_luar"),rs.getDouble("biayaperawat_luar"),
                                 rs.getString("omloop"),rs.getDouble("biaya_omloop"),
                                 rs.getString("omloop2"),rs.getDouble("biaya_omloop2"),
                                 rs.getString("omloop3"),rs.getDouble("biaya_omloop3"),
                                 rs.getString("omloop4"),rs.getDouble("biaya_omloop4"),
                                 rs.getString("omloop5"),rs.getDouble("biaya_omloop5"),
                                 rs.getString("dokter_pjanak"),rs.getDouble("biaya_dokter_pjanak"),
                                 rs.getString("dokter_umum"),rs.getDouble("biaya_dokter_umum"),
                                 rs.getDouble("biayaalat"),rs.getDouble("biayasewaok"),
                                 rs.getDouble("akomodasi"),rs.getDouble("bagian_rs"),
                                 rs.getDouble("biayasarpras"),(rs.getDouble("biayaoperator1")+rs.getDouble("biayaoperator2")+rs.getDouble("biayaoperator3")+
                                 rs.getDouble("biayaasisten_operator1")+rs.getDouble("biayaasisten_operator2")+rs.getDouble("biayaasisten_operator3")+
                                 rs.getDouble("biayainstrumen")+rs.getDouble("biayadokter_anak")+rs.getDouble("biayaperawaat_resusitas")+
                                 rs.getDouble("biayadokter_anestesi")+rs.getDouble("biayaasisten_anestesi")+rs.getDouble("biayaasisten_anestesi2")+
                                 rs.getDouble("biayabidan")+rs.getDouble("biayabidan2")+rs.getDouble("biayabidan3")+
                                 rs.getDouble("biayaperawat_luar")+rs.getDouble("biaya_omloop")+rs.getDouble("biaya_omloop2")+
                                 rs.getDouble("biaya_omloop3")+rs.getDouble("biaya_omloop4")+rs.getDouble("biaya_omloop5")+
                                 rs.getDouble("biaya_dokter_pjanak")+rs.getDouble("biaya_dokter_umum")+rs.getDouble("biayaalat")+
                                 rs.getDouble("biayasewaok")+rs.getDouble("akomodasi")+rs.getDouble("bagian_rs")+rs.getDouble("biayasarpras"))
                             });
                             i++;
                         }
                         if(total>0){
                             tabModeOperasi2.addRow(new Object[]{
                                 "","","","","","","","Jumlah Total :","",biayaoperator1,
                                 "",biayaoperator2,"",biayaoperator3,"",biayaasisten_operator1,
                                 "",biayaasisten_operator2,"",biayaasisten_operator3,
                                 "",biayainstrumen,"",biayadokter_anak,"",biayaperawaat_resusitas,
                                 "",biayadokter_anestesi,"",biayaasisten_anestesi,"",
                                 biayaasisten_anestesi2,"",biayabidan,"",biayabidan2,
                                 "",biayabidan3,"",biayaperawat_luar,"",biaya_omloop,
                                 "",biaya_omloop2,"",biaya_omloop3,"",biaya_omloop4,
                                 "",biaya_omloop5,"",biaya_dokter_pjanak,"",biaya_dokter_umum,
                                 biayaalat,biayasewaok,akomodasi,bagian_rs,biayasarpras,total
                             });
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
                }
            } catch (Exception e) {
                System.out.println("keuangan.DlgDetailVKOK.tampil() 1 : "+e);
            } finally{
                if(rstindakan!=null){
                    rstindakan.close();
                }
                if(pstindakan!=null){
                    pstindakan.close();
                }
            }           
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
    }
    
 
}
