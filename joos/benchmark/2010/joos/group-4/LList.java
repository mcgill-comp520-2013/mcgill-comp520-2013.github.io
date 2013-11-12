import joos.lib.*;

public class LList
{
	protected Node head;

	public LList()
	{
		super();
		head = null;
	}

	public Node getHead()
	{
		return head;
	}

	public void push(Object d)
	{
		head = new Node(d, head);
	}

	public Object pop()
	{
		Node ret;

		ret = head;
		if (head != null) {
			head = head.getNext();
		}
		return ret.getData();
	}

	public void insertLast(Object d)
	{
		Node tmp;

		if (head == null) {
			head = new Node(d, null);
		} else {
			tmp = head;
			while (tmp.getNext() != null) {
				tmp = tmp.getNext();
			}
			tmp.setNext(new Node(d, null));
		}
	}

	public Object getNthElement(int n)
	{
		int i;
		Node tmp;

		tmp = head;
		i = 1;
		while (i < n) {
			tmp = tmp.getNext();
			i = i + 1;
		}
		return tmp.getData();
	}

	public boolean isEmpty()
	{
		return head == null;
	}
}
