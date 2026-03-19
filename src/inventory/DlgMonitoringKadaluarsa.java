package inventory;

import fungsi.WarnaTable;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.akses;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.toedter.calendar.JDateChooser;

public class DlgMonitoringKadaluarsa extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel = new sekuel();
    private validasi Valid = new validasi();
    private Connection koneksi = koneksiDB.condb();
    private PreparedStatement ps;
    private ResultSet rs;
    
    // Components
    private JDateChooser tglDari;
    private JDateChooser tglSampai;
    private JComboBox<String> cmbLokasi;
    private JButton btnTampilkan;
    private JButton btn7Hari;
    private JButton btn14Hari;
    private JButton btn30Hari;
    private JButton btnTutup;
    private JLabel lblTotal;
    private JLabel lblUrgent;
    private JLabel lblWarning;
    private JLabel lblPerhatian;
    private JTable tabelKadaluarsa;
    
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat displayFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    /** Creates new form DlgMonitoringKadaluarsa */
    public DlgMonitoringKadaluarsa(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        // Initialize table model - Harga Beli dihapus, Lokasi ditambahkan
        tabMode = new DefaultTableModel(null, new Object[]{
            "No", "Kode Obat", "Nama Obat", "Lokasi", "No Faktur", "No Batch", 
            "Tgl Beli", "Tgl Kadaluarsa", "Sisa Hari", "Stok", "Tgl Opname"
        }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        initComponents();
        
        // Load data lokasi ke combobox
        loadLokasi();
        
        // Dapatkan ukuran layar
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        
        // Sesuaikan ukuran form berdasarkan resolusi layar
        int formWidth = Math.min(1200, screenWidth - 50);
        int formHeight = Math.min(700, screenHeight - 100);
        
        setSize(formWidth, formHeight);
        setLocationRelativeTo(null); // Center di layar
        
        // Auto load data 30 hari saat form dibuka
        SwingUtilities.invokeLater(() -> btn30Hari.doClick());
    }
    
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Monitoring Obat Akan Kadaluarsa");
        setResizable(true); // Bisa diresize
        
        getContentPane().setLayout(new BorderLayout(5, 5));
        
        // Panel Header - Filter Controls
        JPanel panelHeader = createHeaderPanel();
        getContentPane().add(panelHeader, BorderLayout.NORTH);
        
        // Panel Center - Table
        JPanel panelCenter = createTablePanel();
        getContentPane().add(panelCenter, BorderLayout.CENTER);
        
        // Panel Footer - Summary & Actions
        JPanel panelFooter = createFooterPanel();
        getContentPane().add(panelFooter, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.setBackground(new Color(240, 248, 255));
        
        // Panel utama untuk filter (menggunakan BoxLayout vertikal)
        JPanel panelMain = new JPanel();
        panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));
        panelMain.setOpaque(false);
        
        // Panel Periode dan Lokasi (baris pertama)
        JPanel panelPeriode = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
        panelPeriode.setOpaque(false);
        
        JLabel lblPeriode = new JLabel("Periode:");
        lblPeriode.setFont(new Font("Tahoma", Font.BOLD, 11));
        panelPeriode.add(lblPeriode);
        
        tglDari = new JDateChooser();
        tglDari.setDateFormatString("dd-MM-yyyy");
        tglDari.setPreferredSize(new Dimension(120, 23));
        tglDari.setDate(new Date());
        panelPeriode.add(new JLabel("Dari:"));
        panelPeriode.add(tglDari);
        
        tglSampai = new JDateChooser();
        tglSampai.setDateFormatString("dd-MM-yyyy");
        tglSampai.setPreferredSize(new Dimension(120, 23));
        panelPeriode.add(new JLabel("s/d:"));
        panelPeriode.add(tglSampai);
        
        // Combobox Lokasi
        panelPeriode.add(Box.createHorizontalStrut(15)); // Spacer
        JLabel lblLokasi = new JLabel("Lokasi:");
        lblLokasi.setFont(new Font("Tahoma", Font.BOLD, 11));
        panelPeriode.add(lblLokasi);
        
        cmbLokasi = new JComboBox<>();
        cmbLokasi.setPreferredSize(new Dimension(180, 23));
        cmbLokasi.setFont(new Font("Tahoma", Font.PLAIN, 11));
        panelPeriode.add(cmbLokasi);
        
        btnTampilkan = new JButton("Tampilkan");
        btnTampilkan.setPreferredSize(new Dimension(100, 23));
        btnTampilkan.setMnemonic(KeyEvent.VK_T);
        btnTampilkan.addActionListener(e -> tampilkanData());
        btnTampilkan.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    tampilkanData();
                }
            }
        });
        panelPeriode.add(btnTampilkan);
        
        panelMain.add(panelPeriode);
        
        // Panel Quick Filters (baris kedua)
        JPanel panelQuick = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
        panelQuick.setOpaque(false);
        
        btn7Hari = createQuickButton("7 Hari", new Color(220, 53, 69), 7);
        btn14Hari = createQuickButton("14 Hari", new Color(255, 193, 7), 14);
        btn30Hari = createQuickButton("30 Hari", new Color(40, 167, 69), 30);
        
        panelQuick.add(btn7Hari);
        panelQuick.add(btn14Hari);
        panelQuick.add(btn30Hari);
        
        panelMain.add(panelQuick);
        
        panel.add(panelMain, BorderLayout.WEST);
        
        return panel;
    }
    
    private JButton createQuickButton(String text, Color color, int days) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(80, 26));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Tahoma", Font.BOLD, 10));
        btn.addActionListener(e -> quickFilter(days, btn));
        return btn;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        
        tabelKadaluarsa = new JTable(tabMode);
        tabelKadaluarsa.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabelKadaluarsa.setRowHeight(24);
        tabelKadaluarsa.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 11));
        tabelKadaluarsa.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tabelKadaluarsa.getTableHeader().setReorderingAllowed(false);
        
        // Set fixed width untuk semua kolom kecuali Nama Obat (index 2)
        int[] columnWidths = {45, 85, 200, 160, 130, 85, 80, 95, 70, 70, 90};
        for (int i = 0; i < columnWidths.length; i++) {
            TableColumn column = tabelKadaluarsa.getColumnModel().getColumn(i);
            if (i == 2) {
                // Kolom Nama Obat - flexible
                column.setMinWidth(150);
                column.setPreferredWidth(200);
                column.setMaxWidth(Integer.MAX_VALUE);
            } else {
                // Kolom lainnya - fixed
                column.setMinWidth(columnWidths[i]);
                column.setPreferredWidth(columnWidths[i]);
                column.setMaxWidth(columnWidths[i]);
            }
        }
        
        // Custom renderer untuk color coding
        tabelKadaluarsa.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    try {
                        String sisaHariStr = table.getValueAt(row, 8).toString();
                        int sisaHari = Integer.parseInt(sisaHariStr);
                        
                        if (sisaHari <= 7) {
                            c.setBackground(new Color(255, 220, 220)); // Merah muda
                            c.setForeground(Color.BLACK);
                        } else if (sisaHari <= 14) {
                            c.setBackground(new Color(255, 243, 205)); // Orange muda
                            c.setForeground(Color.BLACK);
                        } else if (sisaHari <= 30) {
                            c.setBackground(new Color(255, 255, 224)); // Kuning muda
                            c.setForeground(Color.BLACK);
                        } else {
                            c.setBackground(Color.WHITE);
                            c.setForeground(Color.BLACK);
                        }
                    } catch (Exception e) {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }
                
                // Alignment per kolom
                if (column == 0 || column == 1 || column == 6 || column == 7 || column == 8 || column == 9 || column == 10) {
                    // No, Kode Obat, Tgl Beli, Tgl Kadaluarsa, Sisa Hari, Stok, Tgl Opname
                    setHorizontalAlignment(JLabel.CENTER);
                } else {
                    // Nama Obat, Lokasi, No Faktur, No Batch
                    setHorizontalAlignment(JLabel.LEFT);
                }
                
                return c;
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tabelKadaluarsa);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        // Listener untuk auto-resize kolom Nama Obat saat form diresize
        scrollPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                adjustNamaObatColumn();
            }
        });
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void adjustNamaObatColumn() {
        if (tabelKadaluarsa == null) return;
        
        try {
            JScrollPane scrollPane = (JScrollPane) tabelKadaluarsa.getParent().getParent();
            int availableWidth = scrollPane.getViewport().getWidth();
            
            // Hitung total width kolom fixed
            int fixedWidth = 0;
            for (int i = 0; i < tabelKadaluarsa.getColumnCount(); i++) {
                if (i != 2) { // Skip kolom Nama Obat
                    fixedWidth += tabelKadaluarsa.getColumnModel().getColumn(i).getWidth();
                }
            }
            
            // Sisa width untuk kolom Nama Obat
            int namaObatWidth = availableWidth - fixedWidth - 20; // 20px untuk margin
            if (namaObatWidth < 150) namaObatWidth = 150; // Minimum width
            
            TableColumn namaObatColumn = tabelKadaluarsa.getColumnModel().getColumn(2);
            namaObatColumn.setPreferredWidth(namaObatWidth);
        } catch (Exception e) {
            // Ignore
        }
    }
    
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        panel.setBackground(new Color(248, 249, 250));
        
        // Panel Summary
        JPanel panelSummary = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 3));
        panelSummary.setOpaque(false);
        
        lblTotal = createSummaryLabel("Total: 0 item", new Color(108, 117, 125));
        lblUrgent = createSummaryLabel("Urgent (≤7): 0", new Color(220, 53, 69));
        lblWarning = createSummaryLabel("Warning (8-14): 0", new Color(255, 193, 7));
        lblPerhatian = createSummaryLabel("Perhatian (15-30): 0", new Color(40, 167, 69));
        
        panelSummary.add(lblTotal);
        panelSummary.add(lblUrgent);
        panelSummary.add(lblWarning);
        panelSummary.add(lblPerhatian);
        
        panel.add(panelSummary, BorderLayout.WEST);
        
        // Panel Buttons
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 3));
        panelButtons.setOpaque(false);
        
        btnTutup = new JButton("Tutup");
        btnTutup.setPreferredSize(new Dimension(90, 26));
        btnTutup.setMnemonic(KeyEvent.VK_U);
        btnTutup.addActionListener(e -> dispose());
        btnTutup.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    dispose();
                }
            }
        });
        panelButtons.add(btnTutup);
        
        panel.add(panelButtons, BorderLayout.EAST);
        
        return panel;
    }
    
    private JLabel createSummaryLabel(String text, Color color) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Tahoma", Font.BOLD, 11));
        lbl.setForeground(color);
        return lbl;
    }
    
    private void quickFilter(int days, JButton activeButton) {
        // Reset button colors
        resetQuickButtons();
        
        // Highlight active button
        activeButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        // Set tanggal
        Date today = new Date();
        tglDari.setDate(today);
        
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(today);
        cal.add(java.util.Calendar.DAY_OF_MONTH, days);
        tglSampai.setDate(cal.getTime());
        
        // Tampilkan data
        tampilkanData();
    }
    
    private void resetQuickButtons() {
        btn7Hari.setBorder(UIManager.getBorder("Button.border"));
        btn14Hari.setBorder(UIManager.getBorder("Button.border"));
        btn30Hari.setBorder(UIManager.getBorder("Button.border"));
    }
    
    private void loadLokasi() {
        try {
            cmbLokasi.removeAllItems();
            cmbLokasi.addItem("-- Semua Lokasi --");
            cmbLokasi.addItem("-- Belum Opname (Lokasi -) --");
            
            String sql = "SELECT kd_bangsal, nm_bangsal FROM bangsal WHERE status='1' ORDER BY nm_bangsal";
            ps = koneksi.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                // Simpan kode bangsal di dalam object untuk keperluan query
                cmbLokasi.addItem(rs.getString("nm_bangsal"));
            }
            
            cmbLokasi.setSelectedIndex(0);
        } catch (Exception e) {
            System.out.println("Error load lokasi: " + e);
        }
    }
    
    private void tampilkanData() {
        if (tglDari.getDate() == null || tglSampai.getDate() == null) {
            JOptionPane.showMessageDialog(this, 
                "Silakan pilih periode tanggal kadaluarsa!", 
                "Peringatan", 
                JOptionPane.WARNING_MESSAGE);
            tglDari.requestFocus();
            return;
        }
        
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        // Clear table
        Valid.tabelKosong(tabMode);
        
        int totalItem = 0;
        int urgentCount = 0;
        int warningCount = 0;
        int perhatianCount = 0;
        
        try {
            // Ambil filter lokasi
            String filterLokasi = "";
            String selectedLokasi = (String) cmbLokasi.getSelectedItem();
            String kdBangsal = "";
            
            if (selectedLokasi != null && !selectedLokasi.equals("-- Semua Lokasi --")) {
                if (selectedLokasi.equals("-- Belum Opname (Lokasi -) --")) {
                    filterLokasi = "BELUM_OPNAME";
                } else {
                    // Cari kode bangsal berdasarkan nama bangsal
                    try {
                        String sqlBangsal = "SELECT kd_bangsal FROM bangsal WHERE nm_bangsal = ?";
                        PreparedStatement psBangsal = koneksi.prepareStatement(sqlBangsal);
                        psBangsal.setString(1, selectedLokasi);
                        ResultSet rsBangsal = psBangsal.executeQuery();
                        if (rsBangsal.next()) {
                            kdBangsal = rsBangsal.getString("kd_bangsal");
                            filterLokasi = "BANGSAL";
                        }
                        rsBangsal.close();
                        psBangsal.close();
                    } catch (Exception ex) {
                        System.out.println("Error get kode bangsal: " + ex);
                    }
                }
            }
            
            // Query dengan stok terpisah per bangsal/ruangan
            String sql = "";
            
            if (filterLokasi.equals("BELUM_OPNAME")) {
                // Hanya tampilkan obat yang belum di-opname
                sql = "SELECT " +
                    "db.kode_brng, " +
                    "d.nama_brng, " +
                    "'-' as lokasi, " +
                    "db.no_faktur, " +
                    "db.no_batch, " +
                    "db.tgl_beli, " +
                    "db.tgl_kadaluarsa, " +
                    "DATEDIFF(db.tgl_kadaluarsa, CURDATE()) as sisa_hari, " +
                    "db.sisa as stok_real, " +
                    "NULL as tgl_opname " +
                    "FROM data_batch db " +
                    "INNER JOIN databarang d ON db.kode_brng = d.kode_brng " +
                    "WHERE db.tgl_kadaluarsa BETWEEN ? AND ? " +
                    "  AND db.tgl_kadaluarsa >= CURDATE() " +
                    "  AND db.sisa > 0 " +
                    "  AND NOT EXISTS ( " +
                    "    SELECT 1 FROM opname o " +
                    "    WHERE o.kode_brng = db.kode_brng " +
                    "      AND o.no_batch = db.no_batch " +
                    "      AND o.no_faktur = db.no_faktur " +
                    "  ) " +
                    "ORDER BY db.tgl_kadaluarsa ASC, d.nama_brng ASC";
            } else if (filterLokasi.equals("BANGSAL")) {
                // Hanya tampilkan obat di bangsal tertentu
                sql = "SELECT " +
                    "db.kode_brng, " +
                    "d.nama_brng, " +
                    "b.nm_bangsal as lokasi, " +
                    "db.no_faktur, " +
                    "db.no_batch, " +
                    "db.tgl_beli, " +
                    "db.tgl_kadaluarsa, " +
                    "DATEDIFF(db.tgl_kadaluarsa, CURDATE()) as sisa_hari, " +
                    "o.real as stok_real, " +
                    "o.tanggal as tgl_opname " +
                    "FROM data_batch db " +
                    "INNER JOIN databarang d ON db.kode_brng = d.kode_brng " +
                    "INNER JOIN opname o ON db.kode_brng = o.kode_brng " +
                    "   AND db.no_batch = o.no_batch " +
                    "   AND db.no_faktur = o.no_faktur " +
                    "INNER JOIN bangsal b ON o.kd_bangsal = b.kd_bangsal " +
                    "WHERE db.tgl_kadaluarsa BETWEEN ? AND ? " +
                    "  AND db.tgl_kadaluarsa >= CURDATE() " +
                    "  AND o.real > 0 " +
                    "  AND o.kd_bangsal = ? " +
                    "  AND o.tanggal = ( " +
                    "    SELECT MAX(o2.tanggal) " +
                    "    FROM opname o2 " +
                    "    WHERE o2.kode_brng = o.kode_brng " +
                    "      AND o2.no_batch = o.no_batch " +
                    "      AND o2.no_faktur = o.no_faktur " +
                    "      AND o2.kd_bangsal = o.kd_bangsal " +
                    "  ) " +
                    "ORDER BY db.tgl_kadaluarsa ASC, d.nama_brng ASC";
            } else {
                // Tampilkan semua (dengan UNION seperti sebelumnya)
                sql = "SELECT " +
                    "db.kode_brng, " +
                    "d.nama_brng, " +
                    "b.nm_bangsal as lokasi, " +
                    "db.no_faktur, " +
                    "db.no_batch, " +
                    "db.tgl_beli, " +
                    "db.tgl_kadaluarsa, " +
                    "DATEDIFF(db.tgl_kadaluarsa, CURDATE()) as sisa_hari, " +
                    "o.real as stok_real, " +
                    "o.tanggal as tgl_opname " +
                    "FROM data_batch db " +
                    "INNER JOIN databarang d ON db.kode_brng = d.kode_brng " +
                    "INNER JOIN opname o ON db.kode_brng = o.kode_brng " +
                    "   AND db.no_batch = o.no_batch " +
                    "   AND db.no_faktur = o.no_faktur " +
                    "INNER JOIN bangsal b ON o.kd_bangsal = b.kd_bangsal " +
                    "WHERE db.tgl_kadaluarsa BETWEEN ? AND ? " +
                    "  AND db.tgl_kadaluarsa >= CURDATE() " +
                    "  AND o.real > 0 " +
                    "  AND o.tanggal = ( " +
                    "    SELECT MAX(o2.tanggal) " +
                    "    FROM opname o2 " +
                    "    WHERE o2.kode_brng = o.kode_brng " +
                    "      AND o2.no_batch = o.no_batch " +
                    "      AND o2.no_faktur = o.no_faktur " +
                    "      AND o2.kd_bangsal = o.kd_bangsal " +
                    "  ) " +
                    "UNION " +
                    "SELECT " +
                    "db.kode_brng, " +
                    "d.nama_brng, " +
                    "'-' as lokasi, " +
                    "db.no_faktur, " +
                    "db.no_batch, " +
                    "db.tgl_beli, " +
                    "db.tgl_kadaluarsa, " +
                    "DATEDIFF(db.tgl_kadaluarsa, CURDATE()) as sisa_hari, " +
                    "db.sisa as stok_real, " +
                    "NULL as tgl_opname " +
                    "FROM data_batch db " +
                    "INNER JOIN databarang d ON db.kode_brng = d.kode_brng " +
                    "WHERE db.tgl_kadaluarsa BETWEEN ? AND ? " +
                    "  AND db.tgl_kadaluarsa >= CURDATE() " +
                    "  AND db.sisa > 0 " +
                    "  AND NOT EXISTS ( " +
                    "    SELECT 1 FROM opname o " +
                    "    WHERE o.kode_brng = db.kode_brng " +
                    "      AND o.no_batch = db.no_batch " +
                    "      AND o.no_faktur = db.no_faktur " +
                    "  ) " +
                    "ORDER BY tgl_kadaluarsa ASC, lokasi ASC, nama_brng ASC";
            }
            
            ps = koneksi.prepareStatement(sql);
            ps.setString(1, dateFormat.format(tglDari.getDate()));
            ps.setString(2, dateFormat.format(tglSampai.getDate()));
            
            if (filterLokasi.equals("BANGSAL")) {
                ps.setString(3, kdBangsal);
            } else if (filterLokasi.isEmpty()) {
                ps.setString(3, dateFormat.format(tglDari.getDate()));
                ps.setString(4, dateFormat.format(tglSampai.getDate()));
            }
            
            rs = ps.executeQuery();
            
            int no = 1;
            while (rs.next()) {
                int sisaHari = rs.getInt("sisa_hari");
                
                // Hitung summary
                totalItem++;
                if (sisaHari <= 7) urgentCount++;
                else if (sisaHari <= 14) warningCount++;
                else if (sisaHari <= 30) perhatianCount++;
                
                // Format tanggal opname
                String tglOpnameStr = "-";
                Date tglOpname = rs.getDate("tgl_opname");
                if (tglOpname != null) {
                    tglOpnameStr = displayFormat.format(tglOpname);
                }
                
                tabMode.addRow(new Object[]{
                    no++,
                    rs.getString("kode_brng"),
                    rs.getString("nama_brng"),
                    rs.getString("lokasi"),
                    rs.getString("no_faktur"),
                    rs.getString("no_batch"),
                    displayFormat.format(rs.getDate("tgl_beli")),
                    displayFormat.format(rs.getDate("tgl_kadaluarsa")),
                    sisaHari,
                    Valid.SetAngka(rs.getDouble("stok_real")),
                    tglOpnameStr
                });
            }
            
            // Update summary labels
            lblTotal.setText("Total: " + totalItem + " item");
            lblUrgent.setText("Urgent (≤7): " + urgentCount);
            lblWarning.setText("Warning (8-14): " + warningCount);
            lblPerhatian.setText("Perhatian (15-30): " + perhatianCount);
            
            if (totalItem == 0) {
                JOptionPane.showMessageDialog(this, 
                    "Tidak ada data obat yang akan kadaluarsa pada periode tersebut.", 
                    "Informasi", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
        } catch (Exception e) {
            System.out.println("Notifikasi : " + e);
        } finally {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }
    
    /**
     * Method untuk memanggil form dari form lain
     * @param parent Frame parent
     * @param modal Apakah dialog modal atau tidak
     */
    public static void tampilForm(java.awt.Frame parent, boolean modal) {
        DlgMonitoringKadaluarsa dialog = new DlgMonitoringKadaluarsa(parent, modal);
        dialog.setVisible(true);
    }
    
    /**
     * Method overload untuk memanggil dengan default modal = true
     * @param parent Frame parent
     */
    public static void tampilForm(java.awt.Frame parent) {
        tampilForm(parent, true);
    }
    
    /**
     * Method untuk memanggil dari internalFrame
     * @param parent Frame parent
     * @param internalFrame Panel internal frame untuk set size
     */
    public static void tampilForm(java.awt.Frame parent, javax.swing.JPanel internalFrame) {
        DlgMonitoringKadaluarsa dialog = new DlgMonitoringKadaluarsa(parent, true);
        if(internalFrame != null) {
            dialog.setSize(internalFrame.getWidth()-20, internalFrame.getHeight()-20);
            dialog.setLocationRelativeTo(internalFrame);
        }
        dialog.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgMonitoringKadaluarsa dialog = new DlgMonitoringKadaluarsa(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }
}