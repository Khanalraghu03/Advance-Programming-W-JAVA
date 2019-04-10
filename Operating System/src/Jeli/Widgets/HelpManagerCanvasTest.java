package Jeli.Widgets;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.io.PrintStream;
import java.util.StringTokenizer;

public class HelpManagerCanvasTest extends HelpManagerCanvas
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
  private boolean firstFillDone = false;
  private int preferredWidth = -1;
  private int preferredHeight = -1;
  private int minWidth = 200;
  private int minHeight = 30;
  private int maxWidth = 600;
  private int maxHeight = 400;
  
  public HelpManagerCanvasTest(HelpManager hm) {
    super(hm);
    this.hm = hm;
    setBackground(backcolor);
    msg = "This is a test";
    msg_array = null;
    addInt2CheatCode("ShowColorCom2");
    addIntCheatCode("ShowColorCom");
    addCheatCode("ShowColorComs");
    addCheatCode("ButtonInfo");
    addCheatCode("ButtonDebug");
    addCallback(hm);
    System.out.println("This is HelpManagerCanvasTest");
  }
  
  private void setMetrics()
  {
    java.awt.Font f = getFont();
    if (f != null) {
      fm = getFontMetrics(f);
    }
  }
  





  protected void fillImage(int w, int h)
  {
    canvasGC.setColor(backcolor);
    canvasGC.fillRect(0, 0, w, h);
    if (fm == null) {
      setMetrics();
    }
    
    canvasGC.setColor(textcolor);
    
    canvasGC.drawRect(0, 0, w - 1, h - 1);
    
    if (msg_array == null) {
      canvasGC.drawString(msg, 4, y0);
      System.out.println("Drew string " + msg + " at " + 4 + " " + y0);
    }
    else if (hm.highlight_delim == 0) {
      for (int i = 0; i < msg_array.length; i++) {
        canvasGC.drawString(msg_array[i], 4, y0 + i * delta_y);
      }
    }
    else {
      for (int i = 0; i < msg_array.length; i++) {
        draw_string_with_highlight(msg_array[i], 4, y0 + i * delta_y, textcolor, highlightcolor, canvasGC, fm, hm.highlight_delim);
      }
    }
    

    System.out.println("fillImage done");
    firstFillDone = true;
    canvasGC.setColor(Color.black);
    canvasGC.drawString("This is a test", 10, 30);
  }
  





  public void draw_string_with_highlight(String s, int x, int y, Color fg, Color hc, Graphics g, FontMetrics fm, char delim)
  {
    boolean fore = true;
    int pos = x;
    int ind = 0;
    for (;;) {
      if (fore) {
        g.setColor(fg);
      }
      else {
        g.setColor(hc);
      }
      int ind1 = s.indexOf(delim, ind);
      if (ind1 < 0) {
        g.drawString(s.substring(ind), pos, y);
        return;
      }
      String s1 = s.substring(ind, ind1);
      g.drawString(s1, pos, y);
      pos += fm.stringWidth(s1);
      ind = ind1 + 1;
      if (fore) {
        fore = false;
      }
      else {
        fore = true;
      }
    }
  }
  












  public void setMessage(String msg)
  {
    if (!firstFillDone) {
      System.out.println("Inhibiting set message");
      return;
    }
    
    if (fm == null) {
      setMetrics();
    }
    if (fm == null) {
      return;
    }
    this.msg = msg;
    y0 = (fm.getMaxAscent() + 4);
    int h0 = y0 + fm.getMaxDescent();
    delta_y = h0;
    System.out.println("delta_y = " + delta_y);
    int w0; if (msg.indexOf("\n") == -1) {
      msg_array = null;
      int w0 = fm.stringWidth(msg);
      System.out.println("msg array is null");
    }
    else {
      StringTokenizer stk = new StringTokenizer(msg, "\n");
      int count = stk.countTokens();
      msg_array = new String[count];
      stk = new StringTokenizer(msg, "\n");
      for (int i = 0; stk.hasMoreTokens(); i++) {
        msg_array[i] = stk.nextToken();
      }
      w0 = fm.stringWidth(msg_array[0]);
      for (i = 1; i < msg_array.length; i++) {
        int w1 = fm.stringWidth(msg_array[i]);
        if (w1 > w0) {
          w0 = w1;
        }
      }
      h0 = delta_y * msg_array.length;
      System.out.println("msg array has size " + msg_array.length);
    }
    int h1 = h0 + 4;
    int w1 = w0 + 8;
    if (h1 > maxHeight) {
      h1 = maxHeight;
    }
    if (w1 > maxWidth) {
      w1 = maxWidth;
    }
    if ((h1 > h) || (w1 > w)) {
      if (h1 > h) {
        h = h1;
      }
      if (w1 > w) {
        w = w1;
      }
      if (w < minWidth) {
        w = minWidth;
      }
      if (h < minHeight) {
        h = minHeight;
      }
      setSize(w, h);
      preferredWidth = w;
      preferredHeight = h;
      hm.reValidate();
    }
  }
  

  public java.awt.Dimension getPreferredSize()
  {
    java.awt.Dimension dim = super.getPreferredSize();
    System.out.println("preferred size is " + dim);
    if ((preferredWidth > 0) && (preferredHeight > 0)) {
      dim = new java.awt.Dimension(preferredWidth, preferredHeight);
    }
    System.out.println("Using preferred size = " + dim);
    return dim;
  }
}
