package Jeli.Widgets;


public class JeliButtonHandler
  implements JeliButtonCallBack
{
  private SimpleJeliButtonCallBack sjbcb;
  
  public JeliButtonHandler(SimpleJeliButtonCallBack sjbcb)
  {
    this.sjbcb = sjbcb;
  }
  

  public void jeliButtonPushed(JeliButton bh) { sjbcb.jeliButtonPushed(bh); }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  public String getClassification() { return "Jeli Button Handler"; }
  
  public void setClassification(String str) {}
}
