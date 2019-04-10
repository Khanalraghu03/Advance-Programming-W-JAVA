package Jeli.Widgets;

import java.awt.Container;

public class JeliGridLayoutSimple extends java.awt.GridLayout
{
  public JeliGridLayoutSimple(int rows, int cols)
  {
    super(rows, cols);
  }
  

  public void layoutContainer(Container parent)
  {
    synchronized (parent.getTreeLock()) {
      java.awt.Insets insets = parent.getInsets();
      int ncomponents = parent.getComponentCount();
      int nrows = getRows();
      int ncols = getColumns();
      boolean ltr = parent.getComponentOrientation().isLeftToRight();
      

      if (ncomponents == 0) {
        return;
      }
      if (nrows > 0) {
        ncols = (ncomponents + nrows - 1) / nrows;
      } else {
        nrows = (ncomponents + ncols - 1) / ncols;
      }
      int w = parent.getWidth() - (left + right);
      int h = parent.getHeight() - (top + bottom);
      int hgap = getHgap();
      int vgap = getVgap();
      
      int extraw = w - (ncols - 1) * hgap;
      w = (w - (ncols - 1) * hgap) / ncols;
      h = (h - (nrows - 1) * vgap) / nrows;
      extraw -= w * ncols;
      
      int thisExtraW = 0;
      int thisExtraX = 0;
      
      if (ltr)
      {
        int c = 0; for (int x = left; c < ncols; x += w + hgap) {
          int r = 0; for (int y = top; r < nrows; y += h + vgap) {
            if (c == ncols - extraw) {
              thisExtraW = 1;
            } else if (c > ncols - extraw)
              thisExtraX++;
            int i = r * ncols + c;
            
            if (i < ncomponents) {
              parent.getComponent(i).setBounds(x + thisExtraX, y, w + thisExtraW, h);
            }
            r++;
          }
          c++;



        }
        



      }
      else
      {



        int c = 0; for (int x = parent.getWidth() - right - w; c < ncols; x -= w + hgap) {
          int r = 0; for (int y = top; r < nrows; y += h + vgap) {
            if (c > 0) {
              thisExtraW = 0;
            } else
              thisExtraW = extraw;
            int i = r * ncols + c;
            if (i < ncomponents) {
              parent.getComponent(i).setBounds(x - thisExtraW, y, w + thisExtraW, h);
            }
            r++;
          }
          c++;
        }
      }
    }
  }
}
