package Jeli.Widgets;

import java.awt.FontMetrics;
import java.util.Vector;


public class Document
{
  public Vector paras;
  private boolean parskip = true;
  private int pindent = 0;
  
  public Document(Vector v) {
    paras = v;
  }
  
  public void removeAll() {
    if (paras != null)
      paras.removeAllElements();
  }
  
  public int getPindent() {
    return pindent;
  }
  
  public void setPindent(int n) {
    pindent = n;
  }
  
  public boolean getParskip() {
    return parskip;
  }
  
  public void setParskip(boolean f) {
    parskip = f;
  }
  
  public int getLength() {
    if (paras == null) return 0;
    return paras.size();
  }
  
  public void addParagraph(Paragraph para) {
    if (paras == null)
      paras = new Vector();
    paras.addElement(para);
  }
  

  public void setLengths(FontMetrics fm)
  {
    if (paras == null) return;
    for (int i = 0; i < paras.size(); i++) {
      Paragraph para = (Paragraph)paras.elementAt(i);
      para.setLengths(fm);
    }
  }
  



  public String format(int wid, int spaceWid)
  {
    if (paras == null) {
      return "";
    }
    Vector v = new Vector();
    
    for (int i = 0; i < paras.size(); i++) {
      Paragraph next = (Paragraph)paras.elementAt(i);
      if ((v.size() != 0) && 
        (parskip)) {
        v.addElement("");
      }
      next.formatV(v, wid, spaceWid, pindent);
    }
    
    StringBuffer sb = new StringBuffer();
    
    for (int i = 0; i < v.size(); i++) {
      sb.append((String)v.elementAt(i));
      sb.append("\n");
    }
    return new String(sb);
  }
}
