package Jeli.Widgets;

import Jeli.Utility;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.io.PrintStream;
import java.util.Vector;

public class JeliTextDisplay extends Panel implements JeliButtonCallBack, JeliCanvasCallBack, Jeli.Logging.Mono, Jeli.Get.GetColorFrameCallBack
{
  private static int defaultIndentPixels = 0;
  
  private int id;
  private int rows;
  private int columns;
  private String label;
  private Color background;
  private Color foreground;
  private HelpManager hm;
  private TextDisplayCallBack callback;
  private Font font;
  private String fontName;
  private int fontStyle;
  private double fontSize;
  public JeliButton labelButton;
  JeliButton clearButton;
  public JeliButton logButton;
  JeliButton fontUpButton;
  JeliButton fontUpButton1;
  JeliButton fontUpButton2;
  JeliButton fontUpButton3;
  JeliButton fontDownButton;
  JeliButton fontDownButton1;
  JeliButton fontDownButton2;
  JeliButton fontDownButton3;
  JeliButton stylePlainButton;
  JeliButton styleBoldButton;
  JeliButton styleItalicButton;
  JeliButton nameSerifButton;
  JeliButton nameSansSerifButton;
  JeliButton nameMonospacedButton;
  JeliTextArea textArea;
  private String classification;
  private boolean monoFlag = false;
  private Panel panelNorth;
  private Panel panelButtons;
  private boolean logFlag = true;
  private boolean fontSizeButtonsFlag = false;
  private Vector buttonList;
  private int indentPixels;
  private SizedPanel indentPanel;
  private JeliCanvasCallBack canvasCallback = null;
  

  public JeliTextDisplay(int id, int rows, int columns, String label, Font font, Color background, Color foreground, HelpManager hm, TextDisplayCallBack callback)
  {
    this(id, rows, columns, label, font, background, foreground, hm, callback, true, false, defaultIndentPixels);
  }
  





  public JeliTextDisplay(int id, int rows, int columns, String label, Font font, Color background, Color foreground, HelpManager hm, TextDisplayCallBack callback, boolean logFlag, boolean fontSizeButtonsFlag, int indentPixels)
  {
    this.id = id;
    this.rows = rows;
    this.columns = columns;
    this.label = label;
    this.font = font;
    this.background = background;
    this.foreground = foreground;
    this.hm = hm;
    this.callback = callback;
    this.logFlag = logFlag;
    this.fontSizeButtonsFlag = fontSizeButtonsFlag;
    this.indentPixels = indentPixels;
    classification = label;
    fontSize = font.getSize();
    fontName = font.getName();
    fontStyle = font.getStyle();
    buttonList = new Vector();
    
    setupLayout(font);
    setFontSizeButtons(font.getSize());
    textArea.addCheatCallback(this);
  }
  
  public static void setDefaultIndentPixels(int n) {
    defaultIndentPixels = n;
  }
  
  public void setCanvasCallback(JeliCanvasCallBack cb) {
    canvasCallback = cb;
  }
  
  public void setVoidCallBack(VoidCallBack cb) {
    textArea.setVoidCallBack(cb);
  }
  
  public void setCallBack(TextDisplayCallBack callback) {
    this.callback = callback;
  }
  
  public void setFont(Font f) {
    font = f;
    fontName = f.getName();
    fontStyle = f.getStyle();
    fontSize = f.getSize();
    textArea.setFont(f, fontSize);
    setChangeFontHelp();
    textArea.resetScrollbars();
    setFontSizeButtons(f.getSize());
  }
  
  public void setFont() {
    font = new Font(fontName, fontStyle, (int)fontSize);
    if (font != null)
      setFont(font);
  }
  
  public int getWidth() {
    return textArea.getWidth();
  }
  
  public java.awt.FontMetrics getFontMetric() {
    return textArea.getFontMetric();
  }
  
