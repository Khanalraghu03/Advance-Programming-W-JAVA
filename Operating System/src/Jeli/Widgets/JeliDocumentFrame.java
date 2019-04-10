package Jeli.Widgets;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.util.Vector;

public class JeliDocumentFrame extends JeliFrame implements TextDisplayCallBack, VoidCallBack
{
  private Document doc = null;
  private Font font;
  private JeliTextDisplay jtd;
  private int pindent = 0;
  private boolean parskip = true;
  
  public JeliDocumentFrame(int id, int rows, int columns, int fontSize, String label)
  {
    super(label);
    font = new Font("Serif", 0, fontSize);
    jtd = new JeliTextDisplay(0, rows, columns, "Hide", font, Color.white, Color.black, Jeli.Utility.hm, this, false, true, 0);
    
    jtd.labelButton.setPositionCenter();
    setLayout(new java.awt.GridLayout(1, 1));
    add(jtd);
    jtd.setVoidCallBack(this);
    pack();
  }
  



  public void addParagraph(String s)
  {
    Paragraph para = new Paragraph(null);
    para.setFromString(s);
    if (doc == null)
      doc = new Document(null);
    doc.setPindent(pindent);
    doc.setParskip(parskip);
    doc.addParagraph(para);
    FontMetrics fm = jtd.getFontMetric();
    para.setLengths(fm);
    String lines = doc.format(jtd.getWidth(), fm.stringWidth(" "));
    jtd.set(lines);
  }
  
  public int getPindent() {
    return pindent;
  }
  
  public void setPindent(int n) {
    if (doc != null) doc.setPindent(n);
    pindent = n;
  }
  
  public boolean getParskip() {
    return parskip;
  }
  
  public void setParskip(boolean f) {
    if (doc != null) doc.setParskip(f);
    parskip = f;
  }
  
  public void setParagraph(String s) {
    if (doc == null) return;
    doc.removeAll();
    if (s != null) {
      addParagraph(s);
    } else {
      jtd.set("");
    }
  }
  
  public void setParagraph(Vector v) {
    if (doc == null) return;
    doc.removeAll();
    if (v == null) {
      jtd.set("");
      return;
    }
    for (int i = 0; i < v.size(); i++) {
      String s = (String)v.elementAt(i);
      addParagraph(s);
    }
  }
  


  public void format()
  {
    if (doc == null) return;
    FontMetrics fm = jtd.getFontMetric();
    doc.setLengths(fm);
    
    String lines = doc.format(jtd.getWidth(), fm.stringWidth(" "));
    jtd.set(lines);
  }
  
  public void textDisplayLabelCallBack(int id) {
    setVisible(false);
  }
  
  public void textDisplayLogCallBack(int id, String str) {}
  
  public void voidNotify(int id) {
    if (id == 0) {
      format();
    } else {
      reduceHeightIfNecessary();
    }
  }
  
  public void reduceHeightIfNecessary()
  {
    int diff = jtd.getDesiredHeightChange();
    if (diff <= 0) return;
    Rectangle bounds = getBounds();
    height -= diff;
    setBounds(bounds);
  }
}
