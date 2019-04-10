package Jeli;

import Jeli.Widgets.HelpManager;
import java.applet.Applet;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StreamTokenizer;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;

public class UtilityIO
{
  private static final int BUFFERSIZE = 256;
  private static boolean localInhibit = false;
  private static String packageName = "";
  
  public UtilityIO() {}
  
  private static boolean lastReadLocalFlag = false;
  
  private static boolean quietFailureFlag = false;
  private static Applet defaultApplet = null;
  private static URL defaultCodeBase = null;
  private static java.applet.AudioClip goodClip;
  private static java.applet.AudioClip badClip;
  private static java.applet.AudioClip clickClip;
  private static String filesep = null;
  private static String searchHistory = "";
  private static Jeli.LocalFiles.LocalFiles localFiles = null;
  private static java.applet.AppletContext defaultAppletContext = null;
  private static boolean promptForMissingFileFlag = false;
  private static boolean editFileFlag = false;
  private static FilePrompter filePrompt = null;
  private static boolean clickPlayed = false;
  private static boolean goodPlayed = false;
  private static boolean badPlayed = false;
  private static String remoteHost = null;
  private static int remotePort = -1;
  private static Jeli.Widgets.JeliTextList textList = null;
  private static int audioLoadType = 0;
  private static boolean debugIO = false;
  private static Class resourceClass = UtilityIO.class;
  private static String forceBrowser = null;
  
  public static boolean getLastReadLocalFlag() {
    return lastReadLocalFlag; }
  
  public static void setResourceClass(Class rc)
  {
    resourceClass = rc;
  }
  
  public static void setDebugIO(boolean f) {
    debugIO = f;
  }
  
  private static void IODebugString(String s) {
    if (debugIO)
      debugOutput(s);
  }
  
  public static boolean getDebugIO() {
    return debugIO;
  }
  
  public static String getRemoteHost() {
    if (remoteHost != null) return remoteHost;
    if (defaultCodeBase == null) return null;
    return defaultCodeBase.getHost();
  }
  
  public static void setRemoteHost(String str, int port) {
    remoteHost = str;
    remotePort = port;
  }
  
  public static int getRemotePort() {
    return remotePort;
  }
  
  public static int getAudioLoadType() {
    return audioLoadType;
  }
  
  public static void setTextList(Jeli.Widgets.JeliTextList textList1) {
    textList = textList1;
  }
  
  public static Jeli.Widgets.JeliTextList getTextList() {
    return textList;
  }
  
  public static void textOutputIncrementLevel(int n) {
    if (textList == null) return;
    textList.incrementLevel(n);
  }
  
  public static void debugOutputLine() {
    if (textList == null) {
      System.out.println();
    } else {
      textList.appendLineIfNecessary();
    }
  }
  
  public static void debugOutput(String str) {
    if (textList == null) {
      System.out.println(str);
    } else
      textList.append(str + "\n");
  }
  
  public static void textOutput(String str) {
    if (textList == null) return;
    textList.append(str);
  }
  
  public static void textOutput(String str, java.awt.Color c)
  {
    if (textList == null) return;
    java.awt.Color oldColor = textList.getForegroundColor();
    textList.setForegroundColor(c);
    textList.append(str);
    textList.setForegroundColor(oldColor);
  }
  






  public static void setFilePrompt(boolean f, HelpManager hm)
  {
    if (f) {
      if (filePrompt == null)
        filePrompt = new FilePrompter(hm);
      promptForMissingFileFlag = true;
    }
    else {
      promptForMissingFileFlag = false;
    }
  }
  
  public static void setEditFile(boolean f, HelpManager hm) { if (f) {
      if (filePrompt == null)
        filePrompt = new FilePrompter(hm);
      editFileFlag = true;
    }
    else {
      editFileFlag = false;
    }
  }
  
  public static URL getDefaultCodeBase() { return defaultCodeBase; }
  
  public static java.applet.AppletContext getAppletContext()
  {
    return defaultAppletContext;
  }
  
  public static String getAudioInfo() {
    return searchHistory;
  }
  
  public static String getFileSeparator() {
    set_file_sep();
    return filesep;
  }
  
  public static void forceFileSeparator(String s) {
    filesep = s;
  }
  
  public static void set_file_sep() {
    if (filesep != null) return;
    filesep = System.getProperty("file.separator");
  }
  
  public static void quietFailure(boolean f) {
    quietFailureFlag = f;
  }
  
  public static void setLocalFiles(Jeli.LocalFiles.LocalFiles lf) {
    localFiles = lf;
  }
  
  public static void inhibitLocal() {
    localInhibit = true;
  }
  
  public static void setPackage(String str) {
    packageName = str;
  }
  
  public static void setApplet(Applet ap) {
    defaultApplet = ap;
    if (ap != null) {
      defaultCodeBase = ap.getCodeBase();
      defaultAppletContext = ap.getAppletContext();
    }
  }
  
