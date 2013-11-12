import java.awt.image.*;

/**
 * A single macroblock. A macroblock is a rectangular subset of the image.
 */
public class MacroBlock {
  protected int x, y, w, h;

  public MacroBlock(int xs, int ys, int ws, int hs) {
    super();
    x = xs;
    y = ys;
    w = ws;
    h = hs;
    
  }

  public String transform(NormalizedImage img, ImageTransform tf) {
     int brightness, pixel;
     int i,j;
     String ascii;

     brightness=100;
     for (i=0; i<w; i++) {
       for(j=0; j<h; j++) {
         pixel = this.calcBrightness(img.getRGB(y+j,x+i));
         brightness = brightness + pixel;
         brightness = brightness / 2;
       }
     }
     
     
     ascii = tf.apply(brightness);
     return ascii;
  }

  public int calcBrightness(int rgb) {
    int a,r,g,b;
    if (rgb < 0) rgb = -rgb;
    a = rgb/16777216;
    rgb = rgb-a*16777216;
    r = (rgb/65536);
    g = (rgb/256) - r*256;
    b = (rgb) - r*65536 - g*256;
    return ((r+g+b)*100)/(255*3);
  }
}

