import java.util.*;
import joos.lib.*;

public class Battle {
	
	protected Team team1;
	protected Team team2;
	
	public Battle(Team t1, Team t2) {
		super();
		team1 = t1;
		team2 = t2;
	}
	
	public static void main(String args[]) {
		JoosIO io;
		JoosRandomAccessFile rFile;
		JoosRandomAccessFile dbFile;
		Battle b;
		DatabaseParser dbParser;
		RosterParser rParser;
		Team t1;
		Team t2;
		Vector pokemonRepository;
		Team winner;
		
		// Set up the pokemon repository.
		dbFile = new JoosRandomAccessFile("poke.db", "r");
		dbParser = new DatabaseParser();
		pokemonRepository = dbParser.parse(dbFile);
		
		// Set up the rosters for the teams.
		rFile = new JoosRandomAccessFile("/dev/stdin", "r");
		rParser = new RosterParser();
		rParser.parse(rFile, pokemonRepository);
		
		// Get our two teams from the roster parser.
		t1 = rParser.getTeamOne();
		t2 = rParser.getTeamTwo();
		
		// Do the battle.
		io = new JoosIO();
		io.println("BATTLE BEGIN!");
		b = new Battle(t1, t2);
		
		winner = b.doBattle();
		
		if(winner == null) {
			io.println("DRAW.");
		} else {
			io.println("TEAM " + winner.getID() + " WON!");
		}
	}
	
	public Team doBattle() {
		JoosIO io;
		Pokemon p1;
		Pokemon p2;
		Team t1;
		Team t2;
		int effective;
		
		t1 = team1;
		t2 = team2;
		
		io = new JoosIO();
		team1.resetPokemon();
		team2.resetPokemon();
		
		// Have both teams battle.
		while(t1.getNextAlivePokemon() != null 
				&& t2.getNextAlivePokemon() != null){
			// Get the current alive pokemon.
			p1 = t1.getNextAlivePokemon();
			p2 = t2.getNextAlivePokemon();
			
			// Have both pokemon choose their actions.
			p1.chooseNextAction();
			io.print("Team1's " + p1.getName() + " " + p1.getAction());
			if(p1.getAction() == "attacks" || p1.getAction() == "special attacks") {
				effective = p1.getTypeModifier(p2);
				if(effective == 4) {
					io.print(" - it's super effective!");
				} else if (effective == 1) {
					io.print(" - it's not very effective...");
				} else if (effective == 0) {
					io.print(" - the opponent is immune!");
				}
			}
			io.println("");
			
			p2.chooseNextAction();
			io.print("Team2's " + p2.getName() + " " + p2.getAction());
			if(p2.getAction().equals("attacks") || p2.getAction().equals("special attacks")) {
				effective = p2.getTypeModifier(p1);
				if(effective == 4) {
					io.print(" - it's super effective!");
				} else if (effective == 1) {
					io.print(" - it's not very effective...");
				} else if (effective == 0) {
					io.print(" - the opponent is immune!");
				}
			}
			io.println("");
			
			// Apply the actions.
			p1.applyAction(p2);
			p2.applyAction(p1);
			
			// Test for aliveness.
			io.println("Team1's " + p1.getName() + " HP: " + p1.getHP());
			if(p1.getHP().equals("0")) {
				io.println(p1.getName() + " faints.");
			}
			io.println("Team2's " + p2.getName() + " HP: " + p2.getHP());
			if(p2.getHP().equals("0")) {
				io.println(p2.getName() + " faints.");
			}
			
			io.println("");
		}
		
		// Determine the winner.
		if(t1.getNextAlivePokemon() == null
				&& t2.getNextAlivePokemon() == null) {
			return null;
		} else if (t1.getNextAlivePokemon() != null 
				&& t2.getNextAlivePokemon() == null) {
			return t1;
		} else {
			return t2;
		}
	}
}
