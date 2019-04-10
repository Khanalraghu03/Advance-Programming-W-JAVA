package Jeli.Widgets;

import Jeli.Utility;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

public class StringArea
  extends Canvas
{
  String[] strs;
  int selected;
  int size;
  Font font;
  FontMetrics fm;
  int textAscent;
  int textDescent;
  int textincrement;
  int text_inity;
  int noscroll;
  int textlines;
  StringAreaAdjust adj;
  String fontname;
  int fontstyle;
  int fontsize;
  int format_width;
  VoidCallBack cb;
  int id;
  final int text_indent = 10;
  boolean user_scroll_flag;
  int scrolled_lines;
  int scrollbox_x;
  final int scrollup_box_y = 5;
  int scrolldown_box_y = 5;
  final int scrollbox_size_x = 10;
  int scrollbox_size_y;
  boolean userscroll;
  boolean initial_paint_flag;
  boolean format_required;
  HelpManager hm;
  
  public StringArea(String[] strs, Font font, int noscroll, boolean userscroll, HelpManager hm)
  {
    this.strs = strs;
    this.font = font;
    this.noscroll = noscroll;
    this.userscroll = userscroll;
    cb = null;
    this.hm = hm;
    size = strs.length;
    selected = -1;
    fontname = font.getName();
    fontstyle = font.getStyle();
    set_font_values();
    setFont(font);
    textlines = -1;
    adj = new StringAreaAdjust(strs[0], this);
    user_scroll_flag = userscroll;
    scrolled_lines = 0;
    initial_paint_flag = true;
    format_required = false;
  }
  
  public void setCallBack(VoidCallBack cb, int id) {
    this.cb = cb;
    this.id = id;
  }
  
  public void hideit() {
    adj.setVisible(false);
  }
  
  void set_font_values() {
    fontsize = font.getSize();
    fm = getFontMetrics(font);
    textAscent = fm.getMaxAscent();
    textDescent = fm.getDescent();
    textincrement = (textAscent + textDescent + 2);
    text_inity = (textAscent + 4);
  }
  






  public void paint(Graphics g)
  {
    int displines = (getBoundsheight - 3) / textincrement;
    
    initial_paint_flag = false;
    if (format_required)
    {
      format_lines();
    }
    Graphics g1 = g.create();
    g1.setColor(Color.red);
    int width = getBoundswidth;
    int height = getBoundsheight;
    g.drawRect(1, 1, width - 3, height - 3);
    
    if ((selected >= 0) && (selected < size) && (selected >= displines)) {
      int dif = selected - displines + 1;
      for (int i = 0; i < noscroll; i++)
        g.drawString(strs[i], 10, text_inity + i * textincrement);
      for (i = noscroll + dif; i < size; i++) {
        if (i == selected) {
          g1.drawString(strs[i], 10, text_inity + (i - dif) * textincrement);
        }
        else
          g.drawString(strs[i], 10, text_inity + (i - dif) * textincrement);
      }
      return;
    }
    if (user_scroll_flag)
    {
      if (scrolled_lines < 0) scrolled_lines = 0;
      if (scrolled_lines + displines > size)
        scrolled_lines = (size - displines);
      if (size <= displines) scrolled_lines = 0;
      if (scrolled_lines < 0) scrolled_lines = 0;
      int dif = scrolled_lines;
      for (int i = 0; i < noscroll; i++)
        g.drawString(strs[i], 10, text_inity + i * textincrement);
      for (i = noscroll + dif; i < size; i++) {
        if (i == selected) {
          g1.drawString(strs[i], 10, text_inity + (i - dif) * textincrement);
        }
        else
          g.drawString(strs[i], 10, text_inity + (i - dif) * textincrement);
      }
      scrollbox_size_y = ((height - 15) / 2);
      scrollbox_x = (width - 10 - 5);
      scrolldown_box_y = (height - scrollbox_size_y - 5);
      g1.drawRect(scrollbox_x, 5, 10, scrollbox_size_y);
      
      g1.drawRect(scrollbox_x, scrolldown_box_y, 10, scrollbox_size_y);
      
      return;
    }
    for (int i = 0; i < size; i++) {
      if (i == selected) {
        g1.drawString(strs[i], 10, text_inity + i * textincrement);
      } else
        g.drawString(strs[i], 10, text_inity + i * textincrement);
    }
  }
  
  boolean check_in_rect(int x, int y, int bx, int by, int bw, int bh) {
    if (x < bx) return false;
    if (x > bx + bw) return false;
    if (y < by) return false;
    if (y > by + bh) return false;
    return true;
  }
  
  boolean check_in_scroll_up(int x, int y) {
    if (!userscroll) return false;
    return check_in_rect(x, y, scrollbox_x, 5, 10, scrollbox_size_y);
  }
  
  boolean check_in_scroll_down(int x, int y)
  {
    if (!userscroll) return false;
    return check_in_rect(x, y, scrollbox_x, scrolldown_box_y, 10, scrollbox_size_y);
  }
  
  boolean check_between_scroll(int x, int y)
  {
    if (!userscroll) return false;
    if (x >= scrollbox_x) return true;
    return false;
  }
  
  public void set_selected(int i) {
    if (i == -1) return;
    selected = i;
    repaint();
  }
  
  int getlines(String str)
  {
    int lines = 0;
    for (int i = 0; i < str.length(); i++)
      if (str.charAt(i) == '\n') lines++;
    return lines;
  }
  





  public void setText(String str)
  {
    int strsize = getlines(str);
    selected = -1;
    size = 0;
    strs = new String[strsize];
    int strstart = 0;
    int i = 0; for (int j = 0; i < str.length(); i++)
      if (str.charAt(i) == '\n') {
        strs[(j++)] = str.substring(strstart, i);
        strstart = i + 1;
      }
    size = strsize;
    repaint();
  }
  
  public void try_resize(int n)
  {
    if (n <= 0) { return;
    }
    textlines = n;
    setSize(getBoundswidth, n * textincrement + 3);
  }
  
  public void setWidth(int w) {
    setSize(w, textlines * textincrement + 3);
  }
  
  public void try_resize() {
    if (textlines < 0) { return;
    }
    try_resize(textlines);
  }
  
  public int getsize() {
    if (textlines < 0) return 0;
    return textlines * textincrement + 3;
  }
  
  void set_text_lines_if_necessary() {
    if (textlines > 0) return;
    textlines = ((getBoundsheight - 3) / textincrement);
  }
  
  void set_text_lines() {
    textlines = ((getBoundsheight - 3) / textincrement);
  }
  
  public void mouseDownCanvas(int x, int y)
  {
    if (check_in_scroll_up(x, y)) {
      scrolled_lines += 1;
      repaint();
    }
    else if (check_in_scroll_down(x, y)) {
      scrolled_lines -= 1;
      repaint();
    }
    else if (!check_between_scroll(x, y)) {
      set_text_lines_if_necessary();
      adj.showit();
    }
  }
  
  public void set_bigger_font() {
    font = new Font(fontname, fontstyle, fontsize + 1);
    setFont(font);
    set_font_values();
    try_resize();
    
    repaint();
  }
  
  public void set_bigger_font(int n) {
    font = new Font(fontname, fontstyle, fontsize + n);
    setFont(font);
    set_font_values();
    try_resize();
    repaint();
  }
  
  public void set_smaller_font() {
    if (fontsize < 6) return;
    font = new Font(fontname, fontstyle, fontsize - 1);
    setFont(font);
    set_font_values();
    try_resize();
    
    repaint();
  }
  
  public void set_more_lines() {
    set_text_lines_if_necessary();
    try_resize(textlines + 1);
  }
  
  public void set_fewer_lines()
  {
    set_text_lines_if_necessary();
    try_resize(textlines - 1);
  }
  
  public void format_lines()
  {
    if (initial_paint_flag) {
      format_required = true;
      
      return;
    }
    format_required = false;
    format_width = (getBoundswidth - 20);
    if (userscroll) format_width = (format_width - 10 - 2);
    strs = Utility.formatArray(strs, format_width, fm);
    size = strs.length;
    repaint();
  }
  
  public void resize_frame() {
    if (cb != null) cb.voidNotify(id);
  }
}
