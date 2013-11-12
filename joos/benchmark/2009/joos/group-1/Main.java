import java.awt.image.BufferedImage;
import lib.JoosImageIO;

import joos.lib.JoosIO;

public class Main {
  public Main() {
    super();
  }
  public static void main(String[] args) {
    BufferedImage image;
    JoosImageIO jiio;
	JoosIO jio;
	JoosImage ji;
	String asciiVersion, coloredAsciiVersion;
	int i, j;

	jio = new JoosIO();
    jiio = new JoosImageIO();
	// creates a JoosImage from the input.
	// A JoosImage is a type we created to handle scaling and ASCII conversion
    ji = new JoosImage(jiio.read());

	// processing
	
	// Automatic scale
	ji.scale();
	// Gets the string for the uncolored ASCII version
	asciiVersion = ji.convertToASCII();
	// Gets the string for the colored version
	coloredAsciiVersion = ji.convertToColoredASCII();
	
    // output : basic HTML tags to display the ascii images properly
	jio.println("<html><head><title>Image to ASCII convertor &mdash; Jeff Wallace & Antony Gardez</title></head><body><p><pre style='font-family: monospace; line-height: 0.45em; font-size:0.7em;'>" + asciiVersion + "</pre></p><p><pre style='font-family: monospace; line-height: 0.45em; font-size:0.7em;'>" + coloredAsciiVersion +"</pre></p></body></html>");
  }
}
