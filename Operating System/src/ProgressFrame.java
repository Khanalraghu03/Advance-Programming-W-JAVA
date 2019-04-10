import Jeli.Widgets.JeliButton;
import Jeli.Widgets.ObjectCallBack;
import java.awt.Panel;

public class ProgressFrame extends Jeli.Widgets.JeliFrame
{
  private ProgressPanel[] pp;
  private JeliButton hideButton;
  private JeliButton helpButton;
  private ProgressTracker pt;
  private ObjectCallBack changeNotify = null;
  private Jeli.Widgets.SelfDestructingShortInfoFrame sif = null;
  private String name = "Progress Tracker";
  private int numSec = 10;
  private String helpMsg1 = "The Progress Tracker shows the steps needed to complete the address translation.\n";
  

  private String helpMsgHelp = "Click on one the the items for an explanation of that step.\n";
  
  private String helpMsgDo = "If the step is the next one to execute, additional information is provided.\nSome of the additional items allow for performing that step.\n";
  


  private String helpMsg2 = "Completed steps are indicated by a cyan dot.\n";
  
  private String helpMsg;
  
  public ProgressFrame(String name, String[] list, ProgressTracker pt, boolean numbered)
  {
    super(name);
    this.pt = pt;
    pp = new ProgressPanel[list.length];
    String numLen = "0.";
    if (list.length > 9) {
      numLen = "0" + numLen;
    }
    for (int i = 0; i < list.length; i++) {
      pp[i] = new ProgressPanel(list[i], i + 1 + ".", numLen);
      pp[i].setCallBack(this);
    }
    setupLayout();
    setResizable(false);
    addWindowListener(this);
    if (pt.getProgressDo()) {
      helpMsg = (helpMsg1 + helpMsgHelp + helpMsgDo + helpMsg2);
    } else if (pt.getProgressHelp()) {
      helpMsg = (helpMsg1 + helpMsgHelp + helpMsg2);
    } else {
      helpMsg = (helpMsg1 + helpMsg2);
    }
  }
  
  private void setupLayout() {
    setLayout(new Jeli.Widgets.JeliGridLayout(pp.length + 1, 1));
    for (int i = 0; i < pp.length; i++) {
      add(pp[i]);
    }
    Panel buttonPanel = new Panel();
    buttonPanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
    buttonPanel.add(this.hideButton = new JeliButton("Hide", this));
    buttonPanel.add(this.helpButton = new JeliButton("Help", this));
    hideButton.setPositionCenter();
    hideButton.setBackground(Address.hideColor);
    helpButton.setPositionCenter();
    helpButton.setBackground(Address.helpColor);
    add(buttonPanel);
    pack();
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == hideButton) {
      setVisible(false);
      return;
    }
    if (bh == helpButton)
    {
      if (sif != null)
        sif.abort();
      sif = new Jeli.Widgets.SelfDestructingShortInfoFrame(name, helpMsg, numSec, helpButton);
      



      return;
    }
    for (int i = 0; i < pp.length; i++) {
      if (pp[i].testButton(bh)) {
        if (pt != null) {
          pt.buttonPushed(i, pp[i]);
          if (changeNotify != null) {
            changeNotify.objectNotify(i, this);
          }
        }
        return;
      }
    }
    System.out.println("Got Jeli Button " + bh);
  }
  
  public String getNotifyString(int i) {
    if (i < 0) {
      return "Progress Tracker Help";
    }
    if (i < pp.length) {
      return "Progress Tracker Help for " + (i + 1) + ". " + pp[i].getDescription();
    }
    
    return "Progress Tracker Unknown Event";
  }
  
  public void disableEntry(int n) {
    if ((n < 0) || (n >= pp.length)) {
      return;
    }
    pp[n].disableDescription();
    pp[n].disableCheck();
    pp[n].refresh();
  }
  
  public boolean getDone(int n) {
    if ((n < 0) || (n >= pp.length)) {
      return false;
    }
    return pp[n].getChecked();
  }
  
  public boolean isNext(int n) {
    if ((n < 0) || (n >= pp.length))
      return false;
    if (pp[n].getChecked())
      return false;
    if (pp[n].getDisabled())
      return false;
    for (int i = 0; i < n; i++)
      if ((!pp[i].getChecked()) && (!pp[i].getDisabled()))
        return false;
    return true;
  }
  
  public boolean getDisabled(int n) {
    if ((n < 0) || (n >= pp.length)) {
      return false;
    }
    return pp[n].getDisabled();
  }
  
  public void disableUndone() {
    for (int i = 0; i < pp.length; i++) {
      if (!pp[i].getChecked()) {
        disableEntry(i);
      }
    }
  }
  
  public void setDone(int n, boolean f) {
    if ((n < 0) || (n >= pp.length)) {
      return;
    }
    if (f) {
      pp[n].setChecked();
    }
    else {
      pp[n].setUnchecked();
    }
  }
  
  public void setDoneUpto(int n) {
    for (int i = 0; i < n; i++) {
      if (i > pp.length) {
        return;
      }
      pp[i].setChecked();
    }
    for (int i = n; i < pp.length; i++) {
      pp[i].setUnchecked();
    }
  }
  
  public void setChangeNotify(ObjectCallBack cb) {
    changeNotify = cb;
  }
}
