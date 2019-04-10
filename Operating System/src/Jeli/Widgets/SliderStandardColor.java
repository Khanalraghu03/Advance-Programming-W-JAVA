package Jeli.Widgets;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.util.Vector;

public class SliderStandardColor implements JeliButtonCallBack, java.awt.event.AdjustmentListener, Jeli.Get.ButtonPopupCallBack
{
  private static String butString = " Green: 255 ";
  private static String[] typeNames = { "Gray", "Red", "Green", "Yellow", "Blue", "Magenta", "Cyan" };
  
  private static String[] degreeNames = { "Full", "Medium", "Light", "VeryLight" };
  private Scrollbar sliderAll;
  private JeliButton setButton;
  private JeliButton resetButton;
  private String msg;
  private JeliButton sliderName;
  private int width;
  private ColorSliderCallBack ap;
  private HelpManager hm;
  private String classification;
  private Panel redPanel;
  private Panel greenPanel;
  private Panel bluePanel;
  private JeliLabel oldColorLabel;
  private JeliLabel newColorLabel;
  private JeliLabel redValLabel;
  private JeliLabel greenValLabel;
  private JeliLabel blueValLabel;
  private JeliLabel typeLabel;
  private int vis = 10;
  private int intensity = 200;
  private Color myRed = new Color(255, intensity, intensity);
  private Color myGreen = new Color(intensity, 255, intensity);
  private Color myBlue = new Color(intensity, intensity, 255);
  private int backThreshhold = 380;
  private SliderColorInfo sci;
  private Vector scis;
  private Jeli.Get.GetButtonPopup chooser = null;
  private String[] chooserNames = null;
  private int CHOOSER_ID = 1;
  private int type;
  private int currentValue;
  private Panel longPanel = null;
  

  public SliderStandardColor(int id, int type, int val, int width, String msg, ColorSliderCallBack ap, HelpManager hm)
  {
    sci = new SliderColorInfo(id, msg, typeToRed(type, val), typeToGreen(type, val), typeToBlue(type, val));
    
    scis = new Vector();
    scis.addElement(sci);
    this.width = width;
    this.msg = msg;
    this.ap = ap;
    this.hm = hm;
    this.type = type;
    currentValue = val;
    classification = msg;
    sliderName = new JeliButton(msg, hm, this);
    sliderName.setAsLabel();
    setWidgets();
    setValueLabels();
    sliderAll.addAdjustmentListener(this);
    setNewColor();
    setOldColor();
    setTypeLabel(type);
  }
  
  public void resetType(int t) {
    if ((t < 0) || (t >= 7))
      return;
    type = t;
    setTypeLabel(type);
    setValueLabels(currentValue);
  }
  
  private int typeToRed(int type, int val) {
    return (type & 0x1) != 0 ? 255 : val;
  }
  
  private int typeToGreen(int type, int val) {
    return (type & 0x2) != 0 ? 255 : val;
  }
  
  private int typeToBlue(int type, int val) {
    return (type & 0x4) != 0 ? 255 : val;
  }
  

  private void setWidgets()
  {
    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    
    fill = 1;
    gridx = 0;
    gridy = 0;
    weightx = 0.0D;
    weighty = 0.0D;
    oldColorLabel = new JeliLabel("old", hm);
    oldColorLabel.setPositionCenter();
    newColorLabel = new JeliLabel("new", hm);
    newColorLabel.setPositionCenter();
    setButton = new JeliButton("Set", hm, this);
    setButton.setPositionCenter();
    resetButton = new JeliButton("Reset", hm, this);
    resetButton.setPositionCenter();
    sliderAll = new Scrollbar(0, currentValue, vis, 0, 255 + vis);
    redValLabel = new JeliLabel("");
    greenValLabel = new JeliLabel(" Green 255 ");
    blueValLabel = new JeliLabel("");
    typeLabel = new JeliLabel(" Magenta ");
  }
  
  public void setName(String str)
  {
    sliderName.setLabel(str);
  }
  
