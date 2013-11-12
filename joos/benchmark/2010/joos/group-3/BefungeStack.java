
public class BefungeStack {
	
	protected Node startNode;
	
	public BefungeStack()
	{
		super();
		startNode = null;
	}
	
	public void push(Node input)
	{
		input.setNextNode(startNode);
		startNode = input;
	}
	public void pushChar(char input)
	{
		CharNode tmp;
		tmp = new CharNode();
		tmp.setChar(input);
		
		if(startNode == null)
		{
			tmp.setNextNode(null);
			startNode = tmp;
		}
		else
		{
			tmp.setNextNode(startNode);
			startNode = tmp;
		}
	}
	
	public void pushInt(int input)
	{
		IntNode tmp;
		tmp = new IntNode();
		tmp.setInt(input);
		
		if(startNode == null)
		{
			tmp.setNextNode(null);
			startNode = tmp;
		}
		else
		{
			tmp.setNextNode(startNode);
			startNode = tmp;
		}
	}
	
	public Node pop()
	{
		Node prevNode;
		prevNode = startNode;
		if(startNode != null)
			startNode = startNode.getNextNode();
		
		return prevNode;
	}

}
