package Jeli.Widgets;

import Jeli.Get.GetButtonPopup;
import Jeli.Utility;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Vector;

public class JeliButton extends JeliPanel implements Jeli.Get.ButtonPopupCallBack, JeliButtonSimulator, Jeli.Logging.LogState, JeliRefreshable
{
  private String report;
  protected HelpManager hm;
  private String msg;
  private String label;
  private JeliButtonCanvas hc;
  public static int TYPE_DEFAULT = -1;
  protected int NormFontType = TYPE_DEFAULT;
  protected int EnterFontType = TYPE_DEFAULT;
  protected int PushFontType = TYPE_DEFAULT;
  protected int NormBorderType = TYPE_DEFAULT;
  protected int EnterBorderType = TYPE_DEFAULT;
  protected int PushBorderType = TYPE_DEFAULT;
  protected int NormReverseType = TYPE_DEFAULT;
  protected int EnterReverseType = TYPE_DEFAULT;
  protected int PushReverseType = TYPE_DEFAULT;
  protected int PushClickType = TYPE_DEFAULT;
  protected int GoodAudioType = TYPE_DEFAULT;
  protected int BadAudioType = TYPE_DEFAULT;
  protected JeliButtonCallBack bhc = null;
  
  public static final int REVERSE_TYPE_OFF = 0;
  
  public static final int REVERSE_TYPE_ON = 1;
  
  public static final int FONT_TYPE_NORM = 0;
  
  public static final int FONT_TYPE_BOLD = 1;
  
  public static final int TYPE_NORM = 0;
  
  public static final int TYPE_BORDER = 1;
  
  public static final int TYPE_BOLD = 2;
  
  public static final int TYPE_BOLD_BORDER = 3;
  public static final int CLICK_OFF = 0;
  public static final int CLICK_ON = 1;
  public static final int AUDIO_OFF = 0;
  public static final int AUDIO_ON = 1;
  public static final int BORDER_TYPE_DEFAULT = -1;
  public static final int BORDER_TYPE_NONE = 0;
  public static final int BORDER_TYPE_TOP = 1;
  public static final int BORDER_TYPE_BOTTOM = 2;
  public static final int BORDER_TYPE_LEFT = 4;
  public static final int BORDER_TYPE_RIGHT = 8;
  public static final int BORDER_TYPE_BOX = 15;
  public static final int BORDER_TYPE_TOP_BOTTOM = 3;
  public static final int BORDER_TYPE_NO_LEFT = 11;
  public static final int BORDER_TYPE_NO_RIGHT = 7;
  public static final int BORDER_TYPE_NO_SIDES = 3;
  public static final int BORDER_TYPE_NO_TOP = 14;
  public static final int BORDER_TYPE_NO_BOTTOM = 13;
  public static final int BORDER_TYPE_LEFT_RIGHT = 12;
  private String classification_string = null;
  private String[] InfoButtons = { "Increase Font Size", "Decrease Font Size", "", "Reverse", "Test" };
  

  protected boolean setsize_flag = false;
  protected boolean setsize_label_flag = false;
  protected int setsize_width;
  protected int setsize_height = -1;
  private DynamicHelpMessenger dhmg = null;
  private String messagename = null;
  private int messagetype = -3;
  private int debug_id = -1;
  private DebugRegistration dr;
  private int info = -1;
  private Object object_info = null;
  private JeliButtonHandler jbh;
  private Vector updateList;
  private int backgroundColorType = -1;
  private int backgroundColorDegree = -1;
  private Component colorLinkedComponent = null;
  private static boolean traceButtons = false;
  private JeliButtonEnterCallBack enterCallback = null;
  
  public JeliButton(String label, SimpleJeliButtonCallBack sjbc) {
    this(label, Utility.hm, null);
    jbh = new JeliButtonHandler(sjbc);
    resetJeliButtonCallBack(jbh);
  }
  
  public JeliButton(String label, HelpManager hm, JeliButtonCallBack bhc) {
    this(label, hm);
    this.bhc = bhc;
  }
  
  public JeliButton(String label, JeliButtonCallBack bhc) {
    this(label, Utility.hm, bhc);
  }
  
  public JeliButton(String label) {
    this(label, null, null);
  }
  
  public JeliButton(String label, HelpManager nhm)
  {
    if (nhm == null) {
      hm = Utility.hm;
    }
    else {
      hm = nhm;
    }
    msg = label;
    this.label = label;
    setsize_flag = false;
    setLayout(new java.awt.GridLayout(1, 1));
    add(this.hc = new JeliButtonCanvas(label, hm.getFont(), this));
    hc.setHighlightColor(hm.getHighlightColor());
    hm.addButton(this);
    bhc = hm.getCallBack();
    dhmg = hm.dhmg;
    updateList = new Vector();
  }
  
