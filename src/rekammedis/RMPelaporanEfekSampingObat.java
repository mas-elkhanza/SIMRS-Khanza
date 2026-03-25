//Kontribusi dari muannas, RSAD Palomonia Makasar
package rekammedis;

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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import kepegawaian.DlgCariPegawai;
import simrskhanza.DlgCariBangsal;



/**
 *
 * @author rsad pelamonia
 */
public final class RMPelaporanEfekSampingObat extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private Connection koneksi=koneksiDB.condb();
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private PreparedStatement ps;
    private ResultSet rs;
    private int i=0;
    public  DlgCariBangsal bangsal=new DlgCariBangsal(null,false);
    public  DlgCariPegawai pegawai=new DlgCariPegawai(null,false);
    private RMCariJumlahObatMeso cariobatdosis=new RMCariJumlahObatMeso(null,false);
    private StringBuilder htmlContent;
    private String finger="";
    
    /** Creates new form DlgRujuk
     * @param parent
     * @param modal */
    public RMPelaporanEfekSampingObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        
        tabMode=new DefaultTableModel(null,new Object[]{
            "No.Rawat","No.RM","Nama Pasien","Tgl.Lahir","J.K.","Umur","Pekerjaan","Suku","No Laporan","Tanggal Laporan","Profesi Pelapor","Kode Pelapor","Nama Pelapor","Kode Ruangan","Nama Ruangan","BB(Kg)",
            "Kehamilan Passien","Kesudahan","Penyakit Lain","Penyakit Utama","Tanggal Mulai Terjadi","Bentuk Manifestasi E.S.O yang terjadi ","Masalah Pada Mutu/ Kualitas Produk Obat","Riwayat E.S.O yang pernah dialami ","Tanggal Kesudahan ESO","Kesudahan ESO","Obat 1","Sedian 1","Jkn 1","Batch 1","Cara 1","Dosis 1","Tanggal Mulai 1","Tanggal Akhir 1 ","Indikasi 1",
            "Obat 2","Sedian 2","Jkn 2","Batch 2","Cara 2","Dosis 2","Tanggal Mulai 2","Tanggal Akhir 2 ","Indikasi 2","Obat 3","Sedian 3","Jkn 3","Batch 3","Cara 3","Dosis 3","Tanggal Mulai 3","Tanggal Akhir 3 ","Indikasi 3","Obat 4","Sedian 4","Jkn 4","Batch 4","Cara 4","Dosis 4","Tanggal Mulai 4","Tanggal Akhir 4 ","Indikasi 4",
            "Obat 5","Sedian 5","Jkn 5","Batch 5","Cara 5","Dosis 5","Tanggal Mulai 5","Tanggal Akhir 5 ","Indikasi 5","Obat 6","Sedian 6","Jkn 6","Batch 6","Cara 6","Dosis 6","Tanggal Mulai 6","Tanggal Akhir 6 ","Indikasi 6","Obat 7","Sedian 7","Jkn 7","Batch 7","Cara 7","Dosis 7","Tanggal Mulai 7","Tanggal Akhir 7 ","Indikasi 7",
            "Obat 8","Sedian 8","Jkn 8","Batch 8","Cara 8","Dosis 8","Tanggal Mulai 8","Tanggal Akhir 8 ","Indikasi 8","Obat 9","Sedian 9","Jkn 9","Batch 9","Cara 9","Dosis 9","Tanggal Mulai 9","Tanggal Akhir 9 ","Indikasi 9","Obat 10","Sedian 10","Jkn 10","Batch 10","Cara 10","Dosis 10","Tanggal Mulai 10","Tanggal Akhir 10 ","Indikasi 10",
            "Apakah reaksi E.S.O hilang setelah obat diberikan ?","Apakah reaksi E.S.O yang sama timbul sewaktu obat yang dicurigai digunakan kembali ?","Apakah ada laporan efek samping obat yang serupa ?","Skor1","Apakah efek samping obat terjadi setelah pembersihan obat yang dicurigai ?","Skor 2","Apakah efek samping obat membaik setelah obat diberhentikan atau obat antagonis khusus diberikan ?","Skor 3",
            "Apakah efek samping obat terjadi berulang kali setelah obat diberikan kembali ?","Skor 4","Apakah ada alternative lain penyebab yang dapat menjelaskan kemungkinan efek samping obat ?","Skor 5","Apakah efek samping obat muncul kembali ketika placebo diberikan ?","Skor 6","Apakah Obat yang dicurigai terdeteksi didalam darah atau cairan tubuh lainnya dengan konsentrasi yang toksik ?","Skor 7",
            "Apakah efek samping obat bertambah parah ketika dosis obat ditingkatkan atau bertambah ringan ketika obat diturunkan dosisnya ?","Skor 8","Apakah pasien pernah mengalami efek samping obat yang sama atau dengan obat yang mirip sebelumnya ?","Skor 9","Apakah efek samping obat dapat dikonfirmasi dengan bukti yang obyektif ?","Skor 10","Total Score","Hasil Kategori"
        }){
              @Override public boolean isCellEditable(int rowIndex, int colIndex){return false;}
        };
        
        tbObat.setModel(tabMode);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 138; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(105);
            }else if(i==1){
                column.setPreferredWidth(70);
            }else if(i==2){
                column.setPreferredWidth(150);
            }else if(i==3){
                column.setPreferredWidth(65);
            }else if(i==4){
                column.setPreferredWidth(50);
            }else if(i==5){
                column.setPreferredWidth(80);
            }else if(i==6){
                column.setPreferredWidth(150);
            }else if(i==7){
                column.setPreferredWidth(115);
            }else if(i==8){
                column.setPreferredWidth(150);
            }else if(i==9){
                column.setPreferredWidth(100);
            }else if(i==10){
                column.setPreferredWidth(100);
            }else if(i==11){
                column.setPreferredWidth(150);
            }else if(i==12){
                column.setPreferredWidth(100);
            }else if(i==13){
                column.setPreferredWidth(150);
            }else if(i==14){
                column.setPreferredWidth(100);
            }else if(i==15){
                column.setPreferredWidth(30);
            }else if(i==16){
                column.setPreferredWidth(100);
            }else if(i==17){
                column.setPreferredWidth(100);
            }else if(i==18){
                column.setPreferredWidth(100);
            }else if(i==19){
                column.setPreferredWidth(100);
            }else if(i==20){
                column.setPreferredWidth(100);
            }else if(i==21){
                column.setPreferredWidth(100);
            }else if(i==22){
                column.setPreferredWidth(300);
            }else if(i==23){
                column.setPreferredWidth(300);
            }else if(i==24){
                column.setPreferredWidth(100);
            }else if(i==25){
                column.setPreferredWidth(100);
            }else if(i==26){ 
                column.setPreferredWidth(80);
            }else if(i==27){
                column.setPreferredWidth(80);
            }else if(i==28){
                column.setPreferredWidth(80);
            }else if(i==29){
                column.setPreferredWidth(80);
            }else if(i==30){
                column.setPreferredWidth(80);
            }else if(i==31){
                column.setPreferredWidth(80);
            }else if(i==32){
                column.setPreferredWidth(80);
            }else if(i==33){
                column.setPreferredWidth(80);
            }else if(i==34){
                column.setPreferredWidth(80);
            }else if(i==35){
                column.setPreferredWidth(80);
            }else if(i==36){
                column.setPreferredWidth(80);
            }else if(i==37){
                column.setPreferredWidth(80);
            }else if(i==38){
                column.setPreferredWidth(80);
            }else if(i==39){
                column.setPreferredWidth(80);
            }else if(i==40){
                column.setPreferredWidth(80);
            }else if(i==41){
                column.setPreferredWidth(80);
            }else if(i==42){
                column.setPreferredWidth(80);
            }else if(i==43){
                column.setPreferredWidth(80);
            }else if(i==44){
                column.setPreferredWidth(80);
            }else if(i==45){
                column.setPreferredWidth(80);
            }else if(i==46){
                column.setPreferredWidth(80);
            }else if(i==47){
                column.setPreferredWidth(80);
            }else if(i==48){
                column.setPreferredWidth(80);
            }else if(i==49){
                column.setPreferredWidth(80);
            }else if(i==50){
                column.setPreferredWidth(80);
            }else if(i==51){
                column.setPreferredWidth(80);
            }else if(i==52){
                column.setPreferredWidth(80);
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
                column.setPreferredWidth(80);
            }else if(i==60){
                column.setPreferredWidth(80);
            }else if(i==61){
                column.setPreferredWidth(80);
            }else if(i==62){
                column.setPreferredWidth(80);
            }else if(i==63){
                column.setPreferredWidth(80);
            }else if(i==64){
                column.setPreferredWidth(80);
            }else if(i==65){
                column.setPreferredWidth(80);
            }else if(i==66){
                column.setPreferredWidth(80);
            }else if(i==67){
                column.setPreferredWidth(80);
            }else if(i==68){
                column.setPreferredWidth(80);
            }else if(i==69){
                column.setPreferredWidth(80);
            }else if(i==70){
                column.setPreferredWidth(80);
            }else if(i==71){
                column.setPreferredWidth(80);
            }else if(i==72){
                column.setPreferredWidth(80);
            }else if(i==73){
                column.setPreferredWidth(80);
            }else if(i==74){
                column.setPreferredWidth(80);
            }else if(i==75){
                column.setPreferredWidth(80);
            }else if(i==76){
                column.setPreferredWidth(80);
            }else if(i==77){
                column.setPreferredWidth(80);
            }else if(i==78){
                column.setPreferredWidth(80);
            }else if(i==79){
                column.setPreferredWidth(80);
            }else if(i==80){
                column.setPreferredWidth(80);
            }else if(i==81){
                column.setPreferredWidth(80);
            }else if(i==82){
                column.setPreferredWidth(80);
            }else if(i==83){
                column.setPreferredWidth(80);
            }else if(i==84){
                column.setPreferredWidth(80);
            }else if(i==85){
                column.setPreferredWidth(80);
            }else if(i==86){
                column.setPreferredWidth(80);
            }else if(i==87){
                column.setPreferredWidth(80);
            }else if(i==88){
                column.setPreferredWidth(80);
            }else if(i==89){
                column.setPreferredWidth(80);
            }else if(i==90){
                column.setPreferredWidth(80);
            }else if(i==91){
                column.setPreferredWidth(80);
            }else if(i==92){
                column.setPreferredWidth(80);
            }else if(i==93){
                column.setPreferredWidth(80);
            }else if(i==94){
                column.setPreferredWidth(80);
            }else if(i==95){
                column.setPreferredWidth(80);
            }else if(i==96){
                column.setPreferredWidth(80);
            }else if(i==97){
                column.setPreferredWidth(80);
            }else if(i==98){
                column.setPreferredWidth(80);
            }else if(i==99){
                column.setPreferredWidth(80);
            }else if(i==100){
                column.setPreferredWidth(80);
            }else if(i==101){
                column.setPreferredWidth(80);
            }else if(i==102){
                column.setPreferredWidth(80);
            }else if(i==103){
                column.setPreferredWidth(80);
            }else if(i==104){
                column.setPreferredWidth(80);
            }else if(i==105){
                column.setPreferredWidth(80);
            }else if(i==106){
                column.setPreferredWidth(80);
            }else if(i==107){
                column.setPreferredWidth(80);
            }else if(i==108){
                column.setPreferredWidth(80);
            }else if(i==109){
                column.setPreferredWidth(80);
            }else if(i==110){
                column.setPreferredWidth(80);
            }else if(i==111){
                column.setPreferredWidth(80);
            }else if(i==112){
                column.setPreferredWidth(80);
            }else if(i==113){
                column.setPreferredWidth(80);
            }else if(i==114){
                column.setPreferredWidth(100);
            }else if(i==115){
                column.setPreferredWidth(100);
            }else if(i==116){
                column.setPreferredWidth(300);
            }else if(i==117){
                column.setPreferredWidth(40);
            }else if(i==118){
                column.setPreferredWidth(300);
            }else if(i==119){
                column.setPreferredWidth(40);
            }else if(i==120){
                column.setPreferredWidth(300);
            }else if(i==121){
                column.setPreferredWidth(40);
            }else if(i==122){
                column.setPreferredWidth(300);
            }else if(i==123){
                column.setPreferredWidth(40);
            }else if(i==124){
                column.setPreferredWidth(300);
            }else if(i==125){
                column.setPreferredWidth(40);
            }else if(i==126){
                column.setPreferredWidth(300);
            }else if(i==127){
                column.setPreferredWidth(40);
            }else if(i==128){
                column.setPreferredWidth(300);
            }else if(i==129){
                column.setPreferredWidth(40);
            }else if(i==130){
                column.setPreferredWidth(300);
            }else if(i==131){
                column.setPreferredWidth(40);
            }else if(i==132){
                column.setPreferredWidth(300);
            }else if(i==133){
                column.setPreferredWidth(80);
            }else if(i==134){
                column.setPreferredWidth(300);
            }else if(i==135){
                column.setPreferredWidth(80);
            }else if(i==136){
                column.setPreferredWidth(80);
            }else if(i==137){
                column.setPreferredWidth(80);
            }
        }
        tbObat.setDefaultRenderer(Object.class, new WarnaTable());
        
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        BB.setDocument(new batasInput((byte)5).getKata(BB));
        Pekerjaan.setDocument(new batasInput((int)30).getKata(Pekerjaan));
        Penyakit_Utama.setDocument(new batasInput((int)2000).getKata(Penyakit_Utama));
        Bentuk_Manifestasi.setDocument(new batasInput((int)2000).getKata(Bentuk_Manifestasi));
        Masalah_Kualitas.setDocument(new batasInput((int)1000).getKata(Masalah_Kualitas));
        Riwayat_eso.setDocument(new batasInput((int)1000).getKata(Riwayat_eso));
        Penyakit_Utama.setDocument(new batasInput((int)5000).getKata(Penyakit_Utama));
        TCari.setDocument(new batasInput((int)100).getKata(TCari));
        
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
        
        pegawai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(pegawai.getTable().getSelectedRow()!= -1){                   
                    KdPegawai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),0).toString());
                    NmPegawai.setText(pegawai.getTable().getValueAt(pegawai.getTable().getSelectedRow(),1).toString());
                }            
                KdPegawai.requestFocus();
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
        
        bangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(bangsal.getTable().getSelectedRow()!= -1){                   
                    kdRuangan.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),0).toString());                    
                    nmruangan.setText(bangsal.getTable().getValueAt(bangsal.getTable().getSelectedRow(),1).toString());
                }  
                kdRuangan.requestFocus();
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
        
        bangsal.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    bangsal.dispose();
                }                
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        cariobatdosis.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(cariobatdosis.getTable().getSelectedRow()!= -1){ 
                    if(i==1){
                        Obat1.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis1.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());
                        Sedian1.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),4).toString());
                    }else if(i==2){
                        Obat2.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis2.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());
                        Sedian2.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),4).toString());
                    }else if(i==3){
                        Obat3.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis3.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());
                        Sedian3.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),4).toString());
                    }
                    else if(i==4){
                        Obat4.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis4.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());
                        Sedian4.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),4).toString());
                    }
                    else if(i==5){
                        Obat5.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis5.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());
                        Sedian5.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),4).toString());
                    }
                    else if(i==6){
                        Obat6.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis6.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString()); 
                        Sedian6.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),4).toString());
                    }
                    else if(i==7){
                        Obat7.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis7.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString());  
                        Sedian7.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),4).toString());
                    }
                    else if(i==8){
                        Obat8.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis8.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString()); 
                        Sedian8.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),4).toString());
                    }
                    else if(i==9){
                        Obat9.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis9.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString()); 
                        Sedian9.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),4).toString());
                    }
                    else if(i==10){
                        Obat10.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),2).toString());
                        Dosis10.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),3).toString()); 
                        Sedian10.setText(cariobatdosis.getTable().getValueAt(cariobatdosis.getTable().getSelectedRow(),4).toString());
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
        
        HTMLEditorKit kit = new HTMLEditorKit();
        LoadHTML.setEditable(true);
        LoadHTML.setEditorKit(kit);
        StyleSheet styleSheet = kit.getStyleSheet();
        styleSheet.addRule(
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
        Document doc = kit.createDefaultDocument();
        LoadHTML.setDocument(doc);
    }


    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        LoadHTML = new widget.editorpane();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnMonitoringEso = new javax.swing.JMenuItem();
        TanggalRegistrasi = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelGlass8 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        BtnKeluar = new widget.Button();
        TabRawat = new javax.swing.JTabbedPane();
        internalFrame2 = new widget.InternalFrame();
        scrollInput = new widget.ScrollPane();
        FormInput = new widget.PanelBiasa();
        TNoRw = new widget.TextBox();
        TPasien = new widget.TextBox();
        TNoRM = new widget.TextBox();
        KdPegawai = new widget.TextBox();
        NmPegawai = new widget.TextBox();
        jLabel8 = new widget.Label();
        TglLahir = new widget.TextBox();
        jLabel9 = new widget.Label();
        Jk = new widget.TextBox();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        BB = new widget.TextBox();
        TanngalMulaiTerjadi = new widget.Tanggal();
        jLabel13 = new widget.Label();
        jLabel30 = new widget.Label();
        scrollPane2 = new widget.ScrollPane();
        Riwayat_eso = new widget.TextArea();
        jLabel31 = new widget.Label();
        jLabel32 = new widget.Label();
        scrollPane4 = new widget.ScrollPane();
        Masalah_Kualitas = new widget.TextArea();
        scrollPane5 = new widget.ScrollPane();
        Penyakit_Utama = new widget.TextArea();
        jLabel94 = new widget.Label();
        jLabel38 = new widget.Label();
        Pekerjaan = new widget.TextBox();
        jLabel33 = new widget.Label();
        scrollPane7 = new widget.ScrollPane();
        Bentuk_Manifestasi = new widget.TextArea();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel39 = new widget.Label();
        Kesudahan = new widget.ComboBox();
        jLabel40 = new widget.Label();
        Hamil = new widget.ComboBox();
        jLabel41 = new widget.Label();
        PenyakitLain = new widget.ComboBox();
        jLabel49 = new widget.Label();
        HasilKesudahan = new widget.ComboBox();
        ReaksiSama = new widget.ComboBox();
        Jkn10 = new widget.ComboBox();
        jLabel99 = new widget.Label();
        label11 = new widget.Label();
        jLabel42 = new widget.Label();
        ReaksiSetelah = new widget.ComboBox();
        TanggalMulai10 = new widget.Tanggal();
        jSeparator19 = new javax.swing.JSeparator();
        jLabel48 = new widget.Label();
        jLabel53 = new widget.Label();
        jLabel47 = new widget.Label();
        Obat1 = new widget.TextBox();
        Dosis1 = new widget.TextBox();
        BtnObat1 = new widget.Button();
        jLabel54 = new widget.Label();
        Obat2 = new widget.TextBox();
        Dosis2 = new widget.TextBox();
        BtnObat2 = new widget.Button();
        jLabel55 = new widget.Label();
        Obat3 = new widget.TextBox();
        Dosis3 = new widget.TextBox();
        BtnObat3 = new widget.Button();
        jLabel56 = new widget.Label();
        Obat4 = new widget.TextBox();
        Dosis4 = new widget.TextBox();
        BtnObat4 = new widget.Button();
        jLabel57 = new widget.Label();
        Obat5 = new widget.TextBox();
        Dosis5 = new widget.TextBox();
        BtnObat5 = new widget.Button();
        jLabel58 = new widget.Label();
        Obat6 = new widget.TextBox();
        Dosis6 = new widget.TextBox();
        BtnObat6 = new widget.Button();
        jLabel59 = new widget.Label();
        Obat7 = new widget.TextBox();
        Dosis7 = new widget.TextBox();
        BtnObat7 = new widget.Button();
        jLabel60 = new widget.Label();
        Obat8 = new widget.TextBox();
        Dosis8 = new widget.TextBox();
        BtnObat8 = new widget.Button();
        jLabel61 = new widget.Label();
        Obat9 = new widget.TextBox();
        Dosis9 = new widget.TextBox();
        BtnObat9 = new widget.Button();
        jLabel62 = new widget.Label();
        Obat10 = new widget.TextBox();
        Dosis10 = new widget.TextBox();
        BtnObat10 = new widget.Button();
        Sedian1 = new widget.TextBox();
        Sedian2 = new widget.TextBox();
        Sedian3 = new widget.TextBox();
        Sedian4 = new widget.TextBox();
        Sedian5 = new widget.TextBox();
        Sedian6 = new widget.TextBox();
        Sedian7 = new widget.TextBox();
        Sedian8 = new widget.TextBox();
        Sedian9 = new widget.TextBox();
        Sedian10 = new widget.TextBox();
        Batch4 = new widget.TextBox();
        Batch5 = new widget.TextBox();
        Batch2 = new widget.TextBox();
        Batch10 = new widget.TextBox();
        Batch6 = new widget.TextBox();
        Batch3 = new widget.TextBox();
        Batch8 = new widget.TextBox();
        Batch7 = new widget.TextBox();
        Batch9 = new widget.TextBox();
        Batch1 = new widget.TextBox();
        jLabel63 = new widget.Label();
        jLabel64 = new widget.Label();
        jLabel65 = new widget.Label();
        Cara4 = new widget.TextBox();
        Cara5 = new widget.TextBox();
        Cara2 = new widget.TextBox();
        Cara10 = new widget.TextBox();
        Cara6 = new widget.TextBox();
        Cara3 = new widget.TextBox();
        Cara8 = new widget.TextBox();
        Cara7 = new widget.TextBox();
        Cara9 = new widget.TextBox();
        Cara1 = new widget.TextBox();
        TanggaKesudahan = new widget.Tanggal();
        TanggalMulai1 = new widget.Tanggal();
        TanggalMulai2 = new widget.Tanggal();
        TanggalMulai3 = new widget.Tanggal();
        TanggalMulai4 = new widget.Tanggal();
        TanggalMulai5 = new widget.Tanggal();
        TanggalMulai6 = new widget.Tanggal();
        TanggalMulai7 = new widget.Tanggal();
        TanggalMulai8 = new widget.Tanggal();
        TanggalMulai9 = new widget.Tanggal();
        jLabel66 = new widget.Label();
        TanggalAkhir10 = new widget.Tanggal();
        jLabel67 = new widget.Label();
        TanggalAkhir1 = new widget.Tanggal();
        TanggalAkhir2 = new widget.Tanggal();
        TanggalAkhir3 = new widget.Tanggal();
        TanggalAkhir4 = new widget.Tanggal();
        TanggalAkhir5 = new widget.Tanggal();
        TanggalAkhir6 = new widget.Tanggal();
        TanggalAkhir7 = new widget.Tanggal();
        TanggalAkhir8 = new widget.Tanggal();
        TanggalAkhir9 = new widget.Tanggal();
        Indikasi1 = new widget.TextBox();
        Indikasi2 = new widget.TextBox();
        Indikasi3 = new widget.TextBox();
        Indikasi4 = new widget.TextBox();
        Indikasi5 = new widget.TextBox();
        Indikasi6 = new widget.TextBox();
        Indikasi7 = new widget.TextBox();
        Indikasi8 = new widget.TextBox();
        Indikasi9 = new widget.TextBox();
        Indikasi10 = new widget.TextBox();
        jLabel68 = new widget.Label();
        jSeparator20 = new javax.swing.JSeparator();
        jLabel43 = new widget.Label();
        jLabel243 = new widget.Label();
        Naranjo1 = new widget.ComboBox();
        NilaiNaranjo1 = new widget.TextBox();
        jLabel244 = new widget.Label();
        Naranjo2 = new widget.ComboBox();
        NilaiNaranjo2 = new widget.TextBox();
        jLabel245 = new widget.Label();
        Naranjo3 = new widget.ComboBox();
        NilaiNaranjo3 = new widget.TextBox();
        jLabel246 = new widget.Label();
        Naranjo4 = new widget.ComboBox();
        NilaiNaranjo4 = new widget.TextBox();
        jLabel247 = new widget.Label();
        Naranjo5 = new widget.ComboBox();
        NilaiNaranjo5 = new widget.TextBox();
        jLabel248 = new widget.Label();
        Naranjo6 = new widget.ComboBox();
        NilaiNaranjo6 = new widget.TextBox();
        jLabel249 = new widget.Label();
        Naranjo7 = new widget.ComboBox();
        NilaiNaranjo7 = new widget.TextBox();
        TingkatResiko1 = new widget.Label();
        KategoriNaranjo = new widget.TextBox();
        jLabel256 = new widget.Label();
        NilaiNaranjoTotal = new widget.TextBox();
        jLabel250 = new widget.Label();
        Naranjo8 = new widget.ComboBox();
        NilaiNaranjo9 = new widget.TextBox();
        Naranjo9 = new widget.ComboBox();
        jLabel251 = new widget.Label();
        NilaiNaranjo8 = new widget.TextBox();
        Naranjo10 = new widget.ComboBox();
        jLabel252 = new widget.Label();
        NilaiNaranjo10 = new widget.TextBox();
        jLabel253 = new widget.Label();
        jLabel69 = new widget.Label();
        Profesi = new widget.ComboBox();
        label16 = new widget.Label();
        kdRuangan = new widget.TextBox();
        nmruangan = new widget.TextBox();
        btnRuangan = new widget.Button();
        btnPegawai = new widget.Button();
        jLabel14 = new widget.Label();
        Suku = new widget.TextBox();
        jLabel27 = new widget.Label();
        Umur = new widget.TextBox();
        Jkn1 = new widget.ComboBox();
        Jkn2 = new widget.ComboBox();
        Jkn3 = new widget.ComboBox();
        Jkn4 = new widget.ComboBox();
        Jkn5 = new widget.ComboBox();
        Jkn6 = new widget.ComboBox();
        Jkn7 = new widget.ComboBox();
        Jkn8 = new widget.ComboBox();
        Jkn9 = new widget.ComboBox();
        jLabel70 = new widget.Label();
        jLabel15 = new widget.Label();
        NoSurat = new widget.TextBox();
        TglLaporan = new widget.Tanggal();
        internalFrame3 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        panelGlass9 = new widget.panelisi();
        jLabel19 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel21 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        jLabel7 = new widget.Label();
        LCount = new widget.Label();

        LoadHTML.setBorder(null);
        LoadHTML.setName("LoadHTML"); // NOI18N

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnMonitoringEso.setBackground(new java.awt.Color(255, 255, 254));
        MnMonitoringEso.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnMonitoringEso.setForeground(new java.awt.Color(50, 50, 50));
        MnMonitoringEso.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        MnMonitoringEso.setText("Laporan Monitoring Efek Samping Obat");
        MnMonitoringEso.setName("MnMonitoringEso"); // NOI18N
        MnMonitoringEso.setPreferredSize(new java.awt.Dimension(220, 26));
        MnMonitoringEso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnMonitoringEsoActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnMonitoringEso);

        TanggalRegistrasi.setHighlighter(null);
        TanggalRegistrasi.setName("TanggalRegistrasi"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Monitoring Efek Samping Obat M.E.S.O ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass8.setName("panelGlass8"); // NOI18N
        panelGlass8.setPreferredSize(new java.awt.Dimension(44, 54));
        panelGlass8.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setGlassColor(new java.awt.Color(255, 255, 255));
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
        BtnBatal.setGlassColor(new java.awt.Color(255, 255, 255));
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
        BtnHapus.setGlassColor(new java.awt.Color(255, 255, 255));
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
        BtnEdit.setGlassColor(new java.awt.Color(255, 255, 255));
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
        BtnPrint.setGlassColor(new java.awt.Color(255, 255, 255));
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
        BtnAll.setGlassColor(new java.awt.Color(255, 255, 255));
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
        BtnKeluar.setGlassColor(new java.awt.Color(255, 255, 255));
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

        internalFrame1.add(panelGlass8, java.awt.BorderLayout.PAGE_END);

        TabRawat.setBackground(new java.awt.Color(254, 255, 254));
        TabRawat.setForeground(new java.awt.Color(50, 50, 50));
        TabRawat.setName("TabRawat"); // NOI18N

        internalFrame2.setBorder(null);
        internalFrame2.setName("internalFrame2"); // NOI18N
        internalFrame2.setLayout(new java.awt.BorderLayout(1, 1));

        scrollInput.setName("scrollInput"); // NOI18N
        scrollInput.setPreferredSize(new java.awt.Dimension(102, 557));

        FormInput.setBackground(new java.awt.Color(255, 255, 255));
        FormInput.setBorder(null);
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(870, 1333));
        FormInput.setLayout(null);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 10, 131, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(309, 10, 260, 23);

        TNoRM.setEditable(false);
        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        FormInput.add(TNoRM);
        TNoRM.setBounds(207, 10, 100, 23);

        KdPegawai.setEditable(false);
        KdPegawai.setName("KdPegawai"); // NOI18N
        KdPegawai.setPreferredSize(new java.awt.Dimension(80, 23));
        KdPegawai.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KdPegawaiKeyPressed(evt);
            }
        });
        FormInput.add(KdPegawai);
        KdPegawai.setBounds(210, 100, 90, 23);

        NmPegawai.setEditable(false);
        NmPegawai.setName("NmPegawai"); // NOI18N
        NmPegawai.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(NmPegawai);
        NmPegawai.setBounds(300, 100, 180, 23);

        jLabel8.setText("Tgl.Lahir :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 70, 70, 23);

        TglLahir.setEditable(false);
        TglLahir.setHighlighter(null);
        TglLahir.setName("TglLahir"); // NOI18N
        FormInput.add(TglLahir);
        TglLahir.setBounds(74, 70, 80, 23);

        jLabel9.setText("Masalah Pada Mutu/ Kualitas Produk Obat:");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(540, 340, 230, 23);

        Jk.setEditable(false);
        Jk.setHighlighter(null);
        Jk.setName("Jk"); // NOI18N
        FormInput.add(Jk);
        Jk.setBounds(74, 40, 80, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        FormInput.add(jLabel10);
        jLabel10.setBounds(0, 10, 70, 23);

        jLabel11.setText("J.K. :");
        jLabel11.setName("jLabel11"); // NOI18N
        FormInput.add(jLabel11);
        jLabel11.setBounds(0, 40, 70, 23);

        jLabel12.setText("BB :");
        jLabel12.setName("jLabel12"); // NOI18N
        FormInput.add(jLabel12);
        jLabel12.setBounds(170, 70, 70, 23);

        BB.setFocusTraversalPolicyProvider(true);
        BB.setName("BB"); // NOI18N
        BB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BBKeyPressed(evt);
            }
        });
        FormInput.add(BB);
        BB.setBounds(245, 70, 80, 23);

        TanngalMulaiTerjadi.setForeground(new java.awt.Color(50, 70, 50));
        TanngalMulaiTerjadi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanngalMulaiTerjadi.setDisplayFormat("dd-MM-yyyy");
        TanngalMulaiTerjadi.setName("TanngalMulaiTerjadi"); // NOI18N
        TanngalMulaiTerjadi.setOpaque(false);
        TanngalMulaiTerjadi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanngalMulaiTerjadiKeyPressed(evt);
            }
        });
        FormInput.add(TanngalMulaiTerjadi);
        TanngalMulaiTerjadi.setBounds(310, 310, 90, 23);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel13.setText("Kg");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(330, 70, 30, 23);

        jLabel30.setText("Bentuk Manifestasi E.S.O yang terjadi :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(100, 340, 200, 23);

        scrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane2.setName("scrollPane2"); // NOI18N

        Riwayat_eso.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Riwayat_eso.setColumns(20);
        Riwayat_eso.setRows(5);
        Riwayat_eso.setName("Riwayat_eso"); // NOI18N
        Riwayat_eso.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Riwayat_esoKeyPressed(evt);
            }
        });
        scrollPane2.setViewportView(Riwayat_eso);

        FormInput.add(scrollPane2);
        scrollPane2.setBounds(190, 460, 340, 70);

        jLabel31.setText("Riwayat E.S.O yang pernah dialami :");
        jLabel31.setName("jLabel31"); // NOI18N
        FormInput.add(jLabel31);
        jLabel31.setBounds(100, 435, 180, 23);

        jLabel32.setText("Saat/ Tanggal Mulai Terjadi :");
        jLabel32.setName("jLabel32"); // NOI18N
        FormInput.add(jLabel32);
        jLabel32.setBounds(120, 310, 180, 23);

        scrollPane4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane4.setName("scrollPane4"); // NOI18N

        Masalah_Kualitas.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Masalah_Kualitas.setColumns(20);
        Masalah_Kualitas.setRows(5);
        Masalah_Kualitas.setName("Masalah_Kualitas"); // NOI18N
        Masalah_Kualitas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Masalah_KualitasKeyPressed(evt);
            }
        });
        scrollPane4.setViewportView(Masalah_Kualitas);

        FormInput.add(scrollPane4);
        scrollPane4.setBounds(650, 360, 340, 70);

        scrollPane5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane5.setName("scrollPane5"); // NOI18N

        Penyakit_Utama.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Penyakit_Utama.setColumns(20);
        Penyakit_Utama.setRows(12);
        Penyakit_Utama.setName("Penyakit_Utama"); // NOI18N
        Penyakit_Utama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Penyakit_UtamaKeyPressed(evt);
            }
        });
        scrollPane5.setViewportView(Penyakit_Utama);

        FormInput.add(scrollPane5);
        scrollPane5.setBounds(190, 180, 340, 80);

        jLabel94.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel94.setText("OBAT");
        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel94.setName("jLabel94"); // NOI18N
        FormInput.add(jLabel94);
        jLabel94.setBounds(20, 550, 180, 23);

        jLabel38.setText("Pekerjaan :");
        jLabel38.setName("jLabel38"); // NOI18N
        FormInput.add(jLabel38);
        jLabel38.setBounds(360, 70, 70, 23);

        Pekerjaan.setName("Pekerjaan"); // NOI18N
        Pekerjaan.setPreferredSize(new java.awt.Dimension(207, 23));
        Pekerjaan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PekerjaanKeyPressed(evt);
            }
        });
        FormInput.add(Pekerjaan);
        Pekerjaan.setBounds(435, 70, 150, 23);

        jLabel33.setText("Penyakit Utama :");
        jLabel33.setName("jLabel33"); // NOI18N
        FormInput.add(jLabel33);
        jLabel33.setBounds(70, 180, 120, 23);

        scrollPane7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scrollPane7.setName("scrollPane7"); // NOI18N

        Bentuk_Manifestasi.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        Bentuk_Manifestasi.setColumns(20);
        Bentuk_Manifestasi.setRows(5);
        Bentuk_Manifestasi.setName("Bentuk_Manifestasi"); // NOI18N
        Bentuk_Manifestasi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Bentuk_ManifestasiKeyPressed(evt);
            }
        });
        scrollPane7.setViewportView(Bentuk_Manifestasi);

        FormInput.add(scrollPane7);
        scrollPane7.setBounds(190, 360, 340, 70);

        jSeparator12.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator12.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator12.setName("jSeparator12"); // NOI18N
        FormInput.add(jSeparator12);
        jSeparator12.setBounds(0, 272, 1270, 2);

        jLabel39.setText("Apakah Pasien Hamil :");
        jLabel39.setName("jLabel39"); // NOI18N
        FormInput.add(jLabel39);
        jLabel39.setBounds(70, 140, 120, 23);

        Kesudahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sembuh", "Meninggal", "Sembuh dengan gejala sisa", "Belum Sembuh", "Tidak Tahu" }));
        Kesudahan.setName("Kesudahan"); // NOI18N
        Kesudahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KesudahanKeyPressed(evt);
            }
        });
        FormInput.add(Kesudahan);
        Kesudahan.setBounds(490, 140, 130, 23);

        jLabel40.setText("Penyakit Lain :");
        jLabel40.setName("jLabel40"); // NOI18N
        FormInput.add(jLabel40);
        jLabel40.setBounds(660, 140, 127, 23);

        Hamil.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak", "Ya", "Tidak Tahu" }));
        Hamil.setName("Hamil"); // NOI18N
        Hamil.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HamilKeyPressed(evt);
            }
        });
        FormInput.add(Hamil);
        Hamil.setBounds(190, 140, 130, 23);

        jLabel41.setText("Kesudahan :");
        jLabel41.setName("jLabel41"); // NOI18N
        FormInput.add(jLabel41);
        jLabel41.setBounds(360, 140, 127, 23);

        PenyakitLain.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Gangguan Ginjal", "Gangguan Hati", "Alergi", "Kondisi Medis Lainnya", "Faktor Industri", "Pertanian", "Kimia", "Lain-Lainnya" }));
        PenyakitLain.setName("PenyakitLain"); // NOI18N
        PenyakitLain.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                PenyakitLainKeyPressed(evt);
            }
        });
        FormInput.add(PenyakitLain);
        PenyakitLain.setBounds(790, 140, 128, 23);

        jLabel49.setText("Kesudahan E.S.O :");
        jLabel49.setName("jLabel49"); // NOI18N
        FormInput.add(jLabel49);
        jLabel49.setBounds(580, 460, 95, 23);

        HasilKesudahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Sembuh", "Meninggal", "Sembuh dengan gejala sisa", "Belum Sembuh", "Tidak Tahu" }));
        HasilKesudahan.setName("HasilKesudahan"); // NOI18N
        HasilKesudahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                HasilKesudahanKeyPressed(evt);
            }
        });
        FormInput.add(HasilKesudahan);
        HasilKesudahan.setBounds(780, 460, 128, 23);

        ReaksiSama.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak", "Tidak Tahu" }));
        ReaksiSama.setName("ReaksiSama"); // NOI18N
        ReaksiSama.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReaksiSamaKeyPressed(evt);
            }
        });
        FormInput.add(ReaksiSama);
        ReaksiSama.setBounds(860, 900, 128, 23);

        Jkn10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        Jkn10.setName("Jkn10"); // NOI18N
        Jkn10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jkn10KeyPressed(evt);
            }
        });
        FormInput.add(Jkn10);
        Jkn10.setBounds(385, 860, 70, 23);

        jLabel99.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel99.setText("EFEK SAMPING OBAT (E.S.O)");
        jLabel99.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel99.setName("jLabel99"); // NOI18N
        FormInput.add(jLabel99);
        jLabel99.setBounds(20, 280, 180, 23);

        label11.setText("Tanggal Laporan :");
        label11.setName("label11"); // NOI18N
        label11.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label11);
        label11.setBounds(580, 10, 110, 23);

        jLabel42.setText("Apakah reaksi E.S.O yang sama timbul sewaktu obat yang dicurigai digunakan kembali :");
        jLabel42.setName("jLabel42"); // NOI18N
        FormInput.add(jLabel42);
        jLabel42.setBounds(410, 900, 450, 23);

        ReaksiSetelah.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ya", "Tidak", "Tidak Tahu" }));
        ReaksiSetelah.setName("ReaksiSetelah"); // NOI18N
        ReaksiSetelah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ReaksiSetelahKeyPressed(evt);
            }
        });
        FormInput.add(ReaksiSetelah);
        ReaksiSetelah.setBounds(290, 900, 110, 23);

        TanggalMulai10.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMulai10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalMulai10.setDisplayFormat("dd-MM-yyyy");
        TanggalMulai10.setName("TanggalMulai10"); // NOI18N
        TanggalMulai10.setOpaque(false);
        TanggalMulai10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMulai10KeyPressed(evt);
            }
        });
        FormInput.add(TanggalMulai10);
        TanggalMulai10.setBounds(800, 860, 90, 23);

        jSeparator19.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator19.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator19.setName("jSeparator19"); // NOI18N
        FormInput.add(jSeparator19);
        jSeparator19.setBounds(0, 542, 1270, 2);

        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("NO BATCH");
        jLabel48.setName("jLabel48"); // NOI18N
        FormInput.add(jLabel48);
        jLabel48.setBounds(460, 560, 110, 23);

        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setText("MULAI");
        jLabel53.setName("jLabel53"); // NOI18N
        FormInput.add(jLabel53);
        jLabel53.setBounds(800, 560, 90, 23);

        jLabel47.setText("Obat 1 :");
        jLabel47.setName("jLabel47"); // NOI18N
        FormInput.add(jLabel47);
        jLabel47.setBounds(20, 590, 60, 23);

        Obat1.setEditable(false);
        Obat1.setFocusTraversalPolicyProvider(true);
        Obat1.setName("Obat1"); // NOI18N
        Obat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Obat1ActionPerformed(evt);
            }
        });
        Obat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat1KeyPressed(evt);
            }
        });
        FormInput.add(Obat1);
        Obat1.setBounds(80, 590, 190, 23);

        Dosis1.setEditable(false);
        Dosis1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Dosis1.setFocusTraversalPolicyProvider(true);
        Dosis1.setName("Dosis1"); // NOI18N
        Dosis1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis1KeyPressed(evt);
            }
        });
        FormInput.add(Dosis1);
        Dosis1.setBounds(686, 590, 110, 23);

        BtnObat1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat1.setMnemonic('2');
        BtnObat1.setToolTipText("Alt+2");
        BtnObat1.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObat1.setName("BtnObat1"); // NOI18N
        BtnObat1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat1ActionPerformed(evt);
            }
        });
        BtnObat1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat1KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat1);
        BtnObat1.setBounds(1200, 590, 28, 23);

        jLabel54.setText("Obat 2 :");
        jLabel54.setName("jLabel54"); // NOI18N
        FormInput.add(jLabel54);
        jLabel54.setBounds(20, 620, 60, 23);

        Obat2.setEditable(false);
        Obat2.setFocusTraversalPolicyProvider(true);
        Obat2.setName("Obat2"); // NOI18N
        Obat2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat2KeyPressed(evt);
            }
        });
        FormInput.add(Obat2);
        Obat2.setBounds(80, 620, 190, 23);

        Dosis2.setEditable(false);
        Dosis2.setFocusTraversalPolicyProvider(true);
        Dosis2.setName("Dosis2"); // NOI18N
        Dosis2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis2KeyPressed(evt);
            }
        });
        FormInput.add(Dosis2);
        Dosis2.setBounds(686, 620, 110, 23);

        BtnObat2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat2.setMnemonic('2');
        BtnObat2.setToolTipText("Alt+2");
        BtnObat2.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObat2.setName("BtnObat2"); // NOI18N
        BtnObat2.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat2ActionPerformed(evt);
            }
        });
        BtnObat2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat2KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat2);
        BtnObat2.setBounds(1200, 620, 28, 23);

        jLabel55.setText("Obat 3 :");
        jLabel55.setName("jLabel55"); // NOI18N
        FormInput.add(jLabel55);
        jLabel55.setBounds(20, 650, 60, 23);

        Obat3.setEditable(false);
        Obat3.setFocusTraversalPolicyProvider(true);
        Obat3.setName("Obat3"); // NOI18N
        Obat3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat3KeyPressed(evt);
            }
        });
        FormInput.add(Obat3);
        Obat3.setBounds(80, 650, 190, 23);

        Dosis3.setEditable(false);
        Dosis3.setFocusTraversalPolicyProvider(true);
        Dosis3.setName("Dosis3"); // NOI18N
        Dosis3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis3KeyPressed(evt);
            }
        });
        FormInput.add(Dosis3);
        Dosis3.setBounds(686, 650, 110, 23);

        BtnObat3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat3.setMnemonic('2');
        BtnObat3.setToolTipText("Alt+2");
        BtnObat3.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObat3.setName("BtnObat3"); // NOI18N
        BtnObat3.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat3ActionPerformed(evt);
            }
        });
        BtnObat3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat3KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat3);
        BtnObat3.setBounds(1200, 650, 28, 23);

        jLabel56.setText("Obat 4 :");
        jLabel56.setName("jLabel56"); // NOI18N
        FormInput.add(jLabel56);
        jLabel56.setBounds(20, 680, 60, 23);

        Obat4.setEditable(false);
        Obat4.setFocusTraversalPolicyProvider(true);
        Obat4.setName("Obat4"); // NOI18N
        Obat4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat4KeyPressed(evt);
            }
        });
        FormInput.add(Obat4);
        Obat4.setBounds(80, 680, 190, 23);

        Dosis4.setEditable(false);
        Dosis4.setFocusTraversalPolicyProvider(true);
        Dosis4.setName("Dosis4"); // NOI18N
        Dosis4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis4KeyPressed(evt);
            }
        });
        FormInput.add(Dosis4);
        Dosis4.setBounds(686, 680, 110, 23);

        BtnObat4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat4.setMnemonic('2');
        BtnObat4.setToolTipText("Alt+2");
        BtnObat4.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObat4.setName("BtnObat4"); // NOI18N
        BtnObat4.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat4ActionPerformed(evt);
            }
        });
        BtnObat4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat4KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat4);
        BtnObat4.setBounds(1200, 680, 28, 23);

        jLabel57.setText("Obat 5 :");
        jLabel57.setName("jLabel57"); // NOI18N
        FormInput.add(jLabel57);
        jLabel57.setBounds(20, 710, 60, 23);

        Obat5.setEditable(false);
        Obat5.setFocusTraversalPolicyProvider(true);
        Obat5.setName("Obat5"); // NOI18N
        Obat5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat5KeyPressed(evt);
            }
        });
        FormInput.add(Obat5);
        Obat5.setBounds(80, 710, 190, 23);

        Dosis5.setEditable(false);
        Dosis5.setFocusTraversalPolicyProvider(true);
        Dosis5.setName("Dosis5"); // NOI18N
        Dosis5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis5KeyPressed(evt);
            }
        });
        FormInput.add(Dosis5);
        Dosis5.setBounds(686, 710, 110, 23);

        BtnObat5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat5.setMnemonic('2');
        BtnObat5.setToolTipText("Alt+2");
        BtnObat5.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObat5.setName("BtnObat5"); // NOI18N
        BtnObat5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat5ActionPerformed(evt);
            }
        });
        BtnObat5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat5KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat5);
        BtnObat5.setBounds(1200, 710, 28, 23);

        jLabel58.setText("Obat 6 :");
        jLabel58.setName("jLabel58"); // NOI18N
        FormInput.add(jLabel58);
        jLabel58.setBounds(20, 740, 60, 23);

        Obat6.setEditable(false);
        Obat6.setFocusTraversalPolicyProvider(true);
        Obat6.setName("Obat6"); // NOI18N
        Obat6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat6KeyPressed(evt);
            }
        });
        FormInput.add(Obat6);
        Obat6.setBounds(80, 740, 190, 23);

        Dosis6.setEditable(false);
        Dosis6.setFocusTraversalPolicyProvider(true);
        Dosis6.setName("Dosis6"); // NOI18N
        Dosis6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis6KeyPressed(evt);
            }
        });
        FormInput.add(Dosis6);
        Dosis6.setBounds(686, 740, 110, 23);

        BtnObat6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat6.setMnemonic('2');
        BtnObat6.setToolTipText("Alt+2");
        BtnObat6.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObat6.setName("BtnObat6"); // NOI18N
        BtnObat6.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat6ActionPerformed(evt);
            }
        });
        BtnObat6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat6KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat6);
        BtnObat6.setBounds(1200, 740, 28, 23);

        jLabel59.setText("Obat 7 :");
        jLabel59.setName("jLabel59"); // NOI18N
        FormInput.add(jLabel59);
        jLabel59.setBounds(20, 770, 60, 23);

        Obat7.setEditable(false);
        Obat7.setFocusTraversalPolicyProvider(true);
        Obat7.setName("Obat7"); // NOI18N
        Obat7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat7KeyPressed(evt);
            }
        });
        FormInput.add(Obat7);
        Obat7.setBounds(80, 770, 190, 23);

        Dosis7.setEditable(false);
        Dosis7.setFocusTraversalPolicyProvider(true);
        Dosis7.setName("Dosis7"); // NOI18N
        Dosis7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis7KeyPressed(evt);
            }
        });
        FormInput.add(Dosis7);
        Dosis7.setBounds(686, 770, 110, 23);

        BtnObat7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat7.setMnemonic('2');
        BtnObat7.setToolTipText("Alt+2");
        BtnObat7.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObat7.setName("BtnObat7"); // NOI18N
        BtnObat7.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat7ActionPerformed(evt);
            }
        });
        BtnObat7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat7KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat7);
        BtnObat7.setBounds(1200, 770, 28, 23);

        jLabel60.setText("Obat 8 :");
        jLabel60.setName("jLabel60"); // NOI18N
        FormInput.add(jLabel60);
        jLabel60.setBounds(20, 800, 60, 23);

        Obat8.setEditable(false);
        Obat8.setFocusTraversalPolicyProvider(true);
        Obat8.setName("Obat8"); // NOI18N
        Obat8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat8KeyPressed(evt);
            }
        });
        FormInput.add(Obat8);
        Obat8.setBounds(80, 800, 190, 23);

        Dosis8.setEditable(false);
        Dosis8.setFocusTraversalPolicyProvider(true);
        Dosis8.setName("Dosis8"); // NOI18N
        Dosis8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis8KeyPressed(evt);
            }
        });
        FormInput.add(Dosis8);
        Dosis8.setBounds(686, 800, 110, 23);

        BtnObat8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat8.setMnemonic('2');
        BtnObat8.setToolTipText("Alt+2");
        BtnObat8.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObat8.setName("BtnObat8"); // NOI18N
        BtnObat8.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat8ActionPerformed(evt);
            }
        });
        BtnObat8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat8KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat8);
        BtnObat8.setBounds(1200, 800, 28, 23);

        jLabel61.setText("Obat 9 :");
        jLabel61.setName("jLabel61"); // NOI18N
        FormInput.add(jLabel61);
        jLabel61.setBounds(20, 830, 60, 23);

        Obat9.setEditable(false);
        Obat9.setFocusTraversalPolicyProvider(true);
        Obat9.setName("Obat9"); // NOI18N
        Obat9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat9KeyPressed(evt);
            }
        });
        FormInput.add(Obat9);
        Obat9.setBounds(80, 830, 190, 23);

        Dosis9.setEditable(false);
        Dosis9.setFocusTraversalPolicyProvider(true);
        Dosis9.setName("Dosis9"); // NOI18N
        Dosis9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis9KeyPressed(evt);
            }
        });
        FormInput.add(Dosis9);
        Dosis9.setBounds(686, 830, 110, 23);

        BtnObat9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat9.setMnemonic('2');
        BtnObat9.setToolTipText("Alt+2");
        BtnObat9.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObat9.setName("BtnObat9"); // NOI18N
        BtnObat9.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat9ActionPerformed(evt);
            }
        });
        BtnObat9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat9KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat9);
        BtnObat9.setBounds(1200, 830, 28, 23);

        jLabel62.setText("Obat 10 :");
        jLabel62.setName("jLabel62"); // NOI18N
        FormInput.add(jLabel62);
        jLabel62.setBounds(20, 860, 60, 23);

        Obat10.setEditable(false);
        Obat10.setFocusTraversalPolicyProvider(true);
        Obat10.setName("Obat10"); // NOI18N
        Obat10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Obat10KeyPressed(evt);
            }
        });
        FormInput.add(Obat10);
        Obat10.setBounds(80, 860, 190, 23);

        Dosis10.setEditable(false);
        Dosis10.setFocusTraversalPolicyProvider(true);
        Dosis10.setName("Dosis10"); // NOI18N
        Dosis10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Dosis10KeyPressed(evt);
            }
        });
        FormInput.add(Dosis10);
        Dosis10.setBounds(686, 860, 110, 23);

        BtnObat10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnObat10.setMnemonic('2');
        BtnObat10.setToolTipText("Alt+2");
        BtnObat10.setGlassColor(new java.awt.Color(255, 255, 255));
        BtnObat10.setName("BtnObat10"); // NOI18N
        BtnObat10.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnObat10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnObat10ActionPerformed(evt);
            }
        });
        BtnObat10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnObat10KeyPressed(evt);
            }
        });
        FormInput.add(BtnObat10);
        BtnObat10.setBounds(1200, 860, 28, 23);

        Sedian1.setEditable(false);
        Sedian1.setFocusTraversalPolicyProvider(true);
        Sedian1.setName("Sedian1"); // NOI18N
        Sedian1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sedian1KeyPressed(evt);
            }
        });
        FormInput.add(Sedian1);
        Sedian1.setBounds(273, 590, 110, 23);

        Sedian2.setEditable(false);
        Sedian2.setFocusTraversalPolicyProvider(true);
        Sedian2.setName("Sedian2"); // NOI18N
        Sedian2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sedian2KeyPressed(evt);
            }
        });
        FormInput.add(Sedian2);
        Sedian2.setBounds(273, 620, 110, 23);

        Sedian3.setEditable(false);
        Sedian3.setFocusTraversalPolicyProvider(true);
        Sedian3.setName("Sedian3"); // NOI18N
        Sedian3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sedian3KeyPressed(evt);
            }
        });
        FormInput.add(Sedian3);
        Sedian3.setBounds(273, 650, 110, 23);

        Sedian4.setEditable(false);
        Sedian4.setFocusTraversalPolicyProvider(true);
        Sedian4.setName("Sedian4"); // NOI18N
        Sedian4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sedian4KeyPressed(evt);
            }
        });
        FormInput.add(Sedian4);
        Sedian4.setBounds(273, 680, 110, 23);

        Sedian5.setEditable(false);
        Sedian5.setFocusTraversalPolicyProvider(true);
        Sedian5.setName("Sedian5"); // NOI18N
        Sedian5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sedian5KeyPressed(evt);
            }
        });
        FormInput.add(Sedian5);
        Sedian5.setBounds(273, 710, 110, 23);

        Sedian6.setEditable(false);
        Sedian6.setFocusTraversalPolicyProvider(true);
        Sedian6.setName("Sedian6"); // NOI18N
        Sedian6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sedian6KeyPressed(evt);
            }
        });
        FormInput.add(Sedian6);
        Sedian6.setBounds(273, 740, 110, 23);

        Sedian7.setEditable(false);
        Sedian7.setFocusTraversalPolicyProvider(true);
        Sedian7.setName("Sedian7"); // NOI18N
        Sedian7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sedian7KeyPressed(evt);
            }
        });
        FormInput.add(Sedian7);
        Sedian7.setBounds(273, 770, 110, 23);

        Sedian8.setEditable(false);
        Sedian8.setFocusTraversalPolicyProvider(true);
        Sedian8.setName("Sedian8"); // NOI18N
        Sedian8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sedian8KeyPressed(evt);
            }
        });
        FormInput.add(Sedian8);
        Sedian8.setBounds(273, 800, 110, 23);

        Sedian9.setEditable(false);
        Sedian9.setFocusTraversalPolicyProvider(true);
        Sedian9.setName("Sedian9"); // NOI18N
        Sedian9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sedian9KeyPressed(evt);
            }
        });
        FormInput.add(Sedian9);
        Sedian9.setBounds(273, 830, 110, 23);

        Sedian10.setEditable(false);
        Sedian10.setFocusTraversalPolicyProvider(true);
        Sedian10.setName("Sedian10"); // NOI18N
        Sedian10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Sedian10KeyPressed(evt);
            }
        });
        FormInput.add(Sedian10);
        Sedian10.setBounds(273, 860, 110, 23);

        Batch4.setFocusTraversalPolicyProvider(true);
        Batch4.setName("Batch4"); // NOI18N
        Batch4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batch4KeyPressed(evt);
            }
        });
        FormInput.add(Batch4);
        Batch4.setBounds(460, 680, 110, 23);

        Batch5.setFocusTraversalPolicyProvider(true);
        Batch5.setName("Batch5"); // NOI18N
        Batch5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batch5KeyPressed(evt);
            }
        });
        FormInput.add(Batch5);
        Batch5.setBounds(460, 710, 110, 23);

        Batch2.setFocusTraversalPolicyProvider(true);
        Batch2.setName("Batch2"); // NOI18N
        Batch2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batch2KeyPressed(evt);
            }
        });
        FormInput.add(Batch2);
        Batch2.setBounds(460, 620, 110, 23);

        Batch10.setFocusTraversalPolicyProvider(true);
        Batch10.setName("Batch10"); // NOI18N
        Batch10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batch10KeyPressed(evt);
            }
        });
        FormInput.add(Batch10);
        Batch10.setBounds(460, 860, 110, 23);

        Batch6.setFocusTraversalPolicyProvider(true);
        Batch6.setName("Batch6"); // NOI18N
        Batch6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batch6KeyPressed(evt);
            }
        });
        FormInput.add(Batch6);
        Batch6.setBounds(460, 740, 110, 23);

        Batch3.setFocusTraversalPolicyProvider(true);
        Batch3.setName("Batch3"); // NOI18N
        Batch3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batch3KeyPressed(evt);
            }
        });
        FormInput.add(Batch3);
        Batch3.setBounds(460, 650, 110, 23);

        Batch8.setFocusTraversalPolicyProvider(true);
        Batch8.setName("Batch8"); // NOI18N
        Batch8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batch8KeyPressed(evt);
            }
        });
        FormInput.add(Batch8);
        Batch8.setBounds(460, 800, 110, 23);

        Batch7.setFocusTraversalPolicyProvider(true);
        Batch7.setName("Batch7"); // NOI18N
        Batch7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batch7KeyPressed(evt);
            }
        });
        FormInput.add(Batch7);
        Batch7.setBounds(460, 770, 110, 23);

        Batch9.setFocusTraversalPolicyProvider(true);
        Batch9.setName("Batch9"); // NOI18N
        Batch9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batch9KeyPressed(evt);
            }
        });
        FormInput.add(Batch9);
        Batch9.setBounds(460, 830, 110, 23);

        Batch1.setFocusTraversalPolicyProvider(true);
        Batch1.setName("Batch1"); // NOI18N
        Batch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Batch1KeyPressed(evt);
            }
        });
        FormInput.add(Batch1);
        Batch1.setBounds(460, 590, 110, 23);

        jLabel63.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel63.setText("NAMA OBAT");
        jLabel63.setName("jLabel63"); // NOI18N
        FormInput.add(jLabel63);
        jLabel63.setBounds(80, 560, 180, 23);

        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setText("JKN");
        jLabel64.setName("jLabel64"); // NOI18N
        FormInput.add(jLabel64);
        jLabel64.setBounds(385, 560, 70, 23);

        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setText("CARA");
        jLabel65.setName("jLabel65"); // NOI18N
        FormInput.add(jLabel65);
        jLabel65.setBounds(574, 560, 110, 23);

        Cara4.setFocusTraversalPolicyProvider(true);
        Cara4.setName("Cara4"); // NOI18N
        Cara4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Cara4KeyPressed(evt);
            }
        });
        FormInput.add(Cara4);
        Cara4.setBounds(574, 680, 110, 23);

        Cara5.setFocusTraversalPolicyProvider(true);
        Cara5.setName("Cara5"); // NOI18N
        Cara5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Cara5KeyPressed(evt);
            }
        });
        FormInput.add(Cara5);
        Cara5.setBounds(574, 710, 110, 23);

        Cara2.setFocusTraversalPolicyProvider(true);
        Cara2.setName("Cara2"); // NOI18N
        Cara2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Cara2KeyPressed(evt);
            }
        });
        FormInput.add(Cara2);
        Cara2.setBounds(574, 620, 110, 23);

        Cara10.setFocusTraversalPolicyProvider(true);
        Cara10.setName("Cara10"); // NOI18N
        Cara10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Cara10KeyPressed(evt);
            }
        });
        FormInput.add(Cara10);
        Cara10.setBounds(574, 860, 110, 23);

        Cara6.setFocusTraversalPolicyProvider(true);
        Cara6.setName("Cara6"); // NOI18N
        Cara6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Cara6KeyPressed(evt);
            }
        });
        FormInput.add(Cara6);
        Cara6.setBounds(574, 740, 110, 23);

        Cara3.setFocusTraversalPolicyProvider(true);
        Cara3.setName("Cara3"); // NOI18N
        Cara3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Cara3KeyPressed(evt);
            }
        });
        FormInput.add(Cara3);
        Cara3.setBounds(574, 650, 110, 23);

        Cara8.setFocusTraversalPolicyProvider(true);
        Cara8.setName("Cara8"); // NOI18N
        Cara8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Cara8KeyPressed(evt);
            }
        });
        FormInput.add(Cara8);
        Cara8.setBounds(574, 800, 110, 23);

        Cara7.setFocusTraversalPolicyProvider(true);
        Cara7.setName("Cara7"); // NOI18N
        Cara7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Cara7KeyPressed(evt);
            }
        });
        FormInput.add(Cara7);
        Cara7.setBounds(574, 770, 110, 23);

        Cara9.setFocusTraversalPolicyProvider(true);
        Cara9.setName("Cara9"); // NOI18N
        Cara9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Cara9KeyPressed(evt);
            }
        });
        FormInput.add(Cara9);
        Cara9.setBounds(574, 830, 110, 23);

        Cara1.setFocusTraversalPolicyProvider(true);
        Cara1.setName("Cara1"); // NOI18N
        Cara1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Cara1KeyPressed(evt);
            }
        });
        FormInput.add(Cara1);
        Cara1.setBounds(574, 590, 110, 23);

        TanggaKesudahan.setForeground(new java.awt.Color(50, 70, 50));
        TanggaKesudahan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggaKesudahan.setDisplayFormat("dd-MM-yyyy");
        TanggaKesudahan.setName("TanggaKesudahan"); // NOI18N
        TanggaKesudahan.setOpaque(false);
        TanggaKesudahan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggaKesudahanKeyPressed(evt);
            }
        });
        FormInput.add(TanggaKesudahan);
        TanggaKesudahan.setBounds(680, 460, 90, 23);

        TanggalMulai1.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMulai1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalMulai1.setDisplayFormat("dd-MM-yyyy");
        TanggalMulai1.setName("TanggalMulai1"); // NOI18N
        TanggalMulai1.setOpaque(false);
        TanggalMulai1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMulai1KeyPressed(evt);
            }
        });
        FormInput.add(TanggalMulai1);
        TanggalMulai1.setBounds(800, 590, 90, 23);

        TanggalMulai2.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMulai2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalMulai2.setDisplayFormat("dd-MM-yyyy");
        TanggalMulai2.setName("TanggalMulai2"); // NOI18N
        TanggalMulai2.setOpaque(false);
        TanggalMulai2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMulai2KeyPressed(evt);
            }
        });
        FormInput.add(TanggalMulai2);
        TanggalMulai2.setBounds(800, 620, 90, 23);

        TanggalMulai3.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMulai3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalMulai3.setDisplayFormat("dd-MM-yyyy");
        TanggalMulai3.setName("TanggalMulai3"); // NOI18N
        TanggalMulai3.setOpaque(false);
        TanggalMulai3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMulai3KeyPressed(evt);
            }
        });
        FormInput.add(TanggalMulai3);
        TanggalMulai3.setBounds(800, 650, 90, 23);

        TanggalMulai4.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMulai4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalMulai4.setDisplayFormat("dd-MM-yyyy");
        TanggalMulai4.setName("TanggalMulai4"); // NOI18N
        TanggalMulai4.setOpaque(false);
        TanggalMulai4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMulai4KeyPressed(evt);
            }
        });
        FormInput.add(TanggalMulai4);
        TanggalMulai4.setBounds(800, 680, 90, 23);

        TanggalMulai5.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMulai5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalMulai5.setDisplayFormat("dd-MM-yyyy");
        TanggalMulai5.setName("TanggalMulai5"); // NOI18N
        TanggalMulai5.setOpaque(false);
        TanggalMulai5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMulai5KeyPressed(evt);
            }
        });
        FormInput.add(TanggalMulai5);
        TanggalMulai5.setBounds(800, 710, 90, 23);

        TanggalMulai6.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMulai6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalMulai6.setDisplayFormat("dd-MM-yyyy");
        TanggalMulai6.setName("TanggalMulai6"); // NOI18N
        TanggalMulai6.setOpaque(false);
        TanggalMulai6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMulai6KeyPressed(evt);
            }
        });
        FormInput.add(TanggalMulai6);
        TanggalMulai6.setBounds(800, 740, 90, 23);

        TanggalMulai7.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMulai7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalMulai7.setDisplayFormat("dd-MM-yyyy");
        TanggalMulai7.setName("TanggalMulai7"); // NOI18N
        TanggalMulai7.setOpaque(false);
        TanggalMulai7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMulai7KeyPressed(evt);
            }
        });
        FormInput.add(TanggalMulai7);
        TanggalMulai7.setBounds(800, 770, 90, 23);

        TanggalMulai8.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMulai8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalMulai8.setDisplayFormat("dd-MM-yyyy");
        TanggalMulai8.setName("TanggalMulai8"); // NOI18N
        TanggalMulai8.setOpaque(false);
        TanggalMulai8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMulai8KeyPressed(evt);
            }
        });
        FormInput.add(TanggalMulai8);
        TanggalMulai8.setBounds(800, 800, 90, 23);

        TanggalMulai9.setForeground(new java.awt.Color(50, 70, 50));
        TanggalMulai9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalMulai9.setDisplayFormat("dd-MM-yyyy");
        TanggalMulai9.setName("TanggalMulai9"); // NOI18N
        TanggalMulai9.setOpaque(false);
        TanggalMulai9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalMulai9KeyPressed(evt);
            }
        });
        FormInput.add(TanggalMulai9);
        TanggalMulai9.setBounds(800, 830, 90, 23);

        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setText("DOSIS");
        jLabel66.setName("jLabel66"); // NOI18N
        FormInput.add(jLabel66);
        jLabel66.setBounds(686, 560, 110, 23);

        TanggalAkhir10.setForeground(new java.awt.Color(50, 70, 50));
        TanggalAkhir10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalAkhir10.setDisplayFormat("dd-MM-yyyy");
        TanggalAkhir10.setName("TanggalAkhir10"); // NOI18N
        TanggalAkhir10.setOpaque(false);
        TanggalAkhir10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalAkhir10KeyPressed(evt);
            }
        });
        FormInput.add(TanggalAkhir10);
        TanggalAkhir10.setBounds(905, 860, 90, 23);

        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setText("INDIKASI PENGGUNAAN");
        jLabel67.setName("jLabel67"); // NOI18N
        FormInput.add(jLabel67);
        jLabel67.setBounds(1005, 560, 190, 23);

        TanggalAkhir1.setForeground(new java.awt.Color(50, 70, 50));
        TanggalAkhir1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalAkhir1.setDisplayFormat("dd-MM-yyyy");
        TanggalAkhir1.setName("TanggalAkhir1"); // NOI18N
        TanggalAkhir1.setOpaque(false);
        TanggalAkhir1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalAkhir1KeyPressed(evt);
            }
        });
        FormInput.add(TanggalAkhir1);
        TanggalAkhir1.setBounds(905, 590, 90, 23);

        TanggalAkhir2.setForeground(new java.awt.Color(50, 70, 50));
        TanggalAkhir2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalAkhir2.setDisplayFormat("dd-MM-yyyy");
        TanggalAkhir2.setName("TanggalAkhir2"); // NOI18N
        TanggalAkhir2.setOpaque(false);
        TanggalAkhir2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalAkhir2KeyPressed(evt);
            }
        });
        FormInput.add(TanggalAkhir2);
        TanggalAkhir2.setBounds(905, 620, 90, 23);

        TanggalAkhir3.setForeground(new java.awt.Color(50, 70, 50));
        TanggalAkhir3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalAkhir3.setDisplayFormat("dd-MM-yyyy");
        TanggalAkhir3.setName("TanggalAkhir3"); // NOI18N
        TanggalAkhir3.setOpaque(false);
        TanggalAkhir3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalAkhir3KeyPressed(evt);
            }
        });
        FormInput.add(TanggalAkhir3);
        TanggalAkhir3.setBounds(905, 650, 90, 23);

        TanggalAkhir4.setForeground(new java.awt.Color(50, 70, 50));
        TanggalAkhir4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalAkhir4.setDisplayFormat("dd-MM-yyyy");
        TanggalAkhir4.setName("TanggalAkhir4"); // NOI18N
        TanggalAkhir4.setOpaque(false);
        TanggalAkhir4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalAkhir4KeyPressed(evt);
            }
        });
        FormInput.add(TanggalAkhir4);
        TanggalAkhir4.setBounds(905, 680, 90, 23);

        TanggalAkhir5.setForeground(new java.awt.Color(50, 70, 50));
        TanggalAkhir5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalAkhir5.setDisplayFormat("dd-MM-yyyy");
        TanggalAkhir5.setName("TanggalAkhir5"); // NOI18N
        TanggalAkhir5.setOpaque(false);
        TanggalAkhir5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalAkhir5KeyPressed(evt);
            }
        });
        FormInput.add(TanggalAkhir5);
        TanggalAkhir5.setBounds(905, 710, 90, 23);

        TanggalAkhir6.setForeground(new java.awt.Color(50, 70, 50));
        TanggalAkhir6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalAkhir6.setDisplayFormat("dd-MM-yyyy");
        TanggalAkhir6.setName("TanggalAkhir6"); // NOI18N
        TanggalAkhir6.setOpaque(false);
        TanggalAkhir6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalAkhir6KeyPressed(evt);
            }
        });
        FormInput.add(TanggalAkhir6);
        TanggalAkhir6.setBounds(905, 740, 90, 23);

        TanggalAkhir7.setForeground(new java.awt.Color(50, 70, 50));
        TanggalAkhir7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalAkhir7.setDisplayFormat("dd-MM-yyyy");
        TanggalAkhir7.setName("TanggalAkhir7"); // NOI18N
        TanggalAkhir7.setOpaque(false);
        TanggalAkhir7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalAkhir7KeyPressed(evt);
            }
        });
        FormInput.add(TanggalAkhir7);
        TanggalAkhir7.setBounds(905, 770, 90, 23);

        TanggalAkhir8.setForeground(new java.awt.Color(50, 70, 50));
        TanggalAkhir8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalAkhir8.setDisplayFormat("dd-MM-yyyy");
        TanggalAkhir8.setName("TanggalAkhir8"); // NOI18N
        TanggalAkhir8.setOpaque(false);
        TanggalAkhir8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalAkhir8KeyPressed(evt);
            }
        });
        FormInput.add(TanggalAkhir8);
        TanggalAkhir8.setBounds(905, 800, 90, 23);

        TanggalAkhir9.setForeground(new java.awt.Color(50, 70, 50));
        TanggalAkhir9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        TanggalAkhir9.setDisplayFormat("dd-MM-yyyy");
        TanggalAkhir9.setName("TanggalAkhir9"); // NOI18N
        TanggalAkhir9.setOpaque(false);
        TanggalAkhir9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TanggalAkhir9KeyPressed(evt);
            }
        });
        FormInput.add(TanggalAkhir9);
        TanggalAkhir9.setBounds(905, 830, 90, 23);

        Indikasi1.setFocusTraversalPolicyProvider(true);
        Indikasi1.setName("Indikasi1"); // NOI18N
        Indikasi1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Indikasi1ActionPerformed(evt);
            }
        });
        Indikasi1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Indikasi1KeyPressed(evt);
            }
        });
        FormInput.add(Indikasi1);
        Indikasi1.setBounds(1005, 590, 190, 23);

        Indikasi2.setFocusTraversalPolicyProvider(true);
        Indikasi2.setName("Indikasi2"); // NOI18N
        Indikasi2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Indikasi2KeyPressed(evt);
            }
        });
        FormInput.add(Indikasi2);
        Indikasi2.setBounds(1005, 620, 190, 23);

        Indikasi3.setFocusTraversalPolicyProvider(true);
        Indikasi3.setName("Indikasi3"); // NOI18N
        Indikasi3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Indikasi3KeyPressed(evt);
            }
        });
        FormInput.add(Indikasi3);
        Indikasi3.setBounds(1005, 650, 190, 23);

        Indikasi4.setFocusTraversalPolicyProvider(true);
        Indikasi4.setName("Indikasi4"); // NOI18N
        Indikasi4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Indikasi4KeyPressed(evt);
            }
        });
        FormInput.add(Indikasi4);
        Indikasi4.setBounds(1005, 680, 190, 23);

        Indikasi5.setFocusTraversalPolicyProvider(true);
        Indikasi5.setName("Indikasi5"); // NOI18N
        Indikasi5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Indikasi5KeyPressed(evt);
            }
        });
        FormInput.add(Indikasi5);
        Indikasi5.setBounds(1005, 710, 190, 23);

        Indikasi6.setFocusTraversalPolicyProvider(true);
        Indikasi6.setName("Indikasi6"); // NOI18N
        Indikasi6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Indikasi6KeyPressed(evt);
            }
        });
        FormInput.add(Indikasi6);
        Indikasi6.setBounds(1005, 740, 190, 23);

        Indikasi7.setFocusTraversalPolicyProvider(true);
        Indikasi7.setName("Indikasi7"); // NOI18N
        Indikasi7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Indikasi7KeyPressed(evt);
            }
        });
        FormInput.add(Indikasi7);
        Indikasi7.setBounds(1005, 770, 190, 23);

        Indikasi8.setFocusTraversalPolicyProvider(true);
        Indikasi8.setName("Indikasi8"); // NOI18N
        Indikasi8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Indikasi8KeyPressed(evt);
            }
        });
        FormInput.add(Indikasi8);
        Indikasi8.setBounds(1005, 800, 190, 23);

        Indikasi9.setFocusTraversalPolicyProvider(true);
        Indikasi9.setName("Indikasi9"); // NOI18N
        Indikasi9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Indikasi9KeyPressed(evt);
            }
        });
        FormInput.add(Indikasi9);
        Indikasi9.setBounds(1005, 830, 190, 23);

        Indikasi10.setFocusTraversalPolicyProvider(true);
        Indikasi10.setName("Indikasi10"); // NOI18N
        Indikasi10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Indikasi10KeyPressed(evt);
            }
        });
        FormInput.add(Indikasi10);
        Indikasi10.setBounds(1005, 860, 190, 23);

        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel68.setText("AKHIR");
        jLabel68.setName("jLabel68"); // NOI18N
        FormInput.add(jLabel68);
        jLabel68.setBounds(905, 560, 90, 23);

        jSeparator20.setBackground(new java.awt.Color(239, 244, 234));
        jSeparator20.setForeground(new java.awt.Color(239, 244, 234));
        jSeparator20.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239, 244, 234)));
        jSeparator20.setName("jSeparator20"); // NOI18N
        FormInput.add(jSeparator20);
        jSeparator20.setBounds(0, 932, 1270, 2);

        jLabel43.setText("Apakah reaksi E.S.O hilang setelah obat diberikan :");
        jLabel43.setName("jLabel43"); // NOI18N
        FormInput.add(jLabel43);
        jLabel43.setBounds(30, 900, 250, 23);

        jLabel243.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel243.setText("1. Apakah ada laporan efek samping obat yang serupa ?");
        jLabel243.setName("jLabel243"); // NOI18N
        FormInput.add(jLabel243);
        jLabel243.setBounds(110, 970, 670, 23);

        Naranjo1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Tahu", "Ya", "Tidak" }));
        Naranjo1.setName("Naranjo1"); // NOI18N
        Naranjo1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Naranjo1ItemStateChanged(evt);
            }
        });
        Naranjo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Naranjo1ActionPerformed(evt);
            }
        });
        Naranjo1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo1KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo1);
        Naranjo1.setBounds(780, 970, 90, 23);

        NilaiNaranjo1.setEditable(false);
        NilaiNaranjo1.setFocusTraversalPolicyProvider(true);
        NilaiNaranjo1.setName("NilaiNaranjo1"); // NOI18N
        NilaiNaranjo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NilaiNaranjo1ActionPerformed(evt);
            }
        });
        FormInput.add(NilaiNaranjo1);
        NilaiNaranjo1.setBounds(880, 970, 40, 23);

        jLabel244.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel244.setText("2. Apakah efek samping obat terjadi setelah pembersihan obat yang dicurigai ?");
        jLabel244.setName("jLabel244"); // NOI18N
        FormInput.add(jLabel244);
        jLabel244.setBounds(110, 1000, 670, 23);

        Naranjo2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Tahu", "Ya", "Tidak" }));
        Naranjo2.setName("Naranjo2"); // NOI18N
        Naranjo2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Naranjo2ItemStateChanged(evt);
            }
        });
        Naranjo2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo2KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo2);
        Naranjo2.setBounds(780, 1000, 90, 23);

        NilaiNaranjo2.setEditable(false);
        NilaiNaranjo2.setFocusTraversalPolicyProvider(true);
        NilaiNaranjo2.setName("NilaiNaranjo2"); // NOI18N
        FormInput.add(NilaiNaranjo2);
        NilaiNaranjo2.setBounds(880, 1000, 40, 23);

        jLabel245.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel245.setText("3. Apakah efek samping obat membaik setelah obat diberhentikan atau obat antagonis khusus diberikan ?");
        jLabel245.setName("jLabel245"); // NOI18N
        FormInput.add(jLabel245);
        jLabel245.setBounds(110, 1030, 670, 23);

        Naranjo3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Tahu", "Ya", "Tidak" }));
        Naranjo3.setName("Naranjo3"); // NOI18N
        Naranjo3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Naranjo3ItemStateChanged(evt);
            }
        });
        Naranjo3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo3KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo3);
        Naranjo3.setBounds(780, 1030, 90, 23);

        NilaiNaranjo3.setEditable(false);
        NilaiNaranjo3.setFocusTraversalPolicyProvider(true);
        NilaiNaranjo3.setName("NilaiNaranjo3"); // NOI18N
        FormInput.add(NilaiNaranjo3);
        NilaiNaranjo3.setBounds(880, 1030, 40, 23);

        jLabel246.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel246.setText("4. Apakah efek samping obat terjadi berulang kali setelah obat diberikan kembali ?");
        jLabel246.setName("jLabel246"); // NOI18N
        FormInput.add(jLabel246);
        jLabel246.setBounds(110, 1060, 670, 23);

        Naranjo4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Tahu", "Ya", "Tidak" }));
        Naranjo4.setName("Naranjo4"); // NOI18N
        Naranjo4.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Naranjo4ItemStateChanged(evt);
            }
        });
        Naranjo4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo4KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo4);
        Naranjo4.setBounds(780, 1060, 90, 23);

        NilaiNaranjo4.setEditable(false);
        NilaiNaranjo4.setFocusTraversalPolicyProvider(true);
        NilaiNaranjo4.setName("NilaiNaranjo4"); // NOI18N
        FormInput.add(NilaiNaranjo4);
        NilaiNaranjo4.setBounds(880, 1060, 40, 23);

        jLabel247.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel247.setText("5. Apakah ada alternative lain penyebab yang dapat menjelaskan kemungkinan efek samping obat ?");
        jLabel247.setName("jLabel247"); // NOI18N
        FormInput.add(jLabel247);
        jLabel247.setBounds(110, 1090, 670, 23);

        Naranjo5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Tahu", "Ya", "Tidak" }));
        Naranjo5.setName("Naranjo5"); // NOI18N
        Naranjo5.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Naranjo5ItemStateChanged(evt);
            }
        });
        Naranjo5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo5KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo5);
        Naranjo5.setBounds(780, 1090, 90, 23);

        NilaiNaranjo5.setEditable(false);
        NilaiNaranjo5.setFocusTraversalPolicyProvider(true);
        NilaiNaranjo5.setName("NilaiNaranjo5"); // NOI18N
        FormInput.add(NilaiNaranjo5);
        NilaiNaranjo5.setBounds(880, 1090, 40, 23);

        jLabel248.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel248.setText("6. Apakah efek samping obat muncul kembali ketika placebo diberikan ?");
        jLabel248.setName("jLabel248"); // NOI18N
        FormInput.add(jLabel248);
        jLabel248.setBounds(110, 1120, 670, 23);

        Naranjo6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Tahu", "Ya", "Tidak" }));
        Naranjo6.setName("Naranjo6"); // NOI18N
        Naranjo6.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Naranjo6ItemStateChanged(evt);
            }
        });
        Naranjo6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo6KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo6);
        Naranjo6.setBounds(780, 1120, 90, 23);

        NilaiNaranjo6.setEditable(false);
        NilaiNaranjo6.setFocusTraversalPolicyProvider(true);
        NilaiNaranjo6.setName("NilaiNaranjo6"); // NOI18N
        FormInput.add(NilaiNaranjo6);
        NilaiNaranjo6.setBounds(880, 1120, 40, 23);

        jLabel249.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel249.setText("7. Apakah Obat yang dicurigai terdeteksi didalam darah atau cairan tubuh lainnya dengan konsentrasi yang toksik ?");
        jLabel249.setName("jLabel249"); // NOI18N
        FormInput.add(jLabel249);
        jLabel249.setBounds(110, 1150, 670, 23);

        Naranjo7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Tahu", "Ya", "Tidak" }));
        Naranjo7.setName("Naranjo7"); // NOI18N
        Naranjo7.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Naranjo7ItemStateChanged(evt);
            }
        });
        Naranjo7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo7KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo7);
        Naranjo7.setBounds(780, 1150, 90, 23);

        NilaiNaranjo7.setEditable(false);
        NilaiNaranjo7.setFocusTraversalPolicyProvider(true);
        NilaiNaranjo7.setName("NilaiNaranjo7"); // NOI18N
        FormInput.add(NilaiNaranjo7);
        NilaiNaranjo7.setBounds(880, 1150, 40, 23);

        TingkatResiko1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        TingkatResiko1.setText("Kategori");
        TingkatResiko1.setToolTipText("");
        TingkatResiko1.setName("TingkatResiko1"); // NOI18N
        FormInput.add(TingkatResiko1);
        TingkatResiko1.setBounds(110, 1270, 90, 23);

        KategoriNaranjo.setEditable(false);
        KategoriNaranjo.setFocusTraversalPolicyProvider(true);
        KategoriNaranjo.setName("KategoriNaranjo"); // NOI18N
        FormInput.add(KategoriNaranjo);
        KategoriNaranjo.setBounds(190, 1270, 368, 23);

        jLabel256.setText("Total :");
        jLabel256.setName("jLabel256"); // NOI18N
        FormInput.add(jLabel256);
        jLabel256.setBounds(800, 1270, 70, 23);

        NilaiNaranjoTotal.setEditable(false);
        NilaiNaranjoTotal.setFocusTraversalPolicyProvider(true);
        NilaiNaranjoTotal.setName("NilaiNaranjoTotal"); // NOI18N
        FormInput.add(NilaiNaranjoTotal);
        NilaiNaranjoTotal.setBounds(880, 1270, 40, 23);

        jLabel250.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel250.setText("9. Apakah pasien pernah mengalami efek samping obat yang sama atau dengan obat yang mirip sebelumnya ?");
        jLabel250.setName("jLabel250"); // NOI18N
        FormInput.add(jLabel250);
        jLabel250.setBounds(110, 1210, 670, 23);

        Naranjo8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Tahu", "Ya", "Tidak" }));
        Naranjo8.setName("Naranjo8"); // NOI18N
        Naranjo8.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Naranjo8ItemStateChanged(evt);
            }
        });
        Naranjo8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo8KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo8);
        Naranjo8.setBounds(780, 1210, 90, 23);

        NilaiNaranjo9.setEditable(false);
        NilaiNaranjo9.setFocusTraversalPolicyProvider(true);
        NilaiNaranjo9.setName("NilaiNaranjo9"); // NOI18N
        FormInput.add(NilaiNaranjo9);
        NilaiNaranjo9.setBounds(880, 1210, 40, 23);

        Naranjo9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Tahu", "Ya", "Tidak" }));
        Naranjo9.setName("Naranjo9"); // NOI18N
        Naranjo9.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Naranjo9ItemStateChanged(evt);
            }
        });
        Naranjo9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo9KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo9);
        Naranjo9.setBounds(780, 1180, 90, 23);

        jLabel251.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel251.setText("8. Apakah efek samping obat bertambah parah ketika dosis obat ditingkatkan atau bertambah ringan ketika obat diturunkan dosisnya ?");
        jLabel251.setName("jLabel251"); // NOI18N
        FormInput.add(jLabel251);
        jLabel251.setBounds(110, 1180, 670, 23);

        NilaiNaranjo8.setEditable(false);
        NilaiNaranjo8.setFocusTraversalPolicyProvider(true);
        NilaiNaranjo8.setName("NilaiNaranjo8"); // NOI18N
        FormInput.add(NilaiNaranjo8);
        NilaiNaranjo8.setBounds(880, 1180, 40, 23);

        Naranjo10.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tidak Tahu", "Ya", "Tidak" }));
        Naranjo10.setName("Naranjo10"); // NOI18N
        Naranjo10.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Naranjo10ItemStateChanged(evt);
            }
        });
        Naranjo10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Naranjo10KeyPressed(evt);
            }
        });
        FormInput.add(Naranjo10);
        Naranjo10.setBounds(780, 1240, 90, 23);

        jLabel252.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel252.setText("10. Apakah efek samping obat dapat dikonfirmasi dengan bukti yang obyektif ?");
        jLabel252.setName("jLabel252"); // NOI18N
        FormInput.add(jLabel252);
        jLabel252.setBounds(110, 1240, 670, 23);

        NilaiNaranjo10.setEditable(false);
        NilaiNaranjo10.setFocusTraversalPolicyProvider(true);
        NilaiNaranjo10.setName("NilaiNaranjo10"); // NOI18N
        FormInput.add(NilaiNaranjo10);
        NilaiNaranjo10.setBounds(880, 1240, 40, 23);

        jLabel253.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel253.setText("ALGORITMA NARANJO");
        jLabel253.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel253.setName("jLabel253"); // NOI18N
        FormInput.add(jLabel253);
        jLabel253.setBounds(20, 940, 380, 23);

        jLabel69.setText("Pelapor:");
        jLabel69.setName("jLabel69"); // NOI18N
        FormInput.add(jLabel69);
        jLabel69.setBounds(0, 100, 70, 23);

        Profesi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Dokter", "Perawat/Bidan", "Farmasi" }));
        Profesi.setName("Profesi"); // NOI18N
        Profesi.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ProfesiItemStateChanged(evt);
            }
        });
        Profesi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProfesiActionPerformed(evt);
            }
        });
        Profesi.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ProfesiKeyPressed(evt);
            }
        });
        FormInput.add(Profesi);
        Profesi.setBounds(74, 100, 130, 23);

        label16.setText("Asal Ruangan/ Poliklinik :");
        label16.setName("label16"); // NOI18N
        label16.setPreferredSize(new java.awt.Dimension(60, 23));
        FormInput.add(label16);
        label16.setBounds(510, 100, 140, 23);

        kdRuangan.setName("kdRuangan"); // NOI18N
        kdRuangan.setPreferredSize(new java.awt.Dimension(80, 23));
        kdRuangan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdRuanganKeyPressed(evt);
            }
        });
        FormInput.add(kdRuangan);
        kdRuangan.setBounds(650, 100, 70, 23);

        nmruangan.setEditable(false);
        nmruangan.setName("nmruangan"); // NOI18N
        nmruangan.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmruangan);
        nmruangan.setBounds(720, 100, 170, 23);

        btnRuangan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnRuangan.setMnemonic('1');
        btnRuangan.setToolTipText("Alt+1");
        btnRuangan.setGlassColor(new java.awt.Color(255, 255, 255));
        btnRuangan.setName("btnRuangan"); // NOI18N
        btnRuangan.setPreferredSize(new java.awt.Dimension(28, 23));
        btnRuangan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRuanganActionPerformed(evt);
            }
        });
        FormInput.add(btnRuangan);
        btnRuangan.setBounds(900, 100, 28, 23);

        btnPegawai.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        btnPegawai.setMnemonic('2');
        btnPegawai.setToolTipText("Alt+2");
        btnPegawai.setGlassColor(new java.awt.Color(255, 255, 255));
        btnPegawai.setName("btnPegawai"); // NOI18N
        btnPegawai.setPreferredSize(new java.awt.Dimension(28, 23));
        btnPegawai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPegawaiActionPerformed(evt);
            }
        });
        FormInput.add(btnPegawai);
        btnPegawai.setBounds(490, 100, 28, 23);

        jLabel14.setText("Suku :");
        jLabel14.setName("jLabel14"); // NOI18N
        FormInput.add(jLabel14);
        jLabel14.setBounds(370, 40, 60, 23);

        Suku.setEditable(false);
        Suku.setHighlighter(null);
        Suku.setName("Suku"); // NOI18N
        FormInput.add(Suku);
        Suku.setBounds(435, 40, 150, 23);

        jLabel27.setText("Umur :");
        jLabel27.setName("jLabel27"); // NOI18N
        FormInput.add(jLabel27);
        jLabel27.setBounds(170, 40, 70, 23);

        Umur.setEditable(false);
        Umur.setHighlighter(null);
        Umur.setName("Umur"); // NOI18N
        FormInput.add(Umur);
        Umur.setBounds(245, 40, 80, 23);

        Jkn1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        Jkn1.setName("Jkn1"); // NOI18N
        Jkn1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jkn1KeyPressed(evt);
            }
        });
        FormInput.add(Jkn1);
        Jkn1.setBounds(385, 590, 70, 23);

        Jkn2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        Jkn2.setName("Jkn2"); // NOI18N
        Jkn2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jkn2KeyPressed(evt);
            }
        });
        FormInput.add(Jkn2);
        Jkn2.setBounds(385, 620, 70, 23);

        Jkn3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        Jkn3.setName("Jkn3"); // NOI18N
        Jkn3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jkn3KeyPressed(evt);
            }
        });
        FormInput.add(Jkn3);
        Jkn3.setBounds(385, 650, 70, 23);

        Jkn4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        Jkn4.setName("Jkn4"); // NOI18N
        Jkn4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jkn4KeyPressed(evt);
            }
        });
        FormInput.add(Jkn4);
        Jkn4.setBounds(385, 680, 70, 23);

        Jkn5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        Jkn5.setName("Jkn5"); // NOI18N
        Jkn5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jkn5KeyPressed(evt);
            }
        });
        FormInput.add(Jkn5);
        Jkn5.setBounds(385, 710, 70, 23);

        Jkn6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        Jkn6.setName("Jkn6"); // NOI18N
        Jkn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Jkn6ActionPerformed(evt);
            }
        });
        Jkn6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jkn6KeyPressed(evt);
            }
        });
        FormInput.add(Jkn6);
        Jkn6.setBounds(385, 740, 70, 23);

        Jkn7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        Jkn7.setName("Jkn7"); // NOI18N
        Jkn7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jkn7KeyPressed(evt);
            }
        });
        FormInput.add(Jkn7);
        Jkn7.setBounds(385, 770, 70, 23);

        Jkn8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        Jkn8.setName("Jkn8"); // NOI18N
        Jkn8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jkn8KeyPressed(evt);
            }
        });
        FormInput.add(Jkn8);
        Jkn8.setBounds(385, 800, 70, 23);

        Jkn9.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "-", "Ya", "Tidak" }));
        Jkn9.setName("Jkn9"); // NOI18N
        Jkn9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Jkn9KeyPressed(evt);
            }
        });
        FormInput.add(Jkn9);
        Jkn9.setBounds(385, 830, 70, 23);

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setText("SEDIAN/BENTUK");
        jLabel70.setName("jLabel70"); // NOI18N
        FormInput.add(jLabel70);
        jLabel70.setBounds(273, 560, 110, 23);

        jLabel15.setText("No. Laporan :");
        jLabel15.setName("jLabel15"); // NOI18N
        FormInput.add(jLabel15);
        jLabel15.setBounds(580, 40, 110, 23);

        NoSurat.setEditable(false);
        NoSurat.setHighlighter(null);
        NoSurat.setName("NoSurat"); // NOI18N
        NoSurat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NoSuratActionPerformed(evt);
            }
        });
        NoSurat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NoSuratKeyPressed(evt);
            }
        });
        FormInput.add(NoSurat);
        NoSurat.setBounds(690, 40, 170, 23);

        TglLaporan.setForeground(new java.awt.Color(50, 70, 50));
        TglLaporan.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "18-11-2024" }));
        TglLaporan.setDisplayFormat("dd-MM-yyyy");
        TglLaporan.setName("TglLaporan"); // NOI18N
        TglLaporan.setOpaque(false);
        TglLaporan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TglLaporanKeyPressed(evt);
            }
        });
        FormInput.add(TglLaporan);
        TglLaporan.setBounds(700, 10, 90, 23);

        scrollInput.setViewportView(FormInput);

        internalFrame2.add(scrollInput, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Input Monitoring", internalFrame2);

        internalFrame3.setBorder(null);
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);
        Scroll.setPreferredSize(new java.awt.Dimension(452, 200));

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(jPopupMenu1);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        internalFrame3.add(Scroll, java.awt.BorderLayout.CENTER);

        panelGlass9.setName("panelGlass9"); // NOI18N
        panelGlass9.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel19.setText("Tgl.Asuhan :");
        jLabel19.setName("jLabel19"); // NOI18N
        jLabel19.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(jLabel19);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari1);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("s.d.");
        jLabel21.setName("jLabel21"); // NOI18N
        jLabel21.setPreferredSize(new java.awt.Dimension(23, 23));
        panelGlass9.add(jLabel21);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "15-11-2024" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(90, 23));
        panelGlass9.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(80, 23));
        panelGlass9.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(195, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass9.add(TCari);

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
        panelGlass9.add(BtnCari);

        jLabel7.setText("Record :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass9.add(jLabel7);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(70, 23));
        panelGlass9.add(LCount);

        internalFrame3.add(panelGlass9, java.awt.BorderLayout.PAGE_END);

        TabRawat.addTab("Data Monitoring", internalFrame3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPegawai.getText().trim().equals("")){
            Valid.textKosong(btnPegawai,"Pegawai");
        }else if(nmruangan.getText().trim().equals("")){
            Valid.textKosong(btnRuangan,"Ruangan");
        }else if(Bentuk_Manifestasi.getText().trim().equals("")){
            Valid.textKosong(Bentuk_Manifestasi,"Bentuk Manifestasi ESO");
        }else if(Masalah_Kualitas.getText().trim().equals("")){
            Valid.textKosong(Masalah_Kualitas,"Masalah Kualitas Obat");
        }else if(Riwayat_eso.getText().trim().equals("")){
            Valid.textKosong(Riwayat_eso,"Riwayat ESO");
        }else if(Obat1.getText().trim().equals("")){
            Valid.textKosong(Obat1,"Obat 1");
        }else if(Sedian1.getText().trim().equals("")){
            Valid.textKosong(Sedian1,"Sedian 1");
        }else if(Batch1.getText().trim().equals("")){
            Valid.textKosong(Batch1,"Batch 1");
        }else if(Cara1.getText().trim().equals("")){
            Valid.textKosong(Cara1,"Cara 1");
        }else if(Dosis1.getText().trim().equals("")){
            Valid.textKosong(Dosis1,"Dosis 1");
        }else if(Indikasi1.getText().trim().equals("")){
            Valid.textKosong(Indikasi1,"Indikasi 1");
        }else{
            JTextField[][] fields = {
            {Obat2, Sedian2, Batch2, Cara2, Dosis2, Indikasi2},
            {Obat3, Sedian3, Batch3, Cara3, Dosis3, Indikasi3},
            {Obat4, Sedian4, Batch4, Cara4, Dosis4, Indikasi4},
            {Obat5, Sedian5, Batch5, Cara5, Dosis5, Indikasi5},
            {Obat6, Sedian6, Batch6, Cara6, Dosis6, Indikasi6},
            {Obat7, Sedian7, Batch7, Cara7, Dosis7, Indikasi7},
            {Obat8, Sedian8, Batch8, Cara8, Dosis8, Indikasi8},
            {Obat9, Sedian9, Batch9, Cara9, Dosis9, Indikasi9},
            {Obat10, Sedian10, Batch10, Cara10, Dosis10, Indikasi10},
        };

        // Set default nilai "-" untuk kolom kosong
        for (JTextField[] group : fields) {
            for (JTextField field : group) {
                if (field.getText().trim().equals("")) {
                    field.setText("-");
                }
            }
        }

            if(Sequel.menyimpantf("monitoring_efek_samping_obat","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",131,new String[]{
                    TNoRw.getText(),NoSurat.getText(),Valid.SetTgl(TglLaporan.getSelectedItem()+""),Profesi.getSelectedItem().toString(),KdPegawai.getText(),kdRuangan.getText(),BB.getText(),
                    Hamil.getSelectedItem().toString(),Kesudahan.getSelectedItem().toString(),PenyakitLain.getSelectedItem().toString(),Penyakit_Utama.getText(),Valid.SetTgl(TanngalMulaiTerjadi.getSelectedItem()+""),Bentuk_Manifestasi.getText(),
                    Masalah_Kualitas.getText(),Riwayat_eso.getText(),Valid.SetTgl(TanggaKesudahan.getSelectedItem()+""),HasilKesudahan.getSelectedItem().toString(),
                    Obat1.getText(),Sedian1.getText(),Jkn1.getSelectedItem().toString(),Batch1.getText(),Cara1.getText(),Dosis1.getText(),Valid.SetTgl(TanggalMulai1.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir1.getSelectedItem()+""),Indikasi1.getText(),
                    Obat2.getText(),Sedian2.getText(),Jkn2.getSelectedItem().toString(),Batch2.getText(),Cara2.getText(),Dosis2.getText(),Valid.SetTgl(TanggalMulai2.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir2.getSelectedItem()+""),Indikasi2.getText(),
                    Obat3.getText(),Sedian3.getText(),Jkn3.getSelectedItem().toString(),Batch3.getText(),Cara3.getText(),Dosis3.getText(),Valid.SetTgl(TanggalMulai3.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir3.getSelectedItem()+""),Indikasi3.getText(),
                    Obat4.getText(),Sedian4.getText(),Jkn4.getSelectedItem().toString(),Batch4.getText(),Cara4.getText(),Dosis4.getText(),Valid.SetTgl(TanggalMulai4.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir4.getSelectedItem()+""),Indikasi4.getText(),
                    Obat5.getText(),Sedian5.getText(),Jkn5.getSelectedItem().toString(),Batch5.getText(),Cara5.getText(),Dosis5.getText(),Valid.SetTgl(TanggalMulai5.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir5.getSelectedItem()+""),Indikasi5.getText(),
                    Obat6.getText(),Sedian6.getText(),Jkn6.getSelectedItem().toString(),Batch6.getText(),Cara6.getText(),Dosis6.getText(),Valid.SetTgl(TanggalMulai6.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir6.getSelectedItem()+""),Indikasi6.getText(),
                    Obat7.getText(),Sedian7.getText(),Jkn7.getSelectedItem().toString(),Batch7.getText(),Cara7.getText(),Dosis7.getText(),Valid.SetTgl(TanggalMulai7.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir7.getSelectedItem()+""),Indikasi7.getText(),
                    Obat8.getText(),Sedian8.getText(),Jkn8.getSelectedItem().toString(),Batch8.getText(),Cara8.getText(),Dosis8.getText(),Valid.SetTgl(TanggalMulai8.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir8.getSelectedItem()+""),Indikasi8.getText(),
                    Obat9.getText(),Sedian9.getText(),Jkn9.getSelectedItem().toString(),Batch9.getText(),Cara9.getText(),Dosis9.getText(),Valid.SetTgl(TanggalMulai9.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir9.getSelectedItem()+""),Indikasi9.getText(),
                    Obat10.getText(),Sedian10.getText(),Jkn10.getSelectedItem().toString(),Batch10.getText(),Cara10.getText(),Dosis10.getText(),Valid.SetTgl(TanggalMulai10.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir10.getSelectedItem()+""),Indikasi10.getText(),
                    ReaksiSetelah.getSelectedItem().toString(),ReaksiSama.getSelectedItem().toString(),Naranjo1.getSelectedItem().toString(),NilaiNaranjo1.getText(),Naranjo2.getSelectedItem().toString(),NilaiNaranjo2.getText(),Naranjo3.getSelectedItem().toString(),NilaiNaranjo3.getText(),
                    Naranjo4.getSelectedItem().toString(),NilaiNaranjo4.getText(),Naranjo5.getSelectedItem().toString(),NilaiNaranjo5.getText(),Naranjo6.getSelectedItem().toString(),NilaiNaranjo6.getText(),Naranjo7.getSelectedItem().toString(),NilaiNaranjo7.getText(),
                    Naranjo8.getSelectedItem().toString(),NilaiNaranjo8.getText(),Naranjo9.getSelectedItem().toString(),NilaiNaranjo9.getText(),Naranjo10.getSelectedItem().toString(),NilaiNaranjo10.getText(),NilaiNaranjoTotal.getText(),KategoriNaranjo.getText() 
                })==true){
                    tampil();
                    emptTeks();
            }
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,KategoriNaranjo,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        emptTeks();
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObat.getSelectedRow()>-1){
            if(Sequel.queryu2tf("delete from monitoring_efek_samping_obat where no_rawat=?",1,new String[]{
                tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
            })==true){
                tampil();
                emptTeks();
            }else{
                JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
            }
        }else{
            JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
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
        if(TNoRM.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"Nama Pasien");
        }else if(NmPegawai.getText().trim().equals("")){
            Valid.textKosong(btnPegawai,"Pegawai");
        }else if(nmruangan.getText().trim().equals("")){
            Valid.textKosong(btnRuangan,"Ruangan");
        }else if(Bentuk_Manifestasi.getText().trim().equals("")){
            Valid.textKosong(Bentuk_Manifestasi,"Bentuk Manifestasi ESO");
        }else if(Masalah_Kualitas.getText().trim().equals("")){
            Valid.textKosong(Masalah_Kualitas,"Masalah Kualitas Obat");
        }else if(Riwayat_eso.getText().trim().equals("")){
            Valid.textKosong(Riwayat_eso,"Riwayat ESO");
        }else{
            if(tbObat.getSelectedRow()>-1){
                if(Sequel.mengedittf("monitoring_efek_samping_obat","no_rawat=?","no_rawat=?,no_laporan=?,tanggal=?,profesi=?,nik=?,kd_bangsal=?,berat_badan=?,pasien_hamil=?,kesudahan=?,penyakit_lain=?,penyakit_utama=?,tanggal_kejadian=?,manifestasi=?,masalah_kualitas=?,riwayat_eso=?,tanggal_kesudahan=?,hasil_kesudahan=?,obat1=?,sedian1=?,obat_jkn1=?,batch1=?,cara1=?,dosis1=?,tanggal_mulai1=?,tanggal_akhir1=?,indikasi1=?,obat2=?,sedian2=?,obat_jkn2=?,batch2=?,cara2=?,dosis2=?,tanggal_mulai2=?,tanggal_akhir2=?,indikasi2=?,obat3=?,sedian3=?,obat_jkn3=?,batch3=?,cara3=?,dosis3=?,tanggal_mulai3=?,tanggal_akhir3=?,indikasi3=?,obat4=?,sedian4=?,obat_jkn4=?,batch4=?,cara4=?,dosis4=?,tanggal_mulai4=?,tanggal_akhir4=?,indikasi4=?,obat5=?,sedian5=?,obat_jkn5=?,batch5=?,cara5=?,dosis5=?,tanggal_mulai5=?,tanggal_akhir5=?,indikasi5=?,obat6=?,sedian6=?,obat_jkn6=?,batch6=?,cara6=?,dosis6=?,tanggal_mulai6=?,tanggal_akhir6=?,indikasi6=?,obat7=?,sedian7=?,obat_jkn7=?,batch7=?,cara7=?,dosis7=?,tanggal_mulai7=?,tanggal_akhir7=?,indikasi7=?,obat8=?,sedian8=?,obat_jkn8=?,batch8=?,cara8=?,dosis8=?,tanggal_mulai8=?,tanggal_akhir8=?,indikasi8=?,obat9=?,sedian9=?,obat_jkn9=?,batch9=?,cara9=?,dosis9=?,tanggal_mulai9=?,tanggal_akhir9=?,indikasi9=?,obat10=?,sedian10=?,obat_jkn10=?,batch10=?,cara10=?,dosis10=?,tanggal_mulai10=?,tanggal_akhir10=?,indikasi10=?,reaksi_setelah=?,reaksi_sama=?,naranjo1=?,nilai_naranjo1=?,naranjo2=?,nilai_naranjo2=?,naranjo3=?,nilai_naranjo3=?,naranjo4=?,nilai_naranjo4=?,naranjo5=?,nilai_naranjo5=?,naranjo6=?,nilai_naranjo6=?,naranjo7=?,nilai_naranjo7=?,naranjo8=?,nilai_naranjo8=?,naranjo9=?,nilai_naranjo9=?,naranjo10=?,nilai_naranjo10=?,total_nilai_naranjo=?,kategori_naranjo=?",132,new String[]{
                    TNoRw.getText(),NoSurat.getText(),Valid.SetTgl(TglLaporan.getSelectedItem()+""),Profesi.getSelectedItem().toString(),KdPegawai.getText(),kdRuangan.getText(),BB.getText(),
                    Hamil.getSelectedItem().toString(),Kesudahan.getSelectedItem().toString(),PenyakitLain.getSelectedItem().toString(),Penyakit_Utama.getText(),Valid.SetTgl(TanngalMulaiTerjadi.getSelectedItem()+""),Bentuk_Manifestasi.getText(),
                    Masalah_Kualitas.getText(),Riwayat_eso.getText(),Valid.SetTgl(TanggaKesudahan.getSelectedItem()+""),HasilKesudahan.getSelectedItem().toString(),
                    Obat1.getText(),Sedian1.getText(),Jkn1.getSelectedItem().toString(),Batch1.getText(),Cara1.getText(),Dosis1.getText(),Valid.SetTgl(TanggalMulai1.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir1.getSelectedItem()+""),Indikasi1.getText(),
                    Obat2.getText(),Sedian2.getText(),Jkn2.getSelectedItem().toString(),Batch2.getText(),Cara2.getText(),Dosis2.getText(),Valid.SetTgl(TanggalMulai2.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir2.getSelectedItem()+""),Indikasi2.getText(),
                    Obat3.getText(),Sedian3.getText(),Jkn3.getSelectedItem().toString(),Batch3.getText(),Cara3.getText(),Dosis3.getText(),Valid.SetTgl(TanggalMulai3.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir3.getSelectedItem()+""),Indikasi3.getText(),
                    Obat4.getText(),Sedian4.getText(),Jkn4.getSelectedItem().toString(),Batch4.getText(),Cara4.getText(),Dosis4.getText(),Valid.SetTgl(TanggalMulai4.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir4.getSelectedItem()+""),Indikasi4.getText(),
                    Obat5.getText(),Sedian5.getText(),Jkn5.getSelectedItem().toString(),Batch5.getText(),Cara5.getText(),Dosis5.getText(),Valid.SetTgl(TanggalMulai5.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir5.getSelectedItem()+""),Indikasi5.getText(),
                    Obat6.getText(),Sedian6.getText(),Jkn6.getSelectedItem().toString(),Batch6.getText(),Cara6.getText(),Dosis6.getText(),Valid.SetTgl(TanggalMulai6.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir6.getSelectedItem()+""),Indikasi6.getText(),
                    Obat7.getText(),Sedian7.getText(),Jkn7.getSelectedItem().toString(),Batch7.getText(),Cara7.getText(),Dosis7.getText(),Valid.SetTgl(TanggalMulai7.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir7.getSelectedItem()+""),Indikasi7.getText(),
                    Obat8.getText(),Sedian8.getText(),Jkn8.getSelectedItem().toString(),Batch8.getText(),Cara8.getText(),Dosis8.getText(),Valid.SetTgl(TanggalMulai8.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir8.getSelectedItem()+""),Indikasi8.getText(),
                    Obat9.getText(),Sedian9.getText(),Jkn9.getSelectedItem().toString(),Batch9.getText(),Cara9.getText(),Dosis9.getText(),Valid.SetTgl(TanggalMulai9.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir9.getSelectedItem()+""),Indikasi9.getText(),
                    Obat10.getText(),Sedian10.getText(),Jkn10.getSelectedItem().toString(),Batch10.getText(),Cara10.getText(),Dosis10.getText(),Valid.SetTgl(TanggalMulai10.getSelectedItem()+""),Valid.SetTgl(TanggalAkhir10.getSelectedItem()+""),Indikasi10.getText(),
                    ReaksiSetelah.getSelectedItem().toString(),ReaksiSama.getSelectedItem().toString(),Naranjo1.getSelectedItem().toString(),NilaiNaranjo1.getText(),Naranjo2.getSelectedItem().toString(),NilaiNaranjo2.getText(),Naranjo3.getSelectedItem().toString(),NilaiNaranjo3.getText(),
                    Naranjo4.getSelectedItem().toString(),NilaiNaranjo4.getText(),Naranjo5.getSelectedItem().toString(),NilaiNaranjo5.getText(),Naranjo6.getSelectedItem().toString(),NilaiNaranjo6.getText(),Naranjo7.getSelectedItem().toString(),NilaiNaranjo7.getText(),
                    Naranjo8.getSelectedItem().toString(),NilaiNaranjo8.getText(),Naranjo9.getSelectedItem().toString(),NilaiNaranjo9.getText(),Naranjo10.getSelectedItem().toString(),NilaiNaranjo10.getText(),NilaiNaranjoTotal.getText(),KategoriNaranjo.getText(),
                    TNoRw.getText()
                    })==true){
                       tampil();
                       emptTeks();
                }
            }else{
                JOptionPane.showMessageDialog(rootPane,"Silahkan anda pilih data terlebih dahulu..!!");
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
//        bangsal.dispose();
//        pegawai.dispose();
//        cariobatdosis.dispose();
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnEdit,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            try{
                htmlContent = new StringBuilder();
                htmlContent.append(                             
                    "<tr class='isi'>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='105px'><b>No.Rawat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='70px'><b>No.RM</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Nama Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='65px'><b>Tgl.Lahir</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='55px'><b>J.K.</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Umur</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Pekerjaan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='115px'><b>Suku</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>No Laporan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Tanggal Laporan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Profesi Pelapor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Kode Pelapor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Nama Pelapor</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Kode Ruangan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Nama Ruangan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='120px'><b>Berat Badan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>Kehamilan Pasien</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='50px'><b>Kesudahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Penyakit Lain</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='60px'><b>Penyakit Utama</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='75px'><b>Tanggal Mulai Terjadi</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='67px'><b>Bentuk Manifestasi ESO</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>Masalah Kulaitas Obat</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>Riwayat Eso</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>Tanggal Kesudahan</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='40px'><b>Kesudahan Eso</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Obat 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Sedian 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Jkn 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Batch 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Cara 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Dosis 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Mulai 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Akhir 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Indikasi 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Obat 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Sedian 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Jkn 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Batch 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Cara 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Dosis 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Mulai 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Akhir 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Indikasi 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Obat 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Sedian 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Jkn 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Batch 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Cara 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Dosis 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Mulai 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Akhir 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Indikasi 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Obat 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Sedian 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Jkn 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Batch 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Cara 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Dosis 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Mulai 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Akhir 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Indikasi 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Obat 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Sedian 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Jkn 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Batch 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Cara 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Dosis 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Mulai 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Akhir 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Indikasi 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Obat 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Sedian 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Jkn 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Batch 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Cara 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Dosis 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Mulai 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Akhir 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Indikasi 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Obat 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Sedian 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Jkn 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Batch 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Cara 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Dosis 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Mulai 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Akhir 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Indikasi 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Obat 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Sedian 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Jkn 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Batch 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Cara 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Dosis 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Mulai 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Tanggal Akhir 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='80px'><b>Indikasi 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>Obat 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>Sedian 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>Jkn 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>Batch 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>Cara 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>Dosis 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>Tanggal Mulai 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>Tanggal Akhir 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='90px'><b>Indikasi 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Obat 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Sedian 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Jkn 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Batch 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Cara 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Dosis 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Tanggal Mulai 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Tanggal Akhir 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Indikasi 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Naranjo 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Nilai Naranjo 1</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='200px'><b>Naranjo 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Nilai Naranjo 2</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Naranjo 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Nilai Naranjo 3</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Naranjo 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Nilai Naranjo 4</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Naranjo 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Nilai Naranjo 5</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='100px'><b>Naranjo 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Nilai Naranjo 6</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='200px'><b>Naranjo 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Nilai Naranjo 7</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Naranjo 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='170px'><b>Nilai Naranjo 8</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Naranjo 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Nilai Naranjo 9</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Naranjo 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Nilai Naranjo 10</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='150px'><b>Total Nilai Naranjo</b></td>"+
                        "<td valign='middle' bgcolor='#FFFAFA' align='center' width='300px'><b>Kategoi Naranjo</b></td>"+
                    "</tr>"
                );
                for (i = 0; i < tabMode.getRowCount(); i++) {
                    htmlContent.append(
                        "<tr class='isi'>"+
                           "<td valign='top'>"+tbObat.getValueAt(i,0).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,1).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,2).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,3).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,4).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,5).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,6).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,7).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,8).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,9).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,10).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,11).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,12).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,13).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,14).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,15).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,16).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,17).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,18).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,19).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,20).toString()+"</td>"+ 
                            "<td valign='top'>"+tbObat.getValueAt(i,21).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,22).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,23).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,24).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,25).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,26).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,27).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,28).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,29).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,30).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,31).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,32).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,33).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,34).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,35).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,36).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,37).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,38).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,39).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,40).toString()+"</td>"+      
                            "<td valign='top'>"+tbObat.getValueAt(i,41).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,42).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,43).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,44).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,45).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,46).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,47).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,48).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,49).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,50).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,51).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,52).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,53).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,54).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,55).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,56).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,57).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,58).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,59).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,60).toString()+"</td>"+ 
                            "<td valign='top'>"+tbObat.getValueAt(i,61).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,62).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,63).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,64).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,65).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,66).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,67).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,68).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,69).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,70).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,71).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,72).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,73).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,74).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,75).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,76).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,77).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,78).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,79).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,80).toString()+"</td>"+       
                            "<td valign='top'>"+tbObat.getValueAt(i,81).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,82).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,83).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,84).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,85).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,86).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,87).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,88).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,89).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,90).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,91).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,92).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,93).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,94).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,95).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,96).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,97).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,98).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,99).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,100).toString()+"</td>"+ 
                            "<td valign='top'>"+tbObat.getValueAt(i,101).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,102).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,103).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,104).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,105).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,106).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,107).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,108).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,109).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,110).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,111).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,112).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,113).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,114).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,115).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,116).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,117).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,118).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,119).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,120).toString()+"</td>"+   
                            "<td valign='top'>"+tbObat.getValueAt(i,121).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,122).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,123).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,124).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,125).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,126).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,127).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,128).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,129).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,130).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,131).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,132).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,133).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,134).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,135).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,136).toString()+"</td>"+
                            "<td valign='top'>"+tbObat.getValueAt(i,137).toString()+"</td>"+
                        "</tr>");
                }
                LoadHTML.setText(
                    "<html>"+
                      "<table width='4600px' border='0' align='center' cellpadding='1px' cellspacing='0' class='tbl_form'>"+
                       htmlContent.toString()+
                      "</table>"+
                    "</html>"
                );

                File g = new File("file2.css");            
                BufferedWriter bg = new BufferedWriter(new FileWriter(g));
                bg.write(
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
                bg.close();

                File f = new File("DataMonitoringEfekSampingObat.html");            
                BufferedWriter bw = new BufferedWriter(new FileWriter(f));            
                bw.write(LoadHTML.getText().replaceAll("<head>","<head>"+
                            "<link href=\"file2.css\" rel=\"stylesheet\" type=\"text/css\" />"+
                            "<table width='4600px' border='0' align='center' cellpadding='3px' cellspacing='0' class='tbl_form'>"+
                                "<tr class='isi2'>"+
                                    "<td valign='top' align='center'>"+
                                        "<font size='4' face='Tahoma'>"+akses.getnamars()+"</font><br>"+
                                        akses.getalamatrs()+", "+akses.getkabupatenrs()+", "+akses.getpropinsirs()+"<br>"+
                                        akses.getkontakrs()+", E-mail : "+akses.getemailrs()+"<br><br>"+
                                        "<font size='2' face='Tahoma'>DATA MONITORING EFEK SAMPING OBAT<br><br></font>"+        
                                    "</td>"+
                               "</tr>"+
                            "</table>")
                );
                bw.close();                         
                Desktop.getDesktop().browse(f.toURI());
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnEdit, BtnKeluar);
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
            TCari.setText("");
            tampil();
        }else{
            Valid.pindah(evt, BtnCari, TPasien);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if((evt.getClickCount()==2)&&(tbObat.getSelectedColumn()==0)){
                TabRawat.setSelectedIndex(0);
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                try {
                    getData();
                    TabRawat.setSelectedIndex(0);
                } catch (java.lang.NullPointerException e) {
                }
            }
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void MnMonitoringEsoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnMonitoringEsoActionPerformed
        if(tbObat.getSelectedRow()>-1){
            Map<String, Object> param = new HashMap<>();
            param.put("namars",akses.getnamars());
            param.put("alamatrs",akses.getalamatrs());
            param.put("kotars",akses.getkabupatenrs());
            param.put("propinsirs",akses.getpropinsirs());
            param.put("kontakrs",akses.getkontakrs());
            param.put("emailrs",akses.getemailrs());          
            param.put("logo",Sequel.cariGambar("select setting.logo from setting")); 
            finger=Sequel.cariIsi("select sha1(sidikjari.sidikjari) from sidikjari inner join pegawai on pegawai.id=sidikjari.id where pegawai.nik=?",tbObat.getValueAt(tbObat.getSelectedRow(),11).toString());
            param.put("finger","Dikeluarkan di "+akses.getnamars()+", Kabupaten/Kota "+akses.getkabupatenrs()+"\nDitandatangani secara elektronik oleh "+tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()+"\nID "+(finger.equals("")?tbObat.getValueAt(tbObat.getSelectedRow(),11).toString():finger)+"\n"+Valid.SetTgl3(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString())); 
            
            Valid.MyReportqry("rptCetakMonitoringEfekSampingObat.jasper","report","::[ Laporan Monitoring Efek Samping Obat ]::",
                        "select monitoring_efek_samping_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,pasien.umur,pasien.pekerjaan,pasien.suku_bangsa,suku_bangsa.nama_suku_bangsa,monitoring_efek_samping_obat.no_laporan,monitoring_efek_samping_obat.tanggal,monitoring_efek_samping_obat.profesi,monitoring_efek_samping_obat.nik,pegawai.nama,monitoring_efek_samping_obat.kd_bangsal,"+
                        "bangsal.nm_bangsal,monitoring_efek_samping_obat.berat_badan,monitoring_efek_samping_obat.pasien_hamil,monitoring_efek_samping_obat.kesudahan,monitoring_efek_samping_obat.penyakit_lain,monitoring_efek_samping_obat.penyakit_utama,monitoring_efek_samping_obat.tanggal_kejadian,monitoring_efek_samping_obat.manifestasi,monitoring_efek_samping_obat.masalah_kualitas,"+       
                        "monitoring_efek_samping_obat.riwayat_eso,monitoring_efek_samping_obat.tanggal_kesudahan,monitoring_efek_samping_obat.hasil_kesudahan,"+
                        "monitoring_efek_samping_obat.obat1,monitoring_efek_samping_obat.sedian1,monitoring_efek_samping_obat.obat_jkn1,monitoring_efek_samping_obat.batch1,monitoring_efek_samping_obat.cara1,monitoring_efek_samping_obat.dosis1,monitoring_efek_samping_obat.tanggal_mulai1,monitoring_efek_samping_obat.tanggal_akhir1,monitoring_efek_samping_obat.indikasi1,"+
                        "monitoring_efek_samping_obat.obat2,monitoring_efek_samping_obat.sedian2,monitoring_efek_samping_obat.obat_jkn2,monitoring_efek_samping_obat.batch2,monitoring_efek_samping_obat.cara2,monitoring_efek_samping_obat.dosis2,monitoring_efek_samping_obat.tanggal_mulai2,monitoring_efek_samping_obat.tanggal_akhir2,monitoring_efek_samping_obat.indikasi2,"+  
                        "monitoring_efek_samping_obat.obat3,monitoring_efek_samping_obat.sedian3,monitoring_efek_samping_obat.obat_jkn3,monitoring_efek_samping_obat.batch3,monitoring_efek_samping_obat.cara3,monitoring_efek_samping_obat.dosis3,monitoring_efek_samping_obat.tanggal_mulai3,monitoring_efek_samping_obat.tanggal_akhir3,monitoring_efek_samping_obat.indikasi3,"+
                        "monitoring_efek_samping_obat.obat4,monitoring_efek_samping_obat.sedian4,monitoring_efek_samping_obat.obat_jkn4,monitoring_efek_samping_obat.batch4,monitoring_efek_samping_obat.cara4,monitoring_efek_samping_obat.dosis4,monitoring_efek_samping_obat.tanggal_mulai4,monitoring_efek_samping_obat.tanggal_akhir4,monitoring_efek_samping_obat.indikasi4,"+
                        "monitoring_efek_samping_obat.obat5,monitoring_efek_samping_obat.sedian5,monitoring_efek_samping_obat.obat_jkn5,monitoring_efek_samping_obat.batch5,monitoring_efek_samping_obat.cara5,monitoring_efek_samping_obat.dosis5,monitoring_efek_samping_obat.tanggal_mulai5,monitoring_efek_samping_obat.tanggal_akhir5,monitoring_efek_samping_obat.indikasi5,"+
                        "monitoring_efek_samping_obat.obat6,monitoring_efek_samping_obat.sedian6,monitoring_efek_samping_obat.obat_jkn6,monitoring_efek_samping_obat.batch6,monitoring_efek_samping_obat.cara6,monitoring_efek_samping_obat.dosis6,monitoring_efek_samping_obat.tanggal_mulai6,monitoring_efek_samping_obat.tanggal_akhir6,monitoring_efek_samping_obat.indikasi6,"+
                        "monitoring_efek_samping_obat.obat7,monitoring_efek_samping_obat.sedian7,monitoring_efek_samping_obat.obat_jkn7,monitoring_efek_samping_obat.batch7,monitoring_efek_samping_obat.cara7,monitoring_efek_samping_obat.dosis7,monitoring_efek_samping_obat.tanggal_mulai7,monitoring_efek_samping_obat.tanggal_akhir7,monitoring_efek_samping_obat.indikasi7,"+
                        "monitoring_efek_samping_obat.obat8,monitoring_efek_samping_obat.sedian8,monitoring_efek_samping_obat.obat_jkn8,monitoring_efek_samping_obat.batch8,monitoring_efek_samping_obat.cara8,monitoring_efek_samping_obat.dosis8,monitoring_efek_samping_obat.tanggal_mulai8,monitoring_efek_samping_obat.tanggal_akhir8,monitoring_efek_samping_obat.indikasi8,"+
                        "monitoring_efek_samping_obat.obat9,monitoring_efek_samping_obat.sedian9,monitoring_efek_samping_obat.obat_jkn9,monitoring_efek_samping_obat.batch9,monitoring_efek_samping_obat.cara9,monitoring_efek_samping_obat.dosis9,monitoring_efek_samping_obat.tanggal_mulai9,monitoring_efek_samping_obat.tanggal_akhir9,monitoring_efek_samping_obat.indikasi9,"+
                        "monitoring_efek_samping_obat.obat10,monitoring_efek_samping_obat.sedian10,monitoring_efek_samping_obat.obat_jkn10,monitoring_efek_samping_obat.batch10,monitoring_efek_samping_obat.cara10,monitoring_efek_samping_obat.dosis10,monitoring_efek_samping_obat.tanggal_mulai10,monitoring_efek_samping_obat.tanggal_akhir10,monitoring_efek_samping_obat.indikasi10,"+
                        "monitoring_efek_samping_obat.reaksi_setelah,monitoring_efek_samping_obat.reaksi_sama,monitoring_efek_samping_obat.naranjo1,monitoring_efek_samping_obat.nilai_naranjo1,monitoring_efek_samping_obat.naranjo2,monitoring_efek_samping_obat.nilai_naranjo2,monitoring_efek_samping_obat.naranjo3,monitoring_efek_samping_obat.nilai_naranjo3,monitoring_efek_samping_obat.naranjo4,"+
                        "monitoring_efek_samping_obat.nilai_naranjo4,monitoring_efek_samping_obat.naranjo5,monitoring_efek_samping_obat.nilai_naranjo5,monitoring_efek_samping_obat.naranjo6,monitoring_efek_samping_obat.nilai_naranjo6,monitoring_efek_samping_obat.naranjo7,monitoring_efek_samping_obat.nilai_naranjo7,monitoring_efek_samping_obat.naranjo8,monitoring_efek_samping_obat.nilai_naranjo8,"+        
                        "monitoring_efek_samping_obat.naranjo9,monitoring_efek_samping_obat.nilai_naranjo9,monitoring_efek_samping_obat.naranjo10,monitoring_efek_samping_obat.nilai_naranjo10,monitoring_efek_samping_obat.total_nilai_naranjo,monitoring_efek_samping_obat.kategori_naranjo "+     
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join monitoring_efek_samping_obat on reg_periksa.no_rawat=monitoring_efek_samping_obat.no_rawat "+
                        "inner join pegawai on monitoring_efek_samping_obat.nik=pegawai.nik "+
                        "inner join bangsal on monitoring_efek_samping_obat.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join suku_bangsa on suku_bangsa.id=pasien.suku_bangsa "+
                        "where monitoring_efek_samping_obat.no_rawat='"+tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()+"'",param);
        }
        
    }//GEN-LAST:event_MnMonitoringEsoActionPerformed

    private void ProfesiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ProfesiKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProfesiKeyPressed

    private void ProfesiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProfesiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ProfesiActionPerformed

    private void ProfesiItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ProfesiItemStateChanged
       // TODO add your handling code here:
    }//GEN-LAST:event_ProfesiItemStateChanged

    private void Naranjo10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Naranjo10KeyPressed

    private void Naranjo10ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Naranjo10ItemStateChanged
        if(Naranjo10.getSelectedIndex()==0){
            NilaiNaranjo10.setText("0");
        }else if(Naranjo10.getSelectedIndex()==1){
            NilaiNaranjo10.setText("1");
        }else{
            NilaiNaranjo10.setText("0");
        }
        calculateTotalNaranjo();
        updateKategoriNaranjo();
    }//GEN-LAST:event_Naranjo10ItemStateChanged

    private void Naranjo9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Naranjo9KeyPressed

    private void Naranjo9ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Naranjo9ItemStateChanged
        if(Naranjo9.getSelectedIndex()==0){
            NilaiNaranjo9.setText("0");
        }else if(Naranjo9.getSelectedIndex()==1){
            NilaiNaranjo9.setText("1");
        }else{
            NilaiNaranjo9.setText("0");
        }
        calculateTotalNaranjo();
        updateKategoriNaranjo();
    }//GEN-LAST:event_Naranjo9ItemStateChanged

    private void Naranjo8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Naranjo8KeyPressed

    private void Naranjo8ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Naranjo8ItemStateChanged
        if(Naranjo8.getSelectedIndex()==0){
            NilaiNaranjo8.setText("0");
        }else if(Naranjo8.getSelectedIndex()==1){
            NilaiNaranjo8.setText("1");
        }else{
            NilaiNaranjo8.setText("0");
        }
        calculateTotalNaranjo();
        updateKategoriNaranjo();
    }//GEN-LAST:event_Naranjo8ItemStateChanged

    private void Naranjo7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo7KeyPressed
        //Valid.pindah(evt,SkalaResiko6,SkalaNIPS1);
    }//GEN-LAST:event_Naranjo7KeyPressed

    private void Naranjo7ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Naranjo7ItemStateChanged
        if(Naranjo7.getSelectedIndex()==0){
            NilaiNaranjo7.setText("0");
        }else if(Naranjo7.getSelectedIndex()==1){
            NilaiNaranjo7.setText("1");
        }else{
            NilaiNaranjo7.setText("0");
        }
        calculateTotalNaranjo();
        updateKategoriNaranjo();
    }//GEN-LAST:event_Naranjo7ItemStateChanged

    private void Naranjo6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo6KeyPressed
        Valid.pindah(evt,Naranjo5,Naranjo7);
    }//GEN-LAST:event_Naranjo6KeyPressed

    private void Naranjo6ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Naranjo6ItemStateChanged
        if(Naranjo6.getSelectedIndex()==0){
            NilaiNaranjo6.setText("0");
        }else if(Naranjo6.getSelectedIndex()==1){
            NilaiNaranjo6.setText("-1");
        }else{
            NilaiNaranjo6.setText("1");
        }
        calculateTotalNaranjo();
        updateKategoriNaranjo();
    }//GEN-LAST:event_Naranjo6ItemStateChanged

    private void Naranjo5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo5KeyPressed
        Valid.pindah(evt,Naranjo4,Naranjo6);
    }//GEN-LAST:event_Naranjo5KeyPressed

    private void Naranjo5ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Naranjo5ItemStateChanged
        if(Naranjo5.getSelectedIndex()==0){
            NilaiNaranjo5.setText("0");
        }else if(Naranjo5.getSelectedIndex()==1){
            NilaiNaranjo5.setText("-1");
        }else{
            NilaiNaranjo5.setText("2");
        }
        calculateTotalNaranjo();
        updateKategoriNaranjo();
    }//GEN-LAST:event_Naranjo5ItemStateChanged

    private void Naranjo4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo4KeyPressed
        Valid.pindah(evt,Naranjo3,Naranjo5);
    }//GEN-LAST:event_Naranjo4KeyPressed

    private void Naranjo4ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Naranjo4ItemStateChanged
        if(Naranjo4.getSelectedIndex()==0){
            NilaiNaranjo4.setText("0");
        }else if(Naranjo4.getSelectedIndex()==1){
            NilaiNaranjo4.setText("2");
        }else{
            NilaiNaranjo4.setText("-1");
        }
        calculateTotalNaranjo();
        updateKategoriNaranjo();
    }//GEN-LAST:event_Naranjo4ItemStateChanged

    private void Naranjo3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo3KeyPressed
        Valid.pindah(evt,Naranjo2,Naranjo4);
    }//GEN-LAST:event_Naranjo3KeyPressed

    private void Naranjo3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Naranjo3ItemStateChanged
        if(Naranjo3.getSelectedIndex()==0){
            NilaiNaranjo3.setText("0");
        }else if(Naranjo3.getSelectedIndex()==1){
            NilaiNaranjo3.setText("1");
        }else{
            NilaiNaranjo3.setText("0");
        }
        calculateTotalNaranjo();
        updateKategoriNaranjo();
    }//GEN-LAST:event_Naranjo3ItemStateChanged

    private void Naranjo2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo2KeyPressed
        Valid.pindah(evt,Naranjo1,Naranjo3);
    }//GEN-LAST:event_Naranjo2KeyPressed

    private void Naranjo2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Naranjo2ItemStateChanged
        if(Naranjo2.getSelectedIndex()==0){
            NilaiNaranjo2.setText("0");
        }else if(Naranjo2.getSelectedIndex()==1){
            NilaiNaranjo2.setText("2");
        }else{
            NilaiNaranjo2.setText("-1");
        }
        calculateTotalNaranjo();
        updateKategoriNaranjo();
    }//GEN-LAST:event_Naranjo2ItemStateChanged

    private void Naranjo1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Naranjo1KeyPressed
