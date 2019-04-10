package Jeli.Widgets;

import java.awt.Color;

public abstract interface JeliColorButtonCallBack
{
  public abstract void jeliColorButtonSetColor(JeliColorButton paramJeliColorButton, int paramInt, Color paramColor);
  
  public abstract void jeliColorButtonSettingIndex(JeliColorButton paramJeliColorButton);
  
  public abstract void jeliColorButtonSetIndex(JeliColorButton paramJeliColorButton, int paramInt);
}
