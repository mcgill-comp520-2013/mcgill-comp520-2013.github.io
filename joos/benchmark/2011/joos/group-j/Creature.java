import java.util.ArrayList;
import joos.lib.JoosIO;


//  This class represents a creature in the game (player or a monster)
public abstract class Creature
{
    protected String name;
    protected int offense;
    protected int defense;
    protected int maxhealth;
    protected int health;
    protected boolean isDead;
    protected Room room;
    protected ArrayList items;
    
    public Creature()
    {
        super();
        isDead = false;
        items = new ArrayList();
    }
    
	public boolean dead()
	{
		return isDead;
	}
    
    public String getName()
    {
        return name;
    }

	public Room getRoom()
	{
		return room;
	}

	public void setRoom(Room r)
	{
		room = r;
	}

	public int getHealth()
    {
        return health;
    }
    
    
    public void takeDamage(int damage)
    {
        health = health - damage;
        if (health < 0)
        {
            isDead = true;
            room.getCreatures().remove(this);
        }    
    }
    
    public int getOffense()
    {
    	int x;
    	int i;
    	int j;
    	
    	x = offense;
    	if (items.isEmpty()) {return x;}
    	
    	j = items.size();
        for (i = 0; i < j; i = i + 1)
        {
        	x = x + ((Item)items.get(i)).getOffense();
        }      
        return x;
    }
    
    public int getDefense()
    {
    	int x;
    	int i;
    	int j;
    	
    	x = defense;
    	if (items.isEmpty()) {return x;}
    	
    	j = items.size();
        for (i = 0; i < j; i = i + 1)
        {
        	x = x + ((Item)items.get(i)).getDefense();
        }      
        return x;
    }
    
    public ArrayList getItems()
    {
    	return items;
    }

	public void setDead(boolean d)
	{
		isDead = d;
	}

	public String attack( Creature c )
    {
        int tempdamage;
        String s;
        s = name + " attacks " + c.getName() + "\n";
        
        tempdamage = this.getOffense();
        tempdamage = tempdamage - c.getDefense();
        s = s + c.getName() + " takes " + tempdamage + " damage\n";
        c.takeDamage(tempdamage);
        
        if(!c.dead()) {s = s + c.attack(this);}
        else {s = s + c.getName() + " dies!\n";}
        
        health = maxhealth;
        return s;
    }
    
}