  public void setDynamicHelpMessenger(DynamicHelpMessenger mg) {
    dhmg = mg;
  }
  
  public void setMessageName(String s) {
    messagename = s;
  }
  
  public void setMessageName(String s, int t) {
    messagename = s;
    messagetype = t;
  }
  
  public void setMessageType(int t) {
    messagetype = t;
  }
  
  public void setClickType(boolean f) {
    if (f) {
      PushClickType = 1;
    }
    else {
      PushClickType = 0;
    }
  }
  
  public void setClickType() {
    PushClickType = TYPE_DEFAULT;
  }
  
  public boolean getInhibitReverse() {
    return hm.inhibit_reverse_flag;
  }
  

  public void setCallbackDelay(String lab)
  {
    hc.setCallbackDelay(lab);
  }
  
  public void resetState() {
    hc.resetState();
  }
  
  public boolean getInhibitClick() {
    return hm.inhibit_click_flag;
  }
  
  public void setClick(boolean f) {
    if (f) {
      hc.setClick(hm.click_clip);
    }
    else {
      hc.setClick(null);
    }
  }
  
  public void setReverse(boolean f) {
    hc.setReverse(f);
  }
  
  public void setInhibitReverse(boolean f) {
    hc.setInhibitReverse(f);
  }
  
  public void setPositionLeft() {
    hc.setPositionLeft();
  }
  
  public void setPositionRight() {
    hc.setPositionRight();
  }
  
  public void setPositionCenter() {
    hc.setPositionCenter();
  }
  
  public void setPosition2Left() {
    hc.setPosition2Left();
  }
  
  public void setPosition2Right() {
    hc.setPosition2Right();
  }
  
  public void setPosition2Center() {
    hc.setPosition2Center();
  }
  
  public void setPosition3Left() {
    hc.setPosition3Left();
  }
  
  public void setPosition3Right() {
    hc.setPosition3Right();
  }
  
  public void setPosition3Center() {
    hc.setPosition3Center();
  }
  
  public void setAfterString(String s) {
    hc.setAfterString(s);
  }
  
  public void setButtonSize(int w) {
    setsize_width = w;
    setsize_flag = true;
  }
  
  public void setButtonSize(int w, int h) {
    setsize_width = w;
    setsize_height = h;
    setsize_flag = true;
  }
  
  public void setButtonSize() {
    setButtonSize(label);
    setsize_label_flag = true;
  }
  

  public void setButtonSize(String str)
  {
    Font f = hc.getButtonFont();
    FontMetrics fm = getFontMetrics(f);
    setsize_width = (fm.stringWidth(str) + 8);
    setsize_height = (fm.getMaxDescent() + fm.getMaxAscent() + 4);
    setsize_flag = true;
  }
  

  public void setMinimalButtonSize(String str)
  {
    Font f = hc.getButtonFont();
    FontMetrics fm = getFontMetrics(f);
    setsize_width = (fm.stringWidth(str) + 2);
    setsize_height = (fm.getMaxDescent() + fm.getMaxAscent() + 4);
    setsize_flag = true;
  }
  
  public int getButtonSetWidth() {
    return setsize_width;
  }
  

  public void setButtonSize(String str, int num)
  {
    Font f = hc.getButtonFont();
    FontMetrics fm = getFontMetrics(f);
    setsize_width = (num * fm.stringWidth(str) + 8);
    setsize_height = (fm.getMaxDescent() + fm.getMaxAscent() + 4);
    setsize_flag = true;
  }
  

  public void setButtonSize(String s, String str, int num)
  {
    Font f = hc.getButtonFont();
    FontMetrics fm = getFontMetrics(f);
    setsize_width = (fm.stringWidth(s) + num * fm.stringWidth(str) + 8);
    setsize_height = (fm.getMaxDescent() + fm.getMaxAscent() + 4);
    setsize_flag = true;
  }
  
  public void setBackground(Color c) {
    super.setBackground(c);
    hc.setBackground(c);
    hc.setColors();
  }
  

  public void setBackground(int which)
  {
    super.setBackground(hm.getColor(which));
    hc.setBackground(which);
  }
  
  public void setDraggable(java.awt.Frame f, Component com) {
    hc.setDraggable(f, com);
  }
  
  public void setAsLabel() {
    hc.setAsLabel(true);
  }
  
  public void setNoMouse(boolean f) {
    hc.setNoMouse(f);
  }
  
