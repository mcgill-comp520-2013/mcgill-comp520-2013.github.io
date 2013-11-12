import java.util.StringTokenizer;
import java.util.Vector;
import joos.lib.JoosRandomAccessFile;

import joos.lib.*;
//import joos.extern.*;

public class RosterParser {
    protected Team teamOne;
    protected Team teamTwo;

    public RosterParser() {
	super();
	teamOne = new Team(1);
	teamTwo = new Team(2);
    }

    public Team getTeamOne() {
	return teamOne;
    }

    public Team getTeamTwo() {
	return teamTwo;
    }

    public Pokemon getPokemon(Vector repo, String pokemon) {
	int index;
	Pokemon p;
        index = 0; 
	while(index < repo.size()){
	    p = (Pokemon) repo.elementAt(index);
	    if (pokemon.equals(p.getName())) {
		return p;
	    }
            index = index+1;
	}
	return null;
    }

    public int parse(JoosRandomAccessFile fIn, Vector repo) {
	StringTokenizer st;
	String currentLine;
	Vector teamOneRoster;
	Vector teamTwoRoster;
	String name;
	Pokemon p;
	JoosIO f;
	
	f = new JoosIO();
	teamOneRoster = new Vector(6);
	teamTwoRoster = new Vector(6);

	// Line 1 of the input.
	currentLine = fIn.readLine();
	st = new StringTokenizer(currentLine);
	// For each name...
	while (st.hasMoreTokens()) {
	    name = st.nextToken(",");
	    // look for the right pokemon...
	    p = this.getPokemon(repo, name);
	    // quit if it's not there...
	    if (p == null) {
		f.println("Error: invalid name: " + name + "!");
		return 1;
	    }
	    // and add it to the roster if it is.
	    teamOneRoster.addElement(p.copy());
	}
	if (teamOneRoster.size() != 6) {
	    f.println("Error: should have 6 pokemon!");
	    return 1;
	}
	// Attach the roster to team 1.
	teamOne.setPokemon(teamOneRoster);

	// Line 2 of the input.
	currentLine = fIn.readLine();
	st = new StringTokenizer(currentLine);
	// For each name...
	while (st.hasMoreTokens()) {
	    name = st.nextToken(",");
	    // look for the right pokemon...
	    p = this.getPokemon(repo, name);
	    // quit if it's not there...
	    if (p == null) {
		f.println("Error: invalid name: " + name + "!");
		return 1;
	    }
	    // and add it to the roster if it is.
	    teamTwoRoster.addElement(p.copy());
	}
	if (teamTwoRoster.size() != 6) {
	    f.println("Error: should have 6 pokemon!");
	    return 1;
	}
	// Attach the roster to team 2.
	teamTwo.setPokemon(teamTwoRoster);

	return 0;
    }
	
}
