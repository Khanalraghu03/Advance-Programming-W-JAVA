package Jeli.Plots;

import Jeli.Logging.Logger;
import Jeli.Widgets.JeliButton;
import Jeli.Widgets.JeliColorButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Rectangle;
import java.awt.TextField;
import java.util.Vector;

public class BoxAndWhiskersPlots extends Jeli.Widgets.JeliFrame implements Jeli.Logging.LogState, Plots, Jeli.Logging.Mono, Jeli.Logging.SaveImageInfo, Jeli.Widgets.JeliColorButtonCallBack
{
  final int TYPE_BAR = 0;
  final int TYPE_LINE = 1;
  final int BELOW_X = 30;
  final int LEFT_OF_Y = 75;
  int fudge = 75;
  double min;
  double max;
  double bwmax;
  double tick;
  double bin_size;
  int g_width;
  int g_height;
  int plus_width;
  int plus_height;
  double height_scale;
  Color axis_color;
  int num_bins;
  Image im;
  Graphics AxisGC;
  Graphics PlotGC;
  Graphics BackGC;
  boolean first_paint = true;
  String name;
  String shortname;
  int max_bin_count;
  java.awt.FontMetrics fm;
  int x_0_start;
  int y_0_start;
  Panel p;
  Logger logger;
  JeliButton HideButton;
  JeliButton DestroyButton;
  JeliButton SaveButton;
  JeliButton KeyButton;
  JeliButton LogButton;
  JeliColorButton ColorButton;
  int graph_type;
  Color BackColor = Jeli.Utility.jeliLightYellow;
  Color[] color_list;
  Vector plots;
  java.awt.Font SmallFont;
  DPInfo dpinfo;
  boolean save_inhibit;
  double force_max = 0.0D;
  TextField bins_field;
  TextField max_field;
  int button_height;
  Jeli.Widgets.HelpManager hm;
  int change_key_index = 0;
  boolean saving_flag = false;
  private String classification;
  private boolean monoFlag = false;
  


  public BoxAndWhiskersPlots(String str, String str1, double[] vals, int g_width, int g_height, boolean save_inhibit, Color[] cl, Jeli.Widgets.HelpManager hm, Logger logger, DPInfo dpinfo)
  {
    this(str, str1, vals, g_width, g_height, save_inhibit, cl, Color.black, hm, logger, dpinfo);
  }
  







  public BoxAndWhiskersPlots(String str, String str1, double[] vals, int g_width, int g_height, boolean save_inhibit, Color[] cl, Color axis_color, Jeli.Widgets.HelpManager hm, Logger logger, DPInfo dpinfo)
  {
    super(str1);
    
    name = new String(str);
    shortname = new String(str1);
    this.g_width = g_width;
    this.g_height = g_height;
    

    this.save_inhibit = save_inhibit;
    this.axis_color = axis_color;
    this.logger = logger;
    this.dpinfo = dpinfo;
    this.hm = hm;
    plots = new Vector();
    graph_type = 0;
    setLayout(new java.awt.BorderLayout());
    p = new Panel();
    Panel r = new Panel();
    r.setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
    p.setLayout(new Jeli.Widgets.JeliGridLayout(1, 5));
    p.add(this.HideButton = new JeliButton("Hide", hm, this));
    p.add(this.DestroyButton = new JeliButton("Destroy", hm, this));
    p.add(this.ColorButton = new JeliColorButton("Color", -1, 1, color_list, hm, this));
    
    p.add(this.SaveButton = new JeliButton("Save", hm, this));
    p.add(this.LogButton = new JeliButton("Log", hm, this));
    LogButton.setCallbackDelay("Saving ...");
    r.add(p);
    r.add(this.KeyButton = new JeliButton("", hm, this));
    set_keybutton();
    bins_field = new TextField();
    max_field = new TextField();
    add("South", r);
    if (cl == null) {
      color_list = new Color[5];
      color_list[0] = Color.red;
      color_list[1] = Color.green;
      color_list[2] = Color.blue;
      color_list[3] = Color.cyan;
      color_list[4] = Color.magenta;
    }
    else {
      color_list = cl; }
    PlotInfo pi = new PlotInfo(vals, getColor(0), str);
    pi.setQuartiles();
    plots.addElement(pi);
    if (save_inhibit) SaveButton.disableJeliButton();
    pack();
    setSize(g_width, g_height);
    num_bins = 10;
    set_bins_field();
    button_height = getBoundsheight;
    show_insets();
    fudge = (button_height + getInsetstop + getInsetsbottom);
  }
  
