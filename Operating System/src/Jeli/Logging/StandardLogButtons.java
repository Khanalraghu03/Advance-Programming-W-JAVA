package Jeli.Logging;

import Jeli.Poster;
import Jeli.Utility;
import Jeli.UtilityIO;
import Jeli.Widgets.HelpManager;
import Jeli.Widgets.JeliButton;
import java.applet.AppletContext;
import java.awt.Color;
import java.awt.Component;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.Vector;

public class StandardLogButtons implements Jeli.Widgets.JeliButtonCallBack, Jeli.Get.UsernameCallBack, Jeli.Get.LogFilenameCallBack, LogTracker, Jeli.Widgets.JeliRefreshable
{
  public static final int TYPE_OPEN_ERROR = 0;
  public static final int TYPE_OPEN_REMOTE_ATTEMPT = 1;
  public static final int TYPE_OPEN_OK = 2;
  public static final int TYPE_CLOSE_LOG = 3;
  public static final int TYPE_START_LOG = 4;
  public static final int TYPE_STOP_LOG = 5;
  public static final int TYPE_LOG_COMMENT = 6;
  public static final int TYPE_SET_USERNAME = 7;
  public static final int TYPE_SETTING_FILENAME = 8;
  public static final int TYPE_SET_FILENAME = 9;
  private static final String showRemoteLogString = "Show Remote Log";
  private static final String showLocalLogString = "Show Local Log";
  private JeliButton OpenCloseButton;
  private JeliButton StartStopButton;
  private JeliButton LogCommentButton;
  private JeliButton ShowRemoteLogButton;
  private java.net.URL LogURL = null;
  

  private String openLogMessage;
  
  private String closeLogMessage;
  
  private boolean logging_flag = false;
  private boolean log_open_flag = false;
  private int uniqueFilenameDigits = 0;
  private int backgroundColorNumber = -1;
  
  private int RemotePort;
  
  private LogButtonCallBack lbcb;
  
  private HelpManager hm;
  
  private String uname;
  private String webfile;
  private String openedWebfile;
  private String webdir;
  private String imname;
  private String outputwebdir;
  private PLogger logger;
  private String title;
  private String version;
  private boolean append_flag;
  private StandardLogger logut;
  private Poster poster;
  private Vector LogButtonList;
  private LogComment comment_logger;
  
  public StandardLogButtons(HelpManager hm, String uname, String fn, String dir, String title, String version, boolean append_flag, int remote_port)
  {
    this(hm, uname, fn, dir, "gifim", title, version, append_flag, remote_port, null, null);
  }
  





  public StandardLogButtons(HelpManager hm, String uname, String fn, String dir, String imname, String title, String version, boolean append_flag, int remote_port, LogButtonCallBack lbcb, Poster poster)
  {
    this.hm = hm;
    if (Utility.getRemotePort() > 0)
      remote_port = Utility.getRemotePort();
    RemotePort = remote_port;
    this.lbcb = lbcb;
    this.uname = uname;
    webfile = fn;
    openedWebfile = fn;
    webdir = dir;
    if (webdir.equals(".")) webdir = "";
    this.imname = imname;
    this.title = title;
    this.version = version;
    this.poster = poster;
    this.append_flag = append_flag;
    if (remote_port <= 0) {
      openLogMessage = "Open Log";
      closeLogMessage = "Close Log";
    }
    else {
      openLogMessage = "Open Remote Log";
      closeLogMessage = "Close Remote Log";
    }
    OpenCloseButton = new JeliButton(openLogMessage, hm, this);
    StartStopButton = new JeliButton("Change Log Filename", hm, this);
    LogCommentButton = new JeliButton("Log Comment", hm, this);
    if (remote_port > 0) {
      ShowRemoteLogButton = new JeliButton("Show Remote Log", hm, this);
    } else
      ShowRemoteLogButton = new JeliButton("Show Local Log", hm, this);
    OpenCloseButton.setClassification("Standard Log Button");
    StartStopButton.setClassification("Standard Log Button");
    LogCommentButton.setClassification("Standard Log Button");
    ShowRemoteLogButton.setClassification("Standard Log Button");
    ShowRemoteLogButton.disableJeliButton();
    setLogCommentButtonLabel();
    LogButtonList = new Vector();
    logut = new StandardLogger(outputwebdir, logger, this, poster);
    Utility.setLogger(logut);
  }
  
  public JeliButton getOpenButton() {
    return OpenCloseButton;
  }
  
  public JeliButton getStartButton() {
    return StartStopButton;
  }
  
  public JeliButton getCommentButton() {
    return LogCommentButton;
  }
  
  public JeliButton getShowRemoteLogButton() {
    return ShowRemoteLogButton;
  }
  
  public void registerLog(LogState lb) {
    LogButtonList.addElement(lb);
    lb.logStateChange(logging_flag);
  }
  
  public void unregisterLogButton(Component com) {
    LogButtonList.removeElement(com);
  }
  
