package Jeli.Widgets;

import java.io.PrintStream;

public class DebuggingCanvas extends java.awt.Canvas
{
  private String report;
  private int gotten_count;
  private boolean focus;
  private int id;
  private DebugRegistration callback;
  
  public DebuggingCanvas(int w, int h, int id, String report, DebugRegistration callback)
  {
    this.report = report;
    this.id = id;
    this.callback = callback;
    setSize(w, h);
    gotten_count = 0;
    System.out.println("Made Debug Canvas with " + report);
  }
  
  public void MouseEnter(int x, int y) {
    requestFocus();
    focus = true;
    System.out.println("Entered");
  }
  
  public void mouseExitCanvas(int x, int y) {
    focus = false;
  }
  
  public void keyPressCanvas(int key)
  {
    if (!focus)
      return;
    char c = (char)key;
    if (c == report.charAt(gotten_count)) {
      gotten_count += 1;
      if (gotten_count == report.length()) {
        callback.setDebugFound(id);
        System.out.println("Got " + report);
        gotten_count = 0;
      }
    }
    else {
      gotten_count = 0;
    }
  }
}
