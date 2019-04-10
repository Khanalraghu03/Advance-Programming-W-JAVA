package Jeli.Plots;

import java.awt.Image;

public abstract interface Plots
{
  public abstract void setVisible(boolean paramBoolean);
  
  public abstract Image getImage();
  
  public abstract void addPlot(double[] paramArrayOfDouble, String paramString);
}
