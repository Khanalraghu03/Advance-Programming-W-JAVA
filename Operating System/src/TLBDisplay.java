import Jeli.Widgets.JeliButton;
import Jeli.Widgets.JeliLabel;
import Jeli.Widgets.SegmentedPanel;
import java.awt.Panel;
import java.io.PrintStream;

public class TLBDisplay extends Jeli.Widgets.JeliFrame implements Jeli.Widgets.SegmentNotify, Jeli.Widgets.ClipBoard, Jeli.Widgets.JeliMouseMotion
{
  private int TLB_ID = 30;
  private TLB tlb;
  private int size;
  private int pBits;
  private int fBits;
  private SegmentedPanel[] entries;
  private java.awt.Font f;
  private JeliLabel pageBitsLabel;
  private JeliLabel frameBitsLabel;
  private JeliButton modeButton;
  private JeliButton hideButton;
  private JeliButton helpButton;
  private JeliLabel clipLabel;
  private JeliLabel infoLabel;
  private JeliButton lookupButton;
  private int segmentPosition = -1;
  private boolean segmentMode = true;
  private String clipStr = "";
  private Jeli.Widgets.ClipBoard cb = null;
  
  private int tlbid = -1;
  
  private java.util.Vector changeNotifyList = null;
  private boolean lastSearchNotFound = false;
  private Jeli.Widgets.SelfDestructingShortInfoFrame sif = null;
  private String name = "Translation Lookaside Buffer";
  private int numSec = 10;
  private String lastEventString = null;
  private String helpMsg = "TLB entries are shown in this widget with a light blue background.\nThey contain a page number and a frame number.\nYou must first determine the boundary between these and segment\n   the TLB on the correct boundary.\nDo this by clicking the mouse at the appropriate location.\nThe number of bits in the page number and frame number are displayed\n   below the TLB entries.\nOnce the TLB is segmented, a page or frame number may be selected\n   by clicking on the Mode widget change to Select mode.\nWhen a field is selected, it is placed in the clipboard.\nSelect mode is chosen by clicking on the Mode button which is\n   below the Page Bits label.\nAfter the TLB has been segmented, you can look up a page number\n   by putting the page number in the clipboard clicking the Lookup button.\n";
  













  public TLBDisplay(TLB tlb, int num)
  {
    super("TLB");
    

    this.tlb = tlb;
    size = tlb.getSize();
    pBits = tlb.getPageBits();
    fBits = tlb.getFrameBits();
    numSec = num;
    
    entries = new SegmentedPanel[size];
    f = new java.awt.Font("Times", 0, 10);
    for (int i = 0; i < size; i++) {
      String str = sl(tlb.getPage(i), pBits) + sl(tlb.getFrame(i), fBits);
      entries[i] = new SegmentedPanel(pBits + fBits, str, '0', f, 4);
      entries[i].setModeSegment();
      entries[i].setID(i);
      entries[i].setCallBack(this);
      entries[i].setPositionMonitor(this);
      entries[i].setBackColor(Jeli.Utility.jeliVeryLightBlue);
    }
    setupLayout();
    setPositionLabels();
    setModeLabel();
    addWindowListener(this);
  }
  
  public boolean notFound() {
    return lastSearchNotFound;
  }
  
  public void setClipBoard(Jeli.Widgets.ClipBoard cb) {
    this.cb = cb;
  }
  
  private void setClipLabel() {
    clipLabel.setLabel2(clipStr);
  }
  
  private void setPositionLabels() {
    if (segmentPosition < 0) {
      pageBitsLabel.setLabel2("");
      frameBitsLabel.setLabel2("");
      lookupButton.disableJeliButton();
    }
    else {
      pageBitsLabel.setLabel2("" + segmentPosition);
      frameBitsLabel.setLabel2("" + (pBits + fBits - segmentPosition));
      lookupButton.enableJeliButton();
    }
  }
  
  private void setModeLabel() {
    if (segmentMode) {
      modeButton.setLabel("Mode: Segment");
    }
    else {
      modeButton.setLabel("Mode: Select");
    }
  }
  
