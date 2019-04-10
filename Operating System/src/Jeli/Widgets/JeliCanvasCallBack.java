package Jeli.Widgets;

public abstract interface JeliCanvasCallBack
{
  public abstract void jeliCanvasCallback(String paramString);
  
  public abstract void jeliCanvasCallback(String paramString1, String paramString2);
  
  public abstract void jeliCanvasCallback(String paramString, int paramInt);
  
  public abstract void jeliCanvasCallback(String paramString, int paramInt1, int paramInt2);
}
