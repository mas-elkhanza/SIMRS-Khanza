package informasi;

import com.toedter.calendar.JDateChooser;
import fungsi.koneksiDB;
import fungsi.sekuel;
import kepegawaian.DlgCariPegawai;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class FormPengumumanLogin extends JDialog {

    private JTextField txtJudul;
    private JTextArea txtIsiPengumuman;
    private JDateChooser tglMulai, tglSelesai;
    private JComboBox<String> cmbAktif, cmbTipePengumuman;
    private JButton btnSimpan, btnBatal, btnHapus, btnEdit, btnHapusUser;
    private JTable tabelPengumuman, tabelTargetUser;
    private Connection koneksi;
    private sekuel Sequel = new sekuel();
    private int selectedId = 0;
    private ArrayList<String> selectedUsers = new ArrayList<>();

    public FormPengumumanLogin(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        try {
            koneksi = koneksiDB.condb();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal koneksi database: " + e.getMessage());
        }
        initComponents();
        this.setTitle("Kelola Pengumuman Login");
        loadData(); // Ganti loadData() dengan tampil()
        //kosongkan();
        setSize(1300, 800);
        setLocationRelativeTo(parent);
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(245, 245, 245));

        // Main Container Panel
        JPanel containerPanel = new JPanel(new BorderLayout(10, 10));
        containerPanel.setBackground(new Color(245, 245, 245));
        containerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== HEADER PANEL =====
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(252, 228, 236));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(248, 187, 208), 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblTitle = new JLabel("📢 Kelola Pengumuman Login");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(new Color(136, 14, 79));

        JLabel lblSubtitle = new JLabel("Buat pengumuman Public untuk semua user atau Private untuk user tertentu");
        lblSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblSubtitle.setForeground(new Color(100, 100, 100));

        JPanel titlePanel = new JPanel(new GridLayout(2, 1, 0, 5));
        titlePanel.setBackground(new Color(252, 228, 236));
        titlePanel.add(lblTitle);
        titlePanel.add(lblSubtitle);

        headerPanel.add(titlePanel, BorderLayout.CENTER);
        containerPanel.add(headerPanel, BorderLayout.NORTH);

        // ===== SPLIT PANEL =====
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(600);
        splitPane.setDividerSize(8);
        splitPane.setBorder(null);

        // ===== LEFT PANEL - INPUT FORM =====
        JPanel leftPanel = new JPanel(new BorderLayout(5, 5));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        // Judul
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.weightx = 0;
        JLabel lblJudul = new JLabel("Judul Pengumuman");
        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 12));
        formPanel.add(lblJudul, gbc);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.weightx = 1.0;
        txtJudul = new JTextField();
        txtJudul.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtJudul.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        formPanel.add(txtJudul, gbc);

        // Isi Pengumuman
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.weightx = 1.0;
        JLabel lblIsi = new JLabel("Isi Pengumuman");
        lblIsi.setFont(new Font("Segoe UI", Font.BOLD, 12));
        formPanel.add(lblIsi, gbc);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        txtIsiPengumuman = new JTextArea(3, 30);
        txtIsiPengumuman.setLineWrap(true);
        txtIsiPengumuman.setWrapStyleWord(true);
        txtIsiPengumuman.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtIsiPengumuman.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JScrollPane scrollPengumuman = new JScrollPane(txtIsiPengumuman);
        scrollPengumuman.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        formPanel.add(scrollPengumuman, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;

        // Tanggal Mulai & Selesai dalam 1 row
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.weightx = 0;
        JPanel panelTanggal = new JPanel(new GridLayout(2, 2, 10, 8));
        panelTanggal.setBackground(Color.WHITE);

        JLabel lblTglMulai = new JLabel("Tanggal Mulai");
        lblTglMulai.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelTanggal.add(lblTglMulai);

        JLabel lblTglSelesai = new JLabel("Tanggal Selesai");
        lblTglSelesai.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelTanggal.add(lblTglSelesai);

        tglMulai = new JDateChooser();
        tglMulai.setDateFormatString("yyyy-MM-dd");
        tglMulai.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tglMulai.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        panelTanggal.add(tglMulai);

        tglSelesai = new JDateChooser();
        tglSelesai.setDateFormatString("yyyy-MM-dd");
        tglSelesai.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tglSelesai.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        panelTanggal.add(tglSelesai);

        formPanel.add(panelTanggal, gbc);

        // Status & Tipe dalam 1 row
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.weightx = 0;
        JPanel panelStatus = new JPanel(new GridLayout(2, 2, 10, 8));
        panelStatus.setBackground(Color.WHITE);

        JLabel lblStatus = new JLabel("Status");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelStatus.add(lblStatus);

        JLabel lblTipe = new JLabel("Tipe Pengumuman");
        lblTipe.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panelStatus.add(lblTipe);

        cmbAktif = new JComboBox<>(new String[]{"Ya", "Tidak"});
        cmbAktif.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelStatus.add(cmbAktif);

        cmbTipePengumuman = new JComboBox<>(new String[]{"Public", "Private"});
        cmbTipePengumuman.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cmbTipePengumuman.addActionListener(e -> toggleUserSelection());
        panelStatus.add(cmbTipePengumuman);

        formPanel.add(panelStatus, gbc);

        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.weightx = 1.0;
        gbc.weighty = 0.8;
        gbc.fill = GridBagConstraints.BOTH;

        JPanel panelTargetUser = new JPanel(new BorderLayout(8, 8));
        panelTargetUser.setBackground(Color.WHITE);
        panelTargetUser.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(248, 187, 208), 2),
                "👥 Target User (Private)",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 12),
                new Color(136, 14, 79)
        ));

        // Panel tombol cari pegawai
        JPanel panelCari = new JPanel(new BorderLayout(5, 5));
        panelCari.setBackground(Color.WHITE);

        // Panel kiri untuk tombol cari
        JPanel panelCariLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelCariLeft.setBackground(Color.WHITE);

        JButton btnCariPegawai = new JButton("🔍 Cari Pegawai");
        btnCariPegawai.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnCariPegawai.setBackground(new Color(33, 150, 243));
        btnCariPegawai.setForeground(Color.WHITE);
        btnCariPegawai.setFocusPainted(false);
        btnCariPegawai.setBorderPainted(false);
        btnCariPegawai.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCariPegawai.setPreferredSize(new Dimension(150, 35));
        btnCariPegawai.addActionListener(e -> cariUser());

        JLabel lblInfo = new JLabel("(Tekan SPASI pada tabel untuk memilih pegawai)");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        lblInfo.setForeground(new Color(100, 100, 100));

        panelCariLeft.add(btnCariPegawai);
        panelCariLeft.add(lblInfo);

        // Panel kanan untuk tombol navigasi
        JPanel panelCariRight = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        panelCariRight.setBackground(Color.WHITE);

        JButton btnScrollToTop = new JButton("⬆️ Ke Atas");
        btnScrollToTop.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnScrollToTop.setBackground(new Color(158, 158, 158));
        btnScrollToTop.setForeground(Color.WHITE);
        btnScrollToTop.setFocusPainted(false);
        btnScrollToTop.setBorderPainted(false);
        btnScrollToTop.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnScrollToTop.setPreferredSize(new Dimension(100, 28));
        btnScrollToTop.setToolTipText("Scroll ke bagian atas form");
        btnScrollToTop.addActionListener(e -> {
            // Scroll ke atas
            SwingUtilities.invokeLater(() -> {
                txtJudul.scrollRectToVisible(new Rectangle(0, 0, 1, 1));
            });
        });

        panelCariRight.add(btnScrollToTop);

        panelCari.add(panelCariLeft, BorderLayout.WEST);
        panelCari.add(panelCariRight, BorderLayout.EAST);

        // Label untuk pegawai yang dipilih
        JLabel lblTarget = new JLabel("Pegawai yang Dipilih:");
        lblTarget.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTarget.setForeground(new Color(136, 14, 79));
        lblTarget.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Tabel target user yang dipilih
        String[] kolomTarget = {"NIK", "Nama"};
        DefaultTableModel modelTarget = new DefaultTableModel(kolomTarget, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelTargetUser = new JTable(modelTarget);
        tabelTargetUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tabelTargetUser.setRowHeight(28);
        tabelTargetUser.setSelectionBackground(new Color(252, 228, 236));
        tabelTargetUser.setSelectionForeground(new Color(136, 14, 79));
        tabelTargetUser.setGridColor(new Color(230, 230, 230));
        tabelTargetUser.setShowGrid(true);

        // Set column widths
        tabelTargetUser.getColumnModel().getColumn(0).setPreferredWidth(100);
        tabelTargetUser.getColumnModel().getColumn(1).setPreferredWidth(250);

        // Header styling
        JTableHeader headerTarget = tabelTargetUser.getTableHeader();
        headerTarget.setFont(new Font("Segoe UI", Font.BOLD, 11));
        headerTarget.setBackground(new Color(252, 228, 236));
        headerTarget.setForeground(new Color(136, 14, 79));
        headerTarget.setPreferredSize(new Dimension(headerTarget.getWidth(), 30));

        JScrollPane scrollTarget = new JScrollPane(tabelTargetUser);
        scrollTarget.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        scrollTarget.setPreferredSize(new Dimension(0, 200));
        scrollTarget.setMinimumSize(new Dimension(0, 150));

        // Button hapus user
        JPanel panelBtnUser = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        panelBtnUser.setBackground(Color.WHITE);
        btnHapusUser = createSmallButton("➖ Hapus Pegawai", new Color(244, 67, 54));
        btnHapusUser.addActionListener(e -> hapusUserTarget());
        panelBtnUser.add(btnHapusUser);

        // Panel tengah untuk label dan tabel
        JPanel centerPanel = new JPanel(new BorderLayout(0, 5));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(lblTarget, BorderLayout.NORTH);
        centerPanel.add(scrollTarget, BorderLayout.CENTER);

        // Susun layout
        panelTargetUser.add(panelCari, BorderLayout.NORTH);
        panelTargetUser.add(centerPanel, BorderLayout.CENTER);
        panelTargetUser.add(panelBtnUser, BorderLayout.SOUTH);
        panelTargetUser.setVisible(false); // Hidden by default

        formPanel.add(panelTargetUser, gbc);

        leftPanel.add(formPanel, BorderLayout.CENTER);

        // Panel Button
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 0;

        JPanel panelButton = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelButton.setBackground(Color.WHITE);

        btnSimpan = createModernButton("💾 Simpan", new Color(76, 175, 80), Color.WHITE);
        btnEdit = createModernButton("✏️ Update", new Color(33, 150, 243), Color.WHITE);
        btnHapus = createModernButton("🗑️ Hapus", new Color(244, 67, 54), Color.WHITE);
        btnBatal = createModernButton("↺ Batal", new Color(158, 158, 158), Color.WHITE);

        btnEdit.setEnabled(false);

        btnSimpan.addActionListener(e -> simpanData());
        btnEdit.addActionListener(e -> updateData());
        btnHapus.addActionListener(e -> hapusData());
        btnBatal.addActionListener(e -> bersihkanForm());

        panelButton.add(btnSimpan);
        panelButton.add(btnEdit);
        panelButton.add(btnHapus);
        panelButton.add(btnBatal);

        leftPanel.add(panelButton, BorderLayout.SOUTH);

        // Store reference to panelTargetUser for toggle
        formPanel.putClientProperty("panelTargetUser", panelTargetUser);

        // ===== RIGHT PANEL - TABLE =====
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JLabel lblTableTitle = new JLabel("📋 Daftar Pengumuman");
        lblTableTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTableTitle.setForeground(new Color(136, 14, 79));
        lblTableTitle.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        rightPanel.add(lblTableTitle, BorderLayout.NORTH);

        // Tabel Pengumuman
        String[] kolom = {"ID", "Judul", "Isi", "Mulai", "Selesai", "Status", "Tipe", "Target"};
        DefaultTableModel model = new DefaultTableModel(kolom, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelPengumuman = new JTable(model);
        tabelPengumuman.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tabelPengumuman.setRowHeight(30);
        tabelPengumuman.setSelectionBackground(new Color(252, 228, 236));
        tabelPengumuman.setSelectionForeground(new Color(136, 14, 79));
        tabelPengumuman.setGridColor(new Color(230, 230, 230));

        JTableHeader header = tabelPengumuman.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 11));
        header.setBackground(new Color(252, 228, 236));
        header.setForeground(new Color(136, 14, 79));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tabelPengumuman.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tabelPengumuman.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tabelPengumuman.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tabelPengumuman.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        tabelPengumuman.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);

        tabelPengumuman.getColumnModel().getColumn(0).setPreferredWidth(30);
        tabelPengumuman.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabelPengumuman.getColumnModel().getColumn(2).setPreferredWidth(180);
        tabelPengumuman.getColumnModel().getColumn(3).setPreferredWidth(70);
        tabelPengumuman.getColumnModel().getColumn(4).setPreferredWidth(70);
        tabelPengumuman.getColumnModel().getColumn(5).setPreferredWidth(50);
        tabelPengumuman.getColumnModel().getColumn(6).setPreferredWidth(60);
        tabelPengumuman.getColumnModel().getColumn(7).setPreferredWidth(80);

        JScrollPane scrollTable = new JScrollPane(tabelPengumuman);
        scrollTable.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        rightPanel.add(scrollTable, BorderLayout.CENTER);

        tabelPengumuman.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    pilihData();
                }
            }
        });

        // Wrap leftPanel dengan JScrollPane untuk enable scrolling
        JScrollPane scrollLeftPanel = new JScrollPane(leftPanel);
        scrollLeftPanel.setBorder(null);
        scrollLeftPanel.getVerticalScrollBar().setUnitIncrement(16);
        scrollLeftPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollLeftPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        splitPane.setLeftComponent(scrollLeftPanel);
        splitPane.setRightComponent(rightPanel);

        containerPanel.add(splitPane, BorderLayout.CENTER);
        add(containerPanel);
    }

    private JButton createModernButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(110, 35));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 11));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private JButton createSmallButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(90, 28));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 10));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void toggleUserSelection() {
        JPanel panelTargetUser = (JPanel) ((JPanel) cmbTipePengumuman.getParent().getParent()).getClientProperty("panelTargetUser");
        if (panelTargetUser != null) {
            boolean isPrivate = cmbTipePengumuman.getSelectedItem().equals("Private");
            panelTargetUser.setVisible(isPrivate);
            revalidate();
            repaint();

            // Auto scroll ke panel Target User saat muncul
            if (isPrivate) {
                SwingUtilities.invokeLater(() -> {
                    panelTargetUser.scrollRectToVisible(panelTargetUser.getBounds());
                });
            }
        }
    }

    private void cariUser() {
        // Buka dialog cari pegawai
        DlgCariPegawai dlg = new DlgCariPegawai(null, true);
        dlg.setSize(900, 500);
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);

        // Setelah dialog ditutup dengan Space, ambil data yang dipilih
        if (dlg.getTable().getSelectedRow() != -1) {
            int selectedRow = dlg.getTable().getSelectedRow();
            String nik = dlg.getTable().getValueAt(selectedRow, 0).toString(); // NIP/NIK ada di kolom 0
            String nama = dlg.getTable().getValueAt(selectedRow, 1).toString(); // Nama ada di kolom 1

            // Cek apakah sudah ada di tabel target user
            DefaultTableModel modelTarget = (DefaultTableModel) tabelTargetUser.getModel();
            boolean sudahAda = false;
            for (int i = 0; i < modelTarget.getRowCount(); i++) {
                if (modelTarget.getValueAt(i, 0).equals(nik)) {
                    sudahAda = true;
                    JOptionPane.showMessageDialog(this, "Pegawai sudah ditambahkan!", "Info", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }

            // Tambahkan ke tabel jika belum ada
            if (!sudahAda) {
                modelTarget.addRow(new Object[]{nik, nama});
                selectedUsers.add(nik);
                JOptionPane.showMessageDialog(this, "Pegawai berhasil ditambahkan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void hapusUserTarget() {
        int row = tabelTargetUser.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih user yang akan dihapus!");
            return;
        }

        String nik = tabelTargetUser.getValueAt(row, 0).toString();
        selectedUsers.remove(nik);
        ((DefaultTableModel) tabelTargetUser.getModel()).removeRow(row);
    }

    private void simpanData() {
        if (txtJudul.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Judul harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            txtJudul.requestFocus();
            return;
        }

        if (tglMulai.getDate() == null || tglSelesai.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Tanggal mulai dan selesai harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipePengumuman = cmbTipePengumuman.getSelectedItem().toString();
        if (tipePengumuman.equals("Private") && selectedUsers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih minimal 1 user untuk pengumuman Private!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String targetUsers = tipePengumuman.equals("Private") ? String.join(",", selectedUsers) : null;

            String sql = "INSERT INTO pengumuman_login (judul, isi_pengumuman, tanggal_mulai, tanggal_selesai, aktif, tipe_pengumuman, target_users, created_date) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, txtJudul.getText());
            ps.setString(2, txtIsiPengumuman.getText());
            ps.setDate(3, new java.sql.Date(tglMulai.getDate().getTime()));
            ps.setDate(4, new java.sql.Date(tglSelesai.getDate().getTime()));
            ps.setString(5, cmbAktif.getSelectedItem().toString());
            ps.setString(6, tipePengumuman);
            ps.setString(7, targetUsers);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "✅ Data berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            bersihkanForm();
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void updateData() {
        if (selectedId == 0) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan diupdate!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (txtJudul.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Judul harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            txtJudul.requestFocus();
            return;
        }

        if (tglMulai.getDate() == null || tglSelesai.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Tanggal mulai dan selesai harus diisi!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipePengumuman = cmbTipePengumuman.getSelectedItem().toString();
        if (tipePengumuman.equals("Private") && selectedUsers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih minimal 1 user untuk pengumuman Private!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            String targetUsers = tipePengumuman.equals("Private") ? String.join(",", selectedUsers) : null;

            String sql = "UPDATE pengumuman_login SET judul=?, isi_pengumuman=?, tanggal_mulai=?, tanggal_selesai=?, aktif=?, tipe_pengumuman=?, target_users=? WHERE id=?";
            PreparedStatement ps = koneksi.prepareStatement(sql);
            ps.setString(1, txtJudul.getText());
            ps.setString(2, txtIsiPengumuman.getText());
            ps.setDate(3, new java.sql.Date(tglMulai.getDate().getTime()));
            ps.setDate(4, new java.sql.Date(tglSelesai.getDate().getTime()));
            ps.setString(5, cmbAktif.getSelectedItem().toString());
            ps.setString(6, tipePengumuman);
            ps.setString(7, targetUsers);
            ps.setInt(8, selectedId);

            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "✅ Data berhasil diupdate!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            bersihkanForm();
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void hapusData() {
        int row = tabelPengumuman.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Yakin ingin menghapus data ini?\n\nJudul: " + tabelPengumuman.getValueAt(row, 1),
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                String sql = "DELETE FROM pengumuman_login WHERE id = ?";
                PreparedStatement ps = koneksi.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(tabelPengumuman.getValueAt(row, 0).toString()));
                ps.executeUpdate();

                JOptionPane.showMessageDialog(this, "✅ Data berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                bersihkanForm();
                loadData();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "❌ Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void pilihData() {
        int row = tabelPengumuman.getSelectedRow();
        if (row != -1) {
            selectedId = Integer.parseInt(tabelPengumuman.getValueAt(row, 0).toString());
            txtJudul.setText(tabelPengumuman.getValueAt(row, 1).toString());
            txtIsiPengumuman.setText(tabelPengumuman.getValueAt(row, 2).toString());

            try {
                if (tabelPengumuman.getValueAt(row, 3) != null) {
                    java.sql.Date tglM = java.sql.Date.valueOf(tabelPengumuman.getValueAt(row, 3).toString());
                    tglMulai.setDate(tglM);
                }
                if (tabelPengumuman.getValueAt(row, 4) != null) {
                    java.sql.Date tglS = java.sql.Date.valueOf(tabelPengumuman.getValueAt(row, 4).toString());
                    tglSelesai.setDate(tglS);
                }
                cmbAktif.setSelectedItem(tabelPengumuman.getValueAt(row, 5).toString());
                cmbTipePengumuman.setSelectedItem(tabelPengumuman.getValueAt(row, 6).toString());

                // Load target users jika Private
                selectedUsers.clear();
                DefaultTableModel modelTarget = (DefaultTableModel) tabelTargetUser.getModel();
                modelTarget.setRowCount(0);

                if (tabelPengumuman.getValueAt(row, 6).equals("Private")) {
                    String targetUsersRaw = tabelPengumuman.getValueAt(row, 7).toString();

                    // Parse dari format "X user" untuk mendapatkan NIK sebenarnya dari database
                    String sqlTarget = "SELECT target_users FROM pengumuman_login WHERE id = ?";
                    PreparedStatement psTarget = koneksi.prepareStatement(sqlTarget);
                    psTarget.setInt(1, selectedId);
                    ResultSet rsTarget = psTarget.executeQuery();

                    if (rsTarget.next()) {
                        String targetUsers = rsTarget.getString("target_users");
                        if (targetUsers != null && !targetUsers.isEmpty()) {
                            String[] niks = targetUsers.split(",");
                            for (String nik : niks) {
                                nik = nik.trim();
                                selectedUsers.add(nik);

                                // Ambil nama dari tabel pegawai
                                String sqlNama = "SELECT nama FROM pegawai WHERE nik=?";
                                PreparedStatement psNama = koneksi.prepareStatement(sqlNama);
                                psNama.setString(1, nik);
                                ResultSet rsNama = psNama.executeQuery();

                                if (rsNama.next()) {
                                    modelTarget.addRow(new Object[]{nik, rsNama.getString("nama")});
                                }
                            }
                        }
                    }
                }

                toggleUserSelection();
                btnEdit.setEnabled(true);
                btnSimpan.setEnabled(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void bersihkanForm() {
        selectedId = 0;
        txtJudul.setText("");
        txtIsiPengumuman.setText("");
        tglMulai.setDate(null);
        tglSelesai.setDate(null);
        cmbAktif.setSelectedIndex(0);
        cmbTipePengumuman.setSelectedIndex(0);
        selectedUsers.clear();
        ((DefaultTableModel) tabelTargetUser.getModel()).setRowCount(0);
        tabelPengumuman.clearSelection();
        toggleUserSelection();
        btnEdit.setEnabled(false);
        btnSimpan.setEnabled(true);
        txtJudul.requestFocus();
    }

    private void loadData() {
        try {
            // Query yang lebih sederhana dan aman
            String sql = "SELECT id, judul, isi_pengumuman, tanggal_mulai, tanggal_selesai, aktif, "
                    + "COALESCE(tipe_pengumuman, 'Public') as tipe_pengumuman, "
                    + "COALESCE(target_users, '') as target_users "
                    + "FROM pengumuman_login ORDER BY created_date DESC";

            Statement st = koneksi.createStatement();
            ResultSet rs = st.executeQuery(sql);

            DefaultTableModel model = (DefaultTableModel) tabelPengumuman.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                String targetUsers = rs.getString("target_users");
                String tipePengumuman = rs.getString("tipe_pengumuman");
                String targetInfo = "Semua";

                // Hitung jumlah user untuk Private
                if ("Private".equals(tipePengumuman) && targetUsers != null && !targetUsers.isEmpty()) {
                    int jumlahUser = targetUsers.split(",").length;
                    targetInfo = jumlahUser + " user";
                }

                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("judul"),
                    rs.getString("isi_pengumuman"),
                    rs.getDate("tanggal_mulai"),
                    rs.getDate("tanggal_selesai"),
                    rs.getString("aktif"),
                    tipePengumuman,
                    targetInfo // Tampilkan info jumlah user, bukan raw data
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error load data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
