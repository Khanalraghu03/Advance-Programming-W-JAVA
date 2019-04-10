package Jeli.Widgets;

import java.awt.Point;

public class HelpNoteThread extends Thread
{
  private long waitTime = 1000L;
  private long keepTime = 5000L;
  private Point currentP;
  private Point previousP;
  private Point displayP = null;
  private boolean running = true;
  private JeliCanvas jc;
  private java.awt.Cursor cur;
  private java.awt.Cursor newCur;
  private boolean cursorChanged = false;
  private boolean leaveFlag = false;
  
  public HelpNoteThread(JeliCanvas jc) {
    this.jc = jc;
    
    setName("Jeli Help Note Thread");
    start();
  }
  
  public synchronized void run() {
    newCur = new java.awt.Cursor(1);
    currentP = new Point(0, 0);
    previousP = new Point(-1, -1);
    try
    {
      wait();
    }
    catch (InterruptedException e) {}
    while (running) {
      try {
        if (leaveFlag) {
          leaveFlag = false;
          wait();
        }
        
        previousP.x = currentP.x;
        previousP.y = currentP.y;
        wait(waitTime);
        
        if ((currentP.x == previousP.x) && (currentP.y == previousP.y))
        {
          displayP = currentP;
          cur = jc.getCursor();
          

          if (jc != null) jc.repaint();
          wait(keepTime);
          
          cursorChanged = false;
          
          displayP = null;
          if (jc != null) jc.repaint();
          if ((currentP.x == previousP.x) && (currentP.y == previousP.y))
          {
            wait();
          }
        }
      }
      catch (InterruptedException e) {}
      



      cursorChanged = false;
    }
    

    cursorChanged = false;
    displayP = null;
    if (jc != null) jc.repaint();
  }
  
  public synchronized void setPoint(Point p) {
    displayP = null;
    if (jc != null) jc.repaint();
    currentP.x = x;
    currentP.y = y;
    notify();
  }
  
  public synchronized void leave() {
    displayP = null;
    if (jc != null) jc.repaint();
    currentP.x = 0;
    currentP.y = 0;
    previousP.x = -1;
    previousP.y = -1;
    leaveFlag = true;
    notify();
  }
  
  public synchronized void stopMe()
  {
    displayP = null;
    if (jc != null) jc.repaint();
    currentP.x = (previousP.x + 1);
    running = false;
    notify();
  }
  
  public synchronized Point getDisplayPoint()
  {
    return displayP;
  }
  
  public void setKeepTime(long n) {
    keepTime = n;
  }
  
  public void setWaitTime(long n) {
    waitTime = n;
  }
  
  protected boolean getRunning() {
    return running;
  }
}