  private void setTypeLabel(int type) {
    typeLabel.setLabel(getTypeName(type));
  }
  
  public void setDegreeLabel(int degree) {
    sliderName.setLabel(getDegreeName(degree));
  }
  
  public String getTypeName(int type) {
    if ((type < 0) || (type >= 7))
      return "unknown";
    return typeNames[type];
  }
  
  public String getDegreeName(int degree) {
    if ((degree < 0) || (degree >= 4))
      return "unknown";
    return degreeNames[degree];
  }
  










  public Panel getInfoPanel()
  {
    Panel p = new Panel();
    p.setLayout(new JeliGridLayout(1, 5));
    p.add(sliderName);
    p.add(setButton);
    p.add(resetButton);
    p.add(oldColorLabel);
    p.add(newColorLabel);
    return p;
  }
  
  public void addAll(Container p) {
    p.add(sliderName);
    p.add(typeLabel);
    p.add(setButton);
    p.add(resetButton);
    p.add(oldColorLabel);
    p.add(newColorLabel);
    p.add(redValLabel);
    p.add(greenValLabel);
    p.add(blueValLabel);
    p.add(Jeli.Utility.getBorderedPanel(sliderAll));
  }
  
  public Panel getLongPanel() {
    if (longPanel != null)
      return longPanel;
    Panel p = new Panel();
    

    GridBagLayout gridbag = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    
    fill = 1;
    gridx = 0;
    gridy = 0;
    
    p.setLayout(gridbag);
    
    sliderName.setButtonSize(butString);
    gridbag.setConstraints(sliderName, c);
    p.add(sliderName);
    gridx += 1;
    typeLabel.setButtonSize(butString);
    gridbag.setConstraints(typeLabel, c);
    p.add(typeLabel);
    gridx += 1;
    setButton.setButtonSize(butString);
    gridbag.setConstraints(setButton, c);
    p.add(setButton);
    gridx += 1;
    resetButton.setButtonSize(butString);
    gridbag.setConstraints(resetButton, c);
    p.add(resetButton);
    gridx += 1;
    oldColorLabel.setButtonSize(butString);
    gridbag.setConstraints(oldColorLabel, c);
    p.add(oldColorLabel);
    gridx += 1;
    newColorLabel.setButtonSize(butString);
    gridbag.setConstraints(newColorLabel, c);
    p.add(newColorLabel);
    gridx += 1;
    redValLabel.setButtonSize(butString);
    gridbag.setConstraints(redValLabel, c);
    p.add(redValLabel);
    gridx += 1;
    greenValLabel.setButtonSize(butString);
    gridbag.setConstraints(greenValLabel, c);
    p.add(greenValLabel);
    gridx += 1;
    blueValLabel.setButtonSize(butString);
    gridbag.setConstraints(blueValLabel, c);
    p.add(blueValLabel);
    gridx += 1;
    SmallComponentPanel sp = new SmallComponentPanel(sliderAll);
    sp.setRelativeComponent(greenValLabel);
    sp.setRelativeSize(2.0D);
    sp.add(Jeli.Utility.getBorderedPanel(sliderAll));
    gridx += 1;
    gridbag.setConstraints(sp, c);
    p.add(sp);
    longPanel = p;
    return p;
  }
  
  public java.awt.Dimension preferredSliderSize() {
    return new java.awt.Dimension(width, 10);
  }
  
  public java.awt.Dimension getPreferredSize() {
    return new java.awt.Dimension(width, 10);
  }
  
  public void jeliButtonPushed(JeliButton bh)
  {
    if (bh == setButton) {
      setColors();
    } else if (bh == resetButton) {
      resetColors();
    } else if ((bh == sliderName) && 
      (chooser != null)) {
      chooser.showit();
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
  }
  
  public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e) {
    if (e.getSource() == sliderAll) {
      currentValue = sliderAll.getValue();
      setValueLabels(currentValue);
    }
    setNewColor();
  }
  
