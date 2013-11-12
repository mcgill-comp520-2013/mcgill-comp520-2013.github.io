import joos.lib.*;
import java.util.*;
public class Player {
    
    //We use two item list as a kind of hack as one has easy look up,
    //the other pretty printing
    protected HashMap inventory;
    protected Vector itemList;
  
    protected JoosIO f;
    protected Location current;
    protected Location previous;
    protected boolean gameOverYet;
    protected String byebye;

    public Player(Location start){
	super();
	inventory = new HashMap();
	itemList = new Vector();
	f = new JoosIO();
	current = start;
	previous = start;
	gameOverYet = false;	
    }
    
    public Location getLocation(){
	return current;
    }

    public Location getPrevious(){
	return previous;
    }

    public void move(Location l){
	current.exit(this);
	previous = current;
	current = l;
	current.enter(this);
    }
    
    public boolean isGameOver(){
	return gameOverYet;
    }

    public void gameOver(String msg){
	byebye = msg;
	gameOverYet = true;
    }
    public String farewellMsg(){
	return byebye;
    }

    public void addItem(Item i){
	inventory.put(i.getName(), i);
	itemList.addElement(i.getName());
    }
    public void removeItem(Item i){
	inventory.remove(i);
	itemList.removeElement(i.getName());
    }

    public Vector getInventory(){
	return itemList;
    }
}