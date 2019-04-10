import Jeli.Widgets.ObjectCallBack;
import Jeli.Widgets.SegmentedPanel;
import Jeli.Widgets.SegmentedPanelInfo;
import Jeli.Widgets.SelfDestructingShortInfoFrame;
import java.io.PrintStream;

public class ProgressTracker implements ObjectCallBack, Jeli.JeliBounds
{
  private String[] progressList;
  private TranslationData data;
  private ProgressFrame pf = null;
  private int TLBSegmentedListEntry = -1;
  private int logicalSegmented2Entry = -1;
  private int logicalSegmentedEntry = -1;
  private int logicalSelectedEntry = -1;
  private int logicalSelected1Entry = -1;
  private int physicalSegmentedEntry = -1;
  private int selectTopFrameEntry = -1;
  private int setStartingFrame2Entry = -1;
  private int selectPageNumber2Entry = -1;
  private int tlbSearchEntry = -1;
  private int displayPageTableEntry = -1;
  
  private int displayFinalFrameNumberEntry = -1;
  private int pasteFrameNumberEntry = -1;
  private int pasteFrameOffsetEntry = -1;
  
  private int displaySecondMemoryViewEntry = -1;
  private int testCompletedSuccessfullyEntry = -1;
  
  private int TLBPageBits;
  private int frameNumberBits;
  private boolean tlbSearchCompleted = false;
  private boolean pageTableDisplayVisible = false;
  
  private boolean locationSet = false;
  
  private boolean physicalAddressFound = false;
  private boolean pageFaultFound = false;
  private boolean pageTableDisable = false;
  private boolean firstStageDone = false;
  private boolean physicalOffsetDone = false;
  private boolean firstPageTableLookupDone = false;
  private boolean secondPageTableLookupDone = false;
  private boolean invalidPageNumberFound = false;
  private SelfDestructingShortInfoFrame sif = null;
  private int sifType = 0;
  private java.awt.Panel sifPanel = null;
  private Address addr;
  private ObjectCallBack changeNotify;
  private boolean progressHelp = false;
  private boolean progressDo = false;
  
  private String logicalSegmentedHelp = "To segment the logical address:\n1) make sure the logical address is in segment mode\n2) click the mouse at the desired position\n";
  


  private String physicalSegmentedHelp = "To segment the physical address:\n1) make sure the physical address is in segment mode\n2) click the mouse at the desired position\n";
  


  private String pasteFrameOffsetHelp = "To paste the frame offset:\n1) make sure the logical address is correctly segmented\n2) make sure the physical address is correctly segmented\n3) make sure the logical address is in select mode\n4) make sure the physical address is in paste mode\n5) click on the offset field in the logical address\n6) click on the offset field in the physical address\n";
  






  private String TLBSegmentedHelp = "To segment the TLB:\n1) make sure the TLB is displayed\n2) make sure the TLB is in Segment mode\n3) click the mouse at the desired position\n";
  



  private String logicalSelectedHelp = "To select the logical address:\n1) make sure the logical address is correctly segmented\n2) make sure the logical address is in select mode\n3) click on the page number field in the logical address\n";
  



  private String TLBSearchHelp = "To search the TLB for a given entry:\n1) make sure the page number in the logical address has been selected\n2) push the Lookup button in the TLB\n";
  


  private String displayPageTableHelp = "To display the page table:\n1) push the Memory View button.\n";
  

  private String logicalSegmented2Help = "To segment the logical address again:\n1) make sure the logical address is in segment mode\n2) click the mouse in the page number field in the desired location\n";
  


  private String logicalSelected1Help = "To select the top level page number in the logical address:\n1) make sure the logical address is in select mode\n2) click the mouse in the top level page number field\n";
  


  private String selectTopFrameHelp = "To select the frame number in the top level page table:\n1) make sure the top level page number has been selected in the logical address\n2) click on the Entry button in the memory view\n";
  



  private String displaySecondMemoryViewHelp = "To display the second level page table memory view:\n1) push the Second button in the memory view\n";
  

  private String setStartingFrame2Help = "To set the starting frame number of the second level page table:\n1) the starting frame number shoul dhave been selected\n2) the second level page table memory view should be displayed\n3) click on the Frame button in the second level page table memory view\n";
  



  private String selectPageNumber2Help = "To select the second level page number:\n1) make sure the logical address is in select mode\n2) select the second level page number from the logical address\n";
  


  private String displayFinalFrameNumberHelp = "To display the final frame number:\n1) make sure the page number in the logical address is selected\n2) click the Entry button in the memory view\n";
  


  private String pasteFrameNumberHelp = "To paste the frame number in the physical address:\n  if the valid bit is set:\n  1) make sure the physical address is in paste mode\n  2) click on the frame number field in the physical address\n  otherwise:\n  1) click on the yellow Page Fault button\n";
  





  private String testCompletedSuccessfullyHelp = "To test whether you completed this successfully:\n1) Push the correct yellow completion button\n";
  

  public ProgressTracker(Address addr)
  {
    this.addr = addr;
  }
  
  public java.awt.Rectangle getBounds() {
    if (pf == null) {
      return null;
    }
    return pf.getBounds();
  }
  
  public boolean getProgressDo() {
    return progressDo;
  }
  
  public boolean getProgressHelp() {
    return progressHelp;
  }
  
  public void initialize(TranslationData data)
  {
    this.data = data;
    TLBPageBits = (logicalAddressBits - pageSizeBits);
    frameNumberBits = (physicalAddressBits - pageSizeBits);
    makeProgressList(useTlb);
    String title; String title; if (pageTableLevels == 1) {
      title = "Single Level Page Table Progress";
    }
    else {
      title = "Two Level Page Table Progress";
    }
    pf = new ProgressFrame(title, progressList, this, true);
    
    if (changeNotify != null) {
      pf.setChangeNotify(changeNotify);
    }
  }
  




