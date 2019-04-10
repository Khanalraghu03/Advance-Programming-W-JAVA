package Jeli.Logging;

public class GanttInfoI
{
  public int ObjectNumber;
  public int Type;
  public long Start;
  public long End;
  
  public GanttInfoI(int num, int type, long start, long end) {
    ObjectNumber = num;
    Type = type;
    Start = start;
    End = end;
  }
}
