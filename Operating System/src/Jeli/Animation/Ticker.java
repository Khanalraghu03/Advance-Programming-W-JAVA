package Jeli.Animation;

public class Ticker extends Thread
{
  private JeliNotify callback;
  
  public Ticker(JeliNotify callback)
  {
    this.callback = callback;
    start();
  }
  
  public void run() {
    for (;;) {
      try {
        sleep(1000L);
      }
      catch (InterruptedException e) {}
      if (callback != null) {
        callback.notifyMe(this);
      }
    }
  }
}
