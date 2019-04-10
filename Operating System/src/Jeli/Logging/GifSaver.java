package Jeli.Logging;

import Jeli.Utility;
import Jeli.Widgets.JeliButton;
import java.awt.Image;
import java.awt.Panel;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class GifSaver extends Jeli.Widgets.JeliFrame implements Runnable
{
  JeliButton AbortButton;
  Image im;
  GIFEncoder encoder;
  FileOutputStream myout;
  JeliButton FileName;
  String erm;
  JeliButton EncodingLabel;
  JeliButton WritingLabel;
  JeliButton ErrorLabel;
  JeliButton Prompt;
  Thread do_it;
  String filename;
  boolean stop_thread_flag = false;
  
  public GifSaver(Image im) {
    super("Jeli Gif Saver Tool");
    this.im = im;
    
    setup_layout();
    setVisible(true);
    stop_thread_flag = false;
    do_it = new Thread(this);
    do_it.setName("Jeli GifSaver Thread");
    filename = null;
    do_it.start();
  }
  
  private void setup_layout() {
    Panel q = new Panel();
    setLayout(new Jeli.Widgets.JeliGridLayout(5, 1));
    q.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
    FileName = new JeliButton("fn", Utility.hm, this);
    FileName.setAsGetString("Filename", "Enter Filename: ", "Filename:", "", false);
    
    FileName.setGetString("Filename: ");
    FileName.setButtonSize(200);
    q.add(FileName);
    AbortButton = new JeliButton("Abort", Utility.hm, this);
    q.add(AbortButton);
    add(this.EncodingLabel = new JeliButton("", Utility.hm, null));
    add(this.WritingLabel = new JeliButton("", Utility.hm, null));
    add(this.ErrorLabel = new JeliButton("", Utility.hm, null));
    EncodingLabel.setAsLabel();
    WritingLabel.setAsLabel();
    ErrorLabel.setAsLabel();
    ErrorLabel.setForeground(java.awt.Color.red);
    Prompt = new JeliButton("Move Pointer into Box Below and Enter File Name:", Utility.hm, null);
    

    Prompt.setAsLabel();
    add(Prompt);
    add(q);
    pack();
  }
  
  public void run() {
    EncodingLabel.setLabel("Creating Gif Encoder ...");
    repaint();
    try {
      encoder = new GIFEncoder(im);
    }
    catch (java.awt.AWTException e1) {
      System.out.println("Exception creating gif encoder");
      EncodingLabel.setLabel("Error Creating Gif Encoder");
      AbortButton.setLabel("Abort");
      Prompt.setLabel("Abort and try again");
      repaint();
      return;
    }
    if (stop_thread_flag) return;
    EncodingLabel.setLabel("Creating Gif Encoder ... Done");
    repaint();
    while (filename == null) {
      if (stop_thread_flag) return;
      WritingLabel.setLabel("Waiting for Output File Name");
      repaint();
      slow_it_down(250);
    }
    if (stop_thread_flag) return;
    WritingLabel.setLabel("Creating file " + filename + " ...");
    repaint();
    slow_it_down(100);
    if (stop_thread_flag) return;
    try {
      erm = ("Error Creating File " + filename);
      myout = new FileOutputStream(filename);
      WritingLabel.setLabel("Creating file " + filename + " ... done, now writing ...");
      
      repaint();
      slow_it_down(100);
      if (stop_thread_flag) return;
      erm = ("Error Writing To File " + filename);
      encoder.writeOutput(myout);
      WritingLabel.setLabel(filename + " created.");
      repaint();
      erm = ("Error Closing File " + filename);
      myout.close();
      AbortButton.setLabel("Done");
      if (stop_thread_flag) return;
    }
    catch (IOException e1) {
      ErrorLabel.setLabel(erm);
      Prompt.setLabel("Abort and try again");
      AbortButton.setLabel("Abort");
    }
    catch (Exception e2) {
      ErrorLabel.setLabel(erm);
      Prompt.setLabel("Abort and try again");
      AbortButton.setLabel("Abort");
    }
    repaint();
  }
  
  private void slow_it_down(int ms) {
    try {
      Thread.sleep(ms);
    }
    catch (InterruptedException e) {}
  }
  






  public void jeliButtonPushed(JeliButton bh)
  {
    if (bh == AbortButton) {
      if (bh.getLabel().equals("Abort")) {
        System.out.println("Abort button pushed");
        stop_thread_flag = true;
        setVisible(false);
      }
      else if (bh.getLabel().equals("Done")) {
        System.out.println("Done button pushed");
        setVisible(false);
      }
    }
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {
    filename = str;
    System.out.println("Got filename " + filename); }
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() { return "GifSaver"; }
  
  public void setClassification(String str) {}
}
