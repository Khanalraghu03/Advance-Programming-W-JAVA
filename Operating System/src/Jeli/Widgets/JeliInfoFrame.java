package Jeli.Widgets;

import java.awt.Color;
import java.awt.Point;
import java.util.StringTokenizer;
import java.util.Vector;

public class JeliInfoFrame extends JeliTextFrame
{
  private String[][] blabels;
  private Color foregroundColor = Color.black;
  private Color highlightColor = Color.blue;
  private Point[] group;
  private String groupSet = "Set Jeli Info Display Group ";
  
  public JeliInfoFrame(int id, int rows, int columns, String label)
  {
    super(id, rows, columns, label, Jeli.Utility.hm, null);
    blabels = new String[1][1];
    blabels[0][0] = "Hide";
    setButtons(blabels);
  }
  
  public void setGroupArray(Point[] group)
  {
    this.group = group;
  }
  
  public void setHighlightNone() {
    setAllLinesForeground(foregroundColor);
  }
  
  public void setHighlighted(int n) {
    setHighlightNone();
    if (group == null) {
      setLineForeground(n, highlightColor);
      return;
    }
    if (n >= group.length) return;
    for (int i = group[n].x; i <= group[n].y; i++)
    {
      setLineForeground(i, highlightColor);
    }
  }
  


  public void setText(String s)
  {
    char groupchar = '\000';
    int count = 0;
    Vector v = null;
    Point p = null;
    

    StringBuffer sb = new StringBuffer();
    StringTokenizer stk = new StringTokenizer(s, "\n\r");
    if (!stk.hasMoreTokens()) return;
    String s1 = stk.nextToken();
    if (s1.startsWith(groupSet)) {
      if (groupSet.length() == s1.length()) return;
      groupchar = s1.charAt(groupSet.length());
      
      if (groupchar == ' ') groupchar = '\000';
    } else {
      getArea().append(s1 + "\n"); }
    while (stk.hasMoreTokens()) {
      s1 = stk.nextToken();
      if ((groupchar != 0) && (s1.length() >= 0) && (s1.charAt(0) == groupchar))
      {

        if (v == null) v = new Vector();
        if (p == null)
        {
          p = new Point(count, -1);
        }
        else
        {
          y = count;
          v.addElement(p);
          p = null;
        }
        s1 = s1.substring(1);
        if ((s1.length() >= 0) && (s1.charAt(0) == groupchar))
        {
          if (p == null)
          {
            p = new Point(count, -1);
          }
          else
          {
            y = count;
            v.addElement(p);
            p = null;
          }
          s1 = s1.substring(1);
        }
      }
      getArea().append(s1 + "\n");
      count++;
    }
    if (p != null) {
      y = (count - 1);
      v.addElement(p);
    }
    if (v != null)
    {






      if (v.size() > 0) {
        group = new Point[v.size()];
        for (int i = 0; i < v.size(); i++)
          group[i] = ((Point)v.elementAt(i));
      }
    }
    top();
  }
}
