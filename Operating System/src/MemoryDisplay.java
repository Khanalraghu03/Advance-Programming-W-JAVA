import Jeli.Widgets.JeliButton;
import Jeli.Widgets.JeliLabel;
import Jeli.Widgets.JeliPanel;
import java.awt.Color;
import java.awt.Panel;

public class MemoryDisplay extends Jeli.Widgets.JeliFrame implements Jeli.Widgets.ClipBoard, java.awt.event.AdjustmentListener
{
  public static int MODE_FIXED_1 = 1;
  public static int MODE_FIXED_2 = 2;
  public static int MODE_FIXED_4 = 3;
  public static int MODE_FIXED_8 = 4;
  public static int MODE_PAGE_TOP = 5;
  public static int MODE_PAGE_BOTTOM = 6;
  
  private JeliLabel validTitle;
  private JeliLabel frameTitle;
  private JeliLabel validBit;
  private JeliButton frameNumber;
  private Memory mem;
  private Jeli.Widgets.ClipBoards clipboards;
  private String clipboardString;
  private int myid = -1;
  private Jeli.Widgets.ObjectCallBack changeNotify = null;
  private TLB tlb;
  private Jeli.Widgets.SelfDestructingShortInfoFrame sif = null;
  private String name = "Memory View";
  private String helpMsg;
  private int numSec = 10;
  
  private long lastPageNumberUsed = -1L;
  private boolean lastValidBit = true;
  private long lastAddressDisplayed = -1L;
  
  private int displayEntries;
  
  private Jeli.Widgets.JeliScrollbar jsb;
  
  private JeliLabel[] addressLabels;
  private JeliLabel[] validLabels;
  private JeliButton[] frameLabels;
  private JeliLabel addressLabel;
  private JeliLabel validLabel;
  private JeliLabel frameLabel;
  private JeliLabel clipboard;
  private JeliLabel clipboardLabel;
  private JeliButton hideButton;
  private JeliButton helpButton;
  private int maxClipBits;
  private int maxFirstDisplayed;
  private JeliButton typeFixed1Button;
  private JeliButton typeFixed2Button;
  private JeliButton typeFixed4Button;
  private JeliButton typeFixed8Button;
  private JeliButton typePageTableTopButton;
  private JeliButton typePageTableBottomButton;
  private int mode = MODE_PAGE_TOP;
  private int lastmode = -1;
  private int selectedEntry = -1;
  
  private long frameMask;
  
  private int frameBits;
  
  private JeliButton addressLabelFrame;
  
  private JeliButton addressLabelEntry;
  
  private JeliButton addressLabelAddress;
  
  private JeliLabel addressValueFrame;
  
  private JeliLabel addressValueOffset;
  
  private JeliPanel addressEntryPanel;
  
  private int maxEntries;
  
  private int maxEntriesBits;
  
  private TranslationData data;
  private JeliLabel viewFixedLabel;
  private JeliLabel viewPageTableLabel;
  private long displayStartFrame = 0L;
  private int displayMemoryWidth = 1;
  
  public MemoryDisplay(TranslationData data, Memory mem, TLB tlb, int displayEntries, Jeli.Widgets.ClipBoards cb, int mode)
  {
    super("Memory View");
    this.mem = mem;
    this.data = data;
    this.tlb = tlb;
    this.displayEntries = displayEntries;
    this.mode = mode;
    clipboards = cb;
    clipboards.addClipBoard(this);
    clipboardString = clipboards.getClipBoardString();
    




















    frameBits = (physicalAddressBits - pageSizeBits);
    frameMask = 1L;
    for (int i = 0; i < frameBits - 1; i++) {
      frameMask = ((frameMask << 1) + 1L);
    }
    


    setMaxEntries();
    setClipBoardString(clipboards.getClipBoardString());
    


    createWidgets();
    setSizes();
    setupLayout(false);
    setModeActive();
    setResizable(false);
    fillButtonLabels();
    pack();
    addWindowListener(this);
  }
  
  private void fillButtonLabels() {
    addressValueFrame.setLabel(Long.toString(displayStartFrame, 2));
  }
  
