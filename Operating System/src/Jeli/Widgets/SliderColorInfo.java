package Jeli.Widgets;

public class SliderColorInfo
{
  public int id;
  public int valRed;
  public int valGreen;
  public int valBlue;
  public int valSavedRed;
  public int valSavedGreen;
  public int valSavedBlue;
  public String name;
  
  public SliderColorInfo(int id, String name, int r, int g, int b) {
    this.id = id;
    this.name = name;
    valRed = r;
    valGreen = g;
    valBlue = b;
    valSavedRed = r;
    valSavedGreen = g;
    valSavedBlue = b;
  }
}
