package widget;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.GeneralPath;
import java.util.Random;
import java.awt.Font;
import javax.swing.*;
 
public class RunText2 extends JPanel {
    String s="Which Way? ";
    Timer t;
    private final int DELAY=200;
    
    public void init() {
        setBackground(Color.BLACK);
        t=new Timer(DELAY,new BlinkText());
        t.start();
    }

    //TM Sets X,Y Grid as Integer
    public void drawShapes(int w, int h, Graphics2D z) {
        GeneralPath draw = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
        //TM Sets Random generator
        Random rx = new Random();

        //Starts drawing an Arrow
        draw.moveTo(w * .5f, h * .25f);
        draw.lineTo(w * .4f, h * .50f);
        draw.lineTo(w * .45f, h * .50f);
        draw.lineTo(w * .45f, h * .75f);

        draw.lineTo(w * .55f, h * .75f);
        draw.lineTo(w * .55f, h * .50f);
        draw.lineTo(w * .6f, h * .50f);
        draw.lineTo(w * .5f, h * .25f);
        //TM Ends drawing an Arrow
        //TM Fills interior of arrow with color
        z.fill(draw);
        //TM Color Chosen by random generator
        z.setColor(new Color(rx.nextInt(256),
        rx.nextInt(256), rx.nextInt(256)));
        //TM Loop for chosing XY location, font and color for text
        int y = 1;
        while (y < 11) {
            //TM Sets Fonts
            String fonts[] = {"Serif", "Monospaced", "Serif", "Bold", "Serif", "Italic","SansSerif", "Dialog", "DialogInput"};
            //TM Colors
            z.setColor(new Color(rx.nextInt(256),
            rx.nextInt(256), rx.nextInt(256)));
            //TM Font chosen + size
            z.setFont(new Font(fonts[rx.nextInt(5)], rx.nextInt(10), 26));
            //TM Outputs msg within specified range
            z.drawString(s, rx.nextInt(400), rx.nextInt(500));
            y++;
        }
    }

    public void paint(Graphics g) {
        Graphics2D z = (Graphics2D) g;
        Dimension d = getSize();
        z.setBackground(getBackground());
        z.clearRect(0, 0, d.width, d.height);
        z.setRenderingHint(RenderingHints.KEY_ANTIALIASING ,
        RenderingHints.VALUE_ANTIALIAS_ON);
        drawShapes(d.width, d.height, z);
    }
    
    private class BlinkText implements ActionListener{
        int x=0;
        int y=1;
        public void actionPerformed(ActionEvent e){
            x+=y;
            if(x==10000){
                s="";
                y=-1;
            }
            if(x==0){
                y=1;
                s="Which Way? ";
            }
            repaint();
        }
    }
    
    public static void main(String arg[]) {
        final RunText2 x = new RunText2();
        x.init();
        JFrame f = new JFrame("Tom's 2d Shape Assignment"); //TM Adds title to JPanel
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        //TM Set Size of JPanel & Centers
        f.getContentPane().add("Center", x);
        f.pack();
        f.setSize(new Dimension(600, 600));
        f.show();
    }
}