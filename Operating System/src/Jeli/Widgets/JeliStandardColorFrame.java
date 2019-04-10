package Jeli.Widgets;

import Jeli.Utility;
import java.awt.Color;
import java.awt.Panel;
import java.io.PrintStream;

public class JeliStandardColorFrame extends JeliFrame implements SliderCallBack, ColorSliderCallBack
{
  private JeliButton helpButton;
  private JeliButton[][] b;
  private JeliButton currentButton = null;
  private int currentType;
  private int currentDegree;
  private SliderStandardColor slc;
  private JeliButton hideButton;
  private JeliButton changeButton;
  private SliderMultiple ms;
  private boolean initialized = false;
  private boolean showSLC = true;
  private Panel sliderPanel;
  
  public JeliStandardColorFrame()
  {
    super("Jeli Standard Colors version L288");
    






    Panel centerPanel = new Panel();
    Panel bottomPanel = new Panel();
    Panel lowerPanel = new Panel();
    sliderPanel = new Panel();
    setLayout(new java.awt.BorderLayout());
    bottomPanel.setLayout(new JeliGridLayout(2, 1));
    centerPanel.setLayout(new JeliGridLayout(7, 4));
    lowerPanel.setLayout(new JeliGridLayout(1, 3));
    slc = new SliderStandardColor(1, 5, 34, 10, "Test", this, Utility.hm);
    sliderPanel.setLayout(new java.awt.GridLayout(1, 1));
    sliderPanel.add(slc.getLongPanel());
    bottomPanel.add(sliderPanel);
    slc.setBackground(Color.white);
    lowerPanel.add(this.helpButton = new JeliButton("Help", this));
    lowerPanel.add(this.changeButton = new JeliButton("Change One", this));
    lowerPanel.add(this.hideButton = new JeliButton("Hide", this));
    helpButton.setBackground(Color.white);
    hideButton.setBackground(Color.white);
    bottomPanel.add(lowerPanel);
    add("Center", centerPanel);
    add("South", bottomPanel);
    Utility.setupStandardColorsIfNecessary();
    b = new JeliButton[7][4];
    for (int i = 0; i < 7; i++)
      for (int j = 0; j < 4; j++) {
        b[i][j] = new JeliButton("" + i + " " + j + " " + Utility.colorDegrees[j], this);
        Utility.setStandardColor(b[i][j], i, j);
        centerPanel.add(b[i][j]);
        Color c = b[i][j].getBackground();
        
        if ((c.getRed() < 50) && (c.getGreen() < 50) && (c.getBlue() < 50))
          b[i][j].setForeground(Color.white);
      }
    currentButton = b[0][0];
    currentType = 0;
    currentDegree = 0;
    currentButton.increaseFontSize();
    resetAllLabels();
    int val = Utility.getValueFromColor(b[0][0].getBackground(), currentType);
    slc.setValue(val);
    slc.resetType(currentType);
    slc.setDegreeLabel(currentDegree);
    slc.resetColor();
    
    pack();
    setVisible(true);
    ms = new SliderMultiple(1, 0, 255, 4, Color.yellow, Color.red, 0.05D, 3, 100);
    ms.setCallback(this);
    ms.setDisplayValueFlag(true);
    for (int i = 0; i < 4; i++)
      ms.initializeValue(i, Utility.colorDegrees[i]);
    initialized = true;
  }
  

  private void resetAllLabels()
  {
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 4; j++) {
        Color c = b[i][j].getBackground();
        b[i][j].setLabel(slc.getDegreeName(j) + " " + slc.getTypeName(i) + ":" + c.getRed() + " " + c.getGreen() + " " + c.getBlue());
      }
    }
  }
  
  public void colorSliderCallBack(int id, int red, int green, int blue)
  {
    if (id == 2) {
      Utility.resetStandardColors();
      return;
    }
    

    Utility.setStandardColor(currentType, currentDegree, red, green, blue);
    Utility.resetStandardComponentColors(currentType, currentDegree);
    Utility.resetStandardComponentColors();
    resetAllLabels();
  }
  
  public void jeliButtonPushed(JeliButton bh)
  {
    if (bh == helpButton) {
      Utility.hm.showHelpManager();
    }
    if (bh == hideButton) {
      setVisible(false);
    }
    if (bh == changeButton) {
      System.out.println("Change button pushed");
      changeIt();
    }
    for (int i = 0; i < 7; i++) {
      for (int j = 0; j < 4; j++) {
        if (bh == b[i][j]) {
          currentButton.decreaseFontSize();
          currentButton = bh;
          currentButton.increaseFontSize();
          currentType = i;
          currentDegree = j;
          int val = Utility.getValueFromColor(bh.getBackground(), i);
          slc.setValue(val);
          slc.resetType(i);
          slc.setDegreeLabel(j);
          slc.resetColor();
          return;
        }
      }
    }
  }
  
  public void sliderchanged(int id, double val) {}
  
  public void sliderchanged(int id) {
    if (!initialized) return;
    Utility.setColorDegrees(ms.getValue(0), ms.getValue(1), ms.getValue(2), ms.getValue(3));
    
    Utility.resetStandardColors();
    resetAllLabels();
  }
  
  private void changeIt() {
    sliderPanel.removeAll();
    if (showSLC) {
      showSLC = false;
      System.out.println("Using ms");
      changeButton.setLabel("Change All");
      sliderPanel.add(ms);
    }
    else {
      showSLC = true;
      System.out.println("Using slc");
      changeButton.setLabel("Change One");
      sliderPanel.add(slc.getLongPanel());
    }
    sliderPanel.invalidate();
    sliderPanel.validate();
  }
}
