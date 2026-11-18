package rekammedis;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public final class PanelOdontogram extends javax.swing.JPanel {
    private final String[][] gigi={
        {"18","17","16","15","14","13","12","11","21","22","23","24","25","26","27","28"},
        {"55","54","53","52","51","61","62","63","64","65"},
        {"85","84","83","82","81","71","72","73","74","75"},
        {"48","47","46","45","44","43","42","41","31","32","33","34","35","36","37","38"}
    };
    private final int[] garis={16,60,94,140};
    private TableModel model;
    private final TableModelListener listener=e -> repaint();

    public PanelOdontogram() {
        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(733,188));
    }

    public void setModel(TableModel model) {
        if(this.model!=null){
            this.model.removeTableModelListener(listener);
        }
        this.model=model;
        if(model!=null){
            model.addTableModelListener(listener);
        }
        repaint();
    }

    @Override
    protected void paintComponent(java.awt.Graphics g) {
        super.paintComponent(g);
        int awal,x,y;
        String kode;
        g.setFont(new java.awt.Font("Tahoma",java.awt.Font.PLAIN,11));
        for(int b=0;b<gigi.length;b++){
            awal=(getWidth()-gigi[b].length*44-8)/2;
            y=garis[b];
            for(int k=0;k<gigi[b].length;k++){
                x=awal+k*44+(k>=gigi[b].length/2?8:0);
                kode=null;
                if(model!=null){
                    for(int r=0;r<model.getRowCount();r++){
                        if(model.getValueAt(r,0).toString().equals(gigi[b][k])){
                            kode=model.getValueAt(r,1).toString().substring(0,3);
                        }
                    }
                }
                if(kode!=null){
                    g.setColor(new java.awt.Color(255,244,190));
                    g.fillRect(x,y,40,26);
                }
                g.setColor(java.awt.Color.DARK_GRAY);
                g.drawRect(x,y,40,26);
                if(kode!=null){
                    g.setColor(new java.awt.Color(150,0,0));
                    g.drawString(kode,x+20-g.getFontMetrics().stringWidth(kode)/2,y+17);
                }else if(gigi[b][k].charAt(1)>'3'){
                    g.drawRect(x+12,y+8,16,10);
                    g.drawLine(x,y,x+12,y+8);
                    g.drawLine(x+40,y,x+28,y+8);
                    g.drawLine(x,y+26,x+12,y+18);
                    g.drawLine(x+40,y+26,x+28,y+18);
                }else{
                    g.drawLine(x,y,x+12,y+13);
                    g.drawLine(x,y+26,x+12,y+13);
                    g.drawLine(x+40,y,x+28,y+13);
                    g.drawLine(x+40,y+26,x+28,y+13);
                    g.drawLine(x+12,y+13,x+28,y+13);
                }
                g.setColor(java.awt.Color.BLACK);
                g.drawString(gigi[b][k],x+20-g.getFontMetrics().stringWidth(gigi[b][k])/2,b<2?y-4:y+39);
            }
        }
    }
}
