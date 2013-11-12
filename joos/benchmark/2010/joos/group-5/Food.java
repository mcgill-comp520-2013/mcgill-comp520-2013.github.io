import joos.lib.*;
import java.util.*;

public abstract class Food{
    protected int amount;
    protected JoosIO f;
    
    public Food( int total ){
	super();
	f = new JoosIO();
	amount = total;
    }

    public int decreaseBy( int num ){
	amount = amount-num;
	return amount; 
    }
    
    public boolean isEnough( int num ){
	int estimate;
	estimate = 0;
	estimate = amount - num;
	if ( estimate > 0 )
	    return true;
	else
	    return false;
    }

    public abstract void printImage();
}