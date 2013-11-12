
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;


public class Room
{
	protected int index;
	protected int x;
	protected int y;
	protected ArrayList links;
	protected Stack randlinks;
	protected ArrayList maze;
	protected Room up;
	protected Room down;
	protected Room left;
	protected Room right;
	protected Random r;
	protected boolean visited;
	protected Room parent;
	protected int i;
	protected int j;
	protected ArrayList creatures;
    protected ArrayList items;
    
    public Room(int xx, int yy, ArrayList m)
    {
        super();
        x = xx;
        y = yy;
        maze = m;
        index = y*20 + x;
        links = new ArrayList(4);
        creatures = new ArrayList();
        items = new ArrayList();
        visited = false;
    }
    
    public Room findandlink(int xx, int yy)
    {
    	Room target;
        if (xx < 0 || xx >= 20 || yy < 0 || yy >= 10) {return null;}
        target = (Room)maze.get(yy*20 + xx);
        //if (links.contains(target)) {return null;}
        //if (target.visited) {return null;}
        links.add(target);
        return target;
    }
    
    public Room setup(int seed)
    {
    	Room target;
    	int xx;
    	int yy;
        while(randlinks != null) //we've started setting up this room up before
        {
            if (randlinks.empty()) {return parent;} //we're done, backtrace to the parent
            if ( ((Room)randlinks.peek()).isVisited() )
            {
            	randlinks.pop(); //pop until we find an unvisited room
            }
            else 
            {            
                target = (Room)randlinks.pop();
                target.setVisited(true);
                links.add(target);
                target.getLinks().add(this);
                target.setParent(this);
                return target; //return the next room to setup
            }
        }
        
        //we need to setup this room
        visited = true;
        r = new Random(seed);
        if (r.nextInt(10) == 0) {new Goblin(this);}
        if (r.nextInt(10) == 0) {new Sword(this);}
        if (r.nextInt(10) == 0) {new Armour(this);}
             
        //a lot of cut and paste code because joos is dumb
        xx = x;
        yy = y+1;
        if (xx >= 0 && xx < 20 && yy >= 0 && yy < 10) 
        {
        	target = (Room)maze.get(yy*20 + xx);
        	links.add(target);
        	up = target;
        }
        
        xx = x;
        yy = y-1;
        if (xx >= 0 && xx < 20 && yy >= 0 && yy < 10) 
        {
        	target = (Room)maze.get(yy*20 + xx);
        	links.add(target);
        	down = target;
        }
        
        xx = x-1;
        yy = y;
        if (xx >= 0 && xx < 20 && yy >= 0 && yy < 10) 
        {
        	target = (Room)maze.get(yy*20 + xx);
        	links.add(target);
        	left = target;
        }
        
        xx = x+1;
        yy = y;
        if (xx >= 0 && xx < 20 && yy >= 0 && yy < 10) 
        {
        	target = (Room)maze.get(yy*20 + xx);
        	links.add(target);
        	right = target;
        }
        
        randlinks = new Stack();
        j = links.size();
        for (i = 0; i < j; i = i + 1)
        {
        	target = (Room)links.get(r.nextInt(links.size()));
        	links.remove(target);
            randlinks.push(target); //create a random stack of neighbors
        }
        
        links.clear();//clear our links, we'll build them later
        if (parent != null) {links.add(parent);} //we always have a path to parent
        
        while(true) //this loop is only entered the first time a room is setup
        {           //we keep popping the stack until we get a next room that is unvisited
            if (randlinks.empty()) {return parent;}
            target = (Room)randlinks.peek();
            if ( target.isVisited() ) {randlinks.pop();}
            else 
            {
                target = (Room)randlinks.pop();
                target.setVisited(true);
                links.add(target);
                target.getLinks().add(this);
                target.setParent(this);
                return target; //return the next room to setup
            }
        }
        return null; //i know this is unreachable, joos needs this to compile
    }
    
    
    
    public String examine()
    {
    	String s;
    	int i;
    	int j;
    	Item item;
    	s = "You're in a room.\n";
    	
    	if (!items.isEmpty())
    	{
    		j = items.size();
            for (i = 0; i < j; i = i + 1)
            {
            	item = (Item)items.get(r.nextInt(items.size()));
            	s = s + "You see a " + item + "\n";
            }
    	}
    	
    	return s;
    }
    	
	public ArrayList getLinks()
	{
		return links;
	}

	public Room getUp()
	{
		return up;
	}

	public Room getDown()
	{
		return down;
	}

	public Room getLeft()
	{
		return left;
	}

	public Room getRight()
	{
		return right;
	}

	public boolean isVisited()
	{
		return visited;
	}
	
	public void setVisited(boolean v)
	{
		visited = v;
	}

	public ArrayList getCreatures()
	{
		return creatures;
	}

	public ArrayList getItems()
	{
		return items;
	}
	
	public void setParent(Room r)
	{
		parent = r;
	}
	
	public Room getParent()
	{
		return parent;
	}

	public void setUp(Room u)
	{
		up = u;
	}

	public void setDown(Room d)
	{
		down = d;
	}

	public void setLeft(Room l)
	{
		left = l;
	}

	public void setRight(Room r)
	{
		right = r;
	}
	
	public Creature getMonster()
	{
		int j;
		Creature c;
    	j = creatures.size();
        for (i = 0; i < j; i = i + 1)
        {
        	c = ((Creature)creatures.get(i));
        	if (!(c instanceof Player)) {return c;}
        }      
        return null;
	}
}
