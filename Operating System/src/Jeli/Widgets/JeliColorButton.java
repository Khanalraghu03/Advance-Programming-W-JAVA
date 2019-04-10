package Jeli.Widgets;

import java.awt.Color;

public class JeliColorButton extends JeliButton implements JeliButtonCallBack
{
  JeliColorButtonCallBack jcbc;
  String classification = "Jeli Color Button";
  Color[] color_list = { Color.black, Color.blue, Color.cyan, Color.darkGray, Color.gray, Color.green, Color.lightGray, Color.magenta, Color.orange, Color.pink, Color.red, Color.white, Color.yellow };
  
  Color[] gray_color_list;
  
  String original_label;
  
  int lowind;
  int highind;
  int change_key_index = 0;
  boolean color_index_flag = false;
  boolean gray_color_flag = false;
  

  public JeliColorButton(String label, int lowind, int highind, Color[] color_list, HelpManager hm, JeliColorButtonCallBack jcbc)
  {
    super(label, hm, null);
    bhc = this;
    this.jcbc = jcbc;
    original_label = label;
    if (color_list != null) {
      this.color_list = new Color[color_list.length];
      for (int i = 0; i < color_list.length; i++)
        this.color_list[i] = color_list[i];
    }
    this.lowind = lowind;
    this.highind = highind;
    gray_color_list = new Color[17];
    for (int i = 0; i < 16; i++)
      gray_color_list[i] = new Color(16 * i, 16 * i, 16 * i);
    gray_color_list[16] = Color.white;
  }
  
  public void setIndexRange(int low, int high) {
    lowind = low;
    highind = high;
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    change_key_index = 1;
    if (highind > lowind) {
      color_index_flag = true;
      setGetInteger("Index: ", 1);
      setGetIntegerRange(lowind, highind);
      jcbc.jeliColorButtonSettingIndex(this);
    }
    else {
      color_index_flag = false;
      jeliButtonGotInteger(this, lowind);
    }
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {
    if (color_index_flag) {
      if ((val < lowind) || (val > highind)) {
        resetGet();
        setLabel(original_label);
        change_key_index = 0;
      }
      else {
        change_key_index = val;
        color_index_flag = false;
        resetGet();
        setLabel(original_label);
        gray_color_flag = false;
        jcbc.jeliColorButtonSetIndex(this, change_key_index);
        setGetColors(color_list);
      }
    }
    else {
      if ((val == 0) && (!gray_color_flag)) {
        resetGet();
        gray_color_flag = true;
        setGetColors(gray_color_list);
        return;
      }
      setGetColors(null);
      if (gray_color_flag) {
        jcbc.jeliColorButtonSetColor(this, change_key_index, gray_color_list[val]);
      }
      else
        jcbc.jeliColorButtonSetColor(this, change_key_index, color_list[val]);
      change_key_index = 0;
    }
  }
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification()
  {
    return classification;
  }
  
  public void setClassification(String str) {}
  
  public void jeliColorButtonReturned(JeliColorButton cb, Color c) {}
  
  public void jeliColorButtonIndex(JeliColorButton cb, int ind) {}
}
