


public class LispInteger extends Expr {

	protected int number;
	
	public LispInteger(int num) {
		super();
		number = num;
	}
	
	public int getInt() {
		return number;
	}
	
	public String toString() {
		return number + "";
	}
}
