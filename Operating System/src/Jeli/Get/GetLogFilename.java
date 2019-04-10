package Jeli.Get;

import Jeli.Utility;
import Jeli.Widgets.JeliButton;
import java.awt.GridBagLayout;
import java.awt.Panel;

public class GetLogFilename extends Jeli.Widgets.JeliFrame implements Jeli.Widgets.JeliButtonCallBack
{
  String fn;
  String dir;
  String imname;
  String newfn;
  String newdir;
  String newimname;
  LogFilenameCallBack callback;
  java.awt.Component com;
  JeliButton ResetFilenameButton;
  JeliButton FilenameButton;
  JeliButton ResetDirectoryButton;
  JeliButton DirectoryButton;
  JeliButton ResetImageButton;
  JeliButton ImageButton;
  JeliButton AbortButton;
  JeliButton DoneButton;
  Jeli.Widgets.HelpManager hm;
  private GridBagLayout gbl;
  private java.awt.GridBagConstraints c;
  private String ResetButtonString = "Reset Image Name xxx";
  private String SetButtonString = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";
  private String classification = null;
  


  public GetLogFilename(String name, String fn, String dir, String imname, Jeli.Widgets.HelpManager hm, LogFilenameCallBack callback, java.awt.Component com)
  {
    super(name);
    this.fn = fn;
    this.dir = dir;
    this.imname = imname;
    this.callback = callback;
    this.com = com;
    this.hm = hm;
    newfn = fn;
    newdir = dir;
    newimname = imname;
    setup_layout();
    pack();
    
    showGetLogFilename();
  }
  
  public void showGetLogFilename()
  {
    if (com != null) {
      java.awt.Point pos = Utility.getAbsolutePosition(com);
      setLocation(x, y);
    }
    setVisible(true);
  }
  

  public void setup_layout()
  {
    Panel p = new Panel();
    Panel q = new Panel();
    gbl = new GridBagLayout();
    c = new java.awt.GridBagConstraints();
    p.setLayout(gbl);
    q.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
    setLayout(new java.awt.BorderLayout());
    c.fill = 1;
    c.weighty = 1.0D;
    c.gridx = 0;
    c.gridy = 0;
    
    c.weightx = 0.0D;
    ResetFilenameButton = new JeliButton("Reset Filename", hm, this);
    ResetFilenameButton.setButtonSize(ResetButtonString);
    gbl.setConstraints(ResetFilenameButton, c);
    p.add(ResetFilenameButton);
    c.weightx = 1.0D;
    c.gridx = 1;
    FilenameButton = new JeliButton("", hm, this);
    FilenameButton.setAsGetString("Filename", "Enter Filename: ", "Filename: ", fn, true);
    
    FilenameButton.setButtonSize(SetButtonString);
    gbl.setConstraints(FilenameButton, c);
    p.add(FilenameButton);
    c.gridx = 0;
    c.gridy = 1;
    c.weightx = 0.0D;
    ResetDirectoryButton = new JeliButton("Reset Directory", hm, this);
    ResetDirectoryButton.setButtonSize(ResetButtonString);
    gbl.setConstraints(ResetDirectoryButton, c);
    if (newdir != null)
      p.add(ResetDirectoryButton);
    c.weightx = 1.0D;
    c.gridx = 1;
    DirectoryButton = new JeliButton("", hm, this);
    DirectoryButton.setAsGetString("Directory", "Enter Directory: ", "Directory: ", dir, true);
    
    DirectoryButton.setButtonSize(SetButtonString);
    gbl.setConstraints(DirectoryButton, c);
    if (newdir != null)
      p.add(DirectoryButton);
    c.gridx = 0;
    c.gridy = 2;
    c.weightx = 0.0D;
    ResetImageButton = new JeliButton("Reset Image Name", hm, this);
    ResetImageButton.setButtonSize(ResetButtonString);
    gbl.setConstraints(ResetImageButton, c);
    p.add(ResetImageButton);
    c.weightx = 1.0D;
    c.gridx = 1;
    ImageButton = new JeliButton("", hm, this);
    ImageButton.setAsGetString("Image Name", "Enter Image Name: ", "Image Name: ", imname, true);
    
    ImageButton.setButtonSize(SetButtonString);
    gbl.setConstraints(ImageButton, c);
    p.add(ImageButton);
    AbortButton = new JeliButton("Abort", hm, this);
    AbortButton.setButtonSize("Abort");
    AbortButton.setBackground(Utility.jeliLightRed);
    q.add(AbortButton);
    DoneButton = new JeliButton("Done", hm, this);
    DoneButton.setButtonSize("Abort");
    DoneButton.setBackground(Utility.jeliLightGreen);
    q.add(DoneButton);
    add("Center", p);
    add("South", q);
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == ResetFilenameButton) {
      newfn = fn;
      FilenameButton.resetGet();
      FilenameButton.setAsGetString("Filename", "Enter Filename: ", "Filename: ", fn, true);
    }
    
    if (bh == ResetDirectoryButton) {
      newdir = dir;
      DirectoryButton.resetGet();
      DirectoryButton.setAsGetString("Directory", "Enter Directory: ", "Directory: ", dir, true);
    }
    
    if (bh == ResetImageButton) {
      newimname = imname;
      ImageButton.resetGet();
      ImageButton.setAsGetString("Image Name", "Enter Image Name: ", "Image Name: ", imname, true);
    }
    
    if (bh == AbortButton) {
      setVisible(false);
      return;
    }
    if (bh == DoneButton) {
      callback.setLogFilename(newfn, newdir, newimname);
      setVisible(false);
    }
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {
    if (bh == FilenameButton)
      newfn = str;
    if (bh == DirectoryButton)
      newdir = str;
    if (bh == ImageButton)
      newimname = str;
  }
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() { return classification; }
  
  public void setClassification(String str)
  {
    classification = str;
  }
}
