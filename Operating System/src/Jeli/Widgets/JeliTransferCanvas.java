package Jeli.Widgets;

import Jeli.JeliRedirectorMaster;
import Jeli.Utility;
import java.awt.Canvas;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class JeliTransferCanvas extends Canvas implements java.awt.event.MouseListener, java.awt.event.MouseMotionListener, java.awt.event.KeyListener
{
  private java.util.Vector directMouseHandlers;
  
  public JeliTransferCanvas()
  {
    addMouseListener(this);
    addMouseMotionListener(this);
    addKeyListener(this);
  }
  

  private void localJeliKeyPress(char c)
  {
    if (Utility.redirector != null)
      Utility.redirector.keyPress(this, c);
    jeliKeyPress(c);
  }
  
  private void localJeliMouseEnter() {
    if (Utility.redirector != null)
      Utility.redirector.mouseEnter(this);
    jeliMouseEnter();
  }
  
  private void localJeliMouseExited() {
    if (Utility.redirector != null)
      Utility.redirector.mouseExit(this);
    jeliMouseExited();
  }
  
  private void localJeliMousePressed(MouseEvent e) {
    if (Utility.redirector != null) {
      Utility.redirector.mousePressed(this, e.getX(), e.getY(), e.getModifiers(), e.getClickCount());
    }
    jeliMousePressed(e.getX(), e.getY(), e.getModifiers(), e.getClickCount());
  }
  
  private void localJeliMouseReleased(MouseEvent e) {
    if (Utility.redirector != null) {
      Utility.redirector.mouseReleased(this, e.getX(), e.getY(), e.getModifiers(), e.getClickCount());
    }
    jeliMouseReleased(e.getX(), e.getY(), e.getModifiers(), e.getClickCount());
  }
  
  private void localJeliMouseMoved(int x, int y) {
    if (Utility.redirector != null)
      Utility.redirector.mouseMoved(this, x, y);
    jeliMouseMoved(x, y);
  }
  
  private void localJeliMouseDragged(int x, int y) {
    if (Utility.redirector != null)
      Utility.redirector.mouseDragged(this, x, y);
    jeliMouseDragged(x, y);
  }
  

  public void keyTyped(KeyEvent e)
  {
    localJeliKeyPress(e.getKeyChar());
  }
  

  public void keyPressed(KeyEvent e) {}
  
  public void keyReleased(KeyEvent e) {}
  
  public void mouseEntered(MouseEvent e)
  {
    localJeliMouseEnter();
  }
  
  public void mouseExited(MouseEvent e) {
    localJeliMouseExited();
  }
  
  public void mouseClicked(MouseEvent e) {}
  
  public void mousePressed(MouseEvent e)
  {
    localJeliMousePressed(e);
  }
  
  public void mouseReleased(MouseEvent e) {
    localJeliMouseReleased(e);
  }
  
  public void mouseMoved(MouseEvent e) {
    localJeliMouseMoved(e.getX(), e.getY());
  }
  
  public void mouseDragged(MouseEvent e) {
    localJeliMouseDragged(e.getX(), e.getY());
  }
  

  public void jeliKeyPress(char c) {}
  
  public void jeliMouseEnter()
  {
    requestFocus();
  }
  
  public void jeliMouseExited() {}
  
  public void jeliMousePressed(int x, int y, int mod, int count) {}
  
  public void jeliMouseReleased(int x, int y, int mod, int count) {}
  
  public void jeliMouseMoved(int x, int y) {}
  
  public void jeliMouseDragged(int x, int y) {}
}
