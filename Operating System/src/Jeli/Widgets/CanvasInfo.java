package Jeli.Widgets;

import Jeli.Animation.TimeSlider;
import Jeli.Utility;
import java.awt.Component;
import java.awt.Panel;

public class CanvasInfo extends JeliFrame
{
  private JeliCanvas can;
  private JeliButton hideButton;
  private JeliButton sizeButton;
  private JeliButton positionButton;
  private JeliButton absolutePositionButton;
  private JeliLabel javaVersionLabel;
  private JeliLabel applicationVersionLabel;
  private JeliLabel applicationUpdateLabel;
  private JeliLabel jeliVersionLabel;
  private JeliLabel jeliNeededLabel;
  private JeliLabel osNameLabel;
  private JeliLabel colorDepthLabel;
  private JeliLabel screenSizeLabel;
  private JeliButton freeMemoryButton;
  private JeliButton totalMemoryButton;
  private JeliButton garbageCollectButton;
  private JeliButton traceButtonsButton;
  private JeliButton showColorFrameButton;
  private JeliButton showColorNumbersButton;
  private JeliButton showHelpButton;
  private JeliButton ioDebugButton;
  private JeliButton gifDebugButton;
  private JeliButton traceFillImageButton;
  private JeliButton traceFillImageAllButton;
  private JeliButton timePauseButton;
  private TimeSlider virtualTimeSlider;
  
  public CanvasInfo(String name, JeliCanvas can)
  {
    super(name);
    this.can = can;
    setupLayout();
    setSizeButton();
    pack();
  }
  
  private void setupLayout()
  {
    int numrows = 11;
    setBackground(java.awt.Color.white);
    setLayout(new java.awt.BorderLayout());
    setupWidgets();
    Panel centerPanel = new Panel();
    if (Utility.getVirtualTime() != null)
      numrows++;
    centerPanel.setLayout(new JeliGridLayout(numrows, 1));
    centerPanel.add(applicationVersionLabel);
    centerPanel.add(applicationUpdateLabel);
    centerPanel.add(jeliVersionLabel);
    centerPanel.add(jeliNeededLabel);
    centerPanel.add(buttonPanel(javaVersionLabel, osNameLabel));
    centerPanel.add(buttonPanel(sizeButton, positionButton, absolutePositionButton));
    
    centerPanel.add(buttonPanel(colorDepthLabel, screenSizeLabel));
    centerPanel.add(buttonPanel(freeMemoryButton, totalMemoryButton, garbageCollectButton));
    
    centerPanel.add(buttonPanel(showColorFrameButton, showColorNumbersButton, showHelpButton));
    
    centerPanel.add(buttonPanel(ioDebugButton, gifDebugButton, traceButtonsButton));
    
    centerPanel.add(buttonPanel(traceFillImageButton, traceFillImageAllButton));
    if (Utility.getVirtualTime() != null) {
      centerPanel.add(buttonPanel(virtualTimeSlider.getLabel(), virtualTimeSlider.getModifyButtons(), virtualTimeSlider.getSlider(), timePauseButton));
    }
    

    add(centerPanel, "Center");
    add(hideButton, "South");
  }
  
  private Panel buttonPanel(Component p1, Component p2) {
    Panel p = new Panel();
    p.setLayout(new JeliGridLayout(1, 2));
    p.add(p1);
    p.add(p2);
    return p;
  }
  
  private Panel buttonPanel(Component p1, Component p2, Component p3) {
    Panel p = new Panel();
    p.setLayout(new JeliGridLayout(1, 3));
    p.add(p1);
    p.add(p2);
    p.add(p3);
    return p;
  }
  
  private Panel buttonPanel(Component p1, Component p2, Component p3, Component p4)
  {
    Panel p = new Panel();
    p.setLayout(new JeliGridLayout(1, 4));
    p.add(p1);
    p.add(p2);
    p.add(p3);
    p.add(p4);
    return p;
  }
  