  private void makeProgressList(boolean useTLB)
  {
    int levels = data.pageTableLevels;
    


    int total = 10;
    if (!useTLB) {
      total -= 3;
    }
    if (levels > 1) {
      total += 6;
    }
    int count = 1;
    progressList = new String[total];
    for (int i = 0; i < total; i++) {
      progressList[i] = "Empty";
    }
    progressList[(count - 1)] = "Segment Logical Address";
    logicalSegmentedEntry = (count - 1);
    count++;
    progressList[(count - 1)] = "Segment Physical Address";
    physicalSegmentedEntry = (count - 1);
    count++;
    progressList[(count - 1)] = "Paste Offset in Physical Address";
    pasteFrameOffsetEntry = (count - 1);
    count++;
    if (useTLB) {
      progressList[(count - 1)] = "Segment TLB";
      TLBSegmentedListEntry = (count - 1);
      count++;
    }
    if (useTLB) {
      progressList[(count - 1)] = "Select Logical Page Number in Logical Address";
      logicalSelectedEntry = (count - 1);
      count++;
      progressList[(count - 1)] = "Search TLB for Page";
      tlbSearchEntry = (count - 1);
      count++;
    }
    if (levels > 1) {
      progressList[(count - 1)] = "Display First Level Page Table Memory View";
    }
    else {
      progressList[(count - 1)] = "Display Page Table Memory View";
    }
    displayPageTableEntry = (count - 1);
    count++;
    


    if (levels > 1) {
      progressList[(count - 1)] = "Segment Logical Address Again";
      logicalSegmented2Entry = (count - 1);
      count++;
      progressList[(count - 1)] = "Select First Level Page Number in Logical Address";
      
      logicalSelected1Entry = (count - 1);
      count++;
      progressList[(count - 1)] = "Find Entry in First Level Page Table, Done if Invalid";
      
      selectTopFrameEntry = (count - 1);
      count++;
      progressList[(count - 1)] = "Display Second Level Page Table Memory View";
      displaySecondMemoryViewEntry = (count - 1);
      count++;
      progressList[(count - 1)] = "Set Starting Frame Number of Second Level Page Table";
      
      setStartingFrame2Entry = (count - 1);
      count++;
      progressList[(count - 1)] = "Select Second Level Page Number in Logical Address";
      
      selectPageNumber2Entry = (count - 1);
      count++;
    }
    if (levels > 1) {
      progressList[(count - 1)] = "Find Entry in Second Level Page Table";
    }
    else {
      progressList[(count - 1)] = "Find Entry in Page Table";
    }
    displayFinalFrameNumberEntry = (count - 1);
    count++;
    
    progressList[(count - 1)] = "Paste Frame Number in Physical Address if Valid";
    pasteFrameNumberEntry = (count - 1);
    count++;
    
    progressList[(count - 1)] = "Push Correct Completion Button";
    testCompletedSuccessfullyEntry = (count - 1);
    count++;
  }
  
  public void showFrame(boolean f) {
    if (pf != null) {
      pf.setVisible(f);
    }
    if (sif != null) {
      sif.abort();
    }
  }
  











  public void objectNotify(int id, Object which)
  {
    if (which == sif) {
      gotHelpButton(id);
      return;
    }
    if (id == Address.LOGICAL_ADDRESS_ID)
    {


      SegmentedPanelInfo info = ((SegmentedPanel)which).getInfo();
      if ((firstStageDone) && (data.pageTableLevels > 1))
      {
        if (segstart.length == 3)
        {
          if ((segstart[1] == TLBPageBits - data.logicalAddress2Bits) && (segstart[2] == TLBPageBits))
          {

            pf.setDone(logicalSegmented2Entry, true);
          }
          else {
            pf.setDone(logicalSegmented2Entry, false);
          }
          if (selectedSegment == 1) {
            pf.setDone(selectPageNumber2Entry, true);
          }
          else if (!secondPageTableLookupDone) {
            pf.setDone(selectPageNumber2Entry, false);
          }
          if (selectedSegment == 0) {
            pf.setDone(logicalSelected1Entry, true);
          }
          else if (!firstPageTableLookupDone) {
            pf.setDone(logicalSelected1Entry, false);
          }
        }
        
        resetHelp();
        return;
      }
      if ((!data.useTlb) && (data.pageTableLevels > 1) && 
        (segstart.length == 3)) {
        if ((segstart[1] == TLBPageBits - data.logicalAddress2Bits) && (segstart[2] == TLBPageBits))
        {
          pf.setDone(logicalSegmentedEntry, true);
          pf.setDone(logicalSegmented2Entry, true);
        }
        else if (segstart[2] == TLBPageBits) {
          pf.setDone(logicalSegmentedEntry, true);
          pf.setDone(logicalSegmented2Entry, false);
        }
        else {
          pf.setDone(logicalSegmentedEntry, false);
          pf.setDone(logicalSegmented2Entry, false);
        }
      }
      
      if ((segstart.length == 2) && (segstart[1] == TLBPageBits)) {
        pf.setDone(logicalSegmentedEntry, true);
        if ((selectedSegment == 0) || (tlbSearchCompleted)) {
          pf.setDone(logicalSelectedEntry, true);
        }
        else {
          pf.setDone(logicalSelectedEntry, false);
        }
      }
      else
      {
        if ((data.useTlb) || (segstart.length != 3) || (segstart[2] != TLBPageBits))
        {
          pf.setDone(logicalSegmentedEntry, false);
        }
        pf.setDone(logicalSelectedEntry, false);
      }
      resetHelp();
      return;
    }
    if (id == Address.PHYSICAL_ADDRESS_ID)
    {

      SegmentedPanelInfo info = ((SegmentedPanel)which).getInfo();
      if ((segstart.length == 2) && (segstart[1] == frameNumberBits)) {
        pf.setDone(physicalSegmentedEntry, true);
        try {
          long val = Long.parseLong(str, 2);
          
          long frame = val >>> data.pageSizeBits;
          long offset = val - (frame << data.pageSizeBits);
          



          if (frame == data.frameNumber) {
            pf.setDone(pasteFrameNumberEntry, true);
          }
          else {
            pf.setDone(pasteFrameNumberEntry, false);
          }
          

          if (offset == data.offset) {
            pf.setDone(pasteFrameOffsetEntry, true);
            physicalOffsetDone = true;
            if ((!data.useTlb) || (tlbSearchCompleted)) {
              firstStageDone = true;
            }
          }
          else {
            pf.setDone(pasteFrameOffsetEntry, false);
          }
          if ((frame == data.frameNumber) && (offset == data.offset)) {
            physicalAddressFound = true;
          }
          else {
            physicalAddressFound = false;
          }
        }
        catch (NumberFormatException e) {
          System.out.println("Invalid physical address contents");
          pf.setDone(pasteFrameNumberEntry, false);
          pf.setDone(pasteFrameOffsetEntry, false);
        }
      }
      else {
        pf.setDone(physicalSegmentedEntry, false);
        pf.setDone(pasteFrameNumberEntry, false);
        pf.setDone(pasteFrameOffsetEntry, false);
      }
      resetHelp();
      return;
    }
    if (id == Address.TLB_ID)
    {
      TLBDisplay tlbD = (TLBDisplay)which;
      int pos = tlbD.getSegmentedPosition();
      

      if (pos == TLBPageBits)
      {
        pf.setDone(TLBSegmentedListEntry, true);
        if (tlbD.selectedCorrect())
        {
          tlbSearchCompleted = true;
          if (physicalOffsetDone) {
            firstStageDone = true;
          }
          pf.setDone(tlbSearchEntry, true);
          pageTableDisable = true;
          pf.disableEntry(displayPageTableEntry);
          pf.disableEntry(displayFinalFrameNumberEntry);
          
          if (data.pageTableLevels > 1) {
            pf.disableEntry(logicalSegmented2Entry);
            pf.disableEntry(logicalSelected1Entry);
            pf.disableEntry(selectTopFrameEntry);
            pf.disableEntry(displaySecondMemoryViewEntry);
            pf.disableEntry(setStartingFrame2Entry);
            pf.disableEntry(selectPageNumber2Entry);
          }
          
        }
        else if (((!data.inTlb) && (tlbD.notFound())) || (!data.useTlb))
        {


          tlbSearchCompleted = true;
          if (physicalOffsetDone) {
            firstStageDone = true;
          }
          pf.setDone(tlbSearchEntry, true);
        }
        else {
          pf.setDone(tlbSearchEntry, false);
        }
        
      }
      else
      {
        pf.setDone(TLBSegmentedListEntry, false);
        pf.setDone(tlbSearchEntry, false);
      }
      

      resetHelp(); return;
    }
    long pageTableEntryAddress;
    if (id == Address.CLIPBOARDS_ID) {
      Jeli.Widgets.ClipBoard cb = (Jeli.Widgets.ClipBoard)which;
      
      pageTableEntryAddress = (data.pageTableStartFrame << data.pageSizeBits) + data.pageNumber * data.pageTableEntryBytes;
    }
    




    if (id == Address.MEMORY_DISPLAY_ID)
    {
      MemoryDisplay md = (MemoryDisplay)which;
      pageTableDisplayVisible = ((md.isVisible()) && (md.modePageTableTop()));
      

      if ((!pageTableDisable) && (!firstPageTableLookupDone)) {
        pf.setDone(displayPageTableEntry, pageTableDisplayVisible);
      }
      long invalidPageNumber = md.getInvalidPageNumber();
      pageFaultFound = (invalidPageNumber == data.pageNumberLast);
      









      long pageTableEntryAddress = (data.pageTableStartFrame << data.pageSizeBits) + data.pageNumber * data.pageTableEntryBytes;
      



      int selectedEntry = md.getSelectedEntry();
      if (data.pageTableLevels > 1)
      {


        if ((firstStageDone) && (selectedEntry == data.pageNumber1))
        {
          pf.setDone(selectTopFrameEntry, true);
          firstPageTableLookupDone = true;
          if (md.getInvalidPageNumber() == data.pageNumber1)
          {


            invalidPageNumberFound = true;
          }
        }
        else if (!firstPageTableLookupDone) {
          pf.setDone(selectTopFrameEntry, false);
        }
        if (firstPageTableLookupDone) {
          if (md.modePageTableBottom()) {
            pf.setDone(displaySecondMemoryViewEntry, true);
            


            if (md.getStartFrame() == data.topFrame) {
              pf.setDone(setStartingFrame2Entry, true);
              selectedEntry = md.getSelectedEntry();
              if (selectedEntry == data.pageNumber2) {
                pf.setDone(displayFinalFrameNumberEntry, true);
              }
              else {
                pf.setDone(displayFinalFrameNumberEntry, false);
              }
            }
            else {
              pf.setDone(setStartingFrame2Entry, false);
            }
          }
          else if (!secondPageTableLookupDone) {
            pf.setDone(displaySecondMemoryViewEntry, false);
          }
        }
      }
      
      if (pageTableEntryAddress == md.getLastAddressDisplayed()) {
        pf.setDone(displayFinalFrameNumberEntry, true);
      }
    }
    resetHelp();
  }
  
