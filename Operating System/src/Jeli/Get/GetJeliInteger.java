package Jeli.Get;

import Jeli.Widgets.JeliButton;
import Jeli.Widgets.JeliFrame;
import java.awt.Component;
import java.awt.Point;

public class GetJeliInteger extends JeliFrame
{
  IntegerCallBack callback;
  JeliButton stringButton;
  String name;
  int id;
  java.awt.Insets insets;
  Component com;
  
  public GetJeliInteger(String name, int id, IntegerCallBack callback, Component com)
  {
    super(name);
    this.callback = callback;
    this.id = id;
    this.com = com;
    setup_layout();
  }
  
  public void showGetJeliInteger()
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
  
  public void jeliButtonGotString(JeliButton bh, String str)
  {
    int val;
    try {
      val = Integer.parseInt(str);
    }
    catch (NumberFormatException e1) {
      setVisible(false);
      return;
    }
    callback.setInteger(id, val);
    setVisible(false);
  }
}
