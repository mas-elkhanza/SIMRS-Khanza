/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package inventory;

import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import restore.DlgRestoreObat;
import simrskhanza.DlgCariBangsal;

public class DlgBarang extends javax.swing.JDialog {

    private DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    public DlgCariJenis jenis = new DlgCariJenis(null, false);
    public DlgCariKategori kategori = new DlgCariKategori(null, false);
    public DlgCariGolongan golongan = new DlgCariGolongan(null, false);
    public DlgCariSatuan satuan = new DlgCariSatuan(null, false);
    public DlgIndustriFarmasi industri=new DlgIndustriFarmasi(null,false);
    private DlgCariBangsal bangsal = new DlgCariBangsal(null, false);
    private double totalstok, stokgudang;
    private PreparedStatement ps, ps2, ps3, ps4;
    private ResultSet rs, rs2, rs3;
    private int i = 0;
    public String aktifkanbatch="no",pengaturanharga=Sequel.cariIsi("select setharga from set_harga_obat");;
    private String kdlokasi = "", nmlokasi = "", tanggal = "0000-00-00";


    public DlgBarang(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode = new DefaultTableModel(null,new Object[]{
            "P", "Kode Barang", "Nama Barang", "Kd.Sat Besar", "Nm.Satuan Besar","Isi", "Kd.Sat Kecil", "Nm.Satuan Kecil",
            "Kps", "Kandungan","Hrg.Dasar(Rp)","Hrg.Beli(Rp)", "Ralan(Rp)", "Ranap K1(Rp)", "Ranap K2(Rp)", "Ranap K3(Rp)",
            "Kelas Utama/BPJS(Rp)", "Ranap VIP(Rp)", "Ranap VVIP(Rp)", "Beli Luar(Rp)","Jual Bebas(Rp)", "Karyawan(Rp)", 
            "Stok Min", "Kode Jenis", "Nama Jenis","Kadaluwarsa","Kode I.F.","Industri Farmasi","Kode Kategori","Kategori",
            "Kode Golongan","Golongan"
        }) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                boolean a = false;
                if (colIndex == 0) {
                    a = true;
                }
                return a;
            }
            Class[] types = new Class[]{
                java.lang.Boolean.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Double.class,java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                java.lang.Object.class,java.lang.Object.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        };
        tbDokter.setModel(tabMode);

