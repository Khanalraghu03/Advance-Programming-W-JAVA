package Jeli.Probability;

import Jeli.Utility;
import java.util.StringTokenizer;
import java.util.Vector;

public class Distribution
{
  public static final int DIST_TYPE_UNKNOWN = 0;
  public static final int DIST_TYPE_CONT_CONSTANT = 1;
  public static final int DIST_TYPE_DISC_CONSTANT = 2;
  public static final int DIST_TYPE_CONT_UNIFORM = 3;
  public static final int DIST_TYPE_DISC_UNIFORM = 4;
  public static final int DIST_TYPE_EXPONENTIAL = 5;
  public static final int DIST_TYPE_NORMAL = 6;
  public static final int DIST_TYPE_SEED = 7;
  public static final int DIST_TYPE_USER = 8;
  private int type;
  private double c_mean;
  private double sd;
  private double c_low;
  private double c_high;
  private int d_mean;
  private int d_low;
  private int d_high;
  private double private_seed;
  private double initial_seed;
  private Vector nextValues = null;
  
  private static boolean force_parse_float = false;
  private boolean use_private_seed = false;
  
  private static Vector userlist = null;
  private String user_filename;
  private int next_user_entry = -1;
  private DistributionUser du = null;
  
  public Distribution(int type) {
    this.type = type;
    if (type != 6)
      this.type = 0;
    c_mean = 0.0D;
    sd = 1.0D;
  }
  
  public Distribution(int type, double mean)
  {
    this.type = type;
    if ((type != 1) && (type != 5) && (type != 6))
    {

      this.type = 0; }
    c_mean = mean;
    sd = 1.0D;
  }
  
  public Distribution(int type, double v1, double v2)
  {
    this.type = type;
    if ((type != 3) && (type != 6))
    {
      this.type = 0;
    } else if (type == 3) {
      c_low = v1;
      c_high = v2;
    }
    else if (type == 6) {
      c_mean = v1;
      sd = v2;
    }
  }
  
  public Distribution(int type, int mean)
  {
    this.type = type;
    if ((type != 2) && (type != 7))
    {
      this.type = 0;
    } else if (type == 2) {
      d_mean = mean;
    } else {
      this.type = 3;
      c_low = 1.0D;
      c_high = 1.0E7D;
      initial_seed = mean;
      setPrivateSeed(mean);
    }
  }
  
  public Distribution(int type, int low, int high)
  {
    this.type = type;
    if (type != 4) {
      this.type = 0;
    } else {
      d_low = low;
      d_high = high;
    }
  }
  



  public Distribution(int type, String fn)
  {
    this.type = type;
    user_filename = fn;
    if (type != 8) {
      this.type = 0;
      return;
    }
    if (userlist == null) {
      this.type = 0;
      return;
    }
    for (int i = 0; i < userlist.size(); i++) {
      DistributionUser tdu = (DistributionUser)userlist.elementAt(i);
      if (fn.equals(fn)) {
        int num_with_name = tdu.getNum();
        if (num_with_name == 1) {
          du = tdu;
          return;
        }
        if (tdu.nextToGet()) {
          du = tdu;
          du.setNextToGet(false);
          tdu = (DistributionUser)userlist.elementAt(i + 1);
          tdu.setNextToGet(true);
          return;
        }
        for (int j = 1; j < num_with_name; j++) {
          tdu = (DistributionUser)userlist.elementAt(j + i);
          if (tdu.nextToGet()) {
            du = tdu;
            du.setNextToGet(false);
            if (j == num_with_name - 1) {
              tdu = (DistributionUser)userlist.elementAt(i);
            } else
              tdu = (DistributionUser)userlist.elementAt(i + j + 1);
            tdu.setNextToGet(true);
            return;
          }
        }
      }
    }
    this.type = 0;
  }
  
  public void resetNext() {
    DistributionUser tdu = null;
    int num_with_name = 1;
    

    if (type != 8) return;
    if (userlist == null) return;
    if (du == null) return;
    for (int i = 0; i < userlist.size(); i++) {
      tdu = (DistributionUser)userlist.elementAt(i);
      if (fn.equals(du.fn)) {
        num_with_name = tdu.getNum();
        break;
      }
    }
    if (num_with_name == 1) { return;
    }
    
    tdu.setNextToGet(true);
    for (int j = 1; j < num_with_name; j++) {
      tdu = (DistributionUser)userlist.elementAt(i + j);
      tdu.setNextToGet(false);
    }
  }
  



  public Distribution newUser()
  {
    if (type != 8) return this;
    if (du == null) return this;
    if (du.getNum() < 2) { return this;
    }
    Distribution td = new Distribution(type, user_filename);
    
    return td;
  }
  
  private static void make_user_if_necessary(String fn)
  {
    if (userlist == null) {
      makeUserDistribution(fn);
      return;
    }
    for (int i = 0; i < userlist.size(); i++) {
      DistributionUser du = (DistributionUser)userlist.elementAt(i);
      if (fn.equals(fn)) return;
    }
    makeUserDistribution(fn);
  }
  



