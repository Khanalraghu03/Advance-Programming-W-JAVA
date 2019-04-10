package Jeli.Logging;

import Jeli.Widgets.JeliButton;
import java.awt.Color;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;

public class LogComment extends Jeli.Widgets.JeliFrame implements LogState
{
  String str1;
  String str2;
  int rows_old;
  int rows_current;
  int columns;
  Color color1;
  Color color2;
  Logger logger;
  TextArea OldLogs;
  TextArea NewLogs;
  Label OldLogLabel;
  Label NewLogLabel;
  Jeli.Widgets.HelpManager hm;
  private String classification = "Log Comment";
  



  public LogComment(String str, String str1, String str2, int rows_old, int rows_current, int columns, Color color1, Color color2, Jeli.Widgets.HelpManager hm, Logger logger)
  {
    super(str);
    this.str1 = str1;
    this.str2 = str2;
    this.rows_old = rows_old;
    this.rows_current = rows_current;
    this.columns = columns;
    this.color1 = color1;
    this.color2 = color2;
    this.logger = logger;
    this.hm = hm;
    setForeground(Color.black);
    setBackground(Color.white);
    setup_layout();
    pack();
  }
  




  public void setup_layout()
  {
    setLayout(new java.awt.BorderLayout());
    Panel buts = new Panel();
    buts.setLayout(new Jeli.Widgets.JeliGridLayout(1, 3));
    JeliButton b; buts.add(b = new JeliButton("Erase", hm, this));
    b.setPositionCenter();
    b.setBackground(Jeli.Utility.jeliVeryLightRed);
    buts.add(b = new JeliButton("Log", hm, this));
    b.setPositionCenter();
    b.setBackground(Jeli.Utility.jeliVeryLightBlue);
    buts.add(b = new JeliButton("Hide", hm, this));
    b.setPositionCenter();
    b.setBackground(Jeli.Utility.jeliVeryLightGreen);
    Panel p = new Panel();
    p.setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
    Panel q1 = new Panel();
    Panel q2 = new Panel();
    q1.setLayout(new java.awt.BorderLayout());
    q2.setLayout(new java.awt.BorderLayout());
    q1.setBackground(color1);
    q2.setBackground(color2);
    OldLogs = new TextArea(rows_old, columns);
    OldLogs.setEditable(false);
    OldLogs.setForeground(Color.black);
    NewLogs = new TextArea(rows_current, columns);
    NewLogs.setForeground(Color.black);
    q1.add("North", this.OldLogLabel = new Label(str1));
    q1.add("Center", OldLogs);
    q2.add("North", this.NewLogLabel = new Label(str2));
    q2.add("Center", NewLogs);
    p.add(q1);
    p.add(q2);
    OldLogLabel.setBackground(color1);
    OldLogs.setBackground(color1);
    NewLogLabel.setBackground(color2);
    NewLogs.setBackground(color2);
    add("South", buts);
    add("Center", p);
  }
  

  public void jeliButtonPushed(JeliButton b)
  {
    String lab = b.getLabel();
    if ("Erase".equals(lab))
    {
      NewLogs.setText("");
    }
    else if ("Log".equals(lab))
    {
      String str = NewLogs.getText();
      OldLogs.append(str);
      logger.logHrule();
      logger.logString(str);
      logger.logHrule();
      NewLogs.setText("");
    }
    else if ("Hide".equals(lab))
    {
      setVisible(false);
    }
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() {
    return classification;
  }
  
  public void setClassification(String str) {
    classification = str;
  }
  
  public void logStateChange(boolean on) {
    if (!on) {
      setVisible(false);
    }
  }
}
