package Jeli.Widgets;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;

public class JeliTextFrame extends JeliFrame implements Jeli.Logging.Mono, Jeli.Logging.LogState
{
  private HelpManager hm;
  private int id;
  protected JeliTextDisplay jtd;
  private TextDisplayButtonCallBack cb;
  private int numButtonsWidth;
  private int numButtonsHeight;
  protected JeliButton[][] buttons;
  private String[][] buttonLabels;
  private String label;
  private boolean separateHideButton = false;
  private Panel allButtonsPanel = null;
  private Panel buttonPanel;
  private boolean showButtons = true;
  private DebugRegistration debugRegistration;
  protected JeliButton hideButton;
  protected Panel hidePanel = null;
  
  public JeliTextFrame(int id, int rows, int columns, String label, HelpManager hm, TextDisplayButtonCallBack cb)
  {
    this(id, rows, columns, label, hm, cb, new Font("Times", 0, 10));
  }
  

  public JeliTextFrame(int id, int rows, int columns, String label, HelpManager hm, TextDisplayButtonCallBack cb, Font font)
  {
    super(label);
    

    this.id = id;
    this.hm = hm;
    this.cb = cb;
    this.label = label;
    
    if (font == null)
      font = hm.getTableFont();
    super.setFont(font);
    jtd = new JeliTextDisplay(id, rows, columns, label, font, Color.white, Color.black, hm, cb);
    
    setBackground(Color.white);
    setForeground(Color.black);
  }
  




  public void useHideButton()
  {
    separateHideButton = true;
  }
  
  public void setDebug(DebugRegistration reg)
  {
    debugRegistration = reg;
  }
  
  public void setLabel(String str) {
    jtd.setLabel(str);
  }
  
  public void setFont(Font f) {
    jtd.setFont(f);
  }
  
  public int getWidth() {
    return jtd.getWidth();
  }
  
  public java.awt.FontMetrics getFontMetric() {
    return jtd.getFontMetric();
  }
  
  public void setButtons(String[][] buttonLabels) {
    this.buttonLabels = buttonLabels;
    numButtonsHeight = buttonLabels.length;
    numButtonsWidth = buttonLabels[0].length;
    setupLayout((JeliButton[][])null);
  }
  
  public void setButtons(String[][] buttonLabels, JeliButton[][] buttons) {
    this.buttonLabels = buttonLabels;
    numButtonsHeight = buttonLabels.length;
    numButtonsWidth = buttonLabels[0].length;
    setupLayout(buttons);
  }
  
  public void setButtonBackground(int row, int col, Color c) {
    buttons[row][col].setBackground(c);
  }
  
  public void setButtonForeground(int row, int col, Color c) {
    buttons[row][col].setForeground(c);
  }
  
  public void setCallBack(TextDisplayButtonCallBack cb) {
    this.cb = cb;
    jtd.setCallBack(cb);
  }
  
  private void setupLayout(JeliButton[][] but) {
    setLayout(new BorderLayout());
    add("Center", jtd);
    buttonPanel = new Panel();
    buttonPanel.setLayout(new JeliGridLayout(numButtonsHeight, numButtonsHeight));
    if (separateHideButton) {
      Panel botPanel = new Panel();
      botPanel.setLayout(new BorderLayout());
      allButtonsPanel = new Panel();
      allButtonsPanel.setLayout(new java.awt.GridLayout(1, 1));
      if (showButtons)
        allButtonsPanel.add(buttonPanel);
      botPanel.add("Center", allButtonsPanel);
      hideButton = new JeliButton("Hide", this);
      hideButton.setPositionCenter();
      hidePanel = new Panel();
      hidePanel.setLayout(new java.awt.GridLayout(1, 1));
      hidePanel.add(hideButton);
      botPanel.add("South", hidePanel);
      if (debugRegistration != null)
        hideButton.setDebugString("debug", 0, debugRegistration);
      add("South", botPanel);
    }
    else {
      add("South", buttonPanel); }
    buttons = new JeliButton[numButtonsHeight][numButtonsWidth];
    for (int i = 0; i < numButtonsHeight; i++)
      for (int j = 0; j < numButtonsWidth; j++) {
        if ((buttonLabels[i][j] == null) && (but == null)) {
          buttons[i][j] = new JeliLabel("");
        } else if ((buttonLabels[i][j] == null) && (but[i][j] == null)) {
          buttons[i][j] = new JeliLabel("");
        } else if (buttonLabels[i][j] == null) {
          buttons[i][j] = but[i][j];
        } else
          buttons[i][j] = new JeliButton(buttonLabels[i][j], this);
        buttonPanel.add(buttons[i][j]);
        buttons[i][j].setClassification(label);
        buttons[i][j].setButtonSize();
      }
    pack();
  }
  






  public void setShowButtons(boolean f)
  {
    showButtons = f;
    revalidate();
  }
  
  private void revalidate() {
    if (allButtonsPanel == null)
      return;
    allButtonsPanel.removeAll();
    if (showButtons)
      allButtonsPanel.add(buttonPanel);
    allButtonsPanel.invalidate();
    validate();
  }
  

