


public class Function extends Expr {
	
	public Function() {
		super();
	}
	
	public LispInteger apply(MyList arguments) {
		return new LispInteger(0);
	}
}