  private void setupLayout(Font f)
  {
    setLayout(new BorderLayout());
    panelNorth = new Panel();
    panelNorth.setLayout(new BorderLayout());
    
    textArea = new JeliTextArea(rows, columns, f);
    textArea.setBackgroundColor(background);
    textArea.setForegroundColor(foreground);
    textArea.setAppendScroll(true);
    
    add("North", panelNorth);
    add("Center", textArea);
    indentPanel = new SizedPanel(0);
    indentPanel.setBackground(background);
    indentPanel.setWidthOnly(indentPixels);
    if (indentPixels > 0)
      add("West", indentPanel);
    panelButtons = new Panel();
    panelButtons.setLayout(new BorderLayout());
    
    panelNorth.add("Center", panelButtons);
    if (fontSizeButtonsFlag) {
      Panel biggerFontPanel = new Panel();
      Panel smallerFontPanel = new Panel();
      biggerFontPanel.setLayout(new JeliGridLayout(1, 4));
      smallerFontPanel.setLayout(new JeliGridLayout(1, 4));
      smallerFontPanel.add(this.fontDownButton = new JeliButton("<", hm));
      smallerFontPanel.add(this.fontDownButton3 = new JeliButton("993", hm));
      smallerFontPanel.add(this.fontDownButton2 = new JeliButton("992", hm));
      smallerFontPanel.add(this.fontDownButton1 = new JeliButton("991", hm));
      biggerFontPanel.add(this.fontUpButton1 = new JeliButton("991", hm));
      biggerFontPanel.add(this.fontUpButton2 = new JeliButton("992", hm));
      biggerFontPanel.add(this.fontUpButton3 = new JeliButton("993", hm));
      biggerFontPanel.add(this.fontUpButton = new JeliButton(">", hm));
      setButtonValues(fontDownButton1);
      setButtonValues(fontDownButton2);
      setButtonValues(fontDownButton3);
      setButtonValues(fontUpButton1);
      setButtonValues(fontUpButton2);
      setButtonValues(fontUpButton3);
      
      panelNorth.add("West", smallerFontPanel);
      panelNorth.add("East", biggerFontPanel);
    }
    else {
      panelNorth.add("West", this.fontDownButton = new JeliButton("<", hm));
      panelNorth.add("East", this.fontUpButton = new JeliButton(">", hm));
      buttonList.addElement(fontDownButton);
      buttonList.addElement(fontUpButton);
    }
    
    labelButton = new JeliButton(label, hm);
    buttonList.addElement(labelButton);
    panelButtons.add("Center", labelButton);
    clearButton = new JeliButton("Clr", hm);
    buttonList.addElement(clearButton);
    logButton = new JeliButton("Log", hm);
    buttonList.addElement(logButton);
    if (logFlag) {
      panelButtons.add("West", clearButton);
      panelButtons.add("East", logButton);
    }
    else {
      Panel familyPanel = new Panel();
      familyPanel.setLayout(new JeliGridLayout(1, 3));
      familyPanel.add(this.stylePlainButton = new JeliButton("Plain", hm));
      familyPanel.add(this.styleBoldButton = new JeliButton("Bold", hm));
      familyPanel.add(this.styleItalicButton = new JeliButton("Italic", hm));
      buttonList.addElement(stylePlainButton);
      buttonList.addElement(styleBoldButton);
      buttonList.addElement(styleItalicButton);
      stylePlainButton.setBackground(background);
      styleBoldButton.setBackground(background);
      styleItalicButton.setBackground(background);
      stylePlainButton.setForeground(foreground);
      styleBoldButton.setForeground(foreground);
      styleItalicButton.setForeground(foreground);
      stylePlainButton.resetJeliButtonCallBack(this);
      styleBoldButton.resetJeliButtonCallBack(this);
      styleItalicButton.resetJeliButtonCallBack(this);
      panelButtons.add("West", familyPanel);
      
      Panel stylePanel = new Panel();
      stylePanel.setLayout(new JeliGridLayout(1, 3));
      stylePanel.add(this.nameSerifButton = new JeliButton("Serif", hm));
      stylePanel.add(this.nameSansSerifButton = new JeliButton("Sans", hm));
      stylePanel.add(this.nameMonospacedButton = new JeliButton("Mono", hm));
      
      buttonList.addElement(nameSerifButton);
      buttonList.addElement(nameSansSerifButton);
      buttonList.addElement(nameMonospacedButton);
      nameSerifButton.setBackground(background);
      nameSansSerifButton.setBackground(background);
      nameMonospacedButton.setBackground(background);
      nameSerifButton.setForeground(foreground);
      nameSansSerifButton.setForeground(foreground);
      nameMonospacedButton.setForeground(foreground);
      nameSerifButton.resetJeliButtonCallBack(this);
      nameSansSerifButton.resetJeliButtonCallBack(this);
      nameMonospacedButton.resetJeliButtonCallBack(this);
      panelButtons.add("East", stylePanel);
    }
    
    labelButton.resetJeliButtonCallBack(this);
    fontDownButton.resetJeliButtonCallBack(this);
    fontUpButton.resetJeliButtonCallBack(this);
    clearButton.resetJeliButtonCallBack(this);
    logButton.resetJeliButtonCallBack(this);
    fontDownButton.setBackground(background);
    fontUpButton.setBackground(background);
    clearButton.setBackground(background);
    labelButton.setBackground(background);
    logButton.setBackground(background);
    fontDownButton.setForeground(foreground);
    fontUpButton.setForeground(foreground);
    clearButton.setForeground(foreground);
    labelButton.setForeground(foreground);
    logButton.setForeground(foreground);
    setChangeFontHelp();
    labelButton.setButtonSize();
    fontDownButton.setPositionCenter();
    fontUpButton.setPositionCenter();
  }
  
