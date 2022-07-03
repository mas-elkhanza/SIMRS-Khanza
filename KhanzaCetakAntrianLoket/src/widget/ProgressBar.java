
package widget;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.geom.Rectangle2D;
import javax.swing.JProgressBar;
import javax.swing.Timer;

/**
 *
 * @author usu
 */
public class ProgressBar extends JProgressBar {

    private static final long serialVersionUID = 1L;
    private Timer timer;
    private int percent;
    private final Color light = new Color(1F, 1F, 1F, 0.4F);
    private final Color dark = new Color(1F, 1F, 1F, 0.4F);
    private final Color black = Color.darkGray;
    private final Color warna = Color.magenta;

    /**
     * 
     * @return
     */
    private Timer getTimer() {
        if (timer == null) {
            timer = new Timer(10, (ActionEvent e) -> {
                if (getPercent() >= 100) {
                    setPercent(0);
                }
                setValue(0);
                setPercent(getPercent() + 1);
            });
        }
        return timer;
    }

    /**
     * 
     * @return
     */
    private int getPercent() {
        return percent;
    }

    /**
     * 
     * @param percent
     */
    private void setPercent(int percent) {
        this.percent = percent;
        repaint();
    }

    /**
     * 
     */
    public ProgressBar() {
        super();
        setPercent(0);
        setOpaque(false);
        setBorderPainted(false);
        super.setIndeterminate(false);
    }

    @Override
    public void setIndeterminate(boolean newValue) {
        if (newValue) {
            setPercent(0);
            getTimer().start();
        } else {
            if (getTimer().isRunning()) {
                timer.stop();
            }
            setPercent(0);
            setValue(0);
            super.setIndeterminate(newValue);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint paint = new GradientPaint(0, 0, black, 0, getHeight(), warna);
        g2.setPaint(paint);
        g2.fillRect(0, 0, getWidth(), getHeight());

        if (getPercent() > 0) {
            paint = new GradientPaint(0, 0, warna, 0, getHeight(), black);
            g2.setPaint(paint);

            int space = getPercent() * getWidth() / 100;
            int width = getWidth() * 10 / 100;
            Shape s = new Rectangle2D.Double(space, 0, width, getHeight());
            g2.fill(s);
        }

        if (getValue() > 0 && getValue() < getMaximum()) {

            int total = getMaximum() - getMinimum();
            double rate = (getWidth() * 1.0) / (total * 1.0);
            int now = getValue() - getMinimum();

            paint = new GradientPaint(0, 0, warna, 0, getHeight(), black);
            g2.setPaint(paint);

            Shape s = new Rectangle2D.Double(0, 0, now * rate, getHeight());
            g2.fill(s);
        }


        paint = new GradientPaint(0, 0, light, 0, getHeight() * 3 / 4, dark);
        g2.setPaint(paint);
        g2.fillRect(3, 3, getWidth(), getHeight() * 3 / 4);

        g2.setColor(black);
        g2.drawRect(3, 3, getWidth(), getHeight());

        g2.dispose();

    }
}