  public boolean getSegmentMode() {
    return segmentMode;
  }
  
  private String sl(long n, int len)
  {
    String s = Long.toString(n, 2);
    while (s.length() < len) {
      s = "0" + s;
    }
    return s;
  }
  
  private void setupLayout() {
    setLayout(new java.awt.BorderLayout());
    Panel ePanel = new Panel();
    ePanel.setLayout(new Jeli.Widgets.JeliGridLayout(size, 1));
    for (int i = 0; i < size; i++) {
      ePanel.add(entries[i]);
    }
    add("Center", ePanel);
    Panel cPanel = new Panel();
    cPanel.setLayout(new Jeli.Widgets.JeliGridLayout(5, 1));
    Panel bPanel1 = new Panel();
    bPanel1.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
    bPanel1.add(this.pageBitsLabel = new JeliLabel("Page Bits:"));
    bPanel1.add(this.frameBitsLabel = new JeliLabel("Frame Bits:"));
    cPanel.add(bPanel1);
    cPanel.add(this.modeButton = new JeliButton("Mode:", this));
    cPanel.add(this.clipLabel = new JeliLabel("clipboard:"));
    Panel bPanel2 = new Panel();
    bPanel2.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
    bPanel2.add(this.lookupButton = new JeliButton("Lookup", this));
    bPanel2.add(this.infoLabel = new JeliLabel(""));
    cPanel.add(bPanel2);
    Panel bPanel3 = new Panel();
    bPanel3.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
    bPanel3.add(this.hideButton = new JeliButton("Hide", this));
    bPanel3.add(this.helpButton = new JeliButton("Help", this));
    helpButton.setBackground(Address.helpColor);
    hideButton.setBackground(Address.hideColor);
    helpButton.setPositionCenter();
    hideButton.setPositionCenter();
    cPanel.add(bPanel3);
    add("South", cPanel);
    pack();
    setResizable(false);
  }
  
  private void setAllSegmentMode() {
    for (int i = 0; i < size; i++) {
      entries[i].setModeSegment();
      entries[i].removeSelected();
    }
  }
  
  private void setAllSelectMode() {
    for (int i = 0; i < size; i++) {
      entries[i].setModeSelect();
    }
  }
  
  public void setSegmentMode() {
    segmentMode = true;
    setAllSegmentMode();
    setModeLabel();
  }
  
  public void segmentOccurred(int id, int pos)
  {
    for (int i = 0; i < size; i++) {
      entries[i].removeAllSegments();
      entries[i].makeSegment(pos);
    }
    segmentPosition = pos;
    setPositionLabels();
    infoLabel.setLabel("Segment Position: " + pos);
    

    lastEventString = ("TLB segmented into " + segmentPosition + " and " + (pBits + fBits - segmentPosition));
    
    sendNotify();
  }
  

  public void sendNotify()
  {
    if (changeNotifyList == null) {
      return;
    }
    for (int i = 0; i < changeNotifyList.size(); i++) {
      ((Jeli.Widgets.ObjectCallBack)changeNotifyList.elementAt(i)).objectNotify(tlbid, this);
    }
  }
  
  public void segmentRemoved(int id, int pos) {
    System.out.println("Segment Removed in " + id + " at " + pos);
    for (int i = 0; i < size; i++) {
      entries[i].removeAllSegments();
    }
    segmentPosition = -1;
    infoLabel.setLabel("Segment removed");
    setPositionLabels();
    

    lastEventString = "TLB Segment Removed";
    sendNotify();
  }
  

  public void selectOccurred(int id, int pos)
  {
    System.out.println("Select Occurred in " + id + " at " + pos);
    
    for (int i = 0; i < size; i++) {
      if (i == id) {
        System.out.println("   Found id");
        String sel = entries[i].getSelectedString();
        if (sel != null) {
          System.out.println("   selected string is " + sel);
          System.out.println("   clipboard is " + cb);
          lastEventString = ("TLB Field Selected: " + sel);
          clipStr = sel;
          if (cb != null) {
            cb.setClipBoardString(clipStr);
          }
        }
        else {
          lastEventString = "TLB Select Failed";
        }
      }
      else {
        entries[i].removeSelected();
        lastEventString = "TLB Select Removed";
      }
    }
    infoLabel.setLabel("Field Selected");
    sendNotify();
  }
  

