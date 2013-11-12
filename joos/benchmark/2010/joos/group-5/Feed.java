import joos.lib.*;
import java.util.*;

public class Feed extends Behavior{
    
    protected Food feedFood;
    protected Farm feedFarm;

    public Feed( Farm farm ){
	super();
	feedFarm = farm;
	f.println("What food do you want to feed?");
	feedFood = feedFarm.getFood( f.readLine().trim() );
	neededEnergy = 5;
    }

    public Food getFood(){
	return feedFood;
    }
}