package Jeli.Get;

import Jeli.Widgets.HelpManager;
import Jeli.Widgets.JeliButton;
import Jeli.Widgets.JeliButtonCallBack;
import Jeli.Widgets.JeliLabel;
import java.awt.Component;
import java.awt.Frame;

public class GetButtonPopupNoDec extends Jeli.Widgets.JeliDialog implements JeliButtonCallBack, Jeli.Widgets.JeliButtonEnterCallBack, ForceKeepFront
{
  String name;
  String[] labels;
  JeliButton[] buttons;
  JeliButton cancelbutton;
  JeliButton persistentbutton;
  GetButtonPopup[] chains;
  int size;
  int id;
  Component com;
  HelpManager hm;
  ButtonPopupCallBack callback;
  int minwidth;
  private String classification = null;
  private boolean persistent_flag = false;
  private boolean allow_persistent_flag = false;
  private boolean force_persistent_flag = false;
  private boolean first_show_flag = true;
  private int keepFrontSeconds = 0;
  private Jeli.JeliKeepFront jkf = null;
  private static int keepFrontDefault = 5;
  private Jeli.Widgets.JeliButtonEnterCallBack jbecb = null;
  private boolean positionRight = false;
  private boolean positionBottom = false;
  private GetButtonPopup chainedFrom = null;
  private JeliButtonCallBack getCallback = null;
  

  private java.awt.Panel[] buttonPanels;
  
  JeliLabel nameLabel;
  
  Frame f;
  

  protected GetButtonPopupNoDec(Frame fr, String name, int id, String[] labels, int minwidth, HelpManager hm, boolean tempflag, boolean allow_persistent_flag, boolean force_persistent_flag, ButtonPopupCallBack callback, Component com, int keepFrontSeconds)
  {
    super(fr, name);
    f = fr;
    try {
      setUndecorated(true);
    }
    catch (NoSuchMethodError e) {}
    if (tempflag) {
      classification = "Temporary";
    }
    else {
      classification = name;
    }
    this.keepFrontSeconds = keepFrontSeconds;
    this.allow_persistent_flag = allow_persistent_flag;
    this.force_persistent_flag = force_persistent_flag;
    this.name = name;
    this.callback = callback;
    this.id = id;
    this.com = com;
    this.hm = hm;
    this.minwidth = minwidth;
    size = labels.length;
    this.labels = new String[size];
    for (int i = 0; i < size; i++) {
      this.labels[i] = labels[i];
    }
    setup_layout();
    
    if (force_persistent_flag) {
      persistent_flag = true;
      persistentbutton.disableJeliButton();
    }
    pack();
    setResizable(false);
  }
  




  public GetButtonPopupNoDec(Frame fr, String name, int id, String[] labels, int minwidth, HelpManager hm, boolean tempflag, boolean allow_persistent_flag, boolean force_persistent_flag, ButtonPopupCallBack callback, Component com)
  {
    this(fr, name, id, labels, minwidth, hm, tempflag, allow_persistent_flag, force_persistent_flag, callback, com, keepFrontDefault);
  }
  





  public GetButtonPopupNoDec(Frame fr, String name, int id, String[] labels, int minwidth, HelpManager hm, boolean tempflag, boolean persistent_flag, ButtonPopupCallBack callback, Component com)
  {
    this(fr, name, id, labels, minwidth, hm, tempflag, persistent_flag, false, callback, com);
  }
  



  public GetButtonPopupNoDec(Frame fr, String name, int id, String[] labels, int minwidth, HelpManager hm, ButtonPopupCallBack callback, Component com)
  {
    this(fr, name, id, labels, minwidth, hm, false, false, callback, com);
  }
  
  public static void setKeepFrontDefault(int sec) {
    keepFrontDefault = sec;
  }
  
  public static boolean isOK()
  {
    String javaVersion = System.getProperty("java.version");
    if (javaVersion.compareTo("1.4") < 0) {
      return false;
    }
    return true;
  }
  
  public Component getTopComponent() {
    return buttons[0];
  }
  
  public JeliButton getButton(int n) {
    if ((n < 0) || (n > buttons.length))
      return null;
    return buttons[n];
  }
  


