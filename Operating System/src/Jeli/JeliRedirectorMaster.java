package Jeli;

import Jeli.Widgets.JeliTransferCanvas;
import java.util.Vector;

public class JeliRedirectorMaster
{
  private Vector history;
  private Vector slaveList;
  
  public JeliRedirectorMaster()
  {
    history = new Vector();
    slaveList = new Vector();
    System.out.println("Created redirector");
  }
  
  public synchronized void keyPress(JeliTransferCanvas kl, char c)
  {
    JeliHistoryEntry he = new JeliHistoryEntry(kl, c);
    showit(he);
    history.addElement(he);
    sendToSlaves(he);
  }
  
  public synchronized void mouseEnter(JeliTransferCanvas ml)
  {
    JeliHistoryEntry he = new JeliHistoryEntry(ml, 2);
    showit(he);
    history.addElement(he);
    sendToSlaves(he);
  }
  
  public synchronized void mouseExit(JeliTransferCanvas ml)
  {
    JeliHistoryEntry he = new JeliHistoryEntry(ml, 3);
    showit(he);
    history.addElement(he);
    sendToSlaves(he);
  }
  
  public synchronized void mouseReleased(JeliTransferCanvas ml, int x, int y, int mod, int count)
  {
    JeliHistoryEntry he = new JeliHistoryEntry(ml, 5, x, y, mod, count);
    showit(he);
    history.addElement(he);
    sendToSlaves(he);
  }
  
  public synchronized void mousePressed(JeliTransferCanvas ml, int x, int y, int mod, int count)
  {
    JeliHistoryEntry he = new JeliHistoryEntry(ml, 4, x, y, mod, count);
    showit(he);
    history.addElement(he);
    sendToSlaves(he);
  }
  
  public synchronized void mouseMoved(JeliTransferCanvas ml, int x, int y)
  {
    JeliHistoryEntry he = new JeliHistoryEntry(ml, 6, x, y);
    showit(he);
    history.addElement(he);
    sendToSlaves(he);
  }
  
  public synchronized void mouseDragged(JeliTransferCanvas ml, int x, int y)
  {
    JeliHistoryEntry he = new JeliHistoryEntry(ml, 7, x, y);
    showit(he);
    history.addElement(he);
    sendToSlaves(he);
  }
  
  private void showit(JeliHistoryEntry he) {
    System.out.println("Redirector got " + he.getDescriptor());
  }
  
  private synchronized void sendToSlaves(JeliHistoryEntry he) {
    for (int i = 0; i < slaveList.size(); i++) {
      sendToSlave((JeliRedirectorSlave)slaveList.elementAt(i), he);
    }
  }
  
  private synchronized void sendToSlave(JeliRedirectorSlave slave, JeliHistoryEntry he) {
    System.out.println("Sending to slave not yet implemented");
  }
}
