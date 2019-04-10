package Jeli.Logging;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Vector;
















































public class GIFEncoder
{
  short width_;
  short height_;
  int numColors_;
  byte[] pixels_;
  byte[] colors_;
  ScreenDescriptor sd_;
  ImageDescriptor id_;
  static boolean debugGif = false;
  
  public GIFEncoder(Image image) throws AWTException
  {
    width_ = ((short)image.getWidth(null));
    height_ = ((short)image.getHeight(null));
    
    int[] values = new int[width_ * height_];
    PixelGrabber grabber = new PixelGrabber(image, 0, 0, width_, height_, values, 0, width_);
    
    try
    {
      if (grabber.grabPixels() != true) {
        throw new AWTException("Grabber returned false: " + grabber.status());
      }
    } catch (InterruptedException e) {}
    fixTooManyColors(values);
    


    byte[][] r = new byte[width_][height_];
    byte[][] g = new byte[width_][height_];
    byte[][] b = new byte[width_][height_];
    int index = 0;
    
    pixels_ = new byte[width_ * height_];
    colors_ = new byte['̀'];
    int[] fastcolor = new int['Ā'];
    int colornum = 0;
    
    for (int y = 0; y < height_; y++) {
      for (int x = 0; x < width_; tmp347_346++) {
        int noalpha = values[index] & 0xFFFFFF;
        r[x][y] = ((byte)(noalpha >>> 16));
        g[x][y] = ((byte)(noalpha >>> 8));
        b[x][y] = ((byte)noalpha);
        
        for (int search = 0; search < colornum; search++)
          if (noalpha == fastcolor[search])
            break;
        if (search > 255) {
          throw new AWTException("Too many colors.");
        }
        pixels_[(y * width_ + x)] = ((byte)search);
        
        if (search == colornum) {
          fastcolor[search] = noalpha; int 
            tmp347_346 = (search * 3);search = tmp347_346;colors_[tmp347_346] = r[tmp347_346][y];
          colors_[(++search)] = g[tmp347_346][y];
          colors_[(++search)] = b[tmp347_346][y];
          colornum++;
        }
        index++;
      }
    }
    

    numColors_ = (1 << BitUtils.BitsNeeded(colornum));
    byte[] copy = new byte[numColors_ * 3];
    System.arraycopy(colors_, 0, copy, 0, numColors_ * 3);
    colors_ = copy;
  }
  




  public void writeOutput(OutputStream output)
    throws IOException
  {
    BitUtils.writeString(output, "GIF87a");
    
    ScreenDescriptor sd = new ScreenDescriptor(width_, height_, numColors_);
    sd.writeOutput(output);
    
    output.write(colors_, 0, colors_.length);
    
    ImageDescriptor id = new ImageDescriptor(width_, height_, ',');
    id.writeOutput(output);
    
    byte codesize = BitUtils.BitsNeeded(numColors_);
    if (codesize == 1)
      codesize = (byte)(codesize + 1);
    output.write(codesize);
    


    LZWCompressor.LZWCompress(output, codesize, pixels_);
    


    output.write(0);
    
    id = new ImageDescriptor((short)0, (short)0, ';');
    id.writeOutput(output);
    output.flush();
  }
  