        tbDokter.setPreferredScrollableViewportSize(new Dimension(800, 800));
        tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 32; i++) {
            TableColumn column = tbDokter.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(20);
            } else if (i == 1) {
                column.setPreferredWidth(85);
            } else if (i == 2) {
                column.setPreferredWidth(200);
            } else if (i == 3) {
                column.setPreferredWidth(68);
            } else if (i == 4) {
                column.setPreferredWidth(90);
            } else if (i == 5) {
                column.setPreferredWidth(37);
            } else if (i == 6) {
                column.setPreferredWidth(68);
            } else if (i == 7) {
                column.setPreferredWidth(90);
            } else if (i == 8) {
                column.setPreferredWidth(37);
            } else if (i == 9) {
                column.setPreferredWidth(120);
            } else if (i == 10) {
                column.setPreferredWidth(85);
            } else if (i == 11) {
                column.setPreferredWidth(85);
            } else if (i == 12) {
                column.setPreferredWidth(85);
            } else if (i == 13) {
                column.setPreferredWidth(85);
            } else if (i == 14) {
                column.setPreferredWidth(85);
            } else if (i == 15) {
                column.setPreferredWidth(85);
            } else if (i == 16) {
                column.setPreferredWidth(85);
            } else if (i == 17) {
                column.setPreferredWidth(85);
            } else if (i == 18) {
                column.setPreferredWidth(85);
            } else if (i == 19) {
                column.setPreferredWidth(85);
            } else if (i == 20) {
                column.setPreferredWidth(85);
            } else if (i == 21) {
                column.setPreferredWidth(85);
            } else if (i == 22) {
                column.setPreferredWidth(50);
            } else if (i == 23) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 24) {
                column.setPreferredWidth(120);
            } else if (i == 25) {
                column.setPreferredWidth(70);
            } else if (i == 26) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 27) {
                column.setPreferredWidth(120);
            } else if (i == 28) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 29) {
                column.setPreferredWidth(120);
            } else if (i == 30) {
                column.setMinWidth(0);
                column.setMaxWidth(0);
            } else if (i == 31) {
                column.setPreferredWidth(120);
            }
        }
        tbDokter.setDefaultRenderer(Object.class, new WarnaTable());   
        
        Kd.setDocument(new batasInput((byte) 15).getKata(Kd));
        Nm.setDocument(new batasInput((byte) 80).getKata(Nm));
        kdsat.setDocument(new batasInput((byte) 4).getKata(kdsat));
        kdsatBesar.setDocument(new batasInput((byte) 4).getKata(kdsatBesar));
        Letak.setDocument(new batasInput((byte) 50).getKata(Letak));
        dasar.setDocument(new batasInput((byte) 15).getKata(dasar));
        beli.setDocument(new batasInput((byte) 15).getKata(beli));
        ralan.setDocument(new batasInput((byte) 15).getKata(ralan));
        kelas1.setDocument(new batasInput((byte) 15).getKata(kelas1));
        kelas2.setDocument(new batasInput((byte) 15).getKata(kelas2));
        kelas3.setDocument(new batasInput((byte) 15).getKata(kelas3));
        utama.setDocument(new batasInput((byte) 15).getKata(utama));
        kelasvip.setDocument(new batasInput((byte) 15).getKata(kelasvip));
        kelasvvip.setDocument(new batasInput((byte) 15).getKata(kelasvvip));
        jualbebas.setDocument(new batasInput((byte) 15).getKata(jualbebas));
        beliluar.setDocument(new batasInput((byte) 15).getKata(beliluar));
        karyawan.setDocument(new batasInput((byte) 15).getKata(karyawan));
        Kapasitas.setDocument(new batasInput((byte) 15).getKata(Kapasitas));
        Isi.setDocument(new batasInput((byte) 15).getKata(Isi));
        stok_minimal.setDocument(new batasInput((byte) 15).getKata(stok_minimal));
        kdjns.setDocument(new batasInput((byte) 5).getKata(kdjns));
        kdgolongan.setDocument(new batasInput((byte) 4).getKata(kdgolongan));
        kdkategori.setDocument(new batasInput((byte) 4).getKata(kdkategori));
        KdIF.setDocument(new batasInput((byte) 5).getKata(KdIF));

        ChkInput.setSelected(false);
        isForm();

        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (bangsal.getTable().getSelectedRow() != -1) {
                    kdlokasi = bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 0).toString();
                    nmlokasi = bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(), 1).toString();
                    tampil3();
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                bangsal.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        jenis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (jenis.getTable().getSelectedRow() != -1) {
                    kdjns.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 0).toString());
                    nmjns.setText(jenis.getTable().getValueAt(jenis.getTable().getSelectedRow(), 1).toString());
                }
                BtnJenis.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });

        satuan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgBarang")) {
                    if (satuan.getTable().getSelectedRow() != -1) {
                        if(i==1){
                            kdsat.setText(satuan.getTable().getValueAt(satuan.getTable().getSelectedRow(), 0).toString());
                            nmsat.setText(satuan.getTable().getValueAt(satuan.getTable().getSelectedRow(), 1).toString());
                            BtnSatuan.requestFocus();
                        }else{
                            kdsatBesar.setText(satuan.getTable().getValueAt(satuan.getTable().getSelectedRow(), 0).toString());
                            nmsatBesar.setText(satuan.getTable().getValueAt(satuan.getTable().getSelectedRow(), 1).toString());
                            BtnSatuanBesar.requestFocus();
                        }
                            
                    }
                }
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                satuan.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        golongan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgBarang")) {
                    if (golongan.getTable().getSelectedRow() != -1) {
                        kdgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 0).toString());
                        nmgolongan.setText(golongan.getTable().getValueAt(golongan.getTable().getSelectedRow(), 1).toString());
                    }
                }
                BtnGolongan.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                golongan.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        kategori.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
            }

            @Override
            public void windowClosed(WindowEvent e) {
                if (akses.getform().equals("DlgBarang")) {
                    if (kategori.getTable().getSelectedRow() != -1) {
                        kdkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 0).toString());
                        nmkategori.setText(kategori.getTable().getValueAt(kategori.getTable().getSelectedRow(), 1).toString());
                    }
                }
                BtnKategori.requestFocus();
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
                kategori.emptTeks();
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
        });
        
        industri.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(akses.getform().equals("DlgBarang")){
                    if(industri.getTable().getSelectedRow()!= -1){                   
                        KdIF.setText(industri.getTable().getValueAt(industri.getTable().getSelectedRow(),0).toString());                    
                        NmIF.setText(industri.getTable().getValueAt(industri.getTable().getSelectedRow(),1).toString());
                    }  
                    BtnIF.requestFocus();
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
        
        industri.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(akses.getform().equals("DlgBarang")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        industri.dispose();
                    }     
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });    
        
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
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppBarcode = new javax.swing.JMenuItem();
        ppBarcodeItem = new javax.swing.JMenuItem();
        ppStok = new javax.swing.JMenuItem();
        ppStok2 = new javax.swing.JMenuItem();
        MnRestore = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel2 = new javax.swing.JPanel();
        panelisi2 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        label10 = new widget.Label();
        LCount = new widget.Label();
        panelisi1 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        scrollPane1 = new widget.ScrollPane();
        tbDokter = new widget.Table();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        label12 = new widget.Label();
        Kd = new widget.TextBox();
        Nm = new widget.TextBox();
        label18 = new widget.Label();
        label26 = new widget.Label();
        kelas1 = new widget.TextBox();
        label28 = new widget.Label();
        kelas2 = new widget.TextBox();
        label31 = new widget.Label();
        Letak = new widget.TextBox();
        label27 = new widget.Label();
        ralan = new widget.TextBox();
        nmsat = new widget.TextBox();
        label19 = new widget.Label();
        kdsat = new widget.TextBox();
        BtnSatuan = new widget.Button();
        label14 = new widget.Label();
        stok_minimal = new widget.TextBox();
        label20 = new widget.Label();
        kdjns = new widget.TextBox();
        nmjns = new widget.TextBox();
        BtnJenis = new widget.Button();
        label29 = new widget.Label();
        beli = new widget.TextBox();
        label32 = new widget.Label();
        Kapasitas = new widget.TextBox();
        kelas3 = new widget.TextBox();
        label30 = new widget.Label();
        label33 = new widget.Label();
        utama = new widget.TextBox();
        kelasvip = new widget.TextBox();
        label34 = new widget.Label();
        label35 = new widget.Label();
        kelasvvip = new widget.TextBox();
        label36 = new widget.Label();
        beliluar = new widget.TextBox();
        jualbebas = new widget.TextBox();
        label37 = new widget.Label();
        label38 = new widget.Label();
        karyawan = new widget.TextBox();
        DTPExpired = new widget.Tanggal();
        ChkKadaluarsa = new widget.CekBox();
        label21 = new widget.Label();
        KdIF = new widget.TextBox();
        NmIF = new widget.TextBox();
        BtnIF = new widget.Button();
        label22 = new widget.Label();
        kdkategori = new widget.TextBox();
        nmkategori = new widget.TextBox();
        BtnKategori = new widget.Button();
        nmgolongan = new widget.TextBox();
        kdgolongan = new widget.TextBox();
        BtnGolongan = new widget.Button();
        label23 = new widget.Label();
        label24 = new widget.Label();
        kdsatBesar = new widget.TextBox();
        nmsatBesar = new widget.TextBox();
        BtnSatuanBesar = new widget.Button();
        label39 = new widget.Label();
        Isi = new widget.TextBox();
        dasar = new widget.TextBox();
        label40 = new widget.Label();
        ChkInput = new widget.CekBox();

        Popup.setName("Popup"); // NOI18N

        ppBarcode.setBackground(new java.awt.Color(255, 255, 254));
        ppBarcode.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBarcode.setForeground(new java.awt.Color(50, 50, 50));
        ppBarcode.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBarcode.setText("Barcode");
        ppBarcode.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBarcode.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBarcode.setName("ppBarcode"); // NOI18N
        ppBarcode.setPreferredSize(new java.awt.Dimension(200, 26));
        ppBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBarcodeBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppBarcode);

        ppBarcodeItem.setBackground(new java.awt.Color(255, 255, 254));
        ppBarcodeItem.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBarcodeItem.setForeground(new java.awt.Color(50, 50, 50));
        ppBarcodeItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBarcodeItem.setText("Barcode Perbarang");
        ppBarcodeItem.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBarcodeItem.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBarcodeItem.setName("ppBarcodeItem"); // NOI18N
        ppBarcodeItem.setPreferredSize(new java.awt.Dimension(200, 26));
        ppBarcodeItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBarcodeItemBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppBarcodeItem);

        ppStok.setBackground(new java.awt.Color(255, 255, 254));
        ppStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok.setForeground(new java.awt.Color(50, 50, 50));
        ppStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok.setText("Tampilkan Semua Stok");
        ppStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok.setName("ppStok"); // NOI18N
        ppStok.setPreferredSize(new java.awt.Dimension(200, 26));
        ppStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStokBtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppStok);

        ppStok2.setBackground(new java.awt.Color(255, 255, 254));
        ppStok2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok2.setForeground(new java.awt.Color(50, 50, 50));
        ppStok2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok2.setText("Tampilkan Stok Per Lokasi");
        ppStok2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok2.setName("ppStok2"); // NOI18N
        ppStok2.setPreferredSize(new java.awt.Dimension(200, 26));
        ppStok2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStok2BtnPrintActionPerformed(evt);
            }
        });
        Popup.add(ppStok2);

        MnRestore.setBackground(new java.awt.Color(255, 255, 254));
        MnRestore.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRestore.setForeground(new java.awt.Color(50, 50, 50));
        MnRestore.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnRestore.setText("Data Sampah");
        MnRestore.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRestore.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRestore.setName("MnRestore"); // NOI18N
        MnRestore.setPreferredSize(new java.awt.Dimension(200, 26));
        MnRestore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRestoreActionPerformed(evt);
            }
        });
        Popup.add(MnRestore);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setPreferredSize(new java.awt.Dimension(816, 100));
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi2.setBackground(new java.awt.Color(255, 150, 255));
        panelisi2.setName("panelisi2"); // NOI18N
        panelisi2.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label9);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(400, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi2.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('3');
        BtnCari.setToolTipText("Alt+3");
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
        panelisi2.add(BtnCari);

        label10.setText("Record :");
        label10.setName("label10"); // NOI18N
        label10.setPreferredSize(new java.awt.Dimension(70, 23));
        panelisi2.add(label10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(60, 23));
        panelisi2.add(LCount);

        jPanel2.add(panelisi2, java.awt.BorderLayout.PAGE_START);

        panelisi1.setName("panelisi1"); // NOI18N
        panelisi1.setPreferredSize(new java.awt.Dimension(100, 44));
        panelisi1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
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
        panelisi1.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelisi1.add(BtnBatal);

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

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelisi1.add(BtnEdit);

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

        jPanel2.add(panelisi1, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        scrollPane1.setComponentPopupMenu(Popup);
        scrollPane1.setName("scrollPane1"); // NOI18N
        scrollPane1.setOpaque(true);

        tbDokter.setAutoCreateRowSorter(true);
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
        tbDokter.setComponentPopupMenu(Popup);
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
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbDokterKeyReleased(evt);
            }
        });
        scrollPane1.setViewportView(tbDokter);

        internalFrame1.add(scrollPane1, java.awt.BorderLayout.CENTER);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setPreferredSize(new java.awt.Dimension(660, 338));
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(660, 257));
        FormInput.setLayout(null);

        label12.setText("Kode Barang :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label12);
        label12.setBounds(8, 12, 88, 23);

        Kd.setName("Kd"); // NOI18N
        Kd.setPreferredSize(new java.awt.Dimension(207, 23));
        Kd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdKeyPressed(evt);
            }
        });
        FormInput.add(Kd);
        Kd.setBounds(100, 12, 100, 23);

        Nm.setName("Nm"); // NOI18N
        Nm.setPreferredSize(new java.awt.Dimension(207, 23));
        Nm.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NmKeyPressed(evt);
            }
        });
        FormInput.add(Nm);
        Nm.setBounds(100, 42, 380, 23);

        label18.setText("Nama Barang :");
        label18.setName("label18"); // NOI18N
        label18.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label18);
        label18.setBounds(8, 42, 88, 23);

        label26.setText("Hrg Rnp Kelas 1 : Rp.");
        label26.setName("label26"); // NOI18N
        label26.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label26);
        label26.setBounds(240, 282, 128, 23);

        kelas1.setName("kelas1"); // NOI18N
        kelas1.setPreferredSize(new java.awt.Dimension(207, 23));
        kelas1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                kelas1MouseMoved(evt);
            }
        });
        kelas1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kelas1MouseExited(evt);
            }
        });
        kelas1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelas1KeyPressed(evt);
            }
        });
        FormInput.add(kelas1);
        kelas1.setBounds(370, 282, 110, 23);

        label28.setText("Hrg Rnp Kelas 2 : Rp.");
        label28.setName("label28"); // NOI18N
        label28.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label28);
        label28.setBounds(480, 12, 148, 23);

        kelas2.setName("kelas2"); // NOI18N
        kelas2.setPreferredSize(new java.awt.Dimension(207, 23));
        kelas2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                kelas2MouseMoved(evt);
            }
        });
        kelas2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kelas2MouseExited(evt);
            }
        });
        kelas2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelas2KeyPressed(evt);
            }
        });
        FormInput.add(kelas2);
        kelas2.setBounds(630, 12, 110, 23);

        label31.setText("Kandungan :");
        label31.setName("label31"); // NOI18N
        label31.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label31);
        label31.setBounds(8, 72, 88, 23);

        Letak.setName("Letak"); // NOI18N
        Letak.setPreferredSize(new java.awt.Dimension(207, 23));
        Letak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LetakKeyPressed(evt);
            }
        });
        FormInput.add(Letak);
        Letak.setBounds(100, 72, 380, 23);

        label27.setText("Hrg Ralan : Rp.");
        label27.setName("label27"); // NOI18N
        label27.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label27);
        label27.setBounds(8, 282, 107, 23);

        ralan.setName("ralan"); // NOI18N
        ralan.setPreferredSize(new java.awt.Dimension(207, 23));
        ralan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                ralanMouseMoved(evt);
            }
        });
        ralan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ralanMouseExited(evt);
            }
        });
        ralan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ralanKeyPressed(evt);
            }
        });
        FormInput.add(ralan);
        ralan.setBounds(117, 282, 110, 23);

        nmsat.setEditable(false);
        nmsat.setName("nmsat"); // NOI18N
        nmsat.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmsat);
        nmsat.setBounds(162, 132, 140, 23);

        label19.setText("Satuan Kecil :");
        label19.setName("label19"); // NOI18N
        label19.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label19);
        label19.setBounds(8, 132, 88, 23);

        kdsat.setEditable(false);
        kdsat.setName("kdsat"); // NOI18N
        kdsat.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(kdsat);
        kdsat.setBounds(100, 132, 60, 23);

        BtnSatuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSatuan.setMnemonic('1');
        BtnSatuan.setToolTipText("Alt+1");
        BtnSatuan.setName("BtnSatuan"); // NOI18N
        BtnSatuan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSatuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSatuanActionPerformed(evt);
            }
        });
        BtnSatuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSatuanKeyPressed(evt);
            }
        });
        FormInput.add(BtnSatuan);
        BtnSatuan.setBounds(305, 132, 25, 23);

        label14.setText("Stok Minimal Barang :");
        label14.setName("label14"); // NOI18N
        label14.setPreferredSize(new java.awt.Dimension(85, 23));
        FormInput.add(label14);
        label14.setBounds(501, 252, 135, 23);

        stok_minimal.setHighlighter(null);
        stok_minimal.setName("stok_minimal"); // NOI18N
        stok_minimal.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                stok_minimalMouseMoved(evt);
            }
        });
        stok_minimal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                stok_minimalMouseExited(evt);
            }
        });
        stok_minimal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                stok_minimalKeyPressed(evt);
            }
        });
        FormInput.add(stok_minimal);
        stok_minimal.setBounds(640, 252, 100, 23);

        label20.setText("Jenis :");
        label20.setName("label20"); // NOI18N
        label20.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label20);
        label20.setBounds(8, 162, 88, 23);

        kdjns.setEditable(false);
        kdjns.setName("kdjns"); // NOI18N
        kdjns.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(kdjns);
        kdjns.setBounds(100, 162, 90, 23);

        nmjns.setEditable(false);
        nmjns.setName("nmjns"); // NOI18N
        nmjns.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmjns);
        nmjns.setBounds(192, 162, 260, 23);

        BtnJenis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenis.setMnemonic('2');
        BtnJenis.setToolTipText("Alt+2");
        BtnJenis.setName("BtnJenis"); // NOI18N
        BtnJenis.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnJenis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisActionPerformed(evt);
            }
        });
        BtnJenis.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnJenisKeyPressed(evt);
            }
        });
        FormInput.add(BtnJenis);
        BtnJenis.setBounds(455, 162, 25, 23);

        label29.setText("Harga Beli : Rp.");
        label29.setName("label29"); // NOI18N
        label29.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label29);
        label29.setBounds(240, 252, 128, 23);

        beli.setName("beli"); // NOI18N
        beli.setPreferredSize(new java.awt.Dimension(207, 23));
        beli.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                beliMouseMoved(evt);
            }
        });
        beli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                beliMouseExited(evt);
            }
        });
        beli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                beliKeyPressed(evt);
            }
        });
        FormInput.add(beli);
        beli.setBounds(370, 252, 110, 23);

        label32.setText("Kapasitas :");
        label32.setName("label32"); // NOI18N
        label32.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label32);
        label32.setBounds(338, 132, 69, 23);

        Kapasitas.setName("Kapasitas"); // NOI18N
        Kapasitas.setPreferredSize(new java.awt.Dimension(207, 23));
        Kapasitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KapasitasKeyPressed(evt);
            }
        });
        FormInput.add(Kapasitas);
        Kapasitas.setBounds(410, 132, 70, 23);

        kelas3.setName("kelas3"); // NOI18N
        kelas3.setPreferredSize(new java.awt.Dimension(207, 23));
        kelas3.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                kelas3MouseMoved(evt);
            }
        });
        kelas3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kelas3MouseExited(evt);
            }
        });
        kelas3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelas3KeyPressed(evt);
            }
        });
        FormInput.add(kelas3);
        kelas3.setBounds(630, 42, 110, 23);

        label30.setText("Hrg Rnp Kelas 3 : Rp.");
        label30.setName("label30"); // NOI18N
        label30.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label30);
        label30.setBounds(480, 42, 148, 23);

        label33.setText("Hrg Rnp Utama/BPJS : Rp.");
        label33.setName("label33"); // NOI18N
        label33.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label33);
        label33.setBounds(480, 72, 148, 23);

        utama.setName("utama"); // NOI18N
        utama.setPreferredSize(new java.awt.Dimension(207, 23));
        utama.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                utamaMouseMoved(evt);
            }
        });
        utama.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                utamaMouseExited(evt);
            }
        });
        utama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                utamaKeyPressed(evt);
            }
        });
        FormInput.add(utama);
        utama.setBounds(630, 72, 110, 23);

        kelasvip.setName("kelasvip"); // NOI18N
        kelasvip.setPreferredSize(new java.awt.Dimension(207, 23));
        kelasvip.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                kelasvipMouseMoved(evt);
            }
        });
        kelasvip.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kelasvipMouseExited(evt);
            }
        });
        kelasvip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelasvipKeyPressed(evt);
            }
        });
        FormInput.add(kelasvip);
        kelasvip.setBounds(630, 102, 110, 23);

        label34.setText("Hrg Rnp Kelas VIP : Rp.");
        label34.setName("label34"); // NOI18N
        label34.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label34);
        label34.setBounds(480, 102, 148, 23);

        label35.setText("Hrg Rnp Kelas VVIP : Rp.");
        label35.setName("label35"); // NOI18N
        label35.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label35);
        label35.setBounds(480, 132, 148, 23);

        kelasvvip.setName("kelasvvip"); // NOI18N
        kelasvvip.setPreferredSize(new java.awt.Dimension(207, 23));
        kelasvvip.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                kelasvvipMouseMoved(evt);
            }
        });
        kelasvvip.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                kelasvvipMouseExited(evt);
            }
        });
        kelasvvip.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kelasvvipKeyPressed(evt);
            }
        });
        FormInput.add(kelasvvip);
        kelasvvip.setBounds(630, 132, 110, 23);

        label36.setText("Hrg Apotek Luar : Rp.");
        label36.setName("label36"); // NOI18N
        label36.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label36);
        label36.setBounds(479, 162, 149, 23);

        beliluar.setName("beliluar"); // NOI18N
        beliluar.setPreferredSize(new java.awt.Dimension(207, 23));
        beliluar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                beliluarMouseMoved(evt);
            }
        });
        beliluar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                beliluarMouseExited(evt);
            }
        });
        beliluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                beliluarKeyPressed(evt);
            }
        });
        FormInput.add(beliluar);
        beliluar.setBounds(630, 162, 110, 23);

        jualbebas.setName("jualbebas"); // NOI18N
        jualbebas.setPreferredSize(new java.awt.Dimension(207, 23));
        jualbebas.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jualbebasMouseMoved(evt);
            }
        });
        jualbebas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jualbebasMouseExited(evt);
            }
        });
        jualbebas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jualbebasKeyPressed(evt);
            }
        });
        FormInput.add(jualbebas);
        jualbebas.setBounds(630, 192, 110, 23);

        label37.setText("Hrg Jual Obat Bebas : Rp.");
        label37.setName("label37"); // NOI18N
        label37.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label37);
        label37.setBounds(479, 192, 149, 23);

        label38.setText("Hrg Karyawan : Rp.");
        label38.setName("label38"); // NOI18N
        label38.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label38);
        label38.setBounds(479, 222, 149, 23);

        karyawan.setName("karyawan"); // NOI18N
        karyawan.setPreferredSize(new java.awt.Dimension(207, 23));
        karyawan.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                karyawanMouseMoved(evt);
            }
        });
        karyawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                karyawanMouseExited(evt);
            }
        });
        karyawan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                karyawanKeyPressed(evt);
            }
        });
        FormInput.add(karyawan);
        karyawan.setBounds(630, 222, 110, 23);

        DTPExpired.setForeground(new java.awt.Color(50, 70, 50));
        DTPExpired.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "03-12-2019" }));
        DTPExpired.setDisplayFormat("dd-MM-yyyy");
        DTPExpired.setName("DTPExpired"); // NOI18N
        DTPExpired.setOpaque(false);
        DTPExpired.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPExpiredKeyPressed(evt);
            }
        });
        FormInput.add(DTPExpired);
        DTPExpired.setBounds(640, 282, 100, 23);

        ChkKadaluarsa.setBackground(new java.awt.Color(235, 255, 235));
        ChkKadaluarsa.setSelected(true);
        ChkKadaluarsa.setText("Tanggal Kadaluwarsa :");
        ChkKadaluarsa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkKadaluarsa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkKadaluarsa.setName("ChkKadaluarsa"); // NOI18N
        ChkKadaluarsa.setOpaque(false);
        FormInput.add(ChkKadaluarsa);
        ChkKadaluarsa.setBounds(486, 282, 151, 23);

        label21.setText("I.F. :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label21);
        label21.setBounds(200, 12, 40, 23);

        KdIF.setEditable(false);
        KdIF.setName("KdIF"); // NOI18N
        KdIF.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(KdIF);
        KdIF.setBounds(243, 12, 50, 23);

        NmIF.setEditable(false);
        NmIF.setName("NmIF"); // NOI18N
        NmIF.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmIF);
        NmIF.setBounds(295, 12, 157, 23);

        BtnIF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnIF.setMnemonic('1');
        BtnIF.setToolTipText("Alt+1");
        BtnIF.setName("BtnIF"); // NOI18N
        BtnIF.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnIF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnIFActionPerformed(evt);
            }
        });
        BtnIF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnIFKeyPressed(evt);
            }
        });
        FormInput.add(BtnIF);
        BtnIF.setBounds(455, 12, 25, 23);

        label22.setText("Kategori :");
        label22.setName("label22"); // NOI18N
        label22.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label22);
        label22.setBounds(8, 192, 88, 23);

        kdkategori.setEditable(false);
        kdkategori.setName("kdkategori"); // NOI18N
        kdkategori.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(kdkategori);
        kdkategori.setBounds(100, 192, 90, 23);

        nmkategori.setEditable(false);
        nmkategori.setName("nmkategori"); // NOI18N
        nmkategori.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmkategori);
        nmkategori.setBounds(192, 192, 260, 23);

        BtnKategori.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnKategori.setMnemonic('2');
        BtnKategori.setToolTipText("Alt+2");
        BtnKategori.setName("BtnKategori"); // NOI18N
        BtnKategori.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKategori.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKategoriActionPerformed(evt);
            }
        });
        BtnKategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKategoriKeyPressed(evt);
            }
        });
        FormInput.add(BtnKategori);
        BtnKategori.setBounds(455, 192, 25, 23);

        nmgolongan.setEditable(false);
        nmgolongan.setName("nmgolongan"); // NOI18N
        nmgolongan.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmgolongan);
        nmgolongan.setBounds(192, 222, 260, 23);

        kdgolongan.setEditable(false);
        kdgolongan.setName("kdgolongan"); // NOI18N
        kdgolongan.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(kdgolongan);
        kdgolongan.setBounds(100, 222, 90, 23);

        BtnGolongan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGolongan.setMnemonic('2');
        BtnGolongan.setToolTipText("Alt+2");
        BtnGolongan.setName("BtnGolongan"); // NOI18N
        BtnGolongan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGolongan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGolonganActionPerformed(evt);
            }
        });
        BtnGolongan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnGolonganKeyPressed(evt);
            }
        });
        FormInput.add(BtnGolongan);
        BtnGolongan.setBounds(455, 222, 25, 23);

        label23.setText("Golongan :");
        label23.setName("label23"); // NOI18N
        label23.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label23);
        label23.setBounds(8, 222, 88, 23);

        label24.setText("Satuan Besar :");
        label24.setName("label24"); // NOI18N
        label24.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label24);
        label24.setBounds(8, 102, 88, 23);

        kdsatBesar.setEditable(false);
        kdsatBesar.setName("kdsatBesar"); // NOI18N
        kdsatBesar.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(kdsatBesar);
        kdsatBesar.setBounds(100, 102, 60, 23);

        nmsatBesar.setEditable(false);
        nmsatBesar.setName("nmsatBesar"); // NOI18N
        nmsatBesar.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmsatBesar);
        nmsatBesar.setBounds(162, 102, 140, 23);

        BtnSatuanBesar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnSatuanBesar.setMnemonic('1');
        BtnSatuanBesar.setToolTipText("Alt+1");
        BtnSatuanBesar.setName("BtnSatuanBesar"); // NOI18N
        BtnSatuanBesar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSatuanBesar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSatuanBesarActionPerformed(evt);
            }
        });
        BtnSatuanBesar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSatuanBesarKeyPressed(evt);
            }
        });
        FormInput.add(BtnSatuanBesar);
        BtnSatuanBesar.setBounds(305, 102, 25, 23);

        label39.setText("Isi :");
        label39.setName("label39"); // NOI18N
        label39.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label39);
        label39.setBounds(338, 102, 69, 23);

        Isi.setName("Isi"); // NOI18N
        Isi.setPreferredSize(new java.awt.Dimension(207, 23));
        Isi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                IsiKeyPressed(evt);
            }
        });
        FormInput.add(Isi);
        Isi.setBounds(410, 102, 70, 23);

        dasar.setName("dasar"); // NOI18N
        dasar.setPreferredSize(new java.awt.Dimension(207, 23));
        dasar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                dasarMouseMoved(evt);
            }
        });
        dasar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                dasarMouseExited(evt);
            }
        });
        dasar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dasarKeyPressed(evt);
            }
        });
        FormInput.add(dasar);
        dasar.setBounds(117, 252, 110, 23);

        label40.setText("Harga Dasar : Rp.");
        label40.setName("label40"); // NOI18N
        label40.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(label40);
        label40.setBounds(8, 252, 107, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnCariActionPerformed(null);
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
            BtnCari.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_PAGE_UP) {
            BtnKeluar.requestFocus();
        } else if (evt.getKeyCode() == KeyEvent.VK_UP) {
            tbDokter.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(akses.getform().equals("tampil3")){
            tampil3();
        }else{
            tampil();
        }   
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnCariActionPerformed(null);
        } else {
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDokterMouseClicked
        if (tabMode.getRowCount() != 0) {
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbDokterMouseClicked

    private void tbDokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyPressed
        if (tabMode.getRowCount() != 0) {
            if (evt.getKeyCode() == KeyEvent.VK_SHIFT) {
                TCari.setText("");
                TCari.requestFocus();
            }
        }
}//GEN-LAST:event_tbDokterKeyPressed

    private void NmKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NmKeyPressed
        Valid.pindah(evt,BtnIF, Letak);
}//GEN-LAST:event_NmKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        for (i = 0; i < tbDokter.getRowCount(); i++) {
            if (tbDokter.getValueAt(i, 0).toString().equals("true")) {
                Sequel.mengedit("databarang","kode_brng='"+tbDokter.getValueAt(i, 1).toString()+"'","status='0'");
            }
        }
        
        tampil();
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnHapusActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if (Kd.getText().trim().equals("")) {
            Valid.textKosong(Kd, "Kode Barang");
        } else if (Nm.getText().trim().equals("")) {
            Valid.textKosong(Nm, "Nama Barang");
        } else if (Letak.getText().trim().equals("")) {
            Valid.textKosong(Letak, "Kandungan");
        } else if (beli.getText().trim().equals("")) {
            Valid.textKosong(beli, "Harga Beli");
        } else if (ralan.getText().trim().equals("")) {
            Valid.textKosong(ralan, "Harga Ralan");
        } else if (kelas1.getText().trim().equals("")) {
            Valid.textKosong(kelas1, "Harga Ranap Kelas 1");
        } else if (kelas2.getText().trim().equals("")) {
            Valid.textKosong(kelas2, "Harga Ranap Kelas 2");
        } else if (kelas3.getText().trim().equals("")) {
            Valid.textKosong(kelas3, "Harga Ranap Kelas 3");
        } else if (utama.getText().trim().equals("")) {
            Valid.textKosong(utama, "Harga Ranap Kelas Utama");
        } else if (kelasvip.getText().trim().equals("")) {
            Valid.textKosong(kelasvip, "Harga Ranap Kelas VIP");
        } else if (kelasvvip.getText().trim().equals("")) {
            Valid.textKosong(kelasvvip, "Harga Ranap Kelas VVIP");
        } else if (beliluar.getText().trim().equals("")) {
            Valid.textKosong(beliluar, "Harga Jika Beli dari Apotek Lain");
        } else if (jualbebas.getText().trim().equals("")) {
            Valid.textKosong(jualbebas, "Harga Jual Obat Bebas");
        } else if (karyawan.getText().trim().equals("")) {
            Valid.textKosong(karyawan, "Harga Karyawan");
        } else if (kdsat.getText().trim().equals("") || nmsat.getText().trim().equals("")) {
            Valid.textKosong(kdsat, "Satuan");
        } else if (kdjns.getText().trim().equals("") || nmjns.getText().trim().equals("")) {
            Valid.textKosong(kdjns, "Jenis");
        } else if (kdkategori.getText().trim().equals("") || nmkategori.getText().trim().equals("")) {
            Valid.textKosong(kdkategori, "Kategori");
        } else if (kdgolongan.getText().trim().equals("") || nmgolongan.getText().trim().equals("")) {
            Valid.textKosong(kdgolongan, "Golongan");
        } else if (stok_minimal.getText().trim().equals("")) {
            Valid.textKosong(stok_minimal, "Stok Minimal");
        } else if (Kapasitas.getText().trim().equals("")) {
            Valid.textKosong(Kapasitas, "Kapasitas");
        }  else if (KdIF.getText().trim().equals("") || NmIF.getText().trim().equals("")) {
            Valid.textKosong(KdIF, "Industri Farmasi");
        } else {
            if (ChkKadaluarsa.isSelected() == true) {
                tanggal = Valid.SetTgl(DTPExpired.getSelectedItem()+"");
            } else if (ChkKadaluarsa.isSelected() == false) {
                tanggal = "0000-00-00";
            }
            Valid.editTable(tabMode, "databarang", "kode_brng", "?", "nama_brng=?,kode_brng=?,kapasitas=?,kode_sat=?,letak_barang=?,"
                    + "h_beli=?,ralan=?,kelas1=?,kelas2=?,stokminimal=?,kdjns=?,kelas3=?,utama=?,vip=?,vvip=?,beliluar=?,jualbebas=?,"
                    + "karyawan=?,expire=?,kode_industri=?,kode_kategori=?,kode_golongan=?,kode_satbesar=?,dasar=?,isi=?", 26, new String[]{
                        Nm.getText(), Kd.getText(), Kapasitas.getText(), kdsat.getText(), Letak.getText(), beli.getText(), ralan.getText(),
                        kelas1.getText(), kelas2.getText(), stok_minimal.getText(), kdjns.getText(),kelas3.getText(), utama.getText(), 
                        kelasvip.getText(), kelasvvip.getText(),beliluar.getText(), jualbebas.getText(), karyawan.getText(),tanggal,KdIF.getText(),
                        kdkategori.getText(),kdgolongan.getText(),kdsatBesar.getText(),dasar.getText(),Isi.getText(),
                        tbDokter.getValueAt(tbDokter.getSelectedRow(), 1).toString(),
                    });
            if (tabMode.getRowCount() != 0) {
                tampil();
            }
            emptTeks();
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnEditActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            if(TCari.getText().trim().equals("")){
                Valid.MyReportqry("rptBarang.jasper", "report", "::[ Data Barang ]::", 
                        "select databarang.kode_brng, databarang.nama_brng,databarang.kode_satbesar,satuanbesar.satuan as satuanbesar, "
                        + " databarang.isi,databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang,databarang.dasar,databarang.h_beli,"
                        + " databarang.ralan,databarang.kelas1,databarang.kelas2,databarang.kelas3,"
                        + " databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.jualbebas,"
                        + " databarang.karyawan,databarang.stokminimal, databarang.kdjns,"
                        + " jenis.nama,kapasitas,databarang.expire,databarang.kode_industri,industrifarmasi.nama_industri, "
                        + " databarang.kode_kategori,kategori_barang.nama as kategori,databarang.kode_golongan,golongan_barang.nama as golongan "
                        + " from databarang inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "
                        + " inner join kodesatuan as satuanbesar on databarang.kode_satbesar=satuanbesar.kode_sat "
                        + " inner join jenis on databarang.kdjns=jenis.kdjns "
                        + " inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "
                        + " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "
                        + " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                        + " where databarang.status='1' order by databarang.nama_brng", param);
            }else{
                Valid.MyReportqry("rptBarang.jasper", "report", "::[ Data Barang ]::", 
                        "select databarang.kode_brng, databarang.nama_brng,databarang.kode_satbesar,satuanbesar.satuan as satuanbesar, "
                        + " databarang.isi,databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang,databarang.dasar,databarang.h_beli,"
                        + " databarang.ralan,databarang.kelas1,databarang.kelas2,databarang.kelas3,"
                        + " databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.jualbebas,"
                        + " databarang.karyawan,databarang.stokminimal, databarang.kdjns,"
                        + " jenis.nama,kapasitas,databarang.expire,databarang.kode_industri,industrifarmasi.nama_industri, "
                        + " databarang.kode_kategori,kategori_barang.nama as kategori,databarang.kode_golongan,golongan_barang.nama as golongan "
                        + " from databarang inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "
                        + " inner join kodesatuan as satuanbesar on databarang.kode_satbesar=satuanbesar.kode_sat "
                        + " inner join jenis on databarang.kdjns=jenis.kdjns "
                        + " inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "
                        + " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "
                        + " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                        + " where databarang.status='1' and databarang.kode_brng like '%" + TCari.getText().trim() + "%' or "
                        + " databarang.status='1' and databarang.nama_brng like '%" + TCari.getText().trim() + "%' or "
                        + " databarang.status='1' and databarang.kode_sat like '%" + TCari.getText().trim() + "%' or "
                        + " databarang.status='1' and kodesatuan.satuan like '%" + TCari.getText().trim() + "%' or "
                        + " databarang.status='1' and databarang.kode_satbesar like '%" + TCari.getText().trim() + "%' or "
                        + " databarang.status='1' and satuanbesar.satuan like '%" + TCari.getText().trim() + "%' or "
                        + " databarang.status='1' and databarang.letak_barang like '%" + TCari.getText().trim() + "%' or "
                        + " databarang.status='1' and databarang.kdjns like '%" + TCari.getText().trim() + "%' or "
                        + " databarang.status='1' and jenis.nama like '%" + TCari.getText().trim() + "%' or "
                        + " databarang.status='1' and databarang.kode_industri like '%" + TCari.getText().trim() + "%' or "
                        + " databarang.status='1' and kategori_barang.nama like '%" + TCari.getText().trim() + "%' or "
                        + " databarang.status='1' and golongan_barang.nama like '%" + TCari.getText().trim() + "%' or "
                        + " databarang.status='1' and industrifarmasi.nama_industri like '%" + TCari.getText().trim() + "%' order by databarang.nama_brng", param);
            }
                
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnPrintActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnEdit, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        if(akses.getform().equals("tampil3")){
            tampil3();
        }else{
            tampil();
        }        
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            BtnAllActionPerformed(null);
        } else {
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            dispose();
        } else {
            Valid.pindah(evt, BtnAll, TCari);
        }
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if (Kd.getText().trim().equals("")) {
            Valid.textKosong(Kd, "Kode Barang");
        } else if (Nm.getText().trim().equals("")) {
            Valid.textKosong(Nm, "Nama Barang");
        } else if (Letak.getText().trim().equals("")) {
            Valid.textKosong(Letak, "Kandungan");
        } else if (beli.getText().trim().equals("")) {
            Valid.textKosong(beli, "Harga Beli");
        } else if (ralan.getText().trim().equals("")) {
            Valid.textKosong(ralan, "Harga Ralan");
        } else if (kelas1.getText().trim().equals("")) {
            Valid.textKosong(kelas1, "Harga Ranap Kelas 1");
        } else if (kelas2.getText().trim().equals("")) {
            Valid.textKosong(kelas2, "Harga Ranap Kelas 2");
        } else if (kelas3.getText().trim().equals("")) {
            Valid.textKosong(kelas3, "Harga Ranap Kelas 3");
        } else if (utama.getText().trim().equals("")) {
            Valid.textKosong(utama, "Harga Ranap Kelas Utama");
        } else if (kelasvip.getText().trim().equals("")) {
            Valid.textKosong(kelasvip, "Harga Ranap Kelas VIP");
        } else if (kelasvvip.getText().trim().equals("")) {
            Valid.textKosong(kelasvvip, "Harga Ranap Kelas VVIP");
        } else if (beliluar.getText().trim().equals("")) {
            Valid.textKosong(beliluar, "Harga Jika Beli dari Apotek Lain");
        } else if (jualbebas.getText().trim().equals("")) {
            Valid.textKosong(jualbebas, "Harga Jual Obat Bebas");
        } else if (karyawan.getText().trim().equals("")) {
            Valid.textKosong(karyawan, "Harga Karyawan");
        } else if (kdsat.getText().trim().equals("") || nmsat.getText().trim().equals("")) {
            Valid.textKosong(kdsat, "Satuan");
        } else if (kdjns.getText().trim().equals("") || nmjns.getText().trim().equals("")) {
            Valid.textKosong(kdjns, "Jenis");
        } else if (kdkategori.getText().trim().equals("") || nmkategori.getText().trim().equals("")) {
            Valid.textKosong(kdkategori, "Kategori");
        } else if (kdgolongan.getText().trim().equals("") || nmgolongan.getText().trim().equals("")) {
            Valid.textKosong(kdgolongan, "Golongan");
        } else if (stok_minimal.getText().trim().equals("")) {
            Valid.textKosong(stok_minimal, "Stok Minimal");
        } else if (Kapasitas.getText().trim().equals("")) {
            Valid.textKosong(Kapasitas, "Kapasitas");
        } else if (KdIF.getText().trim().equals("") || NmIF.getText().trim().equals("")) {
            Valid.textKosong(KdIF, "Industri Farmasi");
        } else {
            if (ChkKadaluarsa.isSelected() == true) {
                tanggal = Valid.SetTgl(DTPExpired.getSelectedItem() + "");
            } else {
                tanggal = "0000-00-00";
            }
            if(Sequel.menyimpantf("databarang", "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?", "Kode Barang", 26, new String[]{
                Kd.getText(), Nm.getText(), kdsatBesar.getText(), kdsat.getText(), Letak.getText(),dasar.getText(), beli.getText(), ralan.getText(),
                kelas1.getText(), kelas2.getText(), kelas3.getText(),utama.getText(), kelasvip.getText(), kelasvvip.getText(),beliluar.getText(), 
                jualbebas.getText(), karyawan.getText(),stok_minimal.getText(), kdjns.getText(), Isi.getText(),Kapasitas.getText(), tanggal,"1",
                KdIF.getText(),kdkategori.getText(),kdgolongan.getText()
                })==true){
                    tampil();
                    emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            BtnSimpanActionPerformed(null);
        } else {
            Valid.pindah(evt, KdIF, BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm();
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_SPACE) {
            emptTeks();
            ChkInput.setSelected(true);
            isForm();
        } else {
            Valid.pindah(evt, BtnSimpan, BtnHapus);
        }
}//GEN-LAST:event_BtnBatalKeyPressed

    private void kelas1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelas1KeyPressed
        Valid.pindah(evt, ralan, kelas2);
    }//GEN-LAST:event_kelas1KeyPressed

    private void kelas2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelas2KeyPressed
        Valid.pindah(evt, kelas1, kelas3);
    }//GEN-LAST:event_kelas2KeyPressed

