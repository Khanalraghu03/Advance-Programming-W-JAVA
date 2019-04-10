package Jeli.RemoteLog;

import java.applet.Applet;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;

public class IDSHandle extends InetDataStorage
{
  private String handle;
  private String basedir;
  private String serverinfo;
  private InetAddress host;
  private int port;
  
  public IDSHandle(String id, Applet applet, int port) throws IOException, IDSException
  {
    this(id, InetAddress.getByName(applet.getCodeBase().getHost()), port);
  }
  

  public IDSHandle(String id, String hostname, int port)
    throws IOException, java.net.UnknownHostException, IDSException
  {
    this(id, InetAddress.getByName(hostname), port);
  }
  

  public IDSHandle(String id, InetAddress host, int port)
    throws IOException, IDSException
  {
    this.host = host;
    this.port = port;
    


    Socket sock = new Socket(this.host, port);
    if (!getBase(id, sock))
      throw new IDSException("cannot create handle");
    sock = new Socket(this.host, port);
    if (!getInfo(id, sock))
      throw new IDSException("cannot create handle");
    sock = new Socket(this.host, port);
    if (!createHandle(id, sock)) {
      throw new IDSException("cannot get base");
    }
  }
  
  public String getDirectory() { return handle; }
  
  public String getHTMLBase() { return basedir; }
  
  public String getServerInfo() { return serverinfo; }
  









  public DataOutputStream open(String fname)
  {
    String name = handle + fname;
    

    byte[] dataBuf = name.getBytes();
    InetDataOutputStream ostream = null;
    



    try
    {
      Socket sock = new Socket(host, port);
      IDSCommand idsCmd = makeCommand(2, dataBuf.length);
      


      OutputStream net_ostream = sock.getOutputStream();
      idsCmd.send(net_ostream);
      net_ostream.write(dataBuf);
      


      InputStream net_istream = sock.getInputStream();
      idsCmd.receive(net_istream);
      



      if (verifyResult(idsCmd))
      {
        ostream = new InetDataOutputStream(sock);
      }
      

    }
    catch (Exception e)
    {
      ostream = null;
    }
    

    return ostream;
  }
  

  public String toString()
  {
    return "[IDSHandle]: " + handle;
  }
  








  protected boolean createHandle(String id, Socket sock)
  {
    byte[] dataBytes = id.getBytes();
    IDSCommand idsCmd = makeCommand(1, dataBytes.length);
    
    boolean result = true;
    
    try
    {
      OutputStream ostream = sock.getOutputStream();
      idsCmd.send(ostream);
      ostream.write(dataBytes);
      

      InputStream istream = sock.getInputStream();
      idsCmd.receive(istream);
      



      if ((result = verifyResult(idsCmd)))
      {
        byte[] hBytes = new byte[idsCmd.dataSize()];
        istream.read(hBytes);
        handle = new String(hBytes);
      }
      
    }
    catch (Exception e)
    {
      result = false;
    }
    

    return result;
  }
  


  protected boolean getBase(String id, Socket sock)
  {
    byte[] dataBytes = id.getBytes();
    IDSCommand idsCmd = makeCommand(5, dataBytes.length);
    
    boolean result = true;
    
    try
    {
      OutputStream ostream = sock.getOutputStream();
      idsCmd.send(ostream);
      ostream.write(dataBytes);
      

      InputStream istream = sock.getInputStream();
      idsCmd.receive(istream);
      



      if ((result = verifyResult(idsCmd)))
      {
        byte[] hBytes = new byte[idsCmd.dataSize()];
        istream.read(hBytes);
        basedir = new String(hBytes);
      }
      
    }
    catch (Exception e)
    {
      result = false;
    }
    

    return result;
  }
  


  protected boolean getInfo(String id, Socket sock)
  {
    byte[] dataBytes = id.getBytes();
    IDSCommand idsCmd = makeCommand(6, dataBytes.length);
    
    boolean result = true;
    
    try
    {
      OutputStream ostream = sock.getOutputStream();
      idsCmd.send(ostream);
      ostream.write(dataBytes);
      

      InputStream istream = sock.getInputStream();
      idsCmd.receive(istream);
      



      if ((result = verifyResult(idsCmd)))
      {
        byte[] hBytes = new byte[idsCmd.dataSize()];
        istream.read(hBytes);
        serverinfo = new String(hBytes);
      }
      
    }
    catch (Exception e)
    {
      result = false;
    }
    

    return result;
  }
}
