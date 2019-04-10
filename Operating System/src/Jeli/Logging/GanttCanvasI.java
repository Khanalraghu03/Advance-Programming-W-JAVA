package Jeli.Logging;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.Vector;

public class GanttCanvasI extends Jeli.Widgets.JeliTransferCanvas
{
  public Vector gantt_list;
  int thiswidth;
  int thisheight;
  Color text_color = Color.black;
  Color grid_text_color = Color.gray;
  Color light_grid_text_color = Color.lightGray;
  Color back_color = Color.white;
  int keyBarLength = 0;
  
  int image_x;
  int image_y;
  int image_move_x = 0;
  int image_move_y = 0;
  int mouse_down_x;
  int mouse_down_y;
  int mouse_down_xoff;
  int mouse_down_yoff;
  final int INIT_IMAGE_X = 0;
  final int INIT_IMAGE_Y = 0;
  long grid_increment;
  long force_grid_increment = -1L;
  int label_grid_skip = 1;
  long grid_init = 0L;
  long last_end_time;
  long last_end_time_display;
  String last_string;
  int last_string_pos_x;
  int last_string_pos_y;
  int first_x;
  int last_x;
  double milliseconds_per_pixel = 100.0D;
  int vertical_offset = 15;
  int vertical_pad = 0;
  int frame_height = 10;
  int frame_offset = 2;
  int start_offset = 20;
  long last_time;
  long first_time;
  long[] grid_increments = { 1L, 2L, 5L, 10L, 50L, 100L, 500L, 1000L, 5000L, 10000L, 50000L };
  double[] autoscale_values = { 1.0D, 0.5D, 0.2D, 0.1D, 0.05D, 0.02D, 0.01D, 0.005D, 0.002D, 0.001D };
  
  boolean draw_done_flag = false;
  boolean autoscale_flag = false;
  
  boolean first_paint_flag = true;
  boolean relative_time_flag = true;
  boolean frame_flag = false;
  
  java.awt.Image foreground;
  Graphics[] GCs;
  Graphics ClearGC;
  Graphics TextGC;
  Graphics barGC;
  Graphics GridTextGC;
  Graphics LightGridTextGC;
  java.awt.Font TextFont;
  java.awt.Font KeyFont;
  FontMetrics Textfm;
  FontMetrics Keyfm;
  GanttChartI gchart;
  Color[] color_list = { Color.red, Color.green, Color.blue, Color.yellow };
  String[] KeyArray = null;
  private final int CheatCodeSize = 40;
  private String CheatCode;
  private int frame_number_offset = 0;
  private int sizeToSet = 0;
  private boolean show_values = false;
  private boolean draw_linear_bar;
  private boolean draw_quadratic_bar;
  private boolean draw_mid_quadratic_bar;
  private boolean draw_log_bar;
  private boolean draw_exp_bar;
  private static Color[] monoColors;
  private static Color[] discreteMonoColors;
  private boolean mono_flag = false;
  private boolean mouseDownFlag = false;
  

  public GanttCanvasI(Vector gantt_list, GanttChartI gchart)
  {
    this.gchart = gchart;
    this.gantt_list = gantt_list;
    image_x = 0;
    image_y = 0;
    initCheatCode();
  }
  


  public void setKey(String[] key)
  {
    KeyArray = key;
  }
  
  public void update(Graphics g) {
    paint(g);
  }
  
  public void paint(Graphics g) {
    if ((first_paint_flag) || (thiswidth != getBoundswidth) || (thisheight != getBoundsheight))
    {

      first_paint_flag = false;
      


      init_images();
    }
    if (sizeToSet > 0) {
      resetToSize(sizeToSet);
      sizeToSet = 0;
    }
    draw_all_to_background();
    if (autoscale_flag)
      autoScale();
    g.drawImage(foreground, 0, 0, null);
  }
  
