package Jeli.Widgets;

import java.awt.Point;
import java.awt.Rectangle;



public class JeliDraggable
{
  private static int snapValue = 5;
  
  protected int relativeX;
  
  protected int relativeY;
  
  protected int minX;
  
  protected int minY;
  
  protected int w;
  
  protected int h;
  protected Point standardPosition = new Point(0, 0);
  protected boolean useStandardPosition = true;
  
  private int dragX;
  
  private int dragY;
  protected Rectangle boundary = null;
  
  public JeliDraggable() {}
  
  public Point getPosition() { if (useStandardPosition)
      return new Point(-1, relativeY);
    return new Point(relativeX, relativeY);
  }
  
  public int getActualX() {
    if (useStandardPosition)
      return standardPosition.x;
    return relativeX;
  }
  
  public int getActualY() {
    if (useStandardPosition)
      return standardPosition.y;
    return relativeY;
  }
  
  public void setPosition(Point p) {
    relativeX = x;
    relativeY = y;
    useStandardPosition = (x < 0);
  }
  
  public void setBoundary(Rectangle rect) {
    boundary = rect;
  }
  
  public void setStandardPosition(Point p) {
    standardPosition.x = x;
    standardPosition.y = y;
  }
  
  public void setUseStandardPosition(boolean f) {
    useStandardPosition = f;
  }
  
  public int getW() {
    return w;
  }
  
  public int getH() {
    return h;
  }
  

  protected boolean nearStandardPosition()
  {
    if (Math.abs(relativeX - standardPosition.x) > snapValue)
      return false;
    if (Math.abs(relativeY - standardPosition.y) > snapValue)
      return false;
    return true;
  }
  








  public JeliDraggable checkIn(int x, int y)
  {
    int absoluteX = boundary.x;
    int absoluteY = boundary.y;
    if ((x < absoluteX + relativeX) || (x >= absoluteX + relativeX + w) || (x >= absoluteX + boundary.width))
    {
      return null; }
    if ((y < absoluteY + relativeY) || (y >= absoluteY + relativeY + h) || (y > absoluteY + boundary.height))
    {
      return null; }
    dragX = x;
    dragY = y;
    return this;
  }
  
  public void fixPosition()
  {
    int maxX = boundary.width - w - 1;
    int maxY = boundary.height - h;
    if (relativeX < minX)
      relativeX = minX;
    if (relativeX > maxX)
      relativeX = maxX;
    if (relativeX < minX)
      relativeX = minX;
    if (relativeY < minY)
      relativeY = minY;
    if (relativeY > maxY)
      relativeY = maxY;
    if (relativeY < minY) {
      relativeY = minY;
    }
  }
  
  public void moveto(int x, int y) {
    int maxX = boundary.width - w - 1;
    int maxY = boundary.height - h - 1;
    relativeX += x - dragX;
    relativeY += y - dragY;
    dragX = x;
    dragY = y;
    

    if (relativeX < minX) {
      dragX += minX - relativeX;
      relativeX = minX;
    }
    if (relativeY < minY) {
      dragY += minY - relativeY;
      relativeY = minY;
    }
    if (maxX < 0) {
      dragX += minX - relativeX;
      relativeX = minX;
    }
    else if ((relativeX > maxX) && (maxX > 0)) {
      dragX += maxX - relativeX;
      relativeX = maxX;
    }
    
    if (maxY < 0) {
      dragY += minY - relativeY;
      relativeY = minY;
    }
    else if ((relativeY > maxY) && (maxY > 0)) {
      dragY += maxY - relativeY;
      relativeY = maxY;
    }
    
    useStandardPosition = nearStandardPosition();
  }
  
  public void checkStandardPosition()
  {
    useStandardPosition = nearStandardPosition();
  }
  
  public void showInfo() {}
}
