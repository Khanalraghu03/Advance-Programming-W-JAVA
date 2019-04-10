package Jeli.Logging;

public class GanttInfo
{
  public int objectNumber;
  public int type;
  public double start;
  public double end;
  
  public GanttInfo(int num, int type, double start, double end) {
    objectNumber = num;
    this.type = type;
    this.start = start;
    this.end = end;
  }
}
