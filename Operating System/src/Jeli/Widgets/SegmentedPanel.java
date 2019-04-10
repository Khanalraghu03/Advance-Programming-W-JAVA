package Jeli.Widgets;

import java.awt.Color;

public class SegmentedPanel extends java.awt.Panel
{
  public static final int OPERATION_SEGMENT = 0;
  public static final int OPERATION_SELECT = 1;
  public static final int OPERATION_PASTE = 2;
  private String str;
  int size;
  java.awt.Font font;
  int spacing;
  SegmentedCanvas sc;
  
  public SegmentedPanel(int n, String str, char fill, java.awt.Font f, int spacing)
  {
    size = n;
    this.str = str;
    if (str == null)
      str = "";
    if (str.length() > size)
      str = str.substring(0, size); else
      while (str.length() < size)
        str = fill + str;
    font = f;
    this.spacing = spacing;
    sc = new SegmentedCanvas(str, f, spacing, fill);
    setLayout(new java.awt.GridLayout(1, 1));
    add(sc);
  }
  
  public void setMaxSegments(int n) {
    sc.setMaxSegments(n);
  }
  
  public void inhibitSegment(boolean f) {
    sc.setInhibitSegment(f);
  }
  
  public void inhibitSelect(boolean f) {
    sc.setInhibitSelect(f);
  }
  
  public void inhibitPaste(boolean f) {
    sc.setInhibitPaste(f);
  }
  
  public void setChangeNotify(int id, ObjectCallBack vn) {
    sc.setChangeNotify(id, vn, this);
  }
  
  public String getString() {
    return str;
  }
  
  public SegmentedPanelInfo getInfo() {
    return sc.getInfo();
  }
  
  public void removeAllSegments() {
    sc.removeAllSegments();
  }
  
  public void makeSegment(int n) {
    sc.makeSegment(n);
  }
  
  public void reDraw() {
    sc.repaint();
  }
  
  public void setModeNone() {
    sc.setModeNone();
  }
  
  public void setModeSegment() {
    sc.setModeSegment();
  }
  
  public void setModeSelect() {
    sc.setModeSelect();
  }
  
  public void setModePaste() {
    sc.setModePaste();
  }
  
  public void setClipBoard(ClipBoard cb) {
    sc.setClipBoard(cb);
  }
  
  public boolean getModeNone() {
    return sc.getModeNone();
  }
  
  public boolean getModeSegment() {
    return sc.getModeSegment();
  }
  
  public boolean getModeSelect() {
    return sc.getModeSelect();
  }
  
  public boolean getModePaste() {
    return sc.getModePaste();
  }
  
  public void setBackColor(Color c)
  {
    sc.setBackColor(c);
  }
  
  public void setSelectedColor(Color c) {
    sc.setSelectedColor(c);
  }
  
  public void setCallBack(SegmentNotify sn) {
    sc.setCallBack(sn);
  }
  
  public void setID(int id) {
    sc.setID(id);
  }
  
  public void removeSelected() {
    sc.removeSelected();
  }
  
  public String getSelectedString() {
    return sc.getSelectedString();
  }
  
  public int getSelectedSegment() {
    return sc.getSelectedSegment();
  }
  
  public void setSelectedSegment(int n) {
    sc.setSelectedSegment(n);
  }
  
  public void setPositionMonitor(JeliMouseMotion mm) {
    sc.setPositionMonitor(mm);
  }
  
  public int getLastOperationType() {
    return sc.getLastOperationType();
  }
  
  public int getLastOperationSegment() {
    return sc.getLastOperationSegment();
  }
  
  public String getSegmentSizesString() {
    return sc.getSegmentSizesString();
  }
  
  public int getNumberOfSegments() {
    return sc.getNumberOfSegments();
  }
  
  public String getSegmentString(int which) {
    return sc.getSegmentString(which);
  }
  
  public java.awt.Dimension getPreferredSize() {
    return sc.getCalculatedSize();
  }
  
  public void pasteSegment(int segnum) {
    sc.pasteSegment(segnum);
  }
  
  public void selectSegment(int segnum) {
    sc.selectSegment(segnum);
  }
}
