package Jeli;

import java.io.PrintStream;

public class ConvertFile
{
  public ConvertFile() {}
  
  public static void main(String[] args) {
    String fullVersionMessage = "Java Electronic Laboratory Interface (Jeli)\nversion 288 last updated March 1, 2007";
    


    String usage = "usage: java ConvertFile [-u -w] fromfile1 fromfile2 ...";
    
    int count = 0;
    



    if (args.length < 2) {
      System.out.println(usage);
      System.exit(0);
    }
    if (args[0].equalsIgnoreCase("-u")) {
      System.out.println("Converting to Unix format ... ");
      for (int i = 1; i < args.length; i++) {
        if (convertToUnix(args[i], args[i]))
          count++;
      }
    } else if (args[0].equalsIgnoreCase("-w")) {
      System.out.println("Converting to Windows format ... ");
      for (int i = 1; i < args.length; i++) {
        if (convertToWin(args[i], args[i]))
          count++;
      }
    } else {
      System.out.println(usage); }
    if (count > 0) {
      if (count == args.length - 1) {
        System.out.println("All files converted\n");
      } else {
        System.out.println("Number of files converted out of " + (args.length - 1) + " is " + count);
      }
    }
  }
  
  public static boolean convertToUnix(String fn1, String fn2) {
    byte[] in = UtilityIO.readEntireFileBytes(fn1);
    
    int count = 0;
    
    boolean changed = false;
    
    if (in == null) {
      System.out.println("Error reading input file " + fn1);
      return false;
    }
    byte[] out = new byte[in.length];
    for (int i = 0; i < in.length; i++)
      if (in[i] == 13) {
        changed = true;
        if ((i >= in.length - 1) || (in[(i + 1)] != 10))
        {
          out[(count++)] = 10;
        }
      } else {
        out[(count++)] = in[i]; }
    if (changed) {
      boolean success = saveInFile(fn2, out, count);
      if (success) {
        System.out.println("File " + fn1 + " of size " + in.length + " converted to " + fn2 + " of size " + count);
      }
      else
        System.out.println("Error saving file " + fn2);
      return success;
    }
    System.out.println("File " + fn1 + " unchanged.");
    return true;
  }
  
  public static boolean convertToWin(String fn1, String fn2) {
    byte[] in = UtilityIO.readEntireFileBytes(fn1);
    
    int count = 0;
    
    boolean changed = false;
    


    if (in == null) {
      System.out.println("Error reading input file " + fn1);
      return false;
    }
    byte[] out = new byte[2 * in.length];
    for (int i = 0; i < in.length; i++)
      if ((in[i] == 10) && ((i == 0) || (in[(i - 1)] != 13))) {
        out[(count++)] = 13;
        out[(count++)] = 10;
        changed = true;
      }
      else {
        out[(count++)] = in[i]; }
    if (changed) {
      boolean success = saveInFile(fn2, out, count);
      if (success) {
        System.out.println("File " + fn1 + " of size " + in.length + " converted to " + fn2 + " of size " + count);
      }
      else
        System.out.println("Error saving file " + fn2);
      return success;
    }
    System.out.println("File " + fn1 + " unchanged.");
    return true;
  }
  
  private static boolean saveInFile(String filename, byte[] b, int count)
  {
    java.io.FileOutputStream myout = null;
    success = true;
    try {
      myout = new java.io.FileOutputStream(filename);
      
      myout.write(b, 0, count);
      












      return success;
    }
    catch (java.io.IOException e)
    {
      success = false;
    } finally {
      try {
        if (myout != null)
          myout.close();
      } catch (java.io.IOException e1) {
        success = false;
      }
    }
  }
}
