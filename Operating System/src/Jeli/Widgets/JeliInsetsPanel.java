package Jeli.Widgets;

import java.awt.Color;
import java.awt.Insets;

public class JeliInsetsPanel extends java.awt.Panel
{
  private Insets insets;
  private Color color;
  
  public JeliInsetsPanel(int top, int bottom, int left, int right, Color color)
  {
    if (top + bottom + right + left == 0) {
      insets = super.getInsets();
      return;
    }
    insets = new Insets(top, left, bottom, right);
    this.color = color;
    setBackground(color);
  }
  
  public void setBackground(Color c)
  {
    super.setBackground(c);
    color = c;
  }
  
  public JeliInsetsPanel(int ind, Color color) {
    this(ind, ind, ind, ind, color);
  }
  
  public Insets getInsets() {
    return insets;
  }
  
  public void paint(java.awt.Graphics g)
  {
    super.paint(g);
    if (getInsetstop + getInsetsbottom + getInsetsleft + getInsetsright == 0)
      return;
    g.setColor(color);
    java.awt.Rectangle bounds = getBounds();
    g.fillRect(0, 0, width, insets.top);
    g.fillRect(0, 0, insets.left, height);
    g.fillRect(width - insets.right, 0, insets.right, height);
    g.fillRect(0, height - insets.bottom, width, insets.bottom);
  }
}
