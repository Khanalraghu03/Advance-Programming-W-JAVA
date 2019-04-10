package Jeli.Logging;

public abstract interface StandardDataInfo
{
  public abstract String getSDComment();
  
  public abstract boolean getSDLogFlag();
  
  public abstract void setSDLogFlag(boolean paramBoolean);
  
  public abstract boolean getSDShowFlag();
  
  public abstract void setSDShowFlag(boolean paramBoolean);
  
  public abstract boolean getSDActiveFlag();
  
  public abstract void setSDActiveFlag(boolean paramBoolean);
}
