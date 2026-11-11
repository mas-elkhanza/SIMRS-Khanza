package widget;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.text.JTextComponent;

@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
public final class Tanggal extends JComboBox {
    private static final long serialVersionUID = 2L;
    private static final Color AKSEN     = new Color(0x16A05D);
    private static final Color BORDER    = new Color(0x96BEA5);   
    private static final Color NONAKTIF  = new Color(0xFAFCFB);
    private static final Color PEMISAH   = new Color(0xD3DDD7);
    private static final Color TINT      = new Color(0x16, 0xA0, 0x5D, 30);   
    private static final Color BENING    = new Color(0, 0, 0, 0);            
    private static final Color LATAR_PANAH = Color.WHITE;
    private static final Color TEKS      = new Color(0x1C2520);
    private static final int   RADIUS    = 6;
    private static final String KUNCI    = "widget.Tanggal.digayakan";

    private boolean hover;
    private boolean hoverPanah;
    private String displayFormat;
    private SimpleDateFormat sdf;
    private Kalender popupEditor;

    public Tanggal() {
        super();
        sdf = new SimpleDateFormat();
        setEditable(true);
        setDisplayFormat(sdf.toPattern());
        setForeground(TEKS);
        setBackground(BENING);   
        setFont(new Font("Tahoma", Font.PLAIN, 11));
        gayakan();

        addContainerListener(new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent e) {
                gayakanAnak(e.getChild());
            }
        });
    }

    @Override
    public void updateUI() {
        Date lama = (popupEditor == null) ? null : popupEditor.getDate();
        setUI(new TanggalUI());
        popupEditor = ((TanggalUI) getUI()).kalender;
        popupEditor.pendengar = () -> setValue(sdf.format(popupEditor.getDate()));
        if (lama != null) {
            popupEditor.lihat(lama);
        }
        gayakan();
    }

    private void setValue(String value) {
        removeAllItems();
        addItem(value);
    }

    @Override
    public void setLocale(Locale l) {
        super.setLocale(l);
        if (popupEditor != null) {
            popupEditor.setLocale(l);
        }
    }

    public Date getDate() {
        return popupEditor.getDate();
    }

    public void setDate(Date date) {
        popupEditor.setDate(date);
    }

    public String getDisplayFormat() {
        return displayFormat;
    }

    public void setDisplayFormat(String displayFormat) {
        String lama = this.displayFormat;
        this.displayFormat = displayFormat;
        sdf.applyPattern(displayFormat);
        setValue(sdf.format(popupEditor.getDate()));
        firePropertyChange("displayFormat", lama, displayFormat);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        super.actionPerformed(e);
        try {
            popupEditor.setDate(sdf.parse((String) getSelectedItem()));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void sinkronkan() {
        if (!sdf.format(popupEditor.getDate()).equals(getSelectedItem())) {
            actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ""));
        }
    }

    private void gayakan() {
        setOpaque(false);
        setBorder(new BorderModern());
        for (Component c : getComponents()) {
            gayakanAnak(c);
        }
        repaint();
    }

    private void gayakanAnak(Component c) {
        if (!(c instanceof JComponent)) {
            return;
        }
        JComponent jc = (JComponent) c;
        boolean sudah = Boolean.TRUE.equals(jc.getClientProperty(KUNCI));

        if (c instanceof AbstractButton) {               
            AbstractButton b = (AbstractButton) c;
            b.setBackground(BENING);
            b.setOpaque(false);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.setBorder(BorderFactory.createEmptyBorder());
            b.setFocusable(false);
            b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            if (!sudah) {
                b.addMouseListener(hoverTombol(b));
                b.addMouseListener(hoverKomponen());
            }
        } else if (c instanceof JTextComponent) {        
            JTextComponent t = (JTextComponent) c;
            t.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
            t.setBackground(BENING);
            t.setOpaque(false);
            t.setForeground(TEKS);
            t.setSelectionColor(new Color(0xCDEBDA));
            t.setSelectedTextColor(new Color(0x0E3B24));
            t.setCaretColor(AKSEN);
            if (!sudah) {
                t.addFocusListener(fokusRepaint());
                t.addMouseListener(hoverKomponen());
            }
        }
        jc.putClientProperty(KUNCI, Boolean.TRUE);
    }

    private MouseListener hoverTombol(final AbstractButton b) {
        return new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hoverPanah = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hoverPanah = false;
                repaint();
            }
        };
    }

    private transient FocusListener fokusRepaintCache;
    private transient MouseListener hoverKomponenCache;

    private FocusListener fokusRepaint() {
        if (fokusRepaintCache == null) {
            fokusRepaintCache = new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) { repaint(); }

                @Override
                public void focusLost(FocusEvent e) { repaint(); }
            };
        }
        return fokusRepaintCache;
    }

    private MouseListener hoverKomponen() {
        if (hoverKomponenCache == null) {
            hoverKomponenCache = new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { hover = true; repaint(); }

                @Override
                public void mouseExited(MouseEvent e) { hover = false; repaint(); }
            };
        }
        return hoverKomponenCache;
    }

    @Override
    public void setEnabled(boolean b) {
        super.setEnabled(b);
        for (Component c : getComponents()) {
            if (c instanceof AbstractButton) {
                c.setBackground(BENING);
            } else if (c instanceof JTextComponent) {
                c.setBackground(BENING);
            }
        }
        repaint();
    }

    private boolean fokus() {
        if (!isEnabled()) {
            return false;
        }
        if (isFocusOwner()) {
            return true;
        }
        for (Component c : getComponents()) {
            if (c.isFocusOwner()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Rectangle r = areaPanah();
        if (r == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.clip(new RoundRectangle2D.Float(2, 2, getWidth() - 4, getHeight() - 4,
                    RADIUS * 2f - 2, RADIUS * 2f - 2));
            g2.clipRect(r.x, r.y, r.width, r.height);

            g2.setColor(isEnabled() ? LATAR_PANAH : NONAKTIF);
            g2.fillRect(r.x, r.y, r.width, r.height);
            if (hoverPanah && isEnabled()) {
                g2.setColor(TINT);
                g2.fillRect(r.x, r.y, r.width, r.height);
            }

            g2.setColor(PEMISAH);
            g2.drawLine(r.x, r.y + 3, r.x, r.y + r.height - 4);

            float cx = r.x + r.width / 2f + 0.5f;
            float cy = r.y + r.height / 2f + 0.5f;
            Path2D chevron = new Path2D.Float();
            chevron.moveTo(cx - 4, cy - 2);
            chevron.lineTo(cx, cy + 2);
            chevron.lineTo(cx + 4, cy - 2);
            g2.setColor(isEnabled() ? new Color(0x3E4B44) : new Color(0xB3BCB7));
            g2.setStroke(new BasicStroke(1.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.draw(chevron);
        } finally {
            g2.dispose();
        }
    }

    private Rectangle areaPanah() {
        Insets in = getInsets();
        int lebar = getHeight() - in.top - in.bottom;     
        if (lebar <= 0) {
            return null;
        }
        AbstractButton b = tombolPanah();
        if (b != null && b.isVisible() && b.getWidth() > 0) {
            lebar = Math.max(lebar, getWidth() - in.right - b.getX());
        }
        lebar = Math.min(lebar, getWidth() / 2);
        return new Rectangle(getWidth() - in.right - lebar, in.top, lebar, getHeight() - in.top - in.bottom);
    }

    @Override
    public void doLayout() {
        AbstractButton b = tombolPanah();
        if (b != null) {
            Insets in = getInsets();
            int s = getHeight() - in.top - in.bottom;
            if (s > 0) {
                Dimension d = b.isPreferredSizeSet() ? b.getPreferredSize() : null;
                if (d == null || d.width != s || d.height != s) {
                    b.setPreferredSize(new Dimension(s, s));
                }
            }
        }
        super.doLayout();
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }

    @Override
    protected boolean isPaintingOrigin() {
        return true;
    }

    private AbstractButton tombolPanah() {
        for (Component c : getComponents()) {
            if (c instanceof AbstractButton) {
                return (AbstractButton) c;
            }
        }
        return null;
    }

    private class BorderModern extends AbstractBorder {

        private static final long serialVersionUID = 1L;

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                float arc = RADIUS * 2f;
                boolean f = fokus();

                if (f) {
                    g2.setColor(new Color(AKSEN.getRed(), AKSEN.getGreen(), AKSEN.getBlue(), 60));
                    g2.setStroke(new BasicStroke(2f));
                    g2.draw(new RoundRectangle2D.Float(x + 1, y + 1, w - 2, h - 2, arc + 1, arc + 1));
                }
                Color garis = !isEnabled() ? new Color(BORDER.getRed(), BORDER.getGreen(), BORDER.getBlue(), 130)
                        : f ? AKSEN
                        : hover ? new Color(0x5A9170)
                        : BORDER;
                g2.setColor(garis);
                g2.setStroke(new BasicStroke(1f));
                g2.draw(new RoundRectangle2D.Float(x + 1.5f, y + 1.5f, w - 3, h - 3, arc, arc));
            } finally {
                g2.dispose();
            }
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(2, 6, 2, 3);   
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(2, 6, 2, 3);
            return insets;
        }
    }

    private static final class TanggalUI extends BasicComboBoxUI {
        private Kalender kalender;

        @Override
        protected ComboPopup createPopup() {
            kalender = new Kalender();
            PopupTanggal popup = new PopupTanggal(comboBox, kalender);
            popup.getAccessibleContext().setAccessibleParent(comboBox);
            return popup;
        }
    }

    private static final class PopupTanggal extends BasicComboPopup {
        private static final long serialVersionUID = 1L;
        private final Kalender kalender;

        PopupTanggal(JComboBox combo, Kalender kalender) {
            super(combo);
            this.kalender = kalender;
            this.kalender.tutup = () -> hide();
            add(kalender);
        }

        @Override
        protected void configurePopup() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorderPainted(true);
            setBorder(BorderFactory.createLineBorder(BORDER));
            setOpaque(true);
            setBackground(Color.WHITE);
            setDoubleBuffered(true);
            setRequestFocusEnabled(false);
        }

        @Override
        public void show() {
            ((Tanggal) comboBox).sinkronkan();
            Dimension ukuran = kalender.getPreferredSize();
            Rectangle batas = computePopupBounds(0, comboBox.getBounds().height, ukuran.width, ukuran.height);
            setLightWeightPopupEnabled(comboBox.isLightWeightPopupEnabled());
            show(comboBox, batas.x, batas.y);
        }
    }

    private static final class Kalender extends JPanel {
        private static final long serialVersionUID = 1L;
        transient Runnable pendengar;
        transient Runnable tutup;
        private Locale locale = Locale.getDefault();
        private Calendar pilih = Calendar.getInstance(locale);
        private Calendar tampil = (Calendar) pilih.clone();
        private final JLabel judul = new JLabel("", SwingConstants.CENTER);
        private final JLabel[] kepala = new JLabel[7];
        private final Sel[] hari = new Sel[42];

        Kalender() {
            super(new BorderLayout(0, 6));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

            judul.setFont(new Font("Tahoma", Font.BOLD, 12));
            judul.setForeground(TEKS);
            JPanel kiri = new JPanel(new GridLayout(1, 2, 2, 0));
            JPanel kanan = new JPanel(new GridLayout(1, 2, 2, 0));
            kiri.setOpaque(false);
            kanan.setOpaque(false);
            kiri.add(navigasi("\u00AB", Calendar.YEAR, -1));
            kiri.add(navigasi("\u2039", Calendar.MONTH, -1));
            kanan.add(navigasi("\u203A", Calendar.MONTH, 1));
            kanan.add(navigasi("\u00BB", Calendar.YEAR, 1));
            JPanel atas = new JPanel(new BorderLayout());
            atas.setOpaque(false);
            atas.add(kiri, BorderLayout.WEST);
            atas.add(judul, BorderLayout.CENTER);
            atas.add(kanan, BorderLayout.EAST);
            add(atas, BorderLayout.NORTH);

            JPanel grid = new JPanel(new GridLayout(7, 7, 2, 2));
            grid.setOpaque(false);
            for (int i = 0; i < 7; i++) {
                kepala[i] = new JLabel("", SwingConstants.CENTER);
                kepala[i].setFont(new Font("Tahoma", Font.BOLD, 10));
                kepala[i].setForeground(new Color(0x6B7A71));
                grid.add(kepala[i]);
            }
            for (int i = 0; i < 42; i++) {
                final Sel b = new Sel("");
                hari[i] = b;
                b.addActionListener(e -> {
                    Calendar c = (Calendar) pilih.clone();
                    c.set(b.tahun, b.bulan, b.tgl);
                    setDate(c.getTime());
                    if (tutup != null) {
                        tutup.run();
                    }
                });
                grid.add(b);
            }
            add(grid, BorderLayout.CENTER);

            Sel hariIni = new Sel("Hari ini");
            hariIni.setFont(new Font("Tahoma", Font.BOLD, 11));
            hariIni.setForeground(AKSEN);
            hariIni.nav = true;
            hariIni.setPreferredSize(new Dimension(90, 24));
            hariIni.addActionListener(e -> {
                setDate(new Date());
                if (tutup != null) {
                    tutup.run();
                }
            });
            JPanel bawah = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            bawah.setOpaque(false);
            bawah.add(hariIni);
            add(bawah, BorderLayout.SOUTH);

            setPreferredSize(new Dimension(230, 236));
            lihat(pilih.getTime());
        }

        private Sel navigasi(String teks, final int satuan, final int jumlah) {
            Sel b = new Sel(teks);
            b.nav = true;
            b.setFont(new Font("Dialog", Font.BOLD, 14));
            b.setPreferredSize(new Dimension(26, 24));
            b.addActionListener(e -> {
                Calendar c = (Calendar) pilih.clone();
                c.add(satuan, jumlah);
                setDate(c.getTime());
            });
            return b;
        }

        Date getDate() {
            return pilih.getTime();
        }

        void setDate(Date d) {
            boolean beda = !pilih.getTime().equals(d);
            lihat(d);
            if (beda && pendengar != null) {
                pendengar.run();
            }
        }

        void lihat(Date d) {
            pilih.setTime(d);
            tampil.setTime(d);
            tampil.set(Calendar.DAY_OF_MONTH, 1);
            render();
        }

        @Override
        public void setLocale(Locale l) {
            super.setLocale(l);
            locale = l;
            Date d = pilih.getTime();
            pilih = Calendar.getInstance(l);
            tampil = Calendar.getInstance(l);
            lihat(d);
        }

        private void render() {
            DateFormatSymbols dfs = new DateFormatSymbols(locale);
            String[] namaBulan = dfs.getMonths();
            String[] namaHari = dfs.getShortWeekdays();
            judul.setText(namaBulan[tampil.get(Calendar.MONTH)] + " " + tampil.get(Calendar.YEAR));
            int awal = tampil.getFirstDayOfWeek();
            for (int i = 0; i < 7; i++) {
                kepala[i].setText(namaHari[(awal - 1 + i) % 7 + 1]);
            }
            Calendar c = (Calendar) tampil.clone();
            int geser = c.get(Calendar.DAY_OF_WEEK) - awal;
            if (geser < 0) {
                geser += 7;
            }
            c.add(Calendar.DAY_OF_MONTH, -geser);
            Calendar sekarang = Calendar.getInstance(locale);
            for (int i = 0; i < 42; i++) {
                Sel b = hari[i];
                b.tahun = c.get(Calendar.YEAR);
                b.bulan = c.get(Calendar.MONTH);
                b.tgl = c.get(Calendar.DAY_OF_MONTH);
                b.setText(Integer.toString(b.tgl));
                b.bulanLain = b.bulan != tampil.get(Calendar.MONTH);
                b.terpilih = b.tahun == pilih.get(Calendar.YEAR) && c.get(Calendar.DAY_OF_YEAR) == pilih.get(Calendar.DAY_OF_YEAR);
                b.hariIni = b.tahun == sekarang.get(Calendar.YEAR) && c.get(Calendar.DAY_OF_YEAR) == sekarang.get(Calendar.DAY_OF_YEAR);
                c.add(Calendar.DAY_OF_MONTH, 1);
            }
            repaint();
        }
    }

    private static final class Sel extends JButton {
        private static final long serialVersionUID = 1L;
        boolean terpilih;
        boolean hariIni;
        boolean bulanLain;
        boolean nav;
        boolean hoverSel;
        int tahun;
        int bulan;
        int tgl;

        Sel(String teks) {
            super(teks);
            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setFocusable(false);
            setRequestFocusEnabled(false);
            setMargin(new Insets(0, 0, 0, 0));
            setFont(new Font("Tahoma", Font.PLAIN, 11));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) { hoverSel = true; repaint(); }

                @Override
                public void mouseExited(MouseEvent e) { hoverSel = false; repaint(); }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                RoundRectangle2D.Float kotak = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 8, 8);
                if (terpilih) {
                    g2.setColor(AKSEN);
                    g2.fill(kotak);
                } else if (hoverSel) {
                    g2.setColor(TINT);
                    g2.fill(kotak);
                }
                if (hariIni && !terpilih) {
                    g2.setColor(AKSEN);
                    g2.draw(kotak);
                }
                g2.setFont(getFont());
                g2.setColor(terpilih ? Color.WHITE : bulanLain ? new Color(0xA7B0AB) : nav ? getForeground() : TEKS);
                FontMetrics fm = g2.getFontMetrics();
                String teks = getText();
                g2.drawString(teks, (getWidth() - fm.stringWidth(teks)) / 2, (getHeight() - fm.getHeight()) / 2 + fm.getAscent());
            } finally {
                g2.dispose();
            }
        }
    }
}
