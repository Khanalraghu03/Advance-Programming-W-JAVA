package Jeli.RemoteLog;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;




public class IDSCommand
{
  private byte id;
  private byte code;
  private int size;
  
  public IDSCommand() {}
  
  public IDSCommand(int id, int code, int dataLen)
  {
    this.id = ((byte)id);
    this.code = ((byte)code);
    size = dataLen;
  }
  



  public IDSCommand(Socket sock)
    throws IOException, IDSException
  {
    receive(new DataInputStream(sock.getInputStream()));
  }
  




  public IDSCommand(InputStream istream)
    throws IOException, IDSException
  {
    receive(new DataInputStream(istream));
  }
  




  public IDSCommand(DataInputStream istream)
    throws IOException, IDSException
  {
    receive(istream);
  }
  

  public byte id()
  {
    return id;
  }
  
  public void id(byte idVal) {
    id = idVal;
  }
  
  public byte code() {
    return code;
  }
  
  public void code(byte codeVal) {
    code = codeVal;
  }
  
  public int dataSize() {
    return size;
  }
  
  public void dataSize(int size) {
    this.size = size;
  }
  



  public void receive(InputStream istream)
    throws IOException, IDSException
  {
    receive(new DataInputStream(istream));
  }
  




  public void receive(DataInputStream istream)
    throws IOException, IDSException
  {
    id = istream.readByte();
    code = istream.readByte();
    size = istream.readInt();
  }
  



  public void send(OutputStream ostream)
    throws IOException
  {
    send(new DataOutputStream(ostream));
  }
  



  public void send(DataOutputStream ostream)
    throws IOException
  {
    ostream.writeByte(id);
    ostream.writeByte(code);
    ostream.writeInt(size);
  }
}
