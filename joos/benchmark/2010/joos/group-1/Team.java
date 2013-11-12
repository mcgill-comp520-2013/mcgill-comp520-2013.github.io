import java.util.*;

public class Team {
	
	protected Vector pokemon;
	protected int id;
	
	public Team(int pId) {
		super();
		id = pId;
		pokemon = new Vector(6);
	}
	
	public int getID() {
		return id;
	}
	
	public Pokemon getNextAlivePokemon(){
		Pokemon p;
		int i;
		i = 0;
		while (i < pokemon.size()){
		
			p = (Pokemon) pokemon.elementAt(i);
			if(p.isAlive()) {
				return p;
			}
			i=i+1;
		}
		return null;
	}
	
	public void setPokemon(Vector pkmn){
		pokemon = pkmn;
	}
	
	public void resetPokemon() {
		Pokemon p;
		int i;
		i = 0; 
		while(i < pokemon.size()){
			p = (Pokemon) pokemon.elementAt(i);
			p.reset();
		 	i=i+1;
		}
	}
}