  private void setButtonValues(JeliButton jb) {
    buttonList.addElement(jb);
    jb.resetJeliButtonCallBack(this);
    jb.setBackground(background);
    jb.setForeground(foreground);
    jb.setPositionCenter();
  }
  
  private void setFontSizeButtons(int fs) {
    if (fontDownButton1 == null) return;
    fontDownButton1.setLabel("" + (fs - 1));
    fontDownButton2.setLabel("" + (fs - 2));
    fontDownButton3.setLabel("" + (fs - 3));
    fontUpButton1.setLabel("" + (fs + 1));
    fontUpButton2.setLabel("" + (fs + 2));
    fontUpButton3.setLabel("" + (fs + 3));
  }
  
  private void setChangeFontHelp() {
    fontDownButton.setHelpManager(hm);
    fontDownButton.setMessage("Reduce the size of the font in the window\n   Current size: " + Utility.n_places(fontSize, 2) + "\n" + "   Next size:    " + Utility.n_places(fontSize / 1.2D, 2) + "\n");
    


    fontUpButton.setHelpManager(hm);
    fontUpButton.setMessage("Increase the size of the font in the window\n   Current size: " + Utility.n_places(fontSize, 2) + "\n" + "   Next size:    " + Utility.n_places(1.2D * fontSize, 2) + "\n");
  }
  


  public void setBackground(Color c)
  {
    super.setBackground(c);
    background = c;
    textArea.setBackgroundColor(c);
    labelButton.setBackground(c);
    clearButton.setBackground(c);
    logButton.setBackground(c);
    fontUpButton.setBackground(c);
    fontDownButton.setBackground(c);
    indentPanel.setBackground(c);
  }
  
  public void appendLine() {
    textArea.appendLine();
  }
  
  public void appendLine(int totab) {
    textArea.appendLine(totab);
  }
  
  public void appendLineIfNecessary() {
    textArea.appendLineIfNecessary();
  }
  
  public void appendLineIfNecessary(int totab) {
    textArea.appendLineIfNecessary(totab);
  }
  
  public void append(String str) {
    if (str != null)
      textArea.append(str);
  }
  
  public void appendToCurrent(String str) {
    textArea.appendToCurrent(str);
  }
  
  public void appendS(String str) {
    if (str != null) {
      textArea.append(str);
    }
  }
  
