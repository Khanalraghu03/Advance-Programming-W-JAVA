package Jeli.Widgets;

import Jeli.Utility;
import Jeli.UtilityIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Vector;

public class JeliCanvas extends JeliTransferCanvas
{
  private String currentString;
  private Vector simpleCheats;
  private Vector intCheats;
  private Vector int2Cheats;
  private Vector stringCheats;
  private Vector callbacks;
  private HelpNoteInfo hni = null;
  protected HelpNoteThread hnt;
  private ImageMaker imageMaker;
  private int preferredWidth = 0;
  private int preferredHeight = 0;
  private JeliMouseReceiver mpr = null;
  protected int w = -1;
  protected int h = -1;
  protected Image canvasImage = null;
  protected Graphics canvasGC = null;
  protected Graphics[] graphics;
  private Color[] graphicsColors;
  private JeliCanvasFiller filler = null;
  private static boolean tracePaintAll = false;
  private boolean tracePaint = false;
  private static boolean traceRepaintAll = false;
  private boolean traceRepaint = false;
  private static boolean traceFillImageAll = false;
  private boolean traceFillImage = false;
  private static boolean traceMakeImageAll = false;
  private boolean traceMakeImage = false;
  private static boolean traceDumpAll = false;
  private boolean traceDump = false;
  private String name = "JeliCanvas";
  private CanvasInfo canInfo = null;
  



  public JeliCanvas()
  {
    currentString = "";
    simpleCheats = new Vector();
    intCheats = new Vector();
    int2Cheats = new Vector();
    stringCheats = new Vector();
    callbacks = new Vector();
    addStringCheatCode("Today");
  }
  
  public void update(Graphics g) {
    paint(g);
  }
  
  public void setGraphicsSize(int n) {
    graphicsColors = new Color[n];
  }
  
  public void setGraphicsColor(int n, Color c) {
    graphicsColors[n] = c;
    makeGraphics();
  }
  
  public void traceD() {
    if ((traceDump) || (traceDumpAll)) {
      Thread.currentThread();Thread.dumpStack();
    }
  }
  
  public void traceP() { if ((tracePaint) || (tracePaintAll)) {
      UtilityIO.debugOutput(name + " paint from " + Thread.currentThread());
      traceD();
    }
  }
  
  public void traceR() {
    if ((traceRepaint) || (traceRepaintAll)) {
      UtilityIO.debugOutput(name + " repaint from " + Thread.currentThread());
      traceD();
    }
  }
  
  public void traceF() {
    if ((traceFillImage) || (traceFillImageAll)) {
      UtilityIO.debugOutput(name + " fillImage from " + Thread.currentThread());
      traceD();
    }
  }
  
  public void traceM() {
    if ((traceMakeImage) || (traceMakeImageAll))
      UtilityIO.debugOutput(name + " makeImage from " + Thread.currentThread());
    traceD();
  }
  
  public void repaint() {
    traceR();
    super.repaint();
  }
  
  public void repaint(long n) {
    traceR();
    super.repaint(n);
  }
  

  public void paint(Graphics g)
  {
    traceP();
    Rectangle bounds = getBounds();
    if ((h == -1) || (w == -1) || (w != width) || (h != height))
    {
      w = width;
      h = height;
      makeImage(w, h);
    }
    if (canvasImage == null)
      return;
    fillImage(w, h);
    g.drawImage(canvasImage, 0, 0, this);
  }
  
  public void setFiller(JeliCanvasFiller f) {
    filler = f;
  }
  
  protected void makeImage(int w, int h) {
    traceM();
    if (canvasImage != null)
      canvasImage.flush();
    canvasImage = createImage(w, h);
    if (canvasGC != null)
      canvasGC.dispose();
    canvasGC = canvasImage.getGraphics();
    makeGraphics();
  }
  
  private void makeGraphics() {
    if (canvasGC == null)
      return;
    if (graphicsColors == null)
      return;
    for (int i = 0; i < graphicsColors.length; i++)
      if (graphicsColors[i] == null)
        return;
    if (graphics == null) {
      graphics = new Graphics[graphicsColors.length];
    } else
      for (int i = 0; i < graphics.length; i++)
        if (graphics[i] != null) {
          graphics[i].dispose();
          graphics[i] = null;
        }
    for (int i = 0; i < graphicsColors.length; i++) {
      graphics[i] = canvasGC.create();
      graphics[i].setColor(graphicsColors[i]);
    }
  }
  