//        Valid.pindah(evt,KeteranganSkriningGizi,Naranjo2);
    }//GEN-LAST:event_Naranjo1KeyPressed

    private void Naranjo1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Naranjo1ItemStateChanged
        if(Naranjo1.getSelectedIndex()==0){
            NilaiNaranjo1.setText("0");
        }else if(Naranjo1.getSelectedIndex()==1){
            NilaiNaranjo1.setText("1");
        }else{
            NilaiNaranjo1.setText("0");
        }
        calculateTotalNaranjo();
        updateKategoriNaranjo();
    }//GEN-LAST:event_Naranjo1ItemStateChanged

    private void Indikasi10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Indikasi10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Indikasi10KeyPressed

    private void Indikasi9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Indikasi9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Indikasi9KeyPressed

    private void Indikasi8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Indikasi8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Indikasi8KeyPressed

    private void Indikasi7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Indikasi7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Indikasi7KeyPressed

    private void Indikasi6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Indikasi6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Indikasi6KeyPressed

    private void Indikasi5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Indikasi5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Indikasi5KeyPressed

    private void Indikasi4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Indikasi4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Indikasi4KeyPressed

    private void Indikasi3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Indikasi3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Indikasi3KeyPressed

    private void Indikasi2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Indikasi2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Indikasi2KeyPressed

    private void Indikasi1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Indikasi1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Indikasi1KeyPressed

    private void TanggalAkhir9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalAkhir9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalAkhir9KeyPressed

    private void TanggalAkhir8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalAkhir8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalAkhir8KeyPressed

    private void TanggalAkhir7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalAkhir7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalAkhir7KeyPressed

    private void TanggalAkhir6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalAkhir6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalAkhir6KeyPressed

    private void TanggalAkhir5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalAkhir5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalAkhir5KeyPressed

    private void TanggalAkhir4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalAkhir4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalAkhir4KeyPressed

    private void TanggalAkhir3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalAkhir3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalAkhir3KeyPressed

    private void TanggalAkhir2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalAkhir2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalAkhir2KeyPressed

    private void TanggalAkhir1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalAkhir1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalAkhir1KeyPressed

    private void TanggalAkhir10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalAkhir10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalAkhir10KeyPressed

    private void TanggalMulai9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMulai9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalMulai9KeyPressed

    private void TanggalMulai8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMulai8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalMulai8KeyPressed

    private void TanggalMulai7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMulai7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalMulai7KeyPressed

    private void TanggalMulai6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMulai6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalMulai6KeyPressed

    private void TanggalMulai5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMulai5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalMulai5KeyPressed

    private void TanggalMulai4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMulai4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalMulai4KeyPressed

    private void TanggalMulai3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMulai3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalMulai3KeyPressed

    private void TanggalMulai2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMulai2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalMulai2KeyPressed

    private void TanggalMulai1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMulai1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalMulai1KeyPressed

    private void TanggaKesudahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggaKesudahanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggaKesudahanKeyPressed

    private void Cara1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Cara1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Cara1KeyPressed

    private void Cara9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Cara9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Cara9KeyPressed

    private void Cara7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Cara7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Cara7KeyPressed

    private void Cara8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Cara8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Cara8KeyPressed

    private void Cara3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Cara3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Cara3KeyPressed

    private void Cara6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Cara6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Cara6KeyPressed

    private void Cara10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Cara10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Cara10KeyPressed

    private void Cara2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Cara2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Cara2KeyPressed

    private void Cara5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Cara5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Cara5KeyPressed

    private void Cara4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Cara4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Cara4KeyPressed

    private void Batch1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batch1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batch1KeyPressed

    private void Batch9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batch9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batch9KeyPressed

    private void Batch7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batch7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batch7KeyPressed

    private void Batch8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batch8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batch8KeyPressed

    private void Batch3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batch3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batch3KeyPressed

    private void Batch6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batch6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batch6KeyPressed

    private void Batch10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batch10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batch10KeyPressed

    private void Batch2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batch2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batch2KeyPressed

    private void Batch5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batch5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batch5KeyPressed

    private void Batch4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Batch4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Batch4KeyPressed

    private void Sedian10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sedian10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sedian10KeyPressed

    private void Sedian9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sedian9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sedian9KeyPressed

    private void Sedian8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sedian8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sedian8KeyPressed

    private void Sedian7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sedian7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sedian7KeyPressed

    private void Sedian6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sedian6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sedian6KeyPressed

    private void Sedian5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sedian5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sedian5KeyPressed

    private void Sedian4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sedian4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sedian4KeyPressed

    private void Sedian3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sedian3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sedian3KeyPressed

    private void Sedian2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sedian2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sedian2KeyPressed

    private void Sedian1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Sedian1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Sedian1KeyPressed

    private void BtnObat10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat10KeyPressed

    private void BtnObat10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat10ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=10;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat10ActionPerformed

    private void Dosis10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis10KeyPressed

    private void Obat10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat10KeyPressed

    private void BtnObat9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat9KeyPressed

    private void BtnObat9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat9ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=9;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat9ActionPerformed

    private void Dosis9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis9KeyPressed

    private void Obat9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat9KeyPressed

    private void BtnObat8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat8KeyPressed

    private void BtnObat8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat8ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=8;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat8ActionPerformed

    private void Dosis8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis8KeyPressed

    private void Obat8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat8KeyPressed

    private void BtnObat7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat7KeyPressed

    private void BtnObat7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat7ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=7;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat7ActionPerformed

    private void Dosis7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis7KeyPressed

    private void Obat7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat7KeyPressed

    private void BtnObat6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat6KeyPressed

    private void BtnObat6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat6ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=6;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat6ActionPerformed

    private void Dosis6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis6KeyPressed

    private void Obat6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat6KeyPressed

    private void BtnObat5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat5KeyPressed

    private void BtnObat5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat5ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=5;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat5ActionPerformed

    private void Dosis5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis5KeyPressed

    private void Obat5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat5KeyPressed

    private void BtnObat4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat4KeyPressed

    private void BtnObat4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat4ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=4;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat4ActionPerformed

    private void Dosis4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis4KeyPressed

    private void Obat4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat4KeyPressed

    private void BtnObat3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat3KeyPressed

    private void BtnObat3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat3ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=3;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat3ActionPerformed

    private void Dosis3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis3KeyPressed

    private void Obat3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat3KeyPressed

    private void BtnObat2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat2KeyPressed

    private void BtnObat2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat2ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=2;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat2ActionPerformed

    private void Dosis2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis2KeyPressed

    private void Obat2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat2KeyPressed

    private void BtnObat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnObat1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_BtnObat1KeyPressed

    private void BtnObat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnObat1ActionPerformed
        if(TNoRw.getText().equals("")&&TNoRM.getText().equals("")){
            JOptionPane.showMessageDialog(null,"Pasien masih kosong...!!!");
        }else{
            i=1;
            cariobatdosis.setNoRawat(TNoRw.getText());
            cariobatdosis.tampil();
            //        cariedukasidokter.isCek();
            cariobatdosis.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            cariobatdosis.setLocationRelativeTo(internalFrame1);
            cariobatdosis.setAlwaysOnTop(false);
            cariobatdosis.setVisible(true);
        }
    }//GEN-LAST:event_BtnObat1ActionPerformed

    private void Dosis1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Dosis1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Dosis1KeyPressed

    private void Obat1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Obat1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat1KeyPressed

    private void TanggalMulai10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanggalMulai10KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TanggalMulai10KeyPressed

    private void ReaksiSetelahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReaksiSetelahKeyPressed
