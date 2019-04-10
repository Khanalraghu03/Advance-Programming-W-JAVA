package Jeli.Probability;

import Jeli.Get.DistributionCallBack;
import Jeli.Widgets.HelpManager;
import Jeli.Widgets.JeliButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

public class ButtonDistribution extends java.awt.Panel implements Jeli.Widgets.JeliButtonCallBack
{
  DistributionCallBack callback;
  Distribution dist;
  JeliButton DistributionLabelButton;
  JeliButton DistributionTypeButton;
  JeliButton HighButton;
  JeliButton MeanButton;
  double mean;
  double low;
  double high;
  int id;
  HelpManager hm;
  boolean LowFlag;
  boolean HighFlag;
  boolean MeanFlag;
  String label;
  String classification = null;
  

  public ButtonDistribution(String label, int id, HelpManager hm, DistributionCallBack callback)
  {
    this.label = label;
    this.callback = callback;
    this.id = id;
    this.hm = hm;
    dist = new Distribution(1, 0.0D);
    setup_layout();
    validate();
  }
  
  public void setDistribution(Distribution d)
  {
    dist = d;
    reset_layout();
  }
  
  public Dimension getPreferredSize()
  {
    Dimension psize = super.getPreferredSize();
    
    if (width > 400) return psize;
    return new Dimension(400, height);
  }
  
  public void setup_layout() {
    setLayout(new Jeli.Widgets.JeliGridLayout(1, 4));
    DistributionLabelButton = new JeliButton(label, hm, this);
    DistributionLabelButton.setBorderType(7);
    add(DistributionLabelButton);
    DistributionTypeButton = new JeliButton("", hm, this);
    DistributionTypeButton.setBorderType(3);
    add(DistributionTypeButton);
    MeanButton = new JeliButton("uvw", hm, this);
    MeanButton.setAsGetDouble("Mean", "Enter Mean: ", "Mean: ", 0.0D, true);
    MeanButton.setBorderType(3);
    add(MeanButton);
    HighButton = new JeliButton("", hm, this);
    HighButton.setAsGetDouble("High", "H: ", "High: ", 0.0D, true);
    HighButton.setBorderType(11);
    add(HighButton);
    reset_layout();
  }
  
  public void reset_layout()
  {
    int type = dist.getType();
    if (type == 1) {
      DistributionTypeButton.setLabel("Constant");
      MeanButton.setAsGetDouble("Mean", "Enter Mean: ", "Mean: ", dist.getContMean(), true);
      
      HighButton.setAsGetDouble("", "", "", 0.0D, false);
      HighButton.disableJeliButton();
    }
    else if (type == 5) {
      DistributionTypeButton.setLabel("Exponential");
      MeanButton.setAsGetDouble("Mean", "Enter Mean: ", "Mean: ", dist.getContMean(), true);
      
      HighButton.setAsGetDouble("", "", "", 0.0D, false);
      HighButton.disableJeliButton();
    }
    else if (type == 3) {
      DistributionTypeButton.setLabel("Uniform");
      HighButton.setAsGetDouble("High", "H: ", "High: ", dist.getContHigh(), true);
      
      HighButton.enableJeliButton();
      MeanButton.setAsGetDouble("Low", "L: ", "Low: ", dist.getContLow(), true);
    }
  }
  









  public int getWidthAdjust()
  {
    int s1 = getBoundswidth;
    if (s1 == 0) return -1;
    int s2 = MeanButton.getBounds().width;
    s1 -= 4 * s2;
    if ((s1 < 0) || (s1 > 3)) {
      System.out.println("Adjust value is " + s1);
      return -1;
    }
    return s1;
  }
  
  public void setBackground(Color c)
  {
    MeanButton.setBackground(c);
    HighButton.setBackground(c);
    DistributionTypeButton.setBackground(c);
    DistributionLabelButton.setBackground(c);
  }
  
  public void jeliButtonPushed(JeliButton bh)
  {
    int type = dist.getType();
    if (bh == DistributionTypeButton) {
      if (type == 1) {
        dist = new Distribution(3, low, high);
      }
      else if (type == 3) {
        dist = new Distribution(5, mean);
      } else if (type == 5) {
        dist = new Distribution(1, mean);
      }
      reset_layout();
      callback.setDistribution(id, dist);
    }
    if (bh == DistributionLabelButton) {
      callback.setDistribution(id, dist);
    }
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val)
  {
    int type = dist.getType();
    if (bh == MeanButton) {
      if (type == 3) {
        low = val;
        dist.setLow(val);
        callback.setDistribution(id, dist);
        return;
      }
      mean = val;
      dist.setMean(val);
      callback.setDistribution(id, dist);
      return;
    }
    if (bh == HighButton) {
      high = val;
      dist.setHigh(val);
      callback.setDistribution(id, dist);
      return;
    }
  }
  
  public String getClassification() {
    return classification;
  }
  
  public void setClassification(String str) {
    classification = str;
  }
}
