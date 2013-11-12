
public class LocationB2 extends Location { 
    public LocationB2() { 
	super();
	roomTitle = "Zork";
	roomDescription = "It is pitch black. You are likely to be eaten by a grue.";
	
    } 
    
    
    
    public void enter(Player p){
	String byebye;
	
	byebye = super.formatString(roomDescription)+"\n\n"+super.formatString("Actually there is one right there. You are dead.")+"\n\nGAME OVER!";
	
	p.gameOver(byebye);
    }
        
} 
