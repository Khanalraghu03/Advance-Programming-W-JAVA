package Jeli.Widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.event.AdjustmentEvent;
import java.util.Vector;

public class SliderColor implements JeliButtonCallBack, java.awt.event.AdjustmentListener, Jeli.Get.ButtonPopupCallBack
{
  private Scrollbar sliderRed;
  private Scrollbar sliderGreen;
  private Scrollbar sliderBlue;
  private Panel sliderRedBorder;
  private Panel sliderGreenBorder;
  private Panel sliderBlueBorder;
  private JeliButton setButton;
  private JeliButton resetButton;
  private JeliButton defaultButton;
  private JeliButton lighterButton;
  private JeliButton darkerButton;
  private String msg;
  private JeliButton sliderName;
  private int width;
  private ColorSliderCallBack ap;
  private HelpManager hm;
  private String classification;
  private SizedPanel redPanel;
  private SizedPanel greenPanel;
  private SizedPanel bluePanel;
  private JeliLabel redValueLabel;
  private JeliLabel greenValueLabel;
  private JeliLabel blueValueLabel;
  private JeliLabel oldColorLabel;
  private JeliLabel newColorLabel;
  private int vis = 10;
  private int intensity = 200;
  private Color myRed = new Color(255, intensity, intensity);
  private Color myGreen = new Color(intensity, 255, intensity);
  private Color myBlue = new Color(intensity, intensity, 255);
  private Color defaultColor = Color.white;
  private int backThreshhold = 380;
  private SliderColorInfo sci;
  private Vector scis;
  private Jeli.Get.GetButtonPopup chooser = null;
  private String[] chooserNames = null;
  private int CHOOSER_ID = 1;
  

  public SliderColor(int id, int valRed, int valGreen, int valBlue, int width, String msg, ColorSliderCallBack ap, HelpManager hm)
  {
    sci = new SliderColorInfo(id, msg, valRed, valGreen, valBlue);
    scis = new Vector();
    scis.addElement(sci);
    this.width = width;
    this.msg = msg;
    this.ap = ap;
    this.hm = hm;
    classification = msg;
    sliderName = new JeliButton(msg, hm, this);
    sliderName.setAsLabel();
    setWidgets();
    setValueLabels();
    sliderRed.addAdjustmentListener(this);
    sliderGreen.addAdjustmentListener(this);
    sliderBlue.addAdjustmentListener(this);
    setNewColor();
    setOldColor();
  }
  

