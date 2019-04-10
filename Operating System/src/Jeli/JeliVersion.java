package Jeli;

import java.io.PrintStream;

public class JeliVersion {
  public static final int versionNumber = 288;
  public static final String version = "L288";
  public static final String last_update = "March 1, 2007";
  
  public JeliVersion() {}
  
  public static void main(String[] args) {
    System.out.println(fullVersionMessage());
    System.exit(0);
  }
  
  public static String fullVersionMessage() {
    String fullVersionMessage = "Java Electronic Laboratory Interface (Jeli)\nversion 288 last updated March 1, 2007";
    


    return fullVersionMessage;
  }
}