  private void createWidgets() {
    clipboardLabel = new JeliLabel("Clipboard");
    clipboardLabel.setPositionCenter();
    clipboardLabel.setButtonSize();
    clipboardLabel.setBorderType(13);
    validTitle = new JeliLabel("v");
    validBit = new JeliLabel("");
    frameTitle = new JeliLabel("Frame Number");
    frameTitle.setPositionRight();
    frameNumber = new JeliButton("", this);
    frameNumber.setPositionRight();
    
    addressLabelFrame = new JeliButton("Frame", this);
    addressLabelFrame.setBorderType(13);
    addressLabelFrame.setButtonSize("Frame");
    



    addressLabelAddress = new JeliButton("A", this);
    addressLabelAddress.setLabelArray("A\nd\nd\nr");
    addressLabelAddress.setBorderType(7);
    addressLabelAddress.setCompressedArray(true);
    addressLabelEntry = new JeliButton("Entry", this);
    addressLabelEntry.setPositionRight();
    addressLabelEntry.setBorderType(9);
    
    addressLabelEntry.setButtonSize("Entry");
    addressValueFrame = new JeliLabel("xxx");
    addressValueFrame.setPositionRight();
    
    addressValueOffset = new JeliLabel("0");
    addressValueOffset.setBorderType(11);
    addressValueOffset.setPositionRight();
    





    typeFixed1Button = new JeliButton("1", this);
    typeFixed2Button = new JeliButton("2", this);
    typeFixed4Button = new JeliButton("4", this);
    typeFixed8Button = new JeliButton("8", this);
    typePageTableTopButton = new JeliButton("First", this);
    typePageTableBottomButton = new JeliButton("Second", this);
    typeFixed1Button.setPositionCenter();
    typeFixed2Button.setPositionCenter();
    typeFixed4Button.setPositionCenter();
    typeFixed8Button.setPositionCenter();
    typeFixed1Button.setInhibitReverse(true);
    typeFixed2Button.setInhibitReverse(true);
    typeFixed4Button.setInhibitReverse(true);
    typeFixed8Button.setInhibitReverse(true);
    typeFixed1Button.setBorderType(9);
    
    typeFixed2Button.setBorderType(9);
    
    typeFixed4Button.setBorderType(9);
    
    typeFixed8Button.setBorderType(9);
    
    typePageTableTopButton.setPositionCenter();
    typePageTableBottomButton.setPositionCenter();
    typePageTableTopButton.setBorderType(11);
    typePageTableBottomButton.setBorderType(11);
    typePageTableTopButton.setInhibitReverse(true);
    typePageTableBottomButton.setInhibitReverse(true);
    
    jsb = new Jeli.Widgets.JeliScrollbar(1, 0, 0, 1, 1);
    jsb.setBackground(Color.white);
    jsb.addAdjustmentListener(this);
    jsb.setMinimum(0);
    jsb.setMaximum(maxEntries);
    jsb.setVisibleAmount(displayEntries);
    jsb.setValue(0);
    
    addressLabel = new JeliLabel("Entry");
    addressLabel.setPositionRight();
    addressLabel.setBorderType(7);
    validLabel = new JeliLabel("v");
    frameLabel = new JeliLabel("Frame");
    frameLabel.setBorderType(11);
    


    frameLabel.setPositionRight();
    
    addressLabels = new JeliLabel[displayEntries];
    validLabels = new JeliLabel[displayEntries];
    frameLabels = new JeliButton[displayEntries];
    
    for (int i = 0; i < displayEntries; i++) {
      addressLabels[i] = new JeliLabel("");
      validLabels[i] = new JeliLabel("");
      frameLabels[i] = new JeliButton("", this);
      
      addressLabels[i].setPositionRight();
      frameLabels[i].setPositionRight();
      

      addressLabels[i].setBorderType(4);
      
      frameLabels[i].setBorderType(8);
    }
    
    clipboard = new JeliLabel(clipboardString);
    clipboard.setPositionRight();
    clipboard.setBorderType(14);
    viewFixedLabel = new JeliLabel("View");
    viewFixedLabel.setLabel2("Fixed Width:");
    viewFixedLabel.setBorderType(13);
    viewFixedLabel.setButtonSize("View Fixed:");
    viewPageTableLabel = new JeliLabel("Page Table Level:");
    viewPageTableLabel.setPositionRight();
    viewPageTableLabel.setBorderType(14);
    viewPageTableLabel.setButtonSize("Page Table Level:");
    


    hideButton = new JeliButton("Hide", this);
    hideButton.setBackground(Address.hideColor);
    hideButton.setButtonSize();
    helpButton = new JeliButton("Help", this);
    helpButton.setBackground(Address.helpColor);
    helpButton.setButtonSize();
    
    hideButton.setPositionCenter();
    helpButton.setPositionCenter();
  }
  