//        Valid.pindah(evt,PenyakitLain,Gigi);
    }//GEN-LAST:event_ReaksiSetelahKeyPressed

    private void Jkn10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jkn10KeyPressed
//        Valid.pindah(evt,ReaksiSama,Kulit);
    }//GEN-LAST:event_Jkn10KeyPressed

    private void ReaksiSamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ReaksiSamaKeyPressed
        Valid.pindah(evt,HasilKesudahan,Jkn10);
    }//GEN-LAST:event_ReaksiSamaKeyPressed

    private void HasilKesudahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HasilKesudahanKeyPressed
//        Valid.pindah(evt,Thoraks,ReaksiSama);
    }//GEN-LAST:event_HasilKesudahanKeyPressed

    private void PenyakitLainKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PenyakitLainKeyPressed
//        Valid.pindah(evt,SPO,ReaksiSetelah);
    }//GEN-LAST:event_PenyakitLainKeyPressed

    private void HamilKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_HamilKeyPressed
//        Valid.pindah(evt,Kesudahan,GCS);
    }//GEN-LAST:event_HamilKeyPressed

    private void KesudahanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KesudahanKeyPressed
//        Valid.pindah(evt,Alergi,Hamil);
    }//GEN-LAST:event_KesudahanKeyPressed

    private void Bentuk_ManifestasiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Bentuk_ManifestasiKeyPressed