  public void registerLogButton(Component com) {
    LogButtonList.addElement(com);
    if ((com instanceof LogState)) {
      ((LogState)com).logStateChange(logging_flag);
      return;
    }
    if (logging_flag) {
      com.setEnabled(true);
    } else {
      com.setEnabled(false);
    }
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    String lab = bh.getLabel();
    if (lab.equals(openLogMessage)) {
      open_log();
    } else if (lab.equals(closeLogMessage)) {
      close_log();
    } else if (lab.equals("Start Log")) {
      start_log();
    } else if (lab.equals("Stop Log")) {
      stop_log();
    } else if (lab.equals("Change Log Filename")) {
      change_log_filename();
    } else if (lab.equals("Log Comment")) {
      log_comment();
    } else if (lab.equals("Replace Old Log")) {
      append_flag = true;
      setLogCommentButtonLabel();
    }
    else if (lab.equals("Append To Old Log")) {
      append_flag = false;
      setLogCommentButtonLabel();
    }
    else if (lab.equals("Reset Username")) {
      reset_username();
    } else if (lab.equals("Show Remote Log")) {
      show_remote_log();
    } else if (lab.equals("Show Local Log")) {
      show_local_log();
    } else {
      System.out.println("Warning: Unknown Standard Log Button " + lab);
    }
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() { return null; }
  
  public void setClassification(String str) {}
  
  public void setUsername(String un)
  {
    uname = un;
    callback(7, un);
  }
  
  public void callback(int n, String str) {
    if (lbcb != null) {
      lbcb.lBCallBack(n, str);
    }
  }
  
  private void show_local_log()
  {
    UtilityIO.openBrowser(webdir, openedWebfile);
  }
  





  private void show_remote_log()
  {
    if (RemotePort <= 0) {
      return;
    }
    





















    if (LogURL == null)
    {
      String LogURLString = logger.getHTMLBase() + logger.getDirectory() + webfile;
      try
      {
        LogURL = new java.net.URL(LogURLString);
      }
      catch (MalformedURLException e) {
        ShowRemoteLogButton.disableJeliButton();
        return;
      }
    }
    AppletContext app = UtilityIO.getAppletContext();
    if (app == null) {
      ShowRemoteLogButton.disableJeliButton();
      return;
    }
    app.showDocument(LogURL, "Remote Log");
  }
  
  public String getRemoteLog() {
    if (RemotePort <= 0)
      return null;
    return logger.getHTMLBase() + logger.getDirectory() + webfile;
  }
  


  private void open_log()
  {
    LogURL = null;
    if ((RemotePort > 0) && (uname.equals("Remote Users"))) {
      new Jeli.Get.GetUsername("Change User Name Before Opening Log File", uname, hm, this, StartStopButton);
      
      return;
    }
    if (RemotePort > 0) {
      webdir = removeSpaces(uname);
      outputwebdir = "";
      callback(1, "Attempting to open remote log ...");

    }
    else
    {
      outputwebdir = webdir;
      if ((webdir.length() > 0) && (!webdir.endsWith("" + java.io.File.separatorChar)))
      {
        webdir += java.io.File.separatorChar; }
      if (uniqueFilenameDigits > 0) {
        openedWebfile = UtilityIO.generateNewFilename(webfile, webdir, uniqueFilenameDigits);
      }
    }
    logger = new PLogger(webdir, openedWebfile, title + " for " + uname, version + "<br>", true, append_flag, RemotePort);
    
    if (!logger.getOpenFlag()) {
      find_default_applet();
      logger = new PLogger(webdir, openedWebfile, title + " for " + uname, version + "<br>", true, append_flag, RemotePort);
    }
    
    if (!logger.getOpenFlag()) {
      callback(0, "Error Opening Log File");
      if (RemotePort > 0) {
        callback(0, "Error Opening Remote Log File");
      } else {
        callback(0, "Error Opening Log File");
      }
      return;
    }
    if (RemotePort > 0) {
      String LogURLString = logger.getHTMLBase() + logger.getDirectory() + webfile;
      callback(-1, "Remote Log File is:\n " + LogURLString + "\n");
    }
    if (RemotePort > 0) ShowRemoteLogButton.enableJeliButton();
    logut.setLogger(outputwebdir, logger);
    logging_flag = true;
    log_open_flag = true;
    callback(2, "Directory is " + outputwebdir + ", File is " + webfile);
    
    OpenCloseButton.setLabel(closeLogMessage);
    StartStopButton.setLabel("Stop Log");
    StartStopButton.setMessage("Stop Log");
    if (RemotePort <= 0)
      ShowRemoteLogButton.enableJeliButton();
    setLogCommentButtonLabel();
    setLogStates();
    comment_logger = new LogComment("Log Comments", "Old Comments", "Current Comment", 10, 8, 40, Utility.jeliLightCyan, Utility.jeliLightYellow, hm, logut);
  }
  



  private void find_default_applet()
  {
    if (UtilityIO.getAppletContext() != null) return;
    Component parent = OpenCloseButton.getParent();
    if (parent == null) return;
    while (parent != null) {
      if ((parent instanceof java.applet.Applet)) {
        System.out.println("   Applet found");
        UtilityIO.setApplet((java.applet.Applet)parent);
        return;
      }
      parent = parent.getParent();
    }
  }
  
  private void close_log()
  {
    callback(3, null);
    logging_flag = false;
    log_open_flag = false;
    OpenCloseButton.setLabel(openLogMessage);
    OpenCloseButton.setMessage(openLogMessage);
    StartStopButton.setLabel("Change Log Filename");
    StartStopButton.setMessage("Change Log Filename");
    setLogCommentButtonLabel();
    setLogStates();
    comment_logger.setVisible(false);
  }
  
  private void start_log() {
    StartStopButton.setLabel("Stop Log");
    StartStopButton.setMessage("Stop Log");
    logging_flag = true;
    callback(4, null);
    setLogStates();
  }
  
  private void stop_log() {
    callback(5, null);
    StartStopButton.setLabel("Start Log");
    StartStopButton.setMessage("Start Log");
    logging_flag = false;
    setLogStates();
    comment_logger.setVisible(false);
  }
  
  private void log_comment() {
    callback(6, null);
    comment_logger.setVisible(true);
  }
  
  private void reset_username() {
    new Jeli.Get.GetUsername("Change User Name", uname, hm, this, StartStopButton);
  }
  

  private void change_log_filename()
  {
    String webdirfixed = webdir;
    if (RemotePort > 0)
      webdirfixed = null;
    new Jeli.Get.GetLogFilename("Change Log File Name", webfile, webdirfixed, imname, hm, this, StartStopButton);
    
    callback(8, null);
  }
  
  private String removeSpaces(String fn)
  {
    String newst = "";
    for (int i = 0; i < fn.length(); i++)
      if (Character.isLetter(fn.charAt(i))) newst = newst + fn.charAt(i);
    if (newst.length() == 0) return "X";
    return newst;
  }
  
  public void setLogFilename(String fn, String dir, String imname) {
    webfile = new String(fn);
    openedWebfile = webfile;
    LogURL = null;
    if ((dir != null) && (RemotePort <= 0))
      webdir = new String(dir);
    if (webdir.equals(".")) webdir = "";
    this.imname = new String(imname);
    callback(9, null);
  }
  
  public void setLogFilename(String fn) {
    webfile = new String(fn);
    openedWebfile = webfile;
  }
  
  public boolean getLoggingFlag()
  {
    return logging_flag;
  }
  
  public StandardLogger getLogger() {
    return logut;
  }
  

  private void setLogStates()
  {
    for (int i = 0; i < LogButtonList.size(); i++) {
      if ((LogButtonList.elementAt(i) instanceof LogState)) {
        LogState ls = (LogState)LogButtonList.elementAt(i);
        ls.logStateChange(logging_flag);

      }
      else if ((LogButtonList.elementAt(i) instanceof Component)) {
        Component com = (Component)LogButtonList.elementAt(i);
        if (logging_flag) {
          com.setEnabled(true);
        } else {
          com.setEnabled(false);
        }
      }
    }
  }
  
  private void setLogCommentButtonLabel() {
    if (logging_flag) {
      LogCommentButton.setLabel("Log Comment");
      LogCommentButton.setMessage("Log Comment");
      return;
    }
    if (RemotePort > 0) {
      LogCommentButton.setLabel("Reset Username");
      LogCommentButton.setMessage("Reset Username");
      return;
    }
    if (append_flag) {
      LogCommentButton.setLabel("Append To Old Log");
      LogCommentButton.setMessage("Append To Old Log");
    }
    else {
      LogCommentButton.setLabel("Replace Old Log");
      LogCommentButton.setMessage("Replace Old Log");
    }
  }
  
  public void setBrowser(String filename) {
    UtilityIO.setBrowser(filename);
  }
  








  public void logString(String str)
  {
    if (logut == null) return;
    logut.logString(str);
  }
  
  public void logStringSimple(String str) {
    if (logut == null) return;
    logut.logStringSimple(str);
  }
  
  public void setVersion(String version) {
    this.version = version;
  }
  
  public void setCallback(LogButtonCallBack lbcb) {
    this.lbcb = lbcb;
  }
  
  public int getRemotePort() {
    return RemotePort;
  }
  
  public void setUniqueFilenameDigits(int n) {
    uniqueFilenameDigits = n;
    logut.setUniqueFilenameDigits(n);
  }
  
  public String getFilename() {
    if (logger != null)
      return logger.getFilename();
    return "";
  }
  
  public void setBackground(Color c) {
    OpenCloseButton.setBackground(c);
    StartStopButton.setBackground(c);
    LogCommentButton.setBackground(c);
    ShowRemoteLogButton.setBackground(c);
  }
  
  public void setBackground(int which) {
    OpenCloseButton.setBackground(which);
    StartStopButton.setBackground(which);
    LogCommentButton.setBackground(which);
    ShowRemoteLogButton.setBackground(which);
    backgroundColorNumber = which;
    Utility.addRefreshable(this);
  }
  
  public void refresh() {
    if (backgroundColorNumber == -1)
      return;
    OpenCloseButton.refresh();
    StartStopButton.refresh();
    LogCommentButton.refresh();
    ShowRemoteLogButton.refresh();
  }
  
  public PLogger getPLogger() {
    return logger;
  }
}
