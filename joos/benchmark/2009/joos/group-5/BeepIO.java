import joos.lib.*;

public class BeepIO extends JoosIO
{
    protected Tools t;
	protected int printDelay;
    
    public BeepIO() 
    { 
        super(); 
        t = new Tools();
		printDelay = 0;
    }
	
	public void setPrintDelay(int delay)
	{
		printDelay = delay;
	}

    public void print(String string)
    {
        int i;
        for (i = 0; i < string.length(); i++)
        {
            super.print("" + string.charAt(i));
            t.sleep(printDelay);
        }
        super.flush();
    }
    
    public void println(String string)
    {
        this.print(string + "\n");
    }
}