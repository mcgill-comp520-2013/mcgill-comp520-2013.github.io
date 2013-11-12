

public class Armour extends Item
{

    public Armour(Room r)
    {
        super();
        defense = 2;
        name = "armour";
        room = r;
        room.getItems().add(this);
    }
    
}