  private void set_keybutton() {
    KeyButton.setAsGetNothing("Change Key");
  }
  
  private void set_max_field() {
    max_field.setText("" + max);
  }
  
  private void set_bins_field() {
    bins_field.setText("" + num_bins);
  }
  
  private void make_images()
  {
    plus_height = (g_height - fudge - 30);
    plus_width = (g_width - 75);
    im = createImage(g_width, g_height - fudge);
    AxisGC = im.getGraphics();
    AxisGC.setColor(axis_color);
    SmallFont = new java.awt.Font("Times", 0, 8);
    AxisGC.setFont(SmallFont);
    fm = AxisGC.getFontMetrics(SmallFont);
    PlotGC = im.getGraphics();
    PlotGC.setFont(SmallFont);
    BackGC = im.getGraphics();
    BackGC.setColor(BackColor);
  }
  










  private void draw_axes()
  {
    BackGC.fillRect(0, 0, g_width, g_height - fudge);
    x_0_start = (g_width - plus_width - 25);
    y_0_start = plus_height;
    AxisGC.drawLine(0, y_0_start, g_width, y_0_start);
    AxisGC.drawLine(x_0_start, 0, x_0_start, g_height);
    
    draw_y_tick(get_y_pos(bwmax), bwmax);
  }
  






  private void set_params()
  {
    max = 0.0D;
    bwmax = 0.0D;
    for (int i = 0; i < plots.size(); i++) {
      PlotInfo pi = (PlotInfo)plots.elementAt(i);
      double[] vs = vals;
      for (int j = 0; j < vs.length; j++) {
        if (max < vs[j]) max = vs[j];
        if (bwmax < max) bwmax = max;
      }
    }
    if (force_max > 0.0D) { max = force_max;
    }
    if (max <= 0.001D)
    {
      max = 0.001D;
      tick = 1.0E-4D;

    }
    else if (max <= 0.01D)
    {
      double t = 1000.0D * max;
      if (t > (int)t) t += 1.0D;
      max = ((int)t / 1000.0D);
      
      tick = 0.001D;
    }
    else if (max <= 0.1D)
    {
      double t = 100.0D * max;
      if (t > (int)t) t += 1.0D;
      max = ((int)t / 100.0D);
      
      tick = 0.01D;
    }
    else
    {
      double t = 10.0D * max;
      if (t > (int)t) t += 1.0D;
      max = ((int)t / 10.0D);
      tick = 0.1D;
    }
    










    set_max_field();
    bin_size = ((max - min) / num_bins);
    set_all_bins();
  }
  
  private void draw_graph()
  {
    set_params();
    draw_bws();
  }
  

  private void draw_bws()
  {
    clear();
    int s = plots.size();
    for (int i = 0; i < s; i++) {
      PlotInfo pi = (PlotInfo)plots.elementAt(i);
      draw_bw(pi, i, s);
    }
  }
  
  private void clear() {
    BackGC.fillRect(0, 0, g_width, g_height - fudge);
    draw_axes();
  }
  
  private int get_y_pos(double val)
  {
    if (bwmax == 0.0D) return 0;
    int p_h = y_0_start - key_pos(plots.size());
    return y_0_start - round(p_h * val / bwmax);
  }
  















