package Jeli.Widgets;

import java.awt.Dimension;

public class SizedPanel
  extends JeliPanel
{
  private int presize;
  private int forced_height = -1;
  private int forced_width = -1;
  
  public SizedPanel(int n)
  {
    presize = n;
  }
  
  public void setHeightOnly(int h) {
    forced_height = h;
  }
  
  public void setWidthOnly(int w) {
    forced_width = w;
  }
  
  public Dimension getPreferredSize()
  {
    Dimension psize = super.getPreferredSize();
    if (forced_height > 0) {
      return new Dimension(width, forced_height);
    }
    if (forced_width > 0) {
      return new Dimension(forced_width, height);
    }
    if (width > presize) {
      return psize;
    }
    return new Dimension(presize, height);
  }
}
