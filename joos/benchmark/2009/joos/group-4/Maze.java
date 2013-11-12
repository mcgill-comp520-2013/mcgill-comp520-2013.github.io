
import joos.lib.*;
import java.util.*;

public class Maze 
{
	
	 //the dimension of our grid
	 
	protected int dimension;
	
	//
	 //the index of which random maze to print
	 //i.e if it is 10 will print the tenth
	 //maze found. This number is based on the dimension
	 //cubed so guaranteed that it will be less than
	 //total number of unicursal mazes possible
	 
	protected int randomMaze;
	
	
	 //will keep track of how long our 
	 //current path is.
	protected int mazeLength;
	

	 //variable to keep track of how many
	 //unicursal mazes have been found
	 
	protected int numOfMazes;
	

	protected boolean PRINT;
	
	protected boolean RANDOM;
	
	protected boolean PNFG;
	// a reference to the maze to print
	 // if the random option has been used
	// node[][]
	protected Vector mazeToPrint;
	
	public Maze(int dimension1, int randomMaze1, int mazeLength1, int numOfMazes1,
			boolean PRINT1, boolean RANDOM1, boolean PNFG1)
	{
		super();
		
		dimension = dimension1;
		randomMaze = randomMaze1;
		mazeLength = mazeLength1;
		numOfMazes = numOfMazes1;
		PRINT = PRINT1;
		RANDOM = RANDOM1;
		PNFG = PNFG1;
	}
	
	public int getDimension()
	{
		return dimension;
	}
	
	public int getNumOfMazes()
	{
		return numOfMazes;
	}
	
	public int getRandomMaze()
	{
		return randomMaze;
	}
	
	public Vector getMazeToPrint()
	{
		return mazeToPrint;
	}

	public static void main(String[] args) 
	{
		
		Maze mazeSolution;
		
		JoosIO io;
		
		int dimension;
		
		int randomMaze;

		int mazeLength;
		
		int numOfMazes;
		

		boolean PRINT;
		
		boolean RANDOM;
		
		boolean PNFG;
		
		Random generator;
		
		PRINT = true;
		RANDOM = false;
		PNFG = false;
		
		randomMaze = 0;
		mazeLength = 1;
		numOfMazes = 0;
		dimension = 0;
		
		io = new JoosIO();
		
		// read from standard in the dimension of the maze
		dimension = io.readInt();
		
	    if( dimension < 2 )
	    {
	    	io.println("dimension must greater than or equal to 2");
	    	(new JoosSystem()).exit(1);
	    }
	    
	    if ( RANDOM || PNFG )
	    {
	    	generator = new Random();
		
	    	// Just a quick fix since we cant really output the zeroth
	    	// maze found since we have not found one yet
	    	while (randomMaze == 0)
	    	{
	    		// relies on the fact that i know the total number of unicursal mazes
	    		// for dimension 2 to 6
	    		if (dimension == 2)
	    		{
	    			randomMaze = generator.nextInt( 9 );
	    		}
	    		else if (dimension == 3)
	    		{
	    			randomMaze = generator.nextInt( 41 );
	    		}    		
	    		else if (dimension == 4)
	    		{
	    			randomMaze = generator.nextInt( 553 );
	    		}
	    		else if (dimension == 5)
	    		{
	    			randomMaze = generator.nextInt( 8649 );
	    		}
	    		else if (dimension == 6)
	    		{
	    			randomMaze = generator.nextInt( 458697 );
	    		}
	    		else
	    		{
	    			randomMaze = generator.nextInt( 458697 );
	    		}
	    	} 	    	
	    		    	
	    }
	    
	    mazeSolution = new Maze(dimension, randomMaze, mazeLength, numOfMazes, PRINT, RANDOM, PNFG);

	    mazeSolution.countNumOfMazes();
	    
	    io.println("Final number of " + mazeSolution.getDimension() + "*" + mazeSolution.getDimension() + " unicursal mazes found is: " + mazeSolution.getNumOfMazes());
	    
	    // print a randomly generated maze ( only the unicursal core)
	    if ( RANDOM )
	    {
	    	io.println("");
	    	io.println( "Printing the " + mazeSolution.getRandomMaze() + " Unicursal maze found");
	    	mazeSolution.printMaze( mazeSolution.getMazeToPrint());
	    }
	    
	    if (RANDOM || PNFG)
	    {
		    // add random n-1 connections between non adjacent nodes to the maze
		    mazeSolution.connectNonAdjacent();
		    
		    // add random n-1 deads ends(size 1-3 rooms) to the maze
		    mazeSolution.addDeadEnds();
	    }
	    
	    if ( PNFG )
	    {
	    	mazeSolution.toPNFG(dimension);
	    }
	}
	
