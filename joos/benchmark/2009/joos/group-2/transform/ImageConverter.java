import java.awt.image.*;
import java.awt.*;
import joos.lib.*;
import javax.swing.*;
import sun.awt.image.*;

/**
 * An imageconverter object. Responsible for splitting the image into several
 * macro blocks, and ensuring that the resulting image keeps its aspect ratio.
 */
public class ImageConverter {
  protected ImageTransform tf;
  protected NormalizedImage img;
  protected BufferedImage bimg;
  protected ImageIcon icon;

  public ImageConverter(String path, ImageTransform tf1) {
    super();
    tf = tf1;
    icon = new ImageIcon(path);
    bimg = ((ToolkitImage)icon.getImage()).getBufferedImage();
    img = new NormalizedImage(icon,bimg,tf);
  }

  public ImageTransform getTransform() {
    return tf;
  }

  public ASCIIImage convert() {
    MacroBlock blk;
    ASCIIImage image;
    int row;
    int col;


    image = new ASCIIImage(tf.getHeight(), tf.getWidth());

    for (row=0; row < tf.getHeight(); row++) {
      for (col=0; col < tf.getWidth(); col++) {
        blk = this.getMacroBlock(row,col);
        image.setCharacter(row,col,blk.transform(img,tf));
      }
    } 

    return image;
  }

  public MacroBlock getMacroBlock(int row, int col) {
    MacroBlock mb;
    int startX, startY, w, h;
    
    startX = (img.getWidth()*col)/tf.getWidth();
    w = img.getWidth()/tf.getWidth();
    startY = (img.getHeight()*row)/tf.getHeight();
    h = img.getHeight()/tf.getHeight();
    mb = new MacroBlock(startX,startY,w,h);
   
    return mb;
  }

}

