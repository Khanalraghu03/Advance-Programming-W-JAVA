package Jeli.LocalFiles;

import java.util.Vector;

public class LocalFilesList implements LocalFiles
{
  private Vector lfs;
  
  public LocalFilesList() {
    lfs = new Vector();
  }
  
  public void add(String name, byte[] vals) {
    lfs.addElement(new LocalFile(name, vals));
  }
  
  public void add(LocalFile lf) {
    lfs.addElement(lf);
  }
  
  public int getSize() {
    return lfs.size();
  }
  
  public String getLocalFile(String name)
  {
    for (int i = 0; i < lfs.size(); i++) {
      LocalFile lf = (LocalFile)lfs.elementAt(i);
      if (name.equals(name))
        return new String(val);
    }
    return null;
  }
}
