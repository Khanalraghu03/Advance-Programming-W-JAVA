package Jeli.Widgets;

import java.util.Vector;

public class JeliTextCanvas extends JeliCanvas implements JeliCanvasCallBack, JeliMouseDragger, Runnable { private Vector info;
  private java.awt.Font f;
  private java.awt.FontMetrics fm;
  private int canvasWidth;
  private int canvasHeight;
  private int deltay;
  private int lineOffset;
  private int underlineOffset;
  private int inity;
  private java.awt.Color currentForeColor;
  private java.awt.Color currentBackColor;
  class ColoredLineInfo { java.awt.Color fc; java.awt.Color bc;
    public ColoredLineInfo(java.awt.Color fc, java.awt.Color bc, int len) { this.fc = fc;
      this.bc = bc;
      this.len = len;
    }
    
    int len; }
  
  class ColoredLine { public static final int TYPE_NONE = 0;
    public static final int TYPE_LINE = 1;
    public static final int TYPE_MULTICOLOR = 2;
    int specialType = 0;
    String str;
    java.awt.Color fc;
    java.awt.Color bc;
    Vector colorInfo;
    int[] tabtype = null;
    int totab = -1;
    
    public ColoredLine(String str, java.awt.Color fc, java.awt.Color bc, int[] tabtype) {
      this.str = str;
      this.fc = fc;
      this.bc = bc;
      this.tabtype = tabtype;
    }
    
    public ColoredLine(int type, java.awt.Color fc, java.awt.Color bc) {
      str = "";
      specialType = type;
      this.fc = fc;
      this.bc = bc;
    }
    
    public ColoredLine(int type, java.awt.Color fc, java.awt.Color bc, int totab) {
      str = "";
      specialType = type;
      this.fc = fc;
      this.bc = bc;
      this.totab = totab;
    }
    

    public String show()
    {
      String s;
      if (specialType == 0) {
        s = "Type: None"; } else { String s;
        if (specialType == 1) {
          s = "Type: Line"; } else { String s;
          if (specialType == 2) {
            s = "Type: Multicolor";
          } else
            s = "Type: Unknown"; } }
      String s = s + "\nForeground=" + fc;
      s = s + "\nBackground=" + bc;
      s = s + "\ntotab = " + totab;
      if (tabtype == null) {
        s = s + "\nno tab type";
      } else {
        s = s + "\ntab types follow: ";
        for (int i = 0; i < tabtype.length; i++)
          s = s + tabtype[i] + " ";
      }
      if (str == null) {
        s = s + "string is null";
      } else {
        s = s + "\nstring of length " + str.length() + " follows:\n";
        s = s + str;
        s = s + "\nspecial characters: ";
        for (int i = 0; i < str.length(); i++) {
          char c = str.charAt(i);
          if ((c < ' ') || (c > '~'))
            s = s + "(" + i + "," + c + ") ";
        }
      }
      return s;
    }
  }
  













  private java.awt.Color permanentBackColor = null;
  private boolean firstPaint = true;
  private int currentLine = 0;
  private int longestLine = 0;
  private int oldLongestLine = 0;
  private int firstPixel = 0;
  private int initx = 0;
  private java.awt.Image im = null;
  private java.awt.Graphics img = null;
  private int currentWidth = -1;
  private int currentHeight = -1;
  private JeliTextArea ta;
  private boolean scrollAfterAppend = false;
  private int noScrollTop;
  private int noScrollBottom;
  private int level;
  private boolean showStatFlag = false;
  private boolean monoFlag = false;
  private int deltax = 0;
  private int[] tabpos;
  private int[] tabtype;
  private int[] tabx;
  private int tabWidth = 1;
  private boolean dynamicTabs = false;
  private boolean setTabs = false;
  private boolean showTabs = false;
  private int dragTab = -1;
  private int dragOriginalPosition;
  private int dragOriginalStart;
  private boolean inhibitDisplayFlag = false;
  private VoidCallBack vcb = null;
  private double fontSize = 0.0D;
  private int inhibitEndTab = 0;
  private boolean dynamicTabsFull = false;
  private boolean mouseByTab = false;
  private boolean useHighlightedTabs = false;
  private boolean[][] highlightedFlags = (boolean[][])null;
  private java.awt.Color highlightedColor = java.awt.Color.cyan;
  
  public JeliTextCanvas(java.awt.Font f, int r, int c, JeliTextArea ta)
  {
    this.ta = ta;
    setFont(f);
    fontSize = f.getSize();
    fm = getFontMetrics(f);
    inity = fm.getMaxAscent();
    deltay = (inity + fm.getMaxDescent());
    

    canvasHeight = (r * deltay);
    deltax = fm.stringWidth("x");
    canvasWidth = (c * deltax);
    info = new Vector();
    currentForeColor = getForeground();
    currentBackColor = getBackground();
    noScrollTop = 0;
    noScrollBottom = 0;
    level = 0;
    lineOffset = 0;
    underlineOffset = 0;
    tabpos = null;
    tabtype = null;
    tabx = null;
    addCheatCode("ShowStat");
    addCheatCode("FontPlain");
    addCheatCode("FontBold");
    addCheatCode("FontItalic");
    addCheatCode("Repack");
    addCheatCode("Scale");
    addCheatCode("ScaleW");
    addCheatCode("ScaleH");
    addIntCheatCode("SetRows");
    addIntCheatCode("SetColumns");
    addIntCheatCode("SetWidth");
    addIntCheatCode("SetHeight");
    addIntCheatCode("FontSize");
    addIntCheatCode("ShowLine");
    addStringCheatCode("FontFamily");
    addCallback(this);
    setMouseReceiver(this);
  }
  
