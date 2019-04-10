package Jeli.Get;

import Jeli.Widgets.JeliFrame;
import java.awt.Component;
import java.awt.Point;
import java.awt.TextField;

public class GetDouble extends JeliFrame
{
  DoubleCallBack callback;
  TextField StringField;
  String name;
  int id;
  Component com;
  
  public GetDouble(String name, int id, DoubleCallBack callback, Component com)
  {
    super(name);
    this.callback = callback;
    this.id = id;
    this.com = com;
    setup_layout();
  }
  

  public void showGetDouble()
  {
    if (com != null) {
      Point pos = Jeli.Utility.getAbsolutePosition(com);
      java.awt.Insets insets = getInsets();
      setLocation(x, y - top);
    }
    super.setVisible(true);
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
      double val;
      try { val = Double.valueOf(str).doubleValue();
      }
      catch (NumberFormatException e1) {
        setVisible(false);
        return;
      }
      callback.setDouble(id, val);
      setVisible(false);
    }
  }
}