  public void set(String str)
  {
    if (str != null) {
      textArea.setText(str);
    } else {
      textArea.setText("");
    }
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    String lab = bh.getLabel();
    if (bh == labelButton)
    {
      if (callback == null) return;
      callback.textDisplayLabelCallBack(id);
      return;
    }
    if (bh == clearButton)
    {
      textArea.setText("");
      return;
    }
    if (bh == logButton)
    {
      if (callback == null) return;
      callback.textDisplayLogCallBack(id, textArea.getHTML(monoFlag));
      return;
    }
    if (bh == fontUpButton)
    {
      incrementFontSize();
      return;
    }
    if (bh == fontDownButton)
    {
      decrementFontSize();
      return;
    }
    if (bh == stylePlainButton) {
      fontStyle = 0;
      setFont();
      
      return;
    }
    if (bh == styleBoldButton) {
      fontStyle = 1;
      setFont();
      
      return;
    }
    if (bh == styleItalicButton) {
      fontStyle = 2;
      setFont();
      
      return;
    }
    if (bh == nameSerifButton) {
      fontName = "Serif";
      setFont();
      
      return;
    }
    if (bh == nameSansSerifButton) {
      fontName = "SansSerif";
      setFont();
      
      return;
    }
    if (bh == nameMonospacedButton) {
      fontName = "Monospaced";
      setFont();
      
      return;
    }
    if (fontDownButton1 == null) return;
    if ((bh == fontUpButton1) || (bh == fontUpButton2) || (bh == fontUpButton3) || (bh == fontDownButton1) || (bh == fontDownButton2) || (bh == fontDownButton3))
    {

      fontSize = Integer.parseInt(bh.getLabel());
      setFont();
      



      return;
    }
  }
  

  public void incrementFontSize()
  {
    double oldFontSize = fontSize;
    fontSize = (1.2D * fontSize);
    if (fontSize - oldFontSize < 1.0D)
      fontSize = (oldFontSize + 1.0D);
    setFont();
  }
  



