package Jeli.Widgets;

import Jeli.Utility;
import java.awt.Color;
import java.awt.Component;
import java.awt.Panel;
import java.awt.Scrollbar;

public class SliderInteger extends Panel implements JeliButtonCallBack, JeliRefreshable, java.awt.event.AdjustmentListener
{
  private Scrollbar slider;
  private JeliButton doublebutton;
  private JeliButton halfbutton;
  private JeliButton roundbutton;
  private String msg;
  private JeliButton but;
  private int val;
  private int min;
  private int max;
  private int width;
  private int vis;
  private int id;
  private SliderCallBack ap;
  private int format_type;
  private boolean butflag;
  private HelpManager hm;
  private String classification;
  private int backgroundColorNumber = -1;
  private boolean naturalSize = false;
  

  public SliderInteger(int id, int val, int min, int max, int width, String msg, int format_type, boolean butflag, SliderCallBack ap)
  {
    this(id, val, min, max, width, msg, format_type, butflag, ap, null);
  }
  

  public SliderInteger(int id, int val, int min, int max, int width, String msg, int format_type, boolean butflag, SliderCallBack ap, HelpManager hm)
  {
    this.id = id;
    this.val = val;
    this.min = min;
    this.max = max;
    this.width = width;
    this.msg = msg;
    this.ap = ap;
    this.hm = hm;
    this.format_type = format_type;
    this.butflag = butflag;
    classification = msg;
    setvis();
    set_layout();
    slider.addAdjustmentListener(this);
  }
  
  public void setNaturalSize(boolean f) {
    naturalSize = f;
  }
  
  private void set_layout() {
    Panel p = new Panel();
    Panel q = new Panel();
    if (format_type == 0) {
      q.setLayout(new JeliGridLayout(1, 3));
      q.setFont(new java.awt.Font("Times", 0, 10));
      q.add(this.doublebutton = new JeliButton("Double", hm));
      q.add(this.halfbutton = new JeliButton("Half", hm));
      q.add(this.roundbutton = new JeliButton("Round", hm));
      p.setLayout(new JeliGridLayout(1, 2));
      p.add(this.but = new JeliButton(msg + val, hm));
      if (!butflag)
        but.setAsLabel();
      p.add(q);
      setLayout(new JeliGridLayout(1, 2));
      add(p);
      slider = new Scrollbar(0, val, vis, min, max + vis);
      add(getSliderWithBorder());
    }
    else if (format_type == 1) {
      q.setLayout(new JeliGridLayout(1, 3));
      q.setFont(new java.awt.Font("Times", 0, 10));
      q.add(this.doublebutton = new JeliButton("Double", hm));
      q.add(this.halfbutton = new JeliButton("Half", hm));
      q.add(this.roundbutton = new JeliButton("Round", hm));
      setLayout(new JeliGridLayout(1, 3));
      add(this.but = new JeliButton(msg + val, hm));
      if (!butflag)
        but.setAsLabel();
      add(q);
      slider = new Scrollbar(0, val, vis, min, max + vis);
      add(getSliderWithBorder());
    }
    else if (format_type == -1) {
      q.setLayout(new java.awt.GridLayout(1, 1));
      q.setFont(new java.awt.Font("Times", 0, 10));
      q.add(this.roundbutton = new JeliButton("Round", hm));
      doublebutton = new JeliButton("Double", hm);
      halfbutton = new JeliButton("Half", hm);
      p.setLayout(new JeliGridLayout(1, 2));
      p.add(this.but = new JeliButton(msg + val, hm));
      if (!butflag)
        but.setAsLabel();
      p.add(q);
      setLayout(new JeliGridLayout(1, 2));
      add(p);
      
      slider = new Scrollbar(0, val, vis, min, max + vis);
      add(getSliderWithBorder());
    }
    else if (format_type == -2)
    {
      roundbutton = new JeliButton("Round", hm);
      doublebutton = new JeliButton("Double", hm);
      halfbutton = new JeliButton("Half", hm);
      slider = new Scrollbar(0, val, vis, min, max + vis);
      but = new JeliButton(msg + val, hm);
      if (!butflag) {
        but.setAsLabel();
      }
    } else {
      q.setLayout(new JeliGridLayout(1, 3));
      q.setFont(new java.awt.Font("Times", 0, 10));
      q.add(this.doublebutton = new JeliButton("Double", hm));
      q.add(this.halfbutton = new JeliButton("Half", hm));
      q.add(this.roundbutton = new JeliButton("Round", hm));
      setLayout(new JeliGridLayout(1, 2));
      p.setLayout(new JeliGridLayout(1, 2));
      add(this.but = new JeliButton(msg + val, hm));
      if (!butflag)
        but.setAsLabel();
      p.add(q);
      slider = new Scrollbar(0, val, vis, min, max + vis);
      p.add(getSliderWithBorder());
      add(p);
    }
    roundbutton.resetJeliButtonCallBack(this);
    halfbutton.resetJeliButtonCallBack(this);
    doublebutton.resetJeliButtonCallBack(this);
    roundbutton.setPositionCenter();
    halfbutton.setPositionCenter();
    doublebutton.setPositionCenter();
    roundbutton.setColorLinkedComponent(slider);
  }
  
  public Component getRound() {
    return roundbutton;
  }
  
  public Component getDouble() {
    return doublebutton;
  }
  
  public Component getHalf() {
    return halfbutton;
  }
  
