/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package widget;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author dosen3
 */
public class PanelWT extends JPanel{
    private static final long serialVersionUID = -1;
    private BufferedImage gradientImage;
    private Color warnaAtas = new Color(255,255,255);
    private Color warnaBawah = new Color(0,0,0);

    public PanelWT(){
        super();
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(3,3,3,3));
    }

    public PanelWT(LayoutManager layout) {
        super(layout);
        addComponentListener(new GradientCacheManager());
    }

    public Color getWarnaAtas() {
        return warnaAtas;
    }

    public void setWarnaAtas(Color warnaAtas) {
        this.warnaAtas = warnaAtas;
    }

    public Color getWarnaBawah() {
        return warnaBawah;
    }

    public void setWarnaBawah(Color warnaBawah) {
        this.warnaBawah = warnaBawah;
    }

    @Override
    protected void paintComponent(Graphics g) {
        createImageCache();
        if (gradientImage != null) {
            g.drawImage(gradientImage, 0, 0, getWidth(), getHeight(), null);
        }
    }

    protected void createImageCache() {
        int width = 2;
        int height = getHeight();

        if (width == 0 || height == 0) {
            return;
        }

        if (gradientImage == null ||width != gradientImage.getWidth() ||height != gradientImage.getHeight()) {
            gradientImage = new BufferedImage(width, height,
            BufferedImage.TYPE_INT_RGB);

            Graphics2D g2 = gradientImage.createGraphics();
            GradientPaint painter = new GradientPaint(0, 0, warnaBawah,0, height / 2, warnaAtas);
            g2.setPaint(painter);

            Rectangle2D rect = new Rectangle2D.Double(0, 0, width, height / 2.0);
            g2.fill(rect);

            painter = new GradientPaint(0, height / 2, warnaAtas,
            0, height, warnaBawah);
            g2.setPaint(painter);

            rect = new Rectangle2D.Double(0, (height / 2.0) - 1.0, width, height);
            g2.fill(rect);

            g2.dispose();
        }
    }

    private void disposeImageCache() {
        synchronized (gradientImage) {
            gradientImage.flush();
            gradientImage = null;
        }
    }

    private class GradientCacheManager implements ComponentListener {
        @Override
        public void componentResized(ComponentEvent e) {
    }

        @Override
    public void componentMoved(ComponentEvent e) {
    }

        @Override
    public void componentShown(ComponentEvent e) {
    }

        @Override
    public void componentHidden(ComponentEvent e) {
        disposeImageCache();
    }
    }

}


