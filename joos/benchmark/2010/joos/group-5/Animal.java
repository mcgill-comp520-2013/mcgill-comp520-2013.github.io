import joos.lib.*;
import java.util.*;

public abstract class Animal extends Item{
    
    public Animal(){
	super();
    }

    public void reaction( Behavior b ){
        Food food;
        Feed feed;
	Play play;
        if ( b instanceof Feed ){
            feed = ( Feed ) b;
	    food = ( Food ) feed.getFood();
	    food.printImage();
            this.eat( food );
        }

	if ( b instanceof Play){
	    play = ( Play ) b;
	    this.playToy();
	}
    }

    public abstract void eat( Food food );
    public abstract void playToy();
    public abstract void printNormal();
    public abstract void printAnnoyed();
    public abstract void printHappy();
}