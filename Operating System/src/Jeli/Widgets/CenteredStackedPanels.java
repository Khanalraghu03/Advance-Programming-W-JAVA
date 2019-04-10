package Jeli.Widgets;

import java.awt.GridBagLayout;

public class CenteredStackedPanels extends JeliPanel
{
  public CenteredStackedPanels(JeliPanel[] p)
  {
    this(p, 0, false);
  }
  
  public CenteredStackedPanels(JeliPanel[] p, int dist) {
    this(p, dist, false);
  }
  

  public CenteredStackedPanels(JeliPanel[] p, int dist, boolean fill)
  {
    GridBagLayout gbl = new GridBagLayout();
    java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
    setLayout(gbl);
    weightx = 0.0D;
    weighty = 0.0D;
    fill = 0;
    if (fill) {
      weightx = 1.0D;
      fill = 2;
    }
    gridx = 1;
    gridy = 0;
    for (int i = 0; i < p.length; i++) {
      gbl.setConstraints(p[i], c);
      add(p[i]);
      gridy += 1;
      if (dist > 0)
      {

        SizedPanel p1 = new SizedPanel(0);
        p1.setHeightOnly(dist);
        gbl.setConstraints(p1, c);
        
        add(p1);
        gridy += 1;
      }
    }
  }
}
