package Jeli.Get;

import Jeli.Widgets.JeliButton;
import Jeli.Widgets.JeliFrame;
import java.awt.Component;
import java.awt.Point;

public class GetJeliString extends JeliFrame
{
  StringCallBack callback;
  JeliButton stringButton;
  String name;
  int id;
  java.awt.Insets insets;
  Component com;
  
  public GetJeliString(String name, int id, StringCallBack callback, Component com)
  {
    super(name);
    this.callback = callback;
    this.id = id;
    this.com = com;
    setup_layout();
  }
  
  public void showGetJeliString()
  {
    if (com != null) {
      insets = getInsets();
      Point pos = Jeli.Utility.getAbsolutePosition(com);
      setLocation(x, y - insets.top);
    }
    super.setVisible(true);
    stringButton.setGetString("");
  }
  
  public void setup_layout() {
    setLayout(new java.awt.GridLayout(1, 1));
    stringButton = new JeliButton("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", this);
    stringButton.setAsGetString("", "", "", "", false);
    stringButton.increaseFontSize();
    stringButton.increaseFontSize();
    stringButton.increaseFontSize();
    stringButton.increaseFontSize();
    stringButton.setButtonSize();
    add(stringButton);
    pack();
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {
    callback.setString(id, str);
    setVisible(false);
  }
}
