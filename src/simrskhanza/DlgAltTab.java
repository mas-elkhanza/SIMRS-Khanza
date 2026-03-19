package simrskhanza;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

/**
 * Window Switcher untuk SIMRS Khanza - Mirip ALT+TAB Windows
 * Fungsi: Tekan Ctrl+Tab untuk switch antar dialog yang terbuka
 * 
 * @author SIMRS Khanza
 */
public class DlgAltTab {
    
    private static DlgAltTab instance;
    private JDialog switcherDialog;
    private Map<String, Window> registeredWindows;
    private List<String> windowOrder;
    private int selectedIndex = 0;
    private boolean isShowing = false;
    
    private JList<String> windowList;
    private DefaultListModel<String> listModel;
    private Timer autoRefreshTimer;
    
    // Untuk drag dialog
    private Point mouseDownCompCoords;
    
    /**
     * Singleton pattern
     */
    public static DlgAltTab getInstance() {
        if (instance == null) {
            instance = new DlgAltTab();
        }
        return instance;
    }
    
    private DlgAltTab() {
        registeredWindows = new LinkedHashMap<>();
        windowOrder = new ArrayList<>();
        listModel = new DefaultListModel<>();
        
        initSwitcherDialog();
        setupGlobalKeyListener();
        setupWindowTracker();
        setupAutoRefresh();
    }
    
