import joos.lib.*;


/*
 * Since Befunge is a STACK BASED language we decided to implement our own stack.
 * This helps buff up some argument passing, as well polymorphic/inhertance behaviours.
 */
public class Node {
	
	protected Node rightNode;
	protected Node leftNode;
	protected Node topNode;
	protected Node bottomNode;
	
	public Node()
	{
		super();
		rightNode = null;
		leftNode = null;
		topNode = null;
		bottomNode = null;
	}
	
	public void setLeftNode(Node input)
	{
		leftNode = input;
	}
	
	public Node getLeftNode()
	{
		return leftNode;
	}
	
	public void setRightNode(Node input)
	{
		rightNode = input;
	}
	
	public Node getRightNode()
	{
		return rightNode;
	}
	
	public void setTopNode(Node input)
	{
		topNode = input;
	}
	
	public Node getTopNode()
	{
		return topNode;
	}
	
	public void setBottomNode(Node input)
	{
		bottomNode = input;
	}
	
	public Node getBottomNode()
	{
		return bottomNode;
	}
	
	public Node getNextNode()
	{
		return rightNode;
	}
	
	public void setNextNode(Node input)
	{
	    rightNode = input;
	}
	
	//public abstract void setData(Object obj);
	//public abstract Object getData();
	
}
