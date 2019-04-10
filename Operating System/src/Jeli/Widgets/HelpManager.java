package Jeli.Widgets;

import Jeli.Get.ButtonPopupCallBack;
import Jeli.Get.GetButtonPopup;
import Jeli.Utility;
import Jeli.UtilityIO;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.io.PrintStream;
import java.util.Vector;















































































public class HelpManager
  implements Runnable, ButtonPopupCallBack, JeliCanvasCallBack
{
  private boolean test_canvas = false;
  public static final String tempClassification = "Temporary";
  public boolean fastUpdate = false;
  public boolean buttonDebugFlag = false;
  private boolean displayed;
  private boolean erase_flag = false;
  
  private String msg;
  private HelpManagerCanvas hmc;
  private Font font;
  private Font tableFont;
  private FontMetrics fm;
  private boolean disabled_flag;
  private HelpManagerFrame par;
  private boolean test_flag;
  private JeliButtonCallBack bhc;
  protected int DefaultNormFontType = 0;
  protected int DefaultEnterFontType = 0;
  protected int DefaultPushFontType = 0;
  protected int DefaultNormBorderType = 15;
  protected int DefaultEnterBorderType = 15;
  protected int DefaultPushBorderType = 15;
  protected int DefaultNormReverseType = 0;
  protected int DefaultEnterReverseType = 0;
  protected int DefaultPushReverseType = 1;
  protected int DefaultPushClickType = 1;
  protected int DefaultGoodAudioType = 1;
  protected int DefaultBadAudioType = 1;
  protected char highlight_delim = '\000';
  private String GeneralPropertiesString = "   General Properties";
  private String ShowAllButtonsString = "  Show All Buttons";
  private Color HighlightColor;
  protected DynamicHelpMessenger dhmg = null;
  private JeliStandardColorFrame jscf = null;
  private JeliNumberedColorFrame jncf = null;
  private boolean dynamicResizeFlag = false;
  
  Vector HelpMessages;
  
  final int init_width = 100;
  final int init_height = 50;
  final int max_width = 640;
  final int max_height = 470;
  AudioClip click_clip;
  AudioClip good_clip;
  AudioClip bad_clip;
  boolean inhibit_reverse_flag = false;
  boolean inhibit_click_flag = false;
  Vector ButtonList;
  String[] ButtonClassificationList;
  int default_border_type = 15;
  private GetButtonPopup GeneralPopup;
  private String[] BorderPopupList = { "None", "Top", "Bottom", "Top Bottom", "Left", "Left Top", "Left Bottom", "Left Top Bottom", "Right", "Right Top", "Right Bottom", "Right Top Bottom", "Right Left", "Right Left Top", "Right Left Bottom", "Box" };
  



  private Color DefaultForeground = Color.black;
  private Color DefaultBackground = Color.white;
  private Thread helpManagerThread = null;
  private int disableCount = 0;
  private Color[] colorList;
  private Color[] defaultColorList;
  public static String[] colorListNames = { "Quit Color", "Abort Color", "Log Color", "Log Image", "Medium Red", "Red", "Green", "Blue", "Cyan", "Magenta", "Yellow", "Grey 1", "Grey 2", "Grey 3" };
  


  private static Color COLOR_QUIT_DEFAULT = Utility.jeliLightRed;
  private static Color COLOR_ABORT_DEFAULT = Color.pink;
  private static Color COLOR_LOG_DEFAULT = Utility.jeliVeryLightCyan;
  private static Color COLOR_LOG_IMAGE_DEFAULT = Utility.jeliLightRed;
  private static Color COLOR_MEDIUM_RED_DEFAULT = Utility.jeliLightRed;
  private static Color COLOR_LIGHT_RED_DEFAULT = Utility.jeliVeryLightRed;
  private static Color COLOR_LIGHT_GREEN_DEFAULT = Utility.jeliVeryLightGreen;
  private static Color COLOR_LIGHT_BLUE_DEFAULT = Utility.jeliVeryLightBlue;
  private static Color COLOR_LIGHT_CYAN_DEFAULT = Utility.jeliVeryLightCyan;
  private static Color COLOR_LIGHT_MAGENTA_DEFAULT = Utility.jeliVeryLightMagenta;
  
  private static Color COLOR_LIGHT_YELLOW_DEFAULT = Utility.jeliVeryLightYellow;
  private static Color COLOR_GRAY_1_DEFAULT = Utility.jeliVeryLightGray;
  private static Color COLOR_GRAY_2_DEFAULT = Utility.jeliLightGray;
  
  private static Color COLOR_GRAY_3_DEFAULT = Utility.jeliMediumGray;
  public static int COLOR_NUMBER_QUIT = 0;
  public static int COLOR_NUMBER_ABORT = 1;
  public static int COLOR_NUMBER_LOG = 2;
  public static int COLOR_NUMBER_LOG_IMAGE = 3;
  public static int COLOR_NUMBER_MEDIUM_RED = 4;
  public static int COLOR_NUMBER_LIGHT_RED = 5;
  public static int COLOR_NUMBER_LIGHT_GREEN = 6;
  public static int COLOR_NUMBER_LIGHT_BLUE = 7;
  public static int COLOR_NUMBER_LIGHT_CYAN = 8;
  public static int COLOR_NUMBER_LIGHT_MAGENTA = 9;
  public static int COLOR_NUMBER_LIGHT_YELLOW = 10;
  public static int COLOR_NUMBER_GRAY_1 = 11;
  public static int COLOR_NUMBER_GRAY_2 = 12;
  public static int COLOR_NUMBER_GRAY_3 = 13;
  public static int COLOR_LIST_SIZE = 14;
  
  public HelpManager(String title, Font font, JeliButtonCallBack bhc) {
    this.font = font;
    tableFont = font;
    this.bhc = bhc;
    test_flag = true;
    displayed = false;
    ButtonList = new Vector();
    colorList = new Color[COLOR_LIST_SIZE];
    defaultColorList = new Color[COLOR_LIST_SIZE];
    defaultColorList[COLOR_NUMBER_QUIT] = COLOR_QUIT_DEFAULT;
    defaultColorList[COLOR_NUMBER_ABORT] = COLOR_ABORT_DEFAULT;
    defaultColorList[COLOR_NUMBER_LOG] = COLOR_LOG_DEFAULT;
    defaultColorList[COLOR_NUMBER_LOG_IMAGE] = COLOR_LOG_IMAGE_DEFAULT;
    defaultColorList[COLOR_NUMBER_LIGHT_RED] = COLOR_LIGHT_RED_DEFAULT;
    defaultColorList[COLOR_NUMBER_MEDIUM_RED] = COLOR_MEDIUM_RED_DEFAULT;
    defaultColorList[COLOR_NUMBER_LIGHT_GREEN] = COLOR_LIGHT_GREEN_DEFAULT;
    defaultColorList[COLOR_NUMBER_LIGHT_BLUE] = COLOR_LIGHT_BLUE_DEFAULT;
    defaultColorList[COLOR_NUMBER_LIGHT_CYAN] = COLOR_LIGHT_CYAN_DEFAULT;
    defaultColorList[COLOR_NUMBER_LIGHT_MAGENTA] = COLOR_LIGHT_MAGENTA_DEFAULT;
    defaultColorList[COLOR_NUMBER_LIGHT_YELLOW] = COLOR_LIGHT_YELLOW_DEFAULT;
    defaultColorList[COLOR_NUMBER_GRAY_1] = COLOR_GRAY_1_DEFAULT;
    defaultColorList[COLOR_NUMBER_GRAY_2] = COLOR_GRAY_2_DEFAULT;
    defaultColorList[COLOR_NUMBER_GRAY_3] = COLOR_GRAY_3_DEFAULT;
    
    for (int i = 0; i < COLOR_LIST_SIZE; i++)
      colorList[i] = defaultColorList[i];
    if (test_canvas) {
      hmc = new HelpManagerCanvasTest(this);
    } else
      hmc = new HelpManagerCanvas(this);
    hmc.setInitialSize(300, 100);
    par = new HelpManagerFrame(title, hmc, this);
    fm = par.getFontMetrics(font);
    disabled_flag = true;
    
    click_clip = null;
    good_clip = null;
    bad_clip = null;
    HighlightColor = null;
    HelpMessages = new Vector();
    par.setDebugButtonLabel(buttonDebugFlag);
    helpManagerThread = new Thread(this);
    helpManagerThread.setName("Jeli Help Manager Thread");
    helpManagerThread.start();
  }
  
  public boolean setDefaultColors(Color[] defaultColorList, String[] colorListNames) {
    if (defaultColorList.length != colorListNames.length)
      return false;
    this.defaultColorList = defaultColorList;
    colorListNames = colorListNames;
    colorList = new Color[defaultColorList.length];
    for (int i = 0; i < colorList.length; i++)
      colorList[i] = defaultColorList[i];
    return true;
  }
  
  public Color getColor(int which) {
    return colorList[(which % colorList.length)];
  }
  
  public Color getDefaultColor(int which) {
    return defaultColorList[(which % colorList.length)];
  }
  
  public void setColor(int which, int red, int green, int blue) {
    if ((which < 0) || (which >= colorList.length))
      return;
    colorList[which] = new Color(red, green, blue);
  }
  
  public int getColorListSize() {
    return colorList.length;
  }
  

  public void setInitialSize(int w, int rows)
  {
    int h = par.getButtonHeight(rows);
    par.setSize(w, h);
  }
  
  public void setFont(Font font) {
    this.font = font;
    fm = par.getFontMetrics(font);
    tableFont = font;
  }
  
  public void setTableFont(Font tableFont) {
    this.tableFont = tableFont;
  }
  
  public void setDefaultCallBack(JeliButtonCallBack bhc) {
    this.bhc = bhc;
  }
  
  public void setHighlightDelim(char d) {
    highlight_delim = d;
  }
  
  public void setDynamicHelpMessenger(DynamicHelpMessenger mg) {
    dhmg = mg;
  }
  
  public Color getHighlightColor() {
    return HighlightColor;
  }
  
  public void setHighlightColor(Color c) {
    HighlightColor = c;
  }
  
  public void setErase(boolean f) {
    erase_flag = f;
  }
  
  public void setDefaultClickType(boolean f) {
    if (f) {
      DefaultPushClickType = 1;
    } else
      DefaultPushClickType = 0;
  }
  
  public void setDefaultGoodAudioType(boolean f) {
    if (f) {
      DefaultGoodAudioType = 1;
    } else
      DefaultGoodAudioType = 0;
  }
  
  public void setDefaultBadAudioType(boolean f) {
    if (f) {
      DefaultBadAudioType = 1;
    } else {
      DefaultBadAudioType = 0;
    }
  }
  
  public void addButton(JeliButton bh) {
    String cl = bh.getClassification();
    if (!"Temporary".equals(cl)) {
      ButtonList.addElement(bh);
    }
  }
  


  public void removeButton(JeliButton bh)
  {
    if (bh != null) {
      ButtonList.removeElement(bh);
    }
  }
  
  public int getButtonListSize() {
    return ButtonList.size();
  }
  
  public void setInhibitClick(boolean f) {
    inhibit_click_flag = f;
  }
  
  public void setInhibitReverse(boolean f) {
    inhibit_reverse_flag = f;
  }
  




  public void resetSizeIfNecessary(int w, int h)
  {
    if (!par.isVisible()) return;
    System.out.println("resetSizeIfNeccessary in HelpManager");
  }
  















  public void reValidate()
  {
    System.out.println("Help Manager reValidate called");
  }
  




  public void showHelpManager()
  {
    disableCount = 0;
    par.setVisible(true);
    disabled_flag = false;
  }
  
  public Font getFont()
  {
    return font;
  }
  
  public Font getTableFont() {
    return tableFont;
  }
  
  public boolean test() {
    return test_flag;
  }
  
  public void setTest(boolean flag) {
    test_flag = flag;
  }
  
  public void setAudioClick(AudioClip ac) {
    click_clip = ac;
  }
  
  public void setAudioGood(AudioClip ac) {
    good_clip = ac;
  }
  
  public void setAudioBad(AudioClip ac) {
    bad_clip = ac;
  }
  
  public void disableHelpManager()
  {
    disabled_flag = true;
    par.setVisible(false);
  }
  
  public void enableHelpManager()
  {
    disabled_flag = false;
  }
  








  public void show(Component com, String msg, int x, int y)
  {
    if (buttonDebugFlag) {
      return;
    }
    if (disabled_flag) return;
    if (disableCount > 0) {
      System.out.println("disableCount was " + disableCount);
      disableCount -= 1;
      if (disableCount == 4) {
        par.setVisible(false);
      } else if (disableCount == 3) {
        wakeUpThread();
      } else if (disableCount == 2)
        par.setVisible(true);
      return;
    }
    


    if (msg == null) return;
    hmc.setMessage(msg);
    hmc.repaint();
    displayed = true;
    wakeUpThread();
  }
  
  public void showDebugMessage(Component com, String msg)
  {
    if (com == par.debugButton)
    {
      buttonDebugFlag = false;
      par.setDebugButtonLabel(buttonDebugFlag);
      
      return;
    }
    hmc.setMessage(msg);
    hmc.repaint();
  }
  
  public void setDebugMode() {
    buttonDebugFlag = true;
    par.setDebugButtonLabel(buttonDebugFlag);
  }
  



  public void hide()
  {
    displayed = false;
    if (erase_flag) {
      hmc.setMessage("");
      hmc.repaint();
    }
  }
  
  public void run() {
    for (;;) {
      waitForActivity();
      checkForNewSize();
      hmc.repaint();
    }
  }
  
  public boolean getDynamicResize() {
    return dynamicResizeFlag;
  }
  
  private void checkForNewSize() {
    if (!dynamicResizeFlag)
      return;
    if (!par.checkSizes())
      return;
    par.fixSize();
  }
  
  public synchronized void wakeUpThread() {
    if (par.isVisible())
      notify();
  }
  
  private synchronized void waitForActivity() {
    try {
      wait();
    }
    catch (InterruptedException e) {}
  }
  


  private void setClassificationList()
  {
    Vector BCL = new Vector();
    for (int i = 0; i < ButtonList.size(); i++) {
      JeliButton bh = (JeliButton)ButtonList.elementAt(i);
      String cl = bh.getClassification();
      if ((!"Temporary".equals(cl)) && (!BCL.contains(cl)))
        BCL.addElement(bh.getClassification());
    }
    ButtonClassificationList = new String[BCL.size() + 2];
    for (int i = 0; i < BCL.size(); i++) {
      ButtonClassificationList[i] = ((String)BCL.elementAt(i));
    }
    ButtonClassificationList[BCL.size()] = GeneralPropertiesString;
    ButtonClassificationList[(BCL.size() + 1)] = ShowAllButtonsString;
    Utility.sortStringArray(ButtonClassificationList);
  }
  
  public void canvasDebugFound()
  {
    setClassificationList();
    



    new GetButtonPopup("Button Classifications", 0, ButtonClassificationList, 40, this, true, true, this, hmc).showit();
  }
  













  private void PopupButtons(String label)
  {
    int count = 0;
    for (int i = 0; i < ButtonList.size(); i++) {
      JeliButton bh = (JeliButton)ButtonList.elementAt(i);
      if (label.equals(bh.getClassification()))
        count++;
    }
    if (count == 0) return;
    String[] bl = new String[count];
    int which = 0;
    for (int i = 0; i < ButtonList.size(); i++) {
      JeliButton bh = (JeliButton)ButtonList.elementAt(i);
      if (label.equals(bh.getClassification())) {
        bl[which] = bh.getLabel();
        which++;
        if (which >= count) break;
      }
    }
    new GetButtonPopup("Buttons: " + label, 1, bl, 40, this, true, true, this, hmc).showit();
  }
  

  private void setGeneralProperties()
  {
    String[] GeneralArray = new String[13];
    if (DefaultPushClickType == 1) {
      GeneralArray[0] = "Click On";
    } else
      GeneralArray[0] = "Click Off";
    if (DefaultGoodAudioType == 1) {
      GeneralArray[1] = "Good Audio On";
    } else
      GeneralArray[1] = "Good Audio Off";
    if (DefaultBadAudioType == 1) {
      GeneralArray[2] = "Bad Audio On";
    } else
      GeneralArray[2] = "Bad Audio Off";
    if (DefaultNormReverseType == 1) {
      GeneralArray[3] = "Norm Reverse On";
    } else
      GeneralArray[3] = "Norm Reverse Off";
    if (DefaultEnterReverseType == 1) {
      GeneralArray[4] = "Enter Reverse On";
    } else
      GeneralArray[4] = "Enter Reverse Off";
    if (DefaultPushReverseType == 1) {
      GeneralArray[5] = "Push Reverse On";
    } else
      GeneralArray[5] = "Push Reverse Off";
    if (DefaultNormFontType == 0) {
      GeneralArray[6] = "Norm Font Plain";
    } else
      GeneralArray[6] = "Norm Font Bold";
    if (DefaultEnterFontType == 0) {
      GeneralArray[7] = "Enter Font Plain";
    } else
      GeneralArray[7] = "Enter Font Bold";
    if (DefaultPushFontType == 0) {
      GeneralArray[8] = "Push Font Norm";
    } else
      GeneralArray[8] = "Push Font Bold";
    GeneralArray[9] = ("Norm Border: " + BorderTypeToString(DefaultNormBorderType));
    
    GeneralArray[10] = ("Enter Border: " + BorderTypeToString(DefaultEnterBorderType));
    
    GeneralArray[11] = ("Push Border: " + BorderTypeToString(DefaultPushBorderType));
    
    GeneralArray[12] = "Set Remote Host";
    
    GeneralPopup = new GetButtonPopup("Default Button Operations", 2, GeneralArray, 40, this, true, true, this, hmc);
    

    GeneralPopup.showit();
  }
  
  private String BorderTypeToString(int type)
  {
    String str = "";
    if (type == 15) return "Box";
    if (type == 0) return "None";
    if ((type & 0x1) != 0) str = str + "Top ";
    if ((type & 0x2) != 0) str = str + "Bottom ";
    if ((type & 0x4) != 0) str = str + "Left ";
    if ((type & 0x8) != 0) str = str + "Right ";
    return str;
  }
  
  private void showAllButtons()
  {
    StringBuffer sb = new StringBuffer();
    

    sb.append("Classification\tLabel(" + ButtonList.size() + ")\t\n");
    for (int i = 0; i < ButtonList.size(); i++) {
      JeliButton bh = (JeliButton)ButtonList.elementAt(i);
      sb.append(bh.getTabbedDescriptor() + "\n");
    }
    JeliStaticFrame jsf = new JeliStaticFrame(0, 2, 20, 30, 100, "All Buttons", sb.toString(), null, null);
    
    jsf.setNumTabs(3);
    jsf.setNoScrollTop(1);
    jsf.disposeOnHide();
  }
  
  public void buttonPopupFound(String name, int id, int ind, String label)
  {
    if (id == 0)
    {
      if (label.equals(GeneralPropertiesString)) {
        System.out.println("Changing General Properties");
        setGeneralProperties();
        return;
      }
      if (label.equals(ShowAllButtonsString)) {
        showAllButtons();
        return;
      }
      PopupButtons(label);
    }
    else if (id == 1)
    {
      JeliButton bh = FindButtonFromClassification(name.substring(9), ind);
      

      if (bh != null) bh.showInfo();
    }
    else if (id == 2)
    {
      if ("Set Remote Host".equals(label)) {
        UtilityIO.setRemoteHost("vip.cs.utsa.edu", 20010);
      }
      if ("Click On".equals(label)) {
        GeneralPopup.setLabel(ind, "Click Off");
        DefaultPushClickType = 0;
      }
      else if ("Click Off".equals(label)) {
        GeneralPopup.setLabel(ind, "Click On");
        DefaultPushClickType = 1;
      }
      if ("Good Audio On".equals(label)) {
        GeneralPopup.setLabel(ind, "Good Audio Off");
        DefaultGoodAudioType = 0;
      }
      else if ("Good Audio Off".equals(label)) {
        GeneralPopup.setLabel(ind, "Good Audio On");
        DefaultGoodAudioType = 1;
      }
      if ("Bad Audio On".equals(label)) {
        GeneralPopup.setLabel(ind, "Bad Audio Off");
        DefaultBadAudioType = 0;
      }
      else if ("Bad Audio Off".equals(label)) {
        GeneralPopup.setLabel(ind, "Bad Audio On");
        DefaultBadAudioType = 1;
      }
      if ("Norm Reverse On".equals(label)) {
        GeneralPopup.setLabel(ind, "Norm Reverse Off");
        DefaultNormReverseType = 0;
      }
      if ("Norm Reverse Off".equals(label)) {
        GeneralPopup.setLabel(ind, "Norm Reverse On");
        DefaultNormReverseType = 1;
      }
      if ("Enter Reverse On".equals(label)) {
        GeneralPopup.setLabel(ind, "Enter Reverse Off");
        DefaultEnterReverseType = 0;
      }
      if ("Enter Reverse Off".equals(label)) {
        GeneralPopup.setLabel(ind, "Enter Reverse On");
        DefaultEnterReverseType = 1;
      }
      if ("Push Reverse On".equals(label)) {
        GeneralPopup.setLabel(ind, "Push Reverse Off");
        DefaultPushReverseType = 0;
      }
      if ("Push Reverse Off".equals(label)) {
        GeneralPopup.setLabel(ind, "Push Reverse On");
        DefaultPushReverseType = 1;
      }
      if ("Norm Font Plain".equals(label)) {
        GeneralPopup.setLabel(ind, "Norm Font Bold");
        DefaultNormFontType = 1;
      }
      if ("Norm Font Bold".equals(label)) {
        GeneralPopup.setLabel(ind, "Norm Font Plain");
        DefaultNormFontType = 0;
      }
      if ("Enter Font Plain".equals(label)) {
        GeneralPopup.setLabel(ind, "Enter Font Bold");
        DefaultEnterFontType = 1;
      }
      if ("Enter Font Bold".equals(label)) {
        GeneralPopup.setLabel(ind, "Enter Font Plain");
        DefaultEnterFontType = 0;
      }
      if ("Push Font Plain".equals(label)) {
        GeneralPopup.setLabel(ind, "Push Font Bold");
        DefaultPushFontType = 1;
      }
      if ("Push Font Bold".equals(label)) {
        GeneralPopup.setLabel(ind, "Push Font Plain");
        DefaultPushFontType = 0;
      }
      if (label.startsWith("Norm Border:")) {
        new GetButtonPopup("Norm Border", 3, BorderPopupList, 40, this, true, true, this, GeneralPopup.getTopComponent()).showit();
      }
      

      if (label.startsWith("Enter Border:")) {
        new GetButtonPopup("Enter Border", 4, BorderPopupList, 40, this, true, true, this, GeneralPopup.getTopComponent()).showit();
      }
      

      if (label.startsWith("Push Border:")) {
        new GetButtonPopup("Push Border", 5, BorderPopupList, 40, this, true, true, this, GeneralPopup.getTopComponent()).showit();
      }
      

    }
    else if (id == 3)
    {
      DefaultNormBorderType = ind;
      GeneralPopup.setLabel(9, "Norm Border: " + BorderTypeToString(DefaultNormBorderType));


    }
    else if (id == 4)
    {
      DefaultEnterBorderType = ind;
      GeneralPopup.setLabel(10, "Enter Border: " + BorderTypeToString(DefaultEnterBorderType));


    }
    else if (id == 5)
    {
      DefaultPushBorderType = ind;
      GeneralPopup.setLabel(11, "Push Border: " + BorderTypeToString(DefaultPushBorderType));
    }
  }
  

  private void GetBox(int n)
  {
    new GetButtonPopup("Choose Border", n, BorderPopupList, 40, this, true, true, this, hmc).showit();
  }
  


  private JeliButton FindButtonFromClassification(String label, int ind)
  {
    int which = 0;
    for (int i = 0; i < ButtonList.size(); i++) {
      JeliButton bh = (JeliButton)ButtonList.elementAt(i);
      if (label.equals(bh.getClassification())) {
        if (which == ind) return bh;
        which++;
      } }
    return null;
  }
  







  public int getDefaultBorderType()
  {
    return default_border_type;
  }
  
  public void setDefaultBorderType(int t) {
    default_border_type = t;
  }
  
  public String lookupDynamicMessage(String msg_name, int type) {
    return HelpMessage.lookup(HelpMessages, msg_name, type);
  }
  

  public void readMessageFile(String filename)
  {
    String str = UtilityIO.readEntireFile(filename, false);
    if (str == null) { return;
    }
    HelpMessage.parseAll(str, HelpMessages);
  }
  
  public Color getDefaultForeground() {
    return DefaultForeground;
  }
  
  public Color getDefaultBackground() {
    return DefaultBackground;
  }
  
  public void setDefaultForeground(Color c) {
    DefaultForeground = c;
  }
  
  public void setDefaultBackground(Color c) {
    DefaultBackground = c;
  }
  
  public void jeliCanvasCallback(String str)
  {
    if (str.equals("ButtonInfo")) {
      canvasDebugFound();
    } else if (str.equals("ButtonDebug")) {
      buttonDebugFlag = true;
      par.setDebugButtonLabel(buttonDebugFlag);
      par.setupDebug();

    }
    else if (str.equals("ShowColorComs")) {
      for (int i = 0; i < 7; i++)
        jeliCanvasCallback("ShowColorCom", i);
    } else if (str.equals("DynamicOn")) {
      dynamicResizeFlag = true;
    } else if (str.equals("DynamicOff")) {
      dynamicResizeFlag = false;
    }
  }
  
  public void jeliCanvasCallback(String str, String str1) {}
  
  public void jeliCanvasCallback(String str, int val) { if (str.equals("ShowColorCom")) {
      for (int i = 0; i < 4; i++)
        jeliCanvasCallback("ShowColorCom2", val, i);
    }
  }
  
  public void jeliCanvasCallback(String str, int val1, int val2) {
    System.out.println("Colored objects for type " + val1 + " and degree " + val2 + "\n" + Utility.getColoredObjects(val1, val2, "  "));
  }
  

  public void showColorFrame()
  {
    if (Utility.hm == null)
      return;
    if (jscf != null) {
      jscf.setVisible(true);
    } else
      jscf = new JeliStandardColorFrame();
  }
  
  public void showNumberedColorFrame() {
    if (Utility.hm == null)
      return;
    if (jncf != null) {
      jncf.setVisible(true);
    } else {
      jncf = new JeliNumberedColorFrame();
    }
  }
  
  public void resetButtonBackgrounds() {
    for (int i = 0; i < ButtonList.size(); i++) {
      JeliButton bh = (JeliButton)ButtonList.elementAt(i);
      bh.resetBackground();
    }
  }
  
  protected JeliButtonCallBack getCallBack() {
    return bhc;
  }
}
