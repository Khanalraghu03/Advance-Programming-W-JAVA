package Jeli.Widgets;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Vector;

public class SegmentedCanvas extends JeliCanvas
{
  private int MODE_NONE = 0;
  private int MODE_SEGMENT = 1;
  private int MODE_SELECT = 2;
  private int MODE_PASTE = 3;
  
  private String str;
  private java.awt.Font font;
  private java.awt.FontMetrics fm;
  private int spacing;
  private int[] segstart;
  private int numseg;
  private int size;
  private Color backColor = Color.white;
  private Color foreColor = Color.black;
  private Color selectedColor = Color.cyan;
  private int charWidth;
  private int charInc;
  private int drawY;
  private int wid;
  private int ht;
  private int mode = MODE_NONE;
  private int selectedSegment = -1;
  private ClipBoard cb = null;
  private char fillChar = '0';
  private SegmentNotify sn = null;
  private ObjectCallBack changeNotify = null;
  private int id = -1;
  private SegmentedPanel pan = null;
  private boolean inhibitSegment = false;
  private boolean inhibitSelect = false;
  private boolean inhibitPaste = false;
  private JeliMouseMotion mm = null;
  private int maxSegments = -1;
  private Vector changeNotifyList = null;
  private int lastOperationType = 0;
  private int lastOperationSegment = 0;
  
  private java.awt.Dimension calculatedSize;
  
  public SegmentedCanvas(String str, java.awt.Font f, int spacing, char fill)
  {
    segstart = new int[1];
    size = str.length();
    this.str = str;
    segstart[0] = 0;
    fillChar = fill;
    numseg = 1;
    font = f;
    fm = getFontMetrics(font);
    charWidth = fm.charWidth(str.charAt(0));
    charInc = (spacing + charWidth);
    this.spacing = spacing;
    wid = (size * charWidth + spacing * (size + 1));
    ht = (fm.getAscent() + fm.getDescent() + 4);
    drawY = (ht - 2 - fm.getDescent());
    setSize(wid, ht);
    calculatedSize = new java.awt.Dimension(wid, ht);
  }
  
  protected java.awt.Dimension getCalculatedSize() {
    return calculatedSize;
  }
  
  protected void setInhibitSegment(boolean f) {
    inhibitSegment = f;
  }
  
  protected void setMaxSegments(int n) {
    maxSegments = (n + 1);
  }
  
  protected void setInhibitSelect(boolean f) {
    inhibitSelect = f;
  }
  
  protected void setInhibitPaste(boolean f) {
    inhibitPaste = f;
  }
  
  protected SegmentedPanelInfo getInfo() {
    return new SegmentedPanelInfo(str, segstart, selectedSegment, maxSegments);
  }
  
  protected void setChangeNotify(int id, ObjectCallBack vn, SegmentedPanel pan) {
    this.pan = pan;
    if (changeNotifyList == null)
      changeNotifyList = new Vector();
    changeNotifyList.addElement(vn);
    
    if (id != -1)
      this.id = id;
  }
  
  private void executeCallBack() {
    if (changeNotifyList == null)
      return;
    for (int i = 0; i < changeNotifyList.size(); i++)
      ((ObjectCallBack)changeNotifyList.elementAt(i)).objectNotify(id, pan);
  }
  
  protected void setBackColor(Color c) {
    backColor = c;
    repaint();
  }
  
  protected void setSelectedColor(Color c) {
    selectedColor = c;
    repaint();
  }
  
  public void setClipBoard(ClipBoard cb) {
    this.cb = cb;
  }
  
  protected void setModeNone() {
    mode = MODE_NONE;
  }
  
  protected void setModeSegment() {
    if (!inhibitSegment)
      mode = MODE_SEGMENT;
  }
  
  protected void setModeSelect() {
    if (!inhibitSelect)
      mode = MODE_SELECT;
  }
  
  protected void setModePaste() {
    if (!inhibitPaste) {
      mode = MODE_PASTE;
      selectedSegment = -1;
      repaint();
    }
  }
  
  protected void removeSelected() {
    selectedSegment = -1;
    repaint();
  }
  
  protected boolean getModeNone() {
    return mode == MODE_NONE;
  }
  
  protected boolean getModeSegment() {
    return mode == MODE_SEGMENT;
  }
  
  protected boolean getModeSelect() {
    return mode == MODE_SELECT;
  }
  
  protected boolean getModePaste() {
    return mode == MODE_PASTE;
  }
  


