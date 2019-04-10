package Jeli.Animation;

import Jeli.Widgets.JeliButton;
import java.awt.Color;
import java.util.Vector;

public class TimePauseButton extends JeliButton implements Jeli.Widgets.JeliButtonCallBack
{
  private VirtualTime[] vt;
  private Vector callbacks = null;
  private String classification = "Time Pause Button";
  private String pauseString = "Pause";
  private String resumeString = "Resume";
  private Color pauseColor = Color.white;
  private Color resumeColor = Color.white;
  private int typePause = -1;
  private int typeResume = -1;
  private int degreePause = -1;
  private int degreeResume = -1;
  private boolean pause = true;
  
  public TimePauseButton(VirtualTime vt, Jeli.Widgets.HelpManager hm) {
    super("", hm, null);
    setLabel(pauseString);
    setBackgroundPause();
    this.vt = new VirtualTime[1];
    this.vt[0] = vt;
    resetJeliButtonCallBack(this);
    setPositionCenter();
  }
  
  public void setStandardColors(int typep, int degp, int typer, int degr)
  {
    typePause = typep;
    typeResume = typer;
    degreePause = degp;
    degreeResume = degr;
    if (getLabel().equals(pauseString)) {
      setBackgroundPause();
    } else
      setBackgroundResume();
  }
  
  private void setBackgroundPause() {
    if (typePause == -1) {
      setBackground(pauseColor);
    } else
      setStandardBackground(typePause, degreePause);
  }
  
  private void setBackgroundResume() {
    if (typeResume == -1) {
      setBackground(resumeColor);
    } else
      setStandardBackground(typeResume, degreeResume);
  }
  
  public void setColors(Color pc, Color rc) {
    pauseColor = pc;
    resumeColor = rc;
    setState();
  }
  
  public void setStateIfNecessary() {
    if ((vt[0].getRate() != 0.0D) == pause)
      setState();
  }
  
  public void setState() {
    if (vt[0].getRate() == 0.0D) {
      setLabel(resumeString);
      setBackgroundResume();
      pause = false;
    }
    else {
      setLabel(pauseString);
      setBackgroundPause();
      pause = true;
    }
  }
  
  public void setVirtualTimes(VirtualTime[] vt) {
    this.vt = new VirtualTime[vt.length];
    for (int i = 0; i < vt.length; i++)
      this.vt[i] = vt[i];
  }
  
  public void setPauseString(String s) {
    pauseString = s;
    setLabel(pauseString);
    setBackgroundPause();
  }
  
  public void setResumeString(String s) {
    resumeString = s;
  }
  


  public void jeliButtonPushed(JeliButton bh)
  {
    String label = bh.getLabel();
    if (label.equals(pauseString)) {
      for (int i = 0; i < vt.length; i++)
        vt[i].stopVirtualTime();
      setLabel(resumeString);
      setBackgroundResume();
    }
    else if (label.equals(resumeString)) {
      for (int i = 0; i < vt.length; i++)
        vt[i].restartVirtualTime();
      setLabel(pauseString);
      setBackgroundPause();
    }
    if (callbacks != null) {
      for (int i = 0; i < callbacks.size(); i++)
        ((AnimationDisplayer)callbacks.elementAt(i)).handleDisplay();
    }
  }
  
  public void addCallback(AnimationDisplayer cb) {
    if (callbacks == null)
      callbacks = new Vector();
    callbacks.addElement(cb);
  }
  
  public void forcePause() {
    for (int i = 0; i < vt.length; i++)
      vt[i].stopVirtualTime();
    setLabel(resumeString);
    setBackgroundPause();
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() {
    return classification;
  }
  
  public void setClassification(String str) {
    classification = str;
  }
}