  private void setWidgets()
  {
    GridBagLayout gridbag = new GridBagLayout();
    java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
    redPanel = new SizedPanel(width);
    greenPanel = new SizedPanel(width);
    bluePanel = new SizedPanel(width);
    redValueLabel = new JeliLabel("22222", hm);
    greenValueLabel = new JeliLabel("22222", hm);
    blueValueLabel = new JeliLabel("22222", hm);
    redValueLabel.setPositionCenter();
    greenValueLabel.setPositionCenter();
    blueValueLabel.setPositionCenter();
    redValueLabel.setBackground(myRed);
    greenValueLabel.setBackground(myGreen);
    blueValueLabel.setBackground(myBlue);
    redValueLabel.setForeground(Color.black);
    greenValueLabel.setForeground(Color.black);
    blueValueLabel.setForeground(Color.black);
    sliderRed = new Scrollbar(0, sci.valRed, vis, 0, 255 + vis);
    
    sliderRedBorder = Jeli.Utility.getBorderedPanel(sliderRed);
    sliderGreen = new Scrollbar(0, sci.valGreen, vis, 0, 255 + vis);
    
    sliderGreenBorder = Jeli.Utility.getBorderedPanel(sliderGreen);
    sliderBlue = new Scrollbar(0, sci.valBlue, vis, 0, 255 + vis);
    
    sliderBlueBorder = Jeli.Utility.getBorderedPanel(sliderBlue);
    sliderRed.setBackground(myRed);
    sliderGreen.setBackground(myGreen);
    sliderBlue.setBackground(myBlue);
    redPanel.setLayout(gridbag);
    greenPanel.setLayout(gridbag);
    bluePanel.setLayout(gridbag);
    fill = 1;
    gridx = 0;
    gridy = 0;
    weightx = 0.0D;
    weighty = 0.0D;
    gridbag.setConstraints(redValueLabel, c);
    gridbag.setConstraints(greenValueLabel, c);
    gridbag.setConstraints(blueValueLabel, c);
    redPanel.add(redValueLabel);
    greenPanel.add(greenValueLabel);
    bluePanel.add(blueValueLabel);
    weightx = 1.0D;
    gridx = 1;
    gridbag.setConstraints(sliderRedBorder, c);
    gridbag.setConstraints(sliderGreenBorder, c);
    gridbag.setConstraints(sliderBlueBorder, c);
    redPanel.add(sliderRedBorder);
    greenPanel.add(sliderGreenBorder);
    bluePanel.add(sliderBlueBorder);
    oldColorLabel = new JeliLabel("old", hm);
    oldColorLabel.setPositionCenter();
    newColorLabel = new JeliLabel("new", hm);
    newColorLabel.setPositionCenter();
    setButton = new JeliButton("Set", hm, this);
    setButton.setPositionCenter();
    resetButton = new JeliButton("Reset", hm, this);
    resetButton.setPositionCenter();
    defaultButton = new JeliButton("Default", hm, this);
    defaultButton.setPositionCenter();
    defaultButton.setBackground(defaultColor);
    lighterButton = new JeliButton("Lighter", hm, this);
    lighterButton.setPositionCenter();
    darkerButton = new JeliButton("Darker", hm, this);
    darkerButton.setPositionCenter();
  }
  
  public void setDefaultColor(Color c) {
    defaultColor = c;
    defaultButton.setBackground(c);
    if (c.getRed() + c.getGreen() + c.getBlue() < backThreshhold) {
      defaultButton.setForeground(Color.white);
    } else
      defaultButton.setForeground(Color.black);
  }
  
  public Panel getSliderPanel() {
    Panel p = new Panel();
    p.setLayout(new JeliGridLayout(1, 3));
    p.add(redPanel);
    p.add(greenPanel);
    p.add(bluePanel);
    return p;
  }
  
  public Panel getInfoPanel() {
    Panel p = new Panel();
    p.setLayout(new JeliGridLayout(1, 5));
    p.add(sliderName);
    p.add(setButton);
    p.add(resetButton);
    p.add(oldColorLabel);
    p.add(newColorLabel);
    return p;
  }
  
  public Panel getInfoDefaultPanel() {
    Panel p = new Panel();
    p.setLayout(new JeliGridLayout(1, 6));
    p.add(sliderName);
    p.add(defaultButton);
    p.add(setButton);
    p.add(resetButton);
    p.add(oldColorLabel);
    p.add(newColorLabel);
    return p;
  }
  
  public Panel getInfoNewPanel() {
    Panel p = new Panel();
    p.setLayout(new JeliGridLayout(1, 8));
    p.add(sliderName);
    p.add(defaultButton);
    p.add(lighterButton);
    p.add(darkerButton);
    p.add(setButton);
    p.add(resetButton);
    p.add(oldColorLabel);
    p.add(newColorLabel);
    return p;
  }
  
  public Panel getSliderName() {
    return sliderName;
  }
  
  public Panel getDefaultButton() {
    return defaultButton;
  }
  
  public Panel getLighterButton() {
    return lighterButton;
  }
  
  public Panel getDarkerButton() {
    return darkerButton;
  }
  
  public Panel getSetButton() {
    return setButton;
  }
  
  public Panel getResetButton() {
    return resetButton;
  }
  
  public Panel getOldColorLabel() {
    return oldColorLabel;
  }
  
  public Panel getNewColorLabel() {
    return newColorLabel;
  }
  
  public Dimension preferredSliderSize() {
    return new Dimension(width, 10);
  }
  
