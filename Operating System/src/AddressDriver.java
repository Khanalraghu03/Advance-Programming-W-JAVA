import Jeli.JeliApplicationVersion;
import Jeli.JeliDriver;
import Jeli.Utility;
import Jeli.Widgets.JeliFrame;

public class AddressDriver
  extends JeliDriver
{
  public AddressDriver() {}
  
  private String backURLString = null;
  private String configString = null;
  private int remotePort = 0;
  
  public static void main(String[] args) {
    JeliFrame initf = null;
    JeliApplicationVersion version = new AddressVersion();
    args = Utility.setupApplication(version, args);
    
    if ((args == null) || (args.length < 1)) {
      initf = new AddressMenu(null, 0);
    } else {
      initf = new AddressMenu(args[0], 0);
    }
    initf.setVisible(true);
  }
  
  public void init() {
    initMost(new AddressVersion());
    backURLString = getParameter("BACKURL");
    configString = getParameter("CONFIG");
    String remotePortString = getParameter("REMOTEPORT");
    if (remotePortString != null) {
      remotePort = Integer.parseInt(remotePortString);
    }
  }
  
  public void run() {
    JeliFrame initf = new AddressMenu(configString, remotePort, backURLString);
    setInitialFrame(initf);
    initf.setVisible(true);
  }
}
