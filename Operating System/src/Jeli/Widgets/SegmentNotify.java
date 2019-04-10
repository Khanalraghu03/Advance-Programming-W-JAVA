package Jeli.Widgets;

public abstract interface SegmentNotify
{
  public abstract void segmentOccurred(int paramInt1, int paramInt2);
  
  public abstract void segmentRemoved(int paramInt1, int paramInt2);
  
  public abstract void selectOccurred(int paramInt1, int paramInt2);
  
  public abstract void pasteOccurred(int paramInt1, int paramInt2);
}
