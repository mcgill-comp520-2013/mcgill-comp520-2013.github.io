import joos.lib.*;

public class Beep 
{

    protected Tools t;
    protected BeepIO io;
    
    public Beep() 
    { 
        super(); 
        t = new Tools();
        io = new BeepIO();
    }
    
    public static void main(String argv[])
    {
        Beep b;
        b = new Beep();
        b.run();
    }
    
    public void run()
    {
        BeepIO f;
        String line;
        String oldword;
        String word;
		int currentLineOffset;
        Lexicon l;
		
        f = new BeepIO();
        oldword = "";
        word = "";
        line = f.readLine();
        currentLineOffset = 0;
        f.setPrintDelay(15);
        l = new Lexicon();
		
        while (line != null)
        {
            oldword = t.getNextToken(line);
			
            word = l.translate(oldword);
			if (word.equals(oldword) && oldword.endsWith("s"))
			{
				oldword = oldword.substring(0, oldword.length()-1);
				word = l.translate(oldword);
				word = word + "s";
				oldword = oldword + "s";
			}
			
            if(!word.equals(oldword))
                word = "*" + word + "*";
            
            if (!oldword.equals(line))
				line = line.substring(oldword.length()+1, line.length());
            else
                line = f.readLine();

            if(!word.equals(oldword)) t.beep(1);
			
			/* Words larger than 80 chars must be split so there is no word wrap */
			if(word.length() > 80)
			{
				f.print(word);
				currentLineOffset = (currentLineOffset + word.length()) % 80;
				if(currentLineOffset > 0)
				{
					f.print(" ");
					currentLineOffset = (currentLineOffset + 1) % 80;
				}
			}
			/* Words smaller than or equal to 80 chars must be wrapped if they make
			* the current offset overflow 80 chars
			*/
			else if(currentLineOffset + word.length() > 80)
			{
				currentLineOffset = word.length() % 80;
				f.println("");
				f.print(word);
				if(currentLineOffset > 0)
				{
					f.print(" ");
					currentLineOffset = (currentLineOffset + 1) % 80;
				}
			}
			/* Otherwise, words are sent straight to stdout!
			*/
			else
			{
				currentLineOffset = (currentLineOffset + word.length()) % 80;
				f.print(word);
				if(currentLineOffset > 0)
				{
					f.print(" ");
					currentLineOffset = (currentLineOffset + 1) % 80;
				}
			}
            t.sleep(200);
        }
    }
}
