package Jeli.Get;

import Jeli.Widgets.JeliButton;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Panel;

public class GetFont extends Jeli.Widgets.JeliFrame implements Jeli.Widgets.JeliCanvasFiller
{
  int id;
  Jeli.Widgets.HelpManager hm;
  java.awt.Component com;
  FontCallBack callback;
  JeliButton[] fontButtons;
  JeliButton promptButton;
  JeliButton plainFontButton;
  JeliButton boldFontButton;
  JeliButton italicFontButton;
  JeliButton boldItalicFontButton;
  JeliButton sizeLabel;
  JeliButton sizeButton;
  JeliButton doneButton;
  JeliButton applyButton;
  JeliButton resizeButton;
  JeliButton cancelButton;
  JeliButton nameButton;
  JeliButton styleButton;
  private String classification = null;
  String[] fontList;
  int fontSize = 10;
  int fontIndex = 0;
  int fontStyle = 0;
  String name;
  Jeli.Widgets.JeliCanvas can;
  java.awt.Image im;
  Graphics GC;
  int image_width = 0;
  int image_height = 0;
  Font font;
  java.awt.FontMetrics fm;
  int descent;
  int ascent;
  Panel sizePanelParent;
  Panel sizePanel;
  String[] defaultFontList = { "Dialog", "SanSerif", "Serif", "MonoSpaced", "DialogInput" };
  
  int init_width;
  
  private boolean center_flag;
  java.awt.Dimension screenSize;
  int max_width;
  int max_height;
  int nameWidth = 5;
  Color fontFamilyColor = Jeli.Utility.jeliLightYellow;
  Color fontStyleColor = Jeli.Utility.jeliLightCyan;
  Color fontSizeColor = Jeli.Utility.jeliLightGreen;
  private String displayString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
  


  public GetFont(String name, int id, Jeli.Widgets.HelpManager hm, FontCallBack callback, java.awt.Component com, Font f)
  {
    this(name, id, hm, callback, com, false, f);
  }
  

  public GetFont(String name, int id, Jeli.Widgets.HelpManager hm, FontCallBack callback, java.awt.Component com, boolean center, Font f)
  {
    super(name);
    this.name = name;
    this.callback = callback;
    this.id = id;
    this.com = com;
    this.hm = hm;
    center_flag = center;
    can = new Jeli.Widgets.JeliCanvas();
    can.setFiller(this);
    
    setup_layout();
    if (f == null)
      f = new Font("Dialog", 0, 10);
    setSelectedFont(f);
    pack();
    screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    max_width = (screenSize.width - 10);
    max_height = (screenSize.height - 10);
  }
  
  public void showGetFont()
  {
    if (com != null) {
      java.awt.Point pos = Jeli.Utility.getAbsolutePosition(com);
      if (center_flag) {
        setLocation(x - getBoundswidth / 2, y - getBoundsheight / 2);
      }
      else
        setLocation(x, y);
      com = null;
    }
    setVisible(true);
    setSelectedButtons();
    repaint();
  }
  