  public void setTabs(int[] tabs) {
    if (tabs == null) {
      tabpos = null;
      tabtype = null;
      tabx = null;
      return;
    }
    tabpos = new int[tabs.length + 1];
    tabtype = new int[tabs.length + 1];
    tabx = new int[tabs.length + 1];
    tabpos[0] = 0;
    tabtype[0] = 0;
    for (int i = 0; i < tabs.length; i++) {
      tabpos[(i + 1)] = tabs[i];
      tabtype[(i + 1)] = 0;
      tabx[(i + 1)] = (tabWidth * tabs[i]);
    }
  }
  
  public void setNewTabs(int n) {
    int lastpos = 0;
    


    if (n < tabpos.length) return;
    int[] newtabpos = new int[n + 1];
    int[] newtabtype = new int[n + 1];
    int[] newtabx = new int[n + 1];
    for (int i = 0; i < tabpos.length; i++) {
      newtabpos[i] = tabpos[i];
      newtabtype[i] = tabtype[i];
      newtabx[i] = tabx[i];
      lastpos = tabpos[i];
    }
    for (int i = tabpos.length; i < n + 1; i++) {
      newtabpos[i] = (lastpos + 5 * (tabpos.length - i + 1));
      newtabtype[i] = 0;
      newtabx[i] = (tabWidth * newtabpos[i]);
    }
    tabpos = newtabpos;
    tabtype = newtabtype;
    tabx = newtabx;
  }
  
  private void resetTabs() {
    if (tabpos == null) return;
    for (int i = 0; i < tabpos.length; i++)
      tabx[i] = (tabWidth * tabpos[i]);
  }
  
  private void resetTabs1() {
    if (tabpos == null) return;
    if (tabWidth == 0) return;
    for (int i = 0; i < tabpos.length; i++)
      tabpos[i] = (tabx[i] / tabWidth);
  }
  
  public void setTabPositionLeft(int n) {
    if (tabpos.length > n)
      tabtype[n] = 0;
  }
  
  public void setTabPositionRight(int n) {
    if (tabpos.length > n)
      tabtype[n] = 1;
  }
  
  public void setTabPositionCenter(int n) {
    if (tabpos.length > n)
      tabtype[n] = 2;
  }
  
  public void setDynamicTabs(boolean f) {
    dynamicTabs = f;
  }
  
  public void setSetTabs(boolean f) {
    setTabs = f;
    repaint();
  }
  
  public void setShowTabs(boolean f) {
    showTabs = f;
    repaint();
  }
  
  public void setFont(java.awt.Font f) {
    super.setFont(f);
    resetTabs1();
    fm = getFontMetrics(f);
    inity = fm.getMaxAscent();
    deltay = (inity + fm.getMaxDescent());
    deltax = fm.stringWidth("x");
    tabWidth = fm.stringWidth("0");
    lineOffset = ((fm.getMaxAscent() - fm.getMaxDescent()) / 2);
    underlineOffset = (fm.getMaxDescent() - 1);
    
    setupImages();
    resetLongestLine();
    resetTabs();
    repaint();
  }
  
  public java.awt.FontMetrics getFontMetric() {
    return fm;
  }
  

  public void setFontSize(int n)
  {
    java.awt.Font f = img.getFont();
    if (f == null) return;
    java.awt.Font f1 = new java.awt.Font(f.getName(), f.getStyle(), n);
    setFont(f1);
  }
  
  public void showLine(int n)
  {
    if ((n < 0) || (n >= info.size())) {
      System.out.println("Line " + n + " does not exist");
      return;
    }
    ColoredLine cl = (ColoredLine)info.elementAt(n);
    System.out.println("Info on line " + n + " follows:");
    System.out.println(cl.show());
  }
  
  public void setDoubleFontSize(double fs) {
    fontSize = fs;
  }
  

  public void setFontFamily(String fam)
  {
    java.awt.Font f = img.getFont();
    if (f == null) return;
    java.awt.Font f1 = new java.awt.Font(fam, f.getStyle(), f.getSize());
    setFont(f1);
  }
  

  public void setFontPlain()
  {
    java.awt.Font f = img.getFont();
    if (f == null) return;
    java.awt.Font f1 = new java.awt.Font(f.getName(), 0, f.getSize());
    setFont(f1);
  }
  

  public void setFontBold()
  {
    java.awt.Font f = img.getFont();
    if (f == null) return;
    java.awt.Font f1 = new java.awt.Font(f.getName(), 1, f.getSize());
    setFont(f1);
  }
  

