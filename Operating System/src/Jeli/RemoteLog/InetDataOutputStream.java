package Jeli.RemoteLog;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;


public class InetDataOutputStream
  extends DataOutputStream
{
  private Socket sock;
  
  public InetDataOutputStream(Socket sock)
    throws IOException
  {
    super(sock.getOutputStream());
    this.sock = sock;
  }
  


  public void write(int b)
    throws IOException
  {
    byteBuf[0] = ((byte)b);
    write(byteBuf, 0, 1);
  }
  


  public void write(byte[] b, int off, int len)
    throws IOException
  {
    IDSCommand idsCmd = InetDataStorage.makeCommand(3, len);
    
    idsCmd.send(out);
    

    out.write(b, off, len);
    

    try
    {
      idsCmd.receive(sock.getInputStream());
      

      if (idsCmd.code() == -1)
      {
        throw new IOException("InetDataOutputStream: write error");
      }
      

      if (idsCmd.code() == Byte.MIN_VALUE)
      {
        throw new IOException("InetDataOutputStream: server overflow");
      }
      
    }
    catch (Exception e)
    {
      throw new IOException(e.getMessage());
    }
  }
  

  public void close()
    throws IOException
  {
    InetDataStorage.makeCommand(4, 0).send(out);
  }
  
  public Socket getSocket() {
    return sock;
  }
  


  private byte[] byteBuf = new byte[8];
}
