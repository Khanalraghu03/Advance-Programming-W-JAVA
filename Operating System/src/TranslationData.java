import Jeli.Probability.Distribution;

public class TranslationData
{
  public static Distribution trDist = null;
  private static double seed = 0.12345D;
  
  private String name;
  
  private String displayName;
  public boolean useTlb;
  public boolean inTlb;
  public boolean pageFault;
  public boolean legalPage;
  public int tlbEntries;
  public int logicalAddressBits;
  public int logicalAddress2Bits;
  public int physicalAddressBits;
  public int pageSizeBits;
  public long logicalAddress;
  public long frameNumber;
  public boolean byteOrderLittle;
  public long pageTableStartFrame;
  public int pageTableEntryBytes;
  public int helpMsgTimeout = 15;
  
  public long pageNumber;
  
  public long pageNumber1;
  
  public long pageNumber2;
  
  public long pageNumberLast;
  public long offset;
  public long physicalAddress;
  public int pageTableLevels;
  public long topFrame = 0L;
  public int completeCount = 0;
  public int failCount = 0;
  






  public TranslationData(String name, String displayName, boolean useTlb, boolean inTlb, boolean pageFault, boolean legalPage, TestDefaults td, long logicalAddress, long frameNumber, long pageTableStartFrame, int pageTableEntryBytes, boolean byteOrderLittle)
  {
    this(name, displayName, useTlb, inTlb, pageFault, legalPage, tlbEntries, logicalAddressBits, logicalAddress2Bits, physicalAddressBits, pageSizeBits, logicalAddress, frameNumber, pageTableStartFrame, pageTableEntryBytes, byteOrderLittle);
  }
  











  public TranslationData(String name, String displayName, boolean useTlb, boolean inTlb, boolean pageFault, boolean legalPage, int tlbEntries, int logicalAddressBits, int logicalAddress2Bits, int physicalAddressBits, int pageSizeBits, long logicalAddress, long frameNumber, long pageTableStartFrame, int pageTableEntryBytes, boolean byteOrderLittle)
  {
    this.name = name;
    this.displayName = displayName;
    this.useTlb = useTlb;
    this.inTlb = inTlb;
    this.pageFault = pageFault;
    this.legalPage = legalPage;
    this.tlbEntries = tlbEntries;
    this.logicalAddressBits = logicalAddressBits;
    this.logicalAddress2Bits = logicalAddress2Bits;
    this.physicalAddressBits = physicalAddressBits;
    this.pageSizeBits = pageSizeBits;
    this.logicalAddress = logicalAddress;
    this.frameNumber = frameNumber;
    this.byteOrderLittle = byteOrderLittle;
    this.pageTableStartFrame = pageTableStartFrame;
    this.pageTableEntryBytes = pageTableEntryBytes;
    calculateValues();
  }
  
  public static void setSeed(double sd) {
    seed = sd;
  }
  
  public static void initDist() {
    if (trDist != null) {
      return;
    }
    trDist = new Distribution(4, 0, Integer.MAX_VALUE);
    
    trDist.setPrivateSeed(seed);
  }
  
  public void setDisplayName(String s)
  {
    displayName = s;
  }
  

  public void calculateValues()
  {
    
    
    if (tlbEntries <= 0) {
      tlbEntries = 0;
      useTlb = false;
    }
    if (!useTlb) {
      tlbEntries = 0;
      inTlb = false;
    }
    

    if ((logicalAddressBits <= 0) || (logicalAddressBits > 63)) {
      logicalAddressBits = 63;
    }
    if ((logicalAddress2Bits <= 0) || (logicalAddress2Bits >= logicalAddressBits))
    {
      logicalAddress2Bits = 0;
    }
    if ((physicalAddressBits <= 0) || (physicalAddressBits > 63)) {
      physicalAddressBits = 63;
    }
    if ((pageSizeBits <= 0) || (pageSizeBits >= logicalAddressBits)) {
      pageSizeBits = (logicalAddressBits - 1);
    }
    if (pageSizeBits >= physicalAddressBits) {
      pageSizeBits = (physicalAddressBits - 1);
    }
    long maxLogicalAddress = bitsToValue(logicalAddressBits);
    if ((logicalAddress <= 0L) || (logicalAddress >= maxLogicalAddress)) {
      logicalAddress = getRandomAddress(logicalAddressBits);
    }
    int frameNumberBits = physicalAddressBits - pageSizeBits;
    long maxFrameNumber = bitsToValue(frameNumberBits);
    if ((frameNumber <= 0L) || (frameNumber >= maxFrameNumber)) {
      frameNumber = getRandomAddress(frameNumberBits);
    }
    if ((pageTableStartFrame <= 0L) || (pageTableStartFrame >= maxFrameNumber)) {
      pageTableStartFrame = getRandomAddress(frameNumberBits);
    }
    if ((pageTableEntryBytes <= 0) || (pageTableEntryBytes > 8)) {
      if (frameNumberBits < 8) {
        pageTableEntryBytes = 1;
      } else if (frameNumberBits < 16) {
        pageTableEntryBytes = 2;
      } else if (frameNumberBits < 32) {
        pageTableEntryBytes = 4;
      } else {
        pageTableEntryBytes = 8;
      }
    }
    


    pageNumber = (logicalAddress >>> pageSizeBits);
    

    offset = (logicalAddress - (pageNumber << pageSizeBits));
    

    physicalAddress = ((frameNumber << pageSizeBits) + offset);
    if (logicalAddress2Bits == 0) {
      pageTableLevels = 1;
    }
    else {
      pageTableLevels = 2;
    }
    pageNumber1 = (pageNumber >>> logicalAddress2Bits);
    pageNumber2 = (pageNumber - (pageNumber1 << logicalAddress2Bits));
    if (pageTableLevels == 1) {
      pageNumberLast = pageNumber;
    } else
      pageNumberLast = pageNumber2;
  }
  
  private long bitsToValue(int bits) {
    long value = 1L;
    for (int i = 0; i < bits; i++) {
      value *= 2L;
    }
    return value;
  }
  




  private long getRandomAddress(int bits)
  {
    long max = bitsToValue(bits);
    if (max < 2147483647L) {
      long val = trDist.next();
      val %= max;
      if (val == 0L) {
        val = 1L;
      }
      
      return val;
    }
    long val1 = trDist.next();
    long val2 = trDist.next();
    long val = val1 + (val2 << 31);
    val %= max;
    
    return val;
  }
  
  public String getName() {
    return name;
  }
  
  public String getDisplayName() {
    return displayName;
  }
}
