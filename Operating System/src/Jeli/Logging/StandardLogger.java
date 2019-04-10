package Jeli.Logging;

import Jeli.Poster;
import java.awt.Image;
import java.util.Date;

public class StandardLogger implements Logger
{
  private PLogger logger;
  private boolean log_hrule_flag = false;
  private LogTracker lt;
  private int linkcount = 0;
  private int debug_level = 0;
  private static int image_number = 0;
  private String imagename = "gifimage";
  private String outputwebdir = "";
  private Poster poster;
  private int uniqueFilenameDigits = 0;
  
  public StandardLogger(String outputwebdir, PLogger logger, LogTracker lt, Poster poster)
  {
    this.poster = poster;
    this.outputwebdir = outputwebdir;
    this.logger = logger;
    this.lt = lt;
  }
  
  public void setUniqueFilenameDigits(int n) {
    uniqueFilenameDigits = n;
  }
  
  public void logStringSimple(String str) {
    if (lt.getLoggingFlag()) {
      logger.writeLogger(str);
      log_hrule_flag = false;
    }
  }
  
  public void setLogger(String outputwebdir, PLogger logger) {
    this.outputwebdir = outputwebdir;
    this.logger = logger;
  }
  
  public void logString(String str)
  {
    if (lt.getLoggingFlag())
    {
      logger.writeLogger(str + "<br>");
      log_hrule_flag = false;
    }
  }
  
  public void logString(String str, int head) {
    if (lt.getLoggingFlag()) {
      logger.writeLogger("<H" + head + ">" + str + "</H" + head + ">");
      log_hrule_flag = false;
    }
  }
  


  public void logStringToHtml(String str)
  {
    StringBuffer buf = new StringBuffer();
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      if (ch == '\n')
        buf.append("<br>");
      buf.append(ch);
    }
    logString(buf.toString());
  }
  
  public void logHrule() {
    if (lt.getLoggingFlag()) {
      if (log_hrule_flag) return;
      logger.writeLogger("<hr>");
      log_hrule_flag = true;
    }
  }
  
  public void logHrule(int size) {
    if (lt.getLoggingFlag()) {
      logger.writeLogger("<hr size=" + size + ">");
      log_hrule_flag = false;
    }
  }
  
  public void logTime() {
    if (lt.getLoggingFlag())
      logString(new Date().toString());
  }
  
  public void logLink(String str) {
    if (lt.getLoggingFlag()) return;
    logger.writeLogger("<a name=" + str + ">\n");
    log_hrule_flag = false;
  }
  
  public String setLink() {
    if (lt.getLoggingFlag()) return null;
    linkcount += 1;
    logStringSimple("<a name=locallink" + linkcount + ">");
    return "locallink" + linkcount;
  }
  
  public int debugLevel() {
    return debug_level;
  }
  

  public void logImage(Image im, String str, String cap, SaveImageInfo sio)
  {
    if (!lt.getLoggingFlag()) { return;
    }
    image_number += 1;
    String name = get_image_name(imagename, image_number);
    logTime();
    logger.writeLogger("<center>\n<IMG SRC=" + name + "><br>\n" + cap + "<br>\n" + str + "<br>\n</center>");
    
    if (poster != null)
      poster.postNoLine("Saving image " + name + " ... ");
    logger.saveImage(im, outputwebdir + name, sio);
    if (poster != null)
      poster.postLine("done");
    log_hrule_flag = false;
  }
  
  public void logImage(Image im, String str, SaveImageInfo sio)
  {
    if (!lt.getLoggingFlag()) { return;
    }
    image_number += 1;
    String name = get_image_name(imagename, image_number);
    logTime();
    logger.writeLogger("<br>\n<center>\n<IMG SRC=" + name + "><br>\n" + str + "<br></center>\n");
    
    if (poster != null)
      poster.postNoLine("Saving image " + name + " ... ");
    logger.saveImage(im, outputwebdir + name, sio);
    log_hrule_flag = false;
    if (poster != null) {
      poster.postLine("done");
    }
  }
  



  private String get_image_name(String imagename, int imnum)
  {
    if (uniqueFilenameDigits > 0) {
      String name = Jeli.UtilityIO.generateNewFilename(imagename + ".gif", outputwebdir, uniqueFilenameDigits);
      
      return name;
    }
    int reopen_count = logger.getReopenCount();
    String extra; String extra; if (reopen_count > 0) {
      extra = "_" + reopen_count + "_";
    } else
      extra = "";
    String name; String name; if (image_number < 10) {
      name = imagename + "0" + image_number + extra + ".gif";
    } else
      name = imagename + image_number + extra + ".gif";
    return name;
  }
}
