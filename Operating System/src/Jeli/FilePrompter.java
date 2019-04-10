package Jeli;

import Jeli.Widgets.HelpManager;
import Jeli.Widgets.JeliButton;
import java.awt.Panel;
import java.awt.TextArea;

public class FilePrompter extends Jeli.Widgets.JeliFrame implements Runnable
{
  TextArea contents;
  String name = "";
  HelpManager hm;
  JeliButton abortButton;
  JeliButton okButton;
  JeliButton clearButton;
  JeliButton title;
  int rows = 10;
  int columns = 50;
  String classification = "File Prompter";
  boolean text_ready = false;
  boolean abort = false;
  
  public FilePrompter(HelpManager hm) {
    super("File Prompter");
    this.hm = hm;
    setup_layout();
    pack();
  }
  
  private void setup_layout()
  {
    setLayout(new java.awt.BorderLayout());
    Panel p = new Panel();
    p.setLayout(new Jeli.Widgets.JeliGridLayout(1, 3));
    p.add(this.abortButton = new JeliButton("Abort", hm, this));
    p.add(this.clearButton = new JeliButton("Clear", hm, this));
    p.add(this.okButton = new JeliButton("OK", hm, this));
    contents = new TextArea(rows, columns);
    title = new JeliButton("x", hm, null);
    set_title_label();
    title.setAsLabel();
    add("Center", contents);
    add("South", p);
    add("North", title);
    abortButton.setBackground(java.awt.Color.pink);
    okButton.setBackground(java.awt.Color.cyan);
  }
  
  public void setName(String name)
  {
    this.name = name;
    set_title_label();
  }
  
  private void set_title_label() {
    if (contents.getText().length() > 0) {
      title.setLabel("Edit Contents of File " + name);
    } else
      title.setLabel("Enter Contents of File " + name);
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == abortButton) {
      abort = true;
      text_ready = true;
      return;
    }
    if (bh == okButton) {
      text_ready = true;
      UtilityIO.playGood();
      return;
    }
    if (bh == clearButton) {
      contents.setText("");
      return;
    } }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() { return classification; }
  
  public void setClassification(String str)
  {
    classification = str;
  }
  
  public void run() {
    while (!text_ready) {
      try {
        Thread.sleep(250L);
      }
      catch (InterruptedException e) {}
    }
  }
  
  public String getContents() {
    return getContents("");
  }
  
  public String getContents(String str)
  {
    Thread th = new Thread(this);
    th.setName("Jeli File Prompter");
    text_ready = false;
    abort = false;
    contents.setText(str);
    set_title_label();
    th.start();
    setVisible(true);
    UtilityIO.playBad();
    try
    {
      th.join();
    }
    catch (InterruptedException e) {}
    
    setVisible(false);
    if (abort) return null;
    return contents.getText();
  }
}
