package Jeli.Plots;

import java.awt.Color;

public class PlotInfo {
  public double[] vals;
  public Color pc;
  public String key;
  public int[] bc;
  public int underflow;
  public int overflow;
  public double min;
  public double max;
  public double median;
  public double quart1;
  public double quart2;
  
  public PlotInfo(double[] vals, Color pc, String key) {
    this.vals = vals;
    this.pc = pc;
    this.key = new String(key);
    bc = null;
    min = 0.0D;
    max = 0.0D;
    median = 0.0D;
    quart1 = 0.0D;
    quart2 = 0.0D;
    underflow = 0;
    overflow = 0;
  }
  
  public void setColor(Color pc)
  {
    this.pc = pc;
  }
  



  private double[] sortit(double[] vals)
  {
    int size = vals.length;
    double[] svals = new double[size];
    for (int i = 0; i < size; i++)
      svals[i] = vals[i];
    for (int i = 0; i < size - 1; i++) {
      for (int j = 0; j < size - i - 1; j++)
        if (svals[j] > svals[(j + 1)]) {
          double temp = svals[j];
          svals[j] = svals[(j + 1)];
          svals[(j + 1)] = temp;
        }
    }
    return svals;
  }
  









  private double[] sortitf(double[] vals)
  {
    int size = 1;
    int logsize = 0;
    while (size < vals.length) {
      logsize++;
      size = 2 * size;
    }
    
    double[] source = new double[size];
    double[] dest = new double[size];
    for (int i = 0; i < vals.length; i++)
      source[i] = vals[i];
    for (; i < size; i++)
      source[i] = Double.MAX_VALUE;
    for (int chunk = 1; chunk < size; chunk = 2 * chunk)
    {
      for (i = 0; i < size; i += 2 * chunk) {
        int n = i;
        int m = i + chunk;
        for (int j = 0; j < 2 * chunk; j++)
        {
          if ((m >= i + 2 * chunk) || ((n < i + chunk) && (source[n] < source[m])))
          {

            dest[(i + j)] = source[n];
            n++;
          }
          else
          {
            dest[(i + j)] = source[m];
            m++;
          }
        }
      }
      double[] temp = source;
      source = dest;
      dest = temp;
    }
    
    return source;
  }
  





  public void setQuartiles()
  {
    int size = vals.length;
    if (size == 0) return;
    if (size == 1) {
      min = vals[0];
      max = vals[0];
      median = vals[0];
      quart1 = vals[0];
      quart2 = vals[0];
      return;
    }
    
    double[] svals = sortitf(vals);
    min = svals[0];
    max = svals[(size - 1)];
    int m1 = size / 2;
    int m2 = (size - 1) / 2;
    median = (0.5D * (svals[m1] + svals[m2]));
    m1 = size / 4;
    m2 = size - m1 - 1;
    quart1 = svals[m1];
    quart2 = svals[m2];
  }
}
