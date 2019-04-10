package Jeli.Logging;

































class LZWStringTable
{
  private static final int RES_CODES = 2;
  































  private static final short HASH_FREE = -1;
  































  private static final short NEXT_FIRST = -1;
  































  private static final int MAXBITS = 12;
  































  private static final int MAXSTR = 4096;
  































  private static final short HASHSIZE = 9973;
  































  private static final short HASHSTEP = 2039;
  































  byte[] strChr_;
  































  short[] strNxt_;
  































  short[] strHsh_;
  































  short numStrings_;
  
































  public LZWStringTable()
  {
    strChr_ = new byte['က'];
    strNxt_ = new short['က'];
    strHsh_ = new short['⛵'];
  }
  

  public int addCharString(short index, byte b)
  {
    if (numStrings_ >= 4096) {
      return 65535;
    }
    int hshidx = hashIt(index, b);
    while (strHsh_[hshidx] != -1) {
      hshidx = (hshidx + 2039) % 9973;
    }
    strHsh_[hshidx] = numStrings_;
    strChr_[numStrings_] = b;
    strNxt_[numStrings_] = (index != -1 ? index : -1);
    
    return numStrings_++;
  }
  

  public short findCharString(short index, byte b)
  {
    if (index == -1) {
      return (short)b;
    }
    int hshidx = hashIt(index, b);
    int nxtidx; while ((nxtidx = strHsh_[hshidx]) != -1) {
      if ((strNxt_[nxtidx] == index) && (strChr_[nxtidx] == b))
        return (short)nxtidx;
      hshidx = (hshidx + 2039) % 9973;
    }
    
    return -1;
  }
  
  public void clearTable(int codesize) {
    numStrings_ = 0;
    
    for (int q = 0; q < 9973; q++) {
      strHsh_[q] = -1;
    }
    
    int w = (1 << codesize) + 2;
    for (int q = 0; q < w; q++)
      addCharString((short)-1, (byte)q);
  }
  
  public static int hashIt(short index, byte lastbyte) {
    return (((short)(lastbyte << 8) ^ index) & 0xFFFF) % 9973;
  }
}
