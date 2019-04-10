package Jeli.Widgets;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class HelpManagerCanvas extends JeliCanvas
{
  String msg;
  String[] msg_array;
  FontMetrics fm = null;
  int y0;
  int delta_y;
  HelpManager hm;
  private final boolean border_flag = true;
  private final int border_x = 4;
  private final int border_y = 2;
  private int gotten_count = 0;
  private String debug_string = "ButtonInfo";
  private boolean focus = false;
  private Color backcolor = Jeli.Utility.jeliVeryLightYellow;
  private Color textcolor = Color.black;
  private Color highlightcolor = Color.red;
  private int preferredWidth = -1;
  private int preferredHeight = -1;
  private int maxW = 600;
  private int maxH = 400;
  private int minW = 100;
  private int minH = 10;
  
  public HelpManagerCanvas(HelpManager hm)
  {
    this.hm = hm;
    setBackground(backcolor);
    msg = "This is a test";
    msg_array = null;
    addInt2CheatCode("ShowColorCom2");
    addIntCheatCode("ShowColorCom");
    addCheatCode("ShowColorComs");
    addCheatCode("ButtonInfo");
    addCheatCode("ButtonDebug");
    addCheatCode("DynamicOn");
    addCheatCode("DynamicOff");
    addCallback(hm);
  }
  
  protected void setInitialSize(int w, int h) {
    preferredWidth = w;
    preferredHeight = h;
  }
  
  private void setMetrics()
  {
    java.awt.Font f = getFont();
    if (f != null)
      fm = getFontMetrics(f);
  }
  
  protected void fillImage(int w, int h) {
    canvasGC.setColor(backcolor);
    canvasGC.fillRect(0, 0, w, h);
    if (fm == null) {
      setMetrics();
    }
    canvasGC.setColor(textcolor);
    
    canvasGC.drawRect(0, 0, w - 1, h - 1);
    if (msg_array == null) {
      canvasGC.drawString(msg, 4, y0);

    }
    else if (hm.highlight_delim == 0) {
      for (int i = 0; i < msg_array.length; i++)
        canvasGC.drawString(msg_array[i], 4, y0 + i * delta_y);
    } else {
      for (int i = 0; i < msg_array.length; i++) {
        draw_string_with_highlight(msg_array[i], 4, y0 + i * delta_y, textcolor, highlightcolor, canvasGC, fm, hm.highlight_delim);
      }
    }
  }
  



  public void draw_string_with_highlight(String s, int x, int y, Color fg, Color hc, Graphics g, FontMetrics fm, char delim)
  {
    boolean fore = true;
    int pos = x;
    int ind = 0;
    for (;;) {
      if (fore) g.setColor(fg); else
        g.setColor(hc);
      int ind1 = s.indexOf(delim, ind);
      if (ind1 < 0) {
        g.drawString(s.substring(ind), pos, y);
        return;
      }
      String s1 = s.substring(ind, ind1);
      g.drawString(s1, pos, y);
      pos += fm.stringWidth(s1);
      ind = ind1 + 1;
      if (fore) fore = false; else
        fore = true;
    }
  }
  
  public int getHeight(int n) {
    if (fm == null) setMetrics();
    if (fm == null) return 10 * n + 2;
    return n * (fm.getMaxAscent() + fm.getMaxDescent()) + 4;
  }
  







  public void setMessage(String msg)
  {
    if (fm == null) setMetrics();
    if (fm == null) return;
    this.msg = msg;
    y0 = (fm.getMaxAscent() + 2);
    int h = y0 + fm.getMaxDescent();
    delta_y = h;
    int w; int w; if (msg.indexOf("\n") == -1) {
      msg_array = null;
      w = fm.stringWidth(msg);
    }
    else {
      java.util.StringTokenizer stk = new java.util.StringTokenizer(msg, "\n");
      int count = stk.countTokens();
      msg_array = new String[count];
      stk = new java.util.StringTokenizer(msg, "\n");
      for (int i = 0; stk.hasMoreTokens(); i++)
        msg_array[i] = stk.nextToken();
      w = fm.stringWidth(msg_array[0]);
      for (i = 1; i < msg_array.length; i++) {
        int w1 = fm.stringWidth(msg_array[i]);
        if (w1 > w) w = w1;
      }
      h = delta_y * msg_array.length;
    }
    if (!hm.getDynamicResize())
      return;
    int h1 = h + 4;
    int w1 = w + 8;
    Rectangle bounds = getBounds();
    if (w1 > maxW)
      w1 = maxW;
    if (w1 < minW)
      w1 = minW;
    if (h1 > maxH)
      h1 = maxH;
    if (h1 < minH)
      h1 = minH;
    if (w1 < width)
      w1 = width;
    if (h1 < height)
      h1 = height;
    if ((w1 <= width) && (h1 <= height))
      return;
    preferredWidth = w1;
    preferredHeight = h1;
  }
  
  public java.awt.Dimension getPreferredSize() { java.awt.Dimension bounds;
    java.awt.Dimension bounds;
    if ((preferredWidth <= 0) || (preferredHeight <= 0)) {
      bounds = super.getPreferredSize();
    } else
      bounds = new java.awt.Dimension(preferredWidth, preferredHeight);
    return bounds;
  }
}