  void init_images()
  {
    thiswidth = getBoundswidth;
    thisheight = getBoundsheight;
    

    foreground = createImage(thiswidth, thisheight);
    
    GCs = new Graphics[color_list.length];
    int c = 256;
    for (int i = 0; i < color_list.length; i++) {
      GCs[i] = foreground.getGraphics();
      if (mono_flag) {
        GCs[i].setColor(new Color(256 - c, 256 - c, 256 - c));
        c /= 2;
        if (c < 10) c = 256;
      }
      else {
        GCs[i].setColor(color_list[i]);
      } }
    TextGC = foreground.getGraphics();
    barGC = foreground.getGraphics();
    GridTextGC = foreground.getGraphics();
    LightGridTextGC = foreground.getGraphics();
    ClearGC = foreground.getGraphics();
    TextGC.setColor(text_color);
    GridTextGC.setColor(grid_text_color);
    LightGridTextGC.setColor(light_grid_text_color);
    ClearGC.setColor(back_color);
    setGanttFont();
    KeyFont = GCs[0].getFont();
    Keyfm = GCs[0].getFontMetrics(KeyFont);
    vertical_offset = (Keyfm.getMaxAscent() + 2);
    vertical_pad = (Keyfm.getMaxDescent() + 2);
  }
  
  void setGanttFont() {
    TextFont = new java.awt.Font("Dialog", 0, frame_height);
    if (TextGC != null) {
      TextGC.setFont(TextFont);
      Textfm = TextGC.getFontMetrics(TextFont);
      TextGC.setFont(TextFont);
      start_offset = Textfm.stringWidth("999");
    }
  }
  





