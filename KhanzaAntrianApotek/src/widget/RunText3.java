package widget;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;

public class RunText3 {

    private JWindow window = new JWindow();
    private JLabel label = new JLabel("Slide Text Swing, Slide Text Swing, ..........");
    private JPanel windowContents = new JPanel();

    public RunText3() {
        windowContents.add(label);
        window.add(windowContents);
        window.pack();
        window.setLocationRelativeTo(null);
        final int desiredWidth = window.getWidth();
        window.getContentPane().setLayout(null);
        window.setSize(0, window.getHeight());
        window.setVisible(true);
        Timer timer = new Timer(20, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int newWidth = Math.min(window.getWidth() + 1, desiredWidth);
                window.setSize(newWidth, window.getHeight());
                windowContents.setLocation(newWidth - desiredWidth, 0);
                if (newWidth >= desiredWidth) {
                    ((Timer) e.getSource()).stop();
                    label.setForeground(Color.red);
                    mainKill();
                }
            }
        });
        timer.start();
    }

    public void mainKill() {
        Timer timer = new Timer(6000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        timer.start();
    }

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                RunText3 windowTest = new RunText3();
            }
        });
    }
}