package Jeli.Widgets;

import java.awt.Point;

public class StringAreaAdjust extends JeliFrame
{
  StringArea sa;
  boolean first_show_flag = true;
  private String classification;
  
  public StringAreaAdjust(String str, StringArea sa) {
    super(str);
    this.sa = sa;
    setLayout(new JeliGridLayout(6, 1));
    add(new JeliButton("Bigger Font", hm, this));
    add(new JeliButton("Smaller Font", hm, this));
    add(new JeliButton("More Lines", hm, this));
    add(new JeliButton("Fewer Lines", hm, this));
    add(new JeliButton("Format", hm, this));
    add(new JeliButton("Done", hm, this));
    classification = str;
    pack();
  }
  



  public void showit()
  {
    if (first_show_flag) {
      first_show_flag = false;
      Point pos = Jeli.Utility.getAbsolutePosition(sa);
      int x = x + sa.getBounds().width / 2;
      int y = y + sa.getBounds().height / 2;
      if (x < 0) x = 0;
      if (y < 0) y = 0;
      setLocation(x, y);
    }
    super.setVisible(true);
  }
  

  public void jeliButtonPushed(JeliButton bh)
  {
    String str = bh.getLabel();
    if ("Bigger Font".equals(str)) {
      sa.set_bigger_font();
    } else if ("Smaller Font".equals(str)) {
      sa.set_smaller_font();
    } else if ("More Lines".equals(str)) {
      sa.set_more_lines();
    } else if ("Fewer Lines".equals(str)) {
      sa.set_fewer_lines();
    } else if ("Format".equals(str)) {
      sa.format_lines();
    } else if ("Done".equals(str)) {
      sa.resize_frame();
      setVisible(false);
    } }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) {}
  
  public String getClassification() { return classification; }
  
  public void setClassification(String str)
  {
    classification = str;
  }
}