  private static void set_history(String str, boolean h) {
    if (h)
      searchHistory = str;
  }
  
  private static void append_history(String str, boolean h) {
    if (h) {
      searchHistory += str;
    }
  }
  
  private static java.io.InputStream search_for_converted_file(String fn, boolean hist) {
    java.io.InputStream input_stream = null;
    
    URL url = null;
    

    byte[] first_byte = new byte[1];
    append_history("Starting search for " + fn + "\n", hist);
    if (defaultApplet == null) {
      append_history("   No defualt applet\n", hist);
      return null;
    }
    append_history("  Using codebase " + defaultCodeBase + "\n", hist);
    try {
      url = new URL(defaultCodeBase, fn);
    }
    catch (java.net.MalformedURLException e) {
      append_history("  URL exception\n", hist);
    }
    if (url == null) {
      append_history("  bad URL\n", hist);
      return null;
    }
    try {
      input_stream = url.openStream();
    }
    catch (IOException e) {
      append_history("  Open Stream exception\n", hist);
      return null;
    }
    if (input_stream == null) {
      append_history("  Open Stream error\n", hist);
      return null;
    }
    append_history("  OK\n", hist);
    return input_stream;
  }
  

  public static java.io.InputStream search_for_file(String fn, boolean hist)
  {
    String converted_fn = convertPackageName(fn);
    set_history("", hist);
    java.io.InputStream is = search_for_converted_file(converted_fn, hist);
    if (is != null) return is;
    converted_fn = fn;
    is = search_for_converted_file(converted_fn, hist);
    if (is != null)
      return is;
    converted_fn = convertPackageNameSep(fn);
    is = search_for_converted_file(converted_fn, hist);
    return is;
  }
  

  public static String search_for_filename(String fn, boolean hist)
  {
    String converted_fn = convertPackageName(fn);
    set_history("", hist);
    java.io.InputStream is = search_for_converted_file(converted_fn, hist);
    if (is != null) {
      try {
        is.close();
      }
      catch (IOException e) {}
      return converted_fn;
    }
    converted_fn = fn;
    is = search_for_converted_file(converted_fn, hist);
    if (is != null) {
      try {
        is.close();
      }
      catch (IOException e) {}
      return converted_fn;
    }
    converted_fn = convertPackageNameSep(fn);
    is = search_for_converted_file(converted_fn, hist);
    if (is != null) {
      try {
        is.close();
      }
      catch (IOException e) {}
      return converted_fn;
    }
    return null;
  }
  
  public static void loadSounds(HelpManager hm)
  {
    if (defaultApplet == null) { return;
    }
    String sounds_fn = search_for_filename("good.au", true);
    
    if (sounds_fn != null) {
      goodClip = defaultApplet.getAudioClip(defaultCodeBase, sounds_fn);
    }
    sounds_fn = search_for_filename("bad.au", false);
    if (sounds_fn != null)
      badClip = defaultApplet.getAudioClip(defaultCodeBase, sounds_fn);
    sounds_fn = search_for_filename("click.au", false);
    if (sounds_fn != null) {
      clickClip = defaultApplet.getAudioClip(defaultCodeBase, sounds_fn);
    }
    if (goodClip == null) {
      sounds_fn = packageName + "/" + "good.au";
      goodClip = defaultApplet.getAudioClip(defaultCodeBase, sounds_fn);
    }
    

    if (clickClip == null) {
      sounds_fn = packageName + "/" + "click.au";
      clickClip = defaultApplet.getAudioClip(defaultCodeBase, sounds_fn);
    }
    if (badClip == null) {
      sounds_fn = packageName + "/" + "bad.au";
      badClip = defaultApplet.getAudioClip(defaultCodeBase, sounds_fn);
    }
    hm.setAudioClick(clickClip);
    hm.setAudioGood(goodClip);
    hm.setAudioBad(badClip);
  }
  
  public static void loadSoundsNosearch(HelpManager hm) {
    loadSoundsNosearch(hm, true);
  }
  
  public static void loadSoundsQuick() {
    if (loadSoundsResource())
      return;
    System.out.println("loadSoundsQuick failed");
  }
  












  public static boolean loadSoundsResource()
  {
    if (Utility.hm == null)
      return false;
    try {
      URL url1 = UtilityIO.class.getResource("good.au");
      URL url2 = UtilityIO.class.getResource("click.au");
      URL url3 = UtilityIO.class.getResource("bad.au");
      if ((url1 != null) && (url2 != null) && (url3 != null)) {
        goodClip = Applet.newAudioClip(url1);
        clickClip = Applet.newAudioClip(url2);
        badClip = Applet.newAudioClip(url3);
        audioLoadType = 1;
      }
      else {
        URL url = new URL("file:good.au");
        goodClip = Applet.newAudioClip(url);
        url = new URL("file:click.au");
        clickClip = Applet.newAudioClip(url);
        url = new URL("file:bad.au");
        badClip = Applet.newAudioClip(url);
        audioLoadType = 2;
      }
      Utility.hm.setAudioClick(clickClip);
      Utility.hm.setAudioGood(goodClip);
      Utility.hm.setAudioBad(badClip);
      return true;
    }
    catch (Exception e) {
      audioLoadType = 3;
    }
    catch (NoSuchMethodError e) {
      audioLoadType = 4;
    }
    return false;
  }
  





