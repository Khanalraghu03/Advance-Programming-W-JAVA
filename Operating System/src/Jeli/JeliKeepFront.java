package Jeli;

import java.awt.Window;

public class JeliKeepFront extends Thread implements JeliKillable
{
  private Window win;
  private int seconds;
  private boolean doneflag = false;
  
  public JeliKeepFront(Window win, int seconds) {
    this.win = win;
    this.seconds = seconds;
    setName("Jeli Keep Front");
    start();
  }
  
  public void run() {
    while (seconds > 0) {
      try {
        win.toFront();
      } catch (NullPointerException e) {
        return;
      }
      seconds -= 1;
      try {
        sleep(1000L);
      } catch (InterruptedException e) {}
      if (doneflag) {
        try {
          win.setVisible(false);
        } catch (NullPointerException e) {}
        return;
      }
    }
  }
  
  public void killMe() {
    doneflag = true;
  }
}
