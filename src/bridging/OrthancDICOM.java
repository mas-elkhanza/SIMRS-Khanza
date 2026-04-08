/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgAbout.java
 *
 * Created on 23 Jun 10, 19:03:08
 */

package bridging;


import fungsi.batasInput;
import fungsi.validasi;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import static javafx.concurrent.Worker.State.FAILED;
import javafx.embed.swing.JFXPanel;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 *
 * @author perpustakaan
 */
public class OrthancDICOM extends javax.swing.JDialog {
    private final JFXPanel jfxPanel = new JFXPanel();
    private WebEngine engine;
    private String urlpanggil="",namafile="",series="",study="",norawat="";
    private final JPanel panel = new JPanel(new BorderLayout());
    private final JLabel lblStatus = new JLabel();
    private final JTextField txtURL = new JTextField();
    private final JProgressBar progressBar = new JProgressBar();
    private final ApiOrthanc orthanc=new ApiOrthanc();
    private final validasi Valid=new validasi();
    private OrthancDataACSN dataacsn;
    
    public OrthancDICOM(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initComponents2();
        AccessionNumber.setDocument(new batasInput((byte)16).getKata(AccessionNumber));
    }
    
    private void initComponents2() {           
        txtURL.addActionListener((ActionEvent e) -> {
            loadURL(txtURL.getText());
        });
  
        progressBar.setPreferredSize(new Dimension(550, 508));
        progressBar.setStringPainted(true);
        panel.add(jfxPanel, BorderLayout.CENTER);
        internalFrame1.setLayout(new BorderLayout());
        internalFrame1.add(panel, BorderLayout.CENTER);    
        internalFrame1.add(PanelMenu,BorderLayout.AFTER_LAST_LINE);        
    }
    
