package Jeli.Widgets;

import java.util.StringTokenizer;
import java.util.Vector;

public class HelpMessage
{
  public static final int TYPE_ALL = -1;
  public static final int TYPE_REST = -2;
  public static final int TYPE_ANY = -3;
  public String name;
  public int ID;
  public String message;
  
  public HelpMessage(String name, int ID, String message)
  {
    this.name = name;
    this.ID = ID;
    this.message = message;
  }
  







  public static String lookup(Vector v, String n, int t)
  {
    String rest = null;
    if (v == null) return null;
    if (v.size() == 0) return null;
    for (int i = 0; i < v.size(); i++) {
      HelpMessage hmsg = (HelpMessage)v.elementAt(i);
      if (n.equals(name))
      {
        if (ID == -1) return message;
        if (t == -3) return message;
        if (t == ID) return message;
        if (ID == -2) rest = message;
      }
    }
    if (rest != null) return rest;
    return null;
  }
  





  public static void parseAll(String s, Vector v)
  {
    char delim = s.charAt(0);
    StringTokenizer stk = new StringTokenizer(s, "" + delim);
    while (stk.hasMoreElements()) {
      String str = stk.nextToken();
      HelpMessage hmsg = ParseOne(str);
      if (hmsg == null) { return;
      }
      
      v.addElement(hmsg);
    }
  }
  











  public static HelpMessage ParseOne(String s)
  {
    int ind = 0;
    while ((s.charAt(ind) == ' ') || (s.charAt(ind) == '\n'))
      ind++;
    int ind1 = s.indexOf(" ", ind);
    if (ind1 < 0) return null;
    String name = s.substring(ind, ind1);
    ind = ind1 + 1;
    ind1 = s.indexOf("\n", ind);
    if (ind1 < 0) return null;
    String typename = s.substring(ind, ind1);
    int type;
    try { type = Integer.parseInt(typename);
    } catch (NumberFormatException e) {
      return null;
    }
    String msg;
    try { msg = s.substring(ind1 + 1);
    } catch (StringIndexOutOfBoundsException e) {
      return null;
    }
    return new HelpMessage(name, type, msg);
  }
}
