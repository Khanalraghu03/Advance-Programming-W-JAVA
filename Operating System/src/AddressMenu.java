import Jeli.ConfigInfo;
import Jeli.Logging.StandardLogButtons;
import Jeli.Utility;
import Jeli.Widgets.JeliButton;
import Jeli.Widgets.JeliLabel;
import Jeli.Widgets.JeliPanel;
import java.awt.Color;
import java.awt.Panel;
import java.io.PrintStream;

public class AddressMenu extends Jeli.Widgets.JeliFrame implements Jeli.Logging.LogButtonCallBack, Jeli.Poster, Jeli.Widgets.DebugRegistration
{
  private final int RANDOMIZE_COUNT = 6;
  private final int DEBUG_ID = 1;
  
  public static final int EMAIL_TYPE_NONE = 0;
  public static final int EMAIL_TYPE_COMPLETION_ATTEMPT = 1;
  public static final int EMAIL_TYPE_COMPLETION_SUCCESS = 2;
  public static final int EMAIL_TYPE_DONE = 3;
  public static final int LOG_TYPE_ALL = 0;
  public static final int LOG_TYPE_COMPLETION = 1;
  public static final int LOG_TYPE_SUMMARY = 2;
  private int logTotalLifelinesUsed = 0;
  private int logTotalLifelinesPerformed = 0;
  private int logProgressHelpUsed = 0;
  private int logProgressDoUsed = 0;
  private int emailTotalLifelinesUsed = 0;
  private int emailTotalLifelinesPerformed = 0;
  private int emailProgressHelpUsed = 0;
  private int emailProgressDoUsed = 0;
  
  private int singleLevelButtonCount = 0;
  private int twoLevelButtonCount = 0;
  private int numButtonCount = 0;
  
  private int maxButtonCount;
  private String emailFrom = null;
  private String emailTo = null;
  private String emailHost = null;
  private int emailType = 0;
  private boolean testOrderFlag = true;
  private Color levelOneColor = Utility.jeliLightYellow;
  private Color levelTwoColor = Utility.jeliLightBlue;
  private static int debugLevel = 0;
  private static boolean sizeChanged = false;
  private static Jeli.Logging.LogButtonCallBack cb = null;
  private static java.net.URL backURL = null;
  
  private final String title = "Virtual Address Translation ";
  
  private String version_message;
  
  private String ver1_msg;
  private String ver2_msg;
  private String configfile = "addressconfig";
  private StandardLogButtons slb = null;
  private int remotePort = 0;
  
  private JeliButton quitButton;
  
  private JeliButton logAllEventsButton;
  
  private JeliButton testButton;
  
  private JeliButton singleLevelTestsButton;
  private JeliButton twoLevelTestsButton;
  private JeliButton debugButton;
  private JeliLabel fileNameLabel;
  private Address af = null;
  private Jeli.Probability.Distribution seedDistribution;
  private int seed = 12345;
  private String username = "Unknown User";
  private String logFilename = "logfile.html";
  private String logDirname = "logs";
  private String imageName = "im";
  private int uniqueFilenameDigits = 0;
  
  private JeliPanel singleTestsPanel;
  private JeliPanel twoTestsPanel;
  private JeliPanel buttonTestsPanel;
  private JeliButton[] singleLevelButtons;
  private JeliButton[] twoLevelButtons;
  private JeliButton[] singleLevelButtonsOrig;
  private JeliButton[] twoLevelButtonsOrig;
  private Jeli.Probability.Distribution randomizeDist;
  private int numberOfLevels = 1;
  private JeliLabel[] completionListLabels;
  private JeliLabel sessionLifelineLabel;
  private JeliLabel instanceLifelineLabel;
  private int sessionLifelines = 0;
  private int instanceLifelines = 0;
  
  private JeliPanel infoPanel;
  private JeliPanel quitPanel;
  private JeliPanel lifeLinePanel;
  private JeliPanel bottomPanel;
  private JeliPanel debugPanel;
  private JeliPanel debugInfoPanel;
  private JeliPanel choosePanel;
  private JeliPanel mainPanel;
  private boolean showTestNames = false;
  
  private boolean[] completeFlags;
  
  private AddressTests testList;
  private String testFilename = null;
  private String testFilelist = null;
  private int lifelinesTotal = 0;
  private int lifelinesPerTest = 0;
  private boolean noProgress = false;
  private boolean progressHelp = false;
  private boolean progressDo = false;
  private int logType = 0;
  private String defaultConfig = "addressdefaultconfig";
  private boolean quietFlag = false;
  private StringBuffer doneLogString;
  private String emailDoneLogString = "";
  private boolean quittingFlag = false;
  private TestDefaults singleTestDefaults = null;
  private TestDefaults twoTestDefaults = null;
  private boolean inhibitSingleDefaultTests = false;
  private boolean inhibitTwoDefaultTests = false;
  
  public AddressMenu(String config, int remotePort, String backURLString) {
    this(config, remotePort);
    setBackURL(backURLString);
  }
  
