package Jeli.Widgets;

import Jeli.Utility;
import java.awt.Component;
import java.awt.Point;
import java.util.StringTokenizer;

public class SelfDestructingShortInfoFrame extends JeliFrame implements Runnable
{
  private JeliButton msg1;
  private JeliButton[] buttonList;
  private ObjectCallBack cb = null;
  int numSec;
  int secToGo;
  private String buttonSizeString = " Auto hide in 1000 seconds unless you click here ";
  

  public SelfDestructingShortInfoFrame(String label, String str, int numSec, Component com)
  {
    this(label, str, numSec, com, null, null);
  }
  


  public SelfDestructingShortInfoFrame(String label, String str, int numSec, Component com, Component com1, JeliButton topButton)
  {
    super(label);
    this.numSec = numSec;
    if (com1 == null)
      com1 = com;
    secToGo = numSec;
    
    String[] lines = stringToArray(str);
    int numLines = lines.length;
    buttonList = new JeliButton[numLines];
    if (topButton != null) {
      setLayout(new JeliGridLayout(numLines + 2, 1));
      add(topButton);
      topButton.setBorderY(0);
      topButton.setButtonHeight(1);
      topButton.setBorderType(2);
    }
    else {
      setLayout(new JeliGridLayout(numLines + 1, 1)); }
    for (int i = 0; i < numLines; i++) {
      buttonList[i] = new JeliButton(lines[i], this);
      add(buttonList[i]);
      buttonList[i].setBorderY(0);
      buttonList[i].setButtonHeight(1);
      buttonList[i].setBorderType(0);
      buttonList[i].setAsLabel();
    }
    add(this.msg1 = new JeliButton(buttonSizeString, this));
    msg1.setBorderY(0);
    msg1.setButtonHeight(1);
    msg1.setPositionCenter();
    msg1.setInhibitReverse(true);
    
    pack();
    if (secToGo < 0) {
      msg1.setLabel("Hide");
    } else
      msg1.setLabel("Auto hide in " + secToGo + " seconds");
    if (com != null) {
      Point pos = Utility.getAbsolutePosition(com);
      java.awt.Dimension dim = Utility.getScreenSize();
      if (x + getBoundswidth + 100 < width) {
        setLocation(x + getBoundswidth, y);
      } else {
        pos = Utility.getAbsolutePosition(com1);
        setLocation(x - getBoundswidth, y);
      }
    }
    setVisible(true);
    if (secToGo > 0)
      new Thread(this).start();
  }
  
  public void run() {
    while (secToGo > 0) {
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {}
      secToGo -= 1;
      setToGoLabel();
    }
    if (secToGo == 0)
      setVisible(false);
  }
  
  public int getTimeout() {
    return secToGo;
  }
  
  public void setCallBack(ObjectCallBack cb) {
    this.cb = cb;
    if (cb != null)
      for (int i = 0; i < buttonList.length; i++)
        buttonList[i].setAsNotLabel();
  }
  
  private void setToGoLabel() {
    if (secToGo > 1) {
      msg1.setLabel("Auto hide in " + secToGo + " seconds unless you click here");
    } else if (secToGo == 1) {
      msg1.setLabel("Auto hide in 1 second unless you click here");
    }
  }
  



  private String[] stringToArray(String s)
  {
    StringTokenizer strtok = new StringTokenizer(s, "\n");
    int count = strtok.countTokens();
    String[] list = new String[count];
    for (int i = 0; i < count; i++)
      list[i] = strtok.nextToken();
    return list;
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh.getLabel().equals("Hide")) {
      setVisible(false);
      return;
    }
    if (bh == msg1) {
      secToGo = -1;
      msg1.setLabel("Hide");
      return;
    }
    for (int i = 0; i < buttonList.length; i++) {
      if ((bh == buttonList[i]) && 
        (cb != null))
        cb.objectNotify(i, this);
    }
  }
  
  public void abort() {
    secToGo = 0;
    setVisible(false);
  }
  
  public void restart() {
    secToGo = numSec;
    setToGoLabel();
    setVisible(true);
    new Thread(this).start();
  }
}