  protected void fillImage(int w, int h)
  {
    canvasGC.setFont(font);
    canvasGC.setColor(backColor);
    canvasGC.fillRect(0, 0, w, h);
    


    if ((selectedSegment < numseg) && (selectedSegment >= 0)) {
      canvasGC.setColor(selectedColor);
      int pos; int pos; if (selectedSegment == 0) {
        pos = 0;
      } else
        pos = spacing + segstart[selectedSegment] * charInc - (spacing + 1) / 2;
      int pos1; int pos1; if (selectedSegment == numseg - 1) {
        pos1 = w;
      } else {
        pos1 = spacing + segstart[(selectedSegment + 1)] * charInc - (spacing + 1) / 2;
      }
      canvasGC.fillRect(pos, 0, pos1 - pos + 1, h);
    }
    canvasGC.setColor(foreColor);
    int pos = spacing;
    for (int i = 0; i < size; i++) {
      canvasGC.drawString("" + str.charAt(i), pos, drawY);
      pos += charInc;
    }
    for (int i = 1; i < numseg; i++) {
      pos = spacing + segstart[i] * charInc - (spacing + 2) / 2;
      canvasGC.drawLine(pos, 0, pos, h);
    }
    canvasGC.setColor(foreColor);
    canvasGC.drawRect(0, 0, w - 1, h - 1);
  }
  
  protected void removeAllSegments() {
    selectedSegment = -1;
    numseg = 1;
    segstart = new int[1];
    segstart[0] = 0;
    repaint();
  }
  

  protected void removeSegment(int n)
  {
    if (n == 0)
    {
      return;
    }
    for (int i = 0; i < numseg; i++) {
      if (segstart[i] == n) {
        removeSegmentAt(i);
        return;
      }
    }
  }
  

  private void removeSegmentAt(int n)
  {
    selectedSegment = -1;
    
    int[] newsegstart = new int[numseg - 1];
    for (int i = 0; i < n; i++)
      newsegstart[i] = segstart[i];
    for (int i = n + 1; i < numseg; i++)
      newsegstart[(i - 1)] = segstart[i];
    numseg -= 1;
    segstart = newsegstart;
    repaint();
  }
  


  protected void makeSegment(int n)
  {
    boolean notin = true;
    
    if (n <= 0)
    {
      return;
    }
    if (n >= size)
    {
      return;
    }
    for (int i = 0; i < numseg; i++) {
      if (segstart[i] == n)
      {
        return;
      }
    }
    selectedSegment = -1;
    if ((maxSegments > 1) && (numseg == maxSegments)) {
      replaceClosestSegment(n);
      repaint();
      return;
    }
    
    int[] newsegstart = new int[numseg + 1];
    for (int i = 0; i < numseg; i++)
      if (segstart[i] < n) {
        newsegstart[i] = segstart[i];
      } else {
        newsegstart[(i + 1)] = segstart[i];
        if (notin) {
          newsegstart[i] = n;
          notin = false;
        }
      }
    if (notin)
      newsegstart[numseg] = n;
    segstart = newsegstart;
    numseg += 1;
    
    repaint();
  }
  
  private void replaceClosestSegment(int n)
  {
    int closestLeft = -1;
    int closestRight = -1;
    int closest = -1;
    for (int i = 1; i < numseg; i++) {
      if (segstart[i] < n) {
        closestLeft = i;
      } else if (segstart[i] > n) {
        closestRight = i;
        break;
      }
    }
    if ((closestLeft == -1) && (closestRight == -1)) {
      System.out.println("can't find segment to remove!");
      System.out.println("fatal error");
      return;
    }
    if (closestLeft == -1) {
      closest = closestRight;
    } else if (closestRight == -1) {
      closest = closestLeft;
    }
    else if (n - segstart[closestLeft] < segstart[closestRight] - n) {
      closest = closestLeft;
    } else {
      closest = closestRight;
    }
    segstart[closest] = n;
  }
  

  public void jeliMousePressed(int x, int y, int mod, int count)
  {
    if (mode == MODE_NONE)
      return;
    if (mode == MODE_SEGMENT) {
      mouseSegment(x, mod);
    } else if (mode == MODE_SELECT) {
      mouseSelect(x);
    } else if (mode == MODE_PASTE) {
      mousePaste(x);
    }
  }
  
  private void mousePaste(int x) {
    int where = x / charInc;
    mousePasteWhere(where);
  }
  
  private void mousePasteWhere(int where)
  {
    int segnum = numseg - 1;
    for (int i = 1; i < numseg; i++) {
      if (segstart[i] > where) {
        segnum = i - 1;
        break;
      }
    }
    pasteSegment(segnum);
  }
  


















