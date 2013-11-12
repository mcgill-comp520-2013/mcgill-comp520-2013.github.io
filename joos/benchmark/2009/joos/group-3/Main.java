/* This program is a very limited Lisp interpreter. It only accepts integers and the function plus (written as "plus"). It doesn't handle syntax errors. */


import joos.lib.*;

public class Main {
	

	public Main() {
		super();
	}
	

	public static void main(String[] arguments) {
		String argument;
		Environment env;
		Expr next;
		JoosIO f;
		int i;
		f = new JoosIO();
		argument = f.readLine();
		env = new Environment(argument);
		next = env.next();
		f.println(next.toString());
		f.println("The answer is " + env.eval(next) + ".");
	}
}