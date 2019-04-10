import java.util.StringTokenizer;
import java.util.Vector;

public class AddressTests
{
  private static final String allDelim = "\n\r\t ,";
  private static final String lineDelim = "\n\r";
  private Vector singleTestButtonVector;
  private Vector twoTestButtonVector;
  private Jeli.Widgets.JeliButtonCallBack cb;
  private String errorMessage;
  private int errorEntry = 0;
  
  private TestDefaults singleDefaults;
  
  private TestDefaults twoDefaults;
  
  public AddressTests(String testlist, boolean inhibitSingleTests, boolean inhibitTwoTests, TestDefaults singleTestDefaults, TestDefaults twoTestDefaults, Jeli.Widgets.JeliButtonCallBack cb)
  {
    this.cb = cb;
    singleDefaults = singleTestDefaults;
    twoDefaults = twoTestDefaults;
    singleTestButtonVector = new Vector();
    twoTestButtonVector = new Vector();
    handleTestList(testlist);
    if (singleTestButtonVector.size() > 0)
      return;
    if (twoTestButtonVector.size() > 0)
      return;
    if (!inhibitSingleTests)
      setSingleDefaultTests();
    if (!inhibitTwoTests)
      setTwoDefaultTests();
  }
  
  public int getSingleSize() {
    return singleTestButtonVector.size();
  }
  
  public Jeli.Widgets.JeliButton getSingleButton(int n) {
    if (n < singleTestButtonVector.size())
      return (Jeli.Widgets.JeliButton)singleTestButtonVector.elementAt(n);
    return null;
  }
  
  public int getTwoSize() {
    return twoTestButtonVector.size();
  }
  
  public Jeli.Widgets.JeliButton getTwoButton(int n) {
    if (n < twoTestButtonVector.size())
      return (Jeli.Widgets.JeliButton)twoTestButtonVector.elementAt(n);
    return null;
  }
  
  public String getErrorMessage() {
    return errorMessage;
  }
  
  public int getErrorEntry() {
    return errorEntry;
  }
  


  private void handleTestList(String testlist)
  {
    if (testlist == null)
      return;
    StringTokenizer stk = new StringTokenizer(testlist, "\n\r");
    for (;;) {
      errorEntry += 1;
      TranslationData td = getNextTranslationData(stk);
      if (td == null) {
        return;
      }
      if (pageTableLevels == 1) {
        setSingleLevelButton(td, singleTestButtonVector.size() + 1);
      } else {
        setTwoLevelButton(td, twoTestButtonVector.size() + 1);
      }
    }
  }
  















  private TranslationData getNextTranslationData(StringTokenizer stk)
  {
    errorMessage = null;
    if (!stk.hasMoreTokens())
      return null;
    try {
      String name = stk.nextToken("\n\r");
      errorMessage = "No Display Name";
      String displayName = stk.nextToken("\n\r");
      errorMessage = "Invalid boolean useTlb value";
      boolean useTlb = nextBooleanToken(stk);
      errorMessage = "Invalid boolean inTlb value";
      boolean inTlb = nextBooleanToken(stk);
      errorMessage = "Invalid boolean pageFault value";
      boolean pageFault = nextBooleanToken(stk);
      errorMessage = "Invalid boolean legalPage value";
      boolean legalPage = nextBooleanToken(stk);
      errorMessage = "Invalid integer tlbEntries value";
      int tlbEntries = nextIntToken(stk);
      errorMessage = "Invalid integer logcialAddressBits value";
      int logicalAddressBits = nextIntToken(stk);
      errorMessage = "Invalid integer logicalAddress2Bits value";
      int logicalAddress2Bits = nextIntToken(stk);
      errorMessage = "Invlalid integer physicalAddressBits value";
      int physicalAddressBits = nextIntToken(stk);
      errorMessage = "Invalid integer pageSizeBits value";
      int pageSizeBits = nextIntToken(stk);
      errorMessage = "Invalid long logicalAddress value";
      long logicalAddress = nextLongToken(stk);
      errorMessage = "Invalid long frameNumber value";
      long frameNumber = nextLongToken(stk);
      errorMessage = "Invalid long pageTableStartFrame value";
      long pageTableStartFrame = nextLongToken(stk);
      errorMessage = "Invalid integer pageTableEntryBytes value";
      int pageTableEntryBytes = nextIntToken(stk);
      errorMessage = "Invalid boolean byteOrderLittle value";
      boolean byteOrderLittle = nextBooleanToken(stk);
      errorMessage = null;
      return new TranslationData(name, displayName, useTlb, inTlb, pageFault, legalPage, tlbEntries, logicalAddressBits, logicalAddress2Bits, physicalAddressBits, pageSizeBits, logicalAddress, frameNumber, pageTableStartFrame, pageTableEntryBytes, byteOrderLittle);
    }
    catch (Exception e) {}
    






    return null;
  }
  
