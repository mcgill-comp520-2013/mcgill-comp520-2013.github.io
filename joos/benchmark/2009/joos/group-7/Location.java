import joos.lib.*;
import java.util.*;

public abstract class Location { 

    
    protected String roomTitle; 
    protected String roomDescription; 
    protected HashMap links;
    protected HashMap items;
    protected int visits; 
 

    public Location() { 
	super();
	//Exits and connections
	links = new HashMap();
	//Items in the room
	items = new HashMap();
	visits = 0;
    } 
    
    public String toString() { 
	return roomTitle+":\n\n"+this.formatString(roomDescription)+"\n";
  	//Exits at:"+this.linksString(); 
    } 

    /*
      Utility to break up strings into bite sized pieces
     */
    public String formatString(String s){
	StringBuilder sb;
	int i;

	sb = new StringBuilder(s);
	i=0;
	//Inserts a new line at blank spaces about every 50 characters
	while ((i = sb.indexOf(" ", i + 50)) != -1) {
	    sb.replace(i, i + 1, "\n");
	}

	return sb.toString();
    }

    public void print(JoosIO f){
	f.println(this.toString());
    }
    
    public void addLink ( Link link ) { 
	links.put(link.getName().toLowerCase(), link); 
    } 
   
    public void removeLink ( Link link ) { 
	if (links.containsKey (link.getName())) { 
	    links.remove (link.getName()); 
	} 
    } 
    
    public HashMap getLinks () { 
	return links; 
    } 
		
    public boolean hasLink( String name ) {
	return links.containsKey(name.toLowerCase());
    } 

    public Link getLink( String name ) {
	return (Link) links.get(name.toLowerCase());
    } 

    public String linksString(){
	return links.toString();
    }

    public String getTitle() { 
	return roomTitle; 
    } 
    
    public void setTitle( String roomTitle ) { 
	roomTitle = roomTitle; 
    } 

    public HashMap getItems() { 
	return items; 
    } 
    public void removeItem(Item i) { 
	items.remove(i.getName()); 
    } 
 
    public String getDescription() { 
	return roomDescription; 
    } 
  
    public void setDescription( String roomDescription ) {
	roomDescription = roomDescription; 
    } 

    //To be used when coming in or out
    public void enter(Player p){
	visits++;
    }

    public void exit(Player p){
	;
    }

    
} 
