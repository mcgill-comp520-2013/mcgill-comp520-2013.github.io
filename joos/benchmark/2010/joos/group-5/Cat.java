import joos.lib.*;
import java.util.*;

public class Cat extends Animal{
    
    public Cat(){
	super();
    }

    public void eat( Food food ){
	if ( food instanceof Fish ){
	    if( food.isEnough(3) ){
		food.decreaseBy( 3 );
		this.printHappy();
		this.happier( 10 );
	    }
	    else f.println("No enough fish");
	}

	if ( food instanceof Water){
	    if( food.isEnough(3) ){
		this.printHappy();
		this.happier( 5 );
		food.decreaseBy( 3 );
	    }
	    else f.println("No enough water");
	}

	if ( food instanceof Bone){
	    this.printAnnoyed();
	    f.println("leave it to dogs");
	}
    }

    public void playToy(){
	this.printHappy();
	this.happier(10);
    }

    public void printNormal(){
	f.println("         /\\_/\\");
	f.println("    ____/ o o \\");
	f.println("  /~____  =o= /");
	f.println(" (______)__m_m)");
    }

    public void printAnnoyed(){
        f.println("         /\\_/\\");
        f.println("    ____/|||||\\");
        f.println("  /~____   _  /");
        f.println(" (______)__m_m)");
    }

    public void printHappy(){
        f.println("         /\\_/\\");
        f.println("    ____/ ^ ^ \\");
        f.println("6 /~____   O  /");
        f.println(" (______)__m_m)");
    }

}