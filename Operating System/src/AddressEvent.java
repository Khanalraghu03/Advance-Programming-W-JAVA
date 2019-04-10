import java.io.PrintStream;

public class AddressEvent { public int position;
  public int type;
  public static int ADDRESS_EVENT_TYPE_UNKNOWN = -1;
  public static int ADDRESS_EVENT_TLB_SEGMENTED = 0;
  public static int ADDRESS_EVENT_TLB_UNSEGMENTED = 1;
  
  public AddressEvent() {
    position = -1;
    type = ADDRESS_EVENT_TYPE_UNKNOWN;
  }
  
  public static AddressEvent createEventTLBSegmented(int pos)
  {
    AddressEvent ev = new AddressEvent();
    type = ADDRESS_EVENT_TLB_SEGMENTED;
    position = pos;
    return ev;
  }
  
  public static AddressEvent createEventTLBSegmentRemoved()
  {
    AddressEvent ev = new AddressEvent();
    type = ADDRESS_EVENT_TLB_UNSEGMENTED;
    return ev;
  }
  
  public boolean equals(AddressEvent ev) {
    System.out.println("Event " + getString() + " comparing to " + ev.getString());
    if (type != type) {
      return false;
    }
    if (type == ADDRESS_EVENT_TYPE_UNKNOWN) {
      return false;
    }
    if (type == ADDRESS_EVENT_TLB_SEGMENTED) {
      if (position == position) {
        return true;
      }
      
      return false;
    }
    
    if (type == ADDRESS_EVENT_TLB_UNSEGMENTED) {
      return true;
    }
    return false;
  }
  
  public String getString() {
    if (type == ADDRESS_EVENT_TYPE_UNKNOWN) {
      return "unknown";
    }
    if (type == ADDRESS_EVENT_TLB_SEGMENTED) {
      return "segmented at " + position;
    }
    if (type == ADDRESS_EVENT_TLB_UNSEGMENTED) {
      return "unsegmented";
    }
    return "unknown event";
  }
}
