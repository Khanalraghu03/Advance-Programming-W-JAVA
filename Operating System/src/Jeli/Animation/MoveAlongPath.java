package Jeli.Animation;

import Jeli.Utility;
import java.awt.Point;

public class MoveAlongPath
{
  protected Point[] points;
  protected long startTime;
  protected long duration;
  protected JeliNotify dn;
  protected Object notifyArg;
  protected double totalLength;
  protected double[] segmentLengths;
  
  public MoveAlongPath(Point[] points, long startTime, long duration, JeliNotify dn, Object notifyArg)
  {
    this.points = points;
    this.startTime = startTime;
    if (Utility.getVirtualTime() != null)
      this.startTime = Utility.getVirtualTime().getVirtualTime(startTime);
    this.duration = duration;
    this.dn = dn;
    this.notifyArg = notifyArg;
    setLengths();
  }
  
  public MoveAlongPath(Point p1, Point p2, long startTime, long duration, JeliNotify dn, Object notifyArg)
  {
    points = new Point[2];
    points[0] = p1;
    points[1] = p2;
    this.startTime = startTime;
    if (Utility.getVirtualTime() != null)
      this.startTime = Utility.getVirtualTime().getVirtualTime(startTime);
    this.duration = duration;
    this.dn = dn;
    this.notifyArg = notifyArg;
    setLengths();
  }
  
  public MoveAlongPath(Point p1, Point p2, Point p3, long startTime, long duration, JeliNotify dn, Object notifyArg)
  {
    points = new Point[3];
    points[0] = p1;
    points[1] = p2;
    points[2] = p3;
    this.startTime = startTime;
    if (Utility.getVirtualTime() != null)
      this.startTime = Utility.getVirtualTime().getVirtualTime(startTime);
    this.duration = duration;
    this.dn = dn;
    this.notifyArg = notifyArg;
    setLengths();
  }
  
  public MoveAlongPath(Point p1, Point p2, Point p3, Point p4, long startTime, long duration, JeliNotify dn, Object notifyArg)
  {
    points = new Point[4];
    points[0] = p1;
    points[1] = p2;
    points[2] = p3;
    points[3] = p4;
    this.startTime = startTime;
    if (Utility.getVirtualTime() != null)
      this.startTime = Utility.getVirtualTime().getVirtualTime(startTime);
    this.duration = duration;
    this.dn = dn;
    this.notifyArg = notifyArg;
    setLengths();
  }
  
  private void setLengths() {
    totalLength = 0.0D;
    segmentLengths = new double[points.length - 1];
    for (int i = 0; i < segmentLengths.length; i++) {
      segmentLengths[i] = Math.sqrt((points[i].x - points[(i + 1)].x) * (points[i].x - points[(i + 1)].x) + (points[i].y - points[(i + 1)].y) * (points[i].y - points[(i + 1)].y));
      


      totalLength += segmentLengths[i];
    }
  }
  


  public MoveAlongPath makeFromPoints(Point p1, Point p2, Point p3, Point p4, long startTime, long duration, JeliNotify dn, Object notifyArg)
  {
    Point[] points = new Point[4];
    points[0] = p1;
    points[1] = p2;
    points[2] = p3;
    points[3] = p4;
    return new MoveAlongPath(points, startTime, duration, dn, notifyArg);
  }
  


  public Point getPoint(long currentTime)
  {
    double cumulativeLength = 0.0D;
    


    if (Utility.getVirtualTime() != null)
      currentTime = Utility.getVirtualTime().getVirtualTime(currentTime);
    if (currentTime > startTime + duration)
      return null;
    double r = (currentTime - startTime) / duration;
    double s = r * totalLength;
    if (s > totalLength)
      return null;
    int segmentIndex = segmentLengths.length - 2;
    for (int i = 0; i < segmentLengths.length; i++) {
      cumulativeLength += segmentLengths[i];
      if (s <= cumulativeLength) {
        segmentIndex = i;
        break;
      }
    }
    double segmentPosition = s - cumulativeLength + segmentLengths[segmentIndex];
    r = segmentPosition / segmentLengths[segmentIndex];
    s = 1.0D - r;
    int currentX = round(s * points[segmentIndex].x + r * points[(segmentIndex + 1)].x);
    
    int currentY = round(s * points[segmentIndex].y + r * points[(segmentIndex + 1)].y);
    


    return new Point(currentX, currentY);
  }
  
  private int round(double x) {
    return (int)(x + 0.5D);
  }
}
