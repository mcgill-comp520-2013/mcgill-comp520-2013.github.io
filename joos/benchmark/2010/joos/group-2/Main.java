import joos.lib.*;

public class Main
{
	public Main()
	{
		super();
	}
	
	public String readAll()
	{
		JoosIO f;
		String all;
		boolean read;
		int i;
		int lineno;
		
		f = new JoosIO();
		i = 0;
		all = "";
		lineno = 0;
		
		read = true;
		
		while (read) {
			String line;
			line = f.readLine();
		
			if (line == null) {
				read = false;
			}
			else
				all = all + line + "\n";
				
			lineno++;
		}
		
		return all;
		
	}
	
	public static void main(String argv[])
	{
		JoosIO f;
		WigGenerator g;
		Main m;
		String tex;
		
		m = new Main();
		tex = m.readAll();
		
		g = new WigGenerator(tex);
		g.generate();
		
		f = new JoosIO();
		f.println(g.toString());
		
		return;
	}
}