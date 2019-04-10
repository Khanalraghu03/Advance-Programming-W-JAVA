package Jeli.Widgets;

import java.awt.Color;
import java.awt.Panel;

public class SegmentedPanelMode extends Panel implements JeliButtonCallBack, JeliMouseMotion
{
  private JeliButton modeNoneButton;
  private JeliButton modeSegmentButton;
  private JeliButton modeSelectButton;
  private JeliButton modePasteButton;
  private JeliButton modeHelpButton = null;
  private JeliLabel titleAboveLabel;
  private JeliLabel titleBelowLabel;
  private JeliButton modePositionButton;
  private SegmentedPanel sp;
  private Color buttonBackground = Color.white;
  private Color buttonBackgroundHighlighted = Color.cyan;
  private Color background = Color.white;
  private Color backgroundHighlighted = Color.cyan;
  private SelfDestructingShortInfoFrame sif = null;
  private String helpMsg = null;
  private String name;
  private int numSec = -1;
  


  private boolean showNoneFlag = false;
  private boolean showSegmentFlag = false;
  private boolean showSelectFlag = false;
  private boolean showPasteFlag = false;
  private boolean showHelpFlag = false;
  private boolean showPositionFlag = false;
  private int size;
  private boolean useModePositionDefault = false;
  private boolean standardHelpMessageFlag = false;
  
  private String classification = "SegmentedPanelMode";
  
  public SegmentedPanelMode(int n, String str, char fill, java.awt.Font f, int spacing, boolean helpFlag, String titleAbove, String titleBelow)
  {
    this(n, str, fill, f, spacing, titleAbove, titleBelow, true, true, true, true, helpFlag, false);
  }
  













  public SegmentedPanelMode(int n, String str, char fill, java.awt.Font f, int spacing, String titleAbove, String titleBelow, boolean showNoneFlag, boolean showSegmentFlag, boolean showSelectFlag, boolean showPasteFlag, boolean showHelpFlag, boolean showPositionFlag)
  {
    int modeCount = 0;
    size = n;
    this.showNoneFlag = showNoneFlag;
    this.showSegmentFlag = showSegmentFlag;
    this.showSelectFlag = showSelectFlag;
    this.showPasteFlag = showPasteFlag;
    this.showHelpFlag = showHelpFlag;
    this.showPositionFlag = showPositionFlag;
    if ((!showNoneFlag) && (!showSegmentFlag) && (!showSelectFlag) && (!showPasteFlag) && (!showHelpFlag) && (!showPositionFlag))
    {
      showHelpFlag = true; }
    if (showNoneFlag)
      modeCount++;
    if (showSegmentFlag)
      modeCount++;
    if (showSelectFlag)
      modeCount++;
    if (showPasteFlag)
      modeCount++;
    if (showHelpFlag)
      modeCount++;
    if (showPositionFlag)
      modeCount++;
    java.awt.GridBagLayout gbl = new java.awt.GridBagLayout();
    java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
    setLayout(gbl);
    weightx = 0.0D;
    weighty = 0.0D;
    fill = 0;
    gridx = 0;
    gridy = 0;
    if (titleAbove != null) {
      titleAboveLabel = new JeliLabel(titleAbove);
      titleAboveLabel.setPositionCenter();
      titleAboveLabel.setBackground(buttonBackground);
      titleAboveLabel.setBorderType(0);
      gbl.setConstraints(titleAboveLabel, c);
      add(titleAboveLabel);
      gridy += 1;
    }
    
    Panel modePanel = new Panel();
    modePanel.setLayout(new JeliGridLayout(1, modeCount));
    modeNoneButton = new JeliButton("None", this);
    modeSegmentButton = new JeliButton("Segment", this);
    modeSelectButton = new JeliButton("Select", this);
    modePasteButton = new JeliButton("Paste", this);
    modeHelpButton = new JeliButton("Help", this);
    modePositionButton = new JeliButton("", this);
    modeNoneButton.setPositionCenter();
    modeSegmentButton.setPositionCenter();
    modeSelectButton.setPositionCenter();
    modePasteButton.setPositionCenter();
    modeHelpButton.setPositionCenter();
    modeSegmentButton.setInhibitReverse(true);
    modePositionButton.setInhibitReverse(true);
    modeSelectButton.setInhibitReverse(true);
    modePasteButton.setInhibitReverse(true);
    modeNoneButton.setInhibitReverse(true);
    modeHelpButton.setInhibitReverse(true);
    if (showSegmentFlag) {
      modeSegmentButton.setBorderType(7);
      modePositionButton.setBorderType(11);
    }
    if (showNoneFlag)
      modePanel.add(modeNoneButton);
    if (showSegmentFlag)
      modePanel.add(modeSegmentButton);
    if (showPositionFlag)
      modePanel.add(modePositionButton);
    if (showSelectFlag)
      modePanel.add(modeSelectButton);
    if (showPasteFlag)
      modePanel.add(modePasteButton);
    if (showHelpFlag)
      modePanel.add(modeHelpButton);
    gbl.setConstraints(modePanel, c);
    add(modePanel);
    gridy += 1;
    
    sp = new SegmentedPanel(n, str, fill, f, spacing);
    if (showPositionFlag)
      sp.setPositionMonitor(this);
    gbl.setConstraints(sp, c);
    add(sp);
    gridy += 1;
    
    if (titleBelow != null) {
      titleBelowLabel = new JeliLabel(titleBelow);
      titleBelowLabel.setPositionCenter();
      titleBelowLabel.setBackground(buttonBackground);
      titleBelowLabel.setBorderType(0);
      gbl.setConstraints(titleBelowLabel, c);
      add(titleBelowLabel);
      gridy += 1;
    }
    sp.setBackColor(background);
    sp.setSelectedColor(backgroundHighlighted);
    setButtonColors();
  }
  