  public AddressMenu(String config, int remotePort) {
    super("Virtual Address Translation Simulator");
    Address.setTester(this);
    doneLogString = new StringBuffer();
    this.remotePort = remotePort;
    setInhibitVisibleComponent(true);
    if (config != null) {
      configfile = config;
    }
    seedDistribution = new Jeli.Probability.Distribution(7, seed);
    TranslationData.setSeed(seedDistribution.next());
    setupConfiguration(configfile);
    if (!quietFlag)
      Jeli.UtilityIO.loadSoundsQuick();
    if (!setupEmail()) {
      System.out.println("Error setting up email");
    }
    if (lifelinesTotal < 0)
      lifelinesTotal = Integer.MAX_VALUE;
    if (lifelinesPerTest < 0)
      lifelinesPerTest = Integer.MAX_VALUE;
    if (lifelinesPerTest > lifelinesTotal)
      lifelinesTotal = lifelinesPerTest;
    if (progressDo)
      progressHelp = true;
    randomizeDist = createRandom(username);
    if (testFilename != null) {
      System.out.println("Using test filename " + testFilename);
      testFilelist = Jeli.UtilityIO.readEntireFile(testFilename);
    }
    testList = new AddressTests(testFilelist, inhibitSingleDefaultTests, inhibitTwoDefaultTests, singleTestDefaults, twoTestDefaults, this);
    

    if (testList.getErrorMessage() != null) {
      System.out.println("Error processing file " + testFilename + "entry " + testList.getErrorEntry() + ": " + testList.getErrorMessage());
    }
    



    singleLevelButtonCount = testList.getSingleSize();
    twoLevelButtonCount = testList.getTwoSize();
    numButtonCount = (singleLevelButtonCount + twoLevelButtonCount);
    maxButtonCount = Math.max(singleLevelButtonCount, twoLevelButtonCount);
    if (maxButtonCount < 7) {
      maxButtonCount = 7;
    }
    
    version_message = "Virtual Address Translation by S. Robbins version 1.03 supported by NSF DUE-0088769, last modified January 2, 2007";
    



    ver1_msg = (version_message + " using Jeli " + "L288" + " last modified " + "March 1, 2007");
    
    ver2_msg = ver1_msg;
    
    setupButtons();
    setupLayout();
    setResizable(false);
    af = new Address(seedDistribution);
    af.setProgress(noProgress, progressHelp, progressDo);
    af.setEmailType(emailType);
    af.setLogType(logType);
    moveIt(af);
    setVisible(true);
  }
  
  public String getUsername() {
    return username;
  }
  
  private boolean setupEmail() {
    if (emailType == 0)
      return true;
    if ((emailFrom == null) || (emailTo == null) || (emailHost == null))
      return false;
    return Utility.setupEmailSender(emailTo, emailFrom, emailHost);
  }
  
  public void registerLog(Jeli.Logging.LogState lb) {
    slb.registerLog(lb);
  }
  
  public void setBackURL(String s) {
    backURL = null;
    try {
      backURL = new java.net.URL(s);
    }
    catch (java.net.MalformedURLException e) {}
  }
  


  private void setupConfiguration(String configfile)
  {
    java.util.Vector v = Jeli.UtilityIO.readConfigurationFile(configfile);
    if (v == null) {
      Jeli.UtilityIO.setResourceClass(AddressMenu.class);
      v = Jeli.UtilityIO.readConfigurationFile(defaultConfig);
      if (v == null) {
        System.out.println("Error reading configuration file " + defaultConfig);
        return;
      }
      System.out.println("Using default configuration file " + defaultConfig);
    }
    for (int i = 0; i < v.size(); i++) {
      handleConfiguration((ConfigInfo)v.elementAt(i));
    }
    if (Utility.getRemotePort() > 0)
      remotePort = Utility.getRemotePort();
  }
  