  public void setValue(int val) {
    currentValue = val;
    sliderAll.setValue(val);
  }
  
  public void resetColor()
  {
    sci.valSavedRed = typeToRed(type, currentValue);
    sci.valSavedGreen = typeToGreen(type, currentValue);
    sci.valSavedBlue = typeToBlue(type, currentValue);
    setNewColor();
    setOldColor();
  }
  
  private void setValueLabels(int currentValue) {
    sci.valRed = typeToRed(type, currentValue);
    sci.valGreen = typeToGreen(type, currentValue);
    sci.valBlue = typeToBlue(type, currentValue);
    setValueLabels();
  }
  
  private void setNewColor()
  {
    Color c = new Color(sci.valRed, sci.valGreen, sci.valBlue);
    newColorLabel.setBackground(c);
    if (sci.valRed + sci.valGreen + sci.valBlue < backThreshhold) {
      newColorLabel.setForeground(Color.white);
    } else {
      newColorLabel.setForeground(Color.black);
    }
  }
  
  private void setOldColor() {
    Color c = new Color(sci.valSavedRed, sci.valSavedGreen, sci.valSavedBlue);
    oldColorLabel.setBackground(c);
    if (sci.valSavedRed + sci.valSavedGreen + sci.valSavedBlue < backThreshhold) {
      oldColorLabel.setForeground(Color.white);
    } else
      oldColorLabel.setForeground(Color.black);
  }
  
  private void setValueLabels() {
    redValLabel.setLabel("Red: " + sci.valRed);
    greenValLabel.setLabel("Green: " + sci.valGreen);
    blueValLabel.setLabel("Blue: " + sci.valBlue);
  }
  
  private void setColors() {
    sci.valSavedRed = sci.valRed;
    sci.valSavedGreen = sci.valGreen;
    sci.valSavedBlue = sci.valBlue;
    setOldColor();
    if (ap != null)
      ap.colorSliderCallBack(sci.id, sci.valRed, sci.valGreen, sci.valBlue);
  }
  
  private void resetColors() {
    sci.valRed = sci.valSavedRed;
    sci.valGreen = sci.valSavedGreen;
    sci.valBlue = sci.valSavedBlue;
    setNewColor();
    setValueLabels();
  }
  
  public void addSlider(int id, String name, int r, int g, int b) {
    scis.addElement(new SliderColorInfo(id, name, r, g, b));
  }
  
  public void addSlider(int id, String name, Color c) {
    scis.addElement(new SliderColorInfo(id, name, c.getRed(), c.getGreen(), c.getBlue()));
  }
  
  public void fixSliders()
  {
    if (scis.size() == 1)
      return;
    sliderName.setAsNotLabel();
    makeChooserNames();
    chooser = new Jeli.Get.GetButtonPopup("Color Values", CHOOSER_ID, chooserNames, 50, hm, this, sliderName);
  }
  
  private void makeChooserNames()
  {
    chooserNames = new String[scis.size()];
    for (int i = 0; i < scis.size(); i++)
      chooserNames[i] = scis.elementAt(i)).name;
  }
  
  public void buttonPopupFound(String name, int id, int ind, String label) {
    sci = ((SliderColorInfo)scis.elementAt(ind));
    setOldColor();
    setNewColor();
    setValueLabels();
    sliderName.setLabel(sci.name);
  }
  
  public void disposeSlider()
  {
    hm.removeButton(sliderName);
    hm.removeButton(setButton);
    hm.removeButton(resetButton);
    hm.removeButton(oldColorLabel);
    hm.removeButton(newColorLabel);
  }
  
  public void setBackground(Color c) {
    sliderName.setBackground(c);
    typeLabel.setBackground(c);
    setButton.setBackground(c);
    resetButton.setBackground(c);
    redValLabel.setBackground(c);
    greenValLabel.setBackground(c);
    blueValLabel.setBackground(c);
    sliderAll.setBackground(c);
  }
}
