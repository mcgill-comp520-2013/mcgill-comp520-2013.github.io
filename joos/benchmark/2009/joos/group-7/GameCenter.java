import joos.lib.*;
import java.util.*;

public class GameCenter {

    protected JoosIO f;
    protected Vector cmdList;
    protected Player p;

    public GameCenter () {
	super();
	f = new JoosIO();
	cmdList = new Vector();
    }

    public static void main(String argv[])
    {  
	GameCenter g;
	g = new GameCenter();
	g.run();
    }

    /*
      Runs program 
      There is probably a nicer way to build up the game
      world than to do it here at the beginning of the method
    */
    public void run(){

	int i;
	String s;
	Location a1, a2, a3, a4, a5, b2, d12;

	//Set up commands
	cmdList.addElement(new MoveCommand());
	cmdList.addElement(new LookCommand());
	cmdList.addElement(new WhatCommand());
	cmdList.addElement(new GetCommand());
	cmdList.addElement(new InventoryCommand());
	//Set up Locations
	a2 = new LocationA2(); //Start
	a1 = new LocationA1();
	b2 = new LocationB2();
	a3 = new LocationA3();
	a4 = new LocationA4();
	a5 = new LocationA5();
	
	d12 = new LocationD12();

	//Add links between locations
	a2.addLink(new Link(a1, "NORTH", "A doorway of threatening igneous rock, still glowing."));  
	a2.addLink(new Link(b2, "EAST", "A familiar tunnel smelling faintly of iron."));  
	a2.addLink(new Link(a3, "SOUTH", "A sign above an opening in the cave wall reads 'Sic Transit Gloria Mundi'."));  
	a3.addLink(new Link(a2, "NORTH", "A sign above an opening in the cave wall reads 'Memento Mori'.")); 
	a3.addLink(new Link(a4, "SOUTH", "An ominous hallway looms ahead.")); 
	a4.addLink(new Link(a3, "NORTH", "To add")); 
	a4.addLink(new Link(a5, "SOUTH", "A calming light shimmers.")); 
	a5.addLink(new Link(a4, "NORTH", "To add."));
	a5.addLink(new Link(d12, "", "HOME!"));
	
	//Start up the player
	p = new Player(a2);

	while(true){
	    if (p.isGameOver()){
		f.println(p.farewellMsg());
		return;
	    }
	    p.getLocation().print(f);
	    f.print("> ");
	    s = f.readLine().trim();
	    f.print("\n\n---------------\n\n");
	    this.parseCommand(s);					
	    f.print("\n---------------\n\n");
	}
				
	
    }

    /*
      Parses and Executes commands
    */

    public void parseCommand(String command ){

	String com;
	String target;
	int i;
	Command c;
	boolean commandFound;

	if (command.length() == 0){
	    f.println("Enter a command or action");
	    return;
	}

	//Break up command and the target of it
	i = command.indexOf(" ", 0);
	if (i == -1){
	    com = command.toUpperCase();
	    target = null;
	}
	else{
	    com = command.substring(0,i).toUpperCase();
	    target = command.substring(i+1, command.length()).toUpperCase();
	}
				
	commandFound = false;

	//Find what command to execute and do it
	for (i=0; i < cmdList.size(); i++){
	    c = (Command) cmdList.elementAt(i);
	    if (c.isCommand(com)){
		c.execute(com, target, p);
		return;
	    }
	}

	f.println("Do not understand "+com.toUpperCase()+ ", try something like GO, LOOK, TAKE");	    
    }

}
