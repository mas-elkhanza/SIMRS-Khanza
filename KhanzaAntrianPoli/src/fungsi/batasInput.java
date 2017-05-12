/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fungsi;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Owner
 */
public class batasInput {
    private final int length;
    private PlainDocument filter;

    public batasInput(int length){this.length=length;}

    public PlainDocument getFilter(final JTextField inputan){
        filter=new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException{
                StringBuilder buf=new StringBuilder();
                int c=0;
                char[] upp=str.toCharArray();
                for(int i=0;i<upp.length;i++){
                    upp[i]=Character.toUpperCase(upp[i]);
                        boolean isOnlyAngka=Character.isDigit(upp[i]);
                        boolean isOnlyLetter=Character.isLetter(upp[i]);
                        boolean isOnlySpasi=Character.isSpaceChar(upp[i]);                        
                        if(isOnlyLetter==true){
                            upp[c]=upp[i];
                            c++;
                        }else if(isOnlyAngka==true){
                            upp[c]=upp[i];
                            c++;
                        }else if(isOnlySpasi==true){
                            upp[c]=upp[i];
                            c++;
                        }else if(isOnlyLetter==false){
                            upp[c]=upp[i];
                            c++;
                        }
                }
                buf.append(upp,0,c);
                int x=inputan.getText().length();
                if(x<length){
                   super.insertString(offs,new String(buf).replaceAll("'","").replaceAll("\\\\", ""), a);
                }
            }
        };return filter;
    }

    public PlainDocument getFilter(final JTextArea inputan){
        filter=new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException{
                StringBuilder buf=new StringBuilder();
                int c=0;
                char[] upp=str.toCharArray();
                for(int i=0;i<upp.length;i++){
                    upp[i]=Character.toUpperCase(upp[i]);
                        boolean isOnlyAngka=Character.isDigit(upp[i]);
                        boolean isOnlyLetter=Character.isLetter(upp[i]);
                        boolean isOnlySpasi=Character.isSpaceChar(upp[i]);
                        if(isOnlyLetter==true){
                            upp[c]=upp[i];
                            c++;
                        }else if(isOnlyAngka==true){
                            upp[c]=upp[i];
                            c++;
                        }else if(isOnlySpasi==true){
                            upp[c]=upp[i];
                            c++;
                        }else if(isOnlyLetter==false){
                            upp[c]=upp[i];
                            c++;
                        }
                }
                buf.append(upp,0,c);
                int x=inputan.getText().length();
                if(x<length){
                   super.insertString(offs,new String(buf).replaceAll("'","").replaceAll("\\\\", ""), a);
                }
            }
        };return filter;
    }
  
    public PlainDocument getOnlyAngka(final JTextField inputan) {
        filter=new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException{
                StringBuffer buf=new StringBuffer();
                int c=0;
                char[] upp=str.toCharArray();
                for(int i=0;i<upp.length;i++){
                    upp[i]=Character.toUpperCase(upp[i]);
                        boolean isOnlyAngka=Character.isDigit(upp[i]);
                        if(isOnlyAngka==true){
                            upp[c]=upp[i];
                            c++;
                        }
                }
                buf.append(upp,0,c);
                int x=inputan.getText().length();
                if(x<length){
                    super.insertString(offs,new String(buf), a);
                }
            }
        };return filter;
    }

    public PlainDocument getKata(final JTextField inputan){
        filter=new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException{
                StringBuilder buf=new StringBuilder();
                int c=0;
                char[] upp=str.toCharArray();
                for(int i=0;i<upp.length;i++){
                        boolean isOnlyAngka=Character.isDigit(upp[i]);
                        boolean isOnlyLetter=Character.isLetter(upp[i]);
                        boolean isOnlySpasi=Character.isSpaceChar(upp[i]);
                        
                        if(isOnlyLetter==true){
                            upp[c]=upp[i];
                            c++;
                        }else if(isOnlyAngka==true){
                            upp[c]=upp[i];
                            c++;
                        }else if(isOnlySpasi==true){
                            upp[c]=upp[i];
                            c++;
                        }else if(isOnlyLetter==false){
                            upp[c]=upp[i];
                            c++;
                        }
                }
                buf.append(upp,0,c);
                int x=inputan.getText().length();
                if(x<length){
                   super.insertString(offs,new String(buf).replaceAll("'","").replaceAll("\\\\", ""), a);
                }
            }
        };return filter;
    }
    
    public PlainDocument getKata(final JTextArea inputan){
        filter=new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a)throws BadLocationException{
                StringBuilder buf=new StringBuilder();
                int c=0;
                char[] upp=str.toCharArray();
                for(int i=0;i<upp.length;i++){
                        boolean isOnlyAngka=Character.isDigit(upp[i]);
                        boolean isOnlyLetter=Character.isLetter(upp[i]);
                        boolean isOnlySpasi=Character.isSpaceChar(upp[i]);
                        
                        if(isOnlyLetter==true){
                            upp[c]=upp[i];
                            c++;
                        }else if(isOnlyAngka==true){
                            upp[c]=upp[i];
                            c++;
                        }else if(isOnlySpasi==true){
                            upp[c]=upp[i];
                            c++;
                        }else if(isOnlyLetter==false){
                            upp[c]=upp[i];
                            c++;
                        }
                }
                buf.append(upp,0,c);
                int x=inputan.getText().length();
                if(x<length){
                   super.insertString(offs,new String(buf).replaceAll("'","").replaceAll("\\\\", ""), a);
                }
            }
        };return filter;
    }
}