  public static void loadSoundsNosearch(HelpManager hm, boolean msgFlag)
  {
    if (defaultApplet == null) {
      try {
        URL url1 = UtilityIO.class.getResource("good.au");
        URL url2 = UtilityIO.class.getResource("click.au");
        URL url3 = UtilityIO.class.getResource("bad.au");
        if ((url1 != null) && (url2 != null) && (url3 != null)) {
          goodClip = Applet.newAudioClip(url1);
          clickClip = Applet.newAudioClip(url2);
          badClip = Applet.newAudioClip(url3);
          audioLoadType = 1;
        }
        else {
          URL url = new URL("file:good.au");
          goodClip = Applet.newAudioClip(url);
          url = new URL("file:click.au");
          clickClip = Applet.newAudioClip(url);
          url = new URL("file:bad.au");
          badClip = Applet.newAudioClip(url);
          audioLoadType = 2;
        }
      }
      catch (Exception e) {
        if (msgFlag)
          System.out.println("Bad URL");
        audioLoadType = 3;
      }
      catch (NoSuchMethodError e) {
        if (msgFlag) {
          System.out.println("Sound from application only supported in Java 2");
        }
        audioLoadType = 4;
      }
      hm.setAudioClick(clickClip);
      hm.setAudioGood(goodClip);
      hm.setAudioBad(badClip);
      return;
    }
    
    if (defaultApplet == null) return;
    try {
      goodClip = defaultApplet.getAudioClip(defaultCodeBase, "good.au");
      clickClip = defaultApplet.getAudioClip(defaultCodeBase, "click.au");
      badClip = defaultApplet.getAudioClip(defaultCodeBase, "bad.au");
    }
    catch (NullPointerException e) {
      System.out.println("Null pointer exception setting audio clip");
      audioLoadType = 6;
      return;
    }
    hm.setAudioClick(clickClip);
    hm.setAudioGood(goodClip);
    hm.setAudioBad(badClip);
    audioLoadType = 5;
  }
  
  public static void playSounds() {
    playClick();
    playGood();
    playBad();
  }
  
  public static void playSoundsIfNecessary() {
    if (!clickPlayed) playClick();
    if (!goodPlayed) playGood();
    if (!badPlayed) playBad();
  }
  
  public static void playClick() {
    if (clickClip != null) {
      clickPlayed = true;
      clickClip.play();
    }
  }
  
  public static void playGood() {
    if (goodClip != null) {
      goodPlayed = true;
      goodClip.play();
    }
  }
  
  public static void playBad() {
    if (badClip != null) {
      badPlayed = true;
      badClip.play();
    }
  }
  
  public static java.applet.AudioClip getAudioClip(String filename)
  {
    if (defaultApplet == null) return null;
    String name = convertPackageName(filename);
    return defaultApplet.getAudioClip(defaultCodeBase, name);
  }
  

  public static byte[] readEntireFileBytes(String name)
  {
    IODebugString("Entered readEntireFileBytes for " + name);
    if (!localInhibit) {
      IODebugString("  Calling readEntireFileLocalBytes for " + name);
      byte[] b = readEntireFileLocalBytes(name);
      if (b != null) {
        IODebugString("   Entire file bytes read locally for " + name);
        return b;
      }
    }
    IODebugString("  Entire file bytes remote read using: " + defaultApplet);
    String str = readEntireFileRemote(name, defaultApplet);
    

    if (str == null)
      return null;
    IODebugString("   Entire file bytes read remotely: " + str.length());
    byte[] b = new byte[str.length()];
    for (int i = 0; i < str.length(); i++)
      b[i] = ((byte)str.charAt(i));
    return b;
  }
  
  private static String readEntireFileAsResource(String name)
  {
    java.io.InputStream ins = resourceClass.getResourceAsStream(name);
    if (ins == null)
      return null;
    IODebugString("  Found file " + name + " as resource");
    return readEntireFile(ins);
  }
  
  public static String readEntireFile(String name) {
    IODebugString("In readEntireFile for " + name);
    return readEntireFile(name, defaultApplet);
  }
  
  public static String readEntireFile(String name, boolean edit_flag) {
    IODebugString("In readEntireFile for " + name + " with edit_flag " + edit_flag);
    
    return readEntireFile(name, defaultApplet, edit_flag);
  }
  
