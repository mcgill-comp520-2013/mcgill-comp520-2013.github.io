import joos.lib.*;
import java.util.*;


public abstract class Item {
    
    protected JoosIO f;
    protected String name;

    public Item(){
	super();
    }
    
    public String getName(){
	return name;
    }
    public boolean canTake(){
	return false;
    }

    public String getDescription(){
	return name+" cannot be examined!";
    }

    public String formatString(String s){
	StringBuilder sb;
	int i;

	sb = new StringBuilder(s);
	i=0;
	//Inserts a new line at blank spaces about every 50 characters
	while ((i = sb.indexOf(" ", i + 50)) != -1) {
	    sb.replace(i, i + 1, "\n");
	}

	return sb.toString();
    }

}