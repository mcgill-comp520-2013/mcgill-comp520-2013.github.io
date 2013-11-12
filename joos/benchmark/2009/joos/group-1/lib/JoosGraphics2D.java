package lib;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;

// Enables scaling.
// We could not do it in joos because we cannot use RenderingHints.Key as a type.
public class JoosGraphics2D {
	public JoosGraphics2D() {super();}
	
	public BufferedImage scaleImage(BufferedImage image, int newWidth, int newHeight) {
		BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = scaledImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, newWidth, newHeight, null);

		graphics2D.dispose();
		
		return scaledImage;
	}
}