  private void showPanelInfo(SegmentedPanel sp)
  {
    SegmentedPanelInfo info = sp.getInfo();
    System.out.println("String: " + str);
    System.out.println("Number of Segments: " + segstart.length);
    System.out.print("Segments: ");
    for (int i = 0; i < segstart.length; i++) {
      System.out.print(" " + segstart[i]);
    }
    System.out.println();
    System.out.println("Selected: " + selectedSegment);
  }
  
  public java.awt.Rectangle getFrameBounds() {
    return pf.getBounds();
  }
  
  public void setFrameLocation(java.awt.Point p) {
    if (!locationSet) {
      pf.setLocation(p);
    }
    locationSet = true;
  }
  
  public boolean getPhysicalAddressFound() {
    return physicalAddressFound;
  }
  
  public boolean getPageFaultFound() {
    return pageFaultFound;
  }
  












  public boolean getInvalidPageNumberFound()
  {
    return invalidPageNumberFound;
  }
  
  public boolean getTlbSearchCompleted() {
    return tlbSearchCompleted;
  }
  
  public void buttonPushed(int n, java.awt.Panel p)
  {
    boolean noTimeout = false;
    
    java.awt.Point location = null;
    if (!progressHelp) {
      return;
    }
    if (sif != null)
    {
      noTimeout = (sifType == n) && (sif.isVisible()) && (sif.getTimeout() <= 0);
      if ((sifType == n) && (sif.isVisible())) {
        location = sif.getLocation();
      }
      sif.abort();
    }
    
    String msg;
    
    String msg;
    if ((pf.isNext(n)) && (progressDo)) {
      msg = getDynamicHelpMessage(n);
    }
    else {
      msg = getStaticHelpMessage(n, pf.getDone(n));
    }
    if (msg == null) return;
    int timeout;
    int timeout;
    if (noTimeout) {
      timeout = -1;
    }
    else {
      timeout = data.helpMsgTimeout;
    }
    sif = new SelfDestructingShortInfoFrame(progressList[n] + " Help", msg, timeout, p);
    
    addr.incrementProgressHelp();
    if (location != null) {
      sif.setLocation(location);
    }
    sifType = n;
    sifPanel = p;
    if (pf.isNext(n)) {
      sif.setCallBack(this);
    }
  }
  
  public void resetHelp() {
    if (sif == null) {
      return;
    }
    if (sif.isVisible()) {
      buttonPushed(sifType, sifPanel);
    }
  }
  
