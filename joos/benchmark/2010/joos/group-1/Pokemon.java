import java.util.*;
import joos.lib.JoosRandom;

public class Pokemon {
	// all the possible types for pokemon (since we can't have static fields in JOOS)
	protected int NORMAL;
	protected int FIRE;
	protected int WATER;
	protected int ELECTRIC;
	protected int GRASS;
	protected int ICE;
	protected int FIGHTING;
	protected int POISON;
	protected int GROUND;
	protected int FLYING;
	protected int PSYCHIC;
	protected int BUG;
	protected int ROCK;
	protected int GHOST;
	protected int DRAGON;
	protected int DARK;
	protected int STEEL;
	
	// all possible actions.
	protected int ATTACK;
	protected int DEFEND;
	protected int SPECIAL_ATTACK;
	
	// this instance's attributes.
	protected String myName;
	protected int myType;
	protected Team myTeam;
	// static battle attributes
	protected int atk;
	protected int spcAtk;
	
	// current hitpoints.
	protected int myHP;
	protected int myAction; // see "all possible actions"
	protected int currentHP;
	
	protected JoosRandom r;
	public Pokemon(String pName, String pType, int pAtk, int pSpcAtk, int pHP) {
		super();
		
		r = new JoosRandom(1);
		
		// all the possible types for pokemon (since we can't have static fields in JOOS)
		NORMAL = 1;
		FIRE = 2;
		WATER = 3;
		ELECTRIC = 4;
		GRASS = 5;
		ICE = 6;
		FIGHTING = 7;
		POISON = 8;
		GROUND = 9;
		FLYING = 10;
		PSYCHIC = 11;
		BUG = 12;
		ROCK = 13;
		GHOST = 14;
		DRAGON = 15;
		DARK = 16;
		STEEL = 17;
		
		// all possible actions.
		ATTACK = 1;
		DEFEND = 2;
		SPECIAL_ATTACK = 3;
		
		myName = pName;
		myType = this.mapStringToType(pType);
		myTeam = null;
		atk = pAtk;
		spcAtk = pSpcAtk;
		myHP = pHP;
		currentHP = myHP;
	}
	
	public Pokemon copy() {
		Pokemon newP;
		newP = new Pokemon(myName, "Normal", atk, spcAtk, myHP);
		//newP.myType = myType;
		newP.setType(myType);
		return newP;
	}
	
	public void setType(int type) {
		myType = type;
	}
	
	public void setTeam(Team t) {
		myTeam = t;
	}
	
	public Team getTeam() {
		return myTeam;
	}
	
	public int mapStringToType(String type) {
		if(type.equals("Normal")) {
			return NORMAL;
		} else if (type.equals("Fire")) {
			return FIRE;
		} else if (type.equals("Water")) {
			return WATER;
		} else if (type.equals("Electric")) {
			return ELECTRIC;
		} else if (type.equals("Grass")) {
			return GRASS;
		} else if (type.equals("Ice")) {
			return ICE;
		} else if (type.equals("Fighting")) {
			return FIGHTING;
		} else if (type.equals("Poison")) {
			return POISON;
		} else if (type.equals("Ground")) {
			return GROUND;
		} else if (type.equals("Flying")) {
			return FLYING;
		} else if (type.equals("Psychic")) {
			return PSYCHIC;
		} else if (type.equals("Bug")) {
			return BUG;
		} else if (type.equals("Rock")) {
			return ROCK;
		} else if (type.equals("Ghost")) {
			return GHOST;
		} else if (type.equals("Dragon")) {
			return DRAGON;
		} else if (type.equals("Dark")) {
			return DARK;
		} else if (type.equals("Steel")) {
			return STEEL;
		} else {
			return NORMAL;
		}
	}
	
	public int getType() {
		return myType;
	}
	
