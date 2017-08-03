package simrskhanza;
import keuangan.Jurnal;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Toolkit;
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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class DlgCariPeriksaLab extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Jurnal jur=new Jurnal();
    private Connection koneksi=koneksiDB.condb();
    private Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
    private DlgPasien member=new DlgPasien(null,false);
    private DlgCariPetugas petugas=new DlgCariPetugas(null,false);
    private int i;
    private PreparedStatement ps,ps2,ps3,ps4,psrekening;
    private ResultSet rs,rs2,rs3,rsrekening;
    private String kamar,namakamar;
    private double ttl=0,item=0;
    private double ttljmdokter=0,ttljmpetugas=0,ttlkso=0,ttlpendapatan=0,ttlbhp=0;
    private String Suspen_Piutang_Laborat_Ranap="", Laborat_Ranap="", Beban_Jasa_Medik_Dokter_Laborat_Ranap="", 
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
                column.setPreferredWidth(150);
            }else if(i==6){
                column.setPreferredWidth(150);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());

        NoRawat.setDocument(new batasInput((byte)17).getKata(NoRawat));
        kdmem.setDocument(new batasInput((byte)8).getKata(kdmem));
        kdptg.setDocument(new batasInput((byte)25).getKata(kdptg));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {tampil();}
                @Override
                public void removeUpdate(DocumentEvent e) {tampil();}
                @Override
                public void changedUpdate(DocumentEvent e) {tampil();}
            });
        } 
        
        member.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgCariPeriksaLab")){
                    if(member.getTable().getSelectedRow()!= -1){                   
                        kdmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),1).toString());
                        nmmem.setText(member.getTable().getValueAt(member.getTable().getSelectedRow(),2).toString());
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
                if(var.getform().equals("DlgCariPeriksaLab")){
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
                if(var.getform().equals("DlgCariPeriksaLab")){
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
            ps=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,petugas.nama,periksa_lab.tgl_periksa,periksa_lab.jam,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,dokter.nm_dokter from periksa_lab inner join reg_periksa inner join pasien inner join petugas inner join dokter "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.kd_dokter=dokter.kd_dokter and periksa_lab.nip=petugas.nip where "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? "+
                    "and petugas.nip like ? and pasien.nm_pasien like ? or "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? "+
                    "and petugas.nip like ? and petugas.nama like ? or "+
                    "periksa_lab.tgl_periksa between ? and ? and periksa_lab.no_rawat like ? and reg_periksa.no_rkm_medis like ? "+
                    "and petugas.nip like ? and reg_periksa.no_rkm_medis like ? group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam)");
            ps2=koneksi.prepareStatement(
                    "select jns_perawatan_lab.kd_jenis_prw,jns_perawatan_lab.nm_perawatan,periksa_lab.biaya from periksa_lab inner join jns_perawatan_lab "+
                    "on periksa_lab.kd_jenis_prw=jns_perawatan_lab.kd_jenis_prw where periksa_lab.no_rawat=? and periksa_lab.tgl_periksa=? "+
                    "and periksa_lab.jam=?");
            ps3=koneksi.prepareStatement(
                    "select template_laboratorium.Pemeriksaan, detail_periksa_lab.nilai,template_laboratorium.satuan,detail_periksa_lab.nilai_rujukan,detail_periksa_lab.biaya_item,"+
                    "detail_periksa_lab.keterangan,detail_periksa_lab.kd_jenis_prw from detail_periksa_lab inner join template_laboratorium on detail_periksa_lab.id_template=template_laboratorium.id_template "+
                    "where detail_periksa_lab.no_rawat=? and detail_periksa_lab.kd_jenis_prw=? and detail_periksa_lab.tgl_periksa=? and detail_periksa_lab.jam=?");
            ps4=koneksi.prepareStatement(
                    "select periksa_lab.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur,petugas.nama,DATE_FORMAT(periksa_lab.tgl_periksa,'%d-%m-%Y') as tgl_periksa,periksa_lab.jam,"+
                    "periksa_lab.dokter_perujuk,periksa_lab.kd_dokter,pasien.alamat,dokter.nm_dokter,DATE_FORMAT(pasien.tgl_lahir,'%d-%m-%Y') as lahir "+
                    " from periksa_lab inner join reg_periksa inner join pasien inner join petugas  inner join dokter "+
                    "on periksa_lab.no_rawat=reg_periksa.no_rawat and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and periksa_lab.nip=petugas.nip and periksa_lab.kd_dokter=dokter.kd_dokter where "+
                    "periksa_lab.tgl_periksa=? and periksa_lab.jam=? and periksa_lab.no_rawat=? group by concat(periksa_lab.no_rawat,periksa_lab.tgl_periksa,periksa_lab.jam)");
        } catch (Exception e) {
            System.out.println(e);
        }
        
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
        MnCetakHasilLab1 = new javax.swing.JMenuItem();
        MnCetakHasilLab2 = new javax.swing.JMenuItem();
        MnCetakHasilLab3 = new javax.swing.JMenuItem();
        MnCetakHasilLab4 = new javax.swing.JMenuItem();
        MnCetakHasilLab5 = new javax.swing.JMenuItem();
        MnCetakNota = new javax.swing.JMenuItem();
        MnUbah = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
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

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnCetakHasilLab.setBackground(new java.awt.Color(255, 255, 255));
        MnCetakHasilLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab.setForeground(java.awt.Color.darkGray);
        MnCetakHasilLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab.setText("Cetak Hasil Lab Model 1");
        MnCetakHasilLab.setName("MnCetakHasilLab"); // NOI18N
        MnCetakHasilLab.setPreferredSize(new java.awt.Dimension(250, 28));
        MnCetakHasilLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakHasilLab);

        MnCetakHasilLab1.setBackground(new java.awt.Color(255, 255, 255));
        MnCetakHasilLab1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab1.setForeground(java.awt.Color.darkGray);
        MnCetakHasilLab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab1.setText("Cetak Hasil Lab Model 2");
        MnCetakHasilLab1.setName("MnCetakHasilLab1"); // NOI18N
        MnCetakHasilLab1.setPreferredSize(new java.awt.Dimension(250, 28));
        MnCetakHasilLab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakHasilLab1);

        MnCetakHasilLab2.setBackground(new java.awt.Color(255, 255, 255));
        MnCetakHasilLab2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab2.setForeground(java.awt.Color.darkGray);
        MnCetakHasilLab2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab2.setText("Cetak Hasil Lab Model 3");
        MnCetakHasilLab2.setName("MnCetakHasilLab2"); // NOI18N
        MnCetakHasilLab2.setPreferredSize(new java.awt.Dimension(250, 28));
        MnCetakHasilLab2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakHasilLab2);

        MnCetakHasilLab3.setBackground(new java.awt.Color(255, 255, 255));
        MnCetakHasilLab3.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab3.setForeground(java.awt.Color.darkGray);
        MnCetakHasilLab3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab3.setText("Cetak Hasil Lab Model 4");
        MnCetakHasilLab3.setName("MnCetakHasilLab3"); // NOI18N
        MnCetakHasilLab3.setPreferredSize(new java.awt.Dimension(250, 28));
        MnCetakHasilLab3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab3ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakHasilLab3);

        MnCetakHasilLab4.setBackground(new java.awt.Color(255, 255, 255));
        MnCetakHasilLab4.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab4.setForeground(java.awt.Color.darkGray);
        MnCetakHasilLab4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab4.setText("Cetak Hasil Lab Model 5");
        MnCetakHasilLab4.setName("MnCetakHasilLab4"); // NOI18N
        MnCetakHasilLab4.setPreferredSize(new java.awt.Dimension(250, 28));
        MnCetakHasilLab4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab4ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakHasilLab4);

        MnCetakHasilLab5.setBackground(new java.awt.Color(255, 255, 255));
        MnCetakHasilLab5.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakHasilLab5.setForeground(java.awt.Color.darkGray);
        MnCetakHasilLab5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakHasilLab5.setText("Cetak Hasil Lab Model 6");
        MnCetakHasilLab5.setName("MnCetakHasilLab5"); // NOI18N
        MnCetakHasilLab5.setPreferredSize(new java.awt.Dimension(250, 28));
        MnCetakHasilLab5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakHasilLab5ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakHasilLab5);

        MnCetakNota.setBackground(new java.awt.Color(255, 255, 255));
        MnCetakNota.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakNota.setForeground(java.awt.Color.darkGray);
        MnCetakNota.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnCetakNota.setText("Cetak Nota Lab");
        MnCetakNota.setName("MnCetakNota"); // NOI18N
        MnCetakNota.setPreferredSize(new java.awt.Dimension(250, 28));
        MnCetakNota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakNotaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnCetakNota);

        MnUbah.setBackground(new java.awt.Color(255, 255, 255));
        MnUbah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnUbah.setForeground(java.awt.Color.darkGray);
        MnUbah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnUbah.setText("Ubah Periksa Lab");
        MnUbah.setName("MnUbah"); // NOI18N
        MnUbah.setPreferredSize(new java.awt.Dimension(250, 28));
        MnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnUbahActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnUbah);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Pemeriksaan Laboratorium ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 40))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

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

        Tgl1.setEditable(false);
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

        Tgl2.setEditable(false);
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

        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(100, 30));
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

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
/*
private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKdKeyPressed
    Valid.pindah(evt,BtnCari,Nm);
}//GEN-LAST:event_TKdKeyPressed
*/

    private void btnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPasienActionPerformed
        var.setform("DlgCariPeriksaLab");
        member.emptTeks();
        member.isCek();
        member.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
        member.setLocationRelativeTo(internalFrame1);
        member.setAlwaysOnTop(false);
        member.setVisible(true);
    }//GEN-LAST:event_btnPasienActionPerformed

    private void btnPetugasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPetugasActionPerformed
        var.setform("DlgCariPeriksaLab");
        petugas.emptTeks();
        petugas.isCek();
        petugas.setSize(internalFrame1.getWidth()-40,internalFrame1.getHeight()-40);
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
        NoRawat.setText("");
        kdmem.setText("");
        nmmem.setText("");
        kdptg.setText("");
        nmptg.setText("");
        tampil();
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
        BtnCariActionPerformed(evt);
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            TCari.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Sequel.AutoComitFalse();
            Sequel.queryu("delete from temporary");
            int row=tabMode.getRowCount();
            for(i=0;i<row;i++){  
                Sequel.menyimpan("temporary","'0','"+
                                tabMode.getValueAt(i,0).toString()+"','"+
                                tabMode.getValueAt(i,1).toString()+"','"+
                                tabMode.getValueAt(i,2).toString()+"','"+
                                tabMode.getValueAt(i,3).toString()+"','"+
                                tabMode.getValueAt(i,4).toString()+"','"+
                                tabMode.getValueAt(i,5).toString()+"','"+
                                tabMode.getValueAt(i,6).toString()+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Periksa Lab"); 
            }
            Sequel.AutoComitTrue();
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            Valid.MyReport("rptDataLab.jrxml","report","::[ Data Pemeriksaan Laboratorium ]::",
                "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc",param);
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
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnPrint,NoRawat);}
    }//GEN-LAST:event_BtnKeluarKeyPressed

