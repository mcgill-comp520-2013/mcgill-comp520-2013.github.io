import joos.lib.*;

public class Node
{
	protected Object data;
	protected Node next;

	public Node(Object d, Node n)
	{
		super();
		data = d;
		next = n;
	}

	public Object getData()
	{
		return data;
	}

	public Node getNext()
	{
		return next;
	}

	public void setData(Object d)
	{
		data = d;
	}

	public void setNext(Node n)
	{
		next = n;
	}
}
