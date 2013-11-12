import joos.lib.*;
import java.util.*;

public class Lexicon 
{
    protected Dictionary _dict;
    
    public Lexicon() 
    { 
        super(); 
        _dict = new Dictionary();
        
        /* Hard-coded lexicon */
        _dict.addElement("ass", "donkey");
        _dict.addElement("asshole", "anal cavity");
        _dict.addElement("asswipe", "anus cleaner");
		_dict.addElement("ball", "testicle");
        _dict.addElement("bastard", "illegitimate child");
        _dict.addElement("bitch", "female doggie");
        _dict.addElement("bitchy", "female doglike");
		_dict.addElement("boner", "erection");
		_dict.addElement("cock", "rooster");
        _dict.addElement("douchebag", "shower purse");
        _dict.addElement("fuck", "please");
        _dict.addElement("fucked", "pleased");
        _dict.addElement("fucker", "pleaser");
        _dict.addElement("fucking", "pleasing");
        _dict.addElement("motherfucker", "mom-pleaser");
        _dict.addElement("motherfucking", "mom-pleasing");
        _dict.addElement("shit", "fecal matter");
		_dict.addElement("suck", "blow");
		_dict.addElement("sucking", "blowing");
        _dict.addElement("retard", "mentally challenged person");
        _dict.addElement("retarted", "mentally challenged");
        _dict.addElement("whore", "streetwalker");
    }
    
    public String translate(String word)
    {
        String translation;
        
        translation = _dict.get(word);
        if(translation == null)
            return word;
        
        return translation;
    }
}