  public void draw_all_to_background()
  {
    int x_start = 0;
    int x_end = 0;
    



    long frame_fix = 0L;
    

    int xoff = image_x + image_move_x;
    int yoff = image_y + image_move_y;
    




    int last_which = -1;
    
    int y = vertical_offset + yoff;
    ClearGC.fillRect(0, 0, thiswidth, thisheight);
    long current_time = 0L;
    last_string = null;
    last_end_time = 0L;
    
    first_x = 100000;
    last_x = 0;
    first_time = 0L;
    last_time = 0L;
    int last_object = 0;
    Vector outlines = new Vector();
    if (gantt_list.size() > 0) {
      first_time = gantt_list.elementAt(0)).Start;
      last_time = gantt_list.elementAt(0)).End;
    }
    for (int i = 0; i < gantt_list.size(); i++) {
      GanttInfoI ginfo = (GanttInfoI)gantt_list.elementAt(i);
      if (Start < first_time) first_time = Start;
      if (End > last_time) last_time = End;
      if (ObjectNumber > last_object) last_object = ObjectNumber;
    }
    if (relative_time_flag) {
      grid_init = first_time;
    } else
      grid_init = 0L;
    if (KeyArray != null)
    {

      y = vertical_offset - 2;
      int x = start_offset;
      for (int i = 0; i < KeyArray.length; i++) {
        if (keyBarLength > 2) {
          GCs[(i % color_list.length)].fillRect(x, y - vertical_offset + 2, keyBarLength - 2, vertical_offset - 2);
        }
        GCs[(i % color_list.length)].drawString(KeyArray[i], x + keyBarLength, y);
        x = x + Keyfm.stringWidth(KeyArray[i]) + 25 + keyBarLength;
      }
    }
    boolean[] first_entry_flag = new boolean[last_object + 1];
    for (int i = 0; i <= last_object; i++)
      first_entry_flag[i] = true;
    for (int i = 0; i < gantt_list.size(); i++) {
      GanttInfoI ginfo = (GanttInfoI)gantt_list.elementAt(i);
      int which = ObjectNumber;
      if (which > last_which) last_which = which;
      int type = Type;
      
      int frame_number = ObjectNumber + frame_number_offset;
      long frame_start = Start - frame_fix;
      long frame_end = End - frame_fix;
      if (frame_start >= -frame_fix)
      {







        x_start = start_offset + (int)(frame_start / milliseconds_per_pixel);
        x_end = start_offset + (int)(frame_end / milliseconds_per_pixel);
        
        if (x_end > x_start) {
          if (x_start < first_x) first_x = x_start;
          if (x_end > last_x) last_x = x_end;
          y = vertical_offset + vertical_pad + yoff + (frame_height + frame_offset) * which;
          
          GCs[(type % color_list.length)].fillRect(x_start + xoff, y, x_end - x_start, frame_height);
          
          if (frame_flag)
          {
            outlines.addElement(new Rectangle(x_start + xoff, y, x_end - x_start, frame_height));
          }
          int stringwidth = Textfm.stringWidth("" + frame_number);
          if (first_entry_flag[which] != 0) {
            TextGC.drawString("" + frame_number, x_start + xoff - stringwidth - 1, y + frame_height);
            
            first_entry_flag[which] = false;
          }
        }
      }
      y = y + frame_height + frame_offset;
    }
    draw_grid();
    draw_bars(vertical_offset + vertical_pad + yoff + (frame_height + frame_offset) * (last_which + 1), x_end + image_move_x);
    

    for (int i = 0; i < outlines.size(); i++) {
      Rectangle r = (Rectangle)outlines.elementAt(i);
      TextGC.drawRect(x, y, width, height);
    }
    if (last_string != null) {
      int stringwidth = Textfm.stringWidth(last_string);
      TextGC.drawString(" at " + time_one_place(last_end_time_display - grid_init), last_string_pos_x + stringwidth + 1, last_string_pos_y);
    }
    

    draw_done_flag = true;
    if (show_values) {
      System.out.println("Must show values");
      System.out.println("x_start: " + x_start);
      System.out.println("x_end:   " + x_end);
      System.out.println("y = " + y);
      System.out.println("first_x = " + (start_offset + image_x + image_move_x));
      TextGC.fillRect(start_offset + image_x + image_move_x, y, 100, 5);
    }
    
    show_values = false;
  }
  



  private void draw_bars(int y, int x_end)
  {
    int x = start_offset + image_x + image_move_x;
    int wid = x_end - x + 1;
    if (wid <= 1) return;
    if (draw_linear_bar) {
      makeMonoColors();
      for (int i = x; i <= x + wid; i++) {
        barGC.setColor(monoColors[((i - x) * 255 / wid)]);
        barGC.drawLine(i, y, i, y + frame_height);
      }
      y = y + frame_height + frame_offset;
    }
    if (draw_quadratic_bar) {
      makeMonoColors();
      for (int i = x; i <= x + wid; i++) {
        barGC.setColor(monoColors[((i - x) * (i - x) * 255 / (wid * wid))]);
        barGC.drawLine(i, y, i, y + frame_height);
      }
      y = y + frame_height + frame_offset;
    }
    if (draw_mid_quadratic_bar) {
      makeMonoColors();
      for (int i = x; i <= x + wid; i++) {
        barGC.setColor(monoColors[((i - x) * (i - x) * 160 / (wid * wid) + 32)]);
        barGC.drawLine(i, y, i, y + frame_height);
      }
      y = y + frame_height + frame_offset;
    }
    if (draw_log_bar) {
      makeMonoColors();
      double h = 255.0D / Math.log(wid + 1);
      for (int i = x; i <= x + wid; i++) {
        int ind = (int)(h * Math.log(i - x + 1));
        barGC.setColor(monoColors[ind]);
        barGC.drawLine(i, y, i, y + frame_height);
      }
    }
    if (draw_exp_bar) {
      makeMonoColors();
      double h = Math.log(256.0D) / wid;
      for (int i = x; i <= x + wid; i++) {
        int ind = (int)(Math.exp(h * (i - x)) - 1.0D);
        barGC.setColor(monoColors[ind]);
        barGC.drawLine(i, y, i, y + frame_height);
      }
      y = y + frame_height + frame_offset;
    }
  }
  

  String time_one_place(long tm)
  {
    return Jeli.Utility.n_places(tm / 1000.0D, 1);
  }
  

  long get_grid_increment()
  {
    if (force_grid_increment > 0L) return force_grid_increment;
    int max_grids = (last_x - first_x) / 50;
    long delta = last_time - grid_init;
    for (int i = 0; i < grid_increments.length; i++)
      if (delta / grid_increments[i] < max_grids) return grid_increments[i];
    return 0L;
  }
  


  void draw_grid()
  {
    grid_increment = get_grid_increment();
    if (grid_increment == 0L)
    {
      return;
    }
    
    long ctime = grid_increment;
    ctime = 0L;
    
    int grid_counter = 0;
    while (ctime <= last_time + grid_increment) {
      int x = start_offset + image_x + image_move_x + (int)(ctime / milliseconds_per_pixel);
      

      if (grid_counter % label_grid_skip == 0) {
        GridTextGC.drawLine(x, vertical_offset + image_y + image_move_y, x, thisheight);
        
        GridTextGC.drawString("" + ctime, x + 2, thisheight - 5);
      }
      else {
        LightGridTextGC.drawLine(x, vertical_offset + image_y + image_move_y, x, thisheight);
      }
      ctime += grid_increment;
      grid_counter++;
    }
  }
  
  private int get_x_offset() {
    return image_x;
  }
  
  private int get_y_offset() {
    return image_y;
  }
  
  public void mouseDown(int x, int y) {
    mouse_down_x = x;
    mouse_down_y = y;
    mouse_down_xoff = get_x_offset();
    mouse_down_yoff = get_y_offset();
    mouseDownFlag = true;
  }
  
  public void mouseUp(int x, int y) {
    if (!mouseDownFlag) return;
    image_move_x = (x - mouse_down_x);
    image_move_y = (y - mouse_down_y);
    image_x += image_move_x;
    image_y += image_move_y;
    image_move_x = 0;
    image_move_x = 0;
    mouseDownFlag = false;
    repaint();
  }
  
  public void mouseDrag(int x, int y) {
    image_move_x = (x - mouse_down_x);
    image_move_y = (y - mouse_down_y);
    repaint();
  }
  
  public void home() {
    image_x = 0;
    image_y = 0;
    image_move_x = 0;
    image_move_y = 0;
    repaint();
  }
  
  public double get_ms() {
    return milliseconds_per_pixel;
  }
  
  public void set_ms(double ms) {
    milliseconds_per_pixel = ms;
  }
  
  public int get_frame_height() {
    return frame_height;
  }
  
  public void set_frame_height(int fh) {
    frame_height = fh;
    setGanttFont();
  }
  
  public void set_frame_increment(int fi) {
    frame_offset = fi;
    setGanttFont();
  }
  
  public int get_frame_increment() {
    return frame_offset;
  }
  
  public void noAutoScale() {
    autoscale_flag = false;
  }
  



  public void autoScale()
  {
    if (!draw_done_flag) {
      autoscale_flag = true;
      return;
    }
    autoscale_flag = false;
    long denom = thiswidth - start_offset - 10;
    


    if (denom <= 0L) return;
    double ideal = (last_time - first_time) / denom;
    if (ideal < 1.0D)
    {
      double new_ideal = autoscale_values[(autoscale_values.length - 1)];
      for (int i = 1; i < autoscale_values.length; i++)
        if (ideal > autoscale_values[i]) {
          new_ideal = autoscale_values[(i - 1)];
          break;
        }
      gchart.setMS(new_ideal);
      gchart.setMax(new_ideal * 4.0D);
      milliseconds_per_pixel = new_ideal;
    }
    else
    {
      long ms = (last_time - first_time + denom - 1L) / denom;
      if (ms < 1L) ms = 1L;
      gchart.setMS(ms);
      milliseconds_per_pixel = ms;
    }
    draw_all_to_background();
  }
  

  public void setColorEntry(int ind, Color c)
  {
    if (ind == -1)
    {
      text_color = c;
      TextGC.setColor(text_color);
    }
    else if (ind == -2) {
      back_color = c;
      ClearGC.setColor(back_color);

    }
    else if (ind == -3) {
      grid_text_color = c;
      GridTextGC.setColor(grid_text_color);
    }
    else
    {
      int index = ind % color_list.length;
      color_list[index] = c;
    }
    init_images();
    repaint();
  }
  
  void forceGridIncrement(long inc) {
    force_grid_increment = inc;
    repaint();
  }
  
  void labelGridSkip(int skip) {
    if (skip <= 0) skip = 1;
    label_grid_skip = skip;
    repaint();
  }
  
















  public void jeliKeyPress(char c)
  {
    if (c == '\n') {
      handleCheatCode();
    } else {
      CheatCode = (CheatCode + String.valueOf(c)).substring(1, 41);
    }
  }
  



  private void handleCheatCode()
  {
    StringTokenizer stk = new StringTokenizer(CheatCode);
    initCheatCode();
    if (!stk.hasMoreTokens()) return;
    String tok = stk.nextToken();
    if (tok.equalsIgnoreCase("Please"))
    {
      System.out.println("Gantt Chart Codes:");
      System.out.println("   GridIncrement n");
      System.out.println("   GridLabelSkip n");
      System.out.println("   FrameOffset n");
      System.out.println("   SetHeight n");
      System.out.println("   SetWidth n");
      System.out.println("   BarHeight n");
      System.out.println("   BarSpacing n");
      System.out.println("   KeyBarLength n");
      System.out.println("   ShowSize");
      System.out.println("   ShowAll");
      System.out.println("   SmallButtons");
      System.out.println("   Frame");
      System.out.println("   NoFrame");
      if (KeyArray != null) {
        for (int i = 0; i < KeyArray.length; i++)
          System.out.println("   " + KeyArray[i] + " n");
      }
    }
    if (tok.equalsIgnoreCase("GridIncrement"))
    {
      if (stk.hasMoreTokens()) {
        tok = stk.nextToken();
        int val = Integer.parseInt(tok);
        if (val >= 0) forceGridIncrement(val);
      }
    }
    if (tok.equalsIgnoreCase("GridLabelSkip"))
    {
      if (stk.hasMoreTokens()) {
        tok = stk.nextToken();
        int val = Integer.parseInt(tok);
        if (val >= 0) labelGridSkip(val);
      }
    }
    if (tok.equalsIgnoreCase("FrameOffset"))
    {
      if (stk.hasMoreTokens()) {
        tok = stk.nextToken();
        int val = Integer.parseInt(tok);
        frame_number_offset = val;
        repaint();
      }
    }
    if (tok.equalsIgnoreCase("SetHeight"))
    {
      if (stk.hasMoreTokens()) {
        tok = stk.nextToken();
        int val = Integer.parseInt(tok);
        if (val > 0) {
          setSize(getBoundswidth, val);
          init_images();
          gchart.repack();
        }
        repaint();
      }
    }
    if (tok.equalsIgnoreCase("SetWidth"))
    {
      if (stk.hasMoreTokens()) {
        tok = stk.nextToken();
        int val = Integer.parseInt(tok);
        if (val > 0)
        {
          setSize(val, getBoundsheight + 1);
          
          init_images();
          gchart.repack();
        }
        repaint();
      }
    }
    if (tok.equalsIgnoreCase("BarHeight"))
    {
      if (stk.hasMoreTokens()) {
        tok = stk.nextToken();
        int val = Integer.parseInt(tok);
        if (val > 0) {
          set_frame_height(val);
          gchart.BarHeight.set_value(val);
        }
        repaint();
      }
    }
    if (tok.equalsIgnoreCase("BarSpacing"))
    {
      if (stk.hasMoreTokens()) {
        tok = stk.nextToken();
        int val = Integer.parseInt(tok);
        if (val > 0) {
          set_frame_increment(val);
          gchart.BarInc.set_value(val);
        }
        repaint();
      }
    }
    if (tok.equalsIgnoreCase("KeyBarLength"))
    {
      if (stk.hasMoreTokens()) {
        tok = stk.nextToken();
        int val = Integer.parseInt(tok);
        if (val > 0) {
          gchart.setKeyBarLength(val);
        }
        repaint();
      }
    }
    if (tok.equalsIgnoreCase("ShowSize"))
    {
      if (!stk.hasMoreTokens()) {
        System.out.println("Gantt Chart Canvas Size: " + getBounds());
      }
    }
    if (tok.equalsIgnoreCase("SmallButtons"))
    {
      if (!stk.hasMoreTokens()) {
        gchart.setSmallButtons();
      }
    }
    if (tok.equalsIgnoreCase("ShowAll"))
    {
      if (!stk.hasMoreTokens()) {
        showGanttList();
      }
    }
    if (tok.equalsIgnoreCase("Frame"))
    {
      if (!stk.hasMoreTokens()) {
        frame_flag = true;
        repaint();
      }
    }
    if (tok.equalsIgnoreCase("NoFrame"))
    {
      if (!stk.hasMoreTokens()) {
        frame_flag = false;
        repaint();
      }
    }
    if (tok.equalsIgnoreCase("ShowValues"))
    {
      if (!stk.hasMoreTokens()) {
        show_values = true;
        repaint();
      }
    }
    if ((tok.equalsIgnoreCase("DrawLinear")) && 
      (!stk.hasMoreTokens())) {
      draw_linear_bar = true;
      repaint();
    }
    
    if ((tok.equalsIgnoreCase("DrawQuadratic")) && 
      (!stk.hasMoreTokens())) {
      draw_quadratic_bar = true;
      repaint();
    }
    
    if ((tok.equalsIgnoreCase("DrawMidQuadratic")) && 
      (!stk.hasMoreTokens())) {
      draw_mid_quadratic_bar = true;
      repaint();
    }
    
    if ((tok.equalsIgnoreCase("DrawLog")) && 
      (!stk.hasMoreTokens())) {
      draw_log_bar = true;
      repaint();
    }
    
    if ((tok.equalsIgnoreCase("DrawExp")) && 
      (!stk.hasMoreTokens())) {
      draw_exp_bar = true;
      repaint();
    }
    
    if ((tok.equalsIgnoreCase("Mono")) && 
      (!stk.hasMoreTokens())) {
      mono_flag = true;
      init_images();
      repaint();
    }
    
    if ((tok.equalsIgnoreCase("Color")) && 
      (!stk.hasMoreTokens())) {
      mono_flag = false;
      init_images();
      repaint();
    }
    
    if (KeyArray != null) {
      for (int i = 0; i < KeyArray.length; i++) {
        if (tok.equalsIgnoreCase(KeyArray[i]))
        {
          if (stk.hasMoreTokens()) {
            tok = stk.nextToken();
            int val = Integer.parseInt(tok);
            if ((val >= 0) && (val <= 255)) {
              int ind = i % color_list.length;
              color_list[ind] = new Color(val, val, val);
              GCs[ind].setColor(color_list[ind]);
            }
          }
          repaint();
          return;
        }
      }
    }
  }
  
  private void initCheatCode() {
    char[] cheat_array = new char[40];
    for (int i = 0; i < 40; i++)
      cheat_array[i] = ' ';
    CheatCode = new String(cheat_array);
  }
  
  public void focus() {
    requestFocus();
  }
  
  private void showGanttList()
  {
    System.out.println("Gantt Chart at (" + image_x + "," + image_y + "  (" + image_move_x + "," + image_move_y + ")");
    
    System.out.println("List has size " + gantt_list.size());
    for (int i = 0; i < gantt_list.size(); i++) {
      GanttInfoI info = (GanttInfoI)gantt_list.elementAt(i);
      System.out.println(i + ": " + ObjectNumber + " " + Type + " " + Start + " " + End);
      
      if (i > 40) return;
    }
  }
  
  private void resetToSize(int n)
  {
    int h = getVerticalPosition(n) + getGridHeight();
    setSize(getBoundswidth, h);
    init_images();
    gchart.repack();
  }
  
  private int getVerticalPosition(int n) {
    return vertical_offset + vertical_pad + image_y + image_move_y + (frame_height + frame_offset) * n;
  }
  

  private int getGridHeight()
  {
    FontMetrics fm = getFontMetrics(GridTextGC.getFont());
    return fm.getMaxAscent() + 10;
  }
  
  public void setToSize(int n) {
    sizeToSet = n;
  }
  



























  public void jeliMouseMoved(int x, int y)
  {
    requestFocus();
  }
  
  public void jeliMouseDragged(int x, int y) {
    mouseDrag(x, y);
  }
  
  public void jeliMousePressed(int x, int y, int mod, int count) {
    mouseDown(x, y);
  }
  
  public void jeliMouseReleased(int x, int y, int mod, int count) {
    mouseUp(x, y);
  }
  
  protected void setKeyBarLength(int n) {
    keyBarLength = n;
  }
  
  private static void makeMonoColors() {
    if (monoColors != null) return;
    monoColors = new Color['Ā'];
    for (int i = 0; i < 256; i++)
      monoColors[i] = new Color(255 - i, 255 - i, 255 - i);
  }
  
  protected void setMono(boolean f) {
    mono_flag = f;
    init_images();
    repaint();
  }
  
  private static void setDiscreteColors() {
    int c = 256;
    if (discreteMonoColors != null)
      return;
    discreteMonoColors = new Color[6];
    for (int i = 0; i < 6; i++) {
      discreteMonoColors[i] = new Color(256 - c, 256 - c, 256 - c);
      c /= 2;
    }
  }
  
  protected static Color getMonoColor(int n) {
    setDiscreteColors();
    return discreteMonoColors[(n % discreteMonoColors.length)];
  }
  
  protected void setColor(int n, Color c) {
    if (n >= color_list.length) return;
    color_list[n] = c;
    first_paint_flag = true;
    repaint();
  }
  


  protected void setToHeight()
  {
    int max = 0;
    for (int i = 0; i < gantt_list.size(); i++) {
      GanttInfoI gi = (GanttInfoI)gantt_list.elementAt(i);
      if (ObjectNumber > max) max = ObjectNumber;
    }
    if (max == 0) return;
    int h = getVerticalPosition(max + 1) + getGridHeight();
    if (h > 900) return;
    setSize(getBoundswidth, h);
    init_images();
    gchart.repack();
  }
  
  protected void setColorList(Color[] cl) {
    color_list = new Color[cl.length];
    for (int i = 0; i < cl.length; i++)
      color_list[i] = cl[i];
    first_paint_flag = true;
    repaint();
  }
}
