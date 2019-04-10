package Jeli.Widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Scrollbar;

public class SliderFraction extends Panel implements JeliButtonCallBack, java.awt.event.AdjustmentListener
{
  Scrollbar slider;
  JeliButton roundbutton;
  HelpManager hm;
  String msg;
  JeliButton but;
  int val;
  int min;
  int max;
  int width;
  int scale;
  int vis;
  int id;
  SliderCallBack ap;
  int format;
  boolean butflag;
  private String classification;
  private boolean naturalSize = true;
  
  public SliderFraction(int id, double val, int width, String msg, int format, boolean butflag, SliderCallBack ap)
  {
    this(id, (int)(val * 1000.0D), 1000, width, msg, format, butflag, ap, null);
  }
  
  public SliderFraction(int id, double val, int width, String msg, int format, boolean butflag, SliderCallBack ap, HelpManager hm)
  {
    this(id, (int)(val * 1000.0D), 1000, width, msg, format, butflag, ap, hm);
  }
  
  public SliderFraction(int id, int val, int scale, int width, String msg, int format, boolean butflag, SliderCallBack ap)
  {
    this(id, val, scale, width, msg, format, butflag, ap, null);
  }
  

  public SliderFraction(int id, int val, int scale, int width, String msg, int format, boolean butflag, SliderCallBack ap, HelpManager hm)
  {
    Panel q = new Panel();
    this.val = val;
    min = 0;
    max = scale;
    this.scale = scale;
    this.width = width;
    this.msg = msg;
    this.id = id;
    this.ap = ap;
    this.format = format;
    this.butflag = butflag;
    this.hm = hm;
    classification = msg;
    setvis();
    if (format == 0) {
      q.setLayout(new JeliGridLayout(1, 2));
      setLayout(new JeliGridLayout(1, 2));
      q.add(this.but = new JeliButton(msg + val, hm));
      if (!butflag)
        but.setAsLabel();
      q.add(this.roundbutton = new JeliButton("Round", hm));
      add(q);
      slider = new Scrollbar(0, val, vis, min, max + vis);
      add(getSliderWithBorder());
    }
    else if (format == 1) {
      q.setLayout(new JeliGridLayout(1, 2));
      setLayout(new JeliGridLayout(1, 2));
      q.add(this.roundbutton = new JeliButton("Round", hm));
      slider = new Scrollbar(0, val, vis, min, max + vis);
      q.add(getSliderWithBorder());
      add(this.but = new JeliButton(msg + val, hm));
      if (!butflag)
        but.setAsLabel();
      add(q);
    }
    else if (format == -2) {
      q.setLayout(new JeliGridLayout(1, 2));
      setLayout(new JeliGridLayout(1, 2));
      roundbutton = new JeliButton("Round", hm);
      slider = new Scrollbar(0, val, vis, min, max + vis);
      but = new JeliButton(msg + val, hm);
      if (!butflag) {
        but.setAsLabel();
      }
    }
    else {
      setLayout(new JeliGridLayout(1, 3));
      add(this.but = new JeliButton(msg + val, hm));
      if (!butflag)
        but.setAsLabel();
      add(this.roundbutton = new JeliButton("Round", hm));
      slider = new Scrollbar(0, val, vis, min, max + vis);
      add(getSliderWithBorder());
    }
    
    roundbutton.resetJeliButtonCallBack(this);
    roundbutton.setPositionCenter();
    roundbutton.setColorLinkedComponent(slider);
    validate();
    slider.addAdjustmentListener(this);
  }
  
  public void setNaturalSize(boolean f) {
    naturalSize = f;
  }
  
  public void setAsLabel() {
    but.setAsLabel();
  }
  
  public void centerButton() {
    roundbutton.setPositionCenter();
  }
  
  public void setBackground(Color C) {
    super.setBackground(C);
    but.setBackground(C);
    roundbutton.setBackground(C);
    slider.setBackground(C);
  }
  
  public void setStandardBackground(int type, int degree) {
    super.setBackground(Jeli.Utility.standardColors[type][degree]);
    roundbutton.setStandardBackground(type, degree);
    but.setStandardBackground(type, degree);
    slider.setBackground(Jeli.Utility.standardColors[type][degree]);
  }
  
  public void buttonPushed(java.awt.Button b, String str) {
    if (str.equals("Round"))
      round_scrollbar();
    if ((str.startsWith(msg)) && 
      (ap != null)) {
      ap.sliderchanged(id);
    }
  }
  
  void round_scrollbar() {
    val = slider.getValue();
    int roundvalue = get_round_value(val);
    val = ((val + roundvalue / 2) / roundvalue * roundvalue);
    if (val > max) val = max;
    slider.setValue(val);
    if (ap != null)
      ap.sliderchanged(id, scaled_value());
    set_label();
  }
  
  int get_round_value(int v) {
    if (10 * (v / 10) != v) return 10;
    if (v < 50) return 10;
    if (100 * (v / 100) != v) return 100;
    if (v < 500) return 100;
    if (1000 * (v / 1000) != v) return 1000;
    if (v < 5000) return 1000;
    return 10000;
  }
  
  public void set_value(double value) {
    val = ((int)(value * scale));
    setSliderValues();
    set_label();
  }
  
  public void sliderEvent() {
    val = slider.getValue();
    setSliderValues();
    set_label();
    if (ap != null)
      ap.sliderchanged(id, scaled_value());
  }
  
  private void set_label() {
    but.setLabel(msg + scaled_value());
  }
  
  void setvis() {
    vis = ((max - min) / 10);
    if (vis < 1) vis = 1;
  }
  
  private double scaled_value() {
    return val / scale;
  }
  
  public double get_value() {
    return val / scale;
  }
  
  public Dimension preferredSliderSize() {
    return new Dimension(width, 10);
  }
  
  public Dimension getPreferredSize() {
    if (naturalSize)
      return super.getPreferredSize();
    return new Dimension(width, 10);
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == roundbutton) {
      round_scrollbar();
      return;
    }
  }
  
  public void disableSlider() {
    slider.setEnabled(false);
    roundbutton.disableJeliButton();
    but.disableJeliButton();
  }
  
  public void enableSlider() {
    slider.setEnabled(true);
    roundbutton.enableJeliButton();
    but.enableJeliButton();
  }
  

  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification()
  {
    return classification;
  }
  
  public void setClassification(String str) {
    classification = str;
    if (but != null)
      but.setClassification(str);
  }
  
  public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e) {
    sliderEvent();
  }
  
  private void setSliderValues() {
    slider.setValues(val, vis, min, max + vis);
  }
  
  public Component getRound() {
    return roundbutton;
  }
  
  public Component getSlider() {
    return slider;
  }
  
  public Component getSliderWithBorder() {
    Panel p = new JeliInsetsPanel(1, Color.black);
    p.setLayout(new java.awt.GridLayout(1, 1));
    p.add(slider);
    return p;
  }
  
  public Component getLabel() {
    return but;
  }
}
