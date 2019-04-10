package Jeli.Animation;

public class AnimationThread extends Thread
{
  private AnimationDisplayer displayer;
  private long sleepTime;
  private boolean active = false;
  
  public AnimationThread(long sleepTime, AnimationDisplayer displayer) {
    this.sleepTime = sleepTime;
    this.displayer = displayer;
    setName("Jeli Animation Thread");
    start();
  }
  
  public void run() {
    for (;;) {
      if (displayer == null)
        return;
      if (!active)
        trywait();
      trysleep(sleepTime);
      displayer.handleDisplay();
    }
  }
  
  private void trysleep(long tm) {
    try {
      sleep(tm);
    } catch (InterruptedException e) {}
  }
  
  public synchronized void setSleepTime(long st) {
    sleepTime = st;
  }
  
  public synchronized void setActive(boolean f) {
    active = f;
    if (f) notify();
  }
  
  private synchronized void trywait()
  {
    try {
      wait();
    }
    catch (InterruptedException e) {}
  }
}