  private void draw_bw(PlotInfo pi, int which, int num)
  {
    if (bwmax == 0.0D) {
      System.out.println("plot max for plot " + which + " is 0");
      return;
    }
    
    int p_h = y_0_start - key_pos(num);
    PlotGC.setColor(pc);
    if (change_key_index > 0) {
      PlotGC.drawString(which + 1 + ": " + key, 55, key_pos(which));
    } else
      PlotGC.drawString(key, 55, key_pos(which));
    double xinc = plus_width * bin_size / (max - min);
    int x = x_0_start + which * plus_width / num + 10;
    int w = plus_width / (2 * num);
    int x_mid = x + w / 2 - 1;
    int x_mid_m = x + w / 4;
    if (w <= 0) { w = 1;
    }
    





    int y_q1 = get_y_pos(quart1);
    int y_q2 = get_y_pos(quart2);
    int y_max = get_y_pos(max);
    int y_min = get_y_pos(min);
    int y_med = get_y_pos(median);
    int h = y_q1 - y_q2;
    int whisk_1 = y_q2 - y_max;
    int whisk_2 = y_min - y_q1;
    PlotGC.drawRect(x, y_q2, w, h);
    PlotGC.fillRect(x, y_med - 1, w, 3);
    PlotGC.fillRect(x_mid, y_max, 3, whisk_1);
    PlotGC.fillRect(x_mid_m, y_max, w / 2, 1);
    PlotGC.fillRect(x_mid, y_q1, 3, whisk_2);
    PlotGC.fillRect(x_mid_m, y_min, w / 2, 1);
  }
  

























  private void set_all_bins()
  {
    max_bin_count = 0;
    for (int i = 0; i < plots.size(); i++) {
      PlotInfo pi = (PlotInfo)plots.elementAt(i);
      set_bins(pi);
      max_bin_count = get_new_max_bin_count(pi, max_bin_count);
    }
  }
  


  private void set_bins(PlotInfo pi)
  {
    bc = new int[num_bins];
    for (int i = 0; i < num_bins; i++)
      bc[i] = 0;
    for (i = 0; i < vals.length; i++) {
      int bin = (int)((vals[i] - min) / bin_size);
      if (bin >= num_bins)
      {
        bin = num_bins - 1;
      }
      bc[bin] += 1;
    }
  }
  

  private int get_new_max_bin_count(PlotInfo pi, int mc)
  {
    int maxc = mc;
    for (int i = 0; i < bc.length; i++)
      if (bc[i] > maxc) maxc = bc[i];
    return maxc;
  }
  
  private int key_pos(int which) {
    return 10 + which * 10;
  }
  





  private void draw_line(PlotInfo pi, int which, int num)
  {
    PlotGC.setColor(pc);
    PlotGC.drawString(key, 55, key_pos(which));
    double xinc = plus_width * bin_size / (max - min);
    for (int i = 1; i < num_bins; i++) {
      int x1 = round(x_0_start + (i - 1) * xinc);
      int x2 = round(x_0_start + i * xinc);
      int y1 = round(y_0_start - height_scale * bc[(i - 1)]);
      int y2 = round(y_0_start - height_scale * bc[i]);
      PlotGC.drawLine(x1, y1, x2, y2);
    }
  }
  
  private void set_height_scale() {
    height_scale = ((y_0_start - key_pos(plots.size()) - 0.0D) / max_bin_count);
  }
  






















  private void draw_y_tick(int y, double val)
  {
    int x = x_0_start;
    AxisGC.drawLine(x - 5, y, x + 5, y);
    String vs; String vs; if (val > 1000.0D) {
      vs = "" + (int)(val + 0.5D);
    } else
      vs = Jeli.Utility.n_places(val, 2);
    int xoff = fm.stringWidth(vs) + 6;
    AxisGC.drawString(vs, x - xoff, y);
  }
  
  public void paint(Graphics g) {
    if (first_paint)
    {
      make_images();
      draw_graph();
    }
    first_paint = false;
    check_image_sizes();
    

    g.drawImage(im, 0, getInsetstop, null);
  }
  
  public Image getImage() {
    return im;
  }
  
  private int round(double d) {
    if (d >= 0.0D) return (int)(d + 0.5D);
    return -(int)(-d + 0.5D);
  }
  

