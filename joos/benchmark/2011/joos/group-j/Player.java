import java.util.ArrayList;



public class Player extends Creature
{

	
	
	public Player(Room r)
	{
		super();
		room = r;
		health = 10;
		maxhealth = 10;
		offense = 5;
		defense = 0;
		name = "Player";
		room.getCreatures().add(this);
	}
		
	public boolean moveToRoom(Room r)
	{
		if (room.getLinks().contains(r)) 
		{
			room.getCreatures().remove(this);
			room = r;
			room.getCreatures().add(this);
			return true;
		}
		else {return false;}
	}
	
	public String Examine()
	{
		return room.examine();
	}
	
	public String take()
	{
		Object item;
		if (!room.getItems().isEmpty())
		{
			item = room.getItems().get(room.getItems().size()-1);
			room.getItems().remove(item);
			items.add(item);
			return "Got " + item;
		}
		else return "There's nothing to take";
	}
}