  public void showit()
  {
    if ((first_show_flag) && (com != null)) {
      setToPosition();
    }
    resetAllStates();
    setVisible(true);
    
    if (keepFrontSeconds > 0) {
      jkf = new Jeli.JeliKeepFront(this, keepFrontSeconds);
    }
  }
  


  private void resetAllStates()
  {
    cancelbutton.resetState();
    persistentbutton.resetState();
    for (int i = 0; i < buttons.length; i++) {
      buttons[i].resetState();
    }
  }
  


  private void setToPosition()
  {
    java.awt.Point pos = Jeli.Utility.getAbsolutePosition(com);
    int y;
    int x;
    int y; if (positionRight) {
      int x = x + com.getBounds().width;
      y = y;
    } else { int y;
      if (positionBottom) {
        int x = x;
        y = y + com.getBounds().height;
      }
      else {
        x = x - getBoundswidth / 2;
        y = y - getBoundsheight / 2;
      }
    }
    if (x < 0) {
      x = 0;
    }
    if (y < 0) {
      y = 0;
    }
    
    setLocation(x, y);
  }
  

  public void setup_layout()
  {
    String CancelString = "Cancel";
    if (force_persistent_flag) {
      CancelString = "Hide";
    }
    while (CancelString.length() < minwidth) {
      CancelString = CancelString + " ";
    }
    int sz = size + 2;
    if (allow_persistent_flag) {
      sz++;
    }
    setLayout(new Jeli.Widgets.JeliGridLayout(sz, 1));
    buttonPanels = new java.awt.Panel[size];
    buttons = new JeliButton[size];
    chains = new GetButtonPopup[size];
    nameLabel = new JeliLabel(name);
    nameLabel.setEnterCallback(this);
    nameLabel.setPositionCenter();
    nameLabel.setFontType(1);
    add(nameLabel);
    for (int i = 0; i < size; i++) {
      buttonPanels[i] = new java.awt.Panel();
      buttonPanels[i].setLayout(new java.awt.GridLayout(1, 1));
      chains[i] = null;
      buttonPanels[i].add(buttons[i] =  = new JeliButton(labels[i], hm, this));
      add(buttonPanels[i]);
      buttons[i].setInhibitReverse(true);
      buttons[i].setEnterCallback(this);
      buttons[i].setClassification("Temporary");
      buttons[i].setBorderType(12, 15, 15);
    }
    

    persistentbutton = new JeliButton("Make Persistent", hm, this);
    persistentbutton.setClassification("Temporary");
    if (allow_persistent_flag) {
      add(persistentbutton);
    }
    add(this.cancelbutton = new JeliButton(CancelString, hm, this));
    cancelbutton.setInhibitReverse(true);
    cancelbutton.setClassification("Temporary");
    cancelbutton.setBorderType(14, 15, 15);
    

    persistentbutton.setBorderType(12, 15, 15);
  }
  

  protected JeliLabel getNameLabel()
  {
    return nameLabel;
  }
  
  public void forceHide() {
    if (jkf != null) {
      jkf.killMe();
    }
    Jeli.Utility.hide(this);
    dispose();
  }
  

  protected void handleCancel()
  {
    if (!force_persistent_flag) {
      persistent_flag = false;
      persistentbutton.enableJeliButton();
      for (int i = 0; i < size; i++) {
        buttons[i].setInhibitReverse(true);
      }
    }
    if (jkf != null) {
      jkf.killMe();
    }
    Jeli.Utility.hide(this);
    dispose();
    if (chainedFrom != null) {
      chainedFrom.handleCancel();
    }
  }
  
  public void jeliButtonPushed(JeliButton bh)
  {
    if (bh == cancelbutton) {
      int modifier = bh.getMouseModifier();
      if ((modifier & 0x1) != 0) {
        if (hm != null) {
          hm.showHelpManager();
        }
        return;
      }
      handleCancel();
      return;
    }
    if (bh == persistentbutton) {
      persistent_flag = true;
      persistentbutton.disableJeliButton();
      for (int i = 0; i < size; i++) {
        buttons[i].setInhibitReverse(false);
      }
      return;
    }
    for (int i = 0; i < size; i++) {
      if (bh == buttons[i]) {
        if (!persistent_flag) {
          if (jkf != null) {
            jkf.killMe();
          }
          Jeli.Utility.hide(this);
          if (chainedFrom != null) {
            chainedFrom.handleCancel();
          }
          dispose();
        }
        if ((bh.getObjectInfo() instanceof JeliButtonCallBack)) {
          ((JeliButtonCallBack)bh.getObjectInfo()).jeliButtonPushed(bh);
        } else if ((callback instanceof ButtonPopupExtendedCallBack)) {
          ((ButtonPopupExtendedCallBack)callback).buttonPopupFound(name, id, i, labels[i], buttons[i]);
        }
        else
        {
          callback.buttonPopupFound(name, id, i, labels[i]);
        }
      }
    }
  }
  