  private boolean nextBooleanToken(StringTokenizer stk)
    throws Exception
  {
    String token = stk.nextToken("\n\r\t ,");
    if (token.equalsIgnoreCase("true"))
      return true;
    if (token.equalsIgnoreCase("false"))
      return false;
    throw new Exception();
  }
  
  private int nextIntToken(StringTokenizer stk) throws Exception
  {
    String token = stk.nextToken("\n\r\t ,");
    return Integer.parseInt(token);
  }
  
  private long nextLongToken(StringTokenizer stk) throws Exception
  {
    String token = stk.nextToken("\n\r\t ,");
    return Long.parseLong(token);
  }
  
  private void setSingleLevelButton(TranslationData td, int n)
  {
    Jeli.Widgets.JeliButton jb = new Jeli.Widgets.JeliButton("", cb);
    jb.setObjectInfo(td);
    jb.setInfo(n);
    singleTestButtonVector.addElement(jb);
  }
  
  private void setTwoLevelButton(TranslationData td, int n)
  {
    Jeli.Widgets.JeliButton jb = new Jeli.Widgets.JeliButton("", cb);
    jb.setObjectInfo(td);
    jb.setInfo(n);
    twoTestButtonVector.addElement(jb);
  }
  

  private void setSingleDefaultTests()
  {
    if (singleDefaults == null) {
      TranslationData td = new TranslationData("1: Single Level Page Table in TLB", "", true, true, false, true, 16, 30, 0, 23, 12, 123456789L, 123L, 54321L, 4, true);
      



      setSingleLevelButton(td, 1);
      
      td = new TranslationData("2: Single Level Page Table not in TLB", "", true, false, false, true, 16, 30, 0, 23, 12, 123456789L, 123L, 54321L, 4, true);
      



      setSingleLevelButton(td, 2);
      
      td = new TranslationData("3: Single Level Page Table with Page Fault", "", true, false, true, true, 12, 35, 0, 18, 8, 12345L, 35L, 1024L, 4, true);
      



      setSingleLevelButton(td, 3);
      
      td = new TranslationData("4: Single Level Page Table without TLB", "", false, false, false, true, 12, 20, 0, 18, 8, 12345L, 35L, 1024L, 4, true);
      



      setSingleLevelButton(td, 4);
      
      td = new TranslationData("5: Single Level Page Table with Page Fault, no TLB", "", false, false, true, true, 12, 35, 0, 18, 8, 12345L, 35L, 1024L, 4, true);
      


      setSingleLevelButton(td, 5);
    }
    else
    {
      TranslationData td = new TranslationData("1: Single Level Page Table in TLB", "", true, true, false, true, singleDefaults, -1L, -1L, -1L, -1, true);
      


      setSingleLevelButton(td, 1);
      
      td = new TranslationData("2: Single Level Page Table not in TLB", "", true, false, false, true, singleDefaults, -1L, -1L, -1L, -1, true);
      



      setSingleLevelButton(td, 2);
      
      td = new TranslationData("3: Single Level Page Table with Page Fault", "", true, false, true, true, singleDefaults, -1L, -1L, -1L, -1, true);
      


      setSingleLevelButton(td, 3);
      
      td = new TranslationData("4: Single Level Page Table without TLB", "", false, false, false, true, singleDefaults, -1L, -1L, -1L, -1, true);
      


      setSingleLevelButton(td, 4);
      
      td = new TranslationData("5: Single Level Page Table with Page Fault, no TLB", "", false, false, true, true, singleDefaults, -1L, -1L, -1L, -1, true);
      


      setSingleLevelButton(td, 5);
    }
  }
  

