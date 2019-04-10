package Jeli.Logging;

import Jeli.Widgets.JeliButton;
import java.awt.Component;
import java.awt.Panel;
import java.util.Vector;

public class EditData extends Jeli.Widgets.JeliFrame
{
  Vector SDL;
  JeliButton ShowAllButton;
  JeliButton DoneButton;
  boolean show_all_flag = false;
  int size;
  JeliButton[] LogButtons;
  JeliButton[] ShowButtons;
  JeliButton[] LabelButtons;
  java.awt.Point pos;
  boolean ButtonFlag;
  Jeli.Widgets.HelpManager hm;
  Component com;
  private String classification = "Edit Data";
  private String logString = "Log";
  private String noLogString = "NoLog";
  
  public EditData(String name, Vector SDL, Jeli.Widgets.HelpManager hm, Component com, boolean ButtonFlag)
  {
    this(name, SDL, hm, com, ButtonFlag, "Log", "NoLog");
  }
  
  public EditData(String name, Vector SDL, Jeli.Widgets.HelpManager hm, Component com, boolean ButtonFlag, String logS, String noLogS)
  {
    super(name);
    
    this.SDL = SDL;
    this.hm = hm;
    this.com = com;
    this.ButtonFlag = ButtonFlag;
    logString = logS;
    noLogString = noLogS;
    size = SDL.size();
    ShowAllButton = new JeliButton("Show Some", hm, this);
    DoneButton = new JeliButton("Done", hm, this);
    DoneButton.setButtonSize("xxxxxxxDonexxxxxxx");
    LogButtons = new JeliButton[size];
    ShowButtons = new JeliButton[size];
    LabelButtons = new JeliButton[size];
    for (int i = 0; i < size; i++) {
      StandardDataInfo SD = (StandardDataInfo)SDL.elementAt(i);
      LogButtons[i] = new JeliButton(logString, hm, this);
      LogButtons[i].setButtonSize(noLogString + "    ");
      ShowButtons[i] = new JeliButton("Show", hm, this);
      ShowButtons[i].setButtonSize("  NoShow  ");
      LabelButtons[i] = new JeliButton(SD.getSDComment(), hm, this);
      if (!SD.getSDLogFlag())
        LogButtons[i].setLabel(noLogString);
      if (!SD.getSDShowFlag())
        ShowButtons[i].setLabel("NoShow");
    }
    setLayout(new java.awt.BorderLayout());
    setup_layout();
    pack();
    pos = Jeli.Utility.getAbsolutePosition(com);
    Jeli.Utility.move(this, pos.x, pos.y);
    set_fonts();
    setResizable(false);
    setVisible(true);
  }
  
  private void set_fonts()
  {
    if (!ButtonFlag) return;
    for (int i = 0; i < size; i++) {
      StandardDataInfo SD = (StandardDataInfo)SDL.elementAt(i);
      if (SD.getSDActiveFlag()) {
        LabelButtons[i].setFontType(1);
      } else {
        LabelButtons[i].setFontType(0);
      }
    }
  }
  
  public void setup_layout() {
    size = SDL.size();
    






    int showsize = 0;
    for (int i = 0; i < size; i++) {
      StandardDataInfo SD = (StandardDataInfo)SDL.elementAt(i);
      if (SD.getSDShowFlag()) showsize++;
    }
    if (show_all_flag) showsize = size;
    Panel p = new Panel();
    p.setLayout(new Jeli.Widgets.JeliGridLayout(showsize + 1, 1));
    Panel s = new Panel();
    s.setLayout(new Jeli.Widgets.JeliGridLayout(showsize + 1, 1));
    Panel[] q = new Panel[size];
    Panel[] qb = new Panel[size];
    int j = 0;
    for (int i = 0; i < size; i++) {
      StandardDataInfo SD = (StandardDataInfo)SDL.elementAt(i);
      if ((SD.getSDShowFlag()) || (show_all_flag)) {
        q[j] = new Panel();
        q[j].setLayout(new Jeli.Widgets.JeliGridLayout(1, 1));
        q[j].add(LabelButtons[i]);
        qb[j] = new Panel();
        qb[j].setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
        qb[j].add(LogButtons[i]);
        qb[j].add(ShowButtons[i]);
        p.add(q[j]);
        s.add(qb[j]);
        j++;
      }
    }
    p.add(DoneButton);
    s.add(ShowAllButton);
    add("Center", p);
    add("East", s);
  }
  

  public void jeliButtonPushed(JeliButton bh)
  {
    if (bh == DoneButton) {
      Jeli.Utility.hide(this);
      return;
    }
    if (bh == ShowAllButton) {
      String lab = bh.getLabel();
      if ("Show Some".equals(lab)) {
        ShowAllButton.setLabel("Show All");
        show_all_flag = true;
        removeAll();
        setup_layout();
        pack();
        return;
      }
      if ("Show All".equals(lab)) {
        ShowAllButton.setLabel("Show Some");
        show_all_flag = false;
        removeAll();
        setup_layout();
        pack();
        return;
      }
    }
    for (int i = 0; i < size; i++) {
      if (bh == LogButtons[i]) {
        StandardDataInfo SD = (StandardDataInfo)SDL.elementAt(i);
        if (SD.getSDLogFlag()) {
          SD.setSDLogFlag(false);
          LogButtons[i].setLabel(noLogString);
        }
        else {
          SD.setSDLogFlag(true);
          LogButtons[i].setLabel(logString);
        }
        return;
      }
      if (bh == ShowButtons[i]) {
        StandardDataInfo SD = (StandardDataInfo)SDL.elementAt(i);
        if (SD.getSDShowFlag()) {
          SD.setSDShowFlag(false);
          ShowButtons[i].setLabel("NoShow");
        }
        else {
          SD.setSDShowFlag(true);
          ShowButtons[i].setLabel("Show");
        }
        removeAll();
        setup_layout();
        pack();
        return;
      }
      if (bh == LabelButtons[i]) {
        for (int j = 0; j < size; j++) {
          StandardDataInfo SD = (StandardDataInfo)SDL.elementAt(j);
          SD.setSDActiveFlag(j == i);
        }
        set_fonts();
        return;
      }
    }
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