  public void setAllBackground(java.awt.Color c) {
    persistentbutton.setBackground(c);
    cancelbutton.setBackground(c);
    nameLabel.setBackground(c);
    for (int i = 0; i < buttons.length; i++) {
      buttons[i].setBackground(c);
    }
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {
    if (getCallback != null) {
      getCallback.jeliButtonGotString(bh, str);
    }
    if (!persistent_flag) {
      if (jkf != null) {
        jkf.killMe();
      }
      Jeli.Utility.hide(this);
      if (chainedFrom != null) {
        chainedFrom.handleCancel();
      }
      dispose();
    }
  }
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {
    if (getCallback != null) {
      getCallback.jeliButtonGotInteger(bh, val);
    }
    if (!persistent_flag) {
      if (jkf != null) {
        jkf.killMe();
      }
      Jeli.Utility.hide(this);
      if (chainedFrom != null) {
        chainedFrom.handleCancel();
      }
      dispose();
    }
  }
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() {
    return classification;
  }
  
  public void setClassification(String str) {
    classification = str;
  }
  
  public void setLabel(int ind, String lab) {
    buttons[ind].setLabel(lab);
    labels[ind] = lab;
  }
  
  public void setButtonEnable(int ind, boolean f) {
    if ((ind < 0) || (ind >= buttons.length)) {
      return;
    }
    if (f) {
      buttons[ind].enableJeliButton();
    }
    else {
      buttons[ind].disableJeliButton();
    }
  }
  
  protected void setEnterCallback(Jeli.Widgets.JeliButtonEnterCallBack cb) {
    jbecb = cb;
  }
  
  public void jeliButtonEntered(JeliButton jb) {
    for (int i = 0; i < size; i++) {
      if ((buttons[i] == jb) && (chains[i] != null)) {
        chains[i].showit();
        break;
      }
    }
    if (jbecb != null) {
      jbecb.jeliButtonEntered(jb);
    }
  }
  
  public void jeliButtonExited(JeliButton jb) {
    for (int i = 0; i < size; i++) {
      if ((jb == buttons[i]) && (chains[i] != null)) {
        chains[i].hideitUnlessActive();
        return;
      }
    }
  }
  
  protected void setPositionRight() {
    positionRight = true;
  }
  
  protected void setPositionBottom() {
    positionBottom = true;
  }
  
  protected void setChain(GetButtonPopup gbp, int index) {
    if (index >= size) {
      return;
    }
    chains[index] = gbp;
    gbp.setComponent(buttons[index]);
  }
  
  protected void setComponent(Component com) {
    this.com = com;
  }
  
  protected void setChainedFrom(GetButtonPopup gbp) {
    chainedFrom = gbp;
  }
  
  protected void setAsGetInteger(int which, String s, int value, int id, JeliButtonCallBack cb)
  {
    if ((which < 0) || (which > buttons.length))
      return;
    buttons[which].setAsGetInteger(s, "Enter " + s + ": ", s + ": ", value, true);
    buttons[which].setInfo(id);
    getCallback = cb;
  }
  
  protected void setAsGetString(int which, String s, String value, int id, JeliButtonCallBack cb)
  {
    if ((which < 0) || (which > buttons.length))
      return;
    buttons[which].setAsGetString(s, "Enter " + s + ": ", s + ": ", value, true);
    buttons[which].setInfo(id);
    getCallback = cb;
  }
  
  protected void resetSize() {
    invalidate();
    validate();
    pack();
  }
  
  protected void setDraggable() {
    nameLabel.setDraggable(f, this);
  }
  
  protected void replaceComponent(Component com, int i) {
    if ((i < 0) || (i >= size))
      return;
    buttonPanels[i].removeAll();
    buttonPanels[i].add(com);
    buttonPanels[i].invalidate();
    invalidate();
    pack();
  }
}
