package Jeli.Widgets;

import Jeli.Utility;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;











public class JeliTextArea
  extends Panel
  implements AdjustmentListener, JeliTextList
{
  public static final int TAB_POSITION_LEFT = 0;
  public static final int TAB_POSITION_RIGHT = 1;
  public static final int TAB_POSITION_CENTER = 2;
  private JeliTextCanvas jtc;
  private Scrollbar vertScroll;
  private Scrollbar horScroll;
  private Font f;
  private boolean vertScrollUsed = false;
  private boolean horScrollUsed = false;
  private JeliMouseCallBack jmcb = null;
  private boolean inhibitDisplay = false;
  private VoidCallBack vcb = null;
  private boolean mouseByTab = false;
  
  public JeliTextArea() {
    this(10, 40);
  }
  
  public JeliTextArea(int r, int c) {
    this(r, c, new Font("Dialog", 0, 10));
  }
  
  public JeliTextArea(int r, int c, Font f)
  {
    this.f = f;
    setLayout(new BorderLayout());
    add("Center", this.jtc = new JeliTextCanvas(f, r, c, this));
    vertScroll = new JeliScrollbar(1, 0, 0, 1, 1);
    horScroll = new JeliScrollbar(0, 0, 0, 1, 1);
    vertScroll.addAdjustmentListener(this);
    horScroll.addAdjustmentListener(this);
    jtc.addCheatCode("Background");
  }
  
  public void setVoidCallBack(VoidCallBack cb) {
    vcb = cb;
  }
  
  public void setFont(Font f) {
    super.setFont(f);
    jtc.setFont(f);
  }
  
  public void setFont(Font f, double fs) {
    setFont(f);
    jtc.setDoubleFontSize(fs);
  }
  
  public int getWidth() {
    return jtc.getBounds().width;
  }
  
  public FontMetrics getFontMetric() {
    return jtc.getFontMetric();
  }
  
  public void setText(String str) {
    jtc.clear();
    append(str);
    resetScrollbars();
  }
  






















  public void append(String str)
  {
    append(str, null);
  }
  

  public synchronized void append(String str, int[] tabs)
  {
    if (str == null) return;
    if (str.length() == 0) return;
    int currentInd = str.indexOf('\n');
    if (currentInd == -1) {
      appendOne(str, tabs);
      return;
    }
    while (currentInd >= 0) {
      appendOne(str.substring(0, currentInd), tabs);
      str = str.substring(currentInd + 1);
      currentInd = str.indexOf('\n');
    }
    if (str.length() > 0)
      appendOne(str, tabs);
  }
  
  public synchronized void appendLine() {
    jtc.appendLine();
    addHorScrollbar();
  }
  
  public synchronized void appendLine(int totab) {
    jtc.appendLine(totab);
    addHorScrollbar();
  }
  
  public synchronized void appendLineIfNecessary() {
    jtc.appendLineIfNecessary();
    addHorScrollbar();
  }
  
  public synchronized void appendLineIfNecessary(int totab) {
    jtc.appendLineIfNecessary(totab);
    addHorScrollbar();
  }
  
  private void appendOne(String str, int[] tabs) {
    jtc.append(str, tabs);
    addHorScrollbar();
  }
  


  private void addHorScrollbar()
  {
    if (inhibitDisplay) return;
    int longest = jtc.getLongestLine();
    int w = jtc.getPixelWidth();
    fixVertScrollbar();
    if ((longest > w) && (horScrollUsed) && (w != 0))
      fixHorScrollbar();
    if ((longest > w) && (!horScrollUsed) && (w != 0)) {
      fixHorScrollbar();
      add("South", horScroll);
      horScrollUsed = true;
      invalidate();
      validate();
      jtc.bottomtest();
      fixVertScrollbar();
    }
  }
  
  public void addScrollbars() {
    addHorScrollbar();
  }
  
  public void clear() {
    jtc.clear();
    if ((vertScrollUsed) && (horScrollUsed)) {
      remove(vertScroll);
      vertScrollUsed = false;
      remove(horScroll);
      horScrollUsed = false;
      invalidate();
      validate();
    }
    if (vertScrollUsed) {
      remove(vertScroll);
      vertScrollUsed = false;
      invalidate();
      validate();
    }
    else if (horScrollUsed) {
      remove(horScroll);
      horScrollUsed = false;
      invalidate();
      validate();
    }
  }
  
  public void setForegroundColor(Color c) {
    jtc.setForegroundColor(c);
  }
  
  public Color getForegroundColor() {
    return jtc.getForegroundColor();
  }
  
  public void setBackgroundColor(Color c) {
    jtc.setBackgroundColor(c);
    jtc.setPermanentBackground(c);
  }
  
  public Color getBackgroundColor() {
    return jtc.getBackgroundColor();
  }
  
  public void adjustmentValueChanged(AdjustmentEvent e)
  {
    if (e.getSource() == vertScroll) {
      int value = vertScroll.getValue();
      jtc.setCurrentLine(value);
    }
    else if (e.getSource() == horScroll) {
      int value = horScroll.getValue();
      jtc.setFirstPixel(value);
    }
  }
  
  public void top() {
    jtc.setCurrentLine(0);
    fixVertScrollbar();
    fixHorScrollbar();
  }
  
  public void setCurrentLine(int n) {
    jtc.setCurrentLine(n);
    fixVertScrollbar();
    fixHorScrollbar();
  }
  
  private void fixScrollbars() {
    fixVertScrollbar();
  }
  


  private void fixHorScrollbar()
  {
    if (inhibitDisplay) return;
    int longest = jtc.getLongestLine();
    int w = jtc.getPixelWidth();
    int smallest = jtc.getFirstPixel();
    horScroll.setMinimum(0);
    horScroll.setMaximum(longest);
    horScroll.setValue(smallest);
    horScroll.setVisibleAmount(w);
  }
  







  private void fixVertScrollbar()
  {
    if (inhibitDisplay) return;
    int lines = jtc.getLines();
    int currentLine = jtc.getCurrentLine();
    
    int size = jtc.getDisplayedLines();
    int min = 0;
    int max = lines;
    int vis = size;
    if (size == 0) return;
    int value = currentLine;
    vertScroll.setMinimum(min);
    vertScroll.setMaximum(max);
    vertScroll.setVisibleAmount(vis);
    vertScroll.setValue(value);
    if ((lines > size) && (!vertScrollUsed)) {
      add("East", vertScroll);
      vertScrollUsed = true;
      invalidate();
      validate();
    }
  }
  
  protected void resetScrollbars() {
    boolean horScrollNeeded = false;
    boolean vertScrollNeeded = false;
    




    if (inhibitDisplay) return;
    fixVertScrollbar();
    fixHorScrollbar();
    int longest = jtc.getLongestLine();
    int w = jtc.getPixelWidth();
    if (w == 0) return;
    if (longest > w)
      horScrollNeeded = true;
    int lines = jtc.getLines();
    int size = jtc.getDisplayedLines(getBoundsheight);
    if (lines > size)
      vertScrollNeeded = true;
    if ((horScrollNeeded == horScrollUsed) && (vertScrollNeeded == vertScrollUsed))
    {
      return; }
    if ((vertScrollUsed) && (!vertScrollNeeded))
      remove(vertScroll);
    if ((!vertScrollUsed) && (vertScrollNeeded))
      add("East", vertScroll);
    if ((horScrollUsed) && (!horScrollNeeded))
      remove(horScroll);
    if ((!horScrollUsed) && (horScrollNeeded))
      add("South", horScroll);
    vertScrollUsed = vertScrollNeeded;
    horScrollUsed = horScrollNeeded;
    if (!horScrollUsed)
      jtc.setFirstPixel(0);
    invalidate();
    validate();
  }
  
  public void setAppendScroll(boolean f) {
    jtc.setAppendScroll(f);
  }
  
  public void setBackgrounds(Color[] c) {
    jtc.setBackgrounds(c);
  }
  
  public int getNumberLines() {
    return jtc.getNumberLines();
  }
  
  public void setBottom(int n) {
    jtc.setBottom(n);
  }
  
  public void setNoScrollTop(int n) {
    jtc.setNoScrollTop(n);
  }
  
  public void setNoScrollBottom(int n) {
    jtc.setNoScrollBottom(n);
  }
  
  public int getNoScrollTop() {
    return jtc.getNoScrollTop();
  }
  
  public int getNoScrollBottom() {
    return jtc.getNoScrollBottom();
  }
  
  public void removeLine(int n) {
    jtc.removeLine(n);
  }
  
  public void removeLines(int n, int m) {
    jtc.removeLines(n, m);
  }
  
  public void setLevel(int n) {
    jtc.setLevel(n);
  }
  
  public void incrementLevel(int n) {
    jtc.incrementLevel(n);
  }
  
  public String getText() {
    return jtc.getText();
  }
  
  public String getHTML(boolean mono) {
    return jtc.getHTML(mono);
  }
  
  public String getHTML() {
    return jtc.getHTML(false);
  }
  
  public int getCurrentLine() {
    return jtc.getCurrentLine();
  }
  
  public void addCheatCallback(JeliCanvasCallBack cb) {
    jtc.addCallback(cb);
  }
  
  public void setMono(boolean f) {
    jtc.setMono(f);
  }
  
  public void appendToCurrent(String str) {
    jtc.appendToCurrent(str);
  }
  
  public void setLineForeground(int n, Color c) {
    jtc.setLineForeground(n, c);
  }
  
  public void setAllLinesForeground(Color c) {
    jtc.setAllLinesForeground(c);
  }
  
  public void setFontPlain() {
    jtc.setFontPlain();
  }
  
  public void setFontBold() {
    jtc.setFontBold();
  }
  
  public void setFontItalic() {
    jtc.setFontItalic();
  }
  
  public void setFontFamily(String fam) {
    jtc.setFontFamily(fam);
  }
  
  public void setFontSize(int sz) {
    jtc.setFontSize(sz);
  }
  
  public void mousePushed(int line, int x, int y)
  {
    if (jmcb != null) {
      if (mouseByTab) {
        jmcb.mousePushed(line, x, y);
      } else {
        Point relPos = Utility.getRelativePosition(this, 1);
        jmcb.mousePushed(line, x + x, y + y);
      }
    }
  }
  
  public void setMouseCallBack(JeliMouseCallBack jmcb) {
    this.jmcb = jmcb;
  }
  
  public void setTabs(int[] tabs) {
    jtc.setTabs(tabs);
  }
  
  public void setNewTabs(int n) {
    jtc.setNewTabs(n);
  }
  
  public void setTabPositionLeft(int n) {
    jtc.setTabPositionLeft(n);
  }
  
  public void setTabPositionRight(int n) {
    jtc.setTabPositionRight(n);
  }
  
  public void setTabPositionCenter(int n) {
    jtc.setTabPositionCenter(n);
  }
  
  public void setDynamicTabs(boolean f) {
    jtc.setDynamicTabs(f);
  }
  
  public void setSetTabs(boolean f) {
    jtc.setSetTabs(f);
  }
  
  public void setShowTabs(boolean f) {
    jtc.setShowTabs(f);
  }
  
  public void setInhibitEndTab(int n) {
    jtc.setInhibitEndTab(n);
  }
  
  public void refresh() {
    jtc.repaint();
  }
  
  public void inhibitDisplay(boolean f) {
    inhibitDisplay = f;
    if (!inhibitDisplay) resetScrollbars();
    jtc.inhibitDisplay(f);
  }
  
  public void setLastUnderline(int n) {
    jtc.setLastUnderline(n);
  }
  
  public void changePreferredWidth(int w) {
    jtc.changePreferredWidth(w);
  }
  
  public void imagesReset() {
    if (vcb != null)
      vcb.voidNotify(0);
  }
  
  public int getDesiredHeightChange() {
    return jtc.getDesiredHeightChange(vcb);
  }
  
  public void addCheatCode(String str) {
    jtc.addCheatCode(str);
  }
  
  public void addIntCheatCode(String s) {
    jtc.addIntCheatCode(s);
  }
  
  public void addInt2CheatCode(String str) {
    jtc.addInt2CheatCode(str);
  }
  
  public void addStringCheatCode(String str) {
    jtc.addStringCheatCode(str);
  }
  
  public void setDynamicTabsFull(boolean f) {
    jtc.setDynamicTabsFull(f);
  }
  
  public void scaleWidth(int max) {
    jtc.scaleWidth(max);
  }
  
  public void scaleHeight(int max) {
    jtc.scaleHeight(max);
  }
  
  public void scale(int maxw, int maxh) {
    jtc.scale(maxw, maxh);
  }
  
  public void setMouseByTab(boolean f) {
    mouseByTab = f;
    jtc.setMouseByTab(f);
  }
  
  public void setUseHighlightedTabs(boolean f) {
    jtc.setUseHighlightedTabs(f);
  }
  
  public void setHighlightedFlags(boolean[][] flags) {
    jtc.setHighlightedFlags(flags);
  }
  
  public void setHighlightedColor(Color c) {
    jtc.setHighlightedColor(c);
  }
  
  public void setName(String s) {
    jtc.setName(s);
  }
}
