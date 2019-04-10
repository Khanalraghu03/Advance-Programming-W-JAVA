package Jeli.Logging;

import java.awt.Image;

public abstract interface Logger
{
  public abstract void logStringSimple(String paramString);
  
  public abstract void logString(String paramString);
  
  public abstract void logString(String paramString, int paramInt);
  
  public abstract void logStringToHtml(String paramString);
  
  public abstract void logImage(Image paramImage, String paramString, SaveImageInfo paramSaveImageInfo);
  
  public abstract void logImage(Image paramImage, String paramString1, String paramString2, SaveImageInfo paramSaveImageInfo);
  
  public abstract void logTime();
  
  public abstract void logHrule();
  
  public abstract void logHrule(int paramInt);
  
  public abstract void logLink(String paramString);
  
  public abstract String setLink();
  
  public abstract int debugLevel();
}
