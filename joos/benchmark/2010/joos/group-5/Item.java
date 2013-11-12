import joos.lib.*; 
import java.util.*; 

public abstract class Item{
    protected int HappyIndex;
    protected JoosIO f;
    
    public Item(){
	super();
	HappyIndex = 0;
	f = new JoosIO();
    }
    
    public int getHappyIndex(){
	return HappyIndex;
    }

    public abstract void reaction( Behavior e );

    public int happier( int happy){
	HappyIndex = HappyIndex + happy;
	return HappyIndex;
    }
}