  public void setUseModePositionDefault(boolean f) {
    useModePositionDefault = f;
  }
  
  public void setInhibitSegment(boolean f) {
    if (f) {
      modeSegmentButton.disableJeliButton();
      modePositionButton.disableJeliButton();
    }
    else {
      modeSegmentButton.enableJeliButton();
      modePositionButton.enableJeliButton();
    }
  }
  
  public void setInhibitSelect(boolean f) {
    if (f) {
      modeSelectButton.disableJeliButton();
    } else
      modeSelectButton.enableJeliButton();
  }
  
  public void setInhibitPaste(boolean f) {
    if (f) {
      modePasteButton.disableJeliButton();
    } else
      modePasteButton.enableJeliButton();
  }
  
  public void setHelpMessage(String name, String s, int n) {
    this.name = name;
    helpMsg = s;
    numSec = n;
  }
  
  public void setHelpBackground(Color c) {
    modeHelpButton.setBackground(c);
  }
  
  public void setStandardHelpMessage(String name, int n) {
    this.name = name;
    standardHelpMessageFlag = true;
    numSec = n;
  }
  
  public SegmentedPanel getSegmentedPanel() {
    return sp;
  }
  
  public void setModeNone() {
    sp.setModeNone();
    setButtonColors();
  }
  
  public void setModeSegment() {
    sp.setModeSegment();
    setButtonColors();
  }
  
  public void setModeSelect() {
    sp.setModeSelect();
    setButtonColors();
  }
  