  protected void fillImage(int w, int h) {
    traceF();
    if (filler != null)
      filler.fillImage(w, h, canvasGC);
  }
  
  public void setHelpNoteInfo(HelpNoteInfo hni) {
    this.hni = hni;
    if (hnt != null)
      hnt.stopMe();
    if (hni != null) {
      hnt = new HelpNoteThread(this);
      imageMaker = new ImageMaker(this);
    }
  }
  
  public boolean getHelpState()
  {
    if (hnt == null) return false;
    return hnt.getRunning();
  }
  
  public void addCheatCode(String str) {
    simpleCheats.addElement(str);
  }
  
  public void addIntCheatCode(String str) {
    intCheats.addElement(str);
  }
  
  public void addInt2CheatCode(String str) {
    int2Cheats.addElement(str);
  }
  
  public void addStringCheatCode(String str) {
    stringCheats.addElement(str);
  }
  
  public void addCallback(JeliCanvasCallBack cb) {
    callbacks.addElement(cb);
  }
  
  public void jeliKeyPress(char c) {
    if ((c == '\n') || (c == '\r')) {
      handleLine();
      return;
    }
    if (c == '\b') {
      if (currentString.length() > 0)
        currentString = currentString.substring(1);
      return;
    }
    currentString += c;
  }
  
  public void jeliMouseExited() {
    if (hni != null)
      hnt.leave();
  }
  
  public void jeliMouseReleased(int x, int y, int mod, int count) {
    if (mpr == null)
      return;
    if ((mpr instanceof JeliMouseDragger)) {
      ((JeliMouseDragger)mpr).mouseReleased(x, y);
    } else
      mpr.mouseReleasedAt(x, y);
  }
  
  public void jeliMousePressed(int x, int y, int mod, int count) {
    if (mpr != null)
      mpr.mousePressedAt(x, y);
  }
  
  public void jeliMouseMoved(int x, int y) {
    if (hni != null)
      setMouseMoved(x, y);
  }
  
  public void jeliMouseDragged(int x, int y) {
    if ((mpr instanceof JeliMouseDragger)) {
      ((JeliMouseDragger)mpr).mouseDragged(x, y);
    }
  }
  
  private void setMouseMoved(int x, int y) {
    Point p = new Point(x, y);
    

    hnt.setPoint(p);
    repaint();
  }
  
  private void showInfoFrame() {
    if (canInfo == null)
      canInfo = new CanvasInfo(name, this);
    canInfo.setVisible(true);
  }
  