  protected void pasteSegment(int segnum)
  {
    if (segnum < 0)
      return;
    if (segnum > numseg - 1)
      return;
    lastOperationSegment = segnum;
    lastOperationType = 2;
    

    if (cb == null)
      return;
    String clip = cb.getClipBoardString();
    if (clip == null) {
      return;
    }
    int lenclip = clip.length();
    int pos1 = segstart[segnum];
    if (pos1 == 0) {
      int lenreplaced;
      if (numseg == 1) {
        int lenreplaced = str.length();
        str = fixString(clip, lenreplaced, fillChar);
      }
      else
      {
        lenreplaced = segstart[1]; }
      String clip1 = fixString(clip, lenreplaced, fillChar);
      
      str = (clip1 + str.substring(lenreplaced));

    }
    else if (segnum == numseg - 1)
    {
      int lenreplaced = str.length() - segstart[segnum];
      String clip1 = fixString(clip, lenreplaced, fillChar);
      
      str = (str.substring(0, segstart[segnum]) + clip1);

    }
    else
    {
      int lenreplaced = segstart[(segnum + 1)] - segstart[segnum];
      String clip1 = fixString(clip, lenreplaced, fillChar);
      
      str = (str.substring(0, segstart[segnum]) + clip1 + str.substring(segstart[(segnum + 1)]));
    }
    


    if (sn != null)
      sn.pasteOccurred(id, segnum);
    executeCallBack();
    repaint();
  }
  


  private String fixString(String s, int newlen, char fill)
  {
    int len = s.length();
    if (len == newlen)
      return s;
    if (len > newlen)
      return s.substring(len - newlen);
    String s1 = s;
    for (int i = 0; i < newlen - len; i++)
      s1 = fill + s1;
    return s1;
  }
  
  private void mouseSegment(int x, int mod)
  {
    int where = x / charInc;
    
    lastOperationType = 0;
    
    if ((mod & 0x1) == 0) {
      makeSegment(where);
      if (sn != null)
        sn.segmentOccurred(id, where);
      executeCallBack();
      

      return;
    }
    removeSegment(where);
    if (sn != null)
      sn.segmentRemoved(id, where);
    executeCallBack();
  }
  


  private void mouseSelect(int x)
  {
    int where = x / charInc;
    mouseSelectWhere(where);
  }
  
  private void mouseSelectWhere(int where)
  {
    int segnum = numseg - 1;
    for (int i = 1; i < numseg; i++) {
      if (segstart[i] > where) {
        segnum = i - 1;
        break;
      }
    }
    selectSegment(segnum);
  }
  










  protected void selectSegment(int segnum)
  {
    if (segnum < 0)
      return;
    if (segnum >= numseg)
      return;
    selectedSegment = segnum;
    
    if (cb != null)
      cb.setClipBoardString(getSelectedString());
    if (sn != null) {
      sn.selectOccurred(id, selectedSegment);
    }
    
    lastOperationSegment = selectedSegment;
    lastOperationType = 1;
    
    executeCallBack();
    repaint();
  }
  
  protected void setSelectedSegment(int n) {
    if (n >= numseg)
      return;
    selectedSegment = n;
    repaint();
  }
  
  protected int getSelectedSegment() {
    return selectedSegment;
  }
  

  protected String getSelectedString()
  {
    if (selectedSegment < 0)
      return null;
    int pos1 = segstart[selectedSegment];
    if (selectedSegment == numseg - 1)
      return str.substring(pos1);
    int pos2 = segstart[(selectedSegment + 1)];
    

    return str.substring(pos1, pos2);
  }
  
  private void showSegmentPositions() {
    System.out.print(numseg + ":");
    for (int i = 0; i < numseg; i++)
      System.out.print(" " + segstart[i]);
    System.out.println();
  }
  
  protected String getSegmentSizesString()
  {
    if (numseg == 1)
      return "" + size;
    String str = "";
    for (int i = 1; i < numseg; i++)
      str = str + (segstart[i] - segstart[(i - 1)]) + " ";
    str = str + (size - segstart[(numseg - 1)]);
    return str;
  }
  
  protected int getNumberOfSegments() {
    return numseg;
  }
  

  protected String getSegmentString(int which)
  {
    if (which < 0)
      return null;
    if (which >= numseg)
      return null;
    int pos1 = segstart[which];
    if (which == numseg - 1)
      return str.substring(pos1);
    int pos2 = segstart[(which + 1)];
    return str.substring(pos1, pos2);
  }
  
  protected void setCallBack(SegmentNotify sn) {
    this.sn = sn;
  }
  
  protected void setID(int id) {
    this.id = id;
  }
  
  public void jeliMouseMoved(int x, int y)
  {
    super.jeliMouseMoved(x, y);
    int where = x / charInc;
    
    if (mm != null)
      mm.jeliMouseMotionMove(where, 0);
  }
  
  public void jeliMouseEnter() {
    super.jeliMouseEnter();
    if (mm != null)
      mm.jeliMouseMotionEnter();
  }
  
  public void jeliMouseExited() {
    super.jeliMouseExited();
    if (mm != null)
      mm.jeliMouseMotionExit();
  }
  
  protected void setPositionMonitor(JeliMouseMotion mm) {
    this.mm = mm;
  }
  
  protected int getLastOperationType() {
    return lastOperationType;
  }
  
  protected int getLastOperationSegment() {
    return lastOperationSegment;
  }
}
