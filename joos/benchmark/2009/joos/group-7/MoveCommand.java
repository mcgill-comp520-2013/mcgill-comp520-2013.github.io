import joos.lib.*;
import java.util.*;

public class MoveCommand extends Command {
    
    protected HashSet synonyms;
    protected JoosIO f;

    public MoveCommand(){
	super();
	synonyms = new HashSet();
	synonyms.add("GO");
	synonyms.add("MOVE");
	synonyms.add("HEAD");
	f = new JoosIO();
    }
    
    public void execute(String name, String target, Player p){
	Link exit;
	Location l;

	l = p.getLocation();

	if (target == null){
	    f.print("Where do you want to "+name+"?");
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