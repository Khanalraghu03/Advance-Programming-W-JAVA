package Jeli.Widgets;

import java.awt.Color;
import java.awt.Graphics;

public class ProgressCanvas extends java.awt.Canvas
{
  Color C1;
  Color C2;
  int width;
  int height;
  double fract;
  
  public ProgressCanvas(Color C1, Color C2, int width, int height)
  {
    this.C1 = C1;
    this.C2 = C2;
    this.width = width;
    this.height = height;
    setSize(width, height);
    fract = 0.0D;
  }
  
  public void update(Graphics g) {
    paint(g);
  }
  

  public void paint(Graphics g)
  {
    width = getBoundswidth;
    height = getBoundsheight;
    int w1 = (int)(fract * width);
    g.setColor(C1);
    g.fillRect(0, 0, w1, height);
    g.setColor(C2);
    g.fillRect(w1, 0, width - w1, height);
  }
  
  public void setFraction(double f) {
    fract = f;
    
    repaint();
  }
}