  private void setTwoDefaultTests()
  {
    if (twoDefaults == null) {
      TranslationData td = new TranslationData("1: Two Level Page Table in TLB", "", true, true, false, true, 16, 32, 5, 24, 10, 123456L, 123L, 1024L, 4, true);
      



      setTwoLevelButton(td, 1);
      
      td = new TranslationData("2: Two Level Page Table not in TLB", "", true, false, false, true, 16, 30, 9, 23, 12, 123456789L, 123L, 54321L, 4, true);
      



      setTwoLevelButton(td, 2);
      
      td = new TranslationData("3: Two Level Page Table with Page Fault", "", true, false, true, true, 12, 20, 8, 18, 8, 12345L, 35L, 1024L, 4, true);
      



      setTwoLevelButton(td, 3);
      
      td = new TranslationData("4: Two Level Page Table with Illegal Page", "", true, false, true, false, 12, 20, 8, 18, 8, 12345L, 35L, 1024L, 4, true);
      



      setTwoLevelButton(td, 4);
      
      td = new TranslationData("5: Two Level Page Table, no TLB", "", false, false, false, true, 12, 20, 8, 18, 8, 12345L, 35L, 1024L, 4, true);
      



      setTwoLevelButton(td, 5);
      
      td = new TranslationData("6: Two Level Page Table with Page Fault, no TLB", "", false, false, true, true, 12, 35, 8, 18, 8, 12345L, 35L, 1024L, 4, true);
      



      setTwoLevelButton(td, 6);
      
      td = new TranslationData("7: Two Level Page Table with Illegal Page, no TLB", "", false, false, true, false, 12, 20, 8, 18, 8, 12345L, 35L, 1024L, 4, true);
      



      setTwoLevelButton(td, 7);
    }
    else {
      TranslationData td = new TranslationData("1: Two Level Page Table in TLB", "", true, true, false, true, twoDefaults, -1L, -1L, -1L, -1, true);
      


      setTwoLevelButton(td, 1);
      
      td = new TranslationData("2: Two Level Page Table not in TLB", "", true, false, false, true, twoDefaults, -1L, -1L, -1L, -1, true);
      


      setTwoLevelButton(td, 2);
      
      td = new TranslationData("3: Two Level Page Table with Page Fault", "", true, false, true, true, twoDefaults, -1L, -1L, -1L, -1, true);
      


      setTwoLevelButton(td, 3);
      
      td = new TranslationData("4: Two Level Page Table with Illegal Page", "", true, false, true, false, twoDefaults, -1L, -1L, -1L, -1, true);
      


      setTwoLevelButton(td, 4);
      
      td = new TranslationData("5: Two Level Page Table without TLB", "", false, false, false, true, twoDefaults, -1L, -1L, -1L, -1, true);
      


      setTwoLevelButton(td, 5);
      
      td = new TranslationData("6: Two Level Page Table with Page Fault, no TLB", "", false, false, true, true, twoDefaults, -1L, -1L, -1L, -1, true);
      


      setTwoLevelButton(td, 6);
      
      td = new TranslationData("7: Two Level Page Table with Illegal Page", "", false, false, true, false, twoDefaults, -1L, -1L, -1L, -1, true);
      


      setTwoLevelButton(td, 7);
    }
  }
}
