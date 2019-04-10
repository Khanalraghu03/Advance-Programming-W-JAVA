package Jeli.Plots;

import Jeli.Logging.Logger;
import Jeli.Utility;
import Jeli.Widgets.JeliButton;
import Jeli.Widgets.JeliColorButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;
import java.io.PrintStream;
import java.util.Vector;

public class DistributionPlots extends Jeli.Widgets.JeliFrame implements Jeli.Logging.LogState, Plots, Jeli.Logging.Mono, Jeli.Logging.SaveImageInfo, Jeli.Widgets.JeliColorButtonCallBack
{
  final int TYPE_BAR = 0;
  final int TYPE_LINE = 1;
  final int BELOW_X = 30;
  final int LEFT_OF_Y = 70;
  int fudge = 75;
  double min;
  double max;
  double tick;
  double bin_size;
  int g_width;
  int g_height;
  int plus_width;
  int plus_height;
  double height_scale;
  Color axis_color;
  int num_bins;
  java.awt.Image im;
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
  JeliColorButton ColorButton;
  JeliButton TypeButton;
  JeliButton SaveButton;
  JeliButton KeyButton;
  JeliButton LogButton;
  JeliButton BinButton;
  JeliButton MaxButton;
  JeliButton MinButton;
  int graph_type;
  Color BackColor = Utility.jeliLightYellow;
  Color[] color_list;
  Vector plots;
  java.awt.Font SmallFont;
  DPInfo dpinfo;
  boolean save_inhibit;
  double force_max = 0.0D;
  double force_min = -1.0D;
  
  int button_height;
  
  Jeli.Widgets.HelpManager hm;
  int change_key_index = 0;
  int overflow;
  int underflow;
  boolean saving_flag = false;
  
  private String classification;
  
  private boolean monoFlag;
  
  public DistributionPlots(String str, String str1, double[] vals, int g_width, int g_height, boolean save_inhibit, Color[] cl, Logger logger, Jeli.Widgets.HelpManager hm, DPInfo dpinfo)
  {
    this(str, str1, vals, g_width, g_height, save_inhibit, cl, Color.black, logger, hm, dpinfo);
  }
  




  public DistributionPlots(String str, String str1, double[] vals, int g_width, int g_height, boolean save_inhibit, Color[] cl, Color axis_color, Logger logger, Jeli.Widgets.HelpManager hm, DPInfo dpinfo)
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
    Panel q = new Panel();
    Panel r = new Panel();
    r.setLayout(new Jeli.Widgets.JeliGridLayout(3, 1));
    q.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
    p.setLayout(new Jeli.Widgets.JeliGridLayout(1, 6));
    p.add(this.HideButton = new JeliButton("Hide", hm, this));
    p.add(this.DestroyButton = new JeliButton("Destroy", hm, this));
    p.add(this.ColorButton = new JeliColorButton("Color", -1, 1, color_list, hm, this));
    
    p.add(this.TypeButton = new JeliButton("Bar", hm, this));
    
    p.add(this.SaveButton = new JeliButton("Save", hm, this));
    p.add(this.LogButton = new JeliButton("Log", hm, this));
    LogButton.setCallbackDelay("Saving ...");
    KeyButton = new JeliButton("", hm, this);
    set_keybutton();
    r.add(p);
    r.add(q);
    r.add(KeyButton);
    

    BinButton = new JeliButton("", hm, this);
    set_bins_field();
    MaxButton = new JeliButton("", hm, this);
    MinButton = new JeliButton("", hm, this);
    set_max_field();
    q.add(BinButton);
    q.add(MinButton);
    q.add(MaxButton);
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
    plots.addElement(new PlotInfo(vals, getColor(0), str));
    if (save_inhibit) SaveButton.disableJeliButton();
    pack();
    setSize(g_width, g_height);
    num_bins = 10;
    set_bins_field();
    button_height = getBoundsheight;
    