  public void decrementFontSize()
  {
    fontSize /= 1.2D;
    if (fontSize < 1.0D) fontSize = 1.0D;
    setFont();
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
  
  public void setAsDefault() {
    Jeli.UtilityIO.setTextList(textArea);
  }
  
  public void setEnabled(boolean f) {
    logButton.logStateChange(f);
  }
  
  public void setNoScrollTop(int n) {
    textArea.setNoScrollTop(n);
  }
  
  public void setNoScrollBottom(int n) {
    textArea.setNoScrollBottom(n);
  }
  
  public int getNoScrollTop() {
    return textArea.getNoScrollTop();
  }
  
  public int getNoScrollBottom() {
    return textArea.getNoScrollBottom();
  }
  
  public void top() {
    textArea.top();
  }
  
  public void setCurrentLine(int n) {
    textArea.setCurrentLine(n);
  }
  
  public int getCurrentLine() {
    return textArea.getCurrentLine();
  }
  
  public void setLabel(String lab) {
    labelButton.setLabel(lab);
  }
  
  public void gotColorFrameColor(int id, Color c) {
    setBackground(c);
  }
  
  public void jeliCanvasCallback(String str)
  {
    if (str.equals("Background")) {
      new Jeli.Get.GetColorFrame(1, "Canvas Color", background, this);
    }
    else if (canvasCallback != null)
      canvasCallback.jeliCanvasCallback(str);
  }
  
  public void jeliCanvasCallback(String str, String str1) {
    if (canvasCallback != null)
      canvasCallback.jeliCanvasCallback(str, str1);
  }
  
  public void jeliCanvasCallback(String str, int val) { if (canvasCallback != null)
      canvasCallback.jeliCanvasCallback(str, val);
  }
  
  public void jeliCanvasCallback(String str, int val1, int val2) { if (canvasCallback != null)
      canvasCallback.jeliCanvasCallback(str, val1, val2);
  }
  
  public void setMono(boolean f) {
    monoFlag = f;
    textArea.setMono(f);
  }
  
  public void showPreferred() {
    System.out.println("Jeli Text Display Preferred Size: " + getPreferredSize());
    System.out.println("     Panel North Preferred Size: " + panelNorth.getPreferredSize());
    System.out.println("     Panel Butons Preferred Size: " + panelButtons.getPreferredSize());
  }
  
  public void setLineForeground(Color c) {
    textArea.setForegroundColor(c);
  }
  
  public void setLineBackground(Color c) {
    textArea.setBackgroundColor(c);
  }
  
  public void setLineForeground(int n, Color c) {
    textArea.setLineForeground(n, c);
  }
  
  public void setAllLinesForeground(Color c) {
    textArea.setAllLinesForeground(c);
  }
  
  public void setMouseCallBack(JeliMouseCallBack jmcb) {
    textArea.setMouseCallBack(jmcb);
  }
  
  public void setTabs(int[] tabs) {
    textArea.setTabs(tabs);
  }
  
  public void setNewTabs(int n) {
    textArea.setNewTabs(n);
  }
  
  public void setTabPositionLeft(int n) {
    textArea.setTabPositionLeft(n);
  }
  
  public void setTabPositionRight(int n) {
    textArea.setTabPositionRight(n);
  }
  
  public void setTabPositionCenter(int n) {
    textArea.setTabPositionCenter(n);
  }
  
  public void setDynamicTabs(boolean f) {
    textArea.setDynamicTabs(f);
  }
  
  public void setSetTabs(boolean f) {
    textArea.setSetTabs(f);
  }
  
  public void setShowTabs(boolean f) {
    textArea.setShowTabs(f);
  }
  
  public void setInhibitEndTab(int n) {
    textArea.setInhibitEndTab(n);
  }
  
  public void refresh() {
    textArea.refresh();
  }
  
  public void inhibitDisplay(boolean f) {
    textArea.inhibitDisplay(f);
  }
  
  public void setLastUnderline(int n) {
    textArea.setLastUnderline(n);
  }
  
  public void changePreferredWidth(int w) {
    textArea.changePreferredWidth(w);
  }
  
  public int getDesiredHeightChange() {
    return textArea.getDesiredHeightChange();
  }
  
  public void addCheatCode(String str) {
    textArea.addCheatCode(str);
  }
  
  public void addIntCheatCode(String s) {
    textArea.addIntCheatCode(s);
  }
  
  public void addInt2CheatCode(String str) {
    textArea.addInt2CheatCode(str);
  }
  
  public void addStringCheatCode(String str) {
    textArea.addStringCheatCode(str);
  }
  
  public void setDynamicTabsFull(boolean f) {
    textArea.setDynamicTabsFull(f);
  }
  
  public void disableClearButton() {
    clearButton.disableJeliButton();
  }
  
  public void disposeButtons() {
    if (Utility.hm == null) return;
    for (int i = 0; i < buttonList.size(); i++)
      Utility.hm.removeButton((JeliButton)buttonList.elementAt(i));
  }
  
  public void scaleWidth(int max) {
    textArea.scaleWidth(max);
  }
  
  public void scaleHeight(int max) {
    textArea.scaleHeight(max);
  }
  
  public void scale(int maxw, int maxh) {
    textArea.scale(maxw, maxh);
  }
  
  public void setMouseByTab(boolean f) {
    textArea.setMouseByTab(f);
  }
  
  public String getText() {
    return textArea.getText();
  }
  
  public Font getDisplayFont() {
    return textArea.getFont();
  }
  
  public void setUseHighlightedTabs(boolean f) {
    textArea.setUseHighlightedTabs(f);
  }
  
  public void setHighlightedFlags(boolean[][] flags) {
    textArea.setHighlightedFlags(flags);
  }
  
  public void setHighlightedColor(Color c) {
    textArea.setHighlightedColor(c);
  }
  
  public void setName(String s) {
    textArea.setName(s);
  }
}
