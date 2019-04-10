package Jeli.Logging;

import Jeli.Widgets.HelpManager;
import Jeli.Widgets.JeliButton;
import Jeli.Widgets.SliderColor;
import Jeli.Widgets.SliderFloat;
import Jeli.Widgets.SliderInteger;
import java.awt.Color;

public class GanttControls extends Jeli.Widgets.JeliFrame implements Jeli.Widgets.SliderCallBack, Jeli.Widgets.ColorSliderCallBack
{
  private final int MS_ID = 1;
  private final int BH_ID = 2;
  private final int BI_ID = 3;
  private final int BL_ID = 4;
  private final int TEXT_COLOR_ID = 10;
  private final int BACK_COLOR_ID = 11;
  private final int GRID_TEXT_COLOR_ID = 12;
  private final int GRID_SKIP_COLOR_ID = 13;
  private GanttCanvas can;
  private GanttChart chart;
  private JeliButton hideButton;
  private JeliButton defaultsButton;
  private String classification = "Gantt Controls";
  
  private HelpManager hm;
  private java.awt.Component com;
  private SliderFloat millisecondsPerPixel;
  private SliderInteger barHeight;
  private SliderInteger barInc;
  private SliderInteger keyBarLength;
  private SliderInteger gridIncrement;
  private SliderInteger labelGridSkip;
  private SliderColor colorSlider;
  
  public GanttControls(String name, GanttCanvas can, GanttChart chart, HelpManager hm, java.awt.Component com)
  {
    super(name);
    this.can = can;
    this.chart = chart;
    this.hm = hm;
    this.com = com;
    setupLayout();
    pack();
  }
  
  private void setupLayout()
  {
    java.awt.Panel buttonsPanel = new java.awt.Panel();
    setLayout(new Jeli.Widgets.JeliGridLayout(9, 1));
    buttonsPanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
    makeslider();
    buttonsPanel.add(this.hideButton = new JeliButton("Hide", hm, this));
    buttonsPanel.add(this.defaultsButton = new JeliButton("Set Defaults", hm, this));
    add(millisecondsPerPixel);
    add(barHeight);
    add(barInc);
    add(keyBarLength);
    add(gridIncrement);
    add(labelGridSkip);
    add(buttonsPanel);
    Color textColor = can.getTextColor();
    colorSlider = new SliderColor(10, textColor.getRed(), textColor.getGreen(), textColor.getBlue(), 10, "Text Color", this, hm);
    

    colorSlider.addSlider(11, "Back Color", can.getBackColor());
    colorSlider.addSlider(12, "Grid Text Color", can.getGridTextColor());
    
    colorSlider.addSlider(13, "Grid Skip Color", can.getGridSkipColor());
    
    colorSlider.fixSliders();
    add(colorSlider.getInfoPanel());
    add(colorSlider.getSliderPanel());
  }
  





  private void makeslider()
  {
    double ms = can.get_ms();
    int fh = can.get_frame_height();
    int fi = can.get_frame_increment();
    int kbl = can.getKeyBarLength();
    int gi = can.getForceGridIncrement();
    if (gi < 0)
      gi = 0;
    int gim = 2 * gi;
    if (gim == 0)
      gim = 100;
    millisecondsPerPixel = new SliderFloat(1, (int)(1000.0D * ms), 1, 100000, 1000, 100, "time per pixel: ", 0, true, this, hm);
    

    millisecondsPerPixel.setAsLabel();
    millisecondsPerPixel.centerButton();
    millisecondsPerPixel.setBackground(hm.getDefaultBackground());
    barHeight = new SliderInteger(2, fh, 1, 20, 100, "bar height: ", 0, true, this, hm);
    
    barHeight.setAsLabel();
    barHeight.centerButton();
    barHeight.setBackground(hm.getDefaultBackground());
    barInc = new SliderInteger(3, fi, 0, 5, 100, "bar spacing: ", 0, true, this, hm);
    
    barInc.setAsLabel();
    barInc.centerButton();
    barInc.setBackground(hm.getDefaultBackground());
    keyBarLength = new SliderInteger(4, kbl, 0, 50, 100, "key bar length: ", 0, true, this, hm);
    
    keyBarLength.setAsLabel();
    keyBarLength.centerButton();
    keyBarLength.setBackground(hm.getDefaultBackground());
    gridIncrement = new SliderInteger(4, gi, 0, gim, 100, "grid increment: ", 0, true, this, hm);
    
    gridIncrement.setAsLabel();
    gridIncrement.centerButton();
    gridIncrement.setBackground(hm.getDefaultBackground());
    labelGridSkip = new SliderInteger(4, 1, 1, 10, 100, "label grid skip: ", 0, true, this, hm);
    
    labelGridSkip.setAsLabel();
    labelGridSkip.centerButton();
    labelGridSkip.setBackground(hm.getDefaultBackground());
  }
  

  public void showIt()
  {
    if (com != null) {
      java.awt.Point pos = Jeli.Utility.getAbsolutePosition(com);
      setLocation(x, y);
    }
    setVisible(true);
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == hideButton) {
      setVisible(false);
      return;
    }
    if (bh == defaultsButton) {
      can.setDefaultsFromCurrent();
      return;
    } }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public void setMS(double val) { millisecondsPerPixel.set_value(val); }
  
  public void setBH(int val)
  {
    barHeight.set_value(val);
  }
  
  public void setBI(int val) {
    barInc.set_value(val);
  }
  
  public void setMax(double max) {
    millisecondsPerPixel.setMax(max);
  }
  
  public double getMS() {
    return millisecondsPerPixel.valueDouble();
  }
  
  public int getBH() {
    return barHeight.value();
  }
  
  public int getBI() {
    return barInc.value();
  }
  
  public int getGI() {
    return gridIncrement.value();
  }
  
  public int getLGS() {
    return labelGridSkip.value();
  }
  
  public int getKBL() {
    return keyBarLength.value();
  }
  






  public void sliderchanged(int id, double val)
  {
    chart.change_parameters();
  }
  
  public void sliderchanged(int id) {}
  
  public String getClassification() {
    return classification;
  }
  
  public void setClassification(String str) {
    classification = str;
  }
  
  public void colorSliderCallBack(int id, int red, int green, int blue)
  {
    Color c = new Color(red, green, blue);
    if (id == 10) {
      can.setTextColor(c);
    } else if (id == 11) {
      can.setBackColor(c);
    } else if (id == 12) {
      can.setGridTextColor(c);
    } else if (id == 13) {
      can.setGridSkipColor(c);
    }
  }
}
