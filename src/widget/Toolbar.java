
package widget;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JToolBar;

/**
 *
 * @author usu
 */
public class Toolbar extends JToolBar {

    private static final long serialVersionUID = 1L;
    private BufferedImage gradientImage;
    private BufferedImage ligthImage;
    private final Color light = new Color(1F, 1F, 1F, 0.5F);
    private final Color dark = new Color(1F, 1F, 1F, 0.0F);
    private final Color black = Color.BLACK;
    private final Color warna = Color.MAGENTA;

    /**
     * 
     */
    public Toolbar() {
        super();
        setFloatable(false);
        setBorder(BorderFactory.createEmptyBorder(3, 3, 4, 3));
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

    /**
     * membuat gambar gradient background
     */
    private void setUpGradientImage() {
        gradientImage = new BufferedImage(1, getHeight(), BufferedImage.TYPE_INT_ARGB);

        GradientPaint paint = new GradientPaint(0, 0, warna, 0, getHeight(), black);

        Graphics2D g = (Graphics2D) gradientImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(paint);
        g.fillRect(0, 0, 1, getHeight());
        g.dispose();
    }

    /**
     * membuat gambar glass
     */
    private void setUpLigthImage() {
        ligthImage = new BufferedImage(1, getHeight() / 2, BufferedImage.TYPE_INT_ARGB);

        GradientPaint paint = new GradientPaint(0, 0, light, 0, getHeight(), dark);

        Graphics2D g = (Graphics2D) ligthImage.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setPaint(paint);
        g.fillRect(2, 2, 5, getHeight() / 2);
        g.dispose();
    }
}
