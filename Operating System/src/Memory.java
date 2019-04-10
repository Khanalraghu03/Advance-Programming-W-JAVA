import Jeli.Probability.Distribution;
import java.util.Vector;

public class Memory
{
  private Vector mem;
  private boolean byteOrderLittle;
  private Distribution dist;
  private static boolean memtest = false;
  
  public Memory(int numBits, boolean byteOrderLittle, double seed) {
    this.byteOrderLittle = byteOrderLittle;
    mem = new Vector();
    dist = new Distribution(3, 0.0D, 1.0D);
    dist.setPrivateSeed(seed);
  }
  
  public static void setMemtest(boolean f) {
    memtest = f;
  }
  

  public void setByte(long addr, byte value)
  {
    mem.addElement(new MemoryValue(addr, value));
  }
  
  public void setByte(long addr, byte value, boolean valid) {
    mem.addElement(new MemoryValue(addr, value, valid));
  }
  
  public boolean getValid(long addr)
  {
    for (int i = 0; i < mem.size(); i++) {
      MemoryValue memval = (MemoryValue)mem.elementAt(i);
      if (addr == addr) {
        return valid;
      }
    }
    return false;
  }
  

  public byte getByte(long addr)
  {
    for (int i = 0; i < mem.size(); i++) {
      MemoryValue memval = (MemoryValue)mem.elementAt(i);
      if (addr == addr) {
        return value;
      }
    }
    return myhash(addr);
  }
  

  private int getByteValue(long addr)
  {
    byte b = getByte(addr);
    int val = b;
    if (val < 0) {
      val = (b & 0x7F) + 128;
    }
    return val;
  }
  
  public long getWord(long address, int numBytes)
  {
    int numshift = 0;
    long value = 0L;
    for (int i = 0; i < numBytes; i++)
    {
      value += (getByteValue(address + i) << numshift);
      numshift += 8;
    }
    
    return value;
  }
  


  public void setWord(long address, long value, int numBytes, boolean valid)
  {
    for (int i = 0; i < numBytes; i++) {
      byte thisOne = (byte)(int)(value & 0xFF);
      
      setByte(address, thisOne, (valid) && (i == 0));
      address += 1L;
      value >>>= 8;
    }
  }
  





  private byte myhash(long addr)
  {
    if (memtest) {
      return 0;
    }
    long b1 = addr & 0xFF;
    long b2 = addr >> 8 & 0xFF;
    long b3 = addr >> 16 & 0xFF;
    long b4 = addr >> 24 & 0xFF;
    long v1 = b1 * b1;
    v1 *= v1;
    long val = b1 * v1 - b1;
    v1 = b2 * b2;
    v1 *= v1;
    val += b2 * v1 - b2;
    v1 = b3 * b3;
    v1 *= v1;
    val += b3 * v1 - b3;
    v1 = b4 * b4;
    v1 *= v1;
    val += b4 * v1 - b4;
    return (byte)(int)val;
  }
  
  public String getString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("Memory has " + mem.size() + " entries\n");
    for (int i = 0; i < mem.size(); i++) {
      MemoryValue mv = (MemoryValue)mem.elementAt(i);
      sb.append(Long.toString(addr, 2) + ": " + Integer.toString(value, 2) + "\n");
    }
    
    return sb.toString();
  }
}
