package Jeli.Widgets;


public class JeliButtonDummy
  implements JeliButtonSimulator
{
  private JeliButtonSimulatorCallBack cb;
  private boolean linkedFlag = false;
  
  public JeliButtonDummy(JeliButtonSimulatorCallBack cb) {
    this.cb = cb;
  }
  
  public void simulatePushed() {
    if (cb != null)
      cb.jeliButtonSimulatePushed(this);
  }
  
  public void setLinked(boolean f) {
    linkedFlag = f;
  }
}