  public void addPlot(double[] vs, String key)
  {
    PlotInfo pi = new PlotInfo(vs, getColor(plots.size() % color_list.length), key);
    pi.setQuartiles();
    plots.addElement(pi);
    if (!first_paint)
      draw_graph();
    ColorButton.setIndexRange(-1, plots.size());
    repaint();
  }
  




  private boolean check_image_sizes()
  {
    if ((g_width == getBoundswidth) && (g_height == getBoundsheight)) {
      return false;
    }
    


    g_width = getBoundswidth;
    g_height = getBoundsheight;
    

    set_height_scale();
    make_images();
    draw_graph();
    return true;
  }
  
  public void logStateChange(boolean on)
  {
    if (on) {
      LogButton.enableJeliButton();
    } else {
      LogButton.disableJeliButton();
    }
  }
  
  private void show_insets() {
    java.awt.Insets insets = getInsets();
  }
  

  private void show_vals()
  {
    for (int i = 0; i < plots.size(); i++) {
      PlotInfo pi = (PlotInfo)plots.elementAt(i);
      for (int j = 0; j < vals.length; j++)
        System.out.println(" " + j + ": " + vals[j]);
    }
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == HideButton) {
      setVisible(false);
      return;
    }
    if (bh == DestroyButton) {
      dpinfo.dPDestroyed(this);
      setVisible(false);
    }
    if (bh == SaveButton) {
      new Jeli.Logging.GifSaver(im);
    }
    if ((bh == LogButton) && 
      (!saving_flag)) {
      saving_flag = true;
      logger.logImage(im, shortname, this);
    }
    
    if (bh == KeyButton) {
      change_key_index = 1;
      draw_graph();
      repaint();
      if (plots.size() > 1) {
        KeyButton.setGetInteger("Enter Key Index: ", 1);
      } else {
        jeliButtonGotInteger(KeyButton, 1);
      }
    }
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {
    if (bh == KeyButton) {
      if (str != null) {
        PlotInfo pi = (PlotInfo)plots.elementAt(change_key_index - 1);
        key = str;
      }
      set_keybutton();
      change_key_index = 0;
      draw_graph();
      repaint();
      return;
    }
  }
  
  public void jeliButtonGotInteger(JeliButton bh, int val)
  {
    if (bh == KeyButton) {
      if ((val < 1) || (val > plots.size())) {
        set_keybutton();
        change_key_index = 0;
        draw_graph();
        repaint();
      }
      else {
        PlotInfo pi = (PlotInfo)plots.elementAt(val - 1);
        change_key_index = val;
        KeyButton.setGetString("Enter New Key " + val + ": ", key);
      }
      return;
    }
  }
  
  public void saveImageFinished(boolean f) {
    if (!f)
      System.out.println("Save Image Finished: " + f);
    saving_flag = false;
    LogButton.setLabel("Log");
  }
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() {
    return classification;
  }
  
  public void setClassification(String str) {
    classification = str;
  }
  
  public void jeliColorButtonSetColor(JeliColorButton cb, int ind, Color c)
  {
    if (ind == -1) {
      BackColor = c;
      BackGC.setColor(BackColor);
    }
    else if (ind == 0) {
      axis_color = c;
      AxisGC.setColor(axis_color);
    }
    else {
      PlotInfo pi = (PlotInfo)plots.elementAt(ind - 1);
      pc = c;
    }
    draw_graph();
    repaint();
  }
  
  public void jeliColorButtonSetIndex(JeliColorButton cb, int ind) {
    draw_graph();
    repaint();
  }
  
  public void jeliColorButtonSettingIndex(JeliColorButton cb) {
    change_key_index = 1;
    draw_graph();
    change_key_index = 0;
    repaint();
  }
  
  private Color getColor(int n) {
    if (monoFlag)
      return Jeli.Logging.GanttChart.getMonoColor(n);
    return color_list[n];
  }
  
  public void setMono(boolean f)
  {
    monoFlag = f;
    for (int i = 0; i < plots.size(); i++) {
      PlotInfo pi = (PlotInfo)plots.elementAt(i);
      pi.setColor(getColor(i));
    }
    make_images();
    draw_graph();
    repaint();
  }
}
