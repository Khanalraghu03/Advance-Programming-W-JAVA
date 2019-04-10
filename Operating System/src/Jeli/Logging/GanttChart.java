package Jeli.Logging;

import Jeli.Widgets.HelpManager;
import Jeli.Widgets.JeliButton;
import Jeli.Widgets.JeliColorButton;
import Jeli.Widgets.SliderFloat;
import Jeli.Widgets.SliderInteger;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Panel;
import java.util.Vector;

public class GanttChart extends Jeli.Widgets.JeliFrame implements Jeli.Widgets.SliderCallBack, SaveImageInfo, LogState, Jeli.Widgets.JeliColorButtonCallBack
{
  public Vector gantt_list;
  JeliButton hideit;
  JeliButton homeit;
  JeliButton reltime;
  JeliButton update;
  JeliButton LogButton;
  JeliButton ScaleButton;
  JeliButton HeightButton;
  JeliColorButton ColorButton;
  JeliButton controls;
  JeliButton SaveButton;
  protected GanttCanvas draw_canvas;
  int init_image_width = 600;
  int init_image_height = 300;
  boolean show_controls;
  Panel ButtonsPanel;
  Panel ControlsPanel;
  Panel control_panel;
  Panel draw_panel;
  SliderFloat MillisecondsPerPixel;
  SliderInteger BarHeight;
  SliderInteger BarInc;
  HelpManager hm;
  private String classification = null;
  double init_milliseconds_per_pixel = 10.0D;
  
  Logger logger;
  String Name;
  GanttControls control;
  
  public GanttChart(String Name, Vector gantt_list, Logger logger, HelpManager hm)
  {
    this(Name, gantt_list, logger, hm, 600, 300);
  }
  
  public GanttChart(String Name, Vector gantt_list, Logger logger, HelpManager hm, int w, int h)
  {
    super(Name);
    this.Name = Name;
    classification = (Name + ": " + "Gantt Chart");
    this.hm = hm;
    this.logger = logger;
    this.gantt_list = gantt_list;
    init_image_width = w;
    init_image_height = h;
    
    show_controls = false;
    setup_layout();
    makeslider();
    draw_canvas.set_ms(init_milliseconds_per_pixel);
    autoScale();
    control = new GanttControls("Controls for " + Name, draw_canvas, this, hm, controls);
  }
  
  public void setKey(String[] key)
  {
    draw_canvas.setKey(key);
  }
  

