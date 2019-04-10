import Jeli.Widgets.JeliButton;


public class AddressActionEvent
{
  public static final int EVENT_INITIAL = 0;
  public static final int EVENT_BUTTON = 1;
  public static final int EVENT_LOGICAL_SEGMENT = 2;
  public static final int EVENT_LOGICAL_SELECTP = 3;
  public static final int EVENT_LOGICAL_SELECTO = 4;
  public static final int EVENT_LOGICAL_SELECTP1 = 5;
  public static final int EVENT_LOGICAL_SELECTP2 = 6;
  public static final int EVENT_LOGICAL_SELECTPF = 7;
  public static final int EVENT_PHYSICAL_SEGMENT = 8;
  public static final int EVENT_PHYSICAL_PASTEA = 9;
  public static final int EVENT_PHYSICAL_PASTEF = 10;
  public static final int EVENT_PHYSICAL_PASTEO = 11;
  public static final int EVENT_TLB = 12;
  public static final int EVENT_PROGRESS = 13;
  public static final int EVENT_COMPLETION = 14;
  public static final int EVENT_LIFELINE = 15;
  public static final int EVENT_PERFORM_STEP = 16;
  public static final String[] nameList = { "Unknown", "Button Pushed" };
  
  private int type;
  
  private Object target;
  private long time;
  
  public AddressActionEvent(int type, Object target)
  {
    this.type = type;
    this.target = target;
    time = System.currentTimeMillis();
  }
  
  public long getTime() {
    return time;
  }
  
  public String toString() {
    switch (type) {
    case 0: 
      return "Initial Event on " + target;
    case 1: 
      return "Button Pushed: " + ((JeliButton)target).getLabel();
    case 2: 
      return "Logical Address Segmented: " + target;
    case 3: 
      return "Logical Address Page Selected: " + target;
    case 4: 
      return "Logical Address Frame Selected: " + target;
    case 5: 
      return "Logical Address Level 1 Page Selected: " + target;
    case 6: 
      return "Logical Address Level 2 Page Selected: " + target;
    case 7: 
      return "Logical Address Full Page Selected: " + target;
    case 8: 
      return "Physical Address Segmented: " + target;
    case 9: 
      return "Physical Address Paste All: " + target;
    case 10: 
      return "Physical Address Paste Frame: " + target;
    case 11: 
      return "Physical Address Paste Offset: " + target;
    case 12: 
      return target.toString();
    case 13: 
      return "Progess Tracker Event: " + target;
    case 14: 
      return "Attempt Completion: " + target;
    case 15: 
      return "Lifeline Requested: " + target;
    case 16: 
      return "Automatic Step Performed: " + target;
    }
    return "Unknown Event";
  }
}
