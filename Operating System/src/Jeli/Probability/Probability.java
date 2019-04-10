package Jeli.Probability;

import java.io.PrintStream;


public class Probability
{
  public static final double N0 = 2.515517D;
  public static final double N1 = 0.802853D;
  public static final double N2 = 0.010328D;
  public static final double D0 = 1.0D;
  public static final double D1 = 1.432788D;
  public static final double D2 = 0.189269D;
  public static final double D3 = 0.001308D;
  public static final double NA0 = 1.0D;
  public static final double NA1 = 0.196854D;
  public static final double NA2 = 0.115194D;
  public static final double NA3 = 3.44E-4D;
  public static final double NA4 = 0.019527D;
  private static final double a = 16807.0D;
  private static final double m = 2.147483647E9D;
  private static final double q = 127773.0D;
  private static final double r = 2836.0D;
  private static double seed;
  private static boolean portable_flag = false;
  
  public Probability() {}
  
  private static void PortableInitialize()
  {
    seed = 1.0D;
    for (int i = 0; i < 10000; i++) {
      portable_next();
    }
  }
  

  private static double portable_next()
  {
    long hilong = (seed / 127773.0D);
    double hi = hilong;
    double lo = seed - 127773.0D * hi;
    double test = 16807.0D * lo - 2836.0D * hi;
    if (test > 0.0D)
      seed = test; else {
      seed = test + 2.147483647E9D;
    }
    return seed / 2.147483647E9D;
  }
  


  private static SeedVal portable_next_seed(double seed1)
  {
    long hilong = (seed1 / 127773.0D);
    double hi = hilong;
    double lo = seed1 - 127773.0D * hi;
    double test = 16807.0D * lo - 2836.0D * hi;
    if (test > 0.0D)
      seed1 = test; else {
      seed1 = test + 2.147483647E9D;
    }
    return new SeedVal(seed1, seed1 / 2.147483647E9D);
  }
  
  public static void debug()
  {
    PortableInitialize();
    System.out.println("Seed=" + (1000.0D * seed) + "  Dif=" + (1000.0D * (seed - 1.043618065E9D)));
  }
  
  public static double setPortable(boolean flag) {
    portable_flag = flag;
    if (portable_flag) {
      PortableInitialize();
      return seed - 1.043618065E9D;
    }
    return 0.0D;
  }
  
  public static double getNewSeed()
  {
    double newseed = 1.0E7D * nextRandom();
    
    return newseed;
  }
  
  public static double nextRandom() {
    if (portable_flag)
    {
      return portable_next();
    }
    return Math.random();
  }
  
  public static double uniform(double low, double high)
  {
    double ran = nextRandom();
    return low + (high - low) * ran;
  }
  
  public static SeedVal uniformSeed(double low, double high, double seed)
  {
    SeedVal sv = portable_next_seed(seed);
    val = (low + (high - low) * val);
    
    return sv;
  }
  
  public static double discreteUniform(int low, int high)
  {
    double ran = nextRandom();
    return low + (int)(ran * (high - low + 1L));
  }
  
  public static SeedVal discreteUniformSeed(int low, int high, double seed)
  {
    SeedVal sv = portable_next_seed(seed);
    val = (low + (int)(val * (high - low + 1L)));
    return sv;
  }
  
  public static double exponential(double mean)
  {
    double ran = nextRandom();
    return -Math.log(1.0D - ran) * mean;
  }
  
  public static SeedVal exponentialSeed(double mean, double seed)
  {
    SeedVal sv = portable_next_seed(seed);
    val = (-Math.log(1.0D - val) * mean);
    
    return sv;
  }
  
  public static double normal()
  {
    double ran = nextRandom();
    return inverseNormal(ran);
  }
  
  public static SeedVal normalSeed(double seed)
  {
    SeedVal sv = portable_next_seed(seed);
    val = inverseNormal(val);
    return sv;
  }
  
  public static double normal(double mean, double sd)
  {
    double z = normal();
    return z * sd + mean;
  }
  
  public static SeedVal normalSeed(double mean, double sd, double seed)
  {
    SeedVal sv = normalSeed(seed);
    val = (val * sd + mean);
    
    return sv;
  }
  







  public static double inverseNormal(double p)
  {
    if (p == 0.5D) return 0.0D;
    double p1 = p;
    if (p < 0.5D)
      p1 = 1.0D - p;
    double eta = Math.sqrt(-2.0D * Math.log(1.0D - p1));
    double eta2 = eta * eta;
    double eta3 = eta2 * eta;
    double ret = eta - (2.515517D + 0.802853D * eta + 0.010328D * eta2) / (1.0D + 1.432788D * eta + 0.189269D * eta2 + 0.001308D * eta3);
    if (p < 0.5D)
      ret = -ret;
    return ret;
  }
  








  public static double normalArea(double z)
  {
    double z1 = z;
    if (z < 0.0D) z1 = -z;
    double z2 = z1 * z1;
    double z3 = z2 * z1;
    double z4 = z2 * z2;
    
    double denom = 1.0D + 0.196854D * z1 + 0.115194D * z2 + 3.44E-4D * z3 + 0.019527D * z4;
    denom *= denom;
    denom *= denom;
    double ret; double ret; if (z < 0.0D) {
      ret = 0.5D / denom;
    } else
      ret = 1.0D - 0.5D / denom;
    return ret;
  }
}
