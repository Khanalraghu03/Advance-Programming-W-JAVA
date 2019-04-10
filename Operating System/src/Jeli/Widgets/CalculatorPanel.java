package Jeli.Widgets;

import java.awt.BorderLayout;
import java.awt.Panel;


public class CalculatorPanel
  extends Panel
  implements JeliButtonCallBack, ClipBoard
{
  private int MODE_BINARY = 0;
  private int MODE_DECIMAL = 1;
  private int MODE_HEXADECIMAL = 2;
  private JeliButton xButton;
  private JeliButton yButton;
  private JeliButton zButton;
  private JeliButton addButton;
  private JeliButton subButton;
  private JeliButton mulButton;
  private JeliButton divButton;
  private JeliButton modeButton;
  private JeliButton selectXButton;
  private JeliButton pasteXButton;
  private JeliButton shiftLeftXButton;
  private JeliButton shiftRightXButton;
  private JeliButton selectYButton;
  private JeliButton pasteYButton;
  private JeliButton shiftLeftYButton;
  private JeliButton shiftRightYButton;
  private JeliButton selectZButton;
  private JeliButton pasteZButton;
  private JeliButton shiftLeftZButton;
  private JeliButton shiftRightZButton;
  private Panel displayPanel;
  private String classification = "Calculator Panel";
  private long xval = 0L;
  private long yval = 0L;
  private long zval = 0L;
  int mode = MODE_BINARY;
  private ClipBoard cb = null;
  
  private JeliLabel cBoard;
  
  private String clipBoardString = "";
  private String name = "";
  

  public CalculatorPanel(int wid, boolean useClip)
  {
    setupLayout(wid, useClip);
    setValues();
  }
  

  public void reset(String s)
  {
    xval = 0L;
    yval = 0L;
    zval = 0L;
    setValues();
    xButton.setButtonSize("xxxxx: " + s);
    yButton.setButtonSize("xxxxx: " + s);
    zButton.setButtonSize("xxxxx: " + s);
    cBoard.setButtonSize("");
    displayPanel.invalidate();
    displayPanel.validate();
    validate();
  }
  
  public void reset(int n) {
    xval = 0L;
    yval = 0L;
    zval = 0L;
    setValues();
    xButton.setButtonSize("xxxxx: ", "0", n);
    yButton.setButtonSize("xxxxx: ", "0", n);
    zButton.setButtonSize("xxxxx: ", "0", n);
    cBoard.setButtonSize("");
    displayPanel.invalidate();
    displayPanel.validate();
    validate();
  }
  
  public void setClipBoard(ClipBoard cb) {
    this.cb = cb;
  }
  









  private void setupLayout(int wid, boolean useClip)
  {
    setLayout(new BorderLayout());
    displayPanel = new Panel();
    cBoard = new JeliLabel("Clipboard");
    if (useClip) {
      cb = this;
      cBoard.setLabel2(clipBoardString);
      add("North", cBoard);
    }
    displayPanel.setLayout(new JeliGridLayout(3, 1));
    displayPanel.add(this.xButton = new JeliButton("x:", this));
    displayPanel.add(this.yButton = new JeliButton("y:", this));
    displayPanel.add(this.zButton = new JeliButton("valz:", this));
    xButton.setButtonSize(wid);
    xButton.setAsGetString("valx: ", "valx: ", "valx: ", convertVal(xval), true);
    yButton.setAsGetString("valy: ", "valy: ", "valy: ", convertVal(yval), true);
    add("Center", displayPanel);
    Panel rightPanel = new Panel();
    rightPanel.setLayout(new JeliGridLayout(3, 1));
    Panel rightPanel1 = new Panel();
    rightPanel1.setLayout(new JeliGridLayout(1, 2));
    Panel rightPanel2 = new Panel();
    rightPanel2.setLayout(new JeliGridLayout(1, 2));
    rightPanel1.add(this.addButton = new JeliButton("x+y", this));
    rightPanel1.add(this.subButton = new JeliButton("x-y", this));
    rightPanel2.add(this.mulButton = new JeliButton("x*y", this));
    rightPanel2.add(this.divButton = new JeliButton("x/y", this));
    rightPanel.add(rightPanel1);
    rightPanel.add(rightPanel2);
    rightPanel.add(this.modeButton = new JeliButton("Base:", this));
    add("East", rightPanel);
    Panel leftPanel = new Panel();
    leftPanel.setLayout(new JeliGridLayout(3, 1));
    Panel leftPanel1 = new Panel();
    leftPanel1.setLayout(new JeliGridLayout(1, 3));
    leftPanel1.add(this.selectXButton = new JeliButton("Select", this));
    leftPanel1.add(this.pasteXButton = new JeliButton("Paste", this));
    Panel leftPanel1S = new Panel();
    leftPanel1S.setLayout(new JeliGridLayout(1, 2));
    leftPanel1S.add(this.shiftLeftXButton = new JeliButton("<<", this));
    leftPanel1S.add(this.shiftRightXButton = new JeliButton(">>", this));
    leftPanel1.add(leftPanel1S);
    selectXButton.setPositionCenter();
    pasteXButton.setPositionCenter();
    Panel leftPanel2 = new Panel();
    leftPanel2.setLayout(new JeliGridLayout(1, 3));
    leftPanel2.add(this.selectYButton = new JeliButton("Select", this));
    leftPanel2.add(this.pasteYButton = new JeliButton("Paste", this));
    Panel leftPanel2S = new Panel();
    leftPanel2S.setLayout(new JeliGridLayout(1, 2));
    leftPanel2S.add(this.shiftLeftYButton = new JeliButton("<<", this));
    leftPanel2S.add(this.shiftRightYButton = new JeliButton(">>", this));
    leftPanel2.add(leftPanel2S);
    selectYButton.setPositionCenter();
    pasteYButton.setPositionCenter();
    selectZButton = new JeliButton("Select", this);
    pasteZButton = new JeliButton("Paste", this);
    selectZButton.setPositionCenter();
    pasteZButton.setPositionCenter();
    Panel leftPanel3 = new Panel();
    leftPanel3.setLayout(new JeliGridLayout(1, 3));
    leftPanel3.add(this.selectZButton = new JeliButton("Select", this));
    leftPanel3.add(this.pasteZButton = new JeliButton("Paste", this));
    Panel leftPanel3S = new Panel();
    leftPanel3S.setLayout(new JeliGridLayout(1, 2));
    leftPanel3S.add(this.shiftLeftZButton = new JeliButton("<<", this));
    leftPanel3S.add(this.shiftRightZButton = new JeliButton(">>", this));
    leftPanel3.add(leftPanel3S);
    leftPanel.add(leftPanel1);
    leftPanel.add(leftPanel2);
    leftPanel.add(leftPanel3);
    

    add("West", leftPanel);
    setModeButton();
  }
  
  private void setValues()
  {
    xButton.setAsGetString("valx: ", "valx: ", "valx: ", convertVal(xval), true);
    xButton.setLabel("valx: ");
    xButton.setLabel2(convertVal(xval));
    yButton.setAsGetString("valy: ", "valy: ", "valy: ", convertVal(yval), true);
    yButton.setLabel("valy: ");
    yButton.setLabel2(convertVal(yval));
    zButton.setLabel2(convertVal(zval));
  }
  
  private void setModeButton() {
    if (mode == MODE_BINARY)
      modeButton.setLabel2("2");
    if (mode == MODE_DECIMAL)
      modeButton.setLabel2("10");
    if (mode == MODE_HEXADECIMAL)
      modeButton.setLabel2("16");
  }
  
  private String convertVal(long n) {
    if (mode == MODE_BINARY)
      return convertVal(n, 2);
    if (mode == MODE_DECIMAL)
      return convertVal(n, 10);
    if (mode == MODE_HEXADECIMAL)
      return convertVal(n, 16);
    return "";
  }
  
  private int getBase() {
    if (mode == MODE_BINARY)
      return 2;
    if (mode == MODE_HEXADECIMAL)
      return 16;
    return 10;
  }
  






  private String convertVal(long n, int base)
  {
    return Long.toString(n, base);
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == modeButton) {
      if (mode == MODE_BINARY) {
        mode = MODE_DECIMAL;
      } else if (mode == MODE_DECIMAL) {
        mode = MODE_HEXADECIMAL;
      } else if (mode == MODE_HEXADECIMAL)
        mode = MODE_BINARY;
      setModeButton();
      setValues();
      return;
    }
    if (bh == addButton) {
      zval = (xval + yval);
      setValues();
      return;
    }
    if (bh == subButton) {
      zval = (xval - yval);
      if (zval < 0L)
        zval = 0L;
      setValues();
      return;
    }
    if (bh == mulButton) {
      zval = (xval * yval);
      setValues();
      return;
    }
    if (bh == divButton) {
      if (yval == 0L)
        return;
      zval = (xval / yval);
      setValues();
      return;
    }
    if (bh == shiftLeftXButton) {
      xval *= 2L;
      setValues();
      return;
    }
    if (bh == shiftRightXButton) {
      xval /= 2L;
      setValues();
      return;
    }
    if (bh == shiftLeftYButton) {
      yval *= 2L;
      setValues();
      return;
    }
    if (bh == shiftRightYButton) {
      yval /= 2L;
      setValues();
      return;
    }
    if (bh == shiftLeftZButton) {
      zval *= 2L;
      setValues();
      return;
    }
    if (bh == shiftRightZButton) {
      zval /= 2L;
      setValues();
      return;
    }
    if (bh == selectXButton) {
      if (cb != null)
        cb.setClipBoardString(Long.toString(xval, getBase()));
      return;
    }
    if (bh == selectYButton) {
      if (cb != null)
        cb.setClipBoardString(Long.toString(yval, getBase()));
      return;
    }
    if (bh == selectZButton) {
      if (cb != null)
        cb.setClipBoardString(Long.toString(zval, getBase()));
      return;
    }
    if (bh == pasteXButton) {
      if (cb != null) {
        try {
          xval = Long.parseLong(cb.getClipBoardString(), getBase());
        } catch (NumberFormatException e) {}
        setValues();
      }
      return;
    }
    if (bh == pasteYButton) {
      if (cb != null) {
        try {
          yval = Long.parseLong(cb.getClipBoardString(), getBase());
        } catch (NumberFormatException e) {}
        setValues();
      }
      return;
    }
    if (bh == pasteZButton) {
      if (cb != null) {
        try {
          zval = Long.parseLong(cb.getClipBoardString(), getBase());
        } catch (NumberFormatException e) {}
        setValues();
      }
      return;
    }
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {
    if (bh == xButton) {
      try {
        xval = Long.parseLong(str, getBase());
      }
      catch (NumberFormatException e) {}
      setValues();
    }
    if (bh == yButton) {
      try {
        yval = Long.parseLong(str, getBase());
      }
      catch (NumberFormatException e) {}
      setValues();
    } }
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() { return classification; }
  
  public void setClassification(String str) {
    classification = str;
  }
  
  public void setClipBoardString(String s) {
    clipBoardString = s;
    cBoard.setLabel2(clipBoardString);
  }
  
  public String getClipBoardString() {
    return clipBoardString;
  }
  
  public void setName(String s) {
    name = s;
  }
}
