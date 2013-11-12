
public class MyList extends Expr {
	

	protected Expr	head;
	
	protected MyList	tail;
	
	
	public MyList() {
		super();
		head = null;
		tail = null;
	}
	

	public MyList(Expr head1, MyList tail1) {
		super();
		head = head1;
		tail = tail1;
	}
	

	public Expr getHead() {
		return head;
	}
	

	public MyList getTail() {
		return tail;
	}
	

	public String toString() {
		String tailString;
		String ans;
		if (tail == null) {
			ans = "(" + head.toString() + ")";
		} else {
			tailString = tail.toString();
			ans = "(" + head.toString() + " " + tailString.substring(1, tailString.length() - 1) + ")";
		}
		return ans;
	}
}
