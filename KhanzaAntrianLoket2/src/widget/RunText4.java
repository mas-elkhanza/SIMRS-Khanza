package widget;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
 
public class RunText4 extends JFrame implements ActionListener{
  JLabel label;  
 
  public RunText4()  {
    label = new JLabel( "Hello World,Where there is will there is& nbsp;a way");
    getContentPane().add(label, BorderLayout.NORTH);
     javax.swing.Timer timer = new javax.swing.Timer(100, this);
    timer.start();
  }
   public void actionPerformed(ActionEvent e)  {
    String oldText = label.getText();
    String newText = oldText.substring(1) + oldText.substring(0, 1);
     label.setText( newText );
  }
   public static void main(String[] args)  {
    RunText4 frame = new RunText4();
    frame.setDefaultCloseOperation( EXIT_ON_CLOSE );
    frame.setSize(300,100);
    frame.setVisible(true);
  }
}