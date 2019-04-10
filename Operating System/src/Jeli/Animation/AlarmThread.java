package Jeli.Animation;

public class AlarmThread extends Thread
{
  private int id;
  private int count;
  private long ms;
  private AlarmCallBack acb;
  
  public AlarmThread(int id, long ms, AlarmCallBack acb) {
    this(id, ms, 1, acb);
  }
  
  public AlarmThread(int id, long ms, int count, AlarmCallBack acb) {
    this.id = id;
    this.ms = ms;
    this.count = count;
    this.acb = acb;
    setName("Jeli Alarm Thread");
    start();
  }
  
  public void run() {
    for (int i = 0; i < count; i++) {
      try {
        sleep(ms);
      } catch (InterruptedException e) {
        return;
      }
      if (acb != null)
        acb.alarmNotify(id, count - i - 1);
    }
  }
  
  public void cancel() {
    acb = null;
  }
}