  public static boolean makeUserDistribution(String fn)
  {
    int num_with_name = 0;
    


    String data = Jeli.UtilityIO.readEntireFile(fn);
    if (data == null) {
      return false;
    }
    if (userlist == null) userlist = new Vector();
    StringTokenizer stk = new StringTokenizer(data);
    try {
      while (stk.hasMoreTokens()) {
        num_with_name++;
        int len = Utility.parseInt(stk);
        double[] vals = new double[len];
        for (int i = 0; i < len; i++) {
          vals[i] = Utility.parseDouble(stk);
        }
        DistributionUser tdu = new DistributionUser(vals, fn, num_with_name);
        userlist.addElement(tdu);
      }
    }
    catch (NumberFormatException e) {
      System.out.println("Error in user distribution file: " + fn);
      return false;
    }
    int usersize = userlist.size();
    for (int i = 0; i < num_with_name; i++) {
      DistributionUser tdu = (DistributionUser)userlist.elementAt(usersize - i - 1);
      tdu.setNum(num_with_name);
      if (i == num_with_name - 1) {
        tdu.setNextToGet(true);
      } else
        tdu.setNextToGet(false);
    }
    return true;
  }
  
  public int getType() {
    return type;
  }
  
  public double getContMean() {
    return c_mean;
  }
  
  public double getContLow() {
    return c_low;
  }
  
  public double getContHigh() {
    return c_high;
  }
  
  public void setMean(double m) {
    c_mean = m;
  }
  
  public void setLow(double low) {
    c_low = low;
  }
  
  public void setHigh(double high) {
    c_high = high;
  }
  
  public int getDiscreteLow() {
    return d_low;
  }
  
  public int getDiscreteHigh() {
    return d_high;
  }
  








  public void resetSeed()
  {
    private_seed = initial_seed;
  }
  
  public void setPrivateSeed(double seed)
  {
    use_private_seed = true;
    initial_seed = seed;
    private_seed = seed;
  }
  







  public static void forceParseFloat(boolean f)
  {
    force_parse_float = f;
  }
  
  public static Distribution parseDistribution(String str) {
    return parseDistribution(new StringTokenizer(str));
  }
  
  public static Distribution parseDistribution(StringTokenizer stk, String str)
  {
    String standard_delims = " \t\n\r";
    String str1 = stk.nextToken(standard_delims);
    if (!str1.equals(str)) return null;
    return parseDistribution(stk);
  }
  




  public static Distribution parseDistribution(StringTokenizer stk)
  {
    String standard_delims = " \t\n\r";
    String str = stk.nextToken();
    try {
      if (str.equals("constant")) {
        str = stk.nextToken(standard_delims);
        double mean = Double.valueOf(str).doubleValue();
        if ((str.indexOf(".") == -1) && (!force_parse_float)) {
          return new Distribution(2, (int)mean);
        }
        return new Distribution(1, mean);
      }
      if (str.equals("uniform")) {
        str = stk.nextToken();
        double low = Double.valueOf(str).doubleValue();
        double high = Double.valueOf(stk.nextToken()).doubleValue();
        if ((str.indexOf(".") == -1) && (!force_parse_float)) {
          return new Distribution(4, (int)low, (int)high);
        }
        return new Distribution(3, low, high);
      }
      if (str.equals("exponential")) {
        str = stk.nextToken();
        double mean = Double.valueOf(str).doubleValue();
        return new Distribution(5, mean);
      }
      if (str.equals("user")) {
        str = stk.nextToken();
        make_user_if_necessary(str);
        return new Distribution(8, str);
      }
      return null;
    }
    catch (java.util.NoSuchElementException e) {
      return null;
    }
    catch (NumberFormatException e) {}
    return null;
  }
  


  private double nextSetValue()
  {
    Double val = (Double)nextValues.elementAt(0);
    nextValues.removeElementAt(0);
    return val.doubleValue();
  }
  
  public void forceValue(double val) {
    if (nextValues == null)
      nextValues = new Vector();
    nextValues.addElement(new Double(val));
  }
  
  public double next() {
    if ((type != 1) && (type != 2) && (type != 3) && (type != 4) && (type != 5) && (type != 8) && (type != 6))
    {





      return 0.0D; }
    if ((nextValues != null) && (nextValues.size() > 0))
      return nextSetValue();
    if (type == 8)
      return next_user_value();
    if (use_private_seed)
      return nextPrivate();
    if (type == 1)
      return c_mean;
    if (type == 2)
      return d_mean;
    if (type == 3)
      return Probability.uniform(c_low, c_high);
    if (type == 4)
      return Probability.discreteUniform(d_low, d_high);
    if (type == 5)
      return Probability.exponential(c_mean);
    if (type == 8) {
      return next_user_value();
    }
    return Probability.normal(c_mean, sd);
  }
  