  void setup_layout()
  {
    hideit = new JeliButton("Hide", hm, this);
    draw_canvas = new GanttCanvas(gantt_list, this);
    draw_canvas.set_ms(init_milliseconds_per_pixel);
    draw_canvas.setSize(init_image_width, init_image_height);
    homeit = new JeliButton("Home", hm, this);
    reltime = new JeliButton("Relative", hm, this);
    LogButton = new JeliButton("Log", hm, this);
    LogButton.setCallbackDelay("Saving ...");
    ScaleButton = new JeliButton("Scale", hm, this);
    HeightButton = new JeliButton("Height", hm, this);
    HeightButton.setCallbackDelay("Height");
    ColorButton = new JeliColorButton("Color", -2, draw_canvas.color_list.length, null, hm, this);
    

    update = new JeliButton("Update", hm, this);
    

    controls = new JeliButton("Controls", hm, this);
    controls.setInhibitReverse(true);
    SaveButton = new JeliButton("Save", hm, this);
    setLayout(new java.awt.BorderLayout());
    ButtonsPanel = new Panel();
    ControlsPanel = new Panel();
    draw_panel = new Panel();
    draw_panel.setLayout(new GridLayout(1, 1));
    ControlsPanel.setLayout(new GridLayout(1, 1));
    ButtonsPanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 10));
    ButtonsPanel.add(hideit);
    

    ButtonsPanel.add(update);
    ButtonsPanel.add(reltime);
    ButtonsPanel.add(homeit);
    ButtonsPanel.add(ColorButton);
    ButtonsPanel.add(ScaleButton);
    ButtonsPanel.add(HeightButton);
    ButtonsPanel.add(controls);
    ButtonsPanel.add(SaveButton);
    ButtonsPanel.add(LogButton);
    ControlsPanel.add(ButtonsPanel);
    control_panel = new Panel();
    control_panel.setLayout(new GridLayout(1, 1));
    JeliButton B; control_panel.add(B = new JeliButton("testit", hm, this));
    add("South", ControlsPanel);
    draw_panel.add(draw_canvas);
    add("Center", draw_panel);
    pack();
  }
  



  void makeslider()
  {
    double ms = draw_canvas.get_ms();
    int fh = draw_canvas.get_frame_height();
    int fi = draw_canvas.get_frame_increment();
    MillisecondsPerPixel = new SliderFloat(1, (int)(1000.0D * ms), 1, 100000, 1000, 100, "time per pixel: ", 0, true, this, hm);
    

    MillisecondsPerPixel.setAsLabel();
    MillisecondsPerPixel.centerButton();
    BarHeight = new SliderInteger(1, fh, 1, 20, 100, "bar height: ", 0, true, this, hm);
    
    BarHeight.setAsLabel();
    BarHeight.centerButton();
    BarInc = new SliderInteger(1, fi, 0, 5, 100, "bar spacing: ", 0, true, this, hm);
    
    BarInc.setAsLabel();
    BarInc.centerButton();
  }
  
  void fix_layout() {
    ControlsPanel.removeAll();
    if (show_controls) {
      ControlsPanel.setLayout(new Jeli.Widgets.JeliGridLayout(4, 1));
      ControlsPanel.add(ButtonsPanel);
      
      ControlsPanel.add(MillisecondsPerPixel);
      ControlsPanel.add(BarHeight);
      ControlsPanel.add(BarInc);
    }
    else {
      ControlsPanel.setLayout(new GridLayout(1, 1));
      ControlsPanel.add(ButtonsPanel);
    }
    validate();
  }
  
  public void paint(Graphics g)
  {
    draw_canvas.repaint();
  }
  

  public void change_parameters()
  {
    if (MillisecondsPerPixel != null)
    {
      double ms = control.getMS();
      draw_canvas.set_ms(ms);
      
      int fh = control.getBH();
      draw_canvas.set_frame_height(fh);
      
      draw_canvas.set_frame_increment(control.getBI());
      draw_canvas.setKeyBarLength(control.getKBL());
      draw_canvas.setLabelGridSkip(control.getLGS());
      draw_canvas.setForceGridIncrement(control.getGI());
      draw_canvas.repaint();
    }
  }
  
  public void setMS(double ms)
  {
    control.setMS(ms);
  }
  
  public void setMax(double max)
  {
    control.setMax(max);
  }
  

  public void jeliButtonPushed(JeliButton bh)
  {
    String lab = bh.getLabel();
    if (bh == LogButton) {
      logger.logImage(draw_canvas.foreground, Name, this);
      return;
    }
    if (bh == ScaleButton) {
      autoScale();
      return;
    }
    if (bh == HeightButton) {
      setToHeight();
      draw_canvas.home();
      return;
    }
    if (handleButtonsByLabel(lab)) return;
    System.out.println("Unknown button " + lab);
  }
  
  private boolean handleButtonsByLabel(String str) {
    if (str.equals("Update")) {
      draw_canvas.first_paint_flag = true;
      draw_canvas.repaint();
      draw_canvas.focus();
      return true;
    }
    if (str.equals("Hide")) {
      setVisible(false);
      return true;
    }
    if (str.equals("Update")) {
      repaint();
      return true;
    }
    if (str.equals("Resize")) {
      draw_canvas.init_images();
      repaint();
      return true;
    }
    if (str.equals("Absolute")) {
      draw_canvas.relative_time_flag = true;
      reltime.setLabel("Relative");
      repaint();
      return true;
    }
    if (str.equals("Relative")) {
      draw_canvas.relative_time_flag = false;
      reltime.setLabel("Absolute");
      repaint();
      return true;
    }
    if (str.equals("Home")) {
      draw_canvas.home();
      return true;
    }
    if (str.equals("Save")) {
      System.out.println("Save pushed");
      new GifSaver(draw_canvas.foreground);
      return true;
    }
    if (str.equals("Controls"))
    {





      control.showIt();
      return true;
    }
    return false;
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void sliderchanged(int id, double val) { change_parameters(); }
  
  public void sliderchanged(int id) {}
  
  public String getClassification()
  {
    return classification;
  }
  
  public void setClassification(String str) {
    classification = str;
  }
  
  public void autoScale() {
    draw_canvas.autoScale();
    

    if (control != null) {
      control.setMS(draw_canvas.get_ms());
    }
  }
  

  public void logStateChange(boolean on)
  {
    if (on) {
      LogButton.enableJeliButton();
    } else {
      LogButton.disableJeliButton();
    }
  }
  
  public void saveImageFinished(boolean f) {
    LogButton.setLabel("Log");
  }
  
  public void jeliColorButtonSetColor(JeliColorButton cb, int ind, Color c) {
    draw_canvas.setColorEntry(ind - 1, c);
  }
  

  public void jeliColorButtonSetIndex(JeliColorButton cb, int ind) {}
  

  public void jeliColorButtonSettingIndex(JeliColorButton cb) {}
  
  public void forceGridIncrement(long inc)
  {
    draw_canvas.forceGridIncrement(inc);
  }
  
  public void labelGridSkip(int skip) {
    draw_canvas.labelGridSkip(skip);
  }
  
  public void repack() {
    setSmallButtons();
    pack();
  }
  
  public void forceMS(double ms) {
    setMS(ms);
    change_parameters();
    draw_canvas.noAutoScale();
  }
  
  public void setToSize(int n) {
    draw_canvas.setToSize(n);
  }
  
  public void setSmallButtons() {
    hideit.setButtonSize(3);
    update.setButtonSize(3);
    reltime.setButtonSize(3);
    homeit.setButtonSize(3);
    ColorButton.setButtonSize(3);
    ScaleButton.setButtonSize(3);
    controls.setButtonSize(3);
    SaveButton.setButtonSize(3);
    LogButton.setButtonSize(3);
    ButtonsPanel.invalidate();
    ButtonsPanel.validate();
  }
  
  public void setKeyBarLength(int n) {
    draw_canvas.setKeyBarLength(n);
  }
  
  public void setMono(boolean f) {
    draw_canvas.setMono(f);
  }
  
  public static Color getMonoColor(int n) {
    return GanttCanvas.getMonoColor(n);
  }
  
  public void setColor(int n, Color c) {
    draw_canvas.setColor(n, c);
  }
  
  public void setBarHeight(int n)
  {
    control.setBH(n);
    change_parameters();
  }
  
  public void setBarInc(int n)
  {
    control.setBI(n);
    change_parameters();
  }
  
  public void setToHeight() {
    draw_canvas.setToHeight();
  }
  
  public void setColorList(Color[] cl) {
    draw_canvas.setColorList(cl);
  }
}
