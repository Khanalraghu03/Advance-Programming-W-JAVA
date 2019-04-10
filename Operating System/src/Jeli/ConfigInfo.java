package Jeli;

import Jeli.Probability.Distribution;
import java.util.Vector;

public class ConfigInfo
{
  public String name;
  public String str;
  public int intval = -1;
  public double doubleval = -1.0D;
  public int[] intvals;
  public double[] doublevals;
  public Distribution dist;
  
  public ConfigInfo(String name, String str, int[] intvals, double[] doublevals, Distribution dist)
  {
    this.name = name;
    this.str = str;
    if (intvals != null)
      intval = intvals[0];
    if (doublevals != null)
      doubleval = doublevals[0];
    this.intvals = intvals;
    this.doublevals = doublevals;
    this.dist = dist;
  }
  
  public boolean checkName(String s) {
    return name.equalsIgnoreCase(s);
  }
  
  public String getDescriptor()
  {
    String s = "Name:    " + name + "\n";
    s = s + "   String:       " + str + "\n";
    if (intvals != null) {
      s = s + "   Integer:     ";
      for (int i = 0; i < intvals.length; i++)
        s = s + " " + intvals[i];
      s = s + "\n";
    }
    if (intvals != null) {
      s = s + "   Double:      ";
      for (int i = 0; i < doublevals.length; i++)
        s = s + " " + doublevals[i];
      s = s + "\n";
    }
    if (dist != null)
      s = s + "   Distribution: " + dist.getShortDescriptor() + "\n";
    return s;
  }
  

  public static String getDescriptors(Vector v)
  {
    if (v == null)
      return "Configuration vector is empty";
    String str = "Vector of size " + v.size() + " follows:\n";
    for (int i = 0; i < v.size(); i++) {
      str = str + ((ConfigInfo)v.elementAt(i)).getDescriptor();
    }
    return str;
  }
}