  public double nextPrivate()
  {
    if (type == 1)
      return c_mean;
    if (type == 2)
      return d_mean;
    SeedVal sv; if (type == 3) {
      sv = Probability.uniformSeed(c_low, c_high, private_seed); } else { SeedVal sv;
      if (type == 4) {
        sv = Probability.discreteUniformSeed(d_low, d_high, private_seed); } else { SeedVal sv;
        if (type == 5) {
          sv = Probability.exponentialSeed(c_mean, private_seed); } else { SeedVal sv;
          if (type == 6) {
            sv = Probability.normalSeed(c_mean, sd, private_seed);
          } else {
            System.out.println("Private distirbution not supported: " + type);
            return 0.0D; } } } }
    SeedVal sv;
    private_seed = seed;
    return val;
  }
  
  public double next_user_value() {
    if (du == null) return 0.0D;
    next_user_entry += 1;
    if (next_user_entry >= du.vals.length)
      next_user_entry = 0;
    return du.vals[next_user_entry];
  }
  
  public double next(SeedVal sv)
  {
    if (sv == null) return next();
    if (type == 1)
      return c_mean;
    SeedVal sv1; SeedVal sv1; if (type == 3) {
      sv1 = Probability.uniformSeed(c_low, c_high, seed); } else { SeedVal sv1;
      if (type == 4) {
        sv1 = Probability.discreteUniformSeed(d_low, d_high, seed); } else { SeedVal sv1;
        if (type == 5) {
          sv1 = Probability.exponentialSeed(c_mean, seed);
        } else { if (type == 8) {
            return next_user_value();
          }
          sv1 = Probability.normalSeed(c_mean, sd, seed); } } }
    seed = seed;
    val = val;
    return val;
  }
  













  public String getDescriptor()
  {
    if (type == 0)
      return "unknown";
    if (type == 1)
      return "constant    " + Utility.n_places_right(c_mean, 2, 8);
    if (type == 2)
      return "constant    " + Utility.n_places_right(d_mean, 0, 8);
    if (type == 3) {
      return "uniform     " + Utility.n_places_right(c_low, 2, 8) + " " + Utility.n_places(c_high, 2);
    }
    if (type == 4)
      return "uniform     " + Utility.n_places_right(d_low, 0, 8) + " " + d_high;
    if (type == 5)
      return "exponential " + Utility.n_places_right(c_mean, 2, 8);
    if (type == 8)
      return "user " + user_filename + " " + du.getIndex();
    return "not done";
  }
  
  public String getDescriptorPrivate() {
    if (use_private_seed) return getDescriptor() + " ! " + initial_seed + " " + private_seed;
    return getDescriptor();
  }
  
  public String getShortDescriptor()
  {
    if (type == 0)
      return "unknown";
    if (type == 1)
      return "constant " + Utility.n_places(c_mean, 2);
    if (type == 2)
      return "constant    " + d_mean;
    if (type == 3) {
      return "uniform " + Utility.n_places(c_low, 2) + " " + Utility.n_places(c_high, 2);
    }
    if (type == 4)
      return "uniform " + Utility.n_places(d_low, 0) + " " + d_high;
    if (type == 5)
      return "exponential " + Utility.n_places(c_mean, 2);
    if (type == 8)
      return "user " + user_filename + " " + du.getIndex();
    return "not done";
  }
  
  public String getStateDescriptor()
  {
    if (!use_private_seed)
      return "Not using private seed";
    long current_seed = (10000.0D * private_seed);
    return "Private seed is " + current_seed;
  }
  
  public Distribution duplicate() {
    if (type == 0) return null;
    if (type == 1)
      return new Distribution(type, c_mean);
    if (type == 5)
      return new Distribution(type, c_mean);
    if (type == 3)
      return new Distribution(type, c_low, c_high);
    if (type == 4)
      return new Distribution(type, d_low, d_high);
    if (type == 8) {
      return new Distribution(type, user_filename);
    }
    return null;
  }
  

  public static String getUserlistDescriptor()
  {
    if (userlist == null) return "Not a user distribution";
    String str = "Number of user distributions: " + userlist.size() + "\n";
    for (int i = 0; i < userlist.size(); i++) {
      DistributionUser du = (DistributionUser)userlist.elementAt(i);
      str = str + "   " + du.getDescriptor() + "\n";
    }
    return str;
  }
  

  public double getChecksumValue()
  {
    double val = 234.56D * type + 987.457D * c_mean + 493.476D * sd + 279.693D * c_low + 725.851D * c_high + 301.583D * d_mean + 593.581D * d_low + 521.593D * d_high + 0.123453D * private_seed + 0.2763174D * initial_seed + 164.384D * next_user_entry;
    



    if (user_filename != null)
      for (int i = 0; i < user_filename.length(); i++)
        val += 234.294D * user_filename.charAt(i);
    if (du != null)
      val += du.getChecksumValue();
    return val;
  }
}
