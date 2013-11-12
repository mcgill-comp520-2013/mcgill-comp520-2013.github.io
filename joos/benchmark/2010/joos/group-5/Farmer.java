import joos.lib.*; 
import java.util.*;

public class Farmer{
    protected int leftEnergy;
    protected JoosIO f;

    public Farmer(){
	super();
	f = new JoosIO();
	leftEnergy = 100;
    }

    public void spendEnergy( int energy ){
	leftEnergy = leftEnergy-energy;
    }

    public boolean enoughEnergy( int energy ){
	int e;
	e = leftEnergy-energy;
	if( e<0 ){
	    f.println("not enough energy left");
	    return false;
	}	    
	else {
	    return true;
	}
    }

    public int leftE(){
	return leftEnergy;
    }
}