private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else if(Kd2.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Gagal menghapus. Pilih dulu data yang mau dihapus.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            if(var.getkode().equals("Admin Utama")){
                try{
                    Sequel.AutoComitFalse();
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
                                jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),Sequel.cariIsi("select current_date()"),"U","PEMBATALAN PEMERIKSAAN LABORAT RAWAT INAP PASIEN OLEH "+var.getkode());  
                            }
                            tampil();                        
                        }
                    }
                    Sequel.AutoComitTrue();
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                    JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
                }
            }else{
                if(Sequel.cariRegistrasi(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString())>0){
                    JOptionPane.showMessageDialog(rootPane,"Data billing sudah terverifikasi, data tidak boleh dihapus.\nSilahkan hubungi bagian kasir/keuangan ..!!");
                    TCari.requestFocus();
                }else{
                    try{
                        Sequel.AutoComitFalse();
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
                                    jur.simpanJurnal(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),Sequel.cariIsi("select current_date()"),"U","PEMBATALAN PEMERIKSAAN LABORAT RAWAT INAP PASIEN OLEH "+var.getkode());  
                                }
                                tampil();                        
                            }
                        }
                        Sequel.AutoComitTrue();
                    }catch(Exception e){
                        System.out.println("Notifikasi : "+e);
                        JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih terlebih dulu data yang mau anda hapus...\n Klik data pada table untuk memilih data...!!!!");
                    }
                }
            }
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
            JOptionPane.showMessageDialog(null,"Maaf, Gagal mencteak. Pilih dulu data yang mau dicetak.\nKlik No.Rawat pada table untuk memilih...!!!!");
        }else if(!(Kd2.getText().trim().equals(""))){    
            try {                
                ps4.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                ps4.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                ps4.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                rs=ps4.executeQuery();
                while(rs.next()){
                    Sequel.AutoComitFalse();
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
                    param.put("alamat",rs.getString("Alamat"));
                    param.put("kamar",kamar);
                    param.put("namakamar",namakamar);
                    
                    Sequel.queryu("delete from temporary");
                    
                    ps2.setString(1,rs.getString("no_rawat"));
                    ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                    ps2.setString(3,rs.getString("jam"));
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        Sequel.menyimpan("temporary","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                        ps3.setString(1,rs.getString("no_rawat"));
                        ps3.setString(2,rs2.getString("kd_jenis_prw"));
                        ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                        ps3.setString(4,rs.getString("jam"));
                        rs3=ps3.executeQuery();
                        while(rs3.next()){
                            Sequel.menyimpan("temporary","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                    +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                        }
                    }
                    Sequel.AutoComitTrue();
                    param.put("namars",var.getnamars());
                    param.put("alamatrs",var.getalamatrs());
                    param.put("kotars",var.getkabupatenrs());
                    param.put("propinsirs",var.getpropinsirs());
                    param.put("kontakrs",var.getkontakrs());
                    param.put("emailrs",var.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptPeriksaLab.jrxml","report","::[ Pemeriksaan Laboratorium ]::",
                                   "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc",param);            
                    
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }            
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCetakHasilLabActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnCetakNotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakNotaActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis...!!!!");
            TCari.requestFocus();
        }else {
            try {
                Sequel.AutoComitFalse();
                ps4.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                ps4.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                ps4.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                rs=ps4.executeQuery();
                if(rs.next()){
                    Sequel.queryu("delete from temporary");
                    ps2.setString(1,rs.getString("no_rawat"));
                    ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                    ps2.setString(3,rs.getString("jam"));
                    rs2=ps2.executeQuery();
                    ttl=0;
                    while(rs2.next()){
                        item=rs2.getDouble("biaya");//Sequel.cariIsiAngka("select sum(biaya_item) from template_laboratorium where kd_jenis_prw=?",rs2.getString("kd_jenis_prw"));
                        ttl=ttl+item;                    
                        Sequel.menyimpan("temporary","'0','"+rs2.getString("kd_jenis_prw")+"','"+rs2.getString("nm_perawatan")+"','"+item+"','Pemeriksaan','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Biaya Lab");                        
                        ps3.setString(1,rs.getString("no_rawat"));
                        ps3.setString(2,rs2.getString("kd_jenis_prw"));
                        ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                        ps3.setString(4,rs.getString("jam"));
                        rs3=ps3.executeQuery();
                        while(rs3.next()){
                            item=rs3.getDouble("biaya_item");
                            ttl=ttl+item; 
                            Sequel.menyimpan("temporary","'0','"+rs3.getString("kd_jenis_prw")+"','   "+rs3.getString("Pemeriksaan")+"','"+item+"','Detail Pemeriksaan','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Biaya Lab");                        
                        }
                    }                       
                    Sequel.menyimpan("temporary","'0','','Total Biaya Pemeriksaan Lab','"+ttl+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Transaksi Biaya Lab");
                    Valid.panggilUrl("billing/LaporanBiayaLab.php?norm="+rs.getString("no_rkm_medis")+"&pasien="+rs.getString("nm_pasien").replaceAll(" ","_")
                            +"&tanggal="+rs.getString("tgl_periksa")+"&jam="+rs.getString("jam")+"&pjlab="+rs.getString("nm_dokter").replaceAll(" ","_")
                            +"&petugas="+rs.getString("nama").replaceAll(" ","_")+"&kasir="+Sequel.cariIsi("select nama from pegawai where nik=?",var.getkode()));
                }  
                Sequel.AutoComitTrue();
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
        }else {
            DlgUbahPeriksaLab ubah=new DlgUbahPeriksaLab(null,false);
            ubah.setSize(this.getWidth()-40,this.getHeight()-40);
            ubah.setNoRm(tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString(),
                    tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString(), 
                    tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
            ubah.setLocationRelativeTo(this);
            ubah.setVisible(true);
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
                ps4.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                ps4.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                ps4.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                rs=ps4.executeQuery();
                while(rs.next()){
                    Sequel.AutoComitFalse();
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
                    param.put("alamat",rs.getString("Alamat"));
                    param.put("kamar",kamar);
                    param.put("namakamar",namakamar);
                    
                    Sequel.queryu("delete from temporary");
                    
                    ps2.setString(1,rs.getString("no_rawat"));
                    ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                    ps2.setString(3,rs.getString("jam"));
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        Sequel.menyimpan("temporary","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                        ps3.setString(1,rs.getString("no_rawat"));
                        ps3.setString(2,rs2.getString("kd_jenis_prw"));
                        ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                        ps3.setString(4,rs.getString("jam"));
                        rs3=ps3.executeQuery();
                        while(rs3.next()){
                            Sequel.menyimpan("temporary","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                    +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                        }
                    }
                    Sequel.AutoComitTrue();
                    param.put("namars",var.getnamars());
                    param.put("alamatrs",var.getalamatrs());
                    param.put("kotars",var.getkabupatenrs());
                    param.put("propinsirs",var.getpropinsirs());
                    param.put("kontakrs",var.getkontakrs());
                    param.put("emailrs",var.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptPeriksaLab2.jrxml","report","::[ Pemeriksaan Laboratorium ]::",
                                   "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc",param);            
                    
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
                ps4.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                ps4.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                ps4.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                rs=ps4.executeQuery();
                while(rs.next()){
                    Sequel.AutoComitFalse();
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
                    param.put("alamat",rs.getString("Alamat"));
                    param.put("kamar",kamar);
                    param.put("namakamar",namakamar);
                    
                    Sequel.queryu("delete from temporary");
                    
                    ps2.setString(1,rs.getString("no_rawat"));
                    ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                    ps2.setString(3,rs.getString("jam"));
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        Sequel.menyimpan("temporary","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                        ps3.setString(1,rs.getString("no_rawat"));
                        ps3.setString(2,rs2.getString("kd_jenis_prw"));
                        ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                        ps3.setString(4,rs.getString("jam"));
                        rs3=ps3.executeQuery();
                        while(rs3.next()){
                            Sequel.menyimpan("temporary","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                    +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                        }
                    }
                    Sequel.AutoComitTrue();
                    param.put("namars",var.getnamars());
                    param.put("alamatrs",var.getalamatrs());
                    param.put("kotars",var.getkabupatenrs());
                    param.put("propinsirs",var.getpropinsirs());
                    param.put("kontakrs",var.getkontakrs());
                    param.put("emailrs",var.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptPeriksaLab3.jrxml","report","::[ Pemeriksaan Laboratorium ]::",
                                   "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc",param);            
                    
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
                ps4.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                ps4.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                ps4.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                rs=ps4.executeQuery();
                while(rs.next()){
                    Sequel.AutoComitFalse();
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
                    param.put("alamat",rs.getString("Alamat"));
                    param.put("kamar",kamar);
                    param.put("namakamar",namakamar);
                    
                    Sequel.queryu("delete from temporary");
                    
                    ps2.setString(1,rs.getString("no_rawat"));
                    ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                    ps2.setString(3,rs.getString("jam"));
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        Sequel.menyimpan("temporary","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                        ps3.setString(1,rs.getString("no_rawat"));
                        ps3.setString(2,rs2.getString("kd_jenis_prw"));
                        ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                        ps3.setString(4,rs.getString("jam"));
                        rs3=ps3.executeQuery();
                        while(rs3.next()){
                            Sequel.menyimpan("temporary","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                    +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                        }
                    }
                    Sequel.AutoComitTrue();
                    param.put("namars",var.getnamars());
                    param.put("alamatrs",var.getalamatrs());
                    param.put("kotars",var.getkabupatenrs());
                    param.put("propinsirs",var.getpropinsirs());
                    param.put("kontakrs",var.getkontakrs());
                    param.put("emailrs",var.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptPeriksaLab4.jrxml","report","::[ Pemeriksaan Laboratorium ]::",
                                   "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc",param);            
                    
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
                ps4.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                ps4.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                ps4.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                rs=ps4.executeQuery();
                while(rs.next()){
                    Sequel.AutoComitFalse();
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
                    param.put("alamat",rs.getString("Alamat"));
                    param.put("kamar",kamar);
                    param.put("namakamar",namakamar);
                    
                    Sequel.queryu("delete from temporary");
                    
                    ps2.setString(1,rs.getString("no_rawat"));
                    ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                    ps2.setString(3,rs.getString("jam"));
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        Sequel.menyimpan("temporary","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                        ps3.setString(1,rs.getString("no_rawat"));
                        ps3.setString(2,rs2.getString("kd_jenis_prw"));
                        ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                        ps3.setString(4,rs.getString("jam"));
                        rs3=ps3.executeQuery();
                        while(rs3.next()){
                            Sequel.menyimpan("temporary","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                    +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                        }
                    }
                    Sequel.AutoComitTrue();
                    param.put("namars",var.getnamars());
                    param.put("alamatrs",var.getalamatrs());
                    param.put("kotars",var.getkabupatenrs());
                    param.put("propinsirs",var.getpropinsirs());
                    param.put("kontakrs",var.getkontakrs());
                    param.put("emailrs",var.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptPeriksaLab5.jrxml","report","::[ Pemeriksaan Laboratorium ]::",
                                   "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc",param);            
                    
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
                ps4.setString(1,tbDokter.getValueAt(tbDokter.getSelectedRow(),3).toString());
                ps4.setString(2,tbDokter.getValueAt(tbDokter.getSelectedRow(),4).toString());
                ps4.setString(3,tbDokter.getValueAt(tbDokter.getSelectedRow(),0).toString());
                rs=ps4.executeQuery();
                while(rs.next()){
                    Sequel.AutoComitFalse();
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
                    param.put("alamat",rs.getString("Alamat"));
                    param.put("kamar",kamar);
                    param.put("namakamar",namakamar);
                    
                    Sequel.queryu("delete from temporary");
                    
                    ps2.setString(1,rs.getString("no_rawat"));
                    ps2.setString(2,Valid.SetTgl(rs.getString("tgl_periksa")));
                    ps2.setString(3,rs.getString("jam"));
                    rs2=ps2.executeQuery();
                    while(rs2.next()){
                        Sequel.menyimpan("temporary","'0','"+rs2.getString("nm_perawatan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                        ps3.setString(1,rs.getString("no_rawat"));
                        ps3.setString(2,rs2.getString("kd_jenis_prw"));
                        ps3.setString(3,Valid.SetTgl(rs.getString("tgl_periksa")));
                        ps3.setString(4,rs.getString("jam"));
                        rs3=ps3.executeQuery();
                        while(rs3.next()){
                            Sequel.menyimpan("temporary","'0','  "+rs3.getString("Pemeriksaan")+"','"+rs3.getString("nilai")+"','"+rs3.getString("satuan")
                                    +"','"+rs3.getString("nilai_rujukan")+"','"+rs3.getString("keterangan")+"','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''","Data User"); 
                        }
                    }
                    Sequel.AutoComitTrue();
                    param.put("namars",var.getnamars());
                    param.put("alamatrs",var.getalamatrs());
                    param.put("kotars",var.getkabupatenrs());
                    param.put("propinsirs",var.getpropinsirs());
                    param.put("kontakrs",var.getkontakrs());
                    param.put("emailrs",var.getemailrs());   
                    param.put("logo",Sequel.cariGambar("select logo from setting")); 
                    Valid.MyReport("rptPeriksaLab6.jrxml","report","::[ Pemeriksaan Laboratorium ]::",
                                   "select no, temp1, temp2, temp3, temp4, temp5, temp6, temp7, temp8, temp9, temp10, temp11, temp12, temp13, temp14, temp14, temp15, temp16 from temporary order by no asc",param);            
                    
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }            
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_MnCetakHasilLab5ActionPerformed

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
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.TextBox Kd2;
    private javax.swing.JMenuItem MnCetakHasilLab;
    private javax.swing.JMenuItem MnCetakHasilLab1;
    private javax.swing.JMenuItem MnCetakHasilLab2;
    private javax.swing.JMenuItem MnCetakHasilLab3;
    private javax.swing.JMenuItem MnCetakHasilLab4;
    private javax.swing.JMenuItem MnCetakHasilLab5;
    private javax.swing.JMenuItem MnCetakNota;
    private javax.swing.JMenuItem MnUbah;
    private widget.TextBox NoRawat;
    private widget.TextBox TCari;
    private widget.Tanggal Tgl1;
    private widget.Tanggal Tgl2;
    private widget.Button btnPasien;
    private widget.Button btnPetugas;
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
    private widget.panelisi panelisi3;
    private widget.ScrollPane scrollPane1;
    private widget.Table tbDokter;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        try {
            Valid.tabelKosong(tabMode);            
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
                ps2.setString(1,rs.getString("no_rawat"));
                ps2.setString(2,rs.getString("tgl_periksa"));
                ps2.setString(3,rs.getString("jam"));
                rs2=ps2.executeQuery();
                item=0;
                while(rs2.next()){     
                   item=item+rs2.getDouble("biaya");
                   ttl=ttl+rs2.getDouble("biaya");
                   tabMode.addRow(new Object[]{"","",rs2.getString("kd_jenis_prw")+" "+rs2.getString("nm_perawatan")+" "+Valid.SetAngka(rs2.getDouble("biaya")),"","","",""});
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
                }
                if(item>0){
                    tabMode.addRow(new Object[]{"","","Biaya Periksa : "+Valid.SetAngka(item),"","","",""});
                }                
            }
            if(ttl>0){
                  tabMode.addRow(new Object[]{">>","Total : "+Valid.SetAngka(ttl),"","","","",""});
            }  
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
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
        MnCetakHasilLab.setEnabled(var.getperiksa_lab());
        MnCetakNota.setEnabled(var.getperiksa_lab());
        MnUbah.setEnabled(var.getperiksa_lab());
        BtnHapus.setEnabled(var.getperiksa_lab());
        BtnPrint.setEnabled(var.getperiksa_lab());
    }
    
    public void setPasien(String pasien){
        NoRawat.setText(pasien);
    }
 

 
}