    show_insets();
    fudge = (button_height + getInsetstop + getInsetsbottom);
  }
  
  private void set_max_field() {
    MaxButton.setAsGetDouble("max", "Enter Max: ", "Max: ", max, true);
    
    MinButton.setAsGetDouble("min", "Enter Min: ", "Min: ", min, true);
  }
  
  private void set_bins_field()
  {
    BinButton.setAsGetInteger("bins", "Enter Bins: ", "Bins: ", num_bins, true);
  }
  
  private void set_keybutton()
  {
    KeyButton.setAsGetNothing("Change Key");
  }
  


  private void make_images()
  {
    plus_width = (g_width - 70);
    plus_height = (g_height - fudge - 30);
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
    x_0_start = (g_width - plus_width - 30);
    y_0_start = plus_height;
    AxisGC.drawLine(0, y_0_start, g_width, y_0_start);
    AxisGC.drawLine(x_0_start, 0, x_0_start, g_height);
    double tick_dist = tick * plus_width / (max - min);
    int tick_pos = (int)tick_dist;
    int tick_places = 2;
    if (tick >= 1.0D) { tick_places = 0;
    } else if (tick < 0.1D) { tick_places = 3;
    }
    if (min != 0.0D) {
      String ts = Utility.n_places(min, tick_places);
      AxisGC.drawString(ts, x_0_start + 1, y_0_start + 18);
    }
    for (int i = 0; tick_pos <= plus_width; i++) {
      int x = x_0_start + tick_pos;
      int y = y_0_start;
      AxisGC.drawLine(x, y - 5, x, y + 5);
      String ts = Utility.n_places((i + 1) * tick + min, tick_places);
      int xoff = fm.stringWidth(ts) / 2;
      AxisGC.drawString(ts, x - xoff, y + 18);
      tick_pos = round((i + 2) * tick_dist);
    }
    if (max_bin_count == 0) return;
    set_height_scale();
    int tick_dec = round((40.0D + height_scale - 1.0D) / height_scale);
    draw_y_tick(tick_dec);
    for (int i = 2 * tick_dec; i <= max_bin_count; i += tick_dec) {
      draw_y_tick(i);
    }
  }
  









  private void set_params()
  {
    max = 0.0D;
    min = 0.0D;
    for (int i = 0; i < plots.size(); i++) {
      PlotInfo pi = (PlotInfo)plots.elementAt(i);
      double[] vs = vals;
      for (int j = 0; j < vs.length; j++)
        if (max < vs[j]) max = vs[j];
    }
    if (force_max > 0.0D) max = force_max;
    if (force_min >= 0.0D) min = force_min;
    if (max > 1.0D) {
      for (int round = 1; 10 * round < max; round = 10 * round) {}
      int newmax;
      int newmin; if (round == 1) {
        int newmax = (int)(max + 0.9D);
        int newmin = (int)min;
        tick = 1.0D;
      }
      else {
        newmax = round * (int)((max + round - 1.0D) / round);
        newmin = round * (int)(min / round);
        tick = round;
      }
      max = newmax;
      min = newmin;


    }
    else if (max <= 0.001D)
    {
      max = 0.001D;
      tick = 1.0E-4D;
      if (min >= max) min = 0.0D; else {
        min = (10000 * (int)(min * 10000.0D));
      }
      
    }
    else if (max <= 0.01D)
    {
      double t = 1000.0D * max;
      if (t > (int)t) t += 1.0D;
      max = ((int)t / 1000.0D);
      
      if (min >= max) min = 0.0D; else
        min = ((int)(min * 1000.0D) / 1000.0D);
      tick = 0.001D;
    }
    else if (max <= 0.1D)
    {
      double t = 100.0D * max;
      if (t > (int)t) t += 1.0D;
      max = ((int)t / 100.0D);
      
      if (min >= max) min = 0.0D; else
        min = ((int)(min * 100.0D) / 100.0D);
      tick = 0.01D;
    }
    else
    {
      double t = 10.0D * max;
      if (t > (int)t) t += 1.0D;
      max = ((int)t / 10.0D);
      if (min >= max) min = 0.0D; else
        min = ((int)(min * 10.0D) / 10.0D);
      tick = 0.1D;
    }
    










    set_max_field();
    bin_size = ((max - min) / num_bins);
    set_all_bins();
  }
  
  private void draw_graph()
  {
    set_params();
    if (graph_type == 0) {
      draw_bars();
    } else if (graph_type == 1)
      draw_lines();
    set_overflows();
  }
  
  private void set_overflows()
  {
    overflow = 0;
    underflow = 0;
    for (int i = 0; i < plots.size(); i++) {
      PlotInfo pi = (PlotInfo)plots.elementAt(i);
      overflow += overflow;
      underflow += underflow;
    }
    if (overflow > 0) {
      MaxButton.setAfterString(" (" + overflow + ")");
    } else
      MaxButton.setAfterString("");
    if (underflow > 0) {
      MinButton.setAfterString(" (" + underflow + ")");
    } else {
      MinButton.setAfterString("");
    }
  }
  
  private void draw_bars()
  {
    clear();
    if (max_bin_count == 0) return;
    int s = plots.size();
    for (int i = 0; i < s; i++) {
      PlotInfo pi = (PlotInfo)plots.elementAt(i);
      draw_bar(pi, i, s);
    }
  }
  

  private void draw_lines()
  {
    clear();
    if (max_bin_count == 0) return;
    int s = plots.size();
    for (int i = 0; i < s; i++) {
      PlotInfo pi = (PlotInfo)plots.elementAt(i);
      draw_line(pi, i, s);
    }
  }
  
  private void clear() {
    BackGC.fillRect(0, 0, g_width, g_height - fudge);
    draw_axes();
  }
  






  private void draw_bar(PlotInfo pi, int which, int num)
  {
    PlotGC.setColor(pc);
    if (change_key_index > 0) {
      PlotGC.drawString(which + 1 + ": " + key, 55, key_pos(which));
    } else
      PlotGC.drawString(key, 55, key_pos(which));
    double xinc = plus_width * bin_size / (max - min);
    int w = round(xinc / num) + 1;
    int xoff = (w - 1) * which;
    for (int i = 0; i < num_bins; i++) {
      int x = round(x_0_start + i * xinc) + xoff;
      int h = round(height_scale * bc[i]);
      int y = y_0_start - h;
      PlotGC.fillRect(x, y, w, h);
    }
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
    overflow = 0;
    underflow = 0;
    for (int i = 0; i < num_bins; i++)
      bc[i] = 0;
    for (i = 0; i < vals.length; i++) {
      int bin = (int)((vals[i] - min) / bin_size);
      if (bin >= num_bins)
      {

        overflow += 1;
      }
      else if (bin < 0)
      {
        underflow += 1;
      }
      else {
        bc[bin] += 1;
      }
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
    if (change_key_index > 0) {
      PlotGC.drawString(which + 1 + ": " + key, 55, key_pos(which));
    } else
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
  




  private void draw_y_tick(int val)
  {
    int x = x_0_start;
    int y = round(y_0_start - height_scale * val);
    AxisGC.drawLine(x - 5, y, x + 5, y);
    String vs = "" + val;
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
  
  public java.awt.Image getImage() {
    return im;
  }
  
  private int round(double d) {
    if (d >= 0.0D) return (int)(d + 0.5D);
    return -(int)(-d + 0.5D);
  }
  

  public void addPlot(double[] vs, String key)
  {
    PlotInfo pi = new PlotInfo(vs, getColor(plots.size() % color_list.length), key);
    plots.addElement(pi);
    if (!first_paint)
      draw_graph();
    ColorButton.setIndexRange(-1, plots.size());
    repaint();
  }
  
  private void change_key() {
    System.out.println("Change Key entered");
    System.out.println("Number of plots is " + plots.size());
    for (int i = 0; i < plots.size(); i++)
      System.out.println("  " + i + ": " + plots.elementAt(i)).key);
  }
  
  private boolean check_image_sizes() {
    if ((g_width == getBoundswidth) && (g_height == getBoundsheight))
      return false;
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
      setVisible(false);
      if (dpinfo != null)
        dpinfo.dPDestroyed(this);
    }
    if (bh == TypeButton) {
      if (graph_type == 0) {
        TypeButton.setLabel("Line");
        graph_type = 1;
      }
      else {
        TypeButton.setLabel("Bar");
        graph_type = 0;
      }
      draw_graph();
      repaint();
      return;
    }
    if (bh == SaveButton) {
      new Jeli.Logging.GifSaver(im);
    }
    if ((bh == LogButton) && 
      (!saving_flag)) {
      saving_flag = true;
      logger.logImage(im, shortname, get_log_caption(), this);
    }
    
    if (bh == KeyButton) {
      change_key_index = 1;
      draw_graph();
      repaint();
      if (plots.size() > 1) {
        KeyButton.setGetInteger("Enter Key Index: ", 1);
      } else
        jeliButtonGotInteger(KeyButton, 1);
    }
  }
  
  private String get_log_caption() {
    String ostring;
    String ostring;
    if (overflow == 0) {
      ostring = "";
    } else
      ostring = " Overflow: " + overflow;
    String ustring; String ustring; if (underflow == 0) {
      ustring = "";
    } else
      ustring = " Underflow: " + underflow;
    return "Bins: " + num_bins + " Min: " + Utility.n_places(min, 2) + " Max: " + Utility.n_places(max, 2) + ostring + ustring;
  }
  


  public void jeliButtonGotString(JeliButton bh, String str)
  {
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
    if (bh == BinButton) {
      if (val > 0)
        num_bins = val;
      set_bins_field();
      draw_graph();
      repaint();
      return;
    }
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
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {
    if (bh == MaxButton) {
      if ((val > 0.0D) && (val > min))
        force_max = val;
      draw_graph();
      set_max_field();
      repaint();
      return;
    }
    if (bh == MinButton) {
      if ((val >= 0.0D) && (val < max))
        force_min = val;
      draw_graph();
      set_max_field();
      repaint();
      return;
    }
  }
  
  public void saveImageFinished(boolean f) {
    if (!f)
      System.out.println("Save Image Finished: " + f);
    saving_flag = false;
    LogButton.setLabel("Log");
  }
  
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
  

  public void jeliColorButtonSetIndex(JeliColorButton cb, int ind)
  {
    draw_graph();
    repaint();
  }
  
  public void jeliColorButtonSettingIndex(JeliColorButton cb)
  {
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