  public static String readEntireFile(String name, Applet ap) {
    IODebugString("In readEntireFile Applet for " + name);
    return readEntireFile(name, ap, editFileFlag);
  }
  

  public static String readEntireFile(String name, Applet ap, boolean edit_flag)
  {
    lastReadLocalFlag = false;
    IODebugString("In readEntireFile Applet for " + name + " with edit " + edit_flag);
    
    if (localFiles != null) {
      String str = localFiles.getLocalFile(name);
      if (str != null) {
        IODebugString("Found Local File " + name);
        if (edit_flag)
          str = editFile(name, str);
        return str;
      }
    }
    if (!localInhibit) {
      String str = readEntireFileLocal(name);
      if (str != null) {
        IODebugString("Found file locally: " + name);
        if (edit_flag)
          str = editFile(name, str);
        return str;
      }
    }
    IODebugString("Trying readEntireFileRemote: " + name);
    String str = readEntireFileRemote(name, ap);
    if (str != null) {
      if (edit_flag)
        str = editFile(name, str);
      return str;
    }
    if (str != null)
      return str;
    str = readEntireFileAsResource(name);
    IODebugString("Trying in resource: " + name);
    if (str != null)
      return str;
    IODebugString("Trying promptForFile: " + name);
    str = promptForFile(name);
    return str;
  }
  
  private static String readEntireFileLocal(String name)
  {
    IODebugString("Trying readEntireFileLocal: " + name);
    byte[] b = readEntireFileLocalBytes(name);
    if (b == null) return null;
    return new String(b);
  }
  





  private static byte[] readEntireFileLocalBytes(String name)
  {
    java.io.File f = new java.io.File(name);
    IODebugString("Trying to open file " + f + " in readEntireFileLocalBytes");
    java.io.FileInputStream fis;
    try { fis = new java.io.FileInputStream(f);
    }
    catch (java.io.FileNotFoundException e)
    {
      IODebugString("   Cannot open file " + f + " in readEntireFileLocalBytes");
      return null;
    }
    catch (Exception e)
    {
      IODebugString("   General cannot open file " + f + " in readEntireFileLocalBytes");
      
      return null;
    }
    int fsize = (int)f.length();
    byte[] buffer = new byte[fsize];
    int bytesread;
    try { bytesread = fis.read(buffer);
    }
    catch (IOException e) {
      IODebugString("   Error reading file " + f + " in readEntireFileLocalBytes");
      
      return null;
    }
    finally {
      try {
        fis.close();
      }
      catch (IOException e) {
        IODebugString("   Error closing file " + f + " in readEntireFileLocalBytes");
      }
      
      IODebugString("   Closed file " + f + " in readEntireFileLocalBytes");
    }
    if (fsize != bytesread) {
      IODebugString("   Wrong number of bytes read for file " + f + ": read " + bytesread + " out of " + fsize);
      
      return null;
    }
    lastReadLocalFlag = true;
    IODebugString("   Success reading file " + f + " in readEntireFileLocalBytes");
    
    return buffer;
  }
  





  public static byte[] fileToByteArray(String name)
  {
    java.io.File f = new java.io.File(name);
    IODebugString("Trying to open file " + f + " in fileToByteArray");
    java.io.FileInputStream fis;
    try { fis = new java.io.FileInputStream(f);
    }
    catch (java.io.FileNotFoundException e)
    {
      IODebugString("   Cannot open file " + f + " in fileToByteArray");
      return null;
    }
    catch (Exception e)
    {
      IODebugString("   General cannot open file " + f + " in fileToByteArray");
      return null;
    }
    int fsize = (int)f.length();
    byte[] buffer = new byte[fsize];
    int bytesread;
    try { bytesread = fis.read(buffer);
    }
    catch (IOException e) {
      IODebugString("   Error reading file " + f + " in fileToByteArray");
      return null;
    }
    finally {
      try {
        fis.close();
      }
      catch (IOException e) {
        IODebugString("   Error closing file " + f + " in fileToByteArray");
      }
      IODebugString("   Closed file " + f + " in fileToByteArray");
    }
    if (fsize != bytesread) {
      IODebugString("Wrong number of bytes read for file " + f + ": read " + bytesread + " out of " + fsize);
      
      return null;
    }
    return buffer;
  }
  


  private static String readEntireFileRemote(String name, Applet ap)
  {
    defaultApplet = ap;
    set_file_sep();
    
    IODebugString("Searching for file " + name + " in readEntireFileRemote");
    java.io.InputStream myin = search_for_file(name, false);
    if (myin == null) return null;
    IODebugString("   Found file " + name + " in readEntireFileRemote");
    String str = readEntireFile(myin);
    return str;
  }
  