  private void handleLine()
  {
    String thisString = currentString;
    currentString = "";
    if (thisString.equals("ShowFrame")) {
      showInfoFrame();
      return;
    }
    if (thisString.equals("ShowAll")) {
      showCodes();
      return;
    }
    if (thisString.equals("TodaysNumber")) {
      showTodaysNumber();
      return;
    }
    if (thisString.equals("ShowMem")) {
      showMem();
      return;
    }
    if (thisString.equals("ShowHelp")) {
      if (Utility.hm != null)
        Utility.hm.showHelpManager();
      return;
    }
    if (thisString.equals("Garbage")) {
      garbageCollect();
      return;
    }
    if (thisString.equals("IODebug")) {
      UtilityIO.debugOutput("IODebug started");
      UtilityIO.setDebugIO(true);
      return;
    }
    if (thisString.equals("NoIODebug")) {
      UtilityIO.debugOutput("IODebug stopped");
      UtilityIO.setDebugIO(false);
      return;
    }
    if (thisString.equals("GifDebug")) {
      Jeli.Logging.GIFEncoder.setDebug(true);
      UtilityIO.debugOutput("gif encode debugging on");
      return;
    }
    if (thisString.equals("GifNoDebug")) {
      Jeli.Logging.GIFEncoder.setDebug(false);
      UtilityIO.debugOutput("gif encode debugging off");
      return;
    }
    if (thisString.equals("ColorFrame")) {
      if (Utility.hm != null)
        Utility.hm.showColorFrame();
      return;
    }
    if (thisString.equals("JeliColors")) {
      if (Utility.hm != null)
        Utility.hm.showNumberedColorFrame();
      return;
    }
    if (thisString.equals("Version")) {
      UtilityIO.debugOutput(Utility.getJavaVersion());
      UtilityIO.debugOutput("Jeli version L288 last updated March 1, 2007");
      
      UtilityIO.debugOutput(Utility.getAppVersionString());
      return;
    }
    if (thisString.equals("TraceButtons")) {
      JeliButton.toggleTraceButtons();
    }
    if (thisString.equals("TracePaint")) {
      toggleTracePaint();
    }
    if (thisString.equals("TracePaintAll")) {
      toggleTracePaintAll();
    }
    if (thisString.equals("TraceRepaint")) {
      toggleTraceRepaint();
    }
    if (thisString.equals("TraceRepaintAll")) {
      toggleTraceRepaintAll();
    }
    if (thisString.equals("TraceFillImageAll")) {
      toggleTraceFillImageAll();
    }
    if (thisString.equals("TraceFillImage")) {
      toggleTraceFillImage();
    }
    if (thisString.equals("TraceMakeImageAll")) {
      toggleTraceMakeImageAll();
    }
    if (thisString.equals("TraceMakeImage")) {
      toggleTraceMakeImage();
    }
    if (thisString.equals("TraceDumpAll")) {
      toggleTraceDumpAll();
    }
    if (thisString.equals("TraceDump")) {
      toggleTraceDump();
    }
    for (int i = 0; i < simpleCheats.size(); i++) {
      if (thisString.equals(simpleCheats.elementAt(i))) {
        for (int j = 0; j < callbacks.size(); j++)
          getCallback(j).jeliCanvasCallback(thisString);
        return;
      }
    }
    int spaceInd = thisString.lastIndexOf(" ");
    if (spaceInd == -1) return;
    String codeString = thisString.substring(0, spaceInd);
    String val1String = thisString.substring(spaceInd + 1);
    spaceInd = codeString.lastIndexOf(" ");
    if (spaceInd == -1) {
      handleLineOneParam(codeString, val1String);
      return;
    }
    String val2String = codeString.substring(spaceInd + 1);
    codeString = codeString.substring(0, spaceInd);
    handleLineTwoParams(codeString, val2String, val1String);
  }
  

  private void handleLineOneParam(String codeString, String valString)
  {
    for (int i = 0; i < intCheats.size(); i++) {
      if (codeString.equals(intCheats.elementAt(i))) {
        int val;
        try { val = Integer.parseInt(valString.trim());
        }
        catch (NumberFormatException e) {
          return;
        }
        for (int j = 0; j < callbacks.size(); j++)
          getCallback(j).jeliCanvasCallback(codeString, val);
        return;
      }
    }
    for (int i = 0; i < stringCheats.size(); i++) {
      if (codeString.equals(stringCheats.elementAt(i))) {
        for (int j = 0; j < callbacks.size(); j++)
          getCallback(j).jeliCanvasCallback(codeString, valString.trim());
        return;
      }
    }
  }
  



  private void handleLineTwoParams(String codeString, String val1String, String val2String)
  {
    for (int i = 0; i < int2Cheats.size(); i++) {
      if (codeString.equals(int2Cheats.elementAt(i))) { int val1;
        int val2;
        try { val1 = Integer.parseInt(val1String.trim());
          val2 = Integer.parseInt(val2String.trim());
        }
        catch (NumberFormatException e) {
          return;
        }
        for (int j = 0; j < callbacks.size(); j++)
          getCallback(j).jeliCanvasCallback(codeString, val1, val2);
        return;
      }
    }
  }
  
  private JeliCanvasCallBack getCallback(int i) {
    return (JeliCanvasCallBack)callbacks.elementAt(i);
  }
  
  private void showTodaysNumber() {
    UtilityIO.debugOutput("Today is " + new java.util.Date() + ": " + Utility.todaysDebugNumber());
  }
  