  private void handleConfiguration(ConfigInfo ci) {
    if (name.startsWith("%")) {
      return;
    }
    if (ci.checkName("user")) {
      username = str;
    } else if (ci.checkName("quiet")) {
      quietFlag = true;
    } else if (ci.checkName("seed")) {
      seed = intval;
    }
    else if (ci.checkName("logfile")) {
      logFilename = str;
    }
    else if (ci.checkName("logdir")) {
      logDirname = str;
    }
    else if (ci.checkName("imagename")) {
      imageName = str;
    }
    else if (ci.checkName("uniquefilename")) {
      uniqueFilenameDigits = intval;
    }
    else if (ci.checkName("port")) {
      if (intval >= 0) {
        remotePort = intval;
      }
      else {
        remotePort = 0;
      }
    }
    else if (ci.checkName("lifelines")) {
      if (intvals != null) {
        if (intvals.length == 1) {
          lifelinesTotal = intvals[0];
          lifelinesPerTest = intvals[0];
        }
        else if (intvals.length == 2) {
          lifelinesTotal = intvals[1];
          lifelinesPerTest = intvals[0];
        }
      }
    }
    else if (ci.checkName("progress")) {
      if (str.equalsIgnoreCase("none")) {
        noProgress = true;
      } else if (str.equalsIgnoreCase("help")) {
        progressHelp = true;
      } else if (str.equalsIgnoreCase("do")) {
        progressDo = true;
      }
    } else if (ci.checkName("log")) {
      if (str.equalsIgnoreCase("all")) {
        logType = 0;
      } else if (str.equalsIgnoreCase("completion")) {
        logType = 1;
      } else if (str.equalsIgnoreCase("summary")) {
        logType = 2;
      } else {
        System.out.println("Invalid configuration entry: " + name + " " + str);
      }
    }
    else if (ci.checkName("NoSingleLevelTests")) {
      inhibitSingleDefaultTests = true;
    } else if (ci.checkName("NoTwoLevelTests")) {
      inhibitTwoDefaultTests = true;
    } else if (ci.checkName("SingleLevelValues")) {
      if (intvals.length == 4) {
        singleTestDefaults = new TestDefaults(intvals[0], intvals[1], intvals[2], intvals[3]);
      }
      else {
        System.out.println("Invalid configuration entry: " + name);
      }
    } else if (ci.checkName("TwoLevelValues")) {
      if (intvals.length == 5) {
        twoTestDefaults = new TestDefaults(intvals[0], intvals[1], intvals[2], intvals[3], intvals[4]);
      }
      else
      {
        System.out.println("Invalid configuration entry: " + name);


      }
      



    }
    else if (ci.checkName("testfilename")) {
      testFilename = str;
    } else if (ci.checkName("emailfrom")) {
      emailFrom = str;
    }
    else if (ci.checkName("emailto")) {
      emailTo = str;
    }
    else if (ci.checkName("emailhost")) {
      emailHost = str;
    }
    else if (ci.checkName("email")) {
      if ("none".equalsIgnoreCase(str)) {
        emailType = 0;
      } else if ("attempt".equalsIgnoreCase(str)) {
        emailType = 1;
      } else if ("success".equalsIgnoreCase(str)) {
        emailType = 2;
      } else if ("done".equalsIgnoreCase(str)) {
        emailType = 3;
      }
    } else
      System.out.println("Invalid configuration entry for " + name);
  }
  
  private void setupButtons() {
    slb = new StandardLogButtons(Utility.hm, username, logFilename, logDirname, imageName, "Virtual Address Translation ", ver2_msg, false, remotePort, this, this);
    

    slb.setUniqueFilenameDigits(uniqueFilenameDigits);
    quitButton = new JeliButton("Quit", this);
    
    logAllEventsButton = new JeliButton("Log Events", this);
    slb.registerLogButton(logAllEventsButton);
    quitButton.setBackground(Utility.jeliLightRed);
    testButton = new JeliButton("Test", this);
    testButton.setPositionCenter();
    
    singleLevelTestsButton = new JeliButton("Single Level", this);
    twoLevelTestsButton = new JeliButton("Two Levels", this);
    singleLevelTestsButton.setPositionCenter();
    twoLevelTestsButton.setPositionCenter();
    singleLevelTestsButton.setBackground(levelOneColor);
    twoLevelTestsButton.setBackground(levelTwoColor);
    singleLevelTestsButton.setButtonSize();
    twoLevelTestsButton.setButtonSize();
    
    debugButton = new JeliButton("", this);
    debugButton.setButtonSize("This is only a test");
    debugButton.setDebugString("debug", 1, this);
    debugButton.setNoMouse(true);
    debugButton.setBorderType(0);
    fileNameLabel = new JeliLabel("");
    fileNameLabel.setBorderType(0);
    fileNameLabel.setButtonSize();
    fileNameLabel.setPositionCenter();
    completeFlags = new boolean[numButtonCount];
    

    for (int i = 0; i < numButtonCount; i++) {
      completeFlags[i] = false;
    }
    

    sessionLifelineLabel = new JeliLabel("Session Lifelines:");
    sessionLifelineLabel.setButtonSize();
    instanceLifelineLabel = new JeliLabel("Current lifelines:");
    instanceLifelineLabel.setButtonSize();
  }
  
