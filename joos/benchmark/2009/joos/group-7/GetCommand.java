import joos.lib.*;
import java.util.*;

public class GetCommand extends Command {
    
    protected HashSet synonyms;
    protected JoosIO f;

    public GetCommand(){
	super();
	synonyms = new HashSet();
	synonyms.add("GET");
	synonyms.add("GRAB");
	synonyms.add("TAKE");
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

	if (l.getItems().containsKey(target)){
	    i = (Item) l.getItems().get(target);
	    if (i.canTake()){
		p.addItem(i);
		l.removeItem(i);	     
		f.print(target + " is now in your inventory.");
		return;
	    }
	}
	f.print("Sorry, Dave, I cannot "+name+" "+target+".");
	
    }
		
    public boolean isCommand(String s){
	return synonyms.contains(s);
    }

}