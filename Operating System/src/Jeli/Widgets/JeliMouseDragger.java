package Jeli.Widgets;

public abstract interface JeliMouseDragger
  extends JeliMouseReceiver
{
  public abstract void mouseDragged(int paramInt1, int paramInt2);
  
  public abstract void mouseReleased(int paramInt1, int paramInt2);
}