  public void jeliButtonPushed(JeliButton bh)
  {
    String label = bh.getLabel();
    if (label.equals("Hide")) {
      setVisible(false);
      return;
    }
    if (cb == null) return;
    for (int i = 0; i < numButtonsHeight; i++)
      for (int j = 0; j < numButtonsWidth; j++)
        if (bh == buttons[i][j]) {
          cb.textDisplayButtonCallBack(id, buttonLabels[i][j]);
          return;
        }
  }
  
  public JeliTextArea getArea() {
    return jtd.textArea;
  }
  
  public void appendLine() {
    jtd.appendLineIfNecessary();
  }
  
  public void appendLine(int totab) {
    jtd.appendLineIfNecessary(totab);
  }
  
  public void appendToCurrent(String str) {
    jtd.appendToCurrent(str);
  }
  
  public void append(String str) {
    jtd.textArea.append(str);
  }
  
  public void append(String str, int[] tabs) {
    jtd.textArea.append(str, tabs);
  }
  
  public void setAsDefault() {
    jtd.setAsDefault();
  }
  
  public void setEnabled(boolean f) {
    jtd.setEnabled(f);
  }
  
  public void setMono(boolean f) {
    jtd.setMono(f);
  }
  
  public void setBorderType(int r, int c, int type) {
    buttons[r][c].setBorderType(type);
  }
  
  public void setAsLabel(int r, int c) {
    buttons[r][c].setAsLabel();
  }
  
  public void setPositionCenter(int r, int c) {
    buttons[r][c].setPositionCenter();
  }
  
  public void setButtonLabel(int r, int c, String str) {
    buttons[r][c].setLabel(str);
    buttonLabels[r][c] = str;
  }
  
  public void setLineForeground(Color c) {
    jtd.setLineForeground(c);
  }
  
  public void setLineBackground(Color c) {
    jtd.setLineBackground(c);
  }
  
  public void setLineForeground(int n, Color c) {
    jtd.setLineForeground(n, c);
  }
  
  public void setAllLinesForeground(Color c) {
    jtd.setAllLinesForeground(c);
  }
  
  public void incrementFontSize() {
    jtd.incrementFontSize();
  }
  
  public void decrementFontSize() {
    jtd.decrementFontSize();
  }
  
  public void top() {
    jtd.top();
  }
  
  public void logStateChange(boolean open) {
    if (open) {
      jtd.logButton.enableJeliButton();
    } else
      jtd.logButton.disableJeliButton();
  }
  
  public void setTabs(int[] tabs) {
    jtd.setTabs(tabs);
  }
  
  public void setNewTabs(int n) {
    jtd.setNewTabs(n);
  }
  
  public void setTabPositionLeft(int n) {
    jtd.setTabPositionLeft(n);
  }
  
  public void setTabPositionRight(int n) {
    jtd.setTabPositionRight(n);
  }
  
  public void setTabPositionCenter(int n) {
    jtd.setTabPositionCenter(n);
  }
  
  public void setDynamicTabs(boolean f) {
    jtd.setDynamicTabs(f);
  }
  
  public void setSetTabs(boolean f) {
    jtd.setSetTabs(f);
  }
  
  public void setShowTabs(boolean f) {
    jtd.setShowTabs(f);
  }
  
  public void setInhibitEndTab(int n) {
    jtd.setInhibitEndTab(n);
  }
  
  public void setNoScrollTop(int n) {
    jtd.setNoScrollTop(n);
  }
  
  public void refresh() {
    jtd.refresh();
  }
  
  public void clear() {
    jtd.textArea.setText("");
  }
  
  public void inhibitDisplay(boolean f) {
    jtd.inhibitDisplay(f);
  }
  
  public void setLastUnderline(int n) {
    jtd.setLastUnderline(n);
  }
  
  public void setDynamicTabsFull(boolean f) {
    jtd.setDynamicTabsFull(f);
  }
  
  public void disposeJeliTextFrame() {
    for (int i = 0; i < numButtonsHeight; i++)
      for (int j = 0; j < numButtonsWidth; j++)
        hm.removeButton(buttons[i][j]);
    jtd.disposeButtons();
    super.dispose();
  }
  
  public void scaleWidth(int max) {
    jtd.scaleWidth(max);
  }
  
  public void scaleHeight(int max) {
    jtd.scaleHeight(max);
  }
  
  public void scale(int maxw, int maxh) {
    jtd.scale(maxw, maxh);
  }
  
  public void setMouseByTab(boolean f) {
    jtd.setMouseByTab(f);
  }
  
  public String getText() {
    return jtd.getText();
  }
  
  public void setUseHighlightedTabs(boolean f) {
    jtd.setUseHighlightedTabs(f);
  }
  
  public void setHighlightedFlags(boolean[][] flags) {
    jtd.setHighlightedFlags(flags);
  }
  
  public void setHighlightedColor(Color c) {
    jtd.setHighlightedColor(c);
  }
}