  public void setFontItalic()
  {
    java.awt.Font f = img.getFont();
    if (f == null) return;
    java.awt.Font f1 = new java.awt.Font(f.getName(), 2, f.getSize());
    setFont(f1);
  }
  
  public java.awt.Dimension getPreferredSize() {
    return new java.awt.Dimension(canvasWidth, canvasHeight);
  }
  
  public void changePreferredWidth(int w) {
    canvasWidth += w;
    reValidate();
  }
  
  public void update(java.awt.Graphics g) {
    paint(g);
  }
  

  private void setupImages()
  {
    currentWidth = getBoundswidth;
    if (currentWidth == 0) return;
    currentHeight = getBoundsheight;
    if (im != null)
      im.flush();
    im = createImage(currentWidth, currentHeight);
    if (img != null)
      img.dispose();
    img = im.getGraphics();
    java.awt.Font f = getFont();
    if (f != null)
      img.setFont(f);
    currentLine = 0;
    ta.resetScrollbars();
    ta.imagesReset();
  }
  
  private void fixScrollbars() {
    ta.addScrollbars();
  }
  








  public synchronized void paint(java.awt.Graphics g)
  {
    int thisLineLength = 0;
    int displayedLongestLine = 0;
    


    if (inhibitDisplayFlag)
      return;
    if ((getBoundswidth != currentWidth) || (getBoundsheight != currentHeight))
    {
      setupImages(); }
    if (vcb != null) {
      vcb.voidNotify(1);
      vcb = null;
    }
    if (currentForeColor == null)
      currentForeColor = getForeground();
    if (currentBackColor == null)
      currentBackColor = getBackground();
    int w = currentWidth;
    int h = currentHeight;
    if (monoFlag) {
      img.setColor(java.awt.Color.white);
    } else
      img.setColor(getBackground());
    img.fillRect(0, 0, w, h);
    int displayed = h / deltay;
    if (showStatFlag)
    {



      java.awt.Font f1 = img.getFont();
      java.awt.FontMetrics fm1 = getFontMetrics(f1);
      int total_font_height = fm1.getMaxDescent() + fm1.getMaxAscent();
      int x_width = fm1.stringWidth("x");
      int heightNeeded = info.size() * deltay + inity;
      System.out.println("Showing stats in paint");
      System.out.println("   currentLine =       " + currentLine);
      System.out.println("   size =              " + info.size());
      System.out.println("   dispalyed =         " + displayed);
      

      System.out.println("   preferred width =   " + canvasWidth);
      System.out.println("   preferred height =  " + canvasHeight);
      System.out.println("   current width =     " + currentWidth);
      System.out.println("   current height =    " + currentHeight);
      System.out.println("   height needed =     " + heightNeeded);
      System.out.println("   total font height = " + total_font_height);
      
      System.out.println("   deltax =            " + deltax);
      System.out.println("   width of x =        " + x_width);
      
      System.out.println("   longestLine =       " + longestLine);
      System.out.println("   initx =             " + initx);
      System.out.println("   firstPixel =        " + firstPixel);
      System.out.println("   fontSize =          " + fontSize);
      System.out.println("   font = " + f1);
      if (dynamicTabsFull) {
        System.out.println("   dynamic tabs full = true");
      } else
        System.out.println("   dynamic tabs full = false");
      if (tabpos != null) {
        System.out.print("   dynamic tabs: " + dynamicTabs + " ");
        for (int i = 0; i < tabx.length; i++)
          System.out.print(" " + tabx[i]);
        System.out.println();
      }
    }
    if (dynamicTabsFull) {
      calculateDynamicTabsFull();
    } else
      calculateDynamicTabs(displayed);
    if (permanentBackColor != null)
      img.setColor(permanentBackColor);
    img.fillRect(0, 0, w, h);
    for (int i = 0; i < displayed; i++) {
      if (i + currentLine >= info.size()) break;
      int infoIndex; int infoIndex; if (i < noScrollTop) {
        infoIndex = i; } else { int infoIndex;
        if (i >= displayed - noScrollBottom) {
          infoIndex = info.size() + i - displayed;
        } else
          infoIndex = i + currentLine; }
      ColoredLine cl = (ColoredLine)info.elementAt(infoIndex);
      if (bc == null)
        bc = currentBackColor;
      if (fc == null)
        fc = currentForeColor;
      if (monoFlag) {
        img.setColor(java.awt.Color.white);
      } else
        img.setColor(bc);
      img.fillRect(0, i * deltay, w, deltay);
      java.awt.Color lineColor;
      java.awt.Color lineColor;
      if (monoFlag) {
        lineColor = java.awt.Color.black;
      } else
        lineColor = fc;
      img.setColor(lineColor);
      int x = initx - firstPixel;
      int y = inity + i * deltay;
      if (specialType == 0) {
        if ((tabpos != null) && (str.indexOf("\t") >= 0)) {
          drawTabbedLine(cl, lineColor, infoIndex, initx - firstPixel, inity + i * deltay);
          
          thisLineLength = getTabLineLength(str);
        }
        else {
          img.drawString(str, initx - firstPixel, inity + i * deltay);
          thisLineLength = fm.stringWidth(str);
          drawUnderline(cl, y + underlineOffset);
        }
      } else if (specialType == 2) {
        drawMulticoloredLine(cl, img, initx - firstPixel, inity + i * deltay);
      } else if (specialType == 1) {
        if ((totab != -1) && (tabx != null)) {
          int tpos = tabx[totab];
          if (!setTabs)
            tpos -= tabWidth / 2;
          if (tpos - firstPixel >= 0) {
            img.drawLine(0, y - lineOffset, tpos - firstPixel, y - lineOffset);
          }
        } else {
          img.drawLine(0, y - lineOffset, currentWidth, y - lineOffset); }
        thisLineLength = 0;
      }
      if (thisLineLength > displayedLongestLine) {
        displayedLongestLine = thisLineLength;
      }
    }
    if (displayedLongestLine > longestLine) {
      longestLine = displayedLongestLine;
      ta.resetScrollbars();
    }
    if ((setTabs) && (tabpos != null)) {
      for (int i = 1; i < tabx.length; i++) {
        img.drawLine(initx - firstPixel + tabx[i], 0, initx - firstPixel + tabx[i], currentHeight);
      }
      
    }
    else if ((showTabs) && (tabpos != null)) {
      for (int i = 1; i < tabx.length - inhibitEndTab; i++) {
        img.drawLine(initx - firstPixel + tabx[i] - tabWidth / 2, 0, initx - firstPixel + tabx[i] - tabWidth / 2, currentHeight);
      }
    }
    

    g.drawImage(im, 0, 0, this);
    showStatFlag = false;
  }
  


