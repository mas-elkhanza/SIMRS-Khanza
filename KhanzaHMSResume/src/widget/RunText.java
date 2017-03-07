/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author igos
 */
public class RunText extends Canvas implements Runnable {

    String text = "aku adalah anak gembala selalu riang serta gembira, karena aku rajin bekerja tak kenal lelah ataupun lengah.";
    int textWidth = 600;
    int x;
    int y;
    final int MARGIN_X = 20;
    int direction;
    final int DIR_LEFT = 0;
    final int DIR_RIGHT = 1;
    boolean isTextMoving;
    int fpsLimiter = 16;
    int posIncrement = 5;

    public RunText() {
        x = MARGIN_X;
        y = getHeight() + 50;
        direction = DIR_LEFT;
        isTextMoving = false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        g.drawString(text, x, y);
    }

    private void updateX() {
        if (direction == DIR_LEFT) {
            if (x < MARGIN_X - textWidth) {
                //x = MARGIN_X;
                direction = DIR_RIGHT;
            } else {
                x -= posIncrement;
            }
        } else { // direction == DIR_RIGHT
            if (x > MARGIN_X + textWidth) {
                //x = MARGIN_X;
                direction = DIR_LEFT;
            } else {
                x += posIncrement;
            }
        }
    }

    public void run() {
        isTextMoving = true;
        long time_1 = 0;
        long time_2 = 0;
        boolean drawNextFrame = true;
        while (isTextMoving) {
            if(drawNextFrame) {
                time_1 = System.currentTimeMillis();
                updateX();
                time_2 = System.currentTimeMillis();
                if((time_2 - time_1) > 1000/fpsLimiter) {
                    //System.out.println(time_2 - time_1);
                    paint(getGraphics());
                } else {
                    drawNextFrame = false;
                }
            }

            if(!drawNextFrame) {
                time_2 = System.currentTimeMillis();
                if((time_2 - time_1) > 1000/fpsLimiter) {
                    //System.out.println(time_2 - time_1);
                    paint(getGraphics());
                    drawNextFrame = true;
                }
            }
        }
    }
}
