package Jeli.Logging;

import java.awt.Image;

public class ImageLogger extends Thread
{
  private Image im;
  private Logger logger;
  private SaveImageInfo sio;
  private String caption;
  
  public ImageLogger(String caption, Image im, SaveImageInfo sio)
  {
    this.im = im;
    logger = Jeli.Utility.getLogger();
    this.sio = sio;
    this.caption = caption;
    setName("Jeli Image Logger");
  }
  
  public void run() {
    if (logger == null)
      return;
    logger.logImage(im, caption, sio);
  }
}
