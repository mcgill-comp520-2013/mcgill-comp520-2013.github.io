

public class Sword extends Item
{
	
    public Sword(Room r)
    {
        super();
        offense = 2;
        name = "sword";
        room = r;
        room.getItems().add(this);
    }
    

}
