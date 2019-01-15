/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package widget;

import java.awt.Color;
import javax.swing.JEditorPane;
import javax.swing.border.LineBorder;

/**
 *
 * @author khanzasoft
 */
public class editorpane extends JEditorPane{
    public editorpane() {
        super();
        setFont(new java.awt.Font("Tahoma", 0, 11));        
        setSelectionColor(new Color(50,51,0));
        setSelectedTextColor(new Color(255,255,0));
        setForeground(new Color(70,70,70));
        setBorder(new LineBorder(new Color(239,244,234)));
        setBackground(new Color(255,255,255));
    }
}
