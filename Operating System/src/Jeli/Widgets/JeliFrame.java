package Jeli.Widgets;

import Jeli.Utility;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class JeliFrame extends Frame implements JeliButtonCallBack, WindowListener, Jeli.JeliBounds
{
  private boolean inhibitVisibleComponent = false;
  
  public JeliFrame(String label) {
    super(label);
    addWindowListener(this);
  }
  

  public void setAllInvisible() {}
  
  public void jeliButtonPushed(JeliButton bh) {}
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification()
  {
    return "none";
  }
  
  public void setClassification(String str) {}
  
  public void windowOpened(WindowEvent e) {}
  
  public void windowClosed(WindowEvent e) {}
  
  public void windowDeactivated(WindowEvent e) {}
  
  public void windowDeiconified(WindowEvent e) {}
  
  public void windowIconified(WindowEvent e) {}
  
  public void windowActivated(WindowEvent e) {}
  
  public void windowClosing(WindowEvent e) {
    setVisible(false);
  }
  
  public void setVisible(boolean f) {
    if (!inhibitVisibleComponent) {
      Utility.setVisibleComponent(this, f);
    }
    super.setVisible(f);
  }
  
  public void scale(int w, int h) {}
  
  public void setInhibitVisibleComponent(boolean f) {
    inhibitVisibleComponent = f;
  }
}
