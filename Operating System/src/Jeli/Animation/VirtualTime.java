package Jeli.Animation;

public class VirtualTime {
  private long real_time_saved;
  private long virtual_time_saved;
  private double time_rate;
  private double saved_rate;
  
  public VirtualTime() {
    real_time_saved = System.currentTimeMillis();
    virtual_time_saved = 0L;
    time_rate = 1.0D;
    saved_rate = 1.0D;
  }
  
  public long getRealTime() {
    return System.currentTimeMillis();
  }
  
  public long getVirtualTime()
  {
    long real_time_now = System.currentTimeMillis();
    return getVirtualTime(real_time_now);
  }
  
  public long getVirtualTime(long timenow)
  {
    long virtual_time_now = virtual_time_saved + ((timenow - real_time_saved) * time_rate);
    
    return virtual_time_now;
  }
  
  public void clearVirtualTime() {
    real_time_saved = System.currentTimeMillis();
    virtual_time_saved = 0L;
  }
  
  public void setVirtualTime(long tm) {
    real_time_saved = System.currentTimeMillis();
    virtual_time_saved = tm;
  }
  
  public void setRate(double r)
  {
    if (time_rate != 0.0D) {
      reset_saved();
      time_rate = r;
      return;
    }
    saved_rate = r;
  }
  
  public double getRate() {
    return time_rate;
  }
  
  public void stopVirtualTime() {
    if (time_rate == 0.0D) return;
    saved_rate = time_rate;
    reset_saved();
    time_rate = 0.0D;
  }
  
  public void restartVirtualTime()
  {
    if (time_rate != 0.0D) return;
    reset_saved();
    time_rate = saved_rate;
  }
  
  private void reset_saved()
  {
    long timenow = System.currentTimeMillis();
    virtual_time_saved = getVirtualTime(timenow);
    real_time_saved = timenow;
  }
  
  public void trysleep(long duration)
  {
    long endTime = getVirtualTime() + duration;
    while (getVirtualTime() < endTime) {
      try {
        Thread.sleep(100L);
      }
      catch (InterruptedException e) {}
    }
  }
}