//        Valid.pindah2(evt,KeluhanUtama,RPK);
    }//GEN-LAST:event_Bentuk_ManifestasiKeyPressed

    private void PekerjaanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_PekerjaanKeyPressed
//        Valid.pindah(evt,Anamnesis,KeluhanUtama);
    }//GEN-LAST:event_PekerjaanKeyPressed

    private void Penyakit_UtamaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Penyakit_UtamaKeyPressed
//        Valid.pindah2(evt,Kulit,KetLokalis);
    }//GEN-LAST:event_Penyakit_UtamaKeyPressed

    private void Masalah_KualitasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Masalah_KualitasKeyPressed
//        Valid.pindah2(evt,Riwayat_eso,Alergi);
    }//GEN-LAST:event_Masalah_KualitasKeyPressed

    private void Riwayat_esoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Riwayat_esoKeyPressed
//        Valid.pindah2(evt,RPK,Masalah_Kualitas);
    }//GEN-LAST:event_Riwayat_esoKeyPressed

    private void TanngalMulaiTerjadiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TanngalMulaiTerjadiKeyPressed
//        Valid.pindah(evt,LamaRatarata,KondisiPulang);
    }//GEN-LAST:event_TanngalMulaiTerjadiKeyPressed

    private void BBKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BBKeyPressed
