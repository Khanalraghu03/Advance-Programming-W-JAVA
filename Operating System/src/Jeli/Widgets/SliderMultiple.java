package Jeli.Widgets;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Panel;

public class SliderMultiple extends Panel implements VoidCallBack
{
  private int id;
  private int min;
  private int max;
  private int num;
  private Color backColor;
  private Color foreColor;
  private double barWidthFract;
  private int barWidthMin;
  private int barWidthMax;
  private SliderMultipleCanvas can;
  private JeliLabel[] labels;
  private SliderCallBack cb = null;
  


  public SliderMultiple(int id, int min, int max, int num, Color backColor, Color foreColor, double barWidthFract, int barWidthMin, int barWidthMax)
  {
    this.id = id;
    this.min = min;
    this.max = max;
    this.num = num;
    this.backColor = backColor;
    this.foreColor = foreColor;
    this.barWidthFract = barWidthFract;
    this.barWidthMin = barWidthMin;
    this.barWidthMax = barWidthMax;
    setLayout(new GridLayout(1, 1));
    labels = new JeliLabel[num];
    for (int i = 0; i < num; i++) {
      labels[i] = new JeliLabel("");
      labels[i].setBackground(Color.white);
    }
    can = new SliderMultipleCanvas(min, max, num, backColor, foreColor, barWidthFract, barWidthMin, barWidthMax, this);
    
    add(can);
    setLabels();
  }
  
  public void setLabels() {
    for (int i = 0; i < num; i++) {
      labels[i].setLabel("" + can.getValue(i));
    }
  }
  
  public void voidNotify(int which) {
    int val = can.getValue(which);
    labels[which].setLabel("" + val);
    if (cb != null)
      cb.sliderchanged(id);
  }
  
  public void setCallback(SliderCallBack cb) {
    this.cb = cb;
  }
  
  public Panel getValuesPanel()
  {
    Panel p = new Panel();
    p.setLayout(new JeliGridLayout(1, num));
    for (int i = 0; i < num; i++)
      p.add(labels[i]);
    return p;
  }
  
  public void setValue(int which, int val) {
    can.setValue(which, val);
  }
  
  public void initializeValue(int which, int val) {
    can.initializeValue(which, val);
  }
  
  public int getValue(int i) {
    return can.getValue(i);
  }
  
  public void setDisplayValueFlag(boolean f) {
    can.setDisplayValueFlag(f);
  }
}