  private void setupWidgets()
  {
    java.awt.Toolkit tk = java.awt.Toolkit.getDefaultToolkit();
    java.awt.Dimension screen_size; int color_depth; java.awt.Dimension screen_size; if (tk == null) {
      int color_depth = 0;
      screen_size = new java.awt.Dimension(0, 0);
    }
    else {
      color_depth = tk.getColorModel().getPixelSize();
      screen_size = tk.getScreenSize();
    }
    Jeli.JeliApplicationVersion version = Utility.getAppVersion();
    hideButton = makeButton("Hide");
    sizeButton = makeButton("Size: xxxxx by xxxxx");
    positionButton = makeButton("Position: (xxxxx,xxxxx)");
    absolutePositionButton = makeButton("Absolute: (xxxxx,xxxxx)");
    javaVersionLabel = makeLabel("Java version: " + System.getProperty("java.version"));
    
    osNameLabel = makeLabel("OS: " + System.getProperty("os.name") + " version " + System.getProperty("os.version"));
    
    colorDepthLabel = makeLabel("Color Depth: " + color_depth);
    screenSizeLabel = makeLabel("Screen Size: " + width + " by " + height);
    
    if (version == null) {
      applicationVersionLabel = makeLabel("Unknown Application Version");
      applicationUpdateLabel = makeLabel("Unknown Update");
      jeliNeededLabel = makeLabel("Unknown Jeli Needed");
    }
    else {
      applicationVersionLabel = makeLabel(version.getName() + " version " + version.getVersionMessageShort());
      
      applicationUpdateLabel = makeLabel("Last update: " + version.getVersionMessageUpdate());
      
      jeliNeededLabel = makeLabel("Requires Jeli Version " + version.getMinimalJeliVersion());
    }
    
    jeliVersionLabel = makeLabel("Using Jeli 288 last updated March 1, 2007");
    
    freeMemoryButton = makeButton("Free Memory: xxxxxxxxxxx");
    totalMemoryButton = makeButton("Total Memory: xxxxxxxxxx");
    garbageCollectButton = makeButton("Garbage Collect");
    showColorFrameButton = makeButton("Show Color Frame");
    traceButtonsButton = makeButton("Trace Buttons: xxx");
    showHelpButton = makeButton("Show Help");
    ioDebugButton = makeButton("IO debug: xxx");
    gifDebugButton = makeButton("GIF debug: xxx");
    showColorNumbersButton = makeButton("Show Color Numbers");
    traceFillImageButton = makeButton("Trace Fill Image: xxx");
    traceFillImageAllButton = makeButton("Trace Fill Image All: xxx");
    setMemory();
    setTraceButtonsLabel();
    setIODebugButtonLabel();
    setGifDebugButtonLabel();
    setTraceFillImageButtonLabels();
    if (Utility.getVirtualTime() != null) {
      virtualTimeSlider = new TimeSlider(Utility.getVirtualTime(), -2);
      timePauseButton = new Jeli.Animation.TimePauseButton(Utility.getVirtualTime(), Utility.hm);
    }
  }
  

  private void garbageCollect()
  {
    Runtime rt = Runtime.getRuntime();
    if (rt != null)
      rt.gc();
    setMemory();
  }
  


  private void setMemory()
  {
    Runtime rt = Runtime.getRuntime();
    if (rt == null) {
      freeMemoryButton.setLabel("Free Memory: unknown");
      totalMemoryButton.setLabel("Total Memory: unknown");
    }
    else {
      long tm = rt.totalMemory();
      long fm = rt.freeMemory();
      freeMemoryButton.setLabel("Free Memory: " + fm);
      totalMemoryButton.setLabel("Total Memory: " + tm);
    }
  }
  
  private JeliButton makeButton(String s)
  {
    JeliButton but = new JeliButton(s, this);
    but.setBackground(java.awt.Color.white);
    return but;
  }
  
