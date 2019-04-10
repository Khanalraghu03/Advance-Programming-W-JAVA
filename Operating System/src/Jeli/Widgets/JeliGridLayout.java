package Jeli.Widgets;

import java.awt.Container;


public class JeliGridLayout
  extends java.awt.GridLayout
{
  public JeliGridLayout(int rows, int cols) { super(rows, cols); }
  
  public void layoutContainer(Container parent) { java.awt.Insets insets;
    int ncomponents;
    int nrows;
    int ncols; int w; int h; int hgap; int vgap; int extraw; int c; int x; synchronized (parent.getTreeLock()) {
      insets = parent.getInsets();
      ncomponents = parent.getComponentCount();
      nrows = getRows();
      ncols = getColumns();
      boolean ltr = parent.getComponentOrientation().isLeftToRight();
      

      if (ncomponents == 0) {
        return;
      }
      if (nrows > 0) {
        ncols = (ncomponents + nrows - 1) / nrows;
      }
      else {
        nrows = (ncomponents + ncols - 1) / ncols;
      }
      w = parent.getWidth() - (left + right);
      h = parent.getHeight() - (top + bottom);
      hgap = getHgap();
      vgap = getVgap();
      

      extraw = w - (ncols - 1) * hgap;
      int extrah = h - (nrows - 1) * vgap;
      w = (w - (ncols - 1) * hgap) / ncols;
      h = (h - (nrows - 1) * vgap) / nrows;
      extraw -= w * ncols;
      extrah -= h * nrows;
      






      if (ltr)
      {
        int c = 0; for (int x = left; c < ncols; x += w + hgap) { int thisExtraW;
          int thisExtraW; if (c >= ncols - extraw) {
            thisExtraW = 1;
          } else
            thisExtraW = 0;
          int thisExtraX; int thisExtraX; if (c > ncols - extraw) {
            thisExtraX = c - ncols + extraw;
          } else
            thisExtraX = 0;
          int r = 0; for (int y = top; r < nrows; y += h + vgap) { int thisExtraH;
            int thisExtraH; if (r >= nrows - extrah) {
              thisExtraH = 1;
            } else
              thisExtraH = 0;
            int thisExtraY; int thisExtraY; if (r > nrows - extrah) {
              thisExtraY = r - nrows + extrah;
            } else {
              thisExtraY = 0;
            }
            int i = r * ncols + c;
            
            if (i < ncomponents) {
              parent.getComponent(i).setBounds(x + thisExtraX, y + thisExtraY, w + thisExtraW, h + thisExtraH);
            }
            r++;
          }
          c++;








        }
        








      }
      else
      {








        c = 0; for (x = parent.getWidth() - right - w; c < ncols;)
        {
          int r = 0; for (int y = top; r < nrows; y += h + vgap) { int thisExtraW;
            int thisExtraW; if (c > 0) {
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
          x -= w + hgap;
        }
      }
    }
  }
}