     private void createScene() {        
        Platform.runLater(new Runnable() {
            public void run() {
                WebView view = new WebView();
                
                engine = view.getEngine();
                engine.setJavaScriptEnabled(true);
                
                engine.setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {
                    @Override
                    public WebEngine call(PopupFeatures p) {
                        Stage stage = new Stage(StageStyle.TRANSPARENT);
                        return view.getEngine();
                    }
                });
                
                engine.titleProperty().addListener((ObservableValue<? extends String> observable, String oldValue, final String newValue) -> {
                    SwingUtilities.invokeLater(() -> {
                        OrthancDICOM.this.setTitle(newValue);
                    });
                });
                
                
                engine.setOnStatusChanged((final WebEvent<String> event) -> {
                    SwingUtilities.invokeLater(() -> {
                        lblStatus.setText(event.getData());
                    });
                });
                
                
                engine.getLoadWorker().workDoneProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, final Number newValue) -> {
                    SwingUtilities.invokeLater(() -> {
                        progressBar.setValue(newValue.intValue());
                    });                                                   
                });
                
                engine.getLoadWorker().exceptionProperty().addListener((ObservableValue<? extends Throwable> o, Throwable old, final Throwable value) -> {
                    if (engine.getLoadWorker().getState() == FAILED) {
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(
                                    panel,
                                    (value != null) ?
                                            engine.getLocation() + "\n" + value.getMessage() :
                                            engine.getLocation() + "\nUnexpected Catatan.",
                                    "Loading Catatan...",
                                    JOptionPane.ERROR_MESSAGE);
                        });
                    }
                });
                
                engine.locationProperty().addListener((ObservableValue<? extends String> ov, String oldValue, final String newValue) -> {
                    SwingUtilities.invokeLater(() -> {
                        txtURL.setText(newValue);
                    });
                });
                
                engine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>() {
                    @Override
                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        if (newState == Worker.State.SUCCEEDED) {
                            try {
                                if(engine.getLocation().contains("https://www.orthanc-server.com")){
                                    setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                    Valid.panggilUrl2(urlpanggil);
                                    engine.executeScript("history.back()");
                                    setCursor(Cursor.getDefaultCursor());
                                }
                            } catch (Exception ex) {
                                System.out.println("Notifikasi : "+ex);
                            }
                        } 
                    }
                });
                
                jfxPanel.setScene(new Scene(view));
            }
        });
    }
 
    public void loadURL(String url) {  
        urlpanggil=url;
        try {
            createScene();
        } catch (Exception e) {
        }
        
        Platform.runLater(() -> {
            try {
                engine.getCreatePopupHandler();
                engine.setJavaScriptEnabled(true);
                engine.setUserAgent("foo\nAuthorization: Basic "+orthanc.Auth());
                engine.load(url);
            }catch (Exception exception) {
                engine.load(url);
            }
        });        
    }    
    
    public void CloseScane(){
        Platform.setImplicitExit(false);
    }
    
    public void print(final Node node) {
        Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.NA_LETTER, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
        double scaleX = pageLayout.getPrintableWidth() / node.getBoundsInParent().getWidth();
        double scaleY = pageLayout.getPrintableHeight() / node.getBoundsInParent().getHeight();
        node.getTransforms().add(new Scale(scaleX, scaleY));

        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
            }
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

        PanelMenu = new widget.panelisi();
        BtnPng = new widget.Button();
        BtnJpg = new widget.Button();
        BtnBmp = new widget.Button();
        BtnDcm = new widget.Button();
        BtnACSN = new widget.Button();
        BtnKeluar = new widget.Button();
        WindowGantiACSN = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        BtnCloseIn4 = new widget.Button();
        BtnSimpan4 = new widget.Button();
        jLabel14 = new widget.Label();
        AccessionNumber = new widget.TextBox();
        BtnCariACSN = new widget.Button();
        internalFrame1 = new widget.InternalFrame();

        PanelMenu.setName("PanelMenu"); // NOI18N
        PanelMenu.setPreferredSize(new java.awt.Dimension(44, 34));
        PanelMenu.setLayout(new java.awt.GridLayout(1, 0));

        BtnPng.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/3079288_adobe file extensions_adobe fireworks_document_extension icon_file_icon.png"))); // NOI18N
        BtnPng.setMnemonic('P');
        BtnPng.setText("Download PNG");
        BtnPng.setToolTipText("Alt+P");
        BtnPng.setName("BtnPng"); // NOI18N
        BtnPng.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPngActionPerformed(evt);
            }
        });
        PanelMenu.add(BtnPng);

        BtnJpg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/2276087_document_extension_format_jpg_paper_icon.png"))); // NOI18N
        BtnJpg.setMnemonic('D');
        BtnJpg.setText("Download JPG");
        BtnJpg.setToolTipText("Alt+D");
        BtnJpg.setName("BtnJpg"); // NOI18N
        BtnJpg.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnJpg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnJpgActionPerformed(evt);
            }
        });
        PanelMenu.add(BtnJpg);

        BtnBmp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/2315911_bmp_documents_file_format_paper_icon.png"))); // NOI18N
        BtnBmp.setMnemonic('D');
        BtnBmp.setText("Download BMP");
        BtnBmp.setToolTipText("Alt+D");
        BtnBmp.setName("BtnBmp"); // NOI18N
        BtnBmp.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBmp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBmpActionPerformed(evt);
            }
        });
        PanelMenu.add(BtnBmp);

        BtnDcm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16i.png"))); // NOI18N
        BtnDcm.setMnemonic('D');
        BtnDcm.setText("Download DCM");
        BtnDcm.setToolTipText("Alt+D");
        BtnDcm.setName("BtnDcm"); // NOI18N
        BtnDcm.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnDcm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDcmActionPerformed(evt);
            }
        });
        PanelMenu.add(BtnDcm);

        BtnACSN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/inventaris.png"))); // NOI18N
        BtnACSN.setMnemonic('D');
        BtnACSN.setText("Update ACSN");
        BtnACSN.setToolTipText("Alt+D");
        BtnACSN.setName("BtnACSN"); // NOI18N
        BtnACSN.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnACSN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnACSNActionPerformed(evt);
            }
        });
        PanelMenu.add(BtnACSN);

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
        PanelMenu.add(BtnKeluar);

        WindowGantiACSN.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        WindowGantiACSN.setName("WindowGantiACSN"); // NOI18N
        WindowGantiACSN.setUndecorated(true);
        WindowGantiACSN.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Ganti Accession Number ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 50, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(null);

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
        internalFrame5.add(BtnCloseIn4);
        BtnCloseIn4.setBounds(370, 20, 100, 30);

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
        internalFrame5.add(BtnSimpan4);
        BtnSimpan4.setBounds(265, 20, 100, 30);

        jLabel14.setText("ACSN :");
        jLabel14.setName("jLabel14"); // NOI18N
        internalFrame5.add(jLabel14);
        jLabel14.setBounds(0, 22, 50, 23);

        AccessionNumber.setHighlighter(null);
        AccessionNumber.setName("AccessionNumber"); // NOI18N
        AccessionNumber.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AccessionNumberKeyPressed(evt);
            }
        });
        internalFrame5.add(AccessionNumber);
        AccessionNumber.setBounds(54, 22, 170, 23);

        BtnCariACSN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnCariACSN.setMnemonic('7');
        BtnCariACSN.setToolTipText("ALt+7");
        BtnCariACSN.setName("BtnCariACSN"); // NOI18N
        BtnCariACSN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariACSNActionPerformed(evt);
            }
        });
        internalFrame5.add(BtnCariACSN);
        BtnCariACSN.setBounds(226, 22, 28, 23);

        WindowGantiACSN.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("::[ About Program ]::");
        setUndecorated(true);
        setResizable(false);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(1, 1));

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Helvetica Neue", 0, 13), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setPreferredSize(new java.awt.Dimension(500, 500));
        internalFrame1.setLayout(new java.awt.BorderLayout());
        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        Platform.setImplicitExit(false);
    }//GEN-LAST:event_formWindowClosed

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        if(this.isActive()==false){
            Platform.setImplicitExit(false);
        }
    }//GEN-LAST:event_formWindowStateChanged

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            dispose();
        }
    }//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnPngActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPngActionPerformed
        orthanc.AmbilPng(namafile,series);
    }//GEN-LAST:event_BtnPngActionPerformed

    private void BtnDcmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDcmActionPerformed
        orthanc.AmbilDcm(namafile,series);
    }//GEN-LAST:event_BtnDcmActionPerformed

    private void BtnJpgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnJpgActionPerformed
        orthanc.AmbilJpg(namafile,series);
    }//GEN-LAST:event_BtnJpgActionPerformed

    private void BtnBmpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBmpActionPerformed
        orthanc.AmbilBmp(namafile,series);
    }//GEN-LAST:event_BtnBmpActionPerformed

    private void BtnACSNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnACSNActionPerformed
        WindowGantiACSN.setSize(482,62);
        WindowGantiACSN.setLocationRelativeTo(internalFrame1);
        WindowGantiACSN.setAlwaysOnTop(false);
        WindowGantiACSN.setVisible(true);
    }//GEN-LAST:event_BtnACSNActionPerformed

    private void BtnCloseIn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCloseIn4ActionPerformed
        WindowGantiACSN.dispose();
    }//GEN-LAST:event_BtnCloseIn4ActionPerformed

    private void BtnSimpan4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpan4ActionPerformed
        if(AccessionNumber.getText().trim().equals("")){
            Valid.textKosong(AccessionNumber,"Accession Number");
        }else{
            if(orthanc.UbahAccession(study,AccessionNumber.getText())==true){
                JOptionPane.showMessageDialog(null,"Update accession number selesai..!!");
            }
        }
    }//GEN-LAST:event_BtnSimpan4ActionPerformed

    private void AccessionNumberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AccessionNumberKeyPressed
        Valid.pindah(evt, BtnCloseIn4, BtnCariACSN);
    }//GEN-LAST:event_AccessionNumberKeyPressed

    private void BtnCariACSNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariACSNActionPerformed
        if (dataacsn == null || !dataacsn.isDisplayable()) {
            dataacsn=new OrthancDataACSN(null,false);
            dataacsn.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            dataacsn.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    if(dataacsn.getTable().getSelectedRow()!= -1){
                        AccessionNumber.setText(dataacsn.getTable().getValueAt(dataacsn.getTable().getSelectedRow(),0).toString());
                    }  
                    AccessionNumber.requestFocus();
                    dataacsn=null;
                }
            });
            
            dataacsn.getTable().addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        dataacsn.dispose();
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {}
            });
                    
            dataacsn.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dataacsn.setLocationRelativeTo(internalFrame1);
        }
        if (dataacsn == null) return;
        if (!dataacsn.isVisible()) {
            dataacsn.setNoRawat(norawat);
            dataacsn.tampil("");
        }
        if (dataacsn.isVisible()) {
            dataacsn.toFront();
            return;
        }    
        dataacsn.setVisible(true);
    }//GEN-LAST:event_BtnCariACSNActionPerformed

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            OrthancDICOM dialog = new OrthancDICOM(new javax.swing.JFrame(), true);
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
    private widget.TextBox AccessionNumber;
    private widget.Button BtnACSN;
    private widget.Button BtnBmp;
    private widget.Button BtnCariACSN;
    private widget.Button BtnCloseIn4;
    private widget.Button BtnDcm;
    private widget.Button BtnJpg;
    private widget.Button BtnKeluar;
    private widget.Button BtnPng;
    private widget.Button BtnSimpan4;
    private widget.panelisi PanelMenu;
    private javax.swing.JDialog WindowGantiACSN;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame5;
    private widget.Label jLabel14;
    // End of variables declaration//GEN-END:variables

    public void setJudul(String Judul,String NamaFile,String Series,String Study,String NoRawat){
        study=Study;
        namafile=NamaFile;
        series=Series;
        norawat=NoRawat;
        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), Judul, javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70,70,70))); 
    }
}
