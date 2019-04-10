package Jeli;

public abstract interface Debugger
{
  public abstract void append(String paramString);
  
  public abstract void appendLine(String paramString);
  
  public abstract void clear();
}
