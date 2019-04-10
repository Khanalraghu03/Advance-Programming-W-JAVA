package Jeli.Widgets;

import Jeli.Utility;

public class JeliLabel extends JeliButton
{
  public JeliLabel(String lab) {
    this(lab, Utility.hm);
  }
  
  public JeliLabel(String lab, HelpManager hm) {
    super(lab, hm, null);
    setAsLabel();
  }
}
