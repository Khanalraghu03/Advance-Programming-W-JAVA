package Jeli.LocalFiles;

import Jeli.UtilityIO;
import java.io.File;
import java.io.PrintStream;
import java.util.Vector;




class DirToJava
{
  DirToJava() {}
  
  public static void main(String[] args)
  {
    char quotechar = '"';
    String heading;
    if (args[0].length() > 0) {
      heading = "package " + args[0] + ";\n\n";
    } else
      heading = "";
    String heading = heading + "import Jeli.*;\n\n   public class " + args[3] + " implements LocalFiles {\n\n" + "      LocalFile[] filelist;\n";
    

    Vector filedata = new Vector();
    String dirlocation = args[1];
    System.out.println("DirToJava for directory " + dirlocation);
    



    File dir = new File(dirlocation);
    String[] ls = dir.list();
    
    for (int i = 0; i < ls.length; i++)
    {
      String test = "      final static byte[] valuelist" + i + " = {" + UtilityIO.fileToDataString(new StringBuffer().append(args[2]).append("/").append(ls[i]).toString()) + "};\n";
      
      filedata.addElement(test);
    }
    heading = heading + "\n";
    for (int i = 0; i < filedata.size(); i++) {
      heading = heading + (String)filedata.elementAt(i);
    }
    
    heading = heading + "\n      public DataFiles() {\n         " + "filelist = new LocalFile[" + filedata.size() + "];\n";
    
    for (int i = 0; i < filedata.size(); i++) {
      heading = heading + "         filelist[" + i + "] = new LocalFile(" + quotechar + args[2] + "/" + ls[i] + quotechar + ",valuelist" + i + ");\n";
    }
    
    heading = heading + "      }\n\n";
    

    heading = heading + "      public String GetLocalFile(String name) {\n" + "         for (int i=0;i<filelist.length;i++)\n" + "            if (name.equals(filelist[i].name))\n" + "               return new String(filelist[i].val);\n" + "         return null;\n" + "      }\n" + "   }\n";
    






    UtilityIO.saveInFile(args[3] + ".java", heading);
  }
}
