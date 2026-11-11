package uz.ncipro.calendar;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

/**
 * Pengganti uz.ncipro.calendar.JDateTimePicker (NCI Projects) yang tidak lagi memakai class internal JDK
 * (com.sun.java.swing.plaf.windows/motif), sehingga jalan di Java 17 ke atas dengan look and feel apa pun.
 * API publik sama dengan versi lama: getDate, setDate, getDisplayFormat, setDisplayFormat, setLocale.
 */
@SuppressWarnings({"rawtypes", "unchecked", "deprecation"})
public class JDateTimePicker extends JComboBox {
    private static final long serialVersionUID = 1L;
    private static final Color AKSEN  = new Color(0x16A05D);
    private static final Color BORDER = new Color(0x96BEA5);
    private static final Color TINT   = new Color(0x16, 0xA0, 0x5D, 30);
    private static final Color TEKS   = new Color(0x1C2520);

    private String displayFormat;
    private SimpleDateFormat sdf;
    private Kalender popupEditor;

    public JDateTimePicker() {
        sdf = new SimpleDateFormat();
        setEditable(true);
        setDisplayFormat(sdf.toPattern());
    }

    @Override
    public void updateUI() {
        Date lama = (popupEditor == null) ? null : popupEditor.getDate();
        setUI(new PickerUI());
        popupEditor = ((PickerUI) getUI()).kalender;
        popupEditor.pendengar = () -> setValue(sdf.format(popupEditor.getDate()));
        if (lama != null) {
            popupEditor.lihat(lama);
        }
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

    private static final class PickerUI extends BasicComboBoxUI {
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
            ((JDateTimePicker) comboBox).sinkronkan();
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
