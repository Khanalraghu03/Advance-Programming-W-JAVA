package Jeli.Widgets;

public class JeliButtonThread extends Thread
{
  JeliButtonCallBack jbc;
  JeliButton jb;
  
  public JeliButtonThread(JeliButtonCallBack jbc, JeliButton jb) {
    this.jbc = jbc;
    this.jb = jb;
    setName("Jeli Button Thread");
    start();
  }
  
  public void run() {
    jbc.jeliButtonPushed(jb);
  }
}
