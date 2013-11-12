import joos.lib.*;
import java.util.*;

public class WhatCommand extends Command {
    
    protected HashSet synonyms;
    protected JoosIO f;
    protected HashMap items;

    public WhatCommand(){
	super();
	synonyms = new HashSet();
	synonyms.add("WHAT");
	f = new JoosIO();
    }
    
    public void execute(String name, String target, Player p){
	Link exit;
	Location l;

	l = p.getLocation();

	if (target == null){
	    f.print("WHAT is nothing but a social construct.");
	    return;
	}
	
	
			
	if (l.hasLink(target)){
	    exit = l.getLink(target);
	    p.move(exit.linksTo());
	    return;
	}
	f.print("Sorry, Dave, I cannot "+name+" "+target+".");
	
    }
		
    public boolean isCommand(String s){
	return synonyms.contains(s);
    }

}