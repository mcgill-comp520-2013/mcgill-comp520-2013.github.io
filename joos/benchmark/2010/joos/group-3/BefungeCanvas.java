import joos.lib.JoosString;



public class BefungeCanvas {
	
	protected Node startNode;
	protected Node curHead;
	public BefungeCanvas()
	{
		super();
		startNode = null;
	}
	
	public char getNextChar()
	{
		if(curHead != null)
			return ((CharNode)curHead).getChar();
		else
			return ((CharNode)startNode).getChar();
	}
	
	public boolean moveLeftCanavas()
	{
		if(curHead == null)
			curHead = startNode.getLeftNode();
		else
			curHead = curHead.getLeftNode();
		
		if(curHead == null)
			return false;
		else
			return true;
	}
	
	public boolean moveRightCanavas()
	{
		if(curHead == null)
			curHead = startNode.getRightNode();
		else
			curHead = curHead.getRightNode();
		
		if(curHead == null)
			return false;
		else
			return true;
	}
	
	public boolean moveTopCanavas()
	{
		if(curHead == null)
			curHead = startNode.getTopNode();
		else
			curHead = curHead.getTopNode();
		
		if(curHead == null)
			return false;
		else
			return true;
	}
	
	public boolean moveBottomCanavas()
	{
		if(curHead == null)
			curHead = startNode.getBottomNode();
		else
			curHead = curHead.getBottomNode();
		
		if(curHead == null)
			return false;
		else
			return true;
	}
	
	public void addLineToCanavas(String inputLine)
	{
		int i;
		if(startNode == null)
		{
			
			Node CurrentLocation;
			CurrentLocation = startNode;
			
			for(i = 0; i < 80; i++ )
			{
				char charVal;
				Node tmpNode;
				tmpNode = new CharNode();
				if(i < inputLine.length())
				{
					charVal = inputLine.charAt(i);
					((CharNode)tmpNode).setChar(charVal);
				}
				else
					((CharNode)tmpNode).setChar('0');
						
				if(startNode == null)
				{
					startNode = tmpNode;
					CurrentLocation = tmpNode;
				}
				else
				{
					CurrentLocation.setRightNode(tmpNode);
					tmpNode.setLeftNode(CurrentLocation);
					CurrentLocation = tmpNode;
				}
			}
		}
		else
		{
			Node CurrentLocation;
			Node PrevLocation;
			PrevLocation = startNode;
			CurrentLocation = null;
			
			while(PrevLocation.getBottomNode() != null)
				PrevLocation = PrevLocation.getBottomNode();
			
			for(i = 0; i < 80; i++)
			{
				char charVal;
				Node tmpNode;
				tmpNode = new CharNode();
				if(i < inputLine.length())
				{
					charVal = inputLine.charAt(i);
					((CharNode)tmpNode).setChar(charVal);
				}
				else
					((CharNode)tmpNode).setChar('0');
				
				if(CurrentLocation == null)
				{
					PrevLocation.setBottomNode(tmpNode);
					tmpNode.setTopNode(PrevLocation);
					CurrentLocation = tmpNode;
					PrevLocation = PrevLocation.getRightNode();
				}
				else
				{
					CurrentLocation.setRightNode(tmpNode);
					tmpNode.setLeftNode(CurrentLocation);
					PrevLocation.setBottomNode(tmpNode);
					tmpNode.setTopNode(PrevLocation);
					PrevLocation = PrevLocation.getRightNode();
					CurrentLocation = tmpNode;
				}
			}
		}
	}

}
