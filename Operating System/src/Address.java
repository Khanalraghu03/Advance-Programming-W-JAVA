//import Jeli.Widgets.JeliButton;
//import Jeli.Widgets.JeliLabel;
//import Jeli.Widgets.JeliPanel;
//import Jeli.Widgets.SegmentedPanel;
//import Jeli.Widgets.SegmentedPanelMode;
//
//public class Address extends Jeli.Widgets.JeliFrame implements Jeli.Widgets.ClipBoard, Jeli.Widgets.ObjectCallBack, Jeli.Widgets.DebugRegistration
//{
//  public static int LOGICAL_ADDRESS_ID = 1;
//  public static int PHYSICAL_ADDRESS_ID = 2;
//  public static int TLB_ID = 3;
//  public static int CLIPBOARDS_ID = 5;
//  public static int MEMORY_DISPLAY_ID = 6;
//  private static boolean sizeChanged = false;
//  public static java.awt.Color helpColor = Jeli.Utility.jeliLightGreen;
//  public static java.awt.Color hideColor = Jeli.Utility.jeliLightRed;
//
//
//
//  private static boolean useRealName = false;
//  private static AddressMenu adt = null;
//
//  private int emailType = 0;
//  private int logType = 0;
//  private int numSec = 10;
//
//  private int lifelineCount;
//  private int automaticStepCount;
//  private ActionEventList ael;
//  private int pageTableLevels = 1;
//  private int pageSize = 1024;
//  private int logicalAddressBits = 32;
//  private int physicalAddressBits = 24;
//  private int TLBEntries = 16;
//  private int offsetBits = -1;
//
//  private SegmentedPanel spLogical;
//  private SegmentedPanel spPhysical;
//  private SegmentedPanelMode spLogicalMode;
//  private SegmentedPanelMode spPhysicalMode;
//  private JeliLabel levelsLabel;
//  private JeliButton helpButton;
//  private JeliButton lifelineButton;
//  private JeliLabel nameLabel;
//  private JeliButton pageTableStartAddressButton;
//  private JeliLabel pageTableEntryBytesLabel;
//  private JeliLabel pageSizeLabel;
//  private JeliLabel secondLevelTableSizeLabel;
//  private JeliLabel vAddrBitsLabel;
//  private JeliLabel pAddrBitsLabel;
//  private JeliLabel tLBEntriesLabel;
//  private JeliLabel completionLabel;
//  private TLB tlb;
//  private Memory mem;
//  private java.awt.Font f;
//  private TLBDisplay tlbDisplay = null;
//
//  private MemoryDisplay memDisplay = null;
//
//  private Jeli.Widgets.ClipBoards clipboards;
//  private ProgressTracker progress;
//  private boolean singleLevel;
//  private JeliButton showTLBButton;
//  private JeliButton showProgressButton;
//  private JeliButton showPageTableButton;
//  private JeliButton foundAddressButton;
//  private JeliButton pageFaultButton;
//  private JeliButton invalidReferenceButton;
//  private JeliButton statusButton;
//  private JeliButton testButton;
//  private JeliButton calculatorButton;
//  private JeliButton performStepButton = null;
//
//  private JeliPanel logicalAddressPanel;
//  private JeliPanel physicalAddressPanel;
//  private JeliPanel pageTableStartAddressPanel;
//  private JeliPanel donePanelOuter;
//  private Jeli.Probability.Distribution seedDist = null;
//  private TranslationData data = null;
//  private Jeli.Widgets.CalculatorFrame calculatorFrame;
//  private int helpMsgTimeout = 10;
//
//  private java.awt.Color completionColor = Jeli.Utility.jeliLightYellow;
//  private int testNumber = 0;
//  private boolean noProgress = false;
//  private boolean progressHelp = false;
//  private boolean progressDo = false;
//
//  private String helpMsg = "Your goal to to convert a logical address to a phtsical address or to\n   determine that a page fault has occured.\nThe table at the top of this window shows the parameters for this test.\nBelow this is the address of the start of the top level page table.\nNext are widgets for manipulating hte logical and physical addresses.\n   Each of these has its own help menu.\nNext, in yellow, are the button to puch when you have determined the\n   correct physical address or that a page fault will occur\nThis is followe by a status message to indicate whether you have\n   successfully completed the address translation.\nLastly, at the very bottom of the window are some buttons which display\n   the TLB, a progress list, memory and a calculator.\n";
//
//
//
//
//
//
//
//
//
//
//
//
//  public Address(Jeli.Probability.Distribution seedDist)
//  {
//    super("Virtual Address Translation");
//    setInhibitVisibleComponent(true);
//    this.seedDist = seedDist;
//    singleLevel = true;
//    clipboards = new Jeli.Widgets.ClipBoards();
//
//    ael = new ActionEventList();
//    setupLayout();
//    resetupLayout(null);
//    spLogical.setClipBoard(clipboards);
//    spPhysical.setClipBoard(clipboards);
//    progress = new ProgressTracker(this);
//    progress.setChangeNotify(this);
//    showTLBButton.disableJeliButton();
//    showProgressButton.disableJeliButton();
//    showPageTableButton.disableJeliButton();
//    testButton.disableJeliButton();
//    foundAddressButton.disableJeliButton();
//    pageFaultButton.disableJeliButton();
//    addWindowListener(this);
//  }
//
//  public static void setUseRealName(boolean f) {
//    useRealName = f;
//  }
//
//  public void setEmailType(int type) {
//    emailType = type;
//  }
//
//  public void setLogType(int type) {
//    logType = type;
//  }
//
//  public void setTestNumber(int i) {
//    testNumber = i;
//  }
//
//
//  private void setLabels(TranslationData data)
//  {
//    long pta = pageTableStartFrame << pageSizeBits;
//    levelsLabel.setLabel2("" + pageTableLevels);
//    pageTableStartAddressButton.setLabel2(Long.toString(pta, 2));
//    pageTableEntryBytesLabel.setLabel2("" + pageTableEntryBytes);
//    pageSizeLabel.setLabel2("" + pageSize);
//    secondLevelTableSizeLabel.setLabel2("xxx");
//    vAddrBitsLabel.setLabel2("" + logicalAddressBits);
//    pAddrBitsLabel.setLabel2("" + physicalAddressBits);
//    if (useTlb) {
//      tLBEntriesLabel.setLabel2("" + TLBEntries);
//    }
//    else {
//      tLBEntriesLabel.setLabel2("0");
//    }
//    if (pageTableLevels == 1) {
//      secondLevelTableSizeLabel.setLabel2("0");
//    }
//    else {
//      long secondLevelSize = 1 << logicalAddress2Bits;
//      secondLevelTableSizeLabel.setLabel2("" + secondLevelSize);
//    }
//    statusButton.setLabel("In Progress");
//    statusButton.setBackground(java.awt.Color.white);
//  }
//
//
//  private void setupLayout()
//  {
//    setBackground(java.awt.Color.white);
//
//    JeliPanel[] mainPanels = new JeliPanel[8];
//    java.awt.GridBagLayout gbl = new java.awt.GridBagLayout();
//    java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
//    setLayout(gbl);
//    weightx = 1.0D;
//    weighty = 0.0D;
//    gridx = 0;
//    gridy = 0;
//    anchor = 12;
//    fill = 0;
//    helpButton = new JeliButton("Help", this);
//    helpButton.setBackground(helpColor);
//    helpButton.setButtonSize();
//    gbl.setConstraints(helpButton, c);
//    add(helpButton);
//    anchor = 18;
//    lifelineButton = new JeliButton("Lifeline", this);
//    lifelineButton.setBackground(helpColor);
//    lifelineButton.setButtonSize();
//    gbl.setConstraints(lifelineButton, c);
//    add(lifelineButton);
//    anchor = 11;
//    nameLabel = new JeliLabel("a test");
//    nameLabel.setPositionCenter();
//    nameLabel.setButtonSize();
//    nameLabel.setBackground(java.awt.Color.white);
//    nameLabel.setBorderType(0);
//
//
//    gridy = 1;
//    anchor = 10;
//    fill = 2;
//
//    mainPanels[0] = nameLabel;
//
//    logicalAddressPanel = new JeliPanel();
//    logicalAddressPanel.setLayout(new java.awt.GridLayout(1, 1));
//    physicalAddressPanel = new JeliPanel();
//    physicalAddressPanel.setLayout(new java.awt.GridLayout(1, 1));
//    pageTableStartAddressPanel = new JeliPanel();
//    pageTableStartAddressPanel.setLayout(new java.awt.GridLayout(1, 1));
//
//    f = new java.awt.Font("Times", 0, 10);
//
//
//    levelsLabel = new JeliLabel("Levels:");
//
//    pageTableStartAddressButton = new JeliButton("Page Table Start Address:", this);
//    pageTableStartAddressPanel.add(pageTableStartAddressButton);
//    pageTableEntryBytesLabel = new JeliLabel("Page Table Width:");
//    pageSizeLabel = new JeliLabel("Page Size:");
//    secondLevelTableSizeLabel = new JeliLabel("Level 2 Page Table Size:");
//    vAddrBitsLabel = new JeliLabel("Virtual Address Bits:");
//    pAddrBitsLabel = new JeliLabel("Physical Address Bits:");
//    pAddrBitsLabel.setButtonSize("Physical Address Bits: yyyyyyyyy");
//    tLBEntriesLabel = new JeliLabel("TLB Entries:");
//
//    JeliPanel infoPanel = new JeliPanel();
//    infoPanel.setLayout(new Jeli.Widgets.JeliGridLayout(7, 1));
//    infoPanel.add(levelsLabel);
//    infoPanel.add(pageSizeLabel);
//    infoPanel.add(secondLevelTableSizeLabel);
//    infoPanel.add(vAddrBitsLabel);
//    infoPanel.add(pAddrBitsLabel);
//    infoPanel.add(tLBEntriesLabel);
//    infoPanel.add(pageTableEntryBytesLabel);
//    mainPanels[1] = infoPanel;
//
//    calculatorFrame = new Jeli.Widgets.CalculatorFrame("Calculator", 10, clipboards);
//    calculatorFrame.addWindowListener(calculatorFrame);
//    calculatorFrame.setHelpBackground(helpColor);
//    calculatorFrame.setHideBackground(hideColor);
//    mainPanels[2] = pageTableStartAddressPanel;
//
//    mainPanels[3] = logicalAddressPanel;
//    mainPanels[4] = physicalAddressPanel;
//
//    donePanelOuter = new JeliPanel();
//    donePanelOuter.setBackground(java.awt.Color.white);
//    donePanelOuter.setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
//    completionLabel = new JeliLabel("Choose a completion button when done");
//    completionLabel.setPositionCenter();
//    completionLabel.setButtonSize();
//    completionLabel.setBackground(completionColor);
//    JeliPanel donePanel = new JeliPanel();
//    donePanel.setLayout(new java.awt.GridLayout(1, 3, 10, 0));
//    foundAddressButton = new JeliButton("Found Physical Address", this);
//    pageFaultButton = new JeliButton("Page Fault", this);
//    invalidReferenceButton = new JeliButton("Invalid Reference", this);
//    foundAddressButton.setPositionCenter();
//    pageFaultButton.setPositionCenter();
//    invalidReferenceButton.setPositionCenter();
//    foundAddressButton.setButtonSize();
//    pageFaultButton.setButtonSize();
//    invalidReferenceButton.setButtonSize();
//    foundAddressButton.setBackground(completionColor);
//    pageFaultButton.setBackground(completionColor);
//    invalidReferenceButton.setBackground(completionColor);
//    donePanel.add(foundAddressButton);
//    donePanel.add(pageFaultButton);
//    donePanel.add(invalidReferenceButton);
//    donePanelOuter.add(completionLabel);
//    donePanelOuter.add(donePanel);
//    singleLevel = false;
//    mainPanels[5] = donePanelOuter;
//
//    statusButton = new JeliButton("In Progress", this);
//    statusButton.setButtonSize("  Invalid Memory Reference Incorrectly Identified  ");
//
//    statusButton.setPositionCenter();
//
//    JeliButton[] statusPanels = new JeliButton[2];
//    statusPanels[0] = new JeliLabel("Status");
//    statusPanels[0].setBorderType(13);
//    statusPanels[1] = statusButton;
//    mainPanels[6] = new Jeli.Widgets.CenteredStackedPanels(statusPanels, 0);
//
//
//    JeliPanel[] showPanel = new JeliPanel[6];
//    showPanel[0] = new JeliLabel("Show:");
//    ((JeliButton)showPanel[0]).setBorderType(0);
//    showProgressButton = new JeliButton("Progress", this);
//    showPanel[1] = showProgressButton;
//    showTLBButton = new JeliButton("TLB", this);
//    showPanel[2] = showTLBButton;
//    showPageTableButton = new JeliButton("Memory View", this);
//    showPanel[3] = showPageTableButton;
//    calculatorButton = new JeliButton("Calculator", this);
//    showPanel[4] = calculatorButton;
//    testButton = new JeliButton("", this);
//    testButton.setButtonSize("test");
//    testButton.setDebugString("debug", 1, this);
//    testButton.setNoMouse(true);
//    testButton.setBorderType(0);
//
//    showPanel[5] = testButton;
//    mainPanels[7] = new Jeli.Widgets.HorizontalPanels(showPanel, 3);
//
//    JeliPanel centerPanel = new Jeli.Widgets.CenteredStackedPanels(mainPanels, 3);
//    gbl.setConstraints(centerPanel, c);
//    add(centerPanel);
//
//
//    pack();
//  }
//
//  private void resetupLayout(TranslationData data)
//  {
//    long logicalAddress = 0L;
//    int logicalAddressBits = 32;
//    long physicalAddress = 0L;
//
//
//    this.data = data;
//
//    if (data != null) {
//      if (useRealName) {
//        nameLabel.setLabel(data.getName());
//      } else
//        nameLabel.setLabel(data.getDisplayName());
//      nameLabel.setButtonSize();
//      logicalAddress = logicalAddress;
//      logicalAddressBits = logicalAddressBits;
//      physicalAddress = physicalAddress;
//      long pta = pageTableStartFrame << pageSizeBits;
//      pageTableStartAddressButton.setButtonSize("Page Table Start Address: " + Long.toString(pta, 2));
//    }
//
//    if ((data != null) && (singleLevel) && (pageTableLevels != 1)) {
//      donePanelOuter.removeAll();
//      java.awt.Panel donePanel = new java.awt.Panel();
//      donePanel.setLayout(new java.awt.GridLayout(1, 3, 10, 0));
//      donePanel.add(foundAddressButton);
//      donePanel.add(pageFaultButton);
//      donePanel.add(invalidReferenceButton);
//      donePanelOuter.add(completionLabel);
//      donePanelOuter.add(donePanel);
//      singleLevel = false;
//    }
//    if ((data != null) && (!singleLevel) && (pageTableLevels == 1)) {
//      donePanelOuter.removeAll();
//      java.awt.Panel donePanel = new java.awt.Panel();
//      donePanel.setLayout(new java.awt.GridLayout(1, 2, 10, 0));
//      donePanel.add(foundAddressButton);
//      donePanel.add(pageFaultButton);
//      donePanelOuter.add(completionLabel);
//      donePanelOuter.add(donePanel);
//      singleLevel = true;
//    }
//
//
//
//    ael.clear();
//    ael.resetTime();
//    pageTableStartAddressPanel.removeAll();
//    pageTableStartAddressPanel.add(pageTableStartAddressButton);
//    logicalAddressPanel.removeAll();
//    logicalAddressPanel.add(this.spLogicalMode = new SegmentedPanelMode(logicalAddressBits, addressToBits(logicalAddress), '0', f, 4, "Logical Address", null, false, true, true, false, true, true));
//
//
//
//
//
//
//    spLogical = spLogicalMode.getSegmentedPanel();
//    spLogical.setModeSegment();
//    spLogicalMode.setUseModePositionDefault(true);
//    spLogicalMode.setHelpBackground(helpColor);
//    spLogicalMode.reset();
//    spLogical.setMaxSegments(1);
//    if (data != null) {
//      spLogical.setMaxSegments(pageTableLevels);
//    }
//
//    spLogicalMode.setStandardHelpMessage("Logical Address", 10);
//    physicalAddressPanel.removeAll();
//
//
//    physicalAddressPanel.add(this.spPhysicalMode = new SegmentedPanelMode(physicalAddressBits, "", '0', f, 4, "Physical Address", null, false, true, false, true, true, true));
//
//
//
//    spPhysical = spPhysicalMode.getSegmentedPanel();
//    spPhysical.setModeSegment();
//    spPhysicalMode.setUseModePositionDefault(true);
//    spPhysicalMode.setHelpBackground(helpColor);
//    spPhysicalMode.reset();
//    spPhysical.setMaxSegments(1);
//    spPhysicalMode.setStandardHelpMessage("Physical Address", 10);
//    logicalAddressPanel.invalidate();
//    physicalAddressPanel.invalidate();
//    donePanelOuter.invalidate();
//    pageTableStartAddressPanel.invalidate();
//    logicalAddressPanel.validate();
//    physicalAddressPanel.validate();
//    pageTableStartAddressPanel.validate();
//    donePanelOuter.validate();
//    validate();
//    pack();
//  }
//
//
//  public void jeliButtonPushed(JeliButton bh)
//  {
//    if (bh == pageTableStartAddressButton) {
//      clipboards.setClipBoardString(bh.getLabel2());
//    }
//    if (bh == showTLBButton) {
//      logButtonPush(bh);
//      createTLBDisplay();
//
//      tlbDisplay.setVisible(true);
//    }
//
//    if (bh == showProgressButton) {
//      logButtonPush(bh);
//      progress.showFrame(true);
//      progress.setFrameLocation(Jeli.Utility.getNextLocation(progress));
//    }
//    if (bh == showPageTableButton) {
//      logButtonPush(bh);
//      createMemoryDisplay();
//    }
//
//    if (bh == foundAddressButton) {
//      if ((data.useTlb) && (!progress.getTlbSearchCompleted())) {
//        statusButton.setLabel("Must Search TLB First");
//        statusButton.setBackground(Jeli.Utility.jeliLightRed);
//        addCompletionEvent();
//        logAttempt();
//      }
//      else if ((progress.getPhysicalAddressFound()) && (!data.pageFault) && (data.legalPage))
//      {
//        statusButton.setLabel("Correct Physical Address Found");
//        statusButton.setBackground(Jeli.Utility.jeliLightGreen);
//        addCompletionEvent();
//        logEmailCompletion();
//      }
//      else {
//        statusButton.setLabel("Physical Address is Incorrect");
//        statusButton.setBackground(Jeli.Utility.jeliLightRed);
//        addCompletionEvent();
//        logAttempt();
//      }
//    }
//    if (bh == pageFaultButton) {
//      if ((data.useTlb) && (!progress.getTlbSearchCompleted())) {
//        statusButton.setLabel("Must Search TLB First");
//        statusButton.setBackground(Jeli.Utility.jeliLightRed);
//        addCompletionEvent();
//        logAttempt();
//      }
//      else if (progress.getPageFaultFound()) {
//        statusButton.setLabel("Page Fault Correctly Identifed");
//        statusButton.setBackground(Jeli.Utility.jeliLightGreen);
//        addCompletionEvent();
//        logEmailCompletion();
//      }
//      else {
//        statusButton.setLabel("Page Fault Incorrectly Identified");
//        statusButton.setBackground(Jeli.Utility.jeliLightRed);
//        addCompletionEvent();
//        logAttempt();
//      }
//    }
//    if (bh == invalidReferenceButton) {
//      if ((data.useTlb) && (!progress.getTlbSearchCompleted())) {
//        statusButton.setLabel("Must Search TLB First");
//        statusButton.setBackground(Jeli.Utility.jeliLightRed);
//        addCompletionEvent();
//        logAttempt();
//      }
//      else if (progress.getInvalidPageNumberFound()) {
//        statusButton.setLabel("Invalid Memory Reference Correctly Identifed");
//        statusButton.setBackground(Jeli.Utility.jeliLightGreen);
//        addCompletionEvent();
//        logEmailCompletion();
//      }
//      else {
//        statusButton.setLabel("Invalid Memory Reference Incorrectly Identified");
//        statusButton.setBackground(Jeli.Utility.jeliLightRed);
//        addCompletionEvent();
//        logAttempt();
//      }
//    }
//    if (bh == calculatorButton) {
//      logButtonPush(bh);
//      calculatorFrame.setVisible(true);
//    }
//    if (bh == testButton) {
//      testButtonFound();
//    }
//    if (bh == helpButton) {
//      new Jeli.Widgets.SelfDestructingShortInfoFrame("Address Translation Help", helpMsg, numSec, helpButton);
//    }
//
//    if (bh == lifelineButton) {
//      adt.incrementLifelinesUsed();
//      getLifeline();
//    }
//    else if (bh == performStepButton) {
//      adt.incrementLifelinesPerformed();
//      performStep();
//    }
//  }
//
//
//
//  private void getLifeline()
//  {
//    int pos = progress.getHintPosition();
//    if (pos < 0) {
//      showLifeline("No Lifeline Available", numSec);
//    }
//    else {
//      String hintName = progress.getHintTitle(pos);
//      ael.addEvent(new AddressActionEvent(15, hintName));
//
//      String hint = progress.getHint(pos, this);
//      showLifeline(hint, -1);
//      lifelineCount += 1;
//      adt.addLifeline();
//    }
//  }
//
//  public SegmentedPanel getLogicalPanel() {
//    return spLogical;
//  }
//
//  public SegmentedPanel getPhysicalPanel() {
//    return spPhysical;
//  }
//
//  private void showLifeline(String s, int num) {
//    if (performStepButton != null) {
//      performStepButton.disableJeliButton();
//    }
//    performStepButton = new JeliButton("Perform This Step", this);
//    performStepButton.setPositionCenter();
//    performStepButton.setBackground(Jeli.Utility.jeliLightGreen);
//    new Jeli.Widgets.SelfDestructingShortInfoFrame("Lifeline", s, num, helpButton, lifelineButton, performStepButton);
//  }
//
//
//
//  private void logAttempt()
//  {
//    String s = "Test " + testNumber + ": " + data.getName();
//    setNotCompleted();
//    if (emailType == 1) {
//      boolean ok = Jeli.Utility.sendHtmlMessage(adt.getHelpConfigurationString() + ael.getLogString(s, getFooter()), adt.getUsername() + ": Unsuccessful Attempt");
//
//
//
//      if (ok) {
//        System.out.println("Email sent for LogAttempt");
//      } else {
//        System.out.println("Email not sent");
//      }
//    }
//  }
//
//
//  private void logEmailCompletion()
//  {
//    String s = "Test " + testNumber + ": " + data.getName();
//    if (logType == 0) {
//      logAllEvents(s);
//    }
//    if ((emailType == 1) || (emailType == 2))
//    {
//      boolean ok = Jeli.Utility.sendHtmlMessage(adt.getHelpConfigurationString() + ael.getLogString(s, getFooter()), adt.getUsername() + ": Successful Completion");
//
//
//
//      if (ok) {
//        System.out.println("Email sent for logEmailCompletion");
//      } else
//        System.out.println("Email not sent");
//    }
//    setCompleted();
//    progress.setAllDone();
//  }
//
//
//  public void emailDone()
//  {
//    if (emailType == 3) {
//      boolean ok = Jeli.Utility.sendHtmlMessage(adt.getHelpConfigurationString() + adt.getEmailDoneLogString() + adt.getEmailHelpStatusString(), adt.getUsername() + ": Done");
//
//
//
//      if (ok) {
//        System.out.println("Email sent for EMAIL_TYPE_DONE");
//      } else
//        System.out.println("Email not sent");
//    }
//  }
//
//  private void addCompletionEvent() {
//    ael.addEvent(new AddressActionEvent(14, statusButton.getLabel()));
//  }
//
//
//
//  private void createMemoryDisplay()
//  {
//    if (memDisplay == null) {
//      memDisplay = new MemoryDisplay(data, mem, tlb, 16, clipboards, MemoryDisplay.MODE_PAGE_TOP);
//
//
//      memDisplay.setLocation(Jeli.Utility.getNextLocation(memDisplay));
//      memDisplay.setChangeNotify(MEMORY_DISPLAY_ID, progress);
//    }
//    memDisplay.setVisible(true);
//    java.awt.Rectangle bounds = memDisplay.getBounds();
//  }
//
//
//  private boolean checkValidValues()
//  {
//    if (pageTableLevels < 0) {
//      System.out.println("Invalid number of page table levels: " + pageTableLevels);
//
//      return false;
//    }
//    if (pageTableLevels > 4) {
//      System.out.println("Too many page table levels, max is 4");
//      return false;
//    }
//    offsetBits = getBits(pageSize);
//    if (offsetBits <= 0) {
//      System.out.println("page size (" + pageSize + ") must be a power of 2");
//      return false;
//    }
//    if (logicalAddressBits < offsetBits + pageTableLevels) {
//      System.out.println("virtual address size (" + logicalAddressBits + ") is too small");
//
//      return false;
//    }
//    if (physicalAddressBits <= offsetBits) {
//      System.out.println("physical address size (" + physicalAddressBits + ") is too small");
//
//      return false;
//    }
//    if (TLBEntries < 0) {
//      TLBEntries = 0;
//    }
//    if (TLBEntries > 64) {
//      System.out.println("Too many TLB entries (" + TLBEntries + "), max is 64");
//      return false;
//    }
//    return true;
//  }
//
//  private int getBits(int n) {
//    int bits = 0;
//    if (n < 0) {
//      return -1;
//    }
//    while (n > 1) {
//      if (n / 2 * 2 != n) {
//        return -1;
//      }
//      n /= 2;
//      bits++;
//    }
//    return bits;
//  }
//
//  public void createTLBDisplayVisible() {
//    createTLBDisplay();
//    tlbDisplay.setVisible(true);
//  }
//
//  public void setTLBSegmentMode() {
//    if (tlbDisplay == null) {
//      return;
//    }
//    tlbDisplay.setSegmentMode();
//  }
//
//  public void setLogicalAddressSegmentMode() {
//    spLogicalMode.setModeSegment();
//  }
//
//  public void setLogicalAddressSelectMode() {
//    spLogicalMode.setModeSelect();
//  }
//
//  public void setPhysicalAddressSegmentMode() {
//    spPhysicalMode.setModeSegment();
//  }
//
//  public void setPhysicalAddressPasteMode() {
//    spPhysicalMode.setModePaste();
//  }
//
//  private void createTLBDisplay() {
//    if (tlbDisplay == null) {
//      tlbDisplay = new TLBDisplay(tlb, helpMsgTimeout);
//      tlbDisplay.setClipBoard(clipboards);
//      tlbDisplay.setChangeNotify(TLB_ID, progress);
//      tlbDisplay.setChangeNotify(TLB_ID, this);
//      clipboards.addClipBoard(tlbDisplay);
//      Jeli.Utility.standardScale(tlbDisplay);
//
//      tlbDisplay.setLocation(Jeli.Utility.getNextLocation(tlbDisplay));
//    }
//  }
//
//  public void reset(TranslationData data)
//  {
//
//    if (tlbDisplay != null) {
//      tlbDisplay.setVisible(false);
//    }
//    tlbDisplay = null;
//    progress.showFrame(false);
//    clipboards.setClipBoardString("");
//    helpMsgTimeout = helpMsgTimeout;
//
//    pageSize = (1 << pageSizeBits);
//    logicalAddressBits = logicalAddressBits;
//    physicalAddressBits = physicalAddressBits;
//    TLBEntries = tlbEntries;
//
//    Jeli.Utility.resetNextLocation(null);
//
//
//    calculatorFrame.reset(logicalAddressBits);
//
//
//
//    mem = new Memory(physicalAddressBits, byteOrderLittle, seedDist.next());
//    tlb = new TLB(TLBEntries, logicalAddressBits - pageSizeBits, physicalAddressBits - pageSizeBits, mem, pageTableStartFrame << pageSizeBits, pageTableEntryBytes, seedDist.next());
//
//
//
//
//    if (pageTableLevels == 1) {
//      tlb.initialize(pageNumber, frameNumber, inTlb, pageFault);
//    }
//    else
//    {
//      topFrame = tlb.initialize2(pageNumber, frameNumber, inTlb, legalPage, pageFault, logicalAddress2Bits, pageSizeBits);
//    }
//
//
//
//
//    clipboards.reset();
//    clipboards.addClipBoard(this);
//
//    calculatorFrame.resetClipboard(clipboards);
//    if (memDisplay != null) {
//      memDisplay.setVisible(false);
//    }
//    memDisplay = null;
//    lifelineCount = 0;
//    automaticStepCount = 0;
//
//    resetupLayout(data);
//    spLogical.setClipBoard(clipboards);
//    spPhysical.setClipBoard(clipboards);
//    setLabels(data);
//    progress = new ProgressTracker(this);
//    progress.setHelp(progressHelp, progressDo);
//    progress.setChangeNotify(this);
//    progress.initialize(data);
//
//
//    spLogical.setChangeNotify(LOGICAL_ADDRESS_ID, progress);
//    spLogical.setChangeNotify(LOGICAL_ADDRESS_ID, this);
//    spPhysical.setChangeNotify(PHYSICAL_ADDRESS_ID, progress);
//    spPhysical.setChangeNotify(PHYSICAL_ADDRESS_ID, this);
//    if (useTlb) {
//      showTLBButton.enableJeliButton();
//    }
//    else {
//      showTLBButton.disableJeliButton();
//    }
//    if (noProgress) {
//      showProgressButton.disableJeliButton();
//    } else
//      showProgressButton.enableJeliButton();
//    showPageTableButton.enableJeliButton();
//    testButton.enableJeliButton();
//    foundAddressButton.enableJeliButton();
//    pageFaultButton.enableJeliButton();
//  }
//
//  private String addressToBits(long addr) {
//    return Long.toBinaryString(addr);
//  }
//
//  public void setClipBoardString(String s)
//  {
//    progress.objectNotify(CLIPBOARDS_ID, clipboards);
//  }
//
//  public String getClipBoardString() {
//    return clipboards.getClipBoardString();
//  }
//
//  public boolean tlbDisplayed() {
//    if (tlbDisplay == null) {
//      return false;
//    }
//    return tlbDisplay.isVisible();
//  }
//
//  public boolean tlbSegmentedMode() {
//    if (tlbDisplay == null) {
//      return false;
//    }
//    return tlbDisplay.getSegmentMode();
//  }
//
//  public int getTLBSegmentedPosition() {
//    if (tlbDisplay == null) {
//      return -1;
//    }
//    return tlbDisplay.getSegmentedPosition();
//  }
//
//  public int getTLBWidth() {
//    if (tlbDisplay == null) {
//      return -1;
//    }
//    return tlbDisplay.getTLBWidth();
//  }
//
//  public void logButtonPush(JeliButton b) {
//    ael.addEvent(new AddressActionEvent(1, b));
//  }
//
//
//  private AddressActionEvent createLogicalSegmentEvent()
//  {
//    return new AddressActionEvent(2, spLogical.getSegmentSizesString());
//  }
//
//  private AddressActionEvent createPhysicalSegmentEvent()
//  {
//    return new AddressActionEvent(8, spPhysical.getSegmentSizesString());
//  }
//
//
//
//  private AddressActionEvent createLogicalSelectEvent(int segment)
//  {
//    int numSeg = spLogical.getNumberOfSegments();
//    int type; int type; if ((data.pageTableLevels == 1) && (segment == 0)) {
//      type = 3;
//    } else { int type;
//      if (data.pageTableLevels == 1) {
//        type = 4;
//      } else { int type;
//        if ((numSeg == 3) && (segment == 0)) {
//          type = 5;
//        } else { int type;
//          if ((numSeg == 3) && (segment == 1)) {
//            type = 6;
//          } else { int type;
//            if ((numSeg < 3) && (segment == 0)) {
//              type = 7;
//            }
//            else
//              type = 4;
//          } } } }
//    return new AddressActionEvent(type, spLogical.getSelectedString());
//  }
//
//  private AddressActionEvent createPhysicalPasteEvent(int segment) { int type;
//    int type;
//    if (spPhysical.getNumberOfSegments() == 1) {
//      type = 9;
//    } else { int type;
//      if (segment == 0) {
//        type = 10;
//      }
//      else
//        type = 11;
//    }
//    return new AddressActionEvent(type, spPhysical.getSegmentString(segment));
//  }
//
//
//
//  public void objectNotify(int id, Object ob)
//  {
//    if (ob == spLogical) {
//      int type = spLogical.getLastOperationType();
//      int segment = spLogical.getLastOperationSegment();
//
//
//      if (type == 0) {
//        ael.addEvent(createLogicalSegmentEvent());
//      }
//      else if (type == 1) {
//        ael.addEvent(createLogicalSelectEvent(segment));
//
//      }
//
//
//
//    }
//    else if (ob == spPhysical) {
//      int type = spPhysical.getLastOperationType();
//      int segment = spPhysical.getLastOperationSegment();
//
//
//      if (type == 0) {
//        ael.addEvent(createPhysicalSegmentEvent());
//      }
//      else if (type == 2) {
//        ael.addEvent(createPhysicalPasteEvent(segment));
//      }
//
//    }
//    else if (ob == tlbDisplay)
//    {
//
//      ael.addEvent(new AddressActionEvent(12, tlbDisplay.getLastEventString()));
//
//    }
//    else if ((ob instanceof ProgressFrame))
//    {
//      ProgressFrame pf = (ProgressFrame)ob;
//      ael.addEvent(new AddressActionEvent(13, pf.getNotifyString(id)));
//    }
//  }
//
//
//  private String getFooter()
//  {
//    if (lifelineCount != 0) {
//      return "Lifeline count: " + lifelineCount + "&nbsp&nbsp&nbsp&nbsp Automatic step count: " + automaticStepCount;
//    }
//    return null;
//  }
//
//
//
//
//
//  public void logAllEvents(String title)
//  {
//    String ft = getFooter();
//    ael.logAllEvents(Jeli.Utility.getLogger(), title, ft);
//    adt.appendToLogString(ael.getLogString(title, ft));
//  }
//
//  public static void setTester(AddressMenu a) {
//    adt = a;
//  }
//
//  private void setCompleted() {
//    adt.setCompleted(data.getDisplayName());
//  }
//
//  private void setNotCompleted() {
//    adt.setNotCompleted(data.getDisplayName());
//  }
//
//  public TLBDisplay getTLBDisplay() {
//    return tlbDisplay;
//  }
//
//  public MemoryDisplay getMemoryDisplay() {
//    return memDisplay;
//  }
//
//
//  public void performStepSegmentLogicalAddress()
//  {
//    Jeli.Widgets.SegmentedPanelInfo info = spLogical.getInfo();
//    int pos = str.length() - data.pageSizeBits;
//    spLogicalMode.setModeSegment();
//    spLogical.removeAllSegments();
//    spLogical.makeSegment(pos);
//    spLogicalMode.jeliMouseMotionExit();
//    progress.objectNotify(LOGICAL_ADDRESS_ID, spLogical);
//  }
//
//
//  public void performStepSegmentPhysicalAddress()
//  {
//    Jeli.Widgets.SegmentedPanelInfo info = spPhysical.getInfo();
//    int pos = str.length() - data.pageSizeBits;
//    spPhysicalMode.setModeSegment();
//    spPhysical.removeAllSegments();
//    spPhysical.makeSegment(pos);
//    spPhysicalMode.jeliMouseMotionExit();
//    progress.objectNotify(PHYSICAL_ADDRESS_ID, spPhysical);
//  }
//
//  public void performStepCopyOffset() {
//    spLogicalMode.setModeSelect();
//    spPhysicalMode.setModePaste();
//    spLogical.selectSegment(1);
//    spPhysical.pasteSegment(1);
//  }
//
//  public void performStepSegmentTLB()
//  {
//    int pnBits = data.logicalAddressBits - data.pageSizeBits;
//    createTLBDisplay();
//    tlbDisplay.setVisible(true);
//    tlbDisplay.setSegmentMode();
//    tlbDisplay.segmentOccurred(0, pnBits);
//  }
//
//  public void performStepLogicalSelected() {
//    spLogicalMode.setModeSelect();
//    spLogical.selectSegment(0);
//  }
//
//  public void performStepTlbSearchEntry()
//  {
//    createTLBDisplay();
//    tlbDisplay.setVisible(true);
//    tlbDisplay.lookup();
//  }
//
//  public void performStepDisplayPageTableEntry() {
//    createMemoryDisplay();
//    memDisplay.setModePageTableTop();
//  }
//
//  public void performStepDisplayFinalFrameNumberEntry()
//  {
//    createMemoryDisplay();
//    if (data.logicalAddress2Bits > 0) {
//      spLogical.setSelectedSegment(1);
//      String s = spLogical.getSelectedString();
//      clipboards.setClipBoardString(s);
//      memDisplay.setEntryFromClipboard();
//
//      return;
//    }
//    spLogical.setSelectedSegment(0);
//    String s = spLogical.getSelectedString();
//
//    clipboards.setClipBoardString(s);
//    memDisplay.setEntryFromClipboard();
//  }
//
//  public void performStepPasteFrameNumberEntry() {
//    if (!data.pageFault) {
//      spPhysical.pasteSegment(0);
//      return;
//    }
//    if (progress.getPageFaultFound()) {
//      statusButton.setLabel("Page Fault Correctly Identifed");
//      statusButton.setBackground(Jeli.Utility.jeliLightGreen);
//      addCompletionEvent();
//      logEmailCompletion();
//    }
//    else {
//      System.out.println("Error performing step, page fault not found\n");
//    }
//  }
//
//  public void performStepTestCompletedSuccessullyEntry()
//  {
//    statusButton.setLabel("Correct Physical Address Found");
//    statusButton.setBackground(Jeli.Utility.jeliLightGreen);
//    addCompletionEvent();
//    logEmailCompletion();
//  }
//
//
//  public void performStepLogicalSegmented2Entry()
//  {
//    int pos1 = data.logicalAddressBits - data.logicalAddress2Bits - data.pageSizeBits;
//
//    int pos2 = pos1 + data.logicalAddress2Bits;
//
//    Jeli.Widgets.SegmentedPanelInfo info = spLogical.getInfo();
//    if (!spLogical.getModeSegment()) {
//      spLogicalMode.setModeSegment();
//    }
//    if ((segstart.length != 2) || (segstart[1] != pos2)) {
//      spLogical.removeAllSegments();
//      spLogical.makeSegment(pos2);
//    }
//    spLogical.makeSegment(pos1);
//    spLogicalMode.jeliMouseMotionExit();
//    progress.objectNotify(LOGICAL_ADDRESS_ID, spLogical);
//  }
//
//  public void performStepLogicalSelected1Entry() {
//    spLogicalMode.setModeSelect();
//    spLogical.selectSegment(0);
//    progress.objectNotify(LOGICAL_ADDRESS_ID, spLogical);
//  }
//
//  public void performStepSelectTopFrameEntry()
//  {
//    createMemoryDisplay();
//    spLogical.setSelectedSegment(0);
//    String s = spLogical.getSelectedString();
//    clipboards.setClipBoardString(s);
//    memDisplay.setEntryFromClipboard();
//  }
//
//  public void performStepdisplaySecondMemoryViewEntry() {
//    if (progress.getInvalidPageNumberFound()) {
//      statusButton.setLabel("Invalid Memory Reference Correctly Identifed");
//      statusButton.setBackground(Jeli.Utility.jeliLightGreen);
//      addCompletionEvent();
//      logEmailCompletion();
//    }
//    else {
//      memDisplay.setModePageTableBottom();
//    }
//  }
//
//  public void performStepSetStartingFrame2Entry() {
//    memDisplay.setFrameFromClipboard();
//  }
//
//  public void performStepSelectPageNumber2Entry()
//  {
//    spLogical.setSelectedSegment(1);
//    String s = spLogical.getSelectedString();
//    clipboards.setClipBoardString(s);
//    progress.objectNotify(LOGICAL_ADDRESS_ID, spLogical);
//  }
//
//  public void setDebugFound(int id) {
//    if (testButton.getLabel().equals("test"))
//    {
//      testButton.setLabel("");
//      testButton.setNoMouse(true);
//      testButton.setBorderType(0);
//      return;
//    }
//    testButton.setLabel("test");
//    testButton.setBorderType(15);
//    testButton.setNoMouse(false);
//  }
//
//
//
//  public void testButtonFound()
//  {
//    if (testButton.getLabel().length() == 0) {
//      return;
//    }
//    int n = progress.getHintPosition();
//    if (n >= 0) {
//      progress.performStep(n, this);
//    }
//  }
//
//
//
//  public void performStep()
//  {
//    int n = progress.getHintPosition();
//    if (n >= 0) {
//      if (performStepButton != null) {
//        performStepButton.disableJeliButton();
//      }
//      automaticStepCount += 1;
//      String hintName = progress.getHintTitle(n);
//      ael.addEvent(new AddressActionEvent(16, hintName));
//
//      progress.performStep(n, this);
//    }
//  }
//
//  public void disableLifelines() {
//    lifelineButton.disableJeliButton();
//  }
//
//  public void enableLifelines() {
//    lifelineButton.enableJeliButton();
//  }
//
//  public void setProgress(boolean noProgress, boolean progressHelp, boolean progressDo)
//  {
//    this.noProgress = noProgress;
//    this.progressHelp = progressHelp;
//    this.progressDo = progressDo;
//  }
//
//  public void incrementProgressDo() {
//    adt.incrementProgressDo();
//  }
//
//  public void incrementProgressHelp() {
//    adt.incrementProgressHelp();
//  }
//}
