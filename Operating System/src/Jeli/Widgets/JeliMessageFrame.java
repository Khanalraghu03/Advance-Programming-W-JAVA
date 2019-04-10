package Jeli.Widgets;

import Jeli.Utility;
import java.awt.Panel;
import java.awt.Rectangle;
import java.util.Vector;

public class JeliMessageFrame extends JeliTextFrame implements DebugRegistration, TextDisplayButtonCallBack, JeliCanvasCallBack
{
  private boolean[][] displayFlags;
  private String[][] buttonStrings;
  private int w;
  private int h;
  private java.awt.Color onColor = Utility.jeliLightGreen;
  private java.awt.Color offColor = Utility.jeliLightRed;
  private Vector lines;
  private boolean showType = false;
  private JeliButton cloneButton;
  private JeliButton typeButton;
  private JeliButton noneButton;
  private JeliButton dividerButton;
  private Vector copies;
  private String dividerString = "--------------------------";
  private TextDisplayButtonCallBack logCallback = null;
  
  public JeliMessageFrame() {
    this(0);
  }
  
  public JeliMessageFrame(int id) {
    super(id, 20, 80, "Messages", Utility.hm, null, null);
    useHideButton();
    setShowButtons(false);
    setDebug(this);
    lines = new Vector();
    copies = new Vector();
    setCallBack(this);
    jtd.setCanvasCallback(this);
  }
  
  public void setLogCallBack(TextDisplayButtonCallBack lcb) {
    logCallback = lcb;
  }
  

  private boolean checkIndex(int i)
  {
    if (i <= 0)
      return true;
    int r = (i - 1) / h;
    int c = (i - 1) % h;
    return displayFlags[r][c];
  }
  
















  public synchronized void addLines(int n, String s)
  {
    for (int i = 0; i < copies.size(); i++)
      ((JeliMessageFrame)copies.elementAt(i)).addLines(n, s);
    while (s.length() > 0) {
      int pos = s.indexOf("\n");
      if (pos == -1) {
        addLine(n, " " + s + "\n");
        return;
      }
      addLine(n, " " + s.substring(0, pos + 1));
      s = s.substring(pos + 1);
    }
  }
  
  private synchronized void addLine(int n, String s) {
    lines.addElement(new NumberedLine(n, s));
    if (checkIndex(n)) {
      append(typeString(n) + s);
    }
  }
  
  private synchronized void fill() {
    clear();
    for (int i = 0; i < lines.size(); i++) {
      NumberedLine nline = (NumberedLine)lines.elementAt(i);
      if (checkIndex(n))
        append(typeString(n) + s);
    }
  }
  
  private String typeString(int n) {
    if (n < 0)
      return "";
    if ((showType & n < 10))
      return " " + n + ": ";
    if (showType) {
      return n + ": ";
    }
    return "";
  }
  
  public void setButtons(String[] bStrings) {
    int cols = 3;
    
    int rows = (bStrings.length - 1) / cols + 1;
    String[][] buttonStrings = new String[rows][cols];
    int ind = 0;
    for (int i = 0; i < rows; i++)
      for (int j = 0; j < cols; j++)
        if (ind < bStrings.length)
          buttonStrings[i][j] = bStrings[(ind++)];
    setButtons(buttonStrings);
  }
  




  public void setButtons(String[][] buttonStrings)
  {
    super.setButtons(buttonStrings);
    jtd.labelButton.setPositionCenter();
    this.buttonStrings = buttonStrings;
    w = buttonStrings.length;
    h = buttonStrings[0].length;
    displayFlags = new boolean[w][h];
    for (int i = 0; i < w; i++)
      for (int j = 0; j < h; j++)
        displayFlags[i][j] = 0;
    setButtonBackgrounds();
  }
  
  public void setButtonBackgrounds() {
    for (int i = 0; i < w; i++)
      for (int j = 0; j < h; j++)
        if (displayFlags[i][j] != 0) {
          buttons[i][j].setBackground(onColor);
        } else
          buttons[i][j].setBackground(offColor);
  }
  
  public void textDisplayButtonCallBack(int id, String s) {
    for (int i = 0; i < w; i++)
      for (int j = 0; j < h; j++)
        if (s.equals(buttonStrings[i][j])) {
          displayFlags[i][j] = (displayFlags[i][j] == 0 ? 1 : 0);
          setButtonBackgrounds();
          fill();
          return;
        }
  }
  
  public void setDebugFound(int id) {
    hidePanel.removeAll();
    hidePanel.setLayout(new JeliGridLayout(1, 5));
    hidePanel.add(hideButton);
    cloneButton = new JeliButton("Clone", this);
    typeButton = new JeliButton("Inhibit Type", this);
    noneButton = new JeliButton("None", this);
    dividerButton = new JeliButton("Divider", this);
    hidePanel.add(cloneButton);
    hidePanel.add(typeButton);
    hidePanel.add(noneButton);
    hidePanel.add(dividerButton);
    hidePanel.invalidate();
    setTypeLabel();
    validate();
    setShowButtons(true);
  }
  
  private void setTypeLabel() {
    if (showType) {
      typeButton.setLabel("Show Type");
    } else
      typeButton.setLabel("Inhibit Type");
  }
  
  public void jeliButtonPushed(JeliButton jb) {
    if (jb == cloneButton) {
      makeCopy();
    } else if (jb == typeButton) {
      showType = (!showType);
      setTypeLabel();
      fill();
    }
    else if (jb == dividerButton) {
      lines.addElement(new NumberedLine(-1, dividerString));
      fill();
    }
    else if (jb == noneButton) {
      for (int i = 0; i < w; i++)
        for (int j = 0; j < h; j++)
          displayFlags[i][j] = 0;
      setButtonBackgrounds();
      fill();
    }
    else {
      super.jeliButtonPushed(jb);
    }
  }
  

  private synchronized void makeCopy()
  {
    Rectangle bounds = getBounds();
    JeliMessageFrame newFrame = new JeliMessageFrame();
    newFrame.setLocation(x, y + height);
    newFrame.setButtons(buttonStrings);
    newFrame.setDebugFound(0);
    for (int i = 0; i < w; i++)
      for (int j = 0; j < h; j++)
        displayFlags[i][j] = displayFlags[i][j];
    showType = showType;
    for (int i = 0; i < lines.size(); i++) {
      NumberedLine line = (NumberedLine)lines.elementAt(i);
      newFrame.addLine(n, s);
    }
    copies.addElement(newFrame);
    newFrame.setButtonBackgrounds();
    newFrame.setTypeLabel();
    newFrame.setVisible(true);
  }
  
  public void setVisible(boolean f)
  {
    super.setVisible(f);
    if (f) {
      for (int i = 0; i < copies.size(); i++) {
        ((JeliMessageFrame)copies.elementAt(i)).setVisible(true);
      }
    }
  }
  
  public void textDisplayLogCallBack(int n, String s) {
    if (logCallback != null)
      logCallback.textDisplayLogCallBack(n, s);
  }
  
  public void textDisplayLabelCallBack(int n) {}
  
  public void jeliCanvasCallback(String str) {}
  
  private class NumberedLine {
    public int n;
    public String s;
    
    public NumberedLine(int n, String s) {
      this.n = n;
      this.s = s;
    }
  }
  

  public void jeliCanvasCallback(String str, String str1)
  {
    String testString = Utility.todaysDebugNumber();
    if (testString.equals(str1)) {
      setDebugFound(0);
    }
  }
  
  public void jeliCanvasCallback(String str, int val) {}
  
  public void jeliCanvasCallback(String str, int val1, int val2) {}
}
