package Jeli.Widgets;

import java.awt.Scrollbar;

public class JeliScrollbar extends Scrollbar
{
  public int size = 12;
  
  public JeliScrollbar(int a, int b, int c, int d, int e) {
    super(a, b, c, d, e);
  }
  
  public void setScrollSize(int s) {
    size = s;
  }
  
  public java.awt.Dimension getPreferredSize() {
    return new java.awt.Dimension(size, size);
  }
}