  private void gotHelpButton(int id) {
    boolean attemptAbort = false;
    boolean noPerform = false;
    

    if (sifType == logicalSegmentedEntry)
    {
      if (id == 1) {
        addr.setLogicalAddressSegmentMode();
      }
      else if (id == 2) {
        addr.performStepSegmentLogicalAddress();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    else if (sifType == physicalSegmentedEntry) {
      if (id == 1) {
        addr.setPhysicalAddressSegmentMode();
      }
      else if (id == 2) {
        addr.performStepSegmentPhysicalAddress();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    else if (sifType == pasteFrameOffsetEntry) {
      if (id == 1) {
        addr.setLogicalAddressSelectMode();
      }
      else if (id == 2) {
        addr.setPhysicalAddressPasteMode();
      }
      else if (id == 4) {
        addr.performStepCopyOffset();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    else if (sifType == TLBSegmentedListEntry) {
      if (id == 1) {
        addr.createTLBDisplayVisible();
      }
      else if (id == 2) {
        addr.setTLBSegmentMode();
      }
      else if ((id == 3) || (id == 6)) {
        addr.performStepSegmentTLB();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    else if (sifType == logicalSelectedEntry) {
      if (id == 2) {
        addr.setLogicalAddressSelectMode();
      }
      else if (id == 3) {
        addr.performStepLogicalSelected();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    else if (sifType == tlbSearchEntry) {
      if (id == 2) {
        addr.performStepTlbSearchEntry();
        attemptAbort = true;
      }
    }
    else if (sifType == displayPageTableEntry) {
      if (id == 1) {
        addr.performStepDisplayPageTableEntry();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    else if (sifType == displayFinalFrameNumberEntry) {
      if (data.pageTableLevels == 1) {
        if (id == 1) {
          addr.performStepLogicalSelected();
        }
        else if (id == 2) {
          addr.performStepSelectTopFrameEntry();
          attemptAbort = true;
        }
        else {
          noPerform = true;
        }
        
      }
      else if (id == 2) {
        addr.performStepDisplayFinalFrameNumberEntry();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
      
    }
    else if (sifType == pasteFrameNumberEntry) {
      if ((id == 1) || (id == 2)) {
        addr.performStepPasteFrameNumberEntry();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    else if (sifType == testCompletedSuccessfullyEntry) {
      if ((id == 1) || (id == 2)) {
        addr.performStepTestCompletedSuccessullyEntry();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    else if (sifType == logicalSegmented2Entry) {
      if (id == 1) {
        addr.setLogicalAddressSegmentMode();
      }
      else if (id == 2) {
        addr.performStepLogicalSegmented2Entry();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    else if (sifType == logicalSelected1Entry) {
      if (id == 1) {
        addr.setLogicalAddressSelectMode();
      }
      else if (id == 2) {
        addr.performStepLogicalSelected1Entry();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    else if (sifType == selectTopFrameEntry) {
      if (id == 2) {
        addr.performStepSelectTopFrameEntry();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    else if (sifType == displaySecondMemoryViewEntry) {
      if (id == 1) {
        addr.performStepdisplaySecondMemoryViewEntry();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    else if (sifType == setStartingFrame2Entry) {
      if (id == 2) {
        addr.performStepdisplaySecondMemoryViewEntry();
      }
      else if (id == 3) {
        addr.performStepSetStartingFrame2Entry();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    else if (sifType == selectPageNumber2Entry) {
      if (id == 1) {
        addr.setLogicalAddressSelectMode();
      }
      else if (id == 2) {
        addr.performStepSelectPageNumber2Entry();
        attemptAbort = true;
      }
      else {
        noPerform = true;
      }
    }
    if (attemptAbort) {
      abortMessageIfNecessary();
    }
    else {
      buttonPushed(sifType, sifPanel);
    }
    if (!noPerform) {
      addr.incrementProgressDo();
    }
  }
  
  private void abortMessageIfNecessary()
  {
    if (sif == null) {
      return;
    }
    if (!sif.isVisible()) {
      return;
    }
    sif.abort();
  }
  
  private String getStaticHelpMessage(int n, boolean done) {
    String s = null;
    if (n == logicalSegmentedEntry) {
      s = logicalSegmentedHelp;
    }
    else if (n == physicalSegmentedEntry) {
      s = physicalSegmentedHelp;
    }
    else if (n == pasteFrameOffsetEntry) {
      s = pasteFrameOffsetHelp;
    }
    else if (n == TLBSegmentedListEntry) {
      s = TLBSegmentedHelp;
    }
    else if (n == logicalSelectedEntry) {
      s = logicalSelectedHelp;
    }
    else if (n == tlbSearchEntry) {
      s = TLBSearchHelp;
    }
    else if (n == displayPageTableEntry) {
      s = displayPageTableHelp;
    }
    else if (n == logicalSegmented2Entry) {
      s = logicalSegmented2Help;
    }
    else if (n == logicalSelected1Entry) {
      s = logicalSelected1Help;
    }
    else if (n == selectTopFrameEntry) {
      s = selectTopFrameHelp;
    }
    else if (n == displaySecondMemoryViewEntry) {
      s = displaySecondMemoryViewHelp;
    }
    else if (n == setStartingFrame2Entry) {
      s = setStartingFrame2Help;
    }
    else if (n == selectPageNumber2Entry) {
      s = selectPageNumber2Help;
    }
    else if (n == displayFinalFrameNumberEntry) {
      s = displayFinalFrameNumberHelp;
    }
    else if (n == pasteFrameNumberEntry) {
      s = pasteFrameNumberHelp;
    }
    else if (n == testCompletedSuccessfullyEntry) {
      s = testCompletedSuccessfullyHelp;
    }
    
    if ((done) && (s != null)) {
      s = s + "      This step has been completed.\n";
    }
    
    return s;
  }
  
  private String getDynamicHelpMessage(int n) {
    if (n == logicalSegmentedEntry) {
      return getLogicalSegmentMsg();
    }
    if (n == physicalSegmentedEntry) {
      return getPhysicalSegmentMsg();
    }
    if (n == pasteFrameOffsetEntry) {
      return getPasteFrameOffsetMsg();
    }
    if (n == TLBSegmentedListEntry) {
      return getTLBSegmentedMsg();
    }
    if (n == logicalSelectedEntry) {
      return getLogicalSelectedMsg();
    }
    if (n == tlbSearchEntry) {
      return getTLBSearchMsg();
    }
    if (n == displayPageTableEntry) {
      return getDisplayPageTableMsg();
    }
    if (n == displayFinalFrameNumberEntry) {
      return getDisplayFinalFrameNumberMsg();
    }
    if (n == pasteFrameNumberEntry) {
      return getPasteFrameNumberMsg();
    }
    if (n == testCompletedSuccessfullyEntry) {
      return getTestCompletedSuccessfullyMsg();
    }
    if (n == logicalSegmented2Entry) {
      return getLogicalSegmented2Msg();
    }
    if (n == logicalSelected1Entry) {
      return getLogicalSelected1Msg();
    }
    if (n == selectTopFrameEntry) {
      return getSelectTopFrameMsg();
    }
    if (n == displaySecondMemoryViewEntry) {
      return getDisplaySecondMemoryViewMsg();
    }
    if (n == setStartingFrame2Entry) {
      return getSetStartingFrame2Msg();
    }
    if (n == selectPageNumber2Entry) {
      return getSelectPageNumber2Msg();
    }
    return null;
  }
  


  private String getLogicalSegmentMsg()
  {
    SegmentedPanel sp = addr.getLogicalPanel();
    SegmentedPanelInfo info = sp.getInfo();
    String msg = "To segment the logical address:\n1) make sure the logical address is in segment mode - ";
    

    if (!sp.getModeSegment()) {
      msg = msg + "do it!\n2) click the mouse at the desited position\n";
      
      return msg;
    }
    
    msg = msg + "OK\n";
    
    if (segstart.length == 1) {
      msg = msg + "2) correctly segment the logical address - do it\n";
    }
    else {
      msg = msg + "2) fix the segmenting of the logical address - do it\n";
    }
    return msg;
  }
  


  private String getPhysicalSegmentMsg()
  {
    SegmentedPanel sp = addr.getPhysicalPanel();
    SegmentedPanelInfo info = sp.getInfo();
    String msg = "To segment the physical address:\n1) make sure the physical address is in segment mode - ";
    

    if (!sp.getModeSegment()) {
      msg = msg + "do it!\n2) click the mouse at the desited position\n";
      
      return msg;
    }
    
    msg = msg + "OK\n";
    
    if (segstart.length == 1) {
      msg = msg + "2) correctly segment the physical address - do it\n";
    }
    else {
      msg = msg + "2) fix the segmenting of the physical address - do it\n";
    }
    return msg;
  }
  
  private String getPasteFrameOffsetMsg()
  {
    String msg = "Paste offset from logical address into physical address:\n";
    msg = msg + "1) Logical address must be in select mode - ";
    if (addr.getLogicalPanel().getModeSelect()) {
      msg = msg + "OK\n";
    }
    else {
      msg = msg + "do it\n";
    }
    msg = msg + "2) Physical address must be in paste mode - ";
    if (addr.getPhysicalPanel().getModePaste()) {
      msg = msg + "OK\n";
    }
    else {
      msg = msg + "do it\n";
    }
    msg = msg + "3) Click on the offset field of the logical address\n";
    msg = msg + "4) Click on the offset field of the physical address - do it\n";
    
    return msg;
  }
  


  private String getTLBSegmentedMsg()
  {
    String msg = "To segment the TLB:\n1) make sure the TLB is displayed";
    if (addr.tlbDisplayed()) {
      msg = msg + " - OK\n";
    }
    else {
      msg = msg + " - do it!\n";
    }
    msg = msg + "2) make sure the TLB is in Segment mode";
    if (addr.tlbDisplayed()) {
      if (addr.tlbSegmentedMode()) {
        msg = msg + " - OK";
      }
      else {
        msg = msg + " - do it!";
      }
    }
    int wid = addr.getTLBWidth();
    if (wid == -1) {
      msg = msg + "\n3) click the mouse at the desired position - do it\n";
      return msg;
    }
    int pos = addr.getTLBSegmentedPosition();
    if (pos == -1) {
      msg = msg + "\n3) click the mouse at the desired position - do it\n";
    }
    else {
      msg = msg + "\n3) Current TLB segmenting:\n     page bits = " + pos + " and frame bits = " + (wid - pos);
      
      if (pos == TLBPageBits) {
        msg = msg + " - OK\n";
      }
      else {
        msg = msg + "\n     The TLB is incorrectly segmented\n" + "     click the mouse at the destired position to change it - do it\n";
      }
    }
    
    return msg;
  }
  


  public String getLogicalSelectedMsg()
  {
    SegmentedPanel sp = addr.getLogicalPanel();
    
    String msg = "To select the logical address:\n1) make sure the logical address is correctly segmented\n";
    
    msg = msg + "2) make sure the logical address is in select mode - ";
    if (sp.getModeSelect()) {
      msg = msg + "OK\n";
    }
    else {
      msg = msg + "do it\n";
    }
    msg = msg + "3) click on the page number field in the logical address - do it\n";
    
    return msg;
  }
  
  public String getTLBSearchMsg()
  {
    String msg = "To search the TLB for a given entry:\n1) make sure the page number in the logical address is selected\n2) push the Lookup button in the TLB - do it\n";
    

    return msg;
  }
  
  public String getDisplayPageTableMsg()
  {
    String msg = "To display the page table:\n1) push the Memory View button - do it\n";
    
    return msg;
  }
  


  public String getDisplayFinalFrameNumberMsg()
  {
    String cbs = addr.getClipBoardString();
    long cbsValue = Long.parseLong(cbs, 2);
    



    String msg = "To display the final frame number:\n1) make sure the page number in the logical address is selected - ";
    
    if (cbsValue != data.pageNumberLast) {
      msg = msg + "do it\n";
    }
    else {
      msg = msg + "OK\n";
    }
    msg = msg + "2) click the Entry button in the memory view - do it\n";
    return msg;
  }
  
  public String getPasteFrameNumberMsg()
  {
    String msg = "Paste frame number in physical address if valid:\n";
    if (data.pageFault) {
      msg = msg + "Since the valid bit of this entry is clear,\n" + "  click the yellow Page Fault button - do it\n";

    }
    else
    {
      msg = msg + "Paste the frame number from the page table into the " + "physical address - do it\n";
    }
    
    return msg;
  }
  
  public String getTestCompletedSuccessfullyMsg()
  {
    String msg = "To test whether you completed this successfully:\n";
    if (data.pageFault) {
      msg = msg + "Since the valid bit of this entry is clear,\n" + "  click the yellow Page Fault button - do it\n";

    }
    else
    {
      msg = msg + "Since the correct physical address has been found,\n" + "  click the yellow Found Physical Address button - do it\n";
    }
    
    return msg;
  }
  

  public String getLogicalSegmented2Msg()
  {
    SegmentedPanel sp = addr.getLogicalPanel();
    String msg = "To segment the logical address again:\n1) make sure the logical address is in segment mode - ";
    
    if (sp.getModeSegment()) {
      msg = msg + "OK\n";
    }
    else {
      msg = msg + "do it\n";
    }
    msg = msg + "2) click the mouse in the page number field in the" + " desired location - do it\n";
    

    return msg;
  }
  

  public String getLogicalSelected1Msg()
  {
    SegmentedPanel sp = addr.getLogicalPanel();
    String msg = "To select the top level page number in the logical address:\n1) make sure the logical address is in select mode - ";
    
    if (sp.getModeSelect()) {
      msg = msg + "OK\n";
    }
    else {
      msg = msg + "do it\n";
    }
    msg = msg + "2) click the mouse in the top level page number field - do it\n";
    
    return msg;
  }
  
  public String getSelectTopFrameMsg()
  {
    String msg = "To select the frame number in the top level page table:\n1) make sure the top level page number has been selected in the logical address\n2) click on the Entry button in the memory view - do it\n";
    


    return msg;
  }
  
  public String getDisplaySecondMemoryViewMsg() { String msg;
    String msg;
    if (getInvalidPageNumberFound()) {
      msg = "An invalid entry in the top level page table was found.\nclick the yellow Invalid Reference button - do it\n";
    }
    else
    {
      msg = "A valid entry in the top level page table was found.\n";
      msg = msg + "click the Second button near the bottom of the memory view - do it\n";
    }
    
    return msg;
  }
  

  public String getSetStartingFrame2Msg()
  {
    MemoryDisplay memDisplay = addr.getMemoryDisplay();
    String msg = "To set the starting frame number of the second level page table:\n1) the starting frame number should have been selected\n2) the second level page table memory view should be displayed - ";
    

    if (memDisplay.modePageTableBottom()) {
      msg = msg + "OK\n";
    }
    else {
      msg = msg + "do it\n";
    }
    msg = msg + "3) click on the Frame button in the second level page table" + " memory view - do it\n";
    

    return msg;
  }
  

  public String getSelectPageNumber2Msg()
  {
    SegmentedPanel sp = addr.getLogicalPanel();
    
    String msg = "To select the second level page number:\n1) make sure the logical address is in select mode - ";
    
    if (sp.getModeSelect()) {
      msg = msg + "OK\n";
    }
    else {
      msg = msg + "do it\n";
    }
    msg = msg + "2) select the second level page number from the logical address - do it\n";
    
    return msg;
  }
  
  public String setDisplayFinalFrameNumberMsg()
  {
    String msg = "To display the final frame number:\n1) make sure the page number in the logical address is selected\n";
    
    msg = msg + "2) click the Entry button in the memory view - do it\n";
    return msg;
  }
  
  public void setChangeNotify(ObjectCallBack cb) {
    changeNotify = cb;
    if (pf != null) {
      pf.setChangeNotify(cb);
    }
  }
  
  public void setAllDone() {
    if (testCompletedSuccessfullyEntry >= 0) {
      pf.setDone(testCompletedSuccessfullyEntry, true);
    }
    pf.disableUndone();
  }
  
  public int getHintPosition()
  {
    for (int nextToDo = 0; nextToDo < progressList.length; nextToDo++) {
      if ((!pf.getDone(nextToDo)) && (!pf.getDisabled(nextToDo))) {
        break;
      }
    }
    if (nextToDo == progressList.length) {
      return -1;
    }
    return nextToDo;
  }
  
  public String getHintTitle(int n) {
    if ((n < 0) || (n >= progressList.length)) {
      return null;
    }
    return progressList[n];
  }
  
  public void performStep(int n, Address ad) {
    if (n == logicalSegmentedEntry) {
      ad.performStepSegmentLogicalAddress();
    }
    else if (n == physicalSegmentedEntry) {
      ad.performStepSegmentPhysicalAddress();
    }
    else if (n == pasteFrameOffsetEntry) {
      ad.performStepCopyOffset();
    }
    else if (n == TLBSegmentedListEntry) {
      ad.performStepSegmentTLB();
    }
    else if (n == logicalSelectedEntry) {
      ad.performStepLogicalSelected();
    }
    else if (n == tlbSearchEntry) {
      ad.performStepTlbSearchEntry();
    }
    else if (n == displayPageTableEntry) {
      ad.performStepDisplayPageTableEntry();
    }
    else if (n == displayFinalFrameNumberEntry) {
      ad.performStepDisplayFinalFrameNumberEntry();
    }
    else if (n == pasteFrameNumberEntry) {
      ad.performStepPasteFrameNumberEntry();
    }
    else if (n == testCompletedSuccessfullyEntry) {
      ad.performStepTestCompletedSuccessullyEntry();
    }
    else if (n == logicalSegmented2Entry) {
      ad.performStepLogicalSegmented2Entry();
    }
    else if (n == logicalSelected1Entry) {
      ad.performStepLogicalSelected1Entry();
    }
    else if (n == selectTopFrameEntry) {
      ad.performStepSelectTopFrameEntry();
    }
    else if (n == displaySecondMemoryViewEntry) {
      ad.performStepdisplaySecondMemoryViewEntry();
    }
    else if (n == setStartingFrame2Entry) {
      ad.performStepSetStartingFrame2Entry();
    }
    else if (n == selectPageNumber2Entry) {
      ad.performStepSelectPageNumber2Entry();
    }
    else
    {
      System.out.println("Automatic step " + n + " unknown!");
    }
  }
  
  public String getHint(int n, Address ad)
  {
    if ((n < 0) || (n >= progressList.length)) {
      return null;
    }
    

    if (n == logicalSegmentedEntry) {
      return logicalSegmentedEntryHint(ad);
    }
    if (n == physicalSegmentedEntry) {
      return physicalSegmentedEntryHint(ad);
    }
    if (n == pasteFrameOffsetEntry) {
      return pasteFrameOffsetEntryHint(ad);
    }
    if (n == TLBSegmentedListEntry) {
      return TLBSetmentedEntryHint(ad);
    }
    if (n == logicalSelectedEntry) {
      return logicalSelectedEntryHint(ad);
    }
    if (n == tlbSearchEntry) {
      return tlbSearchEntryHint(ad);
    }
    if (n == displayPageTableEntry) {
      return displayPageTableEntryHint(ad);
    }
    if (n == displayFinalFrameNumberEntry) {
      return displayFinalFrameNumberEntryHint(ad);
    }
    if (n == pasteFrameNumberEntry) {
      return pasteFrameNumberEntryHint(ad);
    }
    if (n == testCompletedSuccessfullyEntry) {
      return testCompletedSuccessfullyEntryHint(ad);
    }
    if (n == logicalSegmented2Entry) {
      return logicalSegmented2EntryHint(ad);
    }
    if (n == logicalSelected1Entry) {
      return logicalSelected1EntryHint(ad);
    }
    if (n == selectTopFrameEntry) {
      return selectTopFrameEntryHint(ad);
    }
    if (n == displaySecondMemoryViewEntry) {
      return displaySecondMemoryViewEntryHint(ad);
    }
    if (n == setStartingFrame2Entry) {
      return setStartingFrame2EntryHint(ad);
    }
    if (n == selectPageNumber2Entry) {
      return selectPageNumber2EntryHint(ad);
    }
    return "Hint for " + (n + 1) + ":\n" + progressList[n];
  }
  


  private String logicalSegmentedEntryHint(Address ad)
  {
    SegmentedPanel sp = ad.getLogicalPanel();
    SegmentedPanelInfo info = sp.getInfo();
    StringBuffer sb = new StringBuffer();
    sb.append("Start by segmenting the logical address\n");
    sb.append("The logical address is located in this window below the ");
    sb.append("table of values\n");
    if (!sp.getModeSegment()) {
      sb.append("The logical address needs to be put in Segment mode by ");
      sb.append("clicking on the \nSegment button of the logical address.\n");
      sb.append("When this is done, the Segment button should be highlighted\n");
    }
    if (segstart.length == 1) {
      sb.append("Currently the logical address is not segmented.\n");
    }
    else if (segstart.length == 2) {
      sb.append("Currently the logical address is segmented into a page number ");
      
      sb.append("with " + segstart[1] + "bits and an offset of " + (str.length() - segstart[1]) + " bits.\n");
      
      sb.append("Remove this segment by moving the mouse to the segmented ");
      sb.append("position\n");
      sb.append("and clicking the mouse while holding down the SHIFT key.\n");
    }
    else if (segstart.length == 3) {
      sb.append("Currently the logical address is segmented into 3 fields.\n");
      sb.append("Remove all of the segments by moving the mouse to a ");
      sb.append("segmented position\n");
      sb.append("and clicking the mouse while holding down the SHIFT key.\n");
    }
    sb.append("Since the page size is " + (1 << data.pageSizeBits));
    sb.append(" the offset should have " + data.pageSizeBits + " bits.\n");
    sb.append("Move the cursor into the logical address until the ");
    sb.append("values shown in the Segment button are ");
    sb.append(str.length() - data.pageSizeBits + " and ");
    sb.append(data.pageSizeBits + " bits.\n");
    sb.append("Then click the mouse in that position\n");
    
    return sb.toString();
  }
  


  private String physicalSegmentedEntryHint(Address ad)
  {
    SegmentedPanel sp = ad.getPhysicalPanel();
    SegmentedPanelInfo info = sp.getInfo();
    StringBuffer sb = new StringBuffer();
    sb.append("You must correctly segment the physical address.\n");
    if (!sp.getModeSegment()) {
      sb.append("The physical address needs to be put in Segment mode by ");
      sb.append("clicking on the \nSegment button of the physical address.\n");
      sb.append("When this is done, the Segment button should be highlighted\n");
    }
    if (segstart.length == 1) {
      sb.append("Currently the physical address is not segmented.\n");
    }
    else if (segstart.length == 2) {
      sb.append("Currently the physical address is segmented into a frame number ");
      
      sb.append("with " + segstart[1] + "bits and an offset of " + (str.length() - segstart[1]) + " bits.\n");
    }
    
    sb.append("Since the page size is " + (1 << data.pageSizeBits));
    sb.append(" the offset should have " + data.pageSizeBits + " bits.\n");
    sb.append("Move the cursor into the physical address until the ");
    sb.append("values shown in the Segment button are ");
    sb.append(str.length() - data.pageSizeBits + " and ");
    sb.append(data.pageSizeBits + "bits.\n");
    sb.append("Then click the mouse in that position\n");
    return sb.toString();
  }
  

  private String pasteFrameOffsetEntryHint(Address ad)
  {
    SegmentedPanel sp = ad.getLogicalPanel();
    StringBuffer sb = new StringBuffer();
    sb.append("You must copy the offset from the logical address to the");
    sb.append(" physical address.\n");
    if (!sp.getModeSelect()) {
      sb.append("Put the logical address in Select mode.\n");
      sb.append("Do this by clicking the Select button of the logical address.\n");
      
      sb.append("The Select button should then be highlighted.\n");
    }
    sp = ad.getPhysicalPanel();
    if (!sp.getModePaste()) {
      sb.append("Put the physical address in Paste mode.\n");
      sb.append("Do this by clicking the Paste button of the physical address.\n");
      
      sb.append("The Paste button should then be highlighted.\n");
    }
    sb.append("Click the offset field of the logical address to put it in ");
    sb.append("the clipboard.\n");
    sb.append("Then click the offset field of the physical address to put");
    sb.append(" this offset in the physical address.\n");
    return sb.toString();
  }
  





  private String TLBSetmentedEntryHint(Address ad)
  {
    int nBits = data.logicalAddressBits + data.physicalAddressBits - 2 * data.pageSizeBits;
    
    int pnBits = data.logicalAddressBits - data.pageSizeBits;
    int fnBits = data.physicalAddressBits - data.pageSizeBits;
    TLBDisplay tlbD = ad.getTLBDisplay();
    StringBuffer sb = new StringBuffer();
    sb.append("The TLB must be correctly segmented.\n");
    if ((tlbD == null) || (!tlbD.isVisible())) {
      sb.append("Display the TLB by clicking on the TLB button at the bottom");
      sb.append(" of the window containing the Lifeline button.\n");
    }
    if ((tlbD != null) && (!tlbD.getSegmentMode())) {
      sb.append("Set the TLB to Segment mode by pushing the Mode button.\n");
    }
    if (tlbD != null) {
      int segmentedPosition = tlbD.getSegmentedPosition();
      if (segmentedPosition != -1) {
        sb.append("The TLB is segmented, but in the wrong place.\n");
        sb.append("Currently it is segmented at position " + segmentedPosition);
        sb.append(" corresponding to\n" + segmentedPosition);
        sb.append(" bits for the page number and " + (nBits - segmentedPosition));
        sb.append(" for the frame number.\n");
      }
    }
    sb.append("Since a virtual address has " + data.logicalAddressBits);
    sb.append(" bits and the page size of " + (1 << data.pageSizeBits) + "\n");
    sb.append("corresponds to " + data.pageSizeBits + " for the offset,\n");
    sb.append("the page number should contain " + pnBits + " bits\n");
    sb.append("and the frame number should contain " + fnBits + " bits.\n");
    sb.append("Segment the TLB at position " + pnBits + " by moving the cursor\n");
    
    sb.append("to that position in the TLB and clicking the mouse.\n");
    sb.append("As you move the mouse, the corresponding number of bits for the");
    sb.append("\npage number and frame number\n");
    sb.append("are displayed in the Mode button.\n");
    return sb.toString();
  }
  


  private String logicalSelectedEntryHint(Address ad)
  {
    SegmentedPanel sp = ad.getLogicalPanel();
    SegmentedPanelInfo info = sp.getInfo();
    StringBuffer sb = new StringBuffer();
    
    sb.append("Select the page number in the logical address.\n");
    if (!sp.getModeSelect()) {
      sb.append("Put the logical address in Select mode.\n");
      sb.append("Do this by clicking the Select button of the logical address.\n");
      
      sb.append("The Select button should then be highlighted.\n");
    }
    sb.append("Select the page number in the logical address by ");
    sb.append("clicking the mouse in that field.\n");
    return sb.toString();
  }
  

  private String tlbSearchEntryHint(Address ad)
  {
    StringBuffer sb = new StringBuffer();
    TLBDisplay tlbD = ad.getTLBDisplay();
    sb.append("Search the TLB for the logical page number.\n");
    if ((tlbD == null) || (!tlbD.isVisible())) {
      sb.append("Display the TLB by clicking on the TLB button at the bottom");
      sb.append(" of the window containing the Lifeline button.\n");
    }
    sb.append("Click the Lookup button located near the bottom of the TLB.\n");
    
    return sb.toString();
  }
  

  private String displayPageTableEntryHint(Address ad)
  {
    MemoryDisplay memDisplay = ad.getMemoryDisplay();
    StringBuffer sb = new StringBuffer();
    if (data.logicalAddress2Bits == 0) {
      sb.append("Display the page table view of memory.\n");
    }
    else {
      sb.append("Display the top level page table view of memory.\n");
    }
    if ((memDisplay == null) || (!memDisplay.isVisible())) {
      sb.append("Click the Memrory View button located near the bottom of the\n");
      
      sb.append("window containing the Lifeline button.\n");
    }
    if ((memDisplay != null) && (!memDisplay.modePageTableTop())) {
      if (data.logicalAddress2Bits == 0) {
        sb.append("Click the Full Table button at the bottom of the memory view.\n");
      }
      else
      {
        sb.append("Click the First button at the bottom of the memory view.\n");
      }
    }
    
    return sb.toString();
  }
  


  private String displayFinalFrameNumberEntryHint(Address ad)
  {
    StringBuffer sb = new StringBuffer();
    String cbs = ad.getClipBoardString();
    long cbsValue = Long.parseLong(cbs, 2);
    
    sb.append("Must find frame number in page table.\n");
    if (data.logicalAddress2Bits == 0) {
      if (cbsValue != data.pageNumber) {
        sb.append("Select the page number in the logical address.\n");
      }
      sb.append("Click the Entry button in the memory view.\n");
    }
    else {
      sb.append("Click the Entry button in the memory view.\n");
    }
    return sb.toString();
  }
  

  private String pasteFrameNumberEntryHint(Address ad)
  {
    StringBuffer sb = new StringBuffer();
    if (data.pageFault) {
      sb.append("Since the valid bit of this entry is clear,");
      
      sb.append(" a page fault has occured.\n");
      sb.append("Click the yellow Page Fault button in the window containing");
      sb.append(" the LifeLine button.\n");
    }
    else {
      sb.append("Paste the frame number from the page table into the ");
      sb.append("physical address.\n");
      sb.append("Click the first field of the physical address.\n");
    }
    return sb.toString();
  }
  
  private String testCompletedSuccessfullyEntryHint(Address ad)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("The correct physical address has been found.\n");
    sb.append("Click the Found Physical Page button in the window containing");
    sb.append(" the Lifeline button.\n");
    return sb.toString();
  }
  




  private String logicalSegmented2EntryHint(Address ad)
  {
    int pos1 = data.logicalAddressBits - data.logicalAddress2Bits - data.pageSizeBits;
    
    int pos2 = pos1 + data.logicalAddress2Bits;
    int page2Bits = data.logicalAddress2Bits;
    
    SegmentedPanel sp = ad.getLogicalPanel();
    SegmentedPanelInfo info = sp.getInfo();
    
    StringBuffer sb = new StringBuffer();
    sb.append("The logical address must be segmented into three parts.\n");
    if (!sp.getModeSegment()) {
      sb.append("Start by putting the logical address in segment mode by ");
      sb.append("clicking the Segment button.\n");
    }
    if ((segstart.length == 2) && (segstart[1] == pos2)) {
      sb.append("The Logical address is correctly segmented into a ");
      sb.append("full page number and offset.\n");
      sb.append("You must now segment the full page number into its two parts.\n");
      
      sb.append("Since the second level page table has ");
      sb.append((1 << page2Bits) + " entries, " + page2Bits);
      sb.append(" bits are needed for the second level page number.\n");
      sb.append("This leaves " + pos1 + " bits for the first level page number.\n");
      
      sb.append("The logical address should be segmented into three parts ");
      sb.append("having sizes " + pos1 + ", " + page2Bits + " and " + data.pageSizeBits + ".\n");
      
      sb.append("Move the cursor into the logical address at position " + pos1);
      sb.append(" and click the mouse.\n");
      sb.append("If you make a mistake, you can remove segments by clicking\n");
      sb.append("the mouse on a segment boundary while holding down the ");
      sb.append("SHIFT key.\n");
    }
    else
    {
      sb.append("Segment the logical address again.\n");
      sb.append("The full logical page number needs to be divided into");
      sb.append(" page numbers for the two levels.\n");
      sb.append("Start by removing all of the segments by moving the mouse\n");
      sb.append("to a segment boundary and clicking it while holding down");
      sb.append(" the SHIFT key.\n");
      sb.append("Since the second level page table has ");
      sb.append((1 << page2Bits) + " entries, " + page2Bits);
      sb.append(" bits are needed for the second level page number.\n");
      sb.append("This leaves " + pos1 + " bits for the first level page number.\n");
      
      sb.append("The logical address should be segmented into three parts ");
      sb.append("having sizes " + pos1 + ", " + page2Bits + " and " + data.pageSizeBits + ".\n");
      
      sb.append("Move the cursor into the logical address at positions " + pos1);
      sb.append(" and " + pos2 + " and click the mouse.\n");
      sb.append("If you make a mistake, you can remove segments by clicking\n");
      sb.append("the mouse on a segment boundary while holding down the ");
      sb.append("SHIFT key.\n");
    }
    return sb.toString();
  }
  
  private String logicalSelected1EntryHint(Address ad)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("Select the first level page number in the logical address.\n");
    sb.append("Start by putting the logical address in Select mode by\n");
    sb.append("clicking the Select button in the logical address.\n");
    sb.append("Then click the first field of the logical address.\n");
    sb.append("This puts the first level page number in the clipboard.\n");
    return sb.toString();
  }
  
  private String selectTopFrameEntryHint(Address ad)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("Find the entry corresponding to the first level page number");
    sb.append(" in the first level lage table.\n");
    sb.append("Click the Entry button is the first level page table.\n");
    return sb.toString();
  }
  

  private String displaySecondMemoryViewEntryHint(Address ad)
  {
    MemoryDisplay md = ad.getMemoryDisplay();
    
    StringBuffer sb = new StringBuffer();
    if (getInvalidPageNumberFound()) {
      sb.append("An invalid entry in the top level page table was found.\n");
      sb.append("Click the yellow Invalid Reference button in the window\n");
      sb.append("containing the Lifeline button.\n");
    }
    else {
      sb.append("A valid entry in the top level page table was found.\n");
      sb.append("Click the Second button near the bottom of the memory view");
      sb.append("to display a second level page table.\n");
    }
    return sb.toString();
  }
  
  private String setStartingFrame2EntryHint(Address ad)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("Set the starting frame of the second level page table.\n");
    sb.append("Since this frame number is in the clipboard, click the\n");
    sb.append("Frame button button in the memory view.\n");
    return sb.toString();
  }
  
  private String selectPageNumber2EntryHint(Address ad)
  {
    StringBuffer sb = new StringBuffer();
    sb.append("Select the second level page number in the logical address.\n");
    sb.append("Do this by clicking the second field int he logical address.\n");
    return sb.toString();
  }
  
  public void setHelp(boolean progressHelp, boolean progressDo) {
    this.progressHelp = progressHelp;
    this.progressDo = progressDo;
  }
}