  public static String convertPackageName(Applet ap, String name) {
    
    if (packageName == null) return name;
    if (packageName.length() == 0) return name;
    if (ap.getCodeBase().toString().startsWith("file")) {
      return name;
    }
    return packageName + filesep + name;
  }
  
  public static String convertPackageNameSep(String name) {
    if (packageName == null) return name;
    if (packageName.length() == 0) return name;
    if (defaultApplet == null) return name;
    if (defaultApplet.getCodeBase().toString().startsWith("file")) {
      return name;
    }
    return packageName + "/" + name;
  }
  
  public static String convertPackageName(String name) {
    return convertPackageName(defaultApplet, name);
  }
  







  private static String readEntireFile(java.io.InputStream ins)
  {
    int total_bytes_read = 0;
    byte[] buffer = new byte['Ā'];
    IODebugString("Using readEntireFile with InputStream");
    for (;;) {
      int space_left = buffer.length - total_bytes_read;
      if (space_left == 0) {
        byte[] buffer1 = new byte[2 * buffer.length];
        for (int i = 0; i < buffer.length; i++)
          buffer1[i] = buffer[i];
        buffer = buffer1;
        space_left = buffer.length - total_bytes_read;
      }
      int bytes_read;
      try { bytes_read = ins.read(buffer, total_bytes_read, space_left);
      }
      catch (IOException e) {
        IODebugString("IOException in readEntireFile with InputStream");
        try {
          ins.close();
        }
        catch (IOException e1) {}
        return null;
      }
      if (bytes_read == -1) break;
      total_bytes_read += bytes_read;
    }
    try
    {
      ins.close();
    }
    catch (IOException e) {
      IODebugString("IOException closing in readEntireFile with InputStream");
      return null;
    }
    if (total_bytes_read == 0) return null;
    return new String(buffer, 0, total_bytes_read);
  }
  



  public static double readNumber(StreamTokenizer stk, String str)
  {
    try
    {
      stk.nextToken();
      if (ttype != -3)
      {
        return NaN.0D;
      }
      if (!str.equals(sval))
      {
        return NaN.0D;
      }
      stk.nextToken();
      if (ttype != -2)
      {
        return NaN.0D;
      }
      return nval;
    }
    catch (IOException e) {}
    return NaN.0D;
  }
  

  public static double readNumber(StreamTokenizer stk)
  {
    try
    {
      stk.nextToken();
      if (ttype != -2)
      {
        return NaN.0D;
      }
      return nval;
    }
    catch (IOException e) {}
    return NaN.0D;
  }
  
  public static String readWord(StreamTokenizer stk)
  {
    try
    {
      stk.nextToken();
      if (ttype != -3) {
        if (ttype == -1) { return null;
        }
        return null;
      }
      return sval;
    }
    catch (IOException e) {}
    return null;
  }
  




  public static String readWord(StreamTokenizer stk, String str)
  {
    try
    {
      stk.nextToken();
      if (ttype != -3) {
        if (ttype == -1) { return null;
        }
        return null;
      }
      if (!str.equals(sval))
      {

        return null;
      }
      stk.nextToken();
      if (ttype != -3)
      {
        return null;
      }
      return new String(sval);
    }
    catch (IOException e) {}
    return null;
  }
  




  public static String readString(StreamTokenizer stk, String str)
  {
    try
    {
      stk.nextToken();
      if (ttype != -3)
      {
        return null;
      }
      if (!str.equals(sval))
      {

        return null;
      }
      stk.nextToken();
      if (ttype != 34)
      {
        return null;
      }
      return new String(sval);
    }
    catch (IOException e) {}
    return null;
  }
  




  public static String readStringEOL(StreamTokenizer stk, String str)
  {
    try
    {
      stk.nextToken();
      if (ttype != -3) {
        if (ttype != -1)
          System.out.println("ReadStringEOL: First token is not a word");
        return null;
      }
      if (!str.equals(sval)) {
        System.out.println("ReadStringEOL: First token is not " + str + " but " + sval);
        
        return null;
      }
      stk.eolIsSignificant(true);
      stk.nextToken();
      String s = null;
      while (ttype != 10) {
        if (ttype == -3) {
          if (s == null) {
            s = sval;
          } else {
            s = s + " " + sval;
          }
        } else if (ttype == -2) {
          if (s == null) {
            s = "" + nval;
          } else {
            s = s + " " + nval;
          }
        } else {
          if (ttype != -1) {
            System.out.println("ReadStringEOL:token is not of known type:" + ttype);
          }
          stk.eolIsSignificant(false);
          return null;
        }
        stk.nextToken();
      }
      stk.eolIsSignificant(false);
      return new String(s);
    }
    catch (IOException e) {}
    return null;
  }
  

