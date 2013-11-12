import joos.lib.*;

public abstract class ImageTransform {
  public ImageTransform() {
    super();
  }

  public abstract String apply(int brightnessLevel);
  public abstract int getWidth();
  public abstract int getHeight();
}
