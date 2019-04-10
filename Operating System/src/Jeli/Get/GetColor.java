package Jeli.Get;

import Jeli.Widgets.JeliButton;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Panel;

public class GetColor extends Jeli.Widgets.JeliFrame
{
  int id;
  ColorCallBack callback;
  JeliButton AbortButton;
  JeliButton DoneButton;
  JeliButton ApplyButton;
  JeliButton NextButton;
  JeliButton PaletteButton;
  JeliButton PaletteButtonA;
  JeliButton IncrementButton;
  Color selected_color;
  int selected_r;
  int selected_g;
  int selected_b;
  int delta_c;
  int num_c;
  int color_width;
  int color_height;
  int select_position;
  int select_position_x;
  int select_position_y;
  int gross_position_x;
  int gross_position_y;
  int gross_index_i;
  int gross_index_j;
  int gross_index_k;
  int fine_index_i;
  int fine_index_j;
  int fine_index_k;
  boolean selected_flag;
  int init_x = 20;
  int init_y = 50;
  int init_y1 = 50;
  int modify_r;
  int modify_g;
  int modify_b;
  boolean debug_flag;
  java.awt.Image im;
  Graphics GC;
  Jeli.Widgets.HelpManager hm;
  boolean basic_palette_flag = true;
  int[] colorlist = { 0, 102, 153, 204, 255 };
  int inc_value = 8;
  
  public GetColor(String name, int id, Jeli.Widgets.HelpManager hm, ColorCallBack callback)
  {
    super(name);
    this.id = id;
    this.callback = callback;
    this.hm = hm;
    selected_color = null;
    delta_c = 32;
    num_c = 8;
    color_width = 6;
    color_height = 16;
    setup_layout();
    select_position = 0;
    select_position_x = 0;
    select_position_y = 0;
    gross_position_x = 0;
    gross_position_y = 0;
    gross_index_i = 0;
    gross_index_j = 0;
    gross_index_k = 0;
    fine_index_i = 0;
    fine_index_j = 0;
    fine_index_k = 0;
    modify_r = 0;
    modify_g = 0;
    modify_b = 0;
    debug_flag = false;
    init_y1 = (init_y + 9 * (color_height + 2));
  }
  
  public void update(Graphics g) {
    update_image();
    paint(g);
  }
  
  public void paint(Graphics g) {
    if (im == null)
      update_image();
    if (im == null)
      return;
    g.drawImage(im, 0, 0, this);
  }
  
  public java.awt.Point get_pos(int i, int j, int k) { int y;
    int x;
    int y;
    if (basic_palette_flag) {
      int x = init_x + 15 * i + 100 * j;
      y = init_y + 30 * k;
    }
    else {
      x = k * (color_width + 2) + j * 9 * (color_width + 2);
      y = i * (color_height + 2);
    }
    return new java.awt.Point(x, y);
  }
  

  public boolean check_inside(int x, int y, int i, int j, int k, int init_x, int init_y)
  {
    java.awt.Point P = get_pos(i, j, k);
    if (x < init_x + x) return false;
    if (x > init_x + x + color_width + 2) return false;
    if (y < init_y + y) return false;
    if (y > init_y + y + color_height + 2) return false;
    return true;
  }
  
