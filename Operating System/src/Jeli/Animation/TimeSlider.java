package Jeli.Animation;

import Jeli.Widgets.HelpManager;

public class TimeSlider extends Jeli.Widgets.SliderFloat implements Jeli.Widgets.SliderCallBack
{
  VirtualTime vt;
  
  public TimeSlider(VirtualTime vt, int type)
  {
    super(0, 1000, 1, 2000, 1000, 10, "Speed: ", type, true, null, Jeli.Utility.hm);
    setCallBack(this);
    this.vt = vt;
    setBackground(Jeli.Utility.hm.getDefaultBackground());
  }
  
  public TimeSlider(VirtualTime vt, HelpManager hm) {
    super(0, 1000, 1, 2000, 1000, 10, "Speed: ", 0, true, null, hm);
    setCallBack(this);
    this.vt = vt;
    setBackground(hm.getDefaultBackground());
  }
  
  public void sliderchanged(int id, double val) {
    sliderchanged(id);
  }
  
  public void sliderchanged(int id) {
    vt.setRate(valueDouble());
  }
}