private void LetakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LetakKeyPressed
    Valid.pindah(evt, Nm,BtnSatuanBesar);
}//GEN-LAST:event_LetakKeyPressed

private void ralanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ralanKeyPressed
    Valid.pindah(evt, beli, kelas1);
}//GEN-LAST:event_ralanKeyPressed

private void BtnSatuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSatuanActionPerformed
    i=1;
    akses.setform("DlgBarang");
    satuan.isCek();
    satuan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
    satuan.setLocationRelativeTo(internalFrame1);
    satuan.setAlwaysOnTop(false);
    satuan.setVisible(true);
}//GEN-LAST:event_BtnSatuanActionPerformed

private void stok_minimalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stok_minimalKeyPressed
    Valid.pindah(evt, karyawan, DTPExpired);
}//GEN-LAST:event_stok_minimalKeyPressed

    private void BtnJenisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisActionPerformed
        akses.setform("DlgBarang");
        jenis.isCek();
        jenis.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        jenis.setLocationRelativeTo(internalFrame1);
        jenis.setAlwaysOnTop(false);
        jenis.setVisible(true);
    }//GEN-LAST:event_BtnJenisActionPerformed

    private void KdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdKeyPressed
        Valid.pindah(evt, stok_minimal, BtnIF, TCari);
    }//GEN-LAST:event_KdKeyPressed

    private void stok_minimalMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stok_minimalMouseExited
        if (stok_minimal.getText().equals("")) {
            stok_minimal.setText("0");
        }
    }//GEN-LAST:event_stok_minimalMouseExited

    private void ralanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ralanMouseExited
        if (ralan.getText().equals("")) {
            ralan.setText("0");
        }
    }//GEN-LAST:event_ralanMouseExited

    private void kelas1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelas1MouseExited
        if (kelas1.getText().equals("")) {
            kelas1.setText("0");
        }
    }//GEN-LAST:event_kelas1MouseExited

    private void kelas2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelas2MouseExited
        if (kelas2.getText().equals("")) {
            kelas2.setText("0");
        }
    }//GEN-LAST:event_kelas2MouseExited

    private void stok_minimalMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_stok_minimalMouseMoved
        if (stok_minimal.getText().equals("0")) {
            stok_minimal.setText("");
        }
    }//GEN-LAST:event_stok_minimalMouseMoved

    private void ralanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ralanMouseMoved
        if (ralan.getText().equals("0") || ralan.getText().equals("0.0")) {
            ralan.setText("");
        }
    }//GEN-LAST:event_ralanMouseMoved

    private void kelas1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelas1MouseMoved
        if (kelas1.getText().equals("0") || kelas1.getText().equals("0.0")) {
            kelas1.setText("");
        }
    }//GEN-LAST:event_kelas1MouseMoved

    private void kelas2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelas2MouseMoved
        if (kelas2.getText().equals("0") || kelas2.getText().equals("0.0")) {
            kelas2.setText("");
        }
    }//GEN-LAST:event_kelas2MouseMoved

    private void beliMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliMouseExited
        if (beli.getText().equals("")) {
            beli.setText("0");
        }
    }//GEN-LAST:event_beliMouseExited

    private void beliMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliMouseMoved
        if (beli.getText().equals("0")) {
            beli.setText("");
        }
    }//GEN-LAST:event_beliMouseMoved

    private void beliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_beliKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isHitung();        
            ralan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            dasar.requestFocus();
        }
    }//GEN-LAST:event_beliKeyPressed

    private void ppBarcodeBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBarcodeBtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        BtnCariActionPerformed(evt);
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            Map<String, Object> param = new HashMap<>();
            param.put("namars", akses.getnamars());
            param.put("alamatrs", akses.getalamatrs());
            param.put("kotars", akses.getkabupatenrs());
            param.put("propinsirs", akses.getpropinsirs());
            param.put("kontakrs", akses.getkontakrs());
            param.put("emailrs", akses.getemailrs());
            param.put("logo", Sequel.cariGambar("select logo from setting"));
            Valid.MyReportqry("rptBarcodeBarang.jasper", "report", "::[ Data Barang ]::", 
                   "select databarang.kode_brng, databarang.nama_brng, "
                    + " databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang, databarang.h_beli,"
                    + " databarang.ralan,databarang.kelas1,databarang.kelas2,databarang.kelas3,"
                    + " databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.jualbebas,"
                    + " databarang.karyawan,databarang.stokminimal, databarang.kdjns,"
                    + " jenis.nama,kapasitas,databarang.expire,databarang.kode_industri,industrifarmasi.nama_industri, "
                    + " databarang.kode_kategori,kategori_barang.nama as kategori,databarang.kode_golongan,golongan_barang.nama as golongan "
                    + " from databarang inner join kodesatuan inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang "
                    + " on databarang.kode_sat=kodesatuan.kode_sat and databarang.kdjns=jenis.kdjns "
                    + " and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode "
                    + " and databarang.kode_industri=industrifarmasi.kode_industri "
                    + " where databarang.status='1' and databarang.kode_brng like '%" + TCari.getText().trim() + "%' or "
                    + " databarang.status='1' and databarang.nama_brng like '%" + TCari.getText().trim() + "%' or "
                    + " databarang.status='1' and databarang.kode_sat like '%" + TCari.getText().trim() + "%' or "
                    + " databarang.status='1' and kodesatuan.satuan like '%" + TCari.getText().trim() + "%' or "
                    + " databarang.status='1' and databarang.letak_barang like '%" + TCari.getText().trim() + "%' or "
                    + " databarang.status='1' and databarang.kdjns like '%" + TCari.getText().trim() + "%' or "
                    + " databarang.status='1' and jenis.nama like '%" + TCari.getText().trim() + "%' or "
                    + " databarang.status='1' and databarang.kode_industri like '%" + TCari.getText().trim() + "%' or "
                    + " databarang.status='1' and kategori_barang.nama like '%" + TCari.getText().trim() + "%' or "
                    + " databarang.status='1' and golongan_barang.nama like '%" + TCari.getText().trim() + "%' or "
                    + " databarang.status='1' and industrifarmasi.nama_industri like '%" + TCari.getText().trim() + "%' order by databarang.nama_brng",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }//GEN-LAST:event_ppBarcodeBtnPrintActionPerformed

    private void ppBarcodeItemBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBarcodeItemBtnPrintActionPerformed
        if (tabMode.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        } else if (Nm.getText().trim().equals("")) {
            JOptionPane.showMessageDialog(null, "Maaf, Pilih dulu data yang akan Anda hapus dengan menklik data pada tabel...!!!");
            tbDokter.requestFocus();
        } else if (tabMode.getRowCount() != 0) {
            int jml;
            try {
                jml = Integer.parseInt(JOptionPane.showInputDialog("Masukkan jumlah barcode yang mau dicetak !!!"));
            } catch (HeadlessException | NumberFormatException e) {
                jml = 0;
            }

            if (jml > 0) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                Sequel.queryu("truncate table temporary");
                jml = Math.round(jml / 3);
                for (int i = 0; i < jml; i++) {
                    Sequel.menyimpan("temporary", "'0','" + Kd.getText() + "','" + Kd.getText() + "','" + Kd.getText() + "','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','','',''", "Barcode");
                }
                Map<String, Object> param = new HashMap<>();
                param.put("namars", akses.getnamars());
                param.put("alamatrs", akses.getalamatrs());
                param.put("kotars", akses.getkabupatenrs());
                param.put("propinsirs", akses.getpropinsirs());
                param.put("kontakrs", akses.getkontakrs());
                param.put("emailrs", akses.getemailrs());
                param.put("logo", Sequel.cariGambar("select logo from setting"));
                Valid.MyReport("rptBarcodeItem.jasper", "report", "::[ Barcode Perbarang ]::",param);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }

    }//GEN-LAST:event_ppBarcodeItemBtnPrintActionPerformed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
    isForm();
}//GEN-LAST:event_ChkInputActionPerformed

