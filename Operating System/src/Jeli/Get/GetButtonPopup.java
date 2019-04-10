package Jeli.Get;

import Jeli.Widgets.HelpManager;
import Jeli.Widgets.JeliButton;
import java.awt.Component;
import java.awt.Frame;

public class GetButtonPopup implements Jeli.Widgets.JeliButtonEnterCallBack, Runnable, Jeli.Widgets.JeliShowable
{
  private boolean noDec = false;
  private GetButtonPopupDec gbp;
  private GetButtonPopupNoDec gbpn;
  private static int keepFrontDefault = 5;
  private boolean enterFlag = false;
  private Thread hideThread = null;
  private boolean showitLast = false;
  




  public GetButtonPopup(String name, int id, String[] labels, int minwidth, HelpManager hm, boolean tempflag, boolean allow_persistent_flag, boolean force_persistent_flag, ButtonPopupCallBack callback, Component com, int keepFrontSeconds)
  {
    gbp = new GetButtonPopupDec(name, id, labels, minwidth, hm, tempflag, allow_persistent_flag, force_persistent_flag, callback, com, keepFrontSeconds);
  }
  





  public GetButtonPopup(String name, int id, String[] labels, int minwidth, HelpManager hm, boolean tempflag, boolean allow_persistent_flag, boolean force_persistent_flag, ButtonPopupCallBack callback, Component com)
  {
    this(name, id, labels, minwidth, hm, tempflag, allow_persistent_flag, force_persistent_flag, callback, com, keepFrontDefault);
  }
  




  public GetButtonPopup(String name, int id, String[] labels, int minwidth, HelpManager hm, boolean tempflag, boolean persistent_flag, ButtonPopupCallBack callback, Component com)
  {
    this(name, id, labels, minwidth, hm, tempflag, persistent_flag, false, callback, com);
  }
  


  public GetButtonPopup(String name, int id, String[] labels, int minwidth, HelpManager hm, ButtonPopupCallBack callback, Component com)
  {
    this(name, id, labels, minwidth, hm, false, false, callback, com);
  }
  





  public GetButtonPopup(Frame fr, String name, int id, String[] labels, int minwidth, HelpManager hm, boolean tempflag, boolean allow_persistent_flag, boolean force_persistent_flag, ButtonPopupCallBack callback, Component com, int keepFrontSeconds)
  {
    if (GetButtonPopupNoDec.isOK()) {
      gbpn = new GetButtonPopupNoDec(fr, name, id, labels, minwidth, hm, tempflag, allow_persistent_flag, force_persistent_flag, callback, com, keepFrontSeconds);
      



      gbpn.setEnterCallback(this);
      noDec = true;
    }
    else {
      gbp = new GetButtonPopupDec(name, id, labels, minwidth, hm, tempflag, allow_persistent_flag, force_persistent_flag, callback, com, keepFrontSeconds);
    }
  }
  






  public GetButtonPopup(Frame fr, String name, int id, String[] labels, int minwidth, HelpManager hm, boolean tempflag, boolean allow_persistent_flag, boolean force_persistent_flag, ButtonPopupCallBack callback, Component com)
  {
    this(fr, name, id, labels, minwidth, hm, tempflag, allow_persistent_flag, force_persistent_flag, callback, com, keepFrontDefault);
  }
  





  public GetButtonPopup(Frame fr, String name, int id, String[] labels, int minwidth, HelpManager hm, boolean tempflag, boolean persistent_flag, ButtonPopupCallBack callback, Component com)
  {
    this(fr, name, id, labels, minwidth, hm, tempflag, persistent_flag, false, callback, com);
  }
  



  public GetButtonPopup(Frame fr, String name, int id, String[] labels, int minwidth, HelpManager hm, ButtonPopupCallBack callback, Component com)
  {
    this(fr, name, id, labels, minwidth, hm, false, false, callback, com);
  }
  
  public boolean getNoDec() {
    return noDec;
  }
  