  private void showCodes() {
    UtilityIO.debugOutputLine();
    UtilityIO.debugOutput("Default: Version        ColorFrame     TraceButtons ShowFrame\n         ShowAll        ShowMem        ShowHelp     Garbage\n         GifDebug       GifNoDebug     IOdebug      IONoDebeg\n         TraceDump      TraceDumpAll   TodaysNumber\n         TracePaint     TracePaintAll  TraceRepaint TraceRepaintAll\n         TraceFillImage TraceMakeImage TraceFillImageAll TraceMakeImageAll\n");
    





    UtilityIO.debugOutput("simple has size " + simpleCheats.size());
    for (int i = 0; i < simpleCheats.size(); i++)
      UtilityIO.debugOutput("   " + simpleCheats.elementAt(i));
    UtilityIO.debugOutput("1 int  has size " + intCheats.size());
    for (int i = 0; i < intCheats.size(); i++)
      UtilityIO.debugOutput("   " + intCheats.elementAt(i));
    UtilityIO.debugOutput("2 ints has size " + int2Cheats.size());
    for (int i = 0; i < int2Cheats.size(); i++)
      UtilityIO.debugOutput("   " + int2Cheats.elementAt(i));
    UtilityIO.debugOutput("string has size " + stringCheats.size());
    for (int i = 0; i < stringCheats.size(); i++)
      UtilityIO.debugOutput("   " + stringCheats.elementAt(i));
    UtilityIO.debugOutputLine();
  }
  


  private void showMem()
  {
    Runtime rt = Runtime.getRuntime();
    if (rt == null) {
      UtilityIO.debugOutput("Error creating runtime to get memory info");
    } else {
      rt.gc();
      long tm = rt.totalMemory();
      long fm = rt.freeMemory();
      UtilityIO.debugOutput("Free memory: " + Utility.int2String((int)fm, 9));
      UtilityIO.debugOutput("Total memory:" + Utility.int2String((int)tm, 9));
    }
  }
  


  private void garbageCollect()
  {
    Runtime rt = Runtime.getRuntime();
    if (rt == null) {
      UtilityIO.debugOutput("Error creating runtime to get memory info");
    } else {
      long tm = rt.totalMemory();
      long fm = rt.freeMemory();
      UtilityIO.debugOutput("Before free memory: " + Utility.int2String((int)fm, 9));
      
      UtilityIO.debugOutput("Before total memory:" + Utility.int2String((int)tm, 9));
      
      rt.gc();
      tm = rt.totalMemory();
      fm = rt.freeMemory();
      UtilityIO.debugOutput("After free memory:  " + Utility.int2String((int)fm, 9));
      
      UtilityIO.debugOutput("After total memory: " + Utility.int2String((int)tm, 9));
    }
  }
  
  public Point getDisplayPoint()
  {
    if (hnt == null) return null;
    return hnt.getDisplayPoint();
  }
  
  public Image makeImage(String str) {
    if (imageMaker == null) return null;
    if (str == null) return null;
    return imageMaker.getImage(str);
  }
  




  public Point getImagePosition(Image im, Point p)
  {
    if (im == null) return null;
    if (p == null) return null;
    int w = im.getWidth(null);
    int h = im.getHeight(null);
    int cw = getBoundswidth;
    int ch = getBoundsheight;
    Point pos = new Point(x, y - h);
    if ((x + w <= cw) && (y >= 0)) return pos;
    x = (cw - w);
    if (y >= 0) return pos;
    x -= w;
    y = y;
    if ((x >= 0) && (y + h <= ch)) return pos;
    y = 0;
    if (x >= 0) { return pos;
    }
    x = 0;
    y = 0;
    return pos;
  }
  
  public void setPreferredSize(int w, int h) {
    preferredWidth = w;
    preferredHeight = h;
  }
  
  public java.awt.Dimension getPreferredSize() {
    if ((preferredWidth <= 0) || (preferredHeight <= 0))
      return super.getPreferredSize();
    return new java.awt.Dimension(preferredWidth, preferredHeight);
  }
  
  public void setMouseReceiver(JeliMouseReceiver mpr) {
    this.mpr = mpr;
  }
  
