package Jeli.Widgets;

import java.awt.Rectangle;

public class PhantomDialog extends java.awt.Dialog
{
  Rectangle bounds;
  
  public PhantomDialog(java.awt.Frame f, java.awt.Component com)
  {
    super(f);
    try {
      setUndecorated(true);
    }
    catch (NoSuchMethodError e) {}
    bounds = com.getBounds();
    
    setSize(bounds.width, bounds.height);
    setLocation(bounds.x, bounds.y);
  }
}