  public void setLabel(int ind, String label) {
    if (noDec) {
      gbpn.setLabel(ind, label);
    }
    else {
      gbp.setLabel(ind, label);
    }
  }
  
  public Component getTopComponent() {
    if (noDec) {
      return gbpn.getTopComponent();
    }
    
    return gbp.getTopComponent();
  }
  
  public Jeli.Widgets.JeliLabel getNameLabel()
  {
    if (noDec) {
      return gbpn.getNameLabel();
    }
    return null;
  }
  
  public Component getPopup() {
    if (noDec)
      return gbpn;
    return gbp;
  }
  
  public void hideit() {
    showitLast = false;
    if (noDec) {
      gbpn.setVisible(false);
      Jeli.Utility.setVisibleComponent(gbpn, false);
    }
    else {
      gbp.setVisible(false);
      Jeli.Utility.setVisibleComponent(gbp, false);
    }
  }
  
  public void hideitUnlessActive() {
    showitLast = false;
    hideThread = new Thread(this);
    hideThread.start();
  }
  
  public void run()
  {
    try {
      Thread.sleep(100L);
    }
    catch (InterruptedException e) {}
    


    if ((!enterFlag) && (!showitLast)) {
      hideit();
    }
  }
  
  public void setPositionRight()
  {
    if (noDec) {
      gbpn.setPositionRight();
    }
  }
  
  public void setPositionBottom() {
    if (noDec) {
      gbpn.setPositionBottom();
    }
  }
  
  public void setButtonEnable(int which, boolean enable) {
    if (noDec) {
      gbpn.setButtonEnable(which, enable);
    } else {
      gbp.setButtonEnable(which, enable);
    }
  }
  
  public void jeliButtonEntered(JeliButton jb) {
    enterFlag = true;
  }
  

  public void jeliButtonExited(JeliButton jb) {}
  
  public void showit()
  {
    enterFlag = false;
    showitLast = true;
    



    if (noDec) {
      gbpn.showit();
    }
    else {
      gbp.showit();
    }
  }
  
  public void setChain(GetButtonPopup gpb, int index) {
    if (noDec) {
      gbpn.setChain(gpb, index);
      gpb.setChainedFrom(this);
    }
  }
  
  public void setComponent(Component com) {
    if (noDec) {
      gbpn.setComponent(com);
    }
  }
  
  public void handleCancel() {
    if (noDec) {
      gbpn.handleCancel();
    }
  }
  
  public void setChainedFrom(GetButtonPopup gbp) {
    if (noDec) {
      gbpn.setChainedFrom(gbp);
    }
  }
  
  public void setAsGetInteger(int which, String s, int val, int id, Jeli.Widgets.JeliButtonCallBack cb)
  {
    if (noDec) {
      gbpn.setAsGetInteger(which, s, val, id, cb);
    }
    else {
      gbp.setAsGetInteger(which, s, val, id, cb);
    }
  }
  
  public void setAsGetString(int which, String s, String val, int id, Jeli.Widgets.JeliButtonCallBack cb) {
    if (noDec) {
      gbpn.setAsGetString(which, s, val, id, cb);
    } else
      gbp.setAsGetString(which, s, val, id, cb);
  }
  
  public JeliButton getButton(int n) {
    if (noDec) {
      return gbpn.getButton(n);
    }
    return gbp.getButton(n);
  }
  
  public void resetSize() {
    if (noDec) {
      gbpn.resetSize();
    } else
      gbp.resetSize();
  }
  
  public void setBackground(java.awt.Color c) {
    if (noDec) {
      gbpn.setAllBackground(c);
    } else
      gbp.setAllBackground(c);
  }
  
  public boolean isVisible() {
    if (noDec) {
      return gbpn.isVisible();
    }
    return gbp.isVisible();
  }
  
  public void setDraggable() {
    if (noDec)
      gbpn.setDraggable();
  }
  
  public void replaceComponent(Component com, int i) {
    if (noDec) {
      gbpn.replaceComponent(com, i);
    } else {
      gbp.replaceComponent(com, i);
    }
  }
}