  private JeliLabel makeLabel(String s)
  {
    JeliLabel lab = new JeliLabel(s);
    lab.setBackground(java.awt.Color.white);
    return lab;
  }
  
  private void setSizeButton() {
    java.awt.Rectangle bounds = can.getBounds();
    
    sizeButton.setLabel("Size: " + width + " by " + height);
    java.awt.Point pos = Utility.getRelativeTopLevelPosition(can);
    positionButton.setLabel("Position: (" + x + "," + y + ")");
    pos = Utility.getAbsolutePosition(can);
    absolutePositionButton.setLabel("Absolute: (" + x + "," + y + ")");
  }
  
  private void setTraceButtonsLabel() {
    if (JeliButton.getTraceButtons()) {
      traceButtonsButton.setLabel("Trace Buttons: on");
    } else
      traceButtonsButton.setLabel("Trace Buttons: off");
  }
  
  private void setIODebugButtonLabel() {
    if (Jeli.UtilityIO.getDebugIO()) {
      ioDebugButton.setLabel("Debug IO: on");
    } else
      ioDebugButton.setLabel("Debug IO: off");
  }
  
  private void setGifDebugButtonLabel() {
    if (Jeli.Logging.GIFEncoder.getDebug()) {
      gifDebugButton.setLabel("Debug GIF: on");
    } else
      gifDebugButton.setLabel("Debug GIF: off");
  }
  
  private void setTraceFillImageButtonLabels() {
    setBooleanLabel(traceFillImageButton, "Trace Fill Image: ", can.getTraceFillImage());
    
    setBooleanLabel(traceFillImageAllButton, "Trace Fill Image All: ", JeliCanvas.getTraceFillImageAll());
  }
  
  private void toggleTraceFillImage()
  {
    can.setTraceFillImage(!can.getTraceFillImage());
    setTraceFillImageButtonLabels();
  }
  
  private void toggleTraceFillImageAll() {
    JeliCanvas.setTraceFillImageAll(!JeliCanvas.getTraceFillImageAll());
    setTraceFillImageButtonLabels();
  }
  
  private void setBooleanLabel(JeliButton but, String s, boolean f) {
    if (f) {
      but.setLabel(s + "on");
    } else
      but.setLabel(s + "off");
  }
  
  private void toggleIODebug() {
    Jeli.UtilityIO.setDebugIO(!Jeli.UtilityIO.getDebugIO());
    setIODebugButtonLabel();
  }
  
  private void toggleGifDebug() {
    Jeli.Logging.GIFEncoder.setDebug(!Jeli.Logging.GIFEncoder.getDebug());
    setGifDebugButtonLabel();
  }
  
  public void jeliButtonPushed(JeliButton but) {
    if (but == hideButton) {
      setVisible(false);
    } else if (but == sizeButton) {
      setSizeButton();
    } else if (but == freeMemoryButton) {
      setMemory();
    } else if (but == totalMemoryButton) {
      setMemory();
    } else if (but == garbageCollectButton) {
      garbageCollect();
    } else if (but == showColorFrameButton) {
      if (Utility.hm != null) {
        Utility.hm.showColorFrame();
      }
    } else if (but == traceButtonsButton) {
      JeliButton.toggleTraceButtons();
      setTraceButtonsLabel();
    }
    else if (but == showHelpButton) {
      if (Utility.hm != null) {
        Utility.hm.showHelpManager();
      }
    } else if (but == ioDebugButton) {
      toggleIODebug();
    } else if (but == gifDebugButton) {
      toggleGifDebug();
    } else if (but == showColorNumbersButton) {
      if (Utility.hm != null) {
        Utility.hm.showNumberedColorFrame();
      }
    } else if (but == traceFillImageButton) {
      toggleTraceFillImage();
    } else if (but == traceFillImageAllButton) {
      toggleTraceFillImageAll();
    }
  }
}
