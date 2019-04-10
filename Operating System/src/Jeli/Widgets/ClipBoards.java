package Jeli.Widgets;

import java.util.Vector;

public class ClipBoards implements ClipBoard
{
  private Vector clips;
  private String clipStr;
  
  public ClipBoards() {
    clips = new Vector();
    clipStr = "";
  }
  
  public void reset() {
    clips.removeAllElements();
  }
  
  public void addClipBoard(ClipBoard cb) {
    clips.addElement(cb);
  }
  
  public void setClipBoardString(String s) {
    clipStr = s;
    
    for (int i = 0; i < clips.size(); i++)
      ((ClipBoard)clips.elementAt(i)).setClipBoardString(s);
  }
  
  public String getClipBoardString() {
    return clipStr;
  }
}
