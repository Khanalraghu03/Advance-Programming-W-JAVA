import Jeli.Widgets.JeliButton;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class ProgressPanel extends java.awt.Panel implements Jeli.Widgets.ButtonPainter
{
  private JeliButton checkbox;
  private JeliButton description;
  private JeliButton descNumber = null;
  
  private char check = 'ઙ';
  private boolean checkedFlag = false;
  
  private boolean disableCheck = false;
  

  public ProgressPanel(String desc, String num, String numLen)
  {
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    setLayout(gbl);
    weightx = 0.0D;
    weighty = 0.0D;
    fill = 0;
    gridx = 0;
    gridy = 0;
    checkbox = new JeliButton(" ");
    checkbox.setInhibitReverse(true);
    gbl.setConstraints(checkbox, c);
    add(checkbox);
    gridx += 1;
    if (num != null) {
      descNumber = new JeliButton(num);
      descNumber.setInhibitReverse(true);
      descNumber.setPositionRight();
      descNumber.setButtonSize(numLen);
      descNumber.setBorderType(3);
      gbl.setConstraints(descNumber, c);
      add(descNumber);
    }
    
    gridx += 1;
    weightx = 1.0D;
    fill = 2;
    description = new JeliButton(desc);
    description.setInhibitReverse(true);
    description.setBorderType(11);
    gbl.setConstraints(description, c);
    add(description);
    checkbox.setLabel("");
    checkbox.setButtonPainter(this);
    checkbox.setBorderType(7);
  }
  
  public void setCallBack(Jeli.Widgets.JeliButtonCallBack cb) {
    checkbox.resetJeliButtonCallBack(cb);
    description.resetJeliButtonCallBack(cb);
    if (descNumber != null) {
      descNumber.resetJeliButtonCallBack(cb);
    }
  }
  
  public boolean testButton(JeliButton bh) {
    if (bh == null) {
      return false;
    }
    if ((bh == checkbox) || (bh == description) || (bh == descNumber)) {
      return true;
    }
    return false;
  }
  
  public void disableDescription() {
    description.disableJeliButton();
    checkbox.refresh();
  }
  
  public void disableCheck() {
    disableCheck = true;
    checkbox.refresh();
  }
  
  public boolean getDisabled() {
    return disableCheck;
  }
  
  public void setDescription(String s) {
    description.setLabel(s);
  }
  
  public String getDescription() {
    return description.getLabel();
  }
  
  public void refresh() {
    checkbox.refresh();
  }
  
  public void setChecked() {
    checkedFlag = true;
    checkbox.refresh();
  }
  
  public boolean getChecked() {
    return checkedFlag;
  }
  
  public void setUnchecked() {
    checkedFlag = false;
    checkbox.refresh();
  }
  




  public void paintButton(java.awt.Component can, Graphics gc)
  {
    if ((!checkedFlag) && (!disableCheck)) {
      return;
    }
    
    int bHeight = getBoundsheight;
    int bWidth = getBoundswidth;
    int minDim = bHeight;
    if (bWidth < bHeight) {
      minDim = bWidth;
    }
    int size = minDim - 4;
    
    java.awt.Color c = gc.getColor();
    if (disableCheck) {
      gc.setColor(java.awt.Color.gray);
    }
    else {
      gc.setColor(java.awt.Color.cyan);
    }
    gc.fillOval((bWidth - size) / 2, (bHeight - size) / 2, size, size);
    gc.setColor(c);
  }
}