	public int getTypeModifier(Pokemon opponent) {
		int SUPER_EFFECTIVE;
		int STANDARD;
		int NOT_VERY_EFFECTIVE;
		int NO_DMG;
		int ot;
		
		SUPER_EFFECTIVE = 4;
		STANDARD = 2;
		NOT_VERY_EFFECTIVE = 1;
		NO_DMG = 0;
		
		ot = opponent.getType();
		
		// FROM: http://pokemondb.net/type
		if(myType == NORMAL) {
			if(ot == ROCK) {
				return NOT_VERY_EFFECTIVE;
			} else if(ot == GHOST || ot == STEEL) {
				return NO_DMG;
			}
			
		} else if (myType == FIRE) {
			if(ot == FIRE || ot == WATER || ot == ROCK || ot == DRAGON) {
				return NOT_VERY_EFFECTIVE;
			} else if(ot == GRASS || ot == ICE || ot == BUG || ot == STEEL){
				return SUPER_EFFECTIVE;
			}
			
		} else if (myType == WATER) {
			if(ot == FIRE || ot == GROUND || ot == ROCK) {
				return SUPER_EFFECTIVE;
			} else if (ot == WATER || ot == GRASS || ot == ROCK) {
				return NOT_VERY_EFFECTIVE;
			}
			
		} else if (myType == ELECTRIC) {
			if(ot == WATER || ot == FLYING) {
				return SUPER_EFFECTIVE;
			} else if (ot == ELECTRIC || ot == GRASS || ot == DRAGON){
				return NOT_VERY_EFFECTIVE;
			} else if (ot == GROUND) {
				return NO_DMG;
			}
			
		} else if (myType == GRASS) {
			if(ot == FIRE || ot == GRASS || ot == POISON || ot == FLYING || ot == BUG || ot == DRAGON || ot == STEEL) {
				return NOT_VERY_EFFECTIVE;
			} else if( ot == WATER || ot == GROUND || ot == ROCK) {
				return SUPER_EFFECTIVE;
			}
			
		} else if (myType == ICE) {
			if(ot == FIRE || ot == WATER || ot == ICE || ot == STEEL) {
				return NOT_VERY_EFFECTIVE;
			} else if(ot == GRASS || ot == GROUND || ot == FLYING || ot == DRAGON) {
				return SUPER_EFFECTIVE;
			}
			
		} else if (myType == FIGHTING) {
			if(ot == NORMAL || ot == ICE || ot == ROCK || ot == DARK || ot == STEEL) {
				return SUPER_EFFECTIVE;
			} else if(ot == POISON || ot == FLYING || ot == PSYCHIC || ot == BUG) {
				return NOT_VERY_EFFECTIVE;
			} else if(ot == GHOST) {
				return NO_DMG;
			}
			
		} else if (myType == POISON) {
			if(ot == GRASS) {
				return SUPER_EFFECTIVE;
			} else if (ot == POISON || ot == GROUND || ot == ROCK || ot == GHOST) {
				return NOT_VERY_EFFECTIVE;
			} else if (ot == STEEL) {
				return NO_DMG;
			}
			
		} else if (myType == GROUND) {
			if(ot == FIRE || ot == ELECTRIC || ot == POISON || ot == ROCK || ot == STEEL) {
				return SUPER_EFFECTIVE;
			} else if (ot == GRASS  || ot == BUG) {
				return NOT_VERY_EFFECTIVE;
			} else if (ot == FLYING) {
				return NO_DMG;
			}
			
		} else if (myType == FLYING) {
			if(ot == ELECTRIC || ot == ROCK || ot == STEEL) {
				return NOT_VERY_EFFECTIVE;
			} else if(ot == GRASS || ot == FIGHTING || ot == BUG) {
				return SUPER_EFFECTIVE;
			}
			
		} else if (myType == PSYCHIC) {
			if(ot == FIGHTING || ot == POISON) {
				return SUPER_EFFECTIVE;
			} else if(ot == PSYCHIC  || ot == STEEL) {
				return NOT_VERY_EFFECTIVE;
			} else if(ot == DARK) {
				return NO_DMG;
			}
			
		} else if (myType == BUG) {
			if(ot == FIRE || ot == FIGHTING || ot == POISON || ot == FLYING || ot == GHOST || ot == STEEL) {
				return NOT_VERY_EFFECTIVE;
			} else if (ot == GRASS || ot == PSYCHIC || ot == DARK) {
				return SUPER_EFFECTIVE;
			}
			
		} else if (myType == ROCK) {
			if(ot == FIRE || ot == ICE || ot == FLYING || ot == BUG) {
				return SUPER_EFFECTIVE;
			} else if (ot == FIGHTING || ot == GROUND || ot == STEEL) {
				return NOT_VERY_EFFECTIVE;
			}
			
		} else if (myType == GHOST) {
			if(ot == NORMAL) {
				return NO_DMG;
			} else if (ot == PSYCHIC || ot == GHOST) {
				return SUPER_EFFECTIVE;
			} else if (ot == DARK || ot == STEEL) {
				return NOT_VERY_EFFECTIVE;
			}
			
		} else if (myType == DRAGON) {
			if(ot == DRAGON) {
				return SUPER_EFFECTIVE;
			} else if (ot == STEEL) {
				return NOT_VERY_EFFECTIVE;
			}
			
		} else if (myType == DARK) {
			if(ot == POISON || ot == DARK || ot == STEEL) {
				return NOT_VERY_EFFECTIVE;
			} else if (ot == PSYCHIC  || ot == GHOST) {
				return SUPER_EFFECTIVE;
			}
			
		} else if (myType == STEEL) {
			if(ot == FIRE || ot == WATER || ot == ELECTRIC || ot == STEEL) {
				return NOT_VERY_EFFECTIVE;
			} else if (ot == ICE || ot == ROCK) {
				return SUPER_EFFECTIVE;
			}
		}
		
		return STANDARD;
	}
	
	
	public void chooseNextAction() {
		int randNum;
		randNum = r.nextInt();
		
		myAction = randNum % (SPECIAL_ATTACK + 1);
	}
	
	public int getActionAsInt() {
		return myAction;
	}
	
	public int getHPAsInt() {
		return currentHP;
	}
	
	public void setHP(int hp) {
		currentHP = hp;
	}
	
	public void applyAction(Pokemon p) {
		int pa;
		
		pa = p.getActionAsInt();
		
		if(myAction == ATTACK) {
			if(pa == DEFEND) {
				p.setHP(p.getHPAsInt() - atk / 4);
			} else if (pa == ATTACK || pa == SPECIAL_ATTACK) {
				p.setHP(p.getHPAsInt() - atk);
			}
			
		} else if (myAction == SPECIAL_ATTACK) {
			if(pa == DEFEND) {
				p.setHP(p.getHPAsInt() - spcAtk * this.getTypeModifier(p) / 2);
			} else if (pa == ATTACK || pa == SPECIAL_ATTACK) {
				p.setHP(p.getHPAsInt() - spcAtk * this.getTypeModifier(p));
			}
		}
	}
	
	public String getName() {
		return myName;
	}
	
	public String getAction() {
		if(myAction == ATTACK) {
			return "attacks";
		} else if (myAction == SPECIAL_ATTACK) {
			return "special attacks";
		} else { // implies defense.
			return "defends";
		}
	}
	
	public String getHP() {
		int hp;
		if(currentHP < 0) {
			hp = 0;
		} else {
			hp = currentHP;
		}
		return "" + hp;
	}
	
	public boolean isAlive() {
		return (currentHP > 0);
	}
	
	public void reset() {
		currentHP = myHP;
	}
}
