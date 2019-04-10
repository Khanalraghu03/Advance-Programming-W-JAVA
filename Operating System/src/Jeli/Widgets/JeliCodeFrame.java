package Jeli.Widgets;

import java.awt.Color;
import java.awt.Font;

public class JeliCodeFrame extends JeliFrame implements TextDisplayCallBack
{
  private JeliTextDisplay jtd;
  Font font;
  
  public JeliCodeFrame(String name, int size, int codewidth, int codeheight)
  {
    super(name);
    font = new Font("Monospaced", 0, size);
    jtd = new JeliTextDisplay(0, codewidth, codeheight, "Hide", font, Color.white, Color.black, Jeli.Utility.hm, this, false, true, 0);
    
    jtd.labelButton.setPositionCenter();
    setLayout(new java.awt.GridLayout(1, 1));
    add(jtd);
    
    pack();
  }
  
  public void append(String s) {
    jtd.append(s);
  }
  
  public void set(String s) {
    jtd.set(s);
  }
  
  public void top() {
    jtd.top();
  }
  
  public void textDisplayLabelCallBack(int id) {
    setVisible(false);
  }
  
  public void textDisplayLogCallBack(int id, String str) {}
}
