package Jeli.Widgets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

public class HorizontalPanels
  extends JeliPanel
{
  public HorizontalPanels(JeliPanel[] p, int dist)
  {
    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints c = new GridBagConstraints();
    setLayout(gbl);
    weightx = 0.0D;
    weighty = 0.0D;
    fill = 0;
    gridx = 0;
    gridy = 0;
    for (int i = 0; i < p.length; i++) {
      gbl.setConstraints(p[i], c);
      add(p[i]);
      gridx += 1;
      if (dist > 0) {
        SizedPanel p1 = new SizedPanel(0);
        p1.setWidthOnly(dist);
        gbl.setConstraints(p1, c);
        add(p1);
        gridx += 1;
      }
    }
  }
}