  private void calculateDynamicTabsOne(String str, int[] widths)
  {
    int pos1 = -1;
    
    for (int i = 0; i < widths.length; i++) {
      int pos = str.indexOf('\t', pos1 + 1);
      if (pos < 0) { return;
      }
      String s = str.substring(pos1 + 1, pos);
      int w = fm.stringWidth(s);
      
      if (widths[i] < w) widths[i] = w;
      pos1 = pos;
    }
  }
  


  private void calculateDynamicTabsFull()
  {
    if (tabpos == null) return;
    if (!dynamicTabs) return;
    int[] widths = new int[tabpos.length - 1];
    for (int i = 0; i < widths.length; i++)
      widths[i] = 0;
    for (int i = 0; i < info.size(); i++) {
      ColoredLine cl = (ColoredLine)info.elementAt(i);
      if ((specialType == 0) && 
        (str.indexOf('\t') >= 0))
        calculateDynamicTabsOne(str, widths);
    }
    for (int i = 0; i < widths.length; i++) {
      tabx[(i + 1)] = (tabx[i] + widths[i] + tabWidth);
    }
  }
  
  private void calculateDynamicTabs(int displayed)
  {
    if (tabpos == null) return;
    if (!dynamicTabs) return;
    int[] widths = new int[tabpos.length - 1];
    for (int i = 0; i < widths.length; i++) {
      widths[i] = 0;
    }
    for (int i = 0; i < displayed; i++) {
      if (i + currentLine >= info.size()) break;
      ColoredLine cl; ColoredLine cl; if (i < noScrollTop) {
        cl = (ColoredLine)info.elementAt(i);
      } else { ColoredLine cl;
        if (i >= displayed - noScrollBottom) {
          cl = (ColoredLine)info.elementAt(info.size() + i - displayed);
        }
        else
          cl = (ColoredLine)info.elementAt(i + currentLine);
      }
      if ((specialType == 0) && 
        (str.indexOf('\t') >= 0))
        calculateDynamicTabsOne(str, widths);
    }
    for (int i = 0; i < widths.length; i++) {
      tabx[(i + 1)] = (tabx[i] + widths[i] + tabWidth);
    }
  }
  

  private void drawTabbedLine(ColoredLine cl, java.awt.Color lineColor, int index, int x, int y)
  {
    int[] ttype;
    
    int[] ttype;
    
    if (tabtype == null) {
      ttype = tabtype;
    } else {
      ttype = tabtype;
    }
    int pos = str.indexOf('\t');
    if (pos < 0) {
      img.drawString(str, x, y);
      return;
    }
    
    int pos1 = -1;
    for (int i = 0; i < tabpos.length; i++) {
      if (i < tabpos.length - 1) {
        pos = str.indexOf('\t', pos1 + 1);
      } else
        pos = str.length();
      if (pos < 0)
      {
        if (str.length() > pos1 + 1) {
          if (useHighlightedTabs) {}
          

          img.drawString(str.substring(pos1 + 1), x + tabx[i], y);
        }
        drawUnderline(cl, y + underlineOffset);
        return;
      }
      
      String str = str.substring(pos1 + 1, pos);
      int xfix; int xfix; if (ttype[i] == 0) {
        xfix = 0; } else { int xfix;
        if (ttype[i] == 1) {
          xfix = tabx[i] + fm.stringWidth(str) - tabx[(i + 1)] + tabWidth;
        } else {
          int xfix;
          if (ttype[i] == 2)
          {
            xfix = (tabx[i] + fm.stringWidth(str) - tabx[(i + 1)]) / 2 + tabWidth / 2;





          }
          else
          {




            xfix = 0; }
        } }
      if (useHighlightedTabs)
      {



        if ((highlightedFlags != null) && (index < highlightedFlags.length) && (i < highlightedFlags[index].length) && (i < tabx.length - 1) && (highlightedFlags[index][i] != 0))
        {



          img.setColor(highlightedColor);
          img.fillRect(x + tabx[i] - tabWidth / 2, y - inity, tabx[(i + 1)] - tabx[i], deltay);
          img.setColor(lineColor);
        }
      }
      img.drawString(str, x + tabx[i] - xfix, y);
      pos1 = pos;
    }
    

    drawUnderline(cl, y + underlineOffset);
  }
  
