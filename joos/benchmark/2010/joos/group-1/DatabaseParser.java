import java.util.StringTokenizer;
import java.util.Vector;
import java.lang.Integer;
import joos.lib.JoosRandomAccessFile;

import joos.lib.*;
//import joos.extern.*;

public class DatabaseParser {
    public DatabaseParser() {
	super();
    }

    public Vector parse(JoosRandomAccessFile fIn) {
	StringTokenizer st;
	Vector pokemon;
	Integer integer;
	String currentLine;
	String currentToken;
	String name, type;
	int attk, spcAttk, hp;

	currentLine = fIn.readLine();
	pokemon = new Vector(151);
	// For each line of the database...
	while(currentLine != null) {
	    st = new StringTokenizer(currentLine);
	    // read in the stats...
	    name = st.nextToken(" ");
	    type = st.nextToken(" ");
	    // ignore 2nd type...
	    st.nextToken(" ");

	    integer = new Integer(st.nextToken(" "));
	    hp = integer.intValue();

	    integer = new Integer(st.nextToken(" "));
	    attk = integer.intValue();

	    integer = new Integer(st.nextToken(" "));
	    spcAttk = integer.intValue();

	    // and create the pokemon.
	    pokemon.addElement(new Pokemon(name, type, attk, spcAttk, hp));
	    currentLine = fIn.readLine();
	}
	return pokemon;
    }
}
