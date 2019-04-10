package Jeli.Widgets;

import Jeli.Utility;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public class JeliButtonCanvas extends JeliTransferCanvas implements Runnable
{
  private JeliCanvas displayCanvas = null;
  public static int POSITION_LEFT = 0;
  public static int POSITION_RIGHT = 1;
  public static int POSITION_CENTER = 2;
  private String label;
  private String label2 = null;
  private String label3 = null;
  private JeliButton bh;
  private static final int border_x = 4;
  private int border_y = 2;
  private static final int inter_string_gap = 2;
  private int y0;
  private int labellen;
  private Font font;
  private FontMetrics fm;
  private Font boldfont;
  private FontMetrics boldfm;
  private boolean border_flag = true;
  private boolean pushed_state;
  private boolean enter_state;
  private boolean disable_flag;
  private String debug_string;
  private int gotten_count;
  private boolean focus;
  private boolean ReturnStringFlag;
  private boolean ReturnIntegerFlag;
  private boolean ReturnDoubleFlag;
  private String input_string;
  private boolean label_flag;
  private int int_value;
  private double double_value;
  private String string_value;
  private boolean get_integer_flag;
  private boolean get_double_flag;
  private boolean get_string_flag;
  private String init_prompt;
  private String get_value_prompt;
  private String value_prompt;
  private boolean value_flag;
  private boolean get_word_flag;
  private String after_string = "";
  private Color[] cols;
  private int position;
  private int position2;
  private int position3;
  private Color fore;
  private Color back;
  private boolean reverse_push_flag = false;
  private boolean inhibit_reverse_flag = false;
  private java.applet.AudioClip click = null;
  private java.applet.AudioClip good = null;
  private java.applet.AudioClip bad = null;
  private boolean callback_delay = false;
  private String callback_delay_label = null;
  private double fontsize = 0.0D;
  private String[] labelArray = null;
  private String[] labelArray2 = null;
  private String[] labelArray3 = null;
  private Color HighlightColor;
  private String[] FormattedLabelStringArray;
  private int[] FormattedLabelPositionArray;
  private Color[] FormattedLabelColorsArray = null;
  private boolean FormattedLabel = false;
  private java.awt.Image im;
  private boolean first_image_display = true;
  

  private boolean background_only = false;
  private boolean force_image_border = false;
  private int min_return_int;
  private int max_return_int;
  private boolean linked = false;
  private boolean asynchronous = false;
  private boolean noMouse = false;
  private boolean pushLinked = false;
  private boolean demoReverseMode = false;
  private boolean demoReverse = false;
  private Graphics canGC = null;
  private java.awt.Image canImage = null;
  private int w = -1;
  private int h = -1;
  private int mouseEnterCount = 0;
  private int mouseExitCount = 0;
  private int mouseUpCount = 0;
  private int mouseDownCount = 0;
  private int keyPressCount = 0;
  private int buttonCount = 0;
  private static int totalMouseEnterCount = 0;
  private static int totalMouseExitCount = 0;
  private static int totalKeyPressCount = 0;
  private static int totalMouseUpCount = 0;
  private static int totalMouseDownCount = 0;
  private static int totalButtonCount = 0;
  private static int totalButtonDoneCount = 0;
  private static int totalButtonDisposeCount = 0;
  private int lastPressedModifier = 0;
  private int lastClickCount = 0;
  private ButtonPainter buttonPainter = null;
  private boolean compressedArray = false;
  private java.awt.Component draggableComponent = null;
  private java.awt.Frame draggableFrame = null;
  private Point draggingLocation = null;
  private Point draggableComponentLocation = null;
  private PhantomDialog draggablePhantom = null;
  private int backgroundColorNumber = -1;
  
  public JeliButtonCanvas(String label, Font font, JeliButton bh)
  {
    this.label = label;
    this.bh = bh;
    setupFont(font);
    set_size();
    pushed_state = false;
    enter_state = false;
    disable_flag = false;
    debug_string = null;
    focus = false;
    ReturnStringFlag = false;
    ReturnIntegerFlag = false;
    label_flag = false;
    value_flag = false;
    get_integer_flag = false;
    get_double_flag = false;
    get_string_flag = false;
    get_word_flag = false;
    cols = null;
    position = POSITION_LEFT;
    position2 = POSITION_RIGHT;
    position3 = POSITION_CENTER;
    setForeground(hm.getDefaultForeground());
    setBackground(hm.getDefaultBackground());
    setColors();
    





    min_return_int = Integer.MIN_VALUE;
    max_return_int = Integer.MAX_VALUE;
    totalButtonCount += 1;
    buttonCount = totalButtonCount;
  }
  
  protected void setDraggable(java.awt.Frame f, java.awt.Component com) {
    draggingLocation = null;
    draggableComponentLocation = null;
    draggableFrame = f;
    draggableComponent = com;
  }
  
  private void set_size()
  {
    setSize(labellen + 8, y0 + border_y + fm.getMaxDescent());
  }
  
  protected void setButtonHeight(int n)
  {
    y0 = (fm.getMaxAscent() + border_y);
    int newHeight = y0 + border_y + fm.getMaxDescent();
    setSize(labellen + 8, n * newHeight);
  }
  
  protected void setHighlightColor(Color c) {
    HighlightColor = c;
  }
  










  private void setupFont(Font font)
  {
    this.font = font;
    String family = font.getFamily();
    int size = font.getSize();
    if (fontsize == 0.0D) {
      fontsize = size;
    }
    boldfont = new Font(family, 1, size);
    fm = getFontMetrics(font);
    boldfm = getFontMetrics(boldfont);
    setup_label_parameters();
  }
  


  protected void increaseFontSize()
  {
    fontsize *= 1.2D;
    int size = (int)fontsize;
    String family = font.getFamily();
    setupFont(new Font(family, 0, size));
    repaint();
  }
  
  protected void setFontSize(int size)
  {
    String family = font.getFamily();
    setupFont(new Font(family, 0, size));
    repaint();
  }
  


  protected void decreaseFontSize()
  {
    fontsize /= 1.2D;
    int size = (int)fontsize;
    String family = font.getFamily();
    setupFont(new Font(family, 0, size));
    repaint();
  }
  
  protected void setCallbackDelay(String lab)
  {
    callback_delay = true;
    callback_delay_label = lab;
  }
  
  protected void setClick(java.applet.AudioClip ac)
  {
    click = ac;
  }
  
  protected void setReverse(boolean f)
  {
    reverse_push_flag = f;
  }
  
  protected void setInhibitReverse(boolean f)
  {
    inhibit_reverse_flag = f;
  }
  
  protected void setColors()
  {
    fore = getForeground();
    back = getBackground();
  }
  
  protected void setBackground(int which) {
    backgroundColorNumber = which;
    setBackground(bh.hm.getColor(which));
  }
  
  protected void setPositionLeft() {
    position = POSITION_LEFT;
  }
  
  protected void setPositionRight() {
    position = POSITION_RIGHT;
  }
  
  protected void setPositionCenter() {
    position = POSITION_CENTER;
  }
  
  protected void setPosition2Left() {
    position2 = POSITION_LEFT;
  }
  
  protected void setPosition2Right() {
    position2 = POSITION_RIGHT;
  }
  
  protected void setPosition2Center() {
    position2 = POSITION_CENTER;
  }
  
  protected void setPosition3Left() {
    position3 = POSITION_LEFT;
  }
  
  protected void setPosition3Right() {
    position3 = POSITION_RIGHT;
  }
  
  protected void setPosition3Center() {
    position3 = POSITION_CENTER;
  }
  
  protected void setAfterString(String s)
  {
    after_string = s;
    repaint();
  }
  
  protected void setAsLabel(boolean f)
  {
    label_flag = f;
  }
  
  protected void setNoMouse(boolean f)
  {
    noMouse = f;
  }
  
  protected void setAsGetInteger(boolean f)
  {
    value_flag = f;
    if (value_flag) {
      label = (value_prompt + int_value);
    }
    else {
      label = init_prompt;
    }
    repaint();
  }
  
  protected void setAsGetDouble(boolean f)
  {
    value_flag = f;
    if (value_flag) {
      label = (value_prompt + Utility.n_places(double_value, 2));
    }
    else {
      label = init_prompt;
    }
    repaint();
  }
  
  protected void setAsGetString(boolean f)
  {
    value_flag = f;
    if (value_flag) {
      label = (value_prompt + string_value);
    }
    else {
      label = init_prompt;
    }
    repaint();
  }
  


  protected void setAsGetInteger(String init_prompt, String get_value_prompt, String value_prompt, int value, boolean value_flag)
  {
    get_integer_flag = true;
    this.init_prompt = init_prompt;
    this.get_value_prompt = get_value_prompt;
    this.value_prompt = value_prompt;
    int_value = value;
    this.value_flag = value_flag;
    if (value_flag) {
      label = (value_prompt + value);
    }
    else {
      label = init_prompt;
    }
    repaint();
  }
  


  protected void setAsGetDouble(String init_prompt, String get_value_prompt, String value_prompt, double value, boolean value_flag)
  {
    get_double_flag = true;
    this.init_prompt = init_prompt;
    this.get_value_prompt = get_value_prompt;
    this.value_prompt = value_prompt;
    double_value = value;
    this.value_flag = value_flag;
    if (value_flag) {
      label = (value_prompt + Utility.n_places(value, 2));
    }
    else {
      label = init_prompt;
    }
    repaint();
  }
  


  protected void setAsGetString(String init_prompt, String get_value_prompt, String value_prompt, String value, boolean value_flag)
  {
    get_string_flag = true;
    this.init_prompt = init_prompt;
    this.get_value_prompt = get_value_prompt;
    this.value_prompt = value_prompt;
    string_value = value;
    this.value_flag = value_flag;
    if (value_flag) {
      label = (value_prompt + string_value);
    }
    else {
      label = init_prompt;
    }
    repaint();
  }
  
  protected void setAsGetWord()
  {
    get_word_flag = true;
  }
  
  protected void setAsGetNothing(String prompt)
  {
    get_string_flag = false;
    value_flag = false;
    label = prompt;
    repaint();
  }
  
  protected void setDebugString(String str) {
    debug_string = str;
    gotten_count = 0;
  }
  
  protected void resetGet()
  {
    ReturnStringFlag = false;
    ReturnIntegerFlag = false;
    ReturnDoubleFlag = false;
    repaint();
  }
  
  protected void setGetString()
  {
    setGetString("");
  }
  
  protected void setGetColors(Color[] cols)
  {
    this.cols = cols;
    repaint();
  }
  
  protected void setGetString(String str)
  {
    input_string = str;
    ReturnStringFlag = true;
    ReturnIntegerFlag = false;
    ReturnDoubleFlag = false;
  }
  
  protected void setGetInteger()
  {
    input_string = "";
    ReturnStringFlag = true;
    ReturnIntegerFlag = true;
    ReturnDoubleFlag = false;
  }
  


  protected void setGetInteger(int val)
  {
    input_string = ("" + val);
    ReturnStringFlag = true;
    ReturnIntegerFlag = true;
    ReturnDoubleFlag = false;
  }
  


  protected void setGetIntegerRange(int low, int high)
  {
    min_return_int = low;
    max_return_int = high;
  }
  
  protected void setGetIntegerMin(int low)
  {
    min_return_int = low;
  }
  
  protected void setValue(int val)
  {
    int_value = val;
    value_flag = true;
    label = (value_prompt + val);
    repaint();
  }
  
  protected void setGetDouble()
  {
    input_string = "";
    ReturnStringFlag = true;
    ReturnIntegerFlag = false;
    ReturnDoubleFlag = true;
  }
  
  protected void setValue(double val)
  {
    double_value = val;
    value_flag = true;
    label = (value_prompt + Utility.n_places(double_value, 2));
    repaint();
  }
  
  protected void setGetDouble(double val)
  {
    input_string = Utility.n_places(val, 2);
    ReturnStringFlag = true;
    ReturnIntegerFlag = false;
    ReturnDoubleFlag = true;
  }
  
  private void setup_label_parameters()
  {
    y0 = (fm.getMaxAscent() + border_y);
    if (labelArray == null) {
      labellen = fm.stringWidth(label);
    }
    else {
      labellen = fm.stringWidth(labelArray[0]);
      for (int i = 0; i < labelArray.length; i++) {
        int len = fm.stringWidth(labelArray[i]);
        if ((labelArray2 != null) && (labelArray2.length == labelArray.length))
          len = len + fm.stringWidth(labelArray2[i]) + 15;
        if ((labelArray3 != null) && (labelArray3.length == labelArray.length))
          len = len + fm.stringWidth(labelArray3[i]) + 15;
        if (len > labellen) {
          labellen = len;
        }
      }
    }
  }
  
  protected int getLabelLength()
  {
    return labellen;
  }
  
  protected void setCanvasLabel(String lab)
  {
    label = lab;
    setup_label_parameters();
    repaint();
  }
  
  protected void setCanvasLabel2(String lab)
  {
    label2 = lab;
    repaint();
  }
  
  protected void setCanvasLabel3(String lab) {
    label3 = lab;
    repaint();
  }
  
  protected void disableJeliButton()
  {
    disable_flag = true;
    repaint();
  }
  
  protected void enableJeliButton()
  {
    disable_flag = false;
    repaint();
  }
  

  private void draw_border(Graphics g, int t)
  {
    int wm1 = getBoundswidth - 1;
    int hm1 = getBoundsheight - 1;
    if (t == -1) {
      t = bh.hm.getDefaultBorderType();
    }
    if (t == 15) {
      g.drawRect(0, 0, wm1, hm1);
      
      return;
    }
    
    if ((t & 0x1) != 0)
    {
      g.drawLine(0, 0, wm1, 0);
    }
    if ((t & 0x2) != 0)
    {
      g.drawLine(0, hm1, wm1, hm1);
    }
    if ((t & 0x4) != 0)
    {
      g.drawLine(0, 0, 0, hm1);
    }
    if ((t & 0x8) != 0)
    {
      g.drawLine(wm1, 0, wm1, hm1);
    }
  }
  



















  protected void setLabelArray(String str)
  {
    labelArray = getLabelArray(str);
    setup_label_parameters();
    repaint();
  }
  
  protected void setLabelArray2(String str) {
    labelArray2 = getLabelArray(str);
    setup_label_parameters();
    repaint();
  }
  
  protected void setLabelArray3(String str) {
    labelArray3 = getLabelArray(str);
    setup_label_parameters();
    repaint();
  }
  


  private String[] getLabelArray(String str)
  {
    if (str == null)
      return null;
    java.util.StringTokenizer strtok = new java.util.StringTokenizer(str, "\n");
    int count = strtok.countTokens();
    String[] lArray = new String[count];
    for (int i = 0; i < count; i++)
      lArray[i] = strtok.nextToken();
    return lArray;
  }
  

  private void drawString(Graphics g, FontMetrics tfm, String str, int pos)
  {
    if (labelArray != null) {
      if (compressedArray) {
        drawStringArrayCompressed(g, tfm, labelArray, pos);
        drawStringArrayCompressed(g, tfm, labelArray2, POSITION_CENTER);
        drawStringArrayCompressed(g, tfm, labelArray3, POSITION_RIGHT);
      }
      else {
        drawStringArray(g, tfm, labelArray, pos);
        drawStringArray(g, tfm, labelArray2, POSITION_CENTER);
        drawStringArray(g, tfm, labelArray3, POSITION_RIGHT);
      }
      return;
    }
    int y0 = (getBoundsheight + fm.getMaxAscent() - fm.getMaxDescent()) / 2;
    if (FormattedLabel) {
      drawFormattedLabel(g, y0);
      return;
    }
    drawStringPosition(g, tfm, str, y0, pos);
  }
  
  private void drawFormattedLabel(Graphics g, int y) {
    for (int i = 0; i < FormattedLabelStringArray.length; i++) {
      if (FormattedLabelColorsArray != null) {
        g.setColor(FormattedLabelColorsArray[i]);
      }
      g.drawString(FormattedLabelStringArray[i], FormattedLabelPositionArray[i], y);
    }
  }
  










  private void drawStringArray(Graphics g, FontMetrics tfm, String[] str, int pos)
  {
    if (str == null)
      return;
    int n = str.length;
    int max_ascent = fm.getMaxAscent();
    int max_descent = fm.getMaxDescent();
    int border = (getBoundsheight - n * (max_ascent + max_descent) - (n - 1) * 2) / 2;
    
    int y0 = border + max_ascent;
    int delta = max_ascent + max_descent + 2;
    int total_width = getBoundswidth;
    for (int i = 0; i < str.length; i++) {
      drawStringPosition(g, tfm, str[i], y0 + i * delta, pos);
    }
  }
  








  private void drawStringArrayCompressed(Graphics g, FontMetrics tfm, String[] str, int pos)
  {
    if (str == null)
      return;
    int n = str.length;
    if (n <= 1) {
      drawStringArray(g, tfm, str, pos);
      return;
    }
    int max_ascent = fm.getMaxAscent();
    int max_descent = fm.getMaxDescent();
    int border = (getBoundsheight - n * (max_ascent + max_descent) - (n - 1) * 2) / 2;
    
    int y0 = max_ascent;
    int delta = (getBoundsheight - y0 - max_descent) / (n - 1);
    if (delta <= 0) {
      drawStringArray(g, tfm, str, pos);
      return;
    }
    for (int i = 0; i < str.length; i++) {
      drawStringPosition(g, tfm, str[i], y0 + i * delta, pos);
    }
  }
  





















  private void drawStringPosition(Graphics g, FontMetrics tfm, String str, int y, int pos)
  {
    int total_width = getBoundswidth;
    if (pos == POSITION_LEFT) {
      g.drawString(str, 4, y);
    }
    else {
      int str_width = tfm.stringWidth(str);
      if (pos == POSITION_RIGHT) {
        g.drawString(str, total_width - str_width - 4, y);
      }
      else if (pos == POSITION_CENTER) {
        g.drawString(str, (total_width - str_width) / 2, y);
      }
    }
  }
  
  protected void resetState()
  {
    pushed_state = false;
    enter_state = false;
    repaint();
  }
  
  public void update(Graphics g) {
    paint(g);
  }
  
  protected void makeImage(int w, int h) {
    if ((w <= 0) || (h <= 0))
      return;
    if (canGC != null) {
      canGC.dispose();
    }
    if (canImage != null) {
      canImage.flush();
    }
    if (displayCanvas == null) {
      canImage = createImage(w, h);
    }
    else {
      canImage = displayCanvas.createImage(w, h);
    }
    if (canImage != null) {
      canGC = canImage.getGraphics();
    }
  }
  

  public void paint(Graphics g)
  {
    Rectangle bounds = getBounds();
    if ((canImage == null) || (h == -1) || (w == -1) || (w != width) || (h != height))
    {
      w = width;
      h = height;
      makeImage(w, h);
    }
    if (canImage == null) {
      return;
    }
    









    fillImage(w, h);
    g.drawImage(canImage, 0, 0, null);
  }
  


  private void fillImage(int w, int h)
  {
    String append_string = "";
    







    if (backgroundColorNumber != -1) {
      back = bh.hm.getColor(backgroundColorNumber);
    }
    y0 = ((getBoundsheight + fm.getMaxAscent() - fm.getMaxAscent()) / 2);
    
    if ((fore == null) || (back == null)) {
      setColors();
    }
    if (disable_flag) {
      enter_state = false;
      pushed_state = false; }
    int border_type;
    int font_type; int reverse_type; if (pushed_state) {
      int border_type = bh.PushBorderType;
      int font_type = bh.PushFontType;
      int reverse_type = bh.PushReverseType;
      if (border_type == JeliButton.TYPE_DEFAULT) {
        border_type = bh.hm.DefaultPushBorderType;
      }
      if (font_type == JeliButton.TYPE_DEFAULT) {
        font_type = bh.hm.DefaultPushFontType;
      }
      if (reverse_type == JeliButton.TYPE_DEFAULT) {
        reverse_type = bh.hm.DefaultPushReverseType;
      }
    }
    else if (enter_state) {
      int border_type = bh.EnterBorderType;
      int font_type = bh.EnterFontType;
      int reverse_type = bh.EnterReverseType;
      if (border_type == JeliButton.TYPE_DEFAULT) {
        border_type = bh.hm.DefaultEnterBorderType;
      }
      if (font_type == JeliButton.TYPE_DEFAULT) {
        font_type = bh.hm.DefaultEnterFontType;
      }
      if (reverse_type == JeliButton.TYPE_DEFAULT) {
        reverse_type = bh.hm.DefaultEnterReverseType;
      }
    }
    else {
      border_type = bh.NormBorderType;
      font_type = bh.NormFontType;
      reverse_type = bh.NormReverseType;
      if (border_type == JeliButton.TYPE_DEFAULT) {
        border_type = bh.hm.DefaultNormBorderType;
      }
      if (font_type == JeliButton.TYPE_DEFAULT) {
        font_type = bh.hm.DefaultNormFontType;
      }
      if (reverse_type == JeliButton.TYPE_DEFAULT)
        reverse_type = bh.hm.DefaultNormReverseType; }
    Color back1;
    Color fore1;
    Color back1; if ((!inhibit_reverse_flag) && (reverse_type == 1))
    {
      Color fore1 = back;
      back1 = fore;
    }
    else {
      fore1 = fore;
      back1 = back;
    }
    if (demoReverseMode) {
      if (demoReverse) {
        fore1 = back;
        back1 = fore;
      }
      else {
        fore1 = fore;
        back1 = back;
      }
    }
    
    if ((ReturnStringFlag) && (enter_state) && (HighlightColor != null)) {
      back1 = HighlightColor;
    }
    FontMetrics thisfm = fm;
    if (font_type == 1) {
      thisfm = boldfm;
    }
    if (background_only) {
      canGC.setColor(back);
      canGC.fillRect(0, 0, getBoundswidth, getBoundsheight);
      bh.updateAll();
      return;
    }
    if (disable_flag) {
      canGC.setColor(back1);
      canGC.fillRect(0, 0, w, h);
      canGC.setColor(Color.gray);
      canGC.setFont(font);
      drawString(canGC, thisfm, label, position);
      if (label2 != null) {
        drawString(canGC, thisfm, label2, position2);
      }
      if (label3 != null) {
        drawString(canGC, thisfm, label3, position3);
      }
      canGC.setColor(Color.black);
      draw_border(canGC, border_type);
      bh.updateAll();
      return;
    }
    canGC.setColor(back1);
    
    canGC.fillRect(0, 0, w, h);
    
























    canGC.setColor(Color.black);
    draw_border(canGC, border_type);
    canGC.setColor(fore1);
    if (font_type == 1) {
      canGC.setFont(boldfont);
      thisfm = boldfm;
    }
    else {
      canGC.setFont(font);
      thisfm = fm;
    }
    if (ReturnStringFlag) {
      append_string = input_string + "_";
    }
    else {
      append_string = append_string + after_string;
    }
    if (cols != null) {
      int size = cols.length;
      
      int wid = (w + size - 1) / size;
      
      for (int i = 0; i < size; i++) {
        canGC.setColor(cols[i]);
        canGC.fillRect(w * i / size, 0, wid, h);
      }
      bh.updateAll();
      return;
    }
    if (im != null) {
      if ((im.getWidth(null) <= 0) || (im.getHeight(null) <= 0)) {
        new Thread(this).start();

      }
      else if (first_image_display) {
        new Thread(this).start();
        first_image_display = false;
      }
      canGC.drawImage(im, 0, 0, null);
      if (force_image_border) {
        canGC.setColor(Color.black);
        
        draw_border(canGC, border_type);
      }
      bh.updateAll();
    }
    
    drawString(canGC, thisfm, label + append_string, position);
    if (label2 != null) {
      drawString(canGC, thisfm, label2, position2);
    }
    if (label3 != null) {
      drawString(canGC, thisfm, label3, position3);
    }
    if (buttonPainter != null) {
      buttonPainter.paintButton(this, canGC);
    }
    bh.updateAll();
  }
  
  protected void setButtonPainter(ButtonPainter bp) {
    buttonPainter = bp;
  }
  
  public void mouseDownCanvas(int x, int y) {
    mouseDown1(x, y);
    bh.panelMouseEnter(x, y);
    mouseDownCount += 1;
    totalMouseDownCount += 1;
  }
  
  protected void simulatePushed(boolean f)
  {
    pushed_state = f;
    repaint();
  }
  

  private void mouseDown1(int x, int y)
  {
    if (draggableComponent != null) {
      draggingLocation = new Point(x, y);
      draggableComponentLocation = draggableComponent.getLocation();
      draggablePhantom = new PhantomDialog(draggableFrame, draggableComponent);
      draggablePhantom.setVisible(true);
    }
    
    if ((label_flag) || (disable_flag)) {
      return;
    }
    if (noMouse) {
      return;
    }
    pushed_state = true;
    if (pushLinked) {
      Utility.buttonPushed(bh, true);
    }
    int click_type = bh.PushClickType;
    if (click_type == JeliButton.TYPE_DEFAULT) {
      click_type = bh.hm.DefaultPushClickType;
    }
    if (click_type == 1) {
      if (click != null) {
        Utility.playAudioClip(click);
      } else
        Utility.playAudioClip(bh.hm.click_clip);
    }
    if (cols != null) {
      bh.bhc.jeliButtonGotInteger(bh, x * cols.length / getBoundswidth);
      bh.panelMouseEnter(0, 0);
      return;
    }
    
    if (ReturnStringFlag) {
      if (ReturnIntegerFlag) {
        if (get_integer_flag) {
          label2 = null;
          label3 = null;
          if (value_flag) {
            label = (value_prompt + int_value);
          }
          else {
            label = init_prompt;
          }
          ReturnStringFlag = false;
          repaint();
          return;
        }
        bh.bhc.jeliButtonGotInteger(bh, -1);
        bh.panelMouseEnter(0, 0);
        ReturnStringFlag = false;
        return;
      }
      if (ReturnDoubleFlag) {
        if (get_double_flag) {
          label2 = null;
          label3 = null;
          if (value_flag) {
            label = (value_prompt + Utility.n_places(double_value, 2));
          }
          else {
            label = init_prompt;
          }
          ReturnStringFlag = false;
          repaint();
          return;
        }
        bh.bhc.jeliButtonGotDouble(bh, NaN.0D);
        bh.panelMouseEnter(0, 0);
        ReturnStringFlag = false;
        return;
      }
      if (get_string_flag) {
        label2 = null;
        label3 = null;
        if (value_flag) {
          label = (value_prompt + string_value);
        }
        else {
          label = init_prompt;
        }
        ReturnStringFlag = false;
        repaint();
        return;
      }
      bh.bhc.jeliButtonGotString(bh, null);
      bh.panelMouseEnter(0, 0);
      ReturnStringFlag = false;
      return;
    }
    if (get_integer_flag) {
      label2 = null;
      label3 = null;
      label = get_value_prompt;
      if (value_flag) {
        setGetInteger(int_value);
      }
      else {
        setGetInteger();
      }
      requestFocus();
      focus = true;
      repaint();
      return;
    }
    if (get_double_flag) {
      label2 = null;
      label3 = null;
      label = get_value_prompt;
      if (value_flag) {
        setGetDouble(double_value);
      }
      else {
        setGetDouble();
      }
      requestFocus();
      focus = true;
      repaint();
      return;
    }
    if (get_string_flag) {
      label2 = null;
      label3 = null;
      label = get_value_prompt;
      if (value_flag) {
        setGetString(string_value);
      }
      else {
        setGetString();
      }
      requestFocus();
      focus = true;
      repaint();
      return;
    }
    if ((bh.bhc != null) && (!callback_delay)) {
      if (asynchronous) {
        new JeliButtonThread(bh.bhc, bh);
      }
      else {
        bh.trace();
        bh.bhc.jeliButtonPushed(bh);
      }
      if (linked) {
        Utility.handleLink(bh);
      }
    }
    if ((callback_delay) && (callback_delay_label != null)) {
      label = callback_delay_label;
    }
    if (ReturnStringFlag) {
      requestFocus();
      focus = true;
    }
    repaint();
  }
  
  private void setDraggingPosition(int x, int y) {
    if (draggableComponent == null)
      return;
    if (draggablePhantom == null)
      return;
    draggablePhantom.setLocation(draggableComponentLocation.x + x - draggingLocation.x, draggableComponentLocation.y + y - draggingLocation.y);
  }
  


  private void setFinalDraggingPosition(int x, int y)
  {
    if (draggableComponent == null)
      return;
    draggableComponent.setLocation(draggableComponentLocation.x + x - draggingLocation.x, draggableComponentLocation.y + y - draggingLocation.y);
  }
  


  public void mouseUpCanvas(int x, int y)
  {
    mouseUpCount += 1;
    totalMouseUpCount += 1;
    if (draggingLocation != null) {
      setFinalDraggingPosition(x, y);
      draggingLocation = null;
      draggableComponentLocation = null;
      draggablePhantom.setVisible(false);
      draggablePhantom = null;
    }
    
    if ((label_flag) || (disable_flag)) {
      return;
    }
    if (noMouse) {
      return;
    }
    if ((bh.bhc != null) && (callback_delay)) {
      if (asynchronous) {
        new JeliButtonThread(bh.bhc, bh);
      }
      else {
        bh.trace();
        bh.bhc.jeliButtonPushed(bh);
      }
    }
    pushed_state = false;
    if (pushLinked) {
      Utility.buttonPushed(bh, false);
    }
    repaint();
  }
  
  public void mouseEnterCanvas(int x, int y) {
    mouseEnterCount += 1;
    totalMouseEnterCount += 1;
    if (label_flag)
      bh.panelMouseEnter(x, y);
    if ((label_flag) || (disable_flag)) {
      return;
    }
    enter_state = true;
    if ((debug_string != null) || (ReturnStringFlag)) {
      requestFocus();
      focus = true;
    }
    bh.panelMouseEnter(x, y);
    repaint();
  }
  
  public void mouseExitCanvas(int x, int y) {
    mouseExitCount += 1;
    totalMouseExitCount += 1;
    if ((label_flag) || (disable_flag)) {
      return;
    }
    focus = false;
    enter_state = false;
    pushed_state = false;
    bh.panelMouseExit(x, y);
    repaint();
  }
  


  public void keyPressCanvas(int key)
  {
    char c = (char)key;
    keyPressCount += 1;
    totalKeyPressCount += 1;
    if ((label_flag) || (disable_flag)) {
      return;
    }
    if (!focus) {
      return;
    }
    if ((debug_string == null) && (!ReturnStringFlag)) {
      return;
    }
    if (ReturnStringFlag) {
      if ((c == ' ') && (get_word_flag)) {
        return;
      }
      if ((c == '\n') || (c == '\r')) {
        ReturnStringFlag = false;
        if (ReturnIntegerFlag) {
          int val;
          try { val = Integer.parseInt(input_string);
          }
          catch (NumberFormatException e1) {
            if (get_integer_flag) {
              if (value_flag) {
                label = (value_prompt + int_value);
              }
              else {
                label = init_prompt;
              }
              ReturnStringFlag = false;
              playBad();
              repaint();
              return;
            }
            bh.bhc.jeliButtonGotInteger(bh, -1);
            bh.panelMouseEnter(0, 0);
            ReturnStringFlag = false;
            playBad();
            return;
          }
          if ((val < min_return_int) || (val > max_return_int)) {
            if (get_integer_flag) {
              if (value_flag) {
                label = (value_prompt + int_value);
              }
              else {
                label = init_prompt;
              }
              ReturnStringFlag = false;
              playBad();
              repaint();
              return;
            }
            bh.bhc.jeliButtonGotInteger(bh, -1);
            bh.panelMouseEnter(0, 0);
            ReturnStringFlag = false;
            playBad();
            return;
          }
          if (get_integer_flag) {
            int_value = val;
            value_flag = true;
            label = (value_prompt + int_value);
            repaint();
          }
          playGood();
          bh.bhc.jeliButtonGotInteger(bh, val);
          bh.panelMouseEnter(0, 0);
          return;
        }
        if (ReturnDoubleFlag) {
          double dval;
          try { dval = Double.valueOf(input_string).doubleValue();
          }
          catch (NumberFormatException e1) {
            if (get_double_flag) {
              if (value_flag) {
                label = (value_prompt + Utility.n_places(double_value, 2));
              }
              else
              {
                label = init_prompt;
              }
              ReturnStringFlag = false;
              repaint();
              playBad();
              return;
            }
            bh.bhc.jeliButtonGotDouble(bh, NaN.0D);
            bh.panelMouseEnter(0, 0);
            ReturnStringFlag = false;
            return;
          }
          if (get_double_flag) {
            double_value = dval;
            value_flag = true;
            label = (value_prompt + Utility.n_places(double_value, 2));
            repaint();
          }
          playGood();
          bh.bhc.jeliButtonGotDouble(bh, dval);
          bh.panelMouseEnter(0, 0);
        }
        else {
          if (get_string_flag) {
            string_value = input_string;
            value_flag = true;
            label = (value_prompt + string_value);
            repaint();
          }
          playGood();
          bh.bhc.jeliButtonGotString(bh, input_string);
          bh.panelMouseEnter(0, 0);
        }
      }
      else if ((key == 8) || (key == 127)) {
        if (input_string.length() > 0) {
          input_string = input_string.substring(0, input_string.length() - 1);
        }
      }
      else
      {
        input_string += c;
      }
      repaint();
      return;
    }
    if (c == debug_string.charAt(gotten_count)) {
      gotten_count += 1;
      if (gotten_count == debug_string.length()) {
        bh.debugFound();
        gotten_count = 0;
      }
    }
    else {
      gotten_count = 0;
    }
  }
  

  private void playBad()
  {
    int type = bh.BadAudioType;
    if (type == JeliButton.TYPE_DEFAULT) {
      type = bh.hm.DefaultBadAudioType;
    }
    if (type == 1)
    {
      if (bad != null) {
        Utility.playAudioClip(bad);
      } else {
        Utility.playAudioClip(bh.hm.bad_clip);
      }
    }
  }
  
  private void playGood() {
    int type = bh.GoodAudioType;
    if (type == JeliButton.TYPE_DEFAULT) {
      type = bh.hm.DefaultGoodAudioType;
    }
    if (type == 1)
    {
      if (good != null) {
        Utility.playAudioClip(good);
      } else {
        Utility.playAudioClip(bh.hm.good_clip);
      }
    }
  }
  















  public void jeliMouseEnter()
  {
    mouseEnterCanvas(0, 0);
  }
  
  public void jeliMouseExited() {
    mouseExitCanvas(0, 0);
  }
  
  public void jeliMousePressed(int x, int y, int mod, int count) {
    if (bh.hm.buttonDebugFlag) {
      showDebugMessage();
      return;
    }
    lastPressedModifier = mod;
    lastClickCount = count;
    mouseDownCanvas(x, y);
  }
  
  public void jeliMouseReleased(int x, int y, int mod, int count) {
    if (bh.hm.buttonDebugFlag) {
      return;
    }
    mouseUpCanvas(x, y);
  }
  
  public void jeliMouseDragged(int x, int y) {
    if (draggableComponent != null) {
      setDraggingPosition(x, y);
    }
  }
  





















































  public void jeliKeyPress(char c)
  {
    keyPressCanvas(c);
  }
  
  protected void setFormattedLabel(String[] str, int[] pos) {
    if (str.length != pos.length) {
      return;
    }
    FormattedLabelStringArray = new String[str.length];
    FormattedLabelPositionArray = new int[str.length];
    for (int i = 0; i < str.length; i++) {
      FormattedLabelStringArray[i] = str[i];
      FormattedLabelPositionArray[i] = pos[i];
    }
    FormattedLabel = true;
    repaint();
  }
  
  protected void setFormattedLabelColors(Color[] c) {
    FormattedLabelColorsArray = new Color[c.length];
    for (int i = 0; i < c.length; i++) {
      FormattedLabelColorsArray[i] = c[i];
    }
  }
  
  protected void clearFormattedLabel() {
    FormattedLabel = false;
    FormattedLabelColorsArray = null;
    repaint();
  }
  
  protected void setImage(java.awt.Image im) {
    this.im = im;
    first_image_display = true;
    repaint();
  }
  
  public void run() {
    setName("Jeli Button Canvas Thread");
    try {
      Thread.sleep(250L);
    }
    catch (InterruptedException e) {}
    repaint();
  }
  










  protected void backgroundOnly(boolean f)
  {
    background_only = f;
    repaint();
  }
  
  protected void forceImageBorder(boolean f) {
    force_image_border = f;
    repaint();
  }
  
  protected void toggleImageBorder() {
    if (force_image_border) {
      force_image_border = false;
    }
    else {
      force_image_border = true;
    }
    repaint();
  }
  
  protected boolean getImageBorder() {
    return force_image_border;
  }
  
  protected void setLinked(boolean f) {
    linked = f;
  }
  
  protected void simulatePushed() {
    if (bh.bhc != null) {
      bh.trace();
      bh.bhc.jeliButtonPushed(bh);
    }
  }
  
  protected Font getButtonFont() {
    return font;
  }
  
  protected void simulatePaint(Graphics g) {
    paint(g);
  }
  
  protected void setAsynchronous(boolean f) {
    asynchronous = f;
  }
  
  protected void setPushedLinked(boolean f) {
    pushLinked = f;
  }
  
  private void showDebugMessage()
  {
    StringBuffer msg = new StringBuffer();
    msg.append("Button pushed: " + label + "\n");
    msg.append("foreground: " + fore + "\n");
    msg.append("background: " + back + "\n");
    msg.append("dimensions: " + getBounds() + "\n");
    msg.append("position: " + Utility.getAbsolutePosition(this) + "\n");
    msg.append("preferred size      : " + getPreferredSize() + "\n");
    msg.append("preferred panel size: " + bh.getPreferredSize() + "\n");
    msg.append("button list: " + bh.hm.getButtonListSize() + "\n");
    msg.append("button count: " + buttonCount + "/" + totalButtonCount + "/" + totalButtonDoneCount + "\n");
    

    msg.append("dispose count: " + totalButtonDisposeCount + "\n");
    msg.append("enter count: " + mouseEnterCount + "/" + totalMouseEnterCount + "\n");
    
    msg.append("exit count:  " + mouseExitCount + "/" + totalMouseExitCount + "\n");
    
    msg.append("up count:    " + mouseUpCount + "/" + totalMouseUpCount + "\n");
    msg.append("down count:  " + mouseDownCount + "/" + totalMouseDownCount + "\n");
    
    msg.append("key press:  " + keyPressCount + "/" + totalKeyPressCount + "\n");
    bh.hm.showDebugMessage(bh, msg.toString());
  }
  
  protected String getLabel() {
    return label;
  }
  
  protected String getLabel2() {
    return label2;
  }
  
  protected String getLabel3() {
    return label3;
  }
  
  protected void setDemoReverseMode(boolean b) {
    demoReverseMode = b;
    demoReverse = b;
    repaint();
  }
  
  protected void setDemoReverse(boolean b) {
    demoReverse = b;
    repaint();
  }
  
  protected void toggleDemoReverse() {
    if (demoReverse) {
      demoReverse = false;
    }
    else {
      demoReverse = true;
    }
    repaint();
  }
  
  protected void finalize() throws Throwable {
    totalButtonDoneCount += 1;
    
    super.finalize();
  }
  
  protected void disposeButton()
  {
    totalButtonDisposeCount += 1;
    if (canGC != null) {
      canGC.dispose();
    }
    if (canImage != null) {
      canImage.flush();
    }
    removeKeyListener(this);
    removeMouseListener(this);
    removeMouseMotionListener(this);
  }
  
  protected int getButtonCount() {
    return buttonCount;
  }
  
  protected void setDisplayCanvas(JeliCanvas can) {
    displayCanvas = can;
  }
  
  protected int getMouseModifier() {
    return lastPressedModifier;
  }
  
  protected int getClickCount() {
    return lastClickCount;
  }
  
  protected void setBorderY(int n) {
    border_y = n;
  }
  
  protected void setCompressedArray(boolean f) {
    compressedArray = f;
  }
  
  protected boolean usingLabelArray() {
    return labelArray != null;
  }
}