  public static void setTracePaintAll(boolean f) {
    tracePaintAll = f;
    UtilityIO.debugOutput("JeliCanvas tracePaintAll now " + tracePaintAll);
  }
  
  public void setTracePaint(boolean f) {
    tracePaint = f;
    UtilityIO.debugOutput("JeliCanvas tracePaint now " + tracePaint);
  }
  
  public static void setTraceRepaintAll(boolean f) {
    traceRepaintAll = f;
    UtilityIO.debugOutput("JeliCanvas traceRepaintAll now " + traceRepaintAll);
  }
  
  public void setTraceRepaint(boolean f) {
    traceRepaint = f;
    UtilityIO.debugOutput("JeliCanvas traceRepaint now " + traceRepaint);
  }
  
  public static void setTraceFillImageAll(boolean f) {
    traceFillImageAll = f;
    UtilityIO.debugOutput("JeliCanvas traceFillImageAll now " + traceFillImageAll);
  }
  
  public static boolean getTraceFillImageAll()
  {
    return traceFillImageAll;
  }
  
  public boolean getTraceFillImage() {
    return traceFillImage;
  }
  
  public void setTraceFillImage(boolean f) {
    traceFillImage = f;
    UtilityIO.debugOutput("JeliCanvas traceFillImage now " + traceFillImage);
  }
  
  public static void setTraceMakeImageAll(boolean f) {
    traceMakeImageAll = f;
    UtilityIO.debugOutput("JeliCanvas traceMakeImageAll now " + traceMakeImageAll);
  }
  
  public void setTraceMakeImage(boolean f)
  {
    traceMakeImage = f;
    UtilityIO.debugOutput("JeliCanvas traceMakeImage now " + traceMakeImage);
  }
  
  public static void setTraceDumpAll(boolean f) {
    traceDumpAll = f;
    UtilityIO.debugOutput("JeliCanvas traceDumpAll now " + traceDumpAll);
  }
  
  public void setTraceDump(boolean f) {
    traceDump = f;
    UtilityIO.debugOutput("JeliCanvas traceDump now " + traceDump);
  }
  
  public static void toggleTracePaintAll() {
    tracePaintAll = !tracePaintAll;
    UtilityIO.debugOutput("JeliCanvas tracePaintAll now " + tracePaintAll);
  }
  
  public void toggleTracePaint() {
    tracePaint = (!tracePaint);
    UtilityIO.debugOutput("JeliCanvas tracePaint now " + tracePaint);
  }
  
  public static void toggleTraceRepaintAll() {
    traceRepaintAll = !traceRepaintAll;
    UtilityIO.debugOutput("JeliCanvas traceRepaintAll now " + traceRepaintAll);
  }
  
  public void toggleTraceRepaint() {
    traceRepaint = (!traceRepaint);
    UtilityIO.debugOutput("JeliCanvas traceRepaint now " + traceRepaint);
  }
  
  public static void toggleTraceFillImageAll() {
    traceFillImageAll = !traceFillImageAll;
    UtilityIO.debugOutput("JeliCanvas traceFillImageAll now " + traceFillImageAll);
  }
  
  public void toggleTraceFillImage()
  {
    traceFillImage = (!traceFillImage);
    UtilityIO.debugOutput("JeliCanvas traceFillImage now " + traceFillImage);
  }
  
  public static void toggleTraceMakeImageAll() {
    traceMakeImageAll = !traceMakeImageAll;
    UtilityIO.debugOutput("JeliCanvas traceMakeImageAll now " + traceMakeImageAll);
  }
  
  public void toggleTraceMakeImage()
  {
    traceMakeImage = (!traceMakeImage);
    UtilityIO.debugOutput("JeliCanvas traceMakeImage now " + traceMakeImage);
  }
  
  public static void toggleTraceDumpAll() {
    traceDumpAll = !traceDumpAll;
    UtilityIO.debugOutput("JeliCanvas traceDumpAll now " + traceDumpAll);
  }
  
  public void toggleTraceDump() {
    traceDump = (!traceDump);
    UtilityIO.debugOutput("JeliCanvas traceDump now " + traceDump);
  }
  
  public void setName(String s) {
    name = s;
  }
}