  public void setAsNotLabel() {
    hc.setAsLabel(false);
  }
  
  public void setAsGetNothing(String prompt) {
    hc.setAsGetNothing(prompt);
  }
  
  public void setAsGetInteger(boolean f) {
    hc.setAsGetInteger(f);
  }
  
  public void setAsGetString(boolean f) {
    hc.setAsGetString(f);
  }
  
  public void setAsGetDouble(boolean f) {
    hc.setAsGetDouble(f);
  }
  
  public void setValue(double val) {
    hc.setValue(val);
  }
  
  public void setValue(int val) {
    hc.setValue(val);
  }
  

  public void setAsGetInteger(String init_prompt, String get_value_prompt, String value_prompt, int value, boolean value_flag)
  {
    hc.setAsGetInteger(init_prompt, get_value_prompt, value_prompt, value, value_flag);
  }
  


  public void setAsGetDouble(String init_prompt, String get_value_prompt, String value_prompt, double value, boolean value_flag)
  {
    hc.setAsGetDouble(init_prompt, get_value_prompt, value_prompt, value, value_flag);
  }
  


  public void setAsGetString(String init_prompt, String get_value_prompt, String value_prompt, String value, boolean value_flag)
  {
    hc.setAsGetString(init_prompt, get_value_prompt, value_prompt, value, value_flag);
  }
  
  public void setAsGetWord()
  {
    hc.setAsGetWord();
  }
  
  public void resetGet() {
    hc.resetGet();
  }
  
  public void setGetColors(Color[] cols) {
    hc.setGetColors(cols);
  }
  
  public void setGetString(String lab) {
    hc.setGetString();
    setLabel(lab);
  }
  
  public void setGetString(String lab, String def) {
    hc.setGetString(def);
    setLabel(lab);
  }
  
  public void setGetInteger(String lab) {
    hc.setGetInteger();
    setLabel(lab);
  }
  
  public void setGetInteger(String lab, int val) {
    hc.setGetInteger(val);
    setLabel(lab);
  }
  
  public void setGetIntegerRange(int low, int high) {
    hc.setGetIntegerRange(low, high);
  }
  
  public void setGetIntegerMin(int low) {
    hc.setGetIntegerMin(low);
  }
  
  public void setGetDouble(String lab) {
    hc.setGetDouble();
    setLabel(lab);
  }
  
  public void setGetDouble(String lab, double val) {
    hc.setGetDouble(val);
    setLabel(lab);
  }
  
  public void setBorderType(int nt, int et, int pt) {
    NormBorderType = nt;
    EnterBorderType = et;
    PushBorderType = pt;
    hc.repaint();
  }
  
  public void setReverseType(int nt, int et, int pt) {
    NormReverseType = nt;
    EnterReverseType = et;
    PushReverseType = pt;
    hc.repaint();
  }
  
  public void setFontType(int nt, int et, int pt) {
    NormFontType = nt;
    EnterFontType = et;
    PushFontType = pt;
    hc.repaint();
  }
  
  public void setFontType(int t) {
    setFontType(t, t, t);
  }
  
  public void setBorderType(int t) {
    setBorderType(t, t, t);
  }
  
  public void setReverseType(int t) {
    setReverseType(t, t, t);
  }
  
  public void setDebugString(String str, int id, DebugRegistration dr) {
    debug_id = id;
    this.dr = dr;
    hc.setDebugString(str);
  }
  
  protected void debugFound() {
    dr.setDebugFound(debug_id);
  }
  
  public void resetJeliButtonCallBack(JeliButtonCallBack bhc) {
    this.bhc = bhc;
  }
  
  public void disableJeliButton() {
    super.setEnabled(false);
    hc.disableJeliButton();
  }
  
  public void enableJeliButton() {
    super.setEnabled(true);
    hc.enableJeliButton();
  }
  
  public String getLabel() {
    return hc.getLabel();
  }
  
  public String getLabel2() {
    return hc.getLabel2();
  }
  
  public String getLabel3() {
    return hc.getLabel3();
  }
  
  public void setHelpManager(HelpManager hm) {
    this.hm = hm;
  }
  
  public void setLabel(String str) {
    if ((hm.fastUpdate) && (str.equals(label))) {
      return;
    }
    label = str;
    hc.setCanvasLabel(str);
  }
  
  public void setLabel2(String str) {
    hc.setCanvasLabel2(str);
  }
  
  public void setLabel3(String str) {
    hc.setCanvasLabel3(str);
  }
  
  public void setMessage(HelpManager hm, String msg) {
    this.msg = msg;
    this.hm = hm;
    hm.hide();
  }
  
