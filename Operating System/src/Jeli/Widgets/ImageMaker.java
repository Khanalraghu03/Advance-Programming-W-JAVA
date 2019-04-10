package Jeli.Widgets;

import java.awt.FontMetrics;
import java.awt.Graphics;

public class ImageMaker
{
  private java.awt.Font font;
  private FontMetrics fm;
  private java.awt.Color foreColor;
  private java.awt.Color backColor;
  private java.awt.Color borderColor;
  private java.awt.Canvas can;
  private int border = 2;
  private int pad = 2;
  private int skip = 1;
  
  public ImageMaker(java.awt.Canvas can)
  {
    HelpManager hm = Jeli.Utility.hm;
    this.can = can;
    if (hm != null) font = hm.getFont();
    if (font == null) font = new java.awt.Font("Times", 0, 10);
    fm = can.getFontMetrics(font);
    foreColor = java.awt.Color.black;
    backColor = java.awt.Color.white;
    borderColor = java.awt.Color.black;
  }
  






  public java.awt.Image getImage(String str)
  {
    if (str.indexOf('\n') >= 0) return getImageArray(str);
    int h = fm.getMaxAscent() + fm.getMaxDescent() + 2 * border + 2 * pad;
    int w = fm.stringWidth(str) + 2 * border + 2 * pad;
    int x = border + pad;
    int y = fm.getMaxAscent() + border + pad;
    java.awt.Image im = can.createImage(w, h);
    Graphics g = im.getGraphics();
    g.setFont(font);
    g.setColor(borderColor);
    g.fillRect(0, 0, w, h);
    g.setColor(backColor);
    g.fillRect(border, border, w - 2 * border, h - 2 * border);
    g.setColor(foreColor);
    g.drawString(str, x, y);
    g.dispose();
    return im;
  }
  











  private java.awt.Image getImageArray(String str)
  {
    int count = 1;
    
    for (int i = 0; i < str.length() - 1; i++)
      if (str.charAt(i) == '\n') count++;
    String[] strs = new String[count];
    int pos = 0;
    for (int i = 0; i < count; i++) {
      int nlpos = str.indexOf('\n', pos);
      if (nlpos <= 0) strs[i] = str.substring(pos); else
        strs[i] = str.substring(pos, nlpos);
      pos = nlpos + 1;
    }
    int lineskip = fm.getMaxAscent() + fm.getMaxDescent() + skip;
    int h = lineskip * count + 2 * border + 2 * pad - skip;
    int w = fm.stringWidth(strs[0]) + 2 * border + 2 * pad;
    for (int i = 1; i < count; i++) {
      int wt = fm.stringWidth(strs[i]) + 2 * border + 2 * pad;
      if (wt > w) w = wt;
    }
    int x = border + pad;
    int y = fm.getMaxAscent() + border + pad;
    java.awt.Image im = can.createImage(w, h);
    Graphics g = im.getGraphics();
    g.setFont(font);
    g.setColor(borderColor);
    g.fillRect(0, 0, w, h);
    g.setColor(backColor);
    g.fillRect(border, border, w - 2 * border, h - 2 * border);
    g.setColor(foreColor);
    for (int i = 0; i < count; i++)
      g.drawString(strs[i], x, y + i * lineskip);
    g.dispose();
    return im;
  }
}
