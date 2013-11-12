import joos.lib.*;

public class Befunge {

	public Befunge () { super(); }

	public static void main(String argv[])
	{
		boolean takeInputFile;
		BefungeCanvas initCanvas;
		int counter;
		BefungeCoder myCoder;
		JoosIO myIO;
		String res;
		
		JoosIO io;
		String line;

		io = new JoosIO();
		
		initCanvas = new BefungeCanvas();
		
		//here we read in the input from file.
		//Befunge defines an 80*25 Canvas anything more is ignored!!! 
		
		counter = 1;
		while ((line = io.readLine()) != null && counter <= 25 ) 
		{
			initCanvas.addLineToCanavas(line);
			counter = counter + 1;
		}
		
		myCoder = new BefungeCoder(initCanvas);

		res = myCoder.befungeParse();
		
		myIO = new JoosIO();
		myIO.println(res);
	}
}