  public static String readStringEOL(StreamTokenizer stk)
  {
    try
    {
      stk.eolIsSignificant(true);
      stk.nextToken();
      String s = null;
      while (ttype != 10) {
        if (ttype == -3) {
          if (s == null) {
            s = sval;
          } else {
            s = s + " " + sval;
          }
        } else if (ttype == -2) {
          if (s == null) {
            s = "" + nval;
          } else {
            s = s + " " + nval;
          }
        } else {
          if (ttype != -1) {
            System.out.println("ReadStringEOL:token is not of known type:" + ttype);
          }
          stk.eolIsSignificant(false);
          return null;
        }
        stk.nextToken();
      }
      stk.eolIsSignificant(false);
      return new String(s);
    }
    catch (IOException e) {}
    return null;
  }
  





  public static Jeli.Probability.Distribution readDistribution(StreamTokenizer stk, String str)
  {
    try
    {
      Jeli.Probability.Distribution pd = null;
      stk.nextToken();
      if (ttype != -3) {
        System.out.println("First Distribution token is not a word");
        return null;
      }
      if (!str.equals(sval)) {
        System.out.println("Next token is not " + str);
        return null;
      }
      stk.nextToken();
      if (ttype != -3) {
        System.out.println("Third Distribution token is not a word");
        return null;
      }
      if ("constant".equals(sval))
      {
        stk.nextToken();
        if (ttype != -2) {
          System.out.println("Constant distribution value is not a number");
          
          return null;
        }
        double mean = nval;
        
        return new Jeli.Probability.Distribution(1, mean);
      }
      
      if ("uniform".equals(sval))
      {
        stk.nextToken();
        if (ttype != -2) {
          System.out.println("Uniform distribution first value is not a number");
          
          return null;
        }
        double low = nval;
        stk.nextToken();
        










        if ((ttype == -3) && ("to".equals(sval)))
        {
          stk.nextToken(); }
        if (ttype != -2) {
          System.out.println("Uniform distribution second value is not a number");
          
          return null;
        }
        double high = nval;
        
        return new Jeli.Probability.Distribution(3, low, high);
      }
      
      if ("exponential".equals(sval))
      {
        stk.nextToken();
        if (ttype != -2) {
          System.out.println("Exponential distribution value is not a number");
          
          return null;
        }
        double mean = nval;
        
        return new Jeli.Probability.Distribution(5, mean);
      }
      


      System.out.println("Next token is neither constant or uniform");
      return null;
    }
    catch (IOException e) {}
    
    return null;
  }
  
  public static void writeString(java.io.OutputStream myout, String str)
  {
    try {
      myout.write(string_to_byte_array_line(str));
    }
    catch (IOException e) {}
  }
  


  private static byte[] string_to_byte_array_line(String str)
  {
    int n = str.length();
    byte[] b = new byte[n + 1];
    for (int i = 0; i < n; i++)
      b[i] = ((byte)str.charAt(i));
    b[n] = 10;
    return b;
  }
  


  public static byte[] string_to_byte_array(String str)
  {
    int n = str.length();
    byte[] b = new byte[n];
    for (int i = 0; i < n; i++)
      b[i] = ((byte)str.charAt(i));
    return b;
  }
  
  public static void saveInFile(String filename, String str) {
    java.io.FileOutputStream myout = null;
    try {
      myout = new java.io.FileOutputStream(filename);
      
      myout.write(string_to_byte_array(str)); return;
    }
    catch (IOException e)
    {
      System.out.println("Error writing file " + filename);
    }
    finally {
      try {
        if (myout != null) {
          myout.close();
        }
      }
      catch (IOException e1) {}
    }
  }
  