  public Component getLabel() {
    return but;
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
  
  public void setAsLabel() {
    but.setAsLabel();
  }
  
  public void centerButton() {
    roundbutton.setPositionCenter();
    doublebutton.setPositionCenter();
    halfbutton.setPositionCenter();
  }
  
  public void setBackground(Color C) {
    super.setBackground(C);
    doublebutton.setBackground(C);
    halfbutton.setBackground(C);
    roundbutton.setBackground(C);
    but.setBackground(C);
    slider.setBackground(C);
  }
  
  public void setBackground(int which) {
    backgroundColorNumber = which;
    super.setBackground(Utility.hm.getColor(which));
    doublebutton.setBackground(which);
    halfbutton.setBackground(which);
    roundbutton.setBackground(which);
    but.setBackground(which);
    slider.setBackground(Utility.hm.getColor(which));
    Utility.addRefreshable(this);
  }
  
  public void refresh()
  {
    if (backgroundColorNumber == -1)
      return;
    Color c = Utility.hm.getColor(backgroundColorNumber);
    super.setBackground(c);
    doublebutton.refresh();
    halfbutton.refresh();
    roundbutton.refresh();
    but.refresh();
    slider.setBackground(c);
  }
  
  public void setStandardBackground(int type, int degree) {
    super.setBackground(Utility.standardColors[type][degree]);
    doublebutton.setStandardBackground(type, degree);
    halfbutton.setStandardBackground(type, degree);
    roundbutton.setStandardBackground(type, degree);
    but.setStandardBackground(type, degree);
    slider.setBackground(Utility.standardColors[type][degree]);
  }
  
  public void setSliderBackground(Color C) {
    slider.setBackground(C);
  }
  

  public void sliderApply() {}
  
  public void sliderReset() {}
  
  public void sliderDouble()
  {
    double_scrollbar();
  }
  
  public void sliderHalf() {
    half_scrollbar();
  }
  
  public void sliderRound() {
    round_scrollbar();
  }
  
  private void double_scrollbar() {
    if (max >= 1073741823) return;
    max *= 2;
    setvis();
    setSliderValues();
    if (ap != null)
      ap.sliderchanged(id, val);
  }
  
  private void half_scrollbar() {
    if (max / 2 < min + 4) return;
    max /= 2;
    setvis();
    if (val > max) {
      val = max;
      set_label(val);
    }
    setSliderValues();
    if (ap != null) {
      ap.sliderchanged(id, val);
    }
  }
  
  private void round_scrollbar() {
    val = slider.getValue();
    int roundvalue = get_round_value(val);
    val = ((val + roundvalue / 2) / roundvalue * roundvalue);
    if (val > max) val = max;
    slider.setValue(val);
    set_label(val);
    if (ap != null)
      ap.sliderchanged(id, val);
  }
  
  private int get_round_value(int v) {
    if (10 * (v / 10) != v) return 10;
    if (v < 50) return 10;
    if (100 * (v / 100) != v) return 100;
    if (v < 500) return 100;
    if (1000 * (v / 1000) != v) return 1000;
    if (v < 5000) return 1000;
    return 10000;
  }
  
  public void set_value(int value) {
    while (value > max) double_scrollbar();
    val = value;
    if (val > max) val = max;
    setSliderValues();
    set_label(val);
    if (ap != null)
      ap.sliderchanged(id, val);
  }
  
  public void sliderEvent() {
    val = slider.getValue();
    setSliderValues();
    set_label(val);
    if (ap != null)
      ap.sliderchanged(id, val);
  }
  
  private void setvis() {
    vis = ((max - min) / 10);
    if (vis < 1) vis = 1;
  }
  
  public int value() {
    return val;
  }
  
  public java.awt.Dimension preferredSliderSize() {
    return new java.awt.Dimension(width, 10);
  }
  
  public java.awt.Dimension getPreferredSize() {
    if (naturalSize)
      return super.getPreferredSize();
    return new java.awt.Dimension(width, 10);
  }
  
  private void set_label(int v) {
    but.setLabel(msg + v);
  }
  
  public void buttonPushed(java.awt.Button b, String str) {
    if (str.equals("Apply")) {
      sliderApply();
    } else if (str.equals("Reset")) {
      sliderReset();
    } else if (str.equals("Double")) {
      sliderDouble();
    } else if (str.equals("Half")) {
      sliderHalf();
    } else if (str.equals("Round")) {
      sliderRound();
    } else if (str.startsWith(msg))
      ap.sliderchanged(id);
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == roundbutton) {
      round_scrollbar();
    } else if (bh == doublebutton) {
      double_scrollbar();
    } else if (bh == halfbutton) {
      half_scrollbar();
    }
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
  
  public void disposeSlider() {
    hm.removeButton(doublebutton);
    hm.removeButton(halfbutton);
    hm.removeButton(roundbutton);
    hm.removeButton(but);
  }
  
  public void enableSlider() {
    if (doublebutton != null)
      doublebutton.enableJeliButton();
    if (halfbutton != null)
      halfbutton.enableJeliButton();
    if (roundbutton != null)
      roundbutton.enableJeliButton();
    if (but != null)
      but.enableJeliButton();
    if (slider != null)
      slider.setEnabled(true);
  }
  
  public void disableSlider() {
    if (doublebutton != null)
      doublebutton.disableJeliButton();
    if (halfbutton != null)
      halfbutton.disableJeliButton();
    if (roundbutton != null)
      roundbutton.disableJeliButton();
    if (but != null)
      but.disableJeliButton();
    if (slider != null)
      slider.setEnabled(false);
  }
  
  public Panel getButtonPanel() {
    Panel p = new Panel();
    p.setLayout(new JeliGridLayout(1, 3));
    p.add(roundbutton);
    p.add(doublebutton);
    p.add(halfbutton);
    return p;
  }
}
