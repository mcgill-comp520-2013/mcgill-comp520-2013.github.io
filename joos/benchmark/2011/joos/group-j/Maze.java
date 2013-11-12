
import java.util.ArrayList;
import java.util.Random;
import joos.lib.JoosIO;

public class Maze
{
    protected ArrayList rooms;
    protected int i;
    protected int y;
    protected int x;
    protected Room next;
    protected Random r;
    protected Player player;
    protected Treasure treasure;
    
	public Maze()
    {
		super();
		
        rooms = new ArrayList();
        
        for (y = 0; y < 10; y = y + 1)
        {
            for (x = 0; x < 20; x = x + 1)
            {
                rooms.add(new Room(x,y,rooms));
            }
        }
        
        next = (Room)rooms.get(0);
        r = new Random(1);
        
        while (next != null)
        {
            next = next.setup(r.nextInt(100));
        }
        
    }
    
	public Player getPlayer()
	{
		return player;
	}

	public ArrayList getRooms()
	{
		return rooms;
	}

	public void setPlayer(Player p)
	{
		player = p;
	}
	
    public void printMaze(JoosIO io)
    {
        Room target;
        
        
        for (y = 9; y >= 0; y = y - 1)
        {
            
            for (x = 0; x < 20; x = x + 1)
            {
                target = (Room)rooms.get(y*20 + x);
                
                io.print("0");
                if (target.getUp() != null && target.getLinks().contains(target.getUp())) {io.print(" ");}
                else {io.print("0");}
                io.print("0");
            }
            io.println("");
            
            for (x = 0; x < 20; x = x + 1)
            {
                target = (Room)rooms.get(y*20 + x);
                
                if (target.getLeft() != null && target.getLinks().contains(target.getLeft())) {io.print(" ");}
                else {io.print("0");}
                if (target.isVisited() && target.getCreatures().contains(player)) io.print("@");
                else if (target.isVisited() && target.getItems().contains(treasure)) io.print("T");
                else if (target.isVisited() && !target.getCreatures().isEmpty()) io.print("X");
                else if (target.isVisited() && !target.getItems().isEmpty()) io.print("*");
                else if (target.isVisited()) io.print(" ");
                else {io.print("0");}
                if (target.getRight() != null && target.getLinks().contains(target.getRight())) {io.print(" ");}
                else {io.print("0");}
            }
            io.println("");
            
            
            for (x = 0; x < 20; x = x + 1)
            {
                target = (Room)rooms.get(y*20 + x);
                
                io.print("0");
                if (target.getDown() != null && target.getLinks().contains(target.getDown())) {io.print(" ");}
                else {io.print("0");}
                io.print("0");
            }
            io.println("");
                
        }
    }
    
    public Treasure getTreasure()
    {
    	return treasure;
    }
    
    public void setTreasure(Treasure t)
    {
    	treasure = t;
    }
    
    public Random getRandom()
    {
    	return r;
    }
    
    public static void main(String args[])
    {
    	JoosIO io;
    	Maze maze;
        String input;
        Player player;
        Creature monster;
    	io = new JoosIO();
        maze = new Maze();
        
        player = new Player((Room)maze.getRooms().get(0));
        maze.setPlayer(player);
        //new Goblin((Room)maze.getRooms().get(3*20));
        //x = r.nextInt(19*9);
        //templist = getRooms();
        //temp = templist.get(x);
        maze.setTreasure(new Treasure((Room)maze.getRooms().get(maze.getRandom().nextInt(19*9))));
        
        maze.printMaze(io);
        while(true)
        {
        	//deadplayer
        	if(player.dead()){io.println("You have died"); return;}
        	
        	if(player.getItems().contains(maze.getTreasure())){io.println("You found the treasure! You win!"); return;}
        	
        	monster = player.getRoom().getMonster();
        	if (monster != null) {io.print(player.attack(monster));}
        	
        	io.print(player.getRoom().examine());
        	io.print("Input Command: ");
        	input = io.readLine();
        	
        	//map
        	if (input.equals("map")) {maze.printMaze(io);}
        	else if (input.equals("quit")) {return;}
        	
        	//movement
        	else if (input.equals("up") || input.equals("north"))
        	{
        		if (player.moveToRoom(player.getRoom().getUp())){}
        		else {io.println("You can't move that way");}
        	}
        	else if (input.equals("down") || input.equals("south"))
        	{
        		if (player.moveToRoom(player.getRoom().getDown())){}
        		else {io.println("You can't move that way");}
        	}
        	else if (input.equals("left") || input.equals("west")) 
        	{
        		if (player.moveToRoom(player.getRoom().getLeft())){}
        		else {io.println("You can't move that way");}
        	}
        	else if (input.equals("right") || input.equals("east"))
        	{
        		if (player.moveToRoom(player.getRoom().getRight())){}
        		else {io.println("You can't move that way");}
        	}
        	
        	//take
        	else if (input.equals("take"))
        	{
        		io.println(player.take());
        	}
        	
        	//examine
        	else if (input.equals("examine") || input.equals("look"))
        	{
        		io.print(player.getRoom().examine());
        	}
        	
        	else
        	{
        		io.println("invalid command");
        	}
        }
    }
    
}