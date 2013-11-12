public class LocationA1 extends Location { 

    
    public LocationA1() { 
	super();
	roomTitle = "Undead Horde";
	roomDescription = "They are here. Their eyes gleam with hunger, flesh alone will not sate it. As you enter, the Lich King laughs at your insolence. You are not ready.";
    } 


    public void enter(Player p){
	String byebye;
	
	byebye = super.formatString(roomDescription)+"\n\n"+super.formatString("Alas, his dead eyes pierce your soul. They see within you, your innermost self and quash it.")+"\n\n You are no more than a body. \n\n You have become one of them.\n\nGAME OVER =(";
	
	p.gameOver(byebye);
    }
        
} 