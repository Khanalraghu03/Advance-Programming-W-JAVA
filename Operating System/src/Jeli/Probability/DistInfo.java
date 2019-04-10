package Jeli.Probability;

public class DistInfo {
  public int type;
  public String name;
  public double mean;
  public double high;
  
  public DistInfo(int type, double mean, double high) {
    this.type = type;
    this.mean = mean;
    this.high = high;
  }
}
