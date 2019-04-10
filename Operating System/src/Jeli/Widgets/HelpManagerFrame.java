package Jeli.Widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.Rectangle;

class HelpManagerFrame extends JeliFrame
{
  private JeliButton hideButton;
  public JeliButton debugButton;
  private Panel buttonPanel;
  private HelpManager hm;
  private Panel canvasPanel;
  private HelpManagerCanvas hmc;
  
  public HelpManagerFrame(String title, HelpManagerCanvas hmc, HelpManager hm)
  {
    super(title);
    this.hm = hm;
    this.hmc = hmc;
    setLayout(new BorderLayout());
    canvasPanel = new Panel();
    canvasPanel.setLayout(new GridLayout(1, 1));
    canvasPanel.add(hmc);
    buttonPanel = new Panel();
    buttonPanel.setLayout(new GridLayout(1, 1));
    buttonPanel.add(this.hideButton = new JeliButton("Hide", hm, this));
    add("South", buttonPanel);
    hideButton.setButtonSize();
    debugButton = new JeliButton("Button Debug", hm, this);
    debugButton.setButtonSize();
    add("Center", canvasPanel);
    pack();
  }
  
  protected int getButtonHeight(int n)
  {
    Insets insets = getInsets();
    return buttonPanel.getPreferredSize().height + hmc.getHeight(n) + top + bottom;
  }
  


  public boolean checkSizes()
  {
    Rectangle dim1 = canvasPanel.getBounds();
    Dimension dim2 = hmc.getPreferredSize();
    if (width != width)
      return true;
    if (height != height)
      return true;
    return false;
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == hideButton) {
      hm.disableHelpManager();
    }
    else if (bh == debugButton)
      hm.setDebugMode();
  }
  
  public String getClassification() {
    return "Help Manager Info";
  }
  
  public void setupDebug() {
    buttonPanel.removeAll();
    buttonPanel.setLayout(new JeliGridLayout(1, 2));
    buttonPanel.add(hideButton);
    buttonPanel.add(debugButton);
    buttonPanel.invalidate();
    buttonPanel.validate();
  }
  
  public void setDebugButtonLabel(boolean f) {
    if (f) {
      debugButton.setLabel("Debug: On");
    } else
      debugButton.setLabel("Debug: Off");
  }
  
  public void fixSize() {
    canvasPanel.invalidate();
    pack();
  }
}
