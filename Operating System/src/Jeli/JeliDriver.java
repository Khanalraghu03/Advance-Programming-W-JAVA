package Jeli;

import Jeli.Widgets.JeliButton;
import Jeli.Widgets.JeliFrame;
import java.awt.Color;

public abstract class JeliDriver extends java.applet.Applet implements Jeli.Widgets.JeliButtonCallBack, Runnable
{
  public JeliDriver() {}
  
  private JeliFrame initialFrame = null;
  
  public void initMost(JeliApplicationVersion version) {
    setBackground(Color.white);
    Utility.jeliAppletParameters(this);
    Utility.standardHelpManager(version.getName());
    Utility.setApplicationName(" " + version.getName() + " ");
    Utility.setApplicationVersion(version.getVersionMessageShort());
    Utility.setApplicationUpdate(version.getVersionMessageUpdate());
    Utility.setVersionMessage(version);
    add(Utility.makeAppletPanel(this, version));
    UtilityIO.setApplet(this);
    
    if ((UtilityIO.getRemoteHost() != null) && (UtilityIO.getRemoteHost().equals(Utility.getDefaultRemoteHost())))
    {

      Utility.setRemotePort(Utility.getDefaultRemotePort());
    }
  }
  
  public void setInitialFrame(JeliFrame f)
  {
    initialFrame = f;
  }
  
  public void stop() {
    if (initialFrame != null) {
      initialFrame.setAllInvisible();
      initialFrame.setVisible(false);
      initialFrame = null;
      Utility.setStartButtonNameStart();
    }
  }
  



  public abstract void run();
  



  public void jeliButtonPushed(JeliButton bh)
  {
    if (initialFrame != null) {
      initialFrame.setVisible(true);
      return;
    }
    Utility.setStartButtonNameShow();
    new Thread(this).start();
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() {
    return "Startup applet for" + Utility.getApplicationName();
  }
  
  public void setClassification(String str) {}
}