  public void setModePaste() {
    sp.setModePaste();
    setButtonColors();
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == modeNoneButton) {
      sp.setModeNone();
    } else if ((bh == modeSegmentButton) || (bh == modePositionButton)) {
      sp.setModeSegment();
    } else if (bh == modeSelectButton) {
      sp.setModeSelect();
    } else if (bh == modePasteButton) {
      sp.setModePaste();
    } else if (bh == modeHelpButton)
      if (sif == null) {
        if (standardHelpMessageFlag)
          helpMsg = createStandardHelpMessage();
        if (helpMsg == null)
          return;
        sif = new SelfDestructingShortInfoFrame(name, helpMsg, numSec, modeHelpButton);
      }
      else
      {
        sif.restart();
      }
    setButtonColors();
  }
  
  private String createStandardHelpMessage()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("The mode of operation is set by the buttons to the left of");
    sb.append(" this Help button.\n");
    sb.append("The current mode is highlighted.\n");
    sb.append("Clicking the mouse causes the following action");
    sb.append(" depending on the mode:\n");
    if (showNoneFlag)
      sb.append("    None: nothing happens\n");
    if (showSegmentFlag) {
      sb.append("    Segment: divide into logical segments at the");
      sb.append(" current position or\n");
      sb.append("                    (if SHIFT is active)");
      sb.append(" remove a segment at the given position.\n");
    }
    if (showSelectFlag) {
      sb.append("    Select: select the segment,");
      sb.append(" moving its contents into the Clipboard.\n");
    }
    if (showPasteFlag) {
      sb.append("    Paste: replace the segment with the ");
      sb.append("current contents of the Clipboard.\n");
    }
    if (showPositionFlag) {
      sb.append("The current segment sizes are shown in the");
      sb.append(" Segment mode button.\n");
      sb.append("   When this mode is active, the values are updated");
      sb.append(" to display th sizes\n");
      sb.append("   that would be produced by clicking the mouse at the");
      sb.append(" current location.\n");
    }
    return sb.toString();
  }
  
  public void reset() {
    setButtonColors();
  }
  
  private void setButtonColors() {
    if (sp.getModeNone()) {
      modeNoneButton.setBackground(buttonBackgroundHighlighted);
    } else
      modeNoneButton.setBackground(buttonBackground);
    if (sp.getModeSegment()) {
      modeSegmentButton.setBackground(buttonBackgroundHighlighted);
      modePositionButton.setBackground(buttonBackgroundHighlighted);
    }
    else {
      modeSegmentButton.setBackground(buttonBackground);
      modePositionButton.setBackground(buttonBackground);
    }
    if (sp.getModeSelect()) {
      modeSelectButton.setBackground(buttonBackgroundHighlighted);
    } else
      modeSelectButton.setBackground(buttonBackground);
    if (sp.getModePaste()) {
      modePasteButton.setBackground(buttonBackgroundHighlighted);
    } else
      modePasteButton.setBackground(buttonBackground); }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  public String getClassification() { return classification; }
  
  public void setClassification(String str) {}
  
  public void jeliMouseMotionEnter() {}
  
  public void jeliMouseMotionExit()
  {
    String s1 = "";
    String s2 = "";
    String s3 = "";
    
    if (useModePositionDefault) {
      SegmentedPanelInfo info = sp.getInfo();
      if (segstart.length == 2) {
        s1 = "" + segstart[1];
        s2 = "" + (str.length() - segstart[1]);
      }
      else if (segstart.length == 3) {
        s1 = "" + segstart[1];
        s3 = "" + (segstart[2] - segstart[1]);
        s2 = "" + (str.length() - segstart[2]);
      }
    }
    modePositionButton.setLabel(s1);
    modePositionButton.setLabel2(s2);
    modePositionButton.setLabel3(s3);
  }
  
  public void jeliMouseMotionMove(int x, int y)
  {
    if (sp.getModeSegment()) {
      SegmentedPanelInfo info = sp.getInfo();
      if (maxSegments == 2) {
        setModePositionLabel0(x);
      } else if (maxSegments == 3) {
        if (segstart.length == 1) {
          setModePositionLabel0(x);
        } else if (segstart.length == 2) {
          setModePositionLabel1(x, info);
        } else {
          setModePositionLabel2(x, info);
        }
      } else {
        jeliMouseMotionExit();
      }
    } else {
      jeliMouseMotionExit();
    }
  }
  
  private void setModePositionLabel0(int x) {
    modePositionButton.setLabel3(null);
    modePositionButton.setLabel2("" + (size - x));
    modePositionButton.setLabel("" + x);
  }
  

  private void setModePositionLabel1(int x, SegmentedPanelInfo info)
  {
    if ((x == 0) || (x == str.length()))
      x = segstart[1];
    String s1; String s1; String s3; String s2; if (x == segstart[1]) {
      String s3 = null;
      String s2 = "" + (size - x);
      s1 = "" + x;
    } else { String s2;
      if (x < segstart[1]) {
        String s1 = "" + x;
        String s3 = "" + (segstart[1] - x);
        s2 = "" + (str.length() - segstart[1]);
      }
      else {
        s1 = "" + segstart[1];
        s3 = "" + (x - segstart[1]);
        s2 = "" + (str.length() - x);
      } }
    modePositionButton.setLabel(s1);
    modePositionButton.setLabel2(s2);
    modePositionButton.setLabel3(s3);
  }
  

  private void setModePositionLabel2(int x, SegmentedPanelInfo info)
  {
    if ((x == 0) || (x == segstart[1]) || (x == segstart[2]) || (x == str.length()))
    {
      jeliMouseMotionExit(); return; }
    String s2;
    String s1;
    String s3; String s2; if (x < segstart[1]) {
      String s1 = "" + x;
      String s3 = "" + (segstart[2] - x);
      s2 = "" + (str.length() - segstart[2]);
    } else { String s2;
      if (x > segstart[2]) {
        String s1 = "" + segstart[1];
        String s3 = "" + (x - segstart[1]);
        s2 = "" + (str.length() - x);
      } else { String s2;
        if (x - segstart[1] < segstart[2] - x) {
          String s1 = "" + x;
          String s3 = "" + (segstart[2] - x);
          s2 = "" + (str.length() - segstart[2]);
        }
        else {
          s1 = "" + segstart[1];
          s3 = "" + (x - segstart[1]);
          s2 = "" + (str.length() - x);
        } } }
    modePositionButton.setLabel(s1);
    modePositionButton.setLabel2(s2);
    modePositionButton.setLabel3(s3);
  }
}
