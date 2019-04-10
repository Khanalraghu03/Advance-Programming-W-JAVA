public class MemoryValue
{
  public long addr;
  public byte value;
  public boolean valid;
  
  public MemoryValue(long addr, byte value) {
    this.addr = addr;
    this.value = value;
    valid = false;
  }
  
  public MemoryValue(long addr, byte value, boolean valid) {
    this.addr = addr;
    this.value = value;
    this.valid = valid;
  }
}