  public void setup_layout()
  {
    String[] tempFontList = null;
    
    int skip = 0;
    Panel donePanel = new Panel();
    Panel promptPanel = new Panel();
    Panel headingsPanel = new Panel();
    Panel mainPanel = new Panel();
    Panel mainPanelParent = new Panel();
    Panel fontsPanel = new Panel();
    Panel fontsPanelParent = new Panel();
    Panel stylePanel = new Panel();
    mainPanel.setBackground(fontFamilyColor);
    
    Panel canvasPanel = new Panel();
    canvasPanel.setLayout(new java.awt.GridLayout(1, 1));
    canvasPanel.add(can);
    sizePanel = new Panel();
    sizePanelParent = new Panel();
    
    String javaVersion = System.getProperty("java.version");
    if (javaVersion.compareTo("1.2.0") < 0) {
      tempFontList = defaultFontList;
    }
    else {
      tempFontList = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }
    int numfonts = tempFontList.length;
    for (int i = 0; i < tempFontList.length; i++)
      if (!goodString(tempFontList[i]))
        numfonts--;
    fontList = new String[numfonts];
    for (int i = 0; i < tempFontList.length; i++) {
      if (!goodString(tempFontList[i])) {
        skip++;
      } else
        fontList[(i - skip)] = tempFontList[i];
    }
    setLayout(new java.awt.BorderLayout());
    add("North", promptPanel);
    add("South", donePanel);
    add("Center", mainPanelParent);
    mainPanelParent.setLayout(new java.awt.BorderLayout());
    mainPanelParent.add("North", mainPanel);
    mainPanelParent.add("Center", canvasPanel);
    promptPanel.setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
    donePanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 4));
    int nameHeight = (numfonts + nameWidth - 1) / nameWidth;
    mainPanel.setLayout(new Jeli.Widgets.JeliGridLayout(nameHeight, nameWidth));
    fontButtons = new JeliButton[numfonts];
    for (int i = 0; i < numfonts; i++) {
      fontButtons[i] = new JeliButton(fontList[i], hm, this);
      fontButtons[i].setBackground(fontFamilyColor);
      fontButtons[i].setMessage("Set font type to " + fontList[i]);
      mainPanel.add(fontButtons[i]);
    }
    stylePanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 4));
    plainFontButton = new JeliButton("Plain", hm, this);
    boldFontButton = new JeliButton("Bold", hm, this);
    italicFontButton = new JeliButton("Itlaic", hm, this);
    boldItalicFontButton = new JeliButton("Bold Itlaic", hm, this);
    plainFontButton.setBackground(fontStyleColor);
    boldFontButton.setBackground(fontStyleColor);
    italicFontButton.setBackground(fontStyleColor);
    boldItalicFontButton.setBackground(fontStyleColor);
    
    stylePanel.add(plainFontButton);
    stylePanel.add(boldFontButton);
    stylePanel.add(italicFontButton);
    stylePanel.add(boldItalicFontButton);
    
    sizeButton = new JeliButton("" + fontSize, hm, this);
    sizeButton.setAsGetInteger("Font Size", "Font Size: ", "Font Size: ", fontSize, true);
    sizeButton.setBackground(fontSizeColor);
    promptPanel.add(stylePanel);
    promptPanel.add(sizeButton);
    
    doneButton = new JeliButton("Done", hm, this);
    applyButton = new JeliButton("Apply", hm, this);
    resizeButton = new JeliButton("Resize", hm, this);
    cancelButton = new JeliButton("Cancel", hm, this);
    doneButton.setBackground(Color.pink);
    applyButton.setBackground(Color.pink);
    resizeButton.setBackground(Color.pink);
    cancelButton.setBackground(Color.pink);
    donePanel.add(resizeButton);
    donePanel.add(applyButton);
    donePanel.add(doneButton);
    donePanel.add(cancelButton);
    
    resizeButton.setMessage("Reduce to minimal size");
    applyButton.setMessage("Use the selected font\nDo not hide " + name);
    doneButton.setMessage("Use the selected font\nHide " + name);
    cancelButton.setMessage("Do not reset font");
    sizeButton.setMessage("Click on this button to change the font size");
    plainFontButton.setMessage("Set font style to Plain");
    boldFontButton.setMessage("Set font style to Bold");
    italicFontButton.setMessage("Set font style to Italic");
    boldItalicFontButton.setMessage("Set font style to Bold Italic");
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == doneButton) {
      callback.setFont(id, font);
      setVisible(false);
      setSelectedButtons();
      return;
    }
    if (bh == applyButton) {
      callback.setFont(id, font);
      setSelectedButtons();
      return;
    }
    if (bh == resizeButton) {
      setSelectedButtons();
      reduce_to_minimal_size();
      return;
    }
    if (bh == cancelButton) {
      setVisible(false);
      setSelectedButtons();
      return;
    }
    if (bh == plainFontButton) {
      fontStyle = 0;
      setSelectedButtons();
      
      return;
    }
    if (bh == boldFontButton) {
      fontStyle = 1;
      setSelectedButtons();
      
      return;
    }
    if (bh == italicFontButton) {
      fontStyle = 2;
      setSelectedButtons();
      
      return;
    }
    if (bh == boldItalicFontButton) {
      fontStyle = 3;
      setSelectedButtons();
      
      return;
    }
    for (int i = 0; i < fontButtons.length; i++) {
      if (bh == fontButtons[i]) {
        fontIndex = i;
        setSelectedButtons();
        
        return;
      }
    }
  }
  
  public void setSelectedFont(Font f) {
    fontStyle = f.getStyle();
    String fname = f.getFamily();
    fontSize = f.getSize();
    sizeButton.setValue(fontSize);
    for (int i = 0; i < fontList.length; i++)
      if (fontList[i].equalsIgnoreCase(fname)) {
        fontIndex = i;
        break;
      }
    setSelectedButtons();
  }
  

  private boolean goodString(String s)
  {
    if (s == null)
      return false;
    if (s.length() == 0)
      return false;
    for (int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      if (c < ' ') return false;
      if (c > '~') return false;
    }
    return true;
  }
  
  private void setSelectedButtons() {
    String fontStyleString = "";
    font = new Font(fontList[fontIndex], fontStyle, fontSize);
    fm = getFontMetrics(font);
    descent = fm.getMaxDescent();
    ascent = fm.getMaxAscent();
    
    can.setPreferredSize(10, ascent + descent + 20);
    can.repaint();
    for (int i = 0; i < fontButtons.length; i++)
      if (fontIndex == i) {
        fontButtons[i].allReverse(true);
      } else
        fontButtons[i].allReverse(false);
    if (fontStyle == 0) {
      plainFontButton.allReverse(true);
      boldFontButton.allReverse(false);
      italicFontButton.allReverse(false);
      boldItalicFontButton.allReverse(false);
      fontStyleString = "Plain";
    }
    if (fontStyle == 1) {
      plainFontButton.allReverse(false);
      boldFontButton.allReverse(true);
      italicFontButton.allReverse(false);
      boldItalicFontButton.allReverse(false);
      fontStyleString = "Bold";
    }
    if (fontStyle == 2) {
      plainFontButton.allReverse(false);
      boldFontButton.allReverse(false);
      italicFontButton.allReverse(true);
      boldItalicFontButton.allReverse(false);
      fontStyleString = "Italic";
    }
    if (fontStyle == 3) {
      plainFontButton.allReverse(false);
      boldFontButton.allReverse(false);
      italicFontButton.allReverse(false);
      boldItalicFontButton.allReverse(true);
      fontStyleString = "Bold";
    }
    applyButton.setMessage("Use " + fontSize + " point " + fontStyleString + " " + fontList[fontIndex] + " font\nDo not hide " + name + " window");
    
    doneButton.setMessage("Use " + fontSize + " point " + fontStyleString + " " + fontList[fontIndex] + " font\nHide " + name + " window");
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {
    fontSize = val;
    setSelectedButtons();
  }
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification()
  {
    return classification;
  }
  
  public void setClassification(String str) {
    classification = str;
  }
  


  private void reduce_to_minimal_size()
  {
    java.awt.Rectangle oldCanSize = can.getBounds();
    java.awt.Dimension newCanSize = can.getPreferredSize();
    java.awt.Rectangle oldSize = getBounds();
    setSize(width, height - height + height);
    invalidate();
    validate();
  }
  
  public void fillImage(int w, int h, Graphics g) {
    g.setColor(Color.yellow);
    g.fillRect(0, 0, w, h);
    g.setColor(Color.black);
    g.setFont(font);
    g.drawString(displayString, 10, h - 10 - descent);
  }
}
