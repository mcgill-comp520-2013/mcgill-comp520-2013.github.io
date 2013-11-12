
public class Plus extends Function {
	

	public Plus() {
		super();
	}
	

	public LispInteger apply(MyList arguments) {
		int first;
		int next;
		LispInteger ans;
		if (arguments == null) {
			ans = new LispInteger(0);
		} else {
			first = ((LispInteger) arguments.getHead()).getInt();
			next = this.apply(arguments.getTail()).getInt();
			ans = new LispInteger(first + next);
		}
		return ans;
	}
	

	public String toString() {
		return "plus";
	}
}