    /**
     * Setup global key listener untuk Ctrl+Tab
     */
    private void setupGlobalKeyListener() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(new KeyEventDispatcher() {
                private boolean ctrlPressed = false;
                
                @Override
                public boolean dispatchKeyEvent(KeyEvent e) {
                    int id = e.getID();
                    int keyCode = e.getKeyCode();
                    
                    if (keyCode == KeyEvent.VK_CONTROL) {
                        if (id == KeyEvent.KEY_PRESSED) {
                            ctrlPressed = true;
                        } else if (id == KeyEvent.KEY_RELEASED) {
                            ctrlPressed = false;
                            if (isShowing) {
                                activateSelectedWindow();
                                hideSwitcher();
                            }
                        }
                    }
                    
                    if (ctrlPressed && keyCode == KeyEvent.VK_TAB && id == KeyEvent.KEY_PRESSED) {
                        if (!isShowing) {
                            showSwitcher();
                        } else {
                            selectNext();
                        }
                        return true;
                    }
                    
                    if (ctrlPressed && e.isShiftDown() && keyCode == KeyEvent.VK_TAB && id == KeyEvent.KEY_PRESSED) {
                        if (!isShowing) {
                            showSwitcher();
                        } else {
                            selectPrevious();
                        }
                        return true;
                    }
                    
                    if (isShowing && keyCode == KeyEvent.VK_ESCAPE && id == KeyEvent.KEY_PRESSED) {
                        hideSwitcher();
                        return true;
                    }
                    
                    if (isShowing && id == KeyEvent.KEY_PRESSED) {
                        if (keyCode == KeyEvent.VK_DOWN) {
                            selectNext();
                            return true;
                        } else if (keyCode == KeyEvent.VK_UP) {
                            selectPrevious();
                            return true;
                        } else if (keyCode == KeyEvent.VK_ENTER) {
                            activateSelectedWindow();
                            hideSwitcher();
                            return true;
                        }
                    }
                    
                    return false;
                }
            });
    }
    
    /**
     * Setup auto refresh untuk update list saat popup terbuka
     */
    private void setupAutoRefresh() {
        autoRefreshTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isShowing) {
                    // Simpan selected index
                    int currentSelected = selectedIndex;
                    
                    // Force scan untuk deteksi window baru
                    forceScanAndRegister();
                    
                    // Rebuild list
                    rebuildWindowList();
                    
                    // Restore selection jika masih valid
                    if (currentSelected >= 0 && currentSelected < listModel.getSize()) {
                        selectedIndex = currentSelected;
                        windowList.setSelectedIndex(selectedIndex);
                    } else if (listModel.getSize() > 0) {
                        selectedIndex = 0;
                        windowList.setSelectedIndex(0);
                    }
                }
            }
        });
    }
    
    /**
     * Setup window tracker untuk auto-register dialog
     */
    private void setupWindowTracker() {
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (event instanceof WindowEvent) {
                    WindowEvent we = (WindowEvent) event;
                    Window window = we.getWindow();
                    
                    if (we.getID() == WindowEvent.WINDOW_OPENED) {
                        if (window instanceof JDialog) {
                            JDialog dialog = (JDialog) window;
                            String className = dialog.getClass().getName();
                            
                            if (className.contains(".Dlg")) {
                                SwingUtilities.invokeLater(() -> {
                                    try {
                                        Thread.sleep(200);
                                        String title = dialog.getTitle();
                                        if (title == null || title.trim().isEmpty()) {
                                            title = extractSimpleName(className);
                                        }
                                        if (title != null && !title.trim().isEmpty()) {
                                            registerWindow(window, title);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                });
                            }
                        }
                    } else if (we.getID() == WindowEvent.WINDOW_CLOSED || 
                               we.getID() == WindowEvent.WINDOW_CLOSING) {
                        unregisterWindow(window);
                    }
                }
            }
        }, AWTEvent.WINDOW_EVENT_MASK);
    }
    
    /**
     * Extract simple name dari class name
     */
    private String extractSimpleName(String className) {
        if (className.contains(".")) {
            className = className.substring(className.lastIndexOf('.') + 1);
        }
        if (className.startsWith("Dlg")) {
            className = className.substring(3);
        }
        return className;
    }
    
    /**
     * Ambil title dari dialog dengan berbagai metode
     */
    private String extractDialogTitle(JDialog dialog) {
        String title = null;
        
        title = dialog.getTitle();
        if (title != null && !title.trim().isEmpty()) {
            return title;
        }
        
        title = findTitledBorder(dialog.getContentPane());
        if (title != null && !title.trim().isEmpty()) {
            return title;
        }
        
        title = findTitleLabel(dialog.getContentPane());
        if (title != null && !title.trim().isEmpty()) {
            return title;
        }
        
        return extractSimpleName(dialog.getClass().getName());
    }
    
    /**
     * Cari TitledBorder secara recursive
     */
    private String findTitledBorder(Container container) {
        if (container == null) return null;
        
        if (container instanceof javax.swing.JComponent) {
            javax.swing.JComponent jcomp = (javax.swing.JComponent) container;
            javax.swing.border.Border border = jcomp.getBorder();
            
            if (border instanceof javax.swing.border.TitledBorder) {
                javax.swing.border.TitledBorder titledBorder = (javax.swing.border.TitledBorder) border;
                String title = titledBorder.getTitle();
                if (title != null && !title.trim().isEmpty()) {
                    return title;
                }
            }
        }
        
        Component[] children = container.getComponents();
        for (Component child : children) {
            if (child instanceof Container) {
                String found = findTitledBorder((Container) child);
                if (found != null) {
                    return found;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Cari JLabel yang kemungkinan adalah title
     */
    private String findTitleLabel(Container container) {
        if (container == null) return null;
        
        List<JLabel> labels = new ArrayList<>();
        findAllLabels(container, labels, 0);
        
        for (JLabel label : labels) {
            String text = label.getText();
            if (text != null && !text.trim().isEmpty()) {
                text = text.replaceAll("<[^>]*>", "").trim();
                
                Font font = label.getFont();
                Point location = label.getLocation();
                
                if (font != null && font.getSize() >= 14 && 
                    location.y < 100 && 
                    text.length() < 50 &&
                    !text.toLowerCase().contains("copyright") &&
                    !text.toLowerCase().contains("version")) {
                    return text;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Cari semua JLabel secara recursive
     */
    private void findAllLabels(Container container, List<JLabel> labels, int depth) {
        if (container == null || depth > 5) return;
        
        Component[] children = container.getComponents();
        for (Component child : children) {
            if (child instanceof JLabel) {
                labels.add((JLabel) child);
            }
            if (child instanceof Container) {
                findAllLabels((Container) child, labels, depth + 1);
            }
        }
    }
    
    /**
     * Inisialisasi dialog switcher dengan tampilan list
     */
    private void initSwitcherDialog() {
        switcherDialog = new JDialog();
        switcherDialog.setUndecorated(true);
        switcherDialog.setAlwaysOnTop(true);
        switcherDialog.setFocusableWindowState(false);
        
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(250, 250, 250));
        mainPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(250, 250, 250));
        headerPanel.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        
        // Mouse listener untuk drag dialog
        headerPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
        });
        
        headerPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                switcherDialog.setLocation(
                    currCoords.x - mouseDownCompCoords.x, 
                    currCoords.y - mouseDownCompCoords.y
                );
            }
        });
        
        JLabel titleLabel = new JLabel("Window Switcher");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        titleLabel.setForeground(new Color(60, 60, 60));
        
        JButton closeButton = new JButton("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 20));
        closeButton.setForeground(new Color(100, 100, 100));
        closeButton.setBackground(new Color(250, 250, 250));
        closeButton.setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> hideSwitcher());
        
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setForeground(Color.RED);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setForeground(new Color(100, 100, 100));
            }
        });
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(closeButton, BorderLayout.EAST);
        
        // List
        windowList = new JList<>(listModel);
        windowList.setFont(new Font("Tahoma", Font.PLAIN, 12));
        windowList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        windowList.setVisibleRowCount(10);
        windowList.setFixedCellHeight(30);
        windowList.setBackground(Color.WHITE);
        windowList.setSelectionBackground(new Color(0, 120, 215));
        windowList.setSelectionForeground(Color.WHITE);
        
        // Custom cell renderer untuk tampilan lebih baik
        windowList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                label.setText("  📄  " + value);
                return label;
            }
        });
        
        // Mouse listener untuk single click
        windowList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = windowList.locationToIndex(e.getPoint());
                if (index >= 0) {
                    selectedIndex = index;
                    windowList.setSelectedIndex(selectedIndex);
                    activateSelectedWindow();
                    // Dialog tetap terbuka, user bisa klik window lain
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(windowList);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        // Footer hint
        JLabel hintLabel = new JLabel("Click window untuk switch atau gunakan keyboard (Enter/Arrow)");
        hintLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
        hintLabel.setForeground(new Color(120, 120, 120));
        hintLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Layout
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(hintLabel, BorderLayout.SOUTH);
        
        switcherDialog.add(mainPanel);
        switcherDialog.pack();
    }
    
    /**
     * Register window manual
     */
    public void registerWindow(Window window, String title) {
        if (window == null || title == null || title.trim().isEmpty()) {
            return;
        }
        
        if (registeredWindows.containsKey(title)) {
            return;
        }
        
        registeredWindows.put(title, window);
        windowOrder.add(title);
    }
    
    /**
     * Unregister window
     */
    public void unregisterWindow(Window window) {
        if (window == null) return;
        
        String toRemove = null;
        for (Map.Entry<String, Window> entry : registeredWindows.entrySet()) {
            if (entry.getValue() == window) {
                toRemove = entry.getKey();
                break;
            }
        }
        
        if (toRemove != null) {
            registeredWindows.remove(toRemove);
            windowOrder.remove(toRemove);
        }
    }
    
    /**
     * Show window switcher
     */
    private void showSwitcher() {
        cleanupClosedWindows();
        
        if (registeredWindows.isEmpty()) {
            return;
        }
        
        selectedIndex = 0;
        rebuildWindowList();
        
        switcherDialog.pack();
        centerDialog();
        
        isShowing = true;
        switcherDialog.setVisible(true);
        
        // Start auto refresh timer
        autoRefreshTimer.start();
    }
    
    /**
     * Hide window switcher
     */
    private void hideSwitcher() {
        isShowing = false;
        switcherDialog.setVisible(false);
        
        // Stop auto refresh timer
        autoRefreshTimer.stop();
    }
    
    /**
     * Rebuild window list
     */
    private void rebuildWindowList() {
        listModel.clear();
        
        for (String title : windowOrder) {
            Window window = registeredWindows.get(title);
            if (window != null && window.isDisplayable()) {
                listModel.addElement(title);
            }
        }
        
        if (!listModel.isEmpty()) {
            windowList.setSelectedIndex(selectedIndex);
        }
    }
    
    /**
     * Select next window
     */
    private void selectNext() {
        if (listModel.isEmpty()) return;
        
        selectedIndex = (selectedIndex + 1) % listModel.getSize();
        windowList.setSelectedIndex(selectedIndex);
        windowList.ensureIndexIsVisible(selectedIndex);
    }
    
    /**
     * Select previous window
     */
    private void selectPrevious() {
        if (listModel.isEmpty()) return;
        
        selectedIndex = (selectedIndex - 1 + listModel.getSize()) % listModel.getSize();
        windowList.setSelectedIndex(selectedIndex);
        windowList.ensureIndexIsVisible(selectedIndex);
    }
    
    /**
     * Activate selected window
     */
    private void activateSelectedWindow() {
        if (selectedIndex < 0 || selectedIndex >= windowOrder.size()) {
            return;
        }
        
        String selectedTitle = windowOrder.get(selectedIndex);
        Window window = registeredWindows.get(selectedTitle);
        
        if (window != null && window.isDisplayable()) {
            try {
                window.setVisible(true);
                window.toFront();
                window.requestFocus();
                
                if (window instanceof Frame) {
                    Frame frame = (Frame) window;
                    if (frame.getState() == Frame.ICONIFIED) {
                        frame.setState(Frame.NORMAL);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Center dialog on screen
     */
    private void centerDialog() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - switcherDialog.getWidth()) / 2;
        int y = (screen.height - switcherDialog.getHeight()) / 2;
        switcherDialog.setLocation(x, y);
    }
    
    /**
     * Cleanup windows yang sudah ditutup
     */
    private void cleanupClosedWindows() {
        List<String> toRemove = new ArrayList<>();
        
        for (Map.Entry<String, Window> entry : registeredWindows.entrySet()) {
            Window window = entry.getValue();
            if (!window.isDisplayable() || !window.isVisible()) {
                toRemove.add(entry.getKey());
            }
        }
        
        for (String title : toRemove) {
            registeredWindows.remove(title);
            windowOrder.remove(title);
        }
    }
    
    /**
     * Get jumlah window yang terdaftar
     */
    public int getWindowCount() {
        cleanupClosedWindows();
        return registeredWindows.size();
    }
    
    /**
     * Show window list manual
     */
    public void showWindowListManual() {
        showSwitcher();
    }
    
    /**
     * Force scan semua JDialog yang sedang terbuka dan register
     */
    public void forceScanAndRegister() {
        // Simpan window yang sudah ada untuk menghindari duplikasi
        Set<Window> existingWindows = new HashSet<>(registeredWindows.values());
        
        Window[] windows = Window.getWindows();
        
        for (Window window : windows) {
            if (window instanceof JDialog) {
                JDialog dialog = (JDialog) window;
                String className = dialog.getClass().getName();
                
                if (dialog.isShowing()) {
                    boolean isSystemDialog = className.equals("javax.swing.JDialog") ||
                                           dialog.getWidth() < 300 || 
                                           dialog.getHeight() < 300;
                    
                    if (isSystemDialog) {
                        continue;
                    }
                    
                    boolean isUtilityDialog = className.contains("Cari") && dialog.getWidth() < 700;
                    
                    if (!isUtilityDialog && !existingWindows.contains(window)) {
                        String title = extractDialogTitle(dialog);
                        
                        if (title != null && !title.trim().isEmpty()) {
                            registerWindow(window, title);
                        }
                    }
                }
            }
        }
        
        cleanupClosedWindows();
    }
}