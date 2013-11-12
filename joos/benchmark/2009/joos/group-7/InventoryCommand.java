import joos.lib.*;
import java.util.*;

public class InventoryCommand extends Command {
    
    protected HashSet synonyms;
    protected JoosIO f;

    public InventoryCommand(){
	super();
	synonyms = new HashSet();
	synonyms.add("ITEMS");
	synonyms.add("INVENTORY");

	f = new JoosIO();
    }
    
    public void execute(String name, String target, Player p){
	Link link;
	Location l;
	Item i;
	f.print("Inventory:\n"+p.getInventory().toString());
	
    }
		
    public boolean isCommand(String s){
	return synonyms.contains(s);
    }

}