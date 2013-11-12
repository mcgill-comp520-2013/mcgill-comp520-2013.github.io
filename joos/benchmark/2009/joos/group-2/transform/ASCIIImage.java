import joos.lib.*;
import java.util.*;
/**
 * A single ASCII image represented by a 2 dimensional table of characters.
 *
 */
public class ASCIIImage {
  protected ArrayList table;
  protected int width;
  protected int height;
  protected int ii;

  public ASCIIImage(int h, int w) {
     super();
     width = w;
     height = h;
     table = new ArrayList(height);
     for (ii=0; ii<height; ii++) {
       table.add(ii, new ArrayList(width));
     }
  }

  public void setCharacter(int r, int c, String chr) {
    ArrayList row;

    row = (ArrayList)table.get(r);
    row.add(c, chr);
  }

  public String getCharacter(int r, int c) {
    ArrayList row;
    String ch;

    row = (ArrayList)table.get(r);
    ch = (String)row.get(c);

    return ch;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

}
