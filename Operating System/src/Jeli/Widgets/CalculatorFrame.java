package Jeli.Widgets;

import java.awt.Panel;

public class CalculatorFrame extends JeliFrame
{
  private CalculatorPanel cp;
  private Panel buttonPanel;
  private JeliButton hideButton;
  private JeliButton helpButton;
  private JeliButton sizeButton;
  private SelfDestructingShortInfoFrame helpFrame = null;
  private String helpName = "Calculator Help";
  private int numSec = 20;
  private int size;
  private String helpMsg = "This calculator supports the following operations:\n   add subtract multiply divide shift\nCalculations can be done in base 2, 10 or 16.\nChange the base by pushing the Base button.\nAdd subtract, multiply and divide take operands from the x and y\n   values and put the result in the z value.\n   Use the four buttons above the Base button.\nEach of the x, y and z values can be shifted left or right by using\n   the shift buttons (<< and >>) to the left of each value.\nThe shifting operations work on binary values independent of the base.\nAny of the values can be put in the Clipboard by pushing the\n   corresponding Select button\n\nAny of the values can be loaded from the Clipboard by pushing the\n   corresponding Paste button\nYou can also type a value directly in one of the x, y, or z fields\n   by clicking inside the field.  Push the Return key when done.\nThe Size field at the bottom is the maximum number of digitsthat can be displayed.\n   You can change the value by clicking on the Size field.\n   The calculator supports a maximum of 64 bits.\n";
  



















  public CalculatorFrame(String name, int wid, ClipBoards clipboards)
  {
    super(name);
    cp = new CalculatorPanel(10, true);
    cp.setName("FromFrame");
    cp.setClipBoard(clipboards);
    clipboards.addClipBoard(cp);
    cp.reset(wid);
    size = wid;
    setupLayout();
    setInhibitVisibleComponent(true);
  }
  
  public void resetClipboard(ClipBoards clipboards) {
    cp.setClipBoard(clipboards);
    clipboards.addClipBoard(cp);
  }
  
  public CalculatorPanel getPanel() {
    return cp;
  }
  
  private void setupLayout() {
    setLayout(new java.awt.BorderLayout());
    setBackground(java.awt.Color.white);
    buttonPanel = new Panel();
    buttonPanel.setLayout(new JeliGridLayout(1, 3));
    hideButton = new JeliButton("Hide", this);
    hideButton.setPositionCenter();
    helpButton = new JeliButton("Help", this);
    helpButton.setPositionCenter();
    sizeButton = new JeliButton("size", this);
    sizeButton.setAsGetInteger("Size: ", "Enter Size: ", "Size: ", size, true);
    sizeButton.setGetIntegerRange(1, 64);
    buttonPanel.add(hideButton);
    buttonPanel.add(sizeButton);
    buttonPanel.add(helpButton);
    add("South", buttonPanel);
    add("Center", cp);
    pack();
    setResizable(false);
  }
  
  public void reset(int n) {
    size = n;
    cp.reset(n);
    removeAll();
    buttonPanel.removeAll();
    hideButton.setButtonSize("Hide");
    helpButton.setButtonSize("Help");
    sizeButton.setButtonSize("Size: xxx");
    buttonPanel.add(hideButton);
    buttonPanel.add(sizeButton);
    buttonPanel.add(helpButton);
    add("South", buttonPanel);
    add("Center", cp);
    sizeButton.setValue(size);
    buttonPanel.invalidate();
    invalidate();
    validate();
    pack();
  }
  
  public void setHelpBackground(java.awt.Color c) {
    helpButton.setBackground(c);
  }
  
  public void setHideBackground(java.awt.Color c) {
    hideButton.setBackground(c);
  }
  
  public void jeliButtonPushed(JeliButton bh) {
    if (bh == hideButton) {
      setVisible(false);
    } else if ((bh == helpButton) && 
      (helpFrame == null)) {
      helpFrame = new SelfDestructingShortInfoFrame(helpName, helpMsg, numSec, helpButton);
    }
    
    helpFrame.setVisible(true);
    helpFrame.restart();
  }
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {
    if (bh == sizeButton) {
      if (size == val)
        return;
      reset(val);
    }
  }
}
