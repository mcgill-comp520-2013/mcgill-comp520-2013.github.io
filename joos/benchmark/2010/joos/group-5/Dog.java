import joos.lib.*;
import java.util.*; 

public class Dog extends Animal{

    public Dog(){
	super();
    }
    
    public void eat( Food food ){
        if ( food instanceof Fish ){
	    this.printAnnoyed();
	    f.println("only cats can bear this");
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
	    if( food.isEnough(3) ){ 
		this.happier( 10 );
		food.decreaseBy( 3 );
		this.printHappy();
		f.println("Delicious~~");
	    }
	    else f.println("No enough bones");
        }
    }

    public void playToy(){
	this.printHappy();
        f.println("No work all play~~");
	this.happier( 20 );
    }
    
    public void printNormal(){
	f.println("  _.-.");
	f.println("'( ^{_}    (");
	f.println("  `~\\`-----'\\");
	f.println("     )_)---)_)");
    }

    public void printAnnoyed(){
	f.println("        .-'-.");
	f.println("       /|9 9|\\");
	f.println("      {/(_^_)\\}");
	f.println("       _/   \\_");
	f.println("      (/ /^\\ \\)");
	f.println("       ''' ''' ");
    }

    public void printHappy(){
	f.println("        .-'-.");
	f.println("       /|^ ^|\\");
	f.println("      {/(_0_)\\}   ");
	f.println("       _/ ^ \\_ 9   ");
	f.println("      (/ /^\\ \\)   ");
	f.println("       ''' ''' ");
    }
}