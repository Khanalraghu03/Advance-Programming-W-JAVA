package Jeli.Animation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public abstract class MoveAlongLine
{
  protected Point start;
  protected Point end;
  protected Dimension size;
  protected java.awt.Color c;
  protected long startTime;
  protected long duration;
  protected JeliNotify dn;
  protected Object notifyArg;
  
  public MoveAlongLine(Point start, Point end, Dimension size, java.awt.Color c, long startTime, long duration, JeliNotify dn, Object notifyArg)
  {
    this.start = start;
    this.end = end;
    this.size = size;
    this.c = c;
    this.startTime = startTime;
    this.duration = duration;
    this.dn = dn;
    this.notifyArg = notifyArg;
  }
  

  protected abstract void draw(Graphics paramGraphics, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public boolean draw(long currentTime, Graphics g)
  {
    Point p = getPoint(currentTime);
    if (p == null) {
      if (dn != null)
        dn.notifyMe(notifyArg);
      dn = null;
      return true;
    }
    g.setColor(c);
    draw(g, x, y, size.width, size.height);
    return false;
  }
  



  public Point getPoint(long currentTime)
  {
    if (currentTime > startTime + duration)
      return null;
    double r = (currentTime - startTime) / duration;
    double s = 1.0D - r;
    int currentX = (int)(s * start.x + r * end.x);
    int currentY = (int)(s * start.y + r * end.y);
    return new Point(currentX, currentY);
  }
}
