package Jeli.Widgets;

import Jeli.Utility;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Panel;
import java.awt.TextArea;

public class TextDisplay
  extends Panel implements JeliButtonCallBack
{
  int id;
  int rows;
  int columns;
  String label;
  Color background;
  Color foreground;
  Font font;
  HelpManager hm;
  TextDisplayCallBack callback;
  public JeliButton LabelButton;
  JeliButton ClearButton;
  public JeliButton LogButton;
  JeliButton FontUpButton;
  JeliButton FontDownButton;
  TextArea text_area;
  private String classification;
  double FontSize;
  
  public TextDisplay(int id, int rows, int columns, String label, Font font, Color background, Color foreground, HelpManager hm, TextDisplayCallBack callback)
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
    classification = label;
    FontSize = font.getSize();
    setup_layout();
  }
  



  private void setup_layout()
  {
    setLayout(new BorderLayout());
    Panel PanelNorth = new Panel();
    PanelNorth.setLayout(new BorderLayout());
    text_area = new TextArea(rows, columns);
    text_area.setBackground(background);
    text_area.setForeground(foreground);
    text_area.setFont(font);
    text_area.setEditable(false);
    
    add("North", PanelNorth);
    add("Center", text_area);
    

    Panel PanelButtons = new Panel();
    PanelButtons.setLayout(new BorderLayout());
    
    PanelNorth.add("Center", PanelButtons);
    PanelNorth.add("West", this.FontDownButton = new JeliButton("<", hm));
    PanelNorth.add("East", this.FontUpButton = new JeliButton(">", hm));
    
    LabelButton = new JeliButton(label, hm);
    PanelButtons.add("Center", LabelButton);
    PanelButtons.add("West", this.ClearButton = new JeliButton("Clr", hm));
    PanelButtons.add("East", this.LogButton = new JeliButton("Log", hm));
    LabelButton.resetJeliButtonCallBack(this);
    FontDownButton.resetJeliButtonCallBack(this);
    FontUpButton.resetJeliButtonCallBack(this);
    ClearButton.resetJeliButtonCallBack(this);
    LogButton.resetJeliButtonCallBack(this);
    FontDownButton.setBackground(background);
    FontUpButton.setBackground(background);
    ClearButton.setBackground(background);
    LabelButton.setBackground(background);
    LogButton.setBackground(background);
    FontDownButton.setForeground(foreground);
    FontUpButton.setForeground(foreground);
    ClearButton.setForeground(foreground);
    LabelButton.setForeground(foreground);
    LogButton.setForeground(foreground);
    set_change_font_help();
  }
  
  private void set_change_font_help()
  {
    FontDownButton.setHelpManager(hm);
    FontDownButton.setMessage("Reduce the size of the font in the window\n   Current size: " + Utility.n_places(FontSize, 2) + "\n" + "   Next size:    " + Utility.n_places(FontSize / 1.2D, 2) + "\n");
    


    FontUpButton.setHelpManager(hm);
    FontUpButton.setMessage("Increase the size of the font in the window\n   Current size: " + Utility.n_places(FontSize, 2) + "\n" + "   Next size:    " + Utility.n_places(1.2D * FontSize, 2) + "\n");
  }
  


  public void setBackground(Color c)
  {
    setBackground(c);
    text_area.setBackground(c);
    LabelButton.setBackground(c);
    ClearButton.setBackground(c);
    LogButton.setBackground(c);
    FontUpButton.setBackground(c);
    FontDownButton.setBackground(c);
  }
  
  public void append(String str) {
    if (str != null)
      text_area.append(str);
  }
  
  public void appendS(String str) {
    if (str != null) {
      text_area.append(str);
      text_area.setCaretPosition(text_area.getText().length());
    }
  }
  
  public void set(String str) {
    if (str != null) {
      text_area.setText(str);
    } else {
      text_area.setText("");
    }
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    String lab = bh.getLabel();
    if (bh == LabelButton)
    {
      callback.textDisplayLabelCallBack(id);
      return;
    }
    if (bh == ClearButton)
    {
      text_area.setText("");
      return;
    }
    if (bh == LogButton)
    {
      callback.textDisplayLogCallBack(id, text_area.getText());
      return;
    }
    if (bh == FontUpButton)
    {
      FontSize = (1.2D * FontSize);
      text_area.setFont(new Font("Courier", 0, (int)FontSize));
      
      set_change_font_help();
      return;
    }
    if (bh == FontDownButton)
    {
      FontSize /= 1.2D;
      text_area.setFont(new Font("Courier", 0, (int)FontSize));
      
      set_change_font_help();
      return;
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
}