  private void makeInfoPanel() {
    JeliPanel[] p1 = new JeliPanel[7];
    JeliPanel namePanel = new JeliPanel();
    namePanel.setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
    JeliLabel t1 = new JeliLabel("Virtual Address Translation");
    t1.setPositionCenter();
    t1.setBorderType(13);
    t1.setButtonSize();
    namePanel.add(t1);
    t1 = new JeliLabel("Simulator version 1.03");
    t1.setPositionCenter();
    t1.setBorderType(14);
    t1.setButtonSize();
    namePanel.add(t1);
    p1[0] = namePanel;
    p1[2] = new JeliLabel("Jeli Version L288 (March 1, 2007)");
    
    p1[1] = new JeliLabel("Last update: January 2, 2007");
    p1[3] = new JeliLabel(Utility.getJavaOnlyVersion());
    p1[4] = new JeliLabel(Utility.getOSVersion());
    JeliPanel completionPanel = new JeliPanel();
    completionPanel.setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
    JeliLabel completionLabel = new JeliLabel("Successfull Tests This Session:");
    completionLabel.setBorderType(0);
    completionLabel.setButtonSize();
    
    completionPanel.add(completionLabel);
    JeliPanel completionListPanel = new JeliPanel();
    completionListPanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, numButtonCount));
    completionListPanel.setBackground(Color.white);
    completionListLabels = new JeliLabel[numButtonCount];
    for (int i = 0; i < numButtonCount; i++) {
      JeliLabel label = new JeliLabel("");
      completionListLabels[i] = label;
      label.setBackground(Color.white);
      label.setMinimalButtonSize("" + maxButtonCount);
      label.setPositionCenter();
      label.setBorderType(0);
      completionListPanel.add(label);
    }
    completionPanel.add(completionListPanel);
    p1[5] = completionPanel;
    debugPanel = new JeliPanel();
    debugPanel.setLayout(new java.awt.GridLayout(1, 1));
    debugPanel.add(debugButton);
    p1[6] = debugPanel;
    debugInfoPanel = new JeliPanel();
    debugInfoPanel.setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
    debugInfoPanel.add(testButton);
    debugInfoPanel.add(logAllEventsButton);
    


    infoPanel = new Jeli.Widgets.CenteredStackedPanels(p1, 2);
  }
  
  private void makeSingleTestsPanel() {
    JeliPanel[] p2 = new JeliPanel[maxButtonCount];
    
    for (int i = 0; i < singleLevelButtonCount; i++) {
      p2[i] = testList.getSingleButton(i);
    }
    for (int i = singleLevelButtonCount; i < maxButtonCount; i++) {
      p2[i] = new JeliLabel("");
      ((JeliButton)p2[i]).setBorderType(0);
    }
    singleLevelButtonsOrig = new JeliButton[singleLevelButtonCount];
    for (int i = 0; i < singleLevelButtonCount; i++)
      singleLevelButtonsOrig[i] = ((JeliButton)p2[i]);
    randomizeArray(p2, 0, singleLevelButtonCount - 1, 6);
    for (int i = 0; i < singleLevelButtonCount; i++)
    {

      JeliButton jb = (JeliButton)p2[i];
      TranslationData td = (TranslationData)jb.getObjectInfo();
      jb.setInfo(i + 1);
      td.setDisplayName("Single Level Page Table Test " + (i + 1));
    }
    singleTestsPanel = new Jeli.Widgets.CenteredStackedPanels(p2, 2);
    singleTestsPanel.setBackground(Color.white);
    singleLevelButtons = new JeliButton[singleLevelButtonCount];
    
    for (int i = 0; i < singleLevelButtonCount; i++) {
      singleLevelButtons[i] = ((JeliButton)p2[i]);
      singleLevelButtons[i].setPositionCenter();
      singleLevelButtons[i].setBackground(levelOneColor);
    }
    setSingleLevelButtonNames();
  }
  

  private void makeTwoTestsPanel()
  {
    JeliPanel[] p3 = new JeliPanel[maxButtonCount];
    for (int i = 0; i < twoLevelButtonCount; i++) {
      p3[i] = testList.getTwoButton(i);
    }
    
    for (int i = twoLevelButtonCount; i < maxButtonCount; i++) {
      p3[i] = new JeliButton("");
      ((JeliButton)p3[i]).setBorderType(0);
    }
    twoLevelButtonsOrig = new JeliButton[twoLevelButtonCount];
    for (int i = 0; i < twoLevelButtonCount; i++)
      twoLevelButtonsOrig[i] = ((JeliButton)p3[i]);
    randomizeArray(p3, 0, twoLevelButtonCount - 1, 6);
    
    twoTestsPanel = new Jeli.Widgets.CenteredStackedPanels(p3, 2);
    twoTestsPanel.setBackground(Color.white);
    twoLevelButtons = new JeliButton[twoLevelButtonCount];
    for (int i = 0; i < twoLevelButtonCount; i++) {
      JeliButton jb = (JeliButton)p3[i];
      TranslationData td = (TranslationData)jb.getObjectInfo();
      jb.setInfo(i + 1);
      td.setDisplayName("Two Level Page Table Test " + (i + 1));
    }
    for (int i = 0; i < twoLevelButtonCount; i++) {
      twoLevelButtons[i] = ((JeliButton)p3[i]);
      
      twoLevelButtons[i].setPositionCenter();
      twoLevelButtons[i].setBackground(levelTwoColor);
    }
    setTwoLevelButtonNames();
  }
  
  private void makeBottomPanel() {
    bottomPanel = new JeliPanel();
    bottomPanel.setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
    lifeLinePanel = new JeliPanel();
    lifeLinePanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
    lifeLinePanel.add(sessionLifelineLabel);
    lifeLinePanel.add(instanceLifelineLabel);
    bottomPanel.add(lifeLinePanel);
    makeQuitPanel();
    bottomPanel.add(quitPanel);
  }
  
  private void makeQuitPanel() {
    quitPanel = new JeliPanel();
    quitPanel.setLayout(new java.awt.BorderLayout());
    

    quitPanel.add("West", quitButton);
    quitPanel.add("Center", fileNameLabel);
    if (remotePort > 0) {
      Panel remotePanel = new Panel();
      remotePanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
      remotePanel.add(slb.getShowRemoteLogButton());
      remotePanel.add(slb.getOpenButton());
      quitPanel.add("East", remotePanel);
    }
    else {
      quitPanel.add("East", slb.getOpenButton());
    }
  }
  
  private void makeMainPanel() {
    int choosePanelSize = 2;
    
    mainPanel = new JeliPanel();
    
    mainPanel.setLayout(new java.awt.BorderLayout());
    choosePanel = new JeliPanel();
    if ((singleLevelButtonCount == 0) || (twoLevelButtonCount == 0))
      choosePanelSize = 1;
    choosePanel.setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
    Color chooseLabelColor; String chooseLabelString; Color chooseLabelColor; if ((singleLevelButtonCount == 0) && (twoLevelButtonCount == 0)) {
      String chooseLabelString = "No Tests Available";
      chooseLabelColor = Utility.jeliLightRed;
    } else { Color chooseLabelColor;
      if (singleLevelButtonCount == 0) {
        String chooseLabelString = "Choose a two level page test below:";
        chooseLabelColor = levelTwoColor;
      } else { Color chooseLabelColor;
        if (twoLevelButtonCount == 0) {
          String chooseLabelString = "Choose a single level page table test below:";
          chooseLabelColor = levelOneColor;
        }
        else {
          chooseLabelString = "Choose the number of page table levels:";
          chooseLabelColor = Color.white;
        } } }
    JeliLabel chooseLabel = new JeliLabel(chooseLabelString);
    if ((singleLevelButtonCount == 0) && (twoLevelButtonCount == 0))
      chooseLabel.setBackground(Utility.jeliLightRed);
    chooseLabel.setButtonSize();
    chooseLabel.setPositionCenter();
    chooseLabel.setBackground(chooseLabelColor);
    
    Panel choosePanelButtons = new Panel();
    choosePanelButtons.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
    choosePanelButtons.add(singleLevelTestsButton);
    choosePanelButtons.add(twoLevelTestsButton);
    choosePanel.add(chooseLabel);
    if (choosePanelSize == 2) {
      choosePanel.add(choosePanelButtons);
    }
    mainPanel.add("North", choosePanel);
    
    buttonTestsPanel = new JeliPanel();
    
    buttonTestsPanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 1));
    
    makeSingleTestsPanel();
    makeTwoTestsPanel();
    if ((singleLevelButtonCount == 0) && (twoLevelButtonCount > 0))
      numberOfLevels = 2;
    if (numberOfLevels == 1) {
      buttonTestsPanel.add(singleTestsPanel);
    }
    else {
      buttonTestsPanel.add(twoTestsPanel);
    }
    mainPanel.add("Center", buttonTestsPanel);
  }
  
  private void setupLayout() {
    setLayout(new java.awt.BorderLayout(10, 0));
    setBackground(Color.white);
    



    makeInfoPanel();
    
    infoPanel.setBackground(Color.white);
    add("West", infoPanel);
    makeMainPanel();
    makeBottomPanel();
    add("East", mainPanel);
    add("South", bottomPanel);
    pack();
  }
  
  private void setSingleLevelTests() {
    System.out.println("Got single level tests button");
    buttonTestsPanel.removeAll();
    buttonTestsPanel.invalidate();
    buttonTestsPanel.validate();
    buttonTestsPanel.add(singleTestsPanel);
    numberOfLevels = 1;
    buttonTestsPanel.invalidate();
    buttonTestsPanel.validate();
    singleTestsPanel.repaint();
  }
  
  private void setTwoLevelTests() {
    System.out.println("Got two level tests button");
    buttonTestsPanel.removeAll();
    buttonTestsPanel.add(twoTestsPanel);
    numberOfLevels = 2;
    buttonTestsPanel.invalidate();
    buttonTestsPanel.validate();
    twoTestsPanel.repaint();
  }
  


  private void setSingleLevelButtonNames()
  {
    for (int i = 0; i < singleLevelButtonCount; i++) {
      JeliButton jb = singleLevelButtons[i];
      TranslationData td = (TranslationData)jb.getObjectInfo();
      if (showTestNames) {
        jb.setLabel(td.getName());
      } else
        jb.setLabel(td.getDisplayName());
      jb.setButtonSize();
    }
  }
  


  private void setTwoLevelButtonNames()
  {
    for (int i = 0; i < twoLevelButtonCount; i++) {
      JeliButton jb = twoLevelButtons[i];
      TranslationData td = (TranslationData)jb.getObjectInfo();
      if (showTestNames) {
        jb.setLabel(td.getName());
      } else
        jb.setLabel(td.getDisplayName());
      jb.setButtonSize();
    }
  }
  
  private void resetLayout() {
    singleTestsPanel.invalidate();
    singleTestsPanel.validate();
    twoTestsPanel.invalidate();
    twoTestsPanel.validate();
    





    mainPanel.invalidate();
    mainPanel.validate();
    

    infoPanel.invalidate();
    infoPanel.validate();
    invalidate();
    validate();
    pack();
  }
  
  private void resetTestButtonNames() {
    setSingleLevelButtonNames();
    setTwoLevelButtonNames();
    resetLayout();
  }
  
  private void handleQuit() {
    java.applet.AppletContext app = Jeli.UtilityIO.getAppletContext();
    setDoneLogString();
    quittingFlag = true;
    af.emailDone();
    closeLogIfNecessary();
    if (app == null) {
      setVisible(false);
      af.setVisible(false);
      System.exit(0);
    }
    else if (backURL != null) {
      setVisible(false);
      af.setVisible(false);
      Utility.setAllInvisible();
      Jeli.UtilityIO.getAppletContext().showDocument(backURL);
    }
    else {
      setVisible(false);
      af.setVisible(false);
      Utility.setAllInvisible();
    }
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == null) {
      System.out.println("Got null button pushed");
      return;
    }
    if (af == null) {
      System.out.println("Button pushed before af set up");
      return;
    }
    if (bh == quitButton) {
      handleQuit();
      return;
    }
    if (bh == logAllEventsButton) {
      af.logAllEvents(null);
      return;
    }
    if (bh == singleLevelTestsButton)
    {
      if (numberOfLevels == 1) {
        return;
      }
      setSingleLevelTests();
      resetLayout();
      return;
    }
    if (bh == twoLevelTestsButton)
    {
      if (numberOfLevels > 1) {
        return;
      }
      setTwoLevelTests();
      resetLayout();
      return;
    }
    if (bh == testButton) {
      showTestNames = (!showTestNames);
      Address.setUseRealName(showTestNames);
      resetTestButtonNames();
      pack();
      System.out.println("Log   Status:\n" + getLogHelpStatusString());
      System.out.println("Email Status:\n" + getEmailHelpStatusString());
      


      return;
    }
    for (int i = 0; i < singleLevelButtonCount; i++) {
      if (bh == singleLevelButtons[i]) {
        af.reset((TranslationData)bh.getObjectInfo());
        af.setTestNumber(bh.getInfo());
        af.setVisible(true);
        instanceLifelines = 0;
        setLifelineLabels();
        return;
      }
    }
    for (int i = 0; i < twoLevelButtonCount; i++) {
      if (bh == twoLevelButtons[i]) {
        af.reset((TranslationData)bh.getObjectInfo());
        af.setTestNumber(bh.getInfo());
        af.setVisible(true);
        instanceLifelines = 0;
        setLifelineLabels();
        return;
      }
    }
    if (bh == fileNameLabel) {
      slb.getShowRemoteLogButton().simulatePushed();
      return;
    }
  }
  






  private void moveIt(java.awt.Component com)
  {
    java.awt.Point pos = Utility.getAbsolutePosition(this);
    java.awt.Dimension dim = Utility.getScreenSize();
    int x = width - getBoundswidth - 200;
    int y = y;
    com.setLocation(x, y);
  }
  
  public void setAttemptExit(boolean f) {}
  
  public void setAllVisible() {
    setVisible(true);
  }
  
  public void setAllInvisible() {}
  
  public void lBCallBack(int type, String str) {
    if (type == 2) {
      fileNameLabel.setLabel("Log File: " + slb.getFilename());
      if ((emailType == 3) && (logType == 0))
        emailDoneLogString += doneLogString.toString();
      doneLogString.setLength(0);
      
      logTotalLifelinesUsed = 0;
      logTotalLifelinesPerformed = 0;
      logProgressHelpUsed = 0;
      logProgressDoUsed = 0;
      Utility.getLogger().logStringSimple(getHelpConfigurationString());
    }
    else if (type == 3) {
      fileNameLabel.setLabel("");
      if ((logType == 2) || (logType == 1)) {
        setDoneLogString();
        Utility.getLogger().logStringSimple(getDoneLogString());
        Utility.getLogger().logStringSimple(getLogHelpStatusString());
      }
    }
    handleOpenLogButton(type, str);
  }
  
  private void closeLogIfNecessary() {
    if (slb.getLoggingFlag())
      slb.getOpenButton().simulatePushed();
  }
  
  private void handleOpenLogButton(int type, String str) {
    if (remotePort <= 0) {
      return;
    }
    if (type == 0) {
      fileNameLabel.setLabel("Error opening remote log");
    }
    else if (type == 1) {
      fileNameLabel.setLabel("Trying to open remote log ...");
    }
    else if (type == 2) {
      fileNameLabel.setLabel("Remote log opened successfully");
      fileNameLabel.setAsNotLabel();
      fileNameLabel.resetJeliButtonCallBack(this);
    }
    else if (type == 3) {
      fileNameLabel.setLabel("Remote log closed");
    }
  }
  

  public void postLine(String str) {}
  
  public void postNoLine(String str) {}
  
  private void randomizeArray(Object[] ar, int first, int last, int num)
  {
    if (last - first + 1 <= 0) {
      return;
    }
    for (int i = 0; i < num; i++) {
      int ind1 = (int)((last - first + 1) * randomizeDist.next()) + first;
      int ind2 = (int)((last - first + 1) * randomizeDist.next()) + first;
      
      if ((ind1 < first) || (ind1 > last) || (ind2 < first) || (ind2 > last)) {
        System.out.println("Error using " + ind1 + " and " + ind2 + " from " + first + " to " + last);

      }
      else
      {
        Object temp = ar[ind1];
        ar[ind1] = ar[ind2];
        ar[ind2] = temp;
      }
    }
  }
  
  private Jeli.Probability.Distribution createRandom(String s)
  {
    double seed = 0.0D;
    for (int i = 0; i < s.length(); i++) {
      seed += (i + 1) * s.charAt(i);
    }
    Jeli.Probability.Distribution dist = new Jeli.Probability.Distribution(3, 0.0D, 1.0D);
    dist.setPrivateSeed(seed);
    return dist;
  }
  
  public void setDebugFound(int id)
  {
    debugPanel.removeAll();
    debugPanel.add(debugInfoPanel);
    debugPanel.invalidate();
    
    infoPanel.invalidate();
    infoPanel.validate();
    validate();
    pack();
  }
  
  private void fillCompleteLabels()
  {
    int j = 0;
    for (int i = 0; i < numButtonCount; i++) {
      if (completeFlags[i] != 0) {
        if (i < singleLevelButtonCount) {
          completionListLabels[j].setLabel("" + (i + 1));
          completionListLabels[j].setBackground(levelOneColor);
        }
        else {
          completionListLabels[j].setLabel("" + (i + 1 - singleLevelButtonCount));
          
          completionListLabels[j].setBackground(levelTwoColor);
        }
        j++;
      }
    }
    while (j < numButtonCount) {
      completionListLabels[j].setLabel("");
      completionListLabels[j].setBackground(Color.white);
      j++;
    }
  }
  


  public void setSingleCompleted(int n)
  {
    if (n >= singleLevelButtonCount) {
      return;
    }
    JeliButton jb = singleLevelButtons[n];
    TranslationData td = (TranslationData)jb.getObjectInfo();
    completeFlags[n] = true;
    completeCount += 1;
    fillCompleteLabels();
  }
  

  public void setTwoCompleted(int n)
  {
    if (n >= twoLevelButtonCount) {
      return;
    }
    JeliButton jb = twoLevelButtons[n];
    TranslationData td = (TranslationData)jb.getObjectInfo();
    completeFlags[(n + singleLevelButtonCount)] = true;
    completeCount += 1;
    fillCompleteLabels();
  }
  

  public void setNotCompleted(String hiddenName)
  {
    for (int i = 0; i < singleLevelButtonCount; i++) {
      JeliButton jb = singleLevelButtons[i];
      TranslationData td = (TranslationData)jb.getObjectInfo();
      if (hiddenName.equals(td.getDisplayName())) {
        failCount += 1;
        break;
      }
    }
    for (int i = 0; i < twoLevelButtonCount; i++) {
      JeliButton jb = twoLevelButtons[i];
      TranslationData td = (TranslationData)jb.getObjectInfo();
      if (hiddenName.equals(td.getDisplayName())) {
        failCount += 1;
        break;
      }
    }
    fillCompleteLabels();
  }
  


  public void setCompleted(String hiddenName)
  {
    for (int i = 0; i < singleLevelButtonCount; i++) {
      JeliButton jb = singleLevelButtons[i];
      TranslationData td = (TranslationData)jb.getObjectInfo();
      if (hiddenName.equals(td.getDisplayName())) {
        completeFlags[i] = true;
        completeCount += 1;
        break;
      }
    }
    for (int i = 0; i < twoLevelButtonCount; i++) {
      JeliButton jb = twoLevelButtons[i];
      TranslationData td = (TranslationData)jb.getObjectInfo();
      if (hiddenName.equals(td.getDisplayName())) {
        completeFlags[(i + singleLevelButtonCount)] = true;
        completeCount += 1;
        break;
      }
    }
    fillCompleteLabels();
  }
  
  private void setLifelineLabels()
  {
    String l1 = "" + sessionLifelines;
    if (lifelinesTotal < Integer.MAX_VALUE)
      l1 = l1 + "/" + lifelinesTotal;
    sessionLifelineLabel.setLabel2(l1);
    String l2 = "" + instanceLifelines;
    if (lifelinesPerTest < Integer.MAX_VALUE)
      l2 = l2 + "/" + lifelinesPerTest;
    instanceLifelineLabel.setLabel2(l2);
    if ((sessionLifelines >= lifelinesTotal) || (instanceLifelines >= lifelinesPerTest))
    {
      af.disableLifelines();
    } else {
      af.enableLifelines();
    }
  }
  
  public void addLifeline() {
    sessionLifelines += 1;
    instanceLifelines += 1;
    setLifelineLabels();
  }
  



  public void setDoneLogString()
  {
    int numCompleted = 0;
    if (quittingFlag) {
      return;
    }
    if (logType == 2) {
      doneLogString.setLength(0);
      
      for (int i = 0; i < singleLevelButtonCount; i++) {
        JeliButton jb = singleLevelButtonsOrig[i];
        TranslationData td = (TranslationData)jb.getObjectInfo();
        if ((completeCount > 0) || (failCount > 0))
          numCompleted++;
      }
      for (int i = 0; i < twoLevelButtonCount; i++) {
        JeliButton jb = twoLevelButtonsOrig[i];
        TranslationData td = (TranslationData)jb.getObjectInfo();
        if ((completeCount > 0) || (failCount > 0)) {
          numCompleted++;
        }
      }
      if (numCompleted == 0) {
        doneLogString.append("No tests attempted.\n");
      } else {
        doneLogString.append("The following is a summary of attempts:<br>\n");
        doneLogString.append("<table border =1><TR><TD>Test</TD>");
        doneLogString.append("<TD>Successes</TD><TD>Failures</TD></TR>\n");
        
        for (int i = 0; i < singleLevelButtonCount; i++) {
          JeliButton jb = singleLevelButtonsOrig[i];
          TranslationData td = (TranslationData)jb.getObjectInfo();
          int succ = completeCount;
          int fail = failCount;
          if ((succ > 0) || (fail > 0)) {
            doneLogString.append("<TR><TD>");
            doneLogString.append(td.getName());
            doneLogString.append("</TD><TD align=right>" + succ);
            doneLogString.append("</TD><TD align=right>" + fail);
            doneLogString.append("</TD></TR>\n");
          }
        }
        for (int i = 0; i < twoLevelButtonCount; i++) {
          JeliButton jb = twoLevelButtonsOrig[i];
          TranslationData td = (TranslationData)jb.getObjectInfo();
          int succ = completeCount;
          int fail = failCount;
          if ((succ > 0) || (fail > 0)) {
            doneLogString.append("<TR><TD>");
            doneLogString.append(td.getName());
            doneLogString.append("</TD><TD align=right>" + succ);
            doneLogString.append("</TD><TD align=right>" + fail);
            doneLogString.append("</TD></TR>\n");
          }
        }
        doneLogString.append("</table>\n");
      }
      
    }
    else if (logType == 1)
    {
      doneLogString.setLength(0);
      for (int i = 0; i < numButtonCount; i++)
        if (completeFlags[i] != 0)
          numCompleted++;
      if (numCompleted == 0) {
        doneLogString.append("No tests successfully completed.\n");
      } else {
        doneLogString.append("The following tests were completed:\n<UL>\n");
        for (int i = 0; i < singleLevelButtonCount; i++) {
          JeliButton jb = singleLevelButtonsOrig[i];
          TranslationData td = (TranslationData)jb.getObjectInfo();
          if (completeCount > 0)
          {
            doneLogString.append(td.getName());
            doneLogString.append("<br>\n");
          }
        }
        for (int i = 0; i < twoLevelButtonCount; i++) {
          JeliButton jb = twoLevelButtonsOrig[i];
          TranslationData td = (TranslationData)jb.getObjectInfo();
          if (completeCount > 0)
          {
            doneLogString.append(td.getName());
            doneLogString.append("<br>\n");
          }
        }
        doneLogString.append("</UL>\n");
      }
    }
  }
  
  public String getDoneLogString() {
    return doneLogString.toString();
  }
  

  public String getEmailDoneLogString()
  {
    return emailDoneLogString + doneLogString.toString();
  }
  
  public void appendToLogString(String s) {
    doneLogString.append(s);
  }
  
  public String getHelpConfigurationString() {
    String progressConfig;
    String progressConfig;
    if (noProgress) {
      progressConfig = "Progress frame is disabled."; } else { String progressConfig;
      if (progressDo) {
        progressConfig = "Progress do enabled."; } else { String progressConfig;
        if (progressHelp) {
          progressConfig = "Progress help enabled.";
        } else
          progressConfig = "No help rom progress frame."; } }
    String lifelinesConfig; String lifelinesConfig; if (lifelinesTotal == 0) {
      lifelinesConfig = "<br>Lifelines disabled";
    } else {
      lifelinesConfig = "<br>Total lifelines: " + unlimitTest(lifelinesTotal) + "<br>Lifelines per test: " + unlimitTest(lifelinesPerTest);
    }
    return progressConfig + " " + lifelinesConfig + "<p>\n";
  }
  
  private String unlimitTest(int n) {
    if (n < Integer.MAX_VALUE) {
      return "" + n;
    }
    return "unlimited";
  }
  
  public String getHelpStatusString(int lifelinesUsed, int lifelinesPerformed, int progressHelpUsed, int progressDoUsed)
  {
    String lifelinesInfo;
    String lifelinesInfo;
    if (lifelinesTotal > 0) {
      lifelinesInfo = "Lifelines used: " + lifelinesUsed + " Lifeline steps performed: " + lifelinesPerformed;
    }
    else
      lifelinesInfo = "";
    String progressHelpInfo; String progressHelpInfo; if (progressDo) {
      progressHelpInfo = "Progress help total: " + progressHelpUsed + " Progess do total: " + progressDoUsed;
    } else { String progressHelpInfo;
      if (progressHelp) {
        progressHelpInfo = "Progress help total: " + progressHelpUsed;
      } else
        progressHelpInfo = "";
    }
    return "<p>" + lifelinesInfo + "<br>\n" + progressHelpInfo + "<p>\n";
  }
  
  public String getLogHelpStatusString() {
    return getHelpStatusString(logTotalLifelinesUsed, logTotalLifelinesPerformed, logProgressHelpUsed, logProgressDoUsed);
  }
  

  public String getEmailHelpStatusString()
  {
    return getHelpStatusString(emailTotalLifelinesUsed, emailTotalLifelinesPerformed, emailProgressHelpUsed, emailProgressDoUsed);
  }
  


  public void incrementLifelinesUsed()
  {
    emailTotalLifelinesUsed += 1;
    logTotalLifelinesUsed += 1;
  }
  
  public void incrementLifelinesPerformed() {
    emailTotalLifelinesPerformed += 1;
    logTotalLifelinesPerformed += 1;
  }
  
  public void incrementProgressHelp() {
    emailProgressHelpUsed += 1;
    logProgressHelpUsed += 1;
  }
  
  public void incrementProgressDo() {
    emailProgressDoUsed += 1;
    logProgressDoUsed += 1;
  }
}
