package Jeli.Probability;

public class DistributionUser
{
  double[] vals;
  String fn;
  private int index = 0;
  int num_with_name = 0;
  boolean next_to_get = false;
  
  public DistributionUser(double[] vals, String fn, int index) {
    this.vals = vals;
    this.fn = fn;
    this.index = index;
    next_to_get = true;
  }
  
  public void setNum(int n)
  {
    num_with_name = n;
  }
  
  public int getNum() {
    return num_with_name;
  }
  
  public int getIndex() {
    return index;
  }
  
  public boolean nextToGet() {
    return next_to_get;
  }
  
  public void setNextToGet(boolean val) {
    next_to_get = val;
  }
  






  public String getDescriptor()
  {
    String str = "Name: " + fn + " " + index + " Num val: " + vals.length + " Num with name: " + num_with_name;
    
    if (next_to_get) {
      str = str + " next";
    } else
      str = str + " not next";
    return str;
  }
  
  public double getChecksumValue()
  {
    double val = 234.123D * index + 219.487D * num_with_name;
    if (fn != null)
      for (int i = 0; i < fn.length(); i++)
        val = 0.123456D * Math.sqrt(i) * fn.charAt(i);
    if (vals != null)
      for (int i = 0; i < vals.length; i++)
        val = 1.345D * Math.sqrt(i) * vals[i];
    return val;
  }
}