  public static String fileToDataString(String name)
  {
    String comma = "";
    
    String str = readEntireFile(name);
    if (str == null) return null;
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < str.length(); i++) {
      char c = str.charAt(i);
      if ((i > 0) && (i % 10 == 0)) {
        sb.append(comma + "\n\t\t" + c);
      } else
        sb.append(comma + c);
      comma = ",";
    }
    return sb.toString();
  }
  










  public static void setLocalFilesFromFile(String name)
  {
    byte[] filebytes = readEntireFileBytes(name);
    if (filebytes == null) {
      return;
    }
    


    int cur_index = 0;
    
    Jeli.LocalFiles.LocalFilesList lfiles = new Jeli.LocalFiles.LocalFilesList();
    
    while (cur_index < filebytes.length) {
      int token_size = 0;
      for (int i = cur_index; (i < filebytes.length) && (filebytes[i] != 32); 
          i++)
        token_size++;
      if (token_size == 0)
      {
        return;
      }
      String filename = new String(filebytes, cur_index, token_size);
      
      cur_index += token_size;
      token_size = 0;
      while ((cur_index < filebytes.length) && (filebytes[cur_index] == 32))
        cur_index++;
      for (int i = cur_index; (i < filebytes.length) && (filebytes[i] != 10); 
          i++)
        token_size++;
      if (token_size == 0)
      {

        return;
      }
      String sizestring = new String(filebytes, cur_index, token_size);
      int filesize;
      try { filesize = Integer.parseInt(sizestring);
      }
      catch (NumberFormatException e)
      {
        return;
      }
      if (filesize == 0)
      {
        return;
      }
      
      byte[] onefile = new byte[filesize];
      cur_index = cur_index + token_size + 1;
      if (cur_index + filesize > filebytes.length)
      {

        return;
      }
      for (int i = 0; i < filesize; i++)
        onefile[i] = filebytes[(cur_index + i)];
      cur_index += filesize;
      lfiles.add(filename, onefile);
    }
    



    localFiles = lfiles;
    System.out.println("Local files processed: " + lfiles.getSize());
  }
  




  public static Vector readFullConfigurationFile(String fn)
  {
    String delim = " \t\n\r";
    String eolonly = "\n\r";
    
    String name = null;
    


    String str = readEntireFile(fn);
    if (str == null) return null;
    Vector v = new Vector();
    v.addElement(str);
    StringTokenizer stk = new StringTokenizer(str, eolonly);
    try {
      for (;;) {
        String line = stk.nextToken(eolonly).trim();
        int ind = line.indexOf(' ');
        if (ind == -1) {
          v.addElement(makeConfig(line, "none"));
        }
        else {
          name = line.substring(0, ind);
          String val = line.substring(ind).trim();
          v.addElement(makeConfig(name, val));
        }
      }
    }
    catch (java.util.NoSuchElementException e) {}
    
    return v;
  }
  
  public static Vector getConfigurationComments(String s, String start) {
    String eolonly = "\n\r";
    





    StringTokenizer stk = new StringTokenizer(s, eolonly);
    Vector v = new Vector();
    while (stk.hasMoreTokens()) {
      String line = stk.nextToken(eolonly).trim();
      if (line.startsWith(start))
      {
        line = line.substring(start.length());
        int ind = line.indexOf(' ');
        if (ind == -1) {
          v.addElement(makeConfig(line, "none"));
        } else {
          String name = line.substring(0, ind);
          String val = line.substring(ind).trim();
          v.addElement(makeConfig(name, val));
        }
      } }
    return v;
  }
  


  public static Vector readConfigurationFile(String fn)
  {
    String delim = " \t\n\r";
    String eolonly = "\n\r";
    
    String name = null;
    


    String str = readEntireFile(fn);
    if (str == null) return null;
    Vector v = new Vector();
    StringTokenizer stk = new StringTokenizer(str, eolonly);
    try {
      for (;;) {
        String line = stk.nextToken(eolonly).trim();
        int ind = line.indexOf(' ');
        if (ind == -1) {
          v.addElement(makeConfig(line, "none"));
        }
        else {
          name = line.substring(0, ind);
          String val = line.substring(ind).trim();
          v.addElement(makeConfig(name, val));
        }
      }
    }
    catch (java.util.NoSuchElementException e) {}
    
    return v;
  }
  

  public static Vector processConfigurationString(String str)
  {
    String delim = " \t\n\r";
    String eolonly = "\n\r";
    
    String name = null;
    


    if (str == null) return null;
    Vector v = new Vector();
    StringTokenizer stk = new StringTokenizer(str, eolonly);
    try {
      for (;;) {
        String line = stk.nextToken(eolonly).trim();
        int ind = line.indexOf(' ');
        if (ind == -1) {
          v.addElement(makeConfig(line, "none"));
        }
        else {
          name = line.substring(0, ind);
          String val = line.substring(ind).trim();
          v.addElement(makeConfig(name, val));
        }
      }
    }
    catch (java.util.NoSuchElementException e) {}
    
    return v;
  }
  









  public static Vector readConfigurationFile(String fn, String sp)
  {
    String delim = " \t\n\r";
    String eolonly = "\n\r";
    
    String name = null;
    


    String str = readEntireFile(fn);
    if (str == null) return null;
    Vector v = new Vector();
    StringTokenizer stk = new StringTokenizer(str, eolonly);
    try {
      for (;;) {
        String line = stk.nextToken(eolonly).trim();
        int ind = line.indexOf(' ');
        if (ind == -1) {
          v.addElement(makeConfig(line, "none"));
        }
        else {
          name = line.substring(0, ind);
          if (name.equals(sp)) {
            String val = "";
            while (stk.hasMoreTokens()) {
              line = stk.nextToken(eolonly).trim();
              if (line.equals("}")) {
                v.addElement(makeConfig(name, val));
                break;
              }
              val = val + " " + line;
            }
          }
          

          String val = line.substring(ind).trim();
          v.addElement(makeConfig(name, val));
        }
      }
    }
    catch (java.util.NoSuchElementException e) {}
    

    return v;
  }
  




  private static ConfigInfo makeConfig(String name, String val)
  {
    Jeli.Probability.Distribution dist = null;
    
    int[] intvals = null;
    double[] doublevals = null;
    
    Vector doubles = new Vector();
    Vector ints = new Vector();
    dist = Jeli.Probability.Distribution.parseDistribution(val);
    
    StringTokenizer stk = new StringTokenizer(val);
    for (;;) { if (stk.hasMoreTokens()) {
        String str = stk.nextToken();
        try {
          int intval = Integer.parseInt(str);
          ints.addElement(new Integer(intval));
        }
        catch (NumberFormatException e) {}
      }
    }
    
    stk = new StringTokenizer(val);
    for (;;) { if (stk.hasMoreTokens()) {
        String str = stk.nextToken();
        try {
          double doubleval = Double.valueOf(str).doubleValue();
          doubles.addElement(new Double(doubleval));
        }
        catch (NumberFormatException e) {}
      }
    }
    
    if (ints.size() > 0) {
      intvals = new int[ints.size()];
      for (int i = 0; i < ints.size(); i++)
        intvals[i] = ((Integer)ints.elementAt(i)).intValue();
    }
    if (doubles.size() > 0) {
      doublevals = new double[doubles.size()];
      for (int i = 0; i < doubles.size(); i++) {
        doublevals[i] = ((Double)doubles.elementAt(i)).doubleValue();
      }
    }
    return new ConfigInfo(name, val, intvals, doublevals, dist);
  }
  

  public static String promptForFile(String name)
  {
    if (filePrompt == null) return null;
    filePrompt.setName(name);
    filePrompt.setVisible(true);
    String str = filePrompt.getContents();
    return str;
  }
  
  private static String editFile(String name, String str)
  {
    if (filePrompt == null) return null;
    filePrompt.setName(name);
    filePrompt.setVisible(true);
    String str1 = filePrompt.getContents(str);
    return str1;
  }
  



  public static String generateNewFilename(String fullname, String dir, int minDigits)
  {
    String basename = fullname;
    String ext = "";
    int pos = fullname.indexOf('.');
    if (pos >= 0) {
      basename = fullname.substring(0, pos);
      ext = fullname.substring(pos);
    }
    return generateNewFilename(basename, ext, dir, minDigits);
  }
  
  public static void setBrowser(String s) {
    forceBrowser = s;
  }
  



  public static String generateNewFilename(String basename, String ext, String dir, int minDigits)
  {
    int maxFileNumber = 0;
    


    int basenameLength = basename.length();
    int extLength = ext.length();
    if (dir.length() == 0)
      dir = ".";
    java.io.File dirFile = new java.io.File(dir);
    String[] dirlist = dirFile.list();
    if (dirlist == null)
      return basename + ext;
    for (int i = 0; i < dirlist.length; i++) {
      if ((dirlist[i].startsWith(basename)) && (dirlist[i].endsWith(ext))) {
        String fileNumberString = dirlist[i].substring(basenameLength, dirlist[i].length() - extLength);
        
        try
        {
          int fileNumber = Integer.parseInt(fileNumberString);
          if (fileNumber > maxFileNumber) {
            maxFileNumber = fileNumber;
          }
        } catch (NumberFormatException e) {}
      }
    }
    String fileNumberString = "" + (maxFileNumber + 1);
    while (fileNumberString.length() < minDigits)
      fileNumberString = "0" + fileNumberString;
    return basename + fileNumberString + ext;
  }
  
  public static boolean openBrowser(String dir, String fn) {
    String osName = System.getProperty("os.name");
    
    if ((dir == null) || (dir.length() == 0))
      dir = ".";
    try {
      java.io.File file = new java.io.File(dir, fn);
      URL url = file.toURL();
      String urlString = url.toString();
      
      if (osName.startsWith("Mac OS"))
      {
        Class fileMgr = Class.forName("com.apple.eio.FileManager");
        java.lang.reflect.Method openURL = fileMgr.getDeclaredMethod("openURL", new Class[] { String.class });
        
        openURL.invoke(null, new Object[] { urlString });
      }
      else if (osName.startsWith("Windows"))
      {
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + urlString);

      }
      else
      {
        String[] browsers = { "firefox", "opera", "konqueror", "epiphany", "mozilla", "netscape" };
        


        String browser = null;
        if (forceBrowser != null) { if (Runtime.getRuntime().exec(new String[] { "which", forceBrowser }).waitFor() == 0)
          {
            browser = forceBrowser;
            break label308;
          }
        }
        for (int count = 0; (count < browsers.length) && (browser == null); count++) {
          if (Runtime.getRuntime().exec(new String[] { "which", browsers[count] }).waitFor() == 0)
          {
            browser = browsers[count]; }
        }
        label308:
        if (browser == null) {
          throw new Exception("Could not find web browser");
        }
        
        Runtime.getRuntime().exec(new String[] { browser, urlString });
      }
    }
    catch (Exception e)
    {
      return false;
    }
    return true;
  }
}
