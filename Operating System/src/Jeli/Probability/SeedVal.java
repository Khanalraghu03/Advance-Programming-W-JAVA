package Jeli.Probability;

public class SeedVal
{
  public double seed;
  public double val;
  
  public SeedVal(double seed, double val) {
    this.seed = seed;
    this.val = val;
  }
  
  public String getDescriptor() {
    return "Seed: " + seed + "   Value: " + val;
  }
}
