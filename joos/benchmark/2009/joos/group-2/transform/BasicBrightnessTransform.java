
import joos.lib.*;

public class BasicBrightnessTransform extends ImageTransform {

    protected int width;
    protected int height;
    protected String brightness;

    public BasicBrightnessTransform(int widtha, int heighta) {
        super();
        width = widtha;
        height = heighta;
        brightness = (" .`-_':,;^=+/|)\\<>)iv%xclrs{*}I?!][1taeo7zjLu" +
                "nT#JCwfy325Fp6mqSghVd4EgXPGZbYkOA&8U$@KHDBWNMR0Q");
    }

    public String apply(int brightnessLevel) {
        String asciiChar;
        int length;
        int index;
        
        if (brightnessLevel < 0) {
            brightnessLevel = 0;
        }
        if (brightnessLevel > 100) {
            brightnessLevel = 100;
        }

        length = brightness.length()-1;
        index =(length * brightnessLevel) / 100;
        asciiChar = brightness.substring(index, index + 1);
        return asciiChar;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
