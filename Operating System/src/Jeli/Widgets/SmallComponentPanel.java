package Jeli.Widgets;

import java.awt.Component;

public class SmallComponentPanel extends java.awt.Panel
{
  Component relativeComponent = null;
  double relativeSize = 1.0D;
  
  public SmallComponentPanel(Component com)
  {
    setLayout(new java.awt.GridLayout(1, 1));
    add(com);
  }
  
  public void setRelativeComponent(Component com) {
    relativeComponent = com;
  }
  
  public void setRelativeSize(double val) {
    relativeSize = val;
  }
  

  public java.awt.Dimension getPreferredSize()
  {
    if (relativeComponent != null) {
      java.awt.Dimension dem = relativeComponent.getPreferredSize();
      width = ((int)(width * relativeSize + 0.5D));
      return dem;
    }
    return new java.awt.Dimension(1, 1);
  }
}