  private void setSizes()
  {
    int valueWidth = 16;
    if ((mode == MODE_PAGE_TOP) || (mode == MODE_PAGE_BOTTOM)) {
      valueWidth = frameBits;
      displayMemoryWidth = data.pageTableEntryBytes;
    }
    else if (mode == MODE_FIXED_1) {
      valueWidth = 8;
      displayMemoryWidth = 1;
    }
    else if (mode == MODE_FIXED_2) {
      valueWidth = 16;
      displayMemoryWidth = 2;
    }
    else if (mode == MODE_FIXED_4) {
      valueWidth = 32;
      displayMemoryWidth = 4;
    }
    else if (mode == MODE_FIXED_8) {
      valueWidth = 64;
      displayMemoryWidth = 8;
    }
    addressValueFrame.setButtonSize("0", frameBits);
    addressValueOffset.setButtonSize("0", maxEntriesBits);
    addressLabel.setButtonSize("0", maxEntriesBits);
    validLabel.setButtonSize("0");
    frameLabel.setButtonSize("0", valueWidth);
    for (int i = 0; i < displayEntries; i++) {
      addressLabels[i].setButtonSize("0", maxEntriesBits);
      validLabels[i].setButtonSize("0");
      frameLabels[i].setButtonSize("0", valueWidth);
    }
    clipboard.setButtonSize("0", maxClipBits);
  }
  
  public void setChangeNotify(int id, Jeli.Widgets.ObjectCallBack vn) {
    myid = id;
    changeNotify = vn;
  }
  
  private void notifyOfChange() {
    if (changeNotify != null) {
      changeNotify.objectNotify(myid, this);
    }
  }
  
  public void setVisible(boolean f) {
    super.setVisible(f);
    if (changeNotify != null) {
      changeNotify.objectNotify(myid, this);
    }
  }
  
  private void setupLayout(boolean testmode)
  {
    java.awt.GridBagLayout gbl = new java.awt.GridBagLayout();
    java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
    weightx = 1.0D;
    weighty = 0.0D;
    fill = 2;
    
    setLayout(new java.awt.BorderLayout());
    
    Panel topPanel = new Panel();
    JeliPanel[] panelArray = new JeliPanel[5];
    
    JeliPanel buttonPanel = new JeliPanel();
    



    JeliPanel mainPanel = new JeliPanel();
    JeliPanel viewPanel = new JeliPanel();
    add("East", jsb);
    mainPanel.setLayout(new Jeli.Widgets.JeliGridLayout(displayEntries + 1, 1));
    
    gbl.setConstraints(addressLabel, c);
    weightx = 0.0D;
    gbl.setConstraints(validLabel, c);
    gbl.setConstraints(frameLabel, c);
    Panel infoPanel = new Panel();
    infoPanel.setLayout(gbl);
    infoPanel.add(addressLabel);
    infoPanel.add(validLabel);
    infoPanel.add(frameLabel);
    mainPanel.add(infoPanel);
    




    for (int i = 0; i < displayEntries; i++) {
      weightx = 1.0D;
      gbl.setConstraints(addressLabels[i], c);
      weightx = 0.0D;
      gbl.setConstraints(validLabels[i], c);
      gbl.setConstraints(frameLabels[i], c);
      infoPanel = new Panel();
      infoPanel.setLayout(gbl);
      infoPanel.add(addressLabels[i]);
      infoPanel.add(validLabels[i]);
      infoPanel.add(frameLabels[i]);
      mainPanel.add(infoPanel);
    }
    add("Center", mainPanel);
    panelArray[0] = clipboardLabel;
    panelArray[1] = clipboard;
    

    addressEntryPanel = makeAddressEntryPanel();
    panelArray[2] = addressEntryPanel;
    


    viewPanel = makeViewPanel();
    
    buttonPanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
    
    buttonPanel.add(hideButton);
    buttonPanel.add(helpButton);
    panelArray[3] = viewPanel;
    panelArray[4] = buttonPanel;
    



    Jeli.Widgets.CenteredStackedPanels bottomPanel = new Jeli.Widgets.CenteredStackedPanels(panelArray, 0, true);
    add("South", bottomPanel);
    if (mode == MODE_PAGE_TOP) {
      displayStartFrame = data.pageTableStartFrame;
    }
    fillLabels(0);
  }
  
