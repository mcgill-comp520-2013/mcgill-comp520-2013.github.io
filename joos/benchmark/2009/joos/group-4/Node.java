
import java.util.HashMap;

	public class Node 
	{
		// flag indicating if there is an exit to the north
		protected boolean northExit;

		// flag indicating if there is an exit to the east
		protected boolean eastExit;

		
		// flag indicating if there is an exit to the south	 
		protected boolean southExit;

		// flag indicating if there is an exit to the west
		protected boolean westExit;
		
		//flag indicating if this node is the entrance
		protected boolean hasEntrance;

		//flag indicating if this node is the exit
		protected boolean hasExit;
		
		 // a map of all non adjacent nodes that connect
		 // to this node. may be empty this is mapping
		 //which wall it connects to to which room
		 //where room is represented as ij as a string where
		 //i is the row of the room and j is the col in our
		 //2d array
		// <string string>
		protected HashMap connectedNonAdjacent;
		
		 //an array list of potential set of rooms connected
		 //to it that form a dead end. May be empty
		// < string Vector<node>>
		protected HashMap connectedDeadEnds;
		

		public Node()
		{
			super();
			northExit = false;
			eastExit = false;
			southExit = false;
			westExit = false;
			hasEntrance = false;
			hasExit = false;	
			
			connectedNonAdjacent = new HashMap();
			connectedDeadEnds = new HashMap();
		}
		

		public Node( boolean hasEntrance, boolean hasExit)
		{
			super();
			northExit = false;
			eastExit = false;
			southExit = false;
			westExit = false;
			hasEntrance = hasEntrance;
			hasExit = hasExit;
			
			connectedNonAdjacent = new HashMap();
			connectedDeadEnds = new HashMap();
			
		}
		
		public HashMap getConnectedDeadEnds()
		{
			return connectedDeadEnds;
		}
		
		public HashMap getConnectedNonAdjacent()
		{
			return connectedNonAdjacent;
		}
		
		 // This method will find the first wall
		 // that is not set and set it to true
		 // helper method used to create connections
		 // to non adjacent nodes
		 // @return the wall that was set
		public String setFirstFreeWall()
		{
			if (!northExit)
			{
				northExit = true;
				return "northExit";
			}
			else if( !eastExit )
			{
				eastExit = true;
				return "eastExit";
			}
			else if( !southExit )
			{
				southExit = true;
				return "southExit";
			}
			else if( !westExit )
			{
				westExit = true;
				return "westExit";
			}
			
			return null;
		}

		public boolean hasFreeWall()
		{
			return (!northExit || !eastExit || !southExit || !westExit);
		}
		

		public boolean wasVisited()
		{
			return (northExit || eastExit || southExit || westExit || hasEntrance);
		}

	
		public boolean isEastExit() {
			return eastExit;
		}

	
		public void setEastExit(boolean eastExit2) {
			eastExit = eastExit2;
		}

	
		public boolean isNorthExit() {
			return northExit;
		}

	
		public void setNorthExit(boolean northExit2) {
			northExit = northExit2;
		}

	
		public boolean isSouthExit() {
			return southExit;
		}

	
		public void setSouthExit(boolean southExit2) {
			southExit = southExit2;
		}

	
		public boolean isWestExit() {
			return westExit;
		}

	
		public void setWestExit(boolean westExit2) {
			westExit = westExit2;
		}

	
		public boolean gethasEntrance() {
			return hasEntrance;
		}

		
		public void setHasEntrance(boolean hasEntrance2) {
			hasEntrance = hasEntrance2;
		}

		
		public boolean gethasExit() {
			return hasExit;
		}

		
		public void setHasExit(boolean hasExit2) {
			hasExit = hasExit2;
		}		
	}
