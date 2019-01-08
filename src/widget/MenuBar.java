/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package widget;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JMenuBar;

/**
 *
 * @author dosen
 */
public class MenuBar extends JMenuBar {
    private static final long serialVersionUID = 1L;
    private BufferedImage gradientImage;
    private BufferedImage ligthImage;
    private final Color light = new Color(1F, 1F, 1F, 0.35F);
    private final Color dark = new Color(1F, 1F, 1F, 0.35F);
    //private Color black = Color.black;
    //private Color warna = Color.magenta.darker().darker().darker().darker();
    private final Color black = new Color(90,130,80);
    private final Color warna = new Color(90,130,80);

    public MenuBar() {
        super();
        setBorder(BorderFactory.createEmptyBorder(5, 6,6, 6));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setUpGradientImage();
        setUpLigthImage();
        if (isOpaque()) {
            g.drawImage(gradientImage, 0, 0, getWidth(), getHeight(), null);
            g.drawImage(ligthImage, 0, 0, getWidth(), getHeight() / 2, null);
        }
    }

    private void setUpGradientImage() {
        gradientImage = new BufferedImage(1, getHeight(), BufferedImage.TYPE_INT_ARGB);

        GradientPaint paint = new GradientPaint(0, 0, warna, 0, getHeight(), black);

        Graphics2D g = (Graphics2D) gradientImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(paint);
        g.fillRect(0, 0, 1, getHeight());
        g.dispose();
    }

    private void setUpLigthImage() {
        ligthImage = new BufferedImage(1, getHeight() / 2, BufferedImage.TYPE_INT_ARGB);

        GradientPaint paint = new GradientPaint(0, 0, light, 0, getHeight(), dark);

        Graphics2D g = (Graphics2D) ligthImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(paint);
        g.fillRect(0, 0, 1, getHeight() / 2);
        g.dispose();
    }

}