  public void pasteOccurred(int id, int pos)
  {
    System.out.println("Paste Occurred in " + id + " at " + pos);
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == hideButton) {
      infoLabel.setLabel("");
      setVisible(false);
    }
    else if (bh == modeButton) {
      if ((segmentMode) && (segmentPosition < 0)) {
        infoLabel.setLabel("Must Segment First");
        return;
      }
      segmentMode = (!segmentMode);
      if (segmentMode) {
        setAllSegmentMode();
        infoLabel.setLabel("Segment mode set");
      }
      else {
        setAllSelectMode();
        infoLabel.setLabel("Select mode set");
      }
      setModeLabel();
    }
    else if (bh == lookupButton) {
      lookup();
    }
    else if (bh == helpButton) {
      if (sif != null)
        sif.abort();
      sif = new Jeli.Widgets.SelfDestructingShortInfoFrame(name, helpMsg, numSec, helpButton);
    }
  }
  




  public void lookup()
  {
    lastSearchNotFound = false;
    if (segmentPosition < 0) {
      return;
    }
    if ((clipStr == null) || (clipStr.length() == 0)) {
      infoLabel.setLabel("Clipboard is empty");
      return;
    }
    for (int i = 0; i < size; i++) {
      entries[i].removeSelected();
    }
    String newClip = clipStr;
    

    if (newClip.length() > segmentPosition) {
      for (int i = 0; i < newClip.length() - segmentPosition; i++) {
        System.out.println("Checking newClip at position " + i + " is " + newClip.charAt(i));
        
        if (newClip.charAt(i) != '0') {
          infoLabel.setLabel("Clipboard value too large");
          return;
        }
      }
      newClip = newClip.substring(newClip.length() - segmentPosition);
      System.out.println("Using truncated clipboard string: " + newClip);
    }
    while (newClip.length() < segmentPosition)
    {
      newClip = "0" + newClip;
    }
    for (int i = 0; i < size; i++) {
      String s = entries[i].getString();
      String page = s.substring(0, segmentPosition);
      
      if (newClip.equals(page)) {
        infoLabel.setLabel("Found Frame");
        entries[i].setSelectedSegment(1);
        cb.setClipBoardString(s.substring(segmentPosition));
        lastEventString = ("TLB lookup found " + s.substring(segmentPosition));
        sendNotify();
        

        return;
      }
    }
    infoLabel.setLabel("Page not found");
    lastSearchNotFound = true;
    lastEventString = "TLB lookup failed";
    sendNotify();
  }
  

  public void setClipBoardString(String s)
  {
    clipStr = s;
    setClipLabel();
  }
  
  public String getClipBoardString() {
    return clipStr;
  }
  
  public void jeliMouseMotionEnter() {}
  
  public void jeliMouseMotionExit()
  {
    if (segmentMode) {
      modeButton.setLabel("Mode: Segment");
    }
  }
  
  public void jeliMouseMotionMove(int x, int y) {
    if (segmentMode) {
      modeButton.setLabel("Mode: Segment " + x + " " + (pBits + fBits - x));
    }
  }
  
  public void setChangeNotify(int id, Jeli.Widgets.ObjectCallBack vn) {
    tlbid = id;
    
    if (changeNotifyList == null) {
      changeNotifyList = new java.util.Vector();
    }
    changeNotifyList.addElement(vn);
  }
  
  public int getSegmentedPosition() {
    return segmentPosition;
  }
  
  public boolean selectedCorrect()
  {
    int entry = tlb.getCorrectEntry();
    if (entry < 0) {
      return false;
    }
    if (entry >= size) {
      return false;
    }
    if (entries[entry].getSelectedSegment() == 1) {
      return true;
    }
    return false;
  }
  
  public int getTLBWidth() {
    return pBits + fBits;
  }
  
  public String getLastEventString() {
    return lastEventString;
  }
}
