package Jeli.Widgets;

import java.awt.Color;

public abstract interface JeliTextList
{
  public abstract void append(String paramString);
  
  public abstract void appendLine();
  
  public abstract void appendLineIfNecessary();
  
  public abstract void setForegroundColor(Color paramColor);
  
  public abstract Color getForegroundColor();
  
  public abstract void setLevel(int paramInt);
  
  public abstract void incrementLevel(int paramInt);
}
