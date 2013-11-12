import joos.lib.*;

public class WigGenerator extends TexGenerator
{
	protected String input;
	protected boolean inSubsection;
	
	public WigGenerator(String tex)
	{
		super();
		input = tex;
		inSubsection = false;
	}
	
	public boolean isPlainText(String line)
	{
		String trim;
		
		trim = line.trim();
		
		if (trim.startsWith("\\", 0))
			return false;
			
		return true;
	}
			
	public boolean isSubsection(String line)
	{
		String trim;
		
		trim = line.trim();
		
		if (trim.startsWith("\\subsection", 0))
			return true;
			
		return false;
	}
	
	
	public boolean willReplace(String texStr)
	{			
		if (!this.isPlainText(texStr))
			return false;
			
		if (inSubsection && this.isPlainText(texStr))
			return true;
			
		return false;
	}
	
	public String replace(String texStr)
	{
		// texStr should only be plaintext
		/* This would be a good place to use Arash's code
		 * to generate some sentences */

		SentenceGen SenGen;
		String Text;

		SenGen = new SentenceGen("Computer Science", "Compiler Design", "McGill", "Montreal");		
		Text = "";		
		Text = Text + SenGen.Generate(10, 0, 1, 2);
		Text = Text + SenGen.Generate(5, 1, 1, 2);

		return Text;
	}
	
	
	public void processLine(String line)
	{
		boolean plain;
		
		plain = this.isPlainText(line);
		
		if (this.isSubsection(line))
			inSubsection = true;
		else if(!plain)
			inSubsection = false;
			
		this.processTex(line);
		
		return;
	}

	public void processInput()
	{
		String line;
		String newline;
		int pos;
		int p;
		int length;
		JoosIO f;
		
		newline = "\n";
		pos = 0;
		length = input.length();
		f = new JoosIO();
		
		while (pos < length) {
			p = input.indexOf(newline, pos);
			line = input.substring(pos, p + 1);
			this.processLine(line);
			p = p+1;
			pos = p;
		}
		
	}
	
	public void generate()
	{
		this.processInput();
	}
}
