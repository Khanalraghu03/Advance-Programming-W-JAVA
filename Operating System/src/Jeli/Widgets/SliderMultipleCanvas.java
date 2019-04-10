package Jeli.Widgets;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

public class SliderMultipleCanvas extends JeliCanvas implements JeliMouseDragger
{
  private int min;
  private int max;
  private int num;
  private Color backColor;
  private Color foreColor;
  private double barWidthFract;
  private int barWidthMin;
  private int barWidthMax;
  private Image im = null;
  private int w = -1;
  private int h = -1;
  private Graphics gfore = null;
  private Graphics gback = null;
  private Graphics gborder = null;
  private int[] vals;
  private int barWidth;
  private int movingBar = -1;
  private int delta;
  private VoidCallBack cb = null;
  private boolean displayValueFlag = false;
  private int y0 = 0;
  



  public SliderMultipleCanvas(int min, int max, int num, Color backColor, Color foreColor, double barWidthFract, int barWidthMin, int barWidthMax, VoidCallBack cb)
  {
    this.min = min;
    this.max = max;
    this.num = num;
    this.backColor = backColor;
    this.foreColor = foreColor;
    this.barWidthFract = barWidthFract;
    this.barWidthMin = barWidthMin;
    this.barWidthMax = barWidthMax;
    this.cb = cb;
    set_size();
    vals = new int[num];
    vals[0] = 0;
    for (int i = 1; i < num; i++)
      vals[i] = (vals[(i - 1)] + (max - min) / num);
    barWidth = barWidthMin;
    setMouseReceiver(this);
  }
  
  public void setValue(int which, int val) {
    if ((which < 0) || (which >= num)) return;
    setAValue(which, val);
    if (cb != null)
      cb.voidNotify(which);
  }
  
  public void initializeValue(int which, int val) {
    vals[which] = val;
    if (cb != null) {
      cb.voidNotify(which);
    }
  }
  


  private void set_size()
  {
    int border_y = 2;
    int w = barWidthMin * num * 2;
    int h; if (Jeli.Utility.hm == null) {
      int h = 10;
      y0 = 0;
    }
    else {
      java.awt.Font f = Jeli.Utility.hm.getFont();
      java.awt.FontMetrics fm = getFontMetrics(f);
      h = fm.getMaxAscent() + 2 * border_y + fm.getMaxDescent();
      y0 = ((h + fm.getMaxAscent() - fm.getMaxDescent()) / 2);
    }
    setSize(w, h);
  }
  
  public void update(Graphics g) {
    paint(g);
  }
  

  public void paint(Graphics g)
  {
    java.awt.Rectangle bounds = getBounds();
    if ((h == -1) || (w == -1) || (w != width) || (h != height)) {
      w = width;
      h = height;
      makeImage(w, h);
    }
    if (im == null)
      return;
    fillImage(w, h);
    g.drawImage(im, 0, 0, this);
  }
  
  protected void makeImage(int w, int h) {
    if (im != null)
      im.flush();
    im = createImage(w, h);
    if (gback != null)
      gback.dispose();
    gback = im.getGraphics();
    if (gfore != null)
      gfore.dispose();
    gfore = im.getGraphics();
    if (gborder != null)
      gborder.dispose();
    gborder = im.getGraphics();
    gback.setColor(backColor);
    gfore.setColor(foreColor);
    gborder.setColor(Color.black);
    barWidth = ((int)(barWidthFract * w + 0.5D));
    if (barWidth < barWidthMin)
      barWidth = barWidthMin;
    if (barWidth > barWidthMax)
      barWidth = barWidthMax;
  }
  
  protected void fillImage(int w, int h) {
    if (im == null)
      return;
    gback.fillRect(0, 0, w, h);
    for (int i = 0; i < num; i++) {
      drawBar(gfore, gborder, i);
    }
  }
  
  private void drawBar(Graphics g1, Graphics g2, int which) {
    int x = getX(which);
    g1.fillRect(x, 0, barWidth, h);
    g2.drawRect(x, 0, barWidth - 1, h - 1);
    if (displayValueFlag) {
      g2.drawString("" + vals[which], x + 3, y0);
    }
  }
  


  private int getX(int which)
  {
    double totalPixels = w - num * barWidth;
    int x = (int)(totalPixels * (vals[which] - min) / (max - min) + 0.5D) + barWidth * which;
    return x;
  }
  

  private int xToVal(int x, int which)
  {
    double totalPixels = w - num * barWidth;
    int val = (int)((x - barWidth * which) * (max - min) / totalPixels + 0.5D) + min;
    return val;
  }
  
  public int findBarAtX(int x)
  {
    for (int i = 0; i < num; i++) {
      int xs = getX(i);
      if ((xs <= x) && (x <= xs + barWidth))
        return i;
    }
    return -1;
  }
  
  public void mousePressedAt(int x, int y)
  {
    int which = findBarAtX(x);
    if (which >= 0) {
      movingBar = which;
      delta = (x - getX(movingBar));
    }
  }
  
  public void mouseReleasedAt(int x, int y) {
    movingBar = -1;
    repaint();
  }
  
  public void mouseDragged(int x, int y)
  {
    if (movingBar >= 0) {
      int value = xToVal(x - delta, movingBar);
      setAValue(movingBar, value);
      if (cb != null)
        cb.voidNotify(movingBar);
      repaint();
    }
  }
  
  public void mouseReleased(int x, int y) {}
  
  public int getValue(int i)
  {
    return vals[i];
  }
  
  private void setAValue(int which, int val)
  {
    int previous;
    int previous;
    if (which == 0) {
      previous = min;
    } else
      previous = vals[(which - 1)];
    int next; int next; if (which == num - 1) {
      next = max;
    } else
      next = vals[(which + 1)];
    if (val <= previous) {
      vals[which] = previous;
    } else if (val >= next) {
      vals[which] = next;
    } else
      vals[which] = val;
  }
  
  public void setDisplayValueFlag(boolean f) {
    displayValueFlag = f;
  }
}
