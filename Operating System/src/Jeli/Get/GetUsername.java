package Jeli.Get;

import Jeli.Utility;
import Jeli.Widgets.JeliButton;
import java.awt.GridBagLayout;
import java.awt.Panel;

public class GetUsername extends Jeli.Widgets.JeliFrame
{
  String un;
  String newun;
  UsernameCallBack callback;
  java.awt.Component com;
  JeliButton ResetUsernameButton;
  JeliButton UsernameButton;
  JeliButton AbortButton;
  JeliButton DoneButton;
  Jeli.Widgets.HelpManager hm;
  private GridBagLayout gbl;
  private java.awt.GridBagConstraints c;
  private String ResetButtonString = "Reset Image Name xxx";
  private String SetButtonString = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";
  private String classification = null;
  


  public GetUsername(String name, String un, Jeli.Widgets.HelpManager hm, UsernameCallBack callback, java.awt.Component com)
  {
    super(name);
    this.un = un;
    this.callback = callback;
    this.com = com;
    this.hm = hm;
    newun = un;
    setup_layout();
    pack();
    
    showGetUsername();
  }
  
  public void showGetUsername()
  {
    if (com != null) {
      java.awt.Point pos = Utility.getAbsolutePosition(com);
      setLocation(x, y);
    }
    super.setVisible(true);
  }
  

  public void setup_layout()
  {
    Panel p = new Panel();
    Panel q = new Panel();
    gbl = new GridBagLayout();
    c = new java.awt.GridBagConstraints();
    p.setLayout(gbl);
    q.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
    setLayout(new java.awt.BorderLayout());
    c.fill = 1;
    c.weighty = 1.0D;
    c.gridx = 0;
    c.gridy = 0;
    
    c.weightx = 0.0D;
    ResetUsernameButton = new JeliButton("Reset Username", hm, this);
    ResetUsernameButton.setButtonSize(ResetButtonString);
    gbl.setConstraints(ResetUsernameButton, c);
    p.add(ResetUsernameButton);
    c.weightx = 1.0D;
    c.gridx = 1;
    UsernameButton = new JeliButton("", hm, this);
    UsernameButton.setAsGetString("Username", "Enter Username: ", "Username: ", un, true);
    
    UsernameButton.setButtonSize(SetButtonString);
    gbl.setConstraints(UsernameButton, c);
    p.add(UsernameButton);
    c.gridx = 0;
    c.gridy = 1;
    c.weightx = 0.0D;
    AbortButton = new JeliButton("Abort", hm, this);
    AbortButton.setButtonSize("Abort");
    AbortButton.setBackground(Utility.jeliLightRed);
    q.add(AbortButton);
    DoneButton = new JeliButton("Done", hm, this);
    DoneButton.setButtonSize("Abort");
    DoneButton.setBackground(Utility.jeliLightGreen);
    q.add(DoneButton);
    add("Center", p);
    add("South", q);
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == ResetUsernameButton) {
      newun = un;
      UsernameButton.resetGet();
      UsernameButton.setAsGetString("Username", "Enter Username: ", "Username: ", un, true);
    }
    
    if (bh == AbortButton) {
      setVisible(false);
      return;
    }
    if (bh == DoneButton) {
      callback.setUsername(newun);
      setVisible(false);
    }
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {
    if (bh == UsernameButton)
      newun = str;
  }
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() { return classification; }
  
  public void setClassification(String str)
  {
    classification = str;
  }
}
