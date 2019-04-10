package Jeli.Widgets;

public class SegmentedPanelInfo
{
  public String str;
  public int[] segstart;
  public int selectedSegment;
  public int maxSegments;
  
  public SegmentedPanelInfo(String str, int[] segst, int selectedSegment, int maxSegments)
  {
    this.str = str;
    this.selectedSegment = selectedSegment;
    this.maxSegments = maxSegments;
    segstart = new int[segst.length];
    for (int i = 0; i < segst.length; i++) {
      segstart[i] = segst[i];
    }
  }
}
