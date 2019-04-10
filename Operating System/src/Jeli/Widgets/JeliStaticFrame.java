package Jeli.Widgets;

import Jeli.Utility;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;

public class JeliStaticFrame extends JeliFrame implements Jeli.Logging.LogState
{
  private JeliTextDisplay jtd;
  private int rows;
  private int columns;
  private JeliButton hideButton;
  private int id;
  private boolean disposeFlag = false;
  

  private String label;
  

  public JeliStaticFrame(int id, int minRows, int maxRows, int minColumns, int maxColumns, String label, String data, TextDisplayButtonCallBack cb, Component com)
  {
    this(id, minRows, maxRows, minColumns, minColumns, label, data, cb, com, true);
  }
  


  public JeliStaticFrame(int id, int minRows, int maxRows, int minColumns, int maxColumns, String label, String data, TextDisplayButtonCallBack cb, Component com, boolean showFlag)
  {
    super(label);
    
    this.id = id;
    this.label = label;
    java.awt.Font font = Utility.hm.getTableFont();
    
    super.setFont(font);
    rows = countLines(data);
    columns = (longestLine(data) + 5);
    if (rows > maxRows)
      rows = maxRows;
    if (rows < minRows)
      rows = minRows;
    if (columns > maxColumns)
      columns = maxColumns;
    if (columns < minColumns)
      columns = minColumns;
    jtd = new JeliTextDisplay(id, rows, columns, label, font, Color.white, Color.black, Utility.hm, cb);
    
    jtd.disableClearButton();
    jtd.labelButton.setAsLabel();
    jtd.textArea.append(data);
    setBackground(Color.white);
    setForeground(Color.black);
    setupLayout();
    if (com != null) {
      Point pos = Utility.getAbsolutePosition(com);
      int x = x + getBoundswidth / 2;
      int y = y + getBoundsheight / 2;
      if (x < 0) x = 0;
      if (y < 0) y = 0;
      setLocation(x, y);
    }
    if (showFlag)
      Utility.show(this);
  }
  
  public void replace(String s) {
    jtd.textArea.clear();
    jtd.textArea.append(s);
  }
  

  private int countLines(String s)
  {
    int count = 0;
    for (int i = 0; i < s.length(); i++)
      if (s.charAt(i) == '\n')
        count++;
    return count;
  }
  
  private int longestLine(String s) {
    int longest = 0;
    int thisline = 0;
    for (int i = 0; i < s.length(); i++)
      if (s.charAt(i) == '\n') {
        if (thisline > longest)
          longest = thisline;
        thisline = 0;
      }
      else {
        thisline++;
      }
    return longest;
  }
  
  private void setupLayout()
  {
    setLayout(new java.awt.BorderLayout());
    add("Center", jtd);
    hideButton = new JeliButton("Hide", this);
    hideButton.setClassification(label);
    hideButton.setButtonSize();
    hideButton.setPositionCenter();
    add("South", hideButton);
    pack();
  }
  
  public void disposeOnHide() {
    disposeFlag = true;
  }
  

  public void jeliButtonPushed(JeliButton bh)
  {
    String label = bh.getLabel();
    if (label.equals("Hide")) {
      Utility.hide(this);
      if (disposeFlag) {
        Utility.hm.removeButton(hideButton);
        jtd.disposeButtons();
      }
      return;
    }
  }
  
  public void logStateChange(boolean open) {
    if (open) {
      jtd.logButton.enableJeliButton();
    } else {
      jtd.logButton.disableJeliButton();
    }
  }
  
  public void setNumTabs(int n) {
    int[] tabs = new int[n];
    for (int i = 0; i < n; i++)
      tabs[i] = (10 * i + 10);
    jtd.setTabs(tabs);
    jtd.setDynamicTabs(true);
    jtd.setDynamicTabsFull(true);
  }
  
  public void setShowTabs()
  {
    jtd.setShowTabs(true);
  }
  
  public void setNoScrollTop(int n) {
    jtd.setNoScrollTop(n);
  }
  
  public void scale(int w, int h) {
    jtd.scale(w, h);
  }
}
