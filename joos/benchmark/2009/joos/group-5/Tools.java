import joos.lib.*;
import javax.sound.sampled.*;

public class Tools {
    
    public Tools() { super(); }
    
    public void sleep(int milliseconds)
    {
        JoosSystem js;
        int start;
        js = new JoosSystem();
        start = js.currentTimeMillis();
        while (js.currentTimeMillis() < start + milliseconds);
    }
    
    public void beep(int count)
    {
        JoosIO f;
        char ch;
        int i;
        f = new JoosIO();
        ch = (char)7;
        for (i = 0; i < count; i++)
        {            
            f.print("" + ch);
        }
    }
    
    public String getNextToken(String line)
    {
        int index;
        String word;
        index = line.indexOf(" ", 0);
        if (index == -1)
            return line;
        word = line.substring(0, index);
        return word;
    }
}
  
