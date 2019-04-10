package Jeli.Get;

import Jeli.JeliKeepFront;
import Jeli.Widgets.HelpManager;
import Jeli.Widgets.JeliButton;
import Jeli.Widgets.JeliButtonCallBack;
import java.awt.Component;
import java.awt.Panel;

public class GetButtonPopupDec extends Jeli.Widgets.JeliFrame implements JeliButtonCallBack, ForceKeepFront
{
  String name;
  String[] labels;
  JeliButton[] buttons;
  JeliButton cancelbutton;
  JeliButton persistentbutton;
  Jeli.Widgets.JeliLabel nameLabel;
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
  private JeliKeepFront jkf = null;
  private static int keepFrontDefault = 5;
  private JeliButtonCallBack getCallback = null;
  

  private Panel[] buttonPanels;
  


  public GetButtonPopupDec(String name, int id, String[] labels, int minwidth, HelpManager hm, boolean tempflag, boolean allow_persistent_flag, boolean force_persistent_flag, ButtonPopupCallBack callback, Component com, int keepFrontSeconds)
  {
    super(name);
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
  



  public GetButtonPopupDec(String name, int id, String[] labels, int minwidth, HelpManager hm, boolean tempflag, boolean allow_persistent_flag, boolean force_persistent_flag, ButtonPopupCallBack callback, Component com)
  {
    this(name, id, labels, minwidth, hm, tempflag, allow_persistent_flag, force_persistent_flag, callback, com, keepFrontDefault);
  }
  




  public GetButtonPopupDec(String name, int id, String[] labels, int minwidth, HelpManager hm, boolean tempflag, boolean persistent_flag, ButtonPopupCallBack callback, Component com)
  {
    this(name, id, labels, minwidth, hm, tempflag, persistent_flag, false, callback, com);
  }
  


  public GetButtonPopupDec(String name, int id, String[] labels, int minwidth, HelpManager hm, ButtonPopupCallBack callback, Component com)
  {
    this(name, id, labels, minwidth, hm, false, false, callback, com);
  }
  
  public static void setKeepFrontDefault(int sec) {
    keepFrontDefault = sec;
  }
  
  public Component getTopComponent() {
    return buttons[0];
  }
  
  public JeliButton getButton(int n) {
    if ((n > buttons.length) || (n < 0))
      return null;
    return buttons[n];
  }
  


  public void showit()
  {
    if ((first_show_flag) && (com != null)) {
      java.awt.Point pos = Jeli.Utility.getAbsolutePosition(com);
      int x = x - getBoundswidth / 2;
      int y = y - getBoundsheight / 2;
      if (x < 0) {
        x = 0;
      }
      if (y < 0) {
        y = 0;
      }
      setLocation(x, y);
    }
    setVisible(true);
    
    if (keepFrontSeconds > 0) {
      jkf = new JeliKeepFront(this, keepFrontSeconds);
    }
  }
  


  public void setup_layout1()
  {
    setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
    add(new Jeli.Widgets.JeliLabel("abc"));
    add(new Jeli.Widgets.JeliLabel("xxxxxxxxxxxxxxxxxxxxxxxxxxxxx"));
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
    int sz = size + 1;
    if (allow_persistent_flag) {
      sz++;
    }
    setLayout(new Jeli.Widgets.JeliGridLayout(sz, 1));
    buttonPanels = new Panel[size];
    buttons = new JeliButton[size];
    for (int i = 0; i < size; i++) {
      buttonPanels[i] = new Panel();
      buttonPanels[i].setLayout(new java.awt.GridLayout(1, 1));
      buttonPanels[i].add(buttons[i] =  = new JeliButton(labels[i], hm, this));
      add(buttonPanels[i]);
      buttons[i].setClassification("Temporary");
      buttons[i].setBorderType(0, 15, 15);
    }
    

    persistentbutton = new JeliButton("Make Persistent", hm, this);
    persistentbutton.setClassification("Temporary");
    if (allow_persistent_flag) {
      add(persistentbutton);
    }
    add(this.cancelbutton = new JeliButton(CancelString, hm, this));
    cancelbutton.setClassification("Temporary");
    cancelbutton.setBorderType(0, 15, 15);
    

    persistentbutton.setBorderType(0, 15, 15);
  }
  

  public void setAllBackground(java.awt.Color c)
  {
    persistentbutton.setBackground(c);
    cancelbutton.setBackground(c);
    
    for (int i = 0; i < buttons.length; i++)
      buttons[i].setBackground(c);
  }
  
  public void forceHide() {
    if (jkf != null) {
      jkf.killMe();
    }
    Jeli.Utility.hide(this);
    dispose();
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
      
      if (!force_persistent_flag) {
        persistent_flag = false;
        persistentbutton.enableJeliButton();
      }
      if (jkf != null) {
        jkf.killMe();
      }
      Jeli.Utility.hide(this);
      dispose();
      return;
    }
    if (bh == persistentbutton) {
      persistent_flag = true;
      persistentbutton.disableJeliButton();
      return;
    }
    for (int i = 0; i < size; i++) {
      if (bh == buttons[i]) {
        if (!persistent_flag) {
          if (jkf != null) {
            jkf.killMe();
          }
          Jeli.Utility.hide(this);
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
  

  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public void jeliButtonGotString(JeliButton bh, String str)
  {
    if (getCallback != null) {
      getCallback.jeliButtonGotString(bh, str);
    }
    if (!persistent_flag) {
      if (jkf != null) {
        jkf.killMe();
      }
      Jeli.Utility.hide(this);
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
      dispose();
    }
  }
  
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
  
  protected void resetSize() {
    invalidate();
    validate();
    pack();
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
