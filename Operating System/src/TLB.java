import Jeli.Probability.Distribution;

public class TLB
{
  private int size;
  private TLBEntry[] entries;
  private Memory mem;
  private int pageBits;
  private int frameBits;
  private int correctEntry = -1;
  
  private long pageTableStartAddress;
  
  private int pageTableEntryBytes;
  private Distribution dist;
  private long maxPage;
  private long maxFrame;
  private boolean oneLevel = true;
  
  public TLB(int size, int pageBits, int frameBits, Memory mem, long pageTableStartAddress, int pageTableEntryBytes, double seed)
  {
    this.size = size;
    this.pageBits = pageBits;
    this.frameBits = frameBits;
    this.pageTableStartAddress = pageTableStartAddress;
    this.pageTableEntryBytes = pageTableEntryBytes;
    this.mem = mem;
    maxPage = bitsToValue(pageBits);
    maxFrame = bitsToValue(frameBits);
    entries = new TLBEntry[size];
    
    dist = new Distribution(3, 0.0D, 1.0D);
    dist.setPrivateSeed(seed);
  }
  


  public void initialize(long pn, long fn, boolean use, boolean fault)
  {
    for (int i = 0; i < size; i++) {
      entries[i] = new TLBEntry(0L, 0L);
    }
    int thisent = getRandomEntry();
    if (use) {
      entries[thisent].pn = pn;
      entries[thisent].fn = fn;
      correctEntry = thisent;
    }
    if (!fault) {
      setMemory(pn, fn, true);
    }
    
    for (int i = 0; i < size; i++) {
      if ((!use) || (i != thisent)) {
        fillEntry(i, pn);
      }
    }
  }
  








  public long initialize2(long pn, long fn, boolean use, boolean valid, boolean fault, int pageBits2, int pageSizeBits)
  {
    long pageNumber1 = pn >>> pageBits2;
    oneLevel = false;
    long topFrame = getRandomFrame(fn);
    
    for (int i = 0; i < size; i++) {
      entries[i] = new TLBEntry(0L, 0L);
    }
    int thisent = getRandomEntry();
    if (use) {
      entries[thisent].pn = pn;
      entries[thisent].fn = fn;
      correctEntry = thisent;
    }
    if (valid) {
      setMemory(pageNumber1, topFrame, true);
    }
    
    long pageNumber2 = pn - (pageNumber1 << pageBits2);
    
    long secondLevelAddress = (topFrame << pageSizeBits) + pageNumber2 * pageTableEntryBytes;
    
    mem.setWord(secondLevelAddress, fn, pageTableEntryBytes, !fault);
    
    return topFrame;
  }
  
  private long getRandomFrame(long fn)
  {
    return fn + 1L;
  }
  



















  public int getCorrectEntry()
  {
    return correctEntry;
  }
  
  public int getSize() {
    return size;
  }
  
  public int getPageBits() {
    return pageBits;
  }
  
  public int getFrameBits() {
    return frameBits;
  }
  
  public long getPage(int i) {
    return entries[i].pn;
  }
  
  public long getFrame(int i) {
    return entries[i].fn;
  }
  
  private long bitsToValue(int bits) {
    long value = 1L;
    for (int i = 0; i < bits; i++) {
      value *= 2L;
    }
    return value;
  }
  
  private int getRandomEntry()
  {
    if (size == 0) {
      return -1;
    }
    if (size == 1) {
      return 0;
    }
    int ent = (int)(size * dist.next());
    if (ent >= size) {
      ent = size - 1;
    }
    return ent;
  }
  
  private int findDuplicatePage() {
    return -1;
  }
  
  private long findNewPageNumber(int which, long pn) {
    long newpn = 0L;
    boolean foundNew = false;
    while (!foundNew) {
      newpn = (maxPage * dist.next());
      
      if (newpn == 0L) {
        newpn = 1L;
      }
      if (newpn >= maxPage) {
        newpn = maxPage - 1L;
      }
      if (newpn != pn)
      {

        foundNew = true;
        for (int i = 0; i < which; i++) {
          if (newpn == entries[i].pn) {
            foundNew = false;
            break;
          }
        }
      }
    }
    return newpn;
  }
  

  private void fillEntry(int i, long pn)
  {
    long newpn = findNewPageNumber(i, pn);
    
    long newfn = (maxFrame * dist.next());
    if (newfn == 0L) {
      newfn = 1L;
    }
    if (newfn >= maxFrame) {
      newfn = maxFrame - 1L;
    }
    
    entries[i].pn = newpn;
    entries[i].fn = newfn;
    setMemory(newpn, newfn, true);
  }
  



  private void setSecondLevelPageTable(long startFrame, long entry, long val) {}
  



  private void setMemory(long pn, long fn, boolean valid)
  {
    long addr = pageTableStartAddress + pn * pageTableEntryBytes;
    long value = fn;
    mem.setWord(addr, value, pageTableEntryBytes, valid);
  }
  
  public void showIt()
  {
    System.out.println("TLB has size " + size);
    for (int i = 0; i < size; i++) {
      System.out.println(sl(entries[i].pn) + sl(entries[i].fn));
    }
  }
  
  private String sl(long val)
  {
    String t = Long.toString(val, 2);
    while (t.length() < 20) {
      t = " " + t;
    }
    return t;
  }
  
  public String getString()
  {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < size; i++) {
      sb.append(Long.toString(entries[i].pn, 2));
      sb.append("\t");
      sb.append(Long.toString(entries[i].fn, 2));
      sb.append("\n");
    }
    return sb.toString();
  }
}
