package Jeli.Get;

import Jeli.Widgets.SliderInteger;
import java.awt.Color;
import java.awt.Panel;

public class GetColorFrame extends Jeli.Widgets.JeliFrame implements Jeli.Widgets.SliderCallBack
{
  private int redID = 1;
  private int greenID = 2;
  private int blueID = 3;
  private Color oldColor;
  private Jeli.Widgets.JeliLabel oldColorLabel;
  private Jeli.Widgets.JeliLabel newColorLabel;
  private Jeli.Widgets.JeliButton resetButton;
  private Jeli.Widgets.JeliButton doneButton;
  private Jeli.Widgets.JeliButton abortButton;
  private SliderInteger redSlider;
  private SliderInteger greenSlider;
  private SliderInteger blueSlider;
  private int red;
  private int green;
  private int blue;
  private GetColorFrameCallBack cb;
  private int id;
  
  public GetColorFrame(int id, String title, Color c, GetColorFrameCallBack cb) {
    super(title);
    this.cb = cb;
    this.id = id;
    
    oldColor = c;
    red = oldColor.getRed();
    green = oldColor.getGreen();
    blue = oldColor.getBlue();
    setLayout(new Jeli.Widgets.JeliGridLayout(4, 1));
    Panel buttonPanel = new Panel();
    buttonPanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 5));
    buttonPanel.add(this.oldColorLabel = new Jeli.Widgets.JeliLabel("old"));
    buttonPanel.add(this.newColorLabel = new Jeli.Widgets.JeliLabel("new"));
    buttonPanel.add(this.resetButton = new Jeli.Widgets.JeliButton("Reset", this));
    buttonPanel.add(this.abortButton = new Jeli.Widgets.JeliButton("Abort", this));
    buttonPanel.add(this.doneButton = new Jeli.Widgets.JeliButton("Done", this));
    oldColorLabel.setBackground(c);
    add(buttonPanel);
    redSlider = new SliderInteger(redID, red, 0, 255, 400, "Red: ", -1, true, this, Jeli.Utility.hm);
    
    redSlider.setBackground(Color.white);
    redSlider.setForeground(Color.black);
    redSlider.setSliderBackground(Color.red);
    
    add(redSlider);
    greenSlider = new SliderInteger(greenID, green, 0, 255, 400, "Green: ", -1, true, this, Jeli.Utility.hm);
    
    greenSlider.setBackground(Color.white);
    greenSlider.setForeground(Color.black);
    greenSlider.setSliderBackground(Color.green);
    
    add(greenSlider);
    blueSlider = new SliderInteger(blueID, blue, 0, 255, 400, "Blue: ", -1, true, this, Jeli.Utility.hm);
    
    blueSlider.setBackground(Color.white);
    blueSlider.setForeground(Color.black);
    blueSlider.setSliderBackground(Color.blue);
    
    add(blueSlider);
    pack();
    setVisible(true);
  }
  
  public void sliderchanged(int id, double val)
  {
    if (id == redID)
      red = ((int)val);
    if (id == greenID)
      green = ((int)val);
    if (id == blueID)
      blue = ((int)val);
    newColorLabel.setBackground(new Color(red, green, blue));
  }
  
  public void sliderchanged(int id) {}
  
  public void jeliButtonPushed(Jeli.Widgets.JeliButton bh) {
    if (bh == resetButton) {
      red = oldColor.getRed();
      green = oldColor.getGreen();
      blue = oldColor.getBlue();
      redSlider.set_value(red);
      greenSlider.set_value(green);
      blueSlider.set_value(blue);
      newColorLabel.setBackground(new Color(red, green, blue));
    }
    if (bh == abortButton) {
      setVisible(false);
    }
    if (bh == doneButton) {
      setVisible(false);
      if (cb != null) {
        cb.gotColorFrameColor(id, new Color(red, green, blue));
      }
    }
  }
}
