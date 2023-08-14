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
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author khanzasoft
 */
public class panelisi extends JPanel{
    private static final long serialVersionUID = -1;
    private BufferedImage gradientImage;
    //private Color warnaAtas = new Color(245,200,245) ;
    //private Color warnaBawah = new Color(245,150,245) ;
    private Color warnaAtas = new Color(255,255,255) ;
    private Color warnaBawah = new Color(255,255,255) ;

    //private Color warnaAtas = new Color(255,194,255);
    //private Color warnaBawah = new Color(255,194,255);

    public panelisi(){
        super();
        this.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(239,244,234)));
    }

    public panelisi(LayoutManager layout) {
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
         super.paintComponent(g);
        if (isOpaque()) {

            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            setUpGradient();
            g2.drawImage(gradientImage, 0, 0, getWidth(), getHeight(), null);

            int width = getWidth();
            int height = getHeight() * 1 / 200;

            Color light = new Color(1F, 1F, 1F, 0.5F);
            Color dark = new Color(1F, 1F, 1F, 0.0F);

            createImageCache();

            GradientPaint paint = new GradientPaint(0, 0, light, 0, height, dark);
            GeneralPath path = new GeneralPath();
            path.moveTo(0, 0);
            path.lineTo(0, height);
            path.curveTo(0, height, width / 2, height / 2, width, height);
            path.lineTo(width, 0);
            path.closePath();

            g2.setPaint(paint);
            g2.fill(path);

            paint = new GradientPaint(0, getHeight(), light, 0, getHeight() - height, dark);
            path = new GeneralPath();
            path.moveTo(0, getHeight());
            path.lineTo(0, getHeight() - height);
            path.curveTo(0, getHeight() - height, width / 2, getHeight() - height / 2, width, getHeight() - height);
            path.lineTo(width, getHeight());
            path.closePath();

            g2.setPaint(paint);
            g2.fill(path);
            g2.dispose();

            /*if (gradientImage != null) {
                g.drawImage(gradientImage, 0, 0, getWidth(), getHeight(), null);
            }*/
        }

    }

    protected void createImageCache() {
        int width = 2;
        int height = getHeight();

        if (width == 0 || height == 0) {
            return;
        }

        if (gradientImage == null ||width != gradientImage.getWidth() ||height != gradientImage.getHeight()) {

            gradientImage = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);

            Graphics2D g2 = gradientImage.createGraphics();
            GradientPaint painter = new GradientPaint(0, 0, warnaBawah,0, height / 2, warnaAtas);
            g2.setPaint(painter);

            Rectangle2D rect = new Rectangle2D.Double(0, 0, width, height / 2.0);
            g2.fill(rect);

            painter = new GradientPaint(0, height / 2, warnaAtas,0, height, warnaBawah);
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

    private void setUpGradient() {
        gradientImage = new BufferedImage(1, getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = (Graphics2D) gradientImage.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint paint = new GradientPaint(0, 0, warnaAtas, 0, getHeight(), warnaBawah);

        g2.setPaint(paint);
        g2.fillRect(0, 0, 1, getHeight());
        g2.dispose();
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