private void ppStokBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokBtnPrintActionPerformed
    tampil2();
}//GEN-LAST:event_ppStokBtnPrintActionPerformed

private void KapasitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KapasitasKeyPressed
    Valid.pindah(evt, Isi,BtnJenis);
}//GEN-LAST:event_KapasitasKeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(aktifkanbatch.equals("yes")){
            tabMode = new DefaultTableModel(null,new Object[]{
                "P", "Kode Barang", "Nama Barang", "Kd.Sat Besar", "Nm.Satuan Besar","Isi", "Kd.Sat Kecil", "Nm.Satuan Kecil",
                "Kps", "Kandungan","Hrg.Dasar(Rp)","Hrg.Beli(Rp)", "Ralan(Rp)", "Ranap K1(Rp)", "Ranap K2(Rp)", "Ranap K3(Rp)",
                "Kelas Utama/BPJS(Rp)", "Ranap VIP(Rp)", "Ranap VVIP(Rp)", "Beli Luar(Rp)","Jual Bebas(Rp)", "Karyawan(Rp)", 
                "Stok Min", "Kode Jenis", "Nama Jenis","Kadaluwarsa","Kode I.F.","Industri Farmasi","Kode Kategori","Kategori",
                "Kode Golongan","Golongan","No.Batch","No.Faktur"
            }) {
                @Override
                public boolean isCellEditable(int rowIndex, int colIndex) {
                    boolean a = false;
                    if (colIndex == 0) {
                        a = true;
                    }
                    return a;
                }
                Class[] types = new Class[]{
                    java.lang.Boolean.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                    java.lang.Double.class,java.lang.Object.class,java.lang.Object.class,java.lang.Double.class,java.lang.Object.class,
                    java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                    java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,
                    java.lang.Double.class,java.lang.Double.class,java.lang.Double.class,java.lang.Object.class,java.lang.Object.class,
                    java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,
                    java.lang.Object.class,java.lang.Object.class,java.lang.Object.class,java.lang.Object.class
                };

                @Override
                public Class getColumnClass(int columnIndex) {
                    return types[columnIndex];
                }
            };
            tbDokter.setModel(tabMode);

            tbDokter.setPreferredScrollableViewportSize(new Dimension(800, 800));
            tbDokter.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

            for (i = 0; i < 34; i++) {
                TableColumn column = tbDokter.getColumnModel().getColumn(i);
                if (i == 0) {
                    column.setPreferredWidth(20);
                } else if (i == 1) {
                    column.setPreferredWidth(85);
                } else if (i == 2) {
                    column.setPreferredWidth(200);
                } else if (i == 3) {
                    column.setPreferredWidth(68);
                } else if (i == 4) {
                    column.setPreferredWidth(90);
                } else if (i == 5) {
                    column.setPreferredWidth(37);
                } else if (i == 6) {
                    column.setPreferredWidth(68);
                } else if (i == 7) {
                    column.setPreferredWidth(90);
                } else if (i == 8) {
                    column.setPreferredWidth(37);
                } else if (i == 9) {
                    column.setPreferredWidth(120);
                } else if (i == 10) {
                    column.setPreferredWidth(85);
                } else if (i == 11) {
                    column.setPreferredWidth(85);
                } else if (i == 12) {
                    column.setPreferredWidth(85);
                } else if (i == 13) {
                    column.setPreferredWidth(85);
                } else if (i == 14) {
                    column.setPreferredWidth(85);
                } else if (i == 15) {
                    column.setPreferredWidth(85);
                } else if (i == 16) {
                    column.setPreferredWidth(85);
                } else if (i == 17) {
                    column.setPreferredWidth(85);
                } else if (i == 18) {
                    column.setPreferredWidth(85);
                } else if (i == 19) {
                    column.setPreferredWidth(85);
                } else if (i == 20) {
                    column.setPreferredWidth(85);
                } else if (i == 21) {
                    column.setPreferredWidth(85);
                } else if (i == 22) {
                    column.setPreferredWidth(50);
                } else if (i == 23) {
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                } else if (i == 24) {
                    column.setPreferredWidth(120);
                } else if (i == 25) {
                    column.setPreferredWidth(70);
                } else if (i == 26) {
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                } else if (i == 27) {
                    column.setPreferredWidth(120);
                } else if (i == 28) {
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                } else if (i == 29) {
                    column.setPreferredWidth(120);
                } else if (i == 30) {
                    column.setMinWidth(0);
                    column.setMaxWidth(0);
                } else if (i == 31) {
                    column.setPreferredWidth(120);
                } else if (i == 32) {
                    column.setPreferredWidth(80);
                } else if (i == 33) {
                    column.setPreferredWidth(100);
                }
            }
            tbDokter.setDefaultRenderer(Object.class, new WarnaTable());
        }
        
        if(!akses.getform().equals("DlgReturJual")){
            if(akses.getform().equals("tampil3")){
                tampil3();
            }else{
                tampil();
            }   
        }
    }//GEN-LAST:event_formWindowOpened

    private void ppStok2BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStok2BtnPrintActionPerformed
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setVisible(true);
    }//GEN-LAST:event_ppStok2BtnPrintActionPerformed

    private void kelas3MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelas3MouseMoved
        if (kelas3.getText().equals("0") || kelas3.getText().equals("0.0")) {
            kelas3.setText("");
        }
    }//GEN-LAST:event_kelas3MouseMoved

    private void kelas3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelas3MouseExited
        if (kelas3.getText().equals("")) {
            kelas3.setText("0");
        }
    }//GEN-LAST:event_kelas3MouseExited

    private void kelas3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelas3KeyPressed
        Valid.pindah(evt, kelas2, utama);
    }//GEN-LAST:event_kelas3KeyPressed

    private void utamaMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_utamaMouseMoved
        if (utama.getText().equals("0") || utama.getText().equals("0.0")) {
            utama.setText("");
        }
    }//GEN-LAST:event_utamaMouseMoved

    private void utamaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_utamaMouseExited
        if (utama.getText().equals("")) {
            utama.setText("0");
        }
    }//GEN-LAST:event_utamaMouseExited

    private void utamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_utamaKeyPressed
        Valid.pindah(evt, kelas3, kelasvip);
    }//GEN-LAST:event_utamaKeyPressed

    private void kelasvipMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelasvipMouseMoved
        if (kelasvip.getText().equals("0") || kelasvip.getText().equals("0.0")) {
            kelasvip.setText("");
        }
    }//GEN-LAST:event_kelasvipMouseMoved

    private void kelasvipMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelasvipMouseExited
        if (kelasvip.getText().equals("")) {
            kelasvip.setText("0");
        }
    }//GEN-LAST:event_kelasvipMouseExited

    private void kelasvipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelasvipKeyPressed
        Valid.pindah(evt, utama, kelasvvip);
    }//GEN-LAST:event_kelasvipKeyPressed

    private void kelasvvipMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelasvvipMouseMoved
        if (kelasvvip.getText().equals("0") || kelasvvip.getText().equals("0.0")) {
            kelasvvip.setText("");
        }
    }//GEN-LAST:event_kelasvvipMouseMoved

    private void kelasvvipMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kelasvvipMouseExited
        if (kelasvvip.getText().equals("")) {
            kelasvvip.setText("0");
        }
    }//GEN-LAST:event_kelasvvipMouseExited

    private void kelasvvipKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kelasvvipKeyPressed
        Valid.pindah(evt, kelasvip, beliluar);
    }//GEN-LAST:event_kelasvvipKeyPressed

    private void beliluarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliluarMouseMoved
        if (beliluar.getText().equals("0") || beliluar.getText().equals("0.0")) {
            beliluar.setText("");
        }
    }//GEN-LAST:event_beliluarMouseMoved

    private void beliluarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_beliluarMouseExited
        if (beliluar.getText().equals("")) {
            beliluar.setText("0");
        }
    }//GEN-LAST:event_beliluarMouseExited

    private void beliluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_beliluarKeyPressed
        Valid.pindah(evt, kelasvvip, jualbebas);
    }//GEN-LAST:event_beliluarKeyPressed

    private void jualbebasMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jualbebasMouseMoved
        if (jualbebas.getText().equals("0") || jualbebas.getText().equals("0.0")) {
            jualbebas.setText("");
        }
    }//GEN-LAST:event_jualbebasMouseMoved

    private void jualbebasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jualbebasMouseExited
        if (jualbebas.getText().equals("")) {
            jualbebas.setText("0");
        }
    }//GEN-LAST:event_jualbebasMouseExited

    private void jualbebasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jualbebasKeyPressed
        Valid.pindah(evt, beliluar, karyawan);
    }//GEN-LAST:event_jualbebasKeyPressed

    private void karyawanMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawanMouseMoved
        if (karyawan.getText().equals("0") || karyawan.getText().equals("0.0")) {
            karyawan.setText("");
        }
    }//GEN-LAST:event_karyawanMouseMoved

    private void karyawanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_karyawanMouseExited
        if (karyawan.getText().equals("")) {
            karyawan.setText("0");
        }
    }//GEN-LAST:event_karyawanMouseExited

    private void karyawanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_karyawanKeyPressed
        Valid.pindah(evt, jualbebas, stok_minimal);
    }//GEN-LAST:event_karyawanKeyPressed

    private void DTPExpiredKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPExpiredKeyPressed
        Valid.pindah(evt, stok_minimal, KdIF);
    }//GEN-LAST:event_DTPExpiredKeyPressed

    private void MnRestoreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRestoreActionPerformed
        DlgRestoreObat restore=new DlgRestoreObat(null,true);
        restore.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        restore.setLocationRelativeTo(internalFrame1);
        restore.setVisible(true);
    }//GEN-LAST:event_MnRestoreActionPerformed

    private void BtnIFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnIFActionPerformed
        akses.setform("DlgBarang");
        industri.isCek();
        industri.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        industri.setLocationRelativeTo(internalFrame1);
        industri.setAlwaysOnTop(false);
        industri.setVisible(true);
    }//GEN-LAST:event_BtnIFActionPerformed

    private void BtnKategoriActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKategoriActionPerformed
        akses.setform("DlgBarang");
        kategori.isCek();
        kategori.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        kategori.setLocationRelativeTo(internalFrame1);
        kategori.setAlwaysOnTop(false);
        kategori.setVisible(true);
    }//GEN-LAST:event_BtnKategoriActionPerformed

    private void BtnGolonganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGolonganActionPerformed
        akses.setform("DlgBarang");
        golongan.isCek();
        golongan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        golongan.setLocationRelativeTo(internalFrame1);
        golongan.setAlwaysOnTop(false);
        golongan.setVisible(true);
    }//GEN-LAST:event_BtnGolonganActionPerformed

    private void tbDokterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDokterKeyReleased
        if (tabMode.getRowCount() != 0) {
            if ((evt.getKeyCode() == KeyEvent.VK_ENTER) || (evt.getKeyCode() == KeyEvent.VK_UP) || (evt.getKeyCode() == KeyEvent.VK_DOWN)) {
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
    }//GEN-LAST:event_tbDokterKeyReleased

    private void BtnSatuanBesarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSatuanBesarActionPerformed
        i=2;
        akses.setform("DlgBarang");
        satuan.isCek();
        satuan.setSize(internalFrame1.getWidth() - 20, internalFrame1.getHeight() - 20);
        satuan.setLocationRelativeTo(internalFrame1);
        satuan.setAlwaysOnTop(false);
        satuan.setVisible(true);
    }//GEN-LAST:event_BtnSatuanBesarActionPerformed

    private void IsiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IsiKeyPressed
        Valid.pindah(evt,BtnSatuanBesar,BtnSatuan);
    }//GEN-LAST:event_IsiKeyPressed

    private void dasarMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dasarMouseMoved
        if (dasar.getText().equals("0")) {
            dasar.setText("");
        }
    }//GEN-LAST:event_dasarMouseMoved

    private void dasarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dasarMouseExited
         if (dasar.getText().equals("")) {
            dasar.setText("0");
        }
    }//GEN-LAST:event_dasarMouseExited

    private void dasarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dasarKeyPressed
        Valid.pindah(evt,BtnGolongan,beli);
    }//GEN-LAST:event_dasarKeyPressed

    private void BtnIFKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnIFKeyPressed
         Valid.pindah(evt, Kd,Nm);
    }//GEN-LAST:event_BtnIFKeyPressed

    private void BtnSatuanBesarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSatuanBesarKeyPressed
        Valid.pindah(evt, Letak,Isi);
    }//GEN-LAST:event_BtnSatuanBesarKeyPressed

    private void BtnSatuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSatuanKeyPressed
        Valid.pindah(evt,Isi,Kapasitas);
    }//GEN-LAST:event_BtnSatuanKeyPressed

    private void BtnJenisKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnJenisKeyPressed
        Valid.pindah(evt,Kapasitas,BtnKategori);
    }//GEN-LAST:event_BtnJenisKeyPressed

    private void BtnKategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKategoriKeyPressed
        Valid.pindah(evt, BtnJenis,BtnGolongan);
    }//GEN-LAST:event_BtnKategoriKeyPressed

    private void BtnGolonganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnGolonganKeyPressed
        Valid.pindah(evt,BtnKategori,dasar);
    }//GEN-LAST:event_BtnGolonganKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgBarang dialog = new DlgBarang(new javax.swing.JFrame(), true);
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
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnGolongan;
    private widget.Button BtnHapus;
    private widget.Button BtnIF;
    private widget.Button BtnJenis;
    private widget.Button BtnKategori;
    private widget.Button BtnKeluar;
    private widget.Button BtnPrint;
    private widget.Button BtnSatuan;
    private widget.Button BtnSatuanBesar;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkKadaluarsa;
    private widget.Tanggal DTPExpired;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Isi;
    private widget.TextBox Kapasitas;
    private widget.TextBox Kd;
    private widget.TextBox KdIF;
    private widget.Label LCount;
    private widget.TextBox Letak;
    private javax.swing.JMenuItem MnRestore;
    private widget.TextBox Nm;
    private widget.TextBox NmIF;
    private javax.swing.JPanel PanelInput;
    private javax.swing.JPopupMenu Popup;
    private widget.TextBox TCari;
    private widget.TextBox beli;
    private widget.TextBox beliluar;
    private widget.TextBox dasar;
    private widget.InternalFrame internalFrame1;
    private javax.swing.JPanel jPanel2;
    private widget.TextBox jualbebas;
    private widget.TextBox karyawan;
    private widget.TextBox kdgolongan;
    private widget.TextBox kdjns;
    private widget.TextBox kdkategori;
    private widget.TextBox kdsat;
    private widget.TextBox kdsatBesar;
    private widget.TextBox kelas1;
    private widget.TextBox kelas2;
    private widget.TextBox kelas3;
    private widget.TextBox kelasvip;
    private widget.TextBox kelasvvip;
    private widget.Label label10;
    private widget.Label label12;
    private widget.Label label14;
    private widget.Label label18;
    private widget.Label label19;
    private widget.Label label20;
    private widget.Label label21;
    private widget.Label label22;
    private widget.Label label23;
    private widget.Label label24;
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
    private widget.Label label40;
    private widget.Label label9;
    private widget.TextBox nmgolongan;
    private widget.TextBox nmjns;
    private widget.TextBox nmkategori;
    private widget.TextBox nmsat;
    private widget.TextBox nmsatBesar;
    private widget.panelisi panelisi1;
    private widget.panelisi panelisi2;
    private javax.swing.JMenuItem ppBarcode;
    private javax.swing.JMenuItem ppBarcodeItem;
    private javax.swing.JMenuItem ppStok;
    private javax.swing.JMenuItem ppStok2;
    private widget.TextBox ralan;
    private widget.ScrollPane scrollPane1;
    private widget.TextBox stok_minimal;
    private widget.Table tbDokter;
    private widget.TextBox utama;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        if(aktifkanbatch.equals("no")){
            try {
                if(TCari.getText().trim().equals("")){
                    ps = koneksi.prepareStatement(
                            "select databarang.kode_brng, databarang.nama_brng,databarang.kode_satbesar,satuanbesar.satuan as satuanbesar, "
                            + " databarang.isi,databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang,databarang.dasar,databarang.h_beli,"
                            + " databarang.ralan,databarang.kelas1,databarang.kelas2,databarang.kelas3,"
                            + " databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.jualbebas,"
                            + " databarang.karyawan,databarang.stokminimal, databarang.kdjns,"
                            + " jenis.nama,kapasitas,databarang.expire,databarang.kode_industri,industrifarmasi.nama_industri, "
                            + " databarang.kode_kategori,kategori_barang.nama as kategori,databarang.kode_golongan,golongan_barang.nama as golongan "
                            + " from databarang inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "
                            + " inner join kodesatuan as satuanbesar on databarang.kode_satbesar=satuanbesar.kode_sat "
                            + " inner join jenis on databarang.kdjns=jenis.kdjns "
                            + " inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "
                            + " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "
                            + " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                            + " where databarang.status='1' order by databarang.nama_brng");
                }else{
                    ps = koneksi.prepareStatement(
                            "select databarang.kode_brng, databarang.nama_brng,databarang.kode_satbesar,satuanbesar.satuan as satuanbesar, "
                            + " databarang.isi,databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang,databarang.dasar,databarang.h_beli,"
                            + " databarang.ralan,databarang.kelas1,databarang.kelas2,databarang.kelas3,"
                            + " databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.jualbebas,"
                            + " databarang.karyawan,databarang.stokminimal, databarang.kdjns,"
                            + " jenis.nama,kapasitas,databarang.expire,databarang.kode_industri,industrifarmasi.nama_industri, "
                            + " databarang.kode_kategori,kategori_barang.nama as kategori,databarang.kode_golongan,golongan_barang.nama as golongan "
                            + " from databarang inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "
                            + " inner join kodesatuan as satuanbesar on databarang.kode_satbesar=satuanbesar.kode_sat "
                            + " inner join jenis on databarang.kdjns=jenis.kdjns "
                            + " inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "
                            + " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "
                            + " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                            + " where databarang.status='1' and databarang.kode_brng like ? or "
                            + " databarang.status='1' and databarang.nama_brng like ? or "
                            + " databarang.status='1' and databarang.kode_sat like ? or "
                            + " databarang.status='1' and kodesatuan.satuan like ? or "
                            + " databarang.status='1' and databarang.kode_satbesar like ? or "
                            + " databarang.status='1' and satuanbesar.satuan like ? or "
                            + " databarang.status='1' and databarang.letak_barang like ? or "
                            + " databarang.status='1' and databarang.kdjns like ? or "
                            + " databarang.status='1' and kategori_barang.nama like ? or "
                            + " databarang.status='1' and golongan_barang.nama like ? or "
                            + " databarang.status='1' and jenis.nama like ? or "
                            + " databarang.status='1' and databarang.kode_industri like ? or "
                            + " databarang.status='1' and industrifarmasi.nama_industri like ? order by databarang.nama_brng");
                }
                    
                try {
                    if(TCari.getText().trim().equals("")){}else{
                        ps.setString(1, "%" + TCari.getText().trim() + "%");
                        ps.setString(2, "%" + TCari.getText().trim() + "%");
                        ps.setString(3, "%" + TCari.getText().trim() + "%");
                        ps.setString(4, "%" + TCari.getText().trim() + "%");
                        ps.setString(5, "%" + TCari.getText().trim() + "%");
                        ps.setString(6, "%" + TCari.getText().trim() + "%");
                        ps.setString(7, "%" + TCari.getText().trim() + "%");
                        ps.setString(8, "%" + TCari.getText().trim() + "%");
                        ps.setString(9, "%" + TCari.getText().trim() + "%");
                        ps.setString(10, "%" + TCari.getText().trim() + "%");
                        ps.setString(11, "%" + TCari.getText().trim() + "%");
                        ps.setString(12, "%" + TCari.getText().trim() + "%");
                        ps.setString(13, "%" + TCari.getText().trim() + "%");
                    }
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        tabMode.addRow(new Object[]{
                            false, rs.getString("kode_brng"),
                            rs.getString("nama_brng"),
                            rs.getString("kode_satbesar"),
                            rs.getString("satuanbesar"),
                            rs.getDouble("isi"),
                            rs.getString("kode_sat"),
                            rs.getString("satuan"),
                            rs.getDouble("kapasitas"),
                            rs.getString("letak_barang"),
                            rs.getDouble("dasar"),
                            rs.getDouble("h_beli"),
                            rs.getDouble("ralan"),
                            rs.getDouble("kelas1"),
                            rs.getDouble("kelas2"),
                            rs.getDouble("kelas3"),
                            rs.getDouble("utama"),
                            rs.getDouble("vip"),
                            rs.getDouble("vvip"),
                            rs.getDouble("beliluar"),
                            rs.getDouble("jualbebas"),
                            rs.getDouble("karyawan"),
                            rs.getDouble("stokminimal"),
                            rs.getString("kdjns"),
                            rs.getString("nama"),
                            rs.getString("expire"),
                            rs.getString("kode_industri"),
                            rs.getString("nama_industri"),
                            rs.getString("kode_kategori"),
                            rs.getString("kategori"),
                            rs.getString("kode_golongan"),
                            rs.getString("golongan")
                        });
                    }
                    LCount.setText("" + tabMode.getRowCount());
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally{
                    if(rs != null){
                        rs.close();
                    }

                    if(ps != null){
                        ps.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }else if(aktifkanbatch.equals("yes")){
            try {
                ps = koneksi.prepareStatement(
                      "select data_batch.kode_brng, databarang.nama_brng,databarang.kode_satbesar,satuanbesar.satuan as satuanbesar, "
                    + " databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang,data_batch.dasar,data_batch.h_beli,"
                    + " data_batch.ralan,data_batch.kelas1,data_batch.kelas2,data_batch.kelas3,"
                    + " data_batch.utama,data_batch.vip,data_batch.vvip,data_batch.beliluar,data_batch.jualbebas,"
                    + " data_batch.karyawan,databarang.stokminimal, databarang.kdjns,"
                    + " jenis.nama,databarang.kapasitas,databarang.isi,data_batch.tgl_kadaluarsa as expire,databarang.kode_industri,industrifarmasi.nama_industri, "
                    + " databarang.kode_kategori,kategori_barang.nama as kategori,databarang.kode_golongan,golongan_barang.nama as golongan,data_batch.no_batch,data_batch.no_faktur "
                    + " from data_batch inner join databarang on data_batch.kode_brng=databarang.kode_brng "
                    + " inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "
                    + " inner join kodesatuan as satuanbesar on databarang.kode_satbesar=satuanbesar.kode_sat "
                    + " inner join jenis on databarang.kdjns=jenis.kdjns "
                    + " inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "
                    + " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "
                    + " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                    + " where data_batch.sisa>0 and data_batch.kode_brng like ? or "
                    + " data_batch.sisa>0 and databarang.nama_brng like ? or "
                    + " data_batch.sisa>0 and databarang.kode_sat like ? or "
                    + " data_batch.sisa>0 and kodesatuan.satuan like ? or "
                    + " data_batch.sisa>0 and databarang.kode_satbesar like ? or "
                    + " data_batch.sisa>0 and satuanbesar.satuan like ? or "
                    + " data_batch.sisa>0 and databarang.letak_barang like ? or "
                    + " data_batch.sisa>0 and databarang.kdjns like ? or "
                    + " data_batch.sisa>0 and kategori_barang.nama like ? or "
                    + " data_batch.sisa>0 and golongan_barang.nama like ? or "
                    + " data_batch.sisa>0 and data_batch.no_batch like ? or "
                    + " data_batch.sisa>0 and data_batch.no_faktur like ? or "
                    + " data_batch.sisa>0 and jenis.nama like ? or "
                    + " data_batch.sisa>0 and databarang.kode_industri like ? or "
                    + " data_batch.sisa>0 and industrifarmasi.nama_industri like ? order by data_batch.tgl_kadaluarsa");
                try {
                    ps.setString(1, "%" + TCari.getText().trim() + "%");
                    ps.setString(2, "%" + TCari.getText().trim() + "%");
                    ps.setString(3, "%" + TCari.getText().trim() + "%");
                    ps.setString(4, "%" + TCari.getText().trim() + "%");
                    ps.setString(5, "%" + TCari.getText().trim() + "%");
                    ps.setString(6, "%" + TCari.getText().trim() + "%");
                    ps.setString(7, "%" + TCari.getText().trim() + "%");
                    ps.setString(8, "%" + TCari.getText().trim() + "%");
                    ps.setString(9, "%" + TCari.getText().trim() + "%");
                    ps.setString(10, "%" + TCari.getText().trim() + "%");
                    ps.setString(11, "%" + TCari.getText().trim() + "%");
                    ps.setString(12, "%" + TCari.getText().trim() + "%");
                    ps.setString(13, "%" + TCari.getText().trim() + "%");
                    ps.setString(14, "%" + TCari.getText().trim() + "%");
                    ps.setString(15, "%" + TCari.getText().trim() + "%");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        tabMode.addRow(new Object[]{
                            false, rs.getString("kode_brng"),
                            rs.getString("nama_brng"),
                            rs.getString("kode_satbesar"),
                            rs.getString("satuanbesar"),
                            rs.getDouble("isi"),
                            rs.getString("kode_sat"),
                            rs.getString("satuan"),
                            rs.getDouble("kapasitas"),
                            rs.getString("letak_barang"),
                            rs.getDouble("dasar"),
                            rs.getDouble("h_beli"),
                            rs.getDouble("ralan"),
                            rs.getDouble("kelas1"),
                            rs.getDouble("kelas2"),
                            rs.getDouble("kelas3"),
                            rs.getDouble("utama"),
                            rs.getDouble("vip"),
                            rs.getDouble("vvip"),
                            rs.getDouble("beliluar"),
                            rs.getDouble("jualbebas"),
                            rs.getDouble("karyawan"),
                            rs.getDouble("stokminimal"),
                            rs.getString("kdjns"),
                            rs.getString("nama"),
                            rs.getString("expire"),
                            rs.getString("kode_industri"),
                            rs.getString("nama_industri"),
                            rs.getString("kode_kategori"),
                            rs.getString("kategori"),
                            rs.getString("kode_golongan"),
                            rs.getString("golongan"),
                            rs.getString("no_batch"),
                            rs.getString("no_faktur")
                        });
                    }
                    LCount.setText("" + tabMode.getRowCount());
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                } finally{
                    if(rs != null){
                        rs.close();
                    }

                    if(ps != null){
                        ps.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }
            
    }

    private void tampil2() {
        Valid.tabelKosong(tabMode);
        try {
            if(TCari.getText().trim().equals("")){
                ps = koneksi.prepareStatement(
                        "select databarang.kode_brng, databarang.nama_brng,databarang.kode_satbesar,satuanbesar.satuan as satuanbesar, "
                        + " databarang.isi,databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang,databarang.dasar,databarang.h_beli,"
                        + " databarang.ralan,databarang.kelas1,databarang.kelas2,databarang.kelas3,"
                        + " databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.jualbebas,"
                        + " databarang.karyawan,databarang.stokminimal, databarang.kdjns,"
                        + " jenis.nama,kapasitas,databarang.expire,databarang.kode_industri,industrifarmasi.nama_industri, "
                        + " databarang.kode_kategori,kategori_barang.nama as kategori,databarang.kode_golongan,golongan_barang.nama as golongan "
                        + " from databarang inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "
                        + " inner join kodesatuan as satuanbesar on databarang.kode_satbesar=satuanbesar.kode_sat "
                        + " inner join jenis on databarang.kdjns=jenis.kdjns "
                        + " inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "
                        + " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "
                        + " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                        + " where databarang.status='1' order by databarang.nama_brng");
            }else{
                ps = koneksi.prepareStatement(
                        "select databarang.kode_brng, databarang.nama_brng,databarang.kode_satbesar,satuanbesar.satuan as satuanbesar, "
                        + " databarang.isi,databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang,databarang.dasar,databarang.h_beli,"
                        + " databarang.ralan,databarang.kelas1,databarang.kelas2,databarang.kelas3,"
                        + " databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.jualbebas,"
                        + " databarang.karyawan,databarang.stokminimal, databarang.kdjns,"
                        + " jenis.nama,kapasitas,databarang.expire,databarang.kode_industri,industrifarmasi.nama_industri, "
                        + " databarang.kode_kategori,kategori_barang.nama as kategori,databarang.kode_golongan,golongan_barang.nama as golongan "
                        + " from databarang inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "
                        + " inner join kodesatuan as satuanbesar on databarang.kode_satbesar=satuanbesar.kode_sat "
                        + " inner join jenis on databarang.kdjns=jenis.kdjns "
                        + " inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "
                        + " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "
                        + " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                        + " where databarang.status='1' and databarang.kode_brng like ? or "
                        + " databarang.status='1' and databarang.nama_brng like ? or "
                        + " databarang.status='1' and databarang.kode_sat like ? or "
                        + " databarang.status='1' and kodesatuan.satuan like ? or "
                        + " databarang.status='1' and databarang.kode_satbesar like ? or "
                        + " databarang.status='1' and satuanbesar.satuan like ? or "
                        + " databarang.status='1' and databarang.letak_barang like ? or "
                        + " databarang.status='1' and databarang.kdjns like ? or "
                        + " databarang.status='1' and kategori_barang.nama like ? or "
                        + " databarang.status='1' and golongan_barang.nama like ? or "
                        + " databarang.status='1' and jenis.nama like ? or "
                        + " databarang.status='1' and databarang.kode_industri like ? or "
                        + " databarang.status='1' and industrifarmasi.nama_industri like ? order by databarang.nama_brng");
            }

            try {
                if(TCari.getText().trim().equals("")){}else{
                    ps.setString(1, "%" + TCari.getText().trim() + "%");
                    ps.setString(2, "%" + TCari.getText().trim() + "%");
                    ps.setString(3, "%" + TCari.getText().trim() + "%");
                    ps.setString(4, "%" + TCari.getText().trim() + "%");
                    ps.setString(5, "%" + TCari.getText().trim() + "%");
                    ps.setString(6, "%" + TCari.getText().trim() + "%");
                    ps.setString(7, "%" + TCari.getText().trim() + "%");
                    ps.setString(8, "%" + TCari.getText().trim() + "%");
                    ps.setString(9, "%" + TCari.getText().trim() + "%");
                    ps.setString(10, "%" + TCari.getText().trim() + "%");
                    ps.setString(11, "%" + TCari.getText().trim() + "%");
                    ps.setString(12, "%" + TCari.getText().trim() + "%");
                    ps.setString(13, "%" + TCari.getText().trim() + "%");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        false, rs.getString("kode_brng"),
                        rs.getString("nama_brng"),
                        rs.getString("kode_satbesar"),
                        rs.getString("satuanbesar"),
                        rs.getDouble("isi"),
                        rs.getString("kode_sat"),
                        rs.getString("satuan"),
                        rs.getDouble("kapasitas"),
                        rs.getString("letak_barang"),
                        rs.getDouble("dasar"),
                        rs.getDouble("h_beli"),
                        rs.getDouble("ralan"),
                        rs.getDouble("kelas1"),
                        rs.getDouble("kelas2"),
                        rs.getDouble("kelas3"),
                        rs.getDouble("utama"),
                        rs.getDouble("vip"),
                        rs.getDouble("vvip"),
                        rs.getDouble("beliluar"),
                        rs.getDouble("jualbebas"),
                        rs.getDouble("karyawan"),
                        rs.getDouble("stokminimal"),
                        rs.getString("kdjns"),
                        rs.getString("nama"),
                        rs.getString("expire"),
                        rs.getString("kode_industri"),
                        rs.getString("nama_industri"),
                        rs.getString("kode_kategori"),
                        rs.getString("kategori"),
                        rs.getString("kode_golongan"),
                        rs.getString("golongan")
                    });
                    
                    ps2 = koneksi.prepareStatement("select kd_bangsal,nm_bangsal from bangsal");
                    try {
                        rs2 = ps2.executeQuery();
                        totalstok = 0;
                        while (rs2.next()) {
                            stokgudang = 0;
                            ps3 = koneksi.prepareStatement("select sum(stok) from gudangbarang where kode_brng=? and kd_bangsal=?");
                            try {
                                ps3.setString(1, rs.getString(1));
                                ps3.setString(2, rs2.getString(1));
                                rs3 = ps3.executeQuery();
                                if (rs3.next()) {
                                    stokgudang = rs3.getDouble(1);
                                    totalstok = totalstok + rs3.getDouble(1);
                                }
                                tabMode.addRow(new Object[]{false, "","    "+rs2.getString(2),": " + stokgudang, "", null,null,null,null,null, null, null, null, null, null, null, null, null, null, null, null,null,null, null,null,null,null,null,null,null});
                            } catch (Exception e) {
                                System.out.println("Notifikasi : " + e);
                            } finally{
                                if(rs3 != null){
                                    rs3.close();
                                }
                                
                                if(ps3 != null){
                                    ps3.close();
                                }
                            }
                        }
                        tabMode.addRow(new Object[]{false, "", "  Total Stok", ": " + totalstok, "", null,null,null,null,null, null, null, null, null, null, null, null, null, null, null, null, null,null, null,null,null,null,null,null,null});
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally{
                        if(rs2 != null){
                            rs2.close();
                        }
                        if(ps2 != null){
                            ps2.close();
                        }
                    }  
                }
                rs.last();
                LCount.setText("" + rs.getRow());
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally{
                if(rs != null){
                    rs.close();
                }                
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
    }

    public void tampil3() {
        Valid.tabelKosong(tabMode);
        try {
            if(TCari.getText().trim().equals("")){
                ps = koneksi.prepareStatement(
                        "select databarang.kode_brng, databarang.nama_brng,databarang.kode_satbesar,satuanbesar.satuan as satuanbesar, "
                        + " databarang.isi,databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang,databarang.dasar,databarang.h_beli,"
                        + " databarang.ralan,databarang.kelas1,databarang.kelas2,databarang.kelas3,"
                        + " databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.jualbebas,"
                        + " databarang.karyawan,databarang.stokminimal, databarang.kdjns,"
                        + " jenis.nama,kapasitas,databarang.expire,databarang.kode_industri,industrifarmasi.nama_industri, "
                        + " databarang.kode_kategori,kategori_barang.nama as kategori,databarang.kode_golongan,golongan_barang.nama as golongan "
                        + " from databarang inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "
                        + " inner join kodesatuan as satuanbesar on databarang.kode_satbesar=satuanbesar.kode_sat "
                        + " inner join jenis on databarang.kdjns=jenis.kdjns "
                        + " inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "
                        + " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "
                        + " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                        + " where databarang.status='1' order by databarang.nama_brng");
            }else{
                ps = koneksi.prepareStatement(
                        "select databarang.kode_brng, databarang.nama_brng,databarang.kode_satbesar,satuanbesar.satuan as satuanbesar, "
                        + " databarang.isi,databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang,databarang.dasar,databarang.h_beli,"
                        + " databarang.ralan,databarang.kelas1,databarang.kelas2,databarang.kelas3,"
                        + " databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.jualbebas,"
                        + " databarang.karyawan,databarang.stokminimal, databarang.kdjns,"
                        + " jenis.nama,kapasitas,databarang.expire,databarang.kode_industri,industrifarmasi.nama_industri, "
                        + " databarang.kode_kategori,kategori_barang.nama as kategori,databarang.kode_golongan,golongan_barang.nama as golongan "
                        + " from databarang inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "
                        + " inner join kodesatuan as satuanbesar on databarang.kode_satbesar=satuanbesar.kode_sat "
                        + " inner join jenis on databarang.kdjns=jenis.kdjns "
                        + " inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "
                        + " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "
                        + " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                        + " where databarang.status='1' and databarang.kode_brng like ? or "
                        + " databarang.status='1' and databarang.nama_brng like ? or "
                        + " databarang.status='1' and databarang.kode_sat like ? or "
                        + " databarang.status='1' and kodesatuan.satuan like ? or "
                        + " databarang.status='1' and databarang.kode_satbesar like ? or "
                        + " databarang.status='1' and satuanbesar.satuan like ? or "
                        + " databarang.status='1' and databarang.letak_barang like ? or "
                        + " databarang.status='1' and databarang.kdjns like ? or "
                        + " databarang.status='1' and kategori_barang.nama like ? or "
                        + " databarang.status='1' and golongan_barang.nama like ? or "
                        + " databarang.status='1' and jenis.nama like ? or "
                        + " databarang.status='1' and databarang.kode_industri like ? or "
                        + " databarang.status='1' and industrifarmasi.nama_industri like ? order by databarang.nama_brng");
            }

            try {
                if(TCari.getText().trim().equals("")){}else{
                    ps.setString(1, "%" + TCari.getText().trim() + "%");
                    ps.setString(2, "%" + TCari.getText().trim() + "%");
                    ps.setString(3, "%" + TCari.getText().trim() + "%");
                    ps.setString(4, "%" + TCari.getText().trim() + "%");
                    ps.setString(5, "%" + TCari.getText().trim() + "%");
                    ps.setString(6, "%" + TCari.getText().trim() + "%");
                    ps.setString(7, "%" + TCari.getText().trim() + "%");
                    ps.setString(8, "%" + TCari.getText().trim() + "%");
                    ps.setString(9, "%" + TCari.getText().trim() + "%");
                    ps.setString(10, "%" + TCari.getText().trim() + "%");
                    ps.setString(11, "%" + TCari.getText().trim() + "%");
                    ps.setString(12, "%" + TCari.getText().trim() + "%");
                    ps.setString(13, "%" + TCari.getText().trim() + "%");
                }
                rs = ps.executeQuery();
                while (rs.next()) {
                    tabMode.addRow(new Object[]{
                        false, rs.getString("kode_brng"),
                        rs.getString("nama_brng"),
                        rs.getString("kode_satbesar"),
                        rs.getString("satuanbesar"),
                        rs.getDouble("isi"),
                        rs.getString("kode_sat"),
                        rs.getString("satuan"),
                        rs.getDouble("kapasitas"),
                        rs.getString("letak_barang"),
                        rs.getDouble("dasar"),
                        rs.getDouble("h_beli"),
                        rs.getDouble("ralan"),
                        rs.getDouble("kelas1"),
                        rs.getDouble("kelas2"),
                        rs.getDouble("kelas3"),
                        rs.getDouble("utama"),
                        rs.getDouble("vip"),
                        rs.getDouble("vvip"),
                        rs.getDouble("beliluar"),
                        rs.getDouble("jualbebas"),
                        rs.getDouble("karyawan"),
                        rs.getDouble("stokminimal"),
                        rs.getString("kdjns"),
                        rs.getString("nama"),
                        rs.getString("expire"),
                        rs.getString("kode_industri"),
                        rs.getString("nama_industri"),
                        rs.getString("kode_kategori"),
                        rs.getString("kategori"),
                        rs.getString("kode_golongan"),
                        rs.getString("golongan")
                    });
                    stokgudang = 0;
                    ps3 = koneksi.prepareStatement("select sum(stok) from gudangbarang where kode_brng=? and kd_bangsal=?");
                    try {
                        ps3.setString(1, rs.getString(1));
                        ps3.setString(2, kdlokasi);
                        rs3 = ps3.executeQuery();
                        if (rs3.next()) {
                            stokgudang = rs3.getDouble(1);
                        }
                        tabMode.addRow(new Object[]{false, "","    "+nmlokasi, ": " + stokgudang, "", null,null,null,null,null, null, null, null, null, null, null, null, null, null, null, null,null,null, null,null,null,null,null,null,null});
                    } catch (Exception e) {
                        System.out.println("Notifikasi : " + e);
                    } finally{
                        if(rs3 != null){
                            rs3.close();
                        }
                        if(ps3 != null){
                            ps3.close();
                        }
                    }
                }
                rs.last();
                LCount.setText("" + rs.getRow());
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            } finally{
                if(rs != null){
                    rs.close();
                }
                if(ps != null){
                    ps.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
            
    }
    
    public void tampil4(String NoRetur) {
        if(akses.getform().equals("DlgReturJual")){
            Valid.tabelKosong(tabMode);
            try {
                ps4 = koneksi.prepareStatement(
                        "select databarang.kode_brng, databarang.nama_brng,databarang.kode_satbesar,satuanbesar.satuan as satuanbesar, "
                        + " databarang.isi,databarang.kode_sat,kodesatuan.satuan,databarang.letak_barang,databarang.dasar,databarang.h_beli,"
                        + " databarang.ralan,databarang.kelas1,databarang.kelas2,databarang.kelas3,"
                        + " databarang.utama,databarang.vip,databarang.vvip,databarang.beliluar,databarang.jualbebas,"
                        + " databarang.karyawan,databarang.stokminimal, databarang.kdjns,"
                        + " jenis.nama,kapasitas,databarang.expire,databarang.kode_industri,industrifarmasi.nama_industri, "
                        + " databarang.kode_kategori,kategori_barang.nama as kategori,databarang.kode_golongan,golongan_barang.nama as golongan "
                        + " from databarang inner join kodesatuan on databarang.kode_sat=kodesatuan.kode_sat "
                        + " inner join kodesatuan as satuanbesar on databarang.kode_satbesar=satuanbesar.kode_sat "
                        + " inner join jenis on databarang.kdjns=jenis.kdjns "
                        + " inner join industrifarmasi on databarang.kode_industri=industrifarmasi.kode_industri "
                        + " inner join golongan_barang on databarang.kode_golongan=golongan_barang.kode "
                        + " inner join kategori_barang on databarang.kode_kategori=kategori_barang.kode "
                        + " inner join detail_pemberian_obat on detail_pemberian_obat.kode_brng=databarang.kode_brng "
                        + " where detail_pemberian_obat.no_rawat=? group by databarang.kode_brng order by databarang.nama_brng");
                try {
                    ps4.setString(1,NoRetur);
                    rs = ps4.executeQuery();
                    while (rs.next()) {
                        tabMode.addRow(new Object[]{
                            false, rs.getString("kode_brng"),
                            rs.getString("nama_brng"),
                            rs.getString("kode_satbesar"),
                            rs.getString("satuanbesar"),
                            rs.getDouble("isi"),
                            rs.getString("kode_sat"),
                            rs.getString("satuan"),
                            rs.getDouble("kapasitas"),
                            rs.getString("letak_barang"),
                            rs.getDouble("dasar"),
                            rs.getDouble("h_beli"),
                            rs.getDouble("ralan"),
                            rs.getDouble("kelas1"),
                            rs.getDouble("kelas2"),
                            rs.getDouble("kelas3"),
                            rs.getDouble("utama"),
                            rs.getDouble("vip"),
                            rs.getDouble("vvip"),
                            rs.getDouble("beliluar"),
                            rs.getDouble("jualbebas"),
                            rs.getDouble("karyawan"),
                            rs.getDouble("stokminimal"),
                            rs.getString("kdjns"),
                            rs.getString("nama"),
                            rs.getString("expire"),
                            rs.getString("kode_industri"),
                            rs.getString("nama_industri"),
                            rs.getString("kode_kategori"),
                            rs.getString("kategori"),
                            rs.getString("kode_golongan"),
                            rs.getString("golongan")
                        });
                    }
                } catch (Exception e) {
                    System.out.println("Notifikasi : " + e);
                }finally{
                    if(rs != null){
                        rs.close();
                    }
                    if(ps4 != null){
                        ps4.close();
                    }
                }
                LCount.setText("" + tabMode.getRowCount());
                if(tabMode.getRowCount()==0){
                   tampil();
                }
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }else{
            tampil();
        }    
            
    }

    public void emptTeks() {
        Kd.setText("");
        Nm.setText("");
        kdsat.setText("");
        nmsat.setText("");
        kdsatBesar.setText("");
        nmsatBesar.setText("");
        Letak.setText("");
        dasar.setText("0");
        beli.setText("0");
        kelas2.setText("0");
        kelas1.setText("0");
        ralan.setText("0");
        kelas3.setText("0");
        kelasvip.setText("0");
        kelasvvip.setText("0");
        utama.setText("0");
        jualbebas.setText("0");
        beliluar.setText("0");
        karyawan.setText("0");
        Kapasitas.setText("0");
        Isi.setText("0");
        stok_minimal.setText("0");
        kdjns.setText("");
        nmjns.setText("");
        kdkategori.setText("");
        nmkategori.setText("");
        kdgolongan.setText("");
        nmgolongan.setText("");

        Kd.requestFocus();
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(kode_brng,4),signed)),0)  from databarang ", "B", 9, Kd);
        //Valid.autoNomer("databarang","B",9,Kd);
    }

    private void getData() {
        int row = tbDokter.getSelectedRow();
        if (row != -1) {
            Kd.setText(tbDokter.getValueAt(row, 1).toString());
            Nm.setText(tbDokter.getValueAt(row, 2).toString());
            kdsatBesar.setText(tbDokter.getValueAt(row, 3).toString());
            nmsatBesar.setText(tbDokter.getValueAt(row, 4).toString());
            Isi.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 5).toString())));
            kdsat.setText(tbDokter.getValueAt(row, 6).toString());
            nmsat.setText(tbDokter.getValueAt(row, 7).toString());
            Kapasitas.setText(tbDokter.getValueAt(row, 8).toString());
            Letak.setText(tbDokter.getValueAt(row,9).toString());
            dasar.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 10).toString())));
            beli.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 11).toString())));
            ralan.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 12).toString())));
            kelas1.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 13).toString())));
            kelas2.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 14).toString())));
            kelas3.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 15).toString())));
            utama.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 16).toString())));
            kelasvip.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 17).toString())));
            kelasvvip.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 18).toString())));
            beliluar.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 19).toString())));
            jualbebas.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 20).toString())));
            karyawan.setText(Double.toString(Double.parseDouble(tbDokter.getValueAt(row, 21).toString())));
            stok_minimal.setText(tbDokter.getValueAt(row, 22).toString());
            kdjns.setText(tbDokter.getValueAt(row, 23).toString());
            nmjns.setText(tbDokter.getValueAt(row, 24).toString());
            KdIF.setText(tbDokter.getValueAt(row, 26).toString());
            NmIF.setText(tbDokter.getValueAt(row, 27).toString());
            kdkategori.setText(tbDokter.getValueAt(row, 28).toString());
            nmkategori.setText(tbDokter.getValueAt(row, 29).toString());
            kdgolongan.setText(tbDokter.getValueAt(row, 30).toString());
            nmgolongan.setText(tbDokter.getValueAt(row, 31).toString());
           
            ChkKadaluarsa.setSelected(false);
            DTPExpired.setDate(new Date());
            if(tbDokter.getValueAt(row, 25).toString().equals("")||(tbDokter.getValueAt(row, 25).toString().length()==0)){
                ChkKadaluarsa.setSelected(false);
                DTPExpired.setDate(new Date());
            }else{
                ChkKadaluarsa.setSelected(true);
                Valid.SetTgl(DTPExpired, tbDokter.getValueAt(row, 25).toString());
            }
        }
    }

    public JTable getTable() {
        return tbDokter;
    }

    private void isHitung() {   
        if(this.isVisible()==true){
            try {
                if (!beli.getText().equals("")) {
                    switch (pengaturanharga) {
                        case "Per Jenis":
                            if(kdjns.getText().equals("")){
                                Valid.textKosong(kdjns,"Kode Jenis");
                            }else{
                                try{
                                    rs=koneksi.prepareStatement("select * from setpenjualan where kdjns='"+kdjns.getText()+"'").executeQuery();
                                    if(rs.next()){
                                        ralan.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("ralan") / 100)),100)));
                                        kelas1.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("kelas1") / 100)),100)));
                                        kelas2.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("kelas2") / 100)),100)));
                                        kelas3.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("kelas3") / 100)),100)));
                                        utama.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("utama") / 100)),100)));
                                        kelasvip.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("vip") / 100)),100)));
                                        kelasvvip.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("vvip") / 100)),100)));
                                        beliluar.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("beliluar") / 100)),100)));
                                        jualbebas.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("jualbebas") / 100)),100)));
                                        karyawan.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("karyawan") / 100)),100)));
                                    }else{
                                        JOptionPane.showMessageDialog(null,"Pengaturan untuk jenis obat/alkes/bhp ini tidak ditemukan..!!");
                                        TCari.requestFocus();
                                    }
                                }catch(Exception e){
                                    System.out.println("Notifikasi : "+e);
                                }finally{
                                    if(rs!=null){
                                        rs.close();
                                    }
                                }
                            }
                            break;
                        case "Umum":
                            try{
                                rs=koneksi.prepareStatement("select * from setpenjualanumum").executeQuery();
                                if(rs.next()){
                                    ralan.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("ralan") / 100)),100)));
                                    kelas1.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("kelas1") / 100)),100)));
                                    kelas2.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("kelas2") / 100)),100)));
                                    kelas3.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("kelas3") / 100)),100)));
                                    utama.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("utama") / 100)),100)));
                                    kelasvip.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("vip") / 100)),100)));
                                    kelasvvip.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("vvip") / 100)),100)));
                                    beliluar.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("beliluar") / 100)),100)));
                                    jualbebas.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("jualbebas") / 100)),100)));
                                    karyawan.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("karyawan") / 100)),100)));
                                }else{
                                    JOptionPane.showMessageDialog(null,"Pengaturan harga umum masih kosong..!!");
                                    TCari.requestFocus();
                                }
                            }catch(Exception e){
                                System.out.println("Notifikasi : "+e);
                            }finally{
                                if(rs!=null){
                                    rs.close();
                                }
                            }   break;
                        case "Per Barang":
                            if(Kd.getText().equals("")){
                                Valid.textKosong(Kd,"Kode Barang");
                            }else{
                                try{
                                    rs=koneksi.prepareStatement("select * from setpenjualanperbarang where kode_brng='"+Kd.getText()+"'").executeQuery();
                                    if(rs.next()){
                                        ralan.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("ralan") / 100)),100)));
                                        kelas1.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("kelas1") / 100)),100)));
                                        kelas2.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("kelas2") / 100)),100)));
                                        kelas3.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("kelas3") / 100)),100)));
                                        utama.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("utama") / 100)),100)));
                                        kelasvip.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("vip") / 100)),100)));
                                        kelasvvip.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("vvip") / 100)),100)));
                                        beliluar.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("beliluar") / 100)),100)));
                                        jualbebas.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("jualbebas") / 100)),100)));
                                        karyawan.setText(Double.toString(Valid.roundUp(Double.parseDouble(beli.getText()) + (Double.parseDouble(beli.getText()) * (rs.getDouble("karyawan") / 100)),100)));
                                    }else{
                                        JOptionPane.showMessageDialog(null,"Pengaturan untuk obat/alkes/bhp ini tidak ditemukan..!!");
                                        TCari.requestFocus();
                                    }
                                }catch(Exception e){
                                    System.out.println("Notifikasi : "+e);
                                }finally{
                                    if(rs!=null){
                                        rs.close();
                                    }
                                }
                            }
                            break;
                        default:
                            JOptionPane.showMessageDialog(null,"Pengaturan untuk obat/alkes/bhp belum disetting..!!");
                            break;                    
                    }                    
                }                               
            } catch (Exception e) {
                System.out.println("Notifikasi : " + e);
            }
        }            
    }

    private void isForm() {
        if (ChkInput.isSelected() == true) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 338));
            FormInput.setVisible(true);
            ChkInput.setVisible(true);
        } else if (ChkInput.isSelected() == false) {
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH, 20));
            FormInput.setVisible(false);
            ChkInput.setVisible(true);
        }
    }

    public void setLokasi(String kodelokasi,String namalokasi){
        this.kdlokasi=kodelokasi;
        this.nmlokasi=namalokasi;
    }
            
    public void isCek() {
        TCari.requestFocus();
        BtnSimpan.setEnabled(akses.getobat());
        BtnHapus.setEnabled(akses.getobat());
        BtnEdit.setEnabled(akses.getobat());
        BtnPrint.setEnabled(akses.getobat());
        if(akses.getkode().equals("Admin Utama")){
            MnRestore.setEnabled(true);
        }else{
            MnRestore.setEnabled(false);
        } 
    }

}
