package Jeli.Get;

import Jeli.Widgets.JeliFrame;
import java.awt.Component;
import java.awt.Point;
import java.awt.TextField;

public class GetString extends JeliFrame
{
  StringCallBack callback;
  TextField StringField;
  String name;
  int id;
  java.awt.Insets insets;
  Component com;
  
  public GetString(String name, int id, StringCallBack callback, Component com)
  {
    super(name);
    this.callback = callback;
    this.id = id;
    this.com = com;
    setup_layout();
  }
  
  public void showGetString()
  {
    if (com != null) {
      insets = getInsets();
      Point pos = Jeli.Utility.getAbsolutePosition(com);
      setLocation(x, y - insets.top);
    }
    super.setVisible(true);
    StringField.requestFocus();
  }
  
  public void setup_layout() {
    setLayout(new java.awt.GridLayout(1, 1));
    StringField = new TextField("", 30);
    add(StringField);
    pack();
  }
  
  public void textFieldEvent(TextField tf, String str) {
    if (tf == StringField) {
      callback.setString(id, str);
      setVisible(false);
    }
  }
}
