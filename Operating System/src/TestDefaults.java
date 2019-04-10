public class TestDefaults
{
  public int tlbEntries;
  public int logicalAddressBits;
  public int logicalAddress2Bits;
  public int physicalAddressBits;
  public int pageSizeBits;
  
  public TestDefaults(int a, int b, int c, int d, int e) {
    tlbEntries = a;
    logicalAddressBits = b;
    logicalAddress2Bits = c;
    physicalAddressBits = d;
    pageSizeBits = e;
  }
  
  public TestDefaults(int a, int b, int c, int d) {
    this(a, b, 0, c, d);
  }
}
