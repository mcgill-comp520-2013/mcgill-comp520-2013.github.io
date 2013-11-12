
/*THe class represents an item that can be equipped; It's a decorator class for the Creature class*/
public abstract class Item
{
    protected String name;
    protected int offense;
    protected int defense;
    protected Room room;
    
    public Item()
    {
        super();
        name = "some item";
        offense = 0;
        defense = 0;
    }

    public int getOffense()
    {
        return offense;
    }
    
    public int getDefense()
    {
    	return defense;
    }
    
    public String toString()
    {
    	return name;
    }
    
    public String getName()
    {
    	return name;
    }
}
