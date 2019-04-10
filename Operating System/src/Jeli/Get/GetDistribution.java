package Jeli.Get;

import Jeli.Probability.Distribution;
import Jeli.Widgets.JeliButton;
import Jeli.Widgets.JeliFrame;
import java.awt.Component;
import java.awt.Panel;

public class GetDistribution extends JeliFrame
{
  DistributionCallBack callback;
  String ConstantDistributionString = "Constant Distribution              ";
  int DistributionType;
  JeliButton DoneButton;
  JeliButton CancelButton;
  JeliButton DistributionButton;
  JeliButton LowButton;
  JeliButton HighButton;
  JeliButton MeanButton;
  double mean;
  double low;
  double high;
  int id;
  Component com;
  Jeli.Widgets.HelpManager hm;
  Panel DistributionPanel;
  Panel ValuesPanel;
  boolean LowFlag;
  boolean HighFlag;
  boolean MeanFlag;
  private String classification = null;
  
  public GetDistribution(String name, int id, Jeli.Widgets.HelpManager hm, DistributionCallBack callback, Component com)
  {
    super(name);
    this.callback = callback;
    this.id = id;
    this.com = com;
    this.hm = hm;
    setup_layout();
    pack();
  }
  
  public void showGetDistribution()
  {
    if (com != null) {
      java.awt.Point pos = Jeli.Utility.getAbsolutePosition(com);
      setLocation(x - getBoundswidth / 2, y - getBoundsheight / 2);
    }
    super.setVisible(true);
  }
  
  public void setup_layout()
  {
    setLayout(new Jeli.Widgets.JeliGridLayout(3, 1));
    DistributionPanel = new Panel();
    ValuesPanel = new Panel();
    Panel DonePanel = new Panel();
    add(DistributionPanel);
    add(ValuesPanel);
    DonePanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
    DoneButton = new JeliButton("Done                           ", hm, this);
    CancelButton = new JeliButton("Cancel                       ", hm, this);
    DonePanel.add(CancelButton);
    DonePanel.add(DoneButton);
    add(DonePanel);
    DistributionPanel.setLayout(new java.awt.GridLayout(1, 1));
    DistributionButton = new JeliButton("???", hm, this);
    DistributionPanel.add(DistributionButton);
    DistributionType = 1;
    MeanButton = new JeliButton("Mean", hm, this);
    MeanButton.setAsGetDouble("Set Mean", "Enter Mean: ", "Mean: ", 0.0D, false);
    LowButton = new JeliButton("Low", hm, this);
    LowButton.setAsGetDouble("Set Low", "Enter Low: ", "Low: ", 0.0D, false);
    HighButton = new JeliButton("High", hm, this);
    HighButton.setAsGetDouble("Set High", "Enter High: ", "High: ", 0.0D, false);
    set_distribution_button();
    DistributionType = 1;
  }
  
  public void set_distribution_button() {
    if (DistributionType == 1) {
      DistributionButton.setLabel(ConstantDistributionString);
      ValuesPanel.removeAll();
      ValuesPanel.setLayout(new java.awt.GridLayout(1, 1));
      MeanButton.setAsGetDouble(false);
      

      ValuesPanel.add(MeanButton);
      ValuesPanel.invalidate();
      MeanFlag = false;
      validate();
    }
    else if (DistributionType == 3) {
      DistributionButton.setLabel("Uniform Distribution");
      ValuesPanel.removeAll();
      ValuesPanel.setLayout(new Jeli.Widgets.JeliGridLayout(1, 2));
      LowButton.setAsGetDouble(false);
      HighButton.setAsGetDouble(false);
      

      ValuesPanel.add(LowButton);
      ValuesPanel.add(HighButton);
      ValuesPanel.invalidate();
      LowFlag = false;
      HighFlag = false;
      validate();
    }
    else if (DistributionType == 5) {
      DistributionButton.setLabel("Exponential Distribution");
      ValuesPanel.removeAll();
      ValuesPanel.setLayout(new java.awt.GridLayout(1, 1));
      MeanButton.setAsGetDouble(false);
      
      ValuesPanel.add(MeanButton);
      ValuesPanel.invalidate();
      MeanFlag = false;
      validate();
    }
    else {
      DistributionButton.setLabel("Unknown Distribution"); }
    DoneButton.disableJeliButton();
  }
  
  public void jeliButtonPushed(JeliButton bh)
  {
    if (bh == DistributionButton) {
      if (DistributionType == 1) {
        DistributionType = 3;
      } else if (DistributionType == 3) {
        DistributionType = 5;
      } else if (DistributionType == 5)
        DistributionType = 1;
      set_distribution_button();
    }
    

















    if (bh == CancelButton) {
      setVisible(false);
    }
    if (bh == DoneButton) { Distribution dist;
      Distribution dist; if (DistributionType == 3) {
        dist = new Distribution(DistributionType, low, high);
      } else
        dist = new Distribution(DistributionType, mean);
      callback.setDistribution(id, dist);
      setVisible(false);
    }
  }
  
  public void jeliButtonGotString(JeliButton bh, String str) {}
  
  public void jeliButtonGotInteger(JeliButton bh, int val) {}
  
  public void jeliButtonGotDouble(JeliButton bh, double val) { if (bh == MeanButton)
    {






      mean = val;
      MeanFlag = true;
      
      DoneButton.enableJeliButton();
      return;
    }
    if (bh == LowButton)
    {






      low = val;
      LowFlag = true;
      
      if (HighFlag)
        DoneButton.enableJeliButton();
      return;
    }
    if (bh == HighButton)
    {






      high = val;
      HighFlag = true;
      
      if (LowFlag)
        DoneButton.enableJeliButton();
      return;
    }
  }
  
  public String getClassification() {
    return classification;
  }
  
  public void setClassification(String str) {
    classification = str;
  }
}
