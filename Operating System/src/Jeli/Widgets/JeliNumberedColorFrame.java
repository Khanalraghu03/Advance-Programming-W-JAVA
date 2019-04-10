package Jeli.Widgets;

import Jeli.Utility;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Panel;

public class JeliNumberedColorFrame extends JeliFrame implements ColorSliderCallBack
{
  private JeliButton helpButton;
  private SliderColor[] colorSliders;
  private JeliButton hideButton;
  private JeliButton allDarkerButton;
  private JeliButton allLighterButton;
  private JeliButton allDefaultButton;
  private JeliButton allSetButton;
  private boolean exitFlag = false;
  
  public JeliNumberedColorFrame() {
    super("Jeli Colors version L288");
    setupLayout1();
    pack();
    setVisible(true);
  }
  





  private void setupLayout1()
  {
    Panel[] columnPanels = new Panel[9];
    int size = Utility.hm.getColorListSize();
    Panel lowerPanel = new Panel();
    setLayout(new java.awt.BorderLayout());
    lowerPanel.setLayout(new JeliGridLayout(1, 2));
    lowerPanel.add(this.helpButton = new JeliButton("Help", this));
    hideButton = new JeliButton("Hide", this);
    helpButton.setBackground(Color.white);
    hideButton.setBackground(Color.white);
    hideButton.setBackground(HelpManager.COLOR_NUMBER_LIGHT_YELLOW);
    allLighterButton = new JeliButton("All Lighter", this);
    allDarkerButton = new JeliButton("All Darker", this);
    allDefaultButton = new JeliButton("All Default", this);
    allSetButton = new JeliButton("All Set", this);
    lowerPanel.add(allSetButton);
    lowerPanel.add(allDefaultButton);
    lowerPanel.add(allLighterButton);
    lowerPanel.add(allDarkerButton);
    lowerPanel.add(hideButton, this);
    Panel mainPanel = new Panel();
    add("Center", mainPanel);
    add("South", lowerPanel);
    
    java.awt.GridBagLayout gridBag = new java.awt.GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    weightx = 1.0D;
    weighty = 1.0D;
    gridy = 0;
    fill = 1;
    mainPanel.setLayout(gridBag);
    
    colorSliders = new SliderColor[size];
    for (int i = 0; i < size; i++) {
      Color color = Utility.hm.getColor(i);
      colorSliders[i] = new SliderColor(i, color.getRed(), color.getGreen(), color.getBlue(), 150, HelpManager.colorListNames[i], this, Utility.hm);
      


      colorSliders[i].setDefaultColor(Utility.hm.getDefaultColor(i));
      columnPanels[0] = colorSliders[i].getSliderName();
      columnPanels[1] = colorSliders[i].getDefaultButton();
      columnPanels[2] = colorSliders[i].getLighterButton();
      columnPanels[3] = colorSliders[i].getDarkerButton();
      columnPanels[4] = colorSliders[i].getSetButton();
      columnPanels[5] = colorSliders[i].getResetButton();
      columnPanels[6] = colorSliders[i].getOldColorLabel();
      columnPanels[7] = colorSliders[i].getNewColorLabel();
      columnPanels[8] = colorSliders[i].getSliderPanel();
      for (int j = 0; j < 9; j++) {
        gridx = j;
        if (j == 8) {
          weightx = 1.0D;
        } else
          weightx = 0.0D;
        gridBag.setConstraints(columnPanels[j], c);
        mainPanel.add(columnPanels[j]);
      }
      gridy += 1;
    }
  }
  













































  public void colorSliderCallBack(int id, int red, int green, int blue)
  {
    Utility.hm.setColor(id, red, green, blue);
    Utility.refreshAll();
    hideButton.refresh();
    helpButton.refresh();
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == helpButton) {
      Utility.hm.showHelpManager();
    } else if (bh == hideButton) {
      setVisible(false);
      if (exitFlag) {
        System.exit(0);
      }
    } else if (bh == allDefaultButton) {
      setAllDefault();
    } else if (bh == allLighterButton) {
      setAllLighter();
    } else if (bh == allDarkerButton) {
      setAllDarker();
    } else if (bh == allSetButton) {
      setAllSet();
    }
  }
  
  private void setAllSet() { for (int i = 0; i < colorSliders.length; i++)
      colorSliders[i].setColors();
  }
  
  private void setAllDefault() {
    for (int i = 0; i < colorSliders.length; i++)
      colorSliders[i].resetToDefault();
  }
  
  private void setAllLighter() {
    for (int i = 0; i < colorSliders.length; i++)
      colorSliders[i].makeLighter();
  }
  
  private void setAllDarker() {
    for (int i = 0; i < colorSliders.length; i++)
      colorSliders[i].makeDarker();
  }
  
  public void windowClosing(java.awt.event.WindowEvent e) {
    super.windowClosing(e);
    if (exitFlag)
      System.exit(0);
  }
  
  public void setExitFlag(boolean f) {
    exitFlag = f;
  }
  
  public static void main(String[] args) {
    Utility.standardHelpManager("Help Manager for Color Frame");
    new JeliNumberedColorFrame().setExitFlag(true);
  }
}
