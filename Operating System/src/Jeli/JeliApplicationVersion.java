package Jeli;

public abstract interface JeliApplicationVersion
{
  public abstract String getName();
  
  public abstract String getVersionMessageShort();
  
  public abstract String getVersionMessageUpdate();
  
  public abstract int getMinimalJeliVersion();
}
