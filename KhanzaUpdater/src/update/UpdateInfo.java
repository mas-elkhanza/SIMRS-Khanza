/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package update;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Thomas Otero H3R3T1C
 */
public class UpdateInfo extends JFrame{

    private JEditorPane infoPane;
    private JScrollPane scp;
    private JButton ok;
    private JButton cancel;
    private JPanel pan1;
    private JPanel pan2;
   
    public UpdateInfo(String info) {
       
        initComponents();
         Dimension screen=Toolkit.getDefaultToolkit().getScreenSize();
        Dimension screensize=getSize();
        setLocation((screen.width-screensize.width)/2,
        (screen.height-screensize.height)/2)  ;
      //  setLocationRelativeTo(this);
        infoPane.setText(info);
    }

    private void initComponents() {

        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.setTitle("Terdapat Update Aplikasi terbaru");
        pan1 = new JPanel();
        pan1.setLayout(new BorderLayout());

        pan2 = new JPanel();
        pan2.setLayout(new FlowLayout());

        infoPane = new JEditorPane();
        infoPane.setContentType("text/html");

        scp = new JScrollPane();
        scp.setViewportView(infoPane);

        ok = new JButton("Update");
        ok.addActionListener( new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                update();
            }
        });

        cancel = new JButton("Exit");
        cancel.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                UpdateInfo.this.dispose();
            }
        });
        pan2.add(ok);
        pan2.add(cancel);
        pan1.add(pan2, BorderLayout.SOUTH);
        pan1.add(scp, BorderLayout.CENTER);
        this.add(pan1);
        pack();
        show();
        this.setSize(300, 200);
    }
    private void update()
    {
//        String[] run = {"java","-jar","updater/updateNew.jar"};
//        try {
//            Runtime.getRuntime().exec(run);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
          Main_Gui utama=new Main_Gui();
                            utama.setVisible(true);
         dispose();
      //  System.exit(0);
    }

}
