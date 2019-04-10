package Jeli;

import Jeli.Widgets.JeliTransferCanvas;




public class JeliHistoryEntry
{
  public static final int TYPE_NONE = 0;
  public static final int KEY_PRESS = 1;
  public static final int MOUSE_ENTER = 2;
  public static final int MOUSE_EXIT = 3;
  public static final int MOUSE_PRESS = 4;
  public static final int MOUSE_RELEASE = 5;
  public static final int MOUSE_MOVED = 6;
  public static final int MOUSE_DRAGGED = 7;
  public static final int TYPE_MAX = 7;
  public JeliTransferCanvas tc;
  public char c;
  public int type = 0;
  public int x = 0;
  public int y = 0;
  public int mod = 0;
  public int count = 0;
  
  public JeliHistoryEntry(JeliTransferCanvas tc, char c) {
    type = 1;
    this.tc = tc;
    this.c = c;
  }
  
  public JeliHistoryEntry(JeliTransferCanvas tc, int type) {
    this.tc = tc;
    this.type = type;
  }
  
  public JeliHistoryEntry(JeliTransferCanvas tc, int type, int x, int y) {
    this.tc = tc;
    this.type = type;
    this.x = x;
    this.y = y;
  }
  
  public JeliHistoryEntry(JeliTransferCanvas tc, int type, int x, int y, int mod, int count)
  {
    this.tc = tc;
    this.type = type;
    this.x = x;
    this.y = y;
    this.mod = mod;
    this.count = count;
  }
  
  public String getDescriptor() {
    switch (type) {
    case 0: 
      return "Unknown history type";
    case 1: 
      return "key press " + c + " from " + tc;
    case 2: 
      return "mouse enter from " + tc;
    case 3: 
      return "mouse enter from " + tc;
    case 4: 
      return "mouse press   at (" + x + "," + y + ") mod=" + mod + " count=" + count + " from " + tc;
    case 5: 
      return "mouse release at (" + x + "," + y + ") mod=" + mod + " count=" + count + " from " + tc;
    case 6: 
      return "mouse moved   at (" + x + "," + y + ") from " + tc;
    case 7: 
      return "mouse dragged at (" + x + "," + y + ") from " + tc;
    }
    return "Invalid history type";
  }
}
