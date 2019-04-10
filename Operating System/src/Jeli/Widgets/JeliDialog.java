package Jeli.Widgets;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class JeliDialog extends Dialog implements JeliButtonCallBack, WindowListener, Jeli.JeliBounds
{
  private boolean inhibitVisibleComponent = false;
  
  public JeliDialog(Frame fr, String label) {
    super(fr, label);
    addWindowListener(this);
  }
  
  public void jeliButtonPushed(JeliButton bh) {}
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() {
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
      Jeli.Utility.setVisibleComponent(this, f);
    }
    super.setVisible(f);
  }
  
  public void scale(int w, int h) {}
  
  public void setInhibitVisibleComponent(boolean f) {
    inhibitVisibleComponent = f;
  }
}