	/**
	 * a private method in charge of generating pnfg code
	 * for dead end branches
	 * @param out	the file to write to
	 * @param i		the current node row we are looking at that has dead ends connected to it
	 * @param j		the current node col we are looking at that has dead ends connected to it
	 * @param key	the key representing which side the dead end is originated from
	 */
	public void writeDeadEnds( int i, int j, String key )
	{
		Vector deadEnd;
		Node temp;
		JoosIO io;
		int k;
		
		io = new JoosIO();
		
		temp = (Node) ( (Vector)( mazeToPrint.elementAt(i) ) ).elementAt(j);
		
		deadEnd = (Vector) ((HashMap)(temp.getConnectedDeadEnds())).get( key );
		for(k = 0; k < deadEnd.size(); k++)
		{
			  
				  // define room name
				  io.println("room maze_dead_" + key + i + j + k);
				  io.println("");
				  // open room brace
				  io.println("{");
				  io.println("");
				  
				  // add any state variables and enter actions
				  this.writeEnterCommand(i, j, k, key );
				  
				  // add any commands
				  // we will add the look command
				  this.writeLookCommand(i, j, (Node)deadEnd.elementAt( k ) );

				  if ( key.equalsIgnoreCase( "northExit" ) )
				  {
					  // set up command you go north
					  io.println("(you, n)");
					  io.println("");
					  io.println("{");
					  io.println("");


					  // if this is the last room in the chain where the chain is of size 2 or 3
					  // then we need to make sure we print out saying we cannot move north anymore
					  if ( k != deadEnd.size() - 1)
					  {
						  io.println("'Theseus heads north';" );
						  io.println("");
						  io.println("move you from maze_dead_" + key + i + j + k + " to maze_dead_" + key + i + j + (k+1)+";" );	
					  }
					  else
					  {
						  io.println("'Theseus: Cannot go there';");
					  }
					  io.println("");	
					  io.println("}");
					  io.println("");
					  
					  // set up command you go south
					  io.println("(you, s)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  io.println("'Theseus heads south';" );
					  io.println("");
					  if ( k == 0 )
					  {
						  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasExit() )
						  {
							  io.println("move you from maze_dead_" + key + i + j + k + " to maze_exit;" );
						  }
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasEntrance() )
						  {
							  io.println("move you from maze_dead_" + key + i + j + k + " to maze_entrance;" );
						  }
						  else
						  {
							  io.println("move you from maze_dead_" + key + i + j + k + " to maze_" + i + j+";" );
						  }
					  }
					  else
					  {				  
						  io.println("move you from maze_dead_" + key + i + j + k + " to maze_dead_" + key + i + j + (k-1)+";" );
					  }
					  io.println("");	
					  io.println("}");
					  io.println("");
				  }
				  else if ( key.equalsIgnoreCase( "eastExit" ) )
				  {
					  // set up command you go east
					  io.println("(you, e)");
					  io.println("");
					  io.println("{");
					  io.println("");


					  // if this is the last room in the chain where the chain is of size 2 or 3
					  // then we need to make sure we print out saying we cannot move north anymore
					  if ( k != deadEnd.size() - 1)
					  {
						  io.println("'Theseus heads east';" );
						  io.println("");
						  io.println("move you from maze_dead_" + key + i + j + k + " to maze_dead_" + key + i + j + (k+1)+";" );
					  }
					  else
					  {
						  io.println("'Theseus: Cannot go there';");
					  }
					  io.println("");	
					  io.println("}");
					  io.println("");
					  
					  // set up command you go west
					  io.println("(you, w)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  io.println("'Theseus heads west';" );
					  io.println("");
					  if ( k == 0 )
					  {
						  if (((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasExit() )
						  {
							  io.println("move you from maze_dead_" + key + i + j + k + " to maze_exit;" );
						  }
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasEntrance() )
						  {
							  io.println("move you from maze_dead_" + key + i + j + k + " to maze_entrance;" );
						  }
						  else
						  {
							  io.println("move you from maze_dead_" + key + i + j + k + " to maze_" + i + j+";" );
						  }
					  }
					  else
					  {				  
						  io.println("move you from maze_dead_" + key + i + j + k + " to maze_dead_" + key + i + j + (k-1)+";" );
					  }
					  io.println("");	
					  io.println("}");
					  io.println("");
				  }
				  else if ( key.equalsIgnoreCase( "southExit" ) )
				  {
					  // set up command you go south
					  io.println("(you, s)");
					  io.println("");
					  io.println("{");
					  io.println("");


					  // if this is the last room in the chain where the chain is of size 2 or 3
					  // then we need to make sure we print out saying we cannot move north anymore
					  if ( k != deadEnd.size() - 1)
					  {
						  io.println("'Theseus heads south';" );
						  io.println("");
						  io.println("move you from maze_dead_" + key + i + j + k + " to maze_dead_" + key + i + j + (k+1)+";" );	
					  }
					  else
					  {
						  io.println("'Theseus: Cannot go there';");
					  }
					  io.println("");	
					  io.println("}");
					  io.println("");
					  
					  // set up command you go north
					  io.println("(you, n)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  io.println("'Theseus heads north';" );
					  io.println("");
					  if ( k == 0 )
					  {
						  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasExit() )
						  {
							  io.println("move you from maze_dead_" + key + i + j + k + " to maze_exit;" );
						  }
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasEntrance() )
						  {
							  io.println("move you from maze_dead_" + key + i + j + k + " to maze_entrance;" );
						  }
						  else
						  {
							  io.println("move you from maze_dead_" + key + i + j + k + " to maze_" + i + j+";" );
						  }
					  }
					  else
					  {				  
						  io.println("move you from maze_dead_" + key + i + j + k + " to maze_dead_" + key + i + j + (k-1)+";" );
					  }
					  io.println("");	
					  io.println("}");
					  io.println("");				
				  }
				  else if ( key.equalsIgnoreCase( "westExit" ) )
				  {
					  // set up command you go west
					  io.println("(you, w)");
					  io.println("");
					  io.println("{");
					  io.println("");

					  // if this is the last room in the chain where the chain is of size 2 or 3
					  // then we need to make sure we print out saying we cannot move north anymore
					  if ( k != deadEnd.size() - 1)
					  {
						  io.println("'Theseus heads west';" );
						  io.println("");
						  io.println("move you from maze_dead_" + key + i + j + k + " to maze_dead_" + key + i + j + (k+1)+";" );
					  }
					  else
					  {
						  io.println("'Theseus: Cannot go there';");
					  }
					  io.println("");	
					  io.println("}");
					  io.println("");
					  
					  // set up command you go east
					  io.println("(you, e)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  io.println("'Theseus heads east';" );
					  io.println("");
					  if ( k == 0 )
					  {
						  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasExit() )
						  {
							  io.println("move you from maze_dead_" + key + i + j + k + " to maze_exit;" );
						  }
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasEntrance() )
						  {
							  io.println("move you from maze_dead_" + key + i + j + k + " to maze_entrance;" );
						  }
						  else
						  {
							  io.println("move you from maze_dead_" + key + i + j + k + " to maze_" + i + j+";" );
						  }
					  }
					  else
					  {				  
						  io.println("move you from maze_dead_" + key + i + j + k + " to maze_dead_" + key + i + j + (k-1)+";" );
					  }
					  io.println("");	
					  io.println("}");
					  io.println("");
				  }
				  io.println("");
				  io.println("}");
				  io.println("");			  
			}

		
	}
	
	/**
	 * a helper method for writing pnfg code for non adjacent room connections
	 * @param out	the file to write to
	 * @param row	the row in our data structure of the node we are currently looking at
	 * @param col	the col in our data structure of the node we are currently looking at
	 * @param key	the position NESW 
	 * @param type	The type of this room entrance, regular or exit
	 * @return	true if there is a non adjacent room connected to this one
	 */
	public boolean writeNonAdjacentRooms(int row, int col, String key, String type)
	{
		JoosIO io;
		String temp;
		Integer first;
		Integer second;
		int i;
		int j;
		
		temp = null;
		i = 0;
		j = 0;
		io = new JoosIO();
		
		// check if its the non adjacent part(Braided)
		if( !((Node)((Vector)(mazeToPrint.elementAt(row))).elementAt(col)).getConnectedNonAdjacent().isEmpty() )
		{
			if( ((Node)((Vector)(mazeToPrint.elementAt(row))).elementAt(col)).getConnectedNonAdjacent().containsKey( key ) )
			{
				temp = (String)((Node)((Vector)(mazeToPrint.elementAt(row))).elementAt(col)).getConnectedNonAdjacent().get( key );
				
				// need to verify whether moving to the entrance or exit or neither
				first = new Integer(temp.substring(0, 1) );
				second = new Integer( temp.substring(1, temp.length()) );
				
				i = first.intValue();
				j = second.intValue();
				//i = Integer.valueOf( temp.substring(0, 1) );
				//j = Integer.valueOf( temp.substring(1, temp.length()) );
				
					if (type.equalsIgnoreCase("entrance") )
					{
						if (((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasExit() )
						{
							io.println("move you from maze_entrance to maze_exit;" );
						}
						else
						{
							io.println("move you from maze_entrance to maze_" + temp +";" );
						}
					}
					else if (type.equalsIgnoreCase("exit") )
					{
						if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasEntrance() )
						{
							io.println("move you from maze_exit to maze_entrance;" );							
						}
						else
						{
							io.println("move you from maze_exit to maze_" + temp +";" );	
						}
					}
					else
					{
						if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasEntrance() )
						{
							io.println("move you from maze_" + row + col + " to maze_entrance;" );
						}
						else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasExit() )
						{
							io.println("move you from maze_" + row + col + " to maze_exit;" );
						}
						else
						{
							io.println("move you from maze_" + row + col + " to maze_" + temp +";" );
						}
					}
					return true;

			}
		}
		return false;
	}
	
	/**
	 * a helper method to write pnfg code for moving towards dead ends
	 * @param out	the file to write to
	 * @param row	the row in our data structure of the node we are currently looking at
	 * @param col	the col in our data structure of the node we are currently looking at
	 * @param key	the position NESW 
	 * @param type	The type of this room entrance, regular or exit
	 * @return	true if there is a dead end room connected to this one
	 */
	public boolean writeDeadEndRooms(int row, int col, String key, String type )
	{
		JoosIO io;
		
		io = new JoosIO();
		// it must be the dead ends part
		if ( !((Node)((Vector)(mazeToPrint.elementAt(row))).elementAt(col)).getConnectedDeadEnds().isEmpty() )
		{
			if (((Node)((Vector)(mazeToPrint.elementAt(row))).elementAt(col)).getConnectedDeadEnds().containsKey( key ) )
			{
					if (type.equalsIgnoreCase("entrance") )
					{
						io.println("move you from maze_entrance to maze_dead_" + key + row + col + 0 +";" );
					}
					else if (type.equalsIgnoreCase("exit") )
					{
						io.println("move you from maze_exit to maze_dead_" + key + row + col + 0 +";" );
					}
					else
					{
						io.println("move you from maze_" + row + col + " to maze_dead_" + key + row + col + 0 +";" );
					}
			
				return true;
			}
		}
		return false;
	}
	

	/**
	 * a helper method to output on the file the look command in pnfg
	 * @param i		the current i position of the room we are looking at
	 * @param j		the current j position of the room we are looking at
	 * @param deadEnd	the deadend if we want to write look coomand for a dead end. maybe null
	 */
	public void writeLookCommand( int i, int j, Node deadEnd )
	{
		JoosIO io;
		
		io = new JoosIO();
		// we will add the look command
		io.println("(you, look)");
		  io.println("");
		  // open look command brace
		  io.println("{");
		  io.println("");
		  io.println( "room_look();");
		  io.println("");
		  // check to see if this is for a dead end room or regular room
		  if ( deadEnd != null )
		  {
			  if ( deadEnd.isNorthExit() )
			  {
				  io.println( "'Theseus: There is a doorway north';");
				  io.println("");
			  }
			  if ( deadEnd.isSouthExit() )
			  {
				  io.println( "'Theseus: There is a doorway south';");
				  io.println("");
			  }
			  if ( deadEnd.isEastExit() )
			  {
				  io.println( "'Theseus: There is a doorway east';");
				  io.println("");
			  }
			  if ( deadEnd.isWestExit() )
			  {
				  io.println( "'Theseus: There is a doorway west';");
				  io.println("");
			  }
		  }
		  else
		  {
			  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isNorthExit() )
			  {
				  io.println( "'Theseus: There is a doorway north';");
				  io.println("");
			  }
			  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isSouthExit() )
			  {
				  io.println( "'Theseus: There is a doorway south';");
				  io.println("");
			  }
			  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isEastExit() )
			  {
				  io.println( "'Theseus: There is a doorway east';");
				  io.println("");
			  }
			  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isWestExit() )
			  {
				  io.println( "'Theseus: There is a doorway west';");
				  io.println("");
			  }
		  }
		  // close look command brace
		  io.println("}");
		  io.println("");

	}
	

	/**
	 * a helper method to output in pnfg the enter action snippet
	 * @param i		the current i position of the room we are looking at
	 * @param j		the current j position of the room we are looking at
	 * @param k	the deadend index could be -1 indicating we are not looking at a dead end
	 * @param key	the key of the deadend could also be null
	 */
	public void writeEnterCommand( int i, int j, int k, String key )
	{
		JoosIO io;
		io = new JoosIO();
		  // add any state variables
		  io.println("state { visited }");
		  io.println("");
		  
		  // add any enter actions
		  io.println("enter");
		  io.println("");
		  // open enter action brace
		  io.println("{");
		  io.println("");
		  // check to see if writing enter command for a dead end node
		  if ( k != -1 && key != null )
		  {
			  io.println( "if (maze_dead_" + key + i + j + k + "!.visited)");
			  io.println("");
			  // open if brace
			  io.println("{");
			  io.println("");
			  io.println( "+?maze_dead_" + key + i + j + k + ".visited;");
			  io.println("");
		  }
		  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasEntrance() )
		  {
			  io.println( "if (maze_entrance!.visited)");
			  io.println("");
			  // open if brace
			  io.println("{");
			  io.println("");
			  io.println( "+?maze_entrance.visited;");
			  io.println("");
		  }
		  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasExit() )
		  {
			  io.println( "if (maze_exit!.visited)");
			  io.println("");
			  // open if brace
			  io.println("{");
			  io.println("");
			  io.println( "+?maze_exit.visited;");
			  io.println("");
		  }
		  else
		  {
			  io.println( "if (maze_" + i + j + "!.visited)");
			  io.println("");
			  // open if brace
			  io.println("{");
			  io.println("");
			  io.println( "+?maze_" + i + j + ".visited;");
			  io.println("");			  
		  }

		  io.println( "room_look();");
		  io.println("");
		  // close if brace
		  io.println("}");
		  io.println("");
		  // close enter action brace
		  io.println("}");
		  io.println("");	
		
	}
	/**
	 * will output to a file called mazeToPNFG.txt
	 * the maze in pnfg language
	 */
	public void toPNFG(int dimension)
	{
		//HashSet keys;
		String key;
		JoosIO io;
		int i;
		int j;
		
		i = 0;
		j = 0;
		io = new JoosIO();
		// rooms will be name according to their index (row col) in our array
		// hence room maze_10 corresponds to the node at position row 1 col 0
		// room maze_22 corresponds to the node at position row 2 and col 2
			for (i=0; i < dimension ; i++)
			{
			   for (j=0; j < dimension ; j++)
			   {
				  // first print unicursal part of the maze
				  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasEntrance() )
				  {
					  // define room name
					  io.println("room maze_entrance");
					  io.println("");
					  // open room brace
					  io.println("{");
					  io.println("");
					  
					  // add any state variables and enter actions
					  this.writeEnterCommand(i, j, -1, null);
					  
					  // add any commands
					  // we will add the look command
					  this.writeLookCommand(i, j, null);
					  
					  // set up the movement commands
					  
					  // set up command you go north
					  io.println("(you, n)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isNorthExit() )
					  {
						  io.println("'Theseus heads north';" );
						  io.println("");					  
						  // check if its the non adjacent part(Braided)
						  if ( this.writeNonAdjacentRooms(i, j, "northExit", "entrance") )
						  {
							  
						  }
						  // it must be the dead ends part
						  else if ( this.writeDeadEndRooms( i, j, "northExit", "entrance" ) )
						  {
							  
						  }
						  // if connected to unicursal part
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i-1))).elementAt(j)).isSouthExit() )
						  {
							  io.println("move you from maze_entrance to maze_" + (i-1) + j +";" );
						  }
						  else
						  {
							  io.println(" Should never get here ");
						  }
						  io.println("");	
					  }
					  io.println("}");
					  io.println("");
					  // set up command you go east
					  io.println("(you, e)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isEastExit() )
					  {
						  io.println("'Theseus heads east';" );
						  io.println("");
						  // check if its the non adjacent part(Braided)
						  if ( this.writeNonAdjacentRooms( i, j, "eastExit", "entrance") )
						  {
							  
						  }
						  // it must be the dead ends part
						  else if ( this.writeDeadEndRooms(  i, j, "eastExit", "entrance" ) )
						  {
							  
						  }
						  // if connected to unicursal part
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j+1)).isWestExit() )
						  {
							  io.println("move you from maze_entrance to maze_" + i + (j+1)+";" );
						  }
						  else
						  {
							  io.println(" Should never get here ");
						  }

						  
						  io.println("");	
					  }
					  io.println("}");
					  io.println("");
					  
					  // set up command you go south
					  io.println("(you, s)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isSouthExit() )
					  {
						  io.println("'Theseus heads south';" );
						  io.println("");
						  // check if its the non adjacent part(Braided)
						  if ( this.writeNonAdjacentRooms( i, j, "southExit", "entrance") )
						  {
							  
						  }
						  // it must be the dead ends part
						  else if ( this.writeDeadEndRooms( i, j, "southExit", "entrance" ) )
						  {
							  
						  }
						  // if connected to unicursal part
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i+1))).elementAt(j)).isNorthExit() )
						  {
							  io.println("move you from maze_entrance to maze_" + (i+1) + j+";" );
						  }
						  else
						  {
							  io.println(" Should never get here ");
						  }

						  
						  io.println("");	
					  }
					  io.println("}");
					  io.println("");
					  // set up command you go west
					  io.println("(you, w)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isWestExit() )
					  {
						  io.println("'Theseus heads west';" );
						  io.println("");
						  // check if its the non adjacent part(Braided)
						  if ( this.writeNonAdjacentRooms( i, j, "westExit", "entrance") )
						  {
							  
						  }
						  // it must be the dead ends part
						  else if ( this.writeDeadEndRooms( i, j, "westExit", "entrance" ) )
						  {
							  
						  }
						  // if connected to unicursal part
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j-1)).isEastExit() )
						  {
							  io.println("move you from maze_entrance to maze_" + i + (j-1)+";" );
						  }
						  else
						  {
							  io.println(" Should never get here ");
						  }

						  
						  io.println("");	
					  }
					  io.println("}");
					  io.println("");

				  }
				  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).gethasExit() )
				  {
					  // define room name
					  io.println("room maze_exit");
					  io.println("");
					  // open room brace
					  io.println("{");
					  io.println("");
					  // add any state variables and enter actions
					  this.writeEnterCommand(i, j, -1, null);
					  
					  // add any commands
					  // we will add the look command
					  this.writeLookCommand( i, j, null);
					  
					  // set up the movement commands
					  
					  // set up command you go north
					  io.println("(you, n)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isNorthExit() )
					  {
						  io.println("'Theseus heads north';" );
						  io.println("");					  
						  // check if its the non adjacent part(Braided)
						  if ( this.writeNonAdjacentRooms( i, j, "northExit", "exit") )
						  {
							  
						  }
						  // it must be the dead ends part
						  else if ( this.writeDeadEndRooms( i, j, "northExit", "exit" ) )
						  {
							  
						  }
						  // if connected to unicursal part
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i-1))).elementAt(j)).isSouthExit() )
						  {
							  io.println("move you from maze_exit to maze_" + (i-1) + j +";" );
						  }
						  else
						  {
							  io.println(" Should never get here ");
						  }
						  io.println("");		
					  }
					  io.println("}");
					  io.println("");
					  // set up command you go east
					  io.println("(you, e)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isEastExit() )
					  {
						  io.println("'Theseus heads east';" );
						  io.println("");
						  // check if its the non adjacent part(Braided)
						  if ( this.writeNonAdjacentRooms( i, j, "eastExit", "exit") )
						  {
							  
						  }
						  // it must be the dead ends part
						  else if ( this.writeDeadEndRooms( i, j, "eastExit", "exit" ) )
						  {
							  
						  }
						  // if connected to unicursal part
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j+1)).isWestExit() )
						  {
							  io.println("move you from maze_exit to maze_" + i + (j+1)+";" );
						  }
						  else
						  {
							  io.println(" Should never get here ");
						  }

						  
						  io.println("");
					  }
					  io.println("}");
					  io.println("");
					  
					  // set up command you go south
					  io.println("(you, s)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isSouthExit() )
					  {
						  io.println("'Theseus heads south';" );
						  io.println("");
						  // check if its the non adjacent part(Braided)
						  if ( this.writeNonAdjacentRooms( i, j, "southExit", "exit") )
						  {
							  
						  }
						  // it must be the dead ends part
						  else if ( this.writeDeadEndRooms( i, j, "southExit", "exit" ) )
						  {
							  
						  }
						  // if connected to unicursal part
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i+1))).elementAt(j)).isNorthExit() )
						  {
							  io.println("move you from maze_exit to maze_" + (i+1) + j+";" );
						  }
						  else
						  {
							  io.println(" Should never get here ");
						  }

						  
						  io.println("");	
					  }
					  io.println("}");
					  io.println("");
					  
					  // set up command you go west
					  io.println("(you, w)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isWestExit() )
					  {
						  io.println("'Theseus heads west';" );
						  io.println("");
						  // check if its the non adjacent part(Braided)
						  if ( this.writeNonAdjacentRooms( i, j, "westExit", "exit") )
						  {
							  
						  }
						  // it must be the dead ends part
						  else if ( this.writeDeadEndRooms( i, j, "westExit", "exit" ) )
						  {
							  
						  }
						  // if connected to unicursal part
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j-1)).isEastExit() )
						  {
							  io.println("move you from maze_exit to maze_" + i + (j-1)+";" );
						  }
						  else
						  {
							  io.println(" Should never get here ");
						  }

						  
						  io.println("");	
					  }
					  io.println("}");
					  io.println("");
					  
					  // set up special command up to exit maze to the court_yard
					  io.println("(you, u)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  io.println("'Theseus heads up the staircase';" );
					  io.println("");
					  io.println("move you from maze_exit to court_yard;" );
					  io.println("");	
					  io.println("}");
					  io.println("");

				  }
				  // just a regular room neither exit nor entrance
				  else
				  {
					  // define room name
					  io.println("room maze_" + i + j);
					  io.println("");
					  // open room brace
					  io.println("{");
					  io.println("");
					  
					  // add any state variables and enter actions
					  this.writeEnterCommand( i, j, -1, null);
					  
					  // add any commands
					  // we will add the look command
					  this.writeLookCommand( i, j, null);
					  
					  // set up the movement commands
					  
					  // set up command you go north
					  io.println("(you, n)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isNorthExit() )
					  {
						  io.println("'Theseus heads north';" );
						  io.println("");
						  // check if its the non adjacent part(Braided)
						  if ( this.writeNonAdjacentRooms( i, j, "northExit", "normal") )
						  {
							  
						  }
						  // it must be the dead ends part
						  else if ( this.writeDeadEndRooms( i, j, "northExit", "normal" ) )
						  {
							  
						  }	
						  // if connected to unicursal part
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i-1))).elementAt(j)).isSouthExit() )
						  {
							  // need to verify whether moving to the entrance or exit or neither
							  if ( ((Node)((Vector)(mazeToPrint.elementAt(i-1))).elementAt(j)).gethasEntrance() )
							  {
								  io.println("move you from maze_" + i + j + " to maze_entrance;" );
							  }
							  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i-1))).elementAt(j)).gethasExit() )
							  {
								  io.println("move you from maze_" + i + j + " to maze_exit;" );
							  }
							  else
							  {
								  io.println("move you from maze_" + i + j + " to maze_" + (i-1) + j+";" );
							  }
						  }
						  else
						  {
							  io.println(" Should never get here ");
						  }
				  
						  io.println("");	
					  }
					  io.println("}");
					  io.println("");
					  // set up command you go east
					  io.println("(you, e)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isEastExit() )
					  {
						  io.println("'Theseus heads east';" );
						  io.println("");
						  // check if its the non adjacent part(Braided)
						  if ( this.writeNonAdjacentRooms( i, j, "eastExit", "normal") )
						  {
							  
						  }
						  // it must be the dead ends part
						  else if ( this.writeDeadEndRooms( i, j, "eastExit", "normal" ) )
						  {
							  
						  }	
						  // if connected to unicursal part
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j+1)).isWestExit() )
						  {
							  // need to verify whether moving to the entrance or exit or neither
							  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j+1)).gethasEntrance() )
							  {
								  io.println("move you from maze_" + i + j + " to maze_entrance;" );
							  }
							  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j+1)).gethasExit() )
							  {
								  io.println("move you from maze_" + i + j + " to maze_exit;" );
							  }
							  else
							  {
								  io.println("move you from maze_" + i + j + " to maze_" + i + (j+1)+";" );
							  }
						  }
						  else
						  {
							  io.println(" Should never get here ");
						  }

						  
						  io.println("");	
					  }
					  io.println("}");
					  io.println("");
					  
					  // set up command you go south
					  io.println("(you, s)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isSouthExit() )
					  {
						  io.println("'Theseus heads south';" );
						  io.println("");
						  // check if its the non adjacent part(Braided)
						  if ( this.writeNonAdjacentRooms( i, j, "southExit", "normal") )
						  {
							  
						  }
						  // it must be the dead ends part
						  else if ( this.writeDeadEndRooms( i, j, "southExit", "normal" ) )
						  {
							  
						  }	
						  // if connected to unicursal part
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i+1))).elementAt(j)).isNorthExit() )
						  {
							  // need to verify whether moving to the entrance or exit or neither
							  if ( ((Node)((Vector)(mazeToPrint.elementAt(i+1))).elementAt(j)).gethasEntrance() )
							  {
								  io.println("move you from maze_" + i + j + " to maze_entrance;" );
							  }
							  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i+1))).elementAt(j)).gethasExit() )
							  {
								  io.println("move you from maze_" + i + j + " to maze_exit;" );
							  }
							  else
							  {
								  io.println("move you from maze_" + i + j + " to maze_" + (i+1) + j+";" );
							  }
						  }
						  else
						  {
							  io.println(" Should never get here ");
						  }

						  
						  io.println("");	
					  }
					  io.println("}");
					  io.println("");
					  // set up command you go west
					  io.println("(you, w)");
					  io.println("");
					  io.println("{");
					  io.println("");
					  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).isWestExit() )
					  {
						  io.println("'Theseus heads west';" );
						  io.println("");
						  // check if its the non adjacent part(Braided)
						  if ( this.writeNonAdjacentRooms( i, j, "westExit", "normal") )
						  {
							  
						  }
						  // it must be the dead ends part
						  else if ( this.writeDeadEndRooms( i, j, "westExit", "normal" ) )
						  {
							  
						  }	
						  // if connected to unicursal part
						  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j-1)).isEastExit() )
						  {
							  // need to verify whether moving to the entrance or exit or neither
							  if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j-1)).gethasEntrance() )
							  {
								  io.println("move you from maze_" + i + j + " to maze_entrance;" );
							  }
							  else if ( ((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j-1)).gethasExit() )
							  {
								  io.println("move you from maze_" + i + j + " to maze_exit;" );
							  }
							  else
							  {
								  io.println("move you from maze_" + i + j + " to maze_" + i + (j-1)+";" );
							  }
						  }
						  else
						  {
							  io.println(" Should never get here ");
						  }

						  
						  io.println("");	
					  }
					  io.println("}");
					  io.println("");
				  }
				  // close room brace
				  io.println("}");
				  io.println("");
				  io.println("");
				  // now add the dead end rooms for this node if any
				  
				  //keys = (HashSet)((Node)((Vector)(mazeToPrint.elementAt(i))).elementAt(j)).getConnectedDeadEnds().keySet();
				 // while( keys.iterator().hasNext() )
				 // {
				//	  key = (String) keys.iterator().next();
					//  this.writeDeadEnds(i, j, key);
				 // }
			   }
			}
	       
	}
	public void usage()
	{
		JoosIO io;
		
		io = new JoosIO();
		
		io.println("/***************************************************************/");
		io.println("**UnicursalMaze.java - convert python source to formatted html.*");
		io.println("****************************************************************");
		io.println("Usage: ");
		io.println("UnicursalMaze [-options(s)] Dimension");
		io.println("\n");
		io.println("options: ");
		io.println("-verbose = for printing out all the mazes found.");
		io.println("-random  = for printing out a random maze.");
		io.println("-pnfg  = outputs to a file, called mazeToPNFG.txt, a random maze transformed to pnfg language snippet");
		io.println("WARNING -pnfg option overwrites previous existing file called mazeToPNFG.txt");
		io.println("/***************************************************************/");
					
	}
	
	public void countNumOfMazes()
	{
		Vector maze;
		int row;
		int col;
		int k;
		int j;
		
		maze = this.initMazeArray(dimension);
		
		for ( row = 0; row < dimension; row++ )
		{
			for( col = 0; col < dimension; col++ )
			{
				// mark entrance
				((Node)((Vector)(maze.elementAt(row))).elementAt(col)).setHasEntrance( true );
				for (k = 0; k < dimension; k++)
				{
					for ( j = 0; j < dimension; j++ )
					{
						// skip if the exit and entrance are at the same node
						if ( (k != row) || (j != col) )
						{
							//mark exit	
							((Node)((Vector)(maze.elementAt(k))).elementAt(j)).setHasExit( true );
		

							this.find(maze, row, col);
							// reset vars and maze array
							maze = this.initMazeArray( dimension );
							if ( (k < ( dimension - 1 )) || (j < ( dimension - 1 )) )
							{
								((Node)((Vector)(maze.elementAt(row))).elementAt(col)).setHasEntrance( true );
							}
							mazeLength = 1;
						}
					}
				}
			}
		}
	}
	
	public void connectNonAdjacent()
	{
		Random generator;
		int row1;
		int col1;
		int row2;
		int col2;
		String wallSet;
		String wallSet2;
		int i;
		
		generator = new Random();
		wallSet = null;
		wallSet2 = null;
		row1 = 0;
		col1 = 0;
		row2 = 0;
		col2 = 0;
		
		for(i = 0; i < (dimension-1);i++)
		{
			row1 = generator.nextInt( dimension );
			col1 = generator.nextInt( dimension );
			// first find a random node that has
			// an available wall
			while( !((Node)((Vector)(mazeToPrint.elementAt(row1))).elementAt(col1)).hasFreeWall() )
			{
				row1 = generator.nextInt( dimension );
				col1 = generator.nextInt( dimension );
			} 
			
			wallSet = ((Node)((Vector)(mazeToPrint.elementAt(row1))).elementAt(col1)).setFirstFreeWall();
			
			// find a new random node not the same
			// as the one above and not adjacent to it
			// that also has a available wall
			row2 = generator.nextInt( dimension );
			col2 = generator.nextInt( dimension );
			while( !((Node)((Vector)(mazeToPrint.elementAt(row2))).elementAt(col2)).hasFreeWall() 
					|| ( ( row1 == row2 ) && ( col1 == col2 ) )
					|| ( ( row1 == row2 ) && ( col1 + 1 == col2 ) ) 
					|| ( ( row1 == row2 ) && ( col1 - 1 == col2 ) ) 
					|| ( ( row1 + 1 == row2 ) && ( col1 == col2 ) ) 
					|| ( ( row1 - 1 == row2 ) && ( col1 == col2 ) ) )
			{
				row2 = generator.nextInt( dimension );
				col2 = generator.nextInt( dimension );
			} 
			
			wallSet2 = ((Node)((Vector)(mazeToPrint.elementAt(row2))).elementAt(col2)).setFirstFreeWall();
			
			((Node)((Vector)(mazeToPrint.elementAt(row1))).elementAt(col1)).getConnectedNonAdjacent().put( wallSet,""+row2+col2 );
			((Node)((Vector)(mazeToPrint.elementAt(row2))).elementAt(col2)).getConnectedNonAdjacent().put( wallSet2,""+row1+col1 );
		}
	}
	
	public void addDeadEnds()
	{
		Random generator;
		int row;
		int col;
		int length;
		String wallSet;
		Vector nodeList;
		int k;
		int i;
		Node temp;
		
		generator = new Random();
		row = 0;
		col = 0;
		length = 0;
		wallSet = null;
		nodeList = new Vector();
		k = 0;
		
		for(i = 0; i < (dimension - 1);i++)
		{
			// first find a random node that has
			// an available wall
			row = generator.nextInt( dimension );
			col = generator.nextInt( dimension );
			while( !((Node)((Vector)(mazeToPrint.elementAt(row))).elementAt(col)).hasFreeWall() )
			{
				row = generator.nextInt( dimension );
				col = generator.nextInt( dimension );
			} 
			
			// this will randomly generate the size of our dead end branch
			// min 1 and max 3
			length = generator.nextInt( 4 );
			while( length == 0 )
			{
				length = generator.nextInt( 4 );
			} 
			
			wallSet = ((Node)((Vector)(mazeToPrint.elementAt(row))).elementAt(col)).setFirstFreeWall();
			// note wallSet will never be null since we ensure that we
			// only choose a node with a free wall
			temp = null;
			for(k = 0; k < length; k++)
			{
				temp = new Node();
			
				// recall we are connecting dead ends to
				// the first free wall we set
				// so if wallSet is north exit then that connects
				// to the south exit of the dead end branch
				// if our branch is longer than 1 we also need to
				// ensure connections properly for that
				if ( wallSet.equalsIgnoreCase( "northExit") )
				{
					temp.setSouthExit( true );
					if ( k != (length - 1) )
					{
						temp.setNorthExit( true );
					}
				}
				else if ( wallSet.equalsIgnoreCase( "southExit") )
				{
					temp.setNorthExit( true );
					if ( k != (length - 1) )
					{
						temp.setSouthExit( true );
					}
				}
				else if ( wallSet.equalsIgnoreCase( "eastExit") )
				{
					temp.setWestExit( true );
					if ( k != (length - 1) )
					{
						temp.setEastExit( true );
					}
				}
				else if ( wallSet.equalsIgnoreCase( "westExit") )
				{
					temp.setEastExit( true );
					if ( k != (length - 1) )
					{
						temp.setWestExit( true );
					}
				}
				nodeList.addElement( temp );
				
			}
			((Node)((Vector)(mazeToPrint.elementAt(row))).elementAt(col)).getConnectedDeadEnds().put(wallSet, nodeList );
			nodeList = new Vector();
			
		}		
	}
	
	public Vector cloneMaze(  Vector maze )
	{
		Vector copy;
		int i;
		int j;
		
		copy = new Vector( );
		for (i=0; i < dimension ; i++)
		{
			copy.insertElementAt(new Vector(), i);
		   for (j=0; j < dimension ; j++)
		   {
			   ((Vector)(copy.elementAt(i))).insertElementAt(new Node(), j); 
			   ((Node)((Vector)(copy.elementAt(i))).elementAt(j)).setEastExit(((Node)((Vector)(maze.elementAt(i))).elementAt(j)).isEastExit());
			   ((Node)((Vector)(copy.elementAt(i))).elementAt(j)).setHasEntrance(((Node)((Vector)(maze.elementAt(i))).elementAt(j)).gethasEntrance());
			   ((Node)((Vector)(copy.elementAt(i))).elementAt(j)).setHasExit(((Node)((Vector)(maze.elementAt(i))).elementAt(j)).gethasExit());
			   ((Node)((Vector)(copy.elementAt(i))).elementAt(j)).setNorthExit(((Node)((Vector)(maze.elementAt(i))).elementAt(j)).isNorthExit());
			   ((Node)((Vector)(copy.elementAt(i))).elementAt(j)).setSouthExit(((Node)((Vector)(maze.elementAt(i))).elementAt(j)).isSouthExit());
			   ((Node)((Vector)(copy.elementAt(i))).elementAt(j)).setWestExit(((Node)((Vector)(maze.elementAt(i))).elementAt(j)).isWestExit());
				   
		   }
		}
		
		return copy;
	}
	
	/**
	 * initializes our maze data structure
	 * @return a 2d array of Node objects representing our maze
	 */
	public Vector initMazeArray(int dimension)
	{
		Vector maze;
		int i;
		int j;
		maze = new Vector();
		
		for (i=0; i < dimension ; i++)
		{
			maze.insertElementAt( new Vector(), i);
		   for (j=0; j < dimension ; j++)
		   {
			   ((Vector)(maze.elementAt(i))).insertElementAt( new Node(), j);		   
		   }
		}
		
		return maze;
	}

	/**
	 * print out the maze in ascii characters
	 * s will represent a start node
	 * --- will represent a connection east west between nodes
	 * 0 will represent a regular node
	 * e will represent an end or exit node
	 * | will represent a connection north south between nodes
	 * 3 blank spaces in any direction represent no connection
	 * @param mazeToPrint2 The maze to be printed
	 */
	public void printMaze( Vector mazeToPrint2 )
	{
		int j;
		int i;
		JoosIO io;
		
		j = 0;
		i = 0;
		io = new JoosIO();
		io.println("KEY: Node stands for room as well");
		io.println("Reg Node: 0 ");
		io.println("Start Node: s ");
		io.println("End Node: e");
		io.println("East/West path: --- ");
		io.println("North/South path: | ");
		io.println("no path(spaces):   ");
		while( i < dimension )
		{
			io.println("");
			while( j < dimension )
			{

				if ( ((Node)((Vector)(mazeToPrint2.elementAt(i))).elementAt(j)).gethasEntrance() )
				{
					io.print("s");
				}
				else if ( ((Node)((Vector)(mazeToPrint2.elementAt(i))).elementAt(j)).gethasExit() )
				{
					io.print("e");
				}
				else
				{
					io.print("0");
				}
				if ( ((Node)((Vector)(mazeToPrint2.elementAt(i))).elementAt(j)).isEastExit() )
			    {
					io.print("-");
					io.print("-");
					io.print("-");
			    }
				else
				{
					io.print(" ");
					io.print(" ");
					io.print(" ");
				}
				
				j = j + 1;
			}
			j = 0;
			io.println("");
			while( j < dimension )
			{
				if ( ((Node)((Vector)(mazeToPrint2.elementAt(i))).elementAt(j)).isSouthExit() )
				{
					io.print("|");
					io.print(" ");
					io.print(" ");
					io.print(" ");
				}
				else
				{
					io.print(" ");
					io.print(" ");
					io.print(" ");
					io.print(" ");
				}
				j = j + 1;
			}
			j = 0;
			i = i + 1;
		}
		io.println("");
		io.println("");
	}
		

	/**
	 * our recursive function that will compute
	 * all unicursal mazes that fit dimension X dimension
	 * grid
	 * @param maze	
	 * @param row	current row we are looking at in our 2d array
	 * @param col	current col we are looking at in our 2d array
	 * @return 
	 */

	public boolean find(Vector maze, int row, int col)
	{			
		// check to see if there is an adjacent node to the north
		if ( (row - 1) >= 0 )
		{
			// check to see if it is the exit
			if ( ((Node)((Vector)maze.elementAt( row - 1 )).elementAt( col )).gethasExit() )
			{
				// check to see if it is also an entrance
				if ( ((Node)((Vector)maze.elementAt( row - 1 )).elementAt( col )).gethasEntrance() )
				{
					// verify if our maze spans the whole space
					if ( mazeLength == ( dimension * dimension )  )
					{
						// done for sake of printing maze
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setNorthExit( true );
						((Node)((Vector)maze.elementAt( row - 1 )).elementAt( col )).setSouthExit( true );
						// print out the maze
						if ( PRINT )
						{
							(new JoosIO()).println("Maze number: " + (numOfMazes+1) );
							(new JoosIO()).println("");
							this.printMaze( maze );
						}
						//numOfMazes = numOfMazes + 1;
						numOfMazes++;
						if ( RANDOM || PNFG)
						{
							if ( randomMaze == numOfMazes )
							{
								mazeToPrint = this.cloneMaze( maze );
							}
						}
						
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setNorthExit( false );
						((Node)((Vector)maze.elementAt( row - 1 )).elementAt( col )).setSouthExit( false );
					}
				}
				else
				{
					// verify if our maze spans the whole space
					if ( mazeLength == ( ( dimension * dimension ) - 1 )  )
					{
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setNorthExit( true );
						((Node)((Vector)maze.elementAt( row - 1 )).elementAt( col )).setSouthExit( true );
						// print out the maze
						if ( PRINT )
						{
							(new JoosIO()).println("Maze number: " + (numOfMazes+1) );
							(new JoosIO()).println("");
							this.printMaze( maze );
						}
						numOfMazes = numOfMazes + 1;
						if ( RANDOM || PNFG)
						{
							if ( randomMaze == numOfMazes )
							{
								mazeToPrint = this.cloneMaze( maze );
							}
						}
						
						
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setNorthExit( false );
						((Node)((Vector)maze.elementAt( row - 1 )).elementAt( col )).setSouthExit( false );
					}
				}
			}
			// if not exit check to see if the node to the north has been already visited or not
			else if ( !((Node)((Vector)maze.elementAt( row - 1)).elementAt( col )).wasVisited() )
			{
				// visit it normally and do recursive call
				((Node)((Vector)maze.elementAt( row )).elementAt( col )).setNorthExit( true );
				((Node)((Vector)maze.elementAt( row - 1 )).elementAt( col )).setSouthExit( true );
				mazeLength = mazeLength + 1;
				if ( this.find( maze, row - 1, col ) )
				{
					((Node)((Vector)maze.elementAt( row )).elementAt( col )).setNorthExit( false );
					((Node)((Vector)maze.elementAt( row - 1 )).elementAt( col )).setSouthExit( false );
					mazeLength = mazeLength - 1;
				}
			}
			else 
			{	
				
			}
		}			
		// check to see if there is an adjacent node to the east
		if ( (col + 1) < dimension )
		{
			// check to see if it is the exit
			if ( ((Node)((Vector)maze.elementAt( row )).elementAt( col + 1 )).gethasExit() )
			{
				// check to see if it also an entrance
				if ( ((Node)((Vector)maze.elementAt( row )).elementAt( col + 1 )).gethasEntrance() )
				{
					// verify if our maze spans the whole space
					if ( mazeLength == ( dimension * dimension )  )
					{
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setEastExit( true );
						((Node)((Vector)maze.elementAt( row )).elementAt( col + 1 )).setWestExit( true );
						// print out the maze
						if ( PRINT )
						{
							(new JoosIO()).println("Maze number: " + (numOfMazes+1) );
							(new JoosIO()).println("");
							this.printMaze( maze );
						}
						numOfMazes = numOfMazes + 1;
						if ( RANDOM || PNFG)
						{
							if ( randomMaze == numOfMazes )
							{
								mazeToPrint = this.cloneMaze( maze );
							}
						}
						
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setEastExit( false );
						((Node)((Vector)maze.elementAt( row )).elementAt( col + 1 )).setWestExit( false );
					}
				}
				else
				{
					// verify if our maze spans the whole space
					if ( mazeLength == ( dimension * dimension ) - 1 )
					{				
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setEastExit( true );
						((Node)((Vector)maze.elementAt( row )).elementAt( col + 1 )).setWestExit( true );
						// print out the maze
						if ( PRINT )
						{
							(new JoosIO()).println("Maze number: " + (numOfMazes+1) );
							(new JoosIO()).println("");
							this.printMaze( maze );
						}
						numOfMazes = numOfMazes + 1;
						if ( RANDOM || PNFG)
						{
							if ( randomMaze == numOfMazes )
							{
								mazeToPrint = this.cloneMaze( maze );
							}
						}
						
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setEastExit( false );
						((Node)((Vector)maze.elementAt( row )).elementAt( col + 1 )).setWestExit( false );
					}
				}
			}
			// if not exit check to see if the node to the east has been already visited or not
			else if ( !((Node)((Vector)maze.elementAt( row )).elementAt( col + 1 )).wasVisited() )
			{
				// visit it normally and do recursive call
				((Node)((Vector)maze.elementAt( row )).elementAt( col )).setEastExit( true );
				((Node)((Vector)maze.elementAt( row )).elementAt( col + 1 )).setWestExit( true );
				mazeLength = mazeLength + 1;
				if ( this.find( maze, row, col + 1 ) )
				{
					((Node)((Vector)maze.elementAt( row )).elementAt( col )).setEastExit( false );
					((Node)((Vector)maze.elementAt( row )).elementAt( col + 1 )).setWestExit( false );
					mazeLength = mazeLength - 1;
				}
			}
			else 
			{
				
			}
		}
		// check to see if there is an adjacent node to the south
		if ( ( row + 1 ) < dimension )
		{
			// check to see if that node is an exit
			if ( ((Node)((Vector)maze.elementAt( row + 1 )).elementAt( col )).gethasExit() )
			{		
				// check to see if it is also an entrance
				if ( ((Node)((Vector)maze.elementAt( row + 1 )).elementAt( col )).gethasEntrance() )
				{
					// verify if our maze spans the whole space
					if ( mazeLength == ( dimension * dimension )  )
					{
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setSouthExit( true );
						((Node)((Vector)maze.elementAt( row + 1 )).elementAt( col )).setNorthExit( true );
						// print out the maze
						if ( PRINT )
						{
							(new JoosIO()).println("Maze number: " + (numOfMazes+1) );
							(new JoosIO()).println("");
							this.printMaze( maze );
						}
						numOfMazes = numOfMazes + 1;
						if ( RANDOM || PNFG)
						{
							if ( randomMaze == numOfMazes )
							{
								mazeToPrint = this.cloneMaze( maze );
							}
						}
					
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setSouthExit( false );
						((Node)((Vector)maze.elementAt( row + 1 )).elementAt( col )).setNorthExit( false );
					}
				}
				else
				{
					// verify if our maze spans the whole space
					if ( mazeLength == ( dimension * dimension ) - 1 )
					{
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setSouthExit( true );
						((Node)((Vector)maze.elementAt( row + 1 )).elementAt( col )).setNorthExit( true );
						// print out the maze
						if ( PRINT )
						{
							(new JoosIO()).println("Maze number: " + (numOfMazes+1) );
							(new JoosIO()).println("");
							this.printMaze( maze );
						}
						numOfMazes = numOfMazes + 1;
						if ( RANDOM || PNFG)
						{
							if ( randomMaze == numOfMazes )
							{
								mazeToPrint = this.cloneMaze( maze );
							}
						}
					
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setSouthExit( false );
						((Node)((Vector)maze.elementAt( row + 1 )).elementAt( col )).setNorthExit( false );
					}
				}
			}
			// check to see if the node to the south has been already visited or not
			else if ( !((Node)((Vector)maze.elementAt( row + 1 )).elementAt( col )).wasVisited() )
			{
				((Node)((Vector)maze.elementAt( row )).elementAt( col )).setSouthExit( true );
				((Node)((Vector)maze.elementAt( row + 1 )).elementAt( col )).setNorthExit( true );
				mazeLength = mazeLength + 1;
				if ( this.find( maze, row + 1, col ) )
				{
					((Node)((Vector)maze.elementAt( row )).elementAt( col )).setSouthExit( false );
					((Node)((Vector)maze.elementAt( row + 1 )).elementAt( col )).setNorthExit( false );
					mazeLength = mazeLength - 1;
				}

			}
			else 
			{
		
			}
		}
		// check to see if there is an adjacent node to the west
		if ( ( col - 1 ) >= 0 )
		{
			if ( ((Node)((Vector)maze.elementAt( row )).elementAt( col - 1 )).gethasExit() )
			{
				if ( ((Node)((Vector)maze.elementAt( row )).elementAt( col - 1 )).gethasEntrance() )
				{
					// verify if our maze spans the whole space
					if ( mazeLength == ( dimension * dimension )  )
					{
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setWestExit( true );
						((Node)((Vector)maze.elementAt( row )).elementAt( col - 1 )).setEastExit( true );
						// print out the maze
						if ( PRINT )
						{
							(new JoosIO()).println("Maze number: " + (numOfMazes+1) );
							(new JoosIO()).println("");
							this.printMaze( maze );
						}
						numOfMazes = numOfMazes + 1;
						if ( RANDOM || PNFG)
						{
							if ( randomMaze == numOfMazes )
							{
								mazeToPrint = this.cloneMaze( maze );
							}
						}
				
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setWestExit( false );
						((Node)((Vector)maze.elementAt( row )).elementAt( col - 1 )).setEastExit( false );
					}
				}
				else
				{
					// verify if our maze spans the whole space
					if ( mazeLength == ( dimension * dimension ) - 1 )
					{
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setWestExit( true );
						((Node)((Vector)maze.elementAt( row )).elementAt( col - 1 )).setEastExit( true );
						// print out the maze
						if ( PRINT )
						{
							(new JoosIO()).println("Maze number: " + (numOfMazes+1) );
							(new JoosIO()).println("");
							this.printMaze( maze );
						}
						numOfMazes = numOfMazes + 1;
						if ( RANDOM || PNFG)
						{
							if ( randomMaze == numOfMazes )
							{
								mazeToPrint = this.cloneMaze( maze );
							}
						}
		
						((Node)((Vector)maze.elementAt( row )).elementAt( col )).setWestExit( false );
						((Node)((Vector)maze.elementAt( row )).elementAt( col - 1 )).setEastExit( false );
					}
				}
			}
			else if ( !((Node)((Vector)maze.elementAt( row )).elementAt( col - 1 )).wasVisited() )
			{
				((Node)((Vector)maze.elementAt( row )).elementAt( col )).setWestExit( true );
				((Node)((Vector)maze.elementAt( row )).elementAt( col - 1 )).setEastExit( true );
				mazeLength = mazeLength + 1;
				if ( this.find( maze, row, col - 1 ) )
				{
					((Node)((Vector)maze.elementAt( row )).elementAt( col )).setWestExit( false );
					((Node)((Vector)maze.elementAt( row )).elementAt( col - 1 )).setEastExit( false );
					mazeLength = mazeLength - 1;
				}
			}
			else 
			{

			}	
		}
		
		return true;
	}
}

