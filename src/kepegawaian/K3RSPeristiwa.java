/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgJnsPerawatan.java
 *
 * Created on May 22, 2010, 11:58:21 PM
 */

package kepegawaian;
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
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author dosen
 */
public final class K3RSPeristiwa extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    private K3RSJenisPekerjaan jenispekerjaan=new K3RSJenisPekerjaan(null,false);
    private K3RSLokasiKejadian lokasikejadian=new K3RSLokasiKejadian(null,false);
    private K3RSPenyebab penyebab=new K3RSPenyebab(null,false);
    private K3RSJenisCidera jeniscidera=new K3RSJenisCidera(null,false);
    private K3RSJenisLuka jenisluka=new K3RSJenisLuka(null,false);
    private K3RSBagianTubuh bagiantubuh=new K3RSBagianTubuh(null,false);
    private K3RSDampakCidera dampakcidera=new K3RSDampakCidera(null,false);
    private DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private LocalDate birthday;
    private Period p;

    /** Creates new form DlgJnsPerawatan
     * @param parent
     * @param modal */
    public K3RSPeristiwa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(628,674);

        tabMode=new DefaultTableModel(null,new Object[]{
                "No.Laporan","DTPCari Insiden","Kd.Pekerjaan","Jenis Pekerjaan","DTPCari Pelaporan","Kd.Lokasi","Lokasi Kejadian","Kronologi Kejadian","Kd.Aspek","Aspek Penyebab",
                "NIK Korban","Nama Korban","Kategori Cidera","LT(Hari)","Kd.Cidera","Jenis Cidera","Kd.Luka","Jenis Luka","Kd.Bagian","Bagian Tubuh","Kondisi Tidak Aman",
                "Tindakan Tidak Aman","Pribadi","Pekerjaan","Kd.Dampak","Dampak Kejadian","NIK Pelapor","Nama Pelapor","Barang Bukti","Jenis Tindakan","Rencana Tindakan","Target",
                "Wewenang","Catatan","NIK Tim K3","Nama Tim K3"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        tbJnsPerawatan.setModel(tabMode);

        //tbObat.setDefaultRenderer(Object.class, new WarnaTable(panelJudul.getBackground(),tbObat.getBackground()));
        tbJnsPerawatan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbJnsPerawatan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 36; i++) {
            TableColumn column = tbJnsPerawatan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(110);
            }else if(i==1){
                column.setPreferredWidth(110);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(230);
            }else if(i==4){
                column.setPreferredWidth(110);
            }else if(i==5){
                //column.setPreferredWidth(50);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setPreferredWidth(230);
            }else if(i==7){
                column.setPreferredWidth(230);
            }else if(i==8){
                //column.setPreferredWidth(50);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==9){
                column.setPreferredWidth(230);
            }else if(i==10){
                column.setPreferredWidth(90);
            }else if(i==11){
                column.setPreferredWidth(170);
            }else if(i==12){
                column.setPreferredWidth(85);
            }else if(i==13){
                column.setPreferredWidth(47);
            }else if(i==14){
                //column.setPreferredWidth(50);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setPreferredWidth(230);
            }else if(i==16){
                //column.setPreferredWidth(50);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==17){
                column.setPreferredWidth(230);
            }else if(i==18){
                //column.setPreferredWidth(50);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==19){
                column.setPreferredWidth(230);
            }else if(i==20){
                column.setPreferredWidth(190);
            }else if(i==21){
                column.setPreferredWidth(190);
            }else if(i==22){
                column.setPreferredWidth(190);
            }else if(i==23){
                column.setPreferredWidth(190);
            }else if(i==24){
                //column.setPreferredWidth(50);
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==25){
                column.setPreferredWidth(230);
            }else if(i==26){
                column.setPreferredWidth(90);
            }else if(i==27){
                column.setPreferredWidth(170);
            }else if(i==28){
                column.setPreferredWidth(70);
            }else if(i==29){
                column.setPreferredWidth(120);
            }else if(i==30){
                column.setPreferredWidth(200);
            }else if(i==31){
                column.setPreferredWidth(70);
            }else if(i==32){
                column.setPreferredWidth(140);
            }else if(i==33){
                column.setPreferredWidth(200);
            }else if(i==34){
                column.setPreferredWidth(90);
            }else if(i==35){
                column.setPreferredWidth(170);
            }
        }
        tbJnsPerawatan.setDefaultRenderer(Object.class, new WarnaTable());
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        NoLaporan.setDocument(new batasInput((byte)20).getKata(NoLaporan));
        Kronologi.setDocument(new batasInput((int)300).getKata(Kronologi));
        KondisiTidakAman.setDocument(new batasInput((int)100).getKata(KondisiTidakAman));
        TindakanTidakAman.setDocument(new batasInput((int)100).getKata(TindakanTidakAman));
        Pribadi.setDocument(new batasInput((int)100).getKata(Pribadi));
        Pekerjaan.setDocument(new batasInput((int)100).getKata(Pekerjaan));
        Rencana.setDocument(new batasInput((int)200).getKata(Rencana));
        Catatan.setDocument(new batasInput((int)200).getKata(Catatan));
        Wewenang.setDocument(new batasInput((int)100).getKata(Wewenang));
        LT.setDocument(new batasInput((byte)10).getOnlyAngka(LT));
        
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
        
        jenispekerjaan.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jenispekerjaan.getTable().getSelectedRow()!= -1){
                    KdJenisPekerjaan.setText(jenispekerjaan.getTable().getValueAt(jenispekerjaan.getTable().getSelectedRow(),0).toString());
                    NmJenisPekerjaan.setText(jenispekerjaan.getTable().getValueAt(jenispekerjaan.getTable().getSelectedRow(),1).toString());
                }  
                BtnJenisPekerjaan.requestFocus();
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
        
        jenispekerjaan.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    jenispekerjaan.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        lokasikejadian.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(lokasikejadian.getTable().getSelectedRow()!= -1){
                    KdLokasiKejadian.setText(lokasikejadian.getTable().getValueAt(lokasikejadian.getTable().getSelectedRow(),0).toString());
                    NmLokasiKejadian.setText(lokasikejadian.getTable().getValueAt(lokasikejadian.getTable().getSelectedRow(),1).toString());
                }  
                btnLokasiKejadian.requestFocus();
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
        
        lokasikejadian.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    lokasikejadian.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        penyebab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(penyebab.getTable().getSelectedRow()!= -1){
                    KdPenyebab.setText(penyebab.getTable().getValueAt(penyebab.getTable().getSelectedRow(),0).toString());
                    NmPenyebab.setText(penyebab.getTable().getValueAt(penyebab.getTable().getSelectedRow(),1).toString());
                }  
                BtnPenyebab.requestFocus();
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
        
        penyebab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    penyebab.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        jeniscidera.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jeniscidera.getTable().getSelectedRow()!= -1){
                    KdJenisCidera.setText(jeniscidera.getTable().getValueAt(jeniscidera.getTable().getSelectedRow(),0).toString());
                    NmJenisCidera.setText(jeniscidera.getTable().getValueAt(jeniscidera.getTable().getSelectedRow(),1).toString());
                }  
                btnJenisCidera.requestFocus();
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
        
        jeniscidera.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    jeniscidera.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        jenisluka.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(jenisluka.getTable().getSelectedRow()!= -1){
                    KdJenisLuka.setText(jenisluka.getTable().getValueAt(jenisluka.getTable().getSelectedRow(),0).toString());
                    NmJenisLuka.setText(jenisluka.getTable().getValueAt(jenisluka.getTable().getSelectedRow(),1).toString());
                }  
                btnJenisLuka.requestFocus();
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
        
        jenisluka.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    jenisluka.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        bagiantubuh.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bagiantubuh.getTable().getSelectedRow()!= -1){
                    KdBagianTubuh.setText(bagiantubuh.getTable().getValueAt(bagiantubuh.getTable().getSelectedRow(),0).toString());
                    NmBagianTubuh.setText(bagiantubuh.getTable().getValueAt(bagiantubuh.getTable().getSelectedRow(),1).toString());
                }  
                btnBagianTubuh.requestFocus();
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
        
        bagiantubuh.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    bagiantubuh.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        dampakcidera.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(dampakcidera.getTable().getSelectedRow()!= -1){
                    KdDampak.setText(dampakcidera.getTable().getValueAt(dampakcidera.getTable().getSelectedRow(),0).toString());
                    NmDampak.setText(dampakcidera.getTable().getValueAt(dampakcidera.getTable().getSelectedRow(),1).toString());
                }  
                BtnDampak.requestFocus();
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
        
        dampakcidera.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    dampakcidera.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){   
                    if(i==1){
                        NIKKorban.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),0).toString());
                        NmKorban.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
                        JK.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),2).toString().replaceAll("Wanita","P").replaceAll("Pria","L"));
                        Jabatan.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),3).toString());
                        Bidang.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),6).toString());
                        Departemen.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),5).toString());
                        Valid.SetTgl(TglLahir,pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),12).toString());
                        TglInsidenItemStateChanged(null);
                        btnKorban.requestFocus();
                    } else if(i==2){
                        NIKPelapor.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),0).toString());
                        NmPelapor.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
                        BidangPelapor.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),6).toString());
                        DepartemenPelapor.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),5).toString());
                        btnPelapor.requestFocus();
                    } else if(i==3){
                        NIKK3.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),0).toString());
                        NmPetugasK3.setText(pegawai.tbKamar.getValueAt(pegawai.tbKamar.getSelectedRow(),1).toString());
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
        
        pegawai.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    pegawai.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        TglLahir = new widget.Tanggal();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ppGrafikBatangPeristiwaK3PerTahun = new javax.swing.JMenuItem();
        ppGrafikPiePeristiwaK3PerTahun = new javax.swing.JMenuItem();
        ppGrafikBatangPeristiwaK3PerBulan = new javax.swing.JMenuItem();
        ppGrafikPiePeristiwaK3PerBulan = new javax.swing.JMenuItem();
        ppGrafikBatangPeristiwaK3PerTanggal = new javax.swing.JMenuItem();
        ppGrafikPiePeristiwaK3PerTanggal = new javax.swing.JMenuItem();
        ppGrafikBatangPeristiwaK3PerJenisCidera = new javax.swing.JMenuItem();
        ppGrafikPiePeristiwaK3PerJenisCidera = new javax.swing.JMenuItem();
        ppGrafikBatangPeristiwaK3PerPenyebab = new javax.swing.JMenuItem();
        ppGrafikPiePeristiwaK3PerPenyebab = new javax.swing.JMenuItem();
        ppGrafikBatangPeristiwaK3PerJenisLuka = new javax.swing.JMenuItem();
        ppGrafikPiePeristiwaK3PerJenisLuka = new javax.swing.JMenuItem();
        ppGrafikBatangPeristiwaK3PerLokasiKejadian = new javax.swing.JMenuItem();
        ppGrafikPiePeristiwaK3PerLokasiKejadian = new javax.swing.JMenuItem();
        ppGrafikBatangPeristiwaK3PerDampakCidera = new javax.swing.JMenuItem();
        ppGrafikPiePeristiwaK3PerDampakCidera = new javax.swing.JMenuItem();
        ppGrafikBatangPeristiwaK3PerJenisPekerjaan = new javax.swing.JMenuItem();
        ppGrafikPiePeristiwaK3PerJenisPekerjaan = new javax.swing.JMenuItem();
        ppGrafikBatangPeristiwaK3PerBagianTubuh = new javax.swing.JMenuItem();
        ppGrafikPiePeristiwaK3PerBagianTubuh = new javax.swing.JMenuItem();
        internalFrame1 = new widget.InternalFrame();
        jPanel3 = new javax.swing.JPanel();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        panelGlass9 = new widget.panelisi();
        jLabel58 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel59 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        Scroll1 = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        KondisiTidakAman = new widget.TextBox();
        jLabel13 = new widget.Label();
        TglInsiden = new widget.Tanggal();
        DetikInsiden = new widget.ComboBox();
        MenitInsiden = new widget.ComboBox();
        JamInsiden = new widget.ComboBox();
        jLabel14 = new widget.Label();
        KdJenisPekerjaan = new widget.TextBox();
        NmJenisPekerjaan = new widget.TextBox();
        BtnJenisPekerjaan = new widget.Button();
        jLabel16 = new widget.Label();
        TglPelaporan = new widget.Tanggal();
        JamPelaporan = new widget.ComboBox();
        MenitPelaporan = new widget.ComboBox();
        DetikPelaporan = new widget.ComboBox();
        jLabel17 = new widget.Label();
        KdLokasiKejadian = new widget.TextBox();
        NmLokasiKejadian = new widget.TextBox();
        btnLokasiKejadian = new widget.Button();
        jLabel5 = new widget.Label();
        scrollPane1 = new widget.ScrollPane();
        Kronologi = new widget.TextArea();
        jLabel18 = new widget.Label();
        KdPenyebab = new widget.TextBox();
        NmPenyebab = new widget.TextBox();
        BtnPenyebab = new widget.Button();
        jLabel19 = new widget.Label();
        NIKKorban = new widget.TextBox();
        NmKorban = new widget.TextBox();
        btnKorban = new widget.Button();
        jLabel20 = new widget.Label();
        Bidang = new widget.TextBox();
        jLabel21 = new widget.Label();
        Departemen = new widget.TextBox();
        jLabel22 = new widget.Label();
        JK = new widget.TextBox();
        Usia = new widget.TextBox();
        jLabel23 = new widget.Label();
        jLabel24 = new widget.Label();
        Jabatan = new widget.TextBox();
        jLabel25 = new widget.Label();
        Kategori = new widget.ComboBox();
        jLabel26 = new widget.Label();
        LT = new widget.TextBox();
        jLabel27 = new widget.Label();
        KdJenisCidera = new widget.TextBox();
        NmJenisCidera = new widget.TextBox();
        btnJenisCidera = new widget.Button();
        jLabel28 = new widget.Label();
        KdJenisLuka = new widget.TextBox();
        NmJenisLuka = new widget.TextBox();
        btnJenisLuka = new widget.Button();
        jLabel29 = new widget.Label();
        KdBagianTubuh = new widget.TextBox();
        NmBagianTubuh = new widget.TextBox();
        btnBagianTubuh = new widget.Button();
        jLabel4 = new widget.Label();
        jLabel30 = new widget.Label();
        jLabel8 = new widget.Label();
        jLabel9 = new widget.Label();
        jLabel10 = new widget.Label();
        NoLaporan = new widget.TextBox();
        jLabel11 = new widget.Label();
        TindakanTidakAman = new widget.TextBox();
        jLabel12 = new widget.Label();
        Pribadi = new widget.TextBox();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        Pekerjaan = new widget.TextBox();
        jLabel33 = new widget.Label();
        KdDampak = new widget.TextBox();
        NmDampak = new widget.TextBox();
        BtnDampak = new widget.Button();
        jLabel34 = new widget.Label();
        BarangBukti = new widget.ComboBox();
        jLabel35 = new widget.Label();
        NIKPelapor = new widget.TextBox();
        NmPelapor = new widget.TextBox();
        btnPelapor = new widget.Button();
        jLabel36 = new widget.Label();
        BidangPelapor = new widget.TextBox();
        jLabel37 = new widget.Label();
        DepartemenPelapor = new widget.TextBox();
        jLabel38 = new widget.Label();
        jLabel39 = new widget.Label();
        JenisTindakan = new widget.ComboBox();
        jLabel40 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        Rencana = new widget.TextArea();
        jLabel41 = new widget.Label();
        Target = new widget.Tanggal();
        jLabel42 = new widget.Label();
        Wewenang = new widget.TextBox();
        jLabel43 = new widget.Label();
        scrollPane3 = new widget.ScrollPane();
        Catatan = new widget.TextArea();
        jLabel44 = new widget.Label();
        NIKK3 = new widget.TextBox();
        NmPetugasK3 = new widget.TextBox();
        btnTIMK3 = new widget.Button();
        jLabel45 = new widget.Label();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbJnsPerawatan = new widget.Table();

        TglLahir.setForeground(new java.awt.Color(50, 70, 50));
        TglLahir.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-06-2019" }));
        TglLahir.setDisplayFormat("dd-MM-yyyy");
        TglLahir.setName("TglLahir"); // NOI18N
        TglLahir.setOpaque(false);

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        ppGrafikBatangPeristiwaK3PerTahun.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangPeristiwaK3PerTahun.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangPeristiwaK3PerTahun.setForeground(new java.awt.Color(50,50,50));
        ppGrafikBatangPeristiwaK3PerTahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangPeristiwaK3PerTahun.setText("Grafik Batang Peristiwa K3 Per Tahun");
        ppGrafikBatangPeristiwaK3PerTahun.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangPeristiwaK3PerTahun.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangPeristiwaK3PerTahun.setName("ppGrafikBatangPeristiwaK3PerTahun"); // NOI18N
        ppGrafikBatangPeristiwaK3PerTahun.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikBatangPeristiwaK3PerTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangPeristiwaK3PerTahunActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangPeristiwaK3PerTahun);

        ppGrafikPiePeristiwaK3PerTahun.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiePeristiwaK3PerTahun.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiePeristiwaK3PerTahun.setForeground(new java.awt.Color(50,50,50));
        ppGrafikPiePeristiwaK3PerTahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiePeristiwaK3PerTahun.setText("Grafik Pie Peristiwa K3 Per Tahun");
        ppGrafikPiePeristiwaK3PerTahun.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiePeristiwaK3PerTahun.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiePeristiwaK3PerTahun.setName("ppGrafikPiePeristiwaK3PerTahun"); // NOI18N
        ppGrafikPiePeristiwaK3PerTahun.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikPiePeristiwaK3PerTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiePeristiwaK3PerTahunActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiePeristiwaK3PerTahun);

        ppGrafikBatangPeristiwaK3PerBulan.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangPeristiwaK3PerBulan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangPeristiwaK3PerBulan.setForeground(new java.awt.Color(50,50,50));
        ppGrafikBatangPeristiwaK3PerBulan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangPeristiwaK3PerBulan.setText("Grafik Batang Peristiwa K3 Per Bulan");
        ppGrafikBatangPeristiwaK3PerBulan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangPeristiwaK3PerBulan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangPeristiwaK3PerBulan.setName("ppGrafikBatangPeristiwaK3PerBulan"); // NOI18N
        ppGrafikBatangPeristiwaK3PerBulan.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikBatangPeristiwaK3PerBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangPeristiwaK3PerBulanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangPeristiwaK3PerBulan);

        ppGrafikPiePeristiwaK3PerBulan.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiePeristiwaK3PerBulan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiePeristiwaK3PerBulan.setForeground(new java.awt.Color(50,50,50));
        ppGrafikPiePeristiwaK3PerBulan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiePeristiwaK3PerBulan.setText("Grafik Pie Peristiwa K3 Per Bulan");
        ppGrafikPiePeristiwaK3PerBulan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiePeristiwaK3PerBulan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiePeristiwaK3PerBulan.setName("ppGrafikPiePeristiwaK3PerBulan"); // NOI18N
        ppGrafikPiePeristiwaK3PerBulan.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikPiePeristiwaK3PerBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiePeristiwaK3PerBulanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiePeristiwaK3PerBulan);

        ppGrafikBatangPeristiwaK3PerTanggal.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangPeristiwaK3PerTanggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangPeristiwaK3PerTanggal.setForeground(new java.awt.Color(50,50,50));
        ppGrafikBatangPeristiwaK3PerTanggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangPeristiwaK3PerTanggal.setText("Grafik Batang Peristiwa K3 Per Tanggal");
        ppGrafikBatangPeristiwaK3PerTanggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangPeristiwaK3PerTanggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangPeristiwaK3PerTanggal.setName("ppGrafikBatangPeristiwaK3PerTanggal"); // NOI18N
        ppGrafikBatangPeristiwaK3PerTanggal.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikBatangPeristiwaK3PerTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangPeristiwaK3PerTanggalActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangPeristiwaK3PerTanggal);

        ppGrafikPiePeristiwaK3PerTanggal.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiePeristiwaK3PerTanggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiePeristiwaK3PerTanggal.setForeground(new java.awt.Color(50,50,50));
        ppGrafikPiePeristiwaK3PerTanggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiePeristiwaK3PerTanggal.setText("Grafik Pie Peristiwa K3 Per Tanggal");
        ppGrafikPiePeristiwaK3PerTanggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiePeristiwaK3PerTanggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiePeristiwaK3PerTanggal.setName("ppGrafikPiePeristiwaK3PerTanggal"); // NOI18N
        ppGrafikPiePeristiwaK3PerTanggal.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikPiePeristiwaK3PerTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiePeristiwaK3PerTanggalActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiePeristiwaK3PerTanggal);

        ppGrafikBatangPeristiwaK3PerJenisCidera.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangPeristiwaK3PerJenisCidera.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangPeristiwaK3PerJenisCidera.setForeground(new java.awt.Color(50,50,50));
        ppGrafikBatangPeristiwaK3PerJenisCidera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangPeristiwaK3PerJenisCidera.setText("Grafik Batang Peristiwa K3 Per Jenis Cidera");
        ppGrafikBatangPeristiwaK3PerJenisCidera.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangPeristiwaK3PerJenisCidera.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangPeristiwaK3PerJenisCidera.setName("ppGrafikBatangPeristiwaK3PerJenisCidera"); // NOI18N
        ppGrafikBatangPeristiwaK3PerJenisCidera.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikBatangPeristiwaK3PerJenisCidera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangPeristiwaK3PerJenisCideraActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangPeristiwaK3PerJenisCidera);

        ppGrafikPiePeristiwaK3PerJenisCidera.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiePeristiwaK3PerJenisCidera.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiePeristiwaK3PerJenisCidera.setForeground(new java.awt.Color(50,50,50));
        ppGrafikPiePeristiwaK3PerJenisCidera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiePeristiwaK3PerJenisCidera.setText("Grafik Pie Peristiwa K3 Per Jenis Cidera");
        ppGrafikPiePeristiwaK3PerJenisCidera.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiePeristiwaK3PerJenisCidera.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiePeristiwaK3PerJenisCidera.setName("ppGrafikPiePeristiwaK3PerJenisCidera"); // NOI18N
        ppGrafikPiePeristiwaK3PerJenisCidera.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikPiePeristiwaK3PerJenisCidera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiePeristiwaK3PerJenisCideraActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiePeristiwaK3PerJenisCidera);

        ppGrafikBatangPeristiwaK3PerPenyebab.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangPeristiwaK3PerPenyebab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangPeristiwaK3PerPenyebab.setForeground(new java.awt.Color(50,50,50));
        ppGrafikBatangPeristiwaK3PerPenyebab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangPeristiwaK3PerPenyebab.setText("Grafik Batang Peristiwa K3 Per Penyebab Kecelakaan");
        ppGrafikBatangPeristiwaK3PerPenyebab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangPeristiwaK3PerPenyebab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangPeristiwaK3PerPenyebab.setName("ppGrafikBatangPeristiwaK3PerPenyebab"); // NOI18N
        ppGrafikBatangPeristiwaK3PerPenyebab.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikBatangPeristiwaK3PerPenyebab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangPeristiwaK3PerPenyebabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangPeristiwaK3PerPenyebab);

        ppGrafikPiePeristiwaK3PerPenyebab.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiePeristiwaK3PerPenyebab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiePeristiwaK3PerPenyebab.setForeground(new java.awt.Color(50,50,50));
        ppGrafikPiePeristiwaK3PerPenyebab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiePeristiwaK3PerPenyebab.setText("Grafik Pie Peristiwa K3 Per Penyebab Kecelakaan");
        ppGrafikPiePeristiwaK3PerPenyebab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiePeristiwaK3PerPenyebab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiePeristiwaK3PerPenyebab.setName("ppGrafikPiePeristiwaK3PerPenyebab"); // NOI18N
        ppGrafikPiePeristiwaK3PerPenyebab.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikPiePeristiwaK3PerPenyebab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiePeristiwaK3PerPenyebabActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiePeristiwaK3PerPenyebab);

        ppGrafikBatangPeristiwaK3PerJenisLuka.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangPeristiwaK3PerJenisLuka.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangPeristiwaK3PerJenisLuka.setForeground(new java.awt.Color(50,50,50));
        ppGrafikBatangPeristiwaK3PerJenisLuka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangPeristiwaK3PerJenisLuka.setText("Grafik Batang Peristiwa K3 Per Jenis Luka");
        ppGrafikBatangPeristiwaK3PerJenisLuka.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangPeristiwaK3PerJenisLuka.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangPeristiwaK3PerJenisLuka.setName("ppGrafikBatangPeristiwaK3PerJenisLuka"); // NOI18N
        ppGrafikBatangPeristiwaK3PerJenisLuka.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikBatangPeristiwaK3PerJenisLuka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangPeristiwaK3PerJenisLukaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangPeristiwaK3PerJenisLuka);

        ppGrafikPiePeristiwaK3PerJenisLuka.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiePeristiwaK3PerJenisLuka.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiePeristiwaK3PerJenisLuka.setForeground(new java.awt.Color(50,50,50));
        ppGrafikPiePeristiwaK3PerJenisLuka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiePeristiwaK3PerJenisLuka.setText("Grafik Pie Peristiwa K3 Per Jenis Luka");
        ppGrafikPiePeristiwaK3PerJenisLuka.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiePeristiwaK3PerJenisLuka.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiePeristiwaK3PerJenisLuka.setName("ppGrafikPiePeristiwaK3PerJenisLuka"); // NOI18N
        ppGrafikPiePeristiwaK3PerJenisLuka.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikPiePeristiwaK3PerJenisLuka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiePeristiwaK3PerJenisLukaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiePeristiwaK3PerJenisLuka);

        ppGrafikBatangPeristiwaK3PerLokasiKejadian.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangPeristiwaK3PerLokasiKejadian.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangPeristiwaK3PerLokasiKejadian.setForeground(new java.awt.Color(50,50,50));
        ppGrafikBatangPeristiwaK3PerLokasiKejadian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangPeristiwaK3PerLokasiKejadian.setText("Grafik Batang Peristiwa K3 Per Lokasi Kejadian");
        ppGrafikBatangPeristiwaK3PerLokasiKejadian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangPeristiwaK3PerLokasiKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangPeristiwaK3PerLokasiKejadian.setName("ppGrafikBatangPeristiwaK3PerLokasiKejadian"); // NOI18N
        ppGrafikBatangPeristiwaK3PerLokasiKejadian.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikBatangPeristiwaK3PerLokasiKejadian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangPeristiwaK3PerLokasiKejadianActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangPeristiwaK3PerLokasiKejadian);

        ppGrafikPiePeristiwaK3PerLokasiKejadian.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiePeristiwaK3PerLokasiKejadian.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiePeristiwaK3PerLokasiKejadian.setForeground(new java.awt.Color(50,50,50));
        ppGrafikPiePeristiwaK3PerLokasiKejadian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiePeristiwaK3PerLokasiKejadian.setText("Grafik Pie Peristiwa K3 Per Lokasi Kejadian");
        ppGrafikPiePeristiwaK3PerLokasiKejadian.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiePeristiwaK3PerLokasiKejadian.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiePeristiwaK3PerLokasiKejadian.setName("ppGrafikPiePeristiwaK3PerLokasiKejadian"); // NOI18N
        ppGrafikPiePeristiwaK3PerLokasiKejadian.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikPiePeristiwaK3PerLokasiKejadian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiePeristiwaK3PerLokasiKejadianActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiePeristiwaK3PerLokasiKejadian);

        ppGrafikBatangPeristiwaK3PerDampakCidera.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangPeristiwaK3PerDampakCidera.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangPeristiwaK3PerDampakCidera.setForeground(new java.awt.Color(50,50,50));
        ppGrafikBatangPeristiwaK3PerDampakCidera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangPeristiwaK3PerDampakCidera.setText("Grafik Batang Peristiwa K3 Per Dampak Cidera");
        ppGrafikBatangPeristiwaK3PerDampakCidera.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangPeristiwaK3PerDampakCidera.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangPeristiwaK3PerDampakCidera.setName("ppGrafikBatangPeristiwaK3PerDampakCidera"); // NOI18N
        ppGrafikBatangPeristiwaK3PerDampakCidera.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikBatangPeristiwaK3PerDampakCidera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangPeristiwaK3PerDampakCideraActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangPeristiwaK3PerDampakCidera);

        ppGrafikPiePeristiwaK3PerDampakCidera.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiePeristiwaK3PerDampakCidera.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiePeristiwaK3PerDampakCidera.setForeground(new java.awt.Color(50,50,50));
        ppGrafikPiePeristiwaK3PerDampakCidera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiePeristiwaK3PerDampakCidera.setText("Grafik Pie Peristiwa K3 Per Dampak Cidera");
        ppGrafikPiePeristiwaK3PerDampakCidera.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiePeristiwaK3PerDampakCidera.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiePeristiwaK3PerDampakCidera.setName("ppGrafikPiePeristiwaK3PerDampakCidera"); // NOI18N
        ppGrafikPiePeristiwaK3PerDampakCidera.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikPiePeristiwaK3PerDampakCidera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiePeristiwaK3PerDampakCideraActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiePeristiwaK3PerDampakCidera);

        ppGrafikBatangPeristiwaK3PerJenisPekerjaan.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangPeristiwaK3PerJenisPekerjaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangPeristiwaK3PerJenisPekerjaan.setForeground(new java.awt.Color(50,50,50));
        ppGrafikBatangPeristiwaK3PerJenisPekerjaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangPeristiwaK3PerJenisPekerjaan.setText("Grafik Batang Peristiwa K3 Per Jenis Pekerjaan");
        ppGrafikBatangPeristiwaK3PerJenisPekerjaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangPeristiwaK3PerJenisPekerjaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangPeristiwaK3PerJenisPekerjaan.setName("ppGrafikBatangPeristiwaK3PerJenisPekerjaan"); // NOI18N
        ppGrafikBatangPeristiwaK3PerJenisPekerjaan.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikBatangPeristiwaK3PerJenisPekerjaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangPeristiwaK3PerJenisPekerjaanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangPeristiwaK3PerJenisPekerjaan);

        ppGrafikPiePeristiwaK3PerJenisPekerjaan.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiePeristiwaK3PerJenisPekerjaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiePeristiwaK3PerJenisPekerjaan.setForeground(new java.awt.Color(50,50,50));
        ppGrafikPiePeristiwaK3PerJenisPekerjaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiePeristiwaK3PerJenisPekerjaan.setText("Grafik Pie Peristiwa K3 Per Jenis Pekerjaan");
        ppGrafikPiePeristiwaK3PerJenisPekerjaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiePeristiwaK3PerJenisPekerjaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiePeristiwaK3PerJenisPekerjaan.setName("ppGrafikPiePeristiwaK3PerJenisPekerjaan"); // NOI18N
        ppGrafikPiePeristiwaK3PerJenisPekerjaan.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikPiePeristiwaK3PerJenisPekerjaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiePeristiwaK3PerJenisPekerjaanActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiePeristiwaK3PerJenisPekerjaan);

        ppGrafikBatangPeristiwaK3PerBagianTubuh.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikBatangPeristiwaK3PerBagianTubuh.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikBatangPeristiwaK3PerBagianTubuh.setForeground(new java.awt.Color(50,50,50));
        ppGrafikBatangPeristiwaK3PerBagianTubuh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikBatangPeristiwaK3PerBagianTubuh.setText("Grafik Batang Peristiwa K3 Per Bagian Tubuh");
        ppGrafikBatangPeristiwaK3PerBagianTubuh.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikBatangPeristiwaK3PerBagianTubuh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikBatangPeristiwaK3PerBagianTubuh.setName("ppGrafikBatangPeristiwaK3PerBagianTubuh"); // NOI18N
        ppGrafikBatangPeristiwaK3PerBagianTubuh.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikBatangPeristiwaK3PerBagianTubuh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikBatangPeristiwaK3PerBagianTubuhActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikBatangPeristiwaK3PerBagianTubuh);

        ppGrafikPiePeristiwaK3PerBagianTubuh.setBackground(new java.awt.Color(255, 255, 254));
        ppGrafikPiePeristiwaK3PerBagianTubuh.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPiePeristiwaK3PerBagianTubuh.setForeground(new java.awt.Color(50,50,50));
        ppGrafikPiePeristiwaK3PerBagianTubuh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Create-Ticket24.png"))); // NOI18N
        ppGrafikPiePeristiwaK3PerBagianTubuh.setText("Grafik Pie Peristiwa K3 Per Bagian Tubuh");
        ppGrafikPiePeristiwaK3PerBagianTubuh.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPiePeristiwaK3PerBagianTubuh.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPiePeristiwaK3PerBagianTubuh.setName("ppGrafikPiePeristiwaK3PerBagianTubuh"); // NOI18N
        ppGrafikPiePeristiwaK3PerBagianTubuh.setPreferredSize(new java.awt.Dimension(350, 26));
        ppGrafikPiePeristiwaK3PerBagianTubuh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPiePeristiwaK3PerBagianTubuhActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppGrafikPiePeristiwaK3PerBagianTubuh);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Kejadian Kecelakaan Kerja ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50,50,50))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(44, 100));
        jPanel3.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
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
        panelGlass8.add(BtnSimpan);

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
        panelGlass8.add(BtnBatal);

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
        panelGlass8.add(BtnHapus);

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
        panelGlass8.add(BtnEdit);

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
        panelGlass8.add(BtnPrint);

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
        panelGlass8.add(BtnAll);

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
        panelGlass8.add(BtnKeluar);

        jPanel3.add(panelGlass8, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel58.setText("Periode :");
        jLabel58.setName("jLabel58"); // NOI18N
        jLabel58.setPreferredSize(new java.awt.Dimension(55, 23));
        panelGlass9.add(jLabel58);

        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-06-2019" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setText("s.d");
        jLabel59.setName("jLabel59"); // NOI18N
        jLabel59.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass9.add(jLabel59);

        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-06-2019" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(210, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('2');
        BtnCari.setToolTipText("Alt+2");
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
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(75, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(LCount);

        jPanel3.add(panelGlass9, java.awt.BorderLayout.PAGE_START);

        internalFrame1.add(jPanel3, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50,50,50));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 257));
        FormInput.setLayout(null);

        jLabel3.setText("Kondisi Tidak Aman :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(425, 50, 125, 23);

        KondisiTidakAman.setHighlighter(null);
        KondisiTidakAman.setName("KondisiTidakAman"); // NOI18N
        KondisiTidakAman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KondisiTidakAmanKeyPressed(evt);
            }
        });
        FormInput.add(KondisiTidakAman);
        KondisiTidakAman.setBounds(554, 50, 235, 23);

        jLabel13.setText("Tgl.Insiden :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 60, 97, 23);

        TglInsiden.setForeground(new java.awt.Color(50, 70, 50));
        TglInsiden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-06-2019" }));
        TglInsiden.setDisplayFormat("dd-MM-yyyy");
        TglInsiden.setName("TglInsiden"); // NOI18N
        TglInsiden.setOpaque(false);
        TglInsiden.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                TglInsidenItemStateChanged(evt);
            }
        });
        TglInsiden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglInsidenKeyPressed(evt);
            }
        });
        FormInput.add(TglInsiden);
        TglInsiden.setBounds(101, 60, 90, 23);

        DetikInsiden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        DetikInsiden.setName("DetikInsiden"); // NOI18N
        DetikInsiden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikInsidenKeyPressed(evt);
            }
        });
        FormInput.add(DetikInsiden);
        DetikInsiden.setBounds(324, 60, 62, 23);

        MenitInsiden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        MenitInsiden.setName("MenitInsiden"); // NOI18N
        MenitInsiden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitInsidenKeyPressed(evt);
            }
        });
        FormInput.add(MenitInsiden);
        MenitInsiden.setBounds(259, 60, 62, 23);

        JamInsiden.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        JamInsiden.setName("JamInsiden"); // NOI18N
        JamInsiden.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamInsidenKeyPressed(evt);
            }
        });
        FormInput.add(JamInsiden);
        JamInsiden.setBounds(194, 60, 62, 23);

        jLabel14.setText("Jenis Pekerjaan :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(0, 90, 97, 23);

        KdJenisPekerjaan.setEditable(false);
        KdJenisPekerjaan.setHighlighter(null);
        KdJenisPekerjaan.setName("KdJenisPekerjaan"); // NOI18N
        FormInput.add(KdJenisPekerjaan);
        KdJenisPekerjaan.setBounds(101, 90, 55, 23);

        NmJenisPekerjaan.setEditable(false);
        NmJenisPekerjaan.setName("NmJenisPekerjaan"); // NOI18N
        FormInput.add(NmJenisPekerjaan);
        NmJenisPekerjaan.setBounds(159, 90, 196, 23);

        BtnJenisPekerjaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnJenisPekerjaan.setMnemonic('3');
        BtnJenisPekerjaan.setToolTipText("ALt+3");
        BtnJenisPekerjaan.setName("BtnJenisPekerjaan"); // NOI18N
        BtnJenisPekerjaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJenisPekerjaanActionPerformed(evt);
            }
        });
        BtnJenisPekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnJenisPekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(BtnJenisPekerjaan);
        BtnJenisPekerjaan.setBounds(358, 90, 28, 23);

        jLabel16.setText("Tgl.Pelaporan :");
        jLabel16.setName("jLabel16"); // NOI18N
        FormInput.add(jLabel16);
        jLabel16.setBounds(0, 120, 97, 23);

        TglPelaporan.setForeground(new java.awt.Color(50, 70, 50));
        TglPelaporan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-06-2019" }));
        TglPelaporan.setDisplayFormat("dd-MM-yyyy");
        TglPelaporan.setName("TglPelaporan"); // NOI18N
        TglPelaporan.setOpaque(false);
        TglPelaporan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglPelaporanKeyPressed(evt);
            }
        });
        FormInput.add(TglPelaporan);
        TglPelaporan.setBounds(101, 120, 90, 23);

        JamPelaporan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        JamPelaporan.setName("JamPelaporan"); // NOI18N
        JamPelaporan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JamPelaporanKeyPressed(evt);
            }
        });
        FormInput.add(JamPelaporan);
        JamPelaporan.setBounds(194, 120, 62, 23);

        MenitPelaporan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        MenitPelaporan.setName("MenitPelaporan"); // NOI18N
        MenitPelaporan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MenitPelaporanKeyPressed(evt);
            }
        });
        FormInput.add(MenitPelaporan);
        MenitPelaporan.setBounds(259, 120, 62, 23);

        DetikPelaporan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        DetikPelaporan.setName("DetikPelaporan"); // NOI18N
        DetikPelaporan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DetikPelaporanKeyPressed(evt);
            }
        });
        FormInput.add(DetikPelaporan);
        DetikPelaporan.setBounds(324, 120, 62, 23);

        jLabel17.setText("Lokasi Kejadian :");
        jLabel17.setName("jLabel17"); // NOI18N
        FormInput.add(jLabel17);
        jLabel17.setBounds(0, 150, 97, 23);

        KdLokasiKejadian.setEditable(false);
        KdLokasiKejadian.setHighlighter(null);
        KdLokasiKejadian.setName("KdLokasiKejadian"); // NOI18N
        FormInput.add(KdLokasiKejadian);
        KdLokasiKejadian.setBounds(101, 150, 55, 23);

        NmLokasiKejadian.setEditable(false);
        NmLokasiKejadian.setName("NmLokasiKejadian"); // NOI18N
        FormInput.add(NmLokasiKejadian);
        NmLokasiKejadian.setBounds(159, 150, 196, 23);

        btnLokasiKejadian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnLokasiKejadian.setMnemonic('3');
        btnLokasiKejadian.setToolTipText("ALt+3");
        btnLokasiKejadian.setName("btnLokasiKejadian"); // NOI18N
        btnLokasiKejadian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLokasiKejadianActionPerformed(evt);
            }
        });
        btnLokasiKejadian.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnLokasiKejadianKeyPressed(evt);
            }
        });
        FormInput.add(btnLokasiKejadian);
        btnLokasiKejadian.setBounds(358, 150, 28, 23);

        jLabel5.setText("Kronologi :");
        jLabel5.setName("jLabel5"); // NOI18N
        FormInput.add(jLabel5);
        jLabel5.setBounds(0, 180, 97, 23);

        scrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane1.setName("scrollPane1"); // NOI18N

        Kronologi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Kronologi.setColumns(20);
        Kronologi.setRows(5);
        Kronologi.setName("Kronologi"); // NOI18N
        Kronologi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KronologiKeyPressed(evt);
            }
        });
        scrollPane1.setViewportView(Kronologi);

        FormInput.add(scrollPane1);
        scrollPane1.setBounds(101, 180, 285, 83);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("B. KORBAN :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(10, 300, 100, 23);

        KdPenyebab.setEditable(false);
        KdPenyebab.setHighlighter(null);
        KdPenyebab.setName("KdPenyebab"); // NOI18N
        FormInput.add(KdPenyebab);
        KdPenyebab.setBounds(101, 270, 55, 23);

        NmPenyebab.setEditable(false);
        NmPenyebab.setName("NmPenyebab"); // NOI18N
        FormInput.add(NmPenyebab);
        NmPenyebab.setBounds(159, 270, 196, 23);

        BtnPenyebab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnPenyebab.setMnemonic('3');
        BtnPenyebab.setToolTipText("ALt+3");
        BtnPenyebab.setName("BtnPenyebab"); // NOI18N
        BtnPenyebab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPenyebabActionPerformed(evt);
            }
        });
        BtnPenyebab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPenyebabKeyPressed(evt);
            }
        });
        FormInput.add(BtnPenyebab);
        BtnPenyebab.setBounds(358, 270, 28, 23);

        jLabel19.setText("Korban :");
        jLabel19.setName("jLabel19"); // NOI18N
        FormInput.add(jLabel19);
        jLabel19.setBounds(0, 320, 97, 23);

        NIKKorban.setEditable(false);
        NIKKorban.setHighlighter(null);
        NIKKorban.setName("NIKKorban"); // NOI18N
        FormInput.add(NIKKorban);
        NIKKorban.setBounds(101, 320, 100, 23);

        NmKorban.setEditable(false);
        NmKorban.setName("NmKorban"); // NOI18N
        FormInput.add(NmKorban);
        NmKorban.setBounds(204, 320, 151, 23);

        btnKorban.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnKorban.setMnemonic('3');
        btnKorban.setToolTipText("ALt+3");
        btnKorban.setName("btnKorban"); // NOI18N
        btnKorban.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKorbanActionPerformed(evt);
            }
        });
        btnKorban.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnKorbanKeyPressed(evt);
            }
        });
        FormInput.add(btnKorban);
        btnKorban.setBounds(358, 320, 28, 23);

        jLabel20.setText("Bidang :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(0, 350, 97, 23);

        Bidang.setEditable(false);
        Bidang.setHighlighter(null);
        Bidang.setName("Bidang"); // NOI18N
        FormInput.add(Bidang);
        Bidang.setBounds(101, 350, 100, 23);

        jLabel21.setText("Departemen :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(202, 350, 80, 23);

        Departemen.setEditable(false);
        Departemen.setHighlighter(null);
        Departemen.setName("Departemen"); // NOI18N
        FormInput.add(Departemen);
        Departemen.setBounds(286, 350, 100, 23);

        jLabel22.setText("J.K. :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(0, 380, 97, 23);

        JK.setEditable(false);
        JK.setHighlighter(null);
        JK.setName("JK"); // NOI18N
        FormInput.add(JK);
        JK.setBounds(101, 380, 40, 23);

        Usia.setEditable(false);
        Usia.setHighlighter(null);
        Usia.setName("Usia"); // NOI18N
        FormInput.add(Usia);
        Usia.setBounds(179, 380, 40, 23);

        jLabel23.setText("Usia :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(140, 380, 35, 23);

        jLabel24.setText("Jabatan :");
        jLabel24.setName("jLabel24"); // NOI18N
        FormInput.add(jLabel24);
        jLabel24.setBounds(220, 380, 62, 23);

        Jabatan.setEditable(false);
        Jabatan.setHighlighter(null);
        Jabatan.setName("Jabatan"); // NOI18N
        FormInput.add(Jabatan);
        Jabatan.setBounds(286, 380, 100, 23);

        jLabel25.setText("Kategori Cidera :");
        jLabel25.setName("jLabel25"); // NOI18N
        FormInput.add(jLabel25);
        jLabel25.setBounds(0, 410, 97, 23);

        Kategori.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ringan", "Sedang", "Berat", "Fatal" }));
        Kategori.setLightWeightPopupEnabled(false);
        Kategori.setName("Kategori"); // NOI18N
        Kategori.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KategoriKeyPressed(evt);
            }
        });
        FormInput.add(Kategori);
        Kategori.setBounds(101, 410, 100, 23);

        jLabel26.setText("Lost Time :");
        jLabel26.setName("jLabel26"); // NOI18N
        FormInput.add(jLabel26);
        jLabel26.setBounds(202, 410, 80, 23);

        LT.setHighlighter(null);
        LT.setName("LT"); // NOI18N
        LT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                LTKeyPressed(evt);
            }
        });
        FormInput.add(LT);
        LT.setBounds(286, 410, 50, 23);

        jLabel27.setText("Jenis Cidera :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(0, 440, 97, 23);

        KdJenisCidera.setEditable(false);
        KdJenisCidera.setHighlighter(null);
        KdJenisCidera.setName("KdJenisCidera"); // NOI18N
        FormInput.add(KdJenisCidera);
        KdJenisCidera.setBounds(101, 440, 55, 23);

        NmJenisCidera.setEditable(false);
        NmJenisCidera.setName("NmJenisCidera"); // NOI18N
        FormInput.add(NmJenisCidera);
        NmJenisCidera.setBounds(159, 440, 196, 23);

        btnJenisCidera.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnJenisCidera.setMnemonic('3');
        btnJenisCidera.setToolTipText("ALt+3");
        btnJenisCidera.setName("btnJenisCidera"); // NOI18N
        btnJenisCidera.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJenisCideraActionPerformed(evt);
            }
        });
        btnJenisCidera.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnJenisCideraKeyPressed(evt);
            }
        });
        FormInput.add(btnJenisCidera);
        btnJenisCidera.setBounds(358, 440, 28, 23);

        jLabel28.setText("Jenis Luka :");
        jLabel28.setName("jLabel28"); // NOI18N
        FormInput.add(jLabel28);
        jLabel28.setBounds(0, 470, 97, 23);

        KdJenisLuka.setEditable(false);
        KdJenisLuka.setHighlighter(null);
        KdJenisLuka.setName("KdJenisLuka"); // NOI18N
        FormInput.add(KdJenisLuka);
        KdJenisLuka.setBounds(101, 470, 55, 23);

        NmJenisLuka.setEditable(false);
        NmJenisLuka.setName("NmJenisLuka"); // NOI18N
        FormInput.add(NmJenisLuka);
        NmJenisLuka.setBounds(159, 470, 196, 23);

        btnJenisLuka.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnJenisLuka.setMnemonic('3');
        btnJenisLuka.setToolTipText("ALt+3");
        btnJenisLuka.setName("btnJenisLuka"); // NOI18N
        btnJenisLuka.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnJenisLukaActionPerformed(evt);
            }
        });
        btnJenisLuka.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnJenisLukaKeyPressed(evt);
            }
        });
        FormInput.add(btnJenisLuka);
        btnJenisLuka.setBounds(358, 470, 28, 23);

        jLabel29.setText("Bagian Tubuh :");
        jLabel29.setName("jLabel29"); // NOI18N
        FormInput.add(jLabel29);
        jLabel29.setBounds(0, 500, 97, 23);

        KdBagianTubuh.setEditable(false);
        KdBagianTubuh.setHighlighter(null);
        KdBagianTubuh.setName("KdBagianTubuh"); // NOI18N
        FormInput.add(KdBagianTubuh);
        KdBagianTubuh.setBounds(101, 500, 55, 23);

        NmBagianTubuh.setEditable(false);
        NmBagianTubuh.setName("NmBagianTubuh"); // NOI18N
        FormInput.add(NmBagianTubuh);
        NmBagianTubuh.setBounds(159, 500, 196, 23);

        btnBagianTubuh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnBagianTubuh.setMnemonic('3');
        btnBagianTubuh.setToolTipText("ALt+3");
        btnBagianTubuh.setName("btnBagianTubuh"); // NOI18N
        btnBagianTubuh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBagianTubuhActionPerformed(evt);
            }
        });
        btnBagianTubuh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnBagianTubuhKeyPressed(evt);
            }
        });
        FormInput.add(btnBagianTubuh);
        btnBagianTubuh.setBounds(358, 500, 28, 23);

        jLabel4.setText("No. Laporan :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 30, 97, 23);

        jLabel30.setText("Penyebab :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(0, 270, 97, 23);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel8.setText("A. INSIDEN :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(10, 10, 100, 23);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel9.setText("D. PERBAIKAN & PENCEGAHAN :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(410, 300, 180, 23);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel10.setText("1. Penyebab Langsung");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(424, 30, 120, 23);

        NoLaporan.setHighlighter(null);
        NoLaporan.setName("NoLaporan"); // NOI18N
        NoLaporan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoLaporanKeyPressed(evt);
            }
        });
        FormInput.add(NoLaporan);
        NoLaporan.setBounds(101, 30, 160, 23);

        jLabel11.setText("Tindakan Tidak Aman :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(425, 80, 125, 23);

        TindakanTidakAman.setHighlighter(null);
        TindakanTidakAman.setName("TindakanTidakAman"); // NOI18N
        TindakanTidakAman.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TindakanTidakAmanKeyPressed(evt);
            }
        });
        FormInput.add(TindakanTidakAman);
        TindakanTidakAman.setBounds(554, 80, 235, 23);

        jLabel12.setText("Pribadi :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(425, 120, 125, 23);

        Pribadi.setHighlighter(null);
        Pribadi.setName("Pribadi"); // NOI18N
        Pribadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PribadiKeyPressed(evt);
            }
        });
        FormInput.add(Pribadi);
        Pribadi.setBounds(554, 120, 235, 23);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("2. Tidak Langsung");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(424, 100, 120, 23);

        jLabel32.setText("Pekerjaan :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(425, 150, 125, 23);

        Pekerjaan.setHighlighter(null);
        Pekerjaan.setName("Pekerjaan"); // NOI18N
        Pekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(Pekerjaan);
        Pekerjaan.setBounds(554, 150, 235, 23);

        jLabel33.setText("Dampak :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(400, 180, 100, 23);

        KdDampak.setEditable(false);
        KdDampak.setHighlighter(null);
        KdDampak.setName("KdDampak"); // NOI18N
        FormInput.add(KdDampak);
        KdDampak.setBounds(504, 180, 55, 23);

        NmDampak.setEditable(false);
        NmDampak.setName("NmDampak"); // NOI18N
        FormInput.add(NmDampak);
        NmDampak.setBounds(562, 180, 196, 23);

        BtnDampak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnDampak.setMnemonic('3');
        BtnDampak.setToolTipText("ALt+3");
        BtnDampak.setName("BtnDampak"); // NOI18N
        BtnDampak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDampakActionPerformed(evt);
            }
        });
        BtnDampak.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnDampakKeyPressed(evt);
            }
        });
        FormInput.add(BtnDampak);
        BtnDampak.setBounds(761, 180, 28, 23);

        jLabel34.setText("Barang Bukti :");
        jLabel34.setName("jLabel34"); // NOI18N
        FormInput.add(jLabel34);
        jLabel34.setBounds(400, 270, 100, 23);

        BarangBukti.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak" }));
        BarangBukti.setLightWeightPopupEnabled(false);
        BarangBukti.setName("BarangBukti"); // NOI18N
        BarangBukti.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BarangBuktiKeyPressed(evt);
            }
        });
        FormInput.add(BarangBukti);
        BarangBukti.setBounds(504, 270, 100, 23);

        jLabel35.setText("Pelapor :");
        jLabel35.setName("jLabel35"); // NOI18N
        FormInput.add(jLabel35);
        jLabel35.setBounds(400, 210, 100, 23);

        NIKPelapor.setEditable(false);
        NIKPelapor.setHighlighter(null);
        NIKPelapor.setName("NIKPelapor"); // NOI18N
        FormInput.add(NIKPelapor);
        NIKPelapor.setBounds(504, 210, 100, 23);

        NmPelapor.setEditable(false);
        NmPelapor.setName("NmPelapor"); // NOI18N
        FormInput.add(NmPelapor);
        NmPelapor.setBounds(607, 210, 151, 23);

        btnPelapor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPelapor.setMnemonic('3');
        btnPelapor.setToolTipText("ALt+3");
        btnPelapor.setName("btnPelapor"); // NOI18N
        btnPelapor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPelaporActionPerformed(evt);
            }
        });
        btnPelapor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnPelaporKeyPressed(evt);
            }
        });
        FormInput.add(btnPelapor);
        btnPelapor.setBounds(761, 210, 28, 23);

        jLabel36.setText("Bidang :");
        jLabel36.setName("jLabel36"); // NOI18N
        FormInput.add(jLabel36);
        jLabel36.setBounds(400, 240, 100, 23);

        BidangPelapor.setEditable(false);
        BidangPelapor.setHighlighter(null);
        BidangPelapor.setName("BidangPelapor"); // NOI18N
        FormInput.add(BidangPelapor);
        BidangPelapor.setBounds(504, 240, 100, 23);

        jLabel37.setText("Departemen :");
        jLabel37.setName("jLabel37"); // NOI18N
        FormInput.add(jLabel37);
        jLabel37.setBounds(600, 240, 85, 23);

        DepartemenPelapor.setEditable(false);
        DepartemenPelapor.setHighlighter(null);
        DepartemenPelapor.setName("DepartemenPelapor"); // NOI18N
        FormInput.add(DepartemenPelapor);
        DepartemenPelapor.setBounds(689, 240, 100, 23);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("C. INVESTIGASI KECELAKAAN  :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(410, 10, 170, 23);

        jLabel39.setText("Jenis :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(400, 320, 100, 23);

        JenisTindakan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tindakan Perbaikan", "Tindakan Pencegahan" }));
        JenisTindakan.setLightWeightPopupEnabled(false);
        JenisTindakan.setName("JenisTindakan"); // NOI18N
        JenisTindakan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JenisTindakanKeyPressed(evt);
            }
        });
        FormInput.add(JenisTindakan);
        JenisTindakan.setBounds(504, 320, 170, 23);

        jLabel40.setText("Rencana :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(400, 350, 100, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Rencana.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Rencana.setColumns(20);
        Rencana.setRows(5);
        Rencana.setName("Rencana"); // NOI18N
        Rencana.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RencanaKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Rencana);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(504, 350, 285, 50);

        jLabel41.setText("Target :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(400, 410, 100, 23);

        Target.setForeground(new java.awt.Color(50, 70, 50));
        Target.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-06-2019" }));
        Target.setDisplayFormat("dd-MM-yyyy");
        Target.setName("Target"); // NOI18N
        Target.setOpaque(false);
        Target.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TargetKeyPressed(evt);
            }
        });
        FormInput.add(Target);
        Target.setBounds(504, 410, 90, 23);

        jLabel42.setText("Wewenang :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(595, 410, 70, 23);

        Wewenang.setHighlighter(null);
        Wewenang.setName("Wewenang"); // NOI18N
        Wewenang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                WewenangKeyPressed(evt);
            }
        });
        FormInput.add(Wewenang);
        Wewenang.setBounds(669, 410, 120, 23);

        jLabel43.setText("Catatan :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(400, 440, 100, 23);

        scrollPane3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane3.setName("scrollPane3"); // NOI18N

        Catatan.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Catatan.setColumns(20);
        Catatan.setRows(5);
        Catatan.setName("Catatan"); // NOI18N
        Catatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CatatanKeyPressed(evt);
            }
        });
        scrollPane3.setViewportView(Catatan);

        FormInput.add(scrollPane3);
        scrollPane3.setBounds(504, 440, 285, 50);

        jLabel44.setText("Tim K3 :");
        jLabel44.setName("jLabel44"); // NOI18N
        FormInput.add(jLabel44);
        jLabel44.setBounds(400, 500, 100, 23);

        NIKK3.setEditable(false);
        NIKK3.setHighlighter(null);
        NIKK3.setName("NIKK3"); // NOI18N
        FormInput.add(NIKK3);
        NIKK3.setBounds(504, 500, 100, 23);

        NmPetugasK3.setEditable(false);
        NmPetugasK3.setName("NmPetugasK3"); // NOI18N
        FormInput.add(NmPetugasK3);
        NmPetugasK3.setBounds(607, 500, 151, 23);

        btnTIMK3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnTIMK3.setMnemonic('3');
        btnTIMK3.setToolTipText("ALt+3");
        btnTIMK3.setName("btnTIMK3"); // NOI18N
        btnTIMK3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTIMK3ActionPerformed(evt);
            }
        });
        btnTIMK3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btnTIMK3KeyPressed(evt);
            }
        });
        FormInput.add(btnTIMK3);
        btnTIMK3.setBounds(761, 500, 28, 23);

        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel45.setText("Hari");
        jLabel45.setName("jLabel45"); // NOI18N
        FormInput.add(jLabel45);
        jLabel45.setBounds(342, 410, 40, 23);

        Scroll1.setViewportView(FormInput);

        internalFrame2.add(Scroll1, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Data Peristiwa K3", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbJnsPerawatan.setAutoCreateRowSorter(true);
        tbJnsPerawatan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbJnsPerawatan.setComponentPopupMenu(jPopupMenu1);
        tbJnsPerawatan.setName("tbJnsPerawatan"); // NOI18N
        tbJnsPerawatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbJnsPerawatanMouseClicked(evt);
            }
        });
        tbJnsPerawatan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbJnsPerawatanKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbJnsPerawatan);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Daftar Peristiwa K3", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(NoLaporan.getText().trim().equals("")){
            Valid.textKosong(NoLaporan,"No.Laporan");
        }else if(KdJenisPekerjaan.getText().trim().equals("")||NmJenisPekerjaan.getText().trim().equals("")){
            Valid.textKosong(BtnJenisPekerjaan,"Jenis Pekerjaan");
        }else if(KdLokasiKejadian.getText().trim().equals("")||NmLokasiKejadian.getText().trim().equals("")){
            Valid.textKosong(btnLokasiKejadian,"Lokasi Kejadian");
        }else if(Kronologi.getText().trim().equals("")){
            Valid.textKosong(Kronologi,"Kronologi");
        }else if(KdPenyebab.getText().trim().equals("")||NmPenyebab.getText().trim().equals("")){
            Valid.textKosong(BtnPenyebab,"Aspek Penyebab");
        }else if(NIKKorban.getText().trim().equals("")||NmKorban.getText().trim().equals("")){
            Valid.textKosong(btnKorban,"Korban");
        }else if(LT.getText().trim().equals("")){
            Valid.textKosong(LT,"Lost Time");
        }else if(KdJenisCidera.getText().trim().equals("")||NmJenisCidera.getText().trim().equals("")){
            Valid.textKosong(btnJenisCidera,"Jenis Cidera");
        }else if(KdJenisLuka.getText().trim().equals("")||NmJenisLuka.getText().trim().equals("")){
            Valid.textKosong(btnJenisLuka,"Jenis Luka");
        }else if(KdBagianTubuh.getText().trim().equals("")||NmBagianTubuh.getText().trim().equals("")){
            Valid.textKosong(btnBagianTubuh,"Bagian Tubuh");
        }else if(KondisiTidakAman.getText().trim().equals("")){
            Valid.textKosong(KondisiTidakAman,"Penyebab langsung kondisi tidak aman");
        }else if(TindakanTidakAman.getText().trim().equals("")){
            Valid.textKosong(TindakanTidakAman,"Penyebab langsung tindakan tidak aman");
        }else if(Pribadi.getText().trim().equals("")){
            Valid.textKosong(Pribadi,"Penyebab tidak langsung pribadi");
        }else if(Pekerjaan.getText().trim().equals("")){
            Valid.textKosong(Pekerjaan,"Penyebab tidak langsung pekerjaan");
        }else if(KdDampak.getText().trim().equals("")||NmDampak.getText().trim().equals("")){
            Valid.textKosong(BtnDampak,"Dampak/Akibat Kejadian");
        }else if(NIKPelapor.getText().trim().equals("")||NmPelapor.getText().trim().equals("")){
            Valid.textKosong(btnPelapor,"Pelapor");
        }else if(Rencana.getText().trim().equals("")){
            Valid.textKosong(Rencana,"Rencana Perbaikan");
        }else if(Wewenang.getText().trim().equals("")){
            Valid.textKosong(Wewenang,"Wewenang");
        }else if(Catatan.getText().trim().equals("")){
            Valid.textKosong(Catatan,"Catatan");
        }else if(NIKK3.getText().trim().equals("")||NmPetugasK3.getText().trim().equals("")){
            Valid.textKosong(btnTIMK3,"Petugas K3");
        }else{
            if(Sequel.menyimpantf("k3rs_peristiwa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Laporan",28,new String[]{
                    NoLaporan.getText(),Valid.SetTgl(TglInsiden.getSelectedItem()+""),JamInsiden.getSelectedItem()+":"+MenitInsiden.getSelectedItem()+":"+DetikInsiden.getSelectedItem(), 
                    KdJenisPekerjaan.getText(),Valid.SetTgl(TglPelaporan.getSelectedItem()+""),JamPelaporan.getSelectedItem()+":"+MenitPelaporan.getSelectedItem()+":"+DetikPelaporan.getSelectedItem(), 
                    KdLokasiKejadian.getText(),Kronologi.getText(),KdPenyebab.getText(),NIKKorban.getText(),Kategori.getSelectedItem().toString(),KdJenisCidera.getText(),KdJenisLuka.getText(), 
                    KdBagianTubuh.getText(),LT.getText(),KondisiTidakAman.getText(),TindakanTidakAman.getText(),Pribadi.getText(),Pekerjaan.getText(),BarangBukti.getSelectedItem().toString(), 
                    KdDampak.getText(),NIKPelapor.getText(),JenisTindakan.getSelectedItem().toString(),Rencana.getText(),Valid.SetTgl(Target.getSelectedItem()+""),Wewenang.getText(),NIKK3.getText(),Catatan.getText()
                })==true){
                    emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,btnTIMK3,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        TabRawat.setSelectedIndex(0);
        emptTeks();        
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            TabRawat.setSelectedIndex(0);
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbJnsPerawatan.getSelectedRow() > -1){
            Sequel.meghapus("k3rs_peristiwa","no_k3rs",tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
            emptTeks();
            tampil();
        }
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(NoLaporan.getText().trim().equals("")){
            Valid.textKosong(NoLaporan,"No.Laporan");
        }else if(KdJenisPekerjaan.getText().trim().equals("")||NmJenisPekerjaan.getText().trim().equals("")){
            Valid.textKosong(BtnJenisPekerjaan,"Jenis Pekerjaan");
        }else if(KdLokasiKejadian.getText().trim().equals("")||NmLokasiKejadian.getText().trim().equals("")){
            Valid.textKosong(btnLokasiKejadian,"Lokasi Kejadian");
        }else if(Kronologi.getText().trim().equals("")){
            Valid.textKosong(Kronologi,"Kronologi");
        }else if(KdPenyebab.getText().trim().equals("")||NmPenyebab.getText().trim().equals("")){
            Valid.textKosong(BtnPenyebab,"Aspek Penyebab");
        }else if(NIKKorban.getText().trim().equals("")||NmKorban.getText().trim().equals("")){
            Valid.textKosong(btnKorban,"Korban");
        }else if(LT.getText().trim().equals("")){
            Valid.textKosong(LT,"Lost Time");
        }else if(KdJenisCidera.getText().trim().equals("")||NmJenisCidera.getText().trim().equals("")){
            Valid.textKosong(btnJenisCidera,"Jenis Cidera");
        }else if(KdJenisLuka.getText().trim().equals("")||NmJenisLuka.getText().trim().equals("")){
            Valid.textKosong(btnJenisLuka,"Jenis Luka");
        }else if(KdBagianTubuh.getText().trim().equals("")||NmBagianTubuh.getText().trim().equals("")){
            Valid.textKosong(btnBagianTubuh,"Bagian Tubuh");
        }else if(KondisiTidakAman.getText().trim().equals("")){
            Valid.textKosong(KondisiTidakAman,"Penyebab langsung kondisi tidak aman");
        }else if(TindakanTidakAman.getText().trim().equals("")){
            Valid.textKosong(TindakanTidakAman,"Penyebab langsung tindakan tidak aman");
        }else if(Pribadi.getText().trim().equals("")){
            Valid.textKosong(Pribadi,"Penyebab tidak langsung pribadi");
        }else if(Pekerjaan.getText().trim().equals("")){
            Valid.textKosong(Pekerjaan,"Penyebab tidak langsung pekerjaan");
        }else if(KdDampak.getText().trim().equals("")||NmDampak.getText().trim().equals("")){
            Valid.textKosong(BtnDampak,"Dampak/Akibat Kejadian");
        }else if(NIKPelapor.getText().trim().equals("")||NmPelapor.getText().trim().equals("")){
            Valid.textKosong(btnPelapor,"Pelapor");
        }else if(Rencana.getText().trim().equals("")){
            Valid.textKosong(Rencana,"Rencana Perbaikan");
        }else if(Wewenang.getText().trim().equals("")){
            Valid.textKosong(Wewenang,"Wewenang");
        }else if(Catatan.getText().trim().equals("")){
            Valid.textKosong(Catatan,"Catatan");
        }else if(NIKK3.getText().trim().equals("")||NmPetugasK3.getText().trim().equals("")){
            Valid.textKosong(btnTIMK3,"Petugas K3");
        }else{
            if(tbJnsPerawatan.getSelectedRow() > -1){
                if(Sequel.mengedittf("k3rs_peristiwa","no_k3rs=?","no_k3rs=?,tgl_insiden=?,waktu_insiden=?,kode_pekerjaan=?,tgl_pelaporan=?,waktu_pelaporan=?,kode_lokasi=?,kronologi_kejadian=?,kode_penyebab=?,nik=?,kategori_cidera=?,kode_cidera=?,kode_luka=?,kode_bagian=?,lt=?,penyebab_langsung_kondisi=?,penyebab_langsung_tindakan=?,penyebab_tidak_langsung_pribadi=?,penyebab_tidak_langsung_pekerjaan=?,barang_bukti=?,kode_dampak=?,nik_pelapor=?,perbaikan_jenis_tindakan=?,perbaikan_rencana_tindakan=?,perbaikan_target=?,perbaikan_wewenang=?,nik_timk3=?,catatan=?",29,new String[]{
                        NoLaporan.getText(),Valid.SetTgl(TglInsiden.getSelectedItem()+""),JamInsiden.getSelectedItem()+":"+MenitInsiden.getSelectedItem()+":"+DetikInsiden.getSelectedItem(), 
                        KdJenisPekerjaan.getText(),Valid.SetTgl(TglPelaporan.getSelectedItem()+""),JamPelaporan.getSelectedItem()+":"+MenitPelaporan.getSelectedItem()+":"+DetikPelaporan.getSelectedItem(), 
                        KdLokasiKejadian.getText(),Kronologi.getText(),KdPenyebab.getText(),NIKKorban.getText(),Kategori.getSelectedItem().toString(),KdJenisCidera.getText(),KdJenisLuka.getText(), 
                        KdBagianTubuh.getText(),LT.getText(),KondisiTidakAman.getText(),TindakanTidakAman.getText(),Pribadi.getText(),Pekerjaan.getText(),BarangBukti.getSelectedItem().toString(), 
                        KdDampak.getText(),NIKPelapor.getText(),JenisTindakan.getSelectedItem().toString(),Rencana.getText(),Valid.SetTgl(Target.getSelectedItem()+""),Wewenang.getText(),NIKK3.getText(),
                        Catatan.getText(),tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString()
                    })==true){
                        emptTeks();
                        TabRawat.setSelectedIndex(1);
                        tampil();
                }
            }
        }
        
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }else{Valid.pindah(evt,BtnAll,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            Map<String, Object> param = new HashMap<>();    
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());  
            param.put("periode",DTPCari1.getSelectedItem()+" s.d. "+DTPCari2.getSelectedItem());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
            param.put("tanggal1",Valid.SetTgl(DTPCari1.getSelectedItem()+""));
            param.put("tanggal2",Valid.SetTgl(DTPCari2.getSelectedItem()+"")); 
            param.put("parameter","%"+TCari.getText().trim()+"%"); 
            Valid.MyReport("rptPeristiwaK3RS.jasper","report","::[ Data Pasien Teridentifikasi TB ]::",param);
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

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
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbJnsPerawatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbJnsPerawatanMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                if(evt.getClickCount()==2){
                    getData();
                    TabRawat.setSelectedIndex(0);
                }
            } catch (java.lang.NullPointerException e) {
            }
        }
}//GEN-LAST:event_tbJnsPerawatanMouseClicked

    private void tbJnsPerawatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbJnsPerawatanKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbJnsPerawatanKeyPressed

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==1){
            tampil();
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(this.getHeight()<700){   
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            FormInput.setPreferredSize(new Dimension(FormInput.WIDTH,540));
            if(this.getWidth()<835){
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormInput.setPreferredSize(new Dimension(810,540));
            }else{
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }else{
            Scroll1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);            
            if(this.getWidth()<835){
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);                                
                FormInput.setPreferredSize(new Dimension(810,FormInput.HEIGHT));
            }else{
                Scroll1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);                
            }
        }
    }//GEN-LAST:event_formWindowActivated

    private void NoLaporanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoLaporanKeyPressed
        Valid.pindah(evt,TCari, TglInsiden);
    }//GEN-LAST:event_NoLaporanKeyPressed

    private void TglInsidenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglInsidenKeyPressed
        Valid.pindah(evt,NoLaporan,JamInsiden);
    }//GEN-LAST:event_TglInsidenKeyPressed

    private void MenitInsidenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitInsidenKeyPressed
        Valid.pindah(evt,JamInsiden,DetikInsiden);
    }//GEN-LAST:event_MenitInsidenKeyPressed

    private void DetikInsidenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikInsidenKeyPressed
        Valid.pindah(evt,MenitInsiden,BtnJenisPekerjaan);
    }//GEN-LAST:event_DetikInsidenKeyPressed

    private void BtnJenisPekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnJenisPekerjaanKeyPressed
        Valid.pindah(evt,DetikInsiden,TglPelaporan);
    }//GEN-LAST:event_BtnJenisPekerjaanKeyPressed

    private void TglPelaporanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglPelaporanKeyPressed
        Valid.pindah(evt,BtnJenisPekerjaan,JamPelaporan);
    }//GEN-LAST:event_TglPelaporanKeyPressed

    private void JamPelaporanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamPelaporanKeyPressed
        Valid.pindah(evt,TglPelaporan,MenitPelaporan);
    }//GEN-LAST:event_JamPelaporanKeyPressed

    private void MenitPelaporanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MenitPelaporanKeyPressed
        Valid.pindah(evt,JamPelaporan,DetikPelaporan);
    }//GEN-LAST:event_MenitPelaporanKeyPressed

    private void JamInsidenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JamInsidenKeyPressed
        Valid.pindah(evt,TglInsiden,MenitInsiden);
    }//GEN-LAST:event_JamInsidenKeyPressed

    private void DetikPelaporanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DetikPelaporanKeyPressed
        Valid.pindah(evt,MenitPelaporan,btnLokasiKejadian);
    }//GEN-LAST:event_DetikPelaporanKeyPressed

    private void btnLokasiKejadianKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnLokasiKejadianKeyPressed
        Valid.pindah(evt,DetikPelaporan,Kronologi);
    }//GEN-LAST:event_btnLokasiKejadianKeyPressed

    private void BtnPenyebabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPenyebabKeyPressed
        Valid.pindah(evt,Kronologi,btnKorban);
    }//GEN-LAST:event_BtnPenyebabKeyPressed

    private void KronologiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KronologiKeyPressed
        Valid.pindah(evt,btnLokasiKejadian,BtnPenyebab);
    }//GEN-LAST:event_KronologiKeyPressed

    private void BtnJenisPekerjaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJenisPekerjaanActionPerformed
        jenispekerjaan.isCek();
        jenispekerjaan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jenispekerjaan.setLocationRelativeTo(internalFrame1);
        jenispekerjaan.setVisible(true);
    }//GEN-LAST:event_BtnJenisPekerjaanActionPerformed

    private void btnLokasiKejadianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLokasiKejadianActionPerformed
        lokasikejadian.isCek();
        lokasikejadian.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        lokasikejadian.setLocationRelativeTo(internalFrame1);
        lokasikejadian.setVisible(true);
    }//GEN-LAST:event_btnLokasiKejadianActionPerformed

    private void BtnPenyebabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPenyebabActionPerformed
        penyebab.isCek();
        penyebab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        penyebab.setLocationRelativeTo(internalFrame1);
        penyebab.setVisible(true);
    }//GEN-LAST:event_BtnPenyebabActionPerformed

    private void btnKorbanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKorbanActionPerformed
        i=1;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnKorbanActionPerformed

    private void TglInsidenItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_TglInsidenItemStateChanged
        birthday = TglLahir.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        p = Period.between(birthday,TglInsiden.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        Usia.setText(String.valueOf(p.getYears()));
    }//GEN-LAST:event_TglInsidenItemStateChanged

    private void btnKorbanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnKorbanKeyPressed
        Valid.pindah(evt,BtnPenyebab,Kategori);
    }//GEN-LAST:event_btnKorbanKeyPressed

    private void KategoriKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KategoriKeyPressed
        Valid.pindah(evt,btnKorban,LT);
    }//GEN-LAST:event_KategoriKeyPressed

    private void LTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_LTKeyPressed
        Valid.pindah(evt,Kategori,btnJenisCidera);
    }//GEN-LAST:event_LTKeyPressed

    private void btnJenisCideraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnJenisCideraKeyPressed
        Valid.pindah(evt,LT,btnJenisLuka);
    }//GEN-LAST:event_btnJenisCideraKeyPressed

    private void btnJenisLukaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnJenisLukaKeyPressed
        Valid.pindah(evt,btnJenisCidera,btnBagianTubuh);
    }//GEN-LAST:event_btnJenisLukaKeyPressed

    private void btnBagianTubuhKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnBagianTubuhKeyPressed
        Valid.pindah(evt,btnJenisLuka,KondisiTidakAman);
    }//GEN-LAST:event_btnBagianTubuhKeyPressed

    private void btnJenisCideraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJenisCideraActionPerformed
        jeniscidera.isCek();
        jeniscidera.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jeniscidera.setLocationRelativeTo(internalFrame1);
        jeniscidera.setVisible(true);
    }//GEN-LAST:event_btnJenisCideraActionPerformed

    private void btnJenisLukaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnJenisLukaActionPerformed
        jenisluka.isCek();
        jenisluka.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        jenisluka.setLocationRelativeTo(internalFrame1);
        jenisluka.setVisible(true);
    }//GEN-LAST:event_btnJenisLukaActionPerformed

    private void btnBagianTubuhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBagianTubuhActionPerformed
        bagiantubuh.isCek();
        bagiantubuh.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bagiantubuh.setLocationRelativeTo(internalFrame1);
        bagiantubuh.setVisible(true);
    }//GEN-LAST:event_btnBagianTubuhActionPerformed

    private void KondisiTidakAmanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KondisiTidakAmanKeyPressed
        Valid.pindah(evt,btnBagianTubuh,TindakanTidakAman);
    }//GEN-LAST:event_KondisiTidakAmanKeyPressed

    private void TindakanTidakAmanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TindakanTidakAmanKeyPressed
        Valid.pindah(evt,KondisiTidakAman,Pribadi);
    }//GEN-LAST:event_TindakanTidakAmanKeyPressed

    private void PribadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PribadiKeyPressed
        Valid.pindah(evt,TindakanTidakAman,Pekerjaan);
    }//GEN-LAST:event_PribadiKeyPressed

    private void PekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PekerjaanKeyPressed
        Valid.pindah(evt,Pribadi,BtnDampak);
    }//GEN-LAST:event_PekerjaanKeyPressed

    private void BtnDampakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDampakActionPerformed
        dampakcidera.isCek();
        dampakcidera.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dampakcidera.setLocationRelativeTo(internalFrame1);
        dampakcidera.setVisible(true);
    }//GEN-LAST:event_BtnDampakActionPerformed

    private void BtnDampakKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnDampakKeyPressed
        Valid.pindah(evt,Pekerjaan,btnPelapor);
    }//GEN-LAST:event_BtnDampakKeyPressed

    private void btnPelaporKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnPelaporKeyPressed
        Valid.pindah(evt,BtnDampak,BarangBukti);
    }//GEN-LAST:event_btnPelaporKeyPressed

    private void BarangBuktiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BarangBuktiKeyPressed
        Valid.pindah(evt,btnPelapor,JenisTindakan);
    }//GEN-LAST:event_BarangBuktiKeyPressed

    private void btnPelaporActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPelaporActionPerformed
        i=2;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnPelaporActionPerformed

    private void JenisTindakanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JenisTindakanKeyPressed
        Valid.pindah(evt,BarangBukti,Rencana);
    }//GEN-LAST:event_JenisTindakanKeyPressed

    private void RencanaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_RencanaKeyPressed
        Valid.pindah(evt,JenisTindakan,Target);
    }//GEN-LAST:event_RencanaKeyPressed

    private void TargetKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TargetKeyPressed
        Valid.pindah(evt,Rencana,Wewenang);
    }//GEN-LAST:event_TargetKeyPressed

    private void WewenangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_WewenangKeyPressed
        Valid.pindah(evt,Target,Catatan);
    }//GEN-LAST:event_WewenangKeyPressed

    private void CatatanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CatatanKeyPressed
        Valid.pindah(evt,Wewenang,btnTIMK3);
    }//GEN-LAST:event_CatatanKeyPressed

    private void btnTIMK3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnTIMK3KeyPressed
        Valid.pindah(evt,Catatan,BtnSimpan);
    }//GEN-LAST:event_btnTIMK3KeyPressed

    private void btnTIMK3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTIMK3ActionPerformed
        i=3;
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnTIMK3ActionPerformed

    private void ppGrafikBatangPeristiwaK3PerTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangPeristiwaK3PerTahunActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select year(k3rs_peristiwa.tgl_pelaporan),count(year(k3rs_peristiwa.tgl_pelaporan)) as jumlah "+
                "from k3rs_peristiwa where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by year(k3rs_peristiwa.tgl_pelaporan)").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        JFreeChart freeChart = ChartFactory.createBarChart("Grafik Peristiwa K3 Per Tahun Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Tahun","Jumlah", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Tahun",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setLocationRelativeTo(internalFrame1);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikBatangPeristiwaK3PerTahunActionPerformed

    private void ppGrafikPiePeristiwaK3PerTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiePeristiwaK3PerTahunActionPerformed
        DefaultPieDataset dpd = new DefaultPieDataset();
        try {                
            rs = koneksi.prepareStatement("select year(k3rs_peristiwa.tgl_pelaporan),count(year(k3rs_peristiwa.tgl_pelaporan)) as jumlah "+
                "from k3rs_peristiwa where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by year(k3rs_peristiwa.tgl_pelaporan)").executeQuery();
            while(rs.next()) {
                dpd.setValue(rs.getString(1)+"("+rs.getString(2)+")",rs.getDouble(2));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } 
        
        JFreeChart freeChart = ChartFactory.createPieChart("Grafik Peristiwa K3 Per Tahun Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Tahun",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikPiePeristiwaK3PerTahunActionPerformed

    private void ppGrafikBatangPeristiwaK3PerBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangPeristiwaK3PerBulanActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select DATE_FORMAT(k3rs_peristiwa.tgl_pelaporan, '%y-%m'),count(DATE_FORMAT(k3rs_peristiwa.tgl_pelaporan, '%y-%m')) as jumlah "+
                "from k3rs_peristiwa where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by DATE_FORMAT(k3rs_peristiwa.tgl_pelaporan, '%y-%m')").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        JFreeChart freeChart = ChartFactory.createBarChart("Grafik Peristiwa K3 Per Bulan Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Bulan","Jumlah", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Bulan",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setLocationRelativeTo(internalFrame1);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true); 
    }//GEN-LAST:event_ppGrafikBatangPeristiwaK3PerBulanActionPerformed

    private void ppGrafikPiePeristiwaK3PerBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiePeristiwaK3PerBulanActionPerformed
        DefaultPieDataset dpd = new DefaultPieDataset();
        try {                
            rs = koneksi.prepareStatement("select DATE_FORMAT(k3rs_peristiwa.tgl_pelaporan, '%y-%m'),count(DATE_FORMAT(k3rs_peristiwa.tgl_pelaporan, '%y-%m')) as jumlah "+
                "from k3rs_peristiwa where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by DATE_FORMAT(k3rs_peristiwa.tgl_pelaporan, '%y-%m')").executeQuery();
            while(rs.next()) {
                dpd.setValue(rs.getString(1)+"("+rs.getString(2)+")",rs.getDouble(2));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } 
        
        JFreeChart freeChart = ChartFactory.createPieChart("Grafik Peristiwa K3 Per Bulan Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Bulan",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikPiePeristiwaK3PerBulanActionPerformed

    private void ppGrafikBatangPeristiwaK3PerTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangPeristiwaK3PerTanggalActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select DATE_FORMAT(k3rs_peristiwa.tgl_pelaporan, '%y-%m-%d'),count(DATE_FORMAT(k3rs_peristiwa.tgl_pelaporan, '%y-%m-%d')) as jumlah "+
                "from k3rs_peristiwa where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by DATE_FORMAT(k3rs_peristiwa.tgl_pelaporan, '%y-%m-%d')").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        JFreeChart freeChart = ChartFactory.createBarChart("Grafik Peristiwa K3 Per DTPCari Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"DTPCari","Jumlah", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per DTPCari",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setLocationRelativeTo(internalFrame1);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikBatangPeristiwaK3PerTanggalActionPerformed

    private void ppGrafikPiePeristiwaK3PerTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiePeristiwaK3PerTanggalActionPerformed
        DefaultPieDataset dpd = new DefaultPieDataset();
        try {                
            rs = koneksi.prepareStatement("select DATE_FORMAT(k3rs_peristiwa.tgl_pelaporan, '%y-%m-%d'),count(DATE_FORMAT(k3rs_peristiwa.tgl_pelaporan, '%y-%m-%d')) as jumlah "+
                "from k3rs_peristiwa where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by DATE_FORMAT(k3rs_peristiwa.tgl_pelaporan, '%y-%m-%d')").executeQuery();
            while(rs.next()) {
                dpd.setValue(rs.getString(1)+"("+rs.getString(2)+")",rs.getDouble(2));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } 
        
        JFreeChart freeChart = ChartFactory.createPieChart("Grafik Peristiwa K3 Per DTPCari Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per DTPCari",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikPiePeristiwaK3PerTanggalActionPerformed

    private void ppGrafikBatangPeristiwaK3PerJenisCideraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangPeristiwaK3PerJenisCideraActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_jenis_cidera.jenis_cidera,count(k3rs_peristiwa.kode_cidera) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_jenis_cidera on k3rs_jenis_cidera.kode_cidera=k3rs_peristiwa.kode_cidera where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_cidera").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        JFreeChart freeChart = ChartFactory.createBarChart("Grafik Peristiwa K3 Per Jenis Cidera Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Jenis Cidera","Jumlah", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Jenis Cidera",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setLocationRelativeTo(internalFrame1);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikBatangPeristiwaK3PerJenisCideraActionPerformed

    private void ppGrafikPiePeristiwaK3PerJenisCideraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiePeristiwaK3PerJenisCideraActionPerformed
        DefaultPieDataset dpd = new DefaultPieDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_jenis_cidera.jenis_cidera,count(k3rs_peristiwa.kode_cidera) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_jenis_cidera on k3rs_jenis_cidera.kode_cidera=k3rs_peristiwa.kode_cidera where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_cidera").executeQuery();
            while(rs.next()) {
                dpd.setValue(rs.getString(1)+"("+rs.getString(2)+")",rs.getDouble(2));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } 
        
        JFreeChart freeChart = ChartFactory.createPieChart("Grafik Peristiwa K3 Per Jenis Cidera Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Jenis Cidera",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikPiePeristiwaK3PerJenisCideraActionPerformed

    private void ppGrafikBatangPeristiwaK3PerPenyebabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangPeristiwaK3PerPenyebabActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_penyebab.penyebab_kecelakaan,count(k3rs_peristiwa.kode_penyebab) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_penyebab on k3rs_penyebab.kode_penyebab=k3rs_peristiwa.kode_penyebab where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_penyebab").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        JFreeChart freeChart = ChartFactory.createBarChart("Grafik Peristiwa K3 Per Penyebab Kecelakaan Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Penyebab Kecelakaan","Jumlah", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Penyebab Kecelakaan",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikBatangPeristiwaK3PerPenyebabActionPerformed

    private void ppGrafikPiePeristiwaK3PerPenyebabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiePeristiwaK3PerPenyebabActionPerformed
        DefaultPieDataset dpd = new DefaultPieDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_penyebab.penyebab_kecelakaan,count(k3rs_peristiwa.kode_penyebab) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_penyebab on k3rs_penyebab.kode_penyebab=k3rs_peristiwa.kode_penyebab where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_penyebab").executeQuery();
            while(rs.next()) {
                dpd.setValue(rs.getString(1)+"("+rs.getString(2)+")",rs.getDouble(2));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } 
        
        JFreeChart freeChart = ChartFactory.createPieChart("Grafik Peristiwa K3 Per Penyebab Kecelakaan Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Penyebab Kecelakaan",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikPiePeristiwaK3PerPenyebabActionPerformed

    private void ppGrafikBatangPeristiwaK3PerJenisLukaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangPeristiwaK3PerJenisLukaActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_jenis_luka.jenis_luka,count(k3rs_peristiwa.kode_luka) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_jenis_luka on k3rs_jenis_luka.kode_luka=k3rs_peristiwa.kode_luka where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_luka").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        JFreeChart freeChart = ChartFactory.createBarChart("Grafik Peristiwa K3 Per Jenis Luka Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Jenis Luka","Jumlah", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Jenis Luka",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikBatangPeristiwaK3PerJenisLukaActionPerformed

    private void ppGrafikPiePeristiwaK3PerJenisLukaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiePeristiwaK3PerJenisLukaActionPerformed
        DefaultPieDataset dpd = new DefaultPieDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_jenis_luka.jenis_luka,count(k3rs_peristiwa.kode_luka) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_jenis_luka on k3rs_jenis_luka.kode_luka=k3rs_peristiwa.kode_luka where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_luka").executeQuery();
            while(rs.next()) {
                dpd.setValue(rs.getString(1)+"("+rs.getString(2)+")",rs.getDouble(2));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } 
        
        JFreeChart freeChart = ChartFactory.createPieChart("Grafik Peristiwa K3 Per Jenis Luka Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Jenis Luka",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikPiePeristiwaK3PerJenisLukaActionPerformed

    private void ppGrafikBatangPeristiwaK3PerLokasiKejadianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangPeristiwaK3PerLokasiKejadianActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_lokasi_kejadian.lokasi_kejadian,count(k3rs_peristiwa.kode_lokasi) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_lokasi_kejadian on k3rs_lokasi_kejadian.kode_lokasi=k3rs_peristiwa.kode_lokasi where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_lokasi").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        JFreeChart freeChart = ChartFactory.createBarChart("Grafik Peristiwa K3 Per Lokasi Kejadian Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Lokasi Kejadian","Jumlah", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Lokasi Kejadian",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikBatangPeristiwaK3PerLokasiKejadianActionPerformed

    private void ppGrafikPiePeristiwaK3PerLokasiKejadianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiePeristiwaK3PerLokasiKejadianActionPerformed
        DefaultPieDataset dpd = new DefaultPieDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_lokasi_kejadian.lokasi_kejadian,count(k3rs_peristiwa.kode_lokasi) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_lokasi_kejadian on k3rs_lokasi_kejadian.kode_lokasi=k3rs_peristiwa.kode_lokasi where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_lokasi").executeQuery();
            while(rs.next()) {
                dpd.setValue(rs.getString(1)+"("+rs.getString(2)+")",rs.getDouble(2));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } 
        
        JFreeChart freeChart = ChartFactory.createPieChart("Grafik Peristiwa K3 Per Lokasi Kejadian Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Lokasi Kejadian",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true); 
    }//GEN-LAST:event_ppGrafikPiePeristiwaK3PerLokasiKejadianActionPerformed

    private void ppGrafikBatangPeristiwaK3PerDampakCideraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangPeristiwaK3PerDampakCideraActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_dampak_cidera.dampak_cidera,count(k3rs_peristiwa.kode_dampak) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_dampak_cidera on k3rs_dampak_cidera.kode_dampak=k3rs_peristiwa.kode_dampak where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_dampak").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        JFreeChart freeChart = ChartFactory.createBarChart("Grafik Peristiwa K3 Per Dampak Cidera Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Dampak Cidera","Jumlah", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Dampak Cidera",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikBatangPeristiwaK3PerDampakCideraActionPerformed

    private void ppGrafikPiePeristiwaK3PerDampakCideraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiePeristiwaK3PerDampakCideraActionPerformed
        DefaultPieDataset dpd = new DefaultPieDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_dampak_cidera.dampak_cidera,count(k3rs_peristiwa.kode_dampak) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_dampak_cidera on k3rs_dampak_cidera.kode_dampak=k3rs_peristiwa.kode_dampak where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_dampak").executeQuery();
            while(rs.next()) {
                dpd.setValue(rs.getString(1)+"("+rs.getString(2)+")",rs.getDouble(2));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } 
        
        JFreeChart freeChart = ChartFactory.createPieChart("Grafik Peristiwa K3 Per Dampak Cidera Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Dampak Cidera",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikPiePeristiwaK3PerDampakCideraActionPerformed

    private void ppGrafikBatangPeristiwaK3PerJenisPekerjaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangPeristiwaK3PerJenisPekerjaanActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_jenis_pekerjaan.jenis_pekerjaan,count(k3rs_peristiwa.kode_pekerjaan) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_jenis_pekerjaan on k3rs_jenis_pekerjaan.kode_pekerjaan=k3rs_peristiwa.kode_pekerjaan where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_pekerjaan").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        JFreeChart freeChart = ChartFactory.createBarChart("Grafik Peristiwa K3 Per Jenis pekerjaan Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Jenis pekerjaan","Jumlah", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Jenis pekerjaan",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikBatangPeristiwaK3PerJenisPekerjaanActionPerformed

    private void ppGrafikPiePeristiwaK3PerJenisPekerjaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiePeristiwaK3PerJenisPekerjaanActionPerformed
        DefaultPieDataset dpd = new DefaultPieDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_jenis_pekerjaan.jenis_pekerjaan,count(k3rs_peristiwa.kode_pekerjaan) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_jenis_pekerjaan on k3rs_jenis_pekerjaan.kode_pekerjaan=k3rs_peristiwa.kode_pekerjaan where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_pekerjaan").executeQuery();
            while(rs.next()) {
                dpd.setValue(rs.getString(1)+"("+rs.getString(2)+")",rs.getDouble(2));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } 
        
        JFreeChart freeChart = ChartFactory.createPieChart("Grafik Peristiwa K3 Per Jenis pekerjaan Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Jenis pekerjaan",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikPiePeristiwaK3PerJenisPekerjaanActionPerformed

    private void ppGrafikBatangPeristiwaK3PerBagianTubuhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikBatangPeristiwaK3PerBagianTubuhActionPerformed
        DefaultCategoryDataset dcd = new DefaultCategoryDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_bagian_tubuh.bagian_tubuh,count(k3rs_peristiwa.kode_bagian) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_bagian_tubuh on k3rs_bagian_tubuh.kode_bagian=k3rs_peristiwa.kode_bagian where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_bagian").executeQuery();
            while(rs.next()) {
                dcd.setValue(rs.getDouble(2),rs.getString(1)+"("+rs.getString(2)+")",rs.getString(1));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        }
        JFreeChart freeChart = ChartFactory.createBarChart("Grafik Peristiwa K3 Per Bagian Tubuh Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),"Bagian Tubuh","Jumlah", dcd, PlotOrientation.VERTICAL,true, true,true); 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Bagian Tubuh",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikBatangPeristiwaK3PerBagianTubuhActionPerformed

    private void ppGrafikPiePeristiwaK3PerBagianTubuhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPiePeristiwaK3PerBagianTubuhActionPerformed
        DefaultPieDataset dpd = new DefaultPieDataset();
        try {                
            rs = koneksi.prepareStatement("select k3rs_bagian_tubuh.bagian_tubuh,count(k3rs_peristiwa.kode_bagian) as jumlah "+
                "from k3rs_peristiwa inner join k3rs_bagian_tubuh on k3rs_bagian_tubuh.kode_bagian=k3rs_peristiwa.kode_bagian where tgl_pelaporan between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by k3rs_peristiwa.kode_bagian").executeQuery();
            while(rs.next()) {
                dpd.setValue(rs.getString(1)+"("+rs.getString(2)+")",rs.getDouble(2));
            }
            
            if(rs!=null){
                rs.close();
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } 
        
        JFreeChart freeChart = ChartFactory.createPieChart("Grafik Peristiwa K3 Per Bagian Tubuh Periode "+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+" s.d. "+Valid.SetTgl(DTPCari2.getSelectedItem()+""),dpd,true,true, false); //String title,PieDatasheet datasheet,boolean legends,boolean tooltips,boolean url 
        ChartFrame cf = new ChartFrame("Grafik Peristiwa K3 Per Bagian Tubuh",freeChart);
        cf.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);   
        cf.setLocationRelativeTo(internalFrame1);
        cf.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        cf.setAlwaysOnTop(true);
        cf.setIconImage(new ImageIcon(super.getClass().getResource("/picture/addressbook-edit24.png")).getImage());
        cf.setVisible(true);  
    }//GEN-LAST:event_ppGrafikPiePeristiwaK3PerBagianTubuhActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            K3RSPeristiwa dialog = new K3RSPeristiwa(new javax.swing.JFrame(), true);
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
    private widget.ComboBox BarangBukti;
    private widget.TextBox Bidang;
    private widget.TextBox BidangPelapor;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDampak;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnJenisPekerjaan;
    private widget.Button BtnKeluar;
    private widget.Button BtnPenyebab;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextArea Catatan;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Departemen;
    private widget.TextBox DepartemenPelapor;
    private widget.ComboBox DetikInsiden;
    private widget.ComboBox DetikPelaporan;
    private widget.PanelBiasa FormInput;
    private widget.TextBox JK;
    private widget.TextBox Jabatan;
    private widget.ComboBox JamInsiden;
    private widget.ComboBox JamPelaporan;
    private widget.ComboBox JenisTindakan;
    private widget.ComboBox Kategori;
    private widget.TextBox KdBagianTubuh;
    private widget.TextBox KdDampak;
    private widget.TextBox KdJenisCidera;
    private widget.TextBox KdJenisLuka;
    private widget.TextBox KdJenisPekerjaan;
    private widget.TextBox KdLokasiKejadian;
    private widget.TextBox KdPenyebab;
    private widget.TextBox KondisiTidakAman;
    private widget.TextArea Kronologi;
    private widget.Label LCount;
    private widget.TextBox LT;
    private widget.ComboBox MenitInsiden;
    private widget.ComboBox MenitPelaporan;
    private widget.TextBox NIKK3;
    private widget.TextBox NIKKorban;
    private widget.TextBox NIKPelapor;
    private widget.TextBox NmBagianTubuh;
    private widget.TextBox NmDampak;
    private widget.TextBox NmJenisCidera;
    private widget.TextBox NmJenisLuka;
    private widget.TextBox NmJenisPekerjaan;
    private widget.TextBox NmKorban;
    private widget.TextBox NmLokasiKejadian;
    private widget.TextBox NmPelapor;
    private widget.TextBox NmPenyebab;
    private widget.TextBox NmPetugasK3;
    private widget.TextBox NoLaporan;
    private widget.TextBox Pekerjaan;
    private widget.TextBox Pribadi;
    private widget.TextArea Rencana;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.TextBox TCari;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal Target;
    private widget.Tanggal TglInsiden;
    private widget.Tanggal TglLahir;
    private widget.Tanggal TglPelaporan;
    private widget.TextBox TindakanTidakAman;
    private widget.TextBox Usia;
    private widget.TextBox Wewenang;
    private widget.Button btnBagianTubuh;
    private widget.Button btnJenisCidera;
    private widget.Button btnJenisLuka;
    private widget.Button btnKorban;
    private widget.Button btnLokasiKejadian;
    private widget.Button btnPelapor;
    private widget.Button btnTIMK3;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel16;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel19;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel25;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel5;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private javax.swing.JMenuItem ppGrafikBatangPeristiwaK3PerBagianTubuh;
    private javax.swing.JMenuItem ppGrafikBatangPeristiwaK3PerBulan;
    private javax.swing.JMenuItem ppGrafikBatangPeristiwaK3PerDampakCidera;
    private javax.swing.JMenuItem ppGrafikBatangPeristiwaK3PerJenisCidera;
    private javax.swing.JMenuItem ppGrafikBatangPeristiwaK3PerJenisLuka;
    private javax.swing.JMenuItem ppGrafikBatangPeristiwaK3PerJenisPekerjaan;
    private javax.swing.JMenuItem ppGrafikBatangPeristiwaK3PerLokasiKejadian;
    private javax.swing.JMenuItem ppGrafikBatangPeristiwaK3PerPenyebab;
    private javax.swing.JMenuItem ppGrafikBatangPeristiwaK3PerTahun;
    private javax.swing.JMenuItem ppGrafikBatangPeristiwaK3PerTanggal;
    private javax.swing.JMenuItem ppGrafikPiePeristiwaK3PerBagianTubuh;
    private javax.swing.JMenuItem ppGrafikPiePeristiwaK3PerBulan;
    private javax.swing.JMenuItem ppGrafikPiePeristiwaK3PerDampakCidera;
    private javax.swing.JMenuItem ppGrafikPiePeristiwaK3PerJenisCidera;
    private javax.swing.JMenuItem ppGrafikPiePeristiwaK3PerJenisLuka;
    private javax.swing.JMenuItem ppGrafikPiePeristiwaK3PerJenisPekerjaan;
    private javax.swing.JMenuItem ppGrafikPiePeristiwaK3PerLokasiKejadian;
    private javax.swing.JMenuItem ppGrafikPiePeristiwaK3PerPenyebab;
    private javax.swing.JMenuItem ppGrafikPiePeristiwaK3PerTahun;
    private javax.swing.JMenuItem ppGrafikPiePeristiwaK3PerTanggal;
    private widget.ScrollPane scrollPane1;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane3;
    private widget.Table tbJnsPerawatan;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);
        try{    
            ps=koneksi.prepareStatement(
                "select k3rs_peristiwa.no_k3rs,k3rs_peristiwa.tgl_insiden,k3rs_peristiwa.waktu_insiden,k3rs_peristiwa.kode_pekerjaan,k3rs_jenis_pekerjaan.jenis_pekerjaan,"+
                "k3rs_peristiwa.tgl_pelaporan,k3rs_peristiwa.waktu_pelaporan,k3rs_peristiwa.kode_lokasi,k3rs_lokasi_kejadian.lokasi_kejadian,k3rs_peristiwa.kronologi_kejadian,"+
                "k3rs_peristiwa.kode_penyebab,k3rs_penyebab.penyebab_kecelakaan,k3rs_peristiwa.nik,peg1.nama as korban,k3rs_peristiwa.kategori_cidera,k3rs_peristiwa.kode_cidera,"+
                "k3rs_jenis_cidera.jenis_cidera,k3rs_peristiwa.kode_luka,k3rs_jenis_luka.jenis_luka,k3rs_peristiwa.kode_bagian,k3rs_bagian_tubuh.bagian_tubuh,k3rs_peristiwa.lt,"+
                "k3rs_peristiwa.penyebab_langsung_kondisi,k3rs_peristiwa.penyebab_langsung_tindakan,k3rs_peristiwa.penyebab_tidak_langsung_pribadi,k3rs_peristiwa.penyebab_tidak_langsung_pekerjaan,"+
                "k3rs_peristiwa.barang_bukti,k3rs_peristiwa.kode_dampak,k3rs_dampak_cidera.dampak_cidera,k3rs_peristiwa.nik_pelapor,peg2.nama as pelapor,k3rs_peristiwa.perbaikan_jenis_tindakan,"+
                "k3rs_peristiwa.perbaikan_rencana_tindakan,k3rs_peristiwa.perbaikan_target,k3rs_peristiwa.perbaikan_wewenang,k3rs_peristiwa.nik_timk3,peg3.nama as timk3,"+
                "k3rs_peristiwa.catatan from k3rs_peristiwa inner join k3rs_jenis_pekerjaan inner join k3rs_bagian_tubuh inner join k3rs_dampak_cidera "+
                "inner join k3rs_jenis_cidera inner join k3rs_jenis_luka inner join k3rs_lokasi_kejadian inner join k3rs_penyebab inner join pegawai as peg1 "+
                "inner join pegawai as peg2 inner join pegawai as peg3 on k3rs_peristiwa.kode_pekerjaan=k3rs_jenis_pekerjaan.kode_pekerjaan and "+
                "k3rs_peristiwa.kode_lokasi=k3rs_lokasi_kejadian.kode_lokasi and k3rs_peristiwa.kode_penyebab=k3rs_penyebab.kode_penyebab and "+
                "k3rs_peristiwa.kode_cidera=k3rs_jenis_cidera.kode_cidera and k3rs_peristiwa.kode_luka=k3rs_jenis_luka.kode_luka and k3rs_peristiwa.kode_bagian=k3rs_bagian_tubuh.kode_bagian and "+
                "k3rs_peristiwa.kode_dampak=k3rs_dampak_cidera.kode_dampak and k3rs_peristiwa.nik=peg1.nik and k3rs_peristiwa.nik_pelapor=peg2.nik and k3rs_peristiwa.nik_timk3=peg3.nik "+
                "where k3rs_peristiwa.tgl_pelaporan between ? and ? and k3rs_peristiwa.no_k3rs like ? or "+
                "k3rs_peristiwa.tgl_pelaporan between ? and ? and k3rs_jenis_pekerjaan.jenis_pekerjaan like ? or "+
                "k3rs_peristiwa.tgl_pelaporan between ? and ? and k3rs_lokasi_kejadian.lokasi_kejadian like ? or "+
                "k3rs_peristiwa.tgl_pelaporan between ? and ? and k3rs_penyebab.penyebab_kecelakaan like ? or "+
                "k3rs_peristiwa.tgl_pelaporan between ? and ? and k3rs_jenis_cidera.jenis_cidera like ? or "+
                "k3rs_peristiwa.tgl_pelaporan between ? and ? and k3rs_jenis_luka.jenis_luka like ? or "+
                "k3rs_peristiwa.tgl_pelaporan between ? and ? and k3rs_bagian_tubuh.bagian_tubuh like ? or "+
                "k3rs_peristiwa.tgl_pelaporan between ? and ? and k3rs_dampak_cidera.dampak_cidera like ? or "+
                "k3rs_peristiwa.tgl_pelaporan between ? and ? and peg1.nama like ? or "+
                "k3rs_peristiwa.tgl_pelaporan between ? and ? and peg2.nama like ? or "+
                "k3rs_peristiwa.tgl_pelaporan between ? and ? and peg3.nama like ? or "+
                "k3rs_peristiwa.tgl_pelaporan between ? and ? and k3rs_peristiwa.kategori_cidera like ? or "+
                "k3rs_peristiwa.tgl_pelaporan between ? and ? and k3rs_peristiwa.perbaikan_jenis_tindakan like ? order by k3rs_peristiwa.tgl_pelaporan desc ");
            try{
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                ps.setString(28,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(29,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(30,"%"+TCari.getText().trim()+"%");
                ps.setString(31,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(32,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(33,"%"+TCari.getText().trim()+"%");
                ps.setString(34,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(35,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(36,"%"+TCari.getText().trim()+"%");
                ps.setString(37,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(38,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(39,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){   
                    tabMode.addRow(new Object[]{
                        rs.getString("no_k3rs"),rs.getString("tgl_insiden")+" "+rs.getString("waktu_insiden"),rs.getString("kode_pekerjaan"),rs.getString("jenis_pekerjaan"),
                        rs.getString("tgl_pelaporan")+" "+rs.getString("waktu_pelaporan"),rs.getString("kode_lokasi"),rs.getString("lokasi_kejadian"),rs.getString("kronologi_kejadian"),
                        rs.getString("kode_penyebab"),rs.getString("penyebab_kecelakaan"),rs.getString("nik"),rs.getString("korban"),rs.getString("kategori_cidera"),rs.getString("lt"),
                        rs.getString("kode_cidera"),rs.getString("jenis_cidera"),rs.getString("kode_luka"),rs.getString("jenis_luka"),rs.getString("kode_bagian"),rs.getString("bagian_tubuh"),
                        rs.getString("penyebab_langsung_kondisi"),rs.getString("penyebab_langsung_tindakan"),rs.getString("penyebab_tidak_langsung_pribadi"),rs.getString("penyebab_tidak_langsung_pekerjaan"),
                        rs.getString("kode_dampak"),rs.getString("dampak_cidera"),rs.getString("nik_pelapor"),rs.getString("pelapor"),rs.getString("barang_bukti"),rs.getString("perbaikan_jenis_tindakan"),
                        rs.getString("perbaikan_rencana_tindakan"),rs.getString("perbaikan_target"),rs.getString("perbaikan_wewenang"),rs.getString("catatan"),rs.getString("nik_timk3"),
                        rs.getString("timk3")
                    });
                }  
            } catch(Exception e){
                System.out.println(e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        autoNomor();
        KdJenisPekerjaan.setText("");
        NmJenisPekerjaan.setText("");
        KdLokasiKejadian.setText("");
        NmLokasiKejadian.setText("");
        TglInsiden.setDate(new Date());
        TglPelaporan.setDate(new Date());
        TglLahir.setDate(new Date());
        Kronologi.setText("");
        KdPenyebab.setText("");
        NmPenyebab.setText("");
        NIKKorban.setText("");
        NmKorban.setText("");
        Bidang.setText("");
        Departemen.setText("");
        JK.setText("");
        Usia.setText("");
        Jabatan.setText("");
        LT.setText("0");
        KdJenisCidera.setText("");
        NmJenisCidera.setText("");
        KdJenisLuka.setText("");
        NmJenisLuka.setText("");
        KdBagianTubuh.setText("");
        NmBagianTubuh.setText("");
        KondisiTidakAman.setText("");
        TindakanTidakAman.setText("");
        Pribadi.setText("");
        Pekerjaan.setText("");
        KdDampak.setText("");
        NmDampak.setText("");
        NIKPelapor.setText("");
        NmPelapor.setText("");
        BidangPelapor.setText("");
        DepartemenPelapor.setText("");
        BarangBukti.setSelectedIndex(0);
        JenisTindakan.setSelectedItem(0);
        Rencana.setText("");
        Wewenang.setText("");
        Catatan.setText("");
        NoLaporan.requestFocus();
    }
    
    public void autoNomor(){
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_k3rs,4),signed)),0) from k3rs_peristiwa where tgl_pelaporan='"+Valid.SetTgl(TglPelaporan.getSelectedItem()+"")+"' ",
                "K3/"+TglPelaporan.getSelectedItem().toString().substring(6,10)+"/"+TglPelaporan.getSelectedItem().toString().substring(3,5)+"/"+TglPelaporan.getSelectedItem().toString().substring(0,2)+"/",4,NoLaporan); 
    }

    private void getData() {
        if(tbJnsPerawatan.getSelectedRow()!= -1){
            NoLaporan.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),0).toString());
            Valid.SetTgl(TglInsiden,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString());
            JamInsiden.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString().substring(11,13));
            MenitInsiden.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString().substring(14,16));
            DetikInsiden.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),1).toString().substring(17,19));
            KdJenisPekerjaan.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),2).toString());
            NmJenisPekerjaan.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),3).toString());
            Valid.SetTgl(TglPelaporan,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString());
            JamPelaporan.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString().substring(11,13));
            MenitPelaporan.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString().substring(14,16));
            DetikPelaporan.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),4).toString().substring(17,19));
            KdLokasiKejadian.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),5).toString());
            NmLokasiKejadian.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),6).toString());
            Kronologi.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),7).toString());
            KdPenyebab.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),8).toString());
            NmPenyebab.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),9).toString());
            NIKKorban.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),10).toString());
            NmKorban.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),11).toString());
            Kategori.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),12).toString());
            LT.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),13).toString());
            KdJenisCidera.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),14).toString());
            NmJenisCidera.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),15).toString());
            KdJenisLuka.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),16).toString());
            NmJenisLuka.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),17).toString());
            KdBagianTubuh.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),18).toString());
            NmBagianTubuh.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),19).toString());
            KondisiTidakAman.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),20).toString());
            TindakanTidakAman.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),21).toString());
            Pribadi.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),22).toString());
            Pekerjaan.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),23).toString());
            KdDampak.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),24).toString());
            NmDampak.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),25).toString());
            NIKPelapor.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),26).toString());
            NmPelapor.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),27).toString());
            BarangBukti.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),28).toString());
            JenisTindakan.setSelectedItem(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),29).toString());
            Rencana.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),30).toString());
            Valid.SetTgl(Target,tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),31).toString());
            Wewenang.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),32).toString());
            Catatan.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),33).toString());
            NIKK3.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),34).toString());
            NmPetugasK3.setText(tbJnsPerawatan.getValueAt(tbJnsPerawatan.getSelectedRow(),35).toString());
            try {
                ps=koneksi.prepareStatement(
                    "select jk,jbtn,departemen,bidang,tgl_lahir from pegawai where nik=?");
                try {
                    ps.setString(1,NIKKorban.getText());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        Bidang.setText(rs.getString("bidang"));
                        Departemen.setText(rs.getString("departemen"));
                        JK.setText(rs.getString("jk"));
                        Jabatan.setText(rs.getString("jbtn"));
                        TglLahir.setDate(rs.getDate("tgl_lahir"));
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
                
                ps=koneksi.prepareStatement(
                    "select departemen,bidang from pegawai where nik=?");
                try {
                    ps.setString(1,NIKPelapor.getText());
                    rs=ps.executeQuery();
                    if(rs.next()){
                        BidangPelapor.setText(rs.getString("bidang"));
                        DepartemenPelapor.setText(rs.getString("departemen"));
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
    
    public void isCek(){
        TabRawat.setSelectedIndex(1);
        tampil();
        if(akses.getjml2()>=1){
            NIKK3.setEditable(false);
            btnTIMK3.setEnabled(false);
            NIKK3.setText(akses.getkode());
            Sequel.cariIsi("select nama from petugas where nip=?", NmPetugasK3,NIKK3.getText());
        }   
        BtnSimpan.setEnabled(akses.getperistiwa_k3rs());
        BtnHapus.setEnabled(akses.getperistiwa_k3rs());
        BtnEdit.setEnabled(akses.getperistiwa_k3rs());
        BtnPrint.setEnabled(akses.getperistiwa_k3rs());
    }
    
    

    
}
