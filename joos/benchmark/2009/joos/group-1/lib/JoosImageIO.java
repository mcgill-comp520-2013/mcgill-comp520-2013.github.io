package lib;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class JoosImageIO {
  public JoosImageIO(){}
  
  public BufferedImage read()
  {
    try
    {
      return ImageIO.read(System.in);
    }
    catch (IOException e)
    {
      return null;
    }
  }
  
  public boolean write(BufferedImage im)
  {
    try
    {
      return ImageIO.write(im, "PNG", System.out);
    }
    catch (IOException e)
    {
      return false;
    }
  }
}
