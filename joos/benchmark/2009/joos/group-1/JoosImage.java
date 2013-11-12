import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Color;
import lib.JoosGraphics2D;
import lib.JoosInt;
import java.util.Vector;

public class JoosImage {
	
	protected BufferedImage image;
	
	public JoosImage(BufferedImage _image) {
		super();
		image = _image;
	}
	
	public int getWidth() {
		return image.getWidth();
	}
	
	public int getHeight() {
		return image.getHeight();
	}
	
	// According to wikipedia :
	// 30% of the red value, 59% of the green value, and 11% of the blue value
	public Vector getGreyscaleVector() {
		int i, j;
		Color color;
		Vector vect;
		
		vect = new Vector(this.getWidth()*this.getHeight());
		for (j = 0; j < this.getHeight(); j++) {
			for (i = 0; i < this.getWidth(); i++) {
				color = new Color(image.getRGB(i,j));
				vect.addElement(new Integer((color.getRed()*30 + color.getGreen()*59 + color.getBlue()*11)/100));
			}
		}
		
		return vect;
	}
	
	/**
	 Scale the image so that the longest edge is 150 pixels
	*/
	public void scale() {
		Graphics2D graphics2D;
		int newHeight, newWidth, scale;
		
		JoosGraphics2D jg2d;
		
		// find the longest edge and calculate the new height and width
		if (this.getHeight() >  this.getWidth()) {
			newHeight = this.getHeight()*100/this.getHeight();
			newWidth = this.getWidth()*100/this.getHeight();
		} else {
			newHeight = this.getHeight()*100/this.getWidth();
			newWidth = this.getWidth()*100/this.getWidth();
		}

		jg2d = new JoosGraphics2D();
        
        // set image to the new scaled image
		image = jg2d.scaleImage(image, newWidth, newHeight);
	}
	
	/**
	 Convert the image to plain text
	*/
	public String convertToASCII()
    {
        Vector greyscale;
        int c, value;
        char cur;
        String out;
        
        out = "";

        greyscale = this.getGreyscaleVector();
        
        for (c = 0 ; c < greyscale.size() ; c++)
        {
            // for the current intensity, set the appropriate character
            cur = this.getCharForValue(((Integer) greyscale.elementAt(c)).intValue());
            out = out + cur;
            
            if ((c+1) % this.getWidth() == 0)
                out = out + "\n";
        }
        
		return out;
    }
    
    /**
     Convert the image to colored text using HTML
    */
	public String convertToColoredASCII()
    {
        Vector greyscale;
        int i, j;
        String cur, out;
		JoosInt ji;
        
        out = "";
		ji = new JoosInt();

        greyscale = this.getGreyscaleVector();
        
		// loop through the x and y values
		for (i = 0 ; i < this.getHeight(); i++)
		{
	       	for (j = 0 ; j < this.getWidth() ; j++)
        	{
            	// get the RGB value in hex and the appropriate character for the current pixel
            	cur = "<span style='Color: #" + ji.toHexString(image.getRGB(j, i)).substring(2,8) + "'>" + this.getCharForValue(((Integer) greyscale.elementAt(i*this.getWidth()+j)).intValue()) + "</span>";
            	out = out + cur;
        	}
			out = out + "\n";
		}
        
		return out;
    }

    /**
     Get the appropriate character for the given density.
    */
    public char getCharForValue(int value)
    {
        String chars;
        
        // character "ramp" from black to white
        chars = new String("$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~>i!lI;:,^`'. ");
        
        return chars.charAt( (value * chars.length() -1) / 255 );
    }	
}
