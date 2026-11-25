package widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Kotak cari untuk menyaring tombol/checkbox pada panel menu yang panjang.
 * Item yang tersembunyi karena hak akses tetap tersembunyi.
 * Kosongkan teks (setText("")) sebelum mengubah visibilitas item dari luar.
 */
public class FilterMenu extends TextBox {
    private static final long serialVersionUID = 1L;
    private final JComponent menu;
    private final String petunjuk;
    private final Map<Component,Boolean> tampilAwal=new LinkedHashMap<>();
    private Dimension ukuranAwal;
    private boolean menyaring=false,pin=false,antri=false;
    private AbstractButton semua;

    public FilterMenu(JComponent menu){
        super();
        this.menu=menu;
        this.petunjuk="Cari menu...";
        setPreferredSize(new Dimension(100,24));
        setToolTipText("Ketik nama menu, tekan Esc untuk mengosongkan");
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {saring(true);}
            @Override
            public void removeUpdate(DocumentEvent e) {saring(true);}
            @Override
            public void changedUpdate(DocumentEvent e) {saring(true);}
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ESCAPE&&!getText().isEmpty()){
                    setText("");
                    e.consume();
                }
            }
        });
    }

    /**
     * Saat menyaring, item yang tercentang selalu tampil dan di-pin di atas.
     * Tidak berlaku selama checkbox semua tercentang (semua item ikut tercentang).
     */
    public void pinTercentang(AbstractButton semua){
        this.semua=semua;
        pin=true;
        for(Component item:menu.getComponents()){
            if((item instanceof AbstractButton)&&(item!=semua)){
                ((AbstractButton)item).addItemListener(e -> {
                    if(menyaring&&!antri){
                        antri=true;
                        SwingUtilities.invokeLater(() -> {
                            antri=false;
                            if(menyaring){
                                saring(false);
                            }
                        });
                    }
                });
            }
        }
    }

    private void saring(boolean keAtas){
        String cari=getText().trim().toLowerCase();
        if(cari.isEmpty()){
            if(menyaring){
                int i=0;
                for(Map.Entry<Component,Boolean> item:tampilAwal.entrySet()){
                    item.getKey().setVisible(item.getValue());
                    if(pin){
                        menu.setComponentZOrder(item.getKey(),i++);
                    }
                }
                menu.setPreferredSize(ukuranAwal);
                tampilAwal.clear();
                menyaring=false;
            }
        }else{
            if(!menyaring){
                ukuranAwal=menu.isPreferredSizeSet()?menu.getPreferredSize():null;
                for(Component item:menu.getComponents()){
                    tampilAwal.put(item,item.isVisible());
                }
                menyaring=true;
            }
            String[] kata=cari.split("\\s+");
            boolean adapin=pin&&(semua==null||!semua.isSelected());
            List<Component> atas=new ArrayList<>(),bawah=new ArrayList<>();
            int jarak=(menu.getLayout() instanceof FlowLayout)?((FlowLayout)menu.getLayout()).getVgap():0;
            int tinggi=jarak;
            for(Map.Entry<Component,Boolean> item:tampilAwal.entrySet()){
                Component c=item.getKey();
                boolean tercentang=adapin&&(c!=semua)&&(c instanceof AbstractButton)&&((AbstractButton)c).isSelected();
                boolean tampil=item.getValue()&&(tercentang||cocok(c,kata));
                c.setVisible(tampil);
                if(tampil){
                    tinggi=tinggi+c.getPreferredSize().height+jarak;
                }
                if(tampil&&tercentang){
                    atas.add(c);
                }else{
                    bawah.add(c);
                }
            }
            if(pin){
                atas.addAll(bawah);
                for(int i=0;i<atas.size();i++){
                    if(menu.getComponent(i)!=atas.get(i)){
                        menu.setComponentZOrder(atas.get(i),i);
                    }
                }
            }
            menu.setPreferredSize(new Dimension(menu.getPreferredSize().width,tinggi+10));
        }
        menu.revalidate();
        menu.repaint();
        if(keAtas){
            menu.scrollRectToVisible(new Rectangle(0,0,1,1));
        }
    }

    private boolean cocok(Component item,String[] kata){
        if(!(item instanceof AbstractButton)){
            return true;
        }
        String teks=((AbstractButton)item).getText();
        if(teks==null){
            return false;
        }
        teks=teks.toLowerCase();
        for(String k:kata){
            if(!teks.contains(k)){
                return false;
            }
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(getText().isEmpty()){
            Graphics2D g2=(Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setFont(getFont());
            g2.setColor(new Color(160,160,160));
            FontMetrics fm=g2.getFontMetrics();
            g2.drawString(petunjuk,getInsets().left+2,(getHeight()+fm.getAscent()-fm.getDescent())/2);
            g2.dispose();
        }
    }
}