  private void drawUnderline(ColoredLine cl, int y)
  {
    if (totab == -1) return;
    if (totab == 0) {
      img.drawLine(0, y, currentWidth, y);
    }
    else if (tabx != null) {
      int tpos = tabx[totab];
      if (!setTabs)
        tpos -= tabWidth / 2;
      if (tpos - firstPixel >= 0) {
        img.drawLine(0, y, tpos - firstPixel, y);
      }
    }
  }
  
  private void drawMulticoloredLine(ColoredLine cl, java.awt.Graphics g, int x, int y)
  {
    int numDone = 0;
    for (int i = 0; i < colorInfo.size(); i++) {
      ColoredLineInfo cli = (ColoredLineInfo)colorInfo.elementAt(i);
      String str = str.substring(numDone, numDone + len);
      numDone += len;
      if (!monoFlag)
        g.setColor(fc);
      g.drawString(str, x, y);
      x += fm.stringWidth(str);
    }
  }
  
  protected void setForegroundColor(java.awt.Color c) {
    currentForeColor = c;
  }
  
  protected java.awt.Color getForegroundColor() {
    return currentForeColor;
  }
  
  protected void setBackgroundColor(java.awt.Color c) {
    currentBackColor = c;
  }
  
  protected void setPermanentBackground(java.awt.Color c) {
    permanentBackColor = c;
  }
  
  protected java.awt.Color getBackgroundColor() {
    return currentBackColor;
  }
  
  protected void append(String str) {
    append(str, null);
  }
  


  protected void append(String str, int[] tabtype)
  {
    if (str == null) return;
    for (int i = 0; i < level; i++) str = "   " + str;
    int len; int len; if (tabpos == null) {
      len = fm.stringWidth(str);
    } else {
      len = getTabLineLength(str);
    }
    if (len > longestLine) { longestLine = len;
    }
    ColoredLine cl = new ColoredLine(str, currentForeColor, currentBackColor, tabtype);
    info.addElement(cl);
    int displayed = getBoundsheight / deltay;
    if ((scrollAfterAppend) && (displayed > 0) && (info.size() > displayed))
      currentLine = (info.size() - displayed);
    oldLongestLine = longestLine;
    repaint();
  }
  
  protected void setLastUnderline(int n)
  {
    if (info.size() == 0) return;
    ColoredLine cl = (ColoredLine)info.elementAt(info.size() - 1);
    totab = n;
  }
  
  private int getTabLineLength(String str) {
    int pos = 0;
    int pos1 = -1;
    


    for (int i = 1; i < tabpos.length; i++) {
      pos = str.indexOf('\t', pos1 + 1);
      
      if (pos < 0) return 0;
      pos1 = pos;
    }
    
    String s = str.substring(pos + 1);
    
    int len = fm.stringWidth(s) + tabx[(tabpos.length - 1)];
    

    return len;
  }
  

  protected void appendToCurrent(String str)
  {
    if (info.size() == 0) {
      append(str);
      return;
    }
    ColoredLine cl = (ColoredLine)info.elementAt(info.size() - 1);
    if (specialType == 0) {
      specialType = 2;
      colorInfo = new Vector();
      colorInfo.addElement(new ColoredLineInfo(fc, bc, str.length()));
    }
    
    str += str;
    colorInfo.addElement(new ColoredLineInfo(currentForeColor, currentBackColor, str.length()));
    

    int len = fm.stringWidth(str);
    if (len > longestLine) longestLine = len;
    repaint();
  }
  


