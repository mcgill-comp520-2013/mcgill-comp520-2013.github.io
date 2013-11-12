import joos.lib.*;
import java.util.*;

public class LookCommand extends Command {
    
    protected HashSet synonyms;
    protected JoosIO f;

    public LookCommand(){
	super();
	synonyms = new HashSet();
	synonyms.add("LOOK");
	synonyms.add("INSPECT");
	synonyms.add("CHECK");
	synonyms.add("SEE");
	f = new JoosIO();
    }
    
    public void execute(String name, String target, Player p){
	Link link;
	Location l;
	Item i;
	l = p.getLocation();

	if (target == null){
	    f.print("Where/What do you want to "+name+"?");
	    return;
	}
				
	if (l.hasLink(target)){
	    f.print(super.formatString(l.getLink(target).getDescription()));
	    return;
	}
	if (l.getItems().containsKey(target)){
	    i = (Item) l.getItems().get(target);
	    f.print(i.getDescription());
	    return;
	}
	f.print("Sorry, Dave, I cannot "+name+" "+target+".");
	
    }
		
    public boolean isCommand(String s){
	return synonyms.contains(s);
    }

}