  public void setMessage(String msg) {
    this.msg = msg;
    hm.hide();
  }
  
  public void panelMouseEnter(int x, int y) {
    String msg1 = null;
    if (enterCallback != null)
      enterCallback.jeliButtonEntered(this);
    if (messagename != null) {
      msg1 = hm.lookupDynamicMessage(messagename, messagetype);
    }
    if (msg1 != null) {
      hm.show(this, msg1, x, y);
      return;
    }
    if (dhmg != null) {
      msg1 = dhmg.generateDynamicHelpMessage(this);
    }
    if (msg1 != null) {
      hm.show(this, msg1, x, y);
    }
    else {
      hm.show(this, msg, x, y);
    }
  }
  
  public void panelMouseExit(int x, int y) {
    if (enterCallback != null)
      enterCallback.jeliButtonExited(this);
    hm.hide();
  }
  
  public void setClassification(String str) {
    classification_string = str;
    if (str.equals("Temporary")) {
      hm.removeButton(this);
    }
  }
  
  public String getClassification() {
    if (classification_string != null) {
      return classification_string;
    }
    if ((bhc != null) && (bhc.getClassification() != null)) {
      return bhc.getClassification();
    }
    if ((hm.getCallBack() != null) && (hm.getCallBack().getClassification() != null))
    {
      return hm.getCallBack().getClassification();
    }
    return "Unclassified";
  }
  
  public String getShortDescriptor() {
    return getShortDescriptor(0);
  }
  
  public String getShortDescriptor(int n)
  {
    String str = getClassification();
    if (str == null) {
      str = "None";
    }
    while (str.length() < n) {
      str = str + " ";
    }
    return str + " " + getLabel();
  }
  
  public String getTabbedDescriptor()
  {
    String str = getClassification();
    if (str == null) {
      str = "None";
    }
    str = Utility.n_places_right(hc.getButtonCount(), 0, 3) + ": " + str;
    return str + "\t" + getLabel() + "\t";
  }
  
  public void increaseFontSize() {
    hc.increaseFontSize();
  }
  
  public void setFontSize(int n) {
    hc.setFontSize(n);
  }
  
  public void decreaseFontSize() {
    hc.decreaseFontSize();
  }
  
  public Color getBackground() {
    return hc.getBackground();
  }
  
  public void setLabelArray(String str) {
    hc.setLabelArray(str);
  }
  
  public void setLabelArray2(String str) {
    hc.setLabelArray2(str);
  }
  
  public void setLabelArray3(String str) {
    hc.setLabelArray3(str);
  }
  
  public void setButtonHeight(int n) {
    hc.setButtonHeight(n);
  }
  
  public void setInfoButtons() {
    if (PushClickType == TYPE_DEFAULT) {
      InfoButtons[2] = "Click Default";
    }
    else if (PushClickType == 1) {
      InfoButtons[2] = "Click On";
    }
    else if (PushClickType == 0) {
      InfoButtons[2] = "Click Off";
    }
    else {
      InfoButtons[2] = "Click Unknown";
    }
  }
  

  public void showInfo()
  {
    setInfoButtons();
    GetButtonPopup pu = new GetButtonPopup(getLabel() + " Info", 0, InfoButtons, 0, hm, true, true, this, this);
    
    pu.showit();
  }
  
  public void buttonPopupFound(String name, int id, int ind, String label)
  {
    if (label.equals("Increase Font Size")) {
      increaseFontSize();
    }
    else if (label.equals("Decrease Font Size")) {
      decreaseFontSize();
    }
    else if (label.equals("Reverse")) {
      NormReverseType = 1;
      hc.repaint();



    }
    else
    {



      System.out.println("  Info Label Not Implemented");
    }
  }
  
  public void allReverse(boolean f)
  {
    if (f) {
      NormReverseType = 1;
      PushReverseType = 1;
      EnterReverseType = 1;
    }
    else {
      NormReverseType = TYPE_DEFAULT;
      PushReverseType = TYPE_DEFAULT;
      EnterReverseType = TYPE_DEFAULT;
    }
    hc.repaint();
  }
  
  public Dimension getPreferredSize()
  {
    if (!setsize_flag) {
      if (hc.usingLabelArray()) {
        Dimension ps = super.getPreferredSize();
        return new Dimension(hc.getLabelLength(), height);
      }
      return super.getPreferredSize();
    }
    if (setsize_label_flag) {
      setButtonSize(label);
    }
    if (hc.usingLabelArray())
      setsize_width = hc.getLabelLength();
    if (setsize_height > 0)
      return new Dimension(setsize_width, setsize_height);
    Dimension ps = super.getPreferredSize();
    return new Dimension(setsize_width, height);
  }
  