  public Dimension getPreferredSize()
  {
    return new Dimension(width, 10);
  }
  
  public void jeliButtonPushed(JeliButton bh)
  {
    if (bh == setButton) {
      setColors();
    } else if (bh == resetButton) {
      resetColors();
    } else if (bh == defaultButton) {
      resetToDefault();
    } else if (bh == lighterButton) {
      makeLighter();
    } else if (bh == darkerButton) {
      makeDarker();
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
  
  public void adjustmentValueChanged(AdjustmentEvent e) {
    if (e.getSource() == sliderRed) {
      sci.valRed = sliderRed.getValue();
      redValueLabel.setLabel("" + sci.valRed);
    }
    else if (e.getSource() == sliderGreen) {
      sci.valGreen = sliderGreen.getValue();
      greenValueLabel.setLabel("" + sci.valGreen);
    }
    else if (e.getSource() == sliderBlue) {
      sci.valBlue = sliderBlue.getValue();
      blueValueLabel.setLabel("" + sci.valBlue);
    }
    setNewColor();
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
    redValueLabel.setLabel("" + sci.valRed);
    greenValueLabel.setLabel("" + sci.valGreen);
    blueValueLabel.setLabel("" + sci.valBlue);
  }
  
  public void setColors() {
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
    sliderRed.setValue(sci.valRed);
    sliderGreen.setValue(sci.valGreen);
    sliderBlue.setValue(sci.valBlue);
    setNewColor();
    setValueLabels();
  }
  
  public void resetToDefault() {
    sci.valRed = defaultColor.getRed();
    sci.valGreen = defaultColor.getGreen();
    sci.valBlue = defaultColor.getBlue();
    sliderRed.setValue(sci.valRed);
    sliderGreen.setValue(sci.valGreen);
    sliderBlue.setValue(sci.valBlue);
    setValueLabels();
    setNewColor();
    setColors();
  }
  
  private int lighterValue(int old)
  {
    if (old >= 255)
      return 255;
    int increment = (int)((255 - old) * 0.1D);
    if (increment == 0)
      return old + 1;
    return old + increment;
  }
  
  private int darkerValue(int old)
  {
    if (old <= 0)
      return 0;
    int decrement = (int)((255 - old) * 0.1D);
    if (decrement == 0)
      return old - 1;
    if (old < decrement)
      return 0;
    return old - decrement;
  }
  
  public void makeLighter() {
    sci.valRed = lighterValue(sci.valRed);
    sliderRed.setValue(sci.valRed);
    sci.valGreen = lighterValue(sci.valGreen);
    sliderGreen.setValue(sci.valGreen);
    sci.valBlue = lighterValue(sci.valBlue);
    sliderBlue.setValue(sci.valBlue);
    setValueLabels();
    setNewColor();
  }
  
  public void makeDarker() {
    sci.valRed = darkerValue(sci.valRed);
    sliderRed.setValue(sci.valRed);
    sci.valGreen = darkerValue(sci.valGreen);
    sliderGreen.setValue(sci.valGreen);
    sci.valBlue = darkerValue(sci.valBlue);
    sliderBlue.setValue(sci.valBlue);
    setValueLabels();
    setNewColor();
  }
  
  public void addSlider(int id, String name, int r, int g, int b)
  {
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
    sliderRed.setValue(sci.valRed);
    sliderGreen.setValue(sci.valGreen);
    sliderBlue.setValue(sci.valBlue);
    setOldColor();
    setNewColor();
    setValueLabels();
    sliderName.setLabel(sci.name);
  }
  
  public void disposeSlider() {
    hm.removeButton(sliderName);
    hm.removeButton(setButton);
    hm.removeButton(resetButton);
    hm.removeButton(defaultButton);
    hm.removeButton(lighterButton);
    hm.removeButton(darkerButton);
    hm.removeButton(redValueLabel);
    hm.removeButton(greenValueLabel);
    hm.removeButton(blueValueLabel);
    hm.removeButton(oldColorLabel);
    hm.removeButton(newColorLabel);
  }
}
