import java.awt.image.*;
import javax.swing.*;

/**
 * Proxy over BufferedImage, ensures that the aspect ratio is kept during
 * image transformation.
 */
public class NormalizedImage {
  protected int paddingHorizontal, paddingVertical;
  protected BufferedImage img;
  protected ImageTransform tf;
  protected ImageIcon icon;

  public NormalizedImage(ImageIcon icon1, BufferedImage img1, ImageTransform tf1) {
    super();
    img = img1;
    tf = tf1;
    icon = icon1;
    this.normalize();
  }

  public void normalize() {
    int aspectImg, aspectTf;

    paddingHorizontal = 0;
    paddingVertical = 0;
    aspectImg = (this.getRealWidth()*1000)/this.getRealHeight();
    aspectTf = (tf.getWidth()*1000)/tf.getHeight();

    if (aspectTf > aspectImg) {
      //Add horizontal padding
      paddingHorizontal = ((aspectTf-aspectImg)/1000)/2;
    } else if (aspectTf < aspectImg) {
      paddingVertical = ((aspectImg-aspectTf)/1000)/2;
    }
  }

  public int getRGB(int x, int y) {
     x = x - paddingHorizontal;
     y = y - paddingVertical;

     if (x < 0 || x >= this.getRealWidth()) {
       return 0;
     } else if (y < 0 || y >= this.getRealHeight()) {
       return 0;
     } else {
       return img.getRGB(x,y);
     }
  }
  public int getWidth() {
    return (this.getRealWidth() + 2*paddingHorizontal);
  }

  public int getHeight() {
    return (this.getRealHeight() + 2*paddingVertical);
  }

  public int getRealWidth() {
    return icon.getIconWidth();
  }

  public int getRealHeight() {
    return icon.getIconHeight();
  }
}

