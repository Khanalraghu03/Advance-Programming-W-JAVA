package Jeli.Widgets;

import java.util.StringTokenizer;
import java.util.Vector;

public class Paragraph
{
  public Vector words;
  
  public Paragraph(Vector v)
  {
    words = v;
  }
  
  public int getLength() {
    if (words == null) return 0;
    return words.size();
  }
  
  public String getWord(int n) {
    if (words == null) return null;
    if (n > words.size()) return null;
    if (n < 0) return null;
    return words.elementAt(n)).str;
  }
  
  public void addWord(String s) {
    if (words == null)
      words = new Vector();
    words.addElement(new StringAndInt(s));
  }
  
  public void clear() {
    if (words != null) {
      words.removeAllElements();
    }
  }
  
  public void setFromString(String s)
  {
    StringTokenizer stk = new StringTokenizer(s, " \t\r\n");
    if (words == null) words = new Vector();
    words.removeAllElements();
    while (stk.hasMoreTokens()) {
      words.addElement(new StringAndInt(stk.nextToken()));
    }
  }
  
  public void setLengths(java.awt.FontMetrics fm) {
    if (words == null) return;
    for (int i = 0; i < words.size(); i++) {
      StringAndInt next = (StringAndInt)words.elementAt(i);
      val = fm.stringWidth(str);
    }
  }
  




  public Vector formatV(Vector v, int wid, int spaceWid, int pindent)
  {
    if (v == null)
      v = new Vector();
    if (words == null) return v;
    StringBuffer sb = new StringBuffer();
    int currentLength = 0;
    
    for (int i = 0; i < words.size(); i++) {
      StringAndInt next = (StringAndInt)words.elementAt(i);
      int extrawid; int extrawid; if ((i == 0) && (currentLength == 0))
        extrawid = pindent * spaceWid; else
        extrawid = 0;
      if ((currentLength + val + spaceWid + extrawid < wid) || (currentLength == 0))
      {
        if (extrawid > 0) {
          if (i == 0)
            for (int j = 0; j < pindent; j++)
              sb.append(" ");
          sb.append(str);
          currentLength = val + extrawid;
        }
        else {
          sb.append(" " + str);
          currentLength += val + spaceWid;
        }
      }
      else {
        v.addElement(sb.toString());
        currentLength = 0;
        sb.setLength(0);
        i--;
      }
    }
    if (currentLength > 0) {
      v.addElement(sb.toString());
    }
    return v;
  }
  


  public String[] format(int wid, int spacewid, int pindent)
  {
    Vector v = formatV(null, wid, spacewid, pindent);
    String[] para = new String[v.size()];
    for (int i = 0; i < v.size(); i++)
      para[i] = ((String)v.elementAt(i));
    return para;
  }
}