  private JeliPanel makeAddressEntryPanel() {
    JeliPanel newPanel = new JeliPanel();
    java.awt.GridBagLayout gbl = new java.awt.GridBagLayout();
    java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
    newPanel.setLayout(gbl);
    
    gridx = 0;
    gridy = 0;
    weightx = 0.0D;
    weighty = 1.0D;
    gridheight = 2;
    fill = 3;
    gbl.setConstraints(addressLabelAddress, c);
    newPanel.add(addressLabelAddress);
    
    gridx = 1;
    gridy = 0;
    weightx = 1.0D;
    weighty = 0.0D;
    gridheight = 1;
    fill = 2;
    gbl.setConstraints(addressLabelFrame, c);
    newPanel.add(addressLabelFrame);
    gridx = 2;
    gbl.setConstraints(addressLabelEntry, c);
    newPanel.add(addressLabelEntry);
    
    gridx = 1;
    gridy = 1;
    gbl.setConstraints(addressValueFrame, c);
    newPanel.add(addressValueFrame);
    gridx = 2;
    gbl.setConstraints(addressValueOffset, c);
    newPanel.add(addressValueOffset);
    return newPanel;
  }
  



  private JeliPanel makeViewPanel()
  {
    java.awt.GridBagLayout gbl1 = new java.awt.GridBagLayout();
    java.awt.GridBagConstraints c1 = new java.awt.GridBagConstraints();
    JeliPanel newPanel = new JeliPanel();
    newPanel.setLayout(gbl1);
    weightx = 1.0D;
    weighty = 0.0D;
    fill = 2;
    JeliPanel viewTypePanel = new JeliPanel();
    gbl1.setConstraints(viewTypePanel, c1);
    
    weightx = 0.0D;
    JeliPanel viewWidthPanel = new JeliPanel();
    gbl1.setConstraints(viewWidthPanel, c1);
    newPanel.add(viewTypePanel);
    newPanel.add(viewWidthPanel);
    viewTypePanel.setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
    viewTypePanel.add(viewFixedLabel);
    viewTypePanel.add(viewPageTableLabel);
    
    JeliPanel fixedWidthPanel = new JeliPanel();
    viewWidthPanel.setLayout(new Jeli.Widgets.JeliGridLayout(2, 1));
    fixedWidthPanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 4));
    fixedWidthPanel.add(typeFixed1Button);
    fixedWidthPanel.add(typeFixed2Button);
    fixedWidthPanel.add(typeFixed4Button);
    fixedWidthPanel.add(typeFixed8Button);
    viewWidthPanel.add(fixedWidthPanel);
    Panel viewPageTablePanel = new Panel();
    if (data.pageTableLevels == 1) {
      viewPageTablePanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 1));
      viewPageTablePanel.add(typePageTableTopButton);
      viewPageTableLabel.setLabel("Page Table:");
      typePageTableTopButton.setLabel("Full Table");
      typePageTableTopButton.setButtonSize();
    }
    else {
      viewPageTablePanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
      viewPageTablePanel.add(typePageTableTopButton);
      viewPageTablePanel.add(typePageTableBottomButton);
      viewPageTableLabel.setLabel("Page Table Level:");
      typePageTableTopButton.setLabel("First");
      typePageTableTopButton.setButtonSize();
    }
    viewWidthPanel.add(viewPageTablePanel);
    return newPanel;
  }
  
  private void setMaxEntries()
  {
    if (mode == MODE_FIXED_1) {
      maxEntries = (1 << data.pageSizeBits);
    }
    else if (mode == MODE_FIXED_2) {
      maxEntries = (1 << data.pageSizeBits - 1);
    }
    else if (mode == MODE_FIXED_4) {
      maxEntries = (1 << data.pageSizeBits - 2);
    }
    else if (mode == MODE_FIXED_8) {
      maxEntries = (1 << data.pageSizeBits - 3);
    }
    else if (mode == MODE_PAGE_TOP) {
      maxEntries = (1 << data.logicalAddressBits - data.logicalAddress2Bits - data.pageSizeBits);

    }
    else
    {
      maxEntries = (1 << data.logicalAddress2Bits);
    }
    maxEntriesBits = sizeToBits(maxEntries);
    maxClipBits = frameBits;
    if (maxEntriesBits > maxClipBits) {
      maxClipBits = maxEntriesBits;
    }
    if (data.logicalAddressBits > maxClipBits) {
      maxClipBits = data.logicalAddressBits;
    }
  }
  


  private boolean setModeActive()
  {
    if (mode == lastmode) {
      return false;
    }
    lastmode = mode;
    setMaxEntries();
    typeFixed1Button.setBackground(Color.white);
    typeFixed2Button.setBackground(Color.white);
    typeFixed4Button.setBackground(Color.white);
    typeFixed8Button.setBackground(Color.white);
    typePageTableTopButton.setBackground(Color.white);
    typePageTableBottomButton.setBackground(Color.white);
    if (mode == MODE_FIXED_1) {
      typeFixed1Button.setBackground(Color.cyan);
      
      displayMemoryWidth = 1;
    }
    if (mode == MODE_FIXED_2) {
      typeFixed2Button.setBackground(Color.cyan);
      
      displayMemoryWidth = 2;
    }
    if (mode == MODE_FIXED_4) {
      typeFixed4Button.setBackground(Color.cyan);
      
      displayMemoryWidth = 4;
    }
    if (mode == MODE_FIXED_8) {
      typeFixed8Button.setBackground(Color.cyan);
      
      displayMemoryWidth = 8;
    }
    if (mode == MODE_PAGE_TOP) {
      displayStartFrame = data.pageTableStartFrame;
      typePageTableTopButton.setBackground(Color.cyan);
      displayMemoryWidth = data.pageTableEntryBytes;
    }
    if (mode == MODE_PAGE_BOTTOM) {
      typePageTableBottomButton.setBackground(Color.cyan);
      displayMemoryWidth = data.pageTableEntryBytes;
    }
    if (mode == MODE_PAGE_TOP) {
      addressLabelFrame.disableJeliButton();
    }
    else
      addressLabelFrame.enableJeliButton();
    int validBorderType;
    int validBorderType; if ((mode == MODE_FIXED_1) || (mode == MODE_FIXED_2) || (mode == MODE_FIXED_4) || (mode == MODE_FIXED_8))
    {
      addressLabel.setLabel("Entry");
      validLabel.setLabel("");
      frameLabel.setLabel("Value");
      validLabel.setBorderType(7);
      validBorderType = 4;

    }
    else
    {

      addressLabel.setLabel("Entry");
      validLabel.setLabel("v");
      frameLabel.setLabel("Frame");
      validLabel.setBorderType(15);
      validBorderType = 12;
    }
    



    for (int i = 0; i < displayEntries; i++) {
      validLabels[i].setBorderType(validBorderType);
    }
    maxFirstDisplayed = (maxEntries - displayEntries);
    jsb.setMaximum(maxEntries);
    jsb.setValue(0);
    return true;
  }
  
  private void repack() {
    setMaxEntries();
    


    removeAll();
    setSizes();
    setupLayout(true);
    fillButtonLabels();
    pack();
  }
  
  private void setClipboardLabel() {
    if (clipboard != null) {
      clipboard.setLabel(clipboardString);
    }
  }
  
  public void setClipBoardString(String s)
  {
    if (s == null) {
      return;
    }
    int first1 = s.indexOf("1");
    if (first1 < 0) {
      s = "0";
    }
    else {
      s = s.substring(first1);
    }
    if (s.length() > maxClipBits) {
      clipboardString = s.substring(0, maxClipBits);
    }
    else {
      clipboardString = s;
    }
    
    setClipboardLabel();
  }
  
  public String getClipBoardString() {
    return clipboardString;
  }
  











  public void jeliButtonPushed(JeliButton bh)
  {
    if (bh == hideButton) {
      setVisible(false);



















    }
    else if (bh == helpButton) {
      System.out.println("Impement help button here.");
      makeHelpMessage();
      if (sif != null)
        sif.abort();
      sif = new Jeli.Widgets.SelfDestructingShortInfoFrame(name, helpMsg, numSec, helpButton);

    }
    else if (bh == typeFixed1Button) {
      mode = MODE_FIXED_1;
    }
    else if (bh == typeFixed2Button) {
      mode = MODE_FIXED_2;
    }
    else if (bh == typeFixed4Button) {
      mode = MODE_FIXED_4;
    }
    else if (bh == typeFixed8Button) {
      mode = MODE_FIXED_8;
    }
    else if (bh == typePageTableTopButton) {
      displayStartFrame = data.pageTableStartFrame;
      selectedEntry = -1;
      if (mode == MODE_PAGE_TOP) {
        addressValueFrame.setLabel(Long.toString(displayStartFrame, 2));
        addressValueOffset.setLabel("0");
        fillLabels(jsb.getValue());
      }
      else {
        mode = MODE_PAGE_TOP;
      }
    }
    else if (bh == typePageTableBottomButton) {
      mode = MODE_PAGE_BOTTOM;
    }
    else if (bh == addressLabelAddress) {
      setAddressFromClipboard();
    }
    else if (bh == addressLabelFrame) {
      setFrameFromClipboard();
    }
    else if (bh == addressLabelEntry) {
      setEntryFromClipboard();




    }
    else
    {




      for (int i = 0; i < frameLabels.length; i++) {
        if (bh == frameLabels[i]) {
          clipboards.setClipBoardString(bh.getLabel());
          selectedEntry = (jsb.getValue() + i);
          setLabelColors();
          break;
        }
      }
    }
    if (setModeActive()) {
      repack();
      notifyOfChange();
    }
  }
  
  private void makeHelpMessage()
  {
    StringBuffer stb = new StringBuffer();
    stb.append("The View buttons allow you to display a page table or a ");
    stb.append("frame of memory\n");
    stb.append("organized as words of size 1, 2, 4 or 8 bytes.\n");
    if (data.pageTableLevels > 1) {
      stb.append("It can display either a top level or second level page table.\n");
      
      stb.append("Push the First or Second button to display one of these.\n");
    }
    else {
      stb.append("Push the Full Table button to display the page table.\n"); }
    stb.append("Push the buttons labeled 1, 2, 4, or 8 to show a memory frame");
    stb.append("\norganized as words of the given size.\n");
    if (data.pageTableLevels > 1) {
      stb.append("For the first level page table, the starting address is");
      stb.append(" set autmatically\n");
      stb.append("For a second level page table");
      stb.append(" you must set the starting frame number\nby putting it ");
      stb.append("in the clipboard and pushing the Frame button.\n");
    }
    stb.append("For the fixed width display, put the starting frame number\n");
    stb.append("in the clipboard and push the Frame button.\n");
    stb.append("You can search for an entry by putting its index in the\n");
    stb.append("clipboard and pushing the Entry button.\n");
    stb.append("Alternateively, you can put the full address in the\n");
    stb.append("clipboard and pushing the Addr button.\n");
    stb.append("When an entry is found, ot it is clicked on directly,\n");
    stb.append("The corresponding value is placed in the clipboard.\n");
    helpMsg = stb.toString();
  }
  


  public void setFrameFromClipboard()
  {
    long frame = Long.parseLong(clipboardString, 2);
    long maxFrame = 1L << data.physicalAddressBits - data.pageSizeBits;
    if (frame >= maxFrame)
    {
      return;
    }
    
    selectedEntry = -1;
    displayStartFrame = frame;
    jsb.setValue(0);
    fillLabels(0);
    addressValueFrame.setLabel(Long.toString(displayStartFrame, 2));
    addressValueOffset.setLabel("0");
  }
  



  public void setEntryFromClipboard()
  {
    int entry = Integer.parseInt(clipboardString, 2);
    if (entry >= maxEntries) {
      System.out.println("Entry is too large");
      return;
    }
    int topval = entryToTopValue(entry);
    selectedEntry = entry;
    jsb.setValue(topval);
    fillLabels(topval);
    addressValueFrame.setLabel(Long.toString(displayStartFrame, 2));
    addressValueOffset.setLabel(Integer.toString(entry, 2));
  }
  





  private void setAddressFromClipboard()
  {
    long addr = Long.parseLong(clipboardString, 2);
    System.out.println("Address is " + addr);
    long frame = addr >>> data.pageSizeBits;
    
    long offset = addr - (frame << data.pageSizeBits);
    
    int entry = (int)(offset / displayMemoryWidth);
    System.out.println("entry is " + entry);
    selectedEntry = entry;
    if (mode != MODE_PAGE_TOP) {
      displayStartFrame = frame;
    }
    int topval = entryToTopValue(entry);
    System.out.println("topval is " + topval);
    jsb.setValue(topval);
    fillLabels(topval);
    addressValueFrame.setLabel(Long.toString(displayStartFrame, 2));
    addressValueOffset.setLabel(Integer.toString(entry, 2));
  }
  


  private int entryToTopValue(int val)
  {
    if ((val < 0) || (val >= maxEntries)) {
      return 0;
    }
    int topval = val - displayEntries / 2 + 1;
    
    if (topval < 0) {
      topval = 0;
    }
    if (topval > maxFirstDisplayed) {
      topval = maxFirstDisplayed;
    }
    return topval;
  }
  
  public long getInvalidPageNumber()
  {
    if (lastValidBit) {
      return -1L;
    }
    return lastPageNumberUsed;
  }
  
  public long getLastAddressDisplayed() {
    return lastAddressDisplayed;
  }
  
  private int sizeToBits(int n) {
    int bits = 0;
    if (n < 0) {
      return -1;
    }
    while (n > 1) {
      if (n / 2 * 2 != n) {
        return -1;
      }
      n /= 2;
      bits++;
    }
    return bits;
  }
  
  public void adjustmentValueChanged(java.awt.event.AdjustmentEvent e)
  {
    fillLabels(jsb.getValue());
  }
  




  public void fillLabels(int val)
  {
    long startAddress = displayStartFrame << data.pageSizeBits;
    
    for (int i = 0; i < addressLabels.length; i++) {
      addressLabels[i].setLabel(Integer.toString(val + i, 2));
      long memoryAddress = startAddress + displayMemoryWidth * (i + val);
      long memoryValue = mem.getWord(memoryAddress, displayMemoryWidth);
      if (memoryValue < 0L) {
        memoryValue = -memoryValue;
      }
      if ((mode == MODE_PAGE_TOP) || (mode == MODE_PAGE_BOTTOM)) {
        memoryValue &= frameMask;
      }
      
      frameLabels[i].setLabel(Long.toString(memoryValue, 2));
      frameLabels[i].setBackground(Color.white);
      addressLabels[i].setBackground(Color.white);
      validLabels[i].setBackground(Color.white);
      if ((mode == MODE_PAGE_TOP) || (mode == MODE_PAGE_BOTTOM)) {
        validLabels[i].setLabel(getValidLabel(memoryAddress));
      }
      else {
        validLabels[i].setLabel("");
      }
    }
    int selectedDisplay = selectedEntry - val;
    if ((selectedDisplay >= 0) && (selectedDisplay < addressLabels.length)) {
      frameLabels[selectedDisplay].setBackground(Color.cyan);
      addressLabels[selectedDisplay].setBackground(Color.cyan);
      validLabels[selectedDisplay].setBackground(Color.cyan);
      lastValidBit = validLabels[selectedDisplay].getLabel().equals("1");
      lastPageNumberUsed = (val + selectedDisplay);
      lastAddressDisplayed = (startAddress + displayMemoryWidth * (selectedDisplay + val));
      




      clipboards.setClipBoardString(frameLabels[selectedDisplay].getLabel());
    }
    notifyOfChange();
  }
  

  private void setLabelColors()
  {
    int topval = jsb.getValue();
    for (int i = 0; i < addressLabels.length; i++) { Color backColor;
      Color backColor; if (i + topval == selectedEntry) {
        backColor = Color.cyan;
      }
      else {
        backColor = Color.white;
      }
      frameLabels[i].setBackground(backColor);
      addressLabels[i].setBackground(backColor);
      validLabels[i].setBackground(backColor);
    }
  }
  
  private String getValidLabel(long memoryAddress) {
    if (mem.getValid(memoryAddress)) {
      return "1";
    }
    return "0";
  }
  
  public boolean modePageTableTop() {
    return mode == MODE_PAGE_TOP;
  }
  
  public boolean modePageTableBottom() {
    return mode == MODE_PAGE_BOTTOM;
  }
  
  public int getSelectedEntry() {
    return selectedEntry;
  }
  
  public long getStartFrame() {
    return displayStartFrame;
  }
  
  public void setModePageTableTop() {
    if (mode == MODE_PAGE_TOP) {
      return;
    }
    selectedEntry = -1;
    mode = MODE_PAGE_TOP;
    if (setModeActive()) {
      repack();
      notifyOfChange();
    }
  }
  
  public void setModePageTableBottom() {
    if (mode == MODE_PAGE_BOTTOM) {
      return;
    }
    selectedEntry = -1;
    mode = MODE_PAGE_BOTTOM;
    if (setModeActive()) {
      repack();
      notifyOfChange();
    }
  }
}