  private void resetLongestLine()
  {
    if (info == null) return;
    longestLine = 0;
    for (int i = 0; i < info.size(); i++) {
      String line = info.elementAt(i)).str;
      int thisline = fm.stringWidth(line);
      if (thisline > longestLine) {
        longestLine = thisline;
      }
    }
  }
  
  protected void appendLineIfNecessary()
  {
    if (info.size() > 0) {
      ColoredLine cl = (ColoredLine)info.lastElement();
      if (specialType == 1)
        return;
    }
    appendLine();
  }
  

  protected void appendLineIfNecessary(int totab)
  {
    if (info.size() > 0) {
      ColoredLine cl = (ColoredLine)info.lastElement();
      if (specialType == 1)
        return;
    }
    appendLine(totab);
  }
  
  protected void appendLine()
  {
    info.addElement(new ColoredLine(1, currentForeColor, currentBackColor));
    
    int displayed = getBoundsheight / deltay;
    if ((scrollAfterAppend) && (displayed > 0) && (info.size() > displayed))
      currentLine = (info.size() - displayed);
    repaint();
  }
  
  protected void appendLine(int totab)
  {
    info.addElement(new ColoredLine(1, currentForeColor, currentBackColor, totab));
    
    int displayed = getBoundsheight / deltay;
    if ((scrollAfterAppend) && (displayed > 0) && (info.size() > displayed))
      currentLine = (info.size() - displayed);
    repaint();
  }
  

  protected void bottom()
  {
    int displayed = getBoundsheight / deltay;
    if (info.size() > displayed)
      currentLine = (info.size() - displayed);
  }
  
  protected void bottomtest() {
    if (scrollAfterAppend) bottom();
  }
  
  protected void clear() {
    info.removeAllElements();
    currentLine = 0;
    longestLine = 0;
    firstPixel = 0;
    repaint();
  }
  
  protected int getLines() {
    return info.size();
  }
  
  protected int getCurrentLine() {
    return currentLine;
  }
  
  protected void setCurrentLine(int cl) {
    currentLine = cl;
    repaint();
  }
  
  protected int getDisplayedLines() {
    if (deltay == 0) return 0;
    return getBoundsheight / deltay;
  }
  
  protected int getDisplayedLines(int h) {
    if (deltay == 0) return 0;
    return h / deltay;
  }
  
  protected int getLongestLine() {
    return longestLine;
  }
  
  protected int getPixelWidth() {
    return getBoundswidth;
  }
  
  protected int getFirstPixel() {
    return firstPixel;
  }
  
  protected void setFirstPixel(int fp) {
    firstPixel = fp;
    repaint();
  }
  
  protected void setAppendScroll(boolean f) {
    scrollAfterAppend = f;
  }
  
  protected void setBackgrounds(java.awt.Color[] c) {
    for (int i = 0; (i < info.size()) && (i < c.length); i++)
      info.elementAt(i)).bc = c[i];
    repaint();
  }
  
  protected int getNumberLines() {
    return info.size();
  }
  


  protected void setBottom(int n)
  {
    int n1 = n;
    if (n1 > info.size()) n1 = info.size();
    int displayed = currentHeight / deltay;
    if (n1 > displayed + currentLine) {
      currentLine = (n1 - displayed);
      fixScrollbars();
      repaint();
    }
  }
  
  protected void setNoScrollTop(int n) {
    noScrollTop = n;
    fixScrollbars();
  }
  
  protected void setNoScrollBottom(int n) {
    noScrollBottom = n;
    fixScrollbars();
  }
  
  protected int getNoScrollTop() {
    return noScrollTop;
  }
  
  protected int getNoScrollBottom() {
    return noScrollBottom;
  }
  
  protected void removeLine(int n) {
    if (n < 0) return;
    if (n >= info.size()) return;
    info.removeElementAt(n);
    repaint();
  }
  
  protected void removeLines(int n, int m) {
    if (n < 0) return;
    if (m < n) return;
    if (m >= info.size()) m = info.size() - 1;
    for (int i = 0; i < m - n + 1; i++)
      info.removeElementAt(n);
    repaint();
  }
  
  protected void setLevel(int n) {
    level = n;
  }
  
  protected void incrementLevel(int n) {
    level += n;
    if (level < 0) { level = 0;
    }
  }
  


  protected String getText()
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < info.size(); i++) {
      ColoredLine cl = (ColoredLine)info.elementAt(i);
      sb.append(str + "\n");
    }
    return sb.toString();
  }
  
  protected String getHTML(boolean mono) {
    return getHTML(mono, 10);
  }
  










  protected String getHTML(boolean mono, int maxcol)
  {
    StringBuffer sb = new StringBuffer();
    if (tabpos != null)
      sb.append("<table border = 1>\n");
    for (int i = 0; i < info.size(); i++) {
      ColoredLine cl = (ColoredLine)info.elementAt(i);
      if ((tabpos == null) || (specialType != 1))
      {
        if (tabpos != null)
          sb.append("<tr>");
        int type = specialType;
        if ((type == 2) && (mono))
          type = 0;
        if (type == 2) {
          String str = str;
          if (colorInfo != null) {
            int numDone = 0;
            for (int j = 0; j < colorInfo.size(); j++) {
              ColoredLineInfo cli = (ColoredLineInfo)colorInfo.elementAt(j);
              String str1 = str.substring(numDone, numDone + len);
              numDone += len;
              sb.append("<font color=\"#" + hexColor(fc) + "\">");
              sb.append(fixSpecialHTMLChars(str1));
              sb.append("</font>");
            }
          }
          sb.append("<br>\n");
        }
        else if (type == 0) {
          if (!mono)
            sb.append("<font color=\"#" + hexColor(fc) + "\">");
          String str = str;
          str = fixSpecialHTMLChars(str);
          if (tabpos == null) {
            sb.append(str);
          } else {
            int pos1 = -1;
            int[] ttype; int[] ttype; if (tabtype == null) {
              ttype = tabtype;
            } else
              ttype = tabtype;
            for (int j = 0; j < tabpos.length; j++) { int pos;
              int pos; if (j < tabpos.length - 1) {
                pos = str.indexOf('\t', pos1 + 1);
              } else
                pos = str.length();
              if (pos < 0) {
                if (str.length() <= pos1 + 1) break;
                if (pos1 > 0) {
                  sb.append("<td>");
                  sb.append(leadBlanksHTML(str.substring(pos1 + 1)));
                  sb.append("</td>");
                  break;
                }
                sb.append("<td colspan=" + maxcol + ">");
                sb.append(leadBlanksHTML(str.substring(pos1 + 1)));
                sb.append("</td>");
                
                break;
              }
              
              str = str.substring(pos1 + 1, pos);
              if (ttype[j] == 1) {
                sb.append("<TD ALIGN=RIGHT>");
              }
              else if (ttype[j] == 2) {
                sb.append("<TD ALIGN=CENTER>");
              }
              else {
                sb.append("<TD>");
              }
              if (str.trim().length() == 0)
                str = "&nbsp";
              sb.append(leadBlanksHTML(str) + "</TD>");
              pos1 = pos;
            }
          }
          if (!mono)
            sb.append("</font>");
          if (tabpos == null) {
            sb.append("<br>\n");
          } else {
            sb.append("\n");
          }
        } else if (type == 1) {
          if (!mono)
            sb.append("<font color=\"#" + hexColor(fc) + "\">");
          sb.append("<hr></font>\n");
        }
        if (tabpos != null)
          sb.append("</tr>");
      } }
    if (tabpos != null)
      sb.append("</table>\n");
    return sb.toString();
  }
  

  private String leadBlanksHTML(String str)
  {
    if (!str.startsWith(" "))
      return str;
    String s = "";
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) != ' ') break;
      s = s + "&nbsp";
    }
    
    if (i == str.length())
      return s + " ";
    return s + " " + str.substring(i);
  }
  
  private String fixSpecialHTMLChars(String str)
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < str.length(); i++)
      if (str.charAt(i) == '<') {
        sb.append("&lt;");
      } else if (str.charAt(i) == '>') {
        sb.append("&gt;");
      } else if (str.charAt(i) == ' ') {
        sb.append("&nbsp;");
      } else
        sb.append(str.charAt(i));
    return sb.toString();
  }
  
  private String hexColor(java.awt.Color c)
  {
    return intToHex(c.getRed()) + intToHex(c.getGreen()) + intToHex(c.getBlue());
  }
  
  private String intToHex(int n) {
    return "" + intToHexDigit(n / 16) + intToHexDigit(n % 16);
  }
  
  private char intToHexDigit(int n) {
    if (n < 10) return (char)(48 + n);
    return (char)(65 + n - 10);
  }
  
  public void jeliCanvasCallback(String str) {
    if (str.equals("ShowStat"))
    {
      showStatFlag = true;
      repaint();
    }
    if (str.equals("FontPlain"))
    {
      setFontPlain();
      repaint();
    }
    if (str.equals("FontBold"))
    {
      setFontBold();
      repaint();
    }
    if (str.equals("FontItalic"))
    {
      setFontItalic();
      repaint();
    }
    if (str.equals("Repack")) {
      reValidate();
    }
    if (str.equals("Scale")) {
      canvasWidth = longestLine;
      canvasHeight = (info.size() * deltay);
      reValidate();
      reValidate();
    }
    if (str.equals("ScaleW")) {
      canvasWidth = longestLine;
      reValidate();
      reValidate();
    }
    if (str.equals("ScaleH")) {
      canvasHeight = (info.size() * deltay);
      reValidate();
      reValidate();
    }
  }
  
  private void reValidate() {
    reValidateIt();
  }
  

  private void setLocationOfFrame()
  {
    java.awt.Component p = this;
    java.awt.Component p1 = this;
    while (p != null) {
      p1 = p;
      p = p.getParent();
    }
    if ((p1 instanceof java.awt.Frame)) {
      ((java.awt.Frame)p1).setLocation(500, 500);
    }
  }
  
  public void run() {
    reValidateIt();
  }
  

  private synchronized void reValidateIt()
  {
    java.awt.Component p = this;
    java.awt.Component p1 = this;
    while (p != null) {
      p.invalidate();
      p1 = p;
      p = p.getParent();
    }
    p1.validate();
    if ((p1 instanceof java.awt.Frame))
      ((java.awt.Frame)p1).pack();
    ta.resetScrollbars();
  }
  
  public void keyPressed(java.awt.event.KeyEvent e)
  {
    int kc = e.getKeyCode();
    

    if (kc == 38) {
      canvasHeight += deltay;
      reValidate();

    }
    else if (kc == 40) {
      if (canvasHeight > 2 * deltay) {
        canvasHeight -= deltay;
        reValidate();
      }
      
    }
    else if (kc == 39) {
      canvasWidth += deltax;
      reValidate();

    }
    else if ((kc == 37) && 
      (canvasWidth > 2 * deltax)) {
      canvasWidth -= deltax;
      reValidate();
    }
  }
  

  public void jeliCanvasCallback(String str, int val)
  {
    if (str.equals("SetRows")) {
      canvasHeight = (val * deltay);
    }
    if (str.equals("SetColumns")) {
      canvasWidth = (val * fm.stringWidth("x"));
    }
    if (str.equals("SetWidth")) {
      canvasWidth = val;
    }
    if (str.equals("SetHeight")) {
      canvasHeight = val;
    }
    if (str.equals("FontSize")) {
      setFontSize(val);
      return;
    }
    if (str.equals("ShowLine")) {
      showLine(val);
      return;
    }
    
    repaint();
    reValidate();
  }
  
  public void jeliCanvasCallback(String str, int val1, int val2) {}
  
  public void jeliCanvasCallback(String str, String str1) {
    if (str.equals("FontFamily")) {
      setFontFamily(str1);
      return;
    }
  }
  
  protected void setMono(boolean f) {
    monoFlag = f;
    repaint();
  }
  

  protected void setLineForeground(int n, java.awt.Color c)
  {
    if (n >= info.size()) return;
    ColoredLine cl = (ColoredLine)info.elementAt(n);
    fc = c;
    repaint();
  }
  
  protected void setAllLinesForeground(java.awt.Color c) {
    for (int i = 0; i < info.size(); i++) {
      setLineForeground(i, c);
    }
  }
  
  public void mouseDragged(int x, int y) {
    if (dragTab < 0) return;
    tabx[dragTab] = (dragOriginalPosition + x - dragOriginalStart);
    repaint();
  }
  
  public void mouseReleased(int x, int y) { dragTab = -1;
    repaint();
  }
  

  public void mousePressedAt(int x, int y)
  {
    if ((setTabs) && (tabpos != null)) {
      for (int i = 1; i < tabx.length; i++)
        if (tabx[i] > x) break;
      if (i == tabx.length) i--;
      if ((i > 1) && 
        (x - tabx[(i - 1)] < tabx[i] - x)) { i--;
      }
      dragTab = i;
      dragOriginalPosition = tabx[i];
      dragOriginalStart = x;
      tabx[i] += 1;
      repaint();
      return;
    }
    int line = y / deltay + currentLine;
    if (line < info.size())
      if ((mouseByTab) && (tabpos != null)) {
        for (int i = 1; i < tabx.length; i++)
          if (tabx[i] > x) break;
        ta.mousePushed(line, i - 1, y);
      }
      else {
        ta.mousePushed(line, x, y);
      }
  }
  
  public void mouseReleasedAt(int x, int y) {}
  
  public void inhibitDisplay(boolean f) { inhibitDisplayFlag = f;
    if (!f) repaint();
  }
  
  public int getDesiredHeightChange(VoidCallBack vcb) {
    if (currentHeight <= 0) {
      this.vcb = vcb;
      return -1;
    }
    return currentHeight - (info.size() * deltay + inity);
  }
  
  public void setInhibitEndTab(int n) {
    if (n >= 0)
      inhibitEndTab = n;
  }
  
  public void setDynamicTabsFull(boolean f) {
    dynamicTabsFull = f;
    resetLongestLine();
  }
  
  protected void scaleWidth(int max) {
    calculate_longest_line();
    if (longestLine > max)
      return;
    canvasWidth = longestLine;
    reValidate();
    reValidate();
  }
  
  protected void scaleHeight(int max) {
    if (info.size() * deltay > max)
      return;
    canvasHeight = (info.size() * deltay);
    reValidate();
    reValidate();
  }
  
  protected void scale(int maxw, int maxh) {
    calculate_longest_line();
    if (longestLine <= maxw)
      canvasWidth = longestLine;
    if (info.size() * deltay <= maxh)
      canvasHeight = (info.size() * deltay);
    reValidate();
    reValidate();
  }
  



  private void calculate_longest_line()
  {
    calculateDynamicTabsFull();
    longestLine = 0;
    for (int i = 0; i < info.size(); i++) {
      ColoredLine cl = (ColoredLine)info.elementAt(i);
      if (specialType == 0) { int thisLineLength;
        int thisLineLength; if (tabpos != null) {
          thisLineLength = getTabLineLength(str);
        } else
          thisLineLength = fm.stringWidth(str);
        if (thisLineLength > longestLine)
          longestLine = thisLineLength;
      }
    }
  }
  
  protected void setMouseByTab(boolean f) { mouseByTab = f; }
  
  protected void setUseHighlightedTabs(boolean f)
  {
    useHighlightedTabs = f;
  }
  
  protected void setHighlightedFlags(boolean[][] flags) {
    highlightedFlags = flags;
  }
  
  protected void setHighlightedColor(java.awt.Color c) {
    highlightedColor = c;
  }
}
