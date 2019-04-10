package Jeli.LocalFiles;

import Jeli.UtilityIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;


class DirToFile
{
  DirToFile() {}
  
  private static byte[] string_to_byte_array(String str)
  {
    int n = str.length();
    byte[] b = new byte[n];
    for (int i = 0; i < n; i++)
      b[i] = ((byte)str.charAt(i));
    return b;
  }
  





  public static void main(String[] args)
  {
    char quotechar = '"';
    


    if (args.length != 3) {
      System.out.println("Usage: java Jeli.DirToFile dirlocation dir fn\n");
      
      return;
    }
    String dirlocation = args[0];
    String dirname = args[1];
    String outfile = args[2];
    System.out.println("DirTofile for directory " + dirlocation);
    
    File dir = new File(dirlocation);
    String[] ls = dir.list();
    FileOutputStream fis;
    try {
      fis = new FileOutputStream(outfile);
    }
    catch (IOException e) {
      System.out.println("Error creating file " + outfile);
      return;
    }
    
    for (int i = 0; i < ls.length; i++) {
      byte[] filedata = UtilityIO.fileToByteArray(dirlocation + "/" + ls[i]);
      String heading = dirname + "/" + ls[i] + " " + filedata.length + "\n";
      try {
        fis.write(string_to_byte_array(heading));
        fis.write(filedata);
      }
      catch (IOException e) {
        System.out.println("Error writing to file " + outfile);
        return;
      }
    }
    try {
      fis.close();
    }
    catch (IOException e) {
      System.out.println("Error closing file " + outfile);
      return;
    }
  }
}