//        Valid.pindah(evt,TB,TD);
    }//GEN-LAST:event_BBKeyPressed

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
//        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
//            isRawat();
//        }else{
//            Valid.pindah(evt,TCari,BtnDokter);
//        }
    }//GEN-LAST:event_TNoRwKeyPressed

    private void KdPegawaiKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_KdPegawaiKeyPressed

    }//GEN-LAST:event_KdPegawaiKeyPressed

    private void kdRuanganKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdRuanganKeyPressed
//        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
//            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmruangan,kdRuangan.getText());
//        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
//            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmruangan,kdRuangan.getText());
//            NoPermintaan.requestFocus();
//        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
//            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?", nmruangan,kdRuangan.getText());
//            kdptg.requestFocus();
//        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
//            btnSuplierActionPerformed(null);
//        }
    }//GEN-LAST:event_kdRuanganKeyPressed

    private void btnRuanganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRuanganActionPerformed
        bangsal.emptTeks();
        bangsal.isCek();
        bangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        bangsal.setLocationRelativeTo(internalFrame1);
        bangsal.setAlwaysOnTop(false);
        bangsal.setVisible(true);
    }//GEN-LAST:event_btnRuanganActionPerformed

    private void btnPegawaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPegawaiActionPerformed
        pegawai.emptTeks();
        pegawai.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pegawai.setLocationRelativeTo(internalFrame1);
        pegawai.setAlwaysOnTop(false);
        pegawai.setVisible(true);
    }//GEN-LAST:event_btnPegawaiActionPerformed

    private void Jkn1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jkn1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jkn1KeyPressed

    private void Jkn2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jkn2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jkn2KeyPressed

    private void Jkn3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jkn3KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jkn3KeyPressed

    private void Jkn4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jkn4KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jkn4KeyPressed

    private void Jkn5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jkn5KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jkn5KeyPressed

    private void Jkn6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jkn6KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jkn6KeyPressed

    private void Jkn7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jkn7KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jkn7KeyPressed

    private void Jkn8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jkn8KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jkn8KeyPressed

    private void Jkn9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Jkn9KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jkn9KeyPressed

    private void Naranjo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Naranjo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Naranjo1ActionPerformed

    private void NilaiNaranjo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NilaiNaranjo1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NilaiNaranjo1ActionPerformed

    private void NoSuratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NoSuratActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NoSuratActionPerformed

    private void NoSuratKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NoSuratKeyPressed
        Valid.pindah(evt,TCari,Obat1);
    }//GEN-LAST:event_NoSuratKeyPressed

    private void Obat1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Obat1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Obat1ActionPerformed

    private void Indikasi1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Indikasi1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Indikasi1ActionPerformed

    private void Jkn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Jkn6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jkn6ActionPerformed

    private void TglLaporanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TglLaporanKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TglLaporanKeyPressed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            RMPelaporanEfekSampingObat dialog = new RMPelaporanEfekSampingObat(new javax.swing.JFrame(), true);
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
    private widget.TextBox BB;
    private widget.TextBox Batch1;
    private widget.TextBox Batch10;
    private widget.TextBox Batch2;
    private widget.TextBox Batch3;
    private widget.TextBox Batch4;
    private widget.TextBox Batch5;
    private widget.TextBox Batch6;
    private widget.TextBox Batch7;
    private widget.TextBox Batch8;
    private widget.TextBox Batch9;
    private widget.TextArea Bentuk_Manifestasi;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnObat1;
    private widget.Button BtnObat10;
    private widget.Button BtnObat2;
    private widget.Button BtnObat3;
    private widget.Button BtnObat4;
    private widget.Button BtnObat5;
    private widget.Button BtnObat6;
    private widget.Button BtnObat7;
    private widget.Button BtnObat8;
    private widget.Button BtnObat9;
    private widget.Button BtnPrint;
    private widget.Button BtnSimpan;
    private widget.TextBox Cara1;
    private widget.TextBox Cara10;
    private widget.TextBox Cara2;
    private widget.TextBox Cara3;
    private widget.TextBox Cara4;
    private widget.TextBox Cara5;
    private widget.TextBox Cara6;
    private widget.TextBox Cara7;
    private widget.TextBox Cara8;
    private widget.TextBox Cara9;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.TextBox Dosis1;
    private widget.TextBox Dosis10;
    private widget.TextBox Dosis2;
    private widget.TextBox Dosis3;
    private widget.TextBox Dosis4;
    private widget.TextBox Dosis5;
    private widget.TextBox Dosis6;
    private widget.TextBox Dosis7;
    private widget.TextBox Dosis8;
    private widget.TextBox Dosis9;
    private widget.PanelBiasa FormInput;
    private widget.ComboBox Hamil;
    private widget.ComboBox HasilKesudahan;
    private widget.TextBox Indikasi1;
    private widget.TextBox Indikasi10;
    private widget.TextBox Indikasi2;
    private widget.TextBox Indikasi3;
    private widget.TextBox Indikasi4;
    private widget.TextBox Indikasi5;
    private widget.TextBox Indikasi6;
    private widget.TextBox Indikasi7;
    private widget.TextBox Indikasi8;
    private widget.TextBox Indikasi9;
    private widget.TextBox Jk;
    private widget.ComboBox Jkn1;
    private widget.ComboBox Jkn10;
    private widget.ComboBox Jkn2;
    private widget.ComboBox Jkn3;
    private widget.ComboBox Jkn4;
    private widget.ComboBox Jkn5;
    private widget.ComboBox Jkn6;
    private widget.ComboBox Jkn7;
    private widget.ComboBox Jkn8;
    private widget.ComboBox Jkn9;
    private widget.TextBox KategoriNaranjo;
    private widget.TextBox KdPegawai;
    private widget.ComboBox Kesudahan;
    private widget.Label LCount;
    private widget.editorpane LoadHTML;
    private widget.TextArea Masalah_Kualitas;
    private javax.swing.JMenuItem MnMonitoringEso;
    private widget.ComboBox Naranjo1;
    private widget.ComboBox Naranjo10;
    private widget.ComboBox Naranjo2;
    private widget.ComboBox Naranjo3;
    private widget.ComboBox Naranjo4;
    private widget.ComboBox Naranjo5;
    private widget.ComboBox Naranjo6;
    private widget.ComboBox Naranjo7;
    private widget.ComboBox Naranjo8;
    private widget.ComboBox Naranjo9;
    private widget.TextBox NilaiNaranjo1;
    private widget.TextBox NilaiNaranjo10;
    private widget.TextBox NilaiNaranjo2;
    private widget.TextBox NilaiNaranjo3;
    private widget.TextBox NilaiNaranjo4;
    private widget.TextBox NilaiNaranjo5;
    private widget.TextBox NilaiNaranjo6;
    private widget.TextBox NilaiNaranjo7;
    private widget.TextBox NilaiNaranjo8;
    private widget.TextBox NilaiNaranjo9;
    private widget.TextBox NilaiNaranjoTotal;
    private widget.TextBox NmPegawai;
    private widget.TextBox NoSurat;
    private widget.TextBox Obat1;
    private widget.TextBox Obat10;
    private widget.TextBox Obat2;
    private widget.TextBox Obat3;
    private widget.TextBox Obat4;
    private widget.TextBox Obat5;
    private widget.TextBox Obat6;
    private widget.TextBox Obat7;
    private widget.TextBox Obat8;
    private widget.TextBox Obat9;
    private widget.TextBox Pekerjaan;
    private widget.ComboBox PenyakitLain;
    private widget.TextArea Penyakit_Utama;
    private widget.ComboBox Profesi;
    private widget.ComboBox ReaksiSama;
    private widget.ComboBox ReaksiSetelah;
    private widget.TextArea Riwayat_eso;
    private widget.ScrollPane Scroll;
    private widget.TextBox Sedian1;
    private widget.TextBox Sedian10;
    private widget.TextBox Sedian2;
    private widget.TextBox Sedian3;
    private widget.TextBox Sedian4;
    private widget.TextBox Sedian5;
    private widget.TextBox Sedian6;
    private widget.TextBox Sedian7;
    private widget.TextBox Sedian8;
    private widget.TextBox Sedian9;
    private widget.TextBox Suku;
    private widget.TextBox TCari;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private javax.swing.JTabbedPane TabRawat;
    private widget.Tanggal TanggaKesudahan;
    private widget.Tanggal TanggalAkhir1;
    private widget.Tanggal TanggalAkhir10;
    private widget.Tanggal TanggalAkhir2;
    private widget.Tanggal TanggalAkhir3;
    private widget.Tanggal TanggalAkhir4;
    private widget.Tanggal TanggalAkhir5;
    private widget.Tanggal TanggalAkhir6;
    private widget.Tanggal TanggalAkhir7;
    private widget.Tanggal TanggalAkhir8;
    private widget.Tanggal TanggalAkhir9;
    private widget.Tanggal TanggalMulai1;
    private widget.Tanggal TanggalMulai10;
    private widget.Tanggal TanggalMulai2;
    private widget.Tanggal TanggalMulai3;
    private widget.Tanggal TanggalMulai4;
    private widget.Tanggal TanggalMulai5;
    private widget.Tanggal TanggalMulai6;
    private widget.Tanggal TanggalMulai7;
    private widget.Tanggal TanggalMulai8;
    private widget.Tanggal TanggalMulai9;
    private widget.TextBox TanggalRegistrasi;
    private widget.Tanggal TanngalMulaiTerjadi;
    private widget.TextBox TglLahir;
    private widget.Tanggal TglLaporan;
    private widget.Label TingkatResiko1;
    private widget.TextBox Umur;
    private widget.Button btnPegawai;
    private widget.Button btnRuangan;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame2;
    private widget.InternalFrame internalFrame3;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel13;
    private widget.Label jLabel14;
    private widget.Label jLabel15;
    private widget.Label jLabel19;
    private widget.Label jLabel21;
    private widget.Label jLabel243;
    private widget.Label jLabel244;
    private widget.Label jLabel245;
    private widget.Label jLabel246;
    private widget.Label jLabel247;
    private widget.Label jLabel248;
    private widget.Label jLabel249;
    private widget.Label jLabel250;
    private widget.Label jLabel251;
    private widget.Label jLabel252;
    private widget.Label jLabel253;
    private widget.Label jLabel256;
    private widget.Label jLabel27;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel47;
    private widget.Label jLabel48;
    private widget.Label jLabel49;
    private widget.Label jLabel53;
    private widget.Label jLabel54;
    private widget.Label jLabel55;
    private widget.Label jLabel56;
    private widget.Label jLabel57;
    private widget.Label jLabel58;
    private widget.Label jLabel59;
    private widget.Label jLabel6;
    private widget.Label jLabel60;
    private widget.Label jLabel61;
    private widget.Label jLabel62;
    private widget.Label jLabel63;
    private widget.Label jLabel64;
    private widget.Label jLabel65;
    private widget.Label jLabel66;
    private widget.Label jLabel67;
    private widget.Label jLabel68;
    private widget.Label jLabel69;
    private widget.Label jLabel7;
    private widget.Label jLabel70;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private widget.Label jLabel94;
    private widget.Label jLabel99;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator19;
    private javax.swing.JSeparator jSeparator20;
    private widget.TextBox kdRuangan;
    private widget.Label label11;
    private widget.Label label16;
    private widget.TextBox nmruangan;
    private widget.panelisi panelGlass8;
    private widget.panelisi panelGlass9;
    private widget.ScrollPane scrollInput;
    private widget.ScrollPane scrollPane2;
    private widget.ScrollPane scrollPane4;
    private widget.ScrollPane scrollPane5;
    private widget.ScrollPane scrollPane7;
    private widget.Table tbObat;
    // End of variables declaration//GEN-END:variables

    public void tampil() {
        Valid.tabelKosong(tabMode);
        try{
            if(TCari.getText().trim().equals("")){
                ps=koneksi.prepareStatement(
                        "select monitoring_efek_samping_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,pasien.umur,pasien.pekerjaan,pasien.suku_bangsa,suku_bangsa.nama_suku_bangsa,monitoring_efek_samping_obat.no_laporan,monitoring_efek_samping_obat.tanggal,monitoring_efek_samping_obat.profesi,monitoring_efek_samping_obat.nik,pegawai.nama,monitoring_efek_samping_obat.kd_bangsal,"+
                        "bangsal.nm_bangsal,monitoring_efek_samping_obat.berat_badan,monitoring_efek_samping_obat.pasien_hamil,monitoring_efek_samping_obat.kesudahan,monitoring_efek_samping_obat.penyakit_lain,monitoring_efek_samping_obat.penyakit_utama,monitoring_efek_samping_obat.tanggal_kejadian,monitoring_efek_samping_obat.manifestasi,monitoring_efek_samping_obat.masalah_kualitas,"+       
                        "monitoring_efek_samping_obat.riwayat_eso,monitoring_efek_samping_obat.tanggal_kesudahan,monitoring_efek_samping_obat.hasil_kesudahan,"+
                        "monitoring_efek_samping_obat.obat1,monitoring_efek_samping_obat.sedian1,monitoring_efek_samping_obat.obat_jkn1,monitoring_efek_samping_obat.batch1,monitoring_efek_samping_obat.cara1,monitoring_efek_samping_obat.dosis1,monitoring_efek_samping_obat.tanggal_mulai1,monitoring_efek_samping_obat.tanggal_akhir1,monitoring_efek_samping_obat.indikasi1,"+
                        "monitoring_efek_samping_obat.obat2,monitoring_efek_samping_obat.sedian2,monitoring_efek_samping_obat.obat_jkn2,monitoring_efek_samping_obat.batch2,monitoring_efek_samping_obat.cara2,monitoring_efek_samping_obat.dosis2,monitoring_efek_samping_obat.tanggal_mulai2,monitoring_efek_samping_obat.tanggal_akhir2,monitoring_efek_samping_obat.indikasi2,"+  
                        "monitoring_efek_samping_obat.obat3,monitoring_efek_samping_obat.sedian3,monitoring_efek_samping_obat.obat_jkn3,monitoring_efek_samping_obat.batch3,monitoring_efek_samping_obat.cara3,monitoring_efek_samping_obat.dosis3,monitoring_efek_samping_obat.tanggal_mulai3,monitoring_efek_samping_obat.tanggal_akhir3,monitoring_efek_samping_obat.indikasi3,"+
                        "monitoring_efek_samping_obat.obat4,monitoring_efek_samping_obat.sedian4,monitoring_efek_samping_obat.obat_jkn4,monitoring_efek_samping_obat.batch4,monitoring_efek_samping_obat.cara4,monitoring_efek_samping_obat.dosis4,monitoring_efek_samping_obat.tanggal_mulai4,monitoring_efek_samping_obat.tanggal_akhir4,monitoring_efek_samping_obat.indikasi4,"+
                        "monitoring_efek_samping_obat.obat5,monitoring_efek_samping_obat.sedian5,monitoring_efek_samping_obat.obat_jkn5,monitoring_efek_samping_obat.batch5,monitoring_efek_samping_obat.cara5,monitoring_efek_samping_obat.dosis5,monitoring_efek_samping_obat.tanggal_mulai5,monitoring_efek_samping_obat.tanggal_akhir5,monitoring_efek_samping_obat.indikasi5,"+
                        "monitoring_efek_samping_obat.obat6,monitoring_efek_samping_obat.sedian6,monitoring_efek_samping_obat.obat_jkn6,monitoring_efek_samping_obat.batch6,monitoring_efek_samping_obat.cara6,monitoring_efek_samping_obat.dosis6,monitoring_efek_samping_obat.tanggal_mulai6,monitoring_efek_samping_obat.tanggal_akhir6,monitoring_efek_samping_obat.indikasi6,"+
                        "monitoring_efek_samping_obat.obat7,monitoring_efek_samping_obat.sedian7,monitoring_efek_samping_obat.obat_jkn7,monitoring_efek_samping_obat.batch7,monitoring_efek_samping_obat.cara7,monitoring_efek_samping_obat.dosis7,monitoring_efek_samping_obat.tanggal_mulai7,monitoring_efek_samping_obat.tanggal_akhir7,monitoring_efek_samping_obat.indikasi7,"+
                        "monitoring_efek_samping_obat.obat8,monitoring_efek_samping_obat.sedian8,monitoring_efek_samping_obat.obat_jkn8,monitoring_efek_samping_obat.batch8,monitoring_efek_samping_obat.cara8,monitoring_efek_samping_obat.dosis8,monitoring_efek_samping_obat.tanggal_mulai8,monitoring_efek_samping_obat.tanggal_akhir8,monitoring_efek_samping_obat.indikasi8,"+
                        "monitoring_efek_samping_obat.obat9,monitoring_efek_samping_obat.sedian9,monitoring_efek_samping_obat.obat_jkn9,monitoring_efek_samping_obat.batch9,monitoring_efek_samping_obat.cara9,monitoring_efek_samping_obat.dosis9,monitoring_efek_samping_obat.tanggal_mulai9,monitoring_efek_samping_obat.tanggal_akhir9,monitoring_efek_samping_obat.indikasi9,"+
                        "monitoring_efek_samping_obat.obat10,monitoring_efek_samping_obat.sedian10,monitoring_efek_samping_obat.obat_jkn10,monitoring_efek_samping_obat.batch10,monitoring_efek_samping_obat.cara10,monitoring_efek_samping_obat.dosis10,monitoring_efek_samping_obat.tanggal_mulai10,monitoring_efek_samping_obat.tanggal_akhir10,monitoring_efek_samping_obat.indikasi10,"+
                        "monitoring_efek_samping_obat.reaksi_setelah,monitoring_efek_samping_obat.reaksi_sama,monitoring_efek_samping_obat.naranjo1,monitoring_efek_samping_obat.nilai_naranjo1,monitoring_efek_samping_obat.naranjo2,monitoring_efek_samping_obat.nilai_naranjo2,monitoring_efek_samping_obat.naranjo3,monitoring_efek_samping_obat.nilai_naranjo3,monitoring_efek_samping_obat.naranjo4,"+
                        "monitoring_efek_samping_obat.nilai_naranjo4,monitoring_efek_samping_obat.naranjo5,monitoring_efek_samping_obat.nilai_naranjo5,monitoring_efek_samping_obat.naranjo6,monitoring_efek_samping_obat.nilai_naranjo6,monitoring_efek_samping_obat.naranjo7,monitoring_efek_samping_obat.nilai_naranjo7,monitoring_efek_samping_obat.naranjo8,monitoring_efek_samping_obat.nilai_naranjo8,"+        
                        "monitoring_efek_samping_obat.naranjo9,monitoring_efek_samping_obat.nilai_naranjo9,monitoring_efek_samping_obat.naranjo10,monitoring_efek_samping_obat.nilai_naranjo10,monitoring_efek_samping_obat.total_nilai_naranjo,monitoring_efek_samping_obat.kategori_naranjo "+     
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join monitoring_efek_samping_obat on reg_periksa.no_rawat=monitoring_efek_samping_obat.no_rawat "+
                        "inner join pegawai on monitoring_efek_samping_obat.nik=pegawai.nik "+
                        "inner join bangsal on monitoring_efek_samping_obat.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join suku_bangsa on suku_bangsa.id=pasien.suku_bangsa "+
                        "where monitoring_efek_samping_obat.tanggal between ? and ? order by monitoring_efek_samping_obat.tanggal");
            }else{
                ps=koneksi.prepareStatement(
                        "select monitoring_efek_samping_obat.no_rawat,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.tgl_lahir,pasien.jk,pasien.umur,pasien.pekerjaan,pasien.suku_bangsa,suku_bangsa.nama_suku_bangsa,monitoring_efek_samping_obat.no_laporan,monitoring_efek_samping_obat.tanggal,monitoring_efek_samping_obat.profesi,monitoring_efek_samping_obat.nik,pegawai.nama,monitoring_efek_samping_obat.kd_bangsal,"+
                        "bangsal.nm_bangsal,monitoring_efek_samping_obat.berat_badan,monitoring_efek_samping_obat.pasien_hamil,monitoring_efek_samping_obat.kesudahan,monitoring_efek_samping_obat.penyakit_lain,monitoring_efek_samping_obat.penyakit_utama,monitoring_efek_samping_obat.tanggal_kejadian,monitoring_efek_samping_obat.manifestasi,monitoring_efek_samping_obat.masalah_kualitas,"+       
                        "monitoring_efek_samping_obat.riwayat_eso,monitoring_efek_samping_obat.tanggal_kesudahan,monitoring_efek_samping_obat.hasil_kesudahan,"+
                        "monitoring_efek_samping_obat.obat1,monitoring_efek_samping_obat.sedian1,monitoring_efek_samping_obat.obat_jkn1,monitoring_efek_samping_obat.batch1,monitoring_efek_samping_obat.cara1,monitoring_efek_samping_obat.dosis1,monitoring_efek_samping_obat.tanggal_mulai1,monitoring_efek_samping_obat.tanggal_akhir1,monitoring_efek_samping_obat.indikasi1,"+
                        "monitoring_efek_samping_obat.obat2,monitoring_efek_samping_obat.sedian2,monitoring_efek_samping_obat.obat_jkn2,monitoring_efek_samping_obat.batch2,monitoring_efek_samping_obat.cara2,monitoring_efek_samping_obat.dosis2,monitoring_efek_samping_obat.tanggal_mulai2,monitoring_efek_samping_obat.tanggal_akhir2,monitoring_efek_samping_obat.indikasi2,"+  
                        "monitoring_efek_samping_obat.obat3,monitoring_efek_samping_obat.sedian3,monitoring_efek_samping_obat.obat_jkn3,monitoring_efek_samping_obat.batch3,monitoring_efek_samping_obat.cara3,monitoring_efek_samping_obat.dosis3,monitoring_efek_samping_obat.tanggal_mulai3,monitoring_efek_samping_obat.tanggal_akhir3,monitoring_efek_samping_obat.indikasi3,"+
                        "monitoring_efek_samping_obat.obat4,monitoring_efek_samping_obat.sedian4,monitoring_efek_samping_obat.obat_jkn4,monitoring_efek_samping_obat.batch4,monitoring_efek_samping_obat.cara4,monitoring_efek_samping_obat.dosis4,monitoring_efek_samping_obat.tanggal_mulai4,monitoring_efek_samping_obat.tanggal_akhir4,monitoring_efek_samping_obat.indikasi4,"+
                        "monitoring_efek_samping_obat.obat5,monitoring_efek_samping_obat.sedian5,monitoring_efek_samping_obat.obat_jkn5,monitoring_efek_samping_obat.batch5,monitoring_efek_samping_obat.cara5,monitoring_efek_samping_obat.dosis5,monitoring_efek_samping_obat.tanggal_mulai5,monitoring_efek_samping_obat.tanggal_akhir5,monitoring_efek_samping_obat.indikasi5,"+
                        "monitoring_efek_samping_obat.obat6,monitoring_efek_samping_obat.sedian6,monitoring_efek_samping_obat.obat_jkn6,monitoring_efek_samping_obat.batch6,monitoring_efek_samping_obat.cara6,monitoring_efek_samping_obat.dosis6,monitoring_efek_samping_obat.tanggal_mulai6,monitoring_efek_samping_obat.tanggal_akhir6,monitoring_efek_samping_obat.indikasi6,"+
                        "monitoring_efek_samping_obat.obat7,monitoring_efek_samping_obat.sedian7,monitoring_efek_samping_obat.obat_jkn7,monitoring_efek_samping_obat.batch7,monitoring_efek_samping_obat.cara7,monitoring_efek_samping_obat.dosis7,monitoring_efek_samping_obat.tanggal_mulai7,monitoring_efek_samping_obat.tanggal_akhir7,monitoring_efek_samping_obat.indikasi7,"+
                        "monitoring_efek_samping_obat.obat8,monitoring_efek_samping_obat.sedian8,monitoring_efek_samping_obat.obat_jkn8,monitoring_efek_samping_obat.batch8,monitoring_efek_samping_obat.cara8,monitoring_efek_samping_obat.dosis8,monitoring_efek_samping_obat.tanggal_mulai8,monitoring_efek_samping_obat.tanggal_akhir8,monitoring_efek_samping_obat.indikasi8,"+
                        "monitoring_efek_samping_obat.obat9,monitoring_efek_samping_obat.sedian9,monitoring_efek_samping_obat.obat_jkn9,monitoring_efek_samping_obat.batch9,monitoring_efek_samping_obat.cara9,monitoring_efek_samping_obat.dosis9,monitoring_efek_samping_obat.tanggal_mulai9,monitoring_efek_samping_obat.tanggal_akhir9,monitoring_efek_samping_obat.indikasi9,"+
                        "monitoring_efek_samping_obat.obat10,monitoring_efek_samping_obat.sedian10,monitoring_efek_samping_obat.obat_jkn10,monitoring_efek_samping_obat.batch10,monitoring_efek_samping_obat.cara10,monitoring_efek_samping_obat.dosis10,monitoring_efek_samping_obat.tanggal_mulai10,monitoring_efek_samping_obat.tanggal_akhir10,monitoring_efek_samping_obat.indikasi10,"+
                        "monitoring_efek_samping_obat.reaksi_setelah,monitoring_efek_samping_obat.reaksi_sama,monitoring_efek_samping_obat.naranjo1,monitoring_efek_samping_obat.nilai_naranjo1,monitoring_efek_samping_obat.naranjo2,monitoring_efek_samping_obat.nilai_naranjo2,monitoring_efek_samping_obat.naranjo3,monitoring_efek_samping_obat.nilai_naranjo3,monitoring_efek_samping_obat.naranjo4,"+
                        "monitoring_efek_samping_obat.nilai_naranjo4,monitoring_efek_samping_obat.naranjo5,monitoring_efek_samping_obat.nilai_naranjo5,monitoring_efek_samping_obat.naranjo6,monitoring_efek_samping_obat.nilai_naranjo6,monitoring_efek_samping_obat.naranjo7,monitoring_efek_samping_obat.nilai_naranjo7,monitoring_efek_samping_obat.naranjo8,monitoring_efek_samping_obat.nilai_naranjo8,"+        
                        "monitoring_efek_samping_obat.naranjo9,monitoring_efek_samping_obat.nilai_naranjo9,monitoring_efek_samping_obat.naranjo10,monitoring_efek_samping_obat.nilai_naranjo10,monitoring_efek_samping_obat.total_nilai_naranjo,monitoring_efek_samping_obat.kategori_naranjo "+     
                        "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                        "inner join monitoring_efek_samping_obat on reg_periksa.no_rawat=monitoring_efek_samping_obat.no_rawat "+
                        "inner join pegawai on monitoring_efek_samping_obat.nik=pegawai.nik "+
                        "inner join bangsal on monitoring_efek_samping_obat.kd_bangsal=bangsal.kd_bangsal "+
                        "inner join suku_bangsa on suku_bangsa.id=pasien.suku_bangsa "+
                        "where monitoring_efek_samping_obat.tanggal between ? and ? and monitoring_efek_samping_obat.obat1 like ? or "+
                        "monitoring_efek_samping_obat.tanggal between ? and ? and monitoring_efek_samping_obat.no_laporan like ? or "+
                        "monitoring_efek_samping_obat.tanggal between ? and ? and pasien.nm_pasien like ? or "+
                        "monitoring_efek_samping_obat.tanggal between ? and ? and monitoring_efek_samping_obat.nik like ? or "+
                        "monitoring_efek_samping_obat.tanggal between ? and ? and pegawai.nama like ? or "+
                        "monitoring_efek_samping_obat.tanggal between ? and ? and monitoring_efek_samping_obat.nik like ? or "+
                        "monitoring_efek_samping_obat.tanggal between ? and ? and monitoring_efek_samping_obat.obat2 like ? or "+
                        "monitoring_efek_samping_obat.tanggal between ? and ? and monitoring_efek_samping_obat.obat3 like ? or "+
                        "monitoring_efek_samping_obat.tanggal between ? and ? and monitoring_efek_samping_obat.obat4 like ? or "+
                        "monitoring_efek_samping_obat.tanggal between ? and ? and monitoring_efek_samping_obat.no_rawat like ? "+
                        "order by monitoring_efek_samping_obat.tanggal");  
            }
                
            try {
                if(TCari.getText().equals("")){
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                }else{
                    ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(3,"%"+TCari.getText()+"%");
                    ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(6,"%"+TCari.getText()+"%");
                    ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(9,"%"+TCari.getText()+"%");
                    ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(12,"%"+TCari.getText()+"%");
                    ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(15,"%"+TCari.getText()+"%");
                    ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(18,"%"+TCari.getText()+"%");
                    ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(21,"%"+TCari.getText()+"%");
                    ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(24,"%"+TCari.getText()+"%");
                    ps.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(27,"%"+TCari.getText()+"%");
                    ps.setString(28,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                    ps.setString(29,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                    ps.setString(30,"%"+TCari.getText()+"%");
                }   
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new String[]{
                        rs.getString("no_rawat"),rs.getString("no_rkm_medis"),rs.getString("nm_pasien"),rs.getString("tgl_lahir"),rs.getString("jk"),rs.getString("umur"),rs.getString("pekerjaan"),rs.getString("nama_suku_bangsa"),rs.getString("no_laporan"),rs.getString("tanggal"),rs.getString("profesi"),rs.getString("nik"),rs.getString("nama"),rs.getString("kd_bangsal"),
                        rs.getString("nm_bangsal"),rs.getString("berat_badan"),rs.getString("pasien_hamil"),rs.getString("kesudahan"),rs.getString("penyakit_lain"),rs.getString("penyakit_utama"),rs.getString("tanggal_kejadian"),rs.getString("manifestasi"),rs.getString("masalah_kualitas"),rs.getString("riwayat_eso"),rs.getString("tanggal_kesudahan"),rs.getString("hasil_kesudahan"),
                        rs.getString("obat1"),rs.getString("sedian1"),rs.getString("obat_jkn1"),rs.getString("batch1"),rs.getString("cara1"),rs.getString("dosis1"),rs.getString("tanggal_mulai1"),rs.getString("tanggal_akhir1"),rs.getString("indikasi1"),
                        rs.getString("obat2"),rs.getString("sedian2"),rs.getString("obat_jkn2"),rs.getString("batch2"),rs.getString("cara2"),rs.getString("dosis2"),rs.getString("tanggal_mulai2"),rs.getString("tanggal_akhir2"),rs.getString("indikasi2"),
                        rs.getString("obat3"),rs.getString("sedian3"),rs.getString("obat_jkn3"),rs.getString("batch3"),rs.getString("cara3"),rs.getString("dosis3"),rs.getString("tanggal_mulai3"),rs.getString("tanggal_akhir3"),rs.getString("indikasi3"),
                        rs.getString("obat4"),rs.getString("sedian4"),rs.getString("obat_jkn4"),rs.getString("batch4"),rs.getString("cara4"),rs.getString("dosis4"),rs.getString("tanggal_mulai4"),rs.getString("tanggal_akhir4"),rs.getString("indikasi4"),
                        rs.getString("obat5"),rs.getString("sedian5"),rs.getString("obat_jkn5"),rs.getString("batch5"),rs.getString("cara5"),rs.getString("dosis5"),rs.getString("tanggal_mulai5"),rs.getString("tanggal_akhir5"),rs.getString("indikasi5"),
                        rs.getString("obat6"),rs.getString("sedian6"),rs.getString("obat_jkn6"),rs.getString("batch6"),rs.getString("cara6"),rs.getString("dosis6"),rs.getString("tanggal_mulai6"),rs.getString("tanggal_akhir6"),rs.getString("indikasi6"),
                        rs.getString("obat7"),rs.getString("sedian7"),rs.getString("obat_jkn7"),rs.getString("batch7"),rs.getString("cara7"),rs.getString("dosis7"),rs.getString("tanggal_mulai7"),rs.getString("tanggal_akhir7"),rs.getString("indikasi7"),
                        rs.getString("obat8"),rs.getString("sedian8"),rs.getString("obat_jkn8"),rs.getString("batch8"),rs.getString("cara8"),rs.getString("dosis8"),rs.getString("tanggal_mulai8"),rs.getString("tanggal_akhir8"),rs.getString("indikasi8"),
                        rs.getString("obat9"),rs.getString("sedian9"),rs.getString("obat_jkn9"),rs.getString("batch9"),rs.getString("cara9"),rs.getString("dosis9"),rs.getString("tanggal_mulai9"),rs.getString("tanggal_akhir9"),rs.getString("indikasi9"),
                        rs.getString("obat10"),rs.getString("sedian10"),rs.getString("obat_jkn10"),rs.getString("batch10"),rs.getString("cara10"),rs.getString("dosis10"),rs.getString("tanggal_mulai10"),rs.getString("tanggal_akhir10"),rs.getString("indikasi10"),
                        rs.getString("reaksi_setelah"),rs.getString("reaksi_sama"),rs.getString("naranjo1"),rs.getString("nilai_naranjo1"),rs.getString("naranjo2"),rs.getString("nilai_naranjo2"),rs.getString("naranjo3"),rs.getString("nilai_naranjo3"),
                        rs.getString("naranjo4"),rs.getString("nilai_naranjo4"),rs.getString("naranjo5"),rs.getString("nilai_naranjo5"),rs.getString("naranjo6"),rs.getString("nilai_naranjo6"),rs.getString("naranjo7"),rs.getString("nilai_naranjo7"),
                        rs.getString("naranjo8"),rs.getString("nilai_naranjo8"),rs.getString("naranjo9"),rs.getString("nilai_naranjo9"),rs.getString("naranjo10"),rs.getString("nilai_naranjo10"),rs.getString("total_nilai_naranjo"),rs.getString("kategori_naranjo")
                    });
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
            
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TglLaporan.setDate(new Date());
        TanngalMulaiTerjadi.setDate(new Date());
        TanggaKesudahan.setDate(new Date());
        TabRawat.setSelectedIndex(0);
        BB.setText("");
        //Pekerjaan.setText("");
        Profesi.setSelectedIndex(0);
        Kesudahan.setSelectedIndex(0);
        Hamil.setSelectedIndex(0);
        PenyakitLain.setSelectedIndex(0);
        Penyakit_Utama.setText("");
        Bentuk_Manifestasi.setText("");
        Masalah_Kualitas.setText("");
        Riwayat_eso.setText("");
        HasilKesudahan.setSelectedIndex(0);
        Obat1.setText("");
        Sedian1.setText("");
        Jkn1.setSelectedIndex(0);
        Batch1.setText("");
        Cara1.setText("");
        Dosis1.setText("");
        TanggalMulai1.setDate(new Date());
        TanggalAkhir1.setDate(new Date());
        Indikasi1.setText("");
        Obat2.setText("");
        Sedian2.setText("");
        Jkn2.setSelectedIndex(0);
        Batch2.setText("");
        Cara2.setText("");
        Dosis2.setText("");
        TanggalMulai2.setDate(new Date());
        TanggalAkhir2.setDate(new Date());
        Indikasi2.setText("");
        Obat3.setText("");
        Sedian3.setText("");
        Jkn3.setSelectedIndex(0);
        Batch3.setText("");
        Cara3.setText("");
        Dosis3.setText("");
        TanggalMulai3.setDate(new Date());
        TanggalAkhir3.setDate(new Date());
        Indikasi3.setText("");
        Obat4.setText("");
        Sedian4.setText("");
        Jkn4.setSelectedIndex(0);
        Batch4.setText("");
        Cara4.setText("");
        Dosis4.setText("");
        TanggalMulai4.setDate(new Date());
        TanggalAkhir4.setDate(new Date());
        Indikasi4.setText("");
        Obat5.setText("");
        Sedian5.setText("");
        Jkn5.setSelectedIndex(0);
        Batch5.setText("");
        Cara5.setText("");
        Dosis5.setText("");
        TanggalMulai5.setDate(new Date());
        TanggalAkhir5.setDate(new Date());
        Indikasi5.setText("");
        Obat6.setText("");
        Sedian6.setText("");
        Jkn6.setSelectedIndex(0);
        Batch6.setText("");
        Cara6.setText("");
        Dosis6.setText("");
        TanggalMulai6.setDate(new Date());
        TanggalAkhir6.setDate(new Date());
        Indikasi6.setText("");
        Obat7.setText("");
        Sedian7.setText("");
        Jkn7.setSelectedIndex(0);
        Batch7.setText("");
        Cara7.setText("");
        Dosis7.setText("");
        TanggalMulai7.setDate(new Date());
        TanggalAkhir7.setDate(new Date());
        Indikasi7.setText("");
        Obat8.setText("");
        Sedian8.setText("");
        Jkn8.setSelectedIndex(0);
        Batch8.setText("");
        Cara8.setText("");
        Dosis8.setText("");
        TanggalMulai8.setDate(new Date());
        TanggalAkhir8.setDate(new Date());
        Indikasi8.setText("");
        Obat9.setText("");
        Sedian9.setText("");
        Jkn9.setSelectedIndex(0);
        Batch9.setText("");
        Cara9.setText("");
        Dosis9.setText("");
        TanggalMulai9.setDate(new Date());
        TanggalAkhir9.setDate(new Date());
        Indikasi9.setText("");
        Obat10.setText("");
        Sedian10.setText("");
        Jkn10.setSelectedIndex(0);
        Batch10.setText("");
        Cara10.setText("");
        Dosis10.setText("");
        TanggalMulai10.setDate(new Date());
        TanggalAkhir10.setDate(new Date());
        Indikasi10.setText("");
        ReaksiSetelah.setSelectedIndex(0);
        ReaksiSama.setSelectedIndex(0);
        Naranjo1.setSelectedIndex(0);
        NilaiNaranjo1.setText("0");
        Naranjo2.setSelectedIndex(0);
        NilaiNaranjo2.setText("0");
        Naranjo3.setSelectedIndex(0);
        NilaiNaranjo3.setText("0");
        Naranjo4.setSelectedIndex(0);
        NilaiNaranjo4.setText("0");
        Naranjo5.setSelectedIndex(0);
        NilaiNaranjo5.setText("0");
        Naranjo6.setSelectedIndex(0);
        NilaiNaranjo6.setText("0");
        Naranjo7.setSelectedIndex(0);
        NilaiNaranjo7.setText("0");
        Naranjo8.setSelectedIndex(0);
        NilaiNaranjo8.setText("0");
        Naranjo9.setSelectedIndex(0);
        NilaiNaranjo9.setText("0");
        Naranjo10.setSelectedIndex(0);
        NilaiNaranjo10.setText("0");
        NilaiNaranjoTotal.setText("0");
        KategoriNaranjo.setText("Kategori: Doubtful");
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_laporan,3),signed)),0) from monitoring_efek_samping_obat where tanggal='"+Valid.SetTgl(TglLaporan.getSelectedItem()+"")+"' ",
                "MESO"+TglLaporan.getSelectedItem().toString().substring(6,10)+TglLaporan.getSelectedItem().toString().substring(3,5)+TglLaporan.getSelectedItem().toString().substring(0,2),4,NoSurat);
        TglLaporan.requestFocus();
    } 

    private void getData() {
        if(tbObat.getSelectedRow()!= -1){
            TNoRw.setText(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()); 
            TNoRM.setText(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
            TPasien.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            TglLahir.setText(tbObat.getValueAt(tbObat.getSelectedRow(),3).toString());
            Jk.setText(tbObat.getValueAt(tbObat.getSelectedRow(),4).toString()); 
            Umur.setText(tbObat.getValueAt(tbObat.getSelectedRow(),5).toString()); 
            Pekerjaan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),6).toString());
            Suku.setText(tbObat.getValueAt(tbObat.getSelectedRow(),7).toString());
            NoSurat.setText(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString());
            Valid.SetTgl2(TglLaporan,tbObat.getValueAt(tbObat.getSelectedRow(),9).toString());
            Profesi.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),10).toString());
            KdPegawai.setText(tbObat.getValueAt(tbObat.getSelectedRow(),11).toString()); 
            NmPegawai.setText(tbObat.getValueAt(tbObat.getSelectedRow(),12).toString()); 
            kdRuangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),13).toString()); 
            nmruangan.setText(tbObat.getValueAt(tbObat.getSelectedRow(),14).toString()); 
            BB.setText(tbObat.getValueAt(tbObat.getSelectedRow(),15).toString());
            Hamil.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),16).toString());
            Kesudahan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),17).toString());   
            PenyakitLain.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),18).toString());
            Penyakit_Utama.setText(tbObat.getValueAt(tbObat.getSelectedRow(),19).toString());
            Valid.SetTgl2(TanngalMulaiTerjadi,tbObat.getValueAt(tbObat.getSelectedRow(),20).toString());
            Bentuk_Manifestasi.setText(tbObat.getValueAt(tbObat.getSelectedRow(),21).toString());
            Masalah_Kualitas.setText(tbObat.getValueAt(tbObat.getSelectedRow(),22).toString());
            Riwayat_eso.setText(tbObat.getValueAt(tbObat.getSelectedRow(),23).toString());
            Valid.SetTgl2(TanggaKesudahan,tbObat.getValueAt(tbObat.getSelectedRow(),24).toString());
            HasilKesudahan.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),25).toString());
            Obat1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),26).toString());
            Sedian1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),27).toString());
            Jkn1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),28).toString());
            Batch1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),29).toString());
            Cara1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),30).toString());
            Dosis1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),31).toString());
            Valid.SetTgl2(TanggalMulai1,tbObat.getValueAt(tbObat.getSelectedRow(),32).toString());
            Valid.SetTgl2(TanggalAkhir1,tbObat.getValueAt(tbObat.getSelectedRow(),33).toString());
            Indikasi1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),34).toString());
            Obat2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),35).toString());
            Sedian2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),36).toString());
            Jkn2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),37).toString());
            Batch2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),38).toString());
            Cara2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),39).toString());
            Dosis2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),40).toString());
            Valid.SetTgl2(TanggalMulai2,tbObat.getValueAt(tbObat.getSelectedRow(),41).toString());
            Valid.SetTgl2(TanggalAkhir2,tbObat.getValueAt(tbObat.getSelectedRow(),42).toString());
            Indikasi2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),43).toString());
            Obat3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),44).toString());
            Sedian3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),45).toString());
            Jkn3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),46).toString());
            Batch3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),47).toString());
            Cara3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),48).toString());
            Dosis3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),49).toString());
            Valid.SetTgl2(TanggalMulai3,tbObat.getValueAt(tbObat.getSelectedRow(),50).toString());
            Valid.SetTgl2(TanggalAkhir3,tbObat.getValueAt(tbObat.getSelectedRow(),51).toString());
            Indikasi3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),52).toString());
            Obat4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),53).toString());
            Sedian4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),54).toString());
            Jkn4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),55).toString());
            Batch4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),56).toString());
            Cara4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),57).toString());
            Dosis4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),58).toString());
            Valid.SetTgl2(TanggalMulai4,tbObat.getValueAt(tbObat.getSelectedRow(),59).toString());
            Valid.SetTgl2(TanggalAkhir4,tbObat.getValueAt(tbObat.getSelectedRow(),60).toString());
            Indikasi4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),61).toString());
            Obat5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),62).toString());
            Sedian5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),63).toString());
            Jkn5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),64).toString());
            Batch5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),65).toString());
            Cara5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),66).toString());
            Dosis5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),67).toString());
            Valid.SetTgl2(TanggalMulai5,tbObat.getValueAt(tbObat.getSelectedRow(),68).toString());
            Valid.SetTgl2(TanggalAkhir5,tbObat.getValueAt(tbObat.getSelectedRow(),69).toString());
            Indikasi5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),70).toString());
            Obat6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),71).toString());
            Sedian6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),72).toString());
            Jkn6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),73).toString());
            Batch6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),74).toString());
            Cara6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),75).toString());
            Dosis6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),76).toString());
            Valid.SetTgl2(TanggalMulai6,tbObat.getValueAt(tbObat.getSelectedRow(),77).toString());
            Valid.SetTgl2(TanggalAkhir6,tbObat.getValueAt(tbObat.getSelectedRow(),78).toString());
            Indikasi6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),79).toString());
            Obat7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),80).toString());
            Sedian7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),81).toString());
            Jkn7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),82).toString());
            Batch7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),83).toString());
            Cara7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),84).toString());
            Dosis7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),85).toString());
            Valid.SetTgl2(TanggalMulai7,tbObat.getValueAt(tbObat.getSelectedRow(),86).toString());
            Valid.SetTgl2(TanggalAkhir7,tbObat.getValueAt(tbObat.getSelectedRow(),87).toString());
            Indikasi7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),88).toString());
            Obat8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),89).toString());
            Sedian8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),90).toString());
            Jkn8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),91).toString());
            Batch8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),92).toString());
            Cara8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),93).toString());
            Dosis8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),94).toString());
            Valid.SetTgl2(TanggalMulai8,tbObat.getValueAt(tbObat.getSelectedRow(),95).toString());
            Valid.SetTgl2(TanggalAkhir8,tbObat.getValueAt(tbObat.getSelectedRow(),96).toString());
            Indikasi8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),97).toString());
            Obat9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),98).toString());
            Sedian9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),99).toString());
            Jkn9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),100).toString());
            Batch9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),101).toString());
            Cara9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),102).toString());
            Dosis9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),103).toString());
            Valid.SetTgl2(TanggalMulai9,tbObat.getValueAt(tbObat.getSelectedRow(),104).toString());
            Valid.SetTgl2(TanggalAkhir9,tbObat.getValueAt(tbObat.getSelectedRow(),105).toString());
            Indikasi9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),106).toString());
            Obat10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),107).toString());
            Sedian10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),108).toString());
            Jkn10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),109).toString());
            Batch10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),110).toString());
            Cara10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),111).toString());
            Dosis10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),112).toString());
            Valid.SetTgl2(TanggalMulai10,tbObat.getValueAt(tbObat.getSelectedRow(),113).toString());
            Valid.SetTgl2(TanggalAkhir10,tbObat.getValueAt(tbObat.getSelectedRow(),114).toString());
            Indikasi10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),115).toString());
            ReaksiSetelah.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),116).toString());
            ReaksiSama.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),117).toString());
            Naranjo1.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),118).toString());
            NilaiNaranjo1.setText(tbObat.getValueAt(tbObat.getSelectedRow(),119).toString());
            Naranjo2.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),120).toString());
            NilaiNaranjo2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),121).toString());
            Naranjo3.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),122).toString());
            NilaiNaranjo3.setText(tbObat.getValueAt(tbObat.getSelectedRow(),123).toString());
            Naranjo4.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),124).toString());
            NilaiNaranjo4.setText(tbObat.getValueAt(tbObat.getSelectedRow(),125).toString());
            Naranjo5.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),126).toString());
            NilaiNaranjo5.setText(tbObat.getValueAt(tbObat.getSelectedRow(),127).toString());
            Naranjo6.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),128).toString());
            NilaiNaranjo6.setText(tbObat.getValueAt(tbObat.getSelectedRow(),129).toString());
            Naranjo7.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),130).toString());
            NilaiNaranjo7.setText(tbObat.getValueAt(tbObat.getSelectedRow(),131).toString());
            Naranjo8.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),132).toString());
            NilaiNaranjo8.setText(tbObat.getValueAt(tbObat.getSelectedRow(),133).toString());
            Naranjo9.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),134).toString());
            NilaiNaranjo9.setText(tbObat.getValueAt(tbObat.getSelectedRow(),135).toString());
            Naranjo10.setSelectedItem(tbObat.getValueAt(tbObat.getSelectedRow(),136).toString());
            NilaiNaranjo10.setText(tbObat.getValueAt(tbObat.getSelectedRow(),137).toString());
            NilaiNaranjoTotal.setText(tbObat.getValueAt(tbObat.getSelectedRow(),138).toString());
            updateKategoriNaranjo();
        }
    }

    private void isRawat() {
        try {
            ps=koneksi.prepareStatement(
                    "select reg_periksa.no_rkm_medis,pasien.nm_pasien, if(pasien.jk='L','Laki-Laki','Perempuan') as jk,pasien.tgl_lahir,pasien.umur,pasien.pekerjaan,pasien.suku_bangsa,suku_bangsa.nama_suku_bangsa,"+
                    "reg_periksa.tgl_registrasi,reg_periksa.jam_reg "+
                    "from reg_periksa inner join pasien on reg_periksa.no_rkm_medis=pasien.no_rkm_medis INNER JOIN suku_bangsa on suku_bangsa.id=pasien.suku_bangsa "+
                    "where reg_periksa.no_rawat=?");
            try {
                ps.setString(1,TNoRw.getText());
                rs=ps.executeQuery();
                if(rs.next()){
                    TNoRM.setText(rs.getString("no_rkm_medis"));
                    DTPCari1.setDate(rs.getDate("tgl_registrasi"));
                    TPasien.setText(rs.getString("nm_pasien"));
                    Jk.setText(rs.getString("jk"));
                    TglLahir.setText(rs.getString("tgl_lahir"));
                    Umur.setText(rs.getString("umur"));
                    Pekerjaan.setText(rs.getString("pekerjaan"));
                    Suku.setText(rs.getString("nama_suku_bangsa"));
                    TanggalRegistrasi.setText(rs.getString("tgl_registrasi")+" "+rs.getString("jam_reg"));
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
 
    public void setNoRm(String norwt,Date tgl2) {
        TNoRw.setText(norwt);
        TCari.setText(norwt);
        DTPCari2.setDate(tgl2);    
        isRawat(); 
    }
    
    public void isCek(){
        //BtnSimpan.setEnabled(akses.getmonitoringefeksampingobat());
        //BtnHapus.setEnabled(akses.getmonitoringefeksampingobat());
        //BtnEdit.setEnabled(akses.getmonitoringefeksampingobat());
        Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_laporan,3),signed)),0) from monitoring_efek_samping_obat where tanggal='"+Valid.SetTgl(TglLaporan.getSelectedItem()+"")+"' ",
                "MESO"+TglLaporan.getSelectedItem().toString().substring(6,10)+TglLaporan.getSelectedItem().toString().substring(3,5)+TglLaporan.getSelectedItem().toString().substring(0,2),4,NoSurat);
        if(akses.getjml2()>=1){
            KdPegawai.setEditable(false);
            btnPegawai.setEnabled(false);
            KdPegawai.setText(akses.getkode());
            Sequel.cariIsi("select nama from pegawai where nik=?", NmPegawai,KdPegawai.getText());
        }   
    }
    
    public void setTampil(){
       TabRawat.setSelectedIndex(1);
    }

    private void hapus() {
        if(Sequel.queryu2tf("delete from penilaian_medis_ralan_anak where no_rawat=?",1,new String[]{
            tbObat.getValueAt(tbObat.getSelectedRow(),0).toString()
        })==true){
            tabMode.removeRow(tbObat.getSelectedRow());
            LCount.setText(""+tabMode.getRowCount());
            TabRawat.setSelectedIndex(1);
        }else{
            JOptionPane.showMessageDialog(null,"Gagal menghapus..!!");
        }
    }
    
    private void calculateTotalNaranjo() {
    int totalNaranjo = 0;

    // Ambil nilai dari label NilaiNaranjo
    totalNaranjo += Integer.parseInt(NilaiNaranjo1.getText());
    totalNaranjo += Integer.parseInt(NilaiNaranjo2.getText());
    totalNaranjo += Integer.parseInt(NilaiNaranjo3.getText());
    totalNaranjo += Integer.parseInt(NilaiNaranjo4.getText());
    totalNaranjo += Integer.parseInt(NilaiNaranjo5.getText());
    totalNaranjo += Integer.parseInt(NilaiNaranjo6.getText());
    totalNaranjo += Integer.parseInt(NilaiNaranjo7.getText());
    totalNaranjo += Integer.parseInt(NilaiNaranjo8.getText());
    totalNaranjo += Integer.parseInt(NilaiNaranjo9.getText());
    totalNaranjo += Integer.parseInt(NilaiNaranjo10.getText());

    // Set hasil ke label total
    NilaiNaranjoTotal.setText(String.valueOf(totalNaranjo));
}
    
    // Fungsi untuk memperbarui kategori Naranjo
    private void updateKategoriNaranjo() {
        int total = Integer.parseInt(NilaiNaranjoTotal.getText());
        
        if (total >= 9) {
            KategoriNaranjo.setText("Highly probable");
        } else if (total >= 5) {
            KategoriNaranjo.setText("Probable");
        } else if (total >= 1) {
            KategoriNaranjo.setText("Possible");
        } else {
            KategoriNaranjo.setText("Doubtful");
        }
    }
}
