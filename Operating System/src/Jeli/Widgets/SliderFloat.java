package Jeli.Widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Scrollbar;

public class SliderFloat extends Panel implements JeliButtonCallBack, JeliRefreshable, java.awt.event.AdjustmentListener, DynamicHelpMessenger
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
  private int scale;
  private int scaled_value;
  private int vis;
  private int id;
  private SliderCallBack ap;
  private int format_type;
  private boolean butflag;
  private HelpManager hm;
  private String classification;
  private int backgroundColorNumber = -1;
  private boolean naturalSize = true;
  

  public SliderFloat(int id, int val, int min, int max, int scale, int width, String msg, int format_type, boolean butflag, SliderCallBack ap)
  {
    this(id, val, min, max, scale, width, msg, format_type, butflag, ap, null);
  }
  

  public SliderFloat(int id, int val, int min, int max, int scale, int width, String msg, int format_type, boolean butflag, SliderCallBack ap, HelpManager hm)
  {
    this.id = id;
    this.val = val;
    this.min = min;
    this.max = max;
    this.scale = scale;
    this.width = width;
    this.msg = msg;
    this.ap = ap;
    this.hm = hm;
    this.format_type = format_type;
    this.butflag = butflag;
    classification = msg;
    setvis();
    scaled_value = (val / scale);
    if (scaled_value <= 0) scaled_value = 1;
    set_layout();
    slider.addAdjustmentListener(this);
  }
  
  public void setNaturalSize(boolean f) {
    naturalSize = f;
  }
  
  private void set_layout() {
    Panel p = new Panel();
    Panel q = new Panel();
    q.setLayout(new JeliGridLayout(1, 3));
    q.setFont(new java.awt.Font("Times", 0, 10));
    if (format_type == -2) {
      doublebutton = new JeliButton("Double", hm);
      halfbutton = new JeliButton("Half", hm);
      roundbutton = new JeliButton("Round", hm);
    }
    else {
      q.add(this.doublebutton = new JeliButton("Double", hm));
      q.add(this.halfbutton = new JeliButton("Half", hm));
      q.add(this.roundbutton = new JeliButton("Round", hm));
    }
    if (format_type == 0) {
      p.setLayout(new JeliGridLayout(1, 2));
      p.add(this.but = new JeliButton(msg + convert_from_int_to_double(val), hm));
      if (!butflag)
        but.setAsLabel();
      p.add(q);
      setLayout(new JeliGridLayout(1, 2));
      add(p);
      slider = new Scrollbar(0, val, vis, min, max + vis);
      add(getSliderWithBorder());
    }
    else if (format_type == 1) {
      setLayout(new JeliGridLayout(1, 3));
      add(this.but = new JeliButton(msg + convert_from_int_to_double(val), hm));
      if (!butflag)
        but.setAsLabel();
      add(q);
      slider = new Scrollbar(0, val, vis, min, max + vis);
      add(getSliderWithBorder());
    }
    else if (format_type == -2) {
      setLayout(new JeliGridLayout(1, 3));
      but = new JeliButton(msg + convert_from_int_to_double(val), hm);
      if (!butflag)
        but.setAsLabel();
      slider = new Scrollbar(0, val, vis, min, max + vis);
    }
    else {
      setLayout(new JeliGridLayout(1, 2));
      p.setLayout(new JeliGridLayout(1, 2));
      add(this.but = new JeliButton(msg + convert_from_int_to_double(val), hm));
      if (!butflag)
        but.setAsLabel();
      p.add(q);
      slider = new Scrollbar(0, val, vis, min, max + vis);
      add(getSliderWithBorder());
      add(p);
    }
    doublebutton.setDynamicHelpMessenger(this);
    halfbutton.setDynamicHelpMessenger(this);
    roundbutton.setDynamicHelpMessenger(this);
    but.setDynamicHelpMessenger(this);
    roundbutton.resetJeliButtonCallBack(this);
    halfbutton.resetJeliButtonCallBack(this);
    doublebutton.resetJeliButtonCallBack(this);
    doublebutton.setPositionCenter();
    halfbutton.setPositionCenter();
    roundbutton.setPositionCenter();
    roundbutton.setColorLinkedComponent(slider);
  }
  
  public void setCallBack(SliderCallBack ap) {
    this.ap = ap;
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
    
    Color c = Jeli.Utility.hm.getColor(which);
    super.setBackground(c);
    doublebutton.setBackground(which);
    halfbutton.setBackground(which);
    roundbutton.setBackground(which);
    but.setBackground(which);
    slider.setBackground(c);
    Jeli.Utility.addRefreshable(this);
  }
  
  public void refresh() {
    if (backgroundColorNumber == -1) {
      return;
    }
    Color c = Jeli.Utility.hm.getColor(backgroundColorNumber);
    super.setBackground(c);
    doublebutton.refresh();
    halfbutton.refresh();
    roundbutton.refresh();
    but.refresh();
    slider.setBackground(c);
  }
  
  public void setStandardBackground(int type, int degree) {
    super.setBackground(Jeli.Utility.standardColors[type][degree]);
    doublebutton.setStandardBackground(type, degree);
    halfbutton.setStandardBackground(type, degree);
    roundbutton.setStandardBackground(type, degree);
    but.setStandardBackground(type, degree);
    slider.setBackground(Jeli.Utility.standardColors[type][degree]);
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
    ap.sliderchanged(id, convert_from_int_to_double(val));
  }
  
  public void setMax(double m)
  {
    if (m * scale >= 1.073741823E9D) return;
    int m1 = (int)m * scale;
    if (val > m1) return;
    max = m1;
    setvis();
    setSliderValues();
  }
  
  private void half_scrollbar() {
    if (max / 2 < min + 4) return;
    max /= 2;
    setvis();
    if (val > max) {
      val = max;
      scaled_value = (val / scale);
      set_label(val);
    }
    setSliderValues();
    ap.sliderchanged(id, convert_from_int_to_double(val));
  }
  
  private void round_scrollbar()
  {
    val = slider.getValue();
    int roundvalue = get_round_value(val);
    val = ((val + roundvalue / 2) / roundvalue * roundvalue);
    if (val > max) val = max;
    slider.setValue(val);
    scaled_value = (val / scale);
    set_label(val);
    ap.sliderchanged(id, convert_from_int_to_double(val));
  }
  

  private int get_complete_round_value(int v)
  {
    int roundval = get_round_value(v);
    int newv = (v + roundval / 2) / roundval * roundval;
    if (newv > max) newv = max;
    return newv;
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
  
  private void set_value(int value) {
    while (value > max) double_scrollbar();
    val = value;
    if (val > max) val = max;
    scaled_value = (val / scale);
    if (scaled_value <= 0) scaled_value = 1;
    setSliderValues();
    set_label(val);
    ap.sliderchanged(id, convert_from_int_to_double(val));
  }
  
  public void set_value(double val) {
    set_value((int)(val * scale));
  }
  
  public void sliderEvent() {
    val = slider.getValue();
    setSliderValues();
    set_label(val);
    scaled_value = (val / scale);
    if (scaled_value <= 0) scaled_value = 1;
    ap.sliderchanged(id, convert_from_int_to_double(val));
  }
  
  private void setvis() {
    vis = ((max - min) / 10);
    if (vis < 1) vis = 1;
  }
  
  public int scaled_value() {
    return scaled_value;
  }
  
  public int value() {
    return val;
  }
  
  public double valueDouble() {
    return convert_from_int_to_double(val);
  }
  
  public Dimension preferredSliderSize() {
    return new Dimension(width, 10);
  }
  
  public Dimension getPreferredSize() {
    if (naturalSize)
      return super.getPreferredSize();
    return new Dimension(width, 10);
  }
  
  private double convert_from_int_to_double(int i) {
    return i / scale;
  }
  
  private void set_label(int v) {
    but.setLabel(msg + convert_from_int_to_double(v));
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
  
  public void disableSlider() {
    slider.setEnabled(false);
    doublebutton.disableJeliButton();
    halfbutton.disableJeliButton();
    roundbutton.disableJeliButton();
    but.disableJeliButton();
  }
  
  public void enableSlider() {
    slider.setEnabled(true);
    doublebutton.enableJeliButton();
    halfbutton.enableJeliButton();
    roundbutton.enableJeliButton();
    but.enableJeliButton();
  }
  
  public String generateDynamicHelpMessage(Component com) {
    if (com == doublebutton) {
      return "Double the maximum value from " + convert_from_int_to_double(max) + " to " + convert_from_int_to_double(2 * max);
    }
    
    if (com == halfbutton) {
      if (max / 2 < min + 4) return "Cannot reduce scale";
      if (val > max / 2) {
        return "Half the maximum value from " + convert_from_int_to_double(max) + " to " + convert_from_int_to_double(max / 2) + "\n and change the value to " + convert_from_int_to_double(max / 2);
      }
      


      return "Half the maximum value from " + convert_from_int_to_double(max) + " to " + convert_from_int_to_double(max / 2);
    }
    

    if (com == roundbutton) {
      return "Round value from " + convert_from_int_to_double(val) + " to " + convert_from_int_to_double(get_complete_round_value(val));
    }
    
    if (com == but) {
      return msg + convert_from_int_to_double(val);
    }
    
    return null;
  }
  
  public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e) {
    sliderEvent();
  }
  
  private void setSliderValues()
  {
    slider.setValues(val, vis, min, max + vis);
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
  
  public Component getModifyButtons()
  {
    Panel p = new Panel();
    p.setLayout(new JeliGridLayout(1, 3));
    p.add(roundbutton);
    p.add(doublebutton);
    p.add(halfbutton);
    return p;
  }
  
  public void disposeSlider() {
    hm.removeButton(doublebutton);
    hm.removeButton(halfbutton);
    hm.removeButton(roundbutton);
    hm.removeButton(but);
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