  public Dimension getMinimumSize() {
    if (!setsize_flag) {
      return super.getMinimumSize();
    }
    return getPreferredSize();
  }
  
  public void logStateChange(boolean open) {
    if (open) {
      enableJeliButton();
    }
    else {
      disableJeliButton();
    }
  }
  
  public void setForeground(Color c) {
    hc.setForeground(c);
    hc.setColors();
  }
  
  public void setFormattedLabel(String[] str, int[] pos) {
    hc.setFormattedLabel(str, pos);
  }
  
  public void setFormattedLabelColors(Color[] c) {
    hc.setFormattedLabelColors(c);
  }
  
  public void clearFormattedLabel() {
    hc.clearFormattedLabel();
  }
  
  public void setImage(java.awt.Image im) {
    hc.setImage(im);
  }
  
  public void forceImageBorder(boolean f) {
    hc.forceImageBorder(f);
  }
  
  public void toggleImageBorder() {
    hc.toggleImageBorder();
  }
  
  public boolean getImageBorder() {
    return hc.getImageBorder();
  }
  
  public void setInfo(int n) {
    info = n;
  }
  
  public int getInfo() {
    return info;
  }
  
  public void setObjectInfo(Object ob) {
    object_info = ob;
  }
  
  public Object getObjectInfo() {
    return object_info;
  }
  









  public void backgroundOnly(boolean f)
  {
    hc.backgroundOnly(f);
  }
  
  public void setLinked(boolean f) {
    hc.setLinked(f);
  }
  
  public void simulatePushed() {
    hc.simulatePushed();
  }
  
  public void addUpdate(JeliUpdate ju) {
    updateList.addElement(ju);
  }
  
  protected void updateAll()
  {
    if (updateList == null) {
      return;
    }
    for (int i = 0; i < updateList.size(); i++) {
      JeliUpdate ju = (JeliUpdate)updateList.elementAt(i);
      ju.updateDone(this);
    }
  }
  
  public void simulatePaint(java.awt.Graphics g) {
    hc.simulatePaint(g);
  }
  
  public java.awt.Rectangle getJeliButtonBounds() {
    return hc.getBounds();
  }
  
  public void setAsynchronous(boolean f) {
    hc.setAsynchronous(f);
  }
  
  public void simulatePushed(boolean f) {
    hc.simulatePushed(f);
  }
  
  public void setPushedLinked(boolean f) {
    hc.setPushedLinked(f);
  }
  
  public void resetBackground() {
    if (backgroundColorType < 0) {
      return;
    }
    Utility.setBackground(this, backgroundColorType, backgroundColorDegree);
    if (colorLinkedComponent != null) {
      Utility.setBackground(colorLinkedComponent, backgroundColorType, backgroundColorDegree);
    }
  }
  
  public void setColorLinkedComponent(Component com)
  {
    colorLinkedComponent = com;
  }
  
  public void setStandardBackground(int type, int degree) {
    backgroundColorType = type;
    backgroundColorDegree = degree;
    resetBackground();
  }
  
  public void setDemoReverseMode(boolean b) {
    hc.setDemoReverseMode(b);
  }
  
  public void setDemoReverse(boolean b) {
    hc.setDemoReverse(b);
  }
  
  public void toggleDemoReverse() {
    hc.toggleDemoReverse();
  }
  
  public void disposeButton() {
    hc.disposeButton();
  }
  
  public void refresh() {
    hc.repaint();
  }
  
  public void setDisplayCanvas(JeliCanvas can) {
    hc.setDisplayCanvas(can);
  }
  
  public int getMouseModifier() {
    return hc.getMouseModifier();
  }
  
  public int getClickCount() {
    return hc.getClickCount();
  }
  
  public void trace() {
    if (traceButtons) {
      Jeli.UtilityIO.debugOutput("Button " + label + " from " + Thread.currentThread());
    }
  }
  
  public static void setTraceButtons(boolean f) {
    traceButtons = f;
  }
  
  public static void toggleTraceButtons() {
    traceButtons = !traceButtons;
  }
  
  public static boolean getTraceButtons() {
    return traceButtons;
  }
  
  public void setBorderY(int n) {
    hc.setBorderY(n);
  }
  
  public void setButtonPainter(ButtonPainter bp) {
    hc.setButtonPainter(bp);
  }
  
  public void setCompressedArray(boolean f) {
    hc.setCompressedArray(f);
  }
  
  public void setEnterCallback(JeliButtonEnterCallBack cb) {
    enterCallback = cb;
  }
}
