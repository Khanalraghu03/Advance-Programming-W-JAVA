package Jeli.Get;

import Jeli.Widgets.JeliFrame;
import java.awt.Component;
import java.awt.Point;
import java.awt.TextField;

public class GetInteger extends JeliFrame
{
  IntegerCallBack callback;
  TextField StringField;
  String name;
  int id;
  Component com;
  
  public GetInteger(String name, int id, IntegerCallBack callback, Component com)
  {
    super(name);
    this.callback = callback;
    this.id = id;
    this.com = com;
    setup_layout();
  }
  

  public void showGetInteger()
  {
    if (com != null) {
      java.awt.Insets insets = getInsets();
      Point pos = Jeli.Utility.getAbsolutePosition(com);
      setLocation(x, y - top);
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
  

  public void textFieldEvent(TextField tf, String str)
  {
    if (tf == StringField) {
      int val;
      try { val = Integer.parseInt(str);
      }
      catch (NumberFormatException e1) {
        setVisible(false);
        return;
      }
      callback.setInteger(id, val);
      setVisible(false);
    }
  }
}