  public void check_inside_something(int x, int y) {
    if (basic_palette_flag) {
      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          for (int k = 0; k < 5; k++)
            if (check_inside(x, y, i, j, k, 0, 0)) {
              gross_index_i = i;
              gross_index_j = k;
              gross_index_k = j;
            }
        }
      }
      return;
    }
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        for (int k = 0; k < 8; k++) {
          if (check_inside(x, y, i, j, k, init_x, init_y))
          {
            gross_index_i = i;
            gross_index_j = j;
            gross_index_k = k;
            repaint();
            return;
          }
          if (check_inside(x, y, i, j, k, init_x, init_y1))
          {
            fine_index_i = i;
            fine_index_j = j;
            fine_index_k = k;
            repaint();
            return;
          }
        }
      }
    }
  }
  



  private void draw_colored_object(Graphics GC, int cr, int cg, int cb)
  {
    int start_x = init_x + 15 * cr + 100 * cb;
    int start_y = init_y + 30 * cg;
    int cred = colorlist[cr];
    int cgreen = colorlist[cg];
    int cblue = colorlist[cb];
    GC.setColor(new Color(cred, cgreen, cblue));
    GC.fillRect(start_x, start_y, 10, 20);
    if ((cr == gross_index_i) && (cg == gross_index_j) && (cb == gross_index_k))
    {
      GC.setColor(Color.black);
      GC.drawRect(start_x - 3, start_y - 3, 15, 25);
    }
  }
  







  private void update_image()
  {
    int x = init_x;
    int y = init_y;
    

    if (im == null) {
      im = createImage(620, 480);
      GC = im.getGraphics();
    }
    GC.setColor(Color.white);
    GC.fillRect(0, 0, 620, 480);
    int color_b; if (basic_palette_flag) {
      for (int i = 0; i < 5; i++)
        for (int j = 0; j < 5; j++)
          for (int k = 0; k < 5; k++)
            draw_colored_object(GC, k, j, i);
      fine_index_i = 0;
      fine_index_j = 0;
      fine_index_k = 0;
      int color_r = colorlist[gross_index_i];
      int color_g = colorlist[gross_index_j];
      color_b = colorlist[gross_index_k];
    }
    else {
      GC.setColor(Color.black);
      java.awt.Point P = get_pos(gross_index_i, gross_index_j, gross_index_k);
      GC.fillRect(x + init_x - 1, y + init_y - 1, color_width + 2, color_height + 2);
      paint_set(GC, init_y, 0, 0, 0, 32, 8);
      y = init_y + (color_height + 2) * (num_c + 1);
      P = get_pos(fine_index_i, fine_index_j, fine_index_k);
      GC.setColor(Color.black);
      GC.fillRect(x + init_x - 1, y + init_y1 - 1, color_width + 2, color_height + 2);
      paint_set(GC, init_y1, gross_index_i * 32, gross_index_j * 32, gross_index_k * 32, 4, 8);
      
      color_r = 32 * gross_index_i;
      color_g = 32 * gross_index_j;
      color_b = 32 * gross_index_k;
    }
    GC.setColor(Color.black);
    GC.drawString("Base Red = " + color_r, 20, 380);
    GC.drawString("Base Green = " + color_g, 20, 400);
    GC.drawString("Base Blue = " + color_b, 20, 420);
    GC.setColor(new Color(color_r, color_g, color_b));
    GC.fillRect(150, 360, 20, 60);
    GC.setColor(Color.black);
    selected_r = (color_r + 4 * fine_index_i);
    selected_g = (color_g + 4 * fine_index_j);
    selected_b = (color_b + 4 * fine_index_k);
    int color_r = selected_r + modify_r;
    int color_g = selected_g + modify_g;
    int color_b = selected_b + modify_b;
    while (color_r < 0) color_r += 256;
    while (color_g < 0) color_g += 256;
    while (color_b < 0) color_b += 256;
    color_r %= 256;
    color_g %= 256;
    color_b %= 256;
    GC.drawString("Red = " + (selected_r + modify_r) + "(" + modify_r + ")", 200, 380);
    GC.drawString("Green = " + (selected_g + modify_g) + "(" + modify_g + ")", 200, 400);
    GC.drawString("Blue = " + (selected_b + modify_b) + "(" + modify_b + ")", 200, 420);
    GC.setColor(new Color(color_r, color_g, color_b));
    GC.fillRect(170, 360, 20, 60);
    debug_flag = false;
  }
  

  private void paint_set(Graphics g, int y, int f_r, int f_g, int f_b, int del, int n_c)
  {
    for (int i = 0; i < n_c; i++) {
      for (int j = 0; j < n_c; j++) {
        for (int k = 0; k < n_c; k++) {
          java.awt.Point P = get_pos(i, j, k);
          g.setColor(new Color(f_r + i * del, f_g + j * del, f_b + k * del));
          

          g.fillRect(x + init_x, y + y, color_width, color_height);
        }
      }
    }
  }
  

  public void setup_layout()
  {
    Panel p = new Panel();
    setLayout(new java.awt.BorderLayout());
    p.setLayout(new Jeli.Widgets.JeliGridLayout(2, 9));
    p.add(this.NextButton = new JeliButton("Next", hm, this));
    p.add(this.AbortButton = new JeliButton("Abort", hm, this));
    p.add(this.ApplyButton = new JeliButton("Apply", hm, this));
    p.add(this.DoneButton = new JeliButton("Done", hm, this));
    p.add(new JeliButton("", hm, this));
    p.add(new JeliButton("", hm, this));
    p.add(this.IncrementButton = new JeliButton("Inc", hm, this));
    IncrementButton.setAsGetInteger("Inc", "Inc: ", "Inc: ", inc_value, true);
    p.add(this.PaletteButtonA = new JeliButton("Palette:", hm, this));
    PaletteButtonA.setBorderType(7);
    p.add(this.PaletteButton = new JeliButton("Basic", hm, this));
    PaletteButton.setBorderType(11);
    JeliButton B; p.add(B = new JeliButton("Red up", hm, this));
    B.setForeground(Color.red);
    p.add(B = new JeliButton("Red down", hm, this));
    B.setForeground(Color.red);
    p.add(B = new JeliButton("Red 0", hm, this));
    B.setForeground(Color.red);
    p.add(B = new JeliButton("Green up", hm, this));
    B.setForeground(Color.green);
    p.add(B = new JeliButton("Green down", hm, this));
    B.setForeground(Color.green);
    p.add(B = new JeliButton("Green 0", hm, this));
    B.setForeground(Color.green);
    p.add(B = new JeliButton("Blue up", hm, this));
    B.setForeground(Color.blue);
    p.add(B = new JeliButton("Blue down", hm, this));
    B.setForeground(Color.blue);
    p.add(B = new JeliButton("Blue 0", hm, this));
    B.setForeground(Color.blue);
    add("South", p);
    setSize(620, 500);
    setBackground(Color.white);
    im = null;
    validate();
  }
  
  private void buttonByLabel(String arg) {
    if ("<- Left".equals(arg)) {
      System.out.println("Left button pushed");
      select_position_x = ((select_position_x + 63) % 64);
      System.out.println("Fine x Position is " + select_position_x);
      repaint();
    }
    if ("Right ->".equals(arg)) {
      System.out.println("Right button pushed");
      select_position_x = ((select_position_x + 1) % 64);
      System.out.println("Fine x Position is " + select_position_x);
      repaint();
    }
    if ("Up".equals(arg)) {
      System.out.println("Up button pushed");
      select_position_y = ((select_position_y + 7) % 8);
      System.out.println("Fine y Position is " + select_position_y);
      repaint();
    }
    if ("Down".equals(arg)) {
      System.out.println("Down button pushed");
      select_position_y = ((select_position_y + 1) % 8);
      System.out.println("Fine y Position is " + select_position_y);
      repaint();
    }
    if ("L".equals(arg)) {
      System.out.println("L button pushed");
      gross_position_x = ((gross_position_x + 63) % 64);
      System.out.println("Gross x Position is " + gross_position_x);
      repaint();
    }
    if ("R".equals(arg)) {
      System.out.println("R button pushed");
      gross_position_x = ((gross_position_x + 1) % 64);
      System.out.println("Gross x Position is " + gross_position_x);
      repaint();
    }
    if ("U".equals(arg)) {
      System.out.println("U button pushed");
      gross_position_y = ((gross_position_y + 7) % 8);
      System.out.println("Gross y Position is " + gross_position_y);
      repaint();
    }
    if ("D".equals(arg)) {
      System.out.println("D button pushed");
      gross_position_y = ((gross_position_y + 1) % 8);
      System.out.println("Gross y Position is " + gross_position_y);
      repaint();
    }
    if ("Red up".equals(arg)) {
      modify_r += inc_value;
      repaint();
    }
    if ("Red down".equals(arg)) {
      modify_r -= inc_value;
      repaint();
    }
    if ("Red 0".equals(arg)) {
      modify_r = 0;
      repaint();
    }
    if ("Green up".equals(arg)) {
      modify_g += inc_value;
      repaint();
    }
    if ("Green down".equals(arg)) {
      modify_g -= inc_value;
      repaint();
    }
    if ("Green 0".equals(arg)) {
      modify_g = 0;
      repaint();
    }
    if ("Blue up".equals(arg)) {
      modify_b += inc_value;
      repaint();
    }
    if ("Blue down".equals(arg)) {
      modify_b -= inc_value;
      repaint();
    }
    if ("Blue 0".equals(arg)) {
      modify_b = 0;
      repaint();
    }
    if ("Debug".equals(arg)) {
      debug_flag = true;
      repaint();
    }
  }
  
  public void mouseDown(int x, int y) {
    check_inside_something(x, y);
    update_image();
    repaint();
  }
  
  public void jeliButtonPushed(JeliButton bh)
  {
    String lab = bh.getLabel();
    if (bh == AbortButton) {
      setVisible(false);
    } else if (bh == DoneButton) {
      setVisible(false);
      if (callback == null) return;
      callback.color_callback(id, selected_r + modify_r, selected_g + modify_g, selected_b + modify_b);

    }
    else if (bh == ApplyButton) {
      if (callback == null) return;
      callback.color_callback(id, selected_r + modify_r, selected_g + modify_g, selected_b + modify_b);

    }
    else if ((bh == PaletteButton) || (bh == PaletteButtonA)) {
      if (basic_palette_flag) {
        basic_palette_flag = false;
        inc_value = 1;
        PaletteButton.setLabel("Full");
      }
      else {
        basic_palette_flag = true;
        inc_value = 8;
        PaletteButton.setLabel("Basic");
      }
      IncrementButton.setAsGetInteger("Inc", "Inc: ", "Inc: ", inc_value, true);
      gross_index_i = 0;
      gross_index_j = 0;
      gross_index_k = 0;
      update_image();
      repaint();
    }
    else if (bh == NextButton) {
      select_position = ((select_position + 1) % num_c);
      gross_index_k += 1;
      if (gross_index_k >= num_c) {
        gross_index_k = 0;
        gross_index_j += 1;
        if (gross_index_j >= num_c) {
          gross_index_j = 0;
          gross_index_i += 1;
          if (gross_index_i >= num_c)
            gross_index_i = 0;
        }
      }
      repaint();
    }
    else {
      buttonByLabel(lab); } }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() { return "Get Color"; }
  
  public void setClassification(String str) {}
}
