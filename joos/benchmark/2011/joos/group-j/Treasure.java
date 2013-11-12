

public class Treasure extends Item
{
	
    public Treasure(Room r)
    {
        super();
        name = "really awesome treasure";
        room = r;
        room.getItems().add(this);
    }
    

}
