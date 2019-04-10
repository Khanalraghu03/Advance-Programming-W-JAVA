package Jeli.Logging;

import Jeli.RemoteLog.IDSHandle;
import java.awt.Image;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;

public class PLogger
{
  private String dir;
  private String fn;
  private String name;
  private String smname;
  private FileOutputStream myout;
  private java.io.FileInputStream myin;
  private boolean logger_open_flag;
  private boolean checksumflag;
  private int reopen_count = 0;
  private int remote_port = 0;
  private String remote_directory = null;
  private DataOutputStream writer;
  private String host = null;
  private IDSHandle handle;
  public static int force_remote_port = -1;
  
  public PLogger(String dir, String fn, String name, String smname) {
    this(dir, fn, name, smname, false, false, 0);
  }
  



  public PLogger(String dir, String fn, String name, String smname, boolean checksumflag, boolean appendflag, int remoteport)
  {
    int fsize = 0;
    
    byte[] buffer = null;
    



    remote_port = remoteport;
    


    this.dir = dir;
    this.fn = fn;
    this.name = name;
    this.smname = smname;
    this.checksumflag = checksumflag;
    logger_open_flag = false;
    if (Jeli.UtilityIO.getRemotePort() > 0)
      remote_port = Jeli.UtilityIO.getRemotePort();
    if (remote_port > 0) {
      setupRemotePort();
      return;
    }
    String filename = dir + fn;
    if (appendflag) {
      try {
        java.io.File f = new java.io.File(filename);
        java.io.FileInputStream fis = new java.io.FileInputStream(f);
        
        fsize = (int)f.length();
        buffer = new byte[fsize];
        int bytesread = fis.read(buffer);
        if (fsize != bytesread) {
          buffer = null;
        } else {
          String buffer_string = new String(buffer);
          int reopened_index = buffer_string.lastIndexOf("reopened(");
          if (reopened_index >= 0) {
            int reopened_end_index = buffer_string.indexOf(")", reopened_index);
            
            if (reopened_end_index >= 0) {
              String reopened_count_string = buffer_string.substring(reopened_index + 9, reopened_end_index);
              

              reopen_count = Integer.parseInt(reopened_count_string);
            }
          }
        }
      }
      catch (Exception e) {
        System.out.println("Log file: " + filename + " cannot be read");
      }
    }
    try
    {
      myout = new FileOutputStream(filename);
    }
    catch (IOException e) {
      System.out.println("IO Error opening file: " + filename);
      return;
    }
    catch (Exception e1) {
      System.out.println("Cannot open file: " + filename);
      return;
    }
    if (buffer == null) {
      write_html_header();
    } else {
      try {
        myout.write(buffer);
      }
      catch (IOException e) {
        System.out.println("IO Error rewriting file");
        return;
      }
      write_html_reopened();
    }
    logger_open_flag = true;
  }
  
  public String getHost() {
    return host;
  }
  














  private void setupRemotePort()
  {
    host = Jeli.UtilityIO.getRemoteHost();
    if (host == null) return;
    try {
      handle = new IDSHandle(dir, host, remote_port);
    } catch (Jeli.RemoteLog.IDSException e1) {
      return;
    } catch (IOException e2) { return;
    }
    writer = handle.open(fn);
    if (writer == null) return;
    write_html_header();
    logger_open_flag = true;
  }
  































  public int getReopenCount()
  {
    return reopen_count;
  }
  
  public boolean getOpenFlag()
  {
    return logger_open_flag;
  }
  
  public boolean closeLogger() {
    try {
      write_html_trailer();
      if (remote_port > 0) {
        writer.close();
      } else
        myout.close();
      return true;
    } catch (IOException e) {}
    return false;
  }
  
  public void writeLogger(String str) {
    try {
      if (remote_port > 0) {
        writer.write(string_to_byte_array_line(str));
      }
      else
      {
        myout.write(string_to_byte_array_line(str));
      }
    } catch (IOException e) {}
  }
  
  public void saveImage(Image im, String fn) {
    saveImage(im, fn, null);
  }
  

  public void saveImage(Image im, String fn, SaveImageInfo sio)
  {
    GIFEncoder encoder;
    try
    {
      encoder = new GIFEncoder(im);
    }
    catch (java.awt.AWTException e) {
      System.out.println("Error encoding gif: " + e.getMessage());
      if (sio != null)
        sio.saveImageFinished(false);
      return;
    }
    try { java.io.OutputStream mout;
      if (remote_port > 0)
      {

        java.io.OutputStream mout = handle.open(fn);
        if (mout == null) {
          System.out.println("Error opening remote file: " + fn);
          if (sio != null) {
            sio.saveImageFinished(false);
          }
        }
      }
      else
      {
        mout = new FileOutputStream(fn);
      }
      encoder.writeOutput(mout);
      mout.close();
      if (sio != null) {
        sio.saveImageFinished(true);
      }
    } catch (IOException e) {
      System.out.println("There was an error writing gif");
      if (sio != null) {
        sio.saveImageFinished(false);
      }
    }
  }
  


























  private boolean saveInFileRemote(String filename, String str)
  {
    if (handle == null) return false;
    DataOutputStream writer = handle.open(filename);
    if (writer == null) return false;
    try {
      writer.write(Jeli.UtilityIO.string_to_byte_array(str));
      writer.close();
    }
    catch (IOException e) {
      return false;
    }
    System.out.println("  It seems like this worked");
    return true;
  }
  




























  private byte[] string_to_byte_array_line(String str)
  {
    int n = str.length();
    byte[] b = new byte[n + 1];
    for (int i = 0; i < n; i++)
      b[i] = ((byte)str.charAt(i));
    b[n] = 10;
    return b;
  }
  
  private void write_checksum(String s, String n) {
    writeLogger("<H6>" + s + "<br>\n" + "Java version " + System.getProperty("java.version") + " OS " + System.getProperty("os.name") + " version " + System.getProperty("os.version") + " " + Jeli.Utility.createCheckSumString(n) + "\n<br></H6>\n");
  }
  






  private void write_html_header()
  {
    Date date = new Date();
    String n = name + " generated " + date.toString();
    writeLogger("<HEAD>");
    writeLogger("<TITLE>" + n + "</TITLE>");
    writeLogger("</HEAD>");
    writeLogger("<BODY BGCOLOR=#FFFFFF><H3>" + n + "</H3>\n");
    if (checksumflag) {
      write_checksum(smname, n);
    } else
      writeLogger("<H6>" + smname + "</H6>\n");
    writeLogger("<hr>\n");
  }
  

  private void write_html_reopened()
  {
    Date date = new Date();
    reopen_count += 1;
    String n = name + " reopened(" + reopen_count + ") " + date.toString();
    writeLogger("<H3>" + n + "</H3>\n");
    if (checksumflag) {
      write_checksum(smname, n);
    } else
      writeLogger("<H6>" + smname + "</H6>\n");
    writeLogger("<hr>\n");
  }
  

  private void write_html_trailer()
  {
    Date date = new Date();
    String n = name + " completed " + date.toString();
    writeLogger("<hr>");
    writeLogger("<H2>" + n + "</H2>");
    writeLogger("<hr>");
  }
  
  public String getDirectory()
  {
    if (handle == null) return null;
    return handle.getDirectory();
  }
  
  public String getHTMLBase() {
    if (handle == null) return null;
    return handle.getHTMLBase();
  }
  
  public String getFilename() {
    return fn;
  }
}
