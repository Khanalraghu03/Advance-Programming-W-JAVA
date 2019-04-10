import java.io.PrintStream;

public class AddressVersion implements Jeli.JeliApplicationVersion { public static final String versionMessageShort = "1.03";
  public static final String versionMessageUpdate = "January 2, 2007";
  public static final int minimalJeliVersion = 278;
  public static final String name = "Address Translation";
  
  public AddressVersion() {}
  
  public static void main(String[] args) { System.out.println("Address Translation, version 1.03");
    System.out.println("Last updated January 2, 2007");
    System.out.println("Requires Jeli version 278 or later");
  }
  
  public String getName()
  {
    return "Address Translation";
  }
  
  public String getVersionMessageShort() {
    return "1.03";
  }
  
  public String getVersionMessageUpdate() {
    return "January 2, 2007";
  }
  
  public int getMinimalJeliVersion() {
    return 278;
  }
}
