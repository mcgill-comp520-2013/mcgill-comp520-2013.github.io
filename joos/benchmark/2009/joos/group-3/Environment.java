


public class Environment {
	
	protected MyList env;
	protected String current;
	
	public Environment() {
		super();
		env = null;
		current = "";
	}
	
	public Environment(String expression) {
		super();
		env = null;
		current = expression;
	}
	
	// reverse a list
	public MyList reverse(MyList reverse) {
		MyList theList;
		theList = null;
		while(reverse != null) {
			theList = new MyList(reverse.getHead(), theList);
			reverse = reverse.getTail();
		}
		return theList;
	}
	
	// compute a to the b
	public int pow(int a, int b) {
		int c;
		c = 1;
		while(b>0) {
			c = c * a;
			b = b - 1;
		}
		return c;
	}
	
	// transform a string representing an int into an int
	public int decode(String number) {
		int ans;
		int length;
		int i;
		ans = 0;
		length= number.length();
		for(i = 1; i <= length; i++) {
			char digit;
			int cur;
			digit = number.charAt(length - i);
			if(digit == '0') {
				cur = 0;
			} else if(digit == '1') {
				cur = 1;
			} else if(digit == '2') {
				cur = 2;
			} else if(digit == '3') {
				cur = 3;
			} else if(digit == '4') {
				cur = 4;
			} else if(digit == '5') {
				cur = 5;
			} else if(digit == '6') {
				cur = 6;
			} else if(digit == '7') {
				cur = 7;
			} else if(digit == '8') {
				cur = 8;
			} else {
				cur = 9;
			}
			ans = ans + cur * this.pow(10, i - 1);
		}
		return ans;
	}
	
	// takes the next expression of current and transforms it into an Expr, cutting it away from current
	public Expr next() {
		Expr ans;
		if(current.charAt(0) == '(') {
			current = current.substring(1,current.length());
			ans = this.goThrough();
		} else if(current.length() >= 4 && current.substring(0,4).equalsIgnoreCase("plus")) {
			current = current.substring(4, current.length());
			ans = new Plus();
		} else {
			int i;
			int number;
			LispInteger num;
			i = 0;
			while(current.charAt(i) != ' ' && current.charAt(i) != ')') {
				i++;
			}
			number = this.decode(current.substring(0, i));
			current = current.substring(i, current.length());
			num = new LispInteger(number);
			ans = num;
		}
		return ans;
	}
	
	// transforms the exprs of current until the parenthesis into Exprs
	public Expr goThrough() {
		MyList reverse;
		current = current.substring(4,current.length());
		reverse = null;
		while(current.charAt(0) != ')') {
			current = current.substring(1, current.length());
			reverse = new MyList(this.next(), reverse);
		}
		current = current.substring(1, current.length());
		return new MyList(new Plus(), this.reverse(reverse));
	}
	
	// evaluates expr
	public Expr eval(Expr expr) {
		if(expr instanceof MyList) {
			MyList list;
			MyList tail;
			Function function;
			MyList reverse;
			MyList arguments;
			list = (MyList)expr;
			tail = list.getTail();
			function = (Function)this.eval(list.getHead());
			reverse = null;
			while(tail != null) {
				reverse = new MyList(this.eval(tail.getHead()), reverse);
				tail = tail.getTail();
			}
			arguments = this.reverse(reverse);
			return this.apply(function, arguments);
		}
		return expr;
	}
	
	// applies function to arguments
	public Expr apply(Function function, MyList arguments) {
		return function.apply(arguments);
	}
	
}
