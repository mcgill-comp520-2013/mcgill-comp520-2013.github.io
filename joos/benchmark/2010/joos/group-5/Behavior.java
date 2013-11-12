import joos.lib.*; 
import java.util.*; 

public abstract class Behavior{
    protected int neededEnergy;
    protected JoosIO f;

    public Behavior(){
	super();
	f = new JoosIO();
    }
    
    public int getNeededEnergy(){
	return neededEnergy;
    }
}