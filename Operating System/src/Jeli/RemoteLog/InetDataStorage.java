package Jeli.RemoteLog;

public class InetDataStorage
{
  protected static final byte MAGIC = -91;
  protected static final byte CMD_HANDLE = 1;
  protected static final byte CMD_OPEN = 2;
  protected static final byte CMD_DATA = 3;
  protected static final byte CMD_CLOSE = 4;
  protected static final byte CMD_GETBASE = 5;
  protected static final byte CMD_GETINFO = 6;
  protected static final byte ID_OK = 0;
  protected static final byte ID_OVRFLOW = -128;
  protected static final byte ID_ERR = -1;
  public static final int IPPORT_IDS = 61;
  
  public InetDataStorage() {}
  
  public static boolean verify(IDSCommand idsCmd) {
    return idsCmd.id() == -91;
  }
  


  public static boolean verifyCommand(IDSCommand idsCmd)
  {
    return isCommand(idsCmd.code());
  }
  

  public static boolean verifyResult(IDSCommand idsCmd)
  {
    return idsCmd.code() == 0;
  }
  


  public static IDSCommand makeCommand(int cmd, int dataSize)
  {
    IDSCommand idsCmd = new IDSCommand();
    idsCmd.id((byte)-91);
    idsCmd.code((byte)cmd);
    idsCmd.dataSize(dataSize);
    
    return idsCmd;
  }
  



  public static IDSCommand makeResult(boolean bOK, int dataSize)
  {
    int code = 0;
    
    if (!bOK) {
      code = -1;
    }
    return makeCommand(code, dataSize);
  }
  



  protected static boolean isCommand(byte cmd)
  {
    boolean result = false;
    switch (cmd)
    {
    case 1: 
    case 2: 
    case 3: 
    case 4: 
    case 5: 
    case 6: 
      result = true;
    }
    
    return result;
  }
}