  private void fixTooManyColors(int[] values)
  {
    int sizeMax = 250;
    int sizeCounts = 100;
    



    for (int i = 0; i < values.length; i++)
      values[i] &= 0xFFFFFF;
    Vector counts = new Vector();
    for (int i = 0; i < values.length; i++) {
      boolean found = false;
      for (int j = 0; j < counts.size(); j++) {
        if (values[i] == elementAtx) {
          elementAty += 1;
          found = true;
          break;
        }
      }
      if (!found)
        counts.addElement(new Point(values[i], 1));
    }
    if (debugGif)
      System.out.println("counts has size " + counts.size() + " compared to " + sizeMax);
    if (counts.size() <= sizeMax) return;
    sortPoints(counts);
    if (debugGif) {
      Point p = (Point)counts.elementAt(0);
      System.out.println("Max count is " + y + " for " + rgb(x));
    }
    int[] vals = new int[sizeMax];
    for (int i = 0; i < sizeCounts; i++)
      vals[i] = elementAtx;
    int valsSize = sizeCounts;
    if (debugGif) {
      System.out.println("Greatest distance is  " + getDist(counts, vals, valsSize));
    }
    while (valsSize < sizeMax) {
      addNextValue(counts, vals, valsSize);
      valsSize++;
    }
    if (debugGif) {
      System.out.println("Greatest distance now " + getDist(counts, vals, valsSize));
    }
    for (int i = 0; i < values.length; i++) {
      values[i] = bestFit(vals, values[i], valsSize);
    }
  }
  
  void addNextValue(Vector v1, int[] v, int vsize)
  {
    int ind = getDistInd(v1, v, vsize);
    int val = elementAtx;
    v[vsize] = val;
  }
  


  int getDistInd(Vector v1, int[] v, int vsize)
  {
    int dist = getDist(elementAt0x, v, vsize);
    int ind = 0;
    for (int i = 1; i < v1.size(); i++) {
      int thisdist = getDist(elementAtx, v, vsize);
      if (thisdist > dist) {
        dist = thisdist;
        ind = i;
      }
    }
    return ind;
  }
  

  int getDist(Vector v1, int[] v, int vsize)
  {
    int dist = getDist(elementAt0x, v, vsize);
    for (int i = 1; i < v1.size(); i++) {
      int thisdist = getDist(elementAtx, v, vsize);
      if (thisdist > dist)
        dist = thisdist;
    }
    return dist;
  }
  

  int getDist(int val, int[] v, int vsize)
  {
    int dist = diff(val, v[0]);
    for (int i = 1; i < vsize; i++) {
      int thisdist = diff(val, v[i]);
      if (thisdist < dist)
        dist = thisdist;
    }
    return dist;
  }
  
  private int bestFit(int[] vals, int val, int vsize) {
    int bestInd = 0;
    

    int bestDif = diff(vals[0], val);
    if (bestDif == 0)
      return val;
    for (int i = 1; i < vsize; i++) {
      int thisDif = diff(vals[i], val);
      if (thisDif < bestDif) {
        if (thisDif == 0)
          return vals[i];
        bestInd = i;
        bestDif = thisDif;
      }
    }
    if (debugGif) {
      System.out.println("Replacing " + rgb(val) + " with " + rgb(vals[bestInd]) + " having dist " + diff(val, vals[bestInd]));
    }
    return vals[bestInd];
  }
  


  private String rgb(int n)
  {
    int r = n >>> 16 & 0xFF;
    int g = n >> 8 & 0xFF;
    int b = n & 0xFF;
    return r + "+" + g + "+" + b;
  }
  


  private int diff(int x, int y)
  {
    if (x == y) return 0;
    int d1 = (x & 0xFF) - (y & 0xFF);
    int d2 = (x >>> 8 & 0xFF) - (y >>> 8 & 0xFF);
    int d3 = (x >>> 16 & 0xFF) - (y >>> 16 & 0xFF);
    return d1 * d1 + d2 * d2 + d3 * d3;
  }
  



  private void sortPoints(Vector v)
  {
    int size = v.size();
    for (int i = 0; i < size - 1; i++)
      for (int j = 0; j < size - i - 1; j++) {
        Point p1 = (Point)v.elementAt(j);
        Point p2 = (Point)v.elementAt(j + 1);
        if (y < y) {
          v.setElementAt(p1, j + 1);
          v.setElementAt(p2, j);
        }
      }
  }
  
  public static void setDebug(boolean f) {
    debugGif = f;
  }
  
  public static boolean getDebug() {
    return debugGif;
  }
}
