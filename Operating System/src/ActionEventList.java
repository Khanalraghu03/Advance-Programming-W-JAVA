import Jeli.Logging.StandardLogger;
import java.util.Vector;

public class ActionEventList
{
  private Vector list;
  private long startTime;
  
  public ActionEventList()
  {
    list = new Vector();
    list.addElement(new AddressActionEvent(0, new java.util.Date()));
    
    startTime = System.currentTimeMillis();
  }
  
  public void resetTime() {
    startTime = System.currentTimeMillis();
  }
  
  public void addEvent(AddressActionEvent e) {
    list.addElement(e);
  }
  
  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    
    sb.append("Action Event List has size " + list.size() + "\n");
    for (int i = 0; i < list.size(); i++) {
      AddressActionEvent e = (AddressActionEvent)list.elementAt(i);
      String timediff = Jeli.Utility.n_places_right((e.getTime() - startTime) / 1000.0D, 3, 10);
      

      sb.append("  " + timediff + ": " + e.toString() + "\n");
    }
    return sb.toString();
  }
  
  public void clear() {
    list.removeAllElements();
  }
  
  public String logTableHeader(String title, int n) {
    String titleEntry = "";
    if (title != null) {
      titleEntry = "<tr><td colspan=3 align=center><b>" + title + "</b></td></tr>";
    }
    
    return "<table border=1>" + titleEntry;
  }
  


  public String logTableFooter()
  {
    return "</table>";
  }
  
  public void logAllEvents(StandardLogger logger, String title, String ft) {
    logger.logStringSimple(getLogString(title, ft));
  }
  
















  public String getLogString(String title, String ft)
  {
    StringBuffer sb = new StringBuffer();
    
    sb.append(logTableHeader(title, list.size()));
    for (int i = 0; i < list.size(); i++) {
      AddressActionEvent event = (AddressActionEvent)list.elementAt(i);
      sb.append("<tr><td align-right>" + (i + 1) + "</td><td align=right>" + Jeli.Utility.n_places((event.getTime() - startTime) / 1000.0D, 3) + "</td><td>" + event + "</td></tr>");
    }
    




    if (ft != null)
      sb.append("<tr><td colspan=3>" + ft + "</td></tr>");
    sb.append(logTableFooter());
    return sb.toString();
  }